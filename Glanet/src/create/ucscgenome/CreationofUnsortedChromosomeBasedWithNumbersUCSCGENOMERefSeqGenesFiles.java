/**
 * Unsorted
 * Chromosome Based
 * With Numbers
 * UCSC_REFSEQ_Genes Files
 */
package create.ucscgenome;

import enumtypes.ChromosomeName;
import enumtypes.ElementType;
import enumtypes.IntervalName;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Burçak Otlu
 * @date Nov 18, 2014
 * @project Glanet 
 *
 */
public class CreationofUnsortedChromosomeBasedWithNumbersUCSCGENOMERefSeqGenesFiles {
	
	
	public static void createRNANucleotideAccession2GeneIDMap(String fileName,TObjectIntMap<String> rnaNucleotideAccession2GeneIDMap){
		
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
				
				if(!(rnaNucleotideAccession2GeneIDMap.containsKey(refSeqName))){
					rnaNucleotideAccession2GeneIDMap.put(refSeqName, geneId);
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
	
	public static void checkforValidInterval(FivePrimeThreePrime primes){
		
		//Five primes
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
		
		
		
		//Three primes
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
	
	public static void write5p3pIntervals(
			ChromosomeName chromName,
			int RNANucleotideAccessionNumber,
			int geneID,
			int alternateGeneNameNumber,
			int txStart, 
			int txEnd,
			char strand,
			BufferedWriter bufferedWriter)
	{
		
		FivePrimeThreePrime primes = new FivePrimeThreePrime();
		
		switch(strand){
		
		//Always from 5 prime to 3 prime
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
			//Always from 5 prime to 3 prime
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
		
		
		checkforValidInterval(primes);
		
		
		try {
						
				bufferedWriter.write(chromName.convertEnumtoString() + "\t" + primes.get_5p1Start() + "\t" + primes.get_5p1End() + "\t" + RNANucleotideAccessionNumber + "\t" + geneID + "\t" + IntervalName.FIVE_P_ONE.convertEnumtoString() + "\t" + "0" + "\t" + strand + "\t" + alternateGeneNameNumber + System.getProperty("line.separator"));
				bufferedWriter.write(chromName.convertEnumtoString() + "\t" + primes.get_5p2Start() + "\t" + primes.get_5p2End() + "\t" + RNANucleotideAccessionNumber + "\t" + geneID + "\t" + IntervalName.FIVE_P_TWO.convertEnumtoString() + "\t" + "0" + "\t" + strand + "\t" + alternateGeneNameNumber + System.getProperty("line.separator"));
				bufferedWriter.write(chromName.convertEnumtoString() + "\t" + primes.get_5dStart() + "\t" + primes.get_5dEnd() + "\t" + RNANucleotideAccessionNumber + "\t" + geneID + "\t"   + IntervalName.FIVE_D.convertEnumtoString()   + "\t" + "0" + "\t" + strand + "\t" + alternateGeneNameNumber + System.getProperty("line.separator"));

				bufferedWriter.write(chromName.convertEnumtoString() + "\t" + primes.get_3p1Start() + "\t" + primes.get_3p1End() + "\t" + RNANucleotideAccessionNumber + "\t" + geneID + "\t" + IntervalName.THREE_P_ONE.convertEnumtoString() + "\t" + "0" + "\t" + strand + "\t"+ alternateGeneNameNumber + System.getProperty("line.separator"));
				bufferedWriter.write(chromName.convertEnumtoString() + "\t" + primes.get_3p2Start() + "\t" + primes.get_3p2End() + "\t" + RNANucleotideAccessionNumber + "\t" + geneID + "\t" + IntervalName.THREE_P_TWO.convertEnumtoString() + "\t" + "0" + "\t" + strand + "\t"+ alternateGeneNameNumber + System.getProperty("line.separator"));
				bufferedWriter.write(chromName.convertEnumtoString() + "\t" + primes.get_3dStart()+ "\t" + primes.get_3dEnd() + "\t" + RNANucleotideAccessionNumber + "\t" + geneID + "\t" 	  + IntervalName.THREE_D.convertEnumtoString() +  "\t" + "0" + "\t" + strand + "\t"+ alternateGeneNameNumber + System.getProperty("line.separator"));

				bufferedWriter.flush();
		
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//Let's free the space for primes
		primes = null;
		
	}
	
	public static void writeExonIntronIntervals(
			ChromosomeName chromName,
			int exonCounts,
			TIntList exonStartList,
			TIntList exonEndList,
			int RNANucleotideAccessionNumber,
			int geneID,
			char strand, 
			int alternateGeneNameNumber, 
			BufferedWriter bufferedWriter)
	{
		int i;
		
		try {
			
			for(i =0; i< exonCounts-1; i++){
				
				//EXON
					bufferedWriter.write(chromName.convertEnumtoString() + "\t" + exonStartList.get(i) + "\t" + exonEndList.get(i) + "\t" + RNANucleotideAccessionNumber + "\t"+ geneID + "\t" + IntervalName.EXON.convertEnumtoString() + "\t" +(i+1) + "\t"+ strand + "\t" + alternateGeneNameNumber + System.getProperty("line.separator"));
				
				
				//INTRON
				bufferedWriter.write(chromName.convertEnumtoString() + "\t" + (exonEndList.get(i)+1) + "\t" + (exonStartList.get(i+1)-1) + "\t" + RNANucleotideAccessionNumber + "\t"+ geneID + "\t" + IntervalName.INTRON.convertEnumtoString() + "\t" + (i+1) + "\t"+ strand + "\t" + alternateGeneNameNumber + System.getProperty("line.separator"));
				
				bufferedWriter.flush();
			}//End of FOR
			
			//Write the last EXON which is not written in the for loop
			bufferedWriter.write(chromName.convertEnumtoString() + "\t" + exonStartList.get(i) + "\t" + exonEndList.get(i) + "\t" + RNANucleotideAccessionNumber + "\t"+ geneID + "\t" + IntervalName.EXON.convertEnumtoString() + "\t" + (i+1) + "\t"+ strand + "\t" + alternateGeneNameNumber + System.getProperty("line.separator"));
			bufferedWriter.flush();
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	
	public static void readUCSCGenomeHG19RefSeqGenesFileWriteUnsortedChromBasedFilesWithNumbers(
			String UCSC_GENOME_HG19_REFSEQ_GENES_FILE,
			List<BufferedWriter> UCSCGENOMERefSeqGenesBufferedWriterList,
			TObjectIntMap<String> rnaNucleotideAccession2GeneIDMap,
			TObjectIntMap<String> UCSCGENOME_HG19_RefSeq_Genes_RNANucleotideAccession_Name2NumberMap,
			TObjectIntMap<String> UCSCGENOME_HG19_RefSeq_Genes_GeneSymbol_Name2NumberMap){
		
		
		//Initialize alternateGeneNameNumber and RNANucleotideAccessionNumber
		int alternateGeneNameNumber = 1;
		int RNANucleotideAccessionNumber =1;
		
		int currentLineAlternateGeneNameNumber;
		int currentLineRNANucleotideAccessionNumber;
		
		//GLANET convention is 0_based_start and 0 _based_end
		//UCSC GENOME table browser convention: Our internal database representations of coordinates always have a zero-based start and a one-based end.
		//Convert 1_based_end to 0_based_end
			
		
		FileReader fileReader =null;
	    BufferedReader bufferedReader = null;
	    
	    BufferedWriter bufferedWriter = null;
	   
	    String strLine;
	    
	    String refSeqGeneName;
		ChromosomeName chromName;
		char strand;
		int txStart;
		int txEnd;
//		int cdsStart;
//		int cdsEnd;
		int exonCounts;
		String exonStarts;
		String exonEnds;
		String alternateGeneName;
		
		TIntList exonStartList 	= null;
		TIntList exonEndList 	=null;
		
		int geneID;
		
		int indexofFormerComma;
		int indexofLatterComma;
	    
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		int indexofSixthTab = 0;
		int indexofSeventhTab = 0;
		int indexofEigthTab = 0;
		int indexofNinethTab = 0;
		int indexofTenthTab = 0;
		int indexofEleventhTab = 0;
		int indexofTwelfthTab = 0;
		int indexofThirteenthTab = 0;
		
	    try {
	    	
			fileReader = FileOperations.createFileReader(UCSC_GENOME_HG19_REFSEQ_GENES_FILE);
			bufferedReader = new BufferedReader(fileReader);
			
			//skip headerLine
			//#bin	name	chrom	strand	txStart	txEnd	cdsStart	cdsEnd	exonCount	exonStarts	exonEnds	score	name2	cdsStartStat	cdsEndStat	exonFrames
			strLine = bufferedReader.readLine();
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example strLine
				//0	NM_032291	chr1	+	66999824	67210768	67000041	67208778	25	66999824,67091529,67098752,67101626,67105459,67108492,67109226,67126195,67133212,67136677,67137626,67138963,67142686,67145360,67147551,67154830,67155872,67161116,67184976,67194946,67199430,67205017,67206340,67206954,67208755,	67000051,67091593,67098777,67101698,67105516,67108547,67109402,67126207,67133224,67136702,67137678,67139049,67142779,67145435,67148052,67154958,67155999,67161176,67185088,67195102,67199563,67205220,67206405,67207119,67210768,	0	SGIP1	cmpl	cmpl	0,1,2,0,0,0,1,0,0,0,1,2,1,1,1,1,0,1,1,2,2,0,2,1,1,
				
				indexofFirstTab 		= strLine.indexOf('\t');
				indexofSecondTab 		= (indexofFirstTab>0) 	? strLine.indexOf('\t', indexofFirstTab+1) 	: -1;
				indexofThirdTab 		= (indexofSecondTab>0) 	? strLine.indexOf('\t', indexofSecondTab+1) : -1;
				indexofFourthTab 		= (indexofThirdTab>0) 	? strLine.indexOf('\t', indexofThirdTab+1) 	: -1;
				indexofFifthTab 		= (indexofFourthTab>0) 	? strLine.indexOf('\t', indexofFourthTab+1) : -1;
				indexofSixthTab 		= (indexofFifthTab>0) 	? strLine.indexOf('\t', indexofFifthTab+1) 	: -1;
				indexofSeventhTab 		= (indexofSixthTab>0) 	? strLine.indexOf('\t', indexofSixthTab+1) 	: -1;
				indexofEigthTab 		= (indexofSeventhTab>0) ? strLine.indexOf('\t', indexofSeventhTab+1): -1;
				indexofNinethTab 		= (indexofEigthTab>0) 	? strLine.indexOf('\t', indexofEigthTab+1) 	: -1;
				indexofTenthTab 		= (indexofNinethTab>0) 	? strLine.indexOf('\t', indexofNinethTab+1) : -1;
				indexofEleventhTab 		= (indexofTenthTab>0) 	? strLine.indexOf('\t', indexofTenthTab+1) 	: -1;
				indexofTwelfthTab 		= (indexofEleventhTab>0)? strLine.indexOf('\t', indexofEleventhTab+1) : -1;
				indexofThirteenthTab 	= (indexofTwelfthTab>0)	? strLine.indexOf('\t', indexofTwelfthTab+1): -1;
			
				
				refSeqGeneName = strLine.substring(indexofFirstTab+1, indexofSecondTab);
				chromName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				
				strand = strLine.substring(indexofThirdTab+1, indexofFourthTab).trim().charAt(0);
				
				txStart = Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab));
				//Convert one based end to zero based end
				txEnd = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab))-1;
				
				//cdsStart = Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
				//28FEB2014
				//Convert one based end to zero based end
				//cdsEnd = Integer.parseInt(strLine.substring(indexofSeventhTab+1, indexofEigthTab))-1;
				
				exonCounts = Integer.parseInt(strLine.substring(indexofEigthTab+1, indexofNinethTab));
				exonStarts = strLine.substring(indexofNinethTab+1, indexofTenthTab);
				exonEnds = strLine.substring(indexofTenthTab+1, indexofEleventhTab);
				alternateGeneName = strLine.substring(indexofTwelfthTab+1, indexofThirteenthTab);
				
				//For each strLine
				exonStartList 	= new TIntArrayList(exonCounts);
				exonEndList 	= new TIntArrayList(exonCounts);
				
//				Initialize before for loop
				indexofFormerComma =-1;
				indexofLatterComma = -1;
				
				//Fill exonStartList and exonEndList
				for(int i = 0 ; i<exonCounts; i++){
					indexofFormerComma = indexofLatterComma;
					indexofLatterComma = exonStarts.indexOf(',',indexofFormerComma+1);
					
					exonStartList.add(Integer.parseInt(exonStarts.substring(indexofFormerComma+1,indexofLatterComma)));		
					
					//28FEB2014
					//Convert 1_based_end to 0_based_end
					exonEndList.add(Integer.parseInt(exonEnds.substring(indexofFormerComma+1,indexofLatterComma))-1);
					
				}//End of for: filling exonStartList and exonEndList
				
				//Get geneID
				geneID = rnaNucleotideAccession2GeneIDMap.get(refSeqGeneName);
				
				
				if (chromName!=null){
					
					//Get Unsorted ChromosomeBased bufferedWriter			
					bufferedWriter = FileOperations.getChromosomeBasedBufferedWriter(chromName, UCSCGENOMERefSeqGenesBufferedWriterList);
					
					//Write to Unsorted ChromosomeBased WithNumbers  File
					if (bufferedWriter!=null){
						
						//Create and Write  Exon, Intron, 5p1, 5p2, 5d, 3p1, 3p2, 3d intervals starts
						
						//Update maps starts
						if(!UCSCGENOME_HG19_RefSeq_Genes_GeneSymbol_Name2NumberMap.containsKey(alternateGeneName)){
							UCSCGENOME_HG19_RefSeq_Genes_GeneSymbol_Name2NumberMap.put(alternateGeneName, alternateGeneNameNumber);
							alternateGeneNameNumber++;
						}
						
						if(!UCSCGENOME_HG19_RefSeq_Genes_RNANucleotideAccession_Name2NumberMap.containsKey(refSeqGeneName)){
							UCSCGENOME_HG19_RefSeq_Genes_RNANucleotideAccession_Name2NumberMap.put(refSeqGeneName, RNANucleotideAccessionNumber);
							RNANucleotideAccessionNumber++;
						}
						//Update maps ends
						
						currentLineAlternateGeneNameNumber = UCSCGENOME_HG19_RefSeq_Genes_GeneSymbol_Name2NumberMap.get(alternateGeneName);
						currentLineRNANucleotideAccessionNumber = UCSCGENOME_HG19_RefSeq_Genes_RNANucleotideAccession_Name2NumberMap.get(refSeqGeneName);
						
						//Write EXON and INTRON intervals starts
						writeExonIntronIntervals(chromName,exonCounts,exonStartList,exonEndList,currentLineRNANucleotideAccessionNumber,geneID,strand, currentLineAlternateGeneNameNumber, bufferedWriter);
						//Write EXON and INTRON intervals  ends		
						
						//Write 5p1 5p2 5d 3p1 3p2 3d intervals starts
						write5p3pIntervals(chromName,currentLineRNANucleotideAccessionNumber,geneID,currentLineAlternateGeneNameNumber,txStart,txEnd,strand,bufferedWriter);
						//Write 5p1 5p2 5d 3p1 3p2 3d intervals ends
						
						//Create and Write  Exon, Intron, 5p1, 5p2, 5d, 3p1, 3p2, 3d intervals ends

						
					}//End of if bufferedWriter is not null
					
				}//End of if chromName is not null (for example enum chromName is null for String chr6_apd_hap1)
				
			
			}//End of while : each read strLine
			
			bufferedReader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}

	
	public static void main(String[] args) {
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		
		
		/*************************************************************************************************/
		/***************************SOURCE FILES starts***************************************************/
		/*************************************************************************************************/
		//Downloaded from UCSC Genome Table Browser contains RNA_NUCLEOTIDE_ACCESSION and GENE_SYMBOL
		//THIS FILE IS IN LOCAL DISK
		String UCSC_GENOME_HG19_REFSEQ_GENES_FILE = Commons.LOCAL_DISK_G_DOKTORA_DATA + Commons.UCSCGENOME_HG19_REFSEQ_GENES_DOWNLOADED_18_NOV_2014;
				
		//THIS FILE IS IN GLANET DATA
		//This file is prepared by HumanRefSeq2Gene.java  which uses the downloaded file gene2RefSeq.txt from NCBI. 
		String NCBI_HUMAN_RNANUCLEOTIDEACCESSION_2_GENEID_FILE = dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_DIRECTORYNAME + Commons.NCBI_RNANUCLEOTIDEACCESSION_TO_GENEID_18_NOV_2014;
		/*************************************************************************************************/
		/***************************SOURCE FILES ends*****************************************************/
		/*************************************************************************************************/
		
		//UCSCGENOME HG19 RefSeq Genes RNANucleotideAccession NAME2NUMBER
		TObjectIntMap<String> UCSCGENOME_HG19_RefSeq_Genes_RNANucleotideAccession_Name2NumberMap = new TObjectIntHashMap<String>();
	
		//UCSCGENOME HG19 RefSeq Genes GeneSymbol NAME2NUMBER
		TObjectIntMap<String> UCSCGENOME_HG19_RefSeq_Genes_GeneSymbol_Name2NumberMap = new TObjectIntHashMap<String>();

		//Initialize RNANucleotideAccession 2 GeneID Map
		TObjectIntMap<String> rnaNucleotideAccession2GeneIDMap = new TObjectIntHashMap<String>();
		
		//Initialize ChromosomeBased BufferedWriterList
		List<BufferedWriter> UCSCGENOMERefSeqGenesBufferedWriterList 		= new ArrayList<BufferedWriter>();
		
		//Create BufferedWriters
		FileOperations.createUnsortedChromosomeBasedWithNumbersBufferedWriters(dataFolder,ElementType.HG19_REFSEQ_GENE,UCSCGENOMERefSeqGenesBufferedWriterList);
		
		//Fill RNANucleotideAccession 2 GeneID Map
		createRNANucleotideAccession2GeneIDMap(NCBI_HUMAN_RNANUCLEOTIDEACCESSION_2_GENEID_FILE,rnaNucleotideAccession2GeneIDMap);
		
		//Read UCSC_GENOME_HG19_REFSEQ_GENES_FILE
		//Write Unsorted Chromosome Based With Numbers UCSC HG19 REFSEQ Genes File
		readUCSCGenomeHG19RefSeqGenesFileWriteUnsortedChromBasedFilesWithNumbers(UCSC_GENOME_HG19_REFSEQ_GENES_FILE,UCSCGENOMERefSeqGenesBufferedWriterList,rnaNucleotideAccession2GeneIDMap, UCSCGENOME_HG19_RefSeq_Genes_RNANucleotideAccession_Name2NumberMap,UCSCGENOME_HG19_RefSeq_Genes_GeneSymbol_Name2NumberMap);
		
		//Write UCSCGENOME HG19 REFSEQ GENES
		//Write name2Number maps
		FileOperations.writeName2NumberMap(dataFolder,UCSCGENOME_HG19_RefSeq_Genes_RNANucleotideAccession_Name2NumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_RNANUCLEOTIDEACCESSION_NAME_2_NUMBER_OUTPUT_FILENAME);
		FileOperations.writeName2NumberMap(dataFolder,UCSCGENOME_HG19_RefSeq_Genes_GeneSymbol_Name2NumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NAME_2_NUMBER_OUTPUT_FILENAME);
		
				
		
		//Close BufferedWriters
		FileOperations.closeChromosomeBasedBufferedWriters(UCSCGENOMERefSeqGenesBufferedWriterList);
	}

}
