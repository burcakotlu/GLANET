/**
 * 
 */
package rsat;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jaxbxjctool.NCBIEutils;
import remap.Remap;
import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Burçak Otlu
 * @date Nov 28, 2016
 * @project Glanet 
 *
 */
public class GeneAnnotationForPostAnalysisRSAResults {
	
	
	public static void convert_FromLatestAssembly_ToGRCh37p13(
			String dataFolder,
			String inputOutputDataFolder,
			String PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly_FileName,
			String PostAnalysis_RSA_chrName_1BasedCoordinateGRCh37p13_FileName,
			Boolean fillMap,
			Map<String,String> latestAssemblyCoordinates2GRCh37p13CoordinatesMap){
		
		//Get Latest Assembly right now it is GRCh38.p7
		String latestAssembyNameReturnedByNCBIEutils = NCBIEutils.getLatestAssemblyNameReturnedByNCBIEutils();
		
		Remap.convertGivenInputCoordinatesFromSourceAssemblytoTargetAssemblyUsingRemap(
				dataFolder,
				inputOutputDataFolder, 		
				PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly_FileName, 
				PostAnalysis_RSA_chrName_1BasedCoordinateGRCh37p13_FileName, 
				latestAssembyNameReturnedByNCBIEutils, 
				"GRCh37.p13",
				false,
				fillMap,
				latestAssemblyCoordinates2GRCh37p13CoordinatesMap);

		
	}

	public static void annotateWithRefSeqGenes(){
		
		//Left here
		
		/*****************************************************************************************/
		/************************* GIVEN INPUT DATA starts ***************************************/
		/*****************************************************************************************/
		inputFileName = givenInputDataFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE_0BASED_START_END_GRCh37_p13;

		TIntObjectMap<FileWriter> chrNumber2FileWriterMap = new TIntObjectHashMap<FileWriter>();
		TIntObjectMap<BufferedWriter> chrNumber2BufferedWriterMap = new TIntObjectHashMap<BufferedWriter>();

		// Prepare chromosome based partitioned input interval files to be searched for
		// Create Buffered Writers for writing chromosome based input files
		FileOperations.createChromBaseSearchInputFiles(outputFolder, chrNumber2FileWriterMap, chrNumber2BufferedWriterMap);

		// Partition the input file into 24 chromosome based input files
		partitionSearchInputFilePerChromName(inputFileName, chrNumber2BufferedWriterMap);

		// Close Buffered Writers
		closeBufferedWriterList(chrNumber2FileWriterMap, chrNumber2BufferedWriterMap);
		/*****************************************************************************************/
		/************************* GIVEN INPUT DATA ends *****************************************/
		/*****************************************************************************************/
		
	}

