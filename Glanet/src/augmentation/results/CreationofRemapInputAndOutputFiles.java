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

import remap.Remap;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.EnrichmentType;

/****************************************************
 * 													*
 * 													*
 * Creation of REMAP Input Files					*
 * with 0 based 									*
 * Start Inclusive									*
 * End Exclusive									*
 * GRCh37.p13										*
 * coordinates										*
 * 													*
 * 													*
 ****************************************************/
public class CreationofRemapInputAndOutputFiles {
	
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
		
	
	public static void callREMAPAndWriteREMAPOutputFiles(String outputFolder,
			String dataFolder,
			EnrichmentType tfEnrichment, 
			EnrichmentType tfKeggPathwayEnrichment, 
			EnrichmentType tfCellLineKeggPathwayEnrichment){
		
		
		//@todo check this
		//Remap.remap_show_batches(dataFolder,Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);
		//Map<String,String> assemblyName2RefSeqAssemblyIDMap = new HashMap<String,String>();
		//Remap.fillAssemblyName2RefSeqAssemblyIDMap(dataFolder,Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE,assemblyName2RefSeqAssemblyIDMap);
		
		String sourceReferenceAssemblyID = "GCF_000001405.25";
		//In fact targetReferenceAssemblyID must be the assemblyName that NCBI ETILS returns (groupLabel)
		String targetReferenceAssemblyID = "GCF_000001405.26";
		
		String merge = Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_OFF;
		String allowMultipleLocation = Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_OFF;
		double minimumRatioOfBasesThatMustBeRemapped = Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_1;
		double	maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength  = Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_1;
		
	
		
		
		

		 if (tfEnrichment.isTfEnrichment()){
			  Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_OUTPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_RESULTS_CHRNUMBER_1BASED_START_END_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
			
		 }
		 
		
	     if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()){	   
			  Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_OUTPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASED_START_END_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
			  
			  Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_OUTPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASED_START_END_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
  
			  
			  
			  Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_OUTPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASED_START_END_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
  
     }
		
	     if (tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
	   	  Remap.remap(
					dataFolder,
					sourceReferenceAssemblyID, 
					targetReferenceAssemblyID, 
					outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
					outputFolder + Commons.REMAP_OUTPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASED_START_END_GRCH38_COORDINATES,
					merge,
					allowMultipleLocation,
					minimumRatioOfBasesThatMustBeRemapped,
					maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
		  
		  Remap.remap(
					dataFolder,
					sourceReferenceAssemblyID, 
					targetReferenceAssemblyID, 
					outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
					outputFolder + Commons.REMAP_OUTPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASED_START_END_GRCH38_COORDINATES,
					merge,
					allowMultipleLocation,
					minimumRatioOfBasesThatMustBeRemapped,
					maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);

		  
		  
		  Remap.remap(
					dataFolder,
					sourceReferenceAssemblyID, 
					targetReferenceAssemblyID, 
					outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
					outputFolder + Commons.REMAP_OUTPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASED_START_END_GRCH38_COORDINATES,
					merge,
					allowMultipleLocation,
					minimumRatioOfBasesThatMustBeRemapped,
					maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
	     }
		
	}
	
	
	public static void readandCreateREMAPInputFiles(String outputFolder, 
			EnrichmentType tfEnrichment, 
			EnrichmentType tfKeggPathwayEnrichment, 
			EnrichmentType tfCellLineKeggPathwayEnrichment){
		
		 
		
		 if (tfEnrichment.isTfEnrichment()){
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
		 }
		 
		
	     if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()){	   
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);					
	     }
		
	     if (tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);					

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
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator");
		
		EnrichmentType tfEnrichment 		= EnrichmentType.convertStringtoEnum(args[12]);
		EnrichmentType tfKeggPathwayEnrichment 			= EnrichmentType.convertStringtoEnum(args[14]);
		EnrichmentType tfCellLineKeggPathwayEnrichment 	= EnrichmentType.convertStringtoEnum(args[15]);
		
		
			
		//delete old files starts 
		FileOperations.deleteOldFiles(outputFolder + Commons.AUGMENTATION_REMAP_INPUT_OUTPUT_DIRECTORY);
		//delete old files ends
	
		readandCreateREMAPInputFiles(outputFolder,tfEnrichment,tfKeggPathwayEnrichment,tfCellLineKeggPathwayEnrichment);
		
		callREMAPAndWriteREMAPOutputFiles(outputFolder,dataFolder,tfEnrichment,tfKeggPathwayEnrichment,tfCellLineKeggPathwayEnrichment);
		
	}

}
