/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import multipletesting.BenjaminiandHochberg;
import multipletesting.BonferroniCorrection;
import auxiliary.FileOperations;
import auxiliary.FunctionalElementMinimal;
import auxiliary.GlanetDecimalFormat;
import auxiliary.NumberofComparisons;

import common.Commons;

import datadrivenexperiment.DataDrivenExperimentElementTPM.DDEElementTPMChainedComparator;
import enumtypes.AssociationMeasureType;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentDnaseOverlapExclusionType;
import enumtypes.DataDrivenExperimentElementNameType;
import enumtypes.DataDrivenExperimentElementType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTPMType;
import enumtypes.ElementType;
import enumtypes.EnrichmentDecisionType;
import enumtypes.GenerateRandomDataMode;
import enumtypes.MultipleTestingType;
import enumtypes.SignificanceLevel;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

/**
 * @author Burcak Otlu
 * @date May 1, 2015
 * @project Glanet 
 * 
 * Data Driven Experiment Step 5
 * 
 * In this class
 * We collect the Simulations Enrichments Results
 * In the context of counting the number of simulations that has found the repressor element enriched in the nonExpressing Genes Intervals
 * and the number of simulations that has found the expressor element enriched in the Expressing Genes Intervals.
 * 
 * We have to provide the
 * 
 *  Glanet Folder
 *  TPM value (0.1, 0.01, 0.001)
 *  DnaseOverlapExclusionType e.g. PartiallyDiscardIntervalTakeAllTheRemainingIntervals
 *  FDR value (e.g. "0.05")
 *  Bonferroni Correction Significance Level ( e.g "0.05")
 *  MultipleTestingType e.g. "Bonferroni Correction" or "Benjamini Hochberg FDR"
 *  EnrichmentDecision e.g. P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO 
 *  number of simulations (e.g. "100")
 *  GenerateRandomDataMode e.g. "With GC and Mapability" or "Without GC and Mapability"
 *  
 *  
 *  Output: This class writes the Data Driven Computational Experiment results under DDE folder.
 *
 */
public class Step5_DDE_GLANETResults {

	public static boolean isEnriched( String strLine) {

		boolean isEnriched = false;

		float bonnCorrectedPValue = Float.MAX_VALUE;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;

		// 50083 USF1_H1HESC 10 4 10000 406 4E-4 1.624E-1 5.933333E-3 true

		indexofFirstTab = strLine.indexOf( '\t');
		indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
		indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
		indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
		indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
		indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;
		indexofSeventhTab = ( indexofSixthTab > 0)?strLine.indexOf( '\t', indexofSixthTab + 1):-1;
		indexofEigthTab = ( indexofSeventhTab > 0)?strLine.indexOf( '\t', indexofSeventhTab + 1):-1;

		bonnCorrectedPValue = Float.parseFloat( strLine.substring( indexofSeventhTab + 1, indexofEigthTab));

		if( bonnCorrectedPValue <= 0.05f){
			isEnriched = true;
		}

		return isEnriched;

	}

	public static FunctionalElementMinimal getElement(
			String strLine, 
			int numberofComparisons) {

		String elementName;

		int originalNumberofOverlaps;
		int numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps;
		int numberofPermutations;

		// Calculated from ratio of permutations that has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
		float empiricalPValue;

		// Calculated from zScore
		double zScore;
		double empiricalPValueCalculatedFromZScore;

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

		// Case P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO
		// example strLine
		// 50083 USF1_H1HESC 10 4 10000 406 4E-4 1.624E-1 5.933333E-3 true

		// Case P_VALUE_CALCULATED_FROM_Z_SCORE_AND_NUMBER_OF_PERMUTATIONS_RATIO
		// ElementNumber ElementName OriginalNumberofOverlaps
		// NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualToIn10000Permutations NumberofPermutations
		// NumberofcomparisonsforBonferroniCorrection mean stdDev zScore empiricalPValueCalculatedFromZScore
		// BonferroniCorrectedPValueCalculatedFromZScore BHFDRAdjustedPValueCalculatedFromZScore
		// RejectNullHypothesisCalculatedFromZScore empiricalPValue BonfCorrPValuefor406Comparisons BHFDRAdjustedPValue
		// RejectNullHypothesisforanFDRof0.05
		// 1090066 HAE2F1_MCF7 13 0 10000 406 3.2922226855713106 1.6902789833744583 5.743298834047009 4.642483E-9
		// 1.884848E-6 5.470056E-8 true 0E0 0E0 0E0 true

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
		indexofFourteenthTab = ( indexofThirteenthTab > 0)?strLine.indexOf( '\t', indexofThirteenthTab + 1):-1;

		FunctionalElementMinimal element = new FunctionalElementMinimal();

		elementName = strLine.substring( indexofFirstTab + 1, indexofSecondTab);
		originalNumberofOverlaps = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));
		numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = Integer.parseInt( strLine.substring(indexofThirdTab + 1, indexofFourthTab));
		numberofPermutations = Integer.parseInt(strLine.substring( indexofFourthTab + 1, indexofFifthTab));

		element.setName( elementName);
		element.setNumberofPermutations( numberofPermutations);
		element.setNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps( numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps);
		element.setOriginalNumberofOverlaps( originalNumberofOverlaps);
		element.setNumberofComparisons( numberofComparisons);

			

		if( !strLine.substring( indexofEigthTab + 1, indexofNinethTab).equals("NaN") &&
				!strLine.substring( indexofEigthTab + 1, indexofNinethTab).equals("null")	){
			
			zScore = Double.parseDouble( strLine.substring( indexofEigthTab + 1, indexofNinethTab));
			element.setZScore( zScore);

			empiricalPValueCalculatedFromZScore = Double.parseDouble( strLine.substring( indexofNinethTab + 1,indexofTenthTab));
			element.setEmpiricalPValueCalculatedFromZScore( empiricalPValueCalculatedFromZScore);

		}else{
			element.setZScore( null);
			element.setEmpiricalPValueCalculatedFromZScore( null);
		}

		if (indexofThirteenthTab>=0 && indexofFourteenthTab>=0){
			
			empiricalPValue = Float.parseFloat( strLine.substring(indexofThirteenthTab + 1, indexofFourteenthTab));
			element.setEmpiricalPValue(empiricalPValue);
			
		}
		
