/**
 * @author Burcak Otlu
 * Jul 26, 2013
 * 11:26:12 PM
 * 2013
 *
 * 
 */
package generate.randomdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import mapabilityandgc.GC;
import mapabilityandgc.Mapability;

import org.apache.log4j.Logger;

import common.Commons;

import enrichment.InputLine;
import enumtypes.ChromosomeName;
import enumtypes.GenerateRandomDataMode;
import gnu.trove.list.TByteList;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;

public class RandomDataGenerator {
	
	final static Logger logger = Logger.getLogger(RandomDataGenerator.class);


	public static List<InputLine> generateRandomData(
			TByteList gcByteList, 
			TIntList mapabilityChromosomePositionList,
			TShortList mapabilityShortValueList,
			int chromSize, 
			ChromosomeName chromName, 
			List<InputLine> chromosomeBasedOriginalInputLines, 
			ThreadLocalRandom threadLocalRandom, 
			GenerateRandomDataMode generateRandomDataMode) {

		List<InputLine> randomlyGeneratedInputLines = null;

		InputLine originalInputLine;
		InputLine randomlyGeneratedLine;
		int low;
		int high;
		int length;

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

			randomlyGeneratedInputLines = new ArrayList<InputLine>();

			for (int j = 0; j < chromosomeBasedOriginalInputLines.size(); j++) {

				originalInputLine = chromosomeBasedOriginalInputLines.get(j);
				length = originalInputLine.getLength();

				// low must be greater than or equal to zero
				// high must be less than chromSize
				low = threadLocalRandom.nextInt(chromSize - length + 1);
				high = low + length - 1;

				randomlyGeneratedLine = new InputLine(chromName, low, high);
				randomlyGeneratedInputLines.add(randomlyGeneratedLine);
			}// End of for: each original input line

		} else if (generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()) {

			// for logging purposes
			// try {
			// fileWriter = new
			// FileWriter(Commons.RANDOM_DATA_GENERATION_LOG_FILE,true);
			// bufferedWriter = new BufferedWriter(fileWriter);

			randomlyGeneratedInputLines = new ArrayList<InputLine>();

			for (int j = 0; j < chromosomeBasedOriginalInputLines.size(); j++) {
				
				// ORIGINAL INPUT DATA
				originalInputLine = chromosomeBasedOriginalInputLines.get(j);
				
				//GC Old Way
				//GC.calculateGCofInterval(originalInputLine, gcCharArray);
				//oldWayCalculatedGCContent = originalInputLine.getGcContent();
				
				//GC New Way
				GC.calculateGCofIntervalUsingTroveList(originalInputLine, gcByteList);
				//newWayCalculatedGCContent = originalInputLine.getGcContent();
				
//				//debug start
//				if (oldWayCalculatedGCContent!=newWayCalculatedGCContent){
//					logger.info("STOP GC here Conflict" + "\t" + chromName + "\t" + originalInputLine.getLow() + "\t" + originalInputLine.getHigh());
//				}
//				//debug end
				
				//Mapability Old Way
				//Mapability.calculateMapabilityofIntervalUsingArray(originalInputLine, mapabilityFloatArray);
				//oldWayCalculatedMapability = originalInputLine.getMapability();
				
				//Mapability New Way
				Mapability.calculateMapabilityofIntervalUsingTroveList(originalInputLine,mapabilityChromosomePositionList,mapabilityShortValueList);
				//newWayCalculatedMapability = originalInputLine.getMapability();
				

//				//debug start
//				if (oldWayCalculatedMapability!=newWayCalculatedMapability){
//					logger.info("STOP Mapability here Conflict" + "\t" + chromName + "\t" + originalInputLine.getLow() + "\t" + originalInputLine.getHigh() + "\t" + (oldWayCalculatedMapability-newWayCalculatedMapability));
//				}
//				//debug end
				
				length = originalInputLine.getLength();

				// RANDOM INPUT DATA
				// generate random data
				// low must be greater than or equal to zero
				// high must be less than chromSize
				low = threadLocalRandom.nextInt(chromSize - length + 1);
				high = low + length - 1;

				randomlyGeneratedLine = new InputLine(chromName, low, high);

				//GC.calculateGCofInterval(randomlyGeneratedLine, gcCharArray);
				GC.calculateGCofIntervalUsingTroveList(randomlyGeneratedLine, gcByteList);
				differencebetweenGCs = GC.differenceofGCs(randomlyGeneratedLine, originalInputLine);

				Mapability.calculateMapabilityofIntervalUsingTroveList(randomlyGeneratedLine, mapabilityChromosomePositionList,mapabilityShortValueList);
				differencebetweenMapabilities = Mapability.differenceofMapabilities(randomlyGeneratedLine, originalInputLine);

				count = 0;
				counterThreshold = Commons.NUMBER_OF_TRIAL_FIRST_LEVEL;

				dynamicGCThreshold = Commons.GC_THRESHOLD_LOWER_VALUE;
				dynamicMapabilityThreshold = Commons.MAPABILITY_THRESHOLD_LOWER_VALUE;

				while (differencebetweenGCs > dynamicGCThreshold || differencebetweenMapabilities > dynamicMapabilityThreshold) {
					count++;

					if (count > counterThreshold) {

						if (differencebetweenGCs > dynamicGCThreshold) {
							if (!(dynamicGCThreshold >= Commons.GC_THRESHOLD_UPPER_VALUE)) {

								if (count > Commons.NUMBER_OF_TRIAL_FOURTH_LEVEL) {
									dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_004;
								} else if (count > Commons.NUMBER_OF_TRIAL_THIRD_LEVEL) {
									dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_003;
								} else if (count > Commons.NUMBER_OF_TRIAL_SECOND_LEVEL) {
									dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_002;
								} else {
									dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_001;
								}
							}
						}

						if (differencebetweenMapabilities > dynamicMapabilityThreshold) {
							if (!(dynamicMapabilityThreshold >= Commons.MAPABILITY_THRESHOLD_UPPER_VALUE)) {

								if (count > Commons.NUMBER_OF_TRIAL_FOURTH_LEVEL) {
									dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_004;
								} else if (count > Commons.NUMBER_OF_TRIAL_THIRD_LEVEL) {
									dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_003;
								} else if (count > Commons.NUMBER_OF_TRIAL_SECOND_LEVEL) {
									dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_002;
								} else {
									dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_001;
								}

							}
						}

						counterThreshold = counterThreshold + Commons.NUMBER_OF_TRIAL_FIRST_LEVEL;
					}

					// low must be greater than or equal to zero
					// high must be less than chromSize
					low = threadLocalRandom.nextInt(chromSize - length + 1);
					high = low + length - 1;

					randomlyGeneratedLine = new InputLine(chromName, low, high);

					//GC.calculateGCofInterval(randomlyGeneratedLine, gcCharArray);
					GC.calculateGCofIntervalUsingTroveList(randomlyGeneratedLine, gcByteList);
					
					differencebetweenGCs = GC.differenceofGCs(randomlyGeneratedLine, originalInputLine);

					Mapability.calculateMapabilityofIntervalUsingTroveList(randomlyGeneratedLine, mapabilityChromosomePositionList,mapabilityShortValueList);
					differencebetweenMapabilities = Mapability.differenceofMapabilities(randomlyGeneratedLine, originalInputLine);

				}// End of While

				if (count > Commons.NUMBER_OF_TRIAL_FIRST_LEVEL) {
					// bufferedWriter.write("Numberof Trial" + "\t" + count
					// +"\t" + "dynamicGCThreshold"+ "\t" +
					// df.format(dynamicGCThreshold) + "\t"
					// +"dynamicMapabilityThreshold" + "\t" +
					// df.format(dynamicMapabilityThreshold) + "\t"
					// +"original input line gc" + "\t" +
					// df.format(originalInputLine.getGcContent()) + "\t" +
					// "original input line mapability" + "\t" +
					// df.format(originalInputLine.getMapability())+ "\t" +
					// "chromName" + "\t" +chromName +
					// System.getProperty("line.separator") );
				}

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

	// generate random data
	public static void generateRandomData(
			List<InputLine> randomlyGeneratedData, 
			List<InputLine> originalInputData, 
			Random myRandom, 
			Integer chromSize, 
			ChromosomeName chromName) {

		InputLine originalLine;
		InputLine randomlyGeneratedLine;
		int low;
		int high;
		int length;

		for (int i = 0; i < originalInputData.size(); i++) {
			originalLine = originalInputData.get(i);
			length = originalLine.getLength();

			// low must be greater than or equal to zero
			// high must be less than chromSize

			low = myRandom.nextInt(chromSize - length + 1);
			high = low + length - 1;

			randomlyGeneratedLine = new InputLine(chromName, low, high);
			randomlyGeneratedData.add(randomlyGeneratedLine);
		}

	}

}
