/**
 * 
 */
package giveninputdata;

import enumtypes.Assembly;
import enumtypes.CommandLineArguments;
import enumtypes.GivenIntervalsInputFileDataFormat;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import remap.Remap;
import ui.GlanetRunner;
import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Bur�ak Otlu
 * @date Jan 19, 2015
 * @project Glanet
 *
 */
public class InputDataNCBIRemap {

	final static Logger logger = Logger.getLogger(InputDataNCBIRemap.class);

	public static void readRemapOutputFileWriteProcessedInputFile( String givenInputDataFolder,
			String remapOutputFile1BasedStartEndInGRCh37p13, String processedFile0BasedStartEndInGRCh37p13) {

		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;

		String strLine = null;
		int indexoFirstTab;
		int indexofSecondTab;

		int start1Based;
		int end1Based;

		int start0Based;
		int end0Based;

		try{
			bufferedReader = new BufferedReader(
					FileOperations.createFileReader( givenInputDataFolder + remapOutputFile1BasedStartEndInGRCh37p13));
			bufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter( givenInputDataFolder + processedFile0BasedStartEndInGRCh37p13));

			while( ( strLine = bufferedReader.readLine()) != null){

				if( strLine.charAt( 0) != Commons.GLANET_COMMENT_CHARACTER){
					indexoFirstTab = strLine.indexOf( '\t');
					indexofSecondTab = ( indexoFirstTab > 0)?strLine.indexOf( '\t', indexoFirstTab + 1):-1;

					start1Based = Integer.parseInt( strLine.substring( indexoFirstTab + 1, indexofSecondTab));
					end1Based = Integer.parseInt( strLine.substring( indexofSecondTab + 1));

					start0Based = start1Based - 1;
					end0Based = end1Based - 1;

					bufferedWriter.write( strLine.substring( 0, indexoFirstTab) + "\t" + start0Based + "\t" + end0Based + System.getProperty( "line.separator"));
				}

			}// End of WHILE

			// Close
			bufferedReader.close();
			bufferedWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// read inputFileName
	// write
	// Commons.REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE
	public static void readInputFileFillMapWriteRemapInputFile( String givenInputDataFolder,
			String inputFile0BasedStartEnd, TIntObjectMap<String> lineNumber2SourceGenomicLociMap,
			String remapInputFileOBasedStartEndExclusive) {

		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;

		String strLine = null;
		int indexoFirstTab;
		int indexofSecondTab;

		int start0Based;
		int end0Based;
		int end0BasedExclusive;

		int lineNumber = 1;

		try{
			bufferedReader = new BufferedReader(
					FileOperations.createFileReader( givenInputDataFolder + inputFile0BasedStartEnd));
			bufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter( givenInputDataFolder + remapInputFileOBasedStartEndExclusive));

			while( ( strLine = bufferedReader.readLine()) != null){

				indexoFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexoFirstTab > 0)?strLine.indexOf( '\t', indexoFirstTab + 1):-1;

				start0Based = Integer.parseInt( strLine.substring( indexoFirstTab + 1, indexofSecondTab));
				end0Based = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				end0BasedExclusive = end0Based + 1;

				bufferedWriter.write( strLine.substring( 0, indexoFirstTab) + "\t" + start0Based + "\t" + end0BasedExclusive + System.getProperty( "line.separator"));
				lineNumber2SourceGenomicLociMap.put( lineNumber++,
						strLine.substring( 0, indexoFirstTab) + "\t" + start0Based + "\t" + end0BasedExclusive);

			}// End of WHILE

			// Close
			bufferedReader.close();
			bufferedWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void convertGivenInputCoordinatesFromGRCh38toGRCh37p13UsingRemap( String givenInputDataFolder,
			String dataFolder, String inputFile, Assembly sourceAssembly, String outputFile, Assembly targetAssembly) {

		/**********************************************************/
		/********** NCBI REMAP PARAMETERS starts ******************/
		/**********************************************************/
		String sourceAssemblyName = null;
		String targetAssemblyName = null;

		String sourceReferenceAssemblyID = null;
		String targetReferenceAssemblyID = null;

		switch( sourceAssembly){
		case GRCh37_p13:
			sourceAssemblyName = Commons.GRCH37_P13;
			break;
		case GRCh38:
			sourceAssemblyName = Commons.GRCH38;
			break;
		}// End of Switch

		switch( targetAssembly){
		case GRCh37_p13:
			targetAssemblyName = Commons.GRCH37_P13;
			break;
		case GRCh38:
			targetAssemblyName = Commons.GRCH38;
			break;
		}// End of Switch

		Remap.remap_show_batches( dataFolder, Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);

		Map<String, String> assemblyName2RefSeqAssemblyIDMap = new HashMap<String, String>();
		Remap.fillAssemblyName2RefSeqAssemblyIDMap(
				dataFolder, 
				Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE,
				assemblyName2RefSeqAssemblyIDMap);

		// sourceReferenceAssemblyID = "GCF_000001405.26";
		sourceReferenceAssemblyID = assemblyName2RefSeqAssemblyIDMap.get( sourceAssemblyName);

		// targetReferenceAssemblyID = "GCF_000001405.25";
		targetReferenceAssemblyID = assemblyName2RefSeqAssemblyIDMap.get( targetAssemblyName);

		String merge = null;
		String allowMultipleLocation = null;
		double minimumRatioOfBasesThatMustBeRemapped;
		double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength;

		String inputFormat = Commons.BED;

		merge = Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON;
		allowMultipleLocation = Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON;
		minimumRatioOfBasesThatMustBeRemapped = Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_;
		maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength = Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2;
		/**********************************************************/
		/********** NCBI REMAP PARAMETERS ends ********************/
		/**********************************************************/

		String headerLine = Commons.HEADER_LINE_FOR_COORDINATES_IN_LATEST_ASSEMBLY;

		TIntObjectMap<String> lineNumber2SourceGenomicLociMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> lineNumber2SourceInformationMap = null;
		TIntObjectMap<String> lineNumber2TargetGenomicLociMap = new TIntObjectHashMap<String>();

		// read inputFileName
		// write
		// Commons.REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE
		readInputFileFillMapWriteRemapInputFile( givenInputDataFolder, inputFile, lineNumber2SourceGenomicLociMap,
				Commons.REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE);

		if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");

		Remap.remap(
				dataFolder,
				sourceReferenceAssemblyID,
				targetReferenceAssemblyID,
				givenInputDataFolder + Commons.REMAP_INPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_0BASED_START_ENDEXCLUSIVE_BED_FILE,
				givenInputDataFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
				givenInputDataFolder + Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE,
				givenInputDataFolder + Commons.REMAP_DUMMY_GENOME_WORKBENCH_PROJECT_FILE, merge, allowMultipleLocation,
				minimumRatioOfBasesThatMustBeRemapped, maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength,
				inputFormat, Commons.REMAP_GIVENINPUTDATA_FROM_GRCH38_TO_GRCH37P13);

		Remap.fillConversionMap( givenInputDataFolder, Commons.REMAP_REPORT_CHRNAME_1Based_START_END_XLS_FILE,
				lineNumber2SourceGenomicLociMap, lineNumber2TargetGenomicLociMap);

		Remap.convertOneGenomicLociPerLineUsingMap(
				givenInputDataFolder,
				Commons.REMAP_OUTPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_1Based_START_END_BED_FILE_USING_REMAP_REPORT,
				lineNumber2SourceGenomicLociMap, lineNumber2SourceInformationMap, lineNumber2TargetGenomicLociMap,
				headerLine);

		if( GlanetRunner.shouldLog())logger.info( "******************************************************************************");

		// read remap outputfile
		// write processed file in 0Based start end in GRCH37 p13
		readRemapOutputFileWriteProcessedInputFile(
				givenInputDataFolder,
				Commons.REMAP_OUTPUTFILE_ONE_GENOMIC_LOCI_PER_LINE_CHRNAME_1Based_START_END_BED_FILE_USING_REMAP_REPORT,
				outputFile);

	}

	/**
	 * @param args
	 */
	public static void main( String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		Assembly inputFileAssembly = Assembly.convertStringtoEnum( args[CommandLineArguments.InputFileAssembly.value()]);
		GivenIntervalsInputFileDataFormat inputFileFormat = GivenIntervalsInputFileDataFormat.convertStringtoEnum( args[CommandLineArguments.InputFileDataFormat.value()]);

		String inputFileName = null;
		String outputFileName = null;

		// jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if( jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		// jobName ends

		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty( "file.separator") + jobName + System.getProperty( "file.separator");
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
		String givenInputDataFolder = outputFolder + Commons.GIVENINPUTDATA + System.getProperty( "file.separator");

		switch( inputFileFormat){

		case INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES:
		case INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES:
		case INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES:
		case INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES:

			switch( inputFileAssembly){
			case GRCh38:
				inputFileName = Commons.REMOVED_OVERLAPS_INPUT_FILE_0BASED_START_END_GRCh38;
				outputFileName = Commons.REMOVED_OVERLAPS_INPUT_FILE_0BASED_START_END_GRCh37_p13;

				convertGivenInputCoordinatesFromGRCh38toGRCh37p13UsingRemap( givenInputDataFolder, dataFolder,
						inputFileName, Assembly.GRCh38, outputFileName, Assembly.GRCh37_p13);
				break;
			case GRCh37_p13:
				break;
			}// End of SWITCH inputFileAssembly
			break;

		case INPUT_FILE_FORMAT_DBSNP_IDS:
			break;

		}// End of SWITCH inputFileFormat

	}

}
