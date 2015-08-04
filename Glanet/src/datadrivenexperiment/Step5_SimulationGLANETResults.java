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
import enumtypes.DnaseOverlapExclusionType;
import enumtypes.ElementType;
import enumtypes.EnrichmentDecision;
import enumtypes.GenerateRandomDataMode;
import enumtypes.MultipleTestingType;

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
public class Step5_SimulationGLANETResults {

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
			int numberofComparisons,
			EnrichmentDecision enrichmentDecision) {

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
		numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = Integer.parseInt( strLine.substring(
				indexofThirdTab + 1, indexofFourthTab));
		numberofPermutations = Integer.parseInt( strLine.substring( indexofFourthTab + 1, indexofFifthTab));

		element.setName( elementName);
		element.setNumberofPermutations( numberofPermutations);
		element.setNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps( numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps);
		element.setOriginalNumberofOverlaps( originalNumberofOverlaps);
		element.setNumberofComparisons( numberofComparisons);

		switch( enrichmentDecision){

			case OLD_RESULT_FILE_VERSION:{
	
				empiricalPValue = Float.parseFloat( strLine.substring( indexofSixthTab + 1, indexofSeventhTab));
				element.setEmpiricalPValue( empiricalPValue);
				break;
			}
	
			case P_VALUE_CALCULATED_FROM_Z_SCORE:
			case P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO:
			case BOTH_ZSCORE_AND_NUMBEROFPERMUTATIONSRATIO:{
	
				if( !strLine.substring( indexofEigthTab + 1, indexofNinethTab).equals( "NaN")){
	
					zScore = Double.parseDouble( strLine.substring( indexofEigthTab + 1, indexofNinethTab));
					element.setZScore( zScore);
	
					empiricalPValueCalculatedFromZScore = Double.parseDouble( strLine.substring( indexofNinethTab + 1,indexofTenthTab));
					element.setEmpiricalPValueCalculatedFromZScore( empiricalPValueCalculatedFromZScore);
	
				}else{
	
					element.setZScore( null);
					element.setEmpiricalPValueCalculatedFromZScore( null);
	
				}
	
				empiricalPValue = Float.parseFloat( strLine.substring( indexofThirteenthTab + 1, indexofFourteenthTab));
				element.setEmpiricalPValue( empiricalPValue);
				break;
			}
	
			default:
				break;

		}// End of SWITCH

		return element;
	}

	public static int writeNewEnrichmentFile(
			List<FunctionalElementMinimal> elementList,
			BufferedWriter cellLineFilteredEnrichmentBufferedWriter, 
			MultipleTestingType multipleTestingParameter,
			String elementNameCellLineName, 
			Float bonferroniCorrectionSignificanceLevel, 
			Float FDR,
			EnrichmentDecision enrichmentDecision) {

		DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat( "0.######E0");

		FunctionalElementMinimal element = null;

		int numberofSimulationsThatHasElementNameCellLineNameEnriched = 0;

		// Write new enrichmentFile
		// while writing count the numberofSimulations that has found the elementNameCellLineName enriched

		try{
			// Write Header Line
			switch( enrichmentDecision){

			case OLD_RESULT_FILE_VERSION:

				cellLineFilteredEnrichmentBufferedWriter.write( "ElementName" + "\t" + "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps" + "\t" + "NumberofPermutations" + "\t" + "NumberofComparisons" + "\t" + "EmpiricalPValue" + "\t" + "BonferroniCorrectedPValue" + "\t" + "BHFDRAdjustedPValue" + "\t" + "isRejectNullHypothesis" + System.getProperty( "line.separator"));
				break;

			case P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO:
			case P_VALUE_CALCULATED_FROM_Z_SCORE:
			case BOTH_ZSCORE_AND_NUMBEROFPERMUTATIONSRATIO:

				cellLineFilteredEnrichmentBufferedWriter.write("ElementName" + "\t" + "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps" + "\t" + "NumberofPermutations" + "\t" + "NumberofComparisons" + "\t" +

				"zScore" + "\t" + "EmpiricalPValueCalculatedFromZScore" + "\t" + "BonferroniCorrectedPValueCalculatedFromZScore" + "\t" + "BHFDRAdjustedPValueCalculatedFromZScore" + "\t" + "isRejectNullHypothesisCalculatedFromZScore" + "\t" +

				"EmpiricalPValue" + "\t" + "BonferroniCorrectedPValue" + "\t" + "BHFDRAdjustedPValue" + "\t" + "isRejectNullHypothesis" + System.getProperty( "line.separator"));
				break;

			default:
				break;

			}// End of SWITCH

			// Write each element in elementList
			for( int j = 0; j < elementList.size(); j++){

				element = elementList.get( j);

				if( element.getName().contains( elementNameCellLineName)){

					// Take care of numberofPermutationsRatio
					if( enrichmentDecision.isPValueCalculatedFromNumberofPermutationsRatio()){

						switch( multipleTestingParameter){
						
							case BONFERRONI_CORRECTION:
								if( element.getBonferroniCorrectedPValue() <= bonferroniCorrectionSignificanceLevel){
									numberofSimulationsThatHasElementNameCellLineNameEnriched++;
								}
								break;
	
							case BENJAMINI_HOCHBERG_FDR:
								if( element.getBHFDRAdjustedPValue() <= FDR){
									numberofSimulationsThatHasElementNameCellLineNameEnriched++;
								}
								break;
								
							default:
								break;

						}// End of SWITCH

					}
					// Take care of ZScore
					else if( enrichmentDecision.isPValueCalculatedFromZScore()){

						switch( multipleTestingParameter){
						
							case BONFERRONI_CORRECTION:
								if( element.getBonferroniCorrectedPValueCalculatedFromZScore() != null && element.getBonferroniCorrectedPValueCalculatedFromZScore() <= bonferroniCorrectionSignificanceLevel){
									numberofSimulationsThatHasElementNameCellLineNameEnriched++;
								}
								break;
	
							case BENJAMINI_HOCHBERG_FDR:
								if( element.getBHFDRAdjustedPValueCalculatedFromZScore() != null && element.getBHFDRAdjustedPValueCalculatedFromZScore() <= FDR){
									numberofSimulationsThatHasElementNameCellLineNameEnriched++;
								}
								break;
								
							default:
							break;

						}// End of SWITCH

					}
					// Take care of ZScore and numberofPermutationsRatio
					else if( enrichmentDecision.isBothZScoreandNumberofPermutationRatio()){

						switch( multipleTestingParameter){
						
							case BONFERRONI_CORRECTION:
								if( element.getBonferroniCorrectedPValue() <= bonferroniCorrectionSignificanceLevel && element.getBonferroniCorrectedPValueCalculatedFromZScore() <= bonferroniCorrectionSignificanceLevel){
									numberofSimulationsThatHasElementNameCellLineNameEnriched++;
								}
								break;
	
							case BENJAMINI_HOCHBERG_FDR:
								if( element.getBHFDRAdjustedPValue() <= FDR && element.getBHFDRAdjustedPValueCalculatedFromZScore() <= FDR){
									numberofSimulationsThatHasElementNameCellLineNameEnriched++;
								}
								break;
							default:
								break;

						}// End of SWITCH
					}

				}// End of If elementName contains elementNameCellLineName

				// Write content of the file
				switch( enrichmentDecision){

				case OLD_RESULT_FILE_VERSION:
					cellLineFilteredEnrichmentBufferedWriter.write( element.getName() + "\t" + element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format( element.getEmpiricalPValue()) + "\t" + df.format( element.getBonferroniCorrectedPValue()) + "\t" + df.format( element.getBHFDRAdjustedPValue()) + "\t" + element.isRejectNullHypothesis() + System.getProperty( "line.separator"));
					break;

				case P_VALUE_CALCULATED_FROM_Z_SCORE:
				case P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO:
				case BOTH_ZSCORE_AND_NUMBEROFPERMUTATIONSRATIO:
					cellLineFilteredEnrichmentBufferedWriter.write( element.getName() + "\t" + element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + ( element.getZScore() == null?element.getZScore():df.format( element.getZScore())) + "\t" + ( element.getEmpiricalPValueCalculatedFromZScore() == null?element.getEmpiricalPValueCalculatedFromZScore():df.format( element.getEmpiricalPValueCalculatedFromZScore())) + "\t" + ( element.getBonferroniCorrectedPValueCalculatedFromZScore() == null?element.getBonferroniCorrectedPValueCalculatedFromZScore():df.format( element.getBonferroniCorrectedPValueCalculatedFromZScore())) + "\t" + ( element.getBHFDRAdjustedPValueCalculatedFromZScore() == null?element.getBHFDRAdjustedPValueCalculatedFromZScore():df.format( element.getBHFDRAdjustedPValueCalculatedFromZScore())) + "\t" + element.getRejectNullHypothesisCalculatedFromZScore() + "\t" + df.format( element.getEmpiricalPValue()) + "\t" + df.format( element.getBonferroniCorrectedPValue()) + "\t" + df.format( element.getBHFDRAdjustedPValue()) + "\t" + element.isRejectNullHypothesis() + System.getProperty( "line.separator"));
					break;

				default:
					break;

				}// End of enrichmentDecision

			}// End of for each element

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return numberofSimulationsThatHasElementNameCellLineNameEnriched;

	}

	public static void readSimulationGLANETResults(
			String outputFolder, 
			String tpmString,
			DnaseOverlapExclusionType dnaseOverlapExclusionType, 
			int numberofSimulations, 
			int numberofComparisons,
			ElementType elementType, 
			String cellLineName, 
			String elementNameCellLineName,
			Float bonferroniCorrectionSignificanceLevel, 
			Float FDR,
			MultipleTestingType multipleTestingParameter,
			EnrichmentDecision enrichmentDecision,
			GenerateRandomDataMode generateRandomDataMode) {

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
						enrichmentDirectory = new File(outputFolder + tpmString + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "wGCM" +Commons.SIMULATION + i + System.getProperty( "file.separator") + Commons.ENRICHMENT + System.getProperty( "file.separator") + elementType.convertEnumtoString() + System.getProperty( "file.separator"));

						break;
						
					case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
						enrichmentDirectory = new File(outputFolder + tpmString + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "woGCM" +Commons.SIMULATION + i + System.getProperty( "file.separator") + Commons.ENRICHMENT + System.getProperty( "file.separator") + elementType.convertEnumtoString() + System.getProperty( "file.separator"));

						break;
				
				}//End of SWITCH

				
				// Get the enrichmentFile in this folder for this simulation
				// There must only one enrichmentFile
				if( enrichmentDirectory.exists() && enrichmentDirectory.isDirectory()){

					System.out.println( "directory exists and is directory");
					
					for( File eachEnrichmentFile : enrichmentDirectory.listFiles()){

						if( !eachEnrichmentFile.isDirectory() && eachEnrichmentFile.getAbsolutePath().contains(Commons.ALL_WITH_RESPECT_TO_BH_FDR_ADJUSTED_P_VALUE)){

							enrichmentFile = eachEnrichmentFile.getAbsolutePath();

							break;

						}// End of IF EnrichmentFile under EnrichmentDirectory

					}// End of FOR each file under EnrichmentDirectory

				}// End of IF EnrichmentDirectory Exists

				enrichmentFileReader = FileOperations.createFileReader( enrichmentFile);
				enrichmentBufferedReader = new BufferedReader( enrichmentFileReader);

				cellLineFilteredEnrichmentFileWriter = FileOperations.createFileWriter(enrichmentDirectory + System.getProperty( "file.separator") + elementType.convertEnumtoString() + "_" + cellLineName + "_" + Commons.SIMULATION + i + ".txt");
				cellLineFilteredEnrichmentBufferedWriter = new BufferedWriter( cellLineFilteredEnrichmentFileWriter);

				// Skip HeaderLine
				strLine = enrichmentBufferedReader.readLine();

				// Read enrichmentFile
				// Filter lines that contain cellLine only
				while( ( strLine = enrichmentBufferedReader.readLine()) != null){

					if( strLine.contains( cellLineName)){
						// Fill elementList
						elementList.add( getElement( strLine, numberofComparisons, enrichmentDecision));
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
				numberofSimulationsThatHasElementNameCellLineNameEnriched += writeNewEnrichmentFile( elementList,
						cellLineFilteredEnrichmentBufferedWriter, multipleTestingParameter, elementNameCellLineName,
						bonferroniCorrectionSignificanceLevel, FDR, enrichmentDecision);

				// Close
				enrichmentBufferedReader.close();
				cellLineFilteredEnrichmentBufferedWriter.close();

				elementList = null;
			}// End of FOR each simulation

			System.out.println( "Number of simulations that has " + elementNameCellLineName + " is enriched " + numberofSimulationsThatHasElementNameCellLineNameEnriched + " out of " + numberofSimulations + " simulations.");

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * args[0] = Glanet Folder (which is the parent of Data folder)
	 * args[1] = TPM value (0.1, 0.01, 0.001)
	 * args[2] = DnaseOverlapExclusionType e.g. PartiallyDiscardIntervalTakeAllTheRemainingIntervals
	 * args[3] = FDR value (e.g. "0.05")
	 * args[4] = Bonferroni Correction Significance Level ( e.g "0.05")
	 * args[5] = MultipleTestingType e.g. "Bonferroni Correction" or "Benjamini Hochberg FDR"
	 * args[6] = EnrichmentDecision e.g. P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO 
	 * args[7] = number of simulations (e.g. "100")
	 * args[8] = GenerateRandomDataMode e.g. "With GC and Mappability" or "Without GC and Mappability"
	 * 
	 * example parameters:
	 * 
	 * "//Volumes//External//Documents//GLANET//"
	 * "0.001"
	 * "0"
	 * "0.05"
	 * "0.05'
	 * "0"
	 * "0"
	 * "100"
	 */
	public static void main( String[] args) {

		//args[0]
		String glanetFolder = args[0];
		String dataFolder 	= glanetFolder + System.getProperty( "file.separator") + Commons.DATA + System.getProperty( "file.separator");
		String outputFolder = glanetFolder + System.getProperty( "file.separator") + Commons.OUTPUT + System.getProperty( "file.separator");
		
		//args[1]
		float tpm = Float.parseFloat(args[1]);
		String tpmString = Step1_NonExpressingProteinCodingGenesIntervalsPoolCreation.getTPMString(tpm);

		//args[2]
		DnaseOverlapExclusionType dnaseOverlapExclusionType = DnaseOverlapExclusionType.convertStringtoEnum(args[2]);
		
		//args[3]
		float FDR = ( args.length > 3)?Float.parseFloat(args[3]):0.05f;
		
		//args[4]
		float bonferroniCorrectionSignificanceLevel = ( args.length > 4)?Float.parseFloat( args[4]):0.05f;
		
		//args[5]
		MultipleTestingType multipleTestingParameter = MultipleTestingType.convertStringtoEnum(args[5]);
		
		int numberofTFElementsInThisCellLine 		= NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder, ElementType.TF, Commons.GM12878);
		int numberofHistoneElementsInThisCellLine 	= NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder, ElementType.HISTONE, Commons.GM12878);

		//args[6]
		// Should I use pValueCalculatedFromZScore or pValueCalculatedFromNumberofPermutationsSuchThat...Ratio
		EnrichmentDecision enrichmentDecision = EnrichmentDecision.convertStringtoEnum(args[6]);

		//args[7]
		int numberofSimulations = ( args.length > 7)?Integer.parseInt(args[7]):100;

		//args[8]
		GenerateRandomDataMode generateRandomDataMode = GenerateRandomDataMode.convertStringtoEnum(args[8]);
		
		readSimulationGLANETResults(
				outputFolder, 
				tpmString, 
				dnaseOverlapExclusionType, 
				numberofSimulations,
				numberofTFElementsInThisCellLine, 
				ElementType.TF, 
				Commons.GM12878, 
				Commons.POL2_GM12878,
				bonferroniCorrectionSignificanceLevel, 
				FDR, 
				multipleTestingParameter, 
				enrichmentDecision,
				generateRandomDataMode);
		
		readSimulationGLANETResults(
				outputFolder, 
				tpmString, 
				dnaseOverlapExclusionType, 
				numberofSimulations,
				numberofHistoneElementsInThisCellLine, 
				ElementType.HISTONE, 
				Commons.GM12878, 
				Commons.H3K4ME3_GM12878,
				bonferroniCorrectionSignificanceLevel, 
				FDR, 
				multipleTestingParameter, 
				enrichmentDecision,
				generateRandomDataMode);
		
		readSimulationGLANETResults(
				outputFolder, 
				tpmString, 
				dnaseOverlapExclusionType, 
				numberofSimulations,
				numberofHistoneElementsInThisCellLine, 
				ElementType.HISTONE, 
				Commons.GM12878, 
				Commons.H3K27ME3_GM12878,
				bonferroniCorrectionSignificanceLevel, 
				FDR, 
				multipleTestingParameter, 
				enrichmentDecision,
				generateRandomDataMode);
	}

}
