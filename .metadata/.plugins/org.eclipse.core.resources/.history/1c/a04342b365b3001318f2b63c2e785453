/**
 * @author Burcak Otlu
 * Jul 26, 2013
 * 2:03:10 PM
 * 2013
 *
 * 
 */
package empiricalpvalues;

import generate.randomdata.RandomDataGenerator;
import hg19.GRCh37Hg19Chromosome;
import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

import keggpathway.ncbigenes.KeggPathwayUtility;
import mapabilityandgc.ChromosomeBasedGCArray;
import mapabilityandgc.ChromosomeBasedMapabilityArray;
import annotate.intervals.parametric.AnnotateGivenIntervalsWithGivenParameters;
import auxiliary.FunctionalElement;
import auxiliary.NumberofComparisons;
import auxiliary.NumberofComparisonsforBonferroniCorrectionCalculation;

import common.Commons;


public class AnnotatePermutationsUsingForkJoin_withEnrichmentChoices {
	
	static class GenerateRandomData extends RecursiveTask<Map<Integer,List<InputLine>>>{

		/**
		 * 
		 */
		private static final long serialVersionUID = -5508399455444935122L;
		private final int chromSize;
		private final String chromName;
		private final List<InputLine> chromosomeBasedOriginalInputLines;
		private final int repeatNumber;
		private final int NUMBER_OF_PERMUTATIONS;
		
		private final String generateRandomDataMode;
		private final String writeGeneratedRandomDataMode;
		
		private final int lowIndex;
		private final int highIndex;
		
		private final List<AnnotationTask> listofAnnotationTasks;

		private final GCCharArray gcCharArray;
		private final MapabilityFloatArray mapabilityFloatArray;

				
		public GenerateRandomData(int chromSize, String chromName, List<InputLine> chromosomeBasedOriginalInputLines, int repeatNumber,int NUMBER_OF_PERMUTATIONS, String generateRandomDataMode, String writeGeneratedRandomDataMode,int lowIndex, int highIndex, List<AnnotationTask> listofAnnotationTasks,GCCharArray gcCharArray, MapabilityFloatArray mapabilityFloatArray) {
			
			this.chromSize = chromSize;
			this.chromName = chromName;
			this.chromosomeBasedOriginalInputLines = chromosomeBasedOriginalInputLines;
			this.repeatNumber = repeatNumber;
			this.NUMBER_OF_PERMUTATIONS = NUMBER_OF_PERMUTATIONS;
			
			this.generateRandomDataMode = generateRandomDataMode;
			this.writeGeneratedRandomDataMode = writeGeneratedRandomDataMode;
			
			this.lowIndex = lowIndex;
			this.highIndex = highIndex;
			
			this.listofAnnotationTasks = listofAnnotationTasks;
					
			this.gcCharArray = gcCharArray;
			this.mapabilityFloatArray = mapabilityFloatArray;
		}

		
		protected Map<Integer,List<InputLine>> compute() {
			
			int middleIndex;
			Map<Integer,List<InputLine>> rightRandomlyGeneratedData;
			Map<Integer,List<InputLine>> leftRandomlyGeneratedData;
	        
	    	Integer permutationNumber;
			AnnotationTask annotationTask;
					
			//DIVIDE
			if (highIndex-lowIndex>8){
			 	middleIndex = lowIndex + (highIndex - lowIndex) / 2;
			 	GenerateRandomData left  = new GenerateRandomData(chromSize, chromName, chromosomeBasedOriginalInputLines, repeatNumber,NUMBER_OF_PERMUTATIONS, generateRandomDataMode,writeGeneratedRandomDataMode,lowIndex,middleIndex,listofAnnotationTasks,gcCharArray,mapabilityFloatArray);
			 	GenerateRandomData right = new GenerateRandomData(chromSize, chromName, chromosomeBasedOriginalInputLines, repeatNumber,NUMBER_OF_PERMUTATIONS, generateRandomDataMode,writeGeneratedRandomDataMode,middleIndex,highIndex,listofAnnotationTasks,gcCharArray,mapabilityFloatArray);
	            left.fork();
	            rightRandomlyGeneratedData = right.compute();
	            leftRandomlyGeneratedData  = left.join();
	            
	            //Add the contents of leftRandomlyGeneratedData into rightRandomlyGeneratedData
	            mergeMaps(rightRandomlyGeneratedData,leftRandomlyGeneratedData);
	            
	            leftRandomlyGeneratedData = null;
	            return rightRandomlyGeneratedData;
	 		}
			//CONQUER
			else {
				
				Map<Integer,List<InputLine>> randomlyGeneratedDataMap = new HashMap<Integer,List<InputLine>>();
				 	
				for(int i=lowIndex; i<highIndex; i++){
					 annotationTask = listofAnnotationTasks.get(i);
					 permutationNumber = annotationTask.getPermutationNumber();
					      
				     System.out.println("Generate Random Data For Task: " + i + "\t" +chromName);	
				     
				     randomlyGeneratedDataMap.put(permutationNumber, RandomDataGenerator.generateRandomData(gcCharArray,mapabilityFloatArray,chromSize, chromName,chromosomeBasedOriginalInputLines, ThreadLocalRandom.current(), generateRandomDataMode));
				      
				     //Write Generated Random Data
				     if(Commons.WRITE_GENERATED_RANDOM_DATA.equals(writeGeneratedRandomDataMode)){
				    	writeGeneratedRandomData(randomlyGeneratedDataMap.get(permutationNumber),repeatNumber,permutationNumber,NUMBER_OF_PERMUTATIONS);
				     }
						
				}//End of FOR
					
				return randomlyGeneratedDataMap;
			}

		}//End of compute method
		
