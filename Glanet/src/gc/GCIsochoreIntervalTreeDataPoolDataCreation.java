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
import enumtypes.IsochoreFamily;

/**
 * @author Burçak Otlu
 * @date Apr 21, 2015
 * @project Glanet 
 *
 */
public class GCIsochoreIntervalTreeDataPoolDataCreation {
	
	final static Logger logger = Logger.getLogger(GCIsochoreIntervalTreeDataPoolDataCreation.class);
	
	public static IsochoreFamily calculateIsochoreFamily(float gcPercentage){
		
		IsochoreFamily isochoreFamily = null;
		
		//L1, L2, H1, H2 and H3, with GC contents of
		//38%, 38–42%, 42–47%, 47–52%, respectively
		
		if (gcPercentage<38){
			isochoreFamily = IsochoreFamily.L1;
		}else if (gcPercentage>=38 && gcPercentage <42){
			isochoreFamily =  IsochoreFamily.L2;
		}else if (gcPercentage>=42 && gcPercentage<47){
			isochoreFamily =  IsochoreFamily.H1;
		}else if (gcPercentage>=47 && gcPercentage<52){
			isochoreFamily =  IsochoreFamily.H2;
		}else if (gcPercentage>=52){
			isochoreFamily =  IsochoreFamily.H3;
		}
		
		return isochoreFamily;
	}
	
	
	@SuppressWarnings("resource")
	public static void createGCIsochoreFile(String dataFolder,ChromosomeName chrName){
		
		//Input File
		String gcFastaFileName =   Commons.GC + System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_FILE_END;
		
		//Output File : Isochore Interval Data 
		String gcIsochoreFileName =   Commons.GC + System.getProperty("file.separator") + Commons.GC_ISOCHORE_INTERVAL_TREE_DATA +  System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_ISOCHORES_FILE_END;
		
		//Output File : Isochore L1 Pool Data 
		String gcIsochoreFamilyL1PoolFileName =   Commons.GC + System.getProperty("file.separator") + Commons.GC_ISOCHORE_FAMILY_POOL_DATA +  System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_ISOCHOREFAMILY_L1_POOL_FILE_END;

		//Output File : Isochore L2 Pool Data 
		String gcIsochoreFamilyL2PoolFileName =   Commons.GC + System.getProperty("file.separator") + Commons.GC_ISOCHORE_FAMILY_POOL_DATA +  System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_ISOCHOREFAMILY_L2_POOL_FILE_END;
		
		//Output File : Isochore H1 Pool Data 
		String gcIsochoreFamilyH1PoolFileName =   Commons.GC + System.getProperty("file.separator") + Commons.GC_ISOCHORE_FAMILY_POOL_DATA +  System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_ISOCHOREFAMILY_H1_POOL_FILE_END;
		
		//Output File : Isochore H2 Pool Data 
		String gcIsochoreFamilyH2PoolFileName =   Commons.GC + System.getProperty("file.separator") + Commons.GC_ISOCHORE_FAMILY_POOL_DATA +  System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_ISOCHOREFAMILY_H2_POOL_FILE_END;
		
		//Output File : Isochore H3 Pool Data 
		String gcIsochoreFamilyH3PoolFileName =   Commons.GC + System.getProperty("file.separator") + Commons.GC_ISOCHORE_FAMILY_POOL_DATA +  System.getProperty("file.separator") + chrName.convertEnumtoString() +  Commons.GC_ISOCHOREFAMILY_H3_POOL_FILE_END;

		FileReader fileReader;
		BufferedReader bufferedReader;
		
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;

		FileWriter fileWriterL1;
		FileWriter fileWriterL2;
		FileWriter fileWriterH1;
		FileWriter fileWriterH2;
		FileWriter fileWriterH3;

		BufferedWriter bufferedWriterL1;
		BufferedWriter bufferedWriterL2;
		BufferedWriter bufferedWriterH1;
		BufferedWriter bufferedWriterH2;
		BufferedWriter bufferedWriterH3;

		BufferedWriter bufferedWriterPool = null;
				
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
		IsochoreFamily isochoreFamily;
		
		NumberFormat pf = GlanetPercentageFormat.getGlanetPercentageFormat();
		
	
		try {
			
			fileReader = FileOperations.createFileReader(dataFolder + gcFastaFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//GC Isochore Interval Tree File
			fileWriter = FileOperations.createFileWriter(dataFolder + gcIsochoreFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			//GC Isochore Pool File
			fileWriterL1 = FileOperations.createFileWriter(dataFolder + gcIsochoreFamilyL1PoolFileName);
			bufferedWriterL1 = new BufferedWriter(fileWriterL1);

			fileWriterL2 = FileOperations.createFileWriter(dataFolder + gcIsochoreFamilyL2PoolFileName);
			bufferedWriterL2 = new BufferedWriter(fileWriterL2);

			fileWriterH1 = FileOperations.createFileWriter(dataFolder + gcIsochoreFamilyH1PoolFileName);
			bufferedWriterH1 = new BufferedWriter(fileWriterH1);

			fileWriterH2 = FileOperations.createFileWriter(dataFolder + gcIsochoreFamilyH2PoolFileName);
			bufferedWriterH2 = new BufferedWriter(fileWriterH2);

			fileWriterH3 = FileOperations.createFileWriter(dataFolder + gcIsochoreFamilyH3PoolFileName);
			bufferedWriterH3 = new BufferedWriter(fileWriterH3);
			
			// skip first informative line of fasta file
			// >chr22
			strLine = bufferedReader.readLine();

			// check whether fasta file starts with > greater character
			if (!strLine.startsWith(">")) {
				logger.info("Fasta file does not start with > character.");
			}

			
			//Write header file
			bufferedWriter.write("#" + "OBasedStart" + "\t" + "OBasedEnd" + "\t"  + "numberofGCsInTheInterval" + "\t" +  "GCContentPercentage" + "\t" + "Isochore Family" + System.getProperty("line.separator"));
			
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
						
						isochoreFamily = calculateIsochoreFamily(gcPercentage*100);
						
						switch(isochoreFamily) {
						
							case L1: 	bufferedWriterPool = bufferedWriterL1; 
										break;
							case L2: 	bufferedWriterPool = bufferedWriterL2;
										break;
							case H1: 	bufferedWriterPool = bufferedWriterH1;
										break;
							case H2: 	bufferedWriterPool = bufferedWriterH2;
										break;
							case H3:	bufferedWriterPool = bufferedWriterH3;
										break;
						}//End of switch
						
						
						//Write to Isochore Pool File
						bufferedWriterPool.write((nthBase-standardGCIntervalLength) + "\t" + (nthBase-1) + System.getProperty("line.separator"));
						
						//Write to Isochore Interval Tree File
						bufferedWriter.write((nthBase-standardGCIntervalLength) + "\t" + (nthBase-1)  + "\t"  + numberofGCsInStandardGCIntervalLength + "\t" + pf.format(gcPercentage) + "\t" + isochoreFamily + System.getProperty("line.separator"));

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
				 
				isochoreFamily = calculateIsochoreFamily(gcPercentage*100);
				
				switch(isochoreFamily) {
				
					case L1: 	bufferedWriterPool = bufferedWriterL1; 
								break;
					case L2: 	bufferedWriterPool = bufferedWriterL2;
								break;
					case H1: 	bufferedWriterPool = bufferedWriterH1;
								break;
					case H2: 	bufferedWriterPool = bufferedWriterH2;
								break;
					case H3:	bufferedWriterPool = bufferedWriterH3;
								break;
				}//End of switch
			
			
				//Write to Isochore Pool File
				bufferedWriterPool.write((nthBase-lengthOfLastInterval) + "\t" + (nthBase-1)   + System.getProperty("line.separator"));
			
				
				//Write to Isochore Interval Tree File
				bufferedWriter.write( (nthBase-lengthOfLastInterval) + "\t" + (nthBase-1) + "\t" + numberofGCsInStandardGCIntervalLength +  "\t" + pf.format(gcPercentage) + "\t" + isochoreFamily + System.getProperty("line.separator"));
				
			}//End of IF there is a last interval
			
			if ((lengthOfLastInterval==0) && (numberofGCsInStandardGCIntervalLength>0)){
				logger.error("There is a situation1!");
			}
			
			if(nthBase!=totalNumberofCharsReadInChromosome){
				logger.error("There is a situation2!");
			}
			/*****************************************************************/
			
			//Close BufferedReader and BufferedWriter
			bufferedReader.close();
			bufferedWriter.close();
			
			bufferedWriterL1.close();
			bufferedWriterL2.close();
			bufferedWriterH1.close();
			bufferedWriterH2.close();
			bufferedWriterH3.close();
			
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
