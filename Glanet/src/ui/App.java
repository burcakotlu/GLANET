package ui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Commons;

import enumtypes.CommandLineArguments;

/**
 * GLANET GUI Application
 *
 */
public class App {

	public static JFrame mainFrame;
	private static JPanel mainPanel;
	public static ViewController initialViewController;
	private static String notSet = "notSetNull";

	public static void setInitialViewController( ViewController viewController) {

		initialViewController = viewController;
	}

	public static void loadWindow() {

		// Initialize frame
		JFrame frame = new JFrame( "GLANET");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize( new Dimension( 1024, 768));
		frame.setPreferredSize( new Dimension( 1024, 768));
		frame.setResizable( true);

		mainPanel = new JPanel();
		frame.getContentPane().add( mainPanel);

		setInitialViewController( new MainViewController( mainPanel));

		frame.addWindowListener( new WindowAdapter() {

			@Override
			public void windowClosing( WindowEvent e) {

				super.windowClosing( e);
			}
		});
		
		frame.addComponentListener( new ComponentAdapter() {
			
			@Override
			public void componentResized( ComponentEvent event){
				
				initialViewController.frameSizeChanged(frame.getWidth(), frame.getHeight());
			}
		});


		frame.setVisible( true);
		frame.pack();
		frame.repaint();
	}

	static boolean setWithDefaultValueIfNotSet( String[] args, CommandLineArguments argument) {

		if( args[argument.value()].equals( notSet)){

			if( argument.defaultValue() == null){

				System.out.println( argument + " not specified, exiting...");
				return false;
			}

			args[argument.value()] = argument.defaultValue();
		}
		return true;
	}

