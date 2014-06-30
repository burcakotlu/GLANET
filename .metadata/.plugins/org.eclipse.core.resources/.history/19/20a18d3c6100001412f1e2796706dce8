 /**
 * @author Burcak Otlu Saritas
 *
 * 
 */

/*
 * 
 * 
 * 
 * This program calculates the number of nonoverlapping base pairs in whole genome using sliding window
 * for each dnase cell line name,tfbs, histone.
 * 
 * This program requires sorted chromosome based dnase, tfbs and histone files.
 * 
 * Please note that if interval tree was used, I would not need sorted chromosome based dnase, tfbs and histone files.
 * 
 * Unsorted dnase, tfbs and histone files would be enough.
 * 
 * 
 */

package wholegenome.nonoverlappingbasepairs.usingslidingwindow;

import hg19.GRCh37Hg19Chromosome;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import keggpathway.ncbigenes.KeggPathwayUtility;
import ui.GlanetRunner;
import common.Commons;

public class CalculateNumberofNonOverlappingBasePairsinWholeGenomeUsingSlidingWindow {
	
	public static Integer WINDOW_SIZE = new Integer(10000); 
	public static String EMPTY_STRING = ""; 	
	
	
	
	
	public void printChromosomeSizes(List<Integer> chromosomeSizes){
		GlanetRunner.appendLog("For information : Chromosome Sizes");
		
		for(int i =0; i<chromosomeSizes.size(); i++){
			if (i == 22)
				System.out.printf("ChrX \t %d" + System.getProperty("line.separator"), chromosomeSizes.get(i));
			else if (i == 23)
				System.out.printf("ChrY \t %d" + System.getProperty("line.separator"), chromosomeSizes.get(i));
			else
				System.out.printf("Chr%d \t %d" + System.getProperty("line.separator"), i+1, chromosomeSizes.get(i));
				
		}
	}

	

	public void initializeChromBaseList(List<List<String>> chromBaseWindowList,int windowSize){
		for(int i =0; i<windowSize; i++){
			chromBaseWindowList.add(i,new ArrayList<String>());
		}		
	}
	
	public boolean overlaps(int low_x, int high_x, int low_y, int high_y ){
		if (( low_x <= high_y) && (low_y <= high_x))
			return true;
		else 
			return false;
	}
	
	public int maximum(int x, int y){
		
		int max = x;
	
		if (y>max) 
			max = y;
		
		return max;
	}

	public int minimum(int x, int y){
		
		int min = x;
	
		if (y<min) 
			min = y;
		
		return min;
	}
	
	//Do this at the end
	public void accumulateAllRemainingNamesandRemoveAllWindows(Map<Integer,Window> necessaryChromosomeWindowsMap, Map<String,Long> name2NumberofNonOverlappingBasePairsMap){
		Names names = null;
		String name = null;
	
		//accumulate names
		//and then remove that window from the necessary Chromosome Windows Map
				
		Set<Map.Entry<Integer, Window>>  windowSet = necessaryChromosomeWindowsMap.entrySet();
		Iterator<Map.Entry<Integer, Window>> itr = windowSet.iterator();
		
		while(itr.hasNext()){
				//get the next object
				Map.Entry<Integer, Window> key2WindowPair = (Map.Entry<Integer, Window>)itr.next();
				
				//get the window
				Window window = key2WindowPair.getValue();
				int windowSize = window.getWindowBases().size();
				
				//accumulate names in window into name2NumberofNonOverlappingBasePairsMap
				for(int i =0; i<windowSize; i++){
					names = window.getWindowBases().get(i);
					
					for(int j= 0; j<names.getNames().size(); j++){
						name = names.getNames().get(j);
						
						if (name2NumberofNonOverlappingBasePairsMap.get(name) == null){
							name2NumberofNonOverlappingBasePairsMap.put(name, Commons.LONG_ONE);
						}else {
							name2NumberofNonOverlappingBasePairsMap.put(name, name2NumberofNonOverlappingBasePairsMap.get(name)+1);
						}
					}//end of for: all the names in a specific window base
				}// end of for: all the window bases
								
				
				//remove this window
				itr.remove();	
			
		}
			
	}

	public void accumulateNamesandRemoveUnnecessaryChromosomeWindows(int low,Map<Integer,Window> necessaryChromosomeWindowsMap, Map<String,Long> name2NumberofNonOverlappingBasePairsMap){
		
		Names names = null;
		String name = null;
	
		//if window's high is less than the read input line's low
		//since the input is sorted  by low attribute in ascending order
		//accumulate Names first
		//and then remove that window from the necessary Chromosome Windows Map
		Set<Map.Entry<Integer, Window>>  windowSet = necessaryChromosomeWindowsMap.entrySet();
		Iterator<Map.Entry<Integer, Window>> itr = windowSet.iterator();
		
		while(itr.hasNext()){
			Map.Entry<Integer, Window> key2WindowPair = (Map.Entry<Integer, Window>) itr.next();
			
			if (key2WindowPair.getValue().getWindowHigh() < low){
				
				//get the window
				Window window = key2WindowPair.getValue();
				
				int windowSize = window.getWindowBases().size();
				
				//accumulate names in window into name2NumberofNonOverlappingBasePairsMap
				for(int i =0; i<windowSize; i++){
					names = window.getWindowBases().get(i);
					
					for(int j= 0; j<names.getNames().size(); j++){
						name = names.getNames().get(j);
						
						if (name2NumberofNonOverlappingBasePairsMap.get(name) == null){
							name2NumberofNonOverlappingBasePairsMap.put(name, Commons.LONG_ONE);
						}else {
							name2NumberofNonOverlappingBasePairsMap.put(name, name2NumberofNonOverlappingBasePairsMap.get(name)+1);
						}
					}//end of for: all the names in a specific window base
				}// end of for: all the window bases
				
				
				//remove this window
				itr.remove();	
			}//end of if: window's high is less than last read input line's low
		}
		
		
	}
	
