/*
 * This program creates unsorted and sorted chromosome based refseq genes files.
 * For sorting it uses List.sort method.
 * 
 * Refseq genes data are read from hg19_refseq_gens.txt
 * Each refseq gene is augmented with gene id by using human_refseq2gene.txt
 * 
 * human_refseq2gene.txt is output of src\ncbi\HumanRefSeq2Gene.java
 * 
 */

package create.ucscgenome;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.Commons;


public class CreateIntervalFileUsingUCSCGenomeUsingCollectionsSort {
	
//	global variables
	
	public void getRefSeqGeneData(String strLine,RefSeqGene refSeqGene){
		
		String refSeqGeneName;
		String chromName;
		char strand;
		int txStart;
		int txEnd;
		int cdsStart;
		int cdsEnd;
		int exonCounts;
		String exonStarts;
		String exonEnds;
		String alternateGeneName;
		 
		
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
		
		indexofFirstTab = strLine.indexOf('\t');
		indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
		indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
		indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
		indexofFifthTab = strLine.indexOf('\t', indexofFourthTab+1);
		indexofSixthTab = strLine.indexOf('\t', indexofFifthTab+1);
		indexofSeventhTab = strLine.indexOf('\t', indexofSixthTab+1);
		indexofEighthTab = strLine.indexOf('\t', indexofSeventhTab+1);
		indexofNinethTab = strLine.indexOf('\t', indexofEighthTab+1);
		indexofTenthTab = strLine.indexOf('\t', indexofNinethTab+1);
		indexofEleventhTab = strLine.indexOf('\t', indexofTenthTab+1);
		indexofTwelfthTab = strLine.indexOf('\t', indexofEleventhTab+1);
		indexofThirteenthTab = strLine.indexOf('\t', indexofTwelfthTab+1);
		
		
		refSeqGeneName = strLine.substring(indexofFirstTab+1, indexofSecondTab);
		chromName = strLine.substring(indexofSecondTab+1, indexofThirdTab);
		strand = strLine.substring(indexofThirdTab+1, indexofFourthTab).charAt(0);
		txStart = Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab));
		txEnd = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
		cdsStart = Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
		cdsEnd = Integer.parseInt(strLine.substring(indexofSeventhTab+1, indexofEighthTab));		
		exonCounts = Integer.parseInt(strLine.substring(indexofEighthTab+1, indexofNinethTab));
		exonStarts = strLine.substring(indexofNinethTab+1, indexofTenthTab);
		exonEnds = strLine.substring(indexofTenthTab+1, indexofEleventhTab);
		alternateGeneName = strLine.substring(indexofTwelfthTab+1, indexofThirteenthTab);
		
		refSeqGene.setRefSeqGeneName(refSeqGeneName);
		refSeqGene.setAlternateGeneName(alternateGeneName);
		refSeqGene.setChromName(chromName); 
		refSeqGene.setStrand(strand);
		refSeqGene.setTranscriptionStartPosition(txStart);
		refSeqGene.setTranscriptionEndPosition(txEnd);
		refSeqGene.setCdsStart(cdsStart);
		refSeqGene.setCdsEnd(cdsEnd);
		refSeqGene.setExonCounts(exonCounts);
		
		List<Integer> exonStartList = new ArrayList<Integer>(exonCounts);
		List<Integer> exonEndList = new ArrayList<Integer>(exonCounts);
		
//		Initialize before for loop
		int indexofFormerComma =-1;
		int indexofLatterComma = -1;
		
		for(int i = 0 ; i<exonCounts; i++){
			indexofFormerComma = indexofLatterComma;
			indexofLatterComma = exonStarts.indexOf(',',indexofFormerComma+1);
			exonStartList.add(Integer.parseInt(exonStarts.substring(indexofFormerComma+1,indexofLatterComma)));						
		}
		
