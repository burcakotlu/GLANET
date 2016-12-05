package giveninputdata;

import enumtypes.Assembly;
import enumtypes.CommandLineArguments;
import enumtypes.GivenIntervalsInputFileDataFormat;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import intervaltree.IntervalTreeNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jaxbxjctool.AugmentationofGivenRsIdwithInformation;
import jaxbxjctool.NCBIEutilStatistics;
import jaxbxjctool.RsInformation;

import org.apache.log4j.Logger;

import remap.Remap;
import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

/**
 * @author burcakotlu
 * @date Mar 24, 2014 
 * @time 1:58:07 PM
 */

/**
 *  GLANET Convention
 *  0 Based Start
 *  0 Based End (Inclusive)
 */
public class InputDataProcess {

	final static Logger logger = Logger.getLogger(InputDataProcess.class);

	// for debug purposes
	static Collection<IntervalTreeNode> nonOverLap( Collection<IntervalTreeNode> bigger,
			Collection<IntervalTreeNode> smaller) {

		Collection<IntervalTreeNode> result = bigger;
		result.removeAll( smaller);
		return result;
	}

	// for debug purposes
	static void printDifference( Collection<IntervalTreeNode> difference) {

		Iterator<IntervalTreeNode> itr = difference.iterator();

		while( itr.hasNext()){

			GlanetRunner.appendLog( itr.toString());
			itr.next();

		}
	}

	public static void writeRSIDLatestAssemblyGRCh37p13AssemblyFile(
			TIntObjectMap<String> lineNumber2SourceInformationMap,
			TIntObjectMap<String> lineNumber2SourceGenomicLociMap,
			TIntObjectMap<String> lineNumber2TargetGenomicLociMap, BufferedWriter bufferedWriter) throws IOException {

		int lineNumber = 0;
		String rsId = null;
		String source = null;
		String target = null;

		// Write Header Line
		bufferedWriter.write( "#rsId" + "\t" + "chrName" + "\t" + "1BasedStartLatestAssembly" + "\t" + "1BasedEndLatestAssembly" + "\t" + "chrName" + "\t" + "1BasedStartGRCh37p13Assembly" + "\t" + "1BasedEndGRCh37p13Assembly" + System.getProperty( "line.separator"));

		// MAP
		// accessing keys/values through an iterator:
		for( TIntObjectIterator<String> it = lineNumber2SourceInformationMap.iterator(); it.hasNext();){

			it.advance();

			lineNumber = it.key();
			rsId = ( String)it.value();

			source = lineNumber2SourceGenomicLociMap.get( lineNumber);
			target = lineNumber2TargetGenomicLociMap.get( lineNumber);

			bufferedWriter.write( rsId + "\t" + source + "\t" + target + System.getProperty( "line.separator"));

		}// End of FOR:

	}

	// eutil efetch returns 0-based coordinates for given dbSNP ids
	public static void readDBSNPIDs(
			String dataFolder, 
			String inputFileName, 
			Assembly inputFileAssembly,
			String givenDataFolder) {

		// read the file line by line
		BufferedReader bufferedReader = null;

		BufferedWriter bufferedWriter = null;
		BufferedWriter remapInputFileBufferedWriter = null;
		BufferedWriter rsID_LatestAssembly_GRCh37p13Assembly_BufferedWriter = null;

		String rsId = null;
		List<String> rsIdList = new ArrayList<String>();

		int numberofGivenRsIds = 0;
		int numberofGivenUniqueRsIds = 0;

		int numberofRsIdsLost = 0;
		int numberofRsIdsGainedByMerge = 0;

		NCBIEutilStatistics ncbiEutilStatistics = new NCBIEutilStatistics( 0, 0);
		int numberofRsIdsWeMightHaveLostAfterNCBIEUTILs = 0;

		int numberofLocisInRemapInputFile = 1;

		/**********************************************************/
		/********** NCBI REMAP PARAMETERS starts ******************/
		/**********************************************************/
		
		//1 DEC 2016
		//Each rsID may not have the same group label.
		//That's why keep the number of rsIDs for each group label
		//And then we must do a conversion based on the majority of the rsIDs
		Map<String,Integer> sourceAssemblyName2NumberofrsIDsMap = new HashMap<String,Integer>();
		Integer numberofRSIDsInThisSourceAssembly = -1;
		
		String sourceAssemblyName = "";
		String targetAssemblyName = Commons.GRCH37_P13;

		String sourceReferenceAssemblyID = null;
		String targetReferenceAssemblyID = null;

		String merge = null;
		String allowMultipleLocation = null;
		double minimumRatioOfBasesThatMustBeRemapped;
		double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength;
		/**********************************************************/
		/********** NCBI REMAP PARAMETERS ends ********************/
		/**********************************************************/

		String headerLine = Commons.HEADER_LINE_FOR_DBSNP_IDS_FROM_LATEST_ASSEMBLY_TO_GRCH37_P13;

		TIntObjectMap<String> lineNumber2SourceGenomicLociMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> lineNumber2SourceInformationMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> lineNumber2TargetGenomicLociMap = new TIntObjectHashMap<String>();

		String remapInputFileLine = null;

		try{
			bufferedReader = new BufferedReader( new FileReader( inputFileName));

			// This file will have NCBI EUTIL results
			bufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter( givenDataFolder + Commons.RSID_CHRNAME_0Based_START_END_NCBI_EUTIL_RETURNED_LATEST_ASSEMBLY_FILE));
			remapInputFileBufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter( givenDataFolder + Commons.REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE));

