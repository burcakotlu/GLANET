/**
 * @author Burcak Otlu
 * Sep 20, 2013
 * 10:34:15 AM
 * 2013
 *
 * 
 */
package augmentation.keggpathway;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import augmentation.humangenes.HumanGenesAugmentation;
import auxiliary.FunctionalElement;

import common.Commons;


public class KeggPathwayAugmentation {
	
	public static void fillKeggPathwayEntry2NameMap(Map<String,String> entry2Name){
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;
		int indexofFirstTab;
		int indexofFirstColon;
		
		String entry;
		String name;
		
		try {
			fileReader = new FileReader(Commons.KEGG_PATHWAY_ENTRY_2_NAME_INPUT_FILE);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine= bufferedReader.readLine())!=null){
				//path:hsa00010	Glycolysis / Gluconeogenesis - Homo sapiens (human)

				indexofFirstTab = strLine.indexOf('\t');
				indexofFirstColon = strLine.indexOf(':');
				entry = strLine.substring(indexofFirstColon+1,indexofFirstTab);
				name = strLine.substring(indexofFirstTab+1);
				
				entry2Name.put(entry, name);	
				
			}//End of While
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void fillKeggPathwayEntry2GeneIdListMap(Map<String,List<String>> keggPathwayEntry2GeneIdListMap){
		
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab 	= -1;
		int indexofSecondTab 	= -1;
		
		int indexofFirstColon 		= -1;
		int indexofSecondColon 		= -1;
		
		String keggPathwayEntry;
		String ncbiGeneId;
		
		List<String> existingNcbiGeneIdList = null;
			
		try {
			fileReader = new FileReader(Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example line
				//path:hsa00010	hsa:10327	reverse

				indexofFirstColon = strLine.indexOf(':');
				indexofFirstTab = strLine.indexOf('\t');
				
				keggPathwayEntry = strLine.substring(indexofFirstColon+1,indexofFirstTab);
				
				indexofSecondColon = strLine.indexOf(':',indexofFirstColon+1);
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				ncbiGeneId = strLine.substring(indexofSecondColon+1,indexofSecondTab);
				
				
				//Fill keggPathway2NcbiGeneIdHashMap
				//Hash Map does not contain this keggPathwayName
				if(keggPathwayEntry2GeneIdListMap.get(keggPathwayEntry)== null){
					
					List<String> ncbiGeneIdList = new ArrayList<String>();
					ncbiGeneIdList.add(ncbiGeneId);
					keggPathwayEntry2GeneIdListMap.put(keggPathwayEntry, ncbiGeneIdList);
				} 
				//Hash Map contains this keggPathwayName
				else{
					existingNcbiGeneIdList = keggPathwayEntry2GeneIdListMap.get(keggPathwayEntry);
					
					if (!(existingNcbiGeneIdList.contains(ncbiGeneId))){
						existingNcbiGeneIdList.add(ncbiGeneId);
					} else{
						System.out.println("More than one ncbi gene ids for the same kegg pathway");
					}
					
				}
				
			}//End of While
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
	}
	
	
	//example hsa05016
	//augment KeggPathwayEntry with KeggPathwayName
	public static void augmentKeggPathwayEntrywithKeggPathwayName(List<FunctionalElement> list1, List<FunctionalElement> list2,List<FunctionalElement> list3){
		Map<String,String> entry2NameMap = new HashMap<String,String>();
		
		String keggPathwayEntry;
		String keggPathwayName;
		
		List<List<FunctionalElement>> allList = new ArrayList<List<FunctionalElement>>();
		allList.add(list1);
		allList.add(list2);
		allList.add(list3);
		
		fillKeggPathwayEntry2NameMap(entry2NameMap);
		
		for(List<FunctionalElement> list:allList){
			for(FunctionalElement element:list){
				
				keggPathwayEntry = element.getName();
				
				keggPathwayName = entry2NameMap.get(keggPathwayEntry);
				element.setKeggPathwayName(keggPathwayName);	
			}//For each element
		}//For each list
	}
	
		
	
	//all lists start
	//example: hsa05016
	//augment KeggPathwayEntry with KeggPathwayGeneList
	public static void augmentKeggPathwayEntrywithKeggPathwayGeneList(List<FunctionalElement> list1, List<FunctionalElement> list2,List<FunctionalElement> list3){
		
		List<List<FunctionalElement>> allLists = new ArrayList<List<FunctionalElement>>();
		allLists.add(list1);
		allLists.add(list2);
		allLists.add(list3);
		
		Map<String,List<String>> keggPathwayEntry2GeneIdListMap = new HashMap<String,List<String>>();
		Map<String,List<String>> humanGeneId2RefSeqGeneNameListMap = new HashMap<String,List<String>>();
		Map<String,List<String>> humanRefSeqGeneName2AlternateGeneNameListMap = new HashMap<String,List<String>>();
		
		String keggPathwayEntry;
		List<String> keggPathwayGeneIdList;
		List<String> keggPathwayRefSeqGeneNameList;
		List<String> keggPathwayAlternateGeneNameList;
		
		KeggPathwayAugmentation.fillKeggPathwayEntry2GeneIdListMap(keggPathwayEntry2GeneIdListMap);
		HumanGenesAugmentation.fillHumanGeneId2RefSeqGeneNameMap(humanGeneId2RefSeqGeneNameListMap);
		HumanGenesAugmentation.fillHumanRefSeqGeneName2AlternateGeneNameMap(humanRefSeqGeneName2AlternateGeneNameListMap);
		
		for(List<FunctionalElement> list: allLists ){
			for(FunctionalElement element: list){
				keggPathwayEntry = element.getName();
				
				keggPathwayGeneIdList = keggPathwayEntry2GeneIdListMap.get(keggPathwayEntry);
				
				//Initialise the lists for each element
				keggPathwayRefSeqGeneNameList = new ArrayList<String>();
				keggPathwayAlternateGeneNameList= new ArrayList<String>();
				
				HumanGenesAugmentation.augmentGeneIdWithRefSeqGeneName(keggPathwayGeneIdList,keggPathwayRefSeqGeneNameList,humanGeneId2RefSeqGeneNameListMap);
				HumanGenesAugmentation.augmentRefSeqGeneNamewithAlternateGeneName(keggPathwayRefSeqGeneNameList,keggPathwayAlternateGeneNameList,humanRefSeqGeneName2AlternateGeneNameListMap);
				
				element.setKeggPathwayGeneIdList(keggPathwayGeneIdList);
				element.setKeggPathwayRefSeqGeneNameList(keggPathwayRefSeqGeneNameList);
				element.setKeggPathwayAlternateGeneNameList(keggPathwayAlternateGeneNameList);
			}//For each element
		}//For each list
	}
	//all lists end
	
	
	//all lists start
	//example: GABP_hsa05016
	//augment TfNameCellLineNameKeggPathwayEntry with KeggPathwayName
	public static void augmentTfNameKeggPathwayEntrywithKeggPathwayName(List<FunctionalElement> list1, List<FunctionalElement> list2,List<FunctionalElement> list3){
		Map<String,String> entry2NameMap = new HashMap<String,String>();
		
		String keggPathwayEntry;
		String keggPathwayName;
		
		List<List<FunctionalElement>> allList = new ArrayList<List<FunctionalElement>>();
		allList.add(list1);
		allList.add(list2);
		allList.add(list3);
		
		String tfName_keggPathwayEntry;
		int indexofFirstUnderscore;
		
		fillKeggPathwayEntry2NameMap(entry2NameMap);
		
		for(List<FunctionalElement> list:allList){
			for(FunctionalElement element:list){
				
				tfName_keggPathwayEntry = element.getName();
				indexofFirstUnderscore = tfName_keggPathwayEntry.indexOf('_');
				keggPathwayEntry  = tfName_keggPathwayEntry.substring(indexofFirstUnderscore+1);
					
				keggPathwayName = entry2NameMap.get(keggPathwayEntry);
				element.setKeggPathwayName(keggPathwayName);	
			}//For each element
		}//For each list
	}
	//all lists start
	
	//all lists start
	//example: GABP_HELAS3_hsa05016
	//augment TfNameCellLineNameKeggPathwayEntry with KeggPathwayName
	public static void augmentTfNameCellLineNameKeggPathwayEntrywithKeggPathwayName(List<FunctionalElement> list1, List<FunctionalElement> list2,List<FunctionalElement> list3){
		Map<String,String> entry2NameMap = new HashMap<String,String>();
		
		String keggPathwayEntry;
		String keggPathwayName;
		
		List<List<FunctionalElement>> allList = new ArrayList<List<FunctionalElement>>();
		allList.add(list1);
		allList.add(list2);
		allList.add(list3);
		
		String tfName_cellLineName_keggPathwayEntry;
		int indexofFirstUnderscore;
		int indexofSecondUnderscore;
		
		fillKeggPathwayEntry2NameMap(entry2NameMap);
		
		for(List<FunctionalElement> list:allList){
			for(FunctionalElement element:list){
				
				tfName_cellLineName_keggPathwayEntry = element.getName();
				indexofFirstUnderscore = tfName_cellLineName_keggPathwayEntry.indexOf('_');
				indexofSecondUnderscore = tfName_cellLineName_keggPathwayEntry.indexOf('_', indexofFirstUnderscore+1);
				keggPathwayEntry  = tfName_cellLineName_keggPathwayEntry.substring(indexofSecondUnderscore+1);
					
				keggPathwayName = entry2NameMap.get(keggPathwayEntry);
				element.setKeggPathwayName(keggPathwayName);	
			}//For each element
		}//For each list
	}
	//all lists start
		
	
	//all lists start
	//example: GABP_HELAS3_hsa05016
	//augment TfNameCellLineNameKeggPathwayEntry with KeggPathwayGeneList
	public static void augmentTfNameCellLineNameKeggPathwayEntrywithKeggPathwayGeneList(List<FunctionalElement> list1,List<FunctionalElement> list2,List<FunctionalElement> list3){
		
		List<List<FunctionalElement>> allLists = new ArrayList<List<FunctionalElement>>();
		allLists.add(list1);
		allLists.add(list2);
		allLists.add(list3);
		
		Map<String,List<String>> keggPathwayEntry2GeneIdListMap = new HashMap<String,List<String>>();
		Map<String,List<String>> humanGeneId2RefSeqGeneNameListMap = new HashMap<String,List<String>>();
		Map<String,List<String>> humanRefSeqGeneName2AlternateGeneNameListMap = new HashMap<String,List<String>>();
		
		String keggPathwayEntry;
		List<String> keggPathwayGeneIdList;
		List<String> keggPathwayRefSeqGeneNameList;
		List<String> keggPathwayAlternateGeneNameList;
					
		String tfName_cellLineName_keggPathwayEntry;
		int indexofFirstUnderscore;
		int indexofSecondUnderscore;
		
		KeggPathwayAugmentation.fillKeggPathwayEntry2GeneIdListMap(keggPathwayEntry2GeneIdListMap);
		HumanGenesAugmentation.fillHumanGeneId2RefSeqGeneNameMap(humanGeneId2RefSeqGeneNameListMap);
		HumanGenesAugmentation.fillHumanRefSeqGeneName2AlternateGeneNameMap(humanRefSeqGeneName2AlternateGeneNameListMap);
		
		for(List<FunctionalElement> list: allLists){
			for(FunctionalElement element: list){
				
				tfName_cellLineName_keggPathwayEntry = element.getName();
				indexofFirstUnderscore = tfName_cellLineName_keggPathwayEntry.indexOf('_');
				indexofSecondUnderscore = tfName_cellLineName_keggPathwayEntry.indexOf('_', indexofFirstUnderscore+1);
				keggPathwayEntry  = tfName_cellLineName_keggPathwayEntry.substring(indexofSecondUnderscore+1);
				
				keggPathwayGeneIdList = keggPathwayEntry2GeneIdListMap.get(keggPathwayEntry);
				
				//Initialise the lists for each element
				keggPathwayRefSeqGeneNameList = new ArrayList<String>();
				keggPathwayAlternateGeneNameList= new ArrayList<String>();
				
				HumanGenesAugmentation.augmentGeneIdWithRefSeqGeneName(keggPathwayGeneIdList,keggPathwayRefSeqGeneNameList,humanGeneId2RefSeqGeneNameListMap);
				HumanGenesAugmentation.augmentRefSeqGeneNamewithAlternateGeneName(keggPathwayRefSeqGeneNameList,keggPathwayAlternateGeneNameList,humanRefSeqGeneName2AlternateGeneNameListMap);
				
				element.setKeggPathwayGeneIdList(keggPathwayGeneIdList);
				element.setKeggPathwayRefSeqGeneNameList(keggPathwayRefSeqGeneNameList);
				element.setKeggPathwayAlternateGeneNameList(keggPathwayAlternateGeneNameList);
			}
		}
		
		
	}
	//all lists end
	
	
		
		//all lists start
		//example: GABP_hsa05016
		//augment TfNameKeggPathwayEntry with KeggPathwayGeneList
		public static void augmentTfNameKeggPathwayEntrywithKeggPathwayGeneList(List<FunctionalElement> list1,List<FunctionalElement> list2,List<FunctionalElement> list3){
			
			List<List<FunctionalElement>> allLists = new ArrayList<List<FunctionalElement>>();
			allLists.add(list1);
			allLists.add(list2);
			allLists.add(list3);
			
			Map<String,List<String>> keggPathwayEntry2GeneIdListMap = new HashMap<String,List<String>>();
			Map<String,List<String>> humanGeneId2RefSeqGeneNameListMap = new HashMap<String,List<String>>();
			Map<String,List<String>> humanRefSeqGeneName2AlternateGeneNameListMap = new HashMap<String,List<String>>();
			
			String keggPathwayEntry;
			List<String> keggPathwayGeneIdList;
			List<String> keggPathwayRefSeqGeneNameList;
			List<String> keggPathwayAlternateGeneNameList;
						
			String tfName_keggPathwayEntry;
			int indexofFirstUnderscore;
			
			KeggPathwayAugmentation.fillKeggPathwayEntry2GeneIdListMap(keggPathwayEntry2GeneIdListMap);
			HumanGenesAugmentation.fillHumanGeneId2RefSeqGeneNameMap(humanGeneId2RefSeqGeneNameListMap);
			HumanGenesAugmentation.fillHumanRefSeqGeneName2AlternateGeneNameMap(humanRefSeqGeneName2AlternateGeneNameListMap);
			
			for(List<FunctionalElement> list: allLists){
				for(FunctionalElement element: list){
					
					tfName_keggPathwayEntry = element.getName();
					indexofFirstUnderscore = tfName_keggPathwayEntry.indexOf('_');
					keggPathwayEntry  = tfName_keggPathwayEntry.substring(indexofFirstUnderscore+1);
					
					keggPathwayGeneIdList = keggPathwayEntry2GeneIdListMap.get(keggPathwayEntry);
					
					//Initialise the lists for each element
					keggPathwayRefSeqGeneNameList = new ArrayList<String>();
					keggPathwayAlternateGeneNameList= new ArrayList<String>();
					
					HumanGenesAugmentation.augmentGeneIdWithRefSeqGeneName(keggPathwayGeneIdList,keggPathwayRefSeqGeneNameList,humanGeneId2RefSeqGeneNameListMap);
					HumanGenesAugmentation.augmentRefSeqGeneNamewithAlternateGeneName(keggPathwayRefSeqGeneNameList,keggPathwayAlternateGeneNameList,humanRefSeqGeneName2AlternateGeneNameListMap);
					
					element.setKeggPathwayGeneIdList(keggPathwayGeneIdList);
					element.setKeggPathwayRefSeqGeneNameList(keggPathwayRefSeqGeneNameList);
					element.setKeggPathwayAlternateGeneNameList(keggPathwayAlternateGeneNameList);
				}
			}
			
			
		}
		//all lists end
		
	

	/**
	 * 
	 */
	public KeggPathwayAugmentation() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// for testing purposes
		Map<String,List<String>> keggPathwayEntry2GeneIdListMap = new HashMap<String,List<String>>();
		Map<String,List<String>> humanGeneId2RefSeqGeneNameListMap = new HashMap<String,List<String>>();
		Map<String,List<String>> humanRefSeqGeneName2AlternateGeneNameListMap = new HashMap<String,List<String>>();
		
		List<String> keggPathwayGeneIdList;
		List<String> keggPathwayRefSeqGeneNameList = new ArrayList<String>();
		List<String> keggPathwayAlternateGeneNameList = new ArrayList<String>();
		
		
		KeggPathwayAugmentation.fillKeggPathwayEntry2GeneIdListMap(keggPathwayEntry2GeneIdListMap);	
		HumanGenesAugmentation.fillHumanGeneId2RefSeqGeneNameMap(humanGeneId2RefSeqGeneNameListMap);
		HumanGenesAugmentation.fillHumanRefSeqGeneName2AlternateGeneNameMap(humanRefSeqGeneName2AlternateGeneNameListMap);
		
		keggPathwayGeneIdList = keggPathwayEntry2GeneIdListMap.get("hsa00860");
		HumanGenesAugmentation.augmentGeneIdWithRefSeqGeneName(keggPathwayGeneIdList, keggPathwayRefSeqGeneNameList, humanGeneId2RefSeqGeneNameListMap);
		HumanGenesAugmentation.augmentRefSeqGeneNamewithAlternateGeneName(keggPathwayRefSeqGeneNameList, keggPathwayAlternateGeneNameList, humanRefSeqGeneName2AlternateGeneNameListMap);
		
		System.out.println("Stop here");

	}

}
