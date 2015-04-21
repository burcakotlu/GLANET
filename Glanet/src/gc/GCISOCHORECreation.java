/**
 * 
 */
package gc;

import common.Commons;
import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;

/**
 * @author Burçak Otlu
 * @date Apr 21, 2015
 * @project Glanet 
 *
 */
public class GCISOCHORECreation {
	
	public static void createGCIsochoreFile(String dataFolder,ChromosomeName chrName){
		
	}
	

	
	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");
		
		for(ChromosomeName chrName: ChromosomeName.values()){
			createGCIsochoreFile(dataFolder,chrName);
		}
		

	}

}
