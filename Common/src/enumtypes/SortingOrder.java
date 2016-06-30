/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Jun 30, 2016
 * @project Common 
 *
 */
public enum SortingOrder {
	
	SORTING_IN_DESCENDING_ORDER(1),
	SORTING_IN_ASCENDING_ORDER(2);
	
	
	private final int sortingOrder;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private SortingOrder(int sortingOrder) {

		this.sortingOrder = sortingOrder;
	}

	public int getSortingOrder() {

		return sortingOrder;
	}

	public static SortingOrder convertStringtoEnum(String sortingOrder) {

		if( Commons.SORTING_IN_DESCENDING_ORDER.equals(sortingOrder)){
			return SORTING_IN_DESCENDING_ORDER;
		}else if( Commons.SORTING_IN_ASCENDING_ORDER.equals(sortingOrder)){
			return SORTING_IN_ASCENDING_ORDER;
		}
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( SortingOrder.SORTING_IN_DESCENDING_ORDER))
			return Commons.SORTING_IN_DESCENDING_ORDER;
		else if( this.equals( SortingOrder.SORTING_IN_ASCENDING_ORDER))
			return Commons.SORTING_IN_ASCENDING_ORDER;
		else
			return null;
	}

	public boolean isSortingInDescendingOrder() {
		return this == SORTING_IN_DESCENDING_ORDER;
	}

	public boolean isSortingInAscendingOrder() {
		return this == SORTING_IN_ASCENDING_ORDER;
	}

}
