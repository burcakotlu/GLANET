/**
 * @author Burcak Otlu
 * Jan 16, 2014
 * 4:28:36 PM
 * 2014
 *
 * 
 */
package augmentation.results;

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

import userdefined.library.UserDefinedLibraryUtility;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.EnrichmentType;
import enumtypes.MultipleTestingType;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class AugmentationofEnrichmentWithAnnotation {

	/**
	 * 
	 */
	public AugmentationofEnrichmentWithAnnotation() {
	}
	
	//Read C:\Users\burcakotlu\GLANET\Output\Doktora\empiricalpvalues\toBeCollected\Histone
	//Augment it with C:\Users\burcakotlu\GLANET\Output\Doktora\annotate\intervals\parametric\original\histone overlaps
	//Write augmented results
	//For Histone
	public static void	readHistoneAllFileAugmentWrite(String outputFolder,MultipleTestingType multipleTestingParameter,Float FDR, Float bonfCorrectionSignificanceLevel,String inputFileName, String outputFileName){
		String strLine1;
		String strLine2;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab; 
		int indexofNinethTab; 
		
	
		String histoneNameCellLineName;
		
		Float bonfCorrectedPValue; 
		Float bhFDRAdjustedPValue;
		
		FileReader inputFileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter outputFileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		FileReader histoneOriginalOverlapsFileReader = null;
		BufferedReader histoneOriginalOverlapsBufferedReader = null;
				
		List<String> enrichedHistoneElements = new ArrayList<String>();
		
		String givenIntervalChrName;		
		int givenIntervalZeroBasedStart;
		int givenIntervalZeroBasedEnd;		
		String overlapChrName;
		int overlapZeroBasedStart;
		int overlapZeroBasedEnd;
		String rest;

		int givenIntervalOneBasedEnd;		

		int overlapOneBasedEnd;
		
		try {
			inputFileReader = new FileReader(outputFolder + inputFileName);
			bufferedReader 	= new BufferedReader(inputFileReader);
			
			outputFileWriter 	= FileOperations.createFileWriter(outputFolder + outputFileName);
			bufferedWriter 		= new BufferedWriter(outputFileWriter);

			//skip headerLine
			//Element	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 10 Permutations	Number of Permutations	Number of comparisons	empiricalPValue	BonfCorrPVlaue: numberOfComparisons 82	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05
			strLine1= bufferedReader.readLine();
			
			while ((strLine1= bufferedReader.readLine())!=null ){
				
				//old example line
				//H2AZ_K562	129	0	10	162	0.00E+00	0.00E+00	0.00E+00	TRUE

//				new example lines
//				Element Number	Element Name	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 5000 Permutations	Number of Permutations	Number of comparisons	empiricalPValue	BonfCorrPValue for 162 comparisons	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05
//				300630000	H3K27ME3_K562	360	0	5000	162	0.00E+00	0.00E+00	0.00E+00	TRUE

	
				indexofFirstTab 	= strLine1.indexOf('\t');
				indexofSecondTab 	= strLine1.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab 	= strLine1.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab 	= strLine1.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab 	= strLine1.indexOf('\t',indexofFourthTab+1);
				indexofSixthTab 	= strLine1.indexOf('\t',indexofFifthTab+1);
				indexofSeventhTab	= strLine1.indexOf('\t',indexofSixthTab+1);
				indexofEigthTab 	= strLine1.indexOf('\t',indexofSeventhTab+1);
				indexofNinethTab	= strLine1.indexOf('\t',indexofEigthTab+1);
				
				histoneNameCellLineName = strLine1.substring(indexofFirstTab+1, indexofSecondTab);
							
				//Pay attention to the order
				bonfCorrectedPValue= Float.parseFloat(strLine1.substring(indexofSeventhTab+1, indexofEigthTab));
				bhFDRAdjustedPValue = Float.parseFloat(strLine1.substring(indexofEigthTab+1, indexofNinethTab));
											
							
				if(multipleTestingParameter.isBenjaminiHochbergFDR()){					
					if (bhFDRAdjustedPValue <= FDR){					
						enrichedHistoneElements.add(histoneNameCellLineName);	
					}					
				}else if(multipleTestingParameter.isBonferroniCorrection()){					
					if (bonfCorrectedPValue <= bonfCorrectionSignificanceLevel){					
						enrichedHistoneElements.add(histoneNameCellLineName);	
					}					
				}					
			}//end of while : reading enriched dnase elements file line by line.
			
			
			
			//starts
			for(String histoneElementName: enrichedHistoneElements){
				
				bufferedWriter.write("**************" + "\t" + histoneElementName + "\t" + "**************" +  System.getProperty("line.separator"));
												
					histoneOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.HISTONE_ANNOTATION_DIRECTORY + histoneElementName + ".txt");						
					histoneOriginalOverlapsBufferedReader = new BufferedReader(histoneOriginalOverlapsFileReader);
							
					//Get all the lines of the original data annotation for the enriched Histone elements 
					//Write them to the file
					while((strLine2 = histoneOriginalOverlapsBufferedReader.readLine())!=null){
						
						//process strLine2
						if(strLine2.contains("Search")){
							bufferedWriter.write(histoneElementName + "\t" +strLine2 + System.getProperty("line.separator"));
								
						}else{
//							Searched for chr	interval low	interval high	histone node chrom name	node Low	node high	node HistoneName	node CellLineName	node FileName
//							chr1	11862777	11862777	CHROMOSOME1	11862018	11864248	H2AZ	GM12878	wgEncodeBroadHistoneGm12878H2azStdAln.narrowPeak
//							Searched for chr	interval low	interval high	histone node chrom name	node Low	node high	node HistoneName	node CellLineName	node FileName
//							chr17	47440465	47440465	CHROMOSOME17	47437087	47440781	H2AZ	GM12878	wgEncodeBroadHistoneGm12878H2azStdAln.narrowPeak

							indexofFirstTab 	= strLine2.indexOf('\t');
							indexofSecondTab 	= strLine2.indexOf('\t', indexofFirstTab+1);
							indexofThirdTab 	= strLine2.indexOf('\t', indexofSecondTab+1);
							indexofFourthTab 	= strLine2.indexOf('\t', indexofThirdTab+1);
							indexofFifthTab 	= strLine2.indexOf('\t', indexofFourthTab+1);
							indexofSixthTab 	= strLine2.indexOf('\t', indexofFifthTab+1);
							
							givenIntervalChrName = strLine2.substring(0, indexofFirstTab);
							givenIntervalZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFirstTab+1, indexofSecondTab));
							givenIntervalZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofSecondTab+1, indexofThirdTab));
															
							overlapChrName = strLine2.substring(indexofThirdTab+1, indexofFourthTab);
							overlapZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFourthTab+1, indexofFifthTab));
							overlapZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofFifthTab+1, indexofSixthTab));
							
							rest = strLine2.substring(indexofSixthTab+1);
													
							givenIntervalOneBasedEnd 	= givenIntervalZeroBasedEnd+1;
							
							overlapOneBasedEnd = overlapZeroBasedEnd +1;
							
							bufferedWriter.write(histoneElementName + "\t" + givenIntervalChrName + "\t" + givenIntervalZeroBasedStart + "\t"  + givenIntervalOneBasedEnd + "\t" + overlapChrName + "\t" + overlapZeroBasedStart + "\t" + overlapOneBasedEnd +"\t" +rest + System.getProperty("line.separator"));
							
						}
					}//End of while
					
				
			}//End of for	
			//ends
						
			bufferedWriter.close();
			bufferedReader.close();
			
			if(histoneOriginalOverlapsBufferedReader!=null){
				histoneOriginalOverlapsBufferedReader.close();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	 public static void readEnrichedUserDefinedLibraryElementsAugmentWrite(
			 String outputFolder,
			 MultipleTestingType multipleTestingParameter,
			 Float FDR,
			 Float bonfCorrectionSignificanceLevel, 
			 String userDefinedLibraryElementType,
			 String inputFileName,
			 String outputFileName){
		 
		FileReader inputFileReader = null;
		BufferedReader bufferedReader = null;
			
		FileReader userDefinedLibraryAnnotationFileReader = null;
		BufferedReader userDefinedLibraryAnnotationBufferedReader = null;
		
		FileWriter outputFileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine1;
		String strLine2;
		String userDefinedLibraryElementName;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		
		Float bonfCorrectedPValue; 
		Float bhFDRAdjustedPValue;
		
		
		List<String> enrichedUserDefinedLibraryElements = new ArrayList<String>();
		
		String givenIntervalChrName;		
		int givenIntervalZeroBasedStart;
		int givenIntervalZeroBasedEnd;		
		int givenIntervalOneBasedEnd;		
		
		String overlapChrName;
		int overlapZeroBasedStart;
		int overlapZeroBasedEnd;
		int overlapOneBasedEnd;	
		
		String rest;
		
		
		
		
		try {
			inputFileReader = FileOperations.createFileReader(outputFolder + inputFileName);
			bufferedReader = new BufferedReader(inputFileReader);
			
			outputFileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			bufferedWriter = new BufferedWriter(outputFileWriter);
			
			//skip headerLine
			strLine1= bufferedReader.readLine();
			
			while ((strLine1= bufferedReader.readLine())!=null ){
//				new example lines				
//				Element Number	Element Name	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 5000 Permutations	Number of Permutations	Number of comparisons for Bonferroni Correction	empiricalPValue	BonfCorrPValue for 406 comparisons	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05
//				14	H3K36ME3_H1HESC	42	0	5000	406	0E0	0E0	0E0	true

				
				indexofFirstTab 	= strLine1.indexOf('\t');
				indexofSecondTab 	= (indexofFirstTab>0) 	?	strLine1.indexOf('\t',indexofFirstTab+1)	: -1;
				indexofThirdTab 	= (indexofSecondTab>0)	?	strLine1.indexOf('\t',indexofSecondTab+1)	: -1;
				indexofFourthTab 	= (indexofThirdTab>0)	?	strLine1.indexOf('\t',indexofThirdTab+1)	: -1;
				indexofFifthTab 	= (indexofFourthTab>0)	?	strLine1.indexOf('\t',indexofFourthTab+1)	: -1;
				indexofSixthTab 	= (indexofFifthTab>0)	?	strLine1.indexOf('\t',indexofFifthTab+1)	: -1;
				indexofSeventhTab	= (indexofSixthTab>0)	?	strLine1.indexOf('\t',indexofSixthTab+1)	: -1;
				indexofEigthTab 	= (indexofSeventhTab>0)	?	strLine1.indexOf('\t',indexofSeventhTab+1)	: -1;
				indexofNinethTab	= (indexofEigthTab>0)	?	strLine1.indexOf('\t',indexofEigthTab+1)	: -1;
						
				userDefinedLibraryElementName = strLine1.substring(indexofFirstTab+1, indexofSecondTab);
							
				//Pay attention to the order
				bonfCorrectedPValue= Float.parseFloat(strLine1.substring(indexofSeventhTab+1, indexofEigthTab));
				bhFDRAdjustedPValue = Float.parseFloat(strLine1.substring(indexofEigthTab+1, indexofNinethTab));
				
				if(multipleTestingParameter.isBenjaminiHochbergFDR()){
					
					if (bhFDRAdjustedPValue <= FDR){					
						enrichedUserDefinedLibraryElements.add(userDefinedLibraryElementName);	
					}
					
				}else if(multipleTestingParameter.isBonferroniCorrection()){
					
					if (bonfCorrectedPValue <= bonfCorrectionSignificanceLevel){					
						enrichedUserDefinedLibraryElements.add(userDefinedLibraryElementName);	
					}
					
				}
										
			}//end of while : reading enriched userDefinedLibrary elements file line by line.
			
			
			//For each enriched UserDefinedLibrary Element
			for(String elementName: enrichedUserDefinedLibraryElements){
				
				bufferedWriter.write("**************" + "\t" + elementName + "\t" + "**************" +  System.getProperty("line.separator"));
												
				userDefinedLibraryAnnotationFileReader = FileOperations.createFileReader(outputFolder + Commons.USERDEFINEDLIBRARY_ANNOTATION_DIRECTORY + userDefinedLibraryElementType + System.getProperty("file.separator") + elementName + ".txt");						
				userDefinedLibraryAnnotationBufferedReader = new BufferedReader(userDefinedLibraryAnnotationFileReader);
							
					//Get all the lines of the original data annotation for the enriched UserDefinedLibrary
					//Write them to the file
					while((strLine2 = userDefinedLibraryAnnotationBufferedReader.readLine())!=null){
						
						//process strLine2
						if(strLine2.contains("Search")){
							bufferedWriter.write(elementName + "\t" +strLine2 + System.getProperty("line.separator"));
								
						}else{

//							Searched for chr	interval Low	interval High	UserDefinedLibraryNode ChromName	node Low	node High	node Element Name	node FileName
//							chr1	11862777	11862777	chr1	11862700	11862849	AG04450	AG04450-DS12255.peaks.fdr0.01.hg19.bed
//							Searched for chr	interval Low	interval High	UserDefinedLibraryNode ChromName	node Low	node High	node Element Name	node FileName
//							chr1	109817589	109817589	chr1	109817440	109817589	AG04450	AG04450-DS12255.peaks.fdr0.01.hg19.bed
//							Searched for chr	interval Low	interval High	UserDefinedLibraryNode ChromName	node Low	node High	node Element Name	node FileName
//							chr1	154418878	154418878	chr1	154418840	154418989	AG04450	AG04450-DS12270.peaks.fdr0.01.hg19.bed
//							chr1	154418878	154418878	chr1	154418840	154418989	AG04450	AG04450-DS12255.peaks.fdr0.01.hg19.bed

							indexofFirstTab 	= strLine2.indexOf('\t');
							indexofSecondTab 	= (indexofFirstTab>0)	?	strLine2.indexOf('\t', indexofFirstTab+1)	:	-1;
							indexofThirdTab 	= (indexofSecondTab>0)	?	strLine2.indexOf('\t', indexofSecondTab+1)	:	-1;
							indexofFourthTab 	= (indexofThirdTab>0)	?	strLine2.indexOf('\t', indexofThirdTab+1)	:	-1;
							indexofFifthTab 	= (indexofFourthTab>0)	?	strLine2.indexOf('\t', indexofFourthTab+1)	:	-1;
							indexofSixthTab 	= (indexofFifthTab>0)	?	strLine2.indexOf('\t', indexofFifthTab+1)	:	-1;
							
							givenIntervalChrName = strLine2.substring(0, indexofFirstTab);
							givenIntervalZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFirstTab+1, indexofSecondTab));
							givenIntervalZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofSecondTab+1, indexofThirdTab));
														
							overlapChrName = strLine2.substring(indexofThirdTab+1, indexofFourthTab);
							overlapZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFourthTab+1, indexofFifthTab));
							overlapZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofFifthTab+1, indexofSixthTab));
							
							rest = strLine2.substring(indexofSixthTab+1);
													
							givenIntervalOneBasedEnd 	= givenIntervalZeroBasedEnd+1;
							
							overlapOneBasedEnd = overlapZeroBasedEnd +1;
							
							bufferedWriter.write(elementName + "\t" + givenIntervalChrName + "\t" + givenIntervalZeroBasedStart + "\t"  + givenIntervalOneBasedEnd + "\t" + overlapChrName + "\t" + overlapZeroBasedStart + "\t" + overlapOneBasedEnd +"\t" +rest + System.getProperty("line.separator"));
							
						}
						
					}//End of while
					
				
			}//End of For each enriched UserDefinedLibrary Element
	
			
			//Close bufferedReader and bufferedWriter
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
		
	
	//Read C:\Users\burcakotlu\GLANET\Output\Doktora\empiricalpvalues\toBeCollected\Dnase\Dnase_all.txt
	//Augment it with annotate\\intervals\\parametric\\original\\dnase overlaps
	//Write augmented results
	//For Dnase
	public static void readDnaseAllFileAugmentWrite(String outputFolder,MultipleTestingType multipleTestingParameter,Float FDR, Float bonfCorrectionSignificanceLevel,String inputFileName, String outputFileName){
	
		String strLine1;
		String strLine2;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab; 
		int indexofNinethTab;
	
		String dnaseElementName;
		
		Float bonfCorrectedPValue; 
		Float bhFDRAdjustedPValue;
		
		FileReader inputFileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter outputFileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		FileReader dnaseOriginalOverlapFileReader = null;
		BufferedReader dnaseOriginalOverlapBufferedReader = null;
				
		List<String> enrichedDnaseElements = new ArrayList<String>();
		
		String givenIntervalChrName;		
		int givenIntervalZeroBasedStart;
		int givenIntervalZeroBasedEnd;		
		String overlapChrName;
		int overlapZeroBasedStart;
		int overlapZeroBasedEnd;
		String rest;
		
		int givenIntervalOneBasedEnd;		
	
		int overlapOneBasedEnd;	
				
		try {
			inputFileReader = new FileReader(outputFolder + inputFileName);
			bufferedReader = new BufferedReader(inputFileReader);
			
			outputFileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			bufferedWriter = new BufferedWriter(outputFileWriter);

			//skip headerLine
			//Element	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 10 Permutations	Number of Permutations	Number of comparisons	empiricalPValue	BonfCorrPVlaue: numberOfComparisons 82	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05
			strLine1= bufferedReader.readLine();
			
			while ((strLine1= bufferedReader.readLine())!=null ){
//				old example lines				
//				Element	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 8 Permutations	Number of Permutations	Number of comparisons	empiricalPValue	BonfCorrPValue for 82 comparisons	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05
//				NHDF_NEO	51	0	8	82	0.00E+00	0.00E+00	0.00E+00	TRUE

//				new example lines				
//				Element Number	Element Name	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 5000 Permutations	Number of Permutations	Number of comparisons	empiricalPValue	BonfCorrPValue for 82 comparisons	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05
//				560000	HRCE	45	402	5000	82	8.04E-02	1.00E+00	4.71E-01	FALSE

				
				indexofFirstTab 	= strLine1.indexOf('\t');
				indexofSecondTab 	= strLine1.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab 	= strLine1.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab 	= strLine1.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab 	= strLine1.indexOf('\t',indexofFourthTab+1);
				indexofSixthTab 	= strLine1.indexOf('\t',indexofFifthTab+1);
				indexofSeventhTab	= strLine1.indexOf('\t',indexofSixthTab+1);
				indexofEigthTab 	= strLine1.indexOf('\t',indexofSeventhTab+1);
				indexofNinethTab	= strLine1.indexOf('\t',indexofEigthTab+1);
						
				dnaseElementName = strLine1.substring(indexofFirstTab+1, indexofSecondTab);
							
				//Pay attention to the order
				bonfCorrectedPValue= Float.parseFloat(strLine1.substring(indexofSeventhTab+1, indexofEigthTab));
				bhFDRAdjustedPValue = Float.parseFloat(strLine1.substring(indexofEigthTab+1, indexofNinethTab));
				
				if(multipleTestingParameter.isBenjaminiHochbergFDR()){
					
					if (bhFDRAdjustedPValue <= FDR){					
						enrichedDnaseElements.add(dnaseElementName);	
					}
					
				}else if(multipleTestingParameter.isBonferroniCorrection()){
					
					if (bonfCorrectedPValue <= bonfCorrectionSignificanceLevel){					
						enrichedDnaseElements.add(dnaseElementName);	
					}
					
				}
										
			}//end of while : reading enriched dnase elements file line by line.
			
			
			
			//starts
			for(String dnaseName: enrichedDnaseElements){
				
				bufferedWriter.write("**************" + "\t" + dnaseName + "\t" + "**************" +  System.getProperty("line.separator"));
												
					dnaseOriginalOverlapFileReader = new FileReader(outputFolder + Commons.DNASE_ANNOTATION_DIRECTORY  + dnaseName + ".txt");						
					dnaseOriginalOverlapBufferedReader = new BufferedReader(dnaseOriginalOverlapFileReader);
							
					//Get all the lines of the original data annotation for the enriched Dnase
					//Write them to the file
					while((strLine2 = dnaseOriginalOverlapBufferedReader.readLine())!=null){
						
						//process strLine2
						if(strLine2.contains("Search")){
							bufferedWriter.write(dnaseName + "\t" +strLine2 + System.getProperty("line.separator"));
								
						}else{

//							Searched for chr	given interval low	given interval high	dnase overlap chrom name	node low	node high	node CellLineName	node FileName
//							chr10	104846177	104846177	CHROMOSOME10	104846080	104846229	AG04449	AG04449-DS12319.peaks.fdr0.01.hg19.bed
//							chr10	104846177	104846177	CHROMOSOME10	104846100	104846249	AG04449	AG04449-DS12329.peaks.fdr0.01.hg19.bed

							indexofFirstTab 	= strLine2.indexOf('\t');
							indexofSecondTab 	= strLine2.indexOf('\t', indexofFirstTab+1);
							indexofThirdTab 	= strLine2.indexOf('\t', indexofSecondTab+1);
							indexofFourthTab 	= strLine2.indexOf('\t', indexofThirdTab+1);
							indexofFifthTab 	= strLine2.indexOf('\t', indexofFourthTab+1);
							indexofSixthTab 	= strLine2.indexOf('\t', indexofFifthTab+1);
							
							givenIntervalChrName = strLine2.substring(0, indexofFirstTab);
							givenIntervalZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFirstTab+1, indexofSecondTab));
							givenIntervalZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofSecondTab+1, indexofThirdTab));
														
							overlapChrName = strLine2.substring(indexofThirdTab+1, indexofFourthTab);
							overlapZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFourthTab+1, indexofFifthTab));
							overlapZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofFifthTab+1, indexofSixthTab));
							
							rest = strLine2.substring(indexofSixthTab+1);
													
							givenIntervalOneBasedEnd 	= givenIntervalZeroBasedEnd+1;
							
							overlapOneBasedEnd = overlapZeroBasedEnd +1;
							
							bufferedWriter.write(dnaseName + "\t" + givenIntervalChrName + "\t" + givenIntervalZeroBasedStart + "\t"  + givenIntervalOneBasedEnd + "\t" + overlapChrName + "\t" + overlapZeroBasedStart + "\t" + overlapOneBasedEnd +"\t" +rest + System.getProperty("line.separator"));
							
						}
						
					}//End of while
					
				
			}//End of for	
			//ends
			
			
			bufferedWriter.close();
			bufferedReader.close();
			
			if (dnaseOriginalOverlapBufferedReader!=null) {
				dnaseOriginalOverlapBufferedReader.close();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	
	//Read the empiricalpvalues\\output\\tobeCollected\\TfKeggPathway\\  
	//and
	//Augment it with annotate\\intervals\\parametric\\original\\tfKeggPathway overlapped intervals
	//Write augmented results
	//For Tf KeggPathway
	public static void readTfKeggPathwayAllAugmentWrite(String outputFolder,MultipleTestingType multipleTestingParameter, Float FDR, Float bonfCorrectionSignificanceLevel, String inputFileName, String outputFileName, String type){
		String strLine1;
		String strLine2;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;;
			
		int indexofFirstUnderscore;
		
		String tfName_keggPathwayName;
		String keggPathwayName;
		String keggPathwayDescription;
		Float bonfCorrectedPValue; 
		String keggPathwayNameandDescription;
		
		Float bhFDRAdjustedPValue ;
		
		FileReader tfandKeggPathwayOriginalOverlapsFileReader = null;
		BufferedReader tfandKeggPathwayOriginalOverlapsBufferedReader = null;
		
		Map<String,List<String>> enrichedKeggPathways = new HashMap<String,List<String>>();
		List<String> lines;
		
		String givenIntervalChrName;		
		int givenIntervalZeroBasedStart;
		int givenIntervalZeroBasedEnd;	
		
		String tfNameCellLineName;
		int tfCellLineZeroBasedStart;
		int tfCellLineZeroBasedEnd;
				
		String refseqGeneName;
		int refseqGeneZeroBasedStart;
		int refseqGeneZeroBasedEnd;
		
		String rest;

		int givenIntervalOneBasedEnd;		

		int tfCellLineOneBasedEnd;
		
		int refseqGeneOneBasedEnd;
	
				
		try {
			FileReader inputFileReader  = new FileReader(outputFolder + inputFileName);
			BufferedReader bufferedReader = new BufferedReader(inputFileReader);
			
			FileWriter outputFileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			BufferedWriter bufferedWriter = new BufferedWriter(outputFileWriter);
			
			//skip headerLine
			//Name	OriginalNumberofOverlaps	AccumulatedNumberofOverlaps	NumberofPermutations	NumberofComparisons	BonfCorrEmpiricalPValue	EmpiricalPValue
			strLine1= bufferedReader.readLine();
			
			while ((strLine1= bufferedReader.readLine())!=null ){
			

//				old example line
//				JUND_hsa05164	4	0	10000	40081	0.00E+00	0.00E+00	0.00E+00	TRUE
				
//				new example lines
//				Element Number	Element Name	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 5000 Permutations	Number of Permutations	Number of comparisons	empiricalPValue	BonfCorrPValue for 40081 comparisons	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05			
//				4500000233	HDAC2SC6296_hsa05160	3	0	5000	40081	0.00E+00	0.00E+00	0.00E+00	TRUE	Hepatitis C - Homo sapiens (human)	10000, 100132463, 100506658, 10062, 10197, 1026, 10379, 10686, 1147, 1364, 1365, 1366, 137075, 1432, 148022, 149461, 1950, 1956, 1965, 207, 208, 23533, 23562, 23586, 24146, 26285, 27102, 2885, 29110, 2932, 3265, 3434, 3439, 3440, 3441, 3442, 3443, 3444, 3445, 3446, 3447, 3448, 3449, 3451, 3452, 3454, 3455, 3456, 3551, 3576, 3646, 3659, 3661, 3665, 369, 3716, 3845, 3949, 439996, 440275, 4790, 4792, 4893, 4938, 4939, 4940, 49861, 5010, 51208, 5163, 5290, 5291, 5293, 5294, 5295, 5296, 53842, 5465, 5515, 5516, 5518, 5519, 5520, 5521, 5522, 55844, 5594, 5595, 5599, 5600, 5601, 5602, 5603, 5610, 572, 57506, 5894, 5970, 6041, 6256, 6300, 644672, 6654, 6655, 673, 6772, 6773, 6774, 7098, 7122, 7124, 7132, 7157, 7186, 7187, 7189, 7297, 8503, 8517, 8554, 8717, 8737, 9021, 9071, 9073, 9074, 9075, 9076, 9080, 9451, 949, 9641, 975	AKT3, CLDN24, OCLN, NR1H3, PSME3, CDKN1A, IRF9, CLDN16, CHUK, CLDN4, CLDN3, CLDN7, CLDN23, MAPK14, TICAM1, CLDN19, EGF, EGFR, EIF2S1, AKT1, AKT2, PIK3R5, CLDN14, DDX58, CLDN15, CLDN17, EIF2AK1, GRB2, TBK1, GSK3B, HRAS, IFIT1, IFNA1, IFNA2, IFNA4, IFNA5, IFNA6, IFNA7, IFNA8, IFNA10, IFNA13, IFNA14, IFNA16, IFNA17, IFNA21, IFNAR1, IFNAR2, IFNB1, IKBKB, IL8, EIF3E, IRF1, IRF3, IRF7, ARAF, JAK1, KRAS, LDLR, IFIT1B, EIF2AK4, NFKB1, NFKBIA, NRAS, OAS1, OAS2, OAS3, CLDN20, CLDN11, CLDN18, PDK1, PIK3CA, PIK3CB, PIK3CD, PIK3CG, PIK3R1, PIK3R2, CLDN22, PPARA, PPP2CA, PPP2CB, PPP2R1A, PPP2R1B, PPP2R2A, PPP2R2B, PPP2R2C, PPP2R2D, MAPK1, MAPK3, MAPK8, MAPK11, MAPK9, MAPK10, MAPK13, EIF2AK2, BAD, MAVS, RAF1, RELA, RNASEL, RXRA, MAPK12, CLDN25, SOS1, SOS2, BRAF, STAT1, STAT2, STAT3, TLR3, CLDN5, TNF, TNFRSF1A, TP53, TRAF2, TRAF3, TRAF6, TYK2, PIK3R3, IKBKG, PIAS1, TRADD, RIPK1, SOCS3, CLDN10, CLDN8, CLDN6, CLDN2, CLDN1, CLDN9, EIF2AK3, SCARB1, IKBKE, CD81

				
				indexofFirstTab 	= strLine1.indexOf('\t');
				indexofSecondTab 	= strLine1.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab 	= strLine1.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab 	= strLine1.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab 	= strLine1.indexOf('\t',indexofFourthTab+1);
				indexofSixthTab 	= strLine1.indexOf('\t',indexofFifthTab+1);
				indexofSeventhTab	= strLine1.indexOf('\t',indexofSixthTab+1);
				indexofEigthTab 	= strLine1.indexOf('\t',indexofSeventhTab+1);
				indexofNinethTab 	= strLine1.indexOf('\t',indexofEigthTab+1);
				indexofTenthTab 	= strLine1.indexOf('\t',indexofNinethTab+1);
				indexofEleventhTab	= strLine1.indexOf('\t',indexofTenthTab+1);
					
				tfName_keggPathwayName = strLine1.substring(indexofFirstTab+1, indexofSecondTab);
				
				indexofFirstUnderscore = tfName_keggPathwayName.indexOf('_');
				keggPathwayName = tfName_keggPathwayName.substring(indexofFirstUnderscore+1);
				
				
				//Pay attention to the order
				bonfCorrectedPValue= Float.parseFloat(strLine1.substring(indexofSeventhTab+1, indexofEigthTab));
				bhFDRAdjustedPValue = Float.parseFloat(strLine1.substring(indexofEigthTab+1, indexofNinethTab));
				
				keggPathwayDescription = strLine1.substring(indexofTenthTab+1, indexofEleventhTab);
				
				if(multipleTestingParameter.isBenjaminiHochbergFDR()){					
					if (bhFDRAdjustedPValue <= FDR){											
						lines = enrichedKeggPathways.get(keggPathwayName + "\t" + keggPathwayDescription);		
						if (lines==null){
							lines = new ArrayList<String>();
							lines.add(strLine1.substring(indexofFirstTab+1));
							enrichedKeggPathways.put(keggPathwayName+ "\t" +keggPathwayDescription, lines);
						}else{
							lines.add(strLine1.substring(indexofFirstTab+1));
						}			
					}					
				}else if(multipleTestingParameter.isBonferroniCorrection()){					
					if (bonfCorrectedPValue <= bonfCorrectionSignificanceLevel){											
						lines = enrichedKeggPathways.get(keggPathwayName + "\t" + keggPathwayDescription);		
						if (lines==null){
							lines = new ArrayList<String>();
							lines.add(strLine1.substring(indexofFirstTab+1));
							enrichedKeggPathways.put(keggPathwayName+ "\t" +keggPathwayDescription, lines);
						}else{
							lines.add(strLine1.substring(indexofFirstTab+1));
						}			
					}					
				}
								
					
			}//end of while : reading enriched tf and kegg Pathway file line by line.
			
			
			for(Map.Entry<String,List<String>> entry: enrichedKeggPathways.entrySet()){
				keggPathwayNameandDescription = entry.getKey();
				indexofFirstTab = keggPathwayNameandDescription.indexOf("\t");
				keggPathwayDescription = keggPathwayNameandDescription.substring(indexofFirstTab+1);
				
				lines = entry.getValue();
				
				
				bufferedWriter.write("**************\t" + keggPathwayNameandDescription + "\t**************" + System.getProperty("line.separator"));
							
				for(String strLine: lines){
					indexofFirstTab = strLine.indexOf('\t');
					tfName_keggPathwayName = strLine.substring(0,indexofFirstTab);
					
					if(Commons.TF_EXON_BASED_KEGG_PATHWAY.equals(type)){
						tfandKeggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.TF_EXON_BASED_KEGG_PATHWAY_ANNOTATION + tfName_keggPathwayName + ".txt");						
					}else if (Commons.TF_REGULATION_BASED_KEGG_PATHWAY.equals(type)){
						tfandKeggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.TF_REGULATION_BASED_KEGG_PATHWAY_ANNOTATION  + tfName_keggPathwayName + ".txt");						
					}else if (Commons.TF_ALL_BASED_KEGG_PATHWAY.equals(type)){
						tfandKeggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.TF_ALL_BASED_KEGG_PATHWAY_ANNOTATION  + tfName_keggPathwayName + ".txt");					
					}
					
					tfandKeggPathwayOriginalOverlapsBufferedReader = new BufferedReader(tfandKeggPathwayOriginalOverlapsFileReader);
							
					//Get all the lines of the original data annotation for the enriched Tf and KeggPathway 
					//Write them to the file
					while((strLine2 = tfandKeggPathwayOriginalOverlapsBufferedReader.readLine())!=null){
//						Search for chr	given interval low	given interval high	tfbs	tfbs low	tfbs high	refseq gene name	ucscRefSeqGene low	ucscRefSeqGene high	interval name 	hugo suymbol	entrez id	keggPathwayName
//						CHROMOSOME1	11862777	11862777	POL2_K562	11862639	11862938	NM_005957	11861456	11862936	INTRON	MTHFR	4524	hsa00670
//						CHROMOSOME1	11862777	11862777	POL2_K562	11862636	11862905	NM_005957	11861456	11862936	INTRON	MTHFR	4524	hsa00670
						
						//process strLine2
						if(strLine2.contains("Search")){
							bufferedWriter.write(tfName_keggPathwayName + "\t" +strLine2 +  "\t" + keggPathwayDescription + System.getProperty("line.separator"));
									
						}else{

							indexofFirstTab 	= strLine2.indexOf('\t');
							indexofSecondTab 	= strLine2.indexOf('\t', indexofFirstTab+1);
							indexofThirdTab 	= strLine2.indexOf('\t', indexofSecondTab+1);
							indexofFourthTab 	= strLine2.indexOf('\t', indexofThirdTab+1);
							indexofFifthTab 	= strLine2.indexOf('\t', indexofFourthTab+1);
							indexofSixthTab 	= strLine2.indexOf('\t', indexofFifthTab+1);
							indexofSeventhTab 	= strLine2.indexOf('\t', indexofSixthTab+1);
							indexofEigthTab 	= strLine2.indexOf('\t', indexofSeventhTab+1);
							indexofNinethTab 	= strLine2.indexOf('\t', indexofEigthTab+1);
							
							givenIntervalChrName = strLine2.substring(0, indexofFirstTab);
							givenIntervalZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFirstTab+1, indexofSecondTab));
							givenIntervalZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofSecondTab+1, indexofThirdTab));
								
							tfNameCellLineName = strLine2.substring(indexofThirdTab+1, indexofFourthTab);
							tfCellLineZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFourthTab+1, indexofFifthTab));
							tfCellLineZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofFifthTab+1, indexofSixthTab));
							
							refseqGeneName = strLine2.substring(indexofSixthTab+1, indexofSeventhTab);
							refseqGeneZeroBasedStart = Integer.parseInt(strLine2.substring(indexofSeventhTab+1, indexofEigthTab));
							refseqGeneZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofEigthTab+1, indexofNinethTab));
														
							rest = strLine2.substring(indexofNinethTab+1);
													
							givenIntervalOneBasedEnd 	= givenIntervalZeroBasedEnd+1;
							
							tfCellLineOneBasedEnd = tfCellLineZeroBasedEnd +1;
							
							refseqGeneOneBasedEnd = refseqGeneZeroBasedEnd+1;
							
							bufferedWriter.write(tfName_keggPathwayName + "\t" + givenIntervalChrName + "\t" + givenIntervalZeroBasedStart + "\t"  + givenIntervalOneBasedEnd + "\t" +tfNameCellLineName + "\t" + tfCellLineZeroBasedStart + "\t" + tfCellLineOneBasedEnd + "\t" + refseqGeneName + "\t" + refseqGeneZeroBasedStart + "\t" + refseqGeneOneBasedEnd + "\t" + rest +  "\t" + keggPathwayDescription + System.getProperty("line.separator"));
									 
						}
					}//End of while
					
				} //End of for each enriched tf element with that enriched kegg pathway element  
			}//End of for each enriched Kegg Pathway element	
			
			
			bufferedReader.close();
			bufferedWriter.close();
			
			if (tfandKeggPathwayOriginalOverlapsBufferedReader!=null) {
				tfandKeggPathwayOriginalOverlapsBufferedReader.close();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//Read the empiricalpvalues\\toBeCollected\\TfCellLineKeggPathway
	//Augment it with annotate\\intervals\\parametric\\original\\TfCellLineKeggPathway overlapped intervals
	//Write augmented results
	//For Tf CellLine KeggPathway
	
	//ExampleLine
	//POL24H8_HCT116_hsa05020	0.00E+00	Prion diseases - Homo sapiens (human)	100506742, 10963, 1958, 2002, 2534, 3303, 3309, 3552, 3553, 3569, 3915, 4684, 4685, 4851, 5566, 5567, 5568, 5594, 5595, 5604, 5605, 5613, 5621, 581, 6352, 6647, 712, 713, 714, 727, 729, 730, 731, 732, 733, 735	CASP12, STIP1, EGR1, ELK1, FYN, HSPA1A, HSPA5, IL1A, IL1B, IL6, LAMC1, NCAM1, NCAM2, NOTCH1, PRKACA, PRKACB, PRKACG, MAPK1, MAPK3, MAP2K1, MAP2K2, PRKX, PRNP, BAX, CCL5, SOD1, C1QA, C1QB, C1QC, C5, C6, C7, C8A, C8B, C8G, C9
	public static void readTfCellLineKeggPathwayAllFileAugmentWrite(String outputFolder,MultipleTestingType multipleTestingParameter,Float FDR, Float bonfCorrectionSignificanceLevel, String inputFileName, String outputFileName, String type){
		String strLine1;
		String strLine2;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;
				
		
		int indexofFirstUnderscore;
		int indexofSecondUnderscore;
		
		String tfName_cellLineName_keggPathwayName;
		String keggPathwayName;
		Float bonfCorrectedPValue; 
		Float bhFDRAdjustedPValue;
		String keggPathwayDescription;
		String keggPathwayNameandDescription;
		
		FileReader tfCellLineKeggPathwayOriginalOverlapsFileReader = null;
		BufferedReader tfCellLineKeggPathwayOriginalOverlapsBufferedReader = null;
		
		Map<String,List<String>> enrichedKeggPathways = new HashMap<String,List<String>>();
		List<String> lines;
		
		String givenIntervalChrName;		
		int givenIntervalZeroBasedStart;
		int givenIntervalZeroBasedEnd;	
		
		String tfNameCellLineName;
		int tfCellLineZeroBasedStart;
		int tfCellLineZeroBasedEnd;
				
		String refseqGeneName;
		int refseqGeneZeroBasedStart;
		int refseqGeneZeroBasedEnd;
		
		String rest;

		int givenIntervalOneBasedEnd;		

		int tfCellLineOneBasedEnd;
		
		int refseqGeneOneBasedEnd;
		
		try {
			FileReader inputFileReader  = new FileReader(outputFolder + inputFileName);
			BufferedReader bufferedReader = new BufferedReader(inputFileReader);
			
			FileWriter outputFileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			BufferedWriter bufferedWriter = new BufferedWriter(outputFileWriter);
			
			//Skip header line
			//Name	OriginalNumberofOverlaps	AccumulatedNumberofOverlaps	NumberofPermutations	NumberofComparisons	BonfCorrEmpiricalPValue	EmpiricalPValue
			strLine1= bufferedReader.readLine();
			
			while ((strLine1= bufferedReader.readLine())!=null ){
			
				
//				old example line			
//				HEY1_K562_hsa05166	5	0	10000	109214	0.00E+00	0.00E+00	0.00E+00	TRUE
				
//				new example lines exist starting with element number
//				Element Number	Element Name	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 10000 Permutations	Number of Permutations	Number of comparisons	empiricalPValue	BonfCorrPValue for 109214 comparisons	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05			
//				6800990031	NFKB_GM15510_hsa00380	3	0	10000	109214	0.00E+00	0.00E+00	0.00E+00	TRUE	Tryptophan metabolism - Homo sapiens (human)	11185, 121278, 125061, 130013, 15, 1543, 1544, 1545, 1644, 169355, 1892, 1962, 217, 219, 223, 224, 23498, 259307, 26, 2639, 3030, 3033, 316, 3620, 38, 39, 4128, 4129, 438, 4967, 501, 51166, 55753, 56267, 6999, 7166, 847, 8564, 883, 8942	INMT, TPH2, AFMID, ACMSD, AANAT, CYP1A1, CYP1A2, CYP1B1, DDC, IDO2, ECHS1, EHHADH, ALDH2, ALDH1B1, ALDH9A1, ALDH3A2, HAAO, IL4I1, ABP1, GCDH, HADHA, HADH, AOX1, IDO1, ACAT1, ACAT2, MAOA, MAOB, ASMT, OGDH, ALDH7A1, AADAT, OGDHL, CCBL2, TDO2, TPH1, CAT, KMO, CCBL1, KYNU


				indexofFirstTab 	= strLine1.indexOf('\t');
				indexofSecondTab 	= strLine1.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab 	= strLine1.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab 	= strLine1.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab 	= strLine1.indexOf('\t',indexofFourthTab+1);
				indexofSixthTab 	= strLine1.indexOf('\t',indexofFifthTab+1);
				indexofSeventhTab	= strLine1.indexOf('\t',indexofSixthTab+1);
				indexofEigthTab 	= strLine1.indexOf('\t',indexofSeventhTab+1);
				indexofNinethTab 	= strLine1.indexOf('\t',indexofEigthTab+1);
				indexofTenthTab 	= strLine1.indexOf('\t',indexofNinethTab+1);
				indexofEleventhTab	= strLine1.indexOf('\t',indexofTenthTab+1);
				
				tfName_cellLineName_keggPathwayName = strLine1.substring(indexofFirstTab+1, indexofSecondTab);
				
				indexofFirstUnderscore = tfName_cellLineName_keggPathwayName.indexOf('_');
				indexofSecondUnderscore = tfName_cellLineName_keggPathwayName.indexOf('_',indexofFirstUnderscore+1);
				keggPathwayName = tfName_cellLineName_keggPathwayName.substring(indexofSecondUnderscore+1);
				
				//Pay attention order is important
				bonfCorrectedPValue= Float.parseFloat(strLine1.substring(indexofSeventhTab+1, indexofEigthTab));
				bhFDRAdjustedPValue = Float.parseFloat(strLine1.substring(indexofEigthTab+1, indexofNinethTab));
				
				keggPathwayDescription = strLine1.substring(indexofTenthTab+1, indexofEleventhTab);
					
				if(multipleTestingParameter.isBenjaminiHochbergFDR()){
					
					if (bhFDRAdjustedPValue <= FDR){					
						
						lines = enrichedKeggPathways.get(keggPathwayName + "\t" + keggPathwayDescription);
						
						if (lines==null){
							lines = new ArrayList<String>();
							lines.add(strLine1.substring(indexofFirstTab+1));
							enrichedKeggPathways.put(keggPathwayName+ "\t" +keggPathwayDescription, lines);
						}else{
							lines.add(strLine1.substring(indexofFirstTab+1));
						}
					}
					
				}else if(multipleTestingParameter.isBonferroniCorrection()){
					
					if (bonfCorrectedPValue <= bonfCorrectionSignificanceLevel){					
						
						lines = enrichedKeggPathways.get(keggPathwayName + "\t" + keggPathwayDescription);
						
						if (lines==null){
							lines = new ArrayList<String>();
							lines.add(strLine1.substring(indexofFirstTab+1));
							enrichedKeggPathways.put(keggPathwayName+ "\t" +keggPathwayDescription, lines);
						}else{
							lines.add(strLine1.substring(indexofFirstTab+1));
						}
					}
					
				}
					
			}//End of while : reading Tf CellLine KeggPathway input file line by line
			
			
			
			for(Map.Entry<String,List<String>> entry: enrichedKeggPathways.entrySet()){
				
				keggPathwayNameandDescription = entry.getKey();
				indexofFirstTab = keggPathwayNameandDescription.indexOf("\t");
				keggPathwayDescription = keggPathwayNameandDescription.substring(indexofFirstTab+1);
										
				lines = entry.getValue();
								
				bufferedWriter.write("**************\t" + keggPathwayNameandDescription + "\t**************" + System.getProperty("line.separator"));
								
				for(String strLine: lines){
					indexofFirstTab = strLine.indexOf('\t');
					tfName_cellLineName_keggPathwayName = strLine.substring(0,indexofFirstTab);
					
					//Get the original data annotation results
					if(Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY.equals(type)){
						tfCellLineKeggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_ANNOTATION  + tfName_cellLineName_keggPathwayName + ".txt");
						
					}else if (Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY.equals(type)){
						tfCellLineKeggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_ANNOTATION  + tfName_cellLineName_keggPathwayName + ".txt");
						
					}else if (Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY.equals(type)){
						tfCellLineKeggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_ANNOTATION  + tfName_cellLineName_keggPathwayName + ".txt");
						
					}
					
					tfCellLineKeggPathwayOriginalOverlapsBufferedReader = new BufferedReader(tfCellLineKeggPathwayOriginalOverlapsFileReader);
					
					//Add the original data annotation results
					//Get all the lines of the original data annotation for the enriched cellline based Tf and KeggPathway 
					//Write them to the file
					while((strLine2 = tfCellLineKeggPathwayOriginalOverlapsBufferedReader.readLine())!=null){
						
//						Search for chr	given interval low	given interval high	tfbs	tfbs low	tfbs high	refseq gene name	ucscRefSeqGene low	ucscRefSeqGene high	interval name 	hugo suymbol	entrez id	keggPathwayName
//						CHROMOSOME1	11862777	11862777	POL2_K562	11862639	11862938	NM_005957	11861456	11862936	INTRON	MTHFR	4524	hsa00670
//						CHROMOSOME1	11862777	11862777	POL2_K562	11862636	11862905	NM_005957	11861456	11862936	INTRON	MTHFR	4524	hsa00670
						
						//process strLine2
						if(strLine2.contains("Search")){
							bufferedWriter.write(tfName_cellLineName_keggPathwayName + "\t" +strLine2 + "\t" + keggPathwayDescription + System.getProperty("line.separator"));				
						}else{

							indexofFirstTab 	= strLine2.indexOf('\t');
							indexofSecondTab 	= strLine2.indexOf('\t', indexofFirstTab+1);
							indexofThirdTab 	= strLine2.indexOf('\t', indexofSecondTab+1);
							indexofFourthTab 	= strLine2.indexOf('\t', indexofThirdTab+1);
							indexofFifthTab 	= strLine2.indexOf('\t', indexofFourthTab+1);
							indexofSixthTab 	= strLine2.indexOf('\t', indexofFifthTab+1);
							indexofSeventhTab 	= strLine2.indexOf('\t', indexofSixthTab+1);
							indexofEigthTab 	= strLine2.indexOf('\t', indexofSeventhTab+1);
							indexofNinethTab 	= strLine2.indexOf('\t', indexofEigthTab+1);
							
							givenIntervalChrName = strLine2.substring(0, indexofFirstTab);
							givenIntervalZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFirstTab+1, indexofSecondTab));
							givenIntervalZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofSecondTab+1, indexofThirdTab));
													
							tfNameCellLineName = strLine2.substring(indexofThirdTab+1, indexofFourthTab);
							tfCellLineZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFourthTab+1, indexofFifthTab));
							tfCellLineZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofFifthTab+1, indexofSixthTab));
							
							refseqGeneName = strLine2.substring(indexofSixthTab+1, indexofSeventhTab);
							refseqGeneZeroBasedStart = Integer.parseInt(strLine2.substring(indexofSeventhTab+1, indexofEigthTab));
							refseqGeneZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofEigthTab+1, indexofNinethTab));
														
							rest = strLine2.substring(indexofNinethTab+1);
													
							givenIntervalOneBasedEnd 	= givenIntervalZeroBasedEnd+1;
							
							tfCellLineOneBasedEnd = tfCellLineZeroBasedEnd +1;
							
							refseqGeneOneBasedEnd = refseqGeneZeroBasedEnd+1;
							
							bufferedWriter.write(tfName_cellLineName_keggPathwayName + "\t" + givenIntervalChrName + "\t" + givenIntervalZeroBasedStart + "\t"  + givenIntervalOneBasedEnd + "\t" +tfNameCellLineName + "\t" + tfCellLineZeroBasedStart + "\t" + tfCellLineOneBasedEnd + "\t" + refseqGeneName + "\t" + refseqGeneZeroBasedStart + "\t" + refseqGeneOneBasedEnd + "\t" + rest +  "\t" + keggPathwayDescription + System.getProperty("line.separator"));
									 
						}
			
					}//End of while
					
				}
			}	
				
		
			bufferedReader.close();
			bufferedWriter.close();
			
			if (tfCellLineKeggPathwayOriginalOverlapsBufferedReader!=null){
				tfCellLineKeggPathwayOriginalOverlapsBufferedReader.close();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	//read KeggPathwayall File
	//Augment EnrichedKeggPathwayElements with  original Overlapped Intervals and Write
	public static void readKeggPathwayAllFileAugmentWrite(
			String outputFolder,
			MultipleTestingType multipleTestingParameter,
			Float FDR, 
			Float bonfCorrectionSignificanceLevel,
			String inputFileName, 
			String outputFileName,
			String type,
			String userDefinedGeneSetName){
		
		String strLine1;
		String strLine2;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;
		
		
		int indexofFirstUnderscore;
		int indexofSecondUnderscore;
						
		String keggPathwayName;
		String keggPathwayDescription;

		Float bonfCorrectedPValue; 
		Float bhFDRAdjustedPValue ;
		
		FileReader keggPathwayOriginalOverlapsFileReader = null;
		BufferedReader keggPathwayOriginalOverlapsBufferedReader = null;
		
		List<String> enrichedKeggPathways = new ArrayList<String>();
			
		String givenIntervalChrName;		
		int givenIntervalZeroBasedStart;
		int givenIntervalZeroBasedEnd;		
		String overlapChrName;
		int overlapZeroBasedStart;
		int overlapZeroBasedEnd;
		String rest;

		int givenIntervalOneBasedEnd;		

		int overlapOneBasedEnd;

		try {
			FileReader inputFileReader  = new FileReader(outputFolder + inputFileName);
			BufferedReader bufferedReader = new BufferedReader(inputFileReader);
			
			FileWriter outputFileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			BufferedWriter bufferedWriter = new BufferedWriter(outputFileWriter);
			
			//skip headerLine
//			Element	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 8 Permutations	Number of Permutations	Number of comparisons	empiricalPValue	BonfCorrPValue for 269 comparisons	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05			
			strLine1= bufferedReader.readLine();
			
			
			/******************************************************************************/
			/*******************Get the Enriched Elements starts***************************/
			while ((strLine1= bufferedReader.readLine())!=null ){
			
				

//				new example lines
//				Element Number	Element Name	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 5000 Permutations	Number of Permutations	Number of comparisons	empiricalPValue	BonfCorrPValue for 269 comparisons	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05			
//				251	hsa05216	22	0	5000	269	0.00E+00	0.00E+00	0.00E+00	TRUE	Thyroid cancer - Homo sapiens (human)	10342, 1499, 3265, 3845, 4609, 4893, 4914, 51176, 5468, 5594, 5595, 5604, 5605, 595, 5979, 6256, 6257, 6258, 673, 6932, 6934, 7157, 7170, 7175, 7849, 8030, 8031, 83439, 999	TFG, CTNNB1, HRAS, KRAS, MYC, NRAS, NTRK1, LEF1, PPARG, MAPK1, MAPK3, MAP2K1, MAP2K2, CCND1, RET, RXRA, RXRB, RXRG, BRAF, TCF7, TCF7L2, TP53, TPM3, TPR, PAX8, CCDC6, NCOA4, TCF7L1, CDH1

				
				indexofFirstTab 	= strLine1.indexOf('\t');
				indexofSecondTab 	= indexofFirstTab>0 ? strLine1.indexOf('\t',indexofFirstTab+1) : -1;
				indexofThirdTab 	= indexofSecondTab>0 ? strLine1.indexOf('\t',indexofSecondTab+1) : -1;
				indexofFourthTab 	= indexofThirdTab>0 ? strLine1.indexOf('\t',indexofThirdTab+1) : -1;
				indexofFifthTab 	= indexofFourthTab>0 ? strLine1.indexOf('\t',indexofFourthTab+1) : -1;
				indexofSixthTab 	= indexofFifthTab>0 ? strLine1.indexOf('\t',indexofFifthTab+1) : -1;
				indexofSeventhTab	= indexofSixthTab>0 ? strLine1.indexOf('\t',indexofSixthTab+1) : -1;
				indexofEigthTab 	= indexofSeventhTab>0 ? strLine1.indexOf('\t',indexofSeventhTab+1) : -1;
				indexofNinethTab 	= indexofEigthTab>0 ? strLine1.indexOf('\t',indexofEigthTab+1) : -1;
				indexofTenthTab 	= indexofNinethTab>0 ? strLine1.indexOf('\t',indexofNinethTab+1) : -1;
				indexofEleventhTab	= indexofTenthTab>0 ? strLine1.indexOf('\t',indexofTenthTab+1) : -1;
					
				keggPathwayName = strLine1.substring(indexofFirstTab+1, indexofSecondTab);
									
				
				//Pay attention to the order
				bonfCorrectedPValue= Float.parseFloat(strLine1.substring(indexofSeventhTab+1, indexofEigthTab));
				bhFDRAdjustedPValue = Float.parseFloat(strLine1.substring(indexofEigthTab+1, indexofNinethTab));
				
				//KeggPathway Case
				if (indexofTenthTab>0 && indexofEleventhTab>0){
					keggPathwayDescription = strLine1.substring(indexofTenthTab+1, indexofEleventhTab);		
				}
				//UserDefinedGeneSet Case
				else if (indexofTenthTab>0){
					keggPathwayDescription = strLine1.substring(indexofTenthTab+1);		
				}
				else{
					keggPathwayDescription =  Commons.NO_DESCRIPTION_AVAILABLE;
				}
				
				if(multipleTestingParameter.isBenjaminiHochbergFDR()){
					if (bhFDRAdjustedPValue <= FDR){											
						enrichedKeggPathways.add(keggPathwayName + "_" + keggPathwayDescription);
					}					
				}else if(multipleTestingParameter.isBonferroniCorrection()){					
					if (bonfCorrectedPValue <= bonfCorrectionSignificanceLevel){					
						enrichedKeggPathways.add(keggPathwayName + "_" + keggPathwayDescription);																
					}					
				}												
			}//end of while : reading enriched kegg Pathway file line by line.
			/******************************************************************************/
			/*******************Get the Enriched Elements ends***************************/

			
		
			/**********************************************************************************************/
			/*******************Augment each Enriched Element with Annotation Results starts****************/
			for(String enrichedKeggPathwayNameandDescription: enrichedKeggPathways){
				
				bufferedWriter.write("**************" + "\t" + enrichedKeggPathwayNameandDescription + "\t" + "**************" +  System.getProperty("line.separator"));
				
				indexofFirstUnderscore = enrichedKeggPathwayNameandDescription.indexOf('_');
				indexofSecondUnderscore = (indexofFirstUnderscore>0)? enrichedKeggPathwayNameandDescription.indexOf('_',indexofFirstUnderscore+1):-1;
				
				//KEGG PATHWAY
				if (type.equals(Commons.EXON_BASED_KEGG_PATHWAY)){
					keggPathwayName = enrichedKeggPathwayNameandDescription.substring(0,indexofFirstUnderscore);
					keggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.EXON_BASED_KEGG_PATHWAY_ANNOTATION + "_" + keggPathwayName + ".txt");						
					
				}else if (type.equals(Commons.REGULATION_BASED_KEGG_PATHWAY)){
					keggPathwayName = enrichedKeggPathwayNameandDescription.substring(0,indexofFirstUnderscore);
					keggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.REGULATION_BASED_KEGG_PATHWAY_ANNOTATION + "_" + keggPathwayName + ".txt");						
					
				}else if (type.equals(Commons.ALL_BASED_KEGG_PATHWAY)){
					keggPathwayName = enrichedKeggPathwayNameandDescription.substring(0,indexofFirstUnderscore);
					keggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.ALL_BASED_KEGG_PATHWAY_ANALYSIS + "_" + keggPathwayName + ".txt");						
				}
				
				//USERDEFINED GENESET
				else if (type.equals(Commons.EXON_BASED_USER_DEFINED_GENESET)){
					
					if (indexofSecondUnderscore>0){
						keggPathwayName = enrichedKeggPathwayNameandDescription.substring(0,indexofSecondUnderscore);
						keggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.USER_DEFINED_GENESET_ANNOTATION_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.EXONBASED_USERDEFINED_GENESET_ANNOTATION  + "_" + keggPathwayName + ".txt");						
					}else{
						keggPathwayName = enrichedKeggPathwayNameandDescription.substring(0,indexofFirstUnderscore);
						keggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.USER_DEFINED_GENESET_ANNOTATION_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.EXONBASED_USERDEFINED_GENESET_ANNOTATION  + "_" + keggPathwayName + ".txt");						
					}
					
				}else if (type.equals(Commons.REGULATION_BASED_USER_DEFINED_GENESET)){
					
					if(indexofSecondUnderscore>0){
						keggPathwayName = enrichedKeggPathwayNameandDescription.substring(0,indexofSecondUnderscore);
						keggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.USER_DEFINED_GENESET_ANNOTATION_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") +Commons.REGULATIONBASED_USERDEFINED_GENESET_ANNOTATION  +"_" + keggPathwayName + ".txt");						
	
					}else{
						keggPathwayName = enrichedKeggPathwayNameandDescription.substring(0,indexofFirstUnderscore);
						keggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.USER_DEFINED_GENESET_ANNOTATION_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") +Commons.REGULATIONBASED_USERDEFINED_GENESET_ANNOTATION  +"_" + keggPathwayName + ".txt");						
					}
				
				}else if (type.equals(Commons.ALL_BASED_USER_DEFINED_GENESET)){
					
					if(indexofSecondUnderscore>0){
						keggPathwayName = enrichedKeggPathwayNameandDescription.substring(0,indexofSecondUnderscore);
						keggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.USER_DEFINED_GENESET_ANNOTATION_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") +Commons.ALLBASED_USERDEFINED_GENESET_ANNOTATION  +"_" + keggPathwayName + ".txt");						

					}else{
						keggPathwayName = enrichedKeggPathwayNameandDescription.substring(0,indexofFirstUnderscore);
						keggPathwayOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.USER_DEFINED_GENESET_ANNOTATION_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") +Commons.ALLBASED_USERDEFINED_GENESET_ANNOTATION  +"_" + keggPathwayName + ".txt");						

					}
				}
				
				keggPathwayOriginalOverlapsBufferedReader = new BufferedReader(keggPathwayOriginalOverlapsFileReader);
				
										
				//Get all the lines of the original data annotation for the enriched Kegg Pathway Elements
				//Write them to the file
				while((strLine2 = keggPathwayOriginalOverlapsBufferedReader.readLine())!=null){
//					Searched for chr	interval Low	interval High	ucscRefSeqGene node ChromName	node Low	node High	node RefSeqGeneName	node IntervalName	node GeneHugoSymbol	node GeneEntrezId
//					chr1	11862777	11862777	CHROMOSOME1	11861456	11862936	NM_005957	INTRON	MTHFR	4524
//					Searched for chr	interval Low	interval High	ucscRefSeqGene node ChromName	node Low	node High	node RefSeqGeneName	node IntervalName	node GeneHugoSymbol	node GeneEntrezId
//					chr10	96495792	96495792	CHROMOSOME10	96422462	96512461	NM_000769	FIVE_D	CYP2C19	1557
//					chr10	96495792	96495792	CHROMOSOME10	96495019	96495946	NM_001128925	EXON	CYP2C18	1562
//					chr10	96495792	96495792	CHROMOSOME10	96495019	96495946	NM_000772	EXON	CYP2C18	1562

						
					
					//process strLine2
					if(strLine2.contains("Search")){
						bufferedWriter.write(enrichedKeggPathwayNameandDescription + "\t" +strLine2 + System.getProperty("line.separator"));
										
					}else{

						indexofFirstTab 	= strLine2.indexOf('\t');
						indexofSecondTab 	= strLine2.indexOf('\t', indexofFirstTab+1);
						indexofThirdTab 	= strLine2.indexOf('\t', indexofSecondTab+1);
						indexofFourthTab 	= strLine2.indexOf('\t', indexofThirdTab+1);
						indexofFifthTab 	= strLine2.indexOf('\t', indexofFourthTab+1);
						indexofSixthTab 	= strLine2.indexOf('\t', indexofFifthTab+1);
						
						givenIntervalChrName = strLine2.substring(0, indexofFirstTab);
						givenIntervalZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFirstTab+1, indexofSecondTab));
						givenIntervalZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofSecondTab+1, indexofThirdTab));
								
						overlapChrName = strLine2.substring(indexofThirdTab+1, indexofFourthTab);
						overlapZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFourthTab+1, indexofFifthTab));
						overlapZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofFifthTab+1, indexofSixthTab));
						
						rest = strLine2.substring(indexofSixthTab+1);
												
						givenIntervalOneBasedEnd 	= givenIntervalZeroBasedEnd+1;
						
						overlapOneBasedEnd = overlapZeroBasedEnd +1;
						
						bufferedWriter.write(enrichedKeggPathwayNameandDescription + "\t" + givenIntervalChrName + "\t" + givenIntervalZeroBasedStart + "\t"  + givenIntervalOneBasedEnd + "\t" + overlapChrName + "\t" + overlapZeroBasedStart + "\t" + overlapOneBasedEnd +"\t" +rest + System.getProperty("line.separator"));
						
					}
				}//End of while					
				
			}//End of for	
			/**********************************************************************************************/
			/*******************Augment each Enriched Element with Annotation Results ends****************/

				
			/***************************************************************************/
			/*******************Close BufferedReaders and Writers starts****************/
			bufferedReader.close();
			bufferedWriter.close();
			
			if (keggPathwayOriginalOverlapsBufferedReader!=null){
				keggPathwayOriginalOverlapsBufferedReader.close();
				
			}
			/***************************************************************************/
			/*******************Close BufferedReaders and Writers ends******************/
			
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void readTfAllFileAugmentWrite(String outputFolder,MultipleTestingType multipleTestingParameter,Float FDR, Float bonfCorrectionSignificanceLevel,String inputFileName, String outputFileName){
		String strLine1;
		String strLine2;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab; 
		int indexofNinethTab;
	
		String tfNameCellLineName;
		
		Float bonfCorrectedPValue; 
		Float bhFDRAdjustedPValue;
		
		FileReader inputFileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter outputFileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		FileReader tfOriginalOverlapsFileReader = null;
		BufferedReader tfOriginalOverlapsBufferedReader = null;
				
		List<String> enrichedTfElements = new ArrayList<String>();

		String givenIntervalChrName;		
		int givenIntervalZeroBasedStart;
		int givenIntervalZeroBasedEnd;		
		String overlapChrName;
		int overlapZeroBasedStart;
		int overlapZeroBasedEnd;
		String rest;
		
		int givenIntervalOneBasedEnd;		
	
		int overlapOneBasedEnd;
		
				
		try {
			inputFileReader = new FileReader(outputFolder + inputFileName);
			bufferedReader = new BufferedReader(inputFileReader);
			
			outputFileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			bufferedWriter = new BufferedWriter(outputFileWriter);

			//skip headerLine
			//Element	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 10 Permutations	Number of Permutations	Number of comparisons	empiricalPValue	BonfCorrPVlaue: numberOfComparisons 82	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05
			strLine1= bufferedReader.readLine();
			
			while ((strLine1= bufferedReader.readLine())!=null ){
				
				//old example line
				//H2AZ_K562	129	0	10	162	0.00E+00	0.00E+00	0.00E+00	TRUE

//				new example lines
//				Element Number	Element Name	OriginalNumberofOverlaps	NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in 5000 Permutations	Number of Permutations	Number of comparisons	empiricalPValue	BonfCorrPValue for 406 comparisons	BH FDR Adjusted P Value	Reject Null Hypothesis for an FDR of 0.05
//				4500380000	HDAC2SC6296_HEPG2	10	17	5000	406	3.40E-03	1.00E+00	1.03E-01	FALSE

	
				indexofFirstTab 	= strLine1.indexOf('\t');
				indexofSecondTab 	= strLine1.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab 	= strLine1.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab 	= strLine1.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab 	= strLine1.indexOf('\t',indexofFourthTab+1);
				indexofSixthTab 	= strLine1.indexOf('\t',indexofFifthTab+1);
				indexofSeventhTab	= strLine1.indexOf('\t',indexofSixthTab+1);
				indexofEigthTab 	= strLine1.indexOf('\t',indexofSeventhTab+1);
				indexofNinethTab	= strLine1.indexOf('\t',indexofEigthTab+1);
				
				tfNameCellLineName = strLine1.substring(indexofFirstTab+1, indexofSecondTab);
							
				//Pay attention to the order
				bonfCorrectedPValue= Float.parseFloat(strLine1.substring(indexofSeventhTab+1, indexofEigthTab));
				bhFDRAdjustedPValue = Float.parseFloat(strLine1.substring(indexofEigthTab+1, indexofNinethTab));
											
							
				if(multipleTestingParameter.isBenjaminiHochbergFDR()){				
					if (bhFDRAdjustedPValue <= FDR){					
						enrichedTfElements.add(tfNameCellLineName);	
					}					
				}else if(multipleTestingParameter.isBonferroniCorrection()){					
					if (bonfCorrectedPValue <= bonfCorrectionSignificanceLevel){					
						enrichedTfElements.add(tfNameCellLineName);	
					}					
				}					
			}//end of while : reading enriched dnase elements file line by line.
			
						
			//starts
			for(String tfElementName: enrichedTfElements){
				
				bufferedWriter.write("**************" + "\t" + tfElementName + "\t" + "**************" +  System.getProperty("line.separator"));
												
					tfOriginalOverlapsFileReader = new FileReader(outputFolder + Commons.TF_ANNOTATION_DIRECTORY  + tfElementName + ".txt");						
					tfOriginalOverlapsBufferedReader = new BufferedReader(tfOriginalOverlapsFileReader);
							
					//Get all the lines of the original data annotation for the enriched Tf elements
					//Write them to the file
					while((strLine2 = tfOriginalOverlapsBufferedReader.readLine())!=null){
						//process strLine2
						if(strLine2.contains("Search")){
							bufferedWriter.write(tfElementName + "\t" +strLine2 + System.getProperty("line.separator"));
							
						}else{
//							Searched for chr	interval Low	interval High	tfbs node Chrom Name	node Low	node High	node Tfbs Name	node CellLineName	node FileName
//							chr1	11862777	11862777	CHROMOSOME1	11862639	11862938	POL2	K562	spp.optimal.wgEncodeSydhTfbsK562Pol2Ifna30StdAlnRep0_VS_wgEncodeSydhTfbsK562InputIfna30StdAlnRep1.narrowPeak

							indexofFirstTab 	= strLine2.indexOf('\t');
							indexofSecondTab 	= strLine2.indexOf('\t', indexofFirstTab+1);
							indexofThirdTab 	= strLine2.indexOf('\t', indexofSecondTab+1);
							indexofFourthTab 	= strLine2.indexOf('\t', indexofThirdTab+1);
							indexofFifthTab 	= strLine2.indexOf('\t', indexofFourthTab+1);
							indexofSixthTab 	= strLine2.indexOf('\t', indexofFifthTab+1);
							
							givenIntervalChrName = strLine2.substring(0, indexofFirstTab);
							givenIntervalZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFirstTab+1, indexofSecondTab));
							givenIntervalZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofSecondTab+1, indexofThirdTab));
															
							overlapChrName = strLine2.substring(indexofThirdTab+1, indexofFourthTab);
							overlapZeroBasedStart = Integer.parseInt(strLine2.substring(indexofFourthTab+1, indexofFifthTab));
							overlapZeroBasedEnd = Integer.parseInt(strLine2.substring(indexofFifthTab+1, indexofSixthTab));
							
							rest = strLine2.substring(indexofSixthTab+1);
													
							givenIntervalOneBasedEnd 	= givenIntervalZeroBasedEnd+1;
							
							overlapOneBasedEnd = overlapZeroBasedEnd +1;
							
							bufferedWriter.write(tfElementName + "\t" + givenIntervalChrName + "\t" + givenIntervalZeroBasedStart + "\t"  + givenIntervalOneBasedEnd + "\t" + overlapChrName + "\t" + overlapZeroBasedStart + "\t" + overlapOneBasedEnd +"\t" +rest + System.getProperty("line.separator"));
							
						}
						
					}//End of while					
				
			}//End of for	
			//ends
						
			bufferedWriter.close();
			bufferedReader.close();
			
			if(tfOriginalOverlapsBufferedReader!=null){
				tfOriginalOverlapsBufferedReader.close();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	public static void readandWriteFiles(
			String outputFolder,
			String dataFolder,
			String jobName,
			MultipleTestingType multipleTestingParameter,
			Float FDR, 
			Float bonfCorrectionSignificanceLevel,
			EnrichmentType dnaseEnrichment, 
			EnrichmentType histoneEnrichment, 
			EnrichmentType tfEnrichment, 
			EnrichmentType userDefinedGeneSetEnrichmentType,
			String userDefinedGeneSetName,
			EnrichmentType userDefinedLibraryEnrichmentType,
			EnrichmentType keggPathwayEnrichment,
			EnrichmentType tfKeggPathwayEnrichment, 
			EnrichmentType tfCellLineKeggPathwayEnrichment){
		
		
		String withRespectToFileName = null;
		
		String userDefinedLibraryElementType;
		
		//set the file end String
		if (multipleTestingParameter.isBenjaminiHochbergFDR()){
			withRespectToFileName = Commons.ALL_WITH_RESPECT_TO_BH_FDR_ADJUSTED_P_VALUE;
		}else if (multipleTestingParameter.isBonferroniCorrection()){
			withRespectToFileName = Commons.ALL_WITH_RESPECT_TO_BONF_CORRECTED_P_VALUE;
		}
		
		
		 /******************************************************************************/
		 /*********************DNASE starts*********************************************/
		 if (dnaseEnrichment.isDnaseEnrichment()){
			 readDnaseAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_DNASE  + "_" +jobName +  withRespectToFileName, Commons.AUGMENTED_DNASE_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
		 }
		 /*********************DNASE ends***********************************************/
		 /******************************************************************************/
		 
		 
		 
		 /******************************************************************************/
		 /*********************HISTONE starts*******************************************/
		if (histoneEnrichment.isHistoneEnrichment()){
		 			 readHistoneAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_HISTONE + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_HISTONE_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
		}
		 /*********************HISTONE ends*********************************************/
		 /******************************************************************************/
		
		
		
		
		 
		 /******************************************************************************/
		 /*********************TF starts************************************************/
		 if (tfEnrichment.isTfEnrichment() && !(tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment())){
			 readTfAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);
		 }
		 /*********************TF ends**************************************************/
		 /******************************************************************************/

		 
		 
		 /******************************************************************************/
		 /*********************USER DEFINED GENESET starts******************************/
		 if (userDefinedGeneSetEnrichmentType.isUserDefinedGeneSetEnrichment()){
			 
			final String  TO_BE_COLLECTED_EXON_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_USERDEFINED_GENESET_COMMON + userDefinedGeneSetName  + System.getProperty("file.separator") + Commons.ALL_PERMUTAIONS_NUMBER_OF_OVERLAPS_FOR_EXONBASED_USERDEFINED_GENESET +"_" + userDefinedGeneSetName ;
			final String  TO_BE_COLLECTED_REGULATION_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_USERDEFINED_GENESET_COMMON + userDefinedGeneSetName  + System.getProperty("file.separator") + Commons.ALL_PERMUTAIONS_NUMBER_OF_OVERLAPS_FOR_REGULATIONBASED_USERDEFINED_GENESET +"_" + userDefinedGeneSetName ;
			final String  TO_BE_COLLECTED_ALL_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_USERDEFINED_GENESET_COMMON + userDefinedGeneSetName  + System.getProperty("file.separator") + Commons.ALL_PERMUTAIONS_NUMBER_OF_OVERLAPS_FOR_ALLBASED_USERDEFINED_GENESET +"_" + userDefinedGeneSetName ;
	
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, TO_BE_COLLECTED_EXON_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS + "_" +jobName + withRespectToFileName,Commons.AUGMENTED_EXON_BASED_USERDEFINED_GENESET_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.EXON_BASED_USER_DEFINED_GENESET,userDefinedGeneSetName);
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, TO_BE_COLLECTED_REGULATION_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS + "_" +jobName + withRespectToFileName,Commons.AUGMENTED_REGULATION_BASED_USERDEFINED_GENESET_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.REGULATION_BASED_USER_DEFINED_GENESET,userDefinedGeneSetName);
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, TO_BE_COLLECTED_ALL_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS + "_" +jobName + withRespectToFileName,Commons.AUGMENTED_ALL_BASED_USERDEFINED_GENESET_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.ALL_BASED_USER_DEFINED_GENESET,userDefinedGeneSetName);
		 }
		 /*********************USER DEFINED GENESET ends********************************/
		 /******************************************************************************/

		 
		 
		 /******************************************************************************/
		 /*********************USER DEFINED LIBRARY starts******************************/
		 if(userDefinedLibraryEnrichmentType.isUserDefinedLibraryEnrichment()){
			 TIntObjectMap<String> elementTypeNumber2ElementTypeMap = new TIntObjectHashMap<String>();
			 
			 UserDefinedLibraryUtility.fillNumber2NameMap(elementTypeNumber2ElementTypeMap,
					dataFolder,
					Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME,
					Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_NUMBER_2_NAME_OUTPUT_FILENAME);
			 
			 
			 for(TIntObjectIterator<String> it = elementTypeNumber2ElementTypeMap.iterator(); it.hasNext();){
				 it.advance();
				 
//				 userDefinedLibraryElementTypeNumber = it.key();
				 userDefinedLibraryElementType = it.value();
				 
				 readEnrichedUserDefinedLibraryElementsAugmentWrite(
						 outputFolder,
						 multipleTestingParameter,
						 FDR,
						 bonfCorrectionSignificanceLevel, 
						 userDefinedLibraryElementType,
						 Commons.TO_BE_COLLECTED_USER_DEFINED_LIBRARY_NUMBER_OF_OVERLAPS + userDefinedLibraryElementType + System.getProperty("file.separator") +"_" +jobName + withRespectToFileName,
						 Commons.AUGMENTED_USERDEFINED_LIBRARY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES + userDefinedLibraryElementType + Commons.AUGMENTED_USERDEFINED_LIBRARY_RESULTS_FILENAME);
					
			 }
			 	
		 }//End of if: UserDefinedLibrary
		 /*********************USER DEFINED LIBRARY ends********************************/
		 /******************************************************************************/

		 
		 
		 
		 
		 /******************************************************************************/
		 /*********************KEGG PATHWAY starts**************************************/
		 if (keggPathwayEnrichment.isKeggPathwayEnrichment() && !(tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment())){
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY + "_" +jobName  + withRespectToFileName, Commons.AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.EXON_BASED_KEGG_PATHWAY,null);
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.REGULATION_BASED_KEGG_PATHWAY,null);
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.ALL_BASED_KEGG_PATHWAY,null);
		 }
		 /*********************KEGG PATHWAY ends****************************************/
		 /******************************************************************************/

		 
		 
		 
		 
		 /******************************************************************************/
		 /*********************TF KEGGPATHWAY starts************************************/
		 if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() && !(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment())){	    	 
			 readTfAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);
			 
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY + "_" +jobName  + withRespectToFileName, Commons.AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.EXON_BASED_KEGG_PATHWAY,null);
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.REGULATION_BASED_KEGG_PATHWAY,null);
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.ALL_BASED_KEGG_PATHWAY,null);

				
			 readTfKeggPathwayAllAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_EXON_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_EXON_BASED_KEGG_PATHWAY);
	    	 readTfKeggPathwayAllAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY + "_" +jobName+ withRespectToFileName, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_REGULATION_BASED_KEGG_PATHWAY);
	    	 readTfKeggPathwayAllAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_ALL_BASED_KEGG_PATHWAY+ "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_ALL_BASED_KEGG_PATHWAY);			
	     }
		 /*********************TF KEGGPATHWAY ends**************************************/
		 /******************************************************************************/

		 
		 
		 
		 /******************************************************************************/
		 /*********************TF CELLLINE KEGGPATHWAY starts***************************/
	     if (tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment() && !(tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment())){
			 readTfAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);
			 
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY + "_" +jobName  + withRespectToFileName, Commons.AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.EXON_BASED_KEGG_PATHWAY,null);
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.REGULATION_BASED_KEGG_PATHWAY,null);
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.ALL_BASED_KEGG_PATHWAY,null);

			 readTfCellLineKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY + "_" +jobName+ withRespectToFileName, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
	    	 readTfCellLineKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY+ "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
	    	 readTfCellLineKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
	     }
		 /*********************TF CELLLINE KEGGPATHWAY ends*****************************/
		 /******************************************************************************/

	     
	     
	     /******************************************************************************/
	     /*********************************BOTH starts**********************************/
	     /*****************************TF KEGGPATHWAY starts****************************/
	     /************************** TF CELLLINE KEGGPATHWAY starts*********************/
		 if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() && tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
			 readTfAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);
			 
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_EXON_BASED_KEGG_PATHWAY + "_" +jobName  + withRespectToFileName, Commons.AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.EXON_BASED_KEGG_PATHWAY,null);
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_REGULATION_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.REGULATION_BASED_KEGG_PATHWAY,null);
			 readKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel, Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_ALL_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.ALL_BASED_KEGG_PATHWAY,null);
			 
			 readTfKeggPathwayAllAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_EXON_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_EXON_BASED_KEGG_PATHWAY);
	    	 readTfKeggPathwayAllAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_REGULATION_BASED_KEGG_PATHWAY + "_" +jobName+ withRespectToFileName, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_REGULATION_BASED_KEGG_PATHWAY);
	    	 readTfKeggPathwayAllAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_ALL_BASED_KEGG_PATHWAY+ "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_ALL_BASED_KEGG_PATHWAY);			


			 readTfCellLineKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY + "_" +jobName+ withRespectToFileName, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
	    	 readTfCellLineKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY+ "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
	    	 readTfCellLineKeggPathwayAllFileAugmentWrite(outputFolder,multipleTestingParameter,FDR,bonfCorrectionSignificanceLevel,Commons.ALL_PERMUTATIONS_NUMBER_OF_OVERLAPS_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY + "_" +jobName + withRespectToFileName, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
	     }
	     /************************** TF CELLLINE KEGGPATHWAY ends***********************/
	     /*****************************TF KEGGPATHWAY ends******************************/
	     /*********************************BOTH ends************************************/
	     /******************************************************************************/


	}

	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2]	--->	Input File Format	
	//			--->	default	Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE	
	//args[3]	--->	Annotation, overlap definition, number of bases, 
	//					default 1
	//args[4]	--->	Perform Enrichment parameter
	//			--->	default	Commons.DO_ENRICH
	//			--->			Commons.DO_NOT_ENRICH	
	//args[5]	--->	Generate Random Data Mode
	//			--->	default	Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	//			--->			Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT	
	//args[6]	--->	multiple testing parameter, enriched elements will be decided and sorted with respect to this parameter
	//			--->	default Commons.BENJAMINI_HOCHBERG_FDR
	//			--->			Commons.BONFERRONI_CORRECTION
	//args[7]	--->	Bonferroni Correction Significance Level, default 0.05
	//args[8]	--->	Bonferroni Correction Significance Criteria, default 0.05
	//args[9]	--->	Number of permutations, default 5000
	//args[10]	--->	Dnase Enrichment
	//			--->	default Commons.DO_NOT_DNASE_ENRICHMENT
	//			--->	Commons.DO_DNASE_ENRICHMENT
	//args[11]	--->	Histone Enrichment
	//			--->	default	Commons.DO_NOT_HISTONE_ENRICHMENT
	//			--->			Commons.DO_HISTONE_ENRICHMENT
	//args[12]	--->	Transcription Factor(TF) Enrichment 
	//			--->	default	Commons.DO_NOT_TF_ENRICHMENT
	//			--->			Commons.DO_TF_ENRICHMENT
	//args[13]	--->	KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_KEGGPATHWAY_ENRICHMENT
	//args[14]	--->	TF and KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	//args[15]	--->	TF and CellLine and KeggPathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	//args[16]	--->	RSAT parameter
	//			--->	default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//			--->			Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//args[17]	--->	job name example: ocd_gwas_snps 
	//args[18]	--->	writeGeneratedRandomDataMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	//			--->			Commons.WRITE_GENERATED_RANDOM_DATA
	//args[19]	--->	writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//args[20]	--->	writePermutationBasedAnnotationResultMode checkBox
	//			---> 	default	Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//args[21]  --->    number of permutations in each run. Default is 2000
	//args[22]  --->	UserDefinedGeneSet Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT
	//							Commons.DO_USER_DEFINED_GENESET_ENRICHMENT
	//args[23]	--->	UserDefinedGeneSet InputFile 
	//args[24]	--->	UserDefinedGeneSet GeneInformationType
	//					default Commons.GENE_ID
	//							Commons.GENE_SYMBOL
	//							Commons.RNA_NUCLEOTIDE_ACCESSION
	//args[25]	--->	UserDefinedGeneSet	Name
	//args[26]	--->	UserDefinedGeneSet 	Optional GeneSet Description InputFile
	//args[27]  --->	UserDefinedLibrary Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT
	//						 	Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT
	//args[28]  --->	UserDefinedLibrary InputFile
	//args[29] - args[args.length-1]  --->	Note that the selected cell lines are
	//					always inserted at the end of the args array because it's size
	//					is not fixed. So for not (until the next change on args array) the selected cell
	//					lines can be reached starting from 22th index up until (args.length-1)th index.
	//					If no cell line selected so the args.length-1 will be 22-1 = 21. So it will never
	//					give an out of boundry exception in a for loop with this approach.
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		
		//jobName starts
		String jobName = args[17].trim();
		if (jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		//jobName ends
							
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");
		String dataFolder = glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator");
		
		MultipleTestingType multipleTestingParameter = MultipleTestingType.convertStringtoEnum(args[6]);
		Float FDR = Float.parseFloat(args[8]);
		Float bonfCorrectionSignificanceLevel = Float.parseFloat(args[7]);

		EnrichmentType dnaseEnrichment 		= EnrichmentType.convertStringtoEnum(args[10]);
		EnrichmentType histoneEnrichment  	= EnrichmentType.convertStringtoEnum(args[11]);
		EnrichmentType tfEnrichment 		= EnrichmentType.convertStringtoEnum(args[12]);
		EnrichmentType keggPathwayEnrichment  			= EnrichmentType.convertStringtoEnum(args[13]);
		EnrichmentType tfKeggPathwayEnrichment 			= EnrichmentType.convertStringtoEnum(args[14]);
		EnrichmentType tfCellLineKeggPathwayEnrichment 	= EnrichmentType.convertStringtoEnum(args[15]);
		
		/*********************************************************************************/
		/**************************USER DEFINED GENESET***********************************/	
		//User Defined GeneSet Enrichment, DO or DO_NOT
		EnrichmentType userDefinedGeneSetEnrichmentType = EnrichmentType.convertStringtoEnum(args[22]);

//		String userDefinedGeneSetInputFile = args[23];
//		String userDefinedGeneSetInputFile = "G:\\DOKTORA_DATA\\GO\\GO_gene_associations_human_ref.txt";
		  
//		GeneInformationType geneInformationType = GeneInformationType.convertStringtoEnum(args[24]);
//		GeneInformationType geneInformationType = GeneInformationType.GENE_SYMBOL;
		
		String userDefinedGeneSetName = args[25];
//		String userDefinedGeneSetName = "GO";

//		String userDefinedGeneSetDescriptionOptionalInputFile =args[26];		
//		String userDefinedGeneSetDescriptionOptionalInputFile = "G:\\DOKTORA_DATA\\GO\\GO_terms_and_ids.txt";
		/**************************USER DEFINED GENESET***********************************/
		/*********************************************************************************/
		
	
		/*********************************************************************************/
		/**************************USER DEFINED LIBRARY***********************************/
		//User Defined Library Enrichment, DO or DO_NOT
		EnrichmentType userDefinedLibraryEnrichmentType = EnrichmentType.convertStringtoEnum(args[27]);
//		EnrichmentType userDefinedLibraryEnrichmentType = EnrichmentType.DO_USER_DEFINED_LIBRARY_ENRICHMENT;

//		String userDefinedLibraryInputFile = args[28];
//		String userDefinedLibraryInputFile = "C:\\Users\\burcakotlu\\GLANET\\UserDefinedLibraryInputFile.txt";		
		/**************************USER DEFINED LIBRARY***********************************/	
		/*********************************************************************************/
		   	
		//delete old files starts 
		FileOperations.deleteOldFiles(outputFolder + Commons.AUGMENTED_ENRICHED_ELEMENTS_WITH_GIVEN_INPUT_DATA_DIRECTORY);
		//delete old files ends
					
		AugmentationofEnrichmentWithAnnotation.readandWriteFiles(outputFolder,dataFolder,jobName,multipleTestingParameter,FDR, bonfCorrectionSignificanceLevel,dnaseEnrichment,histoneEnrichment,tfEnrichment,userDefinedGeneSetEnrichmentType,userDefinedGeneSetName,userDefinedLibraryEnrichmentType,keggPathwayEnrichment,tfKeggPathwayEnrichment,tfCellLineKeggPathwayEnrichment);
		

	}

}
//