 /*
 * This program reads input file line by line
 * Adds chr string to the line and outputs the line to the output file
 *  
 * It prepares input files for other programs 
 */


package testtcgadata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import common.Commons;

public class AddChrStringtoFirstColumn {
	
//	This code creates test input for  testing the Search program
//	whether it finds the same alternate gene name for the given SNP chromName startPosition and endPosition
//	Test data is from TGCA
//	chr is added to the first column

	
	public static void addChrString(){
		String inputFileName = Commons.SEARCH_INPUT_FILE_WITH_NON_BLANK_SNP_IDS;
		String outputFileName = Commons.SEARCH_INPUT_FILE_FOR_TCGA_DATA_WITH_NON_BLANK_SNP_ROWS;
		String strLine;
		
		FileReader fileReader = null;
		FileWriter fileWriter = null;
		
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		int indexofFirstTab = 0;
		
		String chromName;
		
		String rest;
		
		try {
			fileReader = new FileReader(inputFileName);
			fileWriter = new FileWriter(outputFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		try {
			bufferedReader = new BufferedReader(fileReader);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				
				chromName = strLine.substring(0, indexofFirstTab);
				chromName = "chr" +chromName;
				
				rest = strLine.substring(indexofFirstTab+1);
				
				bufferedWriter.write(chromName+"\t"+ rest+"\n");
				
			}
			
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
		// TODO Auto-generated method stub		
		addChrString();
		
	
	}

}
