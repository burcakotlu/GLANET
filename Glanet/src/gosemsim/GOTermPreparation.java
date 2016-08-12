/**
 * 
 */
package gosemsim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import userdefined.GOAnnotationsInputFileGeneration;
import common.Commons;
import enumtypes.EnrichmentDecisionType;
import auxiliary.FileOperations;

/**
 * @author Burçak Otlu
 * @date Aug 11, 2016
 * @project Glanet 
 *
 *
 * Motivation: In this class my motivation is to form go terms from flat files.
 * And provide them as input to GOSemSim for mgoSim function
 * 
 *  e.g.:
 *  
 *  > go1=c("GO:0004022","GO:0004024","GO:0004174")
 *	> go2=c("GO:0009055","GO:0005515")
 *	> mgoSim(go1, go2, measure="Wang", ont = "MF")
 *	[1] 0.299
 *
 */
public class GOTermPreparation {

	/**
	 * 
	 */
	public GOTermPreparation() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static String convert(
			List<String> GOTermList,
			String name){
		
		String GOTermforGOSemSim = null;
		String GOTerm = null;
		int i=0;
		
		if (GOTermList.size()>0){
			
			
			//Initialize
			GOTermforGOSemSim = "GOTermList_" + name + "= c(";
			
			//For each GOTerm except the last one
			for(i=0; i<(GOTermList.size()-1);i++){
				
				GOTerm = GOTermList.get(i);
				GOTermforGOSemSim = 	GOTermforGOSemSim + "\"" + GOTerm + "\","  ;
				
			}//End of for
			
			//For last element
			GOTerm = GOTermList.get(i);
			GOTermforGOSemSim = 	GOTermforGOSemSim + "\"" + GOTerm + "\")"  ;

			
		}//There is at least one GOTerm
		
		
		return GOTermforGOSemSim;
	}
	
	
	public static void getGOTermAndAccumulate(
			String strLine,
			List<String> GOTermList,
			List<String> enrichedGOTerms_BiologicalProcess_P,
			List<String> enrichedGOTerms_MolecularFunction_F,
			List<String> enrichedGOTerms_CellularComponent_C,
			EnrichmentDecisionType enrichmentDecisionType){
		
		String GOTerm = null;
		Boolean enriched = null;
		
		Float bonferroniCorrectedPValue = null;
		Float BHAdjustedPValue = null;
		
		String ontology = null;
		
		int indexofUnderscore;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;
		int indexofTwelfthTab;
		int indexofThirteenthTab;
		int indexofFourteenthTab;
		int indexofFifteenthTab;
		int indexofSixteenthTab;
		int indexofSeventeenthTab;
		int indexofEighteenthTab;
		
		indexofFirstTab = strLine.indexOf( '\t');
		indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
		indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
		indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
		indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
		indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;
		indexofSeventhTab = ( indexofSixthTab > 0)?strLine.indexOf( '\t', indexofSixthTab + 1):-1;
		indexofEigthTab = ( indexofSeventhTab > 0)?strLine.indexOf( '\t', indexofSeventhTab + 1):-1;
		indexofNinethTab = ( indexofEigthTab > 0)?strLine.indexOf( '\t', indexofEigthTab + 1):-1;
		indexofTenthTab = ( indexofNinethTab > 0)?strLine.indexOf( '\t', indexofNinethTab + 1):-1;
		indexofEleventhTab = ( indexofTenthTab > 0)?strLine.indexOf( '\t', indexofTenthTab + 1):-1;
		indexofTwelfthTab = ( indexofEleventhTab > 0)?strLine.indexOf( '\t', indexofEleventhTab + 1):-1;
		indexofThirteenthTab = ( indexofTwelfthTab > 0)?strLine.indexOf( '\t', indexofTwelfthTab + 1):-1;

		indexofFourteenthTab = ( indexofThirteenthTab > 0)?strLine.indexOf( '\t', indexofThirteenthTab + 1):-1;
		indexofFifteenthTab = ( indexofFourteenthTab > 0)?strLine.indexOf( '\t', indexofFourteenthTab + 1):-1;
		indexofSixteenthTab = ( indexofFifteenthTab > 0)?strLine.indexOf( '\t', indexofFifteenthTab + 1):-1;
		indexofSeventeenthTab = ( indexofSixteenthTab > 0)?strLine.indexOf( '\t', indexofSixteenthTab + 1):-1;
		indexofEighteenthTab = ( indexofSeventeenthTab > 0)?strLine.indexOf( '\t', indexofSeventeenthTab + 1):-1;

//		Look in between 12 and 13
//		1176	GO_0034134	860	117	10000	14697	291.136569	203.2774396	2.798458265	2.57E-03	1.00E+00	4.83E-02	TRUE	1.17E-02	1.00E+00	8.70E-02	FALSE	toll-like receptor 2 signaling pathway
//		1034	GO_0044297	615	88	10000	14697	204.262824	147.6662194	2.78152429	2.71E-03	1.00E+00	5.06E-02	FALSE	8.80E-03	1.00E+00	8.38E-02	FALSE	cell body


		//example strLine
		//9447	GO_1990715	1	3	1000	10060	1.0	0.0	null	null	null	null	null	3E-3	1E0	2.142857E-2	true	NoDescriptionAvailable
		//2070	GO_0035694	2	4	1000	10060	1.4444444444444444	0.5270462766947299	1.0540925533894598	1.459203E-1	1E0	4.974555E-1	false	4E-3	1E0	2.727273E-2	true	mitochondrial protein catabolic process	P


		//GO Term is between 1 and 2
		GOTerm = strLine.substring(indexofFirstTab+1, indexofSecondTab);
		
		//bonferroniCorrectedPValue is between 14 and 15
		bonferroniCorrectedPValue = Float.parseFloat(strLine.substring(indexofFourteenthTab+1, indexofFifteenthTab));
		
		//BHAdjustedPValue is between 15 and 16
		BHAdjustedPValue = Float.parseFloat(strLine.substring(indexofFifteenthTab+1, indexofSixteenthTab));
		
		//ontology is after the eighteenth tab
		ontology = strLine.substring(indexofEighteenthTab+1);
				
		switch(enrichmentDecisionType){
		
			case ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_SAMPLINGS:
				if(bonferroniCorrectedPValue < 0.05f){
					enriched = true;
				}else{
					enriched = false;
				}
				break;
			
			case ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_RATIO_OF_SAMPLINGS:
				if(BHAdjustedPValue < 0.05f){
					enriched = true;
				}else{
					enriched = false;
				}
				break;
				
			default:
				break;
		
		}//End of switch


		indexofUnderscore = GOTerm.indexOf("_");
		String newGOTerm = GOTerm.substring(0, indexofUnderscore)  + ":" + GOTerm.substring(indexofUnderscore+1);
		
		
		
		if (enriched){
			
			if (!GOTermList.contains(newGOTerm)){
				GOTermList.add(newGOTerm);
			}//End of IF list does not contain newGOTerm
			
			if (ontology.equals("P")){
				enrichedGOTerms_BiologicalProcess_P.add(newGOTerm);
			}else if (ontology.equals("F")){
				enrichedGOTerms_MolecularFunction_F.add(newGOTerm);
			}else if (ontology.equals("C")){
				enrichedGOTerms_CellularComponent_C.add(newGOTerm);
			}
		
		}//End of IF enriched
		
		
		
	}
	
