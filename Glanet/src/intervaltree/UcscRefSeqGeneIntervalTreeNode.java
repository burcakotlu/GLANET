/**
 * @author burcakotlu
 * @date May 2, 2014 
 * @time 2:20:29 PM
 */
package intervaltree;

import enumtypes.ChromosomeName;
import enumtypes.IntervalName;
import enumtypes.NodeType;

/**
 * 
 */
public class UcscRefSeqGeneIntervalTreeNode extends IntervalTreeNode {

	// Just for search UCSC RefSeq Gene output
	char strand;
	String refSeqGeneName;
	Integer geneEntrezId;
	IntervalName intervalName;
	int intervalNumber;
	String geneHugoSymbol;

	public ChromosomeName getChromName() {

		return chromName;
	}

	public void setChromName( ChromosomeName chromName) {

		this.chromName = chromName;
	}

	public String getRefSeqGeneName() {

		return refSeqGeneName;
	}

	public void setRefSeqGeneName( String refSeqGeneName) {

		this.refSeqGeneName = refSeqGeneName;
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

	public String getGeneHugoSymbol() {

		return geneHugoSymbol;
	}

	public void setGeneHugoSymbol( String geneHugoSymbol) {

		this.geneHugoSymbol = geneHugoSymbol;
	}

	public char getStrand() {

		return strand;
	}

	public void setStrand( char strand) {

		this.strand = strand;
	}

	public Integer getGeneEntrezId() {

		return geneEntrezId;
	}

	public void setGeneEntrezId( Integer geneEntrezId) {

		this.geneEntrezId = geneEntrezId;
	}

	// For Exon Based Kegg Pathway Enrichment Analysis Ucsc gene
	public UcscRefSeqGeneIntervalTreeNode( ChromosomeName chromName, int low, int high, Integer geneEntrezId,
			IntervalName intervalName, int intervalNumber, NodeType nodeType) {

		super( chromName, low, high, nodeType);

		this.geneEntrezId = geneEntrezId;
		this.intervalName = intervalName;
		this.intervalNumber = intervalNumber;

	}

	// For Ucsc gene without strand attribute
	public UcscRefSeqGeneIntervalTreeNode( ChromosomeName chromName, int low, int high, String refSeqGeneName,
			Integer geneEntrezId, IntervalName intervalName, int intervalNumber, String geneHugoSymbol,
			NodeType nodeType) {

		super( chromName, low, high, nodeType);

		this.refSeqGeneName = refSeqGeneName;
		this.geneEntrezId = geneEntrezId;
		this.intervalName = intervalName;
		this.intervalNumber = intervalNumber;
		this.geneHugoSymbol = geneHugoSymbol;

	}

	// For Ucsc gene with strand attribute
	public UcscRefSeqGeneIntervalTreeNode( ChromosomeName chromName, int low, int high, String refSeqGeneName,
			Integer geneEntrezId, IntervalName intervalName, int intervalNumber, char strand, String geneHugoSymbol,
			NodeType nodeType) {

		super( chromName, low, high, nodeType);

		this.refSeqGeneName = refSeqGeneName;
		this.geneEntrezId = geneEntrezId;
		this.intervalName = intervalName;
		this.intervalNumber = intervalNumber;
		this.strand = strand;
		this.geneHugoSymbol = geneHugoSymbol;

	}

}
