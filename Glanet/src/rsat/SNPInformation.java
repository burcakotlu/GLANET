/**
 * 
 */
package rsat;

import java.util.ArrayList;
import java.util.List;

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
	List<String> usedObservedAlleles;
	List<String> snpAlteredSequences;
	List<String> snpAlteredSequenceNames;

	boolean snpContainsAnyOfObservedAlleles;

	public List<String> getSnpAlteredSequenceNames() {

		return snpAlteredSequenceNames;
	}

	public void setSnpAlteredSequenceNames( List<String> snpAlteredSequenceNames) {

		this.snpAlteredSequenceNames = snpAlteredSequenceNames;
	}

	public boolean isSnpContainsAnyOfObservedAlleles() {

		return snpContainsAnyOfObservedAlleles;
	}

	public void setSnpContainsAnyOfObservedAlleles( boolean snpContainsAnyOfObservedAlleles) {

		this.snpContainsAnyOfObservedAlleles = snpContainsAnyOfObservedAlleles;
	}

	public List<String> getUsedObservedAlleles() {

		return usedObservedAlleles;
	}

	public void setUsedObservedAlleles( List<String> usedObservedAlleles) {

		this.usedObservedAlleles = usedObservedAlleles;
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

	public List<String> getSnpAlteredSequences() {

		return snpAlteredSequences;
	}

	public void setSnpAlteredSequences( List<String> snpAlteredSequences) {

		this.snpAlteredSequences = snpAlteredSequences;
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
		this.usedObservedAlleles = new ArrayList<String>();
		this.snpAlteredSequences = new ArrayList<String>();
		this.snpAlteredSequenceNames = new ArrayList<String>();
		this.snpContainsAnyOfObservedAlleles = false;
	}

}
