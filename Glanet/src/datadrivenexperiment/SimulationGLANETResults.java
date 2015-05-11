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
import enumtypes.MultipleTestingType;

/**
 * @author Burçak Otlu
 * @date May 1, 2015
 * @project Glanet 
 *
 */
public class SimulationGLANETResults {
	
	
	public static boolean isEnriched(String strLine){
		
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
		
		
		//50083	USF1_H1HESC	10	4	10000	406	4E-4	1.624E-1	5.933333E-3	true

		indexofFirstTab 	= strLine.indexOf('\t');
		indexofSecondTab 	= (indexofFirstTab>0) ? strLine.indexOf('\t',indexofFirstTab+1) : -1;
		indexofThirdTab 	= (indexofSecondTab>0) ? strLine.indexOf('\t',indexofSecondTab+1) : -1;
		indexofFourthTab 	= (indexofThirdTab>0) ? strLine.indexOf('\t',indexofThirdTab+1) : -1;
		indexofFifthTab 	= (indexofFourthTab>0) ? strLine.indexOf('\t',indexofFourthTab+1) : -1;
		indexofSixthTab 	= (indexofFifthTab>0) ? strLine.indexOf('\t',indexofFifthTab+1) : -1;
		indexofSeventhTab 	= (indexofSixthTab>0) ? strLine.indexOf('\t',indexofSixthTab+1) : -1;
		indexofEigthTab 	= (indexofSeventhTab>0) ? strLine.indexOf('\t',indexofSeventhTab+1) : -1;
		
		
		bonnCorrectedPValue = Float.parseFloat(strLine.substring(indexofSeventhTab+1, indexofEigthTab));
		
		if (bonnCorrectedPValue <= 0.05f){
			isEnriched = true;
		}
		
		return isEnriched;
		
	}
	
	
	public static FunctionalElementMinimal getElement(String strLine, int numberofComparisons){
		
		
		String elementName;
		
		int originalNumberofOverlaps;
		int numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps;
		int numberofPermutations;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		
		//example strLine
		//50083	USF1_H1HESC	10	4	10000	406	4E-4	1.624E-1	5.933333E-3	true

		indexofFirstTab 	= strLine.indexOf('\t');
		indexofSecondTab 	= (indexofFirstTab>0) ? strLine.indexOf('\t',indexofFirstTab+1) : -1;
		indexofThirdTab 	= (indexofSecondTab>0) ? strLine.indexOf('\t',indexofSecondTab+1) : -1;
		indexofFourthTab 	= (indexofThirdTab>0) ? strLine.indexOf('\t',indexofThirdTab+1) : -1;
		indexofFifthTab 	= (indexofFourthTab>0) ? strLine.indexOf('\t',indexofFourthTab+1) : -1;
		
		elementName = strLine.substring(indexofFirstTab+1,indexofSecondTab);
		originalNumberofOverlaps = Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
		numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = Integer.parseInt(strLine.substring(indexofThirdTab+1,indexofFourthTab));
		numberofPermutations =  Integer.parseInt(strLine.substring(indexofFourthTab+1,indexofFifthTab));
		
		FunctionalElementMinimal element = new FunctionalElementMinimal();
		
		element.setName(elementName);
		element.setNumberofPermutations(numberofPermutations);
		element.setNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps(numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps);
		element.setOriginalNumberofOverlaps(originalNumberofOverlaps);
		element.setNumberofComparisons(numberofComparisons);
		
		return element;
	}
	
