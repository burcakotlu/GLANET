/**
 * @author Burcak Otlu
 * Feb 13, 2014
 * 3:57:56 PM
 * 2014
 *
 * 
 */
package auxiliary;

import java.util.HashMap;
import java.util.Map;

import binomialdistribution.CalculateBinomialDistributions;

import common.Commons;

public class NumberofComparisonsforBonferroniCorrectionCalculation {
	
	public static void getNumberofComparisonsforBonferroniCorrection(String outputFolder,NumberofComparisons numberofComparisons){
		
		Map<String,Integer> dnaseCellLineHashMap = new HashMap<String,Integer>();		
		Map<String,Integer> tfbsNameCellLineNameHashMap = new HashMap<String,Integer>();
		Map<String,Integer> histoneNameCellLineNameHashMap = new HashMap<String,Integer>();		
		Map<String,Integer> exonBasedKeggPathwayHashMap = new HashMap<String,Integer>();
		Map<String,Integer> regulationBasedKeggPathwayHashMap = new HashMap<String,Integer>();
		Map<String,Integer> allBasedKeggPathwayHashMap = new HashMap<String,Integer>();
		
		//Bonferroni Correction
		//Dnase		
		CalculateBinomialDistributions.fillHashMapwithOccurences(dnaseCellLineHashMap,outputFolder, Commons.DNASE_CELL_LINE_WHOLE_GENOME_USING_INTERVAL_TREE);
		numberofComparisons.setNumberofComparisonsDnase(dnaseCellLineHashMap.size());
	
		//Tf
		CalculateBinomialDistributions.fillHashMapwithOccurences(tfbsNameCellLineNameHashMap,outputFolder, Commons.TFBS_WHOLE_GENOME_USING_INTERVAL_TREE);
		numberofComparisons.setNumberofComparisonsTfbs(tfbsNameCellLineNameHashMap.size());
		
		//histone
		CalculateBinomialDistributions.fillHashMapwithOccurences(histoneNameCellLineNameHashMap,outputFolder, Commons.HISTONE_WHOLE_GENOME_USING_INTERVAL_TREE);
		numberofComparisons.setNumberofComparisonsHistone(histoneNameCellLineNameHashMap.size());
		
		//exon based kegg pathway
		CalculateBinomialDistributions.fillHashMapwithOccurences(exonBasedKeggPathwayHashMap,outputFolder, Commons.EXON_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE);
		numberofComparisons.setNumberofComparisonsExonBasedKeggPathway(exonBasedKeggPathwayHashMap.size());
		
		//regulation based Kegg Pathway
		CalculateBinomialDistributions.fillHashMapwithOccurences(regulationBasedKeggPathwayHashMap,outputFolder, Commons.REGULATION_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE);
		numberofComparisons.setNumberofComparisonsRegulationBasedKeggPathway(regulationBasedKeggPathwayHashMap.size());
		
		//all based Kegg Pathway
		//Attention!!!
		//Gets the number of kegg pathways using Commons.REGULATION_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE file
		CalculateBinomialDistributions.fillHashMapwithOccurences(allBasedKeggPathwayHashMap, outputFolder,Commons.REGULATION_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE);
		numberofComparisons.setNumberofComparisonsAllBasedKeggPathway(regulationBasedKeggPathwayHashMap.size());
		
		//Number of Different Tfbs Cell Line Combinations 406
		//Number of Different Kegg Pathways 269
		//406 * 269 = 109214
		numberofComparisons.setNumberofComparisonTfCellLineExonBasedKeggPathway(109214);
		numberofComparisons.setNumberofComparisonTfCellLineRegulationBasedKeggPathway(109214);
		numberofComparisons.setNumberofComparisonTfCellLineAllBasedKeggPathway(109214);

		//Number of Different Tf 149
		//Number of Different Kegg Pathways 269
		//149 * 269 = 40081
		numberofComparisons.setNumberofComparisonTfExonBasedKeggPathway(40081);
		numberofComparisons.setNumberofComparisonTfRegulationBasedKeggPathway(40081);
		numberofComparisons.setNumberofComparisonTfAllBasedKeggPathway(40081);
	}

	
	

	/**
	 * 
	 */
	public NumberofComparisonsforBonferroniCorrectionCalculation() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
