package giveninputdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import jaxbxjctool.AugmentationofGivenRsIdwithInformation;
import jaxbxjctool.RsInformation;
import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;
/**
 * @author burcakotlu
 * @date Mar 24, 2014 
 * @time 1:58:07 PM
 */

/**
 * 
 */
public class InputDataProcess {
	
	//for debug purposes
	static Collection nonOverLap(Collection bigger, Collection smaller) {
		   Collection result = bigger;
		   result.removeAll(smaller);
		   return result;
	}
	
	//for debug purposes
	static void printDifference(Collection difference){
		Iterator itr = difference.iterator();
		
		while(itr.hasNext()){
			
			GlanetRunner.appendLog(itr.toString());
			itr.next();
			
		}
	}
	
	
	//eutil efetch returns 0-based coordinates for given dbSNP ids 
	public static void 	readDBSNPIDs(String inputFileName, String outputFolder){
		
		//read the file line by line
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//write to output file line by line 
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
					
		String rsId = null;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFolder + Commons.PROCESSED_INPUT_FILE);
			bufferedWriter = new BufferedWriter(fileWriter);			
		
			AugmentationofGivenRsIdwithInformation app = new AugmentationofGivenRsIdwithInformation();
						
			//for debug
//			List<String> searched = new ArrayList<String>();
//			List<String> found = new ArrayList<String>();
			//for debug
			
			while((rsId = bufferedReader.readLine())!=null){
				
				//Skip comment lines
				if(!(rsId.startsWith("#"))){
					RsInformation rsInformation = app.getInformationforGivenRsId(rsId);
					bufferedWriter.write( "chr" + rsInformation.getChrNamewithoutChr() + "\t" + rsInformation.getStartZeroBased() + "\t" + rsInformation.getEndZeroBased() + System.getProperty("line.separator"));
				}//End of if not comment line							
			}
			
//			//for debug start
//			GlanetRunner.appendLog("searched size:" + "\t" + searched.size() + "\t" + "found size:" + "\t" +  found.size());
//			List<String> difference = (List<String>) nonOverLap(searched,found);
//			printDifference(difference);
//			//for debug end
			
