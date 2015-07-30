/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burcak Otlu
 * @date May 29, 2015
 * @project Common 
 *
 */
public enum DnaseOverlapExclusionType {

	COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP(1), 
	PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ONLY_THE_LONGEST_INTERVAL(2),
	PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ALL_THE_REMAINING_INTERVALS(3),
	NON_EXPRESSING_PROTEIN_CODING_GENES_INTERVALS(4);

	private final int dnaseOverlapExclusionType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum class itself.
	 */
	private DnaseOverlapExclusionType( int dnaseOverlapExclusionType) {

		this.dnaseOverlapExclusionType = dnaseOverlapExclusionType;
	}

	public int getDnaseOverlapExclusionType() {

		return dnaseOverlapExclusionType;
	}

	public static DnaseOverlapExclusionType convertStringtoEnum( String dnaseOverlapExclusionType) {

		if( Commons.COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP.equals( dnaseOverlapExclusionType)){
			
			return COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
			
		}else if( Commons.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ONLY_THE_LONGEST_INTERVAL.equals( dnaseOverlapExclusionType)){
			
			return PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ONLY_THE_LONGEST_INTERVAL;
			
		}else if( Commons.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ALL_THE_REMAINING_INTERVALS.equals( dnaseOverlapExclusionType)){
			
			return PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ALL_THE_REMAINING_INTERVALS;
			
		}else if( Commons.NON_EXPRESSING_PROTEIN_CODING_GENES_INTERVALS.equals( dnaseOverlapExclusionType)){
			
			return NON_EXPRESSING_PROTEIN_CODING_GENES_INTERVALS;
			
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP))
			return Commons.COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
		
		else if( this.equals( DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ONLY_THE_LONGEST_INTERVAL))
			return Commons.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ONLY_THE_LONGEST_INTERVAL;
		
		else if( this.equals( DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ALL_THE_REMAINING_INTERVALS))
			return Commons.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ALL_THE_REMAINING_INTERVALS;
		
		else if( this.equals( DnaseOverlapExclusionType.NON_EXPRESSING_PROTEIN_CODING_GENES_INTERVALS))
			return Commons.NON_EXPRESSING_PROTEIN_CODING_GENES_INTERVALS;
		
		else
			return null;
	}

	public boolean isCompletelyDiscardIntervalInCaseOfDnaseOverlap() {

		return ( this == DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP);
	}

	public boolean isPartiallyDiscardIntervalRemainOnlyTheLongestIntervalInCaseOfDnaseOverlap() {

		return ( this == DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ONLY_THE_LONGEST_INTERVAL);
	}

	public boolean isPartiallyDiscardIntervalRemainAllTheRemainingIntervalsInCaseOfDnaseOverlap() {

		return ( this == DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP_REMAIN_ALL_THE_REMAINING_INTERVALS);
	}

	public boolean isNonExpressingProteinCodingGenesIntervals() {

		return ( this == DnaseOverlapExclusionType.NON_EXPRESSING_PROTEIN_CODING_GENES_INTERVALS);
	}
}
