/**
 * 
 */
package enumtypes;

import java.util.Comparator;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Sep 30, 2015
 * @project Common 
 *
 */
public enum DataDrivenExperimentTPMType {
	
	TOPUNKNOWN(1),
	TOP5(2),
	TOP10(3),
	TOP20(4),

	
	FIRSTELEMENT(5),
	LASTELEMENT(6);
	
	
	private final int tpmType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum class itself.
	 */
	private DataDrivenExperimentTPMType( int tpmType) {
		this.tpmType = tpmType;
	}

	public int getDataDrivenExperimentTopPercentageType() {
		return tpmType;
	}

	public static DataDrivenExperimentTPMType convertStringtoEnum( String dataDrivenExperimentTopPercentageType) {

		
		
		if (Commons.TOP5.equals(dataDrivenExperimentTopPercentageType)){
			return TOP5;
		}
		
		else if (Commons.TOP10.equals(dataDrivenExperimentTopPercentageType)){
			return TOP10;
		}
		
		else if (Commons.TOP20.equals(dataDrivenExperimentTopPercentageType)){
			return TOP20;
		}
		
		
		
		else if (Commons.TOPUNKNOWN.equals(dataDrivenExperimentTopPercentageType)){
			return TOPUNKNOWN;
		}

		else if (Commons.FIRSTELEMENT.equals(dataDrivenExperimentTopPercentageType)){
			return FIRSTELEMENT;
		}
		
		else if (Commons.LASTELEMENT.equals(dataDrivenExperimentTopPercentageType)){
			return LASTELEMENT;
		}

		else
			return null;
	}

	public String convertEnumtoString() {

		
		if( this.equals(DataDrivenExperimentTPMType.TOP5))
			return Commons.TOP5;
		
		else if( this.equals(DataDrivenExperimentTPMType.TOP10))
			return Commons.TOP10;
		
		else if( this.equals(DataDrivenExperimentTPMType.TOP20))
			return Commons.TOP20;
		
		
		
		else if( this.equals(DataDrivenExperimentTPMType.TOPUNKNOWN))
			return Commons.TOPUNKNOWN;


		else
			return null;
	}

	
	public boolean isTOP5() {
		return ( this == DataDrivenExperimentTPMType.TOP5);
	}

	public boolean isTOP10() {
		return ( this == DataDrivenExperimentTPMType.TOP10);
	}
	
	public boolean isTOP20() {
		return ( this == DataDrivenExperimentTPMType.TOP20);
	}

	
	public boolean isTOPUnknown() {
		return ( this == DataDrivenExperimentTPMType.TOPUNKNOWN);
	}

	public boolean isFIRSTELEMENT() {
		return ( this == DataDrivenExperimentTPMType.FIRSTELEMENT);
	}
	
	public boolean isLASTELEMENT() {
		return ( this == DataDrivenExperimentTPMType.LASTELEMENT);
	}
	
	
	public static Comparator<DataDrivenExperimentTPMType> TPM_TYPE = new Comparator<DataDrivenExperimentTPMType>() {

		//Get the integer after TOP
		//Therefore it is set to 3
		public int compare(DataDrivenExperimentTPMType e1,DataDrivenExperimentTPMType e2) {
			if (e1.isTOPUnknown() || e2.isTOPUnknown()){
				return -1;
			}
			
			return ((Integer)Integer.parseInt(e1.convertEnumtoString().substring(3))).compareTo(Integer.parseInt(e2.convertEnumtoString().substring(3)));
		}
	};
	
}
	

