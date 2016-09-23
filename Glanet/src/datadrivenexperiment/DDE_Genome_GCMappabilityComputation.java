/**
 * 
 */
package datadrivenexperiment;

import enumtypes.CalculateGC;
import enumtypes.ChromosomeName;
import gc.ChromosomeBasedGCIntervalTree;
import gc.GC;
import generate.randomdata.RandomDataGenerator;
import gnu.trove.iterator.TIntFloatIterator;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.iterator.TIntLongIterator;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TShortArrayList;
import gnu.trove.map.TIntFloatMap;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntLongMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntFloatHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntLongHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import intervaltree.Interval;
import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import mapability.ChromosomeBasedMappabilityTroveList;
import mapability.Mapability;
import auxiliary.FileOperations;

import common.Commons;


/**
 * @author Burçak Otlu
 * @date Sep 23, 2016
 * @project Glanet 
 *
 */
public class DDE_Genome_GCMappabilityComputation {


	public DDE_Genome_GCMappabilityComputation() {

	}
	
	
	public static void calculateGCMappability(
			String glanetDataFolder,
			String glanetDDEFolder,
			TIntObjectMap<List<Interval>> chrNumber2RandomIntervalsMap){
		
		FileWriter gcFileWriter = null;
		BufferedWriter gcBufferedWriter = null;
		
		FileWriter mappabilityFileWriter = null;
		BufferedWriter mappabilityBufferedWriter = null;
		
		int chrNumber;
		List<Interval> intervalList;
		Interval interval = null;
		
		TIntList mapabilityChromosomePositionList = null;
		TShortList mapabilityShortValueList = null;
		IntervalTree gcIntervalTree = null;

		float mappability;
		float gc;
		
		float avgMappability = 0f;
		float avgGC = 0f;
		
		int numberofIntervals = 0;
		
		
		/*************************************************************************************************/
		/*******************Get GC and mappability data structures being filled starts********************/
		/*************************************************************************************************/
		TIntObjectMap<TIntList> chrName2MapabilityChromosomePositionList = new TIntObjectHashMap<TIntList>();
		TIntObjectMap<TShortList> chrName2MapabilityShortValueList = new TIntObjectHashMap<TShortList>();
		TIntObjectMap<IntervalTree> chrName2GCIntervalTreeMap = new TIntObjectHashMap<IntervalTree>();

		for(ChromosomeName chrName : ChromosomeName.values() ){
			
			/*************************************************/
			/***************Mappability starts****************/
			/*************************************************/
			mapabilityChromosomePositionList = new TIntArrayList();
			mapabilityShortValueList = new TShortArrayList();
		
			// GLANET Always Fills
			// for Mappability
			// mapabilityChromosomePositionList
			// mapabilityShortValueList
			ChromosomeBasedMappabilityTroveList.fillTroveList( 
					glanetDataFolder, 
					chrName,
					mapabilityChromosomePositionList, 
					mapabilityShortValueList);
			
			//Put into a map
			chrName2MapabilityChromosomePositionList.put(chrName.getChromosomeName(), mapabilityChromosomePositionList);
			chrName2MapabilityShortValueList.put(chrName.getChromosomeName(), mapabilityShortValueList);
			/*************************************************/
			/***************Mappability ends******************/
			/*************************************************/

			
			/*************************************************/
			/***************GC starts*************************/
			/*************************************************/
			gcIntervalTree = new IntervalTree();
			
			ChromosomeBasedGCIntervalTree.fillIntervalTree(glanetDataFolder,chrName,Commons.INTERVAL_LENGTH_100,gcIntervalTree);
			
			//Put into a map
			chrName2GCIntervalTreeMap.put(chrName.getChromosomeName(),gcIntervalTree);
			/*************************************************/
			/***************GC ends***************************/
			/*************************************************/

		}//End of for each chromosome
		/*************************************************************************************************/
		/*******************Get GC and mappability data structures being filled ends**********************/
		/*************************************************************************************************/

		
		try {
			
			gcFileWriter = FileOperations.createFileWriter(glanetDDEFolder + Commons.GC + "_NullDistribution.txt");
			gcBufferedWriter = new BufferedWriter(gcFileWriter);
			
			mappabilityFileWriter = FileOperations.createFileWriter(glanetDDEFolder + Commons.MAPPABILITY + "_NullDistribution.txt");
			mappabilityBufferedWriter = new BufferedWriter(mappabilityFileWriter);
			
			for(TIntObjectIterator<List<Interval>>  itr=chrNumber2RandomIntervalsMap.iterator();itr.hasNext();){
				
				itr.advance();
				
				chrNumber = itr.key();
				intervalList = itr.value();
				
				numberofIntervals += intervalList.size();
				
				for(int i=0; i<intervalList.size(); i++){
					
					
					interval = intervalList.get(i);
					
					/**************************************************************************************************/
					/***************************Filling of MAPPABILITY Data Structures starts**************************/
					/**************************************************************************************************/
					mapabilityChromosomePositionList = chrName2MapabilityChromosomePositionList.get(chrNumber);
					mapabilityShortValueList = chrName2MapabilityShortValueList.get(chrNumber);
					/**************************************************************************************************/
					/***************************Filling of MAPPABILITY Data Structures ends****************************/
					/**************************************************************************************************/
					
					
					/**************************************************************************************************/
					/**********************MAPABILITY Calculation starts***********************************************/
					/**************************************************************************************************/
					mappability = Mapability.calculateMapabilityofIntervalUsingTroveList(interval, mapabilityChromosomePositionList, mapabilityShortValueList);
					/**************************************************************************************************/
					/**********************MAPABILITY Calculation ends*************************************************/
					/**************************************************************************************************/
					
											
					/**************************************************************************************************/
					/***************************Filling of GC Data Structures starts***********************************/
					/**************************************************************************************************/
					gcIntervalTree = chrName2GCIntervalTreeMap.get(chrNumber);
					/**************************************************************************************************/
					/***************************Filling of GC Data Structures ends*************************************/
					/**************************************************************************************************/
				
					/**************************************************************************************************/
					/**********************GC Calculation starts*******************************************************/
					/**************************************************************************************************/
					gc = GC.calculateGCofIntervalUsingIntervalTree(interval, gcIntervalTree,CalculateGC.CALCULATE_GC_USING_GC_INTERVAL_TREE);
					/**************************************************************************************************/
					/**********************GC Calculation ends*********************************************************/
					/**************************************************************************************************/
	
					avgMappability += mappability;
					avgGC += gc;
					
					mappabilityBufferedWriter.write(mappability  + "\t" +  "Mappability_NullDistribution" +  System.getProperty("line.separator"));				
					gcBufferedWriter.write(gc + "\t" +  "GC_NullDistribution" + System.getProperty("line.separator"));
	
					
				}//End of for each intervalList for each chromosome
				
			}//End of for each chromosome
			
			avgMappability = avgMappability/numberofIntervals;
			avgGC = avgGC/numberofIntervals;
			
			System.out.println("Number of intervals: " + numberofIntervals);
			
			System.out.println("avgGC: " + avgGC + " avgMappability: " + avgMappability);
		
		
		//Close
		gcBufferedWriter.close();
		mappabilityBufferedWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void fillNumberofIntervalsPerChromsome(
			int numberofIntervals,
			TIntFloatMap chrName2ChromosomeProportionMap,
			TIntIntMap chrName2NumberofIntervalsMap){
		
		int chrNumber;
		float proportion;
		
		int numberOfIntervalsPerChromosome;
		int numberofIntervalsAfterProportionsCalculations = 0;
		
		for(TIntFloatIterator itr=chrName2ChromosomeProportionMap.iterator(); itr.hasNext();){
			
			itr.advance();
			
			chrNumber = itr.key();
			proportion = itr.value();
			
			numberOfIntervalsPerChromosome = Math.round(proportion*numberofIntervals);
			
			numberofIntervalsAfterProportionsCalculations += numberOfIntervalsPerChromosome;
			
			chrName2NumberofIntervalsMap.put(chrNumber,numberOfIntervalsPerChromosome);
			
		}//End of for each chromosome
		
		System.out.println("Aim was: " + numberofIntervals + " intervals, however " + numberofIntervalsAfterProportionsCalculations + " intervals will be generated.");
	}
	
	

	//Fill the Map as each chromosome is represented as its chromosome length
	public static void fillChromosomeProportions(
			String glanetDataFolder,
			TIntLongMap chrName2ChromsomeSizeMap,
			TIntFloatMap chrName2ChromosomeProportionMap){
		

		String strLine;
		int indexoFirstTab;
		
		ChromosomeName chrName = null;
		Long chrLength = 0l;
		Long genomeLength = 0l; 
		
		String HG19_CHROMOSOME_SIZES_INPUT_FILE = Commons.FTP + System.getProperty( "file.separator") + "HG19_CHROM_SIZES" + System.getProperty( "file.separator") + "hg19.chrom.sizes.txt";
		
		FileReader fileReader = null;
		BufferedReader bufferedReader =null;
		
		try {
			
			fileReader = FileOperations.createFileReader(glanetDataFolder + HG19_CHROMOSOME_SIZES_INPUT_FILE);
			bufferedReader = new BufferedReader(fileReader);
			
			for(int i=0; i<24; i++){
				
				if ((strLine= bufferedReader.readLine())!=null){
					
					indexoFirstTab = strLine.indexOf("\t");
					
					chrName = ChromosomeName.convertStringtoEnum(strLine.substring(0, indexoFirstTab));
					chrLength = Long.parseLong(strLine.substring(indexoFirstTab+1));
					
					chrName2ChromsomeSizeMap.put(chrName.getChromosomeName(), chrLength);
					
					genomeLength += chrLength;
					
				}//End of if strLine is not null
				
			}//End of for each chromosome

			//Close
			bufferedReader.close();
			
			//Now fill chromosomeProportions
			for(TIntLongIterator itr = chrName2ChromsomeSizeMap.iterator();itr.hasNext();){
				itr.advance();
				
				int chrNumber = itr.key();
				chrLength = itr.value();
				
				chrName2ChromosomeProportionMap.put(chrNumber, chrLength*1f/genomeLength);
				
			}
			
		} catch (IOException e) {
				e.printStackTrace();
		}
				
	}

	
	public static void generateRandomIntervalsFromWholeGenome(
			String glanetDataFolder,
			int numberofIntervals,
			int lengthofEachInterval,
			TIntObjectMap<List<Interval>> chrNumber2RandomIntervalsMap){
		
		TIntLongMap chrName2ChromsomeSizeMap = new TIntLongHashMap();
		TIntFloatMap chrName2ChromosomeProportionMap = new TIntFloatHashMap();
		TIntIntMap chrName2NumberofIntervalsMap = new TIntIntHashMap();
		
		Interval interval = null;
		
		fillChromosomeProportions(glanetDataFolder,chrName2ChromsomeSizeMap,chrName2ChromosomeProportionMap);
		
		fillNumberofIntervalsPerChromsome(numberofIntervals,chrName2ChromosomeProportionMap,chrName2NumberofIntervalsMap);
		
		int chrNumber;
		int numberofIntervalsPerChromsome;
		
		for(TIntIntIterator itr=chrName2NumberofIntervalsMap.iterator(); itr.hasNext();){
			itr.advance();
			
			chrNumber = itr.key();
			numberofIntervalsPerChromsome = itr.value();
			
			List<Interval> intervalList = new ArrayList<Interval>();
			
			for (int i=0; i<numberofIntervalsPerChromsome; i++){
				
						interval = RandomDataGenerator.getRandomInterval((int)chrName2ChromsomeSizeMap.get(chrNumber), lengthofEachInterval, ThreadLocalRandom.current());
						intervalList.add(interval);
						
			}//End of generate random interval for each chromosome
			
			chrNumber2RandomIntervalsMap.put(chrNumber, intervalList);
			
		}//End of for each chromosome
		

		
	}
	
	
	public static void main(String[] args) {
		
		String glanetFolder = args[0];
		String glanetDataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");		
		String glanetDDEFolder = glanetFolder + Commons.DDE + System.getProperty("file.separator");
		
		int numberofIntervals = Integer.parseInt(args[1]);
		int lengthofEachInterval = Integer.parseInt(args[2]);
		
		TIntObjectMap<List<Interval>> chrNumber2RandomIntervalsMap = new TIntObjectHashMap<List<Interval>>();
		
		generateRandomIntervalsFromWholeGenome(glanetDataFolder,numberofIntervals,lengthofEachInterval,chrNumber2RandomIntervalsMap);
		
		calculateGCMappability(glanetDataFolder,glanetDDEFolder,chrNumber2RandomIntervalsMap);

	}

}
