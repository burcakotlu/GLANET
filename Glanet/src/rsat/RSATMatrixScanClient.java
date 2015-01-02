/**
 * @author Burcak Otlu
 * Jan 30, 2014
 * 12:05:22 PM
 * 2014
 *
 *
 *How to read RSAT result?
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
 * snp is always at the 15. th base position which is G in this case (R	10	18	CCCGCAACC)
 */
package rsat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import RSATWS.MatrixScanRequest;
import RSATWS.MatrixScanResponse;
import RSATWS.RSATWSPortType;
import RSATWS.RSATWebServicesLocator;
import auxiliary.FileOperations;


import common.Commons;

import enumtypes.CommandLineArguments;

import org.apache.log4j.Logger;

public class RSATMatrixScanClient {
	
	final static Logger logger = Logger.getLogger(RSATMatrixScanClient.class);
	

	/**
	 * 
	 */
	public RSATMatrixScanClient() {
		// TODO Auto-generated constructor stub
	}
	
	public static String readFirstLinefromFile(String inputFileName){
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		String firstLine = "";
		
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				firstLine = firstLine + strLine +System.getProperty("line.separator");
				break;
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return firstLine;
		
	}
	
	public static String readAllfromFile(String inputFileName){
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		String all = "";
		
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				all = all + strLine +System.getProperty("line.separator");
				
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return all;
		
	}
	
	
	
	
	//direction does not matter
	//start is always less than end
	public static boolean constainsSNP(int start,int end,int snpPosition){
			if (snpPosition <= end && snpPosition>=start){
				return true;
			}
					
		return false;
	}
	
	public static int getOneBasedPeakStart(String bestPeakResultLine){
	
		//example
		//gi|224589818:170862228-170862467	site	matrix-scan-matrix_2014-04-14.5	R	37	45	ACAGAGCCG	4.7	5.6e-04	-7.490	3.253	1	1
		
		int indexofFirstColon = bestPeakResultLine.indexOf(':');
		int indexofFirstHyphen = bestPeakResultLine.indexOf('-');
					
		return Integer.parseInt(bestPeakResultLine.substring(indexofFirstColon+1, indexofFirstHyphen));

	}
	
	
	public static int getOneBasedSNPStart(String bestSNPResultLine){
		
		//example snp result line
		//gi|224589818:170862322-170862350	site	matrix-scan-matrix_2014-04-11.5	D	13	21	ACGGCTGCG	3.5	3.7e-03	-5.602	2.433	2	2		
		int indexofFirstColon = bestSNPResultLine.indexOf(':');
		int indexofFirstHyphen = bestSNPResultLine.indexOf('-');
					
		return Integer.parseInt(bestSNPResultLine.substring(indexofFirstColon+1, indexofFirstHyphen)) + Commons.NUMBER_OF_BASES_BEFORE_SNP_POSITION;

		

	}
	
	public static void getPeakSequenceStartandEnd(PeakSequence peakSequence,String peakResultLine){
		
		//example
		//gi|224589818:170862228-170862467	site	matrix-scan-matrix_2014-04-14.5	R	37	45	ACAGAGCCG	4.7	5.6e-04	-7.490	3.253	1	1
		int indexofFirstTab 	= peakResultLine.indexOf('\t');
		int indexofSecondTab 	= peakResultLine.indexOf('\t',indexofFirstTab+1);
		int indexofThirdTab 	= peakResultLine.indexOf('\t',indexofSecondTab+1);
		int indexofFourthTab	= peakResultLine.indexOf('\t',indexofThirdTab+1);
		int indexofFifthTab 	= peakResultLine.indexOf('\t',indexofFourthTab+1);
		int indexofSixthTab 	= peakResultLine.indexOf('\t',indexofFifthTab+1);
		
		peakSequence.setStart(Integer.parseInt(peakResultLine.substring(indexofFourthTab+1, indexofFifthTab)));
		peakSequence.setEnd(Integer.parseInt(peakResultLine.substring(indexofFifthTab+1, indexofSixthTab)));
		
	}

	
	public static boolean peakResultLineContainsSNPPosition(String snpResultLine,String peakResultLine){
		int snpStartOneBased = 0;
		int peakStartOneBased = 0;
		int snpPositionWithRespectoPeakSequence = 0;
		
		PeakSequence peakSequence= new PeakSequence();
		
		
		snpStartOneBased = getOneBasedSNPStart(snpResultLine);
		peakStartOneBased = getOneBasedPeakStart(peakResultLine);
		
		snpPositionWithRespectoPeakSequence = snpStartOneBased - peakStartOneBased +1;
		
		getPeakSequenceStartandEnd(peakSequence,peakResultLine);
		
		return constainsSNP(peakSequence.getStart(), peakSequence.getEnd(), snpPositionWithRespectoPeakSequence);
		
	}
	