	public void calculateDnaseNumberofNonoverlappingBasePairs(int chromosomeNumber, int chromosomeSize, Map<String,Long> dnaseCellLine2NumberofNonOverlappingBasePairsMap,String inputFileName){
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		
		int low = 0;
		int high = 0;
		String cellLineName = null;
			
		System.out.printf("Dnase chromosome%d" + System.getProperty("line.separator") , chromosomeNumber);
		
		List<UnprocessedLine> unprocessedLines = new ArrayList<UnprocessedLine>();
		UnprocessedLine unprocessedLine;
		
		try {
			fileReader = new FileReader(inputFileName);								
			bufferedReader = new BufferedReader(fileReader);
			
			
			Window currentWindow = null;
			Names dnaseCellLineNames = null;
			Map<Integer,Window> necessaryChromosomeWindowsMap = new HashMap<Integer,Window>();
			
			//In this map, First integer indicated the number of window (example jth window), second integer indicates whether it is already created or not.
			//1 means it is already created.
			//0 means it has not been created.
			Map<Integer,Integer> alreadyCreatedWindows = new HashMap<Integer,Integer>();
			
			int allowedWindowLow;
			int allowedWindowHigh;
			
			//window will move forward and backward on the chromosome 
			//it will consume all the data
			//then the results will be accumulated in dnaseCellLine2NumberofNonOverlappingBasePairsMap
			
			//chrom size and data in sorted chromosome based files are not compatible with each other
			//there are intervals in sorted chromosome based files exceeding the chrom size
			//therefore jthWindow<= numberofWindows is removed
			for(int jthWindow = 0; ;){
				
//				GlanetRunner.appendLog("jth\t" + jthWindow + "\twindow");,
				
				
				allowedWindowLow = jthWindow * WINDOW_SIZE;
				allowedWindowHigh = allowedWindowLow + WINDOW_SIZE-1; 
				
				//get the current window
				//from the necessaryChromosomeWindowsMap if it is already created
				//or create a new one
				
				if(necessaryChromosomeWindowsMap.get(jthWindow)==null && alreadyCreatedWindows.get(jthWindow)==null ){
					currentWindow = new Window(WINDOW_SIZE,allowedWindowLow,allowedWindowHigh,jthWindow);
					
					
					if (alreadyCreatedWindows.get(jthWindow)==null){
					 alreadyCreatedWindows.put(jthWindow, Commons.ONE);
					}
					
					for(int i =0; i<WINDOW_SIZE ; i++){
						dnaseCellLineNames = new Names();
						currentWindow.getWindowBases().add(dnaseCellLineNames);
					}
					
					//add it to the necessaryChromosomeWindowsMap
					necessaryChromosomeWindowsMap.put(jthWindow, currentWindow);
				
					
				}else{
					currentWindow = necessaryChromosomeWindowsMap.get(jthWindow);
					
				}
				
				if((unprocessedLines.size()>0)){
					
					unprocessedLine = unprocessedLines.get(0);
					low = unprocessedLine.getLow();
					high = unprocessedLine.getHigh();
					cellLineName = unprocessedLine.getName();
					
					//process the overlap
					if(overlaps(low,high,allowedWindowLow,allowedWindowHigh)){
						for(int j = maximum(low,allowedWindowLow); j <= minimum(high,allowedWindowHigh); j++ ){	
							dnaseCellLineNames = currentWindow.getWindowBases().get(j-(jthWindow*WINDOW_SIZE));
							if (!(dnaseCellLineNames.getNames().contains(cellLineName))){										
								dnaseCellLineNames.getNames().add(cellLineName);
								currentWindow.setNumberofAllNonoverlappingBases(currentWindow.getNumberofAllNonoverlappingBases()+1);
							}											
						}									
					}//end of if: process overlap
					
					if (low>allowedWindowHigh){
						//move window forward
						jthWindow++;
					}else if(high<allowedWindowLow){
						//move window backward
						jthWindow--;
					}else if (low>=allowedWindowLow &&  high <= allowedWindowHigh) {
						//we have processed the line successfully
						//Now we can read a new input line
						unprocessedLines.remove(unprocessedLine);
						
					}else if (low<allowedWindowLow && high<=allowedWindowHigh){
						high= allowedWindowLow-1;
						unprocessedLines.remove(unprocessedLine);
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,cellLineName));
						//move window backward
						jthWindow--;
					}
					else if(high>allowedWindowHigh && low >=allowedWindowLow){
						low = allowedWindowHigh +1;
						unprocessedLines.remove(unprocessedLine);
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,cellLineName));
						//move window forward
						jthWindow++;
					}else if (low<allowedWindowLow && high>allowedWindowHigh){
						//there are two new degenerated lines
						//one is [low,allowedWindowLow-1]
						//second is [allowedWindowHigh+1, high]
						//both of them must be processed
						unprocessedLines.remove(unprocessedLine);					
						unprocessedLines.add(new UnprocessedLine(low,allowedWindowLow-1,Commons.DEGENERATED_LINE,cellLineName));
						unprocessedLines.add(new UnprocessedLine(allowedWindowHigh+1, high,Commons.DEGENERATED_LINE,cellLineName));
						//just give a direction
						//increment or decrement
						//does not matter
						//just avoid infinite loop
						//it will finds its window
						jthWindow--;
							
					}
				}//end of if there is unprocessed line
					
				if (unprocessedLines.size()==0){
						while((strLine = bufferedReader.readLine())!=null){
						
						//chr1	91852781	91853156	GM12878	idrPool.GM12878-DS9432-DS10671.z_OV_GM12878-DS10671.z_VS_GM12878-DS9432.z.npk2.narrowPeak
						indexofFirstTab = strLine.indexOf('\t');
						indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
						
						low = Integer.valueOf(strLine.substring(indexofFirstTab+1, indexofSecondTab));
						high =Integer.valueOf(strLine.substring(indexofSecondTab+1, indexofThirdTab));
						cellLineName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						
						
						//process the overlap
						if(overlaps(low,high,allowedWindowLow,allowedWindowHigh)){
							for(int j = maximum(low,allowedWindowLow); j <= minimum(high,allowedWindowHigh); j++ ){	
								dnaseCellLineNames = currentWindow.getWindowBases().get(j-(jthWindow*WINDOW_SIZE));
								if (!(dnaseCellLineNames.getNames().contains(cellLineName))){										
									dnaseCellLineNames.getNames().add(cellLineName);
									currentWindow.setNumberofAllNonoverlappingBases(currentWindow.getNumberofAllNonoverlappingBases()+1);
								}
							}									
						}//end of if: process overlap
							
						
						if (low>allowedWindowHigh){
							unprocessedLines.add(new UnprocessedLine(low,high,Commons.ORIGINAL_READ_LINE,cellLineName));
							//move window forward
							jthWindow++;
							break;
						}else if(high<allowedWindowLow){
							unprocessedLines.add(new UnprocessedLine(low,high,Commons.ORIGINAL_READ_LINE,cellLineName));						
							//move window backward
							jthWindow--;
							break;
						}else if (low>=allowedWindowLow &&  high <= allowedWindowHigh) {
							//Now we can read a new input line
						}else if (low<allowedWindowLow && high<=allowedWindowHigh){
							high= allowedWindowLow-1;
							unprocessedLines.add(new UnprocessedLine(low, high,Commons.DEGENERATED_LINE,cellLineName));
							//move window backward
							jthWindow--;
							break;
						}
						else if(high>allowedWindowHigh && low >=allowedWindowLow){
							low = allowedWindowHigh +1;
							unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,cellLineName));
							//move window forward
							jthWindow++;
							break;
						}else if (low<allowedWindowLow && high>allowedWindowHigh){
							//there are two new degenerated lines
							//one is [low,allowedWindowLow-1]
							//second is [allowedWindowHigh+1, high]
							//both of them must be processed
							unprocessedLines.add(new UnprocessedLine(low,allowedWindowLow-1,Commons.DEGENERATED_LINE,cellLineName));
							unprocessedLines.add(new UnprocessedLine(allowedWindowHigh+1, high,Commons.DEGENERATED_LINE,cellLineName));
							//just give a direction
							//increment or decrement
							//does not matter
							//just avoid infinite loop
							//it will finds its window
							jthWindow--;
							break;
							
						}

						
					
					}//end of while: read line one by one
				}// end of if : there is no unprocessed input line
				
				
				//there is no artificially created input line to be processed
				//so the unnecessary windows can be deleted.
				if(!unprocessedLinesContainsDegeneratedLines(unprocessedLines)){
						//first accumulate the dnaseCellLineNames stored in unnecessary Chromosome Windows
					//and then remove the unnecessary windows from the necessaryChromosomeWindowsMap
					
					//there must be one original read line or none
					if (unprocessedLines.size()==1 && unprocessedLines.get(0).getType().equals(Commons.ORIGINAL_READ_LINE)){
						low = unprocessedLines.get(0).getLow();
						accumulateNamesandRemoveUnnecessaryChromosomeWindows(low,necessaryChromosomeWindowsMap,dnaseCellLine2NumberofNonOverlappingBasePairsMap);
						
					}
					
				}
				
				if(strLine == null){
					// we have consumed all the input data
					accumulateAllRemainingNamesandRemoveAllWindows(necessaryChromosomeWindowsMap,dnaseCellLine2NumberofNonOverlappingBasePairsMap);
					break;
				}
				
			}//end of For: each jth window on the chromosome
			
			
			bufferedReader.close();
					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
	}
	
	
