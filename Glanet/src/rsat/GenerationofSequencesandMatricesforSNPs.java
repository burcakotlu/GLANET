/**
 * @author burcakotlu
 * @date Apr 2, 2014 
 * @time 5:01:25 PM
 */
package rsat;

import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jaxbxjctool.AugmentationofGivenIntervalwithRsIds;
import jaxbxjctool.AugmentationofGivenRsIdwithInformation;
import jaxbxjctool.PositionFrequency;
import jaxbxjctool.RsInformation;
import jaxbxjctool.SNP;
import jaxbxjctool.TfCellLineGivenInterval;
import jaxbxjctool.TfCellLineTfInterval;
import jaxbxjctool.TfKeggPathwayTfInterval;

import org.apache.log4j.Logger;

import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.CommandLineArguments;
import enumtypes.EnrichmentType;

/**
 * 
 */
public class GenerationofSequencesandMatricesforSNPs {
	
	final static Logger logger = Logger.getLogger(GenerationofSequencesandMatricesforSNPs.class);
	
	
	
	public static void constructLogoMatricesfromEncodeMotifs(String dataFolder,String encodeMotifsInputFileName,Map<String,String>  tfName2LogoMatrices){
		
		FileReader fileReader ;
		BufferedReader bufferedReader;
		String strLine;
					
		//Attention order is important!
		//Order is ACGT
		String tfName = null;
		
		
		try {
				fileReader = new FileReader(dataFolder +  encodeMotifsInputFileName);
				bufferedReader = new BufferedReader(fileReader);
				
				while((strLine = bufferedReader.readLine())!=null){
					
//					Encode-motif matrix
//					Order is ACGT					
//					>NFKB_disc1 NFKB1_GM19193_encode-Snyder_seq_hsa_IgG-rab_r1:MDscan#1#Intergenic
//					K 0.000000 0.000000 0.737500 0.262500
//					G 0.000000 0.000000 1.000000 0.000000
//					G 0.000000 0.000000 1.000000 0.000000
//					R 0.570833 0.000000 0.429167 0.000000
//					A 0.837500 0.158333 0.004167 0.000000
//					W 0.395833 0.000000 0.000000 0.604167
//					T 0.000000 0.004167 0.000000 0.995833
//					Y 0.000000 0.383333 0.000000 0.616667
//					C 0.000000 1.000000 0.000000 0.000000
//					C 0.000000 1.000000 0.000000 0.000000
					
					
					if (strLine.startsWith(">")){
						
						//start reading from first character, skip '>' character
						tfName = strLine.substring(1);
						
						if (tfName2LogoMatrices.get(tfName)==null){
							tfName2LogoMatrices.put(tfName, strLine+ System.getProperty("line.separator"));
							
						}else{
							tfName2LogoMatrices.put(tfName, tfName2LogoMatrices.get(tfName)+ strLine + System.getProperty("line.separator"));	
						}
							
					}//End of if
						
						
						
					else{
						tfName2LogoMatrices.put(tfName, tfName2LogoMatrices.get(tfName)+ strLine+ System.getProperty("line.separator"));
					}
						
				}//end of while
				

				
				bufferedReader.close();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public static void constructPfmMatricesfromEncodeMotifs(String dataFolder,String encodeMotifsInputFileName,Map<String,String> tfName2PfmMatrices){
		FileReader fileReader ;
		BufferedReader bufferedReader;
		String strLine;
		
		int indexofFirstBlank;
		int indexofSecondBlank;
		int indexofThirdBlank;
		int indexofFourthBlank;
		
		float _AFrequency;
		float _CFrequency;
		float _GFrequency;
		float _TFrequency;
		
		
		List<PositionFrequency> positionfrequencyList = new ArrayList<PositionFrequency>();;
		
		//Attention
		//Order is ACGT
		String ALine = "";
		String CLine = "";
		String GLine = "";
		String TLine = "";
		
		
		String tfName = null;
		String formerTfName = null;
		
		Iterator<PositionFrequency> iterator;
		
		try {
				fileReader = new FileReader(dataFolder + encodeMotifsInputFileName);
				bufferedReader = new BufferedReader(fileReader);
				
				while((strLine = bufferedReader.readLine())!=null){
					
//					Encode-motif matrix
//					Order is ACGT					
//					>NFKB_disc1 NFKB1_GM19193_encode-Snyder_seq_hsa_IgG-rab_r1:MDscan#1#Intergenic
//					K 0.000000 0.000000 0.737500 0.262500
//					G 0.000000 0.000000 1.000000 0.000000
//					G 0.000000 0.000000 1.000000 0.000000
//					R 0.570833 0.000000 0.429167 0.000000
//					A 0.837500 0.158333 0.004167 0.000000
//					W 0.395833 0.000000 0.000000 0.604167
//					T 0.000000 0.004167 0.000000 0.995833
//					Y 0.000000 0.383333 0.000000 0.616667
//					C 0.000000 1.000000 0.000000 0.000000
//					C 0.000000 1.000000 0.000000 0.000000
					
					
					if (strLine.startsWith(">")){
						
						
						//start reading from first character, skip '>' character
						tfName = strLine.substring(1);
							
						if(formerTfName!=null){
							//Write former positionfrequencyList to the output file starts
							//if it is full
							ALine ="A |\t";
							CLine ="C |\t";
							GLine ="G |\t";
							TLine ="T |\t";
									
							iterator = positionfrequencyList.iterator();
							
							while(iterator.hasNext()){
								PositionFrequency positionFrequency = (PositionFrequency) iterator.next();
								ALine = ALine + positionFrequency.get_AFrequency() + "\t";
								CLine = CLine + positionFrequency.get_CFrequency() + "\t";
								GLine = GLine + positionFrequency.get_GFrequency() + "\t";
								TLine = TLine + positionFrequency.get_TFrequency() + "\t";
							}
							
							
							//We must have the former tfName
							//We must have inserted the header line
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + ALine +System.getProperty("line.separator"));
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + CLine +System.getProperty("line.separator"));
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + GLine +System.getProperty("line.separator"));
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + TLine +System.getProperty("line.separator"));
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + "//"  +System.getProperty("line.separator"));
							//Write former full pfList to the output file ends
						}//End of if
						
						
						//if tfName is inserted for the first time
						if (tfName2PfmMatrices.get(tfName)==null){
							tfName2PfmMatrices.put(tfName, "; " + strLine.substring(1) + System.getProperty("line.separator"));
						}
						//else start appending the new coming matrix to the already existing matrices for this tfName
						else{
							tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "; " + strLine.substring(1) + System.getProperty("line.separator"));
						}
						
