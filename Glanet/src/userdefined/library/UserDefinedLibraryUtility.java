/**
 * 
 */
package userdefined.library;

import enumtypes.ChromosomeName;
import enumtypes.GeneratedMixedNumberDescriptionOrderLength;
import enumtypes.UserDefinedLibraryDataFormat;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import annotation.WriteAllPossibleNamesandUnsortedFilesWithNumbers;
import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Burçak
 *
 */
public class UserDefinedLibraryUtility {
	
	//Fill the first argument using the second argument
	//Fill target from source
	public static void fillElementTypeNumberBasedMaps(TIntObjectMap<TIntObjectMap<TIntList>> elementTypeNumber2ElementNumber2AllKMap, TIntObjectMap<TIntList> elementTypeNumberElementNumber2AllKMap){
		
		int elementTypeNumberElementNumber;
		int elementTypeNumber;
		int elementNumber;
		TIntList existingAllK;
		
		for(TIntObjectIterator<TIntList> it = elementTypeNumberElementNumber2AllKMap.iterator();it.hasNext();){
			
			it.advance();
			
			elementTypeNumberElementNumber = it.key();
			existingAllK = it.value();
			
			elementTypeNumber = IntervalTree.getElementTypeNumber(elementTypeNumberElementNumber, GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
			elementNumber = IntervalTree.getElementNumber(elementTypeNumberElementNumber, GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
		
			TIntObjectMap<TIntList> elementNumber2AllKMap = elementTypeNumber2ElementNumber2AllKMap.get(elementTypeNumber);
			
			//Now we initialize TIntList
			TIntList allK = new TIntArrayList();
			
			//Fill allK
			for(TIntIterator it2 =existingAllK.iterator(); it2.hasNext();){
				allK.add(it2.next());
			}
			
			elementNumber2AllKMap.put(elementNumber, allK);
		}
	}
	
	
	//Fill the first argument using the second argument
	//Fill elementTypeNumber2ElementNumber2KMap using originalElementTypeNumberElementNumber2KMap
	public static void fillElementTypeNumberBasedMaps(TIntObjectMap<TIntIntMap> elementTypeNumber2ElementNumber2KMap, TIntIntMap originalElementTypeNumberElementNumber2KMap){
		
		int elementTypeNumberElementNumber;
		int elementTypeNumber;
		int elementNumber;
		int numberofOverlaps;
		
		//For each elementTypeNumberElementNumber
		for(TIntIntIterator it = originalElementTypeNumberElementNumber2KMap.iterator();it.hasNext();){

			it.advance();
			
			elementTypeNumberElementNumber = it.key();
			numberofOverlaps = it.value();
			
			elementTypeNumber = IntervalTree.getElementTypeNumber(elementTypeNumberElementNumber, GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
			elementNumber = IntervalTree.getElementNumber(elementTypeNumberElementNumber, GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
			
			TIntIntMap elementNumber2KMap = elementTypeNumber2ElementNumber2KMap.get(elementTypeNumber);
			
			elementNumber2KMap.put(elementNumber, numberofOverlaps);
		}//End of for each elementTypeNumberElementNumber
			
	}
	
	
	
	
	public static void fillNumber2NameMap(
			TIntObjectMap<String> number2NameMap,
			String		dataFolder,
			String directoryName,
			String fileName){

		
		String strLine;
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		int elementTypeNumber;
		String elementType;
		int indexofFirstTab;
		
		try {
			fileReader = FileOperations.createFileReader(dataFolder + directoryName + fileName);			
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				
				elementTypeNumber = Integer.parseInt(strLine.substring(0,indexofFirstTab));
				elementType = strLine.substring(indexofFirstTab+1);
				
				number2NameMap.put(elementTypeNumber, elementType);
				
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void readFileAndWriteElementTypeBasedChromosomeBasedUnsortedFilesWithNumbers(
			String filePathFileName, 
			String fileName,
			UserDefinedLibraryDataFormat userDefinedLibraryDataFormat,
			TObjectIntMap<String> fileName2FileNumberMap,
			int elementTypeNumber,
			TObjectIntMap<String> elementType2ElementTypeNumberMap,
			String elementName, 
			TIntObjectMap<TObjectIntMap<String>> elementTypeNumber2ElementName2ElementNumberMapMap,
			TIntObjectMap<List<BufferedWriter>> elementTypeNumber2BufferedWriterList){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		String chrName = null;
		int start = Integer.MIN_VALUE;
		int end = Integer.MIN_VALUE;
		
		BufferedWriter bufferedWriter = null;
		List<BufferedWriter> bufferedWriterList = null;
		
		
		TObjectIntMap<String> elementName2ElementNumberMap = null;
		
		try {
			
			//Read each file written in UserDefinedLibraryInputFile
			fileReader = FileOperations.createFileReader(filePathFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine= bufferedReader.readLine())!=null){
				
				//example strLine 
				//chr1	91852781	91853156	1	1	.	1728.25	7.867927	7.867927	187
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = (indexofFirstTab>0) ? strLine.indexOf('\t',indexofFirstTab+1) : -1;
				indexofThirdTab = (indexofSecondTab>0) ? strLine.indexOf('\t',indexofSecondTab+1) : -1;
				
				if (indexofFirstTab>0){
					chrName = strLine.substring(0, indexofFirstTab);
				}
				
				if(indexofFirstTab>0 && indexofSecondTab>0){
					start =Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				}
				
				if(indexofSecondTab>0 && indexofThirdTab>0){
					end = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				}else if (indexofSecondTab>0){
					end = Integer.parseInt(strLine.substring(indexofSecondTab+1));			
				}
				
				
				//starts
				switch(userDefinedLibraryDataFormat){
					case USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE: 
						end = end-1; 
						break;
					case USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE: 
						break;
					case USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE:  
						start = start-1; 
						end= end-2;
						break;
					case USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE: 
						start = start-1; 
						end = end-1; 
						break;
				}
				//ends
				
				//Get the bufferedWriterList for a certain elementTypeNumber
				bufferedWriterList = elementTypeNumber2BufferedWriterList.get(elementTypeNumber);
				
				//Get elementName2ElementNumberMap for a certain elementTypeNumber
				elementName2ElementNumberMap = elementTypeNumber2ElementName2ElementNumberMapMap.get(elementTypeNumber);
				
				bufferedWriter = FileOperations.getChromosomeBasedBufferedWriter(ChromosomeName.convertStringtoEnum(chrName),bufferedWriterList);
				bufferedWriter.write(chrName+ "\t" + start + "\t" + end + "\t" +  elementTypeNumber + "\t"+ elementName2ElementNumberMap.get(elementName) + "\t" + fileName2FileNumberMap.get(fileName) + System.getProperty("line.separator") );
			}
					
			//Close each file written in UserDefinedLibraryInputFile
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
   
    public static void readUserDefinedLibraryInputFileCreateUnsortedChromosomeBasedFilesWithNumbersFillMapsWriteMaps(
    		String dataFolder,
    		String userDefinedLibraryInputFile,
    		UserDefinedLibraryDataFormat userDefinedLibraryDataFormat,
    		TObjectIntMap<String> userDefinedLibraryElementType2ElementTypeNumberMap,
    		TIntObjectMap<String> userDefinedLibraryElementTypeNumber2ElementTypeMap,
   
    		TIntObjectMap<TObjectIntMap<String>> elementTypeNumber2ElementName2ElementNumberMapMap,
    		TIntObjectMap<TIntObjectMap<String>> elementTypeNumber2ElementNumber2ElementNameMapMap,
    		
    		TObjectIntMap<String> userDefinedLibraryFileName2FileNumberMap,
    		TIntObjectMap<String> userDefinedLibraryFileNumber2FileNameMap){
    	
    	FileReader fileReader = null;
    	BufferedReader bufferedReader = null;
    	String strLine = null;
    	
    	int indexofFirstTab;
    	int indexofSecondTab;
    	int indexofThirdTab;
    	
    	String filePathFileName = null;
    	String elementType = null;
    	String elementName = null;
    	int intervalAroundSummit = 0;
    	
    	String fileName;
    	
    	int elementTypeNumber = 1;
    	int fileNumber = 1;
    	
    	int currentElementTypeNumber;
    	int currentElementNumber;
    	
    	TIntObjectMap<List<BufferedWriter>> elementTypeNumber2ListofBufferedWritersMap = new TIntObjectHashMap<List<BufferedWriter>>();

    	
    	TObjectIntMap<String> elementName2ElementNumberMap = null;
    	TIntObjectMap<String> elementNumber2ElementNameMap = null;
    	
    	try {
    		
    		//Read UserDefinedLibraryInputFile
			fileReader = FileOperations.createFileReader(userDefinedLibraryInputFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//Skip header or comment line
				if (!strLine.startsWith("!")){
					
					indexofFirstTab  = strLine.indexOf('\t');
					indexofSecondTab = (indexofFirstTab>0) ? strLine.indexOf('\t',indexofFirstTab+1) : -1;
					indexofThirdTab  = (indexofSecondTab>0)? strLine.indexOf('\t',indexofSecondTab+1): -1;
					
					/***********************************************************************************************/
					/***********************FileName 2 FileNumber starts********************************************/
					/***********************FileNumber 2 FileName starts********************************************/
					filePathFileName = strLine.substring(0, indexofFirstTab);
					//Trim
					filePathFileName = filePathFileName.trim();
					File file = new File(filePathFileName);
					fileName= file.getName();
					if(!userDefinedLibraryFileName2FileNumberMap.containsKey(fileName)){
						userDefinedLibraryFileName2FileNumberMap.put(fileName, fileNumber);
						userDefinedLibraryFileNumber2FileNameMap.put(fileNumber, fileName);
						fileNumber++;
					}
					file = null;
					/***********************FileName 2 FileNumber ends**********************************************/
					/***********************FileNumber 2 FileName ends**********************************************/
					/***********************************************************************************************/

					
					
					/*********************************************************************************************************/
					/***********************ElementType 2 ElementTypeNumber starts********************************************/
					/***********************ElementTypeNumber 2 ElementType starts********************************************/
					elementType =  strLine.substring(indexofFirstTab+1, indexofSecondTab);
					//trim
					elementType = elementType.trim();
					if(!userDefinedLibraryElementType2ElementTypeNumberMap.containsKey(elementType)){
						userDefinedLibraryElementType2ElementTypeNumberMap.put(elementType, elementTypeNumber);
						userDefinedLibraryElementTypeNumber2ElementTypeMap.put(elementTypeNumber, elementType);
						
						//Create Chromosome Based BufferedWritwers
						FileOperations.createChromosomeBasedListofBufferedWriters(elementType,elementTypeNumber,elementTypeNumber2ListofBufferedWritersMap,dataFolder + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator") );
						
						elementTypeNumber++;
					}
					/***********************ElementType 2 ElementTypeNumber ends**********************************************/
					/***********************ElementTypeNumber 2 ElementType ends**********************************************/
					/*********************************************************************************************************/
					
					
					
					/*********************************************************************************************************/
					/***************ElementTypeNumber Specific****************************************************************/
					/***************ElementName 2 ElementNumber starts********************************************************/
					/***************ElementNumber 2 ElementName starts********************************************************/
					if (indexofThirdTab>0){
						elementName =  strLine.substring(indexofSecondTab+1, indexofThirdTab);
						intervalAroundSummit = Integer.parseInt(strLine.substring(indexofThirdTab+1));
					} else{
						elementName =  strLine.substring(indexofSecondTab+1);
						intervalAroundSummit = Integer.MIN_VALUE;
					}		
					
					//trim
					elementName = elementName.trim();
					
					currentElementTypeNumber = userDefinedLibraryElementType2ElementTypeNumberMap.get(elementType);
					
					elementName2ElementNumberMap = elementTypeNumber2ElementName2ElementNumberMapMap.get(currentElementTypeNumber);
					elementNumber2ElementNameMap = elementTypeNumber2ElementNumber2ElementNameMapMap.get(currentElementTypeNumber);
					
					
					//Initialize elementName2ElementNumberMap
					if(elementName2ElementNumberMap==null){
						elementName2ElementNumberMap  = new TObjectIntHashMap<String>();
						elementTypeNumber2ElementName2ElementNumberMapMap.put(currentElementTypeNumber, elementName2ElementNumberMap);
					}
									
					//Initialize elementNumber2ElementNameMap
					if(elementNumber2ElementNameMap==null){
						elementNumber2ElementNameMap =  new TIntObjectHashMap<String>();
						elementTypeNumber2ElementNumber2ElementNameMapMap.put(currentElementTypeNumber, elementNumber2ElementNameMap);
					}
					
					//Fill elementName2ElementNumberMap 
					//Fill elementNumber2ElementNameMap
					if(!elementName2ElementNumberMap.containsKey(elementName)){
						currentElementNumber = elementName2ElementNumberMap.size()+1;
						elementName2ElementNumberMap.put(elementName, currentElementNumber);
						elementNumber2ElementNameMap.put(currentElementNumber, elementName);
					}					
					/***************ElementTypeNumber Specific****************************************************************/
					/***************ElementName 2 ElementNumber ends**********************************************************/
					/***************ElementNumber 2 ElementName ends**********************************************************/
					/*********************************************************************************************************/
					
				
					//Process each file written in UserDefinedLibraryInputFile
					//Write ElementTypeBased ChromosomeBased Unsorted With Numbers Files 
					readFileAndWriteElementTypeBasedChromosomeBasedUnsortedFilesWithNumbers(filePathFileName,fileName,userDefinedLibraryDataFormat,userDefinedLibraryFileName2FileNumberMap,currentElementTypeNumber,userDefinedLibraryElementType2ElementTypeNumberMap,elementName,elementTypeNumber2ElementName2ElementNumberMapMap,elementTypeNumber2ListofBufferedWritersMap);
					
				}//End of if it is not a comment line
				
					
			
			}//End of while: We have read all the listed files in userDefinedLibraryInputFile
			
			//Close UserDefinedLibraryInputFile
			bufferedReader.close();
			
			
			//Close ElementTypeBased ChromosomeBased BufferedWriters 
			//elementTypeNumber2ListofBufferedWritersMap
			//MAP
			// accessing keys/values through an iterator:
			for ( TIntObjectIterator<List<BufferedWriter>> it = elementTypeNumber2ListofBufferedWritersMap.iterator(); it.hasNext(); ) {
			    it.advance();
			    FileOperations.closeChromosomeBasedBufferedWriters((List<BufferedWriter>) it.value());
			}
			
			//Write AllPossibleNames for UserDefinedLibraryLibrary
			//Write userDefinedLibraryElementType2ElementTypeNumberMap
	  		//Write userDefinedLibraryElementTypeNumber2ElementTypeMap
    		//Write userDefinedLibraryFileName2FileNumberMap
    		//Write userDefinedLibraryFileNumber2FileNameMap
			WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapString2Integer(dataFolder,userDefinedLibraryElementType2ElementTypeNumberMap, Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_2_ELEMENTTYPENUMBER_OUTPUT_FILENAME);
			WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapString2Integer(dataFolder,userDefinedLibraryFileName2FileNumberMap, Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_FILENAME_2_FILENUMBER_OUTPUT_FILENAME);
		
			
			WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapInteger2String(dataFolder,userDefinedLibraryElementTypeNumber2ElementTypeMap, Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPENUMBER_2_ELEMENTTYPE_OUTPUT_FILENAME);
			WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapInteger2String(dataFolder,userDefinedLibraryFileNumber2FileNameMap, Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_FILENUMBER_2_FILENAME_OUTPUT_FILENAME);
	
			//Write elementType Based 
			//userDefinedLibraryElementName2ElementNumberMap
				for ( TIntObjectIterator<TObjectIntMap<String>> it = elementTypeNumber2ElementName2ElementNumberMapMap.iterator(); it.hasNext(); ) {
			    it.advance();
//			    ElementType
			    elementType = userDefinedLibraryElementTypeNumber2ElementTypeMap.get(it.key());
			   WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapString2Integer(dataFolder,it.value(), Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator") + elementType + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTNAME_2_ELEMENTNUMBER_OUTPUT_FILENAME);
			}//End for each elementTypeSpecific elementName2ElementNumberMap
			
			
			//Write elementType Based 
			//userDefinedLibraryElementNumber2ElementNameMap
			for ( TIntObjectIterator<TIntObjectMap<String>> it = elementTypeNumber2ElementNumber2ElementNameMapMap.iterator(); it.hasNext(); ) {
			    it.advance();
//			    ElementType
			    elementType = userDefinedLibraryElementTypeNumber2ElementTypeMap.get(it.key());
			   WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapInteger2String(dataFolder,it.value(), Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator") + elementType + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTNUMBER_2_ELEMENTNAME_OUTPUT_FILENAME);
			}//End for each elementTypeSpecific elementNumber2ElementNameMap
	
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
