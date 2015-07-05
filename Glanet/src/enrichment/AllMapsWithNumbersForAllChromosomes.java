/**
 * 
 */
package enrichment;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TLongIntHashMap;

/**
 * @author Bur�ak Otlu
 * @date Jun 26, 2015
 * @project Glanet 
 *
 */
public class AllMapsWithNumbersForAllChromosomes {

	// NumberofPermutations ThasHasOverlaps GreaterThanorEqualTo OriginalNumberofOverlapsMap

	// DNASE TF HISTONE
	TIntIntMap dnaseCellLineNumber2NumberofPermutations;
	TIntIntMap tfNumberCellLineNumber2NumberofPermutations;
	TIntIntMap histoneNumberCellLineNumber2NumberofPermutations;

	// Gene
	TIntIntMap geneNumber2NumberofPermutations;

	// UserDefinedGeneset
	TIntIntMap exonBasedUserDefinedGeneSetNumber2NumberofPermutations;
	TIntIntMap regulationBasedUserDefinedGeneSetNumber2NumberofPermutations;
	TIntIntMap allBasedUserDefinedGeneSetNumber2NumberofPermutations;

	// UserDefinedLibrary
	TIntIntMap elementTypeNumberElementNumber2NumberofPermutations;

	// KEGGPathway
	TIntIntMap exonBasedKeggPathwayNumber2NumberofPermutations;
	TIntIntMap regulationBasedKeggPathwayNumber2NumberofPermutations;
	TIntIntMap allBasedKeggPathwayNumber2NumberofPermutations;

	// TF and KEGGPathway
	TIntIntMap tfNumberExonBasedKeggPathwayNumber2NumberofPermutations;
	TIntIntMap tfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations;
	TIntIntMap tfNumberAllBasedKeggPathwayNumber2NumberofPermutations;

	// TF and CellLine and KEGGPathway
	TLongIntMap tfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations;
	TLongIntMap tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations;
	TLongIntMap tfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations;

	public TIntIntMap getDnaseCellLineNumber2NumberofPermutations() {

		return dnaseCellLineNumber2NumberofPermutations;
	}

	public void setDnaseCellLineNumber2NumberofPermutations( TIntIntMap dnaseCellLineNumber2NumberofPermutations) {

		this.dnaseCellLineNumber2NumberofPermutations = dnaseCellLineNumber2NumberofPermutations;
	}

	public TIntIntMap getTfNumberCellLineNumber2NumberofPermutations() {

		return tfNumberCellLineNumber2NumberofPermutations;
	}

	public void setTfNumberCellLineNumber2NumberofPermutations( TIntIntMap tfNumberCellLineNumber2NumberofPermutations) {

		this.tfNumberCellLineNumber2NumberofPermutations = tfNumberCellLineNumber2NumberofPermutations;
	}

	public TIntIntMap getHistoneNumberCellLineNumber2NumberofPermutations() {

		return histoneNumberCellLineNumber2NumberofPermutations;
	}

	public void setHistoneNumberCellLineNumber2NumberofPermutations(
			TIntIntMap histoneNumberCellLineNumber2NumberofPermutations) {

		this.histoneNumberCellLineNumber2NumberofPermutations = histoneNumberCellLineNumber2NumberofPermutations;
	}

	public TIntIntMap getGeneNumber2NumberofPermutations() {

		return geneNumber2NumberofPermutations;
	}

	public void setGeneNumber2NumberofPermutations( TIntIntMap geneNumber2NumberofPermutations) {

		this.geneNumber2NumberofPermutations = geneNumber2NumberofPermutations;
	}

	public TIntIntMap getExonBasedUserDefinedGeneSetNumber2NumberofPermutations() {

		return exonBasedUserDefinedGeneSetNumber2NumberofPermutations;
	}

	public void setExonBasedUserDefinedGeneSetNumber2NumberofPermutations(
			TIntIntMap exonBasedUserDefinedGeneSetNumber2NumberofPermutations) {

		this.exonBasedUserDefinedGeneSetNumber2NumberofPermutations = exonBasedUserDefinedGeneSetNumber2NumberofPermutations;
	}

	public TIntIntMap getRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations() {

		return regulationBasedUserDefinedGeneSetNumber2NumberofPermutations;
	}

	public void setRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations(
			TIntIntMap regulationBasedUserDefinedGeneSetNumber2NumberofPermutations) {

		this.regulationBasedUserDefinedGeneSetNumber2NumberofPermutations = regulationBasedUserDefinedGeneSetNumber2NumberofPermutations;
	}

	public TIntIntMap getAllBasedUserDefinedGeneSetNumber2NumberofPermutations() {

		return allBasedUserDefinedGeneSetNumber2NumberofPermutations;
	}

	public void setAllBasedUserDefinedGeneSetNumber2NumberofPermutations(
			TIntIntMap allBasedUserDefinedGeneSetNumber2NumberofPermutations) {

		this.allBasedUserDefinedGeneSetNumber2NumberofPermutations = allBasedUserDefinedGeneSetNumber2NumberofPermutations;
	}

	public TIntIntMap getElementTypeNumberElementNumber2NumberofPermutations() {

		return elementTypeNumberElementNumber2NumberofPermutations;
	}

