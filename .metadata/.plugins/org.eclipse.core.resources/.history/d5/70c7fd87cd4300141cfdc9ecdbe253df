/**
 * @author burcakotlu
 * @date Aug 30, 2014 
 * @time 4:10:57 PM
 */
package annotate.intervals.parametric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;

import common.Commons;

/**
 * 
 */
public class AnnotationBinaryMatrixGeneration {
	
	public static void readGivenIntervals(Map<String,Short> givenIntervalName2ArrayXIndexMap, String[] givenIntervalNames,String outputFolder){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine;
		
		int indexOfFirstTab;
		int indexOfSecondTab;
			
		String chrNumber;
		int start;
		int end;
				
		String givenIntervalKey = null;
		short givenIntervalIndex = 0;
		
		try {
			fileReader = FileOperations.createFileReader(outputFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE);
			bufferedReader = new BufferedReader(fileReader);
			
			while ((strLine = bufferedReader.readLine())!=null){
//				chr1	11862777	11862777
				indexOfFirstTab = strLine.indexOf('\t');
				indexOfSecondTab = strLine.indexOf('\t',indexOfFirstTab+1);
					
				chrNumber = strLine.substring(0,indexOfFirstTab);
				start = Integer.parseInt(strLine.substring(indexOfFirstTab+1, indexOfSecondTab));
				end = Integer.parseInt(strLine.substring(indexOfSecondTab+1));
				
				givenIntervalKey = chrNumber + "_"  + start + "_" + end;
				givenIntervalName2ArrayXIndexMap.put(givenIntervalKey, givenIntervalIndex);
				givenIntervalNames[givenIntervalIndex] = givenIntervalKey;
				givenIntervalIndex++;
			}//end of while
			
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
	}
	
	public static  void readAnnotations(Map<String,Short>  givenIntervalName2ArrayXIndexMap,List<String> elementNameList,String[] elementNames,short[][] annotationBinaryMatrix, String outputFolder, String elementType){
	
		String folderName = null;
		File folder;
		String fileName;
		String fileAbsolutePath;
		
		int indexofDot;
		String elementName;
		short elementIndex = 0;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine; 
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		String givenIntervalKey = null;
		
		String givenIntervalChrNumber;
		int givenIntervalStart;
		int givenIntervalEnd;
		
		short givenIntervalIndex;
		
		if (elementType.equals(Commons.DNASE)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_DNASE;
		}else if (elementType.equals(Commons.HISTONE)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_HISTONE;
		} else if (elementType.equals(Commons.TF)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TFBS;
		}
		
		else if (elementType.equals(Commons.EXON_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS;
		}else if (elementType.equals(Commons.REGULATION_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS;
		}else if (elementType.equals(Commons.ALL_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS;
		}
		
		
		else if (elementType.equals(Commons.TF_EXON_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_EXON_BASED_KEGG_PATHWAY;
		}else if (elementType.equals(Commons.TF_REGULATION_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_REGULATION_BASED_KEGG_PATHWAY;
		}else if (elementType.equals(Commons.TF_ALL_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_ALL_BASED_KEGG_PATHWAY;
		}
		
		else if (elementType.equals(Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY;
		}else if (elementType.equals(Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY;
		}else if (elementType.equals(Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY;
		}
		
		folder = new File(folderName);
		
		if (folder.exists() && folder.isDirectory()){
			File[] files = folder.listFiles();
			
			   for(File file: files){
				   if(file.isFile()){
						fileName = file.getName();
						indexofDot = fileName.indexOf('.');
						elementName = fileName.substring(0,indexofDot);
						
						elementNameList.add(elementName);
						elementNames[elementIndex] = elementName;
									
         				fileAbsolutePath = file.getAbsolutePath();
         				
         				try {
							fileReader = FileOperations.createFileReader(fileAbsolutePath);
							bufferedReader = new BufferedReader(fileReader);
							
							//read annotations
							while((strLine = bufferedReader.readLine())!=null){
								if(!strLine.contains("Search")){
//									Searched for chr	given interval low	given interval high	dnase overlap chrom name	node low	node high	node CellLineName	node FileName
//									chr1	109817589	109817589	chr1	109817060	109817913	MCF7	MCF7DukeDNaseSeq.pk
									indexofFirstTab = strLine.indexOf('\t');
									indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
									indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
									
									givenIntervalChrNumber = strLine.substring(0,indexofFirstTab);
									givenIntervalStart = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
									givenIntervalEnd =  Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
									
									givenIntervalKey = givenIntervalChrNumber + "_" + givenIntervalStart + "_" + givenIntervalEnd;
									
									givenIntervalIndex = givenIntervalName2ArrayXIndexMap.get(givenIntervalKey);
													
									annotationBinaryMatrix[givenIntervalIndex][elementIndex] = 1;
								}//End of IF
							}//End of WHILE
							
							bufferedReader.close();
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}   
         					
         				elementIndex++;
				   }
				   
			   } 
				   
		}
		
	}
	
	public static void writeAnnotationBinaryMatrix(short[][] annotationBinaryMatrix,String[] givenIntervalNames,int numberofGivenIntervals,String[] elementNames,int numberofElements, String outputFolder, String elementType){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String outputFileName = null;

		if (elementType.equals(Commons.DNASE)){
			outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_DNASE;
		}else if (elementType.equals(Commons.HISTONE)){
			outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_HISTONE;
		}else if (elementType.equals(Commons.TF)){
			outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_TF;
		}
		
		 else if (elementType.equals(Commons.EXON_BASED_KEGG_PATHWAY)){
				outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_EXONBASEDKEGG;
		}else if (elementType.equals(Commons.REGULATION_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_REGULATIONBASEDKEGG;
		}else if (elementType.equals(Commons.ALL_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_ALLBASEDKEGG;
		}
		
		
		 else if (elementType.equals(Commons.TF_EXON_BASED_KEGG_PATHWAY)){
				outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_TFEXONBASEDKEGG;
		}else if (elementType.equals(Commons.TF_REGULATION_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_TFREGULATIONBASEDKEGG;
		}else if (elementType.equals(Commons.TF_ALL_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_TFALLBASEDKEGG;
		}
		
		 else if (elementType.equals(Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY)){
				outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_TFCELLLINEEXONBASEDKEGG;
		}else if (elementType.equals(Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_TFCELLLINEREGULATIONBASEDKEGG;
		}else if (elementType.equals(Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ANNOTATIONBINARYMATRIX_TFCELLLINEALLBASEDKEGG;
		}
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//Write header file starts
			bufferedWriter.write("AnnotationMatrix" + "\t");
			for(int i = 0; i<numberofElements; i++){
				bufferedWriter.write(elementNames[i]+ "\t");
			}
			bufferedWriter.write(System.getProperty("line.separator"));
			//Write header file ends
		
			//Write givenIntervalName and 1s and 0s starts
			for(int i= 0; i<numberofGivenIntervals; i++){
				
				bufferedWriter.write(givenIntervalNames[i]+ "\t");
				
				for (int j= 0; j<numberofElements; j++){
					bufferedWriter.write(annotationBinaryMatrix[i][j] + "\t");
				}
				
				bufferedWriter.write(System.getProperty("line.separator"));
				
			}
			//Write givenIntervalName and 1s and 0s ends

						
				
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;
	
		
		/************************************************************************************/
		/********************************HASH MAPS starts************************************/
		/************************************************************************************/		
		//Given Intervals
		Map<String,Short> givenIntervalName2ArrayXIndexMap 		= new HashMap<String,Short>();
		
		//Dnase
		//Histone
		//TF
		List<String> dnaseCellLineNameList 			= new ArrayList<String>();
		List<String> histoneNameCellLineNameList 	= new ArrayList<String>();
		List<String> tfNameCellLineNameList 			= new ArrayList<String>();
		
		//EXON
		//REGULATION
		//ALL
		List<String> exonBasedKEGGPathwayList 		= new ArrayList<String>();
		List<String> regulationBasedKEGGPathwayList 	= new ArrayList<String>();
		List<String> allBasedKEGGPathwayList 		= new ArrayList<String>();
	
		//TF EXON
		//TF REGULATION
		//TF ALL		
		List<String> tfExonBasedKEGGPathwayList			= new ArrayList<String>();
		List<String> tfRegulationBasedKEGGPathwayList 	= new ArrayList<String>();
		List<String> tfAllBasedKEGGPathwayList 			= new ArrayList<String>();
	
		//TF CELLLINE EXON
		//TF CELLLINE REGULATION
		//TF CELLLINE ALL		
		List<String> tfCellLineExonBasedKEGGPathwayList 		= new ArrayList<String>();
		List<String> tfCellLineRegulationBasedKEGGPathwayList 	= new ArrayList<String>();
		List<String> tfCellLineAllBasedKEGGPathwayList 			= new ArrayList<String>();
		/************************************************************************************/
		/********************************HASH MAPS ends**************************************/
		/************************************************************************************/	
		
		
		/************************************************************************************/
		/********************************INDEX TO NAME ARRAYS starts*************************/
		/************************************************************************************/			
		String[] givenIntervalNames = new String[1000];
		
		String[] dnaseCelllineNames 		= new String[10000];
		String[] tfNameCellLineNames		= new String[10000];
		String[] histoneNameCellLineNames	= new String[10000];
		
		String[] exonBasedKEGGPathwayNames 		= new String[10000];
		String[] regulationBasedKEGGPathwayNames= new String[10000];
		String[] allBasedKEGGPathwayNames 		= new String[10000];
		
		String[] tfExonBasedKEGGPathwayNames	 	= new String[10000];
		String[] tfRegulationBasedKEGGPathwayNames 	= new String[10000];
		String[] tfAllBasedKEGGPathwayNames 		= new String[10000];
		
		String[] tfCellLineExonBasedKEGGPathwayNames 		= new String[10000];
		String[] tfCellLineRegulationBasedKEGGPathwayNames 	= new String[10000];
		String[] tfCellLineAllBasedKEGGPathwayNames 		= new String[10000];
		/************************************************************************************/
		/********************************INDEX TO NAME ARRAYS ends***************************/
		/************************************************************************************/			

	
		/************************************************************************************/
		/***************************ANNOTATION BINARY MATRICES starts************************/
		/************************************************************************************/	
		//My assumptions
		//number of given intervals at most 1000
		//number of elements at most 10000
		
		short[][] annotationBinaryMatrixforDnase 	= new short[1000][10000];
		short[][] annotationBinaryMatrixforHistone 	= new short[1000][10000];
		short[][] annotationBinaryMatrixforTf 		= new short[1000][10000];

		short[][] annotationBinaryMatrixforExonBasedKEGG 		= new short[1000][10000];
		short[][] annotationBinaryMatrixforRegulationBasedKEGG 	= new short[1000][10000];
		short[][] annotationBinaryMatrixforAllBasedKEGG 		= new short[1000][10000];

		short[][] annotationBinaryMatrixforTFExonBasedKEGG 			= new short[1000][10000];
		short[][] annotationBinaryMatrixforTFRegulationBasedKEGG 	= new short[1000][10000];
		short[][] annotationBinaryMatrixforTFAllBasedKEGG 			= new short[1000][10000];

		short[][] annotationBinaryMatrixforTFCellLineExonBasedKEGG 			= new short[1000][10000];
		short[][] annotationBinaryMatrixforTFCellLineRegulationBasedKEGG 	= new short[1000][10000];
		short[][] annotationBinaryMatrixforTFCellLineAllBasedKEGG 			= new short[1000][10000];	
		/************************************************************************************/
		/***************************ANNOTATION BINARY MATRICES ends**************************/
		/************************************************************************************/		
	
		
		/***********************************************************************************************/
		/***************************READ GIVEN INPUT starts*********************************************/
		/***********************************************************************************************/		
		readGivenIntervals(givenIntervalName2ArrayXIndexMap,givenIntervalNames,outputFolder);
		/***********************************************************************************************/
		/***************************READ GIVEN INPUT ends***********************************************/
		/***********************************************************************************************/		
	
		
		
		/***********************************************************************************************/
		/***************************READ ANNOTATIONs FILL BINARY MATRICES starts************************/
		/***********************************************************************************************/		
		readAnnotations(givenIntervalName2ArrayXIndexMap,dnaseCellLineNameList,dnaseCelllineNames,annotationBinaryMatrixforDnase,outputFolder,Commons.DNASE);
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfNameCellLineNameList,tfNameCellLineNames,annotationBinaryMatrixforTf,outputFolder,Commons.TF);
		readAnnotations(givenIntervalName2ArrayXIndexMap,histoneNameCellLineNameList,histoneNameCellLineNames,annotationBinaryMatrixforHistone,outputFolder,Commons.HISTONE);
		
		readAnnotations(givenIntervalName2ArrayXIndexMap,exonBasedKEGGPathwayList,exonBasedKEGGPathwayNames,annotationBinaryMatrixforExonBasedKEGG,outputFolder,Commons.EXON_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,regulationBasedKEGGPathwayList,regulationBasedKEGGPathwayNames,annotationBinaryMatrixforRegulationBasedKEGG,outputFolder,Commons.REGULATION_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,allBasedKEGGPathwayList,allBasedKEGGPathwayNames,annotationBinaryMatrixforAllBasedKEGG,outputFolder,Commons.ALL_BASED_KEGG_PATHWAY);
		
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfExonBasedKEGGPathwayList,tfExonBasedKEGGPathwayNames,annotationBinaryMatrixforTFExonBasedKEGG,outputFolder,Commons.TF_EXON_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfRegulationBasedKEGGPathwayList,tfRegulationBasedKEGGPathwayNames,annotationBinaryMatrixforTFRegulationBasedKEGG,outputFolder,Commons.TF_REGULATION_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfAllBasedKEGGPathwayList,tfAllBasedKEGGPathwayNames,annotationBinaryMatrixforTFAllBasedKEGG,outputFolder,Commons.TF_ALL_BASED_KEGG_PATHWAY);

		readAnnotations(givenIntervalName2ArrayXIndexMap,tfCellLineExonBasedKEGGPathwayList,tfCellLineExonBasedKEGGPathwayNames,annotationBinaryMatrixforTFCellLineExonBasedKEGG,outputFolder,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfCellLineRegulationBasedKEGGPathwayList,tfCellLineRegulationBasedKEGGPathwayNames,annotationBinaryMatrixforTFCellLineRegulationBasedKEGG,outputFolder,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfCellLineAllBasedKEGGPathwayList,tfCellLineAllBasedKEGGPathwayNames,annotationBinaryMatrixforTFCellLineAllBasedKEGG,outputFolder,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
		/***********************************************************************************************/
		/***************************READ ANNOTATIONs FILL BINARY MATRICES ends**************************/
		/***********************************************************************************************/
		
		
		/***********************************************************************************************/
		/***************************WRITE ANNOTATION BINARY MATRICES starts*****************************/
		/***********************************************************************************************/	
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforDnase,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),dnaseCelllineNames,dnaseCellLineNameList.size(),outputFolder,Commons.DNASE);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTf,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfNameCellLineNames,tfNameCellLineNameList.size(),outputFolder,Commons.TF);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforHistone,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),histoneNameCellLineNames,histoneNameCellLineNameList.size(),outputFolder,Commons.HISTONE);
		
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforExonBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),exonBasedKEGGPathwayNames,exonBasedKEGGPathwayList.size(),outputFolder,Commons.EXON_BASED_KEGG_PATHWAY);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforRegulationBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),regulationBasedKEGGPathwayNames,regulationBasedKEGGPathwayList.size(),outputFolder,Commons.REGULATION_BASED_KEGG_PATHWAY);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforAllBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),allBasedKEGGPathwayNames,allBasedKEGGPathwayList.size(),outputFolder,Commons.ALL_BASED_KEGG_PATHWAY);
		
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFExonBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfExonBasedKEGGPathwayNames,tfExonBasedKEGGPathwayList.size(),outputFolder,Commons.TF_EXON_BASED_KEGG_PATHWAY);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFRegulationBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfRegulationBasedKEGGPathwayNames,tfRegulationBasedKEGGPathwayList.size(),outputFolder,Commons.TF_REGULATION_BASED_KEGG_PATHWAY);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFAllBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfAllBasedKEGGPathwayNames,tfAllBasedKEGGPathwayList.size(),outputFolder,Commons.TF_ALL_BASED_KEGG_PATHWAY);
		
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFCellLineExonBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfCellLineExonBasedKEGGPathwayNames,tfCellLineExonBasedKEGGPathwayList.size(),outputFolder,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFCellLineRegulationBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfCellLineRegulationBasedKEGGPathwayNames,tfCellLineRegulationBasedKEGGPathwayList.size(),outputFolder,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFCellLineAllBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfCellLineAllBasedKEGGPathwayNames,tfCellLineAllBasedKEGGPathwayList.size(),outputFolder,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);		
		/***********************************************************************************************/
		/***************************WRITE ANNOTATION BINARY MATRICES ends*******************************/
		/***********************************************************************************************/		

	

	}

}
