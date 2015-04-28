package common;

public class Commons {
	
	//Heap Size 
	public static final int MIN_HEAP_FOR_GLANET = 6500;
	
	//Number of processors
	public static final int NUMBER_OF_AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
	//Number of sequential task equal to the number of available processors is a good practice. 
	public static final int NUMBER_OF_ANNOTATE_RANDOM_DATA_TASK_DONE_IN_SEQUENTIALLY = Commons.NUMBER_OF_AVAILABLE_PROCESSORS;
	public static final int NUMBER_OF_GENERATE_RANDOM_DATA_TASK_DONE_IN_SEQUENTIALLY = Commons.NUMBER_OF_AVAILABLE_PROCESSORS;
	
	//Main Folders
	public static final String ANNOTATION = "Annotation";
	public static final String ANNOTATION_FOR_PERMUTATIONS = "AnnotationForPermutations";
	public static final String GIVENINPUTDATA = "GivenInputData";
	public static final String ENRICHMENT = "Enrichment";
	public static final String REGULATORY_SEQUENCE_ANALYSIS = "RegulatorySequenceAnalysis";
	public static final String FOR_RSA = Commons.REGULATORY_SEQUENCE_ANALYSIS + System.getProperty("file.separator") + "forRSA";
	public static final String ALL_POSSIBLE_NAMES = "AllPossibleNames";
	public static final String AUGMENTATION = "Augmentation";

	public static final String ENCODE_COLLABORATION = "ENCODE_Collaboration";
	public static final String ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE = "AnnotationBinaryMatrixForOnePhenotype";
	public static final String ANNOTATION_FOR_ONE_PHENOTYPE = "AnnotationForOnePhenotype";
	public static final String ANNOTATION_WITH_RS_IDS_ENRICHMENT_SUMMARY_TABLES_FOR_ONE_PHENOTYPE = "AnnotationWithRSIDsEnrichmentForOnePhenotype";
	public static final String ENRICHMENT_BINARY_MATRIX_FOR_ALL_PHENOTYPES = "EnrichmentBinaryMatrixForAllPhenotypes";

	public static final String BYGLANET = "byGLANET";
	public static final String LOCAL_DISK_G_GLANET_DATA = "G:" + System.getProperty("file.separator") + "GLANET_DATA" + System.getProperty("file.separator");
	public static final String ENCODE = "ENCODE";
	public static final String UCSCGENOME = "UCSCGENOME";
	public static final String NCBI = "NCBI";
	public static final String FTP = "FTP";
	public static final String NCBI_REMAP = "REMAP";
	
	//GLANET DATA Driven 
	public static final String RNA_SEQ_GM12878_K562 = "RNA_seq_GM12878_K562";
	public static final String Gm12878Rep1_genes_results = "Gm12878Rep1.genes.results";
	public static final String Gm12878Rep2_genes_results = "Gm12878Rep2.genes.results";
	public static final String female_gtf = "female.gtf";
	public static final String demo_input_data = "demo_input_data";
	
	public static final String OUTPUT = "Output";
	public static final String DATA = "Data";

	// Sub Folders, Internal Folders
	public static final String RESULTS = "Results";

	// When there is no name given for GLANET job
	public static final String NO_NAME = "NoName";

	// For Getting Enrichment Result File
	public static final String WRT = "wrt";

	public static final String SNP = "snp";
	public static final String SNPs = "SNPs";
	public static final String TF_PFM_AND_LOGO_Matrices = "TF_PFM_AND_LOGO_Matrices";
	
	public static final String LOGO_MATRICES = "LogoMatrices";
	public static final String PFM_MATRICES = "PfmMatrices";
	public static final String SNP_REFERENCE_SEQUENCE = "SNPReferenceSequence";
	public static final String SNP_ALTERED_SEQUENCE = "SNPAlteredSequence";
	public static final String TF_EXTENDED_PEAK_SEQUENCE = "TFExtendedPeakSequence";
	public static final String TF_OVERLAPS = "TFOverlaps";
	public static final String OBSERVED_ALLELES = "ObservedAlleles";
	
	
	//For measuring memory usage
	public static final int NUMBER_OF_BYTES_IN_A_MEGABYTE = 1048576;
	
	
	//Number of bases required for p1, p2, d
	public static final int P1_NUMBER_OF_BASES =  2000;
	public static final int P1_NUMBER_OF_BASES_PLUS_ONE =  Commons.P1_NUMBER_OF_BASES + 1;
	
	public static final int P2_NUMBER_OF_BASES = 10000;
	public static final int P2_NUMBER_OF_BASES_PLUS_ONE = Commons.P2_NUMBER_OF_BASES + 1;
	
	//100 KB
	public static final int D_NUMBER_OF_BASES = 100000;
	
	//1000 KB = 1 MB
	//public static final int D_NUMBER_OF_BASES = 1000000;
	

	// dbSNP rs Prefix
	public static final String RS = "rs";
	public static final String COMMA = ",";

	
	public static final String NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE = "NCBI_REMAP_API_Supported_Assemblies.txt";
	public static final String NCBI_REMAP_API_SUPPORTED_ASSEMBLYNAME_2_REFSEQASSEMBLYID_FILE = "NCBI_REMAP_API_Supported_AssemblyName_2_RefSeqAssemblyID.txt";

	
	public static final String NULL = "NULL";
	public static final String PRIMARYASSEMBLY = "Primary Assembly";
	public static final String PATCHES = "PATCHES";

	public static final String NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON = "on";
	public static final String NCBI_REMAP_API_MERGE_FRAGMENTS_OFF = "off";

	public static final String NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON = "on";
	public static final String NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_OFF = "off";

	public static final double NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_1 = 1.0;
	public static final double NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_ = 0.5;

	public static final int NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_1 = 1;
	public static final int NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2 = 2;
	// NCBI REMAP API PARAMETERS ends

	// When there is no KeggPathwayDescription provided for a KeggPathway
	// When there is no UserDefinedGeneSetDescription provided for a
	// UserDefinedGeneSety
	public static final String NO_DESCRIPTION_AVAILABLE = "NoDescriptionAvailable";

	// When there is no Optional UserDefinedGeneSet Description file is provided
	// for a UserDefinedGeneSet Enrichment
	public static final String NO_OPTIONAL_USERDEFINEDGENESET_DESCRIPTION_FILE_PROVIDED = "NO_OPTIONAL_USERDEFINEDGENESET_DESCRIPTION_FILE_PROVIDED";
	public static final String NO_OPTIONAL_USERDEFINEDGENESET_FILE_PROVIDED = "NoFile";
	public static final String NO_OPTIONAL_USERDEFINEDLIBRARY_FILE_PROVIDED = NO_OPTIONAL_USERDEFINEDGENESET_FILE_PROVIDED;
	// Number of program runtime arguments without counting the command line
	// argument
	// command line argument if exists will always be attached to the end of the
	// existing arguments
	// and it will increase the number of program runtime arguments by 1.
	public static final Integer NUMBER_OF_PROGRAM_RUNTIME_ARGUMENTS = 30;

	public static final String CHR = "chr";
	public static final String X = "X";
	public static final String Y = "Y";

	// Enum FileFormatType
	public static final String NARROWPEAK = "narrowpeak";
	public static final String BED = "bed";
	public static final String FILE_FORMAT_TYPE_OTHER = "fileFormatTypeOther";

	// EnumType UserDefinedGeneSetInputType
	public static final String GENE_ID = "GENE ID";
	public static final String RNA_NUCLEOTIDE_ACCESSION = "RNA NUCLEOTIDE ACCESSION";
	public static final String GENE_SYMBOL = "GENE SYMBOL";

	// EnumType AnnotationType
	public static final String DNASE_ANNOTATION = "DNASE_ANNOTATION";
	public static final String TF_ANNOTATION = "TF_ANNOTATION";
	public static final String HISTONE_ANNOTATION = "HISTONE_ANNOTATION";
	public static final String USER_DEFINED_GENE_SET_ANNOTATION = "USER_DEFINED_GENE_SET_ANNOTATION";
	public static final String USER_DEFINED_LIBRARY_ANNOTATION = "USER_DEFINED_LIBRARY_ANNOTATION";
	public static final String KEGG_PATHWAY_ANNOTATION = "KEGG_PATHWAY_ANNOTATION";
	public static final String TF_KEGG_PATHWAY_ANNOTATION = "TF_KEGG_PATHWAY_ANNOTATION";
	public static final String TF_CELLLINE_KEGG_PATHWAY_ANNOTATION = "TF_CELLLINE_KEGG_PATHWAY_ANNOTATION";
	public static final String BOTH_TF_KEGG_PATHWAY_AND_TF_CELLLINE_KEGG_PATHWAY_ANNOTATION = "BOTH_TF_KEGG_PATHWAY_AND_TF_CELLLINE_KEGG_PATHWAY_ANNOTATION";

	//EnumType WriteAnnotationBinaryMatrixMode
	public static final String DO_WRITE_ANNOTATION_BINARY_MATRIX = "DO_WRITE_ANNOTATION_BINARY_MATRIX";
	public static final String DO_NOT_WRITE_ANNOTATION_BINARY_MATRIX = "DO_NOT_WRITE_ANNOTATION_BINARY_MATRIX";
	
	//EnumType WriteElementBasedAnnotationFoundOverlapsMode
	public static final String DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS= "DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS";
	public static final String DO_NOT_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS = "DO_NOT_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS";

	// EnumType WriteGeneratedRandomDataMode
	public static final String DO_WRITE_GENERATED_RANDOM_DATA = "DO_WRITE_GENERATED_RANDOM_DATA";
	public static final String DO_NOT_WRITE_GENERATED_RANDOM_DATA = "DO_NOT_WRITE_GENERATED_RANDOM_DATA";

	// EnumType WritePermutationBasedandParametricBasedAnnotationResultMode
	public static final String DO_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT = "DO_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT";
	public static final String DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT = "DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT";

	// EnumType WritePermutationBasedAnnotationResultMode
	public static final String DO_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT = "DO_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT";
	public static final String DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT = "DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT";

	// Enum type GenerateRandomDataMode
	public static final String GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT = "Without GC and Mappability";
	public static final String GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT = "With GC and Mappability";

	// EnumType RegulatorySequenceAnalysisType
	// RSAT PARAMETER
	public static final String DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT = "DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT";
	public static final String DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT = "DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT";

	// EnumType NodeType
	// Is an interval tree node an original node or a merged node?
	public static final String ORIGINAL_NODE = "ORIGINAL_NODE";
	public static final String MERGED_NODE = "MERGED_NODE";

	// EnumType NodeName
	public static final String SENTINEL = "SENTINEL";
	public static final String NOT_SENTINEL = "NOT_SENTINEL";

	// EnumType MultipleTestingType
	// P Value type
	public static final String BONFERRONI_CORRECTION = "Bonferroni Correction";
	public static final String EMPIRICAL_P_VALUE = "Empirical p value";
	public static final String BENJAMINI_HOCHBERG_FDR = "Benjamini Hochberg FDR";

	// EnumType Interval Name
	public static final String EXON = "EXON";
	public static final String INTRON = "INTRON";
	public static final String FIVE_P_ONE = "5P1";
	public static final String FIVE_P_TWO = "5P2";
	public static final String FIVE_D = "5D";
	public static final String THREE_P_ONE = "3P1";
	public static final String THREE_P_TWO = "3P2";
	public static final String THREE_D = "3D";

	//Annotation
	public static final String ANNOTATION_IN_PARALEL = "ANNOTATION_IN_PARALEL";
	public static final String ANNOTATION_SEQUENTIALLY = "ANNOTATION_SEQUENTIALLY";
	
	//EnumType Annotation Type
	public static final String DO_DNASE_ANNOTATION = "DO_DNASE_ANNOTATION";
	public static final String DO_NOT_DNASE_ANNOTATION = "DO_NOT_DNASE_ANNOTATION";
	
	public static final String DO_HISTONE_ANNOTATION = "DO_HISTONE_ANNOTATION";
	public static final String DO_NOT_HISTONE_ANNOTATION = "DO_NOT_HISTONE_ANNOTATION";
	
	public static final String DO_TF_ANNOTATION = "DO_TF_ANNOTATION";
	public static final String DO_NOT_TF_ANNOTATION = "DO_NOT_TF_ANNOTATION";
	
	public static final String DO_GENE_ANNOTATION = "DO_GENE_ANNOTATION";
	public static final String DO_NOT_GENE_ANNOTATION = "DO_NOT_GENE_ANNOTATION";