	public static String readEnrichmentFileAndFormGOTermList(
			String fileName,
			List<String> enrichedGOTerms_BiologicalProcess_P,
			List<String> enrichedGOTerms_MolecularFunction_F,
			List<String> enrichedGOTerms_CellularComponent_C,
			EnrichmentDecisionType enrichmentDecisionType,
			String auxiliaryName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		List<String> GOTermList = new ArrayList<String>();
		String GOTermforGOSemSim = null;
		
		String GOTerms_BiologicalProcess = null;
		String GOTerms_MolecularFunction = null;
		String GOTerms_CellularComponent = null;
		
		try {
			fileReader = FileOperations.createFileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//Skip header line
			strLine= bufferedReader.readLine();
			
			while((strLine= bufferedReader.readLine())!=null){
				
				getGOTermAndAccumulate(
						strLine,
						GOTermList,
						enrichedGOTerms_BiologicalProcess_P,
						enrichedGOTerms_MolecularFunction_F,
						enrichedGOTerms_CellularComponent_C,
						enrichmentDecisionType);
				
				
			}//End of while
			
			GOTermforGOSemSim = convert(GOTermList,auxiliaryName + "_ALL");
			
			GOTerms_BiologicalProcess = convert(enrichedGOTerms_BiologicalProcess_P, auxiliaryName + "_P");
			GOTerms_MolecularFunction = convert(enrichedGOTerms_MolecularFunction_F, auxiliaryName + "_F");
			GOTerms_CellularComponent = convert(enrichedGOTerms_CellularComponent_C, auxiliaryName + "_C");
			
			System.out.println(GOTerms_BiologicalProcess);
			System.out.println(GOTerms_MolecularFunction);
			System.out.println(GOTerms_CellularComponent);
			
			//Close
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return GOTermforGOSemSim;
		
	}

	
	public static void readGeneAssociationGOPRefHumanFileWriteGATA2GOTerms(
			String geneAssociationGOARefHumanFile,
			List<String> experimentalEvidenceCodeList){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		
		String geneSymbol = null;
		String GOTerm = null;
		String evidenceCode = null;
		String ontology = null;

		
		List<String> GATA2_GO_TERMS_P = new ArrayList<String> ();
		List<String> GATA2_GO_TERMS_F = new ArrayList<String> ();
		List<String> GATA2_GO_TERMS_C = new ArrayList<String> ();
		List<String> GATA2_GO_TERMS_ALL = new ArrayList<String> ();
		
		
		String GATA2_GOTerms_P = null;
		String GATA2_GOTerms_F = null;
		String GATA2_GOTerms_C = null;
		String GATA2_GOTerms_ALL = null;
		
		try {
			
			fileReader = FileOperations.createFileReader(geneAssociationGOARefHumanFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while( ( strLine = bufferedReader.readLine()) != null){

				// Skip comment lines
				if( !( strLine.startsWith( "!"))){

					//example strLine
					//Please notice that there are two tabs between third column and fourth column
					//UniProtKB	A0A024R161	DNAJC25-GNG10		GO:0004871	GO_REF:0000038	IEA	UniProtKB-KW:KW-0807	F	Guanine nucleotide-binding protein subunit gamma	A0A024R161_HUMAN|DNAJC25-GNG10|hCG_1994888	protein	taxon:9606	20160507	UniProt		
					
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab + 1);
					indexofThirdTab = strLine.indexOf('\t', indexofSecondTab + 1);
					indexofFourthTab = strLine.indexOf('\t', indexofThirdTab + 1);
					indexofFifthTab = strLine.indexOf('\t', indexofFourthTab + 1);
					indexofSixthTab = strLine.indexOf('\t', indexofFifthTab + 1);
					indexofSeventhTab = strLine.indexOf('\t', indexofSixthTab + 1);
					indexofEigthTab = strLine.indexOf('\t', indexofSeventhTab + 1);
					indexofNinethTab = strLine.indexOf('\t', indexofEigthTab + 1);

					//GeneSymbol is between theSecondTab and theThirdTab
					geneSymbol = strLine.substring( indexofSecondTab + 1, indexofThirdTab);
					
					//GOTerm is between the FourthTab and the FifthTab
					GOTerm = strLine.substring( indexofFourthTab + 1, indexofFifthTab);
					
					//evidenceCode is between theSixthTab and theSeventhTab
					evidenceCode = strLine.substring( indexofSixthTab + 1, indexofSeventhTab);
					
					//ontology is between theeEighthTab and theNinethTab
					ontology = strLine.substring( indexofEigthTab + 1, indexofNinethTab);
					
					//Write only GOTerm geneSymbol couples with Experimental Evidence Codes
					//In order to form GOTerm from geneSymbols with experimental evidence codes
					//http://geneontology.org/book/export/html/799
					if (geneSymbol.equals("GATA2") && experimentalEvidenceCodeList.contains(evidenceCode)){
						
						if (ontology.equals("P")){
							GATA2_GO_TERMS_P.add(GOTerm);
						}else if (ontology.equals("F")){
							GATA2_GO_TERMS_F.add(GOTerm);
						}else if (ontology.equals("C")){
							GATA2_GO_TERMS_C.add(GOTerm);
						}
						
						GATA2_GO_TERMS_ALL.add(GOTerm);
						
							
						
					}//End of IF experimental evidence code
					
					
				}//End of IF not a header line
			
			}//End of WHILE
			
			GATA2_GOTerms_P = convert(GATA2_GO_TERMS_P, "GATA2_P");
			GATA2_GOTerms_F = convert(GATA2_GO_TERMS_F, "GATA2_F");
			GATA2_GOTerms_C = convert(GATA2_GO_TERMS_C, "GATA2_C");
			GATA2_GOTerms_ALL = convert(GATA2_GO_TERMS_ALL, "GATA2_ALL");
			
			System.out.println(GATA2_GOTerms_P);
			System.out.println(GATA2_GOTerms_F);
			System.out.println(GATA2_GOTerms_C);
			System.out.println(GATA2_GOTerms_ALL);

			
			//close
			bufferedReader.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//C:\Users\Burçak\Google Drive\Output\GLANET_SydhGATA2K562_GOTerms\Enrichment\UserDefinedGeneSet\GO\ExonBased\ExonBased_GO_GLANET_SydhGATA2K562_GOTerms_wrt_zScore.txt
		//RegulationBased_GO_GLANET_SydhGATA2K562_GOTerms_wrt_zScore.txt
		//AllBased_GO_GLANET_SydhGATA2K562_GOTerms_wrt_zScore.txt
		
		//String fileName = args[0];
		
		
		String exonBasedEnrichmentFileName = "C:\\Users\\Burçak\\Google Drive\\Output\\new_sydh_gata2_k562_GO\\Enrichment\\UserDefinedGeneSet\\GO\\ExonBased\\ExonBased_GO_new_sydh_gata2_k562_GO_wrt_BH_FDR_adjusted_pValue.txt";
		String regulationBasedEnrichmentFileName = "C:\\Users\\Burçak\\Google Drive\\Output\\new_sydh_gata2_k562_GO\\Enrichment\\UserDefinedGeneSet\\GO\\RegulationBased\\RegulationBased_GO_new_sydh_gata2_k562_GO_wrt_BH_FDR_adjusted_pValue.txt";
		String allBasedEnrichmentFileName = "C:\\Users\\Burçak\\Google Drive\\Output\\new_sydh_gata2_k562_GO\\Enrichment\\UserDefinedGeneSet\\GO\\AllBased\\AllBased_GO_new_sydh_gata2_k562_GO_wrt_BH_FDR_adjusted_pValue.txt";
				
		String GOTermforGOSemSim = null;
		
		//ExonBased starts
		List<String> exonBasedEnrichedGOTerms_BiologicalProcess_P = new ArrayList<String>();
		List<String> exonBasedEnrichedGOTerms_MolecularFunction_F = new ArrayList<String>();
		List<String> exonBasedEnrichedGOTerms_CellularComponent_C = new ArrayList<String>();
		
		GOTermforGOSemSim = readEnrichmentFileAndFormGOTermList(
				exonBasedEnrichmentFileName,
				exonBasedEnrichedGOTerms_BiologicalProcess_P,
				exonBasedEnrichedGOTerms_MolecularFunction_F,
				exonBasedEnrichedGOTerms_CellularComponent_C,
				EnrichmentDecisionType.ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_SAMPLINGS,
				Commons.EXON_BASED);
		
		System.out.println(GOTermforGOSemSim);
		System.out.println("********************************************");
		//ExonBased ends

		
		//RegulationBased starts
		List<String> regulationBasedEnrichedGOTerms_BiologicalProcess_P = new ArrayList<String>();
		List<String> regulationBasedEnrichedGOTerms_MolecularFunction_F = new ArrayList<String>();
		List<String> regulationBasedEnrichedGOTerms_CellularComponent_C = new ArrayList<String>();
		
		GOTermforGOSemSim = readEnrichmentFileAndFormGOTermList(
				regulationBasedEnrichmentFileName,
				regulationBasedEnrichedGOTerms_BiologicalProcess_P,
				regulationBasedEnrichedGOTerms_MolecularFunction_F,
				regulationBasedEnrichedGOTerms_CellularComponent_C,
				EnrichmentDecisionType.ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_SAMPLINGS,
				Commons.REGULATION_BASED);
		
		System.out.println(GOTermforGOSemSim);		
		System.out.println("********************************************");
		//RegulationBased ends

		//AllBased starts
		List<String> allBasedEnrichedGOTerms_BiologicalProcess_P = new ArrayList<String>();
		List<String> allBasedEnrichedGOTerms_MolecularFunction_F = new ArrayList<String>();
		List<String> allBasedEnrichedGOTerms_CellularComponent_C = new ArrayList<String>();
		
		GOTermforGOSemSim = readEnrichmentFileAndFormGOTermList(
				allBasedEnrichmentFileName,
				allBasedEnrichedGOTerms_BiologicalProcess_P,
				allBasedEnrichedGOTerms_MolecularFunction_F,
				allBasedEnrichedGOTerms_CellularComponent_C,
				EnrichmentDecisionType.ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_SAMPLINGS,
				Commons.ALL_BASED);
		
		System.out.println(GOTermforGOSemSim);
		System.out.println("********************************************");
		//AllBased ends

		
		//Prepare GATA2 GOTerms starts
		//read gene_association.goa_ref_human_May_2016 under G:\GLANET_DATA\GO
		String geneAssociationGOARefHumanFile = "G:\\GLANET_DATA\\GO\\gene_association.goa_ref_human_May_2016";

		List<String> experimentalEvidenceCodeList = new ArrayList<String>();
		GOAnnotationsInputFileGeneration.fillExperimentalEvidenceCodeList(experimentalEvidenceCodeList);
		
		readGeneAssociationGOPRefHumanFileWriteGATA2GOTerms(
				geneAssociationGOARefHumanFile,
				experimentalEvidenceCodeList);
	

		//Prepare GATA2 GOTerms ends
		

	}

}
