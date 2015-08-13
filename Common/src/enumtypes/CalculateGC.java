/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Bur�ak Otlu
 * @date Apr 24, 2015
 * @project Common 
 *
 */
public enum CalculateGC {

	CALCULATE_GC_USING_GC_BYTE_LIST( 1),
	CALCULATE_GC_USING_GC_INTERVAL_TREE( 2),
	CALCULATE_GC_USING_GC_ISOCHORE_INTERVAL_TREE( 3);

	private final int calculateGC;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private CalculateGC( int calculateGC) {

		this.calculateGC = calculateGC;
	}

	public int getCalculateGC() {

		return calculateGC;
	}

	public static CalculateGC convertStringtoEnum( String calculateGC) {

		if( Commons.CALCULATE_GC_USING_GC_BYTE_LIST.equals( calculateGC)){
			return CALCULATE_GC_USING_GC_BYTE_LIST;
		}else if( Commons.CALCULATE_GC_USING_GC_INTERVAL_TREE.equals( calculateGC)){
			return CALCULATE_GC_USING_GC_INTERVAL_TREE;
		}else if( Commons.CALCULATE_GC_USING_GC_ISOCHORE_INTERVAL_TREE.equals( calculateGC)){
			return CALCULATE_GC_USING_GC_ISOCHORE_INTERVAL_TREE;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( CALCULATE_GC_USING_GC_BYTE_LIST))
			return Commons.CALCULATE_GC_USING_GC_BYTE_LIST;
		else if( this.equals( CALCULATE_GC_USING_GC_INTERVAL_TREE))
			return Commons.CALCULATE_GC_USING_GC_INTERVAL_TREE;
		else if( this.equals( CALCULATE_GC_USING_GC_ISOCHORE_INTERVAL_TREE))
			return Commons.CALCULATE_GC_USING_GC_ISOCHORE_INTERVAL_TREE;
		else
			return null;
	}

	public boolean isCalculateGCUsingByteList() {

		return this == CALCULATE_GC_USING_GC_BYTE_LIST;
	}

	public boolean isCalculateGCUsingGCIntervalTree() {

		return this == CALCULATE_GC_USING_GC_INTERVAL_TREE;
	}

	public boolean isCalculateGCUsingGCIsochoreIntervalTree() {

		return this == CALCULATE_GC_USING_GC_ISOCHORE_INTERVAL_TREE;
	}
}
