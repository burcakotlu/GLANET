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

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;

/**
 * @author burcakotlu
 *
 */
public class ChromosomeBasedFilesandOperations {
	
	
	

	
	
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
	
	


	public static void openUnsortedChromosomeBasedRefSeqGeneFileWriters(String dataFolder,List<BufferedWriter> bufferedWriterList){
		
		try {
			FileWriter  fileWriter1 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR1_REFSEQ_GENES);
			FileWriter  fileWriter2 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR2_REFSEQ_GENES);
			FileWriter  fileWriter3 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR3_REFSEQ_GENES);
			FileWriter  fileWriter4 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR4_REFSEQ_GENES);
			FileWriter  fileWriter5 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR5_REFSEQ_GENES);
			FileWriter  fileWriter6 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR6_REFSEQ_GENES);
			FileWriter  fileWriter7 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR7_REFSEQ_GENES);
			FileWriter  fileWriter8 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR8_REFSEQ_GENES);
			FileWriter  fileWriter9 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR9_REFSEQ_GENES);
			FileWriter  fileWriter10 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR10_REFSEQ_GENES);
			FileWriter  fileWriter11 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR11_REFSEQ_GENES);
			FileWriter  fileWriter12 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR12_REFSEQ_GENES);
			FileWriter  fileWriter13 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR13_REFSEQ_GENES);
			FileWriter  fileWriter14 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR14_REFSEQ_GENES);
			FileWriter  fileWriter15 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR15_REFSEQ_GENES);
			FileWriter  fileWriter16 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR16_REFSEQ_GENES);
			FileWriter  fileWriter17 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR17_REFSEQ_GENES);
			FileWriter  fileWriter18 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR18_REFSEQ_GENES);
			FileWriter  fileWriter19 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR19_REFSEQ_GENES);
			FileWriter  fileWriter20 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR20_REFSEQ_GENES);
			FileWriter  fileWriter21 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR21_REFSEQ_GENES);
			FileWriter  fileWriter22 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHR22_REFSEQ_GENES);
			FileWriter  fileWriterX = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHRX_REFSEQ_GENES);
			FileWriter  fileWriterY = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME , Commons.UNSORTED_CHRY_REFSEQ_GENES);
				
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
	
	public static void openUnsortedChromosomeBasedRefSeqGeneFileReaders(String dataFolder,List<BufferedReader> bufferedReaderList){
		
		try {
			FileReader  fileReader1 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR1_REFSEQ_GENES);
			FileReader  fileReader2 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR2_REFSEQ_GENES);
			FileReader  fileReader3 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR3_REFSEQ_GENES);
			FileReader  fileReader4 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR4_REFSEQ_GENES);
			FileReader  fileReader5 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR5_REFSEQ_GENES);
			FileReader  fileReader6 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR6_REFSEQ_GENES);
			FileReader  fileReader7 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR7_REFSEQ_GENES);
			FileReader  fileReader8 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR8_REFSEQ_GENES);
			FileReader  fileReader9 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR9_REFSEQ_GENES);
			FileReader  fileReader10 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR10_REFSEQ_GENES);
			FileReader  fileReader11 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR11_REFSEQ_GENES);
			FileReader  fileReader12 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR12_REFSEQ_GENES);
			FileReader  fileReader13 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR13_REFSEQ_GENES);
			FileReader  fileReader14 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR14_REFSEQ_GENES);
			FileReader  fileReader15 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR15_REFSEQ_GENES);
			FileReader  fileReader16 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR16_REFSEQ_GENES);
			FileReader  fileReader17 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR17_REFSEQ_GENES);
			FileReader  fileReader18 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR18_REFSEQ_GENES);
			FileReader  fileReader19 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR19_REFSEQ_GENES);
			FileReader  fileReader20 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR20_REFSEQ_GENES);
			FileReader  fileReader21 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR21_REFSEQ_GENES);
			FileReader  fileReader22 = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHR22_REFSEQ_GENES);
			FileReader  fileReaderX = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHRX_REFSEQ_GENES);
			FileReader  fileReaderY = FileOperations.createFileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME ,Commons.UNSORTED_CHRY_REFSEQ_GENES);
			
						
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
	
	public static void openSortedChromosomeBasedRefSeqGeneFiles(String dataFolder,List<BufferedWriter> bufferedWriterList){
		try {
			FileWriter  fileWriter1 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR1_REFSEQ_GENES);
			FileWriter  fileWriter2 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR2_REFSEQ_GENES);
			FileWriter  fileWriter3 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR3_REFSEQ_GENES);
			FileWriter  fileWriter4 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR4_REFSEQ_GENES);
			FileWriter  fileWriter5 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR5_REFSEQ_GENES);
			FileWriter  fileWriter6 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR6_REFSEQ_GENES);
			FileWriter  fileWriter7 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR7_REFSEQ_GENES);
			FileWriter  fileWriter8 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR8_REFSEQ_GENES);
			FileWriter  fileWriter9 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR9_REFSEQ_GENES);
			FileWriter  fileWriter10 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR10_REFSEQ_GENES);
			FileWriter  fileWriter11 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR11_REFSEQ_GENES);
			FileWriter  fileWriter12 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR12_REFSEQ_GENES);
			FileWriter  fileWriter13 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR13_REFSEQ_GENES);
			FileWriter  fileWriter14 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR14_REFSEQ_GENES);
			FileWriter  fileWriter15 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR15_REFSEQ_GENES);
			FileWriter  fileWriter16 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR16_REFSEQ_GENES);
			FileWriter  fileWriter17 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR17_REFSEQ_GENES);
			FileWriter  fileWriter18 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR18_REFSEQ_GENES);
			FileWriter  fileWriter19 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR19_REFSEQ_GENES);
			FileWriter  fileWriter20 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR20_REFSEQ_GENES);
			FileWriter  fileWriter21 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR21_REFSEQ_GENES);
			FileWriter  fileWriter22 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHR22_REFSEQ_GENES);
			FileWriter  fileWriterX = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHRX_REFSEQ_GENES);
			FileWriter  fileWriterY = FileOperations.createFileWriter(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME, Commons.SORTED_CHRY_REFSEQ_GENES);
				
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
	
	
	//@todo
	public static FileReader getUnsortedRefSeqGenesFileReaderWithNumbers(String dataFolder,ChromosomeName chromName){
		
		FileReader fileReader = null;
	
		try {
			
			if (ChromosomeName.CHROMOSOME1.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR1_REFSEQ_GENES_WITH_NUMBERS);								
			} else if (ChromosomeName.CHROMOSOME2.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR2_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME3.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR3_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME4.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR4_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME5.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR5_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME6.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR6_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME7.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR7_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME8.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR8_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME9.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR9_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME10.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR10_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME11.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR11_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME12.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR12_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME13.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR13_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME14.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR14_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME15.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR15_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME16.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR16_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME17.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR17_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME18.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR18_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME19.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR19_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME20.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR20_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME21.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR21_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOME22.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR22_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOMEX.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHRX_REFSEQ_GENES_WITH_NUMBERS);				
			} else if (ChromosomeName.CHROMOSOMEY.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHRY_REFSEQ_GENES_WITH_NUMBERS);				
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileReader;
	}
	//@todo
	
	
	public static FileReader getUnsortedRefSeqGenesFileReader(String dataFolder,ChromosomeName chromName){
				
		FileReader fileReader = null;
	
		try {
			
			if (ChromosomeName.CHROMOSOME1.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR1_REFSEQ_GENES);								
			} else if (ChromosomeName.CHROMOSOME2.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR2_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME3.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR3_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME4.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR4_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME5.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR5_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME6.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR6_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME7.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR7_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME8.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR8_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME9.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR9_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME10.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR10_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME11.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR11_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME12.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR12_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME13.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR13_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME14.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR14_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME15.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR15_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME16.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR16_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME17.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR17_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME18.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR18_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME19.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR19_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME20.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR20_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME21.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR21_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOME22.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR22_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOMEX.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHRX_REFSEQ_GENES);				
			} else if (ChromosomeName.CHROMOSOMEY.equals(chromName)){
				fileReader = new FileReader(dataFolder + Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHRY_REFSEQ_GENES);				
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileReader;
	}
	
	
	public static FileReader getSortedRefSeqGenesFileReader(ChromosomeName chromName){
		
		FileReader fileReader = null;
	
		try {
			
			if (chromName.isCHROMOSOME1()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR1_REFSEQ_GENES);								
			} else if (chromName.isCHROMOSOME2()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR2_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME3()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR3_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME4()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR4_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME5()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR5_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME6()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR6_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME7()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR7_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME8()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR8_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME9()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR9_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME10()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR10_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME11()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR11_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME12()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR12_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME13()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR13_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME14()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR14_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME15()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR15_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME16()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR16_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME17()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR17_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME18()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR18_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME19()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR19_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME20()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR20_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME21()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR21_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOME22()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR22_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOMEX()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHRX_REFSEQ_GENES);				
			} else if (chromName.isCHROMOSOMEY()){
				fileReader = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHRY_REFSEQ_GENES);				
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileReader;
	}
	
	public static BufferedWriter getBufferedWriter(ChromosomeName chromName,List<BufferedWriter> bufferedWriterList){
		BufferedWriter bufferedWriter = null;
		
		if (chromName.isCHROMOSOME1()){
			bufferedWriter = bufferedWriterList.get(0);
		}else if (chromName.isCHROMOSOME2()){
			bufferedWriter = bufferedWriterList.get(1);			
		}else if (chromName.isCHROMOSOME3()){
			bufferedWriter = bufferedWriterList.get(2);			
		}else if (chromName.isCHROMOSOME4()){
			bufferedWriter = bufferedWriterList.get(3);			
		}else if (chromName.isCHROMOSOME5()){
			bufferedWriter = bufferedWriterList.get(4);			
		}else if (chromName.isCHROMOSOME6()){
			bufferedWriter = bufferedWriterList.get(5);			
		}else if (chromName.isCHROMOSOME7()){
			bufferedWriter = bufferedWriterList.get(6);			
		}else if (chromName.isCHROMOSOME8()){
			bufferedWriter = bufferedWriterList.get(7);			
		}else if (chromName.isCHROMOSOME9()){
			bufferedWriter = bufferedWriterList.get(8);			
		}else if (chromName.isCHROMOSOME10()){
			bufferedWriter = bufferedWriterList.get(9);			
		}else if (chromName.isCHROMOSOME11()){
			bufferedWriter = bufferedWriterList.get(10);			
		}else if (chromName.isCHROMOSOME12()){
			bufferedWriter = bufferedWriterList.get(11);			
		}else if (chromName.isCHROMOSOME13()){
			bufferedWriter = bufferedWriterList.get(12);			
		}else if (chromName.isCHROMOSOME14()){
			bufferedWriter = bufferedWriterList.get(13);			
		}else if (chromName.isCHROMOSOME15()){
			bufferedWriter = bufferedWriterList.get(14);			
		}else if (chromName.isCHROMOSOME16()){
			bufferedWriter = bufferedWriterList.get(15);			
		}else if (chromName.isCHROMOSOME17()){
			bufferedWriter = bufferedWriterList.get(16);			
		}else if (chromName.isCHROMOSOME18()){
			bufferedWriter = bufferedWriterList.get(17);			
		}else if (chromName.isCHROMOSOME19()){
			bufferedWriter = bufferedWriterList.get(18);			
		}else if (chromName.isCHROMOSOME20()){
			bufferedWriter = bufferedWriterList.get(19);			
		}else if (chromName.isCHROMOSOME21()){
			bufferedWriter = bufferedWriterList.get(20);			
		}else if (chromName.isCHROMOSOME22()){
			bufferedWriter = bufferedWriterList.get(21);			
		}else if (chromName.isCHROMOSOMEX()){
			bufferedWriter = bufferedWriterList.get(22);			
		}else if (chromName.isCHROMOSOMEY()){
			bufferedWriter = bufferedWriterList.get(23);			
		}
		
		return bufferedWriter;
	}
	
	

	
		
	
}
