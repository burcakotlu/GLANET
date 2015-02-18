/**
 * 
 */
package userdefined;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import create.encode.CellLineHistone;
import create.encode.CellLineTranscriptionFactor;
import create.encode.CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles;

/**
 * @author burcakotlu
 *
 */
public class UserDefinedLibraryInputFileGeneration {

	public static void addHeaderLine(String userDefinedLibraryInputFile) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = FileOperations.createFileWriter(userDefinedLibraryInputFile, true);
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write("![1. Column: FilePath_FileName]" + "\t" + "[2. Column: ElementType]" + "\t" + "[3. Column: ElementName]" + "\t" + "[4. Column: Optional Column for considering interval around summit in case of TF Data]" + System.getProperty("line.separator"));

			bufferedWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void readFileNamesUnderGivenDirectoryAndCreateUserDefinedLibraryInputFile(File directory, String elementType, String userDefinedLibraryInputFile) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = FileOperations.createFileWriter(userDefinedLibraryInputFile, true);
			bufferedWriter = new BufferedWriter(fileWriter);

			if (!directory.exists()) {
				GlanetRunner.appendLog("No File/Dir " + directory.getName());
			}

			// Reading directory contents
			if (directory.isDirectory()) {// a directory!

				File[] files = directory.listFiles();
				int numberofDnaseFiles = files.length;

				System.out.printf("Number of Dnase Files %d in %s" + System.getProperty("line.separator"), files.length, directory.getAbsolutePath());

				for (int i = 0; i < numberofDnaseFiles; i++) {

					if (files[i].isFile()) {

						File file = files[i];

						String fileName = file.getName();
						String filePath = file.getPath();

						if (Commons.DNASE.equals(elementType)) {

							String cellLineDnase = null;

							// Get the cell line name from file name
							cellLineDnase = CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles.getCellLineName(fileName);

							bufferedWriter.write(filePath + "\t" + elementType + "\t" + cellLineDnase + System.getProperty("line.separator"));

							cellLineDnase = null;

						} else if (Commons.TF.equals(elementType)) {

							CellLineTranscriptionFactor cellLineandTranscriptionFactorName = new CellLineTranscriptionFactor();

							// Get the cell line name and transcription factor
							// name from file name
							CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles.getCellLineNameandTranscriptionFactorName(cellLineandTranscriptionFactorName, fileName);

							bufferedWriter.write(filePath + "\t" + elementType + "\t" + cellLineandTranscriptionFactorName.getTranscriptionFactorName() + "_" + cellLineandTranscriptionFactorName.getCellLineName() + System.getProperty("line.separator"));

							cellLineandTranscriptionFactorName = null;

						} else if (Commons.HISTONE.equals(elementType)) {

							CellLineHistone cellLineNameHistoneName = new CellLineHistone();

							// Get the cell line name and histone name from file
							// name
							CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles.getCellLineNameandHistoneName(cellLineNameHistoneName, fileName);

							bufferedWriter.write(filePath + "\t" + elementType + "\t" + cellLineNameHistoneName.getHistoneName() + "_" + cellLineNameHistoneName.getCellLineName() + System.getProperty("line.separator"));

							cellLineNameHistoneName = null;
						}

					}// End of if it is a file
				} // End of For each file in the directory

			}// End of if: this is a directory

			bufferedWriter.close();
			;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Read the given directories
		// Create UserDefinedLibraryInputFile

		File dnaseDirectory1 = new File("G:\\DOKTORA_DATA" + System.getProperty("file.separator") + Commons.ENCODE_DNASE_DIRECTORY1);
		File dnaseDirectory2 = new File("G:\\DOKTORA_DATA" + System.getProperty("file.separator") + Commons.ENCODE_DNASE_DIRECTORY2);
		File tfDirectory = new File("G:\\DOKTORA_DATA" + System.getProperty("file.separator") + Commons.ENCODE_TFBS_DIRECTORY);
		File histoneDirectory = new File("G:\\DOKTORA_DATA" + System.getProperty("file.separator") + Commons.ENCODE_HISTONE_DIRECTORY);

		String userDefinedLibraryInputFile = "G:\\DOKTORA_DATA\\UserDefinedLibrary\\UserDefinedLibraryInputFile.txt";

		// Delete Old File
		FileOperations.deleteOldFiles("G:\\DOKTORA_DATA\\UserDefinedLibrary\\");

		// Add Header Line
		addHeaderLine(userDefinedLibraryInputFile);

		readFileNamesUnderGivenDirectoryAndCreateUserDefinedLibraryInputFile(dnaseDirectory1, Commons.DNASE, userDefinedLibraryInputFile);
		readFileNamesUnderGivenDirectoryAndCreateUserDefinedLibraryInputFile(dnaseDirectory2, Commons.DNASE, userDefinedLibraryInputFile);
		readFileNamesUnderGivenDirectoryAndCreateUserDefinedLibraryInputFile(histoneDirectory, Commons.HISTONE, userDefinedLibraryInputFile);
		readFileNamesUnderGivenDirectoryAndCreateUserDefinedLibraryInputFile(tfDirectory, Commons.TF, userDefinedLibraryInputFile);

	}

}
