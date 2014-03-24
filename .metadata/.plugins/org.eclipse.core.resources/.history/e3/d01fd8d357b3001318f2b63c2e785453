/**
 * @author Burcak Otlu
 * Sep 13, 2013
 * 4:48:07 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;


public class CreationofDataFileforRBoxPlot{

	/**
	 * 
	 */
	public CreationofDataFileforRBoxPlot() {
	}

	
	public static void closeBufferedReaders(Map<String, BufferedReader> bufferedReaderHashMap){
		BufferedReader bufferedReader;
		
		for(Map.Entry<String, BufferedReader> entry : bufferedReaderHashMap.entrySet()){
			bufferedReader = entry.getValue();
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeBufferedReaders(List<GCorMapabilityFile> listofTopTenMostVaryingFiles){
		BufferedReader bufferedReader;
		
		for(GCorMapabilityFile file : listofTopTenMostVaryingFiles){
			bufferedReader = file.getBufferedReader();
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//todo
	public static void writeTabDelimitedTextFile(List<GCorMapabilityFile> listofTopTenMostVaryingFiles,String boxPlotFileName, String writeMode){
		 BufferedReader bufferedReader;
		 
		 FileWriter fileWriter;
		 BufferedWriter bufferedWriter;
		 
		 String  wholeStrLinewithTabs = null;
		 String strLine= null;
		 String concatenatedStringwithoutTabs = null;
		 
		 String skipLine1 = null;
		 String fileName = null;
		
		 String skipLine2 = null;
		 String standardDeviation = null;
		 String mean = null;
		
		 
		 String header = null;
		 String functionalElementName;
		 
		 boolean firstFileName = true;
		 boolean firstStandardDeviation = true;
		 boolean firstMean = true;
		 boolean firstFunctionalElementName = true;
		 boolean firstGCorMapability = true;
		 
		 int indexofFirstUnderscore;
		 
		 
		 try {
			fileWriter = new FileWriter(boxPlotFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//Write skipLine1 contains long file names
			for(GCorMapabilityFile file: listofTopTenMostVaryingFiles){
				fileName = file.getFileName();
				
				if (fileName.startsWith("GC_") || (fileName.startsWith("MAPABILITY_"))){
					indexofFirstUnderscore = fileName.indexOf('_');
					//In order to get rid of GC_ or MAPABILITY_ in filename
					if(indexofFirstUnderscore>0){
						fileName = fileName.substring(indexofFirstUnderscore+1);
					}
				}
				
				
				
				if (firstFileName){
					skipLine1 = fileName;
					firstFileName =false;		
				}else{
					skipLine1 = skipLine1 + "\t" + fileName;	
				}
			}//End of for
			bufferedWriter.write(skipLine1 + "\n");
			
			
			if (Commons.WRITE_STANDARD_DEVIATION_VALUE_OF_EACH_FILE.equals(writeMode)){
				
				//Write skipLine2 contains standard deviations
				for(GCorMapabilityFile file: listofTopTenMostVaryingFiles){
					standardDeviation = file.getStandardDeviation().toString();
					
					if (firstStandardDeviation){
						skipLine2 = standardDeviation;
						firstStandardDeviation =false;		
					}else{
						skipLine2 = skipLine2 + "\t" + standardDeviation;	
					}
				}//End of for
				bufferedWriter.write(skipLine2 + "\n");
				
			} else if(Commons.WRITE_MEAN_VALUE_OF_EACH_FILE.equals(writeMode)){
				
				//Write skipLine2 contains means
				for(GCorMapabilityFile file: listofTopTenMostVaryingFiles){
					mean = file.getMean().toString();
					
					if (firstMean){
						skipLine2 = mean;
						firstMean =false;		
					}else{
						skipLine2 = skipLine2 + "\t" + mean;	
					}
				}//End of for
				bufferedWriter.write(skipLine2 + "\n");
			}
			
			
			
			//Write header
			for(GCorMapabilityFile file: listofTopTenMostVaryingFiles){
				functionalElementName = file.getFunctionalElementName();
				
				if (firstFunctionalElementName){
						header = functionalElementName;
						firstFunctionalElementName =false;
				}else{
					header = header + "\t" + functionalElementName;	
				}
			}//End of for		
			bufferedWriter.write(header + "\n");
		
			//Write GC or Mapability values
			do{
				strLine = "";
				wholeStrLinewithTabs="";
				concatenatedStringwithoutTabs = "";
				
				//initialize firstGC to true for each line
				firstGCorMapability = true;
				
				for(GCorMapabilityFile file: listofTopTenMostVaryingFiles){
					
					bufferedReader = file.getBufferedReader();
					
					strLine = bufferedReader.readLine();
					
					if (firstGCorMapability){
						if (strLine==null){
							wholeStrLinewithTabs = "";
						}else{
							wholeStrLinewithTabs = strLine;
							concatenatedStringwithoutTabs = strLine;
						}	
						firstGCorMapability = false;
					}else{
						if (strLine==null){
							wholeStrLinewithTabs = wholeStrLinewithTabs +"\t" + "";	
						}else{
							concatenatedStringwithoutTabs = concatenatedStringwithoutTabs + strLine;
							wholeStrLinewithTabs = wholeStrLinewithTabs +"\t" + strLine;		
							
						}	
					}
					
						
					
				}// End of for
				
				if (concatenatedStringwithoutTabs!=""){
					bufferedWriter.write(wholeStrLinewithTabs + "\n");
				}
				
			}while(concatenatedStringwithoutTabs!="");
			
			
			//do not forget to close buffered readers in hash map
			closeBufferedReaders(listofTopTenMostVaryingFiles);
			
			bufferedWriter.close();
			
			} catch (IOException e) {
				e.printStackTrace();
		}
		 

	}
	
	
	 public static void  writeTabDelimitedTextFile(Map<String,BufferedReader> bufferedReaderHashMap, String boxPlotFileName){
		 
		 BufferedReader bufferedReader;
		 
		 FileWriter fileWriter;
		 BufferedWriter bufferedWriter;
		 
		 String  wholeStrLinewithTabs = null;
		 String strLine= null;
		 String concatenatedStringwithoutTabs = null;
		 
		 String header = null;
		 String fileName = null;
		 
		 boolean firstFileName = true;
		 boolean firstGCorMapability = true;
		 
		 int indexofFirstUnderscore;
		 
		 Set<Map.Entry<String, BufferedReader>> entrySet;

		 entrySet = bufferedReaderHashMap.entrySet();
		 
		 try {
			fileWriter = new FileWriter(boxPlotFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//Write header
			for(Map.Entry<String, BufferedReader> entry: entrySet){
				fileName = entry.getKey();
				
				if (fileName.startsWith("GC_") || (fileName.startsWith("MAPABILITY_"))){
					indexofFirstUnderscore = fileName.indexOf('_');
					//In order to get rid of GC_ or MAPABILITY_ in filename
					if(indexofFirstUnderscore>0){
						fileName = fileName.substring(indexofFirstUnderscore+1);
					}
				}
				
				
				
				if (firstFileName){
						header = fileName;
						firstFileName =false;
					
				}else{
					header = header + "\t" + fileName;	
				}
			}//End of for
				
			bufferedWriter.write(header + "\n");
		
			//Write GC or Mapability values
			do{
				strLine = "";
				wholeStrLinewithTabs="";
				concatenatedStringwithoutTabs = "";
				
				//initialize firstGC to true for each line
				firstGCorMapability = true;
				
				for(Map.Entry<String, BufferedReader> entry: entrySet){
					
					bufferedReader = entry.getValue();
					
					strLine = bufferedReader.readLine();
					
					if (firstGCorMapability){
						if (strLine==null){
							wholeStrLinewithTabs = "";
						}else{
							wholeStrLinewithTabs = strLine;
							concatenatedStringwithoutTabs = strLine;
						}	
						firstGCorMapability = false;
					}else{
						if (strLine==null){
							wholeStrLinewithTabs = wholeStrLinewithTabs +"\t" + "";	
						}else{
							concatenatedStringwithoutTabs = concatenatedStringwithoutTabs + strLine;
							wholeStrLinewithTabs = wholeStrLinewithTabs +"\t" + strLine;		
							
						}	
					}
					
						
					
				}// End of for
				
				if (concatenatedStringwithoutTabs!=""){
					bufferedWriter.write(wholeStrLinewithTabs + "\n");
				}
				
			}while(concatenatedStringwithoutTabs!="");
			
			
			//do not forget to close buffered readers in hash map
			closeBufferedReaders(bufferedReaderHashMap);
			
			bufferedWriter.close();
			
			} catch (IOException e) {
				e.printStackTrace();
		}
		 
		 
	 }
	
	 
	 //todo
	 public static void createTabDelimitedTextFileforBoxPlotinR(File directory, String boxPlotFileName,List<GCorMapabilityFile> listofFiles,String writeMode){
		 	
	    	String path;
	    	
	    	FileReader fileReader;
	    	BufferedReader bufferedReader=null;
	    	
			if(!directory.exists()){
				 System.out.println("No File/Dir" + directory.getName()); 
			 }
			
			 if(directory.isDirectory()){// a directory!
				 
				 path = directory.getPath();
				 
				 for(GCorMapabilityFile file : listofFiles){
					 
					 try {
						fileReader = new FileReader(path +"\\"+ file.getFileName());
						bufferedReader = new BufferedReader(fileReader);
							
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					 
					 file.setBufferedReader(bufferedReader);
					 
				 }//End of For
					 
				       
				writeTabDelimitedTextFile(listofFiles,boxPlotFileName,writeMode);
			        
			        
			 } //End of If

	 }
		
	
	public static void createTabDelimitedTextFileforBoxPlotinR(File directory, String boxPlotFileName){
		
		File[] files;
    	File file;
    	int numberofFiles;
    	
    	String fileName;
    	String filePath;
    	
    	FileReader fileReader;
    	BufferedReader bufferedReader;
    	Map<String,BufferedReader> bufferedReaderHashMap = new HashMap<String,BufferedReader>();
    	
    	
		if(!directory.exists()){
			 System.out.println("No File/Dir" + directory.getName()); 
		 }
		
		 // Reading directory contents
		 if(directory.isDirectory()){// a directory!
			 
			    files = directory.listFiles();
			    numberofFiles= files.length;
			    
			 	System.out.printf("Number of Files %d in %s\n", files.length, directory.getAbsolutePath());
				
			    
		        for (int i = 0; i < numberofFiles; i++) {
		        		
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		file = files[i];		        		
		        		
		        		fileName = file.getName();		
		    			filePath = file.getPath();
		    			
		    			
		    			try {
							fileReader = new FileReader(filePath);
							bufferedReader = new BufferedReader(fileReader);
							
							bufferedReaderHashMap.put(fileName, bufferedReader);
							
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
		    			
		    				
		    		}//Check for each file and read each file		        	
//		            System.out.println(files[i]);		            		         		           		            
		        }	// End of For -----reading each file in the directory	  
		        
			
		        //All BufferedReaders are created and put into the hash map
		        writeTabDelimitedTextFile(bufferedReaderHashMap,boxPlotFileName);
		        
		        
	        } //End of if: For all files in this directory
		
	}
	
	//Create For All Files
	public static void createTabDelimitedTextFileForAllFiles(){
		
		String dnaseGCBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_ALL_DNASE_GC_FILES;
		String tfbsGCBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_ALL_TFBS_GC_FILES;
		String histoneGCBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_ALL_HISTONE_GC_FILES;

		String dnaseMapabilityBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_ALL_DNASE_MAPABILITY_FILES;
		String tfbsMapabilityBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_ALL_TFBS_MAPABILITY_FILES;
		String histoneMapabilityBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_ALL_HISTONE_MAPABILITY_FILES;
		
		String allDnaseGCFilesDirectory = Commons.ALL_DNASE_GC_FILES_DIRECTORY;
		String allTfbsGCFilesDirectory = Commons.ALL_TFBS_GC_FILES_DIRECTORY;
		String allHistoneGCFilesDirectory = Commons.ALL_HISTONE_GC_FILES_DIRECTORY;
		
		String allDnaseMapabilityFilesDirectory = Commons.ALL_DNASE_MAPABILITY_FILES_DIRECTORY;
		String allTfbsMapabilityFilesDirectory = Commons.ALL_TFBS_MAPABILITY_FILES_DIRECTORY;
		String allHistoneMapabilityFilesDirectory = Commons.ALL_HISTONE_MAPABILITY_FILES_DIRECTORY;

		//Create tab delimited text file for GC values of all files
		File dnaseGCDirectory = new File(allDnaseGCFilesDirectory);
		File tfbsGCDirectory = new File(allTfbsGCFilesDirectory);
		File histoneGCDirectory = new File(allHistoneGCFilesDirectory);
		
		createTabDelimitedTextFileforBoxPlotinR(dnaseGCDirectory,dnaseGCBoxPlotinRFileName);
		createTabDelimitedTextFileforBoxPlotinR(tfbsGCDirectory,tfbsGCBoxPlotinRFileName);
		createTabDelimitedTextFileforBoxPlotinR(histoneGCDirectory,histoneGCBoxPlotinRFileName);
		
		//Create tab delimited text file for MAPABILITY values of all files
		File dnaseMapabilityDirectory = new File(allDnaseMapabilityFilesDirectory);
		File tfbsMapabilityDirectory = new File(allTfbsMapabilityFilesDirectory);
		File histoneMapabilityDirectory = new File(allHistoneMapabilityFilesDirectory);
		
		createTabDelimitedTextFileforBoxPlotinR(dnaseMapabilityDirectory,dnaseMapabilityBoxPlotinRFileName);
		createTabDelimitedTextFileforBoxPlotinR(tfbsMapabilityDirectory,tfbsMapabilityBoxPlotinRFileName);
		createTabDelimitedTextFileforBoxPlotinR(histoneMapabilityDirectory,histoneMapabilityBoxPlotinRFileName);

	}
	
	//todo
	public static void fill(List<GCorMapabilityFile> listofFiles,String listofFilesNamesFileName){
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
		
		String fileName;
		String MAPABILITYorGC;
		String functionalElementName;
		double standardDeviation;
		double mean;
		
		GCorMapabilityFile gcorMapabilityFile;
		
		try {
			fileReader = new FileReader(listofFilesNamesFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				//example strLine
				//MAPABILITY	DNASE	idrPool.H1_ES_FAIRE_BP_TP_peaks_OV_H1_ES_B1_peaks_VS_UncFAIRE_H1_ES_B2_peaks.npk2.narrowPeak	H1_ES	number of intervals	1629	mean	0.88037356990742	std dev	0.20231867004224047
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
				indexofFifthTab = strLine.indexOf('\t', indexofFourthTab+1);
				indexofSixthTab = strLine.indexOf('\t', indexofFifthTab+1);
				indexofSeventhTab = strLine.indexOf('\t', indexofSixthTab+1);
				indexofEighthTab = strLine.indexOf('\t', indexofSeventhTab+1);
				indexofNinethTab = strLine.indexOf('\t', indexofEighthTab+1);
				
				MAPABILITYorGC = strLine.substring(0,indexofFirstTab);
				fileName = strLine.substring(indexofSecondTab+1, indexofThirdTab);
				functionalElementName =  strLine.substring(indexofThirdTab+1, indexofFourthTab);
				mean = Double.parseDouble(strLine.substring(indexofSeventhTab+1, indexofEighthTab));
				standardDeviation = Double.parseDouble(strLine.substring(indexofNinethTab+1));
				
				
				//Add mapability or gc to the fileName
				fileName = MAPABILITYorGC + "_" + fileName;
				
				gcorMapabilityFile = new GCorMapabilityFile();
				
				gcorMapabilityFile.setFileName(fileName);
				gcorMapabilityFile.setFunctionalElementName(functionalElementName);
				gcorMapabilityFile.setMean(mean);
				gcorMapabilityFile.setStandardDeviation(standardDeviation);
				gcorMapabilityFile.setBufferedReader(null);
				
				listofFiles.add(gcorMapabilityFile);
				
			}
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		
	}
	
	
	//TODO
	//Create For Tab Delimited Files
	//For Ten Different Mean Files
	public static void createTabDelimitedTextFileForTenDifferentMeanFiles(){
		//Ouput Tab Delimited Text Files
		//Data files for R
		String dataFileforRTenDifferentMeanDnaseGCFiles = Commons.DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_DNASE_GC_FILES;
		String dataFileforRTenDifferentMeanTfbsGCFiles = Commons.DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_TFBS_GC_FILES;
		String dataFileforRTenDifferentMeanHistoneGCFiles = Commons.DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_HISTONE_GC_FILES;

		String dataFileforRTenDifferentMeanDnaseMAPABILITYFiles = Commons.DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_DNASE_MAPABILITY_FILES;
		String dataFileforRTenDifferentMeanTfbsMAPABILITYFiles = Commons.DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_TFBS_MAPABILITY_FILES;
		String dataFileforRTenDifferentMeanHistoneMAPABILITYFiles = Commons.DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_HISTONE_MAPABILITY_FILES;
		
		//Get the required files (ten different mean files) from these directories
		//Same Directories as All Files
		String allDnaseGCFilesDirectory = Commons.ALL_DNASE_GC_FILES_DIRECTORY;
		String allTfbsGCFilesDirectory = Commons.ALL_TFBS_GC_FILES_DIRECTORY;
		String allHistoneGCFilesDirectory = Commons.ALL_HISTONE_GC_FILES_DIRECTORY;
		
		String allDnaseMapabilityFilesDirectory = Commons.ALL_DNASE_MAPABILITY_FILES_DIRECTORY;
		String allTfbsMapabilityFilesDirectory = Commons.ALL_TFBS_MAPABILITY_FILES_DIRECTORY;
		String allHistoneMapabilityFilesDirectory = Commons.ALL_HISTONE_MAPABILITY_FILES_DIRECTORY;

		//GC
		//Get the required GC files from these directories
		File dnaseGCDirectory = new File(allDnaseGCFilesDirectory);
		File tfbsGCDirectory = new File(allTfbsGCFilesDirectory);
		File histoneGCDirectory = new File(allHistoneGCFilesDirectory);
	
		List<GCorMapabilityFile> listofTenDifferentMeanDnaseGCFiles = new ArrayList<GCorMapabilityFile>();
		List<GCorMapabilityFile> listofTenDifferentMeanTfbsGCFiles = new ArrayList<GCorMapabilityFile>();
		List<GCorMapabilityFile> listofTenDifferentMeanHistoneGCFiles = new ArrayList<GCorMapabilityFile>();
		
		fill(listofTenDifferentMeanDnaseGCFiles,Commons.TEN_DIFFERENT_MEAN_DNASE_GC_FILES);
		fill(listofTenDifferentMeanTfbsGCFiles,Commons.TEN_DIFFERENT_MEAN_TFBS_GC_FILES);
		fill(listofTenDifferentMeanHistoneGCFiles,Commons.TEN_DIFFERENT_MEAN_HISTONE_GC_FILES);
	
		createTabDelimitedTextFileforBoxPlotinR(dnaseGCDirectory,dataFileforRTenDifferentMeanDnaseGCFiles,listofTenDifferentMeanDnaseGCFiles,Commons.WRITE_MEAN_VALUE_OF_EACH_FILE);
		createTabDelimitedTextFileforBoxPlotinR(tfbsGCDirectory,dataFileforRTenDifferentMeanTfbsGCFiles,listofTenDifferentMeanTfbsGCFiles,Commons.WRITE_MEAN_VALUE_OF_EACH_FILE);
		createTabDelimitedTextFileforBoxPlotinR(histoneGCDirectory,dataFileforRTenDifferentMeanHistoneGCFiles,listofTenDifferentMeanHistoneGCFiles,Commons.WRITE_MEAN_VALUE_OF_EACH_FILE);
		
		//MAPABILITY
		//Get the required MAPABILITY files from these directories
		File dnaseMapabilityDirectory = new File(allDnaseMapabilityFilesDirectory);
		File tfbsMapabilityDirectory = new File(allTfbsMapabilityFilesDirectory);
		File histoneMapabilityDirectory = new File(allHistoneMapabilityFilesDirectory);
		
		List<GCorMapabilityFile> listofTenDifferentMeanDnaseMapabilityFiles = new ArrayList<GCorMapabilityFile>();
		List<GCorMapabilityFile> listofTenDifferentMeanTfbsMapabilityFiles = new ArrayList<GCorMapabilityFile>();
		List<GCorMapabilityFile> listofTenDifferentMeanHistoneMapabilityFiles = new ArrayList<GCorMapabilityFile>();
		
		fill(listofTenDifferentMeanDnaseMapabilityFiles,Commons.TEN_DIFFERENT_MEAN_DNASE_MAPABILITY_FILES);
		fill(listofTenDifferentMeanTfbsMapabilityFiles,Commons.TOP_TEN_MOST_VARYING_TFBS_MAPABILITY_FILES);
		fill(listofTenDifferentMeanHistoneMapabilityFiles,Commons.TOP_TEN_MOST_VARYING_HISTONE_MAPABILITY_FILES);
		
		createTabDelimitedTextFileforBoxPlotinR(dnaseMapabilityDirectory,dataFileforRTenDifferentMeanDnaseMAPABILITYFiles,listofTenDifferentMeanDnaseMapabilityFiles,Commons.WRITE_MEAN_VALUE_OF_EACH_FILE);
		createTabDelimitedTextFileforBoxPlotinR(tfbsMapabilityDirectory,dataFileforRTenDifferentMeanTfbsMAPABILITYFiles,listofTenDifferentMeanTfbsMapabilityFiles,Commons.WRITE_MEAN_VALUE_OF_EACH_FILE);
		createTabDelimitedTextFileforBoxPlotinR(histoneMapabilityDirectory,dataFileforRTenDifferentMeanHistoneMAPABILITYFiles,listofTenDifferentMeanHistoneMapabilityFiles,Commons.WRITE_MEAN_VALUE_OF_EACH_FILE);
	
	}
	
	
	//TODO
	//Create For Tab Delimited Files
	public static void createTabDelimitedTextFileForTopTenVaryingFiles(){
		
		//Output File Names
		String dnaseGCBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_DNASE_GC_FILES;
		String tfbsGCBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_TFBS_GC_FILES;
		String histoneGCBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_HISTONE_GC_FILES;

		String dnaseMapabilityBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_DNASE_MAPABILITY_FILES;
		String tfbsMapabilityBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_TFBS_MAPABILITY_FILES;
		String histoneMapabilityBoxPlotinRFileName = Commons.DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_HISTONE_MAPABILITY_FILES;
		
		//Get the required file from these directories
		//Same Directories as All Files
		String allDnaseGCFilesDirectory = Commons.ALL_DNASE_GC_FILES_DIRECTORY;
		String allTfbsGCFilesDirectory = Commons.ALL_TFBS_GC_FILES_DIRECTORY;
		String allHistoneGCFilesDirectory = Commons.ALL_HISTONE_GC_FILES_DIRECTORY;
		
		String allDnaseMapabilityFilesDirectory = Commons.ALL_DNASE_MAPABILITY_FILES_DIRECTORY;
		String allTfbsMapabilityFilesDirectory = Commons.ALL_TFBS_MAPABILITY_FILES_DIRECTORY;
		String allHistoneMapabilityFilesDirectory = Commons.ALL_HISTONE_MAPABILITY_FILES_DIRECTORY;
		
		//GC
		//Create tab delimited text file for GC values of top ten most varying
		File dnaseGCDirectory = new File(allDnaseGCFilesDirectory);
		File tfbsGCDirectory = new File(allTfbsGCFilesDirectory);
		File histoneGCDirectory = new File(allHistoneGCFilesDirectory);
		
		List<GCorMapabilityFile> listofTopTenMostVaryingDnaseGCFiles = new ArrayList<GCorMapabilityFile>();
		List<GCorMapabilityFile> listofTopTenMostVaryingTfbsGCFiles = new ArrayList<GCorMapabilityFile>();
		List<GCorMapabilityFile> listofTopTenMostVaryingHistoneGCFiles = new ArrayList<GCorMapabilityFile>();
		
		fill(listofTopTenMostVaryingDnaseGCFiles,Commons.TOP_TEN_MOST_VARYING_DNASE_GC_FILES);
		fill(listofTopTenMostVaryingTfbsGCFiles,Commons.TOP_TEN_MOST_VARYING_TFBS_GC_FILES);
		fill(listofTopTenMostVaryingHistoneGCFiles,Commons.TOP_TEN_MOST_VARYING_HISTONE_GC_FILES);
		
		createTabDelimitedTextFileforBoxPlotinR(dnaseGCDirectory,dnaseGCBoxPlotinRFileName,listofTopTenMostVaryingDnaseGCFiles,Commons.WRITE_STANDARD_DEVIATION_VALUE_OF_EACH_FILE);
		createTabDelimitedTextFileforBoxPlotinR(tfbsGCDirectory,tfbsGCBoxPlotinRFileName,listofTopTenMostVaryingTfbsGCFiles,Commons.WRITE_STANDARD_DEVIATION_VALUE_OF_EACH_FILE);
		createTabDelimitedTextFileforBoxPlotinR(histoneGCDirectory,histoneGCBoxPlotinRFileName,listofTopTenMostVaryingHistoneGCFiles,Commons.WRITE_STANDARD_DEVIATION_VALUE_OF_EACH_FILE);
		
		//MAPABILITY
		//Create tab delimited text file for MAPABILITY values of  top ten most varying
		File dnaseMapabilityDirectory = new File(allDnaseMapabilityFilesDirectory);
		File tfbsMapabilityDirectory = new File(allTfbsMapabilityFilesDirectory);
		File histoneMapabilityDirectory = new File(allHistoneMapabilityFilesDirectory);
		
		List<GCorMapabilityFile> listofTopTenMostVaryingDnaseMapabilityFiles = new ArrayList<GCorMapabilityFile>();
		List<GCorMapabilityFile> listofTopTenMostVaryingTfbsMapabilityFiles = new ArrayList<GCorMapabilityFile>();
		List<GCorMapabilityFile> listofTopTenMostVaryingHistoneMapabilityFiles = new ArrayList<GCorMapabilityFile>();
		
		fill(listofTopTenMostVaryingDnaseMapabilityFiles,Commons.TOP_TEN_MOST_VARYING_DNASE_MAPABILITY_FILES);
		fill(listofTopTenMostVaryingTfbsMapabilityFiles,Commons.TOP_TEN_MOST_VARYING_TFBS_MAPABILITY_FILES);
		fill(listofTopTenMostVaryingHistoneMapabilityFiles,Commons.TOP_TEN_MOST_VARYING_HISTONE_MAPABILITY_FILES);
		
		createTabDelimitedTextFileforBoxPlotinR(dnaseMapabilityDirectory,dnaseMapabilityBoxPlotinRFileName,listofTopTenMostVaryingDnaseMapabilityFiles,Commons.WRITE_STANDARD_DEVIATION_VALUE_OF_EACH_FILE);
		createTabDelimitedTextFileforBoxPlotinR(tfbsMapabilityDirectory,tfbsMapabilityBoxPlotinRFileName,listofTopTenMostVaryingTfbsMapabilityFiles,Commons.WRITE_STANDARD_DEVIATION_VALUE_OF_EACH_FILE);
		createTabDelimitedTextFileforBoxPlotinR(histoneMapabilityDirectory,histoneMapabilityBoxPlotinRFileName,listofTopTenMostVaryingHistoneMapabilityFiles,Commons.WRITE_STANDARD_DEVIATION_VALUE_OF_EACH_FILE);

	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//For All Files
		createTabDelimitedTextFileForAllFiles();
		
		//For Top Ten Standard Deviation Files
		createTabDelimitedTextFileForTopTenVaryingFiles();
		
		//For Ten Files with Different Means
		createTabDelimitedTextFileForTenDifferentMeanFiles();
		
		
				
	}

}
