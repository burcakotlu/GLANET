/**
 * 
 */
package datadrivenexperiment;

import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.map.TObjectFloatMap;
import intervaltree.IntervalTreeNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTPMType;
import enumtypes.NodeType;

/**
 * @author Burçak Otlu
 * @date Aug 16, 2015
 * @project Glanet 
 * 
 * Step1_ProteinCodingGenesIntervalsPoolCreation class 
 * 
 * Firstly chooses the 	protein coding genes having tpm greater than the given tpmThreshold for expressing genes
 * 						or protein coding genes having tpm less than the tpmThreshold for nonExpressing genes
 * 
 * Secondly creates interval pool from these chosen protein coding genes.
 * 
 * 
 *
 */
public class Step1_ProteinCodingGenesIntervalPoolCreation {
	
	
	public static void convertIntervalTreeNode2ProteinCodingGeneInterval(
			List<IntervalTreeNode> nonOverlappingIntervalNodeList,
			List<ProteinCodingGeneExonNumberOneInterval> nonOverlappingAllProteinCodingGenesExonNumberOneIntervalsList){
		
		DataDrivenExperimentIntervalTreeNode ddeIntervalNode = null;
		ProteinCodingGeneExonNumberOneInterval proteinCodingGeneExonNumberOneInterval = null;
		
		ChromosomeName chrName;
		int low;
		int high;
		
		String geneSymbol;
		Float tpm;
		
		for(int i = 0; i<nonOverlappingIntervalNodeList.size(); i++ ){
			
			ddeIntervalNode = (DataDrivenExperimentIntervalTreeNode) nonOverlappingIntervalNodeList.get(i);
			
			chrName = ddeIntervalNode.getChromName();
			low = ddeIntervalNode.getLow();
			high = ddeIntervalNode.getHigh();
			
			geneSymbol = ddeIntervalNode.getGeneSymbol();
			tpm = ddeIntervalNode.getTpm();
			
			proteinCodingGeneExonNumberOneInterval = new ProteinCodingGeneExonNumberOneInterval(chrName,low,high,geneSymbol,tpm);
			nonOverlappingAllProteinCodingGenesExonNumberOneIntervalsList.add(proteinCodingGeneExonNumberOneInterval);
			
		}//End of FOR
		
	}
	
	
	public static void convertProteinCodingGeneInterval2IntervalTreeNode(
			List<ProteinCodingGeneExonNumberOneInterval> allProteinCodingGenesExonNumberOneIntervalsList,
			List<IntervalTreeNode> intervalNodeList){
		
		DataDrivenExperimentIntervalTreeNode ddeIntervalNode = null;
		ProteinCodingGeneExonNumberOneInterval proteinCodingGeneExonNumberOneInterval = null;
		
		ChromosomeName chrName;
		int low;
		int high;
		
		String geneSymbol;
		Float tpm;
		
		for(int i = 0; i<allProteinCodingGenesExonNumberOneIntervalsList.size(); i++ ){
			
			proteinCodingGeneExonNumberOneInterval = allProteinCodingGenesExonNumberOneIntervalsList.get(i);
			
			chrName =proteinCodingGeneExonNumberOneInterval.getChrName();
			low = proteinCodingGeneExonNumberOneInterval.getLow();
			high = proteinCodingGeneExonNumberOneInterval.getHigh();
			
			geneSymbol = proteinCodingGeneExonNumberOneInterval.getGeneSymbol();
			tpm = proteinCodingGeneExonNumberOneInterval.getTpm();
			
			ddeIntervalNode = new DataDrivenExperimentIntervalTreeNode(chrName,low,high,geneSymbol,tpm,NodeType.ORIGINAL);
			intervalNodeList.add(ddeIntervalNode);
			
		}//End of FOR
		
	}
	
	
	public static void generateIntervalsFromFemaleGTFFile(
			TObjectFloatMap<String> ensemblGeneID2TPMMap,
			String femaleGTFFileName, 
			Float tpmValue,
			DataDrivenExperimentTPMType tpmType, 
			BufferedWriter tpmBufferedWriter,
			String proteinCodingGenesIntervalsFileName,
			DataDrivenExperimentGeneType expressingorNonExpressiongGeneType) throws IOException {

		Map<String, List<ProteinCodingGeneExonNumberOneInterval>> proteinCodingGeneId2GeneExonNumberOneIntervalsMap = new HashMap<String, List<ProteinCodingGeneExonNumberOneInterval>>();
		List<ProteinCodingGeneExonNumberOneInterval> proteinCodingGeneExonNumberOneIntervalList = null;
		ProteinCodingGeneExonNumberOneInterval proteinCodingGeneExonNumberOneInterval = null;

		int minLow = Integer.MAX_VALUE;
		int maxHigh = Integer.MIN_VALUE;
		int savedIndex = 0;

		List<String> geneTypesList = new ArrayList<String>();

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

		FileReader fileReader = FileOperations.createFileReader( femaleGTFFileName);
		BufferedReader bufferedReader = new BufferedReader( fileReader);

		FileWriter proteinCodingGenesIntervalsFileWriter = FileOperations.createFileWriter(proteinCodingGenesIntervalsFileName);
		BufferedWriter proteinCodingGenesIntervalsBufferedWriter = new BufferedWriter(proteinCodingGenesIntervalsFileWriter);
		
		// chr1 HAVANA exon 11869 12227 . + . gene_id "ENSG00000223972.4"; transcript_id "ENST00000456328.2";
		// gene_type "pseudogene"; gene_status "KNOWN"; gene_name "DDX11L1"; transcript_type "processed_transcript";
		// transcript_status "KNOWN"; transcript_name "DDX11L1-002"; exon_number 1; exon_id "ENSE00002234944.1";
		// level 2; tag "basic"; havana_gene "OTTHUMG00000000961.2"; havana_transcript "OTTHUMT00000362751.1";

		// First Read the femaleGTFFile
		while( ( strLine = bufferedReader.readLine()) != null){

			indexofFirstTab 	= strLine.indexOf( '\t');
			indexofSecondTab 	= ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
			indexofThirdTab 	= ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
			indexofFourthTab 	= ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
			indexofFifthTab 	= ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
			indexofSixthTab 	= ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;
			indexofSeventhTab 	= ( indexofSixthTab > 0)?strLine.indexOf( '\t', indexofSixthTab + 1):-1;
			indexofEigthTab 	= ( indexofSeventhTab > 0)?strLine.indexOf( '\t', indexofSeventhTab + 1):-1;

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

			indexofFirstSemiColon 	= attribute.indexOf( ';');
			indexofSecondSemiColon 	= ( indexofFirstSemiColon > 0)?attribute.indexOf( ';', indexofFirstSemiColon + 1):-1;
			indexofThirdSemiColon 	= ( indexofSecondSemiColon > 0)?attribute.indexOf( ';',indexofSecondSemiColon + 1):-1;
			indexofFourthSemiColon 	= ( indexofThirdSemiColon > 0)?attribute.indexOf( ';', indexofThirdSemiColon + 1):-1;
			indexofFifthSemiColon 	= ( indexofFourthSemiColon > 0)?attribute.indexOf( ';',indexofFourthSemiColon + 1):-1;
			indexofSixthSemiColon 	= ( indexofFifthSemiColon > 0)?attribute.indexOf( ';', indexofFifthSemiColon + 1):-1;
			indexofSeventhSemiColon = ( indexofSixthSemiColon > 0)?attribute.indexOf( ';',indexofSixthSemiColon + 1):-1;
			indexofEigthSemiColon 	= ( indexofSeventhSemiColon > 0)?attribute.indexOf( ';',indexofSeventhSemiColon + 1):-1;
			indexofNinethSemiColon 	= ( indexofEigthSemiColon > 0)?attribute.indexOf( ';', indexofEigthSemiColon + 1):-1;

			// geneID
			ensemblGeneIDWithPair = attribute.substring( 0, indexofFirstSemiColon);
			ensemblGeneID = ensemblGeneIDWithPair.substring( ensemblGeneIDWithPair.indexOf( ' ') + 1).replace("\"", "");

			// transcriptID
			ensemblTranscriptIDWithPair = attribute.substring( indexofFirstSemiColon + 1, indexofSecondSemiColon).trim();
			ensemblTranscriptID = ensemblTranscriptIDWithPair.substring(ensemblTranscriptIDWithPair.indexOf( ' ') + 1).replace( "\"", "");

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
				
				switch(expressingorNonExpressiongGeneType){
				
					case NONEXPRESSING_PROTEINCODING_GENES: {
						
						if( ensemblGeneID2TPMMap.containsKey(ensemblGeneID) && 
								(ensemblGeneID2TPMMap.get(ensemblGeneID) <= tpmValue) &&
								geneType.equals( "\"protein_coding\"")){

							switch(strand){

								case '+':{
											minusOneHundredFromTSS = low - 500;
											plusFiveHundredFromTSS = low + 100;
											proteinCodingGeneExonNumberOneInterval = new ProteinCodingGeneExonNumberOneInterval( 
													strand,
													minusOneHundredFromTSS, 
													plusFiveHundredFromTSS, 
													chrName, 
													ensemblGeneID2TPMMap.get(ensemblGeneID),
													ensemblGeneID,
													ensemblTranscriptID,
													geneSymbol);
											break;
								}
		
								case '-':{
										minusOneHundredFromTSS = high + 500;
										plusFiveHundredFromTSS = high - 100;
										proteinCodingGeneExonNumberOneInterval = new ProteinCodingGeneExonNumberOneInterval( 
												strand,
												plusFiveHundredFromTSS, 
												minusOneHundredFromTSS, 
												chrName, 
												ensemblGeneID2TPMMap.get(ensemblGeneID),
												ensemblGeneID,
												ensemblTranscriptID,
												geneSymbol);
										break;
								
								}

							} // End of SWITCH for strand

							proteinCodingGeneExonNumberOneIntervalList = proteinCodingGeneId2GeneExonNumberOneIntervalsMap.get(ensemblGeneID);

							if( proteinCodingGeneExonNumberOneIntervalList == null){
								proteinCodingGeneExonNumberOneIntervalList = new ArrayList<ProteinCodingGeneExonNumberOneInterval>();
								proteinCodingGeneExonNumberOneIntervalList.add( proteinCodingGeneExonNumberOneInterval);
								proteinCodingGeneId2GeneExonNumberOneIntervalsMap.put( ensemblGeneID,proteinCodingGeneExonNumberOneIntervalList);
							}else{
								proteinCodingGeneExonNumberOneIntervalList.add( proteinCodingGeneExonNumberOneInterval);
								proteinCodingGeneId2GeneExonNumberOneIntervalsMap.put( ensemblGeneID,proteinCodingGeneExonNumberOneIntervalList);
								
							}

						
						}// End of IF: For this geneId, interval will be created.
						
						break;
					}
						
					case EXPRESSING_PROTEINCODING_GENES:{
						
						if( ensemblGeneID2TPMMap.containsKey(ensemblGeneID) && 
								geneType.equals( "\"protein_coding\"")){

							switch(strand){

								case '+':{
											minusOneHundredFromTSS = low - 500;
											plusFiveHundredFromTSS = low + 100;
											proteinCodingGeneExonNumberOneInterval = new ProteinCodingGeneExonNumberOneInterval( 
													strand,
													minusOneHundredFromTSS, 
													plusFiveHundredFromTSS, 
													chrName,
													ensemblGeneID2TPMMap.get(ensemblGeneID),
													ensemblGeneID,
													ensemblTranscriptID,
													geneSymbol);
											break;
								}
		
								case '-':{
										minusOneHundredFromTSS = high + 500;
										plusFiveHundredFromTSS = high - 100;
										proteinCodingGeneExonNumberOneInterval = new ProteinCodingGeneExonNumberOneInterval(
												strand,
												plusFiveHundredFromTSS, 
												minusOneHundredFromTSS, 
												chrName,
												ensemblGeneID2TPMMap.get(ensemblGeneID),
												ensemblGeneID,
												ensemblTranscriptID,
												geneSymbol);
										break;
								
								}

							} // End of SWITCH for strand

							proteinCodingGeneExonNumberOneIntervalList = proteinCodingGeneId2GeneExonNumberOneIntervalsMap.get( ensemblGeneID);

							if( proteinCodingGeneExonNumberOneIntervalList == null){
								proteinCodingGeneExonNumberOneIntervalList = new ArrayList<ProteinCodingGeneExonNumberOneInterval>();
								proteinCodingGeneExonNumberOneIntervalList.add( proteinCodingGeneExonNumberOneInterval);
								proteinCodingGeneId2GeneExonNumberOneIntervalsMap.put( ensemblGeneID,proteinCodingGeneExonNumberOneIntervalList);
							}else{
								proteinCodingGeneExonNumberOneIntervalList.add( proteinCodingGeneExonNumberOneInterval);
								proteinCodingGeneId2GeneExonNumberOneIntervalsMap.put( ensemblGeneID,proteinCodingGeneExonNumberOneIntervalList);
							}

							
						}// End of IF: For this geneId, interval will be created.
						break;
					}
					
					default: 
						break;
				
				}//End of SWITCH expressing or nonExpressing geneType

			}// End of IF: Exon Number is 1

		}// End of While
		// Reading female.gtf file is accomplished

		
		//for debug purposes
		System.out.println("Size of proteinCodingGeneId2GeneExonNumberOneIntervalsMap" + "\t" + proteinCodingGeneId2GeneExonNumberOneIntervalsMap.size());
		
