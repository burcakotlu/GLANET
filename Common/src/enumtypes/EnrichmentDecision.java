/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date May 22, 2015
 * @project Common 
 *
 */
public enum EnrichmentDecision {
	
	P_VALUE_CALCULATED_FROM_Z_SCORE(1),
	P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO(2),
	P_VALUE_CALCULATED_FROM_Z_SCORE_AND_NUMBER_OF_PERMUTATIONS_RATIO(3);
	
	private final int enrichmentDecision;
	
	/* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private EnrichmentDecision(int enrichmentDecision) {
    	this.enrichmentDecision = enrichmentDecision;
	}
    
    public int getEnrichmentDecision(){
    	return enrichmentDecision;
    }
     
    
    public static EnrichmentDecision convertStringtoEnum(String enrichmentDecision){
    	
    	if (Commons.P_VALUE_CALCULATED_FROM_Z_SCORE.equals(enrichmentDecision)){
    		return P_VALUE_CALCULATED_FROM_Z_SCORE;
    	}else if  (Commons.P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO.equals(enrichmentDecision)){
    		return P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO;
    	}else if  (Commons.P_VALUE_CALCULATED_FROM_Z_SCORE_AND_NUMBER_OF_PERMUTATIONS_RATIO.equals(enrichmentDecision)){
    		return P_VALUE_CALCULATED_FROM_Z_SCORE_AND_NUMBER_OF_PERMUTATIONS_RATIO;
    	}else     	
    		return null;
    }
	
	
 
	 public String convertEnumtoString(){
	 	if (this.equals(EnrichmentDecision.P_VALUE_CALCULATED_FROM_Z_SCORE))
	 		return Commons.P_VALUE_CALCULATED_FROM_Z_SCORE;
	 	else if (this.equals(EnrichmentDecision.P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO))	
	 		return Commons.P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO; 	
	 	else if (this.equals(EnrichmentDecision.P_VALUE_CALCULATED_FROM_Z_SCORE_AND_NUMBER_OF_PERMUTATIONS_RATIO))
	 		return Commons.P_VALUE_CALCULATED_FROM_Z_SCORE_AND_NUMBER_OF_PERMUTATIONS_RATIO;
	 	else
	 		return null;   		
	 }
	
	 public boolean isPValueCalculatedFromNumberofPermutationsRatio() {
		 return (this == EnrichmentDecision.P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO);
	 }
	 
	 public boolean isPValueCalculatedFromZScoreAndNumberofPermutationsRatio() {
		 return (this == EnrichmentDecision.P_VALUE_CALCULATED_FROM_Z_SCORE_AND_NUMBER_OF_PERMUTATIONS_RATIO);
	 }


}
