/**
 * 
 */
package remap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.AssemblySource;
import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * @author Bur�ak Otlu
 * @date Nov 25, 2014
 * @project Glanet
 *
 */
public class Remap {

	final static Logger logger = Logger.getLogger(Remap.class);

	public static void fillAssemblyName2RefSeqAssemblyIDMap(String dataFolder, String supportedAssembliesFileName, Map<String, String> assemblyName2RefSeqAssemblyIDMap) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;

		String species;
		String sourceAssemblyName;
		String sourceRefSeqAssemblyId;
		String targetAssemblyName;
		String targetRefSeqAssemblyId;

		try {
			fileReader = FileOperations.createFileReader(dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator"), supportedAssembliesFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {

				if (!strLine.startsWith("#")) {
					// Example line
					// #batch_id query_species query_name query_ucsc query_acc
					// target_species target_name target_ucsc target_acc
					// alignment_date
					// 85213 Homo sapiens NCBI33 GCF_000001405.8 Homo sapiens
					// GRCh38 hg38 GCF_000001405.26 09/20/2014
					// 85273 Homo sapiens NCBI36 hg18 GCF_000001405.12 Homo
					// sapiens GRCh38 hg38 GCF_000001405.26 09/20/2014
					// 85283 Homo sapiens GRCh37.p11 GCF_000001405.23 Homo
					// sapiens GRCh38 hg38 GCF_000001405.26 09/20/2014

					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = (indexofFirstTab > 0) ? strLine.indexOf('\t', indexofFirstTab + 1) : -1;
					indexofThirdTab = (indexofSecondTab > 0) ? strLine.indexOf('\t', indexofSecondTab + 1) : -1;
					indexofFourthTab = (indexofThirdTab > 0) ? strLine.indexOf('\t', indexofThirdTab + 1) : -1;
					indexofFifthTab = (indexofFourthTab > 0) ? strLine.indexOf('\t', indexofFourthTab + 1) : -1;
					indexofSixthTab = (indexofFifthTab > 0) ? strLine.indexOf('\t', indexofFifthTab + 1) : -1;
					indexofSeventhTab = (indexofSixthTab > 0) ? strLine.indexOf('\t', indexofSixthTab + 1) : -1;
					indexofEigthTab = (indexofSeventhTab > 0) ? strLine.indexOf('\t', indexofSeventhTab + 1) : -1;
					indexofNinethTab = (indexofEigthTab > 0) ? strLine.indexOf('\t', indexofEigthTab + 1) : -1;

					if (indexofFirstTab > 0 && indexofSecondTab > 0 && indexofThirdTab > 0 && indexofFourthTab > 0 && indexofFifthTab > 0 && indexofSixthTab > 0 && indexofSeventhTab > 0 && indexofEigthTab > 0 && indexofNinethTab > 0) {

						species = strLine.substring(indexofFirstTab + 1, indexofSecondTab);

						if (species.equals("Homo sapiens")) {
							sourceAssemblyName = strLine.substring(indexofSecondTab + 1, indexofThirdTab);
							sourceRefSeqAssemblyId = strLine.substring(indexofFourthTab + 1, indexofFifthTab);

							if (!assemblyName2RefSeqAssemblyIDMap.containsKey(sourceAssemblyName)) {
								assemblyName2RefSeqAssemblyIDMap.put(sourceAssemblyName, sourceRefSeqAssemblyId);
							}

							targetAssemblyName = strLine.substring(indexofSixthTab + 1, indexofSeventhTab);
							targetRefSeqAssemblyId = strLine.substring(indexofEigthTab + 1, indexofNinethTab);

							if (!assemblyName2RefSeqAssemblyIDMap.containsKey(targetAssemblyName)) {
								assemblyName2RefSeqAssemblyIDMap.put(targetAssemblyName, targetRefSeqAssemblyId);
							}
						}// End of if species is HOMO SAPIENS

					}// End of if necessary indexofTabs are nonzero

				}// End of if not a comment line

			}// End of while

			// close
			bufferedReader.close();

		} catch (IOException e) {

			logger.error(e.toString());
		}

	}

	public static void remap_show_batches(String dataFolder, String supportedAssembliesFileName) {

		String remapFile = dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator") + "remap_api.pl";
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		BufferedWriter bufferedWriter = null;
		BufferedReader bufferedReader = null;
		String line;

		try {
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator") + supportedAssembliesFileName));

			process = runtime.exec(new String[] { "perl", remapFile, "--mode", "batches" });
			
			//System.out.println("perl " + remapFile + "--mode batches");
			
			//here we use Thread.sleep instead of waitFor() because
			//process's input stream is not immediately written
			//just after the process finishes. Connecting to the server
			//and getting the results may take longer. Therefore,
			//it is not enough just to wait for the end of the process.
			//one should also wait for getting the results from the server
			//properly
			
			//nearly 40-50 trial on that. Worst case is about 5-6 secs.
			//For a very bad network connection, it should not be more
			//than 10 secs. It is not suggested it you decrease sleep
			//amount. It might cause that the file won't be written
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			// Output of the perl execution is here
			bufferedReader = new BufferedReader( new InputStreamReader(process.getInputStream()));

			while ((line = bufferedReader.readLine()) != null) {
				// logger.info(line);
				//System.out.println(line);
				bufferedWriter.write(line + System.getProperty("line.separator"));
			}// End of while

			// Close
			bufferedReader.close();
			bufferedWriter.close();

			logger.info("NCBI REMAP Show Batches Exit status = " + process.exitValue());

		} catch (IOException e) {

			logger.info("NCBI REMAP Show Batches Exception = " + "\t" + "Exception toString():  " + e.toString());
		}
	}

	public static void createOutputFileUsingREMAPREPORTFile(Map<Integer, String> remapInputFileLineNumber2LineContentMap, String remapReportFile, String outputFile) {

		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;

		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;
		int indexofTwelfthTab;
		int indexofThirteenthTab;
		int indexofFourteenthTab;
		int indexofFifteenthTab;
		int indexofSixteenthTab;
		int indexofSeventeenthTab;

		int indexofUnderscore;

		String lineNumberString;
		int lineNumber = Integer.MIN_VALUE;

		ChromosomeName sourceChrName;
		ChromosomeName mappedChrName;

		int sourceInt;
		String mappedIntString;
		int mappedInt;

		int mappedStart;
		int mappedEnd;
		AssemblySource mappedAssembly;

		TIntObjectMap<Remapped> sourceLineNumber2RemappedMap = new TIntObjectHashMap<Remapped>();

		int maximumLineNumber = Integer.MIN_VALUE;
		Remapped remapped = null;

		int numberofUnConvertedGenomicLociInPrimaryAssembly = 0;
		int numberofConvertedGenomicLociInPrimaryAssembly = 0;

		try {

			File file = new File(remapReportFile);

			if (file.exists()) {

				bufferedReader = new BufferedReader(FileOperations.createFileReader(remapReportFile));
				bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFile));

				while ((strLine = bufferedReader.readLine()) != null) {

					// #feat_name source_int mapped_int source_id mapped_id
					// source_length mapped_length source_start source_stop
					// source_strand source_sub_start source_sub_stop
					// mapped_start mapped_stop mapped_strand coverage recip
					// asm_unit
					// line_67 1 1 chr1 chr1 1 1 147664654 147664654 + 147664654
					// 147664654 147136772 147136772 + 1 Second Pass Primary
					// Assembly
					// line_67 1 2 chr1 NW_003871055.3 1 1 147664654 147664654 +
					// 147664654 147664654 4480067 4480067 + 1 First Pass
					// PATCHES
					// line_122 1 NULL chr14 NULL 1 NULL 93095256 93095256 +
					// NOMAP NOALIGN

					if (!strLine.startsWith("#")) {

						indexofFirstTab = strLine.indexOf('\t');
						indexofSecondTab = (indexofFirstTab > 0) ? strLine.indexOf('\t', indexofFirstTab + 1) : -1;
						indexofThirdTab = (indexofSecondTab > 0) ? strLine.indexOf('\t', indexofSecondTab + 1) : -1;
						indexofFourthTab = (indexofThirdTab > 0) ? strLine.indexOf('\t', indexofThirdTab + 1) : -1;
						indexofFifthTab = (indexofFourthTab > 0) ? strLine.indexOf('\t', indexofFourthTab + 1) : -1;
						indexofSixthTab = (indexofFifthTab > 0) ? strLine.indexOf('\t', indexofFifthTab + 1) : -1;
						indexofSeventhTab = (indexofSixthTab > 0) ? strLine.indexOf('\t', indexofSixthTab + 1) : -1;
						indexofEigthTab = (indexofSeventhTab > 0) ? strLine.indexOf('\t', indexofSeventhTab + 1) : -1;
						indexofNinethTab = (indexofEigthTab > 0) ? strLine.indexOf('\t', indexofEigthTab + 1) : -1;
						indexofTenthTab = (indexofNinethTab > 0) ? strLine.indexOf('\t', indexofNinethTab + 1) : -1;
						indexofEleventhTab = (indexofTenthTab > 0) ? strLine.indexOf('\t', indexofTenthTab + 1) : -1;
						indexofTwelfthTab = (indexofEleventhTab > 0) ? strLine.indexOf('\t', indexofEleventhTab + 1) : -1;
						indexofThirteenthTab = (indexofTwelfthTab > 0) ? strLine.indexOf('\t', indexofTwelfthTab + 1) : -1;
						indexofFourteenthTab = (indexofThirteenthTab > 0) ? strLine.indexOf('\t', indexofThirteenthTab + 1) : -1;
						indexofFifteenthTab = (indexofFourteenthTab > 0) ? strLine.indexOf('\t', indexofFourteenthTab + 1) : -1;
						indexofSixteenthTab = (indexofFifteenthTab > 0) ? strLine.indexOf('\t', indexofFifteenthTab + 1) : -1;
						indexofSeventeenthTab = (indexofSixteenthTab > 0) ? strLine.indexOf('\t', indexofSixteenthTab + 1) : -1;

						lineNumberString = strLine.substring(0, indexofFirstTab);

						indexofUnderscore = lineNumberString.indexOf('_');
						lineNumber = Integer.parseInt(lineNumberString.substring(indexofUnderscore + 1));

						sourceInt = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));

						mappedIntString = strLine.substring(indexofSecondTab + 1, indexofThirdTab);

						if (!mappedIntString.equals(Commons.NULL)) {

							mappedInt = Integer.parseInt(strLine.substring(indexofSecondTab + 1, indexofThirdTab));

							sourceChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofThirdTab + 1, indexofFourthTab));
							mappedChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofFourthTab + 1, indexofFifthTab));

							mappedStart = Integer.parseInt(strLine.substring(indexofTwelfthTab + 1, indexofThirteenthTab));
							mappedEnd = Integer.parseInt(strLine.substring(indexofThirteenthTab + 1, indexofFourteenthTab));

							mappedAssembly = AssemblySource.convertStringtoEnum(strLine.substring(indexofSeventeenthTab + 1));

							if ((sourceInt == 1) && (mappedInt == 1) && sourceChrName == mappedChrName && mappedAssembly.isPrimaryAssembly()) {

								remapped = new Remapped(mappedInt, mappedStart, mappedEnd, mappedChrName, mappedAssembly);
								sourceLineNumber2RemappedMap.put(lineNumber, remapped);

							}// End of IF: Valid conversion
						}// End of IF mappedInt is not NULL

					}// End of IF: Not Header or Comment Line

				}// End of while

				// Set maximum line number to the last lineNumber
				maximumLineNumber = lineNumber;

				logger.info("******************************************************************************");
				logger.info("Number of given genomic loci before NCBI REMAP: " + maximumLineNumber);

				// Write to the file
				for (int i = 1; i <= maximumLineNumber; i++) {

					remapped = sourceLineNumber2RemappedMap.get(i);

					if (remapped != null) {
						bufferedWriter.write(remapped.getMappedChrName().convertEnumtoString() + "\t" + remapped.getMappedStart() + "\t" + remapped.getMappedEnd() + System.getProperty("line.separator"));
						numberofConvertedGenomicLociInPrimaryAssembly++;
					} else {
						bufferedWriter.write(Commons.NULL + "\t" + Commons.NULL + "\t" + Commons.NULL + System.getProperty("line.separator"));
						numberofUnConvertedGenomicLociInPrimaryAssembly++;
						logger.warn("We have not converted this genomic loci in latest assembly to hg19 using NCBI REMAP: " + remapInputFileLineNumber2LineContentMap.get(i));
					}

				}// End of for

				logger.info("Number of converted genomic loci after NCBI REMAP: " + numberofConvertedGenomicLociInPrimaryAssembly);
				logger.info("We have lost " + numberofUnConvertedGenomicLociInPrimaryAssembly + " genomic loci during NCBI REMAP");
				logger.info("******************************************************************************");

				// close
				bufferedReader.close();
				bufferedWriter.close();

			}// End of if remapReportFile exists

		} catch (IOException e) {
			logger.error(e.toString());
		}

	}

	/*
	 * Pay attention Previous Report File must not be opened in Excel Otherwise
	 * New Report File can not be updated
	 */
	public static void remap(String dataFolder, String sourceAssembly, String targetAssembly, String sourceFileName, String outputFileName, String reportFileName, String genomeWorkbenchProjectFile, String merge, String allowMultipleLocation, double minimumRatioOfBasesThatMustBeRemapped, double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength, String inputFormat, String information) {

		String remapFile = dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator") + "remap_api.pl";

		// DirectoryName can contain empty spaces
		Runtime runtime = Runtime.getRuntime();
		Process process = null;

		try {

			String[] command = new String[] { "perl", remapFile, "--mode", "asm-asm", "--from", sourceAssembly, "--dest", targetAssembly, "--annotation", sourceFileName, "--annot_out", outputFileName, "--report_out", reportFileName, "--gbench_out", genomeWorkbenchProjectFile, "--merge", merge, "--allowdupes", allowMultipleLocation, "--mincov ", new Double(minimumRatioOfBasesThatMustBeRemapped).toString(), "--maxexp ", new Double(maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength).toString(), "--in_format", inputFormat };
			process = runtime.exec(command);

			process.waitFor();

			// output of the perl execution is here
			// BufferedReader bufferedReader = new BufferedReader( new
			// InputStreamReader( process.getInputStream()));
			// String line;

			// while ( ( line = bufferedReader.readLine()) != null){
			// logger.info(line);
			// }//End of while

			logger.info("NCBI REMAP Exit status = " + process.exitValue() + "\t" + information);

			// Close
			// bufferedReader.close();

		} catch (InterruptedException e) {

			logger.info("NCBI REMAP Exception = " + "\t" + information + "\t" + "Exception toString():  " + e.toString());

		} catch (IOException e) {

			logger.info("NCBI REMAP Exception = " + "\t" + information + "\t" + "Exception toString():  " + e.toString());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");

		// remap(dataFolder,"GCF_000001405.25","GCF_000001405.26","C:\\Users\\Bur�ak\\Google Drive\\Output\\NoName\\GivenInputData\\chrName_0Based_StartInclusive_EndExclusive_hg38_coordinates.bed","C:\\Users\\Bur�ak\\Google Drive\\Output\\NoName\\GivenInputData\\chrName_0Based_StartInclusive_EndExclusive_hg19_coordinates.bed");
		// remap(dataFolder,"GCF_000001405.26","GCF_000001405.25","C:\\Users\\Bur�ak\\Developer\\Java\\GLANET\\Glanet\\src\\remap\\chrName_0Based_StartInclusive_EndExclusive_hg38_coordinates.bed","C:\\Users\\Bur�ak\\Developer\\Java\\GLANET\\Glanet\\src\\remap\\chrName_0Based_StartInclusive_EndExclusive_hg19_coordinates.bed",
		// Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON,
		// Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON,
		// Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_,
		// Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2);
		remap_show_batches(dataFolder, Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);

		// GCF_000001405.25 <----> GRCh37.p13
		// GCF_000001405.26 <------> GRCh38
		// GCF_000001405.27 <------> GRCh38.p1

	}

	/*
	 * InputFileSourceAssembly contains one genomic loci per line.
	 * OutputFileTargetAssembly contains one genomic loci per line.
	 */
	public static void convertOneGenomicLociPerLineUsingMap(String outputFolder, String oneGenomicLociPerLineOutputFileInTargetAssembly, TIntObjectMap<String> lineNumber2SourceGenomicLociMap, TIntObjectMap<String> lineNumber2SourceInformationMap, TIntObjectMap<String> lineNumber2TargetGenomicLociMap, String headerLine) {

		// outputFile In Target Assembly
		BufferedWriter bufferedWriter = null;

		String toBeRemapped = null;
		String mapped = null;
		String toBeRemappedInformation = null;

		int numberofUnRemappedInputLine = 0;

		try {
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + oneGenomicLociPerLineOutputFileInTargetAssembly));

			// Header line
			bufferedWriter.write(headerLine + System.getProperty("line.separator"));

			for (int i = 1; i <= lineNumber2SourceGenomicLociMap.size(); i++) {

				toBeRemapped = lineNumber2SourceGenomicLociMap.get(i);
				mapped = lineNumber2TargetGenomicLociMap.get(i);

				if (mapped != null) {
					bufferedWriter.write(mapped + System.getProperty("line.separator"));
				} else {
					if (lineNumber2SourceInformationMap != null) {
						toBeRemappedInformation = lineNumber2SourceInformationMap.get(i);
						logger.warn("Please notice that there is an unconverted genomic loci during NCBI REMAP API");
						logger.warn("rsId: " + toBeRemappedInformation + " To be Remapped: " + toBeRemapped + " Mapped: " + mapped);
						numberofUnRemappedInputLine++;
					} else {
						logger.warn("Please notice that there is an unconverted genomic loci during NCBI REMAP API");
						logger.warn("To be Remapped: " + toBeRemapped + " Mapped: " + mapped);
						numberofUnRemappedInputLine++;
					}

				}

			}// End of FOR

			logger.warn("Number of unremapped lines is: " + numberofUnRemappedInputLine);

			// CLOSE
			bufferedWriter.close();

		} catch (FileNotFoundException e) {

			logger.error(e.toString());
		} catch (IOException e) {

			logger.error(e.toString());
		}

	}

	/*
	 * InputFileSourceAssembly contains two genomic loci per line.
	 */
	public static void convertTwoGenomicLociPerLineUsingMap(String outputFolder, String outputFileInTargetAssembly, TIntObjectMap<String> lineNumber2SourceGenomicLociMap, TIntObjectMap<String> lineNumber2SourceInformationMap, TIntObjectMap<String> lineNumber2TargetGenomicLociMap, String headerLine) {

		// outputFileInTargetAssembly
		BufferedWriter bufferedWriter = null;

		String toBeRemapped1;
		String toBeRemapped2;

		String mapped1;
		String mapped2;

		String information = null;
		int numberofUnRemappedInputLine = 0;

		try {

			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + outputFileInTargetAssembly));

			// Header line
			bufferedWriter.write(headerLine + System.getProperty("line.separator"));

			for (int i = 1; i <= lineNumber2SourceGenomicLociMap.size();) {

				toBeRemapped1 = lineNumber2SourceGenomicLociMap.get(i);
				toBeRemapped2 = lineNumber2SourceGenomicLociMap.get(i + 1);

				mapped1 = lineNumber2TargetGenomicLociMap.get(i);
				mapped2 = lineNumber2TargetGenomicLociMap.get(i + 1);

				information = lineNumber2SourceInformationMap.get(i);

				// Increase i by 2
				// Since we have two genomic loci per line in original all TF
				// Annotations File,.
				i = i + 2;

				if (mapped1 != null && mapped2 != null) {
					bufferedWriter.write(mapped1 + "\t" + mapped2 + "\t" + information + System.getProperty("line.separator"));
				}// End of IF: None of the mapped is null
				else {

					if (mapped1 == null) {
						logger.warn("Please notice that there is an unconverted genomic loci during NCBI REMAP API");
						logger.warn("To be Remapped1: " + toBeRemapped1 + " Mapped1: " + mapped1 + " after: " + information);

						numberofUnRemappedInputLine++;
					}

					if (mapped2 == null) {
						logger.warn("Please notice that there is an unconverted genomic loci during NCBI REMAP API");
						logger.warn(" To be Remapped2: " + toBeRemapped2 + " Mapped2: " + mapped2 + " after: " + information);

						numberofUnRemappedInputLine++;
					}
				}// End of ELSE: at least one of the mapped is null

			}// End of FOR

			logger.warn("Number of unremapped lines is: " + numberofUnRemappedInputLine);

			// CLOSE
			bufferedWriter.close();

		} catch (FileNotFoundException e) {

			logger.error(e.toString());
		} catch (IOException e) {

			logger.error(e.toString());
		}

	}

	public static void fillConversionMap(String outputFolder, String remapReportFile, TIntObjectMap<String> lineNumber2SourceGenomicLociMap, TIntObjectMap<String> lineNumber2TargetGenomicLociMap) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;
		int indexofTwelfthTab;
		int indexofThirteenthTab;
		int indexofFourteenthTab;
		int indexofFifteenthTab;
		int indexofSixteenthTab;
		int indexofSeventeenthTab;

		int lineNumber;

		// source
		// int sourceInt;
		ChromosomeName sourceChrName;
		int sourceStart;
		int sourceEnd;

		// mapped
		String mappedIntString;
		// int mappedInt;
		ChromosomeName mappedChrName;
		int mappedStart;
		int mappedEnd;
		AssemblySource mappedAssembly;

		// Read remapReportFile
		// Using remapReportFile first fill chrName_start_end_in_one_assembly 2
		// chrName_start_end_in_another_assembly Map

		File file = new File(outputFolder + remapReportFile);

		if (file.exists()) {

			try {

				fileReader = FileOperations.createFileReader(outputFolder + remapReportFile);

				bufferedReader = new BufferedReader(fileReader);

				while ((strLine = bufferedReader.readLine()) != null) {

					// #feat_name source_int mapped_int source_id mapped_id
					// source_length mapped_length source_start source_stop
					// source_strand source_sub_start source_sub_stop
					// mapped_start mapped_stop mapped_strand coverage recip
					// asm_unit
					// line_67 1 1 chr1 chr1 1 1 147664654 147664654 + 147664654
					// 147664654 147136772 147136772 + 1 Second Pass Primary
					// Assembly
					// line_67 1 2 chr1 NW_003871055.3 1 1 147664654 147664654 +
					// 147664654 147664654 4480067 4480067 + 1 First Pass
					// PATCHES
					// line_122 1 NULL chr14 NULL 1 NULL 93095256 93095256 +
					// NOMAP NOALIGN

					if (strLine.charAt(0) != (Commons.GLANET_COMMENT_CHARACTER)) {

						indexofFirstTab = strLine.indexOf('\t');
						indexofSecondTab = (indexofFirstTab > 0) ? strLine.indexOf('\t', indexofFirstTab + 1) : -1;
						indexofThirdTab = (indexofSecondTab > 0) ? strLine.indexOf('\t', indexofSecondTab + 1) : -1;
						indexofFourthTab = (indexofThirdTab > 0) ? strLine.indexOf('\t', indexofThirdTab + 1) : -1;
						indexofFifthTab = (indexofFourthTab > 0) ? strLine.indexOf('\t', indexofFourthTab + 1) : -1;
						indexofSixthTab = (indexofFifthTab > 0) ? strLine.indexOf('\t', indexofFifthTab + 1) : -1;
						indexofSeventhTab = (indexofSixthTab > 0) ? strLine.indexOf('\t', indexofSixthTab + 1) : -1;
						indexofEigthTab = (indexofSeventhTab > 0) ? strLine.indexOf('\t', indexofSeventhTab + 1) : -1;
						indexofNinethTab = (indexofEigthTab > 0) ? strLine.indexOf('\t', indexofEigthTab + 1) : -1;
						indexofTenthTab = (indexofNinethTab > 0) ? strLine.indexOf('\t', indexofNinethTab + 1) : -1;
						indexofEleventhTab = (indexofTenthTab > 0) ? strLine.indexOf('\t', indexofTenthTab + 1) : -1;
						indexofTwelfthTab = (indexofEleventhTab > 0) ? strLine.indexOf('\t', indexofEleventhTab + 1) : -1;
						indexofThirteenthTab = (indexofTwelfthTab > 0) ? strLine.indexOf('\t', indexofTwelfthTab + 1) : -1;
						indexofFourteenthTab = (indexofThirteenthTab > 0) ? strLine.indexOf('\t', indexofThirteenthTab + 1) : -1;
						indexofFifteenthTab = (indexofFourteenthTab > 0) ? strLine.indexOf('\t', indexofFourteenthTab + 1) : -1;
						indexofSixteenthTab = (indexofFifteenthTab > 0) ? strLine.indexOf('\t', indexofFifteenthTab + 1) : -1;
						indexofSeventeenthTab = (indexofSixteenthTab > 0) ? strLine.indexOf('\t', indexofSixteenthTab + 1) : -1;

						lineNumber = Integer.parseInt(strLine.substring(strLine.substring(0, indexofFirstTab).indexOf(Commons.UNDERSCORE) + 1, indexofFirstTab));

						// sourceInt =
						// Integer.parseInt(strLine.substring(indexofFirstTab+1,
						// indexofSecondTab));

						mappedIntString = strLine.substring(indexofSecondTab + 1, indexofThirdTab);

						if (!mappedIntString.equals(Commons.NULL)) {

							// mappedInt =
							// Integer.parseInt(strLine.substring(indexofSecondTab+1,
							// indexofThirdTab));

							sourceChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofThirdTab + 1, indexofFourthTab));
							mappedChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofFourthTab + 1, indexofFifthTab));

							sourceStart = Integer.parseInt(strLine.substring(indexofSeventhTab + 1, indexofEigthTab));
							sourceEnd = Integer.parseInt(strLine.substring(indexofEigthTab + 1, indexofNinethTab));

							mappedStart = Integer.parseInt(strLine.substring(indexofTwelfthTab + 1, indexofThirteenthTab));
							mappedEnd = Integer.parseInt(strLine.substring(indexofThirteenthTab + 1, indexofFourteenthTab));

							mappedAssembly = AssemblySource.convertStringtoEnum(strLine.substring(indexofSeventeenthTab + 1));

							// Pay attention
							// sourceInt and mappedInt does not have to be the
							// same
							// e.g: rs1233578
							if (sourceChrName == mappedChrName && mappedAssembly.isPrimaryAssembly()) {

								lineNumber2TargetGenomicLociMap.put(lineNumber, mappedChrName.convertEnumtoString() + "\t" + mappedStart + "\t" + mappedEnd);

								// check
								if (!lineNumber2SourceGenomicLociMap.get(lineNumber).equals(sourceChrName.convertEnumtoString() + "\t" + sourceStart + "\t" + sourceEnd)) {
									logger.error(Commons.THERE_IS_A_SITUATION);
								}

							}// End of IF: Valid conversion
						}// End of IF mappedInt is not NULL

					}// End of IF: Not Header or Comment Line

				}// End of while

				// for debug purposes starts
				logger.info("Number of Lines In lineNumber2SourceGenomicLociMap : " + lineNumber2SourceGenomicLociMap.size() + " for file: " + remapReportFile);
				logger.info("Number of Lines In lineNumber2TargetGenomicLociMap : " + lineNumber2TargetGenomicLociMap.size() + " for file: " + remapReportFile);
				// for debug purposes ends

				// close
				bufferedReader.close();

			} catch (IOException e) {

				logger.error(e.toString());
			}

		}// End of if remapReportFile exists

	}

}
