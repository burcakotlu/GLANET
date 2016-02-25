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
import gnu.trove.list.TByteList;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;
import intervaltree.Interval;
import intervaltree.IntervalTree;
import intervaltree.IntervalTreeNode;

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
	

	// IsochoreFamilyPoolHigh must not be greater than chromSize
	// With GC and Mappability and IsochoreFamily
	// In addition to chr and givenIntervalLength
	// This method may return a null randomInterval
	// Although it seems to be eliminated by looking at the other isochoreFamilyPools if there is no interval left in the current pool.
	public static InputLineMinimal getRandomLineDependingOnIsochoreFamilyofOriginalInputLine( 
			int chromSize,
			ThreadLocalRandom threadLocalRandom, 
			int originalInputLineLength,
			IsochoreFamily originalInputLineIsochoreFamily, 
			List<Interval> gcIsochoreFamilyL1Pool,
			List<Interval> gcIsochoreFamilyL2Pool, 
			List<Interval> gcIsochoreFamilyH1Pool,
			List<Interval> gcIsochoreFamilyH2Pool, 
			List<Interval> gcIsochoreFamilyH3Pool) {

		int low;
		int high;

		int isochoreFamilyPoolIndex;
		int isochoreFamilyPoolLow = 0;
		int isochoreFamilyPoolHigh = Integer.MAX_VALUE;
		int isochoreFamilyPoolHighExclusive = 0;

		InputLineMinimal randomlyGeneratedLine = null;
		
		int numberofIntervalsInTheIsochorePool = 0;
		IsochoreFamily updatedIsochoreFamily = originalInputLineIsochoreFamily ;
		
		//7 FEB 2016
		//There might be cases where there is no interval in the isochoreIntervalPool
		//This happens especially in H3 isochorePool, since hg19 chromSizes and fasta files base length do not match
		//In that case choose from isochorePool one less e.g.: H2 instead of H3.	
		do {
			
			switch( updatedIsochoreFamily){
				case L1:
					numberofIntervalsInTheIsochorePool = gcIsochoreFamilyL1Pool.size();
					break;
		
				case L2:
					numberofIntervalsInTheIsochorePool = gcIsochoreFamilyL2Pool.size();
					break;
		
				case H1:
					numberofIntervalsInTheIsochorePool = gcIsochoreFamilyH1Pool.size();
					break;
		
				case H2:
					numberofIntervalsInTheIsochorePool = gcIsochoreFamilyH2Pool.size();
					break;
		
				case H3:
					numberofIntervalsInTheIsochorePool = gcIsochoreFamilyH3Pool.size();
					break;
			}// End of Switch
			
			//save originalInputLineIsochoreFamily
			originalInputLineIsochoreFamily = updatedIsochoreFamily;
			//since updatedIsochoreFamily is decremented by one  in advance.
			updatedIsochoreFamily = IsochoreFamily.convertInttoEnum(updatedIsochoreFamily.getIsochoreFamily()-1);
			
		}while(numberofIntervalsInTheIsochorePool==0 && updatedIsochoreFamily!=null );
		
		if(numberofIntervalsInTheIsochorePool>0){
			
			//This condition is used in order to enter while loop
			//And as long as isochoreFamilyPoolHigh is greater than chromSize it is not valid
			//So continue while loop
			//however please notice that since pools are generated from chromosomes
			//this condition will be violated just after it is entered in the loop. 
			while( isochoreFamilyPoolHigh >= chromSize){

				// Get a random isochoreFamilyInterval of 100 KB long from that isochoreFamilyPool
				switch( originalInputLineIsochoreFamily){

					case L1:
						isochoreFamilyPoolIndex = threadLocalRandom.nextInt( gcIsochoreFamilyL1Pool.size());
						isochoreFamilyPoolLow = gcIsochoreFamilyL1Pool.get( isochoreFamilyPoolIndex).getLow();
						isochoreFamilyPoolHigh = gcIsochoreFamilyL1Pool.get( isochoreFamilyPoolIndex).getHigh();
						break;
		
					case L2:
						isochoreFamilyPoolIndex = threadLocalRandom.nextInt( gcIsochoreFamilyL2Pool.size());
						isochoreFamilyPoolLow = gcIsochoreFamilyL2Pool.get( isochoreFamilyPoolIndex).getLow();
						isochoreFamilyPoolHigh = gcIsochoreFamilyL2Pool.get( isochoreFamilyPoolIndex).getHigh();
						break;
		
					case H1:
						isochoreFamilyPoolIndex = threadLocalRandom.nextInt( gcIsochoreFamilyH1Pool.size());
						isochoreFamilyPoolLow = gcIsochoreFamilyH1Pool.get( isochoreFamilyPoolIndex).getLow();
						isochoreFamilyPoolHigh = gcIsochoreFamilyH1Pool.get( isochoreFamilyPoolIndex).getHigh();
						break;
		
					case H2:
						isochoreFamilyPoolIndex = threadLocalRandom.nextInt( gcIsochoreFamilyH2Pool.size());
						isochoreFamilyPoolLow = gcIsochoreFamilyH2Pool.get( isochoreFamilyPoolIndex).getLow();
						isochoreFamilyPoolHigh = gcIsochoreFamilyH2Pool.get( isochoreFamilyPoolIndex).getHigh();
						break;
		
					case H3:
						isochoreFamilyPoolIndex = threadLocalRandom.nextInt( gcIsochoreFamilyH3Pool.size());
						isochoreFamilyPoolLow = gcIsochoreFamilyH3Pool.get( isochoreFamilyPoolIndex).getLow();
						isochoreFamilyPoolHigh = gcIsochoreFamilyH3Pool.get( isochoreFamilyPoolIndex).getHigh();
						break;

				}// End of Switch

			}// End of While

			// Case1 is handled
			// What if length of originalInputLine is greater than isochoreFamilyInterval length which is
			// (isochoreFamilyPoolHigh-isochoreFamilyPoolLow)
			// We accept interval of length greater than 100 KB
			// But we do generate random interval without GC and Mappability and IsochoreFamily in this case.

			// Case2 is handled
			// What if randomLow + length exceeds isochoreFamilyInterval's high

			// Please notice that
			// As the original interval length gets higher at most it can be 100KB
			// Randomization decreases
			// For an interval of 100KB, there is only one choice.

			// Now get a random low between isochoreFamilyPoolLow and isochoreFamilyPoolHigh
			// Please note that isochoreFamilyPoolLow and isochoreFamilyPoolHigh are both zeroBased and Inclusive
			isochoreFamilyPoolHighExclusive = isochoreFamilyPoolHigh + 1;
			low = threadLocalRandom.nextInt( isochoreFamilyPoolHighExclusive - originalInputLineLength + 1 - isochoreFamilyPoolLow) + isochoreFamilyPoolLow;
			high = low + originalInputLineLength - 1;

			randomlyGeneratedLine = new InputLineMinimal(low, high);

		}//End of IF numberofIntervalsInTheIsochorePool>0
		
		return randomlyGeneratedLine;

	}
	
	
	// Without GC and Mappability and IsochoreFamily
	// Only w.r.t. chr and givenIntervalLength
	// This method always return a not null randomInterval
	public static InputLineMinimal getRandomInterval(
			int chromSize,
			int originalInputLineLength,
			ThreadLocalRandom threadLocalRandom){
		
		InputLineMinimal randomInterval = null;
		int low;
		int high;
		
		// low must be greater than or equal to zero
		// high must be less than chromSize
		low = threadLocalRandom.nextInt(chromSize - originalInputLineLength + 1);
		high = low + originalInputLineLength - 1;

		randomInterval = new InputLineMinimal(low, high);
		return randomInterval;
	}
	

	public static List<InputLineMinimal> generateRandomData( 
			GivenInputDataType givenInputsSNPsorIntervals,
			TByteList gcByteList,
			IntervalTree gcIntervalTree,
			CalculateGC calculateGC,
			List<Interval> gcIsochoreFamilyL1Pool, 
			List<Interval> gcIsochoreFamilyL2Pool,
			List<Interval> gcIsochoreFamilyH1Pool, 
			List<Interval> gcIsochoreFamilyH2Pool,
			List<Interval> gcIsochoreFamilyH3Pool,
			TIntList mapabilityChromosomePositionList,
			TShortList mapabilityShortValueList,
			// TByteList mapabilityByteValueList,
			// IntervalTree mapabilityIntervalTree,
			int chromSize, 
			ChromosomeName chromName, 
			List<InputLineMinimal> chromosomeBasedOriginalInputLines,
			ThreadLocalRandom threadLocalRandom, 
			GenerateRandomDataMode generateRandomDataMode) {

		List<InputLineMinimal> randomlyGeneratedInputLines = null;

		InputLineMinimal originalInputLine = null;
		InputLineMinimal randomlyGeneratedLine = null;
		int originalInputLineLength;

		Float originalInputLineGC = null;
		Float randomlyGeneratedInputLineGC = null;

		IsochoreFamily originalInputLineIsochoreFamily = null;

		float originalInputLineMapability;
		float randomlyGeneratedInputLineMapability;

		float differencebetweenGCs = Float.MAX_VALUE;
		float differencebetweenMapabilities = Float.MAX_VALUE;
		
		//24 FEB 2016
		//InputLineMinimal savedBestRandomlyGeneratedLineUpToNowWRTEachGCandMappabilityDifference = null;
		//float savedDifferencebetweenGCs = Float.MAX_VALUE;
		//float savedDifferencebetweenMapabilities = Float.MAX_VALUE;;
		InputLineMinimal savedBestRandomlyGeneratedLineUpToNowWRTSumofGCandMappabilityDifference = null;
		float savedSumofDifferencebetweenGCandMapabilities = Float.MAX_VALUE;
//		float savedGCDifference = Float.MAX_VALUE;
//		float savedMappabilityDifference = Float.MAX_VALUE;
//		int achievedCount = 0;
		

		float dynamicGCThreshold;
		float dynamicMapabilityThreshold;

		int count;
//		int countForIsochoreFamily;
		int countForIntervalTreeOverlap;
		int counterThreshold;

		GCIsochoreIntervalTreeFindAllOverlapsResult result = null;

		//List<GCIsochoreIntervalTreeHitNode> hits = null;

		// float oldWayCalculatedGCContent = Float.MIN_VALUE;
		// float newWayCalculatedGCContent = Float.MIN_VALUE;
		//
		// float oldWayCalculatedMapability= Float.MIN_VALUE;
		// float newWayCalculatedMapability = Float.MIN_VALUE;

		/**************************************************************************************************/
		/***********************************WITHOUT MapabilityandGC starts*********************************/
		/**************************************************************************************************/
		if( generateRandomDataMode.isGenerateRandomDataModeWithoutMapabilityandGc()){

			randomlyGeneratedInputLines = new ArrayList<InputLineMinimal>();
			
			//28 OCT 2015 starts
			IntervalTree intervalTree = new IntervalTree();
			IntervalTreeNode intervalTreeNode = null;
			List<IntervalTreeNode> overlappedNodeList  = null;
			//28 OCT 2015 ends
			
			for( int j = 0; j < chromosomeBasedOriginalInputLines.size(); j++){

				originalInputLine = chromosomeBasedOriginalInputLines.get( j);
				originalInputLineLength = originalInputLine.getHigh() - originalInputLine.getLow() + 1;
				
				//Initialize for each interval
				countForIntervalTreeOverlap = 0;

				do{
					// low must be greater than or equal to zero
					// high must be less than chromSize
					//low = threadLocalRandom.nextInt(chromSize - originalInputLineLength + 1);
					//high = low + originalInputLineLength - 1;
					//randomlyGeneratedLine = new InputLineMinimal(low, high);
					
					//8 FEB 2016 starts
					randomlyGeneratedLine = getRandomInterval(chromSize,originalInputLineLength,threadLocalRandom);
					//8 FEB 2016 ends
					
					//28 OCT 2015 starts
					intervalTreeNode = new IntervalTreeNode(chromName,randomlyGeneratedLine.getLow(),randomlyGeneratedLine.getHigh());
					
					overlappedNodeList = new ArrayList<IntervalTreeNode>();
					
					IntervalTree.findAllOverlappingIntervalsCheckingChrName(
							overlappedNodeList, 
							intervalTree.getRoot(),
							intervalTreeNode);
					
					countForIntervalTreeOverlap++;
					
				}while((overlappedNodeList!=null) && (overlappedNodeList.size()>0) && (countForIntervalTreeOverlap<=Commons.CUT_OFF_VALUE));
				
				
				// Insert this interval if it does not overlap with the already randomly created intervals in the interval tree.
				// Or it may overlap with the already randomly created intervals in the interval tree after Commons.CUT_OFF_VALUE as many trials.
				intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);
				randomlyGeneratedInputLines.add(randomlyGeneratedLine);
				
			}// End of for: each original input line

		}//End of IF without GC and Mappability
		/**************************************************************************************************/
		/***********************************WITHOUT MapabilityandGC ends***********************************/
		/**************************************************************************************************/

		/**************************************************************************************************/
		/***********************************WITH MapabilityandGC starts************************************/
		/**************************************************************************************************/
		else if( generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){

			randomlyGeneratedInputLines = new ArrayList<InputLineMinimal>();
			
			//28 OCT 2015 starts
			IntervalTree intervalTree = new IntervalTree();
			IntervalTreeNode intervalTreeNode = null;
			List<IntervalTreeNode> overlappedNodeList  = null;
			//28 OCT 2015 ends

			// For Each Original InputLine starts
			for( int j = 0; j < chromosomeBasedOriginalInputLines.size(); j++){

				// ORIGINAL INPUT DATA
				originalInputLine = chromosomeBasedOriginalInputLines.get(j);
				originalInputLineLength = originalInputLine.getHigh() - originalInputLine.getLow() + 1;
				
				//8 FEB 2016 starts		
				//originalGivenIntervalLength is less than or equal to Commons.INTERVAL_LENGTH_100000
				//Calculate GC and Mappability
				if(originalInputLineLength <= Commons.INTERVAL_LENGTH_100000){
					
					/**************************************************************************************************/
					/**************************GC Calculation for Original Input Line starts***************************/
					/**************************Isochore Family for Original Input Line starts**************************/
					/**************************************************************************************************/
					/* chrBasedModeGivenIntervalLength is Less Than <= 100 bp*/
					if( calculateGC.isCalculateGCUsingByteList()){
						// Using GCByteList
						originalInputLineGC = GC.calculateGCofIntervalUsingTroveList(originalInputLine, gcByteList);
						originalInputLineIsochoreFamily = GC.calculateIsochoreFamily(originalInputLineGC);

						//hits = gcIsochoreIntervalTree.findAllOverlappingGCIsochoreIntervals(gcIsochoreIntervalTree.getRoot(), originalInputLine);
						//originalInputLineIsochoreFamily = GC.calculateIsochoreFamily(hits);
					}
					
					// chrBasedModeGivenIntervalLength >100 <=1000
					// chrBasedModeGivenIntervalLength >1000 <=10000
					// chrBasedModeGivenIntervalLength >10000 <=100000
					else if (calculateGC.isCalculateGCUsingGCIntervalTree()){
						// Using GCIntervalTree
						originalInputLineGC = GC.calculateGCofIntervalUsingIntervalTree(originalInputLine, gcIntervalTree,calculateGC);
						originalInputLineIsochoreFamily = GC.calculateIsochoreFamily(originalInputLineGC);
					} 
					
					// Unreachable code
					// chrBasedModeGivenIntervalLength >100000
					else if (calculateGC.isCalculateGCUsingGCIsochoreIntervalTree()){
						result = GC.calculateGCofIntervalUsingIsochoreIntervalTree(originalInputLine,gcIntervalTree);
						originalInputLineGC = result.getGc();
						originalInputLineIsochoreFamily = result.getIsochoreFamily();	
					}
					/**************************************************************************************************/
					/**************************GC Calculation for Original Input Line ends*****************************/
					/**************************Isochore Family for Original Input Line ends****************************/
					/**************************************************************************************************/

					/**************************************************************************************************/
					/**********************MAPABILITY Calculation for Original Input Line starts***********************/
					/**************************************************************************************************/
					// Mapability New Way
					// Using MapabilitShortList
					originalInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(originalInputLine, mapabilityChromosomePositionList, mapabilityShortValueList);
					/**************************************************************************************************/
					/**********************MAPABILITY Calculation for Original Input Line ends*************************/
					/**************************************************************************************************/
										
				
					/**************************************************************************************************/
					/**********************Initialization for each original interval starts****************************/
					/**************************************************************************************************/
					count = 0;
					savedBestRandomlyGeneratedLineUpToNowWRTSumofGCandMappabilityDifference = null;
					savedSumofDifferencebetweenGCandMapabilities = Float.MAX_VALUE;
					//savedGCDifference = Float.MAX_VALUE;
					//savedMappabilityDifference = Float.MAX_VALUE;
					//achievedCount = 0;
					
					counterThreshold = Commons.NUMBER_OF_TRIAL_FIRST_LEVEL;
					dynamicGCThreshold = Commons.GC_THRESHOLD_LOWER_VALUE;
					dynamicMapabilityThreshold = Commons.MAPABILITY_THRESHOLD_LOWER_VALUE;
					/**************************************************************************************************/
					/**********************Initialization for each original interval ends******************************/
					/**************************************************************************************************/

	
					
					do{

						/********************************************************************************************************************************************************/
						/**********************Generate a random line depending on the isochore family of originalInputLine starts***********************************************/
						/********************************************************************************************************************************************************/
						
						//Initialize for each interval
						countForIntervalTreeOverlap = 0;
						do{
							
							//Initialize for each interval
							//countForIsochoreFamily = 0;
							do{
								
								// Randomly generated interval will have the same isochore family of the original interval
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
								
								//countForIsochoreFamily++;
							
							} while(randomlyGeneratedLine==null);
							
						
							intervalTreeNode = new IntervalTreeNode(chromName,randomlyGeneratedLine.getLow(),randomlyGeneratedLine.getHigh());
							overlappedNodeList = new ArrayList<IntervalTreeNode>();

							IntervalTree.findAllOverlappingIntervalsCheckingChrName(overlappedNodeList, intervalTree.getRoot(), intervalTreeNode);
							
							countForIntervalTreeOverlap++;
							
						}while((overlappedNodeList!=null) && (overlappedNodeList.size()>0)  && (countForIntervalTreeOverlap<=Commons.CUT_OFF_VALUE));	
						/********************************************************************************************************************************************************/
						/**********************Generate a random line depending on the isochore family of originalInputLine ends*************************************************/
						/********************************************************************************************************************************************************/

						//Let's think that we can not randomly generate an interval that does not have any overlap with already generated intervals.
						//What to do in this case?
						//Accept randomly generated interval as it is and add it
						//Or do not accept it and continue with one less interval, is it possible? Yes it is possible. Since in Enrichment works on the list of intervals it is given. Enrichment does not require a certain number of intervals.
						//Decision I accept it although it has overlap after Commons.CUT_OFF_VALUE many trials and add it.
						
						// Increase count since we have already randomly generated an interval
						count++;

						/**************************************************************************************************/
						/************************MAPABILITY Calculation for Randomly Generated Line starts*****************/
						/**************************************************************************************************/
						// Using MapabilityShortList
						randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(
								randomlyGeneratedLine, 
								mapabilityChromosomePositionList, 
								mapabilityShortValueList);

						differencebetweenMapabilities = Math.abs(randomlyGeneratedInputLineMapability - originalInputLineMapability);
						/**************************************************************************************************/
						/************************MAPABILITY Calculation for Randomly Generated Line ends*******************/
						/**************************************************************************************************/
						
				
						/**************************************************************************************************/
						/**************************GC Calculation for Randomly Generated Line starts***********************/
						/**************************************************************************************************/
						if( calculateGC.isCalculateGCUsingByteList()){
							// GByteList Way
							randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingTroveList(randomlyGeneratedLine,gcByteList);

						}else if(calculateGC.isCalculateGCUsingGCIntervalTree()){
							randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingIntervalTree(randomlyGeneratedLine, gcIntervalTree, calculateGC);
							
						}else if( calculateGC.isCalculateGCUsingGCIsochoreIntervalTree()){
							// Using GCIsochoreIntervalTree

							result = GC.calculateGCofIntervalUsingIsochoreIntervalTree(randomlyGeneratedLine,gcIntervalTree);

							randomlyGeneratedInputLineGC = result.getGc();
							// randomlyGeneratedInputLineIsochoreFamily = result.getIsochoreFamily();
						}

						differencebetweenGCs = Math.abs(randomlyGeneratedInputLineGC - originalInputLineGC);
						/**************************************************************************************************/
						/**************************GC Calculation for Randomly Generated Line ends*************************/
						/**************************************************************************************************/

//						Saved w.r.t. sum of differences gave better results than each GC and Mappabiliyu difference						
//						//Saved best randomlyGeneratedLine up to now wrt to each difference starts 
//						if ((differencebetweenGCs <= savedDifferencebetweenGCs) && (differencebetweenMapabilities <= savedDifferencebetweenMapabilities)){
//							
//							
//							savedBestRandomlyGeneratedLineUpToNowWRTEachGCandMappabilityDifference = randomlyGeneratedLine;
//							savedDifferencebetweenGCs = differencebetweenGCs;
//							savedDifferencebetweenMapabilities = differencebetweenMapabilities;
//							
//						}
//						//Saved best randomlyGeneratedLine up to now wrt to each difference ends
						
						//Saved best randomlyGeneratedLine up to now wrt to sum of differences starts 
						if ((differencebetweenGCs+differencebetweenMapabilities) < savedSumofDifferencebetweenGCandMapabilities){
							
							savedBestRandomlyGeneratedLineUpToNowWRTSumofGCandMappabilityDifference = randomlyGeneratedLine;
							savedSumofDifferencebetweenGCandMapabilities = differencebetweenGCs + differencebetweenMapabilities;	
							//savedGCDifference = differencebetweenGCs;
							//savedMappabilityDifference = differencebetweenMapabilities;
							//achievedCount = count;
						}
						//Saved best randomlyGeneratedLine up to now wrt to sum of differences ends
						
						
					
						// Update dynamicMapabilityThreshold
						// Update dynamicGCThreshold
						if( count > counterThreshold){

							// Increase dynamicMapabilityThreshold starts
							// as number of trials increases, increase dynamicMapabilityThreshold more
							if( differencebetweenMapabilities > dynamicMapabilityThreshold){
								if( !( dynamicMapabilityThreshold >= Commons.MAPABILITY_THRESHOLD_UPPER_VALUE)){

									if( count > Commons.NUMBER_OF_TRIAL_FOURTH_LEVEL){
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_020;
									}else if( count > Commons.NUMBER_OF_TRIAL_THIRD_LEVEL){
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_015;
									}else if( count > Commons.NUMBER_OF_TRIAL_SECOND_LEVEL){
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_010;
									}else{
										dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_005;
									}

								}
							}
							// Increase dynamicMapabilityThreshold ends
							
							// Increase dynamicGCThreshold starts
							// as number of trials increases, increase dynamicGCThreshold more
							if( differencebetweenGCs > dynamicGCThreshold){
								if( !( dynamicGCThreshold >= Commons.GC_THRESHOLD_UPPER_VALUE)){

									if( count > Commons.NUMBER_OF_TRIAL_FOURTH_LEVEL){
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_020;
									}else if( count > Commons.NUMBER_OF_TRIAL_THIRD_LEVEL){
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_015;
									}else if( count > Commons.NUMBER_OF_TRIAL_SECOND_LEVEL){
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_010;
									}else{
										dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_005;
									}
								}
							}
							// Increase dynamicGCThreshold ends

							// Once count has reached to counterThreshold, increase counterThreshold by
							// Commons.NUMBER_OF_TRIAL_FIRST_LEVEL each time
							counterThreshold = counterThreshold + Commons.NUMBER_OF_TRIAL_FIRST_LEVEL;
						}// End of IF
						
				

						
					}while( (differencebetweenGCs > dynamicGCThreshold || differencebetweenMapabilities > dynamicMapabilityThreshold) && (count<= Commons.CUT_OFF_VALUE));
					
					
					//We have tried Commons.CUT_OFF_VALUE many times and still we didn't achieve GC and Mappability  as much as we want.
					//In this case we add the saved best randomlyGeneratedLine up to now.
					if ((count>Commons.CUT_OFF_VALUE) && (differencebetweenGCs > dynamicGCThreshold || differencebetweenMapabilities > dynamicMapabilityThreshold)){
						
						randomlyGeneratedLine = savedBestRandomlyGeneratedLineUpToNowWRTSumofGCandMappabilityDifference;
						
						randomlyGeneratedInputLines.add(randomlyGeneratedLine);
						
						//update intervalTreeNode = new IntervalTreeNode(chromName,randomlyGeneratedLine.getLow(),randomlyGeneratedLine.getHigh());
						intervalTreeNode = new IntervalTreeNode(chromName,randomlyGeneratedLine.getLow(),randomlyGeneratedLine.getHigh());
						intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);
						
					}else {
						
						randomlyGeneratedInputLines.add(randomlyGeneratedLine);
						intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);
					}
					
					

				}else if (originalInputLineLength > Commons.INTERVAL_LENGTH_100000){
					
					//For these much long intervals we don't want to generate random interval w.r.t. GC and Mappability
					//Do not compute its GC
					//Do not compute its Mappability
					//Do not compute its IsochoreFamily
					
					//Just get a random interval w.r.t. its chr and its length
					//But be sure that it does not overlap with the already randomly generated intervals
					//as if we are randomly generating interval wo GC and Mappability
					
					//initialize
					count = 0;
					
					do{
						// low must be greater than or equal to zero
						// high must be less than chromSize
						//low = threadLocalRandom.nextInt(chromSize - originalInputLineLength + 1);
						//high = low + originalInputLineLength - 1;
						//randomlyGeneratedLine = new InputLineMinimal(low, high);
						
						//8 FEB 2016 starts
						randomlyGeneratedLine = getRandomInterval(chromSize,originalInputLineLength,threadLocalRandom);
						//8 FEB 2016 ends
						
						count++;
						
						//28 OCT 2015 starts
						intervalTreeNode = new IntervalTreeNode(chromName,randomlyGeneratedLine.getLow(),randomlyGeneratedLine.getHigh());
						
						overlappedNodeList = new ArrayList<IntervalTreeNode>();
						
						IntervalTree.findAllOverlappingIntervalsCheckingChrName(
								overlappedNodeList, 
								intervalTree.getRoot(),
								intervalTreeNode);
						
					}while((overlappedNodeList!=null) && (overlappedNodeList.size()>0) && (count <= Commons.CUT_OFF_VALUE));
					
					//Decision I accept it although it has overlap after Commons.CUT_OFF_VALUE many times trials and add it.
					randomlyGeneratedInputLines.add(randomlyGeneratedLine);
					intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);
				
					
				}//End of IF originalInputLineLength > Commons.INTERVAL_LENGTH_100000 so without GC and Mappability although it is selected so.
				//8 FEB 2016 ends
				
				
			}// End of FOR: each original input line
			

		}// End of IF generateRandomInterval with GC and Mapability
		/**************************************************************************************************/
		/***********************************WITH MapabilityandGC ends**************************************/
		/**************************************************************************************************/

		//debug starts
//		System.out.println("Come here");
//		logger.info("for one sample randomdDataGeneration ended.");
		//debug ends
		
		return randomlyGeneratedInputLines;
	}

	

	// // generate random data
	// public static void generateRandomData(
	// List<InputLine> randomlyGeneratedData,
	// List<InputLine> originalInputData,
	// Random myRandom,
	// Integer chromSize,
	// ChromosomeName chromName) {
	//
	// InputLine originalLine;
	// InputLine randomlyGeneratedLine;
	// int low;
	// int high;
	// int length;
	//
	// for (int i = 0; i < originalInputData.size(); i++) {
	// originalLine = originalInputData.get(i);
	// length = originalLine.getLength();
	//
	// // low must be greater than or equal to zero
	// // high must be less than chromSize
	//
	// low = myRandom.nextInt(chromSize - length + 1);
	// high = low + length - 1;
	//
	// randomlyGeneratedLine = new InputLine(chromName, low, high);
	// randomlyGeneratedData.add(randomlyGeneratedLine);
	// }
	//
	// }

}