	public static final String DO_KEGGPATHWAY_ANNOTATION = "DO_KEGGPATHWAY_ANNOTATION";
	public static final String DO_NOT_KEGGPATHWAY_ANNOTATION = "DO_NOT_KEGGPATHWAY_ANNOTATION";
	
	public static final String DO_TF_KEGGPATHWAY_ANNOTATION = "DO_TF_KEGGPATHWAY_ANNOTATION";
	public static final String DO_NOT_TF_KEGGPATHWAY_ANNOTATION = "DO_NOT_TF_KEGGPATHWAY_ANNOTATION";
	
	public static final String DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION = "DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION";
	public static final String DO_NOT_TF_CELLLINE_KEGGPATHWAY_ANNOTATION = "DO_NOT_TF_CELLLINE_KEGGPATHWAY_ANNOTATION";
	
	public static final String DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION = "DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION";

	public static final String DO_USER_DEFINED_GENESET_ANNOTATION = "DO_USER_DEFINED_GENESET_ANNOTATION";
	public static final String DO_NOT_USER_DEFINED_GENESET_ANNOTATION = "DO_NOT_USER_DEFINED_GENESET_ANNOTATION";
	
	public static final String DO_USER_DEFINED_LIBRARY_ANNOTATION = "DO_USER_DEFINED_LIBRARY_ANNOTATION";
	public static final String DO_NOT_USER_DEFINED_LIBRARY_ANNOTATION = "DO_NOT_USER_DEFINED_LIBRARY_ANNOTATION";

	// EnumType ChromosomeName
	public static final String CHROMOSOME1 = "chr1";
	public static final String CHROMOSOME2 = "chr2";
	public static final String CHROMOSOME3 = "chr3";
	public static final String CHROMOSOME4 = "chr4";
	public static final String CHROMOSOME5 = "chr5";
	public static final String CHROMOSOME6 = "chr6";
	public static final String CHROMOSOME7 = "chr7";
	public static final String CHROMOSOME8 = "chr8";
	public static final String CHROMOSOME9 = "chr9";
	public static final String CHROMOSOME10 = "chr10";
	public static final String CHROMOSOME11 = "chr11";
	public static final String CHROMOSOME12 = "chr12";
	public static final String CHROMOSOME13 = "chr13";
	public static final String CHROMOSOME14 = "chr14";
	public static final String CHROMOSOME15 = "chr15";
	public static final String CHROMOSOME16 = "chr16";
	public static final String CHROMOSOME17 = "chr17";
	public static final String CHROMOSOME18 = "chr18";
	public static final String CHROMOSOME19 = "chr19";
	public static final String CHROMOSOME20 = "chr20";
	public static final String CHROMOSOME21 = "chr21";
	public static final String CHROMOSOME22 = "chr22";
	public static final String CHROMOSOMEX = "chrX";
	public static final String CHROMOSOMEY = "chrY";
	public static final String CHROMOSOMEWITHDIFFERENTNAME = "chrWithDifferentName";

	// EnumType GivenInputData
	public static final String GIVEN_INPUT_DATA_CONSISTS_OF_SNPS = "GIVEN_INPUT_DATA_CONSISTS_OF_SNPS";
	public static final String GIVEN_INPUT_DATA_CONSISTS_OF_MIXED_LENGTH_INTERVALS = "GIVEN_INPUT_DATA_CONSISTS_OF_MIXED_LENGTH_INTERVALS";

	//EnumType Isochore Families
	public static final String L1 = "L1";
	public static final String L2 = "L2";
	public static final String H1 = "H1";
	public static final String H2 = "H2";
	public static final String H3 = "H3";
	
	//EnumType CalculateGC
	public static final String CALCULATE_GC_USING_GC_BYTE_LIST = "CALCULATE_GC_USING_GC_BYTE_LIST";
	public static final String CALCULATE_GC_USING_GC_INTERVAL_TREE = "CALCULATE_GC_USING_GC_INTERVAL_TREE";
	public static final String CALCULATE_GC_USING_GC_ISOCHORE_INTERVAL_TREE = "CALCULATE_GC_USING_GC_ISOCHORE_INTERVAL_TREE";
	
	public static final int VERY_SHORT_INTERVAL_LENGTH = 10;
	public static final int SHORT_INTERVAL_LENGTH = 500;
	
	/*******************************************************************************************/
	/************************ ChromosomeBased Given Input Files starts ***************************/
	/*******************************************************************************************/
	public static final String CHROMOSOME_BASED_GIVEN_INPUT = "_input_file.txt";
	/*******************************************************************************************/
	/************************ ChromosomeBased Given Input Files ends *****************************/
	/*******************************************************************************************/

	public static final String RSERVE = System.getProperty("user.home") + System.getProperty("file.separator") + "GLANET" + System.getProperty("file.separator") + "Rserve" + System.getProperty("file.separator");

	public static final Integer ORIGINAL_DATA_PERMUTATION_NUMBER = 0;
	public static final String PERMUTATION0 = "PERMUTATION0";

	public static final int HOMO_SAPIENS_TAX_ID = 9606;

	public static Integer ZERO = 0;
	public static Integer ONE = 1;
	public static Integer MINUS_ONE = -1;

	// public static int NUMBER_OF_PERMUTATIONS_IN_EACH_RUN = 2000;

	public static Float FLOAT_ZERO = 0.0f;
	public static Float FLOAT_TEN_QUADRILLION = 10000000000000000f;

	public static Long LONG_ZERO = (long) 0;
	public static Long LONG_ONE = (long) 1;

	public static final String GC = "GC";
	public static final String MAPABILITY = "MAPABILITY";

	public static final String RANDOM_DATA_GENERATION_LOG_FILE = "generate" + System.getProperty("file.separator") + "randomdata" + System.getProperty("file.separator") + "GenerateRandomDataLog.txt";

	// INPUT DATA PROCESS
	public static final String TEST_INPUT_DATA_DBSNP_IDS = "TEST_INPUT_DATA" + System.getProperty("file.separator") + "Test_dbSNP_ids.txt";

	// GIVEN INPUT DATA
	// INPUT FILE DATA FORMAT start
	public static final String INPUT_FILE_FORMAT_DBSNP_IDS = "dbSNP IDs";
	public static final String INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES = "BED";
	public static final String INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES = "GFF3";
	public static final String INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES = "0-based coordinates (End Inclusive)";
	public static final String INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES = "1-based coordinates (End Inclusive)";
	// INPUT FILE DATA FORMAT ends

	// USER DEFINED LIBRARY DATA FORMAT starts
	public static final String USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_START_ENDINCLUSIVE_COORDINATES = "0-based coordinates (End Inclusive)";
	public static final String USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_START_ENDEXCLUSIVE_COORDINATES = "0-based coordinates (End Exclusive)";
	public static final String USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_START_ENDINCLUSIVE_COORDINATES = "1-based coordinates (End Inclusive)";
	public static final String USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_START_ENDEXCLUSIVE_COORDINATES = "1-based coordinates (End Exclusive)";
	// USER DEFINED LIBRARY DATA FORMAT ends

	//Assembly Starts
	public static final String GRCH38 		= "GRCh38";
	public static final String GRCH37_P13 	= "GRCh37.p13";
	//Assembly Ends
		
	
	public static final int NUMBER_OF_RSIDS_SENT_IN_ONE_BATCH = 100;

	
	public static final String PROCESSED_INPUT_FILE_0BASED_START_END_GRCh37_p13 = "Input_Data_Processed_0Based_Start_End_GRCh37_p13_coordinates.txt";
	public static final String PROCESSED_INPUT_FILE_0BASED_START_END_GRCh38 = "Input_Data_Processed_0Based_Start_End_GRCh38_coordinates.txt";
	
	public static final String REMOVED_OVERLAPS_INPUT_FILE_0BASED_START_END_GRCh37_p13 = "Input_Data_Processed_OverlapsRemoved_0Based_Start_End_GRCh37_p13_coordinates.txt";
	public static final String REMOVED_OVERLAPS_INPUT_FILE_0BASED_START_END_GRCh38 = "Input_Data_Processed_OverlapsRemoved_0Based_Start_End_GRCh38_coordinates.txt";

	// FOR TESTING PURPOSES
	public static final String TEST_INPUT_FILE_BED_FORMAT = "";;
	public static final String TEST_INPUT_FILE_GFF3_FORMAT = "";
	public static final String TEST_INPUT_FILE_0_BASED_COORDINATES_FORMAT = "";
	public static final String TEST_INPUT_FILE_DBSNP_IDS_FORMAT = "";

	// RSID to CHRNAME CHRPOSITION OBSERVEDALLELES converter
	public static final String REFSEQ_IDS_FOR_GRCH37_INPUT_FILE = "RefSeqIdsforGRCh37" + System.getProperty("file.separator") + "GCF_000001405.25.assembly.txt";
	public static final String REFSEQ_IDS_FOR_GRCH38_INPUT_FILE = "RefSeqIdsforGRCh38" + System.getProperty("file.separator") + "GCF_000001405.26.assembly.txt";

	// dbSNP
	public static final String NUCLEOTIDE_A = "A";
	public static final String NUCLEOTIDE_C = "C";
	public static final String NUCLEOTIDE_G = "G";
	public static final String NUCLEOTIDE_T = "T";

	// Augmentation of Enriched Elements with Overlaps Output Files starts
	public static final String AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY = Commons.AUGMENTATION + System.getProperty("file.separator") + "EnrichedElementsWithGivenInputData" + System.getProperty("file.separator");

	/*************************************************************************************/
	/******************** AUGMENTATION OF ENRICHMENT WITH ANNOTATION ***********************/
	/******************************* GRCh37.p13 starts *************************************/
	/*************************************************************************************/
	public static final String AUGMENTED_DNASE_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedDnaseResults_1Based_Start_End_GRCh37_P13_coordinates.txt";
	public static final String AUGMENTED_HISTONE_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedHistoneResults_1Based_Start_End_GRCh37_P13_coordinates.txt";
	public static final String AUGMENTED_TF_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedTranscriptionFactorResults_1Based_Start_End_GRCh37_P13_coordinates.txt";

	public static final String AUGMENTED_EXON_BASED_USERDEFINED_GENESET_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedExonBasedUserDefinedGeneSetResults_1Based_Start_End_GRCh37_P13_coordinates.txt";
	public static final String AUGMENTED_REGULATION_BASED_USERDEFINED_GENESET_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedRegulationBasedUserDefinedGeneSetResults_1Based_Start_End_GRCh37_P13_coordinates.txt";
	public static final String AUGMENTED_ALL_BASED_USERDEFINED_GENESET_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedAllBasedUserDefinedGeneSetResults_1Based_Start_End_GRCh37_P13_coordinates.txt";

	public static final String AUGMENTED_USERDEFINED_LIBRARY_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = "_AugmentedUserDefinedLibrary_1Based_Start_End_GRCh37_P13_coordinates.txt";

	public static final String AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedExonBasedKeggPathwayResults_1Based_Start_End_GRCh37_P13_coordinates.txt";
	public static final String AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedRegulationBasedKeggPathwayResults_1Based_Start_End_GRCh37_P13_coordinates.txt";
	public static final String AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedAllBasedKeggPathwayResults_1Based_Start_End_GRCh37_P13_coordinates.txt";

	public static final String AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedTfExonBasedKeggPathwayResults_1Based_Start_End_GRCh37_P13_coordinates.txt";
	public static final String AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedTfRegulationBasedKeggPathwayResults_1Based_Start_End_GRCh37_P13_coordinates.txt";
	public static final String AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedTfAllBasedKeggPathwayResults_1Based_Start_End_GRCh37_P13_coordinates.txt";

	public static final String AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedTfCellLineExonBasedKeggPathwayResults_1Based_Start_End_GRCh37_P13_coordinates.txt";
	public static final String AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedTfCellLineRegulationBasedKeggPathwayResults_1Based_Start_End_GRCh37_P13_coordinates.txt";
	public static final String AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH37_P13_COORDINATES = Commons.AUGMENTATION_OF_ENRICHED_ELEMENTS_WITH_ANNOTATION_DIRECTORY + "AugmentedTfCellLineAllBasedKeggPathwayResults_1Based_Start_End_GRCh37_P13_coordinates.txt";
	/****************************************************************************************/
	/******************** AUGMENTATION OF ENRICHMENT WITH ANNOTATION ************************/
	/******************************* GRCh37.p13 ends ****************************************/
	/****************************************************************************************/

	
	public static final String THERE_IS_A_SITUATION = "There is a situation.";
	
