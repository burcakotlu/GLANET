/**
 * @author Burcak Otlu
 * Sep 30, 2013
 * 11:14:17 AM
 * 2013
 *
 * 
 */
package test;


class TestStaticVariable{
	public static double x;
	
	
	public static double getX() {
		return x;
	}


	public static void setX(double x) {
		TestStaticVariable.x = x;
	}


	TestStaticVariable(double x){
		this.x = x;
	}
}

public class TestingFloatVersusDouble {
	
	

	/**
	 * 
	 */
	public TestingFloatVersusDouble() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
				
		//Seven decimals after decimal point
//		float x = (float)1.23456719123456789;
		float x = 1.23456719123456789f;
			
		//Fifteen decimals after decimal point
		double y= 1.23456719123456789;
		System.out.println("x: " + x + " y: " + y);
		
		TestStaticVariable test1 = new TestStaticVariable(4);
		System.out.println(" x: " + test1.getX());
		
		test1.setX(5);
		System.out.println(" x: " + test1.getX());
		
		test1 = new TestStaticVariable(6);
		System.out.println(" x: " + test1.getX());
		
		test1.setX(7);
		System.out.println(" x: " + test1.getX());
		
				
		// TODO Auto-generated method stub

	}

}
