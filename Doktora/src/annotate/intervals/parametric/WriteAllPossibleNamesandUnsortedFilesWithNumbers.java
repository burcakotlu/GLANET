/**
 * @author Burcak Otlu
 *	It lasts 8 minutes.
 * 
 */

package annotate.intervals.parametric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.IntervalName;
import gnu.trove.iterator.TObjectShortIterator;
import gnu.trove.iterator.TShortObjectIterator;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.TShortObjectMap;

public class WriteAllPossibleNamesandUnsortedFilesWithNumbers {
	
	public static void closeBufferedReaders(List<BufferedReader> bufferedReaderList){
		try {
			for(int i = 0; i<bufferedReaderList.size(); i++){			
					bufferedReaderList.get(i).close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void closeBufferedWriters(List<BufferedWriter> bufferedWriterList){
		try {
			for(int i = 0; i<bufferedWriterList.size(); i++){			
					bufferedWriterList.get(i).close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public static void createChromBaseDnaseBufferedWriters(String dataFolder,List<BufferedWriter> bufferedWriterList){
		
		try {
			FileWriter fileWriter1 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR1_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter2 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR2_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter3 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR3_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter4 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR4_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter5 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR5_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter6 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR6_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter7 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR7_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter8 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR8_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter9 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR9_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter10 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR10_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter11 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR11_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter12 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR12_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter13 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR13_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter14 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR14_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter15 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR15_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter16 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR16_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter17 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR17_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter18 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR18_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter19 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR19_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter20 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR20_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter21 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR21_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter22 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR22_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter23 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRX_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter24 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRY_DNASE_FILENAME_WITH_NUMBERS);
			
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
			BufferedWriter bufferedWriter4 = new BufferedWriter(fileWriter4);
			BufferedWriter bufferedWriter5 = new BufferedWriter(fileWriter5);
			BufferedWriter bufferedWriter6 = new BufferedWriter(fileWriter6);
			BufferedWriter bufferedWriter7 = new BufferedWriter(fileWriter7);
			BufferedWriter bufferedWriter8 = new BufferedWriter(fileWriter8);
			BufferedWriter bufferedWriter9 = new BufferedWriter(fileWriter9);
			BufferedWriter bufferedWriter10 = new BufferedWriter(fileWriter10);
			BufferedWriter bufferedWriter11 = new BufferedWriter(fileWriter11);
			BufferedWriter bufferedWriter12 = new BufferedWriter(fileWriter12);
			BufferedWriter bufferedWriter13 = new BufferedWriter(fileWriter13);
			BufferedWriter bufferedWriter14 = new BufferedWriter(fileWriter14);
			BufferedWriter bufferedWriter15 = new BufferedWriter(fileWriter15);
			BufferedWriter bufferedWriter16 = new BufferedWriter(fileWriter16);
			BufferedWriter bufferedWriter17 = new BufferedWriter(fileWriter17);
			BufferedWriter bufferedWriter18 = new BufferedWriter(fileWriter18);
			BufferedWriter bufferedWriter19 = new BufferedWriter(fileWriter19);
			BufferedWriter bufferedWriter20 = new BufferedWriter(fileWriter20);
			BufferedWriter bufferedWriter21 = new BufferedWriter(fileWriter21);
			BufferedWriter bufferedWriter22 = new BufferedWriter(fileWriter22);
			BufferedWriter bufferedWriter23 = new BufferedWriter(fileWriter23);
			BufferedWriter bufferedWriter24 = new BufferedWriter(fileWriter24);
			
			bufferedWriterList.add(bufferedWriter1);
			bufferedWriterList.add(bufferedWriter2);
			bufferedWriterList.add(bufferedWriter3);
			bufferedWriterList.add(bufferedWriter4);
			bufferedWriterList.add(bufferedWriter5);
			bufferedWriterList.add(bufferedWriter6);
			bufferedWriterList.add(bufferedWriter7);
			bufferedWriterList.add(bufferedWriter8);
			bufferedWriterList.add(bufferedWriter9);
			bufferedWriterList.add(bufferedWriter10);
			bufferedWriterList.add(bufferedWriter11);
			bufferedWriterList.add(bufferedWriter12);
			bufferedWriterList.add(bufferedWriter13);
			bufferedWriterList.add(bufferedWriter14);
			bufferedWriterList.add(bufferedWriter15);
			bufferedWriterList.add(bufferedWriter16);
			bufferedWriterList.add(bufferedWriter17);
			bufferedWriterList.add(bufferedWriter18);
			bufferedWriterList.add(bufferedWriter19);
			bufferedWriterList.add(bufferedWriter20);
			bufferedWriterList.add(bufferedWriter21);
			bufferedWriterList.add(bufferedWriter22);
			bufferedWriterList.add(bufferedWriter23);
			bufferedWriterList.add(bufferedWriter24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void createChromBaseDnaseBufferedReaders(String dataFolder,List<BufferedReader> bufferedReaderList){
		
		try {
			FileReader fileReader1 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR1_DNASE_FILENAME);
			FileReader fileReader2 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR2_DNASE_FILENAME);
			FileReader fileReader3 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR3_DNASE_FILENAME);
			FileReader fileReader4 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR4_DNASE_FILENAME);
			FileReader fileReader5 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR5_DNASE_FILENAME);
			FileReader fileReader6 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR6_DNASE_FILENAME);
			FileReader fileReader7 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR7_DNASE_FILENAME);
			FileReader fileReader8 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR8_DNASE_FILENAME);
			FileReader fileReader9 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR9_DNASE_FILENAME);
			FileReader fileReader10 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR10_DNASE_FILENAME);
			FileReader fileReader11 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR11_DNASE_FILENAME);
			FileReader fileReader12 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR12_DNASE_FILENAME);
			FileReader fileReader13 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR13_DNASE_FILENAME);
			FileReader fileReader14 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR14_DNASE_FILENAME);
			FileReader fileReader15 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR15_DNASE_FILENAME);
			FileReader fileReader16 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR16_DNASE_FILENAME);
			FileReader fileReader17 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR17_DNASE_FILENAME);
			FileReader fileReader18 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR18_DNASE_FILENAME);
			FileReader fileReader19 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR19_DNASE_FILENAME);
			FileReader fileReader20 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR20_DNASE_FILENAME);
			FileReader fileReader21 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR21_DNASE_FILENAME);
			FileReader fileReader22 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR22_DNASE_FILENAME);
			FileReader fileReader23 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRX_DNASE_FILENAME);
			FileReader fileReader24 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRY_DNASE_FILENAME);
			
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
			BufferedReader bufferedReader4 = new BufferedReader(fileReader4);
			BufferedReader bufferedReader5 = new BufferedReader(fileReader5);
			BufferedReader bufferedReader6 = new BufferedReader(fileReader6);
			BufferedReader bufferedReader7 = new BufferedReader(fileReader7);
			BufferedReader bufferedReader8 = new BufferedReader(fileReader8);
			BufferedReader bufferedReader9 = new BufferedReader(fileReader9);
			BufferedReader bufferedReader10 = new BufferedReader(fileReader10);
			BufferedReader bufferedReader11 = new BufferedReader(fileReader11);
			BufferedReader bufferedReader12 = new BufferedReader(fileReader12);
			BufferedReader bufferedReader13 = new BufferedReader(fileReader13);
			BufferedReader bufferedReader14 = new BufferedReader(fileReader14);
			BufferedReader bufferedReader15 = new BufferedReader(fileReader15);
			BufferedReader bufferedReader16 = new BufferedReader(fileReader16);
			BufferedReader bufferedReader17 = new BufferedReader(fileReader17);
			BufferedReader bufferedReader18 = new BufferedReader(fileReader18);
			BufferedReader bufferedReader19 = new BufferedReader(fileReader19);
			BufferedReader bufferedReader20 = new BufferedReader(fileReader20);
			BufferedReader bufferedReader21 = new BufferedReader(fileReader21);
			BufferedReader bufferedReader22 = new BufferedReader(fileReader22);
			BufferedReader bufferedReader23 = new BufferedReader(fileReader23);
			BufferedReader bufferedReader24 = new BufferedReader(fileReader24);
			
			bufferedReaderList.add(bufferedReader1);
			bufferedReaderList.add(bufferedReader2);
			bufferedReaderList.add(bufferedReader3);
			bufferedReaderList.add(bufferedReader4);
			bufferedReaderList.add(bufferedReader5);
			bufferedReaderList.add(bufferedReader6);
			bufferedReaderList.add(bufferedReader7);
			bufferedReaderList.add(bufferedReader8);
			bufferedReaderList.add(bufferedReader9);
			bufferedReaderList.add(bufferedReader10);
			bufferedReaderList.add(bufferedReader11);
			bufferedReaderList.add(bufferedReader12);
			bufferedReaderList.add(bufferedReader13);
			bufferedReaderList.add(bufferedReader14);
			bufferedReaderList.add(bufferedReader15);
			bufferedReaderList.add(bufferedReader16);
			bufferedReaderList.add(bufferedReader17);
			bufferedReaderList.add(bufferedReader18);
			bufferedReaderList.add(bufferedReader19);
			bufferedReaderList.add(bufferedReader20);
			bufferedReaderList.add(bufferedReader21);
			bufferedReaderList.add(bufferedReader22);
			bufferedReaderList.add(bufferedReader23);
			bufferedReaderList.add(bufferedReader24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	public static void createChromBaseTfbsBufferedWriters(String outputFolder,List<BufferedWriter> bufferedWriterList){

		try {
			FileWriter fileWriter1 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR1_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter2 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR2_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter3 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR3_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter4 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR4_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter5 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR5_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter6 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR6_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter7 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR7_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter8 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR8_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter9 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR9_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter10 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR10_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter11 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR11_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter12 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR12_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter13 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR13_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter14 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR14_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter15 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR15_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter16 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR16_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter17 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR17_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter18 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR18_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter19 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR19_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter20 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR20_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter21 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR21_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter22 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR22_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter23 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRX_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter24 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRY_TFBS_FILENAME_WITH_NUMBERS);;
			
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
			BufferedWriter bufferedWriter4 = new BufferedWriter(fileWriter4);
			BufferedWriter bufferedWriter5 = new BufferedWriter(fileWriter5);
			BufferedWriter bufferedWriter6 = new BufferedWriter(fileWriter6);
			BufferedWriter bufferedWriter7 = new BufferedWriter(fileWriter7);
			BufferedWriter bufferedWriter8 = new BufferedWriter(fileWriter8);
			BufferedWriter bufferedWriter9 = new BufferedWriter(fileWriter9);
			BufferedWriter bufferedWriter10 = new BufferedWriter(fileWriter10);
			BufferedWriter bufferedWriter11 = new BufferedWriter(fileWriter11);
			BufferedWriter bufferedWriter12 = new BufferedWriter(fileWriter12);
			BufferedWriter bufferedWriter13 = new BufferedWriter(fileWriter13);
			BufferedWriter bufferedWriter14 = new BufferedWriter(fileWriter14);
			BufferedWriter bufferedWriter15 = new BufferedWriter(fileWriter15);
			BufferedWriter bufferedWriter16 = new BufferedWriter(fileWriter16);
			BufferedWriter bufferedWriter17 = new BufferedWriter(fileWriter17);
			BufferedWriter bufferedWriter18 = new BufferedWriter(fileWriter18);
			BufferedWriter bufferedWriter19 = new BufferedWriter(fileWriter19);
			BufferedWriter bufferedWriter20 = new BufferedWriter(fileWriter20);
			BufferedWriter bufferedWriter21 = new BufferedWriter(fileWriter21);
			BufferedWriter bufferedWriter22 = new BufferedWriter(fileWriter22);
			BufferedWriter bufferedWriter23 = new BufferedWriter(fileWriter23);
			BufferedWriter bufferedWriter24 = new BufferedWriter(fileWriter24);
			
			bufferedWriterList.add(bufferedWriter1);
			bufferedWriterList.add(bufferedWriter2);
			bufferedWriterList.add(bufferedWriter3);
			bufferedWriterList.add(bufferedWriter4);
			bufferedWriterList.add(bufferedWriter5);
			bufferedWriterList.add(bufferedWriter6);
			bufferedWriterList.add(bufferedWriter7);
			bufferedWriterList.add(bufferedWriter8);
			bufferedWriterList.add(bufferedWriter9);
			bufferedWriterList.add(bufferedWriter10);
			bufferedWriterList.add(bufferedWriter11);
			bufferedWriterList.add(bufferedWriter12);
			bufferedWriterList.add(bufferedWriter13);
			bufferedWriterList.add(bufferedWriter14);
			bufferedWriterList.add(bufferedWriter15);
			bufferedWriterList.add(bufferedWriter16);
			bufferedWriterList.add(bufferedWriter17);
			bufferedWriterList.add(bufferedWriter18);
			bufferedWriterList.add(bufferedWriter19);
			bufferedWriterList.add(bufferedWriter20);
			bufferedWriterList.add(bufferedWriter21);
			bufferedWriterList.add(bufferedWriter22);
			bufferedWriterList.add(bufferedWriter23);
			bufferedWriterList.add(bufferedWriter24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//@todo
	public static void createChromBasedUcscRefSeqGeneBufferedWriters(String outputFolder,List<BufferedWriter> bufferedWriterList){

		try {
			FileWriter fileWriter1 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR1_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter2 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR2_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter3 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR3_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter4 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR4_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter5 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR5_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter6 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR6_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter7 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR7_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter8 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR8_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter9 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR9_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter10 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR10_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter11 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR11_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter12 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR12_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter13 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR13_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter14 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR14_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter15 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR15_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter16 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR16_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter17 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR17_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter18 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR18_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter19 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR19_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter20 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR20_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter21 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR21_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter22 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR22_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter23 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHRX_REFSEQ_GENES_WITH_NUMBERS);
			FileWriter fileWriter24 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHRY_REFSEQ_GENES_WITH_NUMBERS);
			
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
			BufferedWriter bufferedWriter4 = new BufferedWriter(fileWriter4);
			BufferedWriter bufferedWriter5 = new BufferedWriter(fileWriter5);
			BufferedWriter bufferedWriter6 = new BufferedWriter(fileWriter6);
			BufferedWriter bufferedWriter7 = new BufferedWriter(fileWriter7);
			BufferedWriter bufferedWriter8 = new BufferedWriter(fileWriter8);
			BufferedWriter bufferedWriter9 = new BufferedWriter(fileWriter9);
			BufferedWriter bufferedWriter10 = new BufferedWriter(fileWriter10);
			BufferedWriter bufferedWriter11 = new BufferedWriter(fileWriter11);
			BufferedWriter bufferedWriter12 = new BufferedWriter(fileWriter12);
			BufferedWriter bufferedWriter13 = new BufferedWriter(fileWriter13);
			BufferedWriter bufferedWriter14 = new BufferedWriter(fileWriter14);
			BufferedWriter bufferedWriter15 = new BufferedWriter(fileWriter15);
			BufferedWriter bufferedWriter16 = new BufferedWriter(fileWriter16);
			BufferedWriter bufferedWriter17 = new BufferedWriter(fileWriter17);
			BufferedWriter bufferedWriter18 = new BufferedWriter(fileWriter18);
			BufferedWriter bufferedWriter19 = new BufferedWriter(fileWriter19);
			BufferedWriter bufferedWriter20 = new BufferedWriter(fileWriter20);
			BufferedWriter bufferedWriter21 = new BufferedWriter(fileWriter21);
			BufferedWriter bufferedWriter22 = new BufferedWriter(fileWriter22);
			BufferedWriter bufferedWriter23 = new BufferedWriter(fileWriter23);
			BufferedWriter bufferedWriter24 = new BufferedWriter(fileWriter24);
			
			bufferedWriterList.add(bufferedWriter1);
			bufferedWriterList.add(bufferedWriter2);
			bufferedWriterList.add(bufferedWriter3);
			bufferedWriterList.add(bufferedWriter4);
			bufferedWriterList.add(bufferedWriter5);
			bufferedWriterList.add(bufferedWriter6);
			bufferedWriterList.add(bufferedWriter7);
			bufferedWriterList.add(bufferedWriter8);
			bufferedWriterList.add(bufferedWriter9);
			bufferedWriterList.add(bufferedWriter10);
			bufferedWriterList.add(bufferedWriter11);
			bufferedWriterList.add(bufferedWriter12);
			bufferedWriterList.add(bufferedWriter13);
			bufferedWriterList.add(bufferedWriter14);
			bufferedWriterList.add(bufferedWriter15);
			bufferedWriterList.add(bufferedWriter16);
			bufferedWriterList.add(bufferedWriter17);
			bufferedWriterList.add(bufferedWriter18);
			bufferedWriterList.add(bufferedWriter19);
			bufferedWriterList.add(bufferedWriter20);
			bufferedWriterList.add(bufferedWriter21);
			bufferedWriterList.add(bufferedWriter22);
			bufferedWriterList.add(bufferedWriter23);
			bufferedWriterList.add(bufferedWriter24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//@todo
	
	//@todo
	public static void createChromBasedUcscRefSeqGeneBufferedReaders(String folderName,List<BufferedReader> bufferedReaderList ){
		try {
			FileReader fileReader1 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR1_REFSEQ_GENES);
			FileReader fileReader2 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR2_REFSEQ_GENES);
			FileReader fileReader3 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR3_REFSEQ_GENES);
			FileReader fileReader4 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR4_REFSEQ_GENES);
			FileReader fileReader5 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR5_REFSEQ_GENES);
			FileReader fileReader6 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR6_REFSEQ_GENES);
			FileReader fileReader7 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR7_REFSEQ_GENES);
			FileReader fileReader8 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR8_REFSEQ_GENES);
			FileReader fileReader9 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR9_REFSEQ_GENES);
			FileReader fileReader10 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR10_REFSEQ_GENES);
			FileReader fileReader11 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR11_REFSEQ_GENES);
			FileReader fileReader12 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR12_REFSEQ_GENES);
			FileReader fileReader13 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR13_REFSEQ_GENES);
			FileReader fileReader14 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR14_REFSEQ_GENES);
			FileReader fileReader15 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR15_REFSEQ_GENES);
			FileReader fileReader16 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR16_REFSEQ_GENES);
			FileReader fileReader17 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR17_REFSEQ_GENES);
			FileReader fileReader18 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR18_REFSEQ_GENES);
			FileReader fileReader19 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR19_REFSEQ_GENES);
			FileReader fileReader20 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR20_REFSEQ_GENES);
			FileReader fileReader21 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR21_REFSEQ_GENES);
			FileReader fileReader22 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHR22_REFSEQ_GENES);
			FileReader fileReader23 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHRX_REFSEQ_GENES);
			FileReader fileReader24 = FileOperations.createFileReader(folderName + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME,Commons.UNSORTED_CHRY_REFSEQ_GENES);
			
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
			BufferedReader bufferedReader4 = new BufferedReader(fileReader4);
			BufferedReader bufferedReader5 = new BufferedReader(fileReader5);
			BufferedReader bufferedReader6 = new BufferedReader(fileReader6);
			BufferedReader bufferedReader7 = new BufferedReader(fileReader7);
			BufferedReader bufferedReader8 = new BufferedReader(fileReader8);
			BufferedReader bufferedReader9 = new BufferedReader(fileReader9);
			BufferedReader bufferedReader10 = new BufferedReader(fileReader10);
			BufferedReader bufferedReader11 = new BufferedReader(fileReader11);
			BufferedReader bufferedReader12 = new BufferedReader(fileReader12);
			BufferedReader bufferedReader13 = new BufferedReader(fileReader13);
			BufferedReader bufferedReader14 = new BufferedReader(fileReader14);
			BufferedReader bufferedReader15 = new BufferedReader(fileReader15);
			BufferedReader bufferedReader16 = new BufferedReader(fileReader16);
			BufferedReader bufferedReader17 = new BufferedReader(fileReader17);
			BufferedReader bufferedReader18 = new BufferedReader(fileReader18);
			BufferedReader bufferedReader19 = new BufferedReader(fileReader19);
			BufferedReader bufferedReader20 = new BufferedReader(fileReader20);
			BufferedReader bufferedReader21 = new BufferedReader(fileReader21);
			BufferedReader bufferedReader22 = new BufferedReader(fileReader22);
			BufferedReader bufferedReader23 = new BufferedReader(fileReader23);
			BufferedReader bufferedReader24 = new BufferedReader(fileReader24);
			
			bufferedReaderList.add(bufferedReader1);
			bufferedReaderList.add(bufferedReader2);
			bufferedReaderList.add(bufferedReader3);
			bufferedReaderList.add(bufferedReader4);
			bufferedReaderList.add(bufferedReader5);
			bufferedReaderList.add(bufferedReader6);
			bufferedReaderList.add(bufferedReader7);
			bufferedReaderList.add(bufferedReader8);
			bufferedReaderList.add(bufferedReader9);
			bufferedReaderList.add(bufferedReader10);
			bufferedReaderList.add(bufferedReader11);
			bufferedReaderList.add(bufferedReader12);
			bufferedReaderList.add(bufferedReader13);
			bufferedReaderList.add(bufferedReader14);
			bufferedReaderList.add(bufferedReader15);
			bufferedReaderList.add(bufferedReader16);
			bufferedReaderList.add(bufferedReader17);
			bufferedReaderList.add(bufferedReader18);
			bufferedReaderList.add(bufferedReader19);
			bufferedReaderList.add(bufferedReader20);
			bufferedReaderList.add(bufferedReader21);
			bufferedReaderList.add(bufferedReader22);
			bufferedReaderList.add(bufferedReader23);
			bufferedReaderList.add(bufferedReader24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//@todo
	
	public static void createChromBaseTfbsBufferedReaders(String outputFolder,List<BufferedReader> bufferedReaderList){

		try {
			FileReader fileReader1 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR1_TFBS_FILENAME);
			FileReader fileReader2 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR2_TFBS_FILENAME);
			FileReader fileReader3 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR3_TFBS_FILENAME);
			FileReader fileReader4 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR4_TFBS_FILENAME);
			FileReader fileReader5 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR5_TFBS_FILENAME);
			FileReader fileReader6 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR6_TFBS_FILENAME);
			FileReader fileReader7 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR7_TFBS_FILENAME);
			FileReader fileReader8 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR8_TFBS_FILENAME);
			FileReader fileReader9 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR9_TFBS_FILENAME);
			FileReader fileReader10 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR10_TFBS_FILENAME);;
			FileReader fileReader11 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR11_TFBS_FILENAME);;
			FileReader fileReader12 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR12_TFBS_FILENAME);;
			FileReader fileReader13 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR13_TFBS_FILENAME);;
			FileReader fileReader14 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR14_TFBS_FILENAME);;
			FileReader fileReader15 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR15_TFBS_FILENAME);;
			FileReader fileReader16 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR16_TFBS_FILENAME);;
			FileReader fileReader17 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR17_TFBS_FILENAME);;
			FileReader fileReader18 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR18_TFBS_FILENAME);;
			FileReader fileReader19 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR19_TFBS_FILENAME);;
			FileReader fileReader20 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR20_TFBS_FILENAME);;
			FileReader fileReader21 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR21_TFBS_FILENAME);;
			FileReader fileReader22 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR22_TFBS_FILENAME);;
			FileReader fileReader23 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRX_TFBS_FILENAME);;
			FileReader fileReader24 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRY_TFBS_FILENAME);;
			
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
			BufferedReader bufferedReader4 = new BufferedReader(fileReader4);
			BufferedReader bufferedReader5 = new BufferedReader(fileReader5);
			BufferedReader bufferedReader6 = new BufferedReader(fileReader6);
			BufferedReader bufferedReader7 = new BufferedReader(fileReader7);
			BufferedReader bufferedReader8 = new BufferedReader(fileReader8);
			BufferedReader bufferedReader9 = new BufferedReader(fileReader9);
			BufferedReader bufferedReader10 = new BufferedReader(fileReader10);
			BufferedReader bufferedReader11 = new BufferedReader(fileReader11);
			BufferedReader bufferedReader12 = new BufferedReader(fileReader12);
			BufferedReader bufferedReader13 = new BufferedReader(fileReader13);
			BufferedReader bufferedReader14 = new BufferedReader(fileReader14);
			BufferedReader bufferedReader15 = new BufferedReader(fileReader15);
			BufferedReader bufferedReader16 = new BufferedReader(fileReader16);
			BufferedReader bufferedReader17 = new BufferedReader(fileReader17);
			BufferedReader bufferedReader18 = new BufferedReader(fileReader18);
			BufferedReader bufferedReader19 = new BufferedReader(fileReader19);
			BufferedReader bufferedReader20 = new BufferedReader(fileReader20);
			BufferedReader bufferedReader21 = new BufferedReader(fileReader21);
			BufferedReader bufferedReader22 = new BufferedReader(fileReader22);
			BufferedReader bufferedReader23 = new BufferedReader(fileReader23);
			BufferedReader bufferedReader24 = new BufferedReader(fileReader24);
			
			bufferedReaderList.add(bufferedReader1);
			bufferedReaderList.add(bufferedReader2);
			bufferedReaderList.add(bufferedReader3);
			bufferedReaderList.add(bufferedReader4);
			bufferedReaderList.add(bufferedReader5);
			bufferedReaderList.add(bufferedReader6);
			bufferedReaderList.add(bufferedReader7);
			bufferedReaderList.add(bufferedReader8);
			bufferedReaderList.add(bufferedReader9);
			bufferedReaderList.add(bufferedReader10);
			bufferedReaderList.add(bufferedReader11);
			bufferedReaderList.add(bufferedReader12);
			bufferedReaderList.add(bufferedReader13);
			bufferedReaderList.add(bufferedReader14);
			bufferedReaderList.add(bufferedReader15);
			bufferedReaderList.add(bufferedReader16);
			bufferedReaderList.add(bufferedReader17);
			bufferedReaderList.add(bufferedReader18);
			bufferedReaderList.add(bufferedReader19);
			bufferedReaderList.add(bufferedReader20);
			bufferedReaderList.add(bufferedReader21);
			bufferedReaderList.add(bufferedReader22);
			bufferedReaderList.add(bufferedReader23);
			bufferedReaderList.add(bufferedReader24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//todo
	
	public static void createChromBaseHistoneBufferedWriters(String outputFolder, List<BufferedWriter> bufferedWriterList){

		try {
			FileWriter fileWriter1 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR1_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter2 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR2_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter3 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR3_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter4 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR4_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter5 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR5_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter6 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR6_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter7 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR7_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter8 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR8_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter9 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR9_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter10 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR10_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter11 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR11_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter12 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR12_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter13 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR13_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter14 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR14_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter15 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR15_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter16 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR16_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter17 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR17_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter18 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR18_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter19 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR19_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter20 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR20_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter21 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR21_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter22 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR22_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter23 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHRX_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter24 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHRY_HISTONE_FILENAME_WITH_NUMBERS);
			
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
			BufferedWriter bufferedWriter4 = new BufferedWriter(fileWriter4);
			BufferedWriter bufferedWriter5 = new BufferedWriter(fileWriter5);
			BufferedWriter bufferedWriter6 = new BufferedWriter(fileWriter6);
			BufferedWriter bufferedWriter7 = new BufferedWriter(fileWriter7);
			BufferedWriter bufferedWriter8 = new BufferedWriter(fileWriter8);
			BufferedWriter bufferedWriter9 = new BufferedWriter(fileWriter9);
			BufferedWriter bufferedWriter10 = new BufferedWriter(fileWriter10);
			BufferedWriter bufferedWriter11 = new BufferedWriter(fileWriter11);
			BufferedWriter bufferedWriter12 = new BufferedWriter(fileWriter12);
			BufferedWriter bufferedWriter13 = new BufferedWriter(fileWriter13);
			BufferedWriter bufferedWriter14 = new BufferedWriter(fileWriter14);
			BufferedWriter bufferedWriter15 = new BufferedWriter(fileWriter15);
			BufferedWriter bufferedWriter16 = new BufferedWriter(fileWriter16);
			BufferedWriter bufferedWriter17 = new BufferedWriter(fileWriter17);
			BufferedWriter bufferedWriter18 = new BufferedWriter(fileWriter18);
			BufferedWriter bufferedWriter19 = new BufferedWriter(fileWriter19);
			BufferedWriter bufferedWriter20 = new BufferedWriter(fileWriter20);
			BufferedWriter bufferedWriter21 = new BufferedWriter(fileWriter21);
			BufferedWriter bufferedWriter22 = new BufferedWriter(fileWriter22);
			BufferedWriter bufferedWriter23 = new BufferedWriter(fileWriter23);
			BufferedWriter bufferedWriter24 = new BufferedWriter(fileWriter24);
			
			bufferedWriterList.add(bufferedWriter1);
			bufferedWriterList.add(bufferedWriter2);
			bufferedWriterList.add(bufferedWriter3);
			bufferedWriterList.add(bufferedWriter4);
			bufferedWriterList.add(bufferedWriter5);
			bufferedWriterList.add(bufferedWriter6);
			bufferedWriterList.add(bufferedWriter7);
			bufferedWriterList.add(bufferedWriter8);
			bufferedWriterList.add(bufferedWriter9);
			bufferedWriterList.add(bufferedWriter10);
			bufferedWriterList.add(bufferedWriter11);
			bufferedWriterList.add(bufferedWriter12);
			bufferedWriterList.add(bufferedWriter13);
			bufferedWriterList.add(bufferedWriter14);
			bufferedWriterList.add(bufferedWriter15);
			bufferedWriterList.add(bufferedWriter16);
			bufferedWriterList.add(bufferedWriter17);
			bufferedWriterList.add(bufferedWriter18);
			bufferedWriterList.add(bufferedWriter19);
			bufferedWriterList.add(bufferedWriter20);
			bufferedWriterList.add(bufferedWriter21);
			bufferedWriterList.add(bufferedWriter22);
			bufferedWriterList.add(bufferedWriter23);
			bufferedWriterList.add(bufferedWriter24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//todo
	
	public static void createChromBaseHistoneBufferedReaders(String outputFolder, List<BufferedReader> bufferedReaderList){

		try {
			FileReader fileReader1 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR1_HISTONE_FILENAME);
			FileReader fileReader2 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR2_HISTONE_FILENAME);
			FileReader fileReader3 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR3_HISTONE_FILENAME);
			FileReader fileReader4 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR4_HISTONE_FILENAME);
			FileReader fileReader5 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR5_HISTONE_FILENAME);
			FileReader fileReader6 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR6_HISTONE_FILENAME);
			FileReader fileReader7 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR7_HISTONE_FILENAME);
			FileReader fileReader8 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR8_HISTONE_FILENAME);
			FileReader fileReader9 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR9_HISTONE_FILENAME);
			FileReader fileReader10 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR10_HISTONE_FILENAME);
			FileReader fileReader11 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR11_HISTONE_FILENAME);
			FileReader fileReader12 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR12_HISTONE_FILENAME);
			FileReader fileReader13 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR13_HISTONE_FILENAME);
			FileReader fileReader14 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR14_HISTONE_FILENAME);
			FileReader fileReader15 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR15_HISTONE_FILENAME);
			FileReader fileReader16 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR16_HISTONE_FILENAME);
			FileReader fileReader17 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR17_HISTONE_FILENAME);
			FileReader fileReader18 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR18_HISTONE_FILENAME);
			FileReader fileReader19 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR19_HISTONE_FILENAME);
			FileReader fileReader20 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR20_HISTONE_FILENAME);
			FileReader fileReader21 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR21_HISTONE_FILENAME);
			FileReader fileReader22 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR22_HISTONE_FILENAME);
			FileReader fileReader23 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHRX_HISTONE_FILENAME);
			FileReader fileReader24 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHRY_HISTONE_FILENAME);
			
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
			BufferedReader bufferedReader4 = new BufferedReader(fileReader4);
			BufferedReader bufferedReader5 = new BufferedReader(fileReader5);
			BufferedReader bufferedReader6 = new BufferedReader(fileReader6);
			BufferedReader bufferedReader7 = new BufferedReader(fileReader7);
			BufferedReader bufferedReader8 = new BufferedReader(fileReader8);
			BufferedReader bufferedReader9 = new BufferedReader(fileReader9);
			BufferedReader bufferedReader10 = new BufferedReader(fileReader10);
			BufferedReader bufferedReader11 = new BufferedReader(fileReader11);
			BufferedReader bufferedReader12 = new BufferedReader(fileReader12);
			BufferedReader bufferedReader13 = new BufferedReader(fileReader13);
			BufferedReader bufferedReader14 = new BufferedReader(fileReader14);
			BufferedReader bufferedReader15 = new BufferedReader(fileReader15);
			BufferedReader bufferedReader16 = new BufferedReader(fileReader16);
			BufferedReader bufferedReader17 = new BufferedReader(fileReader17);
			BufferedReader bufferedReader18 = new BufferedReader(fileReader18);
			BufferedReader bufferedReader19 = new BufferedReader(fileReader19);
			BufferedReader bufferedReader20 = new BufferedReader(fileReader20);
			BufferedReader bufferedReader21 = new BufferedReader(fileReader21);
			BufferedReader bufferedReader22 = new BufferedReader(fileReader22);
			BufferedReader bufferedReader23 = new BufferedReader(fileReader23);
			BufferedReader bufferedReader24 = new BufferedReader(fileReader24);
			
			bufferedReaderList.add(bufferedReader1);
			bufferedReaderList.add(bufferedReader2);
			bufferedReaderList.add(bufferedReader3);
			bufferedReaderList.add(bufferedReader4);
			bufferedReaderList.add(bufferedReader5);
			bufferedReaderList.add(bufferedReader6);
			bufferedReaderList.add(bufferedReader7);
			bufferedReaderList.add(bufferedReader8);
			bufferedReaderList.add(bufferedReader9);
			bufferedReaderList.add(bufferedReader10);
			bufferedReaderList.add(bufferedReader11);
			bufferedReaderList.add(bufferedReader12);
			bufferedReaderList.add(bufferedReader13);
			bufferedReaderList.add(bufferedReader14);
			bufferedReaderList.add(bufferedReader15);
			bufferedReaderList.add(bufferedReader16);
			bufferedReaderList.add(bufferedReader17);
			bufferedReaderList.add(bufferedReader18);
			bufferedReaderList.add(bufferedReader19);
			bufferedReaderList.add(bufferedReader20);
			bufferedReaderList.add(bufferedReader21);
			bufferedReaderList.add(bufferedReader22);
			bufferedReaderList.add(bufferedReader23);
			bufferedReaderList.add(bufferedReader24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public static void readCellLineNames(String dataFolder,Number cellLineNameNumber,List<String> cellLineNames,Map<String,Integer> cellLineName2CellLineNumberMap,Map<Integer,String> cellLineNumber2CellLineNameMap,Number fileNameNumber,List<String> fileNames,Map<String,Integer> fileName2FileNumberMap, Map<Integer,String> fileNumber2FileNameMap){
		
		List<BufferedReader> bufferedReaderList = new ArrayList<BufferedReader>();
		List<BufferedWriter> bufferedWriterList = new ArrayList<BufferedWriter>();
		
		createChromBaseDnaseBufferedReaders(dataFolder,bufferedReaderList);
		//unsorted dnase with numbers
		createChromBaseDnaseBufferedWriters(dataFolder,bufferedWriterList);
		
		String strLine;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		int indexofFirstTab 	= 0;
		int indexofSecondTab 	= 0;
		int indexofThirdTab 	= 0;
		int indexofFourthTab	= 0;
		
		String chrNameLowHigh;
		String cellLineName;
		String fileName;
			
		try {
			for(int i = 0; i< bufferedReaderList.size() ; i++){
				 bufferedReader = bufferedReaderList.get(i);
				 bufferedWriter = bufferedWriterList.get(i);
				 				
					while((strLine = bufferedReader.readLine())!=null){
//						example unsorted dnase line
//						chrY	10036738	10039094	H1_ES	idrPool.H1_ES_DNaseHS_BP_TP_P_peaks_OV_DukeDNase_H1_ES_B1_peaks_VS_DukeDNase_H1_ES_B2_peaks.npk2.narrowPeak

						indexofFirstTab = strLine.indexOf('\t');
						indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
						
						chrNameLowHigh =  strLine.substring(0, indexofThirdTab);
						cellLineName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						fileName = strLine.substring(indexofFourthTab+1);
											
						if(!(cellLineNames.contains(cellLineName))){
							cellLineNames.add(cellLineName);
							
							cellLineName2CellLineNumberMap.put(cellLineName, cellLineNameNumber.getNumber());
							cellLineNumber2CellLineNameMap.put(cellLineNameNumber.getNumber(), cellLineName);
							
							cellLineNameNumber.setNumber(cellLineNameNumber.getNumber()+1);
						}
						
						
						if (!(fileNames.contains(fileName))){							
							fileNames.add(fileName);
							fileName2FileNumberMap.put(fileName, fileNameNumber.getNumber());
							fileNumber2FileNameMap.put(fileNameNumber.getNumber(), fileName);
							
							fileNameNumber.setNumber(fileNameNumber.getNumber()+1);;
						}
						
						//write unsorted dnase files with numbers
						bufferedWriter.write(chrNameLowHigh + "\t" + cellLineName2CellLineNumberMap.get(cellLineName) + "\t" + fileName2FileNumberMap.get(fileName) + System.getProperty("line.separator"));						
						
					 }// End of While			
			}// End of For						
			
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}	
		
		closeBufferedReaders(bufferedReaderList);
		closeBufferedWriters(bufferedWriterList);
	}
	
	
	public static void readTforHistoneNames(String dataFolder,List<String> tfbsorHistoneNames,Map<String,Integer> elementName2ElementNumberMap,Map<Integer,String> elementNumber2ElementNameMap,String tfbsorHistone,Number cellLineNameNumber,List<String> cellLineNames,Map<String,Integer> cellLineName2CellLineNumberMap, Map<Integer,String> cellLineNumber2CellLineNameMap,Number fileNameNumber, List<String> fileNames,Map<String,Integer> fileName2FileNumberMap, Map<Integer,String> fileNumber2FileNameMap){
		List<BufferedReader> bufferedReaderList = new ArrayList<BufferedReader>();
		List<BufferedWriter> bufferedWriterList = new ArrayList<BufferedWriter>();
		
		
		if (tfbsorHistone.equals(Commons.TF)){
			createChromBaseTfbsBufferedReaders(dataFolder,bufferedReaderList);
			createChromBaseTfbsBufferedWriters(dataFolder,bufferedWriterList);
						
		}else if (tfbsorHistone.equals(Commons.HISTONE)) {
			createChromBaseHistoneBufferedReaders(dataFolder,bufferedReaderList);	
			createChromBaseHistoneBufferedWriters(dataFolder,bufferedWriterList);				
			
		}
		
		
		String strLine;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		int indexofFirstTab 	= 0;
		int indexofSecondTab 	= 0;
		int indexofThirdTab 	= 0;
		int indexofFourthTab	= 0;
		int indexofFifthTab	= 0;
			
		String chrNameLowHigh;
		String tfbsorHistoneName;
		String cellLineName;
		String fileName;
		int elementNumber = 1;
		
		
		try {
			for(int i = 0; i< bufferedReaderList.size() ; i++){
				 bufferedReader = bufferedReaderList.get(i);
				 bufferedWriter = bufferedWriterList.get(i);
				 				
					while((strLine = bufferedReader.readLine())!=null){
//						example unsorted tfbs line
//						chrY	2804079	2804213	Ctcf	H1hesc	spp.optimal.wgEncodeBroadHistoneH1hescCtcfStdAlnRep0_VS_wgEncodeBroadHistoneH1hescControlStdAlnRep0.narrowPeak
						
//						example unsorted histone line
//						chrY	15589743	15592520	H3k27ac	H1hesc	wgEncodeBroadHistoneH1hescH3k27acStdAln.narrowPeak

						indexofFirstTab = strLine.indexOf('\t');
						indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
						indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
						
						chrNameLowHigh = strLine.substring(0, indexofThirdTab);
						tfbsorHistoneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						cellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
						fileName = strLine.substring(indexofFifthTab+1);
						
						if(!(tfbsorHistoneNames.contains(tfbsorHistoneName))){
							tfbsorHistoneNames.add(tfbsorHistoneName);
							
							elementName2ElementNumberMap.put(tfbsorHistoneName, elementNumber);
							elementNumber2ElementNameMap.put(elementNumber, tfbsorHistoneName);
							
							elementNumber++;
			
						}
						
						if (!(cellLineNames.contains(cellLineName))){
							
							cellLineNames.add(cellLineName);
							cellLineName2CellLineNumberMap.put(cellLineName, cellLineNameNumber.getNumber());
							cellLineNumber2CellLineNameMap.put(cellLineNameNumber.getNumber(), cellLineName);
							
							cellLineNameNumber.setNumber(cellLineNameNumber.getNumber()+1);;
						}
						
						if (!(fileNames.contains(fileName))){
							
							fileNames.add(fileName);
							fileName2FileNumberMap.put(fileName, fileNameNumber.getNumber());
							fileNumber2FileNameMap.put(fileNameNumber.getNumber(), fileName);
							
							fileNameNumber.setNumber(fileNameNumber.getNumber()+1);;
						}
						
						//Write unsorted tf or histone files with numbers
						bufferedWriter.write(chrNameLowHigh + "\t" + elementName2ElementNumberMap.get(tfbsorHistoneName) + "\t" + cellLineName2CellLineNumberMap.get(cellLineName) + "\t" + fileName2FileNumberMap.get(fileName) + System.getProperty("line.separator"));
					 }// End of While			
			}// End of For
									
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		closeBufferedReaders(bufferedReaderList);		
		closeBufferedWriters(bufferedWriterList);
		
	}


	//@todo
	public static void readUnsortedUcscRefSeqGeneFilesandWriteUnsortedUcscRefSeqGeneFilesWithNumbers(String dataFolder,List<Integer> geneEntrezIds,List<String> refseqGeneNames, Map<String,Integer> refseqGeneName2RefseqGeneNameNumberMap,Map<Integer,String> refseqGeneNameNumber2RefseqGeneNameMap,List<String> geneHugoSymbols,Map<String,Integer> geneHugoSymbol2GeneHugoSymbolNumberMap,Map<Integer,String> geneHugoSymbolNumber2GeneHugoSymbolMap)
	{
		
		List<BufferedReader> bufferedReaderList = new ArrayList<BufferedReader>();
		List<BufferedWriter> bufferedWriterList = new ArrayList<BufferedWriter>();
		
		
		createChromBasedUcscRefSeqGeneBufferedReaders(dataFolder,bufferedReaderList);
		createChromBasedUcscRefSeqGeneBufferedWriters(dataFolder,bufferedWriterList);
		
		
		String strLine;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		int indexofFirstTab 	= 0;
		int indexofSecondTab 	= 0;
		int indexofThirdTab 	= 0;
		int indexofFourthTab	= 0;
		int indexofFifthTab		= 0;
		int indexofSixthTab		= 0;
		int indexofSeventhTab	= 0;
		int indexofEighthTab	= 0;
		
		String chrNameLowHigh;
		String refseqGeneName;
		int entrezGeneId;
		IntervalName intervalName;
		int intervalNumber;
		
		char strand;
		String geneHugoSymbol;
		
		int refseqGeneNameNumber = 1;
		int geneHugoSymbolNumber = 1;
		
				
		try {
			for(int i = 0; i< bufferedReaderList.size() ; i++){
				 bufferedReader = bufferedReaderList.get(i);
				 bufferedWriter = bufferedWriterList.get(i);
				 				
					while((strLine = bufferedReader.readLine())!=null){
//						example unsorted ucsc refseq gene line
//						chrY	16636453	16636815	NR_028319	22829	EXON	1	+	NLGN4Y

						indexofFirstTab 	= strLine.indexOf('\t');
						indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab 	= strLine.indexOf('\t',indexofThirdTab+1);
						indexofFifthTab 	= strLine.indexOf('\t',indexofFourthTab+1);
						indexofSixthTab 	= strLine.indexOf('\t',indexofFifthTab+1);
						indexofSeventhTab 	= strLine.indexOf('\t',indexofSixthTab+1);
						indexofEighthTab	= strLine.indexOf('\t',indexofSeventhTab+1);
						
						chrNameLowHigh 	= strLine.substring(0, indexofThirdTab);
						refseqGeneName 	= strLine.substring(indexofThirdTab+1, indexofFourthTab);
						entrezGeneId 	= Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab));
						intervalName 	= IntervalName.convertStringtoEnum(strLine.substring(indexofFifthTab+1,indexofSixthTab));
						intervalNumber 	= Integer.parseInt(strLine.substring(indexofSixthTab+1,indexofSeventhTab));
						strand 			= (strLine.substring(indexofSeventhTab+1,indexofEighthTab)).charAt(0);
						geneHugoSymbol 	= strLine.substring(indexofEighthTab+1);
						
						if(!(geneEntrezIds.contains(entrezGeneId))){
							geneEntrezIds.add(entrezGeneId);							
						}
						
						if (!(refseqGeneNames.contains(refseqGeneName))){
							
							refseqGeneNames.add(refseqGeneName);
							refseqGeneName2RefseqGeneNameNumberMap.put(refseqGeneName, refseqGeneNameNumber);
							refseqGeneNameNumber2RefseqGeneNameMap.put(refseqGeneNameNumber, refseqGeneName);
							
							refseqGeneNameNumber++;
							
						}
						
						if (!(geneHugoSymbols.contains(geneHugoSymbol))){
							
							geneHugoSymbols.add(geneHugoSymbol);
							geneHugoSymbol2GeneHugoSymbolNumberMap.put(geneHugoSymbol,geneHugoSymbolNumber);
							geneHugoSymbolNumber2GeneHugoSymbolMap.put(geneHugoSymbolNumber, geneHugoSymbol);
							
							geneHugoSymbolNumber++;
						}
						
						
//						example input strLine
//						chrY	16733888	16734470	NR_028319	22829	EXON	2	+	NLGN4Y
						
					

						//Write unsorted ucsc refseq gene files with numbers
						bufferedWriter.write(chrNameLowHigh + "\t" + refseqGeneName2RefseqGeneNameNumberMap.get(refseqGeneName) + "\t" + entrezGeneId + "\t" + intervalName.getIntervalNameString() + "\t" + intervalNumber +"\t" + strand + "\t" + geneHugoSymbol2GeneHugoSymbolNumberMap.get(geneHugoSymbol) + System.getProperty("line.separator"));

						
				}// End of While			
			}// End of For
									
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		closeBufferedReaders(bufferedReaderList);		
		closeBufferedWriters(bufferedWriterList);
	}

	//@todo

	public static void readGeneIds(String dataFolder, List<String> geneIds, String inputFileName){
		
		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		
		String geneId;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
//			example line 
//9606	1	REVIEWED	NM_130786.3	161377438	NP_570602.2	21071030	AC_000151.1	157718668	55167315	55174019	-	Alternate HuRef
				

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				geneId = strLine.substring(indexofFirstTab+1,indexofSecondTab);				
				
				if(!(geneIds.contains(geneId))){					
					geneIds.add(geneId);								
				}													
			} // End of While
						
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void readRNAAccessionVersions(String dataFolder, List<String> rnaNucleotideAccessionVersions,String inputFileName){
		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofDot = 0;
		
		String rnaNucleotideAccessionVersion;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
//				example line 
//9606	1	REVIEWED	NM_130786.3	161377438	NP_570602.2	21071030	AC_000151.1	157718668	55167315	55174019	-	Alternate HuRef

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
				
				rnaNucleotideAccessionVersion = strLine.substring(indexofThirdTab+1,indexofFourthTab);				
				indexofDot =rnaNucleotideAccessionVersion.indexOf('.');
				
				if(indexofDot>=0){
					rnaNucleotideAccessionVersion = rnaNucleotideAccessionVersion.substring(0, indexofDot);
				}
				
				
				if(!(rnaNucleotideAccessionVersions.contains(rnaNucleotideAccessionVersion))){					
					rnaNucleotideAccessionVersions.add(rnaNucleotideAccessionVersion);								
				}													
			} // End of While
			
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void readUcscRefSeqGeneName2s(String dataFolder,List<String>  ucscRefSeqGeneName2s,String inputFileName){
		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		int indexofSixthTab = 0;
		int indexofSeventhTab = 0;
		int indexofEighthTab = 0;
		int indexofNinethTab = 0;
		int indexofTenthTab = 0;
		int indexofEleventhTab = 0;
		int indexofTwelfthTab = 0;
		int indexofThirteenthTab = 0;
		
		String ucscRefSeqGeneName2;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
//			consume first line
			strLine = bufferedReader.readLine();
			
			while((strLine = bufferedReader.readLine())!=null){
//				Example line
//				#bin	name	chrom	strand	txStart	txEnd	cdsStart	cdsEnd	exonCount	exonStarts	exonEnds	score	name2	cdsStartStat	cdsEndStat	exonFrames
//				1	NM_032785	chr1	-	48998526	50489626	48999844	50489468	14	48998526,49000561,49005313,49052675,49056504,49100164,49119008,49128823,49332862,49511255,49711441,50162984,50317067,50489434,	48999965,49000588,49005410,49052838,49056657,49100276,49119123,49128913,49332902,49511472,49711536,50163109,50317190,50489626,	0	AGBL4	cmpl	cmpl	2,2,1,0,0,2,1,1,0,2,0,1,1,0,				

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
				indexofSixthTab = strLine.indexOf('\t',indexofFifthTab+1);
				indexofSeventhTab = strLine.indexOf('\t',indexofSixthTab+1);
				indexofEighthTab = strLine.indexOf('\t',indexofSeventhTab+1);
				indexofNinethTab = strLine.indexOf('\t',indexofEighthTab+1);
				indexofTenthTab = strLine.indexOf('\t',indexofNinethTab+1);
				indexofEleventhTab = strLine.indexOf('\t',indexofTenthTab+1);
				indexofTwelfthTab = strLine.indexOf('\t',indexofEleventhTab+1);
				indexofThirteenthTab = strLine.indexOf('\t',indexofTwelfthTab+1);
				
				
				ucscRefSeqGeneName2 = strLine.substring(indexofTwelfthTab+1,indexofThirteenthTab);				
				
				if(!(ucscRefSeqGeneName2s.contains(ucscRefSeqGeneName2))){					
					ucscRefSeqGeneName2s.add(ucscRefSeqGeneName2);								
				}													
			} // End of While
			
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	

	
	
	public static void readKeggPathwayNames(String dataFolder,List<String> keggPathwayNameList, Map<String,Integer> keggPathwayName2KeggPathwayNumberMap,Map<Integer,String> keggPathwayNumber2KeggPathwayNameMap, String inputFileName){

		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofTab 	= 0;
		int indexofColon = 0;
		
		String keggPathwayName;
		int keggPathwayNumber = 1;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
//				example line
//				path:hsa00010	hsa:10327	reverse

				indexofTab = strLine.indexOf('\t');				
				keggPathwayName = strLine.substring(0,indexofTab);
				
				indexofColon = keggPathwayName.indexOf(':');				
				keggPathwayName = keggPathwayName.substring(indexofColon+1);				
				
				if(!(keggPathwayNameList.contains(keggPathwayName))){					
					keggPathwayNameList.add(keggPathwayName);	
					
					keggPathwayName2KeggPathwayNumberMap.put(keggPathwayName, keggPathwayNumber);
					keggPathwayNumber2KeggPathwayNameMap.put(keggPathwayNumber, keggPathwayName);
					keggPathwayNumber++;
										
				}													
			} // End of While			
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
							
	}
	
	//todo starts
	public static void readNames(String dataFolder,List<String> nameList, String inputDirectoryName, String inputFileName){
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;	
		
		String strLine;
		
		try {
			
			fileReader = FileOperations.createFileReader(dataFolder + inputDirectoryName,inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine= bufferedReader.readLine())!=null){
				if (!nameList.contains(strLine)){
					nameList.add(strLine);
				}
			}//End of While
		
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}
	//todo ends
	
	public static void writeNames(String dataFolder,TObjectShortMap<String> name2NumberMap, String outputDirectoryName, String outputFileName){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
		
			for ( TObjectShortIterator<String> it = name2NumberMap.iterator(); it.hasNext(); ) {
				   it.advance();
				   bufferedWriter.write(it.key()+ System.getProperty("line.separator"));		    
				}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}
	

	
	public static void writeNames(String dataFolder,List<String> nameList, String outputDirectoryName, String outputFileName){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		
		try {
			
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
		
			for(int i = 0; i<nameList.size() ;i++){
								
				bufferedWriter.write(nameList.get(i)+ System.getProperty("line.separator"));
				bufferedWriter.flush();				
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}
	
	
	public static void writeNumbers(String dataFolder,List<Integer> numberList, String outputDirectoryName, String outputFileName){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		
		try {
			
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
		
			for(int i = 0; i<numberList.size() ;i++){
								
				bufferedWriter.write(numberList.get(i)+ System.getProperty("line.separator"));
				bufferedWriter.flush();				
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	
	}
	
	//Write String2ShortMap starts
	public static void writeMapsString2Short(String dataFolder,TObjectShortMap<String> name2NumberMap, String outputDirectoryName, String outputFileName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			
			for ( TObjectShortIterator<String> it = name2NumberMap.iterator(); it.hasNext(); ) {
			   it.advance();
			   bufferedWriter.write(it.key() + "\t" + it.value() + System.getProperty("line.separator"));		    
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}	
	//Write String2ShortMap ends
	
	
	//Write Short2StringMap starts
	public static void writeMapsShort2String(String dataFolder,TShortObjectMap<String> number2NameMap, String outputDirectoryName, String outputFileName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for ( TShortObjectIterator<String> it = number2NameMap.iterator(); it.hasNext(); ) {
			   it.advance();
			   bufferedWriter.write(it.key() + "\t" + it.value() + System.getProperty("line.separator"));		    
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}	
	//Write Short2StringMap ends
	
	public static void writeMapsString2Integer(String dataFolder,Map<String,Integer> cellLineName2CellLineNumberMap, String outputDirectoryName, String outputFileName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Map.Entry<String,Integer> entry :cellLineName2CellLineNumberMap.entrySet()){
				bufferedWriter.write(entry.getKey() + "\t" + entry.getValue() + System.getProperty("line.separator"));
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}
	
	
	public static void writeMapsInteger2String(String dataFolder,Map<Integer,String> cellLineName2CellLineNumberMap, String outputDirectoryName, String outputFileName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Map.Entry<Integer,String> entry :cellLineName2CellLineNumberMap.entrySet()){
				bufferedWriter.write(entry.getKey() + "\t" + entry.getValue() + System.getProperty("line.separator"));
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
	}
	
	
	public static void writeAllPossibleEncodeCellLineNames(String dataFolder,Number cellLineNameNumber, List<String> cellLineNames,Map<String,Integer> cellLineName2CellLineNumberMap, Map<Integer,String> cellLineNumber2CellLineNameMap,Number fileNameNumber, List<String> fileNames,Map<String,Integer> fileName2FileNumberMap, Map<Integer,String> fileNumber2FileNameMap){
		
		readCellLineNames(dataFolder,cellLineNameNumber,cellLineNames,cellLineName2CellLineNumberMap,cellLineNumber2CellLineNameMap,fileNameNumber,fileNames,fileName2FileNumberMap,fileNumber2FileNameMap);
				
	}
	
	
	public static void writeAllPossibleEncodeTfNames(String dataFolder,Number cellLineNameNumber, List<String> cellLineNames, Map<String,Integer> cellLineName2CellLineNumberMap,  Map<Integer,String>  cellLineNumber2CellLineNameMap,Number fileNameNumber, List<String> fileNames,Map<String,Integer> fileName2FileNumberMap, Map<Integer,String> fileNumber2FileNameMap){
		
		List<String> tfNames = new ArrayList<String>();
		Map<String,Integer> tfName2TfNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> tfNumber2TfNameMap = new HashMap<Integer,String>();

		readTforHistoneNames(dataFolder,tfNames, tfName2TfNumberMap, tfNumber2TfNameMap,Commons.TF,cellLineNameNumber,cellLineNames,cellLineName2CellLineNumberMap,cellLineNumber2CellLineNameMap,fileNameNumber, fileNames,fileName2FileNumberMap, fileNumber2FileNameMap);
		writeNames(dataFolder,tfNames,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_ENCODE_TF_NAMES_OUTPUT_FILENAME);		
		writeMapsString2Integer(dataFolder,tfName2TfNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_TFNAME_2_TFNUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,tfNumber2TfNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_TFNUMBER_2_TFNAME_OUTPUT_FILENAME);
	
	}

	public static void writeAllPossibleEncodeHistoneNames(String dataFolder,Number cellLineNameNumber, List<String> cellLineNames, Map<String,Integer> cellLineName2CellLineNumberMap,  Map<Integer,String>  cellLineNumber2CellLineNameMap,Number fileNameNumber, List<String> fileNames,Map<String,Integer> fileName2FileNumberMap, Map<Integer,String> fileNumber2FileNameMap){
		
		List<String> histoneNames = new ArrayList<String>();
		Map<String,Integer> histoneName2HistoneNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> histoneNumber2HistoneNameMap = new HashMap<Integer,String>();

		readTforHistoneNames(dataFolder,histoneNames,histoneName2HistoneNumberMap,histoneNumber2HistoneNameMap,Commons.HISTONE, cellLineNameNumber, cellLineNames, cellLineName2CellLineNumberMap, cellLineNumber2CellLineNameMap,fileNameNumber, fileNames,fileName2FileNumberMap, fileNumber2FileNameMap);
		writeNames(dataFolder,histoneNames,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_HISTONE_NAMES_OUTPUT_FILENAME);		
		writeMapsString2Integer(dataFolder,histoneName2HistoneNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_HISTONENAME_2_HISTONENUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,histoneNumber2HistoneNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_HISTONENUMBER_2_HISTONENAME_OUTPUT_FILENAME);

	}
	
	public static void writeAllPossibleEncodeCellLineNamesFileNames(String dataFolder,List<String> cellLineNames, Map<String,Integer> cellLineName2CellLineNumberMap, Map<Integer,String> cellLineNumber2CellLineNameMap,List<String> fileNames,Map<String,Integer> fileName2FileNumberMap,Map<Integer,String> fileNumber2FileNameMap){
		
		writeNames(dataFolder,cellLineNames,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_CELL_LINE_NAMES_OUTPUT_FILENAME);
		writeMapsString2Integer(dataFolder,cellLineName2CellLineNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_CELLLINENAME_2_CELLLINENUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,cellLineNumber2CellLineNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_CELLLINENUMBER_2_CELLLINENAME_OUTPUT_FILENAME);

		writeNames(dataFolder,fileNames,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_ENCODE_FILE_NAMES_OUTPUT_FILENAME);		
		writeMapsString2Integer(dataFolder,fileName2FileNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_FILENAME_2_FILENUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,fileNumber2FileNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_FILENUMBER_2_FILENAME_OUTPUT_FILENAME);

		
	}


	//@todo
	public static void writeAllPossible_UcscGeneEntrezId_RefSeqGeneName_HugoSymbol(String dataFolder){

		List<Integer> geneEntrezIds = new ArrayList<Integer>();
		
		List<String> refseqGeneNames = new ArrayList<String>();
		Map<String,Integer> refseqGeneName2RefseqGeneNameNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> refseqGeneNameNumber2RefseqGeneNameMap = new HashMap<Integer,String>();
		
		List<String> geneHugoSymbols = new ArrayList<String>();	
		Map<String,Integer> geneHugoSymbol2GeneHugoSymbolNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> geneHugoSymbolNumber2GeneHugoSymbolMap = new HashMap<Integer,String>();

		readUnsortedUcscRefSeqGeneFilesandWriteUnsortedUcscRefSeqGeneFilesWithNumbers(dataFolder,geneEntrezIds,refseqGeneNames,refseqGeneName2RefseqGeneNameNumberMap,refseqGeneNameNumber2RefseqGeneNameMap,geneHugoSymbols,geneHugoSymbol2GeneHugoSymbolNumberMap,geneHugoSymbolNumber2GeneHugoSymbolMap);
		
		writeNumbers(dataFolder,geneEntrezIds,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_UCSC_REF_SEQ_GENES_ENTREZ_GENE_IDS);		
		
		writeNames(dataFolder,refseqGeneNames,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_UCSC_REF_SEQ_GENE_NAMES);		
		writeMapsString2Integer(dataFolder,refseqGeneName2RefseqGeneNameNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_UCSC_REFSEQ_GENE_NAME_2_REFSEQ_GENE_NAME_NUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,refseqGeneNameNumber2RefseqGeneNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_UCSC_REFSEQ_GENE_NAME_NUMBER_2_REFSEQ_GENE_NAME_OUTPUT_FILENAME);

		
		writeNames(dataFolder,geneHugoSymbols,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_UCSC_GENE_HUGO_SYMBOLS_NAMES);		
		writeMapsString2Integer(dataFolder,geneHugoSymbol2GeneHugoSymbolNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_UCSC_GENE_HUGO_SYMBOL_2_GENE_HUGO_SYMBOL_NUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,geneHugoSymbolNumber2GeneHugoSymbolMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_UCSC_GENE_HUGO_SYMBOL_NUMBER_2_GENE_HUGO_SYMBOL_OUTPUT_FILENAME);

	}
	//@todo
	
	
	public static void writeAllPossibleGeneIds(String dataFolder){

		List<String> geneIds = new ArrayList<String>();
		readGeneIds(dataFolder, geneIds,Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_DIRECTORYNAME +Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME_1_OCT_2014 );
		writeNames(dataFolder,geneIds,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_GENE_IDS_OUTPUT_FILENAME);		
		
	}
	
	public static void writeAllPossibleRNAAccessionVersions(String dataFolder){

		List<String> rnaNucleotideAccessionVersions = new ArrayList<String>();
		readRNAAccessionVersions(dataFolder,rnaNucleotideAccessionVersions,Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_DIRECTORYNAME + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME_1_OCT_2014);
		writeNames(dataFolder,rnaNucleotideAccessionVersions,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_RNA_NUCLEUOTIDE_ACCESSION_VERSIONS_OUTPUT_FILENAME);		
		
	}

	
	public static void writeAllPossibleUcscRefSeqGeneName2s(String dataFolder){

		List<String> ucscRefSeqGeneName2s = new ArrayList<String>();
		readUcscRefSeqGeneName2s(dataFolder,ucscRefSeqGeneName2s,Commons.FTP_HG19_REFSEQ_GENES_DOWNLOADED_1_OCT_2014);
		writeNames(dataFolder,ucscRefSeqGeneName2s,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_ALTERNATE_GENE_NAMES_OUTPUT_FILENAME);		
		
	}
	
	public static void writeAllPossibleKeggPathwayNames(String dataFolder){
		
		List<String> keggPathwayNameList = new ArrayList<String>();	
		Map<String,Integer> keggPathwayName2KeggPathwayNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> keggPathwayNumber2KeggPathwayNameMap = new HashMap<Integer,String>();

		
		readKeggPathwayNames(dataFolder,keggPathwayNameList,keggPathwayName2KeggPathwayNumberMap,keggPathwayNumber2KeggPathwayNameMap,Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE);
		writeNames(dataFolder,keggPathwayNameList,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_KEGG_PATHWAY_NAMES_OUTPUT_FILENAME);		
		writeMapsString2Integer(dataFolder,keggPathwayName2KeggPathwayNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_KEGGPATHWAYNAME_2_KEGGPATHWAYNUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,keggPathwayNumber2KeggPathwayNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_KEGGPATHWAYNUMBER_2_KEGGPATHWAYNAME_OUTPUT_FILENAME);

	}
	
	//userDefinedGeneSetName2UserDefinedGeneSetNumberMap is already full
	//userDefinedGeneSetNumber2UserDefinedGeneSetNameMap is already full
	//Write them to name2NumberMap and number2NameMap
	public static void writeAllPossibleUserDefinedGeneSetNames(
			String dataFolder,
			TObjectShortMap<String> userDefinedGeneSetName2UserDefinedGeneSetNumberMap,
			TShortObjectMap<String> userDefinedGeneSetNumber2UserDefinedGeneSetNameMap){
		
		writeNames(dataFolder,userDefinedGeneSetName2UserDefinedGeneSetNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_USERDEFINEDGENESET_NAMES_OUTPUT_FILENAME);		
		writeMapsString2Short(dataFolder,userDefinedGeneSetName2UserDefinedGeneSetNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_USERDEFINEDGENESETNAME_2_USERDEFINEDGENESETNUMBER_OUTPUT_FILENAME);
		writeMapsShort2String(dataFolder,userDefinedGeneSetNumber2UserDefinedGeneSetNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_USERDEFINEDGENESETNUMBER_2_USERDEFINEDGENESETNAME_OUTPUT_FILENAME);
	
	}

	
	//@todo writeAllPossibleUserDefinedGeneSetNames ends
	
	
	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2]	--->	Input File Format	
	//			--->	default	Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE	
	//args[3]	--->	Annotation, overlap definition, number of bases, default 1
	//args[4]	--->	Enrichment parameter
	//			--->	default	Commons.DO_ENRICH
	//			--->			Commons.DO_NOT_ENRICH	
	//args[5]	--->	Generate Random Data Mode
	//			--->	default	Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	//			--->			Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT	
	//args[6]	--->	multiple testing parameter, enriched elements will be decided and sorted with respest to this parameter
	//			--->	default Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE
	//			--->			Commons.BONFERRONI_CORRECTED_P_VALUE
	//args[7]	--->	Bonferroni Correction Significance Level, default 0.05
	//args[8]	--->	Benjamini Hochberg FDR, default 0.05
	//args[9]	--->	Number of permutations, default 5000
	//args[10]	--->	Dnase Enrichment
	//			--->	default Commons.DO_NOT_DNASE_ENRICHMENT
	//			--->	Commons.DO_DNASE_ENRICHMENT
	//args[11]	--->	Histone Enrichment
	//			--->	default	Commons.DO_NOT_HISTONE_ENRICHMENT
	//			--->			Commons.DO_HISTONE_ENRICHMENT
	//args[12]	--->	Transcription Factor(TF) Enrichment 
	//			--->	default	Commons.DO_NOT_TF_ENRICHMENT
	//			--->			Commons.DO_TF_ENRICHMENT
	//args[13]	--->	KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_KEGGPATHWAY_ENRICHMENT
	//args[14]	--->	TF and KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	//args[15]	--->	TF and CellLine and KeggPathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	//args[16]	--->	RSAT parameter
	//			--->	default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//			--->			Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//args[17]	--->	job name example: ocd_gwas_snps 
	//args[18]	--->	writeGeneratedRandomDataMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	//			--->			Commons.WRITE_GENERATED_RANDOM_DATA
	//args[19]	--->	writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//args[20]	--->	writePermutationBasedAnnotationResultMode checkBox
	//			---> 	default	Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT			
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String dataFolder = glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
	
		//All Possible ENCODE File Names 
		Number fileNameNumber = new Number(1);		
		List<String> fileNames = new ArrayList<String>();
		Map<String,Integer> fileName2FileNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> fileNumber2FileNameMap = new HashMap<Integer,String>();
			
		//All Possible ENCODE Cell Line Names
		Number cellLineNameNumber = new Number(1);		
		List<String> cellLineNames = new ArrayList<String>();
		Map<String,Integer> cellLineName2CellLineNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> cellLineNumber2CellLineNameMap = new HashMap<Integer,String>();
					
		
		//Write all possible ENCODE cell line names	
		//Using unsorted dnase txt files under C:\eclipse_ganymede\workspace\Doktora1\src\annotate\encode\input_output\dnase 
		//Also write unsorted dnase files with numbers
		WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeAllPossibleEncodeCellLineNames(dataFolder,cellLineNameNumber, cellLineNames, cellLineName2CellLineNumberMap, cellLineNumber2CellLineNameMap, fileNameNumber, fileNames,fileName2FileNumberMap,fileNumber2FileNameMap);
		
		//Write all possible ENCODE tfbs names
		//Using unsorted tfbs txt files under C:\eclipse_ganymede\workspace\Doktora1\src\annotate\encode\input_output\tfbs 
		WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeAllPossibleEncodeTfNames(dataFolder,cellLineNameNumber, cellLineNames, cellLineName2CellLineNumberMap, cellLineNumber2CellLineNameMap,fileNameNumber, fileNames,fileName2FileNumberMap,fileNumber2FileNameMap);
		
		//Write all possible ENCODE histone names
		//Using unsorted tfbs txt files under C:\eclipse_ganymede\workspace\Doktora1\src\annotate\encode\input_output\\histone
		WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeAllPossibleEncodeHistoneNames(dataFolder,cellLineNameNumber, cellLineNames, cellLineName2CellLineNumberMap, cellLineNumber2CellLineNameMap,fileNameNumber, fileNames,fileName2FileNumberMap,fileNumber2FileNameMap);
		
		//At the end 
		//Write all possible ENCODE CellLine Names and File Names	
		WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeAllPossibleEncodeCellLineNamesFileNames(dataFolder,cellLineNames,cellLineName2CellLineNumberMap,cellLineNumber2CellLineNameMap,fileNames,fileName2FileNumberMap,fileNumber2FileNameMap);


		//Write all possible gene ids from ncbi file which is processed by GLANET
//		Using human_gene2refseq.txt under C:\eclipse_ganymede\workspace\Doktora1\src\ncbi\input_output
		WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeAllPossibleGeneIds(dataFolder);

		//Write all possible RNA nucleotide accession version, in other words ucsc refseq gene names from ncbi file which is processed by GLANET
//		Using human_gene2refseq.txt under C:\eclipse_ganymede\workspace\Doktora1\src\ncbi\input_output
		WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeAllPossibleRNAAccessionVersions(dataFolder);

		//Write all possible ucsc refseq gene name2, in other words hugo gene symbol, alternate gene name from hg19_refseq_genes.txt which is downloaded from ucsc genome browser
//		Using hg19_refseq_genes.txt under C:\\eclipse_ganymede\\workspace\\Doktora1\\src\\annotate\\ucscgenome\\input_output	
		WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeAllPossibleUcscRefSeqGeneName2s(dataFolder);
		
		//@todo
		//UCSC REFSEQ GENES
		//Read unsorted_ucsc_refseq_gene_chromosomebased_files and Write unsorted_ucsc_refseq_gene_chromosomebased_files_with_Numbers
		WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeAllPossible_UcscGeneEntrezId_RefSeqGeneName_HugoSymbol(dataFolder);
		//@todo

		//Write all possible kegg pathway names		
		//Using pathway_hsa.list under C:\eclipse_ganymede\workspace\Doktora1\src\keggpathway\ncbigenes\input_output
		WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeAllPossibleKeggPathwayNames(dataFolder);
		
		
	}

}
