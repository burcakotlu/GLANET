/**
 * @author burcakotlu
 * @date May 2, 2014 
 * @time 2:20:29 PM
 */
package intervaltree;

import enumtypes.ChromosomeName;
import enumtypes.NodeType;


/**
 * 
 */
public class UcscRefSeqGeneIntervalTreeNode extends IntervalTreeNode{
	
//	Just for search UCSC RefSeq Gene output
	char strand;
	String  refSeqGeneName;
	Integer geneEntrezId;
	String intervalName;
	String geneHugoSymbol;
	
	public ChromosomeName getChromName() {
		return chromName;
	}


	public void setChromName(ChromosomeName chromName) {
		this.chromName = chromName;
	}


	public String getRefSeqGeneName() {
		return refSeqGeneName;
	}


	public void setRefSeqGeneName(String refSeqGeneName) {
		this.refSeqGeneName = refSeqGeneName;
	}


	public String getIntervalName() {
		return intervalName;
	}


	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}


	public String getGeneHugoSymbol() {
		return geneHugoSymbol;
	}


	public void setGeneHugoSymbol(String geneHugoSymbol) {
		this.geneHugoSymbol = geneHugoSymbol;
	}

	
	public char getStrand() {
		return strand;
	}


	public void setStrand(char strand) {
		this.strand = strand;
	}


	public Integer getGeneEntrezId() {
		return geneEntrezId;
	}
	
	
	public void setGeneEntrezId(Integer geneEntrezId) {
		this.geneEntrezId = geneEntrezId;
	}
	

	
	//For Exon Based Kegg Pathway Enrichment Analysis Ucsc gene
	public UcscRefSeqGeneIntervalTreeNode(ChromosomeName chromName, int low, int high, Integer geneEntrezId, String intervalName,NodeType nodeType) {
			super(chromName,low,high,nodeType);
			
			this.geneEntrezId = geneEntrezId;
			this.intervalName = intervalName;
			
	}
	
	
	//For Ucsc gene without strand attribute
	public UcscRefSeqGeneIntervalTreeNode(ChromosomeName chromName, int low, int high, String refSeqGeneName, Integer geneEntrezId, String intervalName, String geneHugoSymbol,NodeType nodeType) {
		super(chromName,low,high,nodeType);
		
		this.refSeqGeneName = refSeqGeneName;
		this.geneEntrezId = geneEntrezId;
		this.intervalName = intervalName;
		this.geneHugoSymbol = geneHugoSymbol;
		
		
	}
		
		
	//For Ucsc gene with strand attribute
	public UcscRefSeqGeneIntervalTreeNode(ChromosomeName chromName, int low, int high, String refSeqGeneName, Integer geneEntrezId, String intervalName,char strand, String geneHugoSymbol,NodeType nodeType) {
		super(chromName,low,high,nodeType);
		
		this.refSeqGeneName = refSeqGeneName;
		this.geneEntrezId = geneEntrezId;
		this.intervalName = intervalName;
		this.strand = strand;
		this.geneHugoSymbol = geneHugoSymbol;
		
		
		
	}


}
