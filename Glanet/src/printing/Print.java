/**
 * 
 */
package printing;

import intervaltree.Interval;
import enumtypes.PointType;

/**
 * @author Burçak Otlu
 * @date Jul 1, 2016
 * @project Glanet 
 *
 */
public class Print {

	public static void printArray(int[] array){
		for(int i = 0; i<array.length; i++ ){
			System.out.println(array[i]);
		}
	}
	
	public static void printArray(Interval[] array,PointType pointType){
		
		switch(pointType){
		
			case LEFT_END_POINT:
				for(int i = 0; i<array.length; i++ ){
					System.out.println(array[i].getLow());
				}
				break;
			case RIGHT_END_POINT:
				for(int i = 0; i<array.length; i++ ){
					System.out.println(array[i].getHigh());
				}
				break;
			
		}//End of switch
		
		
		
	}

	public static void printArray(Interval[] array){
		
	
		for(int i = 0; i<array.length; i++ ){
			System.out.print("(" + array[i].getLow() + "," + array[i].getHigh() + ") ");
		}
		
		System.out.println();
		
	}

}
