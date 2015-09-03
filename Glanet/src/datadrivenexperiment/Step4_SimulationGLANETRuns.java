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
import enumtypes.DataDrivenExperimentOperatingSystem;
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
			int numberofSimulations, 
			DataDrivenExperimentCellLineType cellLineType,
			DataDrivenExperimentGeneType geneType,
			DataDrivenExperimentOperatingSystem operatingSystem,
			float tpm,
			GenerateRandomDataMode withorWithout, 
			String args[]) throws IOException {
		
		String rootCommand = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		String fileName = null;
		
		FileWriter qsubFileWriter = null;
		BufferedWriter qsubBufferedWriter = null;
		
		
		String tpmString = DataDrivenExperimentCommon.getTPMString(tpm);
		
		String fileExtension = null;
		
	
		
		for(DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType: DataDrivenExperimentDnaseOverlapExclusionType.values()){
			
			if (geneType.isNonExpressingProteinCodingGenes() || 
				geneType.isExpressingProteinCodingGenes() && dnaseOverlapExclusionType.isNoDiscard()){
				
				//Decide on file extension
				switch(operatingSystem){
				
					case WINDOWS: 	{
						fileExtension = ".bat";
						break;
					}
									
									
					case LINUX: {
						fileExtension = ".sh";
						qsubFileWriter = FileOperations.createFileWriter(args[6] + System.getProperty("file.separator") + "qsub_calls" + fileExtension, true);
						qsubBufferedWriter = new BufferedWriter(qsubFileWriter);
						break;
					}
								
				}//End of SWITCH OperatingSystem
				
				
				//Decide on fileName
				switch( withorWithout){
				
					case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:
						fileName = args[6] + System.getProperty("file.separator") + "SimulationGLANETRuns_" + cellLineType.convertEnumtoString() + "_" + tpmString + "_" +  geneType.convertEnumtoString() + "_"   + dnaseOverlapExclusionType.convertEnumtoString() + "_" +   "wGCM" + fileExtension;
						break;
					case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
						fileName = args[6] + System.getProperty("file.separator") + "SimulationGLANETRuns_" + cellLineType.convertEnumtoString() + "_" + tpmString + "_" +  geneType.convertEnumtoString() + "_"   + dnaseOverlapExclusionType.convertEnumtoString() + "_" +   "woGCM" + fileExtension;
					break;
				
				}//End of SWITCH
				
				
				fileWriter = FileOperations.createFileWriter(fileName);	
				bufferedWriter = new BufferedWriter(fileWriter);
				
				//Adding Header lines
				//Adding qsub call in qsubFile
				switch(operatingSystem){
					
					case LINUX:{ 
						bufferedWriter.write("#!/bin/sh" + System.getProperty("line.separator"));
						bufferedWriter.write("#PBS -l nodes=1:ppn=8" + System.getProperty("line.separator"));
						bufferedWriter.write("# name of the queue that the job will be sent" + System.getProperty("line.separator"));
						bufferedWriter.write("#PBS -q cenga" + System.getProperty("line.separator"));
						bufferedWriter.write("# to use the environment variables of the shell that the job sent" + System.getProperty("line.separator"));
						bufferedWriter.write("#PBS -V" + System.getProperty("line.separator"));
						bufferedWriter.write("#!Running the program" + System.getProperty("line.separator"));
						
						bufferedWriter.write(System.getProperty("line.separator"));
						
						qsubBufferedWriter.write("qsub" + "\t" + fileName + System.getProperty("line.separator"));
						break;
					}
					
					default: 
						break;
					
				}//End of SWITCH
				
				rootCommand = "java -jar \"" + args[0] + "\" -Xms2G -Xmx2G -c -g \"" + args[1] + System.getProperty( "file.separator") + "\" -i \"" + args[1] + System.getProperty("file.separator") + "Data" + System.getProperty("file.separator") + "SimulationData" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + tpmString + "_" + geneType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + "Sim";

				for( int i = 0; i < numberofSimulations; i++){

					String command = rootCommand + i + ".txt\" " + "-f0 " + "-tf " + "-histone " + "-e " + "-ewz ";

					switch( withorWithout){

						case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:
			
							bufferedWriter.write( command + " -rdgcm -pe 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + tpmString + "_" + geneType.convertEnumtoString() +  "_" + dnaseOverlapExclusionType.convertEnumtoString() + "wGCM" + "Sim" + i + System.getProperty( "line.separator"));
							break;
			
						case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
			
							bufferedWriter.write( command + "-rd -pe 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + tpmString + "_" + geneType.convertEnumtoString()  + "_" + dnaseOverlapExclusionType.convertEnumtoString() +"woGCM" + "Sim" + i + System.getProperty( "line.separator"));
							break;
			
						default:
							break;

					}// End of SWITCH

				}// End of FOR
				
				//Close bufferedWriter
				bufferedWriter.close();
				fileWriter.close();
				
				qsubBufferedWriter.close();
				qsubFileWriter.close();
				
				
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
	 * args[7] = Data Driven Experiment Operating System e.g.: Windows or Linux
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
	 * args[7] ---> "Linux"
	 */
	public static void main( String[] args) {
		
		DataDrivenExperimentCellLineType cellLineType = DataDrivenExperimentCellLineType.convertStringtoEnum(args[2]);
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(args[3]);
		
		float tpm = Float.parseFloat(args[4]);
		
		// x1000
		int numberOfSimulations = Integer.parseInt(args[5]);

		
		GenerateRandomDataMode withGCandMapability = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
		GenerateRandomDataMode withoutGCandMapability = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
		
		//Operating System where the Data Driven Experiment will run
		DataDrivenExperimentOperatingSystem operatingSystem = DataDrivenExperimentOperatingSystem.convertStringtoEnum(args[7]);

		try{
			
			
			//*************************************************************************************************************//
			//****************************************************SIMULATIONS**********************************************//
			//**********************************************DATA DRIVEN EXPERIMENT*****************************************//
			//**************************************************GLANET RUNS************************************************//
			//*************************************************************************************************************//
			
			
				
			//******************************************WITH_MAPPABILITY_AND_GC_CONTENT************************************//
			//bufferedWriter.write( "#!/bin/bash\n");
			// With GC and Mapability
			writeGLANETRuns( 
					numberOfSimulations, 
					cellLineType,
					geneType,
					operatingSystem,
					tpm, 
					withGCandMapability,
					args);
			//******************************************WITH_MAPPABILITY_AND_GC_CONTENT************************************//
			
			
			
			//***************************************WITHOUT_MAPPABILITY_AND_GC_CONTENT************************************//
			// Without GC and Mapability
			writeGLANETRuns(
					numberOfSimulations, 
					cellLineType,
					geneType,
					operatingSystem,
					tpm, 
					withoutGCandMapability,
					args);
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
