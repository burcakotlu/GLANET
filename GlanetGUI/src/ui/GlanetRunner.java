package ui;

import empiricalpvalues.AnnotatePermutationsWithEnrichmentChoicesWithNumbers;
import empiricalpvalues.CollectionofPermutationsResults;
import giveninputdata.InputDataProcess;
import giveninputdata.InputDataRemoveOverlaps;
import jaxbxjctool.GenerationofSequencesandMatricesforGivenIntervals;
import rsat.RSATMatrixScanClient;
import annotate.intervals.parametric.AnnotateGivenIntervalsWithGivenParameters;
import augmentation.results.AugmentationofEnrichedElementswithIntervals;
import common.Commons;

public class GlanetRunner extends Thread{
	
	private static String args[];
	private static MainView mainView;
	
	@Override
	public void run(){
		
		getMainView().setCurrentProcessInfo( "InputDataProcess...");
		InputDataProcess.main(getArgs());
		
		getMainView().setCurrentProcessInfo( "RemoveOverlaps...");
		InputDataRemoveOverlaps.main(getArgs());
		
//		getMainView().setCurrentProcessInfo( "HumanRefSeq2Gene...");
//		HumanRefSeq2Gene.main(getArgs());
//		getMainView().setCurrentProcessInfo( "CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting...");
//		CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting.main(getArgs());
//		getMainView().setCurrentProcessInfo( "CreateIntervalFileUsingUCSCGenomeUsingIntervalTreeSorting...");
//		CreateIntervalFileUsingUCSCGenomeUsingIntervalTreeSorting.main(getArgs());
//		getMainView().setCurrentProcessInfo( "WriteAllPossibleNames...");
//		WriteAllPossibleNames.main(getArgs());
//		getMainView().setCurrentProcessInfo( "CalculateNumberofNonOverlappingBasePairsinWholeGenomeUsingIntervalTree...");
//		CalculateNumberofNonOverlappingBasePairsinWholeGenomeUsingIntervalTree.main(getArgs());

		
//		getMainView().setCurrentProcessInfo( "Annotate Given Input Data...");
//		AnnotateGivenIntervalsWithGivenParameters.main(getArgs());
//		
		getMainView().setCurrentProcessInfo( "Annotate Permutations for Enrichment...");
		AnnotatePermutationsWithEnrichmentChoicesWithNumbers.main(getArgs());
		
		getMainView().setCurrentProcessInfo( "CollectionofPermutationsResults...");
		CollectionofPermutationsResults.main(getArgs());
		
		if( getArgs()[16].equalsIgnoreCase(Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT)) {
			getMainView().setCurrentProcessInfo( "AugmentationofEnrichedElementswithIntervals...");
			AugmentationofEnrichedElementswithIntervals.main(getArgs());
			getMainView().setCurrentProcessInfo( "GenerationofSequencesandMatricesforGivenIntervals...");
			GenerationofSequencesandMatricesforGivenIntervals.main(getArgs());
			getMainView().setCurrentProcessInfo( "RSATMatrixScanClient...");
			RSATMatrixScanClient.main(getArgs());
		}
		
		getMainView().setCurrentProcessInfo( "GLANET ended execution. You can see results under " + args[1] + System.getProperty("file.separator") + "Output");
		
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
		
		GlanetRunner.args = args;
	}
}
