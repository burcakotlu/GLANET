/**
 * 
 */
package remap;

import enumtypes.AssemblySource;
import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ui.GlanetRunner;
import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Burcak Otlu
 * @date Nov 25, 2014
 * @project Glanet
 *
 */
public class Remap {

	final static Logger logger = Logger.getLogger(Remap.class);
	
	public static void generateREMAPInputFile(
			String forRSAFolder,
			Map<String,String> snpKey_chrName_1BasedStart_1BasedEnd_2_ObservedAllelesMap, 
			Map<String,String> snpKey_chrName_1BasedStart_1BasedEnd_2_TargetMap,
			String remapInputFile_0Based_Start_EndExclusive_GRCh37_P13){
		
		
		BufferedWriter remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter = null;

		String chrName = null;
		
		int indexofFirstUnderscore;
		int indexofSecondUnderscore;
		
		// snpStart
		int snp_1BasedStart;
		int snp_0BasedStart;
		
		// snpEnd
		int snp_1BasedEnd;
		
		String snpKey = null;
		//snpKey = chrName + "_" + _1BasedStart + "_" + _1BasedEnd;
		
		try{
				remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter(forRSAFolder + Commons.NCBI_REMAP + System.getProperty( "file.separator") + remapInputFile_0Based_Start_EndExclusive_GRCh37_P13));

			
				for(Map.Entry<String,String> entry: snpKey_chrName_1BasedStart_1BasedEnd_2_ObservedAllelesMap.entrySet()){
					
					snpKey = entry.getKey();
					
					indexofFirstUnderscore = snpKey.indexOf('_');
					indexofSecondUnderscore = snpKey.indexOf('_',indexofFirstUnderscore+1);
					
					chrName = snpKey.substring(0, indexofFirstUnderscore);
				
					snp_1BasedStart = Integer.parseInt(snpKey.substring(indexofFirstUnderscore + 1, indexofSecondUnderscore));
					snp_0BasedStart = snp_1BasedStart - 1;
					
					snp_1BasedEnd = Integer.parseInt(snpKey.substring(indexofSecondUnderscore + 1));
														
							
					/*********SNP Genomic Loci Line starts ****************/
					//No duplicates
					if (!snpKey_chrName_1BasedStart_1BasedEnd_2_TargetMap.containsKey(snpKey)){
						snpKey_chrName_1BasedStart_1BasedEnd_2_TargetMap.put(snpKey,null);
						
						//Write to Remap Input File
						remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter.write(chrName + "\t" + snp_0BasedStart + "\t" + snp_1BasedEnd + System.getProperty( "line.separator"));
					}
					/*********SNP Genomic Loci Line ends ******************/

				}//End of for each snp
					
							
			// Close AllTFAnnotationsFile And RemapInputFile
			remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter.close();

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
		
	}
	
	public static void fillAssemblyName2RefSeqAssemblyIDMap(
			String dataFolder, 
			String supportedAssembliesFileName,
			Map<String, String> assemblyName2RefSeqAssemblyIDMap) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;

		String species;
		String sourceAssemblyName;
		String sourceRefSeqAssemblyId;
		String targetAssemblyName;
		String targetRefSeqAssemblyId;