			// This file will have NCBI REMAP results
			rsID_LatestAssembly_GRCh37p13Assembly_BufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter( givenDataFolder + Commons.RSID_ChrName1BasedStartEndLatestAssembly_ChrName1BasedStartEndGRCh37p13_FILE));

			AugmentationofGivenRsIdwithInformation app = new AugmentationofGivenRsIdwithInformation();

			/*********************************************************************/
			/***************** READ GIVEN RSIDs INPUTFILE starts *****************/
			/*********************************************************************/
			while( ( rsId = bufferedReader.readLine()) != null){

				// Skip comment lines
				if( !( rsId.startsWith( Commons.GLANET_COMMENT_STRING))){

					numberofGivenRsIds++;

					if( !rsIdList.contains( rsId)){
						numberofGivenUniqueRsIds++;
						rsIdList.add( rsId);
					}
				}// End of if not comment line
			}// End of WHILE

			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");
			if( GlanetRunner.shouldLog())logger.info( "Number of rsIds in the given rsID input file: " + numberofGivenRsIds);
			if( GlanetRunner.shouldLog())logger.info( "Number of unique rsIds in the given rsID input file: " + numberofGivenUniqueRsIds);
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");
			/*********************************************************************/
			/***************** READ GIVEN RSIDs INPUTFILE ends *******************/
			/*********************************************************************/

			/*********************************************************************/
			/******** GET rsInformation using NCBI EUTILS starts *****************/
			/*********************************************************************/
			List<RsInformation> rsInformationList = app.getInformationforGivenRsIdList( rsIdList, ncbiEutilStatistics);
			/*********************************************************************/
			/******** GET rsInformation using NCBI EUTILS ends *******************/
			/*********************************************************************/

			/************************************************************************************/
			/*************** NCBI EUTIL EFETCH RESULTS ANALYSIS STARTS ****************************/
			/************************************************************************************/
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");
			numberofRsIdsLost = 0;

			for( int i = 0; i < rsIdList.size(); i++){
				boolean check = false;
				for( int j = 0; j < rsInformationList.size(); j++)
					if( ( Commons.RS + rsInformationList.get( j).getRsId()).equalsIgnoreCase( rsIdList.get( i))){
						check = true;
						break;
					}

				if( !check)
					if( GlanetRunner.shouldLog())logger.info( "rsId Lost Count: " + ++numberofRsIdsLost + " Given input rsID: " + rsIdList.get( i) + " Not found in the list returned by NCBI EUTIL");

			}// End of FOR

			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");

			numberofRsIdsGainedByMerge = 0;

			for( int i = 0; i < rsInformationList.size(); i++){
				boolean check = false;
				for( int j = 0; j < rsIdList.size(); j++)
					if( rsIdList.get( j).equalsIgnoreCase( Commons.RS + rsInformationList.get( i).getRsId())){
						check = true;
						break;
					}

				if( !check)
					if( GlanetRunner.shouldLog())logger.info( "rsId Gained By Merge Count: " + ++numberofRsIdsGainedByMerge + " NCBI EUTIL returned rsID: " + rsInformationList.get(
							i).getRsId() + " Not found in the given rsIDList");

			}// End of FOR
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");
			if( GlanetRunner.shouldLog())logger.info( "We have " + rsIdList.size() + " rsIds at hand before NCBI EUTIL EFETCH");

			if( GlanetRunner.shouldLog())logger.info( "Number of given rsIds that are lost " + numberofRsIdsLost);
			if( GlanetRunner.shouldLog())logger.info( "Number of NCBI EUTIL returned rsIds that are gained " + numberofRsIdsGainedByMerge);

			if( GlanetRunner.shouldLog())logger.info( "Number of given rsIds  does not map to any assembly " + ncbiEutilStatistics.getNumberofRsIDsDoesNotMapToAnyAssembly());
			if( GlanetRunner.shouldLog())logger.info( "Number of given rsIds  does not return any rsID " + ncbiEutilStatistics.getNumberofRsIDsDoesNotReturnAnyRs());

			if( GlanetRunner.shouldLog())logger.info( "We have " + rsInformationList.size() + " rsIds remained after NCBI EUTIL EFETCH");

			numberofRsIdsWeMightHaveLostAfterNCBIEUTILs = numberofRsIdsLost - ncbiEutilStatistics.getNumberofRsIDsDoesNotMapToAnyAssembly() - ncbiEutilStatistics.getNumberofRsIDsDoesNotReturnAnyRs() - numberofRsIdsGainedByMerge;

			if( GlanetRunner.shouldLog())logger.info( "We might have lost " + numberofRsIdsWeMightHaveLostAfterNCBIEUTILs + " rsIDs after NCBI EUTIL");
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");
			/************************************************************************************/
			/*************** NCBI EUTIL EFETCH RESULTS ANALYSIS ENDS ******************************/
			/************************************************************************************/

			// header line
			bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "rsID" + "\t" + "chrName" + "\t" + "0BasedStart_in_LatestAssembly" + "\t" + "0BasedEnd_in_LatestAssembly" + System.getProperty( "line.separator"));

			/*********************************************************************/
			/***************** WRITE TO REMAP INPUT FILE starts ******************/
			/*********************************************************************/
			for( RsInformation rsInformation : rsInformationList){

				if( rsInformation != null){

					// My understanding: Remap input file requires 0BasedStart
					// EndExclusive
					remapInputFileLine = Commons.CHR + rsInformation.getChrNameWithoutChr() + "\t" + rsInformation.getZeroBasedStart() + "\t" + ( rsInformation.getZeroBasedEnd() + 1);

					bufferedWriter.write( Commons.RS + rsInformation.getRsId() + "\t" + Commons.CHR + rsInformation.getChrNameWithoutChr() + "\t" + rsInformation.getZeroBasedStart() + "\t" + rsInformation.getZeroBasedEnd() + System.getProperty( "line.separator"));
					remapInputFileBufferedWriter.write( remapInputFileLine + System.getProperty( "line.separator"));

					lineNumber2SourceGenomicLociMap.put(
							numberofLocisInRemapInputFile,
							Commons.CHR + rsInformation.getChrNameWithoutChr() + "\t" + ( rsInformation.getZeroBasedStart() + 1) + "\t" + ( rsInformation.getZeroBasedEnd() + 1));
					lineNumber2SourceInformationMap.put( numberofLocisInRemapInputFile,
							Commons.RS + rsInformation.getRsId());

					numberofLocisInRemapInputFile++;

					//Fill the map 
					if (!sourceAssemblyName2NumberofrsIDsMap.containsKey(rsInformation.getGroupLabel())){
						sourceAssemblyName2NumberofrsIDsMap.put(rsInformation.getGroupLabel(), 1);
					}else{
						sourceAssemblyName2NumberofrsIDsMap.put(rsInformation.getGroupLabel(), sourceAssemblyName2NumberofrsIDsMap.get(rsInformation.getGroupLabel())+1);
					}
						
				}// End of IF rsInformation is not null

			}// End of for
			
			//Now set the sourceAssemblyName based on majority of rs IDs
			for(Map.Entry<String, Integer> entry: sourceAssemblyName2NumberofrsIDsMap.entrySet()){
				
				if (entry.getValue()>numberofRSIDsInThisSourceAssembly){
					numberofRSIDsInThisSourceAssembly = entry.getValue();
					sourceAssemblyName = entry.getKey();
				}
			}

			numberofLocisInRemapInputFile--;

			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");
			if( GlanetRunner.shouldLog())logger.info( "Number of genomic loci is " + numberofLocisInRemapInputFile + " in NCBI REMAP input file in sourceAssembly " + sourceAssemblyName);
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");
			/*********************************************************************/
			/***************** WRITE TO REMAP INPUT FILE ends ********************/
			/*********************************************************************/

			// Close
			bufferedReader.close();
			bufferedWriter.close();
			remapInputFileBufferedWriter.close();

			Remap.remap_show_batches( dataFolder, Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);

			Map<String, String> assemblyName2RefSeqAssemblyIDMap = new HashMap<String, String>();
			Remap.fillAssemblyName2RefSeqAssemblyIDMap(
					dataFolder, 
					Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE,
					assemblyName2RefSeqAssemblyIDMap);
			
			if( !sourceAssemblyName.isEmpty()){
				// sourceReferenceAssemblyID = "GCF_000001405.26";
				sourceReferenceAssemblyID = assemblyName2RefSeqAssemblyIDMap.get( sourceAssemblyName);
			}else{
				if( GlanetRunner.shouldLog())logger.error( Commons.THERE_IS_A_SITUATION + " sourceAssemblyName is " + sourceAssemblyName);
			}

			// targetReferenceAssemblyID = "GCF_000001405.25";
			targetReferenceAssemblyID = assemblyName2RefSeqAssemblyIDMap.get(targetAssemblyName);

			merge = Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON;
			allowMultipleLocation = Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON;
			minimumRatioOfBasesThatMustBeRemapped = Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_;
			maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength = Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2;

			String inputFormat = Commons.BED;

			// Could not find an alignment batch for your assembly pair: GRCh38x GRCh37.p13
			// Please run "--mode batches" for a list of available assembly pairs.
			// Remap.remap(dataFolder,"GRCh38", "GRCh37.p13", outputFolder +
			// Commons.CHRNAME_0Based_START_Inclusive_END_Exclusive_HG38_BED_FILE,
			// outputFolder +
			// Commons.CHRNAME_0Based_START_Inclusive_END_Exclusive_HG19_BED_FILE);

			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");

			Remap.remap(
					dataFolder,
					sourceReferenceAssemblyID,
					targetReferenceAssemblyID,
					givenDataFolder + Commons.REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE,
					givenDataFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
					givenDataFolder + Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE,
					givenDataFolder + Commons.REMAP_DUMMY_GENOME_WORKBENCH_PROJECT_FILE, 
					merge, 
					allowMultipleLocation,
					minimumRatioOfBasesThatMustBeRemapped,
					maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength, inputFormat,
					Commons.REMAP_DBSNP_IDS_COORDINATES_FROM_LATEST_ASSEMBLY_TO_GRCH37P13);

			Remap.fillConversionMap(
					givenDataFolder, 
					Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE,
					lineNumber2SourceGenomicLociMap, 
					lineNumber2TargetGenomicLociMap);

			Remap.convertOneGenomicLociPerLineUsingMap(
					givenDataFolder,
					Commons.REMAP_OUTPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_1Based_START_END_BED_FILE_USING_REMAP_REPORT,
					lineNumber2SourceGenomicLociMap, 
					lineNumber2SourceInformationMap, 
					lineNumber2TargetGenomicLociMap,
					headerLine);

			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");

			// Write
			// rsId_chrNameStartEndLatestAssembly_chrNameStartEndGRCh37p13Assembly_File
			writeRSIDLatestAssemblyGRCh37p13AssemblyFile(
					lineNumber2SourceInformationMap,
					lineNumber2SourceGenomicLociMap, 
					lineNumber2TargetGenomicLociMap,
					rsID_LatestAssembly_GRCh37p13Assembly_BufferedWriter);

			// Read from GRCh37.p13 (Hg19) bed file
			// Write to usual processed input file in GRCh37_hg19
			FileOperations.readFromBedFileWriteToGlanetFile(
					givenDataFolder,
					Commons.REMAP_OUTPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_1Based_START_END_BED_FILE_USING_REMAP_REPORT,
					Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh37_p13);

			// Close
			rsID_LatestAssembly_GRCh37p13Assembly_BufferedWriter.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( Exception e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	// BED format is 0-based, end is exclusive
	// In BED format, bed lines have three required fileds and nine additional
	// optional fields.
	// chrom chr3 chrY chr2_random
	// chromStart first base in a chromosome is numbered 0.
	// chromEnd is exclusive, fisrt 100 bases of a chromosome are defined as
	// chromStart=0 chromEnd=100, and
	// span the bases numbered 0-99.
	public static void readBEDFile( String inputFileName, Assembly inputFileAssembly, String givenDataFolder) {

		// read the file line by line
		BufferedReader bufferedReader = null;

		// write to output file line by line
		BufferedWriter bufferedWriter = null;

		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;

		String chrName;
		int start, exclusiveEnd, inclusiveEnd;

		try{
			bufferedReader = new BufferedReader( new FileReader( inputFileName));

			switch( inputFileAssembly){
			case GRCh38:
				bufferedWriter = new BufferedWriter(
						FileOperations.createFileWriter( givenDataFolder + Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh38));
				break;
			case GRCh37_p13:
				bufferedWriter = new BufferedWriter(
						FileOperations.createFileWriter( givenDataFolder + Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh37_p13));
				break;
			}// End of SWITCH

			while( ( strLine = bufferedReader.readLine()) != null){

				// Skip comment lines
				if( !( strLine.startsWith( "#"))){

					indexofFirstTab = strLine.indexOf( '\t');
					indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
					indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;

					chrName = strLine.substring( 0, indexofFirstTab);
					start = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

					if( indexofThirdTab > 0){
						exclusiveEnd = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));
					}else{
						exclusiveEnd = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
					}

					if( !( chrName.startsWith( "chr"))){
						// add "chr"
						chrName = "chr" + chrName;
					}

					// get the inclusive end
					inclusiveEnd = exclusiveEnd - 1;

					bufferedWriter.write( chrName + "\t" + start + "\t" + inclusiveEnd + System.getProperty( "line.separator"));
				}// End of if not comment line
			}

			bufferedWriter.close();
			bufferedReader.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		// @todo remove overlaps if any exists
	}

	// GFF3 format is 1-based, end is inclusive
	// example GFF3 input line
	// chrX experiment SNP 146993388 146993388 . - 0 cellType=HeLA
	public static void readGFF3File( String inputFileName, Assembly inputFileAssembly, String givenDataFolder) {

		// read the file line by line
		BufferedReader bufferedReader = null;

		// write to output file line by line
		BufferedWriter bufferedWriter = null;

		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;

		String chrName;
		int oneBasedStart, oneBasedInclusiveEnd;
		int zeroBasedStart, zeroBasedInclusiveEnd;

		try{
			bufferedReader = new BufferedReader( new FileReader( inputFileName));

			switch( inputFileAssembly){
			case GRCh38:
				bufferedWriter = new BufferedWriter(
						FileOperations.createFileWriter( givenDataFolder + Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh38));
				break;
			case GRCh37_p13:
				bufferedWriter = new BufferedWriter(
						FileOperations.createFileWriter( givenDataFolder + Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh37_p13));
				break;
			}// End of SWITCH

			while( ( strLine = bufferedReader.readLine()) != null){

				// Skip comment lines
				if( !( strLine.startsWith( "#"))){
					indexofFirstTab = strLine.indexOf( '\t');
					indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
					indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
					indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
					indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;

					chrName = strLine.substring( 0, indexofFirstTab);
					oneBasedStart = Integer.parseInt( strLine.substring( indexofThirdTab + 1, indexofFourthTab));

					if( indexofFifthTab > 0){
						oneBasedInclusiveEnd = Integer.parseInt( strLine.substring( indexofFourthTab + 1,
								indexofFifthTab));
					}else{
						oneBasedInclusiveEnd = Integer.parseInt( strLine.substring( indexofFourthTab + 1));
					}

					if( !( chrName.startsWith( "chr"))){
						// add "chr"
						chrName = "chr" + chrName;
					}

					// get the 0-based start
					zeroBasedStart = oneBasedStart - 1;

					// get the 0-based inclusive end
					zeroBasedInclusiveEnd = oneBasedInclusiveEnd - 1;

					bufferedWriter.write( chrName + "\t" + zeroBasedStart + "\t" + zeroBasedInclusiveEnd + System.getProperty( "line.separator"));
				}// End of if not comment line

			}

			bufferedWriter.close();
			bufferedReader.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	// 1-based coordinates, start and end are inclusive.
	// 1 100 200
	// 1 100
	// chr1 100
	// chr1 100 200
	public static void readOneBasedCoordinates( String inputFileName, Assembly inputFileAssembly, String givenDataFolder) {

		int zeroBasedStart;
		int zeroBasedInclusiveEnd;

		// read the file line by line
		BufferedReader bufferedReader = null;

		// write to output file line by line
		BufferedWriter bufferedWriter = null;

		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;

		int indexofColon;
		int indexofDot;
		int indexofHyphen;

		String chrName = null;
		int oneBasedStart = 0;
		int oneBasedInclusiveEnd = 0;

		try{

			bufferedReader = new BufferedReader( new FileReader( inputFileName));

			switch( inputFileAssembly){
			case GRCh38:
				bufferedWriter = new BufferedWriter(
						FileOperations.createFileWriter( givenDataFolder + Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh38));
				break;
			case GRCh37_p13:
				bufferedWriter = new BufferedWriter(
						FileOperations.createFileWriter( givenDataFolder + Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh37_p13));
				break;
			}// End of SWITCH

			while( ( strLine = bufferedReader.readLine()) != null){

				// skip comment lines
				if( !( strLine.startsWith( "#"))){

					indexofFirstTab = strLine.indexOf( '\t');
					indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
					indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;

					indexofColon = strLine.indexOf( ':');
					indexofDot = strLine.indexOf( '.');
					indexofHyphen = strLine.indexOf( '-');

					// 19:12995239..12998702
					// 19:12995239-12998702
					// chr19:12995239-12998702
					// chr11:524690..5247000
					// there is no tab
					if( indexofColon >= 0){
						chrName = strLine.substring( 0, indexofColon);

						if( !( chrName.startsWith( "chr"))){
							// add "chr"
							chrName = "chr" + chrName;
						}

						if( indexofHyphen >= 0){
							oneBasedStart = Integer.parseInt( strLine.substring( indexofColon + 1, indexofHyphen));
							oneBasedInclusiveEnd = Integer.parseInt( strLine.substring( indexofHyphen + 1).trim());
						}else if( indexofDot >= 0){
							oneBasedStart = Integer.parseInt( strLine.substring( indexofColon + 1, indexofDot));
							oneBasedInclusiveEnd = Integer.parseInt( strLine.substring( indexofDot + 2).trim());
						}
						// 19:12995239
						// chr19:12995239
						else{
							oneBasedStart = Integer.parseInt( strLine.substring( indexofColon + 1).trim());
							oneBasedInclusiveEnd = oneBasedStart;
						}
					}
					// chrX 100 200
					// X 100 200
					else if( indexofThirdTab >= 0 && indexofSecondTab >= 0 && indexofFirstTab >= 0){

						chrName = strLine.substring( 0, indexofFirstTab);

						if( !( chrName.startsWith( "chr"))){
							// add "chr"
							chrName = "chr" + chrName;
						}

						oneBasedStart = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
						oneBasedInclusiveEnd = Integer.parseInt( strLine.substring( indexofSecondTab + 1,
								indexofThirdTab).trim());

					}

					// chrX 100 200
					// X 100 200
					else if( indexofSecondTab >= 0 && indexofFirstTab >= 0){

						chrName = strLine.substring( 0, indexofFirstTab);

						if( !( chrName.startsWith( "chr"))){
							// add "chr"
							chrName = "chr" + chrName;
						}

						oneBasedStart = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
						oneBasedInclusiveEnd = Integer.parseInt( strLine.substring( indexofSecondTab + 1).trim());
					}
					// chrX 100
					// X 100
					else if( indexofFirstTab >= 0){

						chrName = strLine.substring( 0, indexofFirstTab);

						if( !( chrName.startsWith( "chr"))){
							// add "chr"
							chrName = "chr" + chrName;
						}

						oneBasedStart = Integer.parseInt( strLine.substring( indexofFirstTab + 1).trim());
						oneBasedInclusiveEnd = oneBasedStart;

					}

					zeroBasedStart = oneBasedStart - 1;
					zeroBasedInclusiveEnd = oneBasedInclusiveEnd - 1;

					bufferedWriter.write( chrName + "\t" + zeroBasedStart + "\t" + zeroBasedInclusiveEnd + System.getProperty( "line.separator"));
				}// End of if not comment line

			}// End of while

			bufferedWriter.close();
			bufferedReader.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
	}

	// 0-based coordinates, start and end are inclusive.
	// 1 100 200
	// 1 100
	// chr1 100
	// chr1 100 200
	public static void readZeroBasedCoordinates( String inputFileName, Assembly inputFileAssembly,
			String givenDataFolder) {

		// read the file line by line
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		// write to output file line by line
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;

		int indexofColon;
		int indexofDot;
		int indexofHyphen;

		String chrName = null;
		int zeroBasedStart = 0;
		int zeroBasedInclusiveEnd = 0;

		try{

			fileReader = new FileReader( inputFileName);
			bufferedReader = new BufferedReader( fileReader);

			switch( inputFileAssembly){
			case GRCh38:
				fileWriter = FileOperations.createFileWriter( givenDataFolder + Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh38);
				break;
			case GRCh37_p13:
				fileWriter = FileOperations.createFileWriter( givenDataFolder + Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh37_p13);
				break;
			}// End of SWITCH

			bufferedWriter = new BufferedWriter( fileWriter);

			while( ( strLine = bufferedReader.readLine()) != null){

				// skip comment lines
				if( !( strLine.startsWith( "#"))){

					indexofFirstTab = strLine.indexOf( '\t');
					indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
					indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;

					indexofColon = strLine.indexOf( ':');
					indexofDot = strLine.indexOf( '.');
					indexofHyphen = strLine.indexOf( '-');

					// 19:12995239..12998702
					// 19:12995239-12998702
					// chr19:12995239-12998702
					// chr11:524690..5247000
					// there is no tab
					if( indexofColon >= 0){
						chrName = strLine.substring( 0, indexofColon);

						if( !( chrName.startsWith( "chr"))){
							// add "chr"
							chrName = "chr" + chrName;
						}

						if( indexofHyphen >= 0){
							zeroBasedStart = Integer.parseInt( strLine.substring( indexofColon + 1, indexofHyphen));
							zeroBasedInclusiveEnd = Integer.parseInt( strLine.substring( indexofHyphen + 1).trim());
						}else if( indexofDot >= 0){
							zeroBasedStart = Integer.parseInt( strLine.substring( indexofColon + 1, indexofDot));
							zeroBasedInclusiveEnd = Integer.parseInt( strLine.substring( indexofDot + 2).trim());
						}
						// 19:12995239
						// chr19:12995239
						else{
							zeroBasedStart = Integer.parseInt( strLine.substring( indexofColon + 1).trim());
							zeroBasedInclusiveEnd = zeroBasedStart;
						}
					}

					// chrX 100 200 blablabla
					// X 100 200 blablabla
					else if( indexofThirdTab >= 0 && indexofSecondTab >= 0 && indexofFirstTab >= 0){

						chrName = strLine.substring( 0, indexofFirstTab);

						if( !( chrName.startsWith( "chr"))){
							// add "chr"
							chrName = "chr" + chrName;
						}

						zeroBasedStart = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
						zeroBasedInclusiveEnd = Integer.parseInt( strLine.substring( indexofSecondTab + 1,
								indexofThirdTab).trim());

					}

					// chrX 100 200
					// X 100 200
					else if( indexofSecondTab >= 0 && indexofFirstTab >= 0){

						chrName = strLine.substring( 0, indexofFirstTab);

						if( !( chrName.startsWith( "chr"))){
							// add "chr"
							chrName = "chr" + chrName;
						}

						zeroBasedStart = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
						zeroBasedInclusiveEnd = Integer.parseInt( strLine.substring( indexofSecondTab + 1).trim());
					}
					// chrX 100
					// X 100
					else if( indexofFirstTab >= 0){

						chrName = strLine.substring( 0, indexofFirstTab);

						if( !( chrName.startsWith( "chr"))){
							// add "chr"
							chrName = "chr" + chrName;
						}

						zeroBasedStart = Integer.parseInt( strLine.substring( indexofFirstTab + 1).trim());
						zeroBasedInclusiveEnd = zeroBasedStart;

					}

					bufferedWriter.write( chrName + "\t" + zeroBasedStart + "\t" + zeroBasedInclusiveEnd + System.getProperty( "line.separator"));

				}// End of if not comment line

			}

			// Close BufferedWriters
			bufferedWriter.close();
			bufferedReader.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	public static void processInputData( 
			String inputFileName, 
			GivenIntervalsInputFileDataFormat inputFileFormat,
			Assembly inputFileAssembly, 
			String givenDataFolder, 
			String dataFolder) {

		switch( inputFileFormat){
			case INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES:
				readZeroBasedCoordinates( inputFileName, inputFileAssembly, givenDataFolder);
				break;
			case INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES:
				readOneBasedCoordinates( inputFileName, inputFileAssembly, givenDataFolder);
				break;
			case INPUT_FILE_FORMAT_DBSNP_IDS:
				readDBSNPIDs( dataFolder, inputFileName, inputFileAssembly, givenDataFolder);
				break;
			case INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES:
				readBEDFile( inputFileName, inputFileAssembly, givenDataFolder);
				break;
			case INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES:
				readGFF3File( inputFileName, inputFileAssembly, givenDataFolder);
				break;

		}// End of SWITCH

	}

	// args[0] ---> Input File Name with folder
	// args[1] ---> GLANET installation folder with "\\" at the end. This folder
	// will be used for outputFolder and dataFolder.
	// args[2] ---> Input File Format
	// ---> default
	// Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// args[3] ---> Annotation, overlap definition, number of bases,
	// default 1
	// args[4] ---> Perform Enrichment parameter
	// ---> default Commons.DO_ENRICH
	// ---> Commons.DO_NOT_ENRICH
	// args[5] ---> Generate Random Data Mode
	// ---> default Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	// ---> Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT
	// args[6] ---> multiple testing parameter, enriched elements will be
	// decided and sorted with respect to this parameter
	// ---> default Commons.BENJAMINI_HOCHBERG_FDR
	// ---> Commons.BONFERRONI_CORRECTION
	// args[7] ---> Bonferroni Correction Significance Level, default 0.05
	// args[8] ---> Bonferroni Correction Significance Criteria, default 0.05
	// args[9] ---> Number of permutations, default 5000
	// args[10] ---> Dnase Enrichment
	// ---> default Commons.DO_NOT_DNASE_ENRICHMENT
	// ---> Commons.DO_DNASE_ENRICHMENT
	// args[11] ---> Histone Enrichment
	// ---> default Commons.DO_NOT_HISTONE_ENRICHMENT
	// ---> Commons.DO_HISTONE_ENRICHMENT
	// args[12] ---> Transcription Factor(TF) Enrichment
	// ---> default Commons.DO_NOT_TF_ENRICHMENT
	// ---> Commons.DO_TF_ENRICHMENT
	// args[13] ---> KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_KEGGPATHWAY_ENRICHMENT
	// args[14] ---> TF and KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	// args[15] ---> TF and CellLine and KeggPathway Enrichment
	// ---> default Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// args[16] ---> RSAT parameter
	// ---> default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// ---> Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// args[17] ---> job name example: ocd_gwas_snps
	// args[18] ---> writeGeneratedRandomDataMode checkBox
	// ---> default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	// ---> Commons.WRITE_GENERATED_RANDOM_DATA
	// args[19] ---> writePermutationBasedandParametricBasedAnnotationResultMode
	// checkBox
	// ---> default
	// Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// --->
	// Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// args[20] ---> writePermutationBasedAnnotationResultMode checkBox
	// ---> default Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	// ---> Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	// args[21] ---> number of permutations in each run. Default is 2000
	// args[22] ---> UserDefinedGeneSet Enrichment
	// default Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT
	// Commons.DO_USER_DEFINED_GENESET_ENRICHMENT
	// args[23] ---> UserDefinedGeneSet InputFile
	// args[24] ---> UserDefinedGeneSet GeneInformationType
	// default Commons.GENE_ID
	// Commons.GENE_SYMBOL
	// Commons.RNA_NUCLEOTIDE_ACCESSION
	// args[25] ---> UserDefinedGeneSet Name
	// args[26] ---> UserDefinedGeneSet Optional GeneSet Description InputFile
	// args[27] ---> UserDefinedLibrary Enrichment
	// default Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT
	// Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT
	// args[28] ---> UserDefinedLibrary InputFile
	// args[29] - args[args.length-1] ---> Note that the selected cell lines are
	// always inserted at the end of the args array because it's size
	// is not fixed. So for not (until the next change on args array) the
	// selected cell
	// lines can be reached starting from 22th index up until (args.length-1)th
	// index.
	// If no cell line selected so the args.length-1 will be 22-1 = 21. So it
	// will never
	// give an out of boundry exception in a for loop with this approach.
	public static void main( String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		Assembly inputFileAssembly = Assembly.convertStringtoEnum( args[CommandLineArguments.InputFileAssembly.value()]);

		// jobName starts
//		String jobName = args[CommandLineArguments.JobName.value()].trim();
//		if( jobName.isEmpty()){
//			jobName = Commons.NO_NAME;
//		}
		// jobName ends

		String inputFileName = args[CommandLineArguments.InputFileNameWithFolder.value()];
		GivenIntervalsInputFileDataFormat inputFileFormat = GivenIntervalsInputFileDataFormat.convertStringtoEnum( args[CommandLineArguments.InputFileDataFormat.value()]);

//		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty( "file.separator") + jobName + System.getProperty( "file.separator");
		String outputFolder = args[CommandLineArguments.OutputFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
		String givenDataFolder = outputFolder + Commons.GIVENINPUTDATA + System.getProperty( "file.separator");

		/********************************************************************/
		/*********** delete old files starts **********************************/
		String givenInputDataOutputBaseDirectoryName = outputFolder + Commons.GIVENINPUTDATA;

		FileOperations.deleteOldFiles( givenInputDataOutputBaseDirectoryName);
		/*********** delete old files ends ***********************************/
		/******************************************************************/

		// Read input data
		// Process input data
		// Write output data
		processInputData(inputFileName,inputFileFormat,inputFileAssembly,givenDataFolder,dataFolder);
	}

}
