/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Aug 22, 2015
 * @project Common 
 *
 */
public enum DataDrivenExperimentElementNameType {
	
	POL2(1), 		//expressor
	H3K4ME3(2), 	//expressor
	H3K4ME1(3), 	//expressor
	H3K4ME2(4), 	//expressor
	H3K27ME3(5), 	//repressor
	H3K9ME3(6);		//repressor
	
	
	private final int dataDrivenExperimentElementNameType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum class itself.
	 */
	private DataDrivenExperimentElementNameType( int dataDrivenExperimentElementNameType) {

		this.dataDrivenExperimentElementNameType = dataDrivenExperimentElementNameType;
	}

	public int getDataDrivenExperimentElementNameType() {

		return dataDrivenExperimentElementNameType;
	}

	public static DataDrivenExperimentElementNameType convertStringtoEnum( String dataDrivenExperimentElementNameType) {

		if( Commons.POL2.equals(dataDrivenExperimentElementNameType)){
			return POL2;
		}else if( Commons.H3K4ME1.equals(dataDrivenExperimentElementNameType)){
			return H3K4ME1;
		}else if( Commons.H3K4ME2.equals(dataDrivenExperimentElementNameType)){
			return H3K4ME2;
		}else if( Commons.H3K4ME3.equals(dataDrivenExperimentElementNameType)){
			return H3K4ME3;
		}else if( Commons.H3K27ME3.equals(dataDrivenExperimentElementNameType)){
			return H3K27ME3;
		}else if( Commons.H3K9ME3.equals(dataDrivenExperimentElementNameType)){
			return H3K9ME3;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( DataDrivenExperimentElementNameType.POL2))
			return Commons.POL2;
		
		else if( this.equals( DataDrivenExperimentElementNameType.H3K4ME1))
			return Commons.H3K4ME1;
		
		else if( this.equals( DataDrivenExperimentElementNameType.H3K4ME2))
			return Commons.H3K4ME2;
		
		else if( this.equals( DataDrivenExperimentElementNameType.H3K4ME3))
			return Commons.H3K4ME3;
		
		else if( this.equals( DataDrivenExperimentElementNameType.H3K27ME3))
			return Commons.H3K27ME3;
		
		else if( this.equals( DataDrivenExperimentElementNameType.H3K9ME3))
			return Commons.H3K9ME3;
		
		else
			return null;
	}

	public boolean isPOL2() {
		return ( this == DataDrivenExperimentElementNameType.POL2);
	}

	public boolean isH3K4ME1() {
		return ( this == DataDrivenExperimentElementNameType.H3K4ME1);
	}

	public boolean isH3K4ME2() {
		return ( this == DataDrivenExperimentElementNameType.H3K4ME2);
	}
	
	public boolean isH3K4ME3() {
		return ( this == DataDrivenExperimentElementNameType.H3K4ME3);
	}
	
	public boolean isH3K27ME3() {
		return ( this == DataDrivenExperimentElementNameType.H3K27ME3);
	}
	
	public boolean isH3K9ME3() {
		return ( this == DataDrivenExperimentElementNameType.H3K9ME3);
	}
}
