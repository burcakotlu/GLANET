/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import auxiliary.FileOperations;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentDnaseOverlapExclusionType;
import enumtypes.GenerateRandomDataMode;

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

	//For NonExpressingGenes, all of the DataDrivenExperimentDnaseOverlapExclusionType: CompletelyDiscard, NoDiscard, TakeAll, TakeTheLongest
	//For ExpressingGenes, only NoDiscard
	public static void writeGLANETRuns( 
			BufferedWriter bufferedWriter, 
			int numberofSimulations, 
			DataDrivenExperimentCellLineType cellLineType,
			DataDrivenExperimentGeneType geneType,
			String tpm,
			GenerateRandomDataMode withorWithout, 
			String args[]) throws IOException {
		
		String rootCommand = null;
		
		for(DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType: DataDrivenExperimentDnaseOverlapExclusionType.values()){
			
			if (geneType.isNonExpressingProteinCodingGenes() || 
				geneType.isExpressingProteinCodingGenes() && dnaseOverlapExclusionType.isNoDiscard()){
				
				rootCommand = "java -jar \"" + args[0] + "\" -Xms4G -Xmx4G -c -g \"" + args[1] + System.getProperty( "file.separator") + "\" -i \"" + args[1] + System.getProperty("file.separator") + "Data" + System.getProperty("file.separator") + "SimulationData" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + tpm + "_" + geneType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + "Sim";

				for( int i = 0; i < numberofSimulations; i++){

					String command = rootCommand + i + ".txt\" " + "-f0 " + "-tf " + "-histone " + "-e " + "-ewz ";

					switch( withorWithout){

						case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:
			
							bufferedWriter.write( command + " -rdgcm -pe 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + tpm + "_" + geneType.convertEnumtoString() +  "_" + dnaseOverlapExclusionType.convertEnumtoString() + "wGCM" + "Sim" + i + System.getProperty( "line.separator"));
							break;
			
						case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
			
							bufferedWriter.write( command + "-rd -pe 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + tpm + "_" + geneType.convertEnumtoString()  + "_" + dnaseOverlapExclusionType.convertEnumtoString() +"woGCM" + "Sim" + i + System.getProperty( "line.separator"));
							break;
			
						default:
							break;

					}// End of SWITCH

				}// End of FOR
				
			}//End of IF geneType is NonExpressingGenes or ExpressingGenesAndNoDiscard
			

		}//End of For traversing each value in enum DnaseOverlapExclusionType
			


	}

	/**
	 * @param args
	 * args[0] = GLANET.jar location
	 * args[1] = GLANET folder (which is parent of Data folder)
	 * args[2] = cellLineTpe
	 * args[3] = geneType
	 * args[4] = TPM
	 * args[5] = numbeOfSimulations
	 * args[6] = where to save bat file
	 * 
	 * Example:
	 * 
	 * args[0]	-->	"C:\Users\Burcak\Google Drive\GLANET\GLANET.jar"
	 * args[1]	-->	"C:\Users\Burcak\Google Drive"
	 * args[2]  --> GM12878, K562
	 * args[3] 	-->	NonExpressingGenes, ExpressingGenes
	 * args[4]  --> TPM
	 * args[5]	-->	1000
	 * args[6]	-->	"C:\Users\Burcak\Desktop"
	 */
	public static void main( String[] args) {
		
		DataDrivenExperimentCellLineType cellLineType = DataDrivenExperimentCellLineType.convertStringtoEnum(args[2]);
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(args[3]);
		
		float tpm = Float.parseFloat(args[4]);
		String tpmString = DataDrivenExperimentCommon.getTPMString(tpm);
		
		// x1000
		int numberOfSimulations = Integer.parseInt(args[5]);

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		GenerateRandomDataMode withGCandMapability = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
		GenerateRandomDataMode withoutGCandMapability = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;

		try{
			
			
			//*************************************************************************************************************//
			//****************************************************SIMULATIONS**********************************************//
			//**********************************************DATA DRIVEN EXPERIMENT*****************************************//
			//**************************************************GLANET RUNS************************************************//
			//*************************************************************************************************************//
			
				
				//******************************************WITH_MAPPABILITY_AND_GC_CONTENT************************************//
				fileWriter = FileOperations.createFileWriter(args[6] + System.getProperty("file.separator") + "SimulationGLANETRuns_" + cellLineType.convertEnumtoString() + "_" + tpmString + "_" +  geneType.convertEnumtoString() + "_" +   "wGCM" + ".bat");	
				bufferedWriter = new BufferedWriter( fileWriter);
				//bufferedWriter.write( "#!/bin/bash\n");

				// With GC and Mapability
				writeGLANETRuns(
						bufferedWriter, 
						numberOfSimulations, 
						cellLineType,
						geneType,
						tpmString, 
						withGCandMapability,
						args);
				
				//Close bufferedWriter
				bufferedWriter.close();
				fileWriter.close();
				//******************************************WITH_MAPPABILITY_AND_GC_CONTENT************************************//
				
				
				
				//***************************************WITHOUT_MAPPABILITY_AND_GC_CONTENT************************************//
				fileWriter = FileOperations.createFileWriter(args[6] + System.getProperty("file.separator") + "SimulationGLANETRuns_" + cellLineType.convertEnumtoString() + "_" + tpmString + "_" +  geneType.convertEnumtoString() + "_" +   "woGCM" + ".bat");	
				bufferedWriter = new BufferedWriter( fileWriter);

				// Without GC and Mapability
				writeGLANETRuns(
						bufferedWriter, 
						numberOfSimulations, 
						cellLineType,
						geneType,
						tpmString, 
						withoutGCandMapability,
						args);
			
				//Close bufferedWriter
				bufferedWriter.close();
				fileWriter.close();
				//***************************************WITHOUT_MAPPABILITY_AND_GC_CONTENT************************************//


				
			
				
			
			//*************************************************************************************************************//
			//****************************************************SIMULATIONS**********************************************//
			//**********************************************DATA DRIVEN EXPERIMENT*****************************************//
			//**************************************************GLANET RUNS************************************************//
			//*************************************************************************************************************//

		
		}catch( IOException e){
			e.printStackTrace();
		}

	}
}
