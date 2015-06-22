/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import auxiliary.FileOperations;
import common.Commons;
import enrichment.InputLine;
import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import enumtypes.DnaseOverlapExclusionType;

/**
 * @author Burçak Otlu
 * @date May 1, 2015
 * @project Glanet 
 * 
 * Data Driven Experiment Step 3
 *
 */
public class SimulationDataCreation {
	
	public static String getIntervalPoolFileName(String tpmString, DnaseOverlapExclusionType dnaseOverlapExclusionType,String dataFolder){
		
		String intervalPoolFileName =  dataFolder + Commons.demo_input_data + System.getProperty("file.separator") +  tpmString + "_" + dnaseOverlapExclusionType.convertEnumtoString() +  "Intervals_EndInclusive.txt";
		
		return intervalPoolFileName;
	}
	
	public static void fillIntervalPoolData(
			String intervalPoolFileName,
			List<InputLine>  intervalPoolData){
		
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		ChromosomeName chrName;
		int low;
		int high;
		
		InputLine inputLine = null;
		
		try {
			
			fileReader = FileOperations.createFileReader(intervalPoolFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//chr1	68591	69191	"OR4F5"
			//chrX	2669591	2670191
			
			while((strLine= bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = (indexofFirstTab>0)? strLine.indexOf('\t',indexofFirstTab+1) : -1;
				indexofThirdTab = (indexofSecondTab>0)? strLine.indexOf('\t',indexofSecondTab+1) : -1;
				
				chrName = ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab));
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				
				if (indexofThirdTab>0){
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				}else{
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				}
				
				inputLine = new InputLine(chrName, low, high);
				intervalPoolData.add(inputLine);
				
			}//End of while
					
			
			//Close bufferedReader
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	public static void fillRandomIntervalIndexes(
			int[] randomIntervalIndexes,
			int numberofIntervalsInEachSimulation,
			int numberofIntervalsInIntervalPool){
		
		Random rand = new Random(); 
		
		for(int i = 0; i < numberofIntervalsInEachSimulation ; i++ ){
			randomIntervalIndexes[i] = rand.nextInt(numberofIntervalsInIntervalPool); 
		}//End of For
		
	}
	
	public static void writeSimulationData(
			int[] randomIntervalIndexes,
			List<InputLine> intervalPoolData,
			String simulationDataFile){
		
		InputLine inputLine = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			fileWriter = FileOperations.createFileWriter(simulationDataFile);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(int i = 0; i<randomIntervalIndexes.length; i++){
				inputLine = intervalPoolData.get(randomIntervalIndexes[i]);
				
				bufferedWriter.write(inputLine.getChrName().convertEnumtoString() + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + System.getProperty("line.separator"));
			}
			
			//Close bufferedWriter
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void generateRandomSimulationData(
			String dataFolder,
			String tpmString, 
			DnaseOverlapExclusionType dnaseOverlapExclusionType,
			String intervalPoolFileName,
			int numberofSimulations,
			int numberofIntervalsInEachSimulation){
		
		//There will be one interval pool
		List<InputLine> intervalPoolData = new ArrayList<InputLine>();
		
		//There will be one for each simulation
		String simulationDataFile = null;
		int[] randomIntervalIndexes = null;
		
		fillIntervalPoolData(intervalPoolFileName,intervalPoolData);
		
		String baseFolderName = null;
		
		//Set baseFolderName
		baseFolderName = dataFolder + System.getProperty("file.separator") + Commons.SIMULATION_DATA + System.getProperty("file.separator") + tpmString + "_" + dnaseOverlapExclusionType.convertEnumtoString() ;
		
		
		for(int i = 0; i <numberofSimulations; i++){
			
			//Set simulationDataFile
			simulationDataFile = baseFolderName + Commons.SIMULATION +  i + ".txt"; 
			
			randomIntervalIndexes = new int[numberofIntervalsInEachSimulation];
			
			//Get random indexes for each simulation
			fillRandomIntervalIndexes(randomIntervalIndexes,numberofIntervalsInEachSimulation,intervalPoolData.size());
			
			//Write Simulation Data
			writeSimulationData(randomIntervalIndexes,intervalPoolData,simulationDataFile);
		
			
		}//End of for each simulation
		
	}
	
	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");
		
		
		
		//Parameters for Simulations
		//TPM Run simulations 
		//String tpmString = Commons.TPM_0_1;
		//String tpmString = Commons.TPM_0_01;
		String tpmString = Commons.TPM_0_001;
		
		//boolean isDnaseOverlapsExclusionCompletely = true;
		//String dnaseOverlapsExclusionPartiallyorCompletely = DnaseOverlapsExclusionfromNonExpressingGenesIntervalsPoolCreation.getDnaseOverlapsExclusionString(isDnaseOverlapsExclusionCompletely);
		
		//DnaseOverlapExclusionType dnaseOverlapExclusionType  = DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
		DnaseOverlapExclusionType dnaseOverlapExclusionType  = DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
		
		
		//Depending on tpmString and dnaseOverlapsExcluded
		//Set IntervalPoolFile
		String intervalPoolFileName  = getIntervalPoolFileName(tpmString,dnaseOverlapExclusionType,dataFolder);
		
		//Other Parameters for Simulations
		//Number of Simulations
		int numberofSimulations = 1000;
		//Number of intervals in each simulation
		int numberofIntervalsInEachSimulation = 500;
		
		
		//Generate Simulations Data
		//Get random numberofIntervalsInEachSimulation intervals from intervalPool for each simulation
		generateRandomSimulationData(dataFolder,tpmString, dnaseOverlapExclusionType,intervalPoolFileName,numberofSimulations,numberofIntervalsInEachSimulation);
		
		//Then for each simulationData I will run GLANET
		//Count the numberofSimulations that have POL2_GM12878 enriched
		//NumberofSimulations/totalNumberofSimulations will be my false positive rate for POL2_GM12878
		
		
	}

}
