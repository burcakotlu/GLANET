/**
 * @author Burcak Otlu
 * Sep 24, 2013
 * 1:18:27 PM
 * 2013
 *
 * 
 */
package augmentation.humangenes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;
import common.Commons;
import enumtypes.CommandLineArguments;
import gnu.trove.map.TIntObjectMap;

public class HumanGenesAugmentation {

	// read NCBI humanGene2RefSeq file
	// fill map --> geneSymbol2ListofGeneIDMap: this map is ? 2 ? (? pairs)
	public static void fillGeneSymbol2ListofGeneIDMap(String dataFolder, Map<String, List<Integer>> geneSymbol2ListofGeneIdMap) {

		FileReader fileReader;
		BufferedReader bufferedReader;
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
		int indexofTenthTab;
		int indexofEleventhTab;
		int indexofTwelfthTab;
		int indexofThirteenthTab;
		int indexofFourteenthTab;
		int indexofFifteenthTab;

		int geneID;
		String geneSymbol;

		List<Integer> geneIDList;

		try {
			fileReader = FileOperations.createFileReader(dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_OUTPUT_DIRECTORYNAME + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME_18_NOV_2014);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				// example line
				// the second column is gene id column
				// the fourth column is the refseq gene name column
				// 9606 1 REVIEWED NM_130786.3 161377438 NP_570602.2 21071030
				// AC_000151.1 157718668 55167315 55174019 - Alternate HuRef - -
				// A1BG

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf('\t', indexofFourthTab + 1);
				indexofSixthTab = strLine.indexOf('\t', indexofFifthTab + 1);
				indexofSeventhTab = strLine.indexOf('\t', indexofSixthTab + 1);
				indexofEigthTab = strLine.indexOf('\t', indexofSeventhTab + 1);
				indexofNinethTab = strLine.indexOf('\t', indexofEigthTab + 1);
				indexofTenthTab = strLine.indexOf('\t', indexofNinethTab + 1);
				indexofEleventhTab = strLine.indexOf('\t', indexofTenthTab + 1);
				indexofTwelfthTab = strLine.indexOf('\t', indexofEleventhTab + 1);
				indexofThirteenthTab = strLine.indexOf('\t', indexofTwelfthTab + 1);
				indexofFourteenthTab = strLine.indexOf('\t', indexofThirteenthTab + 1);
				indexofFifteenthTab = strLine.indexOf('\t', indexofFourteenthTab + 1);

				geneID = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));

				// geneSymbol the default symbol for the gene
				geneSymbol = strLine.substring(indexofFifteenthTab + 1);