	// it reads args and tries to find the values that are
	// defined in constants in a order. This order is
	// defined in CommandLineArguments.java as a comment just above
	// the enum class. This order
	// is the convention for the rest of the program. Therefore,
	// fillArgumentsInOrder(..) tries to find the specific values in
	// each of the index of argsInOrder[] array. It returns true if everything
	// is
	// okay. It returns false, if the list of the arguments is wrong
	// (e.g. same argument is specified for multiple times, or a required
	// argument is not specified such as glanet folder location)
	static boolean fillArgumentsInOrder( String[] args, String[] argsInOrder) {

		// Initializing argsInOrder array
		for( int i = 0; i < argsInOrder.length; i++)
			argsInOrder[i] = notSet;

		// parsing input file location
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_INPUT_FILE)){
				if( argsInOrder[CommandLineArguments.InputFileNameWithFolder.value()].equals( notSet)){

					argsInOrder[CommandLineArguments.InputFileNameWithFolder.value()] = args[i + 1];
				}else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.InputFileNameWithFolder))
			return false;
		
		// parsing num of threads
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_NUM_OF_THREADS)){
				if( argsInOrder[CommandLineArguments.NumberOfThreads.value()].equals( notSet)){

					argsInOrder[CommandLineArguments.NumberOfThreads.value()] = args[i + 1];
				}else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.NumberOfThreads))
			return false;
		
		/***************************************************************************************/
		/**********************************LOG FILE MODE****************************************/
		/***************************************************************************************/
		//Parsing Log File Mode
		for( int i = 0; i < args.length; i++){
			
			if(args[i].equalsIgnoreCase( Commons.ARG_LOG_FILE)){
				if( argsInOrder[CommandLineArguments.LogFile.value()].equals( notSet))
					argsInOrder[CommandLineArguments.LogFile.value()] = Commons.ARG_LOG_FILE;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
			
			else if (args[i].equalsIgnoreCase(Commons.ARG_NO_LOG_FILE)){
				if( argsInOrder[CommandLineArguments.LogFile.value()].equals( notSet))
					argsInOrder[CommandLineArguments.LogFile.value()] = Commons.ARG_NO_LOG_FILE;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
			
		}//End of FOR Parsing Log File Mode
		
		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.LogFile))
			return false;
		/***************************************************************************************/
		/**********************************LOG FILE MODE****************************************/
		/***************************************************************************************/

		
		/***************************************************************************************/
		/**********************************GLANET RUN MODE**************************************/
		/***************************************************************************************/
		//Parsing GLANET Run Mode
		for( int i = 0; i < args.length; i++){
			if( args[i].equalsIgnoreCase( Commons.ARG_GLANET_NORMAL_RUN)){
				if( argsInOrder[CommandLineArguments.GLANETRun.value()].equals( notSet))
					argsInOrder[CommandLineArguments.GLANETRun.value()] = Commons.GLANET_NORMAL_RUN;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
			
			else if( args[i].equalsIgnoreCase( Commons.ARG_GLANET_EXPERIMENT_RUN)){
				if( argsInOrder[CommandLineArguments.GLANETRun.value()].equals( notSet))
					argsInOrder[CommandLineArguments.GLANETRun.value()] = Commons.GLANET_COMMANDLINE_DATADRIVENEXPERIMENT_RUN;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
			
		}//End of FOR Parsing GLANET Run Mode
		
		
		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.GLANETRun))
			return false;
		/***************************************************************************************/
		/**********************************GLANET RUN MODE**************************************/
		/***************************************************************************************/
		

		/***************************************************************************************/
		/*****************************GIVEN INPUT DATA TYPE starts******************************/
		/***************************************************************************************/
		//Parsing Given Input Data Type
		for( int i = 0; i < args.length; i++){
			if( args[i].equalsIgnoreCase( Commons.ARG_GIVEN_INPUT_HAS_SNPTS)){
				if( argsInOrder[CommandLineArguments.GivenInputDataType.value()].equals( notSet))
					argsInOrder[CommandLineArguments.GivenInputDataType.value()] = Commons.GIVEN_INPUT_DATA_CONSISTS_OF_SNPS;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
			
			else if( args[i].equalsIgnoreCase( Commons.ARG_GIVEN_INPUT_HAS_MIXED_LENGTH_INTERVAL)){
				if( argsInOrder[CommandLineArguments.GivenInputDataType.value()].equals( notSet))
					argsInOrder[CommandLineArguments.GivenInputDataType.value()] = Commons.GIVEN_INPUT_DATA_CONSISTS_OF_MIXED_LENGTH_INTERVALS;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
			
		}//End of FOR Parsing Given Input Data Type
		
		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.GivenInputDataType))
			return false;	
		/***************************************************************************************/
		/*****************************GIVEN INPUT DATA TYPE ends********************************/
		/***************************************************************************************/
	
		
		
		/***************************************************************************************/
		/**********************************ISOCHORE FAMILY MODE starts**************************/
		/***************************************************************************************/
		//Parsing Isochore Family Mode
		for( int i = 0; i < args.length; i++){
			if( args[i].equalsIgnoreCase( Commons.ARG_GENERATE_RANDOM_DATA_WITH_ISOCHORE_FAMILY_POOLS)){
				if( argsInOrder[CommandLineArguments.IsochoreFamilyMode.value()].equals( notSet))
					argsInOrder[CommandLineArguments.IsochoreFamilyMode.value()] = Commons.DO_USE_ISOCHORE_FAMILY;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
			
			else if( args[i].equalsIgnoreCase( Commons.ARG_GENERATE_RANDOM_DATA_WITHOUT_ISOCHORE_FAMILY_POOLS)){
				if( argsInOrder[CommandLineArguments.IsochoreFamilyMode.value()].equals( notSet))
					argsInOrder[CommandLineArguments.IsochoreFamilyMode.value()] = Commons.DO_NOT_USE_ISOCHORE_FAMILY;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
			
		}//End of FOR Parsing GLANET Run Mode
		
		
		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.IsochoreFamilyMode))
			return false;
		/***************************************************************************************/
		/**********************************ISOCHORE FAMILY MODE ends****************************/
		/***************************************************************************************/

		
		// parsing glanet folder location
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_GLANET_FOLDER)){
				if( argsInOrder[CommandLineArguments.GlanetFolder.value()].equals( notSet)){

					argsInOrder[CommandLineArguments.GlanetFolder.value()] = args[i + 1];
				}else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.GlanetFolder))
			return false;

		// parsing input file format
		for( int i = 0; i < args.length; i++){
			if( args[i].equalsIgnoreCase( Commons.ARG_INPUT_FORMAT_1_BASED)){
				if( argsInOrder[CommandLineArguments.InputFileDataFormat.value()].equals( notSet))
					argsInOrder[CommandLineArguments.InputFileDataFormat.value()] = Commons.INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}else if( args[i].equalsIgnoreCase( Commons.ARG_INPUT_FORMAT_0_BASED)){
				if( argsInOrder[CommandLineArguments.InputFileDataFormat.value()].equals( notSet))
					argsInOrder[CommandLineArguments.InputFileDataFormat.value()] = Commons.INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}else if( args[i].equalsIgnoreCase( Commons.ARG_INPUT_FORMAT_BED)){
				if( argsInOrder[CommandLineArguments.InputFileDataFormat.value()].equals( notSet))
					argsInOrder[CommandLineArguments.InputFileDataFormat.value()] = Commons.INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}else if( args[i].equalsIgnoreCase( Commons.ARG_INPUT_FORMAT_GFF)){
				if( argsInOrder[CommandLineArguments.InputFileDataFormat.value()].equals( notSet))
					argsInOrder[CommandLineArguments.InputFileDataFormat.value()] = Commons.INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}else if( args[i].equalsIgnoreCase( Commons.ARG_INPUT_FORMAT_DBSNP)){

				if( argsInOrder[CommandLineArguments.InputFileDataFormat.value()].equals( notSet))
					argsInOrder[CommandLineArguments.InputFileDataFormat.value()] = Commons.INPUT_FILE_FORMAT_DBSNP_IDS;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
		}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.InputFileDataFormat))
			return false;

		// parsing input assembly
		for( int i = 0; i < args.length; i++){

			if( args[i].equalsIgnoreCase( Commons.ARG_ASSEMBLY_FORMAT_HG_19)){

				if( argsInOrder[CommandLineArguments.InputFileAssembly.value()].equals( notSet))
					argsInOrder[CommandLineArguments.InputFileAssembly.value()] = Commons.GRCH37_P13;
				else{

					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}else if( args[i].equalsIgnoreCase( Commons.ARG_ASSEMBLY_FORMAT_HG_38)){

				if( argsInOrder[CommandLineArguments.InputFileAssembly.value()].equals( notSet))
					argsInOrder[CommandLineArguments.InputFileAssembly.value()] = Commons.GRCH38;
				else{

					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
		}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.InputFileAssembly))
			return false;

		//parsing assocationmeasuretype
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_NUMBER_OF_OVERLAPPING_BASES)){
				if( argsInOrder[CommandLineArguments.AssociationMeasureType.value()].equals( notSet))
					argsInOrder[CommandLineArguments.AssociationMeasureType.value()] = Commons.NUMBER_OF_OVERLAPPING_BASES;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}else if( args[i].equalsIgnoreCase( Commons.ARG_EXISTENCE_OF_OVERLAP)){
				if( argsInOrder[CommandLineArguments.AssociationMeasureType.value()].equals( notSet))
					argsInOrder[CommandLineArguments.AssociationMeasureType.value()] = Commons.EXISTENCE_OF_OVERLAP;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
		
		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.AssociationMeasureType))
			return false;
		
		// parsing number of bases value
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_NUMBER_OF_BASES)){
				if( argsInOrder[CommandLineArguments.NumberOfBasesRequiredForOverlap.value()].equals( notSet))
					argsInOrder[CommandLineArguments.NumberOfBasesRequiredForOverlap.value()] = args[i + 1];
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.NumberOfBasesRequiredForOverlap))
			return false;
		else{
			if( argsInOrder[CommandLineArguments.AssociationMeasureType.value()] == Commons.EXISTENCE_OF_OVERLAP && 
				Integer.parseInt(argsInOrder[CommandLineArguments.NumberOfBasesRequiredForOverlap.value()]) < 1){
//				setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.NumberOfBasesRequiredForOverlap);
				System.out.println("Overlap definition must be at least 1 base or more");
				return false;
			}
		}

		// parsing Dnase Annotation
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_DNASE_ANNOTATION)){
				if( argsInOrder[CommandLineArguments.DnaseAnnotation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.DnaseAnnotation.value()] = Commons.DO_DNASE_ANNOTATION;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.DnaseAnnotation))
			return false;

		// parsing Gene Annotation
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_GENE_ANNOTATION)){
				if( argsInOrder[CommandLineArguments.GeneAnnotation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.GeneAnnotation.value()] = Commons.DO_GENE_ANNOTATION;

				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.GeneAnnotation))
			return false;

		// parsing Histone Annotation
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_HISTONE_ANNOTATIONS))
				if( argsInOrder[CommandLineArguments.HistoneAnnotation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.HistoneAnnotation.value()] = Commons.DO_HISTONE_ANNOTATION;

				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.HistoneAnnotation))
			return false;

		// parsing Transcription Factor(TF) Annotation
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_TF_ANNOTATION)){
				if( argsInOrder[CommandLineArguments.TfAnnotation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.TfAnnotation.value()] = Commons.DO_TF_ANNOTATION;

				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.TfAnnotation))
			return false;

		// parsing KEGG Pathway Annotation
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_KEGG_ANNOTATION)){
				if( argsInOrder[CommandLineArguments.KeggPathwayAnnotation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.KeggPathwayAnnotation.value()] = Commons.DO_KEGGPATHWAY_ANNOTATION;

				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.KeggPathwayAnnotation))
			return false;

		// parsing TF and KEGG Pathway Annotation
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_TF_AND_KEGG_ANNOTATION)){
				if( argsInOrder[CommandLineArguments.TfAndKeggPathwayAnnotation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.TfAndKeggPathwayAnnotation.value()] = Commons.DO_TF_KEGGPATHWAY_ANNOTATION;

				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.TfAndKeggPathwayAnnotation))
			return false;

		// parsing TF and CellLine and KeggPathway Annotation
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_CELL_TF_AND_KEGG_ANNOTATION)){
				if( argsInOrder[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()] = Commons.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;

				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation))
			return false;

		// parsing user defined geneset
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_GENESET_ANNOTATION)){
				if( argsInOrder[CommandLineArguments.UserDefinedGeneSetAnnotation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedGeneSetAnnotation.value()] = Commons.DO_USER_DEFINED_GENESET_ANNOTATION;

				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.UserDefinedGeneSetAnnotation))
			return false;

		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_GENESET_ANNOTATION_INPUT)){
				if( argsInOrder[CommandLineArguments.UserDefinedGeneSetInput.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedGeneSetInput.value()] = args[i + 1];
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.UserDefinedGeneSetInput) && argsInOrder[CommandLineArguments.UserDefinedGeneSetAnnotation.value()].equalsIgnoreCase( Commons.DO_USER_DEFINED_GENESET_ANNOTATION)){

			// System.out.println( CommandLineArguments.UserDefinedGeneSetInput
			// + " not specified, exiting...");
			return false;
		}else if( argsInOrder[CommandLineArguments.UserDefinedGeneSetAnnotation.value()].equalsIgnoreCase( Commons.DO_USER_DEFINED_GENESET_ANNOTATION) && argsInOrder[CommandLineArguments.UserDefinedGeneSetInput.value()].equalsIgnoreCase( Commons.NO_OPTIONAL_USERDEFINEDGENESET_FILE_PROVIDED)){

			System.out.println( CommandLineArguments.UserDefinedGeneSetInput + " not specified, exiting...");
			return false;
		}

		for( int i = 0; i < args.length; i++){
			if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_GENESET_ANNOTATION_GENE_INFORMATION_GENE_ID))
				if( argsInOrder[CommandLineArguments.UserDefinedGeneSetGeneInformation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedGeneSetGeneInformation.value()] = Commons.GENE_ID;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			else if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_GENESET_ANNOTATION_GENE_INFORMATION_GENE_SYMBOL))
				if( argsInOrder[CommandLineArguments.UserDefinedGeneSetGeneInformation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedGeneSetGeneInformation.value()] = Commons.GENE_SYMBOL;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			else if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_GENESET_ANNOTATION_GENE_INFORMATION_RNA_NUCLEOTIDE_ACCESSION))
				if( argsInOrder[CommandLineArguments.UserDefinedGeneSetGeneInformation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedGeneSetGeneInformation.value()] = Commons.RNA_NUCLEOTIDE_ACCESSION;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.UserDefinedGeneSetGeneInformation))
			return false;

		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_GENESET_ANNOTATION_GENESET_NAME))
				if( argsInOrder[CommandLineArguments.UserDefinedGeneSetName.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedGeneSetName.value()] = args[i + 1];
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.UserDefinedGeneSetName))
			return false;

		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_GENESET_ANNOTATION_DESCRIPTION_FILE))
				if( argsInOrder[CommandLineArguments.UserDefinedGeneSetDescriptionFile.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedGeneSetDescriptionFile.value()] = args[i + 1];
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.UserDefinedGeneSetDescriptionFile))
			return false;

		// parsing user defined library
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_LIBRARY_ANNOTATION))
				if( argsInOrder[CommandLineArguments.UserDefinedLibraryAnnotation.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedLibraryAnnotation.value()] = Commons.DO_USER_DEFINED_LIBRARY_ANNOTATION;

				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.UserDefinedLibraryAnnotation))
			return false;

		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_LIBRARY_ANNOTATION_INPUT))
				if( argsInOrder[CommandLineArguments.UserDefinedLibraryInput.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedLibraryInput.value()] = args[i + 1];
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.UserDefinedLibraryInput) && argsInOrder[CommandLineArguments.UserDefinedLibraryAnnotation.value()].equalsIgnoreCase( Commons.DO_USER_DEFINED_LIBRARY_ANNOTATION)){

			// System.out.println( CommandLineArguments.UserDefinedLibraryInput
			// + " not specified, exiting...");
			return false;

		}else if( argsInOrder[CommandLineArguments.UserDefinedLibraryAnnotation.value()].equalsIgnoreCase( Commons.DO_USER_DEFINED_LIBRARY_ANNOTATION) && argsInOrder[CommandLineArguments.UserDefinedLibraryInput.value()].equalsIgnoreCase( Commons.NO_OPTIONAL_USERDEFINEDLIBRARY_FILE_PROVIDED)){

			System.out.println( CommandLineArguments.UserDefinedLibraryInput + " not specified, exiting...");
			return false;
		}

		for( int i = 0; i < args.length; i++){
			if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_LIBRARY_ANNOTATION_DATA_FORMAT_0_EXLUSIVE))
				if( argsInOrder[CommandLineArguments.UserDefinedLibraryDataFormat.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedLibraryDataFormat.value()] = Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_START_ENDEXCLUSIVE_COORDINATES;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}

			if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_LIBRARY_ANNOTATION_DATA_FORMAT_0_INCLUSIVE))
				if( argsInOrder[CommandLineArguments.UserDefinedLibraryDataFormat.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedLibraryDataFormat.value()] = Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_START_ENDINCLUSIVE_COORDINATES;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}

			if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_LIBRARY_ANNOTATION_DATA_FORMAT_1_EXCLUSIVE))
				if( argsInOrder[CommandLineArguments.UserDefinedLibraryDataFormat.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedLibraryDataFormat.value()] = Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_START_ENDEXCLUSIVE_COORDINATES;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}

			if( args[i].equalsIgnoreCase( Commons.ARG_USER_DEFINED_LIBRARY_ANNOTATION_DATA_FORMAT_1_INCLUSIVE))
				if( argsInOrder[CommandLineArguments.UserDefinedLibraryDataFormat.value()].equals( notSet))
					argsInOrder[CommandLineArguments.UserDefinedLibraryDataFormat.value()] = Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_START_ENDINCLUSIVE_COORDINATES;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.UserDefinedLibraryDataFormat))
			return false;

		// parsing perform enrichment check
		if( argsInOrder[CommandLineArguments.DnaseAnnotation.value()].equalsIgnoreCase( Commons.DO_DNASE_ANNOTATION) || argsInOrder[CommandLineArguments.HistoneAnnotation.value()].equalsIgnoreCase( Commons.DO_HISTONE_ANNOTATION) || argsInOrder[CommandLineArguments.TfAnnotation.value()].equalsIgnoreCase( Commons.DO_TF_ANNOTATION) || argsInOrder[CommandLineArguments.KeggPathwayAnnotation.value()].equalsIgnoreCase( Commons.DO_KEGGPATHWAY_ANNOTATION) || argsInOrder[CommandLineArguments.TfAndKeggPathwayAnnotation.value()].equalsIgnoreCase( Commons.DO_TF_KEGGPATHWAY_ANNOTATION) || argsInOrder[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()].equalsIgnoreCase( Commons.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION) || argsInOrder[CommandLineArguments.UserDefinedGeneSetAnnotation.value()].equalsIgnoreCase( Commons.DO_USER_DEFINED_GENESET_ANNOTATION) || argsInOrder[CommandLineArguments.UserDefinedLibraryAnnotation.value()].equalsIgnoreCase( Commons.DO_USER_DEFINED_LIBRARY_ANNOTATION)){

			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase( Commons.ARG_PERFORM_ENRICHMENT))
					if( argsInOrder[CommandLineArguments.PerformEnrichment.value()].equals( notSet))
						argsInOrder[CommandLineArguments.PerformEnrichment.value()] = Commons.DO_ENRICH;
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
		}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.PerformEnrichment))
			return false;

		/***************************************************************************************/
		/*******************************ENRICHMENT WITH ZSCORES*********************************/
		/**********************************OR WITHOUT ZSCORES***********************************/
		/***************************************************************************************/
		if( argsInOrder[CommandLineArguments.PerformEnrichment.value()].equalsIgnoreCase( Commons.DO_ENRICH))
			// parsing perform enrichment with zScores
			for( int i = 0; i < args.length; i++){
				
				if( args[i].equalsIgnoreCase( Commons.ARG_PERFORM_ENRICHMENT_WITH_ZSCORES)){
					if( argsInOrder[CommandLineArguments.EnrichmentZScoreMode.value()].equals( notSet))
						argsInOrder[CommandLineArguments.EnrichmentZScoreMode.value()] = Commons.PERFORM_ENRICHMENT_WITH_ZSCORE;
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
				}else if ( args[i].equalsIgnoreCase( Commons.ARG_PERFORM_ENRICHMENT_WITHOUT_ZSCORES)){
					if( argsInOrder[CommandLineArguments.EnrichmentZScoreMode.value()].equals( notSet))
						argsInOrder[CommandLineArguments.EnrichmentZScoreMode.value()] = Commons.PERFORM_ENRICHMENT_WITHOUT_ZSCORE;
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
				}
				
			}//End of FOR parsing Enrichment ZScore decision

		if( !setWithDefaultValueIfNotSet( argsInOrder,CommandLineArguments.EnrichmentZScoreMode))
			return false;
		/***************************************************************************************/
		/*******************************ENRICHMENT WITH ZSCORES*********************************/
		/**********************************OR WITHOUT ZSCORES***********************************/
		/***************************************************************************************/


		if( argsInOrder[CommandLineArguments.PerformEnrichment.value()].equalsIgnoreCase( Commons.DO_ENRICH))
			// parsing generate random data mode enrichment
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase( Commons.ARG_GENERATE_RANDOM_DATA_WITH_GC_AND_MAP))
					if( argsInOrder[CommandLineArguments.GenerateRandomDataMode.value()].equals( notSet))
						argsInOrder[CommandLineArguments.GenerateRandomDataMode.value()] = Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
				else if( args[i].equalsIgnoreCase( Commons.ARG_GENERATE_RANDOM_DATA_WITHOUT_GC_AND_MAP))
					if( argsInOrder[CommandLineArguments.GenerateRandomDataMode.value()].equals( notSet))
						argsInOrder[CommandLineArguments.GenerateRandomDataMode.value()] = Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.GenerateRandomDataMode))
			return false;

		if( argsInOrder[CommandLineArguments.PerformEnrichment.value()].equalsIgnoreCase( Commons.DO_ENRICH))
			// parsing multiple testing parameter
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase( Commons.ARG_MULTIPLE_TESTING_BENJAMINI))
					if( argsInOrder[CommandLineArguments.MultipleTesting.value()].equals( notSet))
						argsInOrder[CommandLineArguments.MultipleTesting.value()] = Commons.BENJAMINI_HOCHBERG_FDR;
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}

				else if( args[i].equalsIgnoreCase( Commons.ARG_MULTIPLE_TESTING_BONFERRONI))
					if( argsInOrder[CommandLineArguments.MultipleTesting.value()].equals( notSet))
						argsInOrder[CommandLineArguments.MultipleTesting.value()] = Commons.BONFERRONI_CORRECTION;
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.MultipleTesting))
			return false;

		if( argsInOrder[CommandLineArguments.PerformEnrichment.value()].equalsIgnoreCase( Commons.DO_ENRICH))
			// parsing Bonferroni Correction Significance Criteria
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase( Commons.ARG_SIGNIFICANCE_CRITERIA))
					if( argsInOrder[CommandLineArguments.BonferroniCorrectionSignificanceCriteria.value()].equals( notSet))
						argsInOrder[CommandLineArguments.BonferroniCorrectionSignificanceCriteria.value()] = args[i + 1];
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.BonferroniCorrectionSignificanceCriteria))
			return false;

		if( argsInOrder[CommandLineArguments.PerformEnrichment.value()].equalsIgnoreCase( Commons.DO_ENRICH))
			// Benjamini Hochberg False Discovery Rate
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase( Commons.ARG_FALSE_DISCOVERY_RATE))
					if( argsInOrder[CommandLineArguments.FalseDiscoveryRate.value()].equals( notSet))
						argsInOrder[CommandLineArguments.FalseDiscoveryRate.value()] = args[i + 1];
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.FalseDiscoveryRate))
			return false;

		if( argsInOrder[CommandLineArguments.PerformEnrichment.value()].equalsIgnoreCase( Commons.DO_ENRICH)){

			// parsing Number of permutations
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase( Commons.ARG_NUMBER_OF_PERMUTATIONS))
					if( argsInOrder[CommandLineArguments.NumberOfPermutation.value()].equals( notSet))
						argsInOrder[CommandLineArguments.NumberOfPermutation.value()] = args[i + 1];
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
		}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.NumberOfPermutation))
			return false;

		if( argsInOrder[CommandLineArguments.PerformEnrichment.value()].equalsIgnoreCase( Commons.DO_ENRICH))
			// parsing Number of permutations in each run
			for( int i = 0; i < args.length; i++)

				if( args[i].equalsIgnoreCase( Commons.ARG_NUMBER_OF_PERMUTATIONS_IN_EACH_RUN))
					if( argsInOrder[CommandLineArguments.NumberOfPermutationsInEachRun.value()].equals( notSet))
						argsInOrder[CommandLineArguments.NumberOfPermutationsInEachRun.value()] = args[i + 1];
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.NumberOfPermutationsInEachRun))
			return false;

		if( argsInOrder[CommandLineArguments.TfAnnotation.value()].equals( Commons.DO_TF_ANNOTATION) || argsInOrder[CommandLineArguments.TfAndKeggPathwayAnnotation.value()].equals( Commons.DO_TF_KEGGPATHWAY_ANNOTATION) || argsInOrder[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()].equals( Commons.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION)){

			// parsing RSAT option
			for( int i = 0; i < args.length; i++)
				if( args[i].equalsIgnoreCase( Commons.ARG_RSAT))
					if( argsInOrder[CommandLineArguments.RegulatorySequenceAnalysisUsingRSAT.value()].equals( notSet))
						argsInOrder[CommandLineArguments.RegulatorySequenceAnalysisUsingRSAT.value()] = Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT;
					else{
						System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
						return false;
					}
		}

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.RegulatorySequenceAnalysisUsingRSAT))
			return false;

		// parsing job name
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_JOB_NAME))
				if( argsInOrder[CommandLineArguments.JobName.value()].equals( notSet))
					argsInOrder[CommandLineArguments.JobName.value()] = args[i + 1];
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		// parsing output folder
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_OUTPUT_RESULTS_FOLDER)){
				if( argsInOrder[CommandLineArguments.OutputFolder.value()].equals( notSet))
					argsInOrder[CommandLineArguments.OutputFolder.value()] = args[i + 1];
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			}
		
		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_ANNOTATION_OUTPUT_ELEMENT))
				if( argsInOrder[CommandLineArguments.WriteAnnotationFoundOverlapsMode.value()].equals( notSet))
					argsInOrder[CommandLineArguments.WriteAnnotationFoundOverlapsMode.value()] = Commons.DO_WRITE_OVERLAPS_EACH_ONE_IN_SEPARATE_FILE_ELEMENT_BASED;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}

			else if( args[i].equalsIgnoreCase( Commons.ARG_ANNOTATION_OUTPUT_ELEMENT_TYPE))
				if( argsInOrder[CommandLineArguments.WriteAnnotationFoundOverlapsMode.value()].equals( notSet))
					argsInOrder[CommandLineArguments.WriteAnnotationFoundOverlapsMode.value()] = Commons.DO_WRITE_OVERLAPS_ALL_IN_ONE_FILE_ELEMENT_TYPE_BASED;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
			else if( args[i].equalsIgnoreCase( Commons.ARG_ANNOTATION_NO_OUTPUT))
				if( argsInOrder[CommandLineArguments.WriteAnnotationFoundOverlapsMode.value()].equals( notSet))
					argsInOrder[CommandLineArguments.WriteAnnotationFoundOverlapsMode.value()] = Commons.DO_NOT_WRITE_OVERLAPS_AT_ALL;
				else{
					System.out.println( "Same argument has already been defined. Conflict occured, exiting...");
					return false;
				}
		
		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.WriteAnnotationFoundOverlapsMode))
			return false;
		
		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.JobName))
			return false;

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.WriteGeneratedRandomDataMode))
			return false;

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.WritePermutationBasedandParametricBasedAnnotationResultMode))
			return false;

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.WritePermutationBasedAnnotationResultMode))
			return false;

		if( !setWithDefaultValueIfNotSet( argsInOrder, CommandLineArguments.WriteAnnotationBinaryMatrixMode))
			return false;
		
		if( argsInOrder[CommandLineArguments.OutputFolder.value()].equals( notSet))
			argsInOrder[CommandLineArguments.OutputFolder.value()] = argsInOrder[CommandLineArguments.GlanetFolder.value()] + Commons.OUTPUT + System.getProperty( "file.separator");
		
