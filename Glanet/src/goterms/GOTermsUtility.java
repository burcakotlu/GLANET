/**
 * 
 */
package goterms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ui.GlanetRunner;
import auxiliary.FileOperations;
import common.Commons;
import enumtypes.AnnotationType;
import enumtypes.GeneOntologyFunction;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;

/**
 * @author Burçak Otlu
 * @date Feb 11, 2017
 * @project Glanet 
 *
 */
public class GOTermsUtility {
	
	final static Logger logger = Logger.getLogger(GOTermsUtility.class);

	
	public static void fillConsideredGOClasses(
			List<GeneOntologyFunction> consideredGOClassesList,
			AnnotationType bpGOTermsAnnotationType,
			AnnotationType mfGOTermsAnnotationType,
			AnnotationType ccGOTermsAnnotationType){
		
		if(bpGOTermsAnnotationType.doBPGOTermsAnnotation()){
			consideredGOClassesList.add(GeneOntologyFunction.BIOLOGICAL_PROCESS);
		}
		
		if(mfGOTermsAnnotationType.doMFGOTermsAnnotation()){
			consideredGOClassesList.add(GeneOntologyFunction.MOLECULAR_FUNCTION);
		}
		
		if(ccGOTermsAnnotationType.doCCGOTermsAnnotation()){
			consideredGOClassesList.add(GeneOntologyFunction.CELLULAR_COMPONENT);
		}
		
	}
	
	
	
	
	public static void fillMaps(
			TIntObjectMap<TIntList> goTermNumber2AllKMap,
			TIntObjectMap<TIntList> bp_goTermNumber2AllKMap,
			TIntObjectMap<TIntList> mf_goTermNumber2AllKMap,
			TIntObjectMap<TIntList> cc_goTermNumber2AllKMap,
			TIntObjectMap<GeneOntologyFunction> goTermNumber2GeneOntologyFunctionMap,
			List<GeneOntologyFunction> consideredGOClassesList){
		
		int goTermNumber;
		TIntList allAssociationMeasureList;
		GeneOntologyFunction goFunction;
		
		for(TIntObjectIterator<TIntList> itr=goTermNumber2AllKMap.iterator();itr.hasNext();){
			
			itr.advance();
			
			goTermNumber = itr.key();
			allAssociationMeasureList = itr.value();
			
			goFunction = goTermNumber2GeneOntologyFunctionMap.get(goTermNumber);
			
			if (consideredGOClassesList.contains(goFunction)){
				
				switch(goFunction){
				
					case BIOLOGICAL_PROCESS:
						bp_goTermNumber2AllKMap.put(goTermNumber, allAssociationMeasureList);
						break;
					case MOLECULAR_FUNCTION:
						mf_goTermNumber2AllKMap.put(goTermNumber, allAssociationMeasureList);
						break;
					case CELLULAR_COMPONENT:
						cc_goTermNumber2AllKMap.put(goTermNumber, allAssociationMeasureList);
						break;
				
				}//End of switch

				
			}//End of IF
			
			
			
		}//End of for every goTermNumber in goTermNumber2KMap
	
		
	}
	
	
	
	public static void fillMaps(
			TIntIntMap goTermNumber2KMap,
			TIntIntMap bp_goTermNumber2KMap,
			TIntIntMap mf_goTermNumber2KMap,
			TIntIntMap cc_goTermNumber2KMap,
			TIntObjectMap<GeneOntologyFunction> goTermNumber2GeneOntologyFunctionMap,
			List<GeneOntologyFunction> consideredGOClassesList){
		
		int goTermNumber;
		int associationMeasure;
		GeneOntologyFunction goFunction;
		
		for(TIntIntIterator itr=goTermNumber2KMap.iterator();itr.hasNext();){
			
			itr.advance();
			
			goTermNumber = itr.key();
			associationMeasure = itr.value();
			
			goFunction = goTermNumber2GeneOntologyFunctionMap.get(goTermNumber);
			
			if (consideredGOClassesList.contains(goFunction)){
				
				switch(goFunction){
				
					case BIOLOGICAL_PROCESS:
						bp_goTermNumber2KMap.put(goTermNumber, associationMeasure);
						break;
					case MOLECULAR_FUNCTION:
						mf_goTermNumber2KMap.put(goTermNumber, associationMeasure);
						break;
					case CELLULAR_COMPONENT:
						cc_goTermNumber2KMap.put(goTermNumber, associationMeasure);
						break;
				
				}//End of switch

			}//End of IF goFunction is in consideredGOClassesList
			
		}//End of for every goTermNumber in goTermNumber2KMap
		
	}
	
