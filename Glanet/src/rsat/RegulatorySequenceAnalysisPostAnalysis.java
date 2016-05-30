/**
 * 
 */
package rsat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import auxiliary.FileOperations;
import auxiliary.GlanetDecimalFormat;

import common.Commons;

import enumtypes.CommandLineArguments;

/**
 * @author Burcak Otlu
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
	
	
	public static void readFileAndWriteImportantFindings(
			String regulatorySequenceAnalysisFolder,
			String RSAFileName,
			String RSAPostAnalysisFileName){
		
		FileReader RSAFileReader = null;
		BufferedReader RSABufferedReader = null;
		
		FileWriter RSAPostAnalysisFileWriter = null;
		BufferedWriter RSAPostAnalysisBufferedWriter = null;
		
		String strLine = null;
		String informationLine = null;
		
		Float snpReferenceSequenceHavingSNPPositionPValue = null;
		Float savedSnpReferenceSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
		
		Float snpAlteredSequenceHavingSNPPositionPValue = null;
		Float savedSnpAlteredSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
		
		Float tfExtendedSequencePValue = null;
		
		int numberofInterestingFindings = 0;
		
		DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat( "0.######E0");
		
		try {
			
			RSAFileReader = FileOperations.createFileReader(regulatorySequenceAnalysisFolder + RSAFileName);
			RSABufferedReader = new BufferedReader(RSAFileReader);
			
			RSAPostAnalysisFileWriter = FileOperations.createFileWriter(regulatorySequenceAnalysisFolder + RSAPostAnalysisFileName);
			RSAPostAnalysisBufferedWriter = new BufferedWriter(RSAPostAnalysisFileWriter);
			
			//Write Header Line
			RSAPostAnalysisBufferedWriter.write("Interesting Finding Number" +  "\t" +  "Chr_Position_rsID_TF" + "\t" + "SNP Effect" + "\t" + "p_Ref" + "\t" + "p_SNP" + "\t" + "p_TFExtended"  + System.getProperty("line.separator"));				

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
				
				
				//We have three not null pValues Case3
				//p_SNP is less than p_reference and p_extended
				//Case1
				//SNP has a better match.
				if (snpReferenceSequenceHavingSNPPositionPValue!= null && 
						snpAlteredSequenceHavingSNPPositionPValue!=null &&
								tfExtendedSequencePValue!=null &&
						(	savedSnpAlteredSequenceHavingSNPPositionPValue < tfExtendedSequencePValue &&
							savedSnpAlteredSequenceHavingSNPPositionPValue < savedSnpReferenceSequenceHavingSNPPositionPValue )){
					
					
					RSAPostAnalysisBufferedWriter.write(++numberofInterestingFindings + "\t");
					informationLine = informationLine.replace("*", "");
					informationLine = informationLine.replace("#", "");
					RSAPostAnalysisBufferedWriter.write(informationLine);
					RSAPostAnalysisBufferedWriter.write( "\t"  + " SNP has a better match.\t" + df.format(savedSnpReferenceSequenceHavingSNPPositionPValue) + "\t" +  df.format(savedSnpAlteredSequenceHavingSNPPositionPValue) + "\t" + df.format(tfExtendedSequencePValue) + System.getProperty("line.separator"));
					
					//Initialize
					snpReferenceSequenceHavingSNPPositionPValue = null;
					savedSnpReferenceSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
					
					snpAlteredSequenceHavingSNPPositionPValue = null;
					savedSnpAlteredSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
					
					tfExtendedSequencePValue = null;
				}
				
				//We have three not null pValues Case4
				//Case2
				//SNP has a worse match.
				if (snpReferenceSequenceHavingSNPPositionPValue!= null && 
						snpAlteredSequenceHavingSNPPositionPValue!=null &&
								tfExtendedSequencePValue!=null &&
						(	savedSnpReferenceSequenceHavingSNPPositionPValue < savedSnpAlteredSequenceHavingSNPPositionPValue &&
							savedSnpReferenceSequenceHavingSNPPositionPValue.equals(tfExtendedSequencePValue))){
					
					RSAPostAnalysisBufferedWriter.write(++numberofInterestingFindings + "\t");
					informationLine = informationLine.replace("*", "");
					informationLine = informationLine.replace("#", "");
					RSAPostAnalysisBufferedWriter.write(informationLine);
					RSAPostAnalysisBufferedWriter.write("\t" + " SNP has a worse match (disrupting effect).\t" + df.format(savedSnpReferenceSequenceHavingSNPPositionPValue) + "\t" +  df.format(savedSnpAlteredSequenceHavingSNPPositionPValue) + "\t" + df.format(tfExtendedSequencePValue) + System.getProperty("line.separator"));
					
					//Initialize
					snpReferenceSequenceHavingSNPPositionPValue = null;
					savedSnpReferenceSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
					
					snpAlteredSequenceHavingSNPPositionPValue = null;
					savedSnpAlteredSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
					
					tfExtendedSequencePValue = null;
				
				}
				
				//For debugging1
				if (snpReferenceSequenceHavingSNPPositionPValue!= null && 
						snpAlteredSequenceHavingSNPPositionPValue!=null &&
								tfExtendedSequencePValue!=null &&
						(savedSnpReferenceSequenceHavingSNPPositionPValue < tfExtendedSequencePValue)){
					
					RSAPostAnalysisBufferedWriter.write(++numberofInterestingFindings + "\t");
					informationLine = informationLine.replace("*", "");
					informationLine = informationLine.replace("#", "");
					RSAPostAnalysisBufferedWriter.write(informationLine);
					RSAPostAnalysisBufferedWriter.write("\t" + "Can p_reference less than p_extended ? I guess no. Just for debugging." + System.getProperty("line.separator"));
					
					//Initialize
					snpReferenceSequenceHavingSNPPositionPValue = null;
					savedSnpReferenceSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
					
					snpAlteredSequenceHavingSNPPositionPValue = null;
					savedSnpAlteredSequenceHavingSNPPositionPValue = Float.MAX_VALUE;
					
					tfExtendedSequencePValue = null;
				
				}
								
				
			}//End of WHILE
			
			//Close
			RSABufferedReader.close();
			RSAPostAnalysisBufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public static void main(String[] args) {

		//Read RegulatorySequenceAnalysisResults.txt
		//Under C:\Users\Burcak\Google Drive\Output\OCD_RSA\RegulatorySequenceAnalysis
				
		// jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();

		if( jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		// jobName ends
		
		//When run from GUI, outputFolder has jobName already concatenated.
		String outputFolder = args[CommandLineArguments.OutputFolder.value()];
		String regulatorySequenceAnalysisFolder = outputFolder + Commons.REGULATORY_SEQUENCE_ANALYSIS + System.getProperty( "file.separator");

		String RSAFileName = "RegulatorySequenceAnalysisResults.txt";
		String RSAPostAnalysisFileName = "PostAnalysisofRegulatorySequenceAnalysisResults.txt";
		
		readFileAndWriteImportantFindings(regulatorySequenceAnalysisFolder,RSAFileName,RSAPostAnalysisFileName);

	}

}
