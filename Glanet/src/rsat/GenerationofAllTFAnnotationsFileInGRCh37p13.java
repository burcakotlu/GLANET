/**
 * 
 */
package rsat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import remap.Remap;
import auxiliary.FileOperations;
import common.Commons;
import enumtypes.CommandLineArguments;

/**
 * @author Burçak Otlu
 * @date Dec 25, 2014
 * @project Glanet 
 *
 */
public class GenerationofAllTFAnnotationsFileInGRCh37p13 {

    public static void callNCBIREMAP(String dataFolder,String outputFolder,String remapInputFile_OBased_Start_EndExclusive_GRCh37_P13){
	
	String forRSAFolder = outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.FOR_RSA + System.getProperty("file.separator");
	
	String sourceReferenceAssemblyID = "GCF_000001405.25";
	//In fact targetReferenceAssemblyID must be the assemblyName that NCBI ETILS returns (groupLabel)
	//args must be augmented with latestNCBIAssemblyName
	String targetReferenceAssemblyID = "GCF_000001405.26";
	
	String merge = Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON;
	String allowMultipleLocation = Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON;
	double minimumRatioOfBasesThatMustBeRemapped = Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_;
	double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength  = Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2;
	
	Map<String,String> conversionMap = null;
	
	Remap.remap(
			dataFolder,
			sourceReferenceAssemblyID, 
			targetReferenceAssemblyID, 
			forRSAFolder + remapInputFile_OBased_Start_EndExclusive_GRCh37_P13 , 
			forRSAFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
			forRSAFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_ALL_TF_ANNOTATIONS_CHRNUMBER_1BASED_START_END_GRCH38_COORDINATES,
			forRSAFolder + Commons.REMAP_DUMMY_GENOME_WORKBENCH_PROJECT_FILE,						
			merge,
			allowMultipleLocation,
			minimumRatioOfBasesThatMustBeRemapped,
			maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);

	
	 conversionMap = new HashMap<String,String>();
	 Remap.fillConversionMap(forRSAFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_ALL_TF_ANNOTATIONS_CHRNUMBER_1BASED_START_END_GRCH38_COORDINATES,conversionMap);
	 Remap.convertUsingMap(forRSAFolder, Commons.ALL_TF_ANNOTATIONS_FILE, Commons.AUGMENTED_TF_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  

    }
    
    public static void generateOneBigTFAnnotationAndREMAPInputFile(String outputFolder, String oneBigFileName, String remapInputFile){

	String fileName=null;
	String fileAbsolutePath = null;
	
	FileReader fileReader = null;
	BufferedReader bufferedReader = null;
	
	FileWriter fileWriter = null;
	BufferedWriter bufferedWriter = null;
	
	
	FileWriter remapInputFileWriter = null;
	BufferedWriter  remapInputBufferedWriter = null;
	
	String strLine;
	
	int indexofFirstTab;
	int indexofSecondTab;
	int indexofThirdTab;
	int indexofFourthTab;
	int indexofFifthTab;
	int indexofSixthTab;
	
	int snpEndInclusive;
	int snpEndExclusive;
	
	int tfEndInclusive;
	int tfEndExclusive;
	
	String tfAnnotationDirectory = outputFolder + System.getProperty("file.separator") + Commons.ANNOTATION +  System.getProperty("file.separator") + Commons.TF + System.getProperty("file.separator")  ;
	String allTFAnnotationsDirectory = outputFolder + System.getProperty("file.separator") + Commons.ANNOTATION +  System.getProperty("file.separator") + Commons.FOR_RSA + System.getProperty("file.separator")  ;
	
	File baseFolder = new File(tfAnnotationDirectory);
	
	try {
	    	fileWriter = FileOperations.createFileWriter(allTFAnnotationsDirectory + oneBigFileName);
	    	bufferedWriter = new BufferedWriter(fileWriter);
        	    
	    	remapInputFileWriter = FileOperations.createFileWriter(allTFAnnotationsDirectory + remapInputFile);
	    	remapInputBufferedWriter = new BufferedWriter(remapInputFileWriter);
        
        	    
        	if (baseFolder.exists() && baseFolder.isDirectory()){
        	    
        		File[] files = baseFolder.listFiles();
        		
        		bufferedWriter.write("#This file contains all TF annotations in 0Based Start, 0Based End Inclusive, in GRCh37 p13 coordinates." + System.getProperty("line.separator"));
	        	
        		
        		for(File tfAnnotationFile: files){
        		    
        		    	fileName = tfAnnotationFile.getName();
        			fileAbsolutePath = tfAnnotationFile.getAbsolutePath();				         				
        			
        			System.out.println(fileName);
        		    	System.out.println(fileAbsolutePath);
        		    	
        		    	
        		    	fileReader =FileOperations.createFileReader(fileAbsolutePath);
        		    	bufferedReader = new BufferedReader(fileReader);
        		    	
		        	     
        		    	while ((strLine = bufferedReader.readLine())!=null){
        		    	    
        		    	    //chr1	11862777	11862777	chr1	11862636	11863019	AP2ALPHA	HELAS3	spp.optimal.wgEncodeSydhTfbsHelas3Ap2alphaStdAlnRep0_VS_wgEncodeSydhTfbsHelas3InputStdAlnRep1.narrowPeak

        		    	    if (strLine.charAt(0) != Commons.GLANET_COMMENT_CHARACTER){
        		    		
        		    		
        		    		indexofFirstTab = strLine.indexOf('\t');
        		    		indexofSecondTab = (indexofFirstTab>0)? strLine.indexOf('\t', indexofFirstTab+1):-1;
        		    		indexofThirdTab = (indexofSecondTab>0)? strLine.indexOf('\t', indexofSecondTab+1):-1;
        		    		indexofFourthTab = (indexofThirdTab>0)? strLine.indexOf('\t', indexofThirdTab+1):-1;
        		    		indexofFifthTab = (indexofFourthTab>0)? strLine.indexOf('\t', indexofFourthTab+1):-1;
        		    		indexofSixthTab = (indexofFifthTab>0)? strLine.indexOf('\t', indexofFifthTab+1):-1;
        		    		
        		    		snpEndInclusive = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
        		    		snpEndExclusive = snpEndInclusive+1;
        		    		
        		    		tfEndInclusive = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
        		    		tfEndExclusive = tfEndInclusive+1;
        		        	   
        		    		bufferedWriter.write(strLine + System.getProperty("line.separator"));
        		     
        		    		remapInputBufferedWriter.write( strLine.substring(0, indexofSecondTab) + "\t" + snpEndExclusive  + System.getProperty("line.separator"));
        		    		remapInputBufferedWriter.write( strLine.substring(indexofThirdTab+1, indexofFifthTab) + "\t" + tfEndExclusive + System.getProperty("line.separator"));
               		    		
               		    	      }//End of IF strLine is not Comment Line
        		    	    
        		    	}//End of While loop: reading each TF Annotation File
        		    	
        		    	//Close each TF File
        		    	bufferedReader.close(); 
        		    	
        		       
        		}//End of For loop: each TF Annotation file
        		        
        	}//TF Annotation Directory
        	
        	//Close AllTFAnnotationsFile And RemapInputFile
        	bufferedWriter.close();
        	remapInputBufferedWriter.close();

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
			
	String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName +  System.getProperty("file.separator");
	String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator");
	
	String oneBigFileName = Commons.ALL_TF_ANNOTATIONS_FILE;
	String remapInputFile_OBased_Start_EndExclusive_GRCh37_P13 = Commons.All_TF_ANNOTATIONS_REMAP_INPUT_FILE_0BASED_START_END_EXCLUSIVE_GRCH37_P13_COORDINATES;
	
	generateOneBigTFAnnotationAndREMAPInputFile(outputFolder,oneBigFileName,remapInputFile_OBased_Start_EndExclusive_GRCh37_P13);
	
	callNCBIREMAP(dataFolder,outputFolder,remapInputFile_OBased_Start_EndExclusive_GRCh37_P13);
	


    }

}
