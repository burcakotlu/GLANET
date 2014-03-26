/**
 * 
 */
package create;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import create.encode.NumberofDNAElements;
import auxiliary.FileOperations;

import common.Commons;

/**
 * @author burcakotlu
 *
 */
public class ChromosomeBasedFilesandOperations {
	
	//	Write Dnase Information to the console
	public static void writeDnaseInformationtoConsole(NumberofDNAElements numberofDNAElements){

		System.out.print("Number of dnase in Chr 1 " + numberofDNAElements.getNumberofDnaseinChr1()+ "\n");
		System.out.print("Number of dnase in Chr 2 " + numberofDNAElements.getNumberofDnaseinChr2()+ "\n");
		System.out.print("Number of dnase in Chr 3 " + numberofDNAElements.getNumberofDnaseinChr3()+ "\n");
		System.out.print("Number of dnase in Chr 4 " + numberofDNAElements.getNumberofDnaseinChr4()+ "\n");
		System.out.print("Number of dnase in Chr 5 " + numberofDNAElements.getNumberofDnaseinChr5()+ "\n");
		System.out.print("Number of dnase in Chr 6 " + numberofDNAElements.getNumberofDnaseinChr6()+ "\n");
		System.out.print("Number of dnase in Chr 7 " + numberofDNAElements.getNumberofDnaseinChr7()+ "\n");
		System.out.print("Number of dnase in Chr 8 " + numberofDNAElements.getNumberofDnaseinChr8()+ "\n");
		System.out.print("Number of dnase in Chr 9 " + numberofDNAElements.getNumberofDnaseinChr9()+ "\n");
		System.out.print("Number of dnase in Chr 10 " + numberofDNAElements.getNumberofDnaseinChr10()+ "\n");
		System.out.print("Number of dnase in Chr 11 " + numberofDNAElements.getNumberofDnaseinChr11()+ "\n");
		System.out.print("Number of dnase in Chr 12 " + numberofDNAElements.getNumberofDnaseinChr12()+ "\n");
		System.out.print("Number of dnase in Chr 13 " + numberofDNAElements.getNumberofDnaseinChr13()+ "\n");
		System.out.print("Number of dnase in Chr 14 " + numberofDNAElements.getNumberofDnaseinChr14()+ "\n");
		System.out.print("Number of dnase in Chr 15 " + numberofDNAElements.getNumberofDnaseinChr15()+ "\n");
		System.out.print("Number of dnase in Chr 16 " + numberofDNAElements.getNumberofDnaseinChr16()+ "\n");
		System.out.print("Number of dnase in Chr 17 " + numberofDNAElements.getNumberofDnaseinChr17()+ "\n");
		System.out.print("Number of dnase in Chr 18 " + numberofDNAElements.getNumberofDnaseinChr18()+ "\n");
		System.out.print("Number of dnase in Chr 19 " + numberofDNAElements.getNumberofDnaseinChr19()+ "\n");
		System.out.print("Number of dnase in Chr 20 " + numberofDNAElements.getNumberofDnaseinChr20()+ "\n");
		System.out.print("Number of dnase in Chr 21 " + numberofDNAElements.getNumberofDnaseinChr21()+ "\n");
		System.out.print("Number of dnase in Chr 22 " + numberofDNAElements.getNumberofDnaseinChr22()+ "\n");
		System.out.print("Number of dnase in Chr X " + numberofDNAElements.getNumberofDnaseinChrX()+ "\n");
		System.out.print("Number of dnase in Chr Y " + numberofDNAElements.getNumberofDnaseinChrY()+ "\n");
	
		System.out.print("Total number of dnase  " + numberofDNAElements.getTotalNumberofDnase()+ "\n");
		
	}
	
//	Write Histone Information to the console
	public static void writeHistoneInformationtoConsole(NumberofDNAElements numberofDNAElements){
		
		System.out.print("Number of histone in Chr 1 " + numberofDNAElements.getNumberofHistoneinChr1()+ "\n");
		System.out.print("Number of histone in Chr 2 " + numberofDNAElements.getNumberofHistoneinChr2()+ "\n");
		System.out.print("Number of histone in Chr 3 " + numberofDNAElements.getNumberofHistoneinChr3()+ "\n");
		System.out.print("Number of histone in Chr 4 " + numberofDNAElements.getNumberofHistoneinChr4()+ "\n");
		System.out.print("Number of histone in Chr 5 " + numberofDNAElements.getNumberofHistoneinChr5()+ "\n");
		System.out.print("Number of histone in Chr 6 " + numberofDNAElements.getNumberofHistoneinChr6()+ "\n");
		System.out.print("Number of histone in Chr 7 " + numberofDNAElements.getNumberofHistoneinChr7()+ "\n");
		System.out.print("Number of histone in Chr 8 " + numberofDNAElements.getNumberofHistoneinChr8()+ "\n");
		System.out.print("Number of histone in Chr 9 " + numberofDNAElements.getNumberofHistoneinChr9()+ "\n");
		System.out.print("Number of histone in Chr 10 " + numberofDNAElements.getNumberofHistoneinChr10()+ "\n");
		System.out.print("Number of histone in Chr 11 " + numberofDNAElements.getNumberofHistoneinChr11()+ "\n");
		System.out.print("Number of histone in Chr 12 " + numberofDNAElements.getNumberofHistoneinChr12()+ "\n");
		System.out.print("Number of histone in Chr 13 " + numberofDNAElements.getNumberofHistoneinChr13()+ "\n");
		System.out.print("Number of histone in Chr 14 " + numberofDNAElements.getNumberofHistoneinChr14()+ "\n");
		System.out.print("Number of histone in Chr 15 " + numberofDNAElements.getNumberofHistoneinChr15()+ "\n");
		System.out.print("Number of histone in Chr 16 " + numberofDNAElements.getNumberofHistoneinChr16()+ "\n");
		System.out.print("Number of histone in Chr 17 " + numberofDNAElements.getNumberofHistoneinChr17()+ "\n");
		System.out.print("Number of histone in Chr 18 " + numberofDNAElements.getNumberofHistoneinChr18()+ "\n");
		System.out.print("Number of histone in Chr 19 " + numberofDNAElements.getNumberofHistoneinChr19()+ "\n");
		System.out.print("Number of histone in Chr 20 " + numberofDNAElements.getNumberofHistoneinChr20()+ "\n");
		System.out.print("Number of histone in Chr 21 " + numberofDNAElements.getNumberofHistoneinChr21()+ "\n");
		System.out.print("Number of histone in Chr 22 " + numberofDNAElements.getNumberofHistoneinChr22()+ "\n");
		System.out.print("Number of histone in Chr X " + numberofDNAElements.getNumberofHistoneinChrX()+ "\n");
		System.out.print("Number of histone in Chr Y " + numberofDNAElements.getNumberofHistoneinChrY()+ "\n");
	
		System.out.print("Total number of histone  " + numberofDNAElements.getTotalNumberofHistone()+ "\n");
			
	}
	
