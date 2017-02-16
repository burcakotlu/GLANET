/**
 * @author Burcak Otlu
 * Feb 13, 2014
 * 1:15:47 PM
 * 2014
 *
 * ZScore computation is achieved in this class.
 * 
 */
package enrichment;

import enumtypes.AnnotationType;
import enumtypes.CommandLineArguments;
import enumtypes.EnrichmentZScoreMode;
import enumtypes.GeneratedMixedNumberDescriptionOrderLength;
import enumtypes.MultipleTestingType;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TLongIntIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import multipletesting.BenjaminiandHochberg;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.log4j.Logger;

import ui.GlanetRunner;
import userdefined.geneset.UserDefinedGeneSetUtility;
import userdefined.library.UserDefinedLibraryUtility;
import augmentation.humangenes.HumanGenesAugmentation;
import augmentation.keggpathway.KeggPathwayAugmentation;
import auxiliary.FileOperations;
import auxiliary.FunctionalElement;
import auxiliary.GlanetDecimalFormat;
import auxiliary.NumberofComparisons;
import auxiliary.StatisticsConversion;

import common.Commons;

public class CollectionofPermutationsResults {

	final static Logger logger = Logger.getLogger(CollectionofPermutationsResults.class);

	private static TIntObjectMap<String> goTermNumber2NameMap = null;
	private static TIntObjectMap<String> keggPathwayNumber2NameMap = null;
	private static TIntObjectMap<String> cellLineNumber2NameMap = null;
	private static TIntObjectMap<String> tfNumber2NameMap = null;
	private static TIntObjectMap<String> histoneNumber2NameMap = null;
	private static TIntObjectMap<String> userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap = null;
	private static TIntObjectMap<String> userDefinedLibraryElementNumber2ElementNameMap = null;
	private static TIntObjectMap<String> geneID2GeneHugoSymbolMap = null;

	//23 FEB 2016
	//GLANET can be run multiple times by the GUI.
	//Therefore Number2NameMap must be refilled for each run.
	//This applies for UDGS and UDL cases
	
	
	//13 FEB 2017
	//goTermNumber2NameMap
	public static TIntObjectMap<String> getGOTermNumber2NameMapInstance(
			String dataFolder) {
		
		if (goTermNumber2NameMap == null){
			
			goTermNumber2NameMap = new TIntObjectHashMap<String>();
	    	  FileOperations.fillNumber2NameMap(
	    			  goTermNumber2NameMap,
	    			  dataFolder + Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
	    			  Commons.ALL_POSSIBLE_GO_TERMS_NUMBER_2_NAME_OUTPUT_FILENAME);
		      
		}
    	 
	    return goTermNumber2NameMap;
	 }
			

	//keggPathwayNumber2NameMap
	public static TIntObjectMap<String> getKeggPathwayNumber2NameMapInstance(
			String dataFolder) {
		
		if (keggPathwayNumber2NameMap == null){
			
			keggPathwayNumber2NameMap = new TIntObjectHashMap<String>();
	    	  FileOperations.fillNumber2NameMap(
	    			  keggPathwayNumber2NameMap,
	    			  dataFolder + Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,
	    			  Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
		      
		}
    	 
	    return keggPathwayNumber2NameMap;
	 }
	
	//cellLineNumber2NameMap
	public static TIntObjectMap<String> getCellLineNumber2NameMapInstance(
			String dataFolder) {
		
	     if (cellLineNumber2NameMap == null){
	    	 
	    	 cellLineNumber2NameMap = new TIntObjectHashMap<String>();
	    	 FileOperations.fillNumber2NameMap(
	    			  cellLineNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
			 
	     }
    	  
	     return cellLineNumber2NameMap;
	 }
	
	
	//tfNumber2NameMap
	public static TIntObjectMap<String> getTFNumber2NameMapInstance(
			String dataFolder) {
		
		if(tfNumber2NameMap == null){
			
	    	  tfNumber2NameMap = new TIntObjectHashMap<String>();
	    	  FileOperations.fillNumber2NameMap(
	    			  tfNumber2NameMap,
	    			  dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
	    			  Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
								
		}
		  	        
		return tfNumber2NameMap;
	 }
	
	
	//histoneNumber2NameMap
	public static TIntObjectMap<String> getHistoneNumber2NameMapInstance(
			String dataFolder) {
		
		if (histoneNumber2NameMap == null){
			
	    	  histoneNumber2NameMap = new TIntObjectHashMap<String>();
	    	  FileOperations.fillNumber2NameMap(histoneNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_HISTONE_NUMBER_2_NAME_OUTPUT_FILENAME);
									

		}
	    	   
		return histoneNumber2NameMap;
	 }
	
	//userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap
	public static TIntObjectMap<String> getUserDefinedGeneSetNumber2UserDefinedGeneSetEntryMapInstance(
			String dataFolder) {
		
    	  userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap = new TIntObjectHashMap<String>();
    	  FileOperations.fillNumber2NameMap(userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap,
					dataFolder + Commons.ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME,
					Commons.ALL_POSSIBLE_USERDEFINEDGENESET_NUMBER_2_NAME_OUTPUT_FILENAME);
										
	     
	      
	      return userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap;
	 }
	
	//userDefinedLibraryElementNumber2ElementNameMap
	
	public static TIntObjectMap<String> getUserDefinedLibraryElementNumber2ElementNameMapInstance(
			String dataFolder,
			String elementType) {
		
		//GLANET can be run multiple times by the GUI.
		//Therefore userDefinedLibraryElementNumber2ElementNameMap must be refilled for each run.
		//UDL file could have changed.	    	  
		userDefinedLibraryElementNumber2ElementNameMap = new TIntObjectHashMap<String>();
		
		UserDefinedLibraryUtility.fillNumber2NameMap(
				userDefinedLibraryElementNumber2ElementNameMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME + elementType + System.getProperty("file.separator"),
				Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENT_NUMBER_2_NAME_OUTPUT_FILENAME);						
  
	      
	      return userDefinedLibraryElementNumber2ElementNameMap;
	 }
	
	//geneID2GeneHugoSymbolMap
	public static TIntObjectMap<String> getGeneID2GeneHugoSymbolMapInstance(
			String dataFolder) {
		
		if (geneID2GeneHugoSymbolMap == null){
		   	  geneID2GeneHugoSymbolMap = new TIntObjectHashMap<String>();
	    	  HumanGenesAugmentation.fillGeneId2GeneHugoSymbolMap(dataFolder, geneID2GeneHugoSymbolMap);

		}
			     	  							
		return geneID2GeneHugoSymbolMap;
	 }
	
	
	public static void writeResults_WRT_ZScores_WithMultipleTestingParameter(
			String outputFolder, 
			String fileName, 
			String jobName,
			List<FunctionalElement> list, 
			AnnotationType annotationType, 
			MultipleTestingType multipleTestingParameter,
			float bonferroniCorrectionSignigicanceLevel, 
			float FDR, 
			int numberofPermutations, 
			int numberofComparisons) throws IOException {

		BufferedWriter bufferedWriter = null;
		FunctionalElement element = null;

		/***********************************************************************************/
		/*********************** SET DECIMAL FORMAT SEPARATORS *****************************/
		/***********************************************************************************/
		DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat("0.######E0");
		/***********************************************************************************/
		/*********************** SET DECIMAL FORMAT SEPARATORS *****************************/
		/***********************************************************************************/

		// sort w.r.t. zScore in Descending Order
		Collections.sort(list, FunctionalElement.Z_SCORE);

		// write the results to a output file starts
		bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + fileName + "_" + jobName + Commons.ALL_WITH_RESPECT_TO_ZSCORE));

		// header line in output file
		bufferedWriter.write("ElementNumber" + "\t" + "ElementName" + "\t" + "Observed Test Statistic" + "\t" + "NumberofSamplingsHavingTestStatisticGreaterThanorEqualToIn" + numberofPermutations + "Samplings" + "\t" + "NumberofSamplings" + "\t" + "NumberofcomparisonsforBonferroniCorrection" + "\t" + "mean" + "\t" + "stdDev" + "\t" + "zScore" + "\t" + "empiricalPValueCalculatedFromZScore" + "\t" + "BonferroniCorrectedPValueCalculatedFromEmpiricalPValueCalculatedFromZScore" + "\t" + "BHFDRAdjustedPValueCalculatedFromEmpiricalPValueCalculatedFromZScore" + "\t" + "RejectNullHypothesisCalculatedFromBHFDRAdjustedPValueCalculatedFromEmpiricalPValueCalculatedFromZScore" + "\t" + "empiricalPValue" + "\t" + "BonfCorrPValuefor" + numberofComparisons + "Comparisons" + "\t" + "BHFDRAdjustedPValue" + "\t" + "RejectNullHypothesisDependingOnBHFDRAdjustedPValueforanFDRof" + FDR + System.getProperty("line.separator"));

		Iterator<FunctionalElement> itr = list.iterator();

		while(itr.hasNext()){
			element = itr.next();

			if(annotationType.doKEGGPathwayAnnotation() || annotationType.doTFKEGGPathwayAnnotation() || annotationType.doTFCellLineKEGGPathwayAnnotation()){

				// line per element in output file
				bufferedWriter.write(element.getNumber() + "\t" + element.getName() + "\t" + element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t" + numberofPermutations + "\t" + numberofComparisons + "\t" + element.getMean() + "\t" + element.getStdDev() + "\t" + element.getZScore() + "\t" + (element.getEmpiricalPValueCalculatedFromZScore() == null?element.getEmpiricalPValueCalculatedFromZScore():df.format(element.getEmpiricalPValueCalculatedFromZScore())) + "\t" + (element.getBonferroniCorrectedPValueCalculatedFromZScore() == null?element.getBonferroniCorrectedPValueCalculatedFromZScore():df.format(element.getBonferroniCorrectedPValueCalculatedFromZScore())) + "\t" + (element.getBHFDRAdjustedPValueCalculatedFromZScore() == null?element.getBHFDRAdjustedPValueCalculatedFromZScore():df.format(element.getBHFDRAdjustedPValueCalculatedFromZScore())) + "\t" + element.getRejectNullHypothesisCalculatedFromZScore() + "\t" + df.format(element.getEmpiricalPValue()) + "\t" + df.format(element.getBonferroniCorrectedPValue()) + "\t" + df.format(element.getBHFDRAdjustedPValue()) + "\t" + element.isRejectNullHypothesis() + "\t");

				bufferedWriter.write(element.getKeggPathwayName() + "\t");

				if(element.getKeggPathwayGeneIdList().size() >= 1){
					int i;
					// Write the gene ids of the kegg pathway
					for(i = 0; i < element.getKeggPathwayGeneIdList().size() - 1; i++){
						bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + ", ");
					}
					bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + "\t");
				}

				if(element.getKeggPathwayAlternateGeneNameList().size() >= 1){
					int i;

					// Write the alternate gene names of the kegg pathway
					for(i = 0; i < element.getKeggPathwayAlternateGeneNameList().size() - 1; i++){
						bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + ", ");
					}
					bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i));

					bufferedWriter.write("\t" + "NumberofGenesInThisKEGGPathway:" + "" + element.getKeggPathwayAlternateGeneNameList().size());

				}

