
package datadrivenexperiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;
import ui.GlanetRunner;
import common.Commons;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentDnaseOverlapExclusionType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTPMType;
import enumtypes.ToolType;

/**
 * @author Burçak Otlu
 * @date Oct 25, 2015
 * @project Glanet 
 *
 */
public class AnalysisForEachGLANETDDERun {
	
	final static Logger logger = Logger.getLogger(AnalysisForEachGLANETDDERun.class);

	public static int readandWriteForEachGLANETDDEDataFile(
			String eachGLANETDDEDataFileName,
			BufferedWriter bufferedWriter,
			int lessThanThreshold){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine = null;
		
		int indexofFirstTab = -1;
		int indexofSecondTab = -1;
		
		int start, end, length;
		
		List<Integer> lengthList = new ArrayList<Integer>();
		
		int size;
		float median;
		
		int numberofIntervalsWithLengthLessThanThreshold = 0;
		
		try{
			fileReader = new FileReader(eachGLANETDDEDataFileName);
			bufferedReader = new BufferedReader(fileReader);

			while( ( strLine = bufferedReader.readLine()) != null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = (indexofFirstTab>=0) ? strLine.indexOf('\t',indexofFirstTab+1) : -1;
			
				start = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				end = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				
				length = end - start +1;
				lengthList.add(length);
				
				if (length <= lessThanThreshold){
					numberofIntervalsWithLengthLessThanThreshold++;
				}
				
				
			}//End of WHILE
			
			//Sort in Ascending Order
			lengthList.sort(Comparator.naturalOrder());
			
			size = lengthList.size();
			
			if (size%2 == 0){
				median = (lengthList.get(size/2-1) + lengthList.get(size/2))/2;
			}else{
				median = lengthList.get(size/2);
			}	
			
			bufferedWriter.write("For " + eachGLANETDDEDataFileName + " median is " + "\t"+ median + "\t" + "NumberofIntervalsWithLengthLessThan" + "\t" + lessThanThreshold + "\t" +numberofIntervalsWithLengthLessThanThreshold + System.getProperty("line.separator"));
			

			bufferedReader.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())
			logger.error( e.toString());
		}

