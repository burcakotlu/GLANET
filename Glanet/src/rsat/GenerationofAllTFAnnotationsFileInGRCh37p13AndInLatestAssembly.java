/**
 * 
 */
package rsat;

import enumtypes.CommandLineArguments;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jaxbxjctool.NCBIEutils;

import org.apache.log4j.Logger;

import remap.Remap;
import ui.GlanetRunner;
import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Burcak Otlu
 * @date Dec 25, 2014
 * @project Glanet
 *
 */
public class GenerationofAllTFAnnotationsFileInGRCh37p13AndInLatestAssembly {

	final static Logger logger = Logger.getLogger(GenerationofAllTFAnnotationsFileInGRCh37p13AndInLatestAssembly.class);

	
	//6 April 2017
	public static void callNCBIREMAPAndGenerateAllTFAnnotationsFileInLatestAssembly( 
			String dataFolder,
			String outputFolder, 
			String all_GivenIntervalsTFOverlapsInGRCh37_p13_FileName,
			Map<String,String> source_1BasedStart_1BasedEnd2TargetMap,
			Map<String,String> source_1BasedStart_1BasedEnd2SourceInformationMap,
			String remapInputFile_0Based_Start_EndExclusive_GRCh37_P13_BED_FILE,
			String latestAssembyNameReturnedByNCBIEutils,
			Map<String, String> assemblyName2RefSeqAssemblyIDMap) {

		String headerLine = Commons.HEADER_LINE_FOR_ALL_TF_ANNOTATIONS_IN_LATEST_ASSEMBLY;

		String forRSA_Folder = outputFolder + Commons.FOR_RSA + System.getProperty( "file.separator");
		String forRSA_REMAP_Folder = outputFolder + Commons.FOR_RSA + System.getProperty( "file.separator") + Commons.NCBI_REMAP + System.getProperty( "file.separator");

		//GLANET internal data is in Commons.GRCH37_P13 coordinates
		String sourceReferenceAssemblyID = assemblyName2RefSeqAssemblyIDMap.get(Commons.GRCH37_P13);
		// In fact targetReferenceAssemblyID must be the assemblyName that NCBI EUTILS returns (groupLabel)
		// args must be augmented with latestNCBIAssemblyName
		// String targetReferenceAssemblyID = "GCF_000001405.26";
		String targetReferenceAssemblyID = assemblyName2RefSeqAssemblyIDMap.get(latestAssembyNameReturnedByNCBIEutils);;
		
	
		String merge = Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON;
		String allowMultipleLocation = Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON;
		double minimumRatioOfBasesThatMustBeRemapped = Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_;
		double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength = Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2;

		String inputFormat = Commons.BED;

		if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");

		Remap.remap(
				dataFolder, 
				sourceReferenceAssemblyID, 
				targetReferenceAssemblyID,
				forRSA_REMAP_Folder + remapInputFile_0Based_Start_EndExclusive_GRCh37_P13_BED_FILE,
				forRSA_REMAP_Folder + Commons.REMAP_DUMMY_OUTPUT_FILE,
				forRSA_REMAP_Folder + Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE,
				forRSA_REMAP_Folder + Commons.REMAP_DUMMY_GENOME_WORKBENCH_PROJECT_FILE, 
				merge, 
				allowMultipleLocation,
				minimumRatioOfBasesThatMustBeRemapped, 
				maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength,
				inputFormat, 
				Commons.REMAP_ALL_TF_ANNOTATIONS_FROM_GRCh37p13_TO_LATEST_ASSEMBLY_RETURNED_BY_NCBIEUTILS_FOR_RSA);

		Remap.fillConversionMap(
				forRSA_REMAP_Folder, 
				Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE,
				source_1BasedStart_1BasedEnd2TargetMap, 
				Commons.TAB);
		
		
		Remap.convertTwoGenomicLociPerLineUsingMap(
				forRSA_Folder,
				all_GivenIntervalsTFOverlapsInGRCh37_p13_FileName,
				Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_LATEST_ASSEMBLY_RETURNED_BY_NCBI_EUTILS,
				source_1BasedStart_1BasedEnd2TargetMap,
				null,
				headerLine);

		//Depreceated
		//Will be deleted
//		Remap.convertTwoGenomicLociPerLineUsingMap( 
//				forRSA_Folder,
//				Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_LATEST_ASSEMBLY_RETURNED_BY_NCBI_EUTILS, 
//				lineNumber2SourceGenomicLociMap,
//				lineNumber2SourceInformationMap, 
//				lineNumber2TargetGenomicLociMap, 
//				headerLine);

		if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");

	}

	
	

	
	//6 April 2017
	public static void generateAllTFAnnotationsFileAndREMAPInputFile( 
			String outputFolder,
			String all_TF_Annotations_1Based_Start_End_GRCh37_p13,
			Map<String,String> source_1BasedStart_1BasedEnd2TargetMap,
			Map<String,String> source_1BasedStart_1BasedEnd2SourceInformationMap,
			String remapInputFile_0Based_Start_EndExclusive_GRCh37_P13) {

		String fileAbsolutePath = null;

		BufferedReader bufferedReader = null;

		BufferedWriter allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter = null;
		BufferedWriter remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter = null;

		String strLine;
		String after;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;

		// snpStart
		int snpStart0Based;
		int snpStart1Based;

		// snpEnd
		int snpEnd0Based;
		int snpEnd1Based;

		// tfStart
		int tfStart0Based;
		int tfStart1Based;

		// tfEnd
		int tfEnd0Based;
		int tfEnd1Based;

		String remapInputFileLine1 = null;
		String remapInputFileLine2 = null;

		String tfAnnotationDirectory = outputFolder + System.getProperty( "file.separator") + Commons.ANNOTATION + System.getProperty( "file.separator") + Commons.TF + System.getProperty( "file.separator");
		String allTFAnnotationsDirectory = outputFolder + System.getProperty( "file.separator") + Commons.FOR_RSA + System.getProperty( "file.separator");

		File baseFolder = new File( tfAnnotationDirectory);

		try{
			allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter( allTFAnnotationsDirectory + all_TF_Annotations_1Based_Start_End_GRCh37_p13));
			remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter( allTFAnnotationsDirectory + Commons.NCBI_REMAP + System.getProperty( "file.separator") + remapInputFile_0Based_Start_EndExclusive_GRCh37_P13));

