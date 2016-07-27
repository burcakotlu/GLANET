/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Aug 17, 2015
 * @project Common 
 *
 */
public enum DataDrivenExperimentCellLineType {
	
	GM12878(1),
	K562(2);
	
	
	private final int dataDrivenExperimentCellLineType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum class itself.
	 */
	private DataDrivenExperimentCellLineType( int dataDrivenExperimentCellLineType) {

		this.dataDrivenExperimentCellLineType = dataDrivenExperimentCellLineType;
	}

	public int getDataDrivenExperimentCellLineType() {

		return dataDrivenExperimentCellLineType;
	}

	public static DataDrivenExperimentCellLineType convertStringtoEnum( String dataDrivenExperimentCellLineType) {

		if( Commons.GM12878.equals(dataDrivenExperimentCellLineType)){
			return GM12878;
		}
		
		else if( Commons.K562.equals(dataDrivenExperimentCellLineType)){
				return K562;
		}
		
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( DataDrivenExperimentCellLineType.GM12878))
			return Commons.GM12878;
		
		else if( this.equals( DataDrivenExperimentCellLineType.K562))
			return Commons.K562;
		
		else
			return null;
	}

	public boolean isGM12878() {
		return ( this == DataDrivenExperimentCellLineType.GM12878);
	}

	public boolean isK562() {
		return ( this == DataDrivenExperimentCellLineType.K562);
	}

	

}
