/**
 * 
 */
package mapabilityandgc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;
import gnu.trove.list.TByteList;
import gnu.trove.list.array.TByteArrayList;

/**
 * @author Burçak Otlu
 * @date Mar 19, 2015
 * @project Glanet 
 *
 *
 * This is the new way of filling GCByteList..
 */
public class ChromosomeBasedGCTroveList {
	
	final static Logger logger = Logger.getLogger(ChromosomeBasedGCTroveList.class);

	
	public static void fillTroveList(
			String dataFolder,
			ChromosomeName chromName,
			TByteList gcByteList){
		
		String gcFastaFileName =   Commons.GC + System.getProperty("file.separator") + chromName.convertEnumtoString() +  Commons.GC_FILE_END;
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		int numberofCharactersRead;

		char[] cbuf = new char[10000];
		char ch;
		int nthBase = 0;
		String strLine;
		
		//byteString has dummy 0 at the beginning
		//Dummy "0" at  the beginning guarantees a positive byte value.
		String  byteString = "0";
		int numberofBasesHasBeenRead = 0;
		int numberofRemainingBases = 0;

		try {
			
			fileReader = FileOperations.createFileReader(dataFolder + gcFastaFileName);
			bufferedReader = new BufferedReader(fileReader);

			// skip first informative line of fasta file
			strLine = bufferedReader.readLine();

			// check whether fasta file starts with > greater character
			if (!strLine.startsWith(">")) {
				logger.info("Fasta file does not start with > character.");
			}

			
			
			while ((numberofCharactersRead = bufferedReader.read(cbuf)) != -1) {

				for (int i = 0; i < numberofCharactersRead; i++) {
					
					//For each read 7 characters
					if (numberofBasesHasBeenRead==7){
						
						gcByteList.add(Byte.parseByte(byteString,2));
						numberofBasesHasBeenRead = 0;
						
						//Initialize byteString with dummy "0" at the beginning
						byteString = "0";
						
					}//End of IF
					
					ch = cbuf[i];

					if ( 	(ch == Commons.NUCLEIC_ACID_UPPER_CASE_A) || 
							(ch == Commons.NUCLEIC_ACID_LOWER_CASE_A) || 
							(ch == Commons.NUCLEIC_ACID_UPPER_CASE_T) || 
							(ch == Commons.NUCLEIC_ACID_LOWER_CASE_T) || 
							(ch == Commons.NUCLEIC_ACID_UPPER_CASE_N) || 
							(ch == Commons.NUCLEIC_ACID_LOWER_CASE_N)) {
						
						byteString = byteString + '0';
						numberofBasesHasBeenRead++;

					} else if (	(ch == Commons.NUCLEIC_ACID_UPPER_CASE_G) || 
								(ch == Commons.NUCLEIC_ACID_LOWER_CASE_G) || 
								(ch == Commons.NUCLEIC_ACID_UPPER_CASE_C) || 
								(ch == Commons.NUCLEIC_ACID_LOWER_CASE_C)) {

						byteString = byteString + '1';
						numberofBasesHasBeenRead++;
					}

				}//End of FOR
				
			}//End of WHILE
			
			
			
			/***********************************************************************/
			/*********************Last Byte*****************************************/
			/***********************************************************************/
			//There might be some remaining bases
			numberofRemainingBases = 7-numberofBasesHasBeenRead;
			
			
			while (numberofRemainingBases>0){
				byteString = byteString + '0';
				numberofRemainingBases--;
			}//End of While
				
			//Add the remaining byteString
			gcByteList.add(Byte.parseByte(byteString,2));
			/***********************************************************************/
			/*********************Last Byte*****************************************/
			/***********************************************************************/
				

		logger.info("nthBase must be written once: " + nthBase + " GCByteList construction has ended.");

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
		// TODO Auto-generated method stub
		
		//Byte b = 100;
		
		byte b1 = 0b000_0000;
		byte b2 = 0b000_1111;
		byte b3 = 0b111_1111; 
		System.out.println(b1 + "\t" + b2 + "\t" + b3);
		
		byte b4 = -0b000_0000;
		byte b5 = -0b000_1111;
		byte b6 = -0b111_1111; 
		System.out.println(b4  + "\t" + b5  + "\t"+ b6);
		
		
		byte b7 = (byte) 0b1111_1111; 
		System.out.println(b7);
		
		System.out.println(Integer.toBinaryString(b6));
		System.out.println(Integer.toHexString(b6));
		System.out.println(Integer.toOctalString(b6));
		
		
		byte b8 = (byte) 129;
		String s1 = String.format("%8s", Integer.toBinaryString(b8 & 0xFF)).replace(' ', '0');
		System.out.println(s1); // 10000001

		byte b9 = (byte) 2;
		String s2 = String.format("%8s", Integer.toBinaryString(b9 & 0xFF)).replace(' ', '0');
		System.out.println(s2); // 00000010
		
//		byte b = Byte.parseByte("01111111", 2);
//		System.out.println(b);
		
		byte b = Byte.parseByte("-01111111", 2);
		System.out.println(b);
		
		
		System.out.println(Byte.parseByte("01100110", 2));
		System.out.println(Byte.parseByte("01111111", 2));
		System.out.println(Byte.parseByte("-01111111", 2));
		
		
		System.out.println("*********");
		byte b10 = Byte.parseByte("01111111", 2);
		byte b11 = Byte.parseByte("-01111101", 2);
		TByteList gcByteList = new TByteArrayList();
		gcByteList.add(b10);
		gcByteList.add(b11);
		
		//byte bit = (byte) ((b11 >> 1) & 1);
		
		System.out.println((-125 >> 7) & 0x01);
		
		System.out.println("*********");		
		

	}

}
