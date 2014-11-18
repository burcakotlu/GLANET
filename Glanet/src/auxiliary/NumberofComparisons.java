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

	//Number of comparisons for Bonferroni Correction
	int dnaseCellLineNumberofComparison;
	int tfNumberofComparison;
	int tfCellLineNumberofComparison;
	int histoneCellLineNumberofComparison;
	
	int exonBasedKEGGPathwayNumberofComparison;
	int regulationBasedKEGGPathwayNumberofComparison;
	int allBasedKEGGPathwayNumberofComparison;

	
	//new functionality
	//25 December 2013
	int tfExonBasedKEGGPathwayNumberofComparison;
	int tfRegulationBasedKEGGPathwayNumberofComparison;
	int tfAllBasedKEGGPathwayNumberofComparison;
	
	//new functionality
	//21 January 2014
	int tfCellLineExonBasedKEGGPathwayNumberofComparison;
	int tfCellLineRegulationBasedKEGGPathwayNumberofComparison;
	int tfCellLineAllBasedKEGGPathwayNumberofComparison;
		
	//8 October 2014
	//UserDefinedGeneSet
	int exonBasedUserDefinedGeneSetNumberofComparison;
	int regulationBasedUserDefinedGeneSetNumberofComparison;
	int allBasedUserDefinedGeneSetNumberofComparison;
	
	
	//11 NOV 2014
	//UserDefinedLibrary
	TIntIntMap userDefinedLibraryElementTypeNumber2NumberofComparisonMap;


	
	
	
	public int getDnaseCellLineNumberofComparison() {
		return dnaseCellLineNumberofComparison;
	}

	public void setDnaseCellLineNumberofComparison(
			int dnaseCellLineNumberofComparison) {
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

	public void setHistoneCellLineNumberofComparison(
			int histoneCellLineNumberofComparison) {
		this.histoneCellLineNumberofComparison = histoneCellLineNumberofComparison;
	}

	public int getExonBasedKEGGPathwayNumberofComparison() {
		return exonBasedKEGGPathwayNumberofComparison;
	}

	public void setExonBasedKEGGPathwayNumberofComparison(
			int exonBasedKEGGPathwayNumberofComparison) {
		this.exonBasedKEGGPathwayNumberofComparison = exonBasedKEGGPathwayNumberofComparison;
	}

	public int getRegulationBasedKEGGPathwayNumberofComparison() {
		return regulationBasedKEGGPathwayNumberofComparison;
	}

	public void setRegulationBasedKEGGPathwayNumberofComparison(
			int regulationBasedKEGGPathwayNumberofComparison) {
		this.regulationBasedKEGGPathwayNumberofComparison = regulationBasedKEGGPathwayNumberofComparison;
	}

	public int getAllBasedKEGGPathwayNumberofComparison() {
		return allBasedKEGGPathwayNumberofComparison;
	}

	public void setAllBasedKEGGPathwayNumberofComparison(
			int allBasedKEGGPathwayNumberofComparison) {
		this.allBasedKEGGPathwayNumberofComparison = allBasedKEGGPathwayNumberofComparison;
	}

	public int getTfExonBasedKEGGPathwayNumberofComparison() {
		return tfExonBasedKEGGPathwayNumberofComparison;
	}

	public void setTfExonBasedKEGGPathwayNumberofComparison(
			int tfExonBasedKEGGPathwayNumberofComparison) {
		this.tfExonBasedKEGGPathwayNumberofComparison = tfExonBasedKEGGPathwayNumberofComparison;
	}

	public int getTfRegulationBasedKEGGPathwayNumberofComparison() {
		return tfRegulationBasedKEGGPathwayNumberofComparison;
	}

	public void setTfRegulationBasedKEGGPathwayNumberofComparison(
			int tfRegulationBasedKEGGPathwayNumberofComparison) {
		this.tfRegulationBasedKEGGPathwayNumberofComparison = tfRegulationBasedKEGGPathwayNumberofComparison;
	}

	public int getTfAllBasedKEGGPathwayNumberofComparison() {
		return tfAllBasedKEGGPathwayNumberofComparison;
	}

	public void setTfAllBasedKEGGPathwayNumberofComparison(
			int tfAllBasedKEGGPathwayNumberofComparison) {
		this.tfAllBasedKEGGPathwayNumberofComparison = tfAllBasedKEGGPathwayNumberofComparison;
	}

	public int getTfCellLineExonBasedKEGGPathwayNumberofComparison() {
		return tfCellLineExonBasedKEGGPathwayNumberofComparison;
	}

	public void setTfCellLineExonBasedKEGGPathwayNumberofComparison(
			int tfCellLineExonBasedKEGGPathwayNumberofComparison) {
		this.tfCellLineExonBasedKEGGPathwayNumberofComparison = tfCellLineExonBasedKEGGPathwayNumberofComparison;
	}

	public int getTfCellLineRegulationBasedKEGGPathwayNumberofComparison() {
		return tfCellLineRegulationBasedKEGGPathwayNumberofComparison;
	}

	public void setTfCellLineRegulationBasedKEGGPathwayNumberofComparison(
			int tfCellLineRegulationBasedKEGGPathwayNumberofComparison) {
		this.tfCellLineRegulationBasedKEGGPathwayNumberofComparison = tfCellLineRegulationBasedKEGGPathwayNumberofComparison;
	}

	public int getTfCellLineAllBasedKEGGPathwayNumberofComparison() {
		return tfCellLineAllBasedKEGGPathwayNumberofComparison;
	}

	public void setTfCellLineAllBasedKEGGPathwayNumberofComparison(
			int tfCellLineAllBasedKEGGPathwayNumberofComparison) {
		this.tfCellLineAllBasedKEGGPathwayNumberofComparison = tfCellLineAllBasedKEGGPathwayNumberofComparison;
	}

	public int getExonBasedUserDefinedGeneSetNumberofComparison() {
		return exonBasedUserDefinedGeneSetNumberofComparison;
	}

	public void setExonBasedUserDefinedGeneSetNumberofComparison(
			int exonBasedUserDefinedGeneSetNumberofComparison) {
		this.exonBasedUserDefinedGeneSetNumberofComparison = exonBasedUserDefinedGeneSetNumberofComparison;
	}

	public int getRegulationBasedUserDefinedGeneSetNumberofComparison() {
		return regulationBasedUserDefinedGeneSetNumberofComparison;
	}

	public void setRegulationBasedUserDefinedGeneSetNumberofComparison(
			int regulationBasedUserDefinedGeneSetNumberofComparison) {
		this.regulationBasedUserDefinedGeneSetNumberofComparison = regulationBasedUserDefinedGeneSetNumberofComparison;
	}

	public int getAllBasedUserDefinedGeneSetNumberofComparison() {
		return allBasedUserDefinedGeneSetNumberofComparison;
	}

	public void setAllBasedUserDefinedGeneSetNumberofComparison(
			int allBasedUserDefinedGeneSetNumberofComparison) {
		this.allBasedUserDefinedGeneSetNumberofComparison = allBasedUserDefinedGeneSetNumberofComparison;
	}

	public TIntIntMap getUserDefinedLibraryElementTypeNumber2NumberofComparisonMap() {
		return userDefinedLibraryElementTypeNumber2NumberofComparisonMap;
	}

	public void setUserDefinedLibraryElementTypeNumber2NumberofComparisonMap(
			TIntIntMap userDefinedLibraryElementTypeNumber2NumberofComparisonMap) {
		this.userDefinedLibraryElementTypeNumber2NumberofComparisonMap = userDefinedLibraryElementTypeNumber2NumberofComparisonMap;
	}

	public static NumberofComparisons getNumberofComparisonsforBonferroniCorrection(String dataFolder){
		
 		NumberofComparisons numberofComparisons = new NumberofComparisons();
 		
		TIntObjectMap<String> cellLineNumber2CellLineNameMap = new TIntObjectHashMap<String>();		
		TIntObjectMap<String> tfNumber2TfNameMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> tfNumberCellLineNumber2TfNameCellLineNameMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> histoneNumberCellLineNumber2HistoneNameCellLineNameMap = new TIntObjectHashMap<String>();		
		TIntObjectMap<String> keggPathwayNumber2KeggPathwayNameMap = new TIntObjectHashMap<String>();
		
		
		//Bonferroni Correction
		//Dnase		
//		Old code commented just for recall		
//		CalculateBinomialDistributions.fillHashMapwithOccurences(dnaseCellLineHashMap,dataFolder, Commons.DNASE_CELL_LINE_WHOLE_GENOME_USING_INTERVAL_TREE);
//		numberofComparisons.setNumberofComparisonsDnase(dnaseCellLineHashMap.size());
		CommonUtilities.fillNumberToNameMaps(cellLineNumber2CellLineNameMap,dataFolder + Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_ENCODE_CELLLINENUMBER_2_CELLLINENAME_OUTPUT_FILENAME);
		numberofComparisons.setDnaseCellLineNumberofComparison(cellLineNumber2CellLineNameMap.size());
		
		//TF
		//@todo 
		CommonUtilities.fillNumberToNameMaps(tfNumber2TfNameMap,dataFolder + Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_ENCODE_TFNUMBER_2_TFNAME_OUTPUT_FILENAME);
		CommonUtilities.fillNumberToNameMaps(tfNumberCellLineNumber2TfNameCellLineNameMap,dataFolder + Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_ENCODE_TFNUMBER_CELLLINENUMBER_2_TFNAME_CELLLINENAME_OUTPUT_FILENAME);
		numberofComparisons.setTfNumberofComparison(tfNumber2TfNameMap.size());
		numberofComparisons.setTfCellLineNumberofComparison(tfNumberCellLineNumber2TfNameCellLineNameMap.size());
		
		//HISTONE
		//@todo
		CommonUtilities.fillNumberToNameMaps(histoneNumberCellLineNumber2HistoneNameCellLineNameMap,dataFolder + Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_ENCODE_HISTONENUMBER_CELLLINENUMBER_2_HISTONENAME_CELLLINENAME_OUTPUT_FILENAME);
		numberofComparisons.setHistoneCellLineNumberofComparison(histoneNumberCellLineNumber2HistoneNameCellLineNameMap.size());
		
		//KEGG PATHWAY	
		//Important ASK
		//QUESTION:Should we use the number of annotated Exon Based KEGG Pathways or the number of KEGG Pathways? 
		CommonUtilities.fillNumberToNameMaps(keggPathwayNumber2KeggPathwayNameMap,dataFolder + Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_KEGGPATHWAYNUMBER_2_KEGGPATHWAYNAME_OUTPUT_FILENAME);
		//EXON BASED KEGG PATHWAY
		numberofComparisons.setExonBasedKEGGPathwayNumberofComparison(keggPathwayNumber2KeggPathwayNameMap.size());
		//REGULATION BASED KEGG PATHWAY
		numberofComparisons.setRegulationBasedKEGGPathwayNumberofComparison(keggPathwayNumber2KeggPathwayNameMap.size());
		//ALL BASED KEGG PATHWAY
		numberofComparisons.setAllBasedKEGGPathwayNumberofComparison(keggPathwayNumber2KeggPathwayNameMap.size());
		
	
		//TF KEGGPathway
		//Number of Different Tf 149
		//Number of Different Kegg Pathways 269
		//149 * 269 = 40081
		numberofComparisons.setTfExonBasedKEGGPathwayNumberofComparison(tfNumber2TfNameMap.size() * keggPathwayNumber2KeggPathwayNameMap.size());
		numberofComparisons.setTfRegulationBasedKEGGPathwayNumberofComparison(tfNumber2TfNameMap.size() * keggPathwayNumber2KeggPathwayNameMap.size());
		numberofComparisons.setTfAllBasedKEGGPathwayNumberofComparison(tfNumber2TfNameMap.size() * keggPathwayNumber2KeggPathwayNameMap.size());
	
		
		//TF CellLine KEGGPathway
		//Number of Different Tf Cell Line Combinations 406
		//Number of Different Kegg Pathways 269
		//406 * 269 = 109214
		numberofComparisons.setTfCellLineExonBasedKEGGPathwayNumberofComparison(tfNumberCellLineNumber2TfNameCellLineNameMap.size() * keggPathwayNumber2KeggPathwayNameMap.size());
		numberofComparisons.setTfCellLineRegulationBasedKEGGPathwayNumberofComparison(tfNumberCellLineNumber2TfNameCellLineNameMap.size() * keggPathwayNumber2KeggPathwayNameMap.size());
		numberofComparisons.setTfCellLineAllBasedKEGGPathwayNumberofComparison(tfNumberCellLineNumber2TfNameCellLineNameMap.size() * keggPathwayNumber2KeggPathwayNameMap.size());

	
		//User Defined GeneSet
		List<String> nameList = new ArrayList<String>();
		
		CommonUtilities.readNames(dataFolder,nameList, Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_USERDEFINEDGENESET_NAMES_OUTPUT_FILENAME);
		numberofComparisons.setExonBasedUserDefinedGeneSetNumberofComparison(nameList.size());
		numberofComparisons.setRegulationBasedUserDefinedGeneSetNumberofComparison(nameList.size());
		numberofComparisons.setAllBasedUserDefinedGeneSetNumberofComparison(nameList.size());
		
		
		
		//UserDefinedLibrary starts
		TIntIntMap userDefinedLibraryElementTypeNumber2NumberofComparisonMap = new TIntIntHashMap();
		TIntObjectMap<String> userDefinedLibraryElementTypeNumber2ElementTypeMap = new TIntObjectHashMap<String>();
		int elementTypeNumber;
		String elementType;
		
		UserDefinedLibraryUtility.fillNumber2NameMap(userDefinedLibraryElementTypeNumber2ElementTypeMap,
				dataFolder,
				Commons.BYGLANET + System.getProperty("file.separator") + Commons.ALL_POSSIBLE_NAMES +  System.getProperty("file.separator") + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"),
				Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPENUMBER_2_ELEMENTTYPE_OUTPUT_FILENAME);
		 
		
		//For each elementTypeNumber starts
		for(TIntObjectIterator<String> it = userDefinedLibraryElementTypeNumber2ElementTypeMap.iterator(); it.hasNext(); ){
			it.advance();
			
			elementTypeNumber = it.key();
			elementType = it.value();
			
			TIntObjectMap<String> userDefinedLibraryElementNumber2ElementNameMap = new TIntObjectHashMap<String>();
			UserDefinedLibraryUtility.fillNumber2NameMap(userDefinedLibraryElementNumber2ElementNameMap,
					dataFolder,
					Commons.BYGLANET + System.getProperty("file.separator") + Commons.ALL_POSSIBLE_NAMES +  System.getProperty("file.separator") + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator") + elementType + System.getProperty("file.separator"),
					Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTNUMBER_2_ELEMENTNAME_OUTPUT_FILENAME);
			
			userDefinedLibraryElementTypeNumber2NumberofComparisonMap.put(elementTypeNumber, userDefinedLibraryElementNumber2ElementNameMap.size());
		}
		//For each elementTypeNumber ends

		numberofComparisons.setUserDefinedLibraryElementTypeNumber2NumberofComparisonMap(userDefinedLibraryElementTypeNumber2NumberofComparisonMap);
		//UserDefinedLibrary ends

		return numberofComparisons;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	}
}
