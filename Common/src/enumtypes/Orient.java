package enumtypes;

import common.Commons;

public enum Orient {

	FORWARD( 1), REVERSE( 2);

	private final int orient;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private Orient( int orient) {

		this.orient = orient;
	}

	public int getOrient() {

		return orient;
	}

	public static Orient convertStringtoEnum( String orient) {

		if( Commons.FORWARD.equals( orient)){
			return FORWARD;
		}else if( Commons.REVERSE.equals( orient)){
			return REVERSE;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( Orient.FORWARD))
			return Commons.FORWARD;
		else if( this.equals( Orient.REVERSE))
			return Commons.REVERSE;
		else
			return null;
	}

	public boolean isForward() {

		return this == FORWARD;
	}

}