public void calculateTfbsNumberofNonoverlappingBasePairs(int chromosomeNumber, int chromosomeSize, Map<String,Long> tfbsNameandCellLineName2NumberofNonoverlappingBasePairsWholeGenomeMap,String inputFileName){
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		
		int low = 0;
		int high = 0;
		String tfbsName = null;
		String cellLineName;
		String tfbsNameandCellLineName;
		
		System.out.printf("Tfbs chromosome%d" + System.getProperty("line.separator") , chromosomeNumber);
		
		List<UnprocessedLine> unprocessedLines = new ArrayList<UnprocessedLine>();
		UnprocessedLine unprocessedLine;
		
		
		try {
			fileReader = new FileReader(inputFileName);								
			bufferedReader = new BufferedReader(fileReader);
			
				
			Window currentWindow = null;
			Names tfbsandCellLineNames = null;
			Map<Integer,Window> necessaryChromosomeWindowsMap = new HashMap<Integer,Window>();
			
			//In this map, First integer indicated the number of window (example jth window), second integer indicates whether it is already created or not.
			//1 means it is already created.
			//0 means it has not been created.
			Map<Integer,Integer> alreadyCreatedWindows = new HashMap<Integer,Integer>();
			
			int allowedWindowLow;
			int allowedWindowHigh;
			
			//window will move forward and backward on the chromosome 
			//it will consume all the data
			//then the results will be accumulated in dnaseCellLine2NumberofNonOverlappingBasePairsMap
			
			//chrom size and data in sorted chromosome based files are not compatible with each other
			//there are intervals in sorted chromosome based files exceeding the chrom size
			//therefore jthWindow<= numberofWindows is removed			
			for(int jthWindow = 0; ;){
				
//				GlanetRunner.appendLog("jth\t" + jthWindow + "\twindow");,
				
				
				allowedWindowLow = jthWindow * WINDOW_SIZE;
				allowedWindowHigh = allowedWindowLow + WINDOW_SIZE-1; 
				
				//get the current window
				//from the necessaryChromosomeWindowsMap if it is already created
				//or create a new one
				
				if(necessaryChromosomeWindowsMap.get(jthWindow)==null && alreadyCreatedWindows.get(jthWindow)==null ){
					currentWindow = new Window(WINDOW_SIZE,allowedWindowLow,allowedWindowHigh,jthWindow);
					
					
					if (alreadyCreatedWindows.get(jthWindow)==null){
					 alreadyCreatedWindows.put(jthWindow, Commons.ONE);
					}
					
					for(int i =0; i<WINDOW_SIZE ; i++){
						tfbsandCellLineNames = new Names();
						currentWindow.getWindowBases().add(tfbsandCellLineNames);
					}
					
					//add it to the necessaryChromosomeWindowsMap
					necessaryChromosomeWindowsMap.put(jthWindow, currentWindow);
				
					
				}else{
					currentWindow = necessaryChromosomeWindowsMap.get(jthWindow);
					
				}
				
				if((unprocessedLines.size()>0)){
					
					unprocessedLine = unprocessedLines.get(0);
					low = unprocessedLine.getLow();
					high = unprocessedLine.getHigh();
					tfbsNameandCellLineName = unprocessedLine.getName();
				
					//process the overlap
					if(overlaps(low,high,allowedWindowLow,allowedWindowHigh)){
						for(int j = maximum(low,allowedWindowLow); j <= minimum(high,allowedWindowHigh); j++ ){	
							tfbsandCellLineNames = currentWindow.getWindowBases().get(j-(jthWindow*WINDOW_SIZE));
							if (!(tfbsandCellLineNames.getNames().contains(tfbsNameandCellLineName))){										
								tfbsandCellLineNames.getNames().add(tfbsNameandCellLineName);
								currentWindow.setNumberofAllNonoverlappingBases(currentWindow.getNumberofAllNonoverlappingBases()+1);
							}											
						}									
					}//end of if: process overlap
					
					if (low>allowedWindowHigh){
						//move window forward
						jthWindow++;
					}else if(high<allowedWindowLow){
						//move window backward
						jthWindow--;
					}else if (low>=allowedWindowLow &&  high <= allowedWindowHigh) {
						//we have processed the line successfully
						//Now we can read a new input line
						unprocessedLines.remove(unprocessedLine);
						
					}else if (low<allowedWindowLow && high<=allowedWindowHigh){
						high= allowedWindowLow-1;
						unprocessedLines.remove(unprocessedLine);
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,tfbsNameandCellLineName));
						//move window backward
						jthWindow--;
					}
					else if(high>allowedWindowHigh && low >=allowedWindowLow){
						low = allowedWindowHigh +1;
						unprocessedLines.remove(unprocessedLine);
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,tfbsNameandCellLineName));
						//move window forward
						jthWindow++;
					}else if (low<allowedWindowLow && high>allowedWindowHigh){
						//there are two new degenerated lines
						//one is [low,allowedWindowLow-1]
						//second is [allowedWindowHigh+1, high]
						//both of them must be processed
						unprocessedLines.remove(unprocessedLine);					
						unprocessedLines.add(new UnprocessedLine(low,allowedWindowLow-1,Commons.DEGENERATED_LINE,tfbsNameandCellLineName));
						unprocessedLines.add(new UnprocessedLine(allowedWindowHigh+1, high,Commons.DEGENERATED_LINE,tfbsNameandCellLineName));
						//just give a direction
						//increment or decrement
						//does not matter
						//just avoid infinite loop
						//it will finds its window
						jthWindow--;
							
					}

				}//end of if there is unprocessed line
					
				if (unprocessedLines.size()==0){
						while((strLine = bufferedReader.readLine())!=null){
						
						//chr3	66870	66986	MAFKAB50322	HEPG2	spp.optimal.wgEncodeSydhTfbsHepg2Mafkab50322IggrabAlnRep0_VS_wgEncodeSydhTfbsHepg2InputIggrabAln.narrowPeak
						indexofFirstTab = strLine.indexOf('\t');
						indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
						indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
						
						low = Integer.valueOf(strLine.substring(indexofFirstTab+1, indexofSecondTab));
						high =Integer.valueOf(strLine.substring(indexofSecondTab+1, indexofThirdTab));
						tfbsName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						cellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
						
						tfbsNameandCellLineName = tfbsName + "_" + cellLineName;
						
						
						//process the overlap
						if(overlaps(low,high,allowedWindowLow,allowedWindowHigh)){
							for(int j = maximum(low,allowedWindowLow); j <= minimum(high,allowedWindowHigh); j++ ){	
								tfbsandCellLineNames = currentWindow.getWindowBases().get(j-(jthWindow*WINDOW_SIZE));
								if (!(tfbsandCellLineNames.getNames().contains(tfbsNameandCellLineName))){										
									tfbsandCellLineNames.getNames().add(tfbsNameandCellLineName);
									currentWindow.setNumberofAllNonoverlappingBases(currentWindow.getNumberofAllNonoverlappingBases()+1);
								}
							}									
						}//end of if: process overlap
							
						
						if (low>allowedWindowHigh){
							unprocessedLines.add(new UnprocessedLine(low,high,Commons.ORIGINAL_READ_LINE,tfbsNameandCellLineName));
							//move window forward
							jthWindow++;
							break;
						}else if(high<allowedWindowLow){
							unprocessedLines.add(new UnprocessedLine(low,high,Commons.ORIGINAL_READ_LINE,tfbsNameandCellLineName));						
							//move window backward
							jthWindow--;
							break;
						}else if (low>=allowedWindowLow &&  high <= allowedWindowHigh) {
							//Now we can read a new input line
						}else if (low<allowedWindowLow && high<=allowedWindowHigh){
							high= allowedWindowLow-1;
							unprocessedLines.add(new UnprocessedLine(low, high,Commons.DEGENERATED_LINE,tfbsNameandCellLineName));
							//move window backward
							jthWindow--;
							break;
						}
						else if(high>allowedWindowHigh && low >=allowedWindowLow){
							low = allowedWindowHigh +1;
							unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,tfbsNameandCellLineName));
							//move window forward
							jthWindow++;
							break;
						}else if (low<allowedWindowLow && high>allowedWindowHigh){
							//there are two new degenerated lines
							//one is [low,allowedWindowLow-1]
							//second is [allowedWindowHigh+1, high]
							//both of them must be processed
							unprocessedLines.add(new UnprocessedLine(low,allowedWindowLow-1,Commons.DEGENERATED_LINE,tfbsNameandCellLineName));
							unprocessedLines.add(new UnprocessedLine(allowedWindowHigh+1, high,Commons.DEGENERATED_LINE,tfbsNameandCellLineName));
							//just give a direction
							//increment or decrement
							//does not matter
							//just avoid infinite loop
							//it will finds its window
							jthWindow--;
							break;
							
						}
						
					
					}//end of while: read line one by one
				}// end of if : there is no unprocessed input line
				
				
				//there is no artificially created input line to be processed
				//so the unnecessary windows can be deleted.
				if(!unprocessedLinesContainsDegeneratedLines(unprocessedLines)){
						//first accumulate the dnaseCellLineNames stored in unnecessary Chromosome Windows
					//and then remove the unnecessary windows from the necessaryChromosomeWindowsMap
					
					//there must be one original read line or none
					if (unprocessedLines.size()==1 && unprocessedLines.get(0).getType().equals(Commons.ORIGINAL_READ_LINE)){
						low = unprocessedLines.get(0).getLow();
						accumulateNamesandRemoveUnnecessaryChromosomeWindows(low,necessaryChromosomeWindowsMap,tfbsNameandCellLineName2NumberofNonoverlappingBasePairsWholeGenomeMap);
						
					}
					
				}
				
				if(strLine == null){
					// we have consumed all the input data
					accumulateAllRemainingNamesandRemoveAllWindows(necessaryChromosomeWindowsMap,tfbsNameandCellLineName2NumberofNonoverlappingBasePairsWholeGenomeMap);
					break;
				}
				
			}//end of For: each jth window on the chromosome
			
			
			bufferedReader.close();
					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
	}