	public static final String REMAP_DBSNP_IDS_COORDINATES_FROM_LATEST_ASSEMBLY_TO_GRCH37P13 = "REMAP_DBSNP_IDS_COORDINATES_FROM_LATEST_ASSEMBLY_TO_GRCH37P13";
	public static final String REMAP_GIVENINPUTDATA_FROM_GRCH38_TO_GRCH37P13 = "REMAP_GIVENINPUTDATA_FROM_GRCH38_TO_GRCH37P13";
	public static final String REMAP_ALL_TF_ANNOTATIONS_FROM_GRCh37p13_TO_GRCh38_FOR_REGULATORY_SEQUENCE_ANALYSIS = "REMAP_ALL_TF_ANNOTATIONS_FROM_GRCh37p13_TO_GRCh38_FOR_REGULATORY_SEQUENCE_ANALYSIS";
	
	/*************************************************************************************/
	/********************** REMAP OUTPUT FILE starts *************************************/
	/*************************************************************************************/
	public static final String REMAP_DUMMY_OUTPUT_FILE = "REMAP_dummy_outputFile.txt";
	public static final String REMAP_DUMMY_GENOME_WORKBENCH_PROJECT_FILE = "REMAP_dummy_genomeWorkbenchProjectFile.gbp";
	public static final String REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE = "remap_reportFile_chrName_1Based_Start_End_coordinates.xls";
	/*************************************************************************************/
	/********************** REMAP OUTPUT FILE ends ***************************************/
	/*************************************************************************************/

	
	
	/***************************************************************************************/
	/***************************REMAP dbSNP IDs case starts*********************************/
	/***************************************************************************************/
	public static final String HEADER_LINE_FOR_DBSNP_IDS_FROM_LATEST_ASSEMBLY_TO_GRCH37_P13="#dbSNP Ids are remapped from Latest Assembly to GRCh37_p13 (hg19).";
	
	public static final String RSID_CHRNAME_0Based_START_END_NCBI_EUTIL_RETURNED_LATEST_ASSEMBLY_FILE = "rsID_chrName_0Based_Start_End_NCBI_EUTIL_Returned_Latest_Assembly_coordinates.txt";
	public static final String RSID_ChrName1BasedStartEndLatestAssembly_ChrName1BasedStartEndGRCh37p13_FILE = "rsID_chrName1BasedStartEndLatestAssembly_chrName1BasedStartEndGRCh37p13_coordinates.txt";
	
	public static final String REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE = "remap_inputFile_oneGenomicLoci_perLine_chrName_0Based_Start_EndExclusive.bed";
	public static final String REMAP_OUTPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_1Based_START_END_BED_FILE_USING_REMAP_REPORT =  "remap_outputFile_oneGenomicLoci_perLine_chrName_1Based_Start_End_usingRemapReportExcelFile.txt";
	/***************************************************************************************/
	/***************************REMAP dbSNP IDs case ends***********************************/
	/***************************************************************************************/
	
	
	/***************************************************************************************/
	/*****************REMAP 0Based 1Based  BED GFF3 GRCh38 case starts**********************/
	/***************************************************************************************/
	public static final String HEADER_LINE_FOR_COORDINATES_IN_LATEST_ASSEMBLY = "#Given Input Data Coordinates in 1Based Start End in GRCh37.p13 (Remapped from GRCh38 (hg38)).";
	/***************************************************************************************/
	/*****************REMAP 0Based 1Based  BED GFF3 GRCh38 case ends************************/
	/***************************************************************************************/

	
	
	
	/***************************************************************************************/
	/***************************REMAP ALL TF Annotations case starts************************/
	/***************************************************************************************/
	// ONE BIG TF Annotation FILE for Regulatory Sequence Analysis
	public static final String HEADER_LINE_FOR_ALL_TF_ANNOTATIONS_IN_LATEST_ASSEMBLY="#All TF Annotations in 1Based Start End in Latest Assembly (Remapped from GRCh37_p13 (hg19)).";

	public static final String ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCh37_P13 = "All_TF_Annotations_1Based_Start_End_GRCh37_p13.txt";
	public static final String REMAP_INPUT_FILE_All_TF_ANNOTATIONS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES_BED_FILE = "All_TF_Annotations_RemapInput_0Based_Start_EndExclusive_GRCh37_P13_Coordinates.bed";
	public static final String ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCH38 = "All_TF_Annotations_1Based_Start_End_GRCh38.txt";
	/***************************************************************************************/
	/***************************REMAP ALL TF Annotations case ends**************************/
	/***************************************************************************************/

	// RSAT
	public static final String FOR_RSA_SNP_TF_SEQUENCES_MATRICES_DIRECTORY = FOR_RSA + System.getProperty("file.separator");
	public static final String REGULATORY_SEQUENCE_ANALYSIS_DIRECTORY = Commons.REGULATORY_SEQUENCE_ANALYSIS + System.getProperty("file.separator");
	public static final String RSA_RESULTS_FOR_ALL_ANNOTATED_TFS =  "RegulatorySequenceAnalysisResults.txt";

	public static final String RSAT_ORGANISM_Homo_sapiens_ensembl_74_GRCh37 = "Homo_sapiens_ensembl_74_GRCh37";
	public static final String RSAT_ORGANISM_Homo_sapiens_GRCh37 = "Homo_sapiens_GRCh37";
	public static final String RSAT_ORGANISM_Homo_sapiens_GRCh38 = "Homo_sapiens_GRCh38";
	
	public static final String RSAT_BACKGROUND_upstream_noorf = "upstream-noorf";
	public static final String RSAT_tmp_background_infile = "/home/rsat/rsat/public_html/data/genomes/Homo_sapiens_ensembl_74_GRCh37/oligo-frequencies/1nt_upstream-noorf_Homo_sapiens_ensembl_74_GRCh37-ovlp-1str.freq.gz";

	/*************************************************************************************/
	
	// TF KEGGPATHWAY DIRECTORY BASES
	public static final String TF_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE = FOR_RSA + System.getProperty("file.separator") + "TfExonBasedKEGG" + System.getProperty("file.separator");
	public static final String TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE = FOR_RSA + System.getProperty("file.separator") + "TfRegulationBasedKEGG" + System.getProperty("file.separator");
	public static final String TF_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE = FOR_RSA + System.getProperty("file.separator") + "TfAllBasedKEGG" + System.getProperty("file.separator");

	// TF CELLLINE KEGGPATHWAY DIRECTORY BASES
	public static final String TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE = FOR_RSA + System.getProperty("file.separator") + "TfCellLineExonBasedKEGG" + System.getProperty("file.separator");
	public static final String TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE = FOR_RSA + System.getProperty("file.separator") + "TfCellLineRegulationBasedKEGG" + System.getProperty("file.separator");
	public static final String TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE = FOR_RSA + System.getProperty("file.separator") + "TfCellLineAllBasedKEGG" + System.getProperty("file.separator");

	/*************************************************************************************/

	// Encode-motifs file
	public static final String ENCODE_MOTIFS = "encode_motifs" + System.getProperty("file.separator") + "motifs.txt";

	// Jaspar Core File
	public static final String JASPAR_CORE = "jaspar_core" + System.getProperty("file.separator") + "pfm_all.txt";

	public static final String JASPAR_CORE_MATRICES_FOR_LOGO = FOR_RSA + System.getProperty("file.separator") + "jaspar_core_logo_matrices.txt";

	public static final int ZERO_BASED_SNP_POSITION = 20;
	public static final int ONE_BASED_SNP_POSITION = ZERO_BASED_SNP_POSITION + 1;
	
	public static final int NUMBER_OF_BASES_BEFORE_SNP_POSITION = ZERO_BASED_SNP_POSITION;
	public static final int NUMBER_OF_BASES_AFTER_SNP_POSITION = ZERO_BASED_SNP_POSITION;
	

	public static final char SEQUENCE_DIRECTION_D = 'D';
	public static final char SEQUENCE_DIRECTION_R = 'R';

	// Case Study OCD_GWAS
	// Comparison of Binomial Test versus Permutation Test
	public static final String DNASE_ADJUSTED_P_VALUE_BINOMIAL_TEST = "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "dnase_adjusted_pvalues.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_10000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_10000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_5000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_5000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_1000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_1000Perm.txt";
	public static final String DNASE_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS = "Doktora" + System.getProperty("file.separator") + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "dnase_comparison_of_binomial_and_permutation_tests_OCD_GWAS.txt";

	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_BINOMIAL_TEST = "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "tfbs_adjusted_pvalues.txt";;
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_10000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_10000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_5000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_5000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_1000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_1000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS = "Doktora" + System.getProperty("file.separator") + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "tfbs_comparison_of_binomial_and_permutation_tests_OCD_GWAS.txt";

	public static final String HISTONE_ADJUSTED_P_VALUE_BINOMIAL_TEST = "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "histone_adjusted_pvalues.txt";;
	public static final String HISTONE_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_10000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_10000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_5000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_5000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_1000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_1000Perm.txt";
	public static final String HISTONE_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS = "Doktora" + System.getProperty("file.separator") + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "histone_comparison_of_binomial_and_permutation_tests_OCD_GWAS.txt";

	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_BINOMIAL_TEST = "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "exonBased_KeggPathway_adjusted_pvalues.txt";;
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_10000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_10000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_5000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_5000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_1000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_1000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS = "Doktora" + System.getProperty("file.separator") + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "exonBasedKeggPathway_comparison_of_binomial_and_permutation_tests_OCD_GWAS.txt";

	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_BINOMIAL_TEST = "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "regulationBased_KeggPathway_adjusted_pvalues.txt";;
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_10000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_10000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_5000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_5000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_1000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_1000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS = "Doktora" + System.getProperty("file.separator") + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "regulationBasedKeggPathway_comparison_of_binomial_and_permutation_tests_OCD_GWAS.txt";

	// Case Study Positive Control K562 GATA1
	// Comparison of Permutation Tests
	public static final String DNASE_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_10000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_10000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_5000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_5000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_1000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_1000Perm.txt";
	public static final String DNASE_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1 = "Doktora" + System.getProperty("file.separator") + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "dnase_comparison_of_permutation_tests_K562_GATA1.txt";

	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_10000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_10000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_5000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_5000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_1000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_1000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1 = "Doktora" + System.getProperty("file.separator") + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "tfbs_comparison_of_permutation_tests_K562_GATA1.txt";

	public static final String HISTONE_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_10000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_10000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_5000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_5000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_1000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_1000Perm.txt";
	public static final String HISTONE_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1 = "Doktora" + System.getProperty("file.separator") + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "histone_comparison_of_permutation_tests_K562_GATA1.txt";

	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_10000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_10000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_5000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_5000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_1000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_1000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1 = "Doktora" + System.getProperty("file.separator") + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "exonBasedKeggPathway_comparison_of_permutation_tests_K562_GATA1.txt";

	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_10000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_10000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_5000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_5000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_1000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_1000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1 = "Doktora" + System.getProperty("file.separator") + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "regulationBasedKeggPathway_comparison_of_permutation_tests_K562_GATA1.txt";

	// Mapability and GC
	public static final String WRITE_MEAN_VALUE_OF_EACH_FILE = "WRITE_MEAN_VALUE_OF_EACH_FILE";
	public static final String WRITE_STANDARD_DEVIATION_VALUE_OF_EACH_FILE = "WRITE_STANDARD_DEVIATION_VALUE_OF_EACH_FILE";

	// ALL FILES
	public static final String ALL_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_gc_files.txt";
	public static final String ALL_DNASE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_dnase_gc_files.txt";
	public static final String ALL_TFBS_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_tfbs_gc_files.txt";
	public static final String ALL_HISTONE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_histone_gc_files.txt";

	public static final String ALL_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_mapability_files.txt";
	public static final String ALL_DNASE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_dnase_mapability_files.txt";
	public static final String ALL_TFBS_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_tfbs_mapability_files.txt";
	public static final String ALL_HISTONE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_histone_mapability_files.txt";

	// Ten Different Mean Files
	public static final String TEN_DIFFERENT_MEAN_DNASE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_dnase_gc_files.txt";
	public static final String TEN_DIFFERENT_MEAN_TFBS_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_tfbs_gc_files.txt";
	public static final String TEN_DIFFERENT_MEAN_HISTONE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_histone_gc_files.txt";

