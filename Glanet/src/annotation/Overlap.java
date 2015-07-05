/**
 * 
 */
package annotation;

import enumtypes.IntervalName;

/**
 * @author Bur�ak Otlu
 * @date Feb 10, 2015
 * @project Glanet
 *
 */
public class Overlap {

	int geneId;
	int alternateGeneNumber;
	int RNANucleotideAccessionNumber;

	IntervalName overlapIntervalName;
	int overlapIntervalNumber;

	public IntervalName getOverlapIntervalName() {

		return overlapIntervalName;
	}

	public void setOverlapIntervalName( IntervalName overlapIntervalName) {

		this.overlapIntervalName = overlapIntervalName;
	}

	public int getOverlapIntervalNumber() {

		return overlapIntervalNumber;
	}

	public void setOverlapIntervalNumber( int overlapIntervalNumber) {

		this.overlapIntervalNumber = overlapIntervalNumber;
	}

	public int getAlternateGeneNumber() {

		return alternateGeneNumber;
	}

	public void setAlternateGeneNumber( int alternateGeneNumber) {

		this.alternateGeneNumber = alternateGeneNumber;
	}

	public int getRNANucleotideAccessionNumber() {

		return RNANucleotideAccessionNumber;
	}

	public void setRNANucleotideAccessionNumber( int rNANucleotideAccessionNumber) {

		RNANucleotideAccessionNumber = rNANucleotideAccessionNumber;
	}

	public int getGeneId() {

		return geneId;
	}

	public void setGeneId( int geneId) {

		this.geneId = geneId;
	}

	public Overlap( int geneId, int alternateGeneNumber, int rNANucleotideAccessionNumber,
			IntervalName overlapIntervalName, int overlapIntervalNumber) {

		super();
		this.geneId = geneId;
		this.alternateGeneNumber = alternateGeneNumber;
		RNANucleotideAccessionNumber = rNANucleotideAccessionNumber;
		this.overlapIntervalName = overlapIntervalName;
		this.overlapIntervalNumber = overlapIntervalNumber;
	}

}
