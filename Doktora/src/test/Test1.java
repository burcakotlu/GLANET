package test;

public class Test1 {
	
	public static void main(String[] args){
		
		String word1 = "burcak";
		String word2 = "burcak";
		
		if (word1.equals(word2))
			System.out.printf("Yes " + word1 +  " and " + word2 + " are equal \n");
		else 
			System.out.printf("No " + word1 +  " and " + word2 + " are not equal \n");
		
		if (word1 == word2)
			System.out.printf("Yes " + word1 +  " and " + word2 + " are equal \n");
		else 
			System.out.printf("No " + word1 +  " and " + word2 + " are not equal \n");

	}
}
