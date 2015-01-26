/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package keggpathway.ncbigenes;

import gnu.trove.list.TShortList;
import gnu.trove.list.array.TShortArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectShortMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;


public class KeggPathwayUtility {
	
	public void readKeggPathwayHsaList(String fileName, Set<KeggPathway> keggPathwaySet ){
		
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab 	= -1;
		int indexofSecondTab 	= -1;
		int indexofFirstColon 		= -1;
		int indexofSecondColon 		= -1;
		
		String keggPathwayName;
		String ncbiGeneId;
		
		List<String> keggPathwayNameList = new ArrayList<String>();
		
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				keggPathwayName = strLine.substring(0,indexofFirstTab);
				ncbiGeneId = strLine.substring(indexofFirstTab+1,indexofSecondTab);
				
				indexofFirstColon = keggPathwayName.indexOf(':');
				indexofSecondColon = ncbiGeneId.indexOf(':');
				
				keggPathwayName = keggPathwayName.substring(indexofFirstColon+1);				
				ncbiGeneId = ncbiGeneId.substring(indexofSecondColon+1);
				
				if(!(keggPathwayNameList.contains(keggPathwayName))){
					
					keggPathwayNameList.add(keggPathwayName);
					
					List<String> geneIdList = new ArrayList<String>();
					
					KeggPathway keggPathway = new KeggPathway(keggPathwayName,geneIdList);
					
					keggPathway.getGeneIdList().add(ncbiGeneId);
					
					keggPathwaySet.add(keggPathway);
					
				} else{
//					Find the already existing keggPathway object by iterating
					Iterator<KeggPathway> itr = keggPathwaySet.iterator();
					
					while(itr.hasNext()){
						KeggPathway keggPathway = (KeggPathway) itr.next();
						if (keggPathway.getKeggPathwayName().equals(keggPathwayName)){
							keggPathway.getGeneIdList().add(ncbiGeneId);
						}
					}
					
				}
				
				
				
				
			} // End of While
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}					
	}
	
	