//		//for a special case
//		//remove this else part later
//		else if (strLine.substring(indexofNinethTab + 1,indexofTenthTab).equals("nullnullnullnull")){
//			
//			if(indexofTenthTab>=0 && indexofEleventhTab>=0){
//				empiricalPValue = Float.parseFloat( strLine.substring(indexofTenthTab + 1, indexofEleventhTab));
//				element.setEmpiricalPValue(empiricalPValue);
//			}
//			
//		}
		
					
		return element;
	}

	public static void writeCellLineFilteredEnrichmentFile(
			DataDrivenExperimentCellLineType cellLineType,
			DataDrivenExperimentTPMType TPMType,
			List<FunctionalElementMinimal> cellLineSpecificElementList,
			BufferedWriter cellLineFilteredEnrichmentBufferedWriter, 
			MultipleTestingType multipleTestingParameter,
			List<DataDrivenExperimentElementNameType> elementNameTypeList,
			TObjectIntMap<String> elementNameTPMName2NumberofEnrichmentMap, 
			Float bonferroniCorrectionSignificanceLevel, 
			Float FDR,
			EnrichmentDecisionType enrichmentDecisionType) {

		DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat( "0.######E0");

		FunctionalElementMinimal element = null;
	
		String elementNameTPMName = null;
		
		int indexofUnderscore = 0;
		String elementName = null;
		DataDrivenExperimentElementNameType elementNameType  = null;
		
		// Write new enrichmentFile
		// while writing count the numberofSimulations that has found the elementNameCellLineName enriched wrt the enrichmentDecisionType 

		try{
			
			// Write Header Line
			cellLineFilteredEnrichmentBufferedWriter.write("ElementName" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("Observed Test Statistic" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("NumberofSamplingsHavingTestStatisticGreaterThanorEqualtoObservedTestStatistic" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("NumberofSamplings" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("NumberofComparisons" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("zScore" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("EmpiricalPValueCalculatedFromZScore" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("BonferroniCorrectedPValueCalculatedFromZScore" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("BHFDRAdjustedPValueCalculatedFromZScore" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("isRejectNullHypothesisCalculatedFromZScore" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("EmpiricalPValue" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("BonferroniCorrectedPValue" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("BHFDRAdjustedPValue" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("isRejectNullHypothesis" + System.getProperty( "line.separator"));
				
			// Write each element in elementList
			for( int i = 0; i < cellLineSpecificElementList.size(); i++){

				//Get each element
				element = cellLineSpecificElementList.get(i);
				
				//Write CellLine Filtered Enrichment Result Line
				cellLineFilteredEnrichmentBufferedWriter.write(element.getName() + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(element.getOriginalNumberofOverlaps() + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(element.getNumberofPermutations() + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(element.getNumberofComparisons() + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(( element.getZScore() == null?element.getZScore():df.format( element.getZScore())) + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(( element.getEmpiricalPValueCalculatedFromZScore() == null?element.getEmpiricalPValueCalculatedFromZScore():df.format( element.getEmpiricalPValueCalculatedFromZScore())) + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(( element.getBonferroniCorrectedPValueCalculatedFromZScore() == null?element.getBonferroniCorrectedPValueCalculatedFromZScore():df.format( element.getBonferroniCorrectedPValueCalculatedFromZScore())) + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(( element.getBHFDRAdjustedPValueCalculatedFromZScore() == null?element.getBHFDRAdjustedPValueCalculatedFromZScore():df.format( element.getBHFDRAdjustedPValueCalculatedFromZScore())) + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(element.getRejectNullHypothesisCalculatedFromZScore() + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(df.format( element.getEmpiricalPValue()) + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(df.format( element.getBonferroniCorrectedPValue()) + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(df.format( element.getBHFDRAdjustedPValue()) + "\t");
				cellLineFilteredEnrichmentBufferedWriter.write(element.isRejectNullHypothesis() + System.getProperty( "line.separator"));


				/*****************************************************************************************/
				/**********************GLANET DDE considered elementNameList starts***********************/
				/*****************************************************************************************/
				indexofUnderscore = element.getName().indexOf('_');
				
				elementName = element.getName().substring(0, indexofUnderscore);
				elementNameType = DataDrivenExperimentElementNameType.convertStringtoEnum(elementName);
				
				if (elementNameTypeList.contains(elementNameType)){
					
					elementNameTPMName = elementName + "_" + TPMType.convertEnumtoString();
					
					//Just put if not put before
					//But set the numberofEnrichment to zero
					//Since we don't know whether it is enriched or not yet.
					if (elementNameTPMName2NumberofEnrichmentMap.get(elementNameTPMName) == 0){
						elementNameTPMName2NumberofEnrichmentMap.put(elementNameTPMName, 0);
					}
					
					
					//17 May 2016
					//Let's decide on Enrichment depending on empirical p value
					if(enrichmentDecisionType.isEnrichedwrtEmpiricalPvalueFromRatioofPermutations()){
						
						if( element.getEmpiricalPValue() <= bonferroniCorrectionSignificanceLevel){
							elementNameTPMName2NumberofEnrichmentMap.put(elementNameTPMName, elementNameTPMName2NumberofEnrichmentMap.get(elementNameTPMName)+1);
						}
						
					}
					
					// BH FDR Adjusted PValue from numberofPermutationsRatio starts
					else if(enrichmentDecisionType.isEnrichedwrtBHFDRAdjustedPvalueFromRatioofPermutations()){
						
						if( element.getBHFDRAdjustedPValue() <= FDR){
							elementNameTPMName2NumberofEnrichmentMap.put(elementNameTPMName, elementNameTPMName2NumberofEnrichmentMap.get(elementNameTPMName)+1);
						}
						
					}// BH FDR Adjusted PValue from numberofPermutationsRatio ends
		
					// Bonferroni Corrected PValue from numberofPermutationsRatio starts
					else if (enrichmentDecisionType.isEnrichedwrtBonferroniCorrectedPvalueFromRatioofPermutations()){
						
						if( element.getBonferroniCorrectedPValue() <= bonferroniCorrectionSignificanceLevel){
								elementNameTPMName2NumberofEnrichmentMap.put(elementNameTPMName, elementNameTPMName2NumberofEnrichmentMap.get(elementNameTPMName)+1);
						}
						
					}// Bonferroni Corrected PValue from numberofPermutationsRatio ends
					
				}//End of IF
				/*****************************************************************************************/
				/**********************GLANET DDE considered elementNameList ends*************************/
				/*****************************************************************************************/

			}// End of FOR each element in the elementList

		}catch( IOException e){
			e.printStackTrace();
		}

	
	}

	//This method also detects and writes unaccomplished GLANET Runs
	public static void readSimulationGLANETResults(
			String outputFolder, 
			DataDrivenExperimentTPMType TPMType,
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType, 
			int numberofSimulations, 
			int numberofComparisons,
			ElementType elementType, 
			DataDrivenExperimentCellLineType cellLineType, 
			DataDrivenExperimentGeneType geneType,
			List<DataDrivenExperimentElementNameType> elementNameTypeList,
			TObjectIntMap<String> elementNameTPMName2NumberofEnrichmentMap,
			TObjectIntMap<String> elementTypeTpmName2NumberofValidSimulationMap,
			Float bonferroniCorrectionSignificanceLevel, 
			Float FDR,
			MultipleTestingType multipleTestingParameter,
			EnrichmentDecisionType enrichmentDecisionType,
			GenerateRandomDataMode generateRandomDataMode,
			AssociationMeasureType associationMeasureType,
			BufferedWriter unaccomplishedGLANETRunsBufferedWriter,
			List<String> unaccomplishedGLANETRunsList,
			DateFormat dateFormat,
			Date date) {

		String strLine = null;

		String enrichmentFile = null;
		File enrichmentDirectory = null;

		FileReader enrichmentFileReader = null;
		BufferedReader enrichmentBufferedReader = null;

		FileWriter cellLineFilteredEnrichmentFileWriter = null;
		BufferedWriter cellLineFilteredEnrichmentBufferedWriter = null;

		List<FunctionalElementMinimal> cellLineSpecificElementList = null;
		
		//Each valid GLANET run must have an existing Enrichment Directory. 
		int numberofExistingEnrichmentDirectories = 0;
		
		String lookFor = null;
		
		
		try{

			// For each simulation
			for( int i = 0; i < numberofSimulations; i++){
				
				//Initialization
				//So that unexisting run can not use the last valid enrichmentFile and copy its content.
				enrichmentFile=null;
			
				// Initialize elementList for each simulation (GLANET run)
				cellLineSpecificElementList = new ArrayList<FunctionalElementMinimal>();
				
				switch(generateRandomDataMode){
					
					case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:
						enrichmentDirectory = new File(outputFolder + cellLineType.convertEnumtoString()  + "_" +  geneType.convertEnumtoString() + "_" +  TPMType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "wGCM" + associationMeasureType.convertEnumtoShortString() + Commons.DDE_RUN + i + System.getProperty( "file.separator") + Commons.ENRICHMENT + System.getProperty( "file.separator") + elementType.convertEnumtoString() + System.getProperty( "file.separator"));

						break;
						
					case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
						enrichmentDirectory = new File(outputFolder + cellLineType.convertEnumtoString()  + "_" + geneType.convertEnumtoString() + "_" + TPMType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "woGCM" + associationMeasureType.convertEnumtoShortString() +Commons.DDE_RUN + i + System.getProperty( "file.separator") + Commons.ENRICHMENT + System.getProperty( "file.separator") + elementType.convertEnumtoString() + System.getProperty( "file.separator"));

						break;
				
				}//End of SWITCH
				
				
				// Get the enrichmentFile in this folder for this simulation
				// There must only one enrichmentFile
				if( enrichmentDirectory.exists() && enrichmentDirectory.isDirectory()){

					//System.out.println( "directory exists and is directory");
					
					for( File eachEnrichmentFile : enrichmentDirectory.listFiles()){

						if( !eachEnrichmentFile.isDirectory() && 
								(eachEnrichmentFile.getAbsolutePath().contains(Commons.ALL_WITH_RESPECT_TO_BONF_CORRECTED_P_VALUE) || eachEnrichmentFile.getAbsolutePath().contains(Commons.ALL_WITH_RESPECT_TO_BH_FDR_ADJUSTED_P_VALUE)) ){

							enrichmentFile = eachEnrichmentFile.getAbsolutePath();
							
							//It has been increased by one if cellLineSpecificElementList size is greater than zero
							//numberofExistingEnrichmentDirectories++;
							break;

						}// End of IF EnrichmentFile under EnrichmentDirectory

					}// End of FOR each file under EnrichmentDirectory

				}// End of IF EnrichmentDirectory Exists
				
				if (enrichmentFile!=null){
					
					enrichmentFileReader = FileOperations.createFileReader(enrichmentFile);
					enrichmentBufferedReader = new BufferedReader( enrichmentFileReader);

					cellLineFilteredEnrichmentFileWriter = FileOperations.createFileWriter(enrichmentDirectory + System.getProperty( "file.separator") + dateFormat.format(date) + elementType.convertEnumtoString() + "_" + cellLineType.convertEnumtoString() + "_" + Commons.DDE_RUN + i + ".txt");
					cellLineFilteredEnrichmentBufferedWriter = new BufferedWriter( cellLineFilteredEnrichmentFileWriter);

					// Skip HeaderLine
					strLine = enrichmentBufferedReader.readLine();

					// Read enrichmentFile
					// Filter lines that contain cellLine only
					while( ( strLine = enrichmentBufferedReader.readLine()) != null){

						if( strLine.contains(cellLineType.convertEnumtoString())){
							// Fill elementList
							cellLineSpecificElementList.add(getElement(strLine, numberofComparisons));
						}

					}// End of WHILE
					
					
					//There must be at least one result other than header line
					//In order to make this simulation valid.
					//There can be an enrichment file but it can only have header line nothing else
					if(cellLineSpecificElementList.size()>0){
						numberofExistingEnrichmentDirectories++;
					}
					//There is enrichment file but there is no result in it Case
					else{
						//Write unaccomplished GLANET runs to a file under DDE directory
						lookFor = cellLineType.convertEnumtoString()  + "_" +  geneType.convertEnumtoString() + "_" +  TPMType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + generateRandomDataMode.convertEnumtoShortString() + associationMeasureType.convertEnumtoShortString() + Commons.DDE_RUN + i;
						
						if (!unaccomplishedGLANETRunsList.contains(lookFor)){
							unaccomplishedGLANETRunsBufferedWriter.write(lookFor + System.getProperty("line.separator"));
							unaccomplishedGLANETRunsList.add(lookFor);
						}
					}

					/**********************************************************************************/
					// Calculate Bonferroni Corrected P Value
					// Number of comparisons is used here
					BonferroniCorrection.calculateBonferroniCorrectedPValue(cellLineSpecificElementList);

					// Sort w.r.t. empiricalPValue
					Collections.sort(cellLineSpecificElementList, FunctionalElementMinimal.EMPIRICAL_P_VALUE);

					// Calculate BH FDR Adjusted PValue
					BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValue(cellLineSpecificElementList, FDR);
					/**********************************************************************************/

					/**********************************************************************************/
					//By the way do we need this calculation?
					// Yes, since we also write pValues calculated from ZScores to the outputFile
					// although output lines are sorted w.r.t BonfCorrected or BH FDR adjusted pValues.
					// Calculate BonferroniCorrectedPValue Calculated From ZScore
					// Number of comparisons is used here
					BonferroniCorrection.calculateBonferroniCorrectedPValueCalculatedFromZScore(cellLineSpecificElementList);

					// Sort w.r.t. empiricalPValue Calculated From ZScore
					Collections.sort(cellLineSpecificElementList, FunctionalElementMinimal.EMPIRICAL_P_VALUE_CALCULATED_FROM_Z_SCORE);

					// Calculate BH FDR Adjusted PValue Calculated From ZScore
					BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValueCalculatedFromZScore(cellLineSpecificElementList, FDR);
					/**********************************************************************************/

					// sort w.r.t. BH or BonferroniCorrection
					switch( multipleTestingParameter){

						case BONFERRONI_CORRECTION:
							Collections.sort(cellLineSpecificElementList, FunctionalElementMinimal.BONFERRONI_CORRECTED_P_VALUE);
							break;
		
						case BENJAMINI_HOCHBERG_FDR:
							Collections.sort(cellLineSpecificElementList, FunctionalElementMinimal.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
							break;
		
						default:
							break;

					}// End of switch
					
					
					//8 OCT 2015 starts
					writeCellLineFilteredEnrichmentFile(cellLineType, 
														TPMType,
														cellLineSpecificElementList,
														cellLineFilteredEnrichmentBufferedWriter, 
														multipleTestingParameter, 
														elementNameTypeList,
														elementNameTPMName2NumberofEnrichmentMap,
														bonferroniCorrectionSignificanceLevel, 
														FDR, 
														enrichmentDecisionType);

					
					// Close
					enrichmentBufferedReader.close();
					cellLineFilteredEnrichmentBufferedWriter.close();
					
				}//End of IF
				
				//There is no enrichment file at all Case
				else{
					
					
					//Write unaccomplished GLANET runs to a file under DDE directory
					lookFor = cellLineType.convertEnumtoString()  + "_" +  geneType.convertEnumtoString() + "_" +  TPMType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + generateRandomDataMode.convertEnumtoShortString() + associationMeasureType.convertEnumtoShortString() + Commons.DDE_RUN + i;
					
					if (!unaccomplishedGLANETRunsList.contains(lookFor)){
						unaccomplishedGLANETRunsBufferedWriter.write(lookFor + System.getProperty("line.separator"));
						unaccomplishedGLANETRunsList.add(lookFor);
					}

					
				}
				
				//Free memory
				cellLineSpecificElementList = null;
				
			}// End of FOR each simulation
			
			elementTypeTpmName2NumberofValidSimulationMap.put(elementType.convertEnumtoString() + "_" + TPMType.convertEnumtoString(), numberofExistingEnrichmentDirectories);
			
		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	

	
	public static boolean isTF(String elementName){
		if (elementName.equals(Commons.POL2))
			return true;
		else 
			return false;
	}
	
	public static void initializeElementName2NumberofEnrichmentMap(
			List<DataDrivenExperimentElementNameType> elementNameList){
		
		//Please note that although H3K9me2 is added and it is a known repressor
		//GLANET Library does not contain any entry for H3K9me2  
		//Therefore there is no output for H3K9me2 
		
		elementNameList.add(DataDrivenExperimentElementNameType.POL2);
		elementNameList.add(DataDrivenExperimentElementNameType.CTCF);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K4ME1);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K4ME2);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K4ME3);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K27ME3);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K9ME2);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K9ME3);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K27AC);
		elementNameList.add(DataDrivenExperimentElementNameType.H2AZ);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K36ME3);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K79ME2);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K9AC);
		elementNameList.add(DataDrivenExperimentElementNameType.H4K20ME1);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K9ME1);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K9ACB);
		elementNameList.add(DataDrivenExperimentElementNameType.H3K36ME3B);
		
	}
	
	public static void convertMapToList(
			TObjectFloatMap<DataDrivenExperimentTPMType> tpmType2TPMValueMap,
			List<Float> tpmValues){
		
		Float tpmValue = null;
		
		for (TObjectFloatIterator<DataDrivenExperimentTPMType> itr = tpmType2TPMValueMap.iterator();itr.hasNext();){
			
			itr.advance();
			tpmValue =itr.value();
			
			tpmValues.add(tpmValue);
			
		}//End of for each TPMType2TPMValue
		
	}
	
	
	public static void convertMapToList(
			TObjectIntMap<String> elementNameTPMName2NumberofEnrichmentMap,
			List<DataDrivenExperimentElementTPM> ddeElementList,
			SortedMap<DataDrivenExperimentTPMType,Float> expGenesTPMType2TPMValueSortedMap,
			SortedMap<DataDrivenExperimentTPMType,Float> nonExpGenesTPMType2TPMValueSortedMap,
			DataDrivenExperimentGeneType geneType,
			//int numberofGLANETRuns,
			TObjectIntMap<String> elementTypeTpmName2NumberofValidSimulationMap){
		
		String elementNameTPMName = null;
		String elementName = null;
		String TPMName = null;
		int indexofUnderscore;
		
		int numberofEnrichment = 0;
		
		DataDrivenExperimentElementTPM ddeElementTPM = null;
		
		DataDrivenExperimentElementNameType elementNameType = null;
		DataDrivenExperimentElementType elementActivatororRepressorType = null;
		DataDrivenExperimentTPMType tpmType = null;
		Float tpmValue = null;
		
		Float typeOneError = null;
		Float power = null;
		
		int numberofValidSimulationsWithEnrichmentDirectoryAndFile = 0;
		String elementTypeName  = null;
		
		for(TObjectIntIterator<String> itr = elementNameTPMName2NumberofEnrichmentMap.iterator();itr.hasNext();){
			
			itr.advance();
			
			elementNameTPMName = itr.key();
			numberofEnrichment = itr.value();
			
			indexofUnderscore = elementNameTPMName.indexOf('_');
			
			elementName = elementNameTPMName.substring(0, indexofUnderscore);
			elementNameType = DataDrivenExperimentElementNameType.convertStringtoEnum(elementName);
			
			ddeElementTPM = new DataDrivenExperimentElementTPM();
			
			ddeElementTPM.setElementNameType(elementNameType);
			
			if (elementNameType.isActivator()){
				elementActivatororRepressorType = DataDrivenExperimentElementType.ACTIVATOR;
			}else if (elementNameType.isRepressor()){
				elementActivatororRepressorType = DataDrivenExperimentElementType.REPRESSOR;
			}else if (elementNameType.isAmbigious()){
				elementActivatororRepressorType = DataDrivenExperimentElementType.AMBIGIOUS;
			}
			
			ddeElementTPM.setElementType(elementActivatororRepressorType);
			
			TPMName = elementNameTPMName.substring(indexofUnderscore+1);
			
			tpmType = DataDrivenExperimentTPMType.convertStringtoEnum(TPMName);
			
			ddeElementTPM.setTpmType(tpmType);
			
			
			if (isTF(elementName)){
				elementTypeName = ElementType.TF.convertEnumtoString();
			}else{
				elementTypeName = ElementType.HISTONE.convertEnumtoString();
			}
			
			numberofValidSimulationsWithEnrichmentDirectoryAndFile = elementTypeTpmName2NumberofValidSimulationMap.get(elementTypeName + "_" + TPMName);
			
			switch(geneType){
			
				case EXPRESSING_PROTEINCODING_GENES:
					tpmValue = expGenesTPMType2TPMValueSortedMap.get(tpmType);
					break;
					
				case NONEXPRESSING_PROTEINCODING_GENES:
					tpmValue = nonExpGenesTPMType2TPMValueSortedMap.get(tpmType);
					break;
				
			}//End of switch for geneType
			
			
			ddeElementTPM.setTpmValue(tpmValue);
			
			ddeElementTPM.setNumberofEnrichment(numberofEnrichment);
			
			//Evaluate One of these
			
			//Null: No Enrichment
			//Alternative : Enrichment
			
			//Type I Error: Rejecting Null when it is true
			//Type II Error: Not rejecting Null when it was false
			//Power : 1 - Type II Error
			
			//Null is false here
			//We have rejected null as the number of enrichment
			//Type II Error and power can be calculated
			//Type II Error  is (numberofGLANETRuns-numberofEnrichment)/numberofGLANETRuns
			//Power is 1 - Type II Error
			//Therefore Power is numberofEnrichment/numberofGLANETRuns
			if ((geneType.isExpressingProteinCodingGenes() && elementActivatororRepressorType.isActivator()) ||
				(geneType.isNonExpressingProteinCodingGenes() && elementActivatororRepressorType.isRepressor()) ){
				
				power = ((float) (numberofEnrichment))/numberofValidSimulationsWithEnrichmentDirectoryAndFile;
				
				ddeElementTPM.setPower(power);
				ddeElementTPM.setTypeOneError(null);
			}
			
			
			//Null is true here
			//We have rejected null as the number of enrichment
			//Type I Error can be calculated
			//Type I Error is the numberofEnrichment/numberofGLANETRuns
			if ((geneType.isExpressingProteinCodingGenes() && elementActivatororRepressorType.isRepressor()) ||
					(geneType.isNonExpressingProteinCodingGenes() && elementActivatororRepressorType.isActivator()) ){
					
					typeOneError = ((float) (numberofEnrichment))/numberofValidSimulationsWithEnrichmentDirectoryAndFile;
					
					ddeElementTPM.setTypeOneError(typeOneError);
					ddeElementTPM.setPower(null);
					
			}
			
			//FOR Ambigious Elements
			//Evaluate Type I Error
			//Evaluate Power
			//In this way of evaluating typeOneError and power are always the same
			if (elementActivatororRepressorType.isAmbigious()){
					
				typeOneError = ((float) (numberofEnrichment))/numberofValidSimulationsWithEnrichmentDirectoryAndFile;
				power = ((float) (numberofEnrichment))/numberofValidSimulationsWithEnrichmentDirectoryAndFile;
				
				ddeElementTPM.setTypeOneError(typeOneError);
				ddeElementTPM.setPower(power);
			}
			
			
			ddeElementList.add(ddeElementTPM);
			
		}//End of for: traversing elementNameTPMName2NumberofEnrichmentMap
		
	}
	
	//Modified 1 March 2016
	public static void writeResults(
			BufferedWriter bufferedWriter,
			List<DataDrivenExperimentElementTPM> ddeElementList,
			Collection<Float> tpmValues,
			Set<DataDrivenExperimentTPMType> tpmTypes,
			GenerateRandomDataMode generateRandomDataMode) throws IOException{
		
		
		DataDrivenExperimentElementTPM elementTPM = null;
		
		DataDrivenExperimentTPMType tpmType = null;
		Float tpmValue  = null;
		
		Float typeOneError = null;
		Float power = null;
		
		//First Header Line starts
		//The order of writing TPMType:TPMValue must match the order we put elements in the ddeElementList
		//For ExpressingElements, TPMValues in descending order
		//For NonExpressingElments, TPMValues in ascending order
		bufferedWriter.write("   " + "\t" + "   " + "\t");
		
		// tpmTypes are already sorted in the ascending order of integer after TOP String
		// tpmValues are sorted in descending order for ExpressingGenes in the order corresponding the tpmType
		// tpmValues are sorted in ascending order for NonExpressingGenes in the order corresponding the tpmType
		for(int i=0; i<tpmTypes.size();i++){
			
			tpmType = (DataDrivenExperimentTPMType) tpmTypes.toArray()[i];
			tpmValue = (Float) tpmValues.toArray()[i];
			
			bufferedWriter.write(tpmType.convertEnumtoString() + ":" + tpmValue + "\t" + "   " + "\t" + "   " + "\t");
		
		}//End of for writing tpmType:tpmValue in header line
		
		bufferedWriter.write(System.getProperty("line.separator"));
		//First Header Line ends
		
		//Second Header Line starts
		bufferedWriter.write("ElementType" + "\t" + "ElementName" + "\t");
		
		for(Iterator<DataDrivenExperimentTPMType> itr = tpmTypes.iterator();itr.hasNext();){
			
			itr.next();						
			bufferedWriter.write("NumberofEnrichment"+ "\t" + "TypeIError" + generateRandomDataMode.convertEnumtoShortString() + "\t" + "Power" + generateRandomDataMode.convertEnumtoShortString()+ "\t");
		
		}
		bufferedWriter.write(System.getProperty("line.separator"));
		//Second Header Line ends

		
		//Subsequent Lines starts
		for(int i= 0; i<ddeElementList.size(); ){
			
			elementTPM = ddeElementList.get(i);
			
			//ElementType
			bufferedWriter.write(elementTPM.getElementType()+ "\t");
			
			//ElementName
			bufferedWriter.write(elementTPM.getElementNameType()+ "\t");			
			
			int j = 0;
			
			//For each tpmType
			for(Iterator<DataDrivenExperimentTPMType> itr = tpmTypes.iterator();itr.hasNext();){
				
				tpmType = itr.next();
			
				if ((elementTPM.getElementNameType() == ddeElementList.get(i+j).getElementNameType()) && 
						(tpmType == ddeElementList.get(i+j).getTpmType())){
					
					typeOneError = ddeElementList.get(i+j).getTypeOneError();
					power = ddeElementList.get(i+j).getPower();
					
					//NumberofEnrichment
					bufferedWriter.write(ddeElementList.get(i+j).getNumberofEnrichment()+ "\t");
					
					//TypeIError
					if (typeOneError!=null){
						bufferedWriter.write(typeOneError + "\t");	
					}else{
						bufferedWriter.write("NA" + "\t");	
					}
				
					//POWER
					if (power!=null){
						bufferedWriter.write(power + "\t");	
					}else{
						bufferedWriter.write("NA" + "\t");	
					}
					
					j++;
				}
				//For this elementNameType and tpmType there was no overlap found in any of the total GLANET DDE runs.
				else{
					
					//NumberofEnrichment
					bufferedWriter.write("NA"+ "\t");
					
					//TypeIError					
					bufferedWriter.write("NA" + "\t");	
				
					//POWER
					bufferedWriter.write("NA" + "\t");	
					
				}
				
			}//End of for traversing each TPMType
			
			bufferedWriter.write(System.getProperty("line.separator"));
			
			i = i + j;
		
		}//End of for each element
		//Subsequent Lines ends

		//Add one more line between results
		bufferedWriter.write(System.getProperty("line.separator"));
		
	}
	

	/*
	 * args[0] = Glanet Folder (which is the parent of Data folder)
	 * args[1] = cellLineType (GM12878 or K562) 
	 * args[2] = geneType (NonExpressingGenes or ExpressingGenes)
	 * args[3] = TPM value (0.1, 0.01, 0.001)
	 * args[4] = DnaseOverlapExclusionType e.g. PartiallyDiscardTakeAllTheRemainingIntervals
	 * args[5] = FDR value (e.g. "0.05")
	 * args[6] = Bonferroni Correction Significance Level ( e.g "0.05")
	 * args[7] = MultipleTestingType e.g. "Bonferroni Correction" or "Benjamini Hochberg FDR"
	 * args[8] = EnrichmentDecision e.g. P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO 
	 * args[9] = number of simulations (e.g. "100")
	 * args[10] = GenerateRandomDataMode e.g. "With GC and Mappability" or "Without GC and Mappability"
	 * 
	 * example parameters:
	 * 
	 * "//Volumes//External//Documents//GLANET//"
	 * GM12878
	 * NonExpressingGenes
	 * NoDiscard, CompletelyDiscard, PartiallyDiscardTakeAllTheRemainingIntervals, PartiallyDiscardTakeTheLongestInterval
	 * "0.05"
	 * "0.05'
	 * "0"
	 * "0"
	 * "100"
	 */
	public static void main( String[] args) {

		//glanetFolder
		String glanetFolder = args[0];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA 	+ System.getProperty("file.separator");
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT 	+ System.getProperty("file.separator");
		String ddeFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DDE 	+ System.getProperty("file.separator");
		
		//cellLineType
		DataDrivenExperimentCellLineType cellLineType = DataDrivenExperimentCellLineType.convertStringtoEnum(args[1]);
		
		//geneType
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(args[2]);
		
		/*************************************************************************************************/
		/******************************Get the tpmValues starts*******************************************/
		/*************************************************************************************************/
		//We can not sort on values. There can be more than one values with same tpmValue
		//tpmTypes are sorted in ascending order according to the integer after the word TOP
		
		SortedMap<DataDrivenExperimentTPMType,Float> expGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		SortedMap<DataDrivenExperimentTPMType,Float> nonExpGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		
		Set<DataDrivenExperimentTPMType> tpmTypes = null;
		Collection<Float> tpmValues = null;
		
		switch(geneType){
		
			case EXPRESSING_PROTEINCODING_GENES:
				DataDrivenExperimentCommon.fillTPMType2TPMValueMap(glanetFolder,cellLineType,geneType,expGenesTPMType2TPMValueSortedMap);
				tpmTypes = expGenesTPMType2TPMValueSortedMap.keySet();
				tpmValues = expGenesTPMType2TPMValueSortedMap.values();
				
				break;
				
			case NONEXPRESSING_PROTEINCODING_GENES:
				DataDrivenExperimentCommon.fillTPMType2TPMValueMap(glanetFolder,cellLineType,geneType,nonExpGenesTPMType2TPMValueSortedMap);
				tpmTypes = nonExpGenesTPMType2TPMValueSortedMap.keySet();
				tpmValues = nonExpGenesTPMType2TPMValueSortedMap.values();
				break;
				
		}//End of SWITCH for geneType
		
		
		/*************************************************************************************************/
		/******************************Get the tpmValues ends*********************************************/
		/*************************************************************************************************/
		//dnaseOverlapExclusionType
		DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType = DataDrivenExperimentDnaseOverlapExclusionType.convertStringtoEnum(args[3]);
		
		//FDR
		float FDR = (args.length > 5)?Float.parseFloat(args[4]):0.05f;
		
		//bonferroniCorrectionSignificanceLevel
		float bonferroniCorrectionSignificanceLevel = (args.length > 6)?Float.parseFloat(args[5]):0.05f;
		
		SignificanceLevel significanceLevel = SignificanceLevel.convertStringtoEnum(args[5]);
		
		//multipleTestingParameter
		MultipleTestingType multipleTestingParameter = MultipleTestingType.convertStringtoEnum(args[6]);
		
		//enrichmentDecisionType
		//ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_ZSCORE
		//ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_ZSCORE
		//ENRICHED_WRT_EMPIRICAL_PVALUE_FROM_RATIO_OF_PERMUTATIONS
		//ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_RATIO_OF_PERMUTATIONS
		//ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_PERMUTATIONS
		EnrichmentDecisionType enrichmentDecisionType = EnrichmentDecisionType.convertStringtoEnum(args[7]);

		//numberofSimulations
		int numberofGLANETRuns = (args.length > 9)?Integer.parseInt(args[8]):0;
		
		//generateRandomDataMode
		GenerateRandomDataMode generateRandomDataMode = GenerateRandomDataMode.convertStringtoEnum(args[9]);
		
		//associationMeasureType
		AssociationMeasureType associationMeasureType = AssociationMeasureType.convertStringtoEnum(args[10]);
		
		//DDE Number
		int DDENumber = Integer.parseInt(args[11]);
		
		int numberofTFElementsInThisCellLine = 0;
		int numberofHistoneElementsInThisCellLine = 0;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		FileWriter unaccomplishedGLANETRunsFileWriter = null;
		BufferedWriter unaccomplishedGLANETRunsBufferedWriter = null;
		
		FileWriter overallSituationFileWriter = null;
		BufferedWriter overallSituationBufferedWriter = null;
		
		DateFormat dateFormat = new SimpleDateFormat("_yyyy_MM_dd_");
		Date date = new Date();
		
		try {
			
			fileWriter = FileOperations.createFileWriter(ddeFolder + dateFormat.format(date) + Commons.GLANET_DDE_RESULTS_FILE_START  + DDENumber +  Commons.GLANET_DDE_RESULTS_FILE_MIDDLE  + significanceLevel.convertEnumtoString() + "_" +associationMeasureType.convertEnumtoShortString() + Commons.GLANET_DDE_RESULTS_FILE_END, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//1 March 2016
			unaccomplishedGLANETRunsFileWriter = FileOperations.createFileWriter(ddeFolder + dateFormat.format(date) + Commons.GLANET_DDE_UNACCOMPLISHED_GLANET_RUNS_FILE_START + DDENumber +  Commons.GLANET_DDE_UNACCOMPLISHED_GLANET_RUNS_FILE_REST, true);
			unaccomplishedGLANETRunsBufferedWriter =  new BufferedWriter(unaccomplishedGLANETRunsFileWriter);
			
			//8 March 2016
			overallSituationFileWriter = FileOperations.createFileWriter(ddeFolder + dateFormat.format(date) + Commons.GLANET_DDE_OVERALL_SITUATION_FILE_START + DDENumber + Commons.GLANET_DDE_OVERALL_SITUATION_FILE_REST , true);
			overallSituationBufferedWriter = new BufferedWriter(overallSituationFileWriter);
			
			//Write Header Line For UnaccomplishedGLANETRunsBufferedWriter
			unaccomplishedGLANETRunsBufferedWriter.write("cellLineType_geneType_TPMType_dnaseOverlapExclusionType_generateRandomDataMode_associationMeasureType_DDE_RUNNumber");
		
				
			//Writer Header Line For Overall Situation Information
			overallSituationBufferedWriter.write(
					"EnrichmentDecisionType" + "\t" + 
					"CellLineType" + "\t" + 
					"GeneType" + "\t" +
					"TPMType" + "\t" +
					"DnaseOverlapExclusionType" + "\t" + 
					"GenerateRandomDataMode" + "\t" +
					"AssociationMeasureType" + "\t" +
					"ElementType" + "\t" + "ElementTypeTpmName2NumberofValidSimulationMap" + "\t" +
					"ElementType" + "\t" + "ElementTypeTpmName2NumberofValidSimulationMap" +System.getProperty("line.separator"));
			
			switch(cellLineType){
			
				case GM12878: {
					numberofTFElementsInThisCellLine 		= NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder, ElementType.TF, Commons.GM12878);
					numberofHistoneElementsInThisCellLine 	= NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder, ElementType.HISTONE, Commons.GM12878);
					break;				
				}
				
				case K562: {
					numberofTFElementsInThisCellLine 		= NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder, ElementType.TF, Commons.K562);
					numberofHistoneElementsInThisCellLine 	= NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder, ElementType.HISTONE, Commons.K562);
					break;				
				}
					
				default: 
					break;
			
			
			}//end of SWITCH
			
			
			DataDrivenExperimentTPMType TPMType = null;
			
			//Initialization
			//Elements that we will gonna consider in GLANET DDE
			List<DataDrivenExperimentElementNameType> elementNameTypeList  = new ArrayList<DataDrivenExperimentElementNameType>();
			
			//Add DNA elements in this method
			initializeElementName2NumberofEnrichmentMap(elementNameTypeList);
			
			TObjectIntMap<String> elementNameTPMName2NumberofEnrichmentMap = new TObjectIntHashMap<String>();

			//Number of valid Simulations (GLANET Runs) with Enrichment Directory
			//It is tpm specific, we calculate for each tpm Value
			//Since all the necessary other parameters are given
			//Such as cellLineType
			//geneType
			//dnaseOverlapExclusionType
			//generateRandomDataMode
			//associationMeasureType
			TObjectIntMap<String> elementTypeTpmName2NumberofValidSimulationMap = new TObjectIntHashMap<String>();
			
			List<String> unaccomplishedGLANETRunsList = new ArrayList<String>();
			
			//Write Header Lines Output ResultFile 
			bufferedWriter.write("CellLine" + "\t" + "GeneType" + "\t"+ "DnaseOverlapExclusionType" + "\t" + "GenerateRandomDataMode" + "\t" + "AssociationMeasureType"  + "\t" + "NumberofSimulations" +  "\t" + "EnrichmentDecisionType" +System.getProperty("line.separator"));
			bufferedWriter.write(cellLineType.convertEnumtoString() + "\t" + geneType.convertEnumtoString()  + "\t" + dnaseOverlapExclusionType.convertEnumtoString() + "\t" + generateRandomDataMode.convertEnumtoShortString()+ "\t" + associationMeasureType.convertEnumtoShortString() + "\t" + numberofGLANETRuns + "\t" + enrichmentDecisionType.convertEnumtoString() + System.getProperty("line.separator"));
			bufferedWriter.write("TPM" + "\t" + "NumberofSimulationWithValidEnrichmentDirectoryAndFile"  + System.getProperty("line.separator"));				
			
			for(Iterator<DataDrivenExperimentTPMType> itr = tpmTypes.iterator();itr.hasNext();){
				
				TPMType = itr.next();
				
				//TF
				readSimulationGLANETResults(
						outputFolder, 
						TPMType, 
						dnaseOverlapExclusionType, 
						numberofGLANETRuns,
						numberofTFElementsInThisCellLine, 
						ElementType.TF, 
						cellLineType, 
						geneType,
						elementNameTypeList,
						elementNameTPMName2NumberofEnrichmentMap,
						elementTypeTpmName2NumberofValidSimulationMap,
						bonferroniCorrectionSignificanceLevel, 
						FDR, 
						multipleTestingParameter, 
						enrichmentDecisionType,
						generateRandomDataMode,
						associationMeasureType,
						unaccomplishedGLANETRunsBufferedWriter,
						unaccomplishedGLANETRunsList,
						dateFormat,
						date);
				
				
				//HISTONE
				readSimulationGLANETResults(
						outputFolder, 
						TPMType, 
						dnaseOverlapExclusionType, 
						numberofGLANETRuns,
						numberofHistoneElementsInThisCellLine, 
						ElementType.HISTONE, 
						cellLineType,
						geneType,
						elementNameTypeList,
						elementNameTPMName2NumberofEnrichmentMap,
						elementTypeTpmName2NumberofValidSimulationMap,
						bonferroniCorrectionSignificanceLevel, 
						FDR, 
						multipleTestingParameter, 
						enrichmentDecisionType,
						generateRandomDataMode,
						associationMeasureType,
						unaccomplishedGLANETRunsBufferedWriter,
						unaccomplishedGLANETRunsList,
						dateFormat,
						date);
				
				//For Overall Situation Information
				overallSituationBufferedWriter.write(
						enrichmentDecisionType.convertEnumtoString() + "\t" + 
						cellLineType.convertEnumtoString() + "\t" + 
						geneType.convertEnumtoString() + "\t" +
						TPMType.convertEnumtoString() + "\t" +
						dnaseOverlapExclusionType.convertEnumtoString() + "\t" + 
						generateRandomDataMode.convertEnumtoShortString() + "\t" +
						associationMeasureType.convertEnumtoShortString() + "\t" +
						ElementType.TF  + "\t" + elementTypeTpmName2NumberofValidSimulationMap.get(ElementType.TF.convertEnumtoString() + "_" + TPMType.convertEnumtoString()) + "\t" +
						ElementType.HISTONE  + "\t" + elementTypeTpmName2NumberofValidSimulationMap.get(ElementType.HISTONE.convertEnumtoString() + "_" + TPMType.convertEnumtoString()) +
						System.getProperty("line.separator"));

				
				bufferedWriter.write(TPMType.convertEnumtoString() + "\t" + ElementType.TF  + "\t" + elementTypeTpmName2NumberofValidSimulationMap.get(ElementType.TF.convertEnumtoString() + "_" + TPMType.convertEnumtoString()) + "\t" + ElementType.HISTONE + "\t" + elementTypeTpmName2NumberofValidSimulationMap.get(ElementType.HISTONE.convertEnumtoString() + "_" + TPMType.convertEnumtoString()) + "\t" + System.getProperty("line.separator"));
				
				
			}//End of FOR each tpmValue
			
			//Convert elementNameTPMName2NumberofEnrichmentMap to List
			List<DataDrivenExperimentElementTPM> ddeElementList = new ArrayList<DataDrivenExperimentElementTPM>();
			
			//Fill ddeElementList
			//Evaluate TypeIError and Power if applicable depending on the geneType and elementType
			convertMapToList(
					elementNameTPMName2NumberofEnrichmentMap,
					ddeElementList,
					expGenesTPMType2TPMValueSortedMap,
					nonExpGenesTPMType2TPMValueSortedMap,
					geneType,
					//numberofGLANETRuns,
					elementTypeTpmName2NumberofValidSimulationMap);
			
			// Sort w.r.t. multiple attributes
			// First by elementType
			// Second by elementName
			// Lastly by tpmType
			Collections.sort(ddeElementList, new DDEElementTPMChainedComparator());
			
			//Check ddeElementList whether there is any missing elementNameTPMName2NumberofEnrichment is missing
			//So let's traverse ddeElementList tpmTypes.size()  by tpmTypes.size()
			//Now, We have to write results here for each TPM
			writeResults(bufferedWriter,ddeElementList,tpmValues,tpmTypes,generateRandomDataMode);
			
			//Flush BufferedWriter
			bufferedWriter.flush();
			
			//Close BufferedWriter
			bufferedWriter.close();
			unaccomplishedGLANETRunsBufferedWriter.close();
			overallSituationBufferedWriter.close();
			
			//Free memory
			elementTypeTpmName2NumberofValidSimulationMap = null;
			unaccomplishedGLANETRunsList = null;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
				
				
	}

}
