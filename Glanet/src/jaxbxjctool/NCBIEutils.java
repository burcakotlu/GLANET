/**
 * 
 */
package jaxbxjctool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;

import remap.Remap;

import common.Commons;

import enumtypes.CommandLineArguments;

/**
 * @author Burçak Otlu
 * @date Sep 29, 2015
 * @project Glanet 
 *
 */
public class NCBIEutils {


	public static String getLatestAssemblyNameReturnedByNCBIEutils(){
		
		//dummy initializations
		String chrNamewithoutPreceedingChr = "1";
		int dummyIncrement = 500;
		int givenIntervalStartOneBased = 20000000;
		int givenIntervalEndOneBased = givenIntervalStartOneBased + dummyIncrement;
		List<Integer> rsIdList = null;
		
		RsInformation rsInformation = null;
		String latestAssembyNameReturnedByNCBIEutils = null;
		//int count = 0;
		
		try {
			AugmentationofGivenIntervalwithRsIds augmentationOfAGivenIntervalWithRsIDs = new AugmentationofGivenIntervalwithRsIds();
			AugmentationofGivenRsIdwithInformation augmentationOfAGivenRsIdWithInformation = new AugmentationofGivenRsIdwithInformation();

			do{
				do{
					
					rsIdList = augmentationOfAGivenIntervalWithRsIDs.getRsIdsInAGivenInterval(
					chrNamewithoutPreceedingChr, 
					givenIntervalStartOneBased, 
					givenIntervalEndOneBased);
					
					//dummy increment value
					givenIntervalStartOneBased = givenIntervalStartOneBased + dummyIncrement;
					givenIntervalEndOneBased = givenIntervalEndOneBased + dummyIncrement;
					
				}while (rsIdList.isEmpty());
				
				//If rsIdList is not empty
				//Then get the LatestAssemblyName returned by NCBI Eutils for the first rsId in the rsIdList
				rsInformation = augmentationOfAGivenRsIdWithInformation.getInformationforGivenRsId(rsIdList.get(0).toString());
				latestAssembyNameReturnedByNCBIEutils =  rsInformation.getGroupLabel();
				
				//System.out.println(count++);
				
			}while (latestAssembyNameReturnedByNCBIEutils == null);
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return latestAssembyNameReturnedByNCBIEutils;
	}
	
	public static String getRefSeqAssemblyID(
			String assemblyName,
			Map<String, String> assemblyName2RefSeqAssemblyIDMap){
		
		return assemblyName2RefSeqAssemblyIDMap.get(assemblyName);
		
	}
	
	// Download from ftp://ftp.ncbi.nlm.nih.gov/genomes/ASSEMBLY_REPORTS/All/GCF_000001405.30.assembly.txt
	// ftp://ftp.ncbi.nlm.nih.gov/genomes/ASSEMBLY_REPORTS/All/GCF_000001405.28.assembly.txt
	// as ftp://ftp.ncbi.nlm.nih.gov/genomes/ASSEMBLY_REPORTS/All/RefSeqAssemblyID.assembly.txt
	// Write to assemblyReportFileName
	public static void getAssemblyReport(
			String refSeqAssemblyID,
			String dataFolder,
			String assemblyReportFileName){
		
		FTPClient ftpClient = new FTPClient();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		int read = 0;
		byte[] bytes = new byte[1024];

		try {
			
			ftpClient.connect("ftp.ncbi.nlm.nih.gov");
			
			ftpClient.login("anonymous", "anonymous");
			
			ftpClient.enterLocalPassiveMode();
			
			ftpClient.changeWorkingDirectory("/genomes/ASSEMBLY_REPORTS/All/");
			
			inputStream = ftpClient.retrieveFileStream(refSeqAssemblyID + Commons.ASSEMBLY_REPORTS_FILE_EXTENSION);
			
			// write the inputStream to a FileOutputStream
			outputStream = new FileOutputStream(new File(dataFolder + assemblyReportFileName));

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}//End of WHILE

			//Close InputStream
			if (inputStream!=null){
				inputStream.close();
			}
			//Close OutputStream
			if (outputStream!=null){
				outputStream.close();
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	// # Sequence-Name Sequence-Role Assigned-Molecule
	// Assigned-Molecule-Location/Type GenBank-Accn Relationship RefSeq-Accn
	// Assembly-Unit
	// 1 assembled-molecule 1 Chromosome CM000663.1 = NC_000001.10 Primary Assembly
	// X assembled-molecule X Chromosome CM000685.1 = NC_000023.10 Primary Assembly
	public static void fillChrName2RefSeqIDMap( 
			String dataFolder, 
			String refSeqIdsforGRChXXInputFile,
			Map<String, String> chrName2RefSeqIdforGrchXXMap) {

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		String strLine = null;
		int numberofChromosomesinHomoSapiens = 24;
		int count = 0;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;

		String chrName;
		String refSeqId;

		try{
			fileReader = new FileReader( dataFolder + refSeqIdsforGRChXXInputFile);
			bufferedReader = new BufferedReader( fileReader);

			while( ( strLine = bufferedReader.readLine()) != null){
				if( strLine.startsWith( "#")){
					continue;
				}else{
					if( count < numberofChromosomesinHomoSapiens){
						count++;

						indexofFirstTab 	= strLine.indexOf('\t');
						indexofSecondTab 	= (indexofFirstTab>0) ? strLine.indexOf('\t', indexofFirstTab + 1): -1;
						indexofThirdTab 	= (indexofSecondTab>0) ? strLine.indexOf('\t', indexofSecondTab + 1): -1;
						indexofFourthTab 	= (indexofThirdTab>0) ? strLine.indexOf('\t', indexofThirdTab + 1) : -1;
						indexofFifthTab 	= (indexofFourthTab>0) ? strLine.indexOf('\t', indexofFourthTab + 1) : -1;
						indexofSixthTab 	= (indexofFifthTab>0) ? strLine.indexOf('\t', indexofFifthTab + 1): -1;
						indexofSeventhTab 	= (indexofSixthTab>0) ? strLine.indexOf('\t', indexofSixthTab + 1) : -1;

						chrName = strLine.substring( 0, indexofFirstTab);
						refSeqId = strLine.substring( indexofSixthTab + 1, indexofSeventhTab);

						chrName2RefSeqIdforGrchXXMap.put( chrName, refSeqId);
						continue;

					}
				}

				break;
			}
		}catch( IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
		
		/***************************************************************************************/
		/***************************************************************************************/
		/***************************************************************************************/
		String latestAssembyNameReturnedByNCBIEutils = getLatestAssemblyNameReturnedByNCBIEutils();
		System.out.println("latestAssembyNameReturnedByNCBIEutils" + "\t" + latestAssembyNameReturnedByNCBIEutils);
		/***************************************************************************************/
		/***************************************************************************************/
		/***************************************************************************************/

		
		/***************************************************************************************/
		/***************************************************************************************/
		/***************************************************************************************/
		Map<String, String> assemblyName2RefSeqAssemblyIDMap = new HashMap<String, String>();
		
		Remap.remap_show_batches(dataFolder, Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);
		
		Remap.fillAssemblyName2RefSeqAssemblyIDMap(
				dataFolder, 
				Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE,
				assemblyName2RefSeqAssemblyIDMap);
		/***************************************************************************************/
		/***************************************************************************************/
		/***************************************************************************************/

		/***************************************************************************************/
		/***************************************************************************************/
		/***************************************************************************************/
		String refSeqAssemblyID = NCBIEutils.getRefSeqAssemblyID(latestAssembyNameReturnedByNCBIEutils, assemblyName2RefSeqAssemblyIDMap);
		System.out.println("refSeqAssemblyID" + "\t" +refSeqAssemblyID);
		/***************************************************************************************/
		/***************************************************************************************/
		/***************************************************************************************/

		/***************************************************************************************/
		/***************************************************************************************/
		/***************************************************************************************/
		String assemblyReportFileName = "AssemblyReports" + System.getProperty("file.separator") + refSeqAssemblyID + ".assembly.txt" ;
		getAssemblyReport(refSeqAssemblyID, dataFolder, assemblyReportFileName);
		/***************************************************************************************/
		/***************************************************************************************/
		/***************************************************************************************/
		
		/***************************************************************************************/
		/***************************************************************************************/
		/***************************************************************************************/
		Map<String, String> chrName2RefSeqIdforGrchXXMap = new HashMap<String, String>();
		fillChrName2RefSeqIDMap(dataFolder, assemblyReportFileName, chrName2RefSeqIdforGrchXXMap);
		/***************************************************************************************/
		/***************************************************************************************/
		/***************************************************************************************/
		
		System.out.println("Dummy");
	}

}
