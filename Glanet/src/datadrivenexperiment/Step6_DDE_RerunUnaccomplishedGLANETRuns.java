/**
 * This class is a combination of Step4 and Step5.
 * After a DDE is completed
 * some GLANET runs are not accomplished 
 * with no Enrichment directory or no Enrichment File at all.
 * This class will detect such GLANET Runs under Output directory and write sbatch calls script files under DDE directory
 * In fact detection is done in Step5 class
 * Now writing script files for sbacth calls for these reruns are handled in this class.
 */
package datadrivenexperiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.AssociationMeasureType;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentDnaseOverlapExclusionType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentOperatingSystem;
import enumtypes.DataDrivenExperimentTPMType;
import enumtypes.GenerateRandomDataMode;

/**
 * @author Burçak Otlu
 * @date Mar 1, 2016
 * @project Glanet 
 *
 */
public class Step6_DDE_RerunUnaccomplishedGLANETRuns {
	
	
	
	public static void writeGLANETRunToSLURMFile(
			String GLANETJarFile,
			String glanetFolder,			
			String ddeFolder,
			DataDrivenExperimentCellLineType cellLineType,
			GenerateRandomDataMode generateRandomDataMode,
			AssociationMeasureType associationMeasureType,
			DataDrivenExperimentGeneType geneType,
			DataDrivenExperimentTPMType tpmType,
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType,
			int runNumber,
			DataDrivenExperimentOperatingSystem operatingSystem){
				
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String rootCommand = null;
		//rootCommand for GLANET.jar call
		rootCommand = "java -jar \"" + GLANETJarFile + "\" -Xms4G -Xmx4G -c -g \"" + glanetFolder + System.getProperty( "file.separator") + "\" -i \"" + glanetFolder + System.getProperty("file.separator") + Commons.DDE + System.getProperty("file.separator") + Commons.DDE_DATA + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" +  Commons.DDE_RUN + runNumber;

		String fileExtension = null;
		
		try {
			
			switch(operatingSystem){
				case TRUBA:
					fileExtension = Commons.SLURM_FILE_EXTENSION;
					break;
				case WINDOWS:
					fileExtension = Commons.BAT_FILE_EXTENSION;
					break;
				default:
					break;
			
			}//End of SWITCH
			
			fileWriter = FileOperations.createFileWriter(ddeFolder + Commons.SCRIPT_FILES_FOR_RERUNS + System.getProperty("file.separator") + cellLineType + "_" + generateRandomDataMode.convertEnumtoShortString() + "_" + associationMeasureType.convertEnumtoShortString() + fileExtension, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			switch(operatingSystem){
			
				case TRUBA:
				case WINDOWS:
					
					String command = null;
					
					if(associationMeasureType.isAssociationMeasureExistenceofOverlap()){
						command = rootCommand  + ".txt\" " + "-f0 " + "-tf " + "-histone " + "-e " + "-ewz " + "-existOv ";							
					}else if(associationMeasureType.isAssociationMeasureNumberOfOverlappingBases()){
						command = rootCommand  + ".txt\" " + "-f0 " + "-tf " + "-histone " + "-e " + "-ewz " + "-numOvBas ";							
					}
					

					switch(generateRandomDataMode){

						case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:
			
							bufferedWriter.write( command + " -rdgcm -pe 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "wGCM" + associationMeasureType.convertEnumtoShortString()+  Commons.DDE_RUN + runNumber + System.getProperty( "line.separator"));
							break;
			
						case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
			
							bufferedWriter.write( command + "-rd -pe 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString()  + "_"  + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() +"woGCM" + associationMeasureType.convertEnumtoShortString() + Commons.DDE_RUN + runNumber + System.getProperty( "line.separator"));
							break;
			
						default:
							break;

					}// End of SWITCH
					
					break;
				default:
					break;
				
			}//End of SWITCH
			
			
			//Close
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
		
	public static void writeHeaderLines(
			String ddeFolder,
			DataDrivenExperimentCellLineType cellLineType,
			GenerateRandomDataMode generateRandomDataMode,
			AssociationMeasureType associationMeasureType,
			DataDrivenExperimentOperatingSystem operatingSystem){
		
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String fileExtension = null;
		String GENERAL_JOBNAME = null;
		
		try {
			
			//Set the file extension
			switch(operatingSystem){
				case TRUBA:
					fileExtension = Commons.SLURM_FILE_EXTENSION;
					break;
				case WINDOWS:
					fileExtension = Commons.BAT_FILE_EXTENSION;				
					break;
				default: 
					break;
		
			}//End of SWITCH
			
			GENERAL_JOBNAME = cellLineType.convertEnumtoString() + "_" + generateRandomDataMode.convertEnumtoShortString() + "_" + associationMeasureType.convertEnumtoShortString();
			
			fileWriter = FileOperations.createFileWriter(ddeFolder + Commons.SCRIPT_FILES_FOR_RERUNS + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + generateRandomDataMode.convertEnumtoShortString() + "_" + associationMeasureType.convertEnumtoShortString() + fileExtension, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//Write the header lines
			switch(operatingSystem){			
				case TRUBA:
					bufferedWriter.write("#!/bin/bash" + System.getProperty("line.separator"));
					bufferedWriter.write("#SBATCH -M truba" + System.getProperty("line.separator"));
					bufferedWriter.write("#SBATCH -p mercan" + System.getProperty("line.separator"));
					
					bufferedWriter.write("#SBATCH -A botlu" + System.getProperty("line.separator"));
					bufferedWriter.write("#SBATCH -J " + GENERAL_JOBNAME + System.getProperty("line.separator"));
					bufferedWriter.write("#SBATCH -N 1" + System.getProperty("line.separator"));
					bufferedWriter.write("#SBATCH -n 8" + System.getProperty("line.separator"));
					bufferedWriter.write("#SBATCH --time=8-00:00:00" + System.getProperty("line.separator"));
					
					//bufferedWriter.write("#SBATCH --mail-type=FAIL" + System.getProperty("line.separator"));					
					bufferedWriter.write("#SBATCH --mail-type=ALL" + System.getProperty("line.separator"));
					bufferedWriter.write("#SBATCH --mail-user=burcak@ceng.metu.edu.tr" + System.getProperty("line.separator"));
					
					bufferedWriter.write(System.getProperty("line.separator"));
					bufferedWriter.write("which java" + System.getProperty("line.separator"));
					bufferedWriter.write("echo \"SLURM_NODELIST $SLURM_NODELIST\"" + System.getProperty("line.separator"));
					bufferedWriter.write("echo \"NUMBER OF CORES $SLURM_NTASKS\"" + System.getProperty("line.separator"));
					bufferedWriter.write(System.getProperty("line.separator"));

					break;
				case WINDOWS:					
					//No header lines for windows
					break;
				default: 
					break;
				
			}//End of switch
			
			
			//Close
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}



	public static void readUnaccomplisgedGLANETRunsFileAndWriteScriptFiles(
			String GLANETJarFile,
			String glanetFolder,
			String ddeFolder,
			DataDrivenExperimentOperatingSystem operatingSystem,
			int DDENumber){
		
		// Read unaccomplishedGLANETRunFile
		String unaccomplishedGLANETRunFile = ddeFolder + System.getProperty("file.separator") + Commons.GLANET_DDE_UNACCOMPLISHED_GLANET_RUNS_FILE_START + DDENumber + Commons.GLANET_DDE_UNACCOMPLISHED_GLANET_RUNS_FILE_REST  ;
		
		FileReader fileReader =  null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		int indexofFirstUnderscore = -1;
		int indexofSecondUnderscore = -1;
		int indexofThirdUnderscore = -1;
		int indexofwGCM = -1;
		int indexofwoGCM = -1;
		//int indexofEOO = -1;
		int indexofNOOB = -1;
		int indexofRun = -1;
		
		//cellLineType
		DataDrivenExperimentCellLineType cellLineType = null;
				
		//geneType
		DataDrivenExperimentGeneType geneType = null;
		
		//TPMType
		DataDrivenExperimentTPMType tpmType = null;
		
		String remaining = null;
		
		//DnaseOverlapExclusionType
		DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType = null;
		
		//generateRandomDataMode
		GenerateRandomDataMode generateRandomDataMode = null;
		
		//associationMeasureType
		AssociationMeasureType associationMeasureType = null;
		
		int runNumber = -1;
		
		List<String> cellLineGenerateRandomDataModeAssociationMeasureTypeList = new ArrayList<String>();
		
		try {
			fileReader = FileOperations.createFileReader(unaccomplishedGLANETRunFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//initialize
				cellLineType = null;
				geneType = null;
				tpmType = null;
				remaining = null;
				dnaseOverlapExclusionType = null;
				generateRandomDataMode = null;
				associationMeasureType = null;
				runNumber = -1;
				
				//get their values
				//example strLine
				//GM12878_ExpressingGenes_Top20_NoDiscardwGCMEOORun9
				indexofFirstUnderscore = strLine.indexOf('_');
				indexofSecondUnderscore = (indexofFirstUnderscore>0)? strLine.indexOf('_',indexofFirstUnderscore+1):-1;
				indexofThirdUnderscore = (indexofSecondUnderscore>0)? strLine.indexOf('_',indexofSecondUnderscore+1):-1;
				
				cellLineType = DataDrivenExperimentCellLineType.convertStringtoEnum(strLine.substring(0, indexofFirstUnderscore));
				geneType = DataDrivenExperimentGeneType.convertStringtoEnum(strLine.substring(indexofFirstUnderscore+1, indexofSecondUnderscore));
				tpmType =  DataDrivenExperimentTPMType.convertStringtoEnum(strLine.substring(indexofSecondUnderscore+1, indexofThirdUnderscore));
				
				remaining= strLine.substring(indexofThirdUnderscore+1);
				
				indexofwGCM = remaining.indexOf(Commons.WGCM);
				indexofwoGCM = remaining.indexOf(Commons.WOGCM);
				indexofNOOB = remaining.indexOf(Commons.NUMBER_OF_OVERLAPPING_BASES_SHORT);
				//indexofEOO = remaining.indexOf(Commons.EXISTENCE_OF_OVERLAP_SHORT);
				
				if (indexofwGCM>0){
					generateRandomDataMode = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
					dnaseOverlapExclusionType = DataDrivenExperimentDnaseOverlapExclusionType.convertStringtoEnum(remaining.substring(0,indexofwGCM));
					
				}else {
					generateRandomDataMode = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
					dnaseOverlapExclusionType = DataDrivenExperimentDnaseOverlapExclusionType.convertStringtoEnum(remaining.substring(0,indexofwoGCM));
				}
				
				if (indexofNOOB>0){
					associationMeasureType = AssociationMeasureType.NUMBER_OF_OVERLAPPING_BASES;
				}else{
					associationMeasureType = AssociationMeasureType.EXISTENCE_OF_OVERLAP;
				}
				
				indexofRun = remaining.indexOf(Commons.DDE_RUN);
				runNumber = Integer.parseInt(remaining.substring(indexofRun+3));
				
				if (!cellLineGenerateRandomDataModeAssociationMeasureTypeList.contains(cellLineType.convertEnumtoString() +  "_" + generateRandomDataMode.convertEnumtoShortString() + "_" + associationMeasureType.convertEnumtoShortString())){
					writeHeaderLines(ddeFolder,cellLineType,generateRandomDataMode,associationMeasureType,operatingSystem);
					cellLineGenerateRandomDataModeAssociationMeasureTypeList.add(cellLineType.convertEnumtoString() +  "_" + generateRandomDataMode.convertEnumtoShortString() + "_" + associationMeasureType.convertEnumtoShortString());
				}
				
				writeGLANETRunToSLURMFile(GLANETJarFile,glanetFolder,ddeFolder,cellLineType,generateRandomDataMode,associationMeasureType,geneType,tpmType,dnaseOverlapExclusionType,runNumber,operatingSystem);

			}//End of WHILE
			
			
			//Close
			bufferedReader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String GLANETJarFile = args[0];
		String glanetFolder = args[1];
		String ddeFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DDE + System.getProperty("file.separator");
		
		//Operating System where the Data Driven Experiment will run
		DataDrivenExperimentOperatingSystem operatingSystem = DataDrivenExperimentOperatingSystem.convertStringtoEnum(args[2]);
		
		int DDENumber = Integer.parseInt(args[3]);
				
		// Read unaccomplishedGLANETRunFile
		readUnaccomplisgedGLANETRunsFileAndWriteScriptFiles(GLANETJarFile,glanetFolder,ddeFolder,operatingSystem,DDENumber);
		

	}

}
