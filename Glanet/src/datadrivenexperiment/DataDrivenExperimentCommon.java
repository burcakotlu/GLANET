/**
 * 
 */
package datadrivenexperiment;

import enumtypes.DataDrivenExperimentGeneType;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.hash.TObjectFloatHashMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Burçak Otlu
 * @date Aug 16, 2015
 * @project Glanet 
 *
 */
public class DataDrivenExperimentCommon {
	
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
	
	
	public static float getTopPercentage(
			TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2,
			DataDrivenExperimentGeneType geneType,
			String topPercentage){
		
		if (geneType.isExpressingProteinCodingGenes()){
			//Sort in descending order
			//Get the topPercentage TPM value
			
			
		}else if (geneType.isNonExpressingProteinCodingGenes()){
			//Sort in ascending order
			//Get the topPercentage TPM value
			
		}
		
		return 0;
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

	
	public static String getTPMString( float tpmThreshold) {

		//TPM values for Expressing Genes
		if( tpmThreshold == 1f)
			return Commons.TPM_1;
		
		else if (tpmThreshold == 10f)
			return Commons.TPM_10;
		
		else if (tpmThreshold == 100f)
			return Commons.TPM_100;
		
		
		//TPM values for NonExpressing Genes
		else if( tpmThreshold == 0f)
			return Commons.TPM_0;

		else if( tpmThreshold == 0.1f)
			return Commons.TPM_0_1;

		else if( tpmThreshold == 0.01f)
			return Commons.TPM_0_01;

		else if( tpmThreshold == 0.001f)
			return Commons.TPM_0_001;

		else if( tpmThreshold == 0.0001f)
			return Commons.TPM_0_0001;

		else if( tpmThreshold == 0.00001f)
			return Commons.TPM_0_00001;

		else if( tpmThreshold == 0.000001f)
			return Commons.TPM_0_000001;
		
		//TPM Unknown
		else
			return Commons.TPM_UNKNOWN;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
