/**
 * 
 */
package auxiliary;

import java.util.Arrays;

/**
 * @author Burçak Otlu
 * @date Mar 10, 2015
 * @project Glanet 
 *
 */
public class Accumulation {
	
	
	public static void accumulate(int[] leftArray, int[] rightArray) {
			Arrays.setAll(rightArray, i-> leftArray[i] +  rightArray[i]);
	}
	
	//It must use boxed types, so if you need speed, no generic implementation can do that in Java, you must use primitive types.
	
//		// TShortIntMap version starts
//		// Accumulate chromosomeBasedName2KMap results in accumulatedName2KMap
//		// Accumulate number of overlaps coming from each chromosome
//		// based on permutationNumber and ElementName
//		public static <T extends Number> void accumulate(T[] leftArray, T[] rightArray) {
//			
//			if (leftArray instanceof Integer[]){
//				Arrays.setAll(rightArray, i-> (int)leftArray[i] +  (int)rightArray[i]);
//			}else if (leftArray instanceof Short[]){
//				Arrays.setAll(rightArray, i-> (short)leftArray[i] +  (short)rightArray[i]);
//			}
//		
//		}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
