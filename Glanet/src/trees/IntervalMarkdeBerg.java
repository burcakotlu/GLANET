/**
 * 
 */
package trees;

import java.util.Comparator;

import auxiliary.FunctionalElement;

/**
 * @author Burçak Otlu
 * @date Jul 8, 2016
 * @project Glanet 
 *
 */
public class IntervalMarkdeBerg {
	
	Integer low;
	Integer high;
	
	public Integer getLow() {
		return low;
	}

	public void setLow(Integer low) {
		this.low = low;
	}

	public Integer getHigh() {
		return high;
	}

	public void setHigh(Integer high) {
		this.high = high;
	}
	
	

	public IntervalMarkdeBerg(Integer low, Integer high) {
		super();
		this.low = low;
		this.high = high;
	}

	//TODO write a comparator in order to sort intervals in ascending order w.r.t. left end points, low
	public static Comparator<IntervalMarkdeBerg> INTERVALTREEMARKDEBERG_LEFTENDPOINT_ASCENDING = new Comparator<IntervalMarkdeBerg>() {

		public int compare( IntervalMarkdeBerg interval1, IntervalMarkdeBerg interval2) {

			return interval1.getLow().compareTo( interval2.getLow());

		}
	};

	
	//TODO write a comparator in order to sort intervals in descending order w.r.t. right end points, high 
	public static Comparator<IntervalMarkdeBerg> INTERVALTREEMARKDEBERG_RIGHTENDPOINT_DESCENDING = new Comparator<IntervalMarkdeBerg>() {

		public int compare( IntervalMarkdeBerg interval1, IntervalMarkdeBerg interval2) {

			return interval2.getHigh().compareTo(interval1.getHigh());

		}
	};

}
