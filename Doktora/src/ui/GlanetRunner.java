package ui;

import empiricalpvalues.AnnotatePermutationsWithNumbersWithChoices;
import empiricalpvalues.CollectionofPermutationsResults;
import giveninputdata.InputDataProcess;
import giveninputdata.InputDataRemoveOverlaps;
import giveninputdata.Preparation;
import jaxbxjctool.GenerationofSequencesandMatricesforGivenIntervals;
import rsat.RSATMatrixScanClient;
import annotate.intervals.parametric.AnnotateGivenIntervalsWithNumbersWithChoices;
import augmentation.results.AugmentationofEnrichedElementswithGivenInputData;
import augmentation.results.CreationofRemapInputFileswith0BasedStart1BasedEndGRCh37Coordinates;
import common.Commons;

public class GlanetRunner extends Thread{
	
	private static String args[];
	private static MainView mainView;

	@Override
	public void run(){
		
		try {
			
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "Preparation...");
			
			Preparation.main( args);
			
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "InputDataProcess...");
			
			InputDataProcess.main( args);
			
			/* In case of Enrichment remove overlaps and merge */
			/* In case of only Annotation with Enrichment, do not remove overlaps and do not merge*/
			if( getArgs()[4].equalsIgnoreCase(Commons.DO_ENRICH)){
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "RemoveOverlaps...");
				
				InputDataRemoveOverlaps.main( args);
			}
			
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "Annotate Given Input Data...");
			
			AnnotateGivenIntervalsWithNumbersWithChoices.main( args);
			
			if( getArgs()[4].equalsIgnoreCase(Commons.DO_ENRICH)){
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "Annotate Permutations for Enrichment...");
				
				AnnotatePermutationsWithNumbersWithChoices.main( args);
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "Collection of Permutations Results...");
				
				CollectionofPermutationsResults.main( args);
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "Augmentation of Enriched Elements with Given Input Data...");
				
				AugmentationofEnrichedElementswithGivenInputData.main( args);
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "Creation of NCBI Remap input files...");
				
				CreationofRemapInputFileswith0BasedStart1BasedEndGRCh37Coordinates.main( args);
		
				
				if( getArgs()[16].equalsIgnoreCase(Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT)) {
					
					if( getMainView() != null)
						getMainView().setCurrentProcessInfo( "GenerationofSequencesandMatricesforGivenIntervals...");
					
					GenerationofSequencesandMatricesforGivenIntervals.main( args);
					
					if( getMainView() != null)
						getMainView().setCurrentProcessInfo( "RSATMatrixScanClient...");
					
					RSATMatrixScanClient.main( args);
				}
			}
			
			//args[1]  already has file separator at the end
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "GLANET execution has ended. You can reach results under " + args[1]  + "Output");
			if( getMainView() != null)
				getMainView().enableStartProcess( true);
			GlanetRunner.appendLog( "Execution has ended");
		
		} catch (SecurityException e) {
			
			GlanetRunner.appendLog( "Pressed stop");
        }
	}
	
	public static void appendLog( String log) {
		
		if( getMainView() == null)
			System.out.println( log);
		else
			getMainView().appendNewTextToLogArea( log);
	}
	
	public static void appendLog( int log) {
		
		if( getMainView() == null)
			System.out.println( log);
		else
			getMainView().appendNewTextToLogArea( log);
	}
	
	public static void appendLog( float log) {
		
		if( getMainView() == null)
			System.out.println( log);
		else
			getMainView().appendNewTextToLogArea( log);
	}

	public static MainView getMainView() {
		
		return mainView;
	}

	public static void setMainView( MainView mainView) {
		
		GlanetRunner.mainView = mainView;
	}

	public static String[] getArgs() {
		
		return args;
	}

	public static void setArgs( String args[]) {
		
		GlanetRunner.args = new String[args.length];
		for( int i = 0; i < args.length; i++) 
			GlanetRunner.args[i] = args[i];
	}
}
