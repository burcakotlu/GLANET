/**
 * 
 */
package trees;

import intervaltree.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Burçak Otlu
 * @date Jun 28, 2016
 * @project Glanet 
 * 
 * Implemented from Computational Geometry, Mark de Berg
 * 
 * Assume that there are n intervals with 2n end points (each interval has two end points namely left and right end points)
 * Let x_mid be the median of 2n end points.
 * So at most half of the interval end points lies to the left of x_mid and at most half of the interval end points lies to the right of x_mid
 * Interval tree is a binary tree constructed based on this idea.
 * The right subtree of the tree stores I_right of the intervals that lie completely to the right of x_mid.   
 * The left subtree of the tree stores I_left of the intervals that lie completely to the left of x_mid.
 * The subtrees are constructed recursively in the same manner.
 * 
 * Root of the tree has its x_mid value and I_mid of of intervals containing (overlapping) this x_mid point.
 * Please notice that median of end points is not the average of end points.
 * 
 * Interval Tree Data Structure
 * 
 * 1. If I is empty set, then the interval tree is a leaf.
 * 2. Otherwise, let x_mid be the median of 2n end points.
 * 		Let 
 * 			I_left= {(x_left,x_right): x_right < x_mid}
 * 			I_mid=  {(x_left,x_right): x_left <= x_mid <= x_right}
 * 			I_right={(x_left,x_right): x_mid < x_left}
 * 
 * Interval tree consists of a root node v storing x_mid.
 * I_mid is stored in 2 lists, L_left is a list sorted in ascending left end points,
 * L_right is a list in descending right end points.
 * 
 * The left subtree of v is an interval tree for the set I_left. 
 * The right subtree of v is an interval tree for the set I_right. 
 * 
 * An interval tree of n intervals uses O(n) storage and has depth of O(logn).
 * 
 * Finding the median of a set of points take linear time.
 * 
 * It is better to find median by pre-sorting the set of points.
 * It is easy to maintain these presorted sets through the recursive calls.
 * 
 * Let n_mid = card(I_mid)
 * Creating the lists, L_left and L_right takes O(n_mid log n_mid) time.
 * Hence time we spent is  O(n + n_mid log n_mid)
 * 
 * An interval tree of n intervals can be built in O(nlogn) time.
 *
 */
public class IntervalTree {

	/**
	 * 
	 */
	public IntervalTree() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static IntervalTree constructIntervalTree(List<Interval> list){
		
		int median = 0;
		List<Interval> leftSubtreeIntervals  = null;
		List<Interval> rightSubtreeIntervals = null;
		List<Interval> medianSubtreeIntervals = null;
		
		IntervalTree intervalTree = null;
		
		//median = findMedianofEndPoints(list);
		
		//leftSubtreeIntervals = findLeftSubtree(list, median);
		
		//rightSubtreeIntervals = findRightSubtree(list,median);
		
		//middleIntervals
		
		return intervalTree;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		Interval i1 = new Interval(10, 20);
		Interval i2 = new Interval(5, 40);
		Interval i3 = new Interval(30, 50);
		Interval i4 = new Interval(0, 15);
		Interval i5 = new Interval(40, 60);
		Interval i6 = new Interval(10, 30);
		
		List<Interval> list = new ArrayList<Interval>();
		
		list.add(i1);
		list.add(i2);
		list.add(i3);
		list.add(i4);
		list.add(i5);
		list.add(i6);
		
		//Collections.sort(list);
		
		//You have a list of intervals
		constructIntervalTree(list);

		

	}

}
