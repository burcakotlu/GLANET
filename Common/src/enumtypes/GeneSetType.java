/**
 * 
 */
package enumtypes;

/**
 * @author burcakotlu
 *
 */
public enum GeneSetType {

	
	USERDEFINEDGENESET(1),
	KEGGPATHWAY(2);
	
	private final int geneSetType;
	
	public int getGeneSetType() {
		return geneSetType;
	}


	// Constructor
	private GeneSetType(int geneSetType){
      this.geneSetType = geneSetType;
   }
	
	 /** An added method.  */
    public boolean isUserDefinedGeneSet() {
     return  this == USERDEFINEDGENESET;
    }

    /** An added method.  */
    public boolean isKeggPathway() {
     return  this == KEGGPATHWAY;
    }
}
