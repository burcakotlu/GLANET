/**
 * @author burcakotlu
 * @date Aug 12, 2014 
 * @time 12:01:57 PM
 */
package annotation;

import enumtypes.IntervalName;
import gnu.trove.list.TIntList;

/**
 * 
 */
public class UcscRefSeqGeneOverlapWithNumbers {

	// example
	// refSeqGeneName NM_002979
	// intervalName INTRON
	// intervalNumber 15
	// geneHugoSymbol SCP2
	// geneEntrezId 6342

	int refSeqGeneNumber;
	int geneHugoSymbolNumber;
	int geneEntrezId;

	TIntList keggPathwayNumberList;

	IntervalName intervalName;
	int intervalNumber;
	int low;
	int high;

	public int getRefSeqGeneNumber() {

		return refSeqGeneNumber;
	}

	public void setRefSeqGeneNumber( int refSeqGeneNumber) {

		this.refSeqGeneNumber = refSeqGeneNumber;
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


	

	public TIntList getKeggPathwayNumberList() {
		return keggPathwayNumberList;
	}

	public void setKeggPathwayNumberList(TIntList keggPathwayNumberList) {
		this.keggPathwayNumberList = keggPathwayNumberList;
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

	public UcscRefSeqGeneOverlapWithNumbers(
			int refSeqGeneNumber, 
			int geneHugoSymbolNumber, 
			int geneEntrezId,
			TIntList keggPathwayNumberList, 
			IntervalName intervalName, 
			int intervalNumber, 
			int low, 
			int high) {

		super();
		this.refSeqGeneNumber = refSeqGeneNumber;
		this.geneHugoSymbolNumber = geneHugoSymbolNumber;
		this.geneEntrezId = geneEntrezId;
		this.keggPathwayNumberList = keggPathwayNumberList;
		this.intervalName = intervalName;
		this.intervalNumber = intervalNumber;
		this.low = low;
		this.high = high;
	}

}
