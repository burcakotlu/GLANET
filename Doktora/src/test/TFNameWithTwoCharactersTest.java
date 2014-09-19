/**
 * @author burcakotlu
 * @date Aug 27, 2014 
 * @time 1:14:27 PM
 */
package test;

/**
 * 
 */
public class TFNameWithTwoCharactersTest {
	
	public static String getTfNameWithFirstNumberWithNextCharacter(String tfName){
		
		int n = tfName.length();
		char c;
		int i;
		
		for (i = 0; i < n; i++) {
		    c = tfName.charAt(i);
		    if (Character.isDigit(c)){
		    	if ((i+1)<n){
		    	 	return tfName.substring(0, i+2);
		  		  
		    	}else{
		    	 	return tfName.substring(0, i+1);
		  		  
		    	}
		     }
		}
		
	
		return tfName.substring(0, i);
	}
	
	public static String removeLastCharacter(String tfName){
		
		int n = tfName.length();
		
		if (n>=5){
			return tfName.substring(0, n-1);
			
		}else{
			return tfName;
			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String tfName ="BURc";
		String tfNameOut;

		System.out.println(tfName);
		
		tfNameOut = removeLastCharacter(tfName);
		System.out.println(tfNameOut);
	}

}
