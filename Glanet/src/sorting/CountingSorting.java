/**
 * 
 */
package sorting;

import intervaltree.Interval;

/**
 * @author Burçak Otlu
 * @date Jun 29, 2016
 * @project Glanet 
 *
 */
public class CountingSorting {
	
	/**
	* Counting sort
	* @param array array to be sorted
	* @return array sorted in ascending order
	*/
	public static int[] countingSort(int[] array) {

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
		counts[0]--;
		for (int i = 1; i < counts.length; i++) {
			counts[i] = counts[i] + counts[i-1];
		}
	
		// Sort the array right to the left
		// 1) look up in the array of occurences the last occurence of the given value
		// 2) place it into the sorted array
		// 3) decrement the index of the last occurence of the given value
		// 4) continue with the previous value of the input array (goto: 1), terminate if all values were already sorted
		for (int i = array.length - 1; i >= 0; i--) {
			aux[counts[array[i] - min]--] = array[i];
		}
	
		return aux;
	}
	

	/**
	* Counting sort
	* @param array array to be sorted
	* @return array sorted in ascending order
	*/
	public static Interval[] countingSort(Interval[] array) {

		// array to be sorted in, this array is necessary
		// when we sort object datatypes, if we don't,
		// we can sort directly into the input array    
		Interval[] aux = new Interval[array.length];
	
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
		for (int i = array.length - 1; i >= 0; i--) {
			aux[counts[array[i].getLow() - min]--] = array[i];
		}
	
		return aux;
	}

	public static void printArray(int[] array){
		for(int i = 0; i<array.length; i++ ){
			System.out.println(array[i]);
		}
	}
	
	public static void printArray(Interval[] array){
		for(int i = 0; i<array.length; i++ ){
			System.out.println(array[i].getLow());
		}
	}

	/**
	 * 
	 */
	public CountingSorting() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
		
		int[] arrayToBeSorted = new int[]{10,20,5,40,30,50,0,15,40,60,10,30};
		int[] arraySorted;
		arraySorted = countingSort(arrayToBeSorted);
		
		printArray(arraySorted);

	}

}
