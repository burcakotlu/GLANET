/*
 * Prepared Input data will be processed.
 * Input data might be composed of snps or intervals in a mixed mode, does not matter.
 * Input will not contain any overlaps.
 * Overlaps will be merged.
 * 
 * 
 */
package giveninputdata;

import intervaltree.GivenInputDataSNPSorIntervals;
import intervaltree.IntervalTree;
import intervaltree.IntervalTreeNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.Assembly;
import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import enumtypes.GivenIntervalsInputFileDataFormat;
import enumtypes.NodeType;

public class InputDataRemoveOverlaps {

	final static Logger logger = Logger.getLogger(InputDataRemoveOverlaps.class);

	public static IntervalTreeNode mergeIntervals(IntervalTreeNode node1, IntervalTreeNode node2) {

		if (node2.getLow() < node1.getLow()) {
			node1.setLow(node2.getLow());
		}

		if (node2.getHigh() > node1.getHigh()) {
			node1.setHigh(node2.getHigh());
		}

		return node1;

	}

	public static void updateMergedNode(IntervalTreeNode mergedNode, IntervalTreeNode overlappedNode) {
		if (overlappedNode.getLow() < mergedNode.getLow()) {
			mergedNode.setLow(overlappedNode.getLow());
		}

		if (overlappedNode.getHigh() > mergedNode.getHigh()) {
			mergedNode.setHigh(overlappedNode.getHigh());
		}

		mergedNode.setNumberofBases(mergedNode.getHigh() - mergedNode.getLow() + 1);
	}

	public static IntervalTreeNode compute(Map<IntervalTreeNode, IntervalTreeNode> splicedNode2CopiedNodeMap, IntervalTreeNode overlappedNode) {
		IntervalTreeNode node = splicedNode2CopiedNodeMap.get(overlappedNode);
		IntervalTreeNode savedPreviousNode = null;

		while (node != null) {
			savedPreviousNode = node;
			node = splicedNode2CopiedNodeMap.get(node);
		}

		return savedPreviousNode;
	}

	/*
	 * Partition the input intervals in a chromosomed based manner Then for each
	 * of the 24 chromosome based partitioned input intervals Construct an
	 * interval tree If the new interval overlaps with any interval in the
	 * interval tree Merge the intervals, update the existing interval Otherwise
	 * insert the new interval.
	 * 
	 * Then write each interval tree to an output file. In this manner input
	 * file will get rid of overlaps.
	 */
	public static void removeOverlaps(String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		Assembly inputFileAssembly = Assembly.convertStringtoEnum(args[CommandLineArguments.InputFileAssembly.value()]);
		GivenIntervalsInputFileDataFormat inputFileFormat = GivenIntervalsInputFileDataFormat.convertStringtoEnum(args[CommandLineArguments.InputFileDataFormat.value()]);
		String inputFileName = null;

		// jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if (jobName.isEmpty()) {
			jobName = Commons.NO_NAME;
		}
		// jobName ends

		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");
		String givenDataFolder = outputFolder + Commons.GIVENINPUTDATA + System.getProperty("file.separator");

		switch (inputFileFormat) {

			case INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES:
			case INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES:
			case INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES:
			case INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES:

				switch (inputFileAssembly) {
					case GRCh38:
						inputFileName = givenDataFolder + Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh38;
						break;
					case GRCh37_p13:
						inputFileName = givenDataFolder + Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh37_p13;
						break;
				}// End of SWITCH inputFileAssembly
				break;

			case INPUT_FILE_FORMAT_DBSNP_IDS:
				inputFileName = givenDataFolder + Commons.PROCESSED_INPUT_FILE_0BASED_START_END_GRCh37_p13;
				break;

		}// End of SWITCH inputFileFormat

		Map<ChromosomeName, IntervalTree> chromosome2IntervalTree = new HashMap<ChromosomeName, IntervalTree>();

		String strLine;
		ChromosomeName chromosomeName;
		int low;
		int high;

		int indexofFirstTab;
		int indexofSecondTab;

		FileReader fileReader;
		BufferedReader bufferedReader;

		IntervalTree intervalTree = null;
		IntervalTreeNode intervalTreeNode = null;

		try {
			fileReader = FileOperations.createFileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				// example strLine
				// chr12 90902 90902
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab + 1);

