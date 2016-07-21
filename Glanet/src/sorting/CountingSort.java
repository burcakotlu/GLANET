/**
 * 
 */
package sorting;

import printing.Print;
import trees.IntervalMarkdeBerg;
import enumtypes.SortingOrder;

/**
 * @author Burçak Otlu
 * @date Jun 29, 2016
 * @project Glanet 
 *
 *
 * The biggest advantage of counting sort is its complexity – O(n+k), 
 * where n is the size of the sorted array and 
 * k is the size of the helper array (range of distinct values).
 * 
 */
public class CountingSort {
	
	/**
	* Counting sort
	* @param array array to be sorted
	* @return array sorted in ascending order
	*/
	public static int[] sort(int[] array, SortingOrder sortingOrder) {
		
		int arrayTopIndex;

		// array to be sorted in, this array is necessary
		// when we sort object datatypes, if we don't,
		// we can sort directly into the input array    
		int[] aux = new int[array.length];
	
		// find the smallest and the largest value
		int min = array[0];
		int max = array[0];
	
		for (int i = 1; i < array.length; i++) {
			if (array[i] < min) min = array[i];
			else if (array[i] > max) max = array[i];
		}
	
		 
	
		// init array of frequencies
		int[] counts = new int[max - min + 1];
	
		// init the frequencies
		for (int i = 0;  i < array.length; i++) {
			counts[array[i] - min]++;
		}
		 
	
		// recalculate the array - create the array of occurences
		// why do we decrease by one?
		// since we want to have a valid array index, not exceed array size, therefore we decrease by one beforehand.
		counts[0]--;
		for (int i = 1; i < counts.length; i++) {
			counts[i] = counts[i] + counts[i-1];
		}
	
		// Sort the array right to the left
		// 1) look up in the array of occurences the last occurence of the given value
		// 2) place it into the sorted array
		// 3) decrement the index of the last occurence of the given value
		// 4) continue with the previous value of the input array (goto: 1), terminate if all values were already sorted
		
		switch(sortingOrder){
		
			case SORTING_IN_ASCENDING_ORDER:
				for (int i = array.length - 1; i >= 0; i--) {
					aux[counts[array[i] - min]--] = array[i];
				}
				break;
				
			case SORTING_IN_DESCENDING_ORDER:
				//array and aux have to be of same size.
				//array is unsorted
				//aux is sorted
				//counts is of size range which is equal to the max-min+1
				arrayTopIndex = array.length-1;
				for (int i = array.length - 1; i >= 0; i--) {
					aux[arrayTopIndex-counts[array[i] - min]--] = array[i];
				}
				break;
			
		}//End of SWITCH
		
	
		//Free space
		counts = null;

		return aux;
	}
	

	/**
	* Counting sort
	* @param array array to be sorted
	* @return array sorted in ascending order
	*/
	public static void sortLeftEndPointsAscending(
			IntervalMarkdeBerg[] array, 
			SortingOrder sortingOrder,
			IntervalMarkdeBerg[] intervalsSorted) {
		
		int arrayTopIndex;

		// array to be sorted in, this array is necessary
		// when we sort object data types, if we don't,
		// we can sort directly into the input array    
		// Interval[] aux = new Interval[array.length];
	
		// find the smallest and the largest value
		int min = array[0].getLow();
		int max = array[0].getLow();
	
		for (int i = 1; i < array.length; i++) {
			if (array[i].getLow() < min) min = array[i].getLow();
			else if (array[i].getLow() > max) max = array[i].getLow();
		}
	
	
		// init array of frequencies
		int[] counts = new int[max - min + 1];
	
		// init the frequencies
		for (int i = 0;  i < array.length; i++) {
			counts[array[i].getLow() - min]++;
		}
		 
	
		// recalculate the array - create the array of occurences
		counts[0]--;
		for (int i = 1; i < counts.length; i++) {
			counts[i] = counts[i] + counts[i-1];
		}
	
		// Sort the array right to the left
		// 1) look up in the array of occurences the last occurence of the given value
		// 2) place it into the sorted array
		// 3) decrement the index of the last occurence of the given value
		// 4) continue with the previous value of the input array (goto: 1), terminate if all values were already sorted
		
		switch(sortingOrder){
		
			case SORTING_IN_ASCENDING_ORDER:
				for (int i = array.length - 1; i >= 0; i--) {
					intervalsSorted[counts[array[i].getLow() - min]--] = array[i];
				}
				break;
			case SORTING_IN_DESCENDING_ORDER:
				arrayTopIndex = array.length-1;
				for (int i = array.length - 1; i >= 0; i--) {
					intervalsSorted[arrayTopIndex-counts[array[i].getLow() - min]--] = array[i];
				}
				break;
		
		}//End of SWITCH
		
		
		//Free space
		counts = null;
	
	}
	
	
	
	public static void sortRightEndPointsDescending(
			IntervalMarkdeBerg[] array, 
			SortingOrder sortingOrder,
			IntervalMarkdeBerg[] intervalsSorted) {
		
		int arrayTopIndex;

		// array to be sorted in, this array is necessary
		// when we sort object data types, if we don't,
		// we can sort directly into the input array    
		//Interval[] aux = new Interval[array.length];
	
		// find the smallest and the largest value
		int min = array[0].getHigh();
		int max = array[0].getHigh();
	
		for (int i = 1; i < array.length; i++) {
			if (array[i].getHigh() < min) min = array[i].getHigh();
			else if (array[i].getHigh() > max) max = array[i].getHigh();
		}
	
		//System.out.println("Range: " + (max-min+1) + "\tArray.length: " + array.length);
	
		// init array of frequencies
		int[] counts = new int[max - min + 1];
	
		// init the frequencies
		for (int i = 0;  i < array.length; i++) {
			counts[array[i].getHigh() - min]++;
		}
		 
	
		// recalculate the array - create the array of occurences
		counts[0]--;
		for (int i = 1; i < counts.length; i++) {
			counts[i] = counts[i] + counts[i-1];
		}
	
		// Sort the array right to the left
		// 1) look up in the array of occurences the last occurence of the given value
		// 2) place it into the sorted array
		// 3) decrement the index of the last occurence of the given value
		// 4) continue with the previous value of the input array (goto: 1), terminate if all values were already sorted
		switch(sortingOrder){
		
			case SORTING_IN_ASCENDING_ORDER:
				for (int i = array.length - 1; i >= 0; i--) {
					intervalsSorted[counts[array[i].getHigh() - min]--] = array[i];
				}
				break;
			case SORTING_IN_DESCENDING_ORDER:
				arrayTopIndex = array.length-1;
				for (int i = array.length - 1; i >= 0; i--) {
					intervalsSorted[arrayTopIndex-counts[array[i].getHigh() - min]--] = array[i];
				}
				break;
		
		}//End of SWITCH
		
		
		//Free space
		counts = null;

	}


	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
		
		int[] arrayToBeSorted = new int[]{-100000000,-10, 10,20,5,40,30,50,0,15,40,60,10,30, 1000,100000000};
		
		System.out.println("*************************");

		arrayToBeSorted = sort(arrayToBeSorted, SortingOrder.SORTING_IN_ASCENDING_ORDER);
		Print.printArray(arrayToBeSorted);

		System.out.println("*************************");
		
		arrayToBeSorted = sort(arrayToBeSorted, SortingOrder.SORTING_IN_DESCENDING_ORDER);
		Print.printArray(arrayToBeSorted);

		System.out.println("*************************");

	}

}
