package ui;

import enrichment.CollectionofPermutationsResults;
import enrichment.Enrichment;
import enumtypes.CommandLineArguments;
import giveninputdata.InputDataNCBIRemap;
import giveninputdata.InputDataProcess;
import giveninputdata.InputDataRemoveOverlaps;
import giveninputdata.Preparation;
import rsat.GeneAnnotationForPostAnalysisRSAResults;
import rsat.GenerationofAllTFAnnotationsFileInGRCh37p13AndInLatestAssembly;
import rsat.GenerationofSequencesandMatricesforSNPs;
import rsat.RegulatorySequenceAnalysisPostAnalysis;
import rsat.RegulatorySequenceAnalysisUsingRSATMatrixScan;
import annotation.Annotation;
//import augmentation.results.AugmentationofEnrichmentWithAnnotationInGRCh37p13Coordinates;
import auxiliary.SummaryReportFile;

import common.Commons;

public class GlanetRunner implements Runnable {

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
		
		/******************************************************************************************/
		/************************ Annotation starts ***********************************************/
		/******************************************************************************************/
		setCurrentProcessInfo("Annotate Given Input Data...");

		Annotation.main(args);
		/******************************************************************************************/
		/************************ Annotation ends *************************************************/
		/******************************************************************************************/

		
//		/************************ Annotation Binary Matrices starts *******************************/
//		setCurrentProcessInfo("Annotation Binary Matrices...");
//
//		GivenIntervalVersusElementAnnotationBinaryMatrixForOnePhenotype.main(args);
//		/************************ Annotation Binary Matrices ends *********************************/

		/******************************************************************************************/
		/************************ Enrichment starts ***********************************************/
		/******************************************************************************************/
		if((getArgs()[CommandLineArguments.PerformEnrichment.value()].equalsIgnoreCase(Commons.DO_ENRICH) || 
			getArgs()[CommandLineArguments.PerformEnrichment.value()].equalsIgnoreCase(Commons.DO_ENRICH_WITHOUT_ANNOTATION))){

			/************************ Annotate Permutations starts ****************************/
			setCurrentProcessInfo("Annotate Samplings for Enrichment...");

			Enrichment.main(args);
			/************************ Annotate Permutations ends ******************************/

			if(getArgs()[CommandLineArguments.EnrichmentZScoreMode.value()].equalsIgnoreCase(Commons.PERFORM_ENRICHMENT_WITH_ZSCORE)){

				/******************* Collection of Samplings Results starts *******************/
				setCurrentProcessInfo("Collection of Samplings Results With ZScores...");

				CollectionofPermutationsResults.main(args);
				/******************* Collection of Samplings Results ends *********************/

			}
			//10th of September 2015
			//We should not do anything here for PERFORM_ENRICHMENT_WITHOUT_ZSCORE
			//Since resulting output file is already written in Enrichment Class
			//This is not correct since there can be more than one run in Enrichment
			//And all of the run results must be collected.
			else if (getArgs()[CommandLineArguments.EnrichmentZScoreMode.value()].equalsIgnoreCase(Commons.PERFORM_ENRICHMENT_WITHOUT_ZSCORE)){
				/******************* Collection of Samplings Results starts *******************/
				setCurrentProcessInfo("Collection of Samplings Results Without ZScores...");

				CollectionofPermutationsResults.main(args);
				/******************* Collection of Samplings Results ends *********************/
			}
			
			/************** Augmentation of Enriched Elements with Given Input Data starts in** GRCh37.p13*******/
			if(getArgs()[CommandLineArguments.AnnotationFoundOverlapsOutputMode.value()].equalsIgnoreCase(Commons.DO_WRITE_OVERLAPS_EACH_ONE_IN_SEPARATE_FILE_ELEMENT_BASED)){
				setCurrentProcessInfo("Write Element Based Annotation Found Overlaps in GRCh37.p13 ...");
			}
			
			if(getArgs()[CommandLineArguments.AnnotationFoundOverlapsOutputMode.value()].equalsIgnoreCase(Commons.DO_WRITE_OVERLAPS_ALL_IN_ONE_FILE_ELEMENT_TYPE_BASED)){
				setCurrentProcessInfo("Write Element Type Based Annotation Found Overlaps in GRCh37.p13 ...");

				//In case of use or need, this class has to be updated since now output have mean, stdDev, zscores amd they can be null
				//Trying to parse null as float causes java.lang.NumberFormatException
//				AugmentationofEnrichmentWithAnnotationInGRCh37p13Coordinates.main(args);
			}
			
			if(getArgs()[CommandLineArguments.AnnotationFoundOverlapsOutputMode.value()].equalsIgnoreCase(Commons.DO_NOT_WRITE_OVERLAPS_AT_ALL)){
				setCurrentProcessInfo("Do Not Write Annotation Found Overlaps At All in GRCh37.p13 ...");

				//In case of use or need, this class has to be updated since now output have mean, stdDev, zscores amd they can be null
				//Trying to parse null as float causes java.lang.NumberFormatException
//				AugmentationofEnrichmentWithAnnotationInGRCh37p13Coordinates.main(args);
			}


			/************** Augmentation of Enriched Elements with Given Input Data ends in** GRCh37.p13*********/

		}
		/****************************************************************************************/
		/************************ Enrichment ends ***********************************************/
		/****************************************************************************************/

