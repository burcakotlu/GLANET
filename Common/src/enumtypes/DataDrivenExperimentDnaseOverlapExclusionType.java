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
public enum DataDrivenExperimentDnaseOverlapExclusionType {

	COMPLETELY_DISCARD_INTERVAL(1), 
	PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL(2),
	NO_DISCARD(3);
	

	//We decided not to use this DnaseOverlapExclusion
	//Since we will create more than one interval from the given interval
	//And this may create unnecessary dependency among intervals 
	//PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS(2),
		
	private final int dnaseOverlapExclusionType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum class itself.
	 */
	private DataDrivenExperimentDnaseOverlapExclusionType( int dnaseOverlapExclusionType) {

		this.dnaseOverlapExclusionType = dnaseOverlapExclusionType;
	}

	public int getDnaseOverlapExclusionType() {

		return dnaseOverlapExclusionType;
	}

	public static DataDrivenExperimentDnaseOverlapExclusionType convertStringtoEnum( String dnaseOverlapExclusionType) {

		if( Commons.COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP.equals( dnaseOverlapExclusionType)){
			return COMPLETELY_DISCARD_INTERVAL;
		}
		
		else if( Commons.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP.equals( dnaseOverlapExclusionType)){
				return PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL;
		}
		
//		else if( Commons.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS_IN_CASE_OF_DNASE_OVERLAP.equals( dnaseOverlapExclusionType)){
//			return PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS;
//		}
		
		else if( Commons.NO_DISCARD.equals( dnaseOverlapExclusionType)){
			return NO_DISCARD;
			
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( DataDrivenExperimentDnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL))
			return Commons.COMPLETELY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
		
		else if( this.equals( DataDrivenExperimentDnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL))
			return Commons.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP;
		
//		else if( this.equals( DataDrivenExperimentDnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS))
//			return Commons.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS_IN_CASE_OF_DNASE_OVERLAP;
		
		else if( this.equals( DataDrivenExperimentDnaseOverlapExclusionType.NO_DISCARD))
			return Commons.NO_DISCARD;
		
		else
			return null;
	}

	public boolean isCompletelyDiscardInterval() {
		return ( this == DataDrivenExperimentDnaseOverlapExclusionType.COMPLETELY_DISCARD_INTERVAL);
	}

	public boolean isTakeTheLongestRemainingInterval() {
		return ( this == DataDrivenExperimentDnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_THE_LONGEST_REMAINING_INTERVAL);
	}

//	public boolean isPartiallyDiscardIntervalTakeAllTheRemainingIntervals() {
//		return ( this == DataDrivenExperimentDnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_TAKE_ALL_THE_REMAINING_INTERVALS);
//	}

	public boolean isNoDiscard() {
		return ( this == DataDrivenExperimentDnaseOverlapExclusionType.NO_DISCARD);
	}
	
}
