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
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentDnaseOverlapExclusionType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTopPercentageType;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectFloatHashMap;
import gnu.trove.map.hash.TObjectShortHashMap;

/**
 * @author Burcak Otlu
 * @date Apr 16, 2015
 * @project Glanet 
 * 
 * Data Driven Experiment Step 2
 * 
 * In this class
 * Dnase Overlaps are excluded from ProteinCoding Genes Intervals Pool
 * Depending on the given 
 * 
 * cellLineType (GM12878, K562)
 * DataDrivenExperimentGeneType (NonExpressingProteinCodingGenes, ExpressingProteinCodingGenes)
 * TPM 	(0.1, 0.01, 0.001 for NonExpressingProteinCodingGenes)
 * 		(1,10,100 for ExpressingProteinCodingGenes)
 * 
 *
 */
public class Step2_DnaseOverlapsExcludedProteinCodingGenesIntervalPoolCreation {

	public static void fillMap( ChromosomeName chrName, int low, int high,
			TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2InputIntervalsListMap) {

		List<IntervalDataDrivenExperiment> inputIntervalsList = chrNumber2InputIntervalsListMap.get( chrName.getChromosomeName());

		if( inputIntervalsList == null){

			inputIntervalsList = new ArrayList<IntervalDataDrivenExperiment>();
			chrNumber2InputIntervalsListMap.put( chrName.getChromosomeName(), inputIntervalsList);
		}

		inputIntervalsList.add( new IntervalDataDrivenExperiment( low, high));

	}

	public static void readInputIntervalFillChromosomeBasedInputIntervalsMap( String inputFileName,
			TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2InputIntervalsListMap) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine = null;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;

		ChromosomeName chrName;
		int low;
		int high;