		/******************************************************************************/
		/************* Regulatory Sequence Analysis starts ****************************/
		/******************************************************************************/
		if((getArgs()[CommandLineArguments.TfAnnotation.value()].equalsIgnoreCase(Commons.DO_TF_ANNOTATION) || 
				getArgs()[CommandLineArguments.TfAndKeggPathwayAnnotation.value()].equalsIgnoreCase(Commons.DO_TF_KEGGPATHWAY_ANNOTATION) || 
				getArgs()[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()].equalsIgnoreCase(Commons.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION)) && 
				getArgs()[CommandLineArguments.RegulatorySequenceAnalysisUsingRSAT.value()].equalsIgnoreCase(Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT)){

			if(getMainView() != null)
				getMainView().setCurrentProcessInfo("For Regulatory Sequence Analysis...");

			/*********** Check whether given input data is snps or not starts **************/
			setCurrentProcessInfo("Check whether given input data is comprised of SNPs before Regulatory Sequence Analysis...");
			
			//debug starts
			setCurrentProcessInfo("GivenInputDataType: " + args[CommandLineArguments.GivenInputDataType.value()]);
			//debug end

			if((args[CommandLineArguments.GivenInputDataType.value()].equalsIgnoreCase(Commons.GIVEN_INPUT_DATA_CONSISTS_OF_SNPS) ||  args[CommandLineArguments.InputFileDataFormat.value()].equalsIgnoreCase(Commons.INPUT_FILE_FORMAT_DBSNP_IDS))&& 
					!args[CommandLineArguments.AnnotationFoundOverlapsOutputMode.value()].equalsIgnoreCase(Commons.DO_NOT_WRITE_OVERLAPS_AT_ALL)){
				
				
			
				/************ Creation of NCBI REMAP Input files starts *************************/
				/************************* CALL NCBI REMAP API starts ***************************/
				/************ Creation of NCBI REMAP Output files starts ************************/
				/**************** Generation of ALL TF Annotations in Latest Assembly starts ***************/
				setCurrentProcessInfo("Generation of All TF Annotations in Latest Assembly (returned by NCBI eutils) using NCBI Remap...");

				GenerationofAllTFAnnotationsFileInGRCh37p13AndInLatestAssembly.main(args);

				/**************** Generation of ALL TF Annotations in Latest Assembly ends ****************/
				/************ Creation of NCBI REMAP Input files ends **************************/
				/************************* CALL NCBI REMAP API ends ****************************/
				/************ Creation of NCBI REMAP Output files ends *************************/
				setCurrentProcessInfo("Generation of SNP Reference and Alternate Sequences, TF Peak Sequence and TF PFM and LOGO Matrices for Given SNPs...");

				GenerationofSequencesandMatricesforSNPs.main(args);

				setCurrentProcessInfo("Regulatory Sequence Analysis Using RSAT...");

				RegulatorySequenceAnalysisUsingRSATMatrixScan.main(args);
				
				setCurrentProcessInfo("Post Analysis of Regulatory Sequence Analysis Results...");

				RegulatorySequenceAnalysisPostAnalysis.main(args);
				
				setCurrentProcessInfo("Gene Annotation for Post Analysis of Regulatory Sequence Analysis Results...");
				
				//For Hacettepe Collaboration
				//GeneAnnotationForPostAnalysisRSAResults.main(args);
				
				//Closes the GUI
				//Closes the JVM
				//Runtime.getRuntime().exit(0);
				

			}
			/*********** Check whether given input data is snps or not ends ****************/

		}
		/******************************************************************************/
		/************* Regulatory Sequence Analysis ends ******************************/
		/******************************************************************************/
		
		
		//9 March 2017
		/******************************************************************************/
		/***************Summary Report File starts*************************************/
		/******************************************************************************/
		SummaryReportFile.main(args);
		setCurrentProcessInfo("You can reach Summary Report file under " + args[CommandLineArguments.OutputFolder.value()]);
		/******************************************************************************/
		/***************Summary Report File ends***************************************/
		/******************************************************************************/

		/************************ GLANET execution ends ********************************************/
		setCurrentProcessInfo("GLANET execution has ended. You can reach results under " + args[CommandLineArguments.OutputFolder.value()]);

		if(getMainView() != null){
			getMainView().enableStartProcess(true);
			getMainView().refreshButtons();
		}

		GlanetRunner.appendLog("Execution has ended");
		/************************ GLANET execution ends *************************************************/
	}

	public static void appendLog(String log) {

		if(getMainView() == null && args[CommandLineArguments.LogFile.value()].equalsIgnoreCase(Commons.ARG_LOG_FILE))
			System.out.println(log);
		else if(getMainView() != null)
			getMainView().appendNewTextToLogArea(log);
	}

	public static void appendLog(int log) {

		if(getMainView() == null && args[CommandLineArguments.LogFile.value()].equalsIgnoreCase(Commons.ARG_LOG_FILE))
			System.out.println(log);
		else if(getMainView() != null)
			getMainView().appendNewTextToLogArea(log);
	}

	public static void appendLog(float log) {

		if(getMainView() == null && args[CommandLineArguments.LogFile.value()].equalsIgnoreCase(Commons.ARG_LOG_FILE))
			System.out.println(log);
		else if(getMainView() != null)
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
		for(int i = 0; i < args.length; i++)
			GlanetRunner.args[i] = args[i];
	}
	
	public static boolean shouldLog(){
		
		if(args != null &&
			args.length > CommandLineArguments.LogFile.value() &&
			GlanetRunner.args[CommandLineArguments.LogFile.value()].equalsIgnoreCase(Commons.ARG_LOG_FILE))
			return true;
		
		return false;
	}
	
	public static void setCurrentProcessInfo(String processInfo) {

		if(getMainView() != null)
			getMainView().setCurrentProcessInfo(processInfo);
		else if(args[CommandLineArguments.LogFile.value()].equalsIgnoreCase(Commons.ARG_LOG_FILE))
			System.out.println("Current Status: " + processInfo);
	}
}
