/**
 * 
 */
package enumtypes;


/**
 * @author Burçak Otlu
 * @date Feb 12, 2015
 * @project Common 
 *
 */
public enum GeneOverlapAnalysisFileMode {
	
	WITH_OVERLAP_INFORMATION(1),
	WITHOUT_OVERLAP_INFORMATION(2);
	
private final int geneOverlapAnalysisFileMode;
	
	/* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private GeneOverlapAnalysisFileMode(int geneOverlapAnalysisFileMode) {
    	this.geneOverlapAnalysisFileMode = geneOverlapAnalysisFileMode;
	}
    
    public int getGeneOverlapAnalysisFileMode(){
    	return geneOverlapAnalysisFileMode;
    }
        
    
    /** An added method.  */
    public boolean is_WITH_OVERLAP_INFORMATION() {
        return  this == WITH_OVERLAP_INFORMATION;
    }
	
    /** An added method.  */
    public boolean is_WITHOUT_OVERLAP_INFORMATION() {
        return  this == WITHOUT_OVERLAP_INFORMATION;
    }
    
  
}
