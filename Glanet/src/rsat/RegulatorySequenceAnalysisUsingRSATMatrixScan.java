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
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import RSATWS.GetResultRequest;
import RSATWS.GetResultResponse;
import RSATWS.MatrixScanRequest;
import RSATWS.MatrixScanResponse;
import RSATWS.MonitorRequest;
import RSATWS.MonitorResponse;
import RSATWS.RSATWSPortType;
import RSATWS.RSATWebServicesLocator;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.CommandLineArguments;

public class RegulatorySequenceAnalysisUsingRSATMatrixScan {
	
	final static Logger logger = Logger.getLogger(RegulatorySequenceAnalysisUsingRSATMatrixScan.class);
	

	/**
	 * 
	 */
	public RegulatorySequenceAnalysisUsingRSATMatrixScan() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static String readFirstLinefromFasta(String fasta){
		
		String firstLine =null;
		
		int indexofLineSeparator;
		
		indexofLineSeparator = fasta.indexOf(System.getProperty("line.separator"));
		
		if (indexofLineSeparator>0){
			firstLine = fasta.substring(0, indexofLineSeparator);
		}
		
		return firstLine;
		
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
				firstLine = firstLine + strLine;
				break;
			}
			
			//Close BufferedReader
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
	public static Result getBestResultLineContainigSNPPosition(String description, String snpResult,BufferedWriter bufferedWriter,Result bestReferenceLineResult) throws IOException{
		
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
		
				
			bufferedWriter.write(countforPeakResultLine + ". Peak Extended Sequence Result Line" +  "\t" + matrixName + "\t" + matrixNumber + "\t" + direction + "\t" + start + "\t" + end + "\t" + sequence + "\t" + df.format(pValue) + "\t" + ""+ System.getProperty("line.separator"));
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
			
				bufferedWriter.write("Best Peak Result Line Containing SNP Position Found At " + countforPeakResultLine + ". peak result lines" +   "\t" + matrixName+ "\t" + matrixNumber + "\t" +  direction + "\t" + start + "\t" + end + "\t" + sequence + "\t" + df.format(pValue) + "\t" + ""+ System.getProperty("line.separator"));

			}
			//There is no peak result line containing snp position
			else{
				bufferedWriter.write("There is no peak result line containing snp position" + System.getProperty("line.separator"));
				
			}
		}
	}
	
	
	
	public static void initializeMatrixScanParameters(MatrixScanRequest matrixScanRequest){
		
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
		matrixScanRequest.setSequence_format(sequence_format);
		
		//How to set this parameter?
		//mask = "non-dna";
					
					
		matrixFormat = "tab";
		matrixScanRequest.setMatrix_format(matrixFormat);

//		While running RSAT from web site with homo sapiens grch37 precalculated background model
//		parameters are as follows				
//		matrix-scan  -v 1 
//		-matrix_format tab 
//		-m $RSAT/public_html/tmp/wwwrun/2014/09/15/matrix-scan_2014-09-15.101219_K3j9MH.matrix 
//		-pseudo 1 
//		-decimals 1 
//		-2str 
//		-origin start 
//		-bgfile $RSAT/public_html/data/genomes/Homo_sapiens_ensembl_74_GRCh37/oligo-frequencies/1nt_upstream-noorf_Homo_sapiens_ensembl_74_GRCh37-ovlp-1str.freq 
//		-bg_pseudo 0.01 
//		-return limits 
//		-return sites 
//		-return pval 
//		-return rank 
//		-lth score 1 
//		-uth pval 1e-4 
//		-i $RSAT/public_html/tmp/wwwrun/2014/09/15/tmp_sequence_2014-09-15.101219_8TAkh2.fasta 
//		-seq_format fasta 
//		-n score

		//If markov order is not set
		//Execution error is given
		//Error: You must specify the method for background estimation (an option among -bgfile, -bginput, -window)
		markov     = 1;
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
		
		//how to set this parameter?
		//offset = "0";
		
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
		
	}
	
	public static String matrixScan(
			String sequence,
			String resultKey,
			String pfmMatrices,
			Map<String,String>	sequence2RSATResultMap,
			MatrixScanRequest matrixScanRequest,
			RSATWSPortType proxy,
			BufferedWriter bufferedWriter){
		
	
		String result = null;
		
		try {
			
				matrixScanRequest.setSequence(sequence);
				matrixScanRequest.setMatrix(pfmMatrices);
				
				/***************************************************/
				/************Old Code starts************************/
				/***************************************************/
				/*Call the service*/
				MatrixScanResponse response;
				response = proxy.matrix_scan(matrixScanRequest);
				
				/*Get the result*/
				result = response.getClient();
				
				/*Put the result*/
				sequence2RSATResultMap.put(resultKey, result);
				/***************************************************/
				/************Old Code ends**************************/
				/***************************************************/

				
//				/************************************************************************/
//				//To avoid time out problems, two new Web Services were added: monitor and get_result. 
//				//If you use the value "ticket" as "output" parameter for most Web Services, you will receive a job ID. 
//				//This job ID can then be used to monitor the status of the running job. 
//				//When it is "Done", use get_result to... well... get the results. 
//				//Please update your libraries to benefit from these improvements.
//				
//				//Monitoring the status of a job
//				//proxy.monitor(MonitorRequest arg0);
//				
//				//Get result of a job
//				//proxy.get_result(GetResultRequest arg0);
//				/************************************************************************/
				
//				/***************************************************/
//				/************New Code starts************************/
//				/***************************************************/
//				matrixScanRequest.setOutput("ticket");
//				
//				//Call Matrix Scan Service
//				MatrixScanResponse response = proxy.matrix_scan(matrixScanRequest);
//				
//				/*Get the result which will be a jobID*/
//				String jobID = response.getClient();
//
//				
//				/* prepare the parameters */
//			 	MonitorRequest monitorParameters = new MonitorRequest();
//			 	GetResultRequest getResultParameters = new GetResultRequest();
//			 	
//			 	monitorParameters.setTicket(jobID);
//			 	getResultParameters.setTicket(jobID);
//
//			 	MonitorResponse monitorResponse = proxy.monitor(monitorParameters);
//				GetResultResponse getResultResponse;
//		 
//				 while (monitorResponse.getStatus() == "Done"){
//					 
//					 getResultResponse = proxy.get_result(getResultParameters);
//							 
//					/*Get the result*/
//					result = getResultResponse.getClient();
//					 
//					 /*Put the result*/
//					sequence2RSATResultMap.put(resultKey, result);
//				
//				 }// End of While
//				/***************************************************/
//				/************New Code ends**************************/
//				/***************************************************/

			
		
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(sequence);
			
		} 
		
		return result;
	}
	
		
	
	public static void matrixScan(
			String eachSNPDirectoryName,
			Map<String,String>	tfName2TFPfmMatricesFileMap,
			String snpReferenceSequenceFile,
			List<String> snpAlteredSequenceFileList,
			Map<String,String> tfName2TfExtendedPeakSequenceFileMap,
			RSATWSPortType proxy,
			MatrixScanRequest matrixScanRequest,
			BufferedWriter bufferedWriter,
			Map<String,String>	referenceSequence2RSATResultMap, 
			Map<String,String>	alteredSequence2RSATResultMap, 
			Map<String,String>  peakSequence2RSATResultMap){
		
		
		try{
		
			/**************************************************************************************************************************/
			/*************************Initialization starts****************************************************************************/
			/**************************************************************************************************************************/
			String tfName = null;
			String tfPfmMatricesFileName = null;
			String pfmMatrices = null;
			
			String description = null;
			
			String referenceResult 	= null;
			String alteredResult 	= null;
			String peakResult 		= null;
			
			String referenceSequence 	= null;
			String alteredSequence 		= null;
			String peakSequence 		= null;
			
			String referenceResultKey 	= null;
			String alteredResultKey 	= null;
			String peakResultKey 		= null;
		
			String alteredSequenceFirstLine	= null;
			
			Result bestReferenceLineResult = null;
			/**************************************************************************************************************************/
			/*************************Initialization ends******************************************************************************/
			/**************************************************************************************************************************/

			
			/**************************************************************************************************************************/
			/*************************RSAT Matrix Calls Starts*************************************************************************/
			/**************************************************************************************************************************/
			//Get SNP Sequence
			referenceSequence = readAllfromFile(snpReferenceSequenceFile);
			
			/**************************************************************************************************************************/
			/*************************For each TF Peak Sequence Starts*****************************************************************/
			/**************************************************************************************************************************/
			for (Map.Entry<String, String> tfEntry: tfName2TfExtendedPeakSequenceFileMap.entrySet()){
				
				//Get TF Name
				tfName= tfEntry.getKey();
				
				//Set Reference Result Key
				referenceResultKey = eachSNPDirectoryName + "_" + tfName;
				
				//Initialize pfmMatrices to null for each TF
				pfmMatrices = null;
				
				//Get TF PFM Matrix File
				tfPfmMatricesFileName = tfName2TFPfmMatricesFileMap.get(tfName);
				
				//If there is no tf pfm matrices we can not realize regulatory sequence analysis
				if (tfPfmMatricesFileName == null){
					bufferedWriter.write(Commons.GLANET_COMMENT_CHARACTER + "******************************" + referenceResultKey + " No PFM Matrices found for "  +  tfName + System.getProperty("line.separator"));
					continue;
				}else{
					//Set PFM Matrices for this TF Name
					pfmMatrices = readAllfromFile(tfPfmMatricesFileName);			
				}
				

				/**************************************************************************************************/
				/********************RSAT Matrix Scan for Reference Sequence for each TF Starts********************/
				/**************************************************************************************************/
				
				bufferedWriter.write(Commons.GLANET_COMMENT_CHARACTER + "******************************" + referenceResultKey + "**********************************" + System.getProperty("line.separator"));
				
				//Write header to the outputFile
				bufferedWriter.write(Commons.GLANET_COMMENT_CHARACTER + "Description" + "\t" + "Matrix Name"+ "\t" + "nth Matrix in the file(First matrix is numbered with 1)" + "\t" +"Direction" + "\t" + "Start" + "\t" + "End" + 	"\t" + "Sequence" + "\t" + "pValue" + "\t" + "log(referenceSequenceResultPValue/alteredSequenceResultPValue)" + System.getProperty("line.separator"));
				
				//Matrix Scan for Reference Sequence for this tfName
				referenceResult =referenceSequence2RSATResultMap.get(referenceResultKey);
				
				description = "Best Reference Sequence Result Line Containing SNP Position ";
				
				
				if (referenceResult==null){
					referenceResult = matrixScan(referenceSequence,referenceResultKey,pfmMatrices,referenceSequence2RSATResultMap,matrixScanRequest,proxy,bufferedWriter);
					
					//Get best reference result line containing snp position if it exists
					bestReferenceLineResult = getBestResultLineContainigSNPPosition(description,referenceResult,bufferedWriter,null);	
					
				}else{
					System.out.println("This else is entered for reference sequence");
					bestReferenceLineResult = null;
			
					//Get best reference result line containing snp position if it exists
					bestReferenceLineResult = getBestResultLineContainigSNPPosition(description,referenceResult,bufferedWriter,null);
				}
				/**************************************************************************************************/
				/********************RSAT Matrix Scan for Reference Sequence for each TF Ends**********************/
				/**************************************************************************************************/
				
				
				
				
				
				
				/**********************************************************************************************************************/
				/*************************For each Altered Sequence Starts*************************************************************/
				/**********************************************************************************************************************/
				for(Iterator<String> itr =snpAlteredSequenceFileList.iterator();itr.hasNext(); ){
					
					
					
					/**************************************************************************************************/
					/********************RSAT Matrix Scan for Altered Sequence Starts**********************************/
					/**************************************************************************************************/
					//Get Altered Sequence
					alteredSequence = readAllfromFile(itr.next());
					
					alteredSequenceFirstLine = readFirstLinefromFasta(alteredSequence);	
					
					
					//Set Altered Result Key
					alteredResultKey = alteredSequenceFirstLine + "_" + tfName;
					
					description = "Best Altered Sequence"  + alteredResultKey + " Result Line Containing SNP Position ";					
					
					alteredResult = alteredSequence2RSATResultMap.get(alteredResultKey);
					
					if (alteredResult==null){
						
						//Altered Result
						alteredResult = matrixScan(alteredSequence,alteredResultKey,pfmMatrices,alteredSequence2RSATResultMap,matrixScanRequest,proxy,bufferedWriter);

						//Put alteredResult into Map
						alteredSequence2RSATResultMap.put(alteredResultKey, alteredResult);
						
						//Get best altered result line containing snp position if it exists
						getBestResultLineContainigSNPPosition(description,alteredResult,bufferedWriter,bestReferenceLineResult);
					}else{
						System.out.println("This else is entered for altered sequence");
						//Get best altered result line containing snp position if it exists
						getBestResultLineContainigSNPPosition(description,alteredResult,bufferedWriter,bestReferenceLineResult);
						
					}
					//Matrix Scan for Altered Sequence for this tfName
					/**************************************************************************************************/
					/********************RSAT Matrix Scan for Altered Sequence Ends************************************/
					/**************************************************************************************************/

					
					

					
				}//End of FOR each Altered Sequence
				/**********************************************************************************************************************/
				/*************************For each Altered Sequence Ends***************************************************************/
				/**********************************************************************************************************************/

				
				
				
				/**************************************************************************************************/
				/********************RSAT Matrix Scan for TF Peak Sequence Starts**********************************/
				/**************************************************************************************************/
				//Get Peak Sequence
				peakSequence = readAllfromFile(tfEntry.getValue());
				
				
				//Set Peak Result Key
				peakResultKey = eachSNPDirectoryName + "_" + tfName;
				
				
				//Matrix Scan for Peak Sequence for this tfName
				peakResult = peakSequence2RSATResultMap.get(peakResultKey);
				
				
				if (peakResult == null){
					
					//Extended Peak Result
					peakResult = matrixScan(peakSequence,peakResultKey,pfmMatrices,peakSequence2RSATResultMap,matrixScanRequest,proxy,bufferedWriter);
					
					//Put peakResult into map
					peakSequence2RSATResultMap.put(peakResultKey, peakResult);
					
					//Get best peak result line containing snp position 
					getBestPeakResultLine(bestReferenceLineResult.getResultLine(),peakResult,bufferedWriter);
					
				}else{
					
					System.out.println("This else is entered for Peak sequence");
					
					//Get best peak result line containing snp position 
					getBestPeakResultLine(bestReferenceLineResult.getResultLine(),peakResult,bufferedWriter);
				
				}
				/**************************************************************************************************/
				/********************RSAT Matrix Scan for TF Peak Sequence Ends************************************/
				/**************************************************************************************************/
				
				
				//Flush to write out
				bufferedWriter.flush();
				
			}//End of FOR each TF
			/**************************************************************************************************************************/
			/*************************For each TF Peak Sequence Ends*******************************************************************/
			/**************************************************************************************************************************/

			
			/**************************************************************************************************************************/
			/*************************RSAT Matrix Calls Ends***************************************************************************/
			/**************************************************************************************************************************/

			
			
		} catch(Exception e) {
			System.out.println(e.toString());
			
		}

	}
	
	
	public static String fillTFName2TFPFMMatriceFileMap(File mainTFPFMAndLogoMatricesDirectory,String tfName, Map<String,String> tfName2TFPfmMatricesFileMap){
		
		String fileName = null;
		String fileAbsolutePath = null;				         				
		String tfPfmMatricesFileName = null;
		
		tfPfmMatricesFileName = tfName2TFPfmMatricesFileMap.get(tfName);
		
		//Not found yet
		if(tfPfmMatricesFileName == null){
			
			//mainTFPFMAndLogoMatricesDirectory is Commons.TF_PFM_AND_LOGO_Matrices
			if (mainTFPFMAndLogoMatricesDirectory.exists() && mainTFPFMAndLogoMatricesDirectory.isDirectory()){
				
				search:
					
				for(File eachTFPFMAndLogoMatricesDirectory: mainTFPFMAndLogoMatricesDirectory.listFiles()){
					
					for(File eachTFFile : eachTFPFMAndLogoMatricesDirectory.listFiles()){
						
						fileName = eachTFFile.getName();
						fileAbsolutePath = eachTFFile.getAbsolutePath();
						
						if ( tfName.equals(getTFName(fileName)) && fileName.startsWith(Commons.PFM_MATRICES)){
							tfPfmMatricesFileName = fileAbsolutePath;
							tfName2TFPfmMatricesFileMap.put(tfName, tfPfmMatricesFileName);
							break search;
	         				
						}//End of IF 
						
						
					}//End of FOR eachTFFile
					
				}//End of FOR eachTFPfmAndLogoMatricesDirectory
				
			}//End of IF mainTFPFMAndLogoMatricesDirectory exists and mainTFPFMAndLogoMatricesDirectory is a directory
			
			
		}//End of IF tfPfmMatricesFileName is null
		
		
		return tfPfmMatricesFileName;
		
		
	}
		
	
	
	public static String getTFName(String fileName){
		
		String tfName = null;
		
		int indexofUnderscore = fileName.indexOf(Commons.UNDERSCORE);
		int indexofDot =  fileName.indexOf(Commons.DOT);

		tfName = fileName.substring(indexofUnderscore+1, indexofDot);
		
		return tfName;
		
	}
		
	

	public static void matrixScan(
			String outputFolder,
			String forRSASNPTFSequencesMatricesDirectory,
			BufferedWriter bufferedWriter){
		
			int matrixScanNumber=1;
		
			Map<String,String> snpReferenceSequence2RSATResultMap 	= new HashMap<String,String>();
			Map<String,String> snpAlteredSequence2RSATResultMap 	= new HashMap<String,String>();
			Map<String,String> tfExtendedPeakSequence2RSATResultMap = new HashMap<String,String>();
	
			File mainSNPsDirectory = new File(outputFolder + forRSASNPTFSequencesMatricesDirectory + Commons.SNPs);
			File mainTFPFMAndLogoMatricesDirectory = new File(outputFolder + forRSASNPTFSequencesMatricesDirectory + Commons.TF_PFM_AND_LOGO_Matrices);
		
			String snpReferenceSequenceFile		= null;
			String snpAlteredSequenceFile		= null;
			String tfExtendedPeakSequenceFile 	= null;
			
			List<String> snpAlteredSequenceFileList = null;
			Map<String,String> tfName2TfExtendedPeakSequenceFileMap = null;
			
			String tfName = null;
			
			Map<String,String>	tfName2TFPfmMatricesFileMap	= new HashMap<String, String>();
			
			String fileName=null;
			String fileAbsolutePath = null;
			
			RSATWebServicesLocator service = new RSATWebServicesLocator();
			RSATWSPortType proxy  = null;
			
			//mainSNPsDirectory is Commons.SNPs
			if (mainSNPsDirectory.exists() && mainSNPsDirectory.isDirectory()){
			
				try {
					proxy = service.getRSATWSPortType();
				} catch (ServiceException e) {
					e.printStackTrace();
				}
				
				MatrixScanRequest matrixScanRequest = new MatrixScanRequest();
				
				/**************************************************************************************************************************/
				/*************************Initialize Matrix Scan Request Parameters starts*************************************************/
				/**************************************************************************************************************************/
				initializeMatrixScanParameters(matrixScanRequest);
				/**************************************************************************************************************************/
				/*************************Initialize Matrix Scan Request Parameters ends***************************************************/
				/**************************************************************************************************************************/
				
				
        		//example eachSNPDirectory is chr1_11802721_rs17367504
			    for(File eachSNPDirectory: mainSNPsDirectory.listFiles()){
			    	
			    	//Initialize input files
	        		snpReferenceSequenceFile 	= null;
	        		snpAlteredSequenceFile 		= null;
	        		tfExtendedPeakSequenceFile 	= null;
	        		
	        		snpAlteredSequenceFileList 				= new ArrayList<String>();
	        		tfName2TfExtendedPeakSequenceFileMap	= new HashMap<String, String>();
	        		
	        		tfName= null;
	        		
	        		fileName = null;
	        		fileAbsolutePath = null;
	        		
			    	//example eachSNPDirectory chr21_42416281_rs9976767
			        if(eachSNPDirectory.isDirectory()) {
			        
			        	//Now get the SNP specific files
			        	for (File eachSNPFile:eachSNPDirectory.listFiles() ){

					         fileName = eachSNPFile.getName();
					         fileAbsolutePath = eachSNPFile.getAbsolutePath();				         				
	         				
	         				if (fileName.startsWith(Commons.SNP_REFERENCE_SEQUENCE)){
	         					snpReferenceSequenceFile = fileAbsolutePath;
	         				}else if (fileName.startsWith(Commons.SNP_ALTERED_SEQUENCE)){
	         					snpAlteredSequenceFile = fileAbsolutePath;
	         					snpAlteredSequenceFileList.add(snpAlteredSequenceFile);
	         				}else if (fileName.startsWith(Commons.TF_EXTENDED_PEAK_SEQUENCE)){
	         					tfExtendedPeakSequenceFile = fileAbsolutePath;
	         					
	         					//Get TF Name from fileName
	         					tfName = getTFName(fileName);
	         					tfName2TfExtendedPeakSequenceFileMap.put(tfName, tfExtendedPeakSequenceFile);
	         					
	         					//Get TF PFM Matrix File
	         					fillTFName2TFPFMMatriceFileMap(mainTFPFMAndLogoMatricesDirectory,tfName,tfName2TFPfmMatricesFileMap);
	         					
	         				}
					         				 
			        	}//End of FOR eachSNPFile under eachSNPDirectory
			        	
			       			         			
	         			//If all necessary files are not null
	         			if(snpReferenceSequenceFile!= null && snpAlteredSequenceFileList.size()>0 && tfName2TfExtendedPeakSequenceFileMap.size()>0){
	            			//Matrix Scan Call
	         				//what is enrichedElement
	         				//what is given interval name
	         				//what is snp
	         				logger.debug("RSAT MatrixScan " +  matrixScanNumber++ +" for " + eachSNPDirectory.getPath());
	         				matrixScan(eachSNPDirectory.getName(),tfName2TFPfmMatricesFileMap,snpReferenceSequenceFile,snpAlteredSequenceFileList,tfName2TfExtendedPeakSequenceFileMap,proxy,matrixScanRequest,bufferedWriter, snpReferenceSequence2RSATResultMap, snpAlteredSequence2RSATResultMap, tfExtendedPeakSequence2RSATResultMap);
		         			
	         			}//End of IF RSAT matrix scan
					         				
			        }//End of IF eachSNPDirectory is a directory
			    }//End of FOR eachSNPDirectory under mainSNPsDirectory
			        			
			        		
			}//End of IF mainSNPsDirectory exists and mainSNPsDirectory is a directory
	
			
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
							
		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator") ;
		
			
		String forRSASNPTFSequencesMatricesDirectory = Commons.FOR_RSA_SNP_TF_SEQUENCES_MATRICES_DIRECTORY;
		
		//delete old files starts 
		FileOperations.deleteOldFiles(outputFolder + Commons.REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT_DIRECTORY);
		//delete old files ends
		
		FileWriter fileWriterTF;
		BufferedWriter bufferedWriterTF;
		
		try {
				fileWriterTF = FileOperations.createFileWriter(outputFolder + Commons.REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT_DIRECTORY + Commons.FOR_ALL_ANNOTATED_TFS_RSAT_RESULTS);
				bufferedWriterTF = new BufferedWriter(fileWriterTF);
			
				/***************************************************************************************************/
				/*****************Regulatory Sequence Analysis for All TF Annotations starts************************/
				/***************************************************************************************************/
				logger.debug("RSAT starts for TF");
				matrixScan(outputFolder,forRSASNPTFSequencesMatricesDirectory,bufferedWriterTF);
				bufferedWriterTF.close();
				logger.debug("RSAT ends for TF");
				/***************************************************************************************************/
				/*****************Regulatory Sequence Analysis for All TF Annotations ends**************************/
				/***************************************************************************************************/
				
				//Close bufferedWriter
				bufferedWriterTF.close();
							
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				

	}
				

}