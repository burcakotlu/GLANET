/**
 * 
 */
package test;

import enumtypes.GeneratedMixedNumberDescriptionOrderLength;


/**
 * @author Burçak Otlu
 * @date Nov 6, 2014
 * @project Glanet 
 *
 */
public class SwitchCaseTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GeneratedMixedNumberDescriptionOrderLength mode = GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER;
		
		switch(mode){
			case LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER: 
				System.out.println("hello1");
				System.out.println("hello2");
				System.out.println("hello3");
				break;
			case LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER:
				System.out.println("world1");
				System.out.println("world2");
				System.out.println("world3");
				break;
			default: 
				break;
		}

	}

}
