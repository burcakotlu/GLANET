/**
 * @author Burcak Otlu
 * Jan 16, 2014
 * 10:29:19 AM
 * 2014
 *
 * 
 */
package annotate.intervals.parametric;

public class TfNameandCellLineNameOverlap {
	
	String tfNameandCellLineName;
	int low;
	int high;
	
	
	

	public String getTfNameandCellLineName() {
		return tfNameandCellLineName;
	}

	public void setTfNameandCellLineName(String tfNameandCellLineName) {
		this.tfNameandCellLineName = tfNameandCellLineName;
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

	/**
	 * 
	 */
	public TfNameandCellLineNameOverlap() {
		// TODO Auto-generated constructor stub
	}

	
	
	public TfNameandCellLineNameOverlap(String tfNameandCellLineName, int low,
			int high) {
		super();
		this.tfNameandCellLineName = tfNameandCellLineName;
		this.low = low;
		this.high = high;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