				if ((geneSymbol != null) && !(geneSymbol.equals("-"))) {

					// FILL geneSymbol2ListoGeneIdMap starts
					geneIDList = geneSymbol2ListofGeneIdMap.get(geneSymbol);

					if (geneIDList == null) {
						geneIDList = new ArrayList<Integer>();
						geneIDList.add(geneID);
						geneSymbol2ListofGeneIdMap.put(geneSymbol, geneIDList);
					} else {
						if (!geneIDList.contains(geneID)) {
							geneIDList.add(geneID);
						}

						// //For debugging purposes starts
						// if(geneIDList.size()>1){
						// System.out.println("geneSymbol: " + geneSymbol +
						// " geneIDList size: " + geneIDList.size());
						// }
						// //For debugging purposes ends

						geneSymbol2ListofGeneIdMap.put(geneSymbol, geneIDList);

					}
					// FILL geneSymbol2ListoGeneIdMap ends

				}// End of IF: geneSymbol is not NULL

			}// End of while

			bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// @todo starts
	// read NCBI humanGene2RefSeq file
	// fill map --> RNANucleotideAccession2ListofGeneIDMap: this map is many 2
	// one (41065 pairs)
	public static void fillRNANucleotideAccession2ListofGeneIdMap(String dataFolder, Map<String, List<Integer>> RNANucleotideAccession2ListofGeneIdMap) {

		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;

		int indexofDot;

		int geneID;
		String RNANucleotideAccessionWithVersion;
		String RNANucleotideAccession;

		List<Integer> geneIDList;

		try {
			fileReader = FileOperations.createFileReader(dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_OUTPUT_DIRECTORYNAME + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME_18_NOV_2014);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				// example line
				// the second column is gene id column
				// the fourth column is the refseq gene name column
				// 9606 63976 REVIEWED NM_022114.3 289547572 NP_071397.3
				// 289547573 NC_000001.10 224589800 2985741 3355184 + Reference
				// GRCh37.p10 Primary Assembly
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab + 1);

				geneID = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));

				// RNA nucleotide accession.version: may be null (-) for some
				// genomes
				RNANucleotideAccessionWithVersion = strLine.substring(indexofThirdTab + 1, indexofFourthTab);

				indexofDot = RNANucleotideAccessionWithVersion.indexOf('.');
				RNANucleotideAccession = null;
				if (indexofDot >= 0) {
					RNANucleotideAccession = RNANucleotideAccessionWithVersion.substring(0, indexofDot);
				}

				if (!RNANucleotideAccessionWithVersion.equals("-")) {

					// FILL RefSeqRNANucleotideAccession2ListofHumanGeneIdMap
					// starts
					if (RNANucleotideAccession != null) {

						geneIDList = RNANucleotideAccession2ListofGeneIdMap.get(RNANucleotideAccession);

						if (geneIDList == null) {
							geneIDList = new ArrayList<Integer>();

							geneIDList.add(geneID);

							RNANucleotideAccession2ListofGeneIdMap.put(RNANucleotideAccession, geneIDList);
						} else {
							if (!geneIDList.contains(geneID)) {
								geneIDList.add(geneID);
							}

							// if(geneIDList.size()>1){
							// System.out.println("geneIDList size: " +
							// geneIDList.size());
							// }

							RNANucleotideAccession2ListofGeneIdMap.put(RNANucleotideAccession, geneIDList);

						}
					}// End of IF: map key is not null
						// FILL
						// RefSeqRNANucleotideAccession2ListofHumanGeneIdMap
						// ends

				}

			}// End of while

			bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// @todo ends

	// //read UCSC GENOME HG19 RefSeq Genes file
	// //fill map --> geneSymbol2ListofRNANucleotideAccessionMap: this map is
	// one 2 many (23732 pairs)
	// public static void fillGeneSymbol2ListofRNANucleotideAccessionMap(
	// String dataFolder,
	// Map<String,List<String>> geneSymbol2ListofRNANucleotideAccessionMap){
	//
	//
	// FileReader fileReader;
	// BufferedReader bufferedReader;
	// String strLine;
	//
	// int indexofFirstTab;
	// int indexofSecondTab;
	// int indexofThirdTab;
	// int indexofFourthTab;
	// int indexofFifthTab;
	// int indexofSixthTab;
	// int indexofSeventhTab;
	// int indexofEighthTab;
	// int indexofNinethTab;
	// int indexofTenthTab;
	// int indexofEleventhTab;
	// int indexofTwelfthTab;
	// int indexofThirteenthTab;
	//
	// String RNANucleotideAccession;
	// String alternateGeneName;
	//
	// List<String> RNANucleotideAccessionList;
	//
	//
	// try {
	// fileReader = FileOperations.createFileReader(dataFolder +
	// Commons.UCSCGENOME_HG19_REFSEQ_GENES_DOWNLOADED_18_NOV_2014);
	// bufferedReader = new BufferedReader(fileReader);
	//
	// //skip header line
	// strLine = bufferedReader.readLine();
	//
	// while((strLine = bufferedReader.readLine())!=null) {
	// //header line
	// //#bin name chrom strand txStart txEnd cdsStart cdsEnd exonCount
	// exonStarts exonEnds score name2 cdsStartStat cdsEndStat exonFrames
	// //example line
	// //0 NM_032291 chr1 + 66999824 67210768 67000041 67208778 25
	// 66999824,67091529,67098752,67101626,67105459,67108492,67109226,67126195,67133212,67136677,67137626,67138963,67142686,67145360,67147551,67154830,67155872,67161116,67184976,67194946,67199430,67205017,67206340,67206954,67208755,
	// 67000051,67091593,67098777,67101698,67105516,67108547,67109402,67126207,67133224,67136702,67137678,67139049,67142779,67145435,67148052,67154958,67155999,67161176,67185088,67195102,67199563,67205220,67206405,67207119,67210768,
	// 0 SGIP1 cmpl cmpl 0,1,2,0,0,0,1,0,0,0,1,2,1,1,1,1,0,1,1,2,2,0,2,1,1,
	//
	// indexofFirstTab = strLine.indexOf('\t');
	// indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
	// indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
	// indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
	// indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
	// indexofSixthTab = strLine.indexOf('\t',indexofFifthTab+1);
	// indexofSeventhTab = strLine.indexOf('\t',indexofSixthTab+1);
	// indexofEighthTab = strLine.indexOf('\t',indexofSeventhTab+1);
	// indexofNinethTab = strLine.indexOf('\t',indexofEighthTab+1);
	// indexofTenthTab = strLine.indexOf('\t',indexofNinethTab+1);
	// indexofEleventhTab = strLine.indexOf('\t',indexofTenthTab+1);
	// indexofTwelfthTab = strLine.indexOf('\t',indexofEleventhTab+1);
	// indexofThirteenthTab = strLine.indexOf('\t',indexofTwelfthTab+1);
	//
	// RNANucleotideAccession = strLine.substring(indexofFirstTab+1,
	// indexofSecondTab);
	// alternateGeneName = strLine.substring(indexofTwelfthTab+1,
	// indexofThirteenthTab);
	//
	//
	//
	// /****************************************************************************/
	// RNANucleotideAccessionList =
	// geneSymbol2ListofRNANucleotideAccessionMap.get(alternateGeneName);
	//
	// if (RNANucleotideAccessionList == null){
	// RNANucleotideAccessionList = new ArrayList<String>();
	// RNANucleotideAccessionList.add(RNANucleotideAccession);
	//
	// geneSymbol2ListofRNANucleotideAccessionMap.put(alternateGeneName,RNANucleotideAccessionList);
	// } else{
	//
	// if (!RNANucleotideAccessionList.contains(RNANucleotideAccession)){
	// RNANucleotideAccessionList.add(RNANucleotideAccession);
	// }
	//
	// // if (RNANucleotideAccessionList.size()>1){
	// // System.out.println("RNANucleotideAccessionList size: " +
	// RNANucleotideAccessionList.size());
	// // }
	//
	// geneSymbol2ListofRNANucleotideAccessionMap.put(alternateGeneName,RNANucleotideAccessionList);
	// }
	// /****************************************************************************/
	//
	//
	//
	// }//End of WHILE
	//
	// bufferedReader.close();
	//
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	//
	// }

	// read UCSC HG19 RefSeq Genes file
	// fill two maps
	// 1. map --> RNANucleotideAccession2ListofAlternateGeneNameMap: this map is
	// one 2 one (41065 pairs)
	// 2. map --> alternateGeneName2ListofRNANucleotideAccessionMap: this map is
	// one 2 many (23732 pairs)
	public static void fillRNANucleotideAccession2ListofAlternateGeneNameMapAndReverseMap(String dataFolder, Map<String, List<String>> RNANucleotideAccession2ListofGeneSymbolMap, Map<String, List<String>> geneSymbol2ListofRNANucleotideAccessionMap) {

		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEighthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;
		int indexofTwelfthTab;
		int indexofThirteenthTab;

		String RNANucleotideAccession;
		String alternateGeneName;

		List<String> alternateGeneNameList;
		List<String> RNANucleotideAccessionList;

		try {
			fileReader = new FileReader(dataFolder + Commons.UCSCGENOME_HG19_REFSEQ_GENES_DOWNLOADED_18_NOV_2014);
			bufferedReader = new BufferedReader(fileReader);

			// skip header line
			strLine = bufferedReader.readLine();

			while ((strLine = bufferedReader.readLine()) != null) {
				// header line
				// #bin name chrom strand txStart txEnd cdsStart cdsEnd
				// exonCount exonStarts exonEnds score name2 cdsStartStat
				// cdsEndStat exonFrames
				// example line
				// 0 NM_032291 chr1 + 66999824 67210768 67000041 67208778 25
				// 66999824,67091529,67098752,67101626,67105459,67108492,67109226,67126195,67133212,67136677,67137626,67138963,67142686,67145360,67147551,67154830,67155872,67161116,67184976,67194946,67199430,67205017,67206340,67206954,67208755,
				// 67000051,67091593,67098777,67101698,67105516,67108547,67109402,67126207,67133224,67136702,67137678,67139049,67142779,67145435,67148052,67154958,67155999,67161176,67185088,67195102,67199563,67205220,67206405,67207119,67210768,
				// 0 SGIP1 cmpl cmpl
				// 0,1,2,0,0,0,1,0,0,0,1,2,1,1,1,1,0,1,1,2,2,0,2,1,1,

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf('\t', indexofFourthTab + 1);
				indexofSixthTab = strLine.indexOf('\t', indexofFifthTab + 1);
				indexofSeventhTab = strLine.indexOf('\t', indexofSixthTab + 1);
				indexofEighthTab = strLine.indexOf('\t', indexofSeventhTab + 1);
				indexofNinethTab = strLine.indexOf('\t', indexofEighthTab + 1);
				indexofTenthTab = strLine.indexOf('\t', indexofNinethTab + 1);
				indexofEleventhTab = strLine.indexOf('\t', indexofTenthTab + 1);
				indexofTwelfthTab = strLine.indexOf('\t', indexofEleventhTab + 1);
				indexofThirteenthTab = strLine.indexOf('\t', indexofTwelfthTab + 1);

				RNANucleotideAccession = strLine.substring(indexofFirstTab + 1, indexofSecondTab);
				alternateGeneName = strLine.substring(indexofTwelfthTab + 1, indexofThirteenthTab);

				/****************************************************************************/
				alternateGeneNameList = RNANucleotideAccession2ListofGeneSymbolMap.get(RNANucleotideAccession);

				if (alternateGeneNameList == null) {
					alternateGeneNameList = new ArrayList<String>();
					alternateGeneNameList.add(alternateGeneName);

					RNANucleotideAccession2ListofGeneSymbolMap.put(RNANucleotideAccession, alternateGeneNameList);
				} else {

					if (!alternateGeneNameList.contains(alternateGeneName)) {
						alternateGeneNameList.add(alternateGeneName);
					}

					if (alternateGeneNameList.size() > 1) {
						System.out.println("alternateGeneNameList size: " + alternateGeneNameList.size());
					}

					RNANucleotideAccession2ListofGeneSymbolMap.put(RNANucleotideAccession, alternateGeneNameList);
				}
				/****************************************************************************/

				/****************************************************************************/
				RNANucleotideAccessionList = geneSymbol2ListofRNANucleotideAccessionMap.get(alternateGeneName);

				if (RNANucleotideAccessionList == null) {
					RNANucleotideAccessionList = new ArrayList<String>();
					RNANucleotideAccessionList.add(RNANucleotideAccession);

					geneSymbol2ListofRNANucleotideAccessionMap.put(alternateGeneName, RNANucleotideAccessionList);
				} else {

					if (!RNANucleotideAccessionList.contains(RNANucleotideAccession)) {
						RNANucleotideAccessionList.add(RNANucleotideAccession);
					}

					// if (RNANucleotideAccessionList.size()>1){
					// System.out.println("RNANucleotideAccessionList size: " +
					// RNANucleotideAccessionList.size());
					// }

					geneSymbol2ListofRNANucleotideAccessionMap.put(alternateGeneName, RNANucleotideAccessionList);
				}
				/****************************************************************************/

			}// End of WHILE

			bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//1 March 2015
	public static void fillGeneId2GeneHugoSymbolMap(
			String dataFolder, 
			TIntObjectMap<String>  geneID2ListofGeneHugoSymbolMap 
	) {
		
		FileReader fileReader;
		BufferedReader bufferedReader;
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
		int indexofTenthTab;
		int indexofEleventhTab;
		int indexofTwelfthTab;
		int indexofThirteenthTab;
		int indexofFourteenthTab;
		int indexofFifteenthTab;

		int geneID;
		String geneSymbol;
		String geneSymbolList;
		
	
		try {
			fileReader = FileOperations.createFileReader(dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_OUTPUT_DIRECTORYNAME + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME_18_NOV_2014);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				// example line
				// the second column is gene id column
				// the fourth column is the refseq gene name column
				// 9606 1 REVIEWED NM_130786.3 161377438 NP_570602.2 21071030
				// AC_000151.1 157718668 55167315 55174019 - Alternate HuRef - -
				// A1BG

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf('\t', indexofFourthTab + 1);
				indexofSixthTab = strLine.indexOf('\t', indexofFifthTab + 1);
				indexofSeventhTab = strLine.indexOf('\t', indexofSixthTab + 1);
				indexofEigthTab = strLine.indexOf('\t', indexofSeventhTab + 1);
				indexofNinethTab = strLine.indexOf('\t', indexofEigthTab + 1);
				indexofTenthTab = strLine.indexOf('\t', indexofNinethTab + 1);
				indexofEleventhTab = strLine.indexOf('\t', indexofTenthTab + 1);
				indexofTwelfthTab = strLine.indexOf('\t', indexofEleventhTab + 1);
				indexofThirteenthTab = strLine.indexOf('\t', indexofTwelfthTab + 1);
				indexofFourteenthTab = strLine.indexOf('\t', indexofThirteenthTab + 1);
				indexofFifteenthTab = strLine.indexOf('\t', indexofFourteenthTab + 1);

				geneID = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));

				// geneSymbol the default symbol for the gene
				geneSymbol = strLine.substring(indexofFifteenthTab + 1);

		
				// FILL geneID2ListofGeneHugoSymbolMap starts
				geneSymbolList = geneID2ListofGeneHugoSymbolMap.get(geneID);

				if (geneSymbolList == null) {
					geneSymbolList =  geneSymbol ;
					geneID2ListofGeneHugoSymbolMap.put(geneID, geneSymbolList);
				} else {
					
					if (!geneSymbolList.contains(geneSymbol)){
						geneSymbolList = geneSymbolList + " " + geneSymbol ;
						geneID2ListofGeneHugoSymbolMap.put(geneID, geneSymbolList);
					}
					
				}
				// FILL geneID2ListofGeneHugoSymbolMap ends

		

			}// End of while

			bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// read NCBI humanGene2RefSeq file
	// fill two maps
	// 1. map --> geneID2ListofRNANucleotideAccessionMap: this map is one 2 many
	// (27240 pairs)
	// 2. map --> RNANucleotideAccession2ListofGeneIDMap: this map is many 2 one
	// (41065 pairs)
	public static void fillGeneId2ListofRNANucleotideAccessionMapAndReverseMap(String dataFolder, Map<Integer, List<String>> humanGeneId2ListofRefSeqRNANucleotideAccessionMap, Map<String, List<Integer>> RefSeqRNANucleotideAccession2ListofHumanGeneIdMap) {

		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;

		int indexofDot;

		int geneID;
		String RNANucleotideAccessionWithVersion;
		String RNANucleotideAccession;

		List<String> RNANucleotideAccessionList;
		List<Integer> geneIDList;

		try {
			fileReader = new FileReader(dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_OUTPUT_DIRECTORYNAME + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME_18_NOV_2014);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				// example line
				// the second column is gene id column
				// the fourth column is the refseq gene name column
				// 9606 63976 REVIEWED NM_022114.3 289547572 NP_071397.3
				// 289547573 NC_000001.10 224589800 2985741 3355184 + Reference
				// GRCh37.p10 Primary Assembly
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab + 1);

				geneID = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));

				// RNA nucleotide accession.version: may be null (-) for some
				// genomes
				RNANucleotideAccessionWithVersion = strLine.substring(indexofThirdTab + 1, indexofFourthTab);

				indexofDot = RNANucleotideAccessionWithVersion.indexOf('.');
				RNANucleotideAccession = null;
				if (indexofDot >= 0) {
					RNANucleotideAccession = RNANucleotideAccessionWithVersion.substring(0, indexofDot);
				}

				RNANucleotideAccessionList = humanGeneId2ListofRefSeqRNANucleotideAccessionMap.get(geneID);

				if (!RNANucleotideAccessionWithVersion.equals("-")) {

					// FILL humanGeneId2ListofRefSeqRNANucleotideAccessionMap
					// starts
					if (RNANucleotideAccessionList == null) {
						RNANucleotideAccessionList = new ArrayList<String>();

						if (RNANucleotideAccession != null) {
							RNANucleotideAccessionList.add(RNANucleotideAccession);
						}

						humanGeneId2ListofRefSeqRNANucleotideAccessionMap.put(geneID, RNANucleotideAccessionList);

					} else {
						if (RNANucleotideAccession != null && !RNANucleotideAccessionList.contains(RNANucleotideAccession)) {
							RNANucleotideAccessionList.add(RNANucleotideAccession);
						}

						// if(refSeqRNANucleotideAccessionList.size()>1){
						// System.out.println("refSeqRNANucleotideAccessionList size: "
						// + refSeqRNANucleotideAccessionList.size());
						// }

						humanGeneId2ListofRefSeqRNANucleotideAccessionMap.put(geneID, RNANucleotideAccessionList);

					}
					// FILL humanGeneId2ListofRefSeqRNANucleotideAccessionMap
					// ends

					// FILL RefSeqRNANucleotideAccession2ListofHumanGeneIdMap
					// starts
					if (RNANucleotideAccession != null) {

						geneIDList = RefSeqRNANucleotideAccession2ListofHumanGeneIdMap.get(RNANucleotideAccession);

						if (geneIDList == null) {
							geneIDList = new ArrayList<Integer>();

							geneIDList.add(geneID);

							RefSeqRNANucleotideAccession2ListofHumanGeneIdMap.put(RNANucleotideAccession, geneIDList);
						} else {
							if (!geneIDList.contains(geneID)) {
								geneIDList.add(geneID);
							}

							// if(geneIDList.size()>1){
							// System.out.println("geneIDList size: " +
							// geneIDList.size());
							// }

							RefSeqRNANucleotideAccession2ListofHumanGeneIdMap.put(RNANucleotideAccession, geneIDList);

						}
					}// End of IF: map key is not null
						// FILL
						// RefSeqRNANucleotideAccession2ListofHumanGeneIdMap
						// ends

				}

			}// End of while

			bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void fillHumanGeneId2ListofRefSeqRNANucleotideAccessionMap(String dataFolder, Map<Integer, List<String>> humanGeneId2ListofRefSeqRNANucleotideAccessionMap) {
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;

		int indexofDot;

		Integer geneId;
		String RNANucleotideAccessionWithVersion;
		String RNANucleotideAccession;

		List<String> refSeqRNANucleotideAccessionList;

		try {
			fileReader = new FileReader(dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_OUTPUT_DIRECTORYNAME + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME_18_NOV_2014);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				// example line
				// the second column is gene id column
				// the fourth column is the refseq gene name column
				// 9606 63976 REVIEWED NM_022114.3 289547572 NP_071397.3
				// 289547573 NC_000001.10 224589800 2985741 3355184 + Reference
				// GRCh37.p10 Primary Assembly
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab + 1);

				geneId = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));

				// RNA nucleotide accession.version: may be null (-) for some
				// genomes
				RNANucleotideAccessionWithVersion = strLine.substring(indexofThirdTab + 1, indexofFourthTab);

				indexofDot = RNANucleotideAccessionWithVersion.indexOf('.');

				RNANucleotideAccession = null;

				if (indexofDot >= 0) {
					RNANucleotideAccession = RNANucleotideAccessionWithVersion.substring(0, indexofDot);
				}

				refSeqRNANucleotideAccessionList = humanGeneId2ListofRefSeqRNANucleotideAccessionMap.get(geneId);

				if (!RNANucleotideAccessionWithVersion.equals("-")) {
					if (refSeqRNANucleotideAccessionList == null) {
						refSeqRNANucleotideAccessionList = new ArrayList<String>();

						if (RNANucleotideAccession != null) {
							refSeqRNANucleotideAccessionList.add(RNANucleotideAccession);
						}

						humanGeneId2ListofRefSeqRNANucleotideAccessionMap.put(geneId, refSeqRNANucleotideAccessionList);

					} else {
						if (RNANucleotideAccession != null && !refSeqRNANucleotideAccessionList.contains(RNANucleotideAccession)) {
							refSeqRNANucleotideAccessionList.add(RNANucleotideAccession);
						}

						humanGeneId2ListofRefSeqRNANucleotideAccessionMap.put(geneId, refSeqRNANucleotideAccessionList);

					}
				}

			}// End of while

			bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void fillHumanRefSeqRNANucleotideAccession2ListofAlternateGeneNameMap(String dataFolder, Map<String, List<String>> humanRefSeqRNANucleotideAccession2ListofAlternateGeneNameMap) {

		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEighthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		int indexofEleventhTab;
		int indexofTwelfthTab;
		int indexofThirteenthTab;

		String refSeqRNANucleotideAccession;
		String alternateGeneName;

		List<String> alternateGeneNameList;

		try {
			fileReader = new FileReader(dataFolder + Commons.UCSCGENOME_HG19_REFSEQ_GENES_DOWNLOADED_18_NOV_2014);
			bufferedReader = new BufferedReader(fileReader);

			// skip header line
			strLine = bufferedReader.readLine();

			while ((strLine = bufferedReader.readLine()) != null) {
				// header line
				// #bin name chrom strand txStart txEnd cdsStart cdsEnd
				// exonCount exonStarts exonEnds score name2 cdsStartStat
				// cdsEndStat exonFrames
				// example line
				// 0 NM_032291 chr1 + 66999824 67210768 67000041 67208778 25
				// 66999824,67091529,67098752,67101626,67105459,67108492,67109226,67126195,67133212,67136677,67137626,67138963,67142686,67145360,67147551,67154830,67155872,67161116,67184976,67194946,67199430,67205017,67206340,67206954,67208755,
				// 67000051,67091593,67098777,67101698,67105516,67108547,67109402,67126207,67133224,67136702,67137678,67139049,67142779,67145435,67148052,67154958,67155999,67161176,67185088,67195102,67199563,67205220,67206405,67207119,67210768,
				// 0 SGIP1 cmpl cmpl
				// 0,1,2,0,0,0,1,0,0,0,1,2,1,1,1,1,0,1,1,2,2,0,2,1,1,

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab + 1);
				indexofFifthTab = strLine.indexOf('\t', indexofFourthTab + 1);
				indexofSixthTab = strLine.indexOf('\t', indexofFifthTab + 1);
				indexofSeventhTab = strLine.indexOf('\t', indexofSixthTab + 1);
				indexofEighthTab = strLine.indexOf('\t', indexofSeventhTab + 1);
				indexofNinethTab = strLine.indexOf('\t', indexofEighthTab + 1);
				indexofTenthTab = strLine.indexOf('\t', indexofNinethTab + 1);
				indexofEleventhTab = strLine.indexOf('\t', indexofTenthTab + 1);
				indexofTwelfthTab = strLine.indexOf('\t', indexofEleventhTab + 1);
				indexofThirteenthTab = strLine.indexOf('\t', indexofTwelfthTab + 1);

				refSeqRNANucleotideAccession = strLine.substring(indexofFirstTab + 1, indexofSecondTab);
				alternateGeneName = strLine.substring(indexofTwelfthTab + 1, indexofThirteenthTab);

				alternateGeneNameList = humanRefSeqRNANucleotideAccession2ListofAlternateGeneNameMap.get(refSeqRNANucleotideAccession);

				if (alternateGeneNameList == null) {
					alternateGeneNameList = new ArrayList<String>();
					alternateGeneNameList.add(alternateGeneName);

					humanRefSeqRNANucleotideAccession2ListofAlternateGeneNameMap.put(refSeqRNANucleotideAccession, alternateGeneNameList);
				} else {

					if (!alternateGeneNameList.contains(alternateGeneName)) {
						alternateGeneNameList.add(alternateGeneName);
					}

					humanRefSeqRNANucleotideAccession2ListofAlternateGeneNameMap.put(refSeqRNANucleotideAccession, alternateGeneNameList);

				}

			}// End of WHILE

			bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void augmentGeneIdWithRefSeqRNANucleotideAccession(List<Integer> keggPathwayGeneIdList, List<String> keggPathwayRefSeqRNANucleotideAccessionList, Map<Integer, List<String>> humanGeneId2ListofRefSeqRNANucleotideAccessionMap) {
		List<String> refSeqRNANucleotideAccessionList;

		for (Integer geneId : keggPathwayGeneIdList) {
			refSeqRNANucleotideAccessionList = humanGeneId2ListofRefSeqRNANucleotideAccessionMap.get(geneId);

			if (refSeqRNANucleotideAccessionList != null) {
				for (String refSeqRNANucleotideAccession : refSeqRNANucleotideAccessionList) {
					if (!keggPathwayRefSeqRNANucleotideAccessionList.contains(refSeqRNANucleotideAccession)) {
						keggPathwayRefSeqRNANucleotideAccessionList.add(refSeqRNANucleotideAccession);
					}
				}
			}
		}

	}

	public static void augmentRefSeqRNANucleotideAccessionwithAlternateGeneName(List<String> keggPathwayRefSeqRNANucleotideAccessionList, List<String> keggPathwayAlternateGeneNameList, Map<String, List<String>> humanRefSeqRNANucleotideAccession2ListofAlternateGeneNameMap) {
		List<String> alternateGeneNameList;

		for (String refSeqRNANucleotideAccession : keggPathwayRefSeqRNANucleotideAccessionList) {
			alternateGeneNameList = humanRefSeqRNANucleotideAccession2ListofAlternateGeneNameMap.get(refSeqRNANucleotideAccession);

			if (alternateGeneNameList != null) {
				for (String alternateGeneName : alternateGeneNameList) {
					if (!keggPathwayAlternateGeneNameList.contains(alternateGeneName)) {
						keggPathwayAlternateGeneNameList.add(alternateGeneName);
					}
				}
			}

		}
	}

	/**
	 * 
	 */
	public HumanGenesAugmentation() {
		// TODO Auto-generated constructor stub
	}

	// args[0] must have input file name with folder
	// args[1] must have GLANET installation folder with "\\" at the end. This
	// folder will be used for outputFolder and dataFolder.
	// args[2] must have Input File Format
	// args[3] must have Number of Permutations
	// args[4] must have False Discovery Rate (ex: 0.05)
	// args[5] must have Generate Random Data Mode (with GC and
	// Mapability/without GC and Mapability)
	// args[6] must have writeGeneratedRandomDataMode checkBox
	// args[7] must have
	// writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	// args[8] must have writePermutationBasedAnnotationResultMode checkBox
	// args[9] must have Dnase Enrichment example: DO_DNASE_ENRICHMENT or
	// DO_NOT_DNASE_ENRICHMENT
	// args[10] must have Histone Enrichment example : DO_HISTONE_ENRICHMENT or
	// DO_NOT_HISTONE_ENRICHMENT
	// args[11] must have Tf and KeggPathway Enrichment example:
	// DO_TF_KEGGPATHWAY_ENRICHMENT or DO_NOT_TF_KEGGPATHWAY_ENRICHMENT
	// args[12] must have Tf and CellLine and KeggPathway Enrichment example:
	// DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT or
	// DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// args[13] must have a job name exampe: any_string
	public static void main(String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");
		// String outputFolder = glanetFolder + Commons.OUTPUT +
		// System.getProperty("file.separator") ;

		// Fill these maps using NCBI Human gene2RefSeq file
		Map<Integer, List<String>> geneId2ListofRNANucleotideAccessionMap = new HashMap<Integer, List<String>>();
		Map<String, List<Integer>> RNANucleotideAccession2ListofGeneIdMap = new HashMap<String, List<Integer>>();

		// Fill these maps using UCSC HG19 RefSeq Genes file
		Map<String, List<String>> RNANucleotideAccession2ListofAlternateGeneNameMap = new HashMap<String, List<String>>();
		Map<String, List<String>> alternateGeneName2ListofRNANucleotideAccessionMap = new HashMap<String, List<String>>();

		// // for testing purposes
		// fillHumanGeneId2ListofRefSeqRNANucleotideAccessionMap(dataFolder,humanGeneId2ListofRefSeqRNANucleotideAccessionMap);
		// fillHumanRefSeqRNANucleotideAccession2ListofAlternateGeneNameMap(dataFolder,humanRefSeqRNANucleotideAccession2ListofAlternateGeneNameMap);

		// For testing purposes
		fillGeneId2ListofRNANucleotideAccessionMapAndReverseMap(dataFolder, geneId2ListofRNANucleotideAccessionMap, RNANucleotideAccession2ListofGeneIdMap);
		System.out.println("geneId2ListofRNANucleotideAccessionMap Size: " + geneId2ListofRNANucleotideAccessionMap.size());
		System.out.println("RNANucleotideAccession2ListofGeneIdMap Size: " + RNANucleotideAccession2ListofGeneIdMap.size());

		fillRNANucleotideAccession2ListofAlternateGeneNameMapAndReverseMap(dataFolder, RNANucleotideAccession2ListofAlternateGeneNameMap, alternateGeneName2ListofRNANucleotideAccessionMap);
		System.out.println("RNANucleotideAccession2ListofAlternateGeneNameMap Size: " + RNANucleotideAccession2ListofAlternateGeneNameMap.size());
		System.out.println("alternateGeneName2ListofRNANucleotideAccessionMap Size: " + alternateGeneName2ListofRNANucleotideAccessionMap.size());

		List<Integer> keggPathwayGeneIdList = new ArrayList<Integer>();
		keggPathwayGeneIdList.add(10000);
		keggPathwayGeneIdList.add(1050);
		keggPathwayGeneIdList.add(11040);
		keggPathwayGeneIdList.add(1147);
		keggPathwayGeneIdList.add(1978);
		keggPathwayGeneIdList.add(207);
		keggPathwayGeneIdList.add(208);
		keggPathwayGeneIdList.add(2322);
		keggPathwayGeneIdList.add(23533);

		List<String> keggPathwayRefSeqRNANucleotideAccessionList = new ArrayList<String>();
		List<String> keggPathwayAlternateGeneNameList = new ArrayList<String>();

		augmentGeneIdWithRefSeqRNANucleotideAccession(keggPathwayGeneIdList, keggPathwayRefSeqRNANucleotideAccessionList, geneId2ListofRNANucleotideAccessionMap);
		augmentRefSeqRNANucleotideAccessionwithAlternateGeneName(keggPathwayRefSeqRNANucleotideAccessionList, keggPathwayAlternateGeneNameList, RNANucleotideAccession2ListofAlternateGeneNameMap);

	}

}
