/**
 * @author Burcak Otlu
 * Feb 6, 2014
 * 3:36:37 PM
 * 2014
 *
 * 
 */
package auxiliary;

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

public class FileOperations {
	
	//Can Fýrtýna
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
	
	public static void deleteDirectoriesandFilesUnderThisDirectory(String baseDirectoryName){
		File folder = new File(baseDirectoryName);
		for(File file:folder.listFiles()){
			deleteDirectoriesandFiles(file.getPath());
		}
		
	}
	
	public static void deleteDirectoriesandFiles(String baseDirectoryName){
		
		Path dir = Paths.get(baseDirectoryName);
		try {
		  Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
		 
		      @Override
		      public FileVisitResult visitFile(Path file,
		              BasicFileAttributes attrs) throws IOException {
		 
		          System.out.println("Deleting file: " + file);
		          Files.delete(file);
		          return FileVisitResult.CONTINUE;
		      }
		 
		      @Override
		      public FileVisitResult postVisitDirectory(Path dir,
		              IOException exc) throws IOException {
		 
		          System.out.println("Deleting dir: " + dir);
		          if (exc == null) {
		              Files.delete(dir);
		              return FileVisitResult.CONTINUE;
		          } else {
		              throw exc;
		          }
		      }
		 
		  });
		} catch (IOException e) {
		  e.printStackTrace();
		}
		
	}
	
	public static void deleteOldFiles(String directoryName){
		System.out.println("Start deleting old files...");
		//Delete old files before new run 
		File folder = new File(directoryName);
		deleteOldFiles(folder);
		System.out.println("Old files are deleted");
	}
	
	
	public static void deleteOldFiles(File folder){
		File[] files = folder.listFiles();
		 
	    for(File file: files){
	        if(file.isFile()){
	            file.delete();
	        }else if(file.isDirectory()) {
	         		deleteOldFiles(file);
	        }  
	    }
	}
	
	public static void deleteFile(String fileName){
		
		File file = new File(fileName);
		
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
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
