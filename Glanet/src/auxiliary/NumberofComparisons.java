/**
 * @author Burcak Otlu
 * Aug 3, 2013
 * 12:11:02 AM
 * 2013
 *
 * 
 */
package auxiliary;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.ArrayList;
import java.util.List;

import userdefined.library.UserDefinedLibraryUtility;

import common.Commons;

public class NumberofComparisons {

	// Number of comparisons for Bonferroni Correction
	int dnaseCellLineNumberofComparison;
	int tfNumberofComparison;
	int tfCellLineNumberofComparison;
	int histoneCellLineNumberofComparison;

	int exonBasedKEGGPathwayNumberofComparison;
	int regulationBasedKEGGPathwayNumberofComparison;
	int allBasedKEGGPathwayNumberofComparison;

	// new functionality
	// 25 December 2013
	int tfExonBasedKEGGPathwayNumberofComparison;
	int tfRegulationBasedKEGGPathwayNumberofComparison;
	int tfAllBasedKEGGPathwayNumberofComparison;

	// new functionality
	// 21 January 2014
	int tfCellLineExonBasedKEGGPathwayNumberofComparison;
	int tfCellLineRegulationBasedKEGGPathwayNumberofComparison;
	int tfCellLineAllBasedKEGGPathwayNumberofComparison;

	// 8 October 2014
	// UserDefinedGeneSet
	int exonBasedUserDefinedGeneSetNumberofComparison;
	int regulationBasedUserDefinedGeneSetNumberofComparison;
	int allBasedUserDefinedGeneSetNumberofComparison;

	// 11 NOV 2014
	// UserDefinedLibrary
	TIntIntMap userDefinedLibraryElementTypeNumber2NumberofComparisonMap;

	public int getDnaseCellLineNumberofComparison() {
		return dnaseCellLineNumberofComparison;
	}

	public void setDnaseCellLineNumberofComparison(int dnaseCellLineNumberofComparison) {
		this.dnaseCellLineNumberofComparison = dnaseCellLineNumberofComparison;
	}

	public int getTfNumberofComparison() {
		return tfNumberofComparison;
	}

	public void setTfNumberofComparison(int tfNumberofComparison) {
		this.tfNumberofComparison = tfNumberofComparison;
	}

	public int getTfCellLineNumberofComparison() {
		return tfCellLineNumberofComparison;
	}

	public void setTfCellLineNumberofComparison(int tfCellLineNumberofComparison) {
		this.tfCellLineNumberofComparison = tfCellLineNumberofComparison;
	}

	public int getHistoneCellLineNumberofComparison() {
		return histoneCellLineNumberofComparison;
	}

	public void setHistoneCellLineNumberofComparison(int histoneCellLineNumberofComparison) {
		this.histoneCellLineNumberofComparison = histoneCellLineNumberofComparison;
	}

	public int getExonBasedKEGGPathwayNumberofComparison() {
		return exonBasedKEGGPathwayNumberofComparison;
	}

	public void setExonBasedKEGGPathwayNumberofComparison(int exonBasedKEGGPathwayNumberofComparison) {
		this.exonBasedKEGGPathwayNumberofComparison = exonBasedKEGGPathwayNumberofComparison;
	}

	public int getRegulationBasedKEGGPathwayNumberofComparison() {
		return regulationBasedKEGGPathwayNumberofComparison;
	}

	public void setRegulationBasedKEGGPathwayNumberofComparison(int regulationBasedKEGGPathwayNumberofComparison) {
		this.regulationBasedKEGGPathwayNumberofComparison = regulationBasedKEGGPathwayNumberofComparison;
	}

	public int getAllBasedKEGGPathwayNumberofComparison() {
		return allBasedKEGGPathwayNumberofComparison;
	}

	public void setAllBasedKEGGPathwayNumberofComparison(int allBasedKEGGPathwayNumberofComparison) {
		this.allBasedKEGGPathwayNumberofComparison = allBasedKEGGPathwayNumberofComparison;
	}

	public int getTfExonBasedKEGGPathwayNumberofComparison() {
		return tfExonBasedKEGGPathwayNumberofComparison;
	}

	public void setTfExonBasedKEGGPathwayNumberofComparison(int tfExonBasedKEGGPathwayNumberofComparison) {
		this.tfExonBasedKEGGPathwayNumberofComparison = tfExonBasedKEGGPathwayNumberofComparison;
	}

	public int getTfRegulationBasedKEGGPathwayNumberofComparison() {
		return tfRegulationBasedKEGGPathwayNumberofComparison;
	}

	public void setTfRegulationBasedKEGGPathwayNumberofComparison(int tfRegulationBasedKEGGPathwayNumberofComparison) {
		this.tfRegulationBasedKEGGPathwayNumberofComparison = tfRegulationBasedKEGGPathwayNumberofComparison;
	}

