/**
 * @author burcakotlu
 * @date Aug 9, 2014 
 * @time 1:05:07 PM
 */
package annotate.intervals.parametric;

/**
 * 
 */
public class TfNameandCellLineNameOverlapWithNumbers {
	//10., 9., 8. and 7. digits have tfNumber
	//6., 5. and 4. digits have cellLineNumber	
	//3., 2. and 1. digits have cellLineNumber
	
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
	
	public TfNameandCellLineNameOverlapWithNumbers(int tfNumberCellLineNumber,
			int low, int high) {
		super();
		this.tfNumberCellLineNumber = tfNumberCellLineNumber;
		this.low = low;
		this.high = high;
	}
	

}
