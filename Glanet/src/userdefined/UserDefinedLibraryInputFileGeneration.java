/**
 * 
 */
package userdefined;

import java.io.FileWriter;

/**
 * @author burcakotlu
 *
 */
public class UserDefinedLibraryInputFileGeneration {


	public static void readFileNamesUnderGivenDirectoryAndCreateInputFile(String dnaseDirectory1,String elementType, String userDefinedLibraryInputFile){
	
		FileWriter fileWriter = new FileWriter(userDefinedLibraryInputFile,true);
		BufferedWriter bufferedWR
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
