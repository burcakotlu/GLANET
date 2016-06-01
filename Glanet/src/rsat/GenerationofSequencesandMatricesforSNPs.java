/**
 * @author burcakotlu
 * @date Apr 2, 2014 
 * @time 5:01:25 PM
 */
package rsat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jaxbxjctool.AugmentationofGivenIntervalwithRsIds;
import jaxbxjctool.AugmentationofGivenRsIdwithInformation;
import jaxbxjctool.NCBIEutils;
import jaxbxjctool.PositionFrequency;
import jaxbxjctool.RsInformation;
import remap.Remap;
import ui.GlanetRunner;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;
import common.Commons;
import enumtypes.AnnotationType;
import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;

/**
 * 
 */
public class GenerationofSequencesandMatricesforSNPs {

	final static Logger logger = Logger.getLogger(GenerationofSequencesandMatricesforSNPs.class);

	public static void constructLogoMatricesfromEncodeMotifs( String dataFolder, String encodeMotifsInputFileName,
			Map<String, String> tfName2LogoMatrices) {

		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;

		// Attention order is important!
		// Order is ACGT
		String tfName = null;

		try{
			fileReader = new FileReader( dataFolder + encodeMotifsInputFileName);
			bufferedReader = new BufferedReader( fileReader);

			while( ( strLine = bufferedReader.readLine()) != null){

				// Encode-motif matrix
				// Order is ACGT
				// >NFKB_disc1
				// NFKB1_GM19193_encode-Snyder_seq_hsa_IgG-rab_r1:MDscan#1#Intergenic
				// K 0.000000 0.000000 0.737500 0.262500
				// G 0.000000 0.000000 1.000000 0.000000
				// G 0.000000 0.000000 1.000000 0.000000
				// R 0.570833 0.000000 0.429167 0.000000
				// A 0.837500 0.158333 0.004167 0.000000
				// W 0.395833 0.000000 0.000000 0.604167
				// T 0.000000 0.004167 0.000000 0.995833
				// Y 0.000000 0.383333 0.000000 0.616667
				// C 0.000000 1.000000 0.000000 0.000000
				// C 0.000000 1.000000 0.000000 0.000000

				if( strLine.startsWith( ">")){

					// start reading from first character, skip '>' character
					tfName = strLine.substring( 1);

					if( tfName2LogoMatrices.get( tfName) == null){
						tfName2LogoMatrices.put( tfName, strLine + System.getProperty( "line.separator"));

					}else{
						tfName2LogoMatrices.put( tfName,
								tfName2LogoMatrices.get( tfName) + strLine + System.getProperty( "line.separator"));
					}

				}// End of if

				else{
					tfName2LogoMatrices.put( tfName,
							tfName2LogoMatrices.get( tfName) + strLine + System.getProperty( "line.separator"));
				}

			}// end of while

			bufferedReader.close();

		}catch( FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void constructPfmMatricesfromEncodeMotifs( String dataFolder, String encodeMotifsInputFileName,
			Map<String, String> tfName2PfmMatrices) {

		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;

		int indexofFirstBlank;
		int indexofSecondBlank;
		int indexofThirdBlank;
		int indexofFourthBlank;

		float _AFrequency;
		float _CFrequency;
		float _GFrequency;
		float _TFrequency;

		List<PositionFrequency> positionfrequencyList = new ArrayList<PositionFrequency>();;

		// Attention
		// Order is ACGT
		String ALine = "";
		String CLine = "";
		String GLine = "";
		String TLine = "";

		String tfName = null;
		String formerTfName = null;

		Iterator<PositionFrequency> iterator;

		try{
			fileReader = new FileReader( dataFolder + encodeMotifsInputFileName);
			bufferedReader = new BufferedReader( fileReader);

			while( ( strLine = bufferedReader.readLine()) != null){

				// Encode-motif matrix
				// Order is ACGT
				// >NFKB_disc1
				// NFKB1_GM19193_encode-Snyder_seq_hsa_IgG-rab_r1:MDscan#1#Intergenic
				// K 0.000000 0.000000 0.737500 0.262500
				// G 0.000000 0.000000 1.000000 0.000000
				// G 0.000000 0.000000 1.000000 0.000000
				// R 0.570833 0.000000 0.429167 0.000000
				// A 0.837500 0.158333 0.004167 0.000000
				// W 0.395833 0.000000 0.000000 0.604167
				// T 0.000000 0.004167 0.000000 0.995833
				// Y 0.000000 0.383333 0.000000 0.616667
				// C 0.000000 1.000000 0.000000 0.000000
				// C 0.000000 1.000000 0.000000 0.000000

				if( strLine.startsWith( ">")){

					// start reading from first character, skip '>' character
					tfName = strLine.substring( 1);

					if( formerTfName != null){
						// Write former positionfrequencyList to the output file
						// starts
						// if it is full
						ALine = "A |\t";
						CLine = "C |\t";
						GLine = "G |\t";
						TLine = "T |\t";

						iterator = positionfrequencyList.iterator();

						while( iterator.hasNext()){
							PositionFrequency positionFrequency = ( PositionFrequency)iterator.next();
							ALine = ALine + positionFrequency.get_AFrequency() + "\t";
							CLine = CLine + positionFrequency.get_CFrequency() + "\t";
							GLine = GLine + positionFrequency.get_GFrequency() + "\t";
							TLine = TLine + positionFrequency.get_TFrequency() + "\t";
						}

						// We must have the former tfName
						// We must have inserted the header line
						tfName2PfmMatrices.put( formerTfName,
								tfName2PfmMatrices.get( formerTfName) + ALine + System.getProperty( "line.separator"));
						tfName2PfmMatrices.put( formerTfName,
								tfName2PfmMatrices.get( formerTfName) + CLine + System.getProperty( "line.separator"));
						tfName2PfmMatrices.put( formerTfName,
								tfName2PfmMatrices.get( formerTfName) + GLine + System.getProperty( "line.separator"));
						tfName2PfmMatrices.put( formerTfName,
								tfName2PfmMatrices.get( formerTfName) + TLine + System.getProperty( "line.separator"));
						tfName2PfmMatrices.put( formerTfName,
								tfName2PfmMatrices.get( formerTfName) + "//" + System.getProperty( "line.separator"));
						// Write former full pfList to the output file ends
					}// End of if

					// if tfName is inserted for the first time
					if( tfName2PfmMatrices.get( tfName) == null){
						tfName2PfmMatrices.put( tfName,
								"; " + strLine.substring( 1) + System.getProperty( "line.separator"));
					}
					// else start appending the new coming matrix to the already
					// existing matrices for this tfName
					else{
						tfName2PfmMatrices.put(
								tfName,
								tfName2PfmMatrices.get( tfName) + "; " + strLine.substring( 1) + System.getProperty( "line.separator"));
					}

					// Initialize positionfrequencyList
					positionfrequencyList = new ArrayList<PositionFrequency>();

				}else{

					indexofFirstBlank = strLine.indexOf( ' ');
					indexofSecondBlank = strLine.indexOf( ' ', indexofFirstBlank + 1);
					indexofThirdBlank = strLine.indexOf( ' ', indexofSecondBlank + 1);
					indexofFourthBlank = strLine.indexOf( ' ', indexofThirdBlank + 1);

					_AFrequency = Float.parseFloat( strLine.substring( indexofFirstBlank + 1, indexofSecondBlank));
					_CFrequency = Float.parseFloat( strLine.substring( indexofSecondBlank + 1, indexofThirdBlank));
					_GFrequency = Float.parseFloat( strLine.substring( indexofThirdBlank + 1, indexofFourthBlank));
					_TFrequency = Float.parseFloat( strLine.substring( indexofFourthBlank + 1));

					PositionFrequency positionFrequency = new PositionFrequency( _AFrequency, _CFrequency, _GFrequency,
							_TFrequency);
					positionfrequencyList.add( positionFrequency);
					formerTfName = tfName;
				}

			}// end of while

			// Write the last positionFrequencyList starts
			// Order is ACGT
			ALine = "A |\t";
			CLine = "C |\t";
			GLine = "G |\t";
			TLine = "T |\t";

			iterator = positionfrequencyList.iterator();

			while( iterator.hasNext()){
				PositionFrequency positionFrequency = ( PositionFrequency)iterator.next();
				ALine = ALine + positionFrequency.get_AFrequency() + "\t";
				CLine = CLine + positionFrequency.get_CFrequency() + "\t";
				GLine = GLine + positionFrequency.get_GFrequency() + "\t";
				TLine = TLine + positionFrequency.get_TFrequency() + "\t";
			}

			// We must use former tfName
			// We must have inserted the header line
			tfName2PfmMatrices.put( formerTfName,
					tfName2PfmMatrices.get( formerTfName) + ALine + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( formerTfName,
					tfName2PfmMatrices.get( formerTfName) + CLine + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( formerTfName,
					tfName2PfmMatrices.get( formerTfName) + GLine + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( formerTfName,
					tfName2PfmMatrices.get( formerTfName) + TLine + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( formerTfName,
					tfName2PfmMatrices.get( formerTfName) + "//" + System.getProperty( "line.separator"));
			// Write the last positionFrequencyList ends

			bufferedReader.close();

		}catch( FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void fillFrequencyListUsingCountList( List<Float> frequencyList, List<Integer> countList,
			Integer totalCount) {

		Iterator<Integer> iterator = countList.iterator();

		Integer count;
		Float frequency;

		while( iterator.hasNext()){

			count = iterator.next();
			frequency = ( count * 1.0f) / totalCount;

			frequencyList.add( frequency);

		}

	}

	// Fill CountList using CountLine
	public static void fillCountList( String countLine, List<Integer> countList) {

		// example Count line
		// 4 19 0 0 0 0
		int indexofFormerTab = 0;
		int indexofLatterTab = 0;

		int count = 0;

		indexofFormerTab = 0;
		indexofLatterTab = countLine.indexOf( '\t');

		// Insert the first count
		if( indexofLatterTab >= 0){
			count = Integer.parseInt( countLine.substring( indexofFormerTab, indexofLatterTab));
			countList.add( count);
		}

		indexofFormerTab = indexofLatterTab;
		indexofLatterTab = countLine.indexOf( '\t', indexofFormerTab + 1);

		// Insert the rest of the counts
		while( indexofLatterTab >= 0){

			count = Integer.parseInt( countLine.substring( indexofFormerTab + 1, indexofLatterTab));

			countList.add( count);

			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = countLine.indexOf( '\t', indexofFormerTab + 1);

		}

		// Insert the last count
		if( indexofFormerTab >= 0){
			count = Integer.parseInt( countLine.substring( indexofFormerTab + 1));
			countList.add( count);

		}

	}

	public static int getTotalCount( List<Integer> ACountList, List<Integer> CCountList, List<Integer> GCountList,
			List<Integer> TCountList) {

		Iterator<Integer> iteratorA = ACountList.iterator();
		Integer countA;

		Iterator<Integer> iteratorC = CCountList.iterator();
		Integer countC;

		Iterator<Integer> iteratorG = GCountList.iterator();
		Integer countG;

		Iterator<Integer> iteratorT = TCountList.iterator();
		Integer countT;

		int totalCount = 0;;

		while( iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()){

			countA = iteratorA.next();
			countC = iteratorC.next();
			countG = iteratorG.next();
			countT = iteratorT.next();

			totalCount = countA + countC + countG + countT;
			return totalCount;

		}

		return totalCount;

	}

	public static void putPFM( String tfName, List<Float> AFrequencyList, List<Float> CFrequencyList,
			List<Float> GFrequencyList, List<Float> TFrequencyList, Map<String, String> tfName2PfmMatrices) {

		// example matrix in tab format
		// ; NFKB_known3 NFKB_1 NF-kappaB_transfac_M00054
		// A | 0 0 0.025 0.675 0.525 0.2 0.025 0.05 0.075 0
		// C | 0 0 0 0 0.325 0.025 0.05 0.45 0.9 0.95
		// G | 1 1 0.975 0.325 0.025 0.075 0.05 0 0 0
		// T | 0 0 0 0 0.125 0.7 0.875 0.5 0.025 0.05
		// //

		Iterator<Float> iteratorA = AFrequencyList.iterator();
		Iterator<Float> iteratorC = CFrequencyList.iterator();
		Iterator<Float> iteratorG = GFrequencyList.iterator();
		Iterator<Float> iteratorT = TFrequencyList.iterator();

		Float frequencyA;
		Float frequencyC;
		Float frequencyG;
		Float frequencyT;

		String strLineA = "";
		String strLineC = "";
		String strLineG = "";
		String strLineT = "";

		while( iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()){

			frequencyA = iteratorA.next();
			frequencyC = iteratorC.next();
			frequencyG = iteratorG.next();
			frequencyT = iteratorT.next();

			strLineA = strLineA + "\t" + frequencyA;
			strLineC = strLineC + "\t" + frequencyC;
			strLineG = strLineG + "\t" + frequencyG;
			strLineT = strLineT + "\t" + frequencyT;

		}// end of while

		// this tfName has no previous position frequency matrix inserted
		if( tfName2PfmMatrices.get( tfName) == null){
			tfName2PfmMatrices.put( tfName, "; " + tfName + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( tfName,
					tfName2PfmMatrices.get( tfName) + "A|" + strLineA + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( tfName,
					tfName2PfmMatrices.get( tfName) + "C|" + strLineC + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( tfName,
					tfName2PfmMatrices.get( tfName) + "G|" + strLineG + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( tfName,
					tfName2PfmMatrices.get( tfName) + "T|" + strLineT + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( tfName,
					tfName2PfmMatrices.get( tfName) + "//" + System.getProperty( "line.separator"));

		}
		// this tfName already has position frequency matrices
		// append the new position frequency matrix
		else{
			tfName2PfmMatrices.put( tfName,
					tfName2PfmMatrices.get( tfName) + "; " + tfName + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( tfName,
					tfName2PfmMatrices.get( tfName) + "A|" + strLineA + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( tfName,
					tfName2PfmMatrices.get( tfName) + "C|" + strLineC + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( tfName,
					tfName2PfmMatrices.get( tfName) + "G|" + strLineG + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( tfName,
					tfName2PfmMatrices.get( tfName) + "T|" + strLineT + System.getProperty( "line.separator"));
			tfName2PfmMatrices.put( tfName,
					tfName2PfmMatrices.get( tfName) + "//" + System.getProperty( "line.separator"));

		}

	}

	public static void putLogoMatrix( String tfName, List<Float> AFrequencyList, List<Float> CFrequencyList,
			List<Float> GFrequencyList, List<Float> TFrequencyList, Map<String, String> tfName2LogoMatrices) {

		// Example logo matrix
		// G 0.008511 0.004255 0.987234 0.000000
		// A 0.902127 0.012766 0.038298 0.046809
		// R 0.455319 0.072340 0.344681 0.127660
		// W 0.251064 0.085106 0.085106 0.578724
		// T 0.000000 0.046809 0.012766 0.940425
		// G 0.000000 0.000000 1.000000 0.000000
		// T 0.038298 0.021277 0.029787 0.910638
		// A 0.944681 0.004255 0.051064 0.000000
		// G 0.000000 0.000000 1.000000 0.000000
		// T 0.000000 0.000000 0.012766 0.987234

		Iterator<Float> iteratorA = AFrequencyList.iterator();
		Iterator<Float> iteratorC = CFrequencyList.iterator();
		Iterator<Float> iteratorG = GFrequencyList.iterator();
		Iterator<Float> iteratorT = TFrequencyList.iterator();

		Float frequencyA;
		Float frequencyC;
		Float frequencyG;
		Float frequencyT;

		String strLine = null;

		if( tfName2LogoMatrices.get( tfName) == null){
			tfName2LogoMatrices.put( tfName, tfName + System.getProperty( "line.separator"));
		}

		else{
			tfName2LogoMatrices.put( tfName,
					tfName2LogoMatrices.get( tfName) + tfName + System.getProperty( "line.separator"));
		}

		while( iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()){

			frequencyA = iteratorA.next();
			frequencyC = iteratorC.next();
			frequencyG = iteratorG.next();
			frequencyT = iteratorT.next();

			strLine = "X" + "\t" + frequencyA + "\t" + frequencyC + "\t" + frequencyG + "\t" + frequencyT + System.getProperty( "line.separator");
			tfName2LogoMatrices.put( tfName, tfName2LogoMatrices.get( tfName) + strLine);

		}// end of while

	}

	public static void constructPfmMatricesandLogoMatricesfromJasparCore( String dataFolder,
			String jasparCoreInputFileName, Map<String, String> tfName2PfmMatrices,
			Map<String, String> tfName2LogoMatrices) {

		// Attention
		// Order is ACGT

		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;

		String tfName = null;

		int whichLine = 0;

		final int headerLine = 0;
		final int ALine = 1;
		final int CLine = 2;
		final int GLine = 3;
		final int TLine = 4;

		List<Integer> ACountList = null;
		List<Float> AFrequencyList = null;

		List<Integer> CCountList = null;
		List<Float> CFrequencyList = null;

		List<Integer> GCountList = null;
		List<Float> GFrequencyList = null;

		List<Integer> TCountList = null;
		List<Float> TFrequencyList = null;

		int totalCount;

		try{
			fileReader = new FileReader( dataFolder + jasparCoreInputFileName);
			bufferedReader = new BufferedReader( fileReader);

			// Example matrix from jaspar core pfm_all.txt
			// >MA0004.1 Arnt
			// 4 19 0 0 0 0
			// 16 0 20 0 0 0
			// 0 1 0 20 0 20
			// 0 0 0 0 20 0

			while( ( strLine = bufferedReader.readLine()) != null){
				if( strLine.startsWith( ">")){
					tfName = strLine.substring( 1);

					// Initialize array lists
					// for the new coming position count matrix and position
					// frequency matrix
					ACountList = new ArrayList<Integer>();
					AFrequencyList = new ArrayList<Float>();

					CCountList = new ArrayList<Integer>();
					CFrequencyList = new ArrayList<Float>();

					GCountList = new ArrayList<Integer>();
					GFrequencyList = new ArrayList<Float>();

					TCountList = new ArrayList<Integer>();
					TFrequencyList = new ArrayList<Float>();

					whichLine = ALine;
					continue;
				}

				switch( whichLine){
				case ALine:{
					fillCountList( strLine, ACountList);
					whichLine = CLine;
					break;
				}

				case CLine:{
					fillCountList( strLine, CCountList);
					whichLine = GLine;
					break;
				}

				case GLine:{
					fillCountList( strLine, GCountList);
					whichLine = TLine;
					break;
				}

				case TLine:{
					fillCountList( strLine, TCountList);
					whichLine = headerLine;

					// Since count lists are available
					// Then compute frequency lists
					totalCount = getTotalCount( ACountList, CCountList, GCountList, TCountList);
					fillFrequencyListUsingCountList( AFrequencyList, ACountList, totalCount);
					fillFrequencyListUsingCountList( CFrequencyList, CCountList, totalCount);
					fillFrequencyListUsingCountList( GFrequencyList, GCountList, totalCount);
					fillFrequencyListUsingCountList( TFrequencyList, TCountList, totalCount);

					// Now put the new matrix to the hashmap in tab format
					putPFM( tfName, AFrequencyList, CFrequencyList, GFrequencyList, TFrequencyList, tfName2PfmMatrices);

					// Put the transpose of the matrix for logo generation
					putLogoMatrix( tfName, AFrequencyList, CFrequencyList, GFrequencyList, TFrequencyList,
							tfName2LogoMatrices);
					// writeLogoMatrix(tfName,AFrequencyList,CFrequencyList,GFrequencyList,TFrequencyList,bufferedWriter);

					break;

				}

				}// End of switch

			}// End of while

			bufferedReader.close();

		}catch( FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getTfNamewithoutNumber( String tfName) {

		int n = tfName.length();
		char c;
		int i;

		for( i = 0; i < n; i++){
			c = tfName.charAt( i);
			if( Character.isDigit( c)){
				break;
			}
		}

		return tfName.substring( 0, i);
	}

	public static String getTfNameWithFirstNumberWithNextCharacter( String tfName) {

		int n = tfName.length();
		char c;
		int i;

		for( i = 0; i < n; i++){
			c = tfName.charAt( i);
			if( Character.isDigit( c)){
				if( ( i + 1) < n){
					return tfName.substring( 0, i + 2);

				}else{
					return tfName.substring( 0, i + 1);

				}
			}
		}

		return tfName.substring( 0, i);
	}

	public static String removeLastCharacter( String tfName) {

		int n = tfName.length();

		if( n >= 6){
			return tfName.substring( 0, n - 1);

		}else{
			return tfName;

		}
	}

	public static void writeTFBasedTFOverlapsFileAndTFPeakSequenceFile(
			String snpDirectory,
			Map<String, TFOverlaps> tfName2TFOverlapMap, 
			String chrNameWithoutPreceedingChr,
			Map<String, String> chrName2RefSeqIdforLatestAssemblyReturnedByNCBIEutilsMap) {

		String tfName;
		TFOverlaps tfOverlap;
		TFCellLineOverlap tfCellLineOverlap;

		FileWriter TFNameBasedTFOverlapsFileWriter = null;
		BufferedWriter TFNameBasedTFOverlapsBufferedWriter = null;

		try{

			for( Map.Entry<String, TFOverlaps> tfEntry : tfName2TFOverlapMap.entrySet()){

				tfName = tfEntry.getKey();
				tfOverlap = tfEntry.getValue();

				TFNameBasedTFOverlapsFileWriter = FileOperations.createFileWriter( snpDirectory + System.getProperty( "file.separator") + Commons.TF_OVERLAPS + Commons.UNDERSCORE + tfName + ".txt");
				TFNameBasedTFOverlapsBufferedWriter = new BufferedWriter( TFNameBasedTFOverlapsFileWriter);

				Iterator<TFCellLineOverlap> iterator = tfOverlap.getTfCellLineOverlaps().iterator();

				// Write TF Based TFCellLineOverlaps into a file
				while( iterator.hasNext()){
					tfCellLineOverlap = iterator.next();

					// Write TF CellLine Based TFOverlap File
					// write snp based tf Intervals file
					TFNameBasedTFOverlapsBufferedWriter.write( ChromosomeName.convertEnumtoString( tfOverlap.getChrNameWithPreceedingChr()) + "\t" + tfCellLineOverlap.getOneBasedStart() + "\t" + tfCellLineOverlap.getOneBasedEnd() + "\t" + tfCellLineOverlap.getTfName() + "\t" + tfCellLineOverlap.getCellLineName() + "\t" + tfCellLineOverlap.getFileName() + System.getProperty( "line.separator"));

					// Set TFOverlap Minimum One Based Start
					if( tfOverlap.getMinimumOneBasedStart() > tfCellLineOverlap.getOneBasedStart()){
						tfOverlap.setMinimumOneBasedStart( tfCellLineOverlap.getOneBasedStart());
					}// End of IF

					// Set TFOverlap Maximum One Based End
					if( tfOverlap.getMaximumOneBasedEnd() < tfCellLineOverlap.getOneBasedEnd()){
						tfOverlap.setMaximumOneBasedEnd( tfCellLineOverlap.getOneBasedEnd());
					}// End of IF

				}// End of WHILE

				// Close bufferedWriter
				TFNameBasedTFOverlapsBufferedWriter.close();

				// Get TF Name Based TFOverlap Peak Sequence
				tfOverlap.setPeakSequence( getDNASequence( 
						chrNameWithoutPreceedingChr,
						tfOverlap.getMinimumOneBasedStart(), 
						tfOverlap.getMaximumOneBasedEnd(),
						chrName2RefSeqIdforLatestAssemblyReturnedByNCBIEutilsMap));

				// Write TF Name Based TFOverlap Peak Sequence
				writeSequenceFile( snpDirectory, Commons.TF_EXTENDED_PEAK_SEQUENCE + Commons.UNDERSCORE + tfName,
						tfOverlap.getPeakSequence());

			}// End of FOR TF Name Based TF CellLine Overlaps

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void writeSequenceFile( String snpDirectory, String fileName, String sequence) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		int indexofLineSeparator;
		String firstLineofFastaFile;

		try{
			fileWriter = FileOperations.createFileWriter( snpDirectory + System.getProperty( "file.separator") + fileName + ".txt");
			bufferedWriter = new BufferedWriter( fileWriter);

			indexofLineSeparator = sequence.indexOf( System.getProperty( "line.separator"));

			// fastaFile is sent
			if( indexofLineSeparator != -1){
				firstLineofFastaFile = sequence.substring( 0, indexofLineSeparator);

				bufferedWriter.write( firstLineofFastaFile + "\t" + fileName + System.getProperty( "line.separator"));
				bufferedWriter.write( sequence.substring( indexofLineSeparator + 1).trim());

			}
			// only sequence is sent
			// so add '>' character to make it in fasta format
			else{
				bufferedWriter.write( ">" + fileName + System.getProperty( "line.separator"));
				bufferedWriter.write( sequence);

			}

			bufferedWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String takeComplementforeachAllele( String allele) {

		String complementedAllele = "";

		for( char nucleotide : allele.toCharArray()){
			switch( nucleotide){
			case 'A':
			case 'a':
				complementedAllele = complementedAllele + "T";
				break;

			case 'C':
			case 'c':
				complementedAllele = complementedAllele + "G";
				break;

			case 'G':
			case 'g':
				complementedAllele = complementedAllele + "C";
				break;

			case 'T':
			case 't':
				complementedAllele = complementedAllele + "A";
				break;

			case '-':
				complementedAllele = complementedAllele + "-";
				break;

			default:
				return null;

			}// End of switch
		}// End of for

		return complementedAllele;
	}

	public static List<String> takeComplement( List<String> observedAllelesList) {

		String complementedAllele = null;
		List<String> complementedAlleles = new ArrayList<String>();

		for( String allele : observedAllelesList){
			complementedAllele = takeComplementforeachAllele( allele);
			if( complementedAllele != null){
				complementedAlleles.add( complementedAllele);
			}

		}// End of for each observed Allele

		return complementedAlleles;

	}

	public static boolean checkWhetherSNPReferenceSequenceContainsAnyObservedAlleleAndCreateAlteredSequences(
			List<String> snpAlteredSequenceNames, 
			List<String> snpAlteredSequences, 
			List<String> usedObservedAlleles,
			String snpForwardReferenceSequence, 
			int rsId, 
			List<String> observedAllelesList) {

		boolean contains = false;
		String formerSNPReferenceSequence = null;
		String latterSNPReferenceSequence = null;
		String alteredSNPSequence = null;

		int lengthOfObservedAllele;
		String SNPReferenceSequenceStartingAtSNPPositionOfLengthObservedAllele = null;

		for( String observedAllele : observedAllelesList){

			lengthOfObservedAllele = observedAllele.length();

			try{

				SNPReferenceSequenceStartingAtSNPPositionOfLengthObservedAllele = snpForwardReferenceSequence.substring(
						Commons.ZERO_BASED_SNP_POSITION, Commons.ZERO_BASED_SNP_POSITION + lengthOfObservedAllele);

			}catch( StringIndexOutOfBoundsException e){
				if( GlanetRunner.shouldLog())logger.error( "Exception Message:" + e.getMessage());
				if( GlanetRunner.shouldLog())logger.error( "Exception toString:" + e.toString());
				if( GlanetRunner.shouldLog())logger.error( "snpForwardReferenceSequence: " + snpForwardReferenceSequence);
				if( GlanetRunner.shouldLog())logger.error( "ObservedAllele: " + observedAllele);
			}

			// This observed allele is already in the SNP Reference Sequence
			if( SNPReferenceSequenceStartingAtSNPPositionOfLengthObservedAllele.equals( observedAllele)){
				contains = true;
			}
			// This observed allele is not in the SNP Reference Sequence
			else{

				if( !usedObservedAlleles.contains( observedAllele)){

					formerSNPReferenceSequence = snpForwardReferenceSequence.substring( 0,
							Commons.ZERO_BASED_SNP_POSITION);
					latterSNPReferenceSequence = snpForwardReferenceSequence.substring( Commons.ZERO_BASED_SNP_POSITION + lengthOfObservedAllele);

					if( !observedAllele.equals( Commons.STRING_HYPHEN)){
						alteredSNPSequence = formerSNPReferenceSequence + observedAllele + latterSNPReferenceSequence;
						snpAlteredSequenceNames.add( Commons.RS + rsId + Commons.UNDERSCORE + observedAllele);

					}else{
						alteredSNPSequence = formerSNPReferenceSequence + latterSNPReferenceSequence;
						snpAlteredSequenceNames.add( Commons.RS + rsId + Commons.UNDERSCORE);
					}

					usedObservedAlleles.add( observedAllele);
					snpAlteredSequences.add( alteredSNPSequence);

				}// End of IF: this observed allele is not used yet

			}

		}// End of for each observed allele

		return contains;
	}

	public static void createSNPAlteredSequences( SNPInformation snpInformation, int rsId,
			List<String> observedAllelesList) {

		// For each observed allele
		// Check whether snpReferenceSequence contains any of these observed
		// alleles starting at snp start position
		// If yes; use the rest of the observed alleles in generation of
		// alteredSequences
		// If no; give alarm; snpReferenceSequence does not contain any of this
		// observed allele starting at snp start position
		Boolean snpContainsAnyOfObservedAlleles;

		snpContainsAnyOfObservedAlleles = checkWhetherSNPReferenceSequenceContainsAnyObservedAlleleAndCreateAlteredSequences(
				snpInformation.getSnpAlteredSequenceNames(), 
				snpInformation.getSnpAlteredSequences(),
				snpInformation.getUsedObservedAlleles(), 
				snpInformation.getSnpReferenceSequence(), 
				rsId,
				observedAllelesList);

		if( !snpContainsAnyOfObservedAlleles){
			// Give alarm
			if( GlanetRunner.shouldLog())logger.error( "There is a situation: SNP Reference Sequence does not contain any of the observed alleles.");
			if( GlanetRunner.shouldLog())logger.error( "rsID: " + rsId);
			if( GlanetRunner.shouldLog())logger.error( "snp Reference Sequence: " + snpInformation.getFastaFile());
		}

		snpInformation.setSnpContainsAnyOfObservedAlleles( snpContainsAnyOfObservedAlleles);

	}

	public static List<String> convertSlashSeparatedObservedAllelesIntoAStringList( String slashSeparatedObservedAlleles) {

		List<String> observedAllelesList = new ArrayList<String>();

		int indexofFormerSlash;
		int indexofLatterSlash;
		String allele;

		indexofFormerSlash = slashSeparatedObservedAlleles.indexOf( Commons.SLASH);

		/*****************************************************************/
		/************* For the first allele starts *************************/
		/*****************************************************************/
		allele = slashSeparatedObservedAlleles.substring( 0, indexofFormerSlash);
		observedAllelesList.add( allele);
		/*****************************************************************/
		/************* For the first allele ends ***************************/
		/*****************************************************************/

		indexofLatterSlash = slashSeparatedObservedAlleles.indexOf( Commons.SLASH, indexofFormerSlash + 1);

		/*****************************************************************/
		/************* For the middle allele starts ************************/
		/*****************************************************************/
		while( indexofFormerSlash != -1 && indexofLatterSlash != -1){
			allele = slashSeparatedObservedAlleles.substring( indexofFormerSlash + 1, indexofLatterSlash);
			observedAllelesList.add( allele);

			indexofFormerSlash = indexofLatterSlash;
			indexofLatterSlash = slashSeparatedObservedAlleles.indexOf( Commons.SLASH, indexofFormerSlash + 1);
		}
		/*****************************************************************/
		/************* For the middle allele ends **************************/
		/*****************************************************************/

		/*****************************************************************/
		/************* For the last allele starts **************************/
		/*****************************************************************/
		allele = slashSeparatedObservedAlleles.substring( indexofFormerSlash + 1);
		observedAllelesList.add( allele);
		/*****************************************************************/
		/************* For the last allele ends ****************************/
		/*****************************************************************/

		return observedAllelesList;

	}

	public static void createSNPAlteredSequences( SNPInformation snpInformation, RsInformation rsInformation) {

		List<String> observedAllelesList = convertSlashSeparatedObservedAllelesIntoAStringList( rsInformation.getSlashSeparatedObservedAlleles());

		List<String> complementedObservedAllelesList = null;

		// rsID Orient is Forward
		if( rsInformation.getOrient().isForward()){
			createSNPAlteredSequences( snpInformation, rsInformation.getRsId(), observedAllelesList);
		}
		// rsID Orient is Reverse
		else{
			// Take Complement of slashSeparatedObservedAlleles
			complementedObservedAllelesList = takeComplement( observedAllelesList);
			createSNPAlteredSequences( snpInformation, rsInformation.getRsId(), complementedObservedAllelesList);
		}

	}

	public static void writeObservedAllelesFile( String snpDirectory, String fileName, String observedAlleles) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try{
			fileWriter = FileOperations.createFileWriter( snpDirectory + System.getProperty( "file.separator") + fileName + ".txt");
			bufferedWriter = new BufferedWriter( fileWriter);

			bufferedWriter.write( observedAlleles + System.getProperty( "line.separator"));

			bufferedWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createPeakSequencesFile( String outputFolder, String directoryBase,
			String sequenceFileDirectory, String fileName, String peakName, String peakSequence) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		int indexofFirstLineSeparator;
		String firstLineofFastaFile;

		try{
			fileWriter = FileOperations.createFileWriter(
					outputFolder + directoryBase + sequenceFileDirectory + System.getProperty( "file.separator") + fileName + ".txt",
					true);
			bufferedWriter = new BufferedWriter( fileWriter);

			indexofFirstLineSeparator = peakSequence.indexOf( System.getProperty( "line.separator"));
			firstLineofFastaFile = peakSequence.substring( 0, indexofFirstLineSeparator);

			bufferedWriter.write( firstLineofFastaFile + "\t" + peakName + System.getProperty( "line.separator"));
			bufferedWriter.write( peakSequence.substring( indexofFirstLineSeparator + 1).trim());

			bufferedWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void writeMatrixFile( String outputFolder, String directoryBase,
			String tfNameCellLineNameorKeggPathwayName, String matrixName, String matrix) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try{
			fileWriter = FileOperations.createFileWriter(
					outputFolder + directoryBase + tfNameCellLineNameorKeggPathwayName + System.getProperty( "file.separator") + matrixName + ".txt",
					true);
			bufferedWriter = new BufferedWriter( fileWriter);

			bufferedWriter.write( matrix);

			bufferedWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Requires chrName without preceeding "chr" string
	// Requires oneBased coordinates
	public static String getDNASequence( 
			String chrNamewithoutPreceedingChr, 
			int oneBasedStart, 
			int oneBasedEnd,
			Map<String, String> chrName2RefSeqIdforLatestAssemblyReturnedByNCBIEutilsMap) {

		String sourceHTML = null;
		String refSeqId;

		refSeqId = chrName2RefSeqIdforLatestAssemblyReturnedByNCBIEutilsMap.get( chrNamewithoutPreceedingChr);

		// GlanetRunner.appendLog("EFETCH RESULT:");
		// Read from the URL
		try{
			String eFetchString = "http://www.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nucleotide&id=" + refSeqId + "&strand=1" + "&seq_start=" + oneBasedStart + "&seq_stop=" + oneBasedEnd + "&rettype=fasta&retmode=text";
			URL url = new URL( eFetchString);

			BufferedReader in = new BufferedReader( new InputStreamReader( url.openStream()));
			String inputLine; // one line of the result, as it is read line by
								// line
			sourceHTML = ""; // will eventually contain the whole result
			// Continue to read lines while there are still some left to read

			// Pay attention
			// Each line including last line has new line character at the end.
			while( ( inputLine = in.readLine()) != null) // read one line of the
															// input stream
			{
				sourceHTML += inputLine + System.getProperty( "line.separator"); // add
																					// this
																					// line
																					// to
																					// end
																					// of
																					// the
																					// whole
																					// shebang
				// ++lineCount; // count the number of lines read
			}

			// Close the connection
			in.close();
		}catch( Exception e){
			if( GlanetRunner.shouldLog())logger.error( "Error reading from the URL:");
			if( GlanetRunner.shouldLog())logger.error( e);
		}

		return sourceHTML;
	}

	public static String getDNASequenceFromFastaFile( String fastaFile) {

		String referenceSequence;
		int indexofFirstLineSeparator;

		indexofFirstLineSeparator = fastaFile.indexOf( System.getProperty( "line.separator"));
		referenceSequence = fastaFile.substring( indexofFirstLineSeparator + 1).trim();

		return referenceSequence;

	}

	// TF starts
	public static void readAllTFAnnotationsWriteSequencesandMatrices(
			AugmentationofGivenIntervalwithRsIds augmentationOfAGivenIntervalWithRsIDs,
			AugmentationofGivenRsIdwithInformation augmentationOfAGivenRsIdWithInformation,
			Map<String, String> chrName2RefSeqIdforLatestAssemblyReturnedByNCBIEutilsMap, 
			String forRSAFolder,
			String all_TF_Annotations_File_1Based_Start_End_LatestAssemblyReturnedFromNCBIEutils, 
			Map<String, String> tfName2PfmMatrices,
			Map<String, String> tfName2LogoMatrices, 
			String enrichmentType) {

		FileReader allTFAnnotationsFileReader;
		BufferedReader allTFAnnotationsBufferedReader;

		String strLine;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;

		String chrNameWithPreceedingChr = null;
		String chrNameWithoutPreceedingChr = null;

		int snpOneBasedStart;
		int snpOneBasedEnd;

		int tfOneBasedStart;
		int tfOneBasedEnd;

		String tfName;
		String cellLineName;
		String fileName;

		String tfNameRemovedLastCharacter;
		String previousTfNameRemovedLastCharacter;

		boolean thereExistsPFMMatrix = false;
		boolean thereExistsLOGOMatrix = false;

		String directoryBase = Commons.TF_PFM_AND_LOGO_Matrices + System.getProperty( "file.separator");

		Boolean isThereAnExactTfNamePfmMatrix = false;

		/*******************************************************************************/
		/********************* TF 2 PFMLogoMatricesExists MAP starts *********************/
		/*******************************************************************************/
		// This map is used whether this pfm matrix file is already created and
		// written for a certain TF.
		// Key is TF Name
		// If once pfm and logo matrices are found and written to text files
		// then there is no need to search and write pfm matrix files again
		Map<String, Boolean> tf2PFMLogoMatriceAlreadyExistsMap = new HashMap<String, Boolean>();
		/*******************************************************************************/
		/********************* TF 2 PFMLogoMatricesExists MAP ends ***********************/
		/*******************************************************************************/

		/*******************************************************************************/
		/************** givenSNP 2 SNPInformation Map starts *****************************/
		/*******************************************************************************/
		// Key is chrNameWithPreceedingChr + "_" + snpOneBasedStart
		String givenSNPKey;
		String snpDirectory;
		List<Integer> rsIdList;
		List<Integer> validRsIdList;
		RsInformation rsInformation;
		SNPInformation snpInformation;
		String fastaFile;
		String snpReferenceSequence;
		int alteredSequenceCount;
		Map<String, SNPInformation> givenSNP2SNPInformationMap = new HashMap<String, SNPInformation>();
		/*******************************************************************************/
		/************** givenSNP 2 SNPInformation Map ends *******************************/
		/*******************************************************************************/

		/*******************************************************************************/
		/************** givenSNP 2 TFOverlapMap Map starts *******************************/
		/*******************************************************************************/
		// 7 April 2014 starts
		// Key is chrNameWithPreceedingChr + "_" + snpOneBasedStart
		Map<String, Map<String, TFOverlaps>> givenSNP2TFName2TFOverlapsMapMap = new HashMap<String, Map<String, TFOverlaps>>();
		Map<String, TFOverlaps> tfName2TFOverlapsMap;

		TFCellLineOverlap tfCellLineOverlap;
		TFOverlaps tfOverlaps;
		/*******************************************************************************/
		/************** givenSNP 2 TFOverlapMap Map ends *********************************/
		/*******************************************************************************/

		/*******************************************************************************/
		/********************* rsID 2 rsInformation MAP starts ***************************/
		/*******************************************************************************/
		Map<Integer, RsInformation> rsID2RsIDInformationMap = new HashMap<Integer, RsInformation>();
		/*******************************************************************************/
		/********************* rsID 2 rsInformation MAP ends *****************************/
		/*******************************************************************************/

		// 10 March 2014
		// Pay attention, there can be more than two observed alleles such as
		// A\tG\tT\t-\tACG
		// Pay attention, for the same chrName and ChrPosition there can be more than one rsIDs
		// Therefore each rsInformation can have observedAlleles String. It is rare but it is possible.

		try{
			
			allTFAnnotationsFileReader = new FileReader( forRSAFolder + all_TF_Annotations_File_1Based_Start_End_LatestAssemblyReturnedFromNCBIEutils);
			allTFAnnotationsBufferedReader = new BufferedReader( allTFAnnotationsFileReader);

			/****************************************************************************************/
			/********************* Reading All TF Annotations File Starts ****************************/
			/****************************************************************************************/
			while( ( strLine = allTFAnnotationsBufferedReader.readLine()) != null){

				// skip strLine starts with '#' comment character
				if( strLine.charAt( 0) != Commons.GLANET_COMMENT_CHARACTER){

					indexofFirstTab = strLine.indexOf( '\t');
					indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
					indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
					indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
					indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
					indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;
					indexofSeventhTab = ( indexofSixthTab > 0)?strLine.indexOf( '\t', indexofSixthTab + 1):-1;
					indexofEigthTab = ( indexofSeventhTab > 0)?strLine.indexOf( '\t', indexofSeventhTab + 1):-1;

					chrNameWithPreceedingChr = strLine.substring( 0, indexofFirstTab);
					chrNameWithoutPreceedingChr = chrNameWithPreceedingChr.substring( 3);

					// Used in finding list of rsIds in this given GRCH38
					// coordinate
					snpOneBasedStart = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
					snpOneBasedEnd = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

					tfOneBasedStart = Integer.parseInt( strLine.substring( indexofFourthTab + 1, indexofFifthTab));
					tfOneBasedEnd = Integer.parseInt( strLine.substring( indexofFifthTab + 1, indexofSixthTab));

					tfName = strLine.substring( indexofSixthTab + 1, indexofSeventhTab);
					cellLineName = strLine.substring( indexofSeventhTab + 1, indexofEigthTab);
					fileName = strLine.substring( indexofEigthTab + 1);

					// Initialize tfNameRemovedLastCharacter to tfName
					tfNameRemovedLastCharacter = tfName;

					/*************************************************************************/
					/********** Create Files for pfm Matrices and logo Matrices starts *********/
					/*************************************************************************/
					if( tf2PFMLogoMatriceAlreadyExistsMap.get( tfName) == null){

						isThereAnExactTfNamePfmMatrix = false;

						// Find PFM entry
						for( Map.Entry<String, String> pfmEntry : tfName2PfmMatrices.entrySet()){
							if( pfmEntry.getKey().contains( tfName)){
								isThereAnExactTfNamePfmMatrix = true;
								writeMatrixFile( forRSAFolder, directoryBase, tfName,
										Commons.PFM_MATRICES + Commons.UNDERSCORE + tfName, pfmEntry.getValue());

							}
						}// End of for

						// Find LOGO entry
						for( Map.Entry<String, String> logoEntry : tfName2LogoMatrices.entrySet()){
							if( logoEntry.getKey().contains( tfName)){
								writeMatrixFile( forRSAFolder, directoryBase, tfName,
										Commons.LOGO_MATRICES + Commons.UNDERSCORE + tfName, logoEntry.getValue());

							}
						}

						if( !isThereAnExactTfNamePfmMatrix){

							thereExistsPFMMatrix = false;
							thereExistsLOGOMatrix = false;

							while( !thereExistsPFMMatrix && !thereExistsLOGOMatrix){
								previousTfNameRemovedLastCharacter = tfNameRemovedLastCharacter;
								// @todo removeLastCharacter may need further check
								tfNameRemovedLastCharacter = removeLastCharacter( tfNameRemovedLastCharacter);

								// If no change
								if( previousTfNameRemovedLastCharacter.equals( tfNameRemovedLastCharacter)){
									break;
								}

								// find pfm entry
								for( Map.Entry<String, String> pfmEntry : tfName2PfmMatrices.entrySet()){
									if( pfmEntry.getKey().contains( tfNameRemovedLastCharacter)){
										thereExistsPFMMatrix = true;
										writeMatrixFile( forRSAFolder, directoryBase, tfName,
												Commons.PFM_MATRICES + Commons.UNDERSCORE + tfName, pfmEntry.getValue());

									}
								}// End of for PFM

								// find logo entry
								for( Map.Entry<String, String> logoEntry : tfName2LogoMatrices.entrySet()){
									if( logoEntry.getKey().contains( tfNameRemovedLastCharacter)){
										thereExistsLOGOMatrix = true;
										writeMatrixFile( forRSAFolder, directoryBase, tfName,
												Commons.LOGO_MATRICES + Commons.UNDERSCORE + tfName,
												logoEntry.getValue());

									}
								}// End of for LOGO

							}// End of while

						}// End of IF there is no exact TF NAME PFM Matrix Match

						tf2PFMLogoMatriceAlreadyExistsMap.put( tfName, true);
					} // End of if
					/*************************************************************************/
					/********** Create Files for pfm Matrices and logo Matrices ends ***********/
					/*************************************************************************/

					/*******************************************************************************************************************************/
					/************************* SET KEYS starts ***************************************************************************************/
					/*******************************************************************************************************************************/
					// Set Given ChrName OneBasedStart
					givenSNPKey = chrNameWithPreceedingChr + "_" + snpOneBasedStart;
					/*******************************************************************************************************************************/
					/************************* SET KEYS ends *****************************************************************************************/
					/*******************************************************************************************************************************/

					/*******************************************************************************************************************************/
					/********************** Filling Maps starts **************************************************************************************/
					/*******************************************************************************************************************************/

					/*************************************************************************/
					/***************** Fill givenSNP2SNPInformationMap starts ******************/
					/*************************************************************************/
					snpInformation = givenSNP2SNPInformationMap.get( givenSNPKey);

					// This SNP is looked for for the first time
					if( snpInformation == null){

						snpInformation = new SNPInformation();

						snpInformation.setChrNameWithoutPreceedingChr( chrNameWithoutPreceedingChr);
						snpInformation.setOneBasedStart( snpOneBasedStart);
						snpInformation.setOneBasedEnd( snpOneBasedEnd);

						// Get all the rsIDs in this given interval
						// We have to provide 1-based coordinates as arguments
						// Caution: This rsIdList may contain merged rsIds
						// rsIdList is a list of integers
						// rsId is an integer without --rs-- prefix
						rsIdList = augmentationOfAGivenIntervalWithRsIDs.getRsIdsInAGivenInterval(chrNameWithoutPreceedingChr, snpOneBasedStart, snpOneBasedEnd);
						validRsIdList = new ArrayList<Integer>();

						/*************************************************************************/
						/*************** Fill rsID2RsIDInformationMap starts ***********************/
						/*************************************************************************/
						for( Integer rsId : rsIdList){

							rsInformation = rsID2RsIDInformationMap.get( rsId);

							// For this rsID, We are getting rsInformation for the first time
							if( rsInformation == null){

								// For each rsId get rsInformation
								rsInformation = augmentationOfAGivenRsIdWithInformation.getInformationforGivenRsId( String.valueOf( rsId));

								// Option 1---Here we can either return a not
								// null rsInformation with a different rsId
								// And check whether given rsID and returned
								// rsID matches
								// If they do not match we can ignore this
								// rsInformation
								// Option 2---Or we can simply return a null
								// rsInformation in case of merge sitution
								// I have chosen ---Option 2--- since I exit the
								// loop and return null, do not continue unused
								// assignments
								if( rsInformation != null){

									// @todo Here we can check whether this rsId
									// is in the given input rsID list if the
									// given input type is dbSNP IDs
									// Decision: I decided --not to do-- such a
									// check since searched rsID and returned
									// rsID might be different because of merge
									// situation

									// rsId and and rsInformation.getRsId()
									// might be different because of merge
									// situation
									if( !rsID2RsIDInformationMap.containsKey( rsInformation.getRsId())){
										rsID2RsIDInformationMap.put( rsInformation.getRsId(), rsInformation);
									}

									if( !validRsIdList.contains( rsInformation.getRsId())){
										validRsIdList.add( rsInformation.getRsId());
									}
								}// End of IF rsInformation is not NULL

							}// End of if rsInformation is null
							else{

								if( GlanetRunner.shouldLog())logger.error( "I guess this else part is unnecessary!");

								// Means that rsInformation is already put
								// so this rsId is not a merged rsId
								if( !validRsIdList.contains( rsInformation.getRsId())){
									validRsIdList.add( rsInformation.getRsId());
								}

							}// End of ELSE: this chrName_OneBasedCoordinate is
								// looked at for the first time but this rsID is
								// found before, can it be?

						}// End of for each rsId
						/*************************************************************************/
						/*************** Fill rsID2RsIDInformationMap ends *************************/
						/*************************************************************************/

						snpInformation.setValidRsIDList( validRsIdList);
						givenSNP2SNPInformationMap.put( givenSNPKey, snpInformation);

					}// End of IF snpInformation is null
					/*************************************************************************/
					/***************** Fill givenSNP2SNPInformationMap ends ********************/
					/*************************************************************************/

					/*************************************************************************/
					/*********** Fill givenSNP2TFName2TFOverlapsMapMap starts ******************/
					/*************************************************************************/
					tfName2TFOverlapsMap = givenSNP2TFName2TFOverlapsMapMap.get( givenSNPKey);

					// This SNP is looked for for the first time
					if( tfName2TFOverlapsMap == null){

						tfName2TFOverlapsMap = new HashMap<String, TFOverlaps>();

						tfOverlaps = tfName2TFOverlapsMap.get( tfName);

						// For this SNP, This TF is looked for the first time
						if( tfOverlaps == null){

							tfOverlaps = new TFOverlaps( tfName,ChromosomeName.convertStringtoEnum( chrNameWithPreceedingChr));

							tfCellLineOverlap = new TFCellLineOverlap( tfName, cellLineName, fileName, tfOneBasedStart,tfOneBasedEnd);

							tfOverlaps.getTfCellLineOverlaps().add( tfCellLineOverlap);

							tfName2TFOverlapsMap.put( tfName, tfOverlaps);
						}

						givenSNP2TFName2TFOverlapsMapMap.put( givenSNPKey, tfName2TFOverlapsMap);

					}
					// For this SNP, we have another kind of TF Overlap
					else{
						tfOverlaps = tfName2TFOverlapsMap.get( tfName);

						// For this SNP, This TF Overlap is looked for the first time.
						if( tfOverlaps == null){

							tfOverlaps = new TFOverlaps( tfName,ChromosomeName.convertStringtoEnum( chrNameWithPreceedingChr));

							tfCellLineOverlap = new TFCellLineOverlap( tfName, cellLineName, fileName, tfOneBasedStart,tfOneBasedEnd);

							tfOverlaps.getTfCellLineOverlaps().add( tfCellLineOverlap);

							tfName2TFOverlapsMap.put( tfName, tfOverlaps);

						}
						// For this SNP, we have another TF Overlap for an already existing TF Overlap
						else{
							tfCellLineOverlap = new TFCellLineOverlap( tfName, cellLineName, fileName, tfOneBasedStart,tfOneBasedEnd);

							tfOverlaps.getTfCellLineOverlaps().add( tfCellLineOverlap);

							tfName2TFOverlapsMap.put( tfName, tfOverlaps);

						}

						givenSNP2TFName2TFOverlapsMapMap.put( givenSNPKey, tfName2TFOverlapsMap);
					}
					/*************************************************************************/
					/*********** Fill givenSNP2TFName2TFOverlapsMapMap ends ********************/
					/*************************************************************************/

					/*******************************************************************************************************************************/
					/********************** Filling Maps ends ****************************************************************************************/
					/*******************************************************************************************************************************/

				}// End of IF strLine is not comment character

			}// End of While loop reading: allTFAnnotationsBufferedReader
			/****************************************************************************************/
			/********************* Reading All TF Annotations File Ends ******************************/
			/****************************************************************************************/

			/****************************************************************************************/
			/****************** Close All TF Annotations Buffered Writer ******************************/
			/****************************************************************************************/
			allTFAnnotationsBufferedReader.close();
			/****************************************************************************************/
			/****************** Close All TF Annotations Buffered Writer ******************************/
			/****************************************************************************************/

			/*******************************************************************************************************************************/
			/**************** Get DNA sequences starts ***************************************************************************************/
			/**************** SNP Reference Sequence *****************************************************************************************/
			/**************** SNP Alternate Sequences ****************************************************************************************/
			/**************** TF OVERLAP Peak Sequence ***************************************************************************************/
			for( Map.Entry<String, SNPInformation> entry : givenSNP2SNPInformationMap.entrySet()){
				givenSNPKey = entry.getKey();
				snpInformation = entry.getValue();

				// Get Fasta File for each SNP
				// Get SNP Reference DNA Sequence from fasta file for each SNP
				fastaFile = getDNASequence(
						snpInformation.getChrNameWithoutPreceedingChr(),
						snpInformation.getOneBasedStart() - Commons.NUMBER_OF_BASES_BEFORE_SNP_POSITION,
						snpInformation.getOneBasedEnd() + Commons.NUMBER_OF_BASES_AFTER_SNP_POSITION,
						chrName2RefSeqIdforLatestAssemblyReturnedByNCBIEutilsMap);

				snpInformation.setFastaFile( fastaFile);
				snpReferenceSequence = getDNASequenceFromFastaFile( fastaFile);
				snpInformation.setSnpReferenceSequence( snpReferenceSequence);

				/*****************************************************************/
				/***************** Set SNP directory starts ************************/
				/*****************************************************************/
				snpDirectory = forRSAFolder + Commons.SNPs + System.getProperty( "file.separator") + entry.getKey();

				// Add valid rsIDs to the snpDirectory
				for( Integer validRsId : snpInformation.getValidRsIDList()){
					snpDirectory = snpDirectory + Commons.UNDERSCORE + Commons.RS + validRsId;
				}// End of for each valid rsID in this SNP

				snpDirectory = snpDirectory + System.getProperty( "file.separator");
				/*****************************************************************/
				/***************** Set SNP directory ends **************************/
				/*****************************************************************/

				/*****************************************************************/
				/******** Write SNP Reference DNA Sequence starts ******************/
				/*****************************************************************/
				writeSequenceFile( snpDirectory, Commons.SNP_REFERENCE_SEQUENCE + "_" + entry.getKey(),entry.getValue().getFastaFile());
				/*****************************************************************/
				/******** Write SNP Reference DNA Sequence ends ********************/
				/*****************************************************************/

				/**********************************************************************************/
				/*********** Write valid rsID Based SNP Observed Alleles Files starts ***************/
				/**********************************************************************************/
				for( Integer validRsId : snpInformation.getValidRsIDList()){

					rsInformation = rsID2RsIDInformationMap.get( validRsId);
					writeObservedAllelesFile(
							snpDirectory,
							Commons.OBSERVED_ALLELES + Commons.UNDERSCORE + Commons.RS + validRsId + Commons.UNDERSCORE + rsInformation.getOrient().convertEnumtoString(),
							rsInformation.getSlashSeparatedObservedAlleles());

					/*******************************************************************/
					/************* Create SNP Altered Sequences starts *******************/
					/*******************************************************************/
					createSNPAlteredSequences( snpInformation, rsInformation);
					/*******************************************************************/
					/************* Create SNP Altered Sequences starts *******************/
					/*******************************************************************/

					/*******************************************************************/
					/************* Write SNP Altered Sequences starts ********************/
					/*******************************************************************/
					if( snpInformation.isSnpContainsAnyOfObservedAlleles()){
						alteredSequenceCount = 0;

						for( String alteredSequence : snpInformation.getSnpAlteredSequences()){
							writeSequenceFile(
									snpDirectory,
									Commons.SNP_ALTERED_SEQUENCE + Commons.UNDERSCORE + snpInformation.getSnpAlteredSequenceNames().get(
											alteredSequenceCount) + Commons.UNDERSCORE + entry.getKey(),
									alteredSequence);
							alteredSequenceCount++;
						}
					}
					/*******************************************************************/
					/************* Write SNP Altered Sequences ends **********************/
					/*******************************************************************/

				}// End of for each valid rsID in this SNP
				/**********************************************************************************/
				/*********** Write valid rsID Based SNP Observed Alleles Files ends ****************/
				/**********************************************************************************/

				/**********************************************************************************/
				/************** Write TF Name Based TF Overlaps File starts *************************/
				/************** Write TF PEAK Sequence starts ***************************************/
				/**********************************************************************************/
				tfName2TFOverlapsMap = givenSNP2TFName2TFOverlapsMapMap.get( givenSNPKey);
				writeTFBasedTFOverlapsFileAndTFPeakSequenceFile( 
						snpDirectory, 
						tfName2TFOverlapsMap,
						snpInformation.getChrNameWithoutPreceedingChr(), 
						chrName2RefSeqIdforLatestAssemblyReturnedByNCBIEutilsMap);
				/**********************************************************************************/
				/************** Write TF Name Based TF Overlaps File ends ***************************/
				/************** Write TF PEAK Sequence ends *****************************************/
				/**********************************************************************************/

			}// End of for each SNP
			/*******************************************************************************************************************************/
			/**************** SNP Reference Sequence *****************************************************************************************/
			/**************** SNP Alternate Sequences ****************************************************************************************/
			/**************** TF OVERLAP Peak Sequence ***************************************************************************************/
			/**************** Get DNA sequences ends *****************************************************************************************/
			/*******************************************************************************************************************************/

		}catch( FileNotFoundException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// end ends

	// Pay Attention
	// Contains X for chrX
	// Contains 1 for chr1

	

	// Pay Attention
	// Contains X for chrX
	// Contains 1 for chr1
	// # Sequence-Name Sequence-Role Assigned-Molecule
	// Assigned-Molecule-Location/Type GenBank-Accn Relationship RefSeq-Accn
	// Assembly-Unit
	// 1 assembled-molecule 1 Chromosome CM000663.1 = NC_000001.10 Primary
	// Assembly
	// X assembled-molecule X Chromosome CM000685.1 = NC_000023.10 Primary
	// Assembly
	public Map<String, String> fillMap( String refSeqIdsforGRCh37InputFile) {

		Map<String, String> chrName2RefSeqIdforGrch37Map = new HashMap<String, String>();

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine = null;
		int numberofChromosomesinHomoSapiens = 24;
		int count = 0;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;

		String chrName;
		String refSeqId;

		try{
			fileReader = new FileReader( refSeqIdsforGRCh37InputFile);
			bufferedReader = new BufferedReader( fileReader);

			while( ( strLine = bufferedReader.readLine()) != null){
				if( strLine.startsWith( "#")){
					continue;
				}else{
					if( count < numberofChromosomesinHomoSapiens){
						count++;

						indexofFirstTab = strLine.indexOf( '\t');
						indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
						indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
						indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);
						indexofFifthTab = strLine.indexOf( '\t', indexofFourthTab + 1);
						indexofSixthTab = strLine.indexOf( '\t', indexofFifthTab + 1);
						indexofSeventhTab = strLine.indexOf( '\t', indexofSixthTab + 1);

						chrName = strLine.substring( 0, indexofFirstTab);
						refSeqId = strLine.substring( indexofSixthTab + 1, indexofSeventhTab);

						chrName2RefSeqIdforGrch37Map.put( chrName, refSeqId);
						continue;

					}
				}

				break;

			}

			bufferedReader.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return chrName2RefSeqIdforGrch37Map;
	}

	// args[0] ---> Input File Name with folder
	// args[1] ---> GLANET installation folder with "\\" at the end. This folder
	// will be used for outputFolder and dataFolder.
	// args[2] ---> Input File Format
	// ---> default
	// Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// args[3] ---> Annotation, overlap definition, number of bases,
	// default 1
	// args[4] ---> Perform Enrichment parameter
	// ---> default Commons.DO_ENRICH
	// ---> Commons.DO_NOT_ENRICH
	// args[5] ---> Generate Random Data Mode
	// ---> default Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	// ---> Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT
	// args[6] ---> multiple testing parameter, enriched elements will be
	// decided and sorted with respect to this parameter
	// ---> default Commons.BENJAMINI_HOCHBERG_FDR
	// ---> Commons.BONFERRONI_CORRECTION
	// args[7] ---> Bonferroni Correction Significance Level, default 0.05
	// args[8] ---> Bonferroni Correction Significance Criteria, default 0.05
	// args[9] ---> Number of permutations, default 5000
	// args[10] ---> Dnase Enrichment
	// ---> default Commons.DO_NOT_DNASE_ENRICHMENT
	// ---> Commons.DO_DNASE_ENRICHMENT
	// args[11] ---> Histone Enrichment
	// ---> default Commons.DO_NOT_HISTONE_ENRICHMENT
	// ---> Commons.DO_HISTONE_ENRICHMENT
	// args[12] ---> Transcription Factor(TF) Enrichment
	// ---> default Commons.DO_NOT_TF_ENRICHMENT
	// ---> Commons.DO_TF_ENRICHMENT
	// args[13] ---> KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_KEGGPATHWAY_ENRICHMENT
	// args[14] ---> TF and KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	// args[15] ---> TF and CellLine and KeggPathway Enrichment
	// ---> default Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// args[16] ---> RSAT parameter
	// ---> default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// ---> Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// args[17] ---> job name example: ocd_gwas_snps
	// args[18] ---> writeGeneratedRandomDataMode checkBox
	// ---> default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	// ---> Commons.WRITE_GENERATED_RANDOM_DATA
	// args[19] ---> writePermutationBasedandParametricBasedAnnotationResultMode
	// checkBox
	// ---> default
	// Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// --->
	// Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// args[20] ---> writePermutationBasedAnnotationResultMode checkBox
	// ---> default Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	// ---> Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	// args[21] ---> number of permutations in each run. Default is 2000
	// args[22] ---> UserDefinedGeneSet Enrichment
	// default Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT
	// Commons.DO_USER_DEFINED_GENESET_ENRICHMENT
	// args[23] ---> UserDefinedGeneSet InputFile
	// args[24] ---> UserDefinedGeneSet GeneInformationType
	// default Commons.GENE_ID
	// Commons.GENE_SYMBOL
	// Commons.RNA_NUCLEOTIDE_ACCESSION
	// args[25] ---> UserDefinedGeneSet Name
	// args[26] ---> UserDefinedGeneSet Optional GeneSet Description InputFile
	// args[27] ---> UserDefinedLibrary Enrichment
	// default Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT
	// Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT
	// args[28] ---> UserDefinedLibrary InputFile
	// args[29] - args[args.length-1] ---> Note that the selected cell lines are
	// always inserted at the end of the args array because it's size
	// is not fixed. So for not (until the next change on args array) the
	// selected cell
	// lines can be reached starting from 22th index up until (args.length-1)th
	// index.
	// If no cell line selected so the args.length-1 will be 22-1 = 21. So it
	// will never
	// give an out of boundry exception in a for loop with this approach.
	public static void main( String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];

		// jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if( jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		// jobName ends

		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
		String outputFolder = args[CommandLineArguments.OutputFolder.value()];

		String forRSAFolder = outputFolder + Commons.FOR_RSA + System.getProperty( "file.separator");

		// TfAnnotation, DO or DO_NOT
		AnnotationType tfAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.TfAnnotation.value()]);
		AnnotationType tfKEGGAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.TfAndKeggPathwayAnnotation.value()]);
		AnnotationType tfCellLineKEGGAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()]);


		// pfm matrices
		String encodeMotifsInputFileName = Commons.ENCODE_MOTIFS;
		String jasparCoreInputFileName = Commons.JASPAR_CORE;

		// TF
		String all_TF_Annotations_File_1Based_Start_End_LatestAssemblyReturnedByNCBIEutils = Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_LATEST_ASSEMBLY_RETURNED_BY_NCBI_EUTILS;

		// Example Data
		// 7 NC_000007.13 GRCh37
		// Chromosome 7 CM000669.2 = NC_000007.14 0 GRCh37
		Map<String, String> chrName2RefSeqIdforLatestAssemblyReturnedByNCBIEutilsMap = new HashMap<String, String>();

			
		
		/***************************************************************************************/
		/***********************************Part1 starts****************************************/
		/***************************************************************************************/
		String latestAssemblyNameReturnedByNCBIEutils = null;
		latestAssemblyNameReturnedByNCBIEutils = NCBIEutils.getLatestAssemblyNameReturnedByNCBIEutils();
		/***************************************************************************************/
		/***********************************Part1 ends******************************************/
		/***************************************************************************************/
		
		
		
		/***************************************************************************************/
		/***********************************Part2 starts****************************************/
		/***************************************************************************************/
		Map<String, String> assemblyName2RefSeqAssemblyIDMap = new HashMap<String, String>();
		
		//No need to call this again and again.
		//Since it is already called in GenerationofAllTFAnnotationsFileInGRCh37p13AndInLatestAssembly class
		//Remap.remap_show_batches(dataFolder, Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);
		
		Remap.fillAssemblyName2RefSeqAssemblyIDMap(
				dataFolder, 
				Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE,
				assemblyName2RefSeqAssemblyIDMap);
		/***************************************************************************************/
		/***********************************Part2 ends******************************************/
		/***************************************************************************************/
	

		/***************************************************************************************/
		/***********************************Part3 starts****************************************/
		/***************************************************************************************/
		String refSeqAssemblyID = NCBIEutils.getRefSeqAssemblyID(latestAssemblyNameReturnedByNCBIEutils, assemblyName2RefSeqAssemblyIDMap);
		/***************************************************************************************/
		/***********************************Part3 ends******************************************/
		/***************************************************************************************/
		
		
		
		/***************************************************************************************/
		/***********************************Part4 starts****************************************/
		/***************************************************************************************/
		// Download from  ftp://ftp.ncbi.nlm.nih.gov/genomes/ASSEMBLY_REPORTS/All/RefSeqAssemblyID.assembly.txt
		String assemblyReportFileName = Commons.ASSEMBLY_REPORTS +  refSeqAssemblyID + Commons.ASSEMBLY_REPORTS_FILE_EXTENSION ;
		NCBIEutils.getAssemblyReport(refSeqAssemblyID, dataFolder, assemblyReportFileName);
		/***************************************************************************************/
		/***********************************Part4 ends******************************************/
		/***************************************************************************************/
		
		
		
		/***************************************************************************************/
		/***********************************Part5 starts****************************************/
		/***************************************************************************************/
		NCBIEutils.fillChrName2RefSeqIDMap( dataFolder, assemblyReportFileName, chrName2RefSeqIdforLatestAssemblyReturnedByNCBIEutilsMap);
		/***************************************************************************************/
		/***********************************Part5 ends******************************************/
		/***************************************************************************************/

		// Construct pfm matrices from encode-motif.txt file
		// A tf can have more than one pfm matrices
		// Take the transpose of given matrices in encode-motif.txt
		// Write the matrices in tab format for RSAT tool
		Map<String, String> tfName2PfmMatrices = new HashMap<String, String>();

		Map<String, String> tfName2LogoMatrices = new HashMap<String, String>();

		// Construct position frequency matrices from Encode Motifs
		constructPfmMatricesfromEncodeMotifs( dataFolder, encodeMotifsInputFileName, tfName2PfmMatrices);

		// Construct logo matrices from Encode Motifs
		constructLogoMatricesfromEncodeMotifs( dataFolder, encodeMotifsInputFileName, tfName2LogoMatrices);

		// Construct position frequency matrices from Jaspar Core
		// Construct logo matrices from Jaspar Core
		constructPfmMatricesandLogoMatricesfromJasparCore(
				dataFolder, 
				jasparCoreInputFileName, 
				tfName2PfmMatrices,
				tfName2LogoMatrices);

		AugmentationofGivenIntervalwithRsIds augmentationOfAGivenIntervalWithRsIDs;
		AugmentationofGivenRsIdwithInformation augmentationOfAGivenRsIdWithInformation;

		try{
			augmentationOfAGivenIntervalWithRsIDs = new AugmentationofGivenIntervalwithRsIds();
			augmentationOfAGivenRsIdWithInformation = new AugmentationofGivenRsIdwithInformation();

			// TF Annotations are used
			if( tfAnnotationType.doTFAnnotation() || 
				tfKEGGAnnotationType.doTFKEGGPathwayAnnotation() || 
				tfCellLineKEGGAnnotationType.doTFCellLineKEGGPathwayAnnotation()){

				// Generate Sequences and Matrices for Annotated TF Elements
				readAllTFAnnotationsWriteSequencesandMatrices(
						augmentationOfAGivenIntervalWithRsIDs,
						augmentationOfAGivenRsIdWithInformation, 
						chrName2RefSeqIdforLatestAssemblyReturnedByNCBIEutilsMap, 
						forRSAFolder,
						all_TF_Annotations_File_1Based_Start_End_LatestAssemblyReturnedByNCBIEutils, 
						tfName2PfmMatrices, 
						tfName2LogoMatrices,
						Commons.TF);

			}

		}catch( Exception e){
			e.printStackTrace();
		}

	}

}
