/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Feb 26, 2016
 * @project Common 
 *
 */
public enum IsochoreFamilyMode {
	
	DO_USE_ISOCHORE_FAMILY( 1),
	DO_NOT_USE_ISOCHORE_FAMILY( 2);

	private final int isochoreFamilyMode;

	public int getIsochoreFamilyMode() {
		return isochoreFamilyMode;
	}

	private IsochoreFamilyMode(int isochoreFamilyMode) {
		this.isochoreFamilyMode = isochoreFamilyMode;
	}

	public static IsochoreFamilyMode convertStringtoEnum(
			String isochoreFamilyMode) {

		if( Commons.DO_USE_ISOCHORE_FAMILY.equals(isochoreFamilyMode) || Commons.WIF.equals(isochoreFamilyMode)){
			return DO_USE_ISOCHORE_FAMILY;
		}else if( Commons.DO_NOT_USE_ISOCHORE_FAMILY.equals(isochoreFamilyMode) ||  Commons.WOIF.equals(isochoreFamilyMode)){
			return DO_NOT_USE_ISOCHORE_FAMILY;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals(DO_USE_ISOCHORE_FAMILY))
			return Commons.DO_USE_ISOCHORE_FAMILY;
		else if( this.equals(DO_NOT_USE_ISOCHORE_FAMILY))
			return Commons.DO_NOT_USE_ISOCHORE_FAMILY;
		else
			return null;

	}
	
	public String convertEnumtoShortString() {

		if( this.equals(DO_USE_ISOCHORE_FAMILY))
			return Commons.WIF;
		else if( this.equals(DO_NOT_USE_ISOCHORE_FAMILY))
			return Commons.WOIF;
		else
			return null;

	}

	/** An added method.  */
	public boolean useIsochoreFamily() {
		return this == IsochoreFamilyMode.DO_USE_ISOCHORE_FAMILY;
	}

	public boolean doNotUseIsochoreFamily() {
		return this == IsochoreFamilyMode.DO_NOT_USE_ISOCHORE_FAMILY;
	}

}
