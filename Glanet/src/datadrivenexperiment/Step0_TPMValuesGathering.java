/**
 * 
 */
package datadrivenexperiment;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import common.Commons;

import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentGeneType;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.map.TObjectFloatMap;

/**
 * @author Burçak Otlu
 * @date Aug 25, 2015
 * @project Glanet 
 *
 */
public class Step0_TPMValuesGathering {
	
	
   static class MyEntry<String, Float> implements Map.Entry<String, Float>{
	  
	   private final String key;
	   private Float value;

	    public MyEntry(String key, Float value) {
	        this.key = key;
	        this.value = value;
	    }

		@Override
		public String getKey() {
			return key;
		}

		@Override
		public Float getValue() {
			return value;
		}

		@Override
		public Float setValue(Float value) {
			 Float old = this.value;
		     this.value = value;
		     return old;
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String glanetFolder = args[0];
		
		DataDrivenExperimentCellLineType cellLineType = DataDrivenExperimentCellLineType.convertStringtoEnum(args[1]);
		
		DataDrivenExperimentGeneType geneType = DataDrivenExperimentGeneType.convertStringtoEnum(args[2]);
		
		// This will be filled while reading cellLineRep1
		TObjectFloatMap<String> ensemblGeneID2TPMMapforRep1;

		// This will be filled while reading cellLineRep2
		TObjectFloatMap<String> ensemblGeneID2TPMMapforRep2;
		
		// This will be filled while reading cellLineRep2
		TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2;


		String cellLineRep1GTFFileName = null;
		String cellLineRep2GTFFileName = null;
		
		switch(cellLineType){
		
			case GM12878: {
				// Input File
				// Set cellLine Replicate1 gtf file with path
				// We will get the TPM values from this input file.
				cellLineRep1GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.Gm12878Rep1_genes_results;

				// Input File
				// Set cellLine Replicate2 gtf file with path
				// We will get the TPM values from this input file.
				cellLineRep2GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.Gm12878Rep2_genes_results;

				break;
			}
			
			case K562: {
				// Input File
				// Set cellLine Replicate1 gtf file with path
				// We will get the TPM values from this input file.
				cellLineRep1GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.K562Rep1_genes_results;

				// Input File
				// Set cellLine Replicate2 gtf file with path
				// We will get the TPM values from this input file.
				cellLineRep2GTFFileName = glanetFolder + Commons.RNA_SEQ_GM12878_K562 + System.getProperty( "file.separator") + Commons.K562Rep2_genes_results;

				break;
			}
			
			default: 
				break;
		
		
		}//End of Switch cellLineType
		
		// Read cellLineRep1Results file and fill ensemblGeneID2TPMMapRep1
		ensemblGeneID2TPMMapforRep1 = DataDrivenExperimentCommon.fillMapUsingGTFFile(cellLineRep1GTFFileName);
		
		// Read cellLineRep1Results file and fill ensemblGeneID2TPMMapRep2
		ensemblGeneID2TPMMapforRep2 = DataDrivenExperimentCommon.fillMapUsingGTFFile(cellLineRep2GTFFileName);
		
		//Get the union of ensemblGeneID2TPMMapRep1 and ensemblGeneID2TPMMapRep2
		ensemblGeneID2TPMMapforUnionofRep1andRep2 = DataDrivenExperimentCommon.getUnion(ensemblGeneID2TPMMapforRep1,ensemblGeneID2TPMMapforRep2, geneType);
		
		
		//Convert map into list
		//Sort list by comparator
		// Convert Map to List
		List<Map.Entry<String, Float>> list =  new LinkedList<Map.Entry<String, Float>>();

		Map.Entry<String, Float> entry = null;
		
		for(TObjectFloatIterator<String> itr = ensemblGeneID2TPMMapforUnionofRep1andRep2.iterator();itr.hasNext();){
			itr.advance();
			
			entry = new MyEntry<String,Float>(itr.key(),itr.value());
			list.add(entry);
			
		}//End of FOR
		
		// Sort list with comparator, to compare the Map values in ascending order
		Collections.sort(list, new Comparator<Map.Entry<String, Float>>() {
			public int compare(Map.Entry<String, Float> o1,
                                           Map.Entry<String, Float> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		
		// Sort list with comparator, to compare the Map values in descending order
		Collections.sort(list, new Comparator<Map.Entry<String, Float>>() {
			public int compare(Map.Entry<String, Float> o1,
                                           Map.Entry<String, Float> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		System.out.println(list.get(list.size()/10).getValue());
		System.out.println(list.get(list.size()/4).getValue());
		System.out.println(list.get(list.size()/2).getValue());
		
		//Get the TOP_TEN_PERCENTAGE_TPM_VALUE
		//Get the TOP_TWENTYFIVE_PERCENTAGE_TPM_VALUE
		//Get the TOP_FIFTY_PERCENTAGE_TPM_VALUE
		float tpmTopTen = DataDrivenExperimentCommon.getTopPercentage(ensemblGeneID2TPMMapforUnionofRep1andRep2,geneType,"TOP_TEN_PERCENTAGE");
		float tpmTopTwenthyFive = DataDrivenExperimentCommon.getTopPercentage(ensemblGeneID2TPMMapforUnionofRep1andRep2,geneType,"TOP_TWENTYFIVE_PERCENTAGE");
		float tpmTopFifty = DataDrivenExperimentCommon.getTopPercentage(ensemblGeneID2TPMMapforUnionofRep1andRep2,geneType,"TOP_FIFTY_PERCENTAGE");

		System.out.println(tpmTopTen);		
		System.out.println(tpmTopTwenthyFive);		
		System.out.println(tpmTopFifty);		
		
	}

}
