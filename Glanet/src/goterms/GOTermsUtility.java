/**
 * 
 */
package goterms;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Feb 11, 2017
 * @project Glanet 
 *
 */
public class GOTermsUtility {
	
	
	public static void createNCBIGeneID2ListofGOTermsNumberMap(
			String dataFolder,
			Map<String,List<Integer>> geneSymbol2ListofGeneIDMap,
			TObjectIntMap<String> goTermName2NumberMap,
			TIntObjectMap<TIntList> geneId2GOTermNumberListMap){
		
		
		String goTerm_geneSymbol_evidenceCode_ontology_inputFile = dataFolder + System.getProperty("file.separator") + Commons.GOTERM_GENESYMBOL_EVIDENCECODE_ONTOLOGY_INPUT_FILE;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine;
		
		int indexofFirstTab;
		int indexofSecondTab;
		//int indexofThirdTab;
		
		String goTermName;
		String geneSymbol;
		
		//TODO ENUM
		//String evidenceCode;		
		//TODO ENUM
		//String ontology;
		
		int geneID;
		List<Integer> geneIDList = null;
		
		TIntList goTermNumberList = null;
		
		int goTermNumber;
		
		//TIntList geneIDList = null;
		
		try {
			
			fileReader = FileOperations.createFileReader(goTerm_geneSymbol_evidenceCode_ontology_inputFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine=bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				//indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
				
				goTermName = strLine.substring(0, indexofFirstTab);
				geneSymbol = strLine.substring(indexofFirstTab+1, indexofSecondTab);
				//evidenceCode = strLine.substring(indexofSecondTab+1, indexofThirdTab);
				//ontology = strLine.substring(indexofThirdTab+1);
				
				
				geneIDList = geneSymbol2ListofGeneIDMap.get(geneSymbol);
				
				if (geneIDList!=null){
					
					for(int i=0; i<geneIDList.size(); i++){
						
						geneID = geneIDList.get(i);
						
						goTermNumberList = geneId2GOTermNumberListMap.get(geneID);
						
						if (goTermNumberList==null){
							
							goTermNumberList = new TIntArrayList();
							goTermNumber = goTermName2NumberMap.get(goTermName);						
							goTermNumberList.add(goTermNumber);
							geneId2GOTermNumberListMap.put(geneID,goTermNumberList);
							
						}else{
							
							goTermNumber = goTermName2NumberMap.get(goTermName);								
							goTermNumberList.add(goTermNumber);
							
						}

					
					}//End of for each geneID
				
					 
				}//End of IF geneIDList is not null 
				else {
					System.out.println("There is no geneID for this geneSymbol " + geneSymbol);
				}
														
			}//End of while
			
			//Close
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
	}

}
