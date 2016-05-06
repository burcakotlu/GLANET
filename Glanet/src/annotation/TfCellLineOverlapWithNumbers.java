/**
 * @author burcakotlu
 * @date Aug 9, 2014 
 * @time 1:05:07 PM
 */
package annotation;

/**
 * 
 */
public class TfCellLineOverlapWithNumbers {

	

	int tfNumberCellLineNumber;
	int low;
	int high;

	public int getTfNumberCellLineNumber() {

		return tfNumberCellLineNumber;
	}

	public void setTfNumberCellLineNumber( int tfNumberCellLineNumber) {

		this.tfNumberCellLineNumber = tfNumberCellLineNumber;
	}

	public int getLow() {

		return low;
	}

	public void setLow( int low) {

		this.low = low;
	}

	public int getHigh() {

		return high;
	}

	public void setHigh( int high) {

		this.high = high;
	}

	public TfCellLineOverlapWithNumbers(
			int tfNumberCellLineNumber, 
			int low, 
			int high) {

		super();
		this.tfNumberCellLineNumber = tfNumberCellLineNumber;
		this.low = low;
		this.high = high;
	}

}