		try{
			fileReader = FileOperations.createFileReader(
					dataFolder + Commons.NCBI_REMAP + System.getProperty( "file.separator"),
					supportedAssembliesFileName);
			bufferedReader = new BufferedReader( fileReader);

			while( ( strLine = bufferedReader.readLine()) != null){

				if( !strLine.startsWith( "#")){
					// Example line
					// #batch_id query_species query_name query_ucsc query_acc
					// target_species target_name target_ucsc target_acc
					// alignment_date
					// 85213 Homo sapiens NCBI33 GCF_000001405.8 Homo sapiens
					// GRCh38 hg38 GCF_000001405.26 09/20/2014
					// 85273 Homo sapiens NCBI36 hg18 GCF_000001405.12 Homo
					// sapiens GRCh38 hg38 GCF_000001405.26 09/20/2014
					// 85283 Homo sapiens GRCh37.p11 GCF_000001405.23 Homo
					// sapiens GRCh38 hg38 GCF_000001405.26 09/20/2014

					indexofFirstTab = strLine.indexOf( '\t');
					indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
					indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
					indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
					indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
					indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;
					indexofSeventhTab = ( indexofSixthTab > 0)?strLine.indexOf( '\t', indexofSixthTab + 1):-1;
					indexofEigthTab = ( indexofSeventhTab > 0)?strLine.indexOf( '\t', indexofSeventhTab + 1):-1;
					indexofNinethTab = ( indexofEigthTab > 0)?strLine.indexOf( '\t', indexofEigthTab + 1):-1;

					if( indexofFirstTab > 0 && indexofSecondTab > 0 && indexofThirdTab > 0 && indexofFourthTab > 0 && indexofFifthTab > 0 && indexofSixthTab > 0 && indexofSeventhTab > 0 && indexofEigthTab > 0 && indexofNinethTab > 0){

						species = strLine.substring( indexofFirstTab + 1, indexofSecondTab);

						if( species.equals(Commons.NCBI_REMAP_HOMO_SAPIENS)){
							sourceAssemblyName = strLine.substring( indexofSecondTab + 1, indexofThirdTab);
							sourceRefSeqAssemblyId = strLine.substring( indexofFourthTab + 1, indexofFifthTab);

							if( !assemblyName2RefSeqAssemblyIDMap.containsKey( sourceAssemblyName)){
								assemblyName2RefSeqAssemblyIDMap.put( sourceAssemblyName, sourceRefSeqAssemblyId);
							}

							targetAssemblyName = strLine.substring( indexofSixthTab + 1, indexofSeventhTab);
							targetRefSeqAssemblyId = strLine.substring( indexofEigthTab + 1, indexofNinethTab);

							if( !assemblyName2RefSeqAssemblyIDMap.containsKey( targetAssemblyName)){
								assemblyName2RefSeqAssemblyIDMap.put( targetAssemblyName, targetRefSeqAssemblyId);
							}
						}// End of if species is HOMO SAPIENS

					}// End of if necessary indexofTabs are nonzero

				}// End of if not a comment line

			}// End of while

			// close
			bufferedReader.close();

		}catch( IOException e){

			if( GlanetRunner.shouldLog()) logger.error( e.toString());
		}

	}

	
	
	
	//25 November 2016 starts
	//Customized for Hacettepe Collaboration LGMD data
	// read inputFileName
	// write Commons.REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE
	public static void readInputFileFillMapWriteRemapInputFile(
			String givenInputDataFolder,
			String inputFile0BasedStartEnd, 
			TIntObjectMap<String> lineNumber2SourceGenomicLociMap,
			TIntObjectMap<String> lineNumber2SourceInformationMap,
			String remapInputFile0BasedStartEndExclusive) {

		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;

		String strLine = null;
		int indexoFirstTab;
		int indexofSecondTab;
		
		int start1Based;

		int start0Based;
		int end0Based;
		int end0BasedExclusive;
		
		int lineNumber = 1;
		
		
		//example lines
//		chr10	52596265	rs141343052
//		chr10	38894488	-
//		chr10	38894494	rs74223011

		try{
			bufferedReader = new BufferedReader(FileOperations.createFileReader(givenInputDataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator") + inputFile0BasedStartEnd));
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(givenInputDataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator") + remapInputFile0BasedStartEndExclusive));

			while( ( strLine = bufferedReader.readLine()) != null){

				indexoFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexoFirstTab > 0)?strLine.indexOf( '\t', indexoFirstTab + 1):-1;
				
				if (indexofSecondTab>=0){
					start1Based = Integer.parseInt(strLine.substring(indexoFirstTab+1,indexofSecondTab));					
				}else{
					start1Based = Integer.parseInt(strLine.substring(indexoFirstTab+1));					
				}

				
				start0Based = start1Based-1;
				end0Based = start0Based;
				end0BasedExclusive = end0Based + 1;

				bufferedWriter.write(strLine.substring(0,indexoFirstTab) + "\t" + start0Based + "\t" + end0BasedExclusive + System.getProperty( "line.separator"));
				
				lineNumber2SourceGenomicLociMap.put(lineNumber,strLine.substring( 0, indexoFirstTab) + "\t" + start0Based + "\t" + end0BasedExclusive);
				lineNumber2SourceInformationMap.put(lineNumber, strLine.substring(indexofSecondTab+1));
				
				lineNumber++;
			}// End of WHILE

			// Close
			bufferedReader.close();
			bufferedWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//25 November 2016 ends
	
	//25 November 2016 starts
	//Customized for Hacettepe Collaboration
	public static void readRemapOutputFileWriteProcessedInputFile(
			String givenInputDataFolder,
			String remapOutputFile1BasedStartEndInGRCh37p13,
			String processedFile1BasedStartEndInTargetAssembly,
			Boolean writeInformationInOutputFile) {

		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;

		String strLine = null;
		int indexoFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;

		int start1Based;
		String rsID = null;
		
		try{
			bufferedReader = new BufferedReader(FileOperations.createFileReader(givenInputDataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator") + remapOutputFile1BasedStartEndInGRCh37p13));
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(givenInputDataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator") + processedFile1BasedStartEndInTargetAssembly));

			//Skip header line
			strLine = bufferedReader.readLine();
			
			while((strLine = bufferedReader.readLine()) != null){

				if( strLine.charAt(0) != Commons.GLANET_COMMENT_CHARACTER){
					indexoFirstTab = strLine.indexOf('\t');
					indexofSecondTab = (indexoFirstTab > 0)?strLine.indexOf( '\t', indexoFirstTab + 1):-1;
					indexofThirdTab = (indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
					
					start1Based = Integer.parseInt(strLine.substring(indexoFirstTab + 1, indexofSecondTab));					
					rsID = strLine.substring(indexofThirdTab+1);
					
					if (writeInformationInOutputFile){
						bufferedWriter.write(strLine.substring(0,indexoFirstTab) + "\t" + start1Based +  "\t"  + rsID + System.getProperty( "line.separator"));	
					}else{
						bufferedWriter.write(strLine.substring(0,indexoFirstTab) + "\t" + start1Based + System.getProperty( "line.separator"));
					}
				
				}

			}// End of WHILE

			// Close
			bufferedReader.close();
			bufferedWriter.close();

		}catch( IOException e){
			e.printStackTrace();
		}

	}
	//25 November 2016 ends
	
	//25 November 2016 starts
	//Written for Hacettepe Collaboration LGMD
	public static void convertGivenInputCoordinatesFromSourceAssemblytoTargetAssemblyUsingRemap(
			String dataFolder, 	
			String inputOutputDataFolder,
			String inputFile, 
			String outputFile, 
			String sourceAssemblyName, 
			String targetAssemblyName,
			Boolean writeInformationInOutputFile,
			Boolean fillMap,
			Map<String,String> sourceAssemblyCoordinates2TargetAssemblyCoordinatesMap) {
		
		String remapFolder = inputOutputDataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator");

		/**********************************************************/
		/********** NCBI REMAP PARAMETERS starts ******************/
		/**********************************************************/
		
		String sourceReferenceAssemblyID = null;
		String targetReferenceAssemblyID = null;

		Remap.remap_show_batches(dataFolder, Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);

		Map<String,String> assemblyName2RefSeqAssemblyIDMap = new HashMap<String, String>();
		
		Remap.fillAssemblyName2RefSeqAssemblyIDMap(
				dataFolder, 
				Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE,
				assemblyName2RefSeqAssemblyIDMap);

		// sourceReferenceAssemblyID = "GCF_000001405.26";
		sourceReferenceAssemblyID = assemblyName2RefSeqAssemblyIDMap.get(sourceAssemblyName);

		// targetReferenceAssemblyID = "GCF_000001405.25";
		targetReferenceAssemblyID = assemblyName2RefSeqAssemblyIDMap.get(targetAssemblyName);

		String merge = null;
		String allowMultipleLocation = null;
		double minimumRatioOfBasesThatMustBeRemapped;
		double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength;

		String inputFormat = Commons.BED;

		merge = Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON;
		allowMultipleLocation = Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON;
		minimumRatioOfBasesThatMustBeRemapped = Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_;
		maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength = Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2;
		/**********************************************************/
		/********** NCBI REMAP PARAMETERS ends ********************/
		/**********************************************************/

		String headerLine = "#Given Input Data Coordinates in 1Based Start End in Latest Assembly:" + targetAssemblyName;

		TIntObjectMap<String> lineNumber2SourceGenomicLociMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> lineNumber2SourceInformationMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> lineNumber2TargetGenomicLociMap = new TIntObjectHashMap<String>();

		// read inputFileName
		// write Commons.REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE
		readInputFileFillMapWriteRemapInputFile(
				inputOutputDataFolder, 
				inputFile, 
				lineNumber2SourceGenomicLociMap,
				lineNumber2SourceInformationMap,
				Commons.REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE);

		
		//sourceReferenceAssemblyID= "GCF_000001405.25"; for GRch37.p13
		//targetReferenceAssemblyID= "GCF_000001405.33"; for GRch38.p7
		
		Remap.remap(
				dataFolder,
				sourceReferenceAssemblyID,
				targetReferenceAssemblyID,
				remapFolder + Commons.REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE,
				remapFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
				remapFolder + Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE,
				remapFolder + Commons.REMAP_DUMMY_GENOME_WORKBENCH_PROJECT_FILE, 
				merge, 
				allowMultipleLocation,
				minimumRatioOfBasesThatMustBeRemapped, 
				maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength,
				inputFormat, 
				Commons.REMAP_GIVENINPUTDATA_FROM_GRCH38_TO_GRCH37P13);

		Remap.fillConversionMap(
				remapFolder, 
				Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE,
				lineNumber2SourceGenomicLociMap, 
				lineNumber2TargetGenomicLociMap);

		Remap.convertOneGenomicLociPerLineUsingMapCustomizedForLHMD(
				remapFolder,
				Commons.REMAP_OUTPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_1Based_START_END_BED_FILE_USING_REMAP_REPORT,
				lineNumber2SourceGenomicLociMap, 
				lineNumber2SourceInformationMap, 
				lineNumber2TargetGenomicLociMap,
				headerLine,
				fillMap,
				sourceAssemblyCoordinates2TargetAssemblyCoordinatesMap);
		
		// read remap outputfile
		// write processed file in 1Based start end in target assembly
		readRemapOutputFileWriteProcessedInputFile(
				inputOutputDataFolder,
				Commons.REMAP_OUTPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_1Based_START_END_BED_FILE_USING_REMAP_REPORT,
				outputFile,
				writeInformationInOutputFile);

	}
	//25 November 2016 ends
	
	
	//12 DEC 2016
	public static List<String> getSupportedAssemblies(String dataFolder,String supportedAssembliesFileName){
		
		
		BufferedReader bufferedReader = null;
		String line;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
		String querySpecie = null;
		String targetSpecie = null;

		String queryAssembly = null;
		String targetAssembly = null;
		
		List<String> supportedAssemblies = new ArrayList<String>();

		try{
			

			// Output of the perl execution is here
			bufferedReader = new BufferedReader(FileOperations.createFileReader(dataFolder + Commons.NCBI_REMAP + System.getProperty( "file.separator") + supportedAssembliesFileName));

			
			//Skip header line
			line = bufferedReader.readLine();
			

			//Since GLANET data is "GRCh37.p13"
			supportedAssemblies.add("GRCh37.p13");

			
			while( ( line = bufferedReader.readLine()) != null){
				//#batch_id	query_species	query_name	query_ucsc	query_acc	target_species	target_name	target_ucsc	target_acc	alignment_date
				//13311	Homo sapiens	NCBI34	hg16	GCF_000001405.10	Homo sapiens	GRCh38.p1		GCF_000001405.27	12/15/2014

				indexofFirstTab = line.indexOf('\t');
				indexofSecondTab = line.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab = line.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab = line.indexOf('\t', indexofThirdTab+1);
				indexofFifthTab = line.indexOf('\t', indexofFourthTab+1);
				indexofSixthTab = line.indexOf('\t', indexofFifthTab+1);
				indexofSeventhTab = line.indexOf('\t', indexofSixthTab+1);
				
				querySpecie = line.substring(indexofFirstTab+1, indexofSecondTab);
				targetSpecie= line.substring(indexofFifthTab+1, indexofSixthTab);
				
				queryAssembly =  line.substring(indexofSecondTab+1, indexofThirdTab);
				targetAssembly = line.substring(indexofSixthTab+1, indexofSeventhTab);
				
				if (querySpecie.equalsIgnoreCase("Homo sapiens") && targetSpecie.equalsIgnoreCase("Homo sapiens") && queryAssembly.startsWith("GRCh") && targetAssembly.equalsIgnoreCase("GRCh37.p13")){
					
					supportedAssemblies.add(queryAssembly);
				}
				
			}// End of while
			
			// Close
			bufferedReader.close();
			
	
		}catch( IOException e){

		}
		
		return supportedAssemblies;
	}
	
	
	//Fill NCBI_REMAP_Supported_Assemblies
	public static void remap_show_batches( 
			String dataFolder, 
			String supportedAssembliesFileName) {

		String remapFile = dataFolder + Commons.NCBI_REMAP + System.getProperty( "file.separator") + "remap_api.pl";
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		BufferedWriter bufferedWriter = null;
		BufferedReader bufferedReader = null;
		String line;

		try{
			
			process = runtime.exec( new String[]{"perl", remapFile, "--mode", "batches"});
			
			// System.out.println("perl " + remapFile + "--mode batches");

			// here we use Thread.sleep instead of waitFor() because
			// process's input stream is not immediately written
			// just after the process finishes. Connecting to the server
			// and getting the results may take longer. Therefore,
			// it is not enough just to wait for the end of the process.
			// one should also wait for getting the results from the server
			// properly

			// nearly 40-50 trial on that. Worst case is about 5-6 secs.
			// For a very bad network connection, it should not be more
			// than 10 secs. It is not suggested it you decrease sleep
			// amount. It might cause that the file won't be written
			try{
				//process.waitFor();

				Thread.sleep(10000);
			}catch( InterruptedException ex){
				Thread.currentThread().interrupt();
			}

			// Output of the perl execution is here
			bufferedReader = new BufferedReader( new InputStreamReader( process.getInputStream()));
			
			//Read the first line
			line = bufferedReader.readLine();
			
			//If it is a valid file overwrite existing file
			//Otherwise use the old valid existing file
			//Don't overwrite it with invalid file content
			if (line!=null && line.startsWith("#batch") ){
				
				bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(dataFolder + Commons.NCBI_REMAP + System.getProperty( "file.separator") + supportedAssembliesFileName));
				bufferedWriter.write(line + System.getProperty( "line.separator"));
				
				while( ( line = bufferedReader.readLine()) != null){
					bufferedWriter.write( line + System.getProperty( "line.separator"));
				}// End of while
				
				bufferedWriter.close();
			}

			

			// Close
			bufferedReader.close();

			if( GlanetRunner.shouldLog())
				logger.info("NCBI REMAP Show Batches Exit status = " + process.exitValue());
			
			if(process != null) {
		        process.destroy();
		    }
			
			runtime.gc();
			
		}catch( IOException e){

			if( GlanetRunner.shouldLog())
				logger.info( "NCBI REMAP Show Batches Exception = " + "\t" + "Exception toString():  " + e.toString());
		}
	}

	public static void createOutputFileUsingREMAPREPORTFile(
			Map<Integer, String> remapInputFileLineNumber2LineContentMap, 
			String remapReportFile, 
			String outputFile) {

		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;

		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;
		int indexofTwelfthTab;
		int indexofThirteenthTab;
		int indexofFourteenthTab;
		int indexofFifteenthTab;
		int indexofSixteenthTab;
		int indexofSeventeenthTab;

		int indexofUnderscore;

		String lineNumberString;
		int lineNumber = Integer.MIN_VALUE;

		ChromosomeName sourceChrName;
		ChromosomeName mappedChrName;

		int sourceInt;
		String mappedIntString;
		int mappedInt;

		int mappedStart;
		int mappedEnd;
		AssemblySource mappedAssembly;

		TIntObjectMap<Remapped> sourceLineNumber2RemappedMap = new TIntObjectHashMap<Remapped>();

		int maximumLineNumber = Integer.MIN_VALUE;
		Remapped remapped = null;

		int numberofUnConvertedGenomicLociInPrimaryAssembly = 0;
		int numberofConvertedGenomicLociInPrimaryAssembly = 0;

		try{

			File file = new File( remapReportFile);

			if( file.exists()){

				bufferedReader = new BufferedReader( FileOperations.createFileReader( remapReportFile));
				bufferedWriter = new BufferedWriter( FileOperations.createFileWriter( outputFile));

				while( ( strLine = bufferedReader.readLine()) != null){

					// #feat_name source_int mapped_int source_id mapped_id
					// source_length mapped_length source_start source_stop
					// source_strand source_sub_start source_sub_stop
					// mapped_start mapped_stop mapped_strand coverage recip
					// asm_unit
					// line_67 1 1 chr1 chr1 1 1 147664654 147664654 + 147664654
					// 147664654 147136772 147136772 + 1 Second Pass Primary
					// Assembly
					// line_67 1 2 chr1 NW_003871055.3 1 1 147664654 147664654 +
					// 147664654 147664654 4480067 4480067 + 1 First Pass
					// PATCHES
					// line_122 1 NULL chr14 NULL 1 NULL 93095256 93095256 +
					// NOMAP NOALIGN

					if( !strLine.startsWith( "#")){

						indexofFirstTab = strLine.indexOf( '\t');
						indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
						indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
						indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
						indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
						indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;
						indexofSeventhTab = ( indexofSixthTab > 0)?strLine.indexOf( '\t', indexofSixthTab + 1):-1;
						indexofEigthTab = ( indexofSeventhTab > 0)?strLine.indexOf( '\t', indexofSeventhTab + 1):-1;
						indexofNinethTab = ( indexofEigthTab > 0)?strLine.indexOf( '\t', indexofEigthTab + 1):-1;
						indexofTenthTab = ( indexofNinethTab > 0)?strLine.indexOf( '\t', indexofNinethTab + 1):-1;
						indexofEleventhTab = ( indexofTenthTab > 0)?strLine.indexOf( '\t', indexofTenthTab + 1):-1;
						indexofTwelfthTab = ( indexofEleventhTab > 0)?strLine.indexOf( '\t', indexofEleventhTab + 1):-1;
						indexofThirteenthTab = ( indexofTwelfthTab > 0)?strLine.indexOf( '\t', indexofTwelfthTab + 1):-1;
						indexofFourteenthTab = ( indexofThirteenthTab > 0)?strLine.indexOf( '\t',
								indexofThirteenthTab + 1):-1;
						indexofFifteenthTab = ( indexofFourteenthTab > 0)?strLine.indexOf( '\t',
								indexofFourteenthTab + 1):-1;
						indexofSixteenthTab = ( indexofFifteenthTab > 0)?strLine.indexOf( '\t', indexofFifteenthTab + 1):-1;
						indexofSeventeenthTab = ( indexofSixteenthTab > 0)?strLine.indexOf( '\t',
								indexofSixteenthTab + 1):-1;

						lineNumberString = strLine.substring( 0, indexofFirstTab);

						indexofUnderscore = lineNumberString.indexOf( '_');
						lineNumber = Integer.parseInt( lineNumberString.substring( indexofUnderscore + 1));

						sourceInt = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

						mappedIntString = strLine.substring( indexofSecondTab + 1, indexofThirdTab);

						if( !mappedIntString.equals( Commons.NULL)){

							mappedInt = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

							sourceChrName = ChromosomeName.convertStringtoEnum( strLine.substring( indexofThirdTab + 1,
									indexofFourthTab));
							mappedChrName = ChromosomeName.convertStringtoEnum( strLine.substring(
									indexofFourthTab + 1, indexofFifthTab));

							mappedStart = Integer.parseInt( strLine.substring( indexofTwelfthTab + 1,
									indexofThirteenthTab));
							mappedEnd = Integer.parseInt( strLine.substring( indexofThirteenthTab + 1,
									indexofFourteenthTab));

							mappedAssembly = AssemblySource.convertStringtoEnum( strLine.substring( indexofSeventeenthTab + 1));

							if( ( sourceInt == 1) && ( mappedInt == 1) && sourceChrName == mappedChrName && mappedAssembly.isPrimaryAssembly()){

								remapped = new Remapped( mappedInt, mappedStart, mappedEnd, mappedChrName,
										mappedAssembly);
								sourceLineNumber2RemappedMap.put( lineNumber, remapped);

							}// End of IF: Valid conversion
						}// End of IF mappedInt is not NULL

					}// End of IF: Not Header or Comment Line

				}// End of while

				// Set maximum line number to the last lineNumber
				maximumLineNumber = lineNumber;

				if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");
				if( GlanetRunner.shouldLog())logger.info( "Number of given genomic loci before NCBI REMAP: " + maximumLineNumber);

				// Write to the file
				for( int i = 1; i <= maximumLineNumber; i++){

					remapped = sourceLineNumber2RemappedMap.get( i);

					if( remapped != null){
						bufferedWriter.write( remapped.getMappedChrName().convertEnumtoString() + "\t" + remapped.getMappedStart() + "\t" + remapped.getMappedEnd() + System.getProperty( "line.separator"));
						numberofConvertedGenomicLociInPrimaryAssembly++;
					}else{
						bufferedWriter.write( Commons.NULL + "\t" + Commons.NULL + "\t" + Commons.NULL + System.getProperty( "line.separator"));
						numberofUnConvertedGenomicLociInPrimaryAssembly++;
						if( GlanetRunner.shouldLog())logger.warn( "We have not converted this genomic loci in latest assembly to hg19 using NCBI REMAP: " + remapInputFileLineNumber2LineContentMap.get( i));
					}

				}// End of for

				if( GlanetRunner.shouldLog())logger.info( "Number of converted genomic loci after NCBI REMAP: " + numberofConvertedGenomicLociInPrimaryAssembly);
				if( GlanetRunner.shouldLog())logger.info( "We have lost " + numberofUnConvertedGenomicLociInPrimaryAssembly + " genomic loci during NCBI REMAP");
				if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");

				// close
				bufferedReader.close();
				bufferedWriter.close();

			}// End of if remapReportFile exists

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	/*
	 * Pay attention Previous Report File must not be opened in Excel Otherwise
	 * New Report File can not be updated
	 */
	public static void remap(
			String dataFolder, 
			String sourceAssembly, 
			String targetAssembly, 
			String sourceFileName,
			String outputFileName, 
			String reportFileName, 
			String genomeWorkbenchProjectFile, 
			String merge,
			String allowMultipleLocation, 
			double minimumRatioOfBasesThatMustBeRemapped,
			double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength, 
			String inputFormat, 
			String information) {

		String remapFile = dataFolder + Commons.NCBI_REMAP + System.getProperty( "file.separator") + "remap_api.pl";

		// DirectoryName can contain empty spaces
		Runtime runtime = Runtime.getRuntime();
		Process process = null;

		try{

			String[] command = new String[]{"perl", remapFile, "--mode", "asm-asm", "--from", sourceAssembly, "--dest",
					targetAssembly, "--annotation", sourceFileName, "--annot_out", outputFileName, "--report_out",
					reportFileName, "--gbench_out", genomeWorkbenchProjectFile, "--merge", merge, "--allowdupes",
					allowMultipleLocation, "--mincov ", new Double( minimumRatioOfBasesThatMustBeRemapped).toString(),
					"--maxexp ", new Double( maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength).toString(),
					"--in_format", inputFormat};
			process = runtime.exec(command);

			process.waitFor();

			if( GlanetRunner.shouldLog())logger.info("NCBI REMAP Exit status = " + process.exitValue() + "\t" + information);
			
			if(process != null) {
		        process.destroy();
		    }
			
			runtime.gc();

		}catch( InterruptedException e){

			if( GlanetRunner.shouldLog())logger.info( "NCBI REMAP Exception = " + "\t" + information + "\t" + "Exception toString():  " + e.toString());

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.info( "NCBI REMAP Exception = " + "\t" + information + "\t" + "Exception toString():  " + e.toString());
		}
	}

	/**
	 * @param args
	 */
	public static void main( String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");

		Map<String, String> assemblyName2RefSeqAssemblyIDMap = new HashMap<String, String>();
		
		// remap(dataFolder,"GCF_000001405.25","GCF_000001405.26","C:\\Users\\Bur�ak\\Google Drive\\Output\\NoName\\GivenInputData\\chrName_0Based_StartInclusive_EndExclusive_hg38_coordinates.bed","C:\\Users\\Bur�ak\\Google Drive\\Output\\NoName\\GivenInputData\\chrName_0Based_StartInclusive_EndExclusive_hg19_coordinates.bed");
		// remap(dataFolder,"GCF_000001405.26","GCF_000001405.25","C:\\Users\\Bur�ak\\Developer\\Java\\GLANET\\Glanet\\src\\remap\\chrName_0Based_StartInclusive_EndExclusive_hg38_coordinates.bed","C:\\Users\\Bur�ak\\Developer\\Java\\GLANET\\Glanet\\src\\remap\\chrName_0Based_StartInclusive_EndExclusive_hg19_coordinates.bed",
		// Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON,
		// Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON,
		// Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_,
		// Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2);
		remap_show_batches( dataFolder, Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);

		// GCF_000001405.25 <----> GRCh37.p13
		// GCF_000001405.26 <------> GRCh38
		// GCF_000001405.27 <------> GRCh38.p1
		
		Remap.fillAssemblyName2RefSeqAssemblyIDMap(
				dataFolder, 
				Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE,
				assemblyName2RefSeqAssemblyIDMap);
		
		System.out.println("Have a look at assemblyName2RefSeqAssemblyIDMap");


	}
	
	//25 November 2016 starts
	//Customized for Hacettepe Collaboration
	/*
	 * InputFileSourceAssembly contains one genomic loci per line.
	 * OutputFileTargetAssembly contains one genomic loci per line.
	 */
	public static void convertOneGenomicLociPerLineUsingMapCustomizedForLHMD( 
			String outputFolder,
			String oneGenomicLociPerLineOutputFileInTargetAssembly,
			TIntObjectMap<String> lineNumber2SourceGenomicLociMap,
			TIntObjectMap<String> lineNumber2SourceInformationMap,
			TIntObjectMap<String> lineNumber2TargetGenomicLociMap, 
			String headerLine,
			Boolean fillMap,
			Map<String,String> sourceAssemblyCoordinates2TargetAssemblyCoordinatesMap) {

		// outputFile In Target Assembly
		BufferedWriter bufferedWriter = null;

		String toBeRemapped = null;
		String mapped = null;
		String toBeRemappedInformation = null;

		int numberofUnRemappedInputLine = 0;
		
		int indexofFirstTab = -1;
		int indexofSecondTab = -1;
		String key_chrNameTab1BasedCoordinateSourceAssembly = null;
		String value_chrNameTab1BasedCoordinateTargetAssembly = null;

		try{
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter( outputFolder + oneGenomicLociPerLineOutputFileInTargetAssembly));

			// Header line
			bufferedWriter.write( headerLine + System.getProperty( "line.separator"));

			for( int i = 1; i <= lineNumber2SourceGenomicLociMap.size(); i++){

				mapped = lineNumber2TargetGenomicLociMap.get(i);
				toBeRemappedInformation = lineNumber2SourceInformationMap.get(i);
				
				if( mapped != null){
					bufferedWriter.write(mapped + "\t" + toBeRemappedInformation +  System.getProperty( "line.separator"));
					
					if(fillMap){
						//source toBeRemapped example: chrName\t_0BasedStart\t_1BasedEnd
						//89=chr5	124649904	124649905
						toBeRemapped = lineNumber2SourceGenomicLociMap.get(i);
						
						indexofFirstTab = toBeRemapped.indexOf("\t");
						indexofSecondTab = toBeRemapped.indexOf("\t",indexofFirstTab+1);
						key_chrNameTab1BasedCoordinateSourceAssembly = toBeRemapped.substring(0, indexofFirstTab) + toBeRemapped.substring(indexofSecondTab);
						
						//target mapped example: chrName\t_1BasedStart_\t_1BasedEnd
						//89=chr5	123985598	123985598
						indexofFirstTab = mapped.indexOf("\t");
						indexofSecondTab = mapped.indexOf("\t",indexofFirstTab+1);
						value_chrNameTab1BasedCoordinateTargetAssembly = mapped.substring(0, indexofSecondTab);						 
						
						if (sourceAssemblyCoordinates2TargetAssemblyCoordinatesMap.containsKey(key_chrNameTab1BasedCoordinateSourceAssembly)){
							sourceAssemblyCoordinates2TargetAssemblyCoordinatesMap.put(key_chrNameTab1BasedCoordinateSourceAssembly, value_chrNameTab1BasedCoordinateTargetAssembly);
						}
						//key in the map: chrName\t_1BasedCoordinate
						//example
						//chr1	617158889						
						
					}//End of fill map
					
				}else{
					numberofUnRemappedInputLine++;
				}

			}// End of FOR

			System.out.println("Number of unremapped lines is: " + numberofUnRemappedInputLine);

			// CLOSE
			bufferedWriter.close();

		}catch( FileNotFoundException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}
	//25 November 2016 ends
	
	
	//5 April 2017
	public static void convertOneGenomicLociPerLineUsingMap(
			String outputFolder,
			String oneGenomicLociPerLineOutputFileInTargetAssembly,
			Map<String,String> source_1BasedStart_1BasedEnd2TargetMap, 
			Map<String,String> source_1BasedStart_1BasedEnd2SourceInformationMap,
			String headerLine){
		
		// outputFile In Target Assembly
		BufferedWriter bufferedWriter = null;

		String toBeRemapped = null;
		String mapped = null;
		String toBeRemappedInformation = null;

		int numberofUnRemappedInputLine = 0;

		try{
			bufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter( outputFolder + oneGenomicLociPerLineOutputFileInTargetAssembly));

			// Header line
			bufferedWriter.write( headerLine + System.getProperty( "line.separator"));

			for(Map.Entry<String, String> entry: source_1BasedStart_1BasedEnd2TargetMap.entrySet()){
				
				toBeRemapped =entry.getKey();
				mapped = entry.getValue();	

				
				if( mapped != null){
					bufferedWriter.write(mapped +  System.getProperty( "line.separator"));
				}else{
					if( source_1BasedStart_1BasedEnd2SourceInformationMap != null){
						toBeRemappedInformation = source_1BasedStart_1BasedEnd2SourceInformationMap.get(toBeRemapped);
						if( GlanetRunner.shouldLog())logger.warn("Please notice that there is an unconverted genomic loci during NCBI REMAP API. This may be due to positions are not in primary assembly.");
						if( GlanetRunner.shouldLog())logger.warn("rsId: " + toBeRemappedInformation + " To be Remapped: " + toBeRemapped + " Mapped: " + mapped);
						numberofUnRemappedInputLine++;
					}else{
						if( GlanetRunner.shouldLog())logger.warn("Please notice that there is an unconverted genomic loci during NCBI REMAP API. This may be due to positions are not in primary assembly.");
						if( GlanetRunner.shouldLog())logger.warn("To be Remapped: " + toBeRemapped + " Mapped: " + mapped);
						numberofUnRemappedInputLine++;
					}

				}

			}// End of FOR

			if( GlanetRunner.shouldLog())logger.warn( "Number of unremapped lines is: " + numberofUnRemappedInputLine);

			// CLOSE
			bufferedWriter.close();

		}catch( FileNotFoundException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		
	}
	
	
	
	//16 DEC 2016
	//Simplified Version
	//Augment with user defined observed alleles
	public static void convertTwoGenomicLociPerLineUsingMap(
			String forRSA_Folder,
			String all_GivenIntervalsTFOverlapsInGRCh37_p13_FileName,
			String all_GivenIntervalsTFOverlapsInLatestAssembly_FileName,
			Map<String,String> source_1BasedStart_1BasedEnd2TargetMap,
			Map<String, String> snpKey2ObservedAllelesMap,
			String headerLine){
		
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		
		String snpChrName;
		int _1BasedStart_SNP;
		int _1BasedEnd_SNP;
		
		String tfChrName;
		int _1BasedStart_TF;
		int _1BasedEnd_TF;
		
		String after = null;
		
		String snpKey = null;
		String snpTarget = null;
		
		String tfKey = null;
		String tfTarget = null;
		
		String observedAlleles = null;

	
		int numberofUnRemappedInputLine = 0;

		try{

			bufferedReader = new  BufferedReader(FileOperations.createFileReader(forRSA_Folder + all_GivenIntervalsTFOverlapsInGRCh37_p13_FileName));
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(forRSA_Folder + all_GivenIntervalsTFOverlapsInLatestAssembly_FileName));

			// Header line
			bufferedWriter.write( headerLine + System.getProperty( "line.separator"));
			
			//Skip header line
			strLine = bufferedReader.readLine();
			
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+2);
				indexofSixthTab = strLine.indexOf('\t',indexofFifthTab+1);
				
				snpChrName = strLine.substring(0, indexofFirstTab);
				_1BasedStart_SNP = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				_1BasedEnd_SNP = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				
				tfChrName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
				_1BasedStart_TF = Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab));
				_1BasedEnd_TF = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
				
				after = strLine.substring(indexofSixthTab+1);
		
				snpKey = snpChrName + "\t" + _1BasedStart_SNP + "\t" + _1BasedEnd_SNP;
				tfKey = tfChrName + "\t" + _1BasedStart_TF + "\t" + _1BasedEnd_TF;
				
				snpTarget = source_1BasedStart_1BasedEnd2TargetMap.get(snpKey);
				tfTarget = source_1BasedStart_1BasedEnd2TargetMap.get(tfKey);
				
				//Get the observed allele for this snpKey
				if(snpKey2ObservedAllelesMap!=null){
					observedAlleles = snpKey2ObservedAllelesMap.get(snpKey);					
				}
						
				if (snpTarget!=null &&  tfTarget!=null && observedAlleles!=null){
					bufferedWriter.write(snpTarget +"\t" + tfTarget + "\t" + after + "\t" + observedAlleles +System.getProperty("line.separator"));
				}else if (snpTarget!=null &&  tfTarget!=null){
					bufferedWriter.write(snpTarget +"\t" + tfTarget + "\t" + after  +System.getProperty("line.separator"));
				}else if(snpTarget!=null){
					bufferedWriter.write(snpTarget +"\t" + tfChrName + "\t" + "-1" + "\t"  + "-1" + "\t" + after +  "\t" + observedAlleles + System.getProperty("line.separator"));
				}else
				{
					numberofUnRemappedInputLine++;
				}
				
			}//End of WHILE reading all_GivenIntervalsTFOverlapsInGRCh37_p13_FileName
			

			if( GlanetRunner.shouldLog())logger.warn( "Number of unremapped lines is: " + numberofUnRemappedInputLine);

			// CLOSE
			bufferedReader.close();
			bufferedWriter.close();

		}catch( FileNotFoundException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
		
	}
	
	//16 DEC 2016 
	//Simplified Version
	public static void fillConversionMap(
			String outputFolder, 
			String remapReportFile,
			Map<String,String> source_1BasedStart_1BasedEnd2TargetMap,
			String underscoreOrTab){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;
		int indexofTwelfthTab;
		int indexofThirteenthTab;
		int indexofFourteenthTab;
		int indexofFifteenthTab;
		int indexofSixteenthTab;
		int indexofSeventeenthTab;

		//int lineNumber;
		
		int numberofConversionsNotInPrimaryAssembly = 0;

		ChromosomeName sourceChrName;
		
		//source
		//int source_0BasedStart = -1;
		int source_1BasedStart = -1;
		int source_1BasesEnd = -1;
		String sourceKey = null;
		
		// mapped
		int mapped_1BasedStart = -1;
		int mapped_1BasedEnd = -1;
		String targetValue = null;


		String mappedIntString;
		ChromosomeName mappedChrName;
		AssemblySource mappedAssembly;

		// Read remapReportFile
		// Using remapReportFile first fill chrName_start_end_in_one_assembly 2
		// chrName_start_end_in_another_assembly Map

		File file = new File(outputFolder + remapReportFile);

		if( file.exists()){

			try{

				fileReader = FileOperations.createFileReader(outputFolder + remapReportFile);
				bufferedReader = new BufferedReader(fileReader);

				while( ( strLine = bufferedReader.readLine()) != null){

					// #feat_name source_int mapped_int source_id mapped_id
					// source_length mapped_length source_start source_stop
					// source_strand source_sub_start source_sub_stop
					// mapped_start mapped_stop mapped_strand coverage recip
					// asm_unit
					// line_67 1 1 chr1 chr1 1 1 147664654 147664654 + 147664654
					// 147664654 147136772 147136772 + 1 Second Pass Primary
					// Assembly
					// line_67 1 2 chr1 NW_003871055.3 1 1 147664654 147664654 +
					// 147664654 147664654 4480067 4480067 + 1 First Pass
					// PATCHES
					// line_122 1 NULL chr14 NULL 1 NULL 93095256 93095256 +
					// NOMAP NOALIGN

					if( strLine.charAt( 0) != (Commons.GLANET_COMMENT_CHARACTER)){

						indexofFirstTab = strLine.indexOf( '\t');
						indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
						indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
						indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
						indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
						indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;
						indexofSeventhTab = ( indexofSixthTab > 0)?strLine.indexOf( '\t', indexofSixthTab + 1):-1;
						indexofEigthTab = ( indexofSeventhTab > 0)?strLine.indexOf( '\t', indexofSeventhTab + 1):-1;
						indexofNinethTab = ( indexofEigthTab > 0)?strLine.indexOf( '\t', indexofEigthTab + 1):-1;
						indexofTenthTab = ( indexofNinethTab > 0)?strLine.indexOf( '\t', indexofNinethTab + 1):-1;
						indexofEleventhTab = ( indexofTenthTab > 0)?strLine.indexOf( '\t', indexofTenthTab + 1):-1;
						indexofTwelfthTab = ( indexofEleventhTab > 0)?strLine.indexOf( '\t', indexofEleventhTab + 1):-1;
						indexofThirteenthTab = ( indexofTwelfthTab > 0)?strLine.indexOf( '\t', indexofTwelfthTab + 1):-1;
						indexofFourteenthTab = ( indexofThirteenthTab > 0)?strLine.indexOf( '\t',
								indexofThirteenthTab + 1):-1;
						indexofFifteenthTab = ( indexofFourteenthTab > 0)?strLine.indexOf( '\t',
								indexofFourteenthTab + 1):-1;
						indexofSixteenthTab = ( indexofFifteenthTab > 0)?strLine.indexOf( '\t', indexofFifteenthTab + 1):-1;
						indexofSeventeenthTab = ( indexofSixteenthTab > 0)?strLine.indexOf( '\t',
								indexofSixteenthTab + 1):-1;


						mappedIntString = strLine.substring( indexofSecondTab + 1, indexofThirdTab);

						if( !mappedIntString.equals(Commons.NULL)){

							sourceChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofThirdTab + 1,indexofFourthTab));
							mappedChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofFourthTab + 1, indexofFifthTab));

							//1based sourceStart
							source_1BasedStart = Integer.parseInt(strLine.substring(indexofSeventhTab + 1, indexofEigthTab));
							//1based sourceEnd
							source_1BasesEnd = Integer.parseInt(strLine.substring( indexofEigthTab + 1, indexofNinethTab));

							//1Based mappedStart
							mapped_1BasedStart = Integer.parseInt(strLine.substring( indexofTwelfthTab + 1,indexofThirteenthTab));
							//1Based mappedEnd
							mapped_1BasedEnd = Integer.parseInt(strLine.substring(indexofThirteenthTab + 1,indexofFourteenthTab));

							mappedAssembly = AssemblySource.convertStringtoEnum(strLine.substring(indexofSeventeenthTab + 1));

							// Pay attention
							// sourceInt and mappedInt does not have to be the same
							// e.g: rs1233578
							if( sourceChrName == mappedChrName && mappedAssembly.isPrimaryAssembly()){

								sourceKey = sourceChrName.convertEnumtoString() + underscoreOrTab + source_1BasedStart + underscoreOrTab + source_1BasesEnd;
								targetValue = mappedChrName.convertEnumtoString() + underscoreOrTab + mapped_1BasedStart + underscoreOrTab + mapped_1BasedEnd;
								source_1BasedStart_1BasedEnd2TargetMap.put(sourceKey,targetValue);
	
							}// End of IF: Valid conversion
							
							else{
								numberofConversionsNotInPrimaryAssembly++;
							}
						}// End of IF mappedInt is not NULL

					}// End of IF: Not Header or Comment Line

				}// End of while

				// for debug purposes starts
				if( GlanetRunner.shouldLog())logger.info( "Number of Lines not in Primary Assembly : " + numberofConversionsNotInPrimaryAssembly + " using file: " + remapReportFile);
				//if( GlanetRunner.shouldLog())logger.info( "Number of Lines in lineNumber2SourceGenomicLociMap and " +  remapReportFile  + " may differ, since there can be more than one lines in " + remapReportFile + " because of multiple passes for mappings not in primary assembly.");
				// for debug purposes ends

				// close
				bufferedReader.close();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of if remapReportFile exists
		
	}

	
	//Depreceated
	//Then delete it
	public static void fillConversionMap(
			String outputFolder, 
			String remapReportFile,
			TIntObjectMap<String> lineNumber2SourceGenomicLociMap, 
			TIntObjectMap<String> lineNumber2TargetGenomicLociMap) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;
		int indexofTwelfthTab;
		int indexofThirteenthTab;
		int indexofFourteenthTab;
		int indexofFifteenthTab;
		int indexofSixteenthTab;
		int indexofSeventeenthTab;

		int lineNumber;
		
		int numberofConversionsNotInPrimaryAssembly = 0;

		// source
		// int sourceInt;
		ChromosomeName sourceChrName;
//		int sourceStart;
//		int sourceEnd;

		// mapped
		String mappedIntString;
		// int mappedInt;
		ChromosomeName mappedChrName;
		int mappedStart;
		int mappedEnd;
		AssemblySource mappedAssembly;

		// Read remapReportFile
		// Using remapReportFile first fill chrName_start_end_in_one_assembly 2
		// chrName_start_end_in_another_assembly Map

		File file = new File( outputFolder + remapReportFile);

		if( file.exists()){

			try{

				fileReader = FileOperations.createFileReader( outputFolder + remapReportFile);

				bufferedReader = new BufferedReader( fileReader);

				while( ( strLine = bufferedReader.readLine()) != null){

					// #feat_name source_int mapped_int source_id mapped_id
					// source_length mapped_length source_start source_stop
					// source_strand source_sub_start source_sub_stop
					// mapped_start mapped_stop mapped_strand coverage recip
					// asm_unit
					// line_67 1 1 chr1 chr1 1 1 147664654 147664654 + 147664654
					// 147664654 147136772 147136772 + 1 Second Pass Primary
					// Assembly
					// line_67 1 2 chr1 NW_003871055.3 1 1 147664654 147664654 +
					// 147664654 147664654 4480067 4480067 + 1 First Pass
					// PATCHES
					// line_122 1 NULL chr14 NULL 1 NULL 93095256 93095256 +
					// NOMAP NOALIGN

					if( strLine.charAt( 0) != ( Commons.GLANET_COMMENT_CHARACTER)){

						indexofFirstTab = strLine.indexOf( '\t');
						indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
						indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
						indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
						indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
						indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;
						indexofSeventhTab = ( indexofSixthTab > 0)?strLine.indexOf( '\t', indexofSixthTab + 1):-1;
						indexofEigthTab = ( indexofSeventhTab > 0)?strLine.indexOf( '\t', indexofSeventhTab + 1):-1;
						indexofNinethTab = ( indexofEigthTab > 0)?strLine.indexOf( '\t', indexofEigthTab + 1):-1;
						indexofTenthTab = ( indexofNinethTab > 0)?strLine.indexOf( '\t', indexofNinethTab + 1):-1;
						indexofEleventhTab = ( indexofTenthTab > 0)?strLine.indexOf( '\t', indexofTenthTab + 1):-1;
						indexofTwelfthTab = ( indexofEleventhTab > 0)?strLine.indexOf( '\t', indexofEleventhTab + 1):-1;
						indexofThirteenthTab = ( indexofTwelfthTab > 0)?strLine.indexOf( '\t', indexofTwelfthTab + 1):-1;
						indexofFourteenthTab = ( indexofThirteenthTab > 0)?strLine.indexOf( '\t',
								indexofThirteenthTab + 1):-1;
						indexofFifteenthTab = ( indexofFourteenthTab > 0)?strLine.indexOf( '\t',
								indexofFourteenthTab + 1):-1;
						indexofSixteenthTab = ( indexofFifteenthTab > 0)?strLine.indexOf( '\t', indexofFifteenthTab + 1):-1;
						indexofSeventeenthTab = ( indexofSixteenthTab > 0)?strLine.indexOf( '\t',
								indexofSixteenthTab + 1):-1;

						lineNumber = Integer.parseInt( strLine.substring(
								strLine.substring( 0, indexofFirstTab).indexOf( Commons.UNDERSCORE) + 1,
								indexofFirstTab));

						// sourceInt =
						// Integer.parseInt(strLine.substring(indexofFirstTab+1,
						// indexofSecondTab));

						mappedIntString = strLine.substring( indexofSecondTab + 1, indexofThirdTab);

						if( !mappedIntString.equals( Commons.NULL)){

							// mappedInt =
							// Integer.parseInt(strLine.substring(indexofSecondTab+1,
							// indexofThirdTab));

							sourceChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofThirdTab + 1,indexofFourthTab));
							mappedChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofFourthTab + 1, indexofFifthTab));

							//0based sourceStart
							//sourceStart = Integer.parseInt(strLine.substring( indexofSeventhTab + 1, indexofEigthTab));
							//1based sourceEnd
							//sourceEnd = Integer.parseInt(strLine.substring( indexofEigthTab + 1, indexofNinethTab));

							//1Based mappedStart
							mappedStart = Integer.parseInt(strLine.substring( indexofTwelfthTab + 1,indexofThirteenthTab));
							//1Based mappedEnd
							mappedEnd = Integer.parseInt(strLine.substring(indexofThirteenthTab + 1,indexofFourteenthTab));

							mappedAssembly = AssemblySource.convertStringtoEnum(strLine.substring(indexofSeventeenthTab + 1));

							// Pay attention
							// sourceInt and mappedInt does not have to be the same
							// e.g: rs1233578
							if( sourceChrName == mappedChrName && mappedAssembly.isPrimaryAssembly()){

								lineNumber2TargetGenomicLociMap.put(lineNumber,mappedChrName.convertEnumtoString() + "\t" + mappedStart + "\t" + mappedEnd);

								//Check
								//No check is needed. lineNumber2SourceGenomicLociMap filled with 0BasedStart and 0BasedEndExclusive or 1BasedEndInclusive
								//However in remap report file 0BasedStart becomes 1BasedStart
//								if(!lineNumber2SourceGenomicLociMap.get(lineNumber).equals(sourceChrName.convertEnumtoString() + "\t" + sourceStart + "\t" + sourceEnd)){
//									if( GlanetRunner.shouldLog())logger.info(Commons.THERE_IS_A_SITUATION);
//								}

							}// End of IF: Valid conversion
							
							else{
								numberofConversionsNotInPrimaryAssembly++;
							}
						}// End of IF mappedInt is not NULL

					}// End of IF: Not Header or Comment Line

				}// End of while

				// for debug purposes starts
				if( GlanetRunner.shouldLog())logger.info( "Number of Lines In lineNumber2SourceGenomicLociMap : " + lineNumber2SourceGenomicLociMap.size() + " using file: " + remapReportFile);
				if( GlanetRunner.shouldLog())logger.info( "Number of Lines In lineNumber2TargetGenomicLociMap : " + lineNumber2TargetGenomicLociMap.size() + " using file: " + remapReportFile);
				if( GlanetRunner.shouldLog())logger.info( "Number of Lines not in Primary Assembly : " + numberofConversionsNotInPrimaryAssembly + " using file: " + remapReportFile);
				if( GlanetRunner.shouldLog())logger.info( "Number of Lines in lineNumber2SourceGenomicLociMap and " +  remapReportFile  + " may differ, since there can be more than one lines in " + remapReportFile + " because of multiple passes for mappings not in primary assembly.");
				// for debug purposes ends

				// close
				bufferedReader.close();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of if remapReportFile exists

	}

}
