/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharArrayPrint {
	
	public static  boolean equals(char[] x, char[] y){
		boolean isEqual= true;
		
		if (x.length!=y.length){
			return false;
		}else{
			for(int i = 0; i<x.length; i++){
				if (x[i]!=y[i])
					return false;
			}
		}
		
		return isEqual;
	}
	
	public static boolean contains(List<char[]> charArrayList,char[] w){
		
		boolean doesContain= false;
		
		for(int i= 0; i<charArrayList.size(); i++){
			char[] temp = charArrayList.get(i);
			
//			if (equals(temp,w)){
//				doesExists= true;
//				break;
//			}
			
			if(Arrays.equals(temp, w)){
				doesContain= true;
				break;
			}
			
		}
	
		return doesContain;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<char[]> charArrayList = new ArrayList<char[]>();
		
		char[] x = new char[6];
		x[0] = 'B';
		x[1] = 'u';
		x[2] = 'r';
		x[3] = 'c';
		x[4] = 'a';
		x[5] ='k';
		
		char[] y = new char[5];
		y[0] = 'A';
		y[1] = 'f';
		y[2] = 's';
		y[3] = 'i';
		y[4] = 'n';

		char[] z = new char[5];
		z[0] = 'B';
		z[1] = 'e';
		z[2] = 't';
		z[3] = 'u';
		z[4] = 'l';
		
		char[] w = new char[5];
		w[0] = 'B';
		w[1] = 'e';
		w[2] = 't';
		w[3] = 'u';
		w[4] = 'l';
		charArrayList.add(x);
		charArrayList.add(y);
		charArrayList.add(z);
		
		String s = "Betul";
		
		if (charArrayList.contains(x))
			System.out.println("Betul exists");
		
		if (charArrayList.get(2).equals(s.toCharArray())){
			System.out.println("equals work");
			
		}else{
			System.out.println("equals does not work");
			System.out.println(charArrayList.get(2));
			System.out.println(s.toCharArray());
			
		}
		
		if (contains(charArrayList,w)){
			System.out.println("yes it exists 1");
			
		}
		
		if (charArrayList.contains(w)){
			System.out.println("yes it exists 2");
			
		}
		
		System.out.println(String.valueOf(x) +" "+ String.valueOf(y));
		
		
		Map<char[], String> bufferedWriterHashMap = new HashMap<char[], String>();
		bufferedWriterHashMap.put(x, "1");
		bufferedWriterHashMap.put(y, "2");
		bufferedWriterHashMap.put(z, "3");
		
		if(bufferedWriterHashMap.containsKey(w)){
			
			System.out.println("yes hash map");
		
		}
	}

}
