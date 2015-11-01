/**
 * @author burcakotlu
 * @date May 9, 2014 
 * @time 10:45:02 AM
 */
package enrichment;

import enumtypes.AnnotationType;
import enumtypes.AssociationMeasureType;
import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import enumtypes.EnrichmentPermutationDivisionType;
import enumtypes.EnrichmentZScoreMode;
import enumtypes.GeneInformationType;
import enumtypes.GenerateRandomDataMode;
import enumtypes.GeneratedMixedNumberDescriptionOrderLength;
import enumtypes.GivenInputDataType;
import enumtypes.IsochoreFamily;
import enumtypes.WriteGeneratedRandomDataMode;
import enumtypes.WritePermutationBasedAnnotationResultMode;
import enumtypes.WritePermutationBasedandParametricBasedAnnotationResultMode;
import gc.ChromosomeBasedGCIntervalTree;
import gc.ChromosomeBasedGCTroveList;
import generate.randomdata.RandomDataGenerator;
import gnu.trove.iterator.TIntByteIterator;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TLongByteIterator;
import gnu.trove.iterator.TLongIntIterator;
import gnu.trove.list.TByteList;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TShortArrayList;
import gnu.trove.map.TIntByteMap;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongByteMap;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import hg19.GRCh37Hg19Chromosome;
import intervaltree.Interval;
import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

import keggpathway.ncbigenes.KeggPathwayUtility;
import mapability.ChromosomeBasedMappabilityTroveList;

import org.apache.log4j.Logger;

import ui.GlanetRunner;
import userdefined.geneset.UserDefinedGeneSetUtility;
import userdefined.library.UserDefinedLibraryUtility;
import annotation.Annotation;
import augmentation.humangenes.HumanGenesAugmentation;
import auxiliary.FileOperations;
import auxiliary.FunctionalElement;
import common.Commons;

/**
 * Annotate Permutations With Numbers With Choices
 */
public class Enrichment {

	final static Logger logger = Logger.getLogger(Enrichment.class);

	// Static Nested Class starts
	static class GenerateRandomData extends RecursiveTask<TIntObjectMap<List<InputLineMinimal>>> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5508399455444935122L;
		private final int chromSize;
		private final ChromosomeName chromName;
		private final List<InputLineMinimal> chromosomeBasedOriginalInputLines;

		private final GenerateRandomDataMode generateRandomDataMode;
		private final WriteGeneratedRandomDataMode writeGeneratedRandomDataMode;

		private final int lowIndex;
		private final int highIndex;

		private final TIntList permutationNumberList;

		// private final GCCharArray gcCharArray;
		private final GivenInputDataType givenInputsSNPsorIntervals;

		private final TByteList gcByteList;
		private final IntervalTree gcIntervalTree;
		private final IntervalTree gcIsochoreIntervalTree;

		private final List<Interval> gcIsochoreFamilyL1Pool;
		private final List<Interval> gcIsochoreFamilyL2Pool;
		private final List<Interval> gcIsochoreFamilyH1Pool;
		private final List<Interval> gcIsochoreFamilyH2Pool;
		private final List<Interval> gcIsochoreFamilyH3Pool;

		// private final IntervalTree mapabilityIntervalTree;

		// private final MapabilityFloatArray mapabilityFloatArray;
		private final TIntList mapabilityChromosomePositionList;
		private final TShortList mapabilityShortValueList;
		// private final TByteList mapabilityByteValueList;

		private final String outputFolder;

		public GenerateRandomData( String outputFolder, int chromSize, ChromosomeName chromName,
				List<InputLineMinimal> chromosomeBasedOriginalInputLines,
				GenerateRandomDataMode generateRandomDataMode,
				WriteGeneratedRandomDataMode writeGeneratedRandomDataMode, int lowIndex, int highIndex,
				TIntList permutationNumberList, GivenInputDataType givenInputsSNPsorIntervals, TByteList gcByteList,
				IntervalTree gcIntervaTree, IntervalTree gcIsochoreIntervalTree, List<Interval> gcIsochoreFamilyL1Pool,
				List<Interval> gcIsochoreFamilyL2Pool, List<Interval> gcIsochoreFamilyH1Pool,
				List<Interval> gcIsochoreFamilyH2Pool, List<Interval> gcIsochoreFamilyH3Pool,
				// IntervalTree mapabilityIntervalTree
				TIntList mapabilityChromosomePositionList, TShortList mapabilityShortValueList
		// TByteList mapabilityByteValueList
		) {

			this.outputFolder = outputFolder;

			this.chromSize = chromSize;
			this.chromName = chromName;
			this.chromosomeBasedOriginalInputLines = chromosomeBasedOriginalInputLines;

			this.generateRandomDataMode = generateRandomDataMode;
			this.writeGeneratedRandomDataMode = writeGeneratedRandomDataMode;

			this.lowIndex = lowIndex;
			this.highIndex = highIndex;

			this.permutationNumberList = permutationNumberList;

			// EnumType
			this.givenInputsSNPsorIntervals = givenInputsSNPsorIntervals;

			// For Commons.VERY_SHORT_INTERVAL_LENGTH
			this.gcByteList = gcByteList;
			// For Commons.SHORT_INTERVAL_LENGTH
			this.gcIntervalTree = gcIntervaTree;

			this.gcIsochoreIntervalTree = gcIsochoreIntervalTree;

			this.gcIsochoreFamilyL1Pool = gcIsochoreFamilyL1Pool;
			this.gcIsochoreFamilyL2Pool = gcIsochoreFamilyL2Pool;
			this.gcIsochoreFamilyH1Pool = gcIsochoreFamilyH1Pool;
			this.gcIsochoreFamilyH2Pool = gcIsochoreFamilyH2Pool;
			this.gcIsochoreFamilyH3Pool = gcIsochoreFamilyH3Pool;

			this.mapabilityChromosomePositionList = mapabilityChromosomePositionList;
			this.mapabilityShortValueList = mapabilityShortValueList;

			// this.mapabilityByteValueList =mapabilityByteValueList;
			// this.mapabilityIntervalTree = mapabilityIntervalTree;

		}

		protected TIntObjectMap<List<InputLineMinimal>> compute() {

			int middleIndex;
			TIntObjectMap<List<InputLineMinimal>> rightRandomlyGeneratedData;
			TIntObjectMap<List<InputLineMinimal>> leftRandomlyGeneratedData;

			Integer permutationNumber;

			// DIVIDE
			if( highIndex - lowIndex > Commons.NUMBER_OF_GENERATE_RANDOM_DATA_TASK_DONE_IN_SEQUENTIALLY){
				middleIndex = lowIndex + ( highIndex - lowIndex) / 2;
				GenerateRandomData left = new GenerateRandomData( outputFolder, chromSize, chromName,
						chromosomeBasedOriginalInputLines, generateRandomDataMode, writeGeneratedRandomDataMode,
						lowIndex, middleIndex, permutationNumberList, givenInputsSNPsorIntervals, gcByteList,
						gcIntervalTree, gcIsochoreIntervalTree, gcIsochoreFamilyL1Pool, gcIsochoreFamilyL2Pool,
						gcIsochoreFamilyH1Pool, gcIsochoreFamilyH2Pool, gcIsochoreFamilyH3Pool,
						mapabilityChromosomePositionList, mapabilityShortValueList);
				GenerateRandomData right = new GenerateRandomData( outputFolder, chromSize, chromName,
						chromosomeBasedOriginalInputLines, generateRandomDataMode, writeGeneratedRandomDataMode,
						middleIndex, highIndex, permutationNumberList, givenInputsSNPsorIntervals, gcByteList,
						gcIntervalTree, gcIsochoreIntervalTree, gcIsochoreFamilyL1Pool, gcIsochoreFamilyL2Pool,
						gcIsochoreFamilyH1Pool, gcIsochoreFamilyH2Pool, gcIsochoreFamilyH3Pool,
						mapabilityChromosomePositionList, mapabilityShortValueList);
				left.fork();
				rightRandomlyGeneratedData = right.compute();
				leftRandomlyGeneratedData = left.join();

				// Add the contents of leftRandomlyGeneratedData into
				// rightRandomlyGeneratedData
				mergeMaps( rightRandomlyGeneratedData, leftRandomlyGeneratedData);

				leftRandomlyGeneratedData = null;
				return rightRandomlyGeneratedData;
			}
			// CONQUER
			else{

				TIntObjectMap<List<InputLineMinimal>> randomlyGeneratedDataMap = new TIntObjectHashMap<List<InputLineMinimal>>();

				for( int i = lowIndex; i < highIndex; i++){
					permutationNumber = permutationNumberList.get( i);

					randomlyGeneratedDataMap.put( permutationNumber, RandomDataGenerator.generateRandomData(
							givenInputsSNPsorIntervals, gcByteList, gcIntervalTree, gcIsochoreIntervalTree,
							gcIsochoreFamilyL1Pool, gcIsochoreFamilyL2Pool, gcIsochoreFamilyH1Pool,
							gcIsochoreFamilyH2Pool, gcIsochoreFamilyH3Pool, mapabilityChromosomePositionList,
							mapabilityShortValueList, chromSize, chromName, chromosomeBasedOriginalInputLines,
							ThreadLocalRandom.current(), generateRandomDataMode));

					// Write Generated Random Data
					if( writeGeneratedRandomDataMode.isWriteGeneratedRandomDataMode()){
						writeGeneratedRandomData( outputFolder, chromName,
								randomlyGeneratedDataMap.get( permutationNumber), permutationNumber);
					}

				}// End of FOR

				return randomlyGeneratedDataMap;
			}

		}// End of compute method

		// Add the content of leftMap to rightMap
		// Clear and null leftMap
		protected void mergeMaps( TIntObjectMap<List<InputLineMinimal>> rightRandomlyGeneratedDataMap,
				TIntObjectMap<List<InputLineMinimal>> leftRandomlyGeneratedDataMap) {

			int permutationNumber;

			for( TIntObjectIterator<List<InputLineMinimal>> it = leftRandomlyGeneratedDataMap.iterator(); it.hasNext();){

				it.advance();

				permutationNumber = it.key();

				if( !rightRandomlyGeneratedDataMap.containsKey( permutationNumber)){
					rightRandomlyGeneratedDataMap.put( permutationNumber, it.value());
				}
			}

			leftRandomlyGeneratedDataMap.clear();
			leftRandomlyGeneratedDataMap = null;

		}

