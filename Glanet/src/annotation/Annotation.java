/**
 * @author burcakotlu
 * @date Jun 30, 2014 
 * @time 5:43:38 PM
 */
package annotation;

import gnu.trove.iterator.TIntByteIterator;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TLongByteIterator;
import gnu.trove.iterator.TLongIntIterator;
import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.iterator.TShortIntIterator;
import gnu.trove.iterator.TShortObjectIterator;
import gnu.trove.list.TIntList;
import gnu.trove.map.TIntByteMap;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongByteMap;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.TShortIntMap;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TIntByteHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongByteHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import hg19.GRCh37Hg19Chromosome;
import intervaltree.DnaseIntervalTreeNode;
import intervaltree.DnaseIntervalTreeNodeWithNumbers;
import intervaltree.Interval;
import intervaltree.IntervalTree;
import intervaltree.IntervalTreeNode;
import intervaltree.TforHistoneIntervalTreeNode;
import intervaltree.TforHistoneIntervalTreeNodeWithNumbers;
import intervaltree.UcscRefSeqGeneIntervalTreeNode;
import intervaltree.UcscRefSeqGeneIntervalTreeNodeWithNumbers;
import intervaltree.UserDefinedLibraryIntervalTreeNodeWithNumbers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import keggpathway.ncbigenes.KeggPathwayUtility;

import org.apache.log4j.Logger;

import ui.GlanetRunner;
import userdefined.geneset.UserDefinedGeneSetUtility;
import userdefined.library.UserDefinedLibraryUtility;
import augmentation.humangenes.HumanGenesAugmentation;
import auxiliary.Accumulation;
import auxiliary.FileOperations;
import common.Commons;
import enrichment.AllMaps;
import enrichment.AllMapsDnaseTFHistoneWithNumbers;
import enrichment.AllMapsKeysWithNumbersAndValuesOneorZero;
import enrichment.AllMapsWithNumbers;
import enrichment.InputLine;
import enrichment.InputLineMinimal;
import enumtypes.AnnotationType;
import enumtypes.AssociationMeasureType;
import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import enumtypes.GeneInformationType;
import enumtypes.GeneOverlapAnalysisFileMode;
import enumtypes.GeneSetAnalysisType;
import enumtypes.GeneSetType;
import enumtypes.GeneratedMixedNumberDescriptionOrderLength;
import enumtypes.IntervalName;
import enumtypes.NodeType;
import enumtypes.RegulatorySequenceAnalysisType;
import enumtypes.UserDefinedLibraryDataFormat;
import enumtypes.WriteElementBasedAnnotationFoundOverlapsMode;

/**
 * Annotate given intervals with annotation options with numbers
 */
public class Annotation {

	final static Logger logger = Logger.getLogger(Annotation.class);

	// Write a class such that it can be called from Annotation and Enrichment
	// Annotate Original Given Data and --> Annotation
	// Annotate Randomly Generated Data --> Enrichment
	static class FindOverlaps extends RecursiveTask<int[]> {

		private static final long serialVersionUID = 4766480987562900233L;

		private String outputFolder;
		private ChromosomeName chromName;
		private List<Interval> data;
		private WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode;

		private int lowTaskIndex;
		private int highTaskIndex;

		private int overlapDefinition;
		private IntervalTree intervalTree;
		private int numberofComparisonsDnase;

		private char[][] dnaseCellLineNames;
		private char[][] fileNames;

		public FindOverlaps( String outputFolder, ChromosomeName chromName, List<Interval> data,
				WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
				int lowTaskIndex, int highTaskIndex, int overlapDefinition, IntervalTree intervalTree,
				int numberofComparisonsDnase, char[][] dnaseCellLineNames, char[][] fileNames) {

			this.outputFolder = outputFolder;
			this.chromName = chromName;
			this.data = data;

			this.writeElementBasedAnnotationFoundOverlapsMode = writeElementBasedAnnotationFoundOverlapsMode;

			this.lowTaskIndex = lowTaskIndex;
			this.highTaskIndex = highTaskIndex;

			this.overlapDefinition = overlapDefinition;
			this.intervalTree = intervalTree;

			this.numberofComparisonsDnase = numberofComparisonsDnase;

			this.dnaseCellLineNames = dnaseCellLineNames;
			this.fileNames = fileNames;

		}

		@Override
		protected int[] compute() {

			int middleTaskIndex;

			int[] leftKArray = null;
			int[] rightKArray = null;
			int[] kArray = null;

			Interval interval;

			int NUMBER_OF_FIND_OVERLAPS_DONE_SEQUENTIALLY_BY_EACH_PROCESS = data.size() / Commons.NUMBER_OF_AVAILABLE_PROCESSORS;

			if( NUMBER_OF_FIND_OVERLAPS_DONE_SEQUENTIALLY_BY_EACH_PROCESS == 0){
				NUMBER_OF_FIND_OVERLAPS_DONE_SEQUENTIALLY_BY_EACH_PROCESS = 8;
			}

			// DIVIDE
			if( highTaskIndex - lowTaskIndex > NUMBER_OF_FIND_OVERLAPS_DONE_SEQUENTIALLY_BY_EACH_PROCESS){
				middleTaskIndex = lowTaskIndex + ( highTaskIndex - lowTaskIndex) / 2;
				FindOverlaps left = new FindOverlaps( outputFolder, chromName, data,
						writeElementBasedAnnotationFoundOverlapsMode, lowTaskIndex, middleTaskIndex, overlapDefinition,
						intervalTree, numberofComparisonsDnase, dnaseCellLineNames, fileNames);

				// if( GlanetRunner.shouldLog())logger.info("Find Overlaps Left" + "\t" + "lowTaskIndex:" + "\t" + lowTaskIndex + "\t" +
				// "middleTaskIndex:" + "\t" + middleTaskIndex);

				FindOverlaps right = new FindOverlaps( outputFolder, chromName, data,
						writeElementBasedAnnotationFoundOverlapsMode, middleTaskIndex, highTaskIndex,
						overlapDefinition, intervalTree, numberofComparisonsDnase, dnaseCellLineNames, fileNames);

				// if( GlanetRunner.shouldLog())logger.info("Find Overlaps Right" + "\t" + "middleTaskIndex:" + "\t" + middleTaskIndex + "\t" +
				// "highTaskIndex:" + "\t" + highTaskIndex);

				left.fork();
				rightKArray = right.compute();
				leftKArray = left.join();

				// Add the contents of leftMap into the rightMap
				accumulate( leftKArray, rightKArray);

				leftKArray = null;
				return rightKArray;
			}
			// CONQUER
			else{

				kArray = new int[numberofComparisonsDnase];

				// if( GlanetRunner.shouldLog())logger.info(chromName.convertEnumtoString() + "\t" + "Before " + "\t" + "lowTaskIndex" + "\t"+
				// lowTaskIndex + "\t" + "highTaskIndex" + "\t"+ highTaskIndex + "\t" + "elementNumber2KMap.size()" +
				// "\t" + elementNumber2KMap.size());

				for( int i = lowTaskIndex; i < highTaskIndex; i++){

					interval = data.get( i);

					// if( GlanetRunner.shouldLog())logger.info(chromName.convertEnumtoString() + "\t" + "Interval Index:" + i);

					byte[] oneOrZeroByteArray = new byte[numberofComparisonsDnase];

					if( intervalTree.getRoot().getNodeName().isNotSentinel()){

						intervalTree.findAllOverlappingDnaseIntervalsWithNumbers( outputFolder,
								writeElementBasedAnnotationFoundOverlapsMode, intervalTree.getRoot(), interval,
								chromName, oneOrZeroByteArray, overlapDefinition, dnaseCellLineNames, fileNames);
					}

					// if( GlanetRunner.shouldLog())logger.info(chromName.convertEnumtoString() + "\t" + "Interval Index:" + i + "\t" +
					// "elementNumber2OneorZeroMap.size()" + "\t" +elementNumber2OneorZeroMap.size());

					accumulate( oneOrZeroByteArray, kArray);

					oneOrZeroByteArray = null;

				}// End of FOR

				// if( GlanetRunner.shouldLog())logger.info(chromName.convertEnumtoString() + "\t" + "After " + "\t" + "lowTaskIndex" + "\t"+
				// lowTaskIndex + "\t" + "highTaskIndex" + "\t" + highTaskIndex + "\t" + "elementNumber2KMap.size()"+
				// "\t"+ elementNumber2KMap.size());

				return kArray;
			}

		}// End of Compute method

		// Add the content of leftArray to rightArray
		// Clear(removed) and null leftMap
		protected void accumulate( byte[] leftArray, int[] rightArray) {

			Arrays.setAll( rightArray, i -> leftArray[i] + rightArray[i]);
		}

		protected void accumulate( int[] leftArray, int[] rightArray) {

			Arrays.setAll( rightArray, i -> leftArray[i] + rightArray[i]);
		}

	}

	// Empirical P value Calculation
	// For Thread Version for
	public void createChromBaseSeachInputFiles( String outputFolder, String permutationNumber,
			List<BufferedWriter> bufferedWriterList) {

		try{
			FileWriter fileWriter1 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr1_input_file.txt");
			FileWriter fileWriter2 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr2_input_file.txt");
			FileWriter fileWriter3 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr3_input_file.txt");
			FileWriter fileWriter4 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr4_input_file.txt");
			FileWriter fileWriter5 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr5_input_file.txt");
			FileWriter fileWriter6 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr6_input_file.txt");
			FileWriter fileWriter7 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr7_input_file.txt");
			FileWriter fileWriter8 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr8_input_file.txt");
			FileWriter fileWriter9 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr9_input_file.txt");
			FileWriter fileWriter10 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr10_input_file.txt");
			FileWriter fileWriter11 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr11_input_file.txt");
			FileWriter fileWriter12 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr12_input_file.txt");
			FileWriter fileWriter13 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr13_input_file.txt");
			FileWriter fileWriter14 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr14_input_file.txt");
			FileWriter fileWriter15 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr15_input_file.txt");
			FileWriter fileWriter16 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr16_input_file.txt");
			FileWriter fileWriter17 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr17_input_file.txt");
			FileWriter fileWriter18 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr18_input_file.txt");
			FileWriter fileWriter19 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr19_input_file.txt");
			FileWriter fileWriter20 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr20_input_file.txt");
			FileWriter fileWriter21 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr21_input_file.txt");
			FileWriter fileWriter22 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chr22_input_file.txt");
			FileWriter fileWriter23 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chrX_input_file.txt");
			FileWriter fileWriter24 = FileOperations.createFileWriter( outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "_" + permutationNumber + "_" + "search_chrY_input_file.txt");

			BufferedWriter bufferedWriter1 = new BufferedWriter( fileWriter1);
			BufferedWriter bufferedWriter2 = new BufferedWriter( fileWriter2);
			BufferedWriter bufferedWriter3 = new BufferedWriter( fileWriter3);
			BufferedWriter bufferedWriter4 = new BufferedWriter( fileWriter4);
			BufferedWriter bufferedWriter5 = new BufferedWriter( fileWriter5);
			BufferedWriter bufferedWriter6 = new BufferedWriter( fileWriter6);
			BufferedWriter bufferedWriter7 = new BufferedWriter( fileWriter7);
			BufferedWriter bufferedWriter8 = new BufferedWriter( fileWriter8);
			BufferedWriter bufferedWriter9 = new BufferedWriter( fileWriter9);
			BufferedWriter bufferedWriter10 = new BufferedWriter( fileWriter10);
			BufferedWriter bufferedWriter11 = new BufferedWriter( fileWriter11);
			BufferedWriter bufferedWriter12 = new BufferedWriter( fileWriter12);
			BufferedWriter bufferedWriter13 = new BufferedWriter( fileWriter13);
			BufferedWriter bufferedWriter14 = new BufferedWriter( fileWriter14);
			BufferedWriter bufferedWriter15 = new BufferedWriter( fileWriter15);
			BufferedWriter bufferedWriter16 = new BufferedWriter( fileWriter16);
			BufferedWriter bufferedWriter17 = new BufferedWriter( fileWriter17);
			BufferedWriter bufferedWriter18 = new BufferedWriter( fileWriter18);
			BufferedWriter bufferedWriter19 = new BufferedWriter( fileWriter19);
			BufferedWriter bufferedWriter20 = new BufferedWriter( fileWriter20);
			BufferedWriter bufferedWriter21 = new BufferedWriter( fileWriter21);
			BufferedWriter bufferedWriter22 = new BufferedWriter( fileWriter22);
			BufferedWriter bufferedWriter23 = new BufferedWriter( fileWriter23);
			BufferedWriter bufferedWriter24 = new BufferedWriter( fileWriter24);

			bufferedWriterList.add( bufferedWriter1);
			bufferedWriterList.add( bufferedWriter2);
			bufferedWriterList.add( bufferedWriter3);
			bufferedWriterList.add( bufferedWriter4);
			bufferedWriterList.add( bufferedWriter5);
			bufferedWriterList.add( bufferedWriter6);
			bufferedWriterList.add( bufferedWriter7);
			bufferedWriterList.add( bufferedWriter8);
			bufferedWriterList.add( bufferedWriter9);
			bufferedWriterList.add( bufferedWriter10);
			bufferedWriterList.add( bufferedWriter11);
			bufferedWriterList.add( bufferedWriter12);
			bufferedWriterList.add( bufferedWriter13);
			bufferedWriterList.add( bufferedWriter14);
			bufferedWriterList.add( bufferedWriter15);
			bufferedWriterList.add( bufferedWriter16);
			bufferedWriterList.add( bufferedWriter17);
			bufferedWriterList.add( bufferedWriter18);
			bufferedWriterList.add( bufferedWriter19);
			bufferedWriterList.add( bufferedWriter20);
			bufferedWriterList.add( bufferedWriter21);
			bufferedWriterList.add( bufferedWriter22);
			bufferedWriterList.add( bufferedWriter23);
			bufferedWriterList.add( bufferedWriter24);

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
	}

	public void writeChromBaseSearchInputFile(
			ChromosomeName chromName, 
			String strLine,
			TIntObjectMap<BufferedWriter> chrNumber2BufferedWriterMap) {

		try{

			chrNumber2BufferedWriterMap.get( chromName.getChromosomeName()).write(strLine + System.getProperty( "line.separator"));

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
	}

	public void partitionSearchInputFilePerChromName( 
			String inputFileName,
			TIntObjectMap<BufferedWriter> chrNumber2BufferedWriterMap) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		ChromosomeName chromName;
		int low;
		int high;

		try{
			fileReader = FileOperations.createFileReader( inputFileName);
			bufferedReader = new BufferedReader( fileReader);

			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = (indexofFirstTab>-1)?strLine.indexOf('\t',indexofFirstTab+1):-1;
				
				chromName = ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab));
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				
				//12 NOV 2015
				//For running BroadEnrich input file
				//Before there was no condition
				//if((high-low+1) <= Commons.GC_ISOCHORE_MOVING_WINDOW_SIZE){
				//Simply since GLANET Convention is 0BasedStart and 0BasedEndInclusive
				if((high-low) < Commons.GC_ISOCHORE_MOVING_WINDOW_SIZE){
					writeChromBaseSearchInputFile( chromName, strLine, chrNumber2BufferedWriterMap);
				}
				
				
			} // End of While

		}catch( FileNotFoundException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		try{
			bufferedReader.close();
			fileReader.close();
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
	}

	// Generate Interval Tree with numbers for the given cellLines only starts
	public static IntervalTree generateEncodeDnaseIntervalTreeWithNumbers( BufferedReader bufferedReader,
			TIntList cellLineNumberList) {

		IntervalTree dnaseIntervalTree = new IntervalTree();
		String strLine = null;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;

		int startPosition = 0;
		int endPosition = 0;

		ChromosomeName chromName;
		short cellLineNumber;
		short fileNumber;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){

				// old example strLine
				// chr1 91852781 91853156 GM12878
				// idrPool.GM12878-DS9432-DS10671.z_OV_GM12878-DS10671.z_VS_GM12878-DS9432.z.npk2.narrowPeak

				// new example line with numbers
				// chrY 2709520 2709669 1 1

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);

				chromName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				cellLineNumber = Short.parseShort( strLine.substring( indexofThirdTab + 1, indexofFourthTab));
				fileNumber = Short.parseShort( strLine.substring( indexofFourthTab + 1));

				// important note
				// while constructing the dnaseIntervalTree
				// we don't check for overlaps
				// we insert any given interval without overlap check

				if( cellLineNumberList.contains( cellLineNumber)){
					DnaseIntervalTreeNodeWithNumbers node = new DnaseIntervalTreeNodeWithNumbers( chromName,
							startPosition, endPosition, cellLineNumber, fileNumber, NodeType.ORIGINAL);
					dnaseIntervalTree.intervalTreeInsert( dnaseIntervalTree, node);
				}

			} // End of While
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return dnaseIntervalTree;
	}

	// Generate Interval Tree with numbers for the given cellLines only ends

	// Generate Dnase Interval Tree with Numbers starts
	// For Annotation and Enrichment
	// Empirical P Value Calculation
	public static IntervalTree generateEncodeDnaseIntervalTreeWithNumbers( BufferedReader bufferedReader) {

		IntervalTree dnaseIntervalTree = new IntervalTree();
		String strLine = null;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;

		int startPosition = 0;
		int endPosition = 0;

		ChromosomeName chromName;
		short cellLineNumber;
		short fileNumber;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){

				// old example strLine
				// chr1 91852781 91853156 GM12878
				// idrPool.GM12878-DS9432-DS10671.z_OV_GM12878-DS10671.z_VS_GM12878-DS9432.z.npk2.narrowPeak

				// new example line with numbers
				// chrY 2709520 2709669 1 1

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);

				chromName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				cellLineNumber = Short.parseShort( strLine.substring( indexofThirdTab + 1, indexofFourthTab));
				fileNumber = Short.parseShort( strLine.substring( indexofFourthTab + 1));

				// important note
				// while constructing the dnaseIntervalTree
				// we don't check for overlaps
				// we insert any given interval without overlap check

				// Creating millions of nodes with six attributes causes out of
				// memory error
				DnaseIntervalTreeNodeWithNumbers node = new DnaseIntervalTreeNodeWithNumbers( chromName, startPosition,
						endPosition, cellLineNumber, fileNumber, NodeType.ORIGINAL);
				dnaseIntervalTree.intervalTreeInsert( dnaseIntervalTree, node);

			} // End of WHILE
			
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return dnaseIntervalTree;
	}

	// Generate Interval Tree with Numbers ends

	// Empirical P Value Calculation
	public static IntervalTree generateEncodeDnaseIntervalTree( BufferedReader bufferedReader) {

		IntervalTree dnaseIntervalTree = new IntervalTree();
		String strLine = null;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;

		int startPosition = 0;
		int endPosition = 0;

		String chromName;
		String cellLineName;
		String fileName;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){
				// example strLine
				// chr1 91852781 91853156 GM12878
				// idrPool.GM12878-DS9432-DS10671.z_OV_GM12878-DS10671.z_VS_GM12878-DS9432.z.npk2.narrowPeak

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);

				chromName = strLine.substring( 0, indexofFirstTab);

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				cellLineName = strLine.substring( indexofThirdTab + 1, indexofFourthTab);

				fileName = strLine.substring( indexofFourthTab + 1);

				// important note
				// while constructing the dnaseIntervalTree
				// we don't check for overlaps
				// we insert any given interval without overlap check

				// Creating millions of nodes with six attributes causes out of
				// memory error
				IntervalTreeNode node = new DnaseIntervalTreeNode( ChromosomeName.convertStringtoEnum( chromName),
						startPosition, endPosition, cellLineName, fileName, NodeType.ORIGINAL);
				dnaseIntervalTree.intervalTreeInsert( dnaseIntervalTree, node);

				chromName = null;
				cellLineName = null;
				fileName = null;
				strLine = null;

			} // End of While
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return dnaseIntervalTree;
	}

	public IntervalTree generateEncodeDnaseIntervalTree( BufferedReader bufferedReader,
			List<String> dnaseCellLineNameList) {

		IntervalTree dnaseIntervalTree = new IntervalTree();
		String strLine = null;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;

		int startPosition = 0;
		int endPosition = 0;

		String chromName;
		String cellLineName;
		String fileName;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){
				// example strLine
				// chr1 91852781 91853156 GM12878
				// idrPool.GM12878-DS9432-DS10671.z_OV_GM12878-DS10671.z_VS_GM12878-DS9432.z.npk2.narrowPeak

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);

				chromName = strLine.substring( 0, indexofFirstTab);

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				cellLineName = strLine.substring( indexofThirdTab + 1, indexofFourthTab);

				fileName = strLine.substring( indexofFourthTab + 1);

				// important note
				// while constructing the dnaseIntervalTree
				// we don't check for overlaps
				// we insert any given interval without overlap check

				// if dnase exists in dnaseList
				if( dnaseCellLineNameList.contains( cellLineName)){
					// Creating millions of nodes with six attributes causes out
					// of memory error
					IntervalTreeNode node = new DnaseIntervalTreeNode( ChromosomeName.convertStringtoEnum( chromName),
							startPosition, endPosition, cellLineName, fileName, NodeType.ORIGINAL);
					dnaseIntervalTree.intervalTreeInsert( dnaseIntervalTree, node);
				} // End of If

				chromName = null;
				cellLineName = null;
				fileName = null;
				strLine = null;

			} // End of While
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return dnaseIntervalTree;
	}

	// starts
	public static IntervalTree generateUserDefinedLibraryIntervalTreeWithNumbers( BufferedReader bufferedReader) {

		IntervalTree userDefinedLibraryIntervalTree = new IntervalTree();
		String strLine;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;

		ChromosomeName chromName;
		int startPosition = 0;
		int endPosition = 0;
		int elementTypeNumber;
		int elementNumber;
		int fileNumber;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){
				// example strLine
				// chr6 133593985 133594135 1 1 1
				// chrX 109134213 109134446 1 1 343

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
				indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
				indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
				indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;

				chromName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));
				elementTypeNumber = Integer.parseInt( strLine.substring( indexofThirdTab + 1, indexofFourthTab));
				elementNumber = Integer.parseInt( strLine.substring( indexofFourthTab + 1, indexofFifthTab));
				fileNumber = Integer.parseInt( strLine.substring( indexofFifthTab + 1));

				// Creating millions of nodes with six attributes causes out of memory error
				UserDefinedLibraryIntervalTreeNodeWithNumbers node = new UserDefinedLibraryIntervalTreeNodeWithNumbers(
						chromName, startPosition, endPosition, elementTypeNumber, elementNumber, fileNumber,
						NodeType.ORIGINAL);
				userDefinedLibraryIntervalTree.intervalTreeInsert( userDefinedLibraryIntervalTree, node);

				chromName = null;
				strLine = null;

			}//End of WHILE
			
		}catch( NumberFormatException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return userDefinedLibraryIntervalTree;

	}

	// ends

	// @todo
	// Empirical P Value Calculation
	public static IntervalTree generateEncodeTfbsIntervalTreeWithNumbers( BufferedReader bufferedReader) {

		IntervalTree tfbsIntervalTree = new IntervalTree();
		String strLine;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;

		int startPosition = 0;
		int endPosition = 0;

		ChromosomeName chromName;
		Short tfNumber;
		Short cellLineNumber;
		Short fileNumber;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){
				// exampple strLine
				// chrY 2804079 2804213 Ctcf H1hesc
				// spp.optimal.wgEncodeBroadHistoneH1hescCtcfStdAlnRep0_VS_wgEncodeBroadHistoneH1hescControlStdAlnRep0.narrowPeak

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf( '\t', indexofFourthTab + 1);

				chromName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				tfNumber = Short.parseShort( strLine.substring( indexofThirdTab + 1, indexofFourthTab));

				cellLineNumber = Short.parseShort( strLine.substring( indexofFourthTab + 1, indexofFifthTab));

				fileNumber = Short.parseShort( strLine.substring( indexofFifthTab + 1));

				// Creating millions of nodes with six attributes causes out of
				// memory error
				TforHistoneIntervalTreeNodeWithNumbers node = new TforHistoneIntervalTreeNodeWithNumbers( chromName,
						startPosition, endPosition, tfNumber, cellLineNumber, fileNumber, NodeType.ORIGINAL);
				tfbsIntervalTree.intervalTreeInsert( tfbsIntervalTree, node);

				chromName = null;
				strLine = null;

			}//END OF WHILE
			
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return tfbsIntervalTree;
	}

	// @todo

	// Empirical P Value Calculation
	public static IntervalTree generateEncodeTfbsIntervalTree( BufferedReader bufferedReader) {

		IntervalTree tfbsIntervalTree = new IntervalTree();
		String strLine;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;

		int startPosition = 0;
		int endPosition = 0;

		String chromName;
		String tfbsName;
		String cellLineName;
		String fileName;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){
				// exampple strLine
				// chrY 2804079 2804213 Ctcf H1hesc
				// spp.optimal.wgEncodeBroadHistoneH1hescCtcfStdAlnRep0_VS_wgEncodeBroadHistoneH1hescControlStdAlnRep0.narrowPeak

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf( '\t', indexofFourthTab + 1);

				chromName = strLine.substring( 0, indexofFirstTab);

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				tfbsName = strLine.substring( indexofThirdTab + 1, indexofFourthTab);

				cellLineName = strLine.substring( indexofFourthTab + 1, indexofFifthTab);

				fileName = strLine.substring( indexofFifthTab + 1);

				// Creating millions of nodes with six attributes causes out of
				// memory error
				IntervalTreeNode node = new TforHistoneIntervalTreeNode(
						ChromosomeName.convertStringtoEnum( chromName), startPosition, endPosition, tfbsName,
						cellLineName, fileName, NodeType.ORIGINAL);
				tfbsIntervalTree.intervalTreeInsert( tfbsIntervalTree, node);

				chromName = null;
				tfbsName = null;
				cellLineName = null;
				fileName = null;
				strLine = null;

			}
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return tfbsIntervalTree;
	}

	public IntervalTree generateEncodeTfbsIntervalTree( BufferedReader bufferedReader, List<String> tfbsNameList) {

		IntervalTree tfbsIntervalTree = new IntervalTree();
		String strLine;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;

		int startPosition = 0;
		int endPosition = 0;

		String chromName;
		String tfbsName;
		String cellLineName;
		String fileName;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){
				// example strLine
				// chrY 2804079 2804213 Ctcf H1hesc
				// spp.optimal.wgEncodeBroadHistoneH1hescCtcfStdAlnRep0_VS_wgEncodeBroadHistoneH1hescControlStdAlnRep0.narrowPeak

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf( '\t', indexofFourthTab + 1);

				chromName = strLine.substring( 0, indexofFirstTab);

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				tfbsName = strLine.substring( indexofThirdTab + 1, indexofFourthTab);

				cellLineName = strLine.substring( indexofFourthTab + 1, indexofFifthTab);

				fileName = strLine.substring( indexofFifthTab + 1);

				// if tfbs exists in tfbsList
				if( tfbsNameList.contains( tfbsName)){
					// Creating millions of nodes with six attributes causes out
					// of memory error
					IntervalTreeNode node = new TforHistoneIntervalTreeNode(
							ChromosomeName.convertStringtoEnum( chromName), startPosition, endPosition, tfbsName,
							cellLineName, fileName, NodeType.ORIGINAL);
					tfbsIntervalTree.intervalTreeInsert( tfbsIntervalTree, node);
				}

				chromName = null;
				tfbsName = null;
				cellLineName = null;
				fileName = null;
				strLine = null;

			}
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return tfbsIntervalTree;
	}

	// @todo
	// Empirical P Value Calculation
	public static IntervalTree generateEncodeHistoneIntervalTreeWithNumbers( BufferedReader bufferedReader) {

		IntervalTree histoneIntervalTree = new IntervalTree();
		String strLine;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;

		ChromosomeName chromName;
		int startPosition = 0;
		int endPosition = 0;

		short histoneNumber;
		short cellLineNumber;
		short fileNumber;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){
				// old example strLine
				// chr9 131533188 131535395 H2az Gm12878
				// wgEncodeBroadHistoneGm12878H2azStdAln.narrowPeak
				// new example strLine
				// chr22 20747267 20749217 1 17 654

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf( '\t', indexofFourthTab + 1);

				chromName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				histoneNumber = Short.parseShort( strLine.substring( indexofThirdTab + 1, indexofFourthTab));

				cellLineNumber = Short.parseShort( strLine.substring( indexofFourthTab + 1, indexofFifthTab));

				fileNumber = Short.parseShort( strLine.substring( indexofFifthTab + 1));

				// Creating millions of nodes with six attributes causes out of
				// memory error
				TforHistoneIntervalTreeNodeWithNumbers node = new TforHistoneIntervalTreeNodeWithNumbers( chromName,
						startPosition, endPosition, histoneNumber, cellLineNumber, fileNumber, NodeType.ORIGINAL);
				histoneIntervalTree.intervalTreeInsert( histoneIntervalTree, node);

				chromName = null;
				strLine = null;

			}//End of WHILE
			
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return histoneIntervalTree;
	}

	// @todo

	// Empirical P Value Calculation
	public static IntervalTree generateEncodeHistoneIntervalTree( BufferedReader bufferedReader) {

		IntervalTree histoneIntervalTree = new IntervalTree();
		String strLine;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;

		String chromName;
		int startPosition = 0;
		int endPosition = 0;

		String histoneName;
		String cellLineName;
		String fileName;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){
				// example strLine
				// chr9 131533188 131535395 H2az Gm12878
				// wgEncodeBroadHistoneGm12878H2azStdAln.narrowPeak

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf( '\t', indexofFourthTab + 1);

				chromName = strLine.substring( 0, indexofFirstTab);

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				histoneName = strLine.substring( indexofThirdTab + 1, indexofFourthTab);

				cellLineName = strLine.substring( indexofFourthTab + 1, indexofFifthTab);

				fileName = strLine.substring( indexofFifthTab + 1);

				// Creating millions of nodes with six attributes causes out of
				// memory error
				IntervalTreeNode node = new TforHistoneIntervalTreeNode(
						ChromosomeName.convertStringtoEnum( chromName), startPosition, endPosition, histoneName,
						cellLineName, fileName, NodeType.ORIGINAL);
				histoneIntervalTree.intervalTreeInsert( histoneIntervalTree, node);

				chromName = null;
				histoneName = null;
				cellLineName = null;
				fileName = null;
				strLine = null;

			}
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return histoneIntervalTree;
	}

	public IntervalTree generateEncodeHistoneIntervalTree( BufferedReader bufferedReader, List<String> histoneNameList) {

		IntervalTree histoneIntervalTree = new IntervalTree();
		String strLine;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;

		String chromName;
		int startPosition = 0;
		int endPosition = 0;

		String histoneName;
		String cellLineName;
		String fileName;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){
				// example strLine
				// chr9 131533188 131535395 H2az Gm12878
				// wgEncodeBroadHistoneGm12878H2azStdAln.narrowPeak

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf( '\t', indexofFourthTab + 1);

				chromName = strLine.substring( 0, indexofFirstTab);

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				histoneName = strLine.substring( indexofThirdTab + 1, indexofFourthTab);

				cellLineName = strLine.substring( indexofFourthTab + 1, indexofFifthTab);

				fileName = strLine.substring( indexofFifthTab + 1);

				if( histoneNameList.contains( histoneName)){
					// Creating millions of nodes with six attributes causes out
					// of memory error
					IntervalTreeNode node = new TforHistoneIntervalTreeNode(
							ChromosomeName.convertStringtoEnum( chromName), startPosition, endPosition, histoneName,
							cellLineName, fileName, NodeType.ORIGINAL);
					histoneIntervalTree.intervalTreeInsert( histoneIntervalTree, node);
				}

				chromName = null;
				histoneName = null;
				cellLineName = null;
				fileName = null;
				strLine = null;

			}
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return histoneIntervalTree;
	}

	// @todo
	public static IntervalTree generateUcscRefSeqGenesIntervalTreeWithNumbers( BufferedReader bufferedReader) {

		IntervalTree tree = new IntervalTree();
		String strLine;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		int indexofSixthTab = 0;
		int indexofSeventhTab = 0;
		int indexofEighthTab = 0;

		int startPosition = 0;
		int endPosition = 0;

		ChromosomeName chromName;
		IntervalName intervalName;
		Integer intervalNumber;

		Integer geneEntrezId;
		Integer refSeqGeneNameNumber;
		Integer geneHugoSymbolNumber;

		try{
			
			while( ( strLine = bufferedReader.readLine()) != null){
				// example strLine
				// chrY 16733888 16734470 NR_028319 22829 EXON 2 + NLGN4Y
				// chr22 25170288 25170686 38084 440822 EXON 21 - 22048
				// chr22 25170687 25172686 38084 440822 5P1 - 22048

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf( '\t', indexofFourthTab + 1);
				indexofSixthTab = strLine.indexOf( '\t', indexofFifthTab + 1);
				indexofSeventhTab = strLine.indexOf( '\t', indexofSixthTab + 1);
				indexofEighthTab = strLine.indexOf( '\t', indexofSeventhTab + 1);

				chromName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				refSeqGeneNameNumber = Integer.parseInt( strLine.substring( indexofThirdTab + 1, indexofFourthTab));
				geneEntrezId = Integer.parseInt( strLine.substring( indexofFourthTab + 1, indexofFifthTab));

				intervalName = IntervalName.convertStringtoEnum( strLine.substring( indexofFifthTab + 1,
						indexofSixthTab));
				intervalNumber = Integer.parseInt( strLine.substring( indexofSixthTab + 1, indexofSeventhTab));

				geneHugoSymbolNumber = Integer.parseInt( strLine.substring( indexofEighthTab + 1));

				// Creating millions of nodes with seven attributes causes out
				// of memory error
				UcscRefSeqGeneIntervalTreeNodeWithNumbers node = new UcscRefSeqGeneIntervalTreeNodeWithNumbers(
						chromName, startPosition, endPosition, geneEntrezId, refSeqGeneNameNumber,
						geneHugoSymbolNumber, intervalName, intervalNumber, NodeType.ORIGINAL);
				tree.intervalTreeInsert( tree, node);

				chromName = null;
				intervalName = null;

				geneEntrezId = null;
				refSeqGeneNameNumber = null;
				geneHugoSymbolNumber = null;

				strLine = null;

			}//End of WHILE
			
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return tree;
	}

	// @todo

	public static IntervalTree generateUcscRefSeqGenesIntervalTree( BufferedReader bufferedReader) {

		IntervalTree tree = new IntervalTree();
		String strLine;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		int indexofSixthTab = 0;
		int indexofSeventhTab = 0;
		int indexofEighthTab = 0;

		int startPosition = 0;
		int endPosition = 0;

		ChromosomeName chromName;
		String refSeqGeneName;
		Integer geneEntrezId;
		IntervalName intervalName;
		int intervalNumber;
		String geneHugoSymbol;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){
				// example strLine
				// chr17 67074846 67075215 NM_080284 23460 Exon 1 - ABCA6

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf( '\t', indexofFourthTab + 1);
				indexofSixthTab = strLine.indexOf( '\t', indexofFifthTab + 1);
				indexofSeventhTab = strLine.indexOf( '\t', indexofSixthTab + 1);
				indexofEighthTab = strLine.indexOf( '\t', indexofSeventhTab + 1);

				chromName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				refSeqGeneName = strLine.substring( indexofThirdTab + 1, indexofFourthTab);

				geneEntrezId = Integer.parseInt( strLine.substring( indexofFourthTab + 1, indexofFifthTab));

				intervalName = IntervalName.convertStringtoEnum( strLine.substring( indexofFifthTab + 1,
						indexofSixthTab));
				intervalNumber = Integer.parseInt( strLine.substring( indexofSixthTab + 1, indexofSeventhTab));

				geneHugoSymbol = strLine.substring( indexofEighthTab + 1);

				// Creating millions of nodes with seven attributes causes out
				// of memory error
				IntervalTreeNode node = new UcscRefSeqGeneIntervalTreeNode( chromName, startPosition, endPosition,
						refSeqGeneName, geneEntrezId, intervalName, intervalNumber, geneHugoSymbol, NodeType.ORIGINAL);
				tree.intervalTreeInsert( tree, node);

				chromName = null;
				refSeqGeneName = null;
				geneEntrezId = null;
				intervalName = null;
				geneHugoSymbol = null;
				strLine = null;
			}// end of While
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return tree;
	}

	public static IntervalTree createUserDefinedIntervalTreeWithNumbers( 
			String dataFolder, 
			int elementTypeNumber,
			String elementType, 
			ChromosomeName chromName) {

		IntervalTree userDefinedLibraryIntervalTree = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try{

			fileReader = FileOperations.createFileReader(
					dataFolder + Commons.USER_DEFINED_LIBRARY + System.getProperty( "file.separator") + elementType + System.getProperty( "file.separator"),
					chromName.convertEnumtoString() + Commons.UNSORTED_USERDEFINEDLIBRARY_FILE_WITH_NUMBERS);
			
			bufferedReader = new BufferedReader( fileReader);
			
			userDefinedLibraryIntervalTree = generateUserDefinedLibraryIntervalTreeWithNumbers(bufferedReader);
			
			//Close BufferedReader
			bufferedReader.close();

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return userDefinedLibraryIntervalTree;

	}

	// Generate Interval Tree with numbers for the given cellLines only starts
	public static IntervalTree createDnaseIntervalTreeWithNumbers( 
			String dataFolder, 
			ChromosomeName chromName,
			TIntList cellLineNumberList) {

		IntervalTree dnaseIntervalTree = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try{

			fileReader = FileOperations.createFileReader( dataFolder + Commons.BYGLANET_ENCODE_DNASE_DIRECTORY,
					chromName.convertEnumtoString() + Commons.UNSORTED_ENCODE_DNASE_FILE_WITH_NUMBERS);
			bufferedReader = new BufferedReader( fileReader);
			dnaseIntervalTree = generateEncodeDnaseIntervalTreeWithNumbers( bufferedReader, cellLineNumberList);

		}catch( FileNotFoundException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return dnaseIntervalTree;
	}

	// Generate Interval Tree with numbers for the given cellLines only ends

	// Generate Interval Tree
	// With Number starts
	// For Annotation and Enrichment
	public static IntervalTree createDnaseIntervalTreeWithNumbers(
			String dataFolder, 
			ChromosomeName chromName) {

		IntervalTree dnaseIntervalTree = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try{

			fileReader = FileOperations.createFileReader( 
					dataFolder + Commons.BYGLANET_ENCODE_DNASE_DIRECTORY,
					chromName.convertEnumtoString() + Commons.UNSORTED_ENCODE_DNASE_FILE_WITH_NUMBERS);
			
			bufferedReader = new BufferedReader( fileReader);
			
			dnaseIntervalTree = generateEncodeDnaseIntervalTreeWithNumbers(bufferedReader);
			
			//Close bufferedReader
			bufferedReader.close();

		}catch( FileNotFoundException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return dnaseIntervalTree;
	}

	// With Number ends

	// @todo
	// Empirical P Value Calculation
	public static IntervalTree createTfbsIntervalTreeWithNumbers( String dataFolder, ChromosomeName chromName) {

		IntervalTree tfbsIntervalTree = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try{

			fileReader = FileOperations.createFileReader(
					dataFolder + Commons.BYGLANET_ENCODE_TF_DIRECTORY,
					chromName.convertEnumtoString() + Commons.UNSORTED_ENCODE_TF_FILE_WITH_NUMBERS);
			
			bufferedReader = new BufferedReader( fileReader);
			
			tfbsIntervalTree = generateEncodeTfbsIntervalTreeWithNumbers( bufferedReader);
			
			//Close BufferedReader
			bufferedReader.close();

		}catch( FileNotFoundException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return tfbsIntervalTree;
	}

	// @todo

	// @todo @test
	// Empirical P Value Calculation
	public static IntervalTree createHistoneIntervalTreeWithNumbers( String dataFolder, ChromosomeName chromName) {

		IntervalTree histoneIntervalTree = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try{
			fileReader = FileOperations.createFileReader( dataFolder + Commons.BYGLANET_ENCODE_HISTONE_DIRECTORY,
					chromName.convertEnumtoString() + Commons.UNSORTED_ENCODE_HISTONE_FILE_WITH_NUMBERS);
			
			bufferedReader = new BufferedReader( fileReader);
			
			histoneIntervalTree = generateEncodeHistoneIntervalTreeWithNumbers( bufferedReader);
			
			//Close bufferedReader
			bufferedReader.close();

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return histoneIntervalTree;
	}

	// @todo @test

	public static IntervalTree createUcscRefSeqGenesIntervalTreeWithNumbers( String dataFolder, ChromosomeName chromName) {

		IntervalTree ucscRefSeqGenesIntervalTree = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try{
			fileReader = FileOperations.createFileReader(
					dataFolder + Commons.BYGLANET_UCSCGENOME_HG19_REFSEQ_GENES_DIRECTORY,
					chromName.convertEnumtoString() + Commons.UNSORTED_UCSCGENOME_HG19_REFSEQ_GENES_FILE_WITH_NUMBERS);
			
			bufferedReader = new BufferedReader( fileReader);
			
			ucscRefSeqGenesIntervalTree = generateUcscRefSeqGenesIntervalTreeWithNumbers(bufferedReader);
			
			//Close bufferedReader
			bufferedReader.close();

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		return ucscRefSeqGenesIntervalTree;

	}

	// 29 June 2015
	// Search Dnase
	// With IO
	// With Numbers
	// For All Chromosomes
	public static void searchDnaseWithIOWithNumbersForAllChromosomes( 
			String outputFolder, 
			int permutationNumber,
			TIntObjectMap<List<InputLineMinimal>> chrNumber2RandomlyGeneratedData,
			TIntObjectMap<IntervalTree> chrNumber2DnaseIntervalTreeMap,
			TIntObjectMap<BufferedWriter> dnaseBufferedWriterHashMap, 
			TIntIntMap dnaseCellLineNumber2PermutationKMap,
			int overlapDefinition) {

		ChromosomeName chromName;

		List<InputLineMinimal> inputLines;
		InputLineMinimal inputLine;

		IntervalTree dnaseIntervalTree;

		for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

			chromName = GRCh37Hg19Chromosome.getChromosomeName( chrNumber);
			inputLines = chrNumber2RandomlyGeneratedData.get( chrNumber);
			dnaseIntervalTree = chrNumber2DnaseIntervalTreeMap.get( chrNumber);

			for( int i = 0; i < inputLines.size(); i++){

				TIntByteMap dnaseCellLineNumber2PermutationZeroorOneMap = new TIntByteHashMap();

				inputLine = inputLines.get( i);

				if( dnaseIntervalTree.getRoot().getNodeName().isNotSentinel()){
					
					dnaseIntervalTree.findAllOverlappingDnaseIntervalsWithIOWithNumbers(
							outputFolder,
							permutationNumber, 
							dnaseIntervalTree.getRoot(), 
							inputLine, 
							chromName,
							dnaseBufferedWriterHashMap, 
							dnaseCellLineNumber2PermutationZeroorOneMap, 
							overlapDefinition);
				}

				// accumulate search results of dnaseCellLine2OneorZeroMap in
				// permutationNumberDnaseCellLineName2KMap
				for( TIntByteIterator it = dnaseCellLineNumber2PermutationZeroorOneMap.iterator(); it.hasNext();){

					it.advance();

					if( !( dnaseCellLineNumber2PermutationKMap.containsKey( it.key()))){
						dnaseCellLineNumber2PermutationKMap.put( it.key(), it.value());
					}else{
						dnaseCellLineNumber2PermutationKMap.put( it.key(),
								dnaseCellLineNumber2PermutationKMap.get( it.key()) + it.value());
					}

				}// End of for

				dnaseCellLineNumber2PermutationZeroorOneMap = null;

			}// End of for each Randomly Generated Interval

		} // End of FOR each CHROMOSOME

	}

	// 29 June 2015

	// Enrichment
	// With IO
	// With Numbers
	// Empirical P Value Calculation
	// with IO
	public static void searchDnaseWithIOWithNumbers( String outputFolder, int permutationNumber,
			ChromosomeName chromName, List<InputLineMinimal> inputLines, IntervalTree dnaseIntervalTree,
			TIntObjectMap<BufferedWriter> dnaseBufferedWriterHashMap,
			TIntIntMap permutationNumberDnaseCellLineNumber2KMap, int overlapDefinition) {

		InputLineMinimal inputLine;

		for( int i = 0; i < inputLines.size(); i++){
			TIntIntMap permutationNumberDnaseCellLineNumber2ZeroorOneMap = new TIntIntHashMap();

			inputLine = inputLines.get( i);

			if( dnaseIntervalTree.getRoot().getNodeName().isNotSentinel()){
				dnaseIntervalTree.findAllOverlappingDnaseIntervalsWithIOWithNumbers( outputFolder, permutationNumber,
						dnaseIntervalTree.getRoot(), inputLine, chromName, dnaseBufferedWriterHashMap,
						permutationNumberDnaseCellLineNumber2ZeroorOneMap, overlapDefinition);
			}

			// accumulate search results of dnaseCellLine2OneorZeroMap in
			// permutationNumberDnaseCellLineName2KMap
			for( TIntIntIterator it = permutationNumberDnaseCellLineNumber2ZeroorOneMap.iterator(); it.hasNext();){
				it.advance();

				if( !( permutationNumberDnaseCellLineNumber2KMap.containsKey( it.key()))){
					permutationNumberDnaseCellLineNumber2KMap.put( it.key(), it.value());
				}else{
					permutationNumberDnaseCellLineNumber2KMap.put( it.key(),permutationNumberDnaseCellLineNumber2KMap.get(it.key()) + it.value());
				}

			}// End of for

		}// End of for
	}

	// with numbers ends
	// @todo

	
	
	// 3 July 2015
	// TF
	// HISTONE
	// Enrichment
	// Without IO
	// With Numbers
	// For All Chromosomes
	public static void searchTForHistoneWithoutIOWithNumbersForAllChromosomes( 
			int permutationNumber,
			TIntObjectMap<List<InputLineMinimal>> chrNumber2InputLines,
			TIntObjectMap<IntervalTree> chrNumber2TFIntervalTreeMap, 
			TIntIntMap tforHistoneNumberCellLineNumber2PermutationKMap,
			int overlapDefinition) {

		ChromosomeName chromName;

		List<InputLineMinimal> inputLines;
		InputLineMinimal inputLine;

		IntervalTree tforHistoneIntervalTree;

		for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

			chromName = GRCh37Hg19Chromosome.getChromosomeName(chrNumber);
			inputLines = chrNumber2InputLines.get(chrNumber);
			tforHistoneIntervalTree = chrNumber2TFIntervalTreeMap.get(chrNumber);

			if( inputLines != null){

				for( int i = 0; i < inputLines.size(); i++){

					TIntByteMap tforHistoneNumberCellLineNumber2PermutationZeroorOneMap = new TIntByteHashMap();

					inputLine = inputLines.get( i);

					if( tforHistoneIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						tforHistoneIntervalTree.findAllOverlappingTForHistoneIntervalsWithoutIOWithNumbers(
								tforHistoneIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								tforHistoneNumberCellLineNumber2PermutationZeroorOneMap, 
								overlapDefinition);
					}

					// accumulate search results of dnaseCellLineNumber2PermutationZeroorOneMap in
					// dnaseCellLineNumber2PermutationKMap
					for( TIntByteIterator it = tforHistoneNumberCellLineNumber2PermutationZeroorOneMap.iterator(); it.hasNext();){

						it.advance();

						if( !(tforHistoneNumberCellLineNumber2PermutationKMap.containsKey(it.key()))){
							tforHistoneNumberCellLineNumber2PermutationKMap.put(it.key(), it.value());
						}else{
							tforHistoneNumberCellLineNumber2PermutationKMap.put(it.key(),tforHistoneNumberCellLineNumber2PermutationKMap.get(it.key()) + it.value());
						}

					}// End of FOR

					// Free space
					tforHistoneNumberCellLineNumber2PermutationZeroorOneMap = null;

				}// End of FOR each Randomly Generated Interval

			}// End of IF inputLines is not NULL

		}// End of FOR each Chromosome

	}
	// 3 July 2015

	// 26 June 2015
	// Enrichment
	// Without IO
	// With Numbers
	// For All Chromosomes
	public static void searchDnaseWithoutIOWithNumbersForAllChromosomes( int permutationNumber,
			TIntObjectMap<List<InputLineMinimal>> chrNumber2InputLines,
			TIntObjectMap<IntervalTree> chrNumber2DnaseIntervalTreeMap, TIntIntMap dnaseCellLineNumber2PermutationKMap,
			int overlapDefinition) {

		ChromosomeName chromName;

		List<InputLineMinimal> inputLines;
		InputLineMinimal inputLine;

		IntervalTree dnaseIntervalTree;

		for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

			chromName = GRCh37Hg19Chromosome.getChromosomeName( chrNumber);
			inputLines = chrNumber2InputLines.get( chrNumber);
			dnaseIntervalTree = chrNumber2DnaseIntervalTreeMap.get( chrNumber);

			if( inputLines != null){

				for( int i = 0; i < inputLines.size(); i++){

					TIntByteMap dnaseCellLineNumber2PermutationZeroorOneMap = new TIntByteHashMap();

					inputLine = inputLines.get( i);

					if( dnaseIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						dnaseIntervalTree.findAllOverlappingDnaseIntervalsWithoutIOWithNumbers( permutationNumber,
								dnaseIntervalTree.getRoot(), inputLine, chromName,
								dnaseCellLineNumber2PermutationZeroorOneMap, overlapDefinition);
					}

					// accumulate search results of dnaseCellLineNumber2PermutationZeroorOneMap in
					// dnaseCellLineNumber2PermutationKMap
					for( TIntByteIterator it = dnaseCellLineNumber2PermutationZeroorOneMap.iterator(); it.hasNext();){

						it.advance();

						if( !( dnaseCellLineNumber2PermutationKMap.containsKey( it.key()))){
							dnaseCellLineNumber2PermutationKMap.put( it.key(), it.value());
						}else{
							dnaseCellLineNumber2PermutationKMap.put( it.key(),
									dnaseCellLineNumber2PermutationKMap.get( it.key()) + it.value());
						}

					}// End of FOR

					// Free space
					dnaseCellLineNumber2PermutationZeroorOneMap = null;

				}// End of FOR each Randomly Generated Interval

			}// End of IF inputLines is not NULL

		}// End of FOR each Chromosome

	}

	// 26 June 2015

	// Enrichment
	// Without IO
	// With Numbers
	// Empirical P Value Calculation
	public static void searchDnaseWithoutIOWithNumbers( 
			int permutationNumber, 
			ChromosomeName chromName,
			List<InputLineMinimal> inputLines, 
			IntervalTree dnaseIntervalTree,
			TIntIntMap permutationNumberDnaseCellLineNumber2KMap, 
			AssociationMeasureType associationMeasureType,
			int overlapDefinition) {

		InputLineMinimal inputLine;
		
		
		for( int i = 0; i < inputLines.size(); i++){
			
			inputLine = inputLines.get( i);
			
			switch(associationMeasureType){
			
				case EXISTENCE_OF_OVERLAP: 
					/*************************************************************************************************/
					/***********************************EXISTENCE_OF_OVERLAP starts***********************************/
					/*************************************************************************************************/	
					TIntIntMap permutationNumberDnaseCellLineName2ZeroorOneMap = new TIntIntHashMap();

					if( dnaseIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						dnaseIntervalTree.findAllOverlappingDnaseIntervalsWithoutIOWithNumbers(
								permutationNumber,
								dnaseIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								permutationNumberDnaseCellLineName2ZeroorOneMap, 
								overlapDefinition);
					}

					// accumulate search results of dnaseCellLine2OneorZeroMap in permutationNumberDnaseCellLineName2KMap
					for( TIntIntIterator it = permutationNumberDnaseCellLineName2ZeroorOneMap.iterator(); it.hasNext();){

						it.advance();

						if( !( permutationNumberDnaseCellLineNumber2KMap.containsKey( it.key()))){
							permutationNumberDnaseCellLineNumber2KMap.put( it.key(), it.value());
						}else{
							permutationNumberDnaseCellLineNumber2KMap.put( it.key(),
									permutationNumberDnaseCellLineNumber2KMap.get( it.key()) + it.value());
						}

					}// End of for


					//Free memory
					permutationNumberDnaseCellLineName2ZeroorOneMap = null;
					/*************************************************************************************************/
					/***********************************EXISTENCE_OF_OVERLAP ends*************************************/
					/*************************************************************************************************/					
					break;
					
				case NUMBER_OF_OVERLAPPING_BASES:
					/*************************************************************************************************/
					/*******************************NUMBER_OF_OVERLAPPING_BASES starts********************************/
					/*************************************************************************************************/
					TIntObjectMap<List<IntervalTreeNode>> permutationNumberDnaseCellLineNumber2OverlappingNodeListMap = new TIntObjectHashMap<List<IntervalTreeNode>>();
					TIntObjectMap<IntervalTree> permutationNumberDnaseCellLineNumber2IntervalTreeWithNonOverlappingNodesMap = new TIntObjectHashMap<IntervalTree>();
					TIntIntMap permutationNumberDnaseCellLineNumber2NumberofOverlappingBasesMap = new TIntIntHashMap();

					if(dnaseIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						//Step1: Get all the overlappingIntervals with the inputLine
						dnaseIntervalTree.findAllOverlappingDnaseIntervalsWithoutIOWithNumbers(
								permutationNumber,
								dnaseIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								permutationNumberDnaseCellLineNumber2OverlappingNodeListMap);
						
						//Step2: Construct an intervalTree from the overlappingIntervals found in step1 such that there are no overlapping nodes in the tree 
						IntervalTree.constructAnIntervalTreeWithNonOverlappingNodes(
								permutationNumberDnaseCellLineNumber2OverlappingNodeListMap, 
								permutationNumberDnaseCellLineNumber2IntervalTreeWithNonOverlappingNodesMap);
						
						//Step3: Calculate the numberofOverlappingBases by overlapping the inputLine with the nodes in intervalTree
						//And fill permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap
						IntervalTree.findNumberofOverlappingBases(
								inputLine,
								permutationNumberDnaseCellLineNumber2IntervalTreeWithNonOverlappingNodesMap,
								permutationNumberDnaseCellLineNumber2NumberofOverlappingBasesMap);
						
					}//End of IF

					// Accumulate search results of permutationNumberDnaseCellLineNumber2NumberofOverlappingBasesMap in permutationNumberDnaseCellLineNumber2KMap
					for( TIntIntIterator it = permutationNumberDnaseCellLineNumber2NumberofOverlappingBasesMap.iterator(); it.hasNext();){

						it.advance();

						if( !(permutationNumberDnaseCellLineNumber2KMap.containsKey(it.key()))){
							permutationNumberDnaseCellLineNumber2KMap.put(it.key(), it.value());
						}else{
							permutationNumberDnaseCellLineNumber2KMap.put( it.key(),
									permutationNumberDnaseCellLineNumber2KMap.get( it.key()) + it.value());

						}

					}// End of FOR
					
					//Free memory
					permutationNumberDnaseCellLineNumber2OverlappingNodeListMap = null;
					permutationNumberDnaseCellLineNumber2IntervalTreeWithNonOverlappingNodesMap = null;
					permutationNumberDnaseCellLineNumber2NumberofOverlappingBasesMap = null;
					/*************************************************************************************************/
					/*******************************NUMBER_OF_OVERLAPPING_BASES ends**********************************/
					/*************************************************************************************************/
					break;
					
				default:
					break;
				
			}//End of SWITCH
				
		}// End of FOR each inputLine
	}

	// with number ends

	// TShortObjectMap<StringBuilder>
	public void searchDnaseWithNumbersWithStringBuilder( String dataFolder, String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			int[] dnaseCellLineKArray, int overlapDefinition,
			TShortObjectMap<StringBuilder> cellLineNumber2CellLineNameMap,
			TShortObjectMap<StringBuilder> fileNumber2FileNameMap) {

		BufferedReader bufferedReader = null;

		IntervalTree dnaseIntervalTree;

		// For each ChromosomeName
		for( ChromosomeName chrName : ChromosomeName.values()){

			dnaseIntervalTree = createDnaseIntervalTreeWithNumbers( dataFolder, chrName);
			bufferedReader = FileOperations.createBufferedReader(
					outputFolder,
					Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);
			searchDnaseWithNumbersStringBuilder( outputFolder, writeElementBasedAnnotationFoundOverlapsMode, chrName,
					bufferedReader, dnaseIntervalTree, dnaseCellLineKArray, overlapDefinition,
					cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
			dnaseIntervalTree = null;

			System.gc();
			System.runFinalization();

			try{
				// close bufferedReader
				bufferedReader.close();
			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of for each chromosomeName

	}

	// new starts
	// Annotation Sequentially
	// With Numbers
	// Using int array
	public void searchDnaseWithNumbers( String dataFolder, String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			int[] dnaseCellLineKArray, int overlapDefinition,
			TShortObjectMap<CharSequence> cellLineNumber2CellLineNameMap,
			TShortObjectMap<CharSequence> fileNumber2FileNameMap) {

		BufferedReader bufferedReader = null;

		IntervalTree dnaseIntervalTree;

		// For each ChromosomeName
		for( ChromosomeName chrName : ChromosomeName.values()){

			dnaseIntervalTree = createDnaseIntervalTreeWithNumbers( dataFolder, chrName);
			bufferedReader = FileOperations.createBufferedReader(
					outputFolder,
					Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);
			searchDnaseWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode, chrName,
					bufferedReader, dnaseIntervalTree, dnaseCellLineKArray, overlapDefinition,
					cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
			dnaseIntervalTree = null;

			System.gc();
			System.runFinalization();

			try{
				// close bufferedReader
				bufferedReader.close();
			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of for each chromosomeName

	}

	// new ends

	// Annotation Sequentially
	// With Numbers
	// Using int array
	public void searchDnaseWithNumbers( String dataFolder, String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			int[] dnaseCellLineKArray, int overlapDefinition, char[][] dnaseCellLineNames, char[][] fileNames) {

		BufferedReader bufferedReader = null;

		IntervalTree dnaseIntervalTree;

		// For each ChromosomeName
		for( ChromosomeName chrName : ChromosomeName.values()){

			dnaseIntervalTree = createDnaseIntervalTreeWithNumbers( dataFolder, chrName);
			bufferedReader = FileOperations.createBufferedReader(
					outputFolder,
					Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);
			searchDnaseWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode, chrName,
					bufferedReader, dnaseIntervalTree, dnaseCellLineKArray, overlapDefinition, dnaseCellLineNames,
					fileNames);
			dnaseIntervalTree = null;

			System.gc();
			System.runFinalization();

			try{
				// close bufferedReader
				bufferedReader.close();
			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of for each chromosomeName

	}

	// Annotation Sequentially
	// With Numbers
	public void searchDnaseWithNumbers(
			String dataFolder, 
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			TIntIntMap dnaseCellLineNumber2KMap, 
			int overlapDefinition,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap, 
			TIntObjectMap<String> fileNumber2FileNameMap) {

		BufferedReader bufferedReader = null;

		IntervalTree dnaseIntervalTree;

		// For each ChromosomeName
		for( ChromosomeName chrName : ChromosomeName.values()){

			dnaseIntervalTree = createDnaseIntervalTreeWithNumbers( dataFolder, chrName);
			bufferedReader = FileOperations.createBufferedReader(
					outputFolder,
					Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);
			
			searchDnaseWithNumbers(
					outputFolder, 
					writeElementBasedAnnotationFoundOverlapsMode, 
					chrName,
					bufferedReader, 
					dnaseIntervalTree, 
					dnaseCellLineNumber2KMap, 
					overlapDefinition,
					cellLineNumber2CellLineNameMap, 
					fileNumber2FileNameMap);
			
			dnaseIntervalTree = null;

			System.gc();
			System.runFinalization();

			try{
				// close bufferedReader
				bufferedReader.close();
			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of for each chromosomeName

	}

	// searchDnaseWithNumbersWithStringBuilder
	public void searchDnaseWithNumbersStringBuilder( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			ChromosomeName chromName, BufferedReader bufferedReader, IntervalTree dnaseIntervalTree,
			int[] dnaseCellLineKArray, int overlapDefinition,
			TShortObjectMap<StringBuilder> cellLineNumber2CellLineNameMap,
			TShortObjectMap<StringBuilder> fileNumber2FileNameMap) {

		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;

		int low;
		int high;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){

				byte[] oneOrZeroByteArray = new byte[cellLineNumber2CellLineNameMap.size()];

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0)
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				else
					high = low;

				Interval interval = new Interval( low, high);

				if( dnaseIntervalTree.getRoot().getNodeName().isNotSentinel()){

					dnaseIntervalTree.findAllOverlappingDnaseIntervalsWithNumbersStringBuilder( outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, dnaseIntervalTree.getRoot(), interval,
							chromName, oneOrZeroByteArray, overlapDefinition, cellLineNumber2CellLineNameMap,
							fileNumber2FileNameMap);
				}

				Accumulation.accumulate( oneOrZeroByteArray, dnaseCellLineKArray);

			}// End of while
		}catch( NumberFormatException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while

	}

	// yeni starts
	// Annotation Sequentially
	// With Numbers
	// Using TShortObjectMap<CharSequence>
	public void searchDnaseWithNumbers( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			ChromosomeName chromName, BufferedReader bufferedReader, IntervalTree dnaseIntervalTree,
			int[] dnaseCellLineKArray, int overlapDefinition,
			TShortObjectMap<CharSequence> cellLineNumber2CellLineNameMap,
			TShortObjectMap<CharSequence> fileNumber2FileNameMap) {

		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;

		int low;
		int high;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){

				byte[] oneOrZeroByteArray = new byte[cellLineNumber2CellLineNameMap.size()];

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0)
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				else
					high = low;

				Interval interval = new Interval( low, high);

				if( dnaseIntervalTree.getRoot().getNodeName().isNotSentinel()){

					dnaseIntervalTree.findAllOverlappingDnaseIntervalsWithNumbers( outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, dnaseIntervalTree.getRoot(), interval,
							chromName, oneOrZeroByteArray, overlapDefinition, cellLineNumber2CellLineNameMap,
							fileNumber2FileNameMap);
				}

				Accumulation.accumulate( oneOrZeroByteArray, dnaseCellLineKArray);

			}// End of while
		}catch( NumberFormatException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while

	}

	// yeni ends

	// Annotation Sequentially
	// With Numbers
	// Using int array
	public void searchDnaseWithNumbers( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			ChromosomeName chromName, BufferedReader bufferedReader, IntervalTree dnaseIntervalTree,
			int[] dnaseCellLineKArray, int overlapDefinition, char[][] dnaseCellLineNames, char[][] fileNames) {

		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;

		int low;
		int high;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){

				byte[] oneOrZeroByteArray = new byte[dnaseCellLineNames.length];

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0)
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				else
					high = low;

				Interval interval = new Interval( low, high);

				if( dnaseIntervalTree.getRoot().getNodeName().isNotSentinel()){

					dnaseIntervalTree.findAllOverlappingDnaseIntervalsWithNumbers( outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, dnaseIntervalTree.getRoot(), interval,
							chromName, oneOrZeroByteArray, overlapDefinition, dnaseCellLineNames, fileNames);
				}

				Accumulation.accumulate( oneOrZeroByteArray, dnaseCellLineKArray);

			}// End of while
		}catch( NumberFormatException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while

	}

	// Annotation Sequentially
	// With Numbers
	public void searchDnaseWithNumbers(
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			ChromosomeName chromName, 
			BufferedReader bufferedReader, 
			IntervalTree dnaseIntervalTree,
			TIntIntMap dnaseCellLineNumber2KMap, 
			int overlapDefinition,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap, 
			TIntObjectMap<String> fileNumber2FileNameMap) {

		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;

		int low;
		int high;

		try{

			while( ( strLine = bufferedReader.readLine()) != null){

				// Fill the hits for the given interval in the strLine
				// A given interval can return 1 or 0 for a dnaseCellLine
				// Although the given interval can have more than 1 hits with a dnaseCellLine
				TIntByteMap dnaseCellLineNumber2OneorZeroMap = new TIntByteHashMap();

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0)
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				else
					high = low;

				Interval interval = new Interval( low, high);

				if( dnaseIntervalTree.getRoot().getNodeName().isNotSentinel()){

					dnaseIntervalTree.findAllOverlappingDnaseIntervalsWithNumbers(
							outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, 
							dnaseIntervalTree.getRoot(), 
							interval,
							chromName, 
							dnaseCellLineNumber2OneorZeroMap, 
							overlapDefinition,
							cellLineNumber2CellLineNameMap, 
							fileNumber2FileNameMap);
					
				}// End of IF

				for( TIntByteIterator it = dnaseCellLineNumber2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					if( !dnaseCellLineNumber2KMap.containsKey( it.key())){
						dnaseCellLineNumber2KMap.put( it.key(), it.value());
					}else{
						dnaseCellLineNumber2KMap.put( it.key(), dnaseCellLineNumber2KMap.get( it.key()) + it.value());

					}

				}// End of FOR

				// After accumulation set to null
				dnaseCellLineNumber2OneorZeroMap = null;

			}// End of WHILE

		}catch( NumberFormatException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());

		} // End of while

	}

	// @todo ends

	// Enrichment
	// With IO
	// With Numbers
	// Empirical P Value Calculation
	public static void searchUserDefinedLibraryWithIOWithNumbers( String outputFolder, int permutationNumber,
			ChromosomeName chromName, List<InputLineMinimal> inputLines, IntervalTree intervalTree,
			TLongObjectMap<BufferedWriter> permutationNumberElementTypeNumberElementNumberr2BufferedWriterMap,
			TLongIntMap permutationNumberElementTypeNumberElementNumber2KMap, int overlapDefinition) {

		InputLineMinimal inputLine;

		for( int i = 0; i < inputLines.size(); i++){
			TLongIntMap permutationNumberElementTypeNumberElementNumber2ZeroorOneMap = new TLongIntHashMap();

			inputLine = inputLines.get( i);

			if( intervalTree.getRoot().getNodeName().isNotSentinel()){
				intervalTree.findAllOverlappingUserDefinedLibraryIntervalsWithIOWithNumbers( outputFolder,
						permutationNumber, intervalTree.getRoot(), inputLine, chromName,
						permutationNumberElementTypeNumberElementNumberr2BufferedWriterMap,
						permutationNumberElementTypeNumberElementNumber2ZeroorOneMap, overlapDefinition);
			}

			// accumulate search results of tfbsNameandCellLineName2ZeroorOneMap
			// in tfbsNameandCellLineName2KMap
			for( TLongIntIterator it = permutationNumberElementTypeNumberElementNumber2ZeroorOneMap.iterator(); it.hasNext();){

				it.advance();

				if( !( permutationNumberElementTypeNumberElementNumber2KMap.containsKey( it.key()))){
					permutationNumberElementTypeNumberElementNumber2KMap.put( it.key(), it.value());
				}else{
					permutationNumberElementTypeNumberElementNumber2KMap.put( it.key(),
							permutationNumberElementTypeNumberElementNumber2KMap.get( it.key()) + it.value());

				}

			}// End of for
		}// End of for

	}

	// 6 July 2015 
	// Enrichment
	// With IO
	// With Numbers
	// For All Chromosomes
	public static void searchTForHistoneWithIOWithNumbersForAllChromosomes(
		String outputFolder,
		int permutationNumber,
		TIntObjectMap<List<InputLineMinimal>> chrNumber2RandomlyGeneratedData, 
		TIntObjectMap<IntervalTree> chrNumber2TForHistoneIntervalTreeMap,
		TLongObjectMap<BufferedWriter> permutationNumberTForHistoneNumberCellLineNumberKEGGPathwayNumberBufferedWriterHashMap,
		TIntIntMap tforHistoneNumberCellLineNumber2PermutationKMap, 
		int overlapDefinition,
		AnnotationType annotationType){
						
		ChromosomeName chromName;

		List<InputLineMinimal> inputLines;
		InputLineMinimal inputLine;

		IntervalTree tforHistoneIntervalTree;

		for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

			chromName = GRCh37Hg19Chromosome.getChromosomeName( chrNumber);
			inputLines = chrNumber2RandomlyGeneratedData.get( chrNumber);
			tforHistoneIntervalTree = chrNumber2TForHistoneIntervalTreeMap.get( chrNumber);

			for( int i = 0; i < inputLines.size(); i++){

				TIntByteMap tforHistoneNumberCellLineNumber2PermutationZeroorOneMap = new TIntByteHashMap();

				inputLine = inputLines.get( i);

				if( tforHistoneIntervalTree.getRoot().getNodeName().isNotSentinel()){
					
					tforHistoneIntervalTree.findAllOverlappingTForHistoneIntervalsWithIOWithNumbers(
							outputFolder, 
							permutationNumber, 
							tforHistoneIntervalTree.getRoot(), 
							inputLine, 
							chromName,
							permutationNumberTForHistoneNumberCellLineNumberKEGGPathwayNumberBufferedWriterHashMap, 
							tforHistoneNumberCellLineNumber2PermutationZeroorOneMap, 
							overlapDefinition,
							annotationType);
				}

				// accumulate search results of tfNumberCellLine2OneorZeroMap in permutationNumberDnaseCellLineName2KMap
				for( TIntByteIterator it = tforHistoneNumberCellLineNumber2PermutationZeroorOneMap.iterator(); it.hasNext();){

					it.advance();

					if( !( tforHistoneNumberCellLineNumber2PermutationKMap.containsKey( it.key()))){
						tforHistoneNumberCellLineNumber2PermutationKMap.put( it.key(), it.value());
					}else{
						tforHistoneNumberCellLineNumber2PermutationKMap.put( it.key(),
								tforHistoneNumberCellLineNumber2PermutationKMap.get( it.key()) + it.value());
					}

				}// End of for

				tforHistoneNumberCellLineNumber2PermutationZeroorOneMap = null;

			}// End of for each Randomly Generated Interval

		} // End of FOR each CHROMOSOME

	}
	// 6 July 2015 
	
	// Enrichment
	// With IO
	// With Numbers
	// Empirical P Value Calculation
	// with IO
	public static void searchTfbsWithIOWithNumbers( String outputFolder, int permutationNumber,
			ChromosomeName chromName, List<InputLineMinimal> inputLines, IntervalTree tfbsIntervalTree,
			TLongObjectMap<BufferedWriter> tfbsBufferedWriterHashMap,
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap, int overlapDefinition) {

		InputLineMinimal inputLine;

		for( int i = 0; i < inputLines.size(); i++){
			TLongIntMap permutationNumberTfNumberCellLineNumber2ZeroorOneMap = new TLongIntHashMap();

			inputLine = inputLines.get( i);

			if( tfbsIntervalTree.getRoot().getNodeName().isNotSentinel()){
				tfbsIntervalTree.findAllOverlappingTfbsIntervalsWithIOWithNumbers( outputFolder, permutationNumber,
						tfbsIntervalTree.getRoot(), inputLine, chromName, tfbsBufferedWriterHashMap,
						permutationNumberTfNumberCellLineNumber2ZeroorOneMap, overlapDefinition);
			}

			// accumulate search results of tfbsNameandCellLineName2ZeroorOneMap
			// in tfbsNameandCellLineName2KMap
			for( TLongIntIterator it = permutationNumberTfNumberCellLineNumber2ZeroorOneMap.iterator(); it.hasNext();){

				it.advance();

				if( !( permutationNumberTfNumberCellLineNumber2KMap.containsKey( it.key()))){
					permutationNumberTfNumberCellLineNumber2KMap.put( it.key(), it.value());
				}else{
					permutationNumberTfNumberCellLineNumber2KMap.put( it.key(),
							permutationNumberTfNumberCellLineNumber2KMap.get( it.key()) + it.value());

				}

			}// End of for
		}// End of for
	}
	// with Numbers
	// @todo

	
	
	// Enrichment
	// Without IO
	// With Numbers
	// Empirical P Value Calculation
	public static void searchTfbsWithoutIOWithNumbers(
			int permutationNumber, 
			ChromosomeName chromName,
			List<InputLineMinimal> inputLines, 
			IntervalTree tfbsIntervalTree,
			TLongIntMap permutationNumberTFNumberCellLineNumber2KMap, 
			AssociationMeasureType associationMeasureType,
			int overlapDefinition) {

		InputLineMinimal inputLine;
		
		
		for( int i = 0; i < inputLines.size(); i++){
			
			inputLine = inputLines.get( i);
			
			
			switch(associationMeasureType){
			
				case EXISTENCE_OF_OVERLAP: 
					
					/*************************************************************************************************/
					/***********************************EXISTENCE_OF_OVERLAP starts***********************************/
					/*************************************************************************************************/					
					TLongIntMap permutationNumberTfbsNameCellLineName2ZeroorOneMap = new TLongIntHashMap();

					
					if( tfbsIntervalTree.getRoot().getNodeName().isNotSentinel()){
						tfbsIntervalTree.findAllOverlappingTfbsIntervalsWithoutIOWithNumbers(
								permutationNumber,
								tfbsIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								permutationNumberTfbsNameCellLineName2ZeroorOneMap, 
								overlapDefinition);
					}

					// accumulate search results of tfbsNameandCellLineName2ZeroorOneMap in tfbsNameandCellLineName2KMap
					for(TLongIntIterator it = permutationNumberTfbsNameCellLineName2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						if( !( permutationNumberTFNumberCellLineNumber2KMap.containsKey(it.key()))){
							permutationNumberTFNumberCellLineNumber2KMap.put(it.key(), it.value());
						}else{
							permutationNumberTFNumberCellLineNumber2KMap.put(it.key(),
									permutationNumberTFNumberCellLineNumber2KMap.get( it.key()) + it.value());

						}

					}// End of for
					
					//Free memory
					permutationNumberTfbsNameCellLineName2ZeroorOneMap = null;
					/*************************************************************************************************/
					/***********************************EXISTENCE_OF_OVERLAP ends*************************************/
					/*************************************************************************************************/

					break;
					
				case NUMBER_OF_OVERLAPPING_BASES:
					
					/*************************************************************************************************/
					/*******************************NUMBER_OF_OVERLAPPING_BASES starts********************************/
					/*************************************************************************************************/
					TLongObjectMap<List<IntervalTreeNode>> permutationNumberTFNumberCellLineNumber2OverlappingNodeListMap = new TLongObjectHashMap<List<IntervalTreeNode>>();
					TLongObjectMap<IntervalTree> permutationNumberTFNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap = new TLongObjectHashMap<IntervalTree>();
					TLongIntMap permutationNumberTFNumberCellLineNumber2NumberofOverlappingBasesMap = new TLongIntHashMap();

					if( tfbsIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						//Step1: Get all the overlappingIntervals with the inputLine
						tfbsIntervalTree.findAllOverlappingTFIntervalsWithoutIOWithNumbers(
								permutationNumber,
								tfbsIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								permutationNumberTFNumberCellLineNumber2OverlappingNodeListMap);
						
						//Step2: Construct an intervalTree from the overlappingIntervals found in step1 such that there are no overlapping nodes in the tree 
						IntervalTree.constructAnIntervalTreeWithNonOverlappingNodes(
								permutationNumberTFNumberCellLineNumber2OverlappingNodeListMap, 
								permutationNumberTFNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap);
						
						//Step3: Calculate the numberofOverlappingBases by overlapping the inputLine with the nodes in intervalTree
						//And fill permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap
						IntervalTree.findNumberofOverlappingBases(
								inputLine,
								permutationNumberTFNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap,
								permutationNumberTFNumberCellLineNumber2NumberofOverlappingBasesMap);
						
					}//End of IF

					// Accumulate search results of permutationNumberTFNumberCellLineNumber2NumberofOverlappingBasesMap in permutationNumberTFNumberCellLineNumber2KMap
					for( TLongIntIterator it = permutationNumberTFNumberCellLineNumber2NumberofOverlappingBasesMap.iterator(); it.hasNext();){

						it.advance();

						if( !(permutationNumberTFNumberCellLineNumber2KMap.containsKey(it.key()))){
							permutationNumberTFNumberCellLineNumber2KMap.put(it.key(), it.value());
						}else{
							permutationNumberTFNumberCellLineNumber2KMap.put( it.key(),
									permutationNumberTFNumberCellLineNumber2KMap.get( it.key()) + it.value());

						}

					}// End of FOR
					
					//Free memory
					permutationNumberTFNumberCellLineNumber2OverlappingNodeListMap = null;
					permutationNumberTFNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap = null;
					permutationNumberTFNumberCellLineNumber2NumberofOverlappingBasesMap = null;
					/*************************************************************************************************/
					/*******************************NUMBER_OF_OVERLAPPING_BASES ends**********************************/
					/*************************************************************************************************/
					break;
					
				default:
					break;
					
			}//End of SWITCH for associationMeasureType

		}// End of FOR each inputLine
	}

	// Gene
	// Annotation
	// with numbers
	public void searchGeneWithNumbers( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			TIntObjectMap<String> givenIntervalNumber2GivenIntervalNameMap,
			TIntObjectMap<OverlapInformation> givenIntervalNumber2OverlapInformationMap,
			TObjectIntMap<ChromosomeName> chromosomeName2CountMap, ChromosomeName chromName,
			BufferedReader bufferedReader, IntervalTree ucscRefSeqGenesIntervalTree, TIntIntMap entrezGeneId2KMap,
			int overlapDefinition, TIntObjectMap<String> officialGeneSymbolNumber2NameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap) {

		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;

		int low;
		int high;

		String givenIntervalName = null;
		int givenIntervalNumber = 0;

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try{

			if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

				if( bufferedWriter == null){
					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.HG19_REFSEQ_GENE_ANNOTATION_DIRECTORY + Commons.HG19_REFSEQ_GENE + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriter.write( "#Searched_for_chr" + "\t" + "interval_Low" + "\t" + "interval_High" + "\t" + "ucscRefSeqGene_node_ChromName" + "\t" + "node_Low" + "\t" + "node_High" + "\t" + "node_RNA_Nucleotide_Accession" + "\t" + "node_IntervalName" + "\t" + "node_IntervalNumber" + "\t" + "node_GeneHugoSymbol" + "\t" + "node_GeneEntrezId" + System.getProperty( "line.separator"));

				}

			}// End of IF

			while( ( strLine = bufferedReader.readLine()) != null){

				TIntByteMap entrezGeneId2OneorZeroMap = new TIntByteHashMap();

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0)
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				else
					high = low;

				Interval interval = new Interval( low, high);

				/***************************************************************************************/
				/************* GIVEN INTERVAL NUMBER 2 GIVEN INTERVAL NAME MAP****starts ***************/
				/************* GIVEN INTERVAL NUMBER 2 NUMBEROFOVERLAPS MAP****starts ******************/
				/***************************************************************************************/
				givenIntervalName = chromName.convertEnumtoString() + "_" + low + "_" + high;

				if( !givenIntervalNumber2GivenIntervalNameMap.containsValue( givenIntervalName)){

					// Set Given Interval Number
					givenIntervalNumber = givenIntervalNumber2GivenIntervalNameMap.size() + 1;

					givenIntervalNumber2GivenIntervalNameMap.put( givenIntervalNumber, givenIntervalName);
					chromosomeName2CountMap.put( chromName, chromosomeName2CountMap.get( chromName) + 1);

				}
				/***************************************************************************************/
				/************* GIVEN INTERVAL NUMBER 2 GIVEN INTERVAL NAME MAP****ends *****************/
				/************* GIVEN INTERVAL NUMBER 2 NUMBEROFOVERLAPS MAP****ends ********************/
				/***************************************************************************************/

				// UCSCRefSeqGenes Search starts here
				if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){
					ucscRefSeqGenesIntervalTree.findAllGeneOverlappingUcscRefSeqGenesIntervalsWithNumbers(
							outputFolder, writeElementBasedAnnotationFoundOverlapsMode, bufferedWriter,
							givenIntervalNumber, givenIntervalNumber2OverlapInformationMap,
							ucscRefSeqGenesIntervalTree.getRoot(), interval, chromName, entrezGeneId2OneorZeroMap,
							Commons.NCBI_GENE_ID, overlapDefinition, officialGeneSymbolNumber2NameMap,
							refSeqGeneNumber2RefSeqGeneNameMap);
				}
				// UCSCRefSeqGenes Search ends here

				// accumulate search results of
				// exonBasedKeggPathway2OneorZeroMap in
				// exonBasedKeggPathway2KMap
				for( TIntByteIterator it = entrezGeneId2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					if( !entrezGeneId2KMap.containsKey( it.key())){
						entrezGeneId2KMap.put( it.key(), it.value());
					}else{
						entrezGeneId2KMap.put( it.key(), entrezGeneId2KMap.get( it.key()) + it.value());
					}

				}// End of FOR

				// After accumulation set to null
				entrezGeneId2OneorZeroMap = null;

			}// End of WHILE

			if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

				// Close BufferedWriter
				if( bufferedWriter != null){
					bufferedWriter.close();
				}

			}// End of IF

		}catch( NumberFormatException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while
	}

	// @todo Gene Annotation with numbers ends

	// Annotation
	// with numbers
	// GeneSet
	public void searchGeneSetWithNumbers( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			ChromosomeName chromName, BufferedReader bufferedReader, IntervalTree ucscRefSeqGenesIntervalTree,
			TIntIntMap exonBasedGeneSetNumber2KMap, 
			TIntIntMap regulationBasedGeneSetNumber2KMap,
			TIntIntMap allBasedGeneSetNumber2KMap, 
			int overlapDefinition,
			TIntObjectMap<String> geneSetNumber2GeneSetNameMap,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap, String geneSetName, GeneSetType geneSetType) {

		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;

		int low;
		int high;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){

				// UserDefinedGeneSet or KEGGPathway
				TIntByteMap exonBasedGeneSet2OneorZeroMap = new TIntByteHashMap();
				TIntByteMap regulationBasedGeneSet2OneorZeroMap = new TIntByteHashMap();
				TIntByteMap allBasedGeneSet2OneorZeroMap = new TIntByteHashMap();

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0)
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				else
					high = low;

				Interval interval = new Interval( low, high);

				// UCSCRefSeqGenes Search starts here
				if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){

					ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers( outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, ucscRefSeqGenesIntervalTree.getRoot(),
							interval, chromName, exonBasedGeneSet2OneorZeroMap, regulationBasedGeneSet2OneorZeroMap,
							allBasedGeneSet2OneorZeroMap, Commons.NCBI_GENE_ID, overlapDefinition,
							geneSetNumber2GeneSetNameMap, 
							geneId2ListofGeneSetNumberMap,
							geneHugoSymbolNumber2GeneHugoSymbolNameMap, 
							refSeqGeneNumber2RefSeqGeneNameMap,
							geneSetName, geneSetType);
				}
				// UCSCRefSeqGenes Search ends here

				// accumulate search results of
				// exonBasedKeggPathway2OneorZeroMap in
				// exonBasedKeggPathway2KMap
				for( TIntByteIterator it = exonBasedGeneSet2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					if( !exonBasedGeneSetNumber2KMap.containsKey( it.key())){
						exonBasedGeneSetNumber2KMap.put( it.key(), it.value());
					}else{
						exonBasedGeneSetNumber2KMap.put( it.key(),
								exonBasedGeneSetNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for

				// accumulate search results of
				// regulationBasedKeggPathway2OneorZeroMap in
				// regulationBasedKeggPathway2KMap
				for( TIntByteIterator it = regulationBasedGeneSet2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					if( !regulationBasedGeneSetNumber2KMap.containsKey( it.key())){
						regulationBasedGeneSetNumber2KMap.put( it.key(), it.value());
					}else{
						regulationBasedGeneSetNumber2KMap.put( it.key(),
								regulationBasedGeneSetNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for

				// accumulate search results of allBasedKeggPathway2OneorZeroMap
				// in allBasedKeggPathway2KMap
				for( TIntByteIterator it = allBasedGeneSet2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					if( !allBasedGeneSetNumber2KMap.containsKey( it.key())){
						allBasedGeneSetNumber2KMap.put( it.key(), it.value());
					}else{
						allBasedGeneSetNumber2KMap.put( it.key(),
								allBasedGeneSetNumber2KMap.get( it.key()) + it.value());

					}

				}// End of for

				// After Accumulation set all of them to null
				exonBasedGeneSet2OneorZeroMap = null;
				regulationBasedGeneSet2OneorZeroMap = null;
				allBasedGeneSet2OneorZeroMap = null;

			}// End of WHILE

		}catch( NumberFormatException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while
	}

	// GeneSet
	// @todo Annotation with numbers ends

	
	// Modified 20 July 2015
	// Annotation
	// With Numbers
	// TF KEGGPathway
	public void searchTfKEGGPathwayWithNumbers( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			ChromosomeName chromName, BufferedReader bufferedReader, IntervalTree tfbsIntervalTree,
			IntervalTree ucscRefSeqGenesIntervalTree, 
			TIntIntMap tfNumberCellLineNumber2KMap,
			TIntIntMap exonBasedKeggPathwayNumber2KMap, 
			TIntIntMap regulationBasedKeggPathwayNumber2KMap,
			TIntIntMap allBasedKeggPathwayNumber2KMap, 
			TIntIntMap tfNumberExonBasedKeggPathwayNumber2KMap,
			TIntIntMap tfNumberRegulationBasedKeggPathwayNumber2KMap,
			TIntIntMap tfNumberAllBasedKeggPathwayNumber2KMap, 
			int overlapDefinition,
			TIntObjectMap<String> tfNumber2TfNameMap, 
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String> fileNumber2FileNameMap,
			TIntObjectMap<String> keggPathwayNumber2KeggPathwayNameMap,
			TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap) {

		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;

		int low;
		int high;

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		int tfNumber;
		int cellLineNumber;
		int keggPathwayNumber;

		int tfNumberCellLineNumber;
		int tfNumberKeggPathwayNumber;
	
		try{

			while( ( strLine = bufferedReader.readLine()) != null){

				// TF CellLine
				TIntByteMap tfNumberCellLineNumber2ZeroorOneMap = new TIntByteHashMap();

				// KEGGPathway
				TIntByteMap exonBasedKeggPathway2OneorZeroMap 		= new TIntByteHashMap();
				TIntByteMap regulationBasedKeggPathway2OneorZeroMap = new TIntByteHashMap();
				TIntByteMap allBasedKeggPathway2OneorZeroMap 		= new TIntByteHashMap();

				// TF KEGGPathway
				TIntByteMap tfExonBasedKeggPathway2OneorZeroMap = new TIntByteHashMap();
				TIntByteMap tfRegulationBasedKeggPathway2OneorZeroMap = new TIntByteHashMap();
				TIntByteMap tfAllBasedKeggPathway2OneorZeroMap = new TIntByteHashMap();

				// Fill these lists during search for TFs and search for UCSCRefSeqGenes
				List<TfCellLineOverlapWithNumbers> tfandCellLineOverlapList = new ArrayList<TfCellLineOverlapWithNumbers>();
				List<UcscRefSeqGeneOverlapWithNumbers> exonBasedKeggPathwayOverlapList = new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();
				List<UcscRefSeqGeneOverlapWithNumbers> regulationBasedKeggPathwayOverlapList = new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();
				List<UcscRefSeqGeneOverlapWithNumbers> allBasedKeggPathwayOverlapList = new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0)
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				else
					high = low;

				Interval interval = new Interval( low, high);

				// TF Search starts here
				if( tfbsIntervalTree.getRoot().getNodeName().isNotSentinel()){
					tfbsIntervalTree.findAllOverlappingTfbsIntervalsWithNumbers( outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, tfbsIntervalTree.getRoot(), interval,
							chromName, tfNumberCellLineNumber2ZeroorOneMap, tfandCellLineOverlapList,
							overlapDefinition, tfNumber2TfNameMap, cellLineNumber2CellLineNameMap,
							fileNumber2FileNameMap);
				}

				// accumulate search results of
				// tfbsNameandCellLineName2ZeroorOneMap in
				// tfbsNameandCellLineName2KMap
				for( TIntByteIterator it = tfNumberCellLineNumber2ZeroorOneMap.iterator(); it.hasNext();){
					it.advance();

					if( !tfNumberCellLineNumber2KMap.containsKey( it.key())){
						tfNumberCellLineNumber2KMap.put( it.key(), it.value());
					}else{
						tfNumberCellLineNumber2KMap.put( it.key(),
								tfNumberCellLineNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for
					// TF Search ends here

				// UCSCRefSeqGenes Search starts here
				if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){
					ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers( outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, ucscRefSeqGenesIntervalTree.getRoot(),
							interval, chromName, geneId2ListofKeggPathwayNumberMap, exonBasedKeggPathway2OneorZeroMap,
							regulationBasedKeggPathway2OneorZeroMap, allBasedKeggPathway2OneorZeroMap,
							Commons.NCBI_GENE_ID, exonBasedKeggPathwayOverlapList,
							regulationBasedKeggPathwayOverlapList, allBasedKeggPathwayOverlapList, overlapDefinition,
							keggPathwayNumber2KeggPathwayNameMap, geneHugoSymbolNumber2GeneHugoSymbolNameMap,
							refSeqGeneNumber2RefSeqGeneNameMap);
				}
				// UCSCRefSeqGenes Search ends here

				// accumulate search results of
				// exonBasedKeggPathway2OneorZeroMap in
				// exonBasedKeggPathway2KMap
				for( TIntByteIterator it = exonBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					if( !exonBasedKeggPathwayNumber2KMap.containsKey( it.key())){
						exonBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
					}else{
						exonBasedKeggPathwayNumber2KMap.put( it.key(),
								exonBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for

				// accumulate search results of
				// regulationBasedKeggPathway2OneorZeroMap in
				// regulationBasedKeggPathway2KMap
				for( TIntByteIterator it = regulationBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					if( !regulationBasedKeggPathwayNumber2KMap.containsKey( it.key())){
						regulationBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
					}else{
						regulationBasedKeggPathwayNumber2KMap.put( it.key(),
								regulationBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for

				// accumulate search results of allBasedKeggPathway2OneorZeroMap
				// in allBasedKeggPathway2KMap
				for( TIntByteIterator it = allBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					if( !allBasedKeggPathwayNumber2KMap.containsKey( it.key())){
						allBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
					}else{
						allBasedKeggPathwayNumber2KMap.put( it.key(),
								allBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());

					}

				}// End of for
					// code will be added here

				// New search for given input SNP or interval case, does not matter.
				// starts here
				// for each tf overlap
				// for each ucscRefSeqGene overlap
				// if these overlaps overlaps
				// then write common overlap to output files
				// question will the overlapDefinition apply here?
				for( TfCellLineOverlapWithNumbers tfOverlap : tfandCellLineOverlapList){

					tfNumberCellLineNumber = tfOverlap.getTfNumberCellLineNumber();
					
					tfNumber = IntervalTree.getElementNumber(
							tfNumberCellLineNumber,
							GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);
					
					cellLineNumber = IntervalTree.getCellLineNumberOrGeneSetNumber(
							tfNumberCellLineNumber,
							GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);

					/****************************************************************************/
					/**************TF and Exon Based KEGGPathway starts**************************/
					/****************************************************************************/
					for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneOverlapWithNumbers : exonBasedKeggPathwayOverlapList){
						if( IntervalTree.overlaps( tfOverlap.getLow(), tfOverlap.getHigh(),
								ucscRefSeqGeneOverlapWithNumbers.getLow(), ucscRefSeqGeneOverlapWithNumbers.getHigh())){

							for( TIntIterator it = ucscRefSeqGeneOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();
								
								tfNumberKeggPathwayNumber = tfNumber * Commons.INT_5DIGITS + keggPathwayNumber;
								
								
								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){
									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.TF_EXON_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);

									if( !tfExonBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfExonBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
										bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));
									}

									bufferedWriter.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter.close();
								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !tfExonBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfExonBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query
					/****************************************************************************/
					/**************TF and Exon Based KEGGPathway ends****************************/
					/****************************************************************************/

					/****************************************************************************/
					/**************TF and Regulation Based KEGGPathway starts********************/
					/****************************************************************************/
					for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneOverlapWithNumbers : regulationBasedKeggPathwayOverlapList){
						if( IntervalTree.overlaps( tfOverlap.getLow(), tfOverlap.getHigh(),
								ucscRefSeqGeneOverlapWithNumbers.getLow(), ucscRefSeqGeneOverlapWithNumbers.getHigh())){
							for( TIntIterator it = ucscRefSeqGeneOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();

								tfNumberKeggPathwayNumber = tfNumber * Commons.INT_5DIGITS  + keggPathwayNumber;
								
								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){
									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.TF_REGULATION_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);

									if( !tfRegulationBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfRegulationBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
										bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));
									}

									bufferedWriter.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter.close();
								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !tfRegulationBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfRegulationBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query
					/****************************************************************************/
					/**************TF and Regulation Based KEGGPathway ends**********************/
					/****************************************************************************/

					/****************************************************************************/
					/**************TF and ALL Based KEGGPathway starts***************************/
					/****************************************************************************/
					for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneOverlapWithNumbers : allBasedKeggPathwayOverlapList){
						if( IntervalTree.overlaps( tfOverlap.getLow(), tfOverlap.getHigh(),
								ucscRefSeqGeneOverlapWithNumbers.getLow(), ucscRefSeqGeneOverlapWithNumbers.getHigh())){
							for( TIntIterator it = ucscRefSeqGeneOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();

								tfNumberKeggPathwayNumber = tfNumber * Commons.INT_5DIGITS  + keggPathwayNumber;
								
								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){
									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.TF_ALL_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);

									if( !tfAllBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfAllBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
										bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));
									}

									bufferedWriter.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter.close();

								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !tfAllBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfAllBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query
					/****************************************************************************/
					/**************TF and ALL Based KEGGPathway ends*****************************/
					/****************************************************************************/

				}// for each tfOverlap for the given query

				// TF EXONBASED_KEGGPATHWAY
				// Fill tfExonBasedKeggPathway2KMap using
				// tfExonBasedKeggPathway2OneorZeroMap
				for( TIntByteIterator it = tfExonBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					tfNumberKeggPathwayNumber = it.key();

					// new
					if( !tfNumberExonBasedKeggPathwayNumber2KMap.containsKey( tfNumberKeggPathwayNumber)){
						tfNumberExonBasedKeggPathwayNumber2KMap.put( tfNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberExonBasedKeggPathwayNumber2KMap.put( tfNumberKeggPathwayNumber,
								tfNumberExonBasedKeggPathwayNumber2KMap.get( tfNumberKeggPathwayNumber) + it.value());
					}
					// new
				}// End of for inner loop

				// TF REGULATIONBASED_KEGGPATHWAY
				// Fill tfRegulationBasedKeggPathway2KMap using
				// tfRegulationBasedKeggPathway2OneorZeroMap
				for( TIntByteIterator it = tfRegulationBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();
					tfNumberKeggPathwayNumber = it.key();

					// new
					if( !tfNumberRegulationBasedKeggPathwayNumber2KMap.containsKey( tfNumberKeggPathwayNumber)){
						tfNumberRegulationBasedKeggPathwayNumber2KMap.put( tfNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberRegulationBasedKeggPathwayNumber2KMap.put(
								tfNumberKeggPathwayNumber,
								tfNumberRegulationBasedKeggPathwayNumber2KMap.get( tfNumberKeggPathwayNumber) + it.value());
					}
					// new
				}// End of for inner loop

				// TF ALLBASED_KEGGPATHWAY
				// Fill tfAllBasedKeggPathway2KMap using
				// tfAllBasedKeggPathway2OneorZeroMap
				for( TIntByteIterator it = tfAllBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();
					tfNumberKeggPathwayNumber = it.key();

					// new
					if( !tfNumberAllBasedKeggPathwayNumber2KMap.containsKey( tfNumberKeggPathwayNumber)){
						tfNumberAllBasedKeggPathwayNumber2KMap.put( tfNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberAllBasedKeggPathwayNumber2KMap.put( tfNumberKeggPathwayNumber,
								tfNumberAllBasedKeggPathwayNumber2KMap.get( tfNumberKeggPathwayNumber) + it.value());
					}
					// new

				}// End of for inner loop

				// After accumulation set to null
				tfNumberCellLineNumber2ZeroorOneMap = null;

				exonBasedKeggPathway2OneorZeroMap = null;
				regulationBasedKeggPathway2OneorZeroMap = null;
				allBasedKeggPathway2OneorZeroMap = null;

				tfExonBasedKeggPathway2OneorZeroMap = null;
				tfRegulationBasedKeggPathway2OneorZeroMap = null;
				tfAllBasedKeggPathway2OneorZeroMap = null;

				tfandCellLineOverlapList = null;
				exonBasedKeggPathwayOverlapList = null;
				regulationBasedKeggPathwayOverlapList = null;
				allBasedKeggPathwayOverlapList = null;

			}// End of WHILE

		}catch( NumberFormatException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while
	}

	// @todo TF KEGGPathway Annotation with Numbers starts

	// Modified 20 July 2015
	// Annotation
	// With Numbers
	// TF CELLLINE KEGGPATHWAY
	public void searchTfCellLineKEGGPathwayWithNumbers( 
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			ChromosomeName chromName, 
			BufferedReader bufferedReader, 
			IntervalTree tfbsIntervalTree,
			IntervalTree ucscRefSeqGenesIntervalTree, 
			TIntIntMap tfNumberCellLineNumber2KMap,
			TIntIntMap exonBasedKeggPathwayNumber2KMap, 
			TIntIntMap regulationBasedKeggPathwayNumber2KMap,
			TIntIntMap allBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap, 
			int overlapDefinition,
			TIntObjectMap<String> tfNumber2TfNameMap, 
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String> fileNumber2FileNameMap,
			TIntObjectMap<String> keggPathwayNumber2KeggPathwayNameMap,
			TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap) {

		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;

		int low;
		int high;

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		int tfNumber;
		int cellLineNumber;
		int keggPathwayNumber;

		int tfNumberCellLineNumber;
		long tfNumberCellLineNumberKeggPathwayNumber;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){

				// TF CellLine
				TIntByteMap tfNumberCellLineNumber2ZeroorOneMap = new TIntByteHashMap();

				// KEGGPathway
				TIntByteMap exonBasedKeggPathway2OneorZeroMap = new TIntByteHashMap();
				TIntByteMap regulationBasedKeggPathway2OneorZeroMap = new TIntByteHashMap();
				TIntByteMap allBasedKeggPathway2OneorZeroMap = new TIntByteHashMap();

				// TF CellLine KEGGPathway
				TLongByteMap tfCellLineExonBasedKeggPathway2OneorZeroMap 		= new TLongByteHashMap();
				TLongByteMap tfCellLineRegulationBasedKeggPathway2OneorZeroMap 	= new TLongByteHashMap();
				TLongByteMap tfCellLineAllBasedKeggPathway2OneorZeroMap 		= new TLongByteHashMap();

				// Fill these lists during search for tfs and search for
				// ucscRefSeqGenes
				List<TfCellLineOverlapWithNumbers> tfandCellLineOverlapList = new ArrayList<TfCellLineOverlapWithNumbers>();
				List<UcscRefSeqGeneOverlapWithNumbers> exonBasedKeggPathwayOverlapList = new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();
				List<UcscRefSeqGeneOverlapWithNumbers> regulationBasedKeggPathwayOverlapList = new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();
				List<UcscRefSeqGeneOverlapWithNumbers> allBasedKeggPathwayOverlapList = new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0)
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				else
					high = low;

				Interval interval = new Interval( low, high);

				// TF Search starts here
				if( tfbsIntervalTree.getRoot().getNodeName().isNotSentinel()){

					tfbsIntervalTree.findAllOverlappingTfbsIntervalsWithNumbers( outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, tfbsIntervalTree.getRoot(), interval,
							chromName, tfNumberCellLineNumber2ZeroorOneMap, tfandCellLineOverlapList,
							overlapDefinition, tfNumber2TfNameMap, cellLineNumber2CellLineNameMap,
							fileNumber2FileNameMap);
				}

				// accumulate search results of
				// tfbsNameandCellLineName2ZeroorOneMap in
				// tfbsNameandCellLineName2KMap
				for( TIntByteIterator it = tfNumberCellLineNumber2ZeroorOneMap.iterator(); it.hasNext();){
					it.advance();

					if( !tfNumberCellLineNumber2KMap.containsKey( it.key())){
						tfNumberCellLineNumber2KMap.put( it.key(), it.value());
					}else{
						tfNumberCellLineNumber2KMap.put( it.key(),
								tfNumberCellLineNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for
					// TF Search ends here

				// UCSCRefSeqGenes Search starts here
				if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){
					ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers( outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, ucscRefSeqGenesIntervalTree.getRoot(),
							interval, chromName, geneId2ListofKeggPathwayNumberMap, exonBasedKeggPathway2OneorZeroMap,
							regulationBasedKeggPathway2OneorZeroMap, allBasedKeggPathway2OneorZeroMap,
							Commons.NCBI_GENE_ID, exonBasedKeggPathwayOverlapList,
							regulationBasedKeggPathwayOverlapList, allBasedKeggPathwayOverlapList, overlapDefinition,
							keggPathwayNumber2KeggPathwayNameMap, geneHugoSymbolNumber2GeneHugoSymbolNameMap,
							refSeqGeneNumber2RefSeqGeneNameMap);
				}
				// UCSCRefSeqGenes Search ends here

				// accumulate search results of
				// exonBasedKeggPathway2OneorZeroMap in
				// exonBasedKeggPathway2KMap
				for( TIntByteIterator it = exonBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					if( !exonBasedKeggPathwayNumber2KMap.containsKey( it.key())){
						exonBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
					}else{
						exonBasedKeggPathwayNumber2KMap.put( it.key(),
								exonBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for

				// accumulate search results of
				// regulationBasedKeggPathway2OneorZeroMap in
				// regulationBasedKeggPathway2KMap
				for( TIntByteIterator it = regulationBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					if( !regulationBasedKeggPathwayNumber2KMap.containsKey( it.key())){
						regulationBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
					}else{
						regulationBasedKeggPathwayNumber2KMap.put( it.key(),
								regulationBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for

				// accumulate search results of allBasedKeggPathway2OneorZeroMap
				// in allBasedKeggPathway2KMap
				for( TIntByteIterator it = allBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					if( !allBasedKeggPathwayNumber2KMap.containsKey( it.key())){
						allBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
					}else{
						allBasedKeggPathwayNumber2KMap.put( it.key(),
								allBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());

					}

				}// End of for
					// code will be added here

				// New search for given input SNP or interval case, does not
				// matter.
				// starts here
				// for each tf overlap
				// for each ucscRefSeqGene overlap
				// if these overlaps overlaps
				// then write common overlap to output files
				// question will the overlapDefinition apply here?
				for( TfCellLineOverlapWithNumbers tfOverlap : tfandCellLineOverlapList){

					tfNumberCellLineNumber = tfOverlap.getTfNumberCellLineNumber();
					
					tfNumber = IntervalTree.getElementNumber(
							tfNumberCellLineNumber,
							GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);
					
					cellLineNumber = IntervalTree.getCellLineNumberOrGeneSetNumber(
							tfNumberCellLineNumber,
							GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);

					/***********************************************************************/
					/***********TF CellLine ExonBasedKEGGPathway starts*********************/
					/***********************************************************************/
					for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneOverlapWithNumbers : exonBasedKeggPathwayOverlapList){
						
						if( IntervalTree.overlaps( tfOverlap.getLow(), tfOverlap.getHigh(),
								ucscRefSeqGeneOverlapWithNumbers.getLow(), ucscRefSeqGeneOverlapWithNumbers.getHigh())){

							for( TIntIterator it = ucscRefSeqGeneOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();
								
								tfNumberCellLineNumberKeggPathwayNumber = IntervalTree.generateLongElementNumberCellLineNumberKeggPathwayNumber(
										tfNumber, 
										cellLineNumber, 
										keggPathwayNumber, 
										GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);

								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);

									if( !tfCellLineExonBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineExonBasedKeggPathway2OneorZeroMap.put(tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
										bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));
									}

									bufferedWriter.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter.close();
								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !tfCellLineExonBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineExonBasedKeggPathway2OneorZeroMap.put(tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query
					/***********************************************************************/
					/***********TF CellLine ExonBasedKEGGPathway ends***********************/
					/***********************************************************************/

					/***********************************************************************/
					/***********TF CellLine RegulationBasedKEGGPathway starts***************/
					/***********************************************************************/
					for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneOverlapWithNumbers : regulationBasedKeggPathwayOverlapList){
						if( IntervalTree.overlaps( tfOverlap.getLow(), tfOverlap.getHigh(),
								ucscRefSeqGeneOverlapWithNumbers.getLow(), ucscRefSeqGeneOverlapWithNumbers.getHigh())){
							for( TIntIterator it = ucscRefSeqGeneOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();
								
								tfNumberCellLineNumberKeggPathwayNumber = IntervalTree.generateLongElementNumberCellLineNumberKeggPathwayNumber(
										tfNumber, 
										cellLineNumber, 
										keggPathwayNumber, 
										GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);

								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);

									if( !tfCellLineRegulationBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineRegulationBasedKeggPathway2OneorZeroMap.put(
												tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
										bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));
									}

									bufferedWriter.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter.close();

								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !tfCellLineRegulationBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineRegulationBasedKeggPathway2OneorZeroMap.put(
												tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query
					/***********************************************************************/
					/***********TF CellLine RegulationBasedKEGGPathway ends*****************/
					/***********************************************************************/

					/***********************************************************************/
					/***********TF CellLine AllBasedKEGGPathway starts**********************/
					/***********************************************************************/
					for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneOverlapWithNumbers : allBasedKeggPathwayOverlapList){
						if( IntervalTree.overlaps( tfOverlap.getLow(), tfOverlap.getHigh(),
								ucscRefSeqGeneOverlapWithNumbers.getLow(), ucscRefSeqGeneOverlapWithNumbers.getHigh())){
							for( TIntIterator it = ucscRefSeqGeneOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();
								
								tfNumberCellLineNumberKeggPathwayNumber = IntervalTree.generateLongElementNumberCellLineNumberKeggPathwayNumber(
										tfNumber, 
										cellLineNumber, 
										keggPathwayNumber, 
										GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);

								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){
									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);

									if( !tfCellLineAllBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineAllBasedKeggPathway2OneorZeroMap.put(
												tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
										bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));
									}

									bufferedWriter.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter.close();
								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !tfCellLineAllBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineAllBasedKeggPathway2OneorZeroMap.put(
												tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query
					/***********************************************************************/
					/***********TF CellLine AllBasedKEGGPathway ends************************/
					/***********************************************************************/

				}// for each tfOverlap for the given query

				// TF CELLLINE EXONBASED_KEGGPATHWAY
				// Fill tfbsAndCellLineAndExonBasedKeggPathway2KMap using tfandExonBasedKeggPathway2OneorZeroMap
				for( TLongByteIterator it = tfCellLineExonBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					tfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
						tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.put(tfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.put(tfNumberCellLineNumberKeggPathwayNumber,tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.get( tfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF CELLLINE REGULATIONBASED_KEGGPATHWAY
				// Fill tfbsAndCellLineAndRegulationBasedKeggPathway2KMap using
				// tfandRegulationBasedKeggPathway2OneorZeroMap
				for( TLongByteIterator it = tfCellLineRegulationBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					tfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
						tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.put(tfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.put(tfNumberCellLineNumberKeggPathwayNumber,tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.get( tfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF CELLLINE ALLBASED_KEGGPATHWAY
				// Fill tfbsAndCellLineAndAllBasedKeggPathway2KMap using
				// tfandAllBasedKeggPathway2OneorZeroMap
				for( TLongByteIterator it = tfCellLineAllBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					tfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
						tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.put(tfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.put(tfNumberCellLineNumberKeggPathwayNumber,tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.get( tfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// After accumulation set to null
				tfNumberCellLineNumber2ZeroorOneMap = null;

				exonBasedKeggPathway2OneorZeroMap = null;
				regulationBasedKeggPathway2OneorZeroMap = null;
				allBasedKeggPathway2OneorZeroMap = null;

				tfCellLineExonBasedKeggPathway2OneorZeroMap = null;
				tfCellLineRegulationBasedKeggPathway2OneorZeroMap = null;
				tfCellLineAllBasedKeggPathway2OneorZeroMap = null;

				tfandCellLineOverlapList = null;
				exonBasedKeggPathwayOverlapList = null;
				regulationBasedKeggPathwayOverlapList = null;
				allBasedKeggPathwayOverlapList = null;

			}// End of WHILE

		}catch( NumberFormatException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while
	}

	// @todo TF CELLLINE KEGGPATHWAY Annotation with Numbers ends

	
	// Modified 20 July 2015
	// Annotation
	// With Numbers
	// TF and KEGGPathway
	// TF and CellLine and KEGGPathway
	public void searchTfandKeggPathwayWithNumbers( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			ChromosomeName chromName, 
			BufferedReader bufferedReader, 
			IntervalTree tfbsIntervalTree,
			IntervalTree ucscRefSeqGenesIntervalTree, 
			TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap,
			TIntIntMap tfNumberCellLineNumber2KMap, 
			TIntIntMap exonBasedKeggPathwayNumber2KMap,
			TIntIntMap regulationBasedKeggPathwayNumber2KMap, 
			TIntIntMap allBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap,
			TIntIntMap tfNumberExonBasedKeggPathwayNumber2KMap,
			TIntIntMap tfNumberRegulationBasedKeggPathwayNumber2KMap,
			TIntIntMap tfNumberAllBasedKeggPathwayNumber2KMap, int overlapDefinition,
			TIntObjectMap<String> tfNumber2TfNameMap, 
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String> fileNumber2FileNameMap,
			TIntObjectMap<String> keggPathwayNumber2KeggPathwayNameMap,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap) {

		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;

		int low;
		int high;

		FileWriter fileWriter1 = null;
		FileWriter fileWriter2 = null;
		BufferedWriter bufferedWriter1 = null;
		BufferedWriter bufferedWriter2 = null;

		int tfNumber;
		int cellLineNumber;
		int keggPathwayNumber;

		int tfNumberCellLineNumber;
		int tfNumberKeggPathwayNumber;
		long tfNumberCellLineNumberKeggPathwayNumber;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){

				// TF CellLine
				TIntByteMap tfNumberCellLineNumber2ZeroorOneMap = new TIntByteHashMap();

				// KEGGPathway
				TIntByteMap exonBasedKeggPathway2OneorZeroMap 		= new TIntByteHashMap();
				TIntByteMap regulationBasedKeggPathway2OneorZeroMap = new TIntByteHashMap();
				TIntByteMap allBasedKeggPathway2OneorZeroMap 		= new TIntByteHashMap();

				// TF KEGGPathway
				TIntByteMap tfExonBasedKeggPathway2OneorZeroMap 		= new TIntByteHashMap();
				TIntByteMap tfRegulationBasedKeggPathway2OneorZeroMap 	= new TIntByteHashMap();
				TIntByteMap tfAllBasedKeggPathway2OneorZeroMap 			= new TIntByteHashMap();

				// TF CellLine KEGGPathway
				TLongByteMap tfCellLineExonBasedKeggPathway2OneorZeroMap 		= new TLongByteHashMap();
				TLongByteMap tfCellLineRegulationBasedKeggPathway2OneorZeroMap 	= new TLongByteHashMap();
				TLongByteMap tfCellLineAllBasedKeggPathway2OneorZeroMap 		= new TLongByteHashMap();

				// Fill these lists during search for tfs and search for
				// ucscRefSeqGenes
				List<TfCellLineOverlapWithNumbers> tfandCellLineOverlapList = new ArrayList<TfCellLineOverlapWithNumbers>();
				List<UcscRefSeqGeneOverlapWithNumbers> exonBasedKeggPathwayOverlapList = new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();
				List<UcscRefSeqGeneOverlapWithNumbers> regulationBasedKeggPathwayOverlapList = new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();
				List<UcscRefSeqGeneOverlapWithNumbers> allBasedKeggPathwayOverlapList = new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0)
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				else
					high = low;

				Interval interval = new Interval( low, high);

				// TF Search starts here
				if( tfbsIntervalTree.getRoot().getNodeName().isNotSentinel()){

					tfbsIntervalTree.findAllOverlappingTfbsIntervalsWithNumbers( outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, tfbsIntervalTree.getRoot(), interval,
							chromName, tfNumberCellLineNumber2ZeroorOneMap, tfandCellLineOverlapList,
							overlapDefinition, tfNumber2TfNameMap, cellLineNumber2CellLineNameMap,
							fileNumber2FileNameMap);
				}

				// accumulate search results of
				// tfbsNameandCellLineName2ZeroorOneMap in
				// tfbsNameandCellLineName2KMap
				for( TIntByteIterator it = tfNumberCellLineNumber2ZeroorOneMap.iterator(); it.hasNext();){
					it.advance();

					if( !tfNumberCellLineNumber2KMap.containsKey( it.key())){
						tfNumberCellLineNumber2KMap.put( it.key(), it.value());
					}else{
						tfNumberCellLineNumber2KMap.put( it.key(),
								tfNumberCellLineNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for
					// TF Search ends here

				// UCSCRefSeqGenes Search starts here
				if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){
					ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers( outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, ucscRefSeqGenesIntervalTree.getRoot(),
							interval, chromName, geneId2ListofKeggPathwayNumberMap, exonBasedKeggPathway2OneorZeroMap,
							regulationBasedKeggPathway2OneorZeroMap, allBasedKeggPathway2OneorZeroMap,
							Commons.NCBI_GENE_ID, exonBasedKeggPathwayOverlapList,
							regulationBasedKeggPathwayOverlapList, allBasedKeggPathwayOverlapList, overlapDefinition,
							keggPathwayNumber2KeggPathwayNameMap, geneHugoSymbolNumber2GeneHugoSymbolNameMap,
							refSeqGeneNumber2RefSeqGeneNameMap);
				}
				// UCSCRefSeqGenes Search ends here

				// accumulate search results of
				// exonBasedKeggPathway2OneorZeroMap in
				// exonBasedKeggPathway2KMap
				for( TIntByteIterator it = exonBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					if( !exonBasedKeggPathwayNumber2KMap.containsKey( it.key())){
						exonBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
					}else{
						exonBasedKeggPathwayNumber2KMap.put( it.key(),
								exonBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for

				// accumulate search results of
				// regulationBasedKeggPathway2OneorZeroMap in
				// regulationBasedKeggPathway2KMap
				for( TIntByteIterator it = regulationBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					if( !regulationBasedKeggPathwayNumber2KMap.containsKey( it.key())){
						regulationBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
					}else{
						regulationBasedKeggPathwayNumber2KMap.put( it.key(),
								regulationBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for

				// accumulate search results of allBasedKeggPathway2OneorZeroMap
				// in allBasedKeggPathway2KMap
				for( TIntByteIterator it = allBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					if( !allBasedKeggPathwayNumber2KMap.containsKey( it.key())){
						allBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
					}else{
						allBasedKeggPathwayNumber2KMap.put( it.key(),
								allBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());

					}

				}// End of for

				// New search for given input SNP or interval case, does not
				// matter.
				// starts here
				// for each tf overlap
				// for each ucscRefSeqGene overlap
				// if these overlaps overlaps
				// then write common overlap to output files
				// question will the overlapDefinition apply here?
				for( TfCellLineOverlapWithNumbers tfOverlap : tfandCellLineOverlapList){

					tfNumberCellLineNumber = tfOverlap.getTfNumberCellLineNumber();
					
					tfNumber = IntervalTree.getElementNumber(
							tfNumberCellLineNumber,
							GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);
					
					cellLineNumber = IntervalTree.getCellLineNumberOrGeneSetNumber(
							tfNumberCellLineNumber,
							GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);

					/**************************************************************************/
					/*********************************BOTH*************************************/
					/****************TF ExonBased KEGG Pathway starts**************************/
					/****************TF CellLine ExonBased KEGG Pathway starts*****************/
					/**************************************************************************/
					for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneOverlapWithNumbers : exonBasedKeggPathwayOverlapList){
						if( IntervalTree.overlaps( tfOverlap.getLow(), tfOverlap.getHigh(),
								ucscRefSeqGeneOverlapWithNumbers.getLow(), ucscRefSeqGeneOverlapWithNumbers.getHigh())){

							for( TIntIterator it = ucscRefSeqGeneOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();
								
								tfNumberKeggPathwayNumber = tfNumber * Commons.INT_5DIGITS + keggPathwayNumber; 
								tfNumberCellLineNumberKeggPathwayNumber = tfNumberCellLineNumber * Commons.LONG_5DIGITS + keggPathwayNumber;
								
								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

									/*************************************************************/
									fileWriter1 = FileOperations.createFileWriter(
											outputFolder + Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter1 = new BufferedWriter( fileWriter1);

									if( !tfCellLineExonBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineExonBasedKeggPathway2OneorZeroMap.put(
												tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
										bufferedWriter1.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));
									}

									bufferedWriter1.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter1.close();;
									/*************************************************************/

									/*************************************************************/
									fileWriter2 = FileOperations.createFileWriter(
											outputFolder + Commons.TF_EXON_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter2 = new BufferedWriter( fileWriter2);

									if( !tfExonBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfExonBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
										bufferedWriter2.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));
									}

									bufferedWriter2.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter2.close();
									/*************************************************************/
								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !tfCellLineExonBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineExonBasedKeggPathway2OneorZeroMap.put(
												tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
									}
									if( !tfExonBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfExonBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query
					/**************************************************************************/
					/*********************************BOTH*************************************/
					/****************TF ExonBased KEGG Pathway ends****************************/
					/****************TF CellLine ExonBased KEGG Pathway ends*******************/
					/**************************************************************************/

					/**************************************************************************/
					/*********************************BOTH*************************************/
					/****************TF RegulationBased KEGG Pathway starts********************/
					/****************TF CellLine RegulationBased KEGG Pathway starts***********/
					/**************************************************************************/
					for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneOverlapWithNumbers : regulationBasedKeggPathwayOverlapList){
						if( IntervalTree.overlaps( tfOverlap.getLow(), tfOverlap.getHigh(),
								ucscRefSeqGeneOverlapWithNumbers.getLow(), ucscRefSeqGeneOverlapWithNumbers.getHigh())){
							for( TIntIterator it = ucscRefSeqGeneOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();
								
								tfNumberKeggPathwayNumber = tfNumber * Commons.INT_5DIGITS + keggPathwayNumber; 
								tfNumberCellLineNumberKeggPathwayNumber = tfNumberCellLineNumber * Commons.LONG_5DIGITS + keggPathwayNumber;
								
								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

									/***********************************************************/
									fileWriter1 = FileOperations.createFileWriter(
											outputFolder + Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter1 = new BufferedWriter( fileWriter1);

									if( !tfCellLineRegulationBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineRegulationBasedKeggPathway2OneorZeroMap.put(
												tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
										bufferedWriter1.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));

									}

									bufferedWriter1.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter1.close();
									/***********************************************************/

									/***********************************************************/
									fileWriter2 = FileOperations.createFileWriter(
											outputFolder + Commons.TF_REGULATION_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter2 = new BufferedWriter( fileWriter2);

									if( !tfRegulationBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfRegulationBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
										bufferedWriter2.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));
									}

									bufferedWriter2.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter2.close();
									/***********************************************************/

								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !tfCellLineRegulationBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineRegulationBasedKeggPathway2OneorZeroMap.put(
												tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
									}
									if( !tfRegulationBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfRegulationBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query
					/**************************************************************************/
					/*********************************BOTH*************************************/
					/****************TF RegulationBased KEGG Pathway ends**********************/
					/****************TF CellLine RegulationBased KEGG Pathway ends*************/
					/**************************************************************************/

					/**************************************************************************/
					/*********************************BOTH*************************************/
					/****************TF ALLBased KEGG Pathway starts***************************/
					/****************TF CellLine ALLBased KEGG Pathway starts******************/
					/**************************************************************************/
					for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneOverlapWithNumbers : allBasedKeggPathwayOverlapList){
						if( IntervalTree.overlaps( tfOverlap.getLow(), tfOverlap.getHigh(),
								ucscRefSeqGeneOverlapWithNumbers.getLow(), ucscRefSeqGeneOverlapWithNumbers.getHigh())){
							for( TIntIterator it = ucscRefSeqGeneOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();
								
								tfNumberKeggPathwayNumber = tfNumber * Commons.INT_5DIGITS + keggPathwayNumber; 
								tfNumberCellLineNumberKeggPathwayNumber = tfNumberCellLineNumber * Commons.LONG_5DIGITS + keggPathwayNumber;
								
								
								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

									/*****************************************************************/
									fileWriter1 = FileOperations.createFileWriter(
											outputFolder + Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter1 = new BufferedWriter( fileWriter1);

									if( !tfCellLineAllBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineAllBasedKeggPathway2OneorZeroMap.put(
												tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
										bufferedWriter1.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));
									}

									bufferedWriter1.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter1.close();
									/*****************************************************************/

									/*****************************************************************/
									fileWriter2 = FileOperations.createFileWriter(
											outputFolder + Commons.TF_ALL_BASED_KEGG_PATHWAY_ANNOTATION + tfNumber2TfNameMap.get( tfNumber) + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter2 = new BufferedWriter( fileWriter2);

									if( !tfAllBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfAllBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
										bufferedWriter2.write( Commons.GLANET_COMMENT_CHARACTER + "Search for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));
									}

									bufferedWriter2.write( ChromosomeName.convertEnumtoString( chromName) + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + tfNumber2TfNameMap.get( tfNumber) + "_" + cellLineNumber2CellLineNameMap.get( cellLineNumber) + "\t" + tfOverlap.getLow() + "\t" + tfOverlap.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getRefSeqGeneNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getLow() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getHigh() + "\t" + ucscRefSeqGeneOverlapWithNumbers.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( ucscRefSeqGeneOverlapWithNumbers.getGeneHugoSymbolNumber()) + "\t" + ucscRefSeqGeneOverlapWithNumbers.getGeneEntrezId() + "\t" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + System.getProperty( "line.separator"));
									bufferedWriter2.close();
									/*****************************************************************/

								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !tfCellLineAllBasedKeggPathway2OneorZeroMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
										tfCellLineAllBasedKeggPathway2OneorZeroMap.put(
												tfNumberCellLineNumberKeggPathwayNumber, Commons.BYTE_1);
									}
									if( !tfAllBasedKeggPathway2OneorZeroMap.containsKey( tfNumberKeggPathwayNumber)){
										tfAllBasedKeggPathway2OneorZeroMap.put( tfNumberKeggPathwayNumber,
												Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query
					/**************************************************************************/
					/*********************************BOTH*************************************/
					/****************TF ALLBased KEGG Pathway ends*****************************/
					/****************TF CellLine ALLBased KEGG Pathway ends********************/
					/**************************************************************************/

				}// for each tfOverlap for the given query
					// ends here

				// TF CELLLINE EXONBASED_KEGGPATHWAY
				// Fill tfbsAndCellLineAndExonBasedKeggPathway2KMap using
				// tfandExonBasedKeggPathway2OneorZeroMap
				for( TLongByteIterator it = tfCellLineExonBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					tfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
						tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.put(
								tfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.put(
								tfNumberCellLineNumberKeggPathwayNumber,
								tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.get( tfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF CELLLINE REGULATIONBASED_KEGGPATHWAY
				// Fill tfbsAndCellLineAndRegulationBasedKeggPathway2KMap using
				// tfandRegulationBasedKeggPathway2OneorZeroMap
				for( TLongByteIterator it = tfCellLineRegulationBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					tfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
						tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.put(
								tfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.put(
								tfNumberCellLineNumberKeggPathwayNumber,
								tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.get( tfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF CELLLINE ALLBASED_KEGGPATHWAY
				// Fill tfbsAndCellLineAndAllBasedKeggPathway2KMap using
				// tfandAllBasedKeggPathway2OneorZeroMap
				for( TLongByteIterator it = tfCellLineAllBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					tfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.containsKey( tfNumberCellLineNumberKeggPathwayNumber)){
						tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.put(
								tfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.put(
								tfNumberCellLineNumberKeggPathwayNumber,
								tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.get( tfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF EXONBASED_KEGGPATHWAY
				// Fill tfExonBasedKeggPathway2KMap using
				// tfExonBasedKeggPathway2OneorZeroMap
				for( TIntByteIterator it = tfExonBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();

					tfNumberKeggPathwayNumber = it.key();

					// new
					if( !tfNumberExonBasedKeggPathwayNumber2KMap.containsKey( tfNumberKeggPathwayNumber)){
						tfNumberExonBasedKeggPathwayNumber2KMap.put( tfNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberExonBasedKeggPathwayNumber2KMap.put( tfNumberKeggPathwayNumber,
								tfNumberExonBasedKeggPathwayNumber2KMap.get( tfNumberKeggPathwayNumber) + it.value());
					}
					// new
				}// End of for inner loop

				// TF REGULATIONBASED_KEGGPATHWAY
				// Fill tfRegulationBasedKeggPathway2KMap using
				// tfRegulationBasedKeggPathway2OneorZeroMap
				for( TIntByteIterator it = tfRegulationBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();
					tfNumberKeggPathwayNumber = it.key();

					// new
					if( !tfNumberRegulationBasedKeggPathwayNumber2KMap.containsKey( tfNumberKeggPathwayNumber)){
						tfNumberRegulationBasedKeggPathwayNumber2KMap.put( tfNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberRegulationBasedKeggPathwayNumber2KMap.put(
								tfNumberKeggPathwayNumber,
								tfNumberRegulationBasedKeggPathwayNumber2KMap.get( tfNumberKeggPathwayNumber) + it.value());
					}
					// new
				}// End of for inner loop

				// TF ALLBASED_KEGGPATHWAY
				// Fill tfAllBasedKeggPathway2KMap using
				// tfAllBasedKeggPathway2OneorZeroMap
				for( TIntByteIterator it = tfAllBasedKeggPathway2OneorZeroMap.iterator(); it.hasNext();){

					it.advance();
					tfNumberKeggPathwayNumber = it.key();

					// new
					if( !tfNumberAllBasedKeggPathwayNumber2KMap.containsKey( tfNumberKeggPathwayNumber)){
						tfNumberAllBasedKeggPathwayNumber2KMap.put( tfNumberKeggPathwayNumber, it.value());
					}else{
						tfNumberAllBasedKeggPathwayNumber2KMap.put( tfNumberKeggPathwayNumber,
								tfNumberAllBasedKeggPathwayNumber2KMap.get( tfNumberKeggPathwayNumber) + it.value());
					}
					// new

				}// End of for inner loop

				// added here ends

				// After accumulation set to null
				tfNumberCellLineNumber2ZeroorOneMap = null;

				exonBasedKeggPathway2OneorZeroMap 		= null;
				regulationBasedKeggPathway2OneorZeroMap = null;
				allBasedKeggPathway2OneorZeroMap 		= null;

				tfExonBasedKeggPathway2OneorZeroMap 		= null;
				tfRegulationBasedKeggPathway2OneorZeroMap	= null;
				tfAllBasedKeggPathway2OneorZeroMap 			= null;

				tfCellLineExonBasedKeggPathway2OneorZeroMap 		= null;
				tfCellLineRegulationBasedKeggPathway2OneorZeroMap 	= null;
				tfCellLineAllBasedKeggPathway2OneorZeroMap 			= null;

				tfandCellLineOverlapList = null;
				exonBasedKeggPathwayOverlapList = null;
				regulationBasedKeggPathwayOverlapList = null;
				allBasedKeggPathwayOverlapList = null;

			}// End of WHILE
		}catch( NumberFormatException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while
	}

	// @todo Annotation with Numbers ends

	// Enrichment
	// With IO
	// With Numbers
	// Empirical P Value Calculation
	// with IO
	public static void searchHistoneWithIOWithNumbers( String outputFolder, int permutationNumber,
			ChromosomeName chromName, List<InputLineMinimal> inputLines, IntervalTree histoneIntervalTree,
			TLongObjectMap<BufferedWriter> histoneBufferedWriterHashMap,
			TLongIntMap permutationNumberHistoneNumberCellLineNumber2KMap, int overlapDefinition) {

		InputLineMinimal inputLine;

		for( int i = 0; i < inputLines.size(); i++){

			TLongIntMap permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap = new TLongIntHashMap();

			inputLine = inputLines.get( i);

			if( histoneIntervalTree.getRoot().getNodeName().isNotSentinel()){
				histoneIntervalTree.findAllOverlappingHistoneIntervalsWithIOWithNumbers( outputFolder,
						permutationNumber, histoneIntervalTree.getRoot(), inputLine, chromName,
						histoneBufferedWriterHashMap, permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap,
						overlapDefinition);
			}

			// accumulate search results of dnaseCellLine2OneorZeroMap in
			// dnaseCellLine2KMap
			for( TLongIntIterator it = permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap.iterator(); it.hasNext();){

				it.advance();

				if( !( permutationNumberHistoneNumberCellLineNumber2KMap.containsKey( it.key()))){
					permutationNumberHistoneNumberCellLineNumber2KMap.put( it.key(), it.value());
				}else{
					permutationNumberHistoneNumberCellLineNumber2KMap.put( it.key(),
							permutationNumberHistoneNumberCellLineNumber2KMap.get( it.key()) + it.value());

				}

			}// End of for

		}// End of for

	}

	// with Numbers ends
	// @todo

	
	
	//27 July 2015 starts
	public static void searchUserDefinedLibraryWithoutIOWithNumbers(
			TIntObjectMap<List<InputLineMinimal>> chrNumber2RandomlyGeneratedData,
			TIntObjectMap<TIntObjectMap<IntervalTree>> userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap,
			TIntIntMap userDefinedLibraryElementTypeNumberElementNumber2PermutationKMap,
			TIntObjectMap<String> userDefinedLibraryElementTypeNumber2ElementTypeNameMap,
			int overlapDefinition){
		
		ChromosomeName chromName;
		List<InputLineMinimal> inputLines;
		InputLineMinimal inputLine;
		
		TIntObjectMap<IntervalTree> chrNumber2UserDefinedLibraryIntervalTree;
		IntervalTree userDefinedLibraryIntervalTree;
		
		TIntByteMap elementTypeNumberElementNumber2PermutationOneorZeroMap = null;
		
		int elementTypeNumber;
		
		for(TIntObjectIterator<String> itr =userDefinedLibraryElementTypeNumber2ElementTypeNameMap.iterator();itr.hasNext();){
			
			itr.advance();
			
			elementTypeNumber = itr.key();
			
			chrNumber2UserDefinedLibraryIntervalTree = userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap.get(elementTypeNumber);
			
			for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){
				
				chromName = GRCh37Hg19Chromosome.getChromosomeName(chrNumber);
				inputLines = chrNumber2RandomlyGeneratedData.get(chrNumber);
				
				userDefinedLibraryIntervalTree = chrNumber2UserDefinedLibraryIntervalTree.get(chrNumber);

				
				if( inputLines != null){
					
					for( int i = 0; i < inputLines.size(); i++){
						
						elementTypeNumberElementNumber2PermutationOneorZeroMap = new TIntByteHashMap();
						
						inputLine = inputLines.get(i);
						
						userDefinedLibraryIntervalTree.findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers(
								userDefinedLibraryIntervalTree.getRoot(), 
								inputLine, 
								chromName, 
								elementTypeNumberElementNumber2PermutationOneorZeroMap, 
								overlapDefinition);
						
						
						// accumulate search results of elementTypeNumberElementNumber2PermutationOneorZeroMap
						// in userDefinedLibraryElementTypeNumberElementNumber2PermutationKMap
						for( TIntByteIterator it = elementTypeNumberElementNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){

							it.advance();

							if( !( userDefinedLibraryElementTypeNumberElementNumber2PermutationKMap.containsKey( it.key()))){
								userDefinedLibraryElementTypeNumberElementNumber2PermutationKMap.put( it.key(), it.value());
							}else{
								userDefinedLibraryElementTypeNumberElementNumber2PermutationKMap.put( it.key(),userDefinedLibraryElementTypeNumberElementNumber2PermutationKMap.get( it.key()) + it.value());
							}

						}// End of for
						
						
					}//End of FOR each inputLine
						
					
				}//End of IF inputLines is not null
				
				
			}//End of FOR each chromosome
		
		}//End of FOR each elementTypeNumber

		
		

			
		
	}
	//27 July 2015 ends
	
	
	
	// 4 NOV 2014
	// Enrichment
	// Without IO
	// With Numbers
	public static void searchUserDefinedLibraryWithoutIOWithNumbers( 
			int permutationNumber, 
			ChromosomeName chromName,
			List<InputLineMinimal> inputLines, 
			IntervalTree userDefinedLibraryIntervalTree,
			TLongIntMap permutationNumberElementTypeNumberElementNumber2KMap, 
			AssociationMeasureType associationMeasureType,
			int overlapDefinition) {

		InputLineMinimal inputLine;
		
			
		for( int i = 0; i < inputLines.size(); i++){
			
			//Get each input line
			inputLine = inputLines.get(i);

			switch(associationMeasureType){
			
				case EXISTENCE_OF_OVERLAP:
				
					/*************************************************************************************************/
					/*******************************EXISTENCE_OF_OVERLAP starts***************************************/
					/*************************************************************************************************/
					TLongIntMap permutationNumberElementTypeNumberElementNumber2ZeroorOneMap = new TLongIntHashMap();
	
					if( userDefinedLibraryIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						userDefinedLibraryIntervalTree.findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers(
								permutationNumber, 
								userDefinedLibraryIntervalTree.getRoot(), 
								inputLine, chromName,
								permutationNumberElementTypeNumberElementNumber2ZeroorOneMap, 
								overlapDefinition);
					}//End of IF

					// accumulate search results of permutationNumberElementTypeNumberElementNumber2ZeroorOneMap in permutationNumberElementTypeNumberElementNumber2KMap
					for( TLongIntIterator it = permutationNumberElementTypeNumberElementNumber2ZeroorOneMap.iterator(); it.hasNext();){

						it.advance();

						if( !( permutationNumberElementTypeNumberElementNumber2KMap.containsKey( it.key()))){
							permutationNumberElementTypeNumberElementNumber2KMap.put( it.key(), it.value());
						}else{
							permutationNumberElementTypeNumberElementNumber2KMap.put( it.key(),
									permutationNumberElementTypeNumberElementNumber2KMap.get( it.key()) + it.value());

						}

					}// End of FOR
					
					//Free memory
					permutationNumberElementTypeNumberElementNumber2ZeroorOneMap = null;
					/*************************************************************************************************/
					/*******************************EXISTENCE_OF_OVERLAP ends*****************************************/
					/*************************************************************************************************/

					break;
					
				case NUMBER_OF_OVERLAPPING_BASES:
					
					//10 NOV 2015 starts
					/*************************************************************************************************/
					/*******************************NUMBER_OF_OVERLAPPING_BASES starts********************************/
					/*************************************************************************************************/
					TLongObjectMap<List<IntervalTreeNode>> permutationNumberElementTypeNumberElementNumber2OverlappingNodeListMap = new TLongObjectHashMap<List<IntervalTreeNode>>();
					TLongObjectMap<IntervalTree> permutationNumberElementTypeNumberElementNumber2IntervalTreeWithNonOverlappingNodesMap = new TLongObjectHashMap<IntervalTree>();
					TLongIntMap permutationNumberElementTypeNumberElementNumber2NumberofOverlappingBasesMap = new TLongIntHashMap();

					if( userDefinedLibraryIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						//Step1: Get all the overlappingIntervals with the inputLine
						userDefinedLibraryIntervalTree.findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers(
								permutationNumber,
								userDefinedLibraryIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								permutationNumberElementTypeNumberElementNumber2OverlappingNodeListMap);
						
						//Step2: Construct an intervalTree from the overlappingIntervals found in step1 such that there are no overlapping nodes in the tree 
						IntervalTree.constructAnIntervalTreeWithNonOverlappingNodes(
								permutationNumberElementTypeNumberElementNumber2OverlappingNodeListMap, 
								permutationNumberElementTypeNumberElementNumber2IntervalTreeWithNonOverlappingNodesMap);
						
						//Step3: Calculate the numberofOverlappingBases by overlapping the inputLine with the nodes in intervalTree
						//And fill permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap
						IntervalTree.findNumberofOverlappingBases(
								inputLine,
								permutationNumberElementTypeNumberElementNumber2IntervalTreeWithNonOverlappingNodesMap,
								permutationNumberElementTypeNumberElementNumber2NumberofOverlappingBasesMap);
						
					}//End of IF

					// Accumulate search results of permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap in permutationNumberHistoneNumberCellLineNumber2KMap
					for( TLongIntIterator it = permutationNumberElementTypeNumberElementNumber2NumberofOverlappingBasesMap.iterator(); it.hasNext();){

						it.advance();

						if( !(permutationNumberElementTypeNumberElementNumber2KMap.containsKey(it.key()))){
							permutationNumberElementTypeNumberElementNumber2KMap.put(it.key(), it.value());
						}else{
							permutationNumberElementTypeNumberElementNumber2KMap.put( it.key(),
								permutationNumberElementTypeNumberElementNumber2KMap.get( it.key()) + it.value());

						}

					}// End of FOR
					
					//Free memory
					permutationNumberElementTypeNumberElementNumber2OverlappingNodeListMap = null;
					permutationNumberElementTypeNumberElementNumber2IntervalTreeWithNonOverlappingNodesMap = null;
					permutationNumberElementTypeNumberElementNumber2NumberofOverlappingBasesMap = null;
					/*************************************************************************************************/
					/*******************************NUMBER_OF_OVERLAPPING_BASES ends**********************************/
					/*************************************************************************************************/
					//10 NOV 2015 ends
					
					break;
				
				default:
					break;
				
			
			}//End of SWITCH
	
		}// End of FOR each inputLine

	}

	// Enrichment
	// Without IO
	// With Numbers
	// Empirical P Value Calculation
	public static void searchHistoneWithoutIOWithNumbers(
			int permutationNumber, 
			ChromosomeName chromName,
			List<InputLineMinimal> inputLines, 
			IntervalTree histoneIntervalTree,
			TLongIntMap permutationNumberHistoneNumberCellLineNumber2KMap, 
			AssociationMeasureType associationMeasureType,
			int overlapDefinition) {
		
		InputLineMinimal inputLine;
	
		for(int i = 0; i < inputLines.size(); i++){

			inputLine = inputLines.get(i);
			
			switch(associationMeasureType){
			
				case EXISTENCE_OF_OVERLAP: 
				
					/*************************************************************************************************/
					/***********************************EXISTENCE_OF_OVERLAP starts***********************************/
					/*************************************************************************************************/
					TLongIntMap permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap = new TLongIntHashMap();

					if( histoneIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						histoneIntervalTree.findAllOverlappingHistoneIntervalsWithoutIOWithNumbers(
								permutationNumber,
								histoneIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap, 
								overlapDefinition);
						
					}//End of IF

					// Accumulate search results of permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap in permutationNumberHistoneNumberCellLineNumber2KMap
					for(TLongIntIterator it = permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap.iterator(); it.hasNext();){

						it.advance();

						if(!(permutationNumberHistoneNumberCellLineNumber2KMap.containsKey(it.key()))){
							permutationNumberHistoneNumberCellLineNumber2KMap.put(it.key(), it.value());
						}else{
							permutationNumberHistoneNumberCellLineNumber2KMap.put(it.key(),
									permutationNumberHistoneNumberCellLineNumber2KMap.get(it.key()) + it.value());
						}

					}// End of FOR
					
					//Free memory
					permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap = null;
					/*************************************************************************************************/
					/***********************************EXISTENCE_OF_OVERLAP ends*************************************/
					/*************************************************************************************************/

					break;
					
					
				case NUMBER_OF_OVERLAPPING_BASES: 
					
					/*************************************************************************************************/
					/*******************************NUMBER_OF_OVERLAPPING_BASES starts********************************/
					/*************************************************************************************************/
					TLongObjectMap<List<IntervalTreeNode>> permutationNumberHistoneNumberCellLineNumber2OverlappingNodeListMap = new TLongObjectHashMap<List<IntervalTreeNode>>();
					TLongObjectMap<IntervalTree> permutationNumberHistoneNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap = new TLongObjectHashMap<IntervalTree>();
					TLongIntMap permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap = new TLongIntHashMap();

					if( histoneIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						//Step1: Get all the overlappingIntervals with the inputLine
						histoneIntervalTree.findAllOverlappingHistoneIntervalsWithoutIOWithNumbers(
								permutationNumber,
								histoneIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								permutationNumberHistoneNumberCellLineNumber2OverlappingNodeListMap);
						
						//Step2: Construct an intervalTree from the overlappingIntervals found in step1 such that there are no overlapping nodes in the tree 
						IntervalTree.constructAnIntervalTreeWithNonOverlappingNodes(
								permutationNumberHistoneNumberCellLineNumber2OverlappingNodeListMap, 
								permutationNumberHistoneNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap);
						
						//Step3: Calculate the numberofOverlappingBases by overlapping the inputLine with the nodes in intervalTree
						//And fill permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap
						IntervalTree.findNumberofOverlappingBases(
								inputLine,
								permutationNumberHistoneNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap,
								permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap);
						
					}//End of IF

					// Accumulate search results of permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap in permutationNumberHistoneNumberCellLineNumber2KMap
					for( TLongIntIterator it = permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap.iterator(); it.hasNext();){

						it.advance();

						if( !(permutationNumberHistoneNumberCellLineNumber2KMap.containsKey(it.key()))){
							permutationNumberHistoneNumberCellLineNumber2KMap.put(it.key(), it.value());
						}else{
							permutationNumberHistoneNumberCellLineNumber2KMap.put( it.key(),
									permutationNumberHistoneNumberCellLineNumber2KMap.get( it.key()) + it.value());

						}

					}// End of FOR
					
					//Free memory
					permutationNumberHistoneNumberCellLineNumber2OverlappingNodeListMap = null;
					permutationNumberHistoneNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap = null;
					permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap = null;
					/*************************************************************************************************/
					/*******************************NUMBER_OF_OVERLAPPING_BASES ends**********************************/
					/*************************************************************************************************/

					break;
					
				default: 
					break;
	
			}//End of SWITCH
			

		}// End of FOR each inputline

	}

	// @todo

	// starts
	// Annotation
	// With Numbers
	public void searchUserDefinedLibraryWithNumbers(
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			ChromosomeName chromName, 
			BufferedReader bufferedReader, 
			IntervalTree userDefinedLibraryIntervalTree,
			TIntIntMap elementNumber2KMap, 
			int overlapDefinition, 
			String elementType,
			TIntObjectMap<String> elementNumber2ElementNameMap, 
			TIntObjectMap<String> fileNumber2FileNameMap) {

		String strLine = null;
		int indexofFirstTab = -1;
		int indexofSecondTab = -1;

		int low;
		int high;

		try{
			while( ( strLine = bufferedReader.readLine()) != null){

				TIntByteMap elementNumber2ZeroorOneMap = new TIntByteHashMap();

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0){
					low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				}else{
					low = Integer.parseInt( strLine.substring( indexofFirstTab + 1));
					high = low;

				}

				Interval interval = new Interval( low, high);

				if( userDefinedLibraryIntervalTree.getRoot().getNodeName().isNotSentinel()){
					userDefinedLibraryIntervalTree.findAllOverlappingUserDefinedLibraryIntervalsWithNumbers(
							outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
							userDefinedLibraryIntervalTree.getRoot(), interval, chromName, elementNumber2ZeroorOneMap,
							overlapDefinition, elementType, elementNumber2ElementNameMap, fileNumber2FileNameMap);
				}

				// accumulate search results of dnaseCellLine2OneorZeroMap in
				// dnaseCellLine2KMap
				for( TIntByteIterator it = elementNumber2ZeroorOneMap.iterator(); it.hasNext();){
					it.advance();

					if( !elementNumber2KMap.containsKey( it.key())){
						elementNumber2KMap.put( it.key(), it.value());
					}else{
						elementNumber2KMap.put( it.key(), elementNumber2KMap.get( it.key()) + it.value());

					}

				}// End of FOR

				// After Accumulation set to null
				elementNumber2ZeroorOneMap = null;

			}// End of WHILE

		}catch( NumberFormatException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while

	}

	// ends

	// Modified 20 July 2015
	// Annotation
	// With Numbers
	public void searchTranscriptionFactorWithNumbers(
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			RegulatorySequenceAnalysisType regulatorySequenceAnalysisUsingRSAT, 
			ChromosomeName chromName,
			BufferedReader bufferedReader, 
			IntervalTree tfIntervalTree, 
			TIntIntMap tfNumberCellLineNumber2KMap,
			int overlapDefinition, 
			TIntObjectMap<String> tfNumber2TFNameMap,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap, 
			TIntObjectMap<String> fileNumber2FileNameMap) {

		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;

		int low;
		int high;

		try{

			while( ( strLine = bufferedReader.readLine()) != null){

				TIntByteMap tfNumberCellLineNumber2ZeroorOneMap = new TIntByteHashMap();

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0)
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				else
					high = low;

				Interval interval = new Interval( low, high);

				if( tfIntervalTree.getRoot().getNodeName().isNotSentinel()){
					tfIntervalTree.findAllOverlappingTfbsIntervalsWithNumbers( outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, regulatorySequenceAnalysisUsingRSAT,
							tfIntervalTree.getRoot(), interval, chromName, tfNumberCellLineNumber2ZeroorOneMap,
							overlapDefinition, tfNumber2TFNameMap, cellLineNumber2CellLineNameMap,
							fileNumber2FileNameMap);
				}

				// accumulate search results of dnaseCellLine2OneorZeroMap in
				// dnaseCellLine2KMap
				for( TIntByteIterator it = tfNumberCellLineNumber2ZeroorOneMap.iterator(); it.hasNext();){

					it.advance();

					if( !tfNumberCellLineNumber2KMap.containsKey( it.key())){
						tfNumberCellLineNumber2KMap.put( it.key(), it.value());
					}else{
						tfNumberCellLineNumber2KMap.put( it.key(),
								tfNumberCellLineNumber2KMap.get( it.key()) + it.value());
					}

				}// End of FOR

				// After Accumulation set to null
				tfNumberCellLineNumber2ZeroorOneMap = null;

			}// End of WHILE

		}catch( NumberFormatException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while
	}

	// @todo searchTranscriptionFactorWithNumbers ends

	//Modified 20 July 2015
	// Annotation
	// With Numbers
	public void searchHistoneWithNumbers(
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			ChromosomeName chromName, 
			BufferedReader bufferedReader, 
			IntervalTree histoneIntervalTree,
			TIntIntMap histoneNumberCellLineNumber2KMap, 
			int overlapDefinition,
			TIntObjectMap<String> histoneNumber2HistoneNameMap,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap, 
			TIntObjectMap<String> fileNumber2FileNameMap) {

		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;

		int low;
		int high;

		try{

			while( ( strLine = bufferedReader.readLine()) != null){

				TIntByteMap histoneNumberCellLineNumber2ZeroorOneMap = new TIntByteHashMap();

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0)
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				else
					high = low;

				Interval interval = new Interval( low, high);

				if( histoneIntervalTree.getRoot().getNodeName().isNotSentinel()){
					histoneIntervalTree.findAllOverlappingHistoneIntervalsWithNumbers(outputFolder,
							writeElementBasedAnnotationFoundOverlapsMode, histoneIntervalTree.getRoot(), interval,
							chromName, histoneNumberCellLineNumber2ZeroorOneMap, overlapDefinition,
							histoneNumber2HistoneNameMap, cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
				}

				// accumulate search results of dnaseCellLine2OneorZeroMap in
				// dnaseCellLine2KMap
				for( TIntByteIterator it = histoneNumberCellLineNumber2ZeroorOneMap.iterator(); it.hasNext();){

					it.advance();

					if( !histoneNumberCellLineNumber2KMap.containsKey( it.key())){
						histoneNumberCellLineNumber2KMap.put( it.key(), it.value());
					}else{
						histoneNumberCellLineNumber2KMap.put( it.key(),
								histoneNumberCellLineNumber2KMap.get( it.key()) + it.value());
					}

				}// End of FOR

				// After accumulation set to null
				histoneNumberCellLineNumber2ZeroorOneMap = null;

			}// End of WHILE

		}catch( NumberFormatException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while
	}

	// @todo searchHistoneWithNumbers ends

	// 23 OCT 2014
	// Enrichment
	// With IO
	// With Numbers
	// Empirical P Value Calculation
	// Search GeneSet
	// KEGGPathway or UserDefinedGeneSet
	public static void searchUcscRefSeqGenesWithIOWithNumbers( String outputFolder, int permutationNumber,
			ChromosomeName chromName, List<InputLineMinimal> inputLines, IntervalTree ucscRefSeqGenesIntervalTree,
			TIntObjectMap<BufferedWriter> permutationNumberKeggPathwayNumber2BufferedWriterMap,
			TLongObjectMap<BufferedWriter> permutationNumberUserDefinedGeneSetNumber2BufferedWriterMap,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap,
			TIntIntMap permutationNumberKeggPathwayNumber2KMap,
			TLongIntMap permutationNumberUserDefinedGeneSetNumber2KMap, String type,
			GeneSetAnalysisType geneSetAnalysisType, GeneSetType geneSetType, int overlapDefinition) {

		InputLineMinimal inputLine;

		for( int i = 0; i < inputLines.size(); i++){

			TIntIntMap permutationNumberKeggPathwayNumber2OneorZeroMap = null;
			TLongIntMap permutationNumberUserDefinedGeneSetNumber2OneorZeroMap = null;

			if( geneSetType.isKeggPathway()){
				permutationNumberKeggPathwayNumber2OneorZeroMap = new TIntIntHashMap();
			}else if( geneSetType.isUserDefinedGeneSet()){
				permutationNumberUserDefinedGeneSetNumber2OneorZeroMap = new TLongIntHashMap();
			}

			inputLine = inputLines.get( i);

			if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){
				ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervalsWithIOWithNumbers( outputFolder,
						permutationNumber, ucscRefSeqGenesIntervalTree.getRoot(), inputLine, chromName,
						permutationNumberKeggPathwayNumber2BufferedWriterMap,
						permutationNumberUserDefinedGeneSetNumber2BufferedWriterMap, geneId2ListofGeneSetNumberMap,
						permutationNumberKeggPathwayNumber2OneorZeroMap,
						permutationNumberUserDefinedGeneSetNumber2OneorZeroMap, type, geneSetAnalysisType, geneSetType,
						overlapDefinition);
			}

			if( geneSetType.isKeggPathway()){

				// accumulate search results of
				// permutationNumberKeggPathwayNumber2OneorZeroMap
				// in permutationNumberKeggPathwayNumber2KMap
				for( TIntIntIterator it = permutationNumberKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					if( !( permutationNumberKeggPathwayNumber2KMap.containsKey( it.key()))){
						permutationNumberKeggPathwayNumber2KMap.put( it.key(), it.value());
					}else{
						permutationNumberKeggPathwayNumber2KMap.put( it.key(),
								permutationNumberKeggPathwayNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for

			}else if( geneSetType.isUserDefinedGeneSet()){

				// accumulate search results of
				// permutationNumberUserDefinedGeneSetNumber2OneorZeroMap
				// in permutationNumberUserDefinedGeneSetNumber2KMap
				for( TLongIntIterator it = permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					if( !( permutationNumberUserDefinedGeneSetNumber2KMap.containsKey( it.key()))){
						permutationNumberUserDefinedGeneSetNumber2KMap.put( it.key(), it.value());
					}else{
						permutationNumberUserDefinedGeneSetNumber2KMap.put( it.key(),
								permutationNumberUserDefinedGeneSetNumber2KMap.get( it.key()) + it.value());
					}

				}// End of for
			}

		}// End of for each inputline

	}
	// Enrichment
	// With IO
	// With Numbers
	
	// 22 July 2015
	// 7 July 2015 
	// KEGGPathway
	// Without ZScores
	// Without IO
	// With Numbers
	public static void searchUcscRefSeqGenesWithoutIOWithNumbersForAllChromosome(
			TIntObjectMap<List<InputLineMinimal>> chrNumber2RandomlyGeneratedData, 
			TIntObjectMap<IntervalTree> chrNumber2IntervalTreeMap,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap,
			TIntIntMap keggPathwayNumber2PermutationKMap,
			TIntIntMap userDefinedGeneSetNumber2PermutationKMap, 
			TIntIntMap geneNumber2PermutationKMap,
			String type, 
			GeneSetAnalysisType geneSetAnalysisType, 
			GeneSetType geneSetType, 
			int overlapDefinition) {

		
		ChromosomeName chromName;

		List<InputLineMinimal> inputLines;
		InputLineMinimal inputLine;

		IntervalTree ucscRefSeqGenesIntervalTree;
		
		for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

			chromName = GRCh37Hg19Chromosome.getChromosomeName(chrNumber);
			inputLines = chrNumber2RandomlyGeneratedData.get(chrNumber);
			ucscRefSeqGenesIntervalTree = chrNumber2IntervalTreeMap.get(chrNumber);

			if( inputLines != null){

				for( int i = 0; i < inputLines.size(); i++){
		
					TIntByteMap keggPathwayNumber2PermutationOneorZeroMap = null;
					TIntByteMap userDefinedGeneSetNumber2PermutationOneorZeroMap = null;
					TIntByteMap geneNumber2PermutationOneorZeroMap = null;
					
		
					if( geneSetType.isKeggPathway()){
						keggPathwayNumber2PermutationOneorZeroMap = new TIntByteHashMap();
					}else if( geneSetType.isUserDefinedGeneSet()){
						userDefinedGeneSetNumber2PermutationOneorZeroMap = new TIntByteHashMap();
					}else if( geneSetType.isNoGeneSetTypeDefined()){
						geneNumber2PermutationOneorZeroMap = new TIntByteHashMap();
					}
		
					inputLine = inputLines.get( i);
		
					if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
								ucscRefSeqGenesIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								geneId2ListofGeneSetNumberMap, 
								keggPathwayNumber2PermutationOneorZeroMap,
								userDefinedGeneSetNumber2PermutationOneorZeroMap,
								geneNumber2PermutationOneorZeroMap, 
								type, 
								geneSetAnalysisType, 
								geneSetType,
								overlapDefinition);
					}
		
					// KEGG Pathway starts
					if( geneSetType.isKeggPathway()){
		
						// accumulate search results of keggPathway2OneorZeroMap in keggPathway2KMap
						for( TIntByteIterator it = keggPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
		
							it.advance();
		
							if( !(keggPathwayNumber2PermutationKMap.containsKey( it.key()))){
								keggPathwayNumber2PermutationKMap.put( it.key(), it.value());
							}else{
								keggPathwayNumber2PermutationKMap.put(it.key(),keggPathwayNumber2PermutationKMap.get( it.key()) + it.value());
							}
		
						}// End of for
		
					}
					// KEGG Pathway ends
		
					// UserDefinedGeneSet starts
					else if( geneSetType.isUserDefinedGeneSet()){
		
						// accumulate search results of keggPathway2OneorZeroMap in
						// keggPathway2KMap
						for( TIntByteIterator it = userDefinedGeneSetNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
		
							it.advance();
		
							if( !(userDefinedGeneSetNumber2PermutationKMap.containsKey( it.key()))){
								userDefinedGeneSetNumber2PermutationKMap.put( it.key(), it.value());
							}else{
								userDefinedGeneSetNumber2PermutationKMap.put( it.key(),
										userDefinedGeneSetNumber2PermutationKMap.get( it.key()) + it.value());
		
							}
		
						}// End of for
		
					}
					// UserDefinedGeneSet ends
		
					// Gene starts
					else if( geneSetType.isNoGeneSetTypeDefined()){
		
						// accumulate search results of keggPathway2OneorZeroMap in
						// keggPathway2KMap
						for( TIntByteIterator it = geneNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
		
							it.advance();
		
							if( !(geneNumber2PermutationKMap.containsKey( it.key()))){
								geneNumber2PermutationKMap.put( it.key(), it.value());
							}else{
								geneNumber2PermutationKMap.put( it.key(),
										geneNumber2PermutationKMap.get( it.key()) + it.value());
		
							}
		
						}// End of for
		
					}
					// Gene ends
		
				}// End of for each input line
		
			}// End of IF inputLines is not NULL

		}// End of FOR each Chromosome

	}
	// 7 July 2015

	// 23 OCT 2014
	// Enrichment
	// Without IO
	// With Numbers
	// Empirical P Value Calculation
	// GeneSetType added 14.10.2014
	// Search GeneSet
	public static void searchUcscRefSeqGenesWithoutIOWithNumbers( 
			int permutationNumber, 
			ChromosomeName chromName,
			List<InputLineMinimal> inputLines, 
			IntervalTree ucscRefSeqGenesIntervalTree,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap,
			TIntIntMap permutationNumberKeggPathwayNumber2KMap,
			TLongIntMap permutationNumberUserDefinedGeneSetNumber2KMap, 
			TLongIntMap permutationNumberGeneNumber2KMap,
			String type, 
			GeneSetAnalysisType geneSetAnalysisType, 
			GeneSetType geneSetType, 
			AssociationMeasureType associationMeasureType,
			int overlapDefinition) {

		InputLineMinimal inputLine;
		
		
		for( int i = 0; i < inputLines.size(); i++){
			
			//Get the inputLine
			inputLine = inputLines.get( i);
			
			switch(associationMeasureType){
				
				case EXISTENCE_OF_OVERLAP:

					/*************************************************************************************************/
					/***********************************EXISTENCE_OF_OVERLAP starts***********************************/
					/*************************************************************************************************/
					TIntIntMap permutationNumberKeggPathwayNumber2OneorZeroMap = null;
					TLongIntMap permutationNumberUserDefinedGeneSetNumber2OneorZeroMap = null;
					TLongIntMap permutationNumberGeneNumber2OneorZeroMap = null;

					if( geneSetType.isKeggPathway()){
						permutationNumberKeggPathwayNumber2OneorZeroMap = new TIntIntHashMap();
					}else if( geneSetType.isUserDefinedGeneSet()){
						permutationNumberUserDefinedGeneSetNumber2OneorZeroMap = new TLongIntHashMap();
					}else if( geneSetType.isNoGeneSetTypeDefined()){
						permutationNumberGeneNumber2OneorZeroMap = new TLongIntHashMap();
					}

				
					if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){
						ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
								permutationNumber, 
								ucscRefSeqGenesIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								geneId2ListofGeneSetNumberMap, 
								permutationNumberKeggPathwayNumber2OneorZeroMap,
								permutationNumberUserDefinedGeneSetNumber2OneorZeroMap,
								permutationNumberGeneNumber2OneorZeroMap, 
								type, 
								geneSetAnalysisType, 
								geneSetType,
								overlapDefinition);
					}

					// KEGG Pathway starts
					if( geneSetType.isKeggPathway()){

						// accumulate search results of keggPathway2OneorZeroMap in
						// keggPathway2KMap
						for( TIntIntIterator it = permutationNumberKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){

							it.advance();

							if( !( permutationNumberKeggPathwayNumber2KMap.containsKey( it.key()))){
								permutationNumberKeggPathwayNumber2KMap.put( it.key(), it.value());
							}else{
								permutationNumberKeggPathwayNumber2KMap.put( it.key(),
										permutationNumberKeggPathwayNumber2KMap.get( it.key()) + it.value());

							}

						}// End of for

					}
					// KEGG Pathway ends

					// UserDefinedGeneSet starts
					else if( geneSetType.isUserDefinedGeneSet()){

						// accumulate search results of keggPathway2OneorZeroMap in
						// keggPathway2KMap
						for( TLongIntIterator it = permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.iterator(); it.hasNext();){

							it.advance();

							if( !( permutationNumberUserDefinedGeneSetNumber2KMap.containsKey( it.key()))){
								permutationNumberUserDefinedGeneSetNumber2KMap.put( it.key(), it.value());
							}else{
								permutationNumberUserDefinedGeneSetNumber2KMap.put( it.key(),
										permutationNumberUserDefinedGeneSetNumber2KMap.get( it.key()) + it.value());

							}

						}// End of for

					}
					// UserDefinedGeneSet ends

					// Gene starts
					else if( geneSetType.isNoGeneSetTypeDefined()){

						// accumulate search results of keggPathway2OneorZeroMap in
						// keggPathway2KMap
						for( TLongIntIterator it = permutationNumberGeneNumber2OneorZeroMap.iterator(); it.hasNext();){

							it.advance();

							if( !( permutationNumberGeneNumber2KMap.containsKey( it.key()))){
								permutationNumberGeneNumber2KMap.put( it.key(), it.value());
							}else{
								permutationNumberGeneNumber2KMap.put( it.key(),
										permutationNumberGeneNumber2KMap.get( it.key()) + it.value());

							}

						}// End of for

					}
					// Gene ends
					
					//Free memory
					permutationNumberKeggPathwayNumber2OneorZeroMap = null;
					permutationNumberUserDefinedGeneSetNumber2OneorZeroMap = null;
					permutationNumberGeneNumber2OneorZeroMap = null;
					/*************************************************************************************************/
					/***********************************EXISTENCE_OF_OVERLAP ends*************************************/
					/*************************************************************************************************/
					break;
					
				case NUMBER_OF_OVERLAPPING_BASES:
					
					/*************************************************************************************************/
					/***********************************NUMBER_OF_OVERLAPPING_BASES starts****************************/
					/*************************************************************************************************/
					TIntObjectMap<List<IntervalTreeNode>> permutationNumberKeggPathwayNumber2OverlappingNodeListMap = null;
					TIntObjectMap<IntervalTree> permutationNumberKeggPathwayNumber2IntervalTreeWithNonOverlappingNodesMap =null;
					TIntIntMap permutationNumberKeggPathwayNumber2NumberofOverlappingBasesMap = null;

					TLongObjectMap<List<IntervalTreeNode>> permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap = null;
					TLongObjectMap<IntervalTree> permutationNumberUserDefinedGeneSetNumber2IntervalTreeWithNonOverlappingNodesMap = null;
					TLongIntMap permutationNumberUserDefinedGeneSetNumber2NumberofOverlappingBasesMap = null;

					TLongObjectMap<List<IntervalTreeNode>> permutationNumberGeneNumber2OverlappingNodeListMap = null;
					TLongObjectMap<IntervalTree> permutationNumberGeneNumber2IntervalTreeWithNonOverlappingNodesMap = null;
					TLongIntMap permutationNumberGeneNumber2NumberofOverlappingBasesMap = null;
					
					
					//Memory Allocation for maps
					switch(geneSetType){
						
						case KEGGPATHWAY:
							permutationNumberKeggPathwayNumber2OverlappingNodeListMap = new TIntObjectHashMap<List<IntervalTreeNode>>();
							permutationNumberKeggPathwayNumber2IntervalTreeWithNonOverlappingNodesMap = new TIntObjectHashMap<IntervalTree>();
							permutationNumberKeggPathwayNumber2NumberofOverlappingBasesMap = new TIntIntHashMap();
							break;
							
						case USERDEFINEDGENESET:
							permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap = new TLongObjectHashMap<List<IntervalTreeNode>>();
							permutationNumberUserDefinedGeneSetNumber2IntervalTreeWithNonOverlappingNodesMap =  new TLongObjectHashMap<IntervalTree>();
							permutationNumberUserDefinedGeneSetNumber2NumberofOverlappingBasesMap =  new TLongIntHashMap();
							break;
							
						case NO_GENESET_TYPE_IS_DEFINED:
							permutationNumberGeneNumber2OverlappingNodeListMap = new TLongObjectHashMap<List<IntervalTreeNode>>();
							permutationNumberGeneNumber2IntervalTreeWithNonOverlappingNodesMap = new TLongObjectHashMap<IntervalTree>();
							permutationNumberGeneNumber2NumberofOverlappingBasesMap = new TLongIntHashMap();
							break;
						
					}//End of SWITCH
					


					if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						//Step1: Get all the overlappingIntervals with the inputLine
						ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
								permutationNumber, 
								ucscRefSeqGenesIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								geneId2ListofGeneSetNumberMap, 
								permutationNumberKeggPathwayNumber2OverlappingNodeListMap,
								permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap,
								permutationNumberGeneNumber2OverlappingNodeListMap, 
								type, 
								geneSetAnalysisType, 
								geneSetType);
						
						//Step2: Construct an intervalTree from the overlappingIntervals found in step1 such that there are no overlapping nodes in the tree 
						switch(geneSetType){
							
							case KEGGPATHWAY:
								IntervalTree.constructAnIntervalTreeWithNonOverlappingNodes(
										permutationNumberKeggPathwayNumber2OverlappingNodeListMap, 
										permutationNumberKeggPathwayNumber2IntervalTreeWithNonOverlappingNodesMap);
								break;
								
							case USERDEFINEDGENESET:
								IntervalTree.constructAnIntervalTreeWithNonOverlappingNodes(
										permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap, 
										permutationNumberUserDefinedGeneSetNumber2IntervalTreeWithNonOverlappingNodesMap);
								break;
								
							case NO_GENESET_TYPE_IS_DEFINED:
								IntervalTree.constructAnIntervalTreeWithNonOverlappingNodes(
										permutationNumberGeneNumber2OverlappingNodeListMap, 
										permutationNumberGeneNumber2IntervalTreeWithNonOverlappingNodesMap);
								break;
							
						}//End of SWITCH

						
						
						//Step3: Calculate the numberofOverlappingBases by overlapping the inputLine with the nodes in intervalTree
						//And fill permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap
						switch(geneSetType){
						
							case KEGGPATHWAY:
								IntervalTree.findNumberofOverlappingBases(
										inputLine,
										permutationNumberKeggPathwayNumber2IntervalTreeWithNonOverlappingNodesMap,
										permutationNumberKeggPathwayNumber2NumberofOverlappingBasesMap);
								break;
								
							case USERDEFINEDGENESET:
								IntervalTree.findNumberofOverlappingBases(
										inputLine,
										permutationNumberUserDefinedGeneSetNumber2IntervalTreeWithNonOverlappingNodesMap, 
										permutationNumberUserDefinedGeneSetNumber2NumberofOverlappingBasesMap);
								break;
								
							case NO_GENESET_TYPE_IS_DEFINED:
								IntervalTree.findNumberofOverlappingBases(
										inputLine,
										permutationNumberGeneNumber2IntervalTreeWithNonOverlappingNodesMap, 
										permutationNumberGeneNumber2NumberofOverlappingBasesMap);
								break;
						
						}//End of SWITCH

						
					}//End of IF intervalTree root node is NOT SENTINEL

					// Accumulate search results of permutationNumberHistoneNumberCellLineNumber2NumberofOverlappingBasesMap in permutationNumberHistoneNumberCellLineNumber2KMap
					switch(geneSetType){
						case KEGGPATHWAY:
							
							for( TIntIntIterator it = permutationNumberKeggPathwayNumber2NumberofOverlappingBasesMap.iterator(); it.hasNext();){

								it.advance();

								if( !(permutationNumberKeggPathwayNumber2KMap.containsKey(it.key()))){
									permutationNumberKeggPathwayNumber2KMap.put(it.key(), it.value());
								}else{
									permutationNumberKeggPathwayNumber2KMap.put( it.key(),
											permutationNumberKeggPathwayNumber2KMap.get( it.key()) + it.value());

								}

							}// End of FOR
							break;
							
						case USERDEFINEDGENESET:
							
							for( TLongIntIterator it = permutationNumberUserDefinedGeneSetNumber2NumberofOverlappingBasesMap.iterator(); it.hasNext();){

								it.advance();

								if( !(permutationNumberUserDefinedGeneSetNumber2KMap.containsKey(it.key()))){
									permutationNumberUserDefinedGeneSetNumber2KMap.put(it.key(), it.value());
								}else{
									permutationNumberUserDefinedGeneSetNumber2KMap.put( it.key(),
											permutationNumberUserDefinedGeneSetNumber2KMap.get( it.key()) + it.value());

								}

							}// End of FOR
							break;
						case NO_GENESET_TYPE_IS_DEFINED:
							
							for( TLongIntIterator it = permutationNumberGeneNumber2NumberofOverlappingBasesMap.iterator(); it.hasNext();){

								it.advance();

								if( !(permutationNumberGeneNumber2KMap.containsKey(it.key()))){
									permutationNumberGeneNumber2KMap.put(it.key(), it.value());
								}else{
									permutationNumberGeneNumber2KMap.put( it.key(),
											permutationNumberGeneNumber2KMap.get( it.key()) + it.value());

								}

							}// End of FOR
							break;
					
					}//End of SWITCH
					
										
					//Free memory
					switch(geneSetType){
						
						case KEGGPATHWAY:
							permutationNumberKeggPathwayNumber2OverlappingNodeListMap = null;
							permutationNumberKeggPathwayNumber2IntervalTreeWithNonOverlappingNodesMap = null;
							permutationNumberKeggPathwayNumber2NumberofOverlappingBasesMap = null;
							break;
							
						case USERDEFINEDGENESET:
							permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap = null;
							permutationNumberUserDefinedGeneSetNumber2IntervalTreeWithNonOverlappingNodesMap =  null;
							permutationNumberUserDefinedGeneSetNumber2NumberofOverlappingBasesMap =  null;
							break;
							
						case NO_GENESET_TYPE_IS_DEFINED:
							permutationNumberGeneNumber2OverlappingNodeListMap = null;
							permutationNumberGeneNumber2IntervalTreeWithNonOverlappingNodesMap = null;
							permutationNumberGeneNumber2NumberofOverlappingBasesMap = null;
							break;
						
					}//End of SWITCH
					/*************************************************************************************************/
					/***********************************NUMBER_OF_OVERLAPPING_BASES ends******************************/
					/*************************************************************************************************/
					break;
					
				default:
					break;
			
			}//End of SWITCH associationMeasureType

		}// End of FOR each input line

	}

	// GeneSetType added 14.10.2014

	// Enrichment
	// With IO
	// With Numbers
	// TF and KeggPathway Enrichment or
	// TF and CellLine and KeggPathway Enrichment starts
	// Empirical P Value Calculation
	// Search tf and keggPathway
	// TF CellLine Exon Based Kegg Pathway
	// TF CellLine Regulation Based Kegg Pathway
	// TF CellLine All Based Kegg Pathway
	// TF Exon Based Kegg Pathway
	// TF Regulation Based Kegg Pathway
	// TF All Based Kegg Pathway
	public static void searchTfandKeggPathwayWithIOWithNumbers( String outputFolder, int permutationNumber,
			ChromosomeName chromName, List<InputLineMinimal> inputLines, IntervalTree tfIntervalTree,
			IntervalTree ucscRefSeqGenesIntervalTree, TLongObjectMap<BufferedWriter> tfBufferedWriterHashMap,
			TIntObjectMap<BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap,
			TIntObjectMap<BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap,
			TIntObjectMap<BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap,
			TLongObjectMap<BufferedWriter> tfExonBasedKeggPathwayBufferedWriterHashMap,
			TLongObjectMap<BufferedWriter> tfRegulationBasedKeggPathwayBufferedWriterHashMap,
			TLongObjectMap<BufferedWriter> tfAllBasedKeggPathwayBufferedWriterHashMap,
			TLongObjectMap<BufferedWriter> tfCellLineExonBasedKeggPathwayBufferedWriterHashMap,
			TLongObjectMap<BufferedWriter> tfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap,
			TLongObjectMap<BufferedWriter> tfCellLineAllBasedKeggPathwayBufferedWriterHashMap,
			TIntObjectMap<TIntList> geneId2KeggPathwayNumberMap,
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap,
			TIntIntMap permutationNumberExonBasedKeggPathway2KMap,
			TIntIntMap permutationNumberRegulationBasedKeggPathway2KMap,
			TIntIntMap permutationNumberAllBasedKeggPathway2KMap,
			TLongIntMap permutationNumberTfNumberExonBasedKeggPathway2KMap,
			TLongIntMap permutationNumberTfNumberRegulationBasedKeggPathway2KMap,
			TLongIntMap permutationNumberTfNumberAllBasedKeggPathway2KMap,
			TLongIntMap permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2KMap,
			TLongIntMap permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2KMap,
			TLongIntMap permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2KMap, String type,
			AnnotationType annotationType, int overlapDefinition) {

		FileWriter fileWriter1 = null;
		BufferedWriter bufferedWriter1 = null;

		FileWriter fileWriter2 = null;
		BufferedWriter bufferedWriter2 = null;

		long permutationNumberTfNumberCellLineNumberKeggPathwayNumber;
		long permutationNumberTfNumberCellLineNumber;
		long permutationNumberTfNumber;
		long permutationNumberTfNumberKeggPathwayNumber;

		int keggPathwayNumber;

		try{
			for( InputLineMinimal inputLine : inputLines){

				// TF Enrichment
				TLongIntMap permutationNumberTfNumberCellLineNumber2ZeroorOneMap = new TLongIntHashMap();

				// KEGGPATHWAY Enrichment
				TIntIntMap permutationNumberExonBasedKeggPathway2ZeroorOneMap = new TIntIntHashMap();
				TIntIntMap permutationNumberRegulationBasedKeggPathway2ZeroorOneMap = new TIntIntHashMap();
				TIntIntMap permutationNumberAllBasedKeggPathway2ZeroorOneMap = new TIntIntHashMap();

				// TF KEGGPATHWAY Enrichment and
				TLongIntMap permutationNumberTfNumberExonBasedKeggPathway2ZeroorOneMap = new TLongIntHashMap();
				TLongIntMap permutationNumberTfNumberRegulationBasedKeggPathway2ZeroorOneMap = new TLongIntHashMap();
				TLongIntMap permutationNumberTfNumberAllBasedKeggPathway2ZeroorOneMap = new TLongIntHashMap();

				// TF CellLine KEGGPATHWAY Enrichment and
				TLongIntMap permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2ZeroorOneMap = new TLongIntHashMap();
				TLongIntMap permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2ZeroorOneMap = new TLongIntHashMap();
				TLongIntMap permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2ZeroorOneMap = new TLongIntHashMap();

				// Fill these lists during search for tfs and search for
				// ucscRefSeqGenes
				List<PermutationNumberTfNumberCellLineNumberOverlap> permutationNumberTfNumberCellLineNumberOverlapList = new ArrayList<PermutationNumberTfNumberCellLineNumberOverlap>();
				List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberExonBasedKeggPathwayOverlapList = new ArrayList<PermutationNumberUcscRefSeqGeneNumberOverlap>();
				List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberRegulationBasedKeggPathwayOverlapList = new ArrayList<PermutationNumberUcscRefSeqGeneNumberOverlap>();
				List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberAllBasedKeggPathwayOverlapList = new ArrayList<PermutationNumberUcscRefSeqGeneNumberOverlap>();

				if( tfIntervalTree.getRoot().getNodeName().isNotSentinel()){
					tfIntervalTree.findAllOverlappingTfbsIntervalsWithIOWithNumbers( outputFolder, permutationNumber,
							tfIntervalTree.getRoot(), inputLine, chromName, tfBufferedWriterHashMap,
							permutationNumberTfNumberCellLineNumber2ZeroorOneMap,
							permutationNumberTfNumberCellLineNumberOverlapList, overlapDefinition);
				}

				// accumulate search results of
				// permutationNumberTfbsNameCellLineName2ZeroorOneMap in
				// permutationNumberTfbsNameCellLineName2KMap
				for( TLongIntIterator it = permutationNumberTfNumberCellLineNumber2ZeroorOneMap.iterator(); it.hasNext();){
					it.advance();

					if( !( permutationNumberTfNumberCellLineNumber2KMap.containsKey( it.key()))){
						permutationNumberTfNumberCellLineNumber2KMap.put( it.key(), it.value());
					}else{
						permutationNumberTfNumberCellLineNumber2KMap.put( it.key(),
								permutationNumberTfNumberCellLineNumber2KMap.get( it.key()) + it.value());
					}
				}// End of for

				if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){
					ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervalsWithIOWithNumbers(
							outputFolder, permutationNumber, ucscRefSeqGenesIntervalTree.getRoot(), inputLine,
							chromName, geneId2KeggPathwayNumberMap, exonBasedKeggPathwayBufferedWriterHashMap,
							regulationBasedKeggPathwayBufferedWriterHashMap, allBasedKeggPathwayBufferedWriterHashMap,
							permutationNumberExonBasedKeggPathway2ZeroorOneMap,
							permutationNumberRegulationBasedKeggPathway2ZeroorOneMap,
							permutationNumberAllBasedKeggPathway2ZeroorOneMap, type,
							permutationNumberExonBasedKeggPathwayOverlapList,
							permutationNumberRegulationBasedKeggPathwayOverlapList,
							permutationNumberAllBasedKeggPathwayOverlapList, overlapDefinition);
				}

				// accumulate search results of
				// permutationNumberExonBasedKeggPathway2ZeroorOneMap in
				// permutationNumberExonBasedKeggPathway2KMap
				for( TIntIntIterator it = permutationNumberExonBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
					it.advance();

					if( !( permutationNumberExonBasedKeggPathway2KMap.containsKey( it.key()))){
						permutationNumberExonBasedKeggPathway2KMap.put( it.key(), it.value());
					}else{
						permutationNumberExonBasedKeggPathway2KMap.put( it.key(),
								permutationNumberExonBasedKeggPathway2KMap.get( it.key()) + it.value());

					}
				}// End of for

				// accumulate search results of
				// permutationNumberRegulationBasedKeggPathway2ZeroorOneMap in
				// permutationNumberRegulationBasedKeggPathway2KMap
				for( TIntIntIterator it = permutationNumberRegulationBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
					it.advance();

					if( !( permutationNumberRegulationBasedKeggPathway2KMap.containsKey( it.key()))){
						permutationNumberRegulationBasedKeggPathway2KMap.put( it.key(), it.value());
					}else{
						permutationNumberRegulationBasedKeggPathway2KMap.put( it.key(),
								permutationNumberRegulationBasedKeggPathway2KMap.get( it.key()) + it.value());

					}
				}// End of for

				// accumulate search results of
				// permutationNumberAllBasedKeggPathway2ZeroorOneMap in
				// permutationNumberAllBasedKeggPathway2KMap
				for( TIntIntIterator it = permutationNumberAllBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
					it.advance();

					if( !( permutationNumberAllBasedKeggPathway2KMap.containsKey( it.key()))){
						permutationNumberAllBasedKeggPathway2KMap.put( it.key(), it.value());
					}else{
						permutationNumberAllBasedKeggPathway2KMap.put( it.key(),
								permutationNumberAllBasedKeggPathway2KMap.get( it.key()) + it.value());

					}
				}// End of for

				// New search for given input SNP or interval case, does not
				// matter.
				// for each tf overlap
				// for each ucscRefSeqGene overlap
				// if these overlaps overlaps
				// question will overlapDefinition apply here?
				// then write common overlap to output files
				// Fill
				// permutationNumberTfNameCellLineNameExonBasedKeggPathway2OneorZeroMap
				// Fill
				// permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2OneorZeroMap
				// Fill
				// permutationNumberTfNameCellLineNameAllBasedKeggPathway2OneorZeroMap

				// permutation number is same for tf and keggPathway maps
				// for example:
				// permutationNumberTfbsNameCellLineName2ZeroorOneMap
				// for example:
				// permutationNumberExonBasedKeggPathway2ZeroorOneMap
				// input lines are randomly generated data for this permutation
				// number
				// all the entries in these maps have 1 values.
				// if there is an entry, it means that it is 1.

				for( PermutationNumberTfNumberCellLineNumberOverlap permutationNumberTfNumberCellLineNumberOverlap : permutationNumberTfNumberCellLineNumberOverlapList){

					permutationNumberTfNumberCellLineNumber = permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber();
					permutationNumberTfNumber = IntervalTree.removeCellLineNumber(
							permutationNumberTfNumberCellLineNumber,
							GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

					// TF and Exon Based Kegg Pathway
					for( PermutationNumberUcscRefSeqGeneNumberOverlap permutationNumberUcscRefSeqGeneNumberOverlap : permutationNumberExonBasedKeggPathwayOverlapList){
						if( IntervalTree.overlaps( permutationNumberTfNumberCellLineNumberOverlap.getLow(),
								permutationNumberTfNumberCellLineNumberOverlap.getHigh(),
								permutationNumberUcscRefSeqGeneNumberOverlap.getLow(),
								permutationNumberUcscRefSeqGeneNumberOverlap.getHigh())){

							for( TIntIterator it = permutationNumberUcscRefSeqGeneNumberOverlap.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();

								if( annotationType.doTFKEGGPathwayAnnotation()){
									/******************************************************/
									permutationNumberTfNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberExonBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
										permutationNumberTfNumberExonBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberKeggPathwayNumber, 1);
									}
									/******************************************************/
									/******************************************************/
									bufferedWriter2 = tfExonBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberKeggPathwayNumber);

									if( bufferedWriter2 == null){

										fileWriter2 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter2 = new BufferedWriter( fileWriter2);
										tfExonBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberKeggPathwayNumber, bufferedWriter2);
										bufferedWriter2.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));

									}

									bufferedWriter2.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter2.flush();
									/******************************************************/

								}else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){
									/******************************************************/
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumberCellLineNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
										permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
									}
									/******************************************************/

									/******************************************************/
									bufferedWriter1 = tfCellLineExonBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber);

									if( bufferedWriter1 == null){

										fileWriter1 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberCellLineNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter1 = new BufferedWriter( fileWriter1);
										tfCellLineExonBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
												bufferedWriter1);
										bufferedWriter1.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name number" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name" + "\t" + "interval number" + "\t" + "hugo suymbol number" + "\t" + "entrez id" + "\t" + "keggPathwayNumber" + System.getProperty( "line.separator"));

									}

									bufferedWriter1.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter1.flush();
									/******************************************************/

								}else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

									// TF EXONKEGG
									/******************************************************/
									permutationNumberTfNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberExonBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
										permutationNumberTfNumberExonBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberKeggPathwayNumber, 1);
									}
									/******************************************************/
									/******************************************************/
									bufferedWriter2 = tfExonBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberKeggPathwayNumber);

									if( bufferedWriter2 == null){

										fileWriter2 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter2 = new BufferedWriter( fileWriter2);
										tfExonBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberKeggPathwayNumber, bufferedWriter2);
										bufferedWriter2.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name " + "\t" + "hugo suymbol" + "\t" + "entrez id" + "\t" + "keggPathwayName" + System.getProperty( "line.separator"));

									}

									bufferedWriter2.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter2.flush();
									/******************************************************/

									// TF CELLLINE EXONKEGG
									/******************************************************/
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumberCellLineNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
										permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
									}
									/******************************************************/

									/******************************************************/
									bufferedWriter1 = tfCellLineExonBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber);

									if( bufferedWriter1 == null){

										fileWriter1 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberCellLineNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter1 = new BufferedWriter( fileWriter1);
										tfCellLineExonBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
												bufferedWriter1);
										bufferedWriter1.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name number" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name" + "\t" + "interval number" + "\t" + "hugo suymbol number" + "\t" + "entrez id" + "\t" + "keggPathwayNumber" + System.getProperty( "line.separator"));

									}

									bufferedWriter1.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter1.flush();
									/******************************************************/

								}

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query

					// TF and Regulation Based Kegg Pathway
					for( PermutationNumberUcscRefSeqGeneNumberOverlap permutationNumberUcscRefSeqGeneNumberOverlap : permutationNumberRegulationBasedKeggPathwayOverlapList){
						if( IntervalTree.overlaps( permutationNumberTfNumberCellLineNumberOverlap.getLow(),
								permutationNumberTfNumberCellLineNumberOverlap.getHigh(),
								permutationNumberUcscRefSeqGeneNumberOverlap.getLow(),
								permutationNumberUcscRefSeqGeneNumberOverlap.getHigh())){
							for( TIntIterator it = permutationNumberUcscRefSeqGeneNumberOverlap.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();

								if( annotationType.doTFKEGGPathwayAnnotation()){

									/************************************************/
									permutationNumberTfNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberRegulationBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
										permutationNumberTfNumberRegulationBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberKeggPathwayNumber, 1);
									}
									/************************************************/

									/************************************************/
									bufferedWriter2 = tfRegulationBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberKeggPathwayNumber);

									if( bufferedWriter2 == null){

										fileWriter2 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter2 = new BufferedWriter( fileWriter2);
										tfRegulationBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberKeggPathwayNumber, bufferedWriter2);
										bufferedWriter2.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name number" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name" + "\t" + "interval number" + "\t" + "hugo suymbol number" + "\t" + "entrez id" + "\t" + "keggPathwayNumber" + System.getProperty( "line.separator"));

									}

									bufferedWriter2.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter2.flush();
									/************************************************/

								}else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){
									/************************************************/
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumberCellLineNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
										permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
									}
									/************************************************/

									/************************************************/
									bufferedWriter1 = tfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber);

									if( bufferedWriter1 == null){

										fileWriter1 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberCellLineNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter1 = new BufferedWriter( fileWriter1);
										tfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
												bufferedWriter1);
										bufferedWriter1.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name number" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name" + "\t" + "interval number" + "\t" + "hugo symbol number" + "\t" + "entrez id" + "\t" + "keggPathwayNumber" + System.getProperty( "line.separator"));

									}

									bufferedWriter1.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter1.flush();
									/************************************************/

								}else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

									// TF RegulationKEGG
									/************************************************/
									permutationNumberTfNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberRegulationBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
										permutationNumberTfNumberRegulationBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberKeggPathwayNumber, 1);
									}
									/************************************************/

									/************************************************/
									bufferedWriter2 = tfRegulationBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberKeggPathwayNumber);

									if( bufferedWriter2 == null){

										fileWriter2 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter2 = new BufferedWriter( fileWriter2);
										tfRegulationBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberKeggPathwayNumber, bufferedWriter2);
										bufferedWriter2.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name number" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name" + "\t" + "interval number" + "\t" + "hugo suymbol number" + "\t" + "entrez id" + "\t" + "keggPathwayNumber" + System.getProperty( "line.separator"));

									}

									bufferedWriter2.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter2.flush();
									/************************************************/

									// TF CellLine RegulationKEGG
									/************************************************/
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumberCellLineNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
										permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
									}
									/************************************************/

									/************************************************/
									bufferedWriter1 = tfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber);

									if( bufferedWriter1 == null){

										fileWriter1 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberCellLineNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter1 = new BufferedWriter( fileWriter1);
										tfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
												bufferedWriter1);
										bufferedWriter1.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name number" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name" + "\t" + "interval number" + "\t" + "hugo symbol number" + "\t" + "entrez id" + "\t" + "keggPathwayNumber" + System.getProperty( "line.separator"));

									}

									bufferedWriter1.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter1.flush();
									/************************************************/

								}

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query

					// TF and All Based Kegg Pathway
					for( PermutationNumberUcscRefSeqGeneNumberOverlap permutationNumberUcscRefSeqGeneNumberOverlap : permutationNumberAllBasedKeggPathwayOverlapList){
						if( IntervalTree.overlaps( permutationNumberTfNumberCellLineNumberOverlap.getLow(),
								permutationNumberTfNumberCellLineNumberOverlap.getHigh(),
								permutationNumberUcscRefSeqGeneNumberOverlap.getLow(),
								permutationNumberUcscRefSeqGeneNumberOverlap.getHigh())){
							for( TIntIterator it = permutationNumberUcscRefSeqGeneNumberOverlap.getKeggPathwayNumberList().iterator(); it.hasNext();){

								keggPathwayNumber = it.next();

								if( annotationType.doTFKEGGPathwayAnnotation()){

									/************************************************/
									permutationNumberTfNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberAllBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
										permutationNumberTfNumberAllBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberKeggPathwayNumber, 1);
									}
									/************************************************/

									/************************************************/
									bufferedWriter2 = tfAllBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberKeggPathwayNumber);

									if( bufferedWriter2 == null){

										fileWriter2 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter2 = new BufferedWriter( fileWriter2);
										tfAllBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberKeggPathwayNumber, bufferedWriter2);
										bufferedWriter2.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name number" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name" + "\t" + "interval number" + "\t" + "hugo suymbol number" + "\t" + "entrez id" + "\t" + "keggPathwayNumber" + System.getProperty( "line.separator"));

									}

									bufferedWriter2.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter2.flush();
									/************************************************/

								}else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){
									/************************************************/
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumberCellLineNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
										permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
									}
									/************************************************/

									/************************************************/
									bufferedWriter1 = tfCellLineAllBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber);

									if( bufferedWriter1 == null){

										fileWriter1 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberCellLineNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter1 = new BufferedWriter( fileWriter1);
										tfCellLineAllBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
												bufferedWriter1);
										bufferedWriter1.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name number" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name" + "\t" + "interval number" + "\t" + "hugo symbol number" + "\t" + "entrez id" + "\t" + "keggPathwayNumber" + System.getProperty( "line.separator"));

									}

									bufferedWriter1.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter1.flush();
									/************************************************/

								}else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

									// TF ALLKEGG
									/************************************************/
									permutationNumberTfNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberAllBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
										permutationNumberTfNumberAllBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberKeggPathwayNumber, 1);
									}
									/************************************************/

									/************************************************/
									bufferedWriter2 = tfAllBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberKeggPathwayNumber);

									if( bufferedWriter2 == null){

										fileWriter2 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter2 = new BufferedWriter( fileWriter2);
										tfAllBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberKeggPathwayNumber, bufferedWriter2);
										bufferedWriter2.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name number" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name" + "\t" + "interval number" + "\t" + "hugo suymbol number" + "\t" + "entrez id" + "\t" + "keggPathwayNumber" + System.getProperty( "line.separator"));

									}

									bufferedWriter2.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter2.flush();
									/************************************************/

									// TF CELLLINE ALLKEGG
									/************************************************/
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
											permutationNumberTfNumberCellLineNumber,
											keggPathwayNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									if( !( permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2ZeroorOneMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
										permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2ZeroorOneMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
									}
									/************************************************/

									/************************************************/
									bufferedWriter1 = tfCellLineAllBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber);

									if( bufferedWriter1 == null){

										fileWriter1 = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + "_" + permutationNumberTfNumberCellLineNumberKeggPathwayNumber + ".txt",
												true);
										bufferedWriter1 = new BufferedWriter( fileWriter1);
										tfCellLineAllBasedKeggPathwayBufferedWriterHashMap.put(
												permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
												bufferedWriter1);
										bufferedWriter1.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "tfbs" + "\t" + "tfbs low" + "\t" + "tfbs high" + "\t" + "refseq gene name number" + "\t" + "ucscRefSeqGene low" + "\t" + "ucscRefSeqGene high" + "\t" + "interval name" + "\t" + "interval number" + "\t" + "hugo symbol number" + "\t" + "entrez id" + "\t" + "keggPathwayNumber" + System.getProperty( "line.separator"));

									}

									bufferedWriter1.write( chromName + "\t" + inputLine.getLow() + "\t" + inputLine.getHigh() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getLow() + "\t" + permutationNumberTfNumberCellLineNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getRefSeqGeneNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getLow() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getHigh() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalName() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getIntervalNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneHugoSymbolNumber() + "\t" + permutationNumberUcscRefSeqGeneNumberOverlap.getGeneEntrezId() + "\t" + keggPathwayNumber + System.getProperty( "line.separator"));
									bufferedWriter1.flush();
									/************************************************/

								}

							} // for each kegg pathways having this gene
						}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
					}// for each ucscRefSeqGeneOverlap for the given query

				}// for each tfOverlap for the given query

				if( annotationType.doTFKEGGPathwayAnnotation()){

					// TF EXON BASED
					// Fill permutationNumberTfNameExonBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameExonBasedKeggPathway2ZeroorOneMap
					for( TLongIntIterator it = permutationNumberTfNumberExonBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberExonBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
							permutationNumberTfNumberExonBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberExonBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber,
									permutationNumberTfNumberExonBasedKeggPathway2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

					// TF REGULATION BASED
					// Fill
					// permutationNumberTfNameRegulationBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameRegulationBasedKeggPathway2ZeroorOneMap
					for( TLongIntIterator it = permutationNumberTfNumberRegulationBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberRegulationBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
							permutationNumberTfNumberRegulationBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberRegulationBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber,
									permutationNumberTfNumberRegulationBasedKeggPathway2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

					// TF ALL BASED
					// Fill permutationNumberTfNameAllBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameAllBasedKeggPathway2ZeroorOneMap
					for( TLongIntIterator it = permutationNumberTfNumberAllBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberAllBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
							permutationNumberTfNumberAllBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberAllBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber,
									permutationNumberTfNumberAllBasedKeggPathway2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

				}else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){
					// TF CELLLINE EXON BASED
					// Fill
					// permutationNumberTfNameCellLineNameExonBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameCellLineNameExonBasedKeggPathway2OneorZeroMap
					for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
							permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
									permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

					// TF CELLLINE REGULATION BASED
					// Fill
					// permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2OneorZeroMap
					for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
							permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
									permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

					// TF CELLLINE ALL BASED
					// Fill
					// permutationNumberTfNameCellLineNameAllBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameCellLineNameAllBasedKeggPathway2OneorZeroMap
					for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
							permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
									permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

				}else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

					// TF KEGG
					// TF EXONBASED
					// Fill permutationNumberTfNameExonBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameExonBasedKeggPathway2ZeroorOneMap
					for( TLongIntIterator it = permutationNumberTfNumberExonBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberExonBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
							permutationNumberTfNumberExonBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberExonBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber,
									permutationNumberTfNumberExonBasedKeggPathway2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

					// TF REGULATIONBASED
					// Fill
					// permutationNumberTfNameRegulationBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameRegulationBasedKeggPathway2ZeroorOneMap
					for( TLongIntIterator it = permutationNumberTfNumberRegulationBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberRegulationBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
							permutationNumberTfNumberRegulationBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberRegulationBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber,
									permutationNumberTfNumberRegulationBasedKeggPathway2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

					// TF ALLBASED
					// Fill permutationNumberTfNameAllBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameAllBasedKeggPathway2ZeroorOneMap
					for( TLongIntIterator it = permutationNumberTfNumberAllBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberAllBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
							permutationNumberTfNumberAllBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberAllBasedKeggPathway2KMap.put(
									permutationNumberTfNumberKeggPathwayNumber,
									permutationNumberTfNumberAllBasedKeggPathway2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

					// TF CELLLINE KEGG
					// TF CELLLINE EXONBASED
					// Fill
					// permutationNumberTfNameCellLineNameExonBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameCellLineNameExonBasedKeggPathway2OneorZeroMap
					for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
							permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
									permutationNumberTfNumberCellLineNumberExonBasedKeggPathway2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

					// TF CELLLINE REGULATIONBASED
					// Fill
					// permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2OneorZeroMap
					for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
							permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
									permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathway2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

					// TF CELLLINE ALLBASED
					// Fill
					// permutationNumberTfNameCellLineNameAllBasedKeggPathway2KMap
					// using
					// permutationNumberTfNameCellLineNameAllBasedKeggPathway2OneorZeroMap
					for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2ZeroorOneMap.iterator(); it.hasNext();){
						it.advance();

						permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

						if( !( permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
							permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
						}else{
							permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2KMap.put(
									permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
									permutationNumberTfNumberCellLineNumberAllBasedKeggPathway2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
						}

					}// End of for inner loop

				}

			}// End of for each input line

		}catch( NumberFormatException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		} // End of while

	}

	// TF and KeggPathway Enrichment or
	// TF and CellLine and KeggPathway Enrichment ends
	// with Numbers
	// @todo

	// TF and KeggPathway Enrichment or
	// TF and CellLine and KeggPathway Enrichment ends
	
	
	//9 July 2015
	// Enrichment
	// Without IO
	// With Numbers
	// Without ZScores (Without keeping number of overlaps coming from each permutation)
	// Empirical P Value Calculation
	// TF KEGGPathway Enrichment or
	// TF CellLine KEGGPathway Enrichment starts
	// Or BOTH
	// New Functionality START
	// Empirical P Value Calculation
	// Search tf and keggPathway
	// TF and Exon Based Kegg Pathway
	// TF and Regulation Based Kegg Pathway
	// TF and All Based Kegg Pathway
	public static void searchTfandKeggPathwayWithoutIOWithNumbersForAllChromosomes(
			int permutationNumber, 
			TIntObjectMap<List<InputLineMinimal>> chrNumber2RandomlyGeneratedData, 
			TIntObjectMap<IntervalTree> chrNumber2TFIntervalTreeMap,
			TIntObjectMap<IntervalTree> chrNumber2UcscRefSeqGenesIntervalTreeMap,
			TIntObjectMap<TIntList> geneId2KeggPathwayNumberMap,
			TIntIntMap tfNumberCellLineNumber2PermutationKMap,
			TIntIntMap exonBasedKEGGPathwayNumber2PermutationKMap,
			TIntIntMap regulationBasedKEGGPathwayNumber2PermutationKMap,
			TIntIntMap allBasedKEGGPathwayNumber2PermutationKMap,
			TIntIntMap tfNumberExonBasedKEGGPathwayNumber2PermutationKMap,
			TIntIntMap tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap,
			TIntIntMap tfNumberAllBasedKEGGPathwayNumber2PermutationKMap,
			TLongIntMap tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap,
			TLongIntMap tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap,
			TLongIntMap tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap,
			String type,
			AnnotationType annotationType, 
			int overlapDefinition) {

		int tfNumberCellLineNumber;
		int keggPathwayNumber;
		
		int tfNumberKEGGPathwayNumber;
		long tfNumberCellLineNumberKEGGPathwayNumber;

		ChromosomeName chromName;
		List<InputLineMinimal> inputLines;
		
		IntervalTree tfIntervalTree;
		IntervalTree ucscRefSeqGenesIntervalTree;
		
		for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

			chromName = GRCh37Hg19Chromosome.getChromosomeName(chrNumber);
			inputLines = chrNumber2RandomlyGeneratedData.get(chrNumber);
			
			tfIntervalTree = chrNumber2TFIntervalTreeMap.get(chrNumber);
			ucscRefSeqGenesIntervalTree = chrNumber2UcscRefSeqGenesIntervalTreeMap.get(chrNumber);

			if( inputLines != null){

				for( InputLineMinimal inputLine : inputLines){
			
					// Will be filled in tfIntervalTree search
					TIntByteMap tfNumberCellLineNumber2PermutationZeroorOneMap = new TIntByteHashMap();
			
					// Will be filled in ucscRefSeqGene search
					TIntByteMap exonBasedKEGGPathwayNumber2PermutationZeroorOneMap 			= new TIntByteHashMap();
					TIntByteMap regulationBasedKEGGPathwayNumber2PermutationZeroorOneMap 	= new TIntByteHashMap();
					TIntByteMap allBasedKEGGPathwayNumber2PermutationZeroorOneMap 			= new TIntByteHashMap();
			
					// Will be filled in common overlap check
					// Will be used for TF and KEGGPathway enrichment
					TIntByteMap tfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap 			= new TIntByteHashMap();
					TIntByteMap tfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap 	= new TIntByteHashMap();
					TIntByteMap tfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap 			= new TIntByteHashMap();
			
					// Will be filled in common overlap check
					// Will be used for TF and CellLine and KEGGPathway enrichment
					TLongByteMap tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap 		= new TLongByteHashMap();
					TLongByteMap tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap = new TLongByteHashMap();
					TLongByteMap tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap 		= new TLongByteHashMap();
			
					// Fill these lists during search for tfs and search for ucscRefSeqGenes
					List<TFNumberCellLineNumberOverlap> 	tfNumberCellLineNumberOverlapList 			= new ArrayList<TFNumberCellLineNumberOverlap>();
					List<UcscRefSeqGeneOverlapWithNumbers> 	exonBasedKEGGPathwayNumberOverlapList 		= new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();
					List<UcscRefSeqGeneOverlapWithNumbers> 	regulationBasedKEGGPathwayNumberOverlapList = new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();
					List<UcscRefSeqGeneOverlapWithNumbers> 	allBasedKEGGPathwayNumberOverlapList 		= new ArrayList<UcscRefSeqGeneOverlapWithNumbers>();
			
					// First TF
					// Fill tfNumberCellLineNumber2ZeroorOneMap
					if( tfIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						tfIntervalTree.findAllOverlappingTfbsIntervalsWithoutIOWithNumbers(
								tfIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								tfNumberCellLineNumber2PermutationZeroorOneMap,
								tfNumberCellLineNumberOverlapList, 
								overlapDefinition);
					}
			
					// accumulate search results coming from each input line
					// Accumulate tfNumberCellLineNumber2PermutationZeroorOneMap in tfNumberCellLineNumber2PermutationZeroorOneMap
					for( TIntByteIterator it = tfNumberCellLineNumber2PermutationZeroorOneMap.iterator(); it.hasNext();){
						it.advance();
			
						if( !( tfNumberCellLineNumber2PermutationKMap.containsKey( it.key()))){
							tfNumberCellLineNumber2PermutationKMap.put(it.key(), it.value());
						}else{
							tfNumberCellLineNumber2PermutationKMap.put(it.key(),tfNumberCellLineNumber2PermutationKMap.get(it.key()) + it.value());
						}
					}// End of FOR
			
					// Second UcscRefSeqGenes
					// Fill permutationNumberExonBasedKeggPathway2ZeroorOneMap
					// Fill permutationNumberRegulationBasedKeggPathway2ZeroorOneMap
					// Fill permutationNumberAllBasedKeggPathway2ZeroorOneMap
					if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){
						
						ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
								ucscRefSeqGenesIntervalTree.getRoot(), 
								inputLine, 
								chromName,
								geneId2KeggPathwayNumberMap, 
								exonBasedKEGGPathwayNumber2PermutationZeroorOneMap,
								regulationBasedKEGGPathwayNumber2PermutationZeroorOneMap,
								allBasedKEGGPathwayNumber2PermutationZeroorOneMap, 
								type,
								exonBasedKEGGPathwayNumberOverlapList,
								regulationBasedKEGGPathwayNumberOverlapList,
								allBasedKEGGPathwayNumberOverlapList, 
								overlapDefinition);
					}
			
					// accumulate search results of exonBasedKeggPathway2ZeroorOneMap in
					// permutationNumberExonBasedKeggPathway2KMap
					for( TIntByteIterator it = exonBasedKEGGPathwayNumber2PermutationZeroorOneMap.iterator(); it.hasNext();){
						it.advance();
			
						if( !(exonBasedKEGGPathwayNumber2PermutationKMap.containsKey( it.key()))){
							exonBasedKEGGPathwayNumber2PermutationKMap.put(it.key(), it.value());
						}else{
							exonBasedKEGGPathwayNumber2PermutationKMap.put(it.key(),exonBasedKEGGPathwayNumber2PermutationKMap.get( it.key()) + it.value());
						}
					}// End of for
			
					// accumulate search results of
					// regulationBasedKeggPathway2ZeroorOneMap in
					// permutationNumberRegulationBasedKeggPathway2KMap
					for( TIntByteIterator it = regulationBasedKEGGPathwayNumber2PermutationZeroorOneMap.iterator(); it.hasNext();){
						it.advance();
			
						if( !(regulationBasedKEGGPathwayNumber2PermutationKMap.containsKey( it.key()))){
							regulationBasedKEGGPathwayNumber2PermutationKMap.put( it.key(), it.value());
						}else{
							regulationBasedKEGGPathwayNumber2PermutationKMap.put( it.key(),regulationBasedKEGGPathwayNumber2PermutationKMap.get( it.key()) + it.value());
						}
					}// End of for
			
					// accumulate search results of allBasedKeggPathway2ZeroorOneMap in
					// permutationNumberAllBasedKeggPathway2KMap
					for( TIntByteIterator it = allBasedKEGGPathwayNumber2PermutationZeroorOneMap.iterator(); it.hasNext();){
						it.advance();
			
						if( !(allBasedKEGGPathwayNumber2PermutationKMap.containsKey( it.key()))){
							allBasedKEGGPathwayNumber2PermutationKMap.put( it.key(), it.value());
						}else{
							allBasedKEGGPathwayNumber2PermutationKMap.put( it.key(),allBasedKEGGPathwayNumber2PermutationKMap.get( it.key()) + it.value());
						}
					}// End of for
			
					// New search for given input SNP or interval case, does not matter.
					// For each TF overlap
					// For each UCSCRefSeqGene overlap
					// IF these overlaps overlaps
					// then write common overlap to output files
					// Fill TfNumberCellLineNumberExonBasedKeggPathwayNumber2OneorZeroMap
					// Fill permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2OneorZeroMap
					// Fill
					// permutationNumberTfNameCellLineNameAllBasedKeggPathway2OneorZeroMap
					// question will overlapDefition apply to here?
					for( TFNumberCellLineNumberOverlap tfNumberCellLineNumberOverlap : tfNumberCellLineNumberOverlapList){
			
						tfNumberCellLineNumber = tfNumberCellLineNumberOverlap.getTfNumberCellLineNumber();
						
						// TF and EXON Based Kegg Pathway starts
						for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneOverlapWithNumbers : exonBasedKEGGPathwayNumberOverlapList){
							
							if( IntervalTree.overlaps( 	tfNumberCellLineNumberOverlap.getLow(),
														tfNumberCellLineNumberOverlap.getHigh(),
														ucscRefSeqGeneOverlapWithNumbers.getLow(),
														ucscRefSeqGeneOverlapWithNumbers.getHigh())){
								
								for( TIntIterator it = ucscRefSeqGeneOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){
			
									keggPathwayNumber = it.next();
			
									// TF EXONKEGG
									if( annotationType.doTFKEGGPathwayAnnotation()){
										/***********************************/
										tfNumberKEGGPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										
										//debug starts
										if (tfNumberKEGGPathwayNumber == 9000322){
											System.out.println("There is a situation, stop here");
										}
										
										if (keggPathwayNumber > 269 || tfNumberKEGGPathwayNumber < 10000){
											System.out.println("There is a situation, stop here");
										}
										//debug ends
										
										if( !(tfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap.containsKey(tfNumberKEGGPathwayNumber))){
											tfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap.put(tfNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
									}
									
									// TF CELLLINE EXONKEGG
									else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){
										/***********************************/
										tfNumberCellLineNumberKEGGPathwayNumber = IntervalTree.addKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										if( !( tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap.containsKey(tfNumberCellLineNumberKEGGPathwayNumber))){
											tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap.put(tfNumberCellLineNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
									}
									
									// TF EXONKEGG and TF CELLLINE EXONKEGG
									else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){
			
										/***********************************/
										tfNumberKEGGPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										if( !( tfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap.containsKey( tfNumberKEGGPathwayNumber))){
											tfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap.put(tfNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
			
										/***********************************/
										tfNumberCellLineNumberKEGGPathwayNumber = IntervalTree.addKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										if( !( tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap.containsKey( tfNumberCellLineNumberKEGGPathwayNumber))){
											tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap.put(tfNumberCellLineNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
									}
			
								} // End of FOR each kegg pathways having this gene
								
							}// End of IF tfOverlap and ucscRefSeqGeneOverlap overlaps
							
						}// End of FOR each EXON BASED ucscRefSeqGeneOverlap for the given query
						// TF and EXON Based Kegg Pathway starts

			
						// TF and REGULATION Based Kegg Pathway
						for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneNumberOverlapWithNumbers : regulationBasedKEGGPathwayNumberOverlapList){
							
							if( IntervalTree.overlaps( 	tfNumberCellLineNumberOverlap.getLow(),
														tfNumberCellLineNumberOverlap.getHigh(),
														ucscRefSeqGeneNumberOverlapWithNumbers.getLow(),
														ucscRefSeqGeneNumberOverlapWithNumbers.getHigh())){
			
								for( TIntIterator it = ucscRefSeqGeneNumberOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){
			
									keggPathwayNumber = it.next();
			
									// TF REGULATIONKEGG
									if( annotationType.doTFKEGGPathwayAnnotation()){
										/***********************************/
										tfNumberKEGGPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										//debug starts
										if (tfNumberKEGGPathwayNumber == 9000322){
											System.out.println("There is a situation, stop here");
										}
										
										if (keggPathwayNumber > 269 || tfNumberKEGGPathwayNumber < 10000){
											System.out.println("There is a situation, stop here");
										}
										//debug ends
										
										if( !( tfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap.containsKey( tfNumberKEGGPathwayNumber))){
											tfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap.put(tfNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
									}
									
									// TF CELLLINE REGULATIONKEGG
									else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){
										/***********************************/
										tfNumberCellLineNumberKEGGPathwayNumber = IntervalTree.addKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										if( !( tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap.containsKey( tfNumberCellLineNumberKEGGPathwayNumber))){
											tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap.put(tfNumberCellLineNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
			
									}
									
									// TF REGULATIONKEGG AND TF CELLLINE REGULATIONKEGG
									else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){
			
										/***********************************/
										tfNumberKEGGPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										if( !( tfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap.containsKey( tfNumberKEGGPathwayNumber))){
											tfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap.put(tfNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
			
										/***********************************/
										tfNumberCellLineNumberKEGGPathwayNumber = IntervalTree.addKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										if( !( tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap.containsKey(tfNumberCellLineNumberKEGGPathwayNumber))){
											tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap.put(tfNumberCellLineNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
									}
			
								} // for each kegg pathways having this gene
							}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
						}// for each ucscRefSeqGeneOverlap for the given query
			
						// TF and ALL Based Kegg Pathway
						for( UcscRefSeqGeneOverlapWithNumbers ucscRefSeqGeneOverlapWithNumbers : allBasedKEGGPathwayNumberOverlapList){
							
							if( IntervalTree.overlaps(	tfNumberCellLineNumberOverlap.getLow(),
														tfNumberCellLineNumberOverlap.getHigh(),
														ucscRefSeqGeneOverlapWithNumbers.getLow(),
														ucscRefSeqGeneOverlapWithNumbers.getHigh())){
								
								for( TIntIterator it = ucscRefSeqGeneOverlapWithNumbers.getKeggPathwayNumberList().iterator(); it.hasNext();){
			
									keggPathwayNumber = it.next();
			
									// TF ALLKEGG
									if( annotationType.doTFKEGGPathwayAnnotation()){
										/***********************************/
										tfNumberKEGGPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										
										//debug starts
										if (tfNumberKEGGPathwayNumber == 9000322){
											System.out.println("There is a situation, stop here");
										}
										
										if (keggPathwayNumber > 269 || tfNumberKEGGPathwayNumber < 10000){
											System.out.println("There is a situation, stop here");
										}
										//debug ends
										
										if( !(tfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap.containsKey( tfNumberKEGGPathwayNumber))){
											tfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap.put(tfNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
			
									}
									// TF CELLLINE ALLKEGG
									else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){
										/***********************************/
										tfNumberCellLineNumberKEGGPathwayNumber = IntervalTree.addKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										if( !( tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap.containsKey( tfNumberCellLineNumberKEGGPathwayNumber))){
											tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap.put(tfNumberCellLineNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
			
									}
									// TF ALLKEGG AND TF CELLLINE ALLKEGG
									else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){
			
										/***********************************/
										tfNumberKEGGPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										if( !(tfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap.containsKey( tfNumberKEGGPathwayNumber))){
											tfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap.put(tfNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
			
										/***********************************/
										tfNumberCellLineNumberKEGGPathwayNumber = IntervalTree.addKeggPathwayNumber(
												tfNumberCellLineNumber,
												keggPathwayNumber,
												GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
			
										if( !( tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap.containsKey( tfNumberCellLineNumberKEGGPathwayNumber))){
											tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap.put(tfNumberCellLineNumberKEGGPathwayNumber, Commons.BYTE_1);
										}
										/***********************************/
			
									}
			
								} // for each kegg pathways having this gene
							}// End of IF tfOverlap and ucscRefSeqGeneOverlap overlaps
							
						}// for each ucscRefSeqGeneOverlap for the given query
						
					}// End of FOR each tfOverlap for the given query
			
					if( annotationType.doTFKEGGPathwayAnnotation()){
			
						// new code starts
						// TF EXON BASED
						// Fill permutationNumberTfExonBasedKeggPathway2KMap using
						// permutationNumberTfNameExonBasedKeggPathway2OneorZeroMap
			
						for( TIntByteIterator it = tfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberKEGGPathwayNumber = it.key();
			
							if( !( tfNumberExonBasedKEGGPathwayNumber2PermutationKMap.containsKey( tfNumberKEGGPathwayNumber))){
								tfNumberExonBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberExonBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber,tfNumberExonBasedKEGGPathwayNumber2PermutationKMap.get( tfNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop
			
						// TF REGULATION BASED
						// Fill permutationNumberTfRegulationBasedKeggPathway2KMap using
						// permutationNumberTfNameRegulationBasedKeggPathway2OneorZeroMap
						for( TIntByteIterator it = tfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberKEGGPathwayNumber = it.key();
			
							if( !( tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.containsKey( tfNumberKEGGPathwayNumber))){
								tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber,tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.get( tfNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop
			
						// TF ALL BASED
						// Fill permutationNumberTfAllBasedKeggPathway2KMap using
						// permutationNumberTfNameAllBasedKeggPathway2OneorZeroMap
						for( TIntByteIterator it = tfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberKEGGPathwayNumber = it.key();
			
							if( !( tfNumberAllBasedKEGGPathwayNumber2PermutationKMap.containsKey( tfNumberKEGGPathwayNumber))){
								tfNumberAllBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberAllBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber,tfNumberAllBasedKEGGPathwayNumber2PermutationKMap.get( tfNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop
			
						// new code ends
			
					}else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){
			
						// TF CELLLINE EXON BASED
						// Fill permutationNumberTfNameCellLineNameExonBasedKeggPathway2KMap
						// using permutationNumberTfNameCellLineNameExonBasedKeggPathway2OneorZeroMap
						for( TLongByteIterator it = tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberCellLineNumberKEGGPathwayNumber = it.key();
							
							if( !( tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap.containsKey(tfNumberCellLineNumberKEGGPathwayNumber))){
								tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber,tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap.get( tfNumberCellLineNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop
			
						// TF CELLLINE REGULATION BASED
						// Fill tfNameCellLineNameRegulationBasedKeggPathway2KMap
						// using permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2OneorZeroMap
						for( TLongByteIterator it = tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberCellLineNumberKEGGPathwayNumber = it.key();
			
							if( !(tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.containsKey(tfNumberCellLineNumberKEGGPathwayNumber))){
								tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber,tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.get(tfNumberCellLineNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop
			
						// TF CELLLINE ALL BASED
						// Fill
						// permutationNumberTfNameCellLineNameAllBasedKeggPathway2KMap
						// using
						// permutationNumberTfNameCellLineNameAllBasedKeggPathway2OneorZeroMap
						for( TLongByteIterator it = tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberCellLineNumberKEGGPathwayNumber = it.key();
			
							if( !(tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap.containsKey( tfNumberCellLineNumberKEGGPathwayNumber))){
								tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber,tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap.get(tfNumberCellLineNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop
			
					}else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){
						
						
						// TF EXON BASED
						for( TIntByteIterator it = tfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberKEGGPathwayNumber = it.key();
			
							if( !( tfNumberExonBasedKEGGPathwayNumber2PermutationKMap.containsKey( tfNumberKEGGPathwayNumber))){
								tfNumberExonBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberExonBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber,tfNumberExonBasedKEGGPathwayNumber2PermutationKMap.get( tfNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop
			
						// TF REGULATION BASED
						for( TIntByteIterator it = tfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberKEGGPathwayNumber = it.key();
			
							if( !( tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.containsKey(tfNumberKEGGPathwayNumber))){
								tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber,tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.get(tfNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop
			
						// TF ALL BASED
						for( TIntByteIterator it = tfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberKEGGPathwayNumber = it.key();
			
							if( !( tfNumberAllBasedKEGGPathwayNumber2PermutationKMap.containsKey(tfNumberKEGGPathwayNumber))){
								tfNumberAllBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberAllBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberKEGGPathwayNumber,tfNumberAllBasedKEGGPathwayNumber2PermutationKMap.get( tfNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop

						
						// TF CELLLINE EXON BASED
						for( TLongByteIterator it = tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberCellLineNumberKEGGPathwayNumber = it.key();
							
							if( !( tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap.containsKey(tfNumberCellLineNumberKEGGPathwayNumber))){
								tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber,tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap.get( tfNumberCellLineNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop
			
						// TF CELLLINE REGULATION BASED
						for( TLongByteIterator it = tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberCellLineNumberKEGGPathwayNumber = it.key();
			
							if( !(tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.containsKey(tfNumberCellLineNumberKEGGPathwayNumber))){
								tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber,tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap.get(tfNumberCellLineNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop
			
						// TF CELLLINE ALL BASED
						for( TLongByteIterator it = tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){
							it.advance();
			
							tfNumberCellLineNumberKEGGPathwayNumber = it.key();
			
							if( !(tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap.containsKey( tfNumberCellLineNumberKEGGPathwayNumber))){
								tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber, it.value());
							}else{
								tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap.put(tfNumberCellLineNumberKEGGPathwayNumber,tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap.get(tfNumberCellLineNumberKEGGPathwayNumber) + it.value());
							}
			
						}// End of for inner loop

					
					}//End of ELSE IF BOTH TFKEGGPathway and TFCellLineKEGGPathway
			
				}// End of for each input line
				
			} // End of IF inputLines is not null
			
		}// End of FOR each chromosome

	}
	//9 July 2015
	

	// Enrichment
	// Without IO
	// With Numbers
	// Empirical P Value Calculation
	// Tf and KeggPathway Enrichment or
	// Tf and CellLine and KeggPathway Enrichment starts
	// Or BOTH
	// New Functionality START
	// Empirical P Value Calculation
	// Search tf and keggPathway
	// TF and Exon Based Kegg Pathway
	// TF and Regulation Based Kegg Pathway
	// TF and All Based Kegg Pathway
	public static void searchTfandKeggPathwayWithoutIOWithNumbers(
			int permutationNumber, 
			ChromosomeName chromName,
			List<InputLineMinimal> inputLines, 
			IntervalTree tfIntervalTree, 
			IntervalTree ucscRefSeqGenesIntervalTree,
			TIntObjectMap<TIntList> geneId2KeggPathwayNumberMap,
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap,
			TIntIntMap permutationNumberExonBasedKeggPathwayNumber2KMap,
			TIntIntMap permutationNumberRegulationBasedKeggPathwayNumber2KMap,
			TIntIntMap permutationNumberAllBasedKeggPathwayNumber2KMap,
			TLongIntMap permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap,
			TLongIntMap permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap,
			TLongIntMap permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap,
			TLongIntMap permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap,
			TLongIntMap permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap,
			TLongIntMap permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap, 
			String type,
			AnnotationType annotationType, 
			int overlapDefinition) {

		long permutationNumberTfNumberCellLineNumber;
		long permutationNumberTfNumberKeggPathwayNumber;
		long permutationNumberTfNumberCellLineNumberKeggPathwayNumber;

		int keggPathwayNumber;

		for( InputLineMinimal inputLine : inputLines){

			// Will be filled in tfIntervalTree search
			TLongIntMap permutationNumberTfNumberCellLineNumber2ZeroorOneMap = new TLongIntHashMap();

			// Will be filled in ucscRefSeqGene search
			TIntIntMap permutationNumberExonBasedKeggPathwayNumber2ZeroorOneMap = new TIntIntHashMap();
			TIntIntMap permutationNumberRegulationBasedKeggPathwayNumber2ZeroorOneMap = new TIntIntHashMap();
			TIntIntMap permutationNumberAllBasedKeggPathwayNumber2ZeroorOneMap = new TIntIntHashMap();

			// Will be filled in common overlap check
			// Will be used for tf and kegg pathway enrichment
			TLongIntMap permutationNumberTfNumberExonBasedKeggPathwayNumber2OneorZeroMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberRegulationBasedKeggPathwayNumber2OneorZeroMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberAllBasedKeggPathwayNumber2OneorZeroMap = new TLongIntHashMap();

			// Will be filled in common overlap check
			// Will be used for tf and cell line and kegg pathway enrichment
			TLongIntMap permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2OneorZeroMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2OneorZeroMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2OneorZeroMap = new TLongIntHashMap();

			// Fill these lists during search for tfs and search for
			// ucscRefSeqGenes
			List<PermutationNumberTfNumberCellLineNumberOverlap> permutationNumberTfNumberCellLineNumberOverlapList = new ArrayList<PermutationNumberTfNumberCellLineNumberOverlap>();
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberExonBasedKeggPathwayNumberOverlapList = new ArrayList<PermutationNumberUcscRefSeqGeneNumberOverlap>();
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberRegulationBasedKeggPathwayNumberOverlapList = new ArrayList<PermutationNumberUcscRefSeqGeneNumberOverlap>();
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberAllBasedKeggPathwayNumberOverlapList = new ArrayList<PermutationNumberUcscRefSeqGeneNumberOverlap>();

			// First TF
			// Fill permutationNumberTfNumberCellLineNumber2ZeroorOneMap
			if( tfIntervalTree.getRoot().getNodeName().isNotSentinel()){
				tfIntervalTree.findAllOverlappingTfbsIntervalsWithoutIOWithNumbers( permutationNumber,
						tfIntervalTree.getRoot(), inputLine, chromName,
						permutationNumberTfNumberCellLineNumber2ZeroorOneMap,
						permutationNumberTfNumberCellLineNumberOverlapList, overlapDefinition);
			}

			// accumulate search results of
			// permutationNumberTfNumberCellLineNumber2ZeroorOneMap in
			// permutationNumberTfNameaCellLineName2KMap
			for( TLongIntIterator it = permutationNumberTfNumberCellLineNumber2ZeroorOneMap.iterator(); it.hasNext();){
				it.advance();

				if( !( permutationNumberTfNumberCellLineNumber2KMap.containsKey( it.key()))){
					permutationNumberTfNumberCellLineNumber2KMap.put( it.key(), it.value());
				}else{
					permutationNumberTfNumberCellLineNumber2KMap.put( it.key(),
							permutationNumberTfNumberCellLineNumber2KMap.get( it.key()) + it.value());
				}
			}// End of for

			// Second UcscRefSeqGenes
			// Fill permutationNumberExonBasedKeggPathway2ZeroorOneMap
			// Fill permutationNumberRegulationBasedKeggPathway2ZeroorOneMap
			// Fill permutationNumberAllBasedKeggPathway2ZeroorOneMap
			if( ucscRefSeqGenesIntervalTree.getRoot().getNodeName().isNotSentinel()){
				
				ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
						permutationNumber, ucscRefSeqGenesIntervalTree.getRoot(), inputLine, chromName,
						geneId2KeggPathwayNumberMap, permutationNumberExonBasedKeggPathwayNumber2ZeroorOneMap,
						permutationNumberRegulationBasedKeggPathwayNumber2ZeroorOneMap,
						permutationNumberAllBasedKeggPathwayNumber2ZeroorOneMap, type,
						permutationNumberExonBasedKeggPathwayNumberOverlapList,
						permutationNumberRegulationBasedKeggPathwayNumberOverlapList,
						permutationNumberAllBasedKeggPathwayNumberOverlapList, overlapDefinition);
			}

			// accumulate search results of exonBasedKeggPathway2ZeroorOneMap in
			// permutationNumberExonBasedKeggPathway2KMap
			for( TIntIntIterator it = permutationNumberExonBasedKeggPathwayNumber2ZeroorOneMap.iterator(); it.hasNext();){
				it.advance();

				if( !( permutationNumberExonBasedKeggPathwayNumber2KMap.containsKey( it.key()))){
					permutationNumberExonBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
				}else{
					permutationNumberExonBasedKeggPathwayNumber2KMap.put( it.key(),
							permutationNumberExonBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());
				}
			}// End of for

			// accumulate search results of
			// regulationBasedKeggPathway2ZeroorOneMap in
			// permutationNumberRegulationBasedKeggPathway2KMap
			for( TIntIntIterator it = permutationNumberRegulationBasedKeggPathwayNumber2ZeroorOneMap.iterator(); it.hasNext();){
				it.advance();

				if( !( permutationNumberRegulationBasedKeggPathwayNumber2KMap.containsKey( it.key()))){
					permutationNumberRegulationBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
				}else{
					permutationNumberRegulationBasedKeggPathwayNumber2KMap.put( it.key(),
							permutationNumberRegulationBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());
				}
			}// End of for

			// accumulate search results of allBasedKeggPathway2ZeroorOneMap in
			// permutationNumberAllBasedKeggPathway2KMap
			for( TIntIntIterator it = permutationNumberAllBasedKeggPathwayNumber2ZeroorOneMap.iterator(); it.hasNext();){
				it.advance();

				if( !( permutationNumberAllBasedKeggPathwayNumber2KMap.containsKey( it.key()))){
					permutationNumberAllBasedKeggPathwayNumber2KMap.put( it.key(), it.value());
				}else{
					permutationNumberAllBasedKeggPathwayNumber2KMap.put( it.key(),
							permutationNumberAllBasedKeggPathwayNumber2KMap.get( it.key()) + it.value());
				}
			}// End of for

			// New search for given input SNP or interval case, does not matter.
			// for each tf overlap
			// for each ucscRefSeqGene overlap
			// if these overlaps overlaps
			// then write common overlap to output files
			// Fill
			// permutationNumberTfNameCellLineNameExonBasedKeggPathway2OneorZeroMap
			// Fill
			// permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2OneorZeroMap
			// Fill
			// permutationNumberTfNameCellLineNameAllBasedKeggPathway2OneorZeroMap
			// question will overlapDefition apply to here?
			for( PermutationNumberTfNumberCellLineNumberOverlap permutationNumberTfNumberCellLineNumberOverlap : permutationNumberTfNumberCellLineNumberOverlapList){

				permutationNumberTfNumberCellLineNumber = permutationNumberTfNumberCellLineNumberOverlap.getPermutationNumberTfNumberCellLineNumber();

				// TF and EXON Based Kegg Pathway
				for( PermutationNumberUcscRefSeqGeneNumberOverlap permutationNumberUcscRefSeqGeneNumberOverlap : permutationNumberExonBasedKeggPathwayNumberOverlapList){
					if( IntervalTree.overlaps( permutationNumberTfNumberCellLineNumberOverlap.getLow(),
							permutationNumberTfNumberCellLineNumberOverlap.getHigh(),
							permutationNumberUcscRefSeqGeneNumberOverlap.getLow(),
							permutationNumberUcscRefSeqGeneNumberOverlap.getHigh())){
						for( TIntIterator it = permutationNumberUcscRefSeqGeneNumberOverlap.getKeggPathwayNumberList().iterator(); it.hasNext();){

							keggPathwayNumber = it.next();

							// TF EXONKEGG
							if( annotationType.doTFKEGGPathwayAnnotation()){
								/***********************************/
								permutationNumberTfNumberKeggPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberExonBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
									permutationNumberTfNumberExonBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberKeggPathwayNumber, 1);
								}
								/***********************************/
							}
							// TF CELLLINE EXONKEGG
							else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){
								/***********************************/
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
									permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
								}
								/***********************************/
							}
							// TF EXONKEGG and TF CELLLINE EXONKEGG
							else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

								/***********************************/
								permutationNumberTfNumberKeggPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberExonBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
									permutationNumberTfNumberExonBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberKeggPathwayNumber, 1);
								}
								/***********************************/

								/***********************************/
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
									permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
								}
								/***********************************/
							}

						} // for each kegg pathways having this gene
					}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
				}// for each ucscRefSeqGeneOverlap for the given query

				// TF and REGULATION Based Kegg Pathway
				for( PermutationNumberUcscRefSeqGeneNumberOverlap permutationNumberUcscRefSeqGeneNumberOverlap : permutationNumberRegulationBasedKeggPathwayNumberOverlapList){
					if( IntervalTree.overlaps( permutationNumberTfNumberCellLineNumberOverlap.getLow(),
							permutationNumberTfNumberCellLineNumberOverlap.getHigh(),
							permutationNumberUcscRefSeqGeneNumberOverlap.getLow(),
							permutationNumberUcscRefSeqGeneNumberOverlap.getHigh())){

						for( TIntIterator it = permutationNumberUcscRefSeqGeneNumberOverlap.getKeggPathwayNumberList().iterator(); it.hasNext();){

							keggPathwayNumber = it.next();

							// TF REGULATIONKEGG
							if( annotationType.doTFKEGGPathwayAnnotation()){
								/***********************************/
								permutationNumberTfNumberKeggPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
									permutationNumberTfNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberKeggPathwayNumber, 1);
								}
								/***********************************/

							}
							// TF CELLLINE REGULATIONKEGG
							else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){
								/***********************************/
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
									permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
								}
								/***********************************/

							}
							// TF REGULATIONKEGG AND TF CELLLINE REGULATIONKEGG
							else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

								/***********************************/
								permutationNumberTfNumberKeggPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
									permutationNumberTfNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberKeggPathwayNumber, 1);
								}
								/***********************************/

								/***********************************/
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
									permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
								}
								/***********************************/
							}

						} // for each kegg pathways having this gene
					}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
				}// for each ucscRefSeqGeneOverlap for the given query

				// TF and ALL Based Kegg Pathway
				for( PermutationNumberUcscRefSeqGeneNumberOverlap permutationNumberUcscRefSeqGeneNumberOverlap : permutationNumberAllBasedKeggPathwayNumberOverlapList){
					if( IntervalTree.overlaps( permutationNumberTfNumberCellLineNumberOverlap.getLow(),
							permutationNumberTfNumberCellLineNumberOverlap.getHigh(),
							permutationNumberUcscRefSeqGeneNumberOverlap.getLow(),
							permutationNumberUcscRefSeqGeneNumberOverlap.getHigh())){
						for( TIntIterator it = permutationNumberUcscRefSeqGeneNumberOverlap.getKeggPathwayNumberList().iterator(); it.hasNext();){

							keggPathwayNumber = it.next();

							// TF ALLKEGG
							if( annotationType.doTFKEGGPathwayAnnotation()){
								/***********************************/
								permutationNumberTfNumberKeggPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberAllBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
									permutationNumberTfNumberAllBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberKeggPathwayNumber, 1);
								}
								/***********************************/

							}
							// TF CELLLINE ALLKEGG
							else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){
								/***********************************/
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
									permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
								}
								/***********************************/

							}
							// TF ALLKEGG AND TF CELLLINE ALLKEGG
							else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

								/***********************************/
								permutationNumberTfNumberKeggPathwayNumber = IntervalTree.removeCellLineNumberAddKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberAllBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
									permutationNumberTfNumberAllBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberKeggPathwayNumber, 1);
								}
								/***********************************/

								/***********************************/
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber = IntervalTree.addKeggPathwayNumber(
										permutationNumberTfNumberCellLineNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								if( !( permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
									permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberTfNumberCellLineNumberKeggPathwayNumber, 1);
								}
								/***********************************/
							}

						} // for each kegg pathways having this gene
					}// if tfOverlap and ucscRefSeqGeneOverlap overlaps
				}// for each ucscRefSeqGeneOverlap for the given query
			}// for each tfOverlap for the given query

			if( annotationType.doTFKEGGPathwayAnnotation()){

				// new code starts
				// TF EXON BASED
				// Fill permutationNumberTfExonBasedKeggPathway2KMap using
				// permutationNumberTfNameExonBasedKeggPathway2OneorZeroMap

				for( TLongIntIterator it = permutationNumberTfNumberExonBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
						permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber,
								permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF REGULATION BASED
				// Fill permutationNumberTfRegulationBasedKeggPathway2KMap using
				// permutationNumberTfNameRegulationBasedKeggPathway2OneorZeroMap
				for( TLongIntIterator it = permutationNumberTfNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
						permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber,
								permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF ALL BASED
				// Fill permutationNumberTfAllBasedKeggPathway2KMap using
				// permutationNumberTfNameAllBasedKeggPathway2OneorZeroMap
				for( TLongIntIterator it = permutationNumberTfNumberAllBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
						permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber,
								permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// new code ends

			}else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){

				// TF CELLLINE EXON BASED
				// Fill
				// permutationNumberTfNameCellLineNameExonBasedKeggPathway2KMap
				// using
				// permutationNumberTfNameCellLineNameExonBasedKeggPathway2OneorZeroMap
				for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
						permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
								permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF CELLLINE REGULATION BASED
				// Fill
				// permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2KMap
				// using
				// permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2OneorZeroMap
				for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
						permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
								permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF CELLLINE ALL BASED
				// Fill
				// permutationNumberTfNameCellLineNameAllBasedKeggPathway2KMap
				// using
				// permutationNumberTfNameCellLineNameAllBasedKeggPathway2OneorZeroMap
				for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
						permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
								permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

			}else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

				// TF EXON BASED
				// Fill permutationNumberTfExonBasedKeggPathway2KMap using
				// permutationNumberTfNameExonBasedKeggPathway2OneorZeroMap
				for( TLongIntIterator it = permutationNumberTfNumberExonBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
						permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber,
								permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF REGULATION BASED
				// Fill permutationNumberTfRegulationBasedKeggPathway2KMap using
				// permutationNumberTfNameRegulationBasedKeggPathway2OneorZeroMap
				for( TLongIntIterator it = permutationNumberTfNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
						permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber,
								permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF ALL BASED
				// Fill permutationNumberTfAllBasedKeggPathway2KMap using
				// permutationNumberTfNameAllBasedKeggPathway2OneorZeroMap
				for( TLongIntIterator it = permutationNumberTfNumberAllBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberKeggPathwayNumber))){
						permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberKeggPathwayNumber,
								permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF CELLLINE EXON BASED
				// Fill
				// permutationNumberTfNameCellLineNameExonBasedKeggPathway2KMap
				// using
				// permutationNumberTfNameCellLineNameExonBasedKeggPathway2OneorZeroMap
				for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
						permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
								permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF CELLLINE REGULATION BASED
				// Fill
				// permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2KMap
				// using
				// permutationNumberTfNameCellLineNameRegulationBasedKeggPathway2OneorZeroMap
				for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
						permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
								permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

				// TF CELLLINE ALL BASED
				// Fill
				// permutationNumberTfNameCellLineNameAllBasedKeggPathway2KMap
				// using
				// permutationNumberTfNameCellLineNameAllBasedKeggPathway2OneorZeroMap
				for( TLongIntIterator it = permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2OneorZeroMap.iterator(); it.hasNext();){
					it.advance();

					permutationNumberTfNumberCellLineNumberKeggPathwayNumber = it.key();

					if( !( permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.containsKey( permutationNumberTfNumberCellLineNumberKeggPathwayNumber))){
						permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber, it.value());
					}else{
						permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.put(
								permutationNumberTfNumberCellLineNumberKeggPathwayNumber,
								permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap.get( permutationNumberTfNumberCellLineNumberKeggPathwayNumber) + it.value());
					}

				}// End of for inner loop

			}

		}// End of for each input line

	}

	// New Functionality END
	// Tf and KeggPathway Enrichment or
	// Tf and CellLine and KeggPathway Enrichment ends
	// with Numbers End
	// @todo

	// yeni starts
	// DNASE Annotation
	// KEGG Pathway Annotation
	public void writeResultsWithNumbers( int[] kArray, char[][] names, String outputFolder, String outputFileName) {

		BufferedWriter bufferedWriter;
		char[] elementName;

		List<Element> elementList = null;

		Element element = null;
		int elementNumber;
		int numberofOverlaps;

		elementList = transformMapToCollection( kArray);

		Collections.sort( elementList, Element.NUMBER_OF_OVERLAPS);

		try{

			bufferedWriter = new BufferedWriter( FileOperations.createFileWriter( outputFolder + outputFileName));

			// header line
			bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "ElementName" + "\t" + "Number of Overlaps: k out of n given intervals overlaps with the intervals of element" + System.getProperty( "line.separator"));

			// Write sorted elementList
			for( Iterator<Element> it = elementList.iterator(); it.hasNext();){

				element = it.next();

				elementNumber = element.getElementIntNumber();

				elementName = names[elementNumber];

				numberofOverlaps = element.getElementNumberofOverlaps();

				bufferedWriter.write( Arrays.toString( elementName) + "\t" + numberofOverlaps + System.getProperty( "line.separator"));

			}// End of For

			// Close BufferedWriter
			bufferedWriter.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	// yeni ends

	// DNASE Annotation
	// KEGG Pathway Annotation
	public void writeResultsWithNumbers(
			TShortIntMap number2KMap, 
			TShortObjectMap<String> number2NameMap,
			String outputFolder, 
			String outputFileName) {

		BufferedWriter bufferedWriter;
		String elementName;

		List<Element> elementList = null;

		Element element = null;
		short elementNumber;
		int numberofOverlaps;

		elementList = transformMapToCollection( number2KMap);

		Collections.sort( elementList, Element.NUMBER_OF_OVERLAPS);

		try{

			bufferedWriter = new BufferedWriter( FileOperations.createFileWriter( outputFolder + outputFileName));

			// Header Line
			bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "ElementNumber" + "\t" + "ElementName" + "\t" + "NumberofOverlaps: k out of n given intervals overlaps with the intervals of element" + System.getProperty( "line.separator"));

			// Write sorted elementList
			for( Iterator<Element> it = elementList.iterator(); it.hasNext();){

				element = it.next();

				elementNumber = element.getElementShortNumber();

				elementName = number2NameMap.get( elementNumber);

				numberofOverlaps = element.getElementNumberofOverlaps();

				bufferedWriter.write( elementNumber + "\t" + elementName + "\t" + numberofOverlaps + System.getProperty( "line.separator"));

			}// End of FOR

			// // accessing keys/values through an iterator:
			// for (TShortIntIterator it = number2KMap.iterator(); it.hasNext();) {
			// it.advance();
			// elementName = number2NameMap.get(it.key());
			// bufferedWriter.write(elementName + "\t" + it.value() + System.getProperty("line.separator"));
			// }

			// Close BufferedWriter
			bufferedWriter.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	public List<Element> transformMapToCollection( int[] kArray) {

		int key;
		int value;

		List<Element> elementList = new ArrayList<Element>();
		Element element = null;

		for( int i = 0; i < kArray.length; i++){
			key = i;
			value = kArray[i];

			element = new Element( key, value);
			elementList.add( element);
		}

		return elementList;
	}

	//13 July 2015
	public List<Element> transformMapToCollection( TLongIntMap number2KMap) {

		long key;
		int value;

		List<Element> elementList = new ArrayList<Element>();
		Element element = null;

		for( TLongIntIterator it = number2KMap.iterator(); it.hasNext();){
			it.advance();

			key = it.key();
			value = it.value();

			element = new Element( key, value);
			elementList.add( element);

		}// End of for

		return elementList;
	}
	
	
	public List<Element> transformMapToCollection( TIntIntMap number2KMap) {

		int key;
		int value;

		List<Element> elementList = new ArrayList<Element>();
		Element element = null;

		for( TIntIntIterator it = number2KMap.iterator(); it.hasNext();){
			it.advance();

			key = it.key();
			value = it.value();

			element = new Element( key, value);
			elementList.add( element);

		}// End of for

		return elementList;
	}

	public List<Element> transformMapToCollection( TShortIntMap number2KMap) {

		short key;
		int value;

		List<Element> elementList = new ArrayList<Element>();
		Element element = null;

		for( TShortIntIterator it = number2KMap.iterator(); it.hasNext();){

			it.advance();

			key = it.key();
			value = it.value();

			element = new Element( key, value);
			elementList.add( element);

		}// End of for

		return elementList;
	}

	// KEGGPathway Annotation (GeneSet Annotation)
	// UserDefined GeneSet Annotation
	// Gene Annotation
	// UserDefinedLibrary Annotation
	public void writeResultsWithNumbers( 
			TIntIntMap number2KMap, 
			TIntObjectMap<String> number2NameMap,
			String outputFolder, 
			String outputFileName) {

		BufferedWriter bufferedWriter;
		String elementName;

		List<Element> elementList = null;

		Element element = null;
		int elementNumber;
		int numberofOverlaps;

		elementList = transformMapToCollection( number2KMap);

		Collections.sort( elementList, Element.NUMBER_OF_OVERLAPS);

		try{

			bufferedWriter = new BufferedWriter( FileOperations.createFileWriter( outputFolder + outputFileName));

			// header line
			bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "ElementNumber" + "\t" + "Element Name" + "\t" + "Number of Overlaps: k out of n given intervals overlaps with the intervals of element" + System.getProperty( "line.separator"));

			// Write sorted elementList
			for( Iterator<Element> it = elementList.iterator(); it.hasNext();){

				element = it.next();

				elementNumber = element.getElementIntNumber();

				elementName = number2NameMap.get(elementNumber);
				numberofOverlaps = element.getElementNumberofOverlaps();

				bufferedWriter.write(elementNumber + "\t" + elementName + "\t" + numberofOverlaps + System.getProperty( "line.separator"));

			}// End of FOR

	
			// Close BufferedWriter
			bufferedWriter.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	// TF CellLine KEGGPathway Annotation
	public void writeResultsWithNumbers( 
			TLongIntMap elementNumberCellLineNumberKeggNumber2KMap,
			TIntObjectMap<String> elementNumber2ElementNameMap,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String> keggPathwayNumber2KeggPathwayNameMap, 
			String outputFolder, String outputFileName) {

		BufferedWriter bufferedWriter;

		long elementNumberCellLineNumberKeggPathwayNumber;
		int elementNumber;
		int cellLineNumber;
		int keggPathwayNumber;

		String elementName;
		String cellLineName;
		String keggPathwayName;

		List<Element> elementList = null;
		elementList = transformMapToCollection( elementNumberCellLineNumberKeggNumber2KMap);

		Element element = null;
		int numberofOverlaps;

		Collections.sort( elementList, Element.NUMBER_OF_OVERLAPS);

		try{
			bufferedWriter = new BufferedWriter( FileOperations.createFileWriter( outputFolder + outputFileName));

			// header line
			bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "ElementNumberCellLineNumberKEGGPathwayNumber" + "\t" + "ElementName" + "_" + "CellLineName" + "_" + "KeggPathwayName" + "\t" + "Number of Overlaps: k out of n given intervals overlaps with the intervals of element" + System.getProperty( "line.separator"));

			// Write sorted elementList
			for( Iterator<Element> it = elementList.iterator(); it.hasNext();){

				element = it.next();

				elementNumberCellLineNumberKeggPathwayNumber = element.getElementLongNumber();

				elementNumber = IntervalTree.getElementNumber(
						elementNumberCellLineNumberKeggPathwayNumber,
						GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
				elementName = elementNumber2ElementNameMap.get(elementNumber);

				cellLineNumber = IntervalTree.getCellLineNumber(
						elementNumberCellLineNumberKeggPathwayNumber,
						GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
				cellLineName = cellLineNumber2CellLineNameMap.get(cellLineNumber);

				keggPathwayNumber = IntervalTree.getKeggPathwayNumber(
						elementNumberCellLineNumberKeggPathwayNumber,
						GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
				keggPathwayName = keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber);

				numberofOverlaps = element.getElementNumberofOverlaps();

				bufferedWriter.write( elementNumberCellLineNumberKeggPathwayNumber + "\t" + elementName + "_" + cellLineName + "_" + keggPathwayName + "\t" + numberofOverlaps + System.getProperty( "line.separator"));

			}// End of For

			// // accessing keys/values through an iterator:
			// for (TIntIntIterator it = elementNumberCellLineNumberKeggNumber2KMap.iterator(); it.hasNext();) {
			// it.advance();
			//
			// elementNumberCellLineNumberKeggPathwayNumber = it.key();
			//
			// elementNumber = IntervalTree.getElementNumber(elementNumberCellLineNumberKeggPathwayNumber);
			// elementName = elementNumber2ElementNameMap.get(elementNumber);
			//
			// cellLineNumber = IntervalTree.getCellLineNumber(elementNumberCellLineNumberKeggPathwayNumber);
			// cellLineName = cellLineNumber2CellLineNameMap.get(cellLineNumber);
			//
			// keggPathwayNumber = IntervalTree.getKeggPathwayNumber(elementNumberCellLineNumberKeggPathwayNumber);
			// keggPathwayName = keggPathwayNumber2KeggPathwayNameMap.get(keggPathwayNumber);
			//
			// bufferedWriter.write(elementName + "_" + cellLineName + "_" + keggPathwayName + "\t" + it.value() +
			// System.getProperty("line.separator"));
			// }

			// Close bufferedWriter
			bufferedWriter.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
	}

	// @todo TF CellLine KEGGPathway ends

	// TF KEGG Pathway Annotation
	public void writeTFKEGGPathwayResultsWithNumbers( 
			TIntIntMap elementNumberKEGGPathwayNumber2KMap,
			TIntObjectMap<String> elementNumber2ElementNameMap,
			TIntObjectMap<String> KEGGPathwayNumber2KEGGPathwayNameMap, 
			String outputFolder, 
			String outputFileName) {

		BufferedWriter bufferedWriter;

		int elementNumberKEGGPathwayNumber;
		int elementNumber;
		int keggPathwayNumber;

		String elementName;
		String keggPathwayName;

		List<Element> elementList = null;
		elementList = transformMapToCollection(elementNumberKEGGPathwayNumber2KMap);

		Collections.sort( elementList, Element.NUMBER_OF_OVERLAPS);

		Element element = null;
		int numberofOverlaps;

		try{

			bufferedWriter = new BufferedWriter( FileOperations.createFileWriter( outputFolder + outputFileName));

			// header line
			bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "ElementNumberCellLineNumberKEGGPathwayNumber" + "\t" + "ElementName_KEGGPathwayName" + "\t" + "Number of Overlaps: k out of n given intervals overlaps with the intervals of element" + System.getProperty( "line.separator"));

			// Write sorted elementList
			for( Iterator<Element> it = elementList.iterator(); it.hasNext();){

				element = it.next();

				elementNumberKEGGPathwayNumber = element.getElementIntNumber();

				elementNumber = IntervalTree.getElementNumber(
						elementNumberKEGGPathwayNumber,
						GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER);
				
				elementName = elementNumber2ElementNameMap.get( elementNumber);
				
				keggPathwayNumber = IntervalTree.getCellLineNumberOrGeneSetNumber(
						elementNumberKEGGPathwayNumber,
						GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER);
				
				keggPathwayName = KEGGPathwayNumber2KEGGPathwayNameMap.get( keggPathwayNumber);

				numberofOverlaps = element.getElementNumberofOverlaps();

				bufferedWriter.write( elementNumberKEGGPathwayNumber + "\t" + elementName + "_" + keggPathwayName + "\t" + numberofOverlaps + System.getProperty( "line.separator"));

			}// End of For

			// // accessing keys/values through an iterator:
			// for (TIntIntIterator it = elementNumberCellLineNumber2KMap.iterator(); it.hasNext();) {
			// it.advance();
			//
			// elementNumberCellLineNumber = it.key();
			//
			// elementNumber = IntervalTree.getElementNumber(elementNumberCellLineNumber);
			// elementName = elementNumber2ElementNameMap.get(elementNumber);
			//
			// keggPathwayNumber = IntervalTree.getKeggPathwayNumber(elementNumberCellLineNumber);
			// keggPathwayName = KEGGPathwayNumber2KEGGPathwayNameMap.get(keggPathwayNumber);
			//
			// bufferedWriter.write(elementName + "_" + keggPathwayName + "\t" + it.value() +
			// System.getProperty("line.separator"));
			// }

			// Close BufferedWriter
			bufferedWriter.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
	}

	// @todo ends

	// UserDefinedLibrary
	public void writeResultsWithNumbers( TIntObjectMap<String> userDefinedLibraryElementTypeNumber2ElementTypeMap,
			TIntObjectMap<TIntIntMap> elementTypeNumber2ElementNumber2KMapMap,
			TIntObjectMap<TIntObjectMap<String>> elementTypeNumber2ElementNumber2ElementNameMapMap,
			String outputFolder, String directoryName, String fileName) {

		String elementType;
		int elementTypeNumber;

		TIntIntMap elementNumber2KMap;
		TIntObjectMap<String> elementNumber2ElementNameMap;

		// For each elementTypeNumber
		for( TIntObjectIterator<String> it = userDefinedLibraryElementTypeNumber2ElementTypeMap.iterator(); it.hasNext();){
			
			it.advance();

			elementTypeNumber = it.key();
			elementType = it.value();

			// Write these results
			elementNumber2KMap = elementTypeNumber2ElementNumber2KMapMap.get( elementTypeNumber);
			elementNumber2ElementNameMap = elementTypeNumber2ElementNumber2ElementNameMapMap.get( elementTypeNumber);

			writeResultsWithNumbers(
					elementNumber2KMap, 
					elementNumber2ElementNameMap, 
					outputFolder + directoryName,
					elementType + fileName);

		}// End of for each elementTypeNumber

	}

	// ends

	// HISTONE Annotation
	// TF Annotation
	public void writeResultsWithNumbers( 
			TIntIntMap elementNumberCellLineNumber2KMap,
			TIntObjectMap<String> elementNumber2ElementNameMap,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap, 
			String outputFolder, 
			String outputFileName) {

		BufferedWriter bufferedWriter;

		int elementNumberCellLineNumber;
		int elementNumber;
		int cellLineNumber;

		String elementName;
		String cellLineName;

		List<Element> elementList = null;
		elementList = transformMapToCollection( elementNumberCellLineNumber2KMap);

		Collections.sort( elementList, Element.NUMBER_OF_OVERLAPS);

		Element element = null;
		int numberofOverlaps;

		try{
			bufferedWriter = new BufferedWriter( FileOperations.createFileWriter( outputFolder + outputFileName));

			// header line
			bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "ElementNumber_CellLineNumber" + "\t" + "ElementName_CellLineName" + "\t" + "Number of Overlaps: k out of n given intervals overlaps with the intervals of element" + System.getProperty( "line.separator"));

			// Write sorted elementList
			for( Iterator<Element> it = elementList.iterator(); it.hasNext();){

				element = it.next();

				elementNumberCellLineNumber = element.getElementIntNumber();
				
				elementNumber = IntervalTree.getElementNumber(
						elementNumberCellLineNumber,
						GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);
				
				elementName = elementNumber2ElementNameMap.get( elementNumber);

				cellLineNumber = IntervalTree.getCellLineNumberOrGeneSetNumber(
						elementNumberCellLineNumber,
						GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);
				
				cellLineName = cellLineNumber2CellLineNameMap.get( cellLineNumber);

				numberofOverlaps = element.getElementNumberofOverlaps();

				bufferedWriter.write( elementNumberCellLineNumber + "\t" + elementName + "_" + cellLineName + "\t" + numberofOverlaps + System.getProperty( "line.separator"));

			}// End of For

			// // accessing keys/values through an iterator:
			// for (TIntIntIterator it = elementNumberCellLineNumber2KMap.iterator(); it.hasNext();) {
			// it.advance();
			//
			// elementNumberCellLineNumber = it.key();
			//
			// elementNumber = IntervalTree.getElementNumber(elementNumberCellLineNumber);
			// elementName = elementNumber2ElementNameMap.get(elementNumber);
			//
			// cellLineNumber = IntervalTree.getCellLineNumber(elementNumberCellLineNumber);
			// cellLineName = cellLineNumber2CellLineNameMap.get(cellLineNumber);
			//
			// bufferedWriter.write(elementName + "_" + cellLineName + "\t" + it.value() +
			// System.getProperty("line.separator"));
			// }

			// Close bufferedWriter
			bufferedWriter.close();

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
	}

	// @todo ends

	public void closeBufferedWriterList( TIntObjectMap<FileWriter> chrNumber2FileWriterMap,
			TIntObjectMap<BufferedWriter> chrNumber2BufferedWriterMap) {

		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;

		try{

			for( ChromosomeName chrName : ChromosomeName.values()){

				bufferedWriter = chrNumber2BufferedWriterMap.get( chrName.getChromosomeName());
				fileWriter = chrNumber2FileWriterMap.get( chrName.getChromosomeName());

				// Close BufferedWriter and FileWriter
				bufferedWriter.close();
				fileWriter.close();

			}// End of for

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
	}

	// Annotation In Paralel
	// with Numbers
	public int[] searchDnaseWithNumbers( String dataFolder, String outputFolder, ChromosomeName chromName,
			List<Interval> dataArrayList, int overlapDefinition, IntervalTree dnaseIntervalTree,
			int numberofComparisonsDnase, char[][] dnaseCellLineNames, char[][] fileNames,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode, ForkJoinPool pool) {

		FindOverlaps findOverlaps;
		int[] kArray = new int[numberofComparisonsDnase];

		findOverlaps = new FindOverlaps( outputFolder, chromName, dataArrayList,
				writeElementBasedAnnotationFoundOverlapsMode, Commons.ZERO, dataArrayList.size(), overlapDefinition,
				dnaseIntervalTree, numberofComparisonsDnase, dnaseCellLineNames, fileNames);

		kArray = pool.invoke( findOverlaps);

		// if( GlanetRunner.shouldLog())logger.info(chromName.convertEnumtoString() + "\t" +
		// "dnaseCellLineNumber2KMap.size() after FindOverlaps call" + "\t" + dnaseCellLineNumber2KMap.size() );

		System.gc();
		System.runFinalization();

		return kArray;

	}

	// For Chen Yao Circulation Paper
	// Hg19 RefSeq Gene
	// Annotation
	// with numbers
	public void searchGeneWithNumbers( String dataFolder, String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			TIntObjectMap<String> givenIntervalNumber2GivenIntervalNameMap,
			TIntObjectMap<OverlapInformation> givenIntervalNumber2OverlapInformationMap,
			TObjectIntMap<ChromosomeName> chromosomeName2CountMap, TIntIntMap entrezGeneId2KMap, int overlapDefinition,
			TIntObjectMap<String> officialGeneSymbolNumber2NameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap) {

		BufferedReader bufferedReader = null;

		IntervalTree ucscRefSeqGenesIntervalTree;

		// For each ChromosomeName
		for( ChromosomeName chrName : ChromosomeName.values()){

			ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTreeWithNumbers( dataFolder, chrName);
			bufferedReader = FileOperations.createBufferedReader(
					outputFolder,
					Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);

			searchGeneWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					givenIntervalNumber2GivenIntervalNameMap, givenIntervalNumber2OverlapInformationMap,
					chromosomeName2CountMap, chrName, bufferedReader, ucscRefSeqGenesIntervalTree, entrezGeneId2KMap,
					overlapDefinition, officialGeneSymbolNumber2NameMap, refSeqGeneNumber2RefSeqGeneNameMap);

			ucscRefSeqGenesIntervalTree = null;

			System.gc();
			System.runFinalization();

			try{
				bufferedReader.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of FOR each chromosomeName

	}

	// Annotation
	// KEGG Pathway
	// UserDefinedGeneSet
	// with numbers
	public void searchGeneSetWithNumbers( String dataFolder, String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			TIntIntMap exonBasedGeneSetNumber2KMap, 
			TIntIntMap regulationBasedGeneSetNumber2KMap,
			TIntIntMap allBasedGeneSetNumber2KMap, 
			int overlapDefinition,
			TIntObjectMap<String> geneSetNumber2GeneSetNameMap,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap, String geneSetName, GeneSetType geneSetType) {

		BufferedReader bufferedReader = null;

		IntervalTree ucscRefSeqGenesIntervalTree;

		// For each ChromosomeName
		for( ChromosomeName chrName : ChromosomeName.values()){

			ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTreeWithNumbers( dataFolder, chrName);
			bufferedReader = FileOperations.createBufferedReader(
					outputFolder,
					Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);

			searchGeneSetWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode, chrName,
					bufferedReader, ucscRefSeqGenesIntervalTree, exonBasedGeneSetNumber2KMap,
					regulationBasedGeneSetNumber2KMap, allBasedGeneSetNumber2KMap, overlapDefinition,
					geneSetNumber2GeneSetNameMap, geneId2ListofGeneSetNumberMap,
					geneHugoSymbolNumber2GeneHugoSymbolNameMap, refSeqGeneNumber2RefSeqGeneNameMap, geneSetName,
					geneSetType);

			ucscRefSeqGenesIntervalTree = null;

			System.gc();
			System.runFinalization();

			try{
				bufferedReader.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of for each chromosomeName

	}

	// KEGGPathway annotation with Numbers ends

	
	// Modified 20 July 2015
	// Annotation
	// With Numbers
	// TF KEGGPathway
	public void searchTfKEGGPathwayWithNumbers(
			String dataFolder, 
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			TIntIntMap tfNumberCellLineNumber2KMap, 
			TIntIntMap exonBasedKeggPathwayNumber2KMap,
			TIntIntMap regulationBasedKeggPathwayNumber2KMap, 
			TIntIntMap allBasedKeggPathwayNumber2KMap,
			TIntIntMap tfNumberExonBasedKeggPathwayNumber2KMap,
			TIntIntMap tfNumberRegulationBasedKeggPathwayNumber2KMap,
			TIntIntMap tfNumberAllBasedKeggPathwayNumber2KMap, 
			int overlapDefinition,
			TIntObjectMap<String> tfNumber2TfNameMap, 
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String> fileNumber2FileNameMap,
			TIntObjectMap<String> keggPathwayNumber2KeggPathwayNameMap,
			TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap) {

		BufferedReader bufferedReader = null;

		IntervalTree tfbsIntervalTree;
		IntervalTree ucscRefSeqGenesIntervalTree;

		// For each ChromosomeName
		for( ChromosomeName chrName : ChromosomeName.values()){

			tfbsIntervalTree = createTfbsIntervalTreeWithNumbers( dataFolder, chrName);
			ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTreeWithNumbers( dataFolder, chrName);
			bufferedReader = FileOperations.createBufferedReader(
					outputFolder,
					Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);

			searchTfKEGGPathwayWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode, chrName,
					bufferedReader, tfbsIntervalTree, ucscRefSeqGenesIntervalTree, tfNumberCellLineNumber2KMap,
					exonBasedKeggPathwayNumber2KMap, regulationBasedKeggPathwayNumber2KMap,
					allBasedKeggPathwayNumber2KMap, tfNumberExonBasedKeggPathwayNumber2KMap,
					tfNumberRegulationBasedKeggPathwayNumber2KMap, tfNumberAllBasedKeggPathwayNumber2KMap,
					overlapDefinition, tfNumber2TfNameMap, cellLineNumber2CellLineNameMap, fileNumber2FileNameMap,
					keggPathwayNumber2KeggPathwayNameMap, geneId2ListofKeggPathwayNumberMap,
					geneHugoSymbolNumber2GeneHugoSymbolNameMap, refSeqGeneNumber2RefSeqGeneNameMap);

			tfbsIntervalTree = null;
			ucscRefSeqGenesIntervalTree = null;

			System.gc();
			System.runFinalization();

			try{
				bufferedReader.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of for each chromosomeName

	}

	
	// Modified 20 July 2015
	// Annotation
	// With Numbers
	// TF CELLLINE KEGGPATHWAY
	public void searchTfCellLineKEGGPathwayWithNumbers(
			String dataFolder, 
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			TIntIntMap tfNumberCellLineNumber2KMap, 
			TIntIntMap exonBasedKeggPathwayNumber2KMap,
			TIntIntMap regulationBasedKeggPathwayNumber2KMap, 
			TIntIntMap allBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap, 
			int overlapDefinition,
			TIntObjectMap<String> tfNumber2TfNameMap, 
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String> fileNumber2FileNameMap,
			TIntObjectMap<String> keggPathwayNumber2KeggPathwayNameMap,
			TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap) {

		BufferedReader bufferedReader = null;

		IntervalTree tfbsIntervalTree;
		IntervalTree ucscRefSeqGenesIntervalTree;

		// For each ChromosomeName
		for( ChromosomeName chrName : ChromosomeName.values()){

			tfbsIntervalTree = createTfbsIntervalTreeWithNumbers( dataFolder, chrName);
			ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTreeWithNumbers( dataFolder, chrName);
			bufferedReader = FileOperations.createBufferedReader(
					outputFolder,
					Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);

			searchTfCellLineKEGGPathwayWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					chrName, bufferedReader, tfbsIntervalTree, ucscRefSeqGenesIntervalTree,
					tfNumberCellLineNumber2KMap, exonBasedKeggPathwayNumber2KMap,
					regulationBasedKeggPathwayNumber2KMap, allBasedKeggPathwayNumber2KMap,
					tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap,
					tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap,
					tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap, overlapDefinition, tfNumber2TfNameMap,
					cellLineNumber2CellLineNameMap, fileNumber2FileNameMap, keggPathwayNumber2KeggPathwayNameMap,
					geneId2ListofKeggPathwayNumberMap, geneHugoSymbolNumber2GeneHugoSymbolNameMap,
					refSeqGeneNumber2RefSeqGeneNameMap);

			tfbsIntervalTree = null;
			ucscRefSeqGenesIntervalTree = null;

			System.gc();
			System.runFinalization();

			try{
				bufferedReader.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of for each chromosomeName
	}

	
	// Modified 20 July 2015
	// Annotation
	// With Numbers
	// New Functionality START
	// TF CellLine KEGGPathway
	// TF KEGGPathway
	public void searchTfandKeggPathwayWithNumbers( 
			String dataFolder, 
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap, 
			TIntIntMap tfNumberCellLineNumber2KMap,
			TIntIntMap exonBasedKeggPathwayNumber2KMap, 
			TIntIntMap regulationBasedKeggPathwayNumber2KMap,
			TIntIntMap allBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap,
			TLongIntMap tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap,
			TIntIntMap tfNumberExonBasedKeggPathwayNumber2KMap,
			TIntIntMap tfNumberRegulationBasedKeggPathwayNumber2KMap,
			TIntIntMap tfNumberAllBasedKeggPathwayNumber2KMap, 
			int overlapDefinition,
			TIntObjectMap<String> tfNumber2TfNameMap, 
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String> fileNumber2FileNameMap,
			TIntObjectMap<String> keggPathwayNumber2KeggPathwayNameMap,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap) {

		BufferedReader bufferedReader = null;

		IntervalTree tfbsIntervalTree;
		IntervalTree ucscRefSeqGenesIntervalTree;

		// For each ChromosomeName
		for( ChromosomeName chrName : ChromosomeName.values()){

			tfbsIntervalTree = createTfbsIntervalTreeWithNumbers( dataFolder, chrName);
			ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTreeWithNumbers( dataFolder, chrName);
			bufferedReader = FileOperations.createBufferedReader(
					outputFolder,
					Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);

			searchTfandKeggPathwayWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode, chrName,
					bufferedReader, tfbsIntervalTree, ucscRefSeqGenesIntervalTree, geneId2ListofKeggPathwayNumberMap,
					tfNumberCellLineNumber2KMap, exonBasedKeggPathwayNumber2KMap,
					regulationBasedKeggPathwayNumber2KMap, allBasedKeggPathwayNumber2KMap,
					tfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap,
					tfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap,
					tfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap, tfNumberExonBasedKeggPathwayNumber2KMap,
					tfNumberRegulationBasedKeggPathwayNumber2KMap, tfNumberAllBasedKeggPathwayNumber2KMap,
					overlapDefinition, tfNumber2TfNameMap, cellLineNumber2CellLineNameMap, fileNumber2FileNameMap,
					keggPathwayNumber2KeggPathwayNameMap, geneHugoSymbolNumber2GeneHugoSymbolNameMap,
					refSeqGeneNumber2RefSeqGeneNameMap);

			tfbsIntervalTree = null;
			ucscRefSeqGenesIntervalTree = null;

			System.gc();
			System.runFinalization();

			try{
				bufferedReader.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}// End of for each chromosomeName

	}

	// @todo Annotation with Numbers ends

	// Annotation
	// UserDefinedLibrary
	// With Numbers
	public void searchUserDefinedLibraryWithNumbers( String dataFolder, String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			int overlapDefinition, TIntObjectMap<TIntIntMap> elementTypeNumber2ElementNumber2KMapMap,
			TIntObjectMap<TIntObjectMap<String>> elementTypeNumber2ElementNumber2ElementNameMapMap,
			TIntObjectMap<String> elementTypeNumber2ElementTypeMap, TIntObjectMap<String> fileNumber2FileNameMap) {

		int elementTypeNumber;
		String elementType;

		// Do Annotation With Numbers
		// For each elementTypeNumber
		for( TIntObjectIterator<String> it = elementTypeNumber2ElementTypeMap.iterator(); it.hasNext();){
			it.advance();
			elementTypeNumber = it.key();
			elementType = it.value();

			BufferedReader bufferedReader = null;
			IntervalTree userDefinedLibraryIntervalTree;

			TIntIntMap elementNumber2KMap = elementTypeNumber2ElementNumber2KMapMap.get( elementTypeNumber);
			TIntObjectMap<String> elementNumber2ElementNameMap = elementTypeNumber2ElementNumber2ElementNameMapMap.get( elementTypeNumber);

			// For each ChromosomeName
			for( ChromosomeName chrName : ChromosomeName.values()){

				userDefinedLibraryIntervalTree = createUserDefinedIntervalTreeWithNumbers( dataFolder,
						elementTypeNumber, elementType, chrName);
				bufferedReader = FileOperations.createBufferedReader(
						outputFolder,
						Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);
				searchUserDefinedLibraryWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
						chrName, bufferedReader, userDefinedLibraryIntervalTree, elementNumber2KMap, overlapDefinition,
						elementType, elementNumber2ElementNameMap, fileNumber2FileNameMap);
				userDefinedLibraryIntervalTree = null;

				System.gc();
				System.runFinalization();

				try{
					bufferedReader.close();
				}catch( IOException e){

					if( GlanetRunner.shouldLog())logger.error( e.toString());
				}

			}// End of for each chromosomeName

		}// End of for each elementTypeNumber

	}

	// Annotation
	// With Numbers
	public void searchTranscriptionFactorWithNumbers(
			String dataFolder, 
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			RegulatorySequenceAnalysisType regulatorySequenceAnalysisUsingRSAT, 
			TIntIntMap tfNumberCellLineNumber2KMap,
			int overlapDefinition, 
			TIntObjectMap<String> tfNumber2TFNameMap,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap, 
			TIntObjectMap<String> fileNumber2FileNameMap) {

		BufferedReader bufferedReader = null;

		IntervalTree transcriptionFactorIntervalTree;

		// For each ChromosomeName
		for( ChromosomeName chrName : ChromosomeName.values()){

			transcriptionFactorIntervalTree = createTfbsIntervalTreeWithNumbers( dataFolder, chrName);
			bufferedReader = FileOperations.createBufferedReader(
					outputFolder,
					Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);
			
			searchTranscriptionFactorWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					regulatorySequenceAnalysisUsingRSAT, chrName, bufferedReader, transcriptionFactorIntervalTree,
					tfNumberCellLineNumber2KMap, overlapDefinition, tfNumber2TFNameMap, cellLineNumber2CellLineNameMap,
					fileNumber2FileNameMap);
			
			transcriptionFactorIntervalTree = null;

			System.gc();
			System.runFinalization();

			try{
				bufferedReader.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of for each chromosomeName

	}

	//Modified 20 July 2015
	// Annotation
	// With Numbers
	public void searchHistoneWithNumbers( 
			String dataFolder, 
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			TIntIntMap histoneNumberCellLineNumber2KMap, 
			int overlapDefinition,
			TIntObjectMap<String> histoneNumber2HistoneNameMap,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap, 
			TIntObjectMap<String> fileNumber2FileNameMap) {

		BufferedReader bufferedReader = null;

		IntervalTree histoneIntervalTree;

		// For each ChromosomeName
		for( ChromosomeName chrName : ChromosomeName.values()){

			histoneIntervalTree = createHistoneIntervalTreeWithNumbers( dataFolder, chrName);
			bufferedReader = FileOperations.createBufferedReader(
					outputFolder,
					Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString( chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);
			
			searchHistoneWithNumbers(
					outputFolder, 
					writeElementBasedAnnotationFoundOverlapsMode, 
					chrName,
					bufferedReader, 
					histoneIntervalTree, 
					histoneNumberCellLineNumber2KMap, 
					overlapDefinition,
					histoneNumber2HistoneNameMap, 
					cellLineNumber2CellLineNameMap, 
					fileNumber2FileNameMap);
			
			histoneIntervalTree = null;

			System.gc();
			System.runFinalization();

			try{
				bufferedReader.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}// End of for each chromosomeName

	}

	// public void emptyIntervalTree(IntervalTreeNode node) {
	// if (node != null) {
	// emptyIntervalTree(node.getRight());
	// emptyIntervalTree(node.getLeft());
	//
	// // if(node.getCellLineName()!=null){
	// // node.setCellLineName(null);
	// // }
	// //
	// // if(node.getFileName()!=null){
	// // node.setFileName(null);
	// // }
	// //
	// // if(node.getTfbsorHistoneName()!=null){
	// // node.setTfbsorHistoneName(null);
	// // }
	// //
	// // if(node.getGeneHugoSymbol()!=null){
	// // node.setGeneHugoSymbol(null);
	// // }
	// //
	// // if(node.getIntervalName()!=null){
	// // node.setIntervalName(null);
	// // }
	// //
	// // if(node.getGeneEntrezId()!=null){
	// // node.setGeneEntrezId(null);
	// // }
	// //
	// // if(node.getRefSeqGeneName()!=null){
	// // node.setRefSeqGeneName(null);
	// // }
	//
	// if (node.getChromName() != null) {
	// node.setChromName(null);
	// }
	//
	// if (node.getLeft() != null) {
	// node.setLeft(null);
	// }
	//
	// if (node.getRight() != null) {
	// node.setRight(null);
	// }
	//
	// if (node.getParent() != null) {
	// node.setParent(null);
	// }
	//
	// node = null;
	// }
	// }

	public static void writeOverlaps(
			TIntObjectMap<List<UcscRefSeqGeneIntervalTreeNodeWithNumbers>> geneId2OverlapListMap,
			int givenIntervalNumber, TIntIntMap givenIntervalNumber2NumberofGeneOverlapsMap,
			GeneOverlapAnalysisFileMode geneOverlapAnalysisFileMode, IntervalName intervalName,
			NumberofGeneOverlaps geneOverlaps, BufferedWriter bufferedWriter,
			TIntObjectMap<String> geneHugoSymbolNumber2NameMap) throws IOException {

		int overlapCount = 0;
		String overlapStringInformation = "";

		List<UcscRefSeqGeneIntervalTreeNodeWithNumbers> overlapList = null;
		UcscRefSeqGeneIntervalTreeNodeWithNumbers overlapNode = null;

		// Accumulate overlapCount and overlapStringInformation for each geneID
		// For each geneId
		// Iterate Over Map
		for( TIntObjectIterator<List<UcscRefSeqGeneIntervalTreeNodeWithNumbers>> it1 = geneId2OverlapListMap.iterator(); it1.hasNext();){
			it1.advance();

			overlapList = it1.value();

			overlapCount = overlapCount + overlapList.size();

			// For each overlap
			// Iterate Over List
			for( Iterator<UcscRefSeqGeneIntervalTreeNodeWithNumbers> it2 = overlapList.iterator(); it2.hasNext();){

				overlapNode = it2.next();

				switch( overlapNode.getIntervalName()){

				// Write IntervalNumber
				case EXON:
				case INTRON:
					overlapStringInformation = overlapStringInformation + "[" + overlapNode.getGeneEntrezId() + "_" + geneHugoSymbolNumber2NameMap.get( overlapNode.getGeneHugoSymbolNumber()) + "_" + overlapNode.getIntervalName().convertEnumtoString() + "_" + overlapNode.getIntervalNumber() + "] ";

					break;

				// Do Not Write IntervalNumber
				case FIVE_P_ONE:
				case FIVE_P_TWO:
				case FIVE_D:
				case THREE_P_ONE:
				case THREE_P_TWO:
				case THREE_D:
					overlapStringInformation = overlapStringInformation + "[" + overlapNode.getGeneEntrezId() + "_" + geneHugoSymbolNumber2NameMap.get( overlapNode.getGeneHugoSymbolNumber()) + "_" + overlapNode.getIntervalName().convertEnumtoString() + "] ";

					break;

				}// End of Switch

			}// End of For: each Overlap

		}// End of For: each geneID

		// Accumulate
		givenIntervalNumber2NumberofGeneOverlapsMap.put( givenIntervalNumber,
				givenIntervalNumber2NumberofGeneOverlapsMap.get( givenIntervalNumber) + overlapCount);

		// Accumulate
		switch( intervalName){

		case EXON:
			geneOverlaps.setNumberofExonOverlaps( geneOverlaps.getNumberofExonOverlaps() + overlapCount);
			break;

		case INTRON:
			geneOverlaps.setNumberofIntronOverlaps( geneOverlaps.getNumberofIntronOverlaps() + overlapCount);
			break;

		case FIVE_P_ONE:
			geneOverlaps.setNumberof5p1Overlaps( geneOverlaps.getNumberof5p1Overlaps() + overlapCount);
			break;

		case FIVE_P_TWO:
			geneOverlaps.setNumberof5p2Overlaps( geneOverlaps.getNumberof5p2Overlaps() + overlapCount);
			break;

		case FIVE_D:
			geneOverlaps.setNumberof5dOverlaps( geneOverlaps.getNumberof5dOverlaps() + overlapCount);
			break;

		case THREE_P_ONE:
			geneOverlaps.setNumberof3p1Overlaps( geneOverlaps.getNumberof3p1Overlaps() + overlapCount);
			break;

		case THREE_P_TWO:
			geneOverlaps.setNumberof3p2Overlaps( geneOverlaps.getNumberof3p2Overlaps() + overlapCount);
			break;

		case THREE_D:
			geneOverlaps.setNumberof3dOverlaps( geneOverlaps.getNumberof3dOverlaps() + overlapCount);
			break;

		}

		switch( geneOverlapAnalysisFileMode){
		case WITH_OVERLAP_INFORMATION:
			bufferedWriter.write( overlapCount + "\t" + overlapStringInformation + "\t");
			break;
		case WITHOUT_OVERLAP_INFORMATION:
			bufferedWriter.write( overlapCount + "\t");
			break;
		}// End of switch

	}

	public static void writeChromosomeDistribution( TObjectIntMap<ChromosomeName> chromosomeName2CountMap,
			BufferedWriter bufferedWriter) throws IOException {

		bufferedWriter.write( System.getProperty( "line.separator"));
		bufferedWriter.write( System.getProperty( "line.separator"));

		int[] counts = chromosomeName2CountMap.values();
		int totalCount = 0;
		int count = 0;
		double percentage = 0;
		double totalPercentage = 0;

		for( int i = 0; i < counts.length; i++){
			totalCount = totalCount + counts[i];
		}

		// Write Header Line
		bufferedWriter.write( "" + "\t" + "Count" + "\t" + "Percentage" + System.getProperty( "line.separator"));

		for( ChromosomeName chrName : ChromosomeName.values()){

			count = chromosomeName2CountMap.get( chrName);
			percentage = ( count * 100.0) / totalCount;

			totalPercentage = totalPercentage + percentage;

			bufferedWriter.write( chrName.convertEnumtoString() + "\t" + count + "\t" + percentage + "%" + System.getProperty( "line.separator"));

		}// For each chromosomeName

		// Write Last Line
		bufferedWriter.write( "Total" + "\t" + totalCount + "\t" + totalPercentage + "%" + System.getProperty( "line.separator"));

	}

	public static void writeGeneOverlapAnalysisFile( String outputFolder, String outputFileName,
			GeneOverlapAnalysisFileMode geneOverlapAnalysisFileMode,
			TIntObjectMap<String> givenIntervalNumber2GivenIntervalNameMap,
			TIntObjectMap<OverlapInformation> givenIntervalNumber2OverlapInformationMap,
			TIntIntMap givenIntervalNumber2NumberofGeneOverlapsMap,
			TObjectIntMap<ChromosomeName> chromosomeName2CountMap, TIntObjectMap<String> geneHugoSymbolNumber2NameMap) {

		BufferedWriter bufferedWriter = null;

		int givenIntervalNumber = 0;
		String givenIntervalName = null;
		OverlapInformation overlapInformation = null;

		TIntObjectMap<List<UcscRefSeqGeneIntervalTreeNodeWithNumbers>> geneId2OverlapListMap = null;

		NumberofGeneOverlaps geneOverlaps = new NumberofGeneOverlaps();

		try{
			bufferedWriter = new BufferedWriter( FileOperations.createFileWriter( outputFolder + outputFileName));

			// Write Header Line
			switch( geneOverlapAnalysisFileMode){

			case WITH_OVERLAP_INFORMATION:
				bufferedWriter.write( "GivenIntervalNumber" + "\t" + "GivenInteval" + "\t" + "#ofExonOverlaps" + "\t" + "ExonOverlapsInformation" + "\t" + "#ofIntronOverlaps" + "\t" + "IntronOverlapsInformation" + "\t" + "#of5p1Overlaps" + "\t" + "5p1OverlapsInformation" + "\t" + "#of5p2Overlaps" + "\t" + "5p2OverlapsInformation" + "\t" + "#of5dOverlaps" + "\t" + "5dOverlapsInformation" + "\t" + "#of3p1Overlaps" + "\t" + "3p1OverlapsInformation" + "\t" + "#of3p2Overlaps" + "\t" + "3p2OverlapsInformation" + "\t" + "#of3dOverlaps" + "\t" + "3dOverlapsInformation" + "\t" + "#ofGeneOverlapsPerGivenInterval" + System.getProperty( "line.separator"));
				break;

			case WITHOUT_OVERLAP_INFORMATION:
				bufferedWriter.write( "GivenIntervalNumber" + "\t" + "GivenInteval" + "\t" + "#ofExonOverlaps" + "\t" + "#ofIntronOverlaps" + "\t" + "#of5p1Overlaps" + "\t" + "#of5p2Overlaps" + "\t" + "#of5dOverlaps" + "\t" + "#of3p1Overlaps" + "\t" + "#of3p2Overlaps" + "\t" + "#of3dOverlaps" + "\t" + "#ofGeneOverlapsPerGivenInterval" + System.getProperty( "line.separator"));
				break;
			}// End of SWITCH

			// For Each GivenInterval
			for( int i = 1; i <= givenIntervalNumber2GivenIntervalNameMap.size(); i++){

				givenIntervalNumber = i;

				givenIntervalName = givenIntervalNumber2GivenIntervalNameMap.get( givenIntervalNumber);

				overlapInformation = givenIntervalNumber2OverlapInformationMap.get( givenIntervalNumber);

				bufferedWriter.write( givenIntervalNumber + "\t" + givenIntervalName + "\t");

				if( overlapInformation != null){

					givenIntervalNumber2NumberofGeneOverlapsMap.put( givenIntervalNumber, 0);

					/***************************************************************/
					/*********** geneId 2 ExonOverlapsList Map starts ****************/
					/***************************************************************/
					// geneId2ExonOverlapsListMap
					geneId2OverlapListMap = overlapInformation.getGeneId2ExonOverlapListMap();

					// Write Number of Exon Overlaps
					writeOverlaps( geneId2OverlapListMap, givenIntervalNumber,
							givenIntervalNumber2NumberofGeneOverlapsMap, geneOverlapAnalysisFileMode,
							IntervalName.EXON, geneOverlaps, bufferedWriter, geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 ExonOverlapsList Map ends ******************/
					/***************************************************************/

					/***************************************************************/
					/*********** geneId 2 IntronOverlapsList Map starts **************/
					/***************************************************************/
					// geneId2IntronOverlapsListMap
					geneId2OverlapListMap = overlapInformation.getGeneId2IntronOverlapListMap();

					// Write Number of Intron Overlaps
					writeOverlaps( geneId2OverlapListMap, givenIntervalNumber,
							givenIntervalNumber2NumberofGeneOverlapsMap, geneOverlapAnalysisFileMode,
							IntervalName.INTRON, geneOverlaps, bufferedWriter, geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 IntronOverlapsList Map ends ****************/
					/***************************************************************/

					/***************************************************************/
					/*********** geneId 2 Fivep1OverlapsList Map starts **************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2Fivep1OverlapListMap();

					writeOverlaps( geneId2OverlapListMap, givenIntervalNumber,
							givenIntervalNumber2NumberofGeneOverlapsMap, geneOverlapAnalysisFileMode,
							IntervalName.FIVE_P_ONE, geneOverlaps, bufferedWriter, geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 Fivep1OverlapsList Map ends ****************/
					/***************************************************************/

					/***************************************************************/
					/*********** geneId 2 Fivep2OverlapsList Map starts **************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2Fivep2OverlapListMap();

					writeOverlaps( geneId2OverlapListMap, givenIntervalNumber,
							givenIntervalNumber2NumberofGeneOverlapsMap, geneOverlapAnalysisFileMode,
							IntervalName.FIVE_P_TWO, geneOverlaps, bufferedWriter, geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 Fivep2OverlapsList Map ends ****************/
					/***************************************************************/

					/***************************************************************/
					/*********** geneId 2 FivedOverlapsList Map starts **************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2FivedOverlapListMap();

					writeOverlaps( geneId2OverlapListMap, givenIntervalNumber,
							givenIntervalNumber2NumberofGeneOverlapsMap, geneOverlapAnalysisFileMode,
							IntervalName.FIVE_D, geneOverlaps, bufferedWriter, geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 FivedOverlapsList Map ends ****************/
					/***************************************************************/

					/***************************************************************/
					/*********** geneId 2 Threep1OverlapsList Map starts *************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2Threep1OverlapListMap();

					writeOverlaps( geneId2OverlapListMap, givenIntervalNumber,
							givenIntervalNumber2NumberofGeneOverlapsMap, geneOverlapAnalysisFileMode,
							IntervalName.THREE_P_ONE, geneOverlaps, bufferedWriter, geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 Threep1OverlapsList Map ends ***************/
					/***************************************************************/

					/***************************************************************/
					/*********** geneId 2 Threep2OverlapsList Map starts *************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2Threep2OverlapListMap();

					writeOverlaps( geneId2OverlapListMap, givenIntervalNumber,
							givenIntervalNumber2NumberofGeneOverlapsMap, geneOverlapAnalysisFileMode,
							IntervalName.THREE_P_TWO, geneOverlaps, bufferedWriter, geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 Threep2OverlapsList Map ends ***************/
					/***************************************************************/

					/***************************************************************/
					/*********** geneId 2 ThreedOverlapsList Map starts **************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2ThreedOverlapListMap();

					writeOverlaps( geneId2OverlapListMap, givenIntervalNumber,
							givenIntervalNumber2NumberofGeneOverlapsMap, geneOverlapAnalysisFileMode,
							IntervalName.THREE_D, geneOverlaps, bufferedWriter, geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 ThreedOverlapsList Map ends ****************/
					/***************************************************************/

					bufferedWriter.write( "" + givenIntervalNumber2NumberofGeneOverlapsMap.get( givenIntervalNumber));

				}// End of IF: overlapInformation is not null
				else{
					// For this given interval there is no overlap
					// OverlapInformation is null

					switch( geneOverlapAnalysisFileMode){
					case WITH_OVERLAP_INFORMATION:
						bufferedWriter.write( "0" + "\t" + "" + "\t" + "0" + "\t" + "" + "\t" + "0" + "\t" + "" + "\t" + "0" + "\t" + "" + "\t" + "0" + "\t" + "" + "\t" + "0" + "\t" + "" + "\t" + "0" + "\t" + "" + "\t" + "0" + "\t" + "" + "\t" + "0");
						break;

					case WITHOUT_OVERLAP_INFORMATION:
						bufferedWriter.write( "0" + "\t" + "0" + "\t" + "0" + "\t" + "0" + "\t" + "0" + "\t" + "0" + "\t" + "0" + "\t" + "0" + "\t" + "0");
						break;
					}// End of switch
				}

				// Go to next line for next given interval
				bufferedWriter.write( System.getProperty( "line.separator"));

			}// End of FOR: each given interval

			// Do calculations
			geneOverlaps.calculate();

			// Write Last Lines
			// Write Number of Gene Overlaps
			switch( geneOverlapAnalysisFileMode){

			case WITH_OVERLAP_INFORMATION:
				bufferedWriter.write( "Number of Overlaps" + "\t" + "" + "\t" + geneOverlaps.getNumberofExonOverlaps() + "\t" + "" + "\t" + geneOverlaps.getNumberofIntronOverlaps() + "\t" + "" + "\t" + geneOverlaps.getNumberof5p1Overlaps() + "\t" + "" + "\t" + geneOverlaps.getNumberof5p2Overlaps() + "\t" + "" + "\t" + geneOverlaps.getNumberof5dOverlaps() + "\t" + "" + "\t" + geneOverlaps.getNumberof3p1Overlaps() + "\t" + "" + "\t" + geneOverlaps.getNumberof3p2Overlaps() + "\t" + "" + "\t" + geneOverlaps.getNumberof3dOverlaps() + "\t" + "" + "\t" + geneOverlaps.getTotalNumberofOverlaps() + System.getProperty( "line.separator"));

				bufferedWriter.write( "" + "\t" + "" + "\t" + geneOverlaps.getExonPercentage() + "%" + "\t" + "" + "\t" + geneOverlaps.getIntronPercentage() + "%" + "\t" + "" + "\t" + geneOverlaps.getFivep1Percentage() + "%" + "\t" + "" + "\t" + geneOverlaps.getFivep2Percentage() + "%" + "\t" + "" + "\t" + geneOverlaps.getFivedPercentage() + "%" + "\t" + "" + "\t" + geneOverlaps.getThreep1Percentage() + "%" + "\t" + "" + "\t" + geneOverlaps.getThreep2Percentage() + "%" + "\t" + "" + "\t" + geneOverlaps.getThreedPercentage() + "%" + "\t" + "" + "\t" + geneOverlaps.getAllPercentage() + "%" + System.getProperty( "line.separator"));

				break;

			case WITHOUT_OVERLAP_INFORMATION:
				bufferedWriter.write( "Number of Overlaps" + "\t" + "" + "\t" + geneOverlaps.getNumberofExonOverlaps() + "\t" + geneOverlaps.getNumberofIntronOverlaps() + "\t" + geneOverlaps.getNumberof5p1Overlaps() + "\t" + geneOverlaps.getNumberof5p2Overlaps() + "\t" + geneOverlaps.getNumberof5dOverlaps() + "\t" + geneOverlaps.getNumberof3p1Overlaps() + "\t" + geneOverlaps.getNumberof3p2Overlaps() + "\t" + geneOverlaps.getNumberof3dOverlaps() + "\t" + geneOverlaps.getTotalNumberofOverlaps() + System.getProperty( "line.separator"));

				bufferedWriter.write( "" + "\t" + "" + "\t" + geneOverlaps.getExonPercentage() + "%" + "\t" + geneOverlaps.getIntronPercentage() + "%" + "\t" + geneOverlaps.getFivep1Percentage() + "%" + "\t" + geneOverlaps.getFivep2Percentage() + "%" + "\t" + geneOverlaps.getFivedPercentage() + "%" + "\t" + geneOverlaps.getThreep1Percentage() + "%" + "\t" + geneOverlaps.getThreep2Percentage() + "%" + "\t" + geneOverlaps.getThreedPercentage() + "%" + "\t" + geneOverlaps.getAllPercentage() + "%" + System.getProperty( "line.separator"));

				break;

			}// End of switch

			// Write ChromosomeName 2 NumberofGivenIntervals and Percentage
			writeChromosomeDistribution( chromosomeName2CountMap, bufferedWriter);

			// Close
			bufferedWriter.close();

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	public static void readDataIntoArray( BufferedReader bufferedReader, List<Interval> dataArrayList) {

		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		int low;
		int high;

		try{

			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexofFirstTab >= 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;

				// indexofSecondTab must be greater than zero if it exists since
				// indexofFirstTab must exists and can be at least zero
				// therefore indexofSecondTab can be at least one.
				if( indexofSecondTab > 0){
					low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				}

				else{
					low = Integer.parseInt( strLine.substring( indexofFirstTab + 1));
					high = low;
				}

				Interval interval = new Interval( low, high);
				dataArrayList.add( interval);

			}// End of while

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	// //Empirical P Value Calculation
	// //args[0] must have input file name with folder
	// //args[1] must have GLANET output folder
	// //args[2] must have GLANET data folder (necessary data for annotation and
	// augmentation)
	// //args[3] must have Input File Format
	// public AllName2KMaps annotateOriginalData(String[] args){
	//
	// String outputFolder = args[1];
	// String dataFolder = args[2];
	// int overlapDefinition = Integer.parseInt(args[3]);
	//
	// String inputFileName = outputFolder +
	// Commons.REMOVED_OVERLAPS_INPUT_FILE;
	//
	// AllName2KMaps allName2KMaps = new AllName2KMaps();
	//
	// //Prepare chromosome based partitioned input interval files to be
	// searched for
	// List<BufferedWriter> bufferedWriterList = new
	// ArrayList<BufferedWriter>();
	// //Create Buffered Writers for writing chromosome based input files
	// createChromBaseSeachInputFiles(outputFolder,bufferedWriterList);
	//
	// //Partition the input file into 24 chromosome based input files
	// partitionSearchInputFilePerChromName(inputFileName,bufferedWriterList);
	//
	// //Close Buffered Writers
	// closeBufferedWriterList(bufferedWriterList);
	//
	// //DNASE
	// //Search input interval files for dnase
	// List<String> dnaseCellLineNameList = new ArrayList<String>();
	// //This dnaseCellLine2KMap hash map will contain the dnase cell line name
	// to number of dnase cell line:k for the given search input size:n
	// Map<String,Integer> dnaseCellLine2KMap = new HashMap<String,Integer>();
	//
	// fillList(dnaseCellLineNameList,dataFolder ,
	// Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME +
	// Commons.WRITE_ALL_POSSIBLE_ENCODE_CELL_LINE_NAMES_OUTPUT_FILENAME);
	// searchDnase(dataFolder,outputFolder,dnaseCellLineNameList,dnaseCellLine2KMap,overlapDefinition);
	// writeResults(dnaseCellLine2KMap, outputFolder,
	// Commons.ANNOTATE_INTERVALS_DNASE_RESULTS_GIVEN_SEARCH_INPUT);
	// allName2KMaps.setDnaseCellLineName2NumberofOverlapsMap(dnaseCellLine2KMap);
	//
	// //TFBS
	// //Search input interval files for tfbs
	// List<String> tfbsNameList = new ArrayList<String>();
	// //This tfbsNameandCellLineName2KMap hash map will contain the
	// tfbsNameandCellLineName to number of tfbsNameandCellLineName: k for the
	// given search input size: n
	// Map<String,Integer> tfbsNameandCellLineName2KMap = new
	// HashMap<String,Integer>();
	//
	// fillList(tfbsNameList,dataFolder ,
	// Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME +
	// Commons.WRITE_ALL_POSSIBLE_ENCODE_TF_NAMES_OUTPUT_FILENAME);
	// searchTfbs(dataFolder,outputFolder,tfbsNameList,tfbsNameandCellLineName2KMap);
	// writeResults(tfbsNameandCellLineName2KMap, outputFolder,
	// Commons.ANNOTATE_INTERVALS_TF_RESULTS_GIVEN_SEARCH_INPUT);
	// allName2KMaps.setTfbsNameandCellLineName2NumberofOverlapsMap(tfbsNameandCellLineName2KMap);
	//
	// //HISTONE
	// //Search input interval files for histone
	// List<String> histoneNameList = new ArrayList<String>();
	// //This histoneNameandCellLineName2KMap hash map will contain the
	// histoneNameandCellLineName to number of histoneNameandCellLineName: k for
	// the given search input size: n
	// Map<String,Integer> histoneNameandCellLineName2KMap = new
	// HashMap<String,Integer>();
	//
	// fillList(histoneNameList,dataFolder
	// ,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME +
	// Commons.WRITE_ALL_POSSIBLE_ENCODE_HISTONE_NAMES_OUTPUT_FILENAME);
	// searchHistone(dataFolder,outputFolder,histoneNameList,histoneNameandCellLineName2KMap,overlapDefinition);
	// writeResults(histoneNameandCellLineName2KMap, outputFolder,
	// Commons.ANNOTATE_INTERVALS_HISTONE_RESULTS_GIVEN_SEARCH_INPUT);
	// allName2KMaps.setHistoneNameandCellLineName2NumberofOverlapsMap(histoneNameandCellLineName2KMap);
	//
	//
	// //KEGG PATHWAY
	// //Search input interval files for kegg Pathway
	// List<String> keggPathwayNameList = new ArrayList<String>();
	//
	// //Fill keggPathwayNameList
	// fillList(keggPathwayNameList,dataFolder ,
	// Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME +
	// Commons.WRITE_ALL_POSSIBLE_KEGG_PATHWAY_NAMES_OUTPUT_FILENAME);
	//
	// Map<String,List<String>> geneId2KeggPathwayMap = new HashMap<String,
	// List<String>>();
	// KeggPathwayUtility.createNcbiGeneId2KeggPathwayMap(dataFolder,
	// Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, geneId2KeggPathwayMap);
	//
	// //Exon Based Kegg Pathway Analysis
	// //This exonBasedKeggPathway2KMap hash map will contain the kegg pathway
	// name to number of kegg pathway:k for the given search input size:n
	// Map<String,Integer> exonBasedKeggPathway2KMap = new
	// HashMap<String,Integer>();
	//
	// searchKeggPathway(dataFolder,outputFolder,geneId2KeggPathwayMap,keggPathwayNameList,
	// exonBasedKeggPathway2KMap, Commons.EXON_BASED_KEGG_PATHWAY_ANALYSIS);
	// writeResults(exonBasedKeggPathway2KMap,
	// outputFolder,Commons.ANNOTATE_INTERVALS_EXON_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT);
	// allName2KMaps.setExonBasedKeggPathway2NumberofOverlapsMap(exonBasedKeggPathway2KMap);
	//
	//
	// //Regulation Based Kegg Pathway Analysis
	// //This regulationBasedKeggPathway2KMap hash map will contain the kegg
	// pathway name to number of kegg pathway:k for the given search input
	// size:n
	// Map<String,Integer> regulationBasedKeggPathway2KMap = new
	// HashMap<String,Integer>();
	//
	// searchKeggPathway(dataFolder,outputFolder,geneId2KeggPathwayMap,keggPathwayNameList,
	// regulationBasedKeggPathway2KMap,
	// Commons.REGULATION_BASED_KEGG_PATHWAY_ANALYSIS);
	// writeResults(regulationBasedKeggPathway2KMap, outputFolder ,
	// Commons.ANNOTATE_INTERVALS_REGULATION_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT);
	// allName2KMaps.setRegulationBasedKeggPathway2NumberofOverlapsMap(regulationBasedKeggPathway2KMap);
	//
	// //All results Kegg Pathway Analysis
	// //This regulationBasedKeggPathway2KMap hash map will contain the kegg
	// pathway name to number of kegg pathway:k for the given search input
	// size:n
	// Map<String,Integer> allResultsKeggPathway2KMap = new
	// HashMap<String,Integer>();
	//
	// searchKeggPathway(dataFolder,outputFolder,
	// geneId2KeggPathwayMap,keggPathwayNameList, allResultsKeggPathway2KMap,
	// Commons.ALL_BASED_KEGG_PATHWAY_ANALYSIS);
	// writeResults(allResultsKeggPathway2KMap, outputFolder ,
	// Commons.ANNOTATE_INTERVALS_ALL_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT);
	//
	// return allName2KMaps;
	// }

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
	// args[29] ---> User Defined Library DataFormat
	// default
	// Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	// Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	// Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// args[29] - args[args.length-1] ---> Note that the selected cell lines are
	// always inserted at the end of the args array because it's size
	// is not fixed. So for not (until the next change on args array) the
	// selected cell
	// lines can be reached starting from 22th index up until (args.length-1)th
	// index.
	// If no cell line selected so the args.length-1 will be 22-1 = 21. So it
	// will never
	// give an out of boundry exception in a for loop with this approach.
	public void annotate( String[] args) {

		/***********************************************************************************/
		/**************Memory Usage Before Annotation***************************************/
		/***********************************************************************************/
		// if( GlanetRunner.shouldLog())logger.info("Memory Used Before Annotation" + "\t" +
		// ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE)
		// + "\t" + "MBs");
		/***********************************************************************************/
		/**************Memory Usage Before Annotation***************************************/
		/***********************************************************************************/

		/*************************************************************************************************/
		/**************************GET SOME ARGUMENTS STARTS**********************************************/
		/*************************************************************************************************/
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];

		// jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if( jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		// jobName ends

		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty( "file.separator") + jobName + System.getProperty( "file.separator");
		String givenInputDataFolder = outputFolder + Commons.GIVENINPUTDATA + System.getProperty( "file.separator");

		RegulatorySequenceAnalysisType regulatorySequenceAnalysisUsingRSAT = RegulatorySequenceAnalysisType.convertStringtoEnum( args[CommandLineArguments.RegulatorySequenceAnalysisUsingRSAT.value()]);

		// This argument is set internally.
		// If you want to change this argument
		// Then update runButtonPressed method of MainView Class for UI and
		// Update CommandLineArguments constructor of CommandLineArguments class for Command Line.
		WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode = WriteElementBasedAnnotationFoundOverlapsMode.convertStringtoEnum( args[CommandLineArguments.WriteElementBasedAnnotationFoundOverlapsMode.value()]);

		int overlapDefinition = Integer.parseInt( args[CommandLineArguments.NumberOfBasesRequiredForOverlap.value()]);

		String inputFileName;
		/*************************************************************************************************/
		/**************************GET SOME ARGUMENTS ENDS************************************************/
		/*************************************************************************************************/

		/*************************************************************************************************/
		/**************************GET CHECKED ANNOTATION TYPES STARTS ***********************************/
		/*************************************************************************************************/

		/***********************************************************************************/
		/*************DEFAULT ANNOTATION ARGUMENTS STARTS***********************************/
		/***********************************************************************************/
		/**************************DNASE ANNOTATION*****************************************/
		AnnotationType dnaseAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.DnaseAnnotation.value()]);

		/**************************HISTONE ANNOTATION***************************************/
		AnnotationType histoneAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.HistoneAnnotation.value()]);

		/**************************TF ANNOTATION********************************************/
		AnnotationType tfAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.TfAnnotation.value()]);

		/**************************GENE ANNOTATION******************************************/
		AnnotationType geneAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.GeneAnnotation.value()]);

		/**************************KEGG Pathway ANNOTATION**********************************/
		AnnotationType keggPathwayAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.KeggPathwayAnnotation.value()]);

		/**************************TF KEGG Pathway ANNOTATION*******************************/
		AnnotationType tfKeggPathwayAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.TfAndKeggPathwayAnnotation.value()]);

		/**************************TF CellLine KEGG Pathway ANNOTATION**********************/
		AnnotationType tfCellLineKeggPathwayAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()]);
		/***********************************************************************************/
		/*************DEFAULT ANNOTATION ARGUMENTS ENDS*************************************/
		/***********************************************************************************/

		/***********************************************************************************/
		/*************USER DEFINED GENESET ANNOTATION ARGUMENTS STARTS**********************/
		/***********************************************************************************/
		// User Defined GeneSet Annotation, DO or DO_NOT
		AnnotationType userDefinedGeneSetAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.UserDefinedGeneSetAnnotation.value()]);

		String userDefinedGeneSetInputFile = args[CommandLineArguments.UserDefinedGeneSetInput.value()];

		GeneInformationType geneInformationType = GeneInformationType.convertStringtoEnum( args[CommandLineArguments.UserDefinedGeneSetGeneInformation.value()]);

		String userDefinedGeneSetName = args[CommandLineArguments.UserDefinedGeneSetName.value()];
		/***********************************************************************************/
		/*************USER DEFINED GENESET ANNOTATION ARGUMENTS ENDS************************/
		/***********************************************************************************/

		
		/***********************************************************************************/
		/*******************USER DEFINED LIBRARY ANNOTAION ARGUMENTS STARTS*****************/
		/***********************************************************************************/
		// User Defined Library Annotation, DO or DO_NOT
		AnnotationType userDefinedLibraryAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.UserDefinedLibraryAnnotation.value()]);

		String userDefinedLibraryInputFile = args[CommandLineArguments.UserDefinedLibraryInput.value()];

		UserDefinedLibraryDataFormat userDefinedLibraryDataFormat = UserDefinedLibraryDataFormat.convertStringtoEnum( args[CommandLineArguments.UserDefinedLibraryDataFormat.value()]);
		/***********************************************************************************/
		/*******************USER DEFINED LIBRARY ANNOTATION ARGUMENTS ENDS******************/
		/***********************************************************************************/

		/*************************************************************************************************/
		/**************************GET CHECKED ANNOTATION TYPES ENDS**************************************/
		/*************************************************************************************************/

		/**********************************************************************/
		/***********Delete old files starts ***********************************/
		/**********************************************************************/
		String annotateOutputBaseDirectoryName = outputFolder + Commons.ANNOTATION;

		FileOperations.deleteOldFiles( annotateOutputBaseDirectoryName);
		/**********************************************************************/
		/***********Delete old files ends *************************************/
		/**********************************************************************/

		/*****************************************************************************************/
		/************************* GIVEN INPUT DATA starts ***************************************/
		/*****************************************************************************************/
		inputFileName = givenInputDataFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE_0BASED_START_END_GRCh37_p13;

		TIntObjectMap<FileWriter> chrNumber2FileWriterMap = new TIntObjectHashMap<FileWriter>();
		TIntObjectMap<BufferedWriter> chrNumber2BufferedWriterMap = new TIntObjectHashMap<BufferedWriter>();

		// Prepare chromosome based partitioned input interval files to be searched for
		// Create Buffered Writers for writing chromosome based input files
		FileOperations.createChromBaseSearchInputFiles( outputFolder, chrNumber2FileWriterMap,
				chrNumber2BufferedWriterMap);

		// Partition the input file into 24 chromosome based input files
		partitionSearchInputFilePerChromName( inputFileName, chrNumber2BufferedWriterMap);

		// Close Buffered Writers
		closeBufferedWriterList( chrNumber2FileWriterMap, chrNumber2BufferedWriterMap);
		/*****************************************************************************************/
		/************************* GIVEN INPUT DATA ends *****************************************/
		/*****************************************************************************************/

		// /********************************************************************************************************/
		// /*************** FILL ARRAYS of CHAR ARRAYS starts*******************************************************/
		// /********************************************************************************************************/
		// char[][] dnaseCellLineNames = new char[100][];
		// char[][] fileNames = new char[1000][];
		//
		//
		// FileOperations.fillNameList(dnaseCellLineNames, dataFolder,
		// Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME +
		// Commons.ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		// FileOperations.fillNameList(fileNames, dataFolder, Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME +
		// Commons.ALL_POSSIBLE_ENCODE_FILE_NUMBER_2_NAME_OUTPUT_FILENAME);
		// /********************************************************************************************************/
		// /*************** FILL ARRAYS of CHAR ARRAYS ends*********************************************************/
		// /********************************************************************************************************/

		// /********************************************************************************************************/
		// /*************** FILL NUMBER 2 NAME TEST TROVE MAP CHAR SEQUENCE starts**********************************/
		// /********************************************************************************************************/
		// TShortObjectMap<CharSequence> dnaseCellLineNumber2NameCharSequenceMap = new
		// TShortObjectHashMap<CharSequence>();
		// TShortObjectMap<CharSequence> fileNumber2NameCharSequenceMap = new TShortObjectHashMap<CharSequence>();
		//
		// FileOperations.fillNumber2NameCharSequenceMap(dnaseCellLineNumber2NameCharSequenceMap, dataFolder,
		// Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME +
		// Commons.ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		// FileOperations.fillNumber2NameCharSequenceMap(fileNumber2NameCharSequenceMap, dataFolder,
		// Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME +
		// Commons.ALL_POSSIBLE_ENCODE_FILE_NUMBER_2_NAME_OUTPUT_FILENAME);
		// /********************************************************************************************************/
		// /*************** FILL NUMBER 2 NAME TEST TROVE MAP CHAR SEQUENCE ends************************************/
		// /********************************************************************************************************/

		// /********************************************************************************************************/
		// /*************** FILL NUMBER 2 NAME TEST TROVE MAP STRING BUILDER starts*********************************/
		// /********************************************************************************************************/
		// TShortObjectMap<StringBuilder> dnaseCellLineNumber2NameStringBuilderMap = new
		// TShortObjectHashMap<StringBuilder>();
		// TShortObjectMap<StringBuilder> fileNumber2NameStringBuilderMap = new TShortObjectHashMap<StringBuilder>();
		//
		// FileOperations.fillNumber2NameStringBuilderMap(dnaseCellLineNumber2NameStringBuilderMap, dataFolder,
		// Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME +
		// Commons.ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		// FileOperations.fillNumber2NameStringBuilderMap(fileNumber2NameStringBuilderMap, dataFolder,
		// Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME +
		// Commons.ALL_POSSIBLE_ENCODE_FILE_NUMBER_2_NAME_OUTPUT_FILENAME);
		// /********************************************************************************************************/
		// /*************** FILL NUMBER 2 NAME TEST TROVE MAP STRING BUILDER ends***********************************/
		// /********************************************************************************************************/

		/********************************************************************************************************/
		/*************** FILL NUMBER 2 NAME TROVE MAP STRING starts**********************************************/
		/********************************************************************************************************/
		TIntObjectMap<String> dnaseCellLineNumber2NameMap 	= new TIntObjectHashMap<String>();
		TIntObjectMap<String> cellLineNumber2NameMap 		= new TIntObjectHashMap<String>();
		TIntObjectMap<String> fileNumber2NameMap 			= new TIntObjectHashMap<String>();
		TIntObjectMap<String> histoneNumber2NameMap 		= new TIntObjectHashMap<String>();
		TIntObjectMap<String> tfNumber2NameMap 				= new TIntObjectHashMap<String>();
		TIntObjectMap<String> keggPathwayNumber2NameMap 	= new TIntObjectHashMap<String>();

		TIntObjectMap<String> geneHugoSymbolNumber2NameMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> geneEntrezId2GeneOfficialSymbolMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> refSeqRNANucleotideAccessionNumber2NameMap = new TIntObjectHashMap<String>();

		FileOperations.fillNumber2NameMap(
				dnaseCellLineNumber2NameMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.fillNumber2NameMap(
				cellLineNumber2NameMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.fillNumber2NameMap(
				fileNumber2NameMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_FILE_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.fillNumber2NameMap(
				histoneNumber2NameMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_HISTONE_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.fillNumber2NameMap(
				tfNumber2NameMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.fillNumber2NameMap(
				keggPathwayNumber2NameMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.fillNumber2NameMap(
				geneHugoSymbolNumber2NameMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.fillNumber2NameMap(
				refSeqRNANucleotideAccessionNumber2NameMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_RNANUCLEOTIDEACCESSION_NUMBER_2_NAME_OUTPUT_FILENAME);

		HumanGenesAugmentation.fillGeneId2GeneHugoSymbolMap( dataFolder, geneEntrezId2GeneOfficialSymbolMap);
		/********************************************************************************************************/
		/*************** FILL NUMBER 2 NAME TROVE MAP STRING ends************************************************/
		/********************************************************************************************************/

		/********************************************************************************************************/
		/*************** FILL NAME 2 NUMBER MAPS starts**********************************************************/
		/********************************************************************************************************/
		TObjectIntMap<String> keggPathwayName2NumberMap = new TObjectIntHashMap<String>();

		FileOperations.fillName2NumberMap(
				keggPathwayName2NumberMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_KEGGPATHWAY_NAME_2_NUMBER_OUTPUT_FILENAME);
		/********************************************************************************************************/
		/*************** FILL NAME 2 NUMBER MAPS ends************************************************************/
		/********************************************************************************************************/

		/**************************************************************************************/
		/********************TIME MEASUREMENT**************************************************/
		/**************************************************************************************/
		// if you want to see the current year and day etc. change the line of code below with:
		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		long dateBefore = Long.MIN_VALUE;
		long dateAfter = Long.MIN_VALUE;
		/**************************************************************************************/
		/********************TIME MEASUREMENT**************************************************/
		/**************************************************************************************/

		/**************************************************************************************/
		/********************ANNOTATION***Sequentially or in Parallel**************************/
		/**************************************************************************************/
		enumtypes.Annotation annotation = enumtypes.Annotation.ANNOTATION_SEQUENTIALLY;
		/**************************************************************************************/
		/********************ANNOTATION***Sequentially or in Parallel**************************/
		/**************************************************************************************/

		// /**********************FOR TESTING PURPOSES******************************************************************/
		// /********************ANNOTATION IN PARALLEL STARTS***********************************************************/
		// /************************************************************************************************************/
		// if (annotation.annotateInParalel()){
		//
		//
		// /**************************************************************************************/
		// /********************Concurrent Programming Initizalization starts*********************/
		// /**************************************************************************************/
		// ForkJoinPool pool = new ForkJoinPool(Commons.NUMBER_OF_AVAILABLE_PROCESSORS);
		//
		// int[] allChromosomesDnaseCellLineKArray = null;
		// int numberofComparisonsDnase = 0;
		//
		// if (dnaseAnnotationType.doDnaseAnnotation()) {
		//
		// GlanetRunner.appendLog("**********************************************************");
		// GlanetRunner.appendLog("CellLine Based DNASE annotation starts: " + new Date());
		// dateBefore = System.currentTimeMillis();
		//
		// numberofComparisonsDnase =
		// NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder,dnaseAnnotationType);
		//
		// allChromosomesDnaseCellLineKArray = new int[numberofComparisonsDnase];
		//
		// }
		// /**************************************************************************************/
		// /********************Concurrent Programming Initizalization ends***********************/
		// /**************************************************************************************/
		//
		// // For each ChromosomeName
		// for (ChromosomeName chrName : ChromosomeName.values()) {
		//
		// List<Interval> dataArrayList =new ArrayList<Interval>();
		// BufferedReader bufferedReader = FileOperations.createBufferedReader(outputFolder,
		// Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString(chrName) +
		// Commons.CHROMOSOME_BASED_GIVEN_INPUT);
		//
		// readDataIntoArray(bufferedReader,dataArrayList);
		//
		// /*******************************************************************************/
		// /************ DNASE**ANNOTATION****starts **************************************/
		// /*******************************************************************************/
		// if (dnaseAnnotationType.doDnaseAnnotation()) {
		//
		// // DNASE
		// int[] dnaseCellLineKArray = null;
		// IntervalTree dnaseIntervalTree = createDnaseIntervalTreeWithNumbers(dataFolder, chrName);
		//
		//
		//
		// //if( GlanetRunner.shouldLog())logger.info(chrName.convertEnumtoString() + "\t" + "number of given interval" + "\t" +
		// dataArrayList.size());
		//
		// dnaseCellLineKArray = searchDnaseWithNumbers(
		// dataFolder,
		// outputFolder,
		// chrName,
		// dataArrayList,
		// overlapDefinition,
		// dnaseIntervalTree,
		// numberofComparisonsDnase,
		// dnaseCellLineNames,
		// fileNames,
		// writeElementBasedAnnotationFoundOverlapsMode,
		// pool);
		//
		//
		// //if( GlanetRunner.shouldLog())logger.info("Check It" + "\t" + chrName.convertEnumtoString() + "\t" + "dnaseCellLineNumber2KMap.size()" +
		// "\t" + dnaseCellLineNumber2KMap.size());
		//
		// Accumulation.accumulate(dnaseCellLineKArray,allChromosomesDnaseCellLineKArray);
		//
		//
		// dnaseCellLineKArray = null;
		// emptyIntervalTree(dnaseIntervalTree.getRoot());
		// dnaseIntervalTree = null;
		//
		// System.gc();
		// System.runFinalization();
		//
		//
		// }
		// /*******************************************************************************/
		// /************ DNASE***ANNOTATION********ends ***********************************/
		// /*******************************************************************************/
		//
		// }//End of for each Chromosome
		//
		// /**************************************************************************************/
		// /********************Concurrent Programming Finalization starts************************/
		// /**************************************************************************************/
		//
		// pool.shutdown();
		//
		// if (dnaseAnnotationType.doDnaseAnnotation()) {
		//
		// writeResultsWithNumbers(allChromosomesDnaseCellLineKArray, dnaseCellLineNames, outputFolder,
		// Commons.ANNOTATION_RESULTS_FOR_DNASE);
		//
		// dateAfter = System.currentTimeMillis();
		// GlanetRunner.appendLog("CellLine Based DNASE annotation ends: " + new Date());
		// GlanetRunner.appendLog("CellLine Based Dnase annotation took: " + (float) ((dateAfter - dateBefore) / 1000) +
		// " seconds");
		// GlanetRunner.appendLog("**********************************************************");
		//
		//
		// allChromosomesDnaseCellLineKArray = null;
		// System.gc();
		// System.runFinalization();
		//
		//
		// }
		// /**************************************************************************************/
		// /********************Concurrent Programming Finalization ends**************************/
		// /**************************************************************************************/
		// }
		// /************************************************************************************************************/
		// /********************ANNOTATION IN PARALLEL
		// ENDS*************************************************************/
		// /**********************FOR TESTING
		// PURPOSES******************************************************************/
		// /************************************************************************************************************/

		/************************************************************************************************************/
		/********************ANNOTATION SEQUENTIALLY STARTS**********************************************************/
		/************************************************************************************************************/
		if( annotation.annotateSequentially()){

			/*******************************************************************************/
			/************DNASE ANNOTATION starts********************************************/
			/*******************************************************************************/
			// TShortObjectMap<String>
			// While reading strLine.substring(indexofFirstTab + 1)
			// While writing as it is
			if( dnaseAnnotationType.doDnaseAnnotation()){

				// DNASE
				TIntIntMap dnaseCellLineNumber2KMap = new TIntIntHashMap();

				GlanetRunner.appendLog( "**********************************************************");
				GlanetRunner.appendLog( "CellLine Based DNASE annotation using TShortIntMap starts: " + new Date());
				dateBefore = System.currentTimeMillis();

				searchDnaseWithNumbers(
						dataFolder, 
						outputFolder, 
						writeElementBasedAnnotationFoundOverlapsMode,
						dnaseCellLineNumber2KMap, 
						overlapDefinition, 
						dnaseCellLineNumber2NameMap, 
						fileNumber2NameMap);
				
				writeResultsWithNumbers(
						dnaseCellLineNumber2KMap, 
						dnaseCellLineNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_DNASE);

				dateAfter = System.currentTimeMillis();

				GlanetRunner.appendLog( "CellLine Based DNASE annotation using TShortIntMap ends: " + new Date());

				GlanetRunner.appendLog( "CellLine Based Dnase annotation using TShortIntMap took: " + ( float)( ( dateAfter - dateBefore) / 1000) + " seconds");
				GlanetRunner.appendLog( "**********************************************************");

				dnaseCellLineNumber2KMap = null;

				System.gc();
				System.runFinalization();

			}
			/*******************************************************************************/
			/************DNASE ANNOTATION ends**********************************************/
			/*******************************************************************************/

			// if( GlanetRunner.shouldLog())logger.info("durationofCharArray" + "\t" + "durationofCharSequenceMap" + "\t" +
			// "durationofStringBuilderMap" + "\t" + "durationofStringMap" );
			// int sizeofArray = 10;
			// float[][] duration = new float[sizeofArray+1][4];
			//
			// for(int i = 0; i<sizeofArray; i++){
			//
			//
			//
			// //charArray
			// //While Reading strLine.substring(indexofFirstTab + 1).toCharArray();
			// //While Writing String.valueof()
			// if (dnaseAnnotationType.doDnaseAnnotation()){
			//
			// GlanetRunner.appendLog("**********************************************************");
			// GlanetRunner.appendLog("CellLine Based DNASE annotation using char[][] starts: " + new Date());
			// dateBefore = System.currentTimeMillis();
			//
			//
			// int[] dnaseCellLineKArray = null;
			// int numberofComparisonsDnase = 0;
			// numberofComparisonsDnase =
			// NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder,dnaseAnnotationType);
			//
			// dnaseCellLineKArray = new int[numberofComparisonsDnase];
			//
			// searchDnaseWithNumbers(dataFolder,outputFolder,writeElementBasedAnnotationFoundOverlapsMode,dnaseCellLineKArray,
			// overlapDefinition, dnaseCellLineNames, fileNames);
			// writeResultsWithNumbers(dnaseCellLineKArray, dnaseCellLineNames, outputFolder,
			// Commons.ANNOTATION_RESULTS_FOR_DNASE_USING_INT_ARRAY);
			//
			// dateAfter = System.currentTimeMillis();
			// duration[i][0] = (float) ((dateAfter - dateBefore) / 1000);
			//
			// duration[sizeofArray][0] += duration[i][0];
			//
			// GlanetRunner.appendLog("CellLine Based DNASE annotation using char[][] ends: " + new Date());
			//
			// GlanetRunner.appendLog("CellLine Based Dnase annotation using char[][] took: " + (float) ((dateAfter -
			// dateBefore) / 1000) + " seconds");
			// GlanetRunner.appendLog("**********************************************************");
			//
			// dnaseCellLineKArray = null;
			//
			// System.gc();
			// System.runFinalization();
			//
			// }
			//
			//
			// //TShortObjectMap<CharSequence>
			// //While Reading strLine.subSequence(indexofFirstTab + 1,strLine.length())
			// //While Writing as it is
			// if (dnaseAnnotationType.doDnaseAnnotation()){
			//
			// GlanetRunner.appendLog("**********************************************************");
			// GlanetRunner.appendLog("CellLine Based DNASE annotation using TShortObjectHashMap<CharSequence> starts: "
			// + new Date());
			// dateBefore = System.currentTimeMillis();
			//
			//
			// int[] dnaseCellLineKArray = null;
			// int numberofComparisonsDnase = 0;
			// numberofComparisonsDnase =
			// NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder,dnaseAnnotationType);
			//
			// dnaseCellLineKArray = new int[numberofComparisonsDnase];
			//
			// searchDnaseWithNumbers(dataFolder,outputFolder,writeElementBasedAnnotationFoundOverlapsMode,dnaseCellLineKArray,
			// overlapDefinition, dnaseCellLineNumber2NameCharSequenceMap, fileNumber2NameCharSequenceMap);
			// writeResultsWithNumbers(dnaseCellLineKArray, dnaseCellLineNames, outputFolder,
			// Commons.ANNOTATION_RESULTS_FOR_DNASE_USING_INT_ARRAY);
			//
			// dateAfter = System.currentTimeMillis();
			// duration[i][1] = (float) ((dateAfter - dateBefore) / 1000);
			// duration[sizeofArray][1] += duration[i][1];
			//
			// GlanetRunner.appendLog("CellLine Based DNASE annotation using TShortObjectHashMap<CharSequence> ends: " +
			// new Date());
			// GlanetRunner.appendLog("CellLine Based Dnase annotation using TShortObjectHashMap<CharSequence> took: " +
			// (float) ((dateAfter - dateBefore) / 1000) + " seconds");
			// GlanetRunner.appendLog("**********************************************************");
			//
			// dnaseCellLineKArray = null;
			//
			// System.gc();
			// System.runFinalization();
			//
			// }
			//
			//
			//
			// //TShortObjectMap<StringBuilder>
			// //While reading new StringBuilder(16).append(strLine.substring(indexofFirstTab + 1));
			// //While writing as it is
			// if (dnaseAnnotationType.doDnaseAnnotation()){
			//
			// GlanetRunner.appendLog("**********************************************************");
			// GlanetRunner.appendLog("CellLine Based DNASE annotation using TShortObjectHashMap<StringBuilder> starts: "
			// + new Date());
			// dateBefore = System.currentTimeMillis();
			//
			//
			// int[] dnaseCellLineKArray = null;
			// int numberofComparisonsDnase = 0;
			// numberofComparisonsDnase =
			// NumberofComparisons.getNumberofComparisonsforBonferroniCorrection(dataFolder,dnaseAnnotationType);
			//
			// dnaseCellLineKArray = new int[numberofComparisonsDnase];
			//
			// searchDnaseWithNumbersWithStringBuilder(dataFolder,outputFolder,writeElementBasedAnnotationFoundOverlapsMode,dnaseCellLineKArray,
			// overlapDefinition, dnaseCellLineNumber2NameStringBuilderMap, fileNumber2NameStringBuilderMap);
			// writeResultsWithNumbers(dnaseCellLineKArray, dnaseCellLineNames, outputFolder,
			// Commons.ANNOTATION_RESULTS_FOR_DNASE_USING_INT_ARRAY);
			//
			// dateAfter = System.currentTimeMillis();
			// duration[i][2] = (float) ((dateAfter - dateBefore) / 1000);
			// duration[sizeofArray][2] += duration[i][2];
			//
			//
			// GlanetRunner.appendLog("CellLine Based DNASE annotation using TShortObjectHashMap<StringBuilder> ends: "
			// + new Date());
			// GlanetRunner.appendLog("CellLine Based Dnase annotation using TShortObjectHashMap<StringBuilder> took: "
			// + (float) ((dateAfter - dateBefore) / 1000) + " seconds");
			// GlanetRunner.appendLog("**********************************************************");
			//
			// dnaseCellLineKArray = null;
			//
			// System.gc();
			// System.runFinalization();
			//
			// }
			//
			// //TShortObjectMap<String>
			// //While reading strLine.substring(indexofFirstTab + 1)
			// //While writing as it is
			// if (dnaseAnnotationType.doDnaseAnnotation()){
			//
			// // DNASE
			// TShortIntMap dnaseCellLineNumber2KMap = new TShortIntHashMap();
			//
			//
			// GlanetRunner.appendLog("**********************************************************");
			// GlanetRunner.appendLog("CellLine Based DNASE annotation using TShortObjectMap<String> starts: " + new
			// Date());
			// dateBefore = System.currentTimeMillis();
			//
			// searchDnaseWithNumbers(dataFolder,outputFolder,writeElementBasedAnnotationFoundOverlapsMode,dnaseCellLineNumber2KMap,
			// overlapDefinition, dnaseCellLineNumber2NameMap, fileNumber2NameMap);
			// writeResultsWithNumbers(dnaseCellLineNumber2KMap, dnaseCellLineNumber2NameMap, outputFolder,
			// Commons.ANNOTATION_RESULTS_FOR_DNASE);
			//
			// dateAfter = System.currentTimeMillis();
			// duration[i][3]= (float) ((dateAfter - dateBefore) / 1000) ;
			// duration[sizeofArray][3] += duration[i][3];
			//
			// GlanetRunner.appendLog("CellLine Based DNASE annotation using TShortObjectMap<String> ends: " + new
			// Date());
			//
			// GlanetRunner.appendLog("CellLine Based Dnase annotation using TShortObjectMap<String> took: " + (float)
			// ((dateAfter - dateBefore) / 1000) + " seconds");
			// GlanetRunner.appendLog("**********************************************************");
			//
			// dnaseCellLineNumber2KMap = null;
			//
			// System.gc();
			// System.runFinalization();
			//
			// }
			// /*******************************************************************************/
			// /************DNASE ANNOTATION ends**********************************************/
			// /*******************************************************************************/
			//
			// if( GlanetRunner.shouldLog())logger.info(duration[i][0] + "\t" + duration[i][1] + "\t" + duration[i][2] + "\t" + duration[i][3]);
			//
			//
			// }//End of FOR
			//
			// if( GlanetRunner.shouldLog())logger.info("Average Run Times");
			//
			// if( GlanetRunner.shouldLog())logger.info(writeElementBasedAnnotationFoundOverlapsMode.convertEnumtoString());
			//
			// if( GlanetRunner.shouldLog())logger.info(duration[sizeofArray][0]/sizeofArray+ "\t" + duration[sizeofArray][1]/sizeofArray + "\t" +
			// duration[sizeofArray][2]/sizeofArray + "\t" + duration[sizeofArray][3]/sizeofArray);

			/*******************************************************************************/
			/************ HISTONE****ANNOTATION***starts ***********************************/
			/*******************************************************************************/
			if( histoneAnnotationType.doHistoneAnnotation()){

				// Histone
				TIntIntMap histoneNumberCellLineNumber2KMap = new TIntIntHashMap();

				GlanetRunner.appendLog( "**********************************************************");
				GlanetRunner.appendLog( "CellLine Based Histone annotation starts: " + new Date());

				dateBefore = System.currentTimeMillis();
				
				searchHistoneWithNumbers(
						dataFolder, 
						outputFolder, 
						writeElementBasedAnnotationFoundOverlapsMode,
						histoneNumberCellLineNumber2KMap, 
						overlapDefinition, 
						histoneNumber2NameMap,
						cellLineNumber2NameMap, 
						fileNumber2NameMap);
				
				writeResultsWithNumbers(
						histoneNumberCellLineNumber2KMap, 
						histoneNumber2NameMap,
						cellLineNumber2NameMap, 
						outputFolder, 
						Commons.ANNOTATION_RESULTS_FOR_HISTONE);
				
				dateAfter = System.currentTimeMillis();

				GlanetRunner.appendLog( "CellLine Based Histone annotation ends: " + new Date());

				GlanetRunner.appendLog( "CellLine Based Histone annotation took: " + ( float)( ( dateAfter - dateBefore) / 1000) + " seconds");
				GlanetRunner.appendLog( "**********************************************************");

				histoneNumberCellLineNumber2KMap = null;

				System.gc();
				System.runFinalization();

			}
			/*******************************************************************************/
			/************ HISTONE*****ANNOTATION***ends ************************************/
			/*******************************************************************************/

			/*******************************************************************************/
			/************ TF******ANNOTATION******starts ***********************************/
			/*******************************************************************************/
			if( tfAnnotationType.doTFAnnotation() && 
					!( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
					!( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){

				// TF
				TIntIntMap tfNumberCellLineNumber2KMap = new TIntIntHashMap();

				GlanetRunner.appendLog( "**********************************************************");
				GlanetRunner.appendLog( "CellLine Based TF annotation starts: " + new Date());

				dateBefore = System.currentTimeMillis();
				
				searchTranscriptionFactorWithNumbers(
						dataFolder, 
						outputFolder,
						writeElementBasedAnnotationFoundOverlapsMode, 
						regulatorySequenceAnalysisUsingRSAT,
						tfNumberCellLineNumber2KMap, 
						overlapDefinition, 
						tfNumber2NameMap, 
						cellLineNumber2NameMap,
						fileNumber2NameMap);
				
				writeResultsWithNumbers(
						tfNumberCellLineNumber2KMap, 
						tfNumber2NameMap, 
						cellLineNumber2NameMap,
						outputFolder, 
						Commons.ANNOTATION_RESULTS_FOR_TF);
				
				dateAfter = System.currentTimeMillis();

				GlanetRunner.appendLog( "CellLine Based TF annotation ends: " + new Date());

				GlanetRunner.appendLog( "CellLine Based TF annotation took: " + ( float)( ( dateAfter - dateBefore) / 1000) + " seconds");
				GlanetRunner.appendLog( "**********************************************************");

				tfNumberCellLineNumber2KMap = null;

				System.gc();
				System.runFinalization();
			}
			/*******************************************************************************/
			/************ TF*******ANNOTATION*****ends *************************************/
			/*******************************************************************************/

			/*******************************************************************************/
			/************* HG19 Refseq GENE*****ANNOTATION***starts ************************/
			/*******************************************************************************/
			/************ This has been coded for Chen Yao Circulation Paper****************/
			/*******************************************************************************/
			if( geneAnnotationType.doGeneAnnotation()){

				// Hg19 RefSeq Genes
				// For Encode Collaboration Chen Yao Circulation Paper
				TIntIntMap entrezGeneId2KMap = new TIntIntHashMap();

				GlanetRunner.appendLog( "**********************************************************");
				GlanetRunner.appendLog( "Hg19 RefSeq Gene Annotation starts: " + new Date());
				dateBefore = System.currentTimeMillis();

				// 10 February 2015
				TIntObjectMap<String> givenIntervalNumber2GivenIntervalNameMap = new TIntObjectHashMap<String>();
				TIntObjectMap<OverlapInformation> givenIntervalNumber2OverlapInformationMap = new TIntObjectHashMap<OverlapInformation>();
				TIntIntMap givenIntervalNumber2NumberofGeneOverlapsMap = new TIntIntHashMap();

				// 13 February 2015
				TObjectIntMap<ChromosomeName> chromosomeName2CountMap = new TObjectIntHashMap<ChromosomeName>();

				searchGeneWithNumbers(
						dataFolder, 
						outputFolder, 
						writeElementBasedAnnotationFoundOverlapsMode,
						givenIntervalNumber2GivenIntervalNameMap, 
						givenIntervalNumber2OverlapInformationMap,
						chromosomeName2CountMap, 
						entrezGeneId2KMap, 
						overlapDefinition, 
						geneHugoSymbolNumber2NameMap,
						refSeqRNANucleotideAccessionNumber2NameMap);

				GeneOverlapAnalysisFileMode geneOverlapAnalysisFileMode = GeneOverlapAnalysisFileMode.WITH_OVERLAP_INFORMATION;

				writeGeneOverlapAnalysisFile( outputFolder,
						Commons.HG19_REFSEQ_GENE_ANNOTATION_DIRECTORY + Commons.OVERLAP_ANALYSIS_FILE,
						geneOverlapAnalysisFileMode, givenIntervalNumber2GivenIntervalNameMap,
						givenIntervalNumber2OverlapInformationMap, givenIntervalNumber2NumberofGeneOverlapsMap,
						chromosomeName2CountMap, geneHugoSymbolNumber2NameMap);

				writeResultsWithNumbers( 
						entrezGeneId2KMap, 
						geneEntrezId2GeneOfficialSymbolMap, 
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_HG19_REFSEQ_GENE_ALTERNATE_NAME);
				dateAfter = System.currentTimeMillis();

				GlanetRunner.appendLog( "Hg19 RefSeq Gene Annotation ends: " + new Date());

				GlanetRunner.appendLog( "Hg19 RefSeq Gene annotation took: " + ( float)( ( dateAfter - dateBefore) / 1000) + " seconds");
				GlanetRunner.appendLog( "**********************************************************");

				entrezGeneId2KMap = null;

				System.gc();
				System.runFinalization();

			}
			/*******************************************************************************/
			/************ This has been coded for Chen Yao Circulation Paper****************/
			/*******************************************************************************/
			/************* HG19 RefSeq GENE*****ANNOTATION***ends **************************/
			/*******************************************************************************/

			/*******************************************************************************/
			/************ KEGG PATHWAY*****ANNOTATION***starts *****************************/
			/*******************************************************************************/
			if( keggPathwayAnnotationType.doKEGGPathwayAnnotation() && 
					!( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
					!( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){

				// KEGGPathway
				TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap = new TIntObjectHashMap<TIntList>();
				KeggPathwayUtility.createNcbiGeneId2ListofKeggPathwayNumberMap(
						dataFolder,
						Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, 
						keggPathwayName2NumberMap,
						geneId2ListofKeggPathwayNumberMap);

				TIntIntMap exonBasedKeggPathway2KMap 		= new TIntIntHashMap();
				TIntIntMap regulationBasedKeggPathway2KMap 	= new TIntIntHashMap();
				TIntIntMap allBasedKeggPathway2KMap 		= new TIntIntHashMap();

				GlanetRunner.appendLog( "**********************************************************");
				GlanetRunner.appendLog( "KEGG Pathway annotation starts: " + new Date());

				dateBefore = System.currentTimeMillis();
				
				searchGeneSetWithNumbers( dataFolder, outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
						exonBasedKeggPathway2KMap, regulationBasedKeggPathway2KMap, allBasedKeggPathway2KMap,
						overlapDefinition, keggPathwayNumber2NameMap, geneId2ListofKeggPathwayNumberMap,
						geneHugoSymbolNumber2NameMap, refSeqRNANucleotideAccessionNumber2NameMap, Commons.KEGG_PATHWAY,
						GeneSetType.KEGGPATHWAY);

				writeResultsWithNumbers(
						exonBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_EXON_BASED_KEGGPATHWAY_FILE);
				writeResultsWithNumbers(
						regulationBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_REGULATION_BASED_KEGGPATHWAY_FILE);
				writeResultsWithNumbers(
						allBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_ALL_BASED_KEGGPATHWAY_FILE);

				dateAfter = System.currentTimeMillis();

				GlanetRunner.appendLog( "KEGG Pathway annotation ends: " + new Date());

				GlanetRunner.appendLog( "KEGG Pathway annotation took: " + ( float)( ( dateAfter - dateBefore) / 1000) + " seconds");
				GlanetRunner.appendLog( "**********************************************************");

				exonBasedKeggPathway2KMap = null;
				regulationBasedKeggPathway2KMap = null;
				allBasedKeggPathway2KMap = null;

				System.gc();
				System.runFinalization();

			}
			/*******************************************************************************/
			/************ KEGG PATHWAY****ANNOTATION*ends **********************************/
			/*******************************************************************************/

			/*******************************************************************************/
			/************ USER DEFINED GENESET*****ANNOTATION***starts *********************/
			/*******************************************************************************/
			if( userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){

				// UserDefinedGeneSet
				TIntIntMap exonBasedUserDefinedGeneSet2KMap = new TIntIntHashMap();
				TIntIntMap regulationBasedUserDefinedGeneSet2KMap = new TIntIntHashMap();
				TIntIntMap allBasedUserDefinedGeneSet2KMap = new TIntIntHashMap();

				GlanetRunner.appendLog( "**********************************************************");
				GlanetRunner.appendLog( "User Defined GeneSet annotation starts: " + new Date());

				dateBefore = System.currentTimeMillis();

				// Used in writing results
				TIntObjectMap<String> userDefinedGeneSetNumber2NameMap = new TIntObjectHashMap<String>();
				// Used in filling geneId2ListofUserDefinedGeneSetNumberMap
				TObjectIntMap<String> userDefinedGeneSetName2NumberMap = new TObjectIntHashMap<String>();

				// User Defined GeneSet
				// Fill userDefinedGeneSetName2UserDefinedGeneSetNumber
				// Fill userDefinedGeneSetNumber2UserDefinedGeneSetName files
				// Fill geneId2ListofUserDefinedGeneSetNumberMap
				TIntObjectMap<TIntList> geneId2ListofUserDefinedGeneSetNumberMap = new TIntObjectHashMap<TIntList>();
				
				UserDefinedGeneSetUtility.createNcbiGeneId2ListofUserDefinedGeneSetNumberMap(
						dataFolder,
						userDefinedGeneSetInputFile, 
						geneInformationType, 
						userDefinedGeneSetName2NumberMap,
						userDefinedGeneSetNumber2NameMap, 
						geneId2ListofUserDefinedGeneSetNumberMap);

				WriteAllPossibleNames.writeAllPossibleUserDefinedGeneSetNames( 
						dataFolder,
						userDefinedGeneSetName2NumberMap, 
						userDefinedGeneSetNumber2NameMap);

				searchGeneSetWithNumbers( dataFolder, outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
						exonBasedUserDefinedGeneSet2KMap, regulationBasedUserDefinedGeneSet2KMap,
						allBasedUserDefinedGeneSet2KMap, overlapDefinition, userDefinedGeneSetNumber2NameMap,
						geneId2ListofUserDefinedGeneSetNumberMap, geneHugoSymbolNumber2NameMap,
						refSeqRNANucleotideAccessionNumber2NameMap, userDefinedGeneSetName,
						GeneSetType.USERDEFINEDGENESET);

				writeResultsWithNumbers(
						exonBasedUserDefinedGeneSet2KMap,
						userDefinedGeneSetNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_USERDEFINEDGENESET_DIRECTORY  + userDefinedGeneSetName + System.getProperty( "file.separator") + Commons.ANNOTATION_RESULTS_FOR_EXON_BASED_USERDEFINEDGENESET_FILE);
				writeResultsWithNumbers(
						regulationBasedUserDefinedGeneSet2KMap,
						userDefinedGeneSetNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_USERDEFINEDGENESET_DIRECTORY + userDefinedGeneSetName + System.getProperty( "file.separator") + Commons.ANNOTATION_RESULTS_FOR_REGULATION_BASED_USERDEFINEDGENESET_FILE);
				writeResultsWithNumbers(
						allBasedUserDefinedGeneSet2KMap,
						userDefinedGeneSetNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_USERDEFINEDGENESET_DIRECTORY + userDefinedGeneSetName + System.getProperty( "file.separator") + Commons.ANNOTATION_RESULTS_FOR_ALL_BASED_USERDEFINEDGENESET_FILE);

				dateAfter = System.currentTimeMillis();

				GlanetRunner.appendLog( "User Defined GeneSet annotation ends: " + new Date());

				GlanetRunner.appendLog( "User Defined GeneSet annotation took: " + ( float)( ( dateAfter - dateBefore) / 1000) + " seconds");
				GlanetRunner.appendLog( "**********************************************************");

				exonBasedUserDefinedGeneSet2KMap = null;
				regulationBasedUserDefinedGeneSet2KMap = null;
				allBasedUserDefinedGeneSet2KMap = null;

				userDefinedGeneSetNumber2NameMap = null;
				userDefinedGeneSetName2NumberMap = null;

				System.gc();
				System.runFinalization();

			}
			/*******************************************************************************/
			/************ USER DEFINED GENESET*****ANNOTATION***ends ***********************/
			/*******************************************************************************/

			/*******************************************************************************/
			/************ USER DEFINED LIBRARY*****ANNOTATION***starts *********************/
			/*******************************************************************************/
			if( userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){

				GlanetRunner.appendLog( "**********************************************************");
				GlanetRunner.appendLog( "User Defined Library Annotation starts: " + new Date());

				dateBefore = System.currentTimeMillis();

				// used in write results
				TObjectIntMap<String> userDefinedLibraryElementType2ElementTypeNumberMap = new TObjectIntHashMap<String>();
				TIntObjectMap<String> userDefinedLibraryElementTypeNumber2ElementTypeMap = new TIntObjectHashMap<String>();

				// This has to be ElementType Specific for Bonferroni Correction
				TIntObjectMap<TObjectIntMap<String>> elementTypeNumber2ElementName2ElementNumberMapMap = new TIntObjectHashMap<TObjectIntMap<String>>();
				TIntObjectMap<TIntObjectMap<String>> elementTypeNumber2ElementNumber2ElementNameMapMap = new TIntObjectHashMap<TIntObjectMap<String>>();

				TObjectIntMap<String> userDefinedLibraryFileName2FileNumberMap = new TObjectIntHashMap<String>();
				TIntObjectMap<String> userDefinedLibraryFileNumber2FileNameMap = new TIntObjectHashMap<String>();

				// UserDefinedLibrary
				// Read UserDefinedLibraryInputFile
				// Read each file written in UserDefinedLibraryInputFile
				// FileName FileNumber
				// Fill userDefinedLibraryFileName2UserDefinedLibraryFileNumber Map
				// Fill userDefinedLibraryFileNumber2UserDefinedLibraryFileName Map
				// ElementType ElementTypeNumber
				// Fill userDefinedLibraryElementType2ElementTypeNumber Map
				// Fill userDefinedLibraryElementTypeNumber2ElementType Map
				// Fill ElementTypeNumber specific ElementName2ElementNumber
				// Fill ElementTypeNumber specific ElementNumber2ElementName
				// For each elementType
				// Create UserDefinedLibrary unsorted chromosome based interval
				// files with numbers

				UserDefinedLibraryUtility.readUserDefinedLibraryInputFileCreateUnsortedChromosomeBasedFilesWithNumbersFillMapsWriteMaps(
						dataFolder, 
						userDefinedLibraryInputFile, 
						userDefinedLibraryDataFormat,
						userDefinedLibraryElementType2ElementTypeNumberMap,
						userDefinedLibraryElementTypeNumber2ElementTypeMap,
						elementTypeNumber2ElementName2ElementNumberMapMap,
						elementTypeNumber2ElementNumber2ElementNameMapMap, 
						userDefinedLibraryFileName2FileNumberMap,
						userDefinedLibraryFileNumber2FileNameMap);

				TIntObjectMap<TIntIntMap> elementTypeNumber2ElementNumber2KMapMap = new TIntObjectHashMap<TIntIntMap>();

				// For each elementTypeNumber
				// Initialize elementNumber2KMap
				for( TIntObjectIterator<String> it = userDefinedLibraryElementTypeNumber2ElementTypeMap.iterator(); it.hasNext();){
					it.advance();
					TIntIntMap map = new TIntIntHashMap();
					elementTypeNumber2ElementNumber2KMapMap.put( it.key(), map);
				}// End of for each elementTypeNumber initialize elementNumber2KMap

				searchUserDefinedLibraryWithNumbers(
						dataFolder, 
						outputFolder,
						writeElementBasedAnnotationFoundOverlapsMode, 
						overlapDefinition,
						elementTypeNumber2ElementNumber2KMapMap, 
						elementTypeNumber2ElementNumber2ElementNameMapMap,
						userDefinedLibraryElementTypeNumber2ElementTypeMap, 
						userDefinedLibraryFileNumber2FileNameMap);

				// UserDefinedLibrary
				writeResultsWithNumbers( userDefinedLibraryElementTypeNumber2ElementTypeMap,
						elementTypeNumber2ElementNumber2KMapMap, elementTypeNumber2ElementNumber2ElementNameMapMap,
						outputFolder, Commons.ANNOTATION_RESULTS_FOR_USERDEFINEDLIBRARY_DIRECTORY,
						Commons.ANNOTATION_RESULTS_FOR_USERDEFINEDLIBRARY_FILE);

				dateAfter = System.currentTimeMillis();

				GlanetRunner.appendLog( "User Defined Library annotation ends: " + new Date());

				GlanetRunner.appendLog( "User Defined Library annotation took: " + ( float)( ( dateAfter - dateBefore) / 1000) + " seconds");
				GlanetRunner.appendLog( "**********************************************************");

				userDefinedLibraryElementType2ElementTypeNumberMap = null;
				userDefinedLibraryElementTypeNumber2ElementTypeMap = null;

				elementTypeNumber2ElementName2ElementNumberMapMap = null;
				elementTypeNumber2ElementNumber2ElementNameMapMap = null;

				userDefinedLibraryFileName2FileNumberMap = null;
				userDefinedLibraryFileNumber2FileNameMap = null;

				System.gc();
				System.runFinalization();

			}
			/*******************************************************************************/
			/************ USER DEFINED LIBRARY*****ANNOTATION***ends ***********************/
			/*******************************************************************************/

			/*******************************************************************************/
			/************ TF KEGGPATHWAY***ANNOTATION*****starts ***************************/
			/*******************************************************************************/
			if( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
					!( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){

				// KEGGPathway
				TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap = new TIntObjectHashMap<TIntList>();
				KeggPathwayUtility.createNcbiGeneId2ListofKeggPathwayNumberMap( 
						dataFolder,
						Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, 
						keggPathwayName2NumberMap,
						geneId2ListofKeggPathwayNumberMap);

				// TF
				TIntIntMap tfNumberCellLineNumber2KMap = new TIntIntHashMap();

				// KEGG Pathway
				TIntIntMap exonBasedKeggPathway2KMap = new TIntIntHashMap();
				TIntIntMap regulationBasedKeggPathway2KMap = new TIntIntHashMap();
				TIntIntMap allBasedKeggPathway2KMap = new TIntIntHashMap();

				// TF KEGGPathway
				TIntIntMap tfExonBasedKeggPathway2KMap = new TIntIntHashMap();
				TIntIntMap tfRegulationBasedKeggPathway2KMap = new TIntIntHashMap();
				TIntIntMap tfAllBasedKeggPathway2KMap = new TIntIntHashMap();

				GlanetRunner.appendLog( "**********************************************************");
				GlanetRunner.appendLog( "TF KEGGPathway annotation starts: " + new Date());

				dateBefore = System.currentTimeMillis();

				searchTfKEGGPathwayWithNumbers( 
						dataFolder, 
						outputFolder, 
						writeElementBasedAnnotationFoundOverlapsMode,
						tfNumberCellLineNumber2KMap, 
						exonBasedKeggPathway2KMap, 
						regulationBasedKeggPathway2KMap,
						allBasedKeggPathway2KMap, 
						tfExonBasedKeggPathway2KMap, 
						tfRegulationBasedKeggPathway2KMap,
						tfAllBasedKeggPathway2KMap, 
						overlapDefinition,
						tfNumber2NameMap, 
						cellLineNumber2NameMap,
						fileNumber2NameMap, 
						keggPathwayNumber2NameMap, 
						geneId2ListofKeggPathwayNumberMap,
						geneHugoSymbolNumber2NameMap, 
						refSeqRNANucleotideAccessionNumber2NameMap);

				// TF
				writeResultsWithNumbers( tfNumberCellLineNumber2KMap, tfNumber2NameMap, cellLineNumber2NameMap,
						outputFolder, Commons.ANNOTATION_RESULTS_FOR_TF);

				// KEGGPathway
				writeResultsWithNumbers(
						exonBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_EXON_BASED_KEGGPATHWAY_FILE);
				writeResultsWithNumbers(
						regulationBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_REGULATION_BASED_KEGGPATHWAY_FILE);
				writeResultsWithNumbers(
						allBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_ALL_BASED_KEGGPATHWAY_FILE);

				// TF KEGGPathway
				writeTFKEGGPathwayResultsWithNumbers(
						tfExonBasedKeggPathway2KMap, 
						tfNumber2NameMap,
						keggPathwayNumber2NameMap, 
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_EXON_BASED_KEGG_PATHWAY);
				
				writeTFKEGGPathwayResultsWithNumbers(
						tfRegulationBasedKeggPathway2KMap, 
						tfNumber2NameMap,
						keggPathwayNumber2NameMap, 
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY);
				
				writeTFKEGGPathwayResultsWithNumbers( 
						tfAllBasedKeggPathway2KMap, 
						tfNumber2NameMap,
						keggPathwayNumber2NameMap, 
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_ALL_BASED_KEGG_PATHWAY);
				
				dateAfter = System.currentTimeMillis();

				GlanetRunner.appendLog( "TF KEGGPathway annotation ends: " + new Date());
				GlanetRunner.appendLog( "TF KEGGPathway annotation took: " + ( float)( ( dateAfter - dateBefore) / 1000) + " seconds");
				GlanetRunner.appendLog( "**********************************************************");

				// null
				// TF
				tfNumberCellLineNumber2KMap = null;

				// KEGG Pathway
				exonBasedKeggPathway2KMap = null;
				regulationBasedKeggPathway2KMap = null;
				allBasedKeggPathway2KMap = null;

				// TF KEGG Pathway
				tfExonBasedKeggPathway2KMap = null;
				tfRegulationBasedKeggPathway2KMap = null;
				tfAllBasedKeggPathway2KMap = null;

				geneId2ListofKeggPathwayNumberMap = null;

				System.gc();
				System.runFinalization();

			}
			/*******************************************************************************/
			/************ TF KEGGPATHWAY*****ANNOTATION***ends *****************************/
			/*******************************************************************************/

			/*******************************************************************************/
			/************ TF CELLLINE KEGGPATHWAY***ANNOTATION*****starts ******************/
			/*******************************************************************************/
			if( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation() && 
					!( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation())){

				// KEGGPathway
				TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap = new TIntObjectHashMap<TIntList>();
				KeggPathwayUtility.createNcbiGeneId2ListofKeggPathwayNumberMap(
						dataFolder,
						Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, 
						keggPathwayName2NumberMap,
						geneId2ListofKeggPathwayNumberMap);

				// TF
				TIntIntMap tfNumberCellLineNumber2KMap = new TIntIntHashMap();

				// KEGG Pathway
				TIntIntMap exonBasedKeggPathway2KMap = new TIntIntHashMap();
				TIntIntMap regulationBasedKeggPathway2KMap = new TIntIntHashMap();
				TIntIntMap allBasedKeggPathway2KMap = new TIntIntHashMap();

				// TF CellLine KEGGPathway
				TLongIntMap tfCellLineExonBasedKeggPathway2KMap 		= new TLongIntHashMap();
				TLongIntMap tfCellLineRegulationBasedKeggPathway2KMap 	= new TLongIntHashMap();
				TLongIntMap tfCellLineAllBasedKeggPathway2KMap 			= new TLongIntHashMap();

				GlanetRunner.appendLog( "**********************************************************");
				GlanetRunner.appendLog( "CellLine Based TF KEGGPATHWAY annotation starts: " + new Date());

				dateBefore = System.currentTimeMillis();

				searchTfCellLineKEGGPathwayWithNumbers(
						dataFolder, 
						outputFolder,
						writeElementBasedAnnotationFoundOverlapsMode, 
						tfNumberCellLineNumber2KMap,
						exonBasedKeggPathway2KMap, 
						regulationBasedKeggPathway2KMap, 
						allBasedKeggPathway2KMap,
						tfCellLineExonBasedKeggPathway2KMap, 
						tfCellLineRegulationBasedKeggPathway2KMap,
						tfCellLineAllBasedKeggPathway2KMap, 
						overlapDefinition, 
						tfNumber2NameMap,
						cellLineNumber2NameMap, 
						fileNumber2NameMap, 
						keggPathwayNumber2NameMap,
						geneId2ListofKeggPathwayNumberMap, 
						geneHugoSymbolNumber2NameMap,
						refSeqRNANucleotideAccessionNumber2NameMap);

				// TF
				writeResultsWithNumbers( tfNumberCellLineNumber2KMap, tfNumber2NameMap, cellLineNumber2NameMap,
						outputFolder, Commons.ANNOTATION_RESULTS_FOR_TF);

				// KEGGPathway
				writeResultsWithNumbers(
						exonBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_EXON_BASED_KEGGPATHWAY_FILE);
				writeResultsWithNumbers(
						regulationBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_REGULATION_BASED_KEGGPATHWAY_FILE);
				writeResultsWithNumbers(
						allBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_ALL_BASED_KEGGPATHWAY_FILE);

				// TF CellLine KEGGPathway
				writeResultsWithNumbers( 
						tfCellLineExonBasedKeggPathway2KMap, 
						tfNumber2NameMap, 
						cellLineNumber2NameMap,
						keggPathwayNumber2NameMap, 
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
				
				writeResultsWithNumbers( 
						tfCellLineRegulationBasedKeggPathway2KMap, 
						tfNumber2NameMap,
						cellLineNumber2NameMap, 
						keggPathwayNumber2NameMap, 
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
				
				writeResultsWithNumbers( 
						tfCellLineAllBasedKeggPathway2KMap, 
						tfNumber2NameMap, 
						cellLineNumber2NameMap,
						keggPathwayNumber2NameMap, 
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
				
				dateAfter = System.currentTimeMillis();

				GlanetRunner.appendLog( "CellLine Based TF KEGGPATHWAY annotation ends: " + new Date());
				GlanetRunner.appendLog( "CellLine Based TF KEGGPATHWAY annotation took: " + ( float)( ( dateAfter - dateBefore) / 1000) + " seconds");
				GlanetRunner.appendLog( "**********************************************************");

				// null
				// TF
				tfNumberCellLineNumber2KMap = null;

				// KEGG Pathway
				exonBasedKeggPathway2KMap = null;
				regulationBasedKeggPathway2KMap = null;
				allBasedKeggPathway2KMap = null;

				// TF CellLine KEGGPathway
				tfCellLineExonBasedKeggPathway2KMap = null;
				tfCellLineRegulationBasedKeggPathway2KMap = null;
				tfCellLineAllBasedKeggPathway2KMap = null;

				geneId2ListofKeggPathwayNumberMap = null;

				System.gc();
				System.runFinalization();

			}
			/*******************************************************************************/
			/************ TF CELLLINE KEGGPATHWAY*****ANNOTATION***ends ********************/
			/*******************************************************************************/

			/*********************************************************************************/
			/************ Search input interval files for TF *********************************/
			/************ Search input interval files for KEGG PATHWAY ***********************/
			/************ Search input interval files for TF AND KEGG PATHWAY ****************/
			/************ Search input interval files for TF AND CELLLINE AND KEGG PATHWAY ***/
			/*********************************************************************************/
			if( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
					tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){

				// KEGGPathway
				TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap = new TIntObjectHashMap<TIntList>();
				KeggPathwayUtility.createNcbiGeneId2ListofKeggPathwayNumberMap( dataFolder,
						Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, keggPathwayName2NumberMap,
						geneId2ListofKeggPathwayNumberMap);

				// TF
				TIntIntMap tfNumberCellLineNumber2KMap = new TIntIntHashMap();

				// KEGG Pathway
				TIntIntMap exonBasedKeggPathway2KMap 		= new TIntIntHashMap();
				TIntIntMap regulationBasedKeggPathway2KMap 	= new TIntIntHashMap();
				TIntIntMap allBasedKeggPathway2KMap 		= new TIntIntHashMap();

				// TF KEGGPathway
				TIntIntMap tfExonBasedKeggPathway2KMap = new TIntIntHashMap();
				TIntIntMap tfRegulationBasedKeggPathway2KMap = new TIntIntHashMap();
				TIntIntMap tfAllBasedKeggPathway2KMap = new TIntIntHashMap();

				// TF CellLine KEGGPathway
				TLongIntMap tfCellLineExonBasedKeggPathway2KMap 		= new TLongIntHashMap();
				TLongIntMap tfCellLineRegulationBasedKeggPathway2KMap 	= new TLongIntHashMap();
				TLongIntMap tfCellLineAllBasedKeggPathway2KMap 			= new TLongIntHashMap();

				GlanetRunner.appendLog( "**********************************************************");
				GlanetRunner.appendLog( "Both TFKEGGPathway and TFCellLineKEGGPathway annotation starts: " + new Date());

				dateBefore = System.currentTimeMillis();

				searchTfandKeggPathwayWithNumbers(
						dataFolder, 
						outputFolder,
						writeElementBasedAnnotationFoundOverlapsMode, 
						geneId2ListofKeggPathwayNumberMap,
						tfNumberCellLineNumber2KMap, 
						exonBasedKeggPathway2KMap, 
						regulationBasedKeggPathway2KMap,
						allBasedKeggPathway2KMap, 
						tfCellLineExonBasedKeggPathway2KMap,
						tfCellLineRegulationBasedKeggPathway2KMap, 
						tfCellLineAllBasedKeggPathway2KMap,
						tfExonBasedKeggPathway2KMap, 
						tfRegulationBasedKeggPathway2KMap, 
						tfAllBasedKeggPathway2KMap,
						overlapDefinition, 
						tfNumber2NameMap, 
						cellLineNumber2NameMap, 
						fileNumber2NameMap,
						keggPathwayNumber2NameMap, 
						geneHugoSymbolNumber2NameMap,
						refSeqRNANucleotideAccessionNumber2NameMap);

				// TF
				writeResultsWithNumbers( tfNumberCellLineNumber2KMap, tfNumber2NameMap, cellLineNumber2NameMap,
						outputFolder, Commons.ANNOTATION_RESULTS_FOR_TF);

				// KEGGPathway
				writeResultsWithNumbers(
						exonBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_EXON_BASED_KEGGPATHWAY_FILE);
				writeResultsWithNumbers(
						regulationBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_REGULATION_BASED_KEGGPATHWAY_FILE);
				writeResultsWithNumbers(
						allBasedKeggPathway2KMap,
						keggPathwayNumber2NameMap,
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_ALL_BASED_KEGGPATHWAY_FILE);

				// TF KEGGPathway
				writeTFKEGGPathwayResultsWithNumbers( 
						tfExonBasedKeggPathway2KMap, 
						tfNumber2NameMap,
						keggPathwayNumber2NameMap, 
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_EXON_BASED_KEGG_PATHWAY);
				
				writeTFKEGGPathwayResultsWithNumbers( tfRegulationBasedKeggPathway2KMap, tfNumber2NameMap,
						keggPathwayNumber2NameMap, outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY);
				writeTFKEGGPathwayResultsWithNumbers( tfAllBasedKeggPathway2KMap, tfNumber2NameMap,
						keggPathwayNumber2NameMap, outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_ALL_BASED_KEGG_PATHWAY);

				// TF CellLine KEGGPathway
				writeResultsWithNumbers( tfCellLineExonBasedKeggPathway2KMap, tfNumber2NameMap, cellLineNumber2NameMap,
						keggPathwayNumber2NameMap, outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
				writeResultsWithNumbers( tfCellLineRegulationBasedKeggPathway2KMap, tfNumber2NameMap,
						cellLineNumber2NameMap, keggPathwayNumber2NameMap, outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
				writeResultsWithNumbers( tfCellLineAllBasedKeggPathway2KMap, tfNumber2NameMap, cellLineNumber2NameMap,
						keggPathwayNumber2NameMap, outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);

				dateAfter = System.currentTimeMillis();

				GlanetRunner.appendLog( "TFCellLineKEGGPathway and  TFKEGGPathway annotation ends: " + new Date());
				GlanetRunner.appendLog( "TFCellLineKEGGPathway and  TFKEGGPathway annotation took: " + ( float)( ( dateAfter - dateBefore) / 1000) + " seconds");
				GlanetRunner.appendLog( "**********************************************************");

				// null
				// TF
				tfNumberCellLineNumber2KMap = null;

				// KEGG Pathway
				exonBasedKeggPathway2KMap = null;
				regulationBasedKeggPathway2KMap = null;
				allBasedKeggPathway2KMap = null;

				// TF KEGGPathway
				tfExonBasedKeggPathway2KMap = null;
				tfRegulationBasedKeggPathway2KMap = null;
				tfAllBasedKeggPathway2KMap = null;

				// TF CellLine KEGGPathway
				tfCellLineExonBasedKeggPathway2KMap = null;
				tfCellLineRegulationBasedKeggPathway2KMap = null;
				tfCellLineAllBasedKeggPathway2KMap = null;

				geneId2ListofKeggPathwayNumberMap = null;

				System.gc();
				System.runFinalization();

			}
			/*********************************************************************************/
			/************ Search input interval files for TF *********************************/
			/************ Search input interval files for KEGG PATHWAY ***********************/
			/************ Search input interval files for TF AND KEGG PATHWAY ****************/
			/************ Search input interval files for TF AND CELLLINE AND KEGG PATHWAY ***/
			/*********************************************************************************/

		}
		/************************************************************************************************************/
		/********************ANNOTATION SEQUENTIALLY ENDS************************************************************/
		/************************************************************************************************************/

		/************************************************************************************************************/
		/********************FREE SPACE STARTS***********************************************************************/
		/************************************************************************************************************/
		dnaseCellLineNumber2NameMap = null;
		cellLineNumber2NameMap = null;
		fileNumber2NameMap = null;
		histoneNumber2NameMap = null;
		tfNumber2NameMap = null;
		keggPathwayNumber2NameMap = null;
		geneHugoSymbolNumber2NameMap = null;
		geneEntrezId2GeneOfficialSymbolMap = null;
		refSeqRNANucleotideAccessionNumber2NameMap = null;
		keggPathwayName2NumberMap = null;
		/************************************************************************************************************/
		/********************FREE SPACE ENDS*************************************************************************/
		/************************************************************************************************************/

		/***********************************************************************************/
		/**************Memory Usage After Annotation****************************************/
		/***********************************************************************************/
		// if( GlanetRunner.shouldLog())logger.info("Memory Used After Annotation" + "\t" +
		// ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE)
		// + "\t" + "MBs");
		/***********************************************************************************/
		/**************Memory Usage After Annotation****************************************/
		/***********************************************************************************/

	}

	// Empirical P Value Calculation
	// ExecutorService
	public AllMaps annotatPermutation( String dataFolder, Integer permutationNumber, String chrName,
			List<InputLine> inputLines, IntervalTree dnaseIntervalTree, IntervalTree tfbsIntervalTree,
			IntervalTree histoneIntervalTree, IntervalTree ucscRefSeqGeneIntervalTree) {

		AllMaps allMaps = new AllMaps();

		// DNASE
		// This dnaseCellLine2KMap hash map will contain the dnase cell line
		// name to number of dnase cell line:k for the given search input size:n
		Map<String, Integer> dnaseCellLine2KMap = new HashMap<String, Integer>();
		// Map<String,BufferedWriter> dnaseBufferedWriterHashMap = new
		// HashMap<String,BufferedWriter>();
		// searchDnase(permutationNumber,chrName,inputLines, dnaseIntervalTree,
		// dnaseBufferedWriterHashMap, dnaseCellLine2KMap);
		allMaps.setPermutationNumberDnaseCellLineName2KMap( dnaseCellLine2KMap);

		// TFBS
		// This tfbsNameandCellLineName2KMap hash map will contain the
		// tfbsNameandCellLineName to number of tfbsNameandCellLineName: k for
		// the given search input size: n
		Map<String, Integer> tfbsNameandCellLineName2KMap = new HashMap<String, Integer>();
		// Map<String,BufferedWriter> tfbsBufferedWriterHashMap = new
		// HashMap<String,BufferedWriter>();
		// searchTfbs(permutationNumber,chrName,inputLines,tfbsIntervalTree,tfbsBufferedWriterHashMap,tfbsNameandCellLineName2KMap);
		allMaps.setPermutationNumberTfNameCellLineName2KMap( tfbsNameandCellLineName2KMap);

		// HISTONE
		// This histoneNameandCellLineName2KMap hash map will contain the
		// histoneNameandCellLineName to number of histoneNameandCellLineName: k
		// for the given search input size: n
		Map<String, Integer> histoneNameandCellLineName2KMap = new HashMap<String, Integer>();
		// Map<String,BufferedWriter> histoneBufferedWriterHashMap = new
		// HashMap<String,BufferedWriter>();
		// searchHistone(permutationNumber,chrName,inputLines,histoneIntervalTree,histoneBufferedWriterHashMap,histoneNameandCellLineName2KMap);
		allMaps.setPermutationNumberHistoneNameCellLineName2KMap( histoneNameandCellLineName2KMap);

		// KEGG PATHWAY
		// Search input interval files for kegg Pathway
		Map<String, List<String>> geneId2KeggPathwayMap = new HashMap<String, List<String>>();
		KeggPathwayUtility.createNcbiGeneId2KeggPathwayMap( dataFolder,
				Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, geneId2KeggPathwayMap);

		// Exon Based Kegg Pathway Analysis
		// This exonBasedKeggPathway2KMap hash map will contain the kegg pathway
		// name to number of kegg pathway:k for the given search input size:n
		Map<String, Integer> exonBasedKeggPathway2KMap = new HashMap<String, Integer>();
		// Map<String,BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap
		// = new HashMap<String,BufferedWriter>();
		// searchUcscRefSeqGenes(permutationNumber, chrName, inputLines,
		// ucscRefSeqGeneIntervalTree,
		// exonBasedKeggPathwayBufferedWriterHashMap, geneId2KeggPathwayMap,
		// exonBasedKeggPathway2KMap,
		// Commons.NCBI_GENE_ID,Commons.EXON_BASED_KEGG_PATHWAY_ANALYSIS);
		allMaps.setPermutationNumberExonBasedKeggPathway2KMap( exonBasedKeggPathway2KMap);

		// Regulation Based Kegg Pathway Analysis
		// This regulationBasedKeggPathway2KMap hash map will contain the kegg
		// pathway name to number of kegg pathway:k for the given search input
		// size:n
		Map<String, Integer> regulationBasedKeggPathway2KMap = new HashMap<String, Integer>();
		// Map<String,BufferedWriter>
		// regulationBasedKeggPathwayBufferedWriterHashMap = new
		// HashMap<String,BufferedWriter>();
		// searchUcscRefSeqGenes(permutationNumber, chrName, inputLines,
		// ucscRefSeqGeneIntervalTree,
		// regulationBasedKeggPathwayBufferedWriterHashMap,
		// geneId2KeggPathwayMap, regulationBasedKeggPathway2KMap,
		// Commons.NCBI_GENE_ID,Commons.REGULATION_BASED_KEGG_PATHWAY_ANALYSIS);
		allMaps.setPermutationNumberRegulationBasedKeggPathway2KMap( regulationBasedKeggPathway2KMap);

		return allMaps;

	}

	// TShortObjectMap<BufferedWriter> version
	public static void closeBufferedWritersWithNumbers( TShortObjectMap<BufferedWriter> bufferedWriterHashMap) {

		BufferedWriter bufferedWriter = null;
		for( TShortObjectIterator<BufferedWriter> it = bufferedWriterHashMap.iterator(); it.hasNext();){
			it.advance();
			try{
				bufferedWriter = it.value();
				bufferedWriter.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}
	}

	// TIntObjectMap<BufferedWriter> version
	public static void closeBufferedWritersWithNumbers( TIntObjectMap<BufferedWriter> bufferedWriterHashMap) {

		BufferedWriter bufferedWriter = null;
		for( TIntObjectIterator<BufferedWriter> it = bufferedWriterHashMap.iterator(); it.hasNext();){

			it.advance();

			try{
				bufferedWriter = it.value();

				bufferedWriter.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}
	}

	// TLongObjectMap<BufferedWriter> version
	public static void closeBufferedWritersWithNumbers( TLongObjectMap<BufferedWriter> bufferedWriterHashMap) {

		BufferedWriter bufferedWriter = null;
		for( TLongObjectIterator<BufferedWriter> it = bufferedWriterHashMap.iterator(); it.hasNext();){

			it.advance();

			try{
				bufferedWriter = it.value();

				bufferedWriter.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}
	}

	// Enrichment
	// With IO
	// With Numbers
	// Tf and KeggPathway Enrichment or
	// Tf and CellLine and KeggPathway Enrichment starts
	public static AllMapsDnaseTFHistoneWithNumbers annotatePermutationWithIOWithNumbers_DnaseTFHistone(
			String outputFolder, int permutationNumber, ChromosomeName chrName,
			List<InputLineMinimal> randomlyGeneratedData, IntervalTree intervalTree, AnnotationType annotationType,
			int overlapDefinition) {

		AllMapsDnaseTFHistoneWithNumbers allMapsWithNumbers = new AllMapsDnaseTFHistoneWithNumbers();

		if( annotationType.doDnaseAnnotation()){
			// DNASE
			// This dnaseCellLine2KMap hash map will contain the dnase cell line
			// name to number of dnase cell line:k for the given search input
			// size:n
			TIntIntMap permutationNumberDnaseCellLineNumber2KMap = new TIntIntHashMap();
			TIntObjectMap<BufferedWriter> dnaseBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();
			searchDnaseWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, dnaseBufferedWriterHashMap, permutationNumberDnaseCellLineNumber2KMap,
					overlapDefinition);
			closeBufferedWritersWithNumbers( dnaseBufferedWriterHashMap);
			allMapsWithNumbers.setPermutationNumberDnaseCellLineNumber2KMap( permutationNumberDnaseCellLineNumber2KMap);

		}else if( annotationType.doTFAnnotation()){
			// TFBS
			// This tfbsNameandCellLineName2KMap hash map will contain the
			// tfbsNameandCellLineName to number of tfbsNameandCellLineName: k
			// for the given search input size: n
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap = new TLongIntHashMap();
			TLongObjectMap<BufferedWriter> tfBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			searchTfbsWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData, intervalTree,
					tfBufferedWriterHashMap, permutationNumberTfNumberCellLineNumber2KMap, overlapDefinition);
			closeBufferedWritersWithNumbers( tfBufferedWriterHashMap);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumber2KMap( permutationNumberTfNumberCellLineNumber2KMap);

		}else if( annotationType.doHistoneAnnotation()){
			// HISTONE
			// This histoneNameandCellLineName2KMap hash map will contain the
			// histoneNameandCellLineName to number of
			// histoneNameandCellLineName: k for the given search input size: n
			TLongIntMap permutationNumberHistoneNumberCellLineNumber2KMap = new TLongIntHashMap();
			TLongObjectMap<BufferedWriter> histoneBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			searchHistoneWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, histoneBufferedWriterHashMap, permutationNumberHistoneNumberCellLineNumber2KMap,
					overlapDefinition);
			closeBufferedWritersWithNumbers( histoneBufferedWriterHashMap);
			allMapsWithNumbers.setPermutationNumberHistoneNumberCellLineNumber2KMap( permutationNumberHistoneNumberCellLineNumber2KMap);

		}

		return allMapsWithNumbers;
	}

	// 29 June 2015
	// Enrichment
	// Annotate Permutation
	// With IO
	// With Numbers
	// For All Chromosomes
	public static AllMapsKeysWithNumbersAndValuesOneorZero annotatePermutationWithIOWithNumbersForAllChromosomes(
			String outputFolder, 
			int permutationNumber,
			TIntObjectMap<List<InputLineMinimal>> chrNumber2RandomlyGeneratedData,
			TIntObjectMap<IntervalTree> chrNumber2IntervalTreeMap,
			TIntObjectMap<IntervalTree> chrNumber2UcscRefSeqGenesIntervalTreeMap, 
			AnnotationType annotationType,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap, 
			int overlapDefinition,
			TIntIntMap elementNumber2OriginalKMap) {

		// For each Permutation, we have TIntByteMap
		// Key is elementNumber and Value is be 1 or 0.
		AllMapsKeysWithNumbersAndValuesOneorZero allMapsKeysWithNumbersAndValuesOneorZero = new AllMapsKeysWithNumbersAndValuesOneorZero();

		if( annotationType.doDnaseAnnotation()){

			// DNASE
			// This will be filled and set.
			TIntByteMap dnaseCellLineNumber2PermutationOneorZeroMap = new TIntByteHashMap();

			// This will be filled during search DNase Interval Trees, after setting dnaseCellLineNumber2OneorZeroMap, then it will be set to null.
			TIntIntMap dnaseCellLineNumber2PermutationKMap = new TIntIntHashMap();

			TIntObjectMap<BufferedWriter> dnaseBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();

			searchDnaseWithIOWithNumbersForAllChromosomes(
					outputFolder, 
					permutationNumber,
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap, 
					dnaseBufferedWriterHashMap,
					dnaseCellLineNumber2PermutationKMap, 
					overlapDefinition);

			closeBufferedWritersWithNumbers( dnaseBufferedWriterHashMap);

			// Fill dnaseCellLineNumber2OneorZeroMap using dnaseCellLineNumber2PermutaionKMap and dnaseCellLineNumber2OriginalKMap
			fillPermutationOneorZeroMap(
					elementNumber2OriginalKMap, 
					dnaseCellLineNumber2PermutationKMap,
					dnaseCellLineNumber2PermutationOneorZeroMap);

			// Set DnaseCellLineNumber2PermutationOneorZeroMap
			allMapsKeysWithNumbersAndValuesOneorZero.setDnaseCellLineNumber2PermutationOneorZeroMap( dnaseCellLineNumber2PermutationOneorZeroMap);

			// Free space
			dnaseCellLineNumber2PermutationKMap = null;

		}
		
		if (annotationType.doTFAnnotation()){
			
			// This will be filled during searching TF interval trees
			TIntIntMap tfNumberCellLineNumber2PermutationKMap = new TIntIntHashMap();
			
			// This will be filled using tfNumberCellLineNumber2PermutationKMap and tfNumberCellLineNumber2OriginalKMap
			TIntByteMap tfNumberCellLineNumber2PermutationOneorZeroMap = new TIntByteHashMap();

			TLongObjectMap<BufferedWriter> permutationNumberTFNumberCellLineNumberKEGGPathwayNumberBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();

			searchTForHistoneWithIOWithNumbersForAllChromosomes(
					outputFolder, 
					permutationNumber,
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap, 
					permutationNumberTFNumberCellLineNumberKEGGPathwayNumberBufferedWriterHashMap,
					tfNumberCellLineNumber2PermutationKMap, 
					overlapDefinition,
					annotationType);

			closeBufferedWritersWithNumbers(permutationNumberTFNumberCellLineNumberKEGGPathwayNumberBufferedWriterHashMap);

			// Fill tfNumberCellLineNumber2PermutationOneorZeroMap using tfNumberCellLineNumber2PermutationKMap and tfNumberCellLineNumber2OriginalKMap
			fillPermutationOneorZeroMap(
					elementNumber2OriginalKMap, 
					tfNumberCellLineNumber2PermutationKMap,
					tfNumberCellLineNumber2PermutationOneorZeroMap);

			// Set tfNumberCellLineNumber2PermutationOneorZeroMap
			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberCellLineNumber2PermutationOneorZeroMap(tfNumberCellLineNumber2PermutationOneorZeroMap);

			// Free space
			tfNumberCellLineNumber2PermutationKMap = null;	
		}
		
		if (annotationType.doHistoneAnnotation()){
			// For each permutation, this will be filled during searching histone interval trees
			TIntIntMap histoneNumberCellLineNumber2PermutationKMap = new TIntIntHashMap();
			
			// This will be filled using histoneNumberCellLineNumber2PermutationKMap and histoneNumberCellLineNumber2OriginalKMap
			TIntByteMap histoneNumberCellLineNumber2PermutationOneorZeroMap = new TIntByteHashMap();

			TLongObjectMap<BufferedWriter> permutationNumberHistoneNumberCellLineNumberKEGGPathwayNumberBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();

			searchTForHistoneWithIOWithNumbersForAllChromosomes(
					outputFolder, 
					permutationNumber,
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap, 
					permutationNumberHistoneNumberCellLineNumberKEGGPathwayNumberBufferedWriterHashMap,
					histoneNumberCellLineNumber2PermutationKMap, 
					overlapDefinition,
					annotationType);

			closeBufferedWritersWithNumbers(permutationNumberHistoneNumberCellLineNumberKEGGPathwayNumberBufferedWriterHashMap);

			// Fill histoneNumberCellLineNumber2PermutationOneorZeroMap using histoneNumberCellLineNumber2PermutationKMap and histoneNumberCellLineNumber2OriginalKMap
			fillPermutationOneorZeroMap(
					elementNumber2OriginalKMap, 
					histoneNumberCellLineNumber2PermutationKMap,
					histoneNumberCellLineNumber2PermutationOneorZeroMap);

			// Set tfNumberCellLineNumber2PermutationOneorZeroMap
			allMapsKeysWithNumbersAndValuesOneorZero.setHistoneNumberCellLineNumber2PermutationOneorZeroMap(histoneNumberCellLineNumber2PermutationOneorZeroMap);

			// Free space
			histoneNumberCellLineNumber2PermutationKMap = null;	

		}

		return allMapsKeysWithNumbersAndValuesOneorZero;

	}

	// 29 June 2015

	// Enrichment
	// With IO
	// With Numbers
	// Tf and KeggPathway Enrichment or
	// Tf and CellLine and KeggPathway Enrichment starts
	public static AllMapsWithNumbers annotatePermutationWithIOWithNumbers( String outputFolder, int permutationNumber,
			ChromosomeName chrName, List<InputLineMinimal> randomlyGeneratedData, IntervalTree intervalTree,
			IntervalTree ucscRefSeqGenesIntervalTree, AnnotationType annotationType,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap, int overlapDefinition) {

		AllMapsWithNumbers allMapsWithNumbers = new AllMapsWithNumbers();

		if( annotationType.doDnaseAnnotation()){
			// DNASE
			// This dnaseCellLine2KMap hash map will contain the dnase cell line
			// name to number of dnase cell line:k for the given search input
			// size:n
			TIntIntMap permutationNumberDnaseCellLineNumber2KMap = new TIntIntHashMap();
			TIntObjectMap<BufferedWriter> dnaseBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();
			searchDnaseWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, dnaseBufferedWriterHashMap, permutationNumberDnaseCellLineNumber2KMap,
					overlapDefinition);
			closeBufferedWritersWithNumbers( dnaseBufferedWriterHashMap);
			allMapsWithNumbers.setPermutationNumberDnaseCellLineNumber2KMap( permutationNumberDnaseCellLineNumber2KMap);

		}else if( annotationType.doTFAnnotation()){
			// TFBS
			// This tfbsNameandCellLineName2KMap hash map will contain the
			// tfbsNameandCellLineName to number of tfbsNameandCellLineName: k
			// for the given search input size: n
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap = new TLongIntHashMap();
			TLongObjectMap<BufferedWriter> tfBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			searchTfbsWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData, intervalTree,
					tfBufferedWriterHashMap, permutationNumberTfNumberCellLineNumber2KMap, overlapDefinition);
			closeBufferedWritersWithNumbers( tfBufferedWriterHashMap);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumber2KMap( permutationNumberTfNumberCellLineNumber2KMap);

		}else if( annotationType.doHistoneAnnotation()){
			// HISTONE
			// This histoneNameandCellLineName2KMap hash map will contain the
			// histoneNameandCellLineName to number of
			// histoneNameandCellLineName: k for the given search input size: n
			TLongIntMap permutationNumberHistoneNumberCellLineNumber2KMap = new TLongIntHashMap();
			TLongObjectMap<BufferedWriter> histoneBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			searchHistoneWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, histoneBufferedWriterHashMap, permutationNumberHistoneNumberCellLineNumber2KMap,
					overlapDefinition);
			closeBufferedWritersWithNumbers( histoneBufferedWriterHashMap);
			allMapsWithNumbers.setPermutationNumberHistoneNumberCellLineNumber2KMap( permutationNumberHistoneNumberCellLineNumber2KMap);

		}else if( annotationType.doUserDefinedGeneSetAnnotation()){
			// USER DEFINED GENESET
			// Search input interval files for USER DEFINED GENESET

			// Exon Based UserDefinedGeneSet Analysis
			// This exonBasedKeggPathway2KMap hash map will contain the kegg
			// pathway name to number of kegg pathway:k for the given search
			// input size:n
			TLongIntMap permutationNumberExonBasedUserDefinedGeneSetNumber2KMap = new TLongIntHashMap();
			TLongObjectMap<BufferedWriter> permutationNumberExonBasedUserDefinedGeneSetNumber2BufferedWriterMap = new TLongObjectHashMap<BufferedWriter>();
			searchUcscRefSeqGenesWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, null, permutationNumberExonBasedUserDefinedGeneSetNumber2BufferedWriterMap,
					geneId2ListofGeneSetNumberMap, null, permutationNumberExonBasedUserDefinedGeneSetNumber2KMap,
					Commons.NCBI_GENE_ID, GeneSetAnalysisType.EXONBASEDGENESETANALYSIS, GeneSetType.USERDEFINEDGENESET,
					overlapDefinition);
			closeBufferedWritersWithNumbers( permutationNumberExonBasedUserDefinedGeneSetNumber2BufferedWriterMap);
			allMapsWithNumbers.setPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap( permutationNumberExonBasedUserDefinedGeneSetNumber2KMap);

			// Regulation Based UserDefinedGeneSet Analysis
			// This regulationBasedKeggPathway2KMap hash map will contain the
			// kegg pathway name to number of kegg pathway:k for the given
			// search input size:n
			TLongIntMap permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap = new TLongIntHashMap();
			TLongObjectMap<BufferedWriter> permutationNumberRegulationBasedUserDefinedGeneSetNumber2BufferedWriterMap = new TLongObjectHashMap<BufferedWriter>();
			searchUcscRefSeqGenesWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, null, permutationNumberRegulationBasedUserDefinedGeneSetNumber2BufferedWriterMap,
					geneId2ListofGeneSetNumberMap, null, permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap,
					Commons.NCBI_GENE_ID, GeneSetAnalysisType.REGULATIONBASEDGENESETANALYSIS,
					GeneSetType.USERDEFINEDGENESET, overlapDefinition);
			closeBufferedWritersWithNumbers( permutationNumberRegulationBasedUserDefinedGeneSetNumber2BufferedWriterMap);
			allMapsWithNumbers.setPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap( permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap);

			// All Based UserDefinedGeneSet Analysis
			// This allBasedKeggPathway2KMap hash map will contain the kegg
			// pathway name to number of kegg pathway:k for the given search
			// input size:n
			TLongIntMap permutationNumberAllBasedUserDefinedGeneSetNumber2KMap = new TLongIntHashMap();
			TLongObjectMap<BufferedWriter> permutationNumberAllBasedUserDefinedGeneSetNumber2BufferedWriterMap = new TLongObjectHashMap<BufferedWriter>();
			searchUcscRefSeqGenesWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, null, permutationNumberAllBasedUserDefinedGeneSetNumber2BufferedWriterMap,
					geneId2ListofGeneSetNumberMap, null, permutationNumberAllBasedUserDefinedGeneSetNumber2KMap,
					Commons.NCBI_GENE_ID, GeneSetAnalysisType.ALLBASEDGENESETANALYSIS, GeneSetType.USERDEFINEDGENESET,
					overlapDefinition);
			closeBufferedWritersWithNumbers( permutationNumberAllBasedUserDefinedGeneSetNumber2BufferedWriterMap);
			allMapsWithNumbers.setPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap( permutationNumberAllBasedUserDefinedGeneSetNumber2KMap);

		}else if( annotationType.doUserDefinedLibraryAnnotation()){

			// USER DEFINED LIBRARY
			TLongIntMap permutationNumberElementTypeNumberElementNumber2KMap = new TLongIntHashMap();
			TLongObjectMap<BufferedWriter> permutationNumberElementTypeNumberElementNumberr2BufferedWriterMap = new TLongObjectHashMap<BufferedWriter>();
			searchUserDefinedLibraryWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, permutationNumberElementTypeNumberElementNumberr2BufferedWriterMap,
					permutationNumberElementTypeNumberElementNumber2KMap, overlapDefinition);
			closeBufferedWritersWithNumbers( permutationNumberElementTypeNumberElementNumberr2BufferedWriterMap);
			allMapsWithNumbers.setPermutationNumberElementTypeNumberElementNumber2KMap( permutationNumberElementTypeNumberElementNumber2KMap);

		}else if( annotationType.doKEGGPathwayAnnotation()){
			// KEGG PATHWAY
			// Search input interval files for kegg Pathway

			// Exon Based Kegg Pathway Analysis
			// This exonBasedKeggPathway2KMap hash map will contain the kegg
			// pathway name to number of kegg pathway:k for the given search
			// input size:n
			TIntIntMap permutationNumberExonBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			TIntObjectMap<BufferedWriter> permutationNumberExonBasedKeggPathwayNumber2BufferedWriterMap = new TIntObjectHashMap<BufferedWriter>();
			searchUcscRefSeqGenesWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, permutationNumberExonBasedKeggPathwayNumber2BufferedWriterMap, null,
					geneId2ListofGeneSetNumberMap, permutationNumberExonBasedKeggPathwayNumber2KMap, null,
					Commons.NCBI_GENE_ID, GeneSetAnalysisType.EXONBASEDGENESETANALYSIS, GeneSetType.KEGGPATHWAY,
					overlapDefinition);
			closeBufferedWritersWithNumbers( permutationNumberExonBasedKeggPathwayNumber2BufferedWriterMap);
			allMapsWithNumbers.setPermutationNumberExonBasedKeggPathwayNumber2KMap( permutationNumberExonBasedKeggPathwayNumber2KMap);

			// Regulation Based Kegg Pathway Analysis
			// This regulationBasedKeggPathway2KMap hash map will contain the
			// kegg pathway name to number of kegg pathway:k for the given
			// search input size:n
			TIntIntMap permutationNumberRegulationBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			TIntObjectMap<BufferedWriter> permutationNumberRegulationBasedKeggPathwayNumber2BufferedWriterMap = new TIntObjectHashMap<BufferedWriter>();
			searchUcscRefSeqGenesWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, permutationNumberRegulationBasedKeggPathwayNumber2BufferedWriterMap, null,
					geneId2ListofGeneSetNumberMap, permutationNumberRegulationBasedKeggPathwayNumber2KMap, null,
					Commons.NCBI_GENE_ID, GeneSetAnalysisType.REGULATIONBASEDGENESETANALYSIS, GeneSetType.KEGGPATHWAY,
					overlapDefinition);
			closeBufferedWritersWithNumbers( permutationNumberRegulationBasedKeggPathwayNumber2BufferedWriterMap);
			allMapsWithNumbers.setPermutationNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberRegulationBasedKeggPathwayNumber2KMap);

			// All Based Kegg Pathway Analysis
			// This allBasedKeggPathway2KMap hash map will contain the kegg
			// pathway name to number of kegg pathway:k for the given search
			// input size:n
			TIntIntMap permutationNumberAllBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			TIntObjectMap<BufferedWriter> permutationNumberAllBasedKeggPathwayNumber2BufferedWriterMap = new TIntObjectHashMap<BufferedWriter>();
			searchUcscRefSeqGenesWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, permutationNumberAllBasedKeggPathwayNumber2BufferedWriterMap, null,
					geneId2ListofGeneSetNumberMap, permutationNumberAllBasedKeggPathwayNumber2KMap, null,
					Commons.NCBI_GENE_ID, GeneSetAnalysisType.ALLBASEDGENESETANALYSIS, GeneSetType.KEGGPATHWAY,
					overlapDefinition);
			closeBufferedWritersWithNumbers( permutationNumberAllBasedKeggPathwayNumber2BufferedWriterMap);
			allMapsWithNumbers.setPermutationNumberAllBasedKeggPathwayNumber2KMap( permutationNumberAllBasedKeggPathwayNumber2KMap);

		}else if( annotationType.doTFKEGGPathwayAnnotation()){

			// start adding new functionality
			/***************************************************************************/
			/***************************************************************************/
			// New Functionality
			// TF and Kegg Pathway
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap = new TLongIntHashMap();

			TIntIntMap permutationNumberExonBasedKeggPathway2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberRegulationBasedKeggPathway2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberAllBasedKeggPathway2KMap = new TIntIntHashMap();

			// Will be used for tf and keggPathway enrichment
			TLongIntMap permutationNumberTfNumberExonBasedKeggPathway2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberRegulationBasedKeggPathway2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberAllBasedKeggPathway2KMap = new TLongIntHashMap();

			TLongObjectMap<BufferedWriter> tfBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			TIntObjectMap<BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();
			TIntObjectMap<BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();
			TIntObjectMap<BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();

			// Will be used for tf and keggPathway enrichment
			TLongObjectMap<BufferedWriter> tfExonBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			TLongObjectMap<BufferedWriter> tfRegulationBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			TLongObjectMap<BufferedWriter> tfAllBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();

			searchTfandKeggPathwayWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, ucscRefSeqGenesIntervalTree, tfBufferedWriterHashMap,
					exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,
					allBasedKeggPathwayBufferedWriterHashMap, tfExonBasedKeggPathwayBufferedWriterHashMap,
					tfRegulationBasedKeggPathwayBufferedWriterHashMap, tfAllBasedKeggPathwayBufferedWriterHashMap,
					null, null, null, geneId2ListofGeneSetNumberMap, permutationNumberTfNumberCellLineNumber2KMap,
					permutationNumberExonBasedKeggPathway2KMap, permutationNumberRegulationBasedKeggPathway2KMap,
					permutationNumberAllBasedKeggPathway2KMap, permutationNumberTfNumberExonBasedKeggPathway2KMap,
					permutationNumberTfNumberRegulationBasedKeggPathway2KMap,
					permutationNumberTfNumberAllBasedKeggPathway2KMap, null, null, null, Commons.NCBI_GENE_ID,
					annotationType, overlapDefinition);

			closeBufferedWritersWithNumbers( tfBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( exonBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( regulationBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( allBasedKeggPathwayBufferedWriterHashMap);

			closeBufferedWritersWithNumbers( tfExonBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( tfRegulationBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( tfAllBasedKeggPathwayBufferedWriterHashMap);

			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumber2KMap( permutationNumberTfNumberCellLineNumber2KMap);
			allMapsWithNumbers.setPermutationNumberExonBasedKeggPathwayNumber2KMap( permutationNumberExonBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberRegulationBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberAllBasedKeggPathwayNumber2KMap( permutationNumberAllBasedKeggPathway2KMap);

			allMapsWithNumbers.setPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap( permutationNumberTfNumberExonBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberTfNumberRegulationBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap( permutationNumberTfNumberAllBasedKeggPathway2KMap);

			/***************************************************************************/
			/***************************************************************************/
			// start adding new functionality
		}else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){

			// start adding new functionality
			/***************************************************************************/
			/***************************************************************************/
			// New Functionality

			// TF Enrichment
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap = new TLongIntHashMap();

			// KEGG PATHWAY Enrichment
			TIntIntMap permutationNumberExonBasedKeggPathway2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberRegulationBasedKeggPathway2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberAllBasedKeggPathway2KMap = new TIntIntHashMap();

			// TF and CELLLINE and KEGG Pathway enrichment
			TLongIntMap permutationNumberTfCellLineExonBasedKeggPathway2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfCellLineRegulationBasedKeggPathway2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfCellLineAllBasedKeggPathway2KMap = new TLongIntHashMap();

			// TF BufferedWriters
			TLongObjectMap<BufferedWriter> tfBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();

			// KEGG Pathway BufferedWriters
			TIntObjectMap<BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();
			TIntObjectMap<BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();
			TIntObjectMap<BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();

			// TF CELLINE KEGG Pathway BufferedWriters
			TLongObjectMap<BufferedWriter> tfCellLineExonBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			TLongObjectMap<BufferedWriter> tfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			TLongObjectMap<BufferedWriter> tfCellLineAllBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();

			searchTfandKeggPathwayWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, ucscRefSeqGenesIntervalTree, tfBufferedWriterHashMap,
					exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,
					allBasedKeggPathwayBufferedWriterHashMap, null, null, null,
					tfCellLineExonBasedKeggPathwayBufferedWriterHashMap,
					tfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap,
					tfCellLineAllBasedKeggPathwayBufferedWriterHashMap, geneId2ListofGeneSetNumberMap,
					permutationNumberTfNumberCellLineNumber2KMap, permutationNumberExonBasedKeggPathway2KMap,
					permutationNumberRegulationBasedKeggPathway2KMap, permutationNumberAllBasedKeggPathway2KMap, null,
					null, null, permutationNumberTfCellLineExonBasedKeggPathway2KMap,
					permutationNumberTfCellLineRegulationBasedKeggPathway2KMap,
					permutationNumberTfCellLineAllBasedKeggPathway2KMap, Commons.NCBI_GENE_ID, annotationType,
					overlapDefinition);

			closeBufferedWritersWithNumbers( tfBufferedWriterHashMap);

			closeBufferedWritersWithNumbers( exonBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( regulationBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( allBasedKeggPathwayBufferedWriterHashMap);

			closeBufferedWritersWithNumbers( tfCellLineExonBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( tfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( tfCellLineAllBasedKeggPathwayBufferedWriterHashMap);

			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumber2KMap( permutationNumberTfNumberCellLineNumber2KMap);

			allMapsWithNumbers.setPermutationNumberExonBasedKeggPathwayNumber2KMap( permutationNumberExonBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberRegulationBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberAllBasedKeggPathwayNumber2KMap( permutationNumberAllBasedKeggPathway2KMap);

			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap( permutationNumberTfCellLineExonBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberTfCellLineRegulationBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap( permutationNumberTfCellLineAllBasedKeggPathway2KMap);
			/***************************************************************************/
			/***************************************************************************/
			// start adding new functionality
		}else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

			// TF Enrichment
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap = new TLongIntHashMap();

			// KEGGPathway Enrichment
			TIntIntMap permutationNumberExonBasedKeggPathway2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberRegulationBasedKeggPathway2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberAllBasedKeggPathway2KMap = new TIntIntHashMap();

			// TF KEGGPathway Enrichment
			TLongIntMap permutationNumberTfNumberExonBasedKeggPathway2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberRegulationBasedKeggPathway2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberAllBasedKeggPathway2KMap = new TLongIntHashMap();

			// TF CELLLINE KEGGPathway Enrichment
			TLongIntMap permutationNumberTfCellLineExonBasedKeggPathway2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfCellLineRegulationBasedKeggPathway2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfCellLineAllBasedKeggPathway2KMap = new TLongIntHashMap();

			// TF BufferedWriters
			TLongObjectMap<BufferedWriter> tfBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();

			// KEGGPathway BufferedWriters
			TIntObjectMap<BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();
			TIntObjectMap<BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();
			TIntObjectMap<BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap = new TIntObjectHashMap<BufferedWriter>();

			// TF KEGGPathway BufferedWriters
			TLongObjectMap<BufferedWriter> tfExonBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			TLongObjectMap<BufferedWriter> tfRegulationBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			TLongObjectMap<BufferedWriter> tfAllBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();

			// TF CELLINE KEGGPathway BufferedWriters
			TLongObjectMap<BufferedWriter> tfCellLineExonBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			TLongObjectMap<BufferedWriter> tfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();
			TLongObjectMap<BufferedWriter> tfCellLineAllBasedKeggPathwayBufferedWriterHashMap = new TLongObjectHashMap<BufferedWriter>();

			searchTfandKeggPathwayWithIOWithNumbers( outputFolder, permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, ucscRefSeqGenesIntervalTree, tfBufferedWriterHashMap,
					exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,
					allBasedKeggPathwayBufferedWriterHashMap, tfExonBasedKeggPathwayBufferedWriterHashMap,
					tfRegulationBasedKeggPathwayBufferedWriterHashMap, tfAllBasedKeggPathwayBufferedWriterHashMap,
					tfCellLineExonBasedKeggPathwayBufferedWriterHashMap,
					tfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap,
					tfCellLineAllBasedKeggPathwayBufferedWriterHashMap, geneId2ListofGeneSetNumberMap,
					permutationNumberTfNumberCellLineNumber2KMap, permutationNumberExonBasedKeggPathway2KMap,
					permutationNumberRegulationBasedKeggPathway2KMap, permutationNumberAllBasedKeggPathway2KMap,
					permutationNumberTfNumberExonBasedKeggPathway2KMap,
					permutationNumberTfNumberRegulationBasedKeggPathway2KMap,
					permutationNumberTfNumberAllBasedKeggPathway2KMap,
					permutationNumberTfCellLineExonBasedKeggPathway2KMap,
					permutationNumberTfCellLineRegulationBasedKeggPathway2KMap,
					permutationNumberTfCellLineAllBasedKeggPathway2KMap, Commons.NCBI_GENE_ID, annotationType,
					overlapDefinition);

			// TF
			closeBufferedWritersWithNumbers( tfBufferedWriterHashMap);

			// KEGG
			closeBufferedWritersWithNumbers( exonBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( regulationBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( allBasedKeggPathwayBufferedWriterHashMap);

			// TF KEGG
			closeBufferedWritersWithNumbers( tfExonBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( tfRegulationBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( tfAllBasedKeggPathwayBufferedWriterHashMap);

			// TF CELLLINE KEGG
			closeBufferedWritersWithNumbers( tfCellLineExonBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( tfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap);
			closeBufferedWritersWithNumbers( tfCellLineAllBasedKeggPathwayBufferedWriterHashMap);

			// TF
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumber2KMap( permutationNumberTfNumberCellLineNumber2KMap);

			// KEGG
			allMapsWithNumbers.setPermutationNumberExonBasedKeggPathwayNumber2KMap( permutationNumberExonBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberRegulationBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberAllBasedKeggPathwayNumber2KMap( permutationNumberAllBasedKeggPathway2KMap);

			// TF KEGG
			allMapsWithNumbers.setPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap( permutationNumberTfNumberExonBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberTfNumberRegulationBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap( permutationNumberTfNumberAllBasedKeggPathway2KMap);

			// TF CELLLINE KEGG
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap( permutationNumberTfCellLineExonBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberTfCellLineRegulationBasedKeggPathway2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap( permutationNumberTfCellLineAllBasedKeggPathway2KMap);

		}

		return allMapsWithNumbers;

	}

	// Tf and KeggPathway Enrichment or
	// Tf and CellLine and KeggPathway Enrichment ends
	// with number ends

	// Enrichment
	// Without IO
	// With Numbers
	// Dnase TF Histone
	// Using fork join framework
	// Empirical P Value Calculation
	public static AllMapsDnaseTFHistoneWithNumbers annotatePermutationWithoutIOWithNumbers_DnaseTFHistone(
			int permutationNumber, 
			ChromosomeName chrName, 
			List<InputLineMinimal> randomlyGeneratedData,
			IntervalTree intervalTree, 
			AnnotationType annotationType, 
			AssociationMeasureType associationMeasureType,
			int overlapDefinition) {

		AllMapsDnaseTFHistoneWithNumbers allMapsWithNumbers = new AllMapsDnaseTFHistoneWithNumbers();

		if( annotationType.doDnaseAnnotation()){
			// DNASE
			// This dnaseCellLine2KMap hash map will contain the dnase cell line
			// name to number of dnase cell line:k for the given search input
			// size:n
			TIntIntMap permutationNumberDnaseCellLineNumber2KMap = new TIntIntHashMap();
			searchDnaseWithoutIOWithNumbers( 
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					permutationNumberDnaseCellLineNumber2KMap, 
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberDnaseCellLineNumber2KMap( permutationNumberDnaseCellLineNumber2KMap);

		}else if( annotationType.doTFAnnotation()){
			// TFBS
			// This tfbsNameandCellLineName2KMap hash map will contain the
			// tfbsNameandCellLineName to number of tfbsNameandCellLineName: k
			// for the given search input size: n
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap = new TLongIntHashMap();
			searchTfbsWithoutIOWithNumbers( 
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					permutationNumberTfNumberCellLineNumber2KMap, 
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumber2KMap( permutationNumberTfNumberCellLineNumber2KMap);

		}else if( annotationType.doHistoneAnnotation()){
			// HISTONE
			// This histoneNameandCellLineName2KMap hash map will contain the
			// histoneNameandCellLineName to number of
			// histoneNameandCellLineName: k for the given search input size: n
			TLongIntMap permutationNumberHistoneNumberCellLineNumber2KMap = new TLongIntHashMap();
			searchHistoneWithoutIOWithNumbers( 
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					permutationNumberHistoneNumberCellLineNumber2KMap, 
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberHistoneNumberCellLineNumber2KMap( permutationNumberHistoneNumberCellLineNumber2KMap);

		}

		return allMapsWithNumbers;
	}

	// 26 June 2015
	// Enrichment
	// Annotate each Permutation
	// Without IO
	// With Numbers
	// For All Chromosomes
	// We know the original number of overlaps for each elemennt
	// With permutation annotation we will learn the number of overlaps for each element for each permutation
	// It will return 1, if permutation has numberofOverlaps greater than equal to original number of overlaps,
	// otherwise 0.
	public static AllMapsKeysWithNumbersAndValuesOneorZero annotatePermutationWithoutIOWithNumbersForAllChromosomes(
			int permutationNumber, 
			TIntObjectMap<List<InputLineMinimal>> chrNumber2RandomlyGeneratedData,
			TIntObjectMap<IntervalTree> chrNumber2IntervalTreeMap,
			TIntObjectMap<IntervalTree> chrNumber2UcscRefSeqGenesIntervalTreeMap, 
			TIntObjectMap<TIntObjectMap<IntervalTree>> userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap,
			TIntObjectMap<String> userDefinedLibraryElementTypeNumber2ElementTypeNameMap,
			AnnotationType annotationType,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap, 
			int overlapDefinition,
			TIntIntMap elementNumber2OriginalKMap,
			TIntIntMap exonBasedGeneSetNumber2OriginalKMap,
			TIntIntMap regulationBasedGeneSetNumber2OriginalKMap,
			TIntIntMap allBasedGeneSetNumber2OriginalKMap,
			TIntIntMap userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap,
			TIntIntMap tfNumberExonBasedKEGGPathwayNumber2OriginalKMap,
			TIntIntMap tfNumberRegulationBasedKEGGPathwayNumber2OriginalKMap,
			TIntIntMap tfNumberAllBasedKEGGPathwayNumber2OriginalKMap,
			TLongIntMap tfNumberCellLineNumberExonBasedKEGGPathwayNumber2OriginalKMap,
			TLongIntMap tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2OriginalKMap,
			TLongIntMap tfNumberCellLineNumberAllBasedKEGGPathwayNumber2OriginalKMap) {

		// For each Permutation we have TIntByteMap
		// Key is elementNumber and Value is be 1 or 0.
		AllMapsKeysWithNumbersAndValuesOneorZero allMapsKeysWithNumbersAndValuesOneorZero = new AllMapsKeysWithNumbersAndValuesOneorZero();

		// DNASE
		if( annotationType.doDnaseAnnotation()){
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap dnaseCellLineNumber2PermutationOneorZeroMap = new TIntByteHashMap();

			// This will be filled during search method call, after setting dnaseCellLineNumber2OneorZeroMap, then it
			// will be set to null.
			TIntIntMap dnaseCellLineNumber2PermutationKMap = new TIntIntHashMap();

			searchDnaseWithoutIOWithNumbersForAllChromosomes( permutationNumber, chrNumber2RandomlyGeneratedData,
					chrNumber2IntervalTreeMap, dnaseCellLineNumber2PermutationKMap, overlapDefinition);

			// Fill dnaseCellLineNumber2OneorZeroMap using dnaseCellLineNumber2PermutaionKMap and
			// dnaseCellLineNumber2OriginalKMap
			fillPermutationOneorZeroMap(
					elementNumber2OriginalKMap, 
					dnaseCellLineNumber2PermutationKMap,
					dnaseCellLineNumber2PermutationOneorZeroMap);

			// Set DnaseCellLineNumber2PermutationOneorZeroMap
			allMapsKeysWithNumbersAndValuesOneorZero.setDnaseCellLineNumber2PermutationOneorZeroMap( dnaseCellLineNumber2PermutationOneorZeroMap);

			// Free space
			dnaseCellLineNumber2PermutationKMap = null;
		}// End of DNase

		// TF
		if( annotationType.doTFAnnotation()){
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap tfNumberCellLineNumber2PermutationOneorZeroMap = new TIntByteHashMap();

			// This will be filled during search method call, after setting
			// tfNumberCellLineNumber2PermutationOneorZeroMap, then it will be set to null.
			TIntIntMap tfNumberCellLineNumber2PermutationKMap = new TIntIntHashMap();

			searchTForHistoneWithoutIOWithNumbersForAllChromosomes(
					permutationNumber, 
					chrNumber2RandomlyGeneratedData,
					chrNumber2IntervalTreeMap, 
					tfNumberCellLineNumber2PermutationKMap, 
					overlapDefinition);

			// Fill dnaseCellLineNumber2OneorZeroMap using dnaseCellLineNumber2PermutaionKMap and
			// dnaseCellLineNumber2OriginalKMap
			fillPermutationOneorZeroMap(
					elementNumber2OriginalKMap, 
					tfNumberCellLineNumber2PermutationKMap,
					tfNumberCellLineNumber2PermutationOneorZeroMap);

			// Set DnaseCellLineNumber2PermutationOneorZeroMap
			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberCellLineNumber2PermutationOneorZeroMap( tfNumberCellLineNumber2PermutationOneorZeroMap);

			// Free space
			tfNumberCellLineNumber2PermutationKMap = null;
		}// End of TF
		
		//HISTONE
		if (annotationType.doHistoneAnnotation()){
			
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap histoneNumberCellLineNumber2PermutationOneorZeroMap = new TIntByteHashMap();

			// This will be filled during search method call, after setting tfNumberCellLineNumber2PermutationOneorZeroMap, then it will be set to null.
			TIntIntMap histoneNumberCellLineNumber2PermutationKMap = new TIntIntHashMap();

			searchTForHistoneWithoutIOWithNumbersForAllChromosomes(
					permutationNumber, 
					chrNumber2RandomlyGeneratedData,
					chrNumber2IntervalTreeMap, 
					histoneNumberCellLineNumber2PermutationKMap,
					overlapDefinition);

			// Fill histoneNumberCellLineNumber2PermutationOneorZeroMap using histoneNumberCellLineNumber2PermutaionKMap and histoneNumberCellLineNumber2OriginalKMap
			fillPermutationOneorZeroMap(
					elementNumber2OriginalKMap, 
					histoneNumberCellLineNumber2PermutationKMap,
					histoneNumberCellLineNumber2PermutationOneorZeroMap);

			// Set HistoneCellLineNumber2PermutationOneorZeroMap
			allMapsKeysWithNumbersAndValuesOneorZero.setHistoneNumberCellLineNumber2PermutationOneorZeroMap(histoneNumberCellLineNumber2PermutationOneorZeroMap);

			// Free space
			histoneNumberCellLineNumber2PermutationKMap = null;
		}
		
		
		//GENE
		//15 September 2015
		if (annotationType.doGeneAnnotation()){
			
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap geneID2PermutationOneorZeroMap = new TIntByteHashMap();

			// This will be filled during search method call, after setting tfNumberCellLineNumber2PermutationOneorZeroMap, then it will be set to null.
			TIntIntMap geneID2PermutationKMap = new TIntIntHashMap();

			searchUcscRefSeqGenesWithoutIOWithNumbersForAllChromosome(
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap,
					null, 
					null,
					null,
					geneID2PermutationKMap, 
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.NO_GENESET_ANALYSIS_TYPE_IS_DEFINED, 
					GeneSetType.NO_GENESET_TYPE_IS_DEFINED,
					overlapDefinition);
			
			
			// Fill geneID2PermutationOneorZeroMap using geneID2PermutationKMap and elementNumber2OriginalKMap
			fillPermutationOneorZeroMap(
					elementNumber2OriginalKMap, 
					geneID2PermutationKMap,
					geneID2PermutationOneorZeroMap);

			// Set GeneNumber2PermutationOneorZeroMap
			// Here geneNumber is geneEntrezID
			allMapsKeysWithNumbersAndValuesOneorZero.setGeneNumber2PermutationOneorZeroMap(geneID2PermutationOneorZeroMap);

			// Free space
			geneID2PermutationKMap = null;
			
		}
		

		//22 July 2015
		//UserDefinedGeneSet
		if (annotationType.doUserDefinedGeneSetAnnotation()){
			
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap exonBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap 		= new TIntByteHashMap();
			TIntByteMap regulationBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap = new TIntByteHashMap();
			TIntByteMap allBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap 		= new TIntByteHashMap();

			// This will be filled during search method call
			TIntIntMap exonBasedUserDefinedGeneSetNumber2PermutationKMap 		= new TIntIntHashMap();
			TIntIntMap regulationBasedUserDefinedGeneSetNumber2PermutationKMap 	= new TIntIntHashMap();
			TIntIntMap allBasedUserDefinedGeneSetNumber2PermutationKMap 		= new TIntIntHashMap();
			
			// EXON Based UserDefined GeneSet Analysis
			searchUcscRefSeqGenesWithoutIOWithNumbersForAllChromosome(
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap,
					geneId2ListofGeneSetNumberMap, 
					null,
					exonBasedUserDefinedGeneSetNumber2PermutationKMap, 
					null, 
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.EXONBASEDGENESETANALYSIS, 
					GeneSetType.USERDEFINEDGENESET,
					overlapDefinition);
			
			
			// REGULATION Based UserDefined GeneSet Analysis
			searchUcscRefSeqGenesWithoutIOWithNumbersForAllChromosome(
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap,
					geneId2ListofGeneSetNumberMap, 
					null,
					regulationBasedUserDefinedGeneSetNumber2PermutationKMap, 
					null,
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.REGULATIONBASEDGENESETANALYSIS, 
					GeneSetType.USERDEFINEDGENESET,
					overlapDefinition);
			
			
			// ALL Based KEGG Pathway Analysis
			searchUcscRefSeqGenesWithoutIOWithNumbersForAllChromosome(
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap,
					geneId2ListofGeneSetNumberMap, 
					null, 
					allBasedUserDefinedGeneSetNumber2PermutationKMap, 
					null,
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.ALLBASEDGENESETANALYSIS, 
					GeneSetType.USERDEFINEDGENESET,
					overlapDefinition);
			
			
			// Fill exonBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap
			// using exonBasedGeneSetNumber2OriginalKMap and exonBasedUserDefinedGeneSetNumber2PermutationKMap
			fillPermutationOneorZeroMap(
				exonBasedGeneSetNumber2OriginalKMap, 
				exonBasedUserDefinedGeneSetNumber2PermutationKMap,
				exonBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap);

			fillPermutationOneorZeroMap(
				regulationBasedGeneSetNumber2OriginalKMap, 
				regulationBasedUserDefinedGeneSetNumber2PermutationKMap,
				regulationBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap);
			
			fillPermutationOneorZeroMap(
				allBasedGeneSetNumber2OriginalKMap, 
				allBasedUserDefinedGeneSetNumber2PermutationKMap,
				allBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap);
			
			// Set elementNumber2PermutationOneorZeroMap
			allMapsKeysWithNumbersAndValuesOneorZero.setExonBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap(exonBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setRegulationBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap(regulationBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setAllBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap(allBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap);

			//Free space
			exonBasedUserDefinedGeneSetNumber2PermutationKMap 			= null;
			regulationBasedUserDefinedGeneSetNumber2PermutationKMap 	= null;
			allBasedUserDefinedGeneSetNumber2PermutationKMap 			= null;
			
		}
		
		
		//@TODO
		//UserDefinedLibrary
		if (annotationType.doUserDefinedLibraryAnnotation()){
			
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap elementTypeNumberElementNumber2PermutationOneorZeroMap 	= new TIntByteHashMap();
			
			// This will be filled during search method call
			TIntIntMap elementTypeNumberElementNumber2PermutationKMap 			= new TIntIntHashMap();
			
			searchUserDefinedLibraryWithoutIOWithNumbers(
					chrNumber2RandomlyGeneratedData,
					userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap,
					elementTypeNumberElementNumber2PermutationKMap,
					userDefinedLibraryElementTypeNumber2ElementTypeNameMap,
					overlapDefinition);
			
			fillPermutationOneorZeroMap(
					userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap, 
					elementTypeNumberElementNumber2PermutationKMap,
					elementTypeNumberElementNumber2PermutationOneorZeroMap);

			// Set elementNumber2PermutationOneorZeroMap
			allMapsKeysWithNumbersAndValuesOneorZero.setElementTypeNumberElementNumber2PermutationOneorZeroMap(elementTypeNumberElementNumber2PermutationOneorZeroMap);
			
			//Free space
			elementTypeNumberElementNumber2PermutationKMap 	= null;
		}
		
		//KEGGPathway
		if (annotationType.doKEGGPathwayAnnotation()){
			
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap exonBasedKEGGPathwayNumber2PermutationOneorZeroMap 		= new TIntByteHashMap();
			TIntByteMap regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap = new TIntByteHashMap();
			TIntByteMap allBasedKEGGPathwayNumber2PermutationOneorZeroMap = new TIntByteHashMap();

			// This will be filled during search method call
			TIntIntMap exonBasedKEGGPathwayNumber2PermutationKMap 		= new TIntIntHashMap();
			TIntIntMap regulationBasedKEGGPathwayNumber2PermutationKMap = new TIntIntHashMap();
			TIntIntMap allBasedKEGGPathwayNumber2PermutationKMap 		= new TIntIntHashMap();
			
			// EXON Based KEGG Pathway Analysis
			searchUcscRefSeqGenesWithoutIOWithNumbersForAllChromosome(
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap,
					geneId2ListofGeneSetNumberMap, 
					exonBasedKEGGPathwayNumber2PermutationKMap, 
					null, 
					null,
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.EXONBASEDGENESETANALYSIS, 
					GeneSetType.KEGGPATHWAY,
					overlapDefinition);
			
			
			// REGULATION Based KEGG Pathway Analysis
			searchUcscRefSeqGenesWithoutIOWithNumbersForAllChromosome(
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap,
					geneId2ListofGeneSetNumberMap, 
					regulationBasedKEGGPathwayNumber2PermutationKMap, 
					null, 
					null,
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.REGULATIONBASEDGENESETANALYSIS, 
					GeneSetType.KEGGPATHWAY,
					overlapDefinition);
			
			
			// ALL Based KEGG Pathway Analysis
			searchUcscRefSeqGenesWithoutIOWithNumbersForAllChromosome(
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap,
					geneId2ListofGeneSetNumberMap, 
					allBasedKEGGPathwayNumber2PermutationKMap, 
					null, 
					null,
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.ALLBASEDGENESETANALYSIS, 
					GeneSetType.KEGGPATHWAY,
					overlapDefinition);
			
			
			// Fill exonBasedKEGGPathwayNumber2PermutationOneorZeroMap using exonBasedKEGGPathwayNumber2OriginalKMap and exonBasedKEGGPathwayNumber2PermutationKMap
			fillPermutationOneorZeroMap(
				exonBasedGeneSetNumber2OriginalKMap, 
				exonBasedKEGGPathwayNumber2PermutationKMap,
				exonBasedKEGGPathwayNumber2PermutationOneorZeroMap);

			fillPermutationOneorZeroMap(
					regulationBasedGeneSetNumber2OriginalKMap, 
					regulationBasedKEGGPathwayNumber2PermutationKMap,
					regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			
			fillPermutationOneorZeroMap(
					allBasedGeneSetNumber2OriginalKMap, 
					allBasedKEGGPathwayNumber2PermutationKMap,
					allBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			
			// Set elementNumber2PermutationOneorZeroMap
			allMapsKeysWithNumbersAndValuesOneorZero.setExonBasedKeggPathwayNumber2PermutationOneorZeroMap(exonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap(regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setAllBasedKeggPathwayNumber2PermutationOneorZeroMap(allBasedKEGGPathwayNumber2PermutationOneorZeroMap);

			//Free space
			exonBasedKEGGPathwayNumber2PermutationKMap 			= null;
			regulationBasedKEGGPathwayNumber2PermutationKMap 	= null;
			allBasedKEGGPathwayNumber2PermutationKMap 			= null;
		

		}
		
		//TF KEGGPATHWAY
		if (annotationType.doTFKEGGPathwayAnnotation()){
			
			// TF
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap tfNumberCellLineNumber2PermutationOneorZeroMap = new TIntByteHashMap();
			// This will be filled during search method call, after setting
			// tfNumberCellLineNumber2PermutationOneorZeroMap, then it will be set to null.
			TIntIntMap tfNumberCellLineNumber2PermutationKMap = new TIntIntHashMap();

		
			// KEGGPATHWAY
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap exonBasedKEGGPathwayNumber2PermutationOneorZeroMap 			= new TIntByteHashMap();
			TIntByteMap regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap 	= new TIntByteHashMap();
			TIntByteMap allBasedKEGGPathwayNumber2PermutationOneorZeroMap 			= new TIntByteHashMap();
			// This will be filled during search method call
			TIntIntMap exonBasedKEGGPathwayNumber2PermutationKMap 		= new TIntIntHashMap();
			TIntIntMap regulationBasedKEGGPathwayNumber2PermutationKMap = new TIntIntHashMap();
			TIntIntMap allBasedKEGGPathwayNumber2PermutationKMap 		= new TIntIntHashMap();
				
			// TF KEGGPATHWAY
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap tfNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap			= new TIntByteHashMap();
			TIntByteMap tfNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap	= new TIntByteHashMap();
			TIntByteMap tfNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap 			= new TIntByteHashMap();
			// This will be filled during search method call
			TIntIntMap tfNumberExonBasedKEGGPathwayNumber2PermutationKMap 			= new TIntIntHashMap();
			TIntIntMap tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap 	= new TIntIntHashMap();
			TIntIntMap tfNumberAllBasedKEGGPathwayNumber2PermutationKMap 			= new TIntIntHashMap();

			// Search interval trees
			searchTfandKeggPathwayWithoutIOWithNumbersForAllChromosomes(
					permutationNumber, 
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap,
					chrNumber2UcscRefSeqGenesIntervalTreeMap,  
					geneId2ListofGeneSetNumberMap,
					tfNumberCellLineNumber2PermutationKMap,
					exonBasedKEGGPathwayNumber2PermutationKMap,
					regulationBasedKEGGPathwayNumber2PermutationKMap,
					allBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberExonBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberAllBasedKEGGPathwayNumber2PermutationKMap,
					null,
					null,
					null,
					Commons.NCBI_GENE_ID,
					annotationType, 
					overlapDefinition);
			
			
			// TF
			fillPermutationOneorZeroMap(
					elementNumber2OriginalKMap, 
					tfNumberCellLineNumber2PermutationKMap,
					tfNumberCellLineNumber2PermutationOneorZeroMap);
			
			// KEGGPathway
			fillPermutationOneorZeroMap(
					exonBasedGeneSetNumber2OriginalKMap, 
					exonBasedKEGGPathwayNumber2PermutationKMap,
					exonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			fillPermutationOneorZeroMap(
					regulationBasedGeneSetNumber2OriginalKMap, 
					regulationBasedKEGGPathwayNumber2PermutationKMap,
					regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			fillPermutationOneorZeroMap(
					allBasedGeneSetNumber2OriginalKMap, 
					allBasedKEGGPathwayNumber2PermutationKMap,
					allBasedKEGGPathwayNumber2PermutationOneorZeroMap);

			
			
			
			// TF KEGGPathway
			fillPermutationOneorZeroMap(
					tfNumberExonBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberExonBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			fillPermutationOneorZeroMap(
					tfNumberRegulationBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);				
			fillPermutationOneorZeroMap(
					tfNumberAllBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberAllBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			
			
//			//debug starts
//			checkforDebug(tfNumberExonBasedKEGGPathwayNumber2PermutationKMap);
//			checkforDebug(tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap);
//			checkforDebug(tfNumberAllBasedKEGGPathwayNumber2PermutationKMap);
//			
//			checkforDebug(tfNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
//			checkforDebug(tfNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
//			checkforDebug(tfNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap);
//			//debug ends
				
			// Set elementNumber2PermutationOneorZeroMap
			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberCellLineNumber2PermutationOneorZeroMap(tfNumberCellLineNumber2PermutationOneorZeroMap);
		
			allMapsKeysWithNumbersAndValuesOneorZero.setExonBasedKeggPathwayNumber2PermutationOneorZeroMap(exonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap(regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setAllBasedKeggPathwayNumber2PermutationOneorZeroMap(allBasedKEGGPathwayNumber2PermutationOneorZeroMap);

			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap);

			//Free space
			tfNumberCellLineNumber2PermutationKMap		= null;
			
			exonBasedKEGGPathwayNumber2PermutationKMap 			= null;
			regulationBasedKEGGPathwayNumber2PermutationKMap 	= null;
			allBasedKEGGPathwayNumber2PermutationKMap 			= null;
			
			tfNumberExonBasedKEGGPathwayNumber2PermutationKMap 		= null;
			tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap = null;
			tfNumberAllBasedKEGGPathwayNumber2PermutationKMap 		= null;

			
		}
		
		//TF CELLLINE KEGGPATHWAY 
		if (annotationType.doTFCellLineKEGGPathwayAnnotation()){
			
			// TF
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap tfNumberCellLineNumber2PermutationOneorZeroMap = new TIntByteHashMap();
			// This will be filled during search method call, after setting
			// tfNumberCellLineNumber2PermutationOneorZeroMap, then it will be set to null.
			TIntIntMap tfNumberCellLineNumber2PermutationKMap = new TIntIntHashMap();

		
			// KEGGPATHWAY
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap exonBasedKEGGPathwayNumber2PermutationOneorZeroMap 			= new TIntByteHashMap();
			TIntByteMap regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap 	= new TIntByteHashMap();
			TIntByteMap allBasedKEGGPathwayNumber2PermutationOneorZeroMap 			= new TIntByteHashMap();
			// This will be filled during search method call
			TIntIntMap exonBasedKEGGPathwayNumber2PermutationKMap 		= new TIntIntHashMap();
			TIntIntMap regulationBasedKEGGPathwayNumber2PermutationKMap = new TIntIntHashMap();
			TIntIntMap allBasedKEGGPathwayNumber2PermutationKMap 		= new TIntIntHashMap();
		
			
			// TF CELLLINE KEGGPATHWAY
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TLongByteMap tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap		= new TLongByteHashMap();
			TLongByteMap tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap	= new TLongByteHashMap();
			TLongByteMap tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap 		= new TLongByteHashMap();
			// This will be filled during search method call
			TLongIntMap tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap 		= new TLongIntHashMap();
			TLongIntMap tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap 	= new TLongIntHashMap();
			TLongIntMap tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap 		= new TLongIntHashMap();

			// Search interval trees
			searchTfandKeggPathwayWithoutIOWithNumbersForAllChromosomes(
					permutationNumber, 
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap,
					chrNumber2UcscRefSeqGenesIntervalTreeMap,  
					geneId2ListofGeneSetNumberMap,
					tfNumberCellLineNumber2PermutationKMap,
					exonBasedKEGGPathwayNumber2PermutationKMap,
					regulationBasedKEGGPathwayNumber2PermutationKMap,
					allBasedKEGGPathwayNumber2PermutationKMap,
					null,
					null,
					null,
					tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap,
					Commons.NCBI_GENE_ID,
					annotationType, 
					overlapDefinition);
			
			// TF
			fillPermutationOneorZeroMap(
					elementNumber2OriginalKMap, 
					tfNumberCellLineNumber2PermutationKMap,
					tfNumberCellLineNumber2PermutationOneorZeroMap);
			
			// KEGGPathway
			fillPermutationOneorZeroMap(
					exonBasedGeneSetNumber2OriginalKMap, 
					exonBasedKEGGPathwayNumber2PermutationKMap,
					exonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			fillPermutationOneorZeroMap(
					regulationBasedGeneSetNumber2OriginalKMap, 
					regulationBasedKEGGPathwayNumber2PermutationKMap,
					regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			fillPermutationOneorZeroMap(
					allBasedGeneSetNumber2OriginalKMap, 
					allBasedKEGGPathwayNumber2PermutationKMap,
					allBasedKEGGPathwayNumber2PermutationOneorZeroMap);

			
			// TF CellLine KEGGPathway
			fillPermutationOneorZeroMap(
					tfNumberCellLineNumberExonBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			fillPermutationOneorZeroMap(
					tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);				
			fillPermutationOneorZeroMap(
					tfNumberCellLineNumberAllBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap);
				
				// Set elementNumber2PermutationOneorZeroMap
				allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberCellLineNumber2PermutationOneorZeroMap(tfNumberCellLineNumber2PermutationOneorZeroMap);
			
				allMapsKeysWithNumbersAndValuesOneorZero.setExonBasedKeggPathwayNumber2PermutationOneorZeroMap(exonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
				allMapsKeysWithNumbersAndValuesOneorZero.setRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap(regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
				allMapsKeysWithNumbersAndValuesOneorZero.setAllBasedKeggPathwayNumber2PermutationOneorZeroMap(allBasedKEGGPathwayNumber2PermutationOneorZeroMap);

				allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
				allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
				allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap);

				//Free space
				tfNumberCellLineNumber2PermutationKMap		= null;
				
				exonBasedKEGGPathwayNumber2PermutationKMap 			= null;
				regulationBasedKEGGPathwayNumber2PermutationKMap 	= null;
				allBasedKEGGPathwayNumber2PermutationKMap 			= null;
				
				tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap 		= null;
				tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap 	= null;
				tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap 		= null;
		}
		
		
		//21 July 2015
		//BOTH TFKEGGPathway and TFCellLineKEGGPathway
		if (annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){
			
			// TF
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap tfNumberCellLineNumber2PermutationOneorZeroMap = new TIntByteHashMap();
			// This will be filled during search method call, after setting
			// tfNumberCellLineNumber2PermutationOneorZeroMap, then it will be set to null.
			TIntIntMap tfNumberCellLineNumber2PermutationKMap = new TIntIntHashMap();

		
			// KEGGPATHWAY
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap exonBasedKEGGPathwayNumber2PermutationOneorZeroMap 			= new TIntByteHashMap();
			TIntByteMap regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap 	= new TIntByteHashMap();
			TIntByteMap allBasedKEGGPathwayNumber2PermutationOneorZeroMap 			= new TIntByteHashMap();
			// This will be filled during search method call
			TIntIntMap exonBasedKEGGPathwayNumber2PermutationKMap 		= new TIntIntHashMap();
			TIntIntMap regulationBasedKEGGPathwayNumber2PermutationKMap = new TIntIntHashMap();
			TIntIntMap allBasedKEGGPathwayNumber2PermutationKMap 		= new TIntIntHashMap();
		
			// TF KEGGPATHWAY
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TIntByteMap tfNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap			= new TIntByteHashMap();
			TIntByteMap tfNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap	= new TIntByteHashMap();
			TIntByteMap tfNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap 			= new TIntByteHashMap();
			// This will be filled during search method call
			TIntIntMap tfNumberExonBasedKEGGPathwayNumber2PermutationKMap 			= new TIntIntHashMap();
			TIntIntMap tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap 	= new TIntIntHashMap();
			TIntIntMap tfNumberAllBasedKEGGPathwayNumber2PermutationKMap 			= new TIntIntHashMap();
		
			// TF CELLLINE KEGGPATHWAY
			// This will be filled and set.
			// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
			// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
			TLongByteMap tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap		= new TLongByteHashMap();
			TLongByteMap tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap	= new TLongByteHashMap();
			TLongByteMap tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap 		= new TLongByteHashMap();
			// This will be filled during search method call
			TLongIntMap tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap 		= new TLongIntHashMap();
			TLongIntMap tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap 	= new TLongIntHashMap();
			TLongIntMap tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap 		= new TLongIntHashMap();

			// Search interval trees
			searchTfandKeggPathwayWithoutIOWithNumbersForAllChromosomes(
					permutationNumber, 
					chrNumber2RandomlyGeneratedData, 
					chrNumber2IntervalTreeMap,
					chrNumber2UcscRefSeqGenesIntervalTreeMap,  
					geneId2ListofGeneSetNumberMap,
					tfNumberCellLineNumber2PermutationKMap,
					exonBasedKEGGPathwayNumber2PermutationKMap,
					regulationBasedKEGGPathwayNumber2PermutationKMap,
					allBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberExonBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberAllBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap,
					Commons.NCBI_GENE_ID,
					annotationType, 
					overlapDefinition);
			
			// TF
			fillPermutationOneorZeroMap(
					elementNumber2OriginalKMap, 
					tfNumberCellLineNumber2PermutationKMap,
					tfNumberCellLineNumber2PermutationOneorZeroMap);
			
			// KEGGPathway
			fillPermutationOneorZeroMap(
					exonBasedGeneSetNumber2OriginalKMap, 
					exonBasedKEGGPathwayNumber2PermutationKMap,
					exonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			fillPermutationOneorZeroMap(
					regulationBasedGeneSetNumber2OriginalKMap, 
					regulationBasedKEGGPathwayNumber2PermutationKMap,
					regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			fillPermutationOneorZeroMap(
					allBasedGeneSetNumber2OriginalKMap, 
					allBasedKEGGPathwayNumber2PermutationKMap,
					allBasedKEGGPathwayNumber2PermutationOneorZeroMap);

			// TF KEGGPathway
			fillPermutationOneorZeroMap(
					tfNumberExonBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberExonBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			fillPermutationOneorZeroMap(
					tfNumberRegulationBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);				
			fillPermutationOneorZeroMap(
					tfNumberAllBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberAllBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			
			// TF CellLine KEGGPathway
			fillPermutationOneorZeroMap(
					tfNumberCellLineNumberExonBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			fillPermutationOneorZeroMap(
					tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);				
			fillPermutationOneorZeroMap(
					tfNumberCellLineNumberAllBasedKEGGPathwayNumber2OriginalKMap, 
					tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap,
					tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap);
				
			// Set elementNumber2PermutationOneorZeroMap
			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberCellLineNumber2PermutationOneorZeroMap(tfNumberCellLineNumber2PermutationOneorZeroMap);
		
			allMapsKeysWithNumbersAndValuesOneorZero.setExonBasedKeggPathwayNumber2PermutationOneorZeroMap(exonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap(regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setAllBasedKeggPathwayNumber2PermutationOneorZeroMap(allBasedKEGGPathwayNumber2PermutationOneorZeroMap);

			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			
			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationOneorZeroMap);
			allMapsKeysWithNumbersAndValuesOneorZero.setTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap(tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationOneorZeroMap);

			//Free space
			tfNumberCellLineNumber2PermutationKMap		= null;
			
			exonBasedKEGGPathwayNumber2PermutationKMap 			= null;
			regulationBasedKEGGPathwayNumber2PermutationKMap 	= null;
			allBasedKEGGPathwayNumber2PermutationKMap 			= null;
			
			tfNumberExonBasedKEGGPathwayNumber2PermutationKMap 			= null;
			tfNumberRegulationBasedKEGGPathwayNumber2PermutationKMap 	= null;
			tfNumberAllBasedKEGGPathwayNumber2PermutationKMap 			= null;
			
			tfNumberCellLineNumberExonBasedKEGGPathwayNumber2PermutationKMap 		= null;
			tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2PermutationKMap 	= null;
			tfNumberCellLineNumberAllBasedKEGGPathwayNumber2PermutationKMap 		= null;
		}
		
		
		
		return allMapsKeysWithNumbersAndValuesOneorZero;
	}
	// 26 June 2015
	
	//9 July 2015
	public static void fillPermutationOneorZeroMap( 
			TLongIntMap elementNumber2OriginalKMap,
			TLongIntMap elementNumber2PermutationKMap, 
			TLongByteMap elementNumber2PermutationOneorZeroMap) {

		long elementNumber;
		int originalNumberofOverlaps;
		Integer permutationNumberofOverlaps;

		for( TLongIntIterator itr = elementNumber2OriginalKMap.iterator(); itr.hasNext();){

			itr.advance();

			elementNumber = itr.key();
			originalNumberofOverlaps = itr.value();

			permutationNumberofOverlaps = elementNumber2PermutationKMap.get( elementNumber);

			if( permutationNumberofOverlaps != null){

				if( permutationNumberofOverlaps >= originalNumberofOverlaps){
					elementNumber2PermutationOneorZeroMap.put( elementNumber, Commons.BYTE_1);
				}else{
					elementNumber2PermutationOneorZeroMap.put( elementNumber, Commons.BYTE_0);
				}// End of IF

			}// End of IF permutationNumberofOverlaps is not NULL

			else{
				elementNumber2PermutationOneorZeroMap.put( elementNumber, Commons.BYTE_0);
			}// End of ELSE permutationNumberofOverlaps is NULL

		}// End of FOR each elementNumber in elementNumber2OriginalKMap

	}
	//9 July 2015
	
	//16 July 2015
	//Method for Debug 
	public static void checkforDebug(
			TIntIntMap tfNumberKEGGPathwayNumber2PermutationKMap){
		
		int tfNumberKEGGPathwayNumber  = Integer.MIN_VALUE;
		int KEGGPathwayNumber = Integer.MIN_VALUE;
		
		
		for(TIntIntIterator itr = tfNumberKEGGPathwayNumber2PermutationKMap.iterator(); itr.hasNext();){
			
			itr.advance();
			
			tfNumberKEGGPathwayNumber = itr.key();
			
			KEGGPathwayNumber = tfNumberKEGGPathwayNumber % 100000;
			
			if (KEGGPathwayNumber > 269){
				System.out.println("There is a situation90, KEGGPathwayNumber: " + KEGGPathwayNumber);
			}
			
		}
		
	}
	
	
	//16 July 2015
	//Method for Debug 
	public static void checkforDebug(
			TIntByteMap tfNumberKEGGPathwayNumber2PermutationOneorZeroMap){
		
		int tfNumberKEGGPathwayNumber  = Integer.MIN_VALUE;
		int KEGGPathwayNumber = Integer.MIN_VALUE;
		
		
		for(TIntByteIterator itr = tfNumberKEGGPathwayNumber2PermutationOneorZeroMap.iterator(); itr.hasNext();){
			
			itr.advance();
			
			tfNumberKEGGPathwayNumber = itr.key();
			
			KEGGPathwayNumber = tfNumberKEGGPathwayNumber % 100000;
			
			if (KEGGPathwayNumber > 269){
				System.out.println("There is a situation100, KEGGPathwayNumber: " + KEGGPathwayNumber);
			}
			
		}//End of for
		
	}

	// We must consider each of the elementNumber
	public static void fillPermutationOneorZeroMap( 
			TIntIntMap elementNumber2OriginalKMap,
			TIntIntMap elementNumber2PermutationKMap, 
			TIntByteMap elementNumber2PermutationOneorZeroMap) {

		int elementNumber;
		int originalNumberofOverlaps;
		Integer permutationNumberofOverlaps;

		for( TIntIntIterator itr = elementNumber2OriginalKMap.iterator(); itr.hasNext();){

			itr.advance();

			elementNumber = itr.key();
			originalNumberofOverlaps = itr.value();
			
			permutationNumberofOverlaps = elementNumber2PermutationKMap.get( elementNumber);

			if( permutationNumberofOverlaps != null){

				if( permutationNumberofOverlaps >= originalNumberofOverlaps){
					elementNumber2PermutationOneorZeroMap.put( elementNumber, Commons.BYTE_1);
				}else{
					elementNumber2PermutationOneorZeroMap.put( elementNumber, Commons.BYTE_0);
				}// End of IF

			}// End of IF permutationNumberofOverlaps is not NULL

			else{
				elementNumber2PermutationOneorZeroMap.put( elementNumber, Commons.BYTE_0);
			}// End of ELSE permutationNumberofOverlaps is NULL

		}// End of FOR each elementNumber in elementNumber2OriginalKMap

	}

	// Enrichment
	// Without IO
	// With Numbers
	// TF_AND_KEGGPATHWAY_ENRICHMENT OR
	// TF_AND_CELLLINE_AND_KEGGPATHWAY_ENRICHMENT
	// OR BOTH enrichment at the same time
	// each time one interval tree EXCEPT NEW FUNCTIONALITY
	// Using fork join framework
	// Empirical P Value Calculation
	public static AllMapsWithNumbers annotatePermutationWithoutIOWithNumbers(
			int permutationNumber,
			ChromosomeName chrName, 
			List<InputLineMinimal> randomlyGeneratedData, 
			IntervalTree intervalTree,
			IntervalTree ucscRefSeqGenesIntervalTree, 
			AnnotationType annotationType,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap, 
			AssociationMeasureType associationMeasureType,
			int overlapDefinition) {

		AllMapsWithNumbers allMapsWithNumbers = new AllMapsWithNumbers();

		if( annotationType.doDnaseAnnotation()){
			// DNASE
			// This dnaseCellLine2KMap hash map will contain the dnase cell line
			// name to number of dnase cell line:k for the given search input size:n
			TIntIntMap permutationNumberDnaseCellLineNumber2KMap = new TIntIntHashMap();
			searchDnaseWithoutIOWithNumbers(
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					permutationNumberDnaseCellLineNumber2KMap, 
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberDnaseCellLineNumber2KMap( permutationNumberDnaseCellLineNumber2KMap);

		}else if( annotationType.doTFAnnotation()){
			// TFBS
			// This tfbsNameandCellLineName2KMap hash map will contain the
			// tfbsNameandCellLineName to number of tfbsNameandCellLineName: k for the given search input size: n
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap = new TLongIntHashMap();
			searchTfbsWithoutIOWithNumbers(
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					permutationNumberTfNumberCellLineNumber2KMap, 
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumber2KMap( permutationNumberTfNumberCellLineNumber2KMap);

		}else if( annotationType.doHistoneAnnotation()){
			// HISTONE
			// This histoneNameandCellLineName2KMap hash map will contain the
			// histoneNameandCellLineName to number of
			// histoneNameandCellLineName: k for the given search input size: n
			TLongIntMap permutationNumberHistoneNumberCellLineNumber2KMap = new TLongIntHashMap();
			searchHistoneWithoutIOWithNumbers(
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					permutationNumberHistoneNumberCellLineNumber2KMap, 
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberHistoneNumberCellLineNumber2KMap( permutationNumberHistoneNumberCellLineNumber2KMap);

		}else if( annotationType.doGeneAnnotation()){

			// Gene
			TLongIntMap permutationNumberGeneNumber2KMap = new TLongIntHashMap();
			// searchGeneWithoutIOWithNumbers(permutationNumber, chrName, randomlyGeneratedData, intervalTree,
			// permutationNumberGeneNumber2KMap, overlapDefinition);
			searchUcscRefSeqGenesWithoutIOWithNumbers(
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					null, 
					null, 
					null, 
					permutationNumberGeneNumber2KMap, 
					Commons.NCBI_GENE_ID,
					GeneSetAnalysisType.NO_GENESET_ANALYSIS_TYPE_IS_DEFINED, 
					GeneSetType.NO_GENESET_TYPE_IS_DEFINED,
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberGeneNumber2KMap( permutationNumberGeneNumber2KMap);

		}

		else if( annotationType.doUserDefinedGeneSetAnnotation()){

			// USER DEFINED GENESET
			// Search input interval files for USER DEFINED GENESET

			// Exon Based USER DEFINED GENESET Analysis
			// This exonBasedUserDefinedGeneSet2KMap hash map will contain the
			// kegg pathway name to number of kegg pathway:k for the given
			// search input size:n
			TLongIntMap permutationNumberExonBasedUserDefinedGeneSetNumber2KMap = new TLongIntHashMap();
			searchUcscRefSeqGenesWithoutIOWithNumbers(
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					geneId2ListofGeneSetNumberMap, 
					null, 
					permutationNumberExonBasedUserDefinedGeneSetNumber2KMap, 
					null,
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.EXONBASEDGENESETANALYSIS, 
					GeneSetType.USERDEFINEDGENESET,
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap( permutationNumberExonBasedUserDefinedGeneSetNumber2KMap);

			// Regulation Based USER DEFINED GENESET Analysis
			// This regulationBasedUserDefinedGeneSet2KMap hash map will contain
			// the kegg pathway name to number of kegg pathway:k for the given
			// search input size:n
			TLongIntMap permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap = new TLongIntHashMap();
			searchUcscRefSeqGenesWithoutIOWithNumbers( 
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					geneId2ListofGeneSetNumberMap, 
					null, 
					permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap,
					null, 
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.REGULATIONBASEDGENESETANALYSIS,
					GeneSetType.USERDEFINEDGENESET,
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap( permutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap);

			// All Based USER DEFINED GENESET Analysis
			// This allBasedUserDefinedGeneSet2KMap hash map will contain the
			// kegg pathway name to number of kegg pathway:k for the given
			// search input size:n
			TLongIntMap permutationNumberAllBasedUserDefinedGeneSetNumber2KMap = new TLongIntHashMap();
			searchUcscRefSeqGenesWithoutIOWithNumbers(
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					geneId2ListofGeneSetNumberMap, 
					null, 
					permutationNumberAllBasedUserDefinedGeneSetNumber2KMap, 
					null,
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.ALLBASEDGENESETANALYSIS,
					GeneSetType.USERDEFINEDGENESET,
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap( permutationNumberAllBasedUserDefinedGeneSetNumber2KMap);

		}else if( annotationType.doUserDefinedLibraryAnnotation()){
			// USER DEFINED LIBRARY
			TLongIntMap permutationNumberElementTypeNumberElementNumber2KMap = new TLongIntHashMap();
			searchUserDefinedLibraryWithoutIOWithNumbers(
					permutationNumber, 
					chrName, 
					randomlyGeneratedData,
					intervalTree, 
					permutationNumberElementTypeNumberElementNumber2KMap,
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberElementTypeNumberElementNumber2KMap( permutationNumberElementTypeNumberElementNumber2KMap);

		}else if( annotationType.doKEGGPathwayAnnotation()){
			// KEGG PATHWAY
			// Search input interval files for kegg Pathway

			// Exon Based Kegg Pathway Analysis
			// This exonBasedKeggPathway2KMap hash map will contain the kegg
			// pathway name to number of kegg pathway:k for the given search
			// input size:n
			TIntIntMap permutationNumberExonBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			searchUcscRefSeqGenesWithoutIOWithNumbers(
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					geneId2ListofGeneSetNumberMap, 
					permutationNumberExonBasedKeggPathwayNumber2KMap, 
					null, 
					null,
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.EXONBASEDGENESETANALYSIS, 
					GeneSetType.KEGGPATHWAY,
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberExonBasedKeggPathwayNumber2KMap( permutationNumberExonBasedKeggPathwayNumber2KMap);

			// Regulation Based Kegg Pathway Analysis
			// This regulationBasedKeggPathway2KMap hash map will contain the
			// kegg pathway name to number of kegg pathway:k for the given
			// search input size:n
			TIntIntMap permutationNumberRegulationBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			searchUcscRefSeqGenesWithoutIOWithNumbers(
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					geneId2ListofGeneSetNumberMap, 
					permutationNumberRegulationBasedKeggPathwayNumber2KMap, 
					null, 
					null,
					Commons.NCBI_GENE_ID, 
					GeneSetAnalysisType.REGULATIONBASEDGENESETANALYSIS, 
					GeneSetType.KEGGPATHWAY,
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberRegulationBasedKeggPathwayNumber2KMap);

			// All Based Kegg Pathway Analysis
			// This allBasedKeggPathway2KMap hash map will contain the kegg
			// pathway name to number of kegg pathway:k for the given search
			// input size:n
			TIntIntMap permutationNumberAllBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			searchUcscRefSeqGenesWithoutIOWithNumbers(
					permutationNumber, 
					chrName, 
					randomlyGeneratedData, 
					intervalTree,
					geneId2ListofGeneSetNumberMap, 
					permutationNumberAllBasedKeggPathwayNumber2KMap, 
					null, 
					null,
					Commons.NCBI_GENE_ID,
					GeneSetAnalysisType.ALLBASEDGENESETANALYSIS, 
					GeneSetType.KEGGPATHWAY,
					associationMeasureType,
					overlapDefinition);
			allMapsWithNumbers.setPermutationNumberAllBasedKeggPathwayNumber2KMap( permutationNumberAllBasedKeggPathwayNumber2KMap);

		}else if( annotationType.doTFKEGGPathwayAnnotation()){

			/***************************************************************************/
			/***************************************************************************/
			// New Functionality
			// TF
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap = new TLongIntHashMap();

			// KEGG
			TIntIntMap permutationNumberExonBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberRegulationBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberAllBasedKeggPathwayNumber2KMap = new TIntIntHashMap();

			// TF KEGG
			TLongIntMap permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap = new TLongIntHashMap();

			searchTfandKeggPathwayWithoutIOWithNumbers( permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, ucscRefSeqGenesIntervalTree, geneId2ListofGeneSetNumberMap,
					permutationNumberTfNumberCellLineNumber2KMap, permutationNumberExonBasedKeggPathwayNumber2KMap,
					permutationNumberRegulationBasedKeggPathwayNumber2KMap,
					permutationNumberAllBasedKeggPathwayNumber2KMap,
					permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap,
					permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap,
					permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap, null, null, null, Commons.NCBI_GENE_ID,
					annotationType, overlapDefinition);

			// TF
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumber2KMap( permutationNumberTfNumberCellLineNumber2KMap);

			// KEGG
			allMapsWithNumbers.setPermutationNumberExonBasedKeggPathwayNumber2KMap( permutationNumberExonBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberRegulationBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberAllBasedKeggPathwayNumber2KMap( permutationNumberAllBasedKeggPathwayNumber2KMap);

			// TF KEGG
			allMapsWithNumbers.setPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap( permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap( permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap);
			/***************************************************************************/
			/***************************************************************************/

		}else if( annotationType.doTFCellLineKEGGPathwayAnnotation()){

			/***************************************************************************/
			/***************************************************************************/
			// New Functionality
			// TF
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap = new TLongIntHashMap();

			// KEGG
			TIntIntMap permutationNumberExonBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberRegulationBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberAllBasedKeggPathwayNumber2KMap = new TIntIntHashMap();

			// TF CELLINE KEGG
			TLongIntMap permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap = new TLongIntHashMap();

			searchTfandKeggPathwayWithoutIOWithNumbers(
					permutationNumber, 
					chrName, 
					randomlyGeneratedData,
					intervalTree, 
					ucscRefSeqGenesIntervalTree, 
					geneId2ListofGeneSetNumberMap,
					permutationNumberTfNumberCellLineNumber2KMap, 
					permutationNumberExonBasedKeggPathwayNumber2KMap,
					permutationNumberRegulationBasedKeggPathwayNumber2KMap,
					permutationNumberAllBasedKeggPathwayNumber2KMap, 
					null, 
					null, 
					null,
					permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap,
					permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap,
					permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap, 
					Commons.NCBI_GENE_ID,
					annotationType, 
					overlapDefinition);

			// TF
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumber2KMap( permutationNumberTfNumberCellLineNumber2KMap);

			// KEGG
			allMapsWithNumbers.setPermutationNumberExonBasedKeggPathwayNumber2KMap( permutationNumberExonBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberRegulationBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberAllBasedKeggPathwayNumber2KMap( permutationNumberAllBasedKeggPathwayNumber2KMap);

			// TF CELLLINE KEGG
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap( permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap( permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap);
			/***************************************************************************/
			/***************************************************************************/

		}else if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

			// TF
			TLongIntMap permutationNumberTfNumberCellLineNumber2KMap = new TLongIntHashMap();

			// KEGG
			TIntIntMap permutationNumberExonBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberRegulationBasedKeggPathwayNumber2KMap = new TIntIntHashMap();
			TIntIntMap permutationNumberAllBasedKeggPathwayNumber2KMap = new TIntIntHashMap();

			// TF KEGG
			TLongIntMap permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap = new TLongIntHashMap();

			// TF CELLLINE KEGG
			TLongIntMap permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap = new TLongIntHashMap();
			TLongIntMap permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap = new TLongIntHashMap();

			searchTfandKeggPathwayWithoutIOWithNumbers( permutationNumber, chrName, randomlyGeneratedData,
					intervalTree, ucscRefSeqGenesIntervalTree, geneId2ListofGeneSetNumberMap,
					permutationNumberTfNumberCellLineNumber2KMap, permutationNumberExonBasedKeggPathwayNumber2KMap,
					permutationNumberRegulationBasedKeggPathwayNumber2KMap,
					permutationNumberAllBasedKeggPathwayNumber2KMap,
					permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap,
					permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap,
					permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap,
					permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap,
					permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap,
					permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap, Commons.NCBI_GENE_ID,
					annotationType, overlapDefinition);

			// TF
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumber2KMap( permutationNumberTfNumberCellLineNumber2KMap);

			// KEGG
			allMapsWithNumbers.setPermutationNumberExonBasedKeggPathwayNumber2KMap( permutationNumberExonBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberRegulationBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberAllBasedKeggPathwayNumber2KMap( permutationNumberAllBasedKeggPathwayNumber2KMap);

			// TF KEGG
			allMapsWithNumbers.setPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap( permutationNumberTfNumberExonBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap( permutationNumberTfNumberAllBasedKeggPathwayNumber2KMap);

			// TF CELLINE KEGG
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap( permutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap( permutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap);
			allMapsWithNumbers.setPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap( permutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap);

		}

		return allMapsWithNumbers;

	}

	// with numbers ends

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
	// args[29] ---> UserDefinedLibrary DataFormat
	// default
	// Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	// Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	// Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// args[30] - args[args.length-1] ---> Note that the selected cell lines are
	// always inserted at the end of the args array because it's size
	// is not fixed. So for not (until the next change on args array) the
	// selected cell
	// lines can be reached starting from 22th index up until (args.length-1)th
	// index.
	// If no cell line selected so the args.length-1 will be 22-1 = 21. So it
	// will never
	// give an out of boundry exception in a for loop with this approach.
	public static void main( String[] args) {
		
		String glanetRunType = args[CommandLineArguments.GLANETRun.value()];
		
		//Annotate GLANET normal run, do not annotate GLANET Data Driven Experiment run
		if(!glanetRunType.equalsIgnoreCase(Commons.GLANET_COMMANDLINE_DATADRIVENEXPERIMENT_RUN)){
			Annotation annotateIntervals = new Annotation();
			annotateIntervals.annotate( args);
		}//End of IF

	
	}

}