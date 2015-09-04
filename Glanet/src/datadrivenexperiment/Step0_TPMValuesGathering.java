/**
 * 
 */
package datadrivenexperiment;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import auxiliary.GlanetDecimalFormat;
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
	
	
   static class MyEntry<K,V> implements Map.Entry<String, Float>{
	  
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
		
		// This map will be filled while reading cellLineRep1
		TObjectFloatMap<String> ensemblGeneID2TPMMapforRep1;

		// This map will be filled while reading cellLineRep2
		TObjectFloatMap<String> ensemblGeneID2TPMMapforRep2;
		
		// This map will be the union of MapFromCellLineRep1 and MapFromCellLineRep2
		TObjectFloatMap<String> ensemblGeneID2TPMMapforUnionofRep1andRep2;

		String cellLineRep1GTFFileName = null;
		String cellLineRep2GTFFileName = null;
		
		float tpmForExpressingGenes = 100;
		float tpmForNonExpressingGenes = 0.001f;
		float percentile;
		
		
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
		List<Map.Entry<String, Float>> list =  new LinkedList<Map.Entry<String, Float>>();

		Map.Entry<String, Float> entry = null;
		
		for(TObjectFloatIterator<String> itr = ensemblGeneID2TPMMapforUnionofRep1andRep2.iterator();itr.hasNext();){
			itr.advance();
			
			entry = new MyEntry<String,Float>(itr.key(),itr.value());
			list.add(entry);
			
		}//End of FOR
		
		//Sort the list w.r.t. the geneType
		list = DataDrivenExperimentCommon.sortList(list,geneType);
		
		//Get the TOP_TEN_PERCENTAGE_TPM_VALUE
		//Get the TOP_TWENTYFIVE_PERCENTAGE_TPM_VALUE
		//Get the TOP_FIFTY_PERCENTAGE_TPM_VALUE
		float tpmFirstElement 	= DataDrivenExperimentCommon.getTopPercentage(list,"FIRST_GENE_TPM");
		
		float tpmTop1			= DataDrivenExperimentCommon.getTopPercentage(list,"TOP_1_PERCENTAGE");;
		float tpmTop2			= DataDrivenExperimentCommon.getTopPercentage(list,"TOP_2_PERCENTAGE");;
		float tpmTop5 		= DataDrivenExperimentCommon.getTopPercentage(list,"TOP_5_PERCENTAGE");
		float tpmTop10 		= DataDrivenExperimentCommon.getTopPercentage(list,"TOP_10_PERCENTAGE");
		float tpmTop25 		= DataDrivenExperimentCommon.getTopPercentage(list,"TOP_25_PERCENTAGE");
		float tpmTop50 		= DataDrivenExperimentCommon.getTopPercentage(list,"TOP_50_PERCENTAGE");
		
		float tpmLastElement 	= DataDrivenExperimentCommon.getTopPercentage(list,"LAST_GENE_TPM");
		
		DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat( "0.######E0");
		
		System.out.println("CellLineType" + "\t" + cellLineType.convertEnumtoString());
		System.out.println("GeneType" + "\t" + geneType.convertEnumtoString());

		System.out.println("First Gene TPM:"  + "\t" + df.format(tpmFirstElement));	
		
		System.out.println("Top1PercentageTPM:"  + "\t" + df.format(tpmTop1));		
		System.out.println("Top2PercentageTPM:"  + "\t" + df.format(tpmTop2));		
		System.out.println("Top5PercentageTPM:"  + "\t" + df.format(tpmTop5));		
		System.out.println("Top10PercentageTPM:"  + "\t" + df.format(tpmTop10));		
		System.out.println("Top25PercentageTPM:"  + "\t" + df.format(tpmTop25));		
		System.out.println("Top50PercentageTPM:"  + "\t" + df.format(tpmTop50));
		
		System.out.println("Last Gene TPM:"  + "\t" + df.format(tpmLastElement));	
		
		if (geneType.isExpressingProteinCodingGenes()){
			percentile = DataDrivenExperimentCommon.getPercentile(list,geneType,tpmForExpressingGenes);
			System.out.println("Percentile for TPM" + "\t" + tpmForExpressingGenes + "\t" + "is" + "\t" + percentile);
		}else if (geneType.isNonExpressingProteinCodingGenes()){
			percentile = DataDrivenExperimentCommon.getPercentile(list,geneType,tpmForNonExpressingGenes);
			System.out.println("Percentile for TPM" + "\t" + tpmForNonExpressingGenes + "\t" + "is" + "\t" + percentile);
		}
		

		
			
	}

}
