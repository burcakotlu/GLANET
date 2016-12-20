/**
 * 
 */
package rsat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jaxbxjctool.NCBIEutils;

import org.apache.log4j.Logger;

import remap.Remap;
import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.CommandLineArguments;

/**
 * @author Burçak Otlu
 * @date Dec 16, 2016
 * @project Glanet 
 *
 */
public class GenerationofAllTFAnnotationsFileInGRCh37p13AndInLatestAssemblySimplified {

	final static Logger logger = Logger.getLogger(GenerationofAllTFAnnotationsFileInGRCh37p13AndInLatestAssemblySimplified.class);

	/*
	 * Generate All Given Intervals and TF Overlaps Annotation File in GRCh37.p13 
	 * Generate REMAP InputFile 
	 * During these fill keys of source_1BasedStart_1BasedEnd2TargetMap
	 */
	public static void generateAllTFAnnotationsFileAndREMAPInputFile( 
			String outputFolder,
			String all_TF_Annotations_1Based_Start_End_GRCh37_p13,
			Map<String,String> source_1BasedStart_1BasedEnd2TargetMap,
			String remapInputFile_0Based_Start_EndExclusive_GRCh37_P13) {

		String fileAbsolutePath = null;

		BufferedReader bufferedReader = null;

		BufferedWriter allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter = null;
		BufferedWriter remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter = null;

		String strLine;
		String after;
		
		String chrNameTab1BasedSNPStart1BasedSNPEnd = null;
		String chrNameTab1BasedTFStart1BasedTFEnd = null;

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

		String remapInput = null;

		String tfAnnotationDirectory = outputFolder + System.getProperty( "file.separator") + Commons.ANNOTATION + System.getProperty( "file.separator") + Commons.TF + System.getProperty( "file.separator");
		String allTFAnnotationsDirectory = outputFolder + System.getProperty( "file.separator") + Commons.FOR_RSA + System.getProperty( "file.separator");

		File baseFolder = new File(tfAnnotationDirectory);

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
							
							chrNameTab1BasedSNPStart1BasedSNPEnd = strLine.substring(0,indexofFirstTab) + "\t" + snpStart1Based + "\t" + snpEnd1Based;
							chrNameTab1BasedTFStart1BasedTFEnd = strLine.substring(indexofThirdTab + 1, indexofFourthTab) + "\t" + tfStart1Based + "\t" + tfEnd1Based;
							
							
							//Write to ALL SNPs TFs Overlaps File
							allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter.write(chrNameTab1BasedSNPStart1BasedSNPEnd + "\t" + chrNameTab1BasedTFStart1BasedTFEnd + "\t" + after + System.getProperty( "line.separator"));

							/*** 1st******SNP Genomic Loci Line starts ****************/
							//No duplicates
							if (!source_1BasedStart_1BasedEnd2TargetMap.containsKey(chrNameTab1BasedSNPStart1BasedSNPEnd)){
								source_1BasedStart_1BasedEnd2TargetMap.put(chrNameTab1BasedSNPStart1BasedSNPEnd,null);
								
								remapInput = strLine.substring(0, indexofFirstTab) + "\t" + snpStart0Based + "\t" + snpEnd1Based + System.getProperty( "line.separator");
								//Write to Remap Input File
								remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter.write(remapInput);
							}
							/*** 1st******SNP Genomic Loci Line ends ******************/

							/*** 2nd******TF Genomic Loci Line starts *****************/													
							//No duplicates
							if (!source_1BasedStart_1BasedEnd2TargetMap.containsKey(chrNameTab1BasedTFStart1BasedTFEnd)){
								source_1BasedStart_1BasedEnd2TargetMap.put(chrNameTab1BasedTFStart1BasedTFEnd,null);
								
								remapInput = strLine.substring(indexofThirdTab + 1, indexofFourthTab) + "\t" + tfStart0Based + "\t" + tfEnd1Based + System.getProperty( "line.separator");
								//Write to Remap Input File
								remapInput0BasedStartEndExclusiveGrch37p13BufferedWriter.write(remapInput);
							}
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
	
	public static void callNCBIREMAPAndGenerateAllTFAnnotationsFileInLatestAssembly( 
			String dataFolder,
			String outputFolder, 
			Map<String,String> source_1BasedStart_1BasedEnd2TargetMap,
			Map<String,String> snpKey_chrName_1BasedStart_1BasedEnd_2ObservedAllelesMap,
			String remapInputFile_OBased_Start_EndExclusive_GRCh37_P13_BED_FILE,
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
				forRSA_REMAP_Folder + remapInputFile_OBased_Start_EndExclusive_GRCh37_P13_BED_FILE,
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
				source_1BasedStart_1BasedEnd2TargetMap);

		Remap.convertTwoGenomicLociPerLineUsingMap( 
				forRSA_Folder,
				Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCh37_P13,
				Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_LATEST_ASSEMBLY_RETURNED_BY_NCBI_EUTILS, 
				source_1BasedStart_1BasedEnd2TargetMap,
				snpKey_chrName_1BasedStart_1BasedEnd_2ObservedAllelesMap,
				headerLine);
		
		if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");

	}

