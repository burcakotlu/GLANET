/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import common.Commons;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentDnaseOverlapExclusionType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTPMType;
import mapability.Mapability;

/**
 * @author burcakotlu
 * 
 * In this class, our aim is to calculate the mappability of randomly generated intervals of Data Driven Computational Experiments Data
 * 
 * Motivation: In this way, we may know the workspace files we need to provide to GAT for mappability.
 *
 */
public class AuxiliaryMappability {
	
	
	public static void readDDCEData(
			String ddceFolder,
			String ddceDataFolder,
			DataDrivenExperimentCellLineType cellLineType, 
			DataDrivenExperimentGeneType geneType, 
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType,
			int numberofRuns,
			Set<DataDrivenExperimentTPMType> tpmTypes){
		
		
		try {
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
			
			FileWriter fileWriter = null;
			BufferedWriter bufferedWriter = null;
			
			
			String strLine = null;
			
			int indexofFirstTab;
			int indexofSecondTab;
			
			String chrName;
			int low;
			int high;
			
			float mappability = 0;
			float accumulatedMappability = 0;
			float avgMappabilityPerDataFile = 0;
			
			int numberofIntervals = 0;
			
			String dataFileName = null;
			
			
			
			for(int i=0; i<numberofRuns; i++){
				
				for(DataDrivenExperimentTPMType TPMType: tpmTypes) {
			
					dataFileName = ddceDataFolder + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + TPMType.convertEnumtoString() + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "_" + Commons.DDE_RUN + i + ".txt";
					
					fileReader = new FileReader(dataFileName);
					bufferedReader = new BufferedReader(fileReader);
					
					fileWriter = new FileWriter(ddceFolder + "Mappabilities.txt", true);
					bufferedWriter = new BufferedWriter(fileWriter);
					
					
					//Initialization
					numberofIntervals = 0;
					accumulatedMappability = 0;
					avgMappabilityPerDataFile = 0;
					
					
					//read file
					while((strLine=bufferedReader.readLine())!=null){
						
						indexofFirstTab = strLine.indexOf("\t");
						indexofSecondTab = strLine.indexOf("\t", indexofFirstTab+1);
						
						chrName = strLine.substring(0,indexofFirstTab);
						low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
						high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
						
						//TODO
						//mappability = Mapability.calculateMappability(chrName,low,high);
						
						accumulatedMappability += mappability;
						numberofIntervals++;
						
						
						
					}//End of WHILE reading one data file
					
					//Close
					bufferedReader.close();
					avgMappabilityPerDataFile = accumulatedMappability/numberofIntervals;
					
					bufferedWriter.write(dataFileName + "\t" +avgMappabilityPerDataFile);
					
				
				}//End of each tpm type
				
				
				
			}//End of FOR each run number 
			
			//Close
			bufferedWriter.close();
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String ddeFolder = args[0];
		String ddeDataFolder = args[0] + Commons.DATA + System.getProperty("file.separator");
		
		DataDrivenExperimentCellLineType cellLineType =  DataDrivenExperimentCellLineType.convertStringtoEnum(args[1]);
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(args[2]);
		DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType = DataDrivenExperimentDnaseOverlapExclusionType.convertStringtoEnum(args[3]);
		
		int numberofRuns = Integer.parseInt(args[4]);
		
		
		/*************************************************************************************************/
		/******************************Get the tpmValues starts*******************************************/
		/*************************************************************************************************/
		//We can not sort on values. There can be more than one values with same tpmValue
		//tpmTypes are sorted in ascending order according to the integer after the word TOP
		
		SortedMap<DataDrivenExperimentTPMType,Float> expGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		SortedMap<DataDrivenExperimentTPMType,Float> nonExpGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		
		Set<DataDrivenExperimentTPMType> tpmTypes = null;
		Collection<Float> tpmValues = null;
		
		switch(geneType){
		
			case EXPRESSING_PROTEINCODING_GENES:
				DataDrivenExperimentCommon.fillTPMType2TPMValueMap(ddeFolder,cellLineType,geneType,expGenesTPMType2TPMValueSortedMap);
				tpmTypes = expGenesTPMType2TPMValueSortedMap.keySet();
				tpmValues = expGenesTPMType2TPMValueSortedMap.values();
				
				break;
				
			case NONEXPRESSING_PROTEINCODING_GENES:
				DataDrivenExperimentCommon.fillTPMType2TPMValueMap(ddeFolder,cellLineType,geneType,nonExpGenesTPMType2TPMValueSortedMap);
				tpmTypes = nonExpGenesTPMType2TPMValueSortedMap.keySet();
				tpmValues = nonExpGenesTPMType2TPMValueSortedMap.values();
				break;
				
		}//End of SWITCH for geneType
		
		
		/*************************************************************************************************/
		/******************************Get the tpmValues ends*********************************************/
		/*************************************************************************************************/

		
		
		readDDCEData(
				ddeFolder,
				ddeDataFolder,
				cellLineType, 
				geneType, 
				dnaseOverlapExclusionType,
				numberofRuns,
				tpmTypes);
		
	}

}
