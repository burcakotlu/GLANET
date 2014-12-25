package ui;

import collaboration.GivenIntervalVersusElementAnnotationBinaryMatrixForOnePhenotype;
import enrichment.CollectionofPermutationsResults;
import enrichment.Enrichment;
import enumtypes.CommandLineArguments;
import giveninputdata.InputDataProcess;
import giveninputdata.InputDataRemoveOverlaps;
import giveninputdata.Preparation;
import jaxbxjctool.GenerationofSequencesandMatricesforGivenIntervals;
import rsat.RSATMatrixScanClient;
import annotation.Annotation;
import augmentation.results.AugmentationofEnrichmentWithAnnotationInGRCh37p13Coordinates;
import augmentation.results.AugmentationofEnrichmentInLatestAssemblyUsingNCBIREMAP;
import common.Commons;

public class GlanetRunner implements Runnable {

    private static String args[];
    private static MainView mainView;

    @Override
    public void run() {

	/************************ Preparation starts ********************************************/
	if (getMainView() != null)
	    getMainView().setCurrentProcessInfo("Preparation...");

	if (Thread.currentThread().isInterrupted())
	    return;
	Preparation.main(args);
	/************************ Preparation ends **********************************************/

	/************************ InputDataProcess starts ***************************************/
	if (getMainView() != null)
	    getMainView().setCurrentProcessInfo("InputDataProcess...");

	if (Thread.currentThread().isInterrupted())
	    return;
	InputDataProcess.main(args);
	/************************ InputDataProcess ends *****************************************/

	/************************ RemoveOverlaps starts ******************************************/
	if (getMainView() != null)
	    getMainView().setCurrentProcessInfo("RemoveOverlaps...");

	if (Thread.currentThread().isInterrupted())
	    return;
	InputDataRemoveOverlaps.main(args);
	/************************ RemoveOverlaps ends ********************************************/

	// /* In case of Enrichment remove overlaps and merge */
	// /* In case of only Annotation with Enrichment, do not remove overlaps
	// and do not merge*/
	// if(
	// getArgs()[CommandLineArguments.PerformEnrichment.value()].equalsIgnoreCase(Commons.DO_ENRICH)){
	//
	// if( getMainView() != null)
	// getMainView().setCurrentProcessInfo( "RemoveOverlaps...");
	//
	// if( Thread.currentThread().isInterrupted())
	// return;
	// InputDataRemoveOverlaps.main( args);
	// }

	/************************ Annotation starts ***********************************************/
	if (getMainView() != null)
	    getMainView().setCurrentProcessInfo("Annotate Given Input Data...");

	if (Thread.currentThread().isInterrupted())
	    return;
	Annotation.main(args);
	/************************ Annotation ends *************************************************/

	/************************ Annotation Binary Matrices starts *******************************/
	if (getMainView() != null)
	    getMainView()
		    .setCurrentProcessInfo("Annotation Binary Matrices...");

	if (Thread.currentThread().isInterrupted())
	    return;
	GivenIntervalVersusElementAnnotationBinaryMatrixForOnePhenotype
		.main(args);
	/************************ Annotation Binary Matrices ends *********************************/

	/******************************************************************************************/
	/************************ Enrichment starts ***********************************************/
	/******************************************************************************************/
	if (getArgs()[CommandLineArguments.PerformEnrichment.value()]
		.equalsIgnoreCase(Commons.DO_ENRICH)) {

	    /************************ Annotate Permutations starts ****************************/
	    if (getMainView() != null)
		getMainView().setCurrentProcessInfo(
			"Annotate Permutations for Enrichment...");

	    if (Thread.currentThread().isInterrupted())
		return;
	    Enrichment.main(args);
	    /************************ Annotate Permutations ends ******************************/

	    /******************* Collection of Permutations Results starts *******************/
	    if (getMainView() != null)
		getMainView().setCurrentProcessInfo(
			"Collection of Permutations Results...");

	    if (Thread.currentThread().isInterrupted())
		return;
	    CollectionofPermutationsResults.main(args);
	    /******************* Collection of Permutations Results ends *********************/

	    /************
	     * Augmentation of Enriched Elements with Given Input Data starts in
	     * GRCh37.p13
	     *****/
	    if (getMainView() != null)
		getMainView()
			.setCurrentProcessInfo(
				"Augmentation of Enriched Elements with Annotation in GRCh37.p13 ...");

	    if (Thread.currentThread().isInterrupted())
		return;
	    AugmentationofEnrichmentWithAnnotationInGRCh37p13Coordinates
		    .main(args);
	    /************
	     * Augmentation of Enriched Elements with Given Input Data ends in
	     * GRCh37.p13
	     *******/

	}
	/****************************************************************************************/
	/************************ Enrichment ends ***********************************************/
	/****************************************************************************************/

	/******************************************************************************/
	/********************************** RSAT starts *******************************/
	/******************************************************************************/
	if (getArgs()[CommandLineArguments.RegulatorySequenceAnalysisUsingRSAT.value()].equalsIgnoreCase(Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT)) {
	    if (getMainView() != null)
		getMainView().setCurrentProcessInfo("For Regulatory Sequence Analysis...");

	    /*********** Check whether given input data is snps or not starts **************/
	    if (getMainView() != null)
		getMainView().setCurrentProcessInfo("Check whether given input data is comprised of SNPs before Regulatory Sequence Analysis...");

	    if (args[CommandLineArguments.GivenInputDataType.value()].equalsIgnoreCase(Commons.GIVEN_INPUT_DATA_CONSISTS_OF_SNPS)) {
		  
		    /************ Creation of NCBI REMAP Input files starts *************************/
		    /************************* CALL NCBI REMAP API starts ***************************/
		    /************ Creation of NCBI REMAP Output files starts ************************/
		    /************* Augmentation of Enriched Elements with Given Input Data starts in GRCh38 *****/
		    if (getMainView() != null)
			getMainView().setCurrentProcessInfo("Augmentation of Enriched Elements with Annotation in GRCh38 using NCBI Remap...");

		    if (Thread.currentThread().isInterrupted())
			return;
		    AugmentationofEnrichmentInLatestAssemblyUsingNCBIREMAP.main(args);
		    /************* Augmentation of Enriched Elements with Given Input Data ends in GRCh38 *****/
		    /************ Creation of NCBI REMAP Input files ends **************************/
		    /************************* CALL NCBI REMAP API ends ****************************/
		    /************ Creation of NCBI REMAP Output files ends *************************/

		    if (getMainView() != null)
			getMainView().setCurrentProcessInfo("GenerationofSequencesandMatricesforGivenIntervals...");

		    if (Thread.currentThread().isInterrupted())
			return;
		    GenerationofSequencesandMatricesforGivenIntervals.main(args);

		    if (getMainView() != null)
			getMainView().setCurrentProcessInfo("RSATMatrixScanClient...");

		    if (Thread.currentThread().isInterrupted())
			return;
		    RSATMatrixScanClient.main(args);
	    }
	    /*********** Check whether given input data is snps or not ends ****************/

	  
	}
	/******************************************************************************/
	/********************************** RSAT ends *********************************/
	/******************************************************************************/

	/************************ GLANET execution ends ********************************************/
	if (getMainView() != null)
	    getMainView().setCurrentProcessInfo(
		    "GLANET execution has ended. You can reach results under "
			    + args[CommandLineArguments.GlanetFolder.value()]
			    + "Output" + System.getProperty("file.separator")
			    + args[CommandLineArguments.JobName.value()]
			    + System.getProperty("file.separator"));
	if (getMainView() != null)
	    getMainView().enableStartProcess(true);
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
	for (int i = 0; i < args.length; i++)
	    GlanetRunner.args[i] = args[i];
    }
}
