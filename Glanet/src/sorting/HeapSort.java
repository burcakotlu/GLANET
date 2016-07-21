/**
 * 
 */
package sorting;

/**
 * @author Burçak Otlu
 * @date Jul 21, 2016
 * @project Glanet 
 * 
 * 
 * HeapSort sorts n numbers in place in O(nlgn)  time.
 * It uses an important data structure called a heap to implement a priority queue.
 * 
 * Heap data structure is an array object that can be viewed as a complete binary tree.
 * The tree is filled on all levels except possibly the lowest, which is filled from left up to a point.
 * 
 *  An array A that represents a heap is an object with two attributes: length[A] which is the number of elements in array A.
 *  heapSize[A] the number of elements in the heap stored within the array.
 *  
 *  Root of the tree is A[1], given index i of a node
 *  parent(i) --> return floor(i/2) (shift i right one bit position)
 *	left(i) --> return 2*i (shift i left one bit position)
 *	right(i) --> return 2*i+1 (shift i left one bit position and shift one as the low-order bit.)
 *	Define macros or in-line procedures.
 *
 *	Heaps also satisfy the heap property: for every node i other than the root, A[parent(i)] >= A[i], that is the value of a node is at most the value of its parent. 
 */
public class HeapSort {

	/**
	 * 
	 */
	public HeapSort() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// TODO First write heapSort for int[]
		// TODO Then write code for IntervalMarkdeBerg[]

	}

}