	public static void fillSNPKey2ObservedAllelesMap(
			String inputFileName,
			Map<String,String> snpKey_chrName_1BasedStart_1BasedEnd_2ObservedAllelesMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		int indexofFirstTab = -1;
		int indexofSecondTab =-1;
		int indexofThirdTab = -1;
		int indexofFourthTab = -1;
		
		String chrName = null ;
		int _1BasedStart = -1;
		int _1BasedEnd = -2;
		String observedAlles = null;
		
		String snpKey = null;
		
		try {
			fileReader = FileOperations.createFileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//Skip header line
			strLine = bufferedReader.readLine();
			
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1) ;
				indexofFourthTab =  strLine.indexOf('\t',indexofThirdTab+1); 
				
				chrName = strLine.substring(0, indexofFirstTab);
				_1BasedStart = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				_1BasedEnd = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				
				if (indexofFourthTab>-1) {
					observedAlles = strLine.substring(indexofThirdTab+1,indexofFourthTab);					
				}else{
					observedAlles = strLine.substring(indexofThirdTab+1);					
				}
				
				snpKey = chrName + "\t" + _1BasedStart + "\t" + _1BasedEnd;
				
				snpKey_chrName_1BasedStart_1BasedEnd_2ObservedAllelesMap.put(snpKey, observedAlles);
				
			}//End of while
			
			//Close
			bufferedReader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public static void main(String[] args) {
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		
		String inputFileName = args[CommandLineArguments.InputFileNameWithFolder.value()];

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

		
		Map<String,String> source_1BasedStart_1BasedEnd2TargetMap = new HashMap<String,String>();
		
		
		Map<String,String> snpKey_chrName_1BasedStart_1BasedEnd_2ObservedAllelesMap = new HashMap<String,String>();

		fillSNPKey2ObservedAllelesMap(inputFileName,snpKey_chrName_1BasedStart_1BasedEnd_2ObservedAllelesMap);

		generateAllTFAnnotationsFileAndREMAPInputFile(
				outputFolder,
				Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCh37_P13, 
				source_1BasedStart_1BasedEnd2TargetMap,
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

			callNCBIREMAPAndGenerateAllTFAnnotationsFileInLatestAssembly(
					dataFolder, 
					outputFolder,
					source_1BasedStart_1BasedEnd2TargetMap, 
					snpKey_chrName_1BasedStart_1BasedEnd_2ObservedAllelesMap,
					Commons.REMAP_INPUT_FILE_All_TF_ANNOTATIONS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES_BED_FILE,
					latestAssembyNameReturnedByNCBIEutils,
					assemblyName2RefSeqAssemblyIDMap);
			
		}//End of IF
		

	}

}
