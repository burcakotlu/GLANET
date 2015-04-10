/**
 * 
 */
package datadrivenexperiment;

import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.hash.TObjectFloatHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import auxiliary.FileOperations;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Apr 8, 2015
 * @project Glanet 
 *
 */
public class GTFFileProcess {
	
	public static void generateIntervalsFromFemaleGTFFile(
			TObjectFloatMap<String> ensemblGeneID2TPMMap,
			String femaleGTFFileName,
			float tpmThreshold, 
			String nonExpressingGenesIntervalsFileName){
		
		List<String> geneIDList = new ArrayList<String>();
		
		int minusOneHundredFromTSS;
		int plusFiveHundredFromTSS;
		
		String strLine  = null;
		String attribute = null;
		
		
		ChromosomeName chrName;
		int low;
		int high;
		
		String ensemblGeneIDWithPair = null;
		String ensemblGeneID = null;
		
		String geneSymbolWithPair = null;
		String geneSymbol = null;
		
		char strand;
		
		String exonNumberWithPair;
		int exonNumber;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		
		int indexofFirstSemiColon;
		int indexofSecondSemiColon;
		int indexofThirdSemiColon;
		int indexofFourthSemiColon;
		int indexofFifthSemiColon;
		int indexofSixthSemiColon;
		int indexofSeventhSemiColon;
		int indexofEigthSemiColon;
		int indexofNinethSemiColon;
		
		try {
			FileReader fileReader = FileOperations.createFileReader(femaleGTFFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			FileWriter fileWriter = FileOperations.createFileWriter(nonExpressingGenesIntervalsFileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			
			//chr1	HAVANA	exon	11869	12227	.	+	.	gene_id "ENSG00000223972.4"; transcript_id "ENST00000456328.2"; gene_type "pseudogene"; gene_status "KNOWN"; gene_name "DDX11L1"; transcript_type "processed_transcript"; transcript_status "KNOWN"; transcript_name "DDX11L1-002"; exon_number 1;  exon_id "ENSE00002234944.1";  level 2; tag "basic"; havana_gene "OTTHUMG00000000961.2"; havana_transcript "OTTHUMT00000362751.1";
			
			
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = (indexofFirstTab>0)?strLine.indexOf('\t',indexofFirstTab+1): -1;
				indexofThirdTab = (indexofSecondTab>0)?strLine.indexOf('\t',indexofSecondTab+1): -1;
				indexofFourthTab = (indexofThirdTab>0)?strLine.indexOf('\t',indexofThirdTab+1): -1;
				indexofFifthTab = (indexofFourthTab>0)?strLine.indexOf('\t',indexofFourthTab+1): -1;
				indexofSixthTab = (indexofFifthTab>0)?strLine.indexOf('\t',indexofFifthTab+1): -1;
				indexofSeventhTab = (indexofSixthTab>0)?strLine.indexOf('\t',indexofSixthTab+1): -1;
				indexofEigthTab = (indexofSeventhTab>0)?strLine.indexOf('\t',indexofSeventhTab+1): -1;
				
				
				chrName = ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab));
				
				low = Integer.parseInt(strLine.substring(indexofThirdTab+1, indexofFourthTab));
				high = Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab));
				
				//For debug purposes starts
				if (low > high) {
					System.out.println("There is a situation. Low: " + low + " High: " + high);
				}
				
				if (low == high) {
					System.out.println("Female.gtf has End Inclusive Coordinates. 0-based or 1-based, I don't know. Low: " + low + " High: " + high);
				}
				
				//For debug purposes ends
				
				
				strand = strLine.substring(indexofSixthTab+1, indexofSeventhTab).charAt(0);
				
				attribute = strLine.substring(indexofEigthTab+1);
				
				indexofFirstSemiColon = attribute.indexOf(';');
				indexofSecondSemiColon = (indexofFirstSemiColon>0)? attribute.indexOf(';',indexofFirstSemiColon+1):-1;
				indexofThirdSemiColon = (indexofSecondSemiColon>0)? attribute.indexOf(';',indexofSecondSemiColon+1):-1;
				indexofFourthSemiColon = (indexofThirdSemiColon>0)? attribute.indexOf(';',indexofThirdSemiColon+1):-1;
				indexofFifthSemiColon = (indexofFourthSemiColon>0)? attribute.indexOf(';',indexofFourthSemiColon+1):-1;
				indexofSixthSemiColon = (indexofFifthSemiColon>0)? attribute.indexOf(';',indexofFifthSemiColon+1):-1;
				indexofSeventhSemiColon = (indexofSixthSemiColon>0)? attribute.indexOf(';',indexofSixthSemiColon+1):-1;
				indexofEigthSemiColon = (indexofSeventhSemiColon>0)? attribute.indexOf(';',indexofSeventhSemiColon+1):-1;
				indexofNinethSemiColon = (indexofEigthSemiColon>0)? attribute.indexOf(';',indexofEigthSemiColon+1):-1;
				
