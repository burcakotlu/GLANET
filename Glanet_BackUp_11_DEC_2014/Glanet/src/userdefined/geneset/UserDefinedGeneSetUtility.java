/**
 * @author burcakotlu
 * @date Sep 26, 2014 
 * @time 5:06:55 PM
 */
package userdefined.geneset;

import enumtypes.GeneInformationType;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TShortArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.TShortObjectMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Commons;

import augmentation.humangenes.HumanGenesAugmentation;
import auxiliary.FileOperations;
import auxiliary.FunctionalElement;
/**
 * 
 */
public class UserDefinedGeneSetUtility {
	
	
	
	
	public static void updateMap(
			List<Integer> geneInformation2ListofGeneIDs,
			TIntObjectMap<TShortList> geneId2ListofUserDefinedGeneSetNumberMap,
			short currentUserDefinedGeneSetNumber,
			String GO_ID,
			String geneInformation,
			BufferedWriter bufferedWriter){
		
		TShortList userDefinedGeneSetNumberList = null;
		TShortList existingUserDefinedGeneSetNumberList = null;
		
		try {
			for(int geneID:geneInformation2ListofGeneIDs){
				//fill geneId2ListofUserDefinedGenesetHashMap
				//Hash Map does not contain this ncbiGeneId
				if (!geneId2ListofUserDefinedGeneSetNumberMap.containsKey(geneID)){					
						userDefinedGeneSetNumberList = new TShortArrayList();
						userDefinedGeneSetNumberList.add(currentUserDefinedGeneSetNumber);
						geneId2ListofUserDefinedGeneSetNumberMap.put(geneID, userDefinedGeneSetNumberList);
						bufferedWriter.write(GO_ID + "\t" + currentUserDefinedGeneSetNumber + "\t" + geneInformation +"\t" + geneID + System.getProperty("line.separator"));
					}
				//Hash Map contains this geneId
				else{
					existingUserDefinedGeneSetNumberList = geneId2ListofUserDefinedGeneSetNumberMap.get(geneID);
					
					if(!(existingUserDefinedGeneSetNumberList.contains(currentUserDefinedGeneSetNumber))){
						existingUserDefinedGeneSetNumberList.add(currentUserDefinedGeneSetNumber);
						bufferedWriter.write(GO_ID + "\t" + currentUserDefinedGeneSetNumber + "\t" + geneInformation +"\t" + geneID + System.getProperty("line.separator"));
							
					} else{
						bufferedWriter.write(GO_ID + "\t" + currentUserDefinedGeneSetNumber + "\t" + geneInformation +"\t" + geneID + System.getProperty("line.separator"));			
					}
					
					geneId2ListofUserDefinedGeneSetNumberMap.put(geneID, existingUserDefinedGeneSetNumberList);
				}

			}//End of for each geneID in geneInformation2ListofGeneIDs
			
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
 public static  void fillUserDefinedGeneSetID2TermMap(String userDefinedGeneSetOptionalDescriptionInputFile,Map<String,String> ID2TermMap){
	 
	FileReader fileReader;
	BufferedReader bufferedReader;
	
	String strLine;
	int indexofFirstTab;
	
	String ID;
	String term;
	
	try {
		fileReader = new FileReader(userDefinedGeneSetOptionalDescriptionInputFile);
		bufferedReader = new BufferedReader(fileReader);
		
		while((strLine= bufferedReader.readLine())!=null){
			//GO:0000001	mitochondrion inheritance

			indexofFirstTab = strLine.indexOf('\t');
			ID = strLine.substring(0,indexofFirstTab);
			term = strLine.substring(indexofFirstTab+1);
			
			ID = removeIllegalCharacters(ID);
			
			ID2TermMap.put(ID, term);	
			
		}//End of While
		
		bufferedReader.close();
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	 
 }
	
	
   public static void augmentUserDefinedGeneSetIDwithTerm(String userDefinedGeneSetOptionalDescriptionInputFile,List<FunctionalElement> list){
	   
	   	Map<String,String> ID2TermMap = new HashMap<String,String>();
		
		String userDefinedGeneSetID;
		String userDefinedGeneSetTerm;
		
		fillUserDefinedGeneSetID2TermMap(userDefinedGeneSetOptionalDescriptionInputFile,ID2TermMap);
		
		for(FunctionalElement element:list){
			
			userDefinedGeneSetID = element.getName();
			userDefinedGeneSetTerm = ID2TermMap.get(userDefinedGeneSetID);
			
			if (userDefinedGeneSetTerm==null){
				element.setUserDefinedGeneSetDescription(Commons.NO_DESCRIPTION_AVAILABLE);
			}else{
				element.setUserDefinedGeneSetDescription(userDefinedGeneSetTerm);

			}
			
		}//End of for each element
	
	
   }
	
	
	//Pay attention: More characters need to be removed.
	public static String removeIllegalCharacters(String GO_ID){
		GO_ID = GO_ID.trim();
		GO_ID = GO_ID.replace(':', '_');
		GO_ID = GO_ID.replace('.', '_');
	
		return GO_ID;
	}
	
	
	public static void createNcbiGeneId2ListofUserDefinedGeneSetNumberMap(
			String dataFolder,
			String userDefinedGeneSetInputFile, 
			GeneInformationType geneInformationType,
			TObjectShortMap<String> userDefinedGeneSetName2UserDefinedGeneSetNumberMap,
			TShortObjectMap<String> userDefinedGeneSetNumber2UserDefinedGeneSetNameMap,
			TIntObjectMap<TShortList> geneId2ListofUserDefinedGeneSetNumberMap){
		
		//Read the user defined geneset inputFile
		String strLine;
		int indexofFirstTab;
		String geneSetName;
		String geneInformation;
		
		short userDefinedGeneSetNumber = 1;
		short currentUserDefinedGeneSetNumber = Short.MIN_VALUE;
		
		//In case of need: First fill these conversion maps
		Map<String,List<Integer>> geneSymbol2ListofGeneIDMap = null;
		Map<String,List<Integer>> RNANucleotideAccession2ListofGeneIDMap = null;
		
		List<String> listofUnconvertedGeneInformation = new ArrayList<String>();
		List<String> listofGeneInformation =  new ArrayList<String>();
 		
		List<Integer> geneIDList = null;
		
		List<Integer> geneInformation2ListofGeneIDs = new ArrayList<Integer>();
		
		
		//Fill these conversion maps only once starts
		 if(geneInformationType.is_GENE_SYMBOL()){
			geneSymbol2ListofGeneIDMap = new HashMap<String,List<Integer>>();		
			HumanGenesAugmentation.fillGeneSymbol2ListofGeneIDMap(dataFolder,geneSymbol2ListofGeneIDMap);		 
	    }else if (geneInformationType.is_RNA_NUCLEOTIDE_ACCESSION()){
	    	RNANucleotideAccession2ListofGeneIDMap = new HashMap<String,List<Integer>>();	    	
	    	HumanGenesAugmentation.fillRNANucleotideAccession2ListofGeneIdMap(dataFolder,RNANucleotideAccession2ListofGeneIDMap);   
	    }
		//Fill these maps only once ends

	
		//Start reading user defined geneset file
		//This user defined geneset file has to be tab separated file
		//First column genesetName tab second column geneInformation per line
		//Fill name2number and number2name maps here
		try {
			FileReader fileReader = FileOperations.createFileReader(userDefinedGeneSetInputFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			//For debugging purposes
			FileWriter fileWriter = FileOperations.createFileWriter("G:\\DOKTORA_DATA\\GO\\control_GO_gene_associations_human_ref.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			
			while((strLine = bufferedReader.readLine())!=null){
				
				
				indexofFirstTab = strLine.indexOf('\t');
				geneSetName = strLine.substring(0,indexofFirstTab);
				
				geneSetName = removeIllegalCharacters(geneSetName);
				
				//geneInformation can be geneID, geneSymbol or RNANucleotideAccession
				geneInformation = strLine.substring(indexofFirstTab+1);
				
				//For debugging purposes
				//To get number of all unique geneInformation
				if (!listofGeneInformation.contains(geneInformation)){
					listofGeneInformation.add(geneInformation);
				}
				
				//Fill name2number and number2name maps starts
				if (!(userDefinedGeneSetName2UserDefinedGeneSetNumberMap.containsKey(geneSetName))){
					userDefinedGeneSetName2UserDefinedGeneSetNumberMap.put(geneSetName, userDefinedGeneSetNumber);
					userDefinedGeneSetNumber2UserDefinedGeneSetNameMap.put(userDefinedGeneSetNumber, geneSetName);
					
					//Increment UserDefinedGeneSetNumber
					userDefinedGeneSetNumber++;
					
				}//End of IF
				//Fill name2number and number2name maps ends
	
				
				//Get the current userDefinedGeneSet number
				currentUserDefinedGeneSetNumber = userDefinedGeneSetName2UserDefinedGeneSetNumberMap.get(geneSetName);
				
				//For each readLine
				geneInformation2ListofGeneIDs.clear();
				
				if (geneInformationType.is_GENE_SYMBOL()){
					
					//geneInformation contains geneSymbol
					//Convert geneSymbol to geneID
					geneIDList = geneSymbol2ListofGeneIDMap.get(geneInformation);
					
					if(geneIDList!=null){							
						for(int geneID: geneIDList){
														
							if(!geneInformation2ListofGeneIDs.contains(geneID)){
								geneInformation2ListofGeneIDs.add(geneID);
							}
							
						}//End of For: each geneID in the geneIDList
					}//End of IF: geneIDList is not null	
							
					
					//No conversion is possible
					if (geneInformation2ListofGeneIDs.isEmpty()){
						if (!listofUnconvertedGeneInformation.contains(geneInformation)){
							listofUnconvertedGeneInformation.add(geneInformation);
						}
						bufferedWriter.write(geneSetName + "\t" + currentUserDefinedGeneSetNumber + "\t" + geneInformation +"\t" + null + System.getProperty("line.separator"));
					}//End of IF: No conversion is possible
					
					updateMap(geneInformation2ListofGeneIDs,geneId2ListofUserDefinedGeneSetNumberMap,currentUserDefinedGeneSetNumber,geneSetName,geneInformation,bufferedWriter);									
					
				}else if (geneInformationType.is_RNA_NUCLEOTIDE_ACCESSION()){
					//geneInformation contains RNANucleotideAccession
					//Convert RNANucleotideAccession to geneID
					
					geneIDList = RNANucleotideAccession2ListofGeneIDMap.get(geneInformation);
					
					if (geneIDList!=null){
						for(Integer geneID: geneIDList){
							if(!geneInformation2ListofGeneIDs.contains(geneID)){
								geneInformation2ListofGeneIDs.add(geneID);
							}
						}//End of For: each geneID in the geneIDList
					}//End of IF: geneIDList is not null
					
					//No conversion is possible
					if (geneInformation2ListofGeneIDs.isEmpty()){
						if (!listofUnconvertedGeneInformation.contains(geneInformation)){
							listofUnconvertedGeneInformation.add(geneInformation);
						}
						bufferedWriter.write(geneSetName + "\t" + currentUserDefinedGeneSetNumber + "\t" + geneInformation +"\t" + null + System.getProperty("line.separator"));
					}//End of IF: No conversion is possible
					
					updateMap(geneInformation2ListofGeneIDs,geneId2ListofUserDefinedGeneSetNumberMap,currentUserDefinedGeneSetNumber,geneSetName,geneInformation,bufferedWriter);		
					
				}else if (geneInformationType.is_GENE_ID()){
					//geneInformation contains geneID
					//No conversion is needed
					int geneID = Integer.parseInt(geneInformation);
					geneInformation2ListofGeneIDs.add(geneID);
					updateMap(geneInformation2ListofGeneIDs,geneId2ListofUserDefinedGeneSetNumberMap,currentUserDefinedGeneSetNumber,geneSetName,geneInformation,bufferedWriter);
					
				}
							
//				System.out.println("Number of Read Lines: " + numberofReadLines);
							
			}//End of While
					
				
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
