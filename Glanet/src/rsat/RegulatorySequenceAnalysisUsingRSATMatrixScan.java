/**
 * @author Burcak Otlu
 * Jan 30, 2014
 * 12:05:22 PM
 * 2014
 *
 *
 *How to read RSAT result?
 *
 *First of all, RSAT results are 1 Based coordinates.
 *
 *example
 *
 *>altered1_snp_chr10_111787715
 *GGTAGAAAAGGTTGCGGGGGGAAGAAAAG
 *1                           29
 *first base is at 1
 *last base is at 29
 *
 *Best alteredSequence1 containing snp position  found at 5. result lines	R	10	18	CCCGCAACC	3.20E-02	-1.43E-01
 *
 *
 * from sequence above(29 base long); get the sequence between 10. and 18. bases (inclusive) 
 * which is        GGTTGCGGG 
 * take complement CCAACGCCC
 * take reverse    CCCGCAACC
 * we got it
 * 
 * In addition to that
 * we read ..... found at 5. result lines R	10	18	CCCGCAACC .... as follows
 *  first base of (R	10	18	CCCGCAACC)  is the  18.th base of original (29 base long) sequence
 *  last base  of (R	10	18	CCCGCAACC) is the 10.th base of the original (29. base long) sequence
 * since we took the reverse of it
 * 
 * In fact,  snp is always at the Commons.ONE_BASED_SNP_POSITION
 * snp is always at the 15. th base position which is G in this case (R	10	18	CCCGCAACC)
 * snp is always at the Commons.ONE_BASED_SNP_POSITION
 */
package rsat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.rpc.ServiceException;
import org.apache.log4j.Logger;
import RSATWS.MatrixScanRequest;
import RSATWS.MatrixScanResponse;
import RSATWS.RSATWSPortType;
import RSATWS.RSATWebServicesLocator;
import auxiliary.FileOperations;
import auxiliary.GlanetDecimalFormat;
import common.Commons;
import enumtypes.CommandLineArguments;
import ui.GlanetRunner;

public class RegulatorySequenceAnalysisUsingRSATMatrixScan {

	final static Logger logger = Logger.getLogger(RegulatorySequenceAnalysisUsingRSATMatrixScan.class);

	/**
	 * 
	 */
	public RegulatorySequenceAnalysisUsingRSATMatrixScan() {

		// TODO Auto-generated constructor stub
	}

	public static String readFirstLinefromFasta( String fasta) {

		String firstLine = null;

		int indexofLineSeparator;

		indexofLineSeparator = fasta.indexOf( System.getProperty( "line.separator"));

		if( indexofLineSeparator > 0){
			firstLine = fasta.substring( 0, indexofLineSeparator);
		}

		return firstLine;

	}

	public static String readFirstLinefromFile( String inputFileName) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine = null;
		String firstLine = "";