//		Initialize before for loop
		indexofFormerComma =-1;
		indexofLatterComma = -1;
		
		for(int i = 0 ; i<exonCounts; i++){
			indexofFormerComma = indexofLatterComma;
			indexofLatterComma = exonEnds.indexOf(',',indexofFormerComma+1);
			exonEndList.add(Integer.parseInt(exonEnds.substring(indexofFormerComma+1,indexofLatterComma)));						
		}
		
		refSeqGene.setExonStarts(exonStartList);
		refSeqGene.setExonEnds(exonEndList);		
		
	}
	
	
	public void writeInformation(Set<RefSeqGene> refSeqGenes, Set<String> refSeqGeneNames,BufferedWriter bufferedWriter){
		try {
				bufferedWriter.write("Size of the refseqGenes is " + refSeqGenes.size()+ "\n");
				bufferedWriter.write("Size of the refseqGeneNamess is " + refSeqGeneNames.size()+"\n");	

		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	
	public void addRefSeqGeneName(RefSeqGene refSeqGene, Set<RefSeqGene> refSeqGenes, Set<String> refSeqGeneNames,BufferedWriter bufferedWriter){
		try {
			
			if (!refSeqGenes.contains(refSeqGene))
				refSeqGenes.add(refSeqGene);
			else 
				bufferedWriter.write("Totally same RefSeq Gene "+ refSeqGene.getRefSeqGeneName() +"\n");
			

			if (!refSeqGeneNames.contains(refSeqGene.getRefSeqGeneName()))
				refSeqGeneNames.add(refSeqGene.getRefSeqGeneName());
			else 
				bufferedWriter.write("This RefSeq Gene Name already exists " + refSeqGene.getRefSeqGeneName()+ "\n");
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void analyzeTxCdsExons(RefSeqGene refSeqGene, BufferedWriter bufferedWriter){
		if (!((refSeqGene.getTranscriptionStartPosition()<= refSeqGene.getCdsStart()) &&
		    (refSeqGene.getTranscriptionEndPosition()>= refSeqGene.getCdsEnd()) &&
		    (refSeqGene.getExonStarts().get(0)==refSeqGene.getTranscriptionStartPosition()) &&
		    (refSeqGene.getExonEnds().get(refSeqGene.getExonCounts()-1)==refSeqGene.getTranscriptionEndPosition()))){
			  try {
				bufferedWriter.write("Unexpected refseq gene\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	
	public void readInputFile(String fileName, List<RefSeqGene> refSeqGeneList, Map<String,Integer> refSeq2GeneHashMap){
	    FileReader fileReader =null;
	    BufferedReader bufferedReader = null;
	    
	    FileWriter fileWriter = null;
	    BufferedWriter bufferedWriter = null;
	    
	    String strLine =null;
	    Set<RefSeqGene> refSeqGenes = new HashSet<RefSeqGene>();
	    Set<String> refSeqGeneNames = new HashSet<String>();
	    Integer geneId;
	    
	    
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);

			fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_ANNOTATE_UCSC_ANALYZE_HG19_REFSEQ_GENES);
			bufferedWriter = new BufferedWriter(fileWriter);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			
//			Consume first line since first line contains column names
			strLine = bufferedReader.readLine();
			if (strLine!=null){
//				System.out.println(strLine);				
			}
			
			while ((strLine = bufferedReader.readLine()) != null)   {	
				RefSeqGene refSeqGene = new RefSeqGene();				
				getRefSeqGeneData(strLine, refSeqGene);	
				geneId = refSeq2GeneHashMap.get(refSeqGene.getRefSeqGeneName());
				
				if (geneId!=null){
					refSeqGene.setGeneId(geneId);
				}else{
//					If rna nucleotide accession version has no corresponding gene id, zero is imserted.
					refSeqGene.setGeneId(Commons.ZERO);
				}
										
				addRefSeqGeneName(refSeqGene, refSeqGenes, refSeqGeneNames,bufferedWriter);
				refSeqGeneList.add(refSeqGene);
				
				analyzeTxCdsExons(refSeqGene,bufferedWriter);
			}
			
			
			
			writeInformation(refSeqGenes,refSeqGeneNames,bufferedWriter);

//			Close the bufferedReader
			bufferedReader.close();
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
				   
	}

	
	public void checkforValidInterval(FivePrimeThreePrime primes){
//		Five primes
		if ((primes.get_5p1Start()).compareTo(0)<0)		
			primes.set_5p1Start(0);
		if ((primes.get_5p1End()).compareTo(0)<0)
			primes.set_5p1End(0);		
		
		if ((primes.get_5p2Start()).compareTo(0)<0)
			primes.set_5p2Start(0);
		if ((primes.get_5p2End()).compareTo(0)<0)
			primes.set_5p2End(0);
		
		if ((primes.get_5dStart()).compareTo(0)<0)
			primes.set_5dStart(0);
		if ((primes.get_5dEnd()).compareTo(0)<0)
			primes.set_5dEnd(0);
		
		
		
//		Three primes
		if ((primes.get_3p1Start()).compareTo(0)<0)
			primes.set_3p1Start(0);
		if ((primes.get_3p1End()).compareTo(0)<0)
			primes.set_3p1End(0);
		
		if ((primes.get_3p2Start()).compareTo(0)<0)
			primes.set_3p2Start(0);
		if ((primes.get_3p2End()).compareTo(0)<0)
			primes.set_3p2End(0);
		
		if ((primes.get_3dStart()).compareTo(0)<0)
			primes.set_3dStart(0);
		if ((primes.get_3dEnd()).compareTo(0)<0)
			primes.set_3dEnd(0);
	}
		
		
	
	public void create5p3pIntervals(RefSeqGene refSeqGene,BufferedWriter bufferedWriter){
		char strand;
		int txStart,txEnd;
		
		FivePrimeThreePrime primes = new FivePrimeThreePrime();
				
		strand = refSeqGene.getStrand();
		txStart = refSeqGene.getTranscriptionStartPosition();
		txEnd = refSeqGene.getTranscriptionEndPosition();
		
		switch(strand){
		
		//For + strand	gene starts at txStart, 5 prime is on the left hand side of txStart, 3 prime is on the right hand side of txEnd
		//For + strand: 5 prime ------- txStart----------(gene starts at txStart and goes to txEnd)-------------> txEnd------- 3 prime, always from 5 prime to 3 prime			
			
		case '+': 	
					//Write 5p1 [txStart-2000, txStart-1]			
					primes.set_5p1Start(txStart-2000);
					primes.set_5p1End(txStart-1);
					
					//Write 5p2 [txStart-10000,txStart-2001]
					primes.set_5p2Start(txStart-10000);
					primes.set_5p2End(txStart-2001);
					
					//Write 5d [txStart-100000,txStart-10001] 
					primes.set_5dStart(txStart-100000);
					primes.set_5dEnd(txStart-10001);
										
					//Write 3p1 [txEnd+1,txEnd+2000]
					primes.set_3p1Start(txEnd+1);
					primes.set_3p1End(txEnd+2000);
				
					//Write 3p2 [txEnd+2001,txEnd+10000]
					primes.set_3p2Start(txEnd+2001);
					primes.set_3p2End(txEnd+10000);

					//Write 3d [txEnd+10001,txEnd+100000]
					primes.set_3dStart(txEnd+10001);
					primes.set_3dEnd(txEnd +100000);				
					break;
		
		//For - strand  gene starts at txEnd, 5 prime is on right hand side of txEnd, 3 prime is on the left hand side of txStart			
		//For - strand: 3 prime ------- txStart <------------(gene starts at txEnd and goes to txStart)---------------txEnd------- 5 prime, always from 5 prime to 3 prime			
		case '-': 
					//Write 5p1 [txEnd+1, txEnd+2000]
                    primes.set_5p1Start(txEnd+1);				
                    primes.set_5p1End(txEnd+2000);
                    
					//Write 5p2 [txEnd+2001,txEnd+10000]
                    primes.set_5p2Start(txEnd+2001);				
                    primes.set_5p2End(txEnd+10000);
                    
                    //Write 5d [txEnd+10001,txEnd+100000] 
                    primes.set_5dStart(txEnd+10001);				
                    primes.set_5dEnd(txEnd+100000);
                                        	
					//Write 3p1 [txStart-2000,txStart-1]
                    primes.set_3p1Start(txStart-2000);				
                    primes.set_3p1End(txStart-1);
                    
					//Write 3p2 [txStart-10000,txStart-2001]
                    primes.set_3p2Start(txStart-10000);				
                    primes.set_3p2End(txStart-2001);
                    
					//Write 3d [txStart-100000,txStart-10001]
                    primes.set_3dStart(txStart-100000);				
                    primes.set_3dEnd(txStart-10001);                    
					break;			
					
		}// End of Switch

//		We have to check for invalid intervals
//		Such as position number less than zero or position number greater than corresponding chromosome length
//		Position number greater than corresponding chromosome length might not be a problem
//		However position number less than zero must be avoided. 
		
		checkforValidInterval(primes);
		
		
		try {
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_5p1Start() + "\t" + primes.get_5p1End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" +"5P1" + "\t"+ strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_5p2Start() + "\t" + primes.get_5p2End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" +"5P2" + "\t"+ strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_5dStart() + "\t" + primes.get_5dEnd() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + "5D" + "\t"+ strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");

			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_3p1Start() + "\t" + primes.get_3p1End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + "3P1" + "\t"+ strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_3p2Start() + "\t" + primes.get_3p2End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + "3P2" + "\t"+ strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_3dStart()+ "\t" + primes.get_3dEnd() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + "3D" + "\t"+ strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");

			bufferedWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		Let's free the space for primes
		primes = null;
	}
	
	public void createExonIntronIntervals(RefSeqGene refSeqGene,int i, BufferedWriter bufferedWriter){
		int j;
		
		
		try {	
			//Write exon and intron intervals 			
			for( j =0; j< refSeqGene.getExonCounts()-1; j++){
			
				bufferedWriter.write(refSeqGene.getChromName() + "\t" + refSeqGene.getExonStarts().get(j) + "\t" + refSeqGene.getExonEnds().get(j) + "\t" + refSeqGene.getRefSeqGeneName() + "\t"+ refSeqGene.getGeneId() + "\t" + Commons.EXON + (j+1) + "\t"+ refSeqGene.getStrand() + "\t" +refSeqGene.getAlternateGeneName()+"\n");			
				bufferedWriter.write(refSeqGene.getChromName() + "\t" + (refSeqGene.getExonEnds().get(j)+1) + "\t" + (refSeqGene.getExonStarts().get(j+1)-1) + "\t" + refSeqGene.getRefSeqGeneName() + "\t"+ refSeqGene.getGeneId() + "\t" + Commons.INTRON + (j+1) + "\t"+ refSeqGene.getStrand() + "\t" + refSeqGene.getAlternateGeneName()+"\n");
				bufferedWriter.flush();
			}
			//Write the last exon which is not written in the for loop
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + refSeqGene.getExonStarts().get(j) + "\t" + refSeqGene.getExonEnds().get(j) + "\t" + refSeqGene.getRefSeqGeneName() + "\t"+ refSeqGene.getGeneId() + "\t" + Commons.EXON + (j+1) + "\t"+ refSeqGene.getStrand() + "\t" +refSeqGene.getAlternateGeneName()+"\n");
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public void getBufferedWriter(String chromName, BufferedWriter bufferedWriter, List<BufferedWriter> bufferedWriterList){
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
		
	}
	
	public void fillUnsortedChromBaseRefSeqGeneIntervalFiles(List<RefSeqGene> refSeqGeneList, List<BufferedWriter> bufferedWriterList){
		RefSeqGene refSeqGene = null;
		BufferedWriter bufferedWriter = null;
		
		String chromName;
		
		for (int i =0; i<refSeqGeneList.size(); i++){
			refSeqGene = refSeqGeneList.get(i);
			chromName = refSeqGene.getChromName();
			
//			getBufferedWriter(chromName,bufferedWriter,bufferedWriterList);		
			
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
			
			
			createExonIntronIntervals(refSeqGene,i,bufferedWriter);			
			create5p3pIntervals(refSeqGene,bufferedWriter);
		}
		
	}

	
	public void openSortedChromBaseRefSeqGeneFiles(List<BufferedWriter> bufferedWriterList){
		try {
			FileWriter  fileWriter1 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR1_REFSEQ_GENES);
			FileWriter  fileWriter2 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR2_REFSEQ_GENES);
			FileWriter  fileWriter3 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR3_REFSEQ_GENES);
			FileWriter  fileWriter4 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR4_REFSEQ_GENES);
			FileWriter  fileWriter5 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR5_REFSEQ_GENES);
			FileWriter  fileWriter6 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR6_REFSEQ_GENES);
			FileWriter  fileWriter7 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR7_REFSEQ_GENES);
			FileWriter  fileWriter8 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR8_REFSEQ_GENES);
			FileWriter  fileWriter9 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR9_REFSEQ_GENES);
			FileWriter  fileWriter10 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR10_REFSEQ_GENES);
			FileWriter  fileWriter11 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR11_REFSEQ_GENES);
			FileWriter  fileWriter12 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR12_REFSEQ_GENES);
			FileWriter  fileWriter13 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR13_REFSEQ_GENES);
			FileWriter  fileWriter14 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR14_REFSEQ_GENES);
			FileWriter  fileWriter15 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR15_REFSEQ_GENES);
			FileWriter  fileWriter16 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR16_REFSEQ_GENES);
			FileWriter  fileWriter17 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR17_REFSEQ_GENES);
			FileWriter  fileWriter18 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR18_REFSEQ_GENES);
			FileWriter  fileWriter19 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR19_REFSEQ_GENES);
			FileWriter  fileWriter20 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR20_REFSEQ_GENES);
			FileWriter  fileWriter21 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR21_REFSEQ_GENES);
			FileWriter  fileWriter22 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR22_REFSEQ_GENES);
			FileWriter  fileWriterX = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHRX_REFSEQ_GENES);
			FileWriter  fileWriterY = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHRY_REFSEQ_GENES);
			
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
	
	
public void openUnsortedChromBaseRefSeqGeneFileReaders(List<BufferedReader> bufferedReaderList){
		
		try {
			FileReader  fileReader1 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR1_REFSEQ_GENES);
			FileReader  fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR2_REFSEQ_GENES);
			FileReader  fileReader3 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR3_REFSEQ_GENES);
			FileReader  fileReader4 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR4_REFSEQ_GENES);
			FileReader  fileReader5 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR5_REFSEQ_GENES);
			FileReader  fileReader6 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR6_REFSEQ_GENES);
			FileReader  fileReader7 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR7_REFSEQ_GENES);
			FileReader  fileReader8 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR8_REFSEQ_GENES);
			FileReader  fileReader9 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR9_REFSEQ_GENES);
			FileReader  fileReader10 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR10_REFSEQ_GENES);
			FileReader  fileReader11 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR11_REFSEQ_GENES);
			FileReader  fileReader12 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR12_REFSEQ_GENES);
			FileReader  fileReader13 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR13_REFSEQ_GENES);
			FileReader  fileReader14 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR14_REFSEQ_GENES);
			FileReader  fileReader15 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR15_REFSEQ_GENES);
			FileReader  fileReader16 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR16_REFSEQ_GENES);
			FileReader  fileReader17 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR17_REFSEQ_GENES);
			FileReader  fileReader18 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR18_REFSEQ_GENES);
			FileReader  fileReader19 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR19_REFSEQ_GENES);
			FileReader  fileReader20 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR20_REFSEQ_GENES);
			FileReader  fileReader21 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR21_REFSEQ_GENES);
			FileReader  fileReader22 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR22_REFSEQ_GENES);
			FileReader  fileReaderX = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHRX_REFSEQ_GENES);
			FileReader  fileReaderY = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHRY_REFSEQ_GENES);
			
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
	
	public void openUnsortedChromBaseRefSeqGeneFileWriters(List<BufferedWriter> bufferedWriterList){
		
		try {
			FileWriter  fileWriter1 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR1_REFSEQ_GENES);
			FileWriter  fileWriter2 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR2_REFSEQ_GENES);
			FileWriter  fileWriter3 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR3_REFSEQ_GENES);
			FileWriter  fileWriter4 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR4_REFSEQ_GENES);
			FileWriter  fileWriter5 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR5_REFSEQ_GENES);
			FileWriter  fileWriter6 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR6_REFSEQ_GENES);
			FileWriter  fileWriter7 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR7_REFSEQ_GENES);
			FileWriter  fileWriter8 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR8_REFSEQ_GENES);
			FileWriter  fileWriter9 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR9_REFSEQ_GENES);
			FileWriter  fileWriter10 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR10_REFSEQ_GENES);
			FileWriter  fileWriter11 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR11_REFSEQ_GENES);
			FileWriter  fileWriter12 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR12_REFSEQ_GENES);
			FileWriter  fileWriter13 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR13_REFSEQ_GENES);
			FileWriter  fileWriter14 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR14_REFSEQ_GENES);
			FileWriter  fileWriter15 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR15_REFSEQ_GENES);
			FileWriter  fileWriter16 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR16_REFSEQ_GENES);
			FileWriter  fileWriter17 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR17_REFSEQ_GENES);
			FileWriter  fileWriter18 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR18_REFSEQ_GENES);
			FileWriter  fileWriter19 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR19_REFSEQ_GENES);
			FileWriter  fileWriter20 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR20_REFSEQ_GENES);
			FileWriter  fileWriter21 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR21_REFSEQ_GENES);
			FileWriter  fileWriter22 = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR22_REFSEQ_GENES);
			FileWriter  fileWriterX = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHRX_REFSEQ_GENES);
			FileWriter  fileWriterY = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHRY_REFSEQ_GENES);
			
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
	
	
	public void closeChromBaseRefSeqGeneFileWriters(List<BufferedWriter> bufferedWriterList){
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
	
	public void closeChromBaseRefSeqGeneFileReaders(List<BufferedReader> bufferedReaderList){
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
	
	
	
	public void readUnsortedChromBaseRefSeqGeneFilesSortWriteSortedChromBaseRefSeqGeneFiles(List<BufferedReader> unsortedBufferedReaderList, List<BufferedWriter> sortedBufferedWriterList){
		
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		String strLine =null;
		
		int indexofFirstTab =0;
		int indexofSecondTab =0;
		int indexofThirdTab =0;
		int indexofFourthTab =0;
		int indexofFifthTab =0;
		int indexofSixthTab =0;		
		int indexofSeventhTab = 0;
		
		for(int i=0; i<24 ; i++){
			switch(i){
			case 0:
					bufferedReader = unsortedBufferedReaderList.get(0);
					bufferedWriter = sortedBufferedWriterList.get(0);
					break;
			case 1:
					bufferedReader = unsortedBufferedReaderList.get(1);
					bufferedWriter = sortedBufferedWriterList.get(1);
					break;
			case 2:
					bufferedReader = unsortedBufferedReaderList.get(2);
					bufferedWriter = sortedBufferedWriterList.get(2);
					break;
			case 3:
					bufferedReader = unsortedBufferedReaderList.get(3);
					bufferedWriter = sortedBufferedWriterList.get(3);
					break;
			case 4:
					bufferedReader = unsortedBufferedReaderList.get(4);
					bufferedWriter = sortedBufferedWriterList.get(4);
					break;
			case 5:
					bufferedReader = unsortedBufferedReaderList.get(5);
					bufferedWriter = sortedBufferedWriterList.get(5);
					break;
			case 6:
					bufferedReader = unsortedBufferedReaderList.get(6);
					bufferedWriter = sortedBufferedWriterList.get(6);
					break;
			case 7:
					bufferedReader = unsortedBufferedReaderList.get(7);
					bufferedWriter = sortedBufferedWriterList.get(7);
					break;
			case 8:
					bufferedReader = unsortedBufferedReaderList.get(8);
					bufferedWriter = sortedBufferedWriterList.get(8);
					break;
			case 9:
					bufferedReader = unsortedBufferedReaderList.get(9);
					bufferedWriter = sortedBufferedWriterList.get(9);
					break;
			case 10:
					bufferedReader = unsortedBufferedReaderList.get(10);
					bufferedWriter = sortedBufferedWriterList.get(10);
					break;
			case 11:
					bufferedReader = unsortedBufferedReaderList.get(11);
					bufferedWriter = sortedBufferedWriterList.get(11);
					break;
			case 12:
					bufferedReader = unsortedBufferedReaderList.get(12);
					bufferedWriter = sortedBufferedWriterList.get(12);
					break;
			case 13:
					bufferedReader = unsortedBufferedReaderList.get(13);
					bufferedWriter = sortedBufferedWriterList.get(13);
					break;
			case 14:
					bufferedReader = unsortedBufferedReaderList.get(14);
					bufferedWriter = sortedBufferedWriterList.get(14);
					break;
			case 15:
					bufferedReader = unsortedBufferedReaderList.get(15);
					bufferedWriter = sortedBufferedWriterList.get(15);
					break;
			case 16:
					bufferedReader = unsortedBufferedReaderList.get(16);
					bufferedWriter = sortedBufferedWriterList.get(16);
					break;
			case 17:
					bufferedReader = unsortedBufferedReaderList.get(17);
					bufferedWriter = sortedBufferedWriterList.get(17);
					break;
			case 18:
					bufferedReader = unsortedBufferedReaderList.get(18);
					bufferedWriter = sortedBufferedWriterList.get(18);
					break;
			case 19:
					bufferedReader = unsortedBufferedReaderList.get(19);
					bufferedWriter = sortedBufferedWriterList.get(19);
					break;
			case 20:
					bufferedReader = unsortedBufferedReaderList.get(20);
					bufferedWriter = sortedBufferedWriterList.get(20);
					break;
			case 21:
					bufferedReader = unsortedBufferedReaderList.get(21);
					bufferedWriter = sortedBufferedWriterList.get(21);
					break;
			case 22:
					bufferedReader = unsortedBufferedReaderList.get(22);
					bufferedWriter = sortedBufferedWriterList.get(22);
					break;
			case 23:
					bufferedReader = unsortedBufferedReaderList.get(23);
					bufferedWriter = sortedBufferedWriterList.get(23);
					break;
				
			}
			
			List<RefSeqGeneInterval> refSeqGeneIntervalList = new ArrayList<RefSeqGeneInterval>();
		
			try {
				while ((strLine = bufferedReader.readLine()) != null)   {
					  // ADD the content to the ArrayList
					RefSeqGeneInterval refSeqGeneInterval = new RefSeqGeneInterval();
					
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
					indexofSixthTab = strLine.indexOf('\t',indexofFifthTab+1);
					indexofSeventhTab = strLine.indexOf('\t',indexofSixthTab+1);
					
//					example line
//					chrY	16636453	16636816	NR_028319	22829	Exon1	+	NLGN4Y

					if ((indexofFirstTab<0) || (indexofSecondTab<0) || (indexofThirdTab<0) || (indexofFourthTab<0) || (indexofFifthTab<0) || (indexofSixthTab<0) || indexofSeventhTab<0){
						System.out.println("Unexpected format in Unsorted RefSeq Gene File");
						System.out.println("For chromosome " + i);
						System.out.println(strLine);								
					}
					
					refSeqGeneInterval.setChromName(strLine.substring(0, indexofFirstTab));							
					refSeqGeneInterval.setIntervalStart(Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab)));
					refSeqGeneInterval.setIntervalEnd(Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab)));
					refSeqGeneInterval.setRefSeqGeneName(strLine.substring(indexofThirdTab+1, indexofFourthTab));
					
					refSeqGeneInterval.setGeneId(Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab)));
					
					refSeqGeneInterval.setIntervalName(strLine.substring(indexofFifthTab+1, indexofSixthTab));
					refSeqGeneInterval.setStrand(strLine.substring(indexofSixthTab+1, indexofSeventhTab).charAt(0));
					refSeqGeneInterval.setAlternateGeneName(strLine.substring(indexofSeventhTab+1));
					
					refSeqGeneIntervalList.add(refSeqGeneInterval);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//sort the data
			Collections.sort(refSeqGeneIntervalList, RefSeqGeneInterval.START_POSITION_ORDER);
			
			
