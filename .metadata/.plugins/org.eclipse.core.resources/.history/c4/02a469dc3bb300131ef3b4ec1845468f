/**
 * @author Burcak Otlu
 * Jan 21, 2014
 * 5:20:00 PM
 * 2014
 *
 * 
 */
package empiricalpvalues;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import common.Commons;

public class CalculateNumberofTfElements {

	/**
	 * 
	 */
	public CalculateNumberofTfElements() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FileReader inputFileReader;
		BufferedReader bufferedReader;
		String strLine;
		int indexofFirstUnderscore;
		String tfName;
		Map<String,Integer> tfName2KMap = new HashMap<String,Integer>();
		
		try {
			inputFileReader = new FileReader(Commons.TFBS_WHOLE_GENOME_USING_INTERVAL_TREE);
			bufferedReader = new BufferedReader(inputFileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstUnderscore = strLine.indexOf('_');
				tfName = strLine.substring(0,indexofFirstUnderscore);
				
				if (!tfName2KMap.containsKey(tfName)){
					tfName2KMap.put(tfName, 1);
				}else {
					tfName2KMap.put(tfName, tfName2KMap.get(tfName)+1);
				}
				
			}
			
			System.out.println(tfName2KMap.size());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
