/**
 * This class creates Chromosome Based GC Interval File
 * for GC Interval Tree Construction
 */
package mapabilityandgc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;

/**
 * @author Burçak Otlu
 * @date Apr 10, 2015
 * @project Glanet 
 *
 */
public class CreationofChromosomeBasedGCIntervalFiles {
	
	final static Logger logger = Logger.getLogger(CreationofChromosomeBasedGCIntervalFiles.class);
	
	public static void createGCIntervalFile(String dataFolder, ChromosomeName  chrName){
		
		String gcFastaFileName =   Commons.GC + System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_FILE_END;
		String gcIntervalTreeFileName =   Commons.GC + System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_INTERVALS_FILE_END;
		
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
		short numberofGCs = 0;
		int totalNumberofCharsRead = 0;
		int totalNumberofGCs = 0;
		
		int standardGCIntervalLength = Commons.GC_INTERVALTREE_INTERVALLENGTH_100;
		
	
		try {
			
			fileReader = FileOperations.createFileReader(dataFolder + gcFastaFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(dataFolder + gcIntervalTreeFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			// skip first informative line of fasta file
			// >chr22
			strLine = bufferedReader.readLine();

			// check whether fasta file starts with > greater character
			if (!strLine.startsWith(">")) {
				logger.info("Fasta file does not start with > character.");
			}

			
			
			/*****************************************************************/
			while ((numberofCharactersRead = bufferedReader.read(cbuf)) != -1) {
				
				totalNumberofCharsRead = totalNumberofCharsRead + numberofCharactersRead;
				
				/**************************************************/
				for (i = 0; i < numberofCharactersRead; i++) {
					
					ch = cbuf[i];

					if (	(ch == Commons.NUCLEIC_ACID_UPPER_CASE_G) || 
							(ch == Commons.NUCLEIC_ACID_LOWER_CASE_G) || 
							(ch == Commons.NUCLEIC_ACID_UPPER_CASE_C) || 
							(ch == Commons.NUCLEIC_ACID_LOWER_CASE_C)) {

						numberofGCs++;
						
					}
					
					nthBase++;
					
					if (nthBase % standardGCIntervalLength == 0){
						
						//For Debug Purposes
						totalNumberofGCs = totalNumberofGCs + numberofGCs;
						
						//Write to File
						bufferedWriter.write( (nthBase-standardGCIntervalLength) + "\t" + (nthBase-1) + "\t"  + numberofGCs + System.getProperty("line.separator"));

						//Set numberofGCs to zero 
						numberofGCs = 0;
						
					}
					
				}//End of FOR
				/**************************************************/
				
			}//End of WHILE
			/*****************************************************************/
			
			
			
			/*****************************************************************/
			//Last Interval whose length can be less than standardGCIntervalLength
			lengthOfLastInterval = nthBase % standardGCIntervalLength;
			
			//For Debug Purposes
			totalNumberofGCs = totalNumberofGCs + numberofGCs;
			
			//If there is a last interval that can be written
			if (lengthOfLastInterval>0){
				
				//Write to File
				bufferedWriter.write( (nthBase-lengthOfLastInterval) + "\t" + (nthBase-1) + "\t"  + numberofGCs + System.getProperty("line.separator"));
				
			}
			/*****************************************************************/
			
			//Close 
			bufferedReader.close();
			bufferedWriter.close();
			
			System.out.println(chrName.convertEnumtoString());
			System.out.println("nthBase: " + nthBase);
			System.out.println( "totalNumberofGCs: " + totalNumberofGCs);
			System.out.println( "totalNumberofCharsRead: " + totalNumberofCharsRead);
			
				

		logger.info("nthBase must be written once: " + nthBase + " GCIntervalTree construction has ended.");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");
	
		for(ChromosomeName chrName: ChromosomeName.values()){
			createGCIntervalFile(dataFolder,chrName);
		}
		
	}

}
