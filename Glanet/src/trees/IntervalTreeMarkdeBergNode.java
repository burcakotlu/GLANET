/**
 * 
 */
package trees;

import intervaltree.Interval;

/**
 * @author Burçak Otlu
 * @date Jun 29, 2016
 * @project Glanet 
 *
 */
public class IntervalTreeMarkdeBergNode {

	//List of intervals where intervals are sorted w.r.t. their left end points in ascending order.
	Interval[] intervalsLeftEndPointsAscending;

	//List of intervals where intervals are sorted w.r.t. their right end points in descending order.
	Interval[] intervalsRightEndPointsDescending;
	
	Float median;
	
	IntervalTreeMarkdeBergNode left;
	IntervalTreeMarkdeBergNode right;
	
	

	
	public Interval[] getIntervalsLeftEndPointsAscending() {
		return intervalsLeftEndPointsAscending;
	}




	public void setIntervalsLeftEndPointsAscending(Interval[] intervalsLeftEndPointsAscending) {
		this.intervalsLeftEndPointsAscending = intervalsLeftEndPointsAscending;
	}







	public Interval[] getIntervalsRightEndPointsDescending() {
		return intervalsRightEndPointsDescending;
	}







	public void setIntervalsRightEndPointsDescending(Interval[] intervalsRightEndPointsDescending) {
		this.intervalsRightEndPointsDescending = intervalsRightEndPointsDescending;
	}







	public Float getMedian() {
		return median;
	}







	public void setMedian(Float median) {
		this.median = median;
	}







	public IntervalTreeMarkdeBergNode getLeft() {
		return left;
	}







	public void setLeft(IntervalTreeMarkdeBergNode left) {
		this.left = left;
	}







	public IntervalTreeMarkdeBergNode getRight() {
		return right;
	}







	public void setRight(IntervalTreeMarkdeBergNode right) {
		this.right = right;
	}







	public IntervalTreeMarkdeBergNode(
			Interval[] intervalsLeftEndPointsAscending,
			Interval[] intervalsRightEndPointsDescending,
			Float median,
			IntervalTreeMarkdeBergNode left,
			IntervalTreeMarkdeBergNode right) {
		
		this.intervalsLeftEndPointsAscending = intervalsLeftEndPointsAscending;
		this.intervalsRightEndPointsDescending = intervalsRightEndPointsDescending;
		this.median = median;
		this.left = left;
		this.right =  right;
		
	}


}
