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
import java.util.List;
import java.util.Map;
import java.util.Set;

import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import create.ChromosomeBasedFilesandOperations;
import enumtypes.ChromosomeName;
import enumtypes.IntervalName;


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
		refSeqGene.setChromName(ChromosomeName.convertStringtoEnum(chromName)); 
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

			fileWriter = FileOperations.createFileWriter(Commons.ANNOTATE_UCSC_ANALYZE_HG19_REFSEQ_GENES_DIRECTORYNAME,Commons.ANNOTATE_UCSC_ANALYZE_HG19_REFSEQ_GENES_FILENAME);
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
//				GlanetRunner.appendLog(strLine);				
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
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_5p1Start() + "\t" + primes.get_5p1End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + IntervalName.FIVE_P_ONE.getIntervalNameString() + "\t" + "0" + "\t" + strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_5p2Start() + "\t" + primes.get_5p2End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + IntervalName.FIVE_P_TWO.getIntervalNameString() + "\t" + "0"  + "\t" + strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_5dStart() + "\t" + primes.get_5dEnd() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + IntervalName.FIVE_D.getIntervalNameString() + "\t" + "0"  + "\t" + strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");

			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_3p1Start() + "\t" + primes.get_3p1End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + IntervalName.THREE_P_ONE.getIntervalNameString() + "\t" + "0"  + "\t" + strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_3p2Start() + "\t" + primes.get_3p2End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + IntervalName.THREE_P_TWO.getIntervalNameString() + "\t" + "0" + "\t" + strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + primes.get_3dStart()+ "\t" + primes.get_3dEnd() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + IntervalName.THREE_D.getIntervalNameString() + "\t" + "0" + "\t" + strand + "\t"+ refSeqGene.getAlternateGeneName()+"\n");

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
							
				bufferedWriter.write(refSeqGene.getChromName() + "\t" + refSeqGene.getExonStarts().get(j) + "\t" + refSeqGene.getExonEnds().get(j) + "\t" + refSeqGene.getRefSeqGeneName() + "\t"+ refSeqGene.getGeneId() + "\t" + IntervalName.EXON.getIntervalNameString() +"\t" + (j+1) + "\t"+ refSeqGene.getStrand() + "\t" +refSeqGene.getAlternateGeneName()+"\n");			
				bufferedWriter.write(refSeqGene.getChromName() + "\t" + (refSeqGene.getExonEnds().get(j)+1) + "\t" + (refSeqGene.getExonStarts().get(j+1)-1) + "\t" + refSeqGene.getRefSeqGeneName() + "\t"+ refSeqGene.getGeneId() + "\t" + IntervalName.INTRON.getIntervalNameString() + "\t" + (j+1) + "\t"+ refSeqGene.getStrand() + "\t" + refSeqGene.getAlternateGeneName()+"\n");
				bufferedWriter.flush();
			}
			//Write the last exon which is not written in the for loop
			bufferedWriter.write(refSeqGene.getChromName() + "\t" + refSeqGene.getExonStarts().get(j) + "\t" + refSeqGene.getExonEnds().get(j) + "\t" + refSeqGene.getRefSeqGeneName() + "\t"+ refSeqGene.getGeneId() + "\t" + IntervalName.EXON.getIntervalNameString() + "\t" + (j+1) + "\t"+ refSeqGene.getStrand() + "\t" +refSeqGene.getAlternateGeneName()+"\n");
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public void fillUnsortedChromBaseRefSeqGeneIntervalFiles(List<RefSeqGene> refSeqGeneList, List<BufferedWriter> bufferedWriterList){
		RefSeqGene refSeqGene = null;
		BufferedWriter bufferedWriter = null;
		
		ChromosomeName chromName;
		
		for (int i =0; i<refSeqGeneList.size(); i++){
			refSeqGene = refSeqGeneList.get(i);
			chromName = refSeqGene.getChromName();
			
			bufferedWriter = ChromosomeBasedFilesandOperations.getBufferedWriter(chromName,bufferedWriterList);		

			//Pay attention, bufferedWriter is null for such refseq genes
			//chr6_ssto_hap7	LY6G5B
			//GlanetRunner.appendLog(refSeqGene.getChromName() + "\t"+refSeqGene.getAlternateGeneName() );
		
			if(bufferedWriter!=null){
				createExonIntronIntervals(refSeqGene,i,bufferedWriter);			
				create5p3pIntervals(refSeqGene,bufferedWriter);
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
		int indexofEighthTab = 0;
		
		
		for(int i=0; i<24 ; i++){
			
			bufferedReader = unsortedBufferedReaderList.get(i);
			bufferedWriter = sortedBufferedWriterList.get(i);
			
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
					indexofEighthTab = strLine.indexOf('\t',indexofSeventhTab+1);
					
//					example line
//					chrY	16636453	16636816	NR_028319	22829	Exon	1	+	NLGN4Y

					if ((indexofFirstTab<0) || (indexofSecondTab<0) || (indexofThirdTab<0) || (indexofFourthTab<0) || (indexofFifthTab<0) || (indexofSixthTab<0) || indexofSeventhTab<0){
						GlanetRunner.appendLog("Unexpected format in Unsorted RefSeq Gene File");
						GlanetRunner.appendLog("For chromosome " + i);
						GlanetRunner.appendLog(strLine);								
					}
					
					refSeqGeneInterval.setChromName(ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab)));							
					refSeqGeneInterval.setIntervalStart(Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab)));
					refSeqGeneInterval.setIntervalEnd(Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab)));
					refSeqGeneInterval.setRefSeqGeneName(strLine.substring(indexofThirdTab+1, indexofFourthTab));
					
					refSeqGeneInterval.setGeneId(Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab)));
					
					refSeqGeneInterval.setIntervalName(IntervalName.convertStringtoEnum(strLine.substring(indexofFifthTab+1, indexofSixthTab)));
					refSeqGeneInterval.setIntervalNumber(Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab)));
					
					refSeqGeneInterval.setStrand(strLine.substring(indexofSeventhTab+1, indexofEighthTab).charAt(0));
					refSeqGeneInterval.setAlternateGeneName(strLine.substring(indexofEighthTab+1));
					
					refSeqGeneIntervalList.add(refSeqGeneInterval);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//sort the data
			Collections.sort(refSeqGeneIntervalList, RefSeqGeneInterval.START_POSITION_ORDER);
			
			