public void calculateHistoneNumberofNonoverlappingBasePairs(int chromosomeNumber, int chromosomeSize, Map<String,Long> histoneNameandCellLineName2NumberofNonOverlappingBasePairsMap,String inputFileName){
	
	FileReader fileReader;
	BufferedReader bufferedReader;
	String strLine = null;
	
	int indexofFirstTab = 0;
	int indexofSecondTab = 0;
	int indexofThirdTab = 0;
	int indexofFourthTab = 0;
	int indexofFifthTab = 0;
	
	int low = 0;
	int high = 0;
	String histoneName = null;
	String cellLineName;
	String histoneNameandCellLineName;
	
	System.out.printf("Histone chromosome%d" + System.getProperty("line.separator") , chromosomeNumber);
	
	List<UnprocessedLine> unprocessedLines = new ArrayList<UnprocessedLine>();
	UnprocessedLine unprocessedLine;
	
	try {
		fileReader = new FileReader(inputFileName);								
		bufferedReader = new BufferedReader(fileReader);
		
		
		Window currentWindow = null;
		Names histoneandCellLineNames = null;
		Map<Integer,Window> necessaryChromosomeWindowsMap = new HashMap<Integer,Window>();
		
		//In this map, First integer indicated the number of window (example jth window), second integer indicates whether it is already created or not.
		//1 means it is already created.
		//0 means it has not been created.
		Map<Integer,Integer> alreadyCreatedWindows = new HashMap<Integer,Integer>();
		
		int allowedWindowLow;
		int allowedWindowHigh;
		
		//window will move forward and backward on the chromosome 
		//it will consume all the data
		//then the results will be accumulated in dnaseCellLine2NumberofNonOverlappingBasePairsMap
		
		//chrom size and data in sorted chromosome based files are not compatible with each other
		//there are intervals in sorted chromosome based files exceeding the chrom size
		//therefore jthWindow<= numberofWindows is removed		
		for(int jthWindow = 0; ;){
			
//			GlanetRunner.appendLog("jth\t" + jthWindow + "\twindow");,
			
			
			allowedWindowLow = jthWindow * WINDOW_SIZE;
			allowedWindowHigh = allowedWindowLow + WINDOW_SIZE-1; 
			
			//get the current window
			//from the necessaryChromosomeWindowsMap if it is already created
			//or create a new one			
			if(necessaryChromosomeWindowsMap.get(jthWindow)==null && alreadyCreatedWindows.get(jthWindow)==null ){
				currentWindow = new Window(WINDOW_SIZE,allowedWindowLow,allowedWindowHigh,jthWindow);
				
				if (alreadyCreatedWindows.get(jthWindow)==null){
				 alreadyCreatedWindows.put(jthWindow, Commons.ONE);
				}
				
				for(int i =0; i<WINDOW_SIZE ; i++){
					histoneandCellLineNames = new Names();
					currentWindow.getWindowBases().add(histoneandCellLineNames);
				}
				
				//add it to the necessaryChromosomeWindowsMap
				necessaryChromosomeWindowsMap.put(jthWindow, currentWindow);
				
			}else{
				currentWindow = necessaryChromosomeWindowsMap.get(jthWindow);
				
			}
			
			if((unprocessedLines.size()>0)){
				
				unprocessedLine = unprocessedLines.get(0);
				low = unprocessedLine.getLow();
				high = unprocessedLine.getHigh();
				histoneNameandCellLineName = unprocessedLine.getName();
				
				//process the overlap
				if(overlaps(low,high,allowedWindowLow,allowedWindowHigh)){
					for(int j = maximum(low,allowedWindowLow); j <= minimum(high,allowedWindowHigh); j++ ){	
						histoneandCellLineNames = currentWindow.getWindowBases().get(j-(jthWindow*WINDOW_SIZE));
						if (!(histoneandCellLineNames.getNames().contains(histoneNameandCellLineName))){										
							histoneandCellLineNames.getNames().add(histoneNameandCellLineName);
							currentWindow.setNumberofAllNonoverlappingBases(currentWindow.getNumberofAllNonoverlappingBases()+1);
						}											
					}									
				}//end of if: process overlap
				
				if (low>allowedWindowHigh){
					//move window forward
					jthWindow++;
				}else if(high<allowedWindowLow){
					//move window backward
					jthWindow--;
				}else if (low>=allowedWindowLow &&  high <= allowedWindowHigh) {
					//we have processed the line successfully
					//Now we can read a new input line
					unprocessedLines.remove(unprocessedLine);
					
				}else if (low<allowedWindowLow && high<=allowedWindowHigh){
					high= allowedWindowLow-1;
					unprocessedLines.remove(unprocessedLine);
					unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,histoneNameandCellLineName));
					//move window backward
					jthWindow--;
				}
				else if(high>allowedWindowHigh && low >=allowedWindowLow){
					low = allowedWindowHigh +1;
					unprocessedLines.remove(unprocessedLine);
					unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,histoneNameandCellLineName));
					//move window forward
					jthWindow++;
				}else if (low<allowedWindowLow && high>allowedWindowHigh){
					//there are two new degenerated lines
					//one is [low,allowedWindowLow-1]
					//second is [allowedWindowHigh+1, high]
					//both of them must be processed
					unprocessedLines.remove(unprocessedLine);					
					unprocessedLines.add(new UnprocessedLine(low,allowedWindowLow-1,Commons.DEGENERATED_LINE,histoneNameandCellLineName));
					unprocessedLines.add(new UnprocessedLine(allowedWindowHigh+1, high,Commons.DEGENERATED_LINE,histoneNameandCellLineName));
					//just give a direction
					//increment or decrement
					//does not matter
					//just avoid infinite loop
					//it will finds its window
					jthWindow--;
						
				}

			}//end of if there is unprocessed line
				
			if (unprocessedLines.size()==0){
					while((strLine = bufferedReader.readLine())!=null){
					
					//chr4	27166	30083	H3K9ME3	HSMM	wgEncodeBroadHistoneHsmmH3k9me3StdAln.narrowPeak
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
					
					low = Integer.valueOf(strLine.substring(indexofFirstTab+1, indexofSecondTab));
					high =Integer.valueOf(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					histoneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
					cellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
					
					histoneNameandCellLineName = histoneName + "_" + cellLineName;
					
					
					//process the overlap
					if(overlaps(low,high,allowedWindowLow,allowedWindowHigh)){
						for(int j = maximum(low,allowedWindowLow); j <= minimum(high,allowedWindowHigh); j++ ){	
							histoneandCellLineNames = currentWindow.getWindowBases().get(j-(jthWindow*WINDOW_SIZE));
							if (!(histoneandCellLineNames.getNames().contains(histoneNameandCellLineName))){										
								histoneandCellLineNames.getNames().add(histoneNameandCellLineName);
								currentWindow.setNumberofAllNonoverlappingBases(currentWindow.getNumberofAllNonoverlappingBases()+1);
							}
						}									
					}//end of if: process overlap
						
					
					if (low>allowedWindowHigh){
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.ORIGINAL_READ_LINE,histoneNameandCellLineName));
						//move window forward
						jthWindow++;
						break;
					}else if(high<allowedWindowLow){
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.ORIGINAL_READ_LINE,histoneNameandCellLineName));						
						//move window backward
						jthWindow--;
						break;
					}else if (low>=allowedWindowLow &&  high <= allowedWindowHigh) {
						//Now we can read a new input line
					}else if (low<allowedWindowLow && high<=allowedWindowHigh){
						high= allowedWindowLow-1;
						unprocessedLines.add(new UnprocessedLine(low, high,Commons.DEGENERATED_LINE,histoneNameandCellLineName));
						//move window backward
						jthWindow--;
						break;
					}
					else if(high>allowedWindowHigh && low >=allowedWindowLow){
						low = allowedWindowHigh +1;
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,histoneNameandCellLineName));
						//move window forward
						jthWindow++;
						break;
					}else if (low<allowedWindowLow && high>allowedWindowHigh){
						//there are two new degenerated lines
						//one is [low,allowedWindowLow-1]
						//second is [allowedWindowHigh+1, high]
						//both of them must be processed
						unprocessedLines.add(new UnprocessedLine(low,allowedWindowLow-1,Commons.DEGENERATED_LINE,histoneNameandCellLineName));
						unprocessedLines.add(new UnprocessedLine(allowedWindowHigh+1, high,Commons.DEGENERATED_LINE,histoneNameandCellLineName));
						//just give a direction
						//increment or decrement
						//does not matter
						//just avoid infinite loop
						//it will finds its window
						jthWindow--;
						break;
					}
					
				
				}//end of while: read line one by one
			}// end of if : there is no unprocessed input line
			
			
			//there is no artificially created input line to be processed
			//so the unnecessary windows can be deleted.
			if(!unprocessedLinesContainsDegeneratedLines(unprocessedLines)){
				//first accumulate the histone names stored in unnecessary Chromosome Windows
				//and then remove the unnecessary windows from the necessaryChromosomeWindowsMap
				
				//there must be one original read line or none
				if (unprocessedLines.size()==1 && unprocessedLines.get(0).getType().equals(Commons.ORIGINAL_READ_LINE)){
					low = unprocessedLines.get(0).getLow();
					accumulateNamesandRemoveUnnecessaryChromosomeWindows(low,necessaryChromosomeWindowsMap,histoneNameandCellLineName2NumberofNonOverlappingBasePairsMap);
					
				}
				
			}
			
			if(strLine == null){
				// we have consumed all the input data
				accumulateAllRemainingNamesandRemoveAllWindows(necessaryChromosomeWindowsMap,histoneNameandCellLineName2NumberofNonOverlappingBasePairsMap);
				break;
			}
			
		}//end of For: each jth window on the chromosome
		

		
		bufferedReader.close();
				
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (NumberFormatException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

	
}



