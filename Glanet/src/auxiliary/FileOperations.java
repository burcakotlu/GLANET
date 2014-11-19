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

import ui.GlanetRunner;
import common.Commons;
import enumtypes.ChromosomeName;
import enumtypes.ElementType;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;

public class FileOperations {
	
	//Can Firtina
	public static FileWriter createFileWriter(String path) throws IOException{
		
		File f = new File(path);
		FileWriter fileWriter = null;
		
		if(f.isDirectory() && !f.exists())
			f.mkdirs();
		else if( !f.isDirectory() && !f.getParentFile().exists())
			f.getParentFile().mkdirs();
		
		fileWriter = new FileWriter(path);
		
		return fileWriter;
	}
	
	public static FileWriter createFileWriter(String path,boolean appendMode) throws IOException{
		
		File f = new File(path);
		FileWriter fileWriter = null;
		
		if(f.isDirectory() && !f.exists())
			f.mkdirs();
		else if( !f.isDirectory() && !f.getParentFile().exists())
			f.getParentFile().mkdirs();
		
		fileWriter = new FileWriter(path,appendMode);
		
		return fileWriter;
	}
	
	public static FileWriter createFileWriter(String directoryName, String fileName) throws IOException{
		//First check whether this directory is already created
		File pathDirectory = new File(directoryName);
		
		//creates the directory named by this abstract pathname, including necessary and non-existent parent directories.
		pathDirectory.mkdirs();
		 		
		return new FileWriter(directoryName+fileName);
	}
	
	
	public static FileReader createFileReader(String directoryName, String fileName) throws IOException{
		 
		return new FileReader(directoryName+fileName);
	}
	
	public static FileReader createFileReader(String directoryNameandfileName) throws IOException{
		 
		return new FileReader(directoryNameandfileName);
	}
	
