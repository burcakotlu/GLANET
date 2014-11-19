/**
 * @author Burcak Otlu
 *	It lasts 8 minutes.
 * 
 */

package annotation;

import gnu.trove.iterator.TObjectShortIterator;
import gnu.trove.iterator.TShortObjectIterator;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.TShortObjectMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;

import common.Commons;

public class WriteAllPossibleNamesandUnsortedFilesWithNumbers {
	
	
	
	
	public static void readKeggPathwayNames(String dataFolder,List<String> keggPathwayNameList, Map<String,Integer> keggPathwayName2KeggPathwayNumberMap,Map<Integer,String> keggPathwayNumber2KeggPathwayNameMap, String inputFileName){

		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofTab 	= 0;
		int indexofColon = 0;
		
		String keggPathwayName;
		int keggPathwayNumber = 1;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
//				example line
//				path:hsa00010	hsa:10327	reverse

				indexofTab = strLine.indexOf('\t');				
				keggPathwayName = strLine.substring(0,indexofTab);
				
				indexofColon = keggPathwayName.indexOf(':');				
				keggPathwayName = keggPathwayName.substring(indexofColon+1);				
				
				if(!(keggPathwayNameList.contains(keggPathwayName))){					
					keggPathwayNameList.add(keggPathwayName);	
					
					keggPathwayName2KeggPathwayNumberMap.put(keggPathwayName, keggPathwayNumber);
					keggPathwayNumber2KeggPathwayNameMap.put(keggPathwayNumber, keggPathwayName);
					keggPathwayNumber++;
										
				}													
			} // End of While			
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
							
	}
	
	
	
	public static void writeNames(String dataFolder,TObjectShortMap<String> name2NumberMap, String outputDirectoryName, String outputFileName){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
		
			for ( TObjectShortIterator<String> it = name2NumberMap.iterator(); it.hasNext(); ) {
				   it.advance();
				   bufferedWriter.write(it.key()+ System.getProperty("line.separator"));		    
				}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}
	

	
	
	
	
	
	public static void writeNames(String dataFolder,List<String> nameList, String outputDirectoryName, String outputFileName){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		
		try {
			
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
		
			for(int i = 0; i<nameList.size() ;i++){
								
				bufferedWriter.write(nameList.get(i)+ System.getProperty("line.separator"));
				bufferedWriter.flush();				
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}
	
	
	
	
	//Write String2ShortMap starts
	public static void writeMapsString2Short(String dataFolder,TObjectShortMap<String> name2NumberMap, String outputDirectoryName, String outputFileName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			
			for ( TObjectShortIterator<String> it = name2NumberMap.iterator(); it.hasNext(); ) {
			   it.advance();
			   bufferedWriter.write(it.key() + "\t" + it.value() + System.getProperty("line.separator"));		    
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}	
	//Write String2ShortMap ends
	
	
	//Write Short2StringMap starts
	public static void writeMapsShort2String(String dataFolder,TShortObjectMap<String> number2NameMap, String outputDirectoryName, String outputFileName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for ( TShortObjectIterator<String> it = number2NameMap.iterator(); it.hasNext(); ) {
			   it.advance();
			   bufferedWriter.write(it.key() + "\t" + it.value() + System.getProperty("line.separator"));		    
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}	
	//Write Short2StringMap ends
	
	public static void writeMapsString2Integer(String dataFolder,Map<String,Integer> cellLineName2CellLineNumberMap, String outputDirectoryName, String outputFileName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Map.Entry<String,Integer> entry :cellLineName2CellLineNumberMap.entrySet()){
				bufferedWriter.write(entry.getKey() + "\t" + entry.getValue() + System.getProperty("line.separator"));
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}
	
	
	

	
	public static void writeMapsInteger2String(String dataFolder,Map<Integer,String> cellLineName2CellLineNumberMap, String outputDirectoryName, String outputFileName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Map.Entry<Integer,String> entry :cellLineName2CellLineNumberMap.entrySet()){
				bufferedWriter.write(entry.getKey() + "\t" + entry.getValue() + System.getProperty("line.separator"));
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
	}
	
	


	
	


	
	
	
	
	
	public static void writeAllPossibleKeggPathwayNames(String dataFolder){
		
		List<String> keggPathwayNameList = new ArrayList<String>();	
		Map<String,Integer> keggPathwayName2KeggPathwayNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> keggPathwayNumber2KeggPathwayNameMap = new HashMap<Integer,String>();

		
		readKeggPathwayNames(dataFolder,keggPathwayNameList,keggPathwayName2KeggPathwayNumberMap,keggPathwayNumber2KeggPathwayNameMap,Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE);
		writeNames(dataFolder,keggPathwayNameList,Commons.WRITE_ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_KEGG_PATHWAY_NAMES_OUTPUT_FILENAME);		
		writeMapsString2Integer(dataFolder,keggPathwayName2KeggPathwayNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_KEGGPATHWAYNAME_2_KEGGPATHWAYNUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,keggPathwayNumber2KeggPathwayNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_KEGGPATHWAYNUMBER_2_KEGGPATHWAYNAME_OUTPUT_FILENAME);

	}
	
	//userDefinedGeneSetName2UserDefinedGeneSetNumberMap is already full
	//userDefinedGeneSetNumber2UserDefinedGeneSetNameMap is already full
	//Write them to name2NumberMap and number2NameMap
	public static void writeAllPossibleUserDefinedGeneSetNames(
			String dataFolder,
			TObjectShortMap<String> userDefinedGeneSetName2UserDefinedGeneSetNumberMap,
			TShortObjectMap<String> userDefinedGeneSetNumber2UserDefinedGeneSetNameMap){
		
		writeNames(dataFolder,userDefinedGeneSetName2UserDefinedGeneSetNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_USERDEFINEDGENESET_NAMES_OUTPUT_FILENAME);		
		writeMapsString2Short(dataFolder,userDefinedGeneSetName2UserDefinedGeneSetNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_USERDEFINEDGENESETNAME_2_USERDEFINEDGENESETNUMBER_OUTPUT_FILENAME);
		writeMapsShort2String(dataFolder,userDefinedGeneSetNumber2UserDefinedGeneSetNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_USERDEFINEDGENESETNUMBER_2_USERDEFINEDGENESETNAME_OUTPUT_FILENAME);
	
	}

	
	//@todo writeAllPossibleUserDefinedGeneSetNames ends
	
	
	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2]	--->	Input File Format	
	//			--->	default	Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE	
	//args[3]	--->	Annotation, overlap definition, number of bases, default 1
	//args[4]	--->	Enrichment parameter
	//			--->	default	Commons.DO_ENRICH
	//			--->			Commons.DO_NOT_ENRICH	
	//args[5]	--->	Generate Random Data Mode
	//			--->	default	Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	//			--->			Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT	
	//args[6]	--->	multiple testing parameter, enriched elements will be decided and sorted with respest to this parameter
	//			--->	default Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE
	//			--->			Commons.BONFERRONI_CORRECTED_P_VALUE
	//args[7]	--->	Bonferroni Correction Significance Level, default 0.05
	//args[8]	--->	Benjamini Hochberg FDR, default 0.05
	//args[9]	--->	Number of permutations, default 5000
	//args[10]	--->	Dnase Enrichment
	//			--->	default Commons.DO_NOT_DNASE_ENRICHMENT
	//			--->	Commons.DO_DNASE_ENRICHMENT
	//args[11]	--->	Histone Enrichment
	//			--->	default	Commons.DO_NOT_HISTONE_ENRICHMENT
	//			--->			Commons.DO_HISTONE_ENRICHMENT
	//args[12]	--->	Transcription Factor(TF) Enrichment 
	//			--->	default	Commons.DO_NOT_TF_ENRICHMENT
	//			--->			Commons.DO_TF_ENRICHMENT
	//args[13]	--->	KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_KEGGPATHWAY_ENRICHMENT
	//args[14]	--->	TF and KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	//args[15]	--->	TF and CellLine and KeggPathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	//args[16]	--->	RSAT parameter
	//			--->	default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//			--->			Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//args[17]	--->	job name example: ocd_gwas_snps 
	//args[18]	--->	writeGeneratedRandomDataMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	//			--->			Commons.WRITE_GENERATED_RANDOM_DATA
	//args[19]	--->	writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//args[20]	--->	writePermutationBasedAnnotationResultMode checkBox
	//			---> 	default	Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT			
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String dataFolder = glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
	
	
		//Write all possible kegg pathway names		
		//Using pathway_hsa.list under C:\eclipse_ganymede\workspace\Doktora1\src\keggpathway\ncbigenes\input_output
		WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeAllPossibleKeggPathwayNames(dataFolder);
		
		
	}

}
