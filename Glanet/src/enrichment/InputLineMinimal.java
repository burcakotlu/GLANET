/**
 * 
 */
package enrichment;

/**
 * @author Bur�ak Otlu
 * @date Mar 31, 2015
 * @project Glanet 
 *
 */
public class InputLineMinimal {

	int low;
	int high;

	// /**************************************/
	// int numberofTrials;
	//
	// public int getNumberofTrials() {
	// return numberofTrials;
	// }
	//
	// public void setNumberofTrials(int numberofTrials) {
	// this.numberofTrials = numberofTrials;
	// }
	// /**************************************/

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

	public InputLineMinimal( int low, int high) {

		super();
		this.low = low;
		this.high = high;
	}

}
