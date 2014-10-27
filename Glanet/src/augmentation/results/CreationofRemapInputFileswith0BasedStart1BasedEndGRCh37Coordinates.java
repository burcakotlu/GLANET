/**
 * @author burcakotlu
 * @date Aug 25, 2014 
 * @time 11:00:56 AM
 */
package augmentation.results;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.EnrichmentType;

/**
 * 
 */
public class CreationofRemapInputFileswith0BasedStart1BasedEndGRCh37Coordinates {
	
	public static void readResultsandWriteVersion2(String outputFolder, String inputFileName, String outputFileName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine= null;
		
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
		
		String givenIntervalChrName;		
		int givenIntervalZeroBasedStart;
		int givenIntervalOneBasedEnd;		
	
//		String tfCellLineChrName;
		int tfCellLineZeroBasedStart;
		int tfCellLineOneBasedEnd;	
		
//		String refseqGeneChrName;
		int refseqGeneZeroBasedStart;
		int refseqGeneOneBasedEnd;	
			
		try {
			fileReader = new FileReader(outputFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				if(!strLine.contains("Search") && !strLine.startsWith("*")){
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
					indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
					indexofSeventhTab	= strLine.indexOf('\t', indexofSixthTab+1);
					indexofEigthTab		= strLine.indexOf('\t', indexofSeventhTab+1);
					indexofNinethTab	= strLine.indexOf('\t', indexofEigthTab+1);
					indexofTenthTab		= strLine.indexOf('\t', indexofNinethTab+1);
					
					givenIntervalChrName = strLine.substring(indexofFirstTab+1, indexofSecondTab);
					givenIntervalZeroBasedStart = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					givenIntervalOneBasedEnd = Integer.parseInt(strLine.substring(indexofThirdTab+1, indexofFourthTab));
												
//					tfCellLineChrName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
					tfCellLineZeroBasedStart = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
					tfCellLineOneBasedEnd = Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
					
//					refseqGeneChrName = strLine.substring(indexofSeventhTab+1, indexofEigthTab);
					refseqGeneZeroBasedStart = Integer.parseInt(strLine.substring(indexofEigthTab+1, indexofNinethTab));
					refseqGeneOneBasedEnd = Integer.parseInt(strLine.substring(indexofNinethTab+1, indexofTenthTab));
					
					bufferedWriter.write(givenIntervalChrName +"\t" + givenIntervalZeroBasedStart +"\t" + givenIntervalOneBasedEnd + System.getProperty("line.separator"));
					bufferedWriter.write(givenIntervalChrName +"\t" + tfCellLineZeroBasedStart +"\t" + tfCellLineOneBasedEnd + System.getProperty("line.separator"));
					bufferedWriter.write(givenIntervalChrName +"\t" + refseqGeneZeroBasedStart +"\t" + refseqGeneOneBasedEnd + System.getProperty("line.separator"));
					
				}//End of if			
			}//End of while
			
			
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public static void readResultsandWrite(String outputFolder, String inputFileName, String outputFileName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine= null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
		String givenIntervalChrName;		
		int givenIntervalZeroBasedStart;
		int givenIntervalOneBasedEnd;		
	
		String overlapChrName;
		int overlapZeroBasedStart;
		int overlapOneBasedEnd;	
		
		
		try {
			fileReader = new FileReader(outputFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				if(!strLine.contains("Search") && !strLine.startsWith("*")){
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
					indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
					indexofSeventhTab	= strLine.indexOf('\t', indexofSixthTab+1);
					
					givenIntervalChrName = strLine.substring(indexofFirstTab+1, indexofSecondTab);
					givenIntervalZeroBasedStart = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					givenIntervalOneBasedEnd = Integer.parseInt(strLine.substring(indexofThirdTab+1, indexofFourthTab));
												
					overlapChrName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
					overlapZeroBasedStart = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
					overlapOneBasedEnd = Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
					
					bufferedWriter.write(givenIntervalChrName +"\t" + givenIntervalZeroBasedStart +"\t" + givenIntervalOneBasedEnd + System.getProperty("line.separator"));
					bufferedWriter.write(overlapChrName +"\t" + overlapZeroBasedStart +"\t" + overlapOneBasedEnd + System.getProperty("line.separator"));
					
				}//End of if			
			}//End of while
			
			
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
		
	
	public static void readandCreateFiles(String outputFolder, EnrichmentType dnaseEnrichment, EnrichmentType histoneEnrichment, EnrichmentType tfEnrichment, EnrichmentType keggPathwayEnrichment, EnrichmentType tfKeggPathwayEnrichment, EnrichmentType tfCellLineKeggPathwayEnrichment){
		if (dnaseEnrichment.isDnaseEnrichment()){
			readResultsandWrite(outputFolder, Commons.AUGMENTED_DNASE_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.LINE_BY_LINE_AUGMENTED_DNASE_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
		 }
		 
		 if (histoneEnrichment.isHistoneEnrichment()){
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_HISTONE_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_HISTONE_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
		 }
		 
		 if (tfEnrichment.isTfEnrichment()){
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
		 }
		 
		 if (keggPathwayEnrichment.isKeggPathwayEnrichment()){
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);					
		}
		
	     if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()){	   
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);					
	     }
		
	     if (tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);					

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
		String glanetFolder = args[1];
		
		//jobName starts
		String jobName = args[17].trim();
		if (jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		//jobName ends
				
				
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName +  System.getProperty("file.separator");
		
		EnrichmentType dnaseEnrichment 		= EnrichmentType.convertStringtoEnum(args[10]);
		EnrichmentType histoneEnrichment  	= EnrichmentType.convertStringtoEnum(args[11]);
		EnrichmentType tfEnrichment 		= EnrichmentType.convertStringtoEnum(args[12]);
		EnrichmentType keggPathwayEnrichment  			= EnrichmentType.convertStringtoEnum(args[13]);
		EnrichmentType tfKeggPathwayEnrichment 			= EnrichmentType.convertStringtoEnum(args[14]);
		EnrichmentType tfCellLineKeggPathwayEnrichment 	= EnrichmentType.convertStringtoEnum(args[15]);
		
		//User Defined GeneSet Enrichment, DO or DO_NOT
//		EnrichmentType userDefinedGeneSetEnrichmentType = EnrichmentType.convertStringtoEnum(args[22]);
		
		//User Defined Library Enrichment, DO or DO_NOT
//		EnrichmentType userDefinedLibraryEnrichmentType = EnrichmentType.convertStringtoEnum(args[27]);

		
		//delete old files starts 
		FileOperations.deleteOldFiles(outputFolder + Commons.AUGMENTED_ENRICHED_ELEMENTS_WITH_GIVEN_INPUT_DATA_REMAP_DIRECTORY);
		//delete old files ends
	
		CreationofRemapInputFileswith0BasedStart1BasedEndGRCh37Coordinates.readandCreateFiles(outputFolder,dnaseEnrichment,histoneEnrichment,tfEnrichment,keggPathwayEnrichment,tfKeggPathwayEnrichment,tfCellLineKeggPathwayEnrichment);
	}

}
