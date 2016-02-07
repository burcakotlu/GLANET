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
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;
import auxiliary.GlanetPercentageFormat;
import common.Commons;
import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import enumtypes.IsochoreFamily;
import ui.GlanetRunner;

/**
 * @author Burcak Otlu
 * @date Apr 21, 2015
 * @project Glanet 
 *
 */
public class GCIsochoreIntervalTreeDataPoolDataCreation {

	final static Logger logger = Logger.getLogger(GCIsochoreIntervalTreeDataPoolDataCreation.class);

	

	@SuppressWarnings( "resource")
	public static void createGCIsochoreFile( String dataFolder, ChromosomeName chrName,List<Integer> hg19ChromosomeSizes) {

		// Input File
		String gcFastaFileName = Commons.GC + System.getProperty( "file.separator") + chrName.convertEnumtoString() + Commons.GC_FILE_END;

		// Output File : Isochore Interval Data
		String gcIsochoreFileName = Commons.GC + System.getProperty( "file.separator") + Commons.GC_ISOCHORE_INTERVAL_TREE_DATA + System.getProperty( "file.separator") + chrName.convertEnumtoString() + Commons.GC_ISOCHORES_FILE_END;

		// Output File : Isochore L1 Pool Data
		String gcIsochoreFamilyL1PoolFileName = Commons.GC + System.getProperty( "file.separator") + Commons.GC_ISOCHORE_FAMILY_POOL_DATA + System.getProperty( "file.separator") + chrName.convertEnumtoString() + Commons.GC_ISOCHOREFAMILY_L1_POOL_FILE_END;

		// Output File : Isochore L2 Pool Data
		String gcIsochoreFamilyL2PoolFileName = Commons.GC + System.getProperty( "file.separator") + Commons.GC_ISOCHORE_FAMILY_POOL_DATA + System.getProperty( "file.separator") + chrName.convertEnumtoString() + Commons.GC_ISOCHOREFAMILY_L2_POOL_FILE_END;

		// Output File : Isochore H1 Pool Data
		String gcIsochoreFamilyH1PoolFileName = Commons.GC + System.getProperty( "file.separator") + Commons.GC_ISOCHORE_FAMILY_POOL_DATA + System.getProperty( "file.separator") + chrName.convertEnumtoString() + Commons.GC_ISOCHOREFAMILY_H1_POOL_FILE_END;

		// Output File : Isochore H2 Pool Data
		String gcIsochoreFamilyH2PoolFileName = Commons.GC + System.getProperty( "file.separator") + Commons.GC_ISOCHORE_FAMILY_POOL_DATA + System.getProperty( "file.separator") + chrName.convertEnumtoString() + Commons.GC_ISOCHOREFAMILY_H2_POOL_FILE_END;

		// Output File : Isochore H3 Pool Data
		String gcIsochoreFamilyH3PoolFileName = Commons.GC + System.getProperty( "file.separator") + Commons.GC_ISOCHORE_FAMILY_POOL_DATA + System.getProperty( "file.separator") + chrName.convertEnumtoString() + Commons.GC_ISOCHOREFAMILY_H3_POOL_FILE_END;

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

		float gcContent = 0;
		IsochoreFamily isochoreFamily;

		NumberFormat pf = GlanetPercentageFormat.getGlanetPercentageFormat();
		
		int chromSize= hg19ChromosomeSizes.get(chrName.getChromosomeName()-1);

		try{

			fileReader = FileOperations.createFileReader( dataFolder + gcFastaFileName);
			bufferedReader = new BufferedReader( fileReader);

			// GC Isochore Interval Tree File
			fileWriter = FileOperations.createFileWriter( dataFolder + gcIsochoreFileName);
			bufferedWriter = new BufferedWriter( fileWriter);

			// GC Isochore Pool File
			fileWriterL1 = FileOperations.createFileWriter( dataFolder + gcIsochoreFamilyL1PoolFileName);
			bufferedWriterL1 = new BufferedWriter( fileWriterL1);

			fileWriterL2 = FileOperations.createFileWriter( dataFolder + gcIsochoreFamilyL2PoolFileName);
			bufferedWriterL2 = new BufferedWriter( fileWriterL2);

			fileWriterH1 = FileOperations.createFileWriter( dataFolder + gcIsochoreFamilyH1PoolFileName);
			bufferedWriterH1 = new BufferedWriter( fileWriterH1);

			fileWriterH2 = FileOperations.createFileWriter( dataFolder + gcIsochoreFamilyH2PoolFileName);
			bufferedWriterH2 = new BufferedWriter( fileWriterH2);

			fileWriterH3 = FileOperations.createFileWriter( dataFolder + gcIsochoreFamilyH3PoolFileName);
			bufferedWriterH3 = new BufferedWriter( fileWriterH3);

			// skip first informative line of fasta file
			// >chr22
			strLine = bufferedReader.readLine();

			// check whether fasta file starts with > greater character
			if( !strLine.startsWith( ">")){
				if( GlanetRunner.shouldLog())logger.info( "Fasta file does not start with > character.");
			}

			// Write header file
			bufferedWriter.write( "#" + "OBasedStart" + "\t" + "OBasedEnd" + "\t" + "numberofGCsInTheInterval" + "\t" + "GCContentPercentage" + "\t" + "Isochore Family" + System.getProperty( "line.separator"));

			/*****************************************************************/
			while( ( numberofCharactersRead = bufferedReader.read( cbuf)) != -1){

				totalNumberofCharsReadInChromosome = totalNumberofCharsReadInChromosome + numberofCharactersRead;

				/**************************************************/
				for( i = 0; i < numberofCharactersRead; i++){

					ch = cbuf[i];

					if( ( ch == Commons.NUCLEIC_ACID_UPPER_CASE_G) || ( ch == Commons.NUCLEIC_ACID_LOWER_CASE_G) || ( ch == Commons.NUCLEIC_ACID_UPPER_CASE_C) || ( ch == Commons.NUCLEIC_ACID_LOWER_CASE_C)){

						numberofGCsInStandardGCIntervalLength++;

					}

					nthBase++;

					// If we have read standardGCIntervalLength many bases
					if( nthBase % standardGCIntervalLength == 0){

						// For Debug Purposes
						totalNumberofGCsInChromosome = totalNumberofGCsInChromosome + numberofGCsInStandardGCIntervalLength;

						gcContent = ( numberofGCsInStandardGCIntervalLength * 1.0f / standardGCIntervalLength);

						isochoreFamily = GC.calculateIsochoreFamily(gcContent);

						switch( isochoreFamily){

						case L1:
							bufferedWriterPool = bufferedWriterL1;
							break;
						case L2:
							bufferedWriterPool = bufferedWriterL2;
							break;
						case H1:
							bufferedWriterPool = bufferedWriterH1;
							break;
						case H2:
							bufferedWriterPool = bufferedWriterH2;
							break;
						case H3:
							bufferedWriterPool = bufferedWriterH3;
							break;
						}// End of switch
						
						//Check whether start and end does not exceed hg19 chromSize
						if(chromSize>(nthBase - standardGCIntervalLength) && chromSize>(nthBase - 1)){
							// Write to Isochore Pool File
							bufferedWriterPool.write((nthBase - standardGCIntervalLength) + "\t" + (nthBase - 1) + System.getProperty( "line.separator"));

							// Write to Isochore Interval Tree File
							bufferedWriter.write((nthBase - standardGCIntervalLength) + "\t" + (nthBase - 1) + "\t" + numberofGCsInStandardGCIntervalLength + "\t" + pf.format(gcContent) + "\t" + isochoreFamily + System.getProperty( "line.separator"));
						}//End of IF

						
						// Set numberofGCs to zero
						numberofGCsInStandardGCIntervalLength = 0;

					}// End of IF

				}// End of FOR
				/**************************************************/

			}// End of WHILE
			/*****************************************************************/

			/*****************************************************************/
			// Last Interval whose length can be less than standardGCIntervalLength
			lengthOfLastInterval = nthBase % standardGCIntervalLength;

			// For Debug Purposes
			totalNumberofGCsInChromosome = totalNumberofGCsInChromosome + numberofGCsInStandardGCIntervalLength;

			// If there is a last interval that can be written
			if( lengthOfLastInterval > 0){

				gcContent = ( numberofGCsInStandardGCIntervalLength * 1.0f / lengthOfLastInterval);

				isochoreFamily = GC.calculateIsochoreFamily(gcContent);

				switch( isochoreFamily){

				case L1:
					bufferedWriterPool = bufferedWriterL1;
					break;
				case L2:
					bufferedWriterPool = bufferedWriterL2;
					break;
				case H1:
					bufferedWriterPool = bufferedWriterH1;
					break;
				case H2:
					bufferedWriterPool = bufferedWriterH2;
					break;
				case H3:
					bufferedWriterPool = bufferedWriterH3;
					break;
				}// End of switch
				
				//Check whether start and end does not exceed hg19 chromSize
				if(chromSize>(nthBase - lengthOfLastInterval) && chromSize>(nthBase - 1)){
					// Write to Isochore Pool File
					bufferedWriterPool.write( ( nthBase - lengthOfLastInterval) + "\t" + ( nthBase - 1) + System.getProperty( "line.separator"));

					// Write to Isochore Interval Tree File
					bufferedWriter.write( ( nthBase - lengthOfLastInterval) + "\t" + ( nthBase - 1) + "\t" + numberofGCsInStandardGCIntervalLength + "\t" + pf.format(gcContent) + "\t" + isochoreFamily + System.getProperty( "line.separator"));
				}


			}// End of IF there is a last interval

			if( ( lengthOfLastInterval == 0) && ( numberofGCsInStandardGCIntervalLength > 0)){
				if( GlanetRunner.shouldLog())logger.error( "There is a situation1!");
			}

			if( nthBase != totalNumberofCharsReadInChromosome){
				if( GlanetRunner.shouldLog())logger.error( "There is a situation2!");
			}
			/*****************************************************************/

			// Close BufferedReader and BufferedWriter
			bufferedReader.close();
			bufferedWriter.close();

			bufferedWriterL1.close();
			bufferedWriterL2.close();
			bufferedWriterH1.close();
			bufferedWriterH2.close();
			bufferedWriterH3.close();

			System.out.println( chrName.convertEnumtoString());
			System.out.println( "nthBase: " + nthBase);
			System.out.println( "totalNumberofGCsInChromosome: " + totalNumberofGCsInChromosome);
			System.out.println( "totalNumberofCharsReadInChromosome: " + totalNumberofCharsReadInChromosome);

			if( GlanetRunner.shouldLog())logger.info( "nthBase must be written once: " + nthBase + " GCIntervalTree construction has ended.");

		}catch( FileNotFoundException e){
			e.printStackTrace();
		}catch( IOException e){
			e.printStackTrace();
		}

	}

	public static void main( String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
		
		/********************************************************************************************************/
		/******************************* GET HG19 CHROMOSOME SIZES STARTS ***************************************/
		List<Integer> hg19ChromosomeSizes = new ArrayList<Integer>();

		hg19.GRCh37Hg19Chromosome.initializeChromosomeSizes( hg19ChromosomeSizes);
		// get the hg19 chromosome sizes
		hg19.GRCh37Hg19Chromosome.getHg19ChromosomeSizes( hg19ChromosomeSizes, dataFolder,Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		/******************************* GET HG19 CHROMOSOME SIZES ENDS *****************************************/
		/********************************************************************************************************/

		// Create Chromosome Based GC ISOCHOREs
		for( ChromosomeName chrName : ChromosomeName.values()){
			createGCIsochoreFile(dataFolder, chrName,hg19ChromosomeSizes);
		}

	}

}