				bufferedWriter.write(System.getProperty("line.separator"));

			}else if(annotationType.doUserDefinedGeneSetAnnotation()){
				// line per element in output file
				bufferedWriter.write(element.getNumber() + "\t");
				bufferedWriter.write(element.getName() + "\t");
				bufferedWriter.write(element.getOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(numberofPermutations + "\t");
				bufferedWriter.write(numberofComparisons + "\t");
				bufferedWriter.write(element.getMean() + "\t");
				bufferedWriter.write(element.getStdDev() + "\t");
				bufferedWriter.write(element.getZScore() + "\t");
				bufferedWriter.write((element.getEmpiricalPValueCalculatedFromZScore() == null? "null" :df.format(element.getEmpiricalPValueCalculatedFromZScore())));
				bufferedWriter.write("\t");
				bufferedWriter.write((element.getBonferroniCorrectedPValueCalculatedFromZScore() == null? "null" :df.format(element.getBonferroniCorrectedPValueCalculatedFromZScore())));
				bufferedWriter.write("\t");
				bufferedWriter.write((element.getBHFDRAdjustedPValueCalculatedFromZScore() == null? "null":df.format(element.getBHFDRAdjustedPValueCalculatedFromZScore())));
				bufferedWriter.write("\t");
				bufferedWriter.write(element.getRejectNullHypothesisCalculatedFromZScore() + "\t");
				bufferedWriter.write(df.format(element.getEmpiricalPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBonferroniCorrectedPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBHFDRAdjustedPValue()) + "\t");
				bufferedWriter.write(element.isRejectNullHypothesis() + "\t");

				bufferedWriter.write(element.getUserDefinedGeneSetDescription() + System.getProperty("line.separator"));

			}else{
				// line per element in output file
				bufferedWriter.write(element.getNumber() + "\t");
				bufferedWriter.write(element.getName() + "\t");
				bufferedWriter.write(element.getOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(numberofPermutations + "\t");
				bufferedWriter.write(numberofComparisons + "\t");
				bufferedWriter.write(element.getMean() + "\t");
				bufferedWriter.write(element.getStdDev() + "\t");
				bufferedWriter.write(element.getZScore() + "\t");
				bufferedWriter.write((element.getEmpiricalPValueCalculatedFromZScore() == null? "null" :df.format(element.getEmpiricalPValueCalculatedFromZScore())));
				bufferedWriter.write("\t");
				bufferedWriter.write((element.getBonferroniCorrectedPValueCalculatedFromZScore() == null? "null" :df.format(element.getBonferroniCorrectedPValueCalculatedFromZScore())));
				bufferedWriter.write("\t");
				bufferedWriter.write((element.getBHFDRAdjustedPValueCalculatedFromZScore() == null? "null" :df.format(element.getBHFDRAdjustedPValueCalculatedFromZScore())));
				bufferedWriter.write("\t");
				bufferedWriter.write(element.getRejectNullHypothesisCalculatedFromZScore() + "\t");
				bufferedWriter.write(df.format(element.getEmpiricalPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBonferroniCorrectedPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBHFDRAdjustedPValue()) + "\t");
				bufferedWriter.write(element.isRejectNullHypothesis() + System.getProperty("line.separator"));

			}

		}// End of while

		// close the file
		bufferedWriter.close();

	}

	public static void writeResults_WRT_MultipleTestingParameter_WithoutZScores(
			String outputFolder, 
			String fileName, 
			String jobName,
			List<FunctionalElement> list, 
			AnnotationType annotationType, 
			MultipleTestingType multipleTestingParameter,
			float bonferroniCorrectionSignigicanceLevel, 
			float FDR, 
			int numberofPermutations, 
			int numberofComparisons) throws IOException {
		
		BufferedWriter bufferedWriter = null;
		FunctionalElement element = null;
		
		//Pay attention reject null hypothesis is always decided on BH FDR adjusted p value 
		//which is calculated from  empiricalPValue calculated from numberofPermutations...Ratio or from empiricalPValue calculated from zScore
		/***********************************************************************************/
		/*********************** SET DECIMAL FORMAT SEPARATORS *****************************/
		/***********************************************************************************/
		DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat("0.######E0");
		/***********************************************************************************/
		/*********************** SET DECIMAL FORMAT SEPARATORS *****************************/
		/***********************************************************************************/

		/***********************************************************************************/
		/********** MULTIPLE TESTING W.R.T. BENJAMINI HOCHBERG starts **********************/
		/***********************************************************************************/
		if(multipleTestingParameter.isBenjaminiHochbergFDR()){

			// sort w.r.t. Benjamini and Hochberg FDR Adjusted pValue
			Collections.sort(list, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);

			// write the results to a output file starts
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + fileName + "_" + jobName + Commons.ALL_WITH_RESPECT_TO_BH_FDR_ADJUSTED_P_VALUE));
		}
		/***********************************************************************************/
		/********** MULTIPLE TESTING W.R.T. BENJAMINI HOCHBERG ends ************************/
		/***********************************************************************************/

		/***********************************************************************************/
		/********** MULTIPLE TESTING W.R.T. BONFERRONI CORRECTION starts *******************/
		/***********************************************************************************/
		else if(multipleTestingParameter.isBonferroniCorrection()){

			// sort w.r.t. Bonferroni Corrected pVlaue
			Collections.sort(list, FunctionalElement.BONFERRONI_CORRECTED_P_VALUE);

			// write the results to a output file starts
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + fileName + "_" + jobName + Commons.ALL_WITH_RESPECT_TO_BONF_CORRECTED_P_VALUE));
		}
		/***********************************************************************************/
		/********** MULTIPLE TESTING W.R.T. BONFERRONI CORRECTION ends *********************/
		/***********************************************************************************/

		/*************************************************************************************************/
		/***************** Common for BenjaminiHochberg and BonferroniCorrection starts ******************/
		/*************************************************************************************************/
		// header line in output file
		bufferedWriter.write("ElementNumber" + "\t");
		bufferedWriter.write("ElementName" + "\t");
		bufferedWriter.write("Observed Test Statistic" + "\t");
		bufferedWriter.write("NumberofSamplingsHavingTestStatisticGreaterThanorEqualToIn" + numberofPermutations + "Samplings" + "\t");
		bufferedWriter.write("NumberofSamplings" + "\t");
		bufferedWriter.write("NumberofComparisonsforBonferroniCorrection" + "\t");
		bufferedWriter.write("empiricalPValue" + "\t");
		bufferedWriter.write("BonfCorrPValuefor" + numberofComparisons + "Comparisons" + "\t"); 
		bufferedWriter.write("BHFDRAdjustedPValue" + "\t"); 
		bufferedWriter.write("RejectNullHypothesisforanFDRof" + FDR + System.getProperty("line.separator"));

		Iterator<FunctionalElement> itr = list.iterator();

		while(itr.hasNext()){
			element = itr.next();

			if(annotationType.doKEGGPathwayAnnotation() || 
					annotationType.doTFKEGGPathwayAnnotation() || 
					annotationType.doTFCellLineKEGGPathwayAnnotation()){

				// line per element in output file
				bufferedWriter.write(element.getNumber() + "\t");
				bufferedWriter.write(element.getName() + "\t");
				bufferedWriter.write(element.getOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(numberofPermutations + "\t");
				bufferedWriter.write(numberofComparisons + "\t");
				bufferedWriter.write(df.format(element.getEmpiricalPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBonferroniCorrectedPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBHFDRAdjustedPValue()) + "\t");
				bufferedWriter.write(element.isRejectNullHypothesis() + "\t");
				bufferedWriter.write(element.getKeggPathwayName() + "\t");

				if(element.getKeggPathwayGeneIdList().size() >= 1){
					int i;
					// Write the gene ids of the kegg pathway
					for(i = 0; i < element.getKeggPathwayGeneIdList().size() - 1; i++){
						bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + ", ");
					}
					bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + "\t");
				}

				if(element.getKeggPathwayAlternateGeneNameList().size() >= 1){
					int i;

					// Write the alternate gene names of the kegg pathway
					for(i = 0; i < element.getKeggPathwayAlternateGeneNameList().size() - 1; i++){
						bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + ", ");
					}
					bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i));

					bufferedWriter.write("\t" + "NumberofGenesInThisKEGGPathway:" + "" + element.getKeggPathwayAlternateGeneNameList().size());

				}

				bufferedWriter.write(System.getProperty("line.separator"));

			}else if(annotationType.doUserDefinedGeneSetAnnotation()){
				// line per element in output file
				//bufferedWriter.write(element.getNumber() + "\t" + element.getName() + "\t" + element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t" + numberofPermutations + "\t" + numberofComparisons + "\t" + element.getMean() + "\t" + element.getStdDev() + "\t" + element.getZScore() + "\t" + element.getEmpiricalPValueCalculatedFromZScore() == null?null:df.format(element.getEmpiricalPValueCalculatedFromZScore()) + "\t" + element.getBonferroniCorrectedPValueCalculatedFromZScore() == null?null:df.format(element.getBonferroniCorrectedPValueCalculatedFromZScore()) + "\t" + element.getBHFDRAdjustedPValueCalculatedFromZScore() == null?null:df.format(element.getBHFDRAdjustedPValueCalculatedFromZScore()) + "\t" + element.getRejectNullHypothesisCalculatedFromZScore() + "\t" + df.format(element.getEmpiricalPValue()) + "\t" + df.format(element.getBonferroniCorrectedPValue()) + "\t" + df.format(element.getBHFDRAdjustedPValue()) + "\t" + element.isRejectNullHypothesis() + "\t");
				
				bufferedWriter.write(element.getNumber() + "\t");
				bufferedWriter.write(element.getName() + "\t");
				bufferedWriter.write(element.getOriginalNumberofOverlaps() + "\t" );
				bufferedWriter.write(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(numberofPermutations + "\t" );
				bufferedWriter.write(numberofComparisons + "\t" );
				bufferedWriter.write(df.format(element.getEmpiricalPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBonferroniCorrectedPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBHFDRAdjustedPValue()) + "\t");
				bufferedWriter.write(element.isRejectNullHypothesis() + "\t");
				
				bufferedWriter.write(element.getUserDefinedGeneSetDescription() + System.getProperty("line.separator"));

			}else{
				// line per element in output file
				//bufferedWriter.write(element.getNumber() + "\t" + element.getName() + "\t" + element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t" + numberofPermutations + "\t" + numberofComparisons + "\t" + element.getMean() + "\t" + element.getStdDev() + "\t" + element.getZScore() + "\t" + element.getEmpiricalPValueCalculatedFromZScore() == null?null:df.format(element.getEmpiricalPValueCalculatedFromZScore()) + "\t" + element.getBonferroniCorrectedPValueCalculatedFromZScore() == null?null:df.format(element.getBonferroniCorrectedPValueCalculatedFromZScore()) + "\t" + element.getBHFDRAdjustedPValueCalculatedFromZScore() == null?null:df.format(element.getBHFDRAdjustedPValueCalculatedFromZScore()) + "\t" + element.getRejectNullHypothesisCalculatedFromZScore() + "\t" + df.format(element.getEmpiricalPValue()) + "\t" + df.format(element.getBonferroniCorrectedPValue()) + "\t" + df.format(element.getBHFDRAdjustedPValue()) + "\t" + element.isRejectNullHypothesis() + System.getProperty("line.separator"));
				bufferedWriter.write(element.getNumber() + "\t");
				bufferedWriter.write(element.getName() + "\t");
				bufferedWriter.write(element.getOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(numberofPermutations + "\t");
				bufferedWriter.write(numberofComparisons + "\t");
				bufferedWriter.write(df.format(element.getEmpiricalPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBonferroniCorrectedPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBHFDRAdjustedPValue()) + "\t");
				bufferedWriter.write(element.isRejectNullHypothesis() + System.getProperty("line.separator"));
				
			}

		}// End of WHILE

		// Close the file
		bufferedWriter.close();
		/*************************************************************************************************/
		/***************** Common for BenjaminiHochberg and BonferroniCorrection ends ********************/
		/*************************************************************************************************/

		
	}
			
	
	// How to decide enriched elements?
	// multipleTestingParameter decides on that
	// with respect to Benjamini Hochberg FDR or
	// with respect to Bonferroni Correction Significance Level
	public static void writeResults_WRT_MultipleTestingParameter_WithZScores(
			String outputFolder, 
			String fileName, 
			String jobName,
			List<FunctionalElement> list, 
			AnnotationType annotationType, 
			MultipleTestingType multipleTestingParameter,
			float bonferroniCorrectionSignigicanceLevel, 
			float FDR, 
			int numberofPermutations, 
			int numberofComparisons) throws IOException {

		BufferedWriter bufferedWriter = null;
		FunctionalElement element = null;
		
		//Pay attention reject null hypothesis is always decided on BH FDR adjusted p value 
		//which is calculated from  empiricalPValue calculated from numberofPermutations...Ratio or from empiricalPValue calculated from zScore
		/***********************************************************************************/
		/*********************** SET DECIMAL FORMAT SEPARATORS *****************************/
		/***********************************************************************************/
		DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat("0.######E0");
		/***********************************************************************************/
		/*********************** SET DECIMAL FORMAT SEPARATORS *****************************/
		/***********************************************************************************/

		/***********************************************************************************/
		/********** MULTIPLE TESTING W.R.T. BENJAMINI HOCHBERG starts **********************/
		/***********************************************************************************/
		if(multipleTestingParameter.isBenjaminiHochbergFDR()){

			// sort w.r.t. Benjamini and Hochberg FDR Adjusted pValue
			Collections.sort(list, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);

			// write the results to a output file starts
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + fileName + "_" + jobName + Commons.ALL_WITH_RESPECT_TO_BH_FDR_ADJUSTED_P_VALUE));
		}
		/***********************************************************************************/
		/********** MULTIPLE TESTING W.R.T. BENJAMINI HOCHBERG ends ************************/
		/***********************************************************************************/

		/***********************************************************************************/
		/********** MULTIPLE TESTING W.R.T. BONFERRONI CORRECTION starts *******************/
		/***********************************************************************************/
		else if(multipleTestingParameter.isBonferroniCorrection()){

			// sort w.r.t. Bonferroni Corrected pVlaue
			Collections.sort(list, FunctionalElement.BONFERRONI_CORRECTED_P_VALUE);

			// write the results to a output file starts
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + fileName + "_" + jobName + Commons.ALL_WITH_RESPECT_TO_BONF_CORRECTED_P_VALUE));
		}
		/***********************************************************************************/
		/********** MULTIPLE TESTING W.R.T. BONFERRONI CORRECTION ends *********************/
		/***********************************************************************************/

		/*************************************************************************************************/
		/***************** Common for BenjaminiHochberg and BonferroniCorrection starts ******************/
		/*************************************************************************************************/
		// header line in output file
		bufferedWriter.write("ElementNumber" + "\t" + "ElementName" + "\t" + "Observed Test Statistic" + "\t" + "NumberofSamplingsHavingTestStatisticGreaterThanorEqualToIn" + numberofPermutations + "Samplings" + "\t" + "NumberofSamplings" + "\t" + "NumberofcomparisonsforBonferroniCorrection" + "\t" + "mean" + "\t" + "stdDev" + "\t" + "zScore" + "\t" + "empiricalPValueCalculatedFromZScore" + "\t" + "BonferroniCorrectedPValueCalculatedFromEmpiricalPValueCalculatedFromZScore" + "\t" + "BHFDRAdjustedPValueCalculatedFromEmpiricalPValueCalculatedFromZScore" + "\t" + "RejectNullHypothesisBHFDRAdjustedPValueCalculatedFromEmpiricalPValueCalculatedFromZScore" + "\t" + "empiricalPValue" + "\t" + "BonfCorrPValuefor" + numberofComparisons + "Comparisons" + "\t" + "BHFDRAdjustedPValue" + "\t" + "RejectNullHypothesisforanFDRof" + FDR + System.getProperty("line.separator"));

		Iterator<FunctionalElement> itr = list.iterator();

		while(itr.hasNext()){
			element = itr.next();

			if(annotationType.doKEGGPathwayAnnotation() || 
					annotationType.doTFKEGGPathwayAnnotation() || 
					annotationType.doTFCellLineKEGGPathwayAnnotation()){

				// line per element in output file
				bufferedWriter.write(element.getNumber() + "\t" + element.getName() + "\t" + element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t" + numberofPermutations + "\t" + numberofComparisons + "\t" + element.getMean() + "\t" + element.getStdDev() + "\t" + element.getZScore() + "\t" + (element.getEmpiricalPValueCalculatedFromZScore() == null?element.getEmpiricalPValueCalculatedFromZScore():df.format(element.getEmpiricalPValueCalculatedFromZScore())) + "\t" + (element.getBonferroniCorrectedPValueCalculatedFromZScore() == null?element.getBonferroniCorrectedPValueCalculatedFromZScore():df.format(element.getBonferroniCorrectedPValueCalculatedFromZScore())) + "\t" + (element.getBHFDRAdjustedPValueCalculatedFromZScore() == null?element.getBHFDRAdjustedPValueCalculatedFromZScore():df.format(element.getBHFDRAdjustedPValueCalculatedFromZScore())) + "\t" + element.getRejectNullHypothesisCalculatedFromZScore() + "\t" + df.format(element.getEmpiricalPValue()) + "\t" + df.format(element.getBonferroniCorrectedPValue()) + "\t" + df.format(element.getBHFDRAdjustedPValue()) + "\t" + element.isRejectNullHypothesis() + "\t");

				bufferedWriter.write(element.getKeggPathwayName() + "\t");

				if(element.getKeggPathwayGeneIdList().size() >= 1){
					int i;
					// Write the gene ids of the kegg pathway
					for(i = 0; i < element.getKeggPathwayGeneIdList().size() - 1; i++){
						bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + ", ");
					}
					bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + "\t");
				}

				if(element.getKeggPathwayAlternateGeneNameList().size() >= 1){
					int i;

					// Write the alternate gene names of the kegg pathway
					for(i = 0; i < element.getKeggPathwayAlternateGeneNameList().size() - 1; i++){
						bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + ", ");
					}
					bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i));

					bufferedWriter.write("\t" + "NumberofGenesInThisKEGGPathway:" + "" + element.getKeggPathwayAlternateGeneNameList().size());

				}

				bufferedWriter.write(System.getProperty("line.separator"));

			}else if(annotationType.doUserDefinedGeneSetAnnotation()){
				// line per element in output file
				//bufferedWriter.write(element.getNumber() + "\t" + element.getName() + "\t" + element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t" + numberofPermutations + "\t" + numberofComparisons + "\t" + element.getMean() + "\t" + element.getStdDev() + "\t" + element.getZScore() + "\t" + element.getEmpiricalPValueCalculatedFromZScore() == null?null:df.format(element.getEmpiricalPValueCalculatedFromZScore()) + "\t" + element.getBonferroniCorrectedPValueCalculatedFromZScore() == null?null:df.format(element.getBonferroniCorrectedPValueCalculatedFromZScore()) + "\t" + element.getBHFDRAdjustedPValueCalculatedFromZScore() == null?null:df.format(element.getBHFDRAdjustedPValueCalculatedFromZScore()) + "\t" + element.getRejectNullHypothesisCalculatedFromZScore() + "\t" + df.format(element.getEmpiricalPValue()) + "\t" + df.format(element.getBonferroniCorrectedPValue()) + "\t" + df.format(element.getBHFDRAdjustedPValue()) + "\t" + element.isRejectNullHypothesis() + "\t");
				
				bufferedWriter.write(element.getNumber() + "\t");
				bufferedWriter.write(element.getName() + "\t");
				bufferedWriter.write(element.getOriginalNumberofOverlaps() + "\t" );
				bufferedWriter.write(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(numberofPermutations + "\t" );
				bufferedWriter.write(numberofComparisons + "\t" );
				bufferedWriter.write(element.getMean() + "\t");
				bufferedWriter.write(element.getStdDev() + "\t" );
				bufferedWriter.write(element.getZScore() + "\t");
				bufferedWriter.write(element.getEmpiricalPValueCalculatedFromZScore() == null? "null" :df.format(element.getEmpiricalPValueCalculatedFromZScore()));
				bufferedWriter.write("\t");
				bufferedWriter.write(element.getBonferroniCorrectedPValueCalculatedFromZScore() == null? "null" :df.format(element.getBonferroniCorrectedPValueCalculatedFromZScore()));
				bufferedWriter.write("\t");
				bufferedWriter.write(element.getBHFDRAdjustedPValueCalculatedFromZScore() == null? "null" :df.format(element.getBHFDRAdjustedPValueCalculatedFromZScore()));
				bufferedWriter.write("\t");
				bufferedWriter.write(element.getRejectNullHypothesisCalculatedFromZScore() + "\t");
				bufferedWriter.write(df.format(element.getEmpiricalPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBonferroniCorrectedPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBHFDRAdjustedPValue()) + "\t");
				bufferedWriter.write(element.isRejectNullHypothesis() + "\t");
				
				bufferedWriter.write(element.getUserDefinedGeneSetDescription() + System.getProperty("line.separator"));

			}else{
				// line per element in output file
				//bufferedWriter.write(element.getNumber() + "\t" + element.getName() + "\t" + element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t" + numberofPermutations + "\t" + numberofComparisons + "\t" + element.getMean() + "\t" + element.getStdDev() + "\t" + element.getZScore() + "\t" + element.getEmpiricalPValueCalculatedFromZScore() == null?null:df.format(element.getEmpiricalPValueCalculatedFromZScore()) + "\t" + element.getBonferroniCorrectedPValueCalculatedFromZScore() == null?null:df.format(element.getBonferroniCorrectedPValueCalculatedFromZScore()) + "\t" + element.getBHFDRAdjustedPValueCalculatedFromZScore() == null?null:df.format(element.getBHFDRAdjustedPValueCalculatedFromZScore()) + "\t" + element.getRejectNullHypothesisCalculatedFromZScore() + "\t" + df.format(element.getEmpiricalPValue()) + "\t" + df.format(element.getBonferroniCorrectedPValue()) + "\t" + df.format(element.getBHFDRAdjustedPValue()) + "\t" + element.isRejectNullHypothesis() + System.getProperty("line.separator"));
				
				
				bufferedWriter.write(element.getNumber() + "\t");
				bufferedWriter.write(element.getName() + "\t");
				bufferedWriter.write(element.getOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t");
				bufferedWriter.write(numberofPermutations + "\t");
				bufferedWriter.write(numberofComparisons + "\t");
				bufferedWriter.write(element.getMean() + "\t"); 
				bufferedWriter.write(element.getStdDev() + "\t"); 
				bufferedWriter.write(element.getZScore() + "\t");
				bufferedWriter.write(element.getEmpiricalPValueCalculatedFromZScore() == null ? "null" : df.format(element.getEmpiricalPValueCalculatedFromZScore()));
				bufferedWriter.write("\t"); 
				bufferedWriter.write(element.getBonferroniCorrectedPValueCalculatedFromZScore() == null ? "null" : df.format(element.getBonferroniCorrectedPValueCalculatedFromZScore()));
				bufferedWriter.write("\t"); 
				bufferedWriter.write(element.getBHFDRAdjustedPValueCalculatedFromZScore() == null ? "null" : df.format(element.getBHFDRAdjustedPValueCalculatedFromZScore()));
				bufferedWriter.write("\t");
				bufferedWriter.write(element.getRejectNullHypothesisCalculatedFromZScore() + "\t"); 
				bufferedWriter.write(df.format(element.getEmpiricalPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBonferroniCorrectedPValue()) + "\t");
				bufferedWriter.write(df.format(element.getBHFDRAdjustedPValue()) + "\t");
				bufferedWriter.write(element.isRejectNullHypothesis() + System.getProperty("line.separator"));
				
			}

		}// End of WHILE

		// Close the file
		bufferedWriter.close();
		/*************************************************************************************************/
		/***************** Common for BenjaminiHochberg and BonferroniCorrection ends ********************/
		/*************************************************************************************************/

	}

	public static String convertGeneratedMixedNumberToName(
			long modifiedMixedNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength,
			TIntObjectMap<String> cellLineNumber2NameMap, 
			TIntObjectMap<String> tfNumber2NameMap,
			TIntObjectMap<String> histoneNumber2NameMap,
			TIntObjectMap<String> userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap,
			TIntObjectMap<String> userDefinedLibraryElementNumber2ElementNameMap,
			TIntObjectMap<String> goTermNumber2NameMap,
			TIntObjectMap<String> keggPathwayNumber2NameMap, 
			TIntObjectMap<String> geneID2GeneHugoSymbolMap,
			AnnotationType annotationType) {

		int cellLineNumber;
		int tforHistoneNumber;
		int tfNumber;
		int histoneNumber;
		int goTermNumber;
		int keggPathwayNumber;
		int userDefinedGeneSetNumber;

		int userDefinedLibraryElementNumber;

		int geneID;

		String cellLineName;
		String tfName;
		String histoneName;
		String tforHistoneName = null;
		String goTermName;
		String keggPathwayName;
		String userDefinedGeneSetName;

		String userDefinedLibraryElementName;

		String geneHugoSymbol;

		switch(generatedMixedNumberDescriptionOrderLength){
			case INT_4DIGIT_DNASECELLLINENUMBER:{
				cellLineNumber = IntervalTree.getCellLineNumber(modifiedMixedNumber,
						generatedMixedNumberDescriptionOrderLength);
				cellLineName = cellLineNumber2NameMap.get(cellLineNumber);
				return cellLineName;
			}
			case INT_10DIGIT_USERDEFINEDGENESETNUMBER:
			{
				userDefinedGeneSetNumber = IntervalTree.getGeneSetNumber(modifiedMixedNumber,
						generatedMixedNumberDescriptionOrderLength);
				userDefinedGeneSetName = userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap.get(userDefinedGeneSetNumber);
				return userDefinedGeneSetName;
		
			}
			
			case INT_10DIGIT_GOTERMNUMBER:
			{
				goTermNumber = IntervalTree.getGeneSetNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
				goTermName = goTermNumber2NameMap.get(goTermNumber);
				return goTermName;
		
			}				
			
			case INT_4DIGIT_KEGGPATHWAYNUMBER:{
				keggPathwayNumber = IntervalTree.getGeneSetNumber(modifiedMixedNumber,
						generatedMixedNumberDescriptionOrderLength);
				keggPathwayName = keggPathwayNumber2NameMap.get(keggPathwayNumber);
				return keggPathwayName;
			}
		
			//Without ZScores
			case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER:{
				
				tforHistoneNumber = IntervalTree.getElementNumber(modifiedMixedNumber, generatedMixedNumberDescriptionOrderLength);
				
				 if (annotationType.doTFAnnotation()){
					 tforHistoneName = tfNumber2NameMap.get(tforHistoneNumber);
						
				 }else if (annotationType.doHistoneAnnotation()){
					 tforHistoneName = histoneNumber2NameMap.get(tforHistoneNumber);
						
				 }
				
				cellLineNumber = IntervalTree.getCellLineNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
				cellLineName = cellLineNumber2NameMap.get(cellLineNumber);
				
				return tforHistoneName + "_" + cellLineName;
			}
			
			//With ZScore
			case INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER:{
				tfNumber = IntervalTree.getElementNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
				tfName = tfNumber2NameMap.get(tfNumber);
		
				cellLineNumber = IntervalTree.getCellLineNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
				cellLineName = cellLineNumber2NameMap.get(cellLineNumber);
		
				return tfName + "_" + cellLineName;
			}
		
		
			//With ZScore
			case INT_4DIGIT_HISTONENUMBER_4DIGIT_CELLLINENUMBER:{
				histoneNumber = IntervalTree.getElementNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
				histoneName = histoneNumber2NameMap.get(histoneNumber);
		
				cellLineNumber = IntervalTree.getCellLineNumber(modifiedMixedNumber,generatedMixedNumberDescriptionOrderLength);
				cellLineName = cellLineNumber2NameMap.get(cellLineNumber);
		
				return histoneName + "_" + cellLineName;
		
			}
		
			case INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER:{
				tfNumber = IntervalTree.getElementNumber(modifiedMixedNumber, generatedMixedNumberDescriptionOrderLength);
				tfName = tfNumber2NameMap.get(tfNumber);
		
				keggPathwayNumber = IntervalTree.getGeneSetNumber(modifiedMixedNumber,
						generatedMixedNumberDescriptionOrderLength);
				keggPathwayName = keggPathwayNumber2NameMap.get(keggPathwayNumber);
		
				return tfName + "_" + keggPathwayName;
			}
			case LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER:{
				tfNumber = IntervalTree.getElementNumber(modifiedMixedNumber, generatedMixedNumberDescriptionOrderLength);
				tfName = tfNumber2NameMap.get(tfNumber);
		
				cellLineNumber = IntervalTree.getCellLineNumber(modifiedMixedNumber,
						generatedMixedNumberDescriptionOrderLength);
				cellLineName = cellLineNumber2NameMap.get(cellLineNumber);
		
				keggPathwayNumber = IntervalTree.getGeneSetNumber(modifiedMixedNumber,
						generatedMixedNumberDescriptionOrderLength);
				keggPathwayName = keggPathwayNumber2NameMap.get(keggPathwayNumber);
		
				return tfName + "_" + cellLineName + "_" + keggPathwayName;
			}
		
			case INT_6DIGIT_ELEMENTNUMBER:{
				userDefinedLibraryElementNumber = IntervalTree.getElementNumber(modifiedMixedNumber,
						generatedMixedNumberDescriptionOrderLength);
				userDefinedLibraryElementName = userDefinedLibraryElementNumber2ElementNameMap.get(userDefinedLibraryElementNumber);
				return userDefinedLibraryElementName;
			}
			case INT_10DIGIT_GENENUMBER:{
				geneID = IntervalTree.getElementNumber(modifiedMixedNumber, generatedMixedNumberDescriptionOrderLength);;
				geneHugoSymbol = geneID2GeneHugoSymbolMap.get(geneID);
				return geneHugoSymbol;
			}
			default:{
				break;
			}

		}

		return null;
	}
	
	//11 Sep 2015
	public static void collectEnrichmentResultsWithZScores(
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
			AnnotationType annotationType,
			String userDefinedGeneSetOptionalDescriptionInputFile, 
			String elementType,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLengthForWithZScoreMode,
			int numberofPermutations,
			String glanetRunType
		){
		
				
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine;
		String tempRunName;
		
		int numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps;
		
		int indexofTab;
		int indexofPipe;
		int indexofFormerComma;
		int indexofLatterComma;
		
		long mixedNumber;
		
		float empiricalPValue;
		float bonferroniCorrectedEmpiricalPValue;
		
		TLongObjectMap<DescriptiveStatistics> elementNumber2StatsMap = new TLongObjectHashMap<DescriptiveStatistics>();
		DescriptiveStatistics statsPerElement = null;
		
		Double mean = 0.0;
		Double stdDev = 0.0;
		Double zScore = 0.0;
		Double empiricalPValueCalculatedFromZScore = 0.0;
		Double bonferroniCorrectedPValueCalculatedFromZScore = 0.0;
		
		int originalNumberofOverlaps = 0;
		TLongIntMap elementNumber2OriginalNumberofOverlaps = new TLongIntHashMap();
		
		int permutationNumberofOverlaps = Integer.MAX_VALUE;
		
		Map<Long, FunctionalElement> elementNumber2ElementMap = new HashMap<Long, FunctionalElement>();
		FunctionalElement element;
		long elementNumber;
		
		String tforHistoneNameCellLineNameKeggPathwayNameGeneHugoSymbol;
		
		// In case of functionalElement contains KEGG Pathway
		int keggPathwayNumber;
				
		switch(annotationType){

			case DO_DNASE_ANNOTATION:{
				cellLineNumber2NameMap = getCellLineNumber2NameMapInstance(dataFolder);
				break;
			}
		
			case DO_TF_ANNOTATION:{
				cellLineNumber2NameMap = getCellLineNumber2NameMapInstance(dataFolder);
				tfNumber2NameMap = getTFNumber2NameMapInstance(dataFolder);
				break;
			}
		
			case DO_HISTONE_ANNOTATION:{
				cellLineNumber2NameMap = getCellLineNumber2NameMapInstance(dataFolder);
				histoneNumber2NameMap = getHistoneNumber2NameMapInstance(dataFolder);
				break;
			}
		
			case DO_BP_GOTERMS_ANNOTATION:
			case DO_MF_GOTERMS_ANNOTATION:
			case DO_CC_GOTERMS_ANNOTATION:{
				goTermNumber2NameMap = getGOTermNumber2NameMapInstance(dataFolder);
				break;		
			}
			
			case DO_KEGGPATHWAY_ANNOTATION:{
				keggPathwayNumber2NameMap = getKeggPathwayNumber2NameMapInstance(dataFolder);
				break;
			}
			case DO_TF_KEGGPATHWAY_ANNOTATION:{
				tfNumber2NameMap = getTFNumber2NameMapInstance(dataFolder);
				keggPathwayNumber2NameMap = getKeggPathwayNumber2NameMapInstance(dataFolder);
				break;
			}
		
			case DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION:{
				cellLineNumber2NameMap = getCellLineNumber2NameMapInstance(dataFolder);
				tfNumber2NameMap = getTFNumber2NameMapInstance(dataFolder);
				keggPathwayNumber2NameMap = getKeggPathwayNumber2NameMapInstance(dataFolder);
				break;
			}
			case DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION:{
				cellLineNumber2NameMap = getCellLineNumber2NameMapInstance(dataFolder);
				tfNumber2NameMap = getTFNumber2NameMapInstance(dataFolder);
				keggPathwayNumber2NameMap = getKeggPathwayNumber2NameMapInstance(dataFolder);
				break;
			}
			case DO_USER_DEFINED_GENESET_ANNOTATION:{
				userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap = getUserDefinedGeneSetNumber2UserDefinedGeneSetEntryMapInstance(dataFolder);
				break;
			}
		
			case DO_USER_DEFINED_LIBRARY_ANNOTATION:{
				userDefinedLibraryElementNumber2ElementNameMap = getUserDefinedLibraryElementNumber2ElementNameMapInstance(dataFolder, elementType);
				break;
			}
		
			case DO_GENE_ANNOTATION:{
				geneID2GeneHugoSymbolMap = getGeneID2GeneHugoSymbolMapInstance(dataFolder);
				break;
			}
			default:{
				break;
			}
	
		}// End of Switch AnnotationType
			
		try{

			/************************************************************************************/
			/*********************** FOR EACH RUN STARTS ****************************************/
			/************************************************************************************/
			for(int i = 1; i <= numberofRuns; i++){

				tempRunName = "_" + jobName + "_" + Commons.RUN + i;

				fileReader = new FileReader(outputFolder + runFileName + tempRunName + ".txt");
				bufferedReader = new BufferedReader(fileReader);

				// Outer While
				while((strLine = bufferedReader.readLine()) != null){

					// Initialize numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps to zero
					numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = 0;

					indexofTab = strLine.indexOf('\t');
					indexofPipe = strLine.indexOf('|');

					// PermutationNumber does not exists
					mixedNumber = Long.parseLong(strLine.substring(0, indexofTab));

					// debug starts
					if(mixedNumber < 0){
						if(GlanetRunner.shouldLog()) logger.error("There is a situation 1");
						if(GlanetRunner.shouldLog()) logger.error(mixedNumber);
					}
					// debug ends

					statsPerElement = elementNumber2StatsMap.get(mixedNumber);

					if(statsPerElement == null){
						statsPerElement = new DescriptiveStatistics();
						elementNumber2StatsMap.put(mixedNumber, statsPerElement);
					}

					// mixedNumber can be in one of these formats
					// INT_4DIGIT_DNASECELLLINENUMBER
					// INT_10DIGIT_USERDEFINEDGENESETNUMBER
					// INT_4DIGIT_KEGGPATHWAYNUMBER
					// INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER
					// INT_4DIGIT_HISTONENUMBER_4DIGIT_CELLLINENUMBER
					// INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER
					// LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER
					// INT_10DIGIT_GENENUMBER

					originalNumberofOverlaps = Integer.parseInt(strLine.substring(indexofTab + 1, indexofPipe));

					// Original Number of Overlaps per each element
					if(!elementNumber2OriginalNumberofOverlaps.containsKey(mixedNumber)){
						elementNumber2OriginalNumberofOverlaps.put(mixedNumber, originalNumberofOverlaps);
					}
					// For Control Purposes
					// originalNumberofOverlaps coming from other run results
					else if(elementNumber2OriginalNumberofOverlaps.get(mixedNumber) != originalNumberofOverlaps){
						if(GlanetRunner.shouldLog()) logger.error("There is a situation: Original Number of Overlaps differ");
					}

					indexofFormerComma = indexofPipe;
					indexofLatterComma = strLine.indexOf(',', indexofFormerComma + 1);

					// Inner While
					while(indexofFormerComma != -1 && indexofLatterComma != -1){

						permutationNumberofOverlaps = Integer.parseInt(strLine.substring(indexofFormerComma + 1,indexofLatterComma));

						// 13 May 2015 starts
						statsPerElement.addValue(permutationNumberofOverlaps);

						if(permutationNumberofOverlaps >= originalNumberofOverlaps){
							numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps++;
						}

						indexofFormerComma = indexofLatterComma;
						indexofLatterComma = strLine.indexOf(',', indexofLatterComma + 1);

					}// End of Inner WHILE loop: all permutations, number of overlaps of an element

					// Write numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps to map
					if(elementNumber2ElementMap.get(mixedNumber) == null){
						element = new FunctionalElement();

						element.setNumber(mixedNumber);

						tforHistoneNameCellLineNameKeggPathwayNameGeneHugoSymbol = convertGeneratedMixedNumberToName(
								mixedNumber, 
								generatedMixedNumberDescriptionOrderLengthForWithZScoreMode, 
								cellLineNumber2NameMap,
								tfNumber2NameMap, 
								histoneNumber2NameMap,
								userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap,
								userDefinedLibraryElementNumber2ElementNameMap, 
								goTermNumber2NameMap,
								keggPathwayNumber2NameMap,
								geneID2GeneHugoSymbolMap,
								annotationType);

						element.setName(tforHistoneNameCellLineNameKeggPathwayNameGeneHugoSymbol);

						// in case of element contains a KEGG PATHWAY
						// We are setting keggPathwayNumber for KEGGPATHWAY
						// INFORMATION augmentation
						// such as KEGGPathway description, geneID List of
						// genes, hugoSymbols of genes in this KEGG Pathway
						if(generatedMixedNumberDescriptionOrderLengthForWithZScoreMode.is_INT_4DIGIT_KEGGPATHWAYNUMBER() || 
								generatedMixedNumberDescriptionOrderLengthForWithZScoreMode.is_INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER() || 
								generatedMixedNumberDescriptionOrderLengthForWithZScoreMode.is_LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER()){

							keggPathwayNumber = IntervalTree.getGeneSetNumber(mixedNumber,generatedMixedNumberDescriptionOrderLengthForWithZScoreMode);
							element.setKeggPathwayNumber(keggPathwayNumber);

						}
						// Set keggPathwayNumber
						element.setOriginalNumberofOverlaps(originalNumberofOverlaps);
						element.setNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps(numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps);

						elementNumber2ElementMap.put(mixedNumber, element);
					}else{

						element = elementNumber2ElementMap.get(mixedNumber);
						element.setNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps);

					}

				}// End of Outer WHILE loop: Read all lines of a run

				// Close bufferedReader
				bufferedReader.close();
				fileReader.close();

			}// End of for: each run
			/************************************************************************************/
			/*********************** FOR EACH RUN ENDS ******************************************/
			/************************************************************************************/
			
			// 13 May 2015 starts
			// Compute mean of permutationNumberofOverlapsList for each element
			// Compute standard deviation of permutationNumberofOverlapsList for each element
			// Compute zScore for each element
			for(TLongIntIterator it = elementNumber2OriginalNumberofOverlaps.iterator(); it.hasNext();){

				it.advance();

				elementNumber = it.key();
				originalNumberofOverlaps = it.value();

				statsPerElement = elementNumber2StatsMap.get(elementNumber);

				element = elementNumber2ElementMap.get(elementNumber);

				if(statsPerElement.getValues().length > 0){

					mean 	= statsPerElement.getMean();
					stdDev 	= statsPerElement.getStandardDeviation();

					element.setMean(mean);
					element.setStdDev(stdDev);

				}else{

					element.setMean(null);
					element.setStdDev(null);
				}
				

				// 27 May 2015
				if(element.getStdDev() == null || element.getStdDev() == 0){
					

					// For these cases
					// Do not calculate zScore
					// Otherwise we will encounter
					// Division by zero
					// Division by not a number
					// Errors
					
					// zScore is infinity and further calculations are not applicable

					element.setZScore(null);
					element.setEmpiricalPValueCalculatedFromZScore(null);
					element.setBonferroniCorrectedPValueCalculatedFromZScore(null);

				}// END of IF

				else{

					zScore = (originalNumberofOverlaps - mean) / stdDev;
					empiricalPValueCalculatedFromZScore = StatisticsConversion.cumulativeProbability(zScore);
					bonferroniCorrectedPValueCalculatedFromZScore = empiricalPValueCalculatedFromZScore * numberofComparisons;

					element.setZScore(zScore);
					element.setEmpiricalPValueCalculatedFromZScore(empiricalPValueCalculatedFromZScore);

					if(bonferroniCorrectedPValueCalculatedFromZScore > 1.0){
						element.setBonferroniCorrectedPValueCalculatedFromZScore(1.0);
					}else{
						element.setBonferroniCorrectedPValueCalculatedFromZScore(bonferroniCorrectedPValueCalculatedFromZScore);
					}

				}// End of ELSE stdDev != 0 or stdDev!= bull

			}// End of for
			// 13 May 2015 ends


			/****************************************************************************************************************************/
			/*****SORT w.r.t. zScore in descending order starts**************************************************************************/
			/****************************************************************************************************************************/
			List<FunctionalElement> list = new ArrayList<FunctionalElement>(elementNumber2ElementMap.values());
			// Why do we sort here?
			//Collections.sort(list, FunctionalElement.Z_SCORE);
			/****************************************************************************************************************************/
			/*****SORT w.r.t. zScore in descending order ends****************************************************************************/
			/****************************************************************************************************************************/

			/************************************************************************************************/
			/************ COMPUTE BENJAMINI HOCHBERG FDR ADJUSTED P VALUE FROM ZSCORE STARTS ****************/
			/************************************************************************************************/
			// Before calculating BH FDR adjusted p values
			// Sort w.r.t. Empirical P Value calculated From Z Scores in Ascending Order
			Collections.sort(list, FunctionalElement.EMPIRICAL_P_VALUE_CALCULATED_FROM_Z_SCORE);
			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValuesFromZScores(list, FDR);
			// Why do we sort here?
			// Sort w.r.t. Benjamini and Hochberg FDR is Ascending Order
			//Collections.sort(list, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE_CALCULATED_FROM_Z_SCORE);
			/************************************************************************************************/
			/************ COMPUTE BENJAMINI HOCHBERG FDR ADJUSTED P VALUE FROM ZSCORE ENDS ******************/
			/************************************************************************************************/


			/************************************************************************************/
			/******* COMPUTE EMPIRICAL P VALUE AND BONFERRONI CORRECTED P VALUE STARTS***********/
			/************************************************************************************/
			// Now compute empirical pValue and Bonferroni Corrected pValue and write
			for(Map.Entry<Long, FunctionalElement> entry : elementNumber2ElementMap.entrySet()){

				mixedNumber = entry.getKey();
				element = entry.getValue();

				numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps();

				empiricalPValue = (numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps * 1.0f) / numberofPermutations;
				bonferroniCorrectedEmpiricalPValue = empiricalPValue * numberofComparisons;

				if(bonferroniCorrectedEmpiricalPValue > 1.0f){
					bonferroniCorrectedEmpiricalPValue = 1.0f;
				}

				element.setEmpiricalPValue(empiricalPValue);
				element.setBonferroniCorrectedPValue(bonferroniCorrectedEmpiricalPValue);

			}
			/************************************************************************************/
			/****** COMPUTE EMPIRICAL P VALUE AND BONFERRONI CORRECTED P VALUE ENDS *************/
			/************************************************************************************/

			/************************************************************************************/
			/************ COMPUTE BENJAMINI HOCHBERG FDR ADJUSTED P VALUE STARTS ****************/
			/************************************************************************************/
			// convert map.values() into a list
			// sort w.r.t. empirical p value
			// Before calculating BH FDR adjusted p values
			list = new ArrayList<FunctionalElement>(elementNumber2ElementMap.values());

			Collections.sort(list, FunctionalElement.EMPIRICAL_P_VALUE);
			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(list, FDR);
			
			//Why do we sort here?
			// sort w.r.t. Benjamini and Hochberg FDR
			//Collections.sort(list, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
			/************************************************************************************/
			/************ COMPUTE BENJAMINI HOCHBERG FDR ADJUSTED P VALUE ENDS ******************/
			/************************************************************************************/

			/************************************************************************************/
			/***************** AUGMENT WITH USERDEFINEDGENESET INFORMATION STARTS ***************/
			/************************************************************************************/
			if(annotationType.doUserDefinedGeneSetAnnotation() && userDefinedGeneSetOptionalDescriptionInputFile != null && !userDefinedGeneSetOptionalDescriptionInputFile.equals(Commons.NO_OPTIONAL_USERDEFINEDGENESET_DESCRIPTION_FILE_PROVIDED)){

				UserDefinedGeneSetUtility.augmentUserDefinedGeneSetIDwithTerm(userDefinedGeneSetOptionalDescriptionInputFile, list);

			}
			/************************************************************************************/
			/***************** AUGMENT WITH USERDEFINEDGENESET INFORMATION ENDS *****************/
			/************************************************************************************/

			/************************************************************************************/
			/***************** AUGMENT WITH KEGG PATHWAY INFORMATION STARTS *********************/
			/************************************************************************************/
			if(annotationType.doKEGGPathwayAnnotation()){

				// Augment KeggPathwayNumber with KeggPathwayEntry
				KeggPathwayAugmentation.augmentKeggPathwayNumberWithKeggPathwayEntry(keggPathwayNumber2NameMap, list);
				// Augment KEGGPathwayEntry with KeggPathwayName starts
				KeggPathwayAugmentation.augmentKeggPathwayEntrywithKeggPathwayName(dataFolder, list, null, null);
				// Augment with KEGGPathway Gene List and Alternate Gene Name List
				KeggPathwayAugmentation.augmentKeggPathwayEntrywithKeggPathwayGeneList(dataFolder, outputFolder, list,null, null);

			}else if(annotationType.doTFKEGGPathwayAnnotation()){
				// Augment KeggPathwayNumber with KeggPathwayEntry
				KeggPathwayAugmentation.augmentKeggPathwayNumberWithKeggPathwayEntry(keggPathwayNumber2NameMap, list);
				KeggPathwayAugmentation.augmentTfNameKeggPathwayEntrywithKeggPathwayName(dataFolder, list, null, null);
				KeggPathwayAugmentation.augmentTfNameKeggPathwayEntrywithKeggPathwayGeneList(dataFolder, outputFolder,list, null, null);

			}else if(annotationType.doTFCellLineKEGGPathwayAnnotation()){
				// Augment KeggPathwayNumber with KeggPathwayEntry
				KeggPathwayAugmentation.augmentKeggPathwayNumberWithKeggPathwayEntry(keggPathwayNumber2NameMap, list);
				KeggPathwayAugmentation.augmentTfNameCellLineNameKeggPathwayEntrywithKeggPathwayName(dataFolder, list,null, null);
				KeggPathwayAugmentation.augmentTfNameCellLineNameKeggPathwayEntrywithKeggPathwayGeneList(dataFolder,outputFolder, list, null, null);

			}
			/************************************************************************************/
			/***************** AUGMENT WITH KEGG PATHWAY INFORMATION ENDS ***********************/
			/************************************************************************************/

			/************************************************************************************/
			/****************************** WRITE RESULTS STARTS ********************************/
			/************************************************************************************/
			// How to decide enriched elements?
			// with respect to Benjamini Hochberg FDR or
			// with respect to Bonferroni Correction Significance Level
			
			//In this class 
			//zScores are presented
			//empiricalPValue, bonferroniCorrectedPValue and BHFDRAdjustedPValue calculated from ZScore are presented
			//empiricalPValue, bonferroniCorrectedPValue and BHFDRAdjustedPValue calculated from ratio of numberofPermutationsThatHasNumberofOverlapsGreaterThanEqualToOriginalNumberofOverlaps and numberofPermutations are presented
			//Results are sorted in ascending order w.r.t. multipleTestingParameter
			//which can be BHFDRAdjustedPValue calculated from ratio of numberofPermutations.... or bonferroniCorrectedPValue calculated from ratio of numberofPermutations....
			writeResults_WRT_MultipleTestingParameter_WithZScores(
					outputFolder, 
					allFileName, 
					jobName, 
					list, 
					annotationType,
					multipleTestingParameter, 
					bonferroniCorrectionSignigicanceLevel, 
					FDR, 
					numberofPermutations,
					numberofComparisons);
			
			
			//25 August 2016
			//If this a data driven computation experiment run do not write this file.
			if(glanetRunType.equalsIgnoreCase(Commons.ARG_GLANET_NORMAL_RUN)){
				
				//In this class 
				//zScores are presented
				//empiricalPValue, bonferroniCorrectedPValue and BHFDRAdjustedPValue calculated from ZScore are presented
				//empiricalPValue, bonferroniCorrectedPValue and BHFDRAdjustedPValue calculated from ratio of numberofPermutationsThatHasNumberofOverlapsGreaterThanEqualToOriginalNumberofOverlaps and numberofPermutations are presented
				//Results are sorted in descending order w.r.t. ZScore
				writeResults_WRT_ZScores_WithMultipleTestingParameter(
						outputFolder, 
						allFileName, 
						jobName, 
						list, 
						annotationType,
						multipleTestingParameter, 
						bonferroniCorrectionSignigicanceLevel, 
						FDR, 
						numberofPermutations,
						numberofComparisons);
				
			}//End of IF
			

			
			/************************************************************************************/
			/****************************** WRITE RESULTS ENDS **********************************/
			/************************************************************************************/

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	//11 Sep 2015
	public static void collectEnrichmentResultsWithoutZScores(
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
			AnnotationType annotationType,
			String userDefinedGeneSetOptionalDescriptionInputFile, 
			String elementType,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength,
			int numberofPermutations){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine;

		int originalNumberofOverlaps;
		int numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps;
		
		float empiricalPValue;
		float bonferroniCorrectedEmpiricalPValue;

		Map<Long, FunctionalElement> elementNumber2ElementMap = new HashMap<Long, FunctionalElement>();
		FunctionalElement element;
		long mixedNumber;

		String elementName = null;

		// In case of functionalElement contains KEGG Pathway
		int keggPathwayNumber;

		String tempRunName;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		List<FunctionalElement> list = null;
		
		
		try{

			/************************************************************************************/
			/*********************** FOR EACH RUN STARTS ****************************************/
			/************************************************************************************/
			for(int i = 1; i <= numberofRuns; i++){

				tempRunName = "_" + jobName + "_" + Commons.RUN + i;

				fileReader = new FileReader(outputFolder + runFileName + tempRunName + ".txt");
				bufferedReader = new BufferedReader(fileReader);
				
				//Skip Header Line
				bufferedReader.readLine();
				
				// Outer While
				while((strLine = bufferedReader.readLine()) != null){

					// Initialize numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps to zero
					originalNumberofOverlaps = 0;
					numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = 0;

					indexofFirstTab  = strLine.indexOf('\t');
					indexofSecondTab = (indexofFirstTab>0) ? strLine.indexOf('\t', indexofFirstTab+1) : -1;
					indexofThirdTab  = (indexofSecondTab>0) ? strLine.indexOf('\t', indexofSecondTab+1): -1;
				
					// PermutationNumber does not exists
					mixedNumber = Long.parseLong(strLine.substring(0, indexofFirstTab));
					if (indexofThirdTab!=-1){
						elementName = strLine.substring(indexofFirstTab+1,indexofSecondTab);
						originalNumberofOverlaps = Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
						numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = Integer.parseInt(strLine.substring(indexofThirdTab+1));
					}
					
					// debug starts
					if(mixedNumber < 0){
						if(GlanetRunner.shouldLog()) logger.error("There is a situation 1");
						if(GlanetRunner.shouldLog()) logger.error(mixedNumber);
					}
					// debug ends

					// mixedNumber can be in one of these formats
					// INT_10DIGIT_DNASECELLLINENUMBER
					// INT_10DIGIT_GENENUMBER
					// INT_10DIGIT_USERDEFINEDGENESETNUMBER
					// INT_10DIGIT_KEGGPATHWAYNUMBER
					// INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER
					// INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER
					// LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER

					
					// Write numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps to map
					if(elementNumber2ElementMap.get(mixedNumber) == null){
						element = new FunctionalElement();

						element.setNumber(mixedNumber);

						element.setName(elementName);
						
						element.setOriginalNumberofOverlaps(originalNumberofOverlaps);

						// In case of element contains a KEGG PATHWAY
						// We are setting keggPathwayNumber for KEGGPATHWAY
						// INFORMATION augmentation such as KEGGPathway description, 
						// geneID List of genes, hugoSymbols of genes in this KEGG Pathway
						if(generatedMixedNumberDescriptionOrderLength.is_INT_10DIGIT_KEGGPATHWAYNUMBER() || 
								generatedMixedNumberDescriptionOrderLength.is_INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER() || 
								generatedMixedNumberDescriptionOrderLength.is_LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER()){

							keggPathwayNumber = IntervalTree.getGeneSetNumber(mixedNumber,generatedMixedNumberDescriptionOrderLength);
							// Set KEGGPathwayNumber
							element.setKeggPathwayNumber(keggPathwayNumber);

						}
						element.setNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps(numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps);
						elementNumber2ElementMap.put(mixedNumber, element);
					}
					//There are more than one runs
					else{

						element = elementNumber2ElementMap.get(mixedNumber);
						//Accumulate
						element.setNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps);

					}

				}// End of Outer WHILE loop: Read all lines of a run

				// Close bufferedReader
				bufferedReader.close();
				fileReader.close();

			}// End of for: each run
			/************************************************************************************/
			/*********************** FOR EACH RUN ENDS ******************************************/
			/************************************************************************************/
			
			/************************************************************************************/
			/******* COMPUTE EMPIRICAL P VALUE AND BONFERRONI CORRECTED P VALUE STARTS***********/
			/************************************************************************************/
			// Now compute empirical pValue and Bonferroni Corrected pValue and write
			for(Map.Entry<Long, FunctionalElement> entry : elementNumber2ElementMap.entrySet()){

				mixedNumber = entry.getKey();
				element = entry.getValue();

				numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps();

				empiricalPValue = (numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps * 1.0f) / numberofPermutations;
				bonferroniCorrectedEmpiricalPValue = empiricalPValue * numberofComparisons;

				if(bonferroniCorrectedEmpiricalPValue > 1.0f){
					bonferroniCorrectedEmpiricalPValue = 1.0f;
				}

				element.setEmpiricalPValue(empiricalPValue);
				element.setBonferroniCorrectedPValue(bonferroniCorrectedEmpiricalPValue);

			}
			/************************************************************************************/
			/****** COMPUTE EMPIRICAL P VALUE AND BONFERRONI CORRECTED P VALUE ENDS *************/
			/************************************************************************************/

			
			/************************************************************************************/
			/************ COMPUTE BENJAMINI HOCHBERG FDR ADJUSTED P VALUE STARTS ****************/
			/************************************************************************************/
			// convert map.values() into a list
			// sort w.r.t. empirical p value
			// Before calculating BH FDR adjusted p values
			list = new ArrayList<FunctionalElement>(elementNumber2ElementMap.values());

			Collections.sort(list, FunctionalElement.EMPIRICAL_P_VALUE);
			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(list, FDR);
			/************************************************************************************/
			/************ COMPUTE BENJAMINI HOCHBERG FDR ADJUSTED P VALUE ENDS ******************/
			/************************************************************************************/

			
			/************************************************************************************/
			/***************** AUGMENT WITH USERDEFINEDGENESET INFORMATION STARTS ***************/
			/************************************************************************************/
			if(annotationType.doUserDefinedGeneSetAnnotation() && userDefinedGeneSetOptionalDescriptionInputFile != null && !userDefinedGeneSetOptionalDescriptionInputFile.equals(Commons.NO_OPTIONAL_USERDEFINEDGENESET_DESCRIPTION_FILE_PROVIDED)){

				UserDefinedGeneSetUtility.augmentUserDefinedGeneSetIDwithTerm(userDefinedGeneSetOptionalDescriptionInputFile, list);

			}
			/************************************************************************************/
			/***************** AUGMENT WITH USERDEFINEDGENESET INFORMATION ENDS *****************/
			/************************************************************************************/

			
			/************************************************************************************/
			/***************** AUGMENT WITH KEGG PATHWAY INFORMATION STARTS *********************/
			/************************************************************************************/
			if (annotationType.doKEGGPathwayAnnotation() ||
				annotationType.doTFKEGGPathwayAnnotation() ||
				annotationType.doTFCellLineKEGGPathwayAnnotation()){
				
				keggPathwayNumber2NameMap = getKeggPathwayNumber2NameMapInstance(dataFolder);
		
			}
			
			
			if(annotationType.doKEGGPathwayAnnotation()){

				// Augment KeggPathwayNumber with KeggPathwayEntry
				KeggPathwayAugmentation.augmentKeggPathwayNumberWithKeggPathwayEntry(keggPathwayNumber2NameMap, list);
				// Augment KeggPathwayEntry with KeggPathwayName starts
				KeggPathwayAugmentation.augmentKeggPathwayEntrywithKeggPathwayName(dataFolder, list, null, null);
				// Augment with Kegg Pathway Gene List and Alternate Gene Name List
				KeggPathwayAugmentation.augmentKeggPathwayEntrywithKeggPathwayGeneList(dataFolder, outputFolder, list,null, null);

			}else if(annotationType.doTFKEGGPathwayAnnotation()){
				// Augment KeggPathwayNumber with KeggPathwayEntry
				KeggPathwayAugmentation.augmentKeggPathwayNumberWithKeggPathwayEntry(keggPathwayNumber2NameMap, list);
				KeggPathwayAugmentation.augmentTfNameKeggPathwayEntrywithKeggPathwayName(dataFolder, list, null, null);
				KeggPathwayAugmentation.augmentTfNameKeggPathwayEntrywithKeggPathwayGeneList(dataFolder, outputFolder,list, null, null);

			}else if(annotationType.doTFCellLineKEGGPathwayAnnotation()){
				// Augment KeggPathwayNumber with KeggPathwayEntry
				KeggPathwayAugmentation.augmentKeggPathwayNumberWithKeggPathwayEntry(keggPathwayNumber2NameMap, list);
				KeggPathwayAugmentation.augmentTfNameCellLineNameKeggPathwayEntrywithKeggPathwayName(dataFolder, list,null, null);
				KeggPathwayAugmentation.augmentTfNameCellLineNameKeggPathwayEntrywithKeggPathwayGeneList(dataFolder,outputFolder, list, null, null);

			}
			/************************************************************************************/
			/***************** AUGMENT WITH KEGG PATHWAY INFORMATION ENDS ***********************/
			/************************************************************************************/
			
			writeResults_WRT_MultipleTestingParameter_WithoutZScores(
					outputFolder, 
					allFileName, 
					jobName, 
					list, 
					annotationType,
					multipleTestingParameter, 
					bonferroniCorrectionSignigicanceLevel, 
					FDR, 
					numberofPermutations,
					numberofComparisons);


			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	
		
	}
	

	
	//11 Sep 2015
	public static void fillNumberToNameMaps(
			AnnotationType annotationType,
			String dataFolder,
			String elementType,
			TIntObjectMap<String> cellLineNumber2NameMap,
			TIntObjectMap<String> tfNumber2NameMap,
			TIntObjectMap<String> histoneNumber2NameMap,
			TIntObjectMap<String> geneHugoSymbolNumber2NameMap,
			TIntObjectMap<String> keggPathwayNumber2NameMap,
			TIntObjectMap<String> userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap,
			TIntObjectMap<String> userDefinedLibraryElementNumber2ElementNameMap,
			TIntObjectMap<String> geneID2GeneHugoSymbolMap){
		
		// Here using same variable name "cellLineNumber2NameMap" for case
		// DO_DNASE_ENRICHMENT and case DO_TF_ENRICHMENT is not important
		// How it is filled is important, I mean its source
		// For example in case DO_DNASE_ENRICHMENT cellLineNumber2NameMap is filled by using
		// ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME
		// For example in case DO_TF_ENRICHMENT cellLineNumber2NameMap is filled by using
		// ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME

		switch(annotationType){

			case DO_DNASE_ANNOTATION:{
				FileOperations.fillNumber2NameMap(cellLineNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
				break;
			}
		
			case DO_TF_ANNOTATION:{
				FileOperations.fillNumber2NameMap(cellLineNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
				FileOperations.fillNumber2NameMap(tfNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
				break;
			}
		
			case DO_HISTONE_ANNOTATION:{
				FileOperations.fillNumber2NameMap(cellLineNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
				FileOperations.fillNumber2NameMap(histoneNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_HISTONE_NUMBER_2_NAME_OUTPUT_FILENAME);
				break;
			}
		
			case DO_KEGGPATHWAY_ANNOTATION:{
				FileOperations.fillNumber2NameMap(geneHugoSymbolNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NUMBER_2_NAME_OUTPUT_FILENAME);
				FileOperations.fillNumber2NameMap(keggPathwayNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
				break;
			}
			case DO_TF_KEGGPATHWAY_ANNOTATION:{
				FileOperations.fillNumber2NameMap(tfNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
				FileOperations.fillNumber2NameMap(geneHugoSymbolNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NUMBER_2_NAME_OUTPUT_FILENAME);
				FileOperations.fillNumber2NameMap(keggPathwayNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
				break;
			}
		
			case DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION:{
				FileOperations.fillNumber2NameMap(cellLineNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
				FileOperations.fillNumber2NameMap(tfNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
				FileOperations.fillNumber2NameMap(geneHugoSymbolNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NUMBER_2_NAME_OUTPUT_FILENAME);
				FileOperations.fillNumber2NameMap(keggPathwayNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
				break;
			}
			case DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION:{
				FileOperations.fillNumber2NameMap(cellLineNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
				FileOperations.fillNumber2NameMap(tfNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
				FileOperations.fillNumber2NameMap(geneHugoSymbolNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NUMBER_2_NAME_OUTPUT_FILENAME);
				FileOperations.fillNumber2NameMap(keggPathwayNumber2NameMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
				break;
			}
			case DO_USER_DEFINED_GENESET_ANNOTATION:{
				FileOperations.fillNumber2NameMap(userDefinedGeneSetNumber2UserDefinedGeneSetEntryMap,
						dataFolder + Commons.ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME,
						Commons.ALL_POSSIBLE_USERDEFINEDGENESET_NUMBER_2_NAME_OUTPUT_FILENAME);
				break;
			}
		
			case DO_USER_DEFINED_LIBRARY_ANNOTATION:{
				
				// Fill elmentType based elementNumber2ElementNameMap
				UserDefinedLibraryUtility.fillNumber2NameMap(
						userDefinedLibraryElementNumber2ElementNameMap,
						dataFolder,
						Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME + elementType + System.getProperty("file.separator"),
						Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENT_NUMBER_2_NAME_OUTPUT_FILENAME);
		
				break;
			}
		
			case DO_GENE_ANNOTATION:{
				HumanGenesAugmentation.fillGeneId2GeneHugoSymbolMap(dataFolder, geneID2GeneHugoSymbolMap);
				break;
			}
			default:{
				break;
			}

		}// End of Switch
		/************************************************************************************/
		/*********************** FILL NUMBER TO NAME MAP ENDS *******************************/
		/************************************************************************************/

		
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
			AnnotationType annotationType,
			String userDefinedGeneSetOptionalDescriptionInputFile, 
			String elementType,
			GeneratedMixedNumberDescriptionOrderLength mixedNumberDescriptionOrderLengthWithZScores,
			GeneratedMixedNumberDescriptionOrderLength mixedNumberDescriptionOrderLengthWithoutZScores,
			EnrichmentZScoreMode enrichmentZScoreMode,
			String glanetRunType) {
		
		/*************************************************************************/
		/*******************Common Variable Starts********************************/
		/******************WithZScores and WithoutZScores*************************/
		/*************************************************************************/
		int numberofPermutations;		
		/*************************************************************************/
		/*******************Common Variable Ends**********************************/
		/******************WithZScores and WithoutZScores*************************/
		/*************************************************************************/
		
		
		
		/*************************************************************************/
		/*******************Compute numberofPermutations starts*******************/
		/*************************************************************************/
		if(numberofRemainders > 0){
			numberofPermutations = ((numberofRuns - 1) * numberofPermutationsInEachRun) + numberofRemainders;
		}else{
			numberofPermutations = numberofRuns * numberofPermutationsInEachRun;
		}
		/*************************************************************************/
		/*******************Compute numberofPermutations ends*********************/
		/*************************************************************************/

				

		
		switch(enrichmentZScoreMode){
		
			case PerformEnrichmentWithZScore: 
				CollectionofPermutationsResults.collectEnrichmentResultsWithZScores(
						numberofPermutationsInEachRun,
						bonferroniCorrectionSignigicanceLevel, 
						FDR, 
						multipleTestingParameter,
						dataFolder, 
						outputFolder, 
						runFileName, 
						allFileName, 
						jobName,
						numberofRuns, 
						numberofRemainders, 
						numberofComparisons, 
						annotationType,
						userDefinedGeneSetOptionalDescriptionInputFile, 
						elementType,
						mixedNumberDescriptionOrderLengthWithZScores,
						numberofPermutations,
						glanetRunType);
				break;
				
			case PerformEnrichmentWithoutZScore:
				CollectionofPermutationsResults.collectEnrichmentResultsWithoutZScores(
						numberofPermutationsInEachRun,
						bonferroniCorrectionSignigicanceLevel, 
						FDR, 
						multipleTestingParameter,
						dataFolder, 
						outputFolder, 
						runFileName, 
						allFileName, 
						jobName,
						numberofRuns, 
						numberofRemainders, 
						numberofComparisons, 
						annotationType,
						userDefinedGeneSetOptionalDescriptionInputFile, 
						elementType,
						mixedNumberDescriptionOrderLengthWithoutZScores,
						numberofPermutations);
				break;
				
		}//End of SWITCH



	}

	/**
	 * 
	 */
	public CollectionofPermutationsResults() {

		// TODO Auto-generated constructor stub
	}

	// args[0] ---> Input File Name with folder
	// args[1] ---> GLANET installation folder with "\\" at the end. This folder
	// will be used for outputFolder and dataFolder.
	// args[2] ---> Input File Format
	// ---> default
	// Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// args[3] ---> Annotation, overlap definition, number of bases,
	// default 1
	// args[4] ---> Perform Enrichment parameter
	// ---> default Commons.DO_ENRICH
	// ---> Commons.DO_NOT_ENRICH
	// args[5] ---> Generate Random Data Mode
	// ---> default Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	// ---> Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT
	// args[6] ---> multiple testing parameter, enriched elements will be
	// decided and sorted with respect to this parameter
	// ---> default Commons.BENJAMINI_HOCHBERG_FDR
	// ---> Commons.BONFERRONI_CORRECTION
	// args[7] ---> Bonferroni Correction Significance Level, default 0.05
	// args[8] ---> Bonferroni Correction Significance Criteria, default 0.05
	// args[9] ---> Number of permutations, default 5000
	// args[10] ---> Dnase Enrichment
	// ---> default Commons.DO_NOT_DNASE_ENRICHMENT
	// ---> Commons.DO_DNASE_ENRICHMENT
	// args[11] ---> Histone Enrichment
	// ---> default Commons.DO_NOT_HISTONE_ENRICHMENT
	// ---> Commons.DO_HISTONE_ENRICHMENT
	// args[12] ---> Transcription Factor(TF) Enrichment
	// ---> default Commons.DO_NOT_TF_ENRICHMENT
	// ---> Commons.DO_TF_ENRICHMENT
	// args[13] ---> KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_KEGGPATHWAY_ENRICHMENT
	// args[14] ---> TF and KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	// args[15] ---> TF and CellLine and KeggPathway Enrichment
	// ---> default Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// args[16] ---> RSAT parameter
	// ---> default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// ---> Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// args[17] ---> job name example: ocd_gwas_snps
	// args[18] ---> writeGeneratedRandomDataMode checkBox
	// ---> default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	// ---> Commons.WRITE_GENERATED_RANDOM_DATA
	// args[19] ---> writePermutationBasedandParametricBasedAnnotationResultMode
	// checkBox
	// ---> default
	// Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// --->
	// Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// args[20] ---> writePermutationBasedAnnotationResultMode checkBox
	// ---> default Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	// ---> Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	// args[21] ---> number of permutations in each run. Default is 2000
	// args[22] ---> UserDefinedGeneSet Enrichment
	// default Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT
	// Commons.DO_USER_DEFINED_GENESET_ENRICHMENT
	// args[23] ---> UserDefinedGeneSet InputFile
	// args[24] ---> UserDefinedGeneSet GeneInformationType
	// default Commons.GENE_ID
	// Commons.GENE_SYMBOL
	// Commons.RNA_NUCLEOTIDE_ACCESSION
	// args[25] ---> UserDefinedGeneSet Name
	// args[26] ---> UserDefinedGeneSet Optional GeneSet Description InputFile
	// args[27] ---> UserDefinedLibrary Enrichment
	// default Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT
	// Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT
	// args[28] ---> UserDefinedLibrary InputFile
	// args[29] - args[args.length-1] ---> Note that the selected cell lines are
	// always inserted at the end of the args array because it's size
	// is not fixed. So for not (until the next change on args array) the
	// selected cell
	// lines can be reached starting from 22th index up until (args.length-1)th
	// index.
	// If no cell line selected so the args.length-1 will be 22-1 = 21. So it
	// will never
	// give an out of boundry exception in a for loop with this approach.
	public static void main(String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];

		// jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if(jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		// jobName ends
		String dataFolder 	= glanetFolder + Commons.DATA + System.getProperty("file.separator");
		String outputFolder = args[CommandLineArguments.OutputFolder.value()];

		
		//14 Nov 2016 starts
		AnnotationType dnaseAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.DnaseAnnotation.value()]);
		AnnotationType histoneAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.HistoneAnnotation.value()]);
		AnnotationType tfAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.TfAnnotation.value()]);
		AnnotationType geneAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.GeneAnnotation.value()]);		
		
		AnnotationType bpGOTermAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.BPGOTermsAnnotation.value()]);		
		AnnotationType mfGOTermAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.MFGOTermsAnnotation.value()]);		
		AnnotationType ccGOTermAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.CCGOTermsAnnotation.value()]);		
		
		AnnotationType keggPathwayAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.KeggPathwayAnnotation.value()]);
		AnnotationType tfKeggPathwayAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.TfAndKeggPathwayAnnotation.value()]);
		AnnotationType tfCellLineKeggPathwayAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()]);
		//14 Nov 2016 ends
		
		/***********************************************************************************/
		/************************** USER DEFINED GENESET ***********************************/
		// User Defined GeneSet Enrichment, DO or DO_NOT
		AnnotationType userDefinedGeneSetAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.UserDefinedGeneSetAnnotation.value()]);

		String userDefinedGeneSetName = args[CommandLineArguments.UserDefinedGeneSetName.value()];
		// String userDefinedGeneSetName = "GO";

		String userDefinedGeneSetDescriptionOptionalInputFile = args[CommandLineArguments.UserDefinedGeneSetDescriptionFile.value()];
		// String userDefinedGeneSetDescriptionOptionalInputFile =
		// "G:\\DOKTORA_DATA\\GO\\GO_terms_and_ids.txt";
		/************************** USER DEFINED GENESET ***********************************/
		/***********************************************************************************/

		/***********************************************************************************/
		/************************** USER DEFINED LIBRARY ***********************************/
		// User Defined Library Enrichment, DO or DO_NOT
		AnnotationType userDefinedLibraryAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.UserDefinedLibraryAnnotation.value()]);

		// String userDefinedLibraryInputFile = args[28];
		// String userDefinedLibraryInputFile =
		// "C:\\Users\\burcakotlu\\GLANET\\UserDefinedLibraryInputFile.txt";
		/************************** USER DEFINED LIBRARY ***********************************/
		/***********************************************************************************/
		
		
		NumberofComparisons numberofComparisons = NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(
				dataFolder,
				dnaseAnnotationType,
				histoneAnnotationType,
				tfAnnotationType,
				geneAnnotationType,
				bpGOTermAnnotationType,
				mfGOTermAnnotationType,
				ccGOTermAnnotationType,
				keggPathwayAnnotationType,
				tfKeggPathwayAnnotationType,
				tfCellLineKeggPathwayAnnotationType,
				userDefinedGeneSetAnnotationType,
				userDefinedLibraryAnnotationType);

		int numberofPermutations = Integer.parseInt(args[CommandLineArguments.NumberOfPermutation.value()]);

		float FDR = Float.parseFloat(args[CommandLineArguments.FalseDiscoveryRate.value()]);
		float bonferroniCorrectionSignificanceLevel = Float.parseFloat(args[CommandLineArguments.BonferroniCorrectionSignificanceCriteria.value()]);

		// set the number of permutations in each run
		int numberofPermutationsInEachRun = Integer.parseInt(args[CommandLineArguments.NumberOfPermutationsInEachRun.value()]);

		int numberofRuns = 0;
		int numberofRemainders = 0;

		// Multiple Testing Parameter for selection of enriched elements
		MultipleTestingType multipleTestingParameter = MultipleTestingType.convertStringtoEnum(args[CommandLineArguments.MultipleTesting.value()]);

		numberofRuns = numberofPermutations / numberofPermutationsInEachRun;
		numberofRemainders = numberofPermutations % numberofPermutationsInEachRun;

		// Increase numberofRuns by 1 
		// When numberofRemainders is nonzero
		if(numberofRemainders > 0){
			numberofRuns += 1;
		}

		EnrichmentZScoreMode enrichmentZScoreMode = EnrichmentZScoreMode.convertStringtoEnum(args[CommandLineArguments.EnrichmentZScoreMode.value()]);
		
		//Whether this is a normal run or data driven computational experiment run
		String glanetRunType = args[CommandLineArguments.GLANETRun.value()];
		
		
		/************************************************************/
		/************ Collection of DNASE RESULTS starts ************/
		if(dnaseAnnotationType.doDnaseAnnotation()){
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
					dnaseAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_DNASECELLLINENUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_DNASECELLLINENUMBER,
					enrichmentZScoreMode,
					glanetRunType);
		}
		/************ Collection of DNASE RESULTS ends **************/
		/************************************************************/

		/************************************************************/
		/************ Collection of HISTONE RESULTS starts **********/
		if(histoneAnnotationType.doHistoneAnnotation()){

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
					histoneAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_HISTONENUMBER_4DIGIT_CELLLINENUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER,
					enrichmentZScoreMode,
					glanetRunType);
		}
		/************ Collection of HISTONE RESULTS ends ************/
		/************************************************************/

		/************************************************************/
		/************ Collection of TF RESULTS starts ***************/
		if(tfAnnotationType.doTFAnnotation() && 
				!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
				!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){

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
					tfAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER,
					enrichmentZScoreMode,
					glanetRunType);
		}
		/************ Collection of TF RESULTS ends *****************/
		/************************************************************/

		/************************************************************/
		/************ Collection of Gene RESULTS starts *************/
		if(geneAnnotationType.doGeneAnnotation()){

			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, 
					FDR, 
					multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_GENE_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_GENE, 
					jobName, 
					numberofRuns, 
					numberofRemainders,
					numberofComparisons.getGeneNumberofComparison(), 
					geneAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GENENUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GENENUMBER,
					enrichmentZScoreMode,
					glanetRunType);
		}
		/************ Collection of Gene RESULTS starts *************/
		/************************************************************/

		/****************************************************************************/
		/************ Collection of UserDefinedGeneSet RESULTS starts ***************/
		if(userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){

			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.EXON_BASED + "_" + userDefinedGeneSetName,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.EXON_BASED  + "_" + userDefinedGeneSetName,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getExonBasedUserDefinedGeneSetNumberofComparison(),
					userDefinedGeneSetAnnotationType, 
					userDefinedGeneSetDescriptionOptionalInputFile, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_USERDEFINEDGENESETNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_USERDEFINEDGENESETNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.REGULATION_BASED + "_" + userDefinedGeneSetName,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.REGULATION_BASED + "_" + userDefinedGeneSetName,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getRegulationBasedUserDefinedGeneSetNumberofComparison(),
					userDefinedGeneSetAnnotationType, userDefinedGeneSetDescriptionOptionalInputFile, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_USERDEFINEDGENESETNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_USERDEFINEDGENESETNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.ALL_BASED + "_" + userDefinedGeneSetName,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.ALL_BASED + "_" + userDefinedGeneSetName,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getAllBasedUserDefinedGeneSetNumberofComparison(),
					userDefinedGeneSetAnnotationType, userDefinedGeneSetDescriptionOptionalInputFile, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_USERDEFINEDGENESETNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_USERDEFINEDGENESETNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

		}
		/************ Collection of UserDefinedGeneSet RESULTS ends *****************/
		/****************************************************************************/

		/****************************************************************************/
		/************ Collection of UserDefinedLibrary RESULTS starts ***************/
		if(userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){

			TIntObjectMap<String> elementTypeNumber2ElementTypeMap = new TIntObjectHashMap<String>();

			int elementTypeNumber;
			String elementType;

			UserDefinedLibraryUtility.fillNumber2NameMap(elementTypeNumber2ElementTypeMap, dataFolder,
					Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME,
					Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_NUMBER_2_NAME_OUTPUT_FILENAME);

			// For each element type
			for(TIntObjectIterator<String> it = elementTypeNumber2ElementTypeMap.iterator(); it.hasNext();){
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
						Commons.TO_BE_COLLECTED_USER_DEFINED_LIBRARY_NUMBER_OF_OVERLAPS + elementType + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + elementType,
						Commons.TO_BE_COLLECTED_USER_DEFINED_LIBRARY_NUMBER_OF_OVERLAPS + elementType,
						jobName,
						numberofRuns,
						numberofRemainders,
						numberofComparisons.getUserDefinedLibraryElementTypeNumber2NumberofComparisonMap().get(elementTypeNumber), 
						userDefinedLibraryAnnotationType, 
						null, 
						elementType,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGIT_ELEMENTNUMBER,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGIT_ELEMENTNUMBER,
						enrichmentZScoreMode,
						glanetRunType);

			}// End of for each elementTypeNumber

		}
		/************ Collection of UserDefinedLibrary RESULTS ends *****************/
		/****************************************************************************/
		
		
		/****************************************************************************/
		/************ Collection of GO Term RESULTS starts **************************/
		/****************************************************************************/
		//BP
		if(bpGOTermAnnotationType.doBPGOTermsAnnotation()){
			
			//BP ExonBased
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.EXON_BASED_BP_GO_TERM,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.EXON_BASED_BP_GO_TERM,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getBP_GOTermNumberofComparison(),
					bpGOTermAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			
			//BP RegulationBased 
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS  + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.REGULATION_BASED_BP_GO_TERM,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS  + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.REGULATION_BASED_BP_GO_TERM,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getBP_GOTermNumberofComparison(),
					bpGOTermAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

			//BP AllBased
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.ALL_BASED_BP_GO_TERM,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.ALL_BASED_BP_GO_TERM,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getBP_GOTermNumberofComparison(),
					bpGOTermAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					enrichmentZScoreMode,
					glanetRunType);	


		}
		
		//MF
		if(mfGOTermAnnotationType.doMFGOTermsAnnotation()){
			
			//MF ExonBased
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.EXON_BASED_MF_GO_TERM,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.EXON_BASED_MF_GO_TERM,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getMF_GOTermNumberofComparison(),
					mfGOTermAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			
			//MF RegulationBased
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS  + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.REGULATION_BASED_MF_GO_TERM,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS  + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.REGULATION_BASED_MF_GO_TERM,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getMF_GOTermNumberofComparison(),
					mfGOTermAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			
			//MF AllBased
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.ALL_BASED_MF_GO_TERM,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.ALL_BASED_MF_GO_TERM,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getMF_GOTermNumberofComparison(),
					mfGOTermAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					enrichmentZScoreMode,
					glanetRunType);	



		}
		
		//CC
		if(ccGOTermAnnotationType.doCCGOTermsAnnotation()){
			
			//CC ExonBased
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.EXON_BASED_CC_GO_TERM,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.EXON_BASED_CC_GO_TERM,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getCC_GOTermNumberofComparison(),
					ccGOTermAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			
			//CC RegulationBased
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS  + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.REGULATION_BASED_CC_GO_TERM,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS  + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.REGULATION_BASED_CC_GO_TERM,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getCC_GOTermNumberofComparison(),
					ccGOTermAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			
			//CC AllBased
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY + Commons.ALL_BASED_CC_GO_TERM,
					Commons.ENRICHMENT_DIRECTORY + Commons.GENE_ONTOLOGY_TERMS + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.ALL_BASED_CC_GO_TERM,
					jobName, numberofRuns, numberofRemainders,
					numberofComparisons.getCC_GOTermNumberofComparison(),
					ccGOTermAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_GOTERMNUMBER,
					enrichmentZScoreMode,
					glanetRunType);	



		}
		/****************************************************************************/
		/************ Collection of GO Term RESULTS ends ****************************/
		/****************************************************************************/
		

		/****************************************************************************/
		/************ Collection of KEGG Pathway RESULTS starts *********************/
		if(keggPathwayAnnotationType.doKEGGPathwayAnnotation() && 
				!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
				!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){

			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, 
					FDR, multipleTestingParameter, 
					dataFolder, 
					outputFolder,
					Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY, 
					jobName, 
					numberofRuns,
					numberofRemainders, 
					numberofComparisons.getExonBasedKEGGPathwayNumberofComparison(),
					keggPathwayAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY, 
					jobName,
					numberofRuns, 
					numberofRemainders,
					numberofComparisons.getRegulationBasedKEGGPathwayNumberofComparison(), 
					keggPathwayAnnotationType,
					null, 
					null, 
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY, jobName, numberofRuns,
					numberofRemainders, numberofComparisons.getAllBasedKEGGPathwayNumberofComparison(),
					keggPathwayAnnotationType, 
					null, 
					null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
		}
		/************ Collection of KEGG Pathway RESULTS ends ***********************/
		/****************************************************************************/

		/****************************************************************************/
		/************ Collection of TF KEGG Pathway RESULTS starts ******************/
		if(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
				!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){

			//TF
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF,
					jobName, numberofRuns, numberofRemainders, numberofComparisons.getTfCellLineNumberofComparison(),
					AnnotationType.DO_TF_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER,
					enrichmentZScoreMode,
					glanetRunType);

			//KEGG Pathway
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY, jobName, numberofRuns,
					numberofRemainders, numberofComparisons.getExonBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY, jobName,
					numberofRuns, numberofRemainders,
					numberofComparisons.getRegulationBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY, jobName, numberofRuns,
					numberofRemainders, numberofComparisons.getAllBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

			//TF and KEGG Pathway
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_EXON_BASED_KEGG_PATHWAY, jobName, numberofRuns,
					numberofRemainders, numberofComparisons.getTfExonBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY, jobName,
					numberofRuns, numberofRemainders,
					numberofComparisons.getTfRegulationBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_ALL_BASED_KEGG_PATHWAY, jobName, numberofRuns,
					numberofRemainders, numberofComparisons.getTfAllBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

		}
		/************ Collection of TF KEGG Pathway RESULTS ends ********************/
		/****************************************************************************/

		/****************************************************************************/
		/************ Collection of TF CellLine KEGG Pathway RESULTS starts *********/
		if(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation() && 
				!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation())){

			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF,
					jobName, numberofRuns, numberofRemainders, numberofComparisons.getTfCellLineNumberofComparison(),
					AnnotationType.DO_TF_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER,
					enrichmentZScoreMode,
					glanetRunType);

			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY, jobName, numberofRuns,
					numberofRemainders, numberofComparisons.getExonBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY, jobName,
					numberofRuns, numberofRemainders,
					numberofComparisons.getRegulationBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY, jobName, numberofRuns,
					numberofRemainders, numberofComparisons.getAllBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY,
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getTfCellLineExonBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY,
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getTfCellLineRegulationBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY,
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getTfCellLineAllBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

		}
		/************ Collection of TF CellLine KEGG Pathway RESULTS ends ***********/
		/****************************************************************************/

		/****************************************************************************/
		/******************************* BOTH ***************************************/
		/************ Collection of TF KEGG Pathway RESULTS starts ******************/
		/************ Collection of TF CellLine KEGG Pathway RESULTS starts *********/
		if(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){

			//TF
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF,
					jobName, numberofRuns, numberofRemainders, numberofComparisons.getTfCellLineNumberofComparison(),
					AnnotationType.DO_TF_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER,
					enrichmentZScoreMode,
					glanetRunType);

			//KEGG Pathway
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY, jobName, numberofRuns,
					numberofRemainders, numberofComparisons.getExonBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,					
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY, jobName,
					numberofRuns, numberofRemainders,
					numberofComparisons.getRegulationBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY, jobName, numberofRuns,
					numberofRemainders, numberofComparisons.getAllBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_10DIGIT_KEGGPATHWAYNUMBER,				
					enrichmentZScoreMode,
					glanetRunType);

			//TF and KEGG Pathway
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_EXON_BASED_KEGG_PATHWAY, jobName, numberofRuns,
					numberofRemainders, numberofComparisons.getTfExonBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY, jobName,
					numberofRuns, numberofRemainders,
					numberofComparisons.getTfRegulationBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, dataFolder, outputFolder,
					Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_ALL_BASED_KEGG_PATHWAY, jobName, numberofRuns,
					numberofRemainders, numberofComparisons.getTfAllBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION, null, null,
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY,
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getTfCellLineExonBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY,
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getTfCellLineRegulationBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);
			CollectionofPermutationsResults.collectPermutationResults(
					numberofPermutationsInEachRun,
					bonferroniCorrectionSignificanceLevel,
					FDR,
					multipleTestingParameter,
					dataFolder,
					outputFolder,
					Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY,
					jobName,
					numberofRuns,
					numberofRemainders,
					numberofComparisons.getTfCellLineAllBasedKEGGPathwayNumberofComparison(),
					AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					GeneratedMixedNumberDescriptionOrderLength.LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER,
					GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER,
					enrichmentZScoreMode,
					glanetRunType);

		}
		/************ Collection of TF KEGG Pathway RESULTS ends ********************/
		/************ Collection of TF CellLine KEGG Pathway RESULTS ends ***********/
		/******************************* BOTH ***************************************/
		/****************************************************************************/

		//24 August 2016 starts
		if(glanetRunType.equalsIgnoreCase(Commons.ARG_GLANET_EXPERIMENT_RUN)){
			System.out.println("Collection of samplings results has just ended."); 
		}
		//24 August 2016 ends

		
	}

}
