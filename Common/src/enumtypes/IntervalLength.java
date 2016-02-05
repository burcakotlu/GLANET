/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Feb 3, 2016
 * @project Common 
 *
 */
public enum IntervalLength {
	
	
	INTERVALLENGTH_100(1),
	INTERVALLENGTH_500(2),
	INTERVALLENGTH_1000(3),
	INTERVALLENGTH_5000(4),
	INTERVALLENGTH_10000(5),
	INTERVALLENGTH_100000(6);

	private final int intervalLength;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private IntervalLength(int intervalLength) {

		this.intervalLength = intervalLength;
	}

	public int getIntervalLength() {

		return intervalLength;
	}

	public static IntervalLength convertInttoEnum(int intervalLength) {

		if( Commons.INTERVALTREE_INTERVALLENGTH_100 == intervalLength){
			return INTERVALLENGTH_100;
		}else if( Commons.INTERVALTREE_INTERVALLENGTH_500 == intervalLength){
			return INTERVALLENGTH_500;
		}else if( Commons.INTERVALTREE_INTERVALLENGTH_1000 == intervalLength){
			return INTERVALLENGTH_1000;
		}else if( Commons.INTERVALTREE_INTERVALLENGTH_5000 == intervalLength){
			return INTERVALLENGTH_5000;
		}else if( Commons.INTERVALTREE_INTERVALLENGTH_10000 == intervalLength){
			return INTERVALLENGTH_10000;
		}else if( Commons.INTERVALTREE_INTERVALLENGTH_100000 == intervalLength){
			return INTERVALLENGTH_100000;
		}else
			return null;
	}

	public int convertEnumtoInt() {

		if( this.equals(INTERVALLENGTH_100))
			return Commons.INTERVALTREE_INTERVALLENGTH_100;
		else if( this.equals(INTERVALLENGTH_500))
			return Commons.INTERVALTREE_INTERVALLENGTH_500;
		else if( this.equals(INTERVALLENGTH_1000))
			return Commons.INTERVALTREE_INTERVALLENGTH_1000;
		else if( this.equals(INTERVALLENGTH_5000))
			return Commons.INTERVALTREE_INTERVALLENGTH_5000;
		else if( this.equals(INTERVALLENGTH_10000))
			return Commons.INTERVALTREE_INTERVALLENGTH_10000;
		else if( this.equals(INTERVALLENGTH_100000))
			return Commons.INTERVALTREE_INTERVALLENGTH_100000;
		else
			return 0;
	}

}
