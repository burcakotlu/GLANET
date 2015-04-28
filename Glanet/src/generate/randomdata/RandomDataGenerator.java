/**
 * @author Burcak Otlu
 * Jul 26, 2013
 * 11:26:12 PM
 * 2013
 *
 * 
 */
package generate.randomdata;

import gc.GC;
import gc.GCIsochoreIntervalTreeFindAllOverlapsResult;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.list.TByteList;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import intervaltree.GCIsochoreIntervalTreeHitNode;
import intervaltree.Interval;
import intervaltree.IntervalTree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import mapability.Mapability;

import org.apache.log4j.Logger;

import common.Commons;

import enrichment.InputLineMinimal;
import enumtypes.CalculateGC;
import enumtypes.ChromosomeName;
import enumtypes.GenerateRandomDataMode;
import enumtypes.GivenInputDataType;
//import gnu.trove.list.TShortList;
import enumtypes.IsochoreFamily;

public class RandomDataGenerator {
	
	final static Logger logger = Logger.getLogger(RandomDataGenerator.class);
	
	
	//Each hit also has numberofOverlappingBases starts
	public static IsochoreFamily calculateIsochoreFamily(
			List<GCIsochoreIntervalTreeHitNode> hits){
		
		
		GCIsochoreIntervalTreeHitNode gcIsochoreIntervalTreeHitNode = null;
		IsochoreFamily isochoreFamilyofInputLine = null;
		IsochoreFamily isochoreFamilyofHit = null;
		int isochoreFamilyNumberofHit;
		int numberofOverlappingBasesofHit;
		TIntIntMap isochoreFamily2NumberofOverlappingBases = null;
		
		int maximumNumberofHits = Integer.MIN_VALUE;
		int isochoreFamilyNumberWithMaximumNumberofHits = 0;
		
		if (hits.size()==0){
			logger.error("There is a situation. Number of hits is 0");
		}
		
		//There is only one hit case
		else if (hits.size()==1){
			isochoreFamilyofInputLine =  hits.get(0).getIsochoreFamily();
		}
		
		//There is more than one hit case
		else {
			
			isochoreFamily2NumberofOverlappingBases = new TIntIntHashMap();
			
			for(int i = 0; i<hits.size();i++){
				
				gcIsochoreIntervalTreeHitNode = hits.get(i);
				
				isochoreFamilyofHit = gcIsochoreIntervalTreeHitNode.getIsochoreFamily();
				isochoreFamilyNumberofHit = isochoreFamilyofHit.getIsochoreFamily();
				
				numberofOverlappingBasesofHit = gcIsochoreIntervalTreeHitNode.getNumberofOverlappingBases();
				//Accumulate
				if (!isochoreFamily2NumberofOverlappingBases.containsKey(isochoreFamilyNumberofHit)){
					isochoreFamily2NumberofOverlappingBases.put(isochoreFamilyNumberofHit, numberofOverlappingBasesofHit);
				}else{
					isochoreFamily2NumberofOverlappingBases.put(isochoreFamilyNumberofHit, isochoreFamily2NumberofOverlappingBases.get(isochoreFamilyNumberofHit) + numberofOverlappingBasesofHit);
				}
				
			}//End of for Each Hit
			
			//Choose the isochoreFamilyNumber with the maximum number of overlapping Bases
			for(TIntIntIterator itr = isochoreFamily2NumberofOverlappingBases.iterator();itr.hasNext();){
				itr.advance();
				
				if (itr.value()>maximumNumberofHits){
					maximumNumberofHits = itr.value();
					isochoreFamilyNumberWithMaximumNumberofHits = itr.key();
				}
				
			}//End of For Finding isochoreFamilyWithMaximumNumberofHits
			
			//Convert int to IsochoreFamily
			isochoreFamilyofInputLine = IsochoreFamily.convertInttoEnum(isochoreFamilyNumberWithMaximumNumberofHits);
			
		}//End of ELSE Part
		
		
		
		return isochoreFamilyofInputLine;
		
	}
	//ends

	

	
	//Shall we consider chromSize
	//so that isochoreFamilyPoolHigh will not greater than chromSize
	//Think about it
	public static InputLineMinimal getRandomLineDependingOnIsochoreFamilyofOriginalInputLine(
			int chromSize,
			ThreadLocalRandom threadLocalRandom,
			int originalInputLineLength,
			IsochoreFamily originalInputLineIsochoreFamily,
			List<Interval> gcIsochoreFamilyL1Pool,
			List<Interval> gcIsochoreFamilyL2Pool,
			List<Interval> gcIsochoreFamilyH1Pool,
			List<Interval> gcIsochoreFamilyH2Pool,
			List<Interval> gcIsochoreFamilyH3Pool){
		
		int low;
		int high;
		
		int isochoreFamilyPoolIndex;
		int isochoreFamilyPoolLow  = 0;
		int isochoreFamilyPoolHigh = Integer.MAX_VALUE;
		int isochoreFamilyPoolHighExclusive = 0;
		
		InputLineMinimal randomlyGeneratedLine = null;
		
		while(isochoreFamilyPoolHigh >= chromSize){
			
			//Get a random isochoreFamilyInterval of 100 KB long from that isochoreFamilyPool
			switch(originalInputLineIsochoreFamily){
			
				case L1: 	isochoreFamilyPoolIndex = threadLocalRandom.nextInt(gcIsochoreFamilyL1Pool.size());
							isochoreFamilyPoolLow = gcIsochoreFamilyL1Pool.get(isochoreFamilyPoolIndex).getLow();
							isochoreFamilyPoolHigh = gcIsochoreFamilyL1Pool.get(isochoreFamilyPoolIndex).getHigh();
							break;
							
				case L2: 	isochoreFamilyPoolIndex = threadLocalRandom.nextInt(gcIsochoreFamilyL2Pool.size());
							isochoreFamilyPoolLow = gcIsochoreFamilyL2Pool.get(isochoreFamilyPoolIndex).getLow();
							isochoreFamilyPoolHigh = gcIsochoreFamilyL2Pool.get(isochoreFamilyPoolIndex).getHigh();
							break;
							
				case H1:	isochoreFamilyPoolIndex = threadLocalRandom.nextInt(gcIsochoreFamilyH1Pool.size());
							isochoreFamilyPoolLow = gcIsochoreFamilyH1Pool.get(isochoreFamilyPoolIndex).getLow();
							isochoreFamilyPoolHigh = gcIsochoreFamilyH1Pool.get(isochoreFamilyPoolIndex).getHigh();
							break;
							
				case H2: 	isochoreFamilyPoolIndex = threadLocalRandom.nextInt(gcIsochoreFamilyH2Pool.size());
							isochoreFamilyPoolLow = gcIsochoreFamilyH2Pool.get(isochoreFamilyPoolIndex).getLow();
							isochoreFamilyPoolHigh = gcIsochoreFamilyH2Pool.get(isochoreFamilyPoolIndex).getHigh();
							break;
							
				case H3: 	isochoreFamilyPoolIndex = threadLocalRandom.nextInt(gcIsochoreFamilyH3Pool.size());
							isochoreFamilyPoolLow = gcIsochoreFamilyH3Pool.get(isochoreFamilyPoolIndex).getLow();
							isochoreFamilyPoolHigh = gcIsochoreFamilyH3Pool.get(isochoreFamilyPoolIndex).getHigh();
							break;
			
			}//End of Switch
		}//End of While
		
		
		//Case1 is handled
		//What if length of originalInputLine is greater than isochoreFamilyInterval length which is (isochoreFamilyPoolHigh-isochoreFamilyPoolLow)
		//We don't accept interval of length greater than 100 KB
		
		//Case2 is handled
		//What if randomLow + length  exceeds isochoreFamilyInterval's high
		
		//Please notice that
		//As the original interval length gets higher at most it can be 100KB
		//Randomization decreases
		//For an interval of 100KB, there is only one choice.
		
		//We don't accept interval of length greater than 100 KB
		if (originalInputLineLength>Commons.GC_ISOCHORE_MOVING_WINDOW_SIZE){
			logger.error("Interval of length greater than " + Commons.GC_ISOCHORE_MOVING_WINDOW_SIZE + " is not accepted.");
		}else{
			
			//Now get a random low between isochoreFamilyPoolLow and isochoreFamilyPoolHigh
			//Please note that isochoreFamilyPoolLow and isochoreFamilyPoolHigh are both zeroBased and Inclusive
			isochoreFamilyPoolHighExclusive = isochoreFamilyPoolHigh+1;
			low = threadLocalRandom.nextInt(isochoreFamilyPoolHighExclusive - originalInputLineLength + 1 - isochoreFamilyPoolLow) + isochoreFamilyPoolLow;
			high = low + originalInputLineLength - 1;
			
			randomlyGeneratedLine = new InputLineMinimal(low, high);
		}
		
			
		
		return randomlyGeneratedLine;
		
	}
	

