/**
 * @author burcakotlu
 * @date Aug 25, 2014 
 * @time 3:06:09 PM
 */
package augmentation.results;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.EnrichmentType;

/**
 * 
 */
public class CreationofAugmentedElementsFilesinGRCh38CoordinatesUsingRemapOutputFiles {
	
	
	public static void 	readTwoFilesandWriteOneFileVersion2(String outputFolder, String fileWithCoordinatesiInGRCh37, String remapOutputFilePath, String fileWithCoordinatesiInGRCh38){
		
		FileReader fileReader1 = null;
		BufferedReader bufferedReader1 = null;
		
		FileReader fileReader2 = null;
		BufferedReader bufferedReader2 = null;
			
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine1;
		String firstLine;
		String secondLine;
		String thirdLine;
		
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
		
		
		
		String before;
		String after;
		
		String tfNameCellLineName;
		String refseqGeneName;
					
		File remapOutputFile;
		
		try {
			fileReader1 = new FileReader(outputFolder + fileWithCoordinatesiInGRCh37 );
			bufferedReader1 = new BufferedReader(fileReader1);
			
			fileWriter = FileOperations.createFileWriter(outputFolder + fileWithCoordinatesiInGRCh38);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			remapOutputFile = new File(outputFolder + remapOutputFilePath);
			
			if (remapOutputFile.exists()){
				fileReader2 = new FileReader(outputFolder + remapOutputFilePath);
				bufferedReader2 = new BufferedReader(fileReader2);
				
				
				while((strLine1 = bufferedReader1.readLine())!=null){
					if(!strLine1.startsWith("*") && !strLine1.contains("Search")){
						
//						**************	hsa04141	Protein processing in endoplasmic reticulum - Homo sapiens (human)	**************											
//						CEBPB_K562_hsa04141	Search for chr	given interval low	given interval high	tfbs	tfbs low	tfbs high	refseq gene name	ucscRefSeqGene low	ucscRefSeqGene high	interval name 	hugo suymbol	entrez id	keggPathwayName	Protein processing in endoplasmic reticulum - Homo sapiens (human)
//						CEBPB_K562_hsa04141	chr10	75421207	75421208	CEBPB_K562	75421133	75421325	NM_004922	75404130	75494130	FIVE_D	SEC24C	9632	hsa04141	Protein processing in endoplasmic reticulum - Homo sapiens (human)
//						CEBPB_K562_hsa04141	chr10	75421207	75421208	CEBPB_K562	75421133	75421325	NM_198597	75404130	75494130	FIVE_D	SEC24C	9632	hsa04141	Protein processing in endoplasmic reticulum - Homo sapiens (human)

						
						indexofFirstTab 	= strLine1.indexOf('\t');
						indexofSecondTab 	= strLine1.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab 	= strLine1.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab 	= strLine1.indexOf('\t',indexofThirdTab+1);
						indexofFifthTab 	= strLine1.indexOf('\t',indexofFourthTab+1);
						indexofSixthTab 	= strLine1.indexOf('\t',indexofFifthTab+1);
						indexofSeventhTab 	= strLine1.indexOf('\t',indexofSixthTab+1);
						indexofEigthTab 	= strLine1.indexOf('\t',indexofSeventhTab+1);
						indexofNinethTab 	= strLine1.indexOf('\t',indexofEigthTab+1);
						indexofTenthTab 	= strLine1.indexOf('\t',indexofNinethTab+1);
						
						
					
						before = strLine1.substring(0, indexofFirstTab);
							
						//Read line from second input file
						firstLine = bufferedReader2.readLine();
						
						tfNameCellLineName = strLine1.substring(indexofFourthTab+1, indexofFifthTab);
						
						//Read line from second input file
						secondLine = bufferedReader2.readLine();
						
						refseqGeneName = strLine1.substring(indexofSeventhTab+1, indexofEigthTab);
						
						//Read line from second input file
						thirdLine = bufferedReader2.readLine();
						
							
						after = strLine1.substring(indexofTenthTab+1);
						
						if(!firstLine.equals("NULL\t\t") && !secondLine.equals("NULL\t\t")){
							bufferedWriter.write(before + "\t");
							bufferedWriter.write(firstLine + "\t");
							bufferedWriter.write(tfNameCellLineName + "\t");
							bufferedWriter.write(secondLine.substring(secondLine.indexOf('\t')+1) + "\t");
							bufferedWriter.write(refseqGeneName + "\t");
							bufferedWriter.write(thirdLine.substring(thirdLine.indexOf('\t')+1) + "\t");
							bufferedWriter.write(after + System.getProperty("line.separator"));							
						}
						
						
								
						
					}//End of IF
					else{
						bufferedWriter.write(strLine1 + System.getProperty("line.separator"));
					}
				}//End of WHILE
				
				bufferedReader2.close();
				
			}//End of IF
			
			
			
			bufferedReader1.close();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void 	readTwoFilesandWriteOneFile(String outputFolder, String fileWithCoordinatesiInGRCh37, String remapOutputFilePath, String fileWithCoordinatesiInGRCh38){
		
		FileReader fileReader1 = null;
		BufferedReader bufferedReader1 = null;
		
		FileReader fileReader2 = null;
		BufferedReader bufferedReader2 = null;
			
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine1;
		String firstLine;
		String secondLine;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
		
		String before;
		String after;
					
		File remapOutputFile;
		
		try {
			fileReader1 = new FileReader(outputFolder + fileWithCoordinatesiInGRCh37 );
			bufferedReader1 = new BufferedReader(fileReader1);
			
			fileWriter = FileOperations.createFileWriter(outputFolder + fileWithCoordinatesiInGRCh38);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			remapOutputFile = new File(outputFolder + remapOutputFilePath);
			
			if (remapOutputFile.exists()){
				fileReader2 = new FileReader(outputFolder + remapOutputFilePath );
				bufferedReader2 = new BufferedReader(fileReader2);
					
			
				while((strLine1 = bufferedReader1.readLine())!=null){
					if(!strLine1.startsWith("*") && !strLine1.contains("Search")){
						
	//					**************	H3K4ME2_HELAS3	**************							
	//					H3K4ME2_HELAS3	Searched for chr	interval low	interval high	histone node chrom name	node Low	node high	node HistoneName	node CellLineName	node FileName
	//					H3K4ME2_HELAS3	chr9	97713458	97713459	chr9	97712055	97714787	H3K4ME2	HELAS3	wgEncodeBroadHistoneHelas3H3k4me2StdAln.narrowPeak
	
						
						indexofFirstTab 	= strLine1.indexOf('\t');
						indexofSecondTab 	= strLine1.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab 	= strLine1.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab 	= strLine1.indexOf('\t',indexofThirdTab+1);
						indexofFifthTab 	= strLine1.indexOf('\t',indexofFourthTab+1);
						indexofSixthTab 	= strLine1.indexOf('\t',indexofFifthTab+1);
						indexofSeventhTab 	= strLine1.indexOf('\t',indexofSixthTab+1);
						
						before = strLine1.substring(0, indexofFirstTab);
							
						//Read line from second input file
						firstLine = bufferedReader2.readLine();
						
						//Read line from second input file
						secondLine = bufferedReader2.readLine();						
							
						after = strLine1.substring(indexofSeventhTab+1);
						
						if(!firstLine.equals("NULL\t\t") && !secondLine.equals("NULL\t\t")){
							bufferedWriter.write(before + "\t");
							bufferedWriter.write(firstLine + "\t");
							bufferedWriter.write(secondLine + "\t");
							bufferedWriter.write(after + System.getProperty("line.separator"));						
						}
								
						
					}//End of IF
					else{
						bufferedWriter.write(strLine1 + System.getProperty("line.separator"));
					}
				}//End of WHILE
			
				bufferedReader2.close();
			
			}//End of IF
			bufferedReader1.close();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void readTwoFilesandGenerateANewFile(String outputFolder,EnrichmentType dnaseEnrichment,EnrichmentType histoneEnrichment,EnrichmentType tfEnrichment,EnrichmentType keggPathwayEnrichment,EnrichmentType tfKeggPathwayEnrichment,EnrichmentType tfCellLineKeggPathwayEnrichment){
		if (dnaseEnrichment.isDnaseEnrichment()){
			readTwoFilesandWriteOneFile(outputFolder, Commons.AUGMENTED_DNASE_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_DNASE_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES,Commons.AUGMENTED_DNASE_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);	
		 }
		 
		 if (histoneEnrichment.isHistoneEnrichment()){
			 readTwoFilesandWriteOneFile(outputFolder, Commons.AUGMENTED_HISTONE_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_HISTONE_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES, Commons.AUGMENTED_HISTONE_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);
		 }
		 
		 if (tfEnrichment.isTfEnrichment()){
			 readTwoFilesandWriteOneFile(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_TF_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES,Commons.AUGMENTED_TF_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);	
		 }
		 
		 if (keggPathwayEnrichment.isKeggPathwayEnrichment()){
			 readTwoFilesandWriteOneFile(outputFolder, Commons.AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES,Commons.AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);	
			 readTwoFilesandWriteOneFile(outputFolder, Commons.AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES,Commons.AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);	
			 readTwoFilesandWriteOneFile(outputFolder, Commons.AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES,Commons.AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);	
		}
		
	     if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()){	   
			 readTwoFilesandWriteOneFileVersion2(outputFolder, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES,Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);	
			 readTwoFilesandWriteOneFileVersion2(outputFolder, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES,Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);	
			 readTwoFilesandWriteOneFileVersion2(outputFolder, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES,Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);	
	     }
		
	     if (tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
	    	 readTwoFilesandWriteOneFileVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES,Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);	
	    	 readTwoFilesandWriteOneFileVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES,Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);	
			 readTwoFilesandWriteOneFileVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_REMAP_OUTPUTFILE_AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES,Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASEDSTART_1BASEDEND_GRCH38_COORDINATES);	

	     }
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;
		
		EnrichmentType dnaseEnrichment 		= EnrichmentType.convertStringtoEnum(args[10]);
		EnrichmentType histoneEnrichment  	= EnrichmentType.convertStringtoEnum(args[11]);
		EnrichmentType tfEnrichment 		= EnrichmentType.convertStringtoEnum(args[12]);
		EnrichmentType keggPathwayEnrichment  			= EnrichmentType.convertStringtoEnum(args[13]);
		EnrichmentType tfKeggPathwayEnrichment 			= EnrichmentType.convertStringtoEnum(args[14]);
		EnrichmentType tfCellLineKeggPathwayEnrichment 	= EnrichmentType.convertStringtoEnum(args[15]);

			
		CreationofAugmentedElementsFilesinGRCh38CoordinatesUsingRemapOutputFiles.readTwoFilesandGenerateANewFile(outputFolder,dnaseEnrichment,histoneEnrichment,tfEnrichment,keggPathwayEnrichment,tfKeggPathwayEnrichment,tfCellLineKeggPathwayEnrichment);

	}

}
