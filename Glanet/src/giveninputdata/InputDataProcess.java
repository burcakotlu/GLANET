package giveninputdata;

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
import jaxbxjctool.RsInformation;

import org.apache.log4j.Logger;

import remap.Remap;
import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.CommandLineArguments;
import enumtypes.GivenIntervalsInputFileDataFormat;
/**
 * @author burcakotlu
 * @date Mar 24, 2014 
 * @time 1:58:07 PM
 */

/**
 * 
 */
public class InputDataProcess {
	
	final static Logger logger = Logger.getLogger(InputDataProcess.class);

	
	//for debug purposes
	static Collection<IntervalTreeNode> nonOverLap(Collection<IntervalTreeNode> bigger, Collection<IntervalTreeNode> smaller) {
		   Collection<IntervalTreeNode> result = bigger;
		   result.removeAll(smaller);
		   return result;
	}
	
	//for debug purposes
	static void printDifference(Collection<IntervalTreeNode> difference){
		Iterator<IntervalTreeNode> itr = difference.iterator();
		
		while(itr.hasNext()){
			
			GlanetRunner.appendLog(itr.toString());
			itr.next();
			
		}
	}
	
	
	//eutil efetch returns 0-based coordinates for given dbSNP ids 
	public static void 	readDBSNPIDs(String dataFolder, String inputFileName, String outputFolder){
		
		//read the file line by line
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//write to output file line by line 
		FileWriter fileWriter = null;
		FileWriter fileWriter2 = null;
		
		BufferedWriter bufferedWriter = null;
		BufferedWriter bufferedWriter2 = null;
					
		String rsId = null;
		List<String> rsIdList = new ArrayList<String>();
		
		int numberofGivenRsIds = 0;
		int numberofGivenUniqueRsIds = 0;
		
		int numberofLocisInRemapInputFile = 0;
		
	
		/**********************************************************/
		/**********NCBI REMAP PARAMETERS starts********************/
		/**********************************************************/
		String sourceAssemblyName = "";
		String targetAssemblyName = Commons.GRCH37_P13;
		
		String sourceReferenceAssemblyID = null;
		String targetReferenceAssemblyID = null;
		
		String merge = null;
		String allowMultipleLocation = null;
		double minimumRatioOfBasesThatMustBeRemapped;
		double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength;
		/**********************************************************/
		/**********NCBI REMAP PARAMETERS ends**********************/
		/**********************************************************/
		
			
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFolder + Commons.RSID_CHRNAME_0Based_START_END_HG38_FILE);
			bufferedWriter = new BufferedWriter(fileWriter);			
			
			fileWriter2 = FileOperations.createFileWriter(outputFolder + Commons.REMAP_INPUTFILE_CHRNAME_0Based_START_END_Exclusive_HG38_BED_FILE);
			bufferedWriter2 = new BufferedWriter(fileWriter2);			
			
		
			AugmentationofGivenRsIdwithInformation app = new AugmentationofGivenRsIdwithInformation();
						
			
			/*********************************************************************/
			/*****************READ GIVEN RSIDs INPUTFILE starts*******************/
			/*********************************************************************/
			while((rsId = bufferedReader.readLine())!=null){
				
				//Skip comment lines
				if(!(rsId.startsWith("#"))){
					
					numberofGivenRsIds++;
					
					//Remove rs prefix
					if (!rsIdList.contains(rsId)){
						numberofGivenUniqueRsIds++;
						rsIdList.add(rsId);
					}
				}//End of if not comment line							
			}//End of WHILE
			
			logger.debug("******************************************************************************");
			logger.debug("Number of rsIds in the given rsID input file: " + numberofGivenRsIds);
			logger.debug("Number of unique rsIds in the given rsID input file: " + numberofGivenUniqueRsIds);
			logger.debug("******************************************************************************");
			/*********************************************************************/
			/*****************READ GIVEN RSIDs INPUTFILE ends*********************/
			/*********************************************************************/
				
			
			/*********************************************************************/
			/********GET rsInformation  using NCBI EUTILS starts******************/
			/*********************************************************************/
			List<RsInformation> rsInformationList = app.getInformationforGivenRsIdList(rsIdList);
			
