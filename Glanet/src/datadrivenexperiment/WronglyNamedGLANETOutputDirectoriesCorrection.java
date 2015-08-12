/**
 * 
 */
package datadrivenexperiment;

import java.io.File;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Aug 12, 2015
 * @project Glanet 
 *
 */
public class WronglyNamedGLANETOutputDirectoriesCorrection {
	
	public static void correctWronglyNamedGLANETOutputDirectories(String glanetOutputFolder){
		
		
		File glanetOutputDirectory = null;
		
		glanetOutputDirectory = new File(glanetOutputFolder + System.getProperty( "file.separator"));

		
		int indexofFirstWo;
		String directoryNameWithoutLastWo;
		
		
		for( File eachGLANETOutputFile : glanetOutputDirectory.listFiles()){

			if( eachGLANETOutputFile.isDirectory() && 
					(eachGLANETOutputFile.getAbsolutePath().endsWith("Wo"))) {
				
				indexofFirstWo = eachGLANETOutputFile.getAbsolutePath().indexOf("Wo");
				
				directoryNameWithoutLastWo = eachGLANETOutputFile.getAbsolutePath().substring(0, indexofFirstWo);
				
				eachGLANETOutputFile.renameTo(new File(directoryNameWithoutLastWo));

			
			} // End of IF
			
		}	// End of FOR
				

		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String glanetFolder = args[0];
		
		String outputFolder = glanetFolder + System.getProperty( "file.separator") + Commons.OUTPUT + System.getProperty( "file.separator");
		
		correctWronglyNamedGLANETOutputDirectories(outputFolder);
	}

}