//	public void writeKeggPathway2NcbiGeneIdFiles(Set<KeggPathway> keggPathwaySet){
//		FileWriter fileWriter = null;
//		BufferedWriter bufferedWriter = null;
//		
//		Iterator<KeggPathway> itr = keggPathwaySet.iterator();
//		try {
//
//			while(itr.hasNext()){
//				KeggPathway keggPathway = (KeggPathway)itr.next();
//				fileWriter = new FileWriter(Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_OUTPUT_FILE_PATH + keggPathway.getKeggPathwayName() + "_to_NcbiGeneList.txt");
//				bufferedWriter = new BufferedWriter(fileWriter);
//				
//				for(int i = 0; i<keggPathway.getGeneIdList().size(); i++){
//					bufferedWriter.write(keggPathway.getGeneIdList().get(i)+ System.getProperty("line.separator"));
//					bufferedWriter.flush();
//				}
//			}
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		try {
//			bufferedWriter.close();
//			fileWriter.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}								
//	}
	
//	public void writeAllPossibleKeggPathwayNamesFile(Set<KeggPathway> keggPathwaySet){
//		FileWriter fileWriter = null;
//		BufferedWriter bufferedWriter = null;
//		
//		Iterator<KeggPathway> itr = keggPathwaySet.iterator();
//		try {
//
//			fileWriter = new FileWriter(Commons.ALL_POSSIBLE_KEGG_PATHWAY_NAMES_OUTPUT_FILE);
//			bufferedWriter = new BufferedWriter(fileWriter);
//		
//			while(itr.hasNext()){
//				KeggPathway keggPathway = (KeggPathway)itr.next();
//								
//				bufferedWriter.write(keggPathway.getKeggPathwayName()+ System.getProperty("line.separator"));
//				bufferedWriter.flush();				
//			}
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		try {
//			bufferedWriter.close();
//			fileWriter.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}									
//	}
	
	
	public static void fillHashMap(Map<String,Integer> hashMap, String inputFileName){
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(inputFileName);			
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null) {
				hashMap.put(strLine, Commons.ZERO);
				
				strLine = null;
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	
	public static void fillKeggPathwayList(List<KeggPathway> keggPathwayList, Map<String,Integer> keggPathwayNameHashMap){
		String keggPathwayName = null;
		
		// assumption is keggPathwayNameHashMap contains unique kegg Pathway names
		Set<String> keggPathwayNameSet = keggPathwayNameHashMap.keySet();
		Iterator<String> itr = keggPathwayNameSet.iterator();
		
		while (itr.hasNext()){
			keggPathwayName = (String)itr.next();
			List<String> keggPathway2NcbiGeneIdsList = new ArrayList<String>();			
			keggPathway2NcbiGeneIdsList.clear();
			
			convertKeggPathway2NcbiGeneIdList(keggPathwayName, Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, keggPathway2NcbiGeneIdsList);
			
			KeggPathway keggPathway = new KeggPathway(keggPathwayName, keggPathway2NcbiGeneIdsList);
			
			keggPathwayList.add(keggPathway);			

		}
	}
	
	public static void convertKeggPathway2NcbiGeneIdList(String givenKeggPathwayName, String keggPathway2NcbiGeneIdsInputFileName, List<String> keggPathway2NcbiGeneIdsList){
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab 	= -1;
		int indexofSecondTab 	= -1;
		int indexofFirstColon 		= -1;
		int indexofSecondColon 		= -1;
		
		String keggPathwayName;
		String ncbiGeneId;
		
		try {
			fileReader = new FileReader(keggPathway2NcbiGeneIdsInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){

				indexofFirstTab 	= strLine.indexOf('\t');
				indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
				
				keggPathwayName 	= strLine.substring(0,indexofFirstTab);
				indexofFirstColon 	= keggPathwayName.indexOf(':');
				keggPathwayName 	= keggPathwayName.substring(indexofFirstColon+1);								
				
				if(givenKeggPathwayName.equals(keggPathwayName)){
					
					ncbiGeneId = strLine.substring(indexofFirstTab+1,indexofSecondTab);
					indexofSecondColon = ncbiGeneId.indexOf(':');					
					ncbiGeneId = ncbiGeneId.substring(indexofSecondColon+1);
					 
//					Add only unique ncbi gene id 
					if(!(keggPathway2NcbiGeneIdsList.contains(ncbiGeneId))){
						keggPathway2NcbiGeneIdsList.add(ncbiGeneId);
					}				
				}																
			} // End of While
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static void fillKeggPathway2NcbiGeneIdList(Map<String,Integer> keggPathwayNameHashMap, String keggPathway2NcbiGeneIdsInputFileName, List<String> allKeggPathways2NcbiGeneIdsList){
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab 	= -1;
		int indexofSecondTab 	= -1;
		int indexofFirstColon 		= -1;
		int indexofSecondColon 		= -1;
		
		String keggPathwayName;
		String ncbiGeneId;
		
		try {
			fileReader = new FileReader(keggPathway2NcbiGeneIdsInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){

				indexofFirstTab 	= strLine.indexOf('\t');
				indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
				
				keggPathwayName 	= strLine.substring(0,indexofFirstTab);
				indexofFirstColon 	= keggPathwayName.indexOf(':');
				keggPathwayName 	= keggPathwayName.substring(indexofFirstColon+1);				
				
				
				if(keggPathwayNameHashMap.containsKey(keggPathwayName)){
					
					ncbiGeneId = strLine.substring(indexofFirstTab+1,indexofSecondTab);
					indexofSecondColon = ncbiGeneId.indexOf(':');					
					ncbiGeneId = ncbiGeneId.substring(indexofSecondColon+1);
					 
//					Add only unique ncbi gene id 
					if(!(allKeggPathways2NcbiGeneIdsList.contains(ncbiGeneId))){
						allKeggPathways2NcbiGeneIdsList.add(ncbiGeneId);
					}				
				}																
			} // End of While
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/*
	 *  pathway_hsa.list file is read.
	 *  Map<String,List<String>> keggPathway2GeneIdHashMap and 
	 *  Map<String,List<String>> geneId2KeggPathwayHashMap will be created.
	 * 
	 */
	public static void readKeggPathwayHsaListAndCreateHashMaps(String fileName, Map<String,List<String>> keggPathway2NcbiGeneIdHashMap, Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap){
		
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab 	= -1;
		int indexofSecondTab 	= -1;
		int indexofFirstColon 		= -1;
		int indexofSecondColon 		= -1;
		
		String keggPathwayName;
		String ncbiGeneId;
		
		List<String> existingNcbiGeneIdList = null;
		List<String> existingKeggPathwayList = null;
		
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example line
				//path:hsa00010	hsa:10327	reverse

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				keggPathwayName = strLine.substring(0,indexofFirstTab);
				ncbiGeneId = strLine.substring(indexofFirstTab+1,indexofSecondTab);
				
				indexofFirstColon = keggPathwayName.indexOf(':');
				indexofSecondColon = ncbiGeneId.indexOf(':');
				
				keggPathwayName = keggPathwayName.substring(indexofFirstColon+1);				
				ncbiGeneId = ncbiGeneId.substring(indexofSecondColon+1);
				
				//Fill keggPathway2NcbiGeneIdHashMap
				//Hash Map does not contain this keggPathwayName
				if(keggPathway2NcbiGeneIdHashMap.get(keggPathwayName)== null){
					
					List<String> ncbiGeneIdList = new ArrayList<String>();
					ncbiGeneIdList.add(ncbiGeneId);
					keggPathway2NcbiGeneIdHashMap.put(keggPathwayName, ncbiGeneIdList);
				} 
				//Hash Map contains this keggPathwayName
				else{
					existingNcbiGeneIdList = keggPathway2NcbiGeneIdHashMap.get(keggPathwayName);
					
					if (!(existingNcbiGeneIdList.contains(ncbiGeneId))){
						existingNcbiGeneIdList.add(ncbiGeneId);
					} else{
						GlanetRunner.appendLog("More than one same ncbi gene ids for the same kegg pathway");
					}
					
					keggPathway2NcbiGeneIdHashMap.put(keggPathwayName, existingNcbiGeneIdList);
				}
				
				
				//fill ncbiGeneId2KeggPathwayHashMap
				//Hash Map does not contain this ncbiGeneId
				if (ncbiGeneId2KeggPathwayHashMap.get(ncbiGeneId)==null){					
					List<String> keggPathwayList = new ArrayList<String>();
					keggPathwayList.add(keggPathwayName);
					ncbiGeneId2KeggPathwayHashMap.put(ncbiGeneId, keggPathwayList);
					}
				//Hash Map contains this ncbiGeneId
				else{
					existingKeggPathwayList = ncbiGeneId2KeggPathwayHashMap.get(ncbiGeneId);
					
					if(!(existingKeggPathwayList.contains(keggPathwayName))){
						existingKeggPathwayList.add(keggPathwayName);
					} else{
						GlanetRunner.appendLog("More than one same kegg pathway for the same ncbi gene id");
					}
					
					ncbiGeneId2KeggPathwayHashMap.put(ncbiGeneId, existingKeggPathwayList);
				}
				
					
				
			} // End of While
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}					
	
	}

	//@todo Trove library usage starts
	public static void fillKeggPathwayName2KeggPathwayNumberMap(String dataFolder, String direcyoryName, String inputFileName, TObjectShortMap<String> keggPathwayName2KeggPathwayNumber){
		
		String strLine;
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		String keggPathwayName;
		short keggPathwayNumber;
		int indexofFirstTab;
		
		try {
			fileReader = FileOperations.createFileReader(dataFolder + direcyoryName + inputFileName);			
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				
				keggPathwayName = strLine.substring(0,indexofFirstTab);
				keggPathwayNumber = Short.parseShort(strLine.substring(indexofFirstTab+1));
				
				keggPathwayName2KeggPathwayNumber.put(keggPathwayName, keggPathwayNumber);
				
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	//@todo Trove library usage ends
	
	//@todo
	public static void fillKeggPathwayName2KeggPathwayNumberMap(String dataFolder, String direcyoryName, String inputFileName, Map<String,Short> keggPathwayName2KeggPathwayNumber){
		
		String strLine;
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		String keggPathwayName;
		short keggPathwayNumber;
		int indexofFirstTab;
		
		try {
			fileReader = FileOperations.createFileReader(dataFolder + direcyoryName + inputFileName);			
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				
				keggPathwayName = strLine.substring(0,indexofFirstTab);
				keggPathwayNumber = Short.parseShort(strLine.substring(indexofFirstTab+1));
				
				keggPathwayName2KeggPathwayNumber.put(keggPathwayName, keggPathwayNumber);
				
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	//@todo
	
	
	//@todo Trove Library usage starts
	public static void createNcbiGeneId2KeggPathwayNumberMap(String dataFolder,String fileName, TIntObjectMap<TShortList> ncbiGeneId2KeggPathwayNumberHashMap,TObjectShortMap<String> keggPathwayName2KeggPathwayNumberMap){
		
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab 	= -1;
		int indexofSecondTab 	= -1;
		int indexofFirstColon 		= -1;
		int indexofSecondColon 		= -1;
		
		String keggPathwayEntry;
		Short keggPathwayNumber;
		String ncbiGeneIdString;
		Integer ncbiGeneId;
		
		
		TShortList  existingKeggPathwayList = null;
		
		try {
			fileReader = new FileReader(dataFolder + fileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example line
				//path:hsa00010	hsa:10327	reverse

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				keggPathwayEntry = strLine.substring(0,indexofFirstTab);
				ncbiGeneIdString = strLine.substring(indexofFirstTab+1,indexofSecondTab);
				
				indexofFirstColon = keggPathwayEntry.indexOf(':');
				indexofSecondColon = ncbiGeneIdString.indexOf(':');
				
				keggPathwayEntry = keggPathwayEntry.substring(indexofFirstColon+1);				
				ncbiGeneIdString = ncbiGeneIdString.substring(indexofSecondColon+1);
				
				keggPathwayNumber = keggPathwayName2KeggPathwayNumberMap.get(keggPathwayEntry);
				ncbiGeneId =Integer.parseInt(ncbiGeneIdString);
				
				//fill ncbiGeneId2KeggPathwayHashMap
				//Hash Map does not contain this ncbiGeneId
				if (ncbiGeneId2KeggPathwayNumberHashMap.get(ncbiGeneId)==null){					
					TShortList keggPathwayNumberList = new TShortArrayList();
					keggPathwayNumberList.add(keggPathwayNumber);
					ncbiGeneId2KeggPathwayNumberHashMap.put(ncbiGeneId, keggPathwayNumberList);
					}
				//Hash Map contains this ncbiGeneId
				else{
					existingKeggPathwayList = ncbiGeneId2KeggPathwayNumberHashMap.get(ncbiGeneId);
					
					if(!(existingKeggPathwayList.contains(keggPathwayNumber))){
						existingKeggPathwayList.add(keggPathwayNumber);
					} else{
						GlanetRunner.appendLog("More than one same kegg pathway for the same ncbi gene id");
					}
					
					ncbiGeneId2KeggPathwayNumberHashMap.put(ncbiGeneId, existingKeggPathwayList);		
				}
								
			} // End of While
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}					
	
	}
	//@todo Trove Library usage ends
	
	//@todo
	public static void createNcbiGeneId2KeggPathwayNumberMap(String dataFolder,String fileName, TIntObjectMap<TShortList> ncbiGeneId2KeggPathwayNumberHashMap,Map<String,Short> keggPathwayEntry2KeggPathwayNumberMap){
		
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab 	= -1;
		int indexofSecondTab 	= -1;
		int indexofFirstColon 		= -1;
		int indexofSecondColon 		= -1;
		
		String keggPathwayEntry;
		Short keggPathwayNumber;
		String ncbiGeneIdString;
		Integer ncbiGeneId;
		
		
		TShortList  existingKeggPathwayList = null;
		
		try {
			fileReader = new FileReader(dataFolder + fileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example line
				//path:hsa00010	hsa:10327	reverse

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				keggPathwayEntry = strLine.substring(0,indexofFirstTab);
				ncbiGeneIdString = strLine.substring(indexofFirstTab+1,indexofSecondTab);
				
				indexofFirstColon = keggPathwayEntry.indexOf(':');
				indexofSecondColon = ncbiGeneIdString.indexOf(':');
				
				keggPathwayEntry = keggPathwayEntry.substring(indexofFirstColon+1);				
				ncbiGeneIdString = ncbiGeneIdString.substring(indexofSecondColon+1);
				
				keggPathwayNumber = keggPathwayEntry2KeggPathwayNumberMap.get(keggPathwayEntry);
				ncbiGeneId =Integer.parseInt(ncbiGeneIdString);
				
				//fill ncbiGeneId2KeggPathwayHashMap
				//Hash Map does not contain this ncbiGeneId
				if (ncbiGeneId2KeggPathwayNumberHashMap.get(ncbiGeneId)==null){					
					TShortList keggPathwayNumberList = new TShortArrayList();
					keggPathwayNumberList.add(keggPathwayNumber);
					ncbiGeneId2KeggPathwayNumberHashMap.put(ncbiGeneId, keggPathwayNumberList);
					}
				//Hash Map contains this ncbiGeneId
				else{
					existingKeggPathwayList = ncbiGeneId2KeggPathwayNumberHashMap.get(ncbiGeneId);
					
					if(!(existingKeggPathwayList.contains(keggPathwayNumber))){
						existingKeggPathwayList.add(keggPathwayNumber);
					} else{
						GlanetRunner.appendLog("More than one same kegg pathway for the same ncbi gene id");
					}
					
					ncbiGeneId2KeggPathwayNumberHashMap.put(ncbiGeneId, existingKeggPathwayList);		
				}
								
			} // End of While
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}					
	
	}
	//@todo
	
	
	//@todo ncbiGeneId2ListofKeggPathwayNumber starts
	/*
	 *  pathway_hsa.list file is read.
	 *  Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap will be created.
	 * 
	 */
	public static void createNcbiGeneId2ListofKeggPathwayNumberMap(
			String dataFolder,
			String fileName, 
			TObjectShortMap<String> keggPathwayName2KeggPathwayNumberMap, 
			TIntObjectMap<TShortList> ncbiGeneId2ListofKeggPathwayNumberHashMap){
		
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab 	= -1;
		int indexofSecondTab 	= -1;
		int indexofFirstColon 		= -1;
		int indexofSecondColon 		= -1;
		
		String keggPathwayName;
		String ncbiGeneIdName;
		
		short keggPathwayNumber;
		int ncbiGeneId;
		
		TShortList existingKeggPathwayNumberList = null;
		
		try {
			fileReader = new FileReader(dataFolder + fileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example line
				//path:hsa00010	hsa:10327	reverse

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				keggPathwayName = strLine.substring(0,indexofFirstTab);
				ncbiGeneIdName = strLine.substring(indexofFirstTab+1,indexofSecondTab);
				
				indexofFirstColon = keggPathwayName.indexOf(':');
				indexofSecondColon = ncbiGeneIdName.indexOf(':');
				
				keggPathwayName = keggPathwayName.substring(indexofFirstColon+1);				
				ncbiGeneId = Integer.parseInt(ncbiGeneIdName.substring(indexofSecondColon+1));
				
				//What if it is null? check this situation
				keggPathwayNumber = keggPathwayName2KeggPathwayNumberMap.get(keggPathwayName);
				
				//fill ncbiGeneId2KeggPathwayHashMap
				//Hash Map does not contain this ncbiGeneId
				if (!ncbiGeneId2ListofKeggPathwayNumberHashMap.containsKey(ncbiGeneId)){					
					TShortArrayList keggPathwayNumberList = new TShortArrayList();
					keggPathwayNumberList.add(keggPathwayNumber);
					ncbiGeneId2ListofKeggPathwayNumberHashMap.put(ncbiGeneId, keggPathwayNumberList);
					}
				//Hash Map contains this ncbiGeneId
				else{
					existingKeggPathwayNumberList = ncbiGeneId2ListofKeggPathwayNumberHashMap.get(ncbiGeneId);
					
					if(!(existingKeggPathwayNumberList.contains(keggPathwayNumber))){
						existingKeggPathwayNumberList.add(keggPathwayNumber);
					} else{
						GlanetRunner.appendLog("More than one same kegg pathway for the same ncbi gene id");
					}
					
					ncbiGeneId2ListofKeggPathwayNumberHashMap.put(ncbiGeneId, existingKeggPathwayNumberList);
		
				}
			} // End of While
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}					
	
	}
	//@todo ncbiGeneId2ListofKeggPathwayNumber ends
	
	/*
	 *  pathway_hsa.list file is read.
	 *  Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap will be created.
	 * 
	 */
	public static void createNcbiGeneId2KeggPathwayMap(String dataFolder,String fileName, Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap){
		
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab 	= -1;
		int indexofSecondTab 	= -1;
		int indexofFirstColon 		= -1;
		int indexofSecondColon 		= -1;
		
		String keggPathwayName;
		String ncbiGeneId;
		
		List<String> existingKeggPathwayList = null;
		
		try {
			fileReader = new FileReader(dataFolder + fileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example line
				//path:hsa00010	hsa:10327	reverse

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				keggPathwayName = strLine.substring(0,indexofFirstTab);
				ncbiGeneId = strLine.substring(indexofFirstTab+1,indexofSecondTab);
				
				indexofFirstColon = keggPathwayName.indexOf(':');
				indexofSecondColon = ncbiGeneId.indexOf(':');
				
				keggPathwayName = keggPathwayName.substring(indexofFirstColon+1);				
				ncbiGeneId = ncbiGeneId.substring(indexofSecondColon+1);
				
				//fill ncbiGeneId2KeggPathwayHashMap
				//Hash Map does not contain this ncbiGeneId
				if (ncbiGeneId2KeggPathwayHashMap.get(ncbiGeneId)==null){					
					List<String> keggPathwayList = new ArrayList<String>();
					keggPathwayList.add(keggPathwayName);
					ncbiGeneId2KeggPathwayHashMap.put(ncbiGeneId, keggPathwayList);
					}
				//Hash Map contains this ncbiGeneId
				else{
					existingKeggPathwayList = ncbiGeneId2KeggPathwayHashMap.get(ncbiGeneId);
					
					if(!(existingKeggPathwayList.contains(keggPathwayName))){
						existingKeggPathwayList.add(keggPathwayName);
					} else{
						GlanetRunner.appendLog("More than one same kegg pathway for the same ncbi gene id");
					}
					
					ncbiGeneId2KeggPathwayHashMap.put(ncbiGeneId, existingKeggPathwayList);
		
				}
			} // End of While
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}					
	
	}

	
	public static  void main(String[] args){
		
		Set<KeggPathway> keggPathwaySet = new HashSet<KeggPathway>();
		
		KeggPathwayUtility keggPathwayUtility = new KeggPathwayUtility();
		keggPathwayUtility.readKeggPathwayHsaList(Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, keggPathwaySet);
//		keggPathwayUtility.writeKeggPathway2NcbiGeneIdFiles(keggPathwaySet);
//		keggPathwayUtility.writeAllPossibleKeggPathwayNamesFile(keggPathwaySet);
		
		
		Map<String,List<String>> keggPathway2GeneIdHashMap = new HashMap<String, List<String>>();
		Map<String,List<String>> geneId2KeggPathwayHashMap = new HashMap<String, List<String>>();
		
		KeggPathwayUtility.readKeggPathwayHsaListAndCreateHashMaps(Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, keggPathway2GeneIdHashMap, geneId2KeggPathwayHashMap);
	}

}
