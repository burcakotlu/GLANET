/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTopPercentageType;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.hash.TObjectFloatHashMap;

/**
 * @author Burçak Otlu
 * @date Aug 16, 2015
 * @project Glanet 
 *
 */
public class DataDrivenExperimentCommon {
	
	public static void getTPMValues(
			String glanetFolder,
			DataDrivenExperimentCellLineType cellLineType,
			DataDrivenExperimentGeneType geneType,
			TObjectFloatMap<DataDrivenExperimentTopPercentageType> tpmValueMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String tpmValuesFileName = glanetFolder +  Commons.DDE + System.getProperty("file.separator") + Commons.DDE_TPM_VALUES + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + ".txt";
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
//		Top1PercentageTPM	Top2PercentageTPM	Top5PercentageTPM	Top10PercentageTPM	Top25PercentageTPM	Top50PercentageTPM	Top55PercentageTPM	Top60PercentageTPM
//		0E0	0E0	0E0	0E0	0E0	0E0	0E0	2E-2

		Float top2TPMValue;
		Float top10TPMValue;
		Float top25TPMValue;
		Float top60TPMValue;
		
		try {
			
			fileReader = FileOperations.createFileReader(tpmValuesFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//Skip header line
			strLine = bufferedReader.readLine();
			
			//Get the tpmValues from the second line
			strLine = bufferedReader.readLine();
				
			indexofFirstTab = strLine.indexOf("\t");
			indexofSecondTab = (indexofFirstTab>0) ? strLine.indexOf("\t",indexofFirstTab+1) : -1;
			indexofThirdTab = (indexofSecondTab>0) ? strLine.indexOf("\t",indexofSecondTab+1) : -1;
			indexofFourthTab = (indexofThirdTab>0) ? strLine.indexOf("\t",indexofThirdTab+1) : -1;
			indexofFifthTab = (indexofFourthTab>0) ? strLine.indexOf("\t",indexofFourthTab+1) : -1;
			indexofSixthTab = (indexofFifthTab>0) ? strLine.indexOf("\t",indexofFifthTab+1) : -1;
			indexofSeventhTab = (indexofSixthTab>0) ? strLine.indexOf("\t",indexofSixthTab+1) : -1;
			
			//top1TPMValue = Float.parseFloat(strLine.substring(0, indexofFirstTab));
			top2TPMValue = Float.parseFloat(strLine.substring(indexofFirstTab+1, indexofSecondTab));
			//top5TPMValue = Float.parseFloat(strLine.substring(indexofSecondTab+1, indexofThirdTab));
			top10TPMValue = Float.parseFloat(strLine.substring(indexofThirdTab+1, indexofFourthTab));
			top25TPMValue = Float.parseFloat(strLine.substring(indexofFourthTab+1, indexofFifthTab));
			//top50TPMValue = Float.parseFloat(strLine.substring(indexofFifthTab+1, indexofSixthTab));
			//top55TPMValue = Float.parseFloat(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
			top60TPMValue = Float.parseFloat(strLine.substring(indexofSeventhTab+1));
				
			switch(geneType){
			
				case EXPRESSING_PROTEINCODING_GENES:
					//TOP2 
					//TOP10 
					//TOP25
					tpmValueMap.put(DataDrivenExperimentTopPercentageType.TOP2PERCENTAGE, top2TPMValue);
					tpmValueMap.put(DataDrivenExperimentTopPercentageType.TOP10PERCENTAGE, top10TPMValue);
					tpmValueMap.put(DataDrivenExperimentTopPercentageType.TOP25PERCENTAGE, top25TPMValue);
					break;
					
				case NONEXPRESSING_PROTEINCODING_GENES:
					//TOP2
					//TOP60
					tpmValueMap.put(DataDrivenExperimentTopPercentageType.TOP2PERCENTAGE, top2TPMValue);
					tpmValueMap.put(DataDrivenExperimentTopPercentageType.TOP60PERCENTAGE, top60TPMValue);
					break;
			
			}//End of SWITCH
			
			//close
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static TObjectFloatMap<String> fillMapUsingGTFFile(
			String gtfFileNameWithPath,
			TObjectFloatMap<String> ensemblGeneId2TPMExistingMap,
			DataDrivenExperimentGeneType geneType) {

		String strLine = null;
		String ensemblGeneID = null;

		float TPM;
		float existingTPM;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;

		int numberofUpdates = 0;
		int numberofExistingGenes = 0;
		int numberofNonExistingGenes = 0;

		TObjectFloatMap<String> ensemblGeneID2TPMUnionMap = new TObjectFloatHashMap<String>();

		try{
			FileReader fileReader = FileOperations.createFileReader( gtfFileNameWithPath);
			BufferedReader bufferedReader = new BufferedReader( fileReader);

			// Skip header line
			// gene_id transcript_id(s) length effective_length expected_count TPM FPKM
			strLine = bufferedReader.readLine();

			// sample line from file
			// ENSG00000000003.10 ENST00000373020.4,ENST00000494424.1,ENST00000496771.1 2206.00 2052.31 5.00 0.05 0.04

			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
				indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
				indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
				indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
				indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;

				ensemblGeneID = strLine.substring( 0, indexofFirstTab);
				TPM = Float.parseFloat( strLine.substring( indexofFifthTab + 1, indexofSixthTab));

				// This ensemblGeneID already exists
				if( ensemblGeneId2TPMExistingMap.containsKey(ensemblGeneID)){

					existingTPM = ensemblGeneId2TPMExistingMap.get(ensemblGeneID);
					
					switch(geneType){
						
						case NONEXPRESSING_PROTEINCODING_GENES:{
							
							if( TPM > existingTPM){
								ensemblGeneID2TPMUnionMap.put( ensemblGeneID, TPM);
								numberofUpdates++;
							}else{
								ensemblGeneID2TPMUnionMap.put( ensemblGeneID, existingTPM);
							}
							
							break;
						}
						
						case EXPRESSING_PROTEINCODING_GENES: {
							
							if( TPM < existingTPM){
								ensemblGeneID2TPMUnionMap.put( ensemblGeneID, TPM);
								numberofUpdates++;
							}else{
								ensemblGeneID2TPMUnionMap.put( ensemblGeneID, existingTPM);
							}

							break;
						}
						
						default: 
							break;
					
					}//End of SWITCH geneType

					numberofExistingGenes++;

				}
				// This ensemblGeneID is seen for the first time
				else{

					ensemblGeneID2TPMUnionMap.put(ensemblGeneID, TPM);
					numberofNonExistingGenes++;

				}

			}// End of While

			// Close BufferedReader
			bufferedReader.close();

			System.out.println( "Number of entries in ensemblGeneID2TPMUnionMap for " + gtfFileNameWithPath + " is " + ensemblGeneID2TPMUnionMap.size());
			System.out.println( "Number of Updates is: " + numberofUpdates);
			System.out.println( "Number of existing genes is: " + numberofExistingGenes);
			System.out.println( "Number of non existing genes is: " + numberofNonExistingGenes);

		}catch( IOException e){
			e.printStackTrace();
		}

		return ensemblGeneID2TPMUnionMap;

	}
	
	public static float getPercentile(
			List<Map.Entry<String, Float>> list,
			DataDrivenExperimentGeneType geneType,
			float tpm,
			BufferedWriter bufferedWriter){
		
		int index = 0;
		int savedIndex= 0;
		float distance = Float.MAX_VALUE;
		
		//Dummy initialization
		float percentile  = -1;
		
		for(Iterator<Map.Entry<String, Float>> itr = list.iterator(); itr.hasNext();){
			
			Map.Entry<String, Float> entry = itr.next();
			
			if (Math.abs(tpm - entry.getValue())<=distance){
				distance = Math.abs(tpm -entry.getValue());
				savedIndex = index;
			}
			
			index++;
			
		}//End of for traversing the list 
		
		try {
			bufferedWriter.write("savedIndex is " + "\t" + savedIndex + System.getProperty("line.separator"));
			percentile = ((float)savedIndex/list.size())*100;
			bufferedWriter.write("TPM Value at percentile is " + "\t" + list.get(savedIndex).getValue() + System.getProperty("line.separator"));
			bufferedWriter.write("TPM Value at percentile+1 is " + "\t" + list.get(savedIndex+1).getValue() + System.getProperty("line.separator"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return percentile;
	}
	
	
	public static List<Map.Entry<String, Float>> sortList(
			List<Map.Entry<String, Float>> list,
			DataDrivenExperimentGeneType geneType){
		
		if (geneType.isExpressingProteinCodingGenes()){
			//Sort in descending order
			
			// For ExpressingGenes
			// From largest to smallest tpm value
			// Sort list with comparator, to compare the Map values in descending order
			Collections.sort(list, new Comparator<Map.Entry<String, Float>>() {
				public int compare(Map.Entry<String, Float> o1,
	                                           Map.Entry<String, Float> o2) {
					return (o2.getValue()).compareTo(o1.getValue());
				}
			});

		}else if (geneType.isNonExpressingProteinCodingGenes()){
			//Sort in ascending order
			
			// For NonExpressingGenes
			// From smallest to largest tpm value
			// Sort list with comparator, to compare the Map values in ascending order
			Collections.sort(list, new Comparator<Map.Entry<String, Float>>() {
				public int compare(Map.Entry<String, Float> o1,
	                                           Map.Entry<String, Float> o2) {
					return (o1.getValue()).compareTo(o2.getValue());
				}
			});

		}//End of ELSE
		
		return list;
		
	}
	
	public static Float getTopPercentage(
			List<Map.Entry<String, Float>> list,
			DataDrivenExperimentTopPercentageType topPercentage){
		
		int  index = 0;
				
		if (topPercentage.equals("FIRST_GENE_TPM")){
			index = 0;
		}
		else if (topPercentage.isTOP1PERCENTAGE()){
			index = list.size()*1/100;
			
		}
		else if (topPercentage.isTOP2PERCENTAGE()){
			index = list.size()*2/100;
			
		}
		else if (topPercentage.isTOP5PERCENTAGE()){
			index = list.size()*5/100;
			
		}
		else if (topPercentage.isTOP10PERCENTAGE()){
			index = list.size()*10/100;
			
		}else 	if (topPercentage.isTOP25PERCENTAGE()){
			index = list.size()*25/100;
			
		}else 	if (topPercentage.isTOP50PERCENTAGE()){
			index = list.size()*50/100;
			
		}
		else 	if (topPercentage.isTOP55PERCENTAGE()){
			index = list.size()*55/100;
			
		}
		else 	if (topPercentage.isTOP60PERCENTAGE()){
			index = list.size()*60/100;
			
		}else if (topPercentage.equals("LAST_GENE_TPM")){
			index = list.size()-1;
			
		}
		
		return list.get(index).getValue();
	}
	
	public static TObjectFloatMap<String> getUnion(
			TObjectFloatMap<String> ensemblGeneID2TPMMapforRep1,
			TObjectFloatMap<String> ensemblGeneID2TPMMapforRep2, 
			DataDrivenExperimentGeneType geneType){
		
		String geneID = null;
		Float tpm1 = null;
		Float tpm2 = null;
		
		TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2 = new TObjectFloatHashMap<String>();
		
		for(TObjectFloatIterator<String> itr =ensemblGeneID2TPMMapforRep1.iterator();itr.hasNext();){
			
			itr.advance();
			
			geneID = itr.key();
			tpm1 =itr.value();
			
			if (ensemblGeneID2TPMMapforRep2.containsKey(geneID)){
				
				tpm2 = ensemblGeneID2TPMMapforRep2.get(geneID);
				
				//Save the lowest TPM
				if (geneType.isExpressingProteinCodingGenes()){
					
					if(tpm1<tpm2){
						ensemblGeneID2TPMMapforUnionofRep1andRep2.put(geneID, tpm1);
					}else{
						ensemblGeneID2TPMMapforUnionofRep1andRep2.put(geneID, tpm2);
					}
					
				}
				
				//Save the highest TPM
				else if (geneType.isNonExpressingProteinCodingGenes()){
					
					if(tpm1>tpm2){
						ensemblGeneID2TPMMapforUnionofRep1andRep2.put(geneID, tpm1);
					}else{
						ensemblGeneID2TPMMapforUnionofRep1andRep2.put(geneID, tpm2);
					}
					
				}
			}//End of IF Rep2 contains geneID
			
			else{
				ensemblGeneID2TPMMapforUnionofRep1andRep2.put(geneID, tpm1);
			}//End of IF Rep2 does not contains geneID
			
		}//End of FOR traversing each element in ensemblGeneID2TPMMapforRep1
		

		
		return ensemblGeneID2TPMMapforUnionofRep1andRep2;
		
	}
	
	public static TObjectFloatMap<String> fillMapUsingGTFFile(
			String gtfFileNameWithPath) {

		String strLine = null;
		String ensemblGeneID = null;

		float tpm;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;

		TObjectFloatMap<String> ensemblGeneID2TPMMap = new TObjectFloatHashMap<String>();

		try{
			FileReader fileReader = FileOperations.createFileReader( gtfFileNameWithPath);
			BufferedReader bufferedReader = new BufferedReader( fileReader);

			// Skip header line
			// gene_id transcript_id(s) length effective_length expected_count TPM FPKM
			strLine = bufferedReader.readLine();

			// sample line from file
			// ENSG00000000003.10 ENST00000373020.4,ENST00000494424.1,ENST00000496771.1 2206.00 2052.31 5.00 0.05 0.04

			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab 	= strLine.indexOf( '\t');
				indexofSecondTab 	= ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
				indexofThirdTab 	= ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
				indexofFourthTab 	= ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
				indexofFifthTab 	= ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
				indexofSixthTab 	= ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;

				ensemblGeneID = strLine.substring( 0, indexofFirstTab);
				tpm = Float.parseFloat( strLine.substring( indexofFifthTab + 1, indexofSixthTab));

				if( !ensemblGeneID2TPMMap.containsKey( ensemblGeneID)){
					ensemblGeneID2TPMMap.put(ensemblGeneID, tpm);
				}else{
					System.out.println( "More than one TPM for the same ensemblGeneID ");
				}

			}// End of While

			// Close BufferedReader
			bufferedReader.close();

			System.out.println("Number of entries in ensemblGeneID2TPMMap for " + gtfFileNameWithPath + " is " + ensemblGeneID2TPMMap.size());

		}catch( IOException e){
			e.printStackTrace();
		}

		return ensemblGeneID2TPMMap;

	}

	
//	public static String getTPMString( float tpmThreshold) {
//
//		//TPM values for Expressing Genes
//		if( tpmThreshold == 1f)
//			return Commons.TPM_1;
//		
//		else if (tpmThreshold == 10f)
//			return Commons.TPM_10;
//		
//		else if (tpmThreshold == 100f)
//			return Commons.TPM_100;
//		
//		
//		//TPM values for NonExpressing Genes
//		else if( tpmThreshold == 0f)
//			return Commons.TPM_0;
//
//		else if( tpmThreshold == 0.1f)
//			return Commons.TPM_0_1;
//
//		else if( tpmThreshold == 0.01f)
//			return Commons.TPM_0_01;
//
//		else if( tpmThreshold == 0.001f)
//			return Commons.TPM_0_001;
//
//		else if( tpmThreshold == 0.0001f)
//			return Commons.TPM_0_0001;
//
//		else if( tpmThreshold == 0.00001f)
//			return Commons.TPM_0_00001;
//
//		else if( tpmThreshold == 0.000001f)
//			return Commons.TPM_0_000001;
//		
//		//TPM Unknown
//		else
//			return Commons.TPM_UNKNOWN;
//
//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
