/**
 * 
 */
package rsat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Burcak Otlu
 * @date Dec 30, 2014
 * @project Glanet
 *
 */
public class SNPInformation {

	String chrNameWithoutPreceedingChr;
	int oneBasedStart;
	int oneBasedEnd;

	String snpReferenceSequence;
	String fastaFile;

	List<Integer> validRsIDList;
	
	Map<String,String> alteredSequenceName2SequenceMap = new HashMap<String,String>();
//	List<String> snpAlteredSequences;
//	List<String> snpAlteredSequenceNames;

	boolean snpContainsAnyOfObservedAlleles;

//	public List<String> getSnpAlteredSequenceNames() {
//
//		return snpAlteredSequenceNames;
//	}
//
//	public void setSnpAlteredSequenceNames( List<String> snpAlteredSequenceNames) {
//
//		this.snpAlteredSequenceNames = snpAlteredSequenceNames;
//	}
	
//	public List<String> getSnpAlteredSequences() {
//
//		return snpAlteredSequences;
//	}
//
//	public void setSnpAlteredSequences( List<String> snpAlteredSequences) {
//
//		this.snpAlteredSequences = snpAlteredSequences;
//	}

	
	
	

	public boolean isSnpContainsAnyOfObservedAlleles() {

		return snpContainsAnyOfObservedAlleles;
	}



	public Map<String, String> getAlteredSequenceName2SequenceMap() {
		return alteredSequenceName2SequenceMap;
	}



	public void setAlteredSequenceName2SequenceMap(Map<String, String> alteredSequenceName2SequenceMap) {
		this.alteredSequenceName2SequenceMap = alteredSequenceName2SequenceMap;
	}



	public void setSnpContainsAnyOfObservedAlleles( boolean snpContainsAnyOfObservedAlleles) {

		this.snpContainsAnyOfObservedAlleles = snpContainsAnyOfObservedAlleles;
	}

	

	public String getChrNameWithoutPreceedingChr() {

		return chrNameWithoutPreceedingChr;
	}

	public void setChrNameWithoutPreceedingChr( String chrNameWithoutPreceedingChr) {

		this.chrNameWithoutPreceedingChr = chrNameWithoutPreceedingChr;
	}

	public int getOneBasedStart() {

		return oneBasedStart;
	}

	public void setOneBasedStart( int oneBasedStart) {

		this.oneBasedStart = oneBasedStart;
	}

	public int getOneBasedEnd() {

		return oneBasedEnd;
	}

	public void setOneBasedEnd( int oneBasedEnd) {

		this.oneBasedEnd = oneBasedEnd;
	}

	public String getSnpReferenceSequence() {

		return snpReferenceSequence;
	}

	public void setSnpReferenceSequence( String snpReferenceSequence) {

		this.snpReferenceSequence = snpReferenceSequence;
	}


	public String getFastaFile() {

		return fastaFile;
	}

	public void setFastaFile( String fastaFile) {

		this.fastaFile = fastaFile;
	}

	public List<Integer> getValidRsIDList() {

		return validRsIDList;
	}

	public void setValidRsIDList( List<Integer> validRsIDList) {

		this.validRsIDList = validRsIDList;
	}

	public SNPInformation() {

		super();
		this.validRsIDList = new ArrayList<Integer>();
		this.alteredSequenceName2SequenceMap = new HashMap<String,String>();
		this.snpContainsAnyOfObservedAlleles = false;
//		this.usedObservedAlleles = new ArrayList<String>();
//		this.snpAlteredSequences = new ArrayList<String>();
//		this.snpAlteredSequenceNames = new ArrayList<String>();
	}

}
