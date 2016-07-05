/**
 * 
 */
package testing.trees;

import annotation.Annotation;

import common.Commons;

import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;

/**
 * @author Burçak Otlu
 * @date Jul 1, 2016
 * @project Glanet 
 *
 */
public class TestingTrees {

	public static intervaltree.IntervalTree createIntervalTreeCormen(String dataFolder, ChromosomeName chrName){
		
		intervaltree.IntervalTree intervalTree = null;
		
		intervalTree = Annotation.createDnaseIntervalTreeWithNumbers(dataFolder, chrName);
	
		return intervalTree;
	}
	
	public static trees.IntervalTreeMarkdeBerg createIntervalTreeMarkdeBerg(String dataFolder, String outputFolder, ChromosomeName chrName){
		
		trees.IntervalTreeMarkdeBerg intervalTree = null;
		
		intervalTree = trees.IntervalTreeMarkdeBerg.createDnaseIntervalTreeWithNumbers(dataFolder, outputFolder,chrName);
		
		return intervalTree;
	}
	
	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty( "file.separator");


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
		
		intervaltree.IntervalTree dnaseIntervalTreeCormen = null;
		trees.IntervalTreeMarkdeBerg dnaseIntervalTreeMarkdeBerg =null;
		
		long constructionTimeForIntervalTreeCormen = 0;
		long constructionTimeForIntervalTreeMarkdeBerg = 0;
		
		for (ChromosomeName chrName: ChromosomeName.values()){
			
			System.out.println("*****************************************************");
			dateBefore = System.currentTimeMillis();
			dnaseIntervalTreeCormen = createIntervalTreeCormen(dataFolder,chrName);
			dateAfter = System.currentTimeMillis();
			constructionTimeForIntervalTreeCormen= dateAfter - dateBefore;
			System.out.println(chrName.convertEnumtoString() + ": Construction Time for Interval Tree Cormen: " + (constructionTimeForIntervalTreeCormen*1.0f)/1000 + " seconds");
			
			dnaseIntervalTreeCormen = null;
			
			dateBefore = System.currentTimeMillis();
			dnaseIntervalTreeMarkdeBerg = createIntervalTreeMarkdeBerg(dataFolder,outputFolder,chrName);
			dateAfter = System.currentTimeMillis();
			constructionTimeForIntervalTreeMarkdeBerg= dateAfter - dateBefore;
			System.out.println(chrName.convertEnumtoString() + ": Construction Time for Interval Tree Mark de Berg: " + (constructionTimeForIntervalTreeMarkdeBerg*1.0f)/1000 + " seconds");
			
			dnaseIntervalTreeMarkdeBerg = null;
			
			System.out.println("ratio: " + constructionTimeForIntervalTreeMarkdeBerg*1.0f/constructionTimeForIntervalTreeCormen) ;
			
			break;

		}//End of for each chrName 

		
	}

}
