/*
 * This class will read line one by one
 * add chr string to the line
 * and write it to a file.
 * 
 */
package processinputdata.prepare;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import auxiliary.FileOperations;

import common.Commons;


public class PreparationofOCDSnps {

	public static void addChrString(String inputFileName, String preparedFileName,String chromosomePositionType){
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		String strLine;
		
		int indexofFirstTab;
		int indexofSecondTab;
		
		String chrNumber;
		String low;
		String high;
		
		int zeroBasedLow;
		int zeroBasedHigh;
		
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(preparedFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine= bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				if (indexofSecondTab > indexofFirstTab){
					chrNumber = strLine.substring(0, indexofFirstTab);
					low = strLine.substring(indexofFirstTab+1,indexofSecondTab);
					high = strLine.substring(indexofSecondTab+1);
				}else{
					chrNumber = strLine.substring(0, indexofFirstTab);
					low = strLine.substring(indexofFirstTab+1);
					high = low;
				}
				
				if (!(low.equals(Commons.NOT_AVAILABLE_SNP_ID))){
					//If given snps have one based chromosome positions 
					//then they must be converted into zero based chromosome positions
					//since my internal convention is using zero based chromosome positions for all calculations
					//such as overlap calculations
					if(chromosomePositionType.equals(Commons.CHROMOSOME_POSITION_TYPE_ZERO_BASED)) {
						bufferedWriter.write(Commons.chr + chrNumber +"\t" + low + "\t" + high+ "\n");

					}else if (chromosomePositionType.equals(Commons.CHROMOSOME_POSITION_TYPE_ONE_BASED)) {
						
						//convert 1-based coordinates to 0-based coordinates 
						
						zeroBasedLow = Integer.parseInt(low)-1;
						zeroBasedHigh = Integer.parseInt(high)-1;
						
						bufferedWriter.write(Commons.chr + chrNumber +"\t" + zeroBasedLow + "\t" + zeroBasedHigh + "\n");
					}
	
				}
			}
			
			bufferedWriter.close();
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		String inputFileName = Commons.OCD_GWAS_SIGNIFICANT_SNPS_CHRNUMBER_BASEPAIRNUMBER;
		String preparedFileName = Commons.OCD_GWAS_SIGNIFICANT_SNPS_PREPARED_FILE;
		
		String chromosomePositionType = Commons.CHROMOSOME_POSITION_TYPE_ONE_BASED;
		addChrString(inputFileName,preparedFileName,chromosomePositionType);
		

	}

}