						//Initialize positionfrequencyList
						positionfrequencyList = new ArrayList<PositionFrequency>();
						
					}else{
						
						indexofFirstBlank 	= strLine.indexOf(' ');
						indexofSecondBlank 	= strLine.indexOf(' ',indexofFirstBlank+1);
						indexofThirdBlank 	= strLine.indexOf(' ',indexofSecondBlank+1);
						indexofFourthBlank 	= strLine.indexOf(' ',indexofThirdBlank+1);
						
						_AFrequency = Float.parseFloat(strLine.substring(indexofFirstBlank+1, indexofSecondBlank));
						_CFrequency = Float.parseFloat(strLine.substring(indexofSecondBlank+1, indexofThirdBlank));
						_GFrequency = Float.parseFloat(strLine.substring(indexofThirdBlank+1, indexofFourthBlank));
						_TFrequency	= Float.parseFloat(strLine.substring(indexofFourthBlank+1));
						
						PositionFrequency positionFrequency = new PositionFrequency(_AFrequency,_CFrequency,_GFrequency,_TFrequency);
						positionfrequencyList.add(positionFrequency);
						formerTfName = tfName;
					}
						
				}//end of while
				
				//Write the last positionFrequencyList starts
				//Order is ACGT
				ALine ="A |\t";
				CLine ="C |\t";
				GLine ="G |\t";
				TLine ="T |\t";
			
				iterator = positionfrequencyList.iterator();
				
				while(iterator.hasNext()){
					PositionFrequency positionFrequency = (PositionFrequency) iterator.next();
					ALine = ALine + positionFrequency.get_AFrequency() + "\t";
					CLine = CLine + positionFrequency.get_CFrequency() + "\t";
					GLine = GLine + positionFrequency.get_GFrequency() + "\t";
					TLine = TLine + positionFrequency.get_TFrequency() + "\t";
				}
				
				
				//We must use former tfName
				//We must have inserted the header line
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + ALine +System.getProperty("line.separator"));
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + CLine +System.getProperty("line.separator"));
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + GLine +System.getProperty("line.separator"));
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + TLine +System.getProperty("line.separator"));
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + "//"  +System.getProperty("line.separator"));
				//Write the last positionFrequencyList ends
				
				bufferedReader.close();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public static void fillFrequencyListUsingCountList(List<Float> frequencyList, List<Integer> countList,Integer totalCount){
		
		Iterator<Integer> iterator = countList.iterator();
		
		Integer count;
		Float frequency;
		
		
		while(iterator.hasNext()){
			
			count = iterator.next();
			frequency = (count*1.0f)/totalCount;
			
			frequencyList.add(frequency);
			
			
		}
		
		
	}
	
	
	//Fill CountList using CountLine
		public static void fillCountList(String countLine,List<Integer> countList){
			//example Count line
			//4	19	0	0	0	0		
			int indexofFormerTab =0;
			int indexofLatterTab =0;
			
			int count =0;
			
			indexofFormerTab = 0;
			indexofLatterTab = countLine.indexOf('\t');
			
			
			//Insert the first count
			if (indexofLatterTab>=0){
				count = Integer.parseInt(countLine.substring(indexofFormerTab, indexofLatterTab));
				countList.add(count);			
			}
			
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = countLine.indexOf('\t',indexofFormerTab+1);

			
			//Insert the rest of the counts
			while(indexofLatterTab>=0){
				
				count = Integer.parseInt(countLine.substring(indexofFormerTab+1, indexofLatterTab));
				
				countList.add(count);
				
				indexofFormerTab = indexofLatterTab;
				indexofLatterTab = countLine.indexOf('\t',indexofFormerTab+1);
				
			}
			
			//Insert the last count
			if(indexofFormerTab>=0){
				count = Integer.parseInt(countLine.substring(indexofFormerTab+1));
				countList.add(count);
				
			}
		
		}
		
		public static int getTotalCount(List<Integer> ACountList,List<Integer> CCountList,List<Integer> GCountList,List<Integer>TCountList){
			
			Iterator<Integer> iteratorA = ACountList.iterator();
			Integer countA;
			
			
			Iterator<Integer> iteratorC = CCountList.iterator();
			Integer countC;
			
			Iterator<Integer> iteratorG = GCountList.iterator();
			Integer countG;
			
			Iterator<Integer> iteratorT = TCountList.iterator();
			Integer countT;
		
			int totalCount = 0;;
			
			while(iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()  ){
				
				countA = iteratorA.next();
				countC = iteratorC.next();
				countG = iteratorG.next();
				countT = iteratorT.next();
				
				totalCount = countA + countC + countG + countT;
				return totalCount;
				
			
				
			}
			
			return totalCount;
			
			
		}
	
		public static void putPFM(String tfName,List<Float> AFrequencyList,List<Float> CFrequencyList,List<Float> GFrequencyList,List<Float> TFrequencyList,Map<String,String> tfName2PfmMatrices){
			
			//example matrix in tab format
			//		; NFKB_known3	NFKB_1	NF-kappaB_transfac_M00054														
			//		A |	0	0	0.025	0.675	0.525	0.2	0.025	0.05	0.075	0						
			//		C |	0	0	0	0	0.325	0.025	0.05	0.45	0.9	0.95						
			//		G |	1	1	0.975	0.325	0.025	0.075	0.05	0	0	0						
			//		T |	0	0	0	0	0.125	0.7	0.875	0.5	0.025	0.05						
			//		//			
			
			Iterator<Float> iteratorA = AFrequencyList.iterator();
			Iterator<Float> iteratorC = CFrequencyList.iterator();
			Iterator<Float> iteratorG = GFrequencyList.iterator();
			Iterator<Float> iteratorT = TFrequencyList.iterator();
			
			Float frequencyA;
			Float frequencyC;
			Float frequencyG;
			Float frequencyT;
			
			String strLineA = "";
			String strLineC = "";
			String strLineG = "";
			String strLineT = "";
			
			while(iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()){
				
				frequencyA = iteratorA.next();
				frequencyC = iteratorC.next();
				frequencyG = iteratorG.next();
				frequencyT = iteratorT.next();
				
				strLineA = strLineA  + "\t"  + frequencyA;
				strLineC = strLineC  + "\t"  + frequencyC;
				strLineG = strLineG  + "\t"  + frequencyG;
				strLineT = strLineT  + "\t"  + frequencyT;
				
			}//end of while

			
			//this tfName has no previous position frequency matrix inserted
			if(tfName2PfmMatrices.get(tfName)==null){
				tfName2PfmMatrices.put(tfName, "; " + tfName + System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "A|"+ strLineA+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "C|"+ strLineC+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "G|"+ strLineG+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "T|"+ strLineT+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "//"+  System.getProperty("line.separator"));
				
			}
			//this tfName already has position frequency matrices
			//append the new position frequency matrix
			else{
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "; " + tfName + System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "A|"+ strLineA+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "C|"+ strLineC+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "G|"+ strLineG+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "T|"+ strLineT+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "//"+  System.getProperty("line.separator"));
					
			}

			
			
			
		}
		
		public static void putLogoMatrix(String tfName,List<Float> AFrequencyList,List<Float> CFrequencyList,List<Float> GFrequencyList,List<Float> TFrequencyList,Map<String,String> tfName2LogoMatrices){
			
//			Example logo matrix
//			G 0.008511 0.004255 0.987234 0.000000		
//			A 0.902127 0.012766 0.038298 0.046809		
//			R 0.455319 0.072340 0.344681 0.127660		
//			W 0.251064 0.085106 0.085106 0.578724		
//			T 0.000000 0.046809 0.012766 0.940425		
//			G 0.000000 0.000000 1.000000 0.000000		
//			T 0.038298 0.021277 0.029787 0.910638		
//			A 0.944681 0.004255 0.051064 0.000000		
//			G 0.000000 0.000000 1.000000 0.000000		
//			T 0.000000 0.000000 0.012766 0.987234		

			Iterator<Float> iteratorA = AFrequencyList.iterator();
			Iterator<Float> iteratorC = CFrequencyList.iterator();
			Iterator<Float> iteratorG = GFrequencyList.iterator();
			Iterator<Float> iteratorT = TFrequencyList.iterator();
			
			Float frequencyA;
			Float frequencyC;
			Float frequencyG;
			Float frequencyT;
		
			String strLine = null;
			
			if (tfName2LogoMatrices.get(tfName) == null){
				tfName2LogoMatrices.put(tfName, tfName + System.getProperty("line.separator"));		
			}
			
			else{
				tfName2LogoMatrices.put(tfName,tfName2LogoMatrices.get(tfName)+ tfName + System.getProperty("line.separator"));			
			}
				
				
				
			while(iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()){
				
				frequencyA = iteratorA.next();
				frequencyC = iteratorC.next();
				frequencyG = iteratorG.next();
				frequencyT = iteratorT.next();
				
				strLine = "X" + "\t" + frequencyA + "\t" + frequencyC + "\t" + frequencyG + "\t" + frequencyT +System.getProperty("line.separator");
				tfName2LogoMatrices.put(tfName, tfName2LogoMatrices.get(tfName) + strLine);
				
				
			}//end of while

		
		}


	public static void constructPfmMatricesandLogoMatricesfromJasparCore(String dataFolder,String jasparCoreInputFileName,Map<String,String> tfName2PfmMatrices,Map<String,String>  tfName2LogoMatrices){
		//Attention
		//Order is ACGT
				
		FileReader fileReader ;
		BufferedReader bufferedReader;
		String strLine;
		
		
		String tfName = null;
		
		int whichLine = 0;
		
		final int headerLine= 0;
		final int ALine = 1;
		final int CLine = 2;
		final int GLine = 3;
		final int TLine = 4;
		
		List<Integer> ACountList = null;
		List<Float>	AFrequencyList = null;
		
		List<Integer> CCountList = null;
		List<Float>	CFrequencyList = null;
		
		List<Integer> GCountList = null;
		List<Float>	GFrequencyList = null;
	
		List<Integer> TCountList = null;
		List<Float>	TFrequencyList = null;
		
		int totalCount;
	
		try {
			fileReader = new FileReader(dataFolder + jasparCoreInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			
//			Example matrix from jaspar core pfm_all.txt
//			>MA0004.1 Arnt																													
//			4	19	0	0	0	0																								
//			16	0	20	0	0	0																								
//			0	1	0	20	0	20																								
//			0	0	0	0	20	0																								

			
			while((strLine = bufferedReader.readLine())!=null){
				if (strLine.startsWith(">")){
					tfName = strLine.substring(1);
					
					//Initialize array lists
					//for the new coming position count matrix and position frequency matrix
					ACountList = new ArrayList<Integer>();
					AFrequencyList = new ArrayList<Float>();
					
					CCountList = new ArrayList<Integer>();
					CFrequencyList = new ArrayList<Float>();
					
					GCountList = new ArrayList<Integer>();
					GFrequencyList = new ArrayList<Float>();
				
					TCountList = new ArrayList<Integer>();
					TFrequencyList = new ArrayList<Float>();
					
					whichLine = ALine;
					continue;
				}
				
				switch(whichLine){			
					case ALine:	{	
									fillCountList(strLine,ACountList);
									whichLine = CLine;
									break;
								}
											
					case CLine:	{
									fillCountList(strLine,CCountList);							
									whichLine = GLine;
									break;
								}
										
					case GLine: {		
									fillCountList(strLine,GCountList);					
									whichLine = TLine;
									break;
								}
						
					case TLine:{
									fillCountList(strLine,TCountList);		
									whichLine = headerLine;
									
									//Since count lists are available
									//Then compute frequency lists
									totalCount = getTotalCount(ACountList,CCountList,GCountList,TCountList);
									fillFrequencyListUsingCountList(AFrequencyList,ACountList,totalCount);
									fillFrequencyListUsingCountList(CFrequencyList,CCountList,totalCount);
									fillFrequencyListUsingCountList(GFrequencyList,GCountList,totalCount);
									fillFrequencyListUsingCountList(TFrequencyList,TCountList,totalCount);
									
									//Now put the new matrix to the hashmap in tab format
									putPFM(tfName,AFrequencyList,CFrequencyList,GFrequencyList,TFrequencyList,tfName2PfmMatrices);
									
									//Put the transpose of the matrix for logo generation
									putLogoMatrix(tfName,AFrequencyList,CFrequencyList,GFrequencyList,TFrequencyList,tfName2LogoMatrices);
//									writeLogoMatrix(tfName,AFrequencyList,CFrequencyList,GFrequencyList,TFrequencyList,bufferedWriter);
	
									break;
									
								}
										
				}//End of switch
							
			}//End of while
			
			bufferedReader.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static String getTfNamewithoutNumber(String tfName){
		
		int n = tfName.length();
		char c;
		int i;
		
		for (i = 0; i < n; i++) {
		    c = tfName.charAt(i);
		    if (Character.isDigit(c)){
		    	break;
		    }
		}
		
		return tfName.substring(0, i);
	}
	
	public static String getTfNameWithFirstNumberWithNextCharacter(String tfName){
		
		int n = tfName.length();
		char c;
		int i;
		
		for (i = 0; i < n; i++) {
		    c = tfName.charAt(i);
		    if (Character.isDigit(c)){
		    	if ((i+1)<n){
		    	 	return tfName.substring(0, i+2);
		  		  
		    	}else{
		    	 	return tfName.substring(0, i+1);
		  		  
		    	}
		     }
		}
		
	
		return tfName.substring(0, i);
	}
	
	public static String removeLastCharacter(String tfName){
		
		int n = tfName.length();
		
		if (n>=6){
			return tfName.substring(0, n-1);
			
		}else{
			return tfName;
			
		}
	}
	
	
	public static void createTfIntervalsFile(String outputFolder,String directoryBase,String snpDirectory,String fileName, List<String> snpBasedTfIntervalKeyList, Map<String,TfKeggPathwayTfInterval> tfKeggPathwayBasedTfIntervalMap){
		
		TfKeggPathwayTfInterval tfInterval;
		int tfIntervalStartOneBased;
		int tfIntervalEndOneBased;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
	
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + snpDirectory + System.getProperty("file.separator") + fileName + ".txt");	
			bufferedWriter = new BufferedWriter(fileWriter);
						
			for(String tfIntervalKey : snpBasedTfIntervalKeyList ){
	
				//get tfInterval
				tfInterval = tfKeggPathwayBasedTfIntervalMap.get(tfIntervalKey);
				
				tfIntervalStartOneBased = tfInterval.getStartOneBased();
				tfIntervalEndOneBased = tfInterval.getEndOneBased();
								
				bufferedWriter.write(tfInterval.getTfNameCellLineName() +  "\t" + "chr" + tfInterval.getChrNamewithoutPreceedingChr() + " \t" + tfIntervalStartOneBased + "\t" +tfIntervalEndOneBased + System.getProperty("line.separator"));				
						
			}//End of file for each tf interval overlapping with this snp
		
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//for tf
	public static void createTfIntervalsFile_forTf(String outputFolder,String directoryBase,String snpDirectory,String fileName, List<String> snpBasedTfIntervalKeyList, Map<String,TfCellLineTfInterval> tfCellLineBasedTfIntervalMap){
		
		TfCellLineTfInterval tfInterval;
		int tfIntervalStartOneBased;
		int tfIntervalEndOneBased;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
	
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + snpDirectory + System.getProperty("file.separator") + fileName + ".txt");	
			bufferedWriter = new BufferedWriter(fileWriter);
						
			for(String tfIntervalKey : snpBasedTfIntervalKeyList ){
	
				//get tfInterval
				tfInterval = tfCellLineBasedTfIntervalMap.get(tfIntervalKey);
				
				tfIntervalStartOneBased = tfInterval.getStartOneBased();
				tfIntervalEndOneBased = tfInterval.getEndOneBased();
								
				bufferedWriter.write(tfInterval.getTfNameCellLineName() +  "\t" + "chr" + tfInterval.getChrNamewithoutPreceedingChr() + " \t" + tfIntervalStartOneBased + "\t" +tfIntervalEndOneBased + System.getProperty("line.separator"));				
						
			}//End of file for each tf interval overlapping with this snp
		
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public static void createSequenceFile(String outputFolder,String directoryBase, String sequenceFileDirectory, String fileName,String sequence){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		int indexofLineSeparator;
		String firstLineofFastaFile;
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + sequenceFileDirectory + System.getProperty("file.separator") + fileName + ".txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			indexofLineSeparator = sequence.indexOf(System.getProperty("line.separator"));
			
			//fastaFile is sent
			if(indexofLineSeparator!=-1){
				firstLineofFastaFile = sequence.substring(0, indexofLineSeparator);
				
				bufferedWriter.write(firstLineofFastaFile + "\t" +fileName + System.getProperty("line.separator"));
				bufferedWriter.write(sequence.substring(indexofLineSeparator+1).trim());
			
			}
			//only sequence is sent
			//so add '>' character to make it fasta format
			else{
				bufferedWriter.write(">" + fileName + System.getProperty("line.separator"));
				bufferedWriter.write(sequence);
	
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getAlteredSequence(String precedingSNP,String allele,String followingSNP){
		
		if(!allele.equals(Commons.STRING_HYPHEN)){
			return precedingSNP + allele + followingSNP;
		}else{
			return precedingSNP + followingSNP;

		}
		
		
	}
	
public static String takeComplementforeachAllele(String allele){
		
		String complementedAllele = "";
		
		for(char nucleotide: allele.toCharArray()){
			switch(nucleotide) {
				case 'A':
				case 'a': 	complementedAllele = complementedAllele + "T";
							break;
							
				case 'C':
				case 'c': 	complementedAllele = complementedAllele + "G";
							break;
							
				case 'G':
				case 'g': 	complementedAllele = complementedAllele + "C";
							break;
							
				case 'T':
				case 't': 	complementedAllele = complementedAllele + "A";
							break;
							
				case '-': 	complementedAllele = complementedAllele + "-";
							break;
							
				default : return null;			
							
			}//End of switch
		}//End of for
		
		return complementedAllele;
	}

	public static String takeComplement(String alleles){
		int indexofFormerTab;
		int indexofLatterTab;
		
		String allele;
		String complementedAllele = null;
		String complementedAlleles =  "";
		
		indexofFormerTab = alleles.indexOf('\t');
		
		//get the first allele
		allele = alleles.substring(0,indexofFormerTab);
		
		//take the complement of this allele
		complementedAllele = takeComplementforeachAllele(allele);
		
		if(complementedAllele!=null){
			complementedAlleles = complementedAlleles + complementedAllele + "\t";
		}
		
		indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);

		while(indexofFormerTab!=-1 && indexofLatterTab!=-1){
			allele = alleles.substring(indexofFormerTab+1, indexofLatterTab);
			
			//take the complement of this allele
			complementedAllele = takeComplementforeachAllele(allele);
			
			if(complementedAllele!=null){
				complementedAlleles = complementedAlleles + complementedAllele + "\t";
			}
			
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);
		}
		
		//get the last allele
		allele = alleles.substring(indexofFormerTab+1);
		//take the complement of this allele
		complementedAllele = takeComplementforeachAllele(allele);
		
		if(complementedAllele!=null){
			complementedAlleles = complementedAlleles + complementedAllele;
		}
	
				
		
		
				
		return complementedAlleles;
		
	}
	
	public static List<String> findOtherObservedAllelesandGetAltereSequences(String snp, String alleles,String precedingSNP,String followingSNP){
		
		List<String> alteredSnpSequences;
		
		String complementedAlleles;
		
			
		//We must decide whether we can use alleles 
		//or we must use the complement of the alleles
		//if snp is equal to the one of these alleles then use alleles
		//else use the complement of alleles
		if (useAlleles(snp,alleles)){
			alteredSnpSequences = getAlteredSnpSequences(snp,alleles,precedingSNP,followingSNP);
		}else {
			complementedAlleles = takeComplement(alleles);
			alteredSnpSequences = getAlteredSnpSequences(snp,complementedAlleles,precedingSNP,followingSNP);	
		}
			
		
		return alteredSnpSequences;
	}
	

	
	public static List<String> getAlteredSNPSequences(String referenceSequence, List<String> observedAlleles,int oneBasedStartSnpPosition, int oneBasedEndSnpPosition){
		
		String precedingSNP;
		String followingSNP;
		String snp;
		
		
		List<String> alteredSnpSequences;
		List<String> allAlteredSnpSequences = new ArrayList<String>();
				
		//snpPosition is at Commons.SNP_POSITION; (one-based)
				
		//precedingSNP is 14 characters long
		precedingSNP = referenceSequence.substring(0, oneBasedStartSnpPosition-1);
		
		//followingSNP is 14 characters long
		followingSNP = referenceSequence.substring(oneBasedEndSnpPosition);
		
		//snp
		snp = referenceSequence.substring(oneBasedStartSnpPosition-1,oneBasedEndSnpPosition);
				
		//take each possible observed alleles
		//C\tT\t
		
		for(String alleles: observedAlleles){
			
			//Find the other alleles other than normal nucleotide
			alteredSnpSequences = findOtherObservedAllelesandGetAltereSequences(snp,alleles,precedingSNP,followingSNP);
			
			//check for whether every altered sequence in alteredSnpSequences exists in allAlteredSnpSequences
			//if allAlteredSnpSequences does not contain it 
			//then add it
			for(String alteredSequence:alteredSnpSequences){
				if (!allAlteredSnpSequences.contains(alteredSequence)){
					allAlteredSnpSequences.add(alteredSequence);
					
				}
			}
			
		}
	
		return allAlteredSnpSequences;
	}
	
	
	public static List<String>  getAlteredSnpSequences(String snp, String alleles,String precedingSNP,String followingSNP){
		
		int indexofFormerTab;
		int indexofLatterTab;
		
		String allele;
		String alteredSnpSequence;
		List<String> alteredSnpSequences = new ArrayList<String>();
		
		indexofFormerTab = alleles.indexOf('\t');
				
		//get the first allele
		allele = alleles.substring(0,indexofFormerTab);
		
		//check for this first allele
		if (!snp.equals(allele)){
			alteredSnpSequence = getAlteredSequence(precedingSNP,allele,followingSNP);
			
			if (alteredSnpSequence!=null && !(alteredSnpSequences.contains(alteredSnpSequence))){
				alteredSnpSequences.add(alteredSnpSequence);
			}
		}
		
		indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);

		while(indexofFormerTab!=-1 && indexofLatterTab!=-1){
			allele = alleles.substring(indexofFormerTab+1, indexofLatterTab);
			
			//check for this allele
			if (!snp.equals(allele)){
				alteredSnpSequence = getAlteredSequence(precedingSNP,allele,followingSNP);
				
				if (alteredSnpSequence!=null && !(alteredSnpSequences.contains(alteredSnpSequence))){
					alteredSnpSequences.add(alteredSnpSequence);
				}
			}
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);
		}
		
		//get the last allele
		allele = alleles.substring(indexofFormerTab+1);
		
		//check for this last allele
		if (!snp.equals(allele)){
			alteredSnpSequence = getAlteredSequence(precedingSNP,allele,followingSNP);
			
			if (alteredSnpSequence!=null && !(alteredSnpSequences.contains(alteredSnpSequence))){
				alteredSnpSequences.add(alteredSnpSequence);
			}
		}
		
		return alteredSnpSequences;
	}


	
	//if snp exists in any of tab delimited alleles useAlleles return true
	//else useAlleles return false
	public static boolean useAlleles(String snp,String alleles){
		//alleles is composed by allele each is seperated by tab
		//A\tC\tG\t
		
		int indexofFormerTab;
		int indexofLatterTab;
		
		String allele;
		
		indexofFormerTab = alleles.indexOf('\t');
		
		//get the first allele
		allele = alleles.substring(0,indexofFormerTab);
				
		//check for this first allele
		if (snp.equals(allele)){
			return true;
		}
		
		indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);

		while(indexofFormerTab!=-1 && indexofLatterTab!=-1){
			allele = alleles.substring(indexofFormerTab+1, indexofLatterTab);
			
			//check for this allele
			if (snp.equals(allele)){
				return true;
			}
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);
		}
		
		//get the last allele
		allele = alleles.substring(indexofFormerTab+1);
		
		//check for this last allele
		if (snp.equals(allele)){
			return true;
		}
		
				
		return false;
		
	}
	
	public static void createObservedAllelesFile(String outputFolder,String directoryBase, String observedAllelesFileDirectory, String fileName,List<String> observedAlleles){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + observedAllelesFileDirectory + System.getProperty("file.separator") + fileName + ".txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(String observedAllele: observedAlleles){
				bufferedWriter.write(observedAllele + System.getProperty("line.separator"));	
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createPeakSequencesFile(String outputFolder, String directoryBase,String sequenceFileDirectory, String fileName, String peakName, String peakSequence){

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		int indexofFirstLineSeparator;
		String firstLineofFastaFile;
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + sequenceFileDirectory + System.getProperty("file.separator") + fileName + ".txt",true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			indexofFirstLineSeparator = peakSequence.indexOf(System.getProperty("line.separator"));
			firstLineofFastaFile = peakSequence.substring(0,indexofFirstLineSeparator);
					
			bufferedWriter.write(firstLineofFastaFile + "\t" +peakName + System.getProperty("line.separator"));
			bufferedWriter.write(peakSequence.substring(indexofFirstLineSeparator+1).trim());
		
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void createMatrixFile(String outputFolder,String directoryBase, String tfNameCellLineNameorKeggPathwayName, String matrixName,String matrix){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + tfNameCellLineNameorKeggPathwayName + System.getProperty("file.separator") +matrixName + ".txt",true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(matrix);
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	
	//Requires chrName without preceeding "chr" string 
	//Requires oneBased coordinates
	public static String  getDNASequence(String chrNamewithoutPreceedingChr,int startOneBased, int endOneBased,Map<String,String> chrName2RefSeqIdforGrch38Map){
		
		String sourceHTML = null;
		String refSeqId;
		
		refSeqId = chrName2RefSeqIdforGrch38Map.get(chrNamewithoutPreceedingChr);
				
	  
	  //GlanetRunner.appendLog("EFETCH RESULT:");
	  // Read from the URL
	  try
	  { 
		  	String eFetchString="http://www.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nucleotide&id="+ refSeqId +"&strand=1" +  "&seq_start="+ startOneBased + "&seq_stop=" + endOneBased + "&rettype=fasta&retmode=text";
		  	URL url= new URL(eFetchString);
		
	   	 	BufferedReader in= new BufferedReader(new InputStreamReader(url.openStream()));
	        String inputLine;       // one line of the result, as it is read line by line
	        sourceHTML= "";  // will eventually contain the whole result
	        // Continue to read lines while there are still some left to read
	        
	        //Pay attention
	        //Each line including last line has new line character at the end.
	        while ((inputLine= in.readLine()) != null)  // read one line of the input stream
	            { sourceHTML+= inputLine + System.getProperty("line.separator");            // add this line to end of the whole shebang
	//	              ++lineCount;                              // count the number of lines read
	            }
	        
	        // Close the connection
	        in.close();
	  }catch (Exception e){ 
		  GlanetRunner.appendLog("Error reading from the URL:");
		  System.out.println(e);
	  }
	  
	
	  return sourceHTML;
	}

	
	//without orient starts
public static String convertSlashSeparatedAllelestoTabSeparatedAlleles(String observedAllelesSeparatedwithSlash){
		
		int indexofFormerSlash = observedAllelesSeparatedwithSlash.indexOf('/');
		int indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/',indexofFormerSlash +1);
		
		String allele;
		String observedAllelesSeparatedwithTabs = "";
		
		//for the first allele
		allele = observedAllelesSeparatedwithSlash.substring(0,indexofFormerSlash);
		
		//update
		observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele + "\t";		
	
		
				
		
		while (indexofFormerSlash>=0 && indexofLatterSlash >=0){
			
			allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash+1, indexofLatterSlash);	
			
			//update
			observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele + "\t";		
				
			indexofFormerSlash = indexofLatterSlash ;			
			indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/',indexofFormerSlash+1);
			
		}
		
		//for the last allele
		allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash+1);	
		
		//update
		observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele;		
		
			
		return observedAllelesSeparatedwithTabs;
			
	}
	//without orient ends
	
	
//	//With Orient starts
//	public static String convertSlashSeparatedAllelestoTabSeparatedAlleles(String observedAllelesSeparatedwithSlash, String orient){
//		
//		int indexofFormerSlash = observedAllelesSeparatedwithSlash.indexOf('/');
//		int indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/',indexofFormerSlash +1);
//		
//		String allele;
//		String complementedAllele;
//		String observedAllelesSeparatedwithTabs = "";
//		
//		//for the first allele
//		allele = observedAllelesSeparatedwithSlash.substring(0,indexofFormerSlash);
//		
//		if (orient.equals(Commons.REVERSE)){
//			complementedAllele = takeComplementforeachAllele(allele);			
//			//update
//			observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + complementedAllele + "\t";			
//		}else{
//			//update
//			observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele + "\t";		
//		}
//		
//		
//				
//		
//		while (indexofFormerSlash>=0 && indexofLatterSlash >=0){
//			
//			allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash+1, indexofLatterSlash);	
//			
//			if (orient.equals(Commons.REVERSE)){
//				complementedAllele = takeComplementforeachAllele(allele);				
//				//update
//				observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + complementedAllele + "\t";				
//			}else{
//				//update
//				observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele + "\t";		
//			}
//			
//			
//			indexofFormerSlash = indexofLatterSlash ;			
//			indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/',indexofFormerSlash+1);
//			
//		}
//		
//		//for the last allele
//		allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash+1);	
//		
//		if (orient.equals(Commons.REVERSE)){
//			complementedAllele = takeComplementforeachAllele(allele);				
//			//update
//			observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + complementedAllele;				
//		}else{
//			//update
//			observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele;		
//		}
//		
//		
//		return observedAllelesSeparatedwithTabs;
//			
//	}
//	//With Orient ends

	
	public static boolean alreadyExists(String observedAllelesSeparatedbyTabs,List<String> observedAlleles){
		
		Boolean exists = false;
		
		
		for(String allele:observedAlleles){
			if (observedAllelesSeparatedbyTabs.equals(allele)){
				exists = true;
				break;
			}
		}
		
		return exists;
		
	}
		
	
	public static void add(String chrNamewithoutChr,int zeroBasedCoordinate, String observedAllelesSeparatedbyTabs,Map<String, List<String>> chrNamewithoutChrandZeroBasedCoordinate2ObservedAlleles){
		String key = chrNamewithoutChr + "_"  + zeroBasedCoordinate;
		
		List<String> observedAlleles = chrNamewithoutChrandZeroBasedCoordinate2ObservedAlleles.get(key);
		
		if (observedAlleles==null){
			observedAlleles = new ArrayList<String>();			
			observedAlleles.add(observedAllelesSeparatedbyTabs);
			chrNamewithoutChrandZeroBasedCoordinate2ObservedAlleles.put(key, observedAlleles);
		} else {
			
			if (!observedAlleles.contains(observedAllelesSeparatedbyTabs)){
				observedAlleles.add(observedAllelesSeparatedbyTabs);			
			}
//			if (!alreadyExists(observedAllelesSeparatedbyTabs,observedAlleles)){
//				observedAlleles.add(observedAllelesSeparatedbyTabs);
//			}
		}
		
	}
	
	
//	public static String getDirectoryBase(String enrichmentType){
//		
//		String directoryBase = null;
//		
//			switch(enrichmentType){
//			
//				case Commons.TF:{
//					directoryBase = Commons.TF_RESULTS_DIRECTORY_BASE;	
//					break;
//				}
//			
//				case Commons.TF_EXON_BASED_KEGG_PATHWAY:{			
//					directoryBase = Commons.TF_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;	
//					break;
//				}
//				
//				case Commons.TF_REGULATION_BASED_KEGG_PATHWAY:{				
//					directoryBase = Commons.TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;			
//					break;
//				}
//					
//				case Commons.TF_ALL_BASED_KEGG_PATHWAY:{			
//					directoryBase = Commons.TF_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;			
//					break;
//				}	
//				
//				case Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY:{
//					directoryBase = Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;		
//					break;
//				}
//				
//				case Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY:{
//					directoryBase = Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;					
//					break;
//				}
//				
//				case Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY:{
//					directoryBase = Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;					
//					break;
//				}
//			
//			} // End of switch
//
//		return directoryBase;
//	}
	
	public static String getDNASequenceFromFastaFile(String fastaFile){
		String referenceSequence;
		int indexofFirstLineSeparator;
		
		
		indexofFirstLineSeparator = fastaFile.indexOf(System.getProperty("line.separator"));
		referenceSequence = fastaFile.substring(indexofFirstLineSeparator+1).trim();
		
		return referenceSequence;
		
		
		
	}
	
	
	public static void getSNPBasedPeakSequence(List<String> snpBasedTfIntervalKeyList,Map<String,TfKeggPathwayTfInterval> tfKeggPathwayBasedTfIntervalMap,String chrNamewithoutPreceedingChr,Map<String,String> chrName2RefSeqIdforGrch38Map,String outputFolder, String directoryBase,String tfNameKeggPathwayNameBased_SnpDirectory,String snpKeyString){
		
		//initialize  min and max tf intervals
		int minTfIntervalStartOneBased = Integer.MAX_VALUE;
		int maxTfIntervalEndOneBased = Integer.MIN_VALUE;
		
		int tfIntervalStartOneBased;
		int tfIntervalEndOneBased;
		
		String tfExtendedPeakSequence;
		TfKeggPathwayTfInterval tfInterval;
					
		for(String tfIntervalKey : snpBasedTfIntervalKeyList ){

			//get tfInterval
			tfInterval = tfKeggPathwayBasedTfIntervalMap.get(tfIntervalKey);
			
			tfIntervalStartOneBased = tfInterval.getStartOneBased();
			tfIntervalEndOneBased = tfInterval.getEndOneBased();
			
			if (tfIntervalStartOneBased < minTfIntervalStartOneBased){
				minTfIntervalStartOneBased = tfIntervalStartOneBased;
			}
			
			if (tfIntervalEndOneBased > maxTfIntervalEndOneBased){
				maxTfIntervalEndOneBased = tfIntervalEndOneBased;
			}		
					
		}//End of file for each tf interval overlapping with this snp
		
		
		tfExtendedPeakSequence = getDNASequence(chrNamewithoutPreceedingChr,minTfIntervalStartOneBased, maxTfIntervalEndOneBased,chrName2RefSeqIdforGrch38Map);
		
		//write snp based extended peak sequence file
		createPeakSequencesFile(outputFolder,directoryBase,tfNameKeggPathwayNameBased_SnpDirectory,"extendedPeakSequence","extendedPeak",tfExtendedPeakSequence);

	}
	
	
	//for tf
	public static void getSNPBasedPeakSequence_forTf(List<String> snpBasedTfIntervalKeyList,Map<String,TfCellLineTfInterval> tfCellLineBasedTfIntervalMap,String chrNamewithoutPreceedingChr,Map<String,String> chrName2RefSeqIdforGrch38Map,String outputFolder, String directoryBase,String tfNameCellLineNameBased_SnpDirectory,String snpKeyString){
		
		//initialize  min and max tf intervals
		int minTfIntervalStartOneBased = Integer.MAX_VALUE;
		int maxTfIntervalEndOneBased = Integer.MIN_VALUE;
		
		int tfIntervalStartOneBased;
		int tfIntervalEndOneBased;
		
		String tfExtendedPeakSequence;
		TfCellLineTfInterval tfInterval;
					
		for(String tfIntervalKey : snpBasedTfIntervalKeyList ){

			//get tfInterval
			tfInterval = tfCellLineBasedTfIntervalMap.get(tfIntervalKey);
			
			tfIntervalStartOneBased = tfInterval.getStartOneBased();
			tfIntervalEndOneBased = tfInterval.getEndOneBased();
			
			if (tfIntervalStartOneBased < minTfIntervalStartOneBased){
				minTfIntervalStartOneBased = tfIntervalStartOneBased;
			}
			
			if (tfIntervalEndOneBased > maxTfIntervalEndOneBased){
				maxTfIntervalEndOneBased = tfIntervalEndOneBased;
			}		
					
		}//End of file for each tf interval overlapping with this snp
		
		
		tfExtendedPeakSequence = getDNASequence(chrNamewithoutPreceedingChr,minTfIntervalStartOneBased, maxTfIntervalEndOneBased,chrName2RefSeqIdforGrch38Map);
		
		//write snp based extended peak sequence file
		createPeakSequencesFile(outputFolder,directoryBase,tfNameCellLineNameBased_SnpDirectory,"extendedPeakSequence","extendedPeak",tfExtendedPeakSequence);

	}
	
	public static List<String> findTfIntervalsOverlappingWithThisSNP(List<String> tfIntervalKeyList, Map<String,TfKeggPathwayTfInterval> tfIntervalMap,int snpZeroBasedStartCoordinate, int snpZeroBasedEndCoordinate){
		
		List<String> snpBasedTfIntervalKeyList = new ArrayList<String>();
		TfKeggPathwayTfInterval tfInterval;
		
		for(String tfIntervalKey: tfIntervalKeyList ){
			tfInterval = tfIntervalMap.get(tfIntervalKey);
					
			//Interval Tree works with 0 Based coordinates
			if (IntervalTree.overlaps((tfInterval.getStartOneBased()-1), (tfInterval.getEndOneBased()-1), snpZeroBasedStartCoordinate, snpZeroBasedEndCoordinate)){
				snpBasedTfIntervalKeyList.add(tfIntervalKey);
			}
		}
		
		return snpBasedTfIntervalKeyList;
		
	}
	
	//for tf
	public static List<String> findTfIntervalsOverlappingWithThisSNP_forTf(List<String> tfIntervalKeyList, Map<String,TfCellLineTfInterval> tfIntervalMap,int snpZeroBasedStartCoordinate, int snpZeroBasedEndCoordinate){
		
		List<String> snpBasedTfIntervalKeyList = new ArrayList<String>();
		TfCellLineTfInterval tfInterval;
		
		for(String tfIntervalKey: tfIntervalKeyList ){
			tfInterval = tfIntervalMap.get(tfIntervalKey);
						
			//IntervalTree works with 0Based coordinates
			if (IntervalTree.overlaps((tfInterval.getStartOneBased()-1), (tfInterval.getEndOneBased()-1), snpZeroBasedStartCoordinate, snpZeroBasedEndCoordinate)){
				snpBasedTfIntervalKeyList.add(tfIntervalKey);
			}
		}
		
		return snpBasedTfIntervalKeyList;
		
	}
	
	//TF starts
	public static void readAllTFAnnotationsWriteSequencesandMatrices(
			AugmentationofGivenIntervalwithRsIds augmentationOfAGivenIntervalWithRsIDs, 
			AugmentationofGivenRsIdwithInformation augmentationOfAGivenRsIdWithInformation,
			Map<String,String> chrName2RefSeqIdforGrch38Map, 
			String forRSAFolder,
			String all_TF_Annotations_File_1Based_Start_End_GRCh38, 
			Map<String,String> tfName2PfmMatrices, 
			Map<String,String> tfName2LogoMatrices,
			String enrichmentType){
		
		FileReader allTFAnnotationsFileReader;
		BufferedReader allTFAnnotationsBufferedReader;
				
		String strLine;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
			
		
			
		String chrNameWithPreceedingChr = null;
		String chrNameWithoutPreceedingChr = null;
						
		int snpOneBasedStart;
		int snpOneBasedEnd;

		int tfOneBasedStart;
		int tfOneBasedEnd;
						
		
		String tfName;
		String cellLineName;
		
		String fileName;
		
		String tfNameRemovedLastCharacter;
		String previousTfNameRemovedLastCharacter;
		
		boolean thereExistsPFMMatrix = false;
		boolean thereExistsLOGOMatrix = false;
		
		
		String fastaFile;
		String referenceSequence;
		String directoryBase = Commons.TF + System.getProperty("file.separator");
					
		Boolean isThereAnExactTfNamePfmMatrix = false;
		
		//4 April 2014
		String observedAllelesSeparatedwithSlash;
		
				
		/*******************************************************************************/
		/**************************TF2PFMLogoMatricesMAP starts*************************/
		/*******************************************************************************/
		//This map is used whether this pfm matrix file is already created and written for a certain TF.
		//Map key is TF 
		//If once pfm and logo matrices are found and written to text files
		//then there is no need to search and write pfm matrix files again
		Map<String,Boolean> tf2PFMMatriceAlreadyExistsTrueorFalseMap 	= new HashMap<String,Boolean>();
		/*******************************************************************************/
		/*************************TF2PFMLogoMatricesMAP ends****************************/
		/*******************************************************************************/
			
		
		/*******************************************************************************/
		/**************givenChrNameOneBasedStart 2 RsIDList Map starts******************/
		/*******************************************************************************/
		//This map contains the rsID list for a given snp position
		//Key is chrNameWithPreceedingChr + "_" + snpOneBasedStart
		String givenSNPKey;
		List<String> rsIdList;
		RsInformation rsInformation;
		Map<String,List<String>> givenSNP2RsIDListMap = new HashMap<String,List<String>>();
		/*******************************************************************************/
		/**************givenChrNameOneBasedStart 2 RsIDList Map ends********************/
		/*******************************************************************************/
		
		
		
		
		/*******************************************************************************/
		/**************givenChrNameOneBasedStart 2 TFOverlaps Map starts****************/
		/*******************************************************************************/
		//7 April 2014 starts		
		//Key must contain TFCellLine givenIntervalName (chrNumber startZeroBased endZeroBased)
		Map<String,Map<String,TFOverlap>> givenSNP2TFOverlapMapMap = new HashMap<String,Map<String,TFOverlap>>();
		Map<String,TFOverlap> tfName2TFOverlapMap;
		
		TFCellLineOverlap tfCellLineOverlap;
		TFOverlap tfOverlap;
		/*******************************************************************************/
		/**************givenChrNameOneBasedStart 2 TFOverlaps Map ends******************/
		/*******************************************************************************/
	
	

		/*******************************************************************************/
		/*********************rsID 2 rsInformation MAP starts***************************/
		/*******************************************************************************/
		Map<String,RsInformation> rsID2RsIDInformationMap =  new HashMap<String,RsInformation>();
		/*******************************************************************************/
		/*********************rsID 2 rsInformation MAP ends*****************************/
		/*******************************************************************************/
	
				
		
		
		
	
		//10 March 2014
		//Each observedAlleles String contains observed alleles which are separated by tabs, pay attention, there can be more than two observed alleles such as A\tG\tT\t-\tACG
		//Pay attention, for the same chrName and ChrPosition there can be more than one observedAlleles String. It is rare but possible.
		List<String> alteredSequences;
		String observedAllelesSeparatedwithTabs;
					
		try {
			allTFAnnotationsFileReader 	= new FileReader(forRSAFolder + all_TF_Annotations_File_1Based_Start_End_GRCh38);
			allTFAnnotationsBufferedReader 	= new BufferedReader(allTFAnnotationsFileReader);
			
			
			/****************************************************************************************/
			/*********************Reading Augmented TF File Starts***********************************/
			/****************************************************************************************/										
			while((strLine = allTFAnnotationsBufferedReader.readLine())!=null){
							
				//skip strLine starts with '#' comment character
				if (strLine.charAt(0) != Commons.GLANET_COMMENT_CHARACTER){
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= (indexofFirstTab>0)?strLine.indexOf('\t',indexofFirstTab+1):-1;
					indexofThirdTab 	= (indexofSecondTab>0)?strLine.indexOf('\t',indexofSecondTab+1):-1;
					indexofFourthTab 	= (indexofThirdTab>0)?strLine.indexOf('\t',indexofThirdTab+1):-1;
					indexofFifthTab 	= (indexofFourthTab>0)?strLine.indexOf('\t',indexofFourthTab+1):-1;
					indexofSixthTab 	= (indexofFifthTab>0)?strLine.indexOf('\t',indexofFifthTab+1):-1;
					indexofSeventhTab 	= (indexofSixthTab>0)?strLine.indexOf('\t',indexofSixthTab+1):-1;
					indexofEigthTab 	= (indexofSeventhTab>0)?strLine.indexOf('\t',indexofSeventhTab+1):-1;
					
					chrNameWithPreceedingChr =  strLine.substring(0, indexofFirstTab);
					chrNameWithoutPreceedingChr = chrNameWithPreceedingChr.substring(3);
					
					//Used in finding list of rsIds in this given GRCH38 coordinate
					snpOneBasedStart = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
					snpOneBasedEnd 	=  Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					
					tfOneBasedStart = Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab));
					tfOneBasedEnd =  Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
					
					tfName = strLine.substring(indexofSixthTab+1, indexofSeventhTab);
					cellLineName = strLine.substring(indexofSeventhTab+1, indexofEigthTab);
					fileName = strLine.substring(indexofEigthTab+1);
					
					
					//Initialize tfNameRemovedLastCharacter to tfName
					tfNameRemovedLastCharacter = tfName;
					
					/*************************************************************************/
					/**********Create Files for pfm Matrices and logo Matrices starts*********/
					/*************************************************************************/
					if(tf2PFMMatriceAlreadyExistsTrueorFalseMap.get(tfName) == null){
												
						isThereAnExactTfNamePfmMatrix = false;
						
						//Find PFM entry							
						for(Map.Entry<String, String> pfmEntry:tfName2PfmMatrices.entrySet()){
							if (pfmEntry.getKey().contains(tfName)){
								isThereAnExactTfNamePfmMatrix = true;
								createMatrixFile(forRSAFolder,directoryBase, tfName,  "pfmMatrices_" + tfName,pfmEntry.getValue());
									
							}
						}//End of for
						
						
						//Find LOGO entry
						for(Map.Entry<String, String> logoEntry:tfName2LogoMatrices.entrySet()){
							if(logoEntry.getKey().contains(tfName)){
								createMatrixFile(forRSAFolder,directoryBase, tfName, "logoMatrices_" +tfName,logoEntry.getValue());

							}
						}

						
						if (!isThereAnExactTfNamePfmMatrix){
							
							thereExistsPFMMatrix= false;
							thereExistsLOGOMatrix = false;
							
							while (!thereExistsPFMMatrix && !thereExistsLOGOMatrix){
								previousTfNameRemovedLastCharacter = tfNameRemovedLastCharacter;
								//@todo removeLastCharacter may need further check
								tfNameRemovedLastCharacter = removeLastCharacter(tfNameRemovedLastCharacter);
								
								//If no  change
								if(previousTfNameRemovedLastCharacter.equals(tfNameRemovedLastCharacter)){
									break;
								}
								
								//find pfm entry							
								for(Map.Entry<String, String> pfmEntry:tfName2PfmMatrices.entrySet()){
									if (pfmEntry.getKey().contains(tfNameRemovedLastCharacter)){
										thereExistsPFMMatrix = true;
										createMatrixFile(forRSAFolder,directoryBase, tfName, "pfmMatrices_" + tfName,pfmEntry.getValue());									
												
									}
								}//End of for PFM
								
								//find logo entry
								for(Map.Entry<String, String> logoEntry:tfName2LogoMatrices.entrySet()){
									if(logoEntry.getKey().contains(tfNameRemovedLastCharacter)){
										thereExistsLOGOMatrix= true;
										createMatrixFile(forRSAFolder,directoryBase, tfName, "logoMatrices_" +tfName,logoEntry.getValue());
	
									}
								}//End of for LOGO
								
							}//End of while
							
														
						}//End of IF there is no exact TF NAME PFM Matrix Match
						
						tf2PFMMatriceAlreadyExistsTrueorFalseMap.put(tfName, true);
					} //End of if
					/*************************************************************************/
					/**********Create Files for pfm Matrices and logo Matrices ends***********/
					/*************************************************************************/

					
					/*************************************************************************/
					/*************************SET KEYS starts*********************************/
					/*************************************************************************/
					//Set Given ChrName OneBasedStart
					givenSNPKey = Commons.SNP + "_" + chrNameWithPreceedingChr + "_" + snpOneBasedStart;
					/*************************************************************************/
					/*************************SET KEYS ends***********************************/
					/*************************************************************************/
					
					
					/*************************************************************************/
					/*****************Fill givenSNP2RsIDListMap starts************************/
					/*************************************************************************/
					rsIdList = givenSNP2RsIDListMap.get(givenSNPKey);
					
					if(rsIdList == null){
					    
					    
					    //Get all the rsIDs in this given interval				
					    //We have to provide 1-based coordinates as arguments
					    rsIdList = augmentationOfAGivenIntervalWithRsIDs.getRsIdsInAGivenInterval(chrNameWithoutPreceedingChr,snpOneBasedStart,snpOneBasedEnd);
					    
					    
					    /*************************************************************************/
					    /***************Fill rsID2RsIDInformationMap starts***********************/
					    /*************************************************************************/    
					    for (String rsId: rsIdList){
						
						 rsInformation = rsID2RsIDInformationMap.get(rsId);
						 
						 if (rsInformation==null){
						     //For each rsId get rsInformation
						     rsInformation = augmentationOfAGivenRsIdWithInformation.getInformationforGivenRsId(rsId);  
						     rsID2RsIDInformationMap.put(rsId, rsInformation);
						 }
						   
					    }
					    /*************************************************************************/
					    /***************Fill rsID2RsIDInformationMap ends*************************/
					    /*************************************************************************/    
					    
					    
					    /*************************************************************************/
					    /***********Fill givenSNP2TFOverlapMaptMap starts*************************/
					    /*************************************************************************/
					    tfName2TFOverlapMap =givenSNP2TFOverlapMapMap.get(givenSNPKey);
					    
					    if (tfName2TFOverlapMap == null){
						
						tfName2TFOverlapMap = new HashMap<String,TFOverlap>();
						
						tfOverlap = tfName2TFOverlapMap.get(tfName);
						
						if (tfOverlap == null){
						    
						    tfOverlap = new TFOverlap(tfName);
						    
						    tfCellLineOverlap = new TFCellLineOverlap(tfName,cellLineName,fileName, tfOneBasedStart, tfOneBasedEnd);
						    
						    //@todo getTfCellLineOverlaps returns null
						    tfOverlap.getTfCellLineOverlaps().add(tfCellLineOverlap);
						    
						    tfName2TFOverlapMap.put(tfName, tfOverlap);
						}
						
						
						givenSNP2TFOverlapMapMap.put(givenSNPKey, tfName2TFOverlapMap);
					    }else{
						tfOverlap = tfName2TFOverlapMap.get(tfName);
						
						if (tfOverlap == null){
						    tfOverlap = new TFOverlap(tfName);
						    
						    tfCellLineOverlap = new TFCellLineOverlap(tfName,cellLineName,fileName, tfOneBasedStart, tfOneBasedEnd);
							
						    tfOverlap.getTfCellLineOverlaps().add(tfCellLineOverlap);
						    
						    tfName2TFOverlapMap.put(tfName, tfOverlap);
						}else{
						    tfCellLineOverlap = new TFCellLineOverlap(tfName,cellLineName,fileName, tfOneBasedStart, tfOneBasedEnd);
							
						    tfOverlap.getTfCellLineOverlaps().add(tfCellLineOverlap);
						    
						}
							
						    
					    }
					    /*************************************************************************/
					    /***********Fill givenSNP2TFOverlapMaptMap ends***************************/
					    /*************************************************************************/
						
						
					    
					    givenSNP2RsIDListMap.put(givenSNPKey,rsIdList);
					}//End of if rsIDList is null
					else{
					    
					    /*************************************************************************/
					    /***********Fill givenSNP2TFOverlapMaptMap starts*************************/
					    /*************************************************************************/
					    tfName2TFOverlapMap =givenSNP2TFOverlapMapMap.get(givenSNPKey);
					    
					    if (tfName2TFOverlapMap == null){
						
						tfName2TFOverlapMap = new HashMap<String,TFOverlap>();
						
						tfOverlap = tfName2TFOverlapMap.get(tfName);
						
						if (tfOverlap == null){
						    
						    tfOverlap = new TFOverlap(tfName);
						    
						    tfCellLineOverlap = new TFCellLineOverlap(tfName,cellLineName,fileName, tfOneBasedStart, tfOneBasedEnd);
							
						    tfOverlap.getTfCellLineOverlaps().add(tfCellLineOverlap);
						    
						    tfName2TFOverlapMap.put(tfName, tfOverlap);
						}
						
						
						givenSNP2TFOverlapMapMap.put(givenSNPKey, tfName2TFOverlapMap);
					    }else{
						tfOverlap = tfName2TFOverlapMap.get(tfName);
						
						if (tfOverlap == null){
						    tfOverlap = new TFOverlap(tfName);
						    
						    tfCellLineOverlap = new TFCellLineOverlap(tfName,cellLineName,fileName, tfOneBasedStart, tfOneBasedEnd);
							
						    tfOverlap.getTfCellLineOverlaps().add(tfCellLineOverlap);
						    
						    tfName2TFOverlapMap.put(tfName, tfOverlap);
						}else{
						    tfCellLineOverlap = new TFCellLineOverlap(tfName,cellLineName,fileName, tfOneBasedStart, tfOneBasedEnd);
							
						    tfOverlap.getTfCellLineOverlaps().add(tfCellLineOverlap);
						    
						}
							
						    
					    }
					    /*************************************************************************/
					    /***********Fill givenSNP2TFOverlapMaptMap ends***************************/
					    /*************************************************************************/
					}
					/*************************************************************************/
					/*****************Fill givenSNP2RsIDListMap ends**************************/
					/*************************************************************************/
					
					
				}//End of IF: not comment character
				
			}//End of While loop reading:  allTFAnnotationsBufferedReader
					
				
			//Close BufferedReader
			allTFAnnotationsBufferedReader.close();
			
			
						
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	//end ends
		
	
	
		
	
	
	//Pay Attention
	//Contains X for chrX
	//Contains 1 for chr1
	
	//# Sequence-Name	Sequence-Role	Assigned-Molecule	Assigned-Molecule-Location/Type	GenBank-Accn	Relationship	RefSeq-Accn	Assembly-Unit
	//1	assembled-molecule	1	Chromosome	CM000663.1	=	NC_000001.10	Primary Assembly
	//X	assembled-molecule	X	Chromosome	CM000685.1	=	NC_000023.10	Primary Assembly
	public static void fillMap(String dataFolder, String refSeqIdsforGRChXXInputFile,Map<String,String> chrName2RefSeqIdforGrchXXMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		int numberofChromosomesinHomoSapiens = 24;
		int count = 0;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
		String chrName;
		String refSeqId;
		
		try {
			fileReader = new FileReader(dataFolder + refSeqIdsforGRChXXInputFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				if(strLine.startsWith("#")){
					continue;
				}else{
					if (count<numberofChromosomesinHomoSapiens){
						count++;
						
						indexofFirstTab 	= strLine.indexOf('\t');
						indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
						indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
						indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);
						indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
						indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
						indexofSeventhTab 	= strLine.indexOf('\t', indexofSixthTab+1);
						
						chrName = strLine.substring(0, indexofFirstTab);
						refSeqId = strLine.substring(indexofSixthTab+1, indexofSeventhTab);
						
						chrName2RefSeqIdforGrchXXMap.put(chrName, refSeqId);
						continue;
						
					}
				}
					
				break;				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Pay Attention
	//Contains X for chrX
	//Contains 1 for chr1	
	//# Sequence-Name	Sequence-Role	Assigned-Molecule	Assigned-Molecule-Location/Type	GenBank-Accn	Relationship	RefSeq-Accn	Assembly-Unit
	//1	assembled-molecule	1	Chromosome	CM000663.1	=	NC_000001.10	Primary Assembly
	//X	assembled-molecule	X	Chromosome	CM000685.1	=	NC_000023.10	Primary Assembly
	public Map<String,String> fillMap(String refSeqIdsforGRCh37InputFile){
		
		Map<String,String> chrName2RefSeqIdforGrch37Map = new HashMap<String,String>();
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		int numberofChromosomesinHomoSapiens = 24;
		int count = 0;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
		String chrName;
		String refSeqId;
		
		try {
			fileReader = new FileReader(refSeqIdsforGRCh37InputFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				if(strLine.startsWith("#")){
					continue;
				}else{
					if (count<numberofChromosomesinHomoSapiens){
						count++;
						
						indexofFirstTab 	= strLine.indexOf('\t');
						indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
						indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
						indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);
						indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
						indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
						indexofSeventhTab 	= strLine.indexOf('\t', indexofSixthTab+1);
						
						chrName = strLine.substring(0, indexofFirstTab);
						refSeqId = strLine.substring(indexofSixthTab+1, indexofSeventhTab);
						
						chrName2RefSeqIdforGrch37Map.put(chrName, refSeqId);
						continue;
						
					}
				}
					
				break;
				
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return chrName2RefSeqIdforGrch37Map;
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
		
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		
		//jobName starts
		String jobName = args[CommandLineArguments.JobName.value()].trim();
		if (jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		//jobName ends
				
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");
		
		String forRSAFolder =	outputFolder + Commons.FOR_RSA + System.getProperty("file.separator");
				
		//TfEnrichment, DO or DO_NOT
		EnrichmentType tfEnrichment = EnrichmentType.convertStringtoEnum(args[CommandLineArguments.TfAnnotation.value()]);
			

		//pfm matrices
		String encodeMotifsInputFileName 	= Commons.ENCODE_MOTIFS ;		
		String jasparCoreInputFileName 		= Commons.JASPAR_CORE;
		
		
		//TF
		String all_TF_Annotations_File_1Based_Start_End_GRCh38 = Commons.ALL_TF_ANNOTATIONS_FILE_1BASED_START_END_GRCH38;
	
	
		//Example Data
		//7 NC_000007.13  GRCh37
//		Chromosome 7	CM000669.2	=	NC_000007.14	0 GRCh37
		Map<String,String> chrName2RefSeqIdforGrch38Map = new HashMap<String,String>();
		
				
		//@todo We have to update this file regularly
		//Construct map for refSeq Ids of homo sapiens chromosomes for GRCh37
		String refSeqIdsforGRCh38InputFile = Commons.REFSEQ_IDS_FOR_GRCH38_INPUT_FILE;
		fillMap(dataFolder,refSeqIdsforGRCh38InputFile,chrName2RefSeqIdforGrch38Map);
							
									
		//Construct pfm matrices from encode-motif.txt file
		//A tf can have more than one pfm matrices
		//Take the transpose of given matrices in encode-motif.txt
		//Write the matrices in tab format for RSAT tool
		Map<String,String> tfName2PfmMatrices = new HashMap<String,String>();
		
		Map<String,String> tfName2LogoMatrices = new HashMap<String,String>();
		
			
		//Construct position frequency matrices from Encode Motifs
		constructPfmMatricesfromEncodeMotifs(dataFolder,encodeMotifsInputFileName,tfName2PfmMatrices);
		
		//Construct logo matrices from Encode Motifs
		constructLogoMatricesfromEncodeMotifs(dataFolder,encodeMotifsInputFileName,tfName2LogoMatrices);
		
		//Construct position frequency matrices from Jaspar Core 
		//Construct logo matrices from Jaspar Core
		constructPfmMatricesandLogoMatricesfromJasparCore(dataFolder,jasparCoreInputFileName,tfName2PfmMatrices,tfName2LogoMatrices);
		
		
		AugmentationofGivenIntervalwithRsIds augmentationOfAGivenIntervalWithRsIDs;
		AugmentationofGivenRsIdwithInformation augmentationOfAGivenRsIdWithInformation ;
		
		try {
			augmentationOfAGivenIntervalWithRsIDs = new AugmentationofGivenIntervalwithRsIds();
			augmentationOfAGivenRsIdWithInformation = new AugmentationofGivenRsIdwithInformation();
			
			//TF Annotations are used
			if (tfEnrichment.isTfEnrichment()){
				
			    //TF
			    //generate sequences and matrices for enriched tf elements
			    readAllTFAnnotationsWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,forRSAFolder,all_TF_Annotations_File_1Based_Start_End_GRCh38,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF);
			}
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	



}
