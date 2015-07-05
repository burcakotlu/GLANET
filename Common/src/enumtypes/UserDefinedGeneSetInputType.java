/**
 * @author burcakotlu
 * @date Sep 29, 2014 
 * @time 12:18:02 PM
 */
package enumtypes;

import common.Commons;

/**
 * 
 */
public enum UserDefinedGeneSetInputType {

	GENE_ID( 1), RNA_NUCLEOTIDE_ACCESSION( 2), GENE_SYMBOL( 3);

	private final int userDefinedGeneSetInputType;

	public int getUserDefinedGeneSetInputType() {

		return userDefinedGeneSetInputType;
	}

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private UserDefinedGeneSetInputType( int userDefinedGeneSetInputType) {

		this.userDefinedGeneSetInputType = userDefinedGeneSetInputType;
	}

	public static UserDefinedGeneSetInputType convertStringtoEnum( String userDefinedGeneSetInputType) {

		if( Commons.GENE_ID.equals( userDefinedGeneSetInputType)){
			return GENE_ID;
		}else if( Commons.RNA_NUCLEOTIDE_ACCESSION.equals( userDefinedGeneSetInputType)){
			return RNA_NUCLEOTIDE_ACCESSION;
		}else if( Commons.GENE_SYMBOL.equals( userDefinedGeneSetInputType)){
			return GENE_SYMBOL;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( UserDefinedGeneSetInputType.GENE_ID))
			return Commons.GENE_ID;
		else if( this.equals( UserDefinedGeneSetInputType.RNA_NUCLEOTIDE_ACCESSION))
			return Commons.RNA_NUCLEOTIDE_ACCESSION;
		else if( this.equals( UserDefinedGeneSetInputType.GENE_SYMBOL))
			return Commons.GENE_SYMBOL;
		else
			return null;
	}

	/** An added method.  */
	public boolean isGeneID() {

		return this == GENE_ID;
	}

	/** An added method.  */
	public boolean isRNANucleotideAccession() {

		return this == RNA_NUCLEOTIDE_ACCESSION;
	}

	/** An added method.  */
	public boolean isGeneSymbol() {

		return this == GENE_SYMBOL;
	}

}
