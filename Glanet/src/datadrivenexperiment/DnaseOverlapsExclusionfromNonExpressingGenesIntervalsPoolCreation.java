/**
 * 
 */
package datadrivenexperiment;

import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import annotation.Annotation;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectShortHashMap;

/**
 * @author Burçak Otlu
 * @date Apr 16, 2015
 * @project Glanet 
 *
 */
public class DnaseOverlapsExclusionfromNonExpressingGenesIntervalsPoolCreation {
	
	public static void fillMap(
			ChromosomeName chrName,
			int low,
			int high,
			TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2InputIntervalsListMap){
		
		
		List<IntervalDataDrivenExperiment> inputIntervalsList = chrNumber2InputIntervalsListMap.get(chrName.getChromosomeName()); 
		
		if (inputIntervalsList == null){
			
			inputIntervalsList = new ArrayList<IntervalDataDrivenExperiment>();
			chrNumber2InputIntervalsListMap.put(chrName.getChromosomeName(), inputIntervalsList);
		}
		
		inputIntervalsList.add(new IntervalDataDrivenExperiment(low, high));
		
	}
	
	public static void readInputIntervalFillChromosomeBasedInputIntervalsMap(
			String inputFileName,
			TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2InputIntervalsListMap){
		
		FileReader fileReader  = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		
		ChromosomeName chrName;
		int low;
		int high;
		
		
		try {
				fileReader = FileOperations.createFileReader(inputFileName);
				bufferedReader = new BufferedReader(fileReader);
				
				//example strLine	
				//chr1	68991	69591	"OR4F5"
					
				while((strLine = bufferedReader.readLine())!=null){
					
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = (indexofFirstTab>0)?strLine.indexOf('\t',indexofFirstTab+1):-1;
					indexofThirdTab = (indexofSecondTab>0)?strLine.indexOf('\t',indexofSecondTab+1):-1;
					
					chrName = ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab));
					low = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
					
					fillMap(chrName,low,high,chrNumber2InputIntervalsListMap);
					
				}//End of While
				
				
				//Close 
				bufferedReader.close();
			
		
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	
	public static List<IntervalDataDrivenExperiment>  createIntervals(
			IntervalDataDrivenExperiment intervalToBeChanged,
			IntervalDataDrivenExperiment overlappingInterval){
		
		List<IntervalDataDrivenExperiment> createdIntervals = new ArrayList<IntervalDataDrivenExperiment>(); 
		
		/**************************************************************************************/
		//Left Interval
		IntervalDataDrivenExperiment leftInterval = null;
		
		int xLow = intervalToBeChanged.getLow();
		int yLow = overlappingInterval.getLow();
		
		
		if(Math.min(xLow,yLow) <= (Math.max(xLow, yLow)-1)){
			leftInterval = new IntervalDataDrivenExperiment(Math.min(xLow, yLow),Math.max(xLow, yLow)-1); 
		}
		/**************************************************************************************/
		
		/**************************************************************************************/
		//Right Interval
		IntervalDataDrivenExperiment rightInterval = null;
		
		int xHigh = intervalToBeChanged.getHigh();
		int yHigh = overlappingInterval.getHigh();
		
		if((Math.min(xHigh, yHigh)+1) <= (Math.max(xHigh, yHigh))){
			
			rightInterval = new IntervalDataDrivenExperiment(Math.min(xHigh, yHigh)+1,Math.max(xHigh, yHigh));
		}
		/**************************************************************************************/

		
		if (leftInterval!= null && IntervalTree.overlaps(intervalToBeChanged,leftInterval)){
			createdIntervals.add(leftInterval);
		}
	
		if (rightInterval!=null && IntervalTree.overlaps(intervalToBeChanged, rightInterval)){
			createdIntervals.add(rightInterval);
		}
		
		return createdIntervals;
		
	}
	
	public static boolean allOverlappingDnaseIntervalsExcludedRemoved(List<IntervalDataDrivenExperiment> overlappingDnaseIntervalsExcluded){
		
		boolean allRemoved = true;
		
		for(int i =0; i<overlappingDnaseIntervalsExcluded.size(); i++){
			
			if (!overlappingDnaseIntervalsExcluded.get(i).isRemoved()){
				
				allRemoved = false;
				break;
				
			}//End of IF
			
		}//End of for
		
		return allRemoved;
		
	}
	
