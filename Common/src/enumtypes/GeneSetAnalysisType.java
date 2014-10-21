/**
 * @author burcakotlu
 * @date Oct 1, 2014 
 * @time 5:20:27 PM
 */
package enumtypes;

import common.Commons;

/**
 * 
 */
public enum GeneSetAnalysisType {

	EXONBASEDGENESETANALYSIS(1),
	REGULATIONBASEDGENESETANALYSIS(2),
	ALLBASEDGENESETANALYSIS(3);
	
	private final int geneSetAnalysisType;
	
	public int getGeneSetAnalysisType() {
		return geneSetAnalysisType;
	}


	// Constructor
	private GeneSetAnalysisType(int geneSetAnalysisType){
      this.geneSetAnalysisType = geneSetAnalysisType;
   }
	
 
    /** An added method.  */
    public boolean isExonBasedGeneSetAnalysis() {
     return  this == EXONBASEDGENESETANALYSIS;
    }

    /** An added method.  */
    public boolean isRegulationBasedGeneSetAnalysis() {
     return  this == REGULATIONBASEDGENESETANALYSIS;
    }
    
    /** An added method.  */
    public boolean isAllBasedGeneSetAnalysis() {
     return  this == ALLBASEDGENESETANALYSIS;
    }

    public static String convertEnumtoString(GeneSetAnalysisType  geneSetAnalysisType){
    	
    	if (GeneSetAnalysisType.EXONBASEDGENESETANALYSIS.equals(geneSetAnalysisType)){
    		return Commons.EXON_BASED;
    	}else if (GeneSetAnalysisType.REGULATIONBASEDGENESETANALYSIS.equals(geneSetAnalysisType)){
    		return Commons.REGULATION_BASED;
    	}else if (GeneSetAnalysisType.ALLBASEDGENESETANALYSIS.equals(geneSetAnalysisType)){
    		return Commons.ALL_BASED;
    	}else
    		return null;
    }

   
    
}