			bufferedWriter.close();
			bufferedReader.close();
				
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
	}

	//BED format is 0-based, end is exclusive
	//In BED format, bed lines have three required fileds and nine additional optional fields.
	//chrom chr3 chrY chr2_random
	//chromStart first base in a chromosome is numbered 0.
	//chromEnd is exclusive, fisrt 100 bases of a chromosome are defined as chromStart=0 chromEnd=100, and 
	//span the bases numbered 0-99.
	public static void 	readBEDFile(String inputFileName, String outputFileFolder){
		
		//read the file line by line
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//write to output file line by line 
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		String chrName;
		int start,exclusiveEnd, inclusiveEnd;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFileFolder + Commons.PROCESSED_INPUT_FILE);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//Skip comment lines
				if(!(strLine.startsWith("#"))){
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
					
					chrName = strLine.substring(0, indexofFirstTab);
					start = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
					exclusiveEnd = Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
					
					if(!(chrName.startsWith("chr"))){
						//add "chr"
						chrName = "chr" + chrName;
					}
					
					//get the inclusive end
					inclusiveEnd = exclusiveEnd-1;
					
					bufferedWriter.write(chrName + "\t" + start +  "\t" + inclusiveEnd + System.getProperty("line.separator"));
				}//End of if not comment line	
			}
			
			bufferedWriter.close();
			bufferedReader.close();
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//@todo remove overlaps if any exists			
	}
	
	//GFF3 format is 1-based, end is inclusive
	//example GFF3 input line
	//chrX	experiment	SNP	146993388	146993388	.	-	0	cellType=HeLA
	public static void 	readGFF3File(String inputFileName, String outputFileFolder){
		
		//read the file line by line
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//write to output file line by line 
		FileWriter fileWriter = null;
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
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFileFolder + Commons.PROCESSED_INPUT_FILE);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//Skip comment lines
				if(!(strLine.startsWith("#"))){
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t',indexofFourthTab+1);
					
					chrName = strLine.substring(0, indexofFirstTab);
					oneBasedStart = Integer.parseInt(strLine.substring(indexofThirdTab+1,indexofFourthTab));
					oneBasedInclusiveEnd = Integer.parseInt(strLine.substring(indexofFourthTab+1,indexofFifthTab));
					
					if(!(chrName.startsWith("chr"))){
						//add "chr"
						chrName = "chr" + chrName;
					}
					
					//get the 0-based start
					zeroBasedStart = oneBasedStart-1;
					
					//get the 0-based inclusive end
					zeroBasedInclusiveEnd = oneBasedInclusiveEnd -1;
					
					bufferedWriter.write(chrName + "\t" + zeroBasedStart +  "\t" + zeroBasedInclusiveEnd + System.getProperty("line.separator"));
				}//End of if not comment line
				
			}
			
			bufferedWriter.close();
			bufferedReader.close();
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//@todo remove overlaps if any exists
	
	}
	
	
	//1-based coordinates, start and end are inclusive.
	//1 100 200
	//1 100
	//chr1 100
	//chr1 100 200
	public static void readOneBasedCoordinates(String inputFileName, String outputFileFolder){
		
		int zeroBasedStart;
		int zeroBasedInclusiveEnd;
		
		//read the file line by line
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//write to output file line by line 
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		
		int indexofColon;
		int indexofDot;
		int indexofHyphen;
		
		String chrName = null;
		int oneBasedStart = 0;
		int oneBasedInclusiveEnd = 0;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFileFolder + Commons.PROCESSED_INPUT_FILE);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//skip comment lines
				if(!(strLine.startsWith("#"))){
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
					
					indexofColon = strLine.indexOf(':');
					indexofDot = strLine.indexOf('.');
					indexofHyphen = strLine.indexOf('-');
				
					//19:12995239..12998702
					//19:12995239-12998702
					//chr19:12995239-12998702				
					//chr11:524690..5247000
					//there is no tab
					if(indexofColon>=0){
						chrName = strLine.substring(0, indexofColon);
						
						if(!(chrName.startsWith("chr"))){
							//add "chr"
							chrName = "chr" + chrName;
						}
						
						
						if (indexofHyphen>=0){
							oneBasedStart = Integer.parseInt(strLine.substring(indexofColon+1, indexofHyphen));
							oneBasedInclusiveEnd = Integer.parseInt(strLine.substring(indexofHyphen+1).trim());
						}else if (indexofDot>=0){
							oneBasedStart = Integer.parseInt(strLine.substring(indexofColon+1, indexofDot));
							oneBasedInclusiveEnd = Integer.parseInt(strLine.substring(indexofDot+2).trim());
						}
						//19:12995239
						//chr19:12995239				
						else {
							oneBasedStart = Integer.parseInt(strLine.substring(indexofColon+1).trim());
							oneBasedInclusiveEnd = oneBasedStart;
						}
					}
									
					//chrX 100 200
					//X 100 200
					else if (indexofSecondTab>=0 && indexofFirstTab>=0){
						
						chrName = strLine.substring(0, indexofFirstTab);
						
						if(!(chrName.startsWith("chr"))){
							//add "chr"
							chrName = "chr" + chrName;
						}
						
						oneBasedStart = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
						oneBasedInclusiveEnd = Integer.parseInt(strLine.substring(indexofSecondTab+1).trim());
					}
					//chrX 100
					//X 100
					else if (indexofFirstTab>=0){
						
						chrName = strLine.substring(0, indexofFirstTab);
						
						if(!(chrName.startsWith("chr"))){
							//add "chr"
							chrName = "chr" + chrName;
						}
						
		
						oneBasedStart = Integer.parseInt(strLine.substring(indexofFirstTab+1).trim());
						oneBasedInclusiveEnd = oneBasedStart;												
					
					}
					
					zeroBasedStart = oneBasedStart - 1;
					zeroBasedInclusiveEnd = oneBasedInclusiveEnd - 1;
					
					bufferedWriter.write(chrName + "\t" + zeroBasedStart +  "\t" + zeroBasedInclusiveEnd + System.getProperty("line.separator"));
				}//End of if not comment line
								
			}//End of while
			
			bufferedWriter.close();
			bufferedReader.close();
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//0-based coordinates, start and end are inclusive.
	//1 100 200
	//1 100
	//chr1 100
	//chr1 100 200
	public static void 	readZeroBasedCoordinates(String inputFileName, String outputFileFolder){
		
		//read the file line by line
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//write to output file line by line 
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		
		int indexofColon;
		int indexofDot;
		int indexofHyphen;
		
		String chrName = null;
		int zeroBasedStart = 0;
		int zeroBasedInclusiveEnd = 0;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFileFolder + Commons.PROCESSED_INPUT_FILE);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//skip comment lines
				if(!(strLine.startsWith("#"))){
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
					
					indexofColon = strLine.indexOf(':');
					indexofDot = strLine.indexOf('.');
					indexofHyphen = strLine.indexOf('-');
				
					//19:12995239..12998702
					//19:12995239-12998702
					//chr19:12995239-12998702				
					//chr11:524690..5247000
					//there is no tab
					if(indexofColon>=0){
						chrName = strLine.substring(0, indexofColon);
						
						if(!(chrName.startsWith("chr"))){
							//add "chr"
							chrName = "chr" + chrName;
						}
						
						
						if (indexofHyphen>=0){
							zeroBasedStart = Integer.parseInt(strLine.substring(indexofColon+1, indexofHyphen));
							zeroBasedInclusiveEnd = Integer.parseInt(strLine.substring(indexofHyphen+1).trim());
						}else if (indexofDot>=0){
							zeroBasedStart = Integer.parseInt(strLine.substring(indexofColon+1, indexofDot));
							zeroBasedInclusiveEnd = Integer.parseInt(strLine.substring(indexofDot+2).trim());
						}
						//19:12995239
						//chr19:12995239				
						else {
							zeroBasedStart = Integer.parseInt(strLine.substring(indexofColon+1).trim());
							zeroBasedInclusiveEnd = zeroBasedStart;
						}
					}
									
					//chrX 100 200
					//X 100 200
					else if (indexofSecondTab>=0 && indexofFirstTab>=0){
						
						chrName = strLine.substring(0, indexofFirstTab);
						
						if(!(chrName.startsWith("chr"))){
							//add "chr"
							chrName = "chr" + chrName;
						}
						
						zeroBasedStart = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
						zeroBasedInclusiveEnd = Integer.parseInt(strLine.substring(indexofSecondTab+1).trim());
					}
					//chrX 100
					//X 100
					else if (indexofFirstTab>=0){
						
						chrName = strLine.substring(0, indexofFirstTab);
						
						if(!(chrName.startsWith("chr"))){
							//add "chr"
							chrName = "chr" + chrName;
						}
						
						zeroBasedStart = Integer.parseInt(strLine.substring(indexofFirstTab+1).trim());
						zeroBasedInclusiveEnd = zeroBasedStart;
					
					}
					
					
					
					bufferedWriter.write(chrName + "\t" + zeroBasedStart +  "\t" + zeroBasedInclusiveEnd + System.getProperty("line.separator"));
				}//End of if not comment line
								
			}
			
			bufferedWriter.close();
			bufferedReader.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	
	}
	
	
		public static void processInputData(String[] args){
		
		String inputFileName = args[0];
		String glanetFolder = args[1];
		String inputFileFormat = args[2];
		
		//jobName starts
		String jobName = args[17].trim();
		if (jobName.isEmpty()){
			jobName = "noname";
		}
		//jobName ends
		
		String outputFolder = args[CommandLineArguments.OutputFolder.value()];
		
		
		if (inputFileFormat.equals(Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE)){
			readDBSNPIDs(inputFileName,outputFolder);	
		}else if (inputFileFormat.equals(Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE)){
			readBEDFile(inputFileName,outputFolder);
		}else if (inputFileFormat.equals(Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE)){
			readGFF3File(inputFileName,outputFolder);
		}else if (inputFileFormat.equals(Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE)){
			readZeroBasedCoordinates(inputFileName,outputFolder);
		}else if (inputFileFormat.equals(Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE)){
			readOneBasedCoordinates(inputFileName,outputFolder);
		}
	}
	
	
	
	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2]	--->	Input File Format	
	//			--->	default	Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE	
	//args[3]	--->	Annotation, overlap definition, number of bases, default 1
	//args[4]	--->	Enrichment parameter
	//			--->	default	Commons.DO_ENRICH
	//			--->			Commons.DO_NOT_ENRICH	
	//args[5]	--->	Generate Random Data Mode
	//			--->	default	Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	//			--->			Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT	
	//args[6]	--->	multiple testing parameter, enriched elements will be decided and sorted with respest to this parameter
	//			--->	default Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE
	//			--->			Commons.BONFERRONI_CORRECTED_P_VALUE
	//args[7]	--->	Bonferroni Correction Significance Level, default 0.05
	//args[8]	--->	Benjamini Hochberg FDR, default 0.05
	//args[9]	--->	Number of permutations, default 5000
	//args[10]	--->	Dnase Enrichment
	//			--->	default Commons.DO_NOT_DNASE_ENRICHMENT
	//			--->	Commons.DO_DNASE_ENRICHMENT
	//args[11]	--->	Histone Enrichment
	//			--->	default	Commons.DO_NOT_HISTONE_ENRICHMENT
	//			--->			Commons.DO_HISTONE_ENRICHMENT
	//args[12]	--->	Transcription Factor(TF) Enrichment 
	//			--->	default	Commons.DO_NOT_TF_ENRICHMENT
	//			--->			Commons.DO_TF_ENRICHMENT
	//args[13]	--->	KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_KEGGPATHWAY_ENRICHMENT
	//args[14]	--->	TF and KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	//args[15]	--->	TF and CellLine and KeggPathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	//args[16]	--->	RSAT parameter
	//			--->	default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//			--->			Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//args[17]	--->	job name example: ocd_gwas_snps 
	//args[18]	--->	writeGeneratedRandomDataMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	//			--->			Commons.WRITE_GENERATED_RANDOM_DATA
	//args[19]	--->	writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//args[20]	--->	writePermutationBasedAnnotationResultMode checkBox
	//			---> 	default	Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//args[21]	--->	number of permutations in each run	
	public static void main(String[] args) {
		
		//Read input data 
		//Process input data
		//Write output data
		processInputData(args);
	}

}
