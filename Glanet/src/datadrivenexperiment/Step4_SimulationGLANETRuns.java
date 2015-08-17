/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import common.Commons;
import enumtypes.DnaseOverlapExclusionType;
import enumtypes.GenerateRandomDataMode;
import auxiliary.FileOperations;

/**
 * @author Burcak Otlu
 * @date May 7, 2015
 * @project Glanet
 * 
 * Data Driven Experiment Step 4
 * 
 * In this class
 * 
 * GLANET Data Driven Experiments Simulations command line runs are written in a bat file.
 *
 */
public class Step4_SimulationGLANETRuns {

	public static void writeGLANETRuns( 
			BufferedWriter bufferedWriter, 
			int numberofSimulations, 
			String tpm,
			DnaseOverlapExclusionType dnaseOverlapExclusionType, 
			GenerateRandomDataMode withorWithout, 
			String args[]) throws IOException {

		String rootCommand = "java -jar \"" + args[0] + "\" -Xms4G -Xmx4G -c -g \"" + args[1] + System.getProperty( "file.separator") + "\" -i \"" + args[1] + "Data" + System.getProperty( "file.separator") + "SimulationData" + System.getProperty( "file.separator") + tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "Sim";

		for( int i = 0; i < numberofSimulations; i++){

			String command = rootCommand + i + ".txt\" " + "-f0 " + "-tf " + "-histone " + "-e " + "-ewz ";

			switch( withorWithout){

				case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:
	
					bufferedWriter.write( command + " -rdgcm -pe 10000 -dder -j " + tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "wGCM" + "Sim" + i + System.getProperty( "line.separator"));
					break;
	
				case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
	
					bufferedWriter.write( command + "-rd -pe 10000 -dder -j " + tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() +"woGCM" + "Sim" + i + System.getProperty( "line.separator"));
					break;
	
				default:
					break;

			}// End of SWITCH

		}// End of for

	}

	/**
	 * @param args
	 * args[0] = GLANET.jar location
	 * args[1] = GLANET folder (which is parent of Data folder)
	 * args[2] = numbeOfSimulations
	 * args[3] = where to save bat file
	 * 
	 * Example:
	 * 
	 * args[0]	-->	"C:\Users\Burcak\Google Drive\GLANET\GLANET.jar"
	 * args[1]	-->	"C:\Users\Burcak\Google Drive"
	 * args[2]	-->	1000
	 * args[3]	-->	"C:\Users\Burcak\Desktop"
	 */
	public static void main( String[] args) {

		// x1000
		int numberOfSimulations = Integer.parseInt(args[2]);

		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		GenerateRandomDataMode withGCandMapability = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
		GenerateRandomDataMode withoutGCandMapability = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;

		try{
			// run this created bat file

			//Can Firtina
			//fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRunsewz(1).sh");
			
			//Burcak Otlu
			
			
			//*************************************************************************************************************//
			//***************************PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS***********************//
			//*********************************WITH_MAPPABILITY_AND_GC_CONTENT*********************************************//
			//*************************************************************************************************************//
			fileWriter = FileOperations.createFileWriter( args[3] + "1SimulationGLANETRunsTakeAllRemainingIntervalsWithGCandMapability.sh");
			
			bufferedWriter = new BufferedWriter( fileWriter);
			//bufferedWriter.write( "#!/bin/bash\n");

			// x12
			// With GC and Mapability
			writeGLANETRuns(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS, 
					withGCandMapability,
					args);
			
			writeGLANETRuns(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS, 
					withGCandMapability,
					args);
			
			writeGLANETRuns(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS, 
					withGCandMapability,
					args);

			//Close bufferedWriter
			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//***************************PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS***********************//
			//*********************************WITH_MAPPABILITY_AND_GC_CONTENT*********************************************//
			//*************************************************************************************************************//

			
			
			//*************************************************************************************************************//
			//***************************PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS***********************//
			//*********************************WITHOUT_MAPPABILITY_AND_GC_CONTENT******************************************//
			//*************************************************************************************************************//
			//fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRunsewz(2).sh");
			fileWriter = FileOperations.createFileWriter( args[3] + "2SimulationGLANETRunsTakeAllRemainingIntervalsWithoutGCandMapability.sh");
			
			bufferedWriter = new BufferedWriter( fileWriter);
//			bufferedWriter.write( "#!/bin/bash\n");

			// Without GC and Mapability
			writeGLANETRuns(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS,
					withoutGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS,
					withoutGCandMapability, 
					args);
			
			
			writeGLANETRuns(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS,
					withoutGCandMapability, 
					args);

			//Close bufferedWriter
			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//***************************PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS***********************//
			//*********************************WITHOUT_MAPPABILITY_AND_GC_CONTENT******************************************//
			//*************************************************************************************************************//

			
			
			
			//*************************************************************************************************************//
			//***************************PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL********************//
			//*********************************WITH_MAPPABILITY_AND_GC_CONTENT*********************************************//
			//*************************************************************************************************************//
//			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRunsewz(3).sh");
			fileWriter = FileOperations.createFileWriter( args[3] + "3SimulationGLANETRunsTakeTheLongestIntervalWithGCandMpability.sh");
			
			bufferedWriter = new BufferedWriter( fileWriter);
//			bufferedWriter.write( "#!/bin/bash\n");

			// With GC and Mapability
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL,
					withGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL,
					withGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL,
					withGCandMapability, 
					args);

			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//***************************PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL********************//
			//*********************************WITH_MAPPABILITY_AND_GC_CONTENT*********************************************//
			//*************************************************************************************************************//
			
			
			
			
			//*************************************************************************************************************//
			//***************************PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL********************//
			//*********************************WITHOUT_MAPPABILITY_AND_GC_CONTENT******************************************//
			//*************************************************************************************************************//
//			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRunsewz(4).sh");
			fileWriter = FileOperations.createFileWriter( args[3] + "4SimulationGLANETRunsTakeTheLongestIntervalWithoutGCandMpability.sh");
			
			bufferedWriter = new BufferedWriter( fileWriter);
//			bufferedWriter.write( "#!/bin/bash\n");

			// Without GC and Mapability
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL,
					withoutGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL,
					withoutGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL,
					withoutGCandMapability, 
					args);

