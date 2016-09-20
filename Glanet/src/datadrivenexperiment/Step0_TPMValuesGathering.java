/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;
import auxiliary.GlanetDecimalFormat;
import common.Commons;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTPMType;
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
		String dataDrivenExperimentFolder = glanetFolder + Commons.DDE + System.getProperty("file.separator");
		
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
		float tpmForNonExpressingGenes = 0.01f;
		float percentile;
		
		//Consecutive Step classes will read this file
		String TPMValuesFileName = dataDrivenExperimentFolder + Commons.DDE_TPM_VALUES + System.getProperty("file.separator") + cellLineType.convertEnumtoString() + "_" + geneType.convertEnumtoString() + ".txt" ; ;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			
			fileWriter = FileOperations.createFileWriter(TPMValuesFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
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
			//In Ascending order for NonExpressingGenes
			//In Descending order for ExpressingGenes
			list = DataDrivenExperimentCommon.sortList(list,geneType);
			
			float tpmFirstElement 	= DataDrivenExperimentCommon.getTopPercentage(list,DataDrivenExperimentTPMType.FIRSTELEMENT);			
			float tpmTop5 			= DataDrivenExperimentCommon.getTopPercentage(list,DataDrivenExperimentTPMType.TOP5);
			float tpmTop10 			= DataDrivenExperimentCommon.getTopPercentage(list,DataDrivenExperimentTPMType.TOP10);
			float tpmTop20 			= DataDrivenExperimentCommon.getTopPercentage(list,DataDrivenExperimentTPMType.TOP20);
			float tpmLastElement 	= DataDrivenExperimentCommon.getTopPercentage(list,DataDrivenExperimentTPMType.LASTELEMENT);
			
			DecimalFormat df = GlanetDecimalFormat.getGLANETDecimalFormat( "0.######E0");
			
			//No need for this information line
			//bufferedWriter.write("CellLineType" + "\t" + cellLineType.convertEnumtoString() +  "\t" + "GeneType" + "\t" + geneType.convertEnumtoString() + System.getProperty("line.separator"));
			
			bufferedWriter.write("Top5PercentageTPM"  + "\t" + "Top10PercentageTPM"  + "\t" + "Top20PercentageTPM"  + "\t"  +System.getProperty("line.separator"));		
			bufferedWriter.write(df.format(tpmTop5) + "\t" + df.format(tpmTop10) + "\t" + df.format(tpmTop20)  +System.getProperty("line.separator"));		
			
			bufferedWriter.write("First Gene TPM:"  + "\t" + df.format(tpmFirstElement) + System.getProperty("line.separator"));	
			bufferedWriter.write("Last Gene TPM:"  + "\t" + df.format(tpmLastElement) + System.getProperty("line.separator"));	
			
			if (geneType.isExpressingProteinCodingGenes()){
				percentile = DataDrivenExperimentCommon.getPercentile(list,geneType,tpmForExpressingGenes,bufferedWriter);
				bufferedWriter.write("Closest or Exact Percentile for TPM" + "\t" + tpmForExpressingGenes + "\t" + "is" + "\t" + percentile + System.getProperty("line.separator"));
			}else if (geneType.isNonExpressingProteinCodingGenes()){
				percentile = DataDrivenExperimentCommon.getPercentile(list,geneType,tpmForNonExpressingGenes,bufferedWriter);
				bufferedWriter.write("Closest or Exact Percentile for TPM" + "\t" + tpmForNonExpressingGenes + "\t" + "is" + "\t" + percentile + System.getProperty("line.separator"));
			}

			//Close
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		

		
			
	}

}
