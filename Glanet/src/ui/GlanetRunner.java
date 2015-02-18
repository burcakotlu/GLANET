package ui;

import enrichment.CollectionofPermutationsResults;
import enrichment.Enrichment;
import enumtypes.CommandLineArguments;
import giveninputdata.InputDataNCBIRemap;
import giveninputdata.InputDataProcess;
import giveninputdata.InputDataRemoveOverlaps;
import giveninputdata.Preparation;

import org.apache.log4j.Logger;

import rsat.GenerationofAllTFAnnotationsFileInGRCh37p13AndInLatestAssembly;
import rsat.GenerationofSequencesandMatricesforSNPs;
import rsat.RegulatorySequenceAnalysisUsingRSATMatrixScan;
import annotation.Annotation;
import augmentation.results.AugmentationofEnrichmentWithAnnotationInGRCh37p13Coordinates;
import collaboration.GivenIntervalVersusElementAnnotationBinaryMatrixForOnePhenotype;

import common.Commons;

public class GlanetRunner implements Runnable {

	final static Logger logger = Logger.getLogger(GlanetRunner.class);

	private static String args[];
	private static MainView mainView;

	@Override
	public void run() {

		/************************ Preparation starts ********************************************/
		setCurrentProcessInfo("Preparation...");

		Preparation.main(args);
		/************************ Preparation ends **********************************************/

		/************************ InputDataProcess starts ***************************************/
		setCurrentProcessInfo("InputDataProcess...");

		InputDataProcess.main(args);
		/************************ InputDataProcess ends *****************************************/

		/************************ RemoveOverlaps starts ******************************************/
		setCurrentProcessInfo("RemoveOverlaps...");

		InputDataRemoveOverlaps.main(args);
		/************************ RemoveOverlaps ends ********************************************/

		/************************ NCBI REMAP starts ***********************************************/
		setCurrentProcessInfo("NCBI REMAP starts...");

		InputDataNCBIRemap.main(args);
		/************************ NCBI REMAP ends ***********************************************/

		/************************ Annotation starts ***********************************************/
		setCurrentProcessInfo("Annotate Given Input Data...");

		Annotation.main(args);
		/************************ Annotation ends *************************************************/

		/************************ Annotation Binary Matrices starts *******************************/
		setCurrentProcessInfo("Annotation Binary Matrices...");

		GivenIntervalVersusElementAnnotationBinaryMatrixForOnePhenotype.main(args);
		/************************ Annotation Binary Matrices ends *********************************/

		/******************************************************************************************/
		/************************ Enrichment starts ***********************************************/
		/******************************************************************************************/
		if (getArgs()[CommandLineArguments.PerformEnrichment.value()].equalsIgnoreCase(Commons.DO_ENRICH)) {

			/************************ Annotate Permutations starts ****************************/
			setCurrentProcessInfo("Annotate Permutations for Enrichment...");

			Enrichment.main(args);
			/************************ Annotate Permutations ends ******************************/

			/******************* Collection of Permutations Results starts *******************/
			setCurrentProcessInfo("Collection of Permutations Results...");

			CollectionofPermutationsResults.main(args);
			/******************* Collection of Permutations Results ends *********************/

			/*************
			 * Augmentation of Enriched Elements with Given Input Data starts in
			 * * GRCh37.p13
			 *****/
			setCurrentProcessInfo("Augmentation of Enriched Elements with Annotation in GRCh37.p13 ...");

			AugmentationofEnrichmentWithAnnotationInGRCh37p13Coordinates.main(args);
			/*************
			 * Augmentation of Enriched Elements with Given Input Data ends in*
			 * GRCh37.p13
			 *******/

		}
		/****************************************************************************************/
		/************************ Enrichment ends ***********************************************/
		/****************************************************************************************/

		/******************************************************************************/
		/************* Regulatory Sequence Analysis starts ****************************/
		/******************************************************************************/

		if ((getArgs()[CommandLineArguments.TfAnnotation.value()].equalsIgnoreCase(Commons.DO_TF_ANNOTATION) || getArgs()[CommandLineArguments.TfAndKeggPathwayAnnotation.value()].equalsIgnoreCase(Commons.DO_TF_KEGGPATHWAY_ANNOTATION) || getArgs()[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()].equalsIgnoreCase(Commons.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION)) && getArgs()[CommandLineArguments.RegulatorySequenceAnalysisUsingRSAT.value()].equalsIgnoreCase(Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT)) {
			if (getMainView() != null)
				getMainView().setCurrentProcessInfo("For Regulatory Sequence Analysis...");

			/*********** Check whether given input data is snps or not starts **************/
			setCurrentProcessInfo("Check whether given input data is comprised of SNPs before Regulatory Sequence Analysis...");

			if (args[CommandLineArguments.GivenInputDataType.value()].equalsIgnoreCase(Commons.GIVEN_INPUT_DATA_CONSISTS_OF_SNPS)) {

				/************ Creation of NCBI REMAP Input files starts *************************/
				/************************* CALL NCBI REMAP API starts ***************************/
				/************ Creation of NCBI REMAP Output files starts ************************/
				/**************** Generation of ALL TF Annotations in GRCh38 starts ***************/
				setCurrentProcessInfo("Generation of All TF  Annotations in GRCh38 using NCBI Remap...");

				GenerationofAllTFAnnotationsFileInGRCh37p13AndInLatestAssembly.main(args);

				/**************** Generation of ALL TF Annotations in GRCh38 ends ****************/
				/************ Creation of NCBI REMAP Input files ends **************************/
				/************************* CALL NCBI REMAP API ends ****************************/
				/************ Creation of NCBI REMAP Output files ends *************************/

				setCurrentProcessInfo("Generation of SNP Reference and Alternate Sequences, TF Peak Sequence and TF PFM and LOGO Matrices for Given SNPs...");

				GenerationofSequencesandMatricesforSNPs.main(args);

				setCurrentProcessInfo("Regulatory Sequence Analysis Using RSAT...");

				RegulatorySequenceAnalysisUsingRSATMatrixScan.main(args);
			}
			/*********** Check whether given input data is snps or not ends ****************/

		}
		/******************************************************************************/
		/************* Regulatory Sequence Analysis ends ******************************/
		/******************************************************************************/

		/************************ GLANET execution ends ********************************************/
		setCurrentProcessInfo("GLANET execution has ended. You can reach results under " + args[CommandLineArguments.GlanetFolder.value()] + "Output" + System.getProperty("file.separator") + args[CommandLineArguments.JobName.value()] + System.getProperty("file.separator"));

		if (getMainView() != null) {
			getMainView().enableStartProcess(true);
			getMainView().refreshButtons();
		}

		GlanetRunner.appendLog("Execution has ended");
		/************************ GLANET execution ends *************************************************/
	}

	public static void appendLog(String log) {

		if (getMainView() == null)
			System.out.println(log);
		else
			getMainView().appendNewTextToLogArea(log);
	}

	public static void appendLog(int log) {

		if (getMainView() == null)
			System.out.println(log);
		else
			getMainView().appendNewTextToLogArea(log);
	}

	public static void appendLog(float log) {

		if (getMainView() == null)
			System.out.println(log);
		else
			getMainView().appendNewTextToLogArea(log);
	}

	public static MainView getMainView() {

		return mainView;
	}

	public static void setMainView(MainView mainView) {

		GlanetRunner.mainView = mainView;
	}

	public static String[] getArgs() {

		return args;
	}

	public static void setArgs(String args[]) {

		GlanetRunner.args = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			GlanetRunner.args[i] = args[i];

		}
	}

	public static void setCurrentProcessInfo(String processInfo) {

		if (getMainView() != null)
			getMainView().setCurrentProcessInfo(processInfo);
		else
			System.out.println("Current Status: " + processInfo);
	}
}
