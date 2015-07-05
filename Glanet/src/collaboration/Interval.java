/**
 * @author burcakotlu
 * @date Sep 16, 2014 
 * @time 10:29:20 AM
 */
package collaboration;

import enumtypes.ChromosomeName;

/**
 * 
 */
public class Interval {

	ChromosomeName chrName;
	int startZeroBasedHg19;
	int endZeroBasedHg19;

	public ChromosomeName getChrName() {

		return chrName;
	}

	public void setChrName( ChromosomeName chrName) {

		this.chrName = chrName;
	}

	public int getStartZeroBasedHg19() {

		return startZeroBasedHg19;
	}

	public void setStartZeroBasedHg19( int startZeroBasedHg19) {

		this.startZeroBasedHg19 = startZeroBasedHg19;
	}

	public int getEndZeroBasedHg19() {

		return endZeroBasedHg19;
	}

	public void setEndZeroBasedHg19( int endZeroBasedHg19) {

		this.endZeroBasedHg19 = endZeroBasedHg19;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args) {

		// TODO Auto-generated method stub

	}

}
