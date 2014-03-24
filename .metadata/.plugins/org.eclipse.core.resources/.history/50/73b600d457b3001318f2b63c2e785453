/**
 * @author Burcak Otlu
 * Nov 4, 2013
 * 2:26:27 PM
 * 2013
 *
 * 
 */
package comparison.binomialversuspermutation;


public class ComparisonElement implements Comparable<ComparisonElement>{
	
	String name;
	Float adjustedPValue;
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getAdjustedPValue() {
		return adjustedPValue;
	}

	public void setAdjustedPValue(Float adjustedPValue) {
		this.adjustedPValue = adjustedPValue;
	}
	
	
	
	static Comparator<ComparisonElement> ADJUSTED_P_VALUE = 
    		new Comparator<ComparisonElement>() {
				public int compare(ComparisonElement element1, ComparisonElement element2) {					
					return element1.getAdjustedPValue().compareTo(element2.getAdjustedPValue());
				}
    };
    
    

	public ComparisonElement(String name, Float adjustedPValue) {
		super();
		this.name = name;
		this.adjustedPValue = adjustedPValue;
	}

	/**
	 * 
	 */
	public ComparisonElement() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(ComparisonElement element) {
		if (this.adjustedPValue < element.getAdjustedPValue())
			return 1;
		else if (this.adjustedPValue == element.getAdjustedPValue())
			return 0;
		else 
			return -1;
	}

}