	public static final String TEN_DIFFERENT_MEAN_DNASE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_dnase_mapability_files.txt";
	public static final String TEN_DIFFERENT_MEAN_TFBS_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_tfbs_mapability_files.txt";
	public static final String TEN_DIFFERENT_MEAN_HISTONE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_histone_mapability_files.txt";

	// TOP TEN MOST VARYING FILES
	public static final String TOP_TEN_MOST_VARYING_DNASE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_dnase_gc_files.txt";
	public static final String TOP_TEN_MOST_VARYING_TFBS_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_tfbs_gc_files.txt";
	public static final String TOP_TEN_MOST_VARYING_HISTONE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_histone_gc_files.txt";

	public static final String TOP_TEN_MOST_VARYING_DNASE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_dnase_mapability_files.txt";
	public static final String TOP_TEN_MOST_VARYING_TFBS_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_tfbs_mapability_files.txt";
	public static final String TOP_TEN_MOST_VARYING_HISTONE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_histone_mapability_files.txt";

	// Data Files for R for Ten Different Mean Files
	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_DNASE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanDnaseGCFiles.txt";
	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_TFBS_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanTfbsGCFiles.txt";
	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_HISTONE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanHistoneGCFiles.txt";

	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_DNASE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanDnaseMAPABILITYFiles.txt";
	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_TFBS_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanTfbsMAPABILITYFiles.txt";
	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_HISTONE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanHistoneMAPABILITYFiles.txt";

	// Top Ten Most Varying Dnase Tfbs Histone Mapability and GC files for Box
	// Plot in R
	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_DNASE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingDnaseGCFiles.txt";
	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_TFBS_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingTfbsGCFiles.txt";
	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_HISTONE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingHistoneGCFiles.txt";

	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_DNASE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingDnaseMapabilityFiles.txt";
	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_TFBS_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingTfbsMapabilityFiles.txt";
	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_HISTONE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingHistoneMapabilityFiles.txt";

	// All Dnase Tfbs Histone Mapability and GC files for Box Plot in R
	public static final String DATA_FILE_FOR_R_ALL_DNASE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllDnaseGCFiles.txt";
	public static final String DATA_FILE_FOR_R_ALL_TFBS_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllTfbsGCFiles.txt";
	public static final String DATA_FILE_FOR_R_ALL_HISTONE_GC_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllHistoneGCFiles.txt";

	public static final String DATA_FILE_FOR_R_ALL_DNASE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllDnaseMapabilityFiles.txt";;
	public static final String DATA_FILE_FOR_R_ALL_TFBS_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllTfbsMapabilityFiles.txt";
	public static final String DATA_FILE_FOR_R_ALL_HISTONE_MAPABILITY_FILES = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllHistoneMapabilityFiles.txt";

	public static final String ALL_DNASE_GC_FILES_DIRECTORY = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Dnase" + System.getProperty("file.separator") + "Gc" + System.getProperty("file.separator");
	public static final String ALL_TFBS_GC_FILES_DIRECTORY = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Tfbs" + System.getProperty("file.separator") + "Gc" + System.getProperty("file.separator");
	public static final String ALL_HISTONE_GC_FILES_DIRECTORY = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Histone" + System.getProperty("file.separator") + "Gc" + System.getProperty("file.separator");

	public static final String ALL_DNASE_MAPABILITY_FILES_DIRECTORY = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Dnase" + System.getProperty("file.separator") + "Mapability" + System.getProperty("file.separator");
	public static final String ALL_TFBS_MAPABILITY_FILES_DIRECTORY = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Tfbs" + System.getProperty("file.separator") + "Mapability" + System.getProperty("file.separator");
	public static final String ALL_HISTONE_MAPABILITY_FILES_DIRECTORY = "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Histone" + System.getProperty("file.separator") + "Mapability" + System.getProperty("file.separator");

	
	
	// MAPABILITY
	public static final short  SHORT_0 = (short) 0;
	
	public static final short MAPABILITY_SHORT_TEN_THOUSAND = (short) 10000;
	
	//for debugging
	public static final byte BYTE_0 = (byte) 0;
	public static final byte MAPABILITY_BYTE_ONE_HUNDRED = (byte) 100;
	
	
	public static final String MAPABILITY_HG19_FILE_END = "_hg19_mapability.txt";
	
	public static final String WG_ENCODE_CRG_MAPABILITY_ALIGN_100_MER_WIG = "MAPABILITY" + System.getProperty("file.separator") + "wgEncodeCrgMapabilityAlign100mer.wig";
	public static final String WG_ENCODE_CRG_MAPABILITY_ALIGN_50_MER_WIG = "MAPABILITY" + System.getProperty("file.separator") + "wgEncodeCrgMapabilityAlign50mer.wig";
	public static final String MAPABILITY_HG19_CHR1_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr1_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR2_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr2_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR3_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr3_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR4_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr4_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR5_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr5_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR6_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr6_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR7_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr7_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR8_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr8_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR9_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr9_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR10_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr10_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR11_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr11_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR12_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr12_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR13_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr13_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR14_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr14_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR15_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr15_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR16_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr16_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR17_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr17_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR18_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr18_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR19_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr19_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR20_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr20_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR21_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr21_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHR22_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chr22_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHRX_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chrX_hg19_mapability.txt";
	public static final String MAPABILITY_HG19_CHRY_FILE = "MAPABILITY" + System.getProperty("file.separator") + "chrY_hg19_mapability.txt";

	
	
	// GC
	public static final String GC_INTERVAL_TREE_DATA = "IntervalTreeData";
	public static final String GC_ISOCHORE_INTERVAL_TREE_DATA = "IsochoreIntervalTreeData";
	public static final String GC_ISOCHORE_FAMILY_POOL_DATA = "IsochoreFamilyPoolData";

	public static final String GC_FILE_END = ".fa";
	public static final String GC_ISOCHORES_FILE_END = "_gc_isochores.txt";
	
	public static final String GC_ISOCHOREFAMILY_L1_POOL_FILE_END = "_gc_isochores_L1_pool.txt";
	public static final String GC_ISOCHOREFAMILY_L2_POOL_FILE_END = "_gc_isochores_L2_pool.txt";
	public static final String GC_ISOCHOREFAMILY_H1_POOL_FILE_END = "_gc_isochores_H1_pool.txt";
	public static final String GC_ISOCHOREFAMILY_H2_POOL_FILE_END = "_gc_isochores_H2_pool.txt";
	public static final String GC_ISOCHOREFAMILY_H3_POOL_FILE_END = "_gc_isochores_H3_pool.txt";
	
	public static final String GC_INTERVALS_FILE_END = "_gc_intervals.txt";
	public static final String GC_INTERVALS_CONSECUTIVE_ZEROS_MERGED_FILE_END = "_gc_intervals_consecutive_zeros_merged.txt";

	public static final String GC_HG19_CHR1_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr1.fa";
	public static final String GC_HG19_CHR2_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr2.fa";
	public static final String GC_HG19_CHR3_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr3.fa";
	public static final String GC_HG19_CHR4_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr4.fa";
	public static final String GC_HG19_CHR5_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr5.fa";
	public static final String GC_HG19_CHR6_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr6.fa";
	public static final String GC_HG19_CHR7_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr7.fa";
	public static final String GC_HG19_CHR8_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr8.fa";
	public static final String GC_HG19_CHR9_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr9.fa";
	public static final String GC_HG19_CHR10_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr10.fa";
	public static final String GC_HG19_CHR11_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr11.fa";
	public static final String GC_HG19_CHR12_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr12.fa";
	public static final String GC_HG19_CHR13_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr13.fa";
	public static final String GC_HG19_CHR14_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr14.fa";
	public static final String GC_HG19_CHR15_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr15.fa";
	public static final String GC_HG19_CHR16_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr16.fa";
	public static final String GC_HG19_CHR17_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr17.fa";
	public static final String GC_HG19_CHR18_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr18.fa";
	public static final String GC_HG19_CHR19_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr19.fa";
	public static final String GC_HG19_CHR20_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr20.fa";
	public static final String GC_HG19_CHR21_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr21.fa";
	public static final String GC_HG19_CHR22_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chr22.fa";
	public static final String GC_HG19_CHRX_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chrX.fa";
	public static final String GC_HG19_CHRY_FASTA_FILE = "GC" + System.getProperty("file.separator") + "chrY.fa";


	public static final char NUCLEIC_ACID_UPPER_CASE_A = 'A';
	public static final char NUCLEIC_ACID_LOWER_CASE_A = 'a';

	public static final char NUCLEIC_ACID_UPPER_CASE_G = 'G';
	public static final char NUCLEIC_ACID_LOWER_CASE_G = 'g';

	public static final char NUCLEIC_ACID_UPPER_CASE_C = 'C';
	public static final char NUCLEIC_ACID_LOWER_CASE_C = 'c';

	public static final char NUCLEIC_ACID_UPPER_CASE_T = 'T';
	public static final char NUCLEIC_ACID_LOWER_CASE_T = 't';

	public static final char NUCLEIC_ACID_UPPER_CASE_N = 'N';
	public static final char NUCLEIC_ACID_LOWER_CASE_N = 'n';
	
	//GC Interval Tree At Most Interval Length
	public static final int GC_INTERVALTREE_INTERVALLENGTH_100 = 100;
	public static final int GC_INTERVALTREE_INTERVALLENGTH_1000 = 1000;
	
	//GC ISOCHORES Moving Window Size is 100 KB
	public static final int GC_ISOCHORE_MOVING_WINDOW_SIZE = 100000;
	

	// Empirical P Value
	public static final float GC_THRESHOLD_LOWER_VALUE = (float) 0.01;
	public static final float GC_THRESHOLD_UPPER_VALUE = (float) 0.1;

	public static final float MAPABILITY_THRESHOLD_LOWER_VALUE = (float) 0.01;
	public static final float MAPABILITY_THRESHOLD_UPPER_VALUE = (float) 0.1;

	public static final float THRESHOLD_INCREASE_VALUE_0_POINT_005 = (float) 0.005;
	public static final float THRESHOLD_INCREASE_VALUE_0_POINT_010 = (float) 0.010;
	public static final float THRESHOLD_INCREASE_VALUE_0_POINT_015 = (float) 0.015;
	public static final float THRESHOLD_INCREASE_VALUE_0_POINT_020 = (float) 0.020;

	public static final int NUMBER_OF_TRIAL_FIRST_LEVEL = 500;
	public static final int NUMBER_OF_TRIAL_SECOND_LEVEL = 1000;
	public static final int NUMBER_OF_TRIAL_THIRD_LEVEL = 1500;
	public static final int NUMBER_OF_TRIAL_FOURTH_LEVEL = 2000;

	public static final String ORIGINAL_INPUT_DATA_FILE = "ORIGINAL_INPUT_DATA_FILE";

	public static final String PERMUTATION = "PERMUTATION";
	public static final String RANDOMLY_GENERATED_DATA_FOLDER = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + "RandomlyGeneratedData" + System.getProperty("file.separator");
	public static final String RANDOMLY_GENERATED_DATA = "RANDOMLY_GENERATED_DATA";

	// Input Data Prepare
	public static final String CHROMOSOME_POSITION_TYPE_ZERO_BASED = "CHROMOSOME_POSITION_TYPE_ZERO_BASED";
	public static final String CHROMOSOME_POSITION_TYPE_ONE_BASED = "CHROMOSOME_POSITION_TYPE_ONE_BASED";

	public static final String NOT_AVAILABLE_SNP_ID = "#N/A";
	public static final String NOT_APLICABLE = "#N/A";

	// OCD_GWAS_SIGNIFICANT_SNPS
	public static final String OCD_GWAS_SIGNIFICANT_SNPS_CHRNUMBER_BASEPAIRNUMBER = "OCD_GWAS_SNP" + System.getProperty("file.separator") + "ocd_gwas_snp_chrNumber_basePairNumber.txt";

	// HIV1 SNPS
	public static final String HIV1_SNPS_START_INCLUSIVE_END_EXCLUSIVE = "HIV1_SNP" + System.getProperty("file.separator") + "hglft_www_5c79_8ab500.bed";

	public static final String RANDOMLY_GENERATED_DATA_FILE = Commons.ANNOTATION + System.getProperty("file.separator") + "RandomlyGeneratedData" + System.getProperty("file.separator") + "PERMUTATION4_RANDOMLY_GENERATED_DATA.txt";

	// For Whole Genome Sliding Window
	public static final String ORIGINAL_READ_LINE = "ORIGINAL_READ_LINE";
	public static final String DEGENERATED_LINE = "DEGENERATED_LINE";

