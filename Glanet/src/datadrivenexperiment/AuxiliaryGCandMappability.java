/**
 * 
 */
package datadrivenexperiment;

import intervaltree.Interval;
import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import mapability.ChromosomeBasedMappabilityTroveList;
import mapability.Mapability;

import common.Commons;

import enumtypes.CalculateGC;
import enumtypes.ChromosomeName;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTPMType;
import gc.ChromosomeBasedGCIntervalTree;
import gc.GC;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TShortArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * @author burcakotlu
 * 
 * In this class, our aim is to calculate the mappability of randomly generated intervals of Data Driven Computational Experiments Data
 * 
 * Motivation: In this way, we may know the workspace files we need to provide to GAT for mappability.
 *
 */
public class AuxiliaryGCandMappability {
	
	
	public static void readDDCEData(
			String ddeFolder,
			String ddeIntervalPoolFolder,
			DataDrivenExperimentCellLineType cellLineType, 
			DataDrivenExperimentGeneType geneType, 
			Set<DataDrivenExperimentTPMType> tpmTypes,
			TIntObjectMap<TIntList> chrName2MapabilityChromosomePositionList,
			TIntObjectMap<TShortList> chrName2MapabilityShortValueList,
			TIntObjectMap<IntervalTree> chrName2GCIntervalTreeMap){
		
		try {
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
			
			String dataFileName = null;
			
			FileWriter mappabilityFileWriter = null;
			BufferedWriter mappabilityBufferedWriter = null;
			
			FileWriter gcFileWriter = null;
			BufferedWriter gcBufferedWriter = null;
			
			String strLine = null;
			
			int indexofFirstTab;
			int indexofSecondTab;
			int indexofThirdTab;
			
			
			ChromosomeName chrName;
			int low;
			int high;
			Interval interval = null;
			
			//*****************************//
			//******** Mappability ********//
			//*****************************//
			TIntList mapabilityChromosomePositionList = null;
			TShortList mapabilityShortValueList = null;

			float mappability = 0;
			float accumulatedMappability = 0;
			float avgMappability = 0;
			
			int numberoIntervalsWithMappabilityGreaterThanOne =0;
			int numberoIntervalsWithMappabilityLessThanPointFive =0;
			
			//*****************************//
			//******** Mappability ********//
			//*****************************//

				
			//*****************************//
			//************ GC *************//
			//*****************************//
			IntervalTree gcIntervalTree = null;
			
			float gc = 0;
			float accumulatedGC = 0;
			float avgGC = 0;
			
			int numberoIntervalsWithGCGreaterThanOne =0;
			int numberoIntervalsWithGCLessThanPointFive =0;
			//*****************************//
			//************ GC *************//
			//*****************************//

			int numberofIntervals = 0;
			
			for(DataDrivenExperimentTPMType TPMType: tpmTypes) {
			
					dataFileName = ddeIntervalPoolFolder + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + TPMType.convertEnumtoString()  + "_" +  Commons.INTERVAL_POOL + ".txt";
					
					fileReader = new FileReader(dataFileName);
					bufferedReader = new BufferedReader(fileReader);
					
					
					mappabilityFileWriter = new FileWriter(ddeFolder + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + TPMType.convertEnumtoString()  +  "_Mappabilities.txt");
					mappabilityBufferedWriter = new BufferedWriter(mappabilityFileWriter);
						
					gcFileWriter = new FileWriter(ddeFolder + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + TPMType.convertEnumtoString()  + "_GC.txt");
					gcBufferedWriter = new BufferedWriter(gcFileWriter);


					
					//Initialization
					numberofIntervals = 0;
					
					accumulatedMappability = 0;
					avgMappability = 0;
					numberoIntervalsWithMappabilityGreaterThanOne =0;
					numberoIntervalsWithMappabilityLessThanPointFive =0;
					
					accumulatedGC = 0;
					avgGC = 0;
					numberoIntervalsWithGCGreaterThanOne =0;
					numberoIntervalsWithGCLessThanPointFive =0;
					
					//read file
					while((strLine=bufferedReader.readLine())!=null){
						
						indexofFirstTab = strLine.indexOf("\t");
						indexofSecondTab = strLine.indexOf("\t", indexofFirstTab+1);
						indexofThirdTab = strLine.indexOf("\t", indexofSecondTab+1);
						
						chrName = ChromosomeName.convertStringtoEnum(strLine.substring(0,indexofFirstTab));
						low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
						high = Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
						
						interval = new Interval(low, high);
						
						
						/**************************************************************************************************/
						/***************************Filling of MAPPABILITY Data Structures starts**************************/
						/**************************************************************************************************/
						mapabilityChromosomePositionList = chrName2MapabilityChromosomePositionList.get(chrName.getChromosomeName());
						mapabilityShortValueList = chrName2MapabilityShortValueList.get(chrName.getChromosomeName());
						/**************************************************************************************************/
						/***************************Filling of MAPPABILITY Data Structures ends****************************/
						/**************************************************************************************************/
						
						
						/**************************************************************************************************/
						/**********************MAPABILITY Calculation starts***********************************************/
						/**************************************************************************************************/
						mappability = Mapability.calculateMapabilityofIntervalUsingTroveList(interval, mapabilityChromosomePositionList, mapabilityShortValueList);
						/**************************************************************************************************/
						/**********************MAPABILITY Calculation ends*************************************************/
						/**************************************************************************************************/
						
												
						/**************************************************************************************************/
						/***************************Filling of GC Data Structures starts***********************************/
						/**************************************************************************************************/
						gcIntervalTree = chrName2GCIntervalTreeMap.get(chrName.getChromosomeName());
						/**************************************************************************************************/
						/***************************Filling of GC Data Structures ends*************************************/
						/**************************************************************************************************/
					
						/**************************************************************************************************/
						/**********************GC Calculation starts*******************************************************/
						/**************************************************************************************************/
						gc = GC.calculateGCofIntervalUsingIntervalTree(interval, gcIntervalTree,CalculateGC.CALCULATE_GC_USING_GC_INTERVAL_TREE);
						/**************************************************************************************************/
						/**********************GC Calculation ends*********************************************************/
						/**************************************************************************************************/
						
						
						mappabilityBufferedWriter.write(dataFileName + "\t" + mappability + System.getProperty("line.separator"));
						gcBufferedWriter.write(dataFileName + "\t" + gc + System.getProperty("line.separator"));

						if (mappability > 1f){
							numberoIntervalsWithMappabilityGreaterThanOne++;
							//bufferedWriter.write("There is a situation: " + dataFileName + "\t" + mappability + System.getProperty("line.separator"));
						}else if (mappability < 0.5f){
							numberoIntervalsWithMappabilityLessThanPointFive++;
						}
						
						if (gc > 1f){
							numberoIntervalsWithGCGreaterThanOne++;
							//bufferedWriter.write("There is a situation: " + dataFileName + "\t" + mappability + System.getProperty("line.separator"));
						}else if (gc < 0.5f){
							numberoIntervalsWithGCLessThanPointFive++;
						}
				
						accumulatedMappability += mappability;
						accumulatedGC += gc;
						numberofIntervals++;
						
					}//End of WHILE reading one data file
					
					
					avgMappability = accumulatedMappability/numberofIntervals;
					avgGC = accumulatedGC/numberofIntervals;
					
					mappabilityBufferedWriter.write("AvgMappability" + "\t" + avgMappability + System.getProperty("line.separator"));
					mappabilityBufferedWriter.write("Number of Intervals with Mappability Less Than Point Five" + "\t" + numberoIntervalsWithMappabilityLessThanPointFive + System.getProperty("line.separator"));
					mappabilityBufferedWriter.write("Number of Intervals with Mappability Greater Than One" + "\t" + numberoIntervalsWithMappabilityGreaterThanOne + System.getProperty("line.separator"));

					gcBufferedWriter.write("AvgGC" + "\t" + avgGC + System.getProperty("line.separator"));
					gcBufferedWriter.write("Number of Intervals with GC Less Than Point Five" + "\t" + numberoIntervalsWithGCLessThanPointFive + System.getProperty("line.separator"));
					gcBufferedWriter.write("Number of Intervals with GC Greater Than One" + "\t" + numberoIntervalsWithGCGreaterThanOne + System.getProperty("line.separator"));

					//Close
					mappabilityBufferedWriter.close();
					gcBufferedWriter.close();
					
					//Close
					bufferedReader.close();
		
				}//End of each tpm type
				
			
			
			
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
		String ddeIntervalPoolFolder = args[1] + Commons.INTERVAL_POOL + System.getProperty("file.separator");
				
		
		/*************************************************************************************************/
		/******************************Get the tpmValues starts*******************************************/
		/*************************************************************************************************/
		//We can not sort on values. There can be more than one values with same tpmValue
		//tpmTypes are sorted in ascending order according to the integer after the word TOP
		
		SortedMap<DataDrivenExperimentTPMType,Float> expGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		SortedMap<DataDrivenExperimentTPMType,Float> nonExpGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		
		Set<DataDrivenExperimentTPMType> tpmTypes = null;
		
		
		
		/*************************************************************************************************/
		/******************************Get the tpmValues ends*********************************************/
		/*************************************************************************************************/
		
		
		/*************************************************************************************************/
		/*******************Get GC and mappability data structures being filled starts********************/
		/*************************************************************************************************/
		TIntObjectMap<TIntList> chrName2MapabilityChromosomePositionList = new TIntObjectHashMap<TIntList>();
		TIntObjectMap<TShortList> chrName2MapabilityShortValueList = new TIntObjectHashMap<TShortList>();
		TIntObjectMap<IntervalTree> chrName2GCIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();

		for(ChromosomeName chrName : ChromosomeName.values() ){
			
			/*************************************************/
			/***************Mappability starts****************/
			/*************************************************/
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
			/*************************************************/
			/***************Mappability ends******************/
			/*************************************************/

			
			/*************************************************/
			/***************GC starts*************************/
			/*************************************************/
			IntervalTree gcIntervalTree = new IntervalTree();
			
			ChromosomeBasedGCIntervalTree.fillIntervalTree(glanetDataFolder,chrName,Commons.INTERVAL_LENGTH_100,gcIntervalTree);
			
			chrName2GCIntervalTreeMap.put(chrName.getChromosomeName(),gcIntervalTree);
			/*************************************************/
			/***************GC ends***************************/
			/*************************************************/

			
		}//End of for each chromosome
		/*************************************************************************************************/
		/*******************Get GC and mappability data structures being filled ends**********************/
		/*************************************************************************************************/

		for(DataDrivenExperimentCellLineType cellLineType: DataDrivenExperimentCellLineType.values()){
			
			for(DataDrivenExperimentGeneType geneType: DataDrivenExperimentGeneType.values()){
				
				switch(geneType){
				
					case EXPRESSING_PROTEINCODING_GENES:
						DataDrivenExperimentCommon.fillTPMType2TPMValueMap(ddeFolder,cellLineType,geneType,expGenesTPMType2TPMValueSortedMap);
						tpmTypes = expGenesTPMType2TPMValueSortedMap.keySet();
						break;
						
					case NONEXPRESSING_PROTEINCODING_GENES:
						DataDrivenExperimentCommon.fillTPMType2TPMValueMap(ddeFolder,cellLineType,geneType,nonExpGenesTPMType2TPMValueSortedMap);
						tpmTypes = nonExpGenesTPMType2TPMValueSortedMap.keySet();
						break;
						
				}//End of SWITCH for geneType

				
				readDDCEData(
						ddeFolder,
						ddeIntervalPoolFolder,
						cellLineType, 
						geneType, 
						tpmTypes,
						chrName2MapabilityChromosomePositionList,
						chrName2MapabilityShortValueList,
						chrName2GCIntervalTreeMap);
				
			}//End of for each gene type 
			
		}//End of for each cell line
		
		
		
	}

}
