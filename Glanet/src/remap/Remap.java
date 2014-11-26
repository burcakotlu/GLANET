/**
 * 
 */
package remap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import auxiliary.FileOperations;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Nov 25, 2014
 * @project Glanet 
 *
 */
public class Remap {
	
	public static void fillAssemblyName2RefSeqAssemblyIDMap(
			String dataFolder,
			String supportedAssembliesFileName,
			Map<String,String> assemblyName2RefSeqAssemblyIDMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine;
		
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		
		String species;
		String sourceAssemblyName;
		String sourceRefSeqAssemblyId;
		String targetAssemblyName;
		String targetRefSeqAssemblyId;
		
		try {
			fileReader = FileOperations.createFileReader(dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator"), supportedAssembliesFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while ((strLine = bufferedReader.readLine())!=null){
				
				if (!strLine.startsWith("#")){
					//Example line
					//#batch_id	query_species	query_name	query_ucsc	query_acc	target_species	target_name	target_ucsc	target_acc	alignment_date
					//85213	Homo sapiens	NCBI33		GCF_000001405.8	Homo sapiens	GRCh38	hg38	GCF_000001405.26	09/20/2014
					//85273	Homo sapiens	NCBI36	hg18	GCF_000001405.12	Homo sapiens	GRCh38	hg38	GCF_000001405.26	09/20/2014
					//85283	Homo sapiens	GRCh37.p11		GCF_000001405.23	Homo sapiens	GRCh38	hg38	GCF_000001405.26	09/20/2014

					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= (indexofFirstTab>0) ? strLine.indexOf('\t', indexofFirstTab+1) :-1;
					indexofThirdTab 	= (indexofSecondTab>0) ? strLine.indexOf('\t', indexofSecondTab+1) :-1;
					indexofFourthTab 	= (indexofThirdTab>0) ? strLine.indexOf('\t', indexofThirdTab+1) :-1;
					indexofFifthTab 	= (indexofFourthTab>0) ? strLine.indexOf('\t', indexofFourthTab+1) :-1;
					indexofSixthTab 	= (indexofFifthTab>0) ? strLine.indexOf('\t', indexofFifthTab+1) :-1;
					indexofSeventhTab 	= (indexofSixthTab>0) ? strLine.indexOf('\t', indexofSixthTab+1) :-1;
					indexofEigthTab 	= (indexofSeventhTab>0) ? strLine.indexOf('\t', indexofSeventhTab+1) :-1;
					indexofNinethTab 	= (indexofEigthTab>0) ? strLine.indexOf('\t', indexofEigthTab+1) :-1;
					
					if (indexofFirstTab>0 && 
							indexofSecondTab>0 && 
							indexofThirdTab>0 && 
							indexofFourthTab>0 && 
							indexofFifthTab>0 &&
							indexofSixthTab>0 &&
							indexofSeventhTab>0 &&
							indexofEigthTab>0 && 
							indexofNinethTab>0){
						
						species = strLine.substring(indexofFirstTab+1, indexofSecondTab);
						
						if (species.equals("Homo sapiens")){
							sourceAssemblyName = strLine.substring(indexofSecondTab+1, indexofThirdTab);
							sourceRefSeqAssemblyId = strLine.substring(indexofFourthTab+1, indexofFifthTab);
							
							if (!assemblyName2RefSeqAssemblyIDMap.containsKey(sourceAssemblyName)){
								assemblyName2RefSeqAssemblyIDMap.put(sourceAssemblyName,sourceRefSeqAssemblyId);
							}
							
							
							targetAssemblyName = strLine.substring(indexofSixthTab+1, indexofSeventhTab);
							targetRefSeqAssemblyId = strLine.substring(indexofEigthTab+1, indexofNinethTab);
							
							if (!assemblyName2RefSeqAssemblyIDMap.containsKey(targetAssemblyName)){
								assemblyName2RefSeqAssemblyIDMap.put(targetAssemblyName,targetRefSeqAssemblyId);
							}
						}//End of if species is HOMO SAPIENS
						
					}//End of if necessary indexofTabs are nonzero
					
					
					

				}//End of if not a comment line
				
			}//End of while
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	
	public static void remap_show_batches(String dataFolder, String supportedAssembliesFileName){
		
		String remapFile = dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator")  + "remap_api.pl";
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		BufferedReader bufferedReader  = null;
		String line;
		
			try {
				
				fileWriter = FileOperations.createFileWriter(dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator"), supportedAssembliesFileName);
				bufferedWriter = new BufferedWriter(fileWriter);
				
				process = runtime.exec("perl "  + "\"" +  remapFile  + "\"");
//				process = runtime.exec("perl "  + "\"" +  remapFile  + "\"" + " --mode batches");
				process.waitFor();
				
				//output of the perl execution is here
				bufferedReader = new BufferedReader( new InputStreamReader( process.getInputStream()));
				
				while ( ( line = bufferedReader.readLine()) != null){
					System.out.println(line);
					bufferedWriter.write(line + System.getProperty("line.separator"));
				}//End of while
				
				bufferedWriter.flush();
				
				//Close 
				bufferedReader.close();
				bufferedWriter.close();
				
				System.err.println("\nExit status = " + process.exitValue());
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		
	}
	
	
	public static void remap(
			String dataFolder,
			String sourceAssembly, 
			String targetAssembly,
			String sourceFileName,
			String outputFileName,
			String merge,
			String allowMultipleLocation,
			double minimumRatioOfBasesThatMustBeRemapped,
			double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength){
					
		String remapFile = dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator")  + "remap_api.pl";
		
		//DirectoryName can contain empty spaces
	
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
			
			try {
				process = runtime.exec("perl "  + "\"" + remapFile + "\"" + " --mode asm-asm --from " + sourceAssembly  + " --dest " +  targetAssembly +  " --annotation " + "\"" + sourceFileName + "\"" +  " --annot_out "+  "\"" + outputFileName + "\""  + " --merge " + merge + " --allowdupes " + allowMultipleLocation +  " --mincov " + minimumRatioOfBasesThatMustBeRemapped + " --maxexp " + maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
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
				
	
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String glanetFolder = args[1];
		String dataFolder = glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator");
		
		
//		remap(dataFolder,"GCF_000001405.25","GCF_000001405.26","C:\\Users\\Burçak\\Google Drive\\Output\\NoName\\GivenInputData\\chrName_0Based_StartInclusive_EndExclusive_hg38_coordinates.bed","C:\\Users\\Burçak\\Google Drive\\Output\\NoName\\GivenInputData\\chrName_0Based_StartInclusive_EndExclusive_hg19_coordinates.bed");
//		remap(dataFolder,"GCF_000001405.26","GCF_000001405.25","C:\\Users\\Burçak\\Developer\\Java\\GLANET\\Glanet\\src\\remap\\chrName_0Based_StartInclusive_EndExclusive_hg38_coordinates.bed","C:\\Users\\Burçak\\Developer\\Java\\GLANET\\Glanet\\src\\remap\\chrName_0Based_StartInclusive_EndExclusive_hg19_coordinates.bed", Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON, Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON, Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_, Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2);
		remap_show_batches(dataFolder,Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);
		
		
//		GCF_000001405.25 <----> GRCh37.p13
//		GCF_000001405.26 <------> GRCh38
//		GCF_000001405.27 <------> GRCh38.p1
		
	}

}
