/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import common.Commons;

import auxiliary.FileOperations;

/**
 * @author Burçak Otlu
 * @date May 7, 2015
 * @project Glanet 
 *
 */
public class SimulationGLANETRuns {
	
	
	public static void writeTPMIntervals(BufferedWriter bufferedWriter, int numberofSimulations,String tpm, String dnaseOverlapsExcludedorNot) throws IOException{
		
		//Desktop in METU
		//C:\Users\aidata\Google Drive\GLANET
		
		for(int i = 0; i<numberofSimulations; i++){
			
			bufferedWriter.write("java -jar" + " "+
								"\"C:" + System.getProperty("file.separator") + System.getProperty("file.separator") +
								"Users" + System.getProperty("file.separator") + System.getProperty("file.separator") +
								//"Burçak" +  System.getProperty("file.separator") + System.getProperty("file.separator") +
								"aidata" +  System.getProperty("file.separator") + System.getProperty("file.separator") +
								"Google Drive" + System.getProperty("file.separator") + System.getProperty("file.separator") +
								"GLANET" + System.getProperty("file.separator") +  System.getProperty("file.separator") +
								"GLANET.jar\"" + " " + 
								"-Xms4G" + " " +
								"-Xmx4G" +  " " +
								"-c" + " " +  
								"-g" + " " +  
								"\"C:" + System.getProperty("file.separator") +System.getProperty("file.separator") +
								"Users" + System.getProperty("file.separator") +System.getProperty("file.separator") +
								//"Burçak" + System.getProperty("file.separator") +System.getProperty("file.separator") +
								"aidata" + System.getProperty("file.separator") +System.getProperty("file.separator") +
								"Google Drive" + System.getProperty("file.separator") + System.getProperty("file.separator") + "\"" + " " +
								"-i" + " " +
								"\"C:" + System.getProperty("file.separator") +System.getProperty("file.separator") +
								"Users" + System.getProperty("file.separator") +System.getProperty("file.separator") +
								//"Burçak" + System.getProperty("file.separator") +System.getProperty("file.separator") +
								"aidata" + System.getProperty("file.separator") +System.getProperty("file.separator") +
								"Google Drive" + System.getProperty("file.separator") +System.getProperty("file.separator") +
								"Data"+ System.getProperty("file.separator") +System.getProperty("file.separator") +
								"SimulationData" + System.getProperty("file.separator") +System.getProperty("file.separator") +
								tpm + dnaseOverlapsExcludedorNot + "Sim" + i + ".txt\""  + " " +
								"-f0" +  " " +
								"-dnase" + " " +  
								"-tf"  + " " +  
								"-histone" +  " " +  
								"-e" +  " " +  
								"-pe" + " " +  
								"10000" +  " " +  
								"-j" + " " +   
								tpm + dnaseOverlapsExcludedorNot +"Sim" + i + 
								System.getProperty("line.separator") );
			
		}//End of for
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int numberofSimulations = 100;
	
		FileWriter fileWriter= null ;
		BufferedWriter bufferedWriter = null;
		
		try {
			fileWriter = FileOperations.createFileWriter("C:" + System.getProperty("file.separator") + 
																"Users" + System.getProperty("file.separator") +
																//"Burçak" + System.getProperty("file.separator") +
																"aidata" + System.getProperty("file.separator") +
																"Desktop" + System.getProperty("file.separator") +
																"SimulationGLANETRuns.bat");
			
			bufferedWriter = new BufferedWriter(fileWriter);
			
			
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0,Commons.NON_EXPRESSING_GENES);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_1,Commons.NON_EXPRESSING_GENES);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_01,Commons.NON_EXPRESSING_GENES);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_001,Commons.NON_EXPRESSING_GENES);
			
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0,Commons.COMPLETELY_DNASE_OVERLAPS_EXCLUSION);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_1,Commons.COMPLETELY_DNASE_OVERLAPS_EXCLUSION);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_01,Commons.COMPLETELY_DNASE_OVERLAPS_EXCLUSION);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_001,Commons.COMPLETELY_DNASE_OVERLAPS_EXCLUSION);
			
			
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0,Commons.PARTIALLY_DNASE_OVERLAPS_EXCLUSION);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_1,Commons.PARTIALLY_DNASE_OVERLAPS_EXCLUSION);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_01,Commons.PARTIALLY_DNASE_OVERLAPS_EXCLUSION);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_001,Commons.PARTIALLY_DNASE_OVERLAPS_EXCLUSION);
			
			//Close BufferedWriter
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

}
