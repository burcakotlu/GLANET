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
import enumtypes.CommandLineArguments;
import enumtypes.ElementType;
import enumtypes.EnrichmentDecision;
import enumtypes.MultipleTestingType;

/**
 * @author Burcak Otlu
 * @date May 1, 2015
 * @project Glanet 
 * 
 * Data Driven Experiment Step 5
 *
 */
public class SimulationGLANETResults {

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

	public static FunctionalElementMinimal getElement( String strLine, int numberofComparisons,
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

		case OLD_RESULT_FILE_VERSION:

			empiricalPValue = Float.parseFloat( strLine.substring( indexofSixthTab + 1, indexofSeventhTab));
			element.setEmpiricalPValue( empiricalPValue);
			break;

		case P_VALUE_CALCULATED_FROM_Z_SCORE:
		case P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO:
		case BOTH_ZSCORE_AND_NUMBEROFPERMUTATIONSRATIO:

			if( !strLine.substring( indexofEigthTab + 1, indexofNinethTab).equals( "NaN")){

				zScore = Double.parseDouble( strLine.substring( indexofEigthTab + 1, indexofNinethTab));
				element.setZScore( zScore);

				empiricalPValueCalculatedFromZScore = Double.parseDouble( strLine.substring( indexofNinethTab + 1,
						indexofTenthTab));
				element.setEmpiricalPValueCalculatedFromZScore( empiricalPValueCalculatedFromZScore);

			}else{

				element.setZScore( null);
				element.setEmpiricalPValueCalculatedFromZScore( null);

			}

			empiricalPValue = Float.parseFloat( strLine.substring( indexofThirteenthTab + 1, indexofFourteenthTab));
			element.setEmpiricalPValue( empiricalPValue);
			break;

		default:
			break;

		}// End of SWITCH

		return element;
	}

	public static int writeNewEnrichmentFile( List<FunctionalElementMinimal> elementList,
			BufferedWriter cellLineFilteredEnrichmentBufferedWriter, MultipleTestingType multipleTestingParameter,
			String elementNameCellLineName, Float bonferroniCorrectionSignificanceLevel, Float FDR,
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

				cellLineFilteredEnrichmentBufferedWriter.write( "ElementName" + "\t" + "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps" + "\t" + "NumberofPermutations" + "\t" + "NumberofComparisons" + "\t" +

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

						}// End of switch

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

						}// End of switch

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

						}// End of switch
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

	public static void readSimulationGLANETResults( String outputFolder, String tpmString,
			String dnaseOverlapsExcludedorNot, int numberofSimulations, int numberofComparisons,
			ElementType elementType, String cellLineName, String elementNameCellLineName,
			Float bonferroniCorrectionSignificanceLevel, Float FDR, MultipleTestingType multipleTestingParameter,
			EnrichmentDecision enrichmentDecision) {

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

				enrichmentDirectory = new File(
						outputFolder + tpmString + dnaseOverlapsExcludedorNot + Commons.SIMULATION + i + System.getProperty( "file.separator") + Commons.ENRICHMENT + System.getProperty( "file.separator") + elementType.convertEnumtoString());

				// Get the enrichmentFile in this folder for this simulation
				// There must only one enrichmentFile
				if( enrichmentDirectory.exists() && enrichmentDirectory.isDirectory()){

					for( File eachEnrichmentFile : enrichmentDirectory.listFiles()){

						if( !eachEnrichmentFile.isDirectory() && eachEnrichmentFile.getAbsolutePath().contains(
								Commons.ALL_WITH_RESPECT_TO_BH_FDR_ADJUSTED_P_VALUE)){

							enrichmentFile = eachEnrichmentFile.getAbsolutePath();

							break;

						}// End of IF TFEnrichmnentFile under TFEnrichmentDirectory

					}// End of For each file under TFEnrichmentDirectory

				}// End of IF TFEnrichmentDirectory Exists

				enrichmentFileReader = FileOperations.createFileReader( enrichmentFile);
				enrichmentBufferedReader = new BufferedReader( enrichmentFileReader);

				cellLineFilteredEnrichmentFileWriter = FileOperations.createFileWriter( enrichmentDirectory + System.getProperty( "file.separator") + elementType.convertEnumtoString() + "_" + cellLineName + "_" + Commons.SIMULATION + i + ".txt");
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

				}// End of While

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
			}// End of for each simulation

			System.out.println( "Number of simulations that has " + elementNameCellLineName + " is enriched " + numberofSimulationsThatHasElementNameCellLineNameEnriched + " out of " + numberofSimulations + " simulations.");

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main( String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];

		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty( "file.separator");

		//String tpmString = Commons.TPM_1;
		// String tpmString = Commons.TPM_01;
		// String tpmString = Commons.TPM_001;
		String tpmString = Commons.TPM_0_001;
		// String tpmString = Commons.TPM_0;

		//String dnaseOverlapsExcludedorNot = Commons.NON_EXPRESSING_GENES;
		// String dnaseOverlapsExcludedorNot = Commons.COMPLETELY_DNASE_OVERLAPS_EXCLUSION;
		String dnaseOverlapsExcludedorNot = Commons.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;

		float FDR = 0.05f;
		float bonferroniCorrectionSignificanceLevel = 0.05f;
		MultipleTestingType multipleTestingParameter = MultipleTestingType.BONFERRONI_CORRECTION;
		// MultipleTestingType multipleTestingParameter = MultipleTestingType.BENJAMINI_HOCHBERG_FDR;

		int numberofTFElementsInCellLine = NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(
				dataFolder, ElementType.TF, Commons.GM12878);
		int numberofHistoneElementsInCellLine = NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(
				dataFolder, ElementType.HISTONE, Commons.GM12878);

		int numberofSimulations = 100;

		// Should I use pValueCalculatedFromZScore or pValueCalculatedFromNumberofPermutationsSuchThat...Ratio
		// EnrichmentDecision enrichmentDecision =
		// EnrichmentDecision.P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO;
		EnrichmentDecision enrichmentDecision = EnrichmentDecision.P_VALUE_CALCULATED_FROM_Z_SCORE;

		readSimulationGLANETResults( outputFolder, tpmString, dnaseOverlapsExcludedorNot, numberofSimulations,
				numberofTFElementsInCellLine, ElementType.TF, Commons.GM12878, Commons.POL2_GM12878,
				bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, enrichmentDecision);
		readSimulationGLANETResults( outputFolder, tpmString, dnaseOverlapsExcludedorNot, numberofSimulations,
				numberofHistoneElementsInCellLine, ElementType.HISTONE, Commons.GM12878, Commons.H3K4ME3_GM12878,
				bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, enrichmentDecision);
		readSimulationGLANETResults( outputFolder, tpmString, dnaseOverlapsExcludedorNot, numberofSimulations,
				numberofHistoneElementsInCellLine, ElementType.HISTONE, Commons.GM12878, Commons.H3K27ME3_GM12878,
				bonferroniCorrectionSignificanceLevel, FDR, multipleTestingParameter, enrichmentDecision);

	}

}
