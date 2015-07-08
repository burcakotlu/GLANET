/**
 * @author burcakotlu
 * @date May 12, 2014 
 * @time 2:58:41 PM
 */
package annotation;

import enumtypes.IntervalName;
import gnu.trove.list.TIntList;

/**
 * 
 */
public class PermutationNumberUcscRefSeqGeneNumberOverlap {

	// example
	// refSeqGeneName NM_002979
	// intervalName INTRON15
	// geneHugoSymbol SCP2
	// geneEntrezId 6342

	int permutationNumber;
	int refSeqGeneNumber;
	IntervalName intervalName;
	int intervalNumber;
	int geneHugoSymbolNumber;
	int geneEntrezId;
	int low;
	int high;
	TIntList keggPathwayNumberList;

	public int getPermutationNumber() {

		return permutationNumber;
	}

	public void setPermutationNumber( int permutationNumber) {

		this.permutationNumber = permutationNumber;
	}

	public int getRefSeqGeneNumber() {

		return refSeqGeneNumber;
	}

	public void setRefSeqGeneNumber( int refSeqGeneNumber) {

		this.refSeqGeneNumber = refSeqGeneNumber;
	}

	public IntervalName getIntervalName() {

		return intervalName;
	}

	public void setIntervalName( IntervalName intervalName) {

		this.intervalName = intervalName;
	}

	public int getIntervalNumber() {

		return intervalNumber;
	}

	public void setIntervalNumber( int intervalNumber) {

		this.intervalNumber = intervalNumber;
	}

	public int getGeneHugoSymbolNumber() {

		return geneHugoSymbolNumber;
	}

	public void setGeneHugoSymbolNumber( int geneHugoSymbolNumber) {

		this.geneHugoSymbolNumber = geneHugoSymbolNumber;
	}

	public int getGeneEntrezId() {

		return geneEntrezId;
	}

	public void setGeneEntrezId( int geneEntrezId) {

		this.geneEntrezId = geneEntrezId;
	}

	public int getLow() {

		return low;
	}

	public void setLow( int low) {

		this.low = low;
	}

	public int getHigh() {

		return high;
	}

	public void setHigh( int high) {

		this.high = high;
	}

	

	public TIntList getKeggPathwayNumberList() {
		return keggPathwayNumberList;
	}

	public void setKeggPathwayNumberList(TIntList keggPathwayNumberList) {
		this.keggPathwayNumberList = keggPathwayNumberList;
	}

	public PermutationNumberUcscRefSeqGeneNumberOverlap(
			int permutationNumber, 
			int refSeqGeneNumber,
			IntervalName intervalName, 
			int intervalNumber, 
			int geneHugoSymbolNumber, 
			int geneEntrezId, 
			int low,
			int high, 
			TIntList keggPathwayNumberList) {

		super();
		this.permutationNumber = permutationNumber;
		this.refSeqGeneNumber = refSeqGeneNumber;
		this.intervalName = intervalName;
		this.intervalNumber = intervalNumber;
		this.geneHugoSymbolNumber = geneHugoSymbolNumber;
		this.geneEntrezId = geneEntrezId;
		this.low = low;
		this.high = high;
		this.keggPathwayNumberList = keggPathwayNumberList;
	}

}
