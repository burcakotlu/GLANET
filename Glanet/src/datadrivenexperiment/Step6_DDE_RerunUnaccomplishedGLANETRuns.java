/**
 * This class is a combination of Step4 and Step5.
 * After a DDE is completed
 * some GLANET runs are not accomplished 
 * with no Enrichment directory or no Enrichment File at all.
 * Step5 class will detect such GLANET Runs under Output directory and write unaccomplished GLANET runs to a file under GLANETFolder/DDE folder
 * Step6 class will write sbatch calls in slurm files under GLANETFolder/DDE/ScriptFilesForReruns folder for such unaccomplished runs.
 * 
 */
package datadrivenexperiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;
import common.Commons;
import enumtypes.AssociationMeasureType;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentDnaseOverlapExclusionType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentOperatingSystem;
import enumtypes.DataDrivenExperimentTPMType;
import enumtypes.GenerateRandomDataMode;
import enumtypes.IsochoreFamilyMode;

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
		
		//root Command for TRUBA and WINDOWS
		//rootCommand for GLANET.jar call
		rootCommand = "java -jar \"" + GLANETJarFile + "\" -Xms4G -Xmx4G -c -g \"" + glanetFolder + System.getProperty( "file.separator") + "\" -i \"" + glanetFolder + System.getProperty("file.separator") + Commons.DDE + System.getProperty("file.separator") + Commons.DDE_DATA + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" +  Commons.DDE_RUN + runNumber;

		String fileExtension = null;
		
		try {
			
			
			//Set File Extension
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
			
							bufferedWriter.write( command + " -rdgcm -pe 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + generateRandomDataMode.convertEnumtoShortString() + associationMeasureType.convertEnumtoShortString()+  Commons.DDE_RUN + runNumber + System.getProperty( "line.separator"));
							break;
			
						case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
			
							bufferedWriter.write( command + "-rd -pe 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString()  + "_"  + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + generateRandomDataMode.convertEnumtoShortString() + associationMeasureType.convertEnumtoShortString() + Commons.DDE_RUN + runNumber + System.getProperty( "line.separator"));
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
					//No header line is needed for windows
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
	
	

	//e.g.: GM12878_NonExpressingGenes_TakeTheLongest_TopUnknown
	//CellLineType
	public static DataDrivenExperimentCellLineType fillCellLineTypeParameter(String mainKey){
		
		int indexofFirstUnderscore = -1;
		
		indexofFirstUnderscore = mainKey.indexOf(Commons.UNDERSCORE_CHAR);
		
		DataDrivenExperimentCellLineType cellLineType = DataDrivenExperimentCellLineType.convertStringtoEnum(mainKey.substring(0, indexofFirstUnderscore));		
		return cellLineType;
		
	}
	
	
	
	//e.g.: wGC_wIF_NOOB
	//generateRandomDataMode
	public static GenerateRandomDataMode fillGenerateRandomDataModeParameter(String subKey){
			
		int indexofFirstUnderscore = -1;
		
		indexofFirstUnderscore = subKey.indexOf(Commons.UNDERSCORE_CHAR);
		
		GenerateRandomDataMode generateRandomDataMode = GenerateRandomDataMode.convertStringtoEnum(subKey.substring(0,indexofFirstUnderscore));		
		return generateRandomDataMode;
	}
	
	//e.g.: wGC_wIF_NOOB
	public static IsochoreFamilyMode fillIsochoreFamilyModeParameter(String subKey){
		
		int indexofFirstUnderscore = -1;
		int indexofSecondUnderscore = -1;
		
		indexofFirstUnderscore = subKey.indexOf(Commons.UNDERSCORE_CHAR);
		indexofSecondUnderscore = subKey.indexOf(Commons.UNDERSCORE_CHAR,indexofFirstUnderscore+1);
		
		IsochoreFamilyMode isochoreFamilyMode = IsochoreFamilyMode.convertStringtoEnum(subKey.substring(indexofFirstUnderscore+1, indexofSecondUnderscore));		
		return isochoreFamilyMode;
	}
	
	//wGC_wIF_NOOB
	public static AssociationMeasureType fillAssociationMeasureTypeParameter(String subKey){
		
		int indexofFirstUnderscore = -1;
		int indexofSecondUnderscore = -1;
		
		indexofFirstUnderscore = subKey.indexOf(Commons.UNDERSCORE_CHAR);
		indexofSecondUnderscore = subKey.indexOf(Commons.UNDERSCORE_CHAR,indexofFirstUnderscore+1);
		
		AssociationMeasureType associationMeasureType = AssociationMeasureType.convertStringtoEnum(subKey.substring(indexofSecondUnderscore+1));		
		return associationMeasureType;
		
	}
	

	
	//e.g.: GM12878_NonExpressingGenes_TakeTheLongest_TopUnknown
	public static DataDrivenExperimentGeneType fillGeneTypeParameter(String mainKey){

		int indexofFirstUnderscore = -1;
		int indexofSecondUnderscore = -1;
		
		indexofFirstUnderscore = mainKey.indexOf(Commons.UNDERSCORE_CHAR);
		indexofSecondUnderscore = mainKey.indexOf(Commons.UNDERSCORE_CHAR,indexofFirstUnderscore+1);
		
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(mainKey.substring(indexofFirstUnderscore+1, indexofSecondUnderscore));
		
		return geneType;

	}
	
	
	//e.g.: GM12878_NonExpressingGenes_TakeTheLongest_TopUnknown
	public static DataDrivenExperimentTPMType fillTpmTypeParameter(String mainKey){

		int indexofFirstUnderscore = -1;
		int indexofSecondUnderscore = -1;
		int indexofThirdUnderscore = -1;
		
		
		indexofFirstUnderscore = mainKey.indexOf(Commons.UNDERSCORE_CHAR);
		indexofSecondUnderscore = mainKey.indexOf(Commons.UNDERSCORE_CHAR, indexofFirstUnderscore+1);
		indexofThirdUnderscore = mainKey.indexOf(Commons.UNDERSCORE_CHAR, indexofSecondUnderscore+1);
	
		DataDrivenExperimentTPMType tpmType = DataDrivenExperimentTPMType.convertStringtoEnum(mainKey.substring(indexofThirdUnderscore+1));
		
		return tpmType;
	}

	//e.g.: GM12878_NonExpressingGenes_TakeTheLongest_TopUnknown
	//dnaseOverlapExclusionType
	public static DataDrivenExperimentDnaseOverlapExclusionType fillDnaseOverlapExclusionTypeParameter(String mainKey){

		int indexofFirstUnderscore = -1;
		int indexofSecondUnderscore = -1;
		int indexofThirdUnderscore = -1;
		
		indexofFirstUnderscore = mainKey.indexOf(Commons.UNDERSCORE_CHAR);
		indexofSecondUnderscore = mainKey.indexOf(Commons.UNDERSCORE_CHAR, indexofFirstUnderscore+1);
		indexofThirdUnderscore = mainKey.indexOf(Commons.UNDERSCORE_CHAR, indexofSecondUnderscore+1);
		
		DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType = DataDrivenExperimentDnaseOverlapExclusionType.convertStringtoEnum(mainKey.substring(indexofSecondUnderscore+1,indexofThirdUnderscore));

		return dnaseOverlapExclusionType;
	}

	
	
	public static void writeMainKeyTextFile(BufferedWriter mainKeyTextBufferedWriter, String ddeFolder, String subKeyFileName) throws IOException{
		
		mainKeyTextBufferedWriter.write("sbatch" + "\t" +  ddeFolder + Commons.SCRIPT_FILES_FOR_RERUNS + System.getProperty("file.separator") + subKeyFileName + System.getProperty("line.separator"));
	}
	
	
	public static void writeSubKeyFile(
			String GLANETJarFile,
			String GLANETFolder,
			String mainKey,
			String subKey,
			BufferedWriter bufferedWriter, 
			List<Integer> runNumberList,
			DataDrivenExperimentCellLineType cellLineType,
			DataDrivenExperimentGeneType geneType,
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType,
			DataDrivenExperimentTPMType tpmType,
			GenerateRandomDataMode generateRandomDataMode,
			IsochoreFamilyMode isochoreFamilyMode,
			AssociationMeasureType associationMeasureType){
		
		//rootCommand for GLANET.jar call
		String rootCommand = "java -jar \"" + GLANETJarFile + "\" -Xms4G -Xmx4G -c -g \"" + GLANETFolder + System.getProperty( "file.separator") + "\" -i \"" + GLANETFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") + "DDE11Data" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + Commons.DDE_RUN;
		String command = null;
		int i = 0;
		
		try {
		
			for(Iterator<Integer> itr=runNumberList.iterator();itr.hasNext();){
				
				i= itr.next();
				
				command = rootCommand + i + ".txt\" " + "-f0 " + "-udl -udlinput " + "\"" + GLANETFolder + Commons.DATA + System.getProperty("file.separator") + "demo_input_data" + System.getProperty("file.separator") +  "UserDefinedLibrary" + System.getProperty("file.separator") + "DDCE_UserDefinedLibraryInputFile.txt\"" +  " -udldf0exc"  + " -e " + "-ewz ";							
				
				switch(generateRandomDataMode){
					case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:
						command = command + " -wgcm";
						break;
					case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
						command = command + " -wogcm";
						break;
					case GENERATE_RANDOM_DATA_WITH_MAPPABILITY:
						command = command + " -wm";
						break;
					case GENERATE_RANDOM_DATA_WITH_GC_CONTENT:
						command = command + " -wgc";
						break;
				}
				
				
				switch(isochoreFamilyMode){
					case DO_USE_ISOCHORE_FAMILY:
						command = command + " -wif";
						break;
					case DO_NOT_USE_ISOCHORE_FAMILY:
						command = command + " -woif";
						break;
				}
				
				switch(associationMeasureType){
					case NUMBER_OF_OVERLAPPING_BASES:
						command = command + " -noob";
						break;
					case EXISTENCE_OF_OVERLAP:
						command = command + " -eoo";
						break;
				}
	
				bufferedWriter.write( command + " -pe 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + generateRandomDataMode.convertEnumtoShortString() + isochoreFamilyMode.convertEnumtoShortString() + associationMeasureType.convertEnumtoShortString()+  Commons.DDE_RUN + i + System.getProperty( "line.separator"));	
				
			}//End of for
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void writeSubKeySlurmFile(
			String GLANETJarFile,
			String GLANETFolder,
			String mainKey,
			String subKey,
			BufferedWriter subKeySlurmBufferedWriter, 
			List<Integer> runNumberList) throws IOException{
		
		Integer runNumber = null;
		
		
		//subKey
		GenerateRandomDataMode generateRandomDataMode = fillGenerateRandomDataModeParameter(subKey);
		IsochoreFamilyMode isochoreFamilyMode = fillIsochoreFamilyModeParameter(subKey);
		AssociationMeasureType associationMeasureType = fillAssociationMeasureTypeParameter(subKey);
		
		//e.g.: mainKey --> GM12878_NonExpressingGenes_TakeTheLongest_TopUnknown
		DataDrivenExperimentCellLineType cellLineType = fillCellLineTypeParameter(mainKey);
		DataDrivenExperimentGeneType geneType = fillGeneTypeParameter(mainKey);
		DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType = fillDnaseOverlapExclusionTypeParameter(mainKey);
		DataDrivenExperimentTPMType tpmType = fillTpmTypeParameter(mainKey);
		
		String GENERAL_JOBNAME = null;
		String rootCommand = null;
		String command = null;
		
		
		//Add GCM extension
		GENERAL_JOBNAME = Commons.GLANET + "_" + Commons.DDE + "_" + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + generateRandomDataMode.convertEnumtoShortString(); 
		
		
		//Add associationMeasureType extension
		GENERAL_JOBNAME = GENERAL_JOBNAME + "_" + isochoreFamilyMode.convertEnumtoShortString();
				
		//Add associationMeasureType extension
		GENERAL_JOBNAME = GENERAL_JOBNAME + "_" + associationMeasureType.convertEnumtoShortString();
			
		//Write Header lines for TRUBA with array index
		subKeySlurmBufferedWriter.write("#!/bin/bash" + System.getProperty("line.separator"));
		subKeySlurmBufferedWriter.write("#SBATCH -M truba" + System.getProperty("line.separator"));
				
		//One core jobs can be sent to single and mercan.
		subKeySlurmBufferedWriter.write("#SBATCH -p mercan" + System.getProperty("line.separator"));
		
		subKeySlurmBufferedWriter.write("#SBATCH -A botlu" + System.getProperty("line.separator"));
		subKeySlurmBufferedWriter.write("#SBATCH -J " + GENERAL_JOBNAME + System.getProperty("line.separator"));
		subKeySlurmBufferedWriter.write("#SBATCH -N 1" + System.getProperty("line.separator"));
		subKeySlurmBufferedWriter.write("#SBATCH -n 8" + System.getProperty("line.separator"));
		subKeySlurmBufferedWriter.write("#SBATCH --time=8-00:00:00" + System.getProperty("line.separator"));
		
		
		//We don't want to get email for each of the 1000 GLANET Runs begins and ends but fails
		subKeySlurmBufferedWriter.write("#SBATCH --mail-type=FAIL" + System.getProperty("line.separator"));
		subKeySlurmBufferedWriter.write("#SBATCH --mail-user=burcak@ceng.metu.edu.tr" + System.getProperty("line.separator"));
		
		//newly added
		subKeySlurmBufferedWriter.write("#SBATCH --array=");
		
		for(int i=0; i<runNumberList.size(); i++){
			
			runNumber = runNumberList.get(i);
						
			//Last runNumber needs no comma
			if (i<runNumberList.size()-1){
				subKeySlurmBufferedWriter.write(runNumber + ",");
			}else{
				subKeySlurmBufferedWriter.write(runNumber + System.getProperty("line.separator"));
			}
		}//End of FOR each runNumber
		
		//Add Line Separator
		subKeySlurmBufferedWriter.write(System.getProperty("line.separator"));
		
		subKeySlurmBufferedWriter.write("which java" + System.getProperty("line.separator"));
		subKeySlurmBufferedWriter.write("echo \"SLURM_NODELIST $SLURM_NODELIST\"" + System.getProperty("line.separator"));
		subKeySlurmBufferedWriter.write("echo \"NUMBER OF CORES $SLURM_NTASKS\"" + System.getProperty("line.separator"));
		subKeySlurmBufferedWriter.write("echo \"SLURM_ARRAY_TASK_ID $SLURM_ARRAY_TASK_ID\"" + System.getProperty("line.separator"));
		
		
		//Add Line Separator
		subKeySlurmBufferedWriter.write(System.getProperty("line.separator"));

		//rootCommand for GLANET.jar call
		rootCommand = "java -jar \"" + GLANETJarFile + "\" -Xms4G -Xmx4G -c -g \"" + GLANETFolder + System.getProperty( "file.separator") + "\" -i \"" + GLANETFolder + System.getProperty("file.separator") + Commons.DDE + System.getProperty("file.separator") + Commons.DDE_DATA + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + Commons.DDE_RUN;

		
		if(associationMeasureType.isAssociationMeasureExistenceofOverlap()){
			command = rootCommand + "$SLURM_ARRAY_TASK_ID.txt\" " + "-f0 " + "-tf " + "-histone " + "-e " + "-ewz " + "-existOv ";							
		}else if(associationMeasureType.isAssociationMeasureNumberOfOverlappingBases()){
			command = rootCommand + "$SLURM_ARRAY_TASK_ID.txt\" " + "-f0 " + "-tf " + "-histone " + "-e " + "-ewz " + "-numOvBas ";							
		}
		
		switch(generateRandomDataMode){

			case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:				
				subKeySlurmBufferedWriter.write( command + " -rdgcm -pe 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + generateRandomDataMode.convertEnumtoShortString() + associationMeasureType.convertEnumtoShortString() + Commons.DDE_RUN + "$SLURM_ARRAY_TASK_ID" + System.getProperty("line.separator"));
				break;

			case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:				
				subKeySlurmBufferedWriter.write( command + "-rd -pe 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString()  + "_"  + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + generateRandomDataMode.convertEnumtoShortString() + associationMeasureType.convertEnumtoShortString() + Commons.DDE_RUN + "$SLURM_ARRAY_TASK_ID" + System.getProperty("line.separator"));
				break;

			default:
				break;

		}// End of SWITCH for wGCM or woGCM

		//Add Line Separator
		subKeySlurmBufferedWriter.write(System.getProperty("line.separator"));
		
		//Write exit
		subKeySlurmBufferedWriter.write("exit");
		
	}
	
	
	public static void writeBatFile(
			String GLANETJarFile,
			String GLANETFolder,
			String ddeFolder,
			Map<String,Map<String,List<Integer>>> mainKey2SubKey2RunNumberListMap,
			DataDrivenExperimentCellLineType cellLineType,
			DataDrivenExperimentGeneType geneType,
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType,
			DataDrivenExperimentTPMType tpmType,
			GenerateRandomDataMode generateRandomDataMode,
			IsochoreFamilyMode isochoreFamilyMode,
			AssociationMeasureType associationMeasureType){
		
		Map<String,List<Integer>> subKey2RunNumberListMap = null;
		List<Integer> runNumberList = null;
		
		
		FileWriter subKeyFileWriter = null;
		BufferedWriter subKeyBufferedWriter = null;
		
		String subKeyFileName = null;
		
		try {
			
			//There can be at most 8 mainKeys in our setup DDE
			//For each mainKey create and write a text file
			//In that text file have sbatch calls to each subKey slurm file
			//For each subKeys of the mainKey write a slurm file
			for(String mainKey: mainKey2SubKey2RunNumberListMap.keySet()){
				
					
				subKey2RunNumberListMap = mainKey2SubKey2RunNumberListMap.get(mainKey);
				
				for(String subKey:subKey2RunNumberListMap.keySet()){
					
					//Create subKeySlurmFile
					subKeyFileName = Commons.GLANET_DDE + "_" + mainKey + "_" + subKey + Commons.SLURM_FILE_EXTENSION;
					subKeyFileWriter = FileOperations.createFileWriter(ddeFolder + Commons.SCRIPT_FILES_FOR_RERUNS + System.getProperty("file.separator") + subKeyFileName);
					subKeyBufferedWriter = new BufferedWriter(subKeyFileWriter);
					
					runNumberList = subKey2RunNumberListMap.get(subKey);
					
					writeSubKeyFile(
							GLANETJarFile, 
							GLANETFolder, 
							mainKey,
							subKey,
							subKeyBufferedWriter,
							runNumberList,
							cellLineType,
							geneType,
							dnaseOverlapExclusionType,
							tpmType,
							generateRandomDataMode,
							isochoreFamilyMode,
							associationMeasureType);
				
					//Close subKeySlurmBufferedWriter
					subKeyBufferedWriter.close();
					
				}//End of each subKey
				
				
				
			}//End of FOR each mainKey
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	

	//TRUBA with array index case
	public static void fillSlurmFiles(
			String GLANETJarFile,
			String GLANETFolder,
			String ddeFolder,
			Map<String,Map<String,List<Integer>>> mainKey2SubKey2RunNumberListMap){
		
		Map<String,List<Integer>> subKey2RunNumberListMap = null;
		List<Integer> runNumberList = null;
		
		FileWriter mainKeyTextFileWriter = null;
		BufferedWriter mainKeyTextBufferedWriter = null;

		FileWriter subKeySlurmFileWriter = null;
		BufferedWriter subKeySlurmBufferedWriter = null;
		
		String subKeyFileName = null;
		
		
		try {
			
			//There can be at most 8 mainKeys in our setup DDE
			//For each mainKey create and write a text file
			//In that text file have sbatch calls to each subKey slurm file
			//For each subKeys of the mainKey write a slurm file
			for(String mainKey: mainKey2SubKey2RunNumberListMap.keySet()){
				
				//Create mainKeyTextFile
				mainKeyTextFileWriter = FileOperations.createFileWriter(ddeFolder + Commons.SCRIPT_FILES_FOR_RERUNS + System.getProperty("file.separator") + Commons.SBATCH_CALLS + mainKey + Commons.TEXT_FILE_EXTENSION);
				mainKeyTextBufferedWriter = new BufferedWriter(mainKeyTextFileWriter);
				
				subKey2RunNumberListMap = mainKey2SubKey2RunNumberListMap.get(mainKey);
				
				for(String subKey:subKey2RunNumberListMap.keySet()){
					
					//Create subKeySlurmFile
					subKeyFileName = Commons.GLANET_DDE + "_" + mainKey + "_" + subKey + Commons.SLURM_FILE_EXTENSION;
					subKeySlurmFileWriter = FileOperations.createFileWriter(ddeFolder + Commons.SCRIPT_FILES_FOR_RERUNS + System.getProperty("file.separator") + subKeyFileName);
					subKeySlurmBufferedWriter = new BufferedWriter(subKeySlurmFileWriter);
					
					runNumberList = subKey2RunNumberListMap.get(subKey);
					
					writeSubKeySlurmFile(GLANETJarFile, GLANETFolder, mainKey,subKey,subKeySlurmBufferedWriter,runNumberList);
					
					//Write sbatch call to subKeySlurmFile in mainKeyTextFile
					writeMainKeyTextFile(mainKeyTextBufferedWriter,ddeFolder,subKeyFileName);
					
					//Close subKeySlurmBufferedWriter
					subKeySlurmBufferedWriter.close();
					
				}//End of each subKey
				
				//Close mainKeyTextBufferedWriter
				mainKeyTextBufferedWriter.close();
				
			}//End of FOR each mainKey
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	public static void readUnaccomplisgedGLANETRunsFileAndWriteScriptFiles(
			String GLANETJarFile,
			String GLANETFolder,
			String DDEFolder,
			DataDrivenExperimentOperatingSystem operatingSystem,
			int DDENumber){
		
		DateFormat dateFormat = new SimpleDateFormat("_yyyy_MM_dd_");
		Date date = new Date();
		
		// Read unaccomplishedGLANETRunFile
		String unaccomplishedGLANETRunFile = DDEFolder + System.getProperty("file.separator") + dateFormat.format(date) + Commons.GLANET + "_" + Commons.DDE_UNACCOMPLISHED_RUNS_FILE_START + DDENumber + Commons.DDE_UNACCOMPLISHED_RUNS_FILE_REST  ;
		
		FileReader fileReader =  null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		int indexofFirstUnderscore = -1;
		int indexofSecondUnderscore = -1;
		int indexofThirdUnderscore = -1;
		
		int indexofwGC = -1;
		int indexofwIF = -1;
		
		int indexofwGCM = -1;
		int indexofwoGCM = -1;
		//int indexofEOO = -1;
		int indexofNOOB = -1;
		int indexofRun = -1;
		
		//cellLineType
		DataDrivenExperimentCellLineType cellLineType = null;
				
		//geneType
		DataDrivenExperimentGeneType geneType = null;
		
		//DnaseOverlapExclusionType
		DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType = null;
			
		//TPMType
		DataDrivenExperimentTPMType tpmType = null;
		
		String remaining = null;
		
			
		//generateRandomDataMode
		GenerateRandomDataMode generateRandomDataMode = null;
		
		//isochore Family mode
		IsochoreFamilyMode isochoreFamilyMode = null;
		
		//associationMeasureType
		AssociationMeasureType associationMeasureType = null;
		
		int runNumber = -1;
		
		List<String> cellLineGenerateRandomDataModeAssociationMeasureTypeList = new ArrayList<String>();
		
		//9 March 2016
		//Main case consists of cellLineType generateRandomDataMode associationMeasureType
		//Inner case consists of geneType tpmType dnaseOverlapExclusionType
		Map<String,Map<String,List<Integer>>> mainKey2SubKey2RunNumberListMap = new HashMap<String,Map<String,List<Integer>>>();		
		Map<String,List<Integer>> subKey2RunNumberListMap = null;
		List<Integer> runNumberList = null;
		
		String mainKey = null;
		String subKey = null;
		
		try {
			fileReader = FileOperations.createFileReader(unaccomplishedGLANETRunFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//Not a comment line
				if(!strLine.startsWith("#")){
					
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
					
					indexofwGC = remaining.indexOf(Commons.WGC);
					indexofwIF = remaining.indexOf(Commons.WIF);
					
					indexofwGCM = remaining.indexOf(Commons.WGCM);
					indexofwoGCM = remaining.indexOf(Commons.WOGCM);
					indexofNOOB = remaining.indexOf(Commons.NUMBER_OF_OVERLAPPING_BASES_SHORT);
					//indexofEOO = remaining.indexOf(Commons.EXISTENCE_OF_OVERLAP_SHORT);
					
					if (indexofwGCM>0){
						generateRandomDataMode = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
						dnaseOverlapExclusionType = DataDrivenExperimentDnaseOverlapExclusionType.convertStringtoEnum(remaining.substring(0,indexofwGCM));
						
					} else if (indexofwGC>0){
						generateRandomDataMode = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_GC_CONTENT;
						dnaseOverlapExclusionType = DataDrivenExperimentDnaseOverlapExclusionType.convertStringtoEnum(remaining.substring(0,indexofwGC));

					}else {
						generateRandomDataMode = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
						dnaseOverlapExclusionType = DataDrivenExperimentDnaseOverlapExclusionType.convertStringtoEnum(remaining.substring(0,indexofwoGCM));
					}
					
					if(indexofwIF>0){
						isochoreFamilyMode = IsochoreFamilyMode.DO_USE_ISOCHORE_FAMILY;
					}
					
					if (indexofNOOB>0){
						associationMeasureType = AssociationMeasureType.NUMBER_OF_OVERLAPPING_BASES;
					}else{
						associationMeasureType = AssociationMeasureType.EXISTENCE_OF_OVERLAP;
					}
					
					indexofRun = remaining.indexOf(Commons.DDE_RUN);
					
					//Accumulate the runNumbers in a list
					runNumber = Integer.parseInt(remaining.substring(indexofRun+3));
					
					//Form mainKey
					mainKey = 	cellLineType.convertEnumtoString() +  "_" + 
								geneType.convertEnumtoString() + "_" + 
								dnaseOverlapExclusionType.convertEnumtoString()  + "_"  + 
								tpmType.convertEnumtoString(); 
								
					
					//Form subKey
					subKey = generateRandomDataMode.convertEnumtoShortString() + "_" + 
							isochoreFamilyMode.convertEnumtoShortString() + "_" +
							associationMeasureType.convertEnumtoShortString();
					
					
					subKey2RunNumberListMap = mainKey2SubKey2RunNumberListMap.get(mainKey);
					
					if (subKey2RunNumberListMap==null){
						//Initialize 
						subKey2RunNumberListMap = new HashMap<String,List<Integer>>();
						mainKey2SubKey2RunNumberListMap.put(mainKey, subKey2RunNumberListMap);
											
					}//End of IF
					
					//Continue
					runNumberList = subKey2RunNumberListMap.get(subKey);
					
					if (runNumberList == null){
						
						runNumberList = new ArrayList<Integer>();
						runNumberList.add(runNumber);
						subKey2RunNumberListMap.put(subKey, runNumberList);
						
					}else{
						runNumberList.add(runNumber);
					}
				
					
					//WINDOWS and TRUBA without ArrayIndex Case
					if (operatingSystem.isWindows() || operatingSystem.isTRUBA()){
						
						//Write header lines for the fist time
						if (!cellLineGenerateRandomDataModeAssociationMeasureTypeList.contains(mainKey)){
							writeHeaderLines(DDEFolder,cellLineType,generateRandomDataMode,associationMeasureType,operatingSystem);
							cellLineGenerateRandomDataModeAssociationMeasureTypeList.add(mainKey);
						}
						
						writeGLANETRunToSLURMFile(GLANETJarFile,GLANETFolder,DDEFolder,cellLineType,generateRandomDataMode,associationMeasureType,geneType,tpmType,dnaseOverlapExclusionType,runNumber,operatingSystem);

					}//End of IF OS is WINDOWS or TRUBA without ArrayIndex Case
					
					
				}//End of IF
				
				
				
			}//End of WHILE
			
			//Close
			bufferedReader.close();
			
			writeRerunCommands(
					GLANETJarFile,
					GLANETFolder,
					DDEFolder,
					mainKey2SubKey2RunNumberListMap,
					operatingSystem,
					cellLineType,
					geneType,
					dnaseOverlapExclusionType,
					tpmType,
					generateRandomDataMode,
					isochoreFamilyMode,
					associationMeasureType);
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	
	public static void writeRerunCommands(
			String GLANETJarFile,
			String GLANETFolder,
			String DDEFolder,
			Map<String,Map<String,List<Integer>>> mainKey2SubKey2RunNumberListMap,
			DataDrivenExperimentOperatingSystem operatingSystem,
			DataDrivenExperimentCellLineType cellLineType,
			DataDrivenExperimentGeneType geneType,
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType,
			DataDrivenExperimentTPMType tpmType,
			GenerateRandomDataMode generateRandomDataMode,
			IsochoreFamilyMode isochoreFamilyMode,
			AssociationMeasureType associationMeasureType){
		
		
		switch(operatingSystem){
		
			case TRUBA_FAST:
				//Now in case of TRUBA with array index which means TRUBA_FAST
				
				//For each mainKey create a text file
				//In that text file make sbatch calls for each subKey  slurm file
				//then fill the content of each subKey slurm file
				fillSlurmFiles(GLANETJarFile,GLANETFolder,DDEFolder,mainKey2SubKey2RunNumberListMap);
				break;
				
			case WINDOWS:
				writeBatFile(
						GLANETJarFile,
						GLANETFolder,
						DDEFolder,
						mainKey2SubKey2RunNumberListMap,
						cellLineType,
						geneType,
						dnaseOverlapExclusionType,
						tpmType,
						generateRandomDataMode,
						isochoreFamilyMode,
						associationMeasureType);
				break;
				
			default:
				break;
			
		
		}//End of switch
		
		
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String GLANETJarFile = args[0];
		String GLANETFolder = args[1];
		String DDEFolder 	= GLANETFolder + System.getProperty("file.separator") + Commons.DDE + System.getProperty("file.separator");
		
		//Operating System where the Data Driven Experiment will run
		DataDrivenExperimentOperatingSystem operatingSystem = DataDrivenExperimentOperatingSystem.convertStringtoEnum(args[2]);
		
		int DDENumber = Integer.parseInt(args[3]);
				
		// Read unaccomplishedGLANETRunFile
		readUnaccomplisgedGLANETRunsFileAndWriteScriptFiles(GLANETJarFile,GLANETFolder,DDEFolder,operatingSystem,DDENumber);
		

	}

}