			if( baseFolder.exists() && baseFolder.isDirectory()){

				File[] files = baseFolder.listFiles();

				allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter.write( "#ChrName	snpStart1Based	snpEnd1Based	ChrName	tfStart1Based	tfEnd1Based	(All TF annotations in 1Based Start and End in GRCh37.p13 coordinates.)" + System.getProperty( "line.separator"));

				for( File tfAnnotationFile : files){

					// fileName = tfAnnotationFile.getName();
					fileAbsolutePath = tfAnnotationFile.getAbsolutePath();

					bufferedReader = new BufferedReader( FileOperations.createFileReader( fileAbsolutePath));

					// Start reading each TF Annotation File which is OBased
					// Start End GRCh37 p13
					while( ( strLine = bufferedReader.readLine()) != null){

						// chr1 11862777 11862777 chr1 11862636 11863019
						// AP2ALPHA HELAS3
						// spp.optimal.wgEncodeSydhTfbsHelas3Ap2alphaStdAlnRep0_VS_wgEncodeSydhTfbsHelas3InputStdAlnRep1.narrowPeak

						if( strLine.charAt( 0) != Commons.GLANET_COMMENT_CHARACTER){

							indexofFirstTab = strLine.indexOf( '\t');
							indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
							indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
							indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
							indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
							indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;

							snpStart0Based = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
							snpStart1Based = snpStart0Based + 1;

							snpEnd0Based = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));
							snpEnd1Based = snpEnd0Based + 1;

							tfStart0Based = Integer.parseInt( strLine.substring( indexofFourthTab + 1, indexofFifthTab));
							tfStart1Based = tfStart0Based + 1;

							tfEnd0Based = Integer.parseInt( strLine.substring( indexofFifthTab + 1, indexofSixthTab));
							tfEnd1Based = tfEnd0Based + 1;

							after = strLine.substring( indexofSixthTab + 1);

							allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter.write( strLine.substring( 0,
									indexofFirstTab) + "\t" + snpStart1Based + "\t" + snpEnd1Based + "\t" + strLine.substring(
									indexofThirdTab + 1, indexofFourthTab) + "\t" + tfStart1Based + "\t" + tfEnd1Based + "\t" + after + System.getProperty( "line.separator"));

							/*** 1st******SNP Genomic Loci Line starts ****************/
							remapInputFileLine1 = strLine.substring( 0, indexofFirstTab) + "\t" + snpStart0Based + "\t" + snpEnd1Based + System.getProperty( "line.separator");

							remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter.write( remapInputFileLine1);

							source_1BasedStart_1BasedEnd2TargetMap.put(strLine.substring(0,indexofFirstTab) + "\t" + snpStart1Based + "\t" + snpEnd1Based, null);
							source_1BasedStart_1BasedEnd2SourceInformationMap.put(strLine.substring(0,indexofFirstTab) + "\t" + snpStart1Based + "\t" + snpEnd1Based, after);

							/*** 1st******SNP Genomic Loci Line ends ******************/

							/*** 2nd******TF Genomic Loci Line starts *****************/
							remapInputFileLine2 = strLine.substring( indexofThirdTab + 1, indexofFourthTab) + "\t" + tfStart0Based + "\t" + tfEnd1Based + System.getProperty( "line.separator");

							remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter.write( remapInputFileLine2);

							source_1BasedStart_1BasedEnd2TargetMap.put(strLine.substring(indexofThirdTab+1,indexofFourthTab) + "\t" + tfStart1Based + "\t" + tfEnd1Based,null);
							source_1BasedStart_1BasedEnd2SourceInformationMap.put(strLine.substring(indexofThirdTab + 1, indexofFourthTab) + "\t" + tfStart1Based + "\t" + tfEnd1Based, after);

							/*** 2nd******TF Genomic Loci Line ends *******************/

						}// End of IF strLine is not Comment Line

					}// End of While loop: reading each TF Annotation File

