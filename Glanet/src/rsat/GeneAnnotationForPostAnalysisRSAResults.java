/**
 * 
 */
package rsat;

import enumtypes.AssociationMeasureType;
import enumtypes.ChromosomeName;
import enumtypes.GeneOverlapAnalysisFileMode;
import enumtypes.IntervalName;
import enumtypes.WriteAnnotationFoundOverlapsMode;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import intervaltree.UcscRefSeqGeneIntervalTreeNodeWithNumbers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jaxbxjctool.NCBIEutils;

import org.apache.log4j.Logger;

import remap.Remap;
import annotation.Annotation;
import annotation.OverlapInformation;
import augmentation.humangenes.HumanGenesAugmentation;
import auxiliary.FileOperations;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Nov 28, 2016
 * @project Glanet 
 * 
 * This class enhances gene annotation for the significant SNPs in Post Analysis Results of Regulatory Sequence Analysis.
 *
 */
public class GeneAnnotationForPostAnalysisRSAResults {
	
	final static Logger logger = Logger.getLogger(GeneAnnotationForPostAnalysisRSAResults.class);
	
	public static void convert_FromLatestAssembly_ToGRCh37p13(
			String dataFolder,
			String inputOutputDataFolder,
			String PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly_FileName,
			String PostAnalysis_RSA_chrName_1BasedCoordinateGRCh37p13_FileName,
			Boolean fillMap,
			Map<String,String> latestAssemblyCoordinates2GRCh37p13CoordinatesMap){
		
		//Get Latest Assembly right now it is GRCh38.p7
		String latestAssembyNameReturnedByNCBIEutils = NCBIEutils.getLatestAssemblyNameReturnedByNCBIEutils();
		
		Remap.convertGivenInputCoordinatesFromSourceAssemblytoTargetAssemblyUsingRemap(
				dataFolder,
				inputOutputDataFolder, 		
				PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly_FileName, 
				PostAnalysis_RSA_chrName_1BasedCoordinateGRCh37p13_FileName, 
				latestAssembyNameReturnedByNCBIEutils, 
				"GRCh37.p13",
				false,
				fillMap,
				latestAssemblyCoordinates2GRCh37p13CoordinatesMap);

		
	}
	
