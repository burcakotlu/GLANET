/**
 * @author burcakotlu
 * @date May 9, 2014 
 * @time 12:09:57 PM
 */
package enrichment;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TLongIntHashMap;

/**
 * 
 */
public class AllMapsWithNumbers {
	
	//DNASE TF HISTONE
	TIntIntMap  permutationNumberDnaseCellLineNumber2KMap;
	TLongIntMap permutationNumberTfNumberCellLineNumber2KMap;
	TLongIntMap permutationNumberHistoneNumberCellLineNumber2KMap;
	
	//UserDefinedGeneset
	TLongIntMap permutationNumberExonBasedUserDefinedGeneSetNumber2KMap;
	TLongIntMap permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap;
	TLongIntMap permutationNumberAllBasedUserDefinedGeneSetNumber2KMap;
	
	//UserDefinedLibrary
	TLongIntMap permutationNumberElementTypeNumberElementNumber2KMap;
	
	//KEGG Pathway
	TIntIntMap permutationNumberExonBasedKeggPathwayNumber2KMap;
	TIntIntMap permutationNumberRegulationBasedKeggPathwayNumber2KMap;
	TIntIntMap permutationNumberAllBasedKeggPathwayNumber2KMap;
	
	//TF and KEGG Pathway
	TLongIntMap permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap;
	TLongIntMap permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap;
	TLongIntMap permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap;
	
	//TF and CellLine and KEGG Pathway
	TLongIntMap permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap;
	TLongIntMap permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap;
	TLongIntMap permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap;
	
	
	
	
	
	
	

	
	public TLongIntMap getPermutationNumberElementTypeNumberElementNumber2KMap() {
		return permutationNumberElementTypeNumberElementNumber2KMap;
	}







	public void setPermutationNumberElementTypeNumberElementNumber2KMap(
			TLongIntMap permutationNumberElementTypeNumberElementNumber2KMap) {
		this.permutationNumberElementTypeNumberElementNumber2KMap = permutationNumberElementTypeNumberElementNumber2KMap;
	}







	public TIntIntMap getPermutationNumberDnaseCellLineNumber2KMap() {
		return permutationNumberDnaseCellLineNumber2KMap;
	}







	public void setPermutationNumberDnaseCellLineNumber2KMap(
			TIntIntMap permutationNumberDnaseCellLineNumber2KMap) {
		this.permutationNumberDnaseCellLineNumber2KMap = permutationNumberDnaseCellLineNumber2KMap;
	}







	public TLongIntMap getPermutationNumberTfNumberCellLineNumber2KMap() {
		return permutationNumberTfNumberCellLineNumber2KMap;
	}







	public void setPermutationNumberTfNumberCellLineNumber2KMap(
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap) {
		this.permutationNumberTfNumberCellLineNumber2KMap = permutationNumberTfNumberCellLineNumber2KMap;
	}







	public TLongIntMap getPermutationNumberHistoneNumberCellLineNumber2KMap() {
		return permutationNumberHistoneNumberCellLineNumber2KMap;
	}







	public void setPermutationNumberHistoneNumberCellLineNumber2KMap(
			TLongIntMap permutationNumberHistoneNumberCellLineNumber2KMap) {
		this.permutationNumberHistoneNumberCellLineNumber2KMap = permutationNumberHistoneNumberCellLineNumber2KMap;
	}







	public TLongIntMap getPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap() {
		return permutationNumberExonBasedUserDefinedGeneSetNumber2KMap;
	}







	public void setPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap(
			TLongIntMap permutationNumberExonBasedUserDefinedGeneSetNumber2KMap) {
		this.permutationNumberExonBasedUserDefinedGeneSetNumber2KMap = permutationNumberExonBasedUserDefinedGeneSetNumber2KMap;
	}







	public TLongIntMap getPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap() {
		return permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap;
	}







	public void setPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap(
			TLongIntMap permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap) {
		this.permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap = permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap;
	}







	public TLongIntMap getPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap() {
		return permutationNumberAllBasedUserDefinedGeneSetNumber2KMap;
	}







	public void setPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap(
			TLongIntMap permutationNumberAllBasedUserDefinedGeneSetNumber2KMap) {
		this.permutationNumberAllBasedUserDefinedGeneSetNumber2KMap = permutationNumberAllBasedUserDefinedGeneSetNumber2KMap;
	}