		List<ProteinCodingGeneExonNumberOneInterval> allProteinCodingGenesExonNumberOneIntervalsList  = new ArrayList<ProteinCodingGeneExonNumberOneInterval>(); 
		
		//Select only one of the exonNumber1 intervals if there are more than one starts.
		// For "+" strand, generate only one interval for  exon number 1 of a certain ensemblGeneID with the lowest low
		// For "-" strand, generate only one interval for  exon number 1 of a certain ensemblGeneID with the highest high			
		for( Map.Entry<String, List<ProteinCodingGeneExonNumberOneInterval>> entry : proteinCodingGeneId2GeneExonNumberOneIntervalsMap.entrySet()){

			minLow = Integer.MAX_VALUE;
			maxHigh = Integer.MIN_VALUE;
			savedIndex = 0;

			// entry.getKey()
			proteinCodingGeneExonNumberOneIntervalList = entry.getValue();

			// get the strand
			// there is at least one interval
			strand = proteinCodingGeneExonNumberOneIntervalList.get(0).getStrand();

			// Select which transcript to write
			// Find the lowest low for "+" genes
			// Find the highest high for "-" genes
			switch(strand){
			
				case '+':
					for( int i = 0; i < proteinCodingGeneExonNumberOneIntervalList.size(); i++){

						proteinCodingGeneExonNumberOneInterval = proteinCodingGeneExonNumberOneIntervalList.get( i);

						if( proteinCodingGeneExonNumberOneInterval.getLow() < minLow){
							minLow = proteinCodingGeneExonNumberOneInterval.getLow();
							savedIndex = i;
						}// End of IF

					}// End of FOR
					break;

				case '-':
					for( int i = 0; i < proteinCodingGeneExonNumberOneIntervalList.size(); i++){

						proteinCodingGeneExonNumberOneInterval = proteinCodingGeneExonNumberOneIntervalList.get( i);

						if( proteinCodingGeneExonNumberOneInterval.getHigh() > maxHigh){
							maxHigh = proteinCodingGeneExonNumberOneInterval.getHigh();
							savedIndex = i;
						}// End of IF

					}// End of FOR

					break;
				
			}// End of SWITCH for setting savedIndex
			
			//Fill this list
			allProteinCodingGenesExonNumberOneIntervalsList.add(proteinCodingGeneExonNumberOneIntervalList.get(savedIndex));
			
			
		}// End of for
		//Select only one of the exonNumber1 intervals if there are more than one ends.
		
		
		//My Decision
		//Let fill the interval pool with protein coding genes exon number one intervals which may overlap within
		//Let's permit this in the sake of keeping the intervals in the pool with the same length
		//However let's choose the interval from this pool such that, current selected interval doesn't overlap with the already selected intervals.
		//If it overlaps let's re select from the pool.
		