		//Add the content of leftMap to rightMap
		//Clear and null leftMap
		protected void mergeMaps(Map<Integer,List<InputLine>> rightRandomlyGeneratedDataMap,Map<Integer,List<InputLine>> leftRandomlyGeneratedDataMap){
			
			for(Map.Entry<Integer, List<InputLine>> entry: leftRandomlyGeneratedDataMap.entrySet()){
				Integer permutationNumber = entry.getKey();
				
				if (!rightRandomlyGeneratedDataMap.containsKey(permutationNumber)){
					rightRandomlyGeneratedDataMap.put(permutationNumber, entry.getValue());
				}
			}//End of for
			
			leftRandomlyGeneratedDataMap.clear();
			leftRandomlyGeneratedDataMap = null;
				
		}
	       
		
		protected void writeGeneratedRandomData(List<InputLine> randomlyGeneratedData,int repeatNumber,int permutationNumber, int NUMBER_OF_PERMUTATIONS){
			 FileWriter fileWriter;
		     BufferedWriter bufferedWriter;
		     InputLine randomlyGeneratedInputLine;
		        
		 	try {
		 		
				fileWriter = new FileWriter(Commons.E_DOKTORA_ECLIPSE_WORKSPACE_ANNOTATE_PERMUTATIONS + Commons.RANDOMLY_GENERATED_DATA_FOLDER  + Commons.PERMUTATION + ((repeatNumber-1)*NUMBER_OF_PERMUTATIONS+ permutationNumber)  + "_" + Commons.RANDOMLY_GENERATED_DATA  +".txt",true);
				bufferedWriter = new BufferedWriter(fileWriter);
				
				for(int i=0;i<randomlyGeneratedData.size(); i++){
					randomlyGeneratedInputLine = randomlyGeneratedData.get(i);
					bufferedWriter.write(randomlyGeneratedInputLine.getChrName() +"\t" + randomlyGeneratedInputLine.getLow() + "\t" + randomlyGeneratedInputLine.getHigh() +"\n");
					bufferedWriter.flush();
				}//End of FOR
				
				bufferedWriter.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}//End of GenerateRandomData Class
	
		

	static class Annotate extends RecursiveTask<AllMaps>{
		
		
	private static final long serialVersionUID = 2919115895116169524L;
	private final int chromSize;
	private final String chromName;
	private final Map<Integer,List<InputLine>> randomlyGeneratedDataMap;
	private final int repeatNumber;
	private final int NUMBER_OF_PERMUTATIONS;
	
	private final String writePermutationBasedandParametricBasedAnnotationResultMode;
	
	private final List<AnnotationTask> listofAnnotationTasks;
	private final IntervalTree intervalTree;
	private final IntervalTree ucscRefSeqGenesIntervalTree;
	
	private final String annotationType;
	private final String tfandKeggPathwayEnrichmentType;
	
	private final int lowIndex;
	private final int highIndex;
	
	private final Map<String,List<String>> geneId2KeggPathwayMap;
	
	
	
	
	public Annotate(int chromSize, String chromName, Map<Integer,List<InputLine>> randomlyGeneratedDataMap, int repeatNumber,int NUMBER_OF_PERMUTATIONS, String writePermutationBasedandParametricBasedAnnotationResultMode,int lowIndex, int highIndex, List<AnnotationTask> listofAnnotationTasks, IntervalTree intervalTree, IntervalTree ucscRefSeqGenesIntervalTree,String annotationType, String tfandKeggPathwayEnrichmentType,Map<String, List<String>> geneId2KeggPathwayMap) {
		this.chromSize = chromSize;
		this.chromName = chromName;
		this.randomlyGeneratedDataMap = randomlyGeneratedDataMap;
		this.repeatNumber = repeatNumber;
		this.NUMBER_OF_PERMUTATIONS = NUMBER_OF_PERMUTATIONS;
		
		this.writePermutationBasedandParametricBasedAnnotationResultMode = writePermutationBasedandParametricBasedAnnotationResultMode;
		
		this.lowIndex = lowIndex;
		this.highIndex = highIndex;
		
		this.listofAnnotationTasks = listofAnnotationTasks;
		this.intervalTree = intervalTree;
	
		//sent full when annotation for tf and ucsc refseq genes will be done
		//otherwise sent null
		this.ucscRefSeqGenesIntervalTree = ucscRefSeqGenesIntervalTree;
		
		this.annotationType = annotationType;	
		this.tfandKeggPathwayEnrichmentType = tfandKeggPathwayEnrichmentType;
		
		//geneId2KeggPathwayMap
		//sent full when annotation for tf and ucsc refseq genes will be done
		//otherwise sent null
		this.geneId2KeggPathwayMap = geneId2KeggPathwayMap;
		
	}
	
	
		
		protected AllMaps compute() {
			
			int middleIndex;
			AllMaps rightAllMaps;
	        AllMaps leftAllMaps;
	        
	    	AnnotationTask annotationTask;
			Integer permutationNumber;
			List<AllMaps> listofAllMaps;
			AllMaps allMaps;
				
			//DIVIDE
			if (highIndex-lowIndex>9){
			 	middleIndex = lowIndex + (highIndex - lowIndex) / 2;
	            Annotate left  = new Annotate(chromSize, chromName, randomlyGeneratedDataMap, repeatNumber,NUMBER_OF_PERMUTATIONS,writePermutationBasedandParametricBasedAnnotationResultMode,lowIndex,middleIndex,listofAnnotationTasks,intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2KeggPathwayMap);
	            Annotate right = new Annotate(chromSize, chromName, randomlyGeneratedDataMap, repeatNumber,NUMBER_OF_PERMUTATIONS,writePermutationBasedandParametricBasedAnnotationResultMode,middleIndex,highIndex,listofAnnotationTasks,intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2KeggPathwayMap);
	            left.fork();
	            rightAllMaps = right.compute();
	            leftAllMaps  = left.join();
	            combineAllMaps(leftAllMaps,rightAllMaps);
	            leftAllMaps= null;
	            return rightAllMaps;
			}
			//CONQUER
			else {
				
				listofAllMaps = new ArrayList<AllMaps>();
				allMaps = new AllMaps();
				 	
				for(int i=lowIndex; i<highIndex; i++){
					 annotationTask = listofAnnotationTasks.get(i);
					 permutationNumber = annotationTask.getPermutationNumber();
					      
				     System.out.println("Annotate Random Data For Task: " + i + "\t" +chromName + "\t" + annotationType);	
				     
				     //NEW FUNCTIONALITY HAS BEEN ADDED
				     if(Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT.equals(writePermutationBasedandParametricBasedAnnotationResultMode)){
				    	 listofAllMaps.add(AnnotateGivenIntervalsWithGivenParameters.annotatePermutationwithoutIO(repeatNumber,permutationNumber,NUMBER_OF_PERMUTATIONS,chromName,randomlyGeneratedDataMap.get(permutationNumber), intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2KeggPathwayMap));
				     }
				     
				     //NEW FUNCTIONALITY HAS BEEN ADDED
				     else if (Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT.equals(writePermutationBasedandParametricBasedAnnotationResultMode)){
				     	 listofAllMaps.add(AnnotateGivenIntervalsWithGivenParameters.annotatePermutationwithIO(repeatNumber,permutationNumber,NUMBER_OF_PERMUTATIONS,chromName,randomlyGeneratedDataMap.get(permutationNumber), intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2KeggPathwayMap));
				     }						
				}//End of FOR
					
				combineListofAllMaps(listofAllMaps,allMaps);
				listofAllMaps = null;
				return allMaps;
				
	
			}
		}		
		
		//Accumulate the allMaps in the left into allMaps in the right
		protected void combineListofAllMaps(List<AllMaps> listofAllMaps,AllMaps allMaps){
			for(int i =0; i<listofAllMaps.size(); i++){
				combineAllMaps(listofAllMaps.get(i), allMaps);
			}
		}
		
		
		//Accumulate leftMap in the rightMap
		//Accumulate number of overlaps 
		//based on permutationNumber and ElementName
		protected void  combineMaps(Map<String,Integer> leftMap, Map<String,Integer> rightMap){
			
			for(Map.Entry<String, Integer> entry: leftMap.entrySet()){
				String permutationNumberElementName = entry.getKey();
				Integer numberofOverlaps = entry.getValue();
				
				if (rightMap.get(permutationNumberElementName)==null){
					rightMap.put(permutationNumberElementName, numberofOverlaps);
				}else{
					rightMap.put(permutationNumberElementName, rightMap.get(permutationNumberElementName)+numberofOverlaps);
				}
			}
			
			leftMap.clear();
			leftMap = null;
			//deleteMap(leftMap);
	
		}
		
		
		protected void combineAllMaps(AllMaps leftAllMaps, AllMaps rightAllMaps) {
				
				//LEFT ALL MAPS
				Map<String,Integer> leftPermutationNumberDnaseCellLineName2KMap = leftAllMaps.getPermutationNumberDnaseCellLineName2KMap();
				Map<String,Integer> leftPermutationNumberTfbsNameCellLineName2KMap = leftAllMaps.getPermutationNumberTfNameCellLineName2KMap();
				Map<String,Integer> leftPermutationNumberHistoneNameCellLineName2KMap = leftAllMaps.getPermutationNumberHistoneNameCellLineName2KMap();
				
				Map<String,Integer> leftPermutationNumberExonBasedKeggPathway2KMap = leftAllMaps.getPermutationNumberExonBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberRegulationBasedKeggPathway2KMap = leftAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberAllBasedKeggPathway2KMap = leftAllMaps.getPermutationNumberAllBasedKeggPathway2KMap();
				
				Map<String,Integer> leftPermutationNumberTfExonBasedKeggPathway2KMap 		= leftAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberTfRegulationBasedKeggPathway2KMap 	= leftAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberTfAllBasedKeggPathway2KMap 		= leftAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap();

				Map<String,Integer> leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap 		= leftAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap 	= leftAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap 		= leftAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap();
				
			
				//RIGHT ALL MAPS
				Map<String,Integer> rightPermutationNumberDnaseCellLineName2KMap = rightAllMaps.getPermutationNumberDnaseCellLineName2KMap();
				Map<String,Integer> rightPermutationNumberTfbsNameCellLineName2KMap = rightAllMaps.getPermutationNumberTfNameCellLineName2KMap();
				Map<String,Integer> rightPermutationNumberHistoneNameCellLineName2KMap = rightAllMaps.getPermutationNumberHistoneNameCellLineName2KMap();
				
				Map<String,Integer> rightPermutationNumberExonBasedKeggPathway2KMap = rightAllMaps.getPermutationNumberExonBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberRegulationBasedKeggPathway2KMap = rightAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberAllBasedKeggPathway2KMap = rightAllMaps.getPermutationNumberAllBasedKeggPathway2KMap();
				
				Map<String,Integer> rightPermutationNumberTfExonBasedKeggPathway2KMap 		= rightAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberTfRegulationBasedKeggPathway2KMap = rightAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberTfAllBasedKeggPathway2KMap 		= rightAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap();

				Map<String,Integer> rightPermutationNumberTfCellLineExonBasedKeggPathway2KMap 		= rightAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap = rightAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberTfCellLineAllBasedKeggPathway2KMap 		= rightAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap();
					
			
				
				if (leftPermutationNumberDnaseCellLineName2KMap!=null){
					combineMaps(leftPermutationNumberDnaseCellLineName2KMap,rightPermutationNumberDnaseCellLineName2KMap);
					leftPermutationNumberDnaseCellLineName2KMap = null;
				}
				
				if(leftPermutationNumberTfbsNameCellLineName2KMap!=null){
					combineMaps(leftPermutationNumberTfbsNameCellLineName2KMap,rightPermutationNumberTfbsNameCellLineName2KMap);
					leftPermutationNumberTfbsNameCellLineName2KMap = null;
				}
				
				if(leftPermutationNumberHistoneNameCellLineName2KMap!=null){
					combineMaps(leftPermutationNumberHistoneNameCellLineName2KMap,rightPermutationNumberHistoneNameCellLineName2KMap);
					leftPermutationNumberHistoneNameCellLineName2KMap = null;
					
				}
				
				if(leftPermutationNumberExonBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberExonBasedKeggPathway2KMap,rightPermutationNumberExonBasedKeggPathway2KMap);
					leftPermutationNumberExonBasedKeggPathway2KMap = null;
					
				}
				
				if (leftPermutationNumberRegulationBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberRegulationBasedKeggPathway2KMap,rightPermutationNumberRegulationBasedKeggPathway2KMap);
					leftPermutationNumberRegulationBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberAllBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberAllBasedKeggPathway2KMap,rightPermutationNumberAllBasedKeggPathway2KMap);
					leftPermutationNumberAllBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap,rightPermutationNumberTfCellLineExonBasedKeggPathway2KMap);
					leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap,rightPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap);
					leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap,rightPermutationNumberTfCellLineAllBasedKeggPathway2KMap);
					leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap = null;
				}
			
				if (leftPermutationNumberTfExonBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfExonBasedKeggPathway2KMap,rightPermutationNumberTfExonBasedKeggPathway2KMap);
					leftPermutationNumberTfExonBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberTfRegulationBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfRegulationBasedKeggPathway2KMap,rightPermutationNumberTfRegulationBasedKeggPathway2KMap);
					leftPermutationNumberTfRegulationBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberTfAllBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfAllBasedKeggPathway2KMap,rightPermutationNumberTfAllBasedKeggPathway2KMap);
					leftPermutationNumberTfAllBasedKeggPathway2KMap = null;
				}
				
				
				//delete AllMaps
				//deleteAllMaps(leftAllMaps);
				leftAllMaps = null;
						
		}//End of combineAllMaps
		
		
		  
		
		protected void  deleteRandomlyGeneratedData(List<InputLine>randomlyGeneratedData){
			for(InputLine inputLine: randomlyGeneratedData){
				inputLine.setChrName(null);
				inputLine= null;
			}
			
			randomlyGeneratedData.clear();
		}
			
		
		protected void deleteMap(Map<String,Integer> map){
			if (map!=null){
				for(Map.Entry<String, Integer> entry: map.entrySet()){
					entry.setValue(null);
					entry= null;			
				}
				map= null;
			}
			
		}
		
		protected void deleteAllMaps(AllMaps allMaps){
			Map<String,Integer> map = allMaps.getPermutationNumberDnaseCellLineName2KMap();
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


	
	public void deleteOldFiles(){
		System.out.println("Start deleting old files...");
		//Delete old files before run 
		File folder = new File(Commons.E_DOKTORA_ECLIPSE_WORKSPACE_ANNOTATE_PERMUTATIONS);
		AnnotatePermutationsUsingForkJoin_withEnrichmentChoices.deleteOldFiles(folder);
		File file = new File(Commons.RANDOM_DATA_GENERATION_LOG_FILE);;
		AnnotatePermutationsUsingForkJoin_withEnrichmentChoices.deleteFile(file);
		System.out.println("Old files are deleted");
	
	}

	public static void deleteFile(File file){
		if(file.isFile()){
            file.delete();
        }
	}
	
	public static void deleteOldFiles(File folder){
		File[] files = folder.listFiles();
		 
	    for(File file: files){
	        if(file.isFile()){
	            file.delete();
	        }else if(file.isDirectory()) {
	        	//System.out.println("Folder Name: "+ file.getName() + " Absolute Path: " + file.getAbsolutePath());
	        	//Do not delete Folder "E:\eclipse_juno_workspace\Doktora\annotate\intervals\parametric\output\all_possible_names"
	        	if (!(file.getName().equals("all_possible_names"))){
	        		deleteOldFiles(file);
	        	}
	        }
	    }
		     
	}
	
	public void readOriginalInputDataLines(List<InputLine> originalInputLines, String inputFileName){
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;
		
		int indexofFirstTab;
		int indexofSecondTab;
		
		String chrName;
		int low;
		int high;
	
		System.out.println("Input data file name is: " + inputFileName);
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while( (strLine= bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				
				chrName = strLine.substring(0, indexofFirstTab);
				
				if (indexofSecondTab>0){
					low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				}else{
					low = Integer.parseInt(strLine.substring(indexofFirstTab+1));
					high = low;
				}
				
				InputLine originalInputLine = new InputLine(chrName, low, high);
				originalInputLines.add(originalInputLine);
			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public  void partitionDataChromosomeBased(List<InputLine> originalInputLines, Map<String,List<InputLine>> chromosomeBasedOriginalInputLines){
		InputLine line;
		String chrName;
		List<InputLine> list;
		
		
		for(int i = 0; i<originalInputLines.size(); i++){
			
			line = originalInputLines.get(i);
			chrName = line.getChrName();
			list = chromosomeBasedOriginalInputLines.get(chrName);
			
			if (list == null){
				list = new ArrayList<InputLine>();
				list.add(line);
				chromosomeBasedOriginalInputLines.put(chrName, list);
			}else{
				list.add(line);
				chromosomeBasedOriginalInputLines.put(chrName,list);
			}
		}
	}
	
	//convert permutation augmented name to only name
	//Fill elementName2ALLMap and originalElementName2KMap in convert methods	
	public void convert(Map<String,Integer> permutationDnase2KMap,Map<String,List<Integer>> elementName2AllKMap, Map<String,Integer> test_originalElementName2KMap){
		String permutationAugmentedName;
		Integer numberofOverlaps;
		int indexofFirstUnderscore;
		String name;
		String permutationNumber;
		
		List<Integer> list;
		
		for(Map.Entry<String, Integer> entry: permutationDnase2KMap.entrySet()){
			
			//example permutationAugmentedName PERMUTATION0_K562
			permutationAugmentedName = entry.getKey();
			numberofOverlaps = entry.getValue();
			
			indexofFirstUnderscore = permutationAugmentedName.indexOf('_');
			name = permutationAugmentedName.substring(indexofFirstUnderscore+1);

			//example permutationNumber PERMUTATION0
			permutationNumber = permutationAugmentedName.substring(0, indexofFirstUnderscore);
			
			if (Commons.PERMUTATION0.equals(permutationNumber)){
				test_originalElementName2KMap.put(name, numberofOverlaps);
			}else{
				list =elementName2AllKMap.get(name);
				
				if(list ==null){
					list = new ArrayList<Integer>();
					list.add(numberofOverlaps);
					elementName2AllKMap.put(name, list);
				}else{
					list.add(numberofOverlaps);
					elementName2AllKMap.put(name, list);
					
				}
			}
			
			
			
		}
	}
	
	public void fillMapfromMap(Map<String,Integer> toMap, Map<String,Integer> fromMap){
		String name;
		Integer numberofOverlaps;
		
		for(Map.Entry<String, Integer> entry: fromMap.entrySet()){
			name = entry.getKey();
			numberofOverlaps = entry.getValue();
			
			toMap.put(name, numberofOverlaps);
			
			
		}
	}
	
//	public void annotateOriginalInputData(String inputDataFileName,Map<String,Integer> originalDnase2KMap, Map<String,Integer> originalTfbs2KMap, Map<String,Integer> originalHistone2KMap, Map<String,Integer> originalExonBasedKeggPathway2KMap, Map<String,Integer> originalRegulationBasedKeggPathway2KMap){
//		AnnotateGivenIntervalsWithGivenParameters annotateIntervals = new AnnotateGivenIntervalsWithGivenParameters();
//		
//		AllName2KMaps name2KMap = annotateIntervals.annotateOriginalData(inputDataFileName);
//		
//		fillMapfromMap(originalDnase2KMap, name2KMap.getDnaseCellLineName2NumberofOverlapsMap());
//		fillMapfromMap(originalTfbs2KMap, name2KMap.getTfbsNameandCellLineName2NumberofOverlapsMap());
//		fillMapfromMap(originalHistone2KMap, name2KMap.getHistoneNameandCellLineName2NumberofOverlapsMap());
//		fillMapfromMap(originalExonBasedKeggPathway2KMap, name2KMap.getExonBasedKeggPathway2NumberofOverlapsMap());
//		fillMapfromMap(originalRegulationBasedKeggPathway2KMap, name2KMap.getRegulationBasedKeggPathway2NumberofOverlapsMap());
//		
//		
//	}
	


	//Empirical P Value and Bonferroni Corrected Empirical P Value
	//List<FunctionalElement> list is filled in this method
	//Using name2AccumulatedKMap and originalName2KMap
	public void calculateEmpricalPValues(Integer numberofComparisons, int numberofRepeats, int numberofPermutations,Map<String, List<Integer>> name2AccumulatedKMap,Map<String, Integer> originalName2KMap, List<FunctionalElement> list){
		
			
		String  originalName;
		Integer originalNumberofOverlaps;
		List<Integer> listofNumberofOverlaps;
		Integer numberofOverlaps;
		int  numberofPermutationsHavingOverlapsGreaterThanorEqualto = 0;
		Float empiricalPValue;
		Float bonferroniCorrectedEmpiricalPValue;
		
		FunctionalElement functionalElement;
					
		
		//only consider the names in the original name 2 k map
		for(Map.Entry<String, Integer> entry: originalName2KMap.entrySet()){
			originalName = entry.getKey();
			originalNumberofOverlaps = entry.getValue();
			
			listofNumberofOverlaps = name2AccumulatedKMap.get(originalName);

			//Initialise numberofPermutationsHavingOverlapsGreaterThanorEqualto to zero for each original name 
			numberofPermutationsHavingOverlapsGreaterThanorEqualto = 0;
			
			if (listofNumberofOverlaps!=null){
				for(int i =0; i<listofNumberofOverlaps.size(); i++){
					
					numberofOverlaps =listofNumberofOverlaps.get(i);
					
					if(numberofOverlaps >= originalNumberofOverlaps){
						numberofPermutationsHavingOverlapsGreaterThanorEqualto++;
					}
				}//end of for
			}//end of if
			
			empiricalPValue = (numberofPermutationsHavingOverlapsGreaterThanorEqualto * 1.0f)/(numberofRepeats * numberofPermutations);
			bonferroniCorrectedEmpiricalPValue = ((numberofPermutationsHavingOverlapsGreaterThanorEqualto* 1.0f)/(numberofRepeats * numberofPermutations)) * numberofComparisons;
			
			if(bonferroniCorrectedEmpiricalPValue>=1){
				bonferroniCorrectedEmpiricalPValue = 1.0f;
			}
			
			functionalElement = new FunctionalElement();
			functionalElement.setName(originalName);
			functionalElement.setEmpiricalPValue(empiricalPValue);
			functionalElement.setBonferroniCorrectedEmpiricalPValue(bonferroniCorrectedEmpiricalPValue);
			
			//18 FEB 2014
			functionalElement.setOriginalNumberofOverlaps(originalNumberofOverlaps);
			functionalElement.setNumberofPermutationsHavingOverlapsGreaterThanorEqualto(numberofPermutationsHavingOverlapsGreaterThanorEqualto);
			functionalElement.setNumberofPermutations(numberofRepeats * numberofPermutations);
			functionalElement.setNumberofComparisons(numberofComparisons);
			
			list.add(functionalElement);
				
		}//end of for
			
		
	}
		
		
		
	
	public void generateAnnotationTasks(String chromName, List<AnnotationTask> listofAnnotationTasks,int repeatNumber,int NUMBER_OF_PERMUTATIONS){	
			for(int permutationNumber = 1; permutationNumber<= NUMBER_OF_PERMUTATIONS; permutationNumber++){
				listofAnnotationTasks.add(new AnnotationTask(chromName, (repeatNumber-1)* NUMBER_OF_PERMUTATIONS+permutationNumber));
			}
	}
	
	
	public void generateAnnotationTaskforOriginalData(String chromName, List<AnnotationTask> listofAnnotationTasks,Integer originalDataPermutationNumber){
		listofAnnotationTasks.add(new AnnotationTask(chromName, 0));
	}
	
	public IntervalTree generateDnaseIntervalTree(String chromName){		
		return AnnotateGivenIntervalsWithGivenParameters.createDnaseIntervalTree(chromName);	
	}
	
	public IntervalTree generateTfbsIntervalTree(String chromName){		
		return AnnotateGivenIntervalsWithGivenParameters.createTfbsIntervalTree(chromName);	
	}
	
	public IntervalTree generateHistoneIntervalTree(String chromName){		
		return AnnotateGivenIntervalsWithGivenParameters.createHistoneIntervalTree(chromName);	
	}
	
	public IntervalTree generateUcscRefSeqGeneIntervalTree(String chromName){		
		return AnnotateGivenIntervalsWithGivenParameters.createUcscRefSeqGenesIntervalTree(chromName);	
	}
	
	public void generateIntervalTrees(String chromName, List<IntervalTree> listofIntervalTrees){
		IntervalTree dnaseIntervalTree;
		IntervalTree tfbsIntervalTree ;
		IntervalTree histoneIntervalTree;
		IntervalTree ucscRefSeqGeneIntervalTree;
		
				
		dnaseIntervalTree			= AnnotateGivenIntervalsWithGivenParameters.createDnaseIntervalTree(chromName);
		tfbsIntervalTree 			= AnnotateGivenIntervalsWithGivenParameters.createTfbsIntervalTree(chromName);
		histoneIntervalTree  		= AnnotateGivenIntervalsWithGivenParameters.createHistoneIntervalTree(chromName);
		ucscRefSeqGeneIntervalTree 	= AnnotateGivenIntervalsWithGivenParameters.createUcscRefSeqGenesIntervalTree(chromName);
		
		//order is important
		listofIntervalTrees.add(dnaseIntervalTree);
		listofIntervalTrees.add(tfbsIntervalTree);
		listofIntervalTrees.add(histoneIntervalTree);
		listofIntervalTrees.add(ucscRefSeqGeneIntervalTree);
		
	}

	public void closeBufferedWriters(Map<String,BufferedWriter> permutationNumber2BufferedWriterHashMap){
		
		BufferedWriter bufferedWriter = null;
		try {
			
			for(Map.Entry<String,BufferedWriter> entry: permutationNumber2BufferedWriterHashMap.entrySet() ){
				bufferedWriter = entry.getValue();
				bufferedWriter.close();				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeBuferedWriterswithIntegerKey(Map<Integer,BufferedWriter> permutationNumber2BufferedWriterHashMap){
		
		BufferedWriter bufferedWriter = null;
		try {
			
			for(Map.Entry<Integer,BufferedWriter> entry: permutationNumber2BufferedWriterHashMap.entrySet() ){
				bufferedWriter = entry.getValue();
				bufferedWriter.close();				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void writeAnnotationstoFiles(Map<String,Integer> name2KMap, Map<String,BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName, String extraFileName){
		
		String permutationNumberName;
		String permutationNumber;
		String name;
		
		int indexofFirstUnderscore;
		Integer numberofOverlaps;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		for(Map.Entry<String, Integer> entry: name2KMap.entrySet()){
			permutationNumberName = entry.getKey();
			numberofOverlaps = entry.getValue();
			
			indexofFirstUnderscore = permutationNumberName.indexOf('_');
			permutationNumber = permutationNumberName.substring(0, indexofFirstUnderscore);
			name =  permutationNumberName.substring(indexofFirstUnderscore+1);
			
			bufferedWriter = permutationNumber2BufferedWriterHashMap.get(permutationNumber) ;
			
			try {
				
				if (bufferedWriter==null){
						fileWriter = new FileWriter(Commons.E_DOKTORA_ECLIPSE_WORKSPACE_ANNOTATE_PERMUTATIONS + folderName + permutationNumber +  "_" + extraFileName + ".txt");
						bufferedWriter = new BufferedWriter(fileWriter);
						permutationNumber2BufferedWriterHashMap.put(permutationNumber, bufferedWriter);							
				}
				bufferedWriter.write(name +"\t" + numberofOverlaps +"\n");
				
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//End of for
		
	}
	
	//Accumulate chromosomeBasedName2KMap results in accumulatedName2KMap
	//Accumulate number of overlaps coming from each chromosome
	//based on permutationNumber and ElementName
	public static void accumulate(Map<String,Integer> chromosomeBasedName2KMap, Map<String,Integer> accumulatedName2KMap){
		String permutationNumberElementName;
		Integer numberofOverlaps;
		
		for(Map.Entry<String, Integer> entry: chromosomeBasedName2KMap.entrySet()){
			permutationNumberElementName = entry.getKey();
			numberofOverlaps = entry.getValue();
			
			
			if (accumulatedName2KMap.get(permutationNumberElementName)==null){
				accumulatedName2KMap.put(permutationNumberElementName, numberofOverlaps);
			}else{
				accumulatedName2KMap.put(permutationNumberElementName, accumulatedName2KMap.get(permutationNumberElementName) + numberofOverlaps);
				
			}
		}
		
	}
	
	
	
	public static void accumulate(AllMaps chromosomeBasedAllMaps, AllMaps accumulatedAllMaps){
		
		//Dnase
		accumulate(chromosomeBasedAllMaps.getPermutationNumberDnaseCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberDnaseCellLineName2KMap());
		
		//Tfbs
		accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNameCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberTfNameCellLineName2KMap());
		
		//Histone
		accumulate(chromosomeBasedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap());
		
		//Exon Based Kegg Pathway
		accumulate(chromosomeBasedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap());
		
		//Regulation Based Kegg Pathway
		accumulate(chromosomeBasedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap());
				
	}
	
	//Accumulate chromosomeBasedAllMaps in accumulatedAllMaps
	public static void accumulate(AllMaps chromosomeBasedAllMaps, AllMaps accumulatedAllMaps, String annotationType){
		
		if (Commons.DNASE_ANNOTATION.equals(annotationType)){
			//Dnase
			accumulate(chromosomeBasedAllMaps.getPermutationNumberDnaseCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberDnaseCellLineName2KMap());
		}else if (Commons.TFBS_ANNOTATION.equals(annotationType)){
			//Tfbs
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNameCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberTfNameCellLineName2KMap());
		}else if (Commons.HISTONE_ANNOTATION.equals(annotationType)){
			//Histone
			accumulate(chromosomeBasedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap());
		}else if (Commons.UCSC_REFSEQ_GENE_ANNOTATION.equals(annotationType)){
			//Exon Based Kegg Pathway
			accumulate(chromosomeBasedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap());
			
			//Regulation Based Kegg Pathway
			accumulate(chromosomeBasedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap());
			
			//All Based Kegg Pathway
			accumulate(chromosomeBasedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap());
	
		}else if (Commons.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION.equals(annotationType)){
			//TF and CellLine and Kegg Pathway Annotation
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap());
		}else if (Commons.TF_KEGG_PATHWAY_ANNOTATION.equals(annotationType)){
			//TF and Kegg Pathway Annotation
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap());
			//NEW FUNCIONALITY
		}
		
						
	}
	
	
	
	public void deleteIntervalTrees(List<IntervalTree> listofIntervalTrees){
		IntervalTree dnaseIntervalTree = listofIntervalTrees.get(0);
		IntervalTree tfbsIntervalTree = listofIntervalTrees.get(1);
		IntervalTree histoneIntervalTree = listofIntervalTrees.get(2);
		IntervalTree ucscRefSeqGenesIntervalTree = listofIntervalTrees.get(3);
		
		IntervalTree.deleteNodesofIntervalTree(dnaseIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree(tfbsIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree(histoneIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree(ucscRefSeqGenesIntervalTree.getRoot());
		
//		dnaseIntervalTree = new IntervalTree();
		dnaseIntervalTree 	= null;
		tfbsIntervalTree 	= null;
		histoneIntervalTree = null;
		ucscRefSeqGenesIntervalTree	= null;
	}
	
	
	public void deleteIntervalTree(IntervalTree intervalTree){
		
		IntervalTree.deleteNodesofIntervalTree(intervalTree.getRoot());
		intervalTree 	= null;
	}
	
	
	public void deleteAnnotationTasks(List<AnnotationTask> listofAnnotationTasks){
		for(AnnotationTask annotationTask : listofAnnotationTasks){
			annotationTask.setChromName(null);
			annotationTask.setPermutationNumber(null);
			annotationTask = null;
		}
	}
	
	public void deleteGCCharArray(char[] gcCharArray){
		gcCharArray = null;
	}
	
	public void deleteMapabilityFloatArray(float[] mapabilityFloatArray){
		mapabilityFloatArray = null;
	}
	
	
	
	//NEW FUNCIONALITY ADDED
	//First Generate random data concurrently
	//then annotate permutations concurrently
	//the tasks are executed
	//after all the parallel work is done
	//results are written to files
	public void annotateAllPermutationsInThreads(int NUMBER_OF_AVAILABLE_PROCESSORS,int NUMBER_OF_REPEATS, int NUMBER_OF_PERMUTATIONS,List<InputLine> allOriginalInputLines, Map<String,List<Integer>> dnase2AllKMap,Map<String,List<Integer>> tfbs2AllKMap,Map<String,List<Integer>> histone2AllKMap,Map<String,List<Integer>> exonBasedKeggPathway2AllKMap,Map<String,List<Integer>> regulationBasedKeggPathway2AllKMap,Map<String,List<Integer>> allBasedKeggPathway2AllKMap,Map<String,List<Integer>> tfExonBasedKeggPathway2AllKMap, Map<String,List<Integer>> tfRegulationBasedKeggPathway2AllKMap,Map<String,List<Integer>> tfAllBasedKeggPathway2AllKMap,Map<String,List<Integer>> tfCellLineExonBasedKeggPathway2AllKMap, Map<String,List<Integer>> tfCellLineRegulationBasedKeggPathway2AllKMap,Map<String,List<Integer>> tfCellLineAllBasedKeggPathway2AllKMap, String generateRandomDataMode, String writeGeneratedRandomDataMode,String writePermutationBasedandParametricBasedAnnotationResultMode,String writePermutationBasedAnnotationResultMode,Map<String,Integer> originalDnase2KMap,Map<String,Integer> originalTfbs2KMap,Map<String,Integer> originalHistone2KMap,Map<String,Integer> originalExonBasedKeggPathway2KMap,Map<String,Integer> originalRegulationBasedKeggPathway2KMap,Map<String,Integer> originalAllBasedKeggPathway2KMap, Map<String,Integer> originalTfExonBasedKeggPathway2KMap,Map<String,Integer> originalTfRegulationBasedKeggPathway2KMap,Map<String,Integer> originalTfAllBasedKeggPathway2KMap,Map<String,Integer> originalTfCellLineExonBasedKeggPathway2KMap,Map<String,Integer> originalTfCellLineRegulationBasedKeggPathway2KMap,Map<String,Integer> originalTfCellLineAllBasedKeggPathway2KMap, String dnaseEnrichment, String histoneEnrichment, String tfKeggPathwayEnrichment, String tfCellLineKeggPathwayEnrichment){
		
		AllMaps allMaps = new AllMaps();
		AllMaps accumulatedAllMaps = new AllMaps();
		
		accumulatedAllMaps.setPermutationNumberDnaseCellLineName2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberTfNameCellLineName2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberHistoneNameCellLineName2KMap(new HashMap<String,Integer>());
		
		accumulatedAllMaps.setPermutationNumberExonBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberRegulationBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberAllBasedKeggPathway2KMap(new HashMap<String,Integer>());
		
		//Will be used 	for Tf and KeggPathway enrichment or
		accumulatedAllMaps.setPermutationNumberTfExonBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberTfRegulationBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberTfAllBasedKeggPathway2KMap(new HashMap<String,Integer>());
			
		//Will be used 	for Tf and CellLine and KeggPathway enrichment
		accumulatedAllMaps.setPermutationNumberTfCellLineExonBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberTfCellLineAllBasedKeggPathway2KMap(new HashMap<String,Integer>());
	
					
		Map<String,BufferedWriter> permutationNumber2DnaseBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2TfbsBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2HistoneBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		
		Map<String,BufferedWriter> permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		
		//Will be used 	for Tf and KeggPathway enrichment or
		Map<String,BufferedWriter> permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		
		//Will be used 	for Tf and CellLine and KeggPathway enrichment
		Map<String,BufferedWriter> permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		
		
	
		Map<String,List<InputLine>> originalInputLinesMap = new HashMap<String,List<InputLine>>();
		
		//todo test it 
		//SecureRandom myrandom = new SecureRandom();

		List<AnnotationTask> listofAnnotationTasks 	= null;
		IntervalTree intervalTree 					= null;
		
		//For NEW FUNCTIONALITY
		IntervalTree tfIntervalTree 				= null;
		IntervalTree ucscRefSeqGenesIntervalTree 	= null;
		
		//For efficiency
		//Fill this map only once.
		Map<String,List<String>> geneId2KeggPathwayMap = new HashMap<String, List<String>>();
		KeggPathwayUtility.createNcbiGeneId2KeggPathwayMap(Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, geneId2KeggPathwayMap);
	
		GCCharArray gcCharArray						= null;
    	MapabilityFloatArray mapabilityFloatArray 	= null;
    	List<Integer> hg19ChromosomeSizes 			= new ArrayList<Integer>();
    	
		//Partition the original input data lines in a chromosome based manner
		partitionDataChromosomeBased(allOriginalInputLines,originalInputLinesMap);
				
    	hg19.GRCh37Hg19Chromosome.initializeChromosomeSizes(hg19ChromosomeSizes);
    	//get the hg19 chromosome sizes
    	hg19.GRCh37Hg19Chromosome.getHg19ChromosomeSizes(hg19ChromosomeSizes, Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		
		String chromName;
    	int chromSize;
    	List<InputLine> chromosomeBaseOriginalInputLines;
    	Map<Integer,List<InputLine>> permutationNumber2RandomlyGeneratedDataHashMap = new HashMap<Integer,List<InputLine>>();
    	
    	Annotate annotate;
    	GenerateRandomData generateRandomData;
    	ForkJoinPool pool = new ForkJoinPool(NUMBER_OF_AVAILABLE_PROCESSORS);
    	
    	long startTimeAllPermutations = System.currentTimeMillis();
    		   
    	for(int repeatNumber = 1; repeatNumber<= NUMBER_OF_REPEATS; repeatNumber++){
    		
    		System.out.println("Repeat: " + repeatNumber);
    			
    		for(int i= 1 ; i<=Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
    			
        		chromName = GRCh37Hg19Chromosome.getChromosomeName(i);
    			chromSize = hg19ChromosomeSizes.get(i-1);
    			
    			System.out.println("chromosome name:" + chromName + " chromosome size: " + chromSize);
    			chromosomeBaseOriginalInputLines 	= originalInputLinesMap.get(chromName);
    							
    			if (chromosomeBaseOriginalInputLines!=null){
    										
    				listofAnnotationTasks = new ArrayList<AnnotationTask>();
    				
    				gcCharArray = new GCCharArray();
    				mapabilityFloatArray = new MapabilityFloatArray();
    			
    				//generate tasks
    				System.out.println("Generate annotation tasks has started.");
    				generateAnnotationTasks(chromName,listofAnnotationTasks,repeatNumber,NUMBER_OF_PERMUTATIONS);
    				System.out.println("Generate annotation tasks has ended.");
    				
    				   				
    				if (Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT.equals(generateRandomDataMode)){
    					gcCharArray = ChromosomeBasedGCArray.getChromosomeGCArray(chromName,chromSize);
    					mapabilityFloatArray = ChromosomeBasedMapabilityArray.getChromosomeMapabilityArray(chromName,chromSize);
    				}
    				System.out.println("Generate Random Data and Annotate has started.");	
    			    long startTime = System.currentTimeMillis();
    			    
    			    System.out.println("First Generate Random Data");
    			    System.out.println("Generate Random Data has started.");
     			    //First generate Random Data
    			    generateRandomData = new GenerateRandomData(chromSize,chromName,chromosomeBaseOriginalInputLines,repeatNumber,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,writeGeneratedRandomDataMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,gcCharArray,mapabilityFloatArray);
    			    permutationNumber2RandomlyGeneratedDataHashMap = pool.invoke(generateRandomData);
    			    System.out.println("Generate Random Data has ended.");
    			    
    			    //After Random Data Generation has been ended
    				//generate task for User Given Original Data(Genomic Variants)
    			    //Since we do not need random data, there is  original data is given
    				generateAnnotationTaskforOriginalData(chromName,listofAnnotationTasks,Commons.ORIGINAL_DATA_PERMUTATION_NUMBER);
    				
    				//Add the original data to permutationNumber2RandomlyGeneratedDataHashMap
    				permutationNumber2RandomlyGeneratedDataHashMap.put(Commons.ORIGINAL_DATA_PERMUTATION_NUMBER, chromosomeBaseOriginalInputLines);
    				    
    				System.out.println("Deletion of the gcCharArray has started.");
    				deleteGCCharArray(gcCharArray.getGcArray());
    				System.out.println("Deletion of the gcCharArray has ended.");
    				gcCharArray = null;
    				
    				System.out.println("Deletion of the mapabilityFloatArray has started.");
    				deleteMapabilityFloatArray(mapabilityFloatArray.getMapabilityArray());
    				System.out.println("Deletion of the mapabilityFloatArray has ended.");
    				mapabilityFloatArray = null;
    				
    				System.out.println("Annotate has started.");
    				
    				if (tfKeggPathwayEnrichment.equals(Commons.DO_TF_KEGGPATHWAY_ENRICHMENT)){
    					
    					//New Functionality START
        				//tfbs 
        				//Kegg Pathway (exon Based, regulation Based, all Based)
        				//tfbs and Kegg Pathway (exon Based, regulation Based, all Based)
        				//generate tf interval tree and ucsc refseq genes interval tree
        				tfIntervalTree = generateTfbsIntervalTree(chromName);
        				ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTree(chromName);
          			    annotate = new Annotate(chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,repeatNumber,NUMBER_OF_PERMUTATIONS,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,tfIntervalTree,ucscRefSeqGenesIntervalTree,Commons.TF_KEGG_PATHWAY_ANNOTATION,tfKeggPathwayEnrichment,geneId2KeggPathwayMap);
          				allMaps = pool.invoke(annotate);    
          				//Will be used 	for Tf and KeggPathway Enrichment or
          				//				for Tf and CellLine and KeggPathway Enrichment
    					accumulate(allMaps, accumulatedAllMaps,Commons.TF_KEGG_PATHWAY_ANNOTATION);	
          			    accumulate(allMaps, accumulatedAllMaps,Commons.TFBS_ANNOTATION);
          			    accumulate(allMaps, accumulatedAllMaps,Commons.UCSC_REFSEQ_GENE_ANNOTATION);
          			  
          			    allMaps = null;
          			    deleteIntervalTree(tfIntervalTree);
          			    deleteIntervalTree(ucscRefSeqGenesIntervalTree);
          			    tfIntervalTree = null;
          			    ucscRefSeqGenesIntervalTree = null;	
          				//New Functionality END
        			
    				}
    				
    				
    				if (tfCellLineKeggPathwayEnrichment.equals(Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT)){
        					
        					//New Functionality START
            				//tfbs 
            				//Kegg Pathway (exon Based, regulation Based, all Based)
            				//tfbs and Kegg Pathway (exon Based, regulation Based, all Based)
            				//generate tf interval tree and ucsc refseq genes interval tree
            				tfIntervalTree = generateTfbsIntervalTree(chromName);
            				ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTree(chromName);
              			    annotate = new Annotate(chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,repeatNumber,NUMBER_OF_PERMUTATIONS,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,tfIntervalTree,ucscRefSeqGenesIntervalTree,Commons.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION,tfCellLineKeggPathwayEnrichment,geneId2KeggPathwayMap);
              				allMaps = pool.invoke(annotate);    
              				//Will be used 	for Tf and KeggPathway Enrichment or
              				//				for Tf and CellLine and KeggPathway Enrichment
        					accumulate(allMaps, accumulatedAllMaps,Commons.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION);	
              			    accumulate(allMaps, accumulatedAllMaps,Commons.TFBS_ANNOTATION);
              			    accumulate(allMaps, accumulatedAllMaps,Commons.UCSC_REFSEQ_GENE_ANNOTATION);
              			  
              			    allMaps = null;
              			    deleteIntervalTree(tfIntervalTree);
              			    deleteIntervalTree(ucscRefSeqGenesIntervalTree);
              			    tfIntervalTree = null;
              			    ucscRefSeqGenesIntervalTree = null;	
              				//New Functionality END
            			
        				}
        				
    				if (dnaseEnrichment.equals(Commons.DO_DNASE_ENRICHMENT)){
    					
    					//dnase
        			    //generate dnase interval tree
        			    intervalTree = generateDnaseIntervalTree(chromName);
        			    annotate = new Annotate(chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,repeatNumber,NUMBER_OF_PERMUTATIONS,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,Commons.DNASE_ANNOTATION,null,null);
        				allMaps = pool.invoke(annotate);    			    
        			    accumulate(allMaps, accumulatedAllMaps, Commons.DNASE_ANNOTATION);
        			    allMaps = null;
        			    deleteIntervalTree(intervalTree);
        			    intervalTree = null;
            	
    				}
    			 		
    				if (histoneEnrichment.equals(Commons.DO_HISTONE_ENRICHMENT)){
    				    //histone
        			    //generate histone interval tree
        			    intervalTree = generateHistoneIntervalTree(chromName);
        			    annotate = new Annotate(chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,repeatNumber,NUMBER_OF_PERMUTATIONS,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,Commons.HISTONE_ANNOTATION,null,null);
        				allMaps = pool.invoke(annotate);    			    
        			    accumulate(allMaps, accumulatedAllMaps,Commons.HISTONE_ANNOTATION);
        			    allMaps = null;
        			    deleteIntervalTree(intervalTree);
        			    intervalTree = null;

    				}
    	    			    
//    			    No NEED, Done in new functionality
//    			    //tfbs
//    			    //generate tfbs interval tree
//    			    intervalTree = generateTfbsIntervalTree(chromName);
//    			    annotate = new Annotate(chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,repeatNumber,NUMBER_OF_PERMUTATIONS,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,Commons.TFBS_ANNOTATION);
//    				allMaps = pool.invoke(annotate);    			    
//    			    accumulate(allMaps, accumulatedAllMaps,Commons.TFBS_ANNOTATION);
//    			    allMaps = null;
//    			    deleteIntervalTree(intervalTree);
//    			    intervalTree = null;
            		
//    			    No NEED, Done in new functionality
//   			    //ucsc RefSeq Genes
//    			    //generate UCSC RefSeq Genes interval tree
//    			    intervalTree = generateUcscRefSeqGeneIntervalTree(chromName);
//    			    annotate = new Annotate(chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,repeatNumber,NUMBER_OF_PERMUTATIONS,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,Commons.UCSC_REFSEQ_GENE_ANNOTATION);
//    				allMaps = pool.invoke(annotate);    			    
//    			    accumulate(allMaps, accumulatedAllMaps,Commons.UCSC_REFSEQ_GENE_ANNOTATION);
//    			    allMaps = null;
//    			    deleteIntervalTree(intervalTree);
//    			    intervalTree = null;	
    				System.out.println("Annotate has ended.");
    				
    			    long endTime = System.currentTimeMillis();
    				System.out.println("Repeat: " + repeatNumber  + " For Chromosome: " + chromName + " Annotation of " + NUMBER_OF_PERMUTATIONS + " thousand permutations took  " + (endTime - startTime) + " milliseconds.");
    				System.out.println("Generate Random Data and Annotate has ended.");
   				
    				System.out.println("Deletion of the tasks has started.");
    				deleteAnnotationTasks(listofAnnotationTasks);
    				System.out.println("Deletion of the tasks has ended.");
   			
    			    permutationNumber2RandomlyGeneratedDataHashMap.clear();
    			    permutationNumber2RandomlyGeneratedDataHashMap= null;
    				listofAnnotationTasks = null;
    				annotate = null;
    				generateRandomData = null;
    				chromosomeBaseOriginalInputLines =null;
    				
    				
    			}//end of if: chromosome based input lines is not null
    			
        	}//End of for: each chromosome
    	
    	}//end of for: each repeat
    		
    	pool.shutdown();
		
		if (pool.isTerminated()){
			System.out.println("ForkJoinPool is terminated ");
			
		}   	
		
		long endTimeAllPermutations = System.currentTimeMillis();
	
		System.out.println("NUMBER_OF_REPEATS: " + NUMBER_OF_REPEATS + " NUMBER_OF_PERMUTATIONS:  "+ NUMBER_OF_PERMUTATIONS  + " took "  + (endTimeAllPermutations - startTimeAllPermutations) + " milliseconds.");
	
		//convert permutation augmented name to only name
		//Fill elementName2ALLMap and originalElementName2KMap in convert methods
		convert(accumulatedAllMaps.getPermutationNumberDnaseCellLineName2KMap(),dnase2AllKMap,originalDnase2KMap);
		convert(accumulatedAllMaps.getPermutationNumberTfNameCellLineName2KMap(),tfbs2AllKMap,originalTfbs2KMap);
		convert(accumulatedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap(),histone2AllKMap,originalHistone2KMap);
		
		convert(accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(),exonBasedKeggPathway2AllKMap,originalExonBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(),regulationBasedKeggPathway2AllKMap,originalRegulationBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap(),allBasedKeggPathway2AllKMap,originalAllBasedKeggPathway2KMap);
			
		convert(accumulatedAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap(),tfExonBasedKeggPathway2AllKMap,originalTfExonBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap(),tfRegulationBasedKeggPathway2AllKMap,originalTfRegulationBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap(),tfAllBasedKeggPathway2AllKMap,originalTfAllBasedKeggPathway2KMap);
		
		convert(accumulatedAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap(),tfCellLineExonBasedKeggPathway2AllKMap,originalTfCellLineExonBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(),tfCellLineRegulationBasedKeggPathway2AllKMap,originalTfCellLineRegulationBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap(),tfCellLineAllBasedKeggPathway2AllKMap,originalTfCellLineAllBasedKeggPathway2KMap);
		
				
		//Permutation Based Results
		if (Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT.equals(writePermutationBasedAnnotationResultMode)){
			
			if(dnaseEnrichment.equals(Commons.DO_DNASE_ENRICHMENT)){
				//Dnase
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberDnaseCellLineName2KMap(),permutationNumber2DnaseBufferedWriterHashMap, "dnase\\" , Commons.DNASE);
				closeBufferedWriters(permutationNumber2DnaseBufferedWriterHashMap);
			
			}
			
			if(histoneEnrichment.equals(Commons.DO_HISTONE_ENRICHMENT)){
				//Histone
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap(),permutationNumber2HistoneBufferedWriterHashMap,"histone\\" , Commons.HISTONE);
				closeBufferedWriters(permutationNumber2HistoneBufferedWriterHashMap);
		
			}
						
			
			if(tfKeggPathwayEnrichment.equals(Commons.DO_TF_KEGGPATHWAY_ENRICHMENT)){
				
				//Tf and Exon Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap(),permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap, "tfKeggPathwayNumberofOverlaps\\tfExonBased\\" , Commons.TF_EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and Regulation Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap(),permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap, "tfKeggPathwayNumberofOverlaps\\tfRegulationBased\\" , Commons.TF_REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and All Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap(),permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap, "tfKeggPathwayNumberofOverlaps\\tfAllBased\\" , Commons.TF_ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap);
				
				//Tfbs
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberTfNameCellLineName2KMap(),permutationNumber2TfbsBufferedWriterHashMap, "tfbs\\" , Commons.TFBS);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);
				
				//Exon Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(),permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap,"keggPathway\\exonBased\\" , Commons.EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);
				
				//Regulation Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(),permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, "keggPathway\\regulationBased\\" , Commons.REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);
				
				//All Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap(),permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, "keggPathway\\allBased\\" , Commons.ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);
			
			}else if(tfCellLineKeggPathwayEnrichment.equals(Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT)){
				
				//Tf and Cell Line and Exon Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap(),permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap, "tfCellLineKeggPathwayNumberofOverlaps\\tfCellLineExonBased\\" , Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and Cell Line and Regulation Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(),permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap, "tfCellLineKeggPathwayNumberofOverlaps\\tfCellLineRegulationBased\\" , Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and Cell Line and All Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap(),permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap, "tfCellLineKeggPathwayNumberofOverlaps\\tfCellLineAllBased\\" , Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap);
				
				//Tfbs
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberTfNameCellLineName2KMap(),permutationNumber2TfbsBufferedWriterHashMap, "tfbs\\" , Commons.TFBS);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);
				
				//Exon Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(),permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap,"keggPathway\\exonBased\\" , Commons.EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);
				
				//Regulation Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(),permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, "keggPathway\\regulationBased\\" , Commons.REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);
				
				//All Based Kegg Pathway
				writeAnnotationstoFiles(accumulatedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap(),permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, "keggPathway\\allBased\\" , Commons.ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);

			}
			
			
		}//End of if: write permutation based results
			
	}
	//NEW FUNCIONALITY ADDED
	
	
	public void writetoFile(List<FunctionalElement> list, String fileName, String empiricalPValueType, int NUMBER_OF_REPEATS, int NUMBER_OF_PERMUTATIONS, String generateRandomDataMode, String inputDataFileName,float FDR){
		FileWriter fileWriter=null;
		BufferedWriter bufferedWriter;
		
		DecimalFormat df = new DecimalFormat("0.######E0");
		int i;
		try {
			
			//Set the file name
			if (Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT.equals(generateRandomDataMode)){
				
				if (inputDataFileName.indexOf("ocd")>=0){
					fileWriter = new FileWriter(fileName + "_OCD_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else if (inputDataFileName.indexOf("HIV1")>=0){
					fileWriter = new FileWriter(fileName + "_HIV1_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");			
				}else if(inputDataFileName.indexOf("positive_control")>=0){
					fileWriter = new FileWriter(fileName + "_K562_GATA1_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else{
					fileWriter = new FileWriter(fileName + "_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}
			}else if(Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT.equals(generateRandomDataMode)){
				if (inputDataFileName.indexOf("ocd")>=0){
						fileWriter = new FileWriter(fileName + "_OCD_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else if (inputDataFileName.indexOf("HIV1")>=0){
					fileWriter = new FileWriter(fileName + "_HIV1_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");			
				}else if(inputDataFileName.indexOf("positive_control")>=0){
						fileWriter = new FileWriter(fileName + "_K562_GATA1_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else{
					fileWriter = new FileWriter(fileName + "_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}
			}
			
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//Write header line
			//If BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE first BonfCorrPValue then Empirical P Value
			//Else If EMPIRICAL_P_VALUE first EmpiricalPValue then BonfCorrPValue
			if (Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE.equals(empiricalPValueType)){	
				bufferedWriter.write("Name" + "\t"+ "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsHavingOverlapsGreaterThanorEqualto" + "\t" + "NumberofPermutations" + "\t" + "NumberofComparisons" + "\t" + "BonfCorrEmpiricalPValue" + "\t"+ "EmpiricalPValue" + "\t" + "BH FDR Adjusted P Value" + "\t" + "Reject Null Hypothesis for an FDR of "+ FDR + "\n");	
			} else if(Commons.EMPIRICAL_P_VALUE.equals(empiricalPValueType)){
				bufferedWriter.write("Name" + "\t"+ "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsHavingOverlapsGreaterThanorEqualto" + "\t" + "NumberofPermutations" + "\t" + "NumberofComparisons" + "\t" + "EmpiricalPValue" + "\t"+ "BonfCorrEmpiricalPValue" + "\t" + "BH FDR Adjusted P Value" + "\t" + "Reject Null Hypothesis for an FDR of "+ FDR + "\n");	
			} else if (Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE.equals(empiricalPValueType)){
				bufferedWriter.write("Name" + "\t"+ "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsHavingOverlapsGreaterThanorEqualto" + "\t" + "NumberofPermutations" + "\t" + "NumberofComparisons" + "\t" + "BonfCorrEmpiricalPValue" + "\t"+ "EmpiricalPValue" + "\t" + "BH FDR Adjusted P Value" + "\t" + "Reject Null Hypothesis for an FDR of "+ FDR + "\n");	
			}
			
			
			//For each element in the list
			for(FunctionalElement element : list){
				
				//In case of Functional Element is a kegg pathway
				if(element.getKeggPathwayName()!=null){
					
					if (Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE.equals(empiricalPValueType)){	
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t"+ df.format(element.getEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() +"\t");	
					} else if(Commons.EMPIRICAL_P_VALUE.equals(empiricalPValueType)){
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getEmpiricalPValue()) + "\t" +df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() +"\t");	
					} else if(Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE.equals(empiricalPValueType)){
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t"+ df.format(element.getEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() +"\t");	
						
					}
					
					bufferedWriter.write(element.getKeggPathwayName()+"\t");
					
					
					if (element.getKeggPathwayGeneIdList().size()>=1){
						//Write the gene ids of the kegg pathway
						for(i =0 ;i < element.getKeggPathwayGeneIdList().size()-1; i++){
							bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + ", ");
						}
						bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + "\t");
					}
					
					if(element.getKeggPathwayAlternateGeneNameList().size()>=1){
						//Write the alternate gene names of the kegg pathway
						for(i =0 ;i < element.getKeggPathwayAlternateGeneNameList().size()-1; i++){
							bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + ", ");
						}
						bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + "\n");
					
					}					
				}else{
					if (Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE.equals(empiricalPValueType)){	
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t"+ df.format(element.getEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() + "\n");	
					} else if(Commons.EMPIRICAL_P_VALUE.equals(empiricalPValueType)){
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getEmpiricalPValue()) + "\t" +df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() + "\n");	
					} else if(Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE.equals(empiricalPValueType)){
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t"+ df.format(element.getEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() + "\n");	
								
					}
				}
							
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public void writetoFileSmallValueInsteadofZero(Random random, List<FunctionalElement> list, String fileName, String empiricalPValueType, int NUMBER_OF_REPEATS, int NUMBER_OF_PERMUTATIONS, String generateRandomDataMode, String inputDataFileName){
		FileWriter fileWriter=null;
		BufferedWriter bufferedWriter;
		
		DecimalFormat df = new DecimalFormat("0.######E0");
		Float empiricalPValue;
		Float bonfCorrEmpiricalPValue;
		
		int i;
		try {
			
			if (Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT.equals(generateRandomDataMode)){
				
				if (inputDataFileName.indexOf("ocd")>=0){
					fileWriter = new FileWriter(fileName + "_OCD_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else if(inputDataFileName.indexOf("positive_control")>=0){
					fileWriter = new FileWriter(fileName + "_K562_GATA1_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else{
					fileWriter = new FileWriter(fileName + "_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}
			}else if(Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT.equals(generateRandomDataMode)){
				if (inputDataFileName.indexOf("ocd")>=0){
						fileWriter = new FileWriter(fileName + "_OCD_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else if(inputDataFileName.indexOf("positive_control")>=0){
						fileWriter = new FileWriter(fileName + "_K562_GATA1_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else{
					fileWriter = new FileWriter(fileName + "_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}
			}
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(FunctionalElement element : list){
				
				//Write the name of the functional element
				bufferedWriter.write(element.getName() + "\t");
				
				
				//In case of Functional Element is a kegg pathway
				if(element.getKeggPathwayName()!=null){
					
					bufferedWriter.write(element.getKeggPathwayName()+"\t");
					
					
					if (element.getKeggPathwayGeneIdList().size()>=1){
						//Write the gene ids of the kegg pathway
						for(i =0 ;i < element.getKeggPathwayGeneIdList().size()-1; i++){
							bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + ", ");
						}
						bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + "\t");
					}
					
					if(element.getKeggPathwayAlternateGeneNameList().size()>=1){
						//Write the alternate gene names of the kegg pathway
						for(i =0 ;i < element.getKeggPathwayAlternateGeneNameList().size()-1; i++){
							bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + ", ");
						}
						bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + "\t");
					
					}					
				}
				
				
				
				if (Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE.equals(empiricalPValueType)){	
					bonfCorrEmpiricalPValue = element.getBonferroniCorrectedEmpiricalPValue();
					if(bonfCorrEmpiricalPValue.equals(Commons.FLOAT_ZERO)){
						element.setBonferroniCorrectedEmpiricalPValue(random.nextFloat()/Commons.FLOAT_TEN_QUADRILLION);
					}
					bufferedWriter.write(df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\n");	
				} else if(Commons.EMPIRICAL_P_VALUE.equals(empiricalPValueType)){
					empiricalPValue = element.getEmpiricalPValue();
					if (empiricalPValue.equals(Commons.FLOAT_ZERO)){
						element.setEmpiricalPValue(random.nextFloat()/Commons.FLOAT_TEN_QUADRILLION);
					}
					bufferedWriter.write(df.format(element.getEmpiricalPValue())+ "\n");	
				}
				
				
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	public void writeToBeCollectedNumberofOverlaps(Map<String,Integer> originalElement2KMap, Map<String,List<Integer>> element2AllKMap,String toBePolledDirectoryName, String runNumber){
		String elementName;
		Integer originalNumberofOverlaps;
		
		List<Integer> permutationNumberofOverlapsList;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = new FileWriter(toBePolledDirectoryName + "_" + runNumber +".txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Map.Entry<String,Integer> entry: originalElement2KMap.entrySet()){
				
				elementName = entry.getKey();
				originalNumberofOverlaps = entry.getValue();
				
				bufferedWriter.write(elementName + "\t" + originalNumberofOverlaps + "|" );
				
				permutationNumberofOverlapsList = element2AllKMap.get(elementName);
				
				if (permutationNumberofOverlapsList!=null){
					for (Integer permutationNumberofOverlaps : permutationNumberofOverlapsList){
						bufferedWriter.write(permutationNumberofOverlaps + "," );
					}//End of inner loop
					
				}

				bufferedWriter.write("\n");
				
				//if permutationNumberofOverlapsList is null 
				//do nothing
				
				
				
			}//End of outer loop
		
		
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public static void writeInformation(){
		System.out.println("Java runtime max memory: " + java.lang.Runtime.getRuntime().maxMemory());
        System.out.println("Java runtime total memory: " + java.lang.Runtime.getRuntime().totalMemory());	
		System.out.println("Java runtime available processors: " + java.lang.Runtime.getRuntime().availableProcessors()); 
	
	}
	
	public static void main(String[] args) {
		
		//Number of processors can be used in deciding on paralellism level
		int NUMBER_OF_AVAILABLE_PROCESSORS =  java.lang.Runtime.getRuntime().availableProcessors();
		
		//Set the number of repeats
//		int NUMBER_OF_REPEATS = 1;
		int NUMBER_OF_REPEATS = Integer.parseInt(args[0]);
		
		//Set the number of permutations
//		int NUMBER_OF_PERMUTATIONS = 10000;
		int NUMBER_OF_PERMUTATIONS = Integer.parseInt(args[1]);
		
		float FDR = Float.parseFloat(args[2]);
		
		//SET the Input Data File
//		String inputDataFileName = Commons.OCD_GWAS_SIGNIFICANT_SNPS_WITHOUT_OVERLAPS;
//		String inputDataFileName = Commons.POSITIVE_CONTROL_OUTPUT_FILE_NAME_WITHOUT_OVERLAPS;
//		String inputDataFileName = Commons.TCGA_INPUT_DATA_WITH_NON_BLANKS_SNP_IDS_WITHOUT_OVERLAPS;
		String inputDataFileName = args[3];
				
		//Set the Generate Random Data Mode
//		String generateRandomDataMode = Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
		String generateRandomDataMode = args[4];
		
		//Set the Write Mode of Generated Random Data
//		String writeGeneratedRandomDataMode = Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA;
		String writeGeneratedRandomDataMode = args[5];
				
		//Set the Write Mode of Permutation Based and Parametric Based Annotation Result
//		String writePermutationBasedandParametricBasedAnnotationResultMode = Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT;
		String writePermutationBasedandParametricBasedAnnotationResultMode = args[6];
		
		//Set the Write Mode of the Permutation Based Annotation Result
//		String writePermutationBasedAnnotationResultMode = Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT;
		String writePermutationBasedAnnotationResultMode = args[7];
		
		//Dnase Enrichment, DO or DO_NOT
		String dnaseEnrichment = args[8];
		
		//Histone Enrichment, DO or DO_NOT
		String histoneEnrichment = args[9];
				
		
		//TfKeggPathway Enrichment, DO or DO_NOT
		String tfKeggPathwayEnrichment = args[10];
		
		//TfCellLineKeggPathway Enrichment, DO or DO_NOT
		String tfCellLineKeggPathwayEnrichment = args[11];
		
		//Run Name
		String runName = args[12] ;
		
		writeInformation();
			
		//Random Class for generating small values instead of zero valued p values
		//Random random = new Random();
		
		AnnotatePermutationsUsingForkJoin_withEnrichmentChoices annotateOneThousandPermutationsUsingForkJoin = new AnnotatePermutationsUsingForkJoin_withEnrichmentChoices();

		List<InputLine> originalInputLines = new ArrayList<InputLine>();
		
		//Read original input data lines in to a list
		annotateOneThousandPermutationsUsingForkJoin.readOriginalInputDataLines(originalInputLines, inputDataFileName);
	
		//For Bonferroni Correction 
		//Set the number of comparisons for DNase, Tfbs, Histone
		//Set the number of comparisons for ExonBasedKeggPathway, RegulationBasedKeggPathway, AllBasedKeggPathway
		//Set the number of comparisons for TfCellLineExonBasedKeggPathway, TfCellLineRegulationBasedKeggPathway, TfCellLineAllBasedKeggPathway
		//Set the number of comparisons for TfExonBasedKeggPathway, TfRegulationBasedKeggPathway, TfAllBasedKeggPathway
		NumberofComparisons  numberofComparisons = new NumberofComparisons();
		NumberofComparisonsforBonferroniCorrectionCalculation.getNumberofComparisonsforBonferroniCorrection(numberofComparisons);
			

		
		//@todo
		//for loop starts
		int numberofRuns = NUMBER_OF_PERMUTATIONS/1000;
		
		String runNumber;
		
		for(int i=1; i<=numberofRuns;i++){
			System.out.println("**************	" + i + ". Run" + "	******************	starts");
			
			runNumber = runName + i;
			
			//annotation of original data with permutations	
			//annotation of original data has permutation number zero
			//number of overlaps for the original data: k out of n for the original data
			Map<String,Integer> originalDnase2KMap = new HashMap<String,Integer>();
			Map<String,Integer> originalTfbs2KMap = new HashMap<String,Integer>();
			Map<String,Integer> originalHistone2KMap = new HashMap<String,Integer>();
			
			Map<String,Integer> originalExonBasedKeggPathway2KMap = new HashMap<String,Integer>();
			Map<String,Integer> originalRegulationBasedKeggPathway2KMap = new HashMap<String,Integer>();
			Map<String,Integer> originalAllBasedKeggPathway2KMap = new HashMap<String,Integer>();
						
			//Tf and KeggPathway Enrichment
			Map<String,Integer> originalTfExonBasedKeggPathway2KMap = new HashMap<String,Integer>();
			Map<String,Integer> originalTfRegulationBasedKeggPathway2KMap = new HashMap<String,Integer>();
			Map<String,Integer> originalTfAllBasedKeggPathway2KMap = new HashMap<String,Integer>();
		
			//Tf and CellLine and KeggPathway Enrichment 
			Map<String,Integer> originalTfCellLineExonBasedKeggPathway2KMap = new HashMap<String,Integer>();
			Map<String,Integer> originalTfCellLineRegulationBasedKeggPathway2KMap = new HashMap<String,Integer>();
			Map<String,Integer> originalTfCellLineAllBasedKeggPathway2KMap = new HashMap<String,Integer>();
								
			//functionalElementName based
			//number of overlaps: k out of n for all permutations
			Map<String,List<Integer>> dnase2AllKMap = new HashMap<String,List<Integer>>();
			Map<String,List<Integer>> histone2AllKMap = new HashMap<String,List<Integer>>();
			Map<String,List<Integer>> tfbs2AllKMap = new HashMap<String,List<Integer>>();
			
			Map<String,List<Integer>> exonBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
			Map<String,List<Integer>> regulationBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
			Map<String,List<Integer>> allBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
			
			//Tf and KeggPathway Enrichment
			Map<String,List<Integer>> tfExonBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
			Map<String,List<Integer>> tfRegulationBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>() ;
			Map<String,List<Integer>> tfAllBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
		
			//Tf and CellLine and KeggPathway Enrichment 
			Map<String,List<Integer>> tfCellLineExonBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
			Map<String,List<Integer>> tfCellLineRegulationBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>() ;
			Map<String,List<Integer>> tfCellLineAllBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
			
			
//			//functionalElementName based
//			//empirical p values and bonferroni corrected empirical p values
//			//These are filled and sorted and then written to files
//			List<FunctionalElement> dnaseList = new ArrayList<FunctionalElement>();
//			List<FunctionalElement> histoneList = new ArrayList<FunctionalElement>();
//			List<FunctionalElement> tfbsList = new ArrayList<FunctionalElement>();
//			
//			List<FunctionalElement> exonBasedKeggPathwayList = new ArrayList<FunctionalElement>();
//			List<FunctionalElement> regulationBasedKeggPathwayList = new ArrayList<FunctionalElement>();	
//			List<FunctionalElement> allBasedKeggPathwayList = new ArrayList<FunctionalElement>();	
//			
//			//Tf and KeggPathway Enrichment or 
//			List<FunctionalElement> tfExonBasedKeggPathwayList = new ArrayList<FunctionalElement>();	
//			List<FunctionalElement> tfRegulationBasedKeggPathwayList = new ArrayList<FunctionalElement>();	
//			List<FunctionalElement> tfAllBasedKeggPathwayList = new ArrayList<FunctionalElement>();	
//			
//			//Tf and CellLine and KeggPathway Enrichment 
//			List<FunctionalElement> tfCellLineExonBasedKeggPathwayList = new ArrayList<FunctionalElement>();	
//			List<FunctionalElement> tfCellLineRegulationBasedKeggPathwayList = new ArrayList<FunctionalElement>();	
//			List<FunctionalElement> tfCellLineAllBasedKeggPathwayList = new ArrayList<FunctionalElement>();	

			
			//Delete old files
			annotateOneThousandPermutationsUsingForkJoin.deleteOldFiles();
			
			System.out.println("Concurrent programming has been started.");				
			//concurrent programming
			//generate random data
			//then annotate permutations concurrently
			//elementName2AllKMap and originalElementName2KMap will be filled here
			annotateOneThousandPermutationsUsingForkJoin.annotateAllPermutationsInThreads(NUMBER_OF_AVAILABLE_PROCESSORS,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS/numberofRuns,originalInputLines,dnase2AllKMap, tfbs2AllKMap, histone2AllKMap, exonBasedKeggPathway2AllKMap, regulationBasedKeggPathway2AllKMap,allBasedKeggPathway2AllKMap,tfExonBasedKeggPathway2AllKMap,tfRegulationBasedKeggPathway2AllKMap,tfAllBasedKeggPathway2AllKMap,tfCellLineExonBasedKeggPathway2AllKMap,tfCellLineRegulationBasedKeggPathway2AllKMap,tfCellLineAllBasedKeggPathway2AllKMap,generateRandomDataMode,writeGeneratedRandomDataMode,writePermutationBasedandParametricBasedAnnotationResultMode,writePermutationBasedAnnotationResultMode,originalDnase2KMap,originalTfbs2KMap,originalHistone2KMap,originalExonBasedKeggPathway2KMap,originalRegulationBasedKeggPathway2KMap,originalAllBasedKeggPathway2KMap,originalTfExonBasedKeggPathway2KMap,originalTfRegulationBasedKeggPathway2KMap,originalTfAllBasedKeggPathway2KMap,originalTfCellLineExonBasedKeggPathway2KMap,originalTfCellLineRegulationBasedKeggPathway2KMap,originalTfCellLineAllBasedKeggPathway2KMap,dnaseEnrichment,histoneEnrichment,tfKeggPathwayEnrichment,tfCellLineKeggPathwayEnrichment);		
			System.out.println("Concurrent programming has been ended.");				
				
			
			//First Calculate Empirical P Values and Bonferroni Corrected Empirical P Values starts
			//List<FunctionalElement> list is filled in this method
			//Using name2AllKMap and originalName2KMap
			if(dnaseEnrichment.equals(Commons.DO_DNASE_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonsDnase(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,dnase2AllKMap, originalDnase2KMap, dnaseList);
			
				//Write to be collected files
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalDnase2KMap,dnase2AllKMap,Commons.TO_BE_POLLED_DNASE_NUMBER_OF_OVERLAPS,runNumber);
			}
			
			if (histoneEnrichment.equals(Commons.DO_HISTONE_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonsHistone(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,histone2AllKMap, originalHistone2KMap,histoneList);
				
				//Write to be collected files
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalHistone2KMap,histone2AllKMap,Commons.TO_BE_POLLED_HISTONE_NUMBER_OF_OVERLAPS,runNumber);
			}
			
				
			if (tfKeggPathwayEnrichment.equals(Commons.DO_TF_KEGGPATHWAY_ENRICHMENT)){

//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonsTfbs(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,tfbs2AllKMap, originalTfbs2KMap, tfbsList);
//				
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonsExonBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,exonBasedKeggPathway2AllKMap, originalExonBasedKeggPathway2KMap, exonBasedKeggPathwayList);
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonsRegulationBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,regulationBasedKeggPathway2AllKMap, originalRegulationBasedKeggPathway2KMap, regulationBasedKeggPathwayList);
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonsAllBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,allBasedKeggPathway2AllKMap, originalAllBasedKeggPathway2KMap, allBasedKeggPathwayList);
//		
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonTfExonBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,tfExonBasedKeggPathway2AllKMap, originalTfExonBasedKeggPathway2KMap, tfExonBasedKeggPathwayList);
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonTfRegulationBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,tfRegulationBasedKeggPathway2AllKMap, originalTfRegulationBasedKeggPathway2KMap, tfRegulationBasedKeggPathwayList);
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonTfAllBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,tfAllBasedKeggPathway2AllKMap, originalTfAllBasedKeggPathway2KMap, tfAllBasedKeggPathwayList);
							
				//Write to be collected files
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalTfbs2KMap,tfbs2AllKMap,Commons.TO_BE_POLLED_TF_NUMBER_OF_OVERLAPS,runNumber);
				
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalExonBasedKeggPathway2KMap,exonBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalRegulationBasedKeggPathway2KMap,regulationBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalAllBasedKeggPathway2KMap,allBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
				
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalTfExonBasedKeggPathway2KMap,tfExonBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalTfRegulationBasedKeggPathway2KMap,tfRegulationBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalTfAllBasedKeggPathway2KMap,tfAllBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
			
			}
			
			if(tfCellLineKeggPathwayEnrichment.equals(Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonsTfbs(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,tfbs2AllKMap, originalTfbs2KMap, tfbsList);
//				
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonsExonBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,exonBasedKeggPathway2AllKMap, originalExonBasedKeggPathway2KMap, exonBasedKeggPathwayList);
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonsRegulationBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,regulationBasedKeggPathway2AllKMap, originalRegulationBasedKeggPathway2KMap, regulationBasedKeggPathwayList);
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonsAllBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,allBasedKeggPathway2AllKMap, originalAllBasedKeggPathway2KMap, allBasedKeggPathwayList);
//		
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonTfCellLineExonBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,tfCellLineExonBasedKeggPathway2AllKMap, originalTfCellLineExonBasedKeggPathway2KMap, tfCellLineExonBasedKeggPathwayList);
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonTfCellLineRegulationBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,tfCellLineRegulationBasedKeggPathway2AllKMap, originalTfCellLineRegulationBasedKeggPathway2KMap, tfCellLineRegulationBasedKeggPathwayList);
//				annotateOneThousandPermutationsUsingForkJoin.calculateEmpricalPValues(numberofComparisons.getNumberofComparisonTfCellLineAllBasedKeggPathway(),NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,tfCellLineAllBasedKeggPathway2AllKMap, originalTfCellLineAllBasedKeggPathway2KMap, tfCellLineAllBasedKeggPathwayList);
								
				//Write to be collected files
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalTfbs2KMap,tfbs2AllKMap,Commons.TO_BE_POLLED_TF_NUMBER_OF_OVERLAPS,runNumber);
				
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalExonBasedKeggPathway2KMap,exonBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalRegulationBasedKeggPathway2KMap,regulationBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalAllBasedKeggPathway2KMap,allBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
				
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalTfCellLineExonBasedKeggPathway2KMap,tfCellLineExonBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalTfCellLineRegulationBasedKeggPathway2KMap,tfCellLineRegulationBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
				annotateOneThousandPermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(originalTfCellLineAllBasedKeggPathway2KMap,tfCellLineAllBasedKeggPathway2AllKMap,Commons.TO_BE_POLLED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runNumber);
			}
			//Calculate Empirical P Values and Bonferroni Corrected Empirical P Values ends

//			//Sort w.r.t. Empirical P Values starts in ascending order
//			Collections.sort(dnaseList, FunctionalElement.EMPIRICAL_P_VALUE);
//			Collections.sort(tfbsList, FunctionalElement.EMPIRICAL_P_VALUE);
//			Collections.sort(histoneList, FunctionalElement.EMPIRICAL_P_VALUE);
//			
//			Collections.sort(exonBasedKeggPathwayList, FunctionalElement.EMPIRICAL_P_VALUE);
//			Collections.sort(regulationBasedKeggPathwayList, FunctionalElement.EMPIRICAL_P_VALUE);
//			Collections.sort(allBasedKeggPathwayList, FunctionalElement.EMPIRICAL_P_VALUE);
//			
//			Collections.sort(tfExonBasedKeggPathwayList, FunctionalElement.EMPIRICAL_P_VALUE);
//			Collections.sort(tfRegulationBasedKeggPathwayList, FunctionalElement.EMPIRICAL_P_VALUE);
//			Collections.sort(tfAllBasedKeggPathwayList, FunctionalElement.EMPIRICAL_P_VALUE);
//
//			Collections.sort(tfCellLineExonBasedKeggPathwayList, FunctionalElement.EMPIRICAL_P_VALUE);
//			Collections.sort(tfCellLineRegulationBasedKeggPathwayList, FunctionalElement.EMPIRICAL_P_VALUE);
//			Collections.sort(tfCellLineAllBasedKeggPathwayList, FunctionalElement.EMPIRICAL_P_VALUE);
//			//Sort w.r.t. Empirical P Values in ascending order ends
//						
//					
//			//After sorting w.r.t. empirical p values in ascending order
//			//Calculate Benjamini and Hochberg Adjusted P Values for FDR starts
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(dnaseList,FDR);
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(histoneList,FDR);
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(tfbsList,FDR);
//			
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(exonBasedKeggPathwayList,FDR);
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(regulationBasedKeggPathwayList,FDR);
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(allBasedKeggPathwayList,FDR);
//			
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(tfExonBasedKeggPathwayList,FDR);
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(tfRegulationBasedKeggPathwayList,FDR);
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(tfAllBasedKeggPathwayList,FDR);
//			
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(tfCellLineExonBasedKeggPathwayList,FDR);
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(tfCellLineRegulationBasedKeggPathwayList,FDR);
//			BenjaminiandHochberg.calculateBenjaminiHochbergFDRAdjustedPValues(tfCellLineAllBasedKeggPathwayList,FDR);
//			//Calculate Benjamini and Hochberg Adjusted P Values for FDR ends
//			
//			
//			//Augment with Kegg Pathway Name starts
//			KeggPathwayAugmentation.augmentKeggPathwayEntrywithKeggPathwayName(exonBasedKeggPathwayList,regulationBasedKeggPathwayList,allBasedKeggPathwayList);		
//			//Augment with Kegg Pathway Gene List and Alternate Gene Name List
//			KeggPathwayAugmentation.augmentKeggPathwayEntrywithKeggPathwayGeneList(exonBasedKeggPathwayList,regulationBasedKeggPathwayList,allBasedKeggPathwayList);
//			
//			if (tfKeggPathwayEnrichment.equals(Commons.DO_TF_KEGGPATHWAY_ENRICHMENT)){
//				KeggPathwayAugmentation.augmentTfNameKeggPathwayEntrywithKeggPathwayName(tfExonBasedKeggPathwayList,tfRegulationBasedKeggPathwayList,tfAllBasedKeggPathwayList);
//				KeggPathwayAugmentation.augmentTfNameKeggPathwayEntrywithKeggPathwayGeneList(tfExonBasedKeggPathwayList,tfRegulationBasedKeggPathwayList,tfAllBasedKeggPathwayList);
//			}
//			
//			if(tfCellLineKeggPathwayEnrichment.equals(Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT)){
//				KeggPathwayAugmentation.augmentTfNameCellLineNameKeggPathwayEntrywithKeggPathwayName(tfCellLineExonBasedKeggPathwayList,tfCellLineRegulationBasedKeggPathwayList,tfCellLineAllBasedKeggPathwayList);
//				KeggPathwayAugmentation.augmentTfNameCellLineNameKeggPathwayEntrywithKeggPathwayGeneList(tfCellLineExonBasedKeggPathwayList,tfCellLineRegulationBasedKeggPathwayList,tfCellLineAllBasedKeggPathwayList);		
//			}
//			//Augment with Kegg Pathway Name ends
//		
//			
//				
//			
//				
//			//Write Sorted Empirical P Values to output files		
//			if(dnaseEnrichment.equals(Commons.DO_DNASE_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(dnaseList,Commons.DNASE_CELL_LINE_NAME_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//			}
//			
//			if(histoneEnrichment.equals(Commons.DO_HISTONE_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(histoneList,Commons.HISTONE_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//			}
//			
//			
//			if (tfKeggPathwayEnrichment.equals(Commons.DO_TF_KEGGPATHWAY_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfExonBasedKeggPathwayList,Commons.TF_EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfRegulationBasedKeggPathwayList,Commons.TF_REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfAllBasedKeggPathwayList,Commons.TF_ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfbsList,Commons.TFBS_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(exonBasedKeggPathwayList,Commons.EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(regulationBasedKeggPathwayList,Commons.REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(allBasedKeggPathwayList,Commons.ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//			}
//			
//			if(tfCellLineKeggPathwayEnrichment.equals(Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfCellLineExonBasedKeggPathwayList,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfCellLineRegulationBasedKeggPathwayList,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfCellLineAllBasedKeggPathwayList,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfbsList,Commons.TFBS_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(exonBasedKeggPathwayList,Commons.EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(regulationBasedKeggPathwayList,Commons.REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(allBasedKeggPathwayList,Commons.ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,Commons.EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//			}
//
//			
//			
//			//sort w.r.t. Benjamini and Hochberg FDR in ascending order
//			Collections.sort(dnaseList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			Collections.sort(histoneList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			Collections.sort(tfbsList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			
//			Collections.sort(exonBasedKeggPathwayList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			Collections.sort(regulationBasedKeggPathwayList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			Collections.sort(allBasedKeggPathwayList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			
//			Collections.sort(tfExonBasedKeggPathwayList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			Collections.sort(tfRegulationBasedKeggPathwayList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			Collections.sort(tfAllBasedKeggPathwayList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			
//			Collections.sort(tfCellLineExonBasedKeggPathwayList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			Collections.sort(tfCellLineRegulationBasedKeggPathwayList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			Collections.sort(tfCellLineAllBasedKeggPathwayList, FunctionalElement.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);
//			
//			//Write sorted Benjamini and Hochberg FDR Adjusted P Values
//			if(dnaseEnrichment.equals(Commons.DO_DNASE_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(dnaseList,Commons.DNASE_CELL_LINE_NAME_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//			}
//			
//			if(histoneEnrichment.equals(Commons.DO_HISTONE_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(histoneList,Commons.HISTONE_NAME_CELL_LINE_NAME_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//			}
//			
//			
//			if (tfKeggPathwayEnrichment.equals(Commons.DO_TF_KEGGPATHWAY_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfExonBasedKeggPathwayList,Commons.TF_EXON_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfRegulationBasedKeggPathwayList,Commons.TF_REGULATION_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfAllBasedKeggPathwayList,Commons.TF_ALL_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfbsList,Commons.TFBS_NAME_CELL_LINE_NAME_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(exonBasedKeggPathwayList,Commons.EXON_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(regulationBasedKeggPathwayList,Commons.REGULATION_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(allBasedKeggPathwayList,Commons.ALL_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//			}
//			
//			if(tfCellLineKeggPathwayEnrichment.equals(Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfCellLineExonBasedKeggPathwayList,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfCellLineRegulationBasedKeggPathwayList,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfCellLineAllBasedKeggPathwayList,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfbsList,Commons.TFBS_NAME_CELL_LINE_NAME_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(exonBasedKeggPathwayList,Commons.EXON_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(regulationBasedKeggPathwayList,Commons.REGULATION_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(allBasedKeggPathwayList,Commons.ALL_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR+ "_Level_" + FDR,Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//			}
//			//Writing Benjamini and Hochberg Adjusted P Values for FDR ends
//				
//			
//			//Sort w.r.t. Bonferroni Corrected Empirical P Values
//			Collections.sort(dnaseList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//			Collections.sort(histoneList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//			Collections.sort(tfbsList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//			
//			Collections.sort(exonBasedKeggPathwayList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//			Collections.sort(regulationBasedKeggPathwayList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//			Collections.sort(allBasedKeggPathwayList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//				
//			Collections.sort(tfExonBasedKeggPathwayList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//			Collections.sort(tfRegulationBasedKeggPathwayList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//			Collections.sort(tfAllBasedKeggPathwayList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//		
//			Collections.sort(tfCellLineExonBasedKeggPathwayList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//			Collections.sort(tfCellLineRegulationBasedKeggPathwayList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//			Collections.sort(tfCellLineAllBasedKeggPathwayList, FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
//			
//		
//			//Write Sorted Bonferroni Corrected Empirical P Values to output files
//			if(dnaseEnrichment.equals(Commons.DO_DNASE_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(dnaseList,Commons.DNASE_CELL_LINE_NAME_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION, Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//			}
//			
//			if(histoneEnrichment.equals(Commons.DO_HISTONE_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(histoneList,Commons.HISTONE_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//			}
//			
//			
//			if (tfKeggPathwayEnrichment.equals(Commons.DO_TF_KEGGPATHWAY_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfExonBasedKeggPathwayList,Commons.TF_EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfRegulationBasedKeggPathwayList,Commons.TF_REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfAllBasedKeggPathwayList,Commons.TF_ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfbsList,Commons.TFBS_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(exonBasedKeggPathwayList,Commons.EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(regulationBasedKeggPathwayList,Commons.REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(allBasedKeggPathwayList,Commons.ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//
//			}
//			
//			if(tfCellLineKeggPathwayEnrichment.equals(Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT)){
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfCellLineExonBasedKeggPathwayList,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfCellLineRegulationBasedKeggPathwayList,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfCellLineAllBasedKeggPathwayList,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(tfbsList,Commons.TFBS_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(exonBasedKeggPathwayList,Commons.EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(regulationBasedKeggPathwayList,Commons.REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//				annotateOneThousandPermutationsUsingForkJoin.writetoFile(allBasedKeggPathwayList,Commons.ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION,Commons.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE,NUMBER_OF_REPEATS,NUMBER_OF_PERMUTATIONS,generateRandomDataMode,inputDataFileName,FDR);
//
//			}
			
			originalDnase2KMap = null;
			originalTfbs2KMap = null;
			originalHistone2KMap = null;
			
			originalExonBasedKeggPathway2KMap = null;
			originalRegulationBasedKeggPathway2KMap = null;
			originalAllBasedKeggPathway2KMap = null;
			
			
			originalTfExonBasedKeggPathway2KMap = null;
			originalTfRegulationBasedKeggPathway2KMap = null;
			originalTfAllBasedKeggPathway2KMap = null;
		
			originalTfCellLineExonBasedKeggPathway2KMap = null;
			originalTfCellLineRegulationBasedKeggPathway2KMap = null;
			originalTfCellLineAllBasedKeggPathway2KMap = null;
					
			
			//functionalElementName based
			//number of overlaps: k out of n for all permutations
			dnase2AllKMap = null;
			histone2AllKMap = null;
			tfbs2AllKMap = null;
			
			exonBasedKeggPathway2AllKMap = null;
			regulationBasedKeggPathway2AllKMap = null;
			allBasedKeggPathway2AllKMap = null;
			
			//Tf and KeggPathway Enrichment
			tfExonBasedKeggPathway2AllKMap = null;
			tfRegulationBasedKeggPathway2AllKMap = null ;
			tfAllBasedKeggPathway2AllKMap = null;
		
			//Tf and CellLine and KeggPathway Enrichment 
			tfCellLineExonBasedKeggPathway2AllKMap = null;
			tfCellLineRegulationBasedKeggPathway2AllKMap = null ;
			tfCellLineAllBasedKeggPathway2AllKMap = null;
	
			System.out.println("**************	" + i + ". Run" + "	******************	ends");
			
		}
		//for loop ends						
	
	}//End of main function

}
