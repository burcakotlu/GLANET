/**
 * 
 */
package auxiliary;

import gnu.trove.map.TIntObjectMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author Burçak Otlu
 * @date Nov 11, 2014
 * @project Glanet 
 *
 */
public class CommonUtilities {
	
	

	public static void readNames(String dataFolder,List<String> nameList, String inputDirectoryName, String inputFileName){
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;	
		
		String strLine;
		
		try {
			
			fileReader = FileOperations.createFileReader(dataFolder + inputDirectoryName,inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine= bufferedReader.readLine())!=null){
				if (!nameList.contains(strLine)){
					nameList.add(strLine);
				}
			}//End of While
		
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}

	
	public static void fillNumberToNameMaps(TIntObjectMap<String> number2NameMap,String directoryName, String fileName){
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		
		String strLine;
		Integer number;
		String name;
		
		int indexofFirstTab;		
		
		try {
			fileReader = FileOperations.createFileReader(directoryName +fileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while ((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				
				number = Integer.parseInt(strLine.substring(0,indexofFirstTab));
				name = strLine.substring(indexofFirstTab+1);
				
				number2NameMap.put(number, name);
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