	public static final char RED = 'r';
	public static final char BLACK = 'b';

	public static final String INSERT = "INSERT";
	public static final String DELETE = "DELETE";
	public static final String chr = "chr";

	public static final String PROCESS_INPUT_DATA_REMOVE_OVERLAPS = "PROCESS_INPUT_DATA_REMOVE_OVERLAPS";

	public static final String ENCODE_DNASE_DIRECTORY1 = Commons.ENCODE + System.getProperty("file.separator") + "dnase";
	public static final String ENCODE_DNASE_DIRECTORY2 = Commons.ENCODE + System.getProperty("file.separator") + "dnase_jul2010";
	public static final String ENCODE_TFBS_DIRECTORY = Commons.ENCODE + System.getProperty("file.separator") + "transcription_factors";
	public static final String ENCODE_HISTONE_DIRECTORY = Commons.ENCODE + System.getProperty("file.separator") + "histone_macs";

	public static final String STRING_HYPHEN = "-";
	public static final String UNDERSCORE = "_";
	public static final String DOT = ".";
	public static final char SLASH = '/';

	public static final char GLANET_COMMENT_CHARACTER = '#';
	public static final String GLANET_COMMENT_STRING = "#";
	
	public static final byte BYTE_1 = 1;
	
	public static final String HYPHEN = "HYPHEN";
	public static final String TEST_LINEAR_SEARCH_VERSUS_INTERVAL_TREE_SEARCH = "Doktora" + System.getProperty("file.separator") + "testlinearsearchversusintervaltreesearch" + System.getProperty("file.separator") + "Compare.txt";

	public static int NUMBER_OF_CHROMOSOMES_HG19 = 24;

	// Calculations
	public static final String HG19_CHROMOSOME_SIZES_INPUT_FILE = Commons.FTP + System.getProperty("file.separator") + "HG19_CHROM_SIZES" + System.getProperty("file.separator") + "hg19.chrom.sizes.txt";

	/*******************************************************************************************************************/
	/******************** ANNOTATION RESULTS starts **********************************************************************/
	/*******************************************************************************************************************/
	public static final String ANNOTATION_RESULTS_FOR_DNASE_USING_INT_ARRAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.DNASE + System.getProperty("file.separator") + "INT_ARRAY_number_of_k_out_of_n_given_intervals_Dnase_results.txt";
	public static final String ANNOTATION_RESULTS_FOR_DNASE = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.DNASE + System.getProperty("file.separator") + "number_of_k_out_of_n_given_intervals_Dnase_results.txt";
	public static final String ANNOTATION_RESULTS_FOR_TF = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.TF + System.getProperty("file.separator") + "number_of_k_out_of_n_given_intervals_TF_results.txt";
	public static final String ANNOTATION_RESULTS_FOR_HISTONE = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.HISTONE + System.getProperty("file.separator") + "number_of_k_out_of_n_given_intervals_Histone_results.txt";

	public static final String ANNOTATION_RESULTS_FOR_KEGGPATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ANNOTATION_RESULTS_FOR_EXON_BASED_KEGGPATHWAY_FILE = "KEGGPathway_Exon_Based_number_of_k_out_of_n_search_input_lines.txt";
	public static final String ANNOTATION_RESULTS_FOR_REGULATION_BASED_KEGGPATHWAY_FILE = "KEGGPathway_Regulation_Based_number_of_k_out_of_n_search_input_lines.txt";
	public static final String ANNOTATION_RESULTS_FOR_ALL_BASED_KEGGPATHWAY_FILE = "KEGGPathway_All_Based_number_of_k_out_of_n_search_input_lines.txt";

	public static final String ANNOTATION_RESULTS_FOR_USERDEFINEDGENESET_DIRECTORY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator");
	public static final String ANNOTATION_RESULTS_FOR_EXON_BASED_USERDEFINEDGENESET_FILE = "_Exon_Based_number_of_k_out_of_n_search_input_lines.txt";
	public static final String ANNOTATION_RESULTS_FOR_REGULATION_BASED_USERDEFINEDGENESET_FILE = "_Regulation_Based_number_of_k_out_of_n_search_input_lines.txt";
	public static final String ANNOTATION_RESULTS_FOR_ALL_BASED_USERDEFINEDGENESET_FILE = "_All_Based_number_of_k_out_of_n_search_input_lines.txt";

	public static final String GENESET_RESULTS = "_results.txt";

	public static final String ANNOTATION_RESULTS_FOR_USERDEFINEDLIBRARY_DIRECTORY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator");
	public static final String ANNOTATION_RESULTS_FOR_USERDEFINEDLIBRARY_FILE = "_number_of_k_out_of_n_search_input_lines_results.txt";

	public static final String ANNOTATION_RESULTS_FOR_TF_EXON_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_TF_Exon_Based_KEGG_Pathway_results.txt";
	public static final String ANNOTATION_RESULTS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_TF_Regulation_Based_KEGG_Pathway_results.txt";
	public static final String ANNOTATION_RESULTS_FOR_TF_ALL_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_TF_All_Based_KEGG_Pathway_results.txt";

	public static final String ANNOTATION_RESULTS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_TF_CellLine_Exon_Based_KEGG_Pathway_results.txt";
	public static final String ANNOTATION_RESULTS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_TF_CellLine_Regulation_Based_KEGG_Pathway_results.txt";
	public static final String ANNOTATION_RESULTS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_TF_CellLine_All_Based_KEGG_Pathway_results.txt";

	public static final String ANNOTATE_INTERVALS_GENE_ALTERNATE_NAME_RESULTS_GIVEN_SEARCH_INPUT = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_Gene_Alternate_Name_results.txt";
	/*******************************************************************************************************************/
	/******************** ANNOTATION RESULTS ends ************************************************************************/
	/*******************************************************************************************************************/

	// whole genome using interval tree
	public static final String DNASE_CELL_LINE_WHOLE_GENOME_USING_INTERVAL_TREE = Commons.BYGLANET + System.getProperty("file.separator") + "fromWholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_intervaltree" + System.getProperty("file.separator") + "dnaseCellLine_whole_genome_using_interval_tree_number_of_non_overlapping_base_pairs_.txt";
	public static final String TFBS_WHOLE_GENOME_USING_INTERVAL_TREE = Commons.BYGLANET + System.getProperty("file.separator") + "fromWholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_intervaltree" + System.getProperty("file.separator") + "tfbs_whole_genome_using_interval_tree_number_of_non_overlapping_base_pairs_.txt";
	public static final String HISTONE_WHOLE_GENOME_USING_INTERVAL_TREE = Commons.BYGLANET + System.getProperty("file.separator") + "fromWholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_intervaltree" + System.getProperty("file.separator") + "histone_whole_genome_using_interval_tree_number_of_non_overlapping_base_pairs_.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE = Commons.BYGLANET + System.getProperty("file.separator") + "fromWholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_intervaltree" + System.getProperty("file.separator") + "exon_based_kegg_pathway_whole_genome_using_interval_tree_number_of_non_overlapping_base_pairs.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE = Commons.BYGLANET + System.getProperty("file.separator") + "fromWholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_intervaltree" + System.getProperty("file.separator") + "regulation_based_kegg_pathway_whole_genome_using_interval_tree_number_of_non_overlapping_base_pairs_.txt";

	/*******************************************************************************************************************/
	/******************** ANNOTATION OF PERMUTATIONS RESULTS starts ******************************************************/
	/*******************************************************************************************************************/
	public static final String ANNOTATE_PERMUTATIONS_FOR_DNASE = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.DNASE + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_FOR_TFBS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.TF + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_FOR_HISTONE = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.HISTONE + System.getProperty("file.separator");

	public static final String ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_USERDEFINED_GENESET_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + Commons.EXON_BASED_USER_DEFINED_GENESET + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_USERDEFINED_GENESET_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + Commons.REGULATION_BASED_USER_DEFINED_GENESET + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_USERDEFINED_GENESET_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator") + Commons.ALL_BASED_USER_DEFINED_GENESET + System.getProperty("file.separator");

	public static final String ANNOTATE_PERMUTATIONS_FOR_USERDEFINEDLIBRARY = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator");

	public static final String ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.EXON_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.REGULATION_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.ALL_BASED_KEGG_PATHWAY + System.getProperty("file.separator");

	public static final String ANNOTATE_PERMUTATIONS_TF_EXON_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_EXON_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_TF_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_REGULATION_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_TF_ALL_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_ALL_BASED_KEGG_PATHWAY + System.getProperty("file.separator");

	public static final String ANNOTATE_PERMUTATIONS_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	/*******************************************************************************************************************/
	/******************** ANNOTATION OF PERMUTATIONS RESULTS ends ********************************************************/
	/*******************************************************************************************************************/

	// Results to be Collected
	public static final String ENRICHMENT_DIRECTORY = Commons.ENRICHMENT + System.getProperty("file.separator");

	public static final String RUN = "run";
	public static final String RUNS_DIRECTORY = "runs" + System.getProperty("file.separator");

	public static final String ALL_WITH_RESPECT_TO_BH_FDR_ADJUSTED_P_VALUE = "_wrt_BH_FDR_adjusted_pValue.txt";
	public static final String ALL_WITH_RESPECT_TO_BONF_CORRECTED_P_VALUE = "_wrt_Bonf_corrected_pValue.txt";

	// Enrichment Results starts
	public static final String TO_BE_COLLECTED_DNASE_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.DNASE + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.DNASE;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_DNASE = ENRICHMENT_DIRECTORY + Commons.DNASE + System.getProperty("file.separator") + Commons.DNASE;

	public static final String TO_BE_COLLECTED_HISTONE_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.HISTONE + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.HISTONE;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_HISTONE = ENRICHMENT_DIRECTORY + Commons.HISTONE + System.getProperty("file.separator") + Commons.HISTONE;

	public static final String TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.TF + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.TF;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF = ENRICHMENT_DIRECTORY + Commons.TF + System.getProperty("file.separator") + Commons.TF;
	
	public static final String TO_BE_COLLECTED_GENE_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.GENE + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.GENE;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_GENE = ENRICHMENT_DIRECTORY + Commons.GENE + System.getProperty("file.separator") + Commons.GENE;
	
	// Use these constants in Enrichment and
	// AugmentationofEnrichedElementswithGivenInputData
	public static final String ENRICHMENT_USERDEFINED_GENESET_COMMON = ENRICHMENT_DIRECTORY + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator");

	// starts
	public static final String ENRICHMENT_EXONBASED_USERDEFINED_GENESET = Commons.EXON_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.EXON_BASED_USER_DEFINED_GENESET;
	public static final String ALL_PERMUTAIONS_NUMBER_OF_OVERLAPS_FOR_EXONBASED_USERDEFINED_GENESET = Commons.EXON_BASED + System.getProperty("file.separator") + Commons.EXON_BASED_USER_DEFINED_GENESET;

	public static final String ENRICHMENT_REGULATIONBASED_USERDEFINED_GENESET = Commons.REGULATION_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.REGULATION_BASED_USER_DEFINED_GENESET;
	public static final String ALL_PERMUTAIONS_NUMBER_OF_OVERLAPS_FOR_REGULATIONBASED_USERDEFINED_GENESET = Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.REGULATION_BASED_USER_DEFINED_GENESET;

	public static final String ENRICHMENT_ALLBASED_USERDEFINED_GENESET = Commons.ALL_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.ALL_BASED_USER_DEFINED_GENESET;
	public static final String ALL_PERMUTAIONS_NUMBER_OF_OVERLAPS_FOR_ALLBASED_USERDEFINED_GENESET = Commons.ALL_BASED + System.getProperty("file.separator") + Commons.ALL_BASED_USER_DEFINED_GENESET;
	// ends

	public static final String TO_BE_COLLECTED_USER_DEFINED_LIBRARY_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator");

	public static final String TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.EXON_BASED_KEGG_PATHWAY;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY = ENRICHMENT_DIRECTORY + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.EXON_BASED_KEGG_PATHWAY;

	public static final String TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.REGULATION_BASED_KEGG_PATHWAY;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY = ENRICHMENT_DIRECTORY + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.REGULATION_BASED_KEGG_PATHWAY;

	public static final String TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.ALL_BASED_KEGG_PATHWAY;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY = ENRICHMENT_DIRECTORY + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.ALL_BASED_KEGG_PATHWAY;

	public static final String TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.TF_EXON_BASED_KEGG_PATHWAY;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_EXON_BASED_KEGG_PATHWAY = ENRICHMENT_DIRECTORY + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.TF_EXON_BASED_KEGG_PATHWAY;