	public void setElementTypeNumberElementNumber2NumberofPermutations(
			TIntIntMap elementTypeNumberElementNumber2NumberofPermutations) {

		this.elementTypeNumberElementNumber2NumberofPermutations = elementTypeNumberElementNumber2NumberofPermutations;
	}

	public TIntIntMap getExonBasedKeggPathwayNumber2NumberofPermutations() {

		return exonBasedKeggPathwayNumber2NumberofPermutations;
	}

	public void setExonBasedKeggPathwayNumber2NumberofPermutations(
			TIntIntMap exonBasedKeggPathwayNumber2NumberofPermutations) {

		this.exonBasedKeggPathwayNumber2NumberofPermutations = exonBasedKeggPathwayNumber2NumberofPermutations;
	}

	public TIntIntMap getRegulationBasedKeggPathwayNumber2NumberofPermutations() {

		return regulationBasedKeggPathwayNumber2NumberofPermutations;
	}

	public void setRegulationBasedKeggPathwayNumber2NumberofPermutations(
			TIntIntMap regulationBasedKeggPathwayNumber2NumberofPermutations) {

		this.regulationBasedKeggPathwayNumber2NumberofPermutations = regulationBasedKeggPathwayNumber2NumberofPermutations;
	}

	public TIntIntMap getAllBasedKeggPathwayNumber2NumberofPermutations() {

		return allBasedKeggPathwayNumber2NumberofPermutations;
	}

	public void setAllBasedKeggPathwayNumber2NumberofPermutations(
			TIntIntMap allBasedKeggPathwayNumber2NumberofPermutations) {

		this.allBasedKeggPathwayNumber2NumberofPermutations = allBasedKeggPathwayNumber2NumberofPermutations;
	}

	public TIntIntMap getTfNumberExonBasedKeggPathwayNumber2NumberofPermutations() {

		return tfNumberExonBasedKeggPathwayNumber2NumberofPermutations;
	}

	public void setTfNumberExonBasedKeggPathwayNumber2NumberofPermutations(
			TIntIntMap tfNumberExonBasedKeggPathwayNumber2NumberofPermutations) {

		this.tfNumberExonBasedKeggPathwayNumber2NumberofPermutations = tfNumberExonBasedKeggPathwayNumber2NumberofPermutations;
	}

	public TIntIntMap getTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations() {

		return tfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations;
	}

	public void setTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations(
			TIntIntMap tfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations) {

		this.tfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = tfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations;
	}

	public TIntIntMap getTfNumberAllBasedKeggPathwayNumber2NumberofPermutations() {

		return tfNumberAllBasedKeggPathwayNumber2NumberofPermutations;
	}

	public void setTfNumberAllBasedKeggPathwayNumber2NumberofPermutations(
			TIntIntMap tfNumberAllBasedKeggPathwayNumber2NumberofPermutations) {

		this.tfNumberAllBasedKeggPathwayNumber2NumberofPermutations = tfNumberAllBasedKeggPathwayNumber2NumberofPermutations;
	}

	public TLongIntMap getTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations() {

		return tfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations;
	}

	public void setTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations(
			TLongIntMap tfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations) {

		this.tfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations = tfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations;
	}

	public TLongIntMap getTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations() {

		return tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations;
	}

	public void setTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations(
			TLongIntMap tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations) {

		this.tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations;
	}

	public TLongIntMap getTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations() {

		return tfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations;
	}

	public void setTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations(
			TLongIntMap tfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations) {

		this.tfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations = tfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations;
	}

	public AllMapsWithNumbersForAllChromosomes() {

		super();

		this.dnaseCellLineNumber2NumberofPermutations = new TIntIntHashMap();
		this.tfNumberCellLineNumber2NumberofPermutations = new TIntIntHashMap();
		this.histoneNumberCellLineNumber2NumberofPermutations = new TIntIntHashMap();

		this.geneNumber2NumberofPermutations = new TIntIntHashMap();

		this.exonBasedUserDefinedGeneSetNumber2NumberofPermutations = new TIntIntHashMap();
		this.regulationBasedUserDefinedGeneSetNumber2NumberofPermutations = new TIntIntHashMap();
		this.allBasedUserDefinedGeneSetNumber2NumberofPermutations = new TIntIntHashMap();

		this.elementTypeNumberElementNumber2NumberofPermutations = new TIntIntHashMap();

		this.exonBasedKeggPathwayNumber2NumberofPermutations = new TIntIntHashMap();
		this.regulationBasedKeggPathwayNumber2NumberofPermutations = new TIntIntHashMap();
		this.allBasedKeggPathwayNumber2NumberofPermutations = new TIntIntHashMap();

		this.tfNumberExonBasedKeggPathwayNumber2NumberofPermutations = new TIntIntHashMap();
		this.tfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = new TIntIntHashMap();
		this.tfNumberAllBasedKeggPathwayNumber2NumberofPermutations = new TIntIntHashMap();

		this.tfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations = new TLongIntHashMap();
		this.tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = new TLongIntHashMap();
		this.tfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations = new TLongIntHashMap();

	}

}