	public int getTfAllBasedKEGGPathwayNumberofComparison() {
		return tfAllBasedKEGGPathwayNumberofComparison;
	}

	public void setTfAllBasedKEGGPathwayNumberofComparison(int tfAllBasedKEGGPathwayNumberofComparison) {
		this.tfAllBasedKEGGPathwayNumberofComparison = tfAllBasedKEGGPathwayNumberofComparison;
	}

	public int getTfCellLineExonBasedKEGGPathwayNumberofComparison() {
		return tfCellLineExonBasedKEGGPathwayNumberofComparison;
	}

	public void setTfCellLineExonBasedKEGGPathwayNumberofComparison(int tfCellLineExonBasedKEGGPathwayNumberofComparison) {
		this.tfCellLineExonBasedKEGGPathwayNumberofComparison = tfCellLineExonBasedKEGGPathwayNumberofComparison;
	}

	public int getTfCellLineRegulationBasedKEGGPathwayNumberofComparison() {
		return tfCellLineRegulationBasedKEGGPathwayNumberofComparison;
	}

	public void setTfCellLineRegulationBasedKEGGPathwayNumberofComparison(int tfCellLineRegulationBasedKEGGPathwayNumberofComparison) {
		this.tfCellLineRegulationBasedKEGGPathwayNumberofComparison = tfCellLineRegulationBasedKEGGPathwayNumberofComparison;
	}

	public int getTfCellLineAllBasedKEGGPathwayNumberofComparison() {
		return tfCellLineAllBasedKEGGPathwayNumberofComparison;
	}

	public void setTfCellLineAllBasedKEGGPathwayNumberofComparison(int tfCellLineAllBasedKEGGPathwayNumberofComparison) {
		this.tfCellLineAllBasedKEGGPathwayNumberofComparison = tfCellLineAllBasedKEGGPathwayNumberofComparison;
	}

	public int getExonBasedUserDefinedGeneSetNumberofComparison() {
		return exonBasedUserDefinedGeneSetNumberofComparison;
	}

	public void setExonBasedUserDefinedGeneSetNumberofComparison(int exonBasedUserDefinedGeneSetNumberofComparison) {
		this.exonBasedUserDefinedGeneSetNumberofComparison = exonBasedUserDefinedGeneSetNumberofComparison;
	}

	public int getRegulationBasedUserDefinedGeneSetNumberofComparison() {
		return regulationBasedUserDefinedGeneSetNumberofComparison;
	}

	public void setRegulationBasedUserDefinedGeneSetNumberofComparison(int regulationBasedUserDefinedGeneSetNumberofComparison) {
		this.regulationBasedUserDefinedGeneSetNumberofComparison = regulationBasedUserDefinedGeneSetNumberofComparison;
	}

	public int getAllBasedUserDefinedGeneSetNumberofComparison() {
		return allBasedUserDefinedGeneSetNumberofComparison;
	}

	public void setAllBasedUserDefinedGeneSetNumberofComparison(int allBasedUserDefinedGeneSetNumberofComparison) {
		this.allBasedUserDefinedGeneSetNumberofComparison = allBasedUserDefinedGeneSetNumberofComparison;
	}

	public TIntIntMap getUserDefinedLibraryElementTypeNumber2NumberofComparisonMap() {
		return userDefinedLibraryElementTypeNumber2NumberofComparisonMap;
	}

	public void setUserDefinedLibraryElementTypeNumber2NumberofComparisonMap(TIntIntMap userDefinedLibraryElementTypeNumber2NumberofComparisonMap) {
		this.userDefinedLibraryElementTypeNumber2NumberofComparisonMap = userDefinedLibraryElementTypeNumber2NumberofComparisonMap;
	}