	public static void readPostAnalysisRSAResults(
			String dataFolder,
			String outputFolder,
			String inputOutputDataFolder,
			String RSAPostAnalysisFileName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		String PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly_FileName = "PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly.txt";
		BufferedWriter bufferedWriter = null;
		
		String PostAnalysis_RSA_chrName_1BasedCoordinateGRCh37p13_FileName = "PostAnalysis_RSA_chrName_1BasedCoordinateInGRCh37p13.txt";
		
		String strLine = null;
		int indexofFirstTab = -1;
		int indexofSecondTab = -1;
				
		String chrName_1BasedCoordinateInLatestAssemly_rsID_TF = null;
		int indexofFirstUnderScore = -1;
		int indexofSecondUnderScore = -1;		
		String chrName = null;
		int _1BasedCoordinateInLatestAssemly = -1;
		
		Map<String,String> _1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map = new HashMap<String,String>();
		String key_chrName_1BasedCoordinate_latestAssembly = null;
		
		//example lines
//		Interesting Finding Number	Chr_Position_rsID_TF	SNP Effect	p_Ref	p_SNP	p_TFExtended
//		1	chr10_110567718_rs148267784_TBP	 SNP has a worse match (disrupting effect).	1E-5	4.9E-5	1E-5
//		2	chr10_110567718_rs148267784_SP1	 SNP has a worse match (disrupting effect).	9.5E-7	5.6E-6	9.5E-7
		
		try {
			fileReader = FileOperations.createFileReader(inputOutputDataFolder + RSAPostAnalysisFileName);		
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(inputOutputDataFolder +  PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly_FileName);
			bufferedWriter = new BufferedWriter(fileWriter);
						
			//Skip header line
			strLine = bufferedReader.readLine();
		
			while ((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf("\t");
				indexofSecondTab = strLine.indexOf("\t",indexofFirstTab+1);
				
				chrName_1BasedCoordinateInLatestAssemly_rsID_TF = strLine.substring(indexofFirstTab+1,indexofSecondTab);
				
				indexofFirstUnderScore = chrName_1BasedCoordinateInLatestAssemly_rsID_TF.indexOf("_");
				indexofSecondUnderScore = chrName_1BasedCoordinateInLatestAssemly_rsID_TF.indexOf("_",indexofFirstUnderScore+1);
				
				chrName = chrName_1BasedCoordinateInLatestAssemly_rsID_TF.substring(0, indexofFirstUnderScore);
				_1BasedCoordinateInLatestAssemly = Integer.parseInt(chrName_1BasedCoordinateInLatestAssemly_rsID_TF.substring(indexofFirstUnderScore+1,indexofSecondUnderScore));
				
				key_chrName_1BasedCoordinate_latestAssembly = new String(chrName + "\t" + _1BasedCoordinateInLatestAssemly);
				
				if (!_1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map.containsKey(key_chrName_1BasedCoordinate_latestAssembly)){
					_1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map.put(key_chrName_1BasedCoordinate_latestAssembly,null);
					
					//Which ones to write?
					//Do not write duplicates.
					bufferedWriter.write(chrName + "\t" + _1BasedCoordinateInLatestAssemly + System.getProperty("line.separator"));
				}//End of IF
				
				
			}//End of WHILE
			
			//Close 
			bufferedReader.close();
			bufferedWriter.close();
			
			
			//Downlift to GRCh37.p13
			convert_FromLatestAssembly_ToGRCh37p13(
					dataFolder,
					inputOutputDataFolder,
					PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly_FileName,
					PostAnalysis_RSA_chrName_1BasedCoordinateGRCh37p13_FileName,
					true,
					_1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map);
			
		
			
			//gene annotation starts
			//Annotate with genes			
			annotateWithRefSeqGenes(_1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map.values());
			
			

			//gene annotation ends
			
			//Augment RSAPostAnalysisFileName with gene annotations
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void main(String[] args) {
		
//		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
//
//		// jobName starts
//		String jobName = args[CommandLineArguments.JobName.value()].trim();
//		if( jobName.isEmpty()){
//			jobName = Commons.NO_NAME;
//		}
//		// jobName ends
//
//		//String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
//		String outputFolder = args[CommandLineArguments.OutputFolder.value()];
		
		//For Debug delete later
		String dataFolder = "C:\\Users\\Burçak\\Google Drive\\Data\\";
		String outputFolder = "C:\\Users\\Burçak\\Google Drive\\Output\\rsa_lgmd\\";
		String inputOutputDataFolder = "C:\\Users\\Burçak\\Google Drive\\Output\\rsa_lgmd\\RegulatorySequenceAnalysis\\";
		//For Debug delete later

		
		//Read Post Analysis Data
		//Pay attention that post analysis data in latest assembly return by NCBI eutils
		//Down lift to GRCh37.p13
		readPostAnalysisRSAResults(
				dataFolder,
				outputFolder,
				inputOutputDataFolder, 
				Commons.RSAPostAnalysisFileName);
		
		//Overlap with genes
		//Annotate with Genes
		
		//Write the Gene Annotated Post Analysis of RS 

	}

}
