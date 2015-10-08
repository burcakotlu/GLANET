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
	
	H3K4ME1(2), 	//expressor
	H3K4ME2(3), 	//expressor
	H3K4ME3(4), 	//expressor
	H3K27ME3(5), 	//repressor
	H3K9ME3(6),		//repressor
	H3K27AC(7),		//repressor
	
	H2AZ(8),	
	H3K36ME3(9),
	H3K79ME2(10),
	H3K9AC(11),
	H4K20ME1(12),
	H3K9ME1(13),
	H3K9ACB(14),
	H3K36ME3B(15);
	
	
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
		}else if( Commons.H3K27AC.equals(dataDrivenExperimentElementNameType)){
			return H3K27AC;
		}
		
		
		
		else if( Commons.H2AZ.equals(dataDrivenExperimentElementNameType)){
			return H2AZ;
		}
		else if( Commons.H3K36ME3.equals(dataDrivenExperimentElementNameType)){
			return H3K36ME3;
		}
		else if( Commons.H3K79ME2.equals(dataDrivenExperimentElementNameType)){
			return H3K79ME2;
		}
		else if( Commons.H3K9AC.equals(dataDrivenExperimentElementNameType)){
			return H3K9AC;
		}
		else if( Commons.H4K20ME1.equals(dataDrivenExperimentElementNameType)){
			return H4K20ME1;
		}
		else if( Commons.H3K9ME1.equals(dataDrivenExperimentElementNameType)){
			return H3K9ME1;
		}
		else if( Commons.H3K9ACB.equals(dataDrivenExperimentElementNameType)){
			return H3K9ACB;
		}
		else if( Commons.H3K36ME3B.equals(dataDrivenExperimentElementNameType)){
			return H3K36ME3B;
		}
		
		
	
		
		else
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
		
		else if( this.equals( DataDrivenExperimentElementNameType.H3K27AC))
			return Commons.H3K27AC;
		
		
		
		else if( this.equals(DataDrivenExperimentElementNameType.H2AZ)){
			return Commons.H2AZ;
		}
		else if( this.equals(DataDrivenExperimentElementNameType.H3K36ME3)){
			return Commons.H3K36ME3;
		}
		else if( this.equals(DataDrivenExperimentElementNameType.H3K79ME2)){
			return Commons.H3K79ME2;
		}
		else if( this.equals(DataDrivenExperimentElementNameType.H3K9AC)){
			return Commons.H3K9AC;
		}
		else if( this.equals(DataDrivenExperimentElementNameType.H4K20ME1)){
			return Commons.H4K20ME1;
		}
		else if( this.equals(DataDrivenExperimentElementNameType.H3K9ME1)){
			return Commons.H3K9ME1;
		}
		else if( this.equals(DataDrivenExperimentElementNameType.H3K9ACB)){
			return Commons.H3K9ACB;
		}
		else if( this.equals(DataDrivenExperimentElementNameType.H3K36ME3B)){
			return Commons.H3K36ME3B;
		}
		
		

		
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
	
	public boolean isH3K27AC() {
		return ( this == DataDrivenExperimentElementNameType.H3K27AC);
	}
	
}