	//	Write Tfbs Information to the console
	public static void writeTfbsInformationtoConsole(NumberofDNAElements numberofDNAElements){
		
		System.out.print("Number of tfbs in Chr 1 " + numberofDNAElements.getNumberofTfbsinChr1()+ "\n");
		System.out.print("Number of tfbs in Chr 2 " + numberofDNAElements.getNumberofTfbsinChr2()+ "\n");
		System.out.print("Number of tfbs in Chr 3 " + numberofDNAElements.getNumberofTfbsinChr3()+ "\n");
		System.out.print("Number of tfbs in Chr 4 " + numberofDNAElements.getNumberofTfbsinChr4()+ "\n");
		System.out.print("Number of tfbs in Chr 5 " + numberofDNAElements.getNumberofTfbsinChr5()+ "\n");
		System.out.print("Number of tfbs in Chr 6 " + numberofDNAElements.getNumberofTfbsinChr6()+ "\n");
		System.out.print("Number of tfbs in Chr 7 " + numberofDNAElements.getNumberofTfbsinChr7()+ "\n");
		System.out.print("Number of tfbs in Chr 8 " + numberofDNAElements.getNumberofTfbsinChr8()+ "\n");
		System.out.print("Number of tfbs in Chr 9 " + numberofDNAElements.getNumberofTfbsinChr9()+ "\n");
		System.out.print("Number of tfbs in Chr 10 " + numberofDNAElements.getNumberofTfbsinChr10()+ "\n");
		System.out.print("Number of tfbs in Chr 11 " + numberofDNAElements.getNumberofTfbsinChr11()+ "\n");
		System.out.print("Number of tfbs in Chr 12 " + numberofDNAElements.getNumberofTfbsinChr12()+ "\n");
		System.out.print("Number of tfbs in Chr 13 " + numberofDNAElements.getNumberofTfbsinChr13()+ "\n");
		System.out.print("Number of tfbs in Chr 14 " + numberofDNAElements.getNumberofTfbsinChr14()+ "\n");
		System.out.print("Number of tfbs in Chr 15 " + numberofDNAElements.getNumberofTfbsinChr15()+ "\n");
		System.out.print("Number of tfbs in Chr 16 " + numberofDNAElements.getNumberofTfbsinChr16()+ "\n");
		System.out.print("Number of tfbs in Chr 17 " + numberofDNAElements.getNumberofTfbsinChr17()+ "\n");
		System.out.print("Number of tfbs in Chr 18 " + numberofDNAElements.getNumberofTfbsinChr18()+ "\n");
		System.out.print("Number of tfbs in Chr 19 " + numberofDNAElements.getNumberofTfbsinChr19()+ "\n");
		System.out.print("Number of tfbs in Chr 20 " + numberofDNAElements.getNumberofTfbsinChr20()+ "\n");
		System.out.print("Number of tfbs in Chr 21 " + numberofDNAElements.getNumberofTfbsinChr21()+ "\n");
		System.out.print("Number of tfbs in Chr 22 " + numberofDNAElements.getNumberofTfbsinChr22()+ "\n");
		System.out.print("Number of tfbs in Chr X " + numberofDNAElements.getNumberofTfbsinChrX()+ "\n");
		System.out.print("Number of tfbs in Chr Y " + numberofDNAElements.getNumberofTfbsinChrY()+ "\n");
	
		System.out.print("Total number of tfbs  " + numberofDNAElements.getTotalNumberofTfbs()+ "\n");
			
	}

	
	public static void closeChromosomeBasedBufferedWriters(List<BufferedWriter> bufferedWriterList){
		Iterator<BufferedWriter> itr = bufferedWriterList.iterator();
		
		while (itr.hasNext()){
			BufferedWriter bw = (BufferedWriter) itr.next();
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public static void closeChromosomeBasedBufferedReaders(List<BufferedReader> bufferedReaderList){
		Iterator<BufferedReader> itr = bufferedReaderList.iterator();
		
		while (itr.hasNext()){
			BufferedReader br = (BufferedReader) itr.next();
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	public static void openUnsortedChromosomeBasedDnaseFileWriters(String outputFolder,List<BufferedWriter> bufferedWriterList){
		
		try {
			FileWriter  fileWriter1 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR1_DNASE_FILENAME);
			FileWriter  fileWriter2 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR2_DNASE_FILENAME);
			FileWriter  fileWriter3 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR3_DNASE_FILENAME);
			FileWriter  fileWriter4 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR4_DNASE_FILENAME);
			FileWriter  fileWriter5 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR5_DNASE_FILENAME);
			FileWriter  fileWriter6 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR6_DNASE_FILENAME);
			FileWriter  fileWriter7 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR7_DNASE_FILENAME);
			FileWriter  fileWriter8 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR8_DNASE_FILENAME);
			FileWriter  fileWriter9 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR9_DNASE_FILENAME);
			FileWriter  fileWriter10 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR10_DNASE_FILENAME);
			FileWriter  fileWriter11 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR11_DNASE_FILENAME);
			FileWriter  fileWriter12 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR12_DNASE_FILENAME);
			FileWriter  fileWriter13 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR13_DNASE_FILENAME);
			FileWriter  fileWriter14 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR14_DNASE_FILENAME);
			FileWriter  fileWriter15 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR15_DNASE_FILENAME);
			FileWriter  fileWriter16 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR16_DNASE_FILENAME);
			FileWriter  fileWriter17 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR17_DNASE_FILENAME);
			FileWriter  fileWriter18 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR18_DNASE_FILENAME);
			FileWriter  fileWriter19 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR19_DNASE_FILENAME);
			FileWriter  fileWriter20 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR20_DNASE_FILENAME);
			FileWriter  fileWriter21 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR21_DNASE_FILENAME);
			FileWriter  fileWriter22 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHR22_DNASE_FILENAME);
			FileWriter  fileWriterX = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHRX_DNASE_FILENAME);
			FileWriter  fileWriterY = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY , Commons.UNSORTED_CHRY_DNASE_FILENAME);
				
			bufferedWriterList.add(new BufferedWriter(fileWriter1));
			bufferedWriterList.add(new BufferedWriter(fileWriter2));
			bufferedWriterList.add(new BufferedWriter(fileWriter3));
			bufferedWriterList.add(new BufferedWriter(fileWriter4));
			bufferedWriterList.add(new BufferedWriter(fileWriter5));
			bufferedWriterList.add(new BufferedWriter(fileWriter6));
			bufferedWriterList.add(new BufferedWriter(fileWriter7));
			bufferedWriterList.add(new BufferedWriter(fileWriter8));
			bufferedWriterList.add(new BufferedWriter(fileWriter9));
			bufferedWriterList.add(new BufferedWriter(fileWriter10));
			bufferedWriterList.add(new BufferedWriter(fileWriter11));
			bufferedWriterList.add(new BufferedWriter(fileWriter12));
			bufferedWriterList.add(new BufferedWriter(fileWriter13));
			bufferedWriterList.add(new BufferedWriter(fileWriter14));
			bufferedWriterList.add(new BufferedWriter(fileWriter15));
			bufferedWriterList.add(new BufferedWriter(fileWriter16));
			bufferedWriterList.add(new BufferedWriter(fileWriter17));
			bufferedWriterList.add(new BufferedWriter(fileWriter18));
			bufferedWriterList.add(new BufferedWriter(fileWriter19));
			bufferedWriterList.add(new BufferedWriter(fileWriter20));
			bufferedWriterList.add(new BufferedWriter(fileWriter21));
			bufferedWriterList.add(new BufferedWriter(fileWriter22));
			bufferedWriterList.add(new BufferedWriter(fileWriterX));
			bufferedWriterList.add(new BufferedWriter(fileWriterY));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	public static void openUnsortedChromosomeBasedHistoneFileWriters(String outputFolder,List<BufferedWriter> bufferedWriterList){
		
		try {
			FileWriter  fileWriter1 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR1_HISTONE_FILENAME);
			FileWriter  fileWriter2 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR2_HISTONE_FILENAME);
			FileWriter  fileWriter3 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR3_HISTONE_FILENAME);
			FileWriter  fileWriter4 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR4_HISTONE_FILENAME);
			FileWriter  fileWriter5 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR5_HISTONE_FILENAME);
			FileWriter  fileWriter6 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR6_HISTONE_FILENAME);
			FileWriter  fileWriter7 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR7_HISTONE_FILENAME);
			FileWriter  fileWriter8 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR8_HISTONE_FILENAME);
			FileWriter  fileWriter9 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR9_HISTONE_FILENAME);
			FileWriter  fileWriter10 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR10_HISTONE_FILENAME);
			FileWriter  fileWriter11 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR11_HISTONE_FILENAME);
			FileWriter  fileWriter12 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR12_HISTONE_FILENAME);
			FileWriter  fileWriter13 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR13_HISTONE_FILENAME);
			FileWriter  fileWriter14 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR14_HISTONE_FILENAME);
			FileWriter  fileWriter15 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR15_HISTONE_FILENAME);
			FileWriter  fileWriter16 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR16_HISTONE_FILENAME);
			FileWriter  fileWriter17 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR17_HISTONE_FILENAME);
			FileWriter  fileWriter18 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR18_HISTONE_FILENAME);
			FileWriter  fileWriter19 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR19_HISTONE_FILENAME);
			FileWriter  fileWriter20 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR20_HISTONE_FILENAME);
			FileWriter  fileWriter21 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR21_HISTONE_FILENAME);
			FileWriter  fileWriter22 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHR22_HISTONE_FILENAME);
			FileWriter  fileWriterX = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHRX_HISTONE_FILENAME);
			FileWriter  fileWriterY = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY , Commons.UNSORTED_CHRY_HISTONE_FILENAME);
				
			bufferedWriterList.add(new BufferedWriter(fileWriter1));
			bufferedWriterList.add(new BufferedWriter(fileWriter2));
			bufferedWriterList.add(new BufferedWriter(fileWriter3));
			bufferedWriterList.add(new BufferedWriter(fileWriter4));
			bufferedWriterList.add(new BufferedWriter(fileWriter5));
			bufferedWriterList.add(new BufferedWriter(fileWriter6));
			bufferedWriterList.add(new BufferedWriter(fileWriter7));
			bufferedWriterList.add(new BufferedWriter(fileWriter8));
			bufferedWriterList.add(new BufferedWriter(fileWriter9));
			bufferedWriterList.add(new BufferedWriter(fileWriter10));
			bufferedWriterList.add(new BufferedWriter(fileWriter11));
			bufferedWriterList.add(new BufferedWriter(fileWriter12));
			bufferedWriterList.add(new BufferedWriter(fileWriter13));
			bufferedWriterList.add(new BufferedWriter(fileWriter14));
			bufferedWriterList.add(new BufferedWriter(fileWriter15));
			bufferedWriterList.add(new BufferedWriter(fileWriter16));
			bufferedWriterList.add(new BufferedWriter(fileWriter17));
			bufferedWriterList.add(new BufferedWriter(fileWriter18));
			bufferedWriterList.add(new BufferedWriter(fileWriter19));
			bufferedWriterList.add(new BufferedWriter(fileWriter20));
			bufferedWriterList.add(new BufferedWriter(fileWriter21));
			bufferedWriterList.add(new BufferedWriter(fileWriter22));
			bufferedWriterList.add(new BufferedWriter(fileWriterX));
			bufferedWriterList.add(new BufferedWriter(fileWriterY));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void openUnsortedChromosomeBasedTfbsFileWriters(String outputFolder,List<BufferedWriter> bufferedWriterList){
		
		try {
			FileWriter  fileWriter1 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR1_TFBS_FILENAME);
			FileWriter  fileWriter2 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR2_TFBS_FILENAME);
			FileWriter  fileWriter3 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR3_TFBS_FILENAME);
			FileWriter  fileWriter4 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR4_TFBS_FILENAME);
			FileWriter  fileWriter5 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR5_TFBS_FILENAME);
			FileWriter  fileWriter6 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR6_TFBS_FILENAME);
			FileWriter  fileWriter7 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR7_TFBS_FILENAME);
			FileWriter  fileWriter8 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR8_TFBS_FILENAME);
			FileWriter  fileWriter9 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR9_TFBS_FILENAME);
			FileWriter  fileWriter10 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR10_TFBS_FILENAME);
			FileWriter  fileWriter11 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR11_TFBS_FILENAME);
			FileWriter  fileWriter12 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR12_TFBS_FILENAME);
			FileWriter  fileWriter13 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR13_TFBS_FILENAME);
			FileWriter  fileWriter14 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR14_TFBS_FILENAME);
			FileWriter  fileWriter15 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR15_TFBS_FILENAME);
			FileWriter  fileWriter16 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR16_TFBS_FILENAME);
			FileWriter  fileWriter17 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR17_TFBS_FILENAME);
			FileWriter  fileWriter18 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR18_TFBS_FILENAME);
			FileWriter  fileWriter19 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR19_TFBS_FILENAME);
			FileWriter  fileWriter20 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR20_TFBS_FILENAME);
			FileWriter  fileWriter21 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR21_TFBS_FILENAME);
			FileWriter  fileWriter22 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHR22_TFBS_FILENAME);
			FileWriter  fileWriterX = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHRX_TFBS_FILENAME);
			FileWriter  fileWriterY = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY , Commons.UNSORTED_CHRY_TFBS_FILENAME);
				
			bufferedWriterList.add(new BufferedWriter(fileWriter1));
			bufferedWriterList.add(new BufferedWriter(fileWriter2));
			bufferedWriterList.add(new BufferedWriter(fileWriter3));
			bufferedWriterList.add(new BufferedWriter(fileWriter4));
			bufferedWriterList.add(new BufferedWriter(fileWriter5));
			bufferedWriterList.add(new BufferedWriter(fileWriter6));
			bufferedWriterList.add(new BufferedWriter(fileWriter7));
			bufferedWriterList.add(new BufferedWriter(fileWriter8));
			bufferedWriterList.add(new BufferedWriter(fileWriter9));
			bufferedWriterList.add(new BufferedWriter(fileWriter10));
			bufferedWriterList.add(new BufferedWriter(fileWriter11));
			bufferedWriterList.add(new BufferedWriter(fileWriter12));
			bufferedWriterList.add(new BufferedWriter(fileWriter13));
			bufferedWriterList.add(new BufferedWriter(fileWriter14));
			bufferedWriterList.add(new BufferedWriter(fileWriter15));
			bufferedWriterList.add(new BufferedWriter(fileWriter16));
			bufferedWriterList.add(new BufferedWriter(fileWriter17));
			bufferedWriterList.add(new BufferedWriter(fileWriter18));
			bufferedWriterList.add(new BufferedWriter(fileWriter19));
			bufferedWriterList.add(new BufferedWriter(fileWriter20));
			bufferedWriterList.add(new BufferedWriter(fileWriter21));
			bufferedWriterList.add(new BufferedWriter(fileWriter22));
			bufferedWriterList.add(new BufferedWriter(fileWriterX));
			bufferedWriterList.add(new BufferedWriter(fileWriterY));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void openUnsortedChromosomeBasedRefSeqGeneFileWriters(String outputFolder,List<BufferedWriter> bufferedWriterList){
		
		try {
			FileWriter  fileWriter1 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR1_REFSEQ_GENES);
			FileWriter  fileWriter2 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR2_REFSEQ_GENES);
			FileWriter  fileWriter3 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR3_REFSEQ_GENES);
			FileWriter  fileWriter4 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR4_REFSEQ_GENES);
			FileWriter  fileWriter5 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR5_REFSEQ_GENES);
			FileWriter  fileWriter6 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR6_REFSEQ_GENES);
			FileWriter  fileWriter7 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR7_REFSEQ_GENES);
			FileWriter  fileWriter8 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR8_REFSEQ_GENES);
			FileWriter  fileWriter9 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR9_REFSEQ_GENES);
			FileWriter  fileWriter10 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR10_REFSEQ_GENES);
			FileWriter  fileWriter11 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR11_REFSEQ_GENES);
			FileWriter  fileWriter12 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR12_REFSEQ_GENES);
			FileWriter  fileWriter13 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR13_REFSEQ_GENES);
			FileWriter  fileWriter14 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR14_REFSEQ_GENES);
			FileWriter  fileWriter15 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR15_REFSEQ_GENES);
			FileWriter  fileWriter16 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR16_REFSEQ_GENES);
			FileWriter  fileWriter17 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR17_REFSEQ_GENES);
			FileWriter  fileWriter18 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR18_REFSEQ_GENES);
			FileWriter  fileWriter19 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR19_REFSEQ_GENES);
			FileWriter  fileWriter20 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR20_REFSEQ_GENES);
			FileWriter  fileWriter21 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR21_REFSEQ_GENES);
			FileWriter  fileWriter22 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR22_REFSEQ_GENES);
			FileWriter  fileWriterX = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHRX_REFSEQ_GENES);
			FileWriter  fileWriterY = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHRY_REFSEQ_GENES);
				
			bufferedWriterList.add(new BufferedWriter(fileWriter1));
			bufferedWriterList.add(new BufferedWriter(fileWriter2));
			bufferedWriterList.add(new BufferedWriter(fileWriter3));
			bufferedWriterList.add(new BufferedWriter(fileWriter4));
			bufferedWriterList.add(new BufferedWriter(fileWriter5));
			bufferedWriterList.add(new BufferedWriter(fileWriter6));
			bufferedWriterList.add(new BufferedWriter(fileWriter7));
			bufferedWriterList.add(new BufferedWriter(fileWriter8));
			bufferedWriterList.add(new BufferedWriter(fileWriter9));
			bufferedWriterList.add(new BufferedWriter(fileWriter10));
			bufferedWriterList.add(new BufferedWriter(fileWriter11));
			bufferedWriterList.add(new BufferedWriter(fileWriter12));
			bufferedWriterList.add(new BufferedWriter(fileWriter13));
			bufferedWriterList.add(new BufferedWriter(fileWriter14));
			bufferedWriterList.add(new BufferedWriter(fileWriter15));
			bufferedWriterList.add(new BufferedWriter(fileWriter16));
			bufferedWriterList.add(new BufferedWriter(fileWriter17));
			bufferedWriterList.add(new BufferedWriter(fileWriter18));
			bufferedWriterList.add(new BufferedWriter(fileWriter19));
			bufferedWriterList.add(new BufferedWriter(fileWriter20));
			bufferedWriterList.add(new BufferedWriter(fileWriter21));
			bufferedWriterList.add(new BufferedWriter(fileWriter22));
			bufferedWriterList.add(new BufferedWriter(fileWriterX));
			bufferedWriterList.add(new BufferedWriter(fileWriterY));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void openUnsortedChromosomeBasedRefSeqGeneFileReaders(String outputFolder,List<BufferedReader> bufferedReaderList){
		
		try {
			FileReader  fileReader1 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR1_REFSEQ_GENES);
			FileReader  fileReader2 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR2_REFSEQ_GENES);
			FileReader  fileReader3 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR3_REFSEQ_GENES);
			FileReader  fileReader4 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR4_REFSEQ_GENES);
			FileReader  fileReader5 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR5_REFSEQ_GENES);
			FileReader  fileReader6 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR6_REFSEQ_GENES);
			FileReader  fileReader7 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR7_REFSEQ_GENES);
			FileReader  fileReader8 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR8_REFSEQ_GENES);
			FileReader  fileReader9 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR9_REFSEQ_GENES);
			FileReader  fileReader10 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR10_REFSEQ_GENES);
			FileReader  fileReader11 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR11_REFSEQ_GENES);
			FileReader  fileReader12 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR12_REFSEQ_GENES);
			FileReader  fileReader13 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR13_REFSEQ_GENES);
			FileReader  fileReader14 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR14_REFSEQ_GENES);
			FileReader  fileReader15 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR15_REFSEQ_GENES);
			FileReader  fileReader16 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR16_REFSEQ_GENES);
			FileReader  fileReader17 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR17_REFSEQ_GENES);
			FileReader  fileReader18 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR18_REFSEQ_GENES);
			FileReader  fileReader19 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR19_REFSEQ_GENES);
			FileReader  fileReader20 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR20_REFSEQ_GENES);
			FileReader  fileReader21 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR21_REFSEQ_GENES);
			FileReader  fileReader22 = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR22_REFSEQ_GENES);
			FileReader  fileReaderX = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHRX_REFSEQ_GENES);
			FileReader  fileReaderY = FileOperations.createFileReader(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHRY_REFSEQ_GENES);
			
						
			bufferedReaderList.add(new BufferedReader(fileReader1));
			bufferedReaderList.add(new BufferedReader(fileReader2));
			bufferedReaderList.add(new BufferedReader(fileReader3));
			bufferedReaderList.add(new BufferedReader(fileReader4));
			bufferedReaderList.add(new BufferedReader(fileReader5));
			bufferedReaderList.add(new BufferedReader(fileReader6));
			bufferedReaderList.add(new BufferedReader(fileReader7));
			bufferedReaderList.add(new BufferedReader(fileReader8));
			bufferedReaderList.add(new BufferedReader(fileReader9));
			bufferedReaderList.add(new BufferedReader(fileReader10));
			bufferedReaderList.add(new BufferedReader(fileReader11));
			bufferedReaderList.add(new BufferedReader(fileReader12));
			bufferedReaderList.add(new BufferedReader(fileReader13));
			bufferedReaderList.add(new BufferedReader(fileReader14));
			bufferedReaderList.add(new BufferedReader(fileReader15));
			bufferedReaderList.add(new BufferedReader(fileReader16));
			bufferedReaderList.add(new BufferedReader(fileReader17));
			bufferedReaderList.add(new BufferedReader(fileReader18));
			bufferedReaderList.add(new BufferedReader(fileReader19));
			bufferedReaderList.add(new BufferedReader(fileReader20));
			bufferedReaderList.add(new BufferedReader(fileReader21));
			bufferedReaderList.add(new BufferedReader(fileReader22));
			bufferedReaderList.add(new BufferedReader(fileReaderX));
			bufferedReaderList.add(new BufferedReader(fileReaderY));
						
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void openSortedChromosomeBasedRefSeqGeneFiles(String outputFolder,List<BufferedWriter> bufferedWriterList){
		try {
			FileWriter  fileWriter1 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR1_REFSEQ_GENES);
			FileWriter  fileWriter2 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR2_REFSEQ_GENES);
			FileWriter  fileWriter3 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR3_REFSEQ_GENES);
			FileWriter  fileWriter4 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR4_REFSEQ_GENES);
			FileWriter  fileWriter5 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR5_REFSEQ_GENES);
			FileWriter  fileWriter6 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR6_REFSEQ_GENES);
			FileWriter  fileWriter7 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR7_REFSEQ_GENES);
			FileWriter  fileWriter8 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR8_REFSEQ_GENES);
			FileWriter  fileWriter9 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR9_REFSEQ_GENES);
			FileWriter  fileWriter10 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR10_REFSEQ_GENES);
			FileWriter  fileWriter11 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR11_REFSEQ_GENES);
			FileWriter  fileWriter12 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR12_REFSEQ_GENES);
			FileWriter  fileWriter13 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR13_REFSEQ_GENES);
			FileWriter  fileWriter14 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR14_REFSEQ_GENES);
			FileWriter  fileWriter15 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR15_REFSEQ_GENES);
			FileWriter  fileWriter16 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR16_REFSEQ_GENES);
			FileWriter  fileWriter17 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR17_REFSEQ_GENES);
			FileWriter  fileWriter18 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR18_REFSEQ_GENES);
			FileWriter  fileWriter19 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR19_REFSEQ_GENES);
			FileWriter  fileWriter20 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR20_REFSEQ_GENES);
			FileWriter  fileWriter21 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR21_REFSEQ_GENES);
			FileWriter  fileWriter22 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR22_REFSEQ_GENES);
			FileWriter  fileWriterX = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHRX_REFSEQ_GENES);
			FileWriter  fileWriterY = FileOperations.createFileWriter(outputFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHRY_REFSEQ_GENES);
				
			bufferedWriterList.add(new BufferedWriter(fileWriter1));
			bufferedWriterList.add(new BufferedWriter(fileWriter2));
			bufferedWriterList.add(new BufferedWriter(fileWriter3));
			bufferedWriterList.add(new BufferedWriter(fileWriter4));
			bufferedWriterList.add(new BufferedWriter(fileWriter5));
			bufferedWriterList.add(new BufferedWriter(fileWriter6));
			bufferedWriterList.add(new BufferedWriter(fileWriter7));
			bufferedWriterList.add(new BufferedWriter(fileWriter8));
			bufferedWriterList.add(new BufferedWriter(fileWriter9));
			bufferedWriterList.add(new BufferedWriter(fileWriter10));
			bufferedWriterList.add(new BufferedWriter(fileWriter11));
			bufferedWriterList.add(new BufferedWriter(fileWriter12));
			bufferedWriterList.add(new BufferedWriter(fileWriter13));
			bufferedWriterList.add(new BufferedWriter(fileWriter14));
			bufferedWriterList.add(new BufferedWriter(fileWriter15));
			bufferedWriterList.add(new BufferedWriter(fileWriter16));
			bufferedWriterList.add(new BufferedWriter(fileWriter17));
			bufferedWriterList.add(new BufferedWriter(fileWriter18));
			bufferedWriterList.add(new BufferedWriter(fileWriter19));
			bufferedWriterList.add(new BufferedWriter(fileWriter20));
			bufferedWriterList.add(new BufferedWriter(fileWriter21));
			bufferedWriterList.add(new BufferedWriter(fileWriter22));
			bufferedWriterList.add(new BufferedWriter(fileWriterX));
			bufferedWriterList.add(new BufferedWriter(fileWriterY));
			
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}
	
	public static FileReader getUnsortedRefSeqGenesFileReader(String chromName){
				
		FileReader fileReader = null;
	
		try {
			
			if (Commons.CHROMOSOME1.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR1_REFSEQ_GENES);								
			} else if (Commons.CHROMOSOME2.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR2_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME3.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR3_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME4.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR4_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME5.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR5_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME6.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR6_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME7.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR7_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME8.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR8_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME9.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR9_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME10.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR10_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME11.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR11_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME12.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR12_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME13.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR13_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME14.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR14_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME15.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR15_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME16.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR16_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME17.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR17_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME18.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR18_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME19.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR19_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME20.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR20_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME21.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR21_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME22.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR22_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOMEX.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHRX_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOMEY.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHRY_REFSEQ_GENES);				
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileReader;
	}
	
	
	public static FileReader getSortedRefSeqGenesFileReader(String chromName){
		
		FileReader fileReader = null;
	
		try {
			
			if (Commons.CHROMOSOME1.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR1_REFSEQ_GENES);								
			} else if (Commons.CHROMOSOME2.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR2_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME3.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR3_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME4.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR4_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME5.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR5_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME6.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR6_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME7.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR7_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME8.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR8_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME9.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR9_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME10.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR10_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME11.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR11_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME12.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR12_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME13.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR13_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME14.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR14_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME15.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR15_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME16.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR16_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME17.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR17_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME18.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR18_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME19.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR19_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME20.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR20_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME21.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR21_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOME22.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR22_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOMEX.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHRX_REFSEQ_GENES);				
			} else if (Commons.CHROMOSOMEY.equals(chromName)){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHRY_REFSEQ_GENES);				
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileReader;
	}
	
	public static BufferedWriter getBufferedWriter(String chromName,List<BufferedWriter> bufferedWriterList){
		BufferedWriter bufferedWriter = null;
		
		if (chromName.equals(Commons.CHROMOSOME1)){
			bufferedWriter = bufferedWriterList.get(0);
		}else if (chromName.equals(Commons.CHROMOSOME2)){
			bufferedWriter = bufferedWriterList.get(1);			
		}else if (chromName.equals(Commons.CHROMOSOME3)){
			bufferedWriter = bufferedWriterList.get(2);			
		}else if (chromName.equals(Commons.CHROMOSOME4)){
			bufferedWriter = bufferedWriterList.get(3);			
		}else if (chromName.equals(Commons.CHROMOSOME5)){
			bufferedWriter = bufferedWriterList.get(4);			
		}else if (chromName.equals(Commons.CHROMOSOME6)){
			bufferedWriter = bufferedWriterList.get(5);			
		}else if (chromName.equals(Commons.CHROMOSOME7)){
			bufferedWriter = bufferedWriterList.get(6);			
		}else if (chromName.equals(Commons.CHROMOSOME8)){
			bufferedWriter = bufferedWriterList.get(7);			
		}else if (chromName.equals(Commons.CHROMOSOME9)){
			bufferedWriter = bufferedWriterList.get(8);			
		}else if (chromName.equals(Commons.CHROMOSOME10)){
			bufferedWriter = bufferedWriterList.get(9);			
		}else if (chromName.equals(Commons.CHROMOSOME11)){
			bufferedWriter = bufferedWriterList.get(10);			
		}else if (chromName.equals(Commons.CHROMOSOME12)){
			bufferedWriter = bufferedWriterList.get(11);			
		}else if (chromName.equals(Commons.CHROMOSOME13)){
			bufferedWriter = bufferedWriterList.get(12);			
		}else if (chromName.equals(Commons.CHROMOSOME14)){
			bufferedWriter = bufferedWriterList.get(13);			
		}else if (chromName.equals(Commons.CHROMOSOME15)){
			bufferedWriter = bufferedWriterList.get(14);			
		}else if (chromName.equals(Commons.CHROMOSOME16)){
			bufferedWriter = bufferedWriterList.get(15);			
		}else if (chromName.equals(Commons.CHROMOSOME17)){
			bufferedWriter = bufferedWriterList.get(16);			
		}else if (chromName.equals(Commons.CHROMOSOME18)){
			bufferedWriter = bufferedWriterList.get(17);			
		}else if (chromName.equals(Commons.CHROMOSOME19)){
			bufferedWriter = bufferedWriterList.get(18);			
		}else if (chromName.equals(Commons.CHROMOSOME20)){
			bufferedWriter = bufferedWriterList.get(19);			
		}else if (chromName.equals(Commons.CHROMOSOME21)){
			bufferedWriter = bufferedWriterList.get(20);			
		}else if (chromName.equals(Commons.CHROMOSOME22)){
			bufferedWriter = bufferedWriterList.get(21);			
		}else if (chromName.equals(Commons.CHROMOSOMEX)){
			bufferedWriter = bufferedWriterList.get(22);			
		}else if (chromName.equals(Commons.CHROMOSOMEY)){
			bufferedWriter = bufferedWriterList.get(23);			
		}
		
		return bufferedWriter;
	}
	
	
	public static FileReader getUnsortedDnaseFileReader(int i, String outputFolder){
		try {
				switch (i) {
			        case 1:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR1_DNASE_FILENAME);			
			        case 2:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR2_DNASE_FILENAME);
					case 3:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR3_DNASE_FILENAME);
			        case 4: return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR4_DNASE_FILENAME);
			        case 5:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR5_DNASE_FILENAME);
			        case 6:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR6_DNASE_FILENAME);
			        case 7:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR7_DNASE_FILENAME);
			        case 8:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR8_DNASE_FILENAME);
			        case 9:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR9_DNASE_FILENAME);
			        case 10:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR10_DNASE_FILENAME);
			        case 11:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR11_DNASE_FILENAME);
			        case 12:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR12_DNASE_FILENAME);
			        case 13:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR13_DNASE_FILENAME);
			        case 14:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR14_DNASE_FILENAME);
			        case 15:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR15_DNASE_FILENAME);
			        case 16:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR16_DNASE_FILENAME);
			        case 17:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR17_DNASE_FILENAME);
			        case 18:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR18_DNASE_FILENAME);
			        case 19:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR19_DNASE_FILENAME);
			        case 20:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR20_DNASE_FILENAME);
			        case 21:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR21_DNASE_FILENAME);
			        case 22:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR22_DNASE_FILENAME);
			        case 23:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRX_DNASE_FILENAME);
			        case 24:return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRY_DNASE_FILENAME);
				}
				
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
    }
	
	
			
	
	public static FileWriter getSortedDnaseFileWriter(int i,String outputFolder){
		try {
			
			switch (i) {
	            case 1:	return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR1_DNASE_FILENAME);
	            case 2: return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR2_DNASE_FILENAME);
	            case 3:	return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR3_DNASE_FILENAME);
	     		case 4: return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR4_DNASE_FILENAME);
	     		case 5:	return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR5_DNASE_FILENAME);
	            case 6:	return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR6_DNASE_FILENAME);
	            case 7: return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR7_DNASE_FILENAME);
	            case 8:	return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR8_DNASE_FILENAME);
	     	    case 9: return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR9_DNASE_FILENAME);
	     	    case 10:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR10_DNASE_FILENAME);
	     	    case 11:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR11_DNASE_FILENAME);
	     	    case 12:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR12_DNASE_FILENAME);
	            case 13:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR13_DNASE_FILENAME);
	            case 14:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR14_DNASE_FILENAME);
	            case 15:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR15_DNASE_FILENAME);
	            case 16:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR16_DNASE_FILENAME);
	            case 17:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR17_DNASE_FILENAME);
	            case 18:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR18_DNASE_FILENAME);
	            case 19:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR19_DNASE_FILENAME);
	            case 20:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR20_DNASE_FILENAME);
	            case 21:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR21_DNASE_FILENAME);
	            case 22:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR22_DNASE_FILENAME);
	            case 23:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHRX_DNASE_FILENAME);
	            case 24:return  FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHRY_DNASE_FILENAME);
			}
									
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
    }
	
	public static FileReader getUnsortedHistoneFileReader(int i,String outputFolder){
		try {
				switch (i) {
			        case 1:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR1_HISTONE_FILENAME);
			        case 2:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR2_HISTONE_FILENAME);			
			        case 3:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR3_HISTONE_FILENAME);			
			        case 4:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR4_HISTONE_FILENAME);			
			        case 5:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR5_HISTONE_FILENAME);			
			        case 6:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR6_HISTONE_FILENAME);			
			        case 7:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR7_HISTONE_FILENAME);			
			        case 8:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR8_HISTONE_FILENAME);			
			        case 9:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR9_HISTONE_FILENAME);			
			        case 10:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR10_HISTONE_FILENAME);			
			        case 11:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR11_HISTONE_FILENAME);			
			        case 12:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR12_HISTONE_FILENAME);			
			        case 13:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR13_HISTONE_FILENAME);			
			        case 14:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR14_HISTONE_FILENAME);			
			        case 15:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR15_HISTONE_FILENAME);			
			        case 16:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR16_HISTONE_FILENAME);			
			        case 17:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR17_HISTONE_FILENAME);			
			        case 18:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR18_HISTONE_FILENAME);			
			        case 19:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR19_HISTONE_FILENAME);			
			        case 20:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR20_HISTONE_FILENAME);			
			        case 21:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR21_HISTONE_FILENAME);			
			        case 22:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR22_HISTONE_FILENAME);			
			        case 23:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHRX_HISTONE_FILENAME);			
			        case 24:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHRY_HISTONE_FILENAME);			
					
				}
				
				
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
    }
	
	public static FileWriter getSortedHistoneFileWriter(int i,String outputFolder){
		try {
				switch (i) {
			        case 1:		return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR1_HISTONE_FILENAME);
			        case 2:		return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR2_HISTONE_FILENAME);
			        case 3:		return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR3_HISTONE_FILENAME);
			        case 4:		return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR4_HISTONE_FILENAME);
			        case 5:		return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR5_HISTONE_FILENAME);
			        case 6:		return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR6_HISTONE_FILENAME);
			        case 7:		return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR7_HISTONE_FILENAME);
			        case 8:		return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR8_HISTONE_FILENAME);
			        case 9:		return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR9_HISTONE_FILENAME);
			        case 10:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR10_HISTONE_FILENAME);
			        case 11:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR11_HISTONE_FILENAME);
			        case 12:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR12_HISTONE_FILENAME);
			        case 13:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR13_HISTONE_FILENAME);
			        case 14:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR14_HISTONE_FILENAME);
			        case 15:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR15_HISTONE_FILENAME);
			        case 16:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR16_HISTONE_FILENAME);
			        case 17:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR17_HISTONE_FILENAME);
			        case 18:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR18_HISTONE_FILENAME);
			        case 19:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR19_HISTONE_FILENAME);
			        case 20:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR20_HISTONE_FILENAME);
			        case 21:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR21_HISTONE_FILENAME);
			        case 22:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR22_HISTONE_FILENAME);
			        case 23:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHRX_HISTONE_FILENAME);
			        case 24:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHRY_HISTONE_FILENAME);
					       	
				}
				
				
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
    }
	
	//yeni
	public static FileReader getUnsortedTfbsFileReader(int i,String outputFolder){
		try {
				switch (i) {
			        case 1:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR1_TFBS_FILENAME);
			        case 2:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR2_TFBS_FILENAME);
			        case 3:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR3_TFBS_FILENAME);
			        case 4:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR4_TFBS_FILENAME);
			        case 5:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR5_TFBS_FILENAME);
			        case 6:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR6_TFBS_FILENAME);
			        case 7:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR7_TFBS_FILENAME);
			        case 8:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR8_TFBS_FILENAME);
			        case 9:		return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR9_TFBS_FILENAME);
			        case 10:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR10_TFBS_FILENAME);
			        case 11:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR11_TFBS_FILENAME);
			        case 12:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR12_TFBS_FILENAME);
			        case 13:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR13_TFBS_FILENAME);
			        case 14:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR14_TFBS_FILENAME);
			        case 15:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR15_TFBS_FILENAME);
			        case 16:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR16_TFBS_FILENAME);
			        case 17:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR17_TFBS_FILENAME);
			        case 18:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR18_TFBS_FILENAME);
			        case 19:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR19_TFBS_FILENAME);
			        case 20:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR20_TFBS_FILENAME);
			        case 21:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR21_TFBS_FILENAME);
			        case 22:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR22_TFBS_FILENAME);
			        case 23:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRX_TFBS_FILENAME);
			        case 24:	return FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRY_TFBS_FILENAME);							
				}
				
							
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
    }
	
	
	public static FileWriter getSortedTfbsFileWriter(int i,String outputFolder){
		try {
				switch (i) {
			        case 1:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR1_TFBS_FILENAME);
			        case 2:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR2_TFBS_FILENAME);
			        case 3:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR3_TFBS_FILENAME);
			        case 4:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR4_TFBS_FILENAME);
			        case 5:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR5_TFBS_FILENAME);
			        case 6:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR6_TFBS_FILENAME);
			        case 7:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR7_TFBS_FILENAME);
			        case 8:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR8_TFBS_FILENAME);
			        case 9:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR9_TFBS_FILENAME);
			        case 10:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR10_TFBS_FILENAME);
			        case 11:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR11_TFBS_FILENAME);
			        case 12:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR12_TFBS_FILENAME);
			        case 13:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR13_TFBS_FILENAME);
			        case 14:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR14_TFBS_FILENAME);
			        case 15:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR15_TFBS_FILENAME);
			        case 16:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR16_TFBS_FILENAME);
			        case 17:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR17_TFBS_FILENAME);
			        case 18:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR18_TFBS_FILENAME);
			        case 19:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR19_TFBS_FILENAME);
			        case 20:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR20_TFBS_FILENAME);
			        case 21:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR21_TFBS_FILENAME);
			        case 22:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR22_TFBS_FILENAME);
			        case 23:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHRX_TFBS_FILENAME);
			        case 24:	return FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHRY_TFBS_FILENAME);
			    			        					       	
				}
				
				
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
    }
	
	
		
	
}
