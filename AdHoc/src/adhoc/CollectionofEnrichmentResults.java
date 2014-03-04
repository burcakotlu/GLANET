/**
 * @author Burcak Otlu
 * Feb 13, 2014
 * 1:15:47 PM
 * 2014
 *
 * 
 */
package adhoc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import multipletesting.BenjaminiandHochberg;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;

import auxiliary.FunctionalElement;
import auxiliary.NumberofComparisons;
import auxiliary.NumberofComparisonsforBonferroniCorrectionCalculation;

import common.Commons;




public class CollectionofEnrichmentResults {
	
	public void collectEnrichmentResults(String fileName, int numberofRuns, int numberofPermutations, int numberofComparisons, float level){
		
		//Commons.DOKTORA_ECLIPSE_WORKSPACE + toBePolledDirectoryName + "_" + runNumber +".txt");
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine;
		
		int indexofTab;
		int indexofPipe;
		int indexofFormerComma;
		int indexofLatterComma;
		
		String elementName;
		int originalNumberofOverlaps;
		int permutationNumberofOverlaps;
		
		int numberofPermutationsHavingOverlapsGreaterThanorEqualto;
		float empiricalPValue;
		float bonferroniCorrectedEmpiricalPValue;
		
		FunctionalElement element;
		
		Map<String,FunctionalElement> name2ElementMap = new  HashMap<String,FunctionalElement>();
	
		try {
			
			for (int i=1; i<=numberofRuns; i++){
					fileReader = new FileReader(fileName + "_HIV_Run" + i + ".txt" );
					bufferedReader = new BufferedReader(fileReader);
					
					while((strLine = bufferedReader.readLine())!=null){
						
						//Initialize numberofPermutationsHavingOverlapsGreaterThanorEqualto to zero
						numberofPermutationsHavingOverlapsGreaterThanorEqualto = 0;
						
						indexofTab = strLine.indexOf('\t');
						indexofPipe = strLine.indexOf('|');
						
						elementName = strLine.substring(0,indexofTab);
						originalNumberofOverlaps = Integer.parseInt(strLine.substring(indexofTab+1,indexofPipe));
						
						indexofFormerComma = indexofPipe;
						indexofLatterComma = strLine.indexOf(',', indexofFormerComma+1);
						
						while(indexofFormerComma!= -1 && indexofLatterComma!= -1){
							permutationNumberofOverlaps = Integer.parseInt(strLine.substring(indexofFormerComma+1, indexofLatterComma));
							
							if(permutationNumberofOverlaps >= originalNumberofOverlaps){
								numberofPermutationsHavingOverlapsGreaterThanorEqualto++;
							}
							
							indexofFormerComma = indexofLatterComma;
							indexofLatterComma = strLine.indexOf(',', indexofLatterComma+1);
							
						}// Inner while loop: all permutations, number of overlaps
						
						//write numberofPermutationsHavingOverlapsGreaterThanorEqualto to map
						if(name2ElementMap.get(elementName)==null){
							element = new FunctionalElement();
							
							element.setName(elementName);
							element.setOriginalNumberofOverlaps(originalNumberofOverlaps);
							element.setNumberofPermutationsHavingOverlapsGreaterThanorEqualto(numberofPermutationsHavingOverlapsGreaterThanorEqualto);
							
							name2ElementMap.put(elementName, element);
						}else{
							
							element = name2ElementMap.get(elementName);
							
							element.setNumberofPermutationsHavingOverlapsGreaterThanorEqualto(element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + numberofPermutationsHavingOverlapsGreaterThanorEqualto );
							
						}
												
					}//Outer while loop: Read all lines					
					
					//Close bufferedReader
					bufferedReader.close();
									
			}//End of for: each run
			
			
			
			
			//Now compute empirical pValue and Bonferroni Corrected pValue and write
			for(Map.Entry<String, FunctionalElement> entry: name2ElementMap.entrySet()){
				
				elementName = entry.getKey();
				element 	= entry.getValue();
				
				numberofPermutationsHavingOverlapsGreaterThanorEqualto = element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto();
				
				empiricalPValue = (numberofPermutationsHavingOverlapsGreaterThanorEqualto *1.0f)/(numberofRuns * numberofPermutations);
				bonferroniCorrectedEmpiricalPValue = ((numberofPermutationsHavingOverlapsGreaterThanorEqualto*1.0f)/(numberofRuns * numberofPermutations)) * numberofComparisons;
				
				if (bonferroniCorrectedEmpiricalPValue>1.0f){
					bonferroniCorrectedEmpiricalPValue = 1.0f;
				}
										
				element.setEmpiricalPValue(empiricalPValue);
				element.setBonferroniCorrectedEmpiricalPValue(bonferroniCorrectedEmpiricalPValue);			
	
			}
			
			//convert map.values() into a list
			//sort w.r.t. empirical p value
			//control for FDR
			List<FunctionalElement> list = new ArrayList<FunctionalElement>(name2ElementMap.values());
			Collections.sort(list,FunctionalElement.EMPIRICAL_P_VALUE);
			BenjaminiandHochberg.controlFalseDiscoveryRate(list,level);
			//sort w.r.t. Benjamini and Hochberg FDR
			Collections.sort(list,FunctionalElement.BENJAMINI_HOCHBERG_FDR);
			
			//write the results to a output file starts		
			fileWriter = new FileWriter(fileName  + "_all.txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//header line in output file
			bufferedWriter.write("Element" + "\t" + "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsHavingNumberofOverlapsGreaterThanorEqualTo in " +(numberofRuns * numberofPermutations)+ " Permutations" + "\t" + "empiricalPValue" + "\t" + "BonfCorrPVlaue: numberOfComparisons " + numberofComparisons + "\t" + "joverMtimesDelta" + "\t" + "Benjamini and Hochberg FDR" +"\n");

			Iterator<FunctionalElement> itr = list.iterator();
			
			while(itr.hasNext()){
				element = itr.next();
				
				//line per element in output file
				bufferedWriter.write(element.getName() + "\t" +  element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t"+ element.getEmpiricalPValue() + "\t" +element.getBonferroniCorrectedEmpiricalPValue() + "\t" + element.getJoverMtimesDelta() + "\t" + element.getSignificantFDR() + "\n");
			
			}
			
			//close the file
			bufferedWriter.close();
			//write the results to a output file ends

		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
	}

	/**
	 * 
	 */
	public CollectionofEnrichmentResults() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CollectionofEnrichmentResults collection = new CollectionofEnrichmentResults();
		
		NumberofComparisons  numberofComparisons = new NumberofComparisons();
		NumberofComparisonsforBonferroniCorrectionCalculation.getNumberofComparisonsforBonferroniCorrection(numberofComparisons);
		
		String dnaseEnrichment = args[0];
		String histoneEnrichment  = args[1];
		String tfKeggPathwayEnrichment = args[2];
		String tfCellLineKeggPathwayEnrichment = args[3];
		
		int numberofRuns = Integer.parseInt(args[4]);
		int numberofPermutations = Integer.parseInt(args[5]);
		float level = Float.parseFloat(args[6]);
		
		if(dnaseEnrichment.equals(Commons.DO_DNASE_ENRICHMENT)){
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_DNASE_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonsDnase(),level);
		}
		
		if (histoneEnrichment.equals(Commons.DO_HISTONE_ENRICHMENT)){
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_HISTONE_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonsHistone(),level);
		}
		
		if(tfKeggPathwayEnrichment.equals(Commons.DO_TF_KEGGPATHWAY_ENRICHMENT)){
			
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_TF_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonsTfbs(),level);

			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonsExonBasedKeggPathway(),level);
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonsRegulationBasedKeggPathway(),level);
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonsAllBasedKeggPathway(),level);

			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonTfExonBasedKeggPathway(),level);
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonTfRegulationBasedKeggPathway(),level);
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonTfAllBasedKeggPathway(),level);

		}
		
		if (tfCellLineKeggPathwayEnrichment.equals(Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT)){
			
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_TF_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonsTfbs(),level);

			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonsExonBasedKeggPathway(),level);
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonsRegulationBasedKeggPathway(),level);
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonsAllBasedKeggPathway(),level);

			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonTfCellLineExonBasedKeggPathway(),level);
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonTfCellLineRegulationBasedKeggPathway(),level);
			collection.collectEnrichmentResults(Commons.DOKTORA_ECLIPSE_WORKSPACE + Commons.TO_BE_POLLED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS, numberofRuns,numberofPermutations,numberofComparisons.getNumberofComparisonTfCellLineAllBasedKeggPathway(),level);

		}
		
	
	}

}
