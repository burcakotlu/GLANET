/**
 * 
 */
package rsat;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import RSATWS.MatrixScanRequest;
import RSATWS.MatrixScanResponse;
import RSATWS.RSATWSPortType;
import RSATWS.RSATWebServicesLocator;

import common.Commons;

/**
 * @author Bur�ak Otlu
 * @date Jan 15, 2015
 * @project Glanet
 *
 */
public class BurcakOtluTestRSATOldCode {

	public static void initializeMatrixScanParameters(MatrixScanRequest matrixScanRequest) {

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
		String[] uth = { "pval 0.1" };

		sequence_format = "fasta";
		matrixScanRequest.setSequence_format(sequence_format);

		// How to set this parameter?
		// mask = "non-dna";

		matrixFormat = "tab";
		matrixScanRequest.setMatrix_format(matrixFormat);

		// If markov order is not set
		// Execution error is given
		// Error: You must specify the method for background estimation (an
		// option among -bgfile, -bginput, -window)
		markov = 1;
		matrixScanRequest.setMarkov(markov);

		organism = Commons.RSAT_ORGANISM_Homo_sapiens_ensembl_74_GRCh37;
		matrixScanRequest.setOrganism(organism);

		background = Commons.RSAT_BACKGROUND_upstream_noorf;
		matrixScanRequest.setBackground(background);

		pseudo_frequencies = 0.01f;
		matrixScanRequest.setBackground_pseudo(new Float(pseudo_frequencies));

		search_strands = new Integer(2);
		matrixScanRequest.setStr(search_strands);

		// Although it is set to start
		// It is not set in the called matrixScan
		origin = "start";
		matrixScanRequest.setOrigin(origin);

		// how to set this parameter?
		// offset = "0";

		verbosity = new Integer(1);
		matrixScanRequest.setVerbosity(verbosity);

		pseudo_counts = new Integer(1);
		matrixScanRequest.setPseudo(pseudo_counts);

		score_decimals = new Integer(1);
		matrixScanRequest.setDecimals(score_decimals);

		n_treatment = "score";
		matrixScanRequest.setN_treatment(n_treatment);

		// If you do not set these parameters
		// they are set to NONE

		matrixScanRequest.setUth(uth);
		matrixScanRequest.setReturn_fields("sites,pval,rank");

	}

	public static void matrixScanUsingRSAT() {

		RSATWebServicesLocator service = new RSATWebServicesLocator();
		RSATWSPortType proxy = null;
		MatrixScanRequest matrixScanRequest = new MatrixScanRequest();
		String result = null;
		//String jobID = null;

		initializeMatrixScanParameters(matrixScanRequest);

		String sequence = ">SNPAlteredSequence_rs1891215_C_chr1_7667794" + System.getProperty("line.separator") + "GCCAGCCTTGCTTTCCCAGAAGCCCTCAA";

		String pfmMatrices = "; MA0139.1 CTCF" + System.getProperty("line.separator") + "A|	0.09529025	0.18291347	0.30777657	0.061336253	0.008762322	0.8148959	0.04381161	0.11719605	0.932092	0.0054764515	0.36473164	0.059145674	0.013143483	0.061336253	0.11391018	0.40744796	0.0898138	0.12814896	0.4403067" + System.getProperty("line.separator") + "C|	0.31872946	0.15881708	0.05366922	0.8762322	0.9890471	0.014238773	0.57831323	0.4742607	0.012048192	0.0	0.0032858707	0.013143483	0.0	0.008762322	0.80284774	0.014238773	0.5279299	0.35268345	0.19824754" + System.getProperty("line.separator") + "G|	0.08324206	0.45345017	0.49178532	0.023001095	0.0	0.07119387	0.36582693	0.05257393	0.03504929	0.9890471	0.61993426	0.5520263	0.97480834	0.84884995	0.0054764515	0.55531216	0.33625412	0.07995619	0.2913472" + System.getProperty("line.separator") + "T|	0.50273824	0.20481928	0.1467689	0.03943045	0.0021905806	0.099671416	0.012048192	0.35487404	0.019715225	0.0032858707	0.009857613	0.37349397	0.008762322	0.07776561	0.07338445	0.018619934	0.04052574	0.43373495	0.06462213" + System.getProperty("line.separator") + "//" + System.getProperty("line.separator");

		matrixScanRequest.setSequence(sequence);
		matrixScanRequest.setMatrix(pfmMatrices);

		try {
			proxy = service.getRSATWSPortType();

			/***************************************************************************/
			/************************ OLD CODE STARTS ************************************/
			/***************************************************************************/
			/* Call the service */
			MatrixScanResponse response;
			response = proxy.matrix_scan(matrixScanRequest);

			/* Get the result */
			result = response.getClient();

			System.out.println(result);
			/***************************************************************************/
			/************************ OLD CODE ENDS **************************************/
			/***************************************************************************/

		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		matrixScanUsingRSAT();

	}

}
