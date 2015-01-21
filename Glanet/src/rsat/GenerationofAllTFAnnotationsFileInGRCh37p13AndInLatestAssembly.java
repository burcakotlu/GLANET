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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import remap.Remap;
import auxiliary.FileOperations;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Dec 25, 2014
 * @project Glanet 
 *
 */
public class GenerationofAllTFAnnotationsFileInGRCh37p13AndInLatestAssembly {

    public static void callNCBIREMAPAndGenerateAllTFAnnotationsFileInLatestAssembly(
	    String dataFolder,
	    String outputFolder,
	    String all_TF_Annotations_File_1Based_Start_End_GRCh37_p13,
	    TIntObjectMap<String> lineNumber2SourceGenomicLociMap,
	    TIntObjectMap<String> lineNumber2SourceInformationMap,
	    TIntObjectMap<String> lineNumber2TargetGenomicLociMap,
	    String remapInputFile_OBased_Start_EndExclusive_GRCh37_P13,
	    String all_TF_Annotations_File_1Based_Start_End_GRCh38){
    	
    
    String headerLine = Commons.HEADER_LINE_FOR_ALL_TF_ANNOTATIONS_IN_LATEST_ASSEMBLY;
		
    String forRSA_Folder 		= outputFolder + Commons.FOR_RSA + System.getProperty("file.separator");
    String forRSA_REMAP_Folder 	= outputFolder + Commons.FOR_RSA + System.getProperty("file.separator") + Commons.NCBI_REMAP + System.getProperty("file.separator");
	
	String sourceReferenceAssemblyID = "GCF_000001405.25";
	//In fact targetReferenceAssemblyID must be the assemblyName that NCBI ETILS returns (groupLabel)
	//args must be augmented with latestNCBIAssemblyName
	String targetReferenceAssemblyID = "GCF_000001405.26";
	
	String merge = Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON;
	String allowMultipleLocation = Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON;
	double minimumRatioOfBasesThatMustBeRemapped = Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_;
	double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength  = Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2;
	
	Remap.remap(
			dataFolder,
			sourceReferenceAssemblyID, 
			targetReferenceAssemblyID, 
			forRSA_REMAP_Folder + remapInputFile_OBased_Start_EndExclusive_GRCh37_P13 , 
			forRSA_REMAP_Folder + Commons.REMAP_DUMMY_OUTPUT_FILE,
			forRSA_REMAP_Folder + Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE,
			forRSA_REMAP_Folder + Commons.REMAP_DUMMY_GENOME_WORKBENCH_PROJECT_FILE,						
			merge,
			allowMultipleLocation,
			minimumRatioOfBasesThatMustBeRemapped,
			maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
	
	Remap.fillConversionMap(forRSA_REMAP_Folder, 
			Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE, 
			lineNumber2SourceGenomicLociMap,
			lineNumber2TargetGenomicLociMap);
	
	Remap.convertTwoGenomicLociPerLineUsingMap(
			forRSA_Folder,
			Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCh37_P13, 
			Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCH38,
			lineNumber2SourceGenomicLociMap,
			lineNumber2SourceInformationMap,
			lineNumber2TargetGenomicLociMap,
			headerLine);
    }
    
    /*
     * By reading each TF Annotation File Generate All TF Annotations File
     * Then Generate REMAP InputFile
     * During these fill lineNumber2SourceGenomicLociMap and lineNumber2SourceInformationMap
     */
    public static void generateAllTFAnnotationsFileAndREMAPInputFile(
	    String outputFolder, 
	    String all_TF_Annotations_1Based_Start_End_GRCh37_p13, 
	    TIntObjectMap<String> lineNumber2SourceGenomicLociMap,
	    TIntObjectMap<String> lineNumber2SourceInformationMap,
	    String remapInputFile_OBased_Start_EndExclusive_GRCh37_P13){

	String fileAbsolutePath = null;
	
	FileReader fileReader = null;
	BufferedReader bufferedReader = null;
	
	FileWriter fileWriter = null;
	BufferedWriter allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter = null;
	
	
	FileWriter remapInputFileWriter = null;
	BufferedWriter  remapInputOBasedStartEndExclusiveGrch37p13BufferedWriter = null;
	
	String strLine;
	String after;
	
	int indexofFirstTab;
	int indexofSecondTab;
	int indexofThirdTab;
	int indexofFourthTab;
	int indexofFifthTab;
	int indexofSixthTab;
	
	//snpStart
	int snpStartInclusive;
	int snpStartExclusive;
	
	//snpEnd
	int snpEndInclusive;
	int snpEndExclusive;
	
	//tfStart
	int tfStartInclusive;
	int tfStartExclusive;
	
	//tfEnd
	int tfEndInclusive;
	int tfEndExclusive;
	
	int numberofLocisInRemapInputFile = 1;
	String remapInputFileLine1 = null;
	String remapInputFileLine2 = null;
	
	
	String tfAnnotationDirectory = outputFolder + System.getProperty("file.separator") + Commons.ANNOTATION +  System.getProperty("file.separator") + Commons.TF + System.getProperty("file.separator")  ;
	String allTFAnnotationsDirectory = outputFolder + System.getProperty("file.separator")  + Commons.FOR_RSA + System.getProperty("file.separator")  ;
	
	File baseFolder = new File(tfAnnotationDirectory);
	
	try {
	    	fileWriter = FileOperations.createFileWriter(allTFAnnotationsDirectory + all_TF_Annotations_1Based_Start_End_GRCh37_p13);
	    	allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter = new BufferedWriter(fileWriter);
        	    
	    	remapInputFileWriter = FileOperations.createFileWriter(allTFAnnotationsDirectory + Commons.NCBI_REMAP  + System.getProperty("file.separator") + remapInputFile_OBased_Start_EndExclusive_GRCh37_P13);
	    	remapInputOBasedStartEndExclusiveGrch37p13BufferedWriter = new BufferedWriter(remapInputFileWriter);
        
        	if (baseFolder.exists() && baseFolder.isDirectory()){
        	    
        		File[] files = baseFolder.listFiles();
        		
        		allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter.write("#All TF annotations in 1Based Start and End in GRCh37 p13 coordinates." + System.getProperty("line.separator"));
        		
        		for(File tfAnnotationFile: files){
        		    
        		    //fileName = tfAnnotationFile.getName();
        			fileAbsolutePath = tfAnnotationFile.getAbsolutePath();				         				
        			
        		    fileReader =FileOperations.createFileReader(fileAbsolutePath);
        		    bufferedReader = new BufferedReader(fileReader);
        		    	
		        	    
        		    //Start reading each TF Annotation File which is OBased Start End GRCh37 p13
    		    	while ((strLine = bufferedReader.readLine())!=null){
    		    	    
    		    	    //chr1	11862777	11862777	chr1	11862636	11863019	AP2ALPHA	HELAS3	spp.optimal.wgEncodeSydhTfbsHelas3Ap2alphaStdAlnRep0_VS_wgEncodeSydhTfbsHelas3InputStdAlnRep1.narrowPeak

    		    	    if (strLine.charAt(0) != Commons.GLANET_COMMENT_CHARACTER){
    		    		
        		    		indexofFirstTab = strLine.indexOf('\t');
        		    		indexofSecondTab = (indexofFirstTab>0)? strLine.indexOf('\t', indexofFirstTab+1):-1;
        		    		indexofThirdTab = (indexofSecondTab>0)? strLine.indexOf('\t', indexofSecondTab+1):-1;
        		    		indexofFourthTab = (indexofThirdTab>0)? strLine.indexOf('\t', indexofThirdTab+1):-1;
        		    		indexofFifthTab = (indexofFourthTab>0)? strLine.indexOf('\t', indexofFourthTab+1):-1;
        		    		indexofSixthTab = (indexofFifthTab>0)? strLine.indexOf('\t', indexofFifthTab+1):-1;
        		    		
        		    		snpStartInclusive = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
        		    		snpStartExclusive = snpStartInclusive+1;
        		    		
        		    		snpEndInclusive = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
        		    		snpEndExclusive = snpEndInclusive+1;
        		    		
        		    		tfStartInclusive = Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab));
        		    		tfStartExclusive = tfStartInclusive+1;
        		    		
        		    		tfEndInclusive = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
        		    		tfEndExclusive = tfEndInclusive+1;
        		    		
        		    		after = strLine.substring(indexofSixthTab+1);
        		        	   
        		    		allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter.write(strLine.substring(0, indexofFirstTab) + "\t" + snpStartExclusive + "\t" + snpEndExclusive + "\t" + strLine.substring(indexofThirdTab+1, indexofFourthTab) +  "\t"  + tfStartExclusive + "\t" + tfEndExclusive + "\t"  + after +  System.getProperty("line.separator"));
        		     
        		    		/***1st******SNP Genomic Loci Line starts****************/
        		    		remapInputFileLine1 = strLine.substring(0, indexofFirstTab) + "\t" + snpStartInclusive +  "\t" +  snpEndExclusive +System.getProperty("line.separator");
        		    				
        		    		remapInputOBasedStartEndExclusiveGrch37p13BufferedWriter.write(remapInputFileLine1);
        		    		
        		    		lineNumber2SourceGenomicLociMap.put(numberofLocisInRemapInputFile, strLine.substring(0, indexofFirstTab) + "\t" + snpStartInclusive +  "\t" +  snpEndExclusive);
        		    		lineNumber2SourceInformationMap.put(numberofLocisInRemapInputFile, after);
        		    		
        		    		numberofLocisInRemapInputFile++;
           		    		/***1st******SNP Genomic Loci Line ends******************/
       		    		
        		    		
        		    		/***2nd******TF Genomic Loci Line starts*****************/
        		    		remapInputFileLine2 = strLine.substring(indexofThirdTab+1, indexofFourthTab) + "\t" + tfStartInclusive + "\t" +   tfEndExclusive + System.getProperty("line.separator");
        		    		
        		    		remapInputOBasedStartEndExclusiveGrch37p13BufferedWriter.write(remapInputFileLine2);
        		    		
        		    		lineNumber2SourceGenomicLociMap.put(numberofLocisInRemapInputFile,strLine.substring(indexofThirdTab+1, indexofFourthTab) + "\t" + tfStartInclusive + "\t" +   tfEndExclusive);
        		    		lineNumber2SourceInformationMap.put(numberofLocisInRemapInputFile, after);
        		    		
        		    		numberofLocisInRemapInputFile++;
        		    		/***2nd******TF Genomic Loci Line ends*******************/
      		    		  
           		    		
    		    	    }//End of IF strLine is not Comment Line
    		    	    
    		    	}//End of While loop: reading each TF Annotation File
        		    	
    		    	//Close each TF File
    		    	bufferedReader.close(); 
        		    	
        		}//End of For loop: each TF Annotation file
        		        
        	}//TF Annotation Directory
        	
        	//Close AllTFAnnotationsFile And RemapInputFile
        	allTFAnnotations1BasedStartEndGRCh37p13BufferedWriter.close();
        	remapInputOBasedStartEndExclusiveGrch37p13BufferedWriter.close();

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	
	String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
	
	//jobName starts
	String jobName = args[CommandLineArguments.JobName.value()].trim();
	if (jobName.isEmpty()){
		jobName = Commons.NO_NAME;
	}
	//jobName ends
			
	String outputFolder 	= glanetFolder + Commons.OUTPUT + System.getProperty("file.separator") + jobName +  System.getProperty("file.separator");
	String dataFolder 		= glanetFolder + Commons.DATA + System.getProperty("file.separator");
	
		
	//Before each run
	//delete directories and files under base directories
	FileOperations.deleteOldFiles(outputFolder + Commons.FOR_RSA + System.getProperty("file.separator"));

	TIntObjectMap<String> lineNumber2SourceGenomicLociMap 	= new TIntObjectHashMap<String>();
	TIntObjectMap<String> lineNumber2SourceInformationMap 	= new TIntObjectHashMap<String>();
	TIntObjectMap<String> lineNumber2TargetGenomicLociMap 	= new TIntObjectHashMap<String>();
	
	
	
	generateAllTFAnnotationsFileAndREMAPInputFile(
			outputFolder,
			Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCh37_P13,
			lineNumber2SourceGenomicLociMap,
			lineNumber2SourceInformationMap,
			Commons.REMAP_INPUT_FILE_All_TF_ANNOTATIONS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);
	
	
	
	callNCBIREMAPAndGenerateAllTFAnnotationsFileInLatestAssembly(
			dataFolder,
			outputFolder,
			Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCh37_P13,
			lineNumber2SourceGenomicLociMap,
			lineNumber2SourceInformationMap,
			lineNumber2TargetGenomicLociMap,
			Commons.REMAP_INPUT_FILE_All_TF_ANNOTATIONS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES,
			Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCH38);
	


    }

}
