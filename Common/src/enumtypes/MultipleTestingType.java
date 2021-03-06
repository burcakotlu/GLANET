/**
 * @author burcakotlu
 * @date Jun 24, 2014 
 * @time 4:14:21 PM
 */
package enumtypes;

import common.Commons;

/**
 * 
 */
public enum MultipleTestingType {

	EMPIRICAL_P_VALUE( 1), 
	BONFERRONI_CORRECTION( 2), 
	BENJAMINI_HOCHBERG_FDR( 3);

	private final int multipleTestingType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private MultipleTestingType( int multipleTestingType) {

		this.multipleTestingType = multipleTestingType;
	}

	public int getMultipleTestingType() {

		return multipleTestingType;
	}

	public String convertEnumtoString() {

		if( this.equals( MultipleTestingType.EMPIRICAL_P_VALUE))
			return Commons.EMPIRICAL_P_VALUE;
		else if( this.equals( MultipleTestingType.BONFERRONI_CORRECTION))
			return Commons.BONFERRONI_CORRECTION;
		else if( this.equals( MultipleTestingType.BENJAMINI_HOCHBERG_FDR))
			return Commons.BENJAMINI_HOCHBERG_FDR;
		else
			return null;

	}

	public static MultipleTestingType convertStringtoEnum( String multipleTestingType) {

		if( Commons.EMPIRICAL_P_VALUE.equals( multipleTestingType)){
			return EMPIRICAL_P_VALUE;
		}else if( Commons.BONFERRONI_CORRECTION.equals( multipleTestingType)){
			return BONFERRONI_CORRECTION;
		}else if( Commons.BENJAMINI_HOCHBERG_FDR.equals( multipleTestingType)){
			return BENJAMINI_HOCHBERG_FDR;
		}else
			return null;
	}

	/** An added method.  */
	public boolean isEmpiricalPValue() {

		return this == EMPIRICAL_P_VALUE;
	}

	/** An added method.  */
	public boolean isBonferroniCorrection() {

		return this == BONFERRONI_CORRECTION;
	}

	/** An added method.  */
	public boolean isBenjaminiHochbergFDR() {

		return this == BENJAMINI_HOCHBERG_FDR;
	}

}
