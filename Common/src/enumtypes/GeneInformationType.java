/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author burcakotlu
 *
 */
public enum GeneInformationType {

	GENE_ID( 1),
	GENE_SYMBOL( 2),
	RNA_NUCLEOTIDE_ACCESSION( 3);

	private final int geneInformationType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private GeneInformationType( int geneInformationType) {

		this.geneInformationType = geneInformationType;
	}

	public int getGeneInformationType() {

		return geneInformationType;
	}

	public static GeneInformationType convertStringtoEnum( String geneInformationType) {

		if( Commons.GENE_ID.equals( geneInformationType)){
			return GENE_ID;
		}else if( Commons.GENE_SYMBOL.equals( geneInformationType)){
			return GENE_SYMBOL;
		}else if( Commons.RNA_NUCLEOTIDE_ACCESSION.equals( geneInformationType)){
			return RNA_NUCLEOTIDE_ACCESSION;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( GeneInformationType.GENE_ID))
			return Commons.GENE_ID;
		else if( this.equals( GeneInformationType.GENE_SYMBOL))
			return Commons.GENE_SYMBOL;
		else if( this.equals( GeneInformationType.RNA_NUCLEOTIDE_ACCESSION))
			return Commons.RNA_NUCLEOTIDE_ACCESSION;
		else
			return null;
	}

	/** An added method.  */
	public boolean is_GENE_ID() {

		return this == GENE_ID;
	}

	/** An added method.  */
	public boolean is_GENE_SYMBOL() {

		return this == GENE_SYMBOL;
	}

	/** An added method.  */
	public boolean is_RNA_NUCLEOTIDE_ACCESSION() {

		return this == RNA_NUCLEOTIDE_ACCESSION;
	}

}
