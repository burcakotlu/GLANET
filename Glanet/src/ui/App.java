package ui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Commons;

/**
 * GLANET GUI Application
 *
 */
public class App 
{
	public static JFrame mainFrame;
	private static JPanel mainPanel;
	public static ViewController initialViewController;
	
	public static void setInitialViewController( ViewController viewController){		
		initialViewController = viewController;
	}
	
	public static void loadWindow(){
		
		//Initialize frame
		JFrame frame = new JFrame("GLANET");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setResizable(true);
        
        mainPanel = new JPanel();
        frame.getContentPane().add( mainPanel);
        
        setInitialViewController( new MainViewController(mainPanel));
        
        frame.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		super.windowClosing(e);
        	}
		});
        
        frame.setVisible(true);
        frame.pack();
        frame.repaint();
	}
	
	//it reads args and tries to find the values that are
	//defined in constants in a order. This order is
	//defined in MainViewController as a comment just above
	//startRunActionsWithOptions(...) method. This order
	//is the convention for the rest of the program. Therefore,
	//fillArgumentsInOrder(..) tries to find the specific values for
	//each of the index of argsInOrder[] array. It returns true if everything is
	//okay. It returns false, if the list of the arguments is wrong
	//(e.g. same argument is specified for multiple times, or a required
	//argument is not specified such as glanet folder location)
	static boolean fillArgumentsInOrder( String[] args, String[] argsInOrder){
		
		//Initializing argsInOrder array.
		String notSet = "notSetNull";
		for( int i = 0; i < argsInOrder.length; i++)
			argsInOrder[i] = notSet;
		
		//parsing input file location
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_INPUT_FILE))
				if( argsInOrder[0].equals( notSet))
					argsInOrder[0] = args[i+1];
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[0].equals( notSet)){
			
			System.out.println( "Input file name not specified, exiting...");
			return false;
		}
		
		//parsing glanet folder location
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_GLANET_FOLDER))
				if( argsInOrder[1].equals( notSet))
					argsInOrder[1] = args[i+1];
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[1].equals( notSet)){
			
			System.out.println( "Glanet folder not specified, exiting...");
			return false;
		}
		
		//parsing input file format
		for( int i = 0; i < args.length; i++){
			if( args[i].equalsIgnoreCase(Commons.ARG_INPUT_FORMAT_1_BASED))
				if( argsInOrder[2].equals( notSet))
					argsInOrder[2] = Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			else if( args[i].equalsIgnoreCase(Commons.ARG_INPUT_FORMAT_0_BASED))
				if( argsInOrder[2].equals( notSet))
					argsInOrder[2] = Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			else if( args[i].equalsIgnoreCase(Commons.ARG_INPUT_FORMAT_BED))
				if( argsInOrder[2].equals( notSet))
					argsInOrder[2] = Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			else if( args[i].equalsIgnoreCase(Commons.ARG_INPUT_FORMAT_GFF))
				if( argsInOrder[2].equals( notSet))
					argsInOrder[2] = Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		}
		
		if( argsInOrder[2].equals( notSet)){
			
			System.out.println( "Input file format not specified, exiting...");
			return false;
		}
		
		//parsing number of bases value
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_NUMBER_OF_BASES))
				if( argsInOrder[3].equals( notSet))
					argsInOrder[3] = args[i+1];
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[3].equals( notSet))
			//default value
			argsInOrder[3] = "1";
		
		//parsing perform enrichment check
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_PERFORM_ENRICHMENT))
				if( argsInOrder[4].equals( notSet))
					argsInOrder[4] = Commons.DO_ENRICH;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[4].equals( notSet))
			argsInOrder[4] = Commons.DO_NOT_ENRICH;
		
		//if enrichment enabled...
		if( argsInOrder[4].equals( Commons.DO_ENRICH)){
			
			//parsing generate random data mode enrichment
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase(Commons.ARG_GENERATE_RANDOM_DATA_WITH_GC_AND_MAP))
					if( argsInOrder[5].equals( notSet))
						argsInOrder[5] = Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
					else {
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
				else if( args[i].equalsIgnoreCase(Commons.ARG_GENERATE_RANDOM_DATA_WITHOUT_GC_AND_MAP))
					if( argsInOrder[5].equals( notSet))
						argsInOrder[5] = Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
					else {
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
			
			//parsing multiple testing parameter
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase(Commons.ARG_MULTIPLE_TESTING_BENJAMINI))
					if( argsInOrder[6].equals( notSet))
						argsInOrder[6] = Commons.BENJAMINI_HOCHBERG_FDR;
					else {
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
				else if( args[i].equalsIgnoreCase(Commons.ARG_MULTIPLE_TESTING_BONFERRONI))
					if( argsInOrder[6].equals( notSet))
						argsInOrder[6] = Commons.BONFERRONI_CORRECTION;
					else {
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
			
			//Parsing Bonferroni Correction Significance Level
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase(Commons.ARG_FALSE_DISCOVERY_RATE))
					if( argsInOrder[7].equals( notSet))
						argsInOrder[7] = args[i+1];
					else {
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
			
			//parsing Bonferroni Correction Significance Criteria
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase(Commons.ARG_SIGNIFICANCE_CRITERIA))
					if( argsInOrder[8].equals( notSet))
						argsInOrder[8] = args[i+1];
					else {
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
			
			//parsing Number of permutations
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase(Commons.ARG_NUMBER_OF_PERMUTATIONS))
					if( argsInOrder[9].equals( notSet))
						argsInOrder[9] = args[i+1];
					else {
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
			
			//parsing Number of permutations
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase(Commons.ARG_NUMBER_OF_PERMUTATIONS_IN_EACH_RUN))
					if( argsInOrder[21].equals( notSet))
						argsInOrder[21] = args[i+1];
					else {
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
		}
		
		if( argsInOrder[5].equals( notSet))
			//default value
			argsInOrder[5] = Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
		
		if( argsInOrder[6].equals( notSet))
			//default value
			argsInOrder[6] = Commons.BENJAMINI_HOCHBERG_FDR;
		
		if( argsInOrder[7].equals( notSet))
			//default value
			argsInOrder[7] = "0.05";
		
		if( argsInOrder[8].equals( notSet))
			//default value
			argsInOrder[8] = "0.05";
		
		if( argsInOrder[9].equals( notSet))
			//default value
			argsInOrder[9] = "5000";
		
		if( argsInOrder[21].equals( notSet))
			//default value
			argsInOrder[21] = "2000";
		
		//parsing Dnase Enrichment
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_DNASE_ANNOTATION))
				if( argsInOrder[10].equals( notSet))
					argsInOrder[10] = Commons.DO_DNASE_ENRICHMENT;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[10].equals( notSet))
			//default value
			argsInOrder[10] = Commons.DO_NOT_DNASE_ENRICHMENT;
		
		//parsing Histone Enrichment
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_HISTONE_ANNOTATIONS))
				if( argsInOrder[11].equals( notSet))
					argsInOrder[11] = Commons.DO_HISTONE_ENRICHMENT;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[11].equals( notSet))
			//default value
			argsInOrder[11] = Commons.DO_NOT_HISTONE_ENRICHMENT;
		
		//parsing Transcription Factor(TF) Enrichment
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_TF_ANNOTATION))
				if( argsInOrder[12].equals( notSet))
					argsInOrder[12] = Commons.DO_TF_ENRICHMENT;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[12].equals( notSet))
			//default value
			argsInOrder[12] = Commons.DO_NOT_TF_ENRICHMENT;
		
		//parsing KEGG Pathway Enrichment
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_KEGG_ANNOTATION))
				if( argsInOrder[13].equals( notSet))
					argsInOrder[13] = Commons.DO_KEGGPATHWAY_ENRICHMENT;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[13].equals( notSet))
			//default value
			argsInOrder[13] = Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT;
		
		//parsing TF and KEGG Pathway Enrichment
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_TF_AND_KEGG_ANNOTATION))
				if( argsInOrder[14].equals( notSet))
					argsInOrder[14] = Commons.DO_TF_KEGGPATHWAY_ENRICHMENT;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[14].equals( notSet))
			//default value
			argsInOrder[14] = Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT;
				
		//parsing TF and CellLine and KeggPathway Enrichment
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_CELL_TF_AND_KEGG_ANNOTATION))
				if( argsInOrder[15].equals( notSet))
					argsInOrder[15] = Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[15].equals( notSet))
			//default value
			argsInOrder[15] = Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT;
		
		//parsing RSAT option
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_RSAT) &&
					(argsInOrder[12].equals( Commons.DO_TF_ENRICHMENT) ||
					argsInOrder[14].equals(Commons.DO_TF_KEGGPATHWAY_ENRICHMENT) ||
					argsInOrder[15].equals(Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT)))
				if( argsInOrder[16].equals( notSet))
					argsInOrder[16] = Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[16].equals( notSet))
			//default value
			argsInOrder[16] = Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT;
		
		//parsing job name
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_JOB_NAME))
				if( argsInOrder[17].equals( notSet))
					argsInOrder[17] = args[i+1];
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[17].equals( notSet)){
			
			System.out.println( "Job name not specified, exiting...");
			return false;
		}
		
		argsInOrder[18] = Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA;
		argsInOrder[19] = Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT;
		argsInOrder[20] = Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT;
		
		//parsing user defined geneset
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_USER_DEFINED_GENESET_ANNOTATION))
				if( argsInOrder[22].equals( notSet))
					argsInOrder[22] = Commons.DO_USER_DEFINED_GENESET_ENRICHMENT;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[22].equals( notSet))
			//default value
			argsInOrder[22] = Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT;
		
		//parsing user defined library
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase(Commons.ARG_USER_DEFINED_LIBRARY_ANNOTATION))
				if( argsInOrder[23].equals( notSet))
					argsInOrder[23] = Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT;
				else {
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( argsInOrder[23].equals( notSet))
			//default value
			argsInOrder[23] = Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT;
		
		return true;
	}
	
	static boolean isCommandLineEnabled( String[] args){
		
		for( int i = 0; i < args.length; i++)
			if( args[i].startsWith( Commons.ARG_IS_COMMAND_LINE_ENABLED))
				return true;
		
		return false;
	}
	
	//args contains all arguments including memory arguments, jar files and class paths
	//argsForGlanetRunner contains only the arguments required in the GUI
	public static void main(String args[]) {
		
		if( args.length > 0 && isCommandLineEnabled( args)){
			
			String[] argsForGlanetRunner = new String[Commons.NUMBER_OF_PROGRAM_RUNTIME_ARGUMENTS];
			
			if( !fillArgumentsInOrder( args, argsForGlanetRunner))
				return;
			
			for( int i = 0; i < argsForGlanetRunner.length; i++)
				System.out.println( argsForGlanetRunner[i]);
			
			GlanetRunner.setMainView( null);
    		GlanetRunner.setArgs( argsForGlanetRunner);
    		
    		GlanetRunner runner = new GlanetRunner();
    		new Thread( runner).start();
		}else if( !GraphicsEnvironment.isHeadless())
    		loadWindow();
	}
}
