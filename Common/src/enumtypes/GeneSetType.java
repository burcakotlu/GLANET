/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author burcakotlu
 *
 */
public enum GeneSetType {

	USERDEFINEDGENESET(1),
	KEGGPATHWAY(2),
	GENE_ONTOLOGY_TERMS(3),
	NO_GENESET_TYPE_IS_DEFINED(4);

	private final int geneSetType;

	public int getGeneSetType() {

		return geneSetType;
	}

	// Constructor
	private GeneSetType( int geneSetType) {

		this.geneSetType = geneSetType;
	}

	/** An added method.  */
	public boolean isUserDefinedGeneSet() {

		return this == USERDEFINEDGENESET;
	}

	/** An added method.  */
	public boolean isKeggPathway() {

		return this == KEGGPATHWAY;
	}
	
	/** An added method.  */
	public boolean isGeneOntologyTerms() {

		return this == GENE_ONTOLOGY_TERMS;
	}
	

	public boolean isNoGeneSetTypeDefined() {

		return this == NO_GENESET_TYPE_IS_DEFINED;
	}
	
	
	public static GeneSetType convertStringtoEnum(String geneSetType) {

		if(Commons.USER_DEFINED_GENESET.equalsIgnoreCase(geneSetType)){
			return USERDEFINEDGENESET;
		}else if( Commons.KEGG_PATHWAY.equalsIgnoreCase(geneSetType)){
			return KEGGPATHWAY;
		}else if( Commons.GENE_ONTOLOGY_TERMS.equalsIgnoreCase(geneSetType)){
			return GENE_ONTOLOGY_TERMS;
		}else if( Commons.NO_GENESET_TYPE_IS_DEFINED.equalsIgnoreCase(geneSetType)){
			return NO_GENESET_TYPE_IS_DEFINED;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals(GeneSetType.USERDEFINEDGENESET))
			return Commons.USER_DEFINED_GENESET;
		else if( this.equals( GeneSetType.KEGGPATHWAY))
			return Commons.KEGG_PATHWAY;
		else if( this.equals( GeneSetType.GENE_ONTOLOGY_TERMS))
			return Commons.GENE_ONTOLOGY_TERMS;
		else if( this.equals( GeneSetType.NO_GENESET_TYPE_IS_DEFINED))
			return Commons.NO_GENESET_TYPE_IS_DEFINED;
		else
			return null;

	}
}