	public static List<InputLineMinimal> generateRandomData(
			GivenInputDataType  givenInputsSNPsorIntervals,
			TByteList gcByteList, 
			IntervalTree gcIntervalTree,
			IntervalTree gcIsochoreIntervalTree,
			List<Interval> gcIsochoreFamilyL1Pool,
			List<Interval> gcIsochoreFamilyL2Pool,
			List<Interval> gcIsochoreFamilyH1Pool,
			List<Interval> gcIsochoreFamilyH2Pool,
			List<Interval> gcIsochoreFamilyH3Pool,
			TIntList mapabilityChromosomePositionList,
			TShortList mapabilityShortValueList,
			//TByteList mapabilityByteValueList,
			//IntervalTree mapabilityIntervalTree,
			int chromSize, 
			ChromosomeName chromName, 
			List<InputLineMinimal> chromosomeBasedOriginalInputLines, 
			ThreadLocalRandom threadLocalRandom, 
			GenerateRandomDataMode generateRandomDataMode) {

		List<InputLineMinimal> randomlyGeneratedInputLines = null;

		InputLineMinimal originalInputLine;
		InputLineMinimal randomlyGeneratedLine;
		int low;
		int high;
		int originalInputLineLength;
		
		CalculateGC calculateGC = null;

		Float originalInputLineGC = null;
		Float randomlyGeneratedInputLineGC = null;
		
		IsochoreFamily originalInputLineIsochoreFamily = null;
		IsochoreFamily randomlyGeneratedInputLineIsochoreFamily = null;
		
		float originalInputLineMapability;
		float randomlyGeneratedInputLineMapability;

		float differencebetweenGCs = Float.MAX_VALUE;
		float differencebetweenMapabilities = Float.MAX_VALUE;

		float dynamicGCThreshold;
		float dynamicMapabilityThreshold;

		int count;
		int counterThreshold;
		
		GCIsochoreIntervalTreeFindAllOverlapsResult result = null;
		
		List<GCIsochoreIntervalTreeHitNode> hits = null;
		
//		float oldWayCalculatedGCContent = Float.MIN_VALUE;
//		float newWayCalculatedGCContent = Float.MIN_VALUE;
//		
//		float oldWayCalculatedMapability= Float.MIN_VALUE;
//		float newWayCalculatedMapability = Float.MIN_VALUE;
		
		/**************************************************************************************************/
		/***********************************WITHOUT MapabilityandGC starts*********************************/
		/**************************************************************************************************/
		if (generateRandomDataMode.isGenerateRandomDataModeWithoutMapabilityandGc()) {

			randomlyGeneratedInputLines = new ArrayList<InputLineMinimal>();

			for (int j = 0; j < chromosomeBasedOriginalInputLines.size(); j++) {

				originalInputLine = chromosomeBasedOriginalInputLines.get(j);
				originalInputLineLength = originalInputLine.getHigh() - originalInputLine.getLow() + 1;

				// low must be greater than or equal to zero
				// high must be less than chromSize
				low = threadLocalRandom.nextInt(chromSize - originalInputLineLength + 1);
				high = low + originalInputLineLength - 1;

				randomlyGeneratedLine = new InputLineMinimal(low, high);
				randomlyGeneratedInputLines.add(randomlyGeneratedLine);
			}// End of for: each original input line

		} 
		/**************************************************************************************************/
		/***********************************WITHOUT MapabilityandGC ends***********************************/
		/**************************************************************************************************/
		
		/**************************************************************************************************/
		/***********************************WITH MapabilityandGC starts************************************/
		/**************************************************************************************************/
		else if (generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()) {

			// for logging purposes
			// try {
			// fileWriter = new
			// FileWriter(Commons.RANDOM_DATA_GENERATION_LOG_FILE,true);
			// bufferedWriter = new BufferedWriter(fileWriter);

			randomlyGeneratedInputLines = new ArrayList<InputLineMinimal>();

			//For Each Original InputLine starts
			for (int j = 0; j < chromosomeBasedOriginalInputLines.size(); j++) {
				
				// ORIGINAL INPUT DATA
				originalInputLine = chromosomeBasedOriginalInputLines.get(j);
				originalInputLineLength = originalInputLine.getHigh() - originalInputLine.getLow() + 1;

				/**************************************************************************************************/
				/************************Decide on GC Calculation per each original interval starts****************/
				/**************************************************************************************************/
				if (originalInputLineLength <= Commons.VERY_SHORT_INTERVAL_LENGTH){
					calculateGC = CalculateGC.CALCULATE_GC_USING_GC_BYTE_LIST;
				}else if (originalInputLineLength <= Commons.SHORT_INTERVAL_LENGTH){
					calculateGC = CalculateGC.CALCULATE_GC_USING_GC_INTERVAL_TREE;
				}else {
					calculateGC = CalculateGC.CALCULATE_GC_USING_GC_ISOCHORE_INTERVAL_TREE;		
				}
				/**************************************************************************************************/
				/************************Decide on GC Calculation per each original interval ends******************/
				/**************************************************************************************************/
				
				
				/**************************************************************************************************/
				/**************************GC Calculation for Original Input Line starts***************************/
				/**************************************************************************************************/
				//GC Old Way
				//GC.calculateGCofInterval(originalInputLine, gcCharArray);
				//oldWayCalculatedGCContent = originalInputLine.getGcContent();
				
				if (calculateGC.isCalculateGCUsingByteList()){
					//Using GCByteList					
					originalInputLineGC = GC.calculateGCofIntervalUsingTroveList(originalInputLine, gcByteList);
					
					hits= gcIsochoreIntervalTree.findAllOverlappingGCIsochoreIntervals(gcIsochoreIntervalTree.getRoot(), originalInputLine);
					originalInputLineIsochoreFamily = calculateIsochoreFamily(hits);
					
				}else if (calculateGC.isCalculateGCUsingGCIntervalTree()){
					//Using GCIntervalTree
					originalInputLineGC = GC.calculateGCofIntervalUsingIntervalTree(originalInputLine, gcIntervalTree,calculateGC);
					
					hits= gcIsochoreIntervalTree.findAllOverlappingGCIsochoreIntervals(gcIsochoreIntervalTree.getRoot(), originalInputLine);
					originalInputLineIsochoreFamily = calculateIsochoreFamily(hits);
					
				}else if (calculateGC.isCalculateGCUsingGCIsochoreIntervalTree()){
					//Using GCIsochoreIntervalTree
					//When we do this we must be able to store hits with their numberofOverlappingBases
					
					result = GC.calculateGCofIntervalUsingIsochoreIntervalTree(originalInputLine, gcIsochoreIntervalTree);
					
					originalInputLineGC = result.getGc();
					originalInputLineIsochoreFamily = result.getIsochoreFamily();
				}
								
				
//				//For SNP Data
//				if (givenInputsSNPsorIntervals.isGivenInputDataSNP()) {
//					//GCByteList Way
//					originalInputLineGC = GC.calculateGCofIntervalUsingTroveList(originalInputLine, gcByteList);
//					
//					//for debug starts
//					//newWayCalculatedGCContent = originalInputLine.getGcContent();
//					//for debug ends
//					
//				} 
//				//For Interval Data
//				else {
//					//GCIntervalTree Way
//					originalInputLineGC = GC.calculateGCofIntervalUsingIntervalTree(originalInputLine, gcIntervalTree);				
//				}
//				
//				//debug start
//				if (oldWayCalculatedGCContent!=newWayCalculatedGCContent){
//					logger.info("STOP GC here Conflict" + "\t" + chromName + "\t" + originalInputLine.getLow() + "\t" + originalInputLine.getHigh());
//				}
//				//debug end
				/**************************************************************************************************/
				/**************************GC Calculation for Original Input Line ends*****************************/
				/**************************************************************************************************/
				
				
				
				
				
				
				/**************************************************************************************************/
				/**********************MAPABILITY Calculation for Original Input Line starts***********************/
				/**************************************************************************************************/
				//Mapability Old Way
				//Mapability.calculateMapabilityofIntervalUsingArray(originalInputLine, mapabilityFloatArray);
				//oldWayCalculatedMapability = originalInputLine.getMapability();
				
				//Mapability New Way
				//Using MapabilitShortList
				originalInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(originalInputLine,mapabilityChromosomePositionList,mapabilityShortValueList);
				
				//For testing purposes
				//Mapability Interval Tree Way
				//originalInputLineMapability = Mapability.calculateMapabilityofIntervalUsingIntervalTree(originalInputLine, mapabilityIntervalTree);
				
				//Using MapabilityByteList
				//originalInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(originalInputLine,mapabilityChromosomePositionList,mapabilityByteValueList);
				
				//newWayCalculatedMapability = originalInputLine.getMapability();
				

//				//debug start
//				if (oldWayCalculatedMapability!=newWayCalculatedMapability){
//					logger.info("STOP Mapability here Conflict" + "\t" + chromName + "\t" + originalInputLine.getLow() + "\t" + originalInputLine.getHigh() + "\t" + (oldWayCalculatedMapability-newWayCalculatedMapability));
//				}
//				//debug end
				/**************************************************************************************************/
				/**********************MAPABILITY Calculation for Original Input Line ends*************************/
				/**************************************************************************************************/
				
				
				
				/********************************************************************************************************************************************************/
				/**********************Generate a random line depending on the isochore family of originalInputLine starts***********************************************/
				/********************************************************************************************************************************************************/
				//OLD CODE
//				// RANDOM INPUT DATA
//				// generate random data
//				// low must be greater than or equal to zero
//				// high must be less than chromSize
//				low = threadLocalRandom.nextInt(chromSize - length + 1);
//				high = low + length - 1;
//
//				randomlyGeneratedLine = new InputLineMinimal(low, high);

				
				randomlyGeneratedLine = getRandomLineDependingOnIsochoreFamilyofOriginalInputLine(
						chromSize,
						threadLocalRandom,
						originalInputLineLength,
						originalInputLineIsochoreFamily,
						gcIsochoreFamilyL1Pool,
						gcIsochoreFamilyL2Pool,
						gcIsochoreFamilyH1Pool,
						gcIsochoreFamilyH2Pool,
						gcIsochoreFamilyH3Pool);
				/********************************************************************************************************************************************************/
				/**********************Generate a random line depending on the isochore family of originalInputLine ends*************************************************/
				/********************************************************************************************************************************************************/


				
				
				/**************************************************************************************************/
				/**************************GC Calculation for Randomly Generated Line starts***********************/
				/**************************************************************************************************/
				//OLD WAY
				//GC.calculateGCofInterval(randomlyGeneratedLine, gcCharArray);
				
//				//For SNP Data
//				if (givenInputsSNPsorIntervals.isGivenInputDataSNP()){
//					//GByteList Way
//					randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingTroveList(randomlyGeneratedLine, gcByteList);
//				}
//				//For Interval Data
//				else{
//					//GCIntervalTree Way
//					randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingIntervalTree(randomlyGeneratedLine, gcIntervalTree);
//				}
				
				if (calculateGC.isCalculateGCUsingByteList()){
					//GByteList Way
					randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingTroveList(randomlyGeneratedLine, gcByteList);
					
//					hits= gcIsochoreIntervalTree.findAllOverlappingGCIsochoreIntervals(gcIsochoreIntervalTree.getRoot(), randomlyGeneratedLine);
//					randomlyGeneratedInputLineIsochoreFamily = calculateIsochoreFamily(hits);
					
				}
				else if (calculateGC.isCalculateGCUsingGCIntervalTree()){
					//GCIntervalTree Way
					randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingIntervalTree(randomlyGeneratedLine, gcIntervalTree,calculateGC);
					
//					hits= gcIsochoreIntervalTree.findAllOverlappingGCIsochoreIntervals(gcIsochoreIntervalTree.getRoot(), randomlyGeneratedLine);
//					randomlyGeneratedInputLineIsochoreFamily = calculateIsochoreFamily(hits);
					
				}else if (calculateGC.isCalculateGCUsingGCIsochoreIntervalTree()){
					//Using GCIsochoreIntervalTree
					
					result = GC.calculateGCofIntervalUsingIsochoreIntervalTree(randomlyGeneratedLine, gcIsochoreIntervalTree);
					
					randomlyGeneratedInputLineGC = result.getGc();
					randomlyGeneratedInputLineIsochoreFamily = result.getIsochoreFamily();
					
				}
				
				differencebetweenGCs = Math.abs(randomlyGeneratedInputLineGC - originalInputLineGC);
				/**************************************************************************************************/
				/**************************GC Calculation for Randomly Generated Line ends*************************/
				/**************************************************************************************************/

				
				
				
				
				
				/**************************************************************************************************/
				/************************MAPABILITY Calculation for Randomly Generated Line starts*****************/
				/**************************************************************************************************/
				//Using MapabilityShortList
				randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(randomlyGeneratedLine, mapabilityChromosomePositionList,mapabilityShortValueList);
				
				differencebetweenMapabilities = Math.abs(randomlyGeneratedInputLineMapability- originalInputLineMapability);

				//Using MapabilityByteList
				//randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(randomlyGeneratedLine, mapabilityChromosomePositionList,mapabilityByteValueList);
				
				//For testing purposes
				//Mapability Interval Tree Way
				//randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingIntervalTree(randomlyGeneratedLine, mapabilityIntervalTree);
				/**************************************************************************************************/
				/************************MAPABILITY Calculation for Randomly Generated Line ends*******************/
				/**************************************************************************************************/
				
				
				
				/**************************************************************************************************/
				/***********************WHILE LOOP For ByteList or IntervalTree starts*****************************/
				/**************************************************************************************************/
				if (calculateGC.isCalculateGCUsingByteList() || calculateGC.isCalculateGCUsingGCIntervalTree()){
					
					count = 0;
					counterThreshold = Commons.NUMBER_OF_TRIAL_FIRST_LEVEL;

					dynamicGCThreshold = Commons.GC_THRESHOLD_LOWER_VALUE;
					dynamicMapabilityThreshold = Commons.MAPABILITY_THRESHOLD_LOWER_VALUE;

					
					while (	differencebetweenGCs > dynamicGCThreshold || 
							differencebetweenMapabilities > dynamicMapabilityThreshold) {
						
						count++;

						if (count > counterThreshold) {

							//Increase dynamicGCThreshold starts
							//as number of trials increases, increase dynamicGCThreshold more
							if (differencebetweenGCs > dynamicGCThreshold) {
								if (!(dynamicGCThreshold >= Commons.GC_THRESHOLD_UPPER_VALUE)) {

									if (count > Commons.NUMBER_OF_TRIAL_FOURTH_LEVEL) {
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_020;
									} else if (count > Commons.NUMBER_OF_TRIAL_THIRD_LEVEL) {
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_015;
									} else if (count > Commons.NUMBER_OF_TRIAL_SECOND_LEVEL) {
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_010;
									} else {
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_005;
									}
								}
							}
							//Increase dynamicGCThreshold ends
					

							//Increase dynamicMapabilityThreshold starts
							//as number of trials increases, increase dynamicMapabilityThreshold more
							if (differencebetweenMapabilities > dynamicMapabilityThreshold) {
								if (!(dynamicMapabilityThreshold >= Commons.MAPABILITY_THRESHOLD_UPPER_VALUE)) {

									if (count > Commons.NUMBER_OF_TRIAL_FOURTH_LEVEL) {
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_020;
									} else if (count > Commons.NUMBER_OF_TRIAL_THIRD_LEVEL) {
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_015;
									} else if (count > Commons.NUMBER_OF_TRIAL_SECOND_LEVEL) {
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_010;
									} else {
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_005;
									}

								}
							}
							//Increase dynamicMapabilityThreshold ends

							//Once count has reached to counterThreshold, increase counterThreshold by Commons.NUMBER_OF_TRIAL_FIRST_LEVEL each time 
							counterThreshold = counterThreshold + Commons.NUMBER_OF_TRIAL_FIRST_LEVEL;
						}//End of IF

//						// low must be greater than or equal to zero
//						// high must be less than chromSize
//						low = threadLocalRandom.nextInt(chromSize - length + 1);
//						high = low + length - 1;
//
//						randomlyGeneratedLine.setLow(low);
//						randomlyGeneratedLine.setHigh(high);
						
						randomlyGeneratedLine = getRandomLineDependingOnIsochoreFamilyofOriginalInputLine(
								chromSize,
								threadLocalRandom,
								originalInputLineLength,
								originalInputLineIsochoreFamily,
								gcIsochoreFamilyL1Pool,
								gcIsochoreFamilyL2Pool,
								gcIsochoreFamilyH1Pool,
								gcIsochoreFamilyH2Pool,
								gcIsochoreFamilyH3Pool);
						

						/**************************************************************************************************/
						/**************************GC Calculation for Randomly Generated Line starts***********************/
						/**************************************************************************************************/
						//Old Way
						//GC.calculateGCofInterval(randomlyGeneratedLine, gcCharArray);
						
						if (calculateGC.isCalculateGCUsingByteList()){
							//GByteList Way
							randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingTroveList(randomlyGeneratedLine, gcByteList);
							
//							hits= gcIsochoreIntervalTree.findAllOverlappingGCIsochoreIntervals(gcIsochoreIntervalTree.getRoot(), randomlyGeneratedLine);
//							randomlyGeneratedInputLineIsochoreFamily = calculateIsochoreFamily(hits);
							
						}
						else if (calculateGC.isCalculateGCUsingGCIntervalTree()){
							//GCIntervalTree Way
							randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingIntervalTree(randomlyGeneratedLine, gcIntervalTree,calculateGC);
							
//							hits= gcIsochoreIntervalTree.findAllOverlappingGCIsochoreIntervals(gcIsochoreIntervalTree.getRoot(), randomlyGeneratedLine);
//							randomlyGeneratedInputLineIsochoreFamily = calculateIsochoreFamily(hits);
							
						}
						
						differencebetweenGCs = Math.abs(randomlyGeneratedInputLineGC - originalInputLineGC);
						/**************************************************************************************************/
						/**************************GC Calculation for Randomly Generated Line ends*************************/
						/**************************************************************************************************/

						
						
						
			
						
						/**************************************************************************************************/
						/************************MAPABILITY Calculation for Randomly Generated Line starts*****************/
						/**************************************************************************************************/
						//Using MapabilityShortList
						randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(randomlyGeneratedLine, mapabilityChromosomePositionList,mapabilityShortValueList);
						
						//For testing purposes
						//Using MapabilityByteList
						//randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(randomlyGeneratedLine, mapabilityChromosomePositionList,mapabilityByteValueList);
						
						//For testing purposes
						//Mapability Interval Tree Way
						//randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingIntervalTree(randomlyGeneratedLine, mapabilityIntervalTree);
						
						differencebetweenMapabilities = Math.abs(randomlyGeneratedInputLineMapability-originalInputLineMapability);
						/**************************************************************************************************/
						/************************MAPABILITY Calculation for Randomly Generated Line ends*******************/
						/**************************************************************************************************/

						
					}// End of While
				}//End of IF
				/**************************************************************************************************/
				/***********************WHILE LOOP For ByteList or IntervalTree ends*******************************/
				/**************************************************************************************************/


				/**************************************************************************************************/
				/***********************WHILE LOOP For IsochoreIntervalTree starts*********************************/
				/**************************************************************************************************/
				else if (calculateGC.isCalculateGCUsingGCIsochoreIntervalTree()){
					
					count = 0;
					counterThreshold = Commons.NUMBER_OF_TRIAL_FIRST_LEVEL;

					dynamicGCThreshold = Commons.GC_THRESHOLD_LOWER_VALUE;
					dynamicMapabilityThreshold = Commons.MAPABILITY_THRESHOLD_LOWER_VALUE;

					
					while (	randomlyGeneratedInputLineIsochoreFamily != originalInputLineIsochoreFamily ||
							differencebetweenGCs > dynamicGCThreshold || 
							differencebetweenMapabilities > dynamicMapabilityThreshold) {
						
						count++;

						if (count > counterThreshold) {
							
							
							//Increase dynamicGCThreshold starts
							//as number of trials increases, increase dynamicGCThreshold more
							if (differencebetweenGCs > dynamicGCThreshold) {
								if (!(dynamicGCThreshold >= Commons.GC_THRESHOLD_UPPER_VALUE)) {

									if (count > Commons.NUMBER_OF_TRIAL_FOURTH_LEVEL) {
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_020;
									} else if (count > Commons.NUMBER_OF_TRIAL_THIRD_LEVEL) {
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_015;
									} else if (count > Commons.NUMBER_OF_TRIAL_SECOND_LEVEL) {
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_010;
									} else {
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_005;
									}
								}
							}
							//Increase dynamicGCThreshold ends
							

							//Increase dynamicMapabilityThreshold starts
							//as number of trials increases, increase dynamicMapabilityThreshold more
							if (differencebetweenMapabilities > dynamicMapabilityThreshold) {
								if (!(dynamicMapabilityThreshold >= Commons.MAPABILITY_THRESHOLD_UPPER_VALUE)) {

									if (count > Commons.NUMBER_OF_TRIAL_FOURTH_LEVEL) {
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_020;
									} else if (count > Commons.NUMBER_OF_TRIAL_THIRD_LEVEL) {
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_015;
									} else if (count > Commons.NUMBER_OF_TRIAL_SECOND_LEVEL) {
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_010;
									} else {
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_005;
									}

								}
							}
							//Increase dynamicMapabilityThreshold ends

							//Once count has reached to counterThreshold, increase counterThreshold by Commons.NUMBER_OF_TRIAL_FIRST_LEVEL each time 
							counterThreshold = counterThreshold + Commons.NUMBER_OF_TRIAL_FIRST_LEVEL;
						}

						
						randomlyGeneratedLine = getRandomLineDependingOnIsochoreFamilyofOriginalInputLine(
								chromSize,
								threadLocalRandom,
								originalInputLineLength,
								originalInputLineIsochoreFamily,
								gcIsochoreFamilyL1Pool,
								gcIsochoreFamilyL2Pool,
								gcIsochoreFamilyH1Pool,
								gcIsochoreFamilyH2Pool,
								gcIsochoreFamilyH3Pool);
						
						
						/**************************************************************************************************/
						/**************************Calculate IsochoreFamily of random InputLine starts*********************/
						/**************************************************************************************************/
						//Using GCIsochoreIntervalTree
						
						result = GC.calculateGCofIntervalUsingIsochoreIntervalTree(randomlyGeneratedLine, gcIsochoreIntervalTree);
						
						randomlyGeneratedInputLineGC = result.getGc();
						randomlyGeneratedInputLineIsochoreFamily = result.getIsochoreFamily();
						
						differencebetweenGCs = Math.abs(randomlyGeneratedInputLineGC - originalInputLineGC);
						/**************************************************************************************************/
						/**************************Calculate IsochoreFamily of random InputLine ends***********************/
						/**************************************************************************************************/

							
						/**************************************************************************************************/
						/************************MAPABILITY Calculation for Randomly Generated Line starts*****************/
						/**************************************************************************************************/
						//Using MapabilityShortList
						randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(randomlyGeneratedLine, mapabilityChromosomePositionList,mapabilityShortValueList);
						
						differencebetweenMapabilities = Math.abs(randomlyGeneratedInputLineMapability-originalInputLineMapability);
						/**************************************************************************************************/
						/************************MAPABILITY Calculation for Randomly Generated Line ends*******************/
						/**************************************************************************************************/

						
					}// End of While
				}//End of IF
				/**************************************************************************************************/
				/***********************WHILE LOOP For IsochoreIntervalTree ends***********************************/
				/**************************************************************************************************/

				
				//for debug starts
//				logger.info( 	"original low: " + originalInputLine.getLow() + "\t" + 
//								"original high: " + originalInputLine.getHigh() + "\t" + 
//								"count: "+ count + "\t" +  
//								"dbGCs: " + differencebetweenGCs + "\t" +  
//								"dbMap: " + differencebetweenMapabilities + "\t" +
//								"random low: " + randomlyGeneratedLine.getLow() + "\t" + 
//								"random high: " + randomlyGeneratedLine.getHigh() );
				//for debug ends

				//For debug purposes starts
				//Set number of trials
				//randomlyGeneratedLine.setNumberofTrials(count);
				//For debug purposes ends
				
				randomlyGeneratedInputLines.add(randomlyGeneratedLine);
				
				
				
			}// End of for: each original input line
			//For Each Original InputLine ends

			
			// bufferedWriter.close();

			// } catch (IOException e) {
			// e.printStackTrace();
			// }

		}// End of IF
		/**************************************************************************************************/
		/***********************************WITH MapabilityandGC ends**************************************/
		/**************************************************************************************************/

		return randomlyGeneratedInputLines;
	}

	// todo

//	// generate random data
//	public static void generateRandomData(
//			List<InputLine> randomlyGeneratedData, 
//			List<InputLine> originalInputData, 
//			Random myRandom, 
//			Integer chromSize, 
//			ChromosomeName chromName) {
//
//		InputLine originalLine;
//		InputLine randomlyGeneratedLine;
//		int low;
//		int high;
//		int length;
//
//		for (int i = 0; i < originalInputData.size(); i++) {
//			originalLine = originalInputData.get(i);
//			length = originalLine.getLength();
//
//			// low must be greater than or equal to zero
//			// high must be less than chromSize
//
//			low = myRandom.nextInt(chromSize - length + 1);
//			high = low + length - 1;
//
//			randomlyGeneratedLine = new InputLine(chromName, low, high);
//			randomlyGeneratedData.add(randomlyGeneratedLine);
//		}
//
//	}

}