		//27 OCT 2015 starts
		//Here shall we check whether there are any overlapping intervals among allProteinCodingGenesExonNumberOneIntervalsList
		//And shall we merge in intervals in case of any overlap?
		//Please notice that intervals have mixed chromosomeName
//		List<IntervalTreeNode> intervalNodeList = new ArrayList<IntervalTreeNode>();
//		List<IntervalTreeNode> nonOverlappingIntervalNodeList = new ArrayList<IntervalTreeNode>();
//		
//		List<ProteinCodingGeneExonNumberOneInterval> nonOverlappingAllProteinCodingGenesExonNumberOneIntervalsList  = new ArrayList<ProteinCodingGeneExonNumberOneInterval>(); 
//		convertProteinCodingGeneInterval2IntervalTreeNode(allProteinCodingGenesExonNumberOneIntervalsList,intervalNodeList);
//		
//		IntervalTree intervalTree = new IntervalTree();
//		intervalTree = IntervalTree.constructAnIntervalTreeWithNonOverlappingNodes(intervalNodeList,expressingorNonExpressiongGeneType);
//	
//		nonOverlappingIntervalNodeList = IntervalTree.getIntervalNodeList(intervalTree.getRoot());
//		convertIntervalTreeNode2ProteinCodingGeneInterval(nonOverlappingIntervalNodeList,nonOverlappingAllProteinCodingGenesExonNumberOneIntervalsList);
//		
//		//for debug purposes
//		System.out.println("Size of allProteinCodingGenesExonNumberOneIntervalsList" + "\t" + allProteinCodingGenesExonNumberOneIntervalsList.size());
//		System.out.println("Size of nonOverlappingAllProteinCodingGenesExonNumberOneIntervalsList" + "\t" + nonOverlappingAllProteinCodingGenesExonNumberOneIntervalsList.size());
		//27 OCT 2015 ends
		
			
		// Now sort the list w.r.t. tpmValue
		// For ExpressingGenes in DescendingOrder
		// For NonExpressingGenes in AscendingOrder
		switch(expressingorNonExpressiongGeneType){
		
			case NONEXPRESSING_PROTEINCODING_GENES: {
				Collections.sort(allProteinCodingGenesExonNumberOneIntervalsList,ProteinCodingGeneExonNumberOneInterval.TPM_VALUE_ASCENDING);
				break;
			}
			case EXPRESSING_PROTEINCODING_GENES: {
				Collections.sort(allProteinCodingGenesExonNumberOneIntervalsList,ProteinCodingGeneExonNumberOneInterval.TPM_VALUE_DESCENDING);
				break;
			}
	
		}//End of SWITCH for sorting proteinCodingGenesExonNumberOneIntervals w.r.t. TPN values
		
		
		// Get the topX elements from allProteinCodingGenesExonNumberOneIntervalsList
		// Writing ExpressingGenes or nonExpressingGenes Intervals starts
		writeTopXProteinCodingGenesExonNumberOneIntervals(
				tpmValue,
				tpmType,
				expressingorNonExpressiongGeneType,
				allProteinCodingGenesExonNumberOneIntervalsList,
				ensemblGeneID2TPMMap,
				proteinCodingGenesIntervalsBufferedWriter,
				tpmBufferedWriter);

		
		// Close BufferedReader
		bufferedReader.close();
		proteinCodingGenesIntervalsBufferedWriter.close();
	
