/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

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
import enumtypes.ToolType;

/**
 * @author Burcak Otlu
 * @date May 7, 2015
 * @project Glanet
 * 
 * Data Driven Experiment Step 4
 * 
 * In this class
 * 
 * GLANET Data Driven Experiments Simulations command line runs are written in a bat file for WINDOWS and in a slrum file for TRUBA.
 * By the way, SLURM stands for Simple LinUx Resource Management
 *
 */
public class Step4_DDE_ScriptFiles {
	
	
	//25 July 2016 starts
	public static void writeGATRuns(
			int startingRunNumber, 
			int endingRunNumber,
			DataDrivenExperimentCellLineType cellLineType,
			DataDrivenExperimentGeneType geneType,
			DataDrivenExperimentOperatingSystem operatingSystem,
			DataDrivenExperimentTPMType tpmType,
			AssociationMeasureType associationMeasureType,
			GenerateRandomDataMode generateRandomDataMode,
			String args[],
			String dataDrivenExperimentFolder,
			String dataDrivenExperimentScriptFolder)throws IOException{
		
		String rootCommand = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		String fileName = null;
		
		//FileWriter call_Runs_WithGC_FileWriter = null;
		//BufferedWriter call_Runs_WithGC_BufferedWriter = null;		
		
		FileWriter call_Runs_WithGCM_FileWriter = null;
		BufferedWriter call_Runs_WithGCM_BufferedWriter = null;
		
		FileWriter call_Runs_WithoutGCM_FileWriter = null;
		BufferedWriter call_Runs_WithoutGCM_BufferedWriter = null;
		
		String fileExtension = null;
		
		String GENERAL_JOBNAME = null;
		
		for(DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType: DataDrivenExperimentDnaseOverlapExclusionType.values()){
			
			GENERAL_JOBNAME = Commons.GAT + "_" + Commons.DDE + "_" + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + generateRandomDataMode.convertEnumtoShortString() +  "_" + associationMeasureType.convertEnumtoShortString(); 
			
			//We want to get rid of NonExpressingGenes and NoDiscard Case
			//We want to run ExpressingGenes and NoDiscard Case only
			if ((geneType.isNonExpressingProteinCodingGenes() && !dnaseOverlapExclusionType.isNoDiscard()) || 
				(geneType.isExpressingProteinCodingGenes() && dnaseOverlapExclusionType.isNoDiscard())){
				
				//Decide on file extension w.r.t. OperatingSystem
				switch(operatingSystem){
				
					case WINDOWS: 	{
						fileExtension = Commons.BAT_FILE_EXTENSION;
						break;
					}
																		
					case LINUX:
					case TURENG_MACHINE:{
						fileExtension = Commons.SH_FILE_EXTENSION;
						break;
					}
					
					case TRUBA:
					case TRUBA_FAST: {
						fileExtension = Commons.SLURM_FILE_EXTENSION;
						
						break;
					}
								
				}//End of SWITCH
				
				
				//Text Files 
				switch(generateRandomDataMode){
									
				
					case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:	
						call_Runs_WithGCM_FileWriter = FileOperations.createFileWriter(dataDrivenExperimentScriptFolder + Commons.SBATCH_CALLS + cellLineType.convertEnumtoString() +  "_" + Commons.WGCM + "_" + associationMeasureType.convertEnumtoShortString() + Commons.TEXT_FILE_EXTENSION, true);
						call_Runs_WithGCM_BufferedWriter = new BufferedWriter(call_Runs_WithGCM_FileWriter);						
						break;
					
					case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:
						call_Runs_WithoutGCM_FileWriter = FileOperations.createFileWriter(dataDrivenExperimentScriptFolder + Commons.SBATCH_CALLS + cellLineType.convertEnumtoString() + "_" + Commons.WOGCM + "_" + associationMeasureType.convertEnumtoShortString() + Commons.TEXT_FILE_EXTENSION, true);
						call_Runs_WithoutGCM_BufferedWriter = new BufferedWriter(call_Runs_WithoutGCM_FileWriter);
						break;
						
					default:
						break;
					
				}//End of GenerateRandomDataMode
				
				
				//Decide on fileName
				fileName = dataDrivenExperimentScriptFolder + "GAT_DDE_" + cellLineType.convertEnumtoString() + "_" +  geneType.convertEnumtoString() + "_"  + tpmType.convertEnumtoString() + "_"  + dnaseOverlapExclusionType.convertEnumtoString() + "_"+  generateRandomDataMode.convertEnumtoShortString() + "_" + associationMeasureType.convertEnumtoShortString() + fileExtension;
				
				fileWriter = FileOperations.createFileWriter(fileName);	
				bufferedWriter = new BufferedWriter(fileWriter);
				
				//Adding Header lines
				//Adding qsub call in qsubFile
				switch(operatingSystem){
				
					case WINDOWS:{
						if(generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){
							if (call_Runs_WithGCM_BufferedWriter!=null){
								call_Runs_WithGCM_BufferedWriter.write("cmd /c" +  "\t" + fileName + System.getProperty("line.separator"));
							}
						}else if (generateRandomDataMode.isGenerateRandomDataModeWithoutMapabilityandGc()){
							if (call_Runs_WithoutGCM_BufferedWriter!=null){
								call_Runs_WithoutGCM_BufferedWriter.write("cmd /c" +  "\t" + fileName + System.getProperty("line.separator"));								
							}
						}
						break;
					}
					
					case LINUX:{ 
						bufferedWriter.write("#!/bin/sh" + System.getProperty("line.separator"));
						bufferedWriter.write("#PBS -l nodes=1:ppn=16" + System.getProperty("line.separator"));
						bufferedWriter.write("# name of the queue that the job will be sent" + System.getProperty("line.separator"));
						bufferedWriter.write("#PBS -q cenga" + System.getProperty("line.separator"));
						bufferedWriter.write("# to use the environment variables of the shell that the job sent" + System.getProperty("line.separator"));
						bufferedWriter.write("#PBS -V" + System.getProperty("line.separator"));
						bufferedWriter.write("#!Running the program" + System.getProperty("line.separator"));
						
						bufferedWriter.write(System.getProperty("line.separator"));
						
						if(generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){
							if (call_Runs_WithGCM_BufferedWriter!=null){
								call_Runs_WithGCM_BufferedWriter.write("qsub" + "\t" + fileName + System.getProperty("line.separator"));								
							}
						}else if (generateRandomDataMode.isGenerateRandomDataModeWithoutMapabilityandGc()){
							if (call_Runs_WithoutGCM_BufferedWriter!=null){
								call_Runs_WithoutGCM_BufferedWriter.write("qsub" + "\t" + fileName + System.getProperty("line.separator"));
							}
						}
						
						break;
					}
					
					
					case TRUBA_FAST:	{					
						bufferedWriter.write("#!/bin/bash" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -M truba" + System.getProperty("line.separator"));
						
						
						//One core jobs can be sent to single and mercan.
						bufferedWriter.write("#SBATCH -p mercan" + System.getProperty("line.separator"));
						
						bufferedWriter.write("#SBATCH -A botlu" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -J " + GENERAL_JOBNAME + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -N 1" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -n 8" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH --time=8-00:00:00" + System.getProperty("line.separator"));
						
						
						//We don't want to get email for each of the 1000 GLANET Runs begins and ends but fails
						bufferedWriter.write("#SBATCH --mail-type=FAIL" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH --mail-user=burcak@ceng.metu.edu.tr" + System.getProperty("line.separator"));
						
						//newly added
						bufferedWriter.write("#SBATCH --array="+ startingRunNumber + "-" + (endingRunNumber-1) + System.getProperty("line.separator"));
						
						
						bufferedWriter.write(System.getProperty("line.separator"));
						bufferedWriter.write("which java" + System.getProperty("line.separator"));
						bufferedWriter.write("echo \"SLURM_NODELIST $SLURM_NODELIST\"" + System.getProperty("line.separator"));
						bufferedWriter.write("echo \"NUMBER OF CORES $SLURM_NTASKS\"" + System.getProperty("line.separator"));
						bufferedWriter.write("echo \"SLURM_ARRAY_TASK_ID $SLURM_ARRAY_TASK_ID\"" + System.getProperty("line.separator"));
				
						bufferedWriter.write(System.getProperty("line.separator"));
						
						if(generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){
							if (call_Runs_WithGCM_BufferedWriter!=null){
								call_Runs_WithGCM_BufferedWriter.write("sbatch" + "\t" + fileName + System.getProperty("line.separator"));								
							}
						}else if (generateRandomDataMode.isGenerateRandomDataModeWithoutMapabilityandGc()){
							if (call_Runs_WithoutGCM_BufferedWriter!=null){
								call_Runs_WithoutGCM_BufferedWriter.write("sbatch" + "\t" + fileName + System.getProperty("line.separator"));								
							}
						}
	
						break;
					}

					
					default: 
						break;
					
				}//End of SWITCH for writing header lines
					

				switch(operatingSystem){
					//TODO has to be updated.
					case TRUBA_FAST:{
						
						//rootCommand for GAT call
						//gat-run.py --segments=srf.hg19.bed --annotations=jurkat.hg19.dhs.bed --workspace=contigs.bed --ignore-segment-tracks --num-samples=1000 --log=gat.log > gat.tsv

						//we have to have gat-run for each element
						rootCommand = "gat-run.py --segments=" + args[1] + System.getProperty("file.separator") + Commons.DDE + System.getProperty("file.separator") + Commons.DDE_DATA + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + Commons.DDE_RUN + "$SLURM_ARRAY_TASK_ID.txt\" " 
							+ " --annotations=" + cellLineType.convertEnumtoString()+  "_H2AZ.narrowPeak"
							+ " --annotations=" + cellLineType.convertEnumtoString()+  "_H3K27AC.narrowPeak"
							+ " --annotations=" + cellLineType.convertEnumtoString()+  "_H3K4ME2.narrowPeak" 
							+ " --annotations=" + cellLineType.convertEnumtoString()+  "_H3K4ME3.narrowPeak" 
							+ " --annotations=" + cellLineType.convertEnumtoString()+  "_H3K79ME2.narrowPeak"
							+ " --annotations=" + cellLineType.convertEnumtoString()+  "_H3K9AC.narrowPeak";
						
						//We look for H3K9ACB only for K562 cellLine
						if (cellLineType.isK562()){
							rootCommand = rootCommand 
									+ 	" --annotations=" + cellLineType.convertEnumtoString()+  "_H3K9ACB.narrowPeak";
						}
							
						rootCommand = rootCommand	
							+ " --annotations=" + cellLineType.convertEnumtoString()+  "_POL2.narrowPeak"
							+ " --annotations=" + cellLineType.convertEnumtoString()+  "_H3K36ME3.narrowPeak"
							+ " --annotations=" + cellLineType.convertEnumtoString()+  "_H3K4ME1.narrowPeak"
							+ " --annotations=" + cellLineType.convertEnumtoString()+  "_H3K9ME3.narrowPeak"
							+ " --annotations=" + cellLineType.convertEnumtoString()+  "_H4K20ME1.narrowPeak"
							+ " --workspace=contigs.bed";
						
						
						//consider isochore or not
						if (generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){
							rootCommand = rootCommand 
									+ 	" --isochore-file=/home/burcakotlu/DDE/Isochore/gcprofile_bins.bed";
						}
						
						rootCommand = rootCommand  
							+ " --ignore-segment-tracks"
							+ " --num-samples=10000"
							+ " --log=" + 
							Commons.GAT + "_" +
							cellLineType.convertEnumtoString() + "_" +
							geneType.convertEnumtoString() + "_" + 
							tpmType.convertEnumtoString() + "_" + 
							dnaseOverlapExclusionType.convertEnumtoString() + "_" + 
							generateRandomDataMode.convertEnumtoShortString() +  "_" + 
							associationMeasureType.convertEnumtoShortString() + "_" + 
							Commons.DDE_RUN +
							"$SLURM_ARRAY_TASK_ID.log"
							+ " > " + Commons.GAT + "_" +
							cellLineType.convertEnumtoString() + "_" +
							geneType.convertEnumtoString() + "_" + 
							tpmType.convertEnumtoString() + "_" + 
							dnaseOverlapExclusionType.convertEnumtoString() + "_" + 
							generateRandomDataMode.convertEnumtoShortString() +  "_" + 
							associationMeasureType.convertEnumtoShortString() + "_" + 
							Commons.DDE_RUN +
							"$SLURM_ARRAY_TASK_ID.tsv";
							
							bufferedWriter.write(rootCommand + System.getProperty("line.separator"));
							
						}
						break;
						
					case LINUX:{
						
							for(int i=startingRunNumber; i< endingRunNumber; i++){
								 
								//rootCommand for GAT call
								//gat-run.py --segments=srf.hg19.bed --annotations=jurkat.hg19.dhs.bed --workspace=contigs.bed --ignore-segment-tracks --num-samples=1000 --log=gat.log > gat.tsv

								//we have to have gat-run for each element
								rootCommand = "gat-run.py --segments=" + dataDrivenExperimentFolder + Commons.DATA + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + Commons.DDE_RUN + i + ".txt " 
										+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H3K27ME3.narrowPeak"
										+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H2AZ.narrowPeak"
										+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H3K27AC.narrowPeak"
									+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H3K4ME2.narrowPeak" 
									+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H3K4ME3.narrowPeak" 
									+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H3K79ME2.narrowPeak"
									+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") +  cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H3K9AC.narrowPeak";
								
								//We look for H3K9ACB only for K562 cellLine
								if (cellLineType.isK562()){
									rootCommand = rootCommand 
											+ 	" --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H3K9ACB.narrowPeak";
								}
									
								rootCommand = rootCommand	
									+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_POL2.narrowPeak"
									+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H3K36ME3.narrowPeak"
									+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H3K4ME1.narrowPeak"
									+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H3K9ME3.narrowPeak"
									+ " --annotations=" + dataDrivenExperimentFolder + "Annotations" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "/" + cellLineType.convertEnumtoString()+  "_H4K20ME1.narrowPeak"
									+ " --workspace=" + dataDrivenExperimentFolder + "Workspace" + System.getProperty("file.separator") + "contigs.bed";
								
								
								//consider isochore or not
								if (generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc() || generateRandomDataMode.isGenerateRandomDataModeWithGC()){
									rootCommand = rootCommand 
											+ 	" --isochore-file=" +  dataDrivenExperimentFolder + "Isochore" + System.getProperty("file.separator") + "gcprofile_bins.bed";
								}
								
								
								//consider association measure type
								if (associationMeasureType.isAssociationMeasureExistenceofOverlap()){
									rootCommand = rootCommand 
											+ 	" --counter=segment-overlap";
								}else if (associationMeasureType.isAssociationMeasureNumberOfOverlappingBases()){
									rootCommand = rootCommand 
											+ 	" --counter=nucleotide-overlap";
								}
																
								rootCommand = rootCommand	
									+ " --ignore-segment-tracks"
									+ " --num-samples=10000"
									+ " --log=" + dataDrivenExperimentFolder + Commons.OUTPUT + System.getProperty("file.separator") +
									Commons.GAT + "_" +
									cellLineType.convertEnumtoString() + "_" +
									geneType.convertEnumtoString() + "_" + 
									tpmType.convertEnumtoString() + "_" + 
									dnaseOverlapExclusionType.convertEnumtoString() + "_" + 
									generateRandomDataMode.convertEnumtoShortString() +  "_" + 
									associationMeasureType.convertEnumtoShortString() + "_" + 
									Commons.DDE_RUN + i + 
									".log"
									+ " > " + dataDrivenExperimentFolder + Commons.OUTPUT + System.getProperty("file.separator") + Commons.GAT + "_" +
									cellLineType.convertEnumtoString() + "_" +
									geneType.convertEnumtoString() + "_" + 
									tpmType.convertEnumtoString() + "_" + 
									dnaseOverlapExclusionType.convertEnumtoString() + "_" + 
									generateRandomDataMode.convertEnumtoShortString() +  "_" + 
									associationMeasureType.convertEnumtoShortString() + "_" + 
									Commons.DDE_RUN + i + 
									".tsv";
								
								bufferedWriter.write(rootCommand + System.getProperty("line.separator") + System.getProperty("line.separator"));
								
							}//End of FOR each run
						
						}
						break;
						
					default:
						break;
				}
				
				//Add Line Separator
				bufferedWriter.write(System.getProperty("line.separator"));
				
				if (call_Runs_WithGCM_BufferedWriter!=null){
					call_Runs_WithGCM_BufferedWriter.write(System.getProperty("line.separator"));					
				}
				if(call_Runs_WithoutGCM_BufferedWriter!=null){
					call_Runs_WithoutGCM_BufferedWriter.write(System.getProperty("line.separator"));
				}
				
				//Adding Exit line
				switch(operatingSystem){
				
					case TRUBA:
					case TRUBA_FAST:
						bufferedWriter.write("exit");
						break;
						
					default: 
						break;
				
				}//End of SWITCH for adding exit line.				
				
				//Close bufferedWriters
				bufferedWriter.close();
				fileWriter.close();
				
				if (call_Runs_WithGCM_BufferedWriter!=null){
					call_Runs_WithGCM_BufferedWriter.close();
					call_Runs_WithGCM_FileWriter.close();
				}
				
				
				if(call_Runs_WithoutGCM_BufferedWriter!=null){
					call_Runs_WithoutGCM_BufferedWriter.close();
					call_Runs_WithoutGCM_FileWriter.close();
				}
				
			}//End of IF geneType is NonExpressingGenes or ExpressingGenesAndNoDiscard
		}//End of For traversing each value in enum DnaseOverlapExclusionType

	}	
	//25 July 2016 ends

	

	//For NonExpressingGenes, only CompletelyDiscard, TakeTheLongest
	//For ExpressingGenes, only NoDiscard
	//For AssociationMeasureType only NumberofOverlappingBases
	public static void writeGLANETRuns( 
			int startingRunNumber, 
			int endingRunNumber,
			DataDrivenExperimentCellLineType cellLineType,
			DataDrivenExperimentGeneType geneType,
			DataDrivenExperimentOperatingSystem operatingSystem,
			DataDrivenExperimentTPMType tpmType,
			AssociationMeasureType associationMeasureType,
			GenerateRandomDataMode generateRandomDataMode, 
			IsochoreFamilyMode isochoreFamilyMode,
			String args[],
			String dataDrivenExperimentScriptFolder) throws IOException {
		
		String rootCommand = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		String fileName = null;
		
		FileWriter sbatch_calls_FileWriter = null;
		BufferedWriter sbatch_calls_BufferedWriter = null;
		
		String fileExtension = null;
		
		String GENERAL_JOBNAME = null;
			
		for(DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType: DataDrivenExperimentDnaseOverlapExclusionType.values()){
			
			//Add cellLineTyoe geneType tpmType dnaseOverlapExclusionType
			GENERAL_JOBNAME = Commons.GLANET + "_" + Commons.DDE + "_" + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString();  
			
			//Add isochoreFamilyMode extension
			GENERAL_JOBNAME = GENERAL_JOBNAME + "_" + generateRandomDataMode.convertEnumtoShortString() ;

			//Add isochoreFamilyMode extension
			GENERAL_JOBNAME = GENERAL_JOBNAME + "_" + isochoreFamilyMode.convertEnumtoShortString();
			
			//Add associationMeasureType extension
			GENERAL_JOBNAME = GENERAL_JOBNAME + "_" + associationMeasureType.convertEnumtoShortString();
			
			//We want to get rid of NonExpressingGenes and NoDiscard Case
			//We want to run ExpressingGenes and NoDiscard Case only
			if ((geneType.isNonExpressingProteinCodingGenes() && !dnaseOverlapExclusionType.isNoDiscard()) || 
				(geneType.isExpressingProteinCodingGenes() && dnaseOverlapExclusionType.isNoDiscard())){
				
				//Decide on file extension w.r.t. OperatingSystem
				switch(operatingSystem){
				
					case WINDOWS: 	{
						fileExtension = Commons.BAT_FILE_EXTENSION;
						break;
					}
																		
					case LINUX:
					case TURENG_MACHINE:{
						fileExtension = Commons.SH_FILE_EXTENSION;
						break;
					}
					
					case TRUBA:
					case TRUBA_FAST: {
						fileExtension = Commons.SLURM_FILE_EXTENSION;
						break;
					}
								
				}//End of SWITCH for file extension
				
				
				sbatch_calls_FileWriter = FileOperations.createFileWriter(dataDrivenExperimentScriptFolder + Commons.SBATCH_CALLS + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_"  + tpmType.convertEnumtoString() + "_"  + dnaseOverlapExclusionType.convertEnumtoString() + Commons.TEXT_FILE_EXTENSION, true);
				sbatch_calls_BufferedWriter = new BufferedWriter(sbatch_calls_FileWriter);
				
				//Decide on fileName
				fileName = dataDrivenExperimentScriptFolder + "GLANET_DDE_" + cellLineType.convertEnumtoString() + "_" +  geneType.convertEnumtoString() + "_"  + tpmType.convertEnumtoString() + "_"  + dnaseOverlapExclusionType.convertEnumtoString() + "_"+  generateRandomDataMode.convertEnumtoShortString() + "_" +  isochoreFamilyMode.convertEnumtoShortString() + "_" + associationMeasureType.convertEnumtoShortString() + fileExtension;
								
				fileWriter = FileOperations.createFileWriter(fileName);	
				bufferedWriter = new BufferedWriter(fileWriter);
				
				//Adding Header lines
				//Adding qsub call in qsubFile
				switch(operatingSystem){
				
					case WINDOWS:{
						sbatch_calls_BufferedWriter.write("cmd /c" +  "\t" + fileName + System.getProperty("line.separator"));
						break;
					}
					
					case LINUX:{ 
						bufferedWriter.write("#!/bin/sh" + System.getProperty("line.separator"));
						bufferedWriter.write("#PBS -l nodes=1:ppn=16" + System.getProperty("line.separator"));
						bufferedWriter.write("# name of the queue that the job will be sent" + System.getProperty("line.separator"));
						bufferedWriter.write("#PBS -q cenga" + System.getProperty("line.separator"));
						bufferedWriter.write("# to use the environment variables of the shell that the job sent" + System.getProperty("line.separator"));
						bufferedWriter.write("#PBS -V" + System.getProperty("line.separator"));
						bufferedWriter.write("#!Running the program" + System.getProperty("line.separator"));
						
						bufferedWriter.write(System.getProperty("line.separator"));
						
						sbatch_calls_BufferedWriter.write("qsub" + "\t" + fileName + System.getProperty("line.separator"));	
						break;
					}
					
					case TURENG_MACHINE:{
						
						bufferedWriter.write("#!/bin/sh" + System.getProperty("line.separator"));
						bufferedWriter.write(System.getProperty("line.separator"));
						
						sbatch_calls_BufferedWriter.write("qsub" + "\t" + fileName + System.getProperty("line.separator"));
						break;
						
					}
					
					case TRUBA:{
						bufferedWriter.write("#!/bin/bash" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -M truba" + System.getProperty("line.separator"));
						
						if(generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){	
							bufferedWriter.write("#SBATCH -p mid1" + System.getProperty("line.separator"));
						}else if (generateRandomDataMode.isGenerateRandomDataModeWithoutMapabilityandGc()){
							bufferedWriter.write("#SBATCH -p mid2" + System.getProperty("line.separator"));
						}
						
						bufferedWriter.write("#SBATCH -A botlu" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -J " + GENERAL_JOBNAME + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -N 1" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -n 8" + System.getProperty("line.separator"));
//						bufferedWriter.write("#SBATCH -n 4" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH --time=8-00:00:00" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH --mail-type=ALL" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH --mail-user=burcak@ceng.metu.edu.tr" + System.getProperty("line.separator"));
						bufferedWriter.write(System.getProperty("line.separator"));
						bufferedWriter.write("which java" + System.getProperty("line.separator"));
						bufferedWriter.write("echo \"SLURM_NODELIST $SLURM_NODELIST\"" + System.getProperty("line.separator"));
						bufferedWriter.write("echo \"NUMBER OF CORES $SLURM_NTASKS\"" + System.getProperty("line.separator"));
						bufferedWriter.write(System.getProperty("line.separator"));
						
						sbatch_calls_BufferedWriter.write("sbatch" + "\t" + fileName + System.getProperty("line.separator"));
						break;
					}
					
					case TRUBA_FAST:	{					
						bufferedWriter.write("#!/bin/bash" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -M truba" + System.getProperty("line.separator"));
						
//						if(withorWithoutGCandMapability.isGenerateRandomDataModeWithMapabilityandGc()){	
//							bufferedWriter.write("#SBATCH -p mid1" + System.getProperty("line.separator"));
//						}else if (withorWithoutGCandMapability.isGenerateRandomDataModeWithoutMapabilityandGc()){
//							bufferedWriter.write("#SBATCH -p mid2" + System.getProperty("line.separator"));
//						}
						
						//One core jobs can be sent to single and mercan.
						//bufferedWriter.write("#SBATCH -p mercan" + System.getProperty("line.separator"));
						
						//levrekv2 can be faster than mercan
						bufferedWriter.write("#SBATCH -p levrekv2" + System.getProperty("line.separator"));
						//bufferedWriter.write("#SBATCH -p orkinos" + System.getProperty("line.separator"));
						
						bufferedWriter.write("#SBATCH -A botlu" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -J " + GENERAL_JOBNAME + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -N 1" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH -n 8" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH --time=8-00:00:00" + System.getProperty("line.separator"));
						
						
						//We don't want to get email for each of the 1000 GLANET Runs begins and ends but fails
						bufferedWriter.write("#SBATCH --mail-type=FAIL" + System.getProperty("line.separator"));
						bufferedWriter.write("#SBATCH --mail-user=burcak@ceng.metu.edu.tr" + System.getProperty("line.separator"));
						
						//newly added
						bufferedWriter.write("#SBATCH --array=" + startingRunNumber + "-" + (endingRunNumber-1) + System.getProperty("line.separator"));
						
						
						bufferedWriter.write(System.getProperty("line.separator"));
						bufferedWriter.write("which java" + System.getProperty("line.separator"));
						bufferedWriter.write("echo \"SLURM_NODELIST $SLURM_NODELIST\"" + System.getProperty("line.separator"));
						bufferedWriter.write("echo \"NUMBER OF CORES $SLURM_NTASKS\"" + System.getProperty("line.separator"));
						bufferedWriter.write("echo \"SLURM_ARRAY_TASK_ID $SLURM_ARRAY_TASK_ID\"" + System.getProperty("line.separator"));
				
						bufferedWriter.write(System.getProperty("line.separator"));
						
						sbatch_calls_BufferedWriter.write("sbatch" + "\t" + fileName + System.getProperty("line.separator"));
						break;
					}

					default: 
						break;
					
				}//End of SWITCH for writing header lines
						
				//rootCommand for GLANET.jar call
				rootCommand = "java -Xms4G -Xmx4G -jar \"" + args[0] + "\" -c -g \"" + args[1] + "\" -i \"" + args[1] + Commons.DDE + System.getProperty("file.separator") + "DDEData" + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + Commons.DDE_RUN;

				switch(operatingSystem){
					
					case TRUBA_FAST:{
						
						String command = null;
						
						//Using -tf -histone
						command = rootCommand + "$SLURM_ARRAY_TASK_ID.txt\" " + "-f0 " + "-tf " + "-histone " + "-e " + "-ewz ";
						
						// Using UDL lead to write errors since each experiment tries to write under the same directory with same file names.
						//command = rootCommand + "$SLURM_ARRAY_TASK_ID.txt\" " + "-f0 " + "-udl -udldf0exc -udlinput " + "\"" + args[1] + Commons.DATA + System.getProperty("file.separator") + "demo_input_data" + System.getProperty("file.separator") +  "UserDefinedLibrary" + System.getProperty("file.separator") + "DDCE_UserDefinedLibraryInputFile.txt\"" + " -e " + "-ewz ";
					
						
						switch(generateRandomDataMode){
						
							case GENERATE_RANDOM_DATA_WITH_GC_CONTENT:	
								command = command + " -wgc ";
								break;
								
							case GENERATE_RANDOM_DATA_WITH_MAPPABILITY:	
								command = command + " -wm ";
								break;
						
							case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT:	
								command = command + " -wgcm ";
								break;

							case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT:	
								command = command + " -wogcm ";
								break;
								
						}//End of switch adding parameter for generateRandomDataMode
						
						
						switch(isochoreFamilyMode){
						
							case DO_USE_ISOCHORE_FAMILY:	
								command = command + " -wif ";
								break;
								
							case DO_NOT_USE_ISOCHORE_FAMILY:	
								command = command + " -woif ";
								break;
						
						}//End of switch adding parameter for generateRandomDataMode
						
						switch(associationMeasureType){
						
							case EXISTENCE_OF_OVERLAP:	
								command = command + " -eoo ";
								break;
								
							case NUMBER_OF_OVERLAPPING_BASES:	
								command = command + " -noob ";
								break;
						
						}//End of switch adding parameter for generateRandomDataMode


						bufferedWriter.write( command + " -s 10000 -se 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + generateRandomDataMode.convertEnumtoShortString() + "_" + isochoreFamilyMode.convertEnumtoShortString() + "_" + associationMeasureType.convertEnumtoShortString() + Commons.DDE_RUN + "$SLURM_ARRAY_TASK_ID" + System.getProperty("line.separator"));

					}
					break;
						
					case WINDOWS:
					case LINUX:
					case TURENG_MACHINE:
					case TRUBA:
					{

						for( int i = startingRunNumber; i < endingRunNumber; i++){

							
							String command = null;
							
							command = rootCommand + i + ".txt\" " + "-f0 " + "-udl -udldf0exc -udlinput " + "\"" + args[1] + Commons.DATA + System.getProperty("file.separator") + "demo_input_data" + System.getProperty("file.separator") +  "UserDefinedLibrary" + System.getProperty("file.separator") + "DDCE_UserDefinedLibraryInputFile.txt\"" + " -e " + "-ewz ";							
								
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

							bufferedWriter.write(command + " -s 10000 -se 10000 -dder -j " + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + generateRandomDataMode.convertEnumtoShortString() + isochoreFamilyMode.convertEnumtoShortString() + associationMeasureType.convertEnumtoShortString()+  Commons.DDE_RUN + i + System.getProperty( "line.separator"));
							

						}// End of FOR each simulation
					}
					break;
						
						
					
				}//End of SWITCH for writing GLANET.jar calls
							
				
				//Add Line Separator
				bufferedWriter.write(System.getProperty("line.separator"));
				sbatch_calls_BufferedWriter.write(System.getProperty("line.separator"));
				
				//Adding Exit line
				switch(operatingSystem){
				
					case TRUBA:
					case TRUBA_FAST:
						bufferedWriter.write("exit");
						break;
						
					default: 
						break;
				
				}//End of SWITCH for adding exit line.				
				
				//Close bufferedWriters
				bufferedWriter.close();
				fileWriter.close();
				
				sbatch_calls_BufferedWriter.close();
				sbatch_calls_FileWriter.close();
				
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
	 * args[6] = Data Driven Experiment Operating System e.g.: Windows or Linux
	 * 
	 * Example:
	 * 
	 * args[0]	-->	"C:\Users\Burcak\Google Drive\GLANET\GLANET.jar"
	 * args[1]	-->	"C:\Users\Burcak\Google Drive"
	 * args[2]  --> GM12878, K562
	 * args[3] 	-->	NonExpressingGenes, ExpressingGenes
	 * args[4]  --> TPM
	 * args[5]	-->	1000
	 * args[6] ---> "Linux"
	 */
	public static void main( String[] args) {
		
		String glanetFolder = args[1];
		String dataDrivenExperimentFolder = glanetFolder + Commons.DDE + System.getProperty("file.separator");
		String dataDrivenExperimentScriptFolder =  dataDrivenExperimentFolder +  Commons.SCRIPT_FILES + System.getProperty("file.separator");
		
		DataDrivenExperimentCellLineType cellLineType = DataDrivenExperimentCellLineType.convertStringtoEnum(args[2]);
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(args[3]);
		
		
		/*************************************************************************************************/
		/******************************Get the tpmValues starts*******************************************/
		/*************************************************************************************************/
		//For expressingGenes tpmValues are sorted in descending order
		SortedMap<DataDrivenExperimentTPMType,Float> expGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		//For nonExpressingGenes tpmValues are sorted in ascending order
		SortedMap<DataDrivenExperimentTPMType,Float> nonExpGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		
		Set<DataDrivenExperimentTPMType> tpmTypes = null;
		
		switch(geneType){
		
			case EXPRESSING_PROTEINCODING_GENES:
				DataDrivenExperimentCommon.fillTPMType2TPMValueMap(dataDrivenExperimentFolder,cellLineType,geneType,expGenesTPMType2TPMValueSortedMap);
				tpmTypes = expGenesTPMType2TPMValueSortedMap.keySet();
				
				break;
				
			case NONEXPRESSING_PROTEINCODING_GENES:
				DataDrivenExperimentCommon.fillTPMType2TPMValueMap(dataDrivenExperimentFolder,cellLineType,geneType,nonExpGenesTPMType2TPMValueSortedMap);
				tpmTypes = nonExpGenesTPMType2TPMValueSortedMap.keySet();
				break;
				
		}//End of SWITCH for geneType
		/*************************************************************************************************/
		/******************************Get the tpmValues ends*********************************************/
		/*************************************************************************************************/

		
		//10 August 2016
		//startingRunNumber is inclusive
		//endingRunNumber is exclusive
		int startingRunNumber= Integer.parseInt(args[4]); 
		int endingRunNumber= Integer.parseInt(args[5]);;
		
		
		GenerateRandomDataMode withGC = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_GC_CONTENT;
		GenerateRandomDataMode withoutGCandMapability = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
		
		//Operating System where the Data Driven Experiment will run
		DataDrivenExperimentOperatingSystem operatingSystem = DataDrivenExperimentOperatingSystem.convertStringtoEnum(args[6]);
			
		DataDrivenExperimentTPMType tpmType = null;
		
		ToolType toolType = ToolType.convertStringtoEnum(args[7]);
		

		try{
			
			
			switch(toolType){
			
				case GLANET:
					
					//*************************************************************************************************************//
					//****************************************************SIMULATIONS**********************************************//
					//**********************************************DATA DRIVEN EXPERIMENT*****************************************//
					//**************************************************GLANET RUNS************************************************//
					//*************************************************************************************************************//
					for(Iterator<DataDrivenExperimentTPMType> itr = tpmTypes.iterator();itr.hasNext();){
						
						tpmType = itr.next();
						
						for(AssociationMeasureType associationMeasureType: AssociationMeasureType.values()){
								
								//18 August 2016 starts
								for(GenerateRandomDataMode generateRandomDataMode: GenerateRandomDataMode.values()){
									
									for(IsochoreFamilyMode isochoreFamilyMode : IsochoreFamilyMode.values()){
										
										writeGLANETRuns( 
												startingRunNumber, 
												endingRunNumber,
												cellLineType,
												geneType,
												operatingSystem,
												tpmType, 
												associationMeasureType,
												generateRandomDataMode,
												isochoreFamilyMode,
												args,
												dataDrivenExperimentScriptFolder);

										
									}//End of for each isochoreFamilyMode
									
								}//End of for each generateRandomDataMode
								//18 August 2016 ends
								
							
						}//End of For each associationMeasureType
							
		
					}//End of FOR each tpmValue
					//*************************************************************************************************************//
					//****************************************************SIMULATIONS**********************************************//
					//**********************************************DATA DRIVEN EXPERIMENT*****************************************//
					//**************************************************GLANET RUNS************************************************//
					//*************************************************************************************************************//
					break;
					
				case GAT:
					
				
					
					//*************************************************************************************************************//
					//****************************************************SIMULATIONS**********************************************//
					//**********************************************DATA DRIVEN EXPERIMENT*****************************************//
					//**************************************************GAT RUNS***************************************************//
					//*************************************************************************************************************//
					for(Iterator<DataDrivenExperimentTPMType> itr = tpmTypes.iterator();itr.hasNext();){
						
						tpmType = itr.next();
						
						AssociationMeasureType associationMeasureType = AssociationMeasureType.EXISTENCE_OF_OVERLAP;
						//AssociationMeasureType associationMeasureType = AssociationMeasureType.NUMBER_OF_OVERLAPPING_BASES;
						

						//***************************************WITH_MAPPABILITY_AND_GC_CONTENT************************************//						
						// With GC and Mapability
						writeGATRuns(
								startingRunNumber, 
								endingRunNumber,
								cellLineType,
								geneType,
								operatingSystem,
								tpmType, 
								associationMeasureType,
								withGC,
								args,
								dataDrivenExperimentFolder,
								dataDrivenExperimentScriptFolder);
						//***************************************WITHOUT_MAPPABILITY_AND_GC_CONTENT************************************//

						
						//***************************************WITHOUT_MAPPABILITY_AND_GC_CONTENT************************************//						
						// Without GC and Mapability
						writeGATRuns(
								startingRunNumber, 
								endingRunNumber,
								cellLineType,
								geneType,
								operatingSystem,
								tpmType, 
								associationMeasureType,
								withoutGCandMapability,
								args,
								dataDrivenExperimentFolder,
								dataDrivenExperimentScriptFolder);
						//***************************************WITHOUT_MAPPABILITY_AND_GC_CONTENT************************************//
							
							
					}//End of FOR each tpmValue
					//*************************************************************************************************************//
					//****************************************************SIMULATIONS**********************************************//
					//**********************************************DATA DRIVEN EXPERIMENT*****************************************//
					//**************************************************GAT RUNS***************************************************//
					//*************************************************************************************************************//

					
					break;
			} //End of ToolType
			

		
		}catch( IOException e){
			e.printStackTrace();
		}

	}
}
