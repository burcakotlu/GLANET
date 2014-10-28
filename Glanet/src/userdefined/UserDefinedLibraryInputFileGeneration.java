/**
 * 
 */
package userdefined;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import annotation.AnnotateGivenIntervalsWithNumbersWithChoices;
import auxiliary.FileOperations;



/**
 * @author burcakotlu
 *
 */
public class UserDefinedLibraryInputFileGeneration {


	public static void readFileNamesUnderGivenDirectoryAndCreateInputFile(String dnaseDirectory1,String elementType, String userDefinedLibraryInputFile){
	
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		try{
			fileWriter = FileOperations.createFileWriter(userDefinedLibraryInputFile,true);
			bufferedWriter = new BufferedWriter(fileWriter);

		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		//Read the given directories
		//Create UserDefinedLibraryInputFile
		
		String dnaseDirectory1 	= 	"E:\\DOKTORA_DATA\\ENCODE\\dnase";
		String dnaseDirectory2 	=	"E:\\DOKTORA_DATA\\ENCODE\\dnase_jul2010";
		String histoneDirectory	=	"E:\\DOKTORA_DATA\\ENCODE\\histone_macs";
		String tfDirectory 		=	"E:\\DOKTORA_DATA\\ENCODE\\transcription_factors";
		
		String userDefinedLibraryInputFile = "E:\\DOKTORA_DATA\\UserDefinedLibrary\\UserDefinedLibraryInputFile.txt";
		
		
		readFileNamesUnderGivenDirectoryAndCreateInputFile(dnaseDirectory1,"DNASE",userDefinedLibraryInputFile);
		readFileNamesUnderGivenDirectoryAndCreateInputFile(dnaseDirectory2,"DNASE",userDefinedLibraryInputFile);
		readFileNamesUnderGivenDirectoryAndCreateInputFile(histoneDirectory,"HISTONE",userDefinedLibraryInputFile);
		readFileNamesUnderGivenDirectoryAndCreateInputFile(tfDirectory,"TF",userDefinedLibraryInputFile);
		
	}

}
