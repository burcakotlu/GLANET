/*
 * spp.optimal.wgEncodeSydhTfbsK562bGata1UcdAlnRep0_VS_wgEncodeSydhTfbsK562bInputUcdAlnRep1.narrowPeak
 * Tfbs Gata1
 * Cell Line K562
 * 
 * Proof of Principle
 * Positive Control
 * 
 * 
 */

package processinputdata.prepare;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import common.Commons;


public class PositiveControl {

	public static void readPositiveControlInputFileandWriteOutputFile(String inputFileName, String outputFileName){
		FileReader  fileReader;
		BufferedReader bufferedReader;
		
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		String strLine;
		/*
		 * All the peak calls are in narrowPeak foramt. It is a BED6+4 format.		
		
		field	type	description
		chrom	 string	 Name of the chromosome
		chromStart	 int	 The starting position of the feature in the chromosome. The first base in a chromosome is numbered 0.
		chromEnd	 int	 The ending position of the feature in the chromosome or scaffold. The chromEnd base is not included in the display of the feature. For example, the first 100 bases of a chromosome are defined as chromStart=0, chromEnd=100, and span the bases numbered 0-99.
		name	 string	 Name given to a region (preferably unique). Use '.' if no name is assigned.
		score	 int	 Indicates how dark the peak will be displayed in the browser (1-1000). If '0', the DCC will assign this based on signal value. Ideally average signalValue per base spread between 100-1000.
		strand	 char	 +/- to denote strand or orientation (whenever applicable). Use '.' if no orientation is assigned.
		signalValue	 float	 Measurement of overall (usually, average) enrichment for the region.
		pValue	 float	 Measurement of statistical signficance (-log10). Use -1 if no pValue is assigned.
		qValue	 float	 Measurement of statistical significance using false discovery rate (-log10). Use -1 if no qValue is assigned.
		peak	 int	 Point-source called for this peak; 0-based offset from chromStart. Use -1 if no point-source called.
		
		 */
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEighthTab;
		int indexofNinethTab;

	    String chrName;
	    int low;
	    int high;
	    int summit;
	    int numberofBases;
	    int numberofBasesafterSummit;
		
		try {
			fileReader = new FileReader(inputFileName);			
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			int minofnumberofBasesBeforeSummit  = Integer.MAX_VALUE;
			int minofnumberofBasesAfterSummit = Integer.MAX_VALUE; 
			int numberofInputLines = 0;
			int numberofInputLinesSatisfiesCriteria = 0;
			
			int meanofSummit =0;
			int meanofNumberofBasesAfterSummit =0;
			
			int newLow;
			int newHigh;
			
			
			while((strLine= bufferedReader.readLine())!=null){
				
				//example line
				//chr22	20012946	20013254	.	0	.	357.198.159.240.837	-1	408.525.489.110.388	160
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
				indexofFifthTab = strLine.indexOf('\t', indexofFourthTab+1);
				indexofSixthTab = strLine.indexOf('\t', indexofFifthTab+1);
				indexofSeventhTab= strLine.indexOf('\t', indexofSixthTab+1);
				indexofEighthTab = strLine.indexOf('\t', indexofSeventhTab+1);
				indexofNinethTab = strLine.indexOf('\t', indexofEighthTab+1);
				
				chrName = strLine.substring(0, indexofFirstTab);
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				numberofBases = high-low;
				summit = Integer.parseInt(strLine.substring(indexofNinethTab+1));
				numberofBasesafterSummit = high-low-summit-1;
				
				numberofInputLines++;
				
				if (summit>136 && numberofBasesafterSummit>136){
					
					numberofInputLinesSatisfiesCriteria++;
							
				}
				
				newLow = low + summit -137;
				newHigh = low + summit + 137;
				
				bufferedWriter.write(chrName + "\t" + newLow  + "\t" + newHigh + "\n");

				
				meanofSummit = meanofSummit + summit;
				meanofNumberofBasesAfterSummit = meanofNumberofBasesAfterSummit + numberofBasesafterSummit;
				
				if(summit < minofnumberofBasesBeforeSummit)
					minofnumberofBasesBeforeSummit = summit;
				
				if (numberofBasesafterSummit < minofnumberofBasesAfterSummit)
					minofnumberofBasesAfterSummit = numberofBasesafterSummit;
			}
			
			meanofSummit = meanofSummit /numberofInputLines;
			meanofNumberofBasesAfterSummit = meanofNumberofBasesAfterSummit / numberofInputLines;
			
			bufferedWriter.close();
			bufferedReader.close();
		
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
			
	}
	
	public static void main(String[] args) {
		
//		String inputFileName = Commons.POSITIVE_CONTROL_INPUT_FILE_NAME;
//		String outputFileName = Commons.POSITIVE_CONTROL_OUTPUT_FILE_NAME;
//		
//		readPositiveControlInputFileandWriteOutputFile(inputFileName,outputFileName);
	}

}
