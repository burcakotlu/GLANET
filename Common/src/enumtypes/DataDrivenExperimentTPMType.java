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
	
	TOP1(1),
	TOP2(2),
	TOP5(3),
	TOP10(4),
	TOP20(5),
	TOP25(6),
	TOP50(7),
	TOP55(8),
	TOP60(9),
	
	TOPUNKNOWN(10),

	FIRSTELEMENT(11),
	LASTELEMENT(12);
	
	
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

		if( Commons.TOP1.equals(dataDrivenExperimentTopPercentageType)){
			return TOP1;
		}
		
		else if( Commons.TOP2.equals(dataDrivenExperimentTopPercentageType)){
				return TOP2;
		}
		
		else if (Commons.TOP5.equals(dataDrivenExperimentTopPercentageType)){
			return TOP5;
		}
		
		else if (Commons.TOP10.equals(dataDrivenExperimentTopPercentageType)){
			return TOP10;
		}
		
		else if (Commons.TOP20.equals(dataDrivenExperimentTopPercentageType)){
			return TOP20;
		}
		
		else if (Commons.TOP25.equals(dataDrivenExperimentTopPercentageType)){
			return TOP25;
		}
		
		else if (Commons.TOP50.equals(dataDrivenExperimentTopPercentageType)){
			return TOP50;
		}
		
		else if (Commons.TOP55.equals(dataDrivenExperimentTopPercentageType)){
			return TOP55;
		}
		
		else if (Commons.TOP60.equals(dataDrivenExperimentTopPercentageType)){
			return TOP60;
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

		if( this.equals(DataDrivenExperimentTPMType.TOP1))
			return Commons.TOP1;
		
		else if( this.equals(DataDrivenExperimentTPMType.TOP2))
			return Commons.TOP2;
		
		else if( this.equals(DataDrivenExperimentTPMType.TOP5))
			return Commons.TOP5;
		
		else if( this.equals(DataDrivenExperimentTPMType.TOP10))
			return Commons.TOP10;
		
		else if( this.equals(DataDrivenExperimentTPMType.TOP20))
			return Commons.TOP20;
		
		else if( this.equals(DataDrivenExperimentTPMType.TOP25))
			return Commons.TOP25;

		else if( this.equals(DataDrivenExperimentTPMType.TOP50))
			return Commons.TOP50;
		
		else if( this.equals(DataDrivenExperimentTPMType.TOP55))
			return Commons.TOP55;
	
		else if( this.equals(DataDrivenExperimentTPMType.TOP60))
			return Commons.TOP60;
		
		else if( this.equals(DataDrivenExperimentTPMType.TOPUNKNOWN))
			return Commons.TOPUNKNOWN;


		else
			return null;
	}

	public boolean isTOP1() {
		return ( this == DataDrivenExperimentTPMType.TOP1);
	}

	public boolean isTOP2() {
		return ( this == DataDrivenExperimentTPMType.TOP2);
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

	public boolean isTOP25() {
		return ( this == DataDrivenExperimentTPMType.TOP25);
	}
	
	public boolean isTOP50() {
		return ( this == DataDrivenExperimentTPMType.TOP50);
	}

	public boolean isTOP55() {
		return ( this == DataDrivenExperimentTPMType.TOP55);
	}
	public boolean isTOP60() {
		return ( this == DataDrivenExperimentTPMType.TOP60);
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
			return ((Integer)Integer.parseInt(e1.convertEnumtoString().substring(3))).compareTo(Integer.parseInt(e2.convertEnumtoString().substring(3)));
		}
	};
	
}
	

