/**
 * 
 */
package datadrivenexperiment;

import common.Commons;

import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentGeneType;
import gnu.trove.map.TObjectFloatMap;

/**
 * @author Burçak Otlu
 * @date Aug 25, 2015
 * @project Glanet 
 *
 */
public class Step0_TPMValuesGathering {

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
		
		//Get the TOP_TEN_PERCENTAGE_TPM_VALUE
		//Get the TOP_TWENTYFIVE_PERCENTAGE_TPM_VALUE
		//Get the TOP_FIFTY_PERCENTAGE_TPM_VALUE
		float tpmTopTen = DataDrivenExperimentCommon.getTopPercentage(ensemblGeneID2TPMMapforUnionofRep1andRep2,geneType,"TOP_TEN_PERCENTAGE");
		float tpmTopTwenthyFive = DataDrivenExperimentCommon.getTopPercentage(ensemblGeneID2TPMMapforUnionofRep1andRep2,geneType,"TOP_TWENTYFIVE_PERCENTAGE");
		float tpmTopFifty = DataDrivenExperimentCommon.getTopPercentage(ensemblGeneID2TPMMapforUnionofRep1andRep2,geneType,"TOP_FIFTY_PERCENTAGE");

		System.out.println(ensemblGeneID2TPMMapforUnionofRep1andRep2.size());		
	}

}
