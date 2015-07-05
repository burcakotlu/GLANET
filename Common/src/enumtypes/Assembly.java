package enumtypes;

import common.Commons;

public enum Assembly {

	GRCh37_p13( 1), GRCh38( 2);

	private final int assembly;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private Assembly( int assembly) {

		this.assembly = assembly;
	}

	public int getAssembly() {

		return assembly;
	}

	public static Assembly convertStringtoEnum( String assembly) {

		if( Commons.GRCH37_P13.equals( assembly)){
			return GRCh37_p13;
		}else if( Commons.GRCH38.equals( assembly)){
			return GRCh38;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( Assembly.GRCh37_p13))
			return Commons.GRCH37_P13;
		else if( this.equals( Assembly.GRCh38))
			return Commons.GRCH38;
		else
			return null;
	}

	public boolean isAssemblyGRCh37_p13() {

		return this == GRCh37_p13;
	}

	public boolean isAssemblyGRCh38() {

		return this == GRCh38;
	}

}