public void calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(int chromosomeNumber, int chromosomeSize, Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap,Map<String,Long> exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap,String inputFileName){
	
	FileReader fileReader;
	BufferedReader bufferedReader;
	String strLine = null;
	
	int indexofFirstTab = 0;
	int indexofSecondTab = 0;
	int indexofThirdTab = 0;
	int indexofFourthTab = 0;
	int indexofFifthTab = 0;
	int indexofSixthTab = 0;
	
	int low = 0;
	int high = 0;
	String ncbiGeneId = null;
	String keggPathwayName = null;
	String intervalName = null;
		
	System.out.printf("Exon Based Kegg Pathway chromosome%d" + System.getProperty("line.separator") , chromosomeNumber);
	
	List<UnprocessedLine> unprocessedLines = new ArrayList<UnprocessedLine>();
	UnprocessedLine unprocessedLine;
	
	try {
		fileReader = new FileReader(inputFileName);								
		bufferedReader = new BufferedReader(fileReader);
		
		Window currentWindow = null;
		Names keggPathwayNames = null;
		Map<Integer,Window> necessaryChromosomeWindowsMap = new HashMap<Integer,Window>();
		
		//In this map, First integer indicated the number of window (example jth window), second integer indicates whether it is already created or not.
		//1 means it is already created.
		//0 means it has not been created.
		Map<Integer,Integer> alreadyCreatedWindows = new HashMap<Integer,Integer>();
		
		int allowedWindowLow;
		int allowedWindowHigh;
		
		//window will move forward and backward on the chromosome 
		//it will consume all the data
		//then the results will be accumulated in dnaseCellLine2NumberofNonOverlappingBasePairsMap
		
		//chrom size and data in sorted chromosome based files are not compatible with each other
		//there are intervals in sorted chromosome based files exceeding the chrom size
		//therefore jthWindow<= numberofWindows is removed
		for(int jthWindow = 0; ;){
			
//			GlanetRunner.appendLog("jth\t" + jthWindow + "\twindow");,
			
			allowedWindowLow = jthWindow * WINDOW_SIZE;
			allowedWindowHigh = allowedWindowLow + WINDOW_SIZE-1; 
			
			//get the current window
			//from the necessaryChromosomeWindowsMap if it is already created
			//or create a new one
			
			if(necessaryChromosomeWindowsMap.get(jthWindow)==null && alreadyCreatedWindows.get(jthWindow)==null ){
				currentWindow = new Window(WINDOW_SIZE,allowedWindowLow,allowedWindowHigh,jthWindow);
				
				
				if (alreadyCreatedWindows.get(jthWindow)==null){
				 alreadyCreatedWindows.put(jthWindow, Commons.ONE);
				}
				
				for(int i =0; i<WINDOW_SIZE ; i++){
					keggPathwayNames = new Names();
					currentWindow.getWindowBases().add(keggPathwayNames);
				}
				
				//add it to the necessaryChromosomeWindowsMap
				necessaryChromosomeWindowsMap.put(jthWindow, currentWindow);
			
				
			}else{
				currentWindow = necessaryChromosomeWindowsMap.get(jthWindow);
				
			}
			
			if((unprocessedLines.size()>0)){
					
				unprocessedLine = unprocessedLines.get(0);
				low = unprocessedLine.getLow();
				high = unprocessedLine.getHigh();
				ncbiGeneId = unprocessedLine.getName();
				
			
				//process the overlap
				if(overlaps(low,high,allowedWindowLow,allowedWindowHigh)){
					
					if (intervalName.startsWith(Commons.EXON)){
						//Get the list of kegg pathway names contain this ncbi gene id
						List<String> keggPathwayNameListContainingThisNcbiGeneId = ncbiGeneId2KeggPathwayHashMap.get(ncbiGeneId);
						
						
						if (keggPathwayNameListContainingThisNcbiGeneId!=null){
							for(int i=0; i<keggPathwayNameListContainingThisNcbiGeneId.size() ; i++){
								
								keggPathwayName = keggPathwayNameListContainingThisNcbiGeneId.get(i);
								
								for(int j = maximum(low,allowedWindowLow); j <= minimum(high,allowedWindowHigh); j++ ){	
									keggPathwayNames = currentWindow.getWindowBases().get(j-(jthWindow*WINDOW_SIZE));
									if (!(keggPathwayNames.getNames().contains(keggPathwayName))){										
										keggPathwayNames.getNames().add(keggPathwayName);
										currentWindow.setNumberofAllNonoverlappingBases(currentWindow.getNumberofAllNonoverlappingBases()+1);
									}											
								}
							}//end of for: all kegg pathways containing this gene id
						}//if there are kegg pathways containing this gene id 
					}//end of if: it is an EXON
														
				}//end of if: process overlap
				
				if (low>allowedWindowHigh){
					//move window forward
					jthWindow++;
				}else if(high<allowedWindowLow){
					//move window backward
					jthWindow--;
				}else if (low>=allowedWindowLow &&  high <= allowedWindowHigh) {
					//we have processed the line successfully
					//Now we can read a new input line
					unprocessedLines.remove(unprocessedLine);
					
				}else if (low<allowedWindowLow && high<=allowedWindowHigh){
					high= allowedWindowLow-1;
					unprocessedLines.remove(unprocessedLine);
					unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,ncbiGeneId));
					//move window backward
					jthWindow--;
				}
				else if(high>allowedWindowHigh && low >=allowedWindowLow){
					low = allowedWindowHigh +1;
					unprocessedLines.remove(unprocessedLine);
					unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,ncbiGeneId));
					//move window forward
					jthWindow++;
				}else if (low<allowedWindowLow && high>allowedWindowHigh){
					//there are two new degenerated lines
					//one is [low,allowedWindowLow-1]
					//second is [allowedWindowHigh+1, high]
					//both of them must be processed
					unprocessedLines.remove(unprocessedLine);					
					unprocessedLines.add(new UnprocessedLine(low,allowedWindowLow-1,Commons.DEGENERATED_LINE,ncbiGeneId));
					unprocessedLines.add(new UnprocessedLine(allowedWindowHigh+1, high,Commons.DEGENERATED_LINE,ncbiGeneId));
					//just give a direction
					//increment or decrement
					//does not matter
					//just avoid infinite loop
					//it will finds its window
					jthWindow--;
						
				}
						
			}//end of if there is unprocessed line
				
			if (unprocessedLines.size()==0){
					while((strLine = bufferedReader.readLine())!=null){
					
					//chr9	4510	12509	NM_182905	100287171	3P2	-	WASH1

					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
					indexofSixthTab = strLine.indexOf('\t',indexofFifthTab+1);
					
					low = Integer.valueOf(strLine.substring(indexofFirstTab+1, indexofSecondTab));
					high =Integer.valueOf(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					ncbiGeneId = strLine.substring(indexofFourthTab+1, indexofFifthTab);
					intervalName = strLine.substring(indexofFifthTab+1, indexofSixthTab);
				
					
					//process the overlap
					if(overlaps(low,high,allowedWindowLow,allowedWindowHigh)){
						
						if (intervalName.startsWith(Commons.EXON)){
							//Get the list of kegg pathway names contain this ncbi gene id
							List<String> keggPathwayNameListContainingThisNcbiGeneId = ncbiGeneId2KeggPathwayHashMap.get(ncbiGeneId);
							
							
							if (keggPathwayNameListContainingThisNcbiGeneId!=null){
								for(int i=0; i<keggPathwayNameListContainingThisNcbiGeneId.size() ; i++){
									
									keggPathwayName = keggPathwayNameListContainingThisNcbiGeneId.get(i);
									
									for(int j = maximum(low,allowedWindowLow); j <= minimum(high,allowedWindowHigh); j++ ){	
										keggPathwayNames = currentWindow.getWindowBases().get(j-(jthWindow*WINDOW_SIZE));
										if (!(keggPathwayNames.getNames().contains(keggPathwayName))){										
											keggPathwayNames.getNames().add(keggPathwayName);
											currentWindow.setNumberofAllNonoverlappingBases(currentWindow.getNumberofAllNonoverlappingBases()+1);
										}
									}
									
											
								}//end of for: all kegg pathways containing this gene id
							}//if there are kegg pathways containing this gene id 
						}//end of if: it is an EXON
						
																
					}//end of if: process overlap
						
					
					if (low>allowedWindowHigh){
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.ORIGINAL_READ_LINE,ncbiGeneId));
						//move window forward
						jthWindow++;
						break;
					}else if(high<allowedWindowLow){
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.ORIGINAL_READ_LINE,ncbiGeneId));						
						//move window backward
						jthWindow--;
						break;
					}else if (low>=allowedWindowLow &&  high <= allowedWindowHigh) {
						//Now we can read a new input line
					}else if (low<allowedWindowLow && high<=allowedWindowHigh){
						high= allowedWindowLow-1;
						unprocessedLines.add(new UnprocessedLine(low, high,Commons.DEGENERATED_LINE,ncbiGeneId));
						//move window backward
						jthWindow--;
						break;
					}
					else if(high>allowedWindowHigh && low >=allowedWindowLow){
						low = allowedWindowHigh +1;
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,ncbiGeneId));
						//move window forward
						jthWindow++;
						break;
					}else if (low<allowedWindowLow && high>allowedWindowHigh){
						//there are two new degenerated lines
						//one is [low,allowedWindowLow-1]
						//second is [allowedWindowHigh+1, high]
						//both of them must be processed
						unprocessedLines.add(new UnprocessedLine(low,allowedWindowLow-1,Commons.DEGENERATED_LINE,ncbiGeneId));
						unprocessedLines.add(new UnprocessedLine(allowedWindowHigh+1, high,Commons.DEGENERATED_LINE,ncbiGeneId));
						//just give a direction
						//increment or decrement
						//does not matter
						//just avoid infinite loop
						//it will finds its window
						jthWindow--;
						break;
						
					}

					
				}//end of while: read line one by one
			}// end of if : there is no unprocessed input line
			
			
			//there is no artificially created input line to be processed
			//so the unnecessary windows can be deleted.
			if(!unprocessedLinesContainsDegeneratedLines(unprocessedLines)){
				//first accumulate the dnaseCellLineNames stored in unnecessary Chromosome Windows
				//and then remove the unnecessary windows from the necessaryChromosomeWindowsMap
				
				//there must be one original read line or none
				if (unprocessedLines.size()==1 && unprocessedLines.get(0).getType().equals(Commons.ORIGINAL_READ_LINE)){
					low = unprocessedLines.get(0).getLow();
					accumulateNamesandRemoveUnnecessaryChromosomeWindows(low,necessaryChromosomeWindowsMap,exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap);
					
				}
			}
			
			if(strLine == null){
				// we have consumed all the input data
				accumulateAllRemainingNamesandRemoveAllWindows(necessaryChromosomeWindowsMap,exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap);
				
				break;
			}
			
		}//end of For: each jth window on the chromosome
		
		
		bufferedReader.close();
				
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (NumberFormatException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

	
}


	public boolean unprocessedLinesContainsDegeneratedLines(List<UnprocessedLine> unprocessedLines){
		UnprocessedLine line ;
		
		for(int i =0; i<unprocessedLines.size(); i++){
			line = unprocessedLines.get(i);
			if (line.getType().equals(Commons.DEGENERATED_LINE)){
				return true;
			}
		}
		
		return false;
		
	}
	