	public static void writeInputFile(
			String RSAFolder,
			String PostAnalysis_RSA_chrName_0BasedStart_0BasedEnd_CoordinatesınGRCh37p13_FileName,
			Map<String,String> _0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap){
		
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		int indexofFirstTab = -1;
		int _0BasedCoordinate_InGRCH37p13 = -1;
		
		try {
			fileWriter = FileOperations.createFileWriter(RSAFolder + PostAnalysis_RSA_chrName_0BasedStart_0BasedEnd_CoordinatesınGRCh37p13_FileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(String key : _0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap.keySet()){
				
				indexofFirstTab = key.indexOf("\t");
				_0BasedCoordinate_InGRCH37p13 = Integer.parseInt(key.substring(indexofFirstTab+1));
				
				bufferedWriter.write(key + "\t" + _0BasedCoordinate_InGRCH37p13 + System.getProperty("line.separator"));
			
			}//End of FOR
			
			
			//Close
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void fillMaps(
			Map<String,String> _1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map,
			Map<String,String> _1BasedCoordinatesInlatestAssembly_2_0BasedCoordinatesInGRCh37p13Map,
			Map<String,String> _0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap){
		
		String key_chrName_Tab_1BasedCoordinate_InLatestAssembly = null;
		String value_chrName_Tab_1BasedCoordinate_InGRCh37p13 = null;
		String chrName_Tab_0BasedCoordinate_InGRCh37p13 = null;
		
		
		int indexofFirstTab = -1;
		int _1BasedCoordinate_InGRCh37p13 = -1;
		int _0BasedCoordinate_InGRCh37p13 = -1;
		String chrName = null;
		
		
		for(Map.Entry<String, String> entry: _1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map.entrySet()){
			
			key_chrName_Tab_1BasedCoordinate_InLatestAssembly = entry.getKey();
			value_chrName_Tab_1BasedCoordinate_InGRCh37p13 = entry.getValue();
			
			indexofFirstTab = value_chrName_Tab_1BasedCoordinate_InGRCh37p13.indexOf("\t");
			
			chrName = value_chrName_Tab_1BasedCoordinate_InGRCh37p13.substring(0,indexofFirstTab);
			_1BasedCoordinate_InGRCh37p13 = Integer.parseInt(value_chrName_Tab_1BasedCoordinate_InGRCh37p13.substring(indexofFirstTab+1));
			
			_0BasedCoordinate_InGRCh37p13 = _1BasedCoordinate_InGRCh37p13 - 1 ;
			
			chrName_Tab_0BasedCoordinate_InGRCh37p13 = chrName + "\t" + _0BasedCoordinate_InGRCh37p13;
		
			//Fill map
			_1BasedCoordinatesInlatestAssembly_2_0BasedCoordinatesInGRCh37p13Map.put(key_chrName_Tab_1BasedCoordinate_InLatestAssembly,chrName_Tab_0BasedCoordinate_InGRCh37p13);
			
			//Fill map, put only keys.
			_0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap.put(chrName_Tab_0BasedCoordinate_InGRCh37p13, null);
		}//End of FOR
		
				
	}
	
	

	
	
	
	public static String writeOverlaps(
			TIntObjectMap<List<UcscRefSeqGeneIntervalTreeNodeWithNumbers>> geneId2OverlapListMap,
			int givenIntervalNumber, 
			IntervalName intervalName,
			TIntObjectMap<String> geneHugoSymbolNumber2NameMap) throws IOException {

		int overlapCount = 0;
		String overlapStringInformation = "";

		List<UcscRefSeqGeneIntervalTreeNodeWithNumbers> overlapList = null;
		UcscRefSeqGeneIntervalTreeNodeWithNumbers overlapNode = null;

		// Accumulate overlapCount and overlapStringInformation for each geneID
		// For each geneId
		// Iterate Over Map
		for( TIntObjectIterator<List<UcscRefSeqGeneIntervalTreeNodeWithNumbers>> it1 = geneId2OverlapListMap.iterator(); it1.hasNext();){
			it1.advance();

			overlapList = it1.value();

			overlapCount = overlapCount + overlapList.size();

			// For each overlap
			// Iterate Over List
			for( Iterator<UcscRefSeqGeneIntervalTreeNodeWithNumbers> it2 = overlapList.iterator(); it2.hasNext();){

				overlapNode = it2.next();

				switch( overlapNode.getIntervalName()){

				// Write IntervalNumber
				case EXON:
				case INTRON:
					overlapStringInformation = overlapStringInformation + "[" + overlapNode.getGeneEntrezId() + "_" + geneHugoSymbolNumber2NameMap.get( overlapNode.getGeneHugoSymbolNumber()) + "_" + overlapNode.getIntervalName().convertEnumtoString() + "_" + overlapNode.getIntervalNumber() + "] ";

					break;

				// Do Not Write IntervalNumber
				case FIVE_P_ONE:
				case FIVE_P_TWO:
				case FIVE_D:
				case THREE_P_ONE:
				case THREE_P_TWO:
				case THREE_D:
					overlapStringInformation = overlapStringInformation + "[" + overlapNode.getGeneEntrezId() + "_" + geneHugoSymbolNumber2NameMap.get( overlapNode.getGeneHugoSymbolNumber()) + "_" + overlapNode.getIntervalName().convertEnumtoString() + "] ";

					break;

				}// End of Switch

			}// End of For: each Overlap

		}// End of For: each geneID

		return overlapStringInformation;
	}
	
	
	public static void fill(
			Map<String,String> _0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap,
			TIntObjectMap<String> givenIntervalNumber2GivenIntervalNameMap,
			TIntObjectMap<OverlapInformation> givenIntervalNumber2OverlapInformationMap,
			TIntObjectMap<String> geneHugoSymbolNumber2NameMap){
		
		int givenIntervalNumber = -1;
		String givenIntervalName = null;
		
		OverlapInformation overlapInformation = null;
		TIntObjectMap<List<UcscRefSeqGeneIntervalTreeNodeWithNumbers>> geneId2OverlapListMap = null;
		
		
		int  indexofFirstUnderscore = -1;
		int  indexofSecondUnderscore = -1;
		String chrName_0BasedStartInGRCh37p13 = null;
		String chrNameTab0BasedStartInGRCh37p13 =null;
		
		String overlapStringInformation = "";
		
		
		for( int i = 1; i <= givenIntervalNumber2GivenIntervalNameMap.size(); i++){

			givenIntervalNumber = i;
			
			//initialize
			overlapStringInformation = "";

			//example chr1_55352821_55352821
			givenIntervalName = givenIntervalNumber2GivenIntervalNameMap.get(givenIntervalNumber);
			
			indexofFirstUnderscore = givenIntervalName.indexOf("_");
			indexofSecondUnderscore = givenIntervalName.indexOf("_",indexofFirstUnderscore+1);
			chrName_0BasedStartInGRCh37p13 = givenIntervalName.substring(0, indexofSecondUnderscore);
			chrNameTab0BasedStartInGRCh37p13 = chrName_0BasedStartInGRCh37p13.replace('_', '\t');
			
			

			overlapInformation = givenIntervalNumber2OverlapInformationMap.get(givenIntervalNumber);

			
			if( overlapInformation != null){

				try {
					/***************************************************************/
					/*********** geneId 2 ExonOverlapsList Map starts ****************/
					/***************************************************************/
					// geneId2ExonOverlapsListMap
					geneId2OverlapListMap = overlapInformation.getGeneId2ExonOverlapListMap();
	
					// Write Number of Exon Overlaps
					
					overlapStringInformation =	overlapStringInformation + writeOverlaps(
								geneId2OverlapListMap, 
								givenIntervalNumber,
								IntervalName.EXON, 
								geneHugoSymbolNumber2NameMap);
					
					/***************************************************************/
					/*********** geneId 2 ExonOverlapsList Map ends ******************/
					/***************************************************************/
	
					/***************************************************************/
					/*********** geneId 2 IntronOverlapsList Map starts **************/
					/***************************************************************/
					// geneId2IntronOverlapsListMap
					geneId2OverlapListMap = overlapInformation.getGeneId2IntronOverlapListMap();
	
					// Write Number of Intron Overlaps
					overlapStringInformation = overlapStringInformation + writeOverlaps(
							geneId2OverlapListMap, 
							givenIntervalNumber,
							IntervalName.INTRON, 
							geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 IntronOverlapsList Map ends ****************/
					/***************************************************************/
	
					/***************************************************************/
					/*********** geneId 2 Fivep1OverlapsList Map starts **************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2Fivep1OverlapListMap();
	
					overlapStringInformation = overlapStringInformation + writeOverlaps(
							geneId2OverlapListMap, 
							givenIntervalNumber,
							IntervalName.FIVE_P_ONE, 
							geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 Fivep1OverlapsList Map ends ****************/
					/***************************************************************/
	
					/***************************************************************/
					/*********** geneId 2 Fivep2OverlapsList Map starts **************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2Fivep2OverlapListMap();
	
					overlapStringInformation = overlapStringInformation + writeOverlaps(
							geneId2OverlapListMap, 
							givenIntervalNumber,
							IntervalName.FIVE_P_TWO, 
							geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 Fivep2OverlapsList Map ends ****************/
					/***************************************************************/
	
					/***************************************************************/
					/*********** geneId 2 FivedOverlapsList Map starts **************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2FivedOverlapListMap();
	
					overlapStringInformation = overlapStringInformation + writeOverlaps(
							geneId2OverlapListMap, 
							givenIntervalNumber,
							IntervalName.FIVE_D, 
							geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 FivedOverlapsList Map ends ****************/
					/***************************************************************/
	
					/***************************************************************/
					/*********** geneId 2 Threep1OverlapsList Map starts *************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2Threep1OverlapListMap();
	
					overlapStringInformation = overlapStringInformation + writeOverlaps(
							geneId2OverlapListMap, 
							givenIntervalNumber,
							IntervalName.THREE_P_ONE, 
							geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 Threep1OverlapsList Map ends ***************/
					/***************************************************************/
	
					/***************************************************************/
					/*********** geneId 2 Threep2OverlapsList Map starts *************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2Threep2OverlapListMap();
	
					overlapStringInformation = overlapStringInformation + writeOverlaps(
							geneId2OverlapListMap, 
							givenIntervalNumber,
							IntervalName.THREE_P_TWO, 
							geneHugoSymbolNumber2NameMap);
					/***************************************************************/
					/*********** geneId 2 Threep2OverlapsList Map ends ***************/
					/***************************************************************/
	
					/***************************************************************/
					/*********** geneId 2 ThreedOverlapsList Map starts **************/
					/***************************************************************/
					geneId2OverlapListMap = overlapInformation.getGeneId2ThreedOverlapListMap();
	
					overlapStringInformation = overlapStringInformation + writeOverlaps(
							geneId2OverlapListMap, 
							givenIntervalNumber,
							IntervalName.THREE_D, 
							geneHugoSymbolNumber2NameMap);
					
					/***************************************************************/
					/*********** geneId 2 ThreedOverlapsList Map ends ****************/
					/***************************************************************/
		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}// End of IF: overlapInformation is not null
			
			if (_0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap.containsKey(chrNameTab0BasedStartInGRCh37p13)){
				_0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap.put(chrNameTab0BasedStartInGRCh37p13,overlapStringInformation);
			}
			
		}// End of FOR: each given interval

		
	}
	
	
	public static void augmentRSAtPostAnalysisWithGeneAnnotations(
			String RSAFolder,
			String RSAPostAnalysisFileName,
			String RSAPostAnalysisAugmentedWithGeneAnnotationsFileName,
			Map<String,String> _1BasedCoordinatesInlatestAssembly_2_0BasedCoordinatesInGRCh37p13Map,
			Map<String,String> _0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap){
		
		
		FileReader fileReader  =null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter  =null;
		BufferedWriter bufferedWriter = null;
		
		String strLine = null;		
		int indexofFirstTab = -1;
		int indexofSecondTab = -1;
				
		String chrName_1BasedCoordinateInLatestAssemly_rsID_TF = null;
		int indexofFirstUnderScore = -1;
		int indexofSecondUnderScore = -1;		
		String chrName = null;
		int _1BasedCoordinateInLatestAssemly = -1;

		String chrName_1BasedCoordinateInLatestAssembly = null;
		String chrName_0BasedCoordinateInGRCh37p13 = null;
		String geneAnnotations = null;
		
		
		try {
			fileReader  = FileOperations.createFileReader(RSAFolder + RSAPostAnalysisFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter  = FileOperations.createFileWriter(RSAFolder + RSAPostAnalysisAugmentedWithGeneAnnotationsFileName);
			bufferedWriter = new BufferedWriter(fileWriter);

			//Example lines from RSAPostAnalysisFileName
//			Interesting Finding Number	Chr_Position_rsID_TF	SNP Effect	p_Ref	p_SNP	p_TFExtended
//			1	chr10_110567718_rs148267784_TBP	 SNP has a worse match (disrupting effect).	1E-5	4.9E-5	1E-5
			
			//Sip header line
			strLine= bufferedReader.readLine();
		
			while((strLine=bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf("\t");
				indexofSecondTab = strLine.indexOf("\t",indexofFirstTab+1);
				
				chrName_1BasedCoordinateInLatestAssemly_rsID_TF = strLine.substring(indexofFirstTab+1,indexofSecondTab);
				
				indexofFirstUnderScore = chrName_1BasedCoordinateInLatestAssemly_rsID_TF.indexOf("_");
				indexofSecondUnderScore = chrName_1BasedCoordinateInLatestAssemly_rsID_TF.indexOf("_",indexofFirstUnderScore+1);
				
				chrName = chrName_1BasedCoordinateInLatestAssemly_rsID_TF.substring(0, indexofFirstUnderScore);
				_1BasedCoordinateInLatestAssemly = Integer.parseInt(chrName_1BasedCoordinateInLatestAssemly_rsID_TF.substring(indexofFirstUnderScore+1,indexofSecondUnderScore));
				
				chrName_1BasedCoordinateInLatestAssembly = new String(chrName + "\t" + _1BasedCoordinateInLatestAssemly);
				
				chrName_0BasedCoordinateInGRCh37p13 = _1BasedCoordinatesInlatestAssembly_2_0BasedCoordinatesInGRCh37p13Map.get(chrName_1BasedCoordinateInLatestAssembly);
			
				geneAnnotations = _0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap.get(chrName_0BasedCoordinateInGRCh37p13);
				
				bufferedWriter.write(strLine + "\t" + geneAnnotations + System.getProperty("line.separator")); 
				
			}

			//Close
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



	public static void annotateInputFileWithRefSeqGenes(
			String dataFolder,
			String RSAFolder,			
			String PostAnalysis_RSA_chrName_0BasedStart_0BasedEnd_CoordinatesınGRCh37p13_FileName,
			String RSAPostAnalysisFileName,
			String RSAPostAnalysisAugmentedWithGeneAnnotationsFileName,
			Map<String,String> _1BasedCoordinatesInlatestAssembly_2_0BasedCoordinatesInGRCh37p13Map,
			Map<String,String> _0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap,
			WriteAnnotationFoundOverlapsMode writeFoundOverlapsMode){
		
		/*****************************************************************************************/
		/************************* GIVEN INPUT DATA starts ***************************************/
		/*****************************************************************************************/
		String inputFileName = RSAFolder +  PostAnalysis_RSA_chrName_0BasedStart_0BasedEnd_CoordinatesınGRCh37p13_FileName;

		TIntObjectMap<FileWriter> chrNumber2FileWriterMap = new TIntObjectHashMap<FileWriter>();
		TIntObjectMap<BufferedWriter> chrNumber2BufferedWriterMap = new TIntObjectHashMap<BufferedWriter>();

		// Prepare chromosome based partitioned input interval files to be searched for
		// Create Buffered Writers for writing chromosome based input files
		FileOperations.createChromBaseSearchInputFiles(RSAFolder, chrNumber2FileWriterMap, chrNumber2BufferedWriterMap);

		// Partition the input file into 24 chromosome based input files
		FileOperations.partitionSearchInputFilePerChromName(inputFileName, chrNumber2BufferedWriterMap);

		// Close Buffered Writers
		FileOperations.closeBufferedWriterList(chrNumber2FileWriterMap, chrNumber2BufferedWriterMap);
		/*****************************************************************************************/
		/************************* GIVEN INPUT DATA ends *****************************************/
		/*****************************************************************************************/
		
		
//		GlanetRunner.appendLog("**********************************************************");
//		GlanetRunner.appendLog("Hg19 RefSeq Gene Annotation for RSA Post Analysis starts: " + new Date());
		
		AssociationMeasureType associationMeasureType = AssociationMeasureType.EXISTENCE_OF_OVERLAP;
		int overlapDefinition = 1;
		
		TIntObjectMap<String> geneHugoSymbolNumber2NameMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> refSeqRNANucleotideAccessionNumber2NameMap = new TIntObjectHashMap<String>();
		TIntObjectMap<String> geneEntrezId2GeneOfficialSymbolMap = new TIntObjectHashMap<String>();
		
		FileOperations.fillNumber2NameMap(
				geneHugoSymbolNumber2NameMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_GENESYMBOL_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.fillNumber2NameMap(
				refSeqRNANucleotideAccessionNumber2NameMap,
				dataFolder,
				Commons.ALL_POSSIBLE_NAMES_UCSCGENOME_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_UCSCGENOME_HG19_REFSEQ_GENES_RNANUCLEOTIDEACCESSION_NUMBER_2_NAME_OUTPUT_FILENAME);

		HumanGenesAugmentation.fillGeneId2GeneHugoSymbolMap(
				dataFolder, 
				geneEntrezId2GeneOfficialSymbolMap);

		
		// Hg19 RefSeq Genes
		TIntIntMap geneEntrezID2KMap = new TIntIntHashMap();
		TIntObjectMap<String> givenIntervalNumber2GivenIntervalNameMap = new TIntObjectHashMap<String>();
		TIntObjectMap<OverlapInformation> givenIntervalNumber2OverlapInformationMap = new TIntObjectHashMap<OverlapInformation>();
		TIntIntMap givenIntervalNumber2NumberofGeneOverlapsMap = new TIntIntHashMap();
		// 13 February 2015
		TObjectIntMap<ChromosomeName> chromosomeName2CountMap = new TObjectIntHashMap<ChromosomeName>();

		Annotation.searchGeneWithNumbers(
				dataFolder, 
				RSAFolder, 
				writeFoundOverlapsMode,
				givenIntervalNumber2GivenIntervalNameMap, 
				givenIntervalNumber2OverlapInformationMap,
				chromosomeName2CountMap, 
				geneEntrezID2KMap, 
				overlapDefinition, 
				geneHugoSymbolNumber2NameMap,
				refSeqRNANucleotideAccessionNumber2NameMap,
				associationMeasureType);

		GeneOverlapAnalysisFileMode geneOverlapAnalysisFileMode = GeneOverlapAnalysisFileMode.WITH_OVERLAP_INFORMATION;
		
		//Fill this map _0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap
		fill(_0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap,
			givenIntervalNumber2GivenIntervalNameMap,
			givenIntervalNumber2OverlapInformationMap,
			geneHugoSymbolNumber2NameMap);
		
		augmentRSAtPostAnalysisWithGeneAnnotations(
				RSAFolder,
				RSAPostAnalysisFileName,
				RSAPostAnalysisAugmentedWithGeneAnnotationsFileName,
				_1BasedCoordinatesInlatestAssembly_2_0BasedCoordinatesInGRCh37p13Map,
				_0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap);

		Annotation.writeGeneOverlapAnalysisFile( 
				RSAFolder,
				Commons.HG19_REFSEQ_GENE_ANNOTATION_DIRECTORY + Commons.ANALYSIS_DIRECTORY + Commons.OVERLAP_ANALYSIS_FILE,
				geneOverlapAnalysisFileMode, 
				givenIntervalNumber2GivenIntervalNameMap,
				givenIntervalNumber2OverlapInformationMap, 
				givenIntervalNumber2NumberofGeneOverlapsMap,
				chromosomeName2CountMap, 
				geneHugoSymbolNumber2NameMap);

		Annotation.writeResultsWithNumbers( 
				associationMeasureType,
				geneEntrezID2KMap, 
				geneEntrezId2GeneOfficialSymbolMap, 
				RSAFolder,
				Commons.ANNOTATION_RESULTS_FOR_HG19_REFSEQ_GENE_ALTERNATE_NAME);
		
		
//		GlanetRunner.appendLog("Hg19 RefSeq Gene Annotation for RSA Post Analysis ends: " + new Date());
//		GlanetRunner.appendLog("**********************************************************");

		geneEntrezID2KMap = null;
		
	}

	public static void readPostAnalysisRSAResults(
			String dataFolder,
			String outputFolder,
			String RSAFolder,
			String RSAPostAnalysisFileName,
			String RSAPostAnalysisAugmentedWithGeneAnnotationsFileName,
			WriteAnnotationFoundOverlapsMode writeFoundOverlapsMode){
		
		//First read outputFile resulting from RSA post analysis
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//Then write outputfile in _1BasedCoordinates in LatestAssemblyfile post analysis RSA file
		//With no duplicates
		FileWriter fileWriter = null;
		String PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly_FileName = "PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly.txt";
		BufferedWriter bufferedWriter = null;
		
		//Second downlift and write outputFile in 1Based Coordinates in GRCh37.p13 using remap report or excel files
		String PostAnalysis_RSA_chrName_1BasedCoordinateGRCh37p13_FileName = "PostAnalysis_RSA_chrName_1BasedCoordinateInGRCh37p13.txt";

		//Third outputFile in _0BasedStart _0BasedEnd Coordinates in GRCh37.p13 using my map, this will be used in gene annotation
		String PostAnalysis_RSA_chrName_0BasedStart_0BasedEnd_CoordinatesınGRCh37p13_FileName = "PostAnalysis_RSA_chrName_0BasedStart_0BasedEnd_CoordinateInGRCh37p13.txt";		
		
		String strLine = null;
		int indexofFirstTab = -1;
		int indexofSecondTab = -1;
				
		String chrName_1BasedCoordinateInLatestAssemly_rsID_TF = null;
		int indexofFirstUnderScore = -1;
		int indexofSecondUnderScore = -1;		
		String chrName = null;
		int _1BasedCoordinateInLatestAssemly = -1;
		
		Map<String,String> _1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map = new HashMap<String,String>();
		Map<String,String> _1BasedCoordinatesInlatestAssembly_2_0BasedCoordinatesInGRCh37p13Map = new HashMap<String,String>();
		Map<String,String> _0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap = new HashMap<String,String>();
		
		String key_chrName_1BasedCoordinateInLatestAssembly = null;
		
		//example lines in RSAPostAnalysisFile
//		Interesting Finding Number	Chr_Position_rsID_TF	SNP Effect	p_Ref	p_SNP	p_TFExtended
//		1	chr10_110567718_rs148267784_TBP	 SNP has a worse match (disrupting effect).	1E-5	4.9E-5	1E-5
//		2	chr10_110567718_rs148267784_SP1	 SNP has a worse match (disrupting effect).	9.5E-7	5.6E-6	9.5E-7
		
		try {
			fileReader = FileOperations.createFileReader(RSAFolder + RSAPostAnalysisFileName);		
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(RSAFolder +  PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly_FileName);
			bufferedWriter = new BufferedWriter(fileWriter);
						
			//Skip header line in RSAPostAnalysisFile
			strLine = bufferedReader.readLine();
		
			while ((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf("\t");
				indexofSecondTab = strLine.indexOf("\t",indexofFirstTab+1);
				
				chrName_1BasedCoordinateInLatestAssemly_rsID_TF = strLine.substring(indexofFirstTab+1,indexofSecondTab);
				
				indexofFirstUnderScore = chrName_1BasedCoordinateInLatestAssemly_rsID_TF.indexOf("_");
				indexofSecondUnderScore = chrName_1BasedCoordinateInLatestAssemly_rsID_TF.indexOf("_",indexofFirstUnderScore+1);
				
				chrName = chrName_1BasedCoordinateInLatestAssemly_rsID_TF.substring(0, indexofFirstUnderScore);
				_1BasedCoordinateInLatestAssemly = Integer.parseInt(chrName_1BasedCoordinateInLatestAssemly_rsID_TF.substring(indexofFirstUnderScore+1,indexofSecondUnderScore));
				
				key_chrName_1BasedCoordinateInLatestAssembly = new String(chrName + "\t" + _1BasedCoordinateInLatestAssemly);
				
				//Do not write duplicates.
				//Fill keys only
				if (!_1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map.containsKey(key_chrName_1BasedCoordinateInLatestAssembly)){
					_1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map.put(key_chrName_1BasedCoordinateInLatestAssembly,null);
					
					bufferedWriter.write(chrName + "\t" + _1BasedCoordinateInLatestAssemly + System.getProperty("line.separator"));
					
				}//End of IF
				
				
			}//End of WHILE
			
			//Close 
			bufferedReader.close();
			bufferedWriter.close();
			
			
			//Downlift from latest assembly  to GRCh37.p13
			//Fill _1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map (with values)
			//_1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map does not contain any duplicates
			convert_FromLatestAssembly_ToGRCh37p13(
					dataFolder,
					RSAFolder,
					PostAnalysis_RSA_chrName_1BasedCoordinateInLatestAssembly_FileName,
					PostAnalysis_RSA_chrName_1BasedCoordinateGRCh37p13_FileName,
					true,
					_1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map);
			
		
			
			
			//_1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map is full
			//Rest of the maps are initialized. But they will be filled now.
			//Fill keys and values of  _1BasedCoordinatesInlatestAssembly_2_0BasedCoordinatesInGRCh37p13Map
			//Fill only keys of 0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap
			fillMaps(_1BasedCoordinatesInlatestAssembly_2_1BasedCoordinatesInGRCh37p13Map,
				_1BasedCoordinatesInlatestAssembly_2_0BasedCoordinatesInGRCh37p13Map,
				_0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap);
			
			//Write input file for GLANET annotation using keys in _0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap
			writeInputFile(
					RSAFolder,
					PostAnalysis_RSA_chrName_0BasedStart_0BasedEnd_CoordinatesınGRCh37p13_FileName,
					_0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap);
			
			//Annotate with genes
			//Augment RSAPostAnalysisFileName with gene annotations	and write RSAPostAnalysisAugmentedWithGeneAnnotationsFileName
			annotateInputFileWithRefSeqGenes(
					dataFolder,
					RSAFolder,
					PostAnalysis_RSA_chrName_0BasedStart_0BasedEnd_CoordinatesınGRCh37p13_FileName,
					RSAPostAnalysisFileName,
					RSAPostAnalysisAugmentedWithGeneAnnotationsFileName,
					_1BasedCoordinatesInlatestAssembly_2_0BasedCoordinatesInGRCh37p13Map,
					_0BasedCoordinatesInGRCh37p13Map_2_GeneAnnotationsMap,
					writeFoundOverlapsMode);			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void main(String[] args) {
		
//		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
//
//		// jobName starts
//		String jobName = args[CommandLineArguments.JobName.value()].trim();
//		if( jobName.isEmpty()){
//			jobName = Commons.NO_NAME;
//		}
//		// jobName ends
//
//		//String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
//		String outputFolder = args[CommandLineArguments.OutputFolder.value()];
		
//		WriteAnnotationFoundOverlapsMode writeFoundOverlapsMode = WriteAnnotationFoundOverlapsMode.convertStringtoEnum( args[CommandLineArguments.WriteAnnotationFoundOverlapsMode.value()]);

		
		//For Debug delete later
		String dataFolder = "C:\\Users\\Burçak\\Google Drive\\Data\\";
		String outputFolder = "C:\\Users\\Burçak\\Google Drive\\Output\\rsa_lgmd\\";
		String RSAFolder = outputFolder + "RegulatorySequenceAnalysis" + System.getProperty("file.separator");		
		WriteAnnotationFoundOverlapsMode writeFoundOverlapsMode = WriteAnnotationFoundOverlapsMode.DO_NOT_WRITE_OVERLAPS_AT_ALL;
		//For Debug delete later

		
		//Read Post Analysis Data
		//Pay attention that post analysis data is in latest assembly return by NCBI eutils
		//Down lift to GRCh37.p13
		//Annotate with Genes since GLANET uses _0based GRCh37.p13 coordinates
		//Write the Gene Annotated Post Analysis of RSA Results in latest assembly return by NCBI eutils
		readPostAnalysisRSAResults(
				dataFolder,
				outputFolder,
				RSAFolder, 
				Commons.RSAPostAnalysisFileName,
				Commons.RSAPostAnalysisAugmentedWithGeneAnnotationsFileName,
				writeFoundOverlapsMode);
		

	}

}
