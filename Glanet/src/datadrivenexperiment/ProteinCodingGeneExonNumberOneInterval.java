/**
 * 
 */
package datadrivenexperiment;

import java.util.Comparator;

import enumtypes.ChromosomeName;

/**
 * @author Burcak Otlu
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
	
	Float tpm;
	String ensemblGeneID;
	
	
	public String getEnsemblGeneID() {
		return ensemblGeneID;
	}

	public void setEnsemblGeneID(String ensemblGeneID) {
		this.ensemblGeneID = ensemblGeneID;
	}



	public Float getTpm() {
		return tpm;
	}

	public void setTpm(Float tpm) {
		this.tpm = tpm;
	}

	public char getStrand() {

		return strand;
	}

	public void setStrand( char strand) {

		this.strand = strand;
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

	public ChromosomeName getChrName() {

		return chrName;
	}

	public void setChrName( ChromosomeName chrName) {

		this.chrName = chrName;
	}

	public String getTranscriptID() {

		return transcriptID;
	}

	public void setTranscriptID( String transcriptID) {

		this.transcriptID = transcriptID;
	}

	public String getGeneSymbol() {

		return geneSymbol;
	}

	public void setGeneSymbol( String geneSymbol) {

		this.geneSymbol = geneSymbol;
	}

	public ProteinCodingGeneExonNumberOneInterval(
			char strand, 
			int low, 
			int high, 
			ChromosomeName chrName,
			float tpm,
			String ensemblGeneID,
			String transcriptID, 
			String geneSymbol) {

		super();
		this.strand = strand;
		this.low = low;
		this.high = high;
		this.chrName = chrName;
		this.tpm = tpm;
		this.ensemblGeneID = ensemblGeneID;
		this.transcriptID = transcriptID;
		this.geneSymbol = geneSymbol;
	}

	public ProteinCodingGeneExonNumberOneInterval() {

		super();
	}
	
	public static Comparator<ProteinCodingGeneExonNumberOneInterval> TPM_VALUE_ASCENDING = new Comparator<ProteinCodingGeneExonNumberOneInterval>() {

		public int compare( ProteinCodingGeneExonNumberOneInterval element1, ProteinCodingGeneExonNumberOneInterval element2) {

			return element1.getTpm().compareTo(element2.getTpm());

		}
	};

	
	public static Comparator<ProteinCodingGeneExonNumberOneInterval> TPM_VALUE_DESCENDING = new Comparator<ProteinCodingGeneExonNumberOneInterval>() {

		public int compare( ProteinCodingGeneExonNumberOneInterval element1, ProteinCodingGeneExonNumberOneInterval element2) {

			return element2.getTpm().compareTo(element1.getTpm());

		}
	};
}
