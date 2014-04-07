/**
 * @author burcakotlu
 * @date Apr 4, 2014 
 * @time 4:08:27 PM
 */
package jaxbxjctool;

import java.util.List;

/**
 * 
 */
public class SNPInformation {
	
	String chrNamewithoutChr;
	int snpOneBasedCoordinate;
	
	List<String> observedAlleles;
	String referenceSequence;
	List<String> alteredSequences;
	
	
	

	public String getChrNamewithoutChr() {
		return chrNamewithoutChr;
	}




	public void setChrNamewithoutChr(String chrNamewithoutChr) {
		this.chrNamewithoutChr = chrNamewithoutChr;
	}




	public int getSnpOneBasedCoordinate() {
		return snpOneBasedCoordinate;
	}




	public void setSnpOneBasedCoordinate(int snpOneBasedCoordinate) {
		this.snpOneBasedCoordinate = snpOneBasedCoordinate;
	}




	public List<String> getObservedAlleles() {
		return observedAlleles;
	}




	public void setObservedAlleles(List<String> observedAlleles) {
		this.observedAlleles = observedAlleles;
	}




	public String getReferenceSequence() {
		return referenceSequence;
	}




	public void setReferenceSequence(String referenceSequence) {
		this.referenceSequence = referenceSequence;
	}




	public List<String> getAlteredSequences() {
		return alteredSequences;
	}




	public void setAlteredSequences(List<String> alteredSequences) {
		this.alteredSequences = alteredSequences;
	}




	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
