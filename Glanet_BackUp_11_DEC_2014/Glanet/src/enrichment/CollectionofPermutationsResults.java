/**
 * @author Burcak Otlu
 * Feb 13, 2014
 * 1:15:47 PM
 * 2014
 *
 * 
 */
package enrichment;

import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import multipletesting.BenjaminiandHochberg;
import userdefined.geneset.UserDefinedGeneSetUtility;
import userdefined.library.UserDefinedLibraryUtility;
import augmentation.keggpathway.KeggPathwayAugmentation;
import auxiliary.FileOperations;
import auxiliary.FunctionalElement;
import auxiliary.NumberofComparisons;

import common.Commons;

import enumtypes.EnrichmentType;
import enumtypes.GeneratedMixedNumberDescriptionOrderLength;
import enumtypes.MultipleTestingType;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;




public class CollectionofPermutationsResults {
	
	//How to decide enriched elements?
	//with respect to Benjamini Hochberg FDR or
	//with respect to Bonferroni Correction Significance Level
	public static void writeResultstoOutputFiles(String outputFolder,String fileName,String jobName,List<FunctionalElement> list,EnrichmentType enrichmentType,MultipleTestingType multipleTestingParameter,float bonferroniCorrectionSignigicanceLevel,float FDR,int numberofPermutations,int numberofComparisons) throws IOException{
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		FunctionalElement element = null;
		DecimalFormat df = new DecimalFormat("0.######E0");
		
		
		/*********************************************************************************/
		/**********MULTIPLE TESTING W.R.T. BENJAMINI HOCHBERG starts**********************/
		if(multipleTestingParameter.isBenjaminiHochbergFDR()){
			
			//sort w.r.t. Benjamini and Hochberg FDR Adjusted pValue
			Collections.sort(list,FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
			
			//write the results to a output file starts		
			fileWriter = FileOperations.createFileWriter(outputFolder + fileName  +   "_" + jobName +Commons.ALL_WITH_RESPECT_TO_BH_FDR_ADJUSTED_P_VALUE);
		}
		/*********************************************************************************/
		/**********MULTIPLE TESTING W.R.T. BENJAMINI HOCHBERG ends************************/
		
		
		
		/*********************************************************************************/
		/**********MULTIPLE TESTING W.R.T. BONFERRONI CORRECTION starts*******************/
		else if (multipleTestingParameter.isBonferroniCorrection()){
			
			//sort w.r.t. Bonferroni Corrected pVlaue
			Collections.sort(list,FunctionalElement.BONFERRONI_CORRECTED_P_VALUE);
			
			//write the results to a output file starts		
			fileWriter = FileOperations.createFileWriter(outputFolder + fileName  + "_" + jobName+ Commons.ALL_WITH_RESPECT_TO_BONF_CORRECTED_P_VALUE);
		}
		/*********************************************************************************/
		/**********MULTIPLE TESTING W.R.T. BONFERRONI CORRECTION ends*********************/
	
		/*************************************************************************************************/	
		/*****************Common  for BenjaminiHochberg and BonferroniCorrection starts*******************/
		bufferedWriter = new BufferedWriter(fileWriter);
		
		//header line in output file
		bufferedWriter.write("Element Number" + "\t" +  "Element Name" + "\t" + "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in " + numberofPermutations + " Permutations" + "\t"+ "Number of Permutations" + "\t" + "Number of comparisons for Bonferroni Correction" + "\t" + "empiricalPValue" + "\t" + "BonfCorrPValue for " +  numberofComparisons  + " comparisons"  + "\t" + "BH FDR Adjusted P Value" + "\t" + "Reject Null Hypothesis for an FDR of " + FDR +System.getProperty("line.separator"));

		Iterator<FunctionalElement> itr = list.iterator();
		
		while(itr.hasNext()){
			element = itr.next();
			
			
			if(	enrichmentType.isKeggPathwayEnrichment() ||
				enrichmentType.isTfKeggPathwayEnrichment() ||
				enrichmentType.isTfCellLineKeggPathwayEnrichment()){
				
				//line per element in output file
				bufferedWriter.write(element.getNumber() + "\t" + element.getName() + "\t" +  element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t"+ numberofPermutations+ "\t" +numberofComparisons + "\t" + df.format(element.getEmpiricalPValue()) + "\t" + df.format(element.getBonferroniCorrectedEmpiricalPValue()) + "\t" + df.format(element.getBH_FDR_adjustedPValue()) + "\t" + element.isRejectNullHypothesis() +"\t");
				
				
				bufferedWriter.write(element.getKeggPathwayName()+"\t");
				
				
				if (element.getKeggPathwayGeneIdList().size()>=1){
					int i;
					//Write the gene ids of the kegg pathway
					for(i =0 ;i < element.getKeggPathwayGeneIdList().size()-1; i++){
						bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + ", ");
					}
					bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + "\t");
				}
				
				if(element.getKeggPathwayAlternateGeneNameList().size()>=1){
					int i;
					
					//Write the alternate gene names of the kegg pathway
					for(i =0 ;i < element.getKeggPathwayAlternateGeneNameList().size()-1; i++){
						bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + ", ");
					}
					bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i));			
				}
				
