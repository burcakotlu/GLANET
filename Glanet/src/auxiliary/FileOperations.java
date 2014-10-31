/**
 * @author Burcak Otlu
 * Feb 6, 2014
 * 3:36:37 PM
 * 2014
 *
 * 
 */
package auxiliary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import common.Commons;

import enumtypes.ChromosomeName;
import gnu.trove.map.TIntObjectMap;
import ui.GlanetRunner;

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
	
//	//attention gives java.nio.file.FileSystemException
//	//The process cannot access the file because it is being used by another process.
//	//Delete all directories under this base directory name
//	//Unless any directory name is in list of notToBeDeleted 
//	public static void deleteDirectoriesandFilesUnderThisDirectory(String baseDirectoryName,List<String> notToBeDeleted){
//		File folder = new File(baseDirectoryName);
//		if (folder.exists()){
//			for(File file:folder.listFiles()){
//							
//				if (!(notToBeDeleted.contains(file.getName()))){
//					deleteDirectoriesandFiles(file.getPath());
//					
//				}
//				
//			}
//		}
//		
//		
//		
//	}
//	
//	
//	//attention gives java.nio.file.FileSystemException
//	//The process cannot access the file because it is being used by another process.
//	public static void deleteDirectoriesandFilesUnderThisDirectory(String baseDirectoryName){
//		File folder = new File(baseDirectoryName);
//		
//		if (folder.exists()){
//			for(File file:folder.listFiles()){
//				deleteDirectoriesandFiles(file.getPath());
//			}
//		}
//				
//	}
//	
//	//attention gives java.nio.file.FileSystemException
//	//The process cannot access the file because it is being used by another process.
//	public static void deleteDirectoriesandFilesUnderThisDirectory(String outputFolder, String baseDirectoryName){
//		File folder = new File(outputFolder + baseDirectoryName);
//		
//		if (folder.exists()){
//			for(File file:folder.listFiles()){
//				deleteDirectoriesandFiles(file.getPath());
//			}
//		}
//				
//	}
//	
//	//attention gives java.nio.file.FileSystemException
//	//The process cannot access the file because it is being used by another process.
//	public static void deleteDirectoriesandFiles(String baseDirectoryName){
//		
//		Path dir = Paths.get(baseDirectoryName);
//		try {
//		  Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
//		 
//		      @Override
//		      public FileVisitResult visitFile(Path file,
//		              BasicFileAttributes attrs) throws IOException {
//		 
//		          GlanetRunner.appendLog("Deleting file: " + file);
//		          Files.delete(file);
//		          return FileVisitResult.CONTINUE;
//		      }
//		 
//		      @Override
//		      public FileVisitResult postVisitDirectory(Path dir,
//		              IOException exc) throws IOException {
//		 
//		          GlanetRunner.appendLog("Deleting dir: " + dir);
//		          if (exc == null) {
//		              Files.delete(dir);
//		              return FileVisitResult.CONTINUE;
//		          } else {
//		              throw exc;
//		          }
//		      }
//		 
//		  });
//		} catch (IOException e) {
//		  e.printStackTrace();
//		}
//		
//	}
	
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