			// Close BufferedWriter
			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//***************************PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL********************//
			//*********************************WITHOUT_MAPPABILITY_AND_GC_CONTENT******************************************//
			//*************************************************************************************************************//
			
			
			
			//*************************************************************************************************************//
			//********************************COMPLETELY_DISCARD_INTERVAL**************************************************//
			//*******************************WITH_MAPPABILITY_AND_GC_CONTENT***********************************************//
			//*************************************************************************************************************//
			fileWriter = FileOperations.createFileWriter( args[3] + "5SimulationGLANETRunsCompletelyDiscardWithGCandMpability.sh");
			
			bufferedWriter = new BufferedWriter( fileWriter);

			// With GC and Mapability
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL,
					withGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL,
					withGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL,
					withGCandMapability, 
					args);

			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//********************************COMPLETELY_DISCARD_INTERVAL**************************************************//
			//*******************************WITH_MAPPABILITY_AND_GC_CONTENT***********************************************//
			//*************************************************************************************************************//
			
			
			//*************************************************************************************************************//
			//********************************COMPLETELY_DISCARD_INTERVAL**************************************************//
			//*******************************WITHOUT_MAPPABILITY_AND_GC_CONTENT********************************************//
			//*************************************************************************************************************//
			fileWriter = FileOperations.createFileWriter( args[3] + "6SimulationGLANETRunsCompletelyDiscardWithoutGCandMpability.sh");
			
			bufferedWriter = new BufferedWriter( fileWriter);

			// With GC and Mapability
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL,
					withoutGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL,
					withoutGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL,
					withoutGCandMapability, 
					args);

			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//********************************COMPLETELY_DISCARD_INTERVAL**************************************************//
			//*******************************WITHOUT_MAPPABILITY_AND_GC_CONTENT********************************************//
			//*************************************************************************************************************//

			
			
			//*************************************************************************************************************//
			//***************************************NO_DISCARD************************************************************//
			//*******************************WITH_MAPPABILITY_AND_GC_CONTENT***********************************************//
			//*************************************************************************************************************//
			fileWriter = FileOperations.createFileWriter( args[3] + "7SimulationGLANETRunsNoDiscardWithGCandMpability.sh");
			
			bufferedWriter = new BufferedWriter( fileWriter);

			// With GC and Mapability
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.NO_DISCARD,
					withGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.NO_DISCARD,
					withGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.NO_DISCARD,
					withGCandMapability, 
					args);

			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//***************************************NO_DISCARD************************************************************//
			//*******************************WITH_MAPPABILITY_AND_GC_CONTENT***********************************************//
			//*************************************************************************************************************//
	
			
			//*************************************************************************************************************//
			//***************************************NO_DISCARD************************************************************//
			//*******************************WITHOUT_MAPPABILITY_AND_GC_CONTENT********************************************//
			//*************************************************************************************************************//
			fileWriter = FileOperations.createFileWriter( args[3] + "8SimulationGLANETRunsNoDiscardWithoutGCandMpability.sh");
			
			bufferedWriter = new BufferedWriter( fileWriter);

			// With GC and Mapability
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.NO_DISCARD,
					withoutGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.NO_DISCARD,
					withoutGCandMapability, 
					args);
			
			writeGLANETRuns(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.NO_DISCARD,
					withoutGCandMapability, 
					args);

			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//***************************************NO_DISCARD************************************************************//
			//*******************************WITHOUT_MAPPABILITY_AND_GC_CONTENT********************************************//
			//*************************************************************************************************************//


			

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