	public static final String TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.TF_REGULATION_BASED_KEGG_PATHWAY;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY = ENRICHMENT_DIRECTORY + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.TF_REGULATION_BASED_KEGG_PATHWAY;

	public static final String TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.TF_ALL_BASED_KEGG_PATHWAY;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_ALL_BASED_KEGG_PATHWAY = ENRICHMENT_DIRECTORY + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.TF_ALL_BASED_KEGG_PATHWAY;

	public static final String TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY = ENRICHMENT_DIRECTORY + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY;

	public static final String TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY = ENRICHMENT_DIRECTORY + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY;

	public static final String TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = ENRICHMENT_DIRECTORY + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + RUNS_DIRECTORY + Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY;
	public static final String ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY = ENRICHMENT_DIRECTORY + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY;
	// Enrichment Results ends

	/*******************************************************************************************************************/
	/*************************** Binomial Distribution starts ************************************************************/
	/*******************************************************************************************************************/
	public static final String DNASE_CELLLINE_NAMES_P_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "dnase_pvalues.txt";
	public static final String DNASE_CELLLINE_NAMES_ADJUSTED_P_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "dnase_adjusted_pvalues.txt";
	public static final String DNASE_CELLLINE_NAMES_ALL_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "dnase_all_values.txt";
	public static final String DNASE_CELLLINE_NAMES_ADJUSTED_ALL_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "dnase_adjusted_all_values.txt";

	public static final String TFBS_P_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "tfbs_pvalues.txt";
	public static final String TFBS_ADJUSTED_P_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "tfbs_adjusted_pvalues.txt";
	public static final String TFBS_ALL_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "tfbs_all_values.txt";
	public static final String TFBS_ADJUSTED_ALL_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "tfbs_adjusted_all_values.txt";

	public static final String HISTONE_P_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "histone_pvalues.txt";
	public static final String HISTONE_ADJUSTED_P_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "histone_adjusted_pvalues.txt";
	public static final String HISTONE_ALL_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "histone_all_values.txt";
	public static final String HISTONE_ADJUSTED_ALL_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "histone_adjusted_all_values.txt";

	public static final String EXON_BASED_KEGG_PATHWAY_P_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "exonBased_KeggPathway_pvalues.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "exonBased_KeggPathway_adjusted_pvalues.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ALL_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "exonBased_KeggPathway_all_values.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_ALL_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "exonBased_KeggPathway_adjusted_all_values.txt";

	public static final String REGULATION_BASED_KEGG_PATHWAY_P_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "regulationBased_KeggPathway_pvalues.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "regulationBased_KeggPathway_adjusted_pvalues.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ALL_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "regulationBased_KeggPathway_all_values.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_ALL_VALUES = "BinomialDistribution" + System.getProperty("file.separator") + "regulationBased_KeggPathway_adjusted_all_values.txt";

	public static final String CALCULATE_USING_BINOMIAL_DISTRIBUTION = "CALCULATE_USING_BINOMIAL_DISTRIBUTION";
	public static final String CALCULATE_USING_BURCAK_BINOMIAL_DISTRIBUTION = "CALCULATE_USING_BURCAK_BINOMIAL_DISTRIBUTION";
	/*******************************************************************************************************************/
	/*************************** Binomial Distribution ends **************************************************************/
	/*******************************************************************************************************************/

	public static final String EMPTY_STRING = "";

	// Enrichment parameter
	//Enumtypes
	//Enrichment
	public static final String DO_ENRICH = "DO_ENRICH";
	public static final String DO_NOT_ENRICH = "DO_NOT_ENRICH";

	// Enumtypes
	// Write all possible names
	public static final String DNASE = "Dnase";
	public static final String TF = "TF";
	public static final String HISTONE = "Histone";
	
	

	public static final String EXON_BASED = "ExonBased";
	public static final String REGULATION_BASED = "RegulationBased";
	public static final String ALL_BASED = "AllBased";
	public static final String NO_GENESET_ANALYSIS_TYPE_IS_DEFINED = "NoGeneSetAnalysisTypeIsDefined";

	public static final String EXON_BASED_USER_DEFINED_GENESET = "ExonBased_UserDefinedGeneSet";
	public static final String REGULATION_BASED_USER_DEFINED_GENESET = "RegulationBased_UserDefinedGeneSet";
	public static final String ALL_BASED_USER_DEFINED_GENESET = "AllBased_UserDefinedGeneSet";

	public static final String EXON_BASED_KEGG_PATHWAY = "ExonBased_KEGGPathway";
	public static final String REGULATION_BASED_KEGG_PATHWAY = "RegulationBased_KEGGPathway";
	public static final String ALL_BASED_KEGG_PATHWAY = "AllBased_KEGGPathway";

	public static final String TF_EXON_BASED_KEGG_PATHWAY = "TFExonBasedKEGGPathway";
	public static final String TF_REGULATION_BASED_KEGG_PATHWAY = "TFRegulationBasedKEGGPathway";
	public static final String TF_ALL_BASED_KEGG_PATHWAY = "TFAllBasedKEGGPathway";

	public static final String TF_CELLLINE_EXON_BASED_KEGG_PATHWAY = "TFCellLineExonBasedKEGGPathway";
	public static final String TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY = "TFCellLineRegulationBasedKEGGPathway";
	public static final String TF_CELLLINE_ALL_BASED_KEGG_PATHWAY = "TFCellLineAllBasedKEGGPathway";

	public static final String USER_DEFINED_GENESET = "UserDefinedGeneSet";
	public static final String USER_DEFINED_LIBRARY = "UserDefinedLibrary";

	public static final String KEGG_PATHWAY = "KEGGPathway";
	public static final String TF_KEGGPATHWAY = "TFKEGGPathway";
	public static final String TF_CELLLINE_KEGGPATHWAY = "TFCellLineKEGGPathway";

	public static final String GENE = "Gene";
	
	public static final String HG19_REFSEQ_GENE = "Hg19RefSeqGene";
	public static final String NCBI_GENE_ID = "NCBIGeneID";
	public static final String NCBI_RNA_NUCLEOTIDE_ACCESSION_VERSION = "NCBI_RNA_NUCLEOTIDE_ACCESSION_VERSION";
	public static final String UCSC_GENE_ALTERNATE_NAME = "UCSC_GENE_ALTERNATE_NAME";
	public static final String CELLLINE = "CELLLINE";

	/**************************************************************************************************/
	/**************************** ALL POSSIBLE NAMES Directories starts *********************************/
	/**************************************************************************************************/
	// All possible names Output Directories
	public static final String ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME = Commons.BYGLANET + System.getProperty("file.separator") + Commons.ALL_POSSIBLE_NAMES + System.getProperty("file.separator") + Commons.ENCODE + System.getProperty("file.separator");
	public static final String ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME = Commons.BYGLANET + System.getProperty("file.separator") + Commons.ALL_POSSIBLE_NAMES + System.getProperty("file.separator") + Commons.UCSCGENOME + System.getProperty("file.separator");
	public static final String NCBI_HUMAN_GENE_TO_REF_SEQ_OUTPUT_DIRECTORYNAME = Commons.BYGLANET + System.getProperty("file.separator") + Commons.NCBI + System.getProperty("file.separator");
	public static final String ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME = Commons.BYGLANET + System.getProperty("file.separator") + Commons.ALL_POSSIBLE_NAMES + System.getProperty("file.separator") + Commons.KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME = Commons.BYGLANET + System.getProperty("file.separator") + Commons.ALL_POSSIBLE_NAMES + System.getProperty("file.separator") + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator");
	public static final String ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME = Commons.BYGLANET + System.getProperty("file.separator") + Commons.ALL_POSSIBLE_NAMES + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator");
	/**************************************************************************************************/
	/**************************** ALL POSSIBLE NAMES Directories ends ***********************************/
	/**************************************************************************************************/

	/**************************************************************************************************/
	/********************************** ENCODE ALL POSSIBLE NAMES starts ********************************/
	/**************************************************************************************************/
	// ENCODE DNASE CELLLINE NAME2NUMBER
	public static final String ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_ENCODE_DnaseCellLine_Number_2_Name.txt";
	public static final String ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NAME_2_NUMBER_OUTPUT_FILENAME = "all_possible_ENCODE_DnaseCellLine_Name_2_Number.txt";
	// ENCODE CELLLINE
	public static final String ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_ENCODE_CellLine_Number_2_Name.txt";
	// ENCODE TF
	public static final String ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_ENCODE_TF_Number_2_Name.txt";
	// ENCODE TF CELLLINE
	public static final String ALL_POSSIBLE_ENCODE_TF_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_ENCODE_TF_CellLine_Number_2_Name.txt";
	// ENCODE HISTONE
	public static final String ALL_POSSIBLE_ENCODE_HISTONE_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_ENCODE_HISTONE_Number_2_Name.txt";
	// ENCODE HISTONE
	public static final String ALL_POSSIBLE_ENCODE_HISTONE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_ENCODE_HISTONE_CellLine_Number_2_Name.txt";
	// ENCODE FILENAME
	public static final String ALL_POSSIBLE_ENCODE_FILE_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_ENCODE_FILE_Number_2_Name.txt";

	// ENCODE CELLLINE NAME
	public static final String ALL_POSSIBLE_ENCODE_CELL_LINES_NAMES_FILENAME = "all_possible_ENCODE_CellLine_Names.txt";
	/**************************************************************************************************/
	/********************************** ENCODE ALL POSSIBLE NAMES ends **********************************/
	/**************************************************************************************************/

	/**************************************************************************************************/
	/********************************** UCSC GENOME REFSEQ GENES ALL POSSIBLE NAMES starts **************/
	/**************************************************************************************************/
	public static final String ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_RNANUCLEOTIDEACCESSION_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_UCSCGENOME_HG19_REFSEQ_GENES_RNANUCLEOTIDEACCESSION_Number_2_Name.txt";
	public static final String ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_Number_2_Name.txt";
	public static final String ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENEIDNUMBER_2_GENEID_OUTPUT_FILENAME = "all_possible_UCSCGENOME_HG19_REFSEQ_GENES_GENEIDNUMBER_2_GENEID.txt";
	/**************************************************************************************************/
	/********************************** UCSC GENOME REFSEQ GENES ALL POSSIBLE NAMES ends ****************/
	/**************************************************************************************************/

	/**************************************************************************************************/
	/********************************** NCBI HUMAN GENE 2 REFSEQ ALL POSSIBLE NAMES starts **************/
	/**************************************************************************************************/
	// These files are prepared by HumanRefSeq2Gene.java
	public static final String NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME_18_NOV_2014 = "human_gene2refseq_18_NOV_2014.txt";
	public static final String NCBI_RNANUCLEOTIDEACCESSION_TO_GENEID_18_NOV_2014 = "human_RNANucleotideAccession2GeneID_18_NOV_2014.txt";
	public static final String NCBI_RNANUCLEOTIDEACCESSIONWITHVERSION_TO_GENEID_18_NOV_2014 = "human_RNANucleotideAccessionWithVersion2GeneID_18_NOV_2014.txt";
	/**************************************************************************************************/
	/********************************** NCBI HUMAN GENE 2 REFSEQ ALL POSSIBLE NAMES ends ****************/
	/**************************************************************************************************/

	/**************************************************************************************************/
	/********************************** KEGG PATHWAY ALL POSSIBLE NAMES starts **************************/
	/**************************************************************************************************/
	// KEGG PATHWAY
	public static final String ALL_POSSIBLE_KEGG_PATHWAY_NAMES_OUTPUT_FILENAME = "all_possible_kegg_pathway_names.txt";
	public static final String ALL_POSSIBLE_KEGGPATHWAY_NAME_2_NUMBER_OUTPUT_FILENAME = "all_possible_keggPathway_Name_2_Number.txt";
	public static final String ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_keggPathway_Number_2_Name.txt";
	// Kegg Pathway to Ncbi Ref Seq Gene Ids
	public static final String KEGG_PATHWAY_ENTRY_2_NAME_INPUT_FILE = Commons.KEGG_PATHWAY + System.getProperty("file.separator") + "list_pathway_hsa.txt";
	public static final String KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE = Commons.KEGG_PATHWAY + System.getProperty("file.separator") + "pathway_hsa.list";
	/**************************************************************************************************/
	/********************************** KEGG PATHWAY ALL POSSIBLE NAMES ends ****************************/
	/**************************************************************************************************/