public void calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(int chromosomeNumber, int chromosomeSize, Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap,Map<String,Long> regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap,String inputFileName){
	
	FileReader fileReader;
	BufferedReader bufferedReader;
	String strLine = null;
	
	int indexofFirstTab = 0;
	int indexofSecondTab = 0;
	int indexofThirdTab = 0;
	int indexofFourthTab = 0;
	int indexofFifthTab = 0;
	int indexofSixthTab = 0;
	
	int low = 0;
	int high = 0;
	String ncbiGeneId = null;
	String keggPathwayName = null;
	String intervalName = null;
	
	System.out.printf("Regulation Based Kegg Pathway chromosome%d" + System.getProperty("line.separator") , chromosomeNumber);

	List<UnprocessedLine> unprocessedLines = new ArrayList<UnprocessedLine>();
	UnprocessedLine unprocessedLine;
	
	try {
		fileReader = new FileReader(inputFileName);								
		bufferedReader = new BufferedReader(fileReader);
		
		Window currentWindow = null;
		Names keggPathwayNames = null;
		Map<Integer,Window> necessaryChromosomeWindowsMap = new HashMap<Integer,Window>();
		
		//In this map, First integer indicated the number of window (example jth window), second integer indicates whether it is already created or not.
		//1 means it is already created.
		//0 means it has not been created.
		Map<Integer,Integer> alreadyCreatedWindows = new HashMap<Integer,Integer>();
		
		int allowedWindowLow;
		int allowedWindowHigh;
		
		//window will move forward and backward on the chromosome 
		//it will consume all the data
		//then the results will be accumulated in regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap
		
		//chrom size and data in sorted chromosome based files may not be compatible with each other
		//there are intervals in sorted chromosome based files may exceed the given chrom size
		//therefore jthWindow<= numberofWindows condition is removed
		for(int jthWindow = 0; ;){
//			GlanetRunner.appendLog("jth\t" + jthWindow + "\twindow");,
			
			allowedWindowLow = jthWindow * WINDOW_SIZE;
			allowedWindowHigh = allowedWindowLow + WINDOW_SIZE-1; 
			
			//get the current window
			//from the necessaryChromosomeWindowsMap if it is already created
			//or create a new one
			
			if(necessaryChromosomeWindowsMap.get(jthWindow)==null && alreadyCreatedWindows.get(jthWindow)==null ){
				currentWindow = new Window(WINDOW_SIZE,allowedWindowLow,allowedWindowHigh,jthWindow);
								
				if (alreadyCreatedWindows.get(jthWindow)==null){
				 alreadyCreatedWindows.put(jthWindow, Commons.ONE);
				}				
				
				for(int i =0; i<WINDOW_SIZE ; i++){
					keggPathwayNames = new Names();
					currentWindow.getWindowBases().add(keggPathwayNames);
				}
				
				//add it to the necessaryChromosomeWindowsMap
				necessaryChromosomeWindowsMap.put(jthWindow, currentWindow);
			
				
			}else{
				currentWindow = necessaryChromosomeWindowsMap.get(jthWindow);
			}
			
						
			if((unprocessedLines.size()>0)){
				
				unprocessedLine = unprocessedLines.get(0);
				low = unprocessedLine.getLow();
				high = unprocessedLine.getHigh();
				ncbiGeneId = unprocessedLine.getName();
				
				
				//process the overlap
				if(overlaps(low,high,allowedWindowLow,allowedWindowHigh)){
					
					if (intervalName.startsWith(Commons.INTRON) ||
							intervalName.startsWith(Commons.FIVE_P_ONE) ||
							intervalName.startsWith(Commons.FIVE_P_TWO)||
							intervalName.startsWith(Commons.THREE_P_ONE) ||
							intervalName.startsWith(Commons.THREE_P_TWO)){
						//Get the list of kegg pathway names contain this ncbi gene id
						List<String> keggPathwayNameListContainingThisNcbiGeneId = ncbiGeneId2KeggPathwayHashMap.get(ncbiGeneId);
						
						
						if (keggPathwayNameListContainingThisNcbiGeneId!=null){
							for(int i=0; i<keggPathwayNameListContainingThisNcbiGeneId.size() ; i++){
								
								keggPathwayName = keggPathwayNameListContainingThisNcbiGeneId.get(i);
								
								for(int j = maximum(low,allowedWindowLow); j <= minimum(high,allowedWindowHigh); j++ ){	
									keggPathwayNames = currentWindow.getWindowBases().get(j-(jthWindow*WINDOW_SIZE));

									if (!(keggPathwayNames.getNames().contains(keggPathwayName))){										
										keggPathwayNames.getNames().add(keggPathwayName);
										currentWindow.setNumberofAllNonoverlappingBases(currentWindow.getNumberofAllNonoverlappingBases()+1);
									}											
								}
							}//end of for: all kegg pathways containing this gene id
						}//if there are kegg pathways containing this gene id 
					}//end of if: it is an EXON
														
				}//end of if: process overlap
				
				if (low>allowedWindowHigh){
					//move window forward
					jthWindow++;
				}else if(high<allowedWindowLow){
					//move window backward
					jthWindow--;
				}else if (low>=allowedWindowLow &&  high <= allowedWindowHigh) {
					//we have processed the line successfully
					//Now we can read a new input line
					unprocessedLines.remove(unprocessedLine);
					
				}else if (low<allowedWindowLow && high<=allowedWindowHigh){
					high= allowedWindowLow-1;
					unprocessedLines.remove(unprocessedLine);
					unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,ncbiGeneId));
					//move window backward
					jthWindow--;
				}
				else if(high>allowedWindowHigh && low >=allowedWindowLow){
					low = allowedWindowHigh +1;
					unprocessedLines.remove(unprocessedLine);
					unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,ncbiGeneId));
					//move window forward
					jthWindow++;
				}else if (low<allowedWindowLow && high>allowedWindowHigh){
					//there are two new degenerated lines
					//one is [low,allowedWindowLow-1]
					//second is [allowedWindowHigh+1, high]
					//both of them must be processed
					unprocessedLines.remove(unprocessedLine);					
					unprocessedLines.add(new UnprocessedLine(low,allowedWindowLow-1,Commons.DEGENERATED_LINE,ncbiGeneId));
					unprocessedLines.add(new UnprocessedLine(allowedWindowHigh+1, high,Commons.DEGENERATED_LINE,ncbiGeneId));
					//just give a direction
					//increment or decrement
					//does not matter
					//just avoid infinite loop
					//it will finds its window
					jthWindow--;
						
				}
				
				
						
			}//end of if there is unprocessed line
				
			if (unprocessedLines.size()==0){
				while((strLine = bufferedReader.readLine())!=null){
					
					//chr9	4510	12509	NM_182905	100287171	3P2	-	WASH1

					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
					indexofSixthTab = strLine.indexOf('\t',indexofFifthTab+1);
					
					low = Integer.valueOf(strLine.substring(indexofFirstTab+1, indexofSecondTab));
					high =Integer.valueOf(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					ncbiGeneId = strLine.substring(indexofFourthTab+1, indexofFifthTab);
					intervalName = strLine.substring(indexofFifthTab+1, indexofSixthTab);
				
					
					//process the overlap
					if(overlaps(low,high,allowedWindowLow,allowedWindowHigh)){
						
						if (intervalName.startsWith(Commons.INTRON) ||
								intervalName.startsWith(Commons.FIVE_P_ONE) ||
								intervalName.startsWith(Commons.FIVE_P_TWO)||
								intervalName.startsWith(Commons.THREE_P_ONE) ||
								intervalName.startsWith(Commons.THREE_P_TWO)){
							//Get the list of kegg pathway names contain this ncbi gene id
							List<String> keggPathwayNameListContainingThisNcbiGeneId = ncbiGeneId2KeggPathwayHashMap.get(ncbiGeneId);
							
							
							if (keggPathwayNameListContainingThisNcbiGeneId!=null){
								for(int i=0; i<keggPathwayNameListContainingThisNcbiGeneId.size() ; i++){
									
									keggPathwayName = keggPathwayNameListContainingThisNcbiGeneId.get(i);
													
									
									for(int j = maximum(low,allowedWindowLow); j <= minimum(high,allowedWindowHigh); j++ ){	
										keggPathwayNames = currentWindow.getWindowBases().get(j-(jthWindow*WINDOW_SIZE));
										if (!(keggPathwayNames.getNames().contains(keggPathwayName))){										
											keggPathwayNames.getNames().add(keggPathwayName);
											currentWindow.setNumberofAllNonoverlappingBases(currentWindow.getNumberofAllNonoverlappingBases()+1);
										}
									}
								}//end of for: all kegg pathways containing this gene id
							}//if there are kegg pathways containing this gene id 
						}//end of if: it is an EXON
						
																
					}//end of if: process overlap
						
					
					if (low>allowedWindowHigh){
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.ORIGINAL_READ_LINE,ncbiGeneId));
						//move window forward
						jthWindow++;
						break;
					}else if(high<allowedWindowLow){
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.ORIGINAL_READ_LINE,ncbiGeneId));						
						//move window backward
						jthWindow--;
						break;
					}else if (low>=allowedWindowLow &&  high <= allowedWindowHigh) {
						//Now we can read a new input line
					}else if (low<allowedWindowLow && high<=allowedWindowHigh){
						high= allowedWindowLow-1;
						unprocessedLines.add(new UnprocessedLine(low, high,Commons.DEGENERATED_LINE,ncbiGeneId));
						//move window backward
						jthWindow--;
						break;
					}
					else if(high>allowedWindowHigh && low >=allowedWindowLow){
						low = allowedWindowHigh +1;
						unprocessedLines.add(new UnprocessedLine(low,high,Commons.DEGENERATED_LINE,ncbiGeneId));
						//move window forward
						jthWindow++;
						break;
					}else if (low<allowedWindowLow && high>allowedWindowHigh){
						//there are two new degenerated lines
						//one is [low,allowedWindowLow-1]
						//second is [allowedWindowHigh+1, high]
						//both of them must be processed
						unprocessedLines.add(new UnprocessedLine(low,allowedWindowLow-1,Commons.DEGENERATED_LINE,ncbiGeneId));
						unprocessedLines.add(new UnprocessedLine(allowedWindowHigh+1, high,Commons.DEGENERATED_LINE,ncbiGeneId));
						//just give a direction
						//increment or decrement
						//does not matter
						//just avoid infinite loop
						//it will finds its window
						jthWindow--;
						break;
						
					}
					
	
					
				}//end of while: read line one by one
			}// end of if : there is no unprocessed input line
			
			
			//there is no artificially created input line to be processed
			//so the unnecessary windows can be deleted.
			if(!unprocessedLinesContainsDegeneratedLines(unprocessedLines)){
				//first accumulate the names stored in unnecessary Chromosome Windows
				//and then remove the unnecessary windows from the necessaryChromosomeWindowsMap
				
				//there must be one original read line or none
				if (unprocessedLines.size()==1 && unprocessedLines.get(0).getType().equals(Commons.ORIGINAL_READ_LINE)){
					low = unprocessedLines.get(0).getLow();
					accumulateNamesandRemoveUnnecessaryChromosomeWindows(low,necessaryChromosomeWindowsMap,regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap);
					
				}			
			}
			
			if(strLine == null){
				// we have consumed all the input data
				accumulateAllRemainingNamesandRemoveAllWindows(necessaryChromosomeWindowsMap,regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap);
								
				break;
			}
			
		}//end of For: each jth window on the chromosome
		
		
		bufferedReader.close();
				
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (NumberFormatException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

	
}
	
	public void calculateNumberofNonoverlappingBasePairsforDnase(List<Integer> chromosomeSizes,Map<String,Long> dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap){				
		
		for(int chromosomeNumber= 1; chromosomeNumber<=Commons.NUMBER_OF_CHROMOSOMES_HG19; chromosomeNumber++){
			
			switch(chromosomeNumber){
				case 1: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR1_DNASE_FILENAME);
						break;
				case 2: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR2_DNASE_FILENAME);
						break;
				case 3: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR3_DNASE_FILENAME);
						break;
				case 4: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR4_DNASE_FILENAME);
						break;
				case 5: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR5_DNASE_FILENAME);
						break;
				case 6: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR6_DNASE_FILENAME);
						break;
				case 7: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR7_DNASE_FILENAME);
						break;
				case 8: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR8_DNASE_FILENAME);
						break;
				case 9: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR9_DNASE_FILENAME);
						break;
				case 10: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR10_DNASE_FILENAME);
						break;
				case 11: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR11_DNASE_FILENAME);
						break;
				case 12: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR12_DNASE_FILENAME);
						break;
				case 13: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR13_DNASE_FILENAME);
						break;
				case 14: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR14_DNASE_FILENAME);
						break;
				case 15: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR15_DNASE_FILENAME);
						break;
				case 16: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR16_DNASE_FILENAME);
						break;
				case 17: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR17_DNASE_FILENAME);
						break;
				case 18: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR18_DNASE_FILENAME);
						break;
				case 19: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR19_DNASE_FILENAME);
						break;
				case 20: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR20_DNASE_FILENAME);
						break;
				case 21: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR21_DNASE_FILENAME);
						break;
				case 22: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHR22_DNASE_FILENAME);
						break;
				case 23: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHRX_DNASE_FILENAME);
						break;			
				case 24: calculateDnaseNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.SORTED_CHRY_DNASE_FILENAME);
						break;
								
			}//End of switch

		} // End of For each chromosome in the whole genome
		
	}
	
	
	public void calculateNumberofNonoverlappingBasePairsforTfbs(List<Integer> chromosomeSizes,Map<String,Long> tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap){				
		
		
		for(int chromosomeNumber= 1; chromosomeNumber<=Commons.NUMBER_OF_CHROMOSOMES_HG19; chromosomeNumber++){
			
			switch(chromosomeNumber){
				case 1: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR1_TFBS_FILENAME);
						break;
				case 2: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR2_TFBS_FILENAME);
						break;
				case 3: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR3_TFBS_FILENAME);
						break;
				case 4: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR4_TFBS_FILENAME);
						break;
				case 5: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR5_TFBS_FILENAME);
						break;
				case 6: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR6_TFBS_FILENAME);
						break;
				case 7: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR7_TFBS_FILENAME);
						break;
				case 8: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR8_TFBS_FILENAME);
						break;
				case 9: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR9_TFBS_FILENAME);
						break;
				case 10: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR10_TFBS_FILENAME);
						break;
				case 11: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR11_TFBS_FILENAME);
						break;
				case 12: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR12_TFBS_FILENAME);
						break;
				case 13: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR13_TFBS_FILENAME);
						break;
				case 14: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR14_TFBS_FILENAME);
						break;
				case 15: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR15_TFBS_FILENAME);
						break;
				case 16: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR16_TFBS_FILENAME);
						break;
				case 17: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR17_TFBS_FILENAME);
						break;
				case 18: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR18_TFBS_FILENAME);
						break;
				case 19: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR19_TFBS_FILENAME);
						break;
				case 20: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR20_TFBS_FILENAME);
						break;
				case 21: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR21_TFBS_FILENAME);
						break;
				case 22: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHR22_TFBS_FILENAME);
						break;
				case 23: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHRX_TFBS_FILENAME);
						break;			
				case 24: calculateTfbsNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), tfbsName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.SORTED_CHRY_TFBS_FILENAME);
						break;
								
			}//End of switch

		} // End of For each chromosome in the whole genome
		
	}


	public void calculateNumberofNonoverlappingBasePairsforHistone(List<Integer> chromosomeSizes,Map<String,Long> histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap){				
	
		for(int chromosomeNumber= 1; chromosomeNumber<=Commons.NUMBER_OF_CHROMOSOMES_HG19; chromosomeNumber++){
			
			switch(chromosomeNumber){
				case 1: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR1_HISTONE_FILENAME);
						break;
				case 2: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR2_HISTONE_FILENAME);
						break;
				case 3: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR3_HISTONE_FILENAME);
						break;
				case 4: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR4_HISTONE_FILENAME);
						break;
				case 5: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR5_HISTONE_FILENAME);
						break;
				case 6: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR6_HISTONE_FILENAME);
						break;
				case 7: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR7_HISTONE_FILENAME);
						break;
				case 8: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR8_HISTONE_FILENAME);
						break;
				case 9: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR9_HISTONE_FILENAME);
						break;
				case 10: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR10_HISTONE_FILENAME);
						break;
				case 11: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR11_HISTONE_FILENAME);
						break;
				case 12: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR12_HISTONE_FILENAME);
						break;
				case 13: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR13_HISTONE_FILENAME);
						break;
				case 14: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR14_HISTONE_FILENAME);
						break;
				case 15: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR15_HISTONE_FILENAME);
						break;
				case 16: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR16_HISTONE_FILENAME);
						break;
				case 17: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR17_HISTONE_FILENAME);
						break;
				case 18: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR18_HISTONE_FILENAME);
						break;
				case 19: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR19_HISTONE_FILENAME);
						break;
				case 20: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR20_HISTONE_FILENAME);
						break;
				case 21: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR21_HISTONE_FILENAME);
						break;
				case 22: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHR22_HISTONE_FILENAME);
						break;
				case 23: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHRX_HISTONE_FILENAME);
						break;			
				case 24: calculateHistoneNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), histoneName2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.SORTED_CHRY_HISTONE_FILENAME);
						break;
								
			}//End of switch

		} // End of For each chromosome in the whole genome
		

	}
	
	
	public void calculateNumberofNonoverlappingBasePairsforExonBasedKeggPathway(List<Integer> chromosomeSizes, Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap,Map<String,Long> exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap){				
		
		
		for(int chromosomeNumber= 1; chromosomeNumber<=Commons.NUMBER_OF_CHROMOSOMES_HG19; chromosomeNumber++){
			
			switch(chromosomeNumber){
				case 1: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR1_REFSEQ_GENES);
						break;
				case 2: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR2_REFSEQ_GENES);
						break;
				case 3: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR3_REFSEQ_GENES);
						break;
				case 4: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR4_REFSEQ_GENES);
						break;
				case 5: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR5_REFSEQ_GENES);
						break;
				case 6: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR6_REFSEQ_GENES);
						break;
				case 7: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR7_REFSEQ_GENES);
						break;
				case 8: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR8_REFSEQ_GENES);
						break;
				case 9: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR9_REFSEQ_GENES);
						break;
				case 10: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR10_REFSEQ_GENES);
						break;
				case 11: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR11_REFSEQ_GENES);
						break;
				case 12: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR12_REFSEQ_GENES);
						break;
				case 13: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR13_REFSEQ_GENES);
						break;
				case 14: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR14_REFSEQ_GENES);
						break;
				case 15: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR15_REFSEQ_GENES);
						break;
				case 16: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR16_REFSEQ_GENES);
						break;
				case 17: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR17_REFSEQ_GENES);
						break;
				case 18: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR18_REFSEQ_GENES);
						break;
				case 19: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR19_REFSEQ_GENES);
						break;
				case 20: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR20_REFSEQ_GENES);
						break;
				case 21: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR21_REFSEQ_GENES);
						break;
				case 22: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR22_REFSEQ_GENES);
						break;
				case 23: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHRX_REFSEQ_GENES);
						break;			
				case 24: calculateExonBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHRY_REFSEQ_GENES);
						break;
								
			}//End of switch

		} // End of For each chromosome in the whole genome
		

	}


	

	public void calculateNumberofNonoverlappingBasePairsforRegulationBasedKeggPathway(List<Integer> chromosomeSizes, Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap,Map<String,Long> regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap){				
		
		for(int chromosomeNumber= 1; chromosomeNumber<=Commons.NUMBER_OF_CHROMOSOMES_HG19; chromosomeNumber++){
			
			switch(chromosomeNumber){
				case 1: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR1_REFSEQ_GENES);
						break;
				case 2: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR2_REFSEQ_GENES);
						break;
				case 3: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR3_REFSEQ_GENES);
						break;
				case 4: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR4_REFSEQ_GENES);
						break;
				case 5: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR5_REFSEQ_GENES);
						break;
				case 6: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR6_REFSEQ_GENES);
						break;
				case 7: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR7_REFSEQ_GENES);
						break;
				case 8: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR8_REFSEQ_GENES);
						break;
				case 9: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR9_REFSEQ_GENES);
						break;
				case 10: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR10_REFSEQ_GENES);
						break;
				case 11: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR11_REFSEQ_GENES);
						break;
				case 12: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR12_REFSEQ_GENES);
						break;
				case 13: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR13_REFSEQ_GENES);
						break;
				case 14: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR14_REFSEQ_GENES);
						break;
				case 15: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR15_REFSEQ_GENES);
						break;
				case 16: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR16_REFSEQ_GENES);
						break;
				case 17: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR17_REFSEQ_GENES);
						break;
				case 18: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR18_REFSEQ_GENES);
						break;
				case 19: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR19_REFSEQ_GENES);
						break;
				case 20: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR20_REFSEQ_GENES);
						break;
				case 21: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR21_REFSEQ_GENES);
						break;
				case 22: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap,  regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR22_REFSEQ_GENES);
						break;
				case 23: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHRX_REFSEQ_GENES);
						break;			
				case 24: calculateRegulationBasedKeggPathwayNumberofNonoverlappingBasePairs(chromosomeNumber, chromosomeSizes.get(chromosomeNumber-1), ncbiGeneId2KeggPathwayHashMap, regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHRY_REFSEQ_GENES);
						break;
								
			}//End of switch

		} // End of For each chromosome in the whole genome
		

	}



	
	public void writeNumberofOccurencesonNonoverlappingBasePairs(Map<String,Long> name2NumberofNonoverlappingBasePairsWholeGenomeMap, String outputFileName){
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		try {
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			Set<Map.Entry<String,Long>> set =name2NumberofNonoverlappingBasePairsWholeGenomeMap.entrySet();
			
			Iterator<Map.Entry<String,Long>> iterator = set.iterator();
			
			while(iterator.hasNext()){
				Map.Entry<String,Long> entry = iterator.next();	
				bufferedWriter.write(entry.getKey()+ "\t" + entry.getValue() + System.getProperty("line.separator"));
			}

			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	//args[0] must have input file name with folder
	//args[1] must have GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2] must have Input File Format		
	//args[3] must have Number of Permutations	
	//args[4] must have False Discovery Rate (ex: 0.05)
	//args[5] must have Generate Random Data Mode (with GC and Mapability/without GC and Mapability)
	//args[6] must have writeGeneratedRandomDataMode checkBox
	//args[7] must have writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//args[8] must have writePermutationBasedAnnotationResultMode checkBox
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		
		
		List<Integer> chromosomeSizes = new ArrayList<Integer>(Commons.NUMBER_OF_CHROMOSOMES_HG19);
		
		CalculateNumberofNonOverlappingBasePairsinWholeGenomeUsingSlidingWindow calculate = new CalculateNumberofNonOverlappingBasePairsinWholeGenomeUsingSlidingWindow();
		
		GRCh37Hg19Chromosome.initializeChromosomeSizes(chromosomeSizes);
		GRCh37Hg19Chromosome.getHg19ChromosomeSizes(chromosomeSizes,dataFolder,Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		calculate.printChromosomeSizes(chromosomeSizes);
				
		Map<String,List<String>> keggPathway2NcbiGeneIdHashMap = new HashMap<String,List<String>>();
		Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap = new HashMap<String,List<String>>();
		String pathwayHsaListFileName = Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE;
		
		KeggPathwayUtility.readKeggPathwayHsaListAndCreateHashMaps(pathwayHsaListFileName, keggPathway2NcbiGeneIdHashMap, ncbiGeneId2KeggPathwayHashMap);
		
		
		Map<String,Long> dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap = new HashMap<String,Long>();
		Map<String,Long> tfbs2NumberofNonoverlappingBasePairsWholeGenomeMap = new HashMap<String,Long>();
		Map<String,Long> histone2NumberofNonoverlappingBasePairsWholeGenomeMap = new HashMap<String,Long>();
		Map<String,Long> exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap = new HashMap<String,Long>();
		Map<String,Long> regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap = new HashMap<String,Long>();
		
		//Dnase
		calculate.calculateNumberofNonoverlappingBasePairsforDnase(chromosomeSizes,dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap);
		calculate.writeNumberofOccurencesonNonoverlappingBasePairs(dnaseCellLine2NumberofNonoverlappingBasePairsWholeGenomeMap,Commons.DNASE_CELL_LINE_WHOLE_GENOME_USING_SLIDING_WINDOW);

		//Tfbs
		calculate.calculateNumberofNonoverlappingBasePairsforTfbs(chromosomeSizes,tfbs2NumberofNonoverlappingBasePairsWholeGenomeMap);		
		calculate.writeNumberofOccurencesonNonoverlappingBasePairs(tfbs2NumberofNonoverlappingBasePairsWholeGenomeMap,Commons.TFBS_WHOLE_GENOME_USING_SLIDING_WINDOW);

		//Histone
		calculate.calculateNumberofNonoverlappingBasePairsforHistone(chromosomeSizes,histone2NumberofNonoverlappingBasePairsWholeGenomeMap);						
		calculate.writeNumberofOccurencesonNonoverlappingBasePairs(histone2NumberofNonoverlappingBasePairsWholeGenomeMap,Commons.HISTONE_WHOLE_GENOME_USING_SLIDING_WINDOW);
	
		//exon Based Kegg Pathway
		calculate.calculateNumberofNonoverlappingBasePairsforExonBasedKeggPathway(chromosomeSizes,ncbiGeneId2KeggPathwayHashMap,exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap);						
		calculate.writeNumberofOccurencesonNonoverlappingBasePairs(exonBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap,Commons.EXON_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_SLIDING_WINDOW);
	
		//regulation Based Kegg Pathway
		calculate.calculateNumberofNonoverlappingBasePairsforRegulationBasedKeggPathway(chromosomeSizes,ncbiGeneId2KeggPathwayHashMap,regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap);						
		calculate.writeNumberofOccurencesonNonoverlappingBasePairs(regulationBasedKeggPathway2NumberofNonoverlappingBasePairsWholeGenomeMap,Commons.REGULATION_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_SLIDING_WINDOW);
		
	}

}
