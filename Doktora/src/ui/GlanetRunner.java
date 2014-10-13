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

public class GlanetRunner implements Runnable{
	
	private String args[];
	private static MainView mainView;
	
	public void run(){
		
		for( int i = 0; i < args.length; i++)
		System.out.println(args[i]);
		
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "Preparation...");
			
			if( !Thread.currentThread().isInterrupted())
				Preparation.main( args);
			else
				return;
			
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "InputDataProcess...");
			
			if( !Thread.currentThread().isInterrupted())
				InputDataProcess.main( args);
			else return;
			
			/* In case of Enrichment remove overlaps and merge */
			/* In case of only Annotation with Enrichment, do not remove overlaps and do not merge*/
			if( getArgs()[4].equalsIgnoreCase(Commons.DO_ENRICH)){
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "RemoveOverlaps...");
				
				if( !Thread.currentThread().isInterrupted())
					InputDataRemoveOverlaps.main( args);
				else return;
			}
			
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "Annotate Given Input Data...");
			
			if( !Thread.currentThread().isInterrupted())
				AnnotateGivenIntervalsWithNumbersWithChoices.main( args);
			else return;
			
			if( getArgs()[4].equalsIgnoreCase(Commons.DO_ENRICH)){
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "Annotate Permutations for Enrichment...");
				
				if( !Thread.currentThread().isInterrupted())
					AnnotatePermutationsWithNumbersWithChoices.main( args);
				else return;
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "Collection of Permutations Results...");
				
				if( !Thread.currentThread().isInterrupted())
					CollectionofPermutationsResults.main( args);
				else return;
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "Augmentation of Enriched Elements with Given Input Data...");
				
				if( !Thread.currentThread().isInterrupted())
					AugmentationofEnrichedElementswithGivenInputData.main( args);
				else return;
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "Creation of NCBI Remap input files...");
				
				if( !Thread.currentThread().isInterrupted())
					CreationofRemapInputFileswith0BasedStart1BasedEndGRCh37Coordinates.main( args);
				else return;
		
				
				if( getArgs()[16].equalsIgnoreCase(Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT)) {
					
					if( getMainView() != null)
						getMainView().setCurrentProcessInfo( "GenerationofSequencesandMatricesforGivenIntervals...");
					
					if( !Thread.currentThread().isInterrupted())
						GenerationofSequencesandMatricesforGivenIntervals.main( args);
					else return;
					
					if( getMainView() != null)
						getMainView().setCurrentProcessInfo( "RSATMatrixScanClient...");
					
					if( !Thread.currentThread().isInterrupted())
						RSATMatrixScanClient.main( args);
					else return;
				}
			}
			
			//args[1]  already has file separator at the end
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "GLANET execution has ended. You can reach results under " + args[1]  + "Output");
			if( getMainView() != null)
				getMainView().enableStartProcess( true);
			GlanetRunner.appendLog( "Execution has ended");
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

	public String[] getArgs() {
		
		return args;
	}

	public void setArgs( String args[]) {
		
		args = new String[args.length];
		for( int i = 0; i < args.length; i++) 
			args[i] = args[i];
	}
}
