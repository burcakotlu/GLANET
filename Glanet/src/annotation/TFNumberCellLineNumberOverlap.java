/**
 * 
 */
package annotation;

/**
 * @author Burçak Otlu
 * @date Jul 9, 2015
 * @project Glanet 
 *
 */
public class TFNumberCellLineNumberOverlap {
	
	int tfNumberCellLineNumber;
	int low;
	int high;
	
	
	public int getTfNumberCellLineNumber() {
		return tfNumberCellLineNumber;
	}
	public void setTfNumberCellLineNumber(int tfNumberCellLineNumber) {
		this.tfNumberCellLineNumber = tfNumberCellLineNumber;
	}
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	
	
	public TFNumberCellLineNumberOverlap( 
			int tfNumberCellLineNumber, 
			int low,
			int high) {

		super();
		this.tfNumberCellLineNumber = tfNumberCellLineNumber;
		this.low = low;
		this.high = high;
	}
	

}
