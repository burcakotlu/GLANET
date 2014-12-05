/**
 * @author burcakotlu
 * @date Aug 25, 2014 
 * @time 11:00:56 AM
 */
package augmentation.results;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import remap.Remap;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.Assembly;
import enumtypes.ChromosomeName;
import enumtypes.EnrichmentType;

/****************************************************
 * 													*
 * 													*
 * Creation of REMAP Input Files					*
 * with 0 based 									*
 * Start											*
 * End Exclusive									*
 * GRCh37.p13										*
 * coordinates										*
 * 													*
 * Call REMAP API									*
 * 													*
 * Creation of REMAP Output Files					*
 * with 1Based										*
 * Start											*
 * End												*
 * GRCh38											*
 * coordinates										*
 ****************************************************/
public class AugmentationofEnrichmentInLatestAssemblyUsingNCBIREMAP {
	
	
	public static void convertUsingMap(
		String outputFolder, 
		String augmentedFileInGRCh37p13,
		String augmentedFileInGRCh38,
		Map<String,String> conversionMap){
		
		//augmentedFileInGRCh37p13
		FileReader fileReader= null;
		BufferedReader bufferedReader= null;
		
		//augmentedFileInGRCh38
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
		String before;
		String after;
		
		String toBeRemapped1;
		String toBeRemapped2;
		
		String mapped1;
		String mapped2;
		
		try {
			
			File file = new File(outputFolder + augmentedFileInGRCh37p13);
			
			if (file.exists()){
				
				fileReader = FileOperations.createFileReader(outputFolder + augmentedFileInGRCh37p13);
				bufferedReader = new BufferedReader(fileReader);
				
				fileWriter = FileOperations.createFileWriter(outputFolder + augmentedFileInGRCh38);
				bufferedWriter = new BufferedWriter(fileWriter);
				
				while((strLine = bufferedReader.readLine())!=null){
					if(!strLine.startsWith("*") && !strLine.contains("Search")){
						
						//**************	H3K4ME2_HELAS3	**************							
						//H3K4ME2_HELAS3	Searched for chr	interval low	interval high	histone node chrom name	node Low	node high	node HistoneName	node CellLineName	node FileName
						//H3K4ME2_HELAS3	chr9	97713458	97713459	chr9	97712055	97714787	H3K4ME2	HELAS3	wgEncodeBroadHistoneHelas3H3k4me2StdAln.narrowPeak

						indexofFirstTab 	= strLine.indexOf('\t');
						indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab 	= strLine.indexOf('\t',indexofThirdTab+1);
						indexofFifthTab 	= strLine.indexOf('\t',indexofFourthTab+1);
						indexofSixthTab 	= strLine.indexOf('\t',indexofFifthTab+1);
						indexofSeventhTab 	= strLine.indexOf('\t',indexofSixthTab+1);
						
						before = strLine.substring(0, indexofFirstTab);
						
						toBeRemapped1 = strLine.substring(indexofFirstTab+1, indexofFourthTab);;
						toBeRemapped2 = strLine.substring(indexofFourthTab+1, indexofSeventhTab);;
							
						after = strLine.substring(indexofSeventhTab+1);
						
						mapped1 = conversionMap.get(toBeRemapped1);
						mapped2 = conversionMap.get(toBeRemapped2);
						
						if(mapped1!=null && mapped2!=null){
							bufferedWriter.write(before + "\t");
							bufferedWriter.write(mapped1 + "\t");
							bufferedWriter.write(mapped2 + "\t");
							bufferedWriter.write(after + System.getProperty("line.separator"));						
						}else{
							System.out.println("Pleae notice that there is an unconverted genomic loci");
						}
								
						
					}//End of IF
					else{
						bufferedWriter.write(strLine + System.getProperty("line.separator"));
					}
				}//End of WHILE
				
				//CLOSE	
				bufferedReader.close();
				bufferedWriter.close();

				
			}//End of IF remapOutputFileUsingReport exists
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void convertUsingMapVersion2(
			String outputFolder, 
			String augmentedFileInGRCh37p13,
			String augmentedFileInGRCh38,
			Map<String,String> conversionMap){
			
			//augmentedFileInGRCh37p13
			FileReader fileReader= null;
			BufferedReader bufferedReader= null;
			
			//augmentedFileInGRCh38
			FileWriter fileWriter = null;
			BufferedWriter bufferedWriter = null;
			
			String strLine;
			String chrName;
			String elementName;
			String geneName;
			
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
			
			int indexofFirstTabInMapped2;
			int indexofFirstTabInMapped3;
			
			String before;
			String after;
			
			String toBeRemapped1;
			String toBeRemapped2;
			String toBeRemapped3;
			
			String mapped1;
			String mapped2;
			String mapped3;
			
			try {
				
				File file = new File(outputFolder + augmentedFileInGRCh37p13);
				
				if (file.exists()){
					
					fileReader = FileOperations.createFileReader(outputFolder + augmentedFileInGRCh37p13);
					bufferedReader = new BufferedReader(fileReader);
					
					fileWriter = FileOperations.createFileWriter(outputFolder + augmentedFileInGRCh38);
					bufferedWriter = new BufferedWriter(fileWriter);
					
					while((strLine = bufferedReader.readLine())!=null){
						if(!strLine.startsWith("*") && !strLine.contains("Search")){
							
//							**************	hsa05320	Autoimmune thyroid disease - Homo sapiens (human)	**************
//							TBP_hsa05320	Search for chr	given interval low	given interval high	tfbs	tfbs low	tfbs high	refseq gene name	ucscRefSeqGene low	ucscRefSeqGene high	interval name 	hugo suymbol	entrez id	keggPathwayName	Autoimmune thyroid disease - Homo sapiens (human)
//							TBP_hsa05320	chr6	32784675	32784676	TBP_GM12878	32784648	32784958	NM_020056	32724664	32814664	THREE_D	HLA-DQA2	3118	hsa05320	Autoimmune thyroid disease - Homo sapiens (human)
//							TBP_hsa05320	chr6	32784675	32784676	TBP_GM12878	32784648	32784958	NM_002120	32784637	32784825	EXON	HLA-DOB	3112	hsa05320	Autoimmune thyroid disease - Homo sapiens (human)

							indexofFirstTab 	= strLine.indexOf('\t');
							indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
							indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
							indexofFourthTab 	= strLine.indexOf('\t',indexofThirdTab+1);
							indexofFifthTab 	= strLine.indexOf('\t',indexofFourthTab+1);
							indexofSixthTab 	= strLine.indexOf('\t',indexofFifthTab+1);
							indexofSeventhTab 	= strLine.indexOf('\t',indexofSixthTab+1);
							indexofEigthTab 	= strLine.indexOf('\t',indexofSeventhTab+1);
							indexofNinethTab 	= strLine.indexOf('\t',indexofEigthTab+1);
							indexofTenthTab 	= strLine.indexOf('\t',indexofNinethTab+1);
							
							before = strLine.substring(0, indexofFirstTab);
							
							chrName = strLine.substring(indexofFirstTab+1, indexofSecondTab);
							elementName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
							geneName = strLine.substring(indexofSeventhTab+1, indexofEigthTab);
							
							toBeRemapped1 = strLine.substring(indexofSecondTab+1, indexofFourthTab);;
							toBeRemapped2 = strLine.substring(indexofFifthTab+1, indexofSeventhTab);;
							toBeRemapped3 = strLine.substring(indexofEigthTab+1, indexofTenthTab);;
								
							after = strLine.substring(indexofTenthTab+1);
							
							mapped1 = conversionMap.get(chrName + "\t" + toBeRemapped1);
							mapped2 = conversionMap.get(chrName + "\t" + toBeRemapped2);
							mapped3 = conversionMap.get(chrName + "\t" + toBeRemapped3);
							
							indexofFirstTabInMapped2 = mapped2.indexOf('\t');
							indexofFirstTabInMapped3 = mapped3.indexOf('\t');
							
							
							if(mapped1!=null && mapped2!=null && mapped3!=null){
								bufferedWriter.write(before + "\t");
								bufferedWriter.write(mapped1 + "\t");
								bufferedWriter.write(elementName + "\t" + mapped2.substring(indexofFirstTabInMapped2+1) + "\t");
								bufferedWriter.write(geneName + "\t" + mapped3.substring(indexofFirstTabInMapped3+1) + "\t");
								bufferedWriter.write(after + System.getProperty("line.separator"));						
							}else{
								System.out.println("Pleae notice that there is an unconverted genomic loci");
							}
									
							
						}//End of IF
						else{
							bufferedWriter.write(strLine + System.getProperty("line.separator"));
						}
					}//End of WHILE
					
					//CLOSE	
					bufferedReader.close();
					bufferedWriter.close();

					
				}//End of IF remapOutputFileUsingReport exists
				
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	public static void fillConversionMap(
		String outputFolder,  
		String remapReportFile,
		Map<String,String> conversionMap){
		
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
	
		String strLine = null;
		
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
		int indexofSixteenthTab;
		int indexofSeventeenthTab;
		
	
		//source
		int sourceInt;	
		ChromosomeName sourceChrName;
		int sourceStart;
		int sourceEnd;
		
		//mapped
		String mappedIntString;
		int mappedInt;
		ChromosomeName mappedChrName;
		int mappedStart;
		int mappedEnd;
		Assembly mappedAssembly;
		
		//Read remapReportFile
		//Using remapReportFile first fill chrName_start_end_in_one_assembly 2 chrName_start_end_in_another_assembly Map
		
		File file = new File(outputFolder + remapReportFile);
		
		if (file.exists()){
			
			try {
				
				fileReader = FileOperations.createFileReader(outputFolder + remapReportFile);
			
				bufferedReader = new BufferedReader(fileReader);
							
				while((strLine = bufferedReader.readLine())!=null){
		
					//#feat_name	source_int	mapped_int	source_id	mapped_id	source_length	mapped_length	source_start	source_stop	source_strand	source_sub_start	source_sub_stop	mapped_start	mapped_stop	mapped_strand	coverage	recip	asm_unit
					//line_67	1	1	chr1	chr1	1	1	147664654	147664654	+	147664654	147664654	147136772	147136772	+	1	Second Pass	Primary Assembly
					//line_67	1	2	chr1	NW_003871055.3	1	1	147664654	147664654	+	147664654	147664654	4480067	4480067	+	1	First Pass	PATCHES
					//line_122	1	NULL	chr14	NULL	1	NULL	93095256	93095256	+	NOMAP	NOALIGN						
	
					
					if (!strLine.startsWith("#")){
						
						indexofFirstTab 	= strLine.indexOf('\t');
						indexofSecondTab 	= (indexofFirstTab>0) ? strLine.indexOf('\t', indexofFirstTab+1) :-1 ;
						indexofThirdTab 	= (indexofSecondTab>0) ? strLine.indexOf('\t', indexofSecondTab+1) :-1 ;
						indexofFourthTab 	= (indexofThirdTab>0) ? strLine.indexOf('\t', indexofThirdTab+1) :-1 ;
						indexofFifthTab 	= (indexofFourthTab>0) ? strLine.indexOf('\t', indexofFourthTab+1) :-1 ;
						indexofSixthTab 	= (indexofFifthTab>0) ? strLine.indexOf('\t', indexofFifthTab+1) :-1 ;
						indexofSeventhTab 	= (indexofSixthTab>0) ? strLine.indexOf('\t', indexofSixthTab+1) :-1 ;
						indexofEigthTab		= (indexofSeventhTab>0) ? strLine.indexOf('\t', indexofSeventhTab+1) :-1 ;
						indexofNinethTab 	= (indexofEigthTab>0) ? strLine.indexOf('\t', indexofEigthTab+1) :-1 ;
						indexofTenthTab 	= (indexofNinethTab>0) ? strLine.indexOf('\t', indexofNinethTab+1) :-1 ;
						indexofEleventhTab 	= (indexofTenthTab>0) ? strLine.indexOf('\t', indexofTenthTab+1) :-1 ;
						indexofTwelfthTab 	= (indexofEleventhTab>0) ? strLine.indexOf('\t', indexofEleventhTab+1) :-1 ;
						indexofThirteenthTab 	= (indexofTwelfthTab>0) ? strLine.indexOf('\t', indexofTwelfthTab+1) :-1 ;
						indexofFourteenthTab 	= (indexofThirteenthTab>0) ? strLine.indexOf('\t', indexofThirteenthTab+1) :-1 ;
						indexofFifteenthTab 	= (indexofFourteenthTab>0) ? strLine.indexOf('\t', indexofFourteenthTab+1) :-1 ;
						indexofSixteenthTab 	= (indexofFifteenthTab>0) ? strLine.indexOf('\t', indexofFifteenthTab+1) :-1 ;
						indexofSeventeenthTab 	= (indexofSixteenthTab>0) ? strLine.indexOf('\t', indexofSixteenthTab+1) :-1 ;
						
						sourceInt = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
						
						mappedIntString = strLine.substring(indexofSecondTab+1, indexofThirdTab);
						
						if (!mappedIntString.equals(Commons.NULL)){
							
							mappedInt = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
							
							sourceChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofThirdTab+1, indexofFourthTab));
							mappedChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofFourthTab+1, indexofFifthTab));
							
							sourceStart = Integer.parseInt(strLine.substring(indexofSeventhTab+1, indexofEigthTab));
							sourceEnd = Integer.parseInt(strLine.substring(indexofEigthTab+1, indexofNinethTab));
							
							mappedStart = Integer.parseInt(strLine.substring(indexofTwelfthTab+1, indexofThirteenthTab));
							mappedEnd = Integer.parseInt(strLine.substring(indexofThirteenthTab+1, indexofFourteenthTab));
							
							mappedAssembly =  Assembly.convertStringtoEnum(strLine.substring(indexofSeventeenthTab+1));
							
							if ((sourceInt == 1) && 
									(mappedInt == 1) &&
									sourceChrName == mappedChrName &&
									mappedAssembly.isPrimaryAssembly()){
																
								conversionMap.put(sourceChrName.convertEnumtoString() + "\t" + (sourceStart-1) + "\t" + sourceEnd, mappedChrName.convertEnumtoString() + "\t" + (mappedStart-1) + "\t" + mappedEnd);
								
							}//End of IF: Valid conversion
						}//End of IF mappedInt is not NULL
						
									
					}//End of IF: Not Header or Comment Line
					
				}//End of while
				
				//for debug purposes starts
				System.out.println("number of lines in conversionMap: " + conversionMap.size() + " for file: " + remapReportFile);
				//for debug purposes ends 
				
				
				//close 
				bufferedReader.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}//End of if remapReportFile exists

		
		
	}
	
	
	public static void readResultsandWriteVersion2(String outputFolder, String inputFileName, String outputFileName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine= null;
		
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
		
		String givenIntervalChrName;		
			
		List<String> toBeRemappedList = new ArrayList<String>();
		String toBeRemapped1 = null;
		String toBeRemapped2 = null;
		String toBeRemapped3 = null;
			
		try {
			fileReader = new FileReader(outputFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				if(!strLine.contains("Search") && !strLine.startsWith("*")){
									
					//**************	hsa05320	Autoimmune thyroid disease - Homo sapiens (human)	**************
					//TBP_hsa05320	Search for chr	given interval low	given interval high	tfbs	tfbs low	tfbs high	refseq gene name	ucscRefSeqGene low	ucscRefSeqGene high	interval name 	hugo suymbol	entrez id	keggPathwayName	Autoimmune thyroid disease - Homo sapiens (human)
					//TBP_hsa05320	chr6	32784675	32784676	TBP_GM12878	32784648	32784958	NM_020056	32724664	32814664	THREE_D	HLA-DQA2	3118	hsa05320	Autoimmune thyroid disease - Homo sapiens (human)
					//TBP_hsa05320	chr6	32784675	32784676	TBP_GM12878	32784648	32784958	NM_002120	32784637	32784825	EXON	HLA-DOB	3112	hsa05320	Autoimmune thyroid disease - Homo sapiens (human)
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
					indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
					indexofSeventhTab	= strLine.indexOf('\t', indexofSixthTab+1);
					indexofEigthTab		= strLine.indexOf('\t', indexofSeventhTab+1);
					indexofNinethTab	= strLine.indexOf('\t', indexofEigthTab+1);
					indexofTenthTab		= strLine.indexOf('\t', indexofNinethTab+1);
					
					givenIntervalChrName = strLine.substring(indexofFirstTab+1, indexofSecondTab);
					
					toBeRemapped1 = strLine.substring(indexofFirstTab+1, indexofFourthTab);
					toBeRemapped2 = givenIntervalChrName + "\t" + strLine.substring(indexofFifthTab+1, indexofSeventhTab);
					toBeRemapped3 = givenIntervalChrName + "\t" + strLine.substring(indexofEigthTab+1, indexofTenthTab);
					
					if (!toBeRemappedList.contains(toBeRemapped1)){
						toBeRemappedList.add(toBeRemapped1);
					}//End of if it is not contained in the toBeRemappedList
					
					
					if (!toBeRemappedList.contains(toBeRemapped2)){
						toBeRemappedList.add(toBeRemapped2);
					}//End of if it is not contained in the toBeRemappedList
					
					if (!toBeRemappedList.contains(toBeRemapped3)){
						toBeRemappedList.add(toBeRemapped3);
					}//End of if it is not contained in the toBeRemappedList
					
				}//End of if			
			}//End of while
			
			
			//for debug purposes starts
			System.out.println("number of lines in remap input file " + outputFileName + " is: " +toBeRemappedList.size());
			//for debug purposes ends
		
			
			//Write Lines to REMAP Input Files	
			for(String s:toBeRemappedList){
				bufferedWriter.write(s + System.getProperty("line.separator"));
			}//End of FOR
			
			//Close
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public static void readResultsandWrite(String outputFolder, String inputFileName, String outputFileName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine= null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
	
		List<String> toBeRemappedList = new ArrayList<String>();
		String toBeRemapped1 = null;
		String toBeRemapped2 = null;
		
		
		try {
			fileReader = new FileReader(outputFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				if(!strLine.contains("Search") && !strLine.startsWith("*")){
					
					//**************	POL2_HEPG2	**************
					//POL2_HEPG2	Searched for chr	interval Low	interval High	tfbs node Chrom Name	node Low	node High	node Tfbs Name	node CellLineName	node FileName
					//POL2_HEPG2	chr4	187188093	187188094	chr4	187187901	187188157	POL2	HEPG2	spp.optimal.wgEncodeHaibTfbsHepg2Pol2Pcr2xAlnRep0_VS_wgEncodeHaibTfbsHepg2ControlPcr2xAlnRep0.narrowPeak
					//POL2_HEPG2	chr4	187188093	187188094	chr4	187187924	187188140	POL2	HEPG2	spp.optimal.wgEncodeSydhTfbsHepg2Pol2ForsklnStdAlnRep0_VS_wgEncodeSydhTfbsHepg2InputForsklnStdAlnRep0.narrowPeak

					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
					indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
					indexofSeventhTab	= strLine.indexOf('\t', indexofSixthTab+1);
					
					toBeRemapped1 = strLine.substring(indexofFirstTab+1, indexofFourthTab);						
					toBeRemapped2 = strLine.substring(indexofFourthTab+1, indexofSeventhTab);
					
					
					if (!toBeRemappedList.contains(toBeRemapped1)){
						toBeRemappedList.add(toBeRemapped1);
					}//End of if it is not contained in the toBeRemappedList
					
					
					if (!toBeRemappedList.contains(toBeRemapped2)){
						toBeRemappedList.add(toBeRemapped2);
					}//End of if it is not contained in the toBeRemappedList
					
					
				}//End of if			
			}//End of while
			
			
			//for debug purposes starts
			System.out.println("number of lines in remap input file " + outputFileName + " is: " +toBeRemappedList.size());
			//for debug purposes ends
			
			
			//Write Lines to REMAP Input Files	
			for(String s:toBeRemappedList){
				bufferedWriter.write(s + System.getProperty("line.separator"));
			}//End of FOR
			
			//Close
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
		
	
	public static void callREMAPAndWriteAugmentationFilesInLatestAssembly(String outputFolder,
			String dataFolder,
			EnrichmentType tfEnrichment, 
			EnrichmentType tfKeggPathwayEnrichment, 
			EnrichmentType tfCellLineKeggPathwayEnrichment){
		
		
		//@todo check this
		//Remap.remap_show_batches(dataFolder,Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);
		//Map<String,String> assemblyName2RefSeqAssemblyIDMap = new HashMap<String,String>();
		//Remap.fillAssemblyName2RefSeqAssemblyIDMap(dataFolder,Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE,assemblyName2RefSeqAssemblyIDMap);
		
		String sourceReferenceAssemblyID = "GCF_000001405.25";
		//In fact targetReferenceAssemblyID must be the assemblyName that NCBI ETILS returns (groupLabel)
		String targetReferenceAssemblyID = "GCF_000001405.26";
		
		String merge = Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON;
		String allowMultipleLocation = Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON;
		double minimumRatioOfBasesThatMustBeRemapped = Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_;
		double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength  = Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2;
		
		Map<String,String> conversionMap = null;
		
		//ONLY TF ENRICHMENT CASE
		if (tfEnrichment.isTfEnrichment() && 
				 !tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() && 
				 !tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment() ){
			 
			 /**********************************************************/
	    	 /***********************TF starts**************************/
	    	 /**********************************************************/
			 Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
						outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
			  
			//TF
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMap(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  
	    	/**********************************************************/
	    	/***********************TF ends****************************/
	    	/**********************************************************/
	
			
		 }//End of IF: TF Enrichment
		 
		
		//ONLY TFKEGGPATHWAY ENRICHMENT CASE (DO NOT CARE TF ENRICHMENT)
	    if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() && 
	    		 !tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){	
	    	 
	    	 /**********************************************************/
	    	 /***********************TF starts**************************/
	    	 /**********************************************************/
	    	 Remap.remap(
	    			dataFolder,
					sourceReferenceAssemblyID, 
					targetReferenceAssemblyID, 
					outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
					outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
					outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
					merge,
					allowMultipleLocation,
					minimumRatioOfBasesThatMustBeRemapped,
					maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
	    	//TF
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMap(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  
			/**********************************************************/
			/***********************TF ends****************************/
			/**********************************************************/

	    	 /**********************************************************/
	    	 /************TF EXON BASED KEGG PATHWAY starts*************/
	    	 /**********************************************************/
			 Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
						outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
			 
			//TFEXONBASEDKEGGPATHWAY
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
			/**********************************************************/
	    	/************TF EXON BASED KEGG PATHWAY ends***************/
	    	/**********************************************************/

			  
	    	 /**********************************************************/
	    	 /************TF REGULATION BASED KEGG PATHWAY starts*******/
	    	 /**********************************************************/
			Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
						outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
			
			
			//TFREGULATIONBASEDKEGGPATHWAY
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
			/**********************************************************/
	    	/************TF REGULATION BASED KEGG PATHWAY ends*********/
	    	/**********************************************************/
 
			  
	    	/**********************************************************/
	    	/************TF ALL BASED KEGG PATHWAY starts**************/
			/**********************************************************/
			Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
						outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
			
			
			//TFALLBASEDKEGGPATHWAY
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
	    	/**********************************************************/
	    	/************TF ALL BASED KEGG PATHWAY ends****************/
			/**********************************************************/

  
	     }//End of IF: TF KEGG PATHWAY Enrichment
		
		//ONLY TFCELLLINEKEGGPATHWAY ENRICHMENT CASE (DO NOT CARE TF ENRICHMENT)
	    if (tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment() &&
	    		 !tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()){
	    	 
	    	 /**********************************************************/
	    	 /***********************TF starts**************************/
	    	 /**********************************************************/
	    	 Remap.remap(
	    			dataFolder,
					sourceReferenceAssemblyID, 
					targetReferenceAssemblyID, 
					outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
					outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
					outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
					merge,
					allowMultipleLocation,
					minimumRatioOfBasesThatMustBeRemapped,
					maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
	    	 
		    //TF
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMap(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  
	    	 /**********************************************************/
	    	 /***********************TF ends****************************/
	    	 /**********************************************************/


	    	/**********************************************************/
	    	/********TF CELLLINE EXON BASED KEGG PATHWAY starts********/
	    	/**********************************************************/
	   	  	Remap.remap(
					dataFolder,
					sourceReferenceAssemblyID, 
					targetReferenceAssemblyID, 
					outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
					outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
					outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
					merge,
					allowMultipleLocation,
					minimumRatioOfBasesThatMustBeRemapped,
					maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
	   	  	
	   	  	//TFCELLINEEXONBASEDKEGGPATHWAY
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
	    	 /**********************************************************/
	    	 /********TF CELLLINE EXON BASED KEGG PATHWAY ends**********/
	    	 /**********************************************************/
	  
	   	  	
	    	/**********************************************************/
	    	/*****TF CELLLINE REGULATION BASED KEGG PATHWAY starts*****/
	    	/**********************************************************/
	   	  	Remap.remap(
					dataFolder,
					sourceReferenceAssemblyID, 
					targetReferenceAssemblyID, 
					outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
					outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
					outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
						merge,
					allowMultipleLocation,
					minimumRatioOfBasesThatMustBeRemapped,
					maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
	   	  	
	   	  	//TFCELLINEEXONBASEDKEGGPATHWAY
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
	    	/**********************************************************/
	    	/*****TF CELLLINE REGULATION BASED KEGG PATHWAY ends*******/
	    	/**********************************************************/

	    	/**********************************************************/
	    	/********TF CELLLINE ALL BASED KEGG PATHWAY starts*********/
	    	/**********************************************************/
	   	  	Remap.remap(
					dataFolder,
					sourceReferenceAssemblyID, 
					targetReferenceAssemblyID, 
					outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
					outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
					outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
					merge,
					allowMultipleLocation,
					minimumRatioOfBasesThatMustBeRemapped,
					maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
	   	  	
	   	  	//TFCELLINEEXONBASEDKEGGPATHWAY
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
	    	/**********************************************************/
	    	/********TF CELLLINE ALL BASED KEGG PATHWAY ends***********/
	    	/**********************************************************/

	     }//End of IF: TF CELLLINE KEGG PATHWAY Enrichment
	     
	     
	     
		//BOTH TFKEGGPATHWAY AND TFCELLLINEKEGGPATHWAY ENRICHMENT CASE (DO NOT CARE TF ENRICHMENT)
	    if(tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() && tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
	    	 
	    	 /**********************************************************/
	    	 /***********************TF starts**************************/
	    	 /**********************************************************/
	    	 Remap.remap(
	    			dataFolder,
					sourceReferenceAssemblyID, 
					targetReferenceAssemblyID, 
					outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
					outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
					outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
					merge,
					allowMultipleLocation,
					minimumRatioOfBasesThatMustBeRemapped,
					maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
	    	//TF
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMap(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  
			/**********************************************************/
			/***********************TF ends****************************/
			/**********************************************************/

			
	    	 /**********************************************************/
	    	 /************TF EXON BASED KEGG PATHWAY starts*************/
	    	 /**********************************************************/
			 Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
						outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
			 
			//TFEXONBASEDKEGGPATHWAY
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
			/**********************************************************/
	    	/************TF EXON BASED KEGG PATHWAY ends***************/
	    	/**********************************************************/

			  
	    	/**********************************************************/
	    	/************TF REGULATION BASED KEGG PATHWAY starts*******/
	    	/**********************************************************/
			Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
						outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
			
			
			//TFREGULATIONBASEDKEGGPATHWAY
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
			 /**********************************************************/
	    	 /************TF REGULATION BASED KEGG PATHWAY ends*********/
	    	 /**********************************************************/
 
			  
	    	/**********************************************************/
	    	/************TF ALL BASED KEGG PATHWAY starts**************/
			/**********************************************************/
			Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
						outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
			
			
			//TFALLBASEDKEGGPATHWAY
			conversionMap = new HashMap<String,String>();
			fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
			convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
	    	/**********************************************************/
	    	/************TF ALL BASED KEGG PATHWAY ends****************/
			/**********************************************************/

	    	 
	    	 	/**********************************************************/
		    	/********TF CELLLINE EXON BASED KEGG PATHWAY starts********/
		    	/**********************************************************/
		   	  	Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
						outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
		   	  	
		   	  	//TFCELLINEEXONBASEDKEGGPATHWAY
				conversionMap = new HashMap<String,String>();
				fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
				convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
		    	 /**********************************************************/
		    	 /********TF CELLLINE EXON BASED KEGG PATHWAY ends**********/
		    	 /**********************************************************/
		  
		   	  	
		    	/**********************************************************/
		    	/*****TF CELLLINE REGULATION BASED KEGG PATHWAY starts*****/
		    	/**********************************************************/
		   	  	Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
						outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
							merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
		   	  	
		   	  	//TFCELLINEEXONBASEDKEGGPATHWAY
				conversionMap = new HashMap<String,String>();
				fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
				convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
		    	/**********************************************************/
		    	/*****TF CELLLINE REGULATION BASED KEGG PATHWAY ends*******/
		    	/**********************************************************/

		    	/**********************************************************/
		    	/********TF CELLLINE ALL BASED KEGG PATHWAY starts*********/
		    	/**********************************************************/
		   	  	Remap.remap(
						dataFolder,
						sourceReferenceAssemblyID, 
						targetReferenceAssemblyID, 
						outputFolder + Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES , 
						outputFolder + Commons.REMAP_DUMMY_OUTPUT_FILE,
						outputFolder + Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,
						merge,
						allowMultipleLocation,
						minimumRatioOfBasesThatMustBeRemapped,
						maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
		   	  	
		   	  	//TFCELLINEEXONBASEDKEGGPATHWAY
				conversionMap = new HashMap<String,String>();
				fillConversionMap(outputFolder, Commons.REMAP_REPORT_FILE_LINE_BY_LINE_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH38_COORDINATES,conversionMap);
				convertUsingMapVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES,conversionMap);  	
		    	/**********************************************************/
		    	/********TF CELLLINE ALL BASED KEGG PATHWAY ends***********/
		    	/**********************************************************/
				
	     }//End of IF: TFKEGGPATHWAY AND TFCELLLINEKEGGPATHWAY Enrichment
		
	}
	
	
	public static void readandCreateREMAPInputFiles(String outputFolder, 
			EnrichmentType tfEnrichment, 
			EnrichmentType tfKeggPathwayEnrichment, 
			EnrichmentType tfCellLineKeggPathwayEnrichment){
		
		//ONLY TF ENRICHMENT CASE
		if (tfEnrichment.isTfEnrichment() &&
				 !tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() &&
				 !tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
		 }
		 
		
		//ONLY TFKEGGPATHWAY ENRICHMENT CASE (DO NOT CARE  TF ENRICHMENT)
	    if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() &&
	    		 !tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){	   
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
			
			 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);					
	     }
		
	    //ONLY TFCELLLINEKEGGPATHWAY ENRICHMENT CASE (DO NOT CARE  TF ENRICHMENT)
	    if (tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment() &&
	    		 !tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()){
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
				
			 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);					

	     }
	     
	    //BOTH TFKEGGPATHWAY AND TFCELLLINEKEGGPATHWAY ENRICHMENT CASE (DO NOT CARE  TF ENRICHMENT)
	    if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() &&
	    		 tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
	    	 readResultsandWrite(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
				
			 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);					
	    	 
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES, Commons.REMAP_INPUT_FILE_LINE_BY_LINE_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_CHRNUMBER_0BASED_START_ENDEXCLUSIVE_GRCH37_P13_COORDINATES);					

	     }
	}

	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2]	--->	Input File Format	
	//			--->	default	Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE	
	//args[3]	--->	Annotation, overlap definition, number of bases, 
	//					default 1
	//args[4]	--->	Perform Enrichment parameter
	//			--->	default	Commons.DO_ENRICH
	//			--->			Commons.DO_NOT_ENRICH	
	//args[5]	--->	Generate Random Data Mode
	//			--->	default	Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	//			--->			Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT	
	//args[6]	--->	multiple testing parameter, enriched elements will be decided and sorted with respect to this parameter
	//			--->	default Commons.BENJAMINI_HOCHBERG_FDR
	//			--->			Commons.BONFERRONI_CORRECTION
	//args[7]	--->	Bonferroni Correction Significance Level, default 0.05
	//args[8]	--->	Bonferroni Correction Significance Criteria, default 0.05
	//args[9]	--->	Number of permutations, default 5000
	//args[10]	--->	Dnase Enrichment
	//			--->	default Commons.DO_NOT_DNASE_ENRICHMENT
	//			--->	Commons.DO_DNASE_ENRICHMENT
	//args[11]	--->	Histone Enrichment
	//			--->	default	Commons.DO_NOT_HISTONE_ENRICHMENT
	//			--->			Commons.DO_HISTONE_ENRICHMENT
	//args[12]	--->	Transcription Factor(TF) Enrichment 
	//			--->	default	Commons.DO_NOT_TF_ENRICHMENT
	//			--->			Commons.DO_TF_ENRICHMENT
	//args[13]	--->	KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_KEGGPATHWAY_ENRICHMENT
	//args[14]	--->	TF and KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	//args[15]	--->	TF and CellLine and KeggPathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	//args[16]	--->	RSAT parameter
	//			--->	default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//			--->			Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//args[17]	--->	job name example: ocd_gwas_snps 
	//args[18]	--->	writeGeneratedRandomDataMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	//			--->			Commons.WRITE_GENERATED_RANDOM_DATA
	//args[19]	--->	writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//args[20]	--->	writePermutationBasedAnnotationResultMode checkBox
	//			---> 	default	Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//args[21]  --->    number of permutations in each run. Default is 2000
	//args[22]  --->	UserDefinedGeneSet Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT
	//							Commons.DO_USER_DEFINED_GENESET_ENRICHMENT
	//args[23]	--->	UserDefinedGeneSet InputFile 
	//args[24]	--->	UserDefinedGeneSet GeneInformationType
	//					default Commons.GENE_ID
	//							Commons.GENE_SYMBOL
	//							Commons.RNA_NUCLEOTIDE_ACCESSION
	//args[25]	--->	UserDefinedGeneSet	Name
	//args[26]	--->	UserDefinedGeneSet 	Optional GeneSet Description InputFile
	//args[27]  --->	UserDefinedLibrary Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT
	//						 	Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT
	//args[28]  --->	UserDefinedLibrary InputFile
	//args[29] - args[args.length-1]  --->	Note that the selected cell lines are
	//					always inserted at the end of the args array because it's size
	//					is not fixed. So for not (until the next change on args array) the selected cell
	//					lines can be reached starting from 22th index up until (args.length-1)th index.
	//					If no cell line selected so the args.length-1 will be 22-1 = 21. So it will never
	//					give an out of boundry exception in a for loop with this approach.
	public static void main(String[] args) {
		String glanetFolder = args[1];
		
		//jobName starts
		String jobName = args[17].trim();
		if (jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		//jobName ends
				
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName +  System.getProperty("file.separator");
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator");
		
		EnrichmentType tfEnrichment 		= EnrichmentType.convertStringtoEnum(args[12]);
		EnrichmentType tfKeggPathwayEnrichment 			= EnrichmentType.convertStringtoEnum(args[14]);
		EnrichmentType tfCellLineKeggPathwayEnrichment 	= EnrichmentType.convertStringtoEnum(args[15]);
	
		//delete old files starts 
		FileOperations.deleteOldFiles(outputFolder + Commons.AUGMENTATION_REMAP_INPUT_OUTPUT_DIRECTORY);
		//delete old files ends
	
		readandCreateREMAPInputFiles(outputFolder,tfEnrichment,tfKeggPathwayEnrichment,tfCellLineKeggPathwayEnrichment);
		
		callREMAPAndWriteAugmentationFilesInLatestAssembly(outputFolder,dataFolder,tfEnrichment,tfKeggPathwayEnrichment,tfCellLineKeggPathwayEnrichment);
		
	}

}
