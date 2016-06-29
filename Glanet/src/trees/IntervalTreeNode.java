/**
 * 
 */
package trees;

import intervaltree.Interval;

import java.util.List;

/**
 * @author Burçak Otlu
 * @date Jun 29, 2016
 * @project Glanet 
 *
 */
public class IntervalTreeNode {

	//List of intervals where intervals are sorted w.r.t. their left end points in ascending order.
	List<Interval> leftEndPointsAscending;

	//List of intervals where intervals are sorted w.r.t. their right end points in descending order.
	List<Interval> rightEndPointsDescending;
	
	Float x_mid;
	
	IntervalTreeNode leftSubTree;
	IntervalTreeNode rightSubTree;
	
	
	/**
	 * 
	 */
	public IntervalTreeNode(
			List<Interval> leftEndPointsAscending,
			List<Interval> rightEndPointsDescending,
			Float x_mid,
			IntervalTreeNode leftSubTree,
			IntervalTreeNode rightSubTree) {
		
		this.leftEndPointsAscending = leftEndPointsAscending;
		this.rightEndPointsDescending = rightEndPointsDescending;
		this.x_mid = x_mid;
		this.leftSubTree = leftSubTree;
		this.rightSubTree =  rightSubTree;
		
	}


}
