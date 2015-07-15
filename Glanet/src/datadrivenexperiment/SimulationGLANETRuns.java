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
 *          Data Driven Experiment Step 4
 *
 */
public class SimulationGLANETRuns {

	public static void writeTPMIntervals( BufferedWriter bufferedWriter, int numberofSimulations, String tpm,
			DnaseOverlapExclusionType dnaseOverlapExclusionType, GenerateRandomDataMode withorWithout, String args[]) throws IOException {

		String rootCommand = "java -jar \"" + args[0] + "\" -Xms4G -Xmx4G -c -g \"" + args[1] + "\" -i \"" + args[1] + "Data" + System.getProperty( "file.separator") + "SimulationData" + System.getProperty( "file.separator") + tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "Sim";

		for( int i = 0; i < numberofSimulations; i++){

			String command = rootCommand + i + ".txt\"" + " " + "-f0" + " " + "-dnase" + " " + "-tf" + " " + "-histone" + " " + "-e ";

			switch( withorWithout){

			case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:

				bufferedWriter.write( command + " -rdgcm -pe 10000 -j " + tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "Sim" + i + System.getProperty( "line.separator"));
				break;

			case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:

				bufferedWriter.write( command + "-rd -pe 10000 -j " + tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "Sim" + i + "Wo" + System.getProperty( "line.separator"));
				break;

			default:
				break;

			}// End of SWITCH

		}// End of for

	}

	/**
	 * @param args
	 * args[0] = jar location
	 * args[1] = glanet folder (which includes Data folder inside)
	 * args[2] = numbeOfSimulations
	 * args[3] = location to save bat file
	 * 
	 * Example:
	 * 
	 * GLANET.jar (args[0])
	 * "/Volumes/External/Documents/GLANET/" (args[1])
	 * "100" (args[2])
	 * "/Volumes/Macintosh/Users/CanFirtina/Desktop/" (args[3])
	 */
	public static void main( String[] args) {

		// x1000
		int numberOfSimulations = Integer.parseInt( args[2]);

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		GenerateRandomDataMode withGCandMapability = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
		GenerateRandomDataMode withoutGCandMapability = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;

		try{
			// run this created bat file
			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRuns(1).sh");
			bufferedWriter = new BufferedWriter( fileWriter);
			bufferedWriter.write( "#!/bin/bash\n");

			// x12
			// With GC and Mapability
			writeTPMIntervals( bufferedWriter, numberOfSimulations, Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP, withGCandMapability,
					args);
			writeTPMIntervals( bufferedWriter, numberOfSimulations, Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP, withGCandMapability,
					args);
			writeTPMIntervals( bufferedWriter, numberOfSimulations, Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP, withGCandMapability,
					args);

			bufferedWriter.close();
			fileWriter.close();
			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRuns(2).sh");
			bufferedWriter = new BufferedWriter( fileWriter);
			bufferedWriter.write( "#!/bin/bash\n");

			// Without GC and Mapability
			writeTPMIntervals( bufferedWriter, numberOfSimulations, Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, args);
			writeTPMIntervals( bufferedWriter, numberOfSimulations, Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, args);
			writeTPMIntervals( bufferedWriter, numberOfSimulations, Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, args);

			bufferedWriter.close();
			fileWriter.close();
			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRuns(3).sh");
			bufferedWriter = new BufferedWriter( fileWriter);
			bufferedWriter.write( "#!/bin/bash\n");

			// With GC and Mapability
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withGCandMapability, args);
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withGCandMapability, args);
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withGCandMapability, args);

			bufferedWriter.close();
			fileWriter.close();
			fileWriter = FileOperations.createFileWriter( args[3] + "SimulationGLANETRuns(4).sh");
			bufferedWriter = new BufferedWriter( fileWriter);
			bufferedWriter.write( "#!/bin/bash\n");

			// Without GC and Mapability
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_001,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, args);
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_01,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, args);
			writeTPMIntervals(
					bufferedWriter,
					numberOfSimulations,
					Commons.TPM_0_1,
					DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,
					withoutGCandMapability, args);

			// Close BufferedWriter
			bufferedWriter.close();
			fileWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
