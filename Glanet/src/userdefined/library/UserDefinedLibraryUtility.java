/**
 * 
 */
package userdefined.library;

import enumtypes.ChromosomeName;
import enumtypes.FileFormatType;
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

import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Bur�ak
 *
 */
public class UserDefinedLibraryUtility {

	// Fill the first argument using the second argument
	// Fill target from source
	public static void fillElementTypeNumberBasedMaps(TIntObjectMap<TIntObjectMap<TIntList>> elementTypeNumber2ElementNumber2AllKMap, TIntObjectMap<TIntList> elementTypeNumberElementNumber2AllKMap) {

		int elementTypeNumberElementNumber;
		int elementTypeNumber;
		int elementNumber;
		TIntList existingAllK;

		for (TIntObjectIterator<TIntList> it = elementTypeNumberElementNumber2AllKMap.iterator(); it.hasNext();) {

			it.advance();

			elementTypeNumberElementNumber = it.key();
			existingAllK = it.value();

			elementTypeNumber = IntervalTree.getElementTypeNumber(elementTypeNumberElementNumber, GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
			elementNumber = IntervalTree.getElementNumber(elementTypeNumberElementNumber, GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);

			TIntObjectMap<TIntList> elementNumber2AllKMap = elementTypeNumber2ElementNumber2AllKMap.get(elementTypeNumber);

			// Now we initialize TIntList
			TIntList allK = new TIntArrayList();

			// Fill allK
			for (TIntIterator it2 = existingAllK.iterator(); it2.hasNext();) {
				allK.add(it2.next());
			}

			elementNumber2AllKMap.put(elementNumber, allK);
		}
	}

	// Fill the first argument using the second argument
	// Fill elementTypeNumber2ElementNumber2KMap using
	// originalElementTypeNumberElementNumber2KMap
	public static void fillElementTypeNumberBasedMaps(TIntObjectMap<TIntIntMap> elementTypeNumber2ElementNumber2KMap, TIntIntMap originalElementTypeNumberElementNumber2KMap) {

		int elementTypeNumberElementNumber;
		int elementTypeNumber;
		int elementNumber;
		int numberofOverlaps;

		// For each elementTypeNumberElementNumber
		for (TIntIntIterator it = originalElementTypeNumberElementNumber2KMap.iterator(); it.hasNext();) {

			it.advance();

			elementTypeNumberElementNumber = it.key();
			numberofOverlaps = it.value();

			elementTypeNumber = IntervalTree.getElementTypeNumber(elementTypeNumberElementNumber, GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
			elementNumber = IntervalTree.getElementNumber(elementTypeNumberElementNumber, GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);

			TIntIntMap elementNumber2KMap = elementTypeNumber2ElementNumber2KMap.get(elementTypeNumber);

			elementNumber2KMap.put(elementNumber, numberofOverlaps);
		}// End of for each elementTypeNumberElementNumber

	}

	public static void fillNumber2NameMap(TIntObjectMap<String> number2NameMap, String dataFolder, String directoryName, String fileName) {

		String strLine;
		FileReader fileReader;
		BufferedReader bufferedReader;

		int elementTypeNumber;
		String elementType;
		int indexofFirstTab;

		try {
			fileReader = FileOperations.createFileReader(dataFolder + directoryName + fileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {

				indexofFirstTab = strLine.indexOf('\t');

				elementTypeNumber = Integer.parseInt(strLine.substring(0, indexofFirstTab));
				elementType = strLine.substring(indexofFirstTab + 1);

				number2NameMap.put(elementTypeNumber, elementType);

			}

			bufferedReader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readFileAndWriteElementTypeBasedChromosomeBasedUnsortedFilesWithNumbers(String filePathFileName, String fileName, UserDefinedLibraryDataFormat userDefinedLibraryDataFormat, TObjectIntMap<String> fileName2FileNumberMap, int elementTypeNumber, TObjectIntMap<String> elementType2ElementTypeNumberMap, String elementName, TIntObjectMap<TObjectIntMap<String>> elementTypeNumber2ElementName2ElementNumberMapMap, TIntObjectMap<List<BufferedWriter>> elementTypeNumber2BufferedWriterList, int windowAroundSummit, FileFormatType fileFormatType) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;

		// For getting summit position
		// in case of fileFormatType is narrowPeak file and windowAroundSummit
		// is greater than zero
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;

		String chrName = null;
		int start = Integer.MIN_VALUE;
		int end = Integer.MIN_VALUE;
		int summitPositionWRTStart = Integer.MIN_VALUE;
		int summitPosition = Integer.MIN_VALUE;

		int windowStart = Integer.MIN_VALUE;
		int windowEnd = Integer.MIN_VALUE;

		BufferedWriter bufferedWriter = null;
		List<BufferedWriter> bufferedWriterList = null;

		TObjectIntMap<String> elementName2ElementNumberMap = null;

		try {

			// Read each file written in UserDefinedLibraryInputFile
			fileReader = FileOperations.createFileReader(filePathFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {

				// example strLine
				// chr1 91852781 91853156 1 1 . 1728.25 7.867927 7.867927 187

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = (indexofFirstTab > 0) ? strLine.indexOf('\t', indexofFirstTab + 1) : -1;
				indexofThirdTab = (indexofSecondTab > 0) ? strLine.indexOf('\t', indexofSecondTab + 1) : -1;

				if (indexofFirstTab > 0) {
					chrName = strLine.substring(0, indexofFirstTab);
				}

				if (indexofFirstTab > 0 && indexofSecondTab > 0) {
					start = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));
				}

				if (indexofSecondTab > 0 && indexofThirdTab > 0) {
					end = Integer.parseInt(strLine.substring(indexofSecondTab + 1, indexofThirdTab));
				} else if (indexofSecondTab > 0) {
					end = Integer.parseInt(strLine.substring(indexofSecondTab + 1));
				}

				// SET startPosition and endPosition starts
				switch (userDefinedLibraryDataFormat) {
					case USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_START_ENDEXCLUSIVE_COORDINATES:
						end = end - 1;
						break;
					case USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_START_ENDINCLUSIVE_COORDINATES:
						break;
					case USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_START_ENDEXCLUSIVE_COORDINATES:
						start = start - 1;
						end = end - 2;
						break;
					case USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_START_ENDINCLUSIVE_COORDINATES:
						start = start - 1;
						end = end - 1;
						break;
				}
				// SET startPosition and endPosition ends

				// Consider intervalAroundSummit if fileFormatType is narrowPeak
				// And windowAroundSummit > 0
				// Otherwise don't care windowAroundSummit
				if (fileFormatType.isNarrowPeak() && windowAroundSummit > 0) {

					// get summit position
					indexofFourthTab = (indexofThirdTab > 0) ? strLine.indexOf('\t', indexofThirdTab + 1) : -1;
					indexofFifthTab = (indexofFourthTab > 0) ? strLine.indexOf('\t', indexofFourthTab + 1) : -1;
					indexofSixthTab = (indexofFifthTab > 0) ? strLine.indexOf('\t', indexofFifthTab + 1) : -1;
					indexofSeventhTab = (indexofSixthTab > 0) ? strLine.indexOf('\t', indexofSixthTab + 1) : -1;
					indexofEigthTab = (indexofSeventhTab > 0) ? strLine.indexOf('\t', indexofSeventhTab + 1) : -1;
					indexofNinethTab = (indexofEigthTab > 0) ? strLine.indexOf('\t', indexofEigthTab + 1) : -1;

					summitPositionWRTStart = Integer.parseInt(strLine.substring(indexofNinethTab + 1));
					summitPosition = start + summitPositionWRTStart;

					windowStart = summitPosition - windowAroundSummit;
					windowEnd = summitPosition + windowAroundSummit;

					// SET new startPosition and endPosition starts
					if ((windowStart > start) && (windowStart <= end)) {
						start = windowStart;
					}

					if ((windowEnd < end) && (windowEnd >= start)) {
						end = windowEnd;
					}
					// SET new startPosition and endPosition ends

				}// End of if fileFormatType is narrowPeak and
					// windowAroundSummit is greater than zero

				// Get the bufferedWriterList for a certain elementTypeNumber
				bufferedWriterList = elementTypeNumber2BufferedWriterList.get(elementTypeNumber);

				// Get elementName2ElementNumberMap for a certain
				// elementTypeNumber
				elementName2ElementNumberMap = elementTypeNumber2ElementName2ElementNumberMapMap.get(elementTypeNumber);

				bufferedWriter = FileOperations.getChromosomeBasedBufferedWriter(ChromosomeName.convertStringtoEnum(chrName), bufferedWriterList);
				bufferedWriter.write(chrName + "\t" + start + "\t" + end + "\t" + elementTypeNumber + "\t" + elementName2ElementNumberMap.get(elementName) + "\t" + fileName2FileNumberMap.get(fileName) + System.getProperty("line.separator"));
			}

			// Close each file written in UserDefinedLibraryInputFile
			bufferedReader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void readUserDefinedLibraryInputFileCreateUnsortedChromosomeBasedFilesWithNumbersFillMapsWriteMaps(String dataFolder, String userDefinedLibraryInputFile, UserDefinedLibraryDataFormat userDefinedLibraryDataFormat, TObjectIntMap<String> userDefinedLibraryElementType2ElementTypeNumberMap, TIntObjectMap<String> userDefinedLibraryElementTypeNumber2ElementTypeMap,

	TIntObjectMap<TObjectIntMap<String>> elementTypeNumber2ElementName2ElementNumberMapMap, TIntObjectMap<TIntObjectMap<String>> elementTypeNumber2ElementNumber2ElementNameMapMap,

	TObjectIntMap<String> userDefinedLibraryFileName2FileNumberMap, TIntObjectMap<String> userDefinedLibraryFileNumber2FileNameMap) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine = null;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;

		String filePathFileName = null;
		String elementType = null;
		String elementName = null;
		int windowAroundSummit = Integer.MIN_VALUE;

		String fileName;
		FileFormatType fileFormatType;

		int elementTypeNumber = 1;
		int fileNumber = 1;

		int currentElementTypeNumber;
		int currentElementNumber;

		TIntObjectMap<List<BufferedWriter>> elementTypeNumber2ListofBufferedWritersMap = new TIntObjectHashMap<List<BufferedWriter>>();

		TObjectIntMap<String> elementName2ElementNumberMap = null;
		TIntObjectMap<String> elementNumber2ElementNameMap = null;

		try {

			// Read UserDefinedLibraryInputFile
			fileReader = FileOperations.createFileReader(userDefinedLibraryInputFile);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {

				// Skip header or comment line
				if (!strLine.startsWith("!")) {

					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = (indexofFirstTab > 0) ? strLine.indexOf('\t', indexofFirstTab + 1) : -1;
					indexofThirdTab = (indexofSecondTab > 0) ? strLine.indexOf('\t', indexofSecondTab + 1) : -1;

					/***********************************************************************************************/
					/*********************** FileName 2 FileNumber starts ********************************************/
					/*********************** FileNumber 2 FileName starts ********************************************/
					filePathFileName = strLine.substring(0, indexofFirstTab);
					// Trim
					filePathFileName = filePathFileName.trim();
					File file = new File(filePathFileName);
					fileName = file.getName();
					if (!userDefinedLibraryFileName2FileNumberMap.containsKey(fileName)) {
						userDefinedLibraryFileName2FileNumberMap.put(fileName, fileNumber);
						userDefinedLibraryFileNumber2FileNameMap.put(fileNumber, fileName);
						fileNumber++;
					}
					file = null;
					/*********************** FileName 2 FileNumber ends **********************************************/
					/*********************** FileNumber 2 FileName ends **********************************************/
					/***********************************************************************************************/

					/*********************************************************************************************************/
					/*********************** ElementType 2 ElementTypeNumber starts ********************************************/
					/*********************** ElementTypeNumber 2 ElementType starts ********************************************/
					elementType = strLine.substring(indexofFirstTab + 1, indexofSecondTab);
					// trim
					elementType = elementType.trim();
					if (!userDefinedLibraryElementType2ElementTypeNumberMap.containsKey(elementType)) {
						userDefinedLibraryElementType2ElementTypeNumberMap.put(elementType, elementTypeNumber);
						userDefinedLibraryElementTypeNumber2ElementTypeMap.put(elementTypeNumber, elementType);

						// Create Chromosome Based BufferedWritwers
						FileOperations.createChromosomeBasedListofBufferedWriters(elementType, elementTypeNumber, elementTypeNumber2ListofBufferedWritersMap, dataFolder + Commons.USER_DEFINED_LIBRARY + System.getProperty("file.separator"));

						elementTypeNumber++;
					}
					/*********************** ElementType 2 ElementTypeNumber ends **********************************************/
					/*********************** ElementTypeNumber 2 ElementType ends **********************************************/
					/*********************************************************************************************************/

					/*********************************************************************************************************/
					/*************** ElementTypeNumber Specific ****************************************************************/
					/*************** ElementName 2 ElementNumber starts ********************************************************/
					/*************** ElementNumber 2 ElementName starts ********************************************************/
					if (indexofThirdTab > 0) {
						elementName = strLine.substring(indexofSecondTab + 1, indexofThirdTab);
						windowAroundSummit = Integer.parseInt(strLine.substring(indexofThirdTab + 1));
					} else {
						elementName = strLine.substring(indexofSecondTab + 1);
						windowAroundSummit = Integer.MIN_VALUE;
					}

					// Consider windowAroundSummit if fileFormatType is
					// narrowPeak
					// And windowAroundSummit > 0
					// Otherwise don't care windowAroundSummit
					if (fileName.toLowerCase().endsWith(Commons.NARROWPEAK)) {
						fileFormatType = FileFormatType.NARROWPEAK;
					} else if (fileName.toLowerCase().endsWith(Commons.BED)) {
						fileFormatType = FileFormatType.BED;
					} else {
						fileFormatType = FileFormatType.FILE_FORMAT_TYPE_OTHER;
					}

					// trim
					elementName = elementName.trim();

					currentElementTypeNumber = userDefinedLibraryElementType2ElementTypeNumberMap.get(elementType);

					elementName2ElementNumberMap = elementTypeNumber2ElementName2ElementNumberMapMap.get(currentElementTypeNumber);
					elementNumber2ElementNameMap = elementTypeNumber2ElementNumber2ElementNameMapMap.get(currentElementTypeNumber);

					// Initialize elementName2ElementNumberMap
					if (elementName2ElementNumberMap == null) {
						elementName2ElementNumberMap = new TObjectIntHashMap<String>();
						elementTypeNumber2ElementName2ElementNumberMapMap.put(currentElementTypeNumber, elementName2ElementNumberMap);
					}

					// Initialize elementNumber2ElementNameMap
					if (elementNumber2ElementNameMap == null) {
						elementNumber2ElementNameMap = new TIntObjectHashMap<String>();
						elementTypeNumber2ElementNumber2ElementNameMapMap.put(currentElementTypeNumber, elementNumber2ElementNameMap);
					}

					// Fill elementName2ElementNumberMap
					// Fill elementNumber2ElementNameMap
					if (!elementName2ElementNumberMap.containsKey(elementName)) {
						currentElementNumber = elementName2ElementNumberMap.size() + 1;
						elementName2ElementNumberMap.put(elementName, currentElementNumber);
						elementNumber2ElementNameMap.put(currentElementNumber, elementName);
					}
					/*************** ElementTypeNumber Specific ****************************************************************/
					/*************** ElementName 2 ElementNumber ends **********************************************************/
					/*************** ElementNumber 2 ElementName ends **********************************************************/
					/*********************************************************************************************************/

					// Process each file written in UserDefinedLibraryInputFile
					// Write ElementTypeBased ChromosomeBased Unsorted With
					// Numbers Files
					readFileAndWriteElementTypeBasedChromosomeBasedUnsortedFilesWithNumbers(filePathFileName, fileName, userDefinedLibraryDataFormat, userDefinedLibraryFileName2FileNumberMap, currentElementTypeNumber, userDefinedLibraryElementType2ElementTypeNumberMap, elementName, elementTypeNumber2ElementName2ElementNumberMapMap, elementTypeNumber2ListofBufferedWritersMap, windowAroundSummit, fileFormatType);

				}// End of if it is not a comment line

			}// End of while: We have read all the listed files in
				// userDefinedLibraryInputFile

			// Close UserDefinedLibraryInputFile
			bufferedReader.close();

			// Close ElementTypeBased ChromosomeBased BufferedWriters
			// elementTypeNumber2ListofBufferedWritersMap
			// MAP
			// accessing keys/values through an iterator:
			for (TIntObjectIterator<List<BufferedWriter>> it = elementTypeNumber2ListofBufferedWritersMap.iterator(); it.hasNext();) {
				it.advance();
				FileOperations.closeChromosomeBasedBufferedWriters((List<BufferedWriter>) it.value());
			}

			// Write AllPossibleNames for UserDefinedLibraryLibrary
			// Write userDefinedLibraryElementType2ElementTypeNumberMap
			// Write userDefinedLibraryElementTypeNumber2ElementTypeMap
			// Write userDefinedLibraryFileName2FileNumberMap
			// Write userDefinedLibraryFileNumber2FileNameMap
			FileOperations.writeName2NumberMap(dataFolder, userDefinedLibraryElementType2ElementTypeNumberMap, Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_NAME_2_NUMBER_OUTPUT_FILENAME);
			FileOperations.writeName2NumberMap(dataFolder, userDefinedLibraryFileName2FileNumberMap, Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_FILE_NAME_2_NUMBER_OUTPUT_FILENAME);

			FileOperations.writeNumber2NameMap(dataFolder, userDefinedLibraryElementTypeNumber2ElementTypeMap, Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENTTYPE_NUMBER_2_NAME_OUTPUT_FILENAME);
			FileOperations.writeNumber2NameMap(dataFolder, userDefinedLibraryFileNumber2FileNameMap, Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_FILE_NUMBER_2_NAME_OUTPUT_FILENAME);

			// Write elementType Based
			// userDefinedLibraryElementName2ElementNumberMap
			for (TIntObjectIterator<TObjectIntMap<String>> it = elementTypeNumber2ElementName2ElementNumberMapMap.iterator(); it.hasNext();) {
				it.advance();
				// ElementType
				elementType = userDefinedLibraryElementTypeNumber2ElementTypeMap.get(it.key());
				FileOperations.writeName2NumberMap(dataFolder, it.value(), Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME + elementType + System.getProperty("file.separator"), Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENT_NAME_2_NUMBER_OUTPUT_FILENAME);
			}// End for each elementTypeSpecific elementName2ElementNumberMap

			// Write elementType Based
			// userDefinedLibraryElementNumber2ElementNameMap
			for (TIntObjectIterator<TIntObjectMap<String>> it = elementTypeNumber2ElementNumber2ElementNameMapMap.iterator(); it.hasNext();) {
				it.advance();
				// ElementType
				elementType = userDefinedLibraryElementTypeNumber2ElementTypeMap.get(it.key());
				FileOperations.writeNumber2NameMap(dataFolder, it.value(), Commons.ALL_POSSIBLE_NAMES_USERDEFINEDLIBRARY_OUTPUT_DIRECTORYNAME + elementType + System.getProperty("file.separator"), Commons.ALL_POSSIBLE_USERDEFINEDLIBRARY_ELEMENT_NUMBER_2_NAME_OUTPUT_FILENAME);
			}// End for each elementTypeSpecific elementNumber2ElementNameMap

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
