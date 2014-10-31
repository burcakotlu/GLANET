/**
 * 
 */
package userdefined.library;

import enumtypes.ChromosomeName;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TObjectShortIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import annotation.WriteAllPossibleNamesandUnsortedFilesWithNumbers;
import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Burçak
 *
 */
public class UserDefinedLibraryUtility {
	
	
	
	
	
	public static void readFileAndWriteElementTypeBasedChromosomeBasedUnsortedFilesWithNumbers(
			String filePathFileName, 
			String fileName,
			TObjectIntMap<String> fileName2FileNumberMap,
			String elementType,
			TObjectIntMap<String> elementType2ElementTypeNumberMap,
			String elementName, 
			TObjectIntMap<String> elementName2ElementNumberMap,
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
		int elementTypeNumber = Integer.MIN_VALUE;
		
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
				
				
				//Get the bufferedWriterList for a certain elementTypeNumber
				elementTypeNumber = elementType2ElementTypeNumberMap.get(elementType);
				bufferedWriterList = elementTypeNumber2BufferedWriterList.get(elementTypeNumber);
				
				bufferedWriter = FileOperations.getChromosomeBasedBufferedWriter(ChromosomeName.convertStringtoEnum(chrName),bufferedWriterList);
				bufferedWriter.write(chrName+ "\t" + start + "\t" + end + "\t" + elementName2ElementNumberMap.get(elementName) + "\t" + fileName2FileNumberMap.get(fileName) + System.getProperty("line.separator") );
			}
					
			//Close each file written in UserDefinedLibraryInputFile
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
   
    public static void readUserDefinedLibraryInputFileAndCreateUnsortedChromosomeBasedFilesWithNumbersAndFillMaps(
    		String dataFolder,
    		String userDefinedLibraryInputFile,
    		TObjectIntMap<String> userDefinedLibraryElementType2ElementTypeNumberMap,
    		TIntObjectMap<String> userDefinedLibraryElementTypeNumber2ElementTypeMap,
    		TObjectIntMap<String> userDefinedLibraryElementName2ElementNumberMap,
    		TIntObjectMap<String> userDefinedLibraryElementNumber2ElementNameMap,
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
    	
    	int elementNumber = 1;
    	int elementTypeNumber = 1;
    	int fileNumber = 1;
    	
    	
    	TIntObjectMap<List<BufferedWriter>> elementTypeNumber2ListofBufferedWritersMap = new TIntObjectHashMap<List<BufferedWriter>>();

    	
    	try {
			fileReader = FileOperations.createFileReader(userDefinedLibraryInputFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//Skip header or comment line
				if (!strLine.startsWith("!")){
					
					indexofFirstTab  = strLine.indexOf('\t');
					indexofSecondTab = (indexofFirstTab>0) ? strLine.indexOf('\t',indexofFirstTab+1) : -1;
					indexofThirdTab  = (indexofSecondTab>0)? strLine.indexOf('\t',indexofSecondTab+1): -1;
					
					/*******************************************************************/
					//FileName 2 FileNumber
					//FileNumber 2 FileNamee
					filePathFileName = strLine.substring(0, indexofFirstTab);
					File file = new File(filePathFileName);
					fileName= file.getName();
					if(!userDefinedLibraryFileName2FileNumberMap.containsKey(fileName)){
						userDefinedLibraryFileName2FileNumberMap.put(fileName, fileNumber);
						userDefinedLibraryFileNumber2FileNameMap.put(fileNumber, fileName);
						fileNumber++;
					}
					file = null;
					/*******************************************************************/

					
					
					/*******************************************************************/
					//ElementType 2 ElementTypeNumber
					//ElementTypeNumber 2 ElementType
					elementType =  strLine.substring(indexofFirstTab+1, indexofSecondTab);
					if(!userDefinedLibraryElementType2ElementTypeNumberMap.containsKey(elementType)){
						userDefinedLibraryElementType2ElementTypeNumberMap.put(elementType, elementTypeNumber);
						userDefinedLibraryElementTypeNumber2ElementTypeMap.put(elementTypeNumber, elementType);
						
						//Create Chromosome Based BufferedWritwers
						FileOperations.createChromosomeBasedListofBufferedWriters(elementType,elementTypeNumber,elementTypeNumber2ListofBufferedWritersMap,dataFolder + Commons.BYGLANET + System.getProperty("file.separator") + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator") );
						
						elementTypeNumber++;
					}
					
					
					/*******************************************************************/
					
					
					
					/*******************************************************************/
					//ElementName 2 ElementNumber
					//ElementNumber 2 ElementName
					if (indexofThirdTab>0){
						elementName =  strLine.substring(indexofSecondTab+1, indexofThirdTab);
						intervalAroundSummit = Integer.parseInt(strLine.substring(indexofThirdTab+1));
					} else{
						elementName =  strLine.substring(indexofSecondTab+1);
						intervalAroundSummit = Integer.MIN_VALUE;
					}		
					if(!userDefinedLibraryElementName2ElementNumberMap.containsKey(elementName)){
						userDefinedLibraryElementName2ElementNumberMap.put(elementName, elementNumber);
						userDefinedLibraryElementNumber2ElementNameMap.put(elementNumber, elementName);
						elementNumber++;
					}
					/*******************************************************************/
					
				
					//Process each file written in UserDefinedLibraryInputFile
					readFileAndWriteElementTypeBasedChromosomeBasedUnsortedFilesWithNumbers(filePathFileName,fileName,userDefinedLibraryFileName2FileNumberMap,elementType,userDefinedLibraryElementType2ElementTypeNumberMap,elementName,userDefinedLibraryElementName2ElementNumberMap,elementTypeNumber2ListofBufferedWritersMap);
					
				}//End of if it is not a comment line
				
					
			
			}//End of while
			
			//Close UserDefinedLibrary InputFile
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
	  		//Write userDefinedLibraryElementTypeNumber2ElementTypeMap,
    		//Write userDefinedLibraryElementName2ElementNumberMap,
			//Write userDefinedLibraryElementNumber2ElementNameMap,
    		//Write userDefinedLibraryFileName2FileNumberMap,
    		//Write userDefinedLibraryFileNumber2FileNameMap){
			WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapString2Integer(dataFolder,userDefinedLibraryElementType2ElementTypeNumberMap, Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_2_ELEMENTTYPENUMBER_OUTPUT_FILENAME);
			WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapString2Integer(dataFolder,userDefinedLibraryElementName2ElementNumberMap, Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTNAME_2_ELEMENTNUMBER_OUTPUT_FILENAME);
			WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapString2Integer(dataFolder,userDefinedLibraryFileName2FileNumberMap, Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_FILENAME_2_FILENUMBER_OUTPUT_FILENAME);
		
			
			WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapInteger2String(dataFolder,userDefinedLibraryElementTypeNumber2ElementTypeMap, Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPENUMBER_2_ELEMENTTYPE_OUTPUT_FILENAME);
			WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapInteger2String(dataFolder,userDefinedLibraryElementNumber2ElementNameMap, Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTNUMBER_2_ELEMENTNAME_OUTPUT_FILENAME);
			WriteAllPossibleNamesandUnsortedFilesWithNumbers.writeTroveMapInteger2String(dataFolder,userDefinedLibraryFileNumber2FileNameMap, Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"), Commons.WRITE_ALL_POSSIBLE_USERDEFINEDLIBRARY_FILENUMBER_2_FILENAME_OUTPUT_FILENAME);
	
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
