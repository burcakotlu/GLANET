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
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileDeletion {
	
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

	/**
	 * 
	 */
	public FileDeletion() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
