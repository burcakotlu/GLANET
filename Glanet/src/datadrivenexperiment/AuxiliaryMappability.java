/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import common.Commons;
import enumtypes.ChromosomeName;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentDnaseOverlapExclusionType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTPMType;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TShortArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import intervaltree.Interval;
import mapability.ChromosomeBasedMappabilityTroveList;
import mapability.Mapability;

/**
 * @author burcakotlu
 * 
 * In this class, our aim is to calculate the mappability of randomly generated intervals of Data Driven Computational Experiments Data
 * 
 * Motivation: In this way, we may know the workspace files we need to provide to GAT for mappability.
 *
 */
public class AuxiliaryMappability {
	
	
	public static void readDDCEData(
			String ddeFolder,
			String ddeDataFolder,
			DataDrivenExperimentCellLineType cellLineType, 
			DataDrivenExperimentGeneType geneType, 
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType,
			int numberofRuns,
			Set<DataDrivenExperimentTPMType> tpmTypes,
			TIntObjectMap<TIntList> chrName2MapabilityChromosomePositionList,
			TIntObjectMap<TShortList> chrName2MapabilityShortValueList
			){
		
		
		try {
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
			
			String dataFileName = null;
			
			FileWriter fileWriter = null;
			BufferedWriter bufferedWriter = null;
			
			String strLine = null;
			
			int indexofFirstTab;
			int indexofSecondTab;
			
			ChromosomeName chrName;
			int low;
			int high;
			Interval interval = null;
			
			TIntList mapabilityChromosomePositionList = null;
			TShortList mapabilityShortValueList = null;


			float mappability = 0;
			float accumulatedMappability = 0;
			float avgMappabilityPerDataFile = 0;
			
			int numberofIntervals = 0;
			
			fileWriter = new FileWriter(ddeFolder + "Mappabilities.txt", true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			float lowestAvgMappabilityPerDataFile = 1f;
			float highestAvgMappabilityPerDataFile = 0f;			
			
			for(int i=0; i<numberofRuns; i++){
				
				for(DataDrivenExperimentTPMType TPMType: tpmTypes) {
			
					dataFileName = ddeDataFolder + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + TPMType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + Commons.DDE_RUN + i + ".txt";
					
					fileReader = new FileReader(dataFileName);
					bufferedReader = new BufferedReader(fileReader);
					
						
					//Initialization
					numberofIntervals = 0;
					accumulatedMappability = 0;
					avgMappabilityPerDataFile = 0;
					
					
					//read file
					while((strLine=bufferedReader.readLine())!=null){
						
						indexofFirstTab = strLine.indexOf("\t");
						indexofSecondTab = strLine.indexOf("\t", indexofFirstTab+1);
						
						chrName = ChromosomeName.convertStringtoEnum(strLine.substring(0,indexofFirstTab));
						low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
						high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
						
						interval = new Interval(low, high);
						
						mapabilityChromosomePositionList = chrName2MapabilityChromosomePositionList.get(chrName.getChromosomeName());
						mapabilityShortValueList = chrName2MapabilityShortValueList.get(chrName.getChromosomeName());
						
						
						/**************************************************************************************************/
						/**********************MAPABILITY Calculation for Original Input Line starts***********************/
						/**************************************************************************************************/
						// Mapability New Way
						// Using MapabilitShortList
						mappability = Mapability.calculateMapabilityofIntervalUsingTroveList(interval, mapabilityChromosomePositionList, mapabilityShortValueList);
						/**************************************************************************************************/
						/**********************MAPABILITY Calculation for Original Input Line ends*************************/
						/**************************************************************************************************/
						
//						if (mappability > 1f){
//							bufferedWriter.write(dataFileName + "\t" + mappability + System.getProperty("line.separator"));
//						}
						
				
						accumulatedMappability += mappability;
						numberofIntervals++;
						
					}//End of WHILE reading one data file
					
					//Close
					bufferedReader.close();
					avgMappabilityPerDataFile = accumulatedMappability/numberofIntervals;
					
					
					if(avgMappabilityPerDataFile < lowestAvgMappabilityPerDataFile){
						lowestAvgMappabilityPerDataFile = avgMappabilityPerDataFile;
					}
					
					if (avgMappabilityPerDataFile > highestAvgMappabilityPerDataFile){
						highestAvgMappabilityPerDataFile = avgMappabilityPerDataFile;
					}

					
					bufferedWriter.write(dataFileName + "\t" + avgMappabilityPerDataFile + System.getProperty("line.separator"));
					
					
				}//End of each tpm type
				
				
				
			}//End of FOR each run number 
			
			
			bufferedWriter.write("lowestAvgMappabilityPerDataFile: " + "\t" + lowestAvgMappabilityPerDataFile + "\t" +  "highestAvgMappabilityPerDataFile: " + highestAvgMappabilityPerDataFile + System.getProperty("line.separator"));
			
			//Close
			bufferedWriter.close();
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String glanetDataFolder = args[0];
		
		String ddeFolder = args[1];
		String ddeDataFolder = args[1] + Commons.DATA + System.getProperty("file.separator");
		
		DataDrivenExperimentCellLineType cellLineType =  DataDrivenExperimentCellLineType.convertStringtoEnum(args[2]);
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(args[3]);
		DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType = DataDrivenExperimentDnaseOverlapExclusionType.convertStringtoEnum(args[4]);
		
		int numberofRuns = Integer.parseInt(args[5]);
		
		
		/*************************************************************************************************/
		/******************************Get the tpmValues starts*******************************************/
		/*************************************************************************************************/
		//We can not sort on values. There can be more than one values with same tpmValue
		//tpmTypes are sorted in ascending order according to the integer after the word TOP
		
		SortedMap<DataDrivenExperimentTPMType,Float> expGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		SortedMap<DataDrivenExperimentTPMType,Float> nonExpGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		
		Set<DataDrivenExperimentTPMType> tpmTypes = null;
		//Collection<Float> tpmValues = null;
		
		switch(geneType){
		
			case EXPRESSING_PROTEINCODING_GENES:
				DataDrivenExperimentCommon.fillTPMType2TPMValueMap(ddeFolder,cellLineType,geneType,expGenesTPMType2TPMValueSortedMap);
				tpmTypes = expGenesTPMType2TPMValueSortedMap.keySet();
				//tpmValues = expGenesTPMType2TPMValueSortedMap.values();
				
				break;
				
			case NONEXPRESSING_PROTEINCODING_GENES:
				DataDrivenExperimentCommon.fillTPMType2TPMValueMap(ddeFolder,cellLineType,geneType,nonExpGenesTPMType2TPMValueSortedMap);
				tpmTypes = nonExpGenesTPMType2TPMValueSortedMap.keySet();
				//tpmValues = nonExpGenesTPMType2TPMValueSortedMap.values();
				break;
				
		}//End of SWITCH for geneType
		
		
		/*************************************************************************************************/
		/******************************Get the tpmValues ends*********************************************/
		/*************************************************************************************************/
		
		
		/*************************************************************************************************/
		/*******************Get mappability data structures being filled starts***************************/
		/*************************************************************************************************/
		TIntObjectMap<TIntList> chrName2MapabilityChromosomePositionList = new TIntObjectHashMap<TIntList>();
		TIntObjectMap<TShortList> chrName2MapabilityShortValueList = new TIntObjectHashMap<TShortList>();
		
		for(ChromosomeName chrName : ChromosomeName.values() ){
			
			TIntList mapabilityChromosomePositionList = new TIntArrayList();
			TShortList mapabilityShortValueList = new TShortArrayList();
		
			// GLANET Always Fills
			// for Mappability
			// mapabilityChromosomePositionList
			// mapabilityShortValueList
			ChromosomeBasedMappabilityTroveList.fillTroveList( 
					glanetDataFolder, 
					chrName,
					mapabilityChromosomePositionList, 
					mapabilityShortValueList);
			
			//Put into a map
			chrName2MapabilityChromosomePositionList.put(chrName.getChromosomeName(), mapabilityChromosomePositionList);
			chrName2MapabilityShortValueList.put(chrName.getChromosomeName(), mapabilityShortValueList);

			
		}//End of for each chromosome
		/*************************************************************************************************/
		/*******************Get mappability data structures being filled ends*****************************/
		/*************************************************************************************************/

		
		readDDCEData(
				ddeFolder,
				ddeDataFolder,
				cellLineType, 
				geneType, 
				dnaseOverlapExclusionType,
				numberofRuns,
				tpmTypes,
				chrName2MapabilityChromosomePositionList,
				chrName2MapabilityShortValueList);
		
	}

}