			logger.debug("******************************************************************************");
			logger.debug("Number of remaining rsIds after NCBI EUTIL EFETCH: " + rsInformationList.size());
			logger.debug("We have lost " + (numberofGivenUniqueRsIds- rsInformationList.size()) +  " rsIDs during NCBI EUTIL EFETCH");
			logger.debug("******************************************************************************");
			/*********************************************************************/
			/********GET rsInformation  using NCBI EUTILS ends********************/
			/*********************************************************************/

			
			
			/*********************************************************************/
			/*****************WRITE TO REMAP INPUT FILE starts********************/
			/*********************************************************************/
			for (RsInformation rsInformation: rsInformationList){
				
				if (rsInformation!=null){
					
					bufferedWriter.write(rsInformation.getRsId() + "\t" + Commons.CHR + rsInformation.getChrNamewithoutChr() + "\t" + rsInformation.getStartZeroBased() + "\t" + rsInformation.getEndZeroBased() + System.getProperty("line.separator"));
					bufferedWriter2.write(Commons.CHR + rsInformation.getChrNamewithoutChr() + "\t" + rsInformation.getStartZeroBased() + "\t" + (rsInformation.getEndZeroBased()+1) + System.getProperty("line.separator"));
					
					numberofLocisInRemapInputFile++;
					
					if (!sourceAssemblyName.contains(rsInformation.getGroupLabel())){
						sourceAssemblyName = sourceAssemblyName + rsInformation.getGroupLabel();
					}
					
				}//End of IF rsInformation is not null
				
			}//End of for
			
			logger.debug("******************************************************************************");
			logger.debug("Number of genomic loci is " + numberofLocisInRemapInputFile + " in NCBI REMAP input file in sourceAssembly " + sourceAssemblyName );
			logger.debug("******************************************************************************");
			/*********************************************************************/
			/*****************WRITE TO REMAP INPUT FILE ends**********************/
			/*********************************************************************/
		
			
				
			//Close
			bufferedReader.close();
			bufferedWriter.close();
			bufferedWriter2.close();
			
			
			//@todo check this
			//Remap.remap_show_batches(dataFolder,Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);
			
			Map<String,String> assemblyName2RefSeqAssemblyIDMap = new HashMap<String,String>();
			Remap.fillAssemblyName2RefSeqAssemblyIDMap(dataFolder,Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE,assemblyName2RefSeqAssemblyIDMap);
			
			//sourceReferenceAssemblyID = "GCF_000001405.26";
			sourceReferenceAssemblyID = assemblyName2RefSeqAssemblyIDMap.get(sourceAssemblyName);
			
			
			//targetReferenceAssemblyID = "GCF_000001405.25";
			targetReferenceAssemblyID = assemblyName2RefSeqAssemblyIDMap.get(targetAssemblyName);
			
			merge = Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON;
			allowMultipleLocation = Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON;
			minimumRatioOfBasesThatMustBeRemapped = Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_;
			maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength  = Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2;
			
		
			
