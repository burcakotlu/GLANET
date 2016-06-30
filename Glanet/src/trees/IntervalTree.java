/**
 * 
 */
package trees;

import intervaltree.Interval;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sorting.CountingSorting;
import enumtypes.PointType;
import enumtypes.SortingOrder;

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
 * It is easy to maintain these pre-sorted sets through the recursive calls.
 * 
 * Let n_mid = card(I_mid)
 * Creating the lists, L_left and L_right takes O(n_mid log n_mid) time.
 * Hence time we spent is  O(n + n_mid log n_mid)
 * 
 * An interval tree of n intervals can be built in O(nlogn) time.
 *
 */
public class IntervalTree {
	
	IntervalTreeNode root;

	/**
	 * 
	 */
	public IntervalTree() {
		// TODO Auto-generated constructor stub
	}
	
	
	//allIntervals are not sorted whether w.r.t. to left or right end points
	//Take left and right end points and sort the all end points and find the median
	//Having median at hand, find the left node intervals such that right end point is less than median
	//Having median at hand, find the middle node intervals such that left end point is less than or equal to median and right end point is greater than or equal to median.
	//Having median at hand, find the right node intervals such that left end point is greater than median
	public static void findMedianAndFill(
			List<Interval> allIntervals,
			Float median,
			List<Interval> leftNodeIntervals,
			List<Interval> middleNodeIntervals,
			List<Interval> rightNodeIntervals){
		
		int numberofIntervals = allIntervals.size();
		int numberofEndPoints = numberofIntervals*2;
		
		int[] allEndPoints = new int[numberofEndPoints];
		int[] allEndPointsSorted = new int[numberofEndPoints];
		
		Interval interval = null;
		int i=0;

		//Fill allEndPoints
		for(Iterator<Interval> itr= allIntervals.iterator(); itr.hasNext();){
			interval = itr.next();	
			allEndPoints[i++] = interval.getLow();
			allEndPoints[i++] = interval.getHigh();
		}
		
		allEndPointsSorted = CountingSorting.sort(allEndPoints, SortingOrder.SORTING_IN_ASCENDING_ORDER);
		
		//Find median
		median = (allEndPointsSorted[numberofIntervals-1] + allEndPointsSorted[numberofIntervals])*1.0f/2;
		
		//Fill left, middle and right interval lists
		//Think about if intervals were sorted w.r.t. left end points or right end points how would it be affected?
		//w.r.t. left end point ascending
		//w.r.t. left end point descending
		//w.r.t. right end point ascending
		//w.r.t. right end point descending
		
		//TODO
		//Assume that we have sorted all the intervals w.r.t. left end points in ascending order O(nlogn) or O(n+k)
		//Loop until you hit an interval with its low is greater than median
		//Then exit loop and add all the remaining intervals into right intervals.
		for(Iterator<Interval> itr= allIntervals.iterator(); itr.hasNext();){
			interval = itr.next();	
			
			if (interval.getHigh() < median){
				leftNodeIntervals.add(interval);
			}else if (interval.getLow() > median){
				rightNodeIntervals.add(interval);
			}else{
				middleNodeIntervals.add(interval);
			}
		}//End of FOR
		
		System.out.println("Median is " + median);
		System.out.println("Size of all intervals  is: " + allIntervals.size());
		System.out.println("Size of left node intervals  is: " + leftNodeIntervals.size());
		System.out.println("Size of middle node intervals  is: " + middleNodeIntervals.size());
		System.out.println("Size of right node intervals  is: " + rightNodeIntervals.size());
		
		//Sorting an array into ascending order. 
		//This can be done either sequentially, using the sort method, or concurrently, using the parallelSort method introduced in Java SE 8
		//Parallel sorting of large arrays on multiprocessor systems is faster than sequential array sorting.
	}
	
	public static void fill(
			List<Interval> middleNodeIntervals,
			Interval[] middleIntervalsLeftEndPointsAscending,
			Interval[] middleIntervalsRightEndPointsDescending){
		
		//Does middleNodeIntervals has to be a list? Can it be stored in an array?
		Interval[] middleIntervalsUnsorted = middleNodeIntervals.toArray(new Interval[middleNodeIntervals.size()]);
		
		CountingSorting.sortLeftEndPointsAscending(middleIntervalsUnsorted, SortingOrder.SORTING_IN_ASCENDING_ORDER,middleIntervalsLeftEndPointsAscending);
		CountingSorting.sortRightEndPointsDescending(middleIntervalsUnsorted, SortingOrder.SORTING_IN_DESCENDING_ORDER,middleIntervalsRightEndPointsDescending);
		
		CountingSorting.printArray(middleIntervalsLeftEndPointsAscending, PointType.LEFT_END_POINT);
		CountingSorting.printArray(middleIntervalsRightEndPointsDescending,PointType.RIGHT_END_POINT);
	}
	
	
	//list contains unsorted intervals.
	//First sort the interval end points
	//Find the median
	//Find the node intervals
	//Find the left subtree intervals
	//Find the right subtree intervals
	public static IntervalTreeNode constructIntervalTree(List<Interval> allIntervals){
		
		IntervalTreeNode node = null;
		
		if (allIntervals==null || allIntervals.isEmpty()){
			
			return node;
			
		}else{
			
			Float median = new Float(0);
			List<Interval> leftNodeIntervals = new ArrayList<>();
			List<Interval> middleNodeIntervals = new ArrayList<>();
			List<Interval> rightNodeIntervals = new ArrayList<>();
			
			IntervalTreeNode leftSubTreeNode = null;
			IntervalTreeNode rightSubTreeNode =null;
			
			
			//allIntervals can be an array
			//However we do not know how many of the intervals will be in left, middle and right.
			findMedianAndFill(allIntervals,median,leftNodeIntervals,middleNodeIntervals,rightNodeIntervals);
			
			//We don't know the number of middle intervals
			//middleIntervalsLeftEndPointsAscending can be an array
			//middleIntervalsRightEndPointsDescending can be an array
			Interval[] middleIntervalsLeftEndPointsAscending = new Interval[middleNodeIntervals.size()];
			Interval[] middleIntervalsRightEndPointsDescending = new Interval[middleNodeIntervals.size()];

			fill(middleNodeIntervals,middleIntervalsLeftEndPointsAscending,middleIntervalsRightEndPointsDescending);
			
			leftSubTreeNode = constructIntervalTree(leftNodeIntervals);
			
			rightSubTreeNode = constructIntervalTree(rightNodeIntervals);
			
			node = new IntervalTreeNode(
					middleIntervalsLeftEndPointsAscending, 
					middleIntervalsRightEndPointsDescending, 
					median, 
					leftSubTreeNode, 
					rightSubTreeNode);
			
		}
		
		return node;
		
	}
	
	

	public IntervalTree(IntervalTreeNode root) {
		super();
		this.root = root;
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
		IntervalTreeNode root = constructIntervalTree(list);
		
		//IntervalTree intervalTree = new IntervalTree(root);

		

	}

}