	//After dnaseOverlaps excluded from originalInterval
	//We may have more than one intervals to be returned
	public static List<IntervalDataDrivenExperiment> excludeOverlaps(IntervalDataDrivenExperiment originalInterval, List<IntervalDataDrivenExperiment> overlappingIntervalList){
		
		//This will be filled and returned
		List<IntervalDataDrivenExperiment> overlappingDnaseIntervalsExcluded = new ArrayList<IntervalDataDrivenExperiment>();
		
		List<IntervalDataDrivenExperiment> createdIntervals = null;
		
		IntervalDataDrivenExperiment intervalToBeChanged;
		
		//Initialize to be returned overlappingDnaseIntervalsExcluded with originalInterval
		overlappingDnaseIntervalsExcluded.add(originalInterval);
		
				
				/*****************************************************************************/
				for(IntervalDataDrivenExperiment overlappingInterval: overlappingIntervalList){
					
					/*****************************************************************************/
					for(int i = 0; i<overlappingDnaseIntervalsExcluded.size() ; i++){
						
						intervalToBeChanged = overlappingDnaseIntervalsExcluded.get(i);
						
						if (!intervalToBeChanged.isRemoved()){
					
					
							if (IntervalTree.overlaps(intervalToBeChanged, overlappingInterval)) {
								
								//Set to be Removed true
								intervalToBeChanged.setRemoved(true);
								
								createdIntervals = createIntervals(intervalToBeChanged,overlappingInterval);
								
								if (createdIntervals.size()>0){
									overlappingDnaseIntervalsExcluded.addAll(createdIntervals);
								}//End of if 
								
							}//End of IF intervalToBeChanged and overlappingDnaseInterval Overlaps
							
						}//End of If intervalToBeChanged is not removed
						
					}//End of for each interval.
					/*****************************************************************************/
					
					/*****************************************************************************/
					//If there is no unremoved overlap then get out of outer for loop
					if (allOverlappingDnaseIntervalsExcludedRemoved(overlappingDnaseIntervalsExcluded)){
						
						break;
						
					}
					/*****************************************************************************/
					
					
					
				}//End of for each overlapping interval
				/*****************************************************************************/
				
				
			
		
		return overlappingDnaseIntervalsExcluded;
		
	}
	
	
	
	public static void findOverlapsExcludeOverlaps(
			IntervalTree dnaseIntervalTree,
			List<IntervalDataDrivenExperiment> originalIntervalList,
			boolean isDnaseOverlapsExclusionCompletely,
			List<IntervalDataDrivenExperiment> dnaseOverlapsExcludedIntervalList ){
		
		int overlapDefinition = 1;
		int numberofIntervalsThatHasOverlaps = 0;
		
		//For each originalInterval, there can be more than one dnaseOverlapping Intervals
		List<IntervalDataDrivenExperiment> overlappingIntervalList  = null;
		
		//For each originalInterval, there can be more than one intervals after dnaseOverlapping Intervals are excluded
		List<IntervalDataDrivenExperiment> overlappingIntervalsExcludedIntervalList = null;
		
		/*************************************************************/
		for(IntervalDataDrivenExperiment originalInterval : originalIntervalList){
			
			//Find overlaps
			//Exclude overlaps
			//After excluding overlaps, initial interval may result is numerious shorter overlaps
			
			overlappingIntervalList = dnaseIntervalTree.findAllOverlappingIntervalsForExclusion(dnaseIntervalTree.getRoot(), originalInterval, overlapDefinition);
			
			//Completely Discard NonExpressingGenesIntervals
			if (isDnaseOverlapsExclusionCompletely){
				
				//There is no overlap
				if(overlappingIntervalList.size()==0){
					dnaseOverlapsExcludedIntervalList.add(originalInterval);	
				}
				//There is overlap, so discard original interval completely
				//Do nothing	
				
			}
			
			//Partially Discard NonExpressingGenesIntervals
			else{
				
				//Should I merge the intervals in overlappingIntervalList?
				//Not so important for the time being.
				
				//There is overlap, so put  overlappingIntervalsExcludedIntervalList into dnaseOverlapsExcludedIntervalList
				if(overlappingIntervalList.size()>0){
					
					numberofIntervalsThatHasOverlaps++;
					overlappingIntervalsExcludedIntervalList = excludeOverlaps(originalInterval,overlappingIntervalList);
					
					
					//Add only intervals which is not removed!
					for(int i=0; i<overlappingIntervalsExcludedIntervalList.size(); i++){
						
						if (!overlappingIntervalsExcludedIntervalList.get(i).isRemoved()){
							dnaseOverlapsExcludedIntervalList.add(overlappingIntervalsExcludedIntervalList.get(i));
						}//End of IF
						
					}//End of for
					
				}
				//There is no overlap, so put original interval into dnaseOverlapsExcludedIntervalList
				else{
					dnaseOverlapsExcludedIntervalList.add(originalInterval);	
				}
			}
			
			
			
			
			
		}//End of for each original interval in the originalIntervalList
		/*************************************************************/
		
		System.out.println("Number of intervals:" + "\t" + originalIntervalList.size() +  "\t" + "Number of intervals that has overaps:" +  "\t" + numberofIntervalsThatHasOverlaps);
		
	}
	
