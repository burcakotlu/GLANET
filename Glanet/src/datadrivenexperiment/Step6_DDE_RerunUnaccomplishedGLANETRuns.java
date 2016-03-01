/**
 * This class is a combination of Step4 and Step5.
 * After a DDE is completed
 * some GLANET runs are not accomplished 
 * with no Enrichment directory or no Enrichment File at all.
 * This class will detect such GLANET Runs under Output directory and write sbatch calls script files under DDE directory
 * In fact detection is done in Step5 class
 * Now writing script files for sbacth calls for these reruns remained.
 */
package datadrivenexperiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.glassfish.external.arc.Stability;

import auxiliary.FileOperations;
import common.Commons;
import enumtypes.AssociationMeasureType;
import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentDnaseOverlapExclusionType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTPMType;
import enumtypes.GenerateRandomDataMode;

/**
 * @author Burçak Otlu
 * @date Mar 1, 2016
 * @project Glanet 
 *
 */
public class Step6_DDE_RerunUnaccomplishedGLANETRuns {

	public static void readUnaccomplisgedGLANETRunsFileAndWriteScriptFiles(String ddeFolder){
		
		// Read unaccomplishedGLANETRunFile
		String unaccomplishedGLANETRunFile = ddeFolder + System.getProperty("file.separator") + Commons.GLANET_DDE_UNACCOMPLISHED_GLANET_RUNS_FILE; ;
		
		FileReader fileReader =  null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		int indexofFirstUnderscore = -1;
		int indexofSecondUnderscore = -1;
		int indexofThirdUnderscore = -1;
		
		
		//cellLineType
		//DataDrivenExperimentCellLineType cellLineType = null;
		String cellLineType = null;
				
		//geneType
		//DataDrivenExperimentGeneType geneType = null;
		String geneType = null;
		
		//TPMType
		//DataDrivenExperimentTPMType tpmType = null;
		String tpmType = null;
		
		String remaining = null;
		
		//DnaseOverlapExclusionType
		DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType = null;
		
		//generateRandomDataMode
		GenerateRandomDataMode generateRandomDataMode = null;
		
		//associationMeasureType
		AssociationMeasureType associationMeasureType = null;
		
		String runNumber = null;
		
		List<String> GM12878_wGCM_EOO = new ArrayList<String>();
		List<String> GM12878_woGCM_EOO = new ArrayList<String>();
		List<String> GM12878_wGCM_NOOB = new ArrayList<String>();
		List<String> GM12878_woGCM_NOOB = new ArrayList<String>();
		
		List<String> K562_wGCM_EOO = new ArrayList<String>();
		List<String> K562_woGCM_EOO = new ArrayList<String>();
		List<String> K562_wGCM_NOOB = new ArrayList<String>();
		List<String> K562_woGCM_NOOB = new ArrayList<String>();
		
		try {
			fileReader = FileOperations.createFileReader(unaccomplishedGLANETRunFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//initialize
				cellLineType = null;
				geneType = null;
				tpmType = null;
				remaining = null;
				dnaseOverlapExclusionType = null;
				generateRandomDataMode = null;
				associationMeasureType = null;
				runNumber = null;
				
				//get their values
				//example strLine
				//GM12878_ExpressingGenes_Top20_NoDiscardwGCMEOORun9
				indexofFirstUnderscore = strLine.indexOf('_');
				indexofSecondUnderscore = (indexofFirstUnderscore>0)? strLine.indexOf('_',indexofFirstUnderscore+1):-1;
				indexofThirdUnderscore = (indexofSecondUnderscore>0)? strLine.indexOf('_',indexofSecondUnderscore+1):-1;
				
				cellLineType = strLine.substring(0, indexofFirstUnderscore);
				geneType = strLine.substring(indexofFirstUnderscore+1, indexofSecondUnderscore);
				tpmType =  strLine.substring(indexofSecondUnderscore+1, indexofThirdUnderscore);
				
				remaining= strLine.substring(indexofThirdUnderscore+1);
				//left here
				
			}//End of WHILE
			
			
			//Close
			bufferedReader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String glanetFolder = args[0];
		String ddeFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DDE + System.getProperty("file.separator");
		
		
		// Read unaccomplishedGLANETRunFile
		readUnaccomplisgedGLANETRunsFileAndWriteScriptFiles(ddeFolder);
		

	}

}