	//Among the reference result lines 
	//get the best reference line containing snp position
	public static Result getBestReferenceResultLineContainigSNPPosition(String description, String snpResult,BufferedWriter bufferedWriter,Result bestReferenceLineResult) throws IOException{
		
		String resultLine = null;
		String bestResultLine =null;
		
		int indexofSharpinSNPResult;
		int indexofNewLineinSNPResult;
		int indexofNextNewLineinSNPResult;
			
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		
		int indexofDot;
		
		char direction = ' ';
		int start = 0;
		int end = 0;
		
		String matrixName= null;
		int matrixNumber= 0;
		
		double pValue = 0;
		double pValueofReferenceSequence = 0;
		double logRatio;
		
		String sequence = null;
		
		DecimalFormat df = new DecimalFormat("0.######E0");
		
		
		int countforResultLine = 0;
		
		Result result = new Result();
	
		indexofSharpinSNPResult = snpResult.indexOf('#');
		indexofNewLineinSNPResult = snpResult.indexOf('\n',indexofSharpinSNPResult+1);
		indexofNextNewLineinSNPResult = snpResult.indexOf('\n',indexofNewLineinSNPResult+1);
					
		
		do{
			
			resultLine = snpResult.substring(indexofNewLineinSNPResult+1,indexofNextNewLineinSNPResult);
			
			if(resultLine.startsWith(";")){
				//Not a valid result line 
				break;
			}
			
			//check whether there is no result or there is no result containing snp position
			//if there is a break countforSNPResultLine will remain zero means that there is no result
			//else countforSNPResultLine will be nonzero means there is at least one result 
			countforResultLine++;
			
//			example result line
//			gi|568815592:32096935-32096963	site	matrix-scan-matrix_2014-08-29.14	R	4	19	CTACACTGGCGAGGAC	3.1	4.9e-03	-5.323	2.312	1	1
			
			indexofFirstTab 	= resultLine.indexOf('\t');
			indexofSecondTab 	= resultLine.indexOf('\t',indexofFirstTab+1);
			indexofThirdTab 	= resultLine.indexOf('\t',indexofSecondTab+1);
			indexofFourthTab 	= resultLine.indexOf('\t',indexofThirdTab+1);
			indexofFifthTab 	= resultLine.indexOf('\t',indexofFourthTab+1);
			indexofSixthTab 	= resultLine.indexOf('\t',indexofFifthTab+1);
			indexofSeventhTab	= resultLine.indexOf('\t',indexofSixthTab+1);
			indexofEigthTab 	= resultLine.indexOf('\t',indexofSeventhTab+1);
			indexofNinethTab 	= resultLine.indexOf('\t',indexofEigthTab+1);
			
//			RSAT convention MatrixNumber = LastNumber -1 			
//			matrix	name                     	
//			1	matrix-scan_2014-08-29.2 	
//			2	matrix-scan_2014-08-29.3 	
//			3	matrix-scan_2014-08-29.4 	
//			4	matrix-scan_2014-08-29.5 
//			can be 	matrix-scan_2014-08-29 				

			//matrix-scan-matrix_2014-08-29.14 means 13rd matrix in the file			
			matrixName = resultLine.substring(indexofSecondTab+1, indexofThirdTab);
			indexofDot = matrixName.indexOf('.');
			if (indexofDot>=0){
				matrixNumber = Integer.parseInt(matrixName.substring(indexofDot+1))-1;
			}else{
				//if there is no '.' this means that there is only one matrix which is numbered 1.
				matrixNumber = 1;
			}
			
			direction = resultLine.substring(indexofThirdTab+1, indexofFourthTab).charAt(0);
			start = Integer.parseInt(resultLine.substring(indexofFourthTab+1, indexofFifthTab));
			end = Integer.parseInt(resultLine.substring(indexofFifthTab+1, indexofSixthTab));
			sequence = resultLine.substring(indexofSixthTab+1,indexofSeventhTab);
			pValue = Double.parseDouble(resultLine.substring(indexofEigthTab+1, indexofNinethTab));			
							
			//in case of next snpResult fetch
			indexofNewLineinSNPResult = indexofNextNewLineinSNPResult;
			indexofNextNewLineinSNPResult = snpResult.indexOf('\n', indexofNewLineinSNPResult+1);
			
			
		} while(!constainsSNP(start,end,Commons.ONE_BASED_SNP_POSITION));

		
		if(resultLine.startsWith(";") && countforResultLine==0){
			bufferedWriter.write("There is no result line" + System.getProperty("line.separator"));	
		}else if (resultLine.startsWith(";") && countforResultLine!=0){
			bufferedWriter.write("There is NO result line containing snp position" + System.getProperty("line.separator"));
		}else {
			bestResultLine = resultLine;
		}
		
		
		//there is a result line containing snp position
		if(bestResultLine!=null){
			//This method has called for altered sequence
			//since bestReferenceLineResult is not null
			if (bestReferenceLineResult!=null && bestReferenceLineResult.getResultLine() !=null){
				
				pValueofReferenceSequence = bestReferenceLineResult.getpValue();
				logRatio = Math.log10(pValueofReferenceSequence/pValue);
				
				bufferedWriter.write(description + " found at " +  countforResultLine + ". result lines" + "\t" +  matrixName + "\t" + matrixNumber + "\t" +direction + "\t" + start + "\t" + end + "\t" + sequence + "\t" + df.format(pValue) + "\t" + df.format(logRatio) + System.getProperty("line.separator"));
			}
			//This method has called for reference sequence
			//since bestReferenceLineResult is null
			else{
				bufferedWriter.write(description + " found at " +  countforResultLine + ". result lines" + "\t" + matrixName + "\t" + matrixNumber + "\t"+  direction + "\t" + start + "\t" + end + "\t" + sequence + "\t" + df.format(pValue) + "\t" + ""+ System.getProperty("line.separator"));
				
			}
		}
		//there is no result line containing snp position		
		else{
			//Do nothing
			//Already covered above
		}
		
		result.setpValue(pValue);
		result.setResultLine(bestResultLine);
		
		return result;

	}
	
	
	public static void getBestPeakResultLine(String bestSNPResultLine,String peakResult, BufferedWriter bufferedWriter) throws IOException{
		
		int countforPeakResultLine = 1;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		
		int indexofDot;
		
		char direction = ' ';
		int start = 0;
		int end = 0;	
		String sequence = null;
		double pValue = 0;
		
		String matrixName= null;
		int matrixNumber= 0;
	
		
		boolean isThereAPeakResultLineContainingSNPPosition = false;
		
		DecimalFormat df = new DecimalFormat("0.######E0");
		

		int indexofSharpinPeakResult = peakResult.indexOf('#');
		int indexofNewLineinPeakResult = peakResult.indexOf('\n',indexofSharpinPeakResult+1);
		int indexofNextNewLineinPeakResult = peakResult.indexOf('\n',indexofNewLineinPeakResult+1);
		
		
		String peakResultLine = peakResult.substring(indexofNewLineinPeakResult+1,indexofNextNewLineinPeakResult);
		String  bestPeakResultLine = null;
		
		if(peakResultLine.startsWith(";")){
			bufferedWriter.write("There is no peak result line" + System.getProperty("line.separator"));	
		}else{
			//example
			//gi|224589818:170862228-170862467	site	matrix-scan-matrix_2014-04-14.5	R	37	45	ACAGAGCCG	4.7	5.6e-04	-7.490	3.253	1	1
			
			bestPeakResultLine = peakResultLine;
			
			indexofFirstTab 	= bestPeakResultLine.indexOf('\t');
			indexofSecondTab 	= bestPeakResultLine.indexOf('\t',indexofFirstTab+1);
			indexofThirdTab 	= bestPeakResultLine.indexOf('\t',indexofSecondTab+1);
			indexofFourthTab 	= bestPeakResultLine.indexOf('\t',indexofThirdTab+1);
			indexofFifthTab 	= bestPeakResultLine.indexOf('\t',indexofFourthTab+1);
			indexofSixthTab 	= bestPeakResultLine.indexOf('\t',indexofFifthTab+1);
			indexofSeventhTab	= bestPeakResultLine.indexOf('\t',indexofSixthTab+1);
			indexofEigthTab 	= bestPeakResultLine.indexOf('\t',indexofSeventhTab+1);
			indexofNinethTab 	= bestPeakResultLine.indexOf('\t',indexofEigthTab+1);
			
			
//			RSAT convention MatrixNumber = LastNumber -1 			
//			matrix	name                     	
//			1	matrix-scan_2014-08-29.2 	
//			2	matrix-scan_2014-08-29.3 	
//			3	matrix-scan_2014-08-29.4 	
//			4	matrix-scan_2014-08-29.5 
//			can be 	matrix-scan_2014-08-29 				

			//matrix-scan-matrix_2014-08-29.14 means 13rd matrix in the file			
			matrixName = bestPeakResultLine.substring(indexofSecondTab+1, indexofThirdTab);
			indexofDot = matrixName.indexOf('.');
			if (indexofDot>=0){
				matrixNumber = Integer.parseInt(matrixName.substring(indexofDot+1))-1;
			}else{
				//if there is no '.' this means that there is only one matrix which is numbered 1.
				matrixNumber = 1;
			}
			
			direction = bestPeakResultLine.substring(indexofThirdTab+1, indexofFourthTab).charAt(0);
			start = Integer.parseInt(bestPeakResultLine.substring(indexofFourthTab+1, indexofFifthTab));
			end = Integer.parseInt(bestPeakResultLine.substring(indexofFifthTab+1, indexofSixthTab));
			sequence = bestPeakResultLine.substring(indexofSixthTab+1,indexofSeventhTab);
			pValue = Double.parseDouble(bestPeakResultLine.substring(indexofEigthTab+1, indexofNinethTab));
		
				
			bufferedWriter.write(countforPeakResultLine + ". peak result line" +  "\t" + matrixName + "\t" + matrixNumber + "\t" + direction + "\t" + start + "\t" + end + "\t" + sequence + "\t" + df.format(pValue) + "\t" + ""+ System.getProperty("line.separator"));
		}
		
		//if best snp result line and best peak result line are not null
		if(bestSNPResultLine!=null && bestPeakResultLine!=null){
			
			while(!(isThereAPeakResultLineContainingSNPPosition = peakResultLineContainsSNPPosition(bestSNPResultLine,bestPeakResultLine))){
				
				//in case of next peakResult fetch
				indexofNewLineinPeakResult = indexofNextNewLineinPeakResult;
				indexofNextNewLineinPeakResult = peakResult.indexOf('\n',indexofNewLineinPeakResult+1);
				countforPeakResultLine++;
				
				bestPeakResultLine = peakResult.substring(indexofNewLineinPeakResult+1,indexofNextNewLineinPeakResult);
				
				//Not a valid peak result line
				if(bestPeakResultLine.startsWith(";")){
					break;
				}
				
			}//End of while 
			
			//There is a peak result line containing snp position
			if(isThereAPeakResultLineContainingSNPPosition){
				//example
				//gi|224589818:170862228-170862467	site	matrix-scan-matrix_2014-04-14.5	D	107	115	ACGGCTGCG	3.5	3.7e-03	-5.602	2.433	9	6
				
				indexofFirstTab 	= bestPeakResultLine.indexOf('\t');
				indexofSecondTab 	= bestPeakResultLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab 	= bestPeakResultLine.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab 	= bestPeakResultLine.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab 	= bestPeakResultLine.indexOf('\t',indexofFourthTab+1);
				indexofSixthTab 	= bestPeakResultLine.indexOf('\t',indexofFifthTab+1);
				indexofSeventhTab	= bestPeakResultLine.indexOf('\t',indexofSixthTab+1);
				indexofEigthTab 	= bestPeakResultLine.indexOf('\t',indexofSeventhTab+1);
				indexofNinethTab 	= bestPeakResultLine.indexOf('\t',indexofEigthTab+1);
				
				direction = bestPeakResultLine.substring(indexofThirdTab+1, indexofFourthTab).charAt(0);
				start = Integer.parseInt(bestPeakResultLine.substring(indexofFourthTab+1, indexofFifthTab));
				end = Integer.parseInt(bestPeakResultLine.substring(indexofFifthTab+1, indexofSixthTab));
				sequence = bestPeakResultLine.substring(indexofSixthTab+1,indexofSeventhTab);
				pValue = Double.parseDouble(bestPeakResultLine.substring(indexofEigthTab+1, indexofNinethTab));
			
				bufferedWriter.write("Peak result line containing snp position found at " + countforPeakResultLine + ". peak result lines" +   "\t" + matrixName+ "\t" + matrixNumber + "\t" +  direction + "\t" + start + "\t" + end + "\t" + sequence + "\t" + df.format(pValue) + "\t" + ""+ System.getProperty("line.separator"));

			}
			//There is no peak result line containing snp position
			else{
				bufferedWriter.write("There is no peak result line containing snp position" + System.getProperty("line.separator"));
				
			}
		}
	}
	
	
	
