/**
 * @author Burcak Otlu
 *	It lasts less than a few seconds.
 *	
 *	This class is used for KEGG Pathways
 *	From now on it will be also used for GO Terms (10 FEB 2017)
 * 
 */

package annotation;

import enumtypes.CommandLineArguments;
import enumtypes.GeneOntologyFunction;
import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.iterator.TObjectShortIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import auxiliary.FileOperations;
import common.Commons;

public class WriteAllPossibleNames {
	
	
	public static void readGOTermNames(
			String dataFolder, 			
			List<String> bp_goTermNameList, 
			List<String> mf_goTermNameList,
			List<String> cc_goTermNameList,	
			List<String> goTermNameList,	
			
			TObjectIntMap<String> bp_goTermName2NumberMap, 
			TObjectIntMap<String> mf_goTermName2NumberMap, 
			TObjectIntMap<String> cc_goTermName2NumberMap,	
			TObjectIntMap<String> goTermName2NumberMap,	
			
			TIntObjectMap<String> bp_goTermNumber2NameMap,
			TIntObjectMap<String> mf_goTermNumber2NameMap,
			TIntObjectMap<String> cc_goTermNumber2NameMap,
			TIntObjectMap<String> goTermNumber2NameMap,
			String inputFileName){
		
			String strLine;
	
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
	
			int indexofFirstTab = 0;
			int indexofSecondTab = 0;
			int indexofThirdTab = 0;
			
			String goTermName;
			GeneOntologyFunction goTermOntology;
	
			// Initialize
			int bp_goTermNumber = 1;
			int mf_goTermNumber = 1;
			int cc_goTermNumber = 1;
			int goTermNumber = 1;
			
			try{
				fileReader = new FileReader(dataFolder + inputFileName);
				bufferedReader = new BufferedReader(fileReader);
	
				while((strLine = bufferedReader.readLine()) != null){
					// example line
					// GO:0005829	COMMD3-BMI1	IDA	C

	
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab =  strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab =  strLine.indexOf('\t',indexofSecondTab+1);
					
					goTermName = strLine.substring(0, indexofFirstTab);
					goTermOntology = GeneOntologyFunction.convertStringtoEnum(strLine.substring(indexofThirdTab+1));
					
					if (goTermOntology.isBiologicalProcess()){
						if (!bp_goTermNameList.contains(goTermName)){
							bp_goTermNameList.add(goTermName);
							bp_goTermName2NumberMap.put(goTermName, bp_goTermNumber);
							bp_goTermNumber2NameMap.put(bp_goTermNumber, goTermName);
							bp_goTermNumber++;
						}
					}else if (goTermOntology.isMolecularFunction()){
						if (!mf_goTermNameList.contains(goTermName)){
							mf_goTermNameList.add(goTermName);
							mf_goTermName2NumberMap.put(goTermName, mf_goTermNumber);
							mf_goTermNumber2NameMap.put(mf_goTermNumber, goTermName);
							mf_goTermNumber++;
						}
					}else if (goTermOntology.isCellularComponent()){
						
						if (!cc_goTermNameList.contains(goTermName)){
							cc_goTermNameList.add(goTermName);
							cc_goTermName2NumberMap.put(goTermName, cc_goTermNumber);
							cc_goTermNumber2NameMap.put(cc_goTermNumber, goTermName);
							cc_goTermNumber++;
						}
					}
					
					
					if (!goTermNameList.contains(goTermName)){
						goTermNameList.add(goTermName);
						goTermName2NumberMap.put(goTermName, goTermNumber);
						goTermNumber2NameMap.put(goTermNumber, goTermName);
						goTermNumber++;
					}
					
					
				} // End of While
	
				bufferedReader.close();
				fileReader.close();
	
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
			
		
	}


	public static void readKeggPathwayNames(
			String dataFolder, 
			List<String> keggPathwayNameList,
			TObjectIntMap<String> keggPathwayName2NumberMap, 
			TIntObjectMap<String> keggPathwayNumber2NameMap,
			String inputFileName) {

		String strLine;

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		int indexofTab = 0;
		int indexofColon = 0;

		String keggPathwayName;

		// Initialize
		int keggPathwayNumber = 1;

		try{
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while((strLine = bufferedReader.readLine()) != null){
				// example line
				// path:hsa00010 hsa:10327 reverse

				indexofTab = strLine.indexOf('\t');
				keggPathwayName = strLine.substring(0, indexofTab);

				indexofColon = keggPathwayName.indexOf(':');
				keggPathwayName = keggPathwayName.substring(indexofColon + 1);

				if(!(keggPathwayNameList.contains(keggPathwayName))){
					keggPathwayNameList.add(keggPathwayName);

					keggPathwayName2NumberMap.put(keggPathwayName, keggPathwayNumber);
					keggPathwayNumber2NameMap.put(keggPathwayNumber, keggPathwayName);
					keggPathwayNumber++;

				}
			} // End of While

			bufferedReader.close();
			fileReader.close();

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

	}

	public static void writeNames(String dataFolder, TObjectShortMap<String> name2NumberMap,
			String outputDirectoryName, String outputFileName) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try{

			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			for(TObjectShortIterator<String> it = name2NumberMap.iterator(); it.hasNext();){
				it.advance();
				bufferedWriter.write(it.key() + System.getProperty("line.separator"));
			}

			bufferedWriter.close();

		}catch(IOException e){
			e.printStackTrace();
		}

	}

	// 8 Jul 2015
	public static void writeNames(
			String dataFolder, 
			TObjectIntMap<String> name2NumberMap,
			String outputDirectoryName, 
			String outputFileName) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try{

			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			for(TObjectIntIterator<String> it = name2NumberMap.iterator(); it.hasNext();){
				it.advance();
				bufferedWriter.write(it.key() + System.getProperty("line.separator"));
			}

			bufferedWriter.close();

		}catch(IOException e){
			e.printStackTrace();
		}

	}
	public static void writeNames(String dataFolder, List<String> nameList, String outputDirectoryName,
			String outputFileName) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try{

			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			for(int i = 0; i < nameList.size(); i++){

				bufferedWriter.write(nameList.get(i) + System.getProperty("line.separator"));
				bufferedWriter.flush();
			}

			bufferedWriter.close();

		}catch(IOException e){
			e.printStackTrace();
		}

	}

	// Write String2ShortMap starts
	public static void writeMapsString2Short(String dataFolder, TObjectShortMap<String> name2NumberMap,
			String outputDirectoryName, String outputFileName) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try{
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			for(TObjectShortIterator<String> it = name2NumberMap.iterator(); it.hasNext();){
				it.advance();
				bufferedWriter.write(it.key() + "\t" + it.value() + System.getProperty("line.separator"));
			}

			bufferedWriter.close();

		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// Write String2ShortMap ends
	
	// 8 July 2015
	public static void writeMapsString2Int(
			String dataFolder, 
			TObjectIntMap<String> name2NumberMap,
			String outputDirectoryName, 
			String outputFileName) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try{
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			for(TObjectIntIterator<String> it = name2NumberMap.iterator(); it.hasNext();){
				it.advance();
				bufferedWriter.write(it.key() + "\t" + it.value() + System.getProperty("line.separator"));
			}

			bufferedWriter.close();

		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Write Short2StringMap starts
	// Pay attention first element has elementNumber 1
	public static void writeMapsShort2String(String dataFolder, TShortObjectMap<String> number2NameMap,
			String outputDirectoryName, String outputFileName) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try{
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			for(short i = 1; i <= number2NameMap.size(); i++){
				bufferedWriter.write(i + "\t" + number2NameMap.get(i) + System.getProperty("line.separator"));
			}

			bufferedWriter.close();

		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// Write Short2StringMap ends
	
	// 8 July 2015
	public static void writeMapsInt2String(
			String dataFolder, 
			TIntObjectMap<String> number2NameMap,
			String outputDirectoryName, 
			String outputFileName) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try{
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			for(int i = 1; i <= number2NameMap.size(); i++){
				bufferedWriter.write(i + "\t" + number2NameMap.get(i) + System.getProperty("line.separator"));
			}

			bufferedWriter.close();

		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	//10 FEB 2017
	public static void writeAllPossibleGOTermNames(String dataFolder){
		
		List<String> bp_goTermNameList = new ArrayList<String>();
		List<String> mf_goTermNameList = new ArrayList<String>();
		List<String> cc_goTermNameList = new ArrayList<String>();
		List<String> goTermNameList = new ArrayList<String>();
		
		TObjectIntMap<String> goTermName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> goTermNumber2NameMap = new TIntObjectHashMap<String>();
		
		
		TObjectIntMap<String> bp_goTermName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> bp_goTermNumber2NameMap = new TIntObjectHashMap<String>();
		
		TObjectIntMap<String> mf_goTermName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> mf_goTermNumber2NameMap = new TIntObjectHashMap<String>();
		
		TObjectIntMap<String> cc_goTermName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> cc_goTermNumber2NameMap = new TIntObjectHashMap<String>();
		

		readGOTermNames(
				dataFolder, 				
				bp_goTermNameList, 
				mf_goTermNameList,
				cc_goTermNameList,
				goTermNameList,				
				bp_goTermName2NumberMap, 
				mf_goTermName2NumberMap, 
				cc_goTermName2NumberMap,
				goTermName2NumberMap,				
				bp_goTermNumber2NameMap,
				mf_goTermNumber2NameMap,
				cc_goTermNumber2NameMap,
				goTermNumber2NameMap,
				Commons.GOTERM_GENESYMBOL_EVIDENCECODE_ONTOLOGY_INPUT_FILE);
		
		
		writeNames(
				dataFolder, 
				bp_goTermNameList, 
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_BP_GO_TERMS_NAMES_OUTPUT_FILENAME);
		
		writeNames(
				dataFolder, 
				mf_goTermNameList, 
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_MF_GO_TERMS_NAMES_OUTPUT_FILENAME);
		
		writeNames(
				dataFolder, 
				cc_goTermNameList, 
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_CC_GO_TERMS_NAMES_OUTPUT_FILENAME);

		writeNames(
				dataFolder, 
				goTermNameList, 
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_GO_TERMS_NAMES_OUTPUT_FILENAME);


		FileOperations.writeSortedNumber2NameMap(
				dataFolder, 
				bp_goTermNumber2NameMap,
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_BP_GO_TERMS_NUMBER_2_NAME_OUTPUT_FILENAME);
		
		FileOperations.writeSortedNumber2NameMap(
				dataFolder, 
				mf_goTermNumber2NameMap,
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_MF_GO_TERMS_NUMBER_2_NAME_OUTPUT_FILENAME);
		
		FileOperations.writeSortedNumber2NameMap(
				dataFolder, 
				cc_goTermNumber2NameMap,
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_CC_GO_TERMS_NUMBER_2_NAME_OUTPUT_FILENAME);

		FileOperations.writeSortedNumber2NameMap(
				dataFolder, 
				goTermNumber2NameMap,
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_GO_TERMS_NUMBER_2_NAME_OUTPUT_FILENAME);

		
		FileOperations.writeName2NumberMap(
				dataFolder, 
				bp_goTermName2NumberMap,
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_BP_GO_TERMS_NAME_2_NUMBER_OUTPUT_FILENAME);
		
		FileOperations.writeName2NumberMap(
				dataFolder, 
				mf_goTermName2NumberMap,
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_MF_GO_TERMS_NAME_2_NUMBER_OUTPUT_FILENAME);

		FileOperations.writeName2NumberMap(
				dataFolder, 
				cc_goTermName2NumberMap,
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_CC_GO_TERMS_NAME_2_NUMBER_OUTPUT_FILENAME);

		FileOperations.writeName2NumberMap(
				dataFolder, 
				goTermName2NumberMap,
				Commons.ALL_POSSIBLE_NAMES_GOTERMS_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_GO_TERMS_NAME_2_NUMBER_OUTPUT_FILENAME);


		
	}

	public static void writeAllPossibleKeggPathwayNames(String dataFolder) {

		List<String> keggPathwayNameList = new ArrayList<String>();
		TObjectIntMap<String> keggPathwayName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> keggPathwayNumber2NameMap = new TIntObjectHashMap<String>();

		readKeggPathwayNames(
				dataFolder, 
				keggPathwayNameList, 
				keggPathwayName2NumberMap, 
				keggPathwayNumber2NameMap,
				Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE);
		
		writeNames(
				dataFolder, 
				keggPathwayNameList, 
				Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_KEGG_PATHWAY_NAMES_OUTPUT_FILENAME);

		FileOperations.writeSortedNumber2NameMap(
				dataFolder, 
				keggPathwayNumber2NameMap,
				Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
		
		FileOperations.writeName2NumberMap(
				dataFolder, 
				keggPathwayName2NumberMap,
				Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_KEGGPATHWAY_NAME_2_NUMBER_OUTPUT_FILENAME);

	}

	// userDefinedGeneSetName2UserDefinedGeneSetNumberMap is already full
	// userDefinedGeneSetNumber2UserDefinedGeneSetNameMap is already full
	// Write them to name2NumberMap and number2NameMap
	public static void writeAllPossibleUserDefinedGeneSetNames(
			String dataFolder,
			TObjectIntMap<String> userDefinedGeneSetName2NumberMap,
			TIntObjectMap<String> userDefinedGeneSetNumber2NameMap) {

		writeNames(dataFolder, 
				userDefinedGeneSetName2NumberMap,
				Commons.ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_USERDEFINEDGENESET_NAMES_OUTPUT_FILENAME);
		
		writeMapsString2Int(dataFolder, userDefinedGeneSetName2NumberMap,
				Commons.ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_USERDEFINEDGENESET_NAME_2_NUMBER_OUTPUT_FILENAME);
		
		writeMapsInt2String(dataFolder, userDefinedGeneSetNumber2NameMap,
				Commons.ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME,
				Commons.ALL_POSSIBLE_USERDEFINEDGENESET_NUMBER_2_NAME_OUTPUT_FILENAME);

	}

	// @todo writeAllPossibleUserDefinedGeneSetNames ends

	// args[0] ---> Input File Name with folder
	// args[1] ---> GLANET installation folder with "\\" at the end. This folder
	// will be used for outputFolder and dataFolder.
	// args[2] ---> Input File Format
	// ---> default
	// Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// args[3] ---> Annotation, overlap definition, number of bases, default 1
	// args[4] ---> Enrichment parameter
	// ---> default Commons.DO_ENRICH
	// ---> Commons.DO_NOT_ENRICH
	// args[5] ---> Generate Random Data Mode
	// ---> default Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	// ---> Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT
	// args[6] ---> multiple testing parameter, enriched elements will be
	// decided and sorted with respest to this parameter
	// ---> default Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE
	// ---> Commons.BONFERRONI_CORRECTED_P_VALUE
	// args[7] ---> Bonferroni Correction Significance Level, default 0.05
	// args[8] ---> Benjamini Hochberg FDR, default 0.05
	// args[9] ---> Number of permutations, default 5000
	// args[10] ---> Dnase Enrichment
	// ---> default Commons.DO_NOT_DNASE_ENRICHMENT
	// ---> Commons.DO_DNASE_ENRICHMENT
	// args[11] ---> Histone Enrichment
	// ---> default Commons.DO_NOT_HISTONE_ENRICHMENT
	// ---> Commons.DO_HISTONE_ENRICHMENT
	// args[12] ---> Transcription Factor(TF) Enrichment
	// ---> default Commons.DO_NOT_TF_ENRICHMENT
	// ---> Commons.DO_TF_ENRICHMENT
	// args[13] ---> KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_KEGGPATHWAY_ENRICHMENT
	// args[14] ---> TF and KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	// args[15] ---> TF and CellLine and KeggPathway Enrichment
	// ---> default Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// args[16] ---> RSAT parameter
	// ---> default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// ---> Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// args[17] ---> job name example: ocd_gwas_snps
	// args[18] ---> writeGeneratedRandomDataMode checkBox
	// ---> default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	// ---> Commons.WRITE_GENERATED_RANDOM_DATA
	// args[19] ---> writePermutationBasedandParametricBasedAnnotationResultMode
	// checkBox
	// ---> default
	// Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// --->
	// Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// args[20] ---> writePermutationBasedAnnotationResultMode checkBox
	// ---> default Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	// ---> Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	public static void main(String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");

		// Write all possible kegg pathway names
		// Using pathway_hsa.list under
		// C:\Users\Burçak\Google Drive\Data\KEGGPathway
		writeAllPossibleKeggPathwayNames(dataFolder);
		
		writeAllPossibleGOTermNames(dataFolder);

	}

}
