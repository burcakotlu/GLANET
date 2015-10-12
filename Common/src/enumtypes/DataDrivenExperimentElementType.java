/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Oct 11, 2015
 * @project Common 
 *
 */
public enum DataDrivenExperimentElementType {
	
	EXPRESSOR(1),
	REPRESSOR(2),
	BIVALENT(3);
	
	private final int dataDrivenExperimentElementType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum class itself.
	 */
	private DataDrivenExperimentElementType( int dataDrivenExperimentElementType) {
		this.dataDrivenExperimentElementType = dataDrivenExperimentElementType;
	}

	public int getDataDrivenExperimentElementType() {
		return dataDrivenExperimentElementType;
	}

	public static DataDrivenExperimentElementType convertStringtoEnum(String dataDrivenExperimentElementType) {

		if( Commons.EXPRESSOR.equals(dataDrivenExperimentElementType)){
			return EXPRESSOR;
		}
		
		else if( Commons.REPRESSOR.equals(dataDrivenExperimentElementType)){
				return REPRESSOR;
		}
		
		else if( Commons.BIVALENT.equals(dataDrivenExperimentElementType)){
			return BIVALENT;
	}
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( DataDrivenExperimentElementType.EXPRESSOR))
			return Commons.EXPRESSOR;
		
		else if( this.equals( DataDrivenExperimentElementType.REPRESSOR))
			return Commons.REPRESSOR;
		
		else if( this.equals( DataDrivenExperimentElementType.BIVALENT))
			return Commons.BIVALENT;
		
		else
			return null;
	}

	
	public boolean isExpressor() {
		return ( this == DataDrivenExperimentElementType.EXPRESSOR);
	}
	
	
	public boolean isRepressor() {
		return ( this == DataDrivenExperimentElementType.REPRESSOR);
	}
	
	
	public boolean isBivalent() {
		return ( this == DataDrivenExperimentElementType.BIVALENT);
	}
	

}