//			//for debug purposes
//				System.out.println("chr" + i+  " high: " + refSeqGeneIntervalList.get(refSeqGeneIntervalList.size()-1).getIntervalEnd());
//			//for debug purposes
			

//			write sorted list to file
			for(int j= 0; j <refSeqGeneIntervalList.size(); j++){
				try {
					bufferedWriter.write(refSeqGeneIntervalList.get(j).getChromName()+ "\t" +refSeqGeneIntervalList.get(j).getIntervalStart()+"\t"+ refSeqGeneIntervalList.get(j).getIntervalEnd()+"\t" +refSeqGeneIntervalList.get(j).getRefSeqGeneName()+ "\t" + refSeqGeneIntervalList.get(j).getGeneId()+ "\t" +refSeqGeneIntervalList.get(j).getIntervalName()+ "\t" + refSeqGeneIntervalList.get(j).getStrand() + "\t" + refSeqGeneIntervalList.get(j).getAlternateGeneName()+ "\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}												
			}
			
			
		}//End of for loop
		
	}
	
	public static void createRefSeq2GeneMap(String fileName,Map<String,Integer> refSeq2GeneHashMap){
		
		String strLine;
		int indexofFirstTab; 
		String refSeqName;
		int geneId;
		
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((strLine=bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				refSeqName = strLine.substring(0, indexofFirstTab);
				geneId = Integer.parseInt(strLine.substring(indexofFirstTab+1));
				
				if(!(refSeq2GeneHashMap.containsKey(refSeqName))){
					refSeq2GeneHashMap.put(refSeqName, geneId);
				}else{
					System.out.println("RefSeqName already exists " + refSeqName);
				}
			}// End of while
			
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	
	
	public static void main(String[] args) {
		
//		TODO  might be done without using refSeqGeneList
		List<RefSeqGene> refSeqGeneList = new ArrayList<RefSeqGene>();
		List<BufferedWriter> unsortedBufferedWriterList = new ArrayList<BufferedWriter>(24);
		List<BufferedReader> unsortedBufferedReaderList = new ArrayList<BufferedReader>(24);		
		List<BufferedWriter> sortedBufferedWriterList = new ArrayList<BufferedWriter>(24);
		
		Map<String,Integer> refSeq2GeneHashMap =  new HashMap<String,Integer>();
		String fileName = Commons.FTP_HG19_REFSEQ_GENES;
		String fileName2 = Commons.NCBI_HUMAN_REF_SEQ_TO_GENE;
		
		CreateIntervalFileUsingUCSCGenomeUsingCollectionsSort createIntervals = new CreateIntervalFileUsingUCSCGenomeUsingCollectionsSort();
	    
//		ncbi output file will be read into a map
//		using this map for each gene in the refSeqGeneList  geneId will be 
		createRefSeq2GeneMap(fileName2,refSeq2GeneHashMap);
//		augmentation of RNA nucleotide accession version, in other words refSeqGeneName with entrez gene id is done here
//		refSeqGeneName is augmented with entrez gene id 
		createIntervals.readInputFile(fileName, refSeqGeneList,refSeq2GeneHashMap);	
		
		createIntervals.openUnsortedChromBaseRefSeqGeneFileWriters(unsortedBufferedWriterList);	    
		createIntervals.fillUnsortedChromBaseRefSeqGeneIntervalFiles(refSeqGeneList, unsortedBufferedWriterList);		
		createIntervals.closeChromBaseRefSeqGeneFileWriters(unsortedBufferedWriterList);		
		
		createIntervals.openUnsortedChromBaseRefSeqGeneFileReaders(unsortedBufferedReaderList);
		createIntervals.openSortedChromBaseRefSeqGeneFiles(sortedBufferedWriterList);		
		createIntervals.readUnsortedChromBaseRefSeqGeneFilesSortWriteSortedChromBaseRefSeqGeneFiles(unsortedBufferedReaderList,sortedBufferedWriterList);
		createIntervals.closeChromBaseRefSeqGeneFileReaders(unsortedBufferedReaderList);
		createIntervals.closeChromBaseRefSeqGeneFileWriters(sortedBufferedWriterList);
	}

}
