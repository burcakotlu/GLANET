/**
 * @author Burcak Otlu
 * Jul 26, 2013
 * 2:06:04 PM
 * 2013
 *
 * 
 */
package hg19;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Commons;

public class GRCh37Hg19Chromosome {
	
	public static void initializeChromosomeSizes(List<Integer> chromosomeSizes){
		for(int i =0; i<Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
			chromosomeSizes.add(i, 0);
		}
	}
	
	
	//3 Feb 2014
	public static void getHg19ChromosomeSizes(Map<String,Integer> chromosomeSizes, String inputFileName){
		String strLine = null;
		String chromName = null;
		int indexofFirstTab = 0;
		int chromSize = 0;
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		int numberofChromosomes = 0;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!= null && numberofChromosomes<Commons.NUMBER_OF_CHROMOSOMES_HG19){
				indexofFirstTab = strLine.indexOf('\t');
				
				if (indexofFirstTab >= 0){
					chromName = strLine.substring(0, indexofFirstTab);
					chromSize = Integer.valueOf(strLine.substring(indexofFirstTab+1));
				}
				
				chromosomeSizes.put(chromName, chromSize);	
				
				numberofChromosomes++;
				
			} // End of While	
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//3 Feb 2014
	
	public static void getHg19ChromosomeSizes(List<Integer> chromosomeSizes, String inputFileName){
		String strLine = null;
		String chromName = null;
		int indexofFirstTab = 0;
		int chromSize = 0;
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!= null){
				indexofFirstTab = strLine.indexOf('\t');
				
				if (indexofFirstTab >= 0){
					chromName = strLine.substring(0, indexofFirstTab);
					chromSize = Integer.valueOf(strLine.substring(indexofFirstTab+1));
				}
				
				if (Commons.CHROMOSOME1.equals(chromName)){
					chromosomeSizes.set(0, chromSize);
				}else if (Commons.CHROMOSOME2.equals(chromName)){
					chromosomeSizes.set(1, chromSize);
				}else if (Commons.CHROMOSOME3.equals(chromName)){
					chromosomeSizes.set(2, chromSize);
				}else if (Commons.CHROMOSOME4.equals(chromName)){
					chromosomeSizes.set(3, chromSize);
				}else if (Commons.CHROMOSOME5.equals(chromName)){
					chromosomeSizes.set(4, chromSize);
				}else if (Commons.CHROMOSOME6.equals(chromName)){
					chromosomeSizes.set(5, chromSize);
				}else if (Commons.CHROMOSOME7.equals(chromName)){
					chromosomeSizes.set(6, chromSize);
				}else if (Commons.CHROMOSOME8.equals(chromName)){
					chromosomeSizes.set(7, chromSize);
				}else if (Commons.CHROMOSOME9.equals(chromName)){
					chromosomeSizes.set(8, chromSize);
				}else if (Commons.CHROMOSOME10.equals(chromName)){
					chromosomeSizes.set(9, chromSize);
				}else if (Commons.CHROMOSOME11.equals(chromName)){
					chromosomeSizes.set(10, chromSize);
				}else if (Commons.CHROMOSOME12.equals(chromName)){
					chromosomeSizes.set(11, chromSize);
				}else if (Commons.CHROMOSOME13.equals(chromName)){
					chromosomeSizes.set(12, chromSize);
				}else if (Commons.CHROMOSOME14.equals(chromName)){
					chromosomeSizes.set(13, chromSize);
				}else if (Commons.CHROMOSOME15.equals(chromName)){
					chromosomeSizes.set(14, chromSize);
				}else if (Commons.CHROMOSOME16.equals(chromName)){
					chromosomeSizes.set(15, chromSize);
				}else if (Commons.CHROMOSOME17.equals(chromName)){
					chromosomeSizes.set(16, chromSize);
				}else if (Commons.CHROMOSOME18.equals(chromName)){
					chromosomeSizes.set(17, chromSize);
				}else if (Commons.CHROMOSOME19.equals(chromName)){
					chromosomeSizes.set(18, chromSize);
				}else if (Commons.CHROMOSOME20.equals(chromName)){
					chromosomeSizes.set(19, chromSize);
				}else if (Commons.CHROMOSOME21.equals(chromName)){
					chromosomeSizes.set(20, chromSize);
				}else if (Commons.CHROMOSOME22.equals(chromName)){
					chromosomeSizes.set(21, chromSize);
				}else if (Commons.CHROMOSOMEX.equals(chromName)){
					chromosomeSizes.set(22, chromSize);
				}else if (Commons.CHROMOSOMEY.equals(chromName)){
					chromosomeSizes.set(23, chromSize);
				}				
			} // End of While
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getChromosomeName(int i){
		String chrName = null; 
		
		switch(i){
			case 1:  chrName = Commons.CHROMOSOME1;
					break;
			case 2:  chrName = Commons.CHROMOSOME2;
			 		break;
			case 3:  chrName = Commons.CHROMOSOME3;
			 		break;
			case 4:  chrName = Commons.CHROMOSOME4;
			 		break;
			case 5:  chrName = Commons.CHROMOSOME5;
			 		break;
			case 6:  chrName = Commons.CHROMOSOME6;
			 		break;
			case 7:  chrName = Commons.CHROMOSOME7;
			 		break;
			case 8:  chrName = Commons.CHROMOSOME8;
			 		break;
			case 9:  chrName = Commons.CHROMOSOME9;
			 		break;
			case 10:  chrName = Commons.CHROMOSOME10;
			 		break;
			case 11:  chrName = Commons.CHROMOSOME11;
			 		break;
			case 12:  chrName = Commons.CHROMOSOME12;
			 		break;
			case 13:  chrName = Commons.CHROMOSOME13;
			 		break;
			case 14:  chrName = Commons.CHROMOSOME14;
			 		break;
			case 15:  chrName = Commons.CHROMOSOME15;
			 		break;
			case 16:  chrName = Commons.CHROMOSOME16;
			 		break;
			case 17:  chrName = Commons.CHROMOSOME17;
			 		break;
			case 18:  chrName = Commons.CHROMOSOME18;
			 		break;
			case 19:  chrName = Commons.CHROMOSOME19;
			 		break;
			case 20:  chrName = Commons.CHROMOSOME20;
					break;
			case 21:  chrName = Commons.CHROMOSOME21;
			 		break;
			case 22:  chrName = Commons.CHROMOSOME22;
			 		break;
			case 23:  chrName = Commons.CHROMOSOMEX;
					break;
			case 24:  chrName = Commons.CHROMOSOMEY;
			 		break;
		 
		}//End of switch
		
		return chrName;
	}
	
