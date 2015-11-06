/**
 * 
 */
package debug;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import auxiliary.FileOperations;

/**
 * @author Burçak Otlu
 * @date Nov 4, 2015
 * @project Glanet 
 *
 */
public class Debug {
	
	
	public static void readInputFileAndFilter(
			String permutationNumberMixedNumber,
			String inputFileName,
			String outputFileName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine = null;
		
		try {
			
			fileReader = FileOperations.createFileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				if (strLine.contains(permutationNumberMixedNumber)){
					bufferedWriter.write(strLine + System.getProperty("line.separator"));
				}
				
			}//End of WHILE
			
			
			bufferedReader.close();
			bufferedWriter.close();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		String permutationNumberMixedNumber = "numberofOverlaps";
		String logFile = "GLANET-1446798033669.log";
		String inputFileName = "C:\\Users\\Burçak\\Developer\\Java\\GLANET\\Glanet\\" + logFile;
		String outputFileName = "C:\\Users\\Burçak\\Developer\\Java\\GLANET\\Glanet\\" + "filtered_"  + permutationNumberMixedNumber + "_" +  logFile;
		
		readInputFileAndFilter(permutationNumberMixedNumber,inputFileName,outputFileName);
		
	}

}
