/**
 * 
 */
package datadrivenexperiment;

import intervaltree.Interval;
import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import annotation.Annotation;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * @author Burçak Otlu
 * @date Apr 16, 2015
 * @project Glanet 
 *
 */
public class ExcludeDnaseIntervalsNonExpressingGenesIntervalsFileCreation {
	
	public static void fillMap(ChromosomeName chrName,int low,int high,TIntObjectMap<List<Interval>> chrNumber2InputIntervalsListMap){
		
		
		List<Interval> inputIntervalsList = chrNumber2InputIntervalsListMap.get(chrName.getChromosomeName()); 
		
		if (inputIntervalsList == null){
			
			inputIntervalsList = new ArrayList<Interval>();
			chrNumber2InputIntervalsListMap.put(chrName.getChromosomeName(), inputIntervalsList);
		}
		
		inputIntervalsList.add(new Interval(low, high));
		
	}
	
	public static void readInputIntervalFillChromosomeBasedInputIntervalsMap(String inputFileName,TIntObjectMap<List<Interval>> chrNumber2InputIntervalsListMap){
		
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
	
	
	public static void excludeDnaseCellLineOverlapsFromInputIntervalsWriteResultedIntervals(TIntObjectMap<List<Interval>> chrNumber2InputIntervalsListMap,String outputFileName){
		
		int chrNumber;
		ChromosomeName chrName = null;
		List<Interval> intervalList = null;
		IntervalTree dnaseIntervalTree;
		
		
		for(TIntObjectIterator<List<Interval>> itr =chrNumber2InputIntervalsListMap.iterator();itr.hasNext();){
			
			itr.advance();
			
			chrNumber = itr.key();
			intervalList = itr.value();
			
			chrName = ChromosomeName.convertInttoEnum(chrNumber);;

//			dnaseIntervalTree = Annotation.createDnaseIntervalTreeWithNumbers(dataFolder, chrName);
//			searchDnaseWithNumbers(outputFolder, writeElementBasedAnnotationFoundOverlapsMode,chrName, bufferedReader, dnaseIntervalTree, dnaseCellLineNumber2KMap,overlapDefinition, cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
//			dnaseIntervalTree = null;
			
			
			
		}//End of for
		
		
	}

	
	public static void excludeDnaseIntervals(String inputFileName, String outputFileName, String dnaseCellLineNumberToBeExcluded){
		
		TIntObjectMap<List<Interval>> chrNumber2InputIntervalsListMap = new TIntObjectHashMap<List<Interval>>();
		
		readInputIntervalFillChromosomeBasedInputIntervalsMap(inputFileName,chrNumber2InputIntervalsListMap);
		
		excludeDnaseCellLineOverlapsFromInputIntervalsWriteResultedIntervals(chrNumber2InputIntervalsListMap,outputFileName);
	}

	
	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");
		
		//Input File
		//Set NonExpressingGenesIntervalsFile
		String nonExpressingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") + "NonExpressingGenesIntervals_EndInclusive.txt";
				
		
		//Output File
		//Set NonExpressingGenesIntervalsFile
		String dnaseIntervalsExcludedNonExpressingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") + "DnaseIntervalsExcludedNonExpressingGenesIntervals_EndInclusive.txt";

		
		excludeDnaseIntervals(nonExpressingGenesIntervalsFile,dnaseIntervalsExcludedNonExpressingGenesIntervalsFile,"GM12878");
	}

}
