/**
 * @author Burcak Otlu
 * Feb 18, 2014
 * 10:22:44 AM
 * 2014
 *
 * 
 */
package processinputdata.prepare;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import common.Commons;


public class PrepareHIV1SNPs {

	/**
	 * 
	 */
	public PrepareHIV1SNPs() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void readandWrite(String inputFileName,String outputFileName,String chromosomePositionType){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine = null;
		String chrName;
		int start;
		int end;
		
		int zeroBasedStart ;
		int zeroBasedEnd;
		
		int indexofColon;
		int indexofHyphen;
		
		
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
//			chr9:7682601-7682602
			
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofColon = strLine.indexOf(':');
				indexofHyphen = strLine.indexOf('-');
				
				chrName = strLine.substring(0,indexofColon);
				start = Integer.parseInt(strLine.substring(indexofColon+1, indexofHyphen));
				end = start;
				
				if(chromosomePositionType.equals(Commons.CHROMOSOME_POSITION_TYPE_ZERO_BASED)) {
					bufferedWriter.write(chrName + "\t" + start + "\t" + end + "\n" );
					
				}else if (chromosomePositionType.equals(Commons.CHROMOSOME_POSITION_TYPE_ONE_BASED)) {
					
					//convert 1-based cootdinated to 0-based coordinates 
					zeroBasedStart = start -1;
					zeroBasedEnd = end - 1;
					
					bufferedWriter.write(chrName + "\t" + zeroBasedStart + "\t" + zeroBasedEnd + "\n" );
					
				}
				
				
				
				
			}

			//Close bufferedReader and bufferedWriter
			bufferedReader.close();
			bufferedWriter.close();
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
		
//		String inputFileName  = Commons.HIV1_SNPS_START_INCLUSIVE_END_EXCLUSIVE;
//		String outputFileName = Commons.HIV1_SNPS_START_INCLUSIVE_END_INCLUSIVE;
		
		String chromosomePositionType = Commons.CHROMOSOME_POSITION_TYPE_ONE_BASED;
		
//		readandWrite(inputFileName,outputFileName,chromosomePositionType);

	}

}
