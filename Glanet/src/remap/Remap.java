/**
 * 
 */
package remap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Nov 25, 2014
 * @project Glanet 
 *
 */
public class Remap {
	
	
	public static void remap(
			String dataFolder,
			String sourceAssembly, 
			String targetAssembly,
			String sourceFileName,
			String outputFileName){
		
		String remapFile = dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator")  + "remap_api.pl";
		
		//DirectoryName can contain empty spaces
	
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
			
			try {
				process = runtime.exec("perl "  + "\"" + remapFile + "\"" + " --mode asm-asm --from " + sourceAssembly  + " --dest " +  targetAssembly +  " --annotation " + "\"" + sourceFileName + "\"" +  " --annot_out "+  "\"" + outputFileName + "\"" );
				process.waitFor();
				
				//output of the perl execution is here
				BufferedReader is = new BufferedReader( new InputStreamReader( process.getInputStream()));
				String line;
				while ( ( line = is.readLine()) != null)
					System.out.println(line);
				
				System.out.flush();
				
				System.err.println("\nExit status = " + process.exitValue());
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		return;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String glanetFolder = args[1];
		String dataFolder = glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator");
		
		
//		remap(dataFolder,"GCF_000001405.25","GCF_000001405.26","C:\\Users\\Burçak\\Google Drive\\Output\\NoName\\GivenInputData\\chrName_0Based_StartInclusive_EndExclusive_hg38_coordinates.bed","C:\\Users\\Burçak\\Google Drive\\Output\\NoName\\GivenInputData\\chrName_0Based_StartInclusive_EndExclusive_hg19_coordinates.bed");
		remap(dataFolder,"GCF_000001405.26","GCF_000001405.25","C:\\Users\\Burçak\\Developer\\Java\\GLANET\\Glanet\\src\\remap\\chrName_0Based_StartInclusive_EndExclusive_hg38_coordinates.bed","C:\\Users\\Burçak\\Developer\\Java\\GLANET\\Glanet\\src\\remap\\chrName_0Based_StartInclusive_EndExclusive_hg19_coordinates.bed");

//		GCF_000001405.25 <----> GRCh37.p13
		
//		GCF_000001405.26 <------> GRCh38
		
//		GCF_000001405.27 <------> GRCh38.p1
		
	}

}