	/**************************************************************************************************/
	/********************************** USER DEFINED GENESET ALL POSSIBLE NAMES starts ******************/
	/**************************************************************************************************/
	// USER DEFINED GENESET
	public static final String ALL_POSSIBLE_USERDEFINEDGENESET_NAMES_OUTPUT_FILENAME = "all_possible_userDefinedGeneSet_names.txt";
	public static final String ALL_POSSIBLE_USERDEFINEDGENESET_NAME_2_NUMBER_OUTPUT_FILENAME = "all_possible_userDefinedGeneSet_Name_2_Number.txt";
	public static final String ALL_POSSIBLE_USERDEFINEDGENESET_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_userDefinedGeneSet_Number_2_Name.txt";
	/**************************************************************************************************/
	/********************************** USER DEFINED GENESET ALL POSSIBLE NAMES ends ********************/
	/**************************************************************************************************/

	/**************************************************************************************************/
	/********************************** USER DEFINED LIBRARY ALL POSSIBLE NAMES starts ******************/
	/**************************************************************************************************/
	// USER DEFINED LIBRARY
	public static final String ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_NAME_2_NUMBER_OUTPUT_FILENAME = "all_possible_userDefinedLibrary_elementType_Name_2_Number.txt";
	public static final String ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENT_NAME_2_NUMBER_OUTPUT_FILENAME = "all_possible_userDefinedLibrary_element_Name_2_Number.txt";
	public static final String ALL_POSSIBLE_USERDEFINEDLIBRARY_FILE_NAME_2_NUMBER_OUTPUT_FILENAME = "all_possible_userDefinedLibrary_file_Name_2_Number.txt";

	public static final String ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_userDefinedLibrary_elementType_Number_2_Name.txt";
	public static final String ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENT_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_userDefinedLibrary_element_Number_2_Name.txt";
	public static final String ALL_POSSIBLE_USERDEFINEDLIBRARY_FILE_NUMBER_2_NAME_OUTPUT_FILENAME = "all_possible_userDefinedLibrary_file_Number_2_Name.txt";
	/**************************************************************************************************/
	/********************************** USER DEFINED LIBRARY ALL POSSIBLE NAMES ends ********************/
	/**************************************************************************************************/

	/**************************************************************************************************/
	/****************************** ANNOTATION DIRECTORIES starts ***************************************/
	/**************************************************************************************************/
	public static final String DNASE_ANNOTATION_DIRECTORY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.DNASE + System.getProperty("file.separator");
	public static final String TF_ANNOTATION_DIRECTORY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF + System.getProperty("file.separator");
	public static final String HISTONE_ANNOTATION_DIRECTORY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.HISTONE + System.getProperty("file.separator");

	public static final String NCBI_GENE_ID_ANNOTATION_DIRECTORY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.NCBI_GENE_ID + System.getProperty("file.separator");
	public static final String NCBI_RNA_ANNOTATION_DIRECTORY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.NCBI_RNA_NUCLEOTIDE_ACCESSION_VERSION + System.getProperty("file.separator");
	public static final String UCSC_GENE_ALTERNATE_NAME_ANNOTATION_DIRECTORY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.UCSC_GENE_ALTERNATE_NAME + System.getProperty("file.separator");

	//hg19 RefSeq Genes 
	public static final String HG19_REFSEQ_GENE_ANNOTATION_DIRECTORY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.HG19_REFSEQ_GENE + System.getProperty("file.separator");
	public static final String OVERLAP_ANALYSIS_FILE = "Overlap_Analysis_File.txt";
	
	// UserDefinedGeneSet
	public static final String USER_DEFINED_GENESET_ANNOTATION_DIRECTORY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty("file.separator");
	public static final String EXONBASED_USERDEFINED_GENESET_ANNOTATION = Commons.EXON_BASED + System.getProperty("file.separator") + Commons.EXON_BASED;
	public static final String REGULATIONBASED_USERDEFINED_GENESET_ANNOTATION = Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.REGULATION_BASED;
	public static final String ALLBASED_USERDEFINED_GENESET_ANNOTATION = Commons.ALL_BASED + System.getProperty("file.separator") + Commons.ALL_BASED;

	// USER_DEFINED_LIBRARY
	public static final String USERDEFINEDLIBRARY_ANNOTATION_DIRECTORY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator");

	// KEGG Pathway
	public static final String EXON_BASED_KEGG_PATHWAY_ANNOTATION = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator") + Commons.EXON_BASED;
	public static final String REGULATION_BASED_KEGG_PATHWAY_ANNOTATION = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator") + Commons.REGULATION_BASED;
	public static final String ALL_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator") + Commons.ALL_BASED;

	// TF KEGGPATHWAY
	public static final String TF_EXON_BASED_KEGG_PATHWAY_ANNOTATION = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_EXON_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String TF_REGULATION_BASED_KEGG_PATHWAY_ANNOTATION = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_REGULATION_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String TF_ALL_BASED_KEGG_PATHWAY_ANNOTATION = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_ALL_BASED_KEGG_PATHWAY + System.getProperty("file.separator");

	public static final String TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_ANNOTATION = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_ANNOTATION = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_ANNOTATION = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	/**************************************************************************************************/
	/****************************** ANNOTATION DIRECTORIES ends *****************************************/
	/**************************************************************************************************/

	// Search using Linear search
	public static final String SEARCH_USING_LINEAR_SEARCH_INPUT_FILE = Commons.FTP + System.getProperty("file.separator") + "TCGA" + System.getProperty("file.separator") + "SearchInputforTCGATestData_three_columns.txt";
	public static final String SEARCH_USING_LINEAR_SEARCH_OUTPUT_FILE = "Doktora" + System.getProperty("file.separator") + "search" + System.getProperty("file.separator") + "encodeucscgenome" + System.getProperty("file.separator") + "SearchOutput_Using_LinearSearch.txt";

	// Searching using IntervalTree
	// public static final String SEARCH_USING_INTERVAL_TREE_INPUT_FILE =
	// Commons.FTP + System.getProperty("file.separator") + "TCGA" +
	// System.getProperty("file.separator") +
	// "SearchInputTCGADataWithNonBlankSNPRows.txt";
	public static final String SEARCH_USING_INTERVAL_TREE_OUTPUT_FILE = "Doktora" + System.getProperty("file.separator") + "search" + System.getProperty("file.separator") + "encodeucscgenome" + System.getProperty("file.separator") + "SearchOutput_Using_IntervalTreeSearch.txt";

	public static final String ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY = Commons.GIVENINPUTDATA + System.getProperty("file.separator");

	public static final String SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY = "Doktora" + System.getProperty("file.separator") + "search" + System.getProperty("file.separator") + "encodeucscgenome" + System.getProperty("file.separator");

	// Downloaded from NCBI, gene2refseq.txt data
	public static final String NCBI_GENE_TO_REF_SEQ_18_NOV_2014 = Commons.FTP + System.getProperty("file.separator") + "GENE_2_REFSEQ" + System.getProperty("file.separator") + "gene2refseq_18_NOV_2014.txt";

	public static final String SEARCH_INPUT_FILE_WITH_NON_BLANK_SNP_IDS = Commons.FTP + System.getProperty("file.separator") + "TCGA" + System.getProperty("file.separator") + "SearchInputWithNonBlankSNPIDs.txt";
	public static final String SEARCH_INPUT_FILE_FOR_TCGA_TEST_DATA = Commons.FTP + System.getProperty("file.separator") + "TCGA" + System.getProperty("file.separator") + "SearchInputforTCGATestData.txt";
	public static final String SEARCH_INPUT_FILE_FOR_TCGA_DATA_WITH_NON_BLANK_SNP_ROWS = "Doktora" + System.getProperty("file.separator") + "testtcgadata" + System.getProperty("file.separator") + "SearchInputTCGADataWithNonBlankSNPRows.txt";
	public static final String SEARCH_OUTPUT_FILE_FOR_TCGA_TEST_DATA = "Doktora" + System.getProperty("file.separator") + "testtcgadata" + System.getProperty("file.separator") + "SearchOutputforTCGATestData.txt";

	public static final String SEARCH_OUTPUT_FILE = "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "using" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "SearchOutput.txt";

	// public static final String FTP_HG19_REFSEQ_GENES = Commons.FTP +
	// System.getProperty("file.separator") + "HG19_REFSEQ_GENES" +
	// System.getProperty("file.separator") + "hg19_refseq_genes.txt";
	// public static final String FTP_HG19_REFSEQ_GENES_DOWNLOADED_1_OCT_2014 =
	// Commons.FTP + System.getProperty("file.separator") + "HG19_REFSEQ_GENES"
	// + System.getProperty("file.separator") +
	// "hg19_refseq_genes_1_OCT_2014.txt";
	public static final String UCSCGENOME_HG19_REFSEQ_GENES_DOWNLOADED_18_NOV_2014 = Commons.FTP + System.getProperty("file.separator") + Commons.UCSCGENOME + System.getProperty("file.separator") + "HG19_RefSeqGenes_18_NOV_2014.txt";

	public static final String ANNOTATE_UCSC_ANALYZE_HG19_REFSEQ_GENES_DIRECTORYNAME = Commons.BYGLANET + System.getProperty("file.separator") + "fromCreate" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator");
	public static final String ANNOTATE_UCSC_ANALYZE_HG19_REFSEQ_GENES_FILENAME = "analyze_hg19_refseq_genes.txt";

	// for debug sliding window versus interval tree
	public static final String BURCAK_DEBUG_ENCODE_SORTED_CHR1_HISTONE = "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "burcak_debug_sorted_chr1_histone.txt";

	/***********************************************************************************************/
	/********************************** ENCODE CREATE DIRECTORIES starts *****************************/
	/***********************************************************************************************/
	// Create ENCODE DNASE Directory
	public static final String BYGLANET_ENCODE_DNASE_DIRECTORY = Commons.BYGLANET + System.getProperty("file.separator") + Commons.ENCODE + System.getProperty("file.separator") + Commons.DNASE + System.getProperty("file.separator");

	// CREATE ENCODE HISTONE directory
	public static final String BYGLANET_ENCODE_HISTONE_DIRECTORY = Commons.BYGLANET + System.getProperty("file.separator") + Commons.ENCODE + System.getProperty("file.separator") + Commons.HISTONE + System.getProperty("file.separator");

	// CREATE ENCODE TF directory
	public static final String BYGLANET_ENCODE_TF_DIRECTORY = Commons.BYGLANET + System.getProperty("file.separator") + Commons.ENCODE + System.getProperty("file.separator") + Commons.TF + System.getProperty("file.separator");
	/***********************************************************************************************/
	/********************************** ENCODE CREATE DIRECTORIES ends *******************************/
	/***********************************************************************************************/

	/***********************************************************************************************/
	/************************* UCSC GENOME HG19 REFSEQ GENES CREATE DIRECTORIES starts ***************/
	/***********************************************************************************************/
	// Create ENCODE DNASE Directory
	public static final String BYGLANET_UCSCGENOME_HG19_REFSEQ_GENES_DIRECTORY = Commons.BYGLANET + System.getProperty("file.separator") + Commons.UCSCGENOME + System.getProperty("file.separator") + Commons.HG19_REFSEQ_GENE + System.getProperty("file.separator");
	/***********************************************************************************************/
	/************************* UCSC GENOME HG19 REFSEQ GENES CREATE DIRECTORIES ends *****************/
	/***********************************************************************************************/

	/************************* UNSORTED CHROMOSOME BASED ENCODE FILES WITH NUMBERS STARTS ***********************************/
	public static final String UNSORTED_ENCODE_DNASE_FILE_WITH_NUMBERS = "_unsorted_ENCODE_DNASE_file_with_numbers.txt";
	public static final String UNSORTED_ENCODE_TF_FILE_WITH_NUMBERS = "_unsorted_ENCODE_TF_file_with_numbers.txt";
	public static final String UNSORTED_ENCODE_HISTONE_FILE_WITH_NUMBERS = "_unsorted_ENCODE_HISTONE_file_with_numbers.txt";
	/************************* UNSORTED CHROMOSOME BASED ENCODE FILES WITH NUMBERS ENDS *************************************/