		try{
			fileReader = new FileReader( inputFileName);
			bufferedReader = new BufferedReader( fileReader);

			while( ( strLine = bufferedReader.readLine()) != null){
				firstLine = firstLine + strLine;
				break;
			}

			// Close BufferedReader
			bufferedReader.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())
			logger.error( e.toString());
		}

		return firstLine;

	}

	public static String readAllfromFile( String inputFileName) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine = null;
		String all = "";

		try{
			fileReader = FileOperations.createFileReader(inputFileName);
			bufferedReader = new BufferedReader( fileReader);

			while( ( strLine = bufferedReader.readLine()) != null){
				all = all + strLine + System.getProperty( "line.separator");

			}

			bufferedReader.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())
			logger.error( e.toString());
		}

		return all;

	}

	// direction does not matter
	// start is always less than end
	public static boolean constainsSNP( int start, int end, int snpPosition) {

		if( snpPosition <= end && snpPosition >= start){ return true; }

		return false;
	}

	public static int getTFPeakOneBasedStartPosition( String resultLine) {

		// example
		// gi|224589818:170862228-170862467 site matrix-scan-matrix_2014-04-14.5
		// R 37 45 ACAGAGCCG 4.7 5.6e-04 -7.490 3.253 1 1
		// gi|568815576:21628381-21630286 site matrix-scan-matrix_2015-01-27.11
		// D 695 704 GGCACGCGCG 8.0 4.4e-07 -14.632 6.354 1 1

		int indexofFirstColon = resultLine.indexOf( ':');
		int indexofFirstHyphen = resultLine.indexOf( '-');

		return Integer.parseInt( resultLine.substring( indexofFirstColon + 1, indexofFirstHyphen));

	}

	// public static int getOneBasedSNPStart(String bestSNPResultLine){
	//
	// //example snp result line
	// //gi|224589818:170862322-170862350 site matrix-scan-matrix_2014-04-11.5 D
	// 13 21 ACGGCTGCG 3.5 3.7e-03 -5.602 2.433 2 2
	// int indexofFirstColon = bestSNPResultLine.indexOf(':');
	// int indexofFirstHyphen = bestSNPResultLine.indexOf('-');
	//
	// return Integer.parseInt(bestSNPResultLine.substring(indexofFirstColon+1,
	// indexofFirstHyphen)) + Commons.NUMBER_OF_BASES_BEFORE_SNP_POSITION;
	//
	// }

	public static int getSNPOneBasedPosition( String snpReferenceSequence) {

		// example snp reference sequence
		// >gi|568815587:47291327-47291355 Homo sapiens chromosome 11, GRCh38
		// Primary Assembly SNPReferenceSequence_chr11_47291341
		// GTTCTTTAAATGCTTCTTCACTGGACTTG
		int indexofFirstColon = snpReferenceSequence.indexOf( ':');
		int indexofFirstHyphen = snpReferenceSequence.indexOf( '-');

		return Integer.parseInt( snpReferenceSequence.substring( indexofFirstColon + 1, indexofFirstHyphen)) + Commons.NUMBER_OF_BASES_BEFORE_SNP_POSITION;

	}

	// 27 January 2015
	// This method will work for
	// SNP_REFERENCE_SEQUENCE
	// SNP_ALTERED_SEQUENCE
	// TF_EXTENDED_PEAK_SEQUENCE
	public static Result checkWhetherResultLineContainsSNPPositionAndWriteIfContains(
			SequenceType sequenceType,
			String description, 
			String resultLine, 
			BufferedWriter bufferedWriter, 
			Integer snpOneBasedPosition,
			Result snpReferenceSequenceResultContaingSNPPosition) {

		/*********************** SET DECIMAL FORMAT SEPARATORS *****************************/
		DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat( "0.######E0");
		/*********************** SET DECIMAL FORMAT SEPARATORS *****************************/

		int start = 0;
		int end = 0;

		String matrixName = null;
		int indexofDot;
		int matrixNumber = 0;

		char direction = ' ';
		String sequence = null;
		double pValue = 0;
		double logRatio;
		boolean containsSNP = false;

		Result result = null;

		int tfPeakOneBasedStartPosition = 0;
		int snpOneBasedPositionWithRespectoPeakSequence = 0;

		int indexofFirstTab = resultLine.indexOf( '\t');
		int indexofSecondTab = ( indexofFirstTab > 0)?resultLine.indexOf( '\t', indexofFirstTab + 1):-1;
		int indexofThirdTab = ( indexofSecondTab > 0)?resultLine.indexOf( '\t', indexofSecondTab + 1):-1;
		int indexofFourthTab = ( indexofThirdTab > 0)?resultLine.indexOf( '\t', indexofThirdTab + 1):-1;
		int indexofFifthTab = ( indexofFourthTab > 0)?resultLine.indexOf( '\t', indexofFourthTab + 1):-1;
		int indexofSixthTab = ( indexofFifthTab > 0)?resultLine.indexOf( '\t', indexofFifthTab + 1):-1;
		int indexofSeventhTab = ( indexofSixthTab > 0)?resultLine.indexOf( '\t', indexofSixthTab + 1):-1;
		int indexofEigthTab = ( indexofSeventhTab > 0)?resultLine.indexOf( '\t', indexofSeventhTab + 1):-1;
		int indexofNinethTab = ( indexofEigthTab > 0)?resultLine.indexOf( '\t', indexofEigthTab + 1):-1;

		start = Integer.parseInt( resultLine.substring( indexofFourthTab + 1, indexofFifthTab));
		end = Integer.parseInt( resultLine.substring( indexofFifthTab + 1, indexofSixthTab));

		switch( sequenceType){

		case SNP_REFERENCE_SEQUENCE:
		case SNP_ALTERED_SEQUENCE:
			containsSNP = constainsSNP( start, end, Commons.ONE_BASED_SNP_POSITION);
			break;

		case TF_EXTENDED_PEAK_SEQUENCE:
			tfPeakOneBasedStartPosition = getTFPeakOneBasedStartPosition( resultLine);
			snpOneBasedPositionWithRespectoPeakSequence = snpOneBasedPosition - tfPeakOneBasedStartPosition + 1;
			containsSNP = constainsSNP( start, end, snpOneBasedPositionWithRespectoPeakSequence);
			break;

		}// End of SWITCH

		if( containsSNP){

			// matrix-scan-matrix_2014-08-29.14 means 13rd matrix in the file
			matrixName = resultLine.substring( indexofSecondTab + 1, indexofThirdTab);
			indexofDot = matrixName.indexOf( '.');
			if( indexofDot >= 0){
				matrixNumber = Integer.parseInt( matrixName.substring( indexofDot + 1)) - 1;
			}else{
				// if there is no '.' this means that there is only one matrix
				// which is numbered 1.
				matrixNumber = 1;
			}

			direction = resultLine.substring( indexofThirdTab + 1, indexofFourthTab).charAt( 0);
			sequence = resultLine.substring( indexofSixthTab + 1, indexofSeventhTab);
			pValue = Double.parseDouble( resultLine.substring( indexofEigthTab + 1, indexofNinethTab));

			result = new Result();

			result.setResultLine( resultLine);
			result.setpValue( pValue);

			try{

				switch( sequenceType){
				case SNP_REFERENCE_SEQUENCE:
				case TF_EXTENDED_PEAK_SEQUENCE:
					bufferedWriter.write( sequenceType.convertEnumtoString() + "\t" + description + "\t" + matrixName + "\t" + matrixNumber + "\t" + direction + "\t" + start + "\t" + end + "\t" + sequence + "\t" + df.format( pValue) + System.getProperty( "line.separator"));
					break;

				case SNP_ALTERED_SEQUENCE:
					if( snpReferenceSequenceResultContaingSNPPosition != null){
						logRatio = Math.log10( snpReferenceSequenceResultContaingSNPPosition.getpValue() / pValue);
						bufferedWriter.write( sequenceType.convertEnumtoString() + "\t" + description + "\t" + matrixName + "\t" + matrixNumber + "\t" + direction + "\t" + start + "\t" + end + "\t" + sequence + "\t" + df.format( pValue) + "\t" + df.format( logRatio) + System.getProperty( "line.separator"));
					}else{
						bufferedWriter.write( sequenceType.convertEnumtoString() + "\t" + description + "\t" + matrixName + "\t" + matrixNumber + "\t" + direction + "\t" + start + "\t" + end + "\t" + sequence + "\t" + df.format( pValue) + System.getProperty( "line.separator"));
					}
					break;

				}// End of SWITCH
			}catch( IOException e){

				if( GlanetRunner.shouldLog())
				logger.error( e.toString());
			}

		}// End of IF resultLine contains SNP Position

		return result;

	}

	// 27 January 2015
	public static void writeResultLine( SequenceType sequenceType, String description, String resultLine,
			BufferedWriter bufferedWriter) {

		/*********************** SET DECIMAL FORMAT SEPARATORS *****************************/
		DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat( "0.######E0");
		/*********************** SET DECIMAL FORMAT SEPARATORS *****************************/

		String matrixName = null;
		int indexofDot;
		int matrixNumber = 0;

		char direction = ' ';
		int start = 0;
		int end = 0;
		String sequence = null;
		double pValue = 0;

		// example result line
		// gi|568815587:47291327-47291355 site matrix-scan-matrix_2015-01-27.7 D
		// 17 26 TTCACTGGAC 2.8 1.0e-02 -4.562 1.981 1 1

		int indexofFirstTab = resultLine.indexOf( '\t');
		int indexofSecondTab = ( indexofFirstTab > 0)?resultLine.indexOf( '\t', indexofFirstTab + 1):-1;
		int indexofThirdTab = ( indexofSecondTab > 0)?resultLine.indexOf( '\t', indexofSecondTab + 1):-1;
		int indexofFourthTab = ( indexofThirdTab > 0)?resultLine.indexOf( '\t', indexofThirdTab + 1):-1;
		int indexofFifthTab = ( indexofFourthTab > 0)?resultLine.indexOf( '\t', indexofFourthTab + 1):-1;
		int indexofSixthTab = ( indexofFifthTab > 0)?resultLine.indexOf( '\t', indexofFifthTab + 1):-1;
		int indexofSeventhTab = ( indexofSixthTab > 0)?resultLine.indexOf( '\t', indexofSixthTab + 1):-1;
		int indexofEigthTab = ( indexofSeventhTab > 0)?resultLine.indexOf( '\t', indexofSeventhTab + 1):-1;
		int indexofNinethTab = ( indexofEigthTab > 0)?resultLine.indexOf( '\t', indexofEigthTab + 1):-1;

		// RSAT convention MatrixNumber = LastNumber -1
		// matrix name
		// 1 matrix-scan_2014-08-29.2
		// 2 matrix-scan_2014-08-29.3
		// 3 matrix-scan_2014-08-29.4
		// 4 matrix-scan_2014-08-29.5
		// can be matrix-scan_2014-08-29

		// matrix-scan-matrix_2014-08-29.14 means 13rd matrix in the file
		matrixName = resultLine.substring( indexofSecondTab + 1, indexofThirdTab);
		indexofDot = matrixName.indexOf( '.');
		if( indexofDot >= 0){
			matrixNumber = Integer.parseInt( matrixName.substring( indexofDot + 1)) - 1;
		}else{
			// if there is no '.' this means that there is only one matrix which
			// is numbered 1.
			matrixNumber = 1;
		}

		direction = resultLine.substring( indexofThirdTab + 1, indexofFourthTab).charAt( 0);
		start = Integer.parseInt( resultLine.substring( indexofFourthTab + 1, indexofFifthTab));
		end = Integer.parseInt( resultLine.substring( indexofFifthTab + 1, indexofSixthTab));
		sequence = resultLine.substring( indexofSixthTab + 1, indexofSeventhTab);
		pValue = Double.parseDouble( resultLine.substring( indexofEigthTab + 1, indexofNinethTab));

		try{
			bufferedWriter.write( sequenceType.convertEnumtoString() + "\t" + description + "\t" + matrixName + "\t" + matrixNumber + "\t" + direction + "\t" + start + "\t" + end + "\t" + sequence + "\t" + df.format( pValue) + System.getProperty( "line.separator"));
		}catch( IOException e){

			if( GlanetRunner.shouldLog())
			logger.error( e.toString());
		}

	}

	// 27 January 2015
	public static void findAndWriteFirstResultLine( SequenceType sequenceType, String description,
			String matrixScanResult, BufferedWriter bufferedWriter, Result resultLineToBeCompared) {

		String resultLine = null;

		BufferedReader bufferedReader = new BufferedReader( new StringReader( matrixScanResult));

		try{

			while( ( resultLine = bufferedReader.readLine()) != null){

				if( resultLine.charAt( 0) != ';' && resultLine.charAt( 0) != '#'){
					writeResultLine( sequenceType, description, resultLine, bufferedWriter);
					break;
				}// End of IF

			}// End of WHILE

			// Close bufferedReader
			bufferedReader.close();

		}catch( IOException e){

			if( GlanetRunner.shouldLog())
			logger.error( e.toString());
		}

	}

	// 27 January 2015
	public static Result findAndWriteResultLineContainingSNPPosition(
			SequenceType sequenceType, 
			String description,
			String matrixScanResult, 
			BufferedWriter bufferedWriter, 
			Integer snpOneBasedPosition,
			Result snpReferenceSequenceResultContaingSNPPosition) {

		String resultLine = null;
		Result resultLineContainingSNPPosition = null;
		boolean containsSNP = false;
		int resultLineCount = 1;

		String lineNumberAddedDescription = null;

		BufferedReader bufferedReader = new BufferedReader( new StringReader( matrixScanResult));

		try{
			while( ( resultLine = bufferedReader.readLine()) != null && !containsSNP){

				if( resultLine.charAt( 0) != ';' && resultLine.charAt( 0) != '#'){

					lineNumberAddedDescription = resultLineCount + ". " + description;

					resultLineContainingSNPPosition = checkWhetherResultLineContainsSNPPositionAndWriteIfContains(
							sequenceType, 
							lineNumberAddedDescription, 
							resultLine, 
							bufferedWriter, 
							snpOneBasedPosition,
							snpReferenceSequenceResultContaingSNPPosition);

					resultLineCount++;

					// Not null resultLineContainingSNPPosition means that this result line contains SNP Position
					if( resultLineContainingSNPPosition != null){
						break;
					}
				}// End of IF

			}// End of WHILE

			// Close reader
			bufferedReader.close();

		}catch( IOException e){

			if( GlanetRunner.shouldLog())
			logger.error( e.toString());
		}

		return resultLineContainingSNPPosition;
	}

	public static void initializeMatrixScanParameters( MatrixScanRequest matrixScanRequest) {

		String sequence_format;
		String matrixFormat;
		int markov;
		String organism;
		String background;
		float pseudo_frequencies;
		Integer search_strands;
		String origin;
		Integer verbosity;
		Integer pseudo_counts;
		Integer score_decimals;
		String n_treatment;
		String[] uth = {"pval 0.1"};

		sequence_format = "fasta";
		matrixScanRequest.setSequence_format( sequence_format);

		// How to set this parameter?
		// mask = "non-dna";

		matrixFormat = "tab";
		matrixScanRequest.setMatrix_format( matrixFormat);

		// While running RSAT from web site with homo sapiens grch37
		// precalculated background model
		// parameters are as follows
		// matrix-scan -v 1
		// -matrix_format tab
		// -m
		// $RSAT/public_html/tmp/wwwrun/2014/09/15/matrix-scan_2014-09-15.101219_K3j9MH.matrix
		// -pseudo 1
		// -decimals 1
		// -2str
		// -origin start
		// -bgfile
		// $RSAT/public_html/data/genomes/Homo_sapiens_ensembl_74_GRCh37/oligo-frequencies/1nt_upstream-noorf_Homo_sapiens_ensembl_74_GRCh37-ovlp-1str.freq
		// -bg_pseudo 0.01
		// -return limits
		// -return sites
		// -return pval
		// -return rank
		// -lth score 1
		// -uth pval 1e-4
		// -i
		// $RSAT/public_html/tmp/wwwrun/2014/09/15/tmp_sequence_2014-09-15.101219_8TAkh2.fasta
		// -seq_format fasta
		// -n score

		// If markov order is not set
		// Execution error is given
		// Error: You must specify the method for background estimation (an option among -bgfile, -bginput, -window)
		markov = 1;
		
		// A Markov model of order m=k-1 is automatically obtained from the frequencies of oligonucleotides of length k. 
		//There is thus no need to use the option -markov when the background model is secified with a bg file.
		
		//13 DEC 2016
		matrixScanRequest.setMarkov(markov);

		organism = Commons.RSAT_ORGANISM_Homo_sapiens_GRCh38;
		matrixScanRequest.setOrganism(organism);

		background = Commons.RSAT_BACKGROUND_upstream_noorf;
		matrixScanRequest.setBackground(background);
		
		
		//13 DEC 2016
		//matrixScanRequest.setBackground_input(arg0);
		//matrixScanRequest.setBackground_model("Homo_sapiens_GRCh38");
		//matrixScanRequest.setBackground_pseudo(arg0);
		//matrixScanRequest.setBackground_window(arg0);
		
		pseudo_frequencies = 0.01f;
		matrixScanRequest.setBackground_pseudo( new Float( pseudo_frequencies));

		search_strands = new Integer( 2);
		matrixScanRequest.setStr( search_strands);

		// Although it is set to start
		// It is not set in the called matrixScan
		origin = "start";
		matrixScanRequest.setOrigin( origin);

		// how to set this parameter?
		// offset = "0";

		verbosity = new Integer( 1);
		matrixScanRequest.setVerbosity( verbosity);

		pseudo_counts = new Integer( 1);
		matrixScanRequest.setPseudo( pseudo_counts);

		score_decimals = new Integer( 1);
		matrixScanRequest.setDecimals( score_decimals);

		n_treatment = "score";
		matrixScanRequest.setN_treatment( n_treatment);

		// If you do not set these parameters
		// they are set to NONE

		matrixScanRequest.setUth( uth);
		matrixScanRequest.setReturn_fields( "sites,pval,rank");

	}

	public static String matrixScan(
			String sequence, 
			String resultKey, 
			String pfmMatrices,
			Map<String, String> sequence2RSATResultMap, 
			MatrixScanRequest matrixScanRequest, 
			RSATWSPortType proxy,
			BufferedWriter bufferedWriter) {

		String result = null;

		try{

			matrixScanRequest.setSequence(sequence);
			matrixScanRequest.setMatrix(pfmMatrices);

			/***************************************************/
			/************ Old Code starts ************************/
			/***************************************************/
			/* Call the service */
			MatrixScanResponse response;
			response = proxy.matrix_scan( matrixScanRequest);

			/* Get the result */
			result = response.getClient();

			/* Put the result */
			sequence2RSATResultMap.put( resultKey, result);
			/***************************************************/
			/************ Old Code ends **************************/
			/***************************************************/

			// /************************************************************************/
			// //To avoid time out problems, two new Web Services were added:
			// monitor and get_result.
			// //If you use the value "ticket" as "output" parameter for most
			// Web Services, you will receive a job ID.
			// //This job ID can then be used to monitor the status of the
			// running job.
			// //When it is "Done", use get_result to... well... get the
			// results.
			// //Please update your libraries to benefit from these
			// improvements.
			//
			// //Monitoring the status of a job
			// //proxy.monitor(MonitorRequest arg0);
			//
			// //Get result of a job
			// //proxy.get_result(GetResultRequest arg0);
			// /************************************************************************/

			// /***************************************************/
			// /************New Code starts************************/
			// /***************************************************/
			// matrixScanRequest.setOutput("ticket");
			//
			// //Call Matrix Scan Service
			// MatrixScanResponse response =
			// proxy.matrix_scan(matrixScanRequest);
			//
			// /*Get the result which will be a jobID*/
			// String jobID = response.getClient();
			//
			//
			// /* prepare the parameters */
			// MonitorRequest monitorParameters = new MonitorRequest();
			// GetResultRequest getResultParameters = new GetResultRequest();
			//
			// monitorParameters.setTicket(jobID);
			// getResultParameters.setTicket(jobID);
			//
			// MonitorResponse monitorResponse =
			// proxy.monitor(monitorParameters);
			// GetResultResponse getResultResponse;
			//
			// while (monitorResponse.getStatus() == "Done"){
			//
			// getResultResponse = proxy.get_result(getResultParameters);
			//
			// /*Get the result*/
			// result = getResultResponse.getClient();
			//
			// /*Put the result*/
			// sequence2RSATResultMap.put(resultKey, result);
			//
			// }// End of While
			// /***************************************************/
			// /************New Code ends**************************/
			// /***************************************************/

		}catch( RemoteException e){

			if( GlanetRunner.shouldLog())
			logger.error( e.toString());
			if( GlanetRunner.shouldLog())
			logger.error( sequence);

		}

		return result;
	}

	public static void matrixScan( 
			String eachSNPDirectoryName, 
			Map<String, String> tfName2TFPfmMatricesFileMap,
			String snpReferenceSequenceFile, 
			List<String> snpAlteredSequenceFileList,
			String tfExtendedPeakSequenceFile,
			List<String> tfNames,
			//Map<String, String> tfName2TfExtendedPeakSequenceFileMap, 
			RSATWSPortType proxy,
			MatrixScanRequest matrixScanRequest, 
			BufferedWriter bufferedWriter,
			Map<String, String> snpReferenceSequence2RSATResultMap, 
			Map<String, String> alteredSequence2RSATResultMap,
			Map<String, String> peakSequence2RSATResultMap) {

		try{

			/**************************************************************************************************************************/
			/************************* Initialization starts ****************************************************************************/
			/**************************************************************************************************************************/
			String tfName = null;
			String tfPfmMatricesFileName = null;
			String pfmMatrices = null;

			String description = null;

			String snpReferenceRSATMatrixScanResult = null;
			String snpAlteredRSATMatrixScanResult = null;
			String tfExtendedPeakRSATMatrixScanResult = null;

			String referenceSequence = null;
			String alteredSequence = null;
			String peakSequence = null;

			String snpReferenceResultKey = null;
			String snpAlteredResultKey = null;
			String tfExtendedPeakResultKey = null;

			String alteredSequenceFirstLine = null;

			int snpOneBasedPosition = 0;

			Result snpReferenceSequenceResultContaingSNPPosition = null;

			/**************************************************************************************************************************/
			/************************* Initialization ends ****************************************************************************/
			/**************************************************************************************************************************/

			/**************************************************************************************************************************/
			/************************* RSAT Matrix Calls Starts ***********************************************************************/
			/**************************************************************************************************************************/
			// Get SNP Sequence
			referenceSequence = readAllfromFile( snpReferenceSequenceFile);

			snpOneBasedPosition = getSNPOneBasedPosition( referenceSequence);
			/**************************************************************************************************************************/
			/************************* For each TF Peak Sequence Starts ***************************************************************/
			/**************************************************************************************************************************/
			//Delete 12 DEC 2016 
			//for( Map.Entry<String, String> tfEntry : tfName2TfExtendedPeakSequenceFileMap.entrySet()){
			for(Iterator<String> itrTF= tfNames.iterator();itrTF.hasNext();){

				// Get TF Name
				tfName = itrTF.next();

				// Set SNP Reference Result Key
				snpReferenceResultKey = eachSNPDirectoryName + "_" + tfName;

				// Initialize pfmMatrices to null for each TF
				pfmMatrices = null;

				// Get TF PFM Matrix File
				tfPfmMatricesFileName = tfName2TFPfmMatricesFileMap.get( tfName);

				// If there is no tf pfm matrices we can not realize regulatory
				// sequence analysis
				if( tfPfmMatricesFileName == null){
					bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "******************************" + snpReferenceResultKey + " No PFM Matrices found for " + tfName + System.getProperty( "line.separator"));
					continue;
				}else{
					// Set PFM Matrices for this TF Name
					pfmMatrices = readAllfromFile( tfPfmMatricesFileName);
				}

				/**************************************************************************************************/
				/************** RSAT Matrix Scan for SNP Reference Sequence for each TF Starts   ******************/
				/**************************************************************************************************/

				bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "******************************" + snpReferenceResultKey + "**********************************" + System.getProperty( "line.separator"));

				// Write header to the outputFile
				bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "SequenceType" + "\t" + "nth Result Line (snp is at " + Commons.ONE_BASED_SNP_POSITION + ". position)" + "\t" + "Matrix Name" + "\t" + "nth Matrix in the file(First matrix is numbered with 1)" + "\t" + "Direction" + "\t" + "Start" + "\t" + "End" + "\t" + "Sequence" + "\t" + "pValue" + "\t" + "log(referenceSequenceResultPValue/alteredSequenceResultPValue)" + System.getProperty( "line.separator"));

				// Matrix Scan for Reference Sequence for this tfName
				snpReferenceRSATMatrixScanResult = snpReferenceSequence2RSATResultMap.get( snpReferenceResultKey);

				if( snpReferenceRSATMatrixScanResult == null){

					snpReferenceRSATMatrixScanResult = matrixScan( referenceSequence, snpReferenceResultKey,
							pfmMatrices, snpReferenceSequence2RSATResultMap, matrixScanRequest, proxy, bufferedWriter);

					if( snpReferenceRSATMatrixScanResult != null){

						snpReferenceSequenceResultContaingSNPPosition = null;

						description = "1. Result Line";
						findAndWriteFirstResultLine(
								SequenceType.SNP_REFERENCE_SEQUENCE, 
								description,
								snpReferenceRSATMatrixScanResult, 
								bufferedWriter, 
								null);

						description = "Result Line Containing SNP Position";
						snpReferenceSequenceResultContaingSNPPosition = findAndWriteResultLineContainingSNPPosition(
								SequenceType.SNP_REFERENCE_SEQUENCE, 
								description, 
								snpReferenceRSATMatrixScanResult,
								bufferedWriter, 
								null, 
								null);
					}// End of IF: snpReferenceRSATMatrixScanResult null check

				}else{
					System.out.println( "I guess this part is unnnecessary");

					snpReferenceSequenceResultContaingSNPPosition = null;

					description = "1. Result Line";
					findAndWriteFirstResultLine( SequenceType.SNP_REFERENCE_SEQUENCE, description,
							snpReferenceRSATMatrixScanResult, bufferedWriter, null);

					description = "Result Line Containing SNP Position";
					snpReferenceSequenceResultContaingSNPPosition = findAndWriteResultLineContainingSNPPosition(
							SequenceType.SNP_REFERENCE_SEQUENCE, description, snpReferenceRSATMatrixScanResult,
							bufferedWriter, null, null);

				}
				/**************************************************************************************************/
				/******************** RSAT Matrix Scan for SNP Reference Sequence for each TF Ends ****************/
				/**************************************************************************************************/

				/**********************************************************************************************************************/
				/************************* For each SNP Altered Sequence Starts *******************************************************/
				/**********************************************************************************************************************/
				for( Iterator<String> itr = snpAlteredSequenceFileList.iterator(); itr.hasNext();){

					/**************************************************************************************************/
					/******************** RSAT Matrix Scan for SNP Altered Sequence Starts ****************************/
					/**************************************************************************************************/
					// Get Altered Sequence
					alteredSequence = readAllfromFile( itr.next());

					alteredSequenceFirstLine = readFirstLinefromFasta( alteredSequence);

					// Set Altered Result Key
					snpAlteredResultKey = alteredSequenceFirstLine + "_" + tfName;

					snpAlteredRSATMatrixScanResult = alteredSequence2RSATResultMap.get( snpAlteredResultKey);

					if( snpAlteredRSATMatrixScanResult == null){

						// Altered Result
						snpAlteredRSATMatrixScanResult = matrixScan( alteredSequence, snpAlteredResultKey, pfmMatrices,
								alteredSequence2RSATResultMap, matrixScanRequest, proxy, bufferedWriter);

						if( snpAlteredRSATMatrixScanResult != null){

							description = "1. Result Line";
							findAndWriteFirstResultLine(
									SequenceType.SNP_ALTERED_SEQUENCE, 
									description,
									snpAlteredRSATMatrixScanResult, 
									bufferedWriter, 
									null);

							description = "Result Line Containing SNP Position";
							findAndWriteResultLineContainingSNPPosition(
									SequenceType.SNP_ALTERED_SEQUENCE,
									description, 
									snpAlteredRSATMatrixScanResult, 
									bufferedWriter, 
									null,
									snpReferenceSequenceResultContaingSNPPosition);

						}// End of IF: snpAlteredRSATMatrixScanResult null check

					}else{

						System.out.println( "I guess this part is unnnecessary");
						// Get best altered result line containing snp position
						// if it exists

						description = "1. Result Line";
						findAndWriteFirstResultLine(
								SequenceType.SNP_ALTERED_SEQUENCE, 
								description,
								snpAlteredRSATMatrixScanResult, 
								bufferedWriter, 
								null);

						description = "Result Line Containing SNP Position";
						findAndWriteResultLineContainingSNPPosition( SequenceType.SNP_ALTERED_SEQUENCE, description,
								snpAlteredRSATMatrixScanResult, bufferedWriter, null,
								snpReferenceSequenceResultContaingSNPPosition);

					}
					// Matrix Scan for Altered Sequence for this tfName
					/**************************************************************************************************/
					/******************** RSAT Matrix Scan for SNP Altered Sequence Ends ******************************/
					/**************************************************************************************************/

				}// End of FOR each Altered Sequence
				/**********************************************************************************************************************/
				/************************* For each SNP Altered Sequence Ends *********************************************************/
				/**********************************************************************************************************************/

				/**************************************************************************************************/
				/******************** RSAT Matrix Scan for TF Extended Peak Sequence Starts ***********************/
				/**************************************************************************************************/
				// Get Peak Sequence
				peakSequence = readAllfromFile(tfExtendedPeakSequenceFile);

				// Set Peak Result Key
				tfExtendedPeakResultKey = eachSNPDirectoryName + "_" + tfName;

				// Matrix Scan for Peak Sequence for this tfName
				tfExtendedPeakRSATMatrixScanResult = peakSequence2RSATResultMap.get( tfExtendedPeakResultKey);

				if( tfExtendedPeakRSATMatrixScanResult == null){

					// Extended Peak Result
					tfExtendedPeakRSATMatrixScanResult = matrixScan( peakSequence, tfExtendedPeakResultKey,
							pfmMatrices, peakSequence2RSATResultMap, matrixScanRequest, proxy, bufferedWriter);

					if( tfExtendedPeakRSATMatrixScanResult != null){

						description = "1. Result Line";
						findAndWriteFirstResultLine(
								SequenceType.TF_EXTENDED_PEAK_SEQUENCE, 
								description,
								tfExtendedPeakRSATMatrixScanResult, 
								bufferedWriter, 
								null);

						description = "Result Line Containing SNP Position";
						findAndWriteResultLineContainingSNPPosition(
								SequenceType.TF_EXTENDED_PEAK_SEQUENCE,
								description, 
								tfExtendedPeakRSATMatrixScanResult, 
								bufferedWriter, 
								snpOneBasedPosition,
								null);

					}// End of IF: tfExtendedPeakRSATMatrixScanResult null check

				}else{

					System.out.println( "I guess this part is unnnecessary");

					description = "1. Result Line";
					findAndWriteFirstResultLine(
							SequenceType.TF_EXTENDED_PEAK_SEQUENCE, 
							description,
							tfExtendedPeakRSATMatrixScanResult, 
							bufferedWriter,
							null);

					description = "Result Line Containing SNP Position";
					findAndWriteResultLineContainingSNPPosition(
							SequenceType.TF_EXTENDED_PEAK_SEQUENCE, 
							description,
							tfExtendedPeakRSATMatrixScanResult, 
							bufferedWriter, 
							snpOneBasedPosition, 
							null);

				}
				/**************************************************************************************************/
				/******************** RSAT Matrix Scan for TF Extended Peak Sequence Ends *************************/
				/**************************************************************************************************/

				// Flush to write out
				bufferedWriter.flush();

			}// End of FOR each TF
			/**************************************************************************************************************************/
			/************************* For each TF Extended Peak Sequence Ends ********************************************************/
			/**************************************************************************************************************************/

			/**************************************************************************************************************************/
			/************************* RSAT Matrix Calls Ends *************************************************************************/
			/**************************************************************************************************************************/

		}catch( Exception e){
			System.out.println( e.toString());

		}

	}
	
	//12 DEC 2016
	public static List<String> readTFNames(String tfNamesFile){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		
		List<String> tfNames = new ArrayList<String>();
		
		try {
			
			fileReader = FileOperations.createFileReader(tfNamesFile);		
			bufferedReader = new BufferedReader(fileReader);
	
			while((strLine = bufferedReader.readLine()) != null){
				
				tfNames.add(strLine);
			}//End of while
	
			bufferedReader.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tfNames;
	}

	public static String fillTFName2TFPFMMatriceFileMap( File mainTFPFMAndLogoMatricesDirectory, String tfName,
			Map<String, String> tfName2TFPfmMatricesFileMap) {

		String fileName = null;
		String fileAbsolutePath = null;
		String tfPfmMatricesFileName = null;

		tfPfmMatricesFileName = tfName2TFPfmMatricesFileMap.get( tfName);

		// Not found yet
		if( tfPfmMatricesFileName == null){

			// mainTFPFMAndLogoMatricesDirectory is
			// Commons.TF_PFM_AND_LOGO_Matrices
			if( mainTFPFMAndLogoMatricesDirectory.exists() && mainTFPFMAndLogoMatricesDirectory.isDirectory()){

				search:

				for( File eachTFPFMAndLogoMatricesDirectory : mainTFPFMAndLogoMatricesDirectory.listFiles()){

					for( File eachTFFile : eachTFPFMAndLogoMatricesDirectory.listFiles()){

						fileName = eachTFFile.getName();
						fileAbsolutePath = eachTFFile.getAbsolutePath();

						if( tfName.equals( getTFName( fileName)) && fileName.startsWith( Commons.PFM_MATRICES)){
							tfPfmMatricesFileName = fileAbsolutePath;
							tfName2TFPfmMatricesFileMap.put( tfName, tfPfmMatricesFileName);
							break search;

						}// End of IF

					}// End of FOR eachTFFile

				}// End of FOR eachTFPfmAndLogoMatricesDirectory

			}// End of IF mainTFPFMAndLogoMatricesDirectory exists and
				// mainTFPFMAndLogoMatricesDirectory is a directory

		}// End of IF tfPfmMatricesFileName is null

		return tfPfmMatricesFileName;

	}

	public static String getTFName( String fileName) {

		String tfName = null;

		int indexofUnderscore = fileName.indexOf( Commons.UNDERSCORE);
		int indexofDot = fileName.indexOf( Commons.DOT);

		tfName = fileName.substring( indexofUnderscore + 1, indexofDot);

		return tfName;

	}

	public static void matrixScan( 
			String outputFolder, 
			String forRSASNPTFSequencesMatricesDirectory,
			BufferedWriter bufferedWriter) {

		int matrixScanNumber = 1;

		Map<String, String> snpReferenceSequence2RSATResultMap = new HashMap<String, String>();
		Map<String, String> snpAlteredSequence2RSATResultMap = new HashMap<String, String>();
		Map<String, String> tfExtendedPeakSequence2RSATResultMap = new HashMap<String, String>();

		File mainSNPsDirectory = new File( outputFolder + forRSASNPTFSequencesMatricesDirectory + Commons.SNPs);
		File mainTFPFMAndLogoMatricesDirectory = new File(outputFolder + forRSASNPTFSequencesMatricesDirectory + Commons.TF_PFM_AND_LOGO_Matrices);

		String snpReferenceSequenceFile = null;
		String snpAlteredSequenceFile = null;
		String tfExtendedPeakSequenceFile = null;
		String tfNamesFile = null;

		
		List<String> tfNames = null;
		
		List<String> snpAlteredSequenceFileList = null;
		
		//Delete 12 DEC 2016
		//Map<String, String> tfName2TfExtendedPeakSequenceFileMap = null;
		
		//Delete 12 DEC 2016
		//String tfName = null;

		Map<String, String> tfName2TFPfmMatricesFileMap = new HashMap<String, String>();

		String fileName = null;
		String fileAbsolutePath = null;

		RSATWebServicesLocator service = new RSATWebServicesLocator();

		// User the server address for RSAT Metazoa
		service.setRSATWSPortTypeEndpointAddress("http://rsat.sb-roscoff.fr/web_services/RSATWS.cgi");

		RSATWSPortType proxy = null;

		// mainSNPsDirectory is Commons.SNPs
		if( mainSNPsDirectory.exists() && mainSNPsDirectory.isDirectory()){

			try{
				proxy = service.getRSATWSPortType();
			}catch( ServiceException e){
				if( GlanetRunner.shouldLog())
				logger.error( e.toString());
			}

			MatrixScanRequest matrixScanRequest = new MatrixScanRequest();

			/**************************************************************************************************************************/
			/************************* Initialize Matrix Scan Request Parameters starts ***********************************************/
			/**************************************************************************************************************************/
			initializeMatrixScanParameters(matrixScanRequest);
			/**************************************************************************************************************************/
			/************************* Initialize Matrix Scan Request Parameters ends *************************************************/
			/**************************************************************************************************************************/

			// example eachSNPDirectory is chr1_11802721_rs17367504
			for( File eachSNPDirectory : mainSNPsDirectory.listFiles()){

				// Initialize input files
				snpReferenceSequenceFile = null;
				snpAlteredSequenceFile = null;
				tfExtendedPeakSequenceFile = null;

				snpAlteredSequenceFileList = new ArrayList<String>();
				
				//Delete 12 DEC 2016
				//tfName2TfExtendedPeakSequenceFileMap = new HashMap<String, String>();
				//tfName = null;

				fileName = null;
				fileAbsolutePath = null;

				// example eachSNPDirectory chr21_42416281_rs9976767
				if( eachSNPDirectory.isDirectory()){

					// Now get the SNP specific files
					for( File eachSNPFile : eachSNPDirectory.listFiles()){

						fileName = eachSNPFile.getName();
						fileAbsolutePath = eachSNPFile.getAbsolutePath();

						if( fileName.startsWith( Commons.SNP_REFERENCE_SEQUENCE)){
							snpReferenceSequenceFile = fileAbsolutePath;
						}else if( fileName.startsWith( Commons.SNP_ALTERED_SEQUENCE)){
							snpAlteredSequenceFile = fileAbsolutePath;
							snpAlteredSequenceFileList.add( snpAlteredSequenceFile);
						}else if( fileName.startsWith( Commons.TF_EXTENDED_PEAK_SEQUENCE)){
							
							//There is only one TF extended peak sequence file for all TFS that overlap with that SNP
							tfExtendedPeakSequenceFile = fileAbsolutePath;

							//Delete 12 DEC 2016
							//No need
							//Get TF Name from fileName
							//tfName = getTFName(fileName);
							//tfName2TfExtendedPeakSequenceFileMap.put( tfName, tfExtendedPeakSequenceFile);

						}else if(fileName.startsWith(Commons.OVERLAPPING_TFS)){
							tfNamesFile = fileAbsolutePath;
							//Read tfNames overlapping with this SNP position
							tfNames = readTFNames(tfNamesFile);
							
							for(String tfName: tfNames) {
								// Get TF PFM Matrix File
								fillTFName2TFPFMMatriceFileMap(mainTFPFMAndLogoMatricesDirectory, tfName,tfName2TFPfmMatricesFileMap);
							}
						}

					}// End of FOR eachSNPFile under eachSNPDirectory

					// If all necessary files are not null
					//if( snpReferenceSequenceFile != null && snpAlteredSequenceFileList.size() > 0 && tfName2TfExtendedPeakSequenceFileMap.size() > 0){
					if( snpReferenceSequenceFile != null && snpAlteredSequenceFileList.size() > 0 && tfExtendedPeakSequenceFile!=null){
						
						// Matrix Scan Call
						// what is enrichedElement
						// what is given interval name
						// what is snp
						if( GlanetRunner.shouldLog())
						logger.info( "RSAT MatrixScan " + matrixScanNumber++ + " for " + eachSNPDirectory.getPath());
						matrixScan( eachSNPDirectory.getName(), 
								tfName2TFPfmMatricesFileMap, 
								snpReferenceSequenceFile,
								snpAlteredSequenceFileList, 
								tfExtendedPeakSequenceFile,
								tfNames,
								//tfName2TfExtendedPeakSequenceFileMap, 
								proxy,
								matrixScanRequest, 
								bufferedWriter, 
								snpReferenceSequence2RSATResultMap,
								snpAlteredSequence2RSATResultMap, 
								tfExtendedPeakSequence2RSATResultMap);

					}// End of IF RSAT matrix scan

				}// End of IF eachSNPDirectory is a directory
			}// End of FOR eachSNPDirectory under mainSNPsDirectory

		}// End of IF mainSNPsDirectory exists and mainSNPsDirectory is a directory


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

		//String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];

		// jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if( jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		// jobName ends

		String outputFolder = args[CommandLineArguments.OutputFolder.value()];

		String forRSASNPTFSequencesMatricesDirectory = Commons.FOR_RSA_SNP_TF_SEQUENCES_MATRICES_DIRECTORY;

		FileWriter fileWriter;
		BufferedWriter bufferedWriter;

		try{
			fileWriter = FileOperations.createFileWriter( outputFolder + Commons.REGULATORY_SEQUENCE_ANALYSIS_DIRECTORY + Commons.RSA_RESULTS_FOR_ALL_ANNOTATED_TFS);
			bufferedWriter = new BufferedWriter( fileWriter);

			/***************************************************************************************************/
			/***************** Regulatory Sequence Analysis for All TF Annotations starts ************************/
			/***************************************************************************************************/
			if( GlanetRunner.shouldLog())
				logger.info( "RSAT starts for TF");
			
				matrixScan(outputFolder, forRSASNPTFSequencesMatricesDirectory, bufferedWriter);
				
			if( GlanetRunner.shouldLog())
				logger.info( "RSAT ends for TF");
			/***************************************************************************************************/
			/***************** Regulatory Sequence Analysis for All TF Annotations ends **************************/
			/***************************************************************************************************/

			// Close bufferedWriter
			bufferedWriter.close();

		}catch( IOException e){

			if( GlanetRunner.shouldLog())
			logger.error( e.toString());
		}

	}

}