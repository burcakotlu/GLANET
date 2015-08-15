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
 * What to do when there is an overlap with a DNase Interval and the created interval?
 *
 */
public enum DnaseOverlapExclusionType {

	COMPLETELY_DISCARD_INTERVAL(1), 
	PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL(2),
	PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS(3),
	NO_DISCARD(4);
	

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
			return COMPLETELY_DISCARD_INTERVAL;
		}
		
		else if( Commons.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP.equals( dnaseOverlapExclusionType)){
				return PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL;
		}
		
		else if( Commons.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS_IN_CASE_OF_DNASE_OVERLAP.equals( dnaseOverlapExclusionType)){
			return PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS;

		}else if( Commons.NO_DISCARD.equals( dnaseOverlapExclusionType)){
			return NO_DISCARD;
			
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL))
			return Commons.COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
		
		else if( this.equals( DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL))
			return Commons.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
		
		else if( this.equals( DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS))
			return Commons.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS_IN_CASE_OF_DNASE_OVERLAP;
		
		else if( this.equals( DnaseOverlapExclusionType.NO_DISCARD))
			return Commons.NO_DISCARD;
		
		else
			return null;
	}

	public boolean isCompletelyDiscardInterval() {
		return ( this == DnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL);
	}

	public boolean isPartiallyDiscardIntervalTakeTheLongestRemainingInterval() {
		return ( this == DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL);
	}

	public boolean isPartiallyDiscardIntervalTakeAllTheRemainingIntervals() {
		return ( this == DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS);
	}

	public boolean isNoDiscard() {
		return ( this == DnaseOverlapExclusionType.NO_DISCARD);
	}
	
}