//			//for debug purposes
//				GlanetRunner.appendLog("chr" + i+  " high: " + refSeqGeneIntervalList.get(refSeqGeneIntervalList.size()-1).getIntervalEnd());
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
					GlanetRunner.appendLog("RefSeqName already exists " + refSeqName);
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
	
	//args[0] must have input file name with folder
	//args[1] must have GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2] must have Input File Format		
	//args[3] must have Number of Permutations	
	//args[4] must have False Discovery Rate (ex: 0.05)
	//args[5] must have Generate Random Data Mode (with GC and Mapability/without GC and Mapability)
	//args[6] must have writeGeneratedRandomDataMode checkBox
	//args[7] must have writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//args[8] must have writePermutationBasedAnnotationResultMode checkBox
	public static void main(String[] args) {
		
//		TODO  might be done without using refSeqGeneList
		List<RefSeqGene> refSeqGeneList = new ArrayList<RefSeqGene>();
		List<BufferedWriter> unsortedBufferedWriterList = new ArrayList<BufferedWriter>(24);
		List<BufferedReader> unsortedBufferedReaderList = new ArrayList<BufferedReader>(24);		
		List<BufferedWriter> sortedBufferedWriterList = new ArrayList<BufferedWriter>(24);
		
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;

		
		Map<String,Integer> refSeq2GeneHashMap =  new HashMap<String,Integer>();
		String fileName = dataFolder + Commons.FTP_HG19_REFSEQ_GENES_DOWNLOADED_1_OCT_2014;
		String fileName2 = dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_DIRECTORYNAME + Commons.NCBI_RNANUCLEOTIDEACCESSION_TO_GENEID_1_OCT_2014 ;
		
		CreateIntervalFileUsingUCSCGenomeUsingCollectionsSort createIntervals = new CreateIntervalFileUsingUCSCGenomeUsingCollectionsSort();
	    
//		ncbi output file will be read into a map
//		using this map for each gene in the refSeqGeneList  geneId will be 
		createRefSeq2GeneMap(fileName2,refSeq2GeneHashMap);
//		augmentation of RNA nucleotide accession version, in other words refSeqGeneName with entrez gene id is done here
//		refSeqGeneName is augmented with entrez gene id 
		createIntervals.readInputFile(fileName, refSeqGeneList,refSeq2GeneHashMap);	
		
		ChromosomeBasedFilesandOperations.openUnsortedChromosomeBasedRefSeqGeneFileWriters(outputFolder,unsortedBufferedWriterList);	    
		createIntervals.fillUnsortedChromBaseRefSeqGeneIntervalFiles(refSeqGeneList, unsortedBufferedWriterList);		
		ChromosomeBasedFilesandOperations.closeChromosomeBasedBufferedWriters(unsortedBufferedWriterList);		
		
		ChromosomeBasedFilesandOperations.openUnsortedChromosomeBasedRefSeqGeneFileReaders(outputFolder,unsortedBufferedReaderList);
		ChromosomeBasedFilesandOperations.openSortedChromosomeBasedRefSeqGeneFiles(outputFolder,sortedBufferedWriterList);		
		createIntervals.readUnsortedChromBaseRefSeqGeneFilesSortWriteSortedChromBaseRefSeqGeneFiles(unsortedBufferedReaderList,sortedBufferedWriterList);
		ChromosomeBasedFilesandOperations.closeChromosomeBasedBufferedReaders(unsortedBufferedReaderList);
		ChromosomeBasedFilesandOperations.closeChromosomeBasedBufferedWriters(sortedBufferedWriterList);
	}

}