	public static void readSimulationGLANETResults(
			String outputFolder, 
			String tpmString,
			String dnaseOverlapsExcludedorNot,
			int numberofSimulations,
			int numberofComparisons, 
			ElementType elementType, 
			String cellLineName,
			String elementNameCellLineName,
			Float bonferroniCorrectionSignificanceLevel,
			Float FDR, 
			MultipleTestingType multipleTestingParameter){
		
		DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat("0.######E0");
		
		int numberofSimulationsThatHasElementNameCellLineNameEnriched = 0;
		
		String strLine =null;
		
		String enrichmentFile = null;
		File enrichmentDirectory = null;
		
		FileReader enrichmentFileReader = null;
		BufferedReader enrichmentBufferedReader = null;

		FileWriter cellLineFilteredEnrichmentFileWriter = null;
		BufferedWriter cellLineFilteredEnrichmentBufferedWriter = null;
		
		List<FunctionalElementMinimal> elementList = null;
		FunctionalElementMinimal element = null;
 		
		try {
			
			
			//For each simulation
			for(int i = 0; i<numberofSimulations; i++){
			
				//Initialize elementList
				elementList = new ArrayList<FunctionalElementMinimal>();
				
				enrichmentDirectory = new File(outputFolder + tpmString + dnaseOverlapsExcludedorNot + Commons.SIMULATION + i + System.getProperty("file.separator") + Commons.ENRICHMENT + System.getProperty("file.separator") + elementType.convertEnumtoString());
				
				//Get the enrichmentFile in this folder for this simulation
				//There must only one enrichmentFile
				if (enrichmentDirectory.exists() && enrichmentDirectory.isDirectory()) {
					
					for (File eachEnrichmentFile : enrichmentDirectory.listFiles()) {
						
						if (!eachEnrichmentFile.isDirectory()) {
	
							enrichmentFile = eachEnrichmentFile.getAbsolutePath();
						
						}//End of IF TFEnrichmnentFile under TFEnrichmentDirectory
						
					}//End of For each file under TFEnrichmentDirectory
	
				}//End of IF TFEnrichmentDirectory Exists
	
				
				
				enrichmentFileReader = FileOperations.createFileReader(enrichmentFile);
				enrichmentBufferedReader = new BufferedReader(enrichmentFileReader);
						
				cellLineFilteredEnrichmentFileWriter = FileOperations.createFileWriter(enrichmentDirectory + System.getProperty("file.separator") +  elementType.convertEnumtoString() +  "_" + cellLineName + ".txt");
				cellLineFilteredEnrichmentBufferedWriter = new BufferedWriter(cellLineFilteredEnrichmentFileWriter);
				
				//Read enrichmentFile
				//Filter lines that contain cellLine only
				while((strLine = enrichmentBufferedReader.readLine())!=null){
					
					if (strLine.contains(cellLineName)){
						//Fill elementList
						elementList.add(getElement(strLine,numberofComparisons));
					}
					
				}//End of While
				
				//Calculate Bonferroni Corrected P 	Value
				BonferroniCorrection.calculateBonferroniCorrectedPValue(elementList);
				
				//Sort w.r.t. empiricalPValue
				Collections.sort(elementList, FunctionalElementMinimal.EMPIRICAL_P_VALUE);
				
				//Calculate BH FDR Adjusted PValue
				BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValue(elementList,FDR);
				
				// sort w.r.t. BH or BonferroniCorrection
				switch(multipleTestingParameter){
					case BONFERRONI_CORRECTION: 	Collections.sort(elementList, FunctionalElementMinimal.BONFERRONI_CORRECTED_P_VALUE);
													break;
					case BENJAMINI_HOCHBERG_FDR: 	Collections.sort(elementList, FunctionalElementMinimal.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
													break;
					default:	break;
				
				}//End of switch
				
			
				//Write new enrichmentFile
				//while writing count the numberofSimulations that has found the elementNameCellLineName enriched
				
				//Header Line
				cellLineFilteredEnrichmentBufferedWriter.write(	"ElementName" + "\t" + 
						"OriginalNumberofOverlaps" + "\t" +  
						"NumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps" + "\t" +
						"NumberofPermutations" + "\t" + 
						"NumberofComparisons" + "\t" + 
						"EmpiricalPValue" + "\t" + 
						"BonferroniCorrectedPValue" + "\t" + 
						"BHFDRAdjustedPValue" + "\t" + 
						"isRejectNullHypothesis" +
						System.getProperty("line.separator"));

				//Write each element in elementList
				for(int j = 0; j<elementList.size(); j++){
					
					element = elementList.get(j);
					
					if(element.getName().contains(elementNameCellLineName)){
						
						switch(multipleTestingParameter){
							case BONFERRONI_CORRECTION: 	if (element.getBonferroniCorrectedPValue() <= bonferroniCorrectionSignificanceLevel){
																numberofSimulationsThatHasElementNameCellLineNameEnriched++;
															}
															break;
															
							case BENJAMINI_HOCHBERG_FDR: 	if (element.getBHFDRAdjustedPValue() <= FDR){
																numberofSimulationsThatHasElementNameCellLineNameEnriched++;
															}
															break;
							default:	break;
						
						}//End of switch
						
					}//End of If elementName contains elementNameCellLineName
					
					
					
					cellLineFilteredEnrichmentBufferedWriter.write(	element.getName() + "\t" + 
																	element.getOriginalNumberofOverlaps() + "\t" +  
																	element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() + "\t" +
																	element.getNumberofPermutations() + "\t" + 
																	element.getNumberofComparisons() + "\t" + 
																	df.format(element.getEmpiricalPValue()) + "\t" + 
																	df.format(element.getBonferroniCorrectedPValue()) + "\t" + 
																	df.format(element.getBHFDRAdjustedPValue()) + "\t" + 
																	element.isRejectNullHypothesis() +
																	System.getProperty("line.separator"));
					
				}//End of for each element
				
				
				//Close 
				enrichmentBufferedReader.close();
				cellLineFilteredEnrichmentBufferedWriter.close();
					
				elementList= null;
			}//End of for each simulation	
		
				System.out.println("Number of simulations that has " +  elementNameCellLineName + " is enriched "  + numberofSimulationsThatHasElementNameCellLineNameEnriched + " out of " + numberofSimulations + " simulations.");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	

	
	
	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");
		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty("file.separator") ;
		
		String tpmString = Commons.TPM_0;
		//String dnaseOverlapsExcludedorNot = Commons.PARTIALLY_DNASE_OVERLAPS_EXCLUSION;
		String dnaseOverlapsExcludedorNot = Commons.COMPLETELY_DNASE_OVERLAPS_EXCLUSION;
		//String dnaseOverlapsExcludedorNot = Commons.NON_EXPRESSING_GENES;
		
		
		float FDR = 0.05f;
		float bonferroniCorrectionSignificanceLevel = 0.05f;
		MultipleTestingType multipleTestingParameter = MultipleTestingType.BONFERRONI_CORRECTION;
		//MultipleTestingType multipleTestingParameter = MultipleTestingType.BENJAMINI_HOCHBERG_FDR;
		
		int numberofTFElementsInCellLine = NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder,ElementType.TF,Commons.GM12878);
		int numberofHistoneElementsInCellLine =NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder,ElementType.HISTONE,Commons.GM12878);
		
		int numberofSimulations = 100;
		
		readSimulationGLANETResults(outputFolder,tpmString,dnaseOverlapsExcludedorNot,numberofSimulations,numberofTFElementsInCellLine,ElementType.TF,Commons.GM12878,Commons.POL2_GM12878, bonferroniCorrectionSignificanceLevel,FDR, multipleTestingParameter);
		readSimulationGLANETResults(outputFolder,tpmString,dnaseOverlapsExcludedorNot,numberofSimulations,numberofHistoneElementsInCellLine,ElementType.HISTONE,Commons.GM12878,Commons.H3K4ME3_GM12878,bonferroniCorrectionSignificanceLevel,FDR, multipleTestingParameter);
		

	}

}
