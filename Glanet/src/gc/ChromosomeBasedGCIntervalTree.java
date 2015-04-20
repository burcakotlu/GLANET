/**
 * 
 */
package gc;

import intervaltree.GCIntervalTreeNode;
import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;

/**
 * @author Burçak Otlu
 * @date Apr 10, 2015
 * @project Glanet 
 *
 */
public class ChromosomeBasedGCIntervalTree {
	
	final static Logger logger = Logger.getLogger(ChromosomeBasedGCIntervalTree.class);
	
	public static void fillIntervalTree(
			String dataFolder,
			ChromosomeName chromName,
			IntervalTree gcIntervalTree){
		
		String gcFastaFileName =   Commons.GC + System.getProperty("file.separator") + chromName.convertEnumtoString() +  Commons.GC_INTERVALS_CONSECUTIVE_ZEROS_MERGED_FILE_END;
		
		FileReader fileReader;
		BufferedReader bufferedReader;
	
		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		
		
		int lowZeroBased;
		int highZeroBased;
		short numberofGCs = 0;
		
		GCIntervalTreeNode node = null;
		

	
		try {
			
			fileReader = FileOperations.createFileReader(dataFolder + gcFastaFileName);
			bufferedReader = new BufferedReader(fileReader);

		
			//example line
			//0Based End Inclusive Coordinates
			//low	high	numberofGCs
			//63300	63399	57

			
			/*****************************************************************/
			while ((strLine = bufferedReader.readLine()) != null) {
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = (indexofFirstTab>0)? strLine.indexOf('\t',indexofFirstTab+1):-1;
				
				lowZeroBased = Integer.parseInt(strLine.substring(0, indexofFirstTab));
				highZeroBased = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				numberofGCs = Short.parseShort(strLine.substring(indexofSecondTab+1));
				
				//Create the Interval Tree Node 
				//Insert the created node into the interval tree
				//if numberofGCs is greater than 0
				if (numberofGCs>0){
					//Create new interval tree node
					node = new GCIntervalTreeNode(lowZeroBased, highZeroBased,numberofGCs);	
					
					//Add to the interval tree
					gcIntervalTree.intervalTreeInsert(gcIntervalTree, node);
				}
				
				
				
				
			}//End of WHILE
			/*****************************************************************/
			
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");
	
		
		IntervalTree gcIntervalTree = new IntervalTree();
		fillIntervalTree(dataFolder,ChromosomeName.CHROMOSOME1,gcIntervalTree);
		
		System.out.println("GC Interval Tree NumberofNonOverlappingBases:" + gcIntervalTree.getNumberofNonOverlappingBases());
		System.out.println("GC Interval Tree NumberofNodes: " + gcIntervalTree.getNumberofNodes());

	}

}
