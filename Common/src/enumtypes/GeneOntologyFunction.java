/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Feb 14, 2017
 * @project Common 
 *
 */
public enum GeneOntologyFunction {

	BIOLOGICAL_PROCESS(1),
	MOLECULAR_FUNCTION(2),
	CELLULAR_COMPONENT(3);

	private final int geneOntologyFunction;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private GeneOntologyFunction(int geneOntologyFunction) {

		this.geneOntologyFunction = geneOntologyFunction;
	}

	public int getGeneOntologyFunction() {

		return geneOntologyFunction;
	}

	public static GeneOntologyFunction convertStringtoEnum(String geneOntologyFunction) {

		if(Commons.BIOLOGICAL_PROCESS.equals(geneOntologyFunction)){
			return BIOLOGICAL_PROCESS;
		}else if(Commons.MOLECULAR_FUNCTION.equals(geneOntologyFunction)){
			return MOLECULAR_FUNCTION;
		}else if(Commons.CELLULAR_COMPONENT.equals(geneOntologyFunction)){
			return CELLULAR_COMPONENT;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if(this.equals(GeneOntologyFunction.BIOLOGICAL_PROCESS))
			return Commons.BIOLOGICAL_PROCESS;
		else if(this.equals(GeneOntologyFunction.MOLECULAR_FUNCTION))
			return Commons.MOLECULAR_FUNCTION;
		else if(this.equals(GeneOntologyFunction.CELLULAR_COMPONENT))
			return Commons.CELLULAR_COMPONENT;
		else
			return null;
	}

	/** An added method.  */
	public boolean isBiologicalProcess() {

		return this == BIOLOGICAL_PROCESS;
	}

	/** An added method.  */
	public boolean isMolecularFunction() {

		return this == MOLECULAR_FUNCTION;
	}

	/** An added method.  */
	public boolean isCellularComponent() {

		return this == CELLULAR_COMPONENT;
	}

}