		try{
			fileReader = FileOperations.createFileReader( inputFileName);
			bufferedReader = new BufferedReader( fileReader);

			// example strLine
			// chr1 68991 69591 "OR4F5"

			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
				indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;

				chrName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));
				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				high = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				fillMap( chrName, low, high, chrNumber2InputIntervalsListMap);

			}// End of While

			// Close
			bufferedReader.close();

		}catch( IOException e){

			e.printStackTrace();
		}

	}

	public static List<IntervalDataDrivenExperiment> createIntervals( IntervalDataDrivenExperiment intervalToBeChanged,
			IntervalDataDrivenExperiment overlappingInterval) {

		List<IntervalDataDrivenExperiment> createdIntervals = new ArrayList<IntervalDataDrivenExperiment>();

		/**************************************************************************************/
		// Left Interval
		IntervalDataDrivenExperiment leftInterval = null;

		int xLow = intervalToBeChanged.getLow();
		int yLow = overlappingInterval.getLow();

		if( Math.min( xLow, yLow) <= ( Math.max( xLow, yLow) - 1)){
			leftInterval = new IntervalDataDrivenExperiment( Math.min( xLow, yLow), Math.max( xLow, yLow) - 1);
		}
		/**************************************************************************************/

		/**************************************************************************************/
		// Right Interval
		IntervalDataDrivenExperiment rightInterval = null;

		int xHigh = intervalToBeChanged.getHigh();
		int yHigh = overlappingInterval.getHigh();

		if( ( Math.min( xHigh, yHigh) + 1) <= ( Math.max( xHigh, yHigh))){

			rightInterval = new IntervalDataDrivenExperiment( Math.min( xHigh, yHigh) + 1, Math.max( xHigh, yHigh));
		}
		/**************************************************************************************/

		if( leftInterval != null && IntervalTree.overlaps( intervalToBeChanged, leftInterval)){
			createdIntervals.add( leftInterval);
		}

		if( rightInterval != null && IntervalTree.overlaps( intervalToBeChanged, rightInterval)){
			createdIntervals.add( rightInterval);
		}

		return createdIntervals;

	}

	public static boolean allOverlappingDnaseIntervalsExcludedRemoved(
			List<IntervalDataDrivenExperiment> overlappingDnaseIntervalsExcluded) {

		boolean allRemoved = true;

		for( int i = 0; i < overlappingDnaseIntervalsExcluded.size(); i++){

			if( !overlappingDnaseIntervalsExcluded.get( i).isRemoved()){

				allRemoved = false;
				break;

			}// End of IF

		}// End of for

		return allRemoved;

	}

	// After dnaseOverlaps excluded from originalInterval
	// We may have more than one intervals to be returned
	public static List<IntervalDataDrivenExperiment> excludeOverlaps( IntervalDataDrivenExperiment originalInterval,
			List<IntervalDataDrivenExperiment> overlappingIntervalList) {

		// This will be filled and returned
		List<IntervalDataDrivenExperiment> overlappingDnaseIntervalsExcluded = new ArrayList<IntervalDataDrivenExperiment>();

		List<IntervalDataDrivenExperiment> createdIntervals = null;

		IntervalDataDrivenExperiment intervalToBeChanged;

		// Initialize to be returned overlappingDnaseIntervalsExcluded with originalInterval
		overlappingDnaseIntervalsExcluded.add( originalInterval);

		/*****************************************************************************/
		for( IntervalDataDrivenExperiment overlappingInterval : overlappingIntervalList){

			/*****************************************************************************/
			for( int i = 0; i < overlappingDnaseIntervalsExcluded.size(); i++){

				intervalToBeChanged = overlappingDnaseIntervalsExcluded.get( i);

				if( !intervalToBeChanged.isRemoved()){

					if( IntervalTree.overlaps( intervalToBeChanged, overlappingInterval)){

						// Set to be Removed true
						intervalToBeChanged.setRemoved( true);

						createdIntervals = createIntervals( intervalToBeChanged, overlappingInterval);

						if( createdIntervals.size() > 0){
							overlappingDnaseIntervalsExcluded.addAll( createdIntervals);
						}// End of if

					}// End of IF intervalToBeChanged and overlappingDnaseInterval Overlaps

				}// End of If intervalToBeChanged is not removed

			}// End of for each interval.
			/*****************************************************************************/

			/*****************************************************************************/
			// If there is no unremoved overlap then get out of outer for loop
			if( allOverlappingDnaseIntervalsExcludedRemoved( overlappingDnaseIntervalsExcluded)){

				break;

			}
			/*****************************************************************************/

		}// End of for each overlapping interval
		/*****************************************************************************/

		return overlappingDnaseIntervalsExcluded;

	}

	
	public static void findOverlapsExcludeOverlaps(
			IntervalTree dnaseIntervalTree,
			List<IntervalDataDrivenExperiment> originalIntervalList,
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType,
			List<IntervalDataDrivenExperiment> dnaseOverlapsExcludedIntervalList) {

		int overlapDefinition = 1;

		// For each originalInterval, there can be more than one dnaseOverlapping Intervals
		List<IntervalDataDrivenExperiment> overlappingIntervalList = null;

		// For each originalInterval, there can be more than one intervals after dnaseOverlapping Intervals are excluded
		List<IntervalDataDrivenExperiment> overlappingIntervalsExcludedIntervalList = null;

		int savedIndexWithLongestInterval = -1;
		int theLongestIntervalLength = Integer.MIN_VALUE;

		/*************************************************************/
		for( IntervalDataDrivenExperiment originalInterval : originalIntervalList){

			// Find overlaps
			// Exclude overlaps
			// After excluding overlaps, initial interval may result is numerious shorter overlaps
			overlappingIntervalList = dnaseIntervalTree.findAllOverlappingIntervalsForExclusion(dnaseIntervalTree.getRoot(), originalInterval, overlapDefinition);

			// CASE1
			// Completely Discard NonExpressingGenesIntervals If There Is Dnase Overlaps
			if( dnaseOverlapExclusionType.isCompletelyDiscardInterval()){

				// There is no overlap
				if( overlappingIntervalList.size() == 0){
					dnaseOverlapsExcludedIntervalList.add( originalInterval);
				}
				// There is overlap, so discard original interval completely
				// Do nothing

			}// END OF CASE1

			// CASE2
			// Partially Discard NonExpressingGenesIntervals Remain All The Intervals If There Is Dnase Overlaps
//			else if( dnaseOverlapExclusionType.isPartiallyDiscardIntervalTakeAllTheRemainingIntervals()){
//
//				// Should I merge the intervals in overlappingIntervalList?
//				// Not so important for the time being.
//
//				// There is overlap, so put overlappingIntervalsExcludedIntervalList into
//				// dnaseOverlapsExcludedIntervalList
//				if( overlappingIntervalList.size() > 0){
//
//					overlappingIntervalsExcludedIntervalList = excludeOverlaps( originalInterval,
//							overlappingIntervalList);
//
//					// Add only intervals which is not removed!
//					for( int i = 0; i < overlappingIntervalsExcludedIntervalList.size(); i++){
//
//						if( !overlappingIntervalsExcludedIntervalList.get( i).isRemoved()){
//							dnaseOverlapsExcludedIntervalList.add( overlappingIntervalsExcludedIntervalList.get( i));
//						}// End of IF
//
//					}// End of for
//
//				}
//				// There is no overlap, so put original interval into dnaseOverlapsExcludedIntervalList
//				else{
//					dnaseOverlapsExcludedIntervalList.add( originalInterval);
//				}
//			}// END OF CASE2

			// CASE3
			// Partially Discard NonExpressingGenesIntervals Remain Only The Longest Interval If There Is Dnase Overlaps
			else if( dnaseOverlapExclusionType.isPartiallyDiscardIntervalTakeTheLongestRemainingInterval()){

				// There is overlap, so put overlappingIntervalsExcludedIntervalList into
				// dnaseOverlapsExcludedIntervalList
				if( overlappingIntervalList.size() > 0){

					overlappingIntervalsExcludedIntervalList = excludeOverlaps( originalInterval,
							overlappingIntervalList);

					// Initialize savedIndexWithLongestInterval to -1 before entering for loop
					// Initialize theLongestIntervalLength to Integer.MIN_VALUE before entering for loop
					savedIndexWithLongestInterval = -1;
					theLongestIntervalLength = Integer.MIN_VALUE;

					// Add only interval which is not removed and the longest interval!
					for( int i = 0; i < overlappingIntervalsExcludedIntervalList.size(); i++){

						if( !overlappingIntervalsExcludedIntervalList.get( i).isRemoved()){
							
							if( (overlappingIntervalsExcludedIntervalList.get(i).getHigh() - overlappingIntervalsExcludedIntervalList.get(i).getLow()) > theLongestIntervalLength){
								savedIndexWithLongestInterval = i;
								theLongestIntervalLength = overlappingIntervalsExcludedIntervalList.get(i).getHigh() - overlappingIntervalsExcludedIntervalList.get(i).getLow();
							}// End of IF
							
						}// End of IF

					}// End of FOR

					if( savedIndexWithLongestInterval != -1){
						dnaseOverlapsExcludedIntervalList.add( overlappingIntervalsExcludedIntervalList.get( savedIndexWithLongestInterval));
					}// End of IF

				}
				// There is no overlap, so put original interval into dnaseOverlapsExcludedIntervalList
				else{
					dnaseOverlapsExcludedIntervalList.add( originalInterval);
				}

			}// END OF CASE3
			
			// CASE4 starts
			else if (dnaseOverlapExclusionType.isNoDiscard()){
				dnaseOverlapsExcludedIntervalList.add(originalInterval);
			}
			// CASE4 ends

		}// End of for each original interval in the originalIntervalList
		/*************************************************************/

	}

	public static void excludeDnaseCellLineOverlapsFromInputIntervals( 
			String dataFolder,
			TIntList dnaseCellLineNumberList,
			TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2OriginalIntervalsListMap,
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType,
			TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2DnaseOverlapsExcludedIntervalsListMap,
			String outputFileName) {

		int chrNumber;
		ChromosomeName chrName = null;
		List<IntervalDataDrivenExperiment> originalIntervalList = null;
		List<IntervalDataDrivenExperiment> dnaseOverlapsExcludedIntervalList = null;
		IntervalTree dnaseIntervalTree;

		for( TIntObjectIterator<List<IntervalDataDrivenExperiment>> itr = chrNumber2OriginalIntervalsListMap.iterator(); itr.hasNext();){

			itr.advance();

			chrNumber = itr.key();

			// For a certain chromosome
			originalIntervalList = itr.value();

			// For a certain chromosome
			dnaseOverlapsExcludedIntervalList = new ArrayList<IntervalDataDrivenExperiment>();

			chrName = ChromosomeName.convertInttoEnum( chrNumber);

			// Create dnaseIntervalTree only for the given dnaseCellLineNumbers in the dnaseCellLineNumberList
			dnaseIntervalTree = Annotation.createDnaseIntervalTreeWithNumbers(
					dataFolder, 
					chrName,
					dnaseCellLineNumberList);

			// Find DnaseOverlaps and Exclude DnaseOverlaps from originalIntervals depending on dnaseOverlapExclusionType
			findOverlapsExcludeOverlaps(
					dnaseIntervalTree, 
					originalIntervalList, 
					dnaseOverlapExclusionType,
					dnaseOverlapsExcludedIntervalList);

			// Set chromosomeBased dnaseOverlapsExcludedIntervalsListMap
			chrNumber2DnaseOverlapsExcludedIntervalsListMap.put(chrNumber, dnaseOverlapsExcludedIntervalList);

			// Free memory
			dnaseIntervalTree = null;

		}// End of for each chromosome

	}

	public static void writeDnaseOverlapsExcludedIntervals( 
			String outputFileName,
			TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2DnaseOverlapsExcludedIntervalsListMap,
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType,
			BufferedWriter dnaseOverlapExcludedIntervalPoolStatisticsBufferedWriter) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		int chrNumber;
		ChromosomeName chrName;
		List<IntervalDataDrivenExperiment> intervals;

		int numberofIntervals = 0;
		float avgIntervalLength = 0f;

		try{
			fileWriter = FileOperations.createFileWriter( outputFileName);
			bufferedWriter = new BufferedWriter( fileWriter);
			
			dnaseOverlapExcludedIntervalPoolStatisticsBufferedWriter.write("NumberofIntervals" + "\t" + "AvgIntervalLength" + System.getProperty("line.separator"));
			

			for( TIntObjectIterator<List<IntervalDataDrivenExperiment>> it = chrNumber2DnaseOverlapsExcludedIntervalsListMap.iterator(); it.hasNext();){

				it.advance();

				chrNumber = it.key();
				intervals = it.value();

				chrName = ChromosomeName.convertInttoEnum( chrNumber);

				for( IntervalDataDrivenExperiment interval : intervals){

					if( !interval.isRemoved()){
						numberofIntervals++;
						avgIntervalLength = avgIntervalLength + interval.getHigh() - interval.getLow() +1;
						bufferedWriter.write( ChromosomeName.convertEnumtoString( chrName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + System.getProperty( "line.separator"));
					}// End of IF interval is not removed

				}// End of For each interval

			}// End of For each chromosome

			System.out.println(dnaseOverlapExclusionType.convertEnumtoString() + "\t" +  "numberofIntervals after dnase overlap exclusion is: " + numberofIntervals);

			avgIntervalLength = avgIntervalLength/numberofIntervals;
			dnaseOverlapExcludedIntervalPoolStatisticsBufferedWriter.write(numberofIntervals + "\t" + avgIntervalLength + System.getProperty("line.separator"));
			
			// Close
			bufferedWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void excludeDnaseIntervalsWriteToOutputFile(
			String dataFolder, 
			String inputFileName,
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType, 
			String outputFileName, 
			TIntList dnaseCellLineNumberList,
			BufferedWriter dnaseOverlapExcludedIntervalPoolStatisticsBufferedWriter) {

		TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2OriginalIntervalsListMap = new TIntObjectHashMap<List<IntervalDataDrivenExperiment>>();
		TIntObjectMap<List<IntervalDataDrivenExperiment>> chrNumber2DnaseOverlapsExcludedIntervalsListMap = new TIntObjectHashMap<List<IntervalDataDrivenExperiment>>();

		readInputIntervalFillChromosomeBasedInputIntervalsMap( inputFileName, chrNumber2OriginalIntervalsListMap);

		// Using DnaseOverlapExclusionType
		excludeDnaseCellLineOverlapsFromInputIntervals(
				dataFolder, 
				dnaseCellLineNumberList,
				chrNumber2OriginalIntervalsListMap, 
				dnaseOverlapExclusionType,
				chrNumber2DnaseOverlapsExcludedIntervalsListMap, 
				outputFileName);

		writeDnaseOverlapsExcludedIntervals(
				outputFileName, 
				chrNumber2DnaseOverlapsExcludedIntervalsListMap,
				dnaseOverlapExclusionType,
				dnaseOverlapExcludedIntervalPoolStatisticsBufferedWriter);
	}

	public static void fillDnaseCellLineNumberList(
			List<String> dnaseCellLineNameList,
			TObjectShortMap<String> dnaseCellLineName2NumberMap, 
			TIntList dnaseCellLineNumberList) {

		int dnaseCellLineNumber;

		for( String dnaseCellLineName : dnaseCellLineNameList){
			dnaseCellLineNumber = dnaseCellLineName2NumberMap.get(dnaseCellLineName);
			dnaseCellLineNumberList.add(dnaseCellLineNumber);
		}// End of for each dnaseCellLineName in the list

	}

	/*
	 *	args[0] = 	GLANET Folder (which is the parent of Data folder)
	 * 
	 *	args[1] =	DataDrivenExperimentCellLineType 
	 * 				GM12878
	 * 				K562
	 *
	 * 	args[2] =	DataDrivenExperimentGeneType
	 * 				NonExpressingGenes
	 * 				ExpressingGenes
	 * 
	 * 	args[3] = 	TPM value (0.1, 0.01, 0.001 for nonExpressing proteinCoding Genes)
	 *				TPM value (1, 10, 100 for expressing proteinCoding Genes)
	 * 
	 * 
	 */
	public static void main( String[] args) {

		String glanetFolder	= args[0];
		
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
		String dataDrivenExperimentFolder	= glanetFolder + Commons.DDE + System.getProperty( "file.separator");
		
		DataDrivenExperimentCellLineType cellLineType = DataDrivenExperimentCellLineType.convertStringtoEnum(args[1]);
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(args[2]);
		
		// We will create the interval pool of nonExpressingGenes intervals (of 601 base long) for various TPM Values
		// Such as Top2Percentage, Top60Percentage for nonExpressing proteinCoding genes
		// Such as Top2Percentage, Top10Percentage, Top25Percentage for expressing proteinCoding genes
		
		// Old Code
		// float tpmThreshold = 0.1f;
		// float tpmThreshold = Float.parseFloat( args[3]);
		// String tpmString = DataDrivenExperimentCommon.getTPMString(tpmThreshold);
		
		
		/*************************************************************************************************/
		/*************************************************************************************************/
		/*************************************************************************************************/		
		List<String> dnaseCellLineNameList = new ArrayList<String>();
		dnaseCellLineNameList.add(cellLineType.convertEnumtoString());
		TIntList dnaseCellLineNumberList = new TIntArrayList();
		TObjectShortMap<String> dnaseCellLineName2NumberMap = new TObjectShortHashMap<String>();
		FileOperations.fillName2NumberMap(
				dnaseCellLineName2NumberMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NAME_2_NUMBER_OUTPUT_FILENAME);
		fillDnaseCellLineNumberList( dnaseCellLineNameList, dnaseCellLineName2NumberMap, dnaseCellLineNumberList);
		/*************************************************************************************************/
		/*************************************************************************************************/
		/*************************************************************************************************/		

		
		/*************************************************************************************************/
		/******************************Get the tpmValues starts*******************************************/
		/*************************************************************************************************/
		TObjectFloatMap<DataDrivenExperimentTopPercentageType> tpmValueMap = new TObjectFloatHashMap<DataDrivenExperimentTopPercentageType>();
		DataDrivenExperimentCommon.getTPMValues(glanetFolder,cellLineType,geneType,tpmValueMap);
		/*************************************************************************************************/
		/******************************Get the tpmValues ends*********************************************/
		/*************************************************************************************************/


		DataDrivenExperimentTopPercentageType percentageType = null;
		Float tpmValue = null;
		
		
		FileWriter fileWriter = null;
		BufferedWriter dnaseOverlapExcludedIntervalPoolStatisticsBufferedWriter = null;
		
		
		try {
			
			fileWriter  = FileOperations.createFileWriter(dataDrivenExperimentFolder + Commons.DDE_DNASEOVERLAPSEXCLUDED_INTERVAL_POOL+ System.getProperty( "file.separator") + Commons.DDE_DNASEOVERLAPSEXCLUDED_INTERVAL_POOL_STATISTICS_FILE, true);
					
			dnaseOverlapExcludedIntervalPoolStatisticsBufferedWriter = new BufferedWriter(fileWriter);
			
			/*************************************************************************************************/
			/******************************For each tpmValue starts*******************************************/
			/*************************************************************************************************/
			for(TObjectFloatIterator<DataDrivenExperimentTopPercentageType> itr = tpmValueMap.iterator();itr.hasNext();){
				
				itr.advance();
				
				percentageType = itr.key();
				tpmValue = itr.value();
				
				System.out.println("CellLineType is: " + cellLineType);
				System.out.println("GeneType is: " + geneType);
				System.out.println("TopPercentageType is: "  + percentageType.convertEnumtoString()  + "\t" + "TPM Value is: " + tpmValue);
				
				
				// Input Intervals Pool File
				// EndInclusive
				// Considers cellLineType
				// tpmString
				// geneType
				String intervals_pool_inputFile = dataDrivenExperimentFolder + Commons.DDE_INTERVAL_POOL + System.getProperty( "file.separator") + cellLineType.convertEnumtoString() +  "_" + geneType.convertEnumtoString() + "_" + percentageType.convertEnumtoString() + "_IntervalPool.txt";
				String dnaseIntervalsExcluded_interval_pool_outputFile = null;
				
				for(DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType: DataDrivenExperimentDnaseOverlapExclusionType.values()){
					
					if (	geneType.isNonExpressingProteinCodingGenes() ||
							(geneType.isExpressingProteinCodingGenes() && dnaseOverlapExclusionType.isNoDiscard())){
						
						dnaseOverlapExcludedIntervalPoolStatisticsBufferedWriter.write(cellLineType + "\t" + geneType.convertEnumtoString() + "\t" + percentageType.convertEnumtoString() + "\t" +  tpmValue + "\t" + dnaseOverlapExclusionType.convertEnumtoString() + System.getProperty("line.separator"));
						
						
						// Output File
						// EndInclusive
						dnaseIntervalsExcluded_interval_pool_outputFile = dataDrivenExperimentFolder + Commons.DDE_DNASEOVERLAPSEXCLUDED_INTERVAL_POOL+ System.getProperty( "file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + percentageType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_IntervalPool.txt";
			
						excludeDnaseIntervalsWriteToOutputFile(
								dataFolder, 
								intervals_pool_inputFile, 
								dnaseOverlapExclusionType,
								dnaseIntervalsExcluded_interval_pool_outputFile, 
								dnaseCellLineNumberList,
								dnaseOverlapExcludedIntervalPoolStatisticsBufferedWriter);
				
					}//End of IF
					
				}//End of for each DataDrivenExperimentDnaseOverlapExclusionType
	
				
			}//End of FOR each TPMValue
			/*************************************************************************************************/
			/******************************For each tpmValue ends*********************************************/
			/*************************************************************************************************/
			
			dnaseOverlapExcludedIntervalPoolStatisticsBufferedWriter.flush();
			
			dnaseOverlapExcludedIntervalPoolStatisticsBufferedWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
							
	}		


}
