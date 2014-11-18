package ui;

import enrichment.Enrichment;
import enrichment.CollectionofPermutationsResults;
import giveninputdata.InputDataProcess;
import giveninputdata.InputDataRemoveOverlaps;
import giveninputdata.Preparation;
import jaxbxjctool.GenerationofSequencesandMatricesforGivenIntervals;
import rsat.RSATMatrixScanClient;
import annotation.Annotation;
import augmentation.results.AugmentationofEnrichmentWithAnnotation;
import augmentation.results.CreationofRemapInputFileswith0BasedStart1BasedEndGRCh37Coordinates;
import common.Commons;

public class GlanetRunner implements Runnable{
	
	private static String args[];
	private static MainView mainView;

	@Override
	public void run(){
		
		/************************Preparation starts********************************************/
		if( getMainView() != null)
			getMainView().setCurrentProcessInfo( "Preparation...");
		
		if( Thread.currentThread().isInterrupted())
			return;
		Preparation.main( args);
		/************************Preparation ends**********************************************/
		
		
		/************************InputDataProcess starts***************************************/
		if( getMainView() != null)
			getMainView().setCurrentProcessInfo( "InputDataProcess...");
		
		if( Thread.currentThread().isInterrupted())
			return;
		InputDataProcess.main( args);
		/************************InputDataProcess ends*****************************************/


		/************************RemoveOverlaps starts******************************************/
		if( getMainView() != null)
			getMainView().setCurrentProcessInfo( "RemoveOverlaps...");
		
		if( Thread.currentThread().isInterrupted())
			return;
		InputDataRemoveOverlaps.main( args);
		/************************RemoveOverlaps ends********************************************/

		
//		/* In case of Enrichment remove overlaps and merge */
//		/* In case of only Annotation with Enrichment, do not remove overlaps and do not merge*/
//		if( getArgs()[4].equalsIgnoreCase(Commons.DO_ENRICH)){
//			
//			if( getMainView() != null)
//				getMainView().setCurrentProcessInfo( "RemoveOverlaps...");
//			
//			if( Thread.currentThread().isInterrupted())
//				return;
//			InputDataRemoveOverlaps.main( args);
//		}
		
		/************************Annotation starts***********************************************/
		if( getMainView() != null)
			getMainView().setCurrentProcessInfo( "Annotate Given Input Data...");
		
		if( Thread.currentThread().isInterrupted())
			return;
		Annotation.main( args);
		/************************Annotation ends*************************************************/
		
		/************************Enrichment starts***********************************************/
		if( getArgs()[4].equalsIgnoreCase(Commons.DO_ENRICH)){
			
			/************************Annotate Permutations starts****************************/
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "Annotate Permutations for Enrichment...");
			
			if( Thread.currentThread().isInterrupted())
				return;
			Enrichment.main( args);
			/************************Annotate Permutations ends******************************/

			
			/*******************Collection of Permutations Results starts*******************/
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "Collection of Permutations Results...");
			
			if( Thread.currentThread().isInterrupted())
				return;
			CollectionofPermutationsResults.main( args);
			/*******************Collection of Permutations Results ends*********************/

			
			/************Augmentation of Enriched Elements with Given Input Data starts*****/
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "Augmentation of Enriched Elements with Given Input Data...");
			
			if( Thread.currentThread().isInterrupted())
				return;
			AugmentationofEnrichmentWithAnnotation.main( args);
			/************Augmentation of Enriched Elements with Given Input Data ends*******/
						
			
			/************Creation of NCBI Remap input files starts*************************/
			if( getMainView() != null)
				getMainView().setCurrentProcessInfo( "Creation of NCBI Remap input files...");
			
			if( Thread.currentThread().isInterrupted())
				return;
			CreationofRemapInputFileswith0BasedStart1BasedEndGRCh37Coordinates.main( args);
			/************Creation of NCBI Remap input files ends***************************/

			
			/**********************************RSAT starts*********************************/
			if( getArgs()[16].equalsIgnoreCase(Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT)) {
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "GenerationofSequencesandMatricesforGivenIntervals...");
				
				if( Thread.currentThread().isInterrupted())
					return;
				GenerationofSequencesandMatricesforGivenIntervals.main( args);
				
				if( getMainView() != null)
					getMainView().setCurrentProcessInfo( "RSATMatrixScanClient...");
				
				if( Thread.currentThread().isInterrupted())
					return;
				RSATMatrixScanClient.main( args);
			}
			/**********************************RSAT ends***********************************/
			
		}
		/************************Enrichment ends*******************************************************/
		
		/************************GLANET execution ends*************************************************/
		//args[1]  already has file separator at the end
		if( getMainView() != null)
			getMainView().setCurrentProcessInfo( "GLANET execution has ended. You can reach results under " + args[1]  + "Output");
		if( getMainView() != null)
			getMainView().enableStartProcess( true);
		GlanetRunner.appendLog( "Execution has ended");
		/************************GLANET execution ends*************************************************/

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
