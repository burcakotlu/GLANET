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
	
	POL2(1), 		//activator
	H3K4ME1(2), 	//activator
	H3K4ME2(3), 	//activator
	H3K4ME3(4), 	//activator
	H3K79ME2(5), 	//activator
	H4K20ME1(6), 	//activator
	H3K9ACB(7), 	//activator
	H3K27AC(8),		//activator
	H2AZ(9),		//activator
	H3K9AC(10),		//activator
	
	H3K27ME3(11), 	//repressor
	H3K9ME2(12), //repressor
	
	H3K36ME3B(13), 	//unknown
	H3K9ME1(14), 	//unknown
	H3K9ME3(15),	//unknown
	H3K36ME3(16); 	//unknown
		

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
		}else if( Commons.H3K9ME2.equals(dataDrivenExperimentElementNameType)){
			return H3K9ME2;
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
		
		else if( this.equals( DataDrivenExperimentElementNameType.H3K9ME2))
			return Commons.H3K9ME2;
		
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

	
	
	public boolean isH3K79ME2() {
		return ( this == DataDrivenExperimentElementNameType.H3K79ME2);
	}
	
	public boolean isH4K20ME1() {
		return ( this == DataDrivenExperimentElementNameType.H4K20ME1);
	}
	
	public boolean isH3K9ACB() {
		return ( this == DataDrivenExperimentElementNameType.H3K9ACB);
	}
	
	public boolean isH3K27ME3() {
		return ( this == DataDrivenExperimentElementNameType.H3K27ME3);
	}
	
	public boolean isH3K27AC() {
		return ( this == DataDrivenExperimentElementNameType.H3K27AC);
	}
	
	public boolean isH3K36ME3B() {
		return ( this == DataDrivenExperimentElementNameType.H3K36ME3B);
	}
	
	public boolean isH3K9ME1() {
		return ( this == DataDrivenExperimentElementNameType.H3K9ME1);
	}
	
	
	public boolean isH3K9ME3() {
		return ( this == DataDrivenExperimentElementNameType.H3K9ME3);
	}
	
	public boolean isH2AZ() {
		return ( this == DataDrivenExperimentElementNameType.H2AZ);
	}
	
	public boolean isH3K36ME3() {
		return ( this == DataDrivenExperimentElementNameType.H3K36ME3);
	}
	
	public boolean isH3K9AC() {
		return ( this == DataDrivenExperimentElementNameType.H3K9AC);
	}

	
	public boolean isActivator() {
		

	
		if (this == DataDrivenExperimentElementNameType.POL2 ||
			this == DataDrivenExperimentElementNameType.H3K4ME2 ||
			this == DataDrivenExperimentElementNameType.H3K4ME3 ||
			this == DataDrivenExperimentElementNameType.H3K79ME2 ||
			this == DataDrivenExperimentElementNameType.H3K9ACB ||
			this == DataDrivenExperimentElementNameType.H3K27AC  ||
			this == DataDrivenExperimentElementNameType.H2AZ ||
			this == DataDrivenExperimentElementNameType.H3K9AC){
			return true;
		}
		return false;
		
	}

	public boolean isRepressor() {
		
		if (this == DataDrivenExperimentElementNameType.H3K27ME3  ||
			this == DataDrivenExperimentElementNameType.H3K9ME2){
			return true;
		}
		return false;
	}
	
	public boolean isAmbigious() {
		
		if (this == DataDrivenExperimentElementNameType.H3K36ME3B ||
			this == DataDrivenExperimentElementNameType.H3K9ME1 ||
			this == DataDrivenExperimentElementNameType.H3K9ME3 ||
			this == DataDrivenExperimentElementNameType.H3K36ME3 ||
			this == DataDrivenExperimentElementNameType.H3K4ME1 ||
			this == DataDrivenExperimentElementNameType.H4K20ME1){
			return true;
		}
		return false;
	}
	
	
	
}
