package ui;

import javax.swing.JPanel;

import ui.MainView.MainViewDelegate;

import common.Commons;

public class MainViewController extends ViewController implements MainViewDelegate {
	
	private MainView mainView;
	private Thread runnerThread;
	private GlanetRunner runner;
	
	public MainViewController( JPanel contentPanel) {
		super(contentPanel);
		
		loadView();
	}

	@Override
	public void loadView() {
		
		if( mainView != null){
			contentPanel.remove(mainView);
		}
		
		mainView = new MainView();
		mainView.setDelegate( this);
		contentPanel.add(mainView);
	}

	@Override
	public void presentViewController(ViewController viewController) {

		contentPanel.removeAll();
		contentPanel.invalidate();
		viewController.loadView();
		contentPanel.revalidate();
		
	}

	@Override
	public void dismissViewController() {
		contentPanel.removeAll();
		contentPanel.add(mainView);
	}

	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2]	--->	Given Interval Input File Data Format	
	//			--->	default	Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE	
	//args[3]	--->	Annotation, overlap definition, number of bases, default 1
	//args[4]	--->	Enrichment parameter
	//			--->	default	Commons.DO_ENRICH
	//			--->			Commons.DO_NOT_ENRICH	
	//args[5]	--->	Generate Random Data Mode
	//			--->	default	Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	//			--->			Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT	
	//args[6]	--->	multiple testing parameter, enriched elements will be decided and sorted with respect to this parameter
	//			--->	default Commons.BENJAMINI_HOCHBERG_FDR
	//			--->			Commons.BONFERRONI_CORRECTION
	//args[7]	--->	Bonferroni Correction Significance Level, default 0.05
	//args[8]	--->	Bonferroni Correction Significance Criteria, default 0.05
	//args[9]	--->	Number of permutations, default 5000
	//args[10]	--->	Dnase Enrichment
	//			--->	default Commons.DO_NOT_DNASE_ENRICHMENT
	//			--->	Commons.DO_DNASE_ENRICHMENT
	//args[11]	--->	Histone Enrichment
	//			--->	default	Commons.DO_NOT_HISTONE_ENRICHMENT
	//			--->			Commons.DO_HISTONE_ENRICHMENT
	//args[12]	--->	Transcription Factor(TF) Enrichment 
	//			--->	default	Commons.DO_NOT_TF_ENRICHMENT
	//			--->			Commons.DO_TF_ENRICHMENT
	//args[13]	--->	KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_KEGGPATHWAY_ENRICHMENT
	//args[14]	--->	TF and KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	//args[15]	--->	TF and CellLine and KeggPathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	//args[16]	--->	RSAT parameter
	//			--->	default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//			--->			Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//args[17]	--->	job name example: ocd_gwas_snps 
	//args[18]	--->	writeGeneratedRandomDataMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	//			--->			Commons.WRITE_GENERATED_RANDOM_DATA
	//args[19]	--->	writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//args[20]	--->	writePermutationBasedAnnotationResultMode checkBox
	//			---> 	default	Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//args[21]  --->    number of permutations in each run. Default is 2000
	//args[22]  --->	User Defined GeneSet Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT
	//							Commons.DO_USER_DEFINED_GENESET_ENRICHMENT
	//args[23]	--->	User Defined GeneSet InputFile 
	//args[24]	--->	User Defined GeneSet GeneInformationType
	//					default Commons.GENE_ID
	//							Commons.GENE_SYMBOL
	//							Commons.RNA_NUCLEOTIDE_ACCESSION
	//args[25]	--->	User Defined GeneSet Name such as "GO"
	//					default Commons.NO_DESCRIPTION
	//args[26]	--->	Optional UserDefinedGeneSet Description InputFile
	//					default Commons.NO_OPTIONAL_USERDEFINEDGENESET_DESCRIPTION_FILE_PROVIDED
	//args[27]  --->	User Defined Library Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT
	//						 	Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT
	//args[28]  --->	User Defined Library InputFile
	//args[29]	--->	User Defined Library DataFormat
	//					default	Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//							Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//							Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//							Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//args[30] - args[args.length-1]  --->	Note that the selected cell lines are
	//					always inserted at the end of the args array because it's size
	//					is not fixed. So for not (until the next change on args array) the selected cell
	//					lines can be reached starting from 22th index up until (args.length-1)th index.
	//					If no cell line selected so the args.length-1 will be 22-1 = 21. So it will never
	//					give an out of boundry exception in a for loop with this approach.
	@Override
	public void startRunActionsWithOptions( String inputFileName, 
			   String outputFolder,
			   String inputFileFormat,
			   String numberOfBases,
			   String enrichmentEnabled,
			   String generateRandomDataMode,
			   String multipleTestingChoice,
			   String bonferoniCorrectionSignificanceLevel,
			   String falseDiscoveryRate,
			   String numberOfPermutations,
			   String dnaseEnrichment,
			   String histoneEnrichment,
			   String tfEnrihment,
			   String keggPathwayEnrichment,
			   String tfAndKeggPathwayEnrichment,
			   String cellLineBasedTfAndKeggPathwayEnrichment,
			   String regulatorySequenceAnalysisUsingRSAT,
			   String jobName,
			   String writeGeneratedRandomDataMode,
			   String writePermutationBasedandParametricBasedAnnotationResultMode,
			   String writePermutationBasedAnnotationResultMode,
			   String numberOfPermutationsInEachRun,
			   String userDefinedGeneSetEnrichment,
			   String userDefinedGeneSetInputFile,
			   String userDefinedGeneSetGeneInformation,
			   String userDefinedGeneSetName,
			   String userDefinedGeneSetDescription,
			   String userDefinedLibraryEnrichment,
			   String userDefinedLibraryInputFile,
			   String userDefinedLibraryDataFormat,
			   String[] cellLinesToBeConsidered) {
		
		int i = 0;
		String[] args = new String[Commons.NUMBER_OF_PROGRAM_RUNTIME_ARGUMENTS+cellLinesToBeConsidered.length];
		
		args[i++] = inputFileName;
		args[i++] = outputFolder;
		args[i++] = inputFileFormat;
		args[i++] = numberOfBases;
		args[i++] = enrichmentEnabled;
		args[i++] = generateRandomDataMode;
		args[i++] = multipleTestingChoice;
		args[i++] = bonferoniCorrectionSignificanceLevel;
		args[i++] = falseDiscoveryRate;
		args[i++] = numberOfPermutations;
		args[i++] = dnaseEnrichment;
		args[i++] = histoneEnrichment;
		args[i++] = tfEnrihment;
		args[i++] = keggPathwayEnrichment;
		args[i++] = tfAndKeggPathwayEnrichment;
		args[i++] = cellLineBasedTfAndKeggPathwayEnrichment;
		args[i++] = regulatorySequenceAnalysisUsingRSAT;
		args[i++] = jobName;
		args[i++] = writeGeneratedRandomDataMode;
		args[i++] = writePermutationBasedandParametricBasedAnnotationResultMode;
		args[i++] = writePermutationBasedAnnotationResultMode;
		args[i++] = numberOfPermutationsInEachRun;
		args[i++] = userDefinedGeneSetEnrichment;
		args[i++] = userDefinedGeneSetInputFile;
		args[i++] = userDefinedGeneSetGeneInformation;
		args[i++] = userDefinedGeneSetName;
		args[i++] = userDefinedGeneSetDescription;
		args[i++] = userDefinedLibraryEnrichment;
		args[i++] = userDefinedLibraryInputFile;
		args[i++] = userDefinedLibraryDataFormat;
		
		//for( i = 0; i < args.length; i++)
		//	System.out.println( args[i]);
		
		//filling the rest with selected cell lines. 
		for( i = Commons.NUMBER_OF_PROGRAM_RUNTIME_ARGUMENTS; i < args.length; i++)
			args[i] = cellLinesToBeConsidered[i-Commons.NUMBER_OF_PROGRAM_RUNTIME_ARGUMENTS];
		
		runner = new GlanetRunner();
		
		GlanetRunner.setArgs( args);
		GlanetRunner.setMainView( mainView);
		
		runnerThread = new Thread(runner);
		runnerThread.start();
	}
	
	@Override
	public void stopCurrentProcess() {
		
		runnerThread.interrupt();
	}
}