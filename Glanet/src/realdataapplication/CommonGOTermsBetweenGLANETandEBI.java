/**
 * 
 */
package realdataapplication;

import enumtypes.CommandLineArguments;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Burçak Otlu
 * @date Feb 10, 2016
 * @project Glanet 
 *
 */
public class CommonGOTermsBetweenGLANETandEBI {

	/**
	 * 
	 */
	public CommonGOTermsBetweenGLANETandEBI() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static String getAdditionalZeros(int goNumber){
		
		String additionalZeros = "";
		
		
		//0000000
		for(int i=6; i>=0; i--){
			if(goNumber/((int)Math.pow(10, i))==0){
				additionalZeros = additionalZeros + "0";
			}
		}
		
		return additionalZeros;
		
		
	}
	
	
	public static void findCommonGOTerms(
			TIntList goNumber_EBI_List,
			TIntList goNumber_GLANET_List,
			TIntObjectMap<String> goNumber2TermMap,
			String information){
		
		int goNumber;
		
		String addZeros = null;
		
		int numberofCommonGONumbers = 0;
		
		System.out.println("\n*************" + information + " starts************************************************************"  );
		System.out.println("GOID\tGOTerm\tzScore");
		for(TIntIterator itr = goNumber_GLANET_List.iterator();itr.hasNext();){
			
			goNumber = itr.next();
			
			if (goNumber_EBI_List.contains(goNumber)){
				
				addZeros = getAdditionalZeros(goNumber);
				System.out.println("GO:" + addZeros + goNumber + "\t" + goNumber2TermMap.get(goNumber));
				numberofCommonGONumbers++;
				
			}//End of IF
			
		}//End of FOR
		System.out.println("Number of Gata2 associated GO Terms from EBI: " + goNumber_EBI_List.size());
		System.out.println("Number of enriched GO Terms from GLANET: " + goNumber_GLANET_List.size());
		System.out.println("Number of common GO Terms is: " + numberofCommonGONumbers);
		System.out.println(numberofCommonGONumbers + " out of " + goNumber_EBI_List.size() + " Gata2 Associated GO Terms from EBI and " + numberofCommonGONumbers + " out of " + goNumber_GLANET_List.size() + "  (" + ((numberofCommonGONumbers/((1.0f)*goNumber_GLANET_List.size()))*100)+  " %) GO terms found enriched by GLANET are common." );
		System.out.println("*************" + information + " ends************************************************************\n");
		
	}
	
	
	public static void readGLANETFile(
			String enrichedGOTermsGLANET_SydhGata2K562FileName,
			TIntList enrichedGOTermsGLANET_SydhGata2K562_List,
			TIntObjectMap<String> goNumber2TermMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String  strLine = null;
		String goNumberWithUnderscore = null;
		
		int goNumber;
		int indexofFirstTab = -1;
		int indexofSecondTab = -1;
		int indexofUnderscore = -1;
		
		String goTerm = null;
		String zScore = null;
		
		try {
			
			fileReader = FileOperations.createFileReader(enrichedGOTermsGLANET_SydhGata2K562FileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//example lines
			//GO ID	zScore	GO Term
			//GO_0002295	25.25324782	T-helper cell lineage commitment
			//GO_0045403	25.25324782	negative regulation of interleukin-4 biosynthetic process

			//skip header line
			strLine = bufferedReader.readLine();
			
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');				
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);	
				
				goNumberWithUnderscore = strLine.substring(0,indexofFirstTab);
				
				indexofUnderscore = goNumberWithUnderscore.indexOf('_');
				
				goNumber = Integer.parseInt(goNumberWithUnderscore.substring(indexofUnderscore+1,indexofFirstTab));
				
				zScore = strLine.substring(indexofFirstTab+1, indexofSecondTab);
				
				goTerm = strLine.substring(indexofSecondTab+1);
				
				if (!enrichedGOTermsGLANET_SydhGata2K562_List.contains(goNumber)){
					enrichedGOTermsGLANET_SydhGata2K562_List.add(goNumber);
					
				}//End of IF
				
				if (!goNumber2TermMap.containsKey(goNumber)){
					goNumber2TermMap.put(goNumber, goTerm + "\t" + zScore);
				}//End of IF
				
			}//End of WHILE
			
			//Close bufferedReader
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void readEBIFile(String gata2AssociatedGOTermsEBIFileName,TIntList gata2AssociatedGOTermsEBI_List){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String  goID = null;
		int goNumber;
		int indexofColon = -1;
		
		try {
			
			fileReader = FileOperations.createFileReader(gata2AssociatedGOTermsEBIFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//example lines
			//GO Identifier
			//GO:0000122
			
			//skip header line
			goID = bufferedReader.readLine();
			
			while((goID = bufferedReader.readLine())!=null){
				
				indexofColon = goID.indexOf(':');				
				goNumber = Integer.parseInt(goID.substring(indexofColon+1));
				
				if (!gata2AssociatedGOTermsEBI_List.contains(goNumber)){
					gata2AssociatedGOTermsEBI_List.add(goNumber);
				}//End of IF
				
			}//End of WHILE
			
			//Close bufferedReader
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	public static void main(String[] args) {
		
		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
		
		
		//EBI PART
		TIntList  gata2AssociatedGONumbersEBI_List = new TIntArrayList();
		//FROM EBI
		String gata2AssociatedGOTermsEBIFileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "EBI_GATA2_GO.txt" ;
		//Fill gata2AssociatedGONumbersEBI_List
		readEBIFile(gata2AssociatedGOTermsEBIFileName,gata2AssociatedGONumbersEBI_List);
				
			
		//GLANET PART
		//There will be three cases 
		//Case1: Sydh_Gata2_K562
		//Case2: Sydh_Gata2_Huvec
		//Case3: Haib_Gata2_K562
		TIntList  exonBased_enrichedGONumbersGLANET_List = null;
		TIntList  regulationBased_enrichedGONumbersGLANET_List = null;
		TIntList  extendedBased_enrichedGONumbersGLANET_List = null;

		TIntObjectMap<String> exonBased_goNumber2TermZScoreMap = null;
		TIntObjectMap<String> regulationBased_goNumber2TermZScoreMap = null;
		TIntObjectMap<String> extendedBased_goNumber2TermZScoreMap = null;
		
		
		/**********************************************************************************/	
		/********FOR GLANET Bonferroni Corrected PValues Results starts********************/	
		/**********************************************************************************/	
		//Case1: Sydh_Gata2_K562 starts
		exonBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		regulationBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		extendedBased_enrichedGONumbersGLANET_List = new TIntArrayList();

		exonBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		regulationBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		extendedBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
	
		//Please note that enriched GO IDs are sorted w.r.t. zScores in descending order.
		String exonBased_enrichedGOTermsGLANET_SydhGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "exonBased_sydhGata2K562_GO_Bonferroni.txt" ;
		String regulationBased_enrichedGOTermsGLANET_SydhGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "regulationBased_sydhGata2K562_GO_Bonferroni.txt" ;
		String extendedBased_enrichedGOTermsGLANET_SydhGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "extendedBased_sydhGata2K562_GO_Bonferroni.txt" ;

		readGLANETFile(exonBased_enrichedGOTermsGLANET_SydhGata2K562FileName,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap);
		readGLANETFile(regulationBased_enrichedGOTermsGLANET_SydhGata2K562FileName,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap);
		readGLANETFile(extendedBased_enrichedGOTermsGLANET_SydhGata2K562FileName,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap);
	
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap, "ExonBased --- SydhGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BonferroniCorrected PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap,"RegulationBased --- SydhGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BonferroniCorrected PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap,"ExtendedBased --- SydhGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BonferroniCorrected PValues) and EMBL-EBI");
		//Case1: Sydh_Gata2_K562 ends
		
		
		//Case2: Sydh_Gata2_Huvec starts
		exonBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		regulationBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		extendedBased_enrichedGONumbersGLANET_List = new TIntArrayList();

		exonBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		regulationBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		extendedBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
	
		String exonBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "exonBased_sydhGata2Huvec_GO_Bonferroni.txt" ;
		String regulationBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "regulationBased_sydhGata2Huvec_GO_Bonferroni.txt" ;
		String extendedBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "extendedBased_sydhGata2Huvec_GO_Bonferroni.txt" ;

		readGLANETFile(exonBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap);
		readGLANETFile(regulationBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap);
		readGLANETFile(extendedBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap);
	
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap, "ExonBased --- SydhGata2(Huvec) --- CommonGOTerms  --- Between GLANET (w.r.t. BonferroniCorrected PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap,"RegulationBased --- SydhGata2(Huvec) --- CommonGOTerms  --- Between GLANET (w.r.t. BonferroniCorrected PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap,"ExtendedBased --- SydhGata2(Huvec) --- CommonGOTerms  --- Between GLANET (w.r.t. BonferroniCorrected PValues) and EMBL-EBI");
		//Case2: Sydh_Gata2_Huvec ends
		
		
		//Case3: Haib_Gata2_K562 starts
		exonBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		regulationBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		extendedBased_enrichedGONumbersGLANET_List = new TIntArrayList();

		exonBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		regulationBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		extendedBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
	
		String exonBased_enrichedGOTermsGLANET_HaibGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "exonBased_haibGata2K562_GO_Bonferroni.txt" ;
		String regulationBased_enrichedGOTermsGLANET_HaibGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "regulationBased_haibGata2K562_GO_Bonferroni.txt" ;
		String extendedBased_enrichedGOTermsGLANET_HaibGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "extendedBased_haibGata2K562_GO_Bonferroni.txt" ;

		readGLANETFile(exonBased_enrichedGOTermsGLANET_HaibGata2K562FileName,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap);
		readGLANETFile(regulationBased_enrichedGOTermsGLANET_HaibGata2K562FileName,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap);
		readGLANETFile(extendedBased_enrichedGOTermsGLANET_HaibGata2K562FileName,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap);
	
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap, "ExonBased --- HaibGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BonferroniCorrected PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap,"RegulationBased --- HaibGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BonferroniCorrected PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap,"ExtendedBased --- HaibGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BonferroniCorrected PValues) and EMBL-EBI");
		//Case3: Haib_Gata2_K562 ends
		/**********************************************************************************/	
		/********FOR GLANET Bonferroni Corrected PValues Results ends**********************/	
		/**********************************************************************************/	

		
		
		/**********************************************************************************/	
		/********FOR GLANET BH FDR Adjusted PValues Results starts*************************/	
		/**********************************************************************************/	
		//Case1: Sydh_Gata2_K562 starts
		exonBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		regulationBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		extendedBased_enrichedGONumbersGLANET_List = new TIntArrayList();

		exonBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		regulationBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		extendedBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
	
		//Please note that enriched GO IDs are sorted w.r.t. zScores in descending order.
		exonBased_enrichedGOTermsGLANET_SydhGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "exonBased_sydhGata2K562_GO_BH.txt" ;
		regulationBased_enrichedGOTermsGLANET_SydhGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "regulationBased_sydhGata2K562_GO_BH.txt" ;
		extendedBased_enrichedGOTermsGLANET_SydhGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "extendedBased_sydhGata2K562_GO_BH.txt" ;

		readGLANETFile(exonBased_enrichedGOTermsGLANET_SydhGata2K562FileName,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap);
		readGLANETFile(regulationBased_enrichedGOTermsGLANET_SydhGata2K562FileName,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap);
		readGLANETFile(extendedBased_enrichedGOTermsGLANET_SydhGata2K562FileName,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap);
	
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap, "ExonBased --- SydhGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BH FDR Adjusted PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap,"RegulationBased --- SydhGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BH FDR Adjusted PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap,"ExtendedBased --- SydhGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BH FDR Adjusted PValues) and EMBL-EBI");
		//Case1: Sydh_Gata2_K562 ends
		
		//Case2: Sydh_Gata2_Huvec starts
		exonBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		regulationBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		extendedBased_enrichedGONumbersGLANET_List = new TIntArrayList();

		exonBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		regulationBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		extendedBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
	
		exonBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "exonBased_sydhGata2Huvec_GO_BH.txt" ;
		regulationBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "regulationBased_sydhGata2Huvec_GO_BH.txt" ;
		extendedBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "extendedBased_sydhGata2Huvec_GO_BH.txt" ;

		readGLANETFile(exonBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap);
		readGLANETFile(regulationBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap);
		readGLANETFile(extendedBased_enrichedGOTermsGLANET_SydhGata2HuvecFileName,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap);
	
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap, "ExonBased --- SydhGata2(Huvec) --- CommonGOTerms  --- Between GLANET (w.r.t. BH FDR Adjusted PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap,"RegulationBased --- SydhGata2(Huvec) --- CommonGOTerms  --- Between GLANET (w.r.t. BH FDR Adjusted PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap,"ExtendedBased --- SydhGata2(Huvec) --- CommonGOTerms  --- Between GLANET (w.r.t. BH FDR Adjusted PValues) and EMBL-EBI");
		//Case2: Sydh_Gata2_Huvec ends
		
		
		//Case3: Haib_Gata2_K562 starts
		exonBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		regulationBased_enrichedGONumbersGLANET_List = new TIntArrayList();
		extendedBased_enrichedGONumbersGLANET_List = new TIntArrayList();

		exonBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		regulationBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
		extendedBased_goNumber2TermZScoreMap = new TIntObjectHashMap<String>();
	
		exonBased_enrichedGOTermsGLANET_HaibGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "exonBased_haibGata2K562_GO_BH.txt" ;
		regulationBased_enrichedGOTermsGLANET_HaibGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "regulationBased_haibGata2K562_GO_BH.txt" ;
		extendedBased_enrichedGOTermsGLANET_HaibGata2K562FileName = dataFolder + System.getProperty( "file.separator") + "demo_input_data" + System.getProperty( "file.separator") + "GREAT_Inspired_RealDataApplication" + System.getProperty( "file.separator") + "extendedBased_haibGata2K562_GO_BH.txt" ;

		readGLANETFile(exonBased_enrichedGOTermsGLANET_HaibGata2K562FileName,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap);
		readGLANETFile(regulationBased_enrichedGOTermsGLANET_HaibGata2K562FileName,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap);
		readGLANETFile(extendedBased_enrichedGOTermsGLANET_HaibGata2K562FileName,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap);
	
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,exonBased_enrichedGONumbersGLANET_List,exonBased_goNumber2TermZScoreMap, "ExonBased --- HaibGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BH FDR Adjusted PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,regulationBased_enrichedGONumbersGLANET_List,regulationBased_goNumber2TermZScoreMap,"RegulationBased --- HaibGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BH FDR Adjusted PValues) and EMBL-EBI");
		findCommonGOTerms(gata2AssociatedGONumbersEBI_List,extendedBased_enrichedGONumbersGLANET_List,extendedBased_goNumber2TermZScoreMap,"ExtendedBased --- HaibGata2(K562) --- CommonGOTerms  --- Between GLANET (w.r.t. BH FDR Adjusted PValues) and EMBL-EBI");
		//Case3: Haib_Gata2_K562 ends
		/**********************************************************************************/	
		/********FOR GLANET BH FDR Adjusted PValues Results ends***************************/	
		/**********************************************************************************/	


		

	}

}