			//Could not find an alignment batch for your assembly pair: GRCh38 x GRCh37.p13
			//Please run "--mode batches" for a list of available assembly pairs.
			//Remap.remap(dataFolder,"GRCh38", "GRCh37.p13", outputFolder + Commons.CHRNAME_0Based_START_Inclusive_END_Exclusive_HG38_BED_FILE, outputFolder + Commons.CHRNAME_0Based_START_Inclusive_END_Exclusive_HG19_BED_FILE);
			Remap.remap(
					dataFolder,
					sourceReferenceAssemblyID, 
					targetReferenceAssemblyID, 
					outputFolder + Commons.REMAP_INPUTFILE_CHRNAME_0Based_START_END_Exclusive_HG38_BED_FILE, 
					outputFolder + Commons.REMAP_DUMMY_OUTPUTFILE_CHRNAME_0Based_START_END_Exclusive_HG19_BED_FILE,
					outputFolder + Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE,
					outputFolder + Commons.GIVENINPUTDATA_REMAP_DUMMY_GENOME_WORKBENCH_PROJECT_FILE,
					merge,
					allowMultipleLocation,
					minimumRatioOfBasesThatMustBeRemapped,
					maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
			
			Remap.createOutputFileUsingREMAPREPORTFile(outputFolder + Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE, 
					outputFolder + Commons.FINAL_REMAP_OUTPUTFILE_CHRNAME_1Based_START_END_HG19_BED_FILE_USING_REMAP_REPORT);
			
			//@todo to be tested
			//Read from GRCh37.p13 (Hg19) bed file
			//Write to usual processed input file
			FileOperations.readFromBedFileWriteToGlanetFile(outputFolder,
					Commons.FINAL_REMAP_OUTPUTFILE_CHRNAME_1Based_START_END_HG19_BED_FILE_USING_REMAP_REPORT,
					Commons.PROCESSED_INPUT_FILE);
				
			
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
		GivenIntervalsInputFileDataFormat inputFileFormat = GivenIntervalsInputFileDataFormat.convertStringtoEnum(args[2]);
		
		//jobName starts
		String jobName = args[17].trim();
		if (jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		//jobName ends
		
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");
		String dataFolder = glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator");
		
		
		switch(inputFileFormat){
			case	INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE:
				readZeroBasedCoordinates(inputFileName,outputFolder);
				break;
			case	INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE:
				readOneBasedCoordinates(inputFileName,outputFolder);
				break;
			case	INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE:
				readDBSNPIDs(dataFolder,inputFileName,outputFolder);	
				break;
			case	INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE:
				readBEDFile(inputFileName,outputFolder);
				break;
			case	INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE:
				readGFF3File(inputFileName,outputFolder);
				break;
				
		}//End of SWITCH
		
		
	}
	
	
	
	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2]	--->	Input File Format	
	//			--->	default	Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE	
	//args[3]	--->	Annotation, overlap definition, number of bases, 
	//					default 1
	//args[4]	--->	Perform Enrichment parameter
	//			--->	default	Commons.DO_ENRICH
	//			--->			Commons.DO_NOT_ENRICH	
	//args[5]	--->	Generate Random Data Mode
	//			--->	default	Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	//			--->			Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT	
	//args[6]	--->	multiple testing parameter, enriched elements will be decided and sorted with respect to this parameter
	//			--->	default Commons.BENJAMINI_HOCHBERG_FDR
	//			--->			Commons.BONFERRONI_CORRECTION
	//args[7]	--->	Bonferroni Correction Significance Level, default 0.05
	//args[8]	--->	Bonferroni Correction Significance Criteria, default 0.05
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
	//args[21]  --->    number of permutations in each run. Default is 2000
	//args[22]  --->	UserDefinedGeneSet Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT
	//							Commons.DO_USER_DEFINED_GENESET_ENRICHMENT
	//args[23]	--->	UserDefinedGeneSet InputFile 
	//args[24]	--->	UserDefinedGeneSet GeneInformationType
	//					default Commons.GENE_ID
	//							Commons.GENE_SYMBOL
	//							Commons.RNA_NUCLEOTIDE_ACCESSION
	//args[25]	--->	UserDefinedGeneSet	Name
	//args[26]	--->	UserDefinedGeneSet 	Optional GeneSet Description InputFile
	//args[27]  --->	UserDefinedLibrary Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT
	//						 	Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT
	//args[28]  --->	UserDefinedLibrary InputFile
	//args[29] - args[args.length-1]  --->	Note that the selected cell lines are
	//					always inserted at the end of the args array because it's size
	//					is not fixed. So for not (until the next change on args array) the selected cell
	//					lines can be reached starting from 22th index up until (args.length-1)th index.
	//					If no cell line selected so the args.length-1 will be 22-1 = 21. So it will never
	//					give an out of boundry exception in a for loop with this approach.
	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		
		//jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if (jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		//jobName ends
		
		
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");

		
		
		/********************************************************************/
		/***********delete old files starts**********************************/
		String givenInputDataOutputBaseDirectoryName = outputFolder + Commons.GIVENINPUTDATA;
		
		FileOperations.deleteOldFiles(givenInputDataOutputBaseDirectoryName);
		/***********delete old files ends***********************************/
		/******************************************************************/
	
		
		//Read input data 
		//Process input data
		//Write output data
		processInputData(args);
	}

}