				ensemblGeneIDWithPair = attribute.substring(0, indexofFirstSemiColon);
				ensemblGeneID = ensemblGeneIDWithPair.substring(ensemblGeneIDWithPair.indexOf(' ')+1).replace("\"", "");
				
				
				geneSymbolWithPair = attribute.substring(indexofFourthSemiColon+1, indexofFifthSemiColon);
				geneSymbol = geneSymbolWithPair.replace("gene_name", "").trim();
				
				exonNumberWithPair = attribute.substring(indexofEigthSemiColon+1, indexofNinethSemiColon);
				
				exonNumber = Integer.parseInt(exonNumberWithPair.replace("exon_number","").trim());
				
				if (exonNumber==1){
					
					if (ensemblGeneID2TPMMap.containsKey(ensemblGeneID) &&
							(ensemblGeneID2TPMMap.get(ensemblGeneID) < tpmThreshold) &&
							!geneIDList.contains(ensemblGeneID)){
						
						switch(strand){
						
							case '+': 	minusOneHundredFromTSS = low-100;  
										plusFiveHundredFromTSS = low+500; 
										bufferedWriter.write(chrName.convertEnumtoString() + "\t" + minusOneHundredFromTSS +"\t" +plusFiveHundredFromTSS + "\t" + geneSymbol + System.getProperty("line.separator") );
										break;
										
							case '-': 	minusOneHundredFromTSS = high+100;  
										plusFiveHundredFromTSS = high-500; 
										bufferedWriter.write(chrName.convertEnumtoString() + "\t" + plusFiveHundredFromTSS +"\t" +minusOneHundredFromTSS + "\t" + geneSymbol +  System.getProperty("line.separator") );
										break;
							
						} //End of SWITCH
						
						geneIDList.add(ensemblGeneID);
					}
				}
					
				
			}//End of While
			
			System.out.println("Number of genes that has intervals: " + geneIDList.size());
			
			//Close BufferedReader
			bufferedReader.close();
			bufferedWriter.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static int findNonExpressingGenes(TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2, float tpmThreshold){
		int numberofNonExpressingGenes = 0;
		float tpm;
		
		for(TObjectFloatIterator<String> it = ensemblGeneID2TPMMapforUnionofRep1andRep2.iterator(); it.hasNext(); ){
			it.advance();
			
			tpm = it.value();
			
			if (tpm<tpmThreshold){
				numberofNonExpressingGenes++;
			}
		}//End of for
		
		return numberofNonExpressingGenes;
		
	}
	
	
	public static TObjectFloatMap<String> fillMapUsingGTFFile(String gtfFileNameWithPath, TObjectFloatMap<String> ensemblGeneId2TPMExistingMap){
		
		String strLine = null;
		String ensemblGeneID = null;
		
		float TPM;
		float existingTPM;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		
		int numberofUpdates = 0;
		int numberofExistingGenes = 0;
		int numberofNonExistingGenes = 0;
		
		
		TObjectFloatMap<String> ensemblGeneID2TPMUnionMap = new TObjectFloatHashMap<String>();
		
		try {
			FileReader fileReader =  FileOperations.createFileReader(gtfFileNameWithPath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			
			//Skip header line
			//gene_id	transcript_id(s)	length	effective_length	expected_count	TPM	FPKM
			strLine = bufferedReader.readLine();
					
			//sample line from file
			//ENSG00000000003.10	ENST00000373020.4,ENST00000494424.1,ENST00000496771.1	2206.00	2052.31	5.00	0.05	0.04

			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = (indexofFirstTab>0)?strLine.indexOf('\t',indexofFirstTab+1): -1;
				indexofThirdTab = (indexofSecondTab>0)?strLine.indexOf('\t',indexofSecondTab+1): -1;
				indexofFourthTab = (indexofThirdTab>0)?strLine.indexOf('\t',indexofThirdTab+1): -1;
				indexofFifthTab = (indexofFourthTab>0)?strLine.indexOf('\t',indexofFourthTab+1): -1;
				indexofSixthTab = (indexofFifthTab>0)?strLine.indexOf('\t',indexofFifthTab+1): -1;
				
				ensemblGeneID = strLine.substring(0,indexofFirstTab);
				TPM = Float.parseFloat(strLine.substring(indexofFifthTab+1,indexofSixthTab));
				
				if (ensemblGeneId2TPMExistingMap.containsKey(ensemblGeneID)){
					
					existingTPM = ensemblGeneId2TPMExistingMap.get(ensemblGeneID);
					
					if (TPM > existingTPM){
						ensemblGeneID2TPMUnionMap.put(ensemblGeneID, TPM);
						numberofUpdates++;
					}else{
						ensemblGeneID2TPMUnionMap.put(ensemblGeneID, existingTPM);
					}
					
					numberofExistingGenes++;
					
				}else{
					
					ensemblGeneID2TPMUnionMap.put(ensemblGeneID, TPM);
					numberofNonExistingGenes++;
					
				}
					
			}//End of While
			
			//Close BufferedReader
			bufferedReader.close();
			
			System.out.println("Number of entries in ensemblGeneID2TPMUnionMap for " + gtfFileNameWithPath + " is " + ensemblGeneID2TPMUnionMap.size());
			System.out.println("Number of Updates is: " + numberofUpdates);
			System.out.println("Number of existing genes is: " + numberofExistingGenes);
			System.out.println("Number of non existing genes is: " + numberofNonExistingGenes);
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return ensemblGeneID2TPMUnionMap;
		
	}
	
	public static TObjectFloatMap<String> fillMapUsingGTFFile(String gtfFileNameWithPath){
		
		String strLine = null;
		String ensemblGeneID = null;
		
		float tpm;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		
		
		TObjectFloatMap<String> ensemblGeneID2TPMMap = new TObjectFloatHashMap<String>();
		
		try {
			FileReader fileReader =  FileOperations.createFileReader(gtfFileNameWithPath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			
			//Skip header line
			//gene_id	transcript_id(s)	length	effective_length	expected_count	TPM	FPKM
			strLine = bufferedReader.readLine();
					
			//sample line from file
			//ENSG00000000003.10	ENST00000373020.4,ENST00000494424.1,ENST00000496771.1	2206.00	2052.31	5.00	0.05	0.04

			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = (indexofFirstTab>0)?strLine.indexOf('\t',indexofFirstTab+1): -1;
				indexofThirdTab = (indexofSecondTab>0)?strLine.indexOf('\t',indexofSecondTab+1): -1;
				indexofFourthTab = (indexofThirdTab>0)?strLine.indexOf('\t',indexofThirdTab+1): -1;
				indexofFifthTab = (indexofFourthTab>0)?strLine.indexOf('\t',indexofFourthTab+1): -1;
				indexofSixthTab = (indexofFifthTab>0)?strLine.indexOf('\t',indexofFifthTab+1): -1;
				
				ensemblGeneID = strLine.substring(0,indexofFirstTab);
				tpm = Float.parseFloat(strLine.substring(indexofFifthTab+1,indexofSixthTab));
				
				if (!ensemblGeneID2TPMMap.containsKey(ensemblGeneID)){
					ensemblGeneID2TPMMap.put(ensemblGeneID, tpm);
				}else{
					System.out.println("More than one TPM for the same ensemblGeneID ");
				}
					
			}//End of While
			
			//Close BufferedReader
			bufferedReader.close();
			
			System.out.println("Number of entries in ensemblGeneID2TPMMap for " + gtfFileNameWithPath + " is " + ensemblGeneID2TPMMap.size());
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return ensemblGeneID2TPMMap;
		
		
	}

	
	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");
		
		TObjectFloatMap<String> ensemblGeneID2TPMMapforRep1;
		TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2;
		
		int numberofNonExpressingGenes = 0;
		float tpmThreshold = 1.0f;
			
		//Set GM12878 Replicate1 gtf file with path
		String GM12878Rep1GTFFileName = Commons.LOCAL_DISK_G_GLANET_DATA + Commons.RNA_SEQ_GM12878_K562 + System.getProperty("file.separator") + Commons.Gm12878Rep1_genes_results; 

		//Set GM12878 Replicate2 gtf file with path
		String GM12878Rep2GTFFileName = Commons.LOCAL_DISK_G_GLANET_DATA + Commons.RNA_SEQ_GM12878_K562 + System.getProperty("file.separator") + Commons.Gm12878Rep2_genes_results; 

		//Set female.gtf file with path
		String femaleGTFFileName = Commons.LOCAL_DISK_G_GLANET_DATA + Commons.RNA_SEQ_GM12878_K562 + System.getProperty("file.separator") + Commons.female_gtf; 

		//Set NonExpressingGenesIntervalsFile
		String nonExpressingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") + "NonExpressingGenesIntervals_EndInclusive.txt";
		
		//Read gtf file and fillEnsemblGeneID2TPMMapRep1
		ensemblGeneID2TPMMapforRep1 = fillMapUsingGTFFile(GM12878Rep1GTFFileName);
		
		//Read gtf file and fillEnsemblGeneID2TPMUnion MapRep2
		ensemblGeneID2TPMMapforUnionofRep1andRep2 = fillMapUsingGTFFile(GM12878Rep2GTFFileName,ensemblGeneID2TPMMapforRep1);
		
		//Generate Intervals for the genes in the ensemblGeneID2TPMMapforUnionofRep1andRep2 using female.gtf
		
		//Find the number of non expressing genes
		numberofNonExpressingGenes = findNonExpressingGenes(ensemblGeneID2TPMMapforUnionofRep1andRep2,tpmThreshold);
		System.out.println("Number of Non Expressing Genes: " + numberofNonExpressingGenes);
		
		
		//Generate Intervals from female.gtf file
		generateIntervalsFromFemaleGTFFile(ensemblGeneID2TPMMapforUnionofRep1andRep2,femaleGTFFileName,tpmThreshold,nonExpressingGenesIntervalsFile);
		
		
		
		
		
	}

}
