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
public enum PointType {
	
	LEFT_END_POINT(1),
	RIGHT_END_POINT(2);
	
	private final int pointType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private PointType(int pointType) {

		this.pointType = pointType;
	}

	public int getPointType() {

		return pointType;
	}

	public static PointType convertStringtoEnum(String pointType) {

		if( Commons.LEFT_END_POINT.equals(pointType)){
			return LEFT_END_POINT;
		}else if( Commons.RIGHT_END_POINT.equals(pointType)){
			return RIGHT_END_POINT;
		}
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( PointType.LEFT_END_POINT))
			return Commons.LEFT_END_POINT;
		else if( this.equals( PointType.RIGHT_END_POINT))
			return Commons.RIGHT_END_POINT;
		else
			return null;
	}


	

}
