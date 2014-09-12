/**
 * @author burcakotlu
 * @date Sep 3, 2014 
 * @time 11:12:29 PM
 */
package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class NumberofUniqueEnrichedElements {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		
		String tfNameCellLineName;
		List<String> tfNameCellLineNameList = new ArrayList<String>();
		
		FileReader reader = null;
		try {
			reader = new FileReader("C:\\Users\\burcakotlu\\GLANET\\Output\\Enrichment\\toBeCollected\\toBeDeleted_EnrichedTFCellLineKEGGPathways.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);

			while ((strLine = bufferedReader.readLine())!=null) {
				
				indexofFirstTab = strLine.indexOf('_');
				indexofSecondTab = strLine.indexOf('_',indexofFirstTab+1);
				
				tfNameCellLineName = strLine.substring(0,indexofSecondTab);
				
				
				if (!tfNameCellLineNameList.contains(tfNameCellLineName)) {
					tfNameCellLineNameList.add(tfNameCellLineName);
					
				}
				
			}
			
			bufferedReader.close();
			
			System.out.println(tfNameCellLineNameList.size());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
