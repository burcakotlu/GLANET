/**
 * 
 */
package rsat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Burçak Otlu
 * @date Dec 30, 2014
 * @project Glanet 
 *
 */
public class SNPInformation {
	
	String chrNameWithoutPreceedingChr;
	int oneBasedStart;
	int oneBasedEnd;
	
	String snpReferenceSequence;
	List<String> snpAlteredSequences;
	String fastaFile;
	
	List<String> validRsIDList;
	List<String> observedAlleles;
	
	
	
	
	

	



	public List<String> getObservedAlleles() {
		return observedAlleles;
	}




	public void setObservedAlleles(List<String> observedAlleles) {
		this.observedAlleles = observedAlleles;
	}




	public String getChrNameWithoutPreceedingChr() {
		return chrNameWithoutPreceedingChr;
	}




	public void setChrNameWithoutPreceedingChr(String chrNameWithoutPreceedingChr) {
		this.chrNameWithoutPreceedingChr = chrNameWithoutPreceedingChr;
	}




	public int getOneBasedStart() {
		return oneBasedStart;
	}




	public void setOneBasedStart(int oneBasedStart) {
		this.oneBasedStart = oneBasedStart;
	}




	public int getOneBasedEnd() {
		return oneBasedEnd;
	}




	public void setOneBasedEnd(int oneBasedEnd) {
		this.oneBasedEnd = oneBasedEnd;
	}




	public String getSnpReferenceSequence() {
		return snpReferenceSequence;
	}




	public void setSnpReferenceSequence(String snpReferenceSequence) {
		this.snpReferenceSequence = snpReferenceSequence;
	}




	public List<String> getSnpAlteredSequences() {
		return snpAlteredSequences;
	}




	public void setSnpAlteredSequences(List<String> snpAlteredSequences) {
		this.snpAlteredSequences = snpAlteredSequences;
	}




	public String getFastaFile() {
		return fastaFile;
	}




	public void setFastaFile(String fastaFile) {
		this.fastaFile = fastaFile;
	}




	public List<String> getValidRsIDList() {
		return validRsIDList;
	}




	public void setValidRsIDList(List<String> validRsIDList) {
		this.validRsIDList = validRsIDList;
	}




	public SNPInformation() {
		
		super();
		this.validRsIDList = new ArrayList<String>();
		this.observedAlleles = new ArrayList<String>();
	}
	
	
	

}
