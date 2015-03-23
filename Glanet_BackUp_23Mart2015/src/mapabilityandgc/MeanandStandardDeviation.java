/**
 * @author Burcak Otlu
 * Sep 9, 2013
 * 2:45:07 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

public class MeanandStandardDeviation {

	float mean;
	float standardDeviation;
	int numberofIntervals;
	float sumofGCs;
	float sumofMapabilities;

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getMean() {
		return mean;
	}

	public void setMean(float mean) {
		this.mean = mean;
	}

	public float getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(float standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public float getSumofGCs() {
		return sumofGCs;
	}

	public void setSumofGCs(float sumofGCs) {
		this.sumofGCs = sumofGCs;
	}

	public float getSumofMapabilities() {
		return sumofMapabilities;
	}

	public void setSumofMapabilities(float sumofMapabilities) {
		this.sumofMapabilities = sumofMapabilities;
	}

	public int getNumberofIntervals() {
		return numberofIntervals;
	}

	public void setNumberofIntervals(int numberofIntervals) {
		this.numberofIntervals = numberofIntervals;
	}

	/**
	 * 
	 */
	public MeanandStandardDeviation() {
		this.mean = 0;
		this.standardDeviation = 0;
		this.numberofIntervals = 0;
		this.sumofGCs = 0;
		this.sumofMapabilities = 0;
		this.name = null;
	}

}