	public static void matrixScan(String enrichedTfandKeggPathwayName, String tfName,String givenIntervalName, String snpName,String snpInputFile,List<String> alteredSNPInputFiles,String peakInputFile,String pfmMatricesInputFile,RSATWSPortType proxy,MatrixScanRequest matrixScanRequest,BufferedWriter bufferedWriter,Map<String,String> snpReferenceSequenceRSATResultsMap, Map<String,String>  snpAlteredSequenceRSATResultsMap, Map<String,String>  tfPeakSequenceRSATResultsMap){
		try{
		
			String description;
			String referenceResult = null;
			String alteredSequenceResult = null;
			String peakSequenceResult = null;
			
			String snpSequence = readAllfromFile(snpInputFile);
			String peakSequence = readAllfromFile(peakInputFile);
			String peakSequenceFirstLine = readFirstLinefromFile(peakInputFile);		
			
			String alteredSNPSequence =null;
			
			String sequence_format;
//			String mask;
			String pfmMatrices;
			String matrixFormat;
//			Integer markov;
			String organism;
			String background;
			float pseudo_frequencies;
			Integer search_strands;
			String origin;
//			String offset;
			Integer verbosity;
			Integer pseudo_counts;
			Integer score_decimals;
			String n_treatment;
			String[] uth = {"pval 0.1"};
			Result bestReferenceLineResult = null;
			
			int indexofFirstUnderScore = snpName.indexOf('_');
			int indexofSecondUnderScore = snpName.indexOf('_',indexofFirstUnderScore+1);
			int indexofThirdUnderScore = snpName.indexOf('_',indexofSecondUnderScore+1);
				
			String snpNameWithoutRsId = snpName.substring(0,indexofThirdUnderScore);
			
			String snpReferenceResultKey = snpNameWithoutRsId + "_" + tfName;
			String snpAlteredResultKey = null;
			String peakSequenceResultKey = null;
			
			
			bufferedWriter.write("**********" + enrichedTfandKeggPathwayName + "**********" + givenIntervalName +  "**********" + snpName + "**********************************" + System.getProperty("line.separator"));
		
			referenceResult =snpReferenceSequenceRSATResultsMap.get(snpReferenceResultKey);
			
			if (referenceResult ==null){
				
				/*******************REFERENCE SNP SEQUENCE STARTS**************/
								
				sequence_format = "fasta";
				matrixScanRequest.setSequence_format(sequence_format);
				
//				mask = "non-dna";
				//How to set this parameter?
							
				pfmMatrices = readAllfromFile(pfmMatricesInputFile);			
				matrixScanRequest.setMatrix(pfmMatrices);
							
				matrixFormat = "tab";
				matrixScanRequest.setMatrix_format(matrixFormat);

//				While running RSAT from web site with homo sapiens grch37 precalculated background model
//				parameters are as follows				
//				matrix-scan  -v 1 
//				-matrix_format tab 
//				-m $RSAT/public_html/tmp/wwwrun/2014/09/15/matrix-scan_2014-09-15.101219_K3j9MH.matrix 
//				-pseudo 1 
//				-decimals 1 
//				-2str 
//				-origin start 
//				-bgfile $RSAT/public_html/data/genomes/Homo_sapiens_ensembl_74_GRCh37/oligo-frequencies/1nt_upstream-noorf_Homo_sapiens_ensembl_74_GRCh37-ovlp-1str.freq 
//				-bg_pseudo 0.01 
//				-return limits 
//				-return sites 
//				-return pval 
//				-return rank 
//				-lth score 1 
//				-uth pval 1e-4 
//				-i $RSAT/public_html/tmp/wwwrun/2014/09/15/tmp_sequence_2014-09-15.101219_8TAkh2.fasta 
//				-seq_format fasta 
//				-n score

				//If markov order is not set
				//Execution error is given
				//Error: You must specify the method for background estimation (an option among -bgfile, -bginput, -window)
				int markov     = 1;
				matrixScanRequest.setMarkov(markov);
					
				organism = Commons.RSAT_ORGANISM_Homo_sapiens_ensembl_74_GRCh37;
				matrixScanRequest.setOrganism(organism);
				
				background 	= Commons.RSAT_BACKGROUND_upstream_noorf;
				matrixScanRequest.setBackground(background);
				
				pseudo_frequencies = 0.01f;
				matrixScanRequest.setBackground_pseudo(new Float(pseudo_frequencies));
						
				search_strands = new Integer(2);
				matrixScanRequest.setStr(search_strands);
				
				//Although it is set to start
				//It is not set in the called matrixScan
				origin 		= "start";
				matrixScanRequest.setOrigin(origin);
				
//				offset = "0";
				//how to set this parameter?
				
				verbosity 	= new Integer (1);
				matrixScanRequest.setVerbosity(verbosity);
			
				pseudo_counts 		= new Integer (1);
				matrixScanRequest.setPseudo(pseudo_counts);
							
				score_decimals 	= new Integer (1);
				matrixScanRequest.setDecimals(score_decimals);
				
				n_treatment	 = "score";
				matrixScanRequest.setN_treatment(n_treatment);
				
				//If you do not set these parameters 
				//they are set to NONE
			
				matrixScanRequest.setUth(uth);
				
							
				matrixScanRequest.setReturn_fields("sites,pval,rank");
				
							
				matrixScanRequest.setSequence(snpSequence);
			
				/*Call the service*/
				MatrixScanResponse snpResponse = proxy.matrix_scan(matrixScanRequest);
					
				/* Process results*/
				//Write header to the outputFile
				bufferedWriter.write("Description" + "\t" + "Matrix Name"+ "\t" + "nth Matrix in the file(First matrix is numbered with 1)" + "\t" +"Direction" + "\t" + "Start" + "\t" + "End" + 	"\t" + "Sequence" + "\t" + "pValue" + "\t" + "log(referenceSequenceResultPValue/alteredSequenceResultPValue)" + System.getProperty("line.separator"));
				
					
				//reference Results
				referenceResult = snpResponse.getClient();
				snpReferenceSequenceRSATResultsMap.put(snpReferenceResultKey, referenceResult);
				
				/****************************************************************************/
				description = "Best reference sequence containing snp position ";
				bestReferenceLineResult = null;
				
				//Get best reference result line containing snp position if it exists
				bestReferenceLineResult = getBestReferenceResultLineContainigSNPPosition(description,referenceResult,bufferedWriter,null);
				/****************************************************************************/
				/*******************REFERENCE SNP SEQUENCE ENDS**************/
			}//End of if 
			else{
				/****************************************************************************/
				description = "Best reference sequence containing snp position ";
				bestReferenceLineResult = null;
				
				//Get best reference result line containing snp position if it exists
				bestReferenceLineResult = getBestReferenceResultLineContainigSNPPosition(description,referenceResult,bufferedWriter,null);
				/****************************************************************************/
		
			}
			
									
			
			//****************ALTERED SEQUENCES STARTS**********************
			int alteredSequenceCount = 1;
			
			for(String alteredSNPInputFile:alteredSNPInputFiles ){
				alteredSNPSequence = readAllfromFile(alteredSNPInputFile);
				
				
				snpAlteredResultKey = alteredSNPSequence + "_" + tfName;
				alteredSequenceResult = snpAlteredSequenceRSATResultsMap.get(snpAlteredResultKey);
				
				if(alteredSequenceResult==null){
					
					//Set the altered SNP Sequence
					matrixScanRequest.setSequence(alteredSNPSequence);
					
					/*Call the service*/
					//bufferedWriter.write("Matrix-scan: sending request to the server for altered sequences..." + System.getProperty("line.separator"));
					//bufferedWriter.write("alteredSequenceFile: "+  alteredSNPInputFile + System.getProperty("line.separator"));
					
					MatrixScanResponse alteredSNPResponse = proxy.matrix_scan(matrixScanRequest);
				
					//Altered Results
					alteredSequenceResult = alteredSNPResponse.getClient();
					
					snpAlteredSequenceRSATResultsMap.put(snpAlteredResultKey, alteredSequenceResult);
					
					/****************************************************************************/
					description = "Best alteredSequence"  + alteredSequenceCount + " containing snp position ";					
					Result bestAlteredLineResult = null;
					
					//Get best altered result line containing snp position if it exists
					bestAlteredLineResult =getBestReferenceResultLineContainigSNPPosition(description,alteredSequenceResult,bufferedWriter,bestReferenceLineResult);
					
					alteredSequenceCount++;
					/****************************************************************************/
					
				}else{
					/****************************************************************************/
					description = "Best alteredSequence"  + alteredSequenceCount + " containing snp position ";					
					Result bestAlteredLineResult = null;
					
					//Get best altered result line containing snp position if it exists
					bestAlteredLineResult =getBestReferenceResultLineContainigSNPPosition(description,alteredSequenceResult,bufferedWriter,bestReferenceLineResult);
					
					alteredSequenceCount++;
					/****************************************************************************/
				
				}
				
								
				
			}//for each altered snp sequence file		
			//****************ALTERED SEQUENCES ENDS**********************
			
			
			
			/*************************Peak Sequence starts***************************************************/
			peakSequenceResultKey = peakSequenceFirstLine + "_" + tfName;
			
			peakSequenceResult = tfPeakSequenceRSATResultsMap.get(peakSequenceResultKey);
			
			if (peakSequenceResult == null){
				matrixScanRequest.setSequence(peakSequence);
				
				
				MatrixScanResponse peakResponse = proxy.matrix_scan(matrixScanRequest);
					
				//Extended Peak Results
				peakSequenceResult = peakResponse.getClient();
				tfPeakSequenceRSATResultsMap.put(peakSequenceResultKey, peakSequenceResult);
				
				//Get best peak result line containing snp position 
				getBestPeakResultLine(bestReferenceLineResult.getResultLine(),peakSequenceResult,bufferedWriter);
				
			}else{
				//Get best peak result line containing snp position 
				getBestPeakResultLine(bestReferenceLineResult.getResultLine(),peakSequenceResult,bufferedWriter);
			
			}
			/*************************Peak Sequence ends***************************************************/
					
		
			
		} catch(Exception e) {
			System.out.println(e.toString());
			
		}

	}
	
	

