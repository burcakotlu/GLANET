/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package fastdirtysolutions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import common.Commons;

public class ConvertCellLineNametoUpperCase {
	
	public void convertCellLineNametoUpperCase(String inputFileName, String outputFileName){

		String strLine;
		String cellLineName;
		int indexofFirstTab;
		
		try {
			FileReader fileReader = new FileReader(inputFileName);
			FileWriter fileWriter = new FileWriter(outputFileName);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
			
				cellLineName = strLine.substring(0,indexofFirstTab);
				
				bufferedWriter.write(cellLineName.toUpperCase(Locale.ENGLISH) + "\t" + strLine.substring(indexofFirstTab+1) +"\n");
			}
			
			bufferedWriter.close();
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String inputFileName = Commons.DNASE_CELL_LINE_WHOLE_GENOME_USING_INTERVAL_TREE;
		String outputFileName = Commons.DUMMY_DNASE_CELL_LINE_WHOLE_GENOME_USING_INTERVAL_TREE; 
		
		ConvertCellLineNametoUpperCase convert = new ConvertCellLineNametoUpperCase();
		
		convert.convertCellLineNametoUpperCase(inputFileName,outputFileName);


	}

}
