/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import common.Commons;
import enumtypes.DnaseOverlapExclusionType;
import enumtypes.GenerateRandomDataMode;
import auxiliary.FileOperations;

/**
 * @author Burçak Otlu
 * @date May 7, 2015
 * @project Glanet 
 * 
 * Data Driven Experiment Step 4
 *
 */
public class SimulationGLANETRuns {
	
	
	public static void writeTPMIntervals(
			BufferedWriter bufferedWriter, 
			int numberofSimulations,
			String tpm, 
			DnaseOverlapExclusionType dnaseOverlapExclusionType,
			GenerateRandomDataMode withorWithout) throws IOException{
		
		//Desktop PC in METU
		//C:\Users\aidata\Google Drive\GLANET
		
		//My Laptop
		//C:\Users\Burçak\Google Drive\GLANET
		
	
		for(int i = 0; i<numberofSimulations; i++){
			
			switch(withorWithout){
				
				case GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT: 
					
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
							tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "Sim" + i + ".txt\""  + " " +
							"-f0" +  " " +
							"-dnase" + " " +  
							"-tf"  + " " +  
							"-histone" +  " " +  
							"-e" +  " " +  
							"-rdgcm" + " " +
							"-pe" + " " +  
							"10000" +  " " +  
							"-j" + " " +   
							tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() +"Sim" + i + 
							System.getProperty("line.separator") );
					break;
					
				case GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT: 
					
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
							tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() + "Sim" + i + ".txt\""  + " " +
							"-f0" +  " " +
							"-dnase" + " " +  
							"-tf"  + " " +  
							"-histone" +  " " +  
							"-e" +  " " +  
							"-rd" + " " +
							"-pe" + " " +  
							"10000" +  " " +  
							"-j" + " " +   
							tpm + "_" + dnaseOverlapExclusionType.convertEnumtoString() +"Sim" + i + 
							System.getProperty("line.separator") );
					break;
					
				default: 
					break;
			
			
			}//End of SWITCH
			
			
			
		}//End of for
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int numberofSimulations = 100;
	
		FileWriter fileWriter= null ;
		BufferedWriter bufferedWriter = null;
		
		GenerateRandomDataMode withGCandMapability = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
		GenerateRandomDataMode withoutGCandMapability = GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
		
		try {
			fileWriter = FileOperations.createFileWriter("C:" + System.getProperty("file.separator") + 
																"Users" + System.getProperty("file.separator") +
																//"Burçak" + System.getProperty("file.separator") +
																"aidata" + System.getProperty("file.separator") +
																"Desktop" + System.getProperty("file.separator") +
																"SimulationGLANETRuns.bat");
			
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//With GC and Mapability
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_001,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withGCandMapability);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_01,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withGCandMapability);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_1,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withGCandMapability);
			
			//Without GC and Mapability
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_001,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withoutGCandMapability);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_01,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withoutGCandMapability);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_1,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withoutGCandMapability);
		
				
			//With GC and Mapability
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_001,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withGCandMapability);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_01,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withGCandMapability);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_1,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withGCandMapability);
			
			//Without GC and Mapability
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_001,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withoutGCandMapability);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_01,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withoutGCandMapability);
			writeTPMIntervals(bufferedWriter,numberofSimulations,Commons.TPM_0_1,DnaseOverlapExclusionType.PARTIALLY_DISCARD_INTERVAL_REMAIN_ONLY_THE_LONGEST_INTERVAL_IN_CASE_OF_DNASE_OVERLAP,withoutGCandMapability);
			
			
			//Close BufferedWriter
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

}