		//Pay attention: tpmBufferedWriter stream is closed in the main function
	}

	
	//Depreceated
	public static int findNonExpressingGenes( 
			TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2,
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

			// Greater than or equal to
			if( tpm >= tpmThreshold){
				numberofExpressingGenes++;
			}
		}// End of for

		return numberofExpressingGenes;

	}
	
	//14 OCT 215
	public static void writeTopXProteinCodingGenesExonNumberOneIntervals(
			Float tpmValue,
			DataDrivenExperimentTPMType tpmType,
			DataDrivenExperimentGeneType geneType,
			List<ProteinCodingGeneExonNumberOneInterval> allProteinCodingGenesExonNumberOneIntervalsList,
			TObjectFloatMap<String> ensemblGeneID2TPMMap,
			BufferedWriter proteinCodingGenesIntervalsBufferedWriter,
			BufferedWriter tpmBufferedWriter ) throws IOException{
		
		int numberofIntervals = allProteinCodingGenesExonNumberOneIntervalsList.size();
		int index = 0;
		
		ProteinCodingGeneExonNumberOneInterval  proteinCodingGeneExonNumberOneInterval = null;
		
		switch(tpmType){
			case TOP2:
				index = numberofIntervals*2/100;
				break;
			case TOP5:
				index = numberofIntervals*5/100;
				break;
			case TOP10:
				index = numberofIntervals*10/100;
				break;
			case TOP20:
				index = numberofIntervals*20/100;
				break;
			case TOP25:
				index = numberofIntervals*25/100;
				break;
			case TOPUNKNOWN:
				index = numberofIntervals;
			default:
				break;
		
		}//End of SWITCH for selecting index
		
		//Write tpmType  and tpmValue to TPMFile
		tpmValue = allProteinCodingGenesExonNumberOneIntervalsList.get(index-1).getTpm();
		tpmBufferedWriter.write(tpmType.convertEnumtoString() + "\t" + tpmValue + System.getProperty( "line.separator"));
		
		for(int i=0; i<index; i++){			
			proteinCodingGeneExonNumberOneInterval = allProteinCodingGenesExonNumberOneIntervalsList.get(i);
			proteinCodingGenesIntervalsBufferedWriter.write( proteinCodingGeneExonNumberOneInterval.getChrName().convertEnumtoString() + "\t" + proteinCodingGeneExonNumberOneInterval.getLow() + "\t" + proteinCodingGeneExonNumberOneInterval.getHigh() + "\t" + proteinCodingGeneExonNumberOneInterval.getGeneSymbol() + System.getProperty( "line.separator"));			
		}//End of for each interval from 0 to index
		
		//For debug purposes
		System.out.println("Number of protein coding genes intervals:" + "\t" + index + " for tpmType " + tpmType.convertEnumtoString() + "\t" +" for tpmValue " + tpmValue  );
		
	}

	
	
	/*
	 * This class takes 4 arguments.
	 * 
	 * args[0] = 	Glanet Folder (which is the parent of Data folder)
	 * 
	 * args[1] =	DataDrivenExperimentCellLineType 
	 * 				GM12878
	 * 				K562
	 * 
	 * 
	 * args[2] =	DataDrivenExperimentGeneType
	 * 				NonExpressingProteinCodingGenes
	 * 				ExpressingProteinCodingGenes
	 * 
	 * args[3] = 	TPM value (1, 10, 100) for ExpressingProteinCodingGenesIntervals pool creation
	 * 				or TPM value (0.1, 0.01, 0.001) for NonExpressingProteinCodingGenesIntervals pool creation
	 *  
	 */
	public static void main( String[] args) {

		String glanetFolder = args[0];
		String dataDrivenExperimentFolder = glanetFolder + Commons.DDE + System.getProperty("file.separator");

		DataDrivenExperimentCellLineType cellLineType = DataDrivenExperimentCellLineType.convertStringtoEnum(args[1]);
		
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(args[2]);
		
		// We will create the interval pool of nonExpressingGenes intervals (of 600 base long) for various TPM Values
		// Such as 0.1f, 0.01f, 0.001f for nonExpressing proteinCoding genes
		// Such as 1f, 10f, 100f for expressing proteinCoding genes
		// float tpmThreshold = 0.1f;

		// Get the TPM Values from cellLine and geneType specific file
		// For NonExpressingGenes use TOP2 and TOP60
		// For ExpressingGenes use TOP2, TOP10, and TOP25
		// The order of TPMs are as follows: TOP1, TOP2, TOP5, TOP10, TOP25, TOP50, TOP55, TOP60
		//float tpmThreshold = Float.parseFloat( args[3]);
		
		//14 OCT 2015 
		//Here we set the topX of the protein coding genes that we will consider in DDE
		//TPM Values will be decided in this class
		//And will be written to an output file.
		//Here we put dummy TPM Values
		//But in the upcoming part of the code real TPM Values will be filled.
		//For ExpressingGenes and For NonExpressingGenes this map can be different
		
		//For expressingGenes we need tpmValues in descending order, so map uses comparator with reverse order.
		//TPMTypes are known and tpmValues are unknown and they will be found
		//Here dummy tpmValues are inserted, they have to be distinct..
		SortedMap<DataDrivenExperimentTPMType,Float> expGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		expGenesTPMType2TPMValueSortedMap.put(DataDrivenExperimentTPMType.TOP5,0f);
		expGenesTPMType2TPMValueSortedMap.put(DataDrivenExperimentTPMType.TOP10,0f);
		expGenesTPMType2TPMValueSortedMap.put(DataDrivenExperimentTPMType.TOP20,0f);
	
		//For nonExpressingGenes we need tpmValues in ascending order, so map uses comparator with natural order
		//Here we give the tpmValue we are interested in, therefore tpmType is unknown.
		//TPMValue is known and tpmTypes are unknown.
		SortedMap<DataDrivenExperimentTPMType,Float> nonExpGenesTPMType2TPMValueSortedMap = new TreeMap<DataDrivenExperimentTPMType,Float>(DataDrivenExperimentTPMType.TPM_TYPE);
		nonExpGenesTPMType2TPMValueSortedMap.put(DataDrivenExperimentTPMType.TOPUNKNOWN,0f);
		
		Set<DataDrivenExperimentTPMType> tpmTypes = null;
		DataDrivenExperimentTPMType tpmType = null;
		
		Collection<Float> tpmValues = null;
		Float tpmValue = null;
		
		String tpmFileName = glanetFolder +  Commons.DDE + System.getProperty("file.separator") + Commons.DDE_TPM_VALUES + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + ".txt";
		
		FileWriter tpmFileWriter = null;
		BufferedWriter tpmBufferedWriter = null;
		
		try {
			
			tpmFileWriter = FileOperations.createFileWriter(tpmFileName);
			tpmBufferedWriter = new BufferedWriter(tpmFileWriter);
				
			// This will be filled while reading cellLineRep1
			TObjectFloatMap<String> ensemblGeneID2TPMMapforRep1;
	
			// This will be filled while reading cellLineRep2
			TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2;
	
			String cellLineRep1GTFFileName = null;
			String cellLineRep2GTFFileName = null;
			
			switch(cellLineType){
			
				case GM12878: {
					// Input File
					// Set cellLine Replicate1 gtf file with path
					// We will get the TPM values from this input file.
					cellLineRep1GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.Gm12878Rep1_genes_results;
	
					// Input File
					// Set cellLine Replicate2 gtf file with path
					// We will get the TPM values from this input file.
					cellLineRep2GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.Gm12878Rep2_genes_results;
	
					break;
				}
				
				case K562: {
					// Input File
					// Set cellLine Replicate1 gtf file with path
					// We will get the TPM values from this input file.
					cellLineRep1GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.K562Rep1_genes_results;
	
					// Input File
					// Set cellLine Replicate2 gtf file with path
					// We will get the TPM values from this input file.
					cellLineRep2GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.K562Rep2_genes_results;
	
					break;
				}
				
				default: 
					break;
			
			}//End of SWITCH cellLineType
					
	
			// Input File
			// Set female.gtf file with path
			// We will get the  gene symbol from this input file.
			// Former input files and female.gtf file will be matched w.r.t. geneID attribute
			String femaleGTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.female_gtf;
			
			// Read cellLineRep1Results file and fill ensemblGeneID2TPMMapRep1
			ensemblGeneID2TPMMapforRep1 = DataDrivenExperimentCommon.fillMapUsingGTFFile(cellLineRep1GTFFileName);
	
			// Read cellLineRep2 results file and fill ensemblGeneID2TPMMapforUnionofRep1andRep2
			ensemblGeneID2TPMMapforUnionofRep1andRep2 = DataDrivenExperimentCommon.fillMapUsingGTFFile(
					cellLineRep2GTFFileName,
					ensemblGeneID2TPMMapforRep1,
					geneType);
	
			
			// Output File
			// End Inclusive
			// Set NonExpressingProteinCodingGenesIntervalsFile
			// Set ExpressingProteinCodingGenesIntervalsFile
			String nonExpressingProteinCodingGenesIntervalsFile = null;
			String expressingProteinCodingGenesIntervalsFile = null;
			
			//17 OCT 2015 starts
			switch(geneType){
			
				case NONEXPRESSING_PROTEINCODING_GENES: {
					
					//tpmTypes(keys) are sorted in ascending order w.r.t. the integer after String TOP
					//values are in order of corresponding keys
					tpmTypes  = nonExpGenesTPMType2TPMValueSortedMap.keySet();
					tpmValues = nonExpGenesTPMType2TPMValueSortedMap.values();
					
					int i = 0;
					
					//We know tpmTypes so traverse using its iterator
					for(Iterator<DataDrivenExperimentTPMType> itr = tpmTypes.iterator();itr.hasNext(); ) {
						 
						tpmType = itr.next();
						tpmValue = (Float)(tpmValues.toArray())[i++];
						
						nonExpressingProteinCodingGenesIntervalsFile = dataDrivenExperimentFolder + Commons.DDE_INTERVAL_POOL + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" +  tpmType.convertEnumtoString() +  "_IntervalPool.txt";
						
						// Generate Intervals for the nonExpressing genes in the ensemblGeneID2TPMMapforUnionofRep1andRep2 according to
						// the Top tpmType using female.gtf to nonExpressingGenesIntervalsFile
						generateIntervalsFromFemaleGTFFile(
								ensemblGeneID2TPMMapforUnionofRep1andRep2, 
								femaleGTFFileName, 
								tpmValue,
								tpmType,
								tpmBufferedWriter,
								nonExpressingProteinCodingGenesIntervalsFile,
								geneType);

					}//End of for each tpmType
					
					break;
				}
				case EXPRESSING_PROTEINCODING_GENES:{
					
					tpmTypes  =expGenesTPMType2TPMValueSortedMap.keySet();
					tpmValues  =expGenesTPMType2TPMValueSortedMap.values();
					
					int i = 0;
					
					//We know tpmTypes so traverse using its iterator
					for(Iterator<DataDrivenExperimentTPMType> itr = tpmTypes.iterator();itr.hasNext(); ) {
						
						tpmType = itr.next();
						tpmValue = (Float)(tpmValues.toArray())[i++];
						
						expressingProteinCodingGenesIntervalsFile = dataDrivenExperimentFolder + Commons.DDE_INTERVAL_POOL + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + "_" + tpmType.convertEnumtoString() +  "_IntervalPool.txt";
						
						// Generate Intervals for the expressing genes in the ensemblGeneID2TPMMapforUnionofRep1andRep2 according to
						// the Top tmpType using female.gtf to expressingGenesIntervalsFile
						generateIntervalsFromFemaleGTFFile(
								ensemblGeneID2TPMMapforUnionofRep1andRep2, 
								femaleGTFFileName, 
								tpmValue,
								tpmType,
								tpmBufferedWriter,
								expressingProteinCodingGenesIntervalsFile,
								geneType);

					}//End of for each tpmType
						 
					break;
				}
			
				default: 
					break;
					
			}//End of SWITCH for geneType
			//17 OCT 
			
			
			
			//Close the tpmBufferedWriter
			tpmBufferedWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}//End of main

}
