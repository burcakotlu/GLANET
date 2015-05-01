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

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.CommandLineArguments;

/**
 * @author Burçak Otlu
 * @date May 1, 2015
 * @project Glanet 
 *
 */
public class SimulationGLANETResults {
	
	
	public static boolean isEnriched_POL2_GM12878(String strLine){
		
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
	
	public static void readSimulationGLANETResultsFilterGM12878(
			String outputFolder,
			int numberofSimulations,
			String elementType,
			String cellLineName,
			String elementName_CellLineName) {
		
		int numberofSimulations_POL2_GM12878_Enriched = 0;
		
		String strLine =null;
		
		String tfEnrichmentFile = null;
		File TFEnrichmentDirectory = null;
		
		
		FileReader TFEnrichmentFileReader = null;
		BufferedReader TFEnrichmentBufferedReader = null;

		FileWriter TFGM12878EnrichmentFileWriter = null;
		BufferedWriter TFGM12878EnrichmentBufferedWriter = null;
		
		try {
			
			
		
			for(int i = 0; i<numberofSimulations; i++){
			
				TFEnrichmentDirectory = new File(outputFolder + Commons.SIMULATION + i + System.getProperty("file.separator") + Commons.ENRICHMENT + System.getProperty("file.separator") + elementType);
				
				//Get the file in this folder
				if (TFEnrichmentDirectory.exists() && TFEnrichmentDirectory.isDirectory()) {
					
					for (File eachTFEnrichmentFile : TFEnrichmentDirectory.listFiles()) {
						
						if (!eachTFEnrichmentFile.isDirectory()) {
	
							tfEnrichmentFile = eachTFEnrichmentFile.getAbsolutePath();
	
						
						}//End of IF TFEnrichmnentFile under TFEnrichmentDirectory
						
					}//End of For each file under TFEnrichmentDirectory
	
				}//End of IF TFEnrichmentDirectory Exists
	
				
				
				TFEnrichmentFileReader = FileOperations.createFileReader(tfEnrichmentFile);
				TFEnrichmentBufferedReader = new BufferedReader(TFEnrichmentFileReader);
						
				TFGM12878EnrichmentFileWriter = FileOperations.createFileWriter(TFEnrichmentDirectory + System.getProperty("file.separator") +  elementType +  "_" + cellLineName + ".txt");
				TFGM12878EnrichmentBufferedWriter = new BufferedWriter(TFGM12878EnrichmentFileWriter);
				
				while((strLine = TFEnrichmentBufferedReader.readLine())!=null){
					
					if (strLine.contains(cellLineName)){
						TFGM12878EnrichmentBufferedWriter.write(strLine + System.getProperty("line.separator"));
					
						if (strLine.contains(elementName_CellLineName)){
							
							//IF POL2_GM12878 is enriched
							if (isEnriched_POL2_GM12878(strLine)){
								numberofSimulations_POL2_GM12878_Enriched++;
							}
							
							
						}
					}
					
				}//End of While
				
				
				//Close 
				TFEnrichmentBufferedReader.close();
				TFGM12878EnrichmentBufferedWriter.close();
				
				}//End of for each simulation	
		
				System.out.println("Number of simulations that has " +  elementName_CellLineName + " is enriched "  + numberofSimulations_POL2_GM12878_Enriched + " out of " + numberofSimulations + " simulations.");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void readSimulationGLANETResults(String outputFolder, int numberofSimulations){
		
		//Read Simulation TF Enrichment File
		//Filter only GM12878 cell lines
		//Write Simulation TF_GM12878 Enrichment File
		readSimulationGLANETResultsFilterGM12878(outputFolder,numberofSimulations,Commons.TF, Commons.GM12878, Commons.POL2_GM12878);
	
		
		//Read Simulation HM Enrichment File
		//Filter only GM12878 cell lines
		//Write Simulation HM_GM12878 Enrichment File
		readSimulationGLANETResultsFilterGM12878(outputFolder,numberofSimulations,Commons.HISTONE, Commons.GM12878, Commons.H3K4ME3_GM12878);
		
		
		//Read Simulation Dnase Enrichment File
		//Filter only GM12878 cell lines
		//Write Simulation DnaseGM12878 Enrichment File
				
				
				
				
	}
	
	
	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty("file.separator");
		
		int numberofSimulations = 27;
		
		readSimulationGLANETResults(outputFolder,numberofSimulations);
		

	}

}