	public static void matrixScan(String outputFolder,String forRSASequencesAndMatricesDirectory,BufferedWriter bufferedWriter, Map<String,String> snpReferenceSequenceRSATResultsMap, Map<String,String>  snpAlteredSequenceRSATResultsMap, Map<String,String>  tfPeakSequenceRSATResultsMap){
		
			File snpsDirectory = new File(outputFolder + forRSASequencesAndMatricesDirectory + Commons.SNPs);
			File tfPFMAndLogoMatricesDirectory = new File(outputFolder + forRSASequencesAndMatricesDirectory + Commons.TF_PFM_AND_LOGO_Matrices);
		
			if (snpsDirectory.exists() && snpsDirectory.isDirectory()){
				
				File[] snpDirectories = snpsDirectory.listFiles();
				
				String snpReferenceSequence = null;
				String snpAlteredSequence = null;
				List<String> snpAlteredSequences = null;
				String tfExtendedPeakSequence = null;
				String tfPfmMatrices = null;
				String tfName = null;
				
				String fileName=null;
				String fileAbsolutePath = null;
				
				RSATWebServicesLocator service = new RSATWebServicesLocator();
				RSATWSPortType proxy  = null;
				
				
				
//				//First get TF PFM Matrices file
//        		//example pfmMatrices_BCL3.txt
//	        	for (File eachSNPFile:eachSNPDirectory.listFiles() ){
//	        		if(!eachSNPFile.isDirectory()){	        		
//	        	 			//get the pfmMatrixFile
//		        			fileName = eachSNPFile.getName();
//	         				fileAbsolutePath = eachSNPFile.getAbsolutePath();
//	         				
//		        			if(fileName.startsWith(Commons.PFM_MATRICES)){
//	         					pfmMatricesInputFile = fileAbsolutePath;
//	         					tfName = fileName.substring(fileName.indexOf('_')+1,fileName.indexOf('.') );
//	         					break;
//	         				}
//		     		}//End of if			        		
//	        	}//End of for
				
				
				try {
					proxy = service.getRSATWSPortType();
				} catch (ServiceException e) {
					e.printStackTrace();
				}
				
				MatrixScanRequest matrixScanRequest = new MatrixScanRequest();
										 
			    for(File eachSNPDirectory: snpDirectories){
			    	
			    	//example chr21_42416281_rs9976767
			        if(eachSNPDirectory.isDirectory()) {
			        	
			        	
			        	//Initialize input files
		        		snpReferenceSequence = null;
		        		snpAlteredSequence = null;
		        		snpAlteredSequences = new ArrayList<String>();
		        		tfExtendedPeakSequence = null;
		        		tfPfmMatrices = null;
			        	
			        	
			        	//Now get the snp specific files
			        	for (File eachSNPFile:eachSNPDirectory.listFiles() ){

					         fileName = eachSNPFile.getName();
					         fileAbsolutePath = eachSNPFile.getAbsolutePath();				         				
	         				
	         				if (fileName.startsWith(Commons.SNP_REFERENCE_SEQUENCE)){
	         					snpReferenceSequence = fileAbsolutePath;
	         				}else if (fileName.startsWith(Commons.SNP_ALTERED_SEQUENCE)){
	         					snpAlteredSequence = fileAbsolutePath;
	         					snpAlteredSequences.add(snpAlteredSequence);
	         				}else if (fileName.startsWith(Commons.TF_EXTENDED_PEAK_SEQUENCE)){
	         					tfExtendedPeakSequence = fileAbsolutePath;
	         				}
					         				 
			        	}//End of FOR each necessary file under snp directory
					         			
	         			//If all necessary files are not null
	         			if(snpReferenceSequence!= null && snpAlteredSequence!= null && tfExtendedPeakSequence!=null && tfPfmMatrices!=null ){
	            			//Matrix Scan Call
	         				//what is enrichedElement
	         				//what is given interval name
	         				//what is snp
	         				System.out.println("ok");
//		         			matrixScan(enrichedTfandKeggPathwayName, tfName,givenIntervalName, snpName,snpReferenceSequence,snpAlteredSequences,tfExtendedPeakSequence,tfPfmMatrices,proxy,matrixScanRequest,bufferedWriter, snpReferenceSequenceRSATResultsMap, snpAlteredSequenceRSATResultsMap, tfPeakSequenceRSATResultsMap);
		   
	         			}
					         				
			        }//if it is a directory
			    }//End of FOR each snp directory under snpsDirectory
			        			
			        		
			}//End of IF snpsDirectory exists and it is a directory
	
			
	}
	
	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2]	--->	Input File Format	
	//			--->	default	Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE	
	//args[3]	--->	Annotation, overlap definition, number of bases, 
	//					default 1
	//args[4]	--->	Perform Enrichment parameter
	//			--->	default	Commons.DO_ENRICH
	//			--->			Commons.DO_NOT_ENRICH	
	//args[5]	--->	Generate Random Data Mode
	//			--->	default	Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	//			--->			Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT	
	//args[6]	--->	multiple testing parameter, enriched elements will be decided and sorted with respect to this parameter
	//			--->	default Commons.BENJAMINI_HOCHBERG_FDR
	//			--->			Commons.BONFERRONI_CORRECTION
	//args[7]	--->	Bonferroni Correction Significance Level, default 0.05
	//args[8]	--->	Bonferroni Correction Significance Criteria, default 0.05
	//args[9]	--->	Number of permutations, default 5000
	//args[10]	--->	Dnase Enrichment
	//			--->	default Commons.DO_NOT_DNASE_ENRICHMENT
	//			--->	Commons.DO_DNASE_ENRICHMENT
	//args[11]	--->	Histone Enrichment
	//			--->	default	Commons.DO_NOT_HISTONE_ENRICHMENT
	//			--->			Commons.DO_HISTONE_ENRICHMENT
	//args[12]	--->	Transcription Factor(TF) Enrichment 
	//			--->	default	Commons.DO_NOT_TF_ENRICHMENT
	//			--->			Commons.DO_TF_ENRICHMENT
	//args[13]	--->	KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_KEGGPATHWAY_ENRICHMENT
	//args[14]	--->	TF and KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	//args[15]	--->	TF and CellLine and KeggPathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	//args[16]	--->	RSAT parameter
	//			--->	default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//			--->			Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//args[17]	--->	job name example: ocd_gwas_snps 
	//args[18]	--->	writeGeneratedRandomDataMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	//			--->			Commons.WRITE_GENERATED_RANDOM_DATA
	//args[19]	--->	writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//args[20]	--->	writePermutationBasedAnnotationResultMode checkBox
	//			---> 	default	Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//args[21]  --->    number of permutations in each run. Default is 2000
	//args[22]  --->	UserDefinedGeneSet Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT
	//							Commons.DO_USER_DEFINED_GENESET_ENRICHMENT
	//args[23]	--->	UserDefinedGeneSet InputFile 
	//args[24]	--->	UserDefinedGeneSet GeneInformationType
	//					default Commons.GENE_ID
	//							Commons.GENE_SYMBOL
	//							Commons.RNA_NUCLEOTIDE_ACCESSION
	//args[25]	--->	UserDefinedGeneSet	Name
	//args[26]	--->	UserDefinedGeneSet 	Optional GeneSet Description InputFile
	//args[27]  --->	UserDefinedLibrary Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT
	//						 	Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT
	//args[28]  --->	UserDefinedLibrary InputFile
	//args[29] - args[args.length-1]  --->	Note that the selected cell lines are
	//					always inserted at the end of the args array because it's size
	//					is not fixed. So for not (until the next change on args array) the selected cell
	//					lines can be reached starting from 22th index up until (args.length-1)th index.
	//					If no cell line selected so the args.length-1 will be 22-1 = 21. So it will never
	//					give an out of boundry exception in a for loop with this approach.
	public static void main(String[] args) {
		
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		
		//jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if (jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		//jobName ends
							
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator") ;
		
		Map<String,String> snpReferenceSequenceRSATResultsMap 	= new HashMap<String,String>();
		Map<String,String> snpAlteredSequenceRSATResultsMap 	= new HashMap<String,String>();
		Map<String,String> tfExtendedPeakSequenceRSATResultsMap 		= new HashMap<String,String>();
		
		
		String forRSATFSequencesMatricesDirectory = Commons.FOR_RSA_TF_SEQUENCES_MATRICES_DIRECTORY;
		
		//delete old files starts 
		FileOperations.deleteOldFiles(outputFolder + Commons.RSAT_DIRECTORY);
		//delete old files ends
		
		FileWriter fileWriterTF;
		BufferedWriter bufferedWriterTF;
		
		try {
				fileWriterTF = FileOperations.createFileWriter(outputFolder +Commons.RSAT_OUTPUT_FILENAME_TF);
				bufferedWriterTF = new BufferedWriter(fileWriterTF);
			
				//Regulatory Sequence Analysis for All TF Annotations
				logger.debug("RSAT starts for TF");
				matrixScan(outputFolder,Commons.FOR_RSA_TF_SEQUENCES_MATRICES_DIRECTORY,bufferedWriterTF, snpReferenceSequenceRSATResultsMap, snpAlteredSequenceRSATResultsMap, tfExtendedPeakSequenceRSATResultsMap);
				bufferedWriterTF.close();
				logger.debug("RSAT ends for TF");
							
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				

	}
				

}
