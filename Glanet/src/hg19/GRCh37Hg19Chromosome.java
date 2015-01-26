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

import ui.GlanetRunner;

import common.Commons;

import enumtypes.ChromosomeName;

public class GRCh37Hg19Chromosome {
	
	public static void initializeChromosomeSizes(List<Integer> chromosomeSizes){
		for(int i =0; i<Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
			chromosomeSizes.add(i, 0);
		}
	}
	
	
	//3 Feb 2014
	public static void getHg19ChromosomeSizes(Map<ChromosomeName,Integer> chromosomeSizes, String inputFileName){
		String strLine = null;
		ChromosomeName chromName = null;
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
					chromName = ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab));
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
	
	public static void getHg19ChromosomeSizes(List<Integer> chromosomeSizes, String dataFolder, String inputFileName){
		String strLine = null;
		ChromosomeName chromName = null;
		int indexofFirstTab = 0;
		int chromSize = 0;
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!= null){
				indexofFirstTab = strLine.indexOf('\t');
				
				if (indexofFirstTab >= 0){
					chromName = ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab));
					chromSize = Integer.valueOf(strLine.substring(indexofFirstTab+1));
				}
				
				if(chromName!=null){
					if (chromName.isCHROMOSOME1()){
						chromosomeSizes.set(0, chromSize);
					}else if (chromName.isCHROMOSOME2()){
						chromosomeSizes.set(1, chromSize);
					}else if (chromName.isCHROMOSOME3()){
						chromosomeSizes.set(2, chromSize);
					}else if (chromName.isCHROMOSOME4()){
						chromosomeSizes.set(3, chromSize);
					}else if (chromName.isCHROMOSOME5()){
						chromosomeSizes.set(4, chromSize);
					}else if (chromName.isCHROMOSOME6()){
						chromosomeSizes.set(5, chromSize);
					}else if (chromName.isCHROMOSOME7()){
						chromosomeSizes.set(6, chromSize);
					}else if (chromName.isCHROMOSOME8()){
						chromosomeSizes.set(7, chromSize);
					}else if (chromName.isCHROMOSOME9()){
						chromosomeSizes.set(8, chromSize);
					}else if (chromName.isCHROMOSOME10()){
						chromosomeSizes.set(9, chromSize);
					}else if (chromName.isCHROMOSOME11()){
						chromosomeSizes.set(10, chromSize);
					}else if (chromName.isCHROMOSOME12()){
						chromosomeSizes.set(11, chromSize);
					}else if (chromName.isCHROMOSOME13()){
						chromosomeSizes.set(12, chromSize);
					}else if (chromName.isCHROMOSOME14()){
						chromosomeSizes.set(13, chromSize);
					}else if (chromName.isCHROMOSOME15()){
						chromosomeSizes.set(14, chromSize);
					}else if (chromName.isCHROMOSOME16()){
						chromosomeSizes.set(15, chromSize);
					}else if (chromName.isCHROMOSOME17()){
						chromosomeSizes.set(16, chromSize);
					}else if (chromName.isCHROMOSOME18()){
						chromosomeSizes.set(17, chromSize);
					}else if (chromName.isCHROMOSOME19()){
						chromosomeSizes.set(18, chromSize);
					}else if (chromName.isCHROMOSOME20()){
						chromosomeSizes.set(19, chromSize);
					}else if (chromName.isCHROMOSOME21()){
						chromosomeSizes.set(20, chromSize);
					}else if (chromName.isCHROMOSOME22()){
						chromosomeSizes.set(21, chromSize);
					}else if (chromName.isCHROMOSOMEX()){
						chromosomeSizes.set(22, chromSize);
					}else if (chromName.isCHROMOSOMEY()){
						chromosomeSizes.set(23, chromSize);
					}				
				}
				
			} // End of While
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ChromosomeName getChromosomeName(int i){
		ChromosomeName chrName = null; 
		
		switch(i){
			case 1:  chrName = ChromosomeName.CHROMOSOME1;
					break;
			case 2:  chrName = ChromosomeName.CHROMOSOME2;
			 		break;
			case 3:  chrName = ChromosomeName.CHROMOSOME3;
			 		break;
			case 4:  chrName = ChromosomeName.CHROMOSOME4;
			 		break;
			case 5:  chrName = ChromosomeName.CHROMOSOME5;
			 		break;
			case 6:  chrName = ChromosomeName.CHROMOSOME6;
			 		break;
			case 7:  chrName = ChromosomeName.CHROMOSOME7;
			 		break;
			case 8:  chrName = ChromosomeName.CHROMOSOME8;
			 		break;
			case 9:  chrName = ChromosomeName.CHROMOSOME9;
			 		break;
			case 10:  chrName = ChromosomeName.CHROMOSOME10;
			 		break;
			case 11:  chrName = ChromosomeName.CHROMOSOME11;
			 		break;
			case 12:  chrName = ChromosomeName.CHROMOSOME12;
			 		break;
			case 13:  chrName = ChromosomeName.CHROMOSOME13;
			 		break;
			case 14:  chrName = ChromosomeName.CHROMOSOME14;
			 		break;
			case 15:  chrName = ChromosomeName.CHROMOSOME15;
			 		break;
			case 16:  chrName = ChromosomeName.CHROMOSOME16;
			 		break;
			case 17:  chrName = ChromosomeName.CHROMOSOME17;
			 		break;
			case 18:  chrName = ChromosomeName.CHROMOSOME18;
			 		break;
			case 19:  chrName = ChromosomeName.CHROMOSOME19;
			 		break;
			case 20:  chrName = ChromosomeName.CHROMOSOME20;
					break;
			case 21:  chrName = ChromosomeName.CHROMOSOME21;
			 		break;
			case 22:  chrName = ChromosomeName.CHROMOSOME22;
			 		break;
			case 23:  chrName = ChromosomeName.CHROMOSOMEX;
					break;
			case 24:  chrName = ChromosomeName.CHROMOSOMEY;
			 		break;
		 
		}//End of switch
		
		return chrName;
	}
	
	public static int getHg19ChromsomeSize(List<Integer> hg19ChromosomeSizes,ChromosomeName chromName){
		
		if(chromName.isCHROMOSOME1()){
			return hg19ChromosomeSizes.get(0);
		}else if(chromName.isCHROMOSOME2()){
			return hg19ChromosomeSizes.get(1);
		}else if(chromName.isCHROMOSOME3()){
			return hg19ChromosomeSizes.get(2);
		}else if(chromName.isCHROMOSOME4()){
			return hg19ChromosomeSizes.get(3);
		}else if(chromName.isCHROMOSOME5()){
			return hg19ChromosomeSizes.get(4);
		}else if(chromName.isCHROMOSOME6()){
			return hg19ChromosomeSizes.get(5);
		}else if(chromName.isCHROMOSOME7()){
			return hg19ChromosomeSizes.get(6);
		}else if(chromName.isCHROMOSOME8()){
			return hg19ChromosomeSizes.get(7);
		}else if(chromName.isCHROMOSOME9()){
			return hg19ChromosomeSizes.get(8);
		}else if(chromName.isCHROMOSOME10()){
			return hg19ChromosomeSizes.get(9);
		}else if(chromName.isCHROMOSOME11()){
			return hg19ChromosomeSizes.get(10);
		}else if(chromName.isCHROMOSOME12()){
			return hg19ChromosomeSizes.get(11);
		}else if(chromName.isCHROMOSOME13()){
			return hg19ChromosomeSizes.get(12);
		}else if(chromName.isCHROMOSOME14()){
			return hg19ChromosomeSizes.get(13);
		}else if(chromName.isCHROMOSOME15()){
			return hg19ChromosomeSizes.get(14);
		}else if(chromName.isCHROMOSOME16()){
			return hg19ChromosomeSizes.get(15);
		}else if(chromName.isCHROMOSOME17()){
			return hg19ChromosomeSizes.get(16);
		}else if(chromName.isCHROMOSOME18()){
			return hg19ChromosomeSizes.get(17);
		}else if(chromName.isCHROMOSOME19()){
			return hg19ChromosomeSizes.get(18);
		}else if(chromName.isCHROMOSOME20()){
			return hg19ChromosomeSizes.get(19);
		}else if(chromName.isCHROMOSOME21()){
			return hg19ChromosomeSizes.get(20);
		}else if(chromName.isCHROMOSOME22()){
			return hg19ChromosomeSizes.get(21);
		}else if(chromName.isCHROMOSOMEX()){
			return hg19ChromosomeSizes.get(22);
		}else if(chromName.isCHROMOSOMEY()){
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
				GlanetRunner.appendLog("Fasta file does not start with > character.");
			}
			
			
			while((i = bufferedReader.read())!=-1 && nthBase<chromSize){
				
					if (Character.isAlphabetic(i)){
						dnaSequenceArray[nthBase++] = Character.toUpperCase((char)i);
					}
									
			}//end of while
			
			
			GlanetRunner.appendLog("nthBase must be written once: " + nthBase + " dnaSequenceArray construction has ended.");
			
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
	

	public static void 	fillDnaSequenceArray(ChromosomeName chromName,Integer chromSize, char[] dnaSequenceArray){
		
		switch(chromName){
		
			case CHROMOSOME1:   fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR1_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME2:   fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR2_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME3:   fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR3_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME4:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR4_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME5:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR5_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME6:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR6_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME7:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR7_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME8:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR8_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME9:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR9_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME10:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR10_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME11:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR11_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME12:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR12_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME13:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR13_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME14:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR14_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME15:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR15_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME16:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR16_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME17:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR17_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME18:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR18_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME19:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR19_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME20:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR20_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME21:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR21_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOME22:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHR22_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOMEX:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHRX_FASTA_FILE,dnaSequenceArray);
										break;
			case CHROMOSOMEY:  fillDnaSequenceArrayfromFastaFile(chromSize,Commons.GC_HG19_CHRY_FASTA_FILE,dnaSequenceArray);
										break;
		}
		
	}
	
	public static char[] fillHsapiensHg19Chromosome(ChromosomeName chromNumber, int chromSize){
		
		char[] chromDNASequence = new char[chromSize];
		fillDnaSequenceArray(chromNumber,chromSize,chromDNASequence);
		return chromDNASequence;
		
	}
	

	
	public static void fillHsapiensHg19Chromosomes(Map<ChromosomeName, Integer> hg19ChromosomeSizes, Map<ChromosomeName,char[]>chromosomeBasedDNASequences){
		ChromosomeName chromName;
		Integer chromSize;
	
		for(Map.Entry<ChromosomeName, Integer> entry : hg19ChromosomeSizes.entrySet()){
			
			chromName = entry.getKey();
			chromSize = entry.getValue();
			
			char[] dnaSequenceArray = new char[chromSize];
			
			fillDnaSequenceArray(chromName,chromSize,dnaSequenceArray);
			
			chromosomeBasedDNASequences.put(chromName, dnaSequenceArray);
		}
	}
	
	
	public static char[] getHsapiensHg19ChromosomeDNASequence(ChromosomeName chromNumber){
		//for testing purposes
		Map<ChromosomeName, Integer> hg19ChromosomeSizes	= new HashMap<ChromosomeName,Integer>();
		
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
		ChromosomeName chromNumber = ChromosomeName.CHROMOSOME1;
		char[] chr1AllSequence = getHsapiensHg19ChromosomeDNASequence(chromNumber);
		
		for(int i=0; i<=10; i++){
			System.out.print(chr1AllSequence[97533597+i]);
		}
		GlanetRunner.appendLog(System.getProperty("line.separator"));


	}

}
