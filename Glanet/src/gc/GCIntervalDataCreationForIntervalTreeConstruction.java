/**
 * This class creates Chromosome Based GC Interval File
 * for GC Interval Tree Construction
 */
package gc;

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
public class GCIntervalDataCreationForIntervalTreeConstruction {
	
	final static Logger logger = Logger.getLogger(GCIntervalDataCreationForIntervalTreeConstruction.class);
	
	
	//Create GC Interval Data where consecutive zeros intervals are merged
	//In this way number of nodes in the GC interval tree will be minimized
	public static void createGCIntervalDataConsecutiveZerosAreMergedFile(String dataFolder, ChromosomeName  chrName){
		
		String gcIntervalDataFileName =   Commons.GC + System.getProperty("file.separator") +Commons.GC_INTERVAL_TREE_DATA + System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_INTERVALS_FILE_END;
		String gcIntervalDataConsecutiveZerosMergedFileName =   Commons.GC + System.getProperty("file.separator") + Commons.GC_INTERVAL_TREE_DATA + System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_INTERVALS_CONSECUTIVE_ZEROS_MERGED_FILE_END;
		
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		
		int startZeroBased;
		int endZeroBased;
		int numberofGSsInStartdardIntervalLength;
		
		boolean  candidateFirstIntervalofConsecutiveIntervalsWithNumberofGCsZeroMayStart = true;
		boolean  mergedIntervalofConsecutiveIntervalsWithNumberofGcsZeroCanBeWritten = true;
		
		int startZeroBasedForConsecutiveZerosInterval = -1;
		int endZeroBasedForConsecutiveZerosInterval = -1;
		int numberofIntervalsInTheToBeMergedConsecutiveZerosInterval = 0;
		
		try {
			fileReader = FileOperations.createFileReader(dataFolder + gcIntervalDataFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(dataFolder + gcIntervalDataConsecutiveZerosMergedFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null) {
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = (indexofFirstTab>0)?strLine.indexOf('\t',indexofFirstTab+1):-1;
				
				startZeroBased = Integer.parseInt(strLine.substring(0, indexofFirstTab));
				endZeroBased = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				numberofGSsInStartdardIntervalLength = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				
				
				//An interval with numberofGSsInStartdardIntervalLength is greater than 0 
				if (numberofGSsInStartdardIntervalLength !=0 ){
					
					//An interval with numberofGSsInStartdardIntervalLength greater than 0 is faced
					//So candidate interval with numberofGCs equals to zero can be seen
					candidateFirstIntervalofConsecutiveIntervalsWithNumberofGCsZeroMayStart = true;
					
					//First Write Merged Interval with consecutive zeros
					//Write consecutive Zeros Merged  Interval if it exists
					//At least there must be two such intervals to be merged
					//Can be written once
					if(numberofIntervalsInTheToBeMergedConsecutiveZerosInterval>1 && mergedIntervalofConsecutiveIntervalsWithNumberofGcsZeroCanBeWritten){
						bufferedWriter.write(startZeroBasedForConsecutiveZerosInterval + "\t" + endZeroBasedForConsecutiveZerosInterval + "\t" + Commons.ZERO + System.getProperty("line.separator"));
						mergedIntervalofConsecutiveIntervalsWithNumberofGcsZeroCanBeWritten = false;
					}
					
					
					//First Write Not Merged Interval with one zero
					//Can be written once
					if(numberofIntervalsInTheToBeMergedConsecutiveZerosInterval==1 && mergedIntervalofConsecutiveIntervalsWithNumberofGcsZeroCanBeWritten){
						bufferedWriter.write(startZeroBasedForConsecutiveZerosInterval + "\t" + endZeroBasedForConsecutiveZerosInterval + "\t" + Commons.ZERO + System.getProperty("line.separator"));
						mergedIntervalofConsecutiveIntervalsWithNumberofGcsZeroCanBeWritten = false;
					}
					
					
					//Second
					//Write the interval with numberofGSsInStartdardIntervalLength is greater than zero
					bufferedWriter.write(startZeroBased + "\t" + endZeroBased + "\t" + numberofGSsInStartdardIntervalLength + System.getProperty("line.separator"));
					
				}
				
				//First interval with numberofGSsInStartdardIntervalLength is 0
				//Keep the first interval's zeroBasedStartPosition for ToBeMergedInterval
				if (numberofGSsInStartdardIntervalLength == 0 && candidateFirstIntervalofConsecutiveIntervalsWithNumberofGCsZeroMayStart){
					
					startZeroBasedForConsecutiveZerosInterval = startZeroBased;
					
					//There might be only one interval with numberofGSsInStartdardIntervalLength is equals to 0.
					endZeroBasedForConsecutiveZerosInterval = endZeroBased;
					
					candidateFirstIntervalofConsecutiveIntervalsWithNumberofGCsZeroMayStart = false;
					numberofIntervalsInTheToBeMergedConsecutiveZerosInterval++;
					
					//Set it to true
					//A merged interval of consecutive intervals with numberofGCs is zero can be written before the first interval with numberofGSsInStartdardIntervalLength is greater than 0.
					mergedIntervalofConsecutiveIntervalsWithNumberofGcsZeroCanBeWritten = true;
				}
				
				
				//Last interval with numberofGSsInStartdardIntervalLength is 0
				//Keep the last interval's zeroBasedEndPosition for ToBeMergedInterval
				if (numberofGSsInStartdardIntervalLength == 0 && !candidateFirstIntervalofConsecutiveIntervalsWithNumberofGCsZeroMayStart){
					
					endZeroBasedForConsecutiveZerosInterval = endZeroBased;
					numberofIntervalsInTheToBeMergedConsecutiveZerosInterval++;
					
				}
				
				
				
			}//End of While
			
			
			/*****************************************************************************************/
			//Write the last merged Interval if it exists.
			//First Merged Interval with consecutive zeros
			//Write consecutive Zeros Merged  Interval if it exists
			//At least there must be two such intervals to be merged
			//Can be written once
			if(numberofIntervalsInTheToBeMergedConsecutiveZerosInterval>1 && mergedIntervalofConsecutiveIntervalsWithNumberofGcsZeroCanBeWritten){
				bufferedWriter.write(startZeroBasedForConsecutiveZerosInterval + "\t" + endZeroBasedForConsecutiveZerosInterval + "\t" + Commons.ZERO + System.getProperty("line.separator"));
			}
			
			
			//First Not Merged Interval with one zero
			//Can be written once
			if(numberofIntervalsInTheToBeMergedConsecutiveZerosInterval==1 && mergedIntervalofConsecutiveIntervalsWithNumberofGcsZeroCanBeWritten){
				bufferedWriter.write(startZeroBasedForConsecutiveZerosInterval + "\t" + endZeroBasedForConsecutiveZerosInterval + "\t" + Commons.ZERO + System.getProperty("line.separator"));
			}
			/*****************************************************************************************/

			
			
			
			//Close
			bufferedReader.close();
			bufferedWriter.close();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		
		
	}
	
	public static void createGCIntervalDataFile(String dataFolder, ChromosomeName  chrName){
		
		String gcFastaFileName =   Commons.GC + System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_FILE_END;
		String gcIntervalDataFileName =   Commons.GC + System.getProperty("file.separator") + Commons.GC_INTERVAL_TREE_DATA  + System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_INTERVALS_FILE_END;
		
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
		short numberofGCsInStandardGCIntervalLength = 0;
		int totalNumberofCharsRead = 0;
		int totalNumberofGCs = 0;
		
		int standardGCIntervalLength = Commons.GC_INTERVALTREE_INTERVALLENGTH_100;
		
		
	
		try {
			
			fileReader = FileOperations.createFileReader(dataFolder + gcFastaFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(dataFolder + gcIntervalDataFileName);
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

						numberofGCsInStandardGCIntervalLength++;
						
					}
					
					nthBase++;
					
					//If we have read standardGCIntervalLength many bases 
					if (nthBase % standardGCIntervalLength == 0){
						
						//For Debug Purposes
						totalNumberofGCs = totalNumberofGCs + numberofGCsInStandardGCIntervalLength;
						
						//Write to File
						bufferedWriter.write( (nthBase-standardGCIntervalLength) + "\t" + (nthBase-1) + "\t"  + numberofGCsInStandardGCIntervalLength + System.getProperty("line.separator"));

						//Set numberofGCs to zero 
						numberofGCsInStandardGCIntervalLength = 0;
						
					}
					
				}//End of FOR
				/**************************************************/
				
			}//End of WHILE
			/*****************************************************************/
			
			
			
			/*****************************************************************/
			//Last Interval whose length can be less than standardGCIntervalLength
			lengthOfLastInterval = nthBase % standardGCIntervalLength;
			
			//For Debug Purposes
			totalNumberofGCs = totalNumberofGCs + numberofGCsInStandardGCIntervalLength;
			
			//If there is a last interval that can be written
			if (lengthOfLastInterval>0){
				
				//Write to File
				bufferedWriter.write( (nthBase-lengthOfLastInterval) + "\t" + (nthBase-1) + "\t"  + numberofGCsInStandardGCIntervalLength + System.getProperty("line.separator"));
				
			}
			/*****************************************************************/
			
			//Close BufferedReader and BufferedWriter
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
	
		//First Pass
		//Create chromosome based GC Interval Data
		//There might be consecutive intervals with numberofGCs is equal to zero.
		for(ChromosomeName chrName: ChromosomeName.values()){
			createGCIntervalDataFile(dataFolder,chrName);
		}
		
		//Second Pass
		//Create chromosome based GC Interval Data where consecutive intervals with numberofGCs equal to zero are merged.
		for(ChromosomeName chrName: ChromosomeName.values()){
			createGCIntervalDataConsecutiveZerosAreMergedFile(dataFolder,chrName);
		}
	}

}