				chromosomeName = ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab));

				if (indexofSecondTab > indexofFirstTab) {
					low = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));
					high = Integer.parseInt(strLine.substring(indexofSecondTab + 1));
				} else {
					low = Integer.parseInt(strLine.substring(indexofFirstTab + 1));
					high = low;
				}

				intervalTree = chromosome2IntervalTree.get(chromosomeName);
				intervalTreeNode = new IntervalTreeNode(chromosomeName, low, high);

				// create chromosome based interval tree
				if (intervalTree == null) {
					intervalTree = new IntervalTree();
					intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);
					chromosome2IntervalTree.put(chromosomeName, intervalTree);
				}
				// chromosome based interval tree already exists
				else {
					List<IntervalTreeNode> overlappedNodeList = new ArrayList<IntervalTreeNode>();

					intervalTree.findAllOverlappingIntervals(overlappedNodeList, intervalTree.getRoot(), intervalTreeNode);

					// there is overlap
					if (overlappedNodeList != null && overlappedNodeList.size() > 0) {

						IntervalTreeNode mergedNode = new IntervalTreeNode(intervalTreeNode.getChromName(), intervalTreeNode.getLow(), intervalTreeNode.getHigh(), NodeType.MERGED);
						IntervalTreeNode splicedoutNode = null;
						IntervalTreeNode nodetoBeDeleted = null;
						// you may try to delete a node which is already spliced
						// out by former deletions
						// therefore you must keep track of the real node to be
						// deleted in case of trial of deletion of an already
						// spliced out node.
						Map<IntervalTreeNode, IntervalTreeNode> splicedoutNode2CopiedNodeMap = new HashMap<IntervalTreeNode, IntervalTreeNode>();

						for (int i = 0; i < overlappedNodeList.size(); i++) {

							IntervalTreeNode overlappedNode = overlappedNodeList.get(i);

							updateMergedNode(mergedNode, overlappedNode);

							// if the to be deleted, intended interval tree node
							// is an already spliced out node
							// in other words if it is copied into another
							// interval tree node
							// then you have to delete that node
							// not the already spliced out node

							nodetoBeDeleted = compute(splicedoutNode2CopiedNodeMap, overlappedNode);

							if (nodetoBeDeleted != null) {
								// they are the same
								// current overlapped node has been copied to
								// the previously deleted overlapped node
								// current overlapped node is spliced out by the
								// previous delete operation
								// so delete that previously deleted overlapped
								// node in order to delete the current
								// overlapped node
								// since current overlapped node is copied to
								// the previously deleted overlapped node
								// Now we can delete this overlappedNode
								splicedoutNode = intervalTree.intervalTreeDelete(intervalTree, nodetoBeDeleted);

								if (splicedoutNode != nodetoBeDeleted)
									splicedoutNode2CopiedNodeMap.put(splicedoutNode, nodetoBeDeleted);
							} else {
								// Now we can delete this overlappedNode
								splicedoutNode = intervalTree.intervalTreeDelete(intervalTree, overlappedNode);

								if (splicedoutNode != overlappedNode)
									splicedoutNode2CopiedNodeMap.put(splicedoutNode, overlappedNode);

							}

						}// end of for: each overlapped node in the list
						intervalTree.intervalTreeInsert(intervalTree, mergedNode);

					}
					// there is no overlap
					else {
						// insert interval
						intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);
					}
				}

			}// End of While: read each input line

			bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// write to output file
		BufferedWriter bufferedWriter = null;

		IntervalTree tree = null;

		String type = Commons.PROCESS_INPUT_DATA_REMOVE_OVERLAPS;

		try {

			switch (inputFileFormat) {

				case INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES:
				case INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES:
				case INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES:
				case INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES:

					switch (inputFileAssembly) {
						case GRCh38:
							bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(givenDataFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE_0BASED_START_END_GRCh38));
							break;
						case GRCh37_p13:
							bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(givenDataFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE_0BASED_START_END_GRCh37_p13));
							break;
					}// End of SWITCH inputFileAssembly
					break;

				case INPUT_FILE_FORMAT_DBSNP_IDS:
					bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(givenDataFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE_0BASED_START_END_GRCh37_p13));
					break;

			}// End of SWITCH inputFileFormat

			// Initialize isGivenInputDataComprisedofSNPs
			// Assume that givenInputData is comprised of SNPs
			GivenInputDataSNPSorIntervals givenInputDataSNPSorIntervals = new GivenInputDataSNPSorIntervals();
			givenInputDataSNPSorIntervals.setGivenInputDataSNPs(true);

			for (Map.Entry<ChromosomeName, IntervalTree> chr2IntervalTree : chromosome2IntervalTree.entrySet()) {

				chromosomeName = chr2IntervalTree.getKey();
				tree = chr2IntervalTree.getValue();

				// write the nodes of the interval tree in a sorted way
				tree.intervalTreeInfixTraversal(tree.getRoot(), bufferedWriter, type, givenInputDataSNPSorIntervals);

			}

			// Set Command Line Argument
			if (!givenInputDataSNPSorIntervals.getGivenInputDataSNPs()) {
				args[CommandLineArguments.GivenInputDataType.value()] = Commons.GIVEN_INPUT_DATA_CONSISTS_OF_MIXED_LENGTH_INTERVALS;
			}

			// Close output file buffered writer
			bufferedWriter.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void writeGLANETRunTimeArguments(String[] args) {

		logger.info("*****************************************************************");
		logger.info("GLANET Parameters starts");

		// Write GLANET Arguments
		for (int i = 0; i < args.length; i++) {
			logger.info(args[i]);
		}

		logger.info("GLANET Parameters ends");
		logger.info("*****************************************************************");

	}

	// args[0] ---> Input File Name with folder
	// args[1] ---> GLANET installation folder with "\\" at the end. This folder
	// will be used for outputFolder and dataFolder.
	// args[2] ---> Input File Format
	// ---> default
	// Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// args[3] ---> Annotation, overlap definition, number of bases,
	// default 1
	// args[4] ---> Perform Enrichment parameter
	// ---> default Commons.DO_ENRICH
	// ---> Commons.DO_NOT_ENRICH
	// args[5] ---> Generate Random Data Mode
	// ---> default Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	// ---> Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT
	// args[6] ---> multiple testing parameter, enriched elements will be
	// decided and sorted with respect to this parameter
	// ---> default Commons.BENJAMINI_HOCHBERG_FDR
	// ---> Commons.BONFERRONI_CORRECTION
	// args[7] ---> Bonferroni Correction Significance Level, default 0.05
	// args[8] ---> Bonferroni Correction Significance Criteria, default 0.05
	// args[9] ---> Number of permutations, default 5000
	// args[10] ---> Dnase Enrichment
	// ---> default Commons.DO_NOT_DNASE_ENRICHMENT
	// ---> Commons.DO_DNASE_ENRICHMENT
	// args[11] ---> Histone Enrichment
	// ---> default Commons.DO_NOT_HISTONE_ENRICHMENT
	// ---> Commons.DO_HISTONE_ENRICHMENT
	// args[12] ---> Transcription Factor(TF) Enrichment
	// ---> default Commons.DO_NOT_TF_ENRICHMENT
	// ---> Commons.DO_TF_ENRICHMENT
	// args[13] ---> KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_KEGGPATHWAY_ENRICHMENT
	// args[14] ---> TF and KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	// args[15] ---> TF and CellLine and KeggPathway Enrichment
	// ---> default Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// args[16] ---> RSAT parameter
	// ---> default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// ---> Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// args[17] ---> job name example: ocd_gwas_snps
	// args[18] ---> writeGeneratedRandomDataMode checkBox
	// ---> default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	// ---> Commons.WRITE_GENERATED_RANDOM_DATA
	// args[19] ---> writePermutationBasedandParametricBasedAnnotationResultMode
	// checkBox
	// ---> default
	// Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// --->
	// Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// args[20] ---> writePermutationBasedAnnotationResultMode checkBox
	// ---> default Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	// ---> Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	// args[21] ---> number of permutations in each run. Default is 2000
	// args[22] ---> UserDefinedGeneSet Enrichment
	// default Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT
	// Commons.DO_USER_DEFINED_GENESET_ENRICHMENT
	// args[23] ---> UserDefinedGeneSet InputFile
	// args[24] ---> UserDefinedGeneSet GeneInformationType
	// default Commons.GENE_ID
	// Commons.GENE_SYMBOL
	// Commons.RNA_NUCLEOTIDE_ACCESSION
	// args[25] ---> UserDefinedGeneSet Name
	// args[26] ---> UserDefinedGeneSet Optional GeneSet Description InputFile
	// args[27] ---> UserDefinedLibrary Enrichment
	// default Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT
	// Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT
	// args[28] ---> UserDefinedLibrary InputFile
	// args[29] - args[args.length-1] ---> Note that the selected cell lines are
	// always inserted at the end of the args array because it's size
	// is not fixed. So for not (until the next change on args array) the
	// selected cell
	// lines can be reached starting from 22th index up until (args.length-1)th
	// index.
	// If no cell line selected so the args.length-1 will be 22-1 = 21. So it
	// will never
	// give an out of boundry exception in a for loop with this approach.
	public static void main(String[] args) {

		removeOverlaps(args);

		writeGLANETRunTimeArguments(args);

	}

}
