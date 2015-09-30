/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Sep 30, 2015
 * @project Common 
 *
 */
public enum DataDrivenExperimentTopPercentageType {
	
	TOP1PERCENTAGE(1),
	TOP2PERCENTAGE(2),
	TOP5PERCENTAGE(3),
	TOP10PERCENTAGE(4),
	TOP25PERCENTAGE(5),
	TOP50PERCENTAGE(6),
	TOP55PERCENTAGE(7),
	TOP60PERCENTAGE(8),
	
	FIRSTELEMENT(9),
	LASTELEMENT(10);
	
	
	private final int dataDrivenExperimentTopPercentageType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum class itself.
	 */
	private DataDrivenExperimentTopPercentageType( int dataDrivenExperimentTopPercentageType) {
		this.dataDrivenExperimentTopPercentageType = dataDrivenExperimentTopPercentageType;
	}

	public int getDataDrivenExperimentTopPercentageType() {
		return dataDrivenExperimentTopPercentageType;
	}

	public static DataDrivenExperimentTopPercentageType convertStringtoEnum( String dataDrivenExperimentTopPercentageType) {

		if( Commons.TOP1PERCENTAGE.equals(dataDrivenExperimentTopPercentageType)){
			return TOP1PERCENTAGE;
		}
		
		else if( Commons.TOP2PERCENTAGE.equals(dataDrivenExperimentTopPercentageType)){
				return TOP2PERCENTAGE;
		}
		
		else if (Commons.TOP5PERCENTAGE.equals(dataDrivenExperimentTopPercentageType)){
			return TOP5PERCENTAGE;
		}
		
		else if (Commons.TOP10PERCENTAGE.equals(dataDrivenExperimentTopPercentageType)){
			return TOP10PERCENTAGE;
		}
		
		else if (Commons.TOP25PERCENTAGE.equals(dataDrivenExperimentTopPercentageType)){
			return TOP25PERCENTAGE;
		}
		
		else if (Commons.TOP50PERCENTAGE.equals(dataDrivenExperimentTopPercentageType)){
			return TOP50PERCENTAGE;
		}
		
		else if (Commons.TOP55PERCENTAGE.equals(dataDrivenExperimentTopPercentageType)){
			return TOP55PERCENTAGE;
		}
		
		else if (Commons.TOP60PERCENTAGE.equals(dataDrivenExperimentTopPercentageType)){
			return TOP60PERCENTAGE;
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

		if( this.equals(DataDrivenExperimentTopPercentageType.TOP1PERCENTAGE))
			return Commons.TOP1PERCENTAGE;
		
		else if( this.equals(DataDrivenExperimentTopPercentageType.TOP2PERCENTAGE))
			return Commons.TOP2PERCENTAGE;
		
		else if( this.equals(DataDrivenExperimentTopPercentageType.TOP5PERCENTAGE))
			return Commons.TOP5PERCENTAGE;
		
		else if( this.equals(DataDrivenExperimentTopPercentageType.TOP10PERCENTAGE))
			return Commons.TOP10PERCENTAGE;
		
		else if( this.equals(DataDrivenExperimentTopPercentageType.TOP25PERCENTAGE))
			return Commons.TOP25PERCENTAGE;

		else if( this.equals(DataDrivenExperimentTopPercentageType.TOP50PERCENTAGE))
			return Commons.TOP50PERCENTAGE;
	
		else if( this.equals(DataDrivenExperimentTopPercentageType.TOP60PERCENTAGE))
			return Commons.TOP60PERCENTAGE;

		else
			return null;
	}

	public boolean isTOP1PERCENTAGE() {
		return ( this == DataDrivenExperimentTopPercentageType.TOP1PERCENTAGE);
	}

	public boolean isTOP2PERCENTAGE() {
		return ( this == DataDrivenExperimentTopPercentageType.TOP2PERCENTAGE);
	}

	public boolean isTOP5PERCENTAGE() {
		return ( this == DataDrivenExperimentTopPercentageType.TOP5PERCENTAGE);
	}

	public boolean isTOP10PERCENTAGE() {
		return ( this == DataDrivenExperimentTopPercentageType.TOP10PERCENTAGE);
	}

	public boolean isTOP25PERCENTAGE() {
		return ( this == DataDrivenExperimentTopPercentageType.TOP25PERCENTAGE);
	}
	
	public boolean isTOP50PERCENTAGE() {
		return ( this == DataDrivenExperimentTopPercentageType.TOP50PERCENTAGE);
	}

	public boolean isTOP55PERCENTAGE() {
		return ( this == DataDrivenExperimentTopPercentageType.TOP55PERCENTAGE);
	}
	public boolean isTOP60PERCENTAGE() {
		return ( this == DataDrivenExperimentTopPercentageType.TOP60PERCENTAGE);
	}

	public boolean isFIRSTELEMENT() {
		return ( this == DataDrivenExperimentTopPercentageType.FIRSTELEMENT);
	}
	
	public boolean isLASTELEMENT() {
		return ( this == DataDrivenExperimentTopPercentageType.LASTELEMENT);
	}

}
