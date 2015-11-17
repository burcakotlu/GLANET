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
	
	ACTIVATOR(1),
	REPRESSOR(2),
	AMBIGIOUS(3);
	
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

		if( Commons.ACTIVATOR.equals(dataDrivenExperimentElementType)){
			return ACTIVATOR;
		}
		
		else if( Commons.REPRESSOR.equals(dataDrivenExperimentElementType)){
				return REPRESSOR;
		}
		
		else if( Commons.AMBIGIOUS.equals(dataDrivenExperimentElementType)){
			return AMBIGIOUS;
	}
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals(DataDrivenExperimentElementType.ACTIVATOR))
			return Commons.ACTIVATOR;
		
		else if( this.equals(DataDrivenExperimentElementType.REPRESSOR))
			return Commons.REPRESSOR;
		
		else if( this.equals(DataDrivenExperimentElementType.AMBIGIOUS))
			return Commons.AMBIGIOUS;
		
		else
			return null;
	}

	
	public boolean isActivator() {
		return ( this == DataDrivenExperimentElementType.ACTIVATOR);
	}
	
	
	public boolean isRepressor() {
		return ( this == DataDrivenExperimentElementType.REPRESSOR);
	}
	
	
	public boolean isAmbigious() {
		return ( this == DataDrivenExperimentElementType.AMBIGIOUS);
	}
	

}
