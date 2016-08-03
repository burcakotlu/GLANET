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
import java.util.SortedMap;

import auxiliary.FileOperations;
import common.Commons;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTPMType;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.hash.TObjectFloatHashMap;

/**
 * @author Burcak Otlu
 * @date Aug 16, 2015
 * @project Glanet 
 *
 */
public class DataDrivenExperimentCommon {
	
	public static void fillTPMType2TPMValueMap(
			String ddeFolder,
			DataDrivenExperimentCellLineType cellLineType,
			DataDrivenExperimentGeneType geneType,
			SortedMap<DataDrivenExperimentTPMType,Float> tpmType2TPMValueSortedMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String tpmValuesFileName = null;
		
	
		//tpmValuesFileName = glanetFolder +  Commons.DDE + System.getProperty("file.separator") + Commons.DDE_TPM_VALUES + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + ".txt";
		//tpmValuesFileName = "/home/burcakotlu/DDCE/TPMValues/" + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + ".txt";
		tpmValuesFileName = ddeFolder  + Commons.DDE_TPM_VALUES + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + ".txt";
		
		String strLine = null;
		
		int indexofFirstTab;
		
//		Top20	0.0
//		Top10	0.0
//		Top5	0.0

		DataDrivenExperimentTPMType tpmType = null;
		Float tpmValue = null;
		
		try {
			
			fileReader = FileOperations.createFileReader(tpmValuesFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf("\t");
				
				tpmType = DataDrivenExperimentTPMType.convertStringtoEnum(strLine.substring(0, indexofFirstTab));
				tpmValue = Float.valueOf(strLine.substring(indexofFirstTab+1));
				
				tpmType2TPMValueSortedMap.put(tpmType,tpmValue);
					
			}//End of WHILE reading tpmType2TPMValue file
			
				
			//Close the stream
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
//	public static void getTPMValues(
//			String glanetFolder,
//			DataDrivenExperimentCellLineType cellLineType,
//			DataDrivenExperimentGeneType geneType,
//			SortedMap<Float,DataDrivenExperimentTPMType> tpmValue2TPMTypeSortedMap){
//		
//		FileReader fileReader = null;
//		BufferedReader bufferedReader = null;
//		
//		String tpmValuesFileName = glanetFolder +  Commons.DDE + System.getProperty("file.separator") + Commons.DDE_TPM_VALUES + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + ".txt";
//		String strLine = null;
//		
//		int indexofFirstTab;
//		
//		
////		Top20	0.0
////		Top10	0.0
////		Top5	0.0
//
//		DataDrivenExperimentTPMType tpmType = null;
//		Float tpmValue = null;
//		
//		try {
//			
//			fileReader = FileOperations.createFileReader(tpmValuesFileName);
//			bufferedReader = new BufferedReader(fileReader);
//			
//			
//			while((strLine = bufferedReader.readLine())!=null){
//				indexofFirstTab = strLine.indexOf("\t");
//				
//				tpmType = DataDrivenExperimentTPMType.convertStringtoEnum(strLine.substring(0, indexofFirstTab));
//				tpmValue = Float.valueOf(strLine.substring(indexofFirstTab+1));
//				
//				tpmValue2TPMTypeSortedMap.put(tpmValue,tpmType);
//				
//			}//End of WHILE reading tpmType2TPMValue file
//			
//				
//			//Close the stream
//			bufferedReader.close();
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	
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
			DataDrivenExperimentTPMType topPercentage){
		
		int  index = 0;
				
		if (topPercentage.equals("FIRST_GENE_TPM")){
			index = 0;
		}
		else if (topPercentage.isTOP1()){
			index = list.size()*1/100;
			
		}
		else if (topPercentage.isTOP2()){
			index = list.size()*2/100;
			
		}
		else if (topPercentage.isTOP5()){
			index = list.size()*5/100;
			
		}
		else if (topPercentage.isTOP10()){
			index = list.size()*10/100;
			
		}else if (topPercentage.isTOP20()){
			index = list.size()*20/100;
			
		}else 	if (topPercentage.isTOP25()){
			index = list.size()*25/100;
			
		}else 	if (topPercentage.isTOP50()){
			index = list.size()*50/100;
			
		}
		else 	if (topPercentage.isTOP55()){
			index = list.size()*55/100;
			
		}
		else 	if (topPercentage.isTOP60()){
			index = list.size()*60/100;
			
		}else if (topPercentage.equals("LAST_GENE_TPM")){
			index = list.size()-1;
			
		}
		
		//for debug purposes
		System.out.println("Index is: " + index + " for " + topPercentage.convertEnumtoString());
		
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
				
				// Save the lowest TPM for ExpressingGenes
				if (geneType.isExpressingProteinCodingGenes()){
					
					if(tpm1<tpm2){
						ensemblGeneID2TPMMapforUnionofRep1andRep2.put(geneID, tpm1);
					}else{
						ensemblGeneID2TPMMapforUnionofRep1andRep2.put(geneID, tpm2);
					}
					
				}
				
				// Save the highest TPM for NonExpressingGenes
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