		protected void writeGeneratedRandomData( String outputFolder, ChromosomeName chrName,
				List<InputLineMinimal> randomlyGeneratedData, int permutationNumber) {

			FileWriter fileWriter;
			BufferedWriter bufferedWriter;
			InputLineMinimal randomlyGeneratedInputLine;

			try{

				fileWriter = FileOperations.createFileWriter(
						outputFolder + Commons.RANDOMLY_GENERATED_DATA_FOLDER + Commons.PERMUTATION + permutationNumber + "_" + Commons.RANDOMLY_GENERATED_DATA + ".txt",
						true);
				bufferedWriter = new BufferedWriter( fileWriter);

				for( int i = 0; i < randomlyGeneratedData.size(); i++){
					randomlyGeneratedInputLine = randomlyGeneratedData.get( i);
					bufferedWriter.write( ChromosomeName.convertEnumtoString( chrName) + "\t" + randomlyGeneratedInputLine.getLow() + "\t" + randomlyGeneratedInputLine.getHigh() + System.getProperty( "line.separator"));
					bufferedWriter.flush();
				}// End of FOR

				bufferedWriter.close();

			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}

		}
	}// End of GenerateRandomData Class
	// Static Nested Class ends

	// For Testing Purposes starts
	static class AnnotateDnaseTFHistoneWithNumbers extends RecursiveTask<AllMapsDnaseTFHistoneWithNumbers> {

		private static final long serialVersionUID = -8989881139246912265L;
		private final ChromosomeName chromName;
		private final TIntObjectMap<List<InputLineMinimal>> randomlyGeneratedDataMap;
		private final int runNumber;
		private final int numberofPermutations;

		private final WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode;

		private final TIntList permutationNumberList;
		private final IntervalTree intervalTree;

		private final AnnotationType annotationType;

		private final int lowIndex;
		private final int highIndex;

		private final String outputFolder;

		private final int overlapDefinition;

		public AnnotateDnaseTFHistoneWithNumbers(
				ChromosomeName chromName,
				TIntObjectMap<List<InputLineMinimal>> randomlyGeneratedDataMap,
				int runNumber,
				int numberofPermutations,
				WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode,
				TIntList permutationNumberList, IntervalTree intervalTree, AnnotationType annotationType, int lowIndex,
				int highIndex, String outputFolder, int overlapDefinition) {

			super();
			this.chromName = chromName;
			this.randomlyGeneratedDataMap = randomlyGeneratedDataMap;
			this.runNumber = runNumber;
			this.numberofPermutations = numberofPermutations;
			this.writePermutationBasedandParametricBasedAnnotationResultMode = writePermutationBasedandParametricBasedAnnotationResultMode;
			this.permutationNumberList = permutationNumberList;
			this.intervalTree = intervalTree;
			this.annotationType = annotationType;
			this.lowIndex = lowIndex;
			this.highIndex = highIndex;
			this.outputFolder = outputFolder;
			this.overlapDefinition = overlapDefinition;
		}

		protected AllMapsDnaseTFHistoneWithNumbers compute() {

			int middleIndex;
			AllMapsDnaseTFHistoneWithNumbers rightAllMapsWithNumbers;
			AllMapsDnaseTFHistoneWithNumbers leftAllMapsWithNumbers;

			int permutationNumber;
			List<AllMapsDnaseTFHistoneWithNumbers> listofAllMapsWithNumbers;
			AllMapsDnaseTFHistoneWithNumbers allMapsWithNumbers;

			// DIVIDE
			if( highIndex - lowIndex > Commons.NUMBER_OF_ANNOTATE_RANDOM_DATA_TASK_DONE_IN_SEQUENTIALLY){
				middleIndex = lowIndex + ( highIndex - lowIndex) / 2;
				AnnotateDnaseTFHistoneWithNumbers left = new AnnotateDnaseTFHistoneWithNumbers( chromName,
						randomlyGeneratedDataMap, runNumber, numberofPermutations,
						writePermutationBasedandParametricBasedAnnotationResultMode, permutationNumberList,
						intervalTree, annotationType, lowIndex, middleIndex, outputFolder, overlapDefinition);
				AnnotateDnaseTFHistoneWithNumbers right = new AnnotateDnaseTFHistoneWithNumbers( chromName,
						randomlyGeneratedDataMap, runNumber, numberofPermutations,
						writePermutationBasedandParametricBasedAnnotationResultMode, permutationNumberList,
						intervalTree, annotationType, middleIndex, highIndex, outputFolder, overlapDefinition);
				left.fork();
				rightAllMapsWithNumbers = right.compute();
				leftAllMapsWithNumbers = left.join();
				combineLeftAllMapsandRightAllMaps( leftAllMapsWithNumbers, rightAllMapsWithNumbers);
				leftAllMapsWithNumbers = null;
				return rightAllMapsWithNumbers;
			}
			// CONQUER
			else{

				listofAllMapsWithNumbers = new ArrayList<AllMapsDnaseTFHistoneWithNumbers>();
				allMapsWithNumbers = new AllMapsDnaseTFHistoneWithNumbers();

				for( int i = lowIndex; i < highIndex; i++){
					permutationNumber = permutationNumberList.get( i);

					// WITHOUT IO WithNumbers
					if( writePermutationBasedandParametricBasedAnnotationResultMode.isDoNotWritePermutationBasedandParametricBasedAnnotationResultMode()){
						listofAllMapsWithNumbers.add( Annotation.annotatePermutationWithoutIOWithNumbers_DnaseTFHistone(
								permutationNumber, chromName, randomlyGeneratedDataMap.get( permutationNumber),
								intervalTree, annotationType, overlapDefinition));
					}

					// WITH IO WithNumbers
					else if( writePermutationBasedandParametricBasedAnnotationResultMode.isWritePermutationBasedandParametricBasedAnnotationResultMode()){
						listofAllMapsWithNumbers.add( Annotation.annotatePermutationWithIOWithNumbers_DnaseTFHistone(
								outputFolder, permutationNumber, chromName,
								randomlyGeneratedDataMap.get( permutationNumber), intervalTree, annotationType,
								overlapDefinition));
					}
				}// End of FOR

				combineListofAllMapsWithNumbers( listofAllMapsWithNumbers, allMapsWithNumbers);

				listofAllMapsWithNumbers = null;
				return allMapsWithNumbers;

			}
		}

		// Combine leftAllMapsWithNumbers and rightAllMapsWithNumbers in rightAllMapsWithNumbers
		protected void combineLeftAllMapsandRightAllMaps( AllMapsDnaseTFHistoneWithNumbers leftAllMapsWithNumbers,
				AllMapsDnaseTFHistoneWithNumbers rightAllMapsWithNumbers) {

			// LEFT ALL MAPS WITH NUMBERS
			// DNASE
			TIntIntMap leftPermutationNumberDnaseCellLineNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap();

			// TF
			TLongIntMap leftPermutationNumberTfNumberCellLineNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap();

			// HISTONE
			TLongIntMap leftPermutationNumberHistoneNumberCellLineNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap();

			// RIGHT ALL MAPS WITH NUMBERS
			// DNASE
			TIntIntMap rightPermutationNumberDnaseCellLineNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap();

			// TF
			TLongIntMap rightPermutationNumberTfNumberCellLineNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap();

			// HISTONE
			TLongIntMap rightPermutationNumberHistoneNumberCellLineNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap();

			// DNASE
			if( leftPermutationNumberDnaseCellLineNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberDnaseCellLineNumber2KMap,
						rightPermutationNumberDnaseCellLineNumber2KMap);
				leftPermutationNumberDnaseCellLineNumber2KMap = null;
			}

			// TF
			if( leftPermutationNumberTfNumberCellLineNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberTfNumberCellLineNumber2KMap,
						rightPermutationNumberTfNumberCellLineNumber2KMap);
				leftPermutationNumberTfNumberCellLineNumber2KMap = null;
			}

			// HISTONE
			if( leftPermutationNumberHistoneNumberCellLineNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberHistoneNumberCellLineNumber2KMap,
						rightPermutationNumberHistoneNumberCellLineNumber2KMap);
				leftPermutationNumberHistoneNumberCellLineNumber2KMap = null;
			}

		}

		// TIntIntMap version starts
		// Accumulate leftMapWithNumbers in the rightMapWithNumbers
		// Accumulate number of overlaps
		// based on permutationNumber and ElementName
		protected void combineLeftMapandRightMap( TIntIntMap leftMapWithNumbers, TIntIntMap rightMapWithNumbers) {

			for( TIntIntIterator it = leftMapWithNumbers.iterator(); it.hasNext();){

				it.advance();

				int permutationNumberCellLineNumberOrKeggPathwayNumber = it.key();
				int numberofOverlaps = it.value();

				// For debug purposes starts
				// System.out.println("permutationNumberCellLineNumberOrKeggPathwayNumber: "
				// + permutationNumberCellLineNumberOrKeggPathwayNumber);
				// For debug purposes ends

				if( !( rightMapWithNumbers.containsKey( permutationNumberCellLineNumberOrKeggPathwayNumber))){
					rightMapWithNumbers.put( permutationNumberCellLineNumberOrKeggPathwayNumber, numberofOverlaps);
				}else{
					rightMapWithNumbers.put(
							permutationNumberCellLineNumberOrKeggPathwayNumber,
							rightMapWithNumbers.get( permutationNumberCellLineNumberOrKeggPathwayNumber) + numberofOverlaps);
				}
			}

			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;

		}

		// TIntIntMap version ends

		// Accumulate leftMapWithNumbers in the rightMapWithNumbers
		// Accumulate number of overlaps
		// based on permutationNumber and ElementName
		protected void combineLeftMapandRightMap( TLongIntMap leftMapWithNumbers, TLongIntMap rightMapWithNumbers) {

			for( TLongIntIterator it = leftMapWithNumbers.iterator(); it.hasNext();){

				it.advance();

				long permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
				int numberofOverlaps = it.value();

				if( !( rightMapWithNumbers.containsKey( permutationNumberElementNumberCellLineNumberKeggPathwayNumber))){
					rightMapWithNumbers.put( permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
							numberofOverlaps);
				}else{
					rightMapWithNumbers.put(
							permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
							rightMapWithNumbers.get( permutationNumberElementNumberCellLineNumberKeggPathwayNumber) + numberofOverlaps);
				}
			}

			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;

		}

		// Accumulate the allMaps in the left into listofAllMaps in the right
		protected void combineListofAllMapsWithNumbers( List<AllMapsDnaseTFHistoneWithNumbers> listofAllMaps,
				AllMapsDnaseTFHistoneWithNumbers allMaps) {

			for( int i = 0; i < listofAllMaps.size(); i++){
				combineLeftAllMapsandRightAllMaps( listofAllMaps.get( i), allMaps);
			}
		}
	}
	// For Testing Purposes ends

	// Static Nested Class starts
	// AnnotateWithNumbersForAllChromosomes
	static class AnnotateWithNumbersForAllChromosomes extends RecursiveTask<AllMapsWithNumbersForAllChromosomes> {

		private static final long serialVersionUID = 793082641696132194L;

		private final TIntObjectMap<TIntObjectMap<List<InputLineMinimal>>> chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap;

		private final int runNumber;
		private final int numberofPermutations;

		private final WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode;

		private final TIntList permutationNumberList;
		
		private final TIntObjectMap<IntervalTree> chrNumber2IntervalTreeMap;
		private final TIntObjectMap<IntervalTree> chrNumber2UcscRefSeqGenesIntervalTreeMap;
		private final TIntObjectMap<TIntObjectMap<IntervalTree>> userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap;
		
		private final TIntObjectMap<String> userDefinedLibraryElementTypeNumber2ElementTypeNameMap;
		
		private final AnnotationType annotationType;

		private final int lowIndex;
		private final int highIndex;

		private final TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap;

		private final String outputFolder;

		private final int overlapDefinition;

		private TIntIntMap elementNumber2OriginalKMap;
		
		private TIntIntMap exonBasedGeneSetNumber2OriginalKMap;
		private TIntIntMap regulationBasedGeneSetNumber2OriginalKMap;
		private TIntIntMap allBasedGeneSetNumber2OriginalKMap;
		
		private TIntIntMap userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap;
		
		private TIntIntMap tfNumberExonBasedKEGGPathwayNumber2OriginalKMap;
		private TIntIntMap tfNumberRegulationBasedKEGGPathwayNumber2OriginalKMap;
		private TIntIntMap tfNumberAllBasedKEGGPathwayNumber2OriginalKMap;
		
		private TLongIntMap tfNumberCellLineNumberExonBasedKEGGPathwayNumber2OriginalKMap;
		private TLongIntMap tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2OriginalKMap;
		private TLongIntMap tfNumberCellLineNumberAllBasedKEGGPathwayNumber2OriginalKMap;
		
		public AnnotateWithNumbersForAllChromosomes(
				String outputFolder,
				TIntObjectMap<TIntObjectMap<List<InputLineMinimal>>> chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap,
				int runNumber,
				int numberofPermutations,
				WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode,
				int lowIndex, 
				int highIndex, 
				TIntList permutationNumberList,
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

			this.outputFolder = outputFolder;

			this.chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap = chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap;
			this.runNumber = runNumber;
			this.numberofPermutations = numberofPermutations;

			this.writePermutationBasedandParametricBasedAnnotationResultMode = writePermutationBasedandParametricBasedAnnotationResultMode;

			this.lowIndex = lowIndex;
			this.highIndex = highIndex;

			this.permutationNumberList = permutationNumberList;
			this.chrNumber2IntervalTreeMap = chrNumber2IntervalTreeMap;

			// sent full when annotationType is TF_KEGG_PATHWAY_ANNOTATION
			// sent full when annotationType is TF_CELLLINE_KEGG_PATHWAY_ANNOTATION
			// otherwise sent null
			this.chrNumber2UcscRefSeqGenesIntervalTreeMap = chrNumber2UcscRefSeqGenesIntervalTreeMap;

			this.userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap =  userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap;
			
			this.userDefinedLibraryElementTypeNumber2ElementTypeNameMap = userDefinedLibraryElementTypeNumber2ElementTypeNameMap;
			
			this.annotationType = annotationType;

			// geneId2ListofGeneSetNumberMap
			// sent full when annotationType is KEGG_PATHWAY_ANNOTATION
			// sent full when annotationType is TF_KEGG_PATHWAY_ANNOTATION
			// sent full when annotationType is TF_CELLLINE_KEGG_PATHWAY_ANNOTATION
			// sent full when annotationType is USER_DEFINED_GENE_SET_ANNOTATION
			// otherwise sent null
			this.geneId2ListofGeneSetNumberMap = geneId2ListofGeneSetNumberMap;

			this.overlapDefinition = overlapDefinition;

			this.elementNumber2OriginalKMap = elementNumber2OriginalKMap;
			
			//Will be used KEGGPathway and UserDefinedGeneSet
			this.exonBasedGeneSetNumber2OriginalKMap 		= exonBasedGeneSetNumber2OriginalKMap;
			this.regulationBasedGeneSetNumber2OriginalKMap 	= regulationBasedGeneSetNumber2OriginalKMap;
			this.allBasedGeneSetNumber2OriginalKMap 		= allBasedGeneSetNumber2OriginalKMap;
			
			this.userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap = userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap;
			
			this.tfNumberExonBasedKEGGPathwayNumber2OriginalKMap = tfNumberExonBasedKEGGPathwayNumber2OriginalKMap;
			this.tfNumberRegulationBasedKEGGPathwayNumber2OriginalKMap = tfNumberRegulationBasedKEGGPathwayNumber2OriginalKMap;
			this.tfNumberAllBasedKEGGPathwayNumber2OriginalKMap = tfNumberAllBasedKEGGPathwayNumber2OriginalKMap;
			
			this.tfNumberCellLineNumberExonBasedKEGGPathwayNumber2OriginalKMap = tfNumberCellLineNumberExonBasedKEGGPathwayNumber2OriginalKMap;
			this.tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2OriginalKMap = tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2OriginalKMap;
			this.tfNumberCellLineNumberAllBasedKEGGPathwayNumber2OriginalKMap = tfNumberCellLineNumberAllBasedKEGGPathwayNumber2OriginalKMap;
			
		}

		protected AllMapsWithNumbersForAllChromosomes compute() {

			int middleIndex;
			AllMapsWithNumbersForAllChromosomes rightAllMapsWithNumbersForAllChromosomes;
			AllMapsWithNumbersForAllChromosomes leftAllMapsWithNumbersForAllChromosomes;

			Integer permutationNumber;
			AllMapsWithNumbersForAllChromosomes allMapsWithNumbersForAllChromosomes;

			// DIVIDE
			if( highIndex - lowIndex > Commons.NUMBER_OF_ANNOTATE_RANDOM_DATA_TASK_DONE_IN_SEQUENTIALLY){
				middleIndex = lowIndex + ( highIndex - lowIndex) / 2;
				AnnotateWithNumbersForAllChromosomes left = new AnnotateWithNumbersForAllChromosomes( 
						outputFolder,
						chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, runNumber, numberofPermutations,
						writePermutationBasedandParametricBasedAnnotationResultMode, lowIndex, middleIndex,
						permutationNumberList, 
						chrNumber2IntervalTreeMap, 
						chrNumber2UcscRefSeqGenesIntervalTreeMap,
						userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap,
						userDefinedLibraryElementTypeNumber2ElementTypeNameMap,
						annotationType, 
						geneId2ListofGeneSetNumberMap, overlapDefinition,
						elementNumber2OriginalKMap,
						exonBasedGeneSetNumber2OriginalKMap,
						regulationBasedGeneSetNumber2OriginalKMap,
						allBasedGeneSetNumber2OriginalKMap,
						userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap,
						tfNumberExonBasedKEGGPathwayNumber2OriginalKMap,
						tfNumberRegulationBasedKEGGPathwayNumber2OriginalKMap,
						tfNumberAllBasedKEGGPathwayNumber2OriginalKMap,
						tfNumberCellLineNumberExonBasedKEGGPathwayNumber2OriginalKMap,
						tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2OriginalKMap,
						tfNumberCellLineNumberAllBasedKEGGPathwayNumber2OriginalKMap);
				
				AnnotateWithNumbersForAllChromosomes right = new AnnotateWithNumbersForAllChromosomes( outputFolder,
						chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, runNumber, numberofPermutations,
						writePermutationBasedandParametricBasedAnnotationResultMode, middleIndex, highIndex,
						permutationNumberList, 
						chrNumber2IntervalTreeMap, 
						chrNumber2UcscRefSeqGenesIntervalTreeMap,
						userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap,
						userDefinedLibraryElementTypeNumber2ElementTypeNameMap,
						annotationType, geneId2ListofGeneSetNumberMap, overlapDefinition,
						elementNumber2OriginalKMap,
						exonBasedGeneSetNumber2OriginalKMap,
						regulationBasedGeneSetNumber2OriginalKMap,
						allBasedGeneSetNumber2OriginalKMap,
						userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap,
						tfNumberExonBasedKEGGPathwayNumber2OriginalKMap,
						tfNumberRegulationBasedKEGGPathwayNumber2OriginalKMap,
						tfNumberAllBasedKEGGPathwayNumber2OriginalKMap,
						tfNumberCellLineNumberExonBasedKEGGPathwayNumber2OriginalKMap,
						tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2OriginalKMap,
						tfNumberCellLineNumberAllBasedKEGGPathwayNumber2OriginalKMap);
				
				left.fork();

				rightAllMapsWithNumbersForAllChromosomes = right.compute();
				leftAllMapsWithNumbersForAllChromosomes = left.join();

				accumulateLeftInRightAllMapsWithNumbersForAllChromosomes(
						leftAllMapsWithNumbersForAllChromosomes,
						rightAllMapsWithNumbersForAllChromosomes, 
						annotationType);

				leftAllMapsWithNumbersForAllChromosomes = null;
				return rightAllMapsWithNumbersForAllChromosomes;
			}//End of DIVIDE
			
			// CONQUER
			else{

				allMapsWithNumbersForAllChromosomes = new AllMapsWithNumbersForAllChromosomes();

				TIntObjectMap<List<InputLineMinimal>> chrNumber2RandomlyGeneratedData = new TIntObjectHashMap<List<InputLineMinimal>>();

				for( int i = lowIndex; i < highIndex; i++){

					permutationNumber = permutationNumberList.get( i);

					// Fill TIntObjectMap<List<InputLineMinimal>> chrNumber2RandomlyGeneratedData using
					// chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap
					fillPermutationRandomlyGeneratedData( permutationNumber,chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, chrNumber2RandomlyGeneratedData);

					// Annotate each permutation with permutationNumber
					// WITHOUT IO
					// WITH NUMBERS
					// For All Chromosomes
					if( writePermutationBasedandParametricBasedAnnotationResultMode.isDoNotWritePermutationBasedandParametricBasedAnnotationResultMode()){

						// What does this function do?
						// This function accumulates the number of permutations that has number of overlaps greater than
						// or equal to the number of original overlaps
						// By annotating each permutation one by one.
						accumulatePermutationGreaterThanOrEqualToOneorZeroInRightAllMaps(

						Annotation.annotatePermutationWithoutIOWithNumbersForAllChromosomes(
								permutationNumber,
								chrNumber2RandomlyGeneratedData, 
								chrNumber2IntervalTreeMap,
								chrNumber2UcscRefSeqGenesIntervalTreeMap,
								userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap,
								userDefinedLibraryElementTypeNumber2ElementTypeNameMap,
								annotationType,
								geneId2ListofGeneSetNumberMap, 
								overlapDefinition, 
								elementNumber2OriginalKMap,
								exonBasedGeneSetNumber2OriginalKMap,
								regulationBasedGeneSetNumber2OriginalKMap,
								allBasedGeneSetNumber2OriginalKMap,
								userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap,
								tfNumberExonBasedKEGGPathwayNumber2OriginalKMap,
								tfNumberRegulationBasedKEGGPathwayNumber2OriginalKMap,
								tfNumberAllBasedKEGGPathwayNumber2OriginalKMap,
								tfNumberCellLineNumberExonBasedKEGGPathwayNumber2OriginalKMap,
								tfNumberCellLineNumberRegulationBasedKEGGPathwayNumber2OriginalKMap,
								tfNumberCellLineNumberAllBasedKEGGPathwayNumber2OriginalKMap),

						allMapsWithNumbersForAllChromosomes, annotationType);
					}

					// Annotate each permutation with permutationNumber
					// WITH IO
					// WITH NUMBERS
					// For All Chromosomes
					else if( writePermutationBasedandParametricBasedAnnotationResultMode.isWritePermutationBasedandParametricBasedAnnotationResultMode()){

						accumulatePermutationGreaterThanOrEqualToOneorZeroInRightAllMaps(
							Annotation.annotatePermutationWithIOWithNumbersForAllChromosomes( 
									outputFolder,
									permutationNumber, 
									chrNumber2RandomlyGeneratedData, 
									chrNumber2IntervalTreeMap,
									chrNumber2UcscRefSeqGenesIntervalTreeMap, 
									annotationType,
									geneId2ListofGeneSetNumberMap, 
									overlapDefinition, 
									elementNumber2OriginalKMap),
									
							allMapsWithNumbersForAllChromosomes, 
							annotationType);
					}
				}// End of FOR

				return allMapsWithNumbersForAllChromosomes;

			}//End of Conquer
		}

		protected void fillPermutationRandomlyGeneratedData(
				int permutationNumber,
				TIntObjectMap<TIntObjectMap<List<InputLineMinimal>>> chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap,
				TIntObjectMap<List<InputLineMinimal>> chrNumber2RandomlyGeneratedData) {

			for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

				if( chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap.get( chrNumber) != null){

					chrNumber2RandomlyGeneratedData.put(chrNumber,chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap.get(chrNumber).get(permutationNumber));

				}// End of IF

			}// End of FOR

		}

		// 29 June 2015
		// LEft Map contains permutations results
		// Accumulate Permutation's 1s or 0s for each elementNumber in the Right Map
		// 1 means that permutation has numberofOverlaps greaterThanOrEqualTo originalNumberofOverlaps
		// 0 means that permutation has numberofOverlaps lessThan originalNumberofOverlaps
		// Accumulate leftAllMapsKeysWithNumbersAndValuesOneorZero in rightAllMapsWithNumbersForAllChromosomes
		// Right Map contains the results up to now.
		// Then Free Space of leftAllMapsKeysWithNumbersAndValuesOneorZero
		protected void accumulatePermutationGreaterThanOrEqualToOneorZeroInRightAllMaps(
				AllMapsKeysWithNumbersAndValuesOneorZero leftAllMapsKeysWithNumbersAndValuesOneorZero,
				AllMapsWithNumbersForAllChromosomes rightAllMapsWithNumbersForAllChromosomes,
				AnnotationType annotationType) {

			// DNASE
			if( annotationType.doDnaseAnnotation()){

				TIntByteMap leftDnaseCellLineNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getDnaseCellLineNumber2PermutationOneorZeroMap();

				TIntIntMap rightDnaseCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getDnaseCellLineNumber2NumberofPermutations();

				if( leftDnaseCellLineNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftDnaseCellLineNumber2PermutationOneorZeroMap,
							rightDnaseCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftDnaseCellLineNumber2PermutationOneorZeroMap = null;
				}
			}

			// TF
			if( annotationType.doTFAnnotation()){

				TIntByteMap leftTfNumberCellLineNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberCellLineNumber2PermutationOneorZeroMap();
				TIntIntMap rightTfNumberCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();

				if( leftTfNumberCellLineNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftTfNumberCellLineNumber2PermutationOneorZeroMap,
							rightTfNumberCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberCellLineNumber2PermutationOneorZeroMap = null;
				}
			}

			// HISTONE
			if( annotationType.doHistoneAnnotation()){

				TIntByteMap leftHistoneNumberCellLineNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getHistoneNumberCellLineNumber2PermutationOneorZeroMap();
				TIntIntMap rightHistoneNumberCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getHistoneNumberCellLineNumber2NumberofPermutations();

				if( leftHistoneNumberCellLineNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftHistoneNumberCellLineNumber2PermutationOneorZeroMap,
							rightHistoneNumberCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftHistoneNumberCellLineNumber2PermutationOneorZeroMap = null;
				}
			}

			// GENE
			if( annotationType.doGeneAnnotation()){

				TIntByteMap leftGeneNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getGeneNumber2PermutationOneorZeroMap();
				TIntIntMap rightGeneNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getGeneNumber2NumberofPermutations();

				if( leftGeneNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftGeneNumber2PermutationOneorZeroMap,
							rightGeneNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftGeneNumber2PermutationOneorZeroMap = null;
				}

			}

			// USERDEFINED GENESET
			if( annotationType.doUserDefinedGeneSetAnnotation()){

				TIntByteMap leftExonBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getExonBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap();
				TIntByteMap leftRegulationBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getRegulationBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap();
				TIntByteMap leftAllBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getAllBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap();

				TIntIntMap rightExonBasedUserDefinedGeneSetNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getExonBasedUserDefinedGeneSetNumber2NumberofPermutations();
				TIntIntMap rightRegulationBasedUserDefinedGeneSetNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations();
				TIntIntMap rightAllBasedUserDefinedGeneSetNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getAllBasedUserDefinedGeneSetNumber2NumberofPermutations();

				// EXON BASED USERDEFINED GENESET
				if( leftExonBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftExonBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap,
							rightExonBasedUserDefinedGeneSetNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftExonBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap = null;
				}

				// REGULATION BASED USERDEFINED GENESET
				if( leftRegulationBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftRegulationBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap,
							rightRegulationBasedUserDefinedGeneSetNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftRegulationBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap = null;
				}

				// ALL BASED USERDEFINED GENESET
				if( leftAllBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftAllBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap,
							rightAllBasedUserDefinedGeneSetNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftAllBasedUserDefinedGeneSetNumber2PermutationOneorZeroMap = null;
				}

			}

			// USERDEFINED LIBRARY
			if( annotationType.doUserDefinedLibraryAnnotation()){

				TIntByteMap leftElementTypeNumberElementNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getElementTypeNumberElementNumber2PermutationOneorZeroMap();

				TIntIntMap rightElementTypeNumberElementNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getElementTypeNumberElementNumber2NumberofPermutations();

				if( leftElementTypeNumberElementNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftElementTypeNumberElementNumber2PermutationOneorZeroMap,
							rightElementTypeNumberElementNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftElementTypeNumberElementNumber2PermutationOneorZeroMap = null;
				}

			}

			if( annotationType.doKEGGPathwayAnnotation()){

				// KEGGPathway
				TIntByteMap leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getExonBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getAllBasedKeggPathwayNumber2PermutationOneorZeroMap();

				TIntIntMap rightExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				// EXON BASED KEGG PATHWAY
				if( leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// REGULATION BASED KEGG PATHWAY
				if( leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// ALL BASED KEGG PATHWAY
				if( leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

			}

			// TF KEGGPathway
			if( annotationType.doTFKEGGPathwayAnnotation()){

				// TF
				TIntByteMap leftTfNumberCellLineNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberCellLineNumber2PermutationOneorZeroMap();
				TIntIntMap rightTfNumberCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();

				if( leftTfNumberCellLineNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftTfNumberCellLineNumber2PermutationOneorZeroMap,
							rightTfNumberCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberCellLineNumber2PermutationOneorZeroMap = null;
				}

				// KEGGPathway
				TIntByteMap leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getExonBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getAllBasedKeggPathwayNumber2PermutationOneorZeroMap();

				TIntIntMap rightExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				// EXON BASED KEGG PATHWAY
				if( leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// REGULATION BASED KEGG PATHWAY
				if( leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// ALL BASED KEGG PATHWAY
				if( leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF KEGGPathway
				TIntByteMap leftTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap();

				TIntIntMap rightTfNumberExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightTfNumberAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				// TF and EXON BASED KEGG PATHWAY
				if( leftTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF and REGULATION BASED KEGG PATHWAY
				if( leftTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF and ALL BASED KEGG PATHWAY
				if( leftTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

			}

			// TF CellLine KEGGPathway
			if( annotationType.doTFCellLineKEGGPathwayAnnotation()){

				// TF
				TIntByteMap leftTfNumberCellLineNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberCellLineNumber2PermutationOneorZeroMap();
				TIntIntMap rightTfNumberCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();

				if( leftTfNumberCellLineNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftTfNumberCellLineNumber2PermutationOneorZeroMap,
							rightTfNumberCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberCellLineNumber2PermutationOneorZeroMap = null;
				}

				// KEGGPathway
				TIntByteMap leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getExonBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getAllBasedKeggPathwayNumber2PermutationOneorZeroMap();

				TIntIntMap rightExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				// EXON BASED KEGG PATHWAY
				if( leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// REGULATION BASED KEGG PATHWAY
				if( leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// ALL BASED KEGG PATHWAY
				if( leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF CellLine KEGGPathway
				TLongByteMap leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TLongByteMap leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TLongByteMap leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap();

				TLongIntMap rightTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				TLongIntMap rightTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				TLongIntMap rightTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				// TF and CellLine and EXON BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF and CellLine and REGULATION BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF and CellLine and ALL BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}
			}

			// BOTH
			// TF KEGGPathway
			// TF CellLine KEGGPathway
			if( annotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

				// TF
				TIntByteMap leftTfNumberCellLineNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberCellLineNumber2PermutationOneorZeroMap();
				TIntIntMap rightTfNumberCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();

				if( leftTfNumberCellLineNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftTfNumberCellLineNumber2PermutationOneorZeroMap,
							rightTfNumberCellLineNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberCellLineNumber2PermutationOneorZeroMap = null;
				}

				// KEGGPathway
				TIntByteMap leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getExonBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getAllBasedKeggPathwayNumber2PermutationOneorZeroMap();

				TIntIntMap rightExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				// EXON BASED KEGG PATHWAY
				if( leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftExonBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// REGULATION BASED KEGG PATHWAY
				if( leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// ALL BASED KEGG PATHWAY
				if( leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftAllBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF KEGGPathway
				TIntByteMap leftTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TIntByteMap leftTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap();

				TIntIntMap rightTfNumberExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				TIntIntMap rightTfNumberAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				// TF and EXON BASED KEGG PATHWAY
				if( leftTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF and REGULATION BASED KEGG PATHWAY
				if( leftTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF and ALL BASED KEGG PATHWAY
				if( leftTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF CellLine KEGGPathway
				TLongByteMap leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TLongByteMap leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap();
				TLongByteMap leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap = leftAllMapsKeysWithNumbersAndValuesOneorZero.getTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap();

				TLongIntMap rightTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				TLongIntMap rightTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				TLongIntMap rightTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				// TF and CellLine and EXON BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF and CellLine and REGULATION BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

				// TF and CellLine and ALL BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap,
							rightTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap);

					leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2PermutationOneorZeroMap = null;
				}

			}

			// Free space
			leftAllMapsKeysWithNumbersAndValuesOneorZero = null;

		}// End of Accumulation of left maps in right maps
			// 29 June 2015

		// Accumulate leftPermutationsMap in rightPermutationsMap
		// LeftMap contains the numberofPermutations that has numberofOverlaps greaterThanOrEqual to the
		// originalNumberofOverlaps for each elementNumber coming from LEFT forkandjoin
		// RightMap contains the numberofPermutations that has numberofOverlaps greaterThanOrEqual to the
		// originalNumberofOverlaps for each elementNumber coming from RIGHT forkandjoin
		// Accumulate leftMap in rightMap
		// Then Free space for leftMap
		protected void accumulateLeftInRightAllMapsWithNumbersForAllChromosomes(
				AllMapsWithNumbersForAllChromosomes leftAllMapsWithNumbersForAllChromosomes,
				AllMapsWithNumbersForAllChromosomes rightAllMapsWithNumbersForAllChromosomes,
				AnnotationType annotationType) {

			// DNase
			TIntIntMap leftDnaseCellLineNumber2NumberofPermutations = null;
			TIntIntMap rightDnaseCellLineNumber2NumberofPermutations = null;

			// TF
			TIntIntMap leftTfNumberCellLineNumber2NumberofPermutations = null;
			TIntIntMap rightTfNumberCellLineNumber2NumberofPermutations = null;

			// Histone
			TIntIntMap leftHistoneNumberCellLineNumber2NumberofPermutations = null;
			TIntIntMap rightHistoneNumberCellLineNumber2NumberofPermutations = null;

			// Gene
			TIntIntMap leftGeneNumber2NumberofPermutations = null;
			TIntIntMap rightGeneNumber2NumberofPermutations = null;

			// UserDefinedGeneSet
			TIntIntMap leftExonBasedUserDefinedGeneSetNumber2NumberofPermutations = null;
			TIntIntMap leftRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations = null;
			TIntIntMap leftAllBasedUserDefinedGeneSetNumber2NumberofPermutations = null;

			TIntIntMap rightExonBasedUserDefinedGeneSetNumber2NumberofPermutations = null;
			TIntIntMap rightRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations = null;
			TIntIntMap rightAllBasedUserDefinedGeneSetNumber2NumberofPermutations = null;

			// USERDEFINED LIBRARY
			TIntIntMap leftElementTypeNumberElementNumber2NumberofPermutations = null;
			TIntIntMap rightElementTypeNumberElementNumber2NumberofPermutations = null;

			// KEGGPathway
			TIntIntMap leftExonBasedKeggPathwayNumber2NumberofPermutations = null;
			TIntIntMap leftRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
			TIntIntMap leftAllBasedKeggPathwayNumber2NumberofPermutations = null;

			TIntIntMap rightExonBasedKeggPathwayNumber2NumberofPermutations = null;
			TIntIntMap rightRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
			TIntIntMap rightAllBasedKeggPathwayNumber2NumberofPermutations = null;

			// TF KEGGPathway
			TIntIntMap leftTfNumberExonBasedKeggPathwayNumber2NumberofPermutations = null;
			TIntIntMap leftTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
			TIntIntMap leftTfNumberAllBasedKeggPathwayNumber2NumberofPermutations = null;

			TIntIntMap rightTfNumberExonBasedKeggPathwayNumber2NumberofPermutations = null;
			TIntIntMap rightTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
			TIntIntMap rightTfNumberAllBasedKeggPathwayNumber2NumberofPermutations = null;

			// TF CellLine KEGGPathway
			TLongIntMap leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations = null;
			TLongIntMap leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
			TLongIntMap leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations = null;

			TLongIntMap rightTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations = null;
			TLongIntMap rightTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
			TLongIntMap rightTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations = null;

			switch( annotationType){

			case DO_DNASE_ANNOTATION:
				// DNASE
				leftDnaseCellLineNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getDnaseCellLineNumber2NumberofPermutations();
				rightDnaseCellLineNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getDnaseCellLineNumber2NumberofPermutations();

				if( leftDnaseCellLineNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftDnaseCellLineNumber2NumberofPermutations,
							rightDnaseCellLineNumber2NumberofPermutations);

					leftDnaseCellLineNumber2NumberofPermutations = null;
				}
				break;

			case DO_TF_ANNOTATION:
				// TF
				leftTfNumberCellLineNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();
				rightTfNumberCellLineNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();

				if( leftTfNumberCellLineNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftTfNumberCellLineNumber2NumberofPermutations,
							rightTfNumberCellLineNumber2NumberofPermutations);

					leftTfNumberCellLineNumber2NumberofPermutations = null;
				}

				break;

			case DO_HISTONE_ANNOTATION:
				// HISTONE
				leftHistoneNumberCellLineNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getHistoneNumberCellLineNumber2NumberofPermutations();
				rightHistoneNumberCellLineNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getHistoneNumberCellLineNumber2NumberofPermutations();

				if( leftHistoneNumberCellLineNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftHistoneNumberCellLineNumber2NumberofPermutations,
							rightHistoneNumberCellLineNumber2NumberofPermutations);

					leftHistoneNumberCellLineNumber2NumberofPermutations = null;
				}
				break;

			case DO_GENE_ANNOTATION:
				// Gene
				leftGeneNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getGeneNumber2NumberofPermutations();
				rightGeneNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getGeneNumber2NumberofPermutations();

				if( leftGeneNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftGeneNumber2NumberofPermutations,
							rightGeneNumber2NumberofPermutations);

					leftGeneNumber2NumberofPermutations = null;
				}
				break;

			case DO_USER_DEFINED_GENESET_ANNOTATION:
				// USERDEFINED GENESET
				leftExonBasedUserDefinedGeneSetNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getExonBasedUserDefinedGeneSetNumber2NumberofPermutations();
				leftRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations();
				leftAllBasedUserDefinedGeneSetNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getAllBasedUserDefinedGeneSetNumber2NumberofPermutations();

				rightExonBasedUserDefinedGeneSetNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getExonBasedUserDefinedGeneSetNumber2NumberofPermutations();
				rightRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations();
				rightAllBasedUserDefinedGeneSetNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getAllBasedUserDefinedGeneSetNumber2NumberofPermutations();

				// EXON BASED USERDEFINED GENESET
				if( leftExonBasedUserDefinedGeneSetNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftExonBasedUserDefinedGeneSetNumber2NumberofPermutations,
							rightExonBasedUserDefinedGeneSetNumber2NumberofPermutations);

					leftExonBasedUserDefinedGeneSetNumber2NumberofPermutations = null;
				}

				// REGULATION BASED USERDEFINED GENESET
				if( leftRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations,
							rightRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations);

					leftRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations = null;
				}

				// ALL BASED USERDEFINED GENESET
				if( leftAllBasedUserDefinedGeneSetNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftAllBasedUserDefinedGeneSetNumber2NumberofPermutations,
							rightAllBasedUserDefinedGeneSetNumber2NumberofPermutations);

					leftAllBasedUserDefinedGeneSetNumber2NumberofPermutations = null;
				}
				break;

			case DO_USER_DEFINED_LIBRARY_ANNOTATION:
				// USERDEFINED LIBRARY
				leftElementTypeNumberElementNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getElementTypeNumberElementNumber2NumberofPermutations();
				rightElementTypeNumberElementNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getElementTypeNumberElementNumber2NumberofPermutations();

				if( leftElementTypeNumberElementNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftElementTypeNumberElementNumber2NumberofPermutations,
							rightElementTypeNumberElementNumber2NumberofPermutations);

					leftElementTypeNumberElementNumber2NumberofPermutations = null;
				}
				break;

			case DO_KEGGPATHWAY_ANNOTATION:
				// KEGGPathway
				leftExonBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				leftRegulationBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				leftAllBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				rightExonBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				rightRegulationBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				rightAllBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				// EXON BASED KEGG PATHWAY
				if( leftExonBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftExonBasedKeggPathwayNumber2NumberofPermutations,
							rightExonBasedKeggPathwayNumber2NumberofPermutations);

					leftExonBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// REGULATION BASED KEGG PATHWAY
				if( leftRegulationBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftRegulationBasedKeggPathwayNumber2NumberofPermutations,
							rightRegulationBasedKeggPathwayNumber2NumberofPermutations);

					leftRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// ALL BASED KEGG PATHWAY
				if( leftAllBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftAllBasedKeggPathwayNumber2NumberofPermutations,
							rightAllBasedKeggPathwayNumber2NumberofPermutations);

					leftAllBasedKeggPathwayNumber2NumberofPermutations = null;
				}
				break;

			case DO_TF_KEGGPATHWAY_ANNOTATION:

				// TF
				leftTfNumberCellLineNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();
				rightTfNumberCellLineNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();

				if( leftTfNumberCellLineNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftTfNumberCellLineNumber2NumberofPermutations,
							rightTfNumberCellLineNumber2NumberofPermutations);

					leftTfNumberCellLineNumber2NumberofPermutations = null;
				}

				// KEGGPathway
				leftExonBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				leftRegulationBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				leftAllBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				rightExonBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				rightRegulationBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				rightAllBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				// EXON BASED KEGG PATHWAY
				if( leftExonBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftExonBasedKeggPathwayNumber2NumberofPermutations,
							rightExonBasedKeggPathwayNumber2NumberofPermutations);

					leftExonBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// REGULATION BASED KEGG PATHWAY
				if( leftRegulationBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftRegulationBasedKeggPathwayNumber2NumberofPermutations,
							rightRegulationBasedKeggPathwayNumber2NumberofPermutations);

					leftRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// ALL BASED KEGG PATHWAY
				if( leftAllBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftAllBasedKeggPathwayNumber2NumberofPermutations,
							rightAllBasedKeggPathwayNumber2NumberofPermutations);

					leftAllBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF KEGGPathway
				leftTfNumberExonBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				leftTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				leftTfNumberAllBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				rightTfNumberExonBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				rightTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				rightTfNumberAllBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				// TF and EXON BASED KEGG PATHWAY
				if( leftTfNumberExonBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberExonBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberExonBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberExonBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF and REGULATION BASED KEGG PATHWAY
				if( leftTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF and ALL BASED KEGG PATHWAY
				if( leftTfNumberAllBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberAllBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberAllBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberAllBasedKeggPathwayNumber2NumberofPermutations = null;
				}
				break;

			case DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION:

				// TF
				leftTfNumberCellLineNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();
				rightTfNumberCellLineNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();

				if( leftTfNumberCellLineNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftTfNumberCellLineNumber2NumberofPermutations,
							rightTfNumberCellLineNumber2NumberofPermutations);

					leftTfNumberCellLineNumber2NumberofPermutations = null;
				}

				// KEGGPathway
				leftExonBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				leftRegulationBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				leftAllBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				rightExonBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				rightRegulationBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				rightAllBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				// EXON BASED KEGG PATHWAY
				if( leftExonBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftExonBasedKeggPathwayNumber2NumberofPermutations,
							rightExonBasedKeggPathwayNumber2NumberofPermutations);

					leftExonBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// REGULATION BASED KEGG PATHWAY
				if( leftRegulationBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftRegulationBasedKeggPathwayNumber2NumberofPermutations,
							rightRegulationBasedKeggPathwayNumber2NumberofPermutations);

					leftRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// ALL BASED KEGG PATHWAY
				if( leftAllBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftAllBasedKeggPathwayNumber2NumberofPermutations,
							rightAllBasedKeggPathwayNumber2NumberofPermutations);

					leftAllBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF CellLine KEGGPathway
				leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				rightTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				rightTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				rightTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				// TF and CellLine and EXON BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF and CellLine and REGULATION BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF and CellLine and ALL BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				break;

			case DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION:
				// TF
				leftTfNumberCellLineNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();
				rightTfNumberCellLineNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations();

				if( leftTfNumberCellLineNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftTfNumberCellLineNumber2NumberofPermutations,
							rightTfNumberCellLineNumber2NumberofPermutations);

					leftTfNumberCellLineNumber2NumberofPermutations = null;
				}

				// KEGGPathway
				leftExonBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				leftRegulationBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				leftAllBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				rightExonBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations();
				rightRegulationBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations();
				rightAllBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations();

				// EXON BASED KEGG PATHWAY
				if( leftExonBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftExonBasedKeggPathwayNumber2NumberofPermutations,
							rightExonBasedKeggPathwayNumber2NumberofPermutations);

					leftExonBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// REGULATION BASED KEGG PATHWAY
				if( leftRegulationBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftRegulationBasedKeggPathwayNumber2NumberofPermutations,
							rightRegulationBasedKeggPathwayNumber2NumberofPermutations);

					leftRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// ALL BASED KEGG PATHWAY
				if( leftAllBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes( leftAllBasedKeggPathwayNumber2NumberofPermutations,
							rightAllBasedKeggPathwayNumber2NumberofPermutations);

					leftAllBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF KEGGPathway
				leftTfNumberExonBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				leftTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				leftTfNumberAllBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				rightTfNumberExonBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				rightTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				rightTfNumberAllBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				// TF and EXON BASED KEGG PATHWAY
				if( leftTfNumberExonBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberExonBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberExonBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberExonBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF and REGULATION BASED KEGG PATHWAY
				if( leftTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF and ALL BASED KEGG PATHWAY
				if( leftTfNumberAllBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberAllBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberAllBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberAllBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF CellLine KEGGPathway
				leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations = leftAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				rightTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations();
				rightTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations();
				rightTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations = rightAllMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations();

				// TF and CellLine and EXON BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF and CellLine and REGULATION BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				// TF and CellLine and ALL BASED KEGG PATHWAY
				if( leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations != null){

					accumulateLeftMapInRightMapForAllChromosomes(
							leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations,
							rightTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations);

					leftTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations = null;
				}

				break;

			default:
				break;

			}// End of SWITCH

			// deleteAllMaps(leftAllMaps);
			// Free space
			leftAllMapsWithNumbersForAllChromosomes = null;

		}// End of Accumulation of left maps in right maps

		// 30 June 2015 starts
		protected void accumulateLeftMapInRightMapForAllChromosomes(
				TLongByteMap leftElementNumberNumber2PermutationOneorZeroMap,
				TLongIntMap rightMapWithNumbersForAllChromosomes) {

			long elementNumber;
			byte permutationOneorZero;

			for( TLongByteIterator it = leftElementNumberNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){

				it.advance();

				elementNumber = it.key();
				permutationOneorZero = it.value();

				if( !( rightMapWithNumbersForAllChromosomes.containsKey( elementNumber))){
					rightMapWithNumbersForAllChromosomes.put( elementNumber, permutationOneorZero);
				}else{
					rightMapWithNumbersForAllChromosomes.put( elementNumber,
							rightMapWithNumbersForAllChromosomes.get( elementNumber) + permutationOneorZero);
				}
			}// End of FOR traversing each elementNumber

			leftElementNumberNumber2PermutationOneorZeroMap.clear();
			leftElementNumberNumber2PermutationOneorZeroMap = null;

		}

		// 30 June 2015 ends

		// 29 June 2015 starts
		protected void accumulateLeftMapInRightMapForAllChromosomes(
				TIntByteMap leftElementNumber2PermutationOneorZeroMap, TIntIntMap rightMapWithNumbersForAllChromosomes) {

			int elementNumber;
			byte permutationOneorZero;

			for( TIntByteIterator it = leftElementNumber2PermutationOneorZeroMap.iterator(); it.hasNext();){

				it.advance();

				elementNumber = it.key();
				permutationOneorZero = it.value();

				if( !( rightMapWithNumbersForAllChromosomes.containsKey( elementNumber))){
					rightMapWithNumbersForAllChromosomes.put( elementNumber, permutationOneorZero);
				}else{
					rightMapWithNumbersForAllChromosomes.put( elementNumber,
							rightMapWithNumbersForAllChromosomes.get( elementNumber) + permutationOneorZero);
				}
			}// End of FOR traversing each elementNumber

			leftElementNumber2PermutationOneorZeroMap.clear();
			leftElementNumber2PermutationOneorZeroMap = null;

		}

		// 29 June 2015 ends

		// 30 June 2015 starts
		protected void accumulateLeftMapInRightMapForAllChromosomes( TLongIntMap leftMapWithNumbersForAllChromosomes,
				TLongIntMap rightMapWithNumbersForAllChromosomes) {

			long elementNumber;
			int numberofPermutationsGreaterThanEqualToOriginalNumberofOverlaps;

			for( TLongIntIterator it = leftMapWithNumbersForAllChromosomes.iterator(); it.hasNext();){

				it.advance();

				elementNumber = it.key();
				numberofPermutationsGreaterThanEqualToOriginalNumberofOverlaps = it.value();

				if( !( rightMapWithNumbersForAllChromosomes.containsKey( elementNumber))){
					rightMapWithNumbersForAllChromosomes.put( elementNumber,
							numberofPermutationsGreaterThanEqualToOriginalNumberofOverlaps);
				}else{
					rightMapWithNumbersForAllChromosomes.put(
							elementNumber,
							rightMapWithNumbersForAllChromosomes.get( elementNumber) + numberofPermutationsGreaterThanEqualToOriginalNumberofOverlaps);
				}

			}// End of FOR

			leftMapWithNumbersForAllChromosomes.clear();
			leftMapWithNumbersForAllChromosomes = null;

		}

		// 30 June 2015 ends

		// 26 June 2015 starts
		// TIntIntMap version starts
		// Accumulate numberofPermutations that has numberOfOverlaps greater than or equal to the original number of
		// overlaps in the leftMap to the rightMap
		// based on elementNumber
		protected void accumulateLeftMapInRightMapForAllChromosomes( TIntIntMap leftMapWithNumbersForAllChromosomes,
				TIntIntMap rightMapWithNumbersForAllChromosomes) {

			int elementNumber;
			int numberofPermutationsGreaterThanEqualToOriginalNumberofOverlaps;

			for( TIntIntIterator it = leftMapWithNumbersForAllChromosomes.iterator(); it.hasNext();){

				it.advance();

				elementNumber = it.key();
				numberofPermutationsGreaterThanEqualToOriginalNumberofOverlaps = it.value();

				if( !( rightMapWithNumbersForAllChromosomes.containsKey( elementNumber))){
					rightMapWithNumbersForAllChromosomes.put( elementNumber,
							numberofPermutationsGreaterThanEqualToOriginalNumberofOverlaps);
				}else{
					rightMapWithNumbersForAllChromosomes.put(
							elementNumber,
							rightMapWithNumbersForAllChromosomes.get( elementNumber) + numberofPermutationsGreaterThanEqualToOriginalNumberofOverlaps);
				}
			}// End of FOR

			leftMapWithNumbersForAllChromosomes.clear();
			leftMapWithNumbersForAllChromosomes = null;

		}

		// 26 June 2015 ends

		// TIntIntMap version starts
		// Accumulate leftMapWithNumbers in the rightMapWithNumbers
		// Accumulate number of overlaps
		// based on permutationNumber and ElementName
		protected void combineLeftMapandRightMap( TIntIntMap leftMapWithNumbers, TIntIntMap rightMapWithNumbers) {

			int permutationNumberCellLineNumberOrKeggPathwayNumber;
			int numberofOverlaps;

			for( TIntIntIterator it = leftMapWithNumbers.iterator(); it.hasNext();){

				it.advance();

				permutationNumberCellLineNumberOrKeggPathwayNumber = it.key();
				numberofOverlaps = it.value();

				// For debug purposes starts
				// System.out.println("permutationNumberCellLineNumberOrKeggPathwayNumber: "
				// + permutationNumberCellLineNumberOrKeggPathwayNumber);
				// For debug purposes ends

				if( !( rightMapWithNumbers.containsKey( permutationNumberCellLineNumberOrKeggPathwayNumber))){
					rightMapWithNumbers.put( permutationNumberCellLineNumberOrKeggPathwayNumber, numberofOverlaps);
				}else{
					rightMapWithNumbers.put(
							permutationNumberCellLineNumberOrKeggPathwayNumber,
							rightMapWithNumbers.get( permutationNumberCellLineNumberOrKeggPathwayNumber) + numberofOverlaps);
				}
			}

			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;

		}

		// TIntIntMap version ends

		// Accumulate leftMapWithNumbers in the rightMapWithNumbers
		// Accumulate number of overlaps
		// based on permutationNumber and ElementNumber
		protected void combineLeftMapandRightMap( TLongIntMap leftMapWithNumbers, TLongIntMap rightMapWithNumbers) {

			long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
			int numberofOverlaps;

			for( TLongIntIterator it = leftMapWithNumbers.iterator(); it.hasNext();){

				it.advance();

				permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
				numberofOverlaps = it.value();

				if( !( rightMapWithNumbers.containsKey( permutationNumberElementNumberCellLineNumberKeggPathwayNumber))){
					rightMapWithNumbers.put( permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
							numberofOverlaps);
				}else{
					rightMapWithNumbers.put(
							permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
							rightMapWithNumbers.get( permutationNumberElementNumberCellLineNumberKeggPathwayNumber) + numberofOverlaps);
				}
			}

			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;

		}

		protected void deleteRandomlyGeneratedData( List<InputLine> randomlyGeneratedData) {

			for( InputLine inputLine : randomlyGeneratedData){
				inputLine.setChrName( null);
				inputLine = null;
			}

			randomlyGeneratedData.clear();
		}

		protected void deleteMap( Map<String, Integer> map) {

			if( map != null){
				for( Map.Entry<String, Integer> entry : map.entrySet()){
					entry.setValue( null);
					entry = null;
				}
				map = null;
			}

		}

		protected void deleteAllMaps( AllMaps allMaps) {

			Map<String, Integer> map = allMaps.getPermutationNumberDnaseCellLineName2KMap();
			deleteMap( map);
			map = allMaps.getPermutationNumberTfNameCellLineName2KMap();
			deleteMap( map);
			map = allMaps.getPermutationNumberHistoneNameCellLineName2KMap();
			deleteMap( map);
			map = allMaps.getPermutationNumberExonBasedKeggPathway2KMap();
			deleteMap( map);
			map = allMaps.getPermutationNumberRegulationBasedKeggPathway2KMap();
			deleteMap( map);
			allMaps = null;
		}

	}
	// AnnotateWithNumbersForAllChromosomes
	// Static Nested Class ends
	
	

	static class AnnotateWithNumbers extends RecursiveTask<AllMapsWithNumbers> {

		private static final long serialVersionUID = 2919115895116169524L;
		private final ChromosomeName chromName;
		private final TIntObjectMap<List<InputLineMinimal>> randomlyGeneratedDataMap;
		private final int runNumber;
		private final int numberofPermutations;
		private final int numberofProcessors;
		

		private final WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode;

		private final TIntList permutationNumberList;
		private final IntervalTree intervalTree;
		private final IntervalTree ucscRefSeqGenesIntervalTree;

		private final AnnotationType annotationType;

		private final int lowIndex;
		private final int highIndex;

		private final TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap;

		private final String outputFolder;

		private final int overlapDefinition;
		
		private final EnrichmentPermutationDivisionType enrichmentPermutationDivisionType;

		public AnnotateWithNumbers(
				String outputFolder,
				ChromosomeName chromName,
				TIntObjectMap<List<InputLineMinimal>> randomlyGeneratedDataMap,
				int runNumber,
				int numberofPermutations,
				int numberofProcessors,
				WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode,
				int lowIndex, 
				int highIndex, 
				TIntList permutationNumberList, 
				IntervalTree intervalTree,
				IntervalTree ucscRefSeqGenesIntervalTree, 
				AnnotationType annotationType,
				TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap, 
				int overlapDefinition,
				EnrichmentPermutationDivisionType enrichmentPermutationDivisionType) {

			this.outputFolder = outputFolder;

			this.chromName = chromName;
			this.randomlyGeneratedDataMap = randomlyGeneratedDataMap;
			this.runNumber = runNumber;
			this.numberofPermutations = numberofPermutations;
			this.numberofProcessors = numberofProcessors;

			this.writePermutationBasedandParametricBasedAnnotationResultMode = writePermutationBasedandParametricBasedAnnotationResultMode;

			this.lowIndex = lowIndex;
			this.highIndex = highIndex;

			this.permutationNumberList = permutationNumberList;
			this.intervalTree = intervalTree;

			// sent full when annotationType is TF_KEGG_PATHWAY_ANNOTATION
			// sent full when annotationType is
			// TF_CELLLINE_KEGG_PATHWAY_ANNOTATION
			// otherwise sent null
			this.ucscRefSeqGenesIntervalTree = ucscRefSeqGenesIntervalTree;

			this.annotationType = annotationType;
			// this.tfandKeggPathwayEnrichmentType =
			// tfandKeggPathwayEnrichmentType;

			// geneId2ListofGeneSetNumberMap
			// sent full when annotationType is KEGG_PATHWAY_ANNOTATION
			// sent full when annotationType is TF_KEGG_PATHWAY_ANNOTATION
			// sent full when annotationType is TF_CELLLINE_KEGG_PATHWAY_ANNOTATION
			// sent full when annotationType is USER_DEFINED_GENE_SET_ANNOTATION
			// otherwise sent null
			this.geneId2ListofGeneSetNumberMap = geneId2ListofGeneSetNumberMap;

			this.overlapDefinition = overlapDefinition;
			
			this.enrichmentPermutationDivisionType = enrichmentPermutationDivisionType;
		}

		protected AllMapsWithNumbers compute() {

			int middleIndex;
			AllMapsWithNumbers rightAllMapsWithNumbers;
			AllMapsWithNumbers leftAllMapsWithNumbers;

			Integer permutationNumber;
			List<AllMapsWithNumbers> listofAllMapsWithNumbers;
			AllMapsWithNumbers allMapsWithNumbers;
			
			int numberofPermutationsForEachProcessor = numberofPermutations/numberofProcessors;
			
			if (enrichmentPermutationDivisionType.isDividePermutationsAsLongAsNumberofPermutationsIsGreaterThanNumberofProcessors()){
				
				// DIVIDE
				if( highIndex - lowIndex > Commons.NUMBER_OF_ANNOTATE_RANDOM_DATA_TASK_DONE_IN_SEQUENTIALLY){
					
					middleIndex = lowIndex + ( highIndex - lowIndex) / 2;
					
					AnnotateWithNumbers left = new AnnotateWithNumbers(
							outputFolder, 
							chromName, 
							randomlyGeneratedDataMap,
							runNumber, 
							numberofPermutations, 
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode,
							lowIndex, 
							middleIndex, 
							permutationNumberList, 
							intervalTree, 
							ucscRefSeqGenesIntervalTree,
							annotationType, 
							geneId2ListofGeneSetNumberMap, 
							overlapDefinition,
							enrichmentPermutationDivisionType);
					
					AnnotateWithNumbers right = new AnnotateWithNumbers(
							outputFolder, 
							chromName, 
							randomlyGeneratedDataMap,
							runNumber, 
							numberofPermutations, 
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode,
							middleIndex, 
							highIndex, 
							permutationNumberList, 
							intervalTree, 
							ucscRefSeqGenesIntervalTree,
							annotationType, 
							geneId2ListofGeneSetNumberMap, 
							overlapDefinition,
							enrichmentPermutationDivisionType);
					
					left.fork();
					rightAllMapsWithNumbers = right.compute();
					leftAllMapsWithNumbers = left.join();
					combineLeftAllMapsandRightAllMaps(leftAllMapsWithNumbers, rightAllMapsWithNumbers);
					leftAllMapsWithNumbers = null;
					return rightAllMapsWithNumbers;
					
				}//End of DIVIDE
				
				// CONQUER
				else{

					listofAllMapsWithNumbers = new ArrayList<AllMapsWithNumbers>();
					allMapsWithNumbers = new AllMapsWithNumbers();

					for( int i = lowIndex; i < highIndex; i++){
						permutationNumber = permutationNumberList.get( i);

						// WITHOUT IO WithNumbers
						if( writePermutationBasedandParametricBasedAnnotationResultMode.isDoNotWritePermutationBasedandParametricBasedAnnotationResultMode()){
							listofAllMapsWithNumbers.add( Annotation.annotatePermutationWithoutIOWithNumbers(
									permutationNumber, 
									chromName, 
									randomlyGeneratedDataMap.get( permutationNumber),
									intervalTree, 
									ucscRefSeqGenesIntervalTree, 
									annotationType,
									geneId2ListofGeneSetNumberMap, 
									overlapDefinition));
						}

						// WITH IO WithNumbers
						else if( writePermutationBasedandParametricBasedAnnotationResultMode.isWritePermutationBasedandParametricBasedAnnotationResultMode()){
							listofAllMapsWithNumbers.add( Annotation.annotatePermutationWithIOWithNumbers( 
									outputFolder,
									permutationNumber, 
									chromName, 
									randomlyGeneratedDataMap.get( permutationNumber),
									intervalTree, 
									ucscRefSeqGenesIntervalTree, 
									annotationType,
									geneId2ListofGeneSetNumberMap, 
									overlapDefinition));
						}
					}// End of FOR

					combineListofAllMapsWithNumbers( listofAllMapsWithNumbers, allMapsWithNumbers);

					listofAllMapsWithNumbers = null;
					return allMapsWithNumbers;

				}//End of CONQUER
				
			}//End of DividePermutationsAsLongAsNumberofPermutationsIsGreaterThanNumberofProcessors
			
			else if (enrichmentPermutationDivisionType.isDividePermutationsAsMuchAsNumberofProcessors()){
				
				// DIVIDE
				// As Much As Number of Processors
				if (highIndex-lowIndex > numberofPermutationsForEachProcessor){
					
					AnnotateWithNumbers left = new AnnotateWithNumbers(
							outputFolder, 
							chromName, 
							randomlyGeneratedDataMap,
							runNumber, 
							numberofPermutations, 
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode,
							lowIndex, 
							lowIndex+numberofPermutationsForEachProcessor, 
							permutationNumberList, 
							intervalTree, 
							ucscRefSeqGenesIntervalTree,
							annotationType, 
							geneId2ListofGeneSetNumberMap, 
							overlapDefinition,
							enrichmentPermutationDivisionType);
						
					AnnotateWithNumbers right = new AnnotateWithNumbers(
							outputFolder, 
							chromName, 
							randomlyGeneratedDataMap,
							runNumber, 
							numberofPermutations, 
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode,
							lowIndex+numberofPermutationsForEachProcessor+1, 
							highIndex, 
							permutationNumberList, 
							intervalTree, 
							ucscRefSeqGenesIntervalTree,
							annotationType, 
							geneId2ListofGeneSetNumberMap, 
							overlapDefinition,
							enrichmentPermutationDivisionType);
					
					left.fork();
					rightAllMapsWithNumbers = right.compute();
					leftAllMapsWithNumbers = left.join();
					combineLeftAllMapsandRightAllMaps( leftAllMapsWithNumbers, rightAllMapsWithNumbers);
					leftAllMapsWithNumbers = null;
					return rightAllMapsWithNumbers;
					
				}//End of DIVIDE
				
				
				// CONQUER
				else{

					listofAllMapsWithNumbers = new ArrayList<AllMapsWithNumbers>();
					allMapsWithNumbers = new AllMapsWithNumbers();

					for( int i = lowIndex; i < highIndex; i++){
						permutationNumber = permutationNumberList.get( i);

						// WITHOUT IO WithNumbers
						if( writePermutationBasedandParametricBasedAnnotationResultMode.isDoNotWritePermutationBasedandParametricBasedAnnotationResultMode()){
							listofAllMapsWithNumbers.add( Annotation.annotatePermutationWithoutIOWithNumbers(
									permutationNumber, chromName, randomlyGeneratedDataMap.get( permutationNumber),
									intervalTree, ucscRefSeqGenesIntervalTree, annotationType,
									geneId2ListofGeneSetNumberMap, overlapDefinition));
						}

						// WITH IO WithNumbers
						else if( writePermutationBasedandParametricBasedAnnotationResultMode.isWritePermutationBasedandParametricBasedAnnotationResultMode()){
							listofAllMapsWithNumbers.add( Annotation.annotatePermutationWithIOWithNumbers( outputFolder,
									permutationNumber, chromName, randomlyGeneratedDataMap.get( permutationNumber),
									intervalTree, ucscRefSeqGenesIntervalTree, annotationType,
									geneId2ListofGeneSetNumberMap, overlapDefinition));
						}
					}// End of FOR

					combineListofAllMapsWithNumbers( listofAllMapsWithNumbers, allMapsWithNumbers);

					listofAllMapsWithNumbers = null;
					return allMapsWithNumbers;

				}//End of CONQUER
				
			}//End of DividePermutationsAsMuchAsNumberofProcessors
			
			
			return null;
		}

		// Accumulate the allMaps in the left into listofAllMaps in the right
		protected void combineListofAllMapsWithNumbers(
				List<AllMapsWithNumbers> listofAllMaps,
				AllMapsWithNumbers allMaps) {

			for( int i = 0; i < listofAllMaps.size(); i++){
				combineLeftAllMapsandRightAllMaps( listofAllMaps.get( i), allMaps);
			}
		}

		// Combine leftAllMapsWithNumbers and rightAllMapsWithNumbers in rightAllMapsWithNumbers
		protected void combineLeftAllMapsandRightAllMaps(
				AllMapsWithNumbers leftAllMapsWithNumbers,
				AllMapsWithNumbers rightAllMapsWithNumbers) {

			// LEFT ALL MAPS WITH NUMBERS
			// DNASE
			TIntIntMap leftPermutationNumberDnaseCellLineNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap();

			// TF
			TLongIntMap leftPermutationNumberTfNumberCellLineNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap();

			// HISTONE
			TLongIntMap leftPermutationNumberHistoneNumberCellLineNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap();

			// Gene
			TLongIntMap leftPermutationNumberGeneNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberGeneNumber2KMap();

			// USERDEFINED GENESET
			TLongIntMap leftPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap();
			TLongIntMap leftPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap();
			TLongIntMap leftPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap();

			// USERDEFINED LIBRARY
			TLongIntMap leftPermutationNumberElementTypeNumberElementNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberElementTypeNumberElementNumber2KMap();

			// KEGG Pathway
			TIntIntMap leftPermutationNumberExonBasedKeggPathwayNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap();
			TIntIntMap leftPermutationNumberRegulationBasedKeggPathwayNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap();
			TIntIntMap leftPermutationNumberAllBasedKeggPathwayNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap();

			// TF KEGGPathway
			TLongIntMap leftPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap();
			TLongIntMap leftPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap();
			TLongIntMap leftPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap();

			// TF CellLine KEGGPathway
			TLongIntMap leftPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap();
			TLongIntMap leftPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap();
			TLongIntMap leftPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap();

			// RIGHT ALL MAPS WITH NUMBERS
			// DNASE
			TIntIntMap rightPermutationNumberDnaseCellLineNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap();

			// TF
			TLongIntMap rightPermutationNumberTfNumberCellLineNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap();

			// HISTONE
			TLongIntMap rightPermutationNumberHistoneNumberCellLineNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap();

			// Gene
			TLongIntMap rightPermutationNumberGeneNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberGeneNumber2KMap();

			// USERDEFINED GENESET
			TLongIntMap rightPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap();
			TLongIntMap rightPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap();
			TLongIntMap rightPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap();

			// USERDEFINED LIBRARY
			TLongIntMap rightPermutationNumberElementTypeNumberElementNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberElementTypeNumberElementNumber2KMap();

			// KEGG Pathway
			TIntIntMap rightPermutationNumberExonBasedKeggPathwayNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap();
			TIntIntMap rightPermutationNumberRegulationBasedKeggPathwayNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap();
			TIntIntMap rightPermutationNumberAllBasedKeggPathwayNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap();

			// TF KEGGPathway
			TLongIntMap rightPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap();
			TLongIntMap rightPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap();
			TLongIntMap rightPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap();

			// TF CellLine KEGGPathway
			TLongIntMap rightPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap();
			TLongIntMap rightPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap();
			TLongIntMap rightPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap = rightAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap();

			// DNASE
			if( leftPermutationNumberDnaseCellLineNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberDnaseCellLineNumber2KMap,
						rightPermutationNumberDnaseCellLineNumber2KMap);
				leftPermutationNumberDnaseCellLineNumber2KMap = null;
			}

			// TF
			if( leftPermutationNumberTfNumberCellLineNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberTfNumberCellLineNumber2KMap,
						rightPermutationNumberTfNumberCellLineNumber2KMap);
				leftPermutationNumberTfNumberCellLineNumber2KMap = null;
			}

			// HISTONE
			if( leftPermutationNumberHistoneNumberCellLineNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberHistoneNumberCellLineNumber2KMap,
						rightPermutationNumberHistoneNumberCellLineNumber2KMap);
				leftPermutationNumberHistoneNumberCellLineNumber2KMap = null;
			}

			// GENE
			if( leftPermutationNumberGeneNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberGeneNumber2KMap, rightPermutationNumberGeneNumber2KMap);
				leftPermutationNumberGeneNumber2KMap = null;
			}

			// USERDEFINED GENESET starts
			// EXON BASED USERDEFINED GENESET
			if( leftPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap,
						rightPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap);
				leftPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap = null;
			}

			// REGULATION BASED USERDEFINED GENESET
			if( leftPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap,
						rightPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap);
				leftPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap = null;
			}

			// ALL BASED USERDEFINED GENESET
			if( leftPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap,
						rightPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap);
				leftPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap = null;
			}
			// USERDEFINED GENESET ends

			// USERDEFINED LIBRARY starts
			if( leftPermutationNumberElementTypeNumberElementNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberElementTypeNumberElementNumber2KMap,
						rightPermutationNumberElementTypeNumberElementNumber2KMap);
				leftPermutationNumberElementTypeNumberElementNumber2KMap = null;
			}
			// USERDEFINED LIBRARY ends

			// EXON BASED KEGG PATHWAY
			if( leftPermutationNumberExonBasedKeggPathwayNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberExonBasedKeggPathwayNumber2KMap,
						rightPermutationNumberExonBasedKeggPathwayNumber2KMap);
				leftPermutationNumberExonBasedKeggPathwayNumber2KMap = null;
			}

			// REGULATION BASED KEGG PATHWAY
			if( leftPermutationNumberRegulationBasedKeggPathwayNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberRegulationBasedKeggPathwayNumber2KMap,
						rightPermutationNumberRegulationBasedKeggPathwayNumber2KMap);
				leftPermutationNumberRegulationBasedKeggPathwayNumber2KMap = null;
			}

			// ALL BASED KEGG PATHWAY
			if( leftPermutationNumberAllBasedKeggPathwayNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberAllBasedKeggPathwayNumber2KMap,
						rightPermutationNumberAllBasedKeggPathwayNumber2KMap);
				leftPermutationNumberAllBasedKeggPathwayNumber2KMap = null;
			}

			// TF and EXON BASED KEGG PATHWAY
			if( leftPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap,
						rightPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap);
				leftPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap = null;
			}

			// TF and REGULATION BASED KEGG PATHWAY
			if( leftPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap,
						rightPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap);
				leftPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap = null;
			}

			// TF and ALL BASED KEGG PATHWAY
			if( leftPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap,
						rightPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap);
				leftPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap = null;
			}

			// TF and CellLine and EXON BASED KEGG PATHWAY
			if( leftPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap,
						rightPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap);
				leftPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap = null;
			}

			// TF and CellLine and REGULATION BASED KEGG PATHWAY
			if( leftPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap != null){
				combineLeftMapandRightMap(
						leftPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap,
						rightPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap);
				leftPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap = null;
			}

			// TF and CellLine and ALL BASED KEGG PATHWAY
			if( leftPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap != null){
				combineLeftMapandRightMap( leftPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap,
						rightPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap);
				leftPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap = null;
			}

			// delete AllMaps
			// deleteAllMaps(leftAllMaps);
			leftAllMapsWithNumbers = null;

		}// End of combineAllMaps

		// TIntIntMap version starts
		// Accumulate leftMapWithNumbers in the rightMapWithNumbers
		// Accumulate number of overlaps
		// based on permutationNumber and ElementName
		protected void combineLeftMapandRightMap(
				TIntIntMap leftMapWithNumbers, 
				TIntIntMap rightMapWithNumbers) {

			for( TIntIntIterator it = leftMapWithNumbers.iterator(); it.hasNext();){

				it.advance();

				int permutationNumberCellLineNumberOrKeggPathwayNumber = it.key();
				int numberofOverlaps = it.value();

				// For debug purposes starts
				// System.out.println("permutationNumberCellLineNumberOrKeggPathwayNumber: "
				// + permutationNumberCellLineNumberOrKeggPathwayNumber);
				// For debug purposes ends

				if( !( rightMapWithNumbers.containsKey( permutationNumberCellLineNumberOrKeggPathwayNumber))){
					rightMapWithNumbers.put( permutationNumberCellLineNumberOrKeggPathwayNumber, numberofOverlaps);
				}else{
					rightMapWithNumbers.put(
							permutationNumberCellLineNumberOrKeggPathwayNumber,
							rightMapWithNumbers.get( permutationNumberCellLineNumberOrKeggPathwayNumber) + numberofOverlaps);
				}
			}

			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;

		}

		// TIntIntMap version ends

		// Accumulate leftMapWithNumbers in the rightMapWithNumbers
		// Accumulate number of overlaps
		// based on permutationNumber and ElementNumber
		protected void combineLeftMapandRightMap(
				TLongIntMap leftMapWithNumbers, 
				TLongIntMap rightMapWithNumbers) {

			for( TLongIntIterator it = leftMapWithNumbers.iterator(); it.hasNext();){

				it.advance();

				long permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
				int numberofOverlaps = it.value();

				if( !( rightMapWithNumbers.containsKey( permutationNumberElementNumberCellLineNumberKeggPathwayNumber))){
					rightMapWithNumbers.put( permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
							numberofOverlaps);
				}else{
					rightMapWithNumbers.put(
							permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
							rightMapWithNumbers.get( permutationNumberElementNumberCellLineNumberKeggPathwayNumber) + numberofOverlaps);
				}
			}

			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;

		}

