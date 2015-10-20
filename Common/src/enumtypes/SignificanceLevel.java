/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Oct 20, 2015
 * @project Common 
 *
 */
public enum SignificanceLevel {
	
	SIG_LEVEL_0_001(1),
	SIG_LEVEL_0_01(2),
	SIG_LEVEL_0_05(3);

	private final int significanceLevel;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private SignificanceLevel(int significanceLevel) {

		this.significanceLevel = significanceLevel;
	}

	public int getSignificanceLevel() {

		return significanceLevel;
	}

	public static SignificanceLevel convertStringtoEnum(String significanceLevel) {

		if( Commons.SIG_LEVEL_0POINT001.equals(significanceLevel)){
			return SIG_LEVEL_0_001;
		}else if( Commons.SIG_LEVEL_0POINT01.equals(significanceLevel)){
			return SIG_LEVEL_0_01;
		}else if( Commons.SIG_LEVEL_0POINT05.equals(significanceLevel)){
			return SIG_LEVEL_0_05;
		}
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( SignificanceLevel.SIG_LEVEL_0_001))
			return Commons.SIG_LEVEL_0_001;
		else if( this.equals( SignificanceLevel.SIG_LEVEL_0_01))
			return Commons.SIG_LEVEL_0_01;
		else if( this.equals( SignificanceLevel.SIG_LEVEL_0_05))
			return Commons.SIG_LEVEL_0_05;
		else
			return null;
	}

	public boolean isSignificanceLevel_0_001() {
		return this == SIG_LEVEL_0_001;
	}

	public boolean isSignificanceLevel_0_01() {
		return this == SIG_LEVEL_0_01;
	}

	public boolean isSignificanceLevel_0_05() {
		return this == SIG_LEVEL_0_05;
	}

}
