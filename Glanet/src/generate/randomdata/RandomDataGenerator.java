/**
 * @author Burcak Otlu
 * Jul 26, 2013
 * 11:26:12 PM
 * 2013
 *
 * 
 */
package generate.randomdata;

import intervaltree.IntervalTree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import mapabilityandgc.GC;
import mapabilityandgc.Mapability;

import org.apache.log4j.Logger;

import common.Commons;

import enrichment.InputLineMinimal;
import enumtypes.ChromosomeName;
import enumtypes.GenerateRandomDataMode;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;
//import gnu.trove.list.TShortList;

public class RandomDataGenerator {
	
	final static Logger logger = Logger.getLogger(RandomDataGenerator.class);


	public static List<InputLineMinimal> generateRandomData(
			//TByteList gcByteList, 
			IntervalTree gcIntervalTree,
			TIntList mapabilityChromosomePositionList,
			TShortList mapabilityShortValueList,
			//TByteList mapabilityByteValueList,
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
		int length;

		float originalInputLineGC;
		float randomlyGeneratedInputLineGC;

		float originalInputLineMapability;
		float randomlyGeneratedInputLineMapability;

		float differencebetweenGCs;
		float differencebetweenMapabilities;

		float dynamicGCThreshold;
		float dynamicMapabilityThreshold;

		int count;
		int counterThreshold;
		
		
		
//		float oldWayCalculatedGCContent = Float.MIN_VALUE;
//		float newWayCalculatedGCContent = Float.MIN_VALUE;
//		
//		float oldWayCalculatedMapability= Float.MIN_VALUE;
//		float newWayCalculatedMapability = Float.MIN_VALUE;
		

		if (generateRandomDataMode.isGenerateRandomDataModeWithoutMapabilityandGc()) {

			randomlyGeneratedInputLines = new ArrayList<InputLineMinimal>();

			for (int j = 0; j < chromosomeBasedOriginalInputLines.size(); j++) {

				originalInputLine = chromosomeBasedOriginalInputLines.get(j);
				length = originalInputLine.getHigh() - originalInputLine.getLow() + 1;

				// low must be greater than or equal to zero
				// high must be less than chromSize
				low = threadLocalRandom.nextInt(chromSize - length + 1);
				high = low + length - 1;

				randomlyGeneratedLine = new InputLineMinimal(low, high);
				randomlyGeneratedInputLines.add(randomlyGeneratedLine);
			}// End of for: each original input line

		} else if (generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()) {

			// for logging purposes
			// try {
			// fileWriter = new
			// FileWriter(Commons.RANDOM_DATA_GENERATION_LOG_FILE,true);
			// bufferedWriter = new BufferedWriter(fileWriter);

			randomlyGeneratedInputLines = new ArrayList<InputLineMinimal>();

			for (int j = 0; j < chromosomeBasedOriginalInputLines.size(); j++) {
				
				// ORIGINAL INPUT DATA
				originalInputLine = chromosomeBasedOriginalInputLines.get(j);
				
				//GC Old Way
				//GC.calculateGCofInterval(originalInputLine, gcCharArray);
				//oldWayCalculatedGCContent = originalInputLine.getGcContent();
				
				//GC New Way
				//originalInputLineGC = GC.calculateGCofIntervalUsingTroveList(originalInputLine, gcByteList);
				//newWayCalculatedGCContent = originalInputLine.getGcContent();
				
				//GC Interval Tree Way
				originalInputLineGC = GC.calculateGCofIntervalUsingIntervalTree(originalInputLine, gcIntervalTree);
				
				
//				//debug start
//				if (oldWayCalculatedGCContent!=newWayCalculatedGCContent){
//					logger.info("STOP GC here Conflict" + "\t" + chromName + "\t" + originalInputLine.getLow() + "\t" + originalInputLine.getHigh());
//				}
//				//debug end
				
				//Mapability Old Way
				//Mapability.calculateMapabilityofIntervalUsingArray(originalInputLine, mapabilityFloatArray);
				//oldWayCalculatedMapability = originalInputLine.getMapability();
				
				//Mapability New Way
				
				
				//Using MapabilitShortList
				originalInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(originalInputLine,mapabilityChromosomePositionList,mapabilityShortValueList);
				
				//Using MapabilityByteList
				//originalInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(originalInputLine,mapabilityChromosomePositionList,mapabilityByteValueList);
				
				//newWayCalculatedMapability = originalInputLine.getMapability();
				

//				//debug start
//				if (oldWayCalculatedMapability!=newWayCalculatedMapability){
//					logger.info("STOP Mapability here Conflict" + "\t" + chromName + "\t" + originalInputLine.getLow() + "\t" + originalInputLine.getHigh() + "\t" + (oldWayCalculatedMapability-newWayCalculatedMapability));
//				}
//				//debug end
				
				length = originalInputLine.getHigh() - originalInputLine.getLow() + 1;

				// RANDOM INPUT DATA
				// generate random data
				// low must be greater than or equal to zero
				// high must be less than chromSize
				low = threadLocalRandom.nextInt(chromSize - length + 1);
				high = low + length - 1;

				randomlyGeneratedLine = new InputLineMinimal(low, high);

				//GC.calculateGCofInterval(randomlyGeneratedLine, gcCharArray);
				//randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingTroveList(randomlyGeneratedLine, gcByteList);
				
				//GC Interval Tree Way
				randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingIntervalTree(randomlyGeneratedLine, gcIntervalTree);
			
				differencebetweenGCs = Math.abs(randomlyGeneratedInputLineGC - originalInputLineGC);

				//Using MapabilityShortList
				randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(randomlyGeneratedLine, mapabilityChromosomePositionList,mapabilityShortValueList);
				
				//Using MapabilityByteList
				//randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(randomlyGeneratedLine, mapabilityChromosomePositionList,mapabilityByteValueList);
				
				differencebetweenMapabilities = Math.abs(randomlyGeneratedInputLineMapability- originalInputLineMapability);

				count = 0;
				counterThreshold = Commons.NUMBER_OF_TRIAL_FIRST_LEVEL;

				dynamicGCThreshold = Commons.GC_THRESHOLD_LOWER_VALUE;
				dynamicMapabilityThreshold = Commons.MAPABILITY_THRESHOLD_LOWER_VALUE;

				while (differencebetweenGCs > dynamicGCThreshold || differencebetweenMapabilities > dynamicMapabilityThreshold) {
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

					// low must be greater than or equal to zero
					// high must be less than chromSize
					low = threadLocalRandom.nextInt(chromSize - length + 1);
					high = low + length - 1;

					randomlyGeneratedLine.setLow(low);
					randomlyGeneratedLine.setHigh(high);

					//GC.calculateGCofInterval(randomlyGeneratedLine, gcCharArray);
					//randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingTroveList(randomlyGeneratedLine, gcByteList);
					
					//GC Interval Tree Way
					randomlyGeneratedInputLineGC = GC.calculateGCofIntervalUsingIntervalTree(randomlyGeneratedLine, gcIntervalTree);
				
					
					differencebetweenGCs = Math.abs(randomlyGeneratedInputLineGC- originalInputLineGC);

					//Using MapabilityShortList
					randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(randomlyGeneratedLine, mapabilityChromosomePositionList,mapabilityShortValueList);
					
					//Using MapabilityByteList
					//randomlyGeneratedInputLineMapability = Mapability.calculateMapabilityofIntervalUsingTroveList(randomlyGeneratedLine, mapabilityChromosomePositionList,mapabilityByteValueList);
					
					differencebetweenMapabilities = Math.abs(randomlyGeneratedInputLineMapability-originalInputLineMapability);

				}// End of While
				
				
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
			
			
			// bufferedWriter.close();

			// } catch (IOException e) {
			// e.printStackTrace();
			// }

		}// End of IF

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