	public static int getHg19ChromsomeSize(List<Integer> hg19ChromosomeSizes,String chromName){
		
		if(chromName.equals(Commons.CHROMOSOME1)){
			return hg19ChromosomeSizes.get(0);
		}else if(chromName.equals(Commons.CHROMOSOME2)){
			return hg19ChromosomeSizes.get(1);
		}else if(chromName.equals(Commons.CHROMOSOME3)){
			return hg19ChromosomeSizes.get(2);
		}else if(chromName.equals(Commons.CHROMOSOME4)){
			return hg19ChromosomeSizes.get(3);
		}else if(chromName.equals(Commons.CHROMOSOME5)){
			return hg19ChromosomeSizes.get(4);
		}else if(chromName.equals(Commons.CHROMOSOME6)){
			return hg19ChromosomeSizes.get(5);
		}else if(chromName.equals(Commons.CHROMOSOME7)){
			return hg19ChromosomeSizes.get(6);
		}else if(chromName.equals(Commons.CHROMOSOME8)){
			return hg19ChromosomeSizes.get(7);
		}else if(chromName.equals(Commons.CHROMOSOME9)){
			return hg19ChromosomeSizes.get(8);
		}else if(chromName.equals(Commons.CHROMOSOME10)){
			return hg19ChromosomeSizes.get(9);
		}else if(chromName.equals(Commons.CHROMOSOME11)){
			return hg19ChromosomeSizes.get(10);
		}else if(chromName.equals(Commons.CHROMOSOME12)){
			return hg19ChromosomeSizes.get(11);
		}else if(chromName.equals(Commons.CHROMOSOME13)){
			return hg19ChromosomeSizes.get(12);
		}else if(chromName.equals(Commons.CHROMOSOME14)){
			return hg19ChromosomeSizes.get(13);
		}else if(chromName.equals(Commons.CHROMOSOME15)){
			return hg19ChromosomeSizes.get(14);
		}else if(chromName.equals(Commons.CHROMOSOME16)){
			return hg19ChromosomeSizes.get(15);
		}else if(chromName.equals(Commons.CHROMOSOME17)){
			return hg19ChromosomeSizes.get(16);
		}else if(chromName.equals(Commons.CHROMOSOME18)){
			return hg19ChromosomeSizes.get(17);
		}else if(chromName.equals(Commons.CHROMOSOME19)){
			return hg19ChromosomeSizes.get(18);
		}else if(chromName.equals(Commons.CHROMOSOME20)){
			return hg19ChromosomeSizes.get(19);
		}else if(chromName.equals(Commons.CHROMOSOME21)){
			return hg19ChromosomeSizes.get(20);
		}else if(chromName.equals(Commons.CHROMOSOME22)){
			return hg19ChromosomeSizes.get(21);
		}else if(chromName.equals(Commons.CHROMOSOMEX)){
			return hg19ChromosomeSizes.get(22);
		}else if(chromName.equals(Commons.CHROMOSOMEY)){
			return hg19ChromosomeSizes.get(23);
		}
		return 0;
	}
	

