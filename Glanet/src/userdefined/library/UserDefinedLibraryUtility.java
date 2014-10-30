/**
 * 
 */
package userdefined.library;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Burçak
 *
 */
public class UserDefinedLibraryUtility {
	
	
	public static void readFileAndWritelementTypeBasedChromosomeBasedUnsortedFilesWithNumbers(String filePathFileName, String ElementType,List<BufferedWriter> bufferedWriterlist){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		String chrName;
		int start;
		int end;
		
		
		try {
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
				
				
			}
			
			
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void 	createChromosomeBasedListofBufferedWriters(
			String elementType,
			int elementTypeNumber,
			TIntObjectMap<List<BufferedWriter>> elementTypeNumber2ListofBufferedWritersMap,
			String baseDirectoryName){
		
		
			try {
				FileWriter fileWriter1 = FileOperations.createFileWriter(baseDirectoryName + elementType + System.getProperty("file.separator") + Commons.UNSORTED_CHR1_USERDEFINEDLIBRARY_FILENAME_WITH_NUMBERS);
				FileWriter fileWriter2 = FileOperations.createFileWriter(baseDirectoryName + elementType + System.getProperty("file.separator") + Commons.UNSORTED_CHR2_USERDEFINEDLIBRARY_FILENAME_WITH_NUMBERS);
				FileWriter fileWriter3 = FileOperations.createFileWriter(baseDirectoryName + elementType + System.getProperty("file.separator") + Commons.UNSORTED_CHR3_USERDEFINEDLIBRARY_FILENAME_WITH_NUMBERS);
		
				
				BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
				BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
				BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
				
				List<BufferedWriter> listofBufferedWriter = new ArrayList<BufferedWriter>();
				
				listofBufferedWriter.add(bufferedWriter1);
				listofBufferedWriter.add(bufferedWriter2);
				listofBufferedWriter.add(bufferedWriter3);
				
				elementTypeNumber2ListofBufferedWritersMap.put(elementTypeNumber,listofBufferedWriter);
				
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		
	}
	
   
    public static void readUserDefinedLibraryInputFileAndCreateUnsortedChromosomeBasedFilesAndFillMaps(
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
						createChromosomeBasedListofBufferedWriters(elementType,elementTypeNumber,elementTypeNumber2ListofBufferedWritersMap,dataFolder + Commons.BYGLANET + System.getProperty("file.separator") + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator") );
						
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
					
				
					//Process element file
					readFileAndWritelementTypeBasedChromosomeBasedUnsortedFilesWithNumbers(filePathFileName,elementType,elementTypeNumber2ListofBufferedWritersMap.get(elementTypeNumber));
					
				}//End of if it is not a comment line
				
					
			
			}//End of while
			
			bufferedReader.close();
			
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
