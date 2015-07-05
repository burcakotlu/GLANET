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
 * @date Mar 31, 2015
 * @project Glanet 
 *
 */
public class AllMapsDnaseTFHistoneWithNumbers {

	// DNASE TF HISTONE
	TIntIntMap permutationNumberDnaseCellLineNumber2KMap;
	TLongIntMap permutationNumberTfNumberCellLineNumber2KMap;
	TLongIntMap permutationNumberHistoneNumberCellLineNumber2KMap;

	public TIntIntMap getPermutationNumberDnaseCellLineNumber2KMap() {

		return permutationNumberDnaseCellLineNumber2KMap;
	}

	public void setPermutationNumberDnaseCellLineNumber2KMap( TIntIntMap permutationNumberDnaseCellLineNumber2KMap) {

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

	public AllMapsDnaseTFHistoneWithNumbers() {

		super();

		// DNase
		this.permutationNumberDnaseCellLineNumber2KMap = new TIntIntHashMap();

		// TF
		this.permutationNumberTfNumberCellLineNumber2KMap = new TLongIntHashMap();

		// HISTONE
		this.permutationNumberHistoneNumberCellLineNumber2KMap = new TLongIntHashMap();

	}

}
