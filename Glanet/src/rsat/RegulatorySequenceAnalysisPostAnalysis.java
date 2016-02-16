/**
 * 
 */
package rsat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import auxiliary.FileOperations;
import common.Commons;
import enumtypes.CommandLineArguments;

/**
 * @author Burçak Otlu
 * @date Feb 15, 2016
 * @project Glanet 
 *
 */
public class RegulatorySequenceAnalysisPostAnalysis {
	
	 public static float getPValue(String strLine){
		 
		 float pValue = 0;
		 
		 int indexofFirstTab =-1;
		 int indexofSecondTab =-1;
		 int indexofThirdTab =-1;
		 int indexofFourthTab =-1;
		 int indexofFifthTab =-1;
		 int indexofSixthTab =-1;
		 int indexofSeventhTab =-1;
		 int indexofEigthTab =-1;
		 int indexofNinethTab =-1;
		 
		 indexofFirstTab = strLine.indexOf('\t');
		 indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
		 indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
		 indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
		 indexofFifthTab = strLine.indexOf('\t', indexofFourthTab+1);
		 indexofSixthTab = strLine.indexOf('\t', indexofFifthTab+1);
		 indexofSeventhTab = strLine.indexOf('\t', indexofSixthTab+1);
		 indexofEigthTab = strLine.indexOf('\t', indexofSeventhTab+1);
		 indexofNinethTab = strLine.indexOf('\t', indexofEigthTab+1);
		 
		 if (indexofNinethTab>0){
			 
			 pValue = Float.parseFloat(strLine.substring(indexofEigthTab+1, indexofNinethTab));
			 
		 }else{
			 pValue = Float.parseFloat(strLine.substring(indexofEigthTab+1));
		 }
		 
		 
		 return pValue;
		 
		 
	 }
	
	
	public static void readFileAndWriteImportantFindings(String regulatorySequenceAnalysisFolder,String RSAFileName){
		
		FileReader RSAFileReader = null;
		BufferedReader RSABufferedReader = null;
		
		String strLine = null;
		String informationLine = null;
		
		Float snpReferenceSequenceHavingSNPPositionPValue = null;
		Float savedSnpReferenceSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
		
		Float snpAlteredSequenceHavingSNPPositionPValue = null;
		Float savedSnpAlteredSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
		
		Float tfExtendedSequencePValue = null;
		
		int numberofInterestingFindings = 0;
		
		
		try {
			
			RSAFileReader = FileOperations.createFileReader(regulatorySequenceAnalysisFolder + RSAFileName);
			RSABufferedReader = new BufferedReader(RSAFileReader);
			
			while((strLine = RSABufferedReader.readLine())!=null){
				
				//Get information line, initialize and skip
				if (strLine.startsWith(Commons.GLANET_COMMENT_STRING) && !strLine.startsWith("#SequenceType")){
					
					
					//Means that a new finding starts
					informationLine = strLine;
					
					//Initialize
					snpReferenceSequenceHavingSNPPositionPValue = null;
					savedSnpReferenceSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
					
					snpAlteredSequenceHavingSNPPositionPValue = null;
					savedSnpAlteredSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
					
					tfExtendedSequencePValue = null;
					
					continue;
						
				}
				
				//Skip comment line
				if (strLine.startsWith(Commons.GLANET_COMMENT_STRING)){
					continue;
				}
				
				//We are interested in the  line that contains "SNPReferenceSequence" and  "Containing SNP Position"
				//There can be more than one such lines
				if (strLine.contains(Commons.SNP_REFERENCE_SEQUENCE) && strLine.contains("Containing SNP Position")){
					//Get snpReferenceSequenceHavingSNPPositionPValue;
					snpReferenceSequenceHavingSNPPositionPValue = getPValue(strLine);
					
//					//debug starts
//					if (savedSnpReferenceSequenceHavingSNPPositionPValue< Float.MAX_VALUE){
//						System.out.println("stop here ref");
//					}
//					//debug ends
					
					if(snpReferenceSequenceHavingSNPPositionPValue < savedSnpReferenceSequenceHavingSNPPositionPValue){
						savedSnpReferenceSequenceHavingSNPPositionPValue = snpReferenceSequenceHavingSNPPositionPValue;
					}
					continue;
					
				}
				
				
				//We are interested in the  line that contains "SNPAlteredSequence" and  "Containing SNP Position"
				//There can be more than one such lines
				if (strLine.contains(Commons.SNP_ALTERED_SEQUENCE) && strLine.contains("Containing SNP Position")){
					//Get snpReferenceSequenceHavingSNPPositionPValue;
					snpAlteredSequenceHavingSNPPositionPValue = getPValue(strLine);
					
//					//debug starts
//					if (savedSnpAlteredSequenceHavingSNPPositionPValue< Float.MAX_VALUE){
//						System.out.println("stop here altered");
//					}
//					//debug ends
					
					if (snpAlteredSequenceHavingSNPPositionPValue < savedSnpAlteredSequenceHavingSNPPositionPValue){
						savedSnpAlteredSequenceHavingSNPPositionPValue = snpAlteredSequenceHavingSNPPositionPValue;
					}
					continue;
					
				}
				
			
				//We are interested in the first line that contains "TFExtendedPeakSequence" and does not contains "Containing SNP Position"
				if (strLine.contains(Commons.TF_EXTENDED_PEAK_SEQUENCE) && !strLine.contains("Containing SNP Position")){
					//Get tfExtendedSequencePValue;
					tfExtendedSequencePValue = getPValue(strLine);
						
				}
				
				
				//We have three not null pValues and ...
				if (snpReferenceSequenceHavingSNPPositionPValue!= null && 
						snpAlteredSequenceHavingSNPPositionPValue!=null &&
								tfExtendedSequencePValue!=null &&
						(savedSnpReferenceSequenceHavingSNPPositionPValue < tfExtendedSequencePValue ||
						 savedSnpAlteredSequenceHavingSNPPositionPValue < tfExtendedSequencePValue)){
					
					System.out.println(informationLine);
					System.out.println(++numberofInterestingFindings +  ": P Values for SNPRefSequence: " + savedSnpReferenceSequenceHavingSNPPositionPValue + "\t" + "SNPAlteredSequence: " +  savedSnpAlteredSequenceHavingSNPPositionPValue + "\t" + "TFExtendedSequence: " + tfExtendedSequencePValue);
					
				
				}
				
				//For debugging1
				if (snpReferenceSequenceHavingSNPPositionPValue!= null && 
						snpAlteredSequenceHavingSNPPositionPValue!=null &&
								tfExtendedSequencePValue!=null &&
						(savedSnpReferenceSequenceHavingSNPPositionPValue < tfExtendedSequencePValue)){
					
					System.out.println("Is it possible? I guess no");
				
				}
				
				
				//For debugging2
				if (snpReferenceSequenceHavingSNPPositionPValue!= null && 
						snpAlteredSequenceHavingSNPPositionPValue!=null &&
								tfExtendedSequencePValue!=null &&
						(savedSnpAlteredSequenceHavingSNPPositionPValue < tfExtendedSequencePValue)  &&
						(savedSnpReferenceSequenceHavingSNPPositionPValue>0.05f)){
				
					System.out.println("Is it such data?");
				
				}
				
				
			}//End of WHILE
			
			//Close
			RSABufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public static void main(String[] args) {

		//Read RegulatorySequenceAnalysisResults.txt
		//Under C:\Users\Burçak\Google Drive\Output\OCD_RSA\RegulatorySequenceAnalysis
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];

		// jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();

		if( jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		// jobName ends
		
		String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty( "file.separator") + jobName + System.getProperty( "file.separator");
		String regulatorySequenceAnalysisFolder = outputFolder + Commons.REGULATORY_SEQUENCE_ANALYSIS + System.getProperty( "file.separator");

		String RSAFileName = "RegulatorySequenceAnalysisResults.txt";
		
		readFileAndWriteImportantFindings(regulatorySequenceAnalysisFolder,RSAFileName);

		
	}

}
