/**
 * 
 */
package giveninputdata;

import remap.Remap;
import common.Commons;
import enumtypes.Assembly;
import enumtypes.CommandLineArguments;
import enumtypes.GivenIntervalsInputFileDataFormat;

/**
 * @author Burçak Otlu
 * @date Jan 19, 2015
 * @project Glanet 
 *
 */
public class InputDataNCBIRemap {
	
	public static void convertUsingRemap(
			String outputFolder,
			String dataFolder,
			String inputFileName,
			Assembly sourceAssembly, 
			String outputFileName,
			Assembly targetAssembly){
		
		/**********************************************************/
		/********** NCBI REMAP PARAMETERS starts ******************/
		/**********************************************************/
		String sourceAssemblyName = "";
		String targetAssemblyName = Commons.GRCH37_P13;

		String sourceReferenceAssemblyID = null;
		String targetReferenceAssemblyID = null;

		String merge = null;
		String allowMultipleLocation = null;
		double minimumRatioOfBasesThatMustBeRemapped;
		double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength;
		/**********************************************************/
		/********** NCBI REMAP PARAMETERS ends ********************/
		/**********************************************************/

		
		sourceReferenceAssemblyID = "GCF_000001405.26";
		targetReferenceAssemblyID = "GCF_000001405.25";

		merge = Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON;
		allowMultipleLocation = Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON;
		minimumRatioOfBasesThatMustBeRemapped = Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_;
		maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength = Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2;

		// Could not find an alignment batch for your assembly pair: GRCh38
		// x GRCh37.p13
		// Please run "--mode batches" for a list of available assembly
		// pairs.
		// Remap.remap(dataFolder,"GRCh38", "GRCh37.p13", outputFolder +
		// Commons.CHRNAME_0Based_START_Inclusive_END_Exclusive_HG38_BED_FILE,
		// outputFolder +
		// Commons.CHRNAME_0Based_START_Inclusive_END_Exclusive_HG19_BED_FILE);
		Remap.remap(dataFolder, 
				sourceReferenceAssemblyID, 
				targetReferenceAssemblyID, 
				outputFolder + Commons.REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE, 
				outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE, 
				outputFolder + Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE, 
				outputFolder + Commons.REMAP_DUMMY_GENOME_WORKBENCH_PROJECT_FILE, 
				merge, 
				allowMultipleLocation, 
				minimumRatioOfBasesThatMustBeRemapped, 
				maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);

	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String glanetFolder 	= args[CommandLineArguments.GlanetFolder.value()];	
		Assembly inputFileAssembly = Assembly.convertStringtoEnum(args[CommandLineArguments.InputFileAssembly.value()]);
		GivenIntervalsInputFileDataFormat  inputFileFormat = GivenIntervalsInputFileDataFormat.convertStringtoEnum(args[CommandLineArguments.InputFileDataFormat.value()]);
		
		String inputFileName = null;
		String outputFileName = null;
		
		//jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if (jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		//jobName ends
	
		String outputFolder 	= glanetFolder + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");
		String dataFolder 	= glanetFolder + Commons.DATA + System.getProperty("file.separator") ;
		
		switch(inputFileFormat) {
		
			case INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES: 
			case INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES: 
			case INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES:
			case INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES: 
				
				switch (inputFileAssembly) {
					case GRCh38_HG38:	inputFileName  	= outputFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE_GRCh38_HG38;
										convertUsingRemap(outputFolder,dataFolder,inputFileName,Assembly.GRCh38_HG38, outputFileName,Assembly.GRCh37_HG19);
										break;
					case GRCh37_HG19:	
										break;
				}//End of SWITCH inputFileAssembly
				break;
			
			case INPUT_FILE_FORMAT_DBSNP_IDS:  
												break;
			
		}//End of SWITCH inputFileFormat

	}

}
