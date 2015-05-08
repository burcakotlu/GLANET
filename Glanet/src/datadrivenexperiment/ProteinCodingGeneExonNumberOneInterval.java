/**
 * 
 */
package datadrivenexperiment;

import enumtypes.ChromosomeName;

/**
 * @author Burçak Otlu
 * @date May 8, 2015
 * @project Glanet 
 *
 */
public class ProteinCodingGeneExonNumberOneInterval {
	
	char strand;
	int low;
	int high;
	
	ChromosomeName chrName;
	String transcriptID;
	String geneSymbol;
	
	
	
	
	public char getStrand() {
		return strand;
	}
	public void setStrand(char strand) {
		this.strand = strand;
	}
	
	
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	
	
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	
	
	public ChromosomeName getChrName() {
		return chrName;
	}
	public void setChrName(ChromosomeName chrName) {
		this.chrName = chrName;
	}
	
	
	
	public String getTranscriptID() {
		return transcriptID;
	}
	public void setTranscriptID(String transcriptID) {
		this.transcriptID = transcriptID;
	}
	
	
	public String getGeneSymbol() {
		return geneSymbol;
	}
	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}
	
	public ProteinCodingGeneExonNumberOneInterval(char strand, int low, int high, ChromosomeName chrName, String transcriptID, String geneSymbol) {
		super();
		this.strand = strand;
		this.low = low;
		this.high = high;
		this.chrName = chrName;
		this.transcriptID = transcriptID;
		this.geneSymbol = geneSymbol;
	}
	
	public ProteinCodingGeneExonNumberOneInterval() {
		super();
	}
	
	
	
	
}
