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
public class IntervalTreeNode {

	//List of intervals where intervals are sorted w.r.t. their left end points in ascending order.
	Interval[] leftEndPointsAscending;

	//List of intervals where intervals are sorted w.r.t. their right end points in descending order.
	Interval[] rightEndPointsDescending;
	
	Float median;
	
	IntervalTreeNode leftSubTree;
	IntervalTreeNode rightSubTree;
	
	

	
	
	public Interval[] getLeftEndPointsAscending() {
		return leftEndPointsAscending;
	}





	public void setLeftEndPointsAscending(Interval[] leftEndPointsAscending) {
		this.leftEndPointsAscending = leftEndPointsAscending;
	}





	public Interval[] getRightEndPointsDescending() {
		return rightEndPointsDescending;
	}





	public void setRightEndPointsDescending(Interval[] rightEndPointsDescending) {
		this.rightEndPointsDescending = rightEndPointsDescending;
	}





	public Float getMedian() {
		return median;
	}





	public void setMedian(Float median) {
		this.median = median;
	}





	public IntervalTreeNode getLeftSubTree() {
		return leftSubTree;
	}





	public void setLeftSubTree(IntervalTreeNode leftSubTree) {
		this.leftSubTree = leftSubTree;
	}





	public IntervalTreeNode getRightSubTree() {
		return rightSubTree;
	}





	public void setRightSubTree(IntervalTreeNode rightSubTree) {
		this.rightSubTree = rightSubTree;
	}





	public IntervalTreeNode(
			Interval[] leftEndPointsAscending,
			Interval[] rightEndPointsDescending,
			Float median,
			IntervalTreeNode leftSubTree,
			IntervalTreeNode rightSubTree) {
		
		this.leftEndPointsAscending = leftEndPointsAscending;
		this.rightEndPointsDescending = rightEndPointsDescending;
		this.median = median;
		this.leftSubTree = leftSubTree;
		this.rightSubTree =  rightSubTree;
		
	}


}
