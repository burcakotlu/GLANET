/**
 * 
 */
package gosemsim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import auxiliary.FileOperations;

/**
 * @author Burçak Otlu
 * @date Aug 11, 2016
 * @project Glanet 
 *
 *
 * Motivation: In this class my motivation is to form go terms from flat files.
 * And provide them as input to GOSemSim for mgoSim function
 * 
 *  e.g.:
 *  
 *  > go1=c("GO:0004022","GO:0004024","GO:0004174")
 *	> go2=c("GO:0009055","GO:0005515")
 *	> mgoSim(go1, go2, measure="Wang", ont = "MF")
 *	[1] 0.299
 *
 */
public class GOTermPreparation {

	/**
	 * 
	 */
	public GOTermPreparation() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static String convert(List<String> GOTermList){
		
		String GOTermforGOSemSim = null;
		String GOTerm = null;
		
		if (GOTermList.size()>0){
			
			
			//Initialize
			GOTermforGOSemSim = "GOTermList = c(";
			
			//For each GOTerm except the last one
			for(int i=1; i<(GOTermList.size()-1);i++){
				
				GOTerm = GOTermList.get(i);
				GOTermforGOSemSim = 	GOTermforGOSemSim + "\"" + GOTerm + "\","  ;
				
			}//End of for
			
			//For last element
			GOTermforGOSemSim = 	GOTermforGOSemSim + "\"" + GOTerm + "\")"  ;

			
		}//There is at least one GOTerm
		
		
		return GOTermforGOSemSim;
	}
	
	
	public static void getGOTermAndAccumulate(String strLine,List<String> GOTermList){
		
		String GOTerm = null;
		Boolean enriched = null;
		
		int indexofUnderscore;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;
		int indexofTwelfthTab;
		int indexofThirteenthTab;
		
		indexofFirstTab = strLine.indexOf( '\t');
		indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
		indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;
		indexofFourthTab = ( indexofThirdTab > 0)?strLine.indexOf( '\t', indexofThirdTab + 1):-1;
		indexofFifthTab = ( indexofFourthTab > 0)?strLine.indexOf( '\t', indexofFourthTab + 1):-1;
		indexofSixthTab = ( indexofFifthTab > 0)?strLine.indexOf( '\t', indexofFifthTab + 1):-1;
		indexofSeventhTab = ( indexofSixthTab > 0)?strLine.indexOf( '\t', indexofSixthTab + 1):-1;
		indexofEigthTab = ( indexofSeventhTab > 0)?strLine.indexOf( '\t', indexofSeventhTab + 1):-1;
		indexofNinethTab = ( indexofEigthTab > 0)?strLine.indexOf( '\t', indexofEigthTab + 1):-1;
		indexofTenthTab = ( indexofNinethTab > 0)?strLine.indexOf( '\t', indexofNinethTab + 1):-1;
		indexofEleventhTab = ( indexofTenthTab > 0)?strLine.indexOf( '\t', indexofTenthTab + 1):-1;
		indexofTwelfthTab = ( indexofEleventhTab > 0)?strLine.indexOf( '\t', indexofEleventhTab + 1):-1;
		indexofThirteenthTab = ( indexofTwelfthTab > 0)?strLine.indexOf( '\t', indexofTwelfthTab + 1):-1;
		
//		Look in between 12 and 13
//		1176	GO_0034134	860	117	10000	14697	291.136569	203.2774396	2.798458265	2.57E-03	1.00E+00	4.83E-02	TRUE	1.17E-02	1.00E+00	8.70E-02	FALSE	toll-like receptor 2 signaling pathway
//		1034	GO_0044297	615	88	10000	14697	204.262824	147.6662194	2.78152429	2.71E-03	1.00E+00	5.06E-02	FALSE	8.80E-03	1.00E+00	8.38E-02	FALSE	cell body


		//GO Term is between 1 and 2
		GOTerm = strLine.substring(indexofFirstTab+1, indexofSecondTab);
		enriched = Boolean.parseBoolean(strLine.substring(indexofTwelfthTab+1, indexofThirteenthTab));
		
		indexofUnderscore = GOTerm.indexOf("_");
		String newGOTerm = GOTerm.substring(0, indexofUnderscore)  + ":" + GOTerm.substring(indexofUnderscore+1);
		
		
		
		if (enriched){
			
			if (!GOTermList.contains(newGOTerm)){
				GOTermList.add(newGOTerm);
			}//End of IF list does not contain newGOTerm
		
		}//End of IF enriched
		
		
		
	}
	
	public static String readEnrichmentFileAndFormGOTermList(String fileName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		List<String> GOTermList = new ArrayList<String>();
		String GOTermforGOSemSim = null;
		
		
		try {
			fileReader = FileOperations.createFileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//Skip header line
			strLine= bufferedReader.readLine();
			
			while((strLine= bufferedReader.readLine())!=null){
				
				getGOTermAndAccumulate(strLine,GOTermList);
				
				
			}//End of while
			
			GOTermforGOSemSim = convert(GOTermList);
			
			//Close
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return GOTermforGOSemSim;
		
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//C:\Users\Burçak\Google Drive\Output\GLANET_SydhGATA2K562_GOTerms\Enrichment\UserDefinedGeneSet\GO\ExonBased\ExonBased_GO_GLANET_SydhGATA2K562_GOTerms_wrt_zScore.txt
		//RegulationBased_GO_GLANET_SydhGATA2K562_GOTerms_wrt_zScore.txt
		//AllBased_GO_GLANET_SydhGATA2K562_GOTerms_wrt_zScore.txt
		
		//String fileName = args[0];
		
		
		String exonBasedFileName = "C:\\Users\\Burçak\\Google Drive\\Output\\GLANET_SydhGATA2K562_GOTerms\\Enrichment\\UserDefinedGeneSet\\GO\\ExonBased\\ExonBased_GO_GLANET_SydhGATA2K562_GOTerms_wrt_zScore.txt";
		String regulationBasedFileName = "C:\\Users\\Burçak\\Google Drive\\Output\\GLANET_SydhGATA2K562_GOTerms\\Enrichment\\UserDefinedGeneSet\\GO\\RegulationBased\\RegulationBased_GO_GLANET_SydhGATA2K562_GOTerms_wrt_zScore.txt";
		String allBasedFileName = "C:\\Users\\Burçak\\Google Drive\\Output\\GLANET_SydhGATA2K562_GOTerms\\Enrichment\\UserDefinedGeneSet\\GO\\AllBased\\AllBased_GO_GLANET_SydhGATA2K562_GOTerms_wrt_zScore.txt";
				
		String GOTermforGOSemSim = null;
		
		GOTermforGOSemSim = readEnrichmentFileAndFormGOTermList(exonBasedFileName);
		System.out.println(GOTermforGOSemSim);
		
		GOTermforGOSemSim = readEnrichmentFileAndFormGOTermList(regulationBasedFileName);
		System.out.println(GOTermforGOSemSim);

		GOTermforGOSemSim = readEnrichmentFileAndFormGOTermList(allBasedFileName);
		System.out.println(GOTermforGOSemSim);

	}

}
