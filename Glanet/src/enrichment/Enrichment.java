/**
 * @author burcakotlu
 * @date May 9, 2014 
 * @time 10:45:02 AM
 */
package enrichment;

import enumtypes.AnnotationType;
import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import enumtypes.GeneInformationType;
import enumtypes.GenerateRandomDataMode;
import enumtypes.GeneratedMixedNumberDescriptionOrderLength;
import enumtypes.WriteGeneratedRandomDataMode;
import enumtypes.WritePermutationBasedAnnotationResultMode;
import enumtypes.WritePermutationBasedandParametricBasedAnnotationResultMode;
import generate.randomdata.RandomDataGenerator;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TLongIntIterator;
import gnu.trove.list.TByteList;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TShortArrayList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.map.hash.TObjectShortHashMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import hg19.GRCh37Hg19Chromosome;
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
import mapabilityandgc.ChromosomeBasedGCTroveList;
import mapabilityandgc.ChromosomeBasedMappabilityTroveList;

import org.apache.log4j.Logger;

import ui.GlanetRunner;
import userdefined.geneset.UserDefinedGeneSetUtility;
import userdefined.library.UserDefinedLibraryUtility;
import annotation.Annotation;
import auxiliary.FileOperations;
import auxiliary.FunctionalElement;

import common.Commons;

/**
 * Annotate Permutations With Numbers With Choices
 */
public class Enrichment {

	final static Logger logger = Logger.getLogger(Enrichment.class);

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

		//private final GCCharArray gcCharArray; 
		private final TByteList gcByteList;
		
		
		//private final MapabilityFloatArray mapabilityFloatArray;
		private final TIntList mapabilityChromosomePositionList;
		private final TShortList mapabilityShortValueList;
		//private final TByteList mapabilityByteValueList;
		
		private final String outputFolder;

		public GenerateRandomData(
				String outputFolder, 
				int chromSize, 
				ChromosomeName chromName, 
				List<InputLineMinimal> chromosomeBasedOriginalInputLines, 
				GenerateRandomDataMode generateRandomDataMode, 
				WriteGeneratedRandomDataMode writeGeneratedRandomDataMode, 
				int lowIndex, 
				int highIndex, 
				TIntList permutationNumberList, 
				TByteList gcByteList, 
				TIntList mapabilityChromosomePositionList,
				TShortList mapabilityShortValueList
				//TByteList mapabilityByteValueList
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

			this.gcByteList = gcByteList;
			
			this.mapabilityChromosomePositionList = mapabilityChromosomePositionList;
			this.mapabilityShortValueList = mapabilityShortValueList;
			//this.mapabilityByteValueList =mapabilityByteValueList;
		}

		protected TIntObjectMap<List<InputLineMinimal>> compute() {

			int middleIndex;
			TIntObjectMap<List<InputLineMinimal>> rightRandomlyGeneratedData;
			TIntObjectMap<List<InputLineMinimal>> leftRandomlyGeneratedData;

			Integer permutationNumber;
			
			// DIVIDE
			if (highIndex - lowIndex > Commons.NUMBER_OF_GENERATE_RANDOM_DATA_TASK_DONE_IN_SEQUENTIALLY) {
				middleIndex = lowIndex + (highIndex - lowIndex) / 2;
				GenerateRandomData left = new GenerateRandomData(outputFolder, chromSize, chromName, chromosomeBasedOriginalInputLines, generateRandomDataMode, writeGeneratedRandomDataMode, lowIndex, middleIndex, permutationNumberList,gcByteList, mapabilityChromosomePositionList,mapabilityShortValueList);
				GenerateRandomData right = new GenerateRandomData(outputFolder, chromSize, chromName, chromosomeBasedOriginalInputLines, generateRandomDataMode, writeGeneratedRandomDataMode, middleIndex, highIndex, permutationNumberList,gcByteList, mapabilityChromosomePositionList,mapabilityShortValueList);
				left.fork();
				rightRandomlyGeneratedData = right.compute();
				leftRandomlyGeneratedData = left.join();

				// Add the contents of leftRandomlyGeneratedData into
				// rightRandomlyGeneratedData
				mergeMaps(rightRandomlyGeneratedData, leftRandomlyGeneratedData);

				leftRandomlyGeneratedData = null;
				return rightRandomlyGeneratedData;
			}
			// CONQUER
			else {

				TIntObjectMap<List<InputLineMinimal>> randomlyGeneratedDataMap = new TIntObjectHashMap<List<InputLineMinimal>>();

				for (int i = lowIndex; i < highIndex; i++) {
					permutationNumber = permutationNumberList.get(i);

					randomlyGeneratedDataMap.put(permutationNumber, RandomDataGenerator.generateRandomData(gcByteList,mapabilityChromosomePositionList,mapabilityShortValueList,chromSize,chromName, chromosomeBasedOriginalInputLines, ThreadLocalRandom.current(), generateRandomDataMode));

					// Write Generated Random Data
					if (writeGeneratedRandomDataMode.isWriteGeneratedRandomDataMode()) {
						writeGeneratedRandomData(outputFolder, chromName, randomlyGeneratedDataMap.get(permutationNumber), permutationNumber);
					}

				}// End of FOR

				return randomlyGeneratedDataMap;
			}

		}// End of compute method

		// Add the content of leftMap to rightMap
		// Clear and null leftMap
		protected void mergeMaps(TIntObjectMap<List<InputLineMinimal>> rightRandomlyGeneratedDataMap, TIntObjectMap<List<InputLineMinimal>> leftRandomlyGeneratedDataMap) {
			
			int permutationNumber;
			
			for(TIntObjectIterator<List<InputLineMinimal>> it= leftRandomlyGeneratedDataMap.iterator();it.hasNext();){
				
				it.advance();
				
				permutationNumber = it.key();
				
				if (!rightRandomlyGeneratedDataMap.containsKey(permutationNumber)) {
					rightRandomlyGeneratedDataMap.put(permutationNumber,it.value());
				}
			}

			leftRandomlyGeneratedDataMap.clear();
			leftRandomlyGeneratedDataMap = null;

		}