					// Close each TF File
					bufferedReader.close();

				}// End of For loop: each TF Annotation file

			}// TF Annotation Directory

			// Close AllTFAnnotationsFile And RemapInputFile
			allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter.close();
			remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter.close();

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
	}

	
	
	//Will be deleted 
	/*
	 * By reading each TF Annotation File Generate All TF Annotations File Then
	 * Generate REMAP InputFile 
	 * During these fill lineNumber2SourceGenomicLociMap and lineNumber2SourceInformationMap
	 */
	public static void generateAllTFAnnotationsFileAndREMAPInputFile( 
			String outputFolder,
			String all_TF_Annotations_1Based_Start_End_GRCh37_p13,
			TIntObjectMap<String> lineNumber2SourceGenomicLociMap,
			TIntObjectMap<String> lineNumber2SourceInformationMap,
			String remapInputFile_0Based_Start_EndExclusive_GRCh37_P13) {

		String fileAbsolutePath = null;

		BufferedReader bufferedReader = null;

		BufferedWriter allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter = null;
		BufferedWriter remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter = null;

		String strLine;
		String after;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;

		// snpStart
		int snpStart0Based;
		int snpStart1Based;

		// snpEnd
		int snpEnd0Based;
		int snpEnd1Based;

		// tfStart
		int tfStart0Based;
		int tfStart1Based;

		// tfEnd
		int tfEnd0Based;
		int tfEnd1Based;

		int numberofLocisInRemapInputFile = 1;
		String remapInputFileLine1 = null;
		String remapInputFileLine2 = null;

		String tfAnnotationDirectory = outputFolder + System.getProperty( "file.separator") + Commons.ANNOTATION + System.getProperty( "file.separator") + Commons.TF + System.getProperty( "file.separator");
		String allTFAnnotationsDirectory = outputFolder + System.getProperty( "file.separator") + Commons.FOR_RSA + System.getProperty( "file.separator");

		File baseFolder = new File( tfAnnotationDirectory);

		try{
			allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter( allTFAnnotationsDirectory + all_TF_Annotations_1Based_Start_End_GRCh37_p13));
			remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter( allTFAnnotationsDirectory + Commons.NCBI_REMAP + System.getProperty( "file.separator") + remapInputFile_0Based_Start_EndExclusive_GRCh37_P13));

			if( baseFolder.exists() && baseFolder.isDirectory()){

				File[] files = baseFolder.listFiles();

				allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter.write( "#ChrName	snpStart1Based	snpEnd1Based	ChrName	tfStart1Based	tfEnd1Based	(All TF annotations in 1Based Start and End in GRCh37.p13 coordinates.)" + System.getProperty( "line.separator"));

				for( File tfAnnotationFile : files){

					// fileName = tfAnnotationFile.getName();
					fileAbsolutePath = tfAnnotationFile.getAbsolutePath();

					bufferedReader = new BufferedReader( FileOperations.createFileReader( fileAbsolutePath));

					// Start reading each TF Annotation File which is OBased
					// Start End GRCh37 p13
					while( ( strLine = bufferedReader.readLine()) != null){

						// chr1 11862777 11862777 chr1 11862636 11863019
						// AP2ALPHA HELAS3
						// spp.optimal.wgEncodeSydhTfbsHelas3Ap2alphaStdAlnRep0_VS_wgEncodeSydhTfbsHelas3InputStdAlnRep1.narrowPeak

						if( strLine.charAt( 0) != Commons.GLANET_COMMENT_CHARACTER){

							indexofFirstTab = strLine.indexOf( '\t');
							indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
							indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
							indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
							indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
							indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;

							snpStart0Based = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
							snpStart1Based = snpStart0Based + 1;

							snpEnd0Based = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));
							snpEnd1Based = snpEnd0Based + 1;

							tfStart0Based = Integer.parseInt( strLine.substring( indexofFourthTab + 1, indexofFifthTab));
							tfStart1Based = tfStart0Based + 1;

							tfEnd0Based = Integer.parseInt( strLine.substring( indexofFifthTab + 1, indexofSixthTab));
							tfEnd1Based = tfEnd0Based + 1;

							after = strLine.substring( indexofSixthTab + 1);

							allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter.write( strLine.substring( 0,
									indexofFirstTab) + "\t" + snpStart1Based + "\t" + snpEnd1Based + "\t" + strLine.substring(
									indexofThirdTab + 1, indexofFourthTab) + "\t" + tfStart1Based + "\t" + tfEnd1Based + "\t" + after + System.getProperty( "line.separator"));

							/*** 1st******SNP Genomic Loci Line starts ****************/
							remapInputFileLine1 = strLine.substring( 0, indexofFirstTab) + "\t" + snpStart0Based + "\t" + snpEnd1Based + System.getProperty( "line.separator");

							remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter.write( remapInputFileLine1);

							lineNumber2SourceGenomicLociMap.put(numberofLocisInRemapInputFile,strLine.substring( 0, indexofFirstTab) + "\t" + snpStart1Based + "\t" + snpEnd1Based);
							lineNumber2SourceInformationMap.put(numberofLocisInRemapInputFile, after);

							numberofLocisInRemapInputFile++;
							/*** 1st******SNP Genomic Loci Line ends ******************/

							/*** 2nd******TF Genomic Loci Line starts *****************/
							remapInputFileLine2 = strLine.substring( indexofThirdTab + 1, indexofFourthTab) + "\t" + tfStart0Based + "\t" + tfEnd1Based + System.getProperty( "line.separator");

							remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter.write( remapInputFileLine2);

							lineNumber2SourceGenomicLociMap.put(
									numberofLocisInRemapInputFile,
									strLine.substring( indexofThirdTab + 1, indexofFourthTab) + "\t" + tfStart1Based + "\t" + tfEnd1Based);
							lineNumber2SourceInformationMap.put( numberofLocisInRemapInputFile, after);

							numberofLocisInRemapInputFile++;
							/*** 2nd******TF Genomic Loci Line ends *******************/

						}// End of IF strLine is not Comment Line

					}// End of While loop: reading each TF Annotation File

					// Close each TF File
					bufferedReader.close();

				}// End of For loop: each TF Annotation file

			}// TF Annotation Directory

			// Close AllTFAnnotationsFile And RemapInputFile
			allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter.close();
			remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter.close();

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
	}

	public static void main( String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];

		// jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if( jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		// jobName ends

		String outputFolder = args[CommandLineArguments.OutputFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");

		// Before each run
		// delete directories and files under base directories
		// delete old files starts
		FileOperations.deleteOldFiles( outputFolder + Commons.REGULATORY_SEQUENCE_ANALYSIS_DIRECTORY);
		// delete old files ends

		TIntObjectMap<String> lineNumber2SourceGenomicLociMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> lineNumber2SourceInformationMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> lineNumber2TargetGenomicLociMap = new TIntObjectHashMap<String>();
		
		//6 April 2017
		Map<String,String> source_1BasedStart_1BasedEnd2TargetMap = new HashMap<String,String>();
		Map<String,String> source_1BasedStart_1BasedEnd2SourceInformationMap = new HashMap<String,String>();
		
		//Depreceated
		//Will be deleted
//		generateAllTFAnnotationsFileAndREMAPInputFile(
//				outputFolder,
//				Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCh37_P13, 
//				lineNumber2SourceGenomicLociMap,
//				lineNumber2SourceInformationMap,
//				Commons.REMAP_INPUT_FILE_All_TF_ANNOTATIONS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES_BED_FILE);
		
		//TODO
		generateAllTFAnnotationsFileAndREMAPInputFile(
				outputFolder,
				Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCh37_P13, 
				source_1BasedStart_1BasedEnd2TargetMap,
				source_1BasedStart_1BasedEnd2SourceInformationMap,
				Commons.REMAP_INPUT_FILE_All_TF_ANNOTATIONS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES_BED_FILE);
		

		
		//Means that there are no source genomic loci to be remapped to target genomic loci.
		if (!source_1BasedStart_1BasedEnd2TargetMap.isEmpty()){
			
			/***************************************************************************************/
			/***************************************************************************************/
			/***************************************************************************************/
			String latestAssembyNameReturnedByNCBIEutils = NCBIEutils.getLatestAssemblyNameReturnedByNCBIEutils();
			/***************************************************************************************/
			/***************************************************************************************/
			/***************************************************************************************/

			//example for debug
			//String latestAssembyNameReturnedByNCBIEutils = "GRCh38.p2";
			
			/***************************************************************************************/
			/***************************************************************************************/
			/***************************************************************************************/
			Map<String, String> assemblyName2RefSeqAssemblyIDMap = new HashMap<String, String>();
			
			Remap.remap_show_batches(dataFolder, Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);
			
			Remap.fillAssemblyName2RefSeqAssemblyIDMap(
					dataFolder, 
					Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE,
					assemblyName2RefSeqAssemblyIDMap);
			/***************************************************************************************/
			/***************************************************************************************/
			/***************************************************************************************/

			
			//TODO
			//6 April 2017
			callNCBIREMAPAndGenerateAllTFAnnotationsFileInLatestAssembly(
					dataFolder, 
					outputFolder,
					Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCh37_P13,
					source_1BasedStart_1BasedEnd2TargetMap,
					source_1BasedStart_1BasedEnd2SourceInformationMap,
					Commons.REMAP_INPUT_FILE_All_TF_ANNOTATIONS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES_BED_FILE,
					latestAssembyNameReturnedByNCBIEutils,
					assemblyName2RefSeqAssemblyIDMap);

			

			//Depreceated
			//Will be deleted
//			callNCBIREMAPAndGenerateAllTFAnnotationsFileInLatestAssembly(
//					dataFolder, 
//					outputFolder,
//					lineNumber2SourceGenomicLociMap, 
//					lineNumber2SourceInformationMap, 
//					lineNumber2TargetGenomicLociMap,
//					Commons.REMAP_INPUT_FILE_All_TF_ANNOTATIONS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES_BED_FILE,
//					latestAssembyNameReturnedByNCBIEutils,
//					assemblyName2RefSeqAssemblyIDMap);
			
		}//End of IF
		
		
	}

}
