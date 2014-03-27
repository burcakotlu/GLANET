/**
 * @author Burcak Otlu
 * Sep 4, 2013
 * 5:31:21 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import hg19.GRCh37Hg19Chromosome;

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

import empiricalpvalues.GCCharArray;
import intervaltree.IntervalTree;

import common.Commons;

public class MeanandStandardDeviationofGCandMapabilityofChromosomeBasedDnaseTfbsHistoneFiles {

	/**
	 * 
	 */
	public MeanandStandardDeviationofGCandMapabilityofChromosomeBasedDnaseTfbsHistoneFiles() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static  String getChromosomeBasedFunctionalElementInputFileName(String functionalElementType, String chromName){
		
		if (Commons.DNASE.equals(functionalElementType)){
			switch (chromName){
				case "chr1" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr1_dnase.txt";
				case "chr2" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr2_dnase.txt";
				case "chr3" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr3_dnase.txt";
				case "chr4" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr4_dnase.txt";
				case "chr5" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr5_dnase.txt";
				case "chr6" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr6_dnase.txt";
				case "chr7" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr7_dnase.txt";
				case "chr8" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr8_dnase.txt";
				case "chr9" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr9_dnase.txt";
				case "chr10" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr10_dnase.txt";
				case "chr11" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr11_dnase.txt";
				case "chr12" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr12_dnase.txt";
				case "chr13" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr13_dnase.txt";
				case "chr14" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr14_dnase.txt";
				case "chr15" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr15_dnase.txt";
				case "chr16" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr16_dnase.txt";
				case "chr17" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr17_dnase.txt";
				case "chr18" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr18_dnase.txt";
				case "chr19" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr19_dnase.txt";
				case "chr20" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr20_dnase.txt";
				case "chr21" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr21_dnase.txt";
				case "chr22" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chr22_dnase.txt";
				case "chrX" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chrX_dnase.txt";
				case "chrY" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\dnase\\unsorted_chrY_dnase.txt";	
			}//End of SWITCH
		}else if (Commons.TFBS.equals(functionalElementType)){
			switch (chromName){
				case "chr1" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr1_tfbs.txt";
				case "chr2" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr2_tfbs.txt";
				case "chr3" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr3_tfbs.txt";
				case "chr4" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr4_tfbs.txt";
				case "chr5" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr5_tfbs.txt";
				case "chr6" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr6_tfbs.txt";
				case "chr7" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr7_tfbs.txt";
				case "chr8" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr8_tfbs.txt";
				case "chr9" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr9_tfbs.txt";
				case "chr10" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr10_tfbs.txt";
				case "chr11" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr11_tfbs.txt";
				case "chr12" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr12_tfbs.txt";
				case "chr13" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr13_tfbs.txt";
				case "chr14" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr14_tfbs.txt";
				case "chr15" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr15_tfbs.txt";
				case "chr16" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr16_tfbs.txt";
				case "chr17" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr17_tfbs.txt";
				case "chr18" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr18_tfbs.txt";
				case "chr19" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr19_tfbs.txt";
				case "chr20" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr20_tfbs.txt";
				case "chr21" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr21_tfbs.txt";
				case "chr22" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chr22_tfbs.txt";
				case "chrX" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chrX_tfbs.txt";
				case "chrY" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\tfbs\\unsorted_chrY_tfbs.txt";
			}//End of SWITCH
		}else if (Commons.HISTONE.equals(functionalElementType)){
				switch (chromName){
					case "chr1" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr1_histone.txt";
					case "chr2" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr2_histone.txt";
					case "chr3" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr3_histone.txt";
					case "chr4" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr4_histone.txt";
					case "chr5" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr5_histone.txt";
					case "chr6" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr6_histone.txt";
					case "chr7" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr7_histone.txt";
					case "chr8" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr8_histone.txt";
					case "chr9" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr9_histone.txt";
					case "chr10" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr10_histone.txt";
					case "chr11" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr11_histone.txt";
					case "chr12" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr12_histone.txt";
					case "chr13" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr13_histone.txt";
					case "chr14" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr14_histone.txt";
					case "chr15" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr15_histone.txt";
					case "chr16" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr16_histone.txt";
					case "chr17" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr17_histone.txt";
					case "chr18" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr18_histone.txt";
					case "chr19" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr19_histone.txt";
					case "chr20" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr20_histone.txt";
					case "chr21" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr21_histone.txt";
					case "chr22" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chr22_histone.txt";
					case "chrX" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chrX_histone.txt";
					case "chrY" : return Commons.OUTPUT_DATA + "Doktora\\create\\encode\\histone\\unsorted_chrY_histone.txt";
				}//End of SWITCH
			
		}
				
		
		return null;
		
	}
	
	
	//Mapability	
	public static void calculateStandardDeviationMapability(String chromName,String functionalElementType, String chromBasedMapabilityFileName, Map<String,MeanandStandardDeviation> mapabilityHashMap){
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		String strLine;
		float mapability;
		float mean;
		float squareofDifference;
		float sumofSquaresofDifferences;
		float meanofSumofSquaresofDifferences ;
		float standardDeviation;
		
		MeanandStandardDeviation values;
		
		try {
			fileReader = new FileReader(chromBasedMapabilityFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			values = mapabilityHashMap.get(chromName + "\t" + functionalElementType + "\t" + Commons.MAPABILITY);
			mean = values.getMean();
			
			//initialize
			sumofSquaresofDifferences = 0;
			
			while((strLine = bufferedReader.readLine())!=null){
				mapability = Float.parseFloat(strLine);
				squareofDifference = (mapability-mean)*(mapability-mean);
				sumofSquaresofDifferences = sumofSquaresofDifferences + squareofDifference;
			}
			
			meanofSumofSquaresofDifferences = sumofSquaresofDifferences / values.getNumberofIntervals();
			standardDeviation = (float)Math.sqrt(meanofSumofSquaresofDifferences);
			
			values.setStandardDeviation(standardDeviation);
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	//GC	
	public static void calculateStandardDeviationGC(String chromName,String functionalElementType, String chromBasedGCFileName, Map<String,MeanandStandardDeviation> gcHashMap){
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		String strLine;
		float gc;
		float mean;
		float squareofDifference;
		float sumofSquaresofDifferences;
		float meanofSumofSquaresofDifferences ;
		float standardDeviation;
		
		MeanandStandardDeviation values;
		
		try {
			fileReader = new FileReader(chromBasedGCFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			values = gcHashMap.get(chromName + "\t" + functionalElementType + "\t" + Commons.GC);
			mean = values.getMean();
			
			//initialize
			sumofSquaresofDifferences = 0;
			
			while((strLine = bufferedReader.readLine())!=null){
				gc = Float.parseFloat(strLine);
				squareofDifference = (gc-mean)*(gc-mean);
				sumofSquaresofDifferences = sumofSquaresofDifferences + squareofDifference;
			}
			
			meanofSumofSquaresofDifferences = sumofSquaresofDifferences / values.getNumberofIntervals();
			standardDeviation = (float)Math.sqrt(meanofSumofSquaresofDifferences);
			
			
			values.setStandardDeviation(standardDeviation);
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
		
	
	//GC
	public static void calculateMean(String chromName,String functionalElementType, String chromBasedInputFileName, String chromBasedGCFileName, GCCharArray gcCharArray, Map<String,MeanandStandardDeviation> gcHashMap){
			
		//do augmentation
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		FileWriter fileWriter;
		BufferedWriter  bufferedWriter;
			
		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		int low;
		int high;
		
		float gc;
		
		MeanandStandardDeviation meanandStandardDeviation =null;
		
		if (Commons.DNASE.equals(functionalElementType)){				
			meanandStandardDeviation = new MeanandStandardDeviation();
			gcHashMap.put(chromName + "\t" + Commons.DNASE + "\t" + Commons.GC, meanandStandardDeviation);
			
		}else if (Commons.TFBS.equals(functionalElementType)){
			meanandStandardDeviation = new MeanandStandardDeviation();
			gcHashMap.put(chromName + "\t" + Commons.TFBS + "\t" + Commons.GC, meanandStandardDeviation);
			
		}else if (Commons.HISTONE.equals(functionalElementType)){
			meanandStandardDeviation = new MeanandStandardDeviation();
			gcHashMap.put(chromName + "\t" + Commons.HISTONE + "\t" + Commons.GC, meanandStandardDeviation);
		}
		
		try {
			fileReader = new FileReader(chromBasedInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = new FileWriter(chromBasedGCFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
				
			while((strLine = bufferedReader.readLine())!=null){
				//example strLine
				//chrY	2709520	2709670	AG04449	AG04449-DS12319.peaks.fdr0.01.hg19.bed
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
				
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				
				gc = GC.calculateGC(low, high, gcCharArray);
				
				//accumulation
				meanandStandardDeviation.setSumofGCs(meanandStandardDeviation.getSumofGCs() + gc);
				meanandStandardDeviation.setNumberofIntervals(meanandStandardDeviation.getNumberofIntervals() + 1);
				
				
				bufferedWriter.write(gc + "\n");
			}
				
			
			//calculate mean(average) and write
			meanandStandardDeviation.setMean(meanandStandardDeviation.getSumofGCs()/meanandStandardDeviation.getNumberofIntervals());
							
			bufferedReader.close();
			bufferedWriter.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	//Mapability
	public static void calculateMean(String chromName,String functionalElementType,String chromBasedInputFileName, String chromBasedMapabilityFileName,  IntervalTree mapabilityIntervalTree, Map<String,MeanandStandardDeviation> mapabilityHashMap){
		
		//do augmentation
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		FileWriter fileWriter;
		BufferedWriter  bufferedWriter;		
		
		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		int low;
		int high;
		
		float mapability=0;
		
		MeanandStandardDeviation meanandStandardDeviation = null;
		
		if (Commons.DNASE.equals(functionalElementType)){				
			meanandStandardDeviation = new MeanandStandardDeviation();
			mapabilityHashMap.put(chromName + "\t" + Commons.DNASE + "\t" + Commons.MAPABILITY, meanandStandardDeviation);
			
		}else if (Commons.TFBS.equals(functionalElementType)){
			meanandStandardDeviation = new MeanandStandardDeviation();
			mapabilityHashMap.put(chromName + "\t" + Commons.TFBS + "\t" + Commons.MAPABILITY, meanandStandardDeviation);
			
		}else if (Commons.HISTONE.equals(functionalElementType)){
			meanandStandardDeviation = new MeanandStandardDeviation();
			mapabilityHashMap.put(chromName + "\t" + Commons.HISTONE + "\t" + Commons.MAPABILITY, meanandStandardDeviation);
		}
		
		
		try {
			fileReader = new FileReader(chromBasedInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = new FileWriter(chromBasedMapabilityFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				//example strLine
				//chrY	2709520	2709670	AG04449	AG04449-DS12319.peaks.fdr0.01.hg19.bed
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
				
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				
				mapability = Mapability.calculateMapability(low, high,mapabilityIntervalTree);
				
				//accumulation
				meanandStandardDeviation.setSumofMapabilities(meanandStandardDeviation.getSumofMapabilities()+ mapability);
				meanandStandardDeviation.setNumberofIntervals(meanandStandardDeviation.getNumberofIntervals()+ 1);
				
				
				
				bufferedWriter.write(mapability + "\n");
			}
			
			
			//take average and write
			meanandStandardDeviation.setMean(meanandStandardDeviation.getSumofMapabilities() / meanandStandardDeviation.getNumberofIntervals());
				
			
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	//todo
	
	//Mapability
	public static void calculateMeanandStandardDeviationofChromosomeBasedFunctionalElementMapability(List<Integer> hg19ChromosomeSizes, Map<String,MeanandStandardDeviation> mapabilityHashMap){ 
		String chromName;
		int chromSize;
		String chromBasedInputFileName;
		String chromBasedMapabilityFileName;
		IntervalTree mapabilityIntervalTree;
		
		for(int i= 1 ; i<=Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
			
			
    		chromName = GRCh37Hg19Chromosome.getChromosomeName(i);
			chromSize = hg19ChromosomeSizes.get(i-1);
			
			System.out.println("chromosome name:" + chromName + " chromosome size: " + chromSize);
			
			mapabilityIntervalTree = ChromosomeBasedMapabilityIntervalTree.getChromosomeBasedMapabilityIntervalTree(chromName, chromSize);
			
			//DNase
			chromBasedInputFileName = getChromosomeBasedFunctionalElementInputFileName(Commons.DNASE, chromName);
			chromBasedMapabilityFileName = Commons.OUTPUT_DATA + "Doktora\\mapabilityandgc\\Augmentation\\ChromosomeBased\\Dnase\\"  + chromName + "_dnase_mapability.txt";
			calculateMean(chromName,Commons.DNASE,chromBasedInputFileName,chromBasedMapabilityFileName, mapabilityIntervalTree,mapabilityHashMap);
			calculateStandardDeviationMapability(chromName, Commons.DNASE, chromBasedMapabilityFileName, mapabilityHashMap);
			
			//Tfbs
			chromBasedInputFileName = getChromosomeBasedFunctionalElementInputFileName(Commons.TFBS, chromName);
			chromBasedMapabilityFileName = Commons.OUTPUT_DATA + "Doktora\\mapabilityandgc\\Augmentation\\ChromosomeBased\\Tfbs\\"  + chromName + "_tfbs_mapability.txt";
			calculateMean(chromName,Commons.TFBS,chromBasedInputFileName,chromBasedMapabilityFileName, mapabilityIntervalTree,mapabilityHashMap);
			calculateStandardDeviationMapability(chromName, Commons.TFBS, chromBasedMapabilityFileName, mapabilityHashMap);
			
			//Histone
			chromBasedInputFileName = getChromosomeBasedFunctionalElementInputFileName(Commons.HISTONE, chromName);
			chromBasedMapabilityFileName = Commons.OUTPUT_DATA + "Doktora\\mapabilityandgc\\Augmentation\\ChromosomeBased\\Histone\\"  + chromName + "_histone_mapability.txt";
			calculateMean(chromName,Commons.HISTONE,chromBasedInputFileName,chromBasedMapabilityFileName, mapabilityIntervalTree,mapabilityHashMap);
			calculateStandardDeviationMapability(chromName, Commons.HISTONE, chromBasedMapabilityFileName, mapabilityHashMap);
			
			mapabilityIntervalTree=null;
		
		}//End of FOR
	}
	//todo
	
	
	//GC
	public static void calculateMeanandStandardDeviationofChromosomeBasedFunctionalElementGC(List<Integer> hg19ChromosomeSizes,Map<String,MeanandStandardDeviation> gcHashMap){
		String chromName;
		int chromSize;
		String chromBasedInputFileName;
		String chromBasedGCFileName;
		GCCharArray gcCharArray;
		
		
		for(int i= 1 ; i<=Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
			
//			//initialize for each chromosome
//			AverageValues averageValues = new AverageValues();
			
    		chromName = GRCh37Hg19Chromosome.getChromosomeName(i);
			chromSize = hg19ChromosomeSizes.get(i-1);
			
			System.out.println("chromosome name:" + chromName + " chromosome size: " + chromSize);
			
			gcCharArray = ChromosomeBasedGCArray.getChromosomeGCArray(chromName, chromSize);

			//DNase
			chromBasedInputFileName = getChromosomeBasedFunctionalElementInputFileName(Commons.DNASE, chromName);
			chromBasedGCFileName = Commons.OUTPUT_DATA + "Doktora\\mapabilityandgc\\Augmentation\\ChromosomeBased\\Dnase\\" + chromName + "_dnase_gc.txt";
			calculateMean(chromName,Commons.DNASE,chromBasedInputFileName,chromBasedGCFileName, gcCharArray,gcHashMap);
			calculateStandardDeviationGC(chromName,Commons.DNASE,chromBasedGCFileName,gcHashMap);
			
			//Tfbs
			chromBasedInputFileName = getChromosomeBasedFunctionalElementInputFileName(Commons.TFBS, chromName);
			chromBasedGCFileName = Commons.OUTPUT_DATA + "Doktora\\mapabilityandgc\\Augmentation\\ChromosomeBased\\Tfbs\\" + chromName + "_tfbs_gc.txt";
			calculateMean(chromName,Commons.TFBS,chromBasedInputFileName,chromBasedGCFileName, gcCharArray,gcHashMap);
			calculateStandardDeviationGC(chromName,Commons.TFBS, chromBasedGCFileName,gcHashMap);
			
			//Histone
			chromBasedInputFileName = getChromosomeBasedFunctionalElementInputFileName(Commons.HISTONE, chromName);
			chromBasedGCFileName = Commons.OUTPUT_DATA + "Doktora\\mapabilityandgc\\Augmentation\\ChromosomeBased\\Histone\\" + chromName + "_histone_gc.txt";
			calculateMean(chromName,Commons.HISTONE,chromBasedInputFileName,chromBasedGCFileName, gcCharArray,gcHashMap);
			calculateStandardDeviationGC(chromName,Commons.HISTONE, chromBasedGCFileName,gcHashMap);
			
			gcCharArray.setGcArray(null);
		
		}//End of FOR
	}


	public static void writeMeanandStdDevResultstoFiles(String chromBasedMeanandStdDevofGcFileName,Map<String,MeanandStandardDeviation> hashMap){
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		try {
			fileWriter = new FileWriter(chromBasedMeanandStdDevofGcFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			String name;
			MeanandStandardDeviation values;
			
			for (Map.Entry<String, MeanandStandardDeviation> entry: hashMap.entrySet()){
				name = entry.getKey();
				values = entry.getValue();

				bufferedWriter.write(name + "\t" + "number of intervals" + "\t" + values.getNumberofIntervals() + "\t" + "mean" + "\t" + values.getMean() + "\t" + "std dev" + "\t" +values.getStandardDeviation() + "\n");
				 
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<Integer> hg19ChromosomeSizes = new ArrayList<Integer>();
		GRCh37Hg19Chromosome.initializeChromosomeSizes(hg19ChromosomeSizes);
    	//get the hg19 chromosome sizes
    	GRCh37Hg19Chromosome.getHg19ChromosomeSizes(hg19ChromosomeSizes, Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
    	
    	String chromBasedMeanandStdDevofGcFileName = Commons.OUTPUT_DATA + "Doktora\\mapabilityandgc\\Augmentation\\ChromosomeBased\\" + "mean_and_standard_deviation_of_gc_of_chromosome_based_dnase_tfbs_histone_files.txt";
    	String chromBasedMeanandStdDevofMapabilityFileName = Commons.OUTPUT_DATA + "Doktora\\mapabilityandgc\\Augmentation\\ChromosomeBased\\" + "mean_and_standard_deviation_of_mapability_of_chromosome_based_dnase_tfbs_histone_files.txt";
    	
    	Map<String,MeanandStandardDeviation> gcHashMap = new HashMap<String,MeanandStandardDeviation>();
    	Map<String,MeanandStandardDeviation> mapabilityHashMap = new HashMap<String,MeanandStandardDeviation>();

    	calculateMeanandStandardDeviationofChromosomeBasedFunctionalElementGC(hg19ChromosomeSizes,gcHashMap);
    	writeMeanandStdDevResultstoFiles(chromBasedMeanandStdDevofGcFileName,gcHashMap);
    	
    	calculateMeanandStandardDeviationofChromosomeBasedFunctionalElementMapability(hg19ChromosomeSizes,mapabilityHashMap);
    	writeMeanandStdDevResultstoFiles(chromBasedMeanandStdDevofMapabilityFileName,mapabilityHashMap);
		
	}

}
