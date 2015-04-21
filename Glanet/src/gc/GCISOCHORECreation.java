/**
 * 
 */
package gc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;
import auxiliary.GlanetPercentageFormat;

import common.Commons;

import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;

/**
 * @author Burçak Otlu
 * @date Apr 21, 2015
 * @project Glanet 
 *
 */
public class GCISOCHORECreation {
	
	final static Logger logger = Logger.getLogger(GCISOCHORECreation.class);
	
	public static void createGCIsochoreFile(String dataFolder,ChromosomeName chrName){
		
		//Input File
		String gcFastaFileName =   Commons.GC + System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_FILE_END;
		
		//Output File
		String gcIsochoreFileName =   Commons.GC + System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_ISOCHORES_FILE_END;
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		int numberofCharactersRead;

		char[] cbuf = new char[10000];
		char ch;
		int nthBase = 0;
		int lengthOfLastInterval = 0;
		String strLine;
		
		int i;
		int numberofGCsInStandardGCIntervalLength = 0;
		int totalNumberofCharsReadInChromosome = 0;
		int totalNumberofGCsInChromosome = 0;
		
		int standardGCIntervalLength = Commons.GC_ISOCHORE_MOVING_WINDOW_SIZE;
		
		float gcPercentage = 0; 
		
		NumberFormat pf = GlanetPercentageFormat.getGlanetPercentageFormat();
		
	
		try {
			
			fileReader = FileOperations.createFileReader(dataFolder + gcFastaFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(dataFolder + gcIsochoreFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			// skip first informative line of fasta file
			// >chr22
			strLine = bufferedReader.readLine();

			// check whether fasta file starts with > greater character
			if (!strLine.startsWith(">")) {
				logger.info("Fasta file does not start with > character.");
			}

			
			//Write header file
			bufferedWriter.write("#" + "OBasedStart" + "\t" + "OBasedEnd" + "\t"  + "intervalLength" + "\t" + "numberofGCsInTheInterval" + "\t" +  "GCContentPercentage" + System.getProperty("line.separator"));

			
			
			/*****************************************************************/
			while ((numberofCharactersRead = bufferedReader.read(cbuf)) != -1) {
				
				totalNumberofCharsReadInChromosome = totalNumberofCharsReadInChromosome + numberofCharactersRead;
				
				/**************************************************/
				for (i = 0; i < numberofCharactersRead; i++) {
					
					ch = cbuf[i];

					if (	(ch == Commons.NUCLEIC_ACID_UPPER_CASE_G) || 
							(ch == Commons.NUCLEIC_ACID_LOWER_CASE_G) || 
							(ch == Commons.NUCLEIC_ACID_UPPER_CASE_C) || 
							(ch == Commons.NUCLEIC_ACID_LOWER_CASE_C)) {

						numberofGCsInStandardGCIntervalLength++;
						
					}
					
					nthBase++;
					
					//If we have read standardGCIntervalLength many bases 
					if (nthBase % standardGCIntervalLength == 0){
						
						//For Debug Purposes
						totalNumberofGCsInChromosome = totalNumberofGCsInChromosome + numberofGCsInStandardGCIntervalLength;
						
						gcPercentage = (numberofGCsInStandardGCIntervalLength*1.0f/standardGCIntervalLength);
						
						//Write to File
						bufferedWriter.write( (nthBase-standardGCIntervalLength) + "\t" + (nthBase-1) + "\t"  + standardGCIntervalLength + "\t"  + numberofGCsInStandardGCIntervalLength + "\t" + pf.format(gcPercentage)  + System.getProperty("line.separator"));

						//Set numberofGCs to zero 
						numberofGCsInStandardGCIntervalLength = 0;
						
					}//End of IF
					
				}//End of FOR
				/**************************************************/
				
			}//End of WHILE
			/*****************************************************************/
			
			
			
			/*****************************************************************/
			//Last Interval whose length can be less than standardGCIntervalLength
			lengthOfLastInterval = nthBase % standardGCIntervalLength;
			
			//For Debug Purposes
			totalNumberofGCsInChromosome = totalNumberofGCsInChromosome + numberofGCsInStandardGCIntervalLength;
			
			//If there is a last interval that can be written
			if (lengthOfLastInterval>0){
				
				gcPercentage = (numberofGCsInStandardGCIntervalLength*1.0f/lengthOfLastInterval);
				 
				//Write to File
				bufferedWriter.write( (nthBase-lengthOfLastInterval) + "\t" + (nthBase-1) + "\t"  + lengthOfLastInterval + "\t" + numberofGCsInStandardGCIntervalLength +  "\t" + pf.format(gcPercentage) + System.getProperty("line.separator"));
				
			}
			
			if ((lengthOfLastInterval==0) && (numberofGCsInStandardGCIntervalLength>0)){
				System.out.println("There is a situation1!");
			}
			
			if(nthBase!=totalNumberofCharsReadInChromosome){
				System.out.println("There is a situation2!");
			}
			/*****************************************************************/
			
			//Close BufferedReader and BufferedWriter
			bufferedReader.close();
			bufferedWriter.close();
			
			System.out.println(chrName.convertEnumtoString());
			System.out.println("nthBase: " + nthBase);
			System.out.println( "totalNumberofGCsInChromosome: " + totalNumberofGCsInChromosome);
			System.out.println( "totalNumberofCharsReadInChromosome: " + totalNumberofCharsReadInChromosome);
			
				

		logger.info("nthBase must be written once: " + nthBase + " GCIntervalTree construction has ended.");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	
	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");
		
		//Create Chromosome Based GC ISOCHOREs
		for(ChromosomeName chrName: ChromosomeName.values()){
			createGCIsochoreFile(dataFolder,chrName);
		}
		

	}

}
