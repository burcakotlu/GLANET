/**
 * @author Burcak Otlu
 * Jul 22, 2013
 * 3:48:28 PM
 * 2013
 *
 * 
 */
package empiricalpvalues;

import java.util.Map;


public class AllName2KMaps {
	
	String permutationNumber;
	String chrName;
	
	Map<String,Integer> dnaseCellLineName2NumberofOverlapsMap;
	Map<String,Integer> tfbsNameandCellLineName2NumberofOverlapsMap;
	Map<String,Integer> histoneNameandCellLineName2NumberofOverlapsMap;
	Map<String,Integer> exonBasedKeggPathway2NumberofOverlapsMap;
	Map<String,Integer> regulationBasedKeggPathway2NumberofOverlapsMap;
	
	
	
	public String getPermutationNumber() {
		return permutationNumber;
	}
	public void setPermutationNumber(String permutationNumber) {
		this.permutationNumber = permutationNumber;
	}
	public String getChrName() {
		return chrName;
	}
	public void setChrName(String chrName) {
		this.chrName = chrName;
	}
	public Map<String, Integer> getDnaseCellLineName2NumberofOverlapsMap() {
		return dnaseCellLineName2NumberofOverlapsMap;
	}
	public void setDnaseCellLineName2NumberofOverlapsMap(
			Map<String, Integer> dnaseCellLineName2NumberofOverlapsMap) {
		this.dnaseCellLineName2NumberofOverlapsMap = dnaseCellLineName2NumberofOverlapsMap;
	}
	public Map<String, Integer> getTfbsNameandCellLineName2NumberofOverlapsMap() {
		return tfbsNameandCellLineName2NumberofOverlapsMap;
	}
	public void setTfbsNameandCellLineName2NumberofOverlapsMap(
			Map<String, Integer> tfbsNameandCellLineName2NumberofOverlapsMap) {
		this.tfbsNameandCellLineName2NumberofOverlapsMap = tfbsNameandCellLineName2NumberofOverlapsMap;
	}
	public Map<String, Integer> getHistoneNameandCellLineName2NumberofOverlapsMap() {
		return histoneNameandCellLineName2NumberofOverlapsMap;
	}
	public void setHistoneNameandCellLineName2NumberofOverlapsMap(
			Map<String, Integer> histoneNameandCellLineName2NumberofOverlapsMap) {
		this.histoneNameandCellLineName2NumberofOverlapsMap = histoneNameandCellLineName2NumberofOverlapsMap;
	}
	public Map<String, Integer> getExonBasedKeggPathway2NumberofOverlapsMap() {
		return exonBasedKeggPathway2NumberofOverlapsMap;
	}
	public void setExonBasedKeggPathway2NumberofOverlapsMap(
			Map<String, Integer> exonBasedKeggPathway2NumberofOverlapsMap) {
		this.exonBasedKeggPathway2NumberofOverlapsMap = exonBasedKeggPathway2NumberofOverlapsMap;
	}
	public Map<String, Integer> getRegulationBasedKeggPathway2NumberofOverlapsMap() {
		return regulationBasedKeggPathway2NumberofOverlapsMap;
	}
	public void setRegulationBasedKeggPathway2NumberofOverlapsMap(
			Map<String, Integer> regulationBasedKeggPathway2NumberofOverlapsMap) {
		this.regulationBasedKeggPathway2NumberofOverlapsMap = regulationBasedKeggPathway2NumberofOverlapsMap;
	}
	
	
	
}
