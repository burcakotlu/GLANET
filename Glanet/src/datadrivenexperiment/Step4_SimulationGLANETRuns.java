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

		String rootCommand = "java -jar \"" + args[0] + "\" -Xms4G -Xmx4G -c -g \"" + args[1] + "\" -i \"" + args[1] + "Data" + System.getProperty( "file.separator") + "SimulationData" + System.getProperty( "file.separator") + tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "Sim";

		for( int i = 0; i < numberofSimulations; i++){

			String command = rootCommand + i + ".txt\" " + "-f0 " + "-dnase " + "-tf " + "-histone " + "-e " + "-ewz ";

			switch( withorWithout){

				case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:
	
					bufferedWriter.write( command + " -rdgcm -pe 10000 -j " + tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "wGCM" + "Sim" + i + System.getProperty( "line.separator"));
					break;
	
				case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
	
					bufferedWriter.write( command + "-rd -pe 10000 -j " + tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() +"woGCM" + "Sim" + i + "Wo" + System.getProperty( "line.separator"));
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
			//*************************************************************************************************************//
			//*************************************************************************************************************//
			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRunsTakeAllRemainingIntervalsWithGCandMapability.bat");
			
			bufferedWriter = new BufferedWriter( fileWriter);
			//bufferedWriter.write( "#!/bin/bash\n");

			// x12
			// With GC and Mapability
			writeTPMIntervals(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS_IN_CASE_OF_DNASE_OVERLAP, 
					withGCandMapability,
					args);
			
			writeTPMIntervals(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS_IN_CASE_OF_DNASE_OVERLAP, 
					withGCandMapability,
					args);
			
			writeTPMIntervals(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS_IN_CASE_OF_DNASE_OVERLAP, 
					withGCandMapability,
					args);

			//Close bufferedWriter
			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//*************************************************************************************************************//
			//*************************************************************************************************************//

			
			
			//*************************************************************************************************************//
			//*************************************************************************************************************//
			//*************************************************************************************************************//
			//fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRunsewz(2).sh");
			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRunsTakeAllRemainingIntervalsWithoutGCandMapability.bat");
			
			bufferedWriter = new BufferedWriter( fileWriter);
//			bufferedWriter.write( "#!/bin/bash\n");

			// Without GC and Mapability
			writeTPMIntervals(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, 
					args);
			
			writeTPMIntervals(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, 
					args);
			
			
			writeTPMIntervals(
					bufferedWriter, 
					numberOfSimulations, 
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, 
					args);

			//Close bufferedWriter
			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//*************************************************************************************************************//
			//*************************************************************************************************************//

			
			
			
			//*************************************************************************************************************//
			//*************************************************************************************************************//
			//*************************************************************************************************************//
//			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRunsewz(3).sh");
			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRunsTakeOnlyTheLongestIntervalWithGCandMpability.bat");
			
			bufferedWriter = new BufferedWriter( fileWriter);
//			bufferedWriter.write( "#!/bin/bash\n");

			// With GC and Mapability
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withGCandMapability, 
					args);
			
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withGCandMapability, 
					args);
			
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withGCandMapability, 
					args);

			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//*************************************************************************************************************//
			//*************************************************************************************************************//
			
			
			
			
			//*************************************************************************************************************//
			//*************************************************************************************************************//
			//*************************************************************************************************************//
//			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRunsewz(4).sh");
			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRunsTakeOnlyTheLongestIntervalWithoutGCandMpability.bat");
			
			bufferedWriter = new BufferedWriter( fileWriter);
//			bufferedWriter.write( "#!/bin/bash\n");

			// Without GC and Mapability
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, 
					args);
			
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, 
					args);
			
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, 
					args);

			// Close BufferedWriter
			bufferedWriter.close();
			fileWriter.close();
			//*************************************************************************************************************//
			//*************************************************************************************************************//
			//*************************************************************************************************************//
			

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
