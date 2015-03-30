/**
 * @author Burcak Otlu
 * Feb 6, 2014
 * 3:36:37 PM
 * 2014
 *
 * 
 */
package auxiliary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ui.GlanetRunner;
import common.Commons;
import enumtypes.ChromosomeName;
import enumtypes.ElementType;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.TShortObjectMap;

public class FileOperations {

	private static Logger logger = Logger.getLogger(FileOperations.class);

	// 15 DEC 2014
	public static void createFolder(String path) {

		File f = new File(path);

		if (f.isDirectory() && !f.exists())
			f.mkdirs();

	}

	// 15 DEC 2014
	public static void createFile(String fileName) {

		File f = new File(fileName);
		String parent = f.getParent();

		if (parent != null) {
			if (f.isDirectory() && !f.exists())
				f.mkdirs();
			else if (!f.isDirectory() && !f.getParentFile().exists())
				f.getParentFile().mkdirs();
		}// End of IF parent is not null

	}

	// Can Firtina
	public static FileWriter createFileWriter(String path) throws IOException {

		File f = new File(path);

		FileWriter fileWriter = null;

		if (f.isDirectory() && !f.exists())
			f.mkdirs();
		else if (!f.isDirectory() && !f.getParentFile().exists())
			f.getParentFile().mkdirs();

		if (!f.isDirectory() && f.exists())
			f.delete();

		fileWriter = new FileWriter(f, false);

		return fileWriter;
	}

	public static FileWriter createFileWriter(String path, boolean appendMode) throws IOException {

		if (!appendMode)
			return createFileWriter(path);

		File f = new File(path);
		FileWriter fileWriter = null;

		if (f.isDirectory() && !f.exists())
			f.mkdirs();
		else if (!f.isDirectory() && !f.getParentFile().exists())
			f.getParentFile().mkdirs();

		fileWriter = new FileWriter(path, appendMode);

		return fileWriter;
	}

	public static FileWriter createFileWriter(String directoryName, String fileName) throws IOException {

		return createFileWriter(directoryName + fileName);
	}

	public static FileReader createFileReader(String directoryName, String fileName) throws IOException {

		return new FileReader(directoryName + fileName);
	}

	public static FileReader createFileReader(String directoryNameandfileName) throws IOException {

		return new FileReader(directoryNameandfileName);
	}

	// Added 18 NOV 2014
	public static BufferedReader createBufferedReader(String outputFolder, String fileName) {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader(outputFolder + fileName);
			bufferedReader = new BufferedReader(fileReader);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return bufferedReader;
	}