	/*************************
	 * UNSORTED CHROMOSOME BASED UCSCGENOME_HG19_REFSEQ_GENES FILES WITH NUMBERS
	 * STARTS
	 ***********************************/
	public static final String UNSORTED_UCSCGENOME_HG19_REFSEQ_GENES_FILE_WITH_NUMBERS = "_unsorted_UCSCGENOME_HG19_REFSEQ_GENES_file_with_numbers.txt";
	/*************************
	 * UNSORTED CHROMOSOME BASED UCSCGENOME_HG19_REFSEQ_GENES FILES WITH NUMBERS
	 * ENDS
	 *************************************/

	/************************* UNSORTED CHROMOSOME BASED USER DEFINED LIBRARY FILES WITH NUMBERS STARTS ***********************************/
	public static final String UNSORTED_USERDEFINEDLIBRARY_FILE_WITH_NUMBERS = "_unsorted_userDefinedLibrary_file_with_numbers.txt";
	/************************* UNSORTED CHROMOSOME BASED USER DEFINED LIBRARY FILES WITH NUMBERS ENDS *************************************/

	/****************************** HINTS ****************************************/
	public static final String GUI_HINT_INPUT_FILE_NAME = "Choose An Input Data File";
	public static final String GUI_HINT_JOB_NAME = "Give A Job Name In Order To Get A Specific Output Folder. Please Choose A Short Job Name For Your Convenience.";
	public static final String GUI_HINT_INPUT_FORMAT = "Choose Data Format In Input Data File";
	public static final String GUI_HINT_ASSEMBLY_FORMAT = "Supported Assemblies";
	public static final String GUI_HINT_GLANET_FOLDER = "Choose GLANET FOLDER Which Is The Parent Of Data Folder";
	public static final String GUI_HINT_NUMBER_OF_BASES = "Number Of Bases That Must Overlap In Order To Accept That Two Intervals Overlap";
	public static final String GUI_HINT_GENERATE_RANDOM_DATA_MODE = "Generate Random Data Mode";
	public static final String GUI_HINT_MULTIPLE_TESTING = "Multiple Testing";
	public static final String GUI_HINT_FDR = "False Discovery Rate";
	public static final String GUI_HINT_BONFERONI_CORRECTION_SIGNIFICANCE_CRITERIA = "Bonferroni Correction Significance Criteria";
	public static final String GUI_HINT_NUMBER_OF_PERMUTATIONS = "Number Of Total Permutations That Will Be Carried Out For Enrichment";
	public static final String GUI_HINT_NUMBER_OF_PERMUTATIONS_IN_EACH_RUN = "Number Of Permutations That Will Be Carried Out In Each Run";
	public static final String GUI_HINT_CELLLINE_BASED_DNASE_ANNOTATION = "DNase Annotation (Cell Line Based)";
	public static final String GUI_HINT_CELLLINE_BASED_HISTONE_ANNOTATION = "Histone Annotation (Cell Line Based)";
	public static final String GUI_HINT_CELLLINE_BASED_TF_ANNOTATION = "Transcription Factor Annotation (Cell Line Based)";
	public static final String GUI_HINT_KEGG_PATHWAY_ANNOTATION = "KEGG Pathway Annotation";
	public static final String GUI_HINT_TF_AND_KEGG_PATHWAY_ANNOTATION = "Transcription Factor and KEGG Pathway Annotation";
	public static final String GUI_HINT_CELLLINE_BASED_TF_AND_KEGG_PATHWAY_ANNOTATION = "Transcription Factor (Cell Line Based) and KEGG Pathway Annotation";
	public static final String GUI_HINT_USER_DEFINED_GENESET_ANNOTATION = "User Defined Gene Set Annotation";
	public static final String GUI_HINT_USER_DEFINED_GENESET_GENEINFORMATIONTYPE = "Select Gene Information Type In User Defined Gene Set Input File";
	public static final String GUI_HINT_USER_DEFINED_GENESET_INPUTFILE = "Choose User Defined Gene Set Input File";
	public static final String GUI_HINT_USER_DEFINED_GENESET_NAME = "User Defined Gene Set Name";
	public static final String GUI_HINT_USER_DEFINED_GENESET_DESCRIPTION_FILE = "User Defined Gene Set Description File (Optional)";
	public static final String GUI_HINT_USER_DEFINED_LIBRARY_INPUTFILE = "Choose User Defined Library Input File";
	public static final String GUI_HINT_USER_DEFINED_LIBRARY_ANNOTATION = "User Defined Library Annotation";
	public static final String GUI_HINT_USER_DEFINED_LIBRARY_DATA_FORMAT = "Choose Data Format In User Defined Library Input File";
	public static final String GUI_HINT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT = "Regulatory Sequence Analysis is possible  when the input file is comprised of SNPs";

	// Annotation Binary Matrices
	public static final String ANNOTATIONBINARYMATRIX_DNASE = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "DnaseAnnotationBinaryMatrix.txt";
	public static final String ANNOTATIONBINARYMATRIX_TF = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "TFAnnotationBinaryMatrix.txt";
	public static final String ANNOTATIONBINARYMATRIX_HISTONE = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "HistoneAnnotationBinaryMatrix.txt";

	public static final String ANNOTATIONBINARYMATRIX_EXONBASEDKEGG = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "ExonBasedKEGGPathwayAnnotationBinaryMatrix.txt";
	public static final String ANNOTATIONBINARYMATRIX_REGULATIONBASEDKEGG = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "RegulationBasedKEGGPathwayAnnotationBinaryMatrix.txt";
	public static final String ANNOTATIONBINARYMATRIX_ALLBASEDKEGG = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "AllBasedKEGGPathwayAnnotationBinaryMatrix.txt";

	public static final String ANNOTATIONBINARYMATRIX_TFEXONBASEDKEGG = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "TFExonBasedKEGGPathwayAnnotationBinaryMatrix.txt";
	public static final String ANNOTATIONBINARYMATRIX_TFREGULATIONBASEDKEGG = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "TFRegulationBasedKEGGPathwayAnnotationBinaryMatrix.txt";
	public static final String ANNOTATIONBINARYMATRIX_TFALLBASEDKEGG = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "TFAllBasedKEGGPathwayAnnotationBinaryMatrix.txt";

	public static final String ANNOTATIONBINARYMATRIX_TFCELLLINEEXONBASEDKEGG = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "TFCellLineExonBasedKEGGPathwayAnnotationBinaryMatrix.txt";
	public static final String ANNOTATIONBINARYMATRIX_TFCELLLINEREGULATIONBASEDKEGG = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "TFCellLineRegulationBasedKEGGPathwayAnnotationBinaryMatrix.txt";
	public static final String ANNOTATIONBINARYMATRIX_TFCELLLINEALLBASEDKEGG = ANNOTATION_BINARY_MATRIX_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") + "TFCellLineAllBasedKEGGPathwayAnnotationBinaryMatrix.txt";

	// dbSNP orient
	public static final String REVERSE = "reverse";
	public static final String FORWARD = "forward";

	// command line arguments
	public static final String ARG_IS_COMMAND_LINE_ENABLED = "-c";
	public static final String ARG_INPUT_FILE = "-i";
	public static final String ARG_ASSEMBLY_FORMAT_HG_19 = "-grch37";
	public static final String ARG_ASSEMBLY_FORMAT_HG_38 = "-grch38";
	public static final String ARG_GLANET_FOLDER = "-g";
	public static final String ARG_INPUT_FORMAT_1_BASED = "-f1";
	public static final String ARG_INPUT_FORMAT_0_BASED = "-f0";
	public static final String ARG_INPUT_FORMAT_BED = "-fbed";
	public static final String ARG_INPUT_FORMAT_GFF = "-fgff";
	public static final String ARG_INPUT_FORMAT_DBSNP = "-fdbsnp";
	public static final String ARG_NUMBER_OF_BASES = "-b";
	public static final String ARG_PERFORM_ENRICHMENT = "-e";
	public static final String ARG_GENERATE_RANDOM_DATA_WITH_GC_AND_MAP = "-rdgcm";
	public static final String ARG_GENERATE_RANDOM_DATA_WITHOUT_GC_AND_MAP = "-rd";
	public static final String ARG_MULTIPLE_TESTING_BENJAMINI = "-mtbhfdr";
	public static final String ARG_MULTIPLE_TESTING_BONFERRONI = "-mtbc";
	public static final String ARG_FALSE_DISCOVERY_RATE = "-fdr";
	public static final String ARG_SIGNIFICANCE_CRITERIA = "-sc";
	public static final String ARG_NUMBER_OF_PERMUTATIONS = "-p";
	public static final String ARG_NUMBER_OF_PERMUTATIONS_IN_EACH_RUN = "-pe";
	public static final String ARG_DNASE_ANNOTATION = "-dnase";
	public static final String ARG_GENE_ANNOTATION = "-gene";
	public static final String ARG_HISTONE_ANNOTATIONS = "-histone";
	public static final String ARG_TF_ANNOTATION = "-tf";
	public static final String ARG_KEGG_ANNOTATION = "-kegg";
	public static final String ARG_TF_AND_KEGG_ANNOTATION = "-tfkegg";
	public static final String ARG_CELL_TF_AND_KEGG_ANNOTATION = "-celltfkegg";
	public static final String ARG_USER_DEFINED_GENESET_ANNOTATION = "-udg";
	public static final String ARG_USER_DEFINED_GENESET_ANNOTATION_INPUT = "-udginput";
	public static final String ARG_USER_DEFINED_GENESET_ANNOTATION_GENE_INFORMATION_GENE_ID = "-udginfoid";
	public static final String ARG_USER_DEFINED_GENESET_ANNOTATION_GENE_INFORMATION_GENE_SYMBOL = "-udginfosym";
	public static final String ARG_USER_DEFINED_GENESET_ANNOTATION_GENE_INFORMATION_RNA_NUCLEOTIDE_ACCESSION = "-udginforna";
	public static final String ARG_USER_DEFINED_GENESET_ANNOTATION_GENESET_NAME = "-udgname";
	public static final String ARG_USER_DEFINED_GENESET_ANNOTATION_DESCRIPTION_FILE = "-udgdfile";
	public static final String ARG_USER_DEFINED_LIBRARY_ANNOTATION = "-udl";
	public static final String ARG_USER_DEFINED_LIBRARY_ANNOTATION_INPUT = "-udlinput";
	public static final String ARG_USER_DEFINED_LIBRARY_ANNOTATION_DATA_FORMAT_0_EXLUSIVE = "-udldf0exc";
	public static final String ARG_USER_DEFINED_LIBRARY_ANNOTATION_DATA_FORMAT_0_INCLUSIVE = "-udldf0inc";
	public static final String ARG_USER_DEFINED_LIBRARY_ANNOTATION_DATA_FORMAT_1_EXCLUSIVE = "-udldf1exc";
	public static final String ARG_USER_DEFINED_LIBRARY_ANNOTATION_DATA_FORMAT_1_INCLUSIVE = "-udldf1inc";
	public static final String ARG_JOB_NAME = "-j";
	public static final String ARG_RSAT = "-rsat";

	// Enum type NumberOfBases
	public static final String NUMBER_BASES_DEFAULT = "1";
	// Enum type SignificanceCriteria
	public static final String SIGNIFICANCE_CRITERIA_DEFAULT = "0.05";
	// Enum type FalseDiscoveryRate
	public static final String FDR_DEFAULT = "0.05";
	// Enum type NumberOfPermutations
	public static final String NUMBER_OF_PERMUTATIONS_DEFAULT = "10000";
	// Enum type NumberOfPermutationsInEachRun
	public static final String NUMBER_OF_PERMUTATIONS_IN_EACH_RUN_DEFAULT = "5000";
	// Enum type JobName

	// ENCODE Collaboration starts
	// AnnotateGivenIntervals starts
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_DNASE = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.DNASE + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TFBS = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_HISTONE = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.HISTONE + System.getProperty("file.separator");

	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_NCBI_GENE_ID = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.NCBI_GENE_ID + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_NCBI_RNA = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.NCBI_RNA_NUCLEOTIDE_ACCESSION_VERSION + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_UCSC_GENE_ALTERNATE_NAME = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.UCSC_GENE_ALTERNATE_NAME + System.getProperty("file.separator");

	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_HG19_REFSEQ_GENE = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.HG19_REFSEQ_GENE + System.getProperty("file.separator");

	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.EXON_BASED + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.REGULATION_BASED + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.ALL_BASED + System.getProperty("file.separator");

	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_EXON_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_EXON_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_REGULATION_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_REGULATION_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_ALL_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_ALL_BASED_KEGG_PATHWAY + System.getProperty("file.separator");

	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY = Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") + Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY + System.getProperty("file.separator");
	// AnnotateGivenIntervals ends
	// ENCODE Collaboration ends

}