	public static void  fillDnaSequenceArrayfromFastaFile(Integer chromSize,String inputFileName, char[] dnaSequenceArray){
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		int i;
		int nthBase =0;
		String strLine;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//skip first informative line of fasta file
			strLine=bufferedReader.readLine();
			
			//check whether fasta file starts with > greater character
			if (!strLine.startsWith(">")){
				System.out.println("Fasta file does not start with > character.");
			}
			
			
			while((i = bufferedReader.read())!=-1 && nthBase<chromSize){
				
					if (Character.isAlphabetic(i)){
						dnaSequenceArray[nthBase++] = Character.toUpperCase((char)i);
					}
									
			}//end of while
			
			
			System.out.println("nthBase must be written once: " + nthBase + " dnaSequenceArray construction has ended.");
			
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
	

	public static void 	fillDnaSequenceArray(String chromName,Integer chromSize, char[] dnaSequenceArray){
		
		switch(chromName){
		
			case Commons.CHROMOSOME1:   fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR1_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME2:   fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR2_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME3:   fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR3_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME4:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR4_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME5:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR5_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME6:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR6_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME7:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR7_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME8:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR8_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME9:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR9_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME10:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR10_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME11:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR11_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME12:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR12_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME13:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR13_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME14:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR14_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME15:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR15_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME16:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR16_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME17:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR17_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME18:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR18_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME19:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR19_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME20:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR20_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME21:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR21_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOME22:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR22_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOMEX:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHRX_FASTA_FILE,dnaSequenceArray);
										break;
			case Commons.CHROMOSOMEY:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHRY_FASTA_FILE,dnaSequenceArray);
										break;			
		}
		
	}
	
	public static char[] fillHsapiensHg19Chromosome(String chromNumber, int chromSize){
		
		char[] chromDNASequence = new char[chromSize];
		fillDnaSequenceArray(chromNumber,chromSize,chromDNASequence);
		return chromDNASequence;
		
	}
	

	
	public static void fillHsapiensHg19Chromosomes(Map<String, Integer> hg19ChromosomeSizes, Map<String,char[]>chromosomeBasedDNASequences){
		String chromName;
		Integer chromSize;
	
		for(Map.Entry<String, Integer> entry : hg19ChromosomeSizes.entrySet()){
			
			chromName = entry.getKey();
			chromSize = entry.getValue();
			
			char[] dnaSequenceArray = new char[chromSize];
			
			fillDnaSequenceArray(chromName,chromSize,dnaSequenceArray);
			
			chromosomeBasedDNASequences.put(chromName, dnaSequenceArray);
		}
	}
	
	
	public static char[] getHsapiensHg19ChromosomeDNASequence(String chromNumber){
		//for testing purposes
		Map<String, Integer> hg19ChromosomeSizes	= new HashMap<String,Integer>();
		
    	//get the hg19 chromosome sizes
    	GRCh37Hg19Chromosome.getHg19ChromosomeSizes(hg19ChromosomeSizes, Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
    	
    	int chromSize  = hg19ChromosomeSizes.get(chromNumber);
    	   	
    	return GRCh37Hg19Chromosome.fillHsapiensHg19Chromosome(chromNumber,chromSize);
		
			
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		// Testing purposes	
		String chromNumber = Commons.CHROMOSOME1;
		char[] chr1AllSequence = getHsapiensHg19ChromosomeDNASequence(chromNumber);
		
		for(int i=0; i<=10; i++){
			System.out.print(chr1AllSequence[97533597+i]);
		}
		System.out.println("\n");


	}

}