		return numberofIntervalsWithLengthLessThanThreshold;
	
		
	}
	
	
	public static void analyzeEachGLANETDDERunData(
			String dataDrivenExperimentDataFolder,
			DataDrivenExperimentCellLineType cellLineType,
			DataDrivenExperimentGeneType geneType, 
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType,
			Set<DataDrivenExperimentTPMType> tpmTypes, 
			Collection<Float> tpmValues,
			int numberofSimulations,
			int numberofIntervalsInEachSimulation,
			BufferedWriter bufferedWriter,
			int lessThanThreshold) throws IOException{
		
		
		//Get the GLANET DDE Data Directory
		File GLANETDDEDataDirectory = new File(dataDrivenExperimentDataFolder);
		
		String fileName = null;
		String fileAbsolutePath = null;
		
		String eachGLANETDDEDataFileName = null;
		
		float totalNumberofIntervalsLessThanThreshold = 0f;
		int numberofGLANETDDERuns = 0;

		if( GLANETDDEDataDirectory.exists() && GLANETDDEDataDirectory.isDirectory()){
			
			//Get each cellLineType_geneType _tpmType specific GLANET DDE Run Data file
			for( File eachGLANETDDEDataFile : GLANETDDEDataDirectory.listFiles()){
				
				fileName = eachGLANETDDEDataFile.getName();
				fileAbsolutePath = eachGLANETDDEDataFile.getAbsolutePath();
				
				if( fileName.startsWith(cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString()) &&
					fileName.contains(dnaseOverlapExclusionType.convertEnumtoString())	){
					
					eachGLANETDDEDataFileName = fileAbsolutePath;
					
					totalNumberofIntervalsLessThanThreshold = totalNumberofIntervalsLessThanThreshold + readandWriteForEachGLANETDDEDataFile(eachGLANETDDEDataFileName,bufferedWriter,lessThanThreshold);
					
					numberofGLANETDDERuns++;
					
				}//End of IF get the right File

				
			}//End of for each GLANET DDE Data File
			
		}//End of IF GLANETDDEDataDirectory exists
		
		bufferedWriter.write(cellLineType.convertEnumtoString() + "\t" + geneType.convertEnumtoString() + "\t" + dnaseOverlapExclusionType.convertEnumtoString() + "\t" + "AverageNumberofIntervalsWithLengthLessThan " + lessThanThreshold + " bp is" + "\t" + totalNumberofIntervalsLessThanThreshold/numberofGLANETDDERuns + System.getProperty("line.separator"));

	}

	// TODO Read each GLANET DDE Data file
	// TODO sort the intervals in ascending order get the median interval length
	// TODO output it
	public static void main(String[] args) {
		
		String glanetFolder	= args[0];
		
		String dataDrivenExperimentFolder = glanetFolder + Commons.DDE + System.getProperty("file.separator");
		String dataDrivenExperimentDataFolder	= glanetFolder + Commons.DDE + System.getProperty( "file.separator") + Commons.DDE_DATA + System.getProperty( "file.separator");
		
		DataDrivenExperimentCellLineType cellLineType = DataDrivenExperimentCellLineType.convertStringtoEnum(args[1]);
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(args[2]);
		
		// Number of GLANET DDE Runs
		int numberofSimulations =  Integer.parseInt(args[3]);
		// Number of intervals in each GLANET DDE Run
		int numberofIntervalsInEachSimulation = Integer.parseInt(args[4]);

		int lessThanThreshold = Integer.parseInt(args[5]);
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			
			fileWriter = FileOperations.createFileWriter(dataDrivenExperimentFolder  + cellLineType.convertEnumtoString() + "_" +  Commons.ANALYSIS_OF_DDE_DATA_FILE);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			/*************************************************************************************************/
			/******************************Get the tpmValues starts*******************************************/
			/*************************************************************************************************/
			//For expressingGenes tpmValues are sorted in descending order
			SortedMap<DataDrivenExperimentTPMType,Float> expGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
			//For nonExpressingGenes tpmValues are sorted in ascending order
			SortedMap<DataDrivenExperimentTPMType,Float> nonExpGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
			
			Set<DataDrivenExperimentTPMType> tpmTypes = null;
			Collection<Float> tpmValues = null;
			
			switch(geneType){
			
				case EXPRESSING_PROTEINCODING_GENES:
					DataDrivenExperimentCommon.fillTPMType2TPMValueMap(glanetFolder,cellLineType,geneType,expGenesTPMType2TPMValueSortedMap,ToolType.GLANET);
					tpmTypes = expGenesTPMType2TPMValueSortedMap.keySet();
					tpmValues = expGenesTPMType2TPMValueSortedMap.values();
					break;
					
				case NONEXPRESSING_PROTEINCODING_GENES:
					DataDrivenExperimentCommon.fillTPMType2TPMValueMap(glanetFolder,cellLineType,geneType,nonExpGenesTPMType2TPMValueSortedMap,ToolType.GLANET);
					tpmTypes = nonExpGenesTPMType2TPMValueSortedMap.keySet();
					tpmValues = nonExpGenesTPMType2TPMValueSortedMap.values();
					break;
					
			}//End of SWITCH for geneType
			/*************************************************************************************************/
			/******************************Get the tpmValues ends*********************************************/
			/*************************************************************************************************/
			
			
			
			
			/*************************************************************************************************/
			/*************************************Analyze starts**********************************************/
			/*************************************************************************************************/
			for(DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType : DataDrivenExperimentDnaseOverlapExclusionType.values() ){
				
				if (geneType.isNonExpressingProteinCodingGenes() && dnaseOverlapExclusionType.isTakeTheLongestRemainingInterval()){
					
					analyzeEachGLANETDDERunData(
							dataDrivenExperimentDataFolder,
							cellLineType,
							geneType,
							dnaseOverlapExclusionType,
							tpmTypes, 
							tpmValues, 
							numberofSimulations, 
							numberofIntervalsInEachSimulation, 
							bufferedWriter,
							lessThanThreshold);
					
				}//End of IF
				
			}//End of FOR each DataDrivenExperimentDnaseOverlapExclusionType
			
		
			/*************************************************************************************************/
			/*************************************Analyze ends************************************************/
			/*************************************************************************************************/
	
			
			//Close the bufferedWriter
			bufferedWriter.close();

		} catch (IOException e) {
			
			if( GlanetRunner.shouldLog())
				logger.error( e.toString());
			
		}
		
		
	}

}
