/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Oct 23, 2015
 * @project Common 
 *
 */
public enum AssociationMeasureType {
	
	NUMBER_OF_OVERLAPPING_BASES(1),
	EXISTENCE_OF_OVERLAP(2);
	//NUMBER_OF_OVERLAPPING_INTERVALS(2),
	
	private final int associationMeasureType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private AssociationMeasureType(int associationMeasureType) {

		this.associationMeasureType = associationMeasureType;
	}

	public int getAssociationMeasureType() {

		return associationMeasureType;
	}

	public static AssociationMeasureType convertStringtoEnum( String associationMeasureType) {

		if( Commons.NUMBER_OF_OVERLAPPING_BASES.equals( associationMeasureType)){
			return NUMBER_OF_OVERLAPPING_BASES;
		}else if( Commons.NUMBER_OF_OVERLAPPING_BASES_SHORT.equals( associationMeasureType)){
			return NUMBER_OF_OVERLAPPING_BASES;
		}else if( Commons.EXISTENCE_OF_OVERLAP.equals( associationMeasureType)){
			return EXISTENCE_OF_OVERLAP;
		}else if( Commons.EXISTENCE_OF_OVERLAP_SHORT.equals( associationMeasureType)){
			return EXISTENCE_OF_OVERLAP;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( AssociationMeasureType.NUMBER_OF_OVERLAPPING_BASES))
			return Commons.NUMBER_OF_OVERLAPPING_BASES;
		else if( this.equals( AssociationMeasureType.EXISTENCE_OF_OVERLAP))
			return Commons.EXISTENCE_OF_OVERLAP;
		else
			return null;
	}
	

	public String convertEnumtoShortString() {

		if( this.equals( AssociationMeasureType.NUMBER_OF_OVERLAPPING_BASES))
			return Commons.NUMBER_OF_OVERLAPPING_BASES_SHORT;
		else if( this.equals( AssociationMeasureType.EXISTENCE_OF_OVERLAP))
			return Commons.EXISTENCE_OF_OVERLAP_SHORT;
		else
			return null;
	}
	
	

	public boolean isAssociationMeasureNumberOfOverlappingBases() {
		return this == NUMBER_OF_OVERLAPPING_BASES;
	}
	
	
	
	public boolean isAssociationMeasureExistenceofOverlap() {
		return this == EXISTENCE_OF_OVERLAP;
	}


}