	public static NumberofComparisons getNumberofComparisonsforBonferroniCorrection(String dataFolder) {

		NumberofComparisons numberofComparisons = new NumberofComparisons();

		TIntObjectMap<String> dnaseCellLineNumber2NameMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> tfNumber2NameMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> tfCellLineNumber2NameMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> histoneCellLineNumber2NameMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> keggPathwayNumber2NameMap = new TIntObjectHashMap<String>();

		// Bonferroni Correction
		// Dnase
		FileOperations.fillNumber2NameMap(dnaseCellLineNumber2NameMap, dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		numberofComparisons.setDnaseCellLineNumberofComparison(dnaseCellLineNumber2NameMap.size());

		// TF
		FileOperations.fillNumber2NameMap(tfNumber2NameMap, dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.fillNumber2NameMap(tfCellLineNumber2NameMap, dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_TF_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		numberofComparisons.setTfNumberofComparison(tfNumber2NameMap.size());
		numberofComparisons.setTfCellLineNumberofComparison(tfCellLineNumber2NameMap.size());

		// HISTONE
		FileOperations.fillNumber2NameMap(histoneCellLineNumber2NameMap, dataFolder + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_HISTONE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		numberofComparisons.setHistoneCellLineNumberofComparison(histoneCellLineNumber2NameMap.size());

		// KEGG PATHWAY
		// Important ASK
		// QUESTION:Should we use the number of annotated Exon Based KEGG
		// Pathways or the number of KEGG Pathways?
		FileOperations.fillNumber2NameMap(keggPathwayNumber2NameMap, dataFolder + Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
		// EXON BASED KEGG PATHWAY
		numberofComparisons.setExonBasedKEGGPathwayNumberofComparison(keggPathwayNumber2NameMap.size());
		// REGULATION BASED KEGG PATHWAY
		numberofComparisons.setRegulationBasedKEGGPathwayNumberofComparison(keggPathwayNumber2NameMap.size());
		// ALL BASED KEGG PATHWAY
		numberofComparisons.setAllBasedKEGGPathwayNumberofComparison(keggPathwayNumber2NameMap.size());

		// TF KEGGPathway
		// Number of Different Tf 149
		// Number of Different Kegg Pathways 269
		// 149 * 269 = 40081
		numberofComparisons.setTfExonBasedKEGGPathwayNumberofComparison(tfNumber2NameMap.size() * keggPathwayNumber2NameMap.size());
		numberofComparisons.setTfRegulationBasedKEGGPathwayNumberofComparison(tfNumber2NameMap.size() * keggPathwayNumber2NameMap.size());
		numberofComparisons.setTfAllBasedKEGGPathwayNumberofComparison(tfNumber2NameMap.size() * keggPathwayNumber2NameMap.size());

		// TF CellLine KEGGPathway
		// Number of Different Tf Cell Line Combinations 406
		// Number of Different Kegg Pathways 269
		// 406 * 269 = 109214
		numberofComparisons.setTfCellLineExonBasedKEGGPathwayNumberofComparison(tfCellLineNumber2NameMap.size() * keggPathwayNumber2NameMap.size());
		numberofComparisons.setTfCellLineRegulationBasedKEGGPathwayNumberofComparison(tfCellLineNumber2NameMap.size() * keggPathwayNumber2NameMap.size());
		numberofComparisons.setTfCellLineAllBasedKEGGPathwayNumberofComparison(tfCellLineNumber2NameMap.size() * keggPathwayNumber2NameMap.size());

		// User Defined GeneSet
		List<String> nameList = new ArrayList<String>();

		FileOperations.readNames(dataFolder, nameList, Commons.ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_USERDEFINEDGENESET_NAMES_OUTPUT_FILENAME);
		numberofComparisons.setExonBasedUserDefinedGeneSetNumberofComparison(nameList.size());
		numberofComparisons.setRegulationBasedUserDefinedGeneSetNumberofComparison(nameList.size());
		numberofComparisons.setAllBasedUserDefinedGeneSetNumberofComparison(nameList.size());

		// UserDefinedLibrary starts
		TIntIntMap userDefinedLibraryElementTypeNumber2NumberofComparisonMap = new TIntIntHashMap();
		TIntObjectMap<String> userDefinedLibraryElementTypeNumber2ElementTypeMap = new TIntObjectHashMap<String>();
		int elementTypeNumber;
		String elementType;

		UserDefinedLibraryUtility.fillNumber2NameMap(userDefinedLibraryElementTypeNumber2ElementTypeMap, dataFolder, Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_NUMBER_2_NAME_OUTPUT_FILENAME);

		// For each elementTypeNumber starts
		for (TIntObjectIterator<String> it = userDefinedLibraryElementTypeNumber2ElementTypeMap.iterator(); it.hasNext();) {
			it.advance();

			elementTypeNumber = it.key();
			elementType = it.value();

			TIntObjectMap<String> userDefinedLibraryElementNumber2ElementNameMap = new TIntObjectHashMap<String>();
			UserDefinedLibraryUtility.fillNumber2NameMap(userDefinedLibraryElementNumber2ElementNameMap, dataFolder, Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME + elementType + System.getProperty("file.separator"), Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENT_NUMBER_2_NAME_OUTPUT_FILENAME);

			userDefinedLibraryElementTypeNumber2NumberofComparisonMap.put(elementTypeNumber, userDefinedLibraryElementNumber2ElementNameMap.size());
		}
		// For each elementTypeNumber ends

		numberofComparisons.setUserDefinedLibraryElementTypeNumber2NumberofComparisonMap(userDefinedLibraryElementTypeNumber2NumberofComparisonMap);
		// UserDefinedLibrary ends

		return numberofComparisons;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