				bufferedWriter.write(System.getProperty("line.separator"));			

		
			}else if (enrichmentType.isUserDefinedGeneSetEnrichment()){
				//line per element in output file
				bufferedWriter.write(element.getNumber() + "\t" + element.getName()  +"\t" +  element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t"+ numberofPermutations+ "\t" +numberofComparisons + "\t" + df.format(element.getEmpiricalPValue()) + "\t" + df.format(element.getBonferroniCorrectedEmpiricalPValue()) + "\t" + df.format(element.getBH_FDR_adjustedPValue()) + "\t" + element.isRejectNullHypothesis() + "\t");
				
				bufferedWriter.write(element.getUserDefinedGeneSetDescription()+ System.getProperty("line.separator"));
				
			}else{
				//line per element in output file
				bufferedWriter.write(element.getNumber() + "\t" + element.getName() + "\t" +  element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t"+ numberofPermutations+ "\t" +numberofComparisons + "\t" + df.format(element.getEmpiricalPValue()) + "\t" + df.format(element.getBonferroniCorrectedEmpiricalPValue()) + "\t" + df.format(element.getBH_FDR_adjustedPValue()) + "\t" + element.isRejectNullHypothesis() +System.getProperty("line.separator"));
	
			}
			
		
		}//End of while
		
		//close the file
		bufferedWriter.close();
		/*************************************************************************************************/	
		/*****************Common  for BenjaminiHochberg and BonferroniCorrection ends*********************/
	
	}

	
	
	
	
	public static String convertGeneratedMixedNumberToName(
			long modifiedMixedNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength, 
			TIntObjectMap<String> cellLineNumber2NameMap,
			TIntObjectMap<String> tfNumber2NameMap,
			TIntObjectMap<String> histoneNumber2NameMap, 
			TIntObjectMap<String> userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap,
			TIntObjectMap<String> userDefinedLibraryElementNumber2ElementNameMap,
			TIntObjectMap<String> keggPathwayNumber2NameMap){
	
		int cellLineNumber;
		int tfNumber;
		int histoneNumber;
		int keggPathwayNumber;
		int userDefinedGeneSetNumber;
		
		int userDefinedLibraryElementNumber;
		
		
		String cellLineName;
		String tfName;
		String histoneName;
		String keggPathwayName;
		String userDefinedGeneSetName;
		
		String userDefinedLibraryElementName;
		
		switch(generatedMixedNumberDescriptionOrderLength){
			case INT_4DIGIT_DNASECELLLINENUMBER:{
												cellLineNumber = IntervalTree.getCellLineNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
												cellLineName = cellLineNumber2NameMap.get(cellLineNumber);
												return cellLineName;	
										}
			case INT_5DIGIT_USERDEFINEDGENESETNUMBER:{
											userDefinedGeneSetNumber =  IntervalTree.getGeneSetNumber(modifiedMixedNumber, generatedMixedNumberDescriptionOrderLength);
											userDefinedGeneSetName = userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap.get(userDefinedGeneSetNumber);
											return userDefinedGeneSetName;
											
										}
			case INT_4DIGIT_KEGGPATHWAYNUMBER:{	keggPathwayNumber = IntervalTree.getGeneSetNumber(modifiedMixedNumber, generatedMixedNumberDescriptionOrderLength);
											 	keggPathwayName = keggPathwayNumber2NameMap.get(keggPathwayNumber);
											 	return keggPathwayName;
										}

			case INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER: 
										{	 tfNumber = IntervalTree.getElementNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
											 tfName = tfNumber2NameMap.get(tfNumber);
											 cellLineNumber = IntervalTree.getCellLineNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
											 cellLineName = cellLineNumber2NameMap.get(cellLineNumber);
											 return tfName + "_" + cellLineName;																					
											}
												
			case INT_4DIGIT_HISTONENUMBER_4DIGIT_CELLLINENUMBER:
										{	 	histoneNumber = IntervalTree.getElementNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
												histoneName = histoneNumber2NameMap.get(histoneNumber);
												 
												cellLineNumber = IntervalTree.getCellLineNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
												cellLineName = cellLineNumber2NameMap.get(cellLineNumber);
												 
												return histoneName + "_" + cellLineName;	
												
										}
		
				
			case INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER:{		
													tfNumber = IntervalTree.getElementNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
													tfName = tfNumber2NameMap.get(tfNumber);
													
													keggPathwayNumber = IntervalTree.getGeneSetNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
												 	keggPathwayName = keggPathwayNumber2NameMap.get(keggPathwayNumber);
												 	
												 	return tfName  + "_" + keggPathwayName;	
											}
			case LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER:{
																tfNumber = IntervalTree.getElementNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
																tfName = tfNumber2NameMap.get(tfNumber);
																
																cellLineNumber = IntervalTree.getCellLineNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
																cellLineName = cellLineNumber2NameMap.get(cellLineNumber);
																
																keggPathwayNumber = IntervalTree.getGeneSetNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
															 	keggPathwayName = keggPathwayNumber2NameMap.get(keggPathwayNumber);
															 	
															 	return tfName + "_" + cellLineName + "_" + keggPathwayName;		 
																}
			
			
			case INT_6DIGIT_ELEMENTNUMBER:{
						userDefinedLibraryElementNumber = IntervalTree.getElementNumber(modifiedMixedNumber, generatedMixedNumberDescriptionOrderLength);
						userDefinedLibraryElementName = userDefinedLibraryElementNumber2ElementNameMap.get(userDefinedLibraryElementNumber);
						return userDefinedLibraryElementName;
					}
			default:{
						break;
					}
										
		}
		

		return null;
	}
	
	
	public static void collectPermutationResults(
			int numberofPermutationsInEachRun,
			float bonferroniCorrectionSignigicanceLevel, 
			float FDR, 
			MultipleTestingType multipleTestingParameter, 
			String dataFolder, 
			String outputFolder,
			String runFileName,
			String allFileName,
			String jobName, 
			int numberofRuns, 
			int numberofRemainders, 
			int numberofComparisons, 
			EnrichmentType enrichmentType,
			String userDefinedGeneSetOptionalDescriptionInputFile,
			String elementType,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
			
		String strLine;
		String tempRunName;
		
		int indexofTab;
		int indexofPipe;
		int indexofFormerComma;
		int indexofLatterComma;
		
		int originalNumberofOverlaps;
		int permutationNumberofOverlaps = Integer.MAX_VALUE;
		
		int numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps;
		float empiricalPValue;
		float bonferroniCorrectedEmpiricalPValue;
		
		FunctionalElement element;
		long mixedNumber;		
		String tforHistoneNameCellLineNameKeggPathwayName;
		
		//In case of functionalElement contains kegg pathway
		int keggPathwayNumber;	
	
		Map<Long,FunctionalElement> elementNumber2ElementMap = new  HashMap<Long,FunctionalElement>();	
		
		int numberofPermutations;
		
		if (numberofRemainders>0){
			numberofPermutations = ((numberofRuns-1) * numberofPermutationsInEachRun) + numberofRemainders;
		}else{
			numberofPermutations = numberofRuns * numberofPermutationsInEachRun;	
		}
		
		
		
		/**********************************************************************************/		
		/***********************FILL NUMBER TO NAME MAP STARTS*****************************/	
		TIntObjectMap<String> cellLineNumber2NameMap = null;
		TIntObjectMap<String> tfNumber2NameMap = null;
		TIntObjectMap<String> histoneNumber2NameMap = null;
		TIntObjectMap<String> geneHugoSymbolNumber2NameMap = null;
		TIntObjectMap<String> keggPathwayNumber2NameMap = null;
		TIntObjectMap<String> userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap = null;
		TIntObjectMap<String> userDefinedLibraryElementNumber2ElementNameMap 		= null;
		
		
		//Here using same variable name "cellLineNumber2NameMap" for case DO_DNASE_ENRICHMENT and case DO_TF_ENRICHMENT is not important
		//How it is filled is important, I mean its source
		//For example in case DO_DNASE_ENRICHMENT cellLineNumber2NameMap is filled by using ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME 
		//For example in case DO_TF_ENRICHMENT cellLineNumber2NameMap is filled by using ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME 
		
		switch(enrichmentType){
		
			case DO_DNASE_ENRICHMENT:	{
											cellLineNumber2NameMap = new TIntObjectHashMap<String>();
											FileOperations.fillNumber2NameMap(cellLineNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
											break;
										}
			
			case DO_TF_ENRICHMENT:		{
											cellLineNumber2NameMap = new TIntObjectHashMap<String>();
											tfNumber2NameMap = new TIntObjectHashMap<String>();
											FileOperations.fillNumber2NameMap(cellLineNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
											FileOperations.fillNumber2NameMap(tfNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
											break;
										}
			
			case DO_HISTONE_ENRICHMENT:{
											cellLineNumber2NameMap = new TIntObjectHashMap<String>();
											histoneNumber2NameMap = new TIntObjectHashMap<String>();
											FileOperations.fillNumber2NameMap(cellLineNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
											FileOperations.fillNumber2NameMap(histoneNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_HISTONE_NUMBER_2_NAME_OUTPUT_FILENAME);
											break;
										}
			
			case DO_KEGGPATHWAY_ENRICHMENT:	{
												geneHugoSymbolNumber2NameMap = new TIntObjectHashMap<String>();
												keggPathwayNumber2NameMap = new TIntObjectHashMap<String>();
												FileOperations.fillNumber2NameMap(geneHugoSymbolNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NUMBER_2_NAME_OUTPUT_FILENAME);
												FileOperations.fillNumber2NameMap(keggPathwayNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
												break;
											}
			case DO_TF_KEGGPATHWAY_ENRICHMENT:	{
													tfNumber2NameMap = new TIntObjectHashMap<String>();
													geneHugoSymbolNumber2NameMap = new TIntObjectHashMap<String>();
													keggPathwayNumber2NameMap = new TIntObjectHashMap<String>();
													FileOperations.fillNumber2NameMap(tfNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
													FileOperations.fillNumber2NameMap(geneHugoSymbolNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NUMBER_2_NAME_OUTPUT_FILENAME);
													FileOperations.fillNumber2NameMap(keggPathwayNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
													break;
												}
			
			case DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT: {
															cellLineNumber2NameMap = new TIntObjectHashMap<String>();
															tfNumber2NameMap = new TIntObjectHashMap<String>();
															geneHugoSymbolNumber2NameMap = new TIntObjectHashMap<String>();
															keggPathwayNumber2NameMap = new TIntObjectHashMap<String>();
															FileOperations.fillNumber2NameMap(cellLineNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
															FileOperations.fillNumber2NameMap(tfNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
															FileOperations.fillNumber2NameMap(geneHugoSymbolNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NUMBER_2_NAME_OUTPUT_FILENAME);
															FileOperations.fillNumber2NameMap(keggPathwayNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
															break;
														}
			case DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT:{
																						cellLineNumber2NameMap = new TIntObjectHashMap<String>();
																						tfNumber2NameMap = new TIntObjectHashMap<String>();
																						geneHugoSymbolNumber2NameMap = new TIntObjectHashMap<String>();
																						keggPathwayNumber2NameMap = new TIntObjectHashMap<String>();
																						FileOperations.fillNumber2NameMap(cellLineNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
																						FileOperations.fillNumber2NameMap(tfNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
																						FileOperations.fillNumber2NameMap(geneHugoSymbolNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NUMBER_2_NAME_OUTPUT_FILENAME);
																						FileOperations.fillNumber2NameMap(keggPathwayNumber2NameMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
																						break;
																				}
			case DO_USER_DEFINED_GENESET_ENRICHMENT:{
														userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap = new TIntObjectHashMap<String>();
														FileOperations.fillNumber2NameMap(userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap,dataFolder + Commons.ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_USERDEFINEDGENESET_NUMBER_2_NAME_OUTPUT_FILENAME);
														break;
													}
			
			case DO_USER_DEFINED_LIBRARY_ENRICHMENT:{
														userDefinedLibraryElementNumber2ElementNameMap 		= new TIntObjectHashMap<String>();
													
														
														
														//Fill elmentType based elementNumber2ElementNameMap
														UserDefinedLibraryUtility.fillNumber2NameMap(
																userDefinedLibraryElementNumber2ElementNameMap,
																dataFolder,
																Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME + elementType + System.getProperty("file.separator") ,
																Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENT_NUMBER_2_NAME_OUTPUT_FILENAME);
														
														break;
													}
			default:	{
							break;
						}
								
		}//End of Switch
		/***********************FILL NUMBER TO NAME MAP ENDS*******************************/		
		/**********************************************************************************/		
		

		try {
			
			
			/**********************************************************************************/		
			/***********************FOR EACH RUN STARTS****************************************/		
			for (int i=1; i<=numberofRuns; i++){
				
					tempRunName =  "_" + jobName +  "_" + Commons.RUN + i;
					
					fileReader = new FileReader(outputFolder + runFileName + tempRunName  + ".txt" );
					bufferedReader = new BufferedReader(fileReader);
					
					//Outer While
					while((strLine = bufferedReader.readLine())!=null){
						
						//Initialize numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps to zero
						numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = 0;
						
						indexofTab = strLine.indexOf('\t');
						indexofPipe = strLine.indexOf('|');
						
						mixedNumber = Long.parseLong(strLine.substring(0,indexofTab));
						
						//debug starts
						if (mixedNumber<0){
							System.out.println("there is a situation 1");
						}
						//debug ends
							

//						mixedNumber can be in one of these formats
//						INT_4DIGIT_DNASECELLLINENUMBER
//						INT_5DIGIT_USERDEFINEDGENESETNUMBER
//						INT_4DIGIT_KEGGPATHWAYNUMBER
//						INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER
//						INT_4DIGIT_HISTONENUMBER_4DIGIT_CELLLINENUMBER
//						INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER
//						LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER

						originalNumberofOverlaps = Integer.parseInt(strLine.substring(indexofTab+1,indexofPipe));
						
						indexofFormerComma = indexofPipe;
						indexofLatterComma = strLine.indexOf(',', indexofFormerComma+1);
						
						//Inner While
						while(indexofFormerComma!= -1 && indexofLatterComma!= -1){
							permutationNumberofOverlaps = Integer.parseInt(strLine.substring(indexofFormerComma+1, indexofLatterComma));
							
							if(permutationNumberofOverlaps >= originalNumberofOverlaps){
								numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps++;
							}
							
							indexofFormerComma = indexofLatterComma;
							indexofLatterComma = strLine.indexOf(',', indexofLatterComma+1);
							
						}//End of Inner While loop: all permutations, number of overlaps of an element
						
						
						//write numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps to map
						if(elementNumber2ElementMap.get(mixedNumber)==null){
							element = new FunctionalElement();
							
							element.setNumber(mixedNumber);
							
							tforHistoneNameCellLineNameKeggPathwayName = convertGeneratedMixedNumberToName(mixedNumber,generatedMixedNumberDescriptionOrderLength,cellLineNumber2NameMap,tfNumber2NameMap,histoneNumber2NameMap,userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap,userDefinedLibraryElementNumber2ElementNameMap,keggPathwayNumber2NameMap);
							element.setName(tforHistoneNameCellLineNameKeggPathwayName);
							
							//in case of element contains a KEGG PATHWAY
							//We are setting keggPathwayNumber for KEGGPATHWAY INFORMATION augmentation
							//such as KEGGPathway description, geneID List of genes, hugoSymbols of genes in this KEGG Pathway
							if (generatedMixedNumberDescriptionOrderLength.is_INT_4DIGIT_KEGGPATHWAYNUMBER() || 
								generatedMixedNumberDescriptionOrderLength.is_INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER() || 
								generatedMixedNumberDescriptionOrderLength.is_LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER()){
								
								keggPathwayNumber = IntervalTree.getGeneSetNumber(mixedNumber,generatedMixedNumberDescriptionOrderLength);
								element.setKeggPathwayNumber(keggPathwayNumber);
							}
							//set keggPathwayNumber
									
							element.setOriginalNumberofOverlaps(originalNumberofOverlaps);
							element.setNumberofPermutationsHavingOverlapsGreaterThanorEqualto(numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps);
							
							elementNumber2ElementMap.put(mixedNumber, element);
						}else{
							
							element = elementNumber2ElementMap.get(mixedNumber);
							
							element.setNumberofPermutationsHavingOverlapsGreaterThanorEqualto(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps );
						}
												
					}//End of Outer While loop: Read all lines of a run					
					
					//Close bufferedReader
					bufferedReader.close();
					fileReader.close();
									
			}//End of for: each run
			/***********************FOR EACH RUN ENDS******************************************/		
			/**********************************************************************************/		

			
			
			/**********************************************************************************/		
			/******COMPUTE EMPIRICAL P VALUE AND BONFERRONI CORRECTED P VALUE STARTS***********/		
			//Now compute empirical pValue and Bonferroni Corrected pValue and write
			for(Map.Entry<Long, FunctionalElement> entry: elementNumber2ElementMap.entrySet()){
				
				mixedNumber = entry.getKey();
				element 	= entry.getValue();
				
				numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto();
				
				empiricalPValue = (numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps *1.0f)/numberofPermutations;
				bonferroniCorrectedEmpiricalPValue = ((numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps*1.0f)/numberofPermutations) * numberofComparisons;
				
				if (bonferroniCorrectedEmpiricalPValue>1.0f){
					bonferroniCorrectedEmpiricalPValue = 1.0f;
				}
										
				element.setEmpiricalPValue(empiricalPValue);
				element.setBonferroniCorrectedEmpiricalPValue(bonferroniCorrectedEmpiricalPValue);			
	
			}
			/******COMPUTE EMPIRICAL P VALUE AND BONFERRONI CORRECTED P VALUE ENDS************/		
			/**********************************************************************************/		

			
			
			
			/**********************************************************************************/		
			/************COMPUTE BENJAMINI HOCHBERG FDR ADJUSTED P VALUE STARTS****************/		
			//convert map.values() into a list
			//sort w.r.t. empirical p value
			//before calculating BH FDR adjusted p values
			List<FunctionalElement> list = new ArrayList<FunctionalElement>(elementNumber2ElementMap.values());
			
			Collections.sort(list,FunctionalElement.EMPIRICAL_P_VALUE);
			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(list, FDR);
			//sort w.r.t. Benjamini and Hochberg FDR
			Collections.sort(list,FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
			/************COMPUTE BENJAMINI HOCHBERG FDR ADJUSTED P VALUE ENDS******************/		
			/**********************************************************************************/		

			
			
			
			/**********************************************************************************/		
			/*****************AUGMENT WITH USERDEFINEDGENESET INFORMATION STARTS***************/	
			if(		enrichmentType.isUserDefinedGeneSetEnrichment() &&
					userDefinedGeneSetOptionalDescriptionInputFile!=null &&
					!userDefinedGeneSetOptionalDescriptionInputFile.equals(Commons.NO_OPTIONAL_USERDEFINEDGENESET_DESCRIPTION_FILE_PROVIDED)){
				
			  	UserDefinedGeneSetUtility.augmentUserDefinedGeneSetIDwithTerm(userDefinedGeneSetOptionalDescriptionInputFile,list);
				 
			}
			/*****************AUGMENT WITH USERDEFINEDGENESET INFORMATION ENDS*****************/		
			/**********************************************************************************/		
		
			
			
			/**********************************************************************************/		
			/*****************AUGMENT WITH KEGG PATHWAY INFORMATION STARTS*********************/		
			if (enrichmentType.isKeggPathwayEnrichment()){
				
				//Augment KeggPathwayNumber with KeggPathwayEntry
				KeggPathwayAugmentation.augmentKeggPathwayNumberWithKeggPathwayEntry(keggPathwayNumber2NameMap,list);
				//Augment KeggPathwayEntry with KeggPathwayName starts
				KeggPathwayAugmentation.augmentKeggPathwayEntrywithKeggPathwayName(dataFolder,list,null,null);
				//Augment with Kegg Pathway Gene List and Alternate Gene Name List
				KeggPathwayAugmentation.augmentKeggPathwayEntrywithKeggPathwayGeneList(dataFolder,outputFolder,list,null,null);

			}else if (enrichmentType.isTfKeggPathwayEnrichment()){
				//Augment KeggPathwayNumber with KeggPathwayEntry
				KeggPathwayAugmentation.augmentKeggPathwayNumberWithKeggPathwayEntry(keggPathwayNumber2NameMap,list);
				KeggPathwayAugmentation.augmentTfNameKeggPathwayEntrywithKeggPathwayName(dataFolder,list,null,null);
				KeggPathwayAugmentation.augmentTfNameKeggPathwayEntrywithKeggPathwayGeneList(dataFolder,outputFolder,list,null,null);

			}else if (enrichmentType.isTfCellLineKeggPathwayEnrichment()){
				//Augment KeggPathwayNumber with KeggPathwayEntry
				KeggPathwayAugmentation.augmentKeggPathwayNumberWithKeggPathwayEntry(keggPathwayNumber2NameMap,list);
				KeggPathwayAugmentation.augmentTfNameCellLineNameKeggPathwayEntrywithKeggPathwayName(dataFolder,list,null,null);
				KeggPathwayAugmentation.augmentTfNameCellLineNameKeggPathwayEntrywithKeggPathwayGeneList(dataFolder,outputFolder,list,null,null);		

			}
			/*****************AUGMENT WITH KEGG PATHWAY INFORMATION ENDS**********************/		
			/**********************************************************************************/		

			
			/**********************************************************************************/		
			/******************************WRITE RESULTS STARTS********************************/		
			//How to decide enriched elements?
			//with respect to Benjamini Hochberg FDR or
			//with respect to Bonferroni Correction Significance Level
			writeResultstoOutputFiles(outputFolder,allFileName,jobName,list,enrichmentType,multipleTestingParameter,bonferroniCorrectionSignigicanceLevel,FDR,numberofPermutations,numberofComparisons);
			/******************************WRITE RESULTS ENDS**********************************/		
			/**********************************************************************************/		

		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
	}

	/**
	 * 
	 */
	public CollectionofPermutationsResults() {
		// TODO Auto-generated constructor stub
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
		
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");

		
		NumberofComparisons numberofComparisons = NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder);
				
		int numberofPermutations = Integer.parseInt(args[9]);
		
		float FDR = Float.parseFloat(args[8]);
		float bonferroniCorrectionSignificanceLevel = Float.parseFloat(args[7]); 

		EnrichmentType dnaseEnrichmentType 					= EnrichmentType.convertStringtoEnum(args[10]);
		EnrichmentType histoneEnrichmentType  				= EnrichmentType.convertStringtoEnum(args[11]);
		EnrichmentType tfEnrichmentType 					= EnrichmentType.convertStringtoEnum(args[12]);
		EnrichmentType keggPathwayEnrichmentType 			= EnrichmentType.convertStringtoEnum(args[13]);
		EnrichmentType tfKeggPathwayEnrichmentType 			= EnrichmentType.convertStringtoEnum(args[14]);
		EnrichmentType tfCellLineKeggPathwayEnrichmentType = EnrichmentType.convertStringtoEnum(args[15]);

				
		/*********************************************************************************/
		/**************************USER DEFINED GENESET***********************************/	
		//User Defined GeneSet Enrichment, DO or DO_NOT
		EnrichmentType userDefinedGeneSetEnrichmentType = EnrichmentType.convertStringtoEnum(args[22]);

//		String userDefinedGeneSetInputFile = args[23];
//		String userDefinedGeneSetInputFile = "G:\\DOKTORA_DATA\\GO\\GO_gene_associations_human_ref.txt";
		  
//		GeneInformationType geneInformationType = GeneInformationType.convertStringtoEnum(args[24]);
//		GeneInformationType geneInformationType = GeneInformationType.GENE_SYMBOL;
		
		String userDefinedGeneSetName = args[25];
//		String userDefinedGeneSetName = "GO";

		String userDefinedGeneSetDescriptionOptionalInputFile =args[26];		
//		String userDefinedGeneSetDescriptionOptionalInputFile = "G:\\DOKTORA_DATA\\GO\\GO_terms_and_ids.txt";
		/**************************USER DEFINED GENESET***********************************/
		/*********************************************************************************/
		
	
		/*********************************************************************************/
		/**************************USER DEFINED LIBRARY***********************************/
		//User Defined Library Enrichment, DO or DO_NOT
		EnrichmentType userDefinedLibraryEnrichmentType = EnrichmentType.convertStringtoEnum(args[27]);
//		EnrichmentType userDefinedLibraryEnrichmentType = EnrichmentType.DO_USER_DEFINED_LIBRARY_ENRICHMENT;

//		String userDefinedLibraryInputFile = args[28];
//		String userDefinedLibraryInputFile = "C:\\Users\\burcakotlu\\GLANET\\UserDefinedLibraryInputFile.txt";		
		/**************************USER DEFINED LIBRARY***********************************/	
		/*********************************************************************************/
		
		//set the number of permutations in each run
		int numberofPermutationsInEachRun = Integer.parseInt(args[21]);
		
		
			
		int numberofRuns = 0;
		int numberofRemainders = 0;
		
		//Multiple Testing Parameter for selection of enriched elements
		MultipleTestingType multipleTestingParameter = MultipleTestingType.convertStringtoEnum(args[6]);
		
		numberofRuns = numberofPermutations / numberofPermutationsInEachRun;
		numberofRemainders = numberofPermutations % numberofPermutationsInEachRun;
		
		//Increase numberofRuns by 1 for remainder permutations less than 1000
		if (numberofRemainders> 0){
			numberofRuns += 1;
		}
				
		/**********************************************************/
		/************Collection of DNASE RESULTS starts************/
		if	(dnaseEnrichmentType.isDnaseEnrichment()){
			
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_DNASE_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_DNASE, 
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getDnaseCellLineNumberofComparison(),
					dnaseEnrichmentType,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_DNASECELLLINENUMBER);
		}
		
		/************Collection of DNASE RESULTS ends**************/
		/**********************************************************/

		/**********************************************************/
		/************Collection of HISTONE RESULTS starts**********/
		if (histoneEnrichmentType.isHistoneEnrichment()){
			
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_HISTONE_NUMBER_OF_OVERLAPS, 
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_HISTONE,
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getHistoneCellLineNumberofComparison(),
					histoneEnrichmentType,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_HISTONENUMBER_4DIGIT_CELLLINENUMBER);
		}
		/************Collection of HISTONE RESULTS ends************/
		/**********************************************************/
		

		/**********************************************************/
		/************Collection of TF RESULTS starts***************/
		if (tfEnrichmentType.isTfEnrichment() && 
				!(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment()) && 
				!(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){
			
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, 
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF,
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getTfCellLineNumberofComparison(),
					tfEnrichmentType,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER);
		}
		/**********************************************************/
		/************Collection of TF RESULTS ends*****************/
		
		
		
		/**************************************************************************/
		/************Collection of UserDefinedGeneSet RESULTS starts***************/
		if (userDefinedGeneSetEnrichmentType.isUserDefinedGeneSetEnrichment()){
			
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName  + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY +  Commons.EXON_BASED_USER_DEFINED_GENESET  +"_" + userDefinedGeneSetName,					
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName  + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.EXON_BASED_USER_DEFINED_GENESET  +"_" + userDefinedGeneSetName,					
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getExonBasedUserDefinedGeneSetNumberofComparison(),
					userDefinedGeneSetEnrichmentType,
					userDefinedGeneSetDescriptionOptionalInputFile,
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGIT_USERDEFINEDGENESETNUMBER);
			
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,outputFolder,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName  + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY +  Commons.REGULATION_BASED_USER_DEFINED_GENESET  +"_" + userDefinedGeneSetName,					
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName  + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.REGULATION_BASED_USER_DEFINED_GENESET  +"_" + userDefinedGeneSetName,					
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getRegulationBasedUserDefinedGeneSetNumberofComparison(),
					userDefinedGeneSetEnrichmentType,
					userDefinedGeneSetDescriptionOptionalInputFile,
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGIT_USERDEFINEDGENESETNUMBER);
			
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName  + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.ALL_BASED_USER_DEFINED_GENESET  +"_" + userDefinedGeneSetName,					
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName  + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.ALL_BASED_USER_DEFINED_GENESET  +"_" + userDefinedGeneSetName,					
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getAllBasedUserDefinedGeneSetNumberofComparison(),
					userDefinedGeneSetEnrichmentType,
					userDefinedGeneSetDescriptionOptionalInputFile,
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGIT_USERDEFINEDGENESETNUMBER);
		
		}
		/************Collection of UserDefinedGeneSet RESULTS ends*****************/
		/**************************************************************************/
		
		
		
		
		
		/**************************************************************************/
		/************Collection of UserDefinedLibrary RESULTS starts***************/
		if (userDefinedLibraryEnrichmentType.isUserDefinedLibraryEnrichment()){
			
			
			TIntObjectMap<String> elementTypeNumber2ElementTypeMap = new TIntObjectHashMap<String>();
			
			int elementTypeNumber;
			String elementType;
			
			UserDefinedLibraryUtility.fillNumber2NameMap(
					elementTypeNumber2ElementTypeMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME,
					Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_NUMBER_2_NAME_OUTPUT_FILENAME);
			
			
			//For each element type
			for(TIntObjectIterator<String> it =  elementTypeNumber2ElementTypeMap.iterator();it.hasNext();){
				it.advance();
				
				elementTypeNumber = it.key();
				elementType = it.value();
				
				CollectionofPermutationsResults.collectPermutationResults(
						numberofPermutationsInEachRun,
						bonferroniCorrectionSignificanceLevel,
						FDR,
						multipleTestingParameter,
						dataFolder,
						outputFolder,
						Commons.TO_BE_COLLECTED_USER_DEFINED_LIBRARY_NUMBER_OF_OVERLAPS + elementType  + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY,
						Commons.TO_BE_COLLECTED_USER_DEFINED_LIBRARY_NUMBER_OF_OVERLAPS + elementType + System.getProperty("file.separator"), 
						jobName,
						numberofRuns,
						numberofRemainders,
						numberofComparisons.getUserDefinedLibraryElementTypeNumber2NumberofComparisonMap().get(elementTypeNumber),
						userDefinedLibraryEnrichmentType,
						null,
						elementType,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGIT_ELEMENTNUMBER);

			}//End of for each elementTypeNumber
			
			
		}
		/************Collection of UserDefinedLibrary RESULTS ends*****************/
		/**************************************************************************/
		
		
		
		
		/**************************************************************************/
		/************Collection of KEGG Pathway RESULTS starts*********************/
		if (keggPathwayEnrichmentType.isKeggPathwayEnrichment() && 
				!(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment()) 
				&& !(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){
			
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, 
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY, 
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getExonBasedKEGGPathwayNumberofComparison(),
					keggPathwayEnrichmentType,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);
			
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, 
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY, 
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getRegulationBasedKEGGPathwayNumberofComparison(),
					keggPathwayEnrichmentType,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);
			
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, 
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY, 
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getAllBasedKEGGPathwayNumberofComparison(),
					keggPathwayEnrichmentType,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);
		}
		/************Collection of KEGG Pathway RESULTS ends***********************/
		/**************************************************************************/

		
		
		/**************************************************************************/
		/************Collection of TF KEGG Pathway RESULTS starts******************/
		if(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment()  && 
			!(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){
	
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF, jobName,numberofRuns,numberofRemainders,numberofComparisons.getTfCellLineNumberofComparison(),EnrichmentType.DO_TF_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER);

			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY, jobName, numberofRuns,numberofRemainders,numberofComparisons.getExonBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY, jobName,numberofRuns,numberofRemainders,numberofComparisons.getRegulationBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY, jobName,numberofRuns,numberofRemainders,numberofComparisons.getAllBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);

			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_EXON_BASED_KEGG_PATHWAY,  jobName,numberofRuns,numberofRemainders,numberofComparisons.getTfExonBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY,  jobName,numberofRuns,numberofRemainders,numberofComparisons.getTfRegulationBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_ALL_BASED_KEGG_PATHWAY,  jobName,numberofRuns,numberofRemainders,numberofComparisons.getTfAllBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER);

		}
		/**************************************************************************/
		/************Collection of TF KEGG Pathway RESULTS ends********************/

		
		
		/**************************************************************************/
		/************Collection of TF CellLine KEGG Pathway RESULTS starts*********/
		if (tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment() && 
			!(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment())){
			
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF, jobName,numberofRuns,numberofRemainders,numberofComparisons.getTfCellLineNumberofComparison(),EnrichmentType.DO_TF_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER);

			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY,  jobName,numberofRuns,numberofRemainders,numberofComparisons.getExonBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY, jobName,numberofRuns,numberofRemainders,numberofComparisons.getRegulationBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY, jobName,numberofRuns,numberofRemainders,numberofComparisons.getAllBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);

			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY,jobName, numberofRuns,numberofRemainders,numberofComparisons.getTfCellLineExonBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY, jobName, numberofRuns,numberofRemainders,numberofComparisons.getTfCellLineRegulationBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY, jobName, numberofRuns,numberofRemainders,numberofComparisons.getTfCellLineAllBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER);

		}
		/**************************************************************************/
		/************Collection of TF CellLine KEGG Pathway RESULTS ends***********/

		
		/**************************************************************************/
		/*******************************BOTH***************************************/
		/************Collection of TF KEGG Pathway RESULTS starts******************/
		/************Collection of TF CellLine KEGG Pathway RESULTS starts*********/
		if(	tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment() && 
			tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment()){
			
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF, jobName,numberofRuns,numberofRemainders,numberofComparisons.getTfCellLineNumberofComparison(),EnrichmentType.DO_TF_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER);

			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,  Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY,jobName,numberofRuns,numberofRemainders,numberofComparisons.getExonBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY,  jobName,numberofRuns,numberofRemainders,numberofComparisons.getRegulationBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY, jobName,numberofRuns,numberofRemainders,numberofComparisons.getAllBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER);
			
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_EXON_BASED_KEGG_PATHWAY,jobName,numberofRuns,numberofRemainders,numberofComparisons.getTfExonBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY, jobName,numberofRuns,numberofRemainders,numberofComparisons.getTfRegulationBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_ALL_BASED_KEGG_PATHWAY, jobName,numberofRuns,numberofRemainders,numberofComparisons.getTfAllBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER);

			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY, jobName, numberofRuns,numberofRemainders,numberofComparisons.getTfCellLineExonBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY,jobName, numberofRuns,numberofRemainders,numberofComparisons.getTfCellLineRegulationBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,bonferroniCorrectionSignificanceLevel,FDR,multipleTestingParameter,dataFolder,outputFolder,Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY,jobName, numberofRuns,numberofRemainders,numberofComparisons.getTfCellLineAllBasedKEGGPathwayNumberofComparison(),EnrichmentType.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT,null,null,GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER);

		}
		/************Collection of TF KEGG Pathway RESULTS ends********************/
		/************Collection of TF CellLine KEGG Pathway RESULTS ends***********/
		/*******************************BOTH***************************************/
		/**************************************************************************/

		
		
	
	}

}
