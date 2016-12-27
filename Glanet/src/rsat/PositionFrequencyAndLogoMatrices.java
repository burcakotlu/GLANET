/**
 * 
 */
package rsat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jaxbxjctool.PositionFrequency;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;

/**
 * @author Burçak Otlu
 * @date Dec 23, 2016
 * @project Glanet 
 *
 */
public class PositionFrequencyAndLogoMatrices {
	
	final static Logger logger = Logger.getLogger(PositionFrequencyAndLogoMatrices.class);

	
	public static void writeMatrixFile(
			String outputFolder, 
			String directoryBase,
			String tfNameCellLineNameorKeggPathwayName, 
			String matrixName, 
			String matrix) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try{
			fileWriter = FileOperations.createFileWriter(
					outputFolder + directoryBase + tfNameCellLineNameorKeggPathwayName + System.getProperty( "file.separator") + matrixName + ".txt",
					true);
			bufferedWriter = new BufferedWriter( fileWriter);

			bufferedWriter.write(matrix);

			bufferedWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//25 DEC 2016
	public static void putPFM(
			String tfName, 
			String firstLineContainingTFName,
			List<Float> AFrequencyList, 
			List<Float> CFrequencyList,
			List<Float> GFrequencyList, 
			List<Float> TFrequencyList, 
			Map<String, String> tfName2PfmMatrices) {

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
			tfName2PfmMatrices.put( tfName, "; " + firstLineContainingTFName + System.getProperty( "line.separator"));
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
					tfName2PfmMatrices.get( tfName) + "; " + firstLineContainingTFName + System.getProperty( "line.separator"));
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
	
	
	public static void putLogoMatrix(
			String tfName, 
			String firstLineContainingTFName,
			List<Float> AFrequencyList, 
			List<Float> CFrequencyList,
			List<Float> GFrequencyList, 
			List<Float> TFrequencyList, 
			Map<String, String> tfName2LogoMatrices) {

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
	
	public static void fillFrequencyListUsingCountList(
			List<Float> frequencyList, 
			List<Integer> countList,
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
	
	public static int getTotalCount(
			List<Integer> ACountList, 
			List<Integer> CCountList, 
			List<Integer> GCountList,
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
	
	// Fill CountList using CountLine
	public static void fillCountList(String countLine, List<Integer> countList) {

		// old example Count line
		// 4 19 0 0 0 0
		
		//new example Count line
		//15 DEC 2016
		//A  [ 3  0  0  0  0  0 ]
		
		int indexofOpenBracket = countLine.indexOf('[');
		int indexofCloseBracket = countLine.indexOf(']');
		
		String countLineWithoutBrackets = null;		
		countLineWithoutBrackets = countLine.substring(indexofOpenBracket+1, indexofCloseBracket);
		
		String[] countsStringArray =countLineWithoutBrackets.split(" ");
		
		for(String eachCount:countsStringArray){
			eachCount  = eachCount.trim();
			if (!eachCount.isEmpty())
				countList.add(Integer.parseInt(eachCount));
		}//End of FOR
		

	}
	
	public static String getTFName(String strLine){
		
		String tfName = null;
		int indexofTab =  strLine.indexOf('\t') ;
		int indexofOpenParanthesis = -1;
		int indexofColon = strLine.indexOf(':');
		
		if (indexofTab>-1){
			tfName = strLine.substring(indexofTab+1);			
		}
		 
		indexofOpenParanthesis = tfName.indexOf('(');
		if (indexofOpenParanthesis>-1){
			tfName = tfName.substring(0,indexofOpenParanthesis);			
		}
		
		
		indexofColon = tfName.indexOf(':');
		if (indexofColon>-1){
			tfName = tfName.substring(0,indexofColon);			
		}
		
		return tfName;
		
	}
	
	//25 DEC 2016
	public static void constructPfmMatricesandLogoMatricesfromJasparCoreWithSpecificTFName(
			String dataFolder,
			String jasparCoreInputFileName, 
			Map<String, String> tfName2PfmMatrices,
			Map<String, String> tfName2LogoMatrices) {

		// Attention
		// Order is ACGT

		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;

		String tfName = null;
		String firstLineContainingTFName = null;

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

			// Example old matrix from jaspar core pfm_all.txt
			// >MA0004.1 Arnt
			// 4 19 0 0 0 0
			// 16 0 20 0 0 0
			// 0 1 0 20 0 20
			// 0 0 0 0 20 0
			
			// Example new matrix
			//>MA0006.1	Ahr::Arnt
			//A  [ 3  0  0  0  0  0 ]
			//C  [ 8  0 23  0  0  0 ]
			//G  [ 2 23  0 23  0 24 ]
			//T  [11  1  1  1 24  0 ]

			while( ( strLine = bufferedReader.readLine()) != null){
				if( strLine.startsWith( ">")){
					
					tfName = getTFName(strLine);
					firstLineContainingTFName= strLine;
					
					// Initialize array lists for the new coming position count matrix and position frequency matrix
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
						putPFM( tfName, 
								firstLineContainingTFName,
								AFrequencyList, 
								CFrequencyList, 
								GFrequencyList, 
								TFrequencyList, 
								tfName2PfmMatrices);
	
						// Put the transpose of the matrix for logo generation
						putLogoMatrix( tfName, 
								firstLineContainingTFName,
								AFrequencyList, 
								CFrequencyList, 
								GFrequencyList, 
								TFrequencyList,
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

	
	
	
	public static void constructPfmMatricesandLogoMatricesfromJasparCore(
			String dataFolder,
			String jasparCoreInputFileName, 
			Map<String, String> tfName2PfmMatrices,
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

			// Example old matrix from jaspar core pfm_all.txt
			// >MA0004.1 Arnt
			// 4 19 0 0 0 0
			// 16 0 20 0 0 0
			// 0 1 0 20 0 20
			// 0 0 0 0 20 0
			
			// Example new matrix
			//>MA0006.1	Ahr::Arnt
			//A  [ 3  0  0  0  0  0 ]
			//C  [ 8  0 23  0  0  0 ]
			//G  [ 2 23  0 23  0 24 ]
			//T  [11  1  1  1 24  0 ]

			while( ( strLine = bufferedReader.readLine()) != null){
				if( strLine.startsWith( ">")){
					tfName = strLine.substring( 1);

					// Initialize array lists for the new coming position count matrix and position frequency matrix
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
						putPFM( tfName, 
								tfName,
								AFrequencyList, 
								CFrequencyList, 
								GFrequencyList, 
								TFrequencyList, 
								tfName2PfmMatrices);
	
						// Put the transpose of the matrix for logo generation
						putLogoMatrix( tfName, 
								tfName,
								AFrequencyList, 
								CFrequencyList, 
								GFrequencyList, 
								TFrequencyList,
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

	
	
	
	public static void constructLogoMatricesfromEncodeMotifs( 
			String dataFolder, 
			String encodeMotifsInputFileName,
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

	public static void constructPfmMatricesfromEncodeMotifs(
			String dataFolder, 
			String encodeMotifsInputFileName,
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


	

}