	public TIntIntMap getPermutationNumberExonBasedKeggPathwayNumber2KMap() {
		return permutationNumberExonBasedKeggPathwayNumber2KMap;
	}







	public void setPermutationNumberExonBasedKeggPathwayNumber2KMap(
			TIntIntMap permutationNumberExonBasedKeggPathwayNumber2KMap) {
		this.permutationNumberExonBasedKeggPathwayNumber2KMap = permutationNumberExonBasedKeggPathwayNumber2KMap;
	}







	public TIntIntMap getPermutationNumberRegulationBasedKeggPathwayNumber2KMap() {
		return permutationNumberRegulationBasedKeggPathwayNumber2KMap;
	}







	public void setPermutationNumberRegulationBasedKeggPathwayNumber2KMap(
			TIntIntMap permutationNumberRegulationBasedKeggPathwayNumber2KMap) {
		this.permutationNumberRegulationBasedKeggPathwayNumber2KMap = permutationNumberRegulationBasedKeggPathwayNumber2KMap;
	}







	public TIntIntMap getPermutationNumberAllBasedKeggPathwayNumber2KMap() {
		return permutationNumberAllBasedKeggPathwayNumber2KMap;
	}







	public void setPermutationNumberAllBasedKeggPathwayNumber2KMap(
			TIntIntMap permutationNumberAllBasedKeggPathwayNumber2KMap) {
		this.permutationNumberAllBasedKeggPathwayNumber2KMap = permutationNumberAllBasedKeggPathwayNumber2KMap;
	}







	public TLongIntMap getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap() {
		return permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap;
	}







	public void setPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap(
			TLongIntMap permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap) {
		this.permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap = permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap;
	}







	public TLongIntMap getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap() {
		return permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap;
	}







	public void setPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap(
			TLongIntMap permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap) {
		this.permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap = permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap;
	}







	public TLongIntMap getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap() {
		return permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap;
	}







	public void setPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap(
			TLongIntMap permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap) {
		this.permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap = permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap;
	}







	public TLongIntMap getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap() {
		return permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap;
	}







	public void setPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap(
			TLongIntMap permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap) {
		this.permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap = permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap;
	}







	public TLongIntMap getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap() {
		return permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap;
	}







	public void setPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap(
			TLongIntMap permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap) {
		this.permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap = permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap;
	}







	public TLongIntMap getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap() {
		return permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap;
	}







	public void setPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap(
			TLongIntMap permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap) {
		this.permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap = permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap;
	}







	public AllMapsWithNumbers() {
		super();
		
		//DNase TF HISTONE
		this.permutationNumberDnaseCellLineNumber2KMap 			= new TIntIntHashMap();
		this.permutationNumberTfNumberCellLineNumber2KMap 		= new TLongIntHashMap();
		this.permutationNumberHistoneNumberCellLineNumber2KMap 	= new TLongIntHashMap();
		
		//UserDefinedGeneset
		this.permutationNumberExonBasedUserDefinedGeneSetNumber2KMap 		= new TLongIntHashMap();
		this.permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap	= new TLongIntHashMap();
		this.permutationNumberAllBasedUserDefinedGeneSetNumber2KMap			= new TLongIntHashMap();
		
		//UserDefinedLibrary
		this.permutationNumberElementTypeNumberElementNumber2KMap	= new TLongIntHashMap();
		
		//KEGG Pathway
		this.permutationNumberExonBasedKeggPathwayNumber2KMap 			= new TIntIntHashMap();
		this.permutationNumberRegulationBasedKeggPathwayNumber2KMap 	= new TIntIntHashMap();
		this.permutationNumberAllBasedKeggPathwayNumber2KMap 			= new TIntIntHashMap();
		
		//TF and KEGG Pathway
		this.permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap 		= new TLongIntHashMap();
		this.permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap = new TLongIntHashMap();
		this.permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap 		= new TLongIntHashMap();
		
		//TF and CellLine and KEGG Pathway
		this.permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap 		= new TLongIntHashMap();
		this.permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap 	= new TLongIntHashMap();
		this.permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap 			= new TLongIntHashMap();	
	
	}
	
	
	
	
	

}
