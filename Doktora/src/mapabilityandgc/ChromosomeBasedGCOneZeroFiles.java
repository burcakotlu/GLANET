/**
 * @author Burcak Otlu
 * Aug 5, 2013
 * 12:02:51 AM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import common.Commons;

public class ChromosomeBasedGCOneZeroFiles {
	
	/*
	 * This method read hg19 chromosomes nucleic acid sequences from files in fast format
	 * In fasta files first line is description line and starts with > greater symbol
	 * The rest of the fasta file is the nucleic sequences of the hg19 chromosomes
	 * N is for the unknown nucleic acid
	 * 
	 */
	public void readHg19ChromosomeFastaFilesCreateChromosomeGCFile(int chromNumber, String inputFileName, String outputFileName){
		FileReader fileReader ;
		BufferedReader bufferedReader;
		
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		String strLine;
		char ch;
		char[] cbuf = new char[1000];
		int numberofCharactersRead;
		int numberofBases = 0;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//skip first informative line of fasta file
			strLine=bufferedReader.readLine();
			
			//check whether fasta file starts with > greater character
			if (!strLine.startsWith(">")){
				System.out.println("Fasta file does not start with > character.");
			}
			
			//end of line characters remain unchanged
			while((numberofCharactersRead = bufferedReader.read(cbuf))!=-1){
				for(int i =0; i<numberofCharactersRead ; i++){
					ch = cbuf[i];
			
					if(	((ch == Commons.NUCLEIC_ACID_UPPER_CASE_A) || (ch==Commons.NUCLEIC_ACID_LOWER_CASE_A)) ||
					    ((ch == Commons.NUCLEIC_ACID_UPPER_CASE_T) || (ch==Commons.NUCLEIC_ACID_LOWER_CASE_T))	||
					    ((ch == Commons.NUCLEIC_ACID_UPPER_CASE_N) || (ch==Commons.NUCLEIC_ACID_LOWER_CASE_N))){
						 	cbuf[i] = '0';
						 	numberofBases++;
							
					}else if (	((ch == Commons.NUCLEIC_ACID_UPPER_CASE_G) || (ch==Commons.NUCLEIC_ACID_LOWER_CASE_G)) ||
						    	((ch == Commons.NUCLEIC_ACID_UPPER_CASE_C) || (ch==Commons.NUCLEIC_ACID_LOWER_CASE_C))){
								cbuf[i] = '1';
								numberofBases++;
					
					}
						
				}//end of for
				bufferedWriter.write(cbuf);
			}//End of while
			
			//Is there any chars remained in the cbuf?
			//No it reads 1000 chars and in the last loop remaining characters till the end of stream
			//At the end it returns -1
			
			//for example 
			// 1000 1000 685 -1
			
			//numberofBases complies with the size of the hg19 chromosomes
			
			System.out.println("chr"+chromNumber + " "+ numberofBases);
			
			bufferedWriter.close();
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void readAllHg19ChromosomeFastaFilesCreateAllChromosomeGCFile(){
		
		for(int i = 1; i<=Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
			switch(i){
				case 1: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR1_FASTA_FILE,Commons.GC_HG19_CHR1_ONEZERO_FILE);			
						break;
				case 2: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR2_FASTA_FILE,Commons.GC_HG19_CHR2_ONEZERO_FILE);			
						break;
				case 3: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR3_FASTA_FILE,Commons.GC_HG19_CHR3_ONEZERO_FILE);			
						break;
				case 4: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR4_FASTA_FILE,Commons.GC_HG19_CHR4_ONEZERO_FILE);			
						break;
				case 5: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR5_FASTA_FILE,Commons.GC_HG19_CHR5_ONEZERO_FILE);			
						break;
				case 6: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR6_FASTA_FILE,Commons.GC_HG19_CHR6_ONEZERO_FILE);			
						break;
				case 7: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR7_FASTA_FILE,Commons.GC_HG19_CHR7_ONEZERO_FILE);			
						break;
				case 8: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR8_FASTA_FILE,Commons.GC_HG19_CHR8_ONEZERO_FILE);			
						break;
				case 9: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR9_FASTA_FILE,Commons.GC_HG19_CHR9_ONEZERO_FILE);			
						break;
				case 10: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR10_FASTA_FILE,Commons.GC_HG19_CHR10_ONEZERO_FILE);			
						break;
				case 11: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR11_FASTA_FILE,Commons.GC_HG19_CHR11_ONEZERO_FILE);			
						break;
				case 12: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR12_FASTA_FILE,Commons.GC_HG19_CHR12_ONEZERO_FILE);			
						break;
				case 13: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR13_FASTA_FILE,Commons.GC_HG19_CHR13_ONEZERO_FILE);			
						break;
				case 14: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR14_FASTA_FILE,Commons.GC_HG19_CHR14_ONEZERO_FILE);			
						break;
				case 15: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR15_FASTA_FILE,Commons.GC_HG19_CHR15_ONEZERO_FILE);			
						break;
				case 16: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR16_FASTA_FILE,Commons.GC_HG19_CHR16_ONEZERO_FILE);			
						break;
				case 17: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR17_FASTA_FILE,Commons.GC_HG19_CHR17_ONEZERO_FILE);			
						break;
				case 18: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR18_FASTA_FILE,Commons.GC_HG19_CHR18_ONEZERO_FILE);			
						break;
				case 19: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR19_FASTA_FILE,Commons.GC_HG19_CHR19_ONEZERO_FILE);			
						break;
				case 20: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR20_FASTA_FILE,Commons.GC_HG19_CHR20_ONEZERO_FILE);			
						break;
				case 21: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR21_FASTA_FILE,Commons.GC_HG19_CHR21_ONEZERO_FILE);			
						break;
				case 22: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHR22_FASTA_FILE,Commons.GC_HG19_CHR22_ONEZERO_FILE);			
						break;
				case 23: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHRX_FASTA_FILE,Commons.GC_HG19_CHRX_ONEZERO_FILE);			
						break;
				case 24: readHg19ChromosomeFastaFilesCreateChromosomeGCFile(i,Commons.GC_HG19_CHRY_FASTA_FILE,Commons.GC_HG19_CHRY_ONEZERO_FILE);			
						break;
		
			}
			
		}
		
	}

	public static void main(String[] args) {
	
		ChromosomeBasedGCOneZeroFiles createGCFiles = new ChromosomeBasedGCOneZeroFiles();
		
		createGCFiles.readAllHg19ChromosomeFastaFilesCreateAllChromosomeGCFile();
	}
}