	//Added 18 NOV 2014
	public static BufferedReader createBufferedReader(String outputFolder, String fileName){
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
	
	
	//Called from GLANET 
	public static void deleteOldFiles(String directoryName){
		//Delete old files before new run 
		File folder = new File(directoryName);
		
		 if(folder.isFile()){			 
			 GlanetRunner.appendLog("Deleting " + folder.getAbsolutePath());	
			 folder.delete();
	     }else if(folder.isDirectory()) {	    		 
	    		 File[] files = folder.listFiles();
	    		 for(File file: files){
	    			 deleteOldFiles(file.getAbsolutePath());
	    		 }	
	    		 folder.delete();	 
	     }  				
	}
	
	
	public static void deleteOldFiles(String directoryName,List<String> notToBeDeleted){
		//Delete old files before new run 
		File folder = new File(directoryName);
		
		 if(folder.isFile()){
			 GlanetRunner.appendLog("Deleting " + folder.getAbsolutePath());	
			 folder.delete();
	     }else if(folder.isDirectory()) {
	    	 if (!(notToBeDeleted.contains(folder.getName()))){
	    		 
	    		 File[] files = folder.listFiles();
	    		 for(File file: files){
	    			 deleteOldFiles(file.getAbsolutePath(),notToBeDeleted);
	    		 }	
	    		 folder.delete();
			}//if it is not in notToBeDeleted	       
	     }  ///if it is a directory
				
	}
	
	
	

	
	public static void deleteFile(String outputFolder,String fileName){
		
		File file = new File(outputFolder + fileName);
		
		if(file.delete()){
			GlanetRunner.appendLog(file.getName() + " is deleted!");
		}else{
			GlanetRunner.appendLog("Delete operation is failed.");
		}
		
	}
	

	

	//Added method 31.OCT.2014
	//GET Chromosome Based BufferedWriter
	public static BufferedWriter getChromosomeBasedBufferedWriter(ChromosomeName chromName,List<BufferedWriter> bufferedWriterList){
		BufferedWriter bufferedWriter = null;
		
		if (chromName.isCHROMOSOME1()){
			bufferedWriter = bufferedWriterList.get(0);
		}else if (chromName.isCHROMOSOME2()){
			bufferedWriter = bufferedWriterList.get(1);			
		}else if (chromName.isCHROMOSOME3()){
			bufferedWriter = bufferedWriterList.get(2);			
		}else if (chromName.isCHROMOSOME4()){
			bufferedWriter = bufferedWriterList.get(3);			
		}else if (chromName.isCHROMOSOME5()){
			bufferedWriter = bufferedWriterList.get(4);			
		}else if (chromName.isCHROMOSOME6()){
			bufferedWriter = bufferedWriterList.get(5);			
		}else if (chromName.isCHROMOSOME7()){
			bufferedWriter = bufferedWriterList.get(6);			
		}else if (chromName.isCHROMOSOME8()){
			bufferedWriter = bufferedWriterList.get(7);			
		}else if (chromName.isCHROMOSOME9()){
			bufferedWriter = bufferedWriterList.get(8);			
		}else if (chromName.isCHROMOSOME10()){
			bufferedWriter = bufferedWriterList.get(9);			
		}else if (chromName.isCHROMOSOME11()){
			bufferedWriter = bufferedWriterList.get(10);			
		}else if (chromName.isCHROMOSOME12()){
			bufferedWriter = bufferedWriterList.get(11);			
		}else if (chromName.isCHROMOSOME13()){
			bufferedWriter = bufferedWriterList.get(12);			
		}else if (chromName.isCHROMOSOME14()){
			bufferedWriter = bufferedWriterList.get(13);			
		}else if (chromName.isCHROMOSOME15()){
			bufferedWriter = bufferedWriterList.get(14);			
		}else if (chromName.isCHROMOSOME16()){
			bufferedWriter = bufferedWriterList.get(15);			
		}else if (chromName.isCHROMOSOME17()){
			bufferedWriter = bufferedWriterList.get(16);			
		}else if (chromName.isCHROMOSOME18()){
			bufferedWriter = bufferedWriterList.get(17);			
		}else if (chromName.isCHROMOSOME19()){
			bufferedWriter = bufferedWriterList.get(18);			
		}else if (chromName.isCHROMOSOME20()){
			bufferedWriter = bufferedWriterList.get(19);			
		}else if (chromName.isCHROMOSOME21()){
			bufferedWriter = bufferedWriterList.get(20);			
		}else if (chromName.isCHROMOSOME22()){
			bufferedWriter = bufferedWriterList.get(21);			
		}else if (chromName.isCHROMOSOMEX()){
			bufferedWriter = bufferedWriterList.get(22);			
		}else if (chromName.isCHROMOSOMEY()){
			bufferedWriter = bufferedWriterList.get(23);			
		}
		
		return bufferedWriter;
	}
	
	
	//Added 31.OCT.2014
	public static void closeChromosomeBasedBufferedWriters(List<BufferedWriter> bufferedWriterList){
		Iterator<BufferedWriter> itr = bufferedWriterList.iterator();
		
		while (itr.hasNext()){
			BufferedWriter bw = (BufferedWriter) itr.next();
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	//Added 15 NOV 2014
	public static void createUnsortedChromosomeBasedWithNumbersBufferedWriters(
			String dataFolder,
			ElementType elementType, 
			List<BufferedWriter> bufferedWriterList){
		
		String baseDirectoryName = null;

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String fileEnd = null;
		
		switch(elementType) {
			case DNASE:  	baseDirectoryName = dataFolder + Commons.BYGLANET + System.getProperty("file.separator") + Commons.ENCODE + System.getProperty("file.separator") + elementType.convertEnumtoString() + System.getProperty("file.separator");
							fileEnd = 		Commons.UNSORTED_ENCODE_DNASE_FILE_WITH_NUMBERS; 
							break;
										
			case TF:  	baseDirectoryName = dataFolder + Commons.BYGLANET + System.getProperty("file.separator") + Commons.ENCODE + System.getProperty("file.separator") + elementType.convertEnumtoString() + System.getProperty("file.separator");
						fileEnd = 		Commons.UNSORTED_ENCODE_TF_FILE_WITH_NUMBERS; 
						break;
										
			case HISTONE:  	baseDirectoryName = dataFolder + Commons.BYGLANET + System.getProperty("file.separator") + Commons.ENCODE + System.getProperty("file.separator") + elementType.convertEnumtoString() + System.getProperty("file.separator");
							fileEnd = 	Commons.UNSORTED_ENCODE_HISTONE_FILE_WITH_NUMBERS; 
							break;
										
			case HG19_REFSEQ_GENE: 		baseDirectoryName = dataFolder + Commons.BYGLANET + System.getProperty("file.separator") + Commons.UCSCGENOME + System.getProperty("file.separator") + elementType.convertEnumtoString() + System.getProperty("file.separator");
										fileEnd = Commons.UNSORTED_UCSCGENOME_HG19_REFSEQ_GENES_FILE_WITH_NUMBERS;
										break;
		}//End of SWITCH
				
			
		try {
		
			for(int i=1; i<=24 ;i++){
				
				//Chromosome X
				if(i == 23){
					fileWriter = FileOperations.createFileWriter(baseDirectoryName  + System.getProperty("file.separator") + Commons.CHR + Commons.X + fileEnd);
				}
				//Chromosome Y
				else if (i == 24){
					fileWriter = FileOperations.createFileWriter(baseDirectoryName  + System.getProperty("file.separator") + Commons.CHR + Commons.Y + fileEnd);
				}
				//Chromosome1..22
				else{
					fileWriter = FileOperations.createFileWriter(baseDirectoryName + System.getProperty("file.separator") + Commons.CHR + i + fileEnd);
				}
				
				bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriterList.add(bufferedWriter);
			}//End of FOR
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	//Added 31.OCT.2014
	public static void 	createChromosomeBasedListofBufferedWriters(
			String elementType,
			int elementTypeNumber,
			TIntObjectMap<List<BufferedWriter>> elementTypeNumber2ListofBufferedWritersMap,
			String baseDirectoryName){
		
			List<BufferedWriter> listofBufferedWriter = new ArrayList<BufferedWriter>();
		
			FileWriter fileWriter = null;
			BufferedWriter bufferedWriter = null;
			
			
			try {
						
				for(int i=1; i<=24 ;i++){
					
					//Chromosome X
					if(i == 23){
						fileWriter = FileOperations.createFileWriter(baseDirectoryName + elementType + System.getProperty("file.separator") + Commons.CHR + Commons.X + Commons.UNSORTED_USERDEFINEDLIBRARY_FILE_WITH_NUMBERS);
						
					}
					//Chromosome Y
					else if (i == 24){
						fileWriter = FileOperations.createFileWriter(baseDirectoryName + elementType + System.getProperty("file.separator") + Commons.CHR + Commons.Y + Commons.UNSORTED_USERDEFINEDLIBRARY_FILE_WITH_NUMBERS);
						
					}
					//Chromosome1..22
					else{
						fileWriter = FileOperations.createFileWriter(baseDirectoryName + elementType + System.getProperty("file.separator") + Commons.CHR + i + Commons.UNSORTED_USERDEFINEDLIBRARY_FILE_WITH_NUMBERS);
					}
					
					bufferedWriter = new BufferedWriter(fileWriter);
					listofBufferedWriter.add(bufferedWriter);
					
				}//End of for each chromosome
				
				elementTypeNumber2ListofBufferedWritersMap.put(elementTypeNumber,listofBufferedWriter);
				
					
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
	}
	
	
	//Added 31.OCT.2014
	public static void writeName(String dataFolder,TObjectIntMap<String> name2NumberMap, String outputDirectoryName, String outputFileName){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
		
			for ( TObjectIntIterator<String> it = name2NumberMap.iterator(); it.hasNext(); ) {
				   it.advance();
				   bufferedWriter.write(it.key() + System.getProperty("line.separator"));		    
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}
	
	//Added 31.OCT.2014
	public static void writeName2NumberMap(String dataFolder,TObjectIntMap<String> name2NumberMap, String outputDirectoryName, String outputFileName){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
		
			for ( TObjectIntIterator<String> it = name2NumberMap.iterator(); it.hasNext(); ) {
				   it.advance();
				   bufferedWriter.write(it.key()+ "\t" + it.value() + System.getProperty("line.separator"));		    
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}
	
	
	//Added 31.OCT.2014
	public static void writeNumber2NameMap(String dataFolder,TIntObjectMap<String> number2NameMap, String outputDirectoryName, String outputFileName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for ( TIntObjectIterator<String> it = number2NameMap.iterator(); it.hasNext(); ) {
				   it.advance();
				   bufferedWriter.write(it.key()+ "\t" + it.value() + System.getProperty("line.separator"));		    
			}
			
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
