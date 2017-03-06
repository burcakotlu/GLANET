/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Aug 17, 2015
 * @project Common 
 *
 */
public enum DataDrivenExperimentGeneType {
	
	NONEXPRESSING_PROTEINCODING_GENES(1),
	EXPRESSING_PROTEINCODING_GENES(2);
	
	private final int dataDrivenExperimentGeneType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum class itself.
	 */
	private DataDrivenExperimentGeneType( int dataDrivenExperimentGeneType) {
		this.dataDrivenExperimentGeneType = dataDrivenExperimentGeneType;
	}

	public int getDataDrivenExperimentGeneType() {
		return dataDrivenExperimentGeneType;
	}

	public static DataDrivenExperimentGeneType convertStringtoEnum( String dataDrivenExperimentGeneType) {

		if( Commons.NONEXPRESSING_PROTEINCODING_GENES.equals(dataDrivenExperimentGeneType)){
			return NONEXPRESSING_PROTEINCODING_GENES;
		}
		
		else if( Commons.EXPRESSING_PROTEINCODING_GENES.equals(dataDrivenExperimentGeneType)){
				return EXPRESSING_PROTEINCODING_GENES;
		}
		
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( DataDrivenExperimentGeneType.NONEXPRESSING_PROTEINCODING_GENES))
			return Commons.NONEXPRESSING_PROTEINCODING_GENES;
		
		else if( this.equals( DataDrivenExperimentGeneType.EXPRESSING_PROTEINCODING_GENES))
			return Commons.EXPRESSING_PROTEINCODING_GENES;
		
		else
			return null;
	}

	public String convertEnumtoShortString() {

		if( this.equals( DataDrivenExperimentGeneType.NONEXPRESSING_PROTEINCODING_GENES))
			return Commons.NONEXPRESSING_PROTEINCODING_GENES_SHORT;
		
		else if( this.equals( DataDrivenExperimentGeneType.EXPRESSING_PROTEINCODING_GENES))
			return Commons.EXPRESSING_PROTEINCODING_GENES_SHORT;
		
		else
			return null;
	}

	public boolean isNonExpressingProteinCodingGenes() {
		return ( this == DataDrivenExperimentGeneType.NONEXPRESSING_PROTEINCODING_GENES);
	}

	public boolean isExpressingProteinCodingGenes() {
		return ( this == DataDrivenExperimentGeneType.EXPRESSING_PROTEINCODING_GENES);
	}

	
	

}