//		if( argsInOrder[CommandLineArguments.OutputFolder.value()].charAt(argsInOrder[CommandLineArguments.OutputFolder.value()].length()-1) != System.getProperty( "file.separator").toCharArray()[0])
//			argsInOrder[CommandLineArguments.OutputFolder.value()] = argsInOrder[CommandLineArguments.OutputFolder.value()] + System.getProperty( "file.separator");
		
		argsInOrder[CommandLineArguments.OutputFolder.value()] =  argsInOrder[CommandLineArguments.OutputFolder.value()] + System.getProperty( "file.separator") + argsInOrder[CommandLineArguments.JobName.value()] + System.getProperty("file.separator");

		return true;
	}

	static boolean isCommandLineEnabled( String[] args) {

		for( int i = 0; i < args.length; i++)
			if( args[i].equalsIgnoreCase( Commons.ARG_IS_COMMAND_LINE_ENABLED))
				return true;

		return false;
	}

	// args contains all arguments including memory arguments, jar files and
	// class paths
	// argsForGlanetRunner contains only the arguments required in the GUI
	public static void main( String args[]) throws URISyntaxException, IOException {
		
		if( args.length > 0 && isCommandLineEnabled( args)){

			// 1024 means 1024 bytes
			// If given memory is less than 1 GB then give error
			// 1 GB (1000000000 B) / 1 MB (1000000 B) = 1000
			if( ( Runtime.getRuntime().maxMemory() / 1024) / 1024 < Commons.MIN_HEAP_FOR_GLANET){

				System.out.println( "There is no enough available memory." + System.getProperty( "line.separator") + "Please see http://glanet.readthedocs.org/en/latest/ to specify minimum required memory. (Also see -Xmx)\nAborting...");
				return;
			}

			String[] argsForGlanetRunner = new String[CommandLineArguments.NumberOfArguments.value()];

			if( !fillArgumentsInOrder( args, argsForGlanetRunner))
				return;

//			 for (int i = 0; i < argsForGlanetRunner.length; i++)
//				 System.out.println(argsForGlanetRunner[i]);

			GlanetRunner.setMainView( null);
			GlanetRunner.setArgs( argsForGlanetRunner);
			
			GlanetRunner runner = new GlanetRunner();
			new Thread( runner).start();

		}else if( !GraphicsEnvironment.isHeadless()){
			// if( (Runtime.getRuntime().maxMemory()/1024)/1024 <
			// MIN_HEAP_FOR_GLANET){
			//
			// String pathToJar =
			// App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			// System.out.println(pathToJar);
			// ProcessBuilder pb = new ProcessBuilder("java", "-Xms4096M",
			// "-Xmx8192M", "-classpath", pathToJar, "ui.App");
			// pb.start();
			// }else
			loadWindow();
		}
	}
}
