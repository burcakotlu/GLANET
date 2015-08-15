package datadrivenexperiment;

import enumtypes.ChromosomeName;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.hash.TObjectFloatHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;

import common.Commons;

/**
 * @author Burcak Otlu
 * @date Apr 8, 2015
 * @project Glanet 
 * 
 * Data Driven Experiment Step 1
 *
 * In this class 
 * We create interval pool of nonExpressing protein coding genes 
 * Depending on the given TPM (Transcription Per Million)
 * Where each interval is 600 base long.
 * 
 * By the way, there can be more than one transcript for a nonExpressing protein coding gene.
 * In this case we consider the transcript with the lowest exon1 start for the "+" strand,
 * or the transcript with the highest exon1 end for the "-" strand.
 * 
 */
public class Step1_NonExpressingProteinCodingGenesIntervalsPoolCreation {

	public static void generateIntervalsFromFemaleGTFFile(
			TObjectFloatMap<String> ensemblGeneID2TPMMap,
			String femaleGTFFileName, 
			float tpmThreshold, 
			String nonExpressingGenesIntervalsFileName) {

		// List<String> geneIDList = new ArrayList<String>();
		Map<String, List<ProteinCodingGeneExonNumberOneInterval>> geneId2GeneExonNumberOneIntervalsMap = new HashMap<String, List<ProteinCodingGeneExonNumberOneInterval>>();
		List<ProteinCodingGeneExonNumberOneInterval> proteinCodingGeneExonNumberOneIntervalList = null;
		ProteinCodingGeneExonNumberOneInterval geneExonNumberOneInterval = null;

		int minLow = Integer.MAX_VALUE;
		int maxHigh = Integer.MIN_VALUE;
		int savedIndex = 0;

		List<String> geneTypesList = new ArrayList<String>();

		int numberofIntervalsCreated = 0;

		int minusOneHundredFromTSS;
		int plusFiveHundredFromTSS;

		String strLine = null;
		String attribute = null;

		ChromosomeName chrName;
		int low;
		int high;

		String ensemblGeneIDWithPair = null;
		String ensemblGeneID = null;

		String ensemblTranscriptIDWithPair = null;
		String ensemblTranscriptID = null;

		String geneTypeWithPair = null;
		String geneType = null;

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

		try{

			FileReader fileReader = FileOperations.createFileReader( femaleGTFFileName);
			BufferedReader bufferedReader = new BufferedReader( fileReader);

			FileWriter fileWriter = FileOperations.createFileWriter( nonExpressingGenesIntervalsFileName);
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter);

			// chr1 HAVANA exon 11869 12227 . + . gene_id "ENSG00000223972.4"; transcript_id "ENST00000456328.2";
			// gene_type "pseudogene"; gene_status "KNOWN"; gene_name "DDX11L1"; transcript_type "processed_transcript";
			// transcript_status "KNOWN"; transcript_name "DDX11L1-002"; exon_number 1; exon_id "ENSE00002234944.1";
			// level 2; tag "basic"; havana_gene "OTTHUMG00000000961.2"; havana_transcript "OTTHUMT00000362751.1";

			// First Read the femaleGTFFile
			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
				indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
				indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
				indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
				indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;
				indexofSeventhTab = ( indexofSixthTab > 0)?strLine.indexOf( '\t', indexofSixthTab + 1):-1;
				indexofEigthTab = ( indexofSeventhTab > 0)?strLine.indexOf( '\t', indexofSeventhTab + 1):-1;

				chrName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));

				low = Integer.parseInt( strLine.substring( indexofThirdTab + 1, indexofFourthTab));
				high = Integer.parseInt( strLine.substring( indexofFourthTab + 1, indexofFifthTab));

				// For debug purposes starts
				if( low > high){
					System.out.println( "There is a situation. Low: " + low + " High: " + high);
				}

				if( low == high){
					// System.out.println("Female.gtf has End Inclusive Coordinates. 0-based or 1-based, I don't know. Low: "
					// + low + " High: " + high);
				}
				// For debug purposes ends

				strand = strLine.substring( indexofSixthTab + 1, indexofSeventhTab).charAt( 0);

				attribute = strLine.substring( indexofEigthTab + 1);

				indexofFirstSemiColon = attribute.indexOf( ';');
				indexofSecondSemiColon = ( indexofFirstSemiColon > 0)?attribute.indexOf( ';', indexofFirstSemiColon + 1):-1;
				indexofThirdSemiColon = ( indexofSecondSemiColon > 0)?attribute.indexOf( ';',
						indexofSecondSemiColon + 1):-1;
				indexofFourthSemiColon = ( indexofThirdSemiColon > 0)?attribute.indexOf( ';', indexofThirdSemiColon + 1):-1;
				indexofFifthSemiColon = ( indexofFourthSemiColon > 0)?attribute.indexOf( ';',
						indexofFourthSemiColon + 1):-1;
				indexofSixthSemiColon = ( indexofFifthSemiColon > 0)?attribute.indexOf( ';', indexofFifthSemiColon + 1):-1;
				indexofSeventhSemiColon = ( indexofSixthSemiColon > 0)?attribute.indexOf( ';',
						indexofSixthSemiColon + 1):-1;
				indexofEigthSemiColon = ( indexofSeventhSemiColon > 0)?attribute.indexOf( ';',
						indexofSeventhSemiColon + 1):-1;
				indexofNinethSemiColon = ( indexofEigthSemiColon > 0)?attribute.indexOf( ';', indexofEigthSemiColon + 1):-1;

				// geneID
				ensemblGeneIDWithPair = attribute.substring( 0, indexofFirstSemiColon);
				ensemblGeneID = ensemblGeneIDWithPair.substring( ensemblGeneIDWithPair.indexOf( ' ') + 1).replace(
						"\"", "");

				// transcriptID
				ensemblTranscriptIDWithPair = attribute.substring( indexofFirstSemiColon + 1, indexofSecondSemiColon).trim();
				ensemblTranscriptID = ensemblTranscriptIDWithPair.substring(
						ensemblTranscriptIDWithPair.indexOf( ' ') + 1).replace( "\"", "");

				// geneSymbol
				geneSymbolWithPair = attribute.substring( indexofFourthSemiColon + 1, indexofFifthSemiColon);
				geneSymbol = geneSymbolWithPair.replace( "gene_name", "").trim();

				geneTypeWithPair = attribute.substring( indexofSecondSemiColon + 1, indexofThirdSemiColon);
				geneType = geneTypeWithPair.replace( "gene_type", "").trim();

				if( !geneTypesList.contains( geneType)){
					geneTypesList.add( geneType);
				}

				exonNumberWithPair = attribute.substring( indexofEigthSemiColon + 1, indexofNinethSemiColon);

				exonNumber = Integer.parseInt( exonNumberWithPair.replace( "exon_number", "").trim());

				if(exonNumber == 1){

					if( ensemblGeneID2TPMMap.containsKey(ensemblGeneID) && ( ensemblGeneID2TPMMap.get( ensemblGeneID) <= tpmThreshold) && geneType.equals( "\"protein_coding\"")){

						switch( strand){

						case '+':
							minusOneHundredFromTSS = low - 500;
							plusFiveHundredFromTSS = low + 100;
							geneExonNumberOneInterval = new ProteinCodingGeneExonNumberOneInterval( strand,
									minusOneHundredFromTSS, plusFiveHundredFromTSS, chrName, ensemblTranscriptID,
									geneSymbol);
							break;

						case '-':
							minusOneHundredFromTSS = high + 500;
							plusFiveHundredFromTSS = high - 100;
							geneExonNumberOneInterval = new ProteinCodingGeneExonNumberOneInterval( strand,
									plusFiveHundredFromTSS, minusOneHundredFromTSS, chrName, ensemblTranscriptID,
									geneSymbol);
							break;

						} // End of SWITCH

						proteinCodingGeneExonNumberOneIntervalList = geneId2GeneExonNumberOneIntervalsMap.get( ensemblGeneID);

						if( proteinCodingGeneExonNumberOneIntervalList == null){
							proteinCodingGeneExonNumberOneIntervalList = new ArrayList<ProteinCodingGeneExonNumberOneInterval>();
							proteinCodingGeneExonNumberOneIntervalList.add( geneExonNumberOneInterval);
							geneId2GeneExonNumberOneIntervalsMap.put( ensemblGeneID,
									proteinCodingGeneExonNumberOneIntervalList);
						}else{
							proteinCodingGeneExonNumberOneIntervalList.add( geneExonNumberOneInterval);
						}

						// In order to generate only one interval for the first occurrence of exon number 1 of a certain
						// ensemblGeneID
						// geneIDList.add(ensemblGeneID);

					}// End of IF: For this geneId, interval will be created.

				}// End of IF: Exon Number is 1

			}// End of While
			// Reading female.gtf file is accomplished

			// Writing NonExpressingGenes Intervals starts
			for( Map.Entry<String, List<ProteinCodingGeneExonNumberOneInterval>> entry : geneId2GeneExonNumberOneIntervalsMap.entrySet()){

				numberofIntervalsCreated++;

				minLow = Integer.MAX_VALUE;
				maxHigh = Integer.MIN_VALUE;
				savedIndex = 0;

				// entry.getKey()
				proteinCodingGeneExonNumberOneIntervalList = entry.getValue();

				// get the strand
				// there is at least one interval
				strand = proteinCodingGeneExonNumberOneIntervalList.get( 0).getStrand();

				// Select which transcript to write
				// Find the lowest low for "+" genes
				// Find the highest high for "-" genes
				switch( strand){
				
					case '+':
						for( int i = 0; i < proteinCodingGeneExonNumberOneIntervalList.size(); i++){
	
							geneExonNumberOneInterval = proteinCodingGeneExonNumberOneIntervalList.get( i);
	
							if( geneExonNumberOneInterval.getLow() < minLow){
								minLow = geneExonNumberOneInterval.getLow();
								savedIndex = i;
							}// End of IF
	
						}// End of FOR
						break;
	
					case '-':
						for( int i = 0; i < proteinCodingGeneExonNumberOneIntervalList.size(); i++){
	
							geneExonNumberOneInterval = proteinCodingGeneExonNumberOneIntervalList.get( i);
	
							if( geneExonNumberOneInterval.getHigh() > maxHigh){
								maxHigh = geneExonNumberOneInterval.getHigh();
								savedIndex = i;
							}// End of IF
	
						}// End of FOR
	
						break;
					
				}// End of SWITCH

				// Now write
				geneExonNumberOneInterval = proteinCodingGeneExonNumberOneIntervalList.get( savedIndex);
				bufferedWriter.write( geneExonNumberOneInterval.getChrName().convertEnumtoString() + "\t" + geneExonNumberOneInterval.getLow() + "\t" + geneExonNumberOneInterval.getHigh() + "\t" + geneExonNumberOneInterval.getGeneSymbol() + System.getProperty( "line.separator"));

			}// End of for
				// Writing NonExpressingGenes Intervals ends

			System.out.println("Number of NonExpressing Protein Coding Genes intervals created: " + numberofIntervalsCreated + " under " +  nonExpressingGenesIntervalsFileName);

			// Close BufferedReader
			bufferedReader.close();
			bufferedWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int findNonExpressingGenes( TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2,
			float tpmThreshold) {

		int numberofNonExpressingGenes = 0;
		float tpm;

		for( TObjectFloatIterator<String> it = ensemblGeneID2TPMMapforUnionofRep1andRep2.iterator(); it.hasNext();){
			it.advance();

			tpm = it.value();

			// Less than or equal to
			if( tpm <= tpmThreshold){
				numberofNonExpressingGenes++;
			}
		}// End of for

		return numberofNonExpressingGenes;

	}

	public static TObjectFloatMap<String> fillMapUsingGTFFile( String gtfFileNameWithPath,
			TObjectFloatMap<String> ensemblGeneId2TPMExistingMap) {

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

		try{
			FileReader fileReader = FileOperations.createFileReader( gtfFileNameWithPath);
			BufferedReader bufferedReader = new BufferedReader( fileReader);

			// Skip header line
			// gene_id transcript_id(s) length effective_length expected_count TPM FPKM
			strLine = bufferedReader.readLine();

			// sample line from file
			// ENSG00000000003.10 ENST00000373020.4,ENST00000494424.1,ENST00000496771.1 2206.00 2052.31 5.00 0.05 0.04

			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
				indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
				indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
				indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
				indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;

				ensemblGeneID = strLine.substring( 0, indexofFirstTab);
				TPM = Float.parseFloat( strLine.substring( indexofFifthTab + 1, indexofSixthTab));

				// This ensemblGeneID already exists
				if( ensemblGeneId2TPMExistingMap.containsKey( ensemblGeneID)){

					existingTPM = ensemblGeneId2TPMExistingMap.get( ensemblGeneID);

					if( TPM > existingTPM){
						ensemblGeneID2TPMUnionMap.put( ensemblGeneID, TPM);
						numberofUpdates++;
					}else{
						ensemblGeneID2TPMUnionMap.put( ensemblGeneID, existingTPM);
					}

					numberofExistingGenes++;

				}
				// This ensemblGeneID is seen for the first time
				else{

					ensemblGeneID2TPMUnionMap.put( ensemblGeneID, TPM);
					numberofNonExistingGenes++;

				}

			}// End of While

			// Close BufferedReader
			bufferedReader.close();

			System.out.println( "Number of entries in ensemblGeneID2TPMUnionMap for " + gtfFileNameWithPath + " is " + ensemblGeneID2TPMUnionMap.size());
			System.out.println( "Number of Updates is: " + numberofUpdates);
			System.out.println( "Number of existing genes is: " + numberofExistingGenes);
			System.out.println( "Number of non existing genes is: " + numberofNonExistingGenes);

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ensemblGeneID2TPMUnionMap;

	}

	public static TObjectFloatMap<String> fillMapUsingGTFFile( String gtfFileNameWithPath) {

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

		try{
			FileReader fileReader = FileOperations.createFileReader( gtfFileNameWithPath);
			BufferedReader bufferedReader = new BufferedReader( fileReader);

			// Skip header line
			// gene_id transcript_id(s) length effective_length expected_count TPM FPKM
			strLine = bufferedReader.readLine();

			// sample line from file
			// ENSG00000000003.10 ENST00000373020.4,ENST00000494424.1,ENST00000496771.1 2206.00 2052.31 5.00 0.05 0.04

			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
				indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
				indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
				indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
				indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;

				ensemblGeneID = strLine.substring( 0, indexofFirstTab);
				tpm = Float.parseFloat( strLine.substring( indexofFifthTab + 1, indexofSixthTab));

				if( !ensemblGeneID2TPMMap.containsKey( ensemblGeneID)){
					ensemblGeneID2TPMMap.put( ensemblGeneID, tpm);
				}else{
					System.out.println( "More than one TPM for the same ensemblGeneID ");
				}

			}// End of While

			// Close BufferedReader
			bufferedReader.close();

			System.out.println( "Number of entries in ensemblGeneID2TPMMap for " + gtfFileNameWithPath + " is " + ensemblGeneID2TPMMap.size());

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ensemblGeneID2TPMMap;

	}

	public static String getTPMString( float tpmThreshold) {

		//TPM values for Expressing Genes
		if( tpmThreshold == 1f)
			return Commons.TPM_1;
		
		else if (tpmThreshold == 10f)
			return Commons.TPM_10;
		
		else if (tpmThreshold == 100f)
			return Commons.TPM_100;
		
		
		//TPM values for NonExpressing Genes
		else if( tpmThreshold == 0f)
			return Commons.TPM_0;

		else if( tpmThreshold == 0.1f)
			return Commons.TPM_0_1;

		else if( tpmThreshold == 0.01f)
			return Commons.TPM_0_01;

		else if( tpmThreshold == 0.001f)
			return Commons.TPM_0_001;

		else if( tpmThreshold == 0.0001f)
			return Commons.TPM_0_0001;

		else if( tpmThreshold == 0.00001f)
			return Commons.TPM_0_00001;

		else if( tpmThreshold == 0.000001f)
			return Commons.TPM_0_000001;
		
		//TPM Unknown
		else
			return Commons.TPM_UNKNOWN;

	}

	/*
	 * args[0] = 	Glanet Folder (which is the parent of Data folder)
	 * args[1] = 	TPM value (0.1, 0.01, 0.001) for NonExpressingProteinCodingGenesIntervals pool creation or
	 * 				TPM value (1, 10, 100) for ExpressingProteinCodingGenesIntervals pool creation
	 */
	public static void main( String[] args) {

		String glanetFolder = args[0];
		
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");

		// This will be filled while reading GM12878 Rep1
		TObjectFloatMap<String> ensemblGeneID2TPMMapforRep1;

		// This will be filled while reading GM12878 Rep2
		TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2;

		int numberofNonExpressingGenes = 0;

		// We will create the interval pool of nonExpressingGenes intervals (of 600 base long) for various TPM Values
		// Such as 0.1f, 0.01f, 0.001f
		// float tpmThreshold = 0.1f;
		// float tpmThreshold = 0.01f;
		float tpmThreshold = Float.parseFloat( args[1]);

		// Input File
		// Set GM12878 Replicate1 gtf file with path
		String GM12878Rep1GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.Gm12878Rep1_genes_results;

		// Input File
		// Set GM12878 Replicate2 gtf file with path
		String GM12878Rep2GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.Gm12878Rep2_genes_results;

		// Input File
		// Set female.gtf file with path
		String femaleGTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.female_gtf;

		// Output File
		// Set NonExpressingGenesIntervalsFile
		String TPMString = getTPMString( tpmThreshold);
		String nonExpressingProteinCodingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") + TPMString + "_" + Commons.NON_EXPRESSING_PROTEIN_CODING_GENES + "Intervals_EndInclusive.txt";

		// Read GM12878 Rep1 results file and fill ensemblGeneID2TPMMapRep1
		ensemblGeneID2TPMMapforRep1 = fillMapUsingGTFFile(GM12878Rep1GTFFileName);

		// Read GM12878 Rep2 results file and fill ensemblGeneID2TPMMapforUnionofRep1andRep2
		ensemblGeneID2TPMMapforUnionofRep1andRep2 = fillMapUsingGTFFile(GM12878Rep2GTFFileName,ensemblGeneID2TPMMapforRep1);

		// Just for information
		// Otherwise no need
		// Find the number of non expressing genes
		numberofNonExpressingGenes = findNonExpressingGenes( ensemblGeneID2TPMMapforUnionofRep1andRep2, tpmThreshold);
		System.out.println("Number of NonExpressing Genes: " + numberofNonExpressingGenes + " for tmpThreshod: " + tpmThreshold);

		// Generate Intervals for the non expressing genes in the ensemblGeneID2TPMMapforUnionofRep1andRep2 according to
		// the tmpThreshold using female.gtf to nonExpressingGenesIntervalsFile
		generateIntervalsFromFemaleGTFFile(
				ensemblGeneID2TPMMapforUnionofRep1andRep2, 
				femaleGTFFileName, 
				tpmThreshold,
				nonExpressingProteinCodingGenesIntervalsFile);

	}

}