	public static void deleteDummyLogFiles(String directoryName, String fileExtension) {
		// Delete dummy log files before new run

		File folder = new File(directoryName);

		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (File file : files) {

				if (file.getAbsolutePath().contains(fileExtension)) {
					deleteFile(directoryName, file.getName());
				}

			}
			folder.delete();
		}
	}

	// Called from GLANET
	public static void deleteOldFiles(String directoryName) {
		// Delete old files before new run
		File folder = new File(directoryName);

		// if(folder.isFile() && folder.getName()!=Commons.GLANET_LOG_FILE){
		if (folder.isFile()) {
			logger.debug("Deleting " + folder.getAbsolutePath());
			folder.delete();
		} else if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (File file : files) {
				deleteOldFiles(file.getAbsolutePath());
			}
			folder.delete();
		}
	}

	public static void deleteOldFiles(String directoryName, List<String> notToBeDeleted) {
		// Delete old files before new run
		File folder = new File(directoryName);

		if (folder.isFile()) {
			GlanetRunner.appendLog("Deleting " + folder.getAbsolutePath());
			folder.delete();
		} else if (folder.isDirectory()) {
			if (!(notToBeDeleted.contains(folder.getName()))) {

				File[] files = folder.listFiles();
				for (File file : files) {
					deleteOldFiles(file.getAbsolutePath(), notToBeDeleted);
				}
				folder.delete();
			}// if it is not in notToBeDeleted
		} // /if it is a directory

	}

	public static void deleteFile(String outputFolder, String fileName) {

		File file = new File(outputFolder + fileName);

		if (file.delete()) {
			logger.debug(file.getName() + " is deleted!");
		} else {
			logger.error("Delete operation is failed.");
		}

	}

	// Added method 31.OCT.2014
	// GET Chromosome Based BufferedWriter
	public static BufferedWriter getChromosomeBasedBufferedWriter(ChromosomeName chromName, List<BufferedWriter> bufferedWriterList) {
		BufferedWriter bufferedWriter = null;

		if (chromName.isCHROMOSOME1()) {
			bufferedWriter = bufferedWriterList.get(0);
		} else if (chromName.isCHROMOSOME2()) {
			bufferedWriter = bufferedWriterList.get(1);
		} else if (chromName.isCHROMOSOME3()) {
			bufferedWriter = bufferedWriterList.get(2);
		} else if (chromName.isCHROMOSOME4()) {
			bufferedWriter = bufferedWriterList.get(3);
		} else if (chromName.isCHROMOSOME5()) {
			bufferedWriter = bufferedWriterList.get(4);
		} else if (chromName.isCHROMOSOME6()) {
			bufferedWriter = bufferedWriterList.get(5);
		} else if (chromName.isCHROMOSOME7()) {
			bufferedWriter = bufferedWriterList.get(6);
		} else if (chromName.isCHROMOSOME8()) {
			bufferedWriter = bufferedWriterList.get(7);
		} else if (chromName.isCHROMOSOME9()) {
			bufferedWriter = bufferedWriterList.get(8);
		} else if (chromName.isCHROMOSOME10()) {
			bufferedWriter = bufferedWriterList.get(9);
		} else if (chromName.isCHROMOSOME11()) {
			bufferedWriter = bufferedWriterList.get(10);
		} else if (chromName.isCHROMOSOME12()) {
			bufferedWriter = bufferedWriterList.get(11);
		} else if (chromName.isCHROMOSOME13()) {
			bufferedWriter = bufferedWriterList.get(12);
		} else if (chromName.isCHROMOSOME14()) {
			bufferedWriter = bufferedWriterList.get(13);
		} else if (chromName.isCHROMOSOME15()) {
			bufferedWriter = bufferedWriterList.get(14);
		} else if (chromName.isCHROMOSOME16()) {
			bufferedWriter = bufferedWriterList.get(15);
		} else if (chromName.isCHROMOSOME17()) {
			bufferedWriter = bufferedWriterList.get(16);
		} else if (chromName.isCHROMOSOME18()) {
			bufferedWriter = bufferedWriterList.get(17);
		} else if (chromName.isCHROMOSOME19()) {
			bufferedWriter = bufferedWriterList.get(18);
		} else if (chromName.isCHROMOSOME20()) {
			bufferedWriter = bufferedWriterList.get(19);
		} else if (chromName.isCHROMOSOME21()) {
			bufferedWriter = bufferedWriterList.get(20);
		} else if (chromName.isCHROMOSOME22()) {
			bufferedWriter = bufferedWriterList.get(21);
		} else if (chromName.isCHROMOSOMEX()) {
			bufferedWriter = bufferedWriterList.get(22);
		} else if (chromName.isCHROMOSOMEY()) {
			bufferedWriter = bufferedWriterList.get(23);
		}

		return bufferedWriter;
	}

	// Added 31.OCT.2014
	public static void closeChromosomeBasedBufferedWriters(List<BufferedWriter> bufferedWriterList) {
		Iterator<BufferedWriter> itr = bufferedWriterList.iterator();

		while (itr.hasNext()) {
			BufferedWriter bw = (BufferedWriter) itr.next();
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Added 15 NOV 2014
	// ENCODE DNASE
	// ENCODE TF
	// ENCODE HISTONE
	// UCSCGENOME REFSEQ GENE
	public static void createUnsortedChromosomeBasedWithNumbersBufferedWriters(String dataFolder, ElementType elementType, List<BufferedWriter> bufferedWriterList) {

		String baseDirectoryName = null;

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		String fileEnd = null;

		switch (elementType) {
			case DNASE:
				baseDirectoryName = dataFolder + Commons.BYGLANET + System.getProperty("file.separator") + Commons.ENCODE + System.getProperty("file.separator") + elementType.convertEnumtoString() + System.getProperty("file.separator");
				fileEnd = Commons.UNSORTED_ENCODE_DNASE_FILE_WITH_NUMBERS;
				break;

			case TF:
				baseDirectoryName = dataFolder + Commons.BYGLANET + System.getProperty("file.separator") + Commons.ENCODE + System.getProperty("file.separator") + elementType.convertEnumtoString() + System.getProperty("file.separator");
				fileEnd = Commons.UNSORTED_ENCODE_TF_FILE_WITH_NUMBERS;
				break;

			case HISTONE:
				baseDirectoryName = dataFolder + Commons.BYGLANET + System.getProperty("file.separator") + Commons.ENCODE + System.getProperty("file.separator") + elementType.convertEnumtoString() + System.getProperty("file.separator");
				fileEnd = Commons.UNSORTED_ENCODE_HISTONE_FILE_WITH_NUMBERS;
				break;

			case HG19_REFSEQ_GENE:
				baseDirectoryName = dataFolder + Commons.BYGLANET + System.getProperty("file.separator") + Commons.UCSCGENOME + System.getProperty("file.separator") + elementType.convertEnumtoString() + System.getProperty("file.separator");
				fileEnd = Commons.UNSORTED_UCSCGENOME_HG19_REFSEQ_GENES_FILE_WITH_NUMBERS;
				break;
		}// End of SWITCH

		try {

			for (int i = 1; i <= 24; i++) {

				// Chromosome X
				if (i == 23) {
					fileWriter = FileOperations.createFileWriter(baseDirectoryName + System.getProperty("file.separator") + Commons.CHR + Commons.X + fileEnd);
				}
				// Chromosome Y
				else if (i == 24) {
					fileWriter = FileOperations.createFileWriter(baseDirectoryName + System.getProperty("file.separator") + Commons.CHR + Commons.Y + fileEnd);
				}
				// Chromosome1..22
				else {
					fileWriter = FileOperations.createFileWriter(baseDirectoryName + System.getProperty("file.separator") + Commons.CHR + i + fileEnd);
				}

				bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriterList.add(bufferedWriter);
			}// End of FOR

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Added 31.OCT.2014
	// UserDefinedLibrary
	public static void createChromosomeBasedListofBufferedWriters(String elementType, int elementTypeNumber, TIntObjectMap<List<BufferedWriter>> elementTypeNumber2ListofBufferedWritersMap, String baseDirectoryName) {

		List<BufferedWriter> listofBufferedWriter = new ArrayList<BufferedWriter>();

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {

			for (int i = 1; i <= 24; i++) {

				// Chromosome X
				if (i == 23) {
					fileWriter = FileOperations.createFileWriter(baseDirectoryName + elementType + System.getProperty("file.separator") + Commons.CHR + Commons.X + Commons.UNSORTED_USERDEFINEDLIBRARY_FILE_WITH_NUMBERS);

				}
				// Chromosome Y
				else if (i == 24) {
					fileWriter = FileOperations.createFileWriter(baseDirectoryName + elementType + System.getProperty("file.separator") + Commons.CHR + Commons.Y + Commons.UNSORTED_USERDEFINEDLIBRARY_FILE_WITH_NUMBERS);

				}
				// Chromosome1..22
				else {
					fileWriter = FileOperations.createFileWriter(baseDirectoryName + elementType + System.getProperty("file.separator") + Commons.CHR + i + Commons.UNSORTED_USERDEFINEDLIBRARY_FILE_WITH_NUMBERS);
				}

				bufferedWriter = new BufferedWriter(fileWriter);
				listofBufferedWriter.add(bufferedWriter);

			}// End of for each chromosome

			elementTypeNumber2ListofBufferedWritersMap.put(elementTypeNumber, listofBufferedWriter);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Added 31.OCT.2014
	public static void writeName(
			String dataFolder, 
			TIntObjectMap<String> number2NameMap, 
			String outputDirectoryName, 
			String outputFileName) {
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {

			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			
			for (int i = 0; i<number2NameMap.size(); i++){
				bufferedWriter.write( number2NameMap.get(i) + System.getProperty("line.separator"));
			}
			

			bufferedWriter.close();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Added 31.OCT.2014
	public static void writeName2NumberMap(String dataFolder, TObjectIntMap<String> name2NumberMap, String outputDirectoryName, String outputFileName) {
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {

			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			for (TObjectIntIterator<String> it = name2NumberMap.iterator(); it.hasNext();) {
				it.advance();
				bufferedWriter.write(it.key() + "\t" + it.value() + System.getProperty("line.separator"));
			}

			bufferedWriter.close();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	//new starts
	public static void writeNumber2NameMap(String dataFolder, TIntIntMap number2NumberMap, String outputDirectoryName, String outputFileName) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			for (TIntIntIterator it = number2NumberMap.iterator(); it.hasNext();) {
				it.advance();
				bufferedWriter.write(it.key() + "\t" + it.value() + System.getProperty("line.separator"));
			}

			bufferedWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//new adds
	
	
	// Added 31.OCT.2014
	public static void writeNumber2NameMap(String dataFolder, TIntObjectMap<String> number2NameMap, String outputDirectoryName, String outputFileName) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			for (TIntObjectIterator<String> it = number2NameMap.iterator(); it.hasNext();) {
				it.advance();
				bufferedWriter.write(it.key() + "\t" + it.value() + System.getProperty("line.separator"));
			}

			bufferedWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//Added 26 March 2015
	public static void writeSortedNumber2NameMap(String dataFolder, TIntIntMap number2NumberMap, String outputDirectoryName, String outputFileName) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(int i = 0; i<number2NumberMap.size(); i++){
				bufferedWriter.write(i + "\t" + number2NumberMap.get(i) + System.getProperty("line.separator"));				
			}

			bufferedWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Added 20.NOV.2014
	public static void writeSortedNumber2NameMap(String dataFolder, TIntObjectMap<String> number2NameMap, String outputDirectoryName, String outputFileName) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(int i = 0; i<number2NameMap.size(); i++){
				bufferedWriter.write(i + "\t" + number2NameMap.get(i) + System.getProperty("line.separator"));				
			}

			bufferedWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void fillName2NumberMap(TObjectShortMap<String> name2NumberMap, String dataFolder, String inputFileName) {
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		int indexofFirstTab;
		short number;
		String name;

		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				indexofFirstTab = strLine.indexOf('\t');
				name = strLine.substring(0, indexofFirstTab);
				number = Short.parseShort(strLine.substring(indexofFirstTab + 1));
				name2NumberMap.put(name, number);
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
	
	
	public static void fillNumber2NumberMap(TIntIntMap number2NumberMap, String dataFolder, String inputFileName) {
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		int indexofFirstTab;

		int number1;
		int number2;

		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				indexofFirstTab = strLine.indexOf('\t');
				number1 = Integer.parseInt(strLine.substring(0, indexofFirstTab));
				number2 = Integer.parseInt(strLine.substring(indexofFirstTab + 1));
				number2NumberMap.put(number1, number2);
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
	
	//Using char[][]
	public static void fillNameList(
			char[][] nameList, 
			String dataFolder, 
			String inputFileName){
		
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		int indexofFirstTab;

		int nameCount = 0;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				
				indexofFirstTab = strLine.indexOf('\t');
				
				nameList[nameCount]= strLine.substring(indexofFirstTab + 1).toCharArray();
				
				nameCount++;
				
				strLine = null;
			}//End of While
			
			
			bufferedReader.close();
			fileReader.close();
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	

	public static void fillNumber2NameMap(TIntObjectMap<String> number2NameMap, String dataFolder, String inputFileName) {
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		int indexofFirstTab;

		int number;
		String name;

		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				indexofFirstTab = strLine.indexOf('\t');
				number = Integer.parseInt(strLine.substring(0, indexofFirstTab));
				name = strLine.substring(indexofFirstTab + 1);
				number2NameMap.put(number, name);
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
	
	//TroveMap using StringBuilder
	public static void fillNumber2NameStringBuilderMap(TShortObjectMap<StringBuilder> number2NameMap, String dataFolder, String inputFileName) {
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		int indexofFirstTab;
		short number;
		StringBuilder name = new StringBuilder(16);

		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				indexofFirstTab = strLine.indexOf('\t');
				
				number = Short.parseShort(strLine.substring(0, indexofFirstTab));
				
				name = new StringBuilder(16).append(strLine.substring(indexofFirstTab + 1));

				name.trimToSize();
				
				number2NameMap.put(number, name);
				
				
				
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
	//new ends
		

	//TroveMap using CharSequence
	public static void fillNumber2NameCharSequenceMap(TShortObjectMap<CharSequence> number2NameMap, String dataFolder, String inputFileName) {
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		int indexofFirstTab;
		short number;
		CharSequence name;

		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				indexofFirstTab = strLine.indexOf('\t');
				
				number = Short.parseShort(strLine.substring(0, indexofFirstTab));
				
				name = strLine.subSequence(indexofFirstTab + 1,strLine.length());
				
				
				number2NameMap.put(number, name);
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
	//new ends
	
	//TroveMap Using String
	public static void fillNumber2NameMap(TShortObjectMap<String> number2NameMap, String dataFolder, String inputFileName) {
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		int indexofFirstTab;
		short number;
		String name;

		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				indexofFirstTab = strLine.indexOf('\t');
				number = Short.parseShort(strLine.substring(0, indexofFirstTab));
				name = strLine.substring(indexofFirstTab + 1);
				number2NameMap.put(number, name);
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

	public static void fillList(List<String> list, String dataFolder, String inputFileName) {
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				list.add(strLine);
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

	public static void fillHashMap(Map<String, Integer> hashMap, String inputFileName) {
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
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

	public static void readNames(String dataFolder, List<String> nameList, String inputDirectoryName, String inputFileName) {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine;

		try {

			fileReader = FileOperations.createFileReader(dataFolder + inputDirectoryName, inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				if (!nameList.contains(strLine)) {
					nameList.add(strLine);
				}
			}// End of While

			bufferedReader.close();
			fileReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void createChromBaseSearchInputFiles(String outputFolder, List<FileWriter> fileWriterList, List<BufferedWriter> bufferedWriterList) {
		try {

			// For each ChromosomeName
			for (ChromosomeName chrName : ChromosomeName.values()) {

				FileWriter fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + ChromosomeName.convertEnumtoString(chrName) + Commons.CHROMOSOME_BASED_GIVEN_INPUT);
				fileWriterList.add(fileWriter);

				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriterList.add(bufferedWriter);

			}// End of for each chromosomeName

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 25 NOV 2014
	public static void readFromBedFileWriteToGlanetFile(String folderName, String inputFileName, String outputFileName) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		String strLine;

		String chrName;
		int oneBasedStart;
		int oneBasedEnd;

		int zeroBasedStart;
		int zeroBasedEnd;

		int indexofFirstTab;
		int indexofSecondTab;

		int numberofProcessedInputLinesForGLANET = 0;

		try {
			fileReader = createFileReader(folderName, inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			fileWriter = createFileWriter(folderName, outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			while ((strLine = bufferedReader.readLine()) != null) {

				if (!strLine.startsWith(Commons.NULL) && strLine.charAt(0) != Commons.GLANET_COMMENT_CHARACTER) {

					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = (indexofFirstTab > 0) ? strLine.indexOf('\t', indexofFirstTab + 1) : -1;

					chrName = strLine.substring(0, indexofFirstTab);
					oneBasedStart = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));
					oneBasedEnd = Integer.parseInt(strLine.substring(indexofSecondTab + 1));

					zeroBasedStart = oneBasedStart - 1;
					zeroBasedEnd = oneBasedEnd - 1;

					bufferedWriter.write(chrName + "\t" + zeroBasedStart + "\t" + zeroBasedEnd + System.getProperty("line.separator"));
					numberofProcessedInputLinesForGLANET++;

				}// End of IF NOT NULL

			}// End of While

			logger.info("******************************************************************************");
			logger.info("Number of given input lines ready for GLANET execution: " + numberofProcessedInputLinesForGLANET);
			logger.info("******************************************************************************");

			// Close bufferedReader and bufferedWriter
			bufferedReader.close();
			bufferedWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public FileOperations() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}