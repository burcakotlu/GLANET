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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import multipletesting.BenjaminiandHochberg;
import multipletesting.BonferroniCorrection;
import auxiliary.FileOperations;
import auxiliary.FunctionalElementMinimal;
import auxiliary.GlanetDecimalFormat;
import auxiliary.NumberofComparisons;
import common.Commons;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentElementNameType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentDnaseOverlapExclusionType;
import enumtypes.DataDrivenExperimentTopPercentageType;
import enumtypes.ElementType;
import enumtypes.EnrichmentDecisionType;
import enumtypes.GenerateRandomDataMode;
import enumtypes.MultipleTestingType;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.hash.TObjectFloatHashMap;

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
 *  GenerateRandomDataMode e.g. "With GC and Mappability" or "Without GC and Mappability"
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
		
		//for a special case
		//remove this else part later
		else if (strLine.substring(indexofNinethTab + 1,indexofTenthTab).equals("nullnullnullnull")){
			
			if(indexofTenthTab>=0 && indexofEleventhTab>=0){
				empiricalPValue = Float.parseFloat( strLine.substring(indexofTenthTab + 1, indexofEleventhTab));
				element.setEmpiricalPValue(empiricalPValue);
			}
			
		}
		
					
		return element;
	}

	public static int writeCellLineFilteredEnrichmentFile(
			List<FunctionalElementMinimal> elementList,
			BufferedWriter cellLineFilteredEnrichmentBufferedWriter, 
			MultipleTestingType multipleTestingParameter,
			String elementNameCellLineName, 
			Float bonferroniCorrectionSignificanceLevel, 
			Float FDR,
			EnrichmentDecisionType enrichmentDecisionType) {

		DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat( "0.######E0");

		FunctionalElementMinimal element = null;

		int numberofSimulationsThatHasElementNameCellLineNameEnriched = 0;

		// Write new enrichmentFile
		// while writing count the numberofSimulations that has found the elementNameCellLineName enriched wrt the enrichmentDecisionType 

		try{
			
			// Write Header Line
			cellLineFilteredEnrichmentBufferedWriter.write("ElementName" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("OriginalNumberofOverlaps" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("NumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps" + "\t");
			cellLineFilteredEnrichmentBufferedWriter.write("NumberofPermutations" + "\t");
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
			for( int j = 0; j < elementList.size(); j++){

				//Get each element
				element = elementList.get(j);

				if( element.getName().contains(elementNameCellLineName)){

					// BH FDR Adjusted PValue from numberofPermutationsRatio starts
					if(enrichmentDecisionType.isEnrichedwrtBHFDRAdjustedPvalueFromRatioofPermutations()){
						
						if( element.getBHFDRAdjustedPValue() <= FDR){
							numberofSimulationsThatHasElementNameCellLineNameEnriched++;
						}
						
					}// BH FDR Adjusted PValue from numberofPermutationsRatio ends
		
//Right now it is meaningless, since BHFDRAdjustedPValueCalculatedFromZScore is not set.				
//					// BH FDR Adjusted PValue from zScore starts
//					else if(enrichmentDecisionType.isEnrichedwrtBHFDRAdjustedPvalueFromZscore()){
//						
//						if( element.getBHFDRAdjustedPValueCalculatedFromZScore() != null && 
//								element.getBHFDRAdjustedPValueCalculatedFromZScore() <= FDR){
//							numberofSimulationsThatHasElementNameCellLineNameEnriched++;
//						}
//						
//					}// BH FDR Adjusted PValue from zScore ends
					
					// Bonferroni Corrected PValue from numberofPermutationsRatio starts
					else if (enrichmentDecisionType.isEnrichedwrtBonferroniCorrectedPvalueFromRatioofPermutations()){
						
						if( element.getBonferroniCorrectedPValue() <= bonferroniCorrectionSignificanceLevel){
							numberofSimulationsThatHasElementNameCellLineNameEnriched++;
						}
					}// Bonferroni Corrected PValue from numberofPermutationsRatio ends

					
//Right now it is meaningless, since BonferroniCorrectedPValueCalculatedFromZScore is not set.				
//					// Bonferroni Corrected PValue from zScore starts
//					else if (enrichmentDecisionType.isEnrichedwrtBonferroniCorrectedPvalueFromZscore()){
//						
//						if( element.getBonferroniCorrectedPValueCalculatedFromZScore() != null && 
//								element.getBonferroniCorrectedPValueCalculatedFromZScore() <= bonferroniCorrectionSignificanceLevel){
//							numberofSimulationsThatHasElementNameCellLineNameEnriched++;
//						}
//						
//					}// Bonferroni Corrected PValue from zScore ends
	
						
				}// End of IF elementName contains elementNameCellLineName

				
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
					

				
			}// End of FOR each element

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return numberofSimulationsThatHasElementNameCellLineNameEnriched;

	}

	public static void readSimulationGLANETResults(
			String outputFolder, 
			DataDrivenExperimentTopPercentageType topPercentageType,
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType, 
			int numberofSimulations, 
			int numberofComparisons,
			ElementType elementType, 
			DataDrivenExperimentCellLineType cellLineType, 
			DataDrivenExperimentGeneType geneType,
			DataDrivenExperimentElementNameType elementNameType,
			Float bonferroniCorrectionSignificanceLevel, 
			Float FDR,
			MultipleTestingType multipleTestingParameter,
			EnrichmentDecisionType enrichmentDecisionType,
			GenerateRandomDataMode generateRandomDataMode,
			BufferedWriter bufferedWriter) {

		int numberofSimulationsThatHasElementNameCellLineNameEnriched = 0;

		String strLine = null;

		String enrichmentFile = null;
		File enrichmentDirectory = null;

		FileReader enrichmentFileReader = null;
		BufferedReader enrichmentBufferedReader = null;

		FileWriter cellLineFilteredEnrichmentFileWriter = null;
		BufferedWriter cellLineFilteredEnrichmentBufferedWriter = null;

		List<FunctionalElementMinimal> elementList = null;

		try{

			// For each simulation
			for( int i = 0; i < numberofSimulations; i++){

				// Initialize elementList
				elementList = new ArrayList<FunctionalElementMinimal>();
				
				switch(generateRandomDataMode){
					
					case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:
						enrichmentDirectory = new File(outputFolder + cellLineType.convertEnumtoString()  + "_" +  geneType.convertEnumtoString() + "_" +  topPercentageType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "wGCM" +Commons.DDE_RUN + i + System.getProperty( "file.separator") + Commons.ENRICHMENT + System.getProperty( "file.separator") + elementType.convertEnumtoString() + System.getProperty( "file.separator"));

						break;
						
					case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
						enrichmentDirectory = new File(outputFolder + cellLineType.convertEnumtoString()  + "_" + geneType.convertEnumtoString() + "_" + topPercentageType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "woGCM" +Commons.DDE_RUN + i + System.getProperty( "file.separator") + Commons.ENRICHMENT + System.getProperty( "file.separator") + elementType.convertEnumtoString() + System.getProperty( "file.separator"));

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
							
							break;

						}// End of IF EnrichmentFile under EnrichmentDirectory

					}// End of FOR each file under EnrichmentDirectory

				}// End of IF EnrichmentDirectory Exists

				enrichmentFileReader = FileOperations.createFileReader(enrichmentFile);
				enrichmentBufferedReader = new BufferedReader( enrichmentFileReader);

				cellLineFilteredEnrichmentFileWriter = FileOperations.createFileWriter(enrichmentDirectory + System.getProperty( "file.separator") + elementType.convertEnumtoString() + "_" + cellLineType.convertEnumtoString() + "_" + Commons.DDE_RUN + i + ".txt");
				cellLineFilteredEnrichmentBufferedWriter = new BufferedWriter( cellLineFilteredEnrichmentFileWriter);

				// Skip HeaderLine
				strLine = enrichmentBufferedReader.readLine();

				// Read enrichmentFile
				// Filter lines that contain cellLine only
				while( ( strLine = enrichmentBufferedReader.readLine()) != null){

					if( strLine.contains(cellLineType.convertEnumtoString())){
						// Fill elementList
						elementList.add(getElement(strLine, numberofComparisons));
					}

				}// End of WHILE

				/**********************************************************************************/
				// Calculate Bonferroni Corrected P Value
				BonferroniCorrection.calculateBonferroniCorrectedPValue( elementList);

				// Sort w.r.t. empiricalPValue
				Collections.sort( elementList, FunctionalElementMinimal.EMPIRICAL_P_VALUE);

				// Calculate BH FDR Adjusted PValue
				BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValue( elementList, FDR);
				/**********************************************************************************/

				/**********************************************************************************/
				// Calculate BonferroniCorrectedPValue Calculated From ZScore
				BonferroniCorrection.calculateBonferroniCorrectedPValueCalculatedFromZScore( elementList);

				// Sort w.r.t. empiricalPValue Calculated From ZScore
				Collections.sort( elementList, FunctionalElementMinimal.EMPIRICAL_P_VALUE_CALCULATED_FROM_Z_SCORE);

				// Calculate BH FDR Adjusted PValue Calculated From ZScore
				BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValueCalculatedFromZScore( elementList, FDR);
				/**********************************************************************************/

				// sort w.r.t. BH or BonferroniCorrection
				switch( multipleTestingParameter){

					case BONFERRONI_CORRECTION:
						Collections.sort( elementList, FunctionalElementMinimal.BONFERRONI_CORRECTED_P_VALUE);
						break;
	
					case BENJAMINI_HOCHBERG_FDR:
						Collections.sort( elementList, FunctionalElementMinimal.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
						break;
	
					default:
						break;

				}// End of switch

				// Write new Enrichment File
				// We have to accumulate the number of simulations whether simulation has returned 1 or 0.
				numberofSimulationsThatHasElementNameCellLineNameEnriched += writeCellLineFilteredEnrichmentFile(elementList,
																									cellLineFilteredEnrichmentBufferedWriter, 
																									multipleTestingParameter, 
																									elementNameType.convertEnumtoString() + "_" + cellLineType.convertEnumtoString(),
																									bonferroniCorrectionSignificanceLevel, 
																									FDR, 
																									enrichmentDecisionType);

				// Close
				enrichmentBufferedReader.close();
				cellLineFilteredEnrichmentBufferedWriter.close();

				elementList = null;
			}// End of FOR each simulation

			System.out.println("Number of simulations that has " + elementNameType.convertEnumtoString() + "_" + cellLineType.convertEnumtoString() + " is enriched " + numberofSimulationsThatHasElementNameCellLineNameEnriched + " out of " + numberofSimulations + " simulations.");
			bufferedWriter.write(elementNameType.convertEnumtoString() +  "_" + cellLineType.convertEnumtoString() + "\t" + numberofSimulationsThatHasElementNameCellLineNameEnriched + "/" +  numberofSimulations + System.getProperty("line.separator"));
			
		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		String dataFolder 	= glanetFolder + System.getProperty( "file.separator") + Commons.DATA + System.getProperty( "file.separator");
		String outputFolder = glanetFolder + System.getProperty( "file.separator") + Commons.OUTPUT + System.getProperty( "file.separator");
		String ddeFolder 	= glanetFolder + System.getProperty( "file.separator") + Commons.DDE + System.getProperty( "file.separator");
		
		//cellLineType
		DataDrivenExperimentCellLineType cellLineType = DataDrivenExperimentCellLineType.convertStringtoEnum(args[1]);
		
		//geneType
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(args[2]);
		
		
		/*************************************************************************************************/
		/******************************Get the tpmValues starts*******************************************/
		/*************************************************************************************************/
		TObjectFloatMap<DataDrivenExperimentTopPercentageType> tpmValueMap = new TObjectFloatHashMap<DataDrivenExperimentTopPercentageType>();
		DataDrivenExperimentCommon.getTPMValues(glanetFolder,cellLineType,geneType,tpmValueMap);
		/*************************************************************************************************/
		/******************************Get the tpmValues ends*********************************************/
		/*************************************************************************************************/


		//dnaseOverlapExclusionType
		DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType = DataDrivenExperimentDnaseOverlapExclusionType.convertStringtoEnum(args[3]);
		
		//FDR
		float FDR = (args.length > 5)?Float.parseFloat(args[4]):0.05f;
		
		//bonferroniCorrectionSignificanceLevel
		float bonferroniCorrectionSignificanceLevel = (args.length > 6)?Float.parseFloat(args[5]):0.05f;
		
		//multipleTestingParameter
		MultipleTestingType multipleTestingParameter = MultipleTestingType.convertStringtoEnum(args[6]);
		
		int numberofTFElementsInThisCellLine = 0;
		int numberofHistoneElementsInThisCellLine = 0;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			fileWriter = FileOperations.createFileWriter(ddeFolder + Commons.GLANET_DDE_RESULTS_FILE, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
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
			
	
			//enrichmentDecisionType
			//ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_ZSCORE
			//ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_ZSCORE
			//ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_RATIO_OF_PERMUTATIONS
			//ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_PERMUTATIONS
			EnrichmentDecisionType enrichmentDecisionType = EnrichmentDecisionType.convertStringtoEnum(args[7]);
	
			//numberofSimulations
			int numberofSimulations = ( args.length > 9)?Integer.parseInt(args[8]):0;
	
			//generateRandomDataMode
			GenerateRandomDataMode generateRandomDataMode = GenerateRandomDataMode.convertStringtoEnum(args[9]);
			
			DataDrivenExperimentTopPercentageType topPercentageType = null;
			
			for(TObjectFloatIterator<DataDrivenExperimentTopPercentageType> itr = tpmValueMap.iterator();itr.hasNext();){
				
				itr.advance();
				
				topPercentageType = itr.key();
				//tpmValue = itr.value();
				
				bufferedWriter.write(cellLineType.convertEnumtoString() + "\t" + geneType.convertEnumtoString() + "\t" + topPercentageType.convertEnumtoString()  + "\t" + dnaseOverlapExclusionType.convertEnumtoString() + "\t" + generateRandomDataMode.convertEnumtoString() + System.getProperty("line.separator"));
				
				bufferedWriter.write("Expressors" + "\t"+ "NumberofEnrichedElements/" + numberofSimulations + " Simulations" + System.getProperty("line.separator"));
				
				//Expressor
				readSimulationGLANETResults(
						outputFolder, 
						topPercentageType, 
						dnaseOverlapExclusionType, 
						numberofSimulations,
						numberofTFElementsInThisCellLine, 
						ElementType.TF, 
						cellLineType, 
						geneType,
						DataDrivenExperimentElementNameType.POL2,
						bonferroniCorrectionSignificanceLevel, 
						FDR, 
						multipleTestingParameter, 
						enrichmentDecisionType,
						generateRandomDataMode,
						bufferedWriter);
				
				//Expressor
				readSimulationGLANETResults(
						outputFolder, 
						topPercentageType, 
						dnaseOverlapExclusionType, 
						numberofSimulations,
						numberofHistoneElementsInThisCellLine, 
						ElementType.HISTONE, 
						cellLineType,
						geneType,
						DataDrivenExperimentElementNameType.H3K4ME1,
						bonferroniCorrectionSignificanceLevel, 
						FDR, 
						multipleTestingParameter, 
						enrichmentDecisionType,
						generateRandomDataMode,
						bufferedWriter);
				
				//Expressor
				readSimulationGLANETResults(
						outputFolder, 
						topPercentageType, 
						dnaseOverlapExclusionType, 
						numberofSimulations,
						numberofHistoneElementsInThisCellLine, 
						ElementType.HISTONE, 
						cellLineType, 
						geneType,
						DataDrivenExperimentElementNameType.H3K4ME2,
						bonferroniCorrectionSignificanceLevel, 
						FDR, 
						multipleTestingParameter, 
						enrichmentDecisionType,
						generateRandomDataMode,
						bufferedWriter);
		
		
				
				//Expressor
				readSimulationGLANETResults(
						outputFolder, 
						topPercentageType, 
						dnaseOverlapExclusionType, 
						numberofSimulations,
						numberofHistoneElementsInThisCellLine, 
						ElementType.HISTONE, 
						cellLineType, 
						geneType,
						DataDrivenExperimentElementNameType.H3K4ME3,
						bonferroniCorrectionSignificanceLevel, 
						FDR, 
						multipleTestingParameter, 
						enrichmentDecisionType,
						generateRandomDataMode,
						bufferedWriter);
				
				bufferedWriter.write("Repressors" + "\t"+ "NumberofEnrichedElements/" + numberofSimulations + " Simulations" + System.getProperty("line.separator"));
				
				//Repressor
				readSimulationGLANETResults(
						outputFolder, 
						topPercentageType, 
						dnaseOverlapExclusionType, 
						numberofSimulations,
						numberofHistoneElementsInThisCellLine, 
						ElementType.HISTONE, 
						cellLineType, 
						geneType,
						DataDrivenExperimentElementNameType.H3K27ME3,
						bonferroniCorrectionSignificanceLevel, 
						FDR, 
						multipleTestingParameter, 
						enrichmentDecisionType,
						generateRandomDataMode,
						bufferedWriter);
				
				//Repressor
				readSimulationGLANETResults(
						outputFolder, 
						topPercentageType, 
						dnaseOverlapExclusionType, 
						numberofSimulations,
						numberofHistoneElementsInThisCellLine, 
						ElementType.HISTONE, 
						cellLineType, 
						geneType,
						DataDrivenExperimentElementNameType.H3K9ME3,
						bonferroniCorrectionSignificanceLevel, 
						FDR, 
						multipleTestingParameter, 
						enrichmentDecisionType,
						generateRandomDataMode,
						bufferedWriter);
				
				bufferedWriter.write(System.getProperty("line.separator"));
				
				

			}//End of FOR each tpmValue
			
			//Flush BufferedWriter
			bufferedWriter.flush();
			
			//Close BufferedWriter
			bufferedWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
				
				
	}

}
