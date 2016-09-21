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
import java.util.Arrays;
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
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TShortArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * @author burcakotlu
 * 
 * In this class, our aim is to calculate gc and mappability of intervals in the interval pools.
 * 
 * We have one nonexpressed interval pool and three expressed interval pools for each cell line: GM12878 and K562
 * 
 * Motivation: In this way, we want to know the GC and mappability distribution difference between expressed and nonexpresssed scenario in two cell lines.
 *
 */
public class AuxiliaryGCandMappability {
	
	//cellLineType at hundred position
	//geneType at ten position
	//tpmType at one position
	public static DataDrivenExperimentCellLineType getCellLine(int key){
		
		DataDrivenExperimentCellLineType cellLineType = null;
		
		int geneTypeNumberTPMTypeNumber = key%100;
		
		int cellLineNumber = (key-geneTypeNumberTPMTypeNumber)/100;
		
		if (cellLineNumber==1)
			return DataDrivenExperimentCellLineType.GM12878;
		else if (cellLineNumber==2){
			return DataDrivenExperimentCellLineType.K562;
		}

		return cellLineType;
		
	}
	
	public static DataDrivenExperimentGeneType getGeneType(int key){
		
		DataDrivenExperimentGeneType geneType = null;
		
		int geneTypeNumber = key%100;
		int tpmTypeNumber = key%10;
		
		geneTypeNumber = (geneTypeNumber-tpmTypeNumber)/10;
		
		if (geneTypeNumber==1)
			return DataDrivenExperimentGeneType.NONEXPRESSING_PROTEINCODING_GENES;
		else if (geneTypeNumber==2){
			return DataDrivenExperimentGeneType.EXPRESSING_PROTEINCODING_GENES;
		}
		
		return geneType;
		
	}
	
	
	public static DataDrivenExperimentTPMType getTPMType(int key){
		
		DataDrivenExperimentTPMType tpmType = null;
		
		int tpmTypeNumber = key%10;
		
		if (tpmTypeNumber==1)
			return DataDrivenExperimentTPMType.TOPUNKNOWN;
		else if (tpmTypeNumber==2){
			return DataDrivenExperimentTPMType.TOP5;
		}else if (tpmTypeNumber==3){
			return DataDrivenExperimentTPMType.TOP10;
		}else if (tpmTypeNumber==4){
			return DataDrivenExperimentTPMType.TOP20;
		}
		
		return tpmType;
		
	}
	
	
	public static int computeMaxNumberofIntervals(TIntObjectMap<TFloatList> cellLineGeneTypeTPMType2GCListMap){
		
		int maximumNumberofIntevals = 0;
		
		for(TIntObjectIterator<TFloatList> itr = cellLineGeneTypeTPMType2GCListMap.iterator(); itr.hasNext();){
			
			itr.advance();
			
			if (itr.value().size()>maximumNumberofIntevals){
				maximumNumberofIntevals = itr.value().size();
			}
			
		}//End of for each TFloatList
	
		return maximumNumberofIntevals;
	}
	
	
	public static void writeToFilesForSMPackage(
			TIntObjectMap<TFloatList> cellLineGeneTypeTPMType2FloatListMap,
			String ddeFolder, 
			String mappabilityOrGC){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		FileWriter fileWriter_GM12878 = null;
		BufferedWriter bufferedWriter_GM12878 = null;
		
		FileWriter fileWriter_K562 = null;
		BufferedWriter bufferedWriter_K562 = null;
		
		int key = 0;
		TFloatList floatList= null;
		
		String keyString = null;
		
		DataDrivenExperimentCellLineType cellLine = null;
		DataDrivenExperimentGeneType geneType = null;
		DataDrivenExperimentTPMType tpmType = null;
		
		try {
			
			fileWriter_GM12878 = new FileWriter(ddeFolder + Commons.GM12878 + "_" + mappabilityOrGC + ".txt");
			bufferedWriter_GM12878 = new BufferedWriter(fileWriter_GM12878);
			
			fileWriter_K562 = new FileWriter(ddeFolder + Commons.K562 + "_" + mappabilityOrGC + ".txt");
			bufferedWriter_K562 = new BufferedWriter(fileWriter_K562);

			//Write header line
			bufferedWriter_GM12878.write("Value" + "\t" + "IntervalPool"  + System.getProperty("line.separator"));
			bufferedWriter_K562.write("Value" + "\t" + "IntervalPool"  + System.getProperty("line.separator"));

			
			
			for(TIntObjectIterator<TFloatList> itr =cellLineGeneTypeTPMType2FloatListMap.iterator();itr.hasNext();){

				itr.advance();
				
				key = itr.key();
				floatList = itr.value();
				
				cellLine = getCellLine(key);
				geneType = getGeneType(key);
				tpmType = getTPMType(key);
				
				keyString = cellLine.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString();
				
				for(int i= 0; i<floatList.size(); i++){
					
					if (cellLine.isGM12878()){
						bufferedWriter_GM12878.write(floatList.get(i)+ "\t" + keyString +  System.getProperty("line.separator"));
					}else if (cellLine.isK562()){
						bufferedWriter_K562.write(floatList.get(i)+ "\t" + keyString +  System.getProperty("line.separator"));
					}
					
					

				}//End of for each value in TFloatList
				
			}//End of for each TFloatList
			
			//Close
			bufferedWriter_GM12878.close();
			bufferedWriter_K562.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writeToFiles(
			TIntObjectMap<TFloatList> cellLineGeneTypeTPMType2FloatListMap,
			String ddeFolder, 
			String mappabilityOrGC,
			int maxNumberofIntervals){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		int key = 0;
		int[] keys = cellLineGeneTypeTPMType2FloatListMap.keys();
		
		Arrays.sort(keys);

		DataDrivenExperimentCellLineType cellLine = null;
		DataDrivenExperimentGeneType geneType = null;
		DataDrivenExperimentTPMType tpmType = null;
		
		try {
			
			fileWriter = new FileWriter(ddeFolder + mappabilityOrGC + ".txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//Write header line starts
			for(int i= 0; i<keys.length; i++){
				
				cellLine = getCellLine(keys[i]);
				geneType = getGeneType(keys[i]);
				tpmType = getTPMType(keys[i]);
				
				bufferedWriter.write(cellLine.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString()  + "\t");
			}	
			bufferedWriter.write(System.getProperty("line.separator"));
			//Write header line ends
			
			for(int i=0; i<maxNumberofIntervals; i++){

				for(int j= 0; j<keys.length; j++){
					
					key = keys[j];
					
					//last key
					if (j==keys.length-1){
						if (i<cellLineGeneTypeTPMType2FloatListMap.get(key).size()){
							bufferedWriter.write(cellLineGeneTypeTPMType2FloatListMap.get(key).get(i) + System.getProperty("line.separator"));
						}else{
							bufferedWriter.write("NA" + System.getProperty("line.separator"));
						}
					}else {
						if (i<cellLineGeneTypeTPMType2FloatListMap.get(key).size()){
							bufferedWriter.write(cellLineGeneTypeTPMType2FloatListMap.get(key).get(i) + "\t");

						}else{
							bufferedWriter.write("NA" + "\t");
						}
					}
					
				}//End of for each key --column
				
			}//End of for each --row
			
			//Close
			bufferedWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	public static void readIntervalsInDDCEIntervalPoolComputeGCMappability(
			String ddeFolder,
			String ddeIntervalPoolFolder,
			DataDrivenExperimentCellLineType cellLineType, 
			DataDrivenExperimentGeneType geneType, 
			Set<DataDrivenExperimentTPMType> tpmTypes,
			TIntObjectMap<TIntList> chrName2MapabilityChromosomePositionList,
			TIntObjectMap<TShortList> chrName2MapabilityShortValueList,
			TIntObjectMap<IntervalTree> chrName2GCIntervalTreeMap,
			TIntObjectMap<TFloatList>  cellLineGeneTypeTPMType2GCListMap,
			TIntObjectMap<TFloatList>  cellLineGeneTypeTPMType2MappabilityListMap){
		
		try {
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
						
			FileWriter mappabilityFileWriter = null;
			BufferedWriter mappabilityBufferedWriter = null;
			
			FileWriter gcFileWriter = null;
			BufferedWriter gcBufferedWriter = null;
			
			String intervalPoolFileName = null;
			
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
			
//			float accumulatedMappability = 0;
//			float avgMappability = 0;	
//			int numberoIntervalsWithMappabilityGreaterThanOne =0;
//			int numberoIntervalsWithMappabilityLessThanZero =0;
//			int numberoIntervalsWithMappabilityLessThanPointFive =0;
			
			//*****************************//
			//******** Mappability ********//
			//*****************************//

				
			//*****************************//
			//************ GC *************//
			//*****************************//
			IntervalTree gcIntervalTree = null;
			
			float gc = 0;
			
//			float accumulatedGC = 0;			
//			float avgGC = 0;
//			int numberoIntervalsWithGCGreaterThanOne =0;
//			int numberoIntervalsWithGCLessThanZero =0;
//			int numberoIntervalsWithGCLessThanPointFive =0;
			//*****************************//
			//************ GC *************//
			//*****************************//

//			int numberofIntervals = 0;
			
			int computedKey = 0;
			TFloatList GCArrayList = null;
			TFloatList MappabilityArrayList = null;
			
			for(DataDrivenExperimentTPMType TPMType: tpmTypes) {
				
				if (TPMType.isTOP20() || TPMType.isTOPUnknown()){
					
					computedKey = cellLineType.getDataDrivenExperimentCellLineType()*100 + geneType.getDataDrivenExperimentGeneType()*10 + TPMType.getDataDrivenExperimentTopPercentageType(); 
					
					GCArrayList =new TFloatArrayList();
					MappabilityArrayList =new TFloatArrayList();
									
					intervalPoolFileName = ddeIntervalPoolFolder + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + TPMType.convertEnumtoString()  + "_" +  Commons.INTERVAL_POOL + ".txt";
					
					fileReader = new FileReader(intervalPoolFileName);
					bufferedReader = new BufferedReader(fileReader);
										
					mappabilityFileWriter = new FileWriter(ddeFolder + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + TPMType.convertEnumtoString()  +  "_Mappabilities.txt");
					mappabilityBufferedWriter = new BufferedWriter(mappabilityFileWriter);
						
					gcFileWriter = new FileWriter(ddeFolder + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + TPMType.convertEnumtoString()  + "_GC.txt");
					gcBufferedWriter = new BufferedWriter(gcFileWriter);

					//Write header lines
					mappabilityBufferedWriter.write(cellLineType.convertEnumtoString() + " " + geneType.convertEnumtoString() + " " + TPMType.convertEnumtoString() + " " + "Mappability" + System.getProperty("line.separator"));
					gcBufferedWriter.write(cellLineType.convertEnumtoString() + " " + geneType.convertEnumtoString() + " " + TPMType.convertEnumtoString() + " " + "GC" + System.getProperty("line.separator"));

									
					//Initialization before entering while loop
//					numberofIntervals = 0;
					
//					accumulatedMappability = 0;
//					avgMappability = 0;
//					numberoIntervalsWithMappabilityGreaterThanOne =0;
//					numberoIntervalsWithMappabilityLessThanZero =0;
//					numberoIntervalsWithMappabilityLessThanPointFive =0;
					
//					accumulatedGC = 0;
//					avgGC = 0;
//					numberoIntervalsWithGCGreaterThanOne =0;
//					numberoIntervalsWithGCLessThanZero =0;
//					numberoIntervalsWithGCLessThanPointFive =0;
					
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
												
//						mappabilityBufferedWriter.write(mappability + System.getProperty("line.separator"));
//						gcBufferedWriter.write(gc + System.getProperty("line.separator"));
						
						mappabilityBufferedWriter.write(mappability  + System.getProperty("line.separator"));
						gcBufferedWriter.write(gc + System.getProperty("line.separator"));

						GCArrayList.add(gc);
						MappabilityArrayList.add(mappability);

//						if (mappability > 1f){
//							numberoIntervalsWithMappabilityGreaterThanOne++;
//							mappabilityBufferedWriter.write("There is a situation: " + dataFileName + "\t" + chrName +  "\t" + low + "\t" + high +  "\t" + mappability + System.getProperty("line.separator"));
//						}else if (mappability<0f){
//							numberoIntervalsWithMappabilityLessThanZero++;
//						}
//						else if (mappability < 0.5f){
//							numberoIntervalsWithMappabilityLessThanPointFive++;
//						}
//						
//						if (gc > 1f){
//							numberoIntervalsWithGCGreaterThanOne++;
//							gcBufferedWriter.write("There is a situation: " + dataFileName + "\t" + chrName +  "\t" + low + "\t" + high +  "\t" + gc + System.getProperty("line.separator"));
//						}else if (gc<0f){
//							numberoIntervalsWithGCLessThanZero++;
//						}else if (gc < 0.5f){
//							numberoIntervalsWithGCLessThanPointFive++;
//						}
//				
//						accumulatedMappability += mappability;
//						accumulatedGC += gc;

//						numberofIntervals++;
						
					}//End of WHILE reading one data file
					
					cellLineGeneTypeTPMType2GCListMap.put(computedKey, GCArrayList);
					cellLineGeneTypeTPMType2MappabilityListMap.put(computedKey, MappabilityArrayList);
					
//					avgMappability = accumulatedMappability/numberofIntervals;
//					avgGC = accumulatedGC/numberofIntervals;
					
//					mappabilityBufferedWriter.write("AvgMappability" + "\t" + avgMappability + System.getProperty("line.separator"));
//					mappabilityBufferedWriter.write("Number of Intervals with Mappability Less Than Point Five" + "\t" + numberoIntervalsWithMappabilityLessThanPointFive + "\t" + "Ratio:" + "\t" + (numberoIntervalsWithMappabilityLessThanPointFive*1f/numberofIntervals) + System.getProperty("line.separator"));
//					mappabilityBufferedWriter.write("Number of Intervals with Mappability Greater Than One" + "\t" + numberoIntervalsWithMappabilityGreaterThanOne + System.getProperty("line.separator"));
//					mappabilityBufferedWriter.write("Number of Intervals with Mappability Less Than Zero" + "\t" + numberoIntervalsWithMappabilityLessThanZero + System.getProperty("line.separator"));

//					gcBufferedWriter.write("AvgGC" + "\t" + avgGC + System.getProperty("line.separator"));
//					gcBufferedWriter.write("Number of Intervals with GC Less Than Point Five" + "\t" + numberoIntervalsWithGCLessThanPointFive + "\t" + "Ratio:" + "\t" + (numberoIntervalsWithGCLessThanPointFive*1f/numberofIntervals)  +System.getProperty("line.separator"));
//					gcBufferedWriter.write("Number of Intervals with GC Greater Than One" + "\t" + numberoIntervalsWithGCGreaterThanOne + System.getProperty("line.separator"));
//					gcBufferedWriter.write("Number of Intervals with GC Less Than Zero" + "\t" + numberoIntervalsWithGCLessThanZero + System.getProperty("line.separator"));

					//Close
					mappabilityBufferedWriter.close();
					gcBufferedWriter.close();
					
					//Close
					bufferedReader.close();

					
				}//End of IF tpMType is TOP20 or TopUnknown
				
		
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
			
			//Put into a map
			chrName2GCIntervalTreeMap.put(chrName.getChromosomeName(),gcIntervalTree);
			/*************************************************/
			/***************GC ends***************************/
			/*************************************************/

		}//End of for each chromosome
		/*************************************************************************************************/
		/*******************Get GC and mappability data structures being filled ends**********************/
		/*************************************************************************************************/
		
		TIntObjectMap<TFloatList>  cellLineGeneTypeTPMType2GCListMap = new TIntObjectHashMap<TFloatList>();
		TIntObjectMap<TFloatList>  cellLineGeneTypeTPMType2MappabilityListMap = new TIntObjectHashMap<TFloatList>();

		for(DataDrivenExperimentCellLineType cellLineType: DataDrivenExperimentCellLineType.values()){
			
			for(DataDrivenExperimentGeneType geneType: DataDrivenExperimentGeneType.values()){
				
				//Fill tpmTypes
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

				
				readIntervalsInDDCEIntervalPoolComputeGCMappability(
						ddeFolder,
						ddeIntervalPoolFolder,
						cellLineType, 
						geneType, 
						tpmTypes,
						chrName2MapabilityChromosomePositionList,
						chrName2MapabilityShortValueList,
						chrName2GCIntervalTreeMap,
						cellLineGeneTypeTPMType2GCListMap,
						cellLineGeneTypeTPMType2MappabilityListMap);
				
			}//End of for each gene type 
			
		}//End of for each cell line
		
		//Compute maximum number of intervals will be same for GC and Mappability
		int maxNumberofIntervals = computeMaxNumberofIntervals(cellLineGeneTypeTPMType2GCListMap);
		
//		writeToFiles(cellLineGeneTypeTPMType2GCListMap,ddeFolder,Commons.GC,maxNumberofIntervals);
//		writeToFiles(cellLineGeneTypeTPMType2MappabilityListMap,ddeFolder,Commons.MAPABILITY,maxNumberofIntervals);	
		
		writeToFilesForSMPackage(cellLineGeneTypeTPMType2GCListMap,ddeFolder,Commons.GC);
		writeToFilesForSMPackage(cellLineGeneTypeTPMType2MappabilityListMap,ddeFolder,Commons.MAPABILITY);
		
	}

}