//		protected void deleteRandomlyGeneratedData( List<InputLine> randomlyGeneratedData) {
//
//			for( InputLine inputLine : randomlyGeneratedData){
//				inputLine.setChrName( null);
//				inputLine = null;
//			}
//
//			randomlyGeneratedData.clear();
//		}

//		protected void deleteMap( Map<String, Integer> map) {
//
//			if( map != null){
//				for( Map.Entry<String, Integer> entry : map.entrySet()){
//					entry.setValue( null);
//					entry = null;
//				}
//				map = null;
//			}
//
//		}

//		protected void deleteAllMaps( AllMaps allMaps) {
//
//			Map<String, Integer> map = allMaps.getPermutationNumberDnaseCellLineName2KMap();
//			deleteMap( map);
//			map = allMaps.getPermutationNumberTfNameCellLineName2KMap();
//			deleteMap( map);
//			map = allMaps.getPermutationNumberHistoneNameCellLineName2KMap();
//			deleteMap( map);
//			map = allMaps.getPermutationNumberExonBasedKeggPathway2KMap();
//			deleteMap( map);
//			map = allMaps.getPermutationNumberRegulationBasedKeggPathway2KMap();
//			deleteMap( map);
//			allMaps = null;
//		}

	}

	public static void readOriginalInputDataLines( List<InputLine> originalInputLines, String inputFileName) {

		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;

		int indexofFirstTab;
		int indexofSecondTab;

		ChromosomeName chrName;
		int low;
		int high;

		GlanetRunner.appendLog( "Input data file name is: " + inputFileName);
		if( GlanetRunner.shouldLog())logger.info( "Input data file name is: " + inputFileName);

		try{
			fileReader = new FileReader( inputFileName);
			bufferedReader = new BufferedReader( fileReader);

			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);

				chrName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));

				if( indexofSecondTab > 0){
					low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1));
				}else{
					low = Integer.parseInt( strLine.substring( indexofFirstTab + 1));
					high = low;
				}

				InputLine originalInputLine = new InputLine( chrName, low, high);
				originalInputLines.add( originalInputLine);

			}
		}catch( FileNotFoundException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	public static void partitionDataChromosomeBased( List<InputLine> originalInputLines,
			Map<ChromosomeName, List<InputLineMinimal>> chromosomeName2OriginalInputLinesMap) {

		InputLineMinimal inputLineMinimal = null;
		ChromosomeName chrName;
		List<InputLineMinimal> list;

		for( int i = 0; i < originalInputLines.size(); i++){

			inputLineMinimal = new InputLineMinimal( originalInputLines.get( i).getLow(),
					originalInputLines.get( i).getHigh());
			chrName = originalInputLines.get( i).getChrName();
			list = chromosomeName2OriginalInputLinesMap.get( chrName);

			if( list == null){
				list = new ArrayList<InputLineMinimal>();
				list.add( inputLineMinimal);
				chromosomeName2OriginalInputLinesMap.put( chrName, list);
			}else{
				list.add( inputLineMinimal);
				chromosomeName2OriginalInputLinesMap.put( chrName, list);
			}
		}
	}

	// TLongIntMap TIntObjectMap<TIntList> TIntIntMap starts
	public static void convert( 
			TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap,
			TIntObjectMap<TIntList> elementNumber2AllKMap, 
			TIntIntMap elementNumber2OriginalKMap,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;
		Integer permutationNumberRemovedMixedNumber;

		Integer numberofOverlaps;

		TIntList list;

		for( TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();){

			it.advance();

			// example permutationAugmentedName PERMUTATION0_K562
			// get permutationNumber from permutationAugmentedName
			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();
			
			permutationNumber = IntervalTree.getPermutationNumber(
					permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
					generatedMixedNumberDescriptionOrderLength);

			permutationNumberRemovedMixedNumber = IntervalTree.getPermutationNumberRemovedMixedNumber(
					permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
					generatedMixedNumberDescriptionOrderLength);

			// example permutationNumber PERMUTATION0

			// @todo check this zero value for permutation of original data
			if( Commons.ZERO.equals( permutationNumber)){
				elementNumber2OriginalKMap.put( permutationNumberRemovedMixedNumber, numberofOverlaps);
			}else{
				list = elementNumber2AllKMap.get( permutationNumberRemovedMixedNumber);

				if( list == null){
					list = new TIntArrayList();
					list.add( numberofOverlaps);
					elementNumber2AllKMap.put( permutationNumberRemovedMixedNumber, list);
				}else{
					list.add( numberofOverlaps);
					elementNumber2AllKMap.put( permutationNumberRemovedMixedNumber, list);
				}
			}
		}// End of for

	}

	// TLongIntMap TIntObjectMap<TIntList> TIntIntMap ends

	// TIntIntMap TIntObjectMap<TIntList> TIntIntMap version starts
	public static void convert(
			TIntIntMap permutationNumberCellLineNumberOrGeneSetNumber2KMap,
			TIntObjectMap<TIntList> cellLineNumberorGeneSetNumber2AllKMap,
			TIntIntMap cellLineNumberorGeneSetNumber2OriginalKMap,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int permutationNumberCellLineNumberOrGeneSetNumber;
		Integer permutationNumber = Commons.MINUS_ONE;
		Integer cellLineNumberOrGeneSetNumber;

		Integer numberofOverlaps;

		TIntList list;

		for( TIntIntIterator it = permutationNumberCellLineNumberOrGeneSetNumber2KMap.iterator(); it.hasNext();){

			it.advance();

			// example permutationAugmentedName PERMUTATION0_K562
			// @todo get permutationNumber from permutationAugmentedName
			permutationNumberCellLineNumberOrGeneSetNumber = it.key();
			numberofOverlaps = it.value();

			permutationNumber = IntervalTree.getPermutationNumber( permutationNumberCellLineNumberOrGeneSetNumber,
					generatedMixedNumberDescriptionOrderLength);

			// INT_4DIGIT_DNASECELLLINENUMBER
			cellLineNumberOrGeneSetNumber = IntervalTree.getCellLineNumberOrGeneSetNumber(
					permutationNumberCellLineNumberOrGeneSetNumber, generatedMixedNumberDescriptionOrderLength);

			// debug starts
			if( cellLineNumberOrGeneSetNumber < 0){
				System.out.println( "there is a situation 2");
			}
			// debug ends

			// example permutationNumber PERMUTATION0

			// @todo check this zero value for permutation of original data
			if( Commons.ZERO.equals( permutationNumber)){
				cellLineNumberorGeneSetNumber2OriginalKMap.put( cellLineNumberOrGeneSetNumber, numberofOverlaps);
			}else{
				list = cellLineNumberorGeneSetNumber2AllKMap.get( cellLineNumberOrGeneSetNumber);

				if( list == null){
					list = new TIntArrayList();
					list.add( numberofOverlaps);
					cellLineNumberorGeneSetNumber2AllKMap.put( cellLineNumberOrGeneSetNumber, list);
				}else{
					list.add( numberofOverlaps);
					cellLineNumberorGeneSetNumber2AllKMap.put( cellLineNumberOrGeneSetNumber, list);
				}
			}
		}

	}

	// TIntIntMap TIntObjectMap<TIntList> TIntIntMap version ends

	// TLongIntMap TLongObjectMap TLongIntMap version starts
	// @todo I must get permutationNumber and elementNumber from combinedNumber
	// convert permutation augmented name to only name
	// Fill elementName2ALLMap and originalElementName2KMap in convert methods
	public static void convert( TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap,
			TLongObjectMap<TIntList> elementNumber2AllKMap, TLongIntMap elementNumber2OriginalKMap,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;
		Long elementNumberCellLineNumberKeggPathwayNumberr;

		Integer numberofOverlaps;

		TIntList list;

		for( TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();){

			it.advance();

			// example permutationAugmentedName PERMUTATION0_K562
			// @todo get permutationNumber from permutationAugmentedName
			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();

			permutationNumber = IntervalTree.getPermutationNumber(
					permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
					generatedMixedNumberDescriptionOrderLength);

			elementNumberCellLineNumberKeggPathwayNumberr = IntervalTree.getPermutationNumberRemovedLongMixedNumber(
					permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
					generatedMixedNumberDescriptionOrderLength);

			// example permutationNumber PERMUTATION0

			// @todo check this zero value for permutation of original data
			if( Commons.ZERO.equals( permutationNumber)){
				elementNumber2OriginalKMap.put( elementNumberCellLineNumberKeggPathwayNumberr, numberofOverlaps);
			}else{
				list = elementNumber2AllKMap.get( elementNumberCellLineNumberKeggPathwayNumberr);

				if( list == null){
					list = new TIntArrayList();
					list.add( numberofOverlaps);
					elementNumber2AllKMap.put( elementNumberCellLineNumberKeggPathwayNumberr, list);
				}else{
					list.add( numberofOverlaps);
					elementNumber2AllKMap.put( elementNumberCellLineNumberKeggPathwayNumberr, list);
				}
			}
		}

	}

	// TLongIntMap TLongObjectMap TLongIntMap version ends

	public void fillMapfromMap( Map<String, Integer> toMap, Map<String, Integer> fromMap) {

		String name;
		Integer numberofOverlaps;

		for( Map.Entry<String, Integer> entry : fromMap.entrySet()){
			name = entry.getKey();
			numberofOverlaps = entry.getValue();

			toMap.put( name, numberofOverlaps);

		}
	}

	// Empirical P Value and Bonferroni Corrected Empirical P Value
	// List<FunctionalElement> list is filled in this method
	// Using name2AccumulatedKMap and originalName2KMap
	public void calculateEmpricalPValues( Integer numberofComparisons, int numberofRepeats, int numberofPermutations,
			Map<String, List<Integer>> name2AccumulatedKMap, Map<String, Integer> originalName2KMap,
			List<FunctionalElement> list) {

		String originalName;
		Integer originalNumberofOverlaps;
		List<Integer> listofNumberofOverlaps;
		Integer numberofOverlaps;
		int numberofPermutationsHavingOverlapsGreaterThanorEqualto = 0;
		Float empiricalPValue;
		Float bonferroniCorrectedEmpiricalPValue;

		FunctionalElement functionalElement;

		// only consider the names in the original name 2 k map
		for( Map.Entry<String, Integer> entry : originalName2KMap.entrySet()){
			originalName = entry.getKey();
			originalNumberofOverlaps = entry.getValue();

			listofNumberofOverlaps = name2AccumulatedKMap.get( originalName);

			// Initialise numberofPermutationsHavingOverlapsGreaterThanorEqualto
			// to zero for each original name
			numberofPermutationsHavingOverlapsGreaterThanorEqualto = 0;

			if( listofNumberofOverlaps != null){
				for( int i = 0; i < listofNumberofOverlaps.size(); i++){

					numberofOverlaps = listofNumberofOverlaps.get( i);

					if( numberofOverlaps >= originalNumberofOverlaps){
						numberofPermutationsHavingOverlapsGreaterThanorEqualto++;
					}
				}// end of for
			}// end of if

			empiricalPValue = ( numberofPermutationsHavingOverlapsGreaterThanorEqualto * 1.0f) / ( numberofRepeats * numberofPermutations);
			bonferroniCorrectedEmpiricalPValue = ( ( numberofPermutationsHavingOverlapsGreaterThanorEqualto * 1.0f) / ( numberofRepeats * numberofPermutations)) * numberofComparisons;

			if( bonferroniCorrectedEmpiricalPValue >= 1){
				bonferroniCorrectedEmpiricalPValue = 1.0f;
			}

			functionalElement = new FunctionalElement();
			functionalElement.setName( originalName);
			functionalElement.setEmpiricalPValue( empiricalPValue);
			functionalElement.setBonferroniCorrectedPValue( bonferroniCorrectedEmpiricalPValue);

			// 18 FEB 2014
			functionalElement.setOriginalNumberofOverlaps( originalNumberofOverlaps);
			functionalElement.setNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps( numberofPermutationsHavingOverlapsGreaterThanorEqualto);
			functionalElement.setNumberofPermutations( numberofRepeats * numberofPermutations);
			functionalElement.setNumberofComparisons( numberofComparisons);

			list.add( functionalElement);

		}// end of for

	}

	public static void fillPermutationNumberList(
			TIntList permutationNumberList, 
			int runNumber,
			int numberofPermutationsinThisRun, 
			int numberofPermutationsinEachRun) {

		for( int permutationNumber = 1; permutationNumber <= numberofPermutationsinThisRun; permutationNumber++){
			permutationNumberList.add( ( runNumber - 1) * numberofPermutationsinEachRun + permutationNumber);
		}

	}

	public static void addPermutationNumberforOriginalData( TIntList permutationNumberList,
			Integer originalDataPermutationNumber) {

		permutationNumberList.add( originalDataPermutationNumber);
	}

	// Generate IntervalTrees for Enrichment starts
	public static IntervalTree generateDnaseIntervalTreeWithNumbers( String dataFolder, ChromosomeName chromName) {

		return Annotation.createDnaseIntervalTreeWithNumbers( dataFolder, chromName);
	}

	public static IntervalTree generateTfbsIntervalTreeWithNumbers( String dataFolder, ChromosomeName chromName) {

		return Annotation.createTfbsIntervalTreeWithNumbers( dataFolder, chromName);
	}

	public static IntervalTree generateHistoneIntervalTreeWithNumbers( String dataFolder, ChromosomeName chromName) {

		return Annotation.createHistoneIntervalTreeWithNumbers( dataFolder, chromName);
	}

	public static IntervalTree generateUcscRefSeqGeneIntervalTreeWithNumbers( String dataFolder,ChromosomeName chromName) {

		return Annotation.createUcscRefSeqGenesIntervalTreeWithNumbers( dataFolder, chromName);
	}

	public static IntervalTree generateUserDefinedLibraryIntervalTreeWithNumbers(
			String dataFolder,
			int elementTypeNumber, 
			String elementType, 
			ChromosomeName chromName) {

		return Annotation.createUserDefinedIntervalTreeWithNumbers(dataFolder,elementTypeNumber,elementType,chromName);
	}
	// Generate IntervalTrees for Enrichment ends

	public static void closeBufferedWriters( Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap) {

		BufferedWriter bufferedWriter = null;
		try{

			for( Map.Entry<Integer, BufferedWriter> entry : permutationNumber2BufferedWriterHashMap.entrySet()){
				bufferedWriter = entry.getValue();
				bufferedWriter.close();
			}
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	public void closeBuferedWriterswithIntegerKey( Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap) {

		BufferedWriter bufferedWriter = null;
		try{

			for( Map.Entry<Integer, BufferedWriter> entry : permutationNumber2BufferedWriterHashMap.entrySet()){
				bufferedWriter = entry.getValue();
				bufferedWriter.close();
			}
		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	// TIntIntMap version starts
	public static void writeAnnotationstoFiles( String outputFolder,
			TIntIntMap permutationNumberCellLineNumberorGeneSetNumber2KMap,
			Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName,
			String extraFileName, GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int permutationNumberCellLineNumberorGeneSetNumber;
		Integer permutationNumber;
		Integer cellLineNumberorGeneSetNumber;

		Integer numberofOverlaps;

		BufferedWriter bufferedWriter = null;

		for( TIntIntIterator it = permutationNumberCellLineNumberorGeneSetNumber2KMap.iterator(); it.hasNext();){

			it.advance();

			permutationNumberCellLineNumberorGeneSetNumber = it.key();
			numberofOverlaps = it.value();

			permutationNumber = IntervalTree.getPermutationNumber( permutationNumberCellLineNumberorGeneSetNumber,
					generatedMixedNumberDescriptionOrderLength);
			cellLineNumberorGeneSetNumber = IntervalTree.getCellLineNumberOrGeneSetNumber(
					permutationNumberCellLineNumberorGeneSetNumber, generatedMixedNumberDescriptionOrderLength);

			bufferedWriter = permutationNumber2BufferedWriterHashMap.get( permutationNumber);

			try{

				if( bufferedWriter == null){
					bufferedWriter = new BufferedWriter(
							FileOperations.createFileWriter( outputFolder + folderName + Commons.PERMUTATION + permutationNumber + "_" + extraFileName + ".txt"));

					bufferedWriter.write( "CellLineNumberOrKeggPathwayNumber" + "\t" + "NumberofOverlaps" + System.getProperty( "line.separator"));

					permutationNumber2BufferedWriterHashMap.put( permutationNumber, bufferedWriter);
				}

				if( cellLineNumberorGeneSetNumber > 0){
					bufferedWriter.write( cellLineNumberorGeneSetNumber + "\t");
				}

				bufferedWriter.write( numberofOverlaps + System.getProperty( "line.separator"));
				bufferedWriter.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}// End of for

	}

	// TIntIntMap version ends

	public static void writeAnnotationstoFiles_ElementNumberCellLineNumber( String outputFolder,
			TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap,
			Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName,
			String extraFileName, GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;
		Integer elementNumber;
		Integer cellLineNumber;

		Integer numberofOverlaps;

		BufferedWriter bufferedWriter = null;

		for( TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();){

			it.advance();

			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();

			permutationNumber = IntervalTree.getPermutationNumber(
					permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
					generatedMixedNumberDescriptionOrderLength);
			elementNumber = IntervalTree.getElementNumber(
					permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
					generatedMixedNumberDescriptionOrderLength);
			cellLineNumber = IntervalTree.getCellLineNumber(
					permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
					generatedMixedNumberDescriptionOrderLength);

			bufferedWriter = permutationNumber2BufferedWriterHashMap.get( permutationNumber);

			try{

				if( bufferedWriter == null){
					bufferedWriter = new BufferedWriter(
							FileOperations.createFileWriter( outputFolder + folderName + Commons.PERMUTATION + permutationNumber + "_" + extraFileName + ".txt"));

					bufferedWriter.write( "TforHistoneNumber" + "\t" + "CellLineNumber" + "\t" + "NumberofOverlaps" + System.getProperty( "line.separator"));

					permutationNumber2BufferedWriterHashMap.put( permutationNumber, bufferedWriter);
				}

				if( elementNumber > 0){
					bufferedWriter.write( elementNumber + "\t");
				}

				if( cellLineNumber > 0){
					bufferedWriter.write( cellLineNumber + "\t");
				}

				bufferedWriter.write( numberofOverlaps + System.getProperty( "line.separator"));

				bufferedWriter.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}// End of for

	}

	public static void writeAnnotationstoFiles_ElementNumberKeggPathwayNumber( String outputFolder,
			TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap,
			Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName,
			String extraFileName, GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;
		Integer elementNumber;
		Integer keggPathwayNumber;

		Integer numberofOverlaps;

		BufferedWriter bufferedWriter = null;

		for( TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();){

			it.advance();

			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();

			permutationNumber = IntervalTree.getPermutationNumber(
					permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
					generatedMixedNumberDescriptionOrderLength);
			elementNumber = IntervalTree.getElementNumber(
					permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
					generatedMixedNumberDescriptionOrderLength);
			keggPathwayNumber = IntervalTree.getGeneSetNumber(
					permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
					generatedMixedNumberDescriptionOrderLength);

			bufferedWriter = permutationNumber2BufferedWriterHashMap.get( permutationNumber);

			try{

				if( bufferedWriter == null){
					bufferedWriter = new BufferedWriter(
							FileOperations.createFileWriter( outputFolder + folderName + Commons.PERMUTATION + permutationNumber + "_" + extraFileName + ".txt"));

					bufferedWriter.write( "TfNumber" + "\t" + "KeggPathwayNumber" + "\t" + "NumberofOverlaps" + System.getProperty( "line.separator"));

					permutationNumber2BufferedWriterHashMap.put( permutationNumber, bufferedWriter);
				}

				if( elementNumber > 0){
					bufferedWriter.write( elementNumber + "\t");
				}

				if( keggPathwayNumber > 0){
					bufferedWriter.write( keggPathwayNumber + "\t");
				}

				bufferedWriter.write( numberofOverlaps + System.getProperty( "line.separator"));
				bufferedWriter.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}// End of for

	}

	public static void writeAnnotationstoFiles( 
			String outputFolder,
			TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap,
			Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap, 
			String folderName,
			String extraFileName, 
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;

		Integer permutationNumber;
		Integer elementNumber;
		Integer cellLineNumber;
		Integer keggPathwayNumber;
		Integer userDefinedGeneSetNumber;
		Integer elementTypeNumber;

		Integer numberofOverlaps;

		BufferedWriter bufferedWriter = null;

		for( TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();){

			it.advance();

			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();

			// Initialize before each mixed number resolution
			permutationNumber = Integer.MIN_VALUE;
			elementNumber = Integer.MIN_VALUE;
			cellLineNumber = Integer.MIN_VALUE;
			keggPathwayNumber = Integer.MIN_VALUE;
			elementTypeNumber = Integer.MIN_VALUE;

			// Get permutation number starts
			permutationNumber = IntervalTree.getPermutationNumber(
					permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
					generatedMixedNumberDescriptionOrderLength);
			// Get permutation number ends

			// Get bufferedWriter starts
			bufferedWriter = permutationNumber2BufferedWriterHashMap.get( permutationNumber);
			// Get bufferedWriter ends

			try{

				if( bufferedWriter == null){
					bufferedWriter = new BufferedWriter(
							FileOperations.createFileWriter( outputFolder + folderName + Commons.PERMUTATION + permutationNumber + "_" + extraFileName + ".txt"));

					// Set header line starts
					switch( generatedMixedNumberDescriptionOrderLength){

						// User Defined Library
						case LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:
							bufferedWriter.write( "ElementTypeNumber" + "\t" + "ElementNumber" + "\t" + "NumberofOverlaps" + System.getProperty( "line.separator"));
							break;
	
						// User Defined Geneset
						case LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER:
							bufferedWriter.write( "UserDefinedGeneSetNumber" + "\t" + "NumberofOverlaps" + System.getProperty( "line.separator"));
							break;
	
						// PermutationNumber_ElementNumber_CellLineNumber_KeggPathwayNumber
						case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:
							bufferedWriter.write( "TfNumber" + "\t" + "CellLineNumber" + "\t" + "KeggPathwayNumber" + "\t" + "NumberofOverlaps" + System.getProperty( "line.separator"));
							break;
	
						default:
							break;
					}// End of SWITCH
					// Set header line ends

					permutationNumber2BufferedWriterHashMap.put( permutationNumber, bufferedWriter);
				}// End of if bufferedWriter is NULL

				// mixed number resolution and write to bufferedWriter starts
				switch( generatedMixedNumberDescriptionOrderLength){

					// User Defined Library
					case LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:
						elementTypeNumber = IntervalTree.getElementTypeNumber(
								permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
								generatedMixedNumberDescriptionOrderLength);
						elementNumber = IntervalTree.getElementNumber(
								permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
								generatedMixedNumberDescriptionOrderLength);
						bufferedWriter.write( elementTypeNumber + "\t" + elementNumber + "\t" + numberofOverlaps + System.getProperty( "line.separator"));
						break;
	
					// User Defined Geneset
					case LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER:
						userDefinedGeneSetNumber = IntervalTree.getGeneSetNumber(
								permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
								generatedMixedNumberDescriptionOrderLength);
						bufferedWriter.write( userDefinedGeneSetNumber + "\t" + numberofOverlaps + System.getProperty( "line.separator"));
	
						break;
	
					// PermutationNumber_ElementNumber_CellLineNumber_KeggPathwayNumber
					case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:
						elementNumber = IntervalTree.getElementNumber(
								permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
								generatedMixedNumberDescriptionOrderLength);
						cellLineNumber = IntervalTree.getCellLineNumber(
								permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
								generatedMixedNumberDescriptionOrderLength);
						keggPathwayNumber = IntervalTree.getGeneSetNumber(
								permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
								generatedMixedNumberDescriptionOrderLength);
						bufferedWriter.write( elementNumber + "\t" + cellLineNumber + "\t" + keggPathwayNumber + "\t" + numberofOverlaps + System.getProperty( "line.separator"));
						break;
	
					default:
						break;
				}// End of SWITCH
				// mixed number resolution and write to bufferedWriter ends

				bufferedWriter.close();
			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}// End of for

	}

	// TIntIntMap version starts
	// Accumulate chromosomeBasedName2KMap results in accumulatedName2KMap
	// Accumulate number of overlaps coming from each chromosome
	// based on permutationNumber and ElementName
	public static void accumulate( TIntIntMap chromosomeBasedName2KMap, TIntIntMap accumulatedName2KMap) {

		int permutationNumberCellLineNumberOrKeggPathwayNumber;
		Integer numberofOverlaps;

		// accessing keys/values through an iterator:
		for( TIntIntIterator it = chromosomeBasedName2KMap.iterator(); it.hasNext();){

			it.advance();

			permutationNumberCellLineNumberOrKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();

			if( !( accumulatedName2KMap.containsKey( permutationNumberCellLineNumberOrKeggPathwayNumber))){
				accumulatedName2KMap.put( permutationNumberCellLineNumberOrKeggPathwayNumber, numberofOverlaps);
			}else{
				accumulatedName2KMap.put(
						permutationNumberCellLineNumberOrKeggPathwayNumber,
						accumulatedName2KMap.get( permutationNumberCellLineNumberOrKeggPathwayNumber) + numberofOverlaps);
			}

		}

	}

	// TIntIntMap version ends

	// Accumulate chromosomeBasedName2KMap results in accumulatedName2KMap
	// Accumulate number of overlaps coming from each chromosome
	// based on permutationNumber and ElementName
	public static void accumulate( TLongIntMap chromosomeBasedName2KMap, TLongIntMap accumulatedName2KMap) {

		Long permutationNumberElementNumber;
		Integer numberofOverlaps;

		// accessing keys/values through an iterator:
		for( TLongIntIterator it = chromosomeBasedName2KMap.iterator(); it.hasNext();){

			it.advance();

			permutationNumberElementNumber = it.key();
			numberofOverlaps = it.value();

			if( !( accumulatedName2KMap.containsKey( permutationNumberElementNumber))){
				accumulatedName2KMap.put( permutationNumberElementNumber, numberofOverlaps);
			}else{
				accumulatedName2KMap.put( permutationNumberElementNumber,
						accumulatedName2KMap.get( permutationNumberElementNumber) + numberofOverlaps);

			}

		}

	}

	// Accumulate chromosomeBasedAllMaps in accumulatedAllMaps
	// Coming from each chromosome
	public static void accumulate( AllMapsDnaseTFHistoneWithNumbers chromosomeBasedAllMaps,
			AllMapsDnaseTFHistoneWithNumbers accumulatedAllMaps, AnnotationType annotationType) {

		switch( annotationType){
		case DO_DNASE_ANNOTATION:
			accumulate( chromosomeBasedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap());
			break;

		case DO_TF_ANNOTATION:
			accumulate( chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap());
			break;

		case DO_HISTONE_ANNOTATION:
			accumulate( chromosomeBasedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap());
			break;

		default:
			break;
		}
	}

	// Accumulate chromosomeBasedAllMaps in accumulatedAllMaps
	// Coming from each chromosome
	public static void accumulate( AllMapsWithNumbers chromosomeBasedAllMaps, AllMapsWithNumbers accumulatedAllMaps,
			AnnotationType annotationType) {

		switch( annotationType){
		case DO_DNASE_ANNOTATION:
			accumulate( chromosomeBasedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap());
			break;

		case DO_TF_ANNOTATION:
			accumulate( chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap());
			break;

		case DO_HISTONE_ANNOTATION:
			accumulate( chromosomeBasedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap());
			break;

		case DO_GENE_ANNOTATION:
			accumulate( chromosomeBasedAllMaps.getPermutationNumberGeneNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberGeneNumber2KMap());
			break;

		case DO_USER_DEFINED_GENESET_ANNOTATION: // Exon Based User Defined
													// GeneSet
			accumulate( chromosomeBasedAllMaps.getPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap());
			// Regulation Based User Defined GeneSet
			accumulate( chromosomeBasedAllMaps.getPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap());
			// All Based User Defined GeneSet
			accumulate( chromosomeBasedAllMaps.getPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap());
			break;

		case DO_USER_DEFINED_LIBRARY_ANNOTATION:
			accumulate( chromosomeBasedAllMaps.getPermutationNumberElementTypeNumberElementNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberElementTypeNumberElementNumber2KMap());
			break;

		case DO_KEGGPATHWAY_ANNOTATION: // Exon Based KEGG Pathway
			accumulate( chromosomeBasedAllMaps.getPermutationNumberExonBasedKeggPathwayNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberExonBasedKeggPathwayNumber2KMap());
			// Regulation Based KEGG Pathway
			accumulate( chromosomeBasedAllMaps.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap());
			// All Based KEGG Pathway
			accumulate( chromosomeBasedAllMaps.getPermutationNumberAllBasedKeggPathwayNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberAllBasedKeggPathwayNumber2KMap());
			break;

		case DO_TF_KEGGPATHWAY_ANNOTATION: // TF and KEGG Pathway Annotation
			accumulate( chromosomeBasedAllMaps.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap());
			accumulate( chromosomeBasedAllMaps.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap());
			accumulate( chromosomeBasedAllMaps.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap());
			break;

		case DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION: // TF and CellLine and
													// KEGG Pathway
													// Annotation
			accumulate(
					chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap());
			accumulate(
					chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap());
			accumulate(
					chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap(),
					accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap());
			break;

		default:
			break;

		}// End od SWITCH

	}

	public void deleteIntervalTrees( List<IntervalTree> listofIntervalTrees) {

		IntervalTree dnaseIntervalTree = listofIntervalTrees.get( 0);
		IntervalTree tfbsIntervalTree = listofIntervalTrees.get( 1);
		IntervalTree histoneIntervalTree = listofIntervalTrees.get( 2);
		IntervalTree ucscRefSeqGenesIntervalTree = listofIntervalTrees.get( 3);

		IntervalTree.deleteNodesofIntervalTree( dnaseIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree( tfbsIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree( histoneIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree( ucscRefSeqGenesIntervalTree.getRoot());

		// dnaseIntervalTree = new IntervalTree();
		dnaseIntervalTree = null;
		tfbsIntervalTree = null;
		histoneIntervalTree = null;
		ucscRefSeqGenesIntervalTree = null;
	}

	// //Has no effect on the execution time
	// public static void deleteIntervalTree(IntervalTree intervalTree) {
	//
	// IntervalTree.deleteNodesofIntervalTree(intervalTree.getRoot());
	// intervalTree.setRoot(null);
	// intervalTree = null;
	// }

	public void deleteGCCharArray( char[] gcCharArray) {

		gcCharArray = null;
	}

	public void deleteMapabilityFloatArray( float[] mapabilityFloatArray) {

		mapabilityFloatArray = null;
	}

	public static boolean containsIntervalBetween( List<InputLineMinimal> inputLines, int intervalLengthLow,
			int intervalLengthHigh) {

		int length;
		boolean contains = false;

		for( int i = 0; i < inputLines.size(); i++){

			length = inputLines.get( i).getHigh() - inputLines.get( i).getLow() + 1;

			if( length > intervalLengthLow && length <= intervalLengthHigh){
				contains = true;
				break;
			}

		}// End of for

		return contains;

	}

	public static boolean containsIntervalLessThanOrEqualTo( List<InputLineMinimal> inputLines, int intervalLength) {

		int length;
		boolean contains = false;

		for( int i = 0; i < inputLines.size(); i++){

			length = inputLines.get( i).getHigh() - inputLines.get( i).getLow() + 1;

			if( length <= intervalLength){
				contains = true;
				break;
			}

		}// End of for

		return contains;

	}

	public static boolean containsIntervalGreaterThan( List<InputLineMinimal> inputLines, int intervalLength) {

		int length;
		boolean contains = false;

		for( int i = 0; i < inputLines.size(); i++){

			length = inputLines.get( i).getHigh() - inputLines.get( i).getLow() + 1;

			if( length > intervalLength){
				contains = true;
				break;
			}

		}// End of for

		return contains;

	}
	
	//9 July 2015
	public static void writeToBeCollectedWithoutZScore( 
			String outputFolder, 
			String outputFileName,
			String runName,
			TLongIntMap elementNumber2OriginalKMap,
			TLongIntMap elementNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap,
			AnnotationType annotationType,
			TIntObjectMap<String> elementNumber2NameMap,
			TIntObjectMap<String> cellLineNumber2NameMap,
			TIntObjectMap<String> keggPathwayNumber2NameMap) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		long mixedNumber;
		
		int elementNumber;
		int cellLineNumber;
		int keggPathwayNumber;
		
		int numberofPermutations;
		int originalNumberofOverlaps;

		try{
			fileWriter = FileOperations.createFileWriter( outputFolder + outputFileName + "_" + runName + ".txt");
			bufferedWriter = new BufferedWriter( fileWriter);

			// Header Line
			bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "MixedNumber" + "\t" + "ElementName" + "_" + 	"CellLineName" + "_" + "KEGGPathwayName" + "\t" + "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsThatHasOverlapsGreaterThanorEqualToNumberofOriginalOverlaps" + System.getProperty( "line.separator"));

			for( TLongIntIterator itr = elementNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap.iterator(); itr.hasNext();){

				itr.advance();

				mixedNumber = itr.key();
				numberofPermutations = itr.value();
				
				originalNumberofOverlaps = elementNumber2OriginalKMap.get(mixedNumber);
				
				elementNumber = IntervalTree.getElementNumber(mixedNumber, GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
				cellLineNumber = IntervalTree.getCellLineNumber(mixedNumber, GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
				keggPathwayNumber = IntervalTree.getKeggPathwayNumber(mixedNumber, GeneratedMixedNumberDescriptionOrderLength.LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER);
				
				bufferedWriter.write(mixedNumber + "\t" + elementNumber2NameMap.get(elementNumber) +  "_" + cellLineNumber2NameMap.get(cellLineNumber)  + "_" + keggPathwayNumber2NameMap.get(keggPathwayNumber) + "\t" +  originalNumberofOverlaps + "\t" + numberofPermutations + System.getProperty( "line.separator"));

			}// End of FOR

			// Close bufferedWriter
			bufferedWriter.close();

		}catch( IOException e){
			e.printStackTrace();
		}

	}
	//9 July 2015
	

	// 1 July 2015
	public static void writeToBeCollectedWithoutZScore( 
			String outputFolder, 
			String outputFileName,
			String runName,
			TIntIntMap elementNumber2OriginalKMap,
			TIntIntMap elementNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap,
			AnnotationType annotationType,
			TIntObjectMap<String> elementNumber2NameMap,
			TIntObjectMap<String> cellLineNumber2NameMap,
			TIntObjectMap<String> keggPathwayNumber2NameMap,
			TIntObjectMap<String> userDefinedGeneSetNumber2NameMap,
			TIntObjectMap<String> userDefinedLibraryElementNumber2ElementNameMap) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		int mixedNumber;
		int elementNumber;
		int cellLineNumber;
		int keggPathwayNumber;
	
		int originalNumberofOverlaps;
		int numberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlaps;
		
		
		
		try{
			fileWriter = FileOperations.createFileWriter( outputFolder + outputFileName + "_" + runName + ".txt");
			bufferedWriter = new BufferedWriter( fileWriter);

			/**********************************************************************************************************/
			/***************************************HeaderLine Starts *************************************************/
			/**********************************************************************************************************/
			switch(annotationType){
			
				case DO_DNASE_ANNOTATION:
					bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "DnaseCellLineNumber" + "\t" + "DnaseCellLineName" + "\t");
					break;
					
				case DO_TF_ANNOTATION:
					bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "MixedNumber" + "\t" + "TFName" + "_" + "CellLineName" + "\t");
					break;
					
				case DO_HISTONE_ANNOTATION:
					bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "MixedNumber" + "\t" + "HistoneName" + "_" + "CellLineName" + "\t");
					break;
					
				case DO_GENE_ANNOTATION:
					bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "MixedNumber" + "\t" + "GeneAlternateName" + "\t");
					break;
										
				case DO_USER_DEFINED_LIBRARY_ANNOTATION:	
					bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "ElementNumber" + "\t" + "ElementName" + "\t");
					break;
					
				case DO_USER_DEFINED_GENESET_ANNOTATION:
					bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "MixedNumber" + "\t" + "UserDefinedGeneSetName" + "\t");
					break;
				
				case DO_KEGGPATHWAY_ANNOTATION:
					bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "MixedNumber" + "\t" + "KEGGPathwayName" + "\t");
					break;
					
				case DO_TF_KEGGPATHWAY_ANNOTATION:
					bufferedWriter.write( Commons.GLANET_COMMENT_CHARACTER + "MixedNumber" + "\t" + "TFName" + "_"  + "KEGGPathwayName" + "\t");
					break;
					
				default:
					break;

			}//End of SWITCH
				
			//Common Header Line
			bufferedWriter.write("OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsThatHasOverlapsGreaterThanorEqualToNumberofOriginalOverlaps" + System.getProperty( "line.separator"));
			/**********************************************************************************************************/
			/***************************************HeaderLine Ends ***************************************************/
			/**********************************************************************************************************/

			
			/**********************************************************************************************************/
			/**************************************Information Line Starts*********************************************/
			/**********************************************************************************************************/
			for( TIntIntIterator itr = elementNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap.iterator(); itr.hasNext();){

				itr.advance();

				mixedNumber = itr.key();
				numberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlaps = itr.value();
				
				originalNumberofOverlaps = elementNumber2OriginalKMap.get(mixedNumber);
				
				switch(annotationType){
				
					case DO_DNASE_ANNOTATION:
						
						bufferedWriter.write( mixedNumber + "\t" + elementNumber2NameMap.get(mixedNumber) + "\t" + originalNumberofOverlaps + "\t" + numberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlaps + System.getProperty( "line.separator"));
						break;
						
					case DO_TF_ANNOTATION:
						elementNumber = mixedNumber / 100000;
						cellLineNumber = mixedNumber % 100000;
						
						bufferedWriter.write( mixedNumber + "\t" + elementNumber2NameMap.get(elementNumber) + "_" +  cellLineNumber2NameMap.get(cellLineNumber) + "\t" + originalNumberofOverlaps + "\t"  + numberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlaps + System.getProperty( "line.separator"));
						break;
						
					case DO_HISTONE_ANNOTATION:
						elementNumber = mixedNumber / 100000;
						cellLineNumber = mixedNumber % 100000;
						
						bufferedWriter.write(mixedNumber + "\t" + elementNumber2NameMap.get(elementNumber) + "_" +  cellLineNumber2NameMap.get(cellLineNumber) + "\t" + originalNumberofOverlaps + "\t"  + numberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlaps + System.getProperty( "line.separator"));
						break;
						
					case DO_GENE_ANNOTATION:
						bufferedWriter.write(mixedNumber + "\t" + elementNumber2NameMap.get(mixedNumber) + "\t" + originalNumberofOverlaps + "\t" + numberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlaps + System.getProperty( "line.separator"));
						break;

					case DO_USER_DEFINED_LIBRARY_ANNOTATION:							
						bufferedWriter.write(mixedNumber + "\t" + userDefinedLibraryElementNumber2ElementNameMap.get(mixedNumber) + "\t" + originalNumberofOverlaps + "\t"  + elementNumber2NumberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlapsMap.get(mixedNumber) + System.getProperty( "line.separator"));
						break;	
						
					case DO_USER_DEFINED_GENESET_ANNOTATION:
						
						bufferedWriter.write( mixedNumber + "\t" + userDefinedGeneSetNumber2NameMap.get(mixedNumber) + "\t" + originalNumberofOverlaps + "\t"  + numberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlaps + System.getProperty( "line.separator"));
						break;
	
					case DO_KEGGPATHWAY_ANNOTATION:
						
						bufferedWriter.write( mixedNumber + "\t" + keggPathwayNumber2NameMap.get(mixedNumber) + "\t" + originalNumberofOverlaps + "\t"  + numberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlaps + System.getProperty( "line.separator"));
						break;
	
					case DO_TF_KEGGPATHWAY_ANNOTATION:
						elementNumber = mixedNumber / 100000;
						keggPathwayNumber = mixedNumber % 100000;
						
						if (keggPathwayNumber2NameMap.get(keggPathwayNumber) == null){
							System.out.println("stop here");	
						}
						
						bufferedWriter.write( mixedNumber + "\t" + elementNumber2NameMap.get(elementNumber) + "_"  + keggPathwayNumber2NameMap.get(keggPathwayNumber) + "\t" + originalNumberofOverlaps + "\t"  + numberofPermutationsThasHasOverlapsGreaterThanorEqualToOriginalNumberofOverlaps + System.getProperty( "line.separator"));
						break;
						
					default:
						break;

				
				}// End of SWITCH
				
					
			}// End of FOR
			/**********************************************************************************************************/
			/**************************************Information Line Ends***********************************************/
			/**********************************************************************************************************/


			// Close bufferedWriter
			bufferedWriter.close();

		}catch( IOException e){
			e.printStackTrace();
		}

	}
	// 1 July 2015

	// 1 July 2015
	public static void fillElementNumber2OriginalKMap( TLongIntMap elementNumber2OriginalKMap, String outputFolder,
			String originalNumberofOverlapsFileName) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine = null;

		long elementNumber;
		int numberofOriginalOverlaps;

		int indexofFirstTab;
		int indexofSecondTab;

		try{

			fileReader = FileOperations.createFileReader( outputFolder + originalNumberofOverlapsFileName);
			bufferedReader = new BufferedReader( fileReader);

			// strLine
			// elementNumber elementName originalNumberofOverlaps

			// Skip header line
			strLine = bufferedReader.readLine();

			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexofFirstTab > -1)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;

				elementNumber = Long.parseLong( strLine.substring( 0, indexofFirstTab));
				numberofOriginalOverlaps = Integer.parseInt( strLine.substring( indexofSecondTab + 1));

				elementNumber2OriginalKMap.put( elementNumber, numberofOriginalOverlaps);

			}// End of WHILE

			bufferedReader.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 1 July 2015

	// 29 June 2015
	public static void fillElementNumber2OriginalKMap( TIntIntMap elementNumber2OriginalKMap, String outputFolder,
			String originalNumberofOverlapsFileName) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine = null;

		int elementNumber;
		int numberofOriginalOverlaps;

		int indexofFirstTab;
		int indexofSecondTab;

		try{

			fileReader = FileOperations.createFileReader( outputFolder + originalNumberofOverlapsFileName);
			bufferedReader = new BufferedReader( fileReader);

			// strLine
			// elementNumber elementName originalNumberofOverlaps

			// Skip header line
			strLine = bufferedReader.readLine();

			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexofFirstTab > -1)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;

				elementNumber = Integer.parseInt( strLine.substring( 0, indexofFirstTab));
				numberofOriginalOverlaps = Integer.parseInt( strLine.substring( indexofSecondTab + 1));

				elementNumber2OriginalKMap.put(elementNumber, numberofOriginalOverlaps);

			}// End of WHILE

			bufferedReader.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 29 June 2015

	// Modified 22 July 2015
	// 24 June 2015 starts
	// DO NOT KEEP NUMBER OF OVERLAPS COMING FROM EACH PERMUTATION starts
	public static void annotateAllPermutationsInThreadsForAllChromosomes(
			String outputFolder,
			String dataFolder,
			GivenInputDataType givenInputsSNPsorIntervals,
			int numberofProcessors,
			int runNumber,
			int numberofPermutationsinThisRun,
			int numberofPermutationsinEachRun,
			String runName,
			List<InputLine> allOriginalInputLines,
			GenerateRandomDataMode generateRandomDataMode,
			WriteGeneratedRandomDataMode writeGeneratedRandomDataMode,
			WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode,
			WritePermutationBasedAnnotationResultMode writePermutationBasedAnnotationResultMode,
			TIntIntMap dnaseCellLineNumber2OriginalKMap, 
			TIntIntMap tfNumberCellLineNumber2OriginalKMap,
			TIntIntMap histoneNumberCellLineNumber2OriginalKMap, 
			TIntIntMap geneID2OriginalKMap,
			TIntIntMap exonBasedUserDefinedGeneSet2OriginalKMap,
			TIntIntMap regulationBasedUserDefinedGeneSet2OriginalKMap,
			TIntIntMap allBasedUserDefinedGeneSet2OriginalKMap,
			TIntIntMap userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap,
			TIntIntMap exonBasedKeggPathway2OriginalKMap, 
			TIntIntMap regulationBasedKeggPathway2OriginalKMap,
			TIntIntMap allBasedKeggPathway2OriginalKMap, 
			TIntIntMap tfExonBasedKeggPathway2OriginalKMap,
			TIntIntMap tfRegulationBasedKeggPathway2OriginalKMap, 
			TIntIntMap tfAllBasedKeggPathway2OriginalKMap,
			TLongIntMap tfCellLineExonBasedKeggPathway2OriginalKMap,
			TLongIntMap tfCellLineRegulationBasedKeggPathway2OriginalKMap,
			TLongIntMap tfCellLineAllBasedKeggPathway2OriginalKMap, 
			AnnotationType dnaseAnnotationType,
			AnnotationType histoneAnnotationType, 
			AnnotationType tfAnnotationType, 
			AnnotationType geneAnnotationType,
			AnnotationType userDefinedGeneSetAnnotationType, 
			AnnotationType userDefinedLibraryAnnotationType,
			AnnotationType keggPathwayAnnotationType, 
			AnnotationType tfKeggPathwayAnnotationType,
			AnnotationType tfCellLineKeggPathwayAnnotationType,
			AnnotationType bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType, 
			String userDefinedGeneSetName,
			int overlapDefinition,
			TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap,
			TIntObjectMap<TIntList> geneId2ListofUserDefinedGeneSetNumberMap,
			TIntObjectMap<String> userDefinedLibraryElementTypeNumber2ElementTypeNameMap,
			TIntObjectMap<TIntObjectMap<String>> userDefinedLibraryElementTypeNumber2ElementNumber2ElementNameMap,
			TIntObjectMap<String> dnaseCellLineNumber2NameMap,
			TIntObjectMap<String> cellLineNumber2NameMap,
			TIntObjectMap<String> tfNumber2NameMap,
			TIntObjectMap<String> histoneNumber2NameMap,
			TIntObjectMap<String> geneID2GeneHugoSymbolMap,
			TIntObjectMap<String> keggPathwayNumber2NameMap,
			TIntObjectMap<String> userDefinedGeneSetNumber2NameMap
			) {

		// 26 June 2015
		// AllMapsWithNumbersForAllChromosomes stores all chromosomes results
		AllMapsWithNumbersForAllChromosomes allMapsWithNumbersForAllChromosomes = new AllMapsWithNumbersForAllChromosomes();

		long startTimeAllPermutationsAllChromosomes;
		long endTimeAllPermutationsAllChromosomes;

		long startTimeFillingList;
		long endTimeFillingList;
		
		long startTimeGenerateRandomDataForEachChromosome;
		long endTimeGenerateRandomDataForEachChromosome;

		long startTimeGenerateRandomData;
		long endTimeGenerateRandomData;

		long startTimeOnlyAnnotationPermutationsForAllChromosome;
		long endTimeOnlyAnnotationPermutationsForAllChromosome;

		/********************************************************************************************************/
		/******************************* ORIGINAL INPUT LINES ***************************************************/
		Map<ChromosomeName, List<InputLineMinimal>> chromosomeName2OriginalInputLinesMap = new HashMap<ChromosomeName, List<InputLineMinimal>>();
		/******************************* ORIGINAL INPUT LINES ***************************************************/
		/********************************************************************************************************/

		TIntList permutationNumberList = null;

		// For DNase, TF, Histone, Gene, KEGGPathway, UserDefinedGeneSet, UserDefinedLibrary Enrichment
		IntervalTree intervalTree = null;

		// For joint TF and KEGGPathway Enrichment
		IntervalTree tfIntervalTree = null;
		IntervalTree ucscRefSeqGenesIntervalTree = null;

		/************************************************/
		/*********************GC*************************/
		/************************************************/
		// Will be used for SNP Data or Short Length Interval
		TByteList gcByteList = null;

		// Will be used for Interval Data or Medium Length Interval
		IntervalTree gcIntervalTree = null;

		// Will be used for Interval Data or Long Length Interval
		IntervalTree gcIsochoreIntervalTree = null;

		// Will be always used
		List<Interval> gcIsochoreFamilyL1Pool = null;
		List<Interval> gcIsochoreFamilyL2Pool = null;
		List<Interval> gcIsochoreFamilyH1Pool = null;
		List<Interval> gcIsochoreFamilyH2Pool = null;
		List<Interval> gcIsochoreFamilyH3Pool = null;
		/************************************************/
		/*********************GC*************************/
		/************************************************/

		/************************************************/
		/**************MAPABILITY************************/
		/************************************************/
		// Will be used for SNP and Interval Data
		TIntList mapabilityChromosomePositionList = null;
		TShortList mapabilityShortValueList = null;
		/************************************************/
		/**************MAPABILITY************************/
		/************************************************/

		List<Integer> hg19ChromosomeSizes = new ArrayList<Integer>();

		/******************************************************************************************************/
		/************** * PARTITION ORIGINAL INPUT LINES INTO CHROMOSOME BASED INPUT LINES STARTS *************/
		// Partition the original input data lines in a chromosome based manner
		partitionDataChromosomeBased( allOriginalInputLines, chromosomeName2OriginalInputLinesMap);
		/*************** PARTITION ORIGINAL INPUT LINES INTO CHROMOSOME BASED INPUT LINES ENDS*****************/
		/******************************************************************************************************/

		/********************************************************************************************************/
		/******************************* GET HG19 CHROMOSOME SIZES STARTS ***************************************/
		hg19.GRCh37Hg19Chromosome.initializeChromosomeSizes( hg19ChromosomeSizes);
		// get the hg19 chromosome sizes
		hg19.GRCh37Hg19Chromosome.getHg19ChromosomeSizes( hg19ChromosomeSizes, dataFolder,Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		/******************************* GET HG19 CHROMOSOME SIZES ENDS *****************************************/
		/********************************************************************************************************/

		// Do we need it? Yes
		ChromosomeName chromName;
		int chromSize;
		List<InputLineMinimal> chromosomeBaseOriginalInputLines;
		TIntObjectMap<List<InputLineMinimal>> permutationNumber2RandomlyGeneratedDataMap;
		// Do we need it? Yes

		AnnotateWithNumbersForAllChromosomes annotateWithNumbersForAllChromosomes;

		GenerateRandomData generateRandomData;

		ForkJoinPool pool = new ForkJoinPool(numberofProcessors);

		startTimeAllPermutationsAllChromosomes = System.currentTimeMillis();

		GlanetRunner.appendLog( "Run Number: " + runNumber);
		if( GlanetRunner.shouldLog())logger.info( "Run Number: " + runNumber);

		/********************************************************************************************************/
		/****************************** GENERATE PERMUTATION NUMBER LIST STARTS *********************************/
		/********************************************************************************************************/
		permutationNumberList = new TIntArrayList();

		GlanetRunner.appendLog( "PermutationNumberList is filled.");
		if( GlanetRunner.shouldLog())
			logger.info( "PermutationNumberList is filled.");
		fillPermutationNumberList(
				permutationNumberList, 
				runNumber, 
				numberofPermutationsinThisRun,
				numberofPermutationsinEachRun);
		/********************************************************************************************************/
		/****************************** GENERATE PERMUTATION NUMBER LIST ENDS ***********************************/
		/********************************************************************************************************/

		/******************************************************************************************************/
		/**********************************24 JUNE 2015 starts*************************************************/
		/******************************************************************************************************/
		// DO I HAVE TO KEEP IT? YES. READ THE EXPLANTION BELOW.
		// I CAN FIRST GENERATE RANDOM DATA AND THEN ANNOTATE RANDOMLY GENERATED DATA
		// RANDOM DATA FOR EACH PERMUTATION

		// Do not forget Generate random data takes time!!!

		// There are two options
		// First option: Only once Generate Random Data separately, keep it in memory
		// Generate and keep dnaseIntervalTrees in memory, annotate randomly generated data, free dnaseIntervalTrees
		// Generate and keep tfIntervalTrees in memory, annotate randomly generated data, free tfIntervalTrees
		// Generate and keep histoneIntervalTrees in memory, annotate randomly generated data, free histoneIntervalTrees
		// ... and so on...

		// Second option: Each time Generate Random Data separately, keep it in memory
		// Generate and keep dnaseIntervalTrees in memory, annotate randomly generated data, free dnaseIntervalTrees,
		// free randomly generated data
		// Generate and keep tfIntervalTrees in memory, annotate randomly generated data, free tfIntervalTrees, free
		// randomly generated data
		// Generate and keep histoneIntervalTrees in memory, annotate randomly generated data, free
		// histoneIntervalTrees, free randomly generated data
		// ... and so on...
		// No need for this

		// Decision: First option is selected

		TIntObjectMap<TIntObjectMap<List<InputLineMinimal>>> chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap = new TIntObjectHashMap<TIntObjectMap<List<InputLineMinimal>>>();
		/******************************************************************************************************/
		/**********************************24 JUNE 2015 ends***************************************************/
		/******************************************************************************************************/

		
		/******************************************************************************************************/
		/**********************************GENERATE RANDOM DATA STARTS*****************************************/
		/******************************************************************************************************/

		/******************************************************************************************************/
		/*********************FILL ALL NECCESSARY DATA STRUCTURES STARTS***************************************/
		/******************************************************************************************************/

		startTimeGenerateRandomData = System.currentTimeMillis();

		
		/******************************************************************************************************/
		/********************************* FOR EACH HG19 CHROMOSOME STARTS ************************************/
		/******************************************************************************************************/
		for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

			chromName = GRCh37Hg19Chromosome.getChromosomeName( chrNumber);
			chromSize = hg19ChromosomeSizes.get( chrNumber - 1);

			GlanetRunner.appendLog( "chromosome name:" + chromName.convertEnumtoString() + " chromosome size: " + chromSize);
			if( GlanetRunner.shouldLog())logger.info( "chromosome name:" + chromName.convertEnumtoString() + " chromosome size: " + chromSize);

			chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get( chromName);

			if( chromosomeBaseOriginalInputLines != null){

				/*******************************************************************************************************************************/
				/************************ FILL GCByteTroveList and MapabilityIntTroveList and  MapabilityShortTroveList STARTS *****************/
				/*******************************************************************************************************************************/
				// Fill GC and Mapability Data Structures
				if( generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){

					GlanetRunner.appendLog("For " + chromName.convertEnumtoString() +  " Filling of gcByteList, gcIntervalTree, gcIsochoreIntervalTree, gcIsochorePools, mapabilityChromosomePositionList, mapabilityShortValueList has started.");
					if( GlanetRunner.shouldLog())logger.info("For " + chromName.convertEnumtoString() +  " Filling of gcByteList, gcIntervalTree, gcIsochoreIntervalTree, gcIsochorePools, mapabilityChromosomePositionList, mapabilityShortValueList  has started.");

					startTimeFillingList = System.currentTimeMillis();

					/************************************************/
					/*********************GC*************************/
					/************************************************/
					// GCByteList
					if( containsIntervalLessThanOrEqualTo( chromosomeBaseOriginalInputLines,
							Commons.VERY_SHORT_INTERVAL_LENGTH)){

						// Fill GCByteList if givenData contains interval of length <= 10
						gcByteList = new TByteArrayList();

						ChromosomeBasedGCTroveList.fillTroveList( dataFolder, chromName, gcByteList);
					}

					// GC IntervalTree
					if( containsIntervalBetween( chromosomeBaseOriginalInputLines, Commons.VERY_SHORT_INTERVAL_LENGTH,Commons.SHORT_INTERVAL_LENGTH)){

						// For Interval Data
						// Fill GCIntervalTree if givenData contains interval of length >10 and <= 100
						gcIntervalTree = new IntervalTree();

						ChromosomeBasedGCIntervalTree.fillIntervalTree( dataFolder, chromName, gcIntervalTree);
					}

					// Always fill GC Isochore IntervalTree for classifying the Isochore Family of the interval
					// GC Isochore IntervalTree

					// Fill GCIsochoreIntervalTree if givenData contains interval of length >100
					gcIsochoreIntervalTree = new IntervalTree();

					ChromosomeBasedGCIntervalTree.fillIsochoreIntervalTree( dataFolder, chromName,gcIsochoreIntervalTree);

					// Always fill GCIsochorePools
					gcIsochoreFamilyL1Pool = new ArrayList<Interval>();
					gcIsochoreFamilyL2Pool = new ArrayList<Interval>();
					gcIsochoreFamilyH1Pool = new ArrayList<Interval>();
					gcIsochoreFamilyH2Pool = new ArrayList<Interval>();
					gcIsochoreFamilyH3Pool = new ArrayList<Interval>();

					// Always fill Isochore Family Pools for random Isochore Interval selection depending on the
					// Isochore Family of the original interval.
					// GC Isochore Family L1 Pool
					ChromosomeBasedGCIntervalTree.fillIsochoreFamilyPool( dataFolder, chromName, IsochoreFamily.L1,gcIsochoreFamilyL1Pool);

					// GC Isochore Family L2 Pool
					ChromosomeBasedGCIntervalTree.fillIsochoreFamilyPool( dataFolder, chromName, IsochoreFamily.L2,gcIsochoreFamilyL2Pool);

					// GC Isochore Family H1 Pool
					ChromosomeBasedGCIntervalTree.fillIsochoreFamilyPool( dataFolder, chromName, IsochoreFamily.H1,gcIsochoreFamilyH1Pool);

					// GC Isochore Family H2 Pool
					ChromosomeBasedGCIntervalTree.fillIsochoreFamilyPool( dataFolder, chromName, IsochoreFamily.H2,gcIsochoreFamilyH2Pool);

					// GC Isochore Family H3 Pool
					ChromosomeBasedGCIntervalTree.fillIsochoreFamilyPool( dataFolder, chromName, IsochoreFamily.H3,gcIsochoreFamilyH3Pool);
					/************************************************/
					/*********************GC*************************/
					/************************************************/

					/************************************************/
					/**************MAPABILITY************************/
					/************************************************/
					mapabilityChromosomePositionList = new TIntArrayList();
					mapabilityShortValueList = new TShortArrayList();

					ChromosomeBasedMappabilityTroveList.fillTroveList(dataFolder, chromName,mapabilityChromosomePositionList, mapabilityShortValueList);
					/************************************************/
					/**************MAPABILITY************************/
					/************************************************/

					endTimeFillingList = System.currentTimeMillis();

					GlanetRunner.appendLog("For " + chromName.convertEnumtoString() +  " Filling of gcByteList, gcIntervalTree, gcIsochoreIntervalTree, gcIsochorePools, mapabilityChromosomePositionList, mapabilityShortValueList  has taken " + ( float)( ( endTimeFillingList - startTimeFillingList) / 1000) + " seconds.");
					if( GlanetRunner.shouldLog())logger.info("For " + chromName.convertEnumtoString() +  " Filling of gcByteList, gcIntervalTree, gcIsochoreIntervalTree, gcIsochorePools, mapabilityChromosomePositionList, mapabilityShortValueList  has taken " + ( float)( ( endTimeFillingList - startTimeFillingList) / 1000) + " seconds.");

				}// Generate Random Data WITH GC and Mapability
				/*******************************************************************************************************************************/
				/************************ FILL GCByteTroveList and MapabilityIntTroveList and  MapabilityShortTroveList ENDS *******************/
				/*******************************************************************************************************************************/

				/********************************************************************************************************/
				/*************************** GENERATE RANDOM DATA FOR EACH CHROMOSOME STARTS ****************************/
				/********************************************************************************************************/
				startTimeGenerateRandomDataForEachChromosome = System.currentTimeMillis() ;
				
				GlanetRunner.appendLog("For " + chromName.convertEnumtoString() +  " Generate Random Data for permutations has started.");
				if( GlanetRunner.shouldLog())
					logger.info("For " + chromName.convertEnumtoString() +  " Generate Random Data for permutations has started.");
				// First generate Random Data
				generateRandomData = new GenerateRandomData( outputFolder, chromSize, chromName,
						chromosomeBaseOriginalInputLines, generateRandomDataMode, writeGeneratedRandomDataMode,
						Commons.ZERO, permutationNumberList.size(), permutationNumberList, givenInputsSNPsorIntervals,
						gcByteList, gcIntervalTree, gcIsochoreIntervalTree, gcIsochoreFamilyL1Pool,
						gcIsochoreFamilyL2Pool, gcIsochoreFamilyH1Pool, gcIsochoreFamilyH2Pool, gcIsochoreFamilyH3Pool,
						mapabilityChromosomePositionList, mapabilityShortValueList);

				permutationNumber2RandomlyGeneratedDataMap = pool.invoke( generateRandomData);
				
				endTimeGenerateRandomDataForEachChromosome = System.currentTimeMillis();
				
				GlanetRunner.appendLog("For " + chromName.convertEnumtoString() +  " Generate Random Data for permutations took " + ( float)( ( endTimeGenerateRandomDataForEachChromosome - startTimeGenerateRandomDataForEachChromosome) / 1000) + " seconds.");
				if( GlanetRunner.shouldLog())logger.info("For " + chromName.convertEnumtoString() +  " Generate Random Data for permutations took " + ( float)( ( endTimeGenerateRandomDataForEachChromosome - startTimeGenerateRandomDataForEachChromosome) / 1000) + " seconds.");
				/********************************************************************************************************/
				/*************************** GENERATE RANDOM DATA FOR EACH CHROMOSOME ENDS ******************************/
				/********************************************************************************************************/

				// 24 June 2015
				chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap.put(chrNumber,permutationNumber2RandomlyGeneratedDataMap);

				// Do not clear permutationNumber2RandomlyGeneratedDataHashMap, DO NOT LOSE just created Random Data.
				// permutationNumber2RandomlyGeneratedDataHashMap.clear();
				
				//Free memory
				gcByteList = null;
				gcIntervalTree = null;
				gcIsochoreIntervalTree = null;

				gcIsochoreFamilyL1Pool = null;
				gcIsochoreFamilyL2Pool = null;
				gcIsochoreFamilyH1Pool = null;
				gcIsochoreFamilyH2Pool = null;
				gcIsochoreFamilyH3Pool = null;
			
				mapabilityChromosomePositionList = null;
				mapabilityShortValueList = null;
				
				System.gc();
				System.runFinalization();

			}// End of IF: Chromosome Based Input Lines are not NULL

		}// End of FOR each CHROMOSOME
		/******************************************************************************************************/
		/******************************** FOR EACH HG19 CHROMOSOME ********************************************/
		/******************************************************************************************************/

		endTimeGenerateRandomData = System.currentTimeMillis();

		GlanetRunner.appendLog( "Generate Random Data for permutations has taken for all chromosomes " + ( float)( ( endTimeGenerateRandomData - startTimeGenerateRandomData) / 1000) + " seconds.");
		if( GlanetRunner.shouldLog())logger.info( "Generate Random Data for permutations has taken for all chromosomes " + ( float)( ( endTimeGenerateRandomData - startTimeGenerateRandomData) / 1000) + " seconds.");
		/******************************************************************************************************/
		/*********************FILL ALL NECCESSARY DATA STRUCTURES ENDS*****************************************/
		/******************************************************************************************************/

		/******************************************************************************************************/
		/**********************************GENERATE RANDOM DATA ENDS*******************************************/
		/******************************************************************************************************/

		
		/******************************************************************************************************/
		/*************************ALL DATA STRUCTURES ARE READY************************************************/
		/*************************************ANNOTATION STARTS************************************************/
		/******************************************************************************************************/

		/********************************************************************************************************/
		/***************************** DNASE ANNOTATION OF PERMUTATIONS STARTS **********************************/
		/********************************************************************************************************/
		if( dnaseAnnotationType.doDnaseAnnotation()){
			
			startTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "DNase Annotation of Permutations has started.");
			if( GlanetRunner.shouldLog())logger.info( "DNase Annotation of Permutations has started.");

			// TLinkable<IntervalTree> dnaseIntervalTreeList = null;
			TIntObjectMap<IntervalTree> dnaseIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();

			// Fill chrNumber2IntervalTreeMap
			// For each chromosome, generate intervalTree and fill intervalTreeMap
			for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

				chromName = GRCh37Hg19Chromosome.getChromosomeName( chrNumber);
				chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get( chromName);

				if( chromosomeBaseOriginalInputLines != null){

					intervalTree = generateDnaseIntervalTreeWithNumbers( dataFolder, chromName);
					dnaseIntervalTreeMap.put( chrNumber, intervalTree);

				}// End of IF chromosomeBaseOriginalInputLines is NOT NULL

			}// End of FOR each CHROMOSOME

			// Why don't we fill it before?
			fillElementNumber2OriginalKMap(
					dnaseCellLineNumber2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_DNASE);

			annotateWithNumbersForAllChromosomes = new AnnotateWithNumbersForAllChromosomes(
					outputFolder,
					chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, 
					runNumber, 
					numberofPermutationsinThisRun,
					writePermutationBasedandParametricBasedAnnotationResultMode, 
					Commons.ZERO,
					permutationNumberList.size(), 
					permutationNumberList, 
					dnaseIntervalTreeMap, 
					null,
					null,
					null,
					AnnotationType.DO_DNASE_ANNOTATION, 
					null, 
					overlapDefinition, 
					dnaseCellLineNumber2OriginalKMap,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null);

			allMapsWithNumbersForAllChromosomes = pool.invoke( annotateWithNumbersForAllChromosomes);

			writeToBeCollectedWithoutZScore(
					outputFolder, 
					Commons.TO_BE_COLLECTED_DNASE_NUMBER_OF_OVERLAPS,
					runName, 
					dnaseCellLineNumber2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getDnaseCellLineNumber2NumberofPermutations(),
					dnaseAnnotationType,
					dnaseCellLineNumber2NameMap,
					null,
					null,
					null,
					null);

			// Free memory
			dnaseIntervalTreeMap = null;
			

			System.gc();
			System.runFinalization();

			GlanetRunner.appendLog( "DNase Annotation of Permutations has ended.");
			if( GlanetRunner.shouldLog())logger.info( "DNase Annotation of Permutations has ended.");
			
			endTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "RunNumber: " + runNumber + " DNase Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			GlanetRunner.appendLog( "******************************************************************************************");

			if( GlanetRunner.shouldLog())logger.info( "RunNumber: " + runNumber + " DNase Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************************");


		}// End of IF DO DNase Annotation
		/********************************************************************************************************/
		/***************************** DNASE ANNOTATION OF PERMUTATIONS ENDS ************************************/
		/********************************************************************************************************/

		/********************************************************************************************************/
		/***************************** TF ANNOTATION OF PERMUTATIONS STARTS *************************************/
		/********************************************************************************************************/
		if( tfAnnotationType.doTFAnnotation()){
			
			startTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "Transcription Factor Annotation of Permutations has started.");
			if( GlanetRunner.shouldLog())logger.info( "Transcription Factor Annotation of Permutations has started.");

			// TLinkable<IntervalTree> dnaseIntervalTreeList = null;
			TIntObjectMap<IntervalTree> tfIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();

			// Fill chrNumber2IntervalTreeMap
			// For each chromosome, generate intervalTree and fill intervalTreeMap
			for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

				chromName = GRCh37Hg19Chromosome.getChromosomeName( chrNumber);
				chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get( chromName);

				if( chromosomeBaseOriginalInputLines != null){

					intervalTree = generateTfbsIntervalTreeWithNumbers( dataFolder, chromName);
					tfIntervalTreeMap.put( chrNumber, intervalTree);

				}// End of IF chromosomeBaseOriginalInputLines is NOT NULL

			}// End of FOR each CHROMOSOME

			// Why don't we fill it before?
			fillElementNumber2OriginalKMap(
					tfNumberCellLineNumber2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF);

			annotateWithNumbersForAllChromosomes = new AnnotateWithNumbersForAllChromosomes( 
					outputFolder,
					chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, 
					runNumber, 
					numberofPermutationsinThisRun,
					writePermutationBasedandParametricBasedAnnotationResultMode, 
					Commons.ZERO,
					permutationNumberList.size(), 
					permutationNumberList, 
					tfIntervalTreeMap, 
					null,
					null,
					null,
					AnnotationType.DO_TF_ANNOTATION, 
					null, 
					overlapDefinition,
					tfNumberCellLineNumber2OriginalKMap,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null);

			allMapsWithNumbersForAllChromosomes = pool.invoke( annotateWithNumbersForAllChromosomes);

			writeToBeCollectedWithoutZScore(
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,
					runName, 
					tfNumberCellLineNumber2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations(),
					tfAnnotationType,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					null,
					null,
					null);

			// Free memory
			tfIntervalTreeMap = null;

			System.gc();
			System.runFinalization();

			GlanetRunner.appendLog( "Transcription Factor Annotation of Permutations has ended.");
			if( GlanetRunner.shouldLog())logger.info( "Transcription Factor Annotation of Permutations has ended.");
			
			endTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "RunNumber: " + runNumber + " TF Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			GlanetRunner.appendLog( "******************************************************************************************");

			if( GlanetRunner.shouldLog())logger.info( "RunNumber: " + runNumber + " TF Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************************");

		}// End of IF DO TF Annotation
		/********************************************************************************************************/
		/***************************** TF ANNOTATION OF PERMUTATIONS STARTS *************************************/
		/********************************************************************************************************/
		
		
		/********************************************************************************************************/
		/***************************** HISTONE ANNOTATION OF PERMUTATIONS STARTS ********************************/
		/********************************************************************************************************/
		if(histoneAnnotationType.doHistoneAnnotation()){
			
			startTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog("Histone Modifications Annotation of Permutations has started.");
			if( GlanetRunner.shouldLog())logger.info( "Histone Modifications Annotation of Permutations has started.");

			TIntObjectMap<IntervalTree> histoneIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();

			// Fill chrNumber2IntervalTreeMap
			// For each chromosome, generate intervalTree and fill intervalTreeMap
			for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

				chromName = GRCh37Hg19Chromosome.getChromosomeName( chrNumber);
				chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get( chromName);

				if( chromosomeBaseOriginalInputLines != null){

					intervalTree = generateHistoneIntervalTreeWithNumbers( dataFolder, chromName);
					histoneIntervalTreeMap.put( chrNumber, intervalTree);

				}// End of IF chromosomeBaseOriginalInputLines is NOT NULL

			}// End of FOR each CHROMOSOME

			// Why don't we fill it before?
			fillElementNumber2OriginalKMap( 
					histoneNumberCellLineNumber2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_HISTONE);

			annotateWithNumbersForAllChromosomes = new AnnotateWithNumbersForAllChromosomes( 
					outputFolder,
					chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, 
					runNumber, 
					numberofPermutationsinThisRun,
					writePermutationBasedandParametricBasedAnnotationResultMode, 
					Commons.ZERO,
					permutationNumberList.size(), 
					permutationNumberList, 
					histoneIntervalTreeMap, 
					null,
					null,
					null,
					AnnotationType.DO_HISTONE_ANNOTATION, 
					null, 
					overlapDefinition,
					histoneNumberCellLineNumber2OriginalKMap,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null);

			allMapsWithNumbersForAllChromosomes = pool.invoke(annotateWithNumbersForAllChromosomes);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_HISTONE_NUMBER_OF_OVERLAPS,
					runName,
					histoneNumberCellLineNumber2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getHistoneNumberCellLineNumber2NumberofPermutations(),
					histoneAnnotationType,
					histoneNumber2NameMap,
					cellLineNumber2NameMap,
					null,
					null,
					null);

			// Free memory
			histoneIntervalTreeMap = null;

			System.gc();
			System.runFinalization();

			GlanetRunner.appendLog("Histone Modifications Annotation of Permutations has ended.");
			if( GlanetRunner.shouldLog())logger.info("Histone Modifications Annotation of Permutations has ended.");

			endTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "RunNumber: " + runNumber + "Histone Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			GlanetRunner.appendLog( "******************************************************************************************");

			if( GlanetRunner.shouldLog())logger.info( "RunNumber: " + runNumber + "Histone Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************************");

		}// End of IF DO HISTONE Annotation
		/********************************************************************************************************/
		/***************************** HISTONE ANNOTATION OF PERMUTATIONS ENDS **********************************/
		/********************************************************************************************************/
		
		/********************************************************************************************************/
		/***************************** GENE ANNOTATION OF PERMUTATIONS STARTS ***********************************/
		/********************************************************************************************************/
		if (geneAnnotationType.doGeneAnnotation()){
			
			startTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog("GENE Annotation of Permutations has started.");
			if( GlanetRunner.shouldLog()) logger.info("GENE Annotation of Permutations has started.");
			
			TIntObjectMap<IntervalTree> ucscRefSeqGenesIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();

			// Fill chrNumber2IntervalTreeMap
			// For each chromosome, generate intervalTree and fill intervalTreeMap
			for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

				chromName = GRCh37Hg19Chromosome.getChromosomeName(chrNumber);
				chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get(chromName);

				if( chromosomeBaseOriginalInputLines != null){

					intervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);
					ucscRefSeqGenesIntervalTreeMap.put( chrNumber, intervalTree);

				}// End of IF chromosomeBaseOriginalInputLines is NOT NULL

			}// End of FOR each CHROMOSOME
			
			//geneId is entrezGeneID
			fillElementNumber2OriginalKMap( 
					geneID2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_HG19_REFSEQ_GENE_ALTERNATE_NAME);

			annotateWithNumbersForAllChromosomes = new AnnotateWithNumbersForAllChromosomes( 
					outputFolder,
					chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, 
					runNumber, 
					numberofPermutationsinThisRun,
					writePermutationBasedandParametricBasedAnnotationResultMode, 
					Commons.ZERO,
					permutationNumberList.size(), 
					permutationNumberList, 
					ucscRefSeqGenesIntervalTreeMap, 
					null,
					null,
					null,
					AnnotationType.DO_GENE_ANNOTATION, 
					null, 
					overlapDefinition,
					geneID2OriginalKMap,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null);

			allMapsWithNumbersForAllChromosomes = pool.invoke(annotateWithNumbersForAllChromosomes);
			
			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_GENE_NUMBER_OF_OVERLAPS,
					runName,
					geneID2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getGeneNumber2NumberofPermutations(),
					geneAnnotationType,
					geneID2GeneHugoSymbolMap,
					null,
					null,
					null,
					null);

			// Free memory
			ucscRefSeqGenesIntervalTreeMap = null;

			System.gc();
			System.runFinalization();

			GlanetRunner.appendLog("Gene Annotation of Permutations has ended.");
			if( GlanetRunner.shouldLog()) logger.info("Gene Annotation of Permutations has ended.");

			endTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "RunNumber: " + runNumber + "Gene Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			GlanetRunner.appendLog( "******************************************************************************************");

			if( GlanetRunner.shouldLog())logger.info( "RunNumber: " + runNumber + "Gene Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************************");

		}
		/********************************************************************************************************/
		/***************************** GENE ANNOTATION OF PERMUTATIONS ENDS *************************************/
		/********************************************************************************************************/
		

		/********************************************************************************************************/
		/*************************** KEGGPathway ANNOTATION OF PERMUTATIONS STARTS ******************************/
		/********************************************************************************************************/
		if(keggPathwayAnnotationType.doKEGGPathwayAnnotation()){

			startTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog("KEGG Pathway Annotation of Permutations has started.");
			if( GlanetRunner.shouldLog())logger.info("KEGG Pathway  Annotation of Permutations has started.");

			TIntObjectMap<IntervalTree> ucscRefSeqGenesIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();

			// Fill chrNumber2IntervalTreeMap
			// For each chromosome, generate intervalTree and fill intervalTreeMap
			for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

				chromName = GRCh37Hg19Chromosome.getChromosomeName( chrNumber);
				chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get( chromName);

				if( chromosomeBaseOriginalInputLines != null){

					intervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);
					ucscRefSeqGenesIntervalTreeMap.put( chrNumber, intervalTree);

				}// End of IF chromosomeBaseOriginalInputLines is NOT NULL

			}// End of FOR each CHROMOSOME

			// Why don't we fill it before?
			fillElementNumber2OriginalKMap( 
					exonBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_EXON_BASED_KEGGPATHWAY_FILE);
			
			fillElementNumber2OriginalKMap( 
					regulationBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_REGULATION_BASED_KEGGPATHWAY_FILE);
			
			fillElementNumber2OriginalKMap( 
					allBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_ALL_BASED_KEGGPATHWAY_FILE);


			annotateWithNumbersForAllChromosomes = new AnnotateWithNumbersForAllChromosomes( 
					outputFolder,
					chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, 
					runNumber, 
					numberofPermutationsinThisRun,
					writePermutationBasedandParametricBasedAnnotationResultMode, 
					Commons.ZERO,
					permutationNumberList.size(), 
					permutationNumberList, 
					ucscRefSeqGenesIntervalTreeMap, 
					null,
					null,
					null,
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION, 
					geneId2ListofKeggPathwayNumberMap, 
					overlapDefinition,
					null,
					exonBasedKeggPathway2OriginalKMap,
					regulationBasedKeggPathway2OriginalKMap,
					allBasedKeggPathway2OriginalKMap,
					null,
					null,
					null,
					null,
					null,
					null,
					null);

			allMapsWithNumbersForAllChromosomes = pool.invoke(annotateWithNumbersForAllChromosomes);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					exonBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations(),
					keggPathwayAnnotationType,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					regulationBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations(),
					keggPathwayAnnotationType,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					allBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations(),
					keggPathwayAnnotationType,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);

			// Free memory
			ucscRefSeqGenesIntervalTreeMap = null;

			System.gc();
			System.runFinalization();

			GlanetRunner.appendLog("KEGG Pathway Annotation of Permutations has ended.");
			if( GlanetRunner.shouldLog())logger.info("KEGG Pathway Annotation of Permutations has ended.");

			endTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "RunNumber: " + runNumber + "KEGG Pathway of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			GlanetRunner.appendLog( "******************************************************************************************");

			if( GlanetRunner.shouldLog())logger.info( "RunNumber: " + runNumber + "KEGG Pathway of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************************");

		}// End of IF DO KEGG Pathway Annotation
		/********************************************************************************************************/
		/*************************** KEGGPathway ANNOTATION OF PERMUTATIONS ENDS ********************************/
		/********************************************************************************************************/
		
		//22 July 2015
		/********************************************************************************************************/
		/*************************User Defined GeneSet ANNOTATION OF PERMUTATIONS STARTS*************************/
		/********************************************************************************************************/
		if(userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){

			startTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog("User Defined GeneSet Annotation of Permutations has started.");
			if( GlanetRunner.shouldLog())logger.info("User Defined GeneSet Annotation of Permutations has started.");

			TIntObjectMap<IntervalTree> ucscRefSeqGenesIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();

			// Fill chrNumber2IntervalTreeMap
			// For each chromosome, generate intervalTree and fill intervalTreeMap
			for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

				chromName = GRCh37Hg19Chromosome.getChromosomeName(chrNumber);
				chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get(chromName);

				if( chromosomeBaseOriginalInputLines != null){

					intervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);
					ucscRefSeqGenesIntervalTreeMap.put( chrNumber, intervalTree);

				}// End of IF chromosomeBaseOriginalInputLines is NOT NULL

			}// End of FOR each CHROMOSOME

			// Why don't we fill it before?
			fillElementNumber2OriginalKMap( 
					exonBasedUserDefinedGeneSet2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_USERDEFINEDGENESET_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.ANNOTATION_RESULTS_FOR_EXON_BASED_USERDEFINEDGENESET_FILE);
			
			fillElementNumber2OriginalKMap( 
					regulationBasedUserDefinedGeneSet2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_USERDEFINEDGENESET_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.ANNOTATION_RESULTS_FOR_REGULATION_BASED_USERDEFINEDGENESET_FILE);
			
			fillElementNumber2OriginalKMap( 
					allBasedUserDefinedGeneSet2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_USERDEFINEDGENESET_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.ANNOTATION_RESULTS_FOR_ALL_BASED_USERDEFINEDGENESET_FILE);


			annotateWithNumbersForAllChromosomes = new AnnotateWithNumbersForAllChromosomes( 
					outputFolder,
					chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, 
					runNumber, 
					numberofPermutationsinThisRun,
					writePermutationBasedandParametricBasedAnnotationResultMode, 
					Commons.ZERO,
					permutationNumberList.size(), 
					permutationNumberList, 
					ucscRefSeqGenesIntervalTreeMap, 
					null,
					null,
					null,
					AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION, 
					geneId2ListofUserDefinedGeneSetNumberMap, 
					overlapDefinition,
					null,
					exonBasedUserDefinedGeneSet2OriginalKMap,
					regulationBasedUserDefinedGeneSet2OriginalKMap,
					allBasedUserDefinedGeneSet2OriginalKMap,
					null,
					null,
					null,
					null,
					null,
					null,
					null);

			allMapsWithNumbersForAllChromosomes = pool.invoke(annotateWithNumbersForAllChromosomes);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					//Commons.TO_BE_COLLECTED_EXON_BASED_USERDEFINED_GENESET_NUMBER_OF_OVERLAPS,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty( "file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty( "file.separator") + userDefinedGeneSetName + System.getProperty( "file.separator") + Commons.EXON_BASED + System.getProperty( "file.separator") + Commons.RUNS_DIRECTORY + Commons.EXON_BASED_USER_DEFINED_GENESET,
					runName,
					exonBasedUserDefinedGeneSet2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getExonBasedUserDefinedGeneSetNumber2NumberofPermutations(),
					userDefinedGeneSetAnnotationType,
					null,
					null,
					null,
					userDefinedGeneSetNumber2NameMap,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					//Commons.TO_BE_COLLECTED_REGULATION_BASED_USERDEFINED_GENESET_NUMBER_OF_OVERLAPS,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty( "file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty( "file.separator") + userDefinedGeneSetName + System.getProperty( "file.separator") + Commons.REGULATION_BASED + System.getProperty( "file.separator") + Commons.RUNS_DIRECTORY + Commons.REGULATION_BASED_USER_DEFINED_GENESET,
					runName,
					regulationBasedUserDefinedGeneSet2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getRegulationBasedUserDefinedGeneSetNumber2NumberofPermutations(),
					userDefinedGeneSetAnnotationType,
					null,
					null,
					null,
					userDefinedGeneSetNumber2NameMap,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					//Commons.TO_BE_COLLECTED_ALL_BASED_USERDEFINED_GENESET_NUMBER_OF_OVERLAPS,
					Commons.ENRICHMENT_DIRECTORY + System.getProperty( "file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty( "file.separator") + userDefinedGeneSetName + System.getProperty( "file.separator") + Commons.ALL_BASED + System.getProperty( "file.separator") + Commons.RUNS_DIRECTORY + Commons.ALL_BASED_USER_DEFINED_GENESET,
					runName,
					allBasedUserDefinedGeneSet2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getAllBasedUserDefinedGeneSetNumber2NumberofPermutations(),
					userDefinedGeneSetAnnotationType,
					null,
					null,
					null,
					userDefinedGeneSetNumber2NameMap,
					null);

			// Free memory
			ucscRefSeqGenesIntervalTreeMap = null;

			System.gc();
			System.runFinalization();

			GlanetRunner.appendLog("User Defined GeneSet Annotation of Permutations has ended.");
			if( GlanetRunner.shouldLog())logger.info("User Defined GeneSet Annotation of Permutations has ended.");

			endTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "RunNumber: " + runNumber + "User Defined GeneSet of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			GlanetRunner.appendLog( "******************************************************************************************");

			if( GlanetRunner.shouldLog())logger.info( "RunNumber: " + runNumber + "User Defined GeneSet of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************************");

		}// End of IF DO User Defined GeneSet Annotation
		/********************************************************************************************************/
		/*************************User Defined GeneSet ANNOTATION OF PERMUTATIONS ENDS***************************/
		/********************************************************************************************************/

		
		//24 July 2015
		/********************************************************************************************************/
		/************************User Defined Library ANNOTATION OF PERMUTATIONS STARTS**************************/
		/********************************************************************************************************/
		if (userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){
			
			int elementTypeNumber;
			String elementTypeName;

			int elementTypeNumberElementNumber;
			int numberofOverlaps;
			
			int elementNumber;
			
		
			startTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog("User Defined Library Annotation of Permutations has started.");
			if( GlanetRunner.shouldLog())logger.info("User Defined Library Annotation of Permutations has started.");

			TIntObjectMap<TIntObjectMap<IntervalTree>> userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap = new TIntObjectHashMap<TIntObjectMap<IntervalTree>>();
			TIntObjectMap<IntervalTree> chrNumber2IntervalTreeMap = null;
			
			//17 Sep 2015
			//Will be used in WriteToBeCollectedWithoutZscores method
			TIntObjectMap<TIntIntMap> userDefinedLibraryElementTypeNumber2ElementNumber2OriginalKMap = new TIntObjectHashMap<TIntIntMap>();
			TIntIntMap userDefinedLibraryElementNumber2OriginalKMap = null;
			
			// For each elementTypeNumber
			for( TIntObjectIterator<String> it = userDefinedLibraryElementTypeNumber2ElementTypeNameMap.iterator(); it.hasNext();){

				it.advance();
				
				elementTypeNumber = it.key();
				elementTypeName = it.value();
				
				// Fill chrNumber2IntervalTreeMap
				// For each chromosome, generate intervalTree and fill intervalTreeMap
				for(int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){
	
					chromName = GRCh37Hg19Chromosome.getChromosomeName(chrNumber);
					chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get(chromName);
	
					if( chromosomeBaseOriginalInputLines != null){
						
						// generate User Defined Library Interval Tree
						intervalTree = generateUserDefinedLibraryIntervalTreeWithNumbers(
								dataFolder,
								elementTypeNumber, 
								elementTypeName, 
								chromName);
							
	
						chrNumber2IntervalTreeMap = userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap.get(elementTypeNumber);
						
						if (chrNumber2IntervalTreeMap == null){
							chrNumber2IntervalTreeMap = new TIntObjectHashMap<IntervalTree>();
							userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap.put(elementTypeNumber, chrNumber2IntervalTreeMap);	
						}//End of IF chrNumber2IntervalTreeMap IS NULL
						
						chrNumber2IntervalTreeMap.put(chrNumber, intervalTree);
						
					}// End of IF chromosomeBaseOriginalInputLines is NOT NULL
	
				}// End of FOR each CHROMOSOME
			
			}//End of FOR each elementType

			
			// For each elementTypeNumber
			for( TIntObjectIterator<String> it = userDefinedLibraryElementTypeNumber2ElementTypeNameMap.iterator(); it.hasNext();){
				
				it.advance();
				
				elementTypeNumber = it.key();
				elementTypeName = it.value();
				
				userDefinedLibraryElementNumber2OriginalKMap = new TIntIntHashMap();
				
				// Why don't we fill it before?
				fillElementNumber2OriginalKMap( 
						userDefinedLibraryElementNumber2OriginalKMap, 
						outputFolder,
						Commons.ANNOTATION_RESULTS_FOR_USERDEFINEDLIBRARY_DIRECTORY + elementTypeName + Commons.ANNOTATION_RESULTS_FOR_USERDEFINEDLIBRARY_FILE);
				
				//Will be used in writeToBeCollectedWithoutZScores method
				userDefinedLibraryElementTypeNumber2ElementNumber2OriginalKMap.put(elementTypeNumber, userDefinedLibraryElementNumber2OriginalKMap);
				
				//Will be used in AnnotateWithNumbersForAllChromosomes
				//I should use userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap.
				for(TIntIntIterator itr=userDefinedLibraryElementNumber2OriginalKMap.iterator(); itr.hasNext();){
					
					itr.advance();
					
					elementNumber = itr.key();
					numberofOverlaps = itr.value();
					
					elementTypeNumberElementNumber = IntervalTree.generateElementTypeNumberElementNumber(elementTypeNumber,elementNumber,GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
					
					userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap.put(elementTypeNumberElementNumber,numberofOverlaps);
					
				}//End of FOR each element
				
			}//End of FOR each elementType

				
			annotateWithNumbersForAllChromosomes = new AnnotateWithNumbersForAllChromosomes( 
					outputFolder,
					chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, 
					runNumber, 
					numberofPermutationsinThisRun,
					writePermutationBasedandParametricBasedAnnotationResultMode, 
					Commons.ZERO,
					permutationNumberList.size(), 
					permutationNumberList, 
					null,
					null,
					userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap,
					userDefinedLibraryElementTypeNumber2ElementTypeNameMap,
					AnnotationType.DO_USER_DEFINED_LIBRARY_ANNOTATION, 
					null, 
					overlapDefinition,
					null,
					null,
					null,
					null,
					userDefinedLibraryElementTypeNumberElementNumber2OriginalKMap,
					null,
					null,
					null,
					null,
					null,
					null);

			allMapsWithNumbersForAllChromosomes = pool.invoke(annotateWithNumbersForAllChromosomes);
			
			
			//28 July 2015
			//We will fill this data structure
			TIntObjectMap<TIntIntMap> elementTypeNumber2ElementNumber2NumberofPermutations = new TIntObjectHashMap<TIntIntMap>();
			
			//Initialize elementTypeNumber2ElementNumber2NumberofPermutations for each elementType
			for(TIntObjectIterator<String> itr = userDefinedLibraryElementTypeNumber2ElementTypeNameMap.iterator();itr.hasNext();){
				
				itr.advance();
				
				elementTypeNumber = itr.key();
				
				elementTypeNumber2ElementNumber2NumberofPermutations.put(elementTypeNumber, new TIntIntHashMap());
				
			}//End of FOR
			
			// Fill the first argument using the second argument
			// Fill elementTypeBased elementNumber2KMap and elementNumber2AllKMap
			UserDefinedLibraryUtility.fillElementTypeNumberBasedMaps( 
					elementTypeNumber2ElementNumber2NumberofPermutations,
					allMapsWithNumbersForAllChromosomes.getElementTypeNumberElementNumber2NumberofPermutations());
		
			
			// For each elementTypeNumber map write
			for( TIntObjectIterator<TIntIntMap> it = elementTypeNumber2ElementNumber2NumberofPermutations.iterator(); it.hasNext();){
				
				it.advance();

				elementTypeNumber = it.key();
				
				TIntIntMap elementNumber2NumberofPermutationsMap = it.value();

				elementTypeName = userDefinedLibraryElementTypeNumber2ElementTypeNameMap.get(elementTypeNumber);
				
				
				userDefinedLibraryElementNumber2OriginalKMap = userDefinedLibraryElementTypeNumber2ElementNumber2OriginalKMap.get(elementTypeNumber);

					writeToBeCollectedWithoutZScore( 
						outputFolder, 
						Commons.TO_BE_COLLECTED_USER_DEFINED_LIBRARY_NUMBER_OF_OVERLAPS + Commons.RUNS_DIRECTORY + elementTypeName,
						runName,
						userDefinedLibraryElementNumber2OriginalKMap,
						elementNumber2NumberofPermutationsMap,
						userDefinedLibraryAnnotationType,
						null,
						null,
						null,
						null,
						userDefinedLibraryElementTypeNumber2ElementNumber2ElementNameMap.get(elementTypeNumber));

			

			}// End of each elementTypeNumberMap

				

			// Free memory
			userDefinedLibraryElementTypeNumber2ChrNumber2IntervalTreeMap = null;

			System.gc();
			System.runFinalization();

			GlanetRunner.appendLog("User Defined Library Annotation of Permutations has ended.");
			if( GlanetRunner.shouldLog())logger.info("User Defined Library Annotation of Permutations has ended.");

			endTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "RunNumber: " + runNumber + "User Defined Library of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			GlanetRunner.appendLog( "******************************************************************************************");

			if( GlanetRunner.shouldLog())logger.info( "RunNumber: " + runNumber + "User Defined Library of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************************");

		}
		/********************************************************************************************************/
		/************************User Defined Library ANNOTATION OF PERMUTATIONS ENDS****************************/
		/********************************************************************************************************/
		
		
		//15 July 2015 
		/********************************************************************************************************/
		/******************************TF KEGGPathway ANNOTATION OF PERMUTATIONS STARTS**************************/
		/********************************************************************************************************/
		if (tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()){
			
			startTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog("TF KEGGPathway Annotation of Permutations has started.");
			if( GlanetRunner.shouldLog())logger.info("TF KEGGPathway Annotation of Permutations has started.");

			TIntObjectMap<IntervalTree> tfIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();
			TIntObjectMap<IntervalTree> ucscRefSeqGenesIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();
			
			// Fill chrNumber2IntervalTreeMap
			// For each chromosome, generate intervalTree and fill intervalTreeMap
			for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

				chromName = GRCh37Hg19Chromosome.getChromosomeName( chrNumber);
				chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get( chromName);

				if( chromosomeBaseOriginalInputLines != null){
					
					tfIntervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder, chromName);
					ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);

					tfIntervalTreeMap.put(chrNumber,tfIntervalTree);
					ucscRefSeqGenesIntervalTreeMap.put(chrNumber,ucscRefSeqGenesIntervalTree);

				}// End of IF chromosomeBaseOriginalInputLines is NOT NULL

			}// End of FOR each CHROMOSOME
			
			
			// TF
			fillElementNumber2OriginalKMap(
					tfNumberCellLineNumber2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF);
			
			
			// KEGGPathway
			fillElementNumber2OriginalKMap( 
					exonBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_EXON_BASED_KEGGPATHWAY_FILE);
			
			fillElementNumber2OriginalKMap( 
					regulationBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_REGULATION_BASED_KEGGPATHWAY_FILE);
			
			fillElementNumber2OriginalKMap( 
					allBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_ALL_BASED_KEGGPATHWAY_FILE);

			
			// TF KEGGPathway
			fillElementNumber2OriginalKMap( 
					tfExonBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_EXON_BASED_KEGG_PATHWAY);
			
			fillElementNumber2OriginalKMap( 
					tfRegulationBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY);
			
			fillElementNumber2OriginalKMap( 
					tfAllBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_ALL_BASED_KEGG_PATHWAY);
			

			annotateWithNumbersForAllChromosomes = new AnnotateWithNumbersForAllChromosomes( 
					outputFolder,
					chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, 
					runNumber, 
					numberofPermutationsinThisRun,
					writePermutationBasedandParametricBasedAnnotationResultMode, 
					Commons.ZERO,
					permutationNumberList.size(), 
					permutationNumberList, 
					tfIntervalTreeMap, 
					ucscRefSeqGenesIntervalTreeMap,
					null,
					null,
					AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION, 
					geneId2ListofKeggPathwayNumberMap, 
					overlapDefinition,
					tfNumberCellLineNumber2OriginalKMap,
					exonBasedKeggPathway2OriginalKMap,
					regulationBasedKeggPathway2OriginalKMap,
					allBasedKeggPathway2OriginalKMap,
					null,
					tfExonBasedKeggPathway2OriginalKMap,
					tfRegulationBasedKeggPathway2OriginalKMap,
					tfAllBasedKeggPathway2OriginalKMap,
					null,
					null,
					null);

			allMapsWithNumbersForAllChromosomes = pool.invoke(annotateWithNumbersForAllChromosomes);

			//TF
			writeToBeCollectedWithoutZScore(
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,
					runName, 
					tfNumberCellLineNumber2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations(),
					AnnotationType.DO_TF_ANNOTATION,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					null,
					null,
					null);

			
			//KEGGPathway
			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					exonBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					regulationBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					allBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);

			//TF KEGGPathway
			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfExonBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberExonBasedKeggPathwayNumber2NumberofPermutations(),
					tfKeggPathwayAnnotationType,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfRegulationBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations(),
					tfKeggPathwayAnnotationType,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfAllBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberAllBasedKeggPathwayNumber2NumberofPermutations(),
					tfKeggPathwayAnnotationType,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap,
					null,
					null);

			// Free memory
			tfIntervalTreeMap = null;
			ucscRefSeqGenesIntervalTreeMap = null;

			System.gc();
			System.runFinalization();

			GlanetRunner.appendLog("TF KEGGPathway Annotation of Permutations has ended.");
			if( GlanetRunner.shouldLog())logger.info("TF KEGGPathway Annotation of Permutations has ended.");

			endTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "RunNumber: " + runNumber + "TF KEGGPathway Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			GlanetRunner.appendLog( "******************************************************************************************");

			if( GlanetRunner.shouldLog())logger.info( "RunNumber: " + runNumber + "TF KEGGPathway Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************************");

		}
		/********************************************************************************************************/
		/******************************TF KEGGPathway ANNOTATION OF PERMUTATIONS ENDS****************************/
		/********************************************************************************************************/
		
		/********************************************************************************************************/
		/***********************TF CellLine KEGGPathway ANNOTATION OF PERMUTATIONS STARTS************************/
		/********************************************************************************************************/
		if(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){
			
			startTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog("TF CellLine KEGGPathway Annotation of Permutations has started.");
			if( GlanetRunner.shouldLog())logger.info("TF CellLine KEGGPathway Annotation of Permutations has started.");

			TIntObjectMap<IntervalTree> tfIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();
			TIntObjectMap<IntervalTree> ucscRefSeqGenesIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();
			
			// Fill chrNumber2IntervalTreeMap
			// For each chromosome, generate intervalTree and fill intervalTreeMap
			for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

				chromName = GRCh37Hg19Chromosome.getChromosomeName( chrNumber);
				chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get( chromName);

				if( chromosomeBaseOriginalInputLines != null){
					
					tfIntervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder, chromName);
					ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);

					tfIntervalTreeMap.put(chrNumber,tfIntervalTree);
					ucscRefSeqGenesIntervalTreeMap.put(chrNumber,ucscRefSeqGenesIntervalTree);

				}// End of IF chromosomeBaseOriginalInputLines is NOT NULL

			}// End of FOR each CHROMOSOME
			
			
			// TF
			fillElementNumber2OriginalKMap(
					tfNumberCellLineNumber2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF);
			
			
			// KEGGPathway
			fillElementNumber2OriginalKMap( 
					exonBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_EXON_BASED_KEGGPATHWAY_FILE);
			
			fillElementNumber2OriginalKMap( 
					regulationBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_REGULATION_BASED_KEGGPATHWAY_FILE);
			
			fillElementNumber2OriginalKMap( 
					allBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_ALL_BASED_KEGGPATHWAY_FILE);

			
			// TF CellLine  KEGGPathway
			fillElementNumber2OriginalKMap( 
					tfCellLineExonBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
			
			fillElementNumber2OriginalKMap( 
					tfCellLineRegulationBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
			
			fillElementNumber2OriginalKMap( 
					tfCellLineAllBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
			

			annotateWithNumbersForAllChromosomes = new AnnotateWithNumbersForAllChromosomes( 
					outputFolder,
					chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, 
					runNumber, 
					numberofPermutationsinThisRun,
					writePermutationBasedandParametricBasedAnnotationResultMode, 
					Commons.ZERO,
					permutationNumberList.size(), 
					permutationNumberList, 
					tfIntervalTreeMap, 
					ucscRefSeqGenesIntervalTreeMap,
					null,
					null,
					AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION, 
					geneId2ListofKeggPathwayNumberMap, 
					overlapDefinition,
					tfNumberCellLineNumber2OriginalKMap,
					exonBasedKeggPathway2OriginalKMap,
					regulationBasedKeggPathway2OriginalKMap,
					allBasedKeggPathway2OriginalKMap,
					null,
					null,
					null,
					null,
					tfCellLineExonBasedKeggPathway2OriginalKMap,
					tfCellLineRegulationBasedKeggPathway2OriginalKMap,
					tfCellLineAllBasedKeggPathway2OriginalKMap);

			allMapsWithNumbersForAllChromosomes = pool.invoke(annotateWithNumbersForAllChromosomes);

			//TF
			writeToBeCollectedWithoutZScore(
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,
					runName, 
					tfNumberCellLineNumber2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations(),
					AnnotationType.DO_TF_ANNOTATION,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					null,
					null,
					null);

			
			//KEGGPathway
			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					exonBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					regulationBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					allBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);

			//TF Cellline KEGGPathway
			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfCellLineExonBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations(),
					tfCellLineKeggPathwayAnnotationType,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfCellLineRegulationBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations(),
					tfCellLineKeggPathwayAnnotationType,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfCellLineAllBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations(),
					tfCellLineKeggPathwayAnnotationType,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap);

			// Free memory
			tfIntervalTreeMap = null;
			ucscRefSeqGenesIntervalTreeMap = null;

			System.gc();
			System.runFinalization();

			GlanetRunner.appendLog("TF CellLine KEGGPathway Annotation of Permutations has ended.");
			if( GlanetRunner.shouldLog())logger.info("TF CellLine KEGGPathway Annotation of Permutations has ended.");

			endTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "RunNumber: " + runNumber + "TF CellLine KEGGPathway Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			GlanetRunner.appendLog( "******************************************************************************************");

			if( GlanetRunner.shouldLog())logger.info( "RunNumber: " + runNumber + "TF CellLine KEGGPathway Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************************");

		}// End of IF DO TF CellLine KEGGPathway Annotation
		/********************************************************************************************************/
		/***********************TF CellLine KEGGPathway ANNOTATION OF PERMUTATIONS ENDS**************************/
		/********************************************************************************************************/
	
		//20 July 2015
		/********************************************************************************************************/
		/********************************************BOTH STARTS*************************************************/
		/****************************TFKEGGPathway ANNOTATION OF PERMUTATIONS STARTS*****************************/
		/***********************TFCellLineKEGGPathway ANNOTATION OF PERMUTATIONS STARTS**************************/
		/********************************************************************************************************/
		if(bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){
			
			startTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog("BOTH TFKEGGPathway and TFCellLineKEGGPathway Annotation of Permutations has started.");
			if( GlanetRunner.shouldLog())logger.info("BOTH TFKEGGPathway and TFCellLineKEGGPathway Annotation of Permutations has started.");

			TIntObjectMap<IntervalTree> tfIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();
			TIntObjectMap<IntervalTree> ucscRefSeqGenesIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();
			
			// Fill chrNumber2IntervalTreeMap
			// For each chromosome, generate intervalTree and fill intervalTreeMap
			for( int chrNumber = 1; chrNumber <= Commons.NUMBER_OF_CHROMOSOMES_HG19; chrNumber++){

				chromName = GRCh37Hg19Chromosome.getChromosomeName( chrNumber);
				chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get( chromName);

				if( chromosomeBaseOriginalInputLines != null){
					
					tfIntervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder, chromName);
					ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);

					tfIntervalTreeMap.put(chrNumber,tfIntervalTree);
					ucscRefSeqGenesIntervalTreeMap.put(chrNumber,ucscRefSeqGenesIntervalTree);

				}// End of IF chromosomeBaseOriginalInputLines is NOT NULL

			}// End of FOR each CHROMOSOME
			
			
			// TF
			fillElementNumber2OriginalKMap(
					tfNumberCellLineNumber2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF);
			
			
			// KEGGPathway
			fillElementNumber2OriginalKMap( 
					exonBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_EXON_BASED_KEGGPATHWAY_FILE);
			
			fillElementNumber2OriginalKMap( 
					regulationBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_REGULATION_BASED_KEGGPATHWAY_FILE);
			
			fillElementNumber2OriginalKMap( 
					allBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_ALL_BASED_KEGGPATHWAY_FILE);

			
			// TF KEGGPathway
			fillElementNumber2OriginalKMap( 
					tfExonBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_EXON_BASED_KEGG_PATHWAY);
			
			fillElementNumber2OriginalKMap( 
					tfRegulationBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY);
			
			fillElementNumber2OriginalKMap( 
					tfAllBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_ALL_BASED_KEGG_PATHWAY);
			
			
			// TF CellLine  KEGGPathway
			fillElementNumber2OriginalKMap( 
					tfCellLineExonBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
			
			fillElementNumber2OriginalKMap( 
					tfCellLineRegulationBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
			
			fillElementNumber2OriginalKMap( 
					tfCellLineAllBasedKeggPathway2OriginalKMap, 
					outputFolder,
					Commons.ANNOTATION_RESULTS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
			

			annotateWithNumbersForAllChromosomes = new AnnotateWithNumbersForAllChromosomes( 
					outputFolder,
					chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap, 
					runNumber, 
					numberofPermutationsinThisRun,
					writePermutationBasedandParametricBasedAnnotationResultMode, 
					Commons.ZERO,
					permutationNumberList.size(), 
					permutationNumberList, 
					tfIntervalTreeMap, 
					ucscRefSeqGenesIntervalTreeMap,
					null,
					null,
					AnnotationType.DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION, 
					geneId2ListofKeggPathwayNumberMap, 
					overlapDefinition,
					tfNumberCellLineNumber2OriginalKMap,
					exonBasedKeggPathway2OriginalKMap,
					regulationBasedKeggPathway2OriginalKMap,
					allBasedKeggPathway2OriginalKMap,
					null,
					tfExonBasedKeggPathway2OriginalKMap,
					tfRegulationBasedKeggPathway2OriginalKMap,
					tfAllBasedKeggPathway2OriginalKMap,
					tfCellLineExonBasedKeggPathway2OriginalKMap,
					tfCellLineRegulationBasedKeggPathway2OriginalKMap,
					tfCellLineAllBasedKeggPathway2OriginalKMap);

			allMapsWithNumbersForAllChromosomes = pool.invoke(annotateWithNumbersForAllChromosomes);

			//TF
			writeToBeCollectedWithoutZScore(
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,
					runName, 
					tfNumberCellLineNumber2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumber2NumberofPermutations(),
					AnnotationType.DO_TF_ANNOTATION,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					null,
					null,
					null);

			
			//KEGGPathway
			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					exonBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getExonBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					regulationBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getRegulationBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					allBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getAllBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_KEGGPATHWAY_ANNOTATION,
					null,
					null,
					keggPathwayNumber2NameMap,
					null,
					null);
			

			//TF KEGGPathway
			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfExonBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberExonBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfRegulationBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberRegulationBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap,
					null,
					null);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfAllBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberAllBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap,
					null,
					null);
			

			//TF Cellline KEGGPathway
			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfCellLineExonBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberExonBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfCellLineRegulationBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap);

			writeToBeCollectedWithoutZScore( 
					outputFolder, 
					Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
					runName,
					tfCellLineAllBasedKeggPathway2OriginalKMap,
					allMapsWithNumbersForAllChromosomes.getTfNumberCellLineNumberAllBasedKeggPathwayNumber2NumberofPermutations(),
					AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
					tfNumber2NameMap,
					cellLineNumber2NameMap,
					keggPathwayNumber2NameMap);

			// Free memory
			tfIntervalTreeMap = null;
			ucscRefSeqGenesIntervalTreeMap = null;

			System.gc();
			System.runFinalization();

			GlanetRunner.appendLog("BOTH TF KEGGPathway and TF CellLine KEGGPathway Annotation of Permutations has ended.");
			if( GlanetRunner.shouldLog())logger.info("BOTH TF KEGGPathway and TF CellLine KEGGPathway Annotation of Permutations has ended.");

			endTimeOnlyAnnotationPermutationsForAllChromosome = System.currentTimeMillis();

			GlanetRunner.appendLog( "RunNumber: " + runNumber + "BOTH TF KEGGPathway and TF CellLine KEGGPathway Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			GlanetRunner.appendLog( "******************************************************************************************");

			if( GlanetRunner.shouldLog())logger.info( "RunNumber: " + runNumber + "BOTH TF KEGGPathway and TF CellLine KEGGPathway Annotation of " + numberofPermutationsinThisRun + " permutations for all chromosomes" + " numberof intervals took  " + ( float)( ( endTimeOnlyAnnotationPermutationsForAllChromosome - startTimeOnlyAnnotationPermutationsForAllChromosome) / 1000) + " seconds.");
			if( GlanetRunner.shouldLog())logger.info( "******************************************************************************************");

		}// End of IF DO TF CellLine KEGGPathway Annotation
		
		/********************************************************************************************************/
		/********************************************BOTH ENDS***************************************************/
		/****************************TFKEGGPathway ANNOTATION OF PERMUTATIONS ENDS*******************************/
		/***********************TFCellLineKEGGPathway ANNOTATION OF PERMUTATIONS ENDS****************************/
		/********************************************************************************************************/
		
		
		/******************************************************************************************************/
		/*************************ALL DATA STRUCTURES ARE READY************************************************/
		/*************************************ANNOTATION ENDS**************************************************/
		/******************************************************************************************************/

		/******************************************************************************************************/
		/***************************************** FREE MEMORY STARTS *****************************************/
		chrNumber2PermutationNumber2RandomlyGeneratedDataHashMap = null;

		annotateWithNumbersForAllChromosomes = null;

		generateRandomData = null;
		chromosomeBaseOriginalInputLines = null;

		System.gc();
		System.runFinalization();
		/***************************************** FREE MEMORY ENDS *******************************************/
		/******************************************************************************************************/

		permutationNumberList = null;

		pool.shutdown();

		if( pool.isTerminated()){
			GlanetRunner.appendLog( "ForkJoinPool is terminated ");
			if( GlanetRunner.shouldLog())logger.info( "ForkJoinPool is terminated ");
		}

		endTimeAllPermutationsAllChromosomes = System.currentTimeMillis();

		GlanetRunner.appendLog( "RUN_NUMBER: " + runNumber + " NUMBER_OF_PERMUTATIONS:  " + numberofPermutationsinThisRun + " took " + ( float)( ( endTimeAllPermutationsAllChromosomes - startTimeAllPermutationsAllChromosomes) / 1000) + " seconds.");
		if( GlanetRunner.shouldLog())logger.info( "RUN_NUMBER: " + runNumber + " NUMBER_OF_PERMUTATIONS:  " + numberofPermutationsinThisRun + " took " + ( float)( ( endTimeAllPermutationsAllChromosomes - startTimeAllPermutationsAllChromosomes) / 1000) + " seconds.");

		/*************************************************************************************************************************/
		/********************** Make null starts**********************************************************************************/
		/*************************************************************************************************************************/
		allMapsWithNumbersForAllChromosomes = null;

		System.gc();
		System.runFinalization();
		/*************************************************************************************************************************/
		/********************** Make null ends************************************************************************************/
		/*************************************************************************************************************************/

	}

	// 24 June 2015 ends
	// DO NOT KEEP NUMBER OF OVERLAPS COMING FROM EACH PERMUTATION ends

	// NEW FUNCIONALITY ADDED
	// First Generate random data concurrently
	// Then annotate permutations concurrently
	// the tasks are executed
	// after all the parallel work is done
	// results are written to files
	public static void annotateAllPermutationsInThreads(
			String outputFolder,
			String dataFolder,
			GivenInputDataType givenInputsSNPsorIntervals,
			int numberofProcessors,
			int runNumber,
			int numberofPermutationsinThisRun,
			int numberofPermutationsinEachRun,
			List<InputLine> allOriginalInputLines,
			TIntObjectMap<TIntList> dnase2AllKMap,
			TIntObjectMap<TIntList> tfbs2AllKMap,
			TIntObjectMap<TIntList> histone2AllKMap,
			TIntObjectMap<TIntList> gene2AllKMap,
			TIntObjectMap<TIntList> exonBasedUserDefinedGeneSet2AllKMap,
			TIntObjectMap<TIntList> regulationBasedUserDefinedGeneSet2AllKMap,
			TIntObjectMap<TIntList> allBasedUserDefinedGeneSet2AllKMap,
			TIntObjectMap<TIntList> elementTypeNumberElementNumber2AllKMap,
			TIntObjectMap<TIntList> exonBasedKeggPathway2AllKMap,
			TIntObjectMap<TIntList> regulationBasedKeggPathway2AllKMap,
			TIntObjectMap<TIntList> allBasedKeggPathway2AllKMap,
			TIntObjectMap<TIntList> tfExonBasedKeggPathway2AllKMap,
			TIntObjectMap<TIntList> tfRegulationBasedKeggPathway2AllKMap,
			TIntObjectMap<TIntList> tfAllBasedKeggPathway2AllKMap,
			TLongObjectMap<TIntList> tfCellLineExonBasedKeggPathway2AllKMap,
			TLongObjectMap<TIntList> tfCellLineRegulationBasedKeggPathway2AllKMap,
			TLongObjectMap<TIntList> tfCellLineAllBasedKeggPathway2AllKMap,
			GenerateRandomDataMode generateRandomDataMode,
			WriteGeneratedRandomDataMode writeGeneratedRandomDataMode,
			WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode,
			WritePermutationBasedAnnotationResultMode writePermutationBasedAnnotationResultMode,
			TIntIntMap originalDnase2KMap, 
			TIntIntMap originalTfbs2KMap, 
			TIntIntMap originalHistone2KMap,
			TIntIntMap originalGene2KMap, 
			TIntIntMap originalExonBasedUserDefinedGeneSet2KMap,
			TIntIntMap originalRegulationBasedUserDefinedGeneSet2KMap,
			TIntIntMap originalAllBasedUserDefinedGeneSet2KMap, 
			TIntIntMap originalElementTypeNumberElementNumber2KMap,
			TIntIntMap originalExonBasedKeggPathway2KMap, 
			TIntIntMap originalRegulationBasedKeggPathway2KMap,
			TIntIntMap originalAllBasedKeggPathway2KMap, 
			TIntIntMap originalTfExonBasedKeggPathway2KMap,
			TIntIntMap originalTfRegulationBasedKeggPathway2KMap, 
			TIntIntMap originalTfAllBasedKeggPathway2KMap,
			TLongIntMap originalTfCellLineExonBasedKeggPathway2KMap,
			TLongIntMap originalTfCellLineRegulationBasedKeggPathway2KMap,
			TLongIntMap originalTfCellLineAllBasedKeggPathway2KMap, 
			AnnotationType dnaseAnnotationType,
			AnnotationType histoneAnnotationType, 
			AnnotationType tfAnnotationType, 
			AnnotationType geneAnnotationType,
			AnnotationType userDefinedGeneSetAnnotationType, 
			AnnotationType userDefinedLibraryAnnotationType,
			AnnotationType keggPathwayAnnotationType, 
			AnnotationType tfKeggPathwayAnnotationType,
			AnnotationType tfCellLineKeggPathwayAnnotationType, 
			AnnotationType bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType,
			int overlapDefinition,
			TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap,
			TIntObjectMap<TIntList> geneId2ListofUserDefinedGeneSetNumberMap,
			TIntObjectMap<String> elementTypeNumber2ElementTypeMap) {
		
		
		//ForkandJoin as long as the number of permutations is greater than number of processsors
		//Sequentially annotate random data as much as the number of processors.
		//This seems to be better.
		EnrichmentPermutationDivisionType enrichmentPermutationDivisionType= EnrichmentPermutationDivisionType.DIVIDE_PERMUTATIONS_AS_LONG_AS_NUMBER_OF_PERMUTATIONS_IS_GREATER_THAN_NUMBER_OF_PROCESSORS;
		//EnrichmentPermutationDivisionType enrichmentPermutationDivisionType= EnrichmentPermutationDivisionType.DIVIDE_PERMUTATIONS_AS_MUCH_AS_NUMBER_OF_PROCESSORS;

		String permutationBasedResultDirectory;

		// allMaps stores one chromosome based results
		AllMapsWithNumbers allMapsWithNumbers = new AllMapsWithNumbers();
		// AllMapsDnaseTFHistoneWithNumbers allMapsDnaseTFHistoneWithNumbers = new AllMapsDnaseTFHistoneWithNumbers();

		// accumulatedAllMaps stores all chromosome results
		// In other words, it contains accumulated results coming from each chromosome
		AllMapsWithNumbers accumulatedAllMapsWithNumbers = new AllMapsWithNumbers();
		AllMapsDnaseTFHistoneWithNumbers accumulatedAllMapsDnaseTFHistoneWithNumbers = new AllMapsDnaseTFHistoneWithNumbers();

		long startTimeAllPermutationsAllChromosomes;
		long endTimeAllPermutationsAllChromosomes;

		long startTimeFillingList;
		long endTimeFillingList;

		long startTimeGenerateRandomData;
		long endTimeGenerateRandomData;

		long startTimeOnlyAnnotationPermutationsForEachChromosome;
		long endTimeOnlyAnnotationPermutationsForEachChromosome;

		long startTimeEverythingIncludedAnnotationPermutationsForEachChromosome;
		long endTimeEverythingIncludedAnnotationPermutationsForEachChromosome;

		/********************************************************************************************************/
		/******************************* ORIGINAL INPUT LINES ***************************************************/
		Map<ChromosomeName, List<InputLineMinimal>> chromosomeName2OriginalInputLinesMap = new HashMap<ChromosomeName, List<InputLineMinimal>>();
		/******************************* ORIGINAL INPUT LINES ***************************************************/
		/********************************************************************************************************/

		// @todo test it
		// SecureRandom myrandom = new SecureRandom();

		TIntList permutationNumberList = null;
		IntervalTree intervalTree = null;

		// For NEW FUNCTIONALITY
		IntervalTree tfIntervalTree = null;
		IntervalTree ucscRefSeqGenesIntervalTree = null;

		/************************************************/
		/*********************GC*************************/
		/************************************************/
		// Will be used for SNP Data
		TByteList gcByteList = null;

		// Will be used for Interval Data
		IntervalTree gcIntervalTree = null;

		// Will be used for Interval Data
		IntervalTree gcIsochoreIntervalTree = null;

		List<Interval> gcIsochoreFamilyL1Pool = null;
		List<Interval> gcIsochoreFamilyL2Pool = null;
		List<Interval> gcIsochoreFamilyH1Pool = null;
		List<Interval> gcIsochoreFamilyH2Pool = null;
		List<Interval> gcIsochoreFamilyH3Pool = null;
		/************************************************/
		/*********************GC*************************/
		/************************************************/

		/************************************************/
		/**************MAPABILITY************************/
		/************************************************/
		// Will be used for SNP and Interval Data
		TIntList mapabilityChromosomePositionList = null;
		TShortList mapabilityShortValueList = null;

		// For Testing Purposes
		// Using mapabilityChromosomePositionList and mapabilityShortValueList run faster than mapabilityIntervalTree
		// IntervalTree mapabilityIntervalTree = null;

		// MapabilityShortValueList is preferred since it provides better mapability values
		// No difference in execution time and memory usage
		// Average number of trials is almost the same.
		// For Testing purposes
		// TByteList mapabilityByteValueList = null;
		/************************************************/
		/**************MAPABILITY************************/
		/************************************************/

		List<Integer> hg19ChromosomeSizes = new ArrayList<Integer>();

		/******************************************************************************************************/
		/************** * PARTITION ORIGINAL INPUT LINES INTO CHROMOSOME BASED INPUT LINES STARTS *************/
		// Partition the original input data lines in a chromosome based manner
		partitionDataChromosomeBased( allOriginalInputLines, chromosomeName2OriginalInputLinesMap);
		/*************** PARTITION ORIGINAL INPUT LINES INTO CHROMOSOME BASED INPUT LINES ENDS*****************/
		/******************************************************************************************************/

		/********************************************************************************************************/
		/******************************* GET HG19 CHROMOSOME SIZES STARTS ***************************************/
		hg19.GRCh37Hg19Chromosome.initializeChromosomeSizes( hg19ChromosomeSizes);
		// get the hg19 chromosome sizes
		hg19.GRCh37Hg19Chromosome.getHg19ChromosomeSizes( hg19ChromosomeSizes, dataFolder,Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		/******************************* GET HG19 CHROMOSOME SIZES ENDS *****************************************/
		/********************************************************************************************************/

		ChromosomeName chromName;
		int chromSize;
		List<InputLineMinimal> chromosomeBaseOriginalInputLines;
		TIntObjectMap<List<InputLineMinimal>> permutationNumber2RandomlyGeneratedDataHashMap = new TIntObjectHashMap<List<InputLineMinimal>>();

		AnnotateWithNumbers annotateWithNumbers;
		
		// For testing purposes
		// AnnotateDnaseTFHistoneWithNumbers annotateDnaseTFHistoneWithNumbers;

		GenerateRandomData generateRandomData;
		ForkJoinPool pool = new ForkJoinPool(numberofProcessors);

		startTimeAllPermutationsAllChromosomes = System.currentTimeMillis();

		GlanetRunner.appendLog( "Run Number: " + runNumber);
		if( GlanetRunner.shouldLog())logger.info( "Run Number: " + runNumber);

		/********************************************************************************************************/
		/****************************** GENERATE PERMUTATION NUMBER LIST STARTS *********************************/
		/********************************************************************************************************/
		permutationNumberList = new TIntArrayList();

		GlanetRunner.appendLog( "PermutationNumberList is filled.");
		if( GlanetRunner.shouldLog())logger.info( "PermutationNumberList is filled.");
		fillPermutationNumberList( permutationNumberList, runNumber, numberofPermutationsinThisRun,numberofPermutationsinEachRun);
		/********************************************************************************************************/
		/****************************** GENERATE PERMUTATION NUMBER LIST ENDS ***********************************/
		/********************************************************************************************************/

		/********************************************************************************************************/
		/********************************** ADD Permutation Number for GIVEN ORIGINAL DATA STARTS ***************/
		/********************************************************************************************************/
		// In each run, add permutation number for original data
		// After Random Data Generation has been ended
		// Add User Given Original Data(Genomic Variants) to the randomly generated data map
		addPermutationNumberforOriginalData( permutationNumberList, Commons.ORIGINAL_DATA_PERMUTATION_NUMBER);
		/********************************************************************************************************/
		/********************************** ADD Permutation Number for GIVEN ORIGINAL DATA ENDS *****************/
		/********************************************************************************************************/

		/******************************************************************************************************/
		/********************************* FOR EACH HG19 CHROMOSOME STARTS ************************************/
		/******************************************************************************************************/
		for( int i = 1; i <= Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){

			chromName = GRCh37Hg19Chromosome.getChromosomeName( i);
			chromSize = hg19ChromosomeSizes.get( i - 1);

			GlanetRunner.appendLog( "chromosome name:" + chromName.convertEnumtoString() + " chromosome size: " + chromSize);
			if( GlanetRunner.shouldLog())logger.info( "chromosome name:" + chromName.convertEnumtoString() + " chromosome size: " + chromSize);

			chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get( chromName);

			if( chromosomeBaseOriginalInputLines != null){

				/************************************************/
				/*********************GC*************************/
				/************************************************/
				// Fill GCByteList if givenData contains interval of length <= 10
				gcByteList = new TByteArrayList();

				// For Interval Data
				// Fill GCIntervalTree if givenData contains interval of length >10 and <= 100
				gcIntervalTree = new IntervalTree();

				// Fill GCIsochoreIntervalTree if givenData contains interval of length >100
				gcIsochoreIntervalTree = new IntervalTree();

				// Always fill GCIsochorePools
				gcIsochoreFamilyL1Pool = new ArrayList<Interval>();
				gcIsochoreFamilyL2Pool = new ArrayList<Interval>();
				gcIsochoreFamilyH1Pool = new ArrayList<Interval>();
				gcIsochoreFamilyH2Pool = new ArrayList<Interval>();
				gcIsochoreFamilyH3Pool = new ArrayList<Interval>();
				/************************************************/
				/*********************GC*************************/
				/************************************************/

				/************************************************/
				/**************MAPABILITY************************/
				/************************************************/
				mapabilityChromosomePositionList = new TIntArrayList();
				mapabilityShortValueList = new TShortArrayList();

				// For testing purposes
				// mapabilityByteValueList
				// mapabilityByteValueList = new TByteArrayList();

				// For testing purposes
				// Mapability Interval Tree
				// mapabilityIntervalTree = new IntervalTree();
				/************************************************/
				/**************MAPABILITY************************/
				/************************************************/

				startTimeEverythingIncludedAnnotationPermutationsForEachChromosome = System.currentTimeMillis();

				/*******************************************************************************************************************************/
				/************************ FILL GCByteTroveList and MapabilityIntTroveList and  MapabilityShortTroveList STARTS *****************/
				/*******************************************************************************************************************************/
				// Fill gcCharArray and mapabilityFloatArray
				if( generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){

					GlanetRunner.appendLog( "Filling of gcByteList, gcIntervalTree, gcIsochoreIntervalTree, gcIsochorePools, mapabilityChromosomePositionList, mapabilityShortValueList has started.");
					if( GlanetRunner.shouldLog())logger.info( "Filling of gcByteList, gcIntervalTree, gcIsochoreIntervalTree, gcIsochorePools, mapabilityChromosomePositionList, mapabilityShortValueList  has started.");

					startTimeFillingList = System.currentTimeMillis();

					/************************************************/
					/*********************GC*************************/
					/************************************************/
					// GC Old way
					// gcCharArray = ChromosomeBasedGCArray.getChromosomeGCArray(dataFolder, chromName, chromSize);

					// GCByteList
					if( containsIntervalLessThanOrEqualTo( chromosomeBaseOriginalInputLines,
							Commons.VERY_SHORT_INTERVAL_LENGTH)){
						ChromosomeBasedGCTroveList.fillTroveList( dataFolder, chromName, gcByteList);
					}

					// GC IntervalTree
					if( containsIntervalBetween( chromosomeBaseOriginalInputLines, Commons.VERY_SHORT_INTERVAL_LENGTH,
							Commons.SHORT_INTERVAL_LENGTH)){
						ChromosomeBasedGCIntervalTree.fillIntervalTree( dataFolder, chromName, gcIntervalTree);
					}

					// Always fill GC Isochore IntervalTree for classifying the Isochore Family of the interval
					// GC Isochore IntervalTree
					ChromosomeBasedGCIntervalTree.fillIsochoreIntervalTree( dataFolder, chromName,
							gcIsochoreIntervalTree);

					// Always fill Isochore Family Pools for random Isochore Interval selection depending on the
					// Isochore Family of the original interval.
					// GC Isochore Family L1 Pool
					ChromosomeBasedGCIntervalTree.fillIsochoreFamilyPool( dataFolder, chromName, IsochoreFamily.L1,
							gcIsochoreFamilyL1Pool);

					// GC Isochore Family L2 Pool
					ChromosomeBasedGCIntervalTree.fillIsochoreFamilyPool( dataFolder, chromName, IsochoreFamily.L2,
							gcIsochoreFamilyL2Pool);

					// GC Isochore Family H1 Pool
					ChromosomeBasedGCIntervalTree.fillIsochoreFamilyPool( dataFolder, chromName, IsochoreFamily.H1,
							gcIsochoreFamilyH1Pool);

					// GC Isochore Family H2 Pool
					ChromosomeBasedGCIntervalTree.fillIsochoreFamilyPool( dataFolder, chromName, IsochoreFamily.H2,
							gcIsochoreFamilyH2Pool);

					// GC Isochore Family H3 Pool
					ChromosomeBasedGCIntervalTree.fillIsochoreFamilyPool( dataFolder, chromName, IsochoreFamily.H3,
							gcIsochoreFamilyH3Pool);
					/************************************************/
					/*********************GC*************************/
					/************************************************/

					/************************************************/
					/**************MAPABILITY************************/
					/************************************************/
					// Mapability Old Way
					// mapabilityFloatArray = ChromosomeBasedMapabilityArray.getChromosomeMapabilityArray(dataFolder,
					// chromName, chromSize);

					// GLANET Always Fills
					// Mapability
					// mapabilityChromosomePositionList
					// mapabilityShortValueList
					ChromosomeBasedMappabilityTroveList.fillTroveList( dataFolder, chromName,
							mapabilityChromosomePositionList, mapabilityShortValueList);

					// For testing purposes
					// Mapability Interval Tree
					// MapabilityIntervalTreeConstruction.fillIntervalTree(dataFolder, chromName,
					// mapabilityIntervalTree);
					/************************************************/
					/**************MAPABILITY************************/
					/************************************************/

					endTimeFillingList = System.currentTimeMillis();

					GlanetRunner.appendLog( "Filling of gcByteList, gcIntervalTree, gcIsochoreIntervalTree, gcIsochorePools, mapabilityChromosomePositionList, mapabilityShortValueList  has taken " + ( float)( ( endTimeFillingList - startTimeFillingList) / 1000) + " seconds.");
					if( GlanetRunner.shouldLog())logger.info( "Filling of gcByteList, gcIntervalTree, gcIsochoreIntervalTree, gcIsochorePools, mapabilityChromosomePositionList, mapabilityShortValueList  has taken " + ( float)( ( endTimeFillingList - startTimeFillingList) / 1000) + " seconds.");

				}
				/*******************************************************************************************************************************/
				/************************ FILL GCByteTroveList and MapabilityIntTroveList and  MapabilityShortTroveList ENDS *******************/
				/*******************************************************************************************************************************/

				/********************************************************************************************************/
				/********************************** GENERATE RANDOM DATA STARTS *****************************************/
				/********************************************************************************************************/
				GlanetRunner.appendLog( "Generate Random Data for permutations has started.");
				if( GlanetRunner.shouldLog())logger.info( "Generate Random Data for permutations has started.");
				// First generate Random Data

				startTimeGenerateRandomData = System.currentTimeMillis();
				
				generateRandomData = new GenerateRandomData(
						outputFolder, 
						chromSize, 
						chromName,
						chromosomeBaseOriginalInputLines, 
						generateRandomDataMode, 
						writeGeneratedRandomDataMode,
						Commons.ZERO, 
						permutationNumberList.size(), 
						permutationNumberList, 
						givenInputsSNPsorIntervals,
						gcByteList, 
						gcIntervalTree, 
						gcIsochoreIntervalTree, 
						gcIsochoreFamilyL1Pool,
						gcIsochoreFamilyL2Pool, 
						gcIsochoreFamilyH1Pool, 
						gcIsochoreFamilyH2Pool, 
						gcIsochoreFamilyH3Pool,
						mapabilityChromosomePositionList, 
						mapabilityShortValueList);
				
				permutationNumber2RandomlyGeneratedDataHashMap = pool.invoke(generateRandomData);

				// //For testing average number of trials mapabilityByteList versus mapabilityShortList starts
				// float totalNumberofTrialsPerPermutation = 0;
				// float averageNumberofTrialsPerPermutation = 0;
				// float averageNumberofTrialsForAllPermutation = 0;
				//
				// for(TIntObjectIterator<List<InputLineMinimal>> it=
				// permutationNumber2RandomlyGeneratedDataHashMap.iterator(); it.hasNext();){
				//
				// it.advance();
				//
				// List<InputLineMinimal> temp = it.value();
				//
				// totalNumberofTrialsPerPermutation = 0;
				//
				// for(InputLineMinimal line : temp) {
				// totalNumberofTrialsPerPermutation += line.getNumberofTrials();
				//
				// }
				//
				// averageNumberofTrialsPerPermutation = totalNumberofTrialsPerPermutation/temp.size();
				//
				// averageNumberofTrialsForAllPermutation +=averageNumberofTrialsPerPermutation;
				// }
				//
				// averageNumberofTrialsForAllPermutation =
				// averageNumberofTrialsForAllPermutation/permutationNumber2RandomlyGeneratedDataHashMap.size();
				//
				// if( GlanetRunner.shouldLog())logger.info("averageNumberofTrialsForAllPermutation" + "\t" +
				// averageNumberofTrialsForAllPermutation);
				// //For testing average number of trials mapabilityByteList versus mapabilityShortList ends

				endTimeGenerateRandomData = System.currentTimeMillis();

				GlanetRunner.appendLog( "Generate Random Data for permutations has taken " + ( float)( ( endTimeGenerateRandomData - startTimeGenerateRandomData) / 1000) + " seconds.");
				if( GlanetRunner.shouldLog())logger.info( "Generate Random Data for permutations has taken " + ( float)( ( endTimeGenerateRandomData - startTimeGenerateRandomData) / 1000) + " seconds.");
				/********************************************************************************************************/
				/********************************** GENERATE RANDOM DATA ENDS *****************************************/
				/******************************************************************************************************/

				/********************************************************************************************************/
				/**********************************Add the original data starts************************** ***************/
				/********************************************************************************************************/
				// Add the original data to permutationNumber2RandomlyGeneratedDataHashMap
				permutationNumber2RandomlyGeneratedDataHashMap.put(Commons.ORIGINAL_DATA_PERMUTATION_NUMBER,chromosomeBaseOriginalInputLines);
				/********************************************************************************************************/
				/**********************************Add the original data ends**************************** ***************/
				/********************************************************************************************************/

				
				
				/******************************************************************************************************/
				/***************************************** FREE MEMORY STARTS *****************************************/
				/******************************************************************************************************/
				gcByteList = null;
				gcIntervalTree = null;
				gcIsochoreIntervalTree = null;

				gcIsochoreFamilyL1Pool = null;
				gcIsochoreFamilyL2Pool = null;
				gcIsochoreFamilyH1Pool = null;
				gcIsochoreFamilyH2Pool = null;
				gcIsochoreFamilyH3Pool = null;

				mapabilityChromosomePositionList = null;
				mapabilityShortValueList = null;

				// mapabilityByteValueList = null;
				// mapabilityIntervalTree = null;

				System.gc();
				System.runFinalization();
				/******************************************************************************************************/
				/***************************************** FREE MEMORY ENDS *******************************************/
				/******************************************************************************************************/

				
				
				/********************************************************************************************************/
				/***************************** ANNOTATE PERMUTATIONS STARTS *********************************************/
				/********************************************************************************************************/
				GlanetRunner.appendLog( "Annotation of Permutations has started.");
				if( GlanetRunner.shouldLog())logger.info( "Annotation of Permutations has started.");

				startTimeOnlyAnnotationPermutationsForEachChromosome = System.currentTimeMillis();

				// /********************************************************************************************************/
				// /***********************Using AnnotateDnaseTFHistoneWithNumbers STARTS
				// **********************************/
				// /********************************************************************************************************/
				// if (dnaseAnnotationType.doDnaseAnnotation()) {
				// // dnase
				// // generate dnase interval tree
				// intervalTree = generateDnaseIntervalTreeWithNumbers(dataFolder, chromName);
				//
				// annotateDnaseTFHistoneWithNumbers = new AnnotateDnaseTFHistoneWithNumbers(
				// chromName,
				// permutationNumber2RandomlyGeneratedDataHashMap,
				// runNumber,
				// numberofPermutationsinThisRun,
				// writePermutationBasedandParametricBasedAnnotationResultMode,
				// permutationNumberList,
				// intervalTree,
				// AnnotationType.DO_DNASE_ANNOTATION,
				// Commons.ZERO,
				// permutationNumberList.size(),
				// outputFolder,
				// overlapDefinition);
				//
				// allMapsDnaseTFHistoneWithNumbers = pool.invoke(annotateDnaseTFHistoneWithNumbers);
				// accumulate(allMapsDnaseTFHistoneWithNumbers, accumulatedAllMapsDnaseTFHistoneWithNumbers,
				// AnnotationType.DO_DNASE_ANNOTATION);
				// allMapsDnaseTFHistoneWithNumbers = null;
				//
				// //Has no effect on the execution time
				// //deleteIntervalTree(intervalTree);
				//
				// intervalTree = null;
				//
				// System.gc();
				// System.runFinalization();
				// }
				//
				// if (histoneAnnotationType.doHistoneAnnotation()) {
				// // histone
				// // generate histone interval tree
				// intervalTree = generateHistoneIntervalTreeWithNumbers(dataFolder, chromName);
				//
				//
				// annotateDnaseTFHistoneWithNumbers = new AnnotateDnaseTFHistoneWithNumbers(
				// chromName,
				// permutationNumber2RandomlyGeneratedDataHashMap,
				// runNumber,
				// numberofPermutationsinThisRun,
				// writePermutationBasedandParametricBasedAnnotationResultMode,
				// permutationNumberList,
				// intervalTree,
				// AnnotationType.DO_HISTONE_ANNOTATION,
				// Commons.ZERO,
				// permutationNumberList.size(),
				// outputFolder,
				// overlapDefinition);
				//
				//
				//
				// allMapsDnaseTFHistoneWithNumbers = pool.invoke(annotateDnaseTFHistoneWithNumbers);
				//
				// accumulate(allMapsDnaseTFHistoneWithNumbers, accumulatedAllMapsDnaseTFHistoneWithNumbers,
				// AnnotationType.DO_HISTONE_ANNOTATION);
				// allMapsDnaseTFHistoneWithNumbers = null;
				//
				// intervalTree = null;
				//
				// System.gc();
				// System.runFinalization();
				// }
				//
				// if ((tfAnnotationType.doTFAnnotation()) &&
				// !(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) &&
				// !(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())) {
				// // tf
				// // generate tf interval tree
				// intervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder, chromName);
				//
				//
				// annotateDnaseTFHistoneWithNumbers = new AnnotateDnaseTFHistoneWithNumbers(
				// chromName,
				// permutationNumber2RandomlyGeneratedDataHashMap,
				// runNumber,
				// numberofPermutationsinThisRun,
				// writePermutationBasedandParametricBasedAnnotationResultMode,
				// permutationNumberList,
				// intervalTree,
				// AnnotationType.DO_TF_ANNOTATION,
				// Commons.ZERO,
				// permutationNumberList.size(),
				// outputFolder,
				// overlapDefinition);
				//
				//
				//
				// allMapsDnaseTFHistoneWithNumbers = pool.invoke(annotateDnaseTFHistoneWithNumbers);
				// accumulate(allMapsDnaseTFHistoneWithNumbers, accumulatedAllMapsDnaseTFHistoneWithNumbers,
				// AnnotationType.DO_TF_ANNOTATION);
				// allMapsDnaseTFHistoneWithNumbers = null;
				//
				// intervalTree = null;
				//
				// System.gc();
				// System.runFinalization();
				// }
				// /********************************************************************************************************/
				// /***********************Using AnnotateDnaseTFHistoneWithNumbers
				// ENDS*************************************/
				// /********************************************************************************************************/

				/********************************************************************************************************/
				/***********************Using AnnotateWithNumbers starts*************************************************/
				/********************************************************************************************************/
				if( dnaseAnnotationType.doDnaseAnnotation()){

					// dnase
					// generate dnase interval tree
					intervalTree = generateDnaseIntervalTreeWithNumbers( dataFolder, chromName);

					annotateWithNumbers = new AnnotateWithNumbers(
							outputFolder, 
							chromName,
							permutationNumber2RandomlyGeneratedDataHashMap, 
							runNumber, 
							numberofPermutationsinThisRun,
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode, 
							Commons.ZERO,
							permutationNumberList.size(), 
							permutationNumberList, 
							intervalTree, 
							null,
							AnnotationType.DO_DNASE_ANNOTATION, 
							null, 
							overlapDefinition,
							enrichmentPermutationDivisionType);

					allMapsWithNumbers = pool.invoke(annotateWithNumbers);
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_DNASE_ANNOTATION);

					allMapsWithNumbers = null;
					intervalTree = null;

					System.gc();
					System.runFinalization();
				}

				// Using AnnotateWithNumbers
				if( histoneAnnotationType.doHistoneAnnotation()){
					// histone
					// generate histone interval tree
					intervalTree = generateHistoneIntervalTreeWithNumbers( dataFolder, chromName);
					
					annotateWithNumbers = new AnnotateWithNumbers(
							outputFolder, 
							chromName,
							permutationNumber2RandomlyGeneratedDataHashMap, 
							runNumber, 
							numberofPermutationsinThisRun,
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode, 
							Commons.ZERO,
							permutationNumberList.size(), 
							permutationNumberList, 
							intervalTree, 
							null,
							AnnotationType.DO_HISTONE_ANNOTATION, 
							null, 
							overlapDefinition,
							enrichmentPermutationDivisionType);
					
					allMapsWithNumbers = pool.invoke( annotateWithNumbers);
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_HISTONE_ANNOTATION);
					allMapsWithNumbers = null;
					intervalTree = null;

					System.gc();
					System.runFinalization();
				}

				// Using AnnotateWithNumbers
				if( ( tfAnnotationType.doTFAnnotation())){

					// tf
					// generate tf interval tree
					
					intervalTree = generateTfbsIntervalTreeWithNumbers( dataFolder, chromName);
					
					annotateWithNumbers = new AnnotateWithNumbers(
							outputFolder, 
							chromName,
							permutationNumber2RandomlyGeneratedDataHashMap, 
							runNumber, 
							numberofPermutationsinThisRun,
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode, 
							Commons.ZERO,
							permutationNumberList.size(), 
							permutationNumberList, 
							intervalTree, 
							null,
							AnnotationType.DO_TF_ANNOTATION, 
							null, 
							overlapDefinition,
							enrichmentPermutationDivisionType);
					
					allMapsWithNumbers = pool.invoke( annotateWithNumbers);
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_ANNOTATION);
					allMapsWithNumbers = null;
					intervalTree = null;

					System.gc();
					System.runFinalization();
				}
				/********************************************************************************************************/
				/***********************Using AnnotateWithNumbers ends***************************************************/
				/********************************************************************************************************/

				/********************************************************************************/
				/*****************Gene Annotate Permutations starts******************************/
				/********************************************************************************/
				if( geneAnnotationType.doGeneAnnotation()){

					// Gene Enrichment
					intervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers( dataFolder, chromName);

					annotateWithNumbers = new AnnotateWithNumbers(
							outputFolder, 
							chromName,
							permutationNumber2RandomlyGeneratedDataHashMap, 
							runNumber, 
							numberofPermutationsinThisRun,
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode, 
							Commons.ZERO,
							permutationNumberList.size(), 
							permutationNumberList, 
							intervalTree, 
							null,
							AnnotationType.DO_GENE_ANNOTATION, 
							null, 
							overlapDefinition,
							enrichmentPermutationDivisionType);

					allMapsWithNumbers = pool.invoke( annotateWithNumbers);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_GENE_ANNOTATION);
					allMapsWithNumbers = null;
					intervalTree = null;

					System.gc();
					System.runFinalization();

				}
				/********************************************************************************/
				/*****************Gene Annotate Permutations ends********************************/
				/********************************************************************************/

				// UserDefinedGeneSet
				if( userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){
					// ucsc RefSeq Genes
					// generate UCSC RefSeq Genes interval tree
					intervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers( dataFolder, chromName);
					
					annotateWithNumbers = new AnnotateWithNumbers(
							outputFolder, 
							chromName,
							permutationNumber2RandomlyGeneratedDataHashMap, 
							runNumber, 
							numberofPermutationsinThisRun,
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode, 
							Commons.ZERO,
							permutationNumberList.size(), 
							permutationNumberList, 
							intervalTree, 
							null,
							AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION,
							geneId2ListofUserDefinedGeneSetNumberMap, 
							overlapDefinition,
							enrichmentPermutationDivisionType);
					
					allMapsWithNumbers = pool.invoke( annotateWithNumbers);
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers,
							AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION);
					allMapsWithNumbers = null;
					intervalTree = null;

					System.gc();
					System.runFinalization();
				}

				// UserDefinedLibrary
				if( userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){

					int elementTypeNumber;
					String elementType;

					// For each elementTypeNumber
					for( TIntObjectIterator<String> it = elementTypeNumber2ElementTypeMap.iterator(); it.hasNext();){

						it.advance();
						elementTypeNumber = it.key();
						elementType = it.value();

						// generate User Defined Library Interval Tree
						intervalTree = generateUserDefinedLibraryIntervalTreeWithNumbers(dataFolder,elementTypeNumber, elementType, chromName);
						
						annotateWithNumbers = new AnnotateWithNumbers(
								outputFolder, 
								chromName,
								permutationNumber2RandomlyGeneratedDataHashMap, 
								runNumber,
								numberofPermutationsinThisRun,
								numberofProcessors,
								writePermutationBasedandParametricBasedAnnotationResultMode, 
								Commons.ZERO,
								permutationNumberList.size(), 
								permutationNumberList, 
								intervalTree, 
								null,
								AnnotationType.DO_USER_DEFINED_LIBRARY_ANNOTATION, 
								null, 
								overlapDefinition,
								enrichmentPermutationDivisionType);
						
						allMapsWithNumbers = pool.invoke( annotateWithNumbers);
						accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers,
								AnnotationType.DO_USER_DEFINED_LIBRARY_ANNOTATION);
						allMapsWithNumbers = null;
						intervalTree = null;

						System.gc();
						System.runFinalization();

					}// End of for each elementTypeNumber
				}

				// KEGGPathway
				if( keggPathwayAnnotationType.doKEGGPathwayAnnotation() && 
						!( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
						!( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){
					// ucsc RefSeq Genes
					// generate UCSC RefSeq Genes interval tree
					intervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers( dataFolder, chromName);
					
					annotateWithNumbers = new AnnotateWithNumbers(
							outputFolder, 
							chromName,
							permutationNumber2RandomlyGeneratedDataHashMap, 
							runNumber, 
							numberofPermutationsinThisRun,
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode, 
							Commons.ZERO,
							permutationNumberList.size(), 
							permutationNumberList, 
							intervalTree, 
							null,
							AnnotationType.DO_KEGGPATHWAY_ANNOTATION, 
							geneId2ListofKeggPathwayNumberMap,
							overlapDefinition,
							enrichmentPermutationDivisionType);
					
					allMapsWithNumbers = pool.invoke( annotateWithNumbers);
					
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.DO_KEGGPATHWAY_ANNOTATION);
					
					allMapsWithNumbers = null;
					intervalTree = null;

					System.gc();
					System.runFinalization();
				}

				if( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
						!( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){

					// New Functionality START
					// tfbs
					// Kegg Pathway (exon Based, regulation Based, all Based)
					// tfbs and Kegg Pathway (exon Based, regulation Based, all
					// Based)
					// generate tf interval tree and ucsc refseq genes interval
					// tree
					tfIntervalTree = generateTfbsIntervalTreeWithNumbers( dataFolder, chromName);
					ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers( dataFolder, chromName);
					
					annotateWithNumbers = new AnnotateWithNumbers(
							outputFolder, 
							chromName,
							permutationNumber2RandomlyGeneratedDataHashMap, 
							runNumber, 
							numberofPermutationsinThisRun,
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode, 
							Commons.ZERO,
							permutationNumberList.size(), 
							permutationNumberList, 
							tfIntervalTree,
							ucscRefSeqGenesIntervalTree, 
							AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION,
							geneId2ListofKeggPathwayNumberMap, 
							overlapDefinition,
							enrichmentPermutationDivisionType);
					
					allMapsWithNumbers = pool.invoke( annotateWithNumbers);
					// Will be used for TF and KEGG Pathway Enrichment or
					// for TF and CellLine and KEGG Pathway Enrichment
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_ANNOTATION);
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.DO_KEGGPATHWAY_ANNOTATION);
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION);

					allMapsWithNumbers = null;
					tfIntervalTree = null;
					ucscRefSeqGenesIntervalTree = null;
					// New Functionality END

					System.gc();
					System.runFinalization();

				}

				if( !( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
						tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){

					// New Functionality START
					// tfbs
					// Kegg Pathway (exon Based, regulation Based, all Based)
					// tfbs and Kegg Pathway (exon Based, regulation Based, all
					// Based)
					// generate tf interval tree and ucsc refseq genes interval
					// tree
					tfIntervalTree = generateTfbsIntervalTreeWithNumbers( dataFolder, chromName);
					ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers( dataFolder, chromName);
					
					annotateWithNumbers = new AnnotateWithNumbers(
							outputFolder, 
							chromName,
							permutationNumber2RandomlyGeneratedDataHashMap, 
							runNumber, 
							numberofPermutationsinThisRun,
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode, 
							Commons.ZERO,
							permutationNumberList.size(), 
							permutationNumberList, 
							tfIntervalTree,
							ucscRefSeqGenesIntervalTree, 
							AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
							geneId2ListofKeggPathwayNumberMap, 
							overlapDefinition,
							enrichmentPermutationDivisionType);
					
					allMapsWithNumbers = pool.invoke( annotateWithNumbers);
					// Will be used for Tf and KeggPathway Enrichment or
					// for Tf and CellLine and KeggPathway Enrichment
					
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_ANNOTATION);
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.DO_KEGGPATHWAY_ANNOTATION);
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION);

					allMapsWithNumbers = null;
					tfIntervalTree = null;
					ucscRefSeqGenesIntervalTree = null;
					// New Functionality END

					System.gc();
					System.runFinalization();

				}

				if( bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){
					
					tfIntervalTree = generateTfbsIntervalTreeWithNumbers( dataFolder, chromName);
					ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers( dataFolder, chromName);

					annotateWithNumbers = new AnnotateWithNumbers(
							outputFolder, 
							chromName,
							permutationNumber2RandomlyGeneratedDataHashMap, 
							runNumber, 
							numberofPermutationsinThisRun,
							numberofProcessors,
							writePermutationBasedandParametricBasedAnnotationResultMode, 
							Commons.ZERO,
							permutationNumberList.size(), 
							permutationNumberList, 
							tfIntervalTree,
							ucscRefSeqGenesIntervalTree,
							AnnotationType.DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
							geneId2ListofKeggPathwayNumberMap, 
							overlapDefinition,
							enrichmentPermutationDivisionType);
					
					allMapsWithNumbers = pool.invoke( annotateWithNumbers);

					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_ANNOTATION);
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.DO_KEGGPATHWAY_ANNOTATION);
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION);
					accumulate( allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION);

					allMapsWithNumbers = null;
					tfIntervalTree = null;
					ucscRefSeqGenesIntervalTree = null;
					// New Functionality END

					System.gc();
					System.runFinalization();

				}

				endTimeOnlyAnnotationPermutationsForEachChromosome = System.currentTimeMillis();

				GlanetRunner.appendLog( "Annotation of Permutations has took " + ( float)( ( endTimeOnlyAnnotationPermutationsForEachChromosome - startTimeOnlyAnnotationPermutationsForEachChromosome) / 1000) + " seconds.");
				if( GlanetRunner.shouldLog())logger.info( "Annotation of Permutations has took " + ( float)( ( endTimeOnlyAnnotationPermutationsForEachChromosome - startTimeOnlyAnnotationPermutationsForEachChromosome) / 1000) + " seconds.");
				/********************************************************************************************************/
				/***************************** ANNOTATE PERMUTATIONS ENDS ***********************************************/
				/********************************************************************************************************/

				endTimeEverythingIncludedAnnotationPermutationsForEachChromosome = System.currentTimeMillis();
				GlanetRunner.appendLog( "RunNumber: " + runNumber + " For Chromosome: " + chromName.convertEnumtoString() + " Annotation of " + numberofPermutationsinThisRun + " permutations where each of them has " + chromosomeBaseOriginalInputLines.size() + "  intervals took  " + ( float)( ( endTimeEverythingIncludedAnnotationPermutationsForEachChromosome - startTimeEverythingIncludedAnnotationPermutationsForEachChromosome) / 1000) + " seconds.");
				GlanetRunner.appendLog( "******************************************************************************************");

				if( GlanetRunner.shouldLog())logger.info( "RunNumber: " + runNumber + " For Chromosome: " + chromName.convertEnumtoString() + " Annotation of " + numberofPermutationsinThisRun + " permutations where each of them has " + chromosomeBaseOriginalInputLines.size() + "  intervals took  " + ( float)( ( endTimeEverythingIncludedAnnotationPermutationsForEachChromosome - startTimeEverythingIncludedAnnotationPermutationsForEachChromosome) / 1000) + " seconds.");
				if( GlanetRunner.shouldLog())logger.info( "******************************************************************************************");

				permutationNumber2RandomlyGeneratedDataHashMap.clear();
				permutationNumber2RandomlyGeneratedDataHashMap = null;
				annotateWithNumbers = null;

				// annotateDnaseTFHistoneWithNumbers = null;

				generateRandomData = null;
				chromosomeBaseOriginalInputLines = null;

				System.gc();
				System.runFinalization();

			}// end of if: chromosome based input lines is not null

		}// End of for: each chromosome
		/******************************************************************************************************/
		/********************************* FOR EACH G19 CHROMOSOME ENDS ***************************************/
		/******************************************************************************************************/

		permutationNumberList = null;

		pool.shutdown();

		if( pool.isTerminated()){
			GlanetRunner.appendLog( "ForkJoinPool is terminated ");
			if( GlanetRunner.shouldLog())logger.info( "ForkJoinPool is terminated ");
		}

		endTimeAllPermutationsAllChromosomes = System.currentTimeMillis();

		GlanetRunner.appendLog( "RUN_NUMBER: " + runNumber + " NUMBER_OF_PERMUTATIONS:  " + numberofPermutationsinThisRun + " took " + ( float)( ( endTimeAllPermutationsAllChromosomes - startTimeAllPermutationsAllChromosomes) / 1000) + " seconds.");
		if( GlanetRunner.shouldLog())logger.info( "RUN_NUMBER: " + runNumber + " NUMBER_OF_PERMUTATIONS:  " + numberofPermutationsinThisRun + " took " + ( float)( ( endTimeAllPermutationsAllChromosomes - startTimeAllPermutationsAllChromosomes) / 1000) + " seconds.");

		/*************************************************************************************************************************/
		/***************************************** CONVERT starts*****************************************************************/
		/*************************************************************************************************************************/
		// We have permutationNumber augmented mixed number at hand.
		// Here we fill elementNumber2ALLMap and originalElementNumber2KMap in convert methods
		// Here elementNumber refers to the permutationNumber removed from augmented mixed number
		// Also other unnecessary numbers are removed.
		// Therefore "elementNumber" does not has the same [length and order] as
		// the number coming from accumulatedAllMapsWithNumbers
		// So we have a GeneratedMixedNumberDescriptionOrderLength
		if( dnaseAnnotationType.doDnaseAnnotation()){
			// convert(accumulatedAllMapsDnaseTFHistoneWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap(),
			// dnase2AllKMap, originalDnase2KMap,
			// GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER);
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap(), 
					dnase2AllKMap,
					originalDnase2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER);
		}

		if( tfAnnotationType.doTFAnnotation()){

			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),
					tfbs2AllKMap,
					originalTfbs2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
		}

		if( histoneAnnotationType.doHistoneAnnotation()){
			// convert(accumulatedAllMapsDnaseTFHistoneWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap(),
			// histone2AllKMap, originalHistone2KMap,
			// GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap(),
					histone2AllKMap,
					originalHistone2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
		}

		// Gene
		if( geneAnnotationType.doGeneAnnotation()){
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberGeneNumber2KMap(), gene2AllKMap,
					originalGene2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGIT_PERMUTATIONNUMBER_10DIGIT_GENENUMBER);
		}

		// UserDefinedGeneSet
		if( userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap(),
					exonBasedUserDefinedGeneSet2AllKMap,
					originalExonBasedUserDefinedGeneSet2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap(),
					regulationBasedUserDefinedGeneSet2AllKMap,
					originalRegulationBasedUserDefinedGeneSet2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap(),
					allBasedUserDefinedGeneSet2AllKMap,
					originalAllBasedUserDefinedGeneSet2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);
		}

		// UserDefinedLibrary
		if( userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberElementTypeNumberElementNumber2KMap(),
					elementTypeNumberElementNumber2AllKMap,
					originalElementTypeNumberElementNumber2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
		}

		// KEGG Pathway
		if( keggPathwayAnnotationType.doKEGGPathwayAnnotation()){

			convert( accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(),
					exonBasedKeggPathway2AllKMap, originalExonBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(),
					regulationBasedKeggPathway2AllKMap, originalRegulationBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(),
					allBasedKeggPathway2AllKMap, originalAllBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

		}

		// TFKEGG Pathway
		if( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()){
			
			//TF
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),
					tfbs2AllKMap,
					originalTfbs2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

			//KEGGPathway
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(),
					exonBasedKeggPathway2AllKMap, 
					originalExonBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(),
					regulationBasedKeggPathway2AllKMap, 
					originalRegulationBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(),
					allBasedKeggPathway2AllKMap, 
					originalAllBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

	
			//TF KEGGPathway
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap(),
					tfExonBasedKeggPathway2AllKMap,
					originalTfExonBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap(),
					tfRegulationBasedKeggPathway2AllKMap,
					originalTfRegulationBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap(),
					tfAllBasedKeggPathway2AllKMap,
					originalTfAllBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

		}

		// TFCelllineKEGG Pathway
		if( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){
			
			//TF
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),
					tfbs2AllKMap,
					originalTfbs2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

			//KEGGPathway
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(),
					exonBasedKeggPathway2AllKMap, 
					originalExonBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(),
					regulationBasedKeggPathway2AllKMap, 
					originalRegulationBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(),
					allBasedKeggPathway2AllKMap, 
					originalAllBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

			//TF CellLine KEGGPathway
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap(),
					tfCellLineExonBasedKeggPathway2AllKMap,
					originalTfCellLineExonBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap(),
					tfCellLineRegulationBasedKeggPathway2AllKMap,
					originalTfCellLineRegulationBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap(),
					tfCellLineAllBasedKeggPathway2AllKMap,
					originalTfCellLineAllBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

		}
		
		// BOTH
		// TF KEGGPathway
		// TF CellLine KEGGPathway
		if (bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){
			
			//TF
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),
					tfbs2AllKMap,
					originalTfbs2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

			//KEGGPathway
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(),
					exonBasedKeggPathway2AllKMap, 
					originalExonBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(),
					regulationBasedKeggPathway2AllKMap, 
					originalRegulationBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert( accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(),
					allBasedKeggPathway2AllKMap, 
					originalAllBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

			
			
			//TF KEGGPathway
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap(),
					tfExonBasedKeggPathway2AllKMap,
					originalTfExonBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap(),
					tfRegulationBasedKeggPathway2AllKMap,
					originalTfRegulationBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap(),
					tfAllBasedKeggPathway2AllKMap,
					originalTfAllBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

			//TF CellLine KEGGPathway
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap(),
					tfCellLineExonBasedKeggPathway2AllKMap,
					originalTfCellLineExonBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap(),
					tfCellLineRegulationBasedKeggPathway2AllKMap,
					originalTfCellLineRegulationBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert(
					accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap(),
					tfCellLineAllBasedKeggPathway2AllKMap,
					originalTfCellLineAllBasedKeggPathway2KMap,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

		}
		/*************************************************************************************************************************/
		/***************************************** CONVERT ends*******************************************************************/
		/*************************************************************************************************************************/

		/*************************************************************************************************************************/
		/********************** WRITE PERMUTATION BASED ANNOTATION RESULTS starts*************************************************/
		/*************************************************************************************************************************/
		// Permutation Based Results
		// BufferedWriterHashMap are really needed?
		// Why created BufferedWriters are not created in append mode?
		if( writePermutationBasedAnnotationResultMode.isWritePermutationBasedAnnotationResultMode()){

			permutationBasedResultDirectory = outputFolder + Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty( "file.separator") + Commons.RESULTS + System.getProperty( "file.separator");

			if( dnaseAnnotationType.doDnaseAnnotation()){

				// DNase
				Map<Integer, BufferedWriter> permutationNumber2DnaseBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// Dnase
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsDnaseTFHistoneWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap(),
						permutationNumber2DnaseBufferedWriterHashMap,
						AnnotationType.DO_DNASE_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator"),
						Commons.DNASE,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER);
				closeBufferedWriters( permutationNumber2DnaseBufferedWriterHashMap);

				permutationNumber2DnaseBufferedWriterHashMap = null;
			}

			if( histoneAnnotationType.doHistoneAnnotation()){

				// Histone
				Map<Integer, BufferedWriter> permutationNumber2HistoneBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// Histone
				writeAnnotationstoFiles_ElementNumberCellLineNumber(
						permutationBasedResultDirectory,
						accumulatedAllMapsDnaseTFHistoneWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap(),
						permutationNumber2HistoneBufferedWriterHashMap,
						AnnotationType.DO_HISTONE_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator"),
						Commons.HISTONE,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2HistoneBufferedWriterHashMap);

				permutationNumber2HistoneBufferedWriterHashMap = null;
			}

			if( tfAnnotationType.doTFAnnotation() && !( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && !( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){

				// TF
				Map<Integer, BufferedWriter> permutationNumber2TfbsBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// Transcription Factor
				writeAnnotationstoFiles_ElementNumberCellLineNumber(
						permutationBasedResultDirectory,
						accumulatedAllMapsDnaseTFHistoneWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),
						permutationNumber2TfbsBufferedWriterHashMap,
						AnnotationType.DO_TF_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator"),
						Commons.TF,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfbsBufferedWriterHashMap);

				permutationNumber2TfbsBufferedWriterHashMap = null;
			}

			if( userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){

				// UserDefinedGeneSet
				Map<Integer, BufferedWriter> permutationNumber2ExonBasedUserDefinedGeneSetBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2RegulationBasedUserDefinedGeneSetBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2AllBasedUserDefinedGeneSetBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// Exon Based User Defined GeneSet
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap(),
						permutationNumber2ExonBasedUserDefinedGeneSetBufferedWriterHashMap,
						AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "exonBased" + System.getProperty( "file.separator"),
						Commons.EXON_BASED,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);
				closeBufferedWriters( permutationNumber2ExonBasedUserDefinedGeneSetBufferedWriterHashMap);

				// Regulation Based User Defined GeneSet
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap(),
						permutationNumber2RegulationBasedUserDefinedGeneSetBufferedWriterHashMap,
						AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "regulationBased" + System.getProperty( "file.separator"),
						Commons.REGULATION_BASED,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);
				closeBufferedWriters( permutationNumber2RegulationBasedUserDefinedGeneSetBufferedWriterHashMap);

				// All Based User Defined GeneSet
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap(),
						permutationNumber2AllBasedUserDefinedGeneSetBufferedWriterHashMap,
						AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "allBased" + System.getProperty( "file.separator"),
						Commons.ALL_BASED,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);
				closeBufferedWriters( permutationNumber2AllBasedUserDefinedGeneSetBufferedWriterHashMap);

				permutationNumber2ExonBasedUserDefinedGeneSetBufferedWriterHashMap = null;
				permutationNumber2RegulationBasedUserDefinedGeneSetBufferedWriterHashMap = null;
				permutationNumber2AllBasedUserDefinedGeneSetBufferedWriterHashMap = null;
			}

			if( userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){
				// UserDefinedLibrary
				// @todo better naming is necesssary
				Map<Integer, BufferedWriter> permutationNumber2ElementTypeElementNameBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// UserDefinedLibrary
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberElementTypeNumberElementNumber2KMap(),
						permutationNumber2ElementTypeElementNameBufferedWriterHashMap,
						AnnotationType.DO_USER_DEFINED_LIBRARY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator"),
						Commons.USER_DEFINED_LIBRARY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
				closeBufferedWriters( permutationNumber2ElementTypeElementNameBufferedWriterHashMap);

				permutationNumber2ElementTypeElementNameBufferedWriterHashMap = null;
			}

			// KEGGPathway
			if( keggPathwayAnnotationType.doKEGGPathwayAnnotation() && !( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && !( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){

				// KEGG Pathway
				Map<Integer, BufferedWriter> permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// Exon Based KEGG Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(),
						permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "exonBased" + System.getProperty( "file.separator"),
						Commons.EXON_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);

				// Regulation Based KEGG Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(),
						permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "regulationBased" + System.getProperty( "file.separator"),
						Commons.REGULATION_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);

				// All Based KEGG Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(),
						permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "allBased" + System.getProperty( "file.separator"),
						Commons.ALL_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);

				permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = null;

			}

			// TFKEGGPathway
			if( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && !( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){

				// TF
				Map<Integer, BufferedWriter> permutationNumber2TfbsBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// KEGG Pathway
				Map<Integer, BufferedWriter> permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// TF KEGGPathway Enrichment
				Map<Integer, BufferedWriter> permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// Tfbs
				writeAnnotationstoFiles_ElementNumberCellLineNumber(
						outputFolder,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),
						permutationNumber2TfbsBufferedWriterHashMap,
						AnnotationType.DO_TF_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator"),
						Commons.TF,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfbsBufferedWriterHashMap);

				// Exon Based Kegg Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(),
						permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "exonBased" + System.getProperty( "file.separator"),
						Commons.EXON_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);

				// Regulation Based Kegg Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(),
						permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "regulationBased" + System.getProperty( "file.separator"),
						Commons.REGULATION_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);

				// All Based Kegg Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(),
						permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "allBased" + System.getProperty( "file.separator"),
						Commons.ALL_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);

				// TF and Exon Based Kegg Pathway
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfExonBased" + System.getProperty( "file.separator"),
						Commons.TF_EXON_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap);

				// TF and Regulation Based Kegg Pathway
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfRegulationBased" + System.getProperty( "file.separator"),
						Commons.TF_REGULATION_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap);

				// TF and All Based Kegg Pathway
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfAllBased" + System.getProperty( "file.separator"),
						Commons.TF_ALL_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap);

				permutationNumber2TfbsBufferedWriterHashMap = null;

				permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = null;

				permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap = null;

			}

			// TFCellLineKEGGPathway
			if( !( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){

				// TF
				Map<Integer, BufferedWriter> permutationNumber2TfbsBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// KEGG Pathway
				Map<Integer, BufferedWriter> permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// TF CellLine KEGGPathway Enrichment
				Map<Integer, BufferedWriter> permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// Tfbs
				writeAnnotationstoFiles_ElementNumberCellLineNumber(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),
						permutationNumber2TfbsBufferedWriterHashMap,
						AnnotationType.DO_TF_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator"),
						Commons.TF,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfbsBufferedWriterHashMap);

				// Exon Based Kegg Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(),
						permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "exonBased" + System.getProperty( "file.separator"),
						Commons.EXON_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);

				// Regulation Based Kegg Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(),
						permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "regulationBased" + System.getProperty( "file.separator"),
						Commons.REGULATION_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);

				// All Based Kegg Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(),
						permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "allBased" + System.getProperty( "file.separator"),
						Commons.ALL_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);

				// Tf and Cell Line and Exon Based Kegg Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfCellLineExonBased" + System.getProperty( "file.separator"),
						Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap);

				// Tf and Cell Line and Regulation Based Kegg Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfCellLineRegulationBased" + System.getProperty( "file.separator"),
						Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap);

				// Tf and Cell Line and All Based Kegg Pathway
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfCellLineAllBased" + System.getProperty( "file.separator"),
						Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap);

				permutationNumber2TfbsBufferedWriterHashMap = null;

				permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = null;

				permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap = null;

			}

			// BOTH TFKEGGPathway and TFCellLineKEGGPathway
			if( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){

				// TF
				Map<Integer, BufferedWriter> permutationNumber2TfbsBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// KEGG Pathway
				Map<Integer, BufferedWriter> permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// TF KEGGPathway Enrichment
				Map<Integer, BufferedWriter> permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// TF CellLine KEGGPathway Enrichment
				Map<Integer, BufferedWriter> permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// TF
				writeAnnotationstoFiles_ElementNumberCellLineNumber(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),
						permutationNumber2TfbsBufferedWriterHashMap,
						AnnotationType.DO_TF_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator"),
						Commons.TF,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfbsBufferedWriterHashMap);

				// ExonKEGG
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(),
						permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "exonBased" + System.getProperty( "file.separator"),
						Commons.EXON_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);

				// RegulationKEGG
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(),
						permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "regulationBased" + System.getProperty( "file.separator"),
						Commons.REGULATION_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);

				// AllKEGG
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(),
						permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "allBased" + System.getProperty( "file.separator"),
						Commons.ALL_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);

				// TF ExonKEGG
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfExonBased" + System.getProperty( "file.separator"),
						Commons.TF_EXON_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap);

				// TF RegulationKEGG
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfRegulationBased" + System.getProperty( "file.separator"),
						Commons.TF_REGULATION_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap);

				// TF AllKEGG
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfAllBased" + System.getProperty( "file.separator"),
						Commons.TF_ALL_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap);

				// TF CellLine ExonKEGG
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfCellLineExonBased" + System.getProperty( "file.separator"),
						Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap);

				// TF CellLine RegulationKEGG
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfCellLineRegulationBased" + System.getProperty( "file.separator"),
						Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap);

				// Tf CellLine AllKEGG
				writeAnnotationstoFiles(
						permutationBasedResultDirectory,
						accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap(),
						permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap,
						AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty( "file.separator") + "tfCellLineAllBased" + System.getProperty( "file.separator"),
						Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters( permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap);

				permutationNumber2TfbsBufferedWriterHashMap = null;

				permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = null;

				permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap = null;

				permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap = null;

			}

		}// End of if: write permutation based results
		/*************************************************************************************************************************/
		/********************** WRITE PERMUTATION BASED ANNOTATION RESULTS ends***************************************************/
		/*************************************************************************************************************************/

		/*************************************************************************************************************************/
		/********************** Make null starts**********************************************************************************/
		/*************************************************************************************************************************/
		accumulatedAllMapsDnaseTFHistoneWithNumbers = null;
		accumulatedAllMapsWithNumbers = null;

		System.gc();
		System.runFinalization();
		/*************************************************************************************************************************/
		/********************** Make null ends************************************************************************************/
		/*************************************************************************************************************************/

	}

	// NEW FUNCIONALITY ADDED

	public static void writeToBeCollectedNumberofOverlapsForUserDefinedLibrary( 
			String outputFolder,
			TIntObjectMap<String> elementTypeNumber2ElementTypeMap,
			TIntIntMap originalElementTypeNumberElementNumber2KMap,
			TIntObjectMap<TIntList> elementTypeNumberElementNumber2AllKMap, 
			String runName) {

		String elementType;
		int elementTypeNumber;

		TIntObjectMap<TIntIntMap> elementTypeNumber2ElementNumber2KMap = new TIntObjectHashMap<TIntIntMap>();
		TIntObjectMap<TIntObjectMap<TIntList>> elementTypeNumber2ElementNumber2AllKMap = new TIntObjectHashMap<TIntObjectMap<TIntList>>();

		// Initialization
		for( TIntObjectIterator<String> it = elementTypeNumber2ElementTypeMap.iterator(); it.hasNext();){

			it.advance();

			elementTypeNumber = it.key();
			elementType = it.value();

			elementTypeNumber2ElementNumber2KMap.put( elementTypeNumber, new TIntIntHashMap());

			// Pay attention
			// We didn't initialize TIntList
			elementTypeNumber2ElementNumber2AllKMap.put( elementTypeNumber, new TIntObjectHashMap<TIntList>());

		}// End of each elementTypeNumber

		// Fill the first argument using the second argument
		// Fill elementTypeBased elementNumber2KMap and elementNumber2AllKMap
		UserDefinedLibraryUtility.fillElementTypeNumberBasedMaps( 
				elementTypeNumber2ElementNumber2KMap,
				originalElementTypeNumberElementNumber2KMap);
		
		UserDefinedLibraryUtility.fillElementTypeNumberBasedMaps( 
				elementTypeNumber2ElementNumber2AllKMap,
				elementTypeNumberElementNumber2AllKMap);

		// For each elementTypeNumber map write
		for( TIntObjectIterator<TIntIntMap> it = elementTypeNumber2ElementNumber2KMap.iterator(); it.hasNext();){
			it.advance();

			elementTypeNumber = it.key();
			TIntIntMap elementNumber2KMap = it.value();

			elementType = elementTypeNumber2ElementTypeMap.get( elementTypeNumber);

			TIntObjectMap<TIntList> elementNumber2AllKMap = elementTypeNumber2ElementNumber2AllKMap.get( elementTypeNumber);

			writeToBeCollectedNumberofOverlaps(
					outputFolder,
					elementNumber2KMap,
					elementNumber2AllKMap,
					Commons.TO_BE_COLLECTED_USER_DEFINED_LIBRARY_NUMBER_OF_OVERLAPS + elementType + System.getProperty( "file.separator") + Commons.RUNS_DIRECTORY,
					runName);

		}// End of each elementTypeNumberMap

	}
	

	// TIntIntMap TIntObjectMap<TIntList> version starts
	public static void writeToBeCollectedNumberofOverlaps( 
			String outputFolder,
			TIntIntMap originalPermutationNumberRemovedMixedNumber2KMap,
			TIntObjectMap<TIntList> permutationNumberRemovedMixedNumber2AllKMap, 
			String toBePolledDirectoryName,
			String runNumber) {

		int permutationNumberRemovedMixedNumber;
		int originalNumberofOverlaps;

		TIntList permutationSpecificNumberofOverlapsList;

		BufferedWriter bufferedWriter = null;

		try{
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter( outputFolder + toBePolledDirectoryName + "_" + runNumber + ".txt"));

			for( TIntIntIterator it = originalPermutationNumberRemovedMixedNumber2KMap.iterator(); it.hasNext();){

				it.advance();
				permutationNumberRemovedMixedNumber = it.key();
				originalNumberofOverlaps = it.value();

				// debug starts
				if( permutationNumberRemovedMixedNumber < 0){
					System.out.println( "there is a situation 3");
					System.out.println( permutationNumberRemovedMixedNumber);
				}
				// debug ends

				bufferedWriter.write( permutationNumberRemovedMixedNumber + "\t" + originalNumberofOverlaps + "|");

				permutationSpecificNumberofOverlapsList = permutationNumberRemovedMixedNumber2AllKMap.get( permutationNumberRemovedMixedNumber);

				if( permutationSpecificNumberofOverlapsList != null){

					for( TIntIterator it2 = permutationSpecificNumberofOverlapsList.iterator(); it2.hasNext();){
						bufferedWriter.write( it2.next() + ",");
					}

				}

				bufferedWriter.write( System.getProperty( "line.separator"));

				// if permutationNumberofOverlapsList is null
				// do nothing

			}// End of outer loop

			bufferedWriter.close();
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	// TIntIntMap TIntObjectMap<TIntList> version ends

	public static void writeToBeCollectedNumberofOverlaps( String outputFolder,
			TLongIntMap originalPermutationNumberRemovedMixedNumber2KMap,
			TLongObjectMap<TIntList> permutationNumberRemovedMixedNumber2AllKMap, String toBePolledDirectoryName,
			String runNumber) {

		long permutationNumberRemovedMixedNumber;
		int originalNumberofOverlaps;

		TIntList permutationSpecificNumberofOverlapsList;

		BufferedWriter bufferedWriter = null;

		try{
			bufferedWriter = new BufferedWriter(
					FileOperations.createFileWriter( outputFolder + toBePolledDirectoryName + "_" + runNumber + ".txt"));

			for( TLongIntIterator it = originalPermutationNumberRemovedMixedNumber2KMap.iterator(); it.hasNext();){

				it.advance();
				permutationNumberRemovedMixedNumber = it.key();
				originalNumberofOverlaps = it.value();

				bufferedWriter.write( permutationNumberRemovedMixedNumber + "\t" + originalNumberofOverlaps + "|");

				permutationSpecificNumberofOverlapsList = permutationNumberRemovedMixedNumber2AllKMap.get( permutationNumberRemovedMixedNumber);

				if( permutationSpecificNumberofOverlapsList != null){

					for( TIntIterator it2 = permutationSpecificNumberofOverlapsList.iterator(); it2.hasNext();){
						bufferedWriter.write( it2.next() + ",");
					}

				}

				bufferedWriter.write( System.getProperty( "line.separator"));

				// if permutationNumberofOverlapsList is null
				// do nothing
			}// End of outer loop

			bufferedWriter.close();
		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

	}

	public static void writeJavaRunTimeMemoryInformation() {

		if( GlanetRunner.shouldLog())logger.info( "Java runtime max memory: " + ( java.lang.Runtime.getRuntime().maxMemory() / Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE) + "\t" + "MBs");
		if( GlanetRunner.shouldLog())logger.info( "Java runtime total memory: " + ( java.lang.Runtime.getRuntime().totalMemory() / Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE) + "\t" + "MBs");
		if( GlanetRunner.shouldLog())logger.info( "Java runtime free memory: " + ( java.lang.Runtime.getRuntime().freeMemory() / Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE) + "\t" + "MBs");
		if( GlanetRunner.shouldLog())logger.info( "Java runtime available processors: " + java.lang.Runtime.getRuntime().availableProcessors());

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
	public static void main( String[] args) {

		/***********************************************************************************/
		/**************Memory Usage Before Enrichment***************************************/
		/***********************************************************************************/
		// if( GlanetRunner.shouldLog())logger.info("Memory Used Before Enrichment" + "\t" +
		// ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE)
		// + "\t" + "MBs");
		/***********************************************************************************/
		/**************Memory Usage Before Enrichment***************************************/
		/***********************************************************************************/
		
		//Get Number of processors
		int numberofProcessors = Commons.NUMBER_OF_AVAILABLE_PROCESSORS;

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];

		// jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();

		if( jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		// jobName ends

		
		/***********************************************************************************/
		/********************FOR SLURM Output starts ***************************************/
		/***********************************************************************************/
		//Write if this is a commandLine DataDrivenExperiment Run
		String glanetRunType = args[CommandLineArguments.GLANETRun.value()];
		
		if(glanetRunType.equalsIgnoreCase(Commons.GLANET_COMMANDLINE_DATADRIVENEXPERIMENT_RUN)){
			System.out.println("JobName: " + jobName + "\t" + "Number of processors: " + numberofProcessors); 
		}
		/***********************************************************************************/
		/********************FOR SLURM Output ends *****************************************/
		/***********************************************************************************/
	
		
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty( "file.separator") + jobName + System.getProperty( "file.separator");
		String givenInputDataFolder = outputFolder + Commons.GIVENINPUTDATA + System.getProperty( "file.separator");

		int overlapDefinition = Integer.parseInt( args[CommandLineArguments.NumberOfBasesRequiredForOverlap.value()]);

		// Set the number of total permutations
		int numberofTotalPermutations = Integer.parseInt( args[CommandLineArguments.NumberOfPermutation.value()]);

		// Set the number of permutations in each run
		int numberofPermutationsInEachRun = Integer.parseInt( args[CommandLineArguments.NumberOfPermutationsInEachRun.value()]);

		// Set the Generate Random Data Mode
		GenerateRandomDataMode generateRandomDataMode = GenerateRandomDataMode.convertStringtoEnum( args[CommandLineArguments.GenerateRandomDataMode.value()]);

		// Set the Write Mode of Generated Random Data
		WriteGeneratedRandomDataMode writeGeneratedRandomDataMode = WriteGeneratedRandomDataMode.convertStringtoEnum( args[CommandLineArguments.WriteGeneratedRandomDataMode.value()]);

		// Set the Write Mode of Permutation Based and Parametric Based Annotation Result
		WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode = WritePermutationBasedandParametricBasedAnnotationResultMode.convertStringtoEnum( args[CommandLineArguments.WritePermutationBasedandParametricBasedAnnotationResultMode.value()]);

		// Set the Write Mode of the Permutation Based Annotation Result
		WritePermutationBasedAnnotationResultMode writePermutationBasedAnnotationResultMode = WritePermutationBasedAnnotationResultMode.convertStringtoEnum( args[CommandLineArguments.WritePermutationBasedAnnotationResultMode.value()]);

		// Dnase Annotation, DO or DO_NOT
		AnnotationType dnaseAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.DnaseAnnotation.value()]);

		// Histone Annotation, DO or DO_NOT
		AnnotationType histoneAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.HistoneAnnotation.value()]);

		// TF Annotation, DO or DO_NOT
		AnnotationType tfAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.TfAnnotation.value()]);

		// DO_GENE_ANNOTATION
		AnnotationType geneAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.GeneAnnotation.value()]);

		// KEGG Pathway Annotation, DO or DO_NOT
		AnnotationType keggPathwayAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.KeggPathwayAnnotation.value()]);

		// TFKEGGPathway Annotation, DO or DO_NOT
		AnnotationType tfKeggPathwayAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.TfAndKeggPathwayAnnotation.value()]);

		// TFCellLineKEGGPathway Annotation, DO or DO_NOT
		AnnotationType tfCellLineKeggPathwayAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()]);

		// Will be set if TFKEGGPathway and tfCellLineKeggPathwayAnnotationType are both checked
		AnnotationType bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType = AnnotationType.DO_NOT_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;

		GivenInputDataType givenInputsSNPsorIntervals = GivenInputDataType.convertStringtoEnum( args[CommandLineArguments.GivenInputDataType.value()]);

		// 23 May 2015
		EnrichmentZScoreMode enrichmentZScoreMode = EnrichmentZScoreMode.convertStringtoEnum(args[CommandLineArguments.EnrichmentZScoreMode.value()]);
		
		//30 OCTOBER 2015 
		//TODO This parameter has to be filled from arguments 
		//But for the time being it is set in Enrichment class
		AssociationMeasureType associationMeasureType = AssociationMeasureType.NUMBER_OF_OVERLAPPING_INTERVALS;

		// 18 FEB 2015
		// performEnrichment is not used since GLANETRunner calls
		// Enrichment.main() function in case of performEnrichment is DO_ENRICH
		/*********************************************************************************/
		/************************** USER DEFINED GENESET *********************************/
		/*********************************************************************************/
		// User Defined GeneSet Enrichment, DO or DO_NOT
		AnnotationType userDefinedGeneSetAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.UserDefinedGeneSetAnnotation.value()]);

		String userDefinedGeneSetInputFile = args[CommandLineArguments.UserDefinedGeneSetInput.value()];

		GeneInformationType geneInformationType = GeneInformationType.convertStringtoEnum( args[CommandLineArguments.UserDefinedGeneSetGeneInformation.value()]);

		String userDefinedGeneSetName = args[CommandLineArguments.UserDefinedGeneSetName.value()];
		/*********************************************************************************/
		/************************** USER DEFINED GENESET *********************************/
		/*********************************************************************************/

		/***********************************************************************************/
		/************************** USER DEFINED LIBRARY ***********************************/
		// User Defined Library Annotation, DO or DO_NOT
		AnnotationType userDefinedLibraryAnnotationType = AnnotationType.convertStringtoEnum( args[CommandLineArguments.UserDefinedLibraryAnnotation.value()]);
		/************************** USER DEFINED LIBRARY ***********************************/
		/***********************************************************************************/

		writeJavaRunTimeMemoryInformation();

		/***********************************************************************************************/
		/************************** READ ORIGINAL INPUT LINES STARTS ***********************************/
		/***********************************************************************************************/
		// SET the Input Data File
		String inputDataFileName = givenInputDataFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE_0BASED_START_END_GRCh37_p13;

		List<InputLine> originalInputLines = new ArrayList<InputLine>();

		// Read original input data lines in to a list
		Enrichment.readOriginalInputDataLines( originalInputLines, inputDataFileName);
		/***********************************************************************************************/
		/************************** READ ORIGINAL INPUT LINES ENDS *************************************/
		/***********************************************************************************************/

		/***********************************************************************************************/
		/********************* DELETE OLD FILES STARTS *************************************************/
		String annotationForPermutationsOutputDirectory = outputFolder + Commons.ANNOTATION_FOR_PERMUTATIONS;
		List<String> notToBeDeleted = new ArrayList<String>();
		// FileOperations.deleteDirectoriesandFilesUnderThisDirectory(annotateOutputBaseDirectoryName,notToBeDeleted);
		FileOperations.deleteOldFiles( annotationForPermutationsOutputDirectory, notToBeDeleted);

		String toBeDeletedDirectoryName = outputFolder + Commons.ENRICHMENT_DIRECTORY;
		FileOperations.deleteOldFiles( toBeDeletedDirectoryName);
		/********************* DELETE OLD FILES ENDS ***************************************************/
		/***********************************************************************************************/

		/**********************************************************************************************/
		/********************* FILL GENEID 2 USER DEFINED GENESET NUMBER MAP STARTS *******************/
		/**********************************************************************************************/
		TIntObjectMap<String> userDefinedGeneSetNumber2UserDefinedGeneSetNameMap = new TIntObjectHashMap<String>();
		// Used in filling geneId2ListofUserDefinedGeneSetNumberMap
		TObjectIntMap<String> userDefinedGeneSetName2UserDefinedGeneSetNumberMap = new TObjectIntHashMap<String>();

		TIntObjectMap<TIntList> geneId2ListofUserDefinedGeneSetNumberMap = new TIntObjectHashMap<TIntList>();

		if( userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){
			UserDefinedGeneSetUtility.createNcbiGeneId2ListofUserDefinedGeneSetNumberMap(
					dataFolder,
					userDefinedGeneSetInputFile, 
					geneInformationType,
					userDefinedGeneSetName2UserDefinedGeneSetNumberMap,
					userDefinedGeneSetNumber2UserDefinedGeneSetNameMap, 
					geneId2ListofUserDefinedGeneSetNumberMap);
		}
		/**********************************************************************************************/
		/********************* FILL GENEID 2 USER DEFINED GENESET NUMBER MAP ENDS *********************/
		/**********************************************************************************************/

		/**********************************************************************************************/
		/********************* FILL GENEID 2 KEGG PATHWAY NUMBER MAP STARTS ***************************/
		/**********************************************************************************************/
		// For efficiency
		// Fill this map only once.
		// NCBI Gene Id is Integer
		TIntObjectMap<TIntList> geneId2KeggPathwayNumberMap = new TIntObjectHashMap<TIntList>();
		TObjectIntMap<String> keggPathwayName2KeggPathwayNumberMap = new TObjectIntHashMap<String>();

		if( keggPathwayAnnotationType.doKEGGPathwayAnnotation() || 
				tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() || 
				tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){

			// all_possible_keggPathwayName_2_keggPathwayNumber_map.txt
			KeggPathwayUtility.fillKeggPathwayName2KeggPathwayNumberMap( dataFolder,
					Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME,
					Commons.ALL_POSSIBLE_KEGGPATHWAY_NAME_2_NUMBER_OUTPUT_FILENAME,
					keggPathwayName2KeggPathwayNumberMap);
			KeggPathwayUtility.createNcbiGeneId2KeggPathwayNumberMap( dataFolder,
					Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, geneId2KeggPathwayNumberMap,
					keggPathwayName2KeggPathwayNumberMap);

		}
		/**********************************************************************************************/
		/********************* FILL GENEID 2 KEGG PATHWAY NUMBER MAP ENDS ****************************/
		/*********************************************************************************************/

		
		/*********************************************************************************/
		/**************DO NOT MAKE SAME CALCULATIONS AGAIN and AGAIN starts***************/
		/*********************************************************************************/
		if( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() || 
				tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){

			tfAnnotationType = AnnotationType.DO_NOT_TF_ANNOTATION;
			keggPathwayAnnotationType = AnnotationType.DO_NOT_KEGGPATHWAY_ANNOTATION;

		}

		if( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
				tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){

			tfKeggPathwayAnnotationType = AnnotationType.DO_NOT_TF_KEGGPATHWAY_ANNOTATION;
			tfCellLineKeggPathwayAnnotationType = AnnotationType.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;

			bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType = AnnotationType.DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;
		}
		/*********************************************************************************/
		/*************DO NOT MAKE SAME CALCULATIONS AGAIN and AGAIN ends******************/
		/*********************************************************************************/

		/************************************************************************************************/
		/******************************* CALCULATE NUMBER OF RUNS STARTS ********************************/
		/************************************************************************************************/
		// for loop starts
		// NUMBER_OF_PERMUTATIONS has to be multiple of 1000 like 1000, 5000, 10000, 50000, 100000
		int numberofRuns = 0;
		int numberofRemainedPermutations = 0;
		String runName;

		// In case of numberofPermutationsInEachRun is greater than numberofTotalPermutations
		if( numberofPermutationsInEachRun > numberofTotalPermutations){
			numberofPermutationsInEachRun = numberofTotalPermutations;
		}

		numberofRuns = numberofTotalPermutations / numberofPermutationsInEachRun;
		numberofRemainedPermutations = numberofTotalPermutations % numberofPermutationsInEachRun;

		// Increase numberofRuns by 1
		// When numberofRemainedPermutations is nonZero
		if( numberofRemainedPermutations > 0){
			numberofRuns += 1;
		}
		/**********************************************************************************************/
		/******************************* CALCULATE NUMBER OF RUNS ENDS ********************************/
		/**********************************************************************************************/
		
		
	

		/***********************************************************************************************/
		/********************** INITIALIZATION of elementNumber2OriginalK TO NULL STARTS****************/
		/***********INITIALIZATION OF elementNumber2OriginalK MAPS for ORIGINAL DATA STARTS ************/
		/***********************************************************************************************/
		// Number2NameMaps
		TIntObjectMap<String> dnaseCellLineNumber2NameMap 	= null;
		TIntObjectMap<String> cellLineNumber2NameMap 		= null;
		TIntObjectMap<String> tfNumber2NameMap 				= null;
		TIntObjectMap<String> histoneNumber2NameMap 		= null;
		TIntObjectMap<String> geneID2GeneHugoSymbolMap 		= null;
		TIntObjectMap<String> keggPathwayNumber2NameMap 	= null;
		TIntObjectMap<String> userDefinedGeneSetNumber2NameMap = null;
		
		TIntObjectMap<String> userDefinedLibraryElementTypeNumber2NameMap = null;
		TIntObjectMap<TIntObjectMap<String>> userDefinedLibraryElementTypeNumber2ElementNumber2ElementNameMap = null;
		
		
		// ElementNumber2OriginalKMaps
		// Annotation of original data: number of overlaps: k out of n for original data

		// DNase
		TIntIntMap dnaseCellLineNumber2OriginalKMap = null;

		// TF
		TIntIntMap tfNumberCellLineNumber2OriginalKMap = null;

		// Histone
		TIntIntMap histoneNumberCellLineNumber2OriginalKMap = null;

		// Gene
		TIntIntMap geneID2OriginalKMap = null;

		// UserDefinedGeneSet
		TIntIntMap exonBasedUserDefinedGeneSet2OriginalKMap = null;
		TIntIntMap regulationBasedUserDefinedGeneSet2OriginalKMap = null;
		TIntIntMap allBasedUserDefinedGeneSet2OriginalKMap = null;

		// UserDefinedLibrary
		TIntIntMap elementTypeNumberElementNumber2OriginalKMap = null;
		

		// KEGGPathway
		TIntIntMap exonBasedKeggPathway2OriginalKMap = null;
		TIntIntMap regulationBasedKeggPathway2OriginalKMap = null;
		TIntIntMap allBasedKeggPathway2OriginalKMap = null;

		// TF and KEGGPathway Enrichment
		TIntIntMap tfExonBasedKeggPathway2OriginalKMap = null;
		TIntIntMap tfRegulationBasedKeggPathway2OriginalKMap = null;
		TIntIntMap tfAllBasedKeggPathway2OriginalKMap = null;

		// TF and CellLine and KEGGPathway Enrichment
		TLongIntMap tfCellLineExonBasedKeggPathway2OriginalKMap = null;
		TLongIntMap tfCellLineRegulationBasedKeggPathway2OriginalKMap = null;
		TLongIntMap tfCellLineAllBasedKeggPathway2OriginalKMap = null;

		
		// DNase
		if(dnaseAnnotationType.doDnaseAnnotation()){
			
			//Number2NameMap
			dnaseCellLineNumber2NameMap = new TIntObjectHashMap<String>();
		
			//Fill Number2NameMap
			FileOperations.fillNumber2NameMap(
					dnaseCellLineNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		
			
			dnaseCellLineNumber2OriginalKMap = new TIntIntHashMap();
			
		}

		// TF
		if( tfAnnotationType.doTFAnnotation()){
			
			//Number2NameMap
			cellLineNumber2NameMap 	=  new TIntObjectHashMap<String>();
			tfNumber2NameMap 		=  new TIntObjectHashMap<String>();
			
			//Fill Number2NameMaps
			FileOperations.fillNumber2NameMap(
					cellLineNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		
			FileOperations.fillNumber2NameMap(
					tfNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
		
			
			tfNumberCellLineNumber2OriginalKMap = new TIntIntHashMap();
				
		}

		// Histone
		if( histoneAnnotationType.doHistoneAnnotation()){
			
			//Number2NameMap
			cellLineNumber2NameMap 	=  new TIntObjectHashMap<String>();
			histoneNumber2NameMap 	=  new TIntObjectHashMap<String>();

			//Fill Number2NameMaps
			FileOperations.fillNumber2NameMap(
					cellLineNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		
			FileOperations.fillNumber2NameMap(
					histoneNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_HISTONE_NUMBER_2_NAME_OUTPUT_FILENAME);
			
			
			histoneNumberCellLineNumber2OriginalKMap = new TIntIntHashMap();
						
		}

		
		// Gene
		if( geneAnnotationType.doGeneAnnotation()){
					
			geneID2GeneHugoSymbolMap = new TIntObjectHashMap<String>();
			
			//Fill geneID2GeneHugoSymbolMap
			HumanGenesAugmentation.fillGeneId2GeneHugoSymbolMap( dataFolder, geneID2GeneHugoSymbolMap);
			
			geneID2OriginalKMap = new TIntIntHashMap();
			
		}

		
		// User Defined Library		
		if( userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){
			
			int elementTypeNumber;
			String elementTypeName;
			
			userDefinedLibraryElementTypeNumber2NameMap = new TIntObjectHashMap<String>();
			userDefinedLibraryElementTypeNumber2ElementNumber2ElementNameMap = new TIntObjectHashMap<TIntObjectMap<String>> ();
			TIntIntMap userDefinedLibraryElementTypeNumber2NumberofComparisonMap = new TIntIntHashMap();
			
					
			UserDefinedLibraryUtility.fillNumber2NameMap( 
					userDefinedLibraryElementTypeNumber2NameMap, 
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME,
					Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_NUMBER_2_NAME_OUTPUT_FILENAME);
			
			for(TIntObjectIterator<String> itr = userDefinedLibraryElementTypeNumber2NameMap.iterator();itr.hasNext();){
				
				itr.advance();
				
				elementTypeNumber = itr.key();
				elementTypeName = itr.value();
				
				TIntObjectMap<String> elementNumber2ElementNameMap = new TIntObjectHashMap<String>();
				
				UserDefinedLibraryUtility.fillNumber2NameMap( 
						elementNumber2ElementNameMap, 
						dataFolder,
						Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME + elementTypeName + System.getProperty( "file.separator") ,
						Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENT_NUMBER_2_NAME_OUTPUT_FILENAME);
			
				userDefinedLibraryElementTypeNumber2ElementNumber2ElementNameMap.put(elementTypeNumber, elementNumber2ElementNameMap);
				userDefinedLibraryElementTypeNumber2NumberofComparisonMap.put(elementTypeNumber, elementNumber2ElementNameMap.size());
			}//End of FOR

			elementTypeNumberElementNumber2OriginalKMap = new TIntIntHashMap();
			
		}
				
		//User Defined GeneSet
		if (userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){
			
			//Number2NameMap
			userDefinedGeneSetNumber2NameMap = new TIntObjectHashMap<String>();
			
			//Fill Number2NameMaps
			FileOperations.fillNumber2NameMap(
					userDefinedGeneSetNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_USERDEFINEDGENESET_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_USERDEFINEDGENESET_NUMBER_2_NAME_OUTPUT_FILENAME);
			
			
			exonBasedUserDefinedGeneSet2OriginalKMap 		= new TIntIntHashMap();
			regulationBasedUserDefinedGeneSet2OriginalKMap 	= new TIntIntHashMap();
			allBasedUserDefinedGeneSet2OriginalKMap 		= new TIntIntHashMap();
			
		}
		
		
		// KEGGPathway
		if( keggPathwayAnnotationType.doKEGGPathwayAnnotation()){

			//Number2NameMap
			keggPathwayNumber2NameMap = new TIntObjectHashMap<String>();
			
			//Fill Number2NameMaps
			FileOperations.fillNumber2NameMap(
					keggPathwayNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
			
			exonBasedKeggPathway2OriginalKMap = new TIntIntHashMap();
			regulationBasedKeggPathway2OriginalKMap = new TIntIntHashMap();
			allBasedKeggPathway2OriginalKMap = new TIntIntHashMap();
			
		}
		
		
		// TF KEGGPathway Enrichment
		if( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
				!tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){
			
			//Number2NameMap
			tfNumber2NameMap 			= new TIntObjectHashMap<String>();
			cellLineNumber2NameMap 		=	new TIntObjectHashMap<String>();
			keggPathwayNumber2NameMap 	= new TIntObjectHashMap<String>();

			//Fill Number2NameMaps
			FileOperations.fillNumber2NameMap(
					tfNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
			
			FileOperations.fillNumber2NameMap(
					cellLineNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
			
			FileOperations.fillNumber2NameMap(
					keggPathwayNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
			
			
			// TF
			tfNumberCellLineNumber2OriginalKMap = new TIntIntHashMap();

			// KEGGPathway
			exonBasedKeggPathway2OriginalKMap 		= new TIntIntHashMap();
			regulationBasedKeggPathway2OriginalKMap = new TIntIntHashMap();
			allBasedKeggPathway2OriginalKMap 		= new TIntIntHashMap();

			// TF KEGGPathway
			tfExonBasedKeggPathway2OriginalKMap 		= new TIntIntHashMap();
			tfRegulationBasedKeggPathway2OriginalKMap 	= new TIntIntHashMap();
			tfAllBasedKeggPathway2OriginalKMap 			= new TIntIntHashMap();
			
		}

		// TF CellLine KEGGPathway Enrichment
		if( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation() && 
				!tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()){
			
			//Number2NameMap
			tfNumber2NameMap 			=	new TIntObjectHashMap<String>();
			cellLineNumber2NameMap 		=	new TIntObjectHashMap<String>();
			keggPathwayNumber2NameMap 	=	new TIntObjectHashMap<String>();
			
			//Fill Number2NameMaps
			FileOperations.fillNumber2NameMap(
					tfNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
		
	
			FileOperations.fillNumber2NameMap(
					cellLineNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
			
			FileOperations.fillNumber2NameMap(
					keggPathwayNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
			

			// TF
			tfNumberCellLineNumber2OriginalKMap = new TIntIntHashMap();

			// KEGGPathway
			exonBasedKeggPathway2OriginalKMap = new TIntIntHashMap();
			regulationBasedKeggPathway2OriginalKMap = new TIntIntHashMap();
			allBasedKeggPathway2OriginalKMap = new TIntIntHashMap();

			// TF CellLine KEGGPathway
			tfCellLineExonBasedKeggPathway2OriginalKMap = new TLongIntHashMap();
			tfCellLineRegulationBasedKeggPathway2OriginalKMap = new TLongIntHashMap();
			tfCellLineAllBasedKeggPathway2OriginalKMap = new TLongIntHashMap();
			
		}

		// BOTH
		// TF KEGGPathway Enrichment
		// TF CellLine KEGGPathway Enrichment
		if( bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

			//Number2NameMap
			tfNumber2NameMap 			=	new TIntObjectHashMap<String>();
			cellLineNumber2NameMap 		=	new TIntObjectHashMap<String>();
			keggPathwayNumber2NameMap 	=	new TIntObjectHashMap<String>();
			
			//Fill Number2NameMaps
			FileOperations.fillNumber2NameMap(
					tfNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
		
	
			FileOperations.fillNumber2NameMap(
					cellLineNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
			
			FileOperations.fillNumber2NameMap(
					keggPathwayNumber2NameMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_KEGGPATHWAY_NUMBER_2_NAME_OUTPUT_FILENAME);
			

			
			// TF
			tfNumberCellLineNumber2OriginalKMap = new TIntIntHashMap();

			// KEGGPathway
			exonBasedKeggPathway2OriginalKMap 		= new TIntIntHashMap();
			regulationBasedKeggPathway2OriginalKMap = new TIntIntHashMap();
			allBasedKeggPathway2OriginalKMap 		= new TIntIntHashMap();

			// TF KEGGPathway
			tfExonBasedKeggPathway2OriginalKMap 		= new TIntIntHashMap();
			tfRegulationBasedKeggPathway2OriginalKMap 	= new TIntIntHashMap();
			tfAllBasedKeggPathway2OriginalKMap 			= new TIntIntHashMap();

			// TF CellLine KEGGPathway
			tfCellLineExonBasedKeggPathway2OriginalKMap 		= new TLongIntHashMap();
			tfCellLineRegulationBasedKeggPathway2OriginalKMap 	= new TLongIntHashMap();
			tfCellLineAllBasedKeggPathway2OriginalKMap 			= new TLongIntHashMap();
			
		}
		/***********************************************************************************************/
		/********************** INITIALIZATION of elementNumber2OriginalK TO NULL ENDS******************/
		/***********INITIALIZATION OF elementNumber2OriginalK MAPS for ORIGINAL DATA ENDS **************/
		/***********************************************************************************************/

		// Perform Enrichment WITHOUT ZSCORES (Without Keeping Number of Overlaps Coming from Each Permutation starts)
		// WITHOUT ZSCORES requires less memory since we don't keep number of overlaps coming from each permutation
		// WITHOUT ZSCORES requires more memory since we generate the interval trees for all chromosomes all at once
		// Therefore WITHOUT ZSCORES
		// Consumes Less Memory when the number of elements is in tens of thousands 
		// Consumes More Memory when the number of elements is in hundreds
		if( enrichmentZScoreMode.isPerformEnrichmentWithoutZScore()){

			/**********************************************************************************************/
			/********************* FOR LOOP FOR RUN NUMBERS STARTS ****************************************/
			for( int runNumber = 1; runNumber <= numberofRuns; runNumber++){

				GlanetRunner.appendLog( "**************	" + runNumber + ". Run" + "	******************	starts");
				if( GlanetRunner.shouldLog())logger.info( "**************	" + runNumber + ". Run" + "	******************	starts");

				runName = jobName + "_" + Commons.RUN + runNumber;

				/***********************************************************************************************/
				/************************** ANNOTATE PERMUTATIONS STARTS ***************************************/
				/***********************************************************************************************/
				GlanetRunner.appendLog( "Concurrent programming has started.");
				if( GlanetRunner.shouldLog())logger.info( "Concurrent programming has started.");

				// Concurrent Programming
				// First Generate Random Data for all permutations
				// Then Annotate Permutations (Random Data) concurrently
				// elementName2AllKMap and originalElementName2KMap will be filled here
				
				//For the last run with numberofRemainedPermutations is nonZero and less than numberofPermutationsInEachRun
				if( ( runNumber == numberofRuns) && ( numberofRemainedPermutations > 0)){

					Enrichment.annotateAllPermutationsInThreadsForAllChromosomes(
							outputFolder, 
							dataFolder,
							givenInputsSNPsorIntervals, 
							numberofProcessors, 
							runNumber,
							numberofRemainedPermutations, 
							numberofPermutationsInEachRun, 
							runName,
							originalInputLines,
							generateRandomDataMode, 
							writeGeneratedRandomDataMode,
							writePermutationBasedandParametricBasedAnnotationResultMode,
							writePermutationBasedAnnotationResultMode, 
							dnaseCellLineNumber2OriginalKMap,
							tfNumberCellLineNumber2OriginalKMap, 
							histoneNumberCellLineNumber2OriginalKMap,
							geneID2OriginalKMap, 
							exonBasedUserDefinedGeneSet2OriginalKMap,
							regulationBasedUserDefinedGeneSet2OriginalKMap, 
							allBasedUserDefinedGeneSet2OriginalKMap,
							elementTypeNumberElementNumber2OriginalKMap, 
							exonBasedKeggPathway2OriginalKMap,
							regulationBasedKeggPathway2OriginalKMap, 
							allBasedKeggPathway2OriginalKMap,
							tfExonBasedKeggPathway2OriginalKMap, 
							tfRegulationBasedKeggPathway2OriginalKMap,
							tfAllBasedKeggPathway2OriginalKMap, 
							tfCellLineExonBasedKeggPathway2OriginalKMap,
							tfCellLineRegulationBasedKeggPathway2OriginalKMap,
							tfCellLineAllBasedKeggPathway2OriginalKMap, 
							dnaseAnnotationType, 
							histoneAnnotationType,
							tfAnnotationType, 
							geneAnnotationType, 
							userDefinedGeneSetAnnotationType,
							userDefinedLibraryAnnotationType, 
							keggPathwayAnnotationType, 
							tfKeggPathwayAnnotationType,
							tfCellLineKeggPathwayAnnotationType, 
							bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType,
							userDefinedGeneSetName,
							overlapDefinition, 
							geneId2KeggPathwayNumberMap, 
							geneId2ListofUserDefinedGeneSetNumberMap,
							userDefinedLibraryElementTypeNumber2NameMap,
							userDefinedLibraryElementTypeNumber2ElementNumber2ElementNameMap,
							dnaseCellLineNumber2NameMap,
							cellLineNumber2NameMap,
							tfNumber2NameMap,
							histoneNumber2NameMap,
							geneID2GeneHugoSymbolMap,
							keggPathwayNumber2NameMap,
							userDefinedGeneSetNumber2NameMap);

				}
				
				//For the rest of the runs
				else{

					Enrichment.annotateAllPermutationsInThreadsForAllChromosomes(
							outputFolder, 
							dataFolder,
							givenInputsSNPsorIntervals, 
							numberofProcessors, 
							runNumber,
							numberofPermutationsInEachRun, 
							numberofPermutationsInEachRun, 
							runName,
							originalInputLines,
							generateRandomDataMode, 
							writeGeneratedRandomDataMode,
							writePermutationBasedandParametricBasedAnnotationResultMode,
							writePermutationBasedAnnotationResultMode, 
							dnaseCellLineNumber2OriginalKMap,
							tfNumberCellLineNumber2OriginalKMap, 
							histoneNumberCellLineNumber2OriginalKMap,
							geneID2OriginalKMap, 
							exonBasedUserDefinedGeneSet2OriginalKMap,
							regulationBasedUserDefinedGeneSet2OriginalKMap, 
							allBasedUserDefinedGeneSet2OriginalKMap,
							elementTypeNumberElementNumber2OriginalKMap, 
							exonBasedKeggPathway2OriginalKMap,
							regulationBasedKeggPathway2OriginalKMap, 
							allBasedKeggPathway2OriginalKMap,
							tfExonBasedKeggPathway2OriginalKMap, 
							tfRegulationBasedKeggPathway2OriginalKMap,
							tfAllBasedKeggPathway2OriginalKMap, 
							tfCellLineExonBasedKeggPathway2OriginalKMap,
							tfCellLineRegulationBasedKeggPathway2OriginalKMap,
							tfCellLineAllBasedKeggPathway2OriginalKMap, 
							dnaseAnnotationType, 
							histoneAnnotationType,
							tfAnnotationType, 
							geneAnnotationType, 
							userDefinedGeneSetAnnotationType,
							userDefinedLibraryAnnotationType, 
							keggPathwayAnnotationType, 
							tfKeggPathwayAnnotationType,
							tfCellLineKeggPathwayAnnotationType,
							bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType,
							userDefinedGeneSetName,
							overlapDefinition, 
							geneId2KeggPathwayNumberMap, 
							geneId2ListofUserDefinedGeneSetNumberMap,
							userDefinedLibraryElementTypeNumber2NameMap,
							userDefinedLibraryElementTypeNumber2ElementNumber2ElementNameMap,
							dnaseCellLineNumber2NameMap,
							cellLineNumber2NameMap,
							tfNumber2NameMap,
							histoneNumber2NameMap,
							geneID2GeneHugoSymbolMap,
							keggPathwayNumber2NameMap,
							userDefinedGeneSetNumber2NameMap);
				}

				GlanetRunner.appendLog( "Concurrent programming has ended.");
				if( GlanetRunner.shouldLog())logger.info( "Concurrent programming has ended.");
				/**********************************************************************************************/
				/************************** ANNOTATE PERMUTATIONS ENDS ****************************************/
				/**********************************************************************************************/
				
				GlanetRunner.appendLog( "**************	" + runNumber + ". Run" + "	******************	ends");
				if( GlanetRunner.shouldLog())logger.info( "**************	" + runNumber + ". Run" + "	******************	ends");

			}// End of FOR each run number
			/*********************************************************************************************/
			/********************* FOR LOOP FOR RUN NUMBERS ENDS *****************************************/
			/*********************************************************************************************/

		}
		// Perform Enrichment WITHOUT Keeping Number of Overlaps Coming from Each Permutation ends

		// Perform Enrichment WITH ZSCORES (With Keeping Number of Overlaps Coming from Each Permutation starts)
		// Consumes More Memory when the number of elements is in tens of thousands
		// Consumes Less Memory when the number of elements is in hundreds
		else if( enrichmentZScoreMode.isPerformEnrichmentWithZScore()){

			/**********************************************************************************************/
			/********************* FOR LOOP FOR RUN NUMBERS STARTS ****************************************/
			for( int runNumber = 1; runNumber <= numberofRuns; runNumber++){

				GlanetRunner.appendLog( "**************	" + runNumber + ". Run" + "	******************	starts");
				if( GlanetRunner.shouldLog())logger.info( "**************	" + runNumber + ". Run" + "	******************	starts");

				runName = jobName + "_" + Commons.RUN + runNumber;

				/***********************************************************************************************/
				/********************* INITIALIZATION OF NUMBER2AllK MAPS for PERMUTATION DATA STARTS **********/
				/*********************** NUMBER OF OVERLAPS FOR ALL PERMUTATIONS STARTS ************************/
				/***********************************************************************************************/
				// ElementNumber2AllK
				// Annotation of each permutation's randomly generated data
				// number of overlaps: k out of n for all permutations
				/********************** INITIALIZATION TO NULL *************************************************/
				/************** INITIALIZATION of elementNumber2AllLMap to NULL starts *************************/
				// DNase
				TIntObjectMap<TIntList> dnase2AllKMap = null;

				// TF
				TIntObjectMap<TIntList> tfbs2AllKMap = null;

				// Histone
				TIntObjectMap<TIntList> histone2AllKMap = null;

				// Gene
				TIntObjectMap<TIntList> gene2AllKMap = null;

				// UserDefinedGeneSet
				TIntObjectMap<TIntList> exonBasedUserDefinedGeneSet2AllKMap = null;
				TIntObjectMap<TIntList> regulationBasedUserDefinedGeneSet2AllKMap = null;
				TIntObjectMap<TIntList> allBasedUserDefinedGeneSet2AllKMap = null;

				// UserDefinedLibrary
				TIntObjectMap<TIntList> elementTypeNumberElementNumber2AllKMap = null;

				// KEGGPathway
				TIntObjectMap<TIntList> exonBasedKeggPathway2AllKMap = null;
				TIntObjectMap<TIntList> regulationBasedKeggPathway2AllKMap = null;
				TIntObjectMap<TIntList> allBasedKeggPathway2AllKMap = null;

				// TF and KEGGPathway Enrichment
				TIntObjectMap<TIntList> tfExonBasedKeggPathway2AllKMap = null;
				TIntObjectMap<TIntList> tfRegulationBasedKeggPathway2AllKMap = null;
				TIntObjectMap<TIntList> tfAllBasedKeggPathway2AllKMap = null;

				// TF and CellLine and KEGGPathway Enrichment
				TLongObjectMap<TIntList> tfCellLineExonBasedKeggPathway2AllKMap = null;
				TLongObjectMap<TIntList> tfCellLineRegulationBasedKeggPathway2AllKMap = null;
				TLongObjectMap<TIntList> tfCellLineAllBasedKeggPathway2AllKMap = null;
				/********************** INITIALIZATION TO NULL *************************************************/
				/************** INITIALIZATION of elementNumber2AllLMap to NULL ends ***************************/

				// DNase
				if( dnaseAnnotationType.doDnaseAnnotation()){
					dnase2AllKMap = new TIntObjectHashMap<TIntList>();
				}

				// TF
				if( tfAnnotationType.doTFAnnotation()){
					tfbs2AllKMap = new TIntObjectHashMap<TIntList>();
				}

				// Histone
				if( histoneAnnotationType.doHistoneAnnotation()){
					histone2AllKMap = new TIntObjectHashMap<TIntList>();
				}

				// Gene
				if( geneAnnotationType.doGeneAnnotation()){
					gene2AllKMap = new TIntObjectHashMap<TIntList>();
				}

				// User Defined GeneSet
				if( userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){
					exonBasedUserDefinedGeneSet2AllKMap 		= new TIntObjectHashMap<TIntList>();
					regulationBasedUserDefinedGeneSet2AllKMap 	= new TIntObjectHashMap<TIntList>();
					allBasedUserDefinedGeneSet2AllKMap 			= new TIntObjectHashMap<TIntList>();
				}

				// User Defined Library
				if( userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){
					elementTypeNumberElementNumber2AllKMap = new TIntObjectHashMap<TIntList>();
				}

				// KEGGPathway
				if( keggPathwayAnnotationType.doKEGGPathwayAnnotation()){

					exonBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					regulationBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					allBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();

				}

				// TF KEGGPathway Enrichment
				if( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
						!tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){

					// TF
					tfbs2AllKMap = new TIntObjectHashMap<TIntList>();

					// KEGGPathway
					exonBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					regulationBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					allBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();

					// TF KEGGPathway
					tfExonBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					tfRegulationBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					tfAllBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();

				}

				// TF CellLine KEGGPathway Enrichment
				if( tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation() && 
						!tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()){

					// TF
					tfbs2AllKMap = new TIntObjectHashMap<TIntList>();

					// KEGGPathway
					exonBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					regulationBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					allBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();

					// TF CellLine KEGGPathway
					tfCellLineExonBasedKeggPathway2AllKMap = new TLongObjectHashMap<TIntList>();
					tfCellLineRegulationBasedKeggPathway2AllKMap = new TLongObjectHashMap<TIntList>();
					tfCellLineAllBasedKeggPathway2AllKMap = new TLongObjectHashMap<TIntList>();

				}

				// BOTH
				// TF KEGGPathway Enrichment
				// TF CellLine KEGGPathway Enrichment
				if( bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

					// TF
					tfbs2AllKMap = new TIntObjectHashMap<TIntList>();

					// KEGGPathway
					exonBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					regulationBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					allBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();

					// TF KEGGPathway
					tfExonBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					tfRegulationBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
					tfAllBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();

					// TF CellLine KEGGPathway
					tfCellLineExonBasedKeggPathway2AllKMap = new TLongObjectHashMap<TIntList>();
					tfCellLineRegulationBasedKeggPathway2AllKMap = new TLongObjectHashMap<TIntList>();
					tfCellLineAllBasedKeggPathway2AllKMap = new TLongObjectHashMap<TIntList>();

				}
				/***********************************************************************************************/
				/*********************** NUMBER OF OVERLAPS FOR ALL PERMUTATIONS ENDS **************************/
				/********************* INITIALIZATION OF NUMBER2AllK MAPS for PERMUTATION DATA ENDS ************/
				/***********************************************************************************************/

				/***********************************************************************************************/
				/************************** ANNOTATE PERMUTATIONS STARTS ***************************************/
				/***********************************************************************************************/
				GlanetRunner.appendLog( "Concurrent programming has started.");
				if( GlanetRunner.shouldLog())logger.info( "Concurrent programming has started.");
				// Concurrent Programming
				// First Generate Random Data for all permutations
				// Then Annotate Permutations (Random Data) concurrently
				// elementName2AllKMap and originalElementName2KMap will be filled here
				if( ( runNumber == numberofRuns) && ( numberofRemainedPermutations > 0)){
					
					Enrichment.annotateAllPermutationsInThreads(
							outputFolder, 
							dataFolder, 
							givenInputsSNPsorIntervals,
							numberofProcessors, 
							runNumber, 
							numberofRemainedPermutations,
							numberofPermutationsInEachRun, 
							originalInputLines, 
							dnase2AllKMap, 
							tfbs2AllKMap,
							histone2AllKMap, 
							gene2AllKMap, 
							exonBasedUserDefinedGeneSet2AllKMap,
							regulationBasedUserDefinedGeneSet2AllKMap, 
							allBasedUserDefinedGeneSet2AllKMap,
							elementTypeNumberElementNumber2AllKMap, 
							exonBasedKeggPathway2AllKMap,
							regulationBasedKeggPathway2AllKMap, 
							allBasedKeggPathway2AllKMap,
							tfExonBasedKeggPathway2AllKMap, 
							tfRegulationBasedKeggPathway2AllKMap,
							tfAllBasedKeggPathway2AllKMap, 
							tfCellLineExonBasedKeggPathway2AllKMap,
							tfCellLineRegulationBasedKeggPathway2AllKMap, 
							tfCellLineAllBasedKeggPathway2AllKMap,
							generateRandomDataMode, 
							writeGeneratedRandomDataMode,
							writePermutationBasedandParametricBasedAnnotationResultMode,
							writePermutationBasedAnnotationResultMode, 
							dnaseCellLineNumber2OriginalKMap,
							tfNumberCellLineNumber2OriginalKMap, 
							histoneNumberCellLineNumber2OriginalKMap,
							geneID2OriginalKMap, 
							exonBasedUserDefinedGeneSet2OriginalKMap,
							regulationBasedUserDefinedGeneSet2OriginalKMap, 
							allBasedUserDefinedGeneSet2OriginalKMap,
							elementTypeNumberElementNumber2OriginalKMap, 
							exonBasedKeggPathway2OriginalKMap,
							regulationBasedKeggPathway2OriginalKMap, 
							allBasedKeggPathway2OriginalKMap,
							tfExonBasedKeggPathway2OriginalKMap, 
							tfRegulationBasedKeggPathway2OriginalKMap,
							tfAllBasedKeggPathway2OriginalKMap, 
							tfCellLineExonBasedKeggPathway2OriginalKMap,
							tfCellLineRegulationBasedKeggPathway2OriginalKMap,
							tfCellLineAllBasedKeggPathway2OriginalKMap, 
							dnaseAnnotationType, 
							histoneAnnotationType,
							tfAnnotationType, 
							geneAnnotationType, 
							userDefinedGeneSetAnnotationType,
							userDefinedLibraryAnnotationType, 
							keggPathwayAnnotationType, 
							tfKeggPathwayAnnotationType,
							tfCellLineKeggPathwayAnnotationType, 
							bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType,
							overlapDefinition, 
							geneId2KeggPathwayNumberMap,
							geneId2ListofUserDefinedGeneSetNumberMap, 
							userDefinedLibraryElementTypeNumber2NameMap);
				}else{
					
					Enrichment.annotateAllPermutationsInThreads(
							outputFolder, 
							dataFolder, 
							givenInputsSNPsorIntervals,
							numberofProcessors, 
							runNumber, 
							numberofPermutationsInEachRun,
							numberofPermutationsInEachRun, 
							originalInputLines, 
							dnase2AllKMap, 
							tfbs2AllKMap,
							histone2AllKMap, 
							gene2AllKMap, 
							exonBasedUserDefinedGeneSet2AllKMap,
							regulationBasedUserDefinedGeneSet2AllKMap, 
							allBasedUserDefinedGeneSet2AllKMap,
							elementTypeNumberElementNumber2AllKMap, 
							exonBasedKeggPathway2AllKMap,
							regulationBasedKeggPathway2AllKMap, 
							allBasedKeggPathway2AllKMap,
							tfExonBasedKeggPathway2AllKMap, 
							tfRegulationBasedKeggPathway2AllKMap,
							tfAllBasedKeggPathway2AllKMap, 
							tfCellLineExonBasedKeggPathway2AllKMap,
							tfCellLineRegulationBasedKeggPathway2AllKMap, 
							tfCellLineAllBasedKeggPathway2AllKMap,
							generateRandomDataMode, 
							writeGeneratedRandomDataMode,
							writePermutationBasedandParametricBasedAnnotationResultMode,
							writePermutationBasedAnnotationResultMode, 
							dnaseCellLineNumber2OriginalKMap,
							tfNumberCellLineNumber2OriginalKMap, 
							histoneNumberCellLineNumber2OriginalKMap,
							geneID2OriginalKMap, 
							exonBasedUserDefinedGeneSet2OriginalKMap,
							regulationBasedUserDefinedGeneSet2OriginalKMap, 
							allBasedUserDefinedGeneSet2OriginalKMap,
							elementTypeNumberElementNumber2OriginalKMap, 
							exonBasedKeggPathway2OriginalKMap,
							regulationBasedKeggPathway2OriginalKMap, 
							allBasedKeggPathway2OriginalKMap,
							tfExonBasedKeggPathway2OriginalKMap, 
							tfRegulationBasedKeggPathway2OriginalKMap,
							tfAllBasedKeggPathway2OriginalKMap, 
							tfCellLineExonBasedKeggPathway2OriginalKMap,
							tfCellLineRegulationBasedKeggPathway2OriginalKMap,
							tfCellLineAllBasedKeggPathway2OriginalKMap, 
							dnaseAnnotationType, 
							histoneAnnotationType,
							tfAnnotationType, 
							geneAnnotationType, 
							userDefinedGeneSetAnnotationType,
							userDefinedLibraryAnnotationType, 
							keggPathwayAnnotationType, 
							tfKeggPathwayAnnotationType,
							tfCellLineKeggPathwayAnnotationType,
							bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType,
							overlapDefinition, geneId2KeggPathwayNumberMap,
							geneId2ListofUserDefinedGeneSetNumberMap, 
							userDefinedLibraryElementTypeNumber2NameMap);
				}
				GlanetRunner.appendLog( "Concurrent programming has ended.");
				if( GlanetRunner.shouldLog())logger.info( "Concurrent programming has ended.");
				/**********************************************************************************************/
				/************************** ANNOTATE PERMUTATIONS ENDS ****************************************/
				/**********************************************************************************************/

				/***********************************************************************************************/
				/************************** WRITE TO BE COLLECTED RESULTS STARTS *******************************/
				/***********************************************************************************************/
				// DNase
				if( dnaseAnnotationType.doDnaseAnnotation()){
					writeToBeCollectedNumberofOverlaps( outputFolder, dnaseCellLineNumber2OriginalKMap, dnase2AllKMap,
							Commons.TO_BE_COLLECTED_DNASE_NUMBER_OF_OVERLAPS, runName);
				}

				// Histone
				if( histoneAnnotationType.doHistoneAnnotation()){
					writeToBeCollectedNumberofOverlaps( outputFolder, histoneNumberCellLineNumber2OriginalKMap,
							histone2AllKMap, Commons.TO_BE_COLLECTED_HISTONE_NUMBER_OF_OVERLAPS, runName);
				}

				// TF
				if( tfAnnotationType.doTFAnnotation()){
					writeToBeCollectedNumberofOverlaps( outputFolder, tfNumberCellLineNumber2OriginalKMap,
							tfbs2AllKMap, Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, runName);
				}

				// Gene
				if( geneAnnotationType.doGeneAnnotation()){
					writeToBeCollectedNumberofOverlaps( outputFolder, geneID2OriginalKMap, gene2AllKMap,
							Commons.TO_BE_COLLECTED_GENE_NUMBER_OF_OVERLAPS, runName);
				}

				// UserDefinedGeneset
				if( userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){

					final String TO_BE_COLLECTED_EXON_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_USERDEFINED_GENESET_COMMON + userDefinedGeneSetName + System.getProperty( "file.separator") + Commons.ENRICHMENT_EXONBASED_USERDEFINED_GENESET + "_" + userDefinedGeneSetName;
					final String TO_BE_COLLECTED_REGULATION_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_USERDEFINED_GENESET_COMMON + userDefinedGeneSetName + System.getProperty( "file.separator") + Commons.ENRICHMENT_REGULATIONBASED_USERDEFINED_GENESET + "_" + userDefinedGeneSetName;
					final String TO_BE_COLLECTED_ALL_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_USERDEFINED_GENESET_COMMON + userDefinedGeneSetName + System.getProperty( "file.separator") + Commons.ENRICHMENT_ALLBASED_USERDEFINED_GENESET + "_" + userDefinedGeneSetName;

					writeToBeCollectedNumberofOverlaps( outputFolder, exonBasedUserDefinedGeneSet2OriginalKMap,
							exonBasedUserDefinedGeneSet2AllKMap,
							TO_BE_COLLECTED_EXON_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, regulationBasedUserDefinedGeneSet2OriginalKMap,
							regulationBasedUserDefinedGeneSet2AllKMap,
							TO_BE_COLLECTED_REGULATION_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, allBasedUserDefinedGeneSet2OriginalKMap,
							allBasedUserDefinedGeneSet2AllKMap,
							TO_BE_COLLECTED_ALL_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS, runName);

				}

				// UserDefinedLibrary
				if( userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){

					writeToBeCollectedNumberofOverlapsForUserDefinedLibrary(
							outputFolder,
							userDefinedLibraryElementTypeNumber2NameMap, 
							elementTypeNumberElementNumber2OriginalKMap,
							elementTypeNumberElementNumber2AllKMap, 
							runName);

				}

				// KEGGPathway
				if( keggPathwayAnnotationType.doKEGGPathwayAnnotation()){

					writeToBeCollectedNumberofOverlaps( outputFolder, exonBasedKeggPathway2OriginalKMap,
							exonBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, regulationBasedKeggPathway2OriginalKMap,
							regulationBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, allBasedKeggPathway2OriginalKMap,
							allBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				}

				// TFKEGGPathway
				if( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
						!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())){

					// TF
					writeToBeCollectedNumberofOverlaps( outputFolder, tfNumberCellLineNumber2OriginalKMap,
							tfbs2AllKMap, Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, runName);

					// KEGG
					writeToBeCollectedNumberofOverlaps( outputFolder, exonBasedKeggPathway2OriginalKMap,
							exonBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, regulationBasedKeggPathway2OriginalKMap,
							regulationBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, allBasedKeggPathway2OriginalKMap,
							allBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

					// TF KEGG
					writeToBeCollectedNumberofOverlaps( outputFolder, tfExonBasedKeggPathway2OriginalKMap,
							tfExonBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, tfRegulationBasedKeggPathway2OriginalKMap,
							tfRegulationBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, tfAllBasedKeggPathway2OriginalKMap,
							tfAllBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

				}

				if( !( tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
						tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){

					// TF
					writeToBeCollectedNumberofOverlaps( outputFolder, tfNumberCellLineNumber2OriginalKMap,
							tfbs2AllKMap, Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, runName);

					// KEGG
					writeToBeCollectedNumberofOverlaps( outputFolder, exonBasedKeggPathway2OriginalKMap,
							exonBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, regulationBasedKeggPathway2OriginalKMap,
							regulationBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, allBasedKeggPathway2OriginalKMap,
							allBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

					// TF CELLLINE KEGG
					writeToBeCollectedNumberofOverlaps( outputFolder, tfCellLineExonBasedKeggPathway2OriginalKMap,
							tfCellLineExonBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder,
							tfCellLineRegulationBasedKeggPathway2OriginalKMap,
							tfCellLineRegulationBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
							runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, tfCellLineAllBasedKeggPathway2OriginalKMap,
							tfCellLineAllBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

				}

				if( bothTFKEGGAndTFCellLineKEGGPathwayAnnotationType.doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation()){

					// TF
					writeToBeCollectedNumberofOverlaps( outputFolder, tfNumberCellLineNumber2OriginalKMap,
							tfbs2AllKMap, Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, runName);

					// KEGG
					writeToBeCollectedNumberofOverlaps( outputFolder, exonBasedKeggPathway2OriginalKMap,
							exonBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, regulationBasedKeggPathway2OriginalKMap,
							regulationBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, allBasedKeggPathway2OriginalKMap,
							allBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

					// TF KEGG
					writeToBeCollectedNumberofOverlaps( outputFolder, tfExonBasedKeggPathway2OriginalKMap,
							tfExonBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, tfRegulationBasedKeggPathway2OriginalKMap,
							tfRegulationBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, tfAllBasedKeggPathway2OriginalKMap,
							tfAllBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

					// TF CELLLINE KEGG
					writeToBeCollectedNumberofOverlaps( outputFolder, tfCellLineExonBasedKeggPathway2OriginalKMap,
							tfCellLineExonBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
					writeToBeCollectedNumberofOverlaps( outputFolder,
							tfCellLineRegulationBasedKeggPathway2OriginalKMap,
							tfCellLineRegulationBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,
							runName);
					writeToBeCollectedNumberofOverlaps( outputFolder, tfCellLineAllBasedKeggPathway2OriginalKMap,
							tfCellLineAllBasedKeggPathway2AllKMap,
							Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

				}
				// Calculate Empirical P Values and Bonferroni Corrected Empirical P
				// Values ends
				/***********************************************************************************************/
				/************************** WRITE TO BE COLLECTED RESULTS ENDS *********************************/
				/***********************************************************************************************/

				/***********************************************************************************************/
				/********************************** FREE MEMORY STARTS *****************************************/
				/********************* MAPS FOR PERMUTATIONS DATA STARTS *************************************/
				// functionalElementName based number of overlaps: k out of n for all permutations

				// DNASE HISTONE TF
				dnase2AllKMap = null;
				histone2AllKMap = null;
				tfbs2AllKMap = null;

				// Gene
				gene2AllKMap = null;

				// USERDEFINED GENESET
				exonBasedUserDefinedGeneSet2AllKMap = null;
				regulationBasedUserDefinedGeneSet2AllKMap = null;
				allBasedUserDefinedGeneSet2AllKMap = null;

				// USERDEFINED LIBRARY
				elementTypeNumberElementNumber2AllKMap = null;

				// KEGG PATHWAY
				exonBasedKeggPathway2AllKMap = null;
				regulationBasedKeggPathway2AllKMap = null;
				allBasedKeggPathway2AllKMap = null;

				// TF KEGGPathway
				tfExonBasedKeggPathway2AllKMap = null;
				tfRegulationBasedKeggPathway2AllKMap = null;
				tfAllBasedKeggPathway2AllKMap = null;

				// TF CellLine KEGGPathway
				tfCellLineExonBasedKeggPathway2AllKMap = null;
				tfCellLineRegulationBasedKeggPathway2AllKMap = null;
				tfCellLineAllBasedKeggPathway2AllKMap = null;

				System.gc();
				System.runFinalization();
				/***********************************************************************************************/
				/********************* MAPS FOR PERMUTATIONS DATA ENDS *****************************************/
				/*********************************** FREE MEMORY ENDS ******************************************/
				/***********************************************************************************************/

				GlanetRunner.appendLog( "**************	" + runNumber + ". Run" + "	******************	ends");
				if( GlanetRunner.shouldLog())logger.info( "**************	" + runNumber + ". Run" + "	******************	ends");

			}// End of FOR each run number
			/*********************************************************************************************/
			/********************* FOR LOOP FOR RUN NUMBERS ENDS *****************************************/
			/*********************************************************************************************/

		}
		// Perform Enrichment WITH Keeping Number of Overlaps Coming from Each Permutation ends

		/***********************************************************************************************/
		/********************************** FREE MEMORY STARTS *****************************************/
		/********************* MAPS FOR ORIGINAL DATA STARTS *******************************************/
		/***********************************************************************************************/
		// DNASE TF HISTONE
		dnaseCellLineNumber2OriginalKMap = null;
		tfNumberCellLineNumber2OriginalKMap = null;
		histoneNumberCellLineNumber2OriginalKMap = null;

		// Gene
		geneID2OriginalKMap = null;

		// USERDEFINEDGENESET
		exonBasedUserDefinedGeneSet2OriginalKMap = null;
		regulationBasedUserDefinedGeneSet2OriginalKMap = null;
		allBasedUserDefinedGeneSet2OriginalKMap = null;

		// USERDEFINEDLIBRARY
		elementTypeNumberElementNumber2OriginalKMap = null;

		// KEGG PATHWAY
		exonBasedKeggPathway2OriginalKMap = null;
		regulationBasedKeggPathway2OriginalKMap = null;
		allBasedKeggPathway2OriginalKMap = null;

		// TF KEGGPATHWAY
		tfExonBasedKeggPathway2OriginalKMap = null;
		tfRegulationBasedKeggPathway2OriginalKMap = null;
		tfAllBasedKeggPathway2OriginalKMap = null;

		// TF CELLLINE KEGGPATHWAY
		tfCellLineExonBasedKeggPathway2OriginalKMap = null;
		tfCellLineRegulationBasedKeggPathway2OriginalKMap = null;
		tfCellLineAllBasedKeggPathway2OriginalKMap = null;

		System.gc();
		System.runFinalization();
		/***********************************************************************************************/
		/********************* MAPS FOR ORIGINAL DATA ENDS *********************************************/
		/*********************************** FREE MEMORY ENDS ******************************************/
		/***********************************************************************************************/

		/***********************************************************************************/
		/**********************FREE AUXILIARY MAPS starts***********************************/
		/***********************************************************************************/
		userDefinedGeneSetNumber2UserDefinedGeneSetNameMap = null;
		userDefinedGeneSetName2UserDefinedGeneSetNumberMap = null;
		geneId2ListofUserDefinedGeneSetNumberMap = null;

		geneId2KeggPathwayNumberMap = null;
		keggPathwayName2KeggPathwayNumberMap = null;

		userDefinedLibraryElementTypeNumber2NameMap = null;
		/***********************************************************************************/
		/**********************FREE AUXILIARY MAPS ends*************************************/
		/***********************************************************************************/

		/***********************************************************************************/
		/**************Memory Usage After Enrichment****************************************/
		/***********************************************************************************/
		// if( GlanetRunner.shouldLog())logger.info("Memory Used After Enrichment" + "\t" +
		// ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE)
		// + "\t" + "MBs");
		/***********************************************************************************/
		/**************Memory Usage After Enrichment****************************************/
		/***********************************************************************************/

	}// End of main function

}
