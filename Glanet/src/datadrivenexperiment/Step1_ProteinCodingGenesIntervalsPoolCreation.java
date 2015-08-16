/**
 * 
 */
package datadrivenexperiment;

import enumtypes.ChromosomeName;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.map.TObjectFloatMap;

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
 * @author Burçak Otlu
 * @date Aug 16, 2015
 * @project Glanet 
 *
 */
public class Step1_ProteinCodingGenesIntervalsPoolCreation {
	
	
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

	
	public static int findExpressingGenes(
			TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2,
			float tpmThreshold) {

		int numberofExpressingGenes = 0;
		float tpm;

		for( TObjectFloatIterator<String> it = ensemblGeneID2TPMMapforUnionofRep1andRep2.iterator(); it.hasNext();){
			it.advance();

			tpm = it.value();

			// Less than or equal to
			if( tpm > tpmThreshold){
				numberofExpressingGenes++;
			}
		}// End of for

		return numberofExpressingGenes;

	}
	

	/*
	 * args[0] = 	Glanet Folder (which is the parent of Data folder)
	 * args[1] =	CellLine GM12878 or K562
	 * args[2] =	ExpressingProteinCodingGenes or NonExpressingProteinCodingGenes
	 * args[1] = 	TPM value (1, 10, 100) for ExpressingProteinCodingGenesIntervals pool creation
	 * 				or TPM value (0.1, 0.01, 0.001) for NonExpressingProteinCodingGenesIntervals pool creation 
	 */
	public static void main( String[] args) {

		String glanetFolder = args[0];
		
		String cellLine = args[1];
		
		String expressingorNonExpressingProteinCodingGenes = args[2];
		
		// We will create the interval pool of nonExpressingGenes intervals (of 600 base long) for various TPM Values
		// Such as 0.1f, 0.01f, 0.001f
		// float tpmThreshold = 0.1f;
		// float tpmThreshold = 0.01f;
		float tpmThreshold = Float.parseFloat( args[3]);

		
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");

		// This will be filled while reading cellLine Rep1
		TObjectFloatMap<String> ensemblGeneID2TPMMapforRep1;

		// This will be filled while reading cellLine Rep2
		TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2;

		int numberofNonExpressingGenes = 0;
		int numberofExpressingGenes = 0;

		String cellLineRep1GTFFileName = null;
		String cellLineRep2GTFFileName = null;
		
		if (cellLine.equalsIgnoreCase(Commons.GM12878)){
			// Input File
			// Set GM12878 Replicate1 gtf file with path
			cellLineRep1GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.Gm12878Rep1_genes_results;

			// Input File
			// Set GM12878 Replicate2 gtf file with path
			cellLineRep2GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.Gm12878Rep2_genes_results;

		}  else if (cellLine.equalsIgnoreCase(Commons.K562)){
			// Input File
			// Set GM12878 Replicate1 gtf file with path
			cellLineRep1GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.K562Rep1_genes_results;

			// Input File
			// Set GM12878 Replicate2 gtf file with path
			cellLineRep2GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.K562Rep2_genes_results;

		}

		// Input File
		// Set female.gtf file with path
		String femaleGTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.female_gtf;

		// Output File
		// Set NonExpressingGenesIntervalsFile
		String TPMString = DataDrivenExperimentCommon.getTPMString( tpmThreshold);
		String nonExpressingProteinCodingGenesIntervalsFile = null;
		String expressingProteinCodingGenesIntervalsFile = null;
		
		if (expressingorNonExpressingProteinCodingGenes.equalsIgnoreCase(Commons.NON_EXPRESSING_PROTEIN_CODING_GENES)){
			nonExpressingProteinCodingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") + TPMString + "_" + Commons.NON_EXPRESSING_PROTEIN_CODING_GENES + "Intervals_EndInclusive.txt";

		}else if (expressingorNonExpressingProteinCodingGenes.equalsIgnoreCase(Commons.EXPRESSING_PROTEIN_CODING_GENES)){
			expressingProteinCodingGenesIntervalsFile = dataFolder + Commons.demo_input_data + System.getProperty("file.separator") + TPMString + "_" + Commons.EXPRESSING_PROTEIN_CODING_GENES + "Intervals_EndInclusive.txt";

		}

		// Read GM12878 Rep1 results file and fill ensemblGeneID2TPMMapRep1
		ensemblGeneID2TPMMapforRep1 = DataDrivenExperimentCommon.fillMapUsingGTFFile(cellLineRep1GTFFileName);

		// Read GM12878 Rep2 results file and fill ensemblGeneID2TPMMapforUnionofRep1andRep2
		ensemblGeneID2TPMMapforUnionofRep1andRep2 = DataDrivenExperimentCommon.fillMapUsingGTFFile(cellLineRep2GTFFileName,ensemblGeneID2TPMMapforRep1);

		// Just for information
		// Otherwise no need
		// Find the number of non expressing genes
		if(expressingorNonExpressingProteinCodingGenes.equalsIgnoreCase(Commons.NON_EXPRESSING_PROTEIN_CODING_GENES)){
			numberofNonExpressingGenes = findNonExpressingGenes( ensemblGeneID2TPMMapforUnionofRep1andRep2, tpmThreshold);
			System.out.println("Number of NonExpressing Genes: " + numberofNonExpressingGenes + " for tmpThreshod: " + tpmThreshold);

		}else if (expressingorNonExpressingProteinCodingGenes.equalsIgnoreCase(Commons.EXPRESSING_PROTEIN_CODING_GENES)){
			numberofExpressingGenes = findExpressingGenes( ensemblGeneID2TPMMapforUnionofRep1andRep2, tpmThreshold);
			System.out.println("Number of Expressing Genes: " + numberofNonExpressingGenes + " for tmpThreshod: " + tpmThreshold);

		}

		// Generate Intervals for the non expressing genes in the ensemblGeneID2TPMMapforUnionofRep1andRep2 according to
		// the tmpThreshold using female.gtf to nonExpressingGenesIntervalsFile
		
		if (expressingorNonExpressingProteinCodingGenes.equalsIgnoreCase(Commons.NON_EXPRESSING_PROTEIN_CODING_GENES)){
			generateIntervalsFromFemaleGTFFile(
					ensemblGeneID2TPMMapforUnionofRep1andRep2, 
					femaleGTFFileName, 
					tpmThreshold,
					nonExpressingProteinCodingGenesIntervalsFile);
		}else if (expressingorNonExpressingProteinCodingGenes.equalsIgnoreCase(Commons.EXPRESSING_PROTEIN_CODING_GENES)){
			generateIntervalsFromFemaleGTFFile(
					ensemblGeneID2TPMMapforUnionofRep1andRep2, 
					femaleGTFFileName, 
					tpmThreshold,
					expressingProteinCodingGenesIntervalsFile);
		
		}
		
		

	}

}