	public static void createNCBIGeneID2ListofGOTermsNumberMap(
			String dataFolder,
			Map<String,List<Integer>> geneSymbol2ListofGeneIDMap,
			TObjectIntMap<String> goTermName2NumberMap,
			TIntObjectMap<TIntList> geneId2GOTermNumberListMap,
			TIntObjectMap<GeneOntologyFunction> goTermNumber2GeneOntologyFunctionMap,
			List<GeneOntologyFunction> consideredGOClassesList){
		
		
		String goTerm_geneSymbol_evidenceCode_ontology_inputFile = dataFolder + System.getProperty("file.separator") + Commons.GOTERM_GENESYMBOL_EVIDENCECODE_ONTOLOGY_INPUT_FILE;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		String goTermName;
		String geneSymbol;
		
		//TODO ENUM
		//String evidenceCode;		
		GeneOntologyFunction goFunction;
		
		int geneID;
		List<Integer> geneIDList = null;
		
		TIntList goTermNumberList = null;
		
		int goTermNumber;
		
		
		
		
		try {
			
			fileReader = FileOperations.createFileReader(goTerm_geneSymbol_evidenceCode_ontology_inputFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine=bufferedReader.readLine())!=null){
				
				//example strline
				//GO:0005783	GIMAP1-GIMAP5	IDA	C

				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
				
				goTermName = strLine.substring(0, indexofFirstTab);
				geneSymbol = strLine.substring(indexofFirstTab+1, indexofSecondTab);
				//We only considered experimental evidence codes
				//evidenceCode = strLine.substring(indexofSecondTab+1, indexofThirdTab);
				goFunction = GeneOntologyFunction.convertStringtoEnum(strLine.substring(indexofThirdTab+1));
				
				

				goTermNumber = goTermName2NumberMap.get(goTermName);
				
				//Fill goTermNumber2GeneOntologyFunctionMap starts
				if(!goTermNumber2GeneOntologyFunctionMap.containsKey(goTermNumber)){
					goTermNumber2GeneOntologyFunctionMap.put(goTermNumber, goFunction);
				}
				//Fill goTermNumber2GeneOntologyFunctionMap ends
				
				
				if(consideredGOClassesList.contains(goFunction)){
					
					//Fill geneId2GOTermNumberListMap starts
					
					geneIDList = geneSymbol2ListofGeneIDMap.get(geneSymbol);
					
					
					if (geneIDList!=null){
						
						for(int i=0; i<geneIDList.size(); i++){
							
							geneID = geneIDList.get(i);
							
							goTermNumberList = geneId2GOTermNumberListMap.get(geneID);
							
							if (goTermNumberList==null){
								
								goTermNumberList = new TIntArrayList();
								goTermNumberList.add(goTermNumber);
								geneId2GOTermNumberListMap.put(geneID,goTermNumberList);
								
							}else{
								
								goTermNumberList.add(goTermNumber);
								
							}

						
						}//End of for each geneID in geneIDList
					
						 
					}//End of IF geneIDList is not null 
					else {
						
						if(GlanetRunner.shouldLog())
							logger.info("There is no geneID for this geneSymbol " + geneSymbol);

					}
					//Fill geneId2GOTermNumberListMap ends
					
				}//End of IF user asked GO class
				
			
														
			}//End of while
			
			//Close
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
		
	}

}
