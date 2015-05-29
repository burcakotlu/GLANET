/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date May 29, 2015
 * @project Common 
 *
 */
public enum DnaseOverlapExclusionType {
	
	COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP(1),
	PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP(2),
	PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP(3),
	NON_EXPRESSING_GENES_INTERVALS(4);
	
	
	private final int dnaseOverlapExclusionType;
	
	/* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private DnaseOverlapExclusionType(int dnaseOverlapExclusionType) {
    	this.dnaseOverlapExclusionType = dnaseOverlapExclusionType;
	}
    
    public int getDnaseOverlapExclusionType(){
    	return dnaseOverlapExclusionType;
    }
     
    
    public static DnaseOverlapExclusionType convertStringtoEnum(String dnaseOverlapExclusionType){
    	
    	if (Commons.COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP.equals(dnaseOverlapExclusionType)){
    		return COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
    	}else if  (Commons.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP.equals(dnaseOverlapExclusionType)){
    		return PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
    	}else if (Commons.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP.equals(dnaseOverlapExclusionType)){
    		return PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
    	}else if (Commons.NON_EXPRESSING_GENES_INTERVALS.equals(dnaseOverlapExclusionType)){
    		return NON_EXPRESSING_GENES_INTERVALS;
    	}else
    		return null;
    }
	
	
	 public String convertEnumtoString(){
	 	if (this.equals(DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP))
	 		return Commons.COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
	 	else if (this.equals(DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP))	
	 		return Commons.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP; 	
	 	else if (this.equals(DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP))	
	 		return Commons.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP; 	
	 	else if (this.equals(DnaseOverlapExclusionType.NON_EXPRESSING_GENES_INTERVALS))	
	 		return Commons.NON_EXPRESSING_GENES_INTERVALS; 	
	 	else
	 		return null;   		
	 }
	
	 public boolean isCompletelyDiscardIntervalInCaseOfDnaseOverlap() {
		 return (this == DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP);
	 }
	 
	 public boolean isPartiallyDiscardIntervalInCaseOfDnaseOverlap() {
		 return (this == DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP);
	 }


	 public boolean isPartiallyDiscardIntervalRemainOnlyTheLongestIntervalInCaseOfDnaseOverlap() {
		 return (this == DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP);
	 }

	 
	 public boolean isNonExpressingGenesIntervals() {
		 return (this == DnaseOverlapExclusionType.NON_EXPRESSING_GENES_INTERVALS);
	 }
}
