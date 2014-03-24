/**
 * @author burcakotlu
 * @date Mar 24, 2014 
 * @time 11:34:48 AM
 */
package src.processinputdata;


/**
 * This class takes the given input data which can be in several data formats.
 * 1. dbSNP Ids
 * 2. 0-based coordinates where start and end are 0-based and all are inclusive
 * 3. BED format 0-based, end exclusiove
 * 4. GFF3 format 1-based, end inclusive  
 */
public class InputDataProcess {
	
	
	public static processInputData(String inputFileName,String inputFileFormat){
		
		if (inputFileFormat.equals(Commons.INPUT_FILE_FORMAT_DBSNP_IDS_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE)){
			
		}else if (inputFileFormat.equals(Commmons.I)){
			
		}
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Get the input file
		String inputFileName = args[0];
		
		//Get the input file format
		String inputFileFormat = args[1];
		
		//Read input data 
		//Process input data
		//Write output data
		processInputData(inputFileName,inputFileFormat);
		

	}

}