		protected void writeGeneratedRandomData(String outputFolder, ChromosomeName chrName, List<InputLineMinimal> randomlyGeneratedData, int permutationNumber) {
			FileWriter fileWriter;
			BufferedWriter bufferedWriter;
			InputLineMinimal randomlyGeneratedInputLine;

			try {

				fileWriter = FileOperations.createFileWriter(outputFolder + Commons.RANDOMLY_GENERATED_DATA_FOLDER + Commons.PERMUTATION + permutationNumber + "_" + Commons.RANDOMLY_GENERATED_DATA + ".txt", true);
				bufferedWriter = new BufferedWriter(fileWriter);

				for (int i = 0; i < randomlyGeneratedData.size(); i++) {
					randomlyGeneratedInputLine = randomlyGeneratedData.get(i);
					bufferedWriter.write(ChromosomeName.convertEnumtoString(chrName) + "\t" + randomlyGeneratedInputLine.getLow() + "\t" + randomlyGeneratedInputLine.getHigh() + System.getProperty("line.separator"));
					bufferedWriter.flush();
				}// End of FOR

				bufferedWriter.close();

			} catch (IOException e) {
				logger.error(e.toString());
			}

		}
	}// End of GenerateRandomData Class
	
	
	//Testing Purposes starts
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
				TIntList permutationNumberList, 
				IntervalTree intervalTree, 
				AnnotationType annotationType, 
				int lowIndex, 
				int highIndex, 
				String outputFolder, 
				int overlapDefinition) {
			
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
			if (highIndex - lowIndex > Commons.NUMBER_OF_ANNOTATE_RANDOM_DATA_TASK_DONE_IN_SEQUENTIALLY) {
				middleIndex = lowIndex + (highIndex - lowIndex) / 2;
				AnnotateDnaseTFHistoneWithNumbers left = new AnnotateDnaseTFHistoneWithNumbers(chromName, randomlyGeneratedDataMap, runNumber, numberofPermutations, writePermutationBasedandParametricBasedAnnotationResultMode,permutationNumberList, intervalTree,annotationType,lowIndex, middleIndex,outputFolder, overlapDefinition);
				AnnotateDnaseTFHistoneWithNumbers right = new AnnotateDnaseTFHistoneWithNumbers(chromName, randomlyGeneratedDataMap, runNumber, numberofPermutations, writePermutationBasedandParametricBasedAnnotationResultMode,permutationNumberList, intervalTree,annotationType, middleIndex, highIndex,outputFolder, overlapDefinition);
				left.fork();
				rightAllMapsWithNumbers = right.compute();
				leftAllMapsWithNumbers = left.join();
				combineLeftAllMapsandRightAllMaps(leftAllMapsWithNumbers, rightAllMapsWithNumbers);
				leftAllMapsWithNumbers = null;
				return rightAllMapsWithNumbers;
			}
			// CONQUER
			else {

				listofAllMapsWithNumbers = new ArrayList<AllMapsDnaseTFHistoneWithNumbers>();
				allMapsWithNumbers = new AllMapsDnaseTFHistoneWithNumbers();

				for (int i = lowIndex; i < highIndex; i++) {
					permutationNumber = permutationNumberList.get(i);
					
				
					// WITHOUT IO WithNumbers
					if (writePermutationBasedandParametricBasedAnnotationResultMode.isDoNotWritePermutationBasedandParametricBasedAnnotationResultMode()) {
						listofAllMapsWithNumbers.add(Annotation.annotatePermutationWithoutIOWithNumbers_DnaseTFHistone(permutationNumber, chromName, randomlyGeneratedDataMap.get(permutationNumber), intervalTree, annotationType, overlapDefinition));
					}

					// WITH IO WithNumbers
					else if (writePermutationBasedandParametricBasedAnnotationResultMode.isWritePermutationBasedandParametricBasedAnnotationResultMode()) {
						listofAllMapsWithNumbers.add(Annotation.annotatePermutationWithIOWithNumbers_DnaseTFHistone(outputFolder, permutationNumber, chromName, randomlyGeneratedDataMap.get(permutationNumber), intervalTree, annotationType, overlapDefinition));
					}
				}// End of FOR

				combineListofAllMapsWithNumbers(listofAllMapsWithNumbers, allMapsWithNumbers);

				listofAllMapsWithNumbers = null;
				return allMapsWithNumbers;

			}
		}
		
		// Combine leftAllMapsWithNumbers and rightAllMapsWithNumbers in rightAllMapsWithNumbers
		protected void combineLeftAllMapsandRightAllMaps(AllMapsDnaseTFHistoneWithNumbers leftAllMapsWithNumbers, AllMapsDnaseTFHistoneWithNumbers rightAllMapsWithNumbers) {

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
			if (leftPermutationNumberDnaseCellLineNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberDnaseCellLineNumber2KMap, rightPermutationNumberDnaseCellLineNumber2KMap);
				leftPermutationNumberDnaseCellLineNumber2KMap = null;
			}

			// TF
			if (leftPermutationNumberTfNumberCellLineNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberTfNumberCellLineNumber2KMap, rightPermutationNumberTfNumberCellLineNumber2KMap);
				leftPermutationNumberTfNumberCellLineNumber2KMap = null;
			}

			// HISTONE
			if (leftPermutationNumberHistoneNumberCellLineNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberHistoneNumberCellLineNumber2KMap, rightPermutationNumberHistoneNumberCellLineNumber2KMap);
				leftPermutationNumberHistoneNumberCellLineNumber2KMap = null;
			}
		
		}
		
		// TIntIntMap version starts
		// Accumulate leftMapWithNumbers in the rightMapWithNumbers
		// Accumulate number of overlaps
		// based on permutationNumber and ElementName
		protected void combineLeftMapandRightMap(TIntIntMap leftMapWithNumbers, TIntIntMap rightMapWithNumbers) {

			for (TIntIntIterator it = leftMapWithNumbers.iterator(); it.hasNext();) {

				it.advance();

				int permutationNumberCellLineNumberOrKeggPathwayNumber = it.key();
				int numberofOverlaps = it.value();

				// For debug purposes starts
				// System.out.println("permutationNumberCellLineNumberOrKeggPathwayNumber: "
				// + permutationNumberCellLineNumberOrKeggPathwayNumber);
				// For debug purposes ends

				if (!(rightMapWithNumbers.containsKey(permutationNumberCellLineNumberOrKeggPathwayNumber))) {
					rightMapWithNumbers.put(permutationNumberCellLineNumberOrKeggPathwayNumber, numberofOverlaps);
				} else {
					rightMapWithNumbers.put(permutationNumberCellLineNumberOrKeggPathwayNumber, rightMapWithNumbers.get(permutationNumberCellLineNumberOrKeggPathwayNumber) + numberofOverlaps);
				}
			}

			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;

		}

		// TIntIntMap version ends

		// Accumulate leftMapWithNumbers in the rightMapWithNumbers
		// Accumulate number of overlaps
		// based on permutationNumber and ElementName
		protected void combineLeftMapandRightMap(TLongIntMap leftMapWithNumbers, TLongIntMap rightMapWithNumbers) {

			for (TLongIntIterator it = leftMapWithNumbers.iterator(); it.hasNext();) {

				it.advance();

				long permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
				int numberofOverlaps = it.value();

				if (!(rightMapWithNumbers.containsKey(permutationNumberElementNumberCellLineNumberKeggPathwayNumber))) {
					rightMapWithNumbers.put(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, numberofOverlaps);
				} else {
					rightMapWithNumbers.put(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, rightMapWithNumbers.get(permutationNumberElementNumberCellLineNumberKeggPathwayNumber) + numberofOverlaps);
				}
			}

			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;

		}
		
		// Accumulate the allMaps in the left into listofAllMaps in the right
		protected void combineListofAllMapsWithNumbers(List<AllMapsDnaseTFHistoneWithNumbers> listofAllMaps, AllMapsDnaseTFHistoneWithNumbers allMaps) {
			for (int i = 0; i < listofAllMaps.size(); i++) {
				combineLeftAllMapsandRightAllMaps(listofAllMaps.get(i), allMaps);
			}
		}
	}

	//Testing Purposes ends
	

	static class AnnotateWithNumbers extends RecursiveTask<AllMapsWithNumbers> {

		private static final long serialVersionUID = 2919115895116169524L;
		private final ChromosomeName chromName;
		private final TIntObjectMap<List<InputLineMinimal>> randomlyGeneratedDataMap;
		private final int runNumber;
		private final int numberofPermutations;

		private final WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode;

		private final TIntList permutationNumberList;
		private final IntervalTree intervalTree;
		private final IntervalTree ucscRefSeqGenesIntervalTree;

		private final AnnotationType annotationType;

		private final int lowIndex;
		private final int highIndex;

		private final TIntObjectMap<TShortList> geneId2ListofGeneSetNumberMap;

		private final String outputFolder;

		private final int overlapDefinition;

		public AnnotateWithNumbers(
				String outputFolder, 
				ChromosomeName chromName, 
				TIntObjectMap<List<InputLineMinimal>> randomlyGeneratedDataMap, 
				int runNumber, 
				int numberofPermutations, 
				WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode, 
				int lowIndex, 
				int highIndex, 
				TIntList permutationNumberList, 
				IntervalTree intervalTree, 
				IntervalTree ucscRefSeqGenesIntervalTree, 
				AnnotationType annotationType, 
				TIntObjectMap<TShortList> geneId2ListofGeneSetNumberMap, 
				int overlapDefinition) {

			this.outputFolder = outputFolder;

			this.chromName = chromName;
			this.randomlyGeneratedDataMap = randomlyGeneratedDataMap;
			this.runNumber = runNumber;
			this.numberofPermutations = numberofPermutations;

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
		}

		protected AllMapsWithNumbers compute() {

			int middleIndex;
			AllMapsWithNumbers rightAllMapsWithNumbers;
			AllMapsWithNumbers leftAllMapsWithNumbers;

			Integer permutationNumber;
			List<AllMapsWithNumbers> listofAllMapsWithNumbers;
			AllMapsWithNumbers allMapsWithNumbers;

			// DIVIDE
			if (highIndex - lowIndex > Commons.NUMBER_OF_ANNOTATE_RANDOM_DATA_TASK_DONE_IN_SEQUENTIALLY) {
				middleIndex = lowIndex + (highIndex - lowIndex) / 2;
				AnnotateWithNumbers left = new AnnotateWithNumbers(outputFolder, chromName, randomlyGeneratedDataMap, runNumber, numberofPermutations, writePermutationBasedandParametricBasedAnnotationResultMode, lowIndex, middleIndex, permutationNumberList, intervalTree, ucscRefSeqGenesIntervalTree, annotationType, geneId2ListofGeneSetNumberMap, overlapDefinition);
				AnnotateWithNumbers right = new AnnotateWithNumbers(outputFolder, chromName, randomlyGeneratedDataMap, runNumber, numberofPermutations, writePermutationBasedandParametricBasedAnnotationResultMode, middleIndex, highIndex, permutationNumberList, intervalTree, ucscRefSeqGenesIntervalTree, annotationType, geneId2ListofGeneSetNumberMap, overlapDefinition);
				left.fork();
				rightAllMapsWithNumbers = right.compute();
				leftAllMapsWithNumbers = left.join();
				combineLeftAllMapsandRightAllMaps(leftAllMapsWithNumbers, rightAllMapsWithNumbers);
				leftAllMapsWithNumbers = null;
				return rightAllMapsWithNumbers;
			}
			// CONQUER
			else {

				listofAllMapsWithNumbers = new ArrayList<AllMapsWithNumbers>();
				allMapsWithNumbers = new AllMapsWithNumbers();

				for (int i = lowIndex; i < highIndex; i++) {
					permutationNumber = permutationNumberList.get(i);
					

					// WITHOUT IO WithNumbers
					if (writePermutationBasedandParametricBasedAnnotationResultMode.isDoNotWritePermutationBasedandParametricBasedAnnotationResultMode()) {
						listofAllMapsWithNumbers.add(Annotation.annotatePermutationWithoutIOWithNumbers(permutationNumber, chromName, randomlyGeneratedDataMap.get(permutationNumber), intervalTree, ucscRefSeqGenesIntervalTree, annotationType, geneId2ListofGeneSetNumberMap, overlapDefinition));
					}

					// WITH IO WithNumbers
					else if (writePermutationBasedandParametricBasedAnnotationResultMode.isWritePermutationBasedandParametricBasedAnnotationResultMode()) {
						listofAllMapsWithNumbers.add(Annotation.annotatePermutationWithIOWithNumbers(outputFolder, permutationNumber, chromName, randomlyGeneratedDataMap.get(permutationNumber), intervalTree, ucscRefSeqGenesIntervalTree, annotationType, geneId2ListofGeneSetNumberMap, overlapDefinition));
					}
				}// End of FOR

				combineListofAllMapsWithNumbers(listofAllMapsWithNumbers, allMapsWithNumbers);

				listofAllMapsWithNumbers = null;
				return allMapsWithNumbers;

			}
		}

		// Accumulate the allMaps in the left into listofAllMaps in the right
		protected void combineListofAllMapsWithNumbers(List<AllMapsWithNumbers> listofAllMaps, AllMapsWithNumbers allMaps) {
			for (int i = 0; i < listofAllMaps.size(); i++) {
				combineLeftAllMapsandRightAllMaps(listofAllMaps.get(i), allMaps);
			}
		}

		


		
		// Combine leftAllMapsWithNumbers and rightAllMapsWithNumbers in rightAllMapsWithNumbers
		protected void combineLeftAllMapsandRightAllMaps(AllMapsWithNumbers leftAllMapsWithNumbers, AllMapsWithNumbers rightAllMapsWithNumbers) {

			// LEFT ALL MAPS WITH NUMBERS
			// DNASE
			TIntIntMap leftPermutationNumberDnaseCellLineNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap();

			// TF
			TLongIntMap leftPermutationNumberTfNumberCellLineNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap();

			// HISTONE
			TLongIntMap leftPermutationNumberHistoneNumberCellLineNumber2KMap = leftAllMapsWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap();
			
			//Gene
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
			if (leftPermutationNumberDnaseCellLineNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberDnaseCellLineNumber2KMap, rightPermutationNumberDnaseCellLineNumber2KMap);
				leftPermutationNumberDnaseCellLineNumber2KMap = null;
			}

			// TF
			if (leftPermutationNumberTfNumberCellLineNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberTfNumberCellLineNumber2KMap, rightPermutationNumberTfNumberCellLineNumber2KMap);
				leftPermutationNumberTfNumberCellLineNumber2KMap = null;
			}

			// HISTONE
			if (leftPermutationNumberHistoneNumberCellLineNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberHistoneNumberCellLineNumber2KMap, rightPermutationNumberHistoneNumberCellLineNumber2KMap);
				leftPermutationNumberHistoneNumberCellLineNumber2KMap = null;
			}
			
			// GENE
			if (leftPermutationNumberGeneNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberGeneNumber2KMap, rightPermutationNumberGeneNumber2KMap);
				leftPermutationNumberGeneNumber2KMap = null;
			}
			
			
			// USERDEFINED GENESET starts
			// EXON BASED USERDEFINED GENESET
			if (leftPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap, rightPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap);
				leftPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap = null;
			}

			// REGULATION BASED USERDEFINED GENESET
			if (leftPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap, rightPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap);
				leftPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap = null;
			}

			// ALL BASED USERDEFINED GENESET
			if (leftPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap, rightPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap);
				leftPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap = null;
			}
			// USERDEFINED GENESET ends

			// USERDEFINED LIBRARY starts
			if (leftPermutationNumberElementTypeNumberElementNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberElementTypeNumberElementNumber2KMap, rightPermutationNumberElementTypeNumberElementNumber2KMap);
				leftPermutationNumberElementTypeNumberElementNumber2KMap = null;
			}
			// USERDEFINED LIBRARY ends

			// EXON BASED KEGG PATHWAY
			if (leftPermutationNumberExonBasedKeggPathwayNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberExonBasedKeggPathwayNumber2KMap, rightPermutationNumberExonBasedKeggPathwayNumber2KMap);
				leftPermutationNumberExonBasedKeggPathwayNumber2KMap = null;
			}

			// REGULATION BASED KEGG PATHWAY
			if (leftPermutationNumberRegulationBasedKeggPathwayNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberRegulationBasedKeggPathwayNumber2KMap, rightPermutationNumberRegulationBasedKeggPathwayNumber2KMap);
				leftPermutationNumberRegulationBasedKeggPathwayNumber2KMap = null;
			}

			// ALL BASED KEGG PATHWAY
			if (leftPermutationNumberAllBasedKeggPathwayNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberAllBasedKeggPathwayNumber2KMap, rightPermutationNumberAllBasedKeggPathwayNumber2KMap);
				leftPermutationNumberAllBasedKeggPathwayNumber2KMap = null;
			}

			// TF and EXON BASED KEGG PATHWAY
			if (leftPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap, rightPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap);
				leftPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap = null;
			}

			// TF and REGULATION BASED KEGG PATHWAY
			if (leftPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap, rightPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap);
				leftPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap = null;
			}

			// TF and ALL BASED KEGG PATHWAY
			if (leftPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap, rightPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap);
				leftPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap = null;
			}

			// TF and CellLine and EXON BASED KEGG PATHWAY
			if (leftPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap, rightPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap);
				leftPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap = null;
			}

			// TF and CellLine and REGULATION BASED KEGG PATHWAY
			if (leftPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap, rightPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap);
				leftPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap = null;
			}

			// TF and CellLine and ALL BASED KEGG PATHWAY
			if (leftPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap != null) {
				combineLeftMapandRightMap(leftPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap, rightPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap);
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
		protected void combineLeftMapandRightMap(TIntIntMap leftMapWithNumbers, TIntIntMap rightMapWithNumbers) {

			for (TIntIntIterator it = leftMapWithNumbers.iterator(); it.hasNext();) {

				it.advance();

				int permutationNumberCellLineNumberOrKeggPathwayNumber = it.key();
				int numberofOverlaps = it.value();

				// For debug purposes starts
				// System.out.println("permutationNumberCellLineNumberOrKeggPathwayNumber: "
				// + permutationNumberCellLineNumberOrKeggPathwayNumber);
				// For debug purposes ends

				if (!(rightMapWithNumbers.containsKey(permutationNumberCellLineNumberOrKeggPathwayNumber))) {
					rightMapWithNumbers.put(permutationNumberCellLineNumberOrKeggPathwayNumber, numberofOverlaps);
				} else {
					rightMapWithNumbers.put(permutationNumberCellLineNumberOrKeggPathwayNumber, rightMapWithNumbers.get(permutationNumberCellLineNumberOrKeggPathwayNumber) + numberofOverlaps);
				}
			}

			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;

		}

		// TIntIntMap version ends

		// Accumulate leftMapWithNumbers in the rightMapWithNumbers
		// Accumulate number of overlaps
		// based on permutationNumber and ElementName
		protected void combineLeftMapandRightMap(TLongIntMap leftMapWithNumbers, TLongIntMap rightMapWithNumbers) {

			for (TLongIntIterator it = leftMapWithNumbers.iterator(); it.hasNext();) {

				it.advance();

				long permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
				int numberofOverlaps = it.value();

				if (!(rightMapWithNumbers.containsKey(permutationNumberElementNumberCellLineNumberKeggPathwayNumber))) {
					rightMapWithNumbers.put(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, numberofOverlaps);
				} else {
					rightMapWithNumbers.put(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, rightMapWithNumbers.get(permutationNumberElementNumberCellLineNumberKeggPathwayNumber) + numberofOverlaps);
				}
			}

			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;

		}

		protected void deleteRandomlyGeneratedData(List<InputLine> randomlyGeneratedData) {
			for (InputLine inputLine : randomlyGeneratedData) {
				inputLine.setChrName(null);
				inputLine = null;
			}

			randomlyGeneratedData.clear();
		}

		protected void deleteMap(Map<String, Integer> map) {
			if (map != null) {
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					entry.setValue(null);
					entry = null;
				}
				map = null;
			}

		}

		protected void deleteAllMaps(AllMaps allMaps) {
			Map<String, Integer> map = allMaps.getPermutationNumberDnaseCellLineName2KMap();
			deleteMap(map);
			map = allMaps.getPermutationNumberTfNameCellLineName2KMap();
			deleteMap(map);
			map = allMaps.getPermutationNumberHistoneNameCellLineName2KMap();
			deleteMap(map);
			map = allMaps.getPermutationNumberExonBasedKeggPathway2KMap();
			deleteMap(map);
			map = allMaps.getPermutationNumberRegulationBasedKeggPathway2KMap();
			deleteMap(map);
			allMaps = null;
		}

	}

	public static void readOriginalInputDataLines(List<InputLine> originalInputLines, String inputFileName) {
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;

		int indexofFirstTab;
		int indexofSecondTab;

		ChromosomeName chrName;
		int low;
		int high;

		GlanetRunner.appendLog("Input data file name is: " + inputFileName);
		logger.info("Input data file name is: " + inputFileName);

		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab + 1);

				chrName = ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab));

				if (indexofSecondTab > 0) {
					low = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));
					high = Integer.parseInt(strLine.substring(indexofSecondTab + 1));
				} else {
					low = Integer.parseInt(strLine.substring(indexofFirstTab + 1));
					high = low;
				}

				InputLine originalInputLine = new InputLine(chrName, low, high);
				originalInputLines.add(originalInputLine);

			}
		} catch (FileNotFoundException e) {
			logger.error(e.toString());
		} catch (IOException e) {
			logger.error(e.toString());
		}

	}

	public static void partitionDataChromosomeBased(List<InputLine> originalInputLines, Map<ChromosomeName, List<InputLineMinimal>> chromosomeName2OriginalInputLinesMap) {
		
		InputLineMinimal inputLineMinimal = null;
		ChromosomeName chrName;
		List<InputLineMinimal> list;

		for (int i = 0; i < originalInputLines.size(); i++) {

			
			inputLineMinimal = new InputLineMinimal( originalInputLines.get(i).getLow(),  originalInputLines.get(i).getHigh());
			chrName = originalInputLines.get(i).getChrName();
			list = chromosomeName2OriginalInputLinesMap.get(chrName);

			if (list == null) {
				list = new ArrayList<InputLineMinimal>();
				list.add(inputLineMinimal);
				chromosomeName2OriginalInputLinesMap.put(chrName, list);
			} else {
				list.add(inputLineMinimal);
				chromosomeName2OriginalInputLinesMap.put(chrName, list);
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

		for (TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();) {

			it.advance();

			// example permutationAugmentedName PERMUTATION0_K562
			// get permutationNumber from permutationAugmentedName
			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();

			permutationNumber = IntervalTree.getPermutationNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);

			permutationNumberRemovedMixedNumber = IntervalTree.getPermutationNumberRemovedMixedNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
			

			// example permutationNumber PERMUTATION0

			// @todo check this zero value for permutation of original data
			if (Commons.ZERO.equals(permutationNumber)) {
				elementNumber2OriginalKMap.put(permutationNumberRemovedMixedNumber, numberofOverlaps);
			} else {
				list = elementNumber2AllKMap.get(permutationNumberRemovedMixedNumber);

				if (list == null) {
					list = new TIntArrayList();
					list.add(numberofOverlaps);
					elementNumber2AllKMap.put(permutationNumberRemovedMixedNumber, list);
				} else {
					list.add(numberofOverlaps);
					elementNumber2AllKMap.put(permutationNumberRemovedMixedNumber, list);
				}
			}
		}// End of for

	}
	// TLongIntMap TIntObjectMap<TIntList> TIntIntMap ends

	// TIntIntMap TIntObjectMap<TIntList> TIntIntMap version starts
	public static void convert(TIntIntMap permutationNumberCellLineNumberOrGeneSetNumber2KMap, TIntObjectMap<TIntList> cellLineNumberorGeneSetNumber2AllKMap, TIntIntMap cellLineNumberorGeneSetNumber2OriginalKMap, GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int permutationNumberCellLineNumberOrGeneSetNumber;
		Integer permutationNumber = Commons.MINUS_ONE;
		Integer cellLineNumberOrGeneSetNumber;

		Integer numberofOverlaps;

		TIntList list;

		for (TIntIntIterator it = permutationNumberCellLineNumberOrGeneSetNumber2KMap.iterator(); it.hasNext();) {

			it.advance();

			// example permutationAugmentedName PERMUTATION0_K562
			// @todo get permutationNumber from permutationAugmentedName
			permutationNumberCellLineNumberOrGeneSetNumber = it.key();
			numberofOverlaps = it.value();

			permutationNumber = IntervalTree.getPermutationNumber(permutationNumberCellLineNumberOrGeneSetNumber, generatedMixedNumberDescriptionOrderLength);

			// INT_4DIGIT_DNASECELLLINENUMBER
			cellLineNumberOrGeneSetNumber = IntervalTree.getCellLineNumberOrGeneSetNumber(permutationNumberCellLineNumberOrGeneSetNumber, generatedMixedNumberDescriptionOrderLength);

			// debug starts
			if (cellLineNumberOrGeneSetNumber < 0) {
				System.out.println("there is a situation 2");
			}
			// debug ends

			// example permutationNumber PERMUTATION0

			// @todo check this zero value for permutation of original data
			if (Commons.ZERO.equals(permutationNumber)) {
				cellLineNumberorGeneSetNumber2OriginalKMap.put(cellLineNumberOrGeneSetNumber, numberofOverlaps);
			} else {
				list = cellLineNumberorGeneSetNumber2AllKMap.get(cellLineNumberOrGeneSetNumber);

				if (list == null) {
					list = new TIntArrayList();
					list.add(numberofOverlaps);
					cellLineNumberorGeneSetNumber2AllKMap.put(cellLineNumberOrGeneSetNumber, list);
				} else {
					list.add(numberofOverlaps);
					cellLineNumberorGeneSetNumber2AllKMap.put(cellLineNumberOrGeneSetNumber, list);
				}
			}
		}

	}

	// TIntIntMap TIntObjectMap<TIntList> TIntIntMap version ends

	// TLongIntMap TLongObjectMap TLongIntMap version starts
	// @todo I must get permutationNumber and elementNumber from combinedNumber
	// convert permutation augmented name to only name
	// Fill elementName2ALLMap and originalElementName2KMap in convert methods
	public static void convert(TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap, TLongObjectMap<TIntList> elementNumber2AllKMap, TLongIntMap elementNumber2OriginalKMap, GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;
		Long elementNumberCellLineNumberKeggPathwayNumberr;

		Integer numberofOverlaps;

		TIntList list;

		for (TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();) {

			it.advance();

			// example permutationAugmentedName PERMUTATION0_K562
			// @todo get permutationNumber from permutationAugmentedName
			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();

			permutationNumber = IntervalTree.getPermutationNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);

			elementNumberCellLineNumberKeggPathwayNumberr = IntervalTree.getPermutationNumberRemovedLongMixedNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);

			// example permutationNumber PERMUTATION0

			// @todo check this zero value for permutation of original data
			if (Commons.ZERO.equals(permutationNumber)) {
				elementNumber2OriginalKMap.put(elementNumberCellLineNumberKeggPathwayNumberr, numberofOverlaps);
			} else {
				list = elementNumber2AllKMap.get(elementNumberCellLineNumberKeggPathwayNumberr);

				if (list == null) {
					list = new TIntArrayList();
					list.add(numberofOverlaps);
					elementNumber2AllKMap.put(elementNumberCellLineNumberKeggPathwayNumberr, list);
				} else {
					list.add(numberofOverlaps);
					elementNumber2AllKMap.put(elementNumberCellLineNumberKeggPathwayNumberr, list);
				}
			}
		}

	}

	// TLongIntMap TLongObjectMap TLongIntMap version ends

	public void fillMapfromMap(Map<String, Integer> toMap, Map<String, Integer> fromMap) {
		String name;
		Integer numberofOverlaps;

		for (Map.Entry<String, Integer> entry : fromMap.entrySet()) {
			name = entry.getKey();
			numberofOverlaps = entry.getValue();

			toMap.put(name, numberofOverlaps);

		}
	}

	// Empirical P Value and Bonferroni Corrected Empirical P Value
	// List<FunctionalElement> list is filled in this method
	// Using name2AccumulatedKMap and originalName2KMap
	public void calculateEmpricalPValues(Integer numberofComparisons, int numberofRepeats, int numberofPermutations, Map<String, List<Integer>> name2AccumulatedKMap, Map<String, Integer> originalName2KMap, List<FunctionalElement> list) {

		String originalName;
		Integer originalNumberofOverlaps;
		List<Integer> listofNumberofOverlaps;
		Integer numberofOverlaps;
		int numberofPermutationsHavingOverlapsGreaterThanorEqualto = 0;
		Float empiricalPValue;
		Float bonferroniCorrectedEmpiricalPValue;

		FunctionalElement functionalElement;

		// only consider the names in the original name 2 k map
		for (Map.Entry<String, Integer> entry : originalName2KMap.entrySet()) {
			originalName = entry.getKey();
			originalNumberofOverlaps = entry.getValue();

			listofNumberofOverlaps = name2AccumulatedKMap.get(originalName);

			// Initialise numberofPermutationsHavingOverlapsGreaterThanorEqualto
			// to zero for each original name
			numberofPermutationsHavingOverlapsGreaterThanorEqualto = 0;

			if (listofNumberofOverlaps != null) {
				for (int i = 0; i < listofNumberofOverlaps.size(); i++) {

					numberofOverlaps = listofNumberofOverlaps.get(i);

					if (numberofOverlaps >= originalNumberofOverlaps) {
						numberofPermutationsHavingOverlapsGreaterThanorEqualto++;
					}
				}// end of for
			}// end of if

			empiricalPValue = (numberofPermutationsHavingOverlapsGreaterThanorEqualto * 1.0f) / (numberofRepeats * numberofPermutations);
			bonferroniCorrectedEmpiricalPValue = ((numberofPermutationsHavingOverlapsGreaterThanorEqualto * 1.0f) / (numberofRepeats * numberofPermutations)) * numberofComparisons;

			if (bonferroniCorrectedEmpiricalPValue >= 1) {
				bonferroniCorrectedEmpiricalPValue = 1.0f;
			}

			functionalElement = new FunctionalElement();
			functionalElement.setName(originalName);
			functionalElement.setEmpiricalPValue(empiricalPValue);
			functionalElement.setBonferroniCorrectedEmpiricalPValue(bonferroniCorrectedEmpiricalPValue);

			// 18 FEB 2014
			functionalElement.setOriginalNumberofOverlaps(originalNumberofOverlaps);
			functionalElement.setNumberofPermutationsHavingOverlapsGreaterThanorEqualto(numberofPermutationsHavingOverlapsGreaterThanorEqualto);
			functionalElement.setNumberofPermutations(numberofRepeats * numberofPermutations);
			functionalElement.setNumberofComparisons(numberofComparisons);

			list.add(functionalElement);

		}// end of for

	}

	public static void fillPermutationNumberList(TIntList permutationNumberList, int runNumber, int numberofPermutationsinThisRun, int numberofPermutationsinEachRun) {

		for (int permutationNumber = 1; permutationNumber <= numberofPermutationsinThisRun; permutationNumber++) {
			permutationNumberList.add((runNumber - 1) * numberofPermutationsinEachRun + permutationNumber);
		}
		
	}

	public static void addPermutationNumberforOriginalData(TIntList permutationNumberList, Integer originalDataPermutationNumber) {
		permutationNumberList.add(originalDataPermutationNumber);
	}

	// Enrichment
	public static IntervalTree generateDnaseIntervalTreeWithNumbers(String dataFolder, ChromosomeName chromName) {
		return Annotation.createDnaseIntervalTreeWithNumbers(dataFolder, chromName);
	}

	public static IntervalTree generateTfbsIntervalTreeWithNumbers(String dataFolder, ChromosomeName chromName) {
		return Annotation.createTfbsIntervalTreeWithNumbers(dataFolder, chromName);
	}

	public static IntervalTree generateHistoneIntervalTreeWithNumbers(String dataFolder, ChromosomeName chromName) {
		return Annotation.createHistoneIntervalTreeWithNumbers(dataFolder, chromName);
	}

	public static IntervalTree generateUcscRefSeqGeneIntervalTreeWithNumbers(String dataFolder, ChromosomeName chromName) {
		return Annotation.createUcscRefSeqGenesIntervalTreeWithNumbers(dataFolder, chromName);
	}

	public static IntervalTree generateUserDefinedLibraryIntervalTreeWithNumbers(String dataFolder, int elementTypeNumber, String elementType, ChromosomeName chromName) {
		return Annotation.createUserDefinedIntervalTreeWithNumbers(dataFolder, elementTypeNumber, elementType, chromName);
	}

	public static void closeBufferedWriters(Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap) {

		BufferedWriter bufferedWriter = null;
		try {

			for (Map.Entry<Integer, BufferedWriter> entry : permutationNumber2BufferedWriterHashMap.entrySet()) {
				bufferedWriter = entry.getValue();
				bufferedWriter.close();
			}
		} catch (IOException e) {
			logger.error(e.toString());
		}

	}

	public void closeBuferedWriterswithIntegerKey(Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap) {

		BufferedWriter bufferedWriter = null;
		try {

			for (Map.Entry<Integer, BufferedWriter> entry : permutationNumber2BufferedWriterHashMap.entrySet()) {
				bufferedWriter = entry.getValue();
				bufferedWriter.close();
			}
		} catch (IOException e) {
			logger.error(e.toString());
		}

	}

	// TIntIntMap version starts
	public static void writeAnnotationstoFiles(
			String outputFolder, 
			TIntIntMap permutationNumberCellLineNumberorGeneSetNumber2KMap, 
			Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap, 
			String folderName, 
			String extraFileName, 
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int permutationNumberCellLineNumberorGeneSetNumber;
		Integer permutationNumber;
		Integer cellLineNumberorGeneSetNumber;

		Integer numberofOverlaps;

		BufferedWriter bufferedWriter = null;

		for (TIntIntIterator it = permutationNumberCellLineNumberorGeneSetNumber2KMap.iterator(); it.hasNext();) {

			it.advance();

			permutationNumberCellLineNumberorGeneSetNumber = it.key();
			numberofOverlaps = it.value();

			permutationNumber = IntervalTree.getPermutationNumber(permutationNumberCellLineNumberorGeneSetNumber, generatedMixedNumberDescriptionOrderLength);
			cellLineNumberorGeneSetNumber = IntervalTree.getCellLineNumberOrGeneSetNumber(permutationNumberCellLineNumberorGeneSetNumber, generatedMixedNumberDescriptionOrderLength);

			bufferedWriter = permutationNumber2BufferedWriterHashMap.get(permutationNumber);

			try {

				if (bufferedWriter == null) {
					bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + folderName + Commons.PERMUTATION + permutationNumber + "_" + extraFileName + ".txt"));

					bufferedWriter.write("CellLineNumberOrKeggPathwayNumber" + "\t" + "NumberofOverlaps" + System.getProperty("line.separator"));

					permutationNumber2BufferedWriterHashMap.put(permutationNumber, bufferedWriter);
				}

				if (cellLineNumberorGeneSetNumber > 0) {
					bufferedWriter.write(cellLineNumberorGeneSetNumber + "\t");
				}

				bufferedWriter.write(numberofOverlaps + System.getProperty("line.separator"));
				bufferedWriter.close();
			} catch (IOException e) {
				logger.error(e.toString());
			}
		}// End of for

	}

	// TIntIntMap version ends

	public static void writeAnnotationstoFiles_ElementNumberCellLineNumber(String outputFolder, TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap, Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName, String extraFileName, GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;
		Integer elementNumber;
		Integer cellLineNumber;

		Integer numberofOverlaps;

		BufferedWriter bufferedWriter = null;

		for (TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();) {

			it.advance();

			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();

			permutationNumber = IntervalTree.getPermutationNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
			elementNumber = IntervalTree.getElementNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
			cellLineNumber = IntervalTree.getCellLineNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);

			bufferedWriter = permutationNumber2BufferedWriterHashMap.get(permutationNumber);

			try {

				if (bufferedWriter == null) {
					bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + folderName + Commons.PERMUTATION + permutationNumber + "_" + extraFileName + ".txt"));

					bufferedWriter.write("TforHistoneNumber" + "\t" + "CellLineNumber" + "\t" + "NumberofOverlaps" + System.getProperty("line.separator"));

					permutationNumber2BufferedWriterHashMap.put(permutationNumber, bufferedWriter);
				}

				if (elementNumber > 0) {
					bufferedWriter.write(elementNumber + "\t");
				}

				if (cellLineNumber > 0) {
					bufferedWriter.write(cellLineNumber + "\t");
				}

				bufferedWriter.write(numberofOverlaps + System.getProperty("line.separator"));

				bufferedWriter.close();
			} catch (IOException e) {
				logger.error(e.toString());
			}
		}// End of for

	}

	public static void writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(String outputFolder, TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap, Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName, String extraFileName, GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;
		Integer elementNumber;
		Integer keggPathwayNumber;

		Integer numberofOverlaps;

		BufferedWriter bufferedWriter = null;

		for (TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();) {

			it.advance();

			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();

			permutationNumber = IntervalTree.getPermutationNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
			elementNumber = IntervalTree.getElementNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
			keggPathwayNumber = IntervalTree.getGeneSetNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);

			bufferedWriter = permutationNumber2BufferedWriterHashMap.get(permutationNumber);

			try {

				if (bufferedWriter == null) {
					bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + folderName + Commons.PERMUTATION + permutationNumber + "_" + extraFileName + ".txt"));

					bufferedWriter.write("TfNumber" + "\t" + "KeggPathwayNumber" + "\t" + "NumberofOverlaps" + System.getProperty("line.separator"));

					permutationNumber2BufferedWriterHashMap.put(permutationNumber, bufferedWriter);
				}

				if (elementNumber > 0) {
					bufferedWriter.write(elementNumber + "\t");
				}

				if (keggPathwayNumber > 0) {
					bufferedWriter.write(keggPathwayNumber + "\t");
				}

				bufferedWriter.write(numberofOverlaps + System.getProperty("line.separator"));
				bufferedWriter.close();
			} catch (IOException e) {
				logger.error(e.toString());
			}
		}// End of for

	}

	public static void writeAnnotationstoFiles(String outputFolder, TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap, Map<Integer, BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName, String extraFileName, GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;

		Integer permutationNumber;
		Integer elementNumber;
		Integer cellLineNumber;
		Integer keggPathwayNumber;
		Integer elementTypeNumber;

		Integer numberofOverlaps;

		BufferedWriter bufferedWriter = null;

		for (TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();) {

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
			permutationNumber = IntervalTree.getPermutationNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
			// Get permutation number ends

			// Get bufferedWriter starts
			bufferedWriter = permutationNumber2BufferedWriterHashMap.get(permutationNumber);
			// Get bufferedWriter ends

			try {

				if (bufferedWriter == null) {
					bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + folderName + Commons.PERMUTATION + permutationNumber + "_" + extraFileName + ".txt"));

					// Set header line starts
					switch (generatedMixedNumberDescriptionOrderLength) {

					// User Defined Library
					case LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:
						bufferedWriter.write("ElementTypeNumber" + "\t" + "ElementNumber" + "\t" + "NumberofOverlaps" + System.getProperty("line.separator"));
						break;

					// User Defined Geneset
					case LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER:
						bufferedWriter.write("UserDefinedGeneSetNumber" + "\t" + "NumberofOverlaps" + System.getProperty("line.separator"));
						break;

					// PermutationNumber_ElementNumber_CellLineNumber_KeggPathwayNumber
					case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:
						bufferedWriter.write("TfNumber" + "\t" + "CellLineNumber" + "\t" + "KeggPathwayNumber" + "\t" + "NumberofOverlaps" + System.getProperty("line.separator"));
						break;

					default:
						break;
					}// End of SWITCH
						// Set header line ends

					permutationNumber2BufferedWriterHashMap.put(permutationNumber, bufferedWriter);
				}// End of if bufferedWriter is NULL

				// mixed number resolution and write to bufferedWriter starts
				switch (generatedMixedNumberDescriptionOrderLength) {

				// User Defined Library
				case LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:
					elementTypeNumber = IntervalTree.getElementTypeNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
					elementNumber = IntervalTree.getElementNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
					bufferedWriter.write(elementTypeNumber + "\t" + elementNumber + "\t" + numberofOverlaps + System.getProperty("line.separator"));
					break;

				// User Defined Geneset
				case LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER:
					keggPathwayNumber = IntervalTree.getGeneSetNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
					bufferedWriter.write(keggPathwayNumber + "\t" + numberofOverlaps + System.getProperty("line.separator"));

					break;

				// PermutationNumber_ElementNumber_CellLineNumber_KeggPathwayNumber
				case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:
					elementNumber = IntervalTree.getElementNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
					cellLineNumber = IntervalTree.getCellLineNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
					keggPathwayNumber = IntervalTree.getGeneSetNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, generatedMixedNumberDescriptionOrderLength);
					bufferedWriter.write(elementNumber + "\t" + cellLineNumber + "\t" + keggPathwayNumber + "\t" + numberofOverlaps + System.getProperty("line.separator"));
					break;

				default:
					break;
				}// End of SWITCH
					// mixed number resolution and write to bufferedWriter ends

				bufferedWriter.close();
			} catch (IOException e) {
				logger.error(e.toString());
			}
		}// End of for

	}

	// TIntIntMap version starts
	// Accumulate chromosomeBasedName2KMap results in accumulatedName2KMap
	// Accumulate number of overlaps coming from each chromosome
	// based on permutationNumber and ElementName
	public static void accumulate(TIntIntMap chromosomeBasedName2KMap, TIntIntMap accumulatedName2KMap) {
		int permutationNumberCellLineNumberOrKeggPathwayNumber;
		Integer numberofOverlaps;

		// accessing keys/values through an iterator:
		for (TIntIntIterator it = chromosomeBasedName2KMap.iterator(); it.hasNext();) {

			it.advance();

			permutationNumberCellLineNumberOrKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();

			if (!(accumulatedName2KMap.containsKey(permutationNumberCellLineNumberOrKeggPathwayNumber))) {
				accumulatedName2KMap.put(permutationNumberCellLineNumberOrKeggPathwayNumber, numberofOverlaps);
			} else {
				accumulatedName2KMap.put(permutationNumberCellLineNumberOrKeggPathwayNumber, accumulatedName2KMap.get(permutationNumberCellLineNumberOrKeggPathwayNumber) + numberofOverlaps);
			}

		}

	}

	// TIntIntMap version ends

	// Accumulate chromosomeBasedName2KMap results in accumulatedName2KMap
	// Accumulate number of overlaps coming from each chromosome
	// based on permutationNumber and ElementName
	public static void accumulate(TLongIntMap chromosomeBasedName2KMap, TLongIntMap accumulatedName2KMap) {
		Long permutationNumberElementNumber;
		Integer numberofOverlaps;

		// accessing keys/values through an iterator:
		for (TLongIntIterator it = chromosomeBasedName2KMap.iterator(); it.hasNext();) {

			it.advance();

			permutationNumberElementNumber = it.key();
			numberofOverlaps = it.value();

			if (!(accumulatedName2KMap.containsKey(permutationNumberElementNumber))) {
				accumulatedName2KMap.put(permutationNumberElementNumber, numberofOverlaps);
			} else {
				accumulatedName2KMap.put(permutationNumberElementNumber, accumulatedName2KMap.get(permutationNumberElementNumber) + numberofOverlaps);

			}

		}

	}
	
	
	// Accumulate chromosomeBasedAllMaps in accumulatedAllMaps
	// Coming from each chromosome
	public static void accumulate(AllMapsDnaseTFHistoneWithNumbers chromosomeBasedAllMaps, AllMapsDnaseTFHistoneWithNumbers accumulatedAllMaps, AnnotationType annotationType) {

		switch (annotationType) {
		case DO_DNASE_ANNOTATION:
			accumulate(chromosomeBasedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap());
			break;

		case DO_TF_ANNOTATION:
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap());
			break;

		case DO_HISTONE_ANNOTATION:
			accumulate(chromosomeBasedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap());
			break;
			
		default:
			break;
		}
	}

	// Accumulate chromosomeBasedAllMaps in accumulatedAllMaps
	// Coming from each chromosome
	public static void accumulate(AllMapsWithNumbers chromosomeBasedAllMaps, AllMapsWithNumbers accumulatedAllMaps, AnnotationType annotationType) {

		switch (annotationType) {
		case DO_DNASE_ANNOTATION:
			accumulate(chromosomeBasedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap());
			break;

		case DO_TF_ANNOTATION:
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap());
			break;

		case DO_HISTONE_ANNOTATION:
			accumulate(chromosomeBasedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap());
			break;
			
		case DO_GENE_ANNOTATION:
			accumulate(chromosomeBasedAllMaps.getPermutationNumberGeneNumber2KMap(), accumulatedAllMaps.getPermutationNumberGeneNumber2KMap());
			break;

		case DO_USER_DEFINED_GENESET_ANNOTATION: // Exon Based User Defined
													// GeneSet
			accumulate(chromosomeBasedAllMaps.getPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap(), accumulatedAllMaps.getPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap());
			// Regulation Based User Defined GeneSet
			accumulate(chromosomeBasedAllMaps.getPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap(), accumulatedAllMaps.getPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap());
			// All Based User Defined GeneSet
			accumulate(chromosomeBasedAllMaps.getPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap(), accumulatedAllMaps.getPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap());
			break;

		case DO_USER_DEFINED_LIBRARY_ANNOTATION:
			accumulate(chromosomeBasedAllMaps.getPermutationNumberElementTypeNumberElementNumber2KMap(), accumulatedAllMaps.getPermutationNumberElementTypeNumberElementNumber2KMap());
			break;

		case DO_KEGGPATHWAY_ANNOTATION: // Exon Based KEGG Pathway
			accumulate(chromosomeBasedAllMaps.getPermutationNumberExonBasedKeggPathwayNumber2KMap(), accumulatedAllMaps.getPermutationNumberExonBasedKeggPathwayNumber2KMap());
			// Regulation Based KEGG Pathway
			accumulate(chromosomeBasedAllMaps.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(), accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap());
			// All Based KEGG Pathway
			accumulate(chromosomeBasedAllMaps.getPermutationNumberAllBasedKeggPathwayNumber2KMap(), accumulatedAllMaps.getPermutationNumberAllBasedKeggPathwayNumber2KMap());
			break;

		case DO_TF_KEGGPATHWAY_ANNOTATION: // TF and KEGG Pathway Annotation
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap(), accumulatedAllMaps.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap(), accumulatedAllMaps.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap(), accumulatedAllMaps.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap());
			break;

		case DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION: // TF and CellLine and
													// KEGG Pathway
													// Annotation
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap(), accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap(), accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap(), accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap());
			break;

		default:
			break;

		}// End od SWITCH

	}

	public void deleteIntervalTrees(List<IntervalTree> listofIntervalTrees) {
		IntervalTree dnaseIntervalTree = listofIntervalTrees.get(0);
		IntervalTree tfbsIntervalTree = listofIntervalTrees.get(1);
		IntervalTree histoneIntervalTree = listofIntervalTrees.get(2);
		IntervalTree ucscRefSeqGenesIntervalTree = listofIntervalTrees.get(3);

		IntervalTree.deleteNodesofIntervalTree(dnaseIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree(tfbsIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree(histoneIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree(ucscRefSeqGenesIntervalTree.getRoot());

		// dnaseIntervalTree = new IntervalTree();
		dnaseIntervalTree = null;
		tfbsIntervalTree = null;
		histoneIntervalTree = null;
		ucscRefSeqGenesIntervalTree = null;
	}

//	//Has no effect on the execution time
//	public static void deleteIntervalTree(IntervalTree intervalTree) {
//
//		IntervalTree.deleteNodesofIntervalTree(intervalTree.getRoot());
//		intervalTree.setRoot(null);
//		intervalTree = null;
//	}



	public void deleteGCCharArray(char[] gcCharArray) {
		gcCharArray = null;
	}

	public void deleteMapabilityFloatArray(float[] mapabilityFloatArray) {
		mapabilityFloatArray = null;
	}

	// NEW FUNCIONALITY ADDED
	// First Generate random data concurrently
	// Then annotate permutations concurrently
	// the tasks are executed
	// after all the parallel work is done
	// results are written to files
	public static void annotateAllPermutationsInThreads(
			String outputFolder, 
			String dataFolder, 
			int NUMBER_OF_AVAILABLE_PROCESSORS, 
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
			int overlapDefinition, 
			TIntObjectMap<TShortList> geneId2ListofKeggPathwayNumberMap, 
			TIntObjectMap<TShortList> geneId2ListofUserDefinedGeneSetNumberMap, 
			TIntObjectMap<String> elementTypeNumber2ElementTypeMap) {

		String permutationBasedResultDirectory;

		// allMaps stores one chromosome based results
		AllMapsWithNumbers allMapsWithNumbers = new AllMapsWithNumbers();
		AllMapsDnaseTFHistoneWithNumbers allMapsDnaseTFHistoneWithNumbers = new AllMapsDnaseTFHistoneWithNumbers();

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

		TByteList gcByteList = null;
		
		TIntList mapabilityChromosomePositionList = null;

		//MapabilityShortValueList is preferred since it provides better mapability values
		//No difference in execution time and memory usage
		//Average number of trials is almost the same.
		TShortList mapabilityShortValueList = null;
		//TByteList mapabilityByteValueList = null;
		
		
		List<Integer> hg19ChromosomeSizes = new ArrayList<Integer>();

		/******************************************************************************************************/
		/************** * PARTITION ORIGINAL INPUT LINES INTO CHROMOSOME BASED INPUT LINES STARTS *************/
		// Partition the original input data lines in a chromosome based manner
		partitionDataChromosomeBased(allOriginalInputLines, chromosomeName2OriginalInputLinesMap);
		/*************** PARTITION ORIGINAL INPUT LINES INTO CHROMOSOME BASED INPUT LINES ENDS*****************/
		/******************************************************************************************************/

		/********************************************************************************************************/
		/******************************* GET HG19 CHROMOSOME SIZES STARTS ***************************************/
		hg19.GRCh37Hg19Chromosome.initializeChromosomeSizes(hg19ChromosomeSizes);
		// get the hg19 chromosome sizes
		hg19.GRCh37Hg19Chromosome.getHg19ChromosomeSizes(hg19ChromosomeSizes, dataFolder, Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		/******************************* GET HG19 CHROMOSOME SIZES ENDS *****************************************/
		/********************************************************************************************************/

		ChromosomeName chromName;
		int chromSize;
		List<InputLineMinimal> chromosomeBaseOriginalInputLines;
		TIntObjectMap<List<InputLineMinimal>> permutationNumber2RandomlyGeneratedDataHashMap = new TIntObjectHashMap<List<InputLineMinimal>>();

		AnnotateWithNumbers annotateWithNumbers;
		AnnotateDnaseTFHistoneWithNumbers annotateDnaseTFHistoneWithNumbers;
		
		GenerateRandomData generateRandomData;
		ForkJoinPool pool = new ForkJoinPool(NUMBER_OF_AVAILABLE_PROCESSORS);

		startTimeAllPermutationsAllChromosomes = System.currentTimeMillis();

		GlanetRunner.appendLog("Run Number: " + runNumber);
		logger.info("Run Number: " + runNumber);
		
		/********************************************************************************************************/
		/****************************** GENERATE PERMUTATION NUMBER LIST STARTS *********************************/
		/********************************************************************************************************/
		permutationNumberList = new TIntArrayList();
		
		GlanetRunner.appendLog("PermutationNumberList is filled.");
		logger.info("PermutationNumberList is filled.");
		fillPermutationNumberList(permutationNumberList, runNumber, numberofPermutationsinThisRun, numberofPermutationsinEachRun);
		/********************************************************************************************************/
		/****************************** GENERATE PERMUTATION NUMBER LIST ENDS ***********************************/
		/********************************************************************************************************/
		
		
		
		/********************************************************************************************************/
		/********************************** ADD Permutation Number for GIVEN ORIGINAL DATA STARTS ***************/
		/********************************************************************************************************/
		// In each run, add permutation number for original data
		// After Random Data Generation has been ended
		// Add User Given Original Data(Genomic Variants) to the randomly generated data map
		addPermutationNumberforOriginalData(permutationNumberList, Commons.ORIGINAL_DATA_PERMUTATION_NUMBER);
		/********************************************************************************************************/
		/********************************** ADD Permutation Number for GIVEN ORIGINAL DATA ENDS *****************/
		/********************************************************************************************************/



		/******************************************************************************************************/
		/********************************* FOR EACH HG19 CHROMOSOME STARTS ************************************/
		/******************************************************************************************************/
		for (int i = 1; i <= Commons.NUMBER_OF_CHROMOSOMES_HG19; i++) {

			chromName = GRCh37Hg19Chromosome.getChromosomeName(i);
			chromSize = hg19ChromosomeSizes.get(i - 1);

			GlanetRunner.appendLog("chromosome name:" + chromName.convertEnumtoString() + " chromosome size: " + chromSize);
			logger.info("chromosome name:" + chromName.convertEnumtoString() + " chromosome size: " + chromSize);
			
			chromosomeBaseOriginalInputLines = chromosomeName2OriginalInputLinesMap.get(chromName);

			if (chromosomeBaseOriginalInputLines != null) {

				gcByteList = new TByteArrayList();
				mapabilityChromosomePositionList = new TIntArrayList();
				
				//MapabilityShortList
				mapabilityShortValueList = new TShortArrayList();
				
				
				//mapabilityByteValueList 
				//mapabilityByteValueList = new TByteArrayList();
				
				startTimeEverythingIncludedAnnotationPermutationsForEachChromosome = System.currentTimeMillis();
				
				
				/*******************************************************************************************************************************/
				/************************ FILL GCByteTroveList and MapabilityIntTroveList and  MapabilityShortTroveList STARTS *****************/
				/*******************************************************************************************************************************/
				// Fill gcCharArray and mapabilityFloatArray
				if (generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()) {
					
					GlanetRunner.appendLog("Filling of gcByteList, mapabilityChromosomePositionList, mapabilityShortValueList  has started.");
					logger.info("Filling of gcByteList, mapabilityChromosomePositionList, mapabilityShortValueList  has started.");
					
					startTimeFillingList = System.currentTimeMillis();

					//GC Old way
					//gcCharArray = ChromosomeBasedGCArray.getChromosomeGCArray(dataFolder, chromName, chromSize);
					//GC New Way
					ChromosomeBasedGCTroveList.fillTroveList(dataFolder,chromName,gcByteList);
					
					//Mapability Old Way
					//mapabilityFloatArray = ChromosomeBasedMapabilityArray.getChromosomeMapabilityArray(dataFolder, chromName, chromSize);
					//Mapability New Way
					ChromosomeBasedMappabilityTroveList.fillTroveList(dataFolder, chromName,mapabilityChromosomePositionList,mapabilityShortValueList);
					 
					endTimeFillingList = System.currentTimeMillis();

					GlanetRunner.appendLog("Filling of gcByteList, mapabilityChromosomePositionList, mapabilityShortValueList  has taken " + (float)((endTimeFillingList-startTimeFillingList)/1000)+ " seconds.");
					logger.info("Filling of gcByteList, mapabilityChromosomePositionList, mapabilityShortValueList  has taken " + (float)((endTimeFillingList-startTimeFillingList)/1000)+ " seconds.");
						
				}
				/*******************************************************************************************************************************/
				/************************ FILL GCByteTroveList and MapabilityIntTroveList and  MapabilityShortTroveList ENDS *******************/
				/*******************************************************************************************************************************/

				
				
				/********************************************************************************************************/
				/********************************** GENERATE RANDOM DATA STARTS *****************************************/
				/********************************************************************************************************/
				GlanetRunner.appendLog("Generate Random Data for permutations has started.");
				logger.info("Generate Random Data for permutations has started.");
				// First generate Random Data
				
				startTimeGenerateRandomData = System.currentTimeMillis();
				
				
				generateRandomData = new GenerateRandomData(outputFolder, chromSize, chromName, chromosomeBaseOriginalInputLines, generateRandomDataMode, writeGeneratedRandomDataMode, Commons.ZERO, permutationNumberList.size(), permutationNumberList,gcByteList, mapabilityChromosomePositionList,mapabilityShortValueList);
				permutationNumber2RandomlyGeneratedDataHashMap = pool.invoke(generateRandomData);
				
				
//				//For testing average number of trials mapabilityByteList versus mapabilityShortList starts
//				float totalNumberofTrialsPerPermutation = 0;
//				float averageNumberofTrialsPerPermutation = 0;
//				float averageNumberofTrialsForAllPermutation = 0;
//				
//				for(TIntObjectIterator<List<InputLineMinimal>> it= permutationNumber2RandomlyGeneratedDataHashMap.iterator(); it.hasNext();){
//					
//					it.advance();
//					
//					List<InputLineMinimal> temp = it.value();
//					
//					totalNumberofTrialsPerPermutation = 0;
//					
//					for(InputLineMinimal line : temp) {
//						totalNumberofTrialsPerPermutation += line.getNumberofTrials();
//						
//					}
//					
//					averageNumberofTrialsPerPermutation = totalNumberofTrialsPerPermutation/temp.size();
//					
//					averageNumberofTrialsForAllPermutation +=averageNumberofTrialsPerPermutation;
//				}
//				
//				averageNumberofTrialsForAllPermutation = averageNumberofTrialsForAllPermutation/permutationNumber2RandomlyGeneratedDataHashMap.size();
//				
//				logger.info("averageNumberofTrialsForAllPermutation" + "\t" + averageNumberofTrialsForAllPermutation);
//				//For testing average number of trials mapabilityByteList versus mapabilityShortList ends
				
				
				endTimeGenerateRandomData = System.currentTimeMillis();

				GlanetRunner.appendLog("Generate Random Data for permutations has taken " + (float)((endTimeGenerateRandomData-startTimeGenerateRandomData)/1000) + " seconds.");
				logger.info("Generate Random Data for permutations has taken " + (float)((endTimeGenerateRandomData-startTimeGenerateRandomData)/1000) + " seconds.");
				/********************************************************************************************************/
				/********************************** GENERATE RANDOM DATA ENDS *****************************************/
				/******************************************************************************************************/

				/********************************************************************************************************/
				/**********************************Add the original data starts************************** ***************/
				/********************************************************************************************************/
				// Add the original data to permutationNumber2RandomlyGeneratedDataHashMap
				permutationNumber2RandomlyGeneratedDataHashMap.put(Commons.ORIGINAL_DATA_PERMUTATION_NUMBER, chromosomeBaseOriginalInputLines);
				/********************************************************************************************************/
				/**********************************Add the original data ends**************************** ***************/
				/********************************************************************************************************/

				
				/******************************************************************************************************/
				/***************************************** FREE MEMORY STARTS *****************************************/
				gcByteList = null;
				mapabilityChromosomePositionList = null;
				
				//MapabilityShortValueList
				mapabilityShortValueList = null;
				
				//mapabilityByteValueList
				//mapabilityByteValueList = null;

				System.gc();
				System.runFinalization();
				/***************************************** FREE MEMORY ENDS *******************************************/
				/******************************************************************************************************/

				
				
				/********************************************************************************************************/
				/***************************** ANNOTATE PERMUTATIONS STARTS *********************************************/
				/********************************************************************************************************/
				GlanetRunner.appendLog("Annotation of Permutations has started.");
				logger.info("Annotation of Permutations has started.");
				
				startTimeOnlyAnnotationPermutationsForEachChromosome = System.currentTimeMillis();

				
//				/********************************************************************************************************/
//				/***********************Using AnnotateDnaseTFHistoneWithNumbers STARTS **********************************/
//				/********************************************************************************************************/
//				if (dnaseAnnotationType.doDnaseAnnotation()) {
//					// dnase
//					// generate dnase interval tree
//					intervalTree = generateDnaseIntervalTreeWithNumbers(dataFolder, chromName);
//					
//					annotateDnaseTFHistoneWithNumbers = new AnnotateDnaseTFHistoneWithNumbers(
//							chromName, 
//							permutationNumber2RandomlyGeneratedDataHashMap, 
//							runNumber, 
//							numberofPermutationsinThisRun, 
//							writePermutationBasedandParametricBasedAnnotationResultMode, 
//							permutationNumberList,
//							intervalTree,
//							AnnotationType.DO_DNASE_ANNOTATION,
//							Commons.ZERO, 
//							permutationNumberList.size(),
//							outputFolder,
//							overlapDefinition);
//					
//					allMapsDnaseTFHistoneWithNumbers = pool.invoke(annotateDnaseTFHistoneWithNumbers);
//					accumulate(allMapsDnaseTFHistoneWithNumbers, accumulatedAllMapsDnaseTFHistoneWithNumbers, AnnotationType.DO_DNASE_ANNOTATION);
//					allMapsDnaseTFHistoneWithNumbers = null;
//					
//					//Has no effect on the execution time
//					//deleteIntervalTree(intervalTree);
//					
//					intervalTree = null;
//
//					System.gc();
//					System.runFinalization();
//				}
//				
//				if (histoneAnnotationType.doHistoneAnnotation()) {
//					// histone
//					// generate histone interval tree
//					intervalTree = generateHistoneIntervalTreeWithNumbers(dataFolder, chromName);
//					
//					
//					annotateDnaseTFHistoneWithNumbers = new AnnotateDnaseTFHistoneWithNumbers(
//							chromName, 
//							permutationNumber2RandomlyGeneratedDataHashMap, 
//							runNumber, 
//							numberofPermutationsinThisRun, 
//							writePermutationBasedandParametricBasedAnnotationResultMode, 
//							permutationNumberList,
//							intervalTree,
//							AnnotationType.DO_HISTONE_ANNOTATION,
//							Commons.ZERO, 
//							permutationNumberList.size(),
//							outputFolder,
//							overlapDefinition);
//					
//					
//					
//					allMapsDnaseTFHistoneWithNumbers = pool.invoke(annotateDnaseTFHistoneWithNumbers);
//					
//					accumulate(allMapsDnaseTFHistoneWithNumbers, accumulatedAllMapsDnaseTFHistoneWithNumbers, AnnotationType.DO_HISTONE_ANNOTATION);
//					allMapsDnaseTFHistoneWithNumbers = null;
//					
//					intervalTree = null;
//
//					System.gc();
//					System.runFinalization();
//				}
//				
//				if ((tfAnnotationType.doTFAnnotation()) && 
//						!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
//						!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())) {
//					// tf
//					// generate tf interval tree
//					intervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder, chromName);
//					
//					
//					annotateDnaseTFHistoneWithNumbers = new AnnotateDnaseTFHistoneWithNumbers(
//							chromName, 
//							permutationNumber2RandomlyGeneratedDataHashMap, 
//							runNumber, 
//							numberofPermutationsinThisRun, 
//							writePermutationBasedandParametricBasedAnnotationResultMode, 
//							permutationNumberList,
//							intervalTree,
//							AnnotationType.DO_TF_ANNOTATION,
//							Commons.ZERO, 
//							permutationNumberList.size(),
//							outputFolder,
//							overlapDefinition);
//					
//					
//					
//					allMapsDnaseTFHistoneWithNumbers = pool.invoke(annotateDnaseTFHistoneWithNumbers);
//					accumulate(allMapsDnaseTFHistoneWithNumbers, accumulatedAllMapsDnaseTFHistoneWithNumbers, AnnotationType.DO_TF_ANNOTATION);
//					allMapsDnaseTFHistoneWithNumbers = null;
//					
//					intervalTree = null;
//
//					System.gc();
//					System.runFinalization();
//				}
//				/********************************************************************************************************/
//				/***********************Using AnnotateDnaseTFHistoneWithNumbers ENDS*************************************/
//				/********************************************************************************************************/
				
				
				/********************************************************************************************************/
				/***********************Using AnnotateWithNumbers starts*************************************************/
				/********************************************************************************************************/
				if (dnaseAnnotationType.doDnaseAnnotation()) {
					// dnase
					// generate dnase interval tree
					intervalTree = generateDnaseIntervalTreeWithNumbers(dataFolder, chromName);
					
					annotateWithNumbers = new AnnotateWithNumbers(
							outputFolder, 
							chromName, 
							permutationNumber2RandomlyGeneratedDataHashMap, 
							runNumber, 
							numberofPermutationsinThisRun, 
							writePermutationBasedandParametricBasedAnnotationResultMode, 
							Commons.ZERO, 
							permutationNumberList.size(), 
							permutationNumberList, 
							intervalTree, 
							null, 
							AnnotationType.DO_DNASE_ANNOTATION, 
							null, 
							overlapDefinition);
					
					allMapsWithNumbers = pool.invoke(annotateWithNumbers);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_DNASE_ANNOTATION);
					
					allMapsWithNumbers = null;
					intervalTree = null;

					System.gc();
					System.runFinalization();
				}
				

				
				
				//Using AnnotateWithNumbers
				if (histoneAnnotationType.doHistoneAnnotation()) {
					// histone
					// generate histone interval tree
					intervalTree = generateHistoneIntervalTreeWithNumbers(dataFolder, chromName);
					annotateWithNumbers = new AnnotateWithNumbers(outputFolder, chromName, permutationNumber2RandomlyGeneratedDataHashMap, runNumber, numberofPermutationsinThisRun, writePermutationBasedandParametricBasedAnnotationResultMode, Commons.ZERO, permutationNumberList.size(), permutationNumberList, intervalTree, null, AnnotationType.DO_HISTONE_ANNOTATION, null, overlapDefinition);
					allMapsWithNumbers = pool.invoke(annotateWithNumbers);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_HISTONE_ANNOTATION);
					allMapsWithNumbers = null;
					intervalTree = null;

					System.gc();
					System.runFinalization();
				}

				
				
				//Using AnnotateWithNumbers
				if ((tfAnnotationType.doTFAnnotation()) && 
						!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
						!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())) {
					// tf
					// generate tf interval tree
					intervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder, chromName);
					annotateWithNumbers = new AnnotateWithNumbers(outputFolder, chromName, permutationNumber2RandomlyGeneratedDataHashMap, runNumber, numberofPermutationsinThisRun, writePermutationBasedandParametricBasedAnnotationResultMode, Commons.ZERO, permutationNumberList.size(), permutationNumberList, intervalTree, null, AnnotationType.DO_TF_ANNOTATION, null, overlapDefinition);
					allMapsWithNumbers = pool.invoke(annotateWithNumbers);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_ANNOTATION);
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
				if (geneAnnotationType.doGeneAnnotation()){
					
					//Gene Enrichment
					intervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);
					
					annotateWithNumbers = new AnnotateWithNumbers(outputFolder, chromName, permutationNumber2RandomlyGeneratedDataHashMap, runNumber, numberofPermutationsinThisRun, writePermutationBasedandParametricBasedAnnotationResultMode, Commons.ZERO, permutationNumberList.size(), permutationNumberList, intervalTree, null, AnnotationType.DO_GENE_ANNOTATION, null, overlapDefinition);
					
					allMapsWithNumbers = pool.invoke(annotateWithNumbers);
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
				if (userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()) {
					// ucsc RefSeq Genes
					// generate UCSC RefSeq Genes interval tree
					intervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);
					annotateWithNumbers = new AnnotateWithNumbers(outputFolder, chromName, permutationNumber2RandomlyGeneratedDataHashMap, runNumber, numberofPermutationsinThisRun, writePermutationBasedandParametricBasedAnnotationResultMode, Commons.ZERO, permutationNumberList.size(), permutationNumberList, intervalTree, null, AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION, geneId2ListofUserDefinedGeneSetNumberMap, overlapDefinition);
					allMapsWithNumbers = pool.invoke(annotateWithNumbers);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION);
					allMapsWithNumbers = null;
					intervalTree = null;

					System.gc();
					System.runFinalization();
				}

				// UserDefinedLibrary starts
				if (userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()) {

					int elementTypeNumber;
					String elementType;

					// For each elementTypeNumber
					for (TIntObjectIterator<String> it = elementTypeNumber2ElementTypeMap.iterator(); it.hasNext();) {

						it.advance();
						elementTypeNumber = it.key();
						elementType = it.value();

						// generate User Defined Library Interval Tree
						intervalTree = generateUserDefinedLibraryIntervalTreeWithNumbers(dataFolder, elementTypeNumber, elementType, chromName);
						annotateWithNumbers = new AnnotateWithNumbers(outputFolder, chromName, permutationNumber2RandomlyGeneratedDataHashMap, runNumber, numberofPermutationsinThisRun, writePermutationBasedandParametricBasedAnnotationResultMode, Commons.ZERO, permutationNumberList.size(), permutationNumberList, intervalTree, null, AnnotationType.DO_USER_DEFINED_LIBRARY_ANNOTATION, null, overlapDefinition);
						allMapsWithNumbers = pool.invoke(annotateWithNumbers);
						accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_USER_DEFINED_LIBRARY_ANNOTATION);
						allMapsWithNumbers = null;
						intervalTree = null;

						System.gc();
						System.runFinalization();

					}// End of for each elementTypeNumber
				}
				// UserDefinedLibrary ends

				if (keggPathwayAnnotationType.doKEGGPathwayAnnotation() && 
						!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
						!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())) {
					// ucsc RefSeq Genes
					// generate UCSC RefSeq Genes interval tree
					intervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);
					annotateWithNumbers = new AnnotateWithNumbers(outputFolder, chromName, permutationNumber2RandomlyGeneratedDataHashMap, runNumber, numberofPermutationsinThisRun, writePermutationBasedandParametricBasedAnnotationResultMode, Commons.ZERO, permutationNumberList.size(), permutationNumberList, intervalTree, null, AnnotationType.DO_KEGGPATHWAY_ANNOTATION, geneId2ListofKeggPathwayNumberMap, overlapDefinition);
					allMapsWithNumbers = pool.invoke(annotateWithNumbers);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_KEGGPATHWAY_ANNOTATION);
					allMapsWithNumbers = null;
					intervalTree = null;

					System.gc();
					System.runFinalization();
				}

				if (tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
						!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())) {

					// New Functionality START
					// tfbs
					// Kegg Pathway (exon Based, regulation Based, all Based)
					// tfbs and Kegg Pathway (exon Based, regulation Based, all
					// Based)
					// generate tf interval tree and ucsc refseq genes interval
					// tree
					tfIntervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder, chromName);
					ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);
					annotateWithNumbers = new AnnotateWithNumbers(outputFolder, chromName, permutationNumber2RandomlyGeneratedDataHashMap, runNumber, numberofPermutationsinThisRun, writePermutationBasedandParametricBasedAnnotationResultMode, Commons.ZERO, permutationNumberList.size(), permutationNumberList, tfIntervalTree, ucscRefSeqGenesIntervalTree, AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION, geneId2ListofKeggPathwayNumberMap, overlapDefinition);
					allMapsWithNumbers = pool.invoke(annotateWithNumbers);
					// Will be used for TF and KEGG Pathway Enrichment or
					// for TF and CellLine and KEGG Pathway Enrichment
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_ANNOTATION);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_KEGGPATHWAY_ANNOTATION);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION);

					allMapsWithNumbers = null;
					tfIntervalTree = null;
					ucscRefSeqGenesIntervalTree = null;
					// New Functionality END

					System.gc();
					System.runFinalization();

				}

				if (!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) &&
						tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()) {

					// New Functionality START
					// tfbs
					// Kegg Pathway (exon Based, regulation Based, all Based)
					// tfbs and Kegg Pathway (exon Based, regulation Based, all
					// Based)
					// generate tf interval tree and ucsc refseq genes interval
					// tree
					tfIntervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder, chromName);
					ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);
					annotateWithNumbers = new AnnotateWithNumbers(outputFolder, chromName, permutationNumber2RandomlyGeneratedDataHashMap, runNumber, numberofPermutationsinThisRun, writePermutationBasedandParametricBasedAnnotationResultMode, Commons.ZERO, permutationNumberList.size(), permutationNumberList, tfIntervalTree, ucscRefSeqGenesIntervalTree, AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION, geneId2ListofKeggPathwayNumberMap, overlapDefinition);
					allMapsWithNumbers = pool.invoke(annotateWithNumbers);
					// Will be used for Tf and KeggPathway Enrichment or
					// for Tf and CellLine and KeggPathway Enrichment
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_ANNOTATION);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_KEGGPATHWAY_ANNOTATION);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION);

					allMapsWithNumbers = null;
					tfIntervalTree = null;
					ucscRefSeqGenesIntervalTree = null;
					// New Functionality END

					System.gc();
					System.runFinalization();

				}

				if (tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
						tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()) {

					tfIntervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder, chromName);
					ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder, chromName);

					annotateWithNumbers = new AnnotateWithNumbers(outputFolder, chromName, permutationNumber2RandomlyGeneratedDataHashMap, runNumber, numberofPermutationsinThisRun, writePermutationBasedandParametricBasedAnnotationResultMode, Commons.ZERO, permutationNumberList.size(), permutationNumberList, tfIntervalTree, ucscRefSeqGenesIntervalTree, AnnotationType.DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION, geneId2ListofKeggPathwayNumberMap, overlapDefinition);
					allMapsWithNumbers = pool.invoke(annotateWithNumbers);

					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_ANNOTATION);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_KEGGPATHWAY_ANNOTATION);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION);
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION);

					allMapsWithNumbers = null;
					tfIntervalTree = null;
					ucscRefSeqGenesIntervalTree = null;
					// New Functionality END

					System.gc();
					System.runFinalization();

				}

				
				
				endTimeOnlyAnnotationPermutationsForEachChromosome = System.currentTimeMillis();
				
				GlanetRunner.appendLog("Annotation of Permutations has took " + (float)((endTimeOnlyAnnotationPermutationsForEachChromosome-startTimeOnlyAnnotationPermutationsForEachChromosome)/1000) + " seconds.");
				logger.info("Annotation of Permutations has took " + (float)((endTimeOnlyAnnotationPermutationsForEachChromosome-startTimeOnlyAnnotationPermutationsForEachChromosome)/1000) + " seconds.");
				/********************************************************************************************************/
				/***************************** ANNOTATE PERMUTATIONS ENDS ***********************************************/
				/********************************************************************************************************/

				endTimeEverythingIncludedAnnotationPermutationsForEachChromosome = System.currentTimeMillis();
				GlanetRunner.appendLog("RunNumber: " + runNumber + " For Chromosome: " + chromName.convertEnumtoString() + " Annotation of " + numberofPermutationsinThisRun + " permutations where each of them has " + chromosomeBaseOriginalInputLines.size() + "  intervals took  " + (float)((endTimeEverythingIncludedAnnotationPermutationsForEachChromosome - startTimeEverythingIncludedAnnotationPermutationsForEachChromosome)/1000) + " seconds.");
				GlanetRunner.appendLog("******************************************************************************************");
				
				logger.info("RunNumber: " + runNumber + " For Chromosome: " + chromName.convertEnumtoString() + " Annotation of " + numberofPermutationsinThisRun + " permutations where each of them has " + chromosomeBaseOriginalInputLines.size() + "  intervals took  " + (float)((endTimeEverythingIncludedAnnotationPermutationsForEachChromosome - startTimeEverythingIncludedAnnotationPermutationsForEachChromosome)/1000) + " seconds.");
				logger.info("******************************************************************************************");
				
				
				permutationNumber2RandomlyGeneratedDataHashMap.clear();
				permutationNumber2RandomlyGeneratedDataHashMap = null;
				annotateWithNumbers = null;
				annotateDnaseTFHistoneWithNumbers = null;
				
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

		if (pool.isTerminated()) {
			GlanetRunner.appendLog("ForkJoinPool is terminated ");
			logger.info("ForkJoinPool is terminated ");
		}

		endTimeAllPermutationsAllChromosomes = System.currentTimeMillis();

		GlanetRunner.appendLog("RUN_NUMBER: " + runNumber + " NUMBER_OF_PERMUTATIONS:  " + numberofPermutationsinThisRun + " took " + (float)((endTimeAllPermutationsAllChromosomes - startTimeAllPermutationsAllChromosomes)/1000) + " seconds.");
		logger.info("RUN_NUMBER: " + runNumber + " NUMBER_OF_PERMUTATIONS:  " + numberofPermutationsinThisRun + " took " + (float)((endTimeAllPermutationsAllChromosomes - startTimeAllPermutationsAllChromosomes)/1000) + " seconds.");

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
		if (dnaseAnnotationType.doDnaseAnnotation()){
			//convert(accumulatedAllMapsDnaseTFHistoneWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap(), dnase2AllKMap, originalDnase2KMap, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER);
			convert(accumulatedAllMapsWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap(), dnase2AllKMap, originalDnase2KMap, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER);
		}
		
		if (tfAnnotationType.doTFAnnotation()){
		//	convert(accumulatedAllMapsDnaseTFHistoneWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(), tfbs2AllKMap, originalTfbs2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(), tfbs2AllKMap, originalTfbs2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
		}
		
		if (histoneAnnotationType.doHistoneAnnotation()){
		//	convert(accumulatedAllMapsDnaseTFHistoneWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap(), histone2AllKMap, originalHistone2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			convert(accumulatedAllMapsWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap(), histone2AllKMap, originalHistone2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
		}
		
		// Gene 
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberGeneNumber2KMap(), gene2AllKMap, originalGene2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGIT_PERMUTATIONNUMBER_10DIGIT_GENENUMBER);

		// UserDefinedGeneSet starts
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap(), exonBasedUserDefinedGeneSet2AllKMap, originalExonBasedUserDefinedGeneSet2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap(), regulationBasedUserDefinedGeneSet2AllKMap, originalRegulationBasedUserDefinedGeneSet2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap(), allBasedUserDefinedGeneSet2AllKMap, originalAllBasedUserDefinedGeneSet2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER);
		//UserDefinedGeneSet ends

		// UserDefinedLibrary starts
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberElementTypeNumberElementNumber2KMap(), elementTypeNumberElementNumber2AllKMap, originalElementTypeNumberElementNumber2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
		// UserDefinedLibrary ends

		convert(accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(), exonBasedKeggPathway2AllKMap, originalExonBasedKeggPathway2KMap, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(), regulationBasedKeggPathway2AllKMap, originalRegulationBasedKeggPathway2KMap, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(), allBasedKeggPathway2AllKMap, originalAllBasedKeggPathway2KMap, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap(), tfExonBasedKeggPathway2AllKMap, originalTfExonBasedKeggPathway2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap(), tfRegulationBasedKeggPathway2AllKMap, originalTfRegulationBasedKeggPathway2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap(), tfAllBasedKeggPathway2AllKMap, originalTfAllBasedKeggPathway2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap(), tfCellLineExonBasedKeggPathway2AllKMap, originalTfCellLineExonBasedKeggPathway2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap(), tfCellLineRegulationBasedKeggPathway2AllKMap, originalTfCellLineRegulationBasedKeggPathway2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap(), tfCellLineAllBasedKeggPathway2AllKMap, originalTfCellLineAllBasedKeggPathway2KMap, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
		/*************************************************************************************************************************/
		/***************************************** CONVERT ends*******************************************************************/
		/*************************************************************************************************************************/

		/*************************************************************************************************************************/
		/********************** WRITE PERMUTATION BASED ANNOTATION RESULTS starts*************************************************/
		/*************************************************************************************************************************/
		// Permutation Based Results
		// BufferedWriterHashMap are really needed?
		// Why created BufferedWriters are not created in append mode?
		if (writePermutationBasedAnnotationResultMode.isWritePermutationBasedAnnotationResultMode()) {
			
		
			permutationBasedResultDirectory = outputFolder + Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + Commons.RESULTS + System.getProperty("file.separator");

			if (dnaseAnnotationType.doDnaseAnnotation()) {
				
				// DNase
				Map<Integer, BufferedWriter> permutationNumber2DnaseBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				
				// Dnase
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsDnaseTFHistoneWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap(), permutationNumber2DnaseBufferedWriterHashMap, AnnotationType.DO_DNASE_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator"), Commons.DNASE, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER);
				closeBufferedWriters(permutationNumber2DnaseBufferedWriterHashMap);
				
				permutationNumber2DnaseBufferedWriterHashMap = null;
			}

			if (histoneAnnotationType.doHistoneAnnotation()) {
				
				// Histone
				Map<Integer, BufferedWriter> permutationNumber2HistoneBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				
				// Histone
				writeAnnotationstoFiles_ElementNumberCellLineNumber(permutationBasedResultDirectory, accumulatedAllMapsDnaseTFHistoneWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap(), permutationNumber2HistoneBufferedWriterHashMap, AnnotationType.DO_HISTONE_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator"), Commons.HISTONE, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2HistoneBufferedWriterHashMap);
				
				permutationNumber2HistoneBufferedWriterHashMap = null;
			}

			if (tfAnnotationType.doTFAnnotation() && !(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && !(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())) {
				
				// TF
				Map<Integer, BufferedWriter> permutationNumber2TfbsBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				
				// Transcription Factor
				writeAnnotationstoFiles_ElementNumberCellLineNumber(permutationBasedResultDirectory, accumulatedAllMapsDnaseTFHistoneWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(), permutationNumber2TfbsBufferedWriterHashMap, AnnotationType.DO_TF_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator"), Commons.TF, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);
				
				permutationNumber2TfbsBufferedWriterHashMap = null;
			}

			if (userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()) {
				
				// UserDefinedGeneSet
				Map<Integer, BufferedWriter> permutationNumber2ExonBasedUserDefinedGeneSetBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2RegulationBasedUserDefinedGeneSetBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2AllBasedUserDefinedGeneSetBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				
				// Exon Based User Defined GeneSet
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedUserDefinedGeneSetNumber2KMap(), permutationNumber2ExonBasedUserDefinedGeneSetBufferedWriterHashMap, AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "exonBased" + System.getProperty("file.separator"), Commons.EXON_BASED, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER);
				closeBufferedWriters(permutationNumber2ExonBasedUserDefinedGeneSetBufferedWriterHashMap);

				// Regulation Based User Defined GeneSet
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedUserDefinedGeneSetNumber2KMap(), permutationNumber2RegulationBasedUserDefinedGeneSetBufferedWriterHashMap, AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator"), Commons.REGULATION_BASED, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER);
				closeBufferedWriters(permutationNumber2RegulationBasedUserDefinedGeneSetBufferedWriterHashMap);

				// All Based User Defined GeneSet
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedUserDefinedGeneSetNumber2KMap(), permutationNumber2AllBasedUserDefinedGeneSetBufferedWriterHashMap, AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator"), Commons.ALL_BASED, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER);
				closeBufferedWriters(permutationNumber2AllBasedUserDefinedGeneSetBufferedWriterHashMap);
				
				permutationNumber2ExonBasedUserDefinedGeneSetBufferedWriterHashMap = null;
				permutationNumber2RegulationBasedUserDefinedGeneSetBufferedWriterHashMap = null;
				permutationNumber2AllBasedUserDefinedGeneSetBufferedWriterHashMap = null;
			}

			if (userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()) {
				// UserDefinedLibrary
				// @todo better naming is necesssary
				Map<Integer, BufferedWriter> permutationNumber2ElementTypeElementNameBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();

				// UserDefinedLibrary
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberElementTypeNumberElementNumber2KMap(), permutationNumber2ElementTypeElementNameBufferedWriterHashMap, AnnotationType.DO_USER_DEFINED_LIBRARY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator"), Commons.USER_DEFINED_LIBRARY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
				closeBufferedWriters(permutationNumber2ElementTypeElementNameBufferedWriterHashMap);
				
				permutationNumber2ElementTypeElementNameBufferedWriterHashMap = null;
			}

			if (keggPathwayAnnotationType.doKEGGPathwayAnnotation() && 
					!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
					!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())) {
				
				// KEGG Pathway
				Map<Integer, BufferedWriter> permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();
				Map<Integer, BufferedWriter> permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer, BufferedWriter>();


				// Exon Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(), permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "exonBased" + System.getProperty("file.separator"), Commons.EXON_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);

				// Regulation Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(), permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator"), Commons.REGULATION_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);

				// All Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(), permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator"), Commons.ALL_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);
				
				
				permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = null;
				
			}

			if (tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
					!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())) {
				
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
				writeAnnotationstoFiles_ElementNumberCellLineNumber(outputFolder, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(), permutationNumber2TfbsBufferedWriterHashMap, AnnotationType.DO_TF_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator"), Commons.TF, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);

				// Exon Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(), permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "exonBased" + System.getProperty("file.separator"), Commons.EXON_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);

				// Regulation Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(), permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator"), Commons.REGULATION_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);

				// All Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(), permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator"), Commons.ALL_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);

				// TF and Exon Based Kegg Pathway
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap(), permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfExonBased" + System.getProperty("file.separator"), Commons.TF_EXON_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap);

				// TF and Regulation Based Kegg Pathway
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap(), permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfRegulationBased" + System.getProperty("file.separator"), Commons.TF_REGULATION_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap);

				// TF and All Based Kegg Pathway
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap(), permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfAllBased" + System.getProperty("file.separator"), Commons.TF_ALL_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap);
				
				permutationNumber2TfbsBufferedWriterHashMap = null;

				permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = null;
				
				permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap = null;
				
				
				
			} else if (!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
					tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()) {
				
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
				writeAnnotationstoFiles_ElementNumberCellLineNumber(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(), permutationNumber2TfbsBufferedWriterHashMap, AnnotationType.DO_TF_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator"), Commons.TF, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);

				// Exon Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(), permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "exonBased" + System.getProperty("file.separator"), Commons.EXON_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);

				// Regulation Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(), permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator"), Commons.REGULATION_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);

				// All Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(), permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator"), Commons.ALL_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);

				// Tf and Cell Line and Exon Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap(), permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineExonBased" + System.getProperty("file.separator"), Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap);

				// Tf and Cell Line and Regulation Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap(), permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineRegulationBased" + System.getProperty("file.separator"), Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap);

				// Tf and Cell Line and All Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap(), permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineAllBased" + System.getProperty("file.separator"), Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap);
				
				permutationNumber2TfbsBufferedWriterHashMap = null;

				permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = null;
				
				permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap = null;
				permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap = null;
				
				
			

			} else if (tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
					tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()) {
				
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
				writeAnnotationstoFiles_ElementNumberCellLineNumber(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(), permutationNumber2TfbsBufferedWriterHashMap, AnnotationType.DO_TF_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator"), Commons.TF, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);

				// ExonKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathwayNumber2KMap(), permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "exonBased" + System.getProperty("file.separator"), Commons.EXON_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);

				// RegulationKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathwayNumber2KMap(), permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator"), Commons.REGULATION_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);

				// AllKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathwayNumber2KMap(), permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator"), Commons.ALL_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);

				// TF ExonKEGG
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberExonBasedKeggPathwayNumber2KMap(), permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfExonBased" + System.getProperty("file.separator"), Commons.TF_EXON_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap);

				// TF RegulationKEGG
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberRegulationBasedKeggPathwayNumber2KMap(), permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfRegulationBased" + System.getProperty("file.separator"), Commons.TF_REGULATION_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap);

				// TF AllKEGG
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberAllBasedKeggPathwayNumber2KMap(), permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfAllBased" + System.getProperty("file.separator"), Commons.TF_ALL_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap);

				// TF CellLine ExonKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberExonBasedKeggPathwayNumber2KMap(), permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineExonBased" + System.getProperty("file.separator"), Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap);

				// TF CellLine RegulationKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberRegulationBasedKeggPathwayNumber2KMap(), permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineRegulationBased" + System.getProperty("file.separator"), Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap);

				// Tf CellLine AllKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory, accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumberAllBasedKeggPathwayNumber2KMap(), permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineAllBased" + System.getProperty("file.separator"), Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY, GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
				closeBufferedWriters(permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap);
				
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

	public static void writeToBeCollectedNumberofOverlapsForUserDefinedLibrary(String outputFolder, TIntObjectMap<String> elementTypeNumber2ElementTypeMap, TIntIntMap originalElementTypeNumberElementNumber2KMap, TIntObjectMap<TIntList> elementTypeNumberElementNumber2AllKMap, String runName) {

		String elementType;
		int elementTypeNumber;

		TIntObjectMap<TIntIntMap> elementTypeNumber2ElementNumber2KMap = new TIntObjectHashMap<TIntIntMap>();
		TIntObjectMap<TIntObjectMap<TIntList>> elementTypeNumber2ElementNumber2AllKMap = new TIntObjectHashMap<TIntObjectMap<TIntList>>();

		// Initialization
		for (TIntObjectIterator<String> it = elementTypeNumber2ElementTypeMap.iterator(); it.hasNext();) {

			it.advance();

			elementTypeNumber = it.key();
			elementType = it.value();

			elementTypeNumber2ElementNumber2KMap.put(elementTypeNumber, new TIntIntHashMap());

			// Pay attention
			// We didn't initialize TIntList
			elementTypeNumber2ElementNumber2AllKMap.put(elementTypeNumber, new TIntObjectHashMap<TIntList>());

		}// End of each elementTypeNumber

		// Fill the first argument using the second argument
		// Fill elementTypeBased elementNumber2KMap and elementNumber2AllKMap
		UserDefinedLibraryUtility.fillElementTypeNumberBasedMaps(elementTypeNumber2ElementNumber2KMap, originalElementTypeNumberElementNumber2KMap);
		UserDefinedLibraryUtility.fillElementTypeNumberBasedMaps(elementTypeNumber2ElementNumber2AllKMap, elementTypeNumberElementNumber2AllKMap);

		// For each elementTypeNumber map write
		for (TIntObjectIterator<TIntIntMap> it = elementTypeNumber2ElementNumber2KMap.iterator(); it.hasNext();) {
			it.advance();

			elementTypeNumber = it.key();
			TIntIntMap elementNumber2KMap = it.value();

			elementType = elementTypeNumber2ElementTypeMap.get(elementTypeNumber);

			TIntObjectMap<TIntList> elementNumber2AllKMap = elementTypeNumber2ElementNumber2AllKMap.get(elementTypeNumber);

			writeToBeCollectedNumberofOverlaps(outputFolder, elementNumber2KMap, elementNumber2AllKMap, Commons.TO_BE_COLLECTED_USER_DEFINED_LIBRARY_NUMBER_OF_OVERLAPS + elementType + System.getProperty("file.separator") + Commons.RUNS_DIRECTORY, runName);

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

		try {
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + toBePolledDirectoryName + "_" + runNumber + ".txt"));

			for (TIntIntIterator it = originalPermutationNumberRemovedMixedNumber2KMap.iterator(); it.hasNext();) {

				it.advance();
				permutationNumberRemovedMixedNumber = it.key();
				originalNumberofOverlaps = it.value();

				// debug starts
				if (permutationNumberRemovedMixedNumber < 0) {
					System.out.println("there is a situation 3");
					System.out.println(permutationNumberRemovedMixedNumber);
				}
				// debug ends

				bufferedWriter.write(permutationNumberRemovedMixedNumber + "\t" + originalNumberofOverlaps + "|");

				permutationSpecificNumberofOverlapsList = permutationNumberRemovedMixedNumber2AllKMap.get(permutationNumberRemovedMixedNumber);

				if (permutationSpecificNumberofOverlapsList != null) {

					for (TIntIterator it2 = permutationSpecificNumberofOverlapsList.iterator(); it2.hasNext();) {
						bufferedWriter.write(it2.next() + ",");
					}

				}

				bufferedWriter.write(System.getProperty("line.separator"));

				// if permutationNumberofOverlapsList is null
				// do nothing

			}// End of outer loop

			bufferedWriter.close();
		} catch (IOException e) {

			logger.error(e.toString());
		}

	}

	// TIntIntMap TIntObjectMap<TIntList> version ends

	public static void writeToBeCollectedNumberofOverlaps(String outputFolder, TLongIntMap originalPermutationNumberRemovedMixedNumber2KMap, TLongObjectMap<TIntList> permutationNumberRemovedMixedNumber2AllKMap, String toBePolledDirectoryName, String runNumber) {
		long permutationNumberRemovedMixedNumber;
		int originalNumberofOverlaps;

		TIntList permutationSpecificNumberofOverlapsList;

		BufferedWriter bufferedWriter = null;

		try {
			bufferedWriter = new BufferedWriter(FileOperations.createFileWriter(outputFolder + toBePolledDirectoryName + "_" + runNumber + ".txt"));

			for (TLongIntIterator it = originalPermutationNumberRemovedMixedNumber2KMap.iterator(); it.hasNext();) {

				it.advance();
				permutationNumberRemovedMixedNumber = it.key();
				originalNumberofOverlaps = it.value();

				bufferedWriter.write(permutationNumberRemovedMixedNumber + "\t" + originalNumberofOverlaps + "|");

				permutationSpecificNumberofOverlapsList = permutationNumberRemovedMixedNumber2AllKMap.get(permutationNumberRemovedMixedNumber);

				if (permutationSpecificNumberofOverlapsList != null) {

					for (TIntIterator it2 = permutationSpecificNumberofOverlapsList.iterator(); it2.hasNext();) {
						bufferedWriter.write(it2.next() + ",");
					}

				}

				bufferedWriter.write(System.getProperty("line.separator"));

				// if permutationNumberofOverlapsList is null
				// do nothing
			}// End of outer loop

			bufferedWriter.close();
		} catch (IOException e) {

			logger.error(e.toString());
		}

	}

	public static void writeJavaRunTimeMemoryInformation() {
		logger.info("Java runtime max memory: " + (java.lang.Runtime.getRuntime().maxMemory()/Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE) + "\t" + "MBs");
		logger.info("Java runtime total memory: " + (java.lang.Runtime.getRuntime().totalMemory()/Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE) + "\t" + "MBs");
		logger.info("Java runtime free memory: " + (java.lang.Runtime.getRuntime().freeMemory()/Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE) + "\t" + "MBs");
		logger.info("Java runtime available processors: " + java.lang.Runtime.getRuntime().availableProcessors());

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
		
		/***********************************************************************************/
		/**************Memory Usage Before Enrichment***************************************/
		/***********************************************************************************/
		//logger.info("Memory Used Before Enrichment" + "\t" + ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE) +   "\t" + "MBs");
		/***********************************************************************************/
		/**************Memory Usage Before Enrichment***************************************/
		/***********************************************************************************/
	

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];

		// jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if (jobName.isEmpty()) {
			jobName = Commons.NO_NAME;
		}
		// jobName ends

		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");
		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");
		String givenInputDataFolder = outputFolder + Commons.GIVENINPUTDATA + System.getProperty("file.separator");

		int overlapDefinition = Integer.parseInt(args[CommandLineArguments.NumberOfBasesRequiredForOverlap.value()]);

		
		// Set the number of total permutations
		int numberofTotalPermutations = Integer.parseInt(args[CommandLineArguments.NumberOfPermutation.value()]);

		// set the number of permutations in each run
		int numberofPermutationsInEachRun = Integer.parseInt(args[CommandLineArguments.NumberOfPermutationsInEachRun.value()]);

		// Set the Generate Random Data Mode
		GenerateRandomDataMode generateRandomDataMode = GenerateRandomDataMode.convertStringtoEnum(args[CommandLineArguments.GenerateRandomDataMode.value()]);

		// Set the Write Mode of Generated Random Data
		WriteGeneratedRandomDataMode writeGeneratedRandomDataMode = WriteGeneratedRandomDataMode.convertStringtoEnum(args[CommandLineArguments.WriteGeneratedRandomDataMode.value()]);

		// Set the Write Mode of Permutation Based and Parametric Based
		// Annotation Result
		WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode = WritePermutationBasedandParametricBasedAnnotationResultMode.convertStringtoEnum(args[CommandLineArguments.WritePermutationBasedandParametricBasedAnnotationResultMode.value()]);

		// Set the Write Mode of the Permutation Based Annotation Result
		WritePermutationBasedAnnotationResultMode writePermutationBasedAnnotationResultMode = WritePermutationBasedAnnotationResultMode.convertStringtoEnum(args[CommandLineArguments.WritePermutationBasedAnnotationResultMode.value()]);

		// Dnase Annotation, DO or DO_NOT
		AnnotationType dnaseAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.DnaseAnnotation.value()]);

		// Histone Annotation, DO or DO_NOT
		AnnotationType histoneAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.HistoneAnnotation.value()]);

		// TF Annotation, DO or DO_NOT
		AnnotationType tfAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.TfAnnotation.value()]);
		
		//DO_GENE_ANNOTATION
		AnnotationType geneAnnotationType =AnnotationType.convertStringtoEnum(args[CommandLineArguments.GeneAnnotation.value()]);
		
		// KEGG Pathway Annotation, DO or DO_NOT
		AnnotationType keggPathwayAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.KeggPathwayAnnotation.value()]);

		// TFKEGGPathway Annotation, DO or DO_NOT
		AnnotationType tfKeggPathwayAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.TfAndKeggPathwayAnnotation.value()]);

		// TFCellLineKEGGPathway Annotation, DO or DO_NOT
		AnnotationType tfCellLineKeggPathwayAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()]);

		
		
		// 18 FEB 2015
		// performEnrichment is not used since GLANETRunner calls
		// Enrichment.main() function in case of performEnrichment is DO_ENRICH
		/*********************************************************************************/
		/************************** USER DEFINED GENESET *********************************/
		/*********************************************************************************/
		// User Defined GeneSet Enrichment, DO or DO_NOT
		AnnotationType userDefinedGeneSetAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.UserDefinedGeneSetAnnotation.value()]);

		String userDefinedGeneSetInputFile = args[CommandLineArguments.UserDefinedGeneSetInput.value()];

		GeneInformationType geneInformationType = GeneInformationType.convertStringtoEnum(args[CommandLineArguments.UserDefinedGeneSetGeneInformation.value()]);

		String userDefinedGeneSetName = args[CommandLineArguments.UserDefinedGeneSetName.value()];
		/*********************************************************************************/
		/************************** USER DEFINED GENESET *********************************/
		/*********************************************************************************/

		
		
		/***********************************************************************************/
		/************************** USER DEFINED LIBRARY ***********************************/
		// User Defined Library Annotation, DO or DO_NOT
		AnnotationType userDefinedLibraryAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.UserDefinedLibraryAnnotation.value()]);
		/************************** USER DEFINED LIBRARY ***********************************/
		/***********************************************************************************/

		writeJavaRunTimeMemoryInformation();

		// Random Class for generating small values instead of zero valued p
		// values
		// Random random = new Random();

		/***********************************************************************************************/
		/************************** READ ORIGINAL INPUT LINES STARTS ***********************************/
		// SET the Input Data File
		String inputDataFileName = givenInputDataFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE_0BASED_START_END_GRCh37_p13;

		List<InputLine> originalInputLines = new ArrayList<InputLine>();

		// Read original input data lines in to a list
		Enrichment.readOriginalInputDataLines(originalInputLines, inputDataFileName);
		/************************** READ ORIGINAL INPUT LINES ENDS *************************************/
		/***********************************************************************************************/

		/***********************************************************************************************/
		/********************* DELETE OLD FILES STARTS *************************************************/
		String annotationForPermutationsOutputDirectory = outputFolder + Commons.ANNOTATION_FOR_PERMUTATIONS;
		List<String> notToBeDeleted = new ArrayList<String>();
		// FileOperations.deleteDirectoriesandFilesUnderThisDirectory(annotateOutputBaseDirectoryName,notToBeDeleted);
		FileOperations.deleteOldFiles(annotationForPermutationsOutputDirectory, notToBeDeleted);

		String toBeDeletedDirectoryName = outputFolder + Commons.ENRICHMENT_DIRECTORY;
		FileOperations.deleteOldFiles(toBeDeletedDirectoryName);
		/********************* DELETE OLD FILES ENDS ***************************************************/
		/***********************************************************************************************/

		
		
		/**********************************************************************************************/
		/********************* FILL GENEID 2 USER DEFINED GENESET NUMBER MAP STARTS *******************/
		/**********************************************************************************************/
		TShortObjectMap<String> userDefinedGeneSetNumber2UserDefinedGeneSetNameMap = new TShortObjectHashMap<String>();
		// used in filling geneId2ListofUserDefinedGeneSetNumberMap
		TObjectShortMap<String> userDefinedGeneSetName2UserDefinedGeneSetNumberMap = new TObjectShortHashMap<String>();

		TIntObjectMap<TShortList> geneId2ListofUserDefinedGeneSetNumberMap = new TIntObjectHashMap<TShortList>();

		if (userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()) {
			UserDefinedGeneSetUtility.createNcbiGeneId2ListofUserDefinedGeneSetNumberMap(dataFolder, userDefinedGeneSetInputFile, geneInformationType, userDefinedGeneSetName2UserDefinedGeneSetNumberMap, userDefinedGeneSetNumber2UserDefinedGeneSetNameMap, geneId2ListofUserDefinedGeneSetNumberMap);
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
		TIntObjectMap<TShortList> geneId2KeggPathwayNumberMap = new TIntObjectHashMap<TShortList>();
		TObjectShortMap<String> keggPathwayName2KeggPathwayNumberMap = new TObjectShortHashMap<String>();

		if (keggPathwayAnnotationType.doKEGGPathwayAnnotation() || tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() || tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()) {

			// all_possible_keggPathwayName_2_keggPathwayNumber_map.txt
			KeggPathwayUtility.fillKeggPathwayName2KeggPathwayNumberMap(dataFolder, Commons.ALL_POSSIBLE_NAMES_KEGGPATHWAY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_KEGGPATHWAY_NAME_2_NUMBER_OUTPUT_FILENAME, keggPathwayName2KeggPathwayNumberMap);
			KeggPathwayUtility.createNcbiGeneId2KeggPathwayNumberMap(dataFolder, Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, geneId2KeggPathwayNumberMap, keggPathwayName2KeggPathwayNumberMap);

		}
		/**********************************************************************************************/
		/********************* FILL GENEID 2 KEGG PATHWAY NUMBER MAP ENDS ****************************/
		/*********************************************************************************************/

		
		
		/***********************************************************************************************/
		/****************************** USER DEFINED LIBRARY *******************************************/
		/********************* FILL ElementTypeNumber2ElementTypeMap STARTS ****************************/
		TIntObjectMap<String> elementTypeNumber2ElementTypeMap = null;

		if (userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()) {

			elementTypeNumber2ElementTypeMap = new TIntObjectHashMap<String>();

			UserDefinedLibraryUtility.fillNumber2NameMap(elementTypeNumber2ElementTypeMap, dataFolder, Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_NUMBER_2_NAME_OUTPUT_FILENAME);

		}
		/********************* FILL ElementTypeNumber2ElementTypeMap ENDS ******************************/
		/****************************** USER DEFINED LIBRARY *******************************************/
		/***********************************************************************************************/

		
		/************************************************************************************************/
		/******************************* CALCULATE NUMBER OF RUNS STARTS ********************************/
		// for loop starts
		// NUMBER_OF_PERMUTATIONS has to be multiple of 1000 like 1000, 5000,
		// 10000, 50000, 100000
		int numberofRuns = 0;
		int numberofRemainedPermutations = 0;
		String runName;

		// In case of numberofPermutationsInEachRun is greater than numberofTotalPermutations
		if (numberofPermutationsInEachRun > numberofTotalPermutations) {
			numberofPermutationsInEachRun = numberofTotalPermutations;
		}

		numberofRuns = numberofTotalPermutations / numberofPermutationsInEachRun;
		numberofRemainedPermutations = numberofTotalPermutations % numberofPermutationsInEachRun;

		// Increase numberofRuns by 1 for remainder permutations less than
		// Commons.NUMBER_OF_PERMUTATIONS_IN_EACH_RUN
		if (numberofRemainedPermutations > 0) {
			numberofRuns += 1;
		}
		/******************************* CALCULATE NUMBER OF RUNS ENDS ********************************/
		/**********************************************************************************************/

		
		
		/**********************************************************************************************/
		/********************* FOR LOOP FOR RUN NUMBERS STARTS ****************************************/
		for (int runNumber = 1; runNumber <= numberofRuns; runNumber++) {

			GlanetRunner.appendLog("**************	" + runNumber + ". Run" + "	******************	starts");
			logger.info("**************	" + runNumber + ". Run" + "	******************	starts");

			runName = jobName + "_" + Commons.RUN + runNumber;

			/***********************************************************************************************/
			/********************** INITIALIZATION OF NUMBER2K MAPS for ORIGINAL DATA STARTS ***************/
			/*********************** NUMBER OF OVERLAPS FOR ORIGINAL DATA STARTS ***************************/
			/***********************************************************************************************/
			/********************* INITIALIZATION OF NUMBER2AllK MAPS for PERMUTATION DATA STARTS **********/
			/*********************** NUMBER OF OVERLAPS FOR ALL PERMUTATIONS STARTS ************************/
			/***********************************************************************************************/
			// ElementNumber2K
			// Annotation of original data 
			// number of overlaps: k out of n for original data
			// Annotation of original data has permutation number zero
			
			// ElementNumber2AllK
			// Annotation of each permutation's randomly generated data 
			// number of overlaps: k out of n for all permutations
						
			/********************** INITIALIZATION TO NULL *************************************************/
			//DNase
			TIntIntMap originalDnase2KMap = null;
			TIntObjectMap<TIntList> dnase2AllKMap = null;
			
			//TF
			TIntIntMap originalTfbs2KMap = null;
			TIntObjectMap<TIntList> tfbs2AllKMap = null;
			
			//Histone
			TIntIntMap originalHistone2KMap = null;
			TIntObjectMap<TIntList> histone2AllKMap  = null;
			
			//Gene
			TIntIntMap originalGene2KMap  = null;
			TIntObjectMap<TIntList> gene2AllKMap = null;
			
			//UserDefinedGeneSet
			TIntIntMap originalExonBasedUserDefinedGeneSet2KMap = null;
			TIntIntMap originalRegulationBasedUserDefinedGeneSet2KMap = null;
			TIntIntMap originalAllBasedUserDefinedGeneSet2KMap = null;

			TIntObjectMap<TIntList> exonBasedUserDefinedGeneSet2AllKMap = null;
			TIntObjectMap<TIntList> regulationBasedUserDefinedGeneSet2AllKMap = null;
			TIntObjectMap<TIntList> allBasedUserDefinedGeneSet2AllKMap = null;
			
			//UserDefinedLibrary
			TIntIntMap originalElementTypeNumberElementNumber2KMap = null;
			TIntObjectMap<TIntList> elementTypeNumberElementNumber2AllKMap = null;
			
			//KEGG Pathway
			TIntIntMap originalExonBasedKeggPathway2KMap = null;
			TIntIntMap originalRegulationBasedKeggPathway2KMap = null;
			TIntIntMap originalAllBasedKeggPathway2KMap = null;
			
			TIntObjectMap<TIntList> exonBasedKeggPathway2AllKMap = null;
			TIntObjectMap<TIntList> regulationBasedKeggPathway2AllKMap = null;
			TIntObjectMap<TIntList> allBasedKeggPathway2AllKMap = null;
			
			// TF and KEGG Pathway Enrichment
			TIntIntMap originalTfExonBasedKeggPathway2KMap = null;
			TIntIntMap originalTfRegulationBasedKeggPathway2KMap = null;
			TIntIntMap originalTfAllBasedKeggPathway2KMap = null;

			TIntObjectMap<TIntList> tfExonBasedKeggPathway2AllKMap = null;
			TIntObjectMap<TIntList> tfRegulationBasedKeggPathway2AllKMap = null;
			TIntObjectMap<TIntList> tfAllBasedKeggPathway2AllKMap = null;

			// TF and CellLine and KEGG Pathway Enrichment
			TLongIntMap originalTfCellLineExonBasedKeggPathway2KMap = null;
			TLongIntMap originalTfCellLineRegulationBasedKeggPathway2KMap = null;
			TLongIntMap originalTfCellLineAllBasedKeggPathway2KMap = null;
			
			TLongObjectMap<TIntList> tfCellLineExonBasedKeggPathway2AllKMap = null;
			TLongObjectMap<TIntList> tfCellLineRegulationBasedKeggPathway2AllKMap = null;
			TLongObjectMap<TIntList> tfCellLineAllBasedKeggPathway2AllKMap = null;
			/********************** INITIALIZATION TO NULL *************************************************/

			
			//DNase
			if (dnaseAnnotationType.doDnaseAnnotation()) {
				originalDnase2KMap = new TIntIntHashMap();
				dnase2AllKMap = new TIntObjectHashMap<TIntList>();
			}
			
			//TF
			if (tfAnnotationType.doTFAnnotation()){
				originalTfbs2KMap = new TIntIntHashMap();
				tfbs2AllKMap = new TIntObjectHashMap<TIntList>();
			}
			
			//Histone
			if (histoneAnnotationType.doHistoneAnnotation()){
				originalHistone2KMap = new TIntIntHashMap();
				histone2AllKMap = new TIntObjectHashMap<TIntList>();
			}
			
			//Gene
			if (geneAnnotationType.doGeneAnnotation()){
				originalGene2KMap = new TIntIntHashMap();
				gene2AllKMap = new TIntObjectHashMap<TIntList>();
			}
			
			// User Defined GeneSet
			if(userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){
				originalExonBasedUserDefinedGeneSet2KMap = new TIntIntHashMap();
				originalRegulationBasedUserDefinedGeneSet2KMap = new TIntIntHashMap();
				originalAllBasedUserDefinedGeneSet2KMap = new TIntIntHashMap();

				exonBasedUserDefinedGeneSet2AllKMap = new TIntObjectHashMap<TIntList>();
				regulationBasedUserDefinedGeneSet2AllKMap = new TIntObjectHashMap<TIntList>();
				allBasedUserDefinedGeneSet2AllKMap = new TIntObjectHashMap<TIntList>();
			}

			// User Defined Library
			if (userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){
				originalElementTypeNumberElementNumber2KMap = new TIntIntHashMap();
				elementTypeNumberElementNumber2AllKMap = new TIntObjectHashMap<TIntList>();
			}
			
			
			// KEGGPathway
			if (keggPathwayAnnotationType.doKEGGPathwayAnnotation()){
				
				originalExonBasedKeggPathway2KMap = new TIntIntHashMap();
				originalRegulationBasedKeggPathway2KMap = new TIntIntHashMap();
				originalAllBasedKeggPathway2KMap = new TIntIntHashMap();
				
				exonBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
				regulationBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
				allBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();

			}
			
			
			// TF KEGGPathway Enrichment
			if (tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()){
				
				//TF
				originalTfbs2KMap = new TIntIntHashMap();
				tfbs2AllKMap = new TIntObjectHashMap<TIntList>();
				
				//KEGGPathway
				originalExonBasedKeggPathway2KMap = new TIntIntHashMap();
				originalRegulationBasedKeggPathway2KMap = new TIntIntHashMap();
				originalAllBasedKeggPathway2KMap = new TIntIntHashMap();
				
				exonBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
				regulationBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
				allBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
		
				//TF KEGGPathway
				originalTfExonBasedKeggPathway2KMap = new TIntIntHashMap();
				originalTfRegulationBasedKeggPathway2KMap = new TIntIntHashMap();
				originalTfAllBasedKeggPathway2KMap = new TIntIntHashMap();

				tfExonBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
				tfRegulationBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
				tfAllBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();

			}
			
			// TF CellLine KEGGPathway Enrichment
			if (tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){
				
				//TF
				originalTfbs2KMap = new TIntIntHashMap();
				tfbs2AllKMap = new TIntObjectHashMap<TIntList>();
				
				//KEGGPathway
				originalExonBasedKeggPathway2KMap = new TIntIntHashMap();
				originalRegulationBasedKeggPathway2KMap = new TIntIntHashMap();
				originalAllBasedKeggPathway2KMap = new TIntIntHashMap();
				
				exonBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
				regulationBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
				allBasedKeggPathway2AllKMap = new TIntObjectHashMap<TIntList>();
		
				//TF CellLine KEGGPathway
				originalTfCellLineExonBasedKeggPathway2KMap = new TLongIntHashMap();
				originalTfCellLineRegulationBasedKeggPathway2KMap = new TLongIntHashMap();
				originalTfCellLineAllBasedKeggPathway2KMap = new TLongIntHashMap();
				
				tfCellLineExonBasedKeggPathway2AllKMap = new TLongObjectHashMap<TIntList>();
				tfCellLineRegulationBasedKeggPathway2AllKMap = new TLongObjectHashMap<TIntList>();
				tfCellLineAllBasedKeggPathway2AllKMap = new TLongObjectHashMap<TIntList>();
				
			}
			/*********************** NUMBER OF OVERLAPS FOR ORIGINAL DATA ENDS *****************************/
			/********************** INITIALIZATION OF NUMBER2K MAPS for ORIGINAL DATA ENDS *****************/
			/***********************************************************************************************/
			/*********************** NUMBER OF OVERLAPS FOR ALL PERMUTATIONS ENDS **************************/
			/********************* INITIALIZATION OF NUMBER2AllK MAPS for PERMUTATION DATA ENDS ************/
			/***********************************************************************************************/
			
			
			
			/***********************************************************************************************/
			/************************** ANNOTATE PERMUTATIONS STARTS ***************************************/
			/***********************************************************************************************/
			GlanetRunner.appendLog("Concurrent programming has started.");
			logger.info("Concurrent programming has started.");
			// Concurrent Programming
			// First Generate Random Data
			// Then Annotate Permutations (Random Data) concurrently
			// elementName2AllKMap and originalElementName2KMap will be filled here
			if ((runNumber == numberofRuns) && (numberofRemainedPermutations > 0)) {
				Enrichment.annotateAllPermutationsInThreads(outputFolder, dataFolder, Commons.NUMBER_OF_AVAILABLE_PROCESSORS, runNumber, numberofRemainedPermutations, numberofPermutationsInEachRun, originalInputLines, dnase2AllKMap, tfbs2AllKMap, histone2AllKMap, gene2AllKMap, exonBasedUserDefinedGeneSet2AllKMap, regulationBasedUserDefinedGeneSet2AllKMap, allBasedUserDefinedGeneSet2AllKMap, elementTypeNumberElementNumber2AllKMap, exonBasedKeggPathway2AllKMap, regulationBasedKeggPathway2AllKMap, allBasedKeggPathway2AllKMap, tfExonBasedKeggPathway2AllKMap, tfRegulationBasedKeggPathway2AllKMap, tfAllBasedKeggPathway2AllKMap, tfCellLineExonBasedKeggPathway2AllKMap, tfCellLineRegulationBasedKeggPathway2AllKMap, tfCellLineAllBasedKeggPathway2AllKMap, generateRandomDataMode, writeGeneratedRandomDataMode, writePermutationBasedandParametricBasedAnnotationResultMode, writePermutationBasedAnnotationResultMode, originalDnase2KMap, originalTfbs2KMap, originalHistone2KMap, originalGene2KMap, originalExonBasedUserDefinedGeneSet2KMap, originalRegulationBasedUserDefinedGeneSet2KMap, originalAllBasedUserDefinedGeneSet2KMap, originalElementTypeNumberElementNumber2KMap, originalExonBasedKeggPathway2KMap, originalRegulationBasedKeggPathway2KMap, originalAllBasedKeggPathway2KMap, originalTfExonBasedKeggPathway2KMap, originalTfRegulationBasedKeggPathway2KMap, originalTfAllBasedKeggPathway2KMap, originalTfCellLineExonBasedKeggPathway2KMap, originalTfCellLineRegulationBasedKeggPathway2KMap, originalTfCellLineAllBasedKeggPathway2KMap, dnaseAnnotationType, histoneAnnotationType, tfAnnotationType, geneAnnotationType, userDefinedGeneSetAnnotationType, userDefinedLibraryAnnotationType, keggPathwayAnnotationType, tfKeggPathwayAnnotationType, tfCellLineKeggPathwayAnnotationType, overlapDefinition, geneId2KeggPathwayNumberMap, geneId2ListofUserDefinedGeneSetNumberMap, elementTypeNumber2ElementTypeMap);
			} else {
				Enrichment.annotateAllPermutationsInThreads(outputFolder, dataFolder, Commons.NUMBER_OF_AVAILABLE_PROCESSORS, runNumber, numberofPermutationsInEachRun, numberofPermutationsInEachRun, originalInputLines, dnase2AllKMap, tfbs2AllKMap, histone2AllKMap, gene2AllKMap, exonBasedUserDefinedGeneSet2AllKMap, regulationBasedUserDefinedGeneSet2AllKMap, allBasedUserDefinedGeneSet2AllKMap, elementTypeNumberElementNumber2AllKMap, exonBasedKeggPathway2AllKMap, regulationBasedKeggPathway2AllKMap, allBasedKeggPathway2AllKMap, tfExonBasedKeggPathway2AllKMap, tfRegulationBasedKeggPathway2AllKMap, tfAllBasedKeggPathway2AllKMap, tfCellLineExonBasedKeggPathway2AllKMap, tfCellLineRegulationBasedKeggPathway2AllKMap, tfCellLineAllBasedKeggPathway2AllKMap, generateRandomDataMode, writeGeneratedRandomDataMode, writePermutationBasedandParametricBasedAnnotationResultMode, writePermutationBasedAnnotationResultMode, originalDnase2KMap, originalTfbs2KMap, originalHistone2KMap, originalGene2KMap, originalExonBasedUserDefinedGeneSet2KMap, originalRegulationBasedUserDefinedGeneSet2KMap, originalAllBasedUserDefinedGeneSet2KMap, originalElementTypeNumberElementNumber2KMap, originalExonBasedKeggPathway2KMap, originalRegulationBasedKeggPathway2KMap, originalAllBasedKeggPathway2KMap, originalTfExonBasedKeggPathway2KMap, originalTfRegulationBasedKeggPathway2KMap, originalTfAllBasedKeggPathway2KMap, originalTfCellLineExonBasedKeggPathway2KMap, originalTfCellLineRegulationBasedKeggPathway2KMap, originalTfCellLineAllBasedKeggPathway2KMap, dnaseAnnotationType, histoneAnnotationType, tfAnnotationType, geneAnnotationType, userDefinedGeneSetAnnotationType, userDefinedLibraryAnnotationType, keggPathwayAnnotationType, tfKeggPathwayAnnotationType, tfCellLineKeggPathwayAnnotationType, overlapDefinition, geneId2KeggPathwayNumberMap, geneId2ListofUserDefinedGeneSetNumberMap, elementTypeNumber2ElementTypeMap);
			}
			GlanetRunner.appendLog("Concurrent programming has ended.");
			logger.info("Concurrent programming has ended.");
			/**********************************************************************************************/
			/************************** ANNOTATE PERMUTATIONS ENDS ****************************************/
			/**********************************************************************************************/

			
			
			/***********************************************************************************************/
			/************************** WRITE TO BE COLLECTED RESULTS STARTS *******************************/
			/***********************************************************************************************/
			if (dnaseAnnotationType.doDnaseAnnotation()) {
				writeToBeCollectedNumberofOverlaps(outputFolder, originalDnase2KMap, dnase2AllKMap, Commons.TO_BE_COLLECTED_DNASE_NUMBER_OF_OVERLAPS, runName);
			}

			if (histoneAnnotationType.doHistoneAnnotation()) {
				writeToBeCollectedNumberofOverlaps(outputFolder, originalHistone2KMap, histone2AllKMap, Commons.TO_BE_COLLECTED_HISTONE_NUMBER_OF_OVERLAPS, runName);
			}

			if (	tfAnnotationType.doTFAnnotation() && 
					!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
					!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())) {

				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfbs2KMap, tfbs2AllKMap, Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, runName);
			}

			if (geneAnnotationType.doGeneAnnotation()){
				
				writeToBeCollectedNumberofOverlaps(outputFolder, originalGene2KMap, gene2AllKMap, Commons.TO_BE_COLLECTED_GENE_NUMBER_OF_OVERLAPS, runName);
			}
			
			
			// UserDefinedGeneset
			if (userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()) {

				final String TO_BE_COLLECTED_EXON_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_USERDEFINED_GENESET_COMMON + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.ENRICHMENT_EXONBASED_USERDEFINED_GENESET + "_" + userDefinedGeneSetName;
				final String TO_BE_COLLECTED_REGULATION_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_USERDEFINED_GENESET_COMMON + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.ENRICHMENT_REGULATIONBASED_USERDEFINED_GENESET + "_" + userDefinedGeneSetName;
				final String TO_BE_COLLECTED_ALL_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_USERDEFINED_GENESET_COMMON + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.ENRICHMENT_ALLBASED_USERDEFINED_GENESET + "_" + userDefinedGeneSetName;

				writeToBeCollectedNumberofOverlaps(outputFolder, originalExonBasedUserDefinedGeneSet2KMap, exonBasedUserDefinedGeneSet2AllKMap, TO_BE_COLLECTED_EXON_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalRegulationBasedUserDefinedGeneSet2KMap, regulationBasedUserDefinedGeneSet2AllKMap, TO_BE_COLLECTED_REGULATION_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalAllBasedUserDefinedGeneSet2KMap, allBasedUserDefinedGeneSet2AllKMap, TO_BE_COLLECTED_ALL_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS, runName);

			}

			// UserDefinedLibrary
			if (userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()) {

				writeToBeCollectedNumberofOverlapsForUserDefinedLibrary(outputFolder, elementTypeNumber2ElementTypeMap, originalElementTypeNumberElementNumber2KMap, elementTypeNumberElementNumber2AllKMap, runName);

			}

			if (	keggPathwayAnnotationType.doKEGGPathwayAnnotation() && 
					!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
					!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())) {

				writeToBeCollectedNumberofOverlaps(outputFolder, originalExonBasedKeggPathway2KMap, exonBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalRegulationBasedKeggPathway2KMap, regulationBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalAllBasedKeggPathway2KMap, allBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
			}

			if (	tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
					!(tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation())) {

				// TF
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfbs2KMap, tfbs2AllKMap, Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, runName);

				// KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder, originalExonBasedKeggPathway2KMap, exonBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalRegulationBasedKeggPathway2KMap, regulationBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalAllBasedKeggPathway2KMap, allBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

				// TF KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfExonBasedKeggPathway2KMap, tfExonBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfRegulationBasedKeggPathway2KMap, tfRegulationBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfAllBasedKeggPathway2KMap, tfAllBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

			}

			if (	!(tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()) && 
					tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()) {

				// TF
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfbs2KMap, tfbs2AllKMap, Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, runName);

				// KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder, originalExonBasedKeggPathway2KMap, exonBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalRegulationBasedKeggPathway2KMap, regulationBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalAllBasedKeggPathway2KMap, allBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

				// TF CELLLINE KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfCellLineExonBasedKeggPathway2KMap, tfCellLineExonBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfCellLineRegulationBasedKeggPathway2KMap, tfCellLineRegulationBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfCellLineAllBasedKeggPathway2KMap, tfCellLineAllBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

			}

			if (	tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && 
					tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()) {

				// TF
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfbs2KMap, tfbs2AllKMap, Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS, runName);

				// KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder, originalExonBasedKeggPathway2KMap, exonBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalRegulationBasedKeggPathway2KMap, regulationBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalAllBasedKeggPathway2KMap, allBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

				// TF KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfExonBasedKeggPathway2KMap, tfExonBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfRegulationBasedKeggPathway2KMap, tfRegulationBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfAllBasedKeggPathway2KMap, tfAllBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

				// TF CELLLINE KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfCellLineExonBasedKeggPathway2KMap, tfCellLineExonBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfCellLineRegulationBasedKeggPathway2KMap, tfCellLineRegulationBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);
				writeToBeCollectedNumberofOverlaps(outputFolder, originalTfCellLineAllBasedKeggPathway2KMap, tfCellLineAllBasedKeggPathway2AllKMap, Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, runName);

			}
			// Calculate Empirical P Values and Bonferroni Corrected Empirical P
			// Values ends
			/***********************************************************************************************/
			/************************** WRITE TO BE COLLECTED RESULTS ENDS *********************************/
			/***********************************************************************************************/

			
			
			/***********************************************************************************************/
			/********************************** FREE MEMORY STARTS *****************************************/
			/********************* MAPS FOR ORIGINAL DATA STARTS *******************************************/
			/***********************************************************************************************/
			// DNASE TF HISTONE
			originalDnase2KMap = null;
			originalTfbs2KMap = null;
			originalHistone2KMap = null;

			// Gene
			originalGene2KMap = null;

			// USERDEFINEDGENESET
			originalExonBasedUserDefinedGeneSet2KMap = null;
			originalRegulationBasedUserDefinedGeneSet2KMap = null;
			originalAllBasedUserDefinedGeneSet2KMap = null;

			// USERDEFINEDLIBRARY
			originalElementTypeNumberElementNumber2KMap = null;

			// KEGG PATHWAY
			originalExonBasedKeggPathway2KMap = null;
			originalRegulationBasedKeggPathway2KMap = null;
			originalAllBasedKeggPathway2KMap = null;

			// TF KEGGPATHWAY
			originalTfExonBasedKeggPathway2KMap = null;
			originalTfRegulationBasedKeggPathway2KMap = null;
			originalTfAllBasedKeggPathway2KMap = null;

			// TF CELLLINE KEGGPATHWAY
			originalTfCellLineExonBasedKeggPathway2KMap = null;
			originalTfCellLineRegulationBasedKeggPathway2KMap = null;
			originalTfCellLineAllBasedKeggPathway2KMap = null;
			/********************* MAPS FOR ORIGINAL DATA ENDS *******************************************/

			/********************* MAPS FOR PERMUTATIONS DATA STARTS *************************************/
			// functionalElementName based
			// number of overlaps: k out of n for all permutations

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

			GlanetRunner.appendLog("**************	" + runNumber + ". Run" + "	******************	ends");
			logger.info("**************	" + runNumber + ". Run" + "	******************	ends");

		}//End of for each run number
		/*********************************************************************************************/
		/********************* FOR LOOP FOR RUN NUMBERS ENDS *****************************************/
		/*********************************************************************************************/
		
		
		/***********************************************************************************/
		/**********************FREE AUXILIARY MAPS starts***********************************/
		/***********************************************************************************/
		userDefinedGeneSetNumber2UserDefinedGeneSetNameMap = null;
		userDefinedGeneSetName2UserDefinedGeneSetNumberMap = null;
		geneId2ListofUserDefinedGeneSetNumberMap = null;
		
		geneId2KeggPathwayNumberMap = null;
		keggPathwayName2KeggPathwayNumberMap = null;
		
		elementTypeNumber2ElementTypeMap = null;
		/***********************************************************************************/
		/**********************FREE AUXILIARY MAPS ends*************************************/
		/***********************************************************************************/
		
		/***********************************************************************************/
		/**************Memory Usage After Enrichment****************************************/
		/***********************************************************************************/
		//logger.info("Memory Used After Enrichment" + "\t" + ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/Commons.NUMBER_OF_BYTES_IN_A_MEGABYTE) +   "\t" + "MBs");
		/***********************************************************************************/
		/**************Memory Usage After Enrichment****************************************/
		/***********************************************************************************/
	

	}// End of main function

}
