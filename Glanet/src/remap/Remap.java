/**
 * 
 */
package remap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;
import common.Commons;
import enumtypes.Assembly;
import enumtypes.ChromosomeName;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * @author Burçak Otlu
 * @date Nov 25, 2014
 * @project Glanet 
 *
 */
public class Remap {
	
	final static Logger logger = Logger.getLogger(Remap.class);

	
	public static void fillAssemblyName2RefSeqAssemblyIDMap(
			String dataFolder,
			String supportedAssembliesFileName,
			Map<String,String> assemblyName2RefSeqAssemblyIDMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
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
		
		String species;
		String sourceAssemblyName;
		String sourceRefSeqAssemblyId;
		String targetAssemblyName;
		String targetRefSeqAssemblyId;
		
		try {
			fileReader = FileOperations.createFileReader(dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator"), supportedAssembliesFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while ((strLine = bufferedReader.readLine())!=null){
				
				if (!strLine.startsWith("#")){
					//Example line
					//#batch_id	query_species	query_name	query_ucsc	query_acc	target_species	target_name	target_ucsc	target_acc	alignment_date
					//85213	Homo sapiens	NCBI33		GCF_000001405.8	Homo sapiens	GRCh38	hg38	GCF_000001405.26	09/20/2014
					//85273	Homo sapiens	NCBI36	hg18	GCF_000001405.12	Homo sapiens	GRCh38	hg38	GCF_000001405.26	09/20/2014
					//85283	Homo sapiens	GRCh37.p11		GCF_000001405.23	Homo sapiens	GRCh38	hg38	GCF_000001405.26	09/20/2014

					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= (indexofFirstTab>0) ? strLine.indexOf('\t', indexofFirstTab+1) :-1;
					indexofThirdTab 	= (indexofSecondTab>0) ? strLine.indexOf('\t', indexofSecondTab+1) :-1;
					indexofFourthTab 	= (indexofThirdTab>0) ? strLine.indexOf('\t', indexofThirdTab+1) :-1;
					indexofFifthTab 	= (indexofFourthTab>0) ? strLine.indexOf('\t', indexofFourthTab+1) :-1;
					indexofSixthTab 	= (indexofFifthTab>0) ? strLine.indexOf('\t', indexofFifthTab+1) :-1;
					indexofSeventhTab 	= (indexofSixthTab>0) ? strLine.indexOf('\t', indexofSixthTab+1) :-1;
					indexofEigthTab 	= (indexofSeventhTab>0) ? strLine.indexOf('\t', indexofSeventhTab+1) :-1;
					indexofNinethTab 	= (indexofEigthTab>0) ? strLine.indexOf('\t', indexofEigthTab+1) :-1;
					
					if (indexofFirstTab>0 && 
							indexofSecondTab>0 && 
							indexofThirdTab>0 && 
							indexofFourthTab>0 && 
							indexofFifthTab>0 &&
							indexofSixthTab>0 &&
							indexofSeventhTab>0 &&
							indexofEigthTab>0 && 
							indexofNinethTab>0){
						
						species = strLine.substring(indexofFirstTab+1, indexofSecondTab);
						
						if (species.equals("Homo sapiens")){
							sourceAssemblyName = strLine.substring(indexofSecondTab+1, indexofThirdTab);
							sourceRefSeqAssemblyId = strLine.substring(indexofFourthTab+1, indexofFifthTab);
							
							if (!assemblyName2RefSeqAssemblyIDMap.containsKey(sourceAssemblyName)){
								assemblyName2RefSeqAssemblyIDMap.put(sourceAssemblyName,sourceRefSeqAssemblyId);
							}
							
							
							targetAssemblyName = strLine.substring(indexofSixthTab+1, indexofSeventhTab);
							targetRefSeqAssemblyId = strLine.substring(indexofEigthTab+1, indexofNinethTab);
							
							if (!assemblyName2RefSeqAssemblyIDMap.containsKey(targetAssemblyName)){
								assemblyName2RefSeqAssemblyIDMap.put(targetAssemblyName,targetRefSeqAssemblyId);
							}
						}//End of if species is HOMO SAPIENS
						
					}//End of if necessary indexofTabs are nonzero
					
					
					

				}//End of if not a comment line
				
			}//End of while
			
			
			//close
			bufferedReader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	
	public static void remap_show_batches(String dataFolder, String supportedAssembliesFileName){
		
		String remapFile = dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator")  + "remap_api.pl";
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		BufferedReader bufferedReader  = null;
		String line;
		String[] cmdarray = {"perl", remapFile, "--mode", "batches"};
		
			try {
				
				fileWriter = FileOperations.createFileWriter(dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator"), supportedAssembliesFileName);
				bufferedWriter = new BufferedWriter(fileWriter);
				
//				process = runtime.exec("perl "  + "\"" +  remapFile  + "\"");
//				process = runtime.exec("perl \"" + remapFile + "\" --mode batches");
				process = runtime.exec(cmdarray);
				
				process.waitFor();
				
				//output of the perl execution is here
				bufferedReader = new BufferedReader( new InputStreamReader( process.getInputStream()));
				
				while ( ( line = bufferedReader.readLine()) != null){
					logger.info(line);	
					bufferedWriter.write(line + System.getProperty("line.separator"));
				}//End of while
				
				bufferedWriter.flush();
				
				//Close 
				bufferedReader.close();
				bufferedWriter.close();
				
				logger.info("\nExit status = " + process.exitValue());
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		
	}
	
	
	public static void createOutputFileUsingREMAPREPORTFile(String remapReportFile, String outputFile){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
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
		
		int indexofUnderscore;
		
		String lineNumberString;
		int lineNumber = Integer.MIN_VALUE;
		
		ChromosomeName sourceChrName;
		ChromosomeName mappedChrName;
		
		int sourceInt;	
		String mappedIntString;
		int mappedInt;
		
		int mappedStart;
		int mappedEnd;
		Assembly mappedAssembly;
		
		TIntObjectMap<Remapped> sourceLineNumber2RemappedMap = new TIntObjectHashMap<Remapped>();
		
		int maximumLineNumber = Integer.MIN_VALUE;
		Remapped remapped = null;
		
		int numberofUnConvertedGenomicLociInPrimaryAssembly = 0;
		int numberofConvertedGenomicLociInPrimaryAssembly = 0;
		
		try {
			
			File file = new File(remapReportFile);
			
			if (file.exists()){
				
				fileReader = FileOperations.createFileReader(remapReportFile);
				bufferedReader = new BufferedReader(fileReader);
				
				fileWriter = FileOperations.createFileWriter(outputFile);
				bufferedWriter = new BufferedWriter(fileWriter);
				
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
						
						lineNumberString = strLine.substring(0,indexofFirstTab);
						
						indexofUnderscore = lineNumberString.indexOf('_');
						lineNumber = Integer.parseInt(lineNumberString.substring(indexofUnderscore+1));
						
						sourceInt = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
						
						mappedIntString = strLine.substring(indexofSecondTab+1, indexofThirdTab);
						
						if (!mappedIntString.equals(Commons.NULL)){
							
							mappedInt = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
							
							sourceChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofThirdTab+1, indexofFourthTab));
							mappedChrName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofFourthTab+1, indexofFifthTab));
							
							mappedStart = Integer.parseInt(strLine.substring(indexofTwelfthTab+1, indexofThirteenthTab));
							mappedEnd = Integer.parseInt(strLine.substring(indexofThirteenthTab+1, indexofFourteenthTab));
							
							mappedAssembly =  Assembly.convertStringtoEnum(strLine.substring(indexofSeventeenthTab+1));
							
							if ((sourceInt == 1) && 
									(mappedInt == 1) &&
									sourceChrName == mappedChrName &&
									mappedAssembly.isPrimaryAssembly()){
								
								remapped = new Remapped(mappedInt, mappedStart, mappedEnd, mappedChrName, mappedAssembly);
								sourceLineNumber2RemappedMap.put(lineNumber, remapped);
								
							}//End of IF: Valid conversion
						}//End of IF mappedInt is not NULL
						
									
					}//End of IF: Not Header or Comment Line
					
				}//End of while
				
				//Set maximum line number to the last lineNumber
				maximumLineNumber = lineNumber;
				
				logger.debug("******************************************************************************");
				logger.debug("Number of given genomic loci before NCBI REMAP: " + maximumLineNumber);
				
				
				//Write to the file
				for(int i=1; i<=maximumLineNumber; i++){
					
					remapped = sourceLineNumber2RemappedMap.get(i);
					
					if(remapped != null){
						bufferedWriter.write(remapped.getMappedChrName().convertEnumtoString() + "\t" + remapped.getMappedStart() + "\t" + remapped.getMappedEnd() + System.getProperty("line.separator"));
						numberofConvertedGenomicLociInPrimaryAssembly++;
					}else{
						bufferedWriter.write(Commons.NULL + "\t" + Commons.NULL + "\t" + Commons.NULL + System.getProperty("line.separator"));
						numberofUnConvertedGenomicLociInPrimaryAssembly++;
					}
					
				}//End of for
				
				logger.debug("Number of converted genomic loci after NCBI REMAP: " + numberofConvertedGenomicLociInPrimaryAssembly );
				logger.debug("We have lost " + numberofUnConvertedGenomicLociInPrimaryAssembly + " genomic loci during NCBI REMAP"  );
				logger.debug("******************************************************************************");

				//close 
				bufferedReader.close();
				bufferedWriter.close();
				
			}//End of if remapReportFile exists
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void remap(
			String dataFolder,
			String sourceAssembly, 
			String targetAssembly,
			String sourceFileName,
			String outputFileName,
			String reportFileName,
			String genomeWorkbenchProjectFile,
			String merge,
			String allowMultipleLocation,
			double minimumRatioOfBasesThatMustBeRemapped,
			double maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength){
					
		String remapFile = dataFolder + Commons.NCBI_REMAP + System.getProperty("file.separator")  + "remap_api.pl";
		
		//DirectoryName can contain empty spaces
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
			
			try {
				process = runtime.exec("perl "  + "\"" + remapFile + "\"" + " --mode asm-asm --from " + sourceAssembly  + " --dest " +  targetAssembly +  " --annotation " + "\"" + sourceFileName + "\"" +  " --annot_out "+  "\"" + outputFileName + "\""  + " --report_out " + "\"" + reportFileName + "\"" +   " --gbench_out " + "\"" + genomeWorkbenchProjectFile   + "\"" + " --merge " + merge + " --allowdupes " + allowMultipleLocation +  " --mincov " + minimumRatioOfBasesThatMustBeRemapped + " --maxexp " + maximumRatioForDifferenceBetweenSourceLengtheAndTargetLength);
				process.waitFor();
				
				//output of the perl execution is here
				BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( process.getInputStream()));
				String line;
				
				while ( ( line = bufferedReader.readLine()) != null){
					logger.info(line);
				}//End of while
				
				logger.info("\nExit status = " + process.exitValue());
				
				//Close
				bufferedReader.close();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
	
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String glanetFolder = args[1];
		String dataFolder = glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator");
		
		
//		remap(dataFolder,"GCF_000001405.25","GCF_000001405.26","C:\\Users\\Burçak\\Google Drive\\Output\\NoName\\GivenInputData\\chrName_0Based_StartInclusive_EndExclusive_hg38_coordinates.bed","C:\\Users\\Burçak\\Google Drive\\Output\\NoName\\GivenInputData\\chrName_0Based_StartInclusive_EndExclusive_hg19_coordinates.bed");
//		remap(dataFolder,"GCF_000001405.26","GCF_000001405.25","C:\\Users\\Burçak\\Developer\\Java\\GLANET\\Glanet\\src\\remap\\chrName_0Based_StartInclusive_EndExclusive_hg38_coordinates.bed","C:\\Users\\Burçak\\Developer\\Java\\GLANET\\Glanet\\src\\remap\\chrName_0Based_StartInclusive_EndExclusive_hg19_coordinates.bed", Commons.NCBI_REMAP_API_MERGE_FRAGMENTS_DEFAULT_ON, Commons.NCBI_REMAP_API_ALLOW_MULTIPLE_LOCATIONS_TO_BE_RETURNED_DEFAULT_ON, Commons.NCBI_REMAP_API_MINIMUM_RATIO_OF_BASES_THAT_MUST_BE_REMAPPED_DEFAULT_0_POINT_5_, Commons.NCBI_REMAP_API_MAXIMUM_RATIO_FOR_DIFFERENCE_BETWEEN_SOURCE_LENGTH_AND_TARGET_LENGTH_DEFAULT_2);
		remap_show_batches(dataFolder,Commons.NCBI_REMAP_API_SUPPORTED_ASSEMBLIES_FILE);
		
		
//		GCF_000001405.25 <----> GRCh37.p13
//		GCF_000001405.26 <------> GRCh38
//		GCF_000001405.27 <------> GRCh38.p1
		
	}
	
	
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
							logger.debug("Please notice that there is an unconverted genomic loci");
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
																
								conversionMap.put(sourceChrName.convertEnumtoString() + "\t" + sourceStart + "\t" + sourceEnd, mappedChrName.convertEnumtoString() + "\t" + mappedStart + "\t" + mappedEnd);
								
							}//End of IF: Valid conversion
						}//End of IF mappedInt is not NULL
						
									
					}//End of IF: Not Header or Comment Line
					
				}//End of while
				
				//for debug purposes starts
				logger.debug("number of lines in conversionMap: " + conversionMap.size() + " for file: " + remapReportFile);
				//for debug purposes ends 
				
				
				//close 
				bufferedReader.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}//End of if remapReportFile exists

		
		
	}

}