	public static void excludeDnaseCellLineOverlapsFromInputIntervals(
			String dataFolder,
			TIntList dnaseCellLineNumberList,
			TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2OriginalIntervalsListMap,
			boolean isDnaseOverlapsExclusionCompletely,
			TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2DnaseOverlapsExcludedIntervalsListMap,
			String outputFileName){
		
		
		int chrNumber;
		ChromosomeName chrName = null;
		List<IntervalDataDrivenExperiment> originalIntervalList = null;
		List<IntervalDataDrivenExperiment> dnaseOverlapsExcludedIntervalList = null;
		IntervalTree dnaseIntervalTree;
		
		for(TIntObjectIterator<List<IntervalDataDrivenExperiment>> itr =chrNumber2OriginalIntervalsListMap.iterator();itr.hasNext();){
			
			itr.advance();
			
			chrNumber = itr.key();
			
			//For a certain chromosome
			originalIntervalList = itr.value();
			
			//For a certain chromosome
			dnaseOverlapsExcludedIntervalList = new ArrayList<IntervalDataDrivenExperiment>();
			
			chrName = ChromosomeName.convertInttoEnum(chrNumber);

			//Create dnaseIntervalTree only for the given dnaseCellLineNumbers in the dnaseCellLineNumberList
			dnaseIntervalTree = Annotation.createDnaseIntervalTreeWithNumbers(dataFolder, chrName,dnaseCellLineNumberList);
			
			//Find DnaseOverlaps and Exclude DnaseOverlaps from originalIntervals
			findOverlapsExcludeOverlaps(dnaseIntervalTree,originalIntervalList,isDnaseOverlapsExclusionCompletely,dnaseOverlapsExcludedIntervalList);	
			
			//Set chromosomeBased dnaseOverlapsExcludedIntervalsListMap
			chrNumber2DnaseOverlapsExcludedIntervalsListMap.put(chrNumber,dnaseOverlapsExcludedIntervalList);
			
			//Free memory
			dnaseIntervalTree = null;
			
		}//End of for each chromosome
		
		
	}

	
	public static void writeDnaseOverlapsExcludedIntervals(String outputFileName,TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2DnaseOverlapsExcludedIntervalsListMap){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		int chrNumber;
		ChromosomeName chrName;
		List<IntervalDataDrivenExperiment> intervals;
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(TIntObjectIterator<List<IntervalDataDrivenExperiment>> it =chrNumber2DnaseOverlapsExcludedIntervalsListMap.iterator();it.hasNext();){
				
				it.advance();
				
				chrNumber = it.key();
				intervals = it.value();
				
				chrName = ChromosomeName.convertInttoEnum(chrNumber);
				
				for(IntervalDataDrivenExperiment interval:intervals){
					
					if (!interval.isRemoved()){
						bufferedWriter.write(ChromosomeName.convertEnumtoString(chrName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + System.getProperty("line.separator") );
					}//End of IF interval is not removed
					
				}//End of For each interval
				
				
			}//End of For each chromosome
			
			//Close
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void excludeDnaseIntervalsWriteToOutputFile(
			String dataFolder,
			String inputFileName, 
			boolean isDnaseOverlapsExclusionCompletely,
			String outputFileName, 
			TIntList dnaseCellLineNumberList){
		
		TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2OriginalIntervalsListMap 				= new TIntObjectHashMap<List<IntervalDataDrivenExperiment>>();
		TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2DnaseOverlapsExcludedIntervalsListMap 	= new TIntObjectHashMap<List<IntervalDataDrivenExperiment>>();
		
		readInputIntervalFillChromosomeBasedInputIntervalsMap(inputFileName,chrNumber2OriginalIntervalsListMap);
		
		//todo isDnaseOverlapsExclusionCompletely
		excludeDnaseCellLineOverlapsFromInputIntervals(dataFolder,dnaseCellLineNumberList,chrNumber2OriginalIntervalsListMap,isDnaseOverlapsExclusionCompletely,chrNumber2DnaseOverlapsExcludedIntervalsListMap,outputFileName);
		
		writeDnaseOverlapsExcludedIntervals(outputFileName,chrNumber2DnaseOverlapsExcludedIntervalsListMap);
	}

	
	
	public static void fillDnaseCellLineNumberList(
			List<String> dnaseCellLineNameList, 
			TObjectShortMap<String> dnaseCellLineName2NumberMap,
			TIntList dnaseCellLineNumberList){
		
		int dnaseCellLineNumber;
		
		for(String dnaseCellLineName : dnaseCellLineNameList){
			dnaseCellLineNumber = dnaseCellLineName2NumberMap.get(dnaseCellLineName);
			dnaseCellLineNumberList.add(dnaseCellLineNumber);
		}//End of for each dnaseCellLineName in the list
		
	}
	
	public static String getDnaseOverlapsExclusionString(boolean isDnaseOverlapsExclusionCompletely){
		
		if (isDnaseOverlapsExclusionCompletely){
			return Commons.COMPLETELY_DNASE_OVERLAPS_EXCLUSION;
		}else {
			return Commons.PARTIALLY_DNASE_OVERLAPS_EXCLUSION;
		}
		
	}
	
	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");
		
		boolean isDnaseOverlapsExclusionCompletely = true;
		String dnaseOverlapsExclusionPartiallyorCompletely = getDnaseOverlapsExclusionString(isDnaseOverlapsExclusionCompletely);
		
		float tpm= 0.1f;
		String tpmString = NonExpressingGenesIntervalsPoolCreation.getTPMString(tpm);
		
		
		/********************************************************************************/
		List<String> dnaseCellLineNameList = new ArrayList<String>();
		dnaseCellLineNameList.add("GM12878");
		TIntList dnaseCellLineNumberList = new TIntArrayList();
		TObjectShortMap<String> dnaseCellLineName2NumberMap = new TObjectShortHashMap<String>();
		FileOperations.fillName2NumberMap(dnaseCellLineName2NumberMap, dataFolder, Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NAME_2_NUMBER_OUTPUT_FILENAME);
		fillDnaseCellLineNumberList(dnaseCellLineNameList,dnaseCellLineName2NumberMap,dnaseCellLineNumberList);
		/********************************************************************************/
		
		//Input File
		//Set NonExpressingGenesIntervalsFile
		//String nonExpressingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") + Commons.TPM_001 + Commons.NON_EXPRESSING_GENES + "Intervals_EndInclusive.txt";				
		//String nonExpressingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") + Commons.TPM_01 + Commons.NON_EXPRESSING_GENES + "Intervals_EndInclusive.txt";				
		String nonExpressingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") + tpmString + Commons.NON_EXPRESSING_GENES + "Intervals_EndInclusive.txt";				
		
		//Output File
		//Set DnaseOverlapsExcluded NonExpressingGenesIntervalsFile
		//String dnaseIntervalsExcludedNonExpressingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") +   Commons.TPM_001 + Commons.DNASE_OVERLAPS_EXCLUDED + "Intervals_EndInclusive.txt";
		//String dnaseIntervalsExcludedNonExpressingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") +   Commons.TPM_01 + Commons.DNASE_OVERLAPS_EXCLUDED + "Intervals_EndInclusive.txt";
		String dnaseIntervalsExcludedNonExpressingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") +   tpmString + dnaseOverlapsExclusionPartiallyorCompletely + "Intervals_EndInclusive.txt";
		
		excludeDnaseIntervalsWriteToOutputFile(dataFolder,nonExpressingGenesIntervalsFile,isDnaseOverlapsExclusionCompletely,dnaseIntervalsExcludedNonExpressingGenesIntervalsFile,dnaseCellLineNumberList);
	}

}
