/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Bur�ak Otlu
 * @date Apr 21, 2015
 * @project Common 
 *
 */
public enum IsochoreFamily {

	L1( 1),
	L2( 2),
	H1( 3),
	H2( 4),
	H3( 5);

	private final int isochoreFamily;

	private IsochoreFamily( int isochoreFamily) {

		this.isochoreFamily = isochoreFamily;
	}

	public int getIsochoreFamily() {

		return isochoreFamily;
	}

	public static IsochoreFamily convertInttoEnum( int isochoreFamily) {

		if( 1 == isochoreFamily){
			return L1;
		}else if( 2 == isochoreFamily){ return L2; }
		if( 3 == isochoreFamily){
			return H1;
		}else if( 4 == isochoreFamily){
			return H2;
		}else if( 5 == isochoreFamily){
			return H3;
		}else
			return null;
	}

	public static IsochoreFamily convertStringtoEnum( String isochoreFamily) {

		if( Commons.L1.equals( isochoreFamily)){
			return L1;
		}else if( Commons.L2.equals( isochoreFamily)){ return L2; }
		if( Commons.H1.equals( isochoreFamily)){
			return H1;
		}else if( Commons.H2.equals( isochoreFamily)){
			return H2;
		}else if( Commons.H3.equals( isochoreFamily)){
			return H3;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( IsochoreFamily.L1))
			return Commons.L1;
		else if( this.equals( IsochoreFamily.L2))
			return Commons.L2;
		if( this.equals( IsochoreFamily.H1))
			return Commons.H1;
		else if( this.equals( IsochoreFamily.H2))
			return Commons.H2;
		else if( this.equals( IsochoreFamily.H3))
			return Commons.H3;
		else
			return null;
	}

}
