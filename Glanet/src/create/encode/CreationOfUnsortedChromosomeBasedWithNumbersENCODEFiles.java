/**
 * Unsorted
 * ChromosomeBased
 * WithNumbers
 * ENCODE Files
 */
package create.encode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import enumtypes.ElementType;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

/**
 * @author Burcak Otlu
 * @date Nov 17, 2014
 * @project Glanet
 *
 */
public class CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles {

	public static String getCellLineName(String fileName) {
		// example fileName
		// It seems that there are five different kinds of labs
		// SM_SFMDukeDNaseSeq.pk
		// hESCT0-DS13133.peaks.fdr0.01.hg19.bed
		// idrPool.GM12878-DS9432-DS10671.z_OV_GM12878-DS10671.z_VS_GM12878-DS9432.z.npk2.narrowPeak
		// idrPool.GM12878_FAIRE_BP_TP_peaks_OV_GM12878_B1_peaks_VS_UncFAIRE_GM12878_B2_peaks.npk2.narrowPeak
		// idrPool.GM12878_DNaseHS_BP_TP_P_peaks_OV_DukeDNase_GM12878_B4_peaks_VS_DukeDNase_GM12878_B5_peaks.npk2.narrowPeak

		int start = 0;
		int indexofDukeDnase = fileName.indexOf("DukeDNase");
		int indexof_DS = fileName.indexOf("-DS");
		int indexof_Dnase = fileName.indexOf("_DNase");
		int indexof_FAIRE = fileName.indexOf("_FAIRE");
		int indexofIdrPool = fileName.indexOf("idrPool");

		String cellLineDnase = null;

		if (indexofDukeDnase >= 0) {

			if (indexof_Dnase >= 0) {
				if (indexofIdrPool >= 0) {
					start = start + "idrPool.".length();
					cellLineDnase = fileName.substring(start, indexof_Dnase).toUpperCase(Locale.ENGLISH);
				} else {
					System.out.println("Unknown Lab: " + fileName);
				}
			} else {
				cellLineDnase = fileName.substring(start, indexofDukeDnase).toUpperCase(Locale.ENGLISH);
			}
		} else if (indexof_DS >= 0) {
			if (indexofIdrPool < 0) {
				cellLineDnase = fileName.substring(start, indexof_DS).toUpperCase(Locale.ENGLISH);
			} else if (indexofIdrPool >= 0) {
				start = start + "idrPool.".length();
				cellLineDnase = fileName.substring(start, indexof_DS).toUpperCase(Locale.ENGLISH);
			} else {
				System.out.println("Unknown Lab: " + fileName);
			}
		} else if (indexof_Dnase >= 0) {
			if (indexofIdrPool >= 0) {
				start = start + "idrPool.".length();
				cellLineDnase = fileName.substring(start, indexof_Dnase).toUpperCase(Locale.ENGLISH);
			} else {
				System.out.println("Unknown Lab: " + fileName);
			}
		} else if (indexof_FAIRE >= 0) {
			if (indexofIdrPool >= 0) {
				start = start + "idrPool.".length();
				cellLineDnase = fileName.substring(start, indexof_FAIRE).toUpperCase(Locale.ENGLISH);
			} else {
				System.out.println("Unknown Lab: " + fileName);
			}

		} else {
			System.out.println("Unknown Lab: " + fileName);
		}

		return cellLineDnase;
	}

	public static void getCellLineNameandHistoneName(CellLineHistone cellLineHistone, String fileName) {
		// example fileName
		// It seems that there are three different kinds of labs
		// wgEncodeUwHistoneNhekH3k4me3StdAln.narrowPeak
		// wgEncodeSydhHistoneU2osH3k9me3UcdAln.narrowPeak
		// wgEncodeBroadHistoneOsteoblH3k9me3StdAln.narrowPeak

		int uwHistoneStart = fileName.indexOf("UwHistone");
		int sydhHistoneStart = fileName.indexOf("SydhHistone");
		int broadHistoneStart = fileName.indexOf("BroadHistone");

		int start = 0;
		int cellLineNameStart = 0;
		int histoneNameStart = 0;
		int laborProtocolNameStart = 0;

		if (uwHistoneStart >= 0) {
			start = uwHistoneStart + "UwHistone".length();
		} else if (sydhHistoneStart >= 0) {
			start = sydhHistoneStart + "SydhHistone".length();
		} else if (broadHistoneStart >= 0) {
			start = broadHistoneStart + "BroadHistone".length();
		} else {
			GlanetRunner.appendLog("Unknown Lab in  Histone Files");
		}

		// Find the First, Second and Third Upper Case Letters in filename after
		// the lab name
		for (int i = start; i < fileName.length(); i++) {
			if (Character.isUpperCase(fileName.charAt(i))) {
				cellLineNameStart = i;
				break;
			}
		}

		for (int i = cellLineNameStart + 1; i < fileName.length(); i++) {
			if (Character.isUpperCase(fileName.charAt(i))) {
				histoneNameStart = i;
				break;
			}
		}

		for (int i = histoneNameStart + 1; i < fileName.length(); i++) {
			if (Character.isUpperCase(fileName.charAt(i))) {
				laborProtocolNameStart = i;
				break;
			}
		}

		cellLineHistone.setCellLineName(fileName.substring(cellLineNameStart, histoneNameStart).toUpperCase(Locale.ENGLISH));
		cellLineHistone.setHistoneName(fileName.substring(histoneNameStart, laborProtocolNameStart).toUpperCase(Locale.ENGLISH));
		cellLineHistone.setFileName(fileName);

	}

	public static void getCellLineNameandTranscriptionFactorName(CellLineTranscriptionFactor cellLineandTranscriptionFactor, String fileName) {
		// example fileName
		// It seems that there are three kind of fileName classes
		// spp.optimal.wgEncodeBroadHistoneGm12878CtcfStdAlnRep0_VS_wgEncodeBroadHistoneGm12878ControlStdAlnRep0.narrowPeak
		// spp.optimal.wgEncodeOpenChromChipFibroblCtcfAlnRep0_VS_wgEncodeOpenChromChipFibroblInputAln.narrowPeak
		// spp.optimal.wgEncodeSydhTfbsK562Pol2IggmusAlnRep0_VS_wgEncodeSydhTfbsK562InputIggmusAlnRep0.narrowPeak

		int tfbsStart = fileName.indexOf("Tfbs");
		int openChromChipStart = fileName.indexOf("OpenChromChip");
		int broadHistoneStart = fileName.indexOf("BroadHistone");

		int start = 0;

		int cellLineNameStart = 0;
		int transcriptionFactorNameStart = 0;
		int laborProtocolNameStart = 0;

		if (tfbsStart >= 0) {
			// Tfbs is 4 char long
			start = tfbsStart + "Tfbs".length();
		} else if (openChromChipStart >= 0) {
			// OpenChromChip
			start = openChromChipStart + "OpenChromChip".length();
		} else if (broadHistoneStart >= 0) {
			// BroadHistone
			start = broadHistoneStart + "BroadHistone".length();
		} else {
			GlanetRunner.appendLog("Unknown Lab in TFBS file");
		}

		// Find the First, Second and Third Upper Case Letters in filename after
		// the lab name

		for (int i = start; i < fileName.length(); i++) {
			if (Character.isUpperCase(fileName.charAt(i))) {
				cellLineNameStart = i;
				break;
			}
		}

		for (int i = cellLineNameStart + 1; i < fileName.length(); i++) {
			if (Character.isUpperCase(fileName.charAt(i))) {
				transcriptionFactorNameStart = i;
				break;
			}
		}

		for (int i = transcriptionFactorNameStart + 1; i < fileName.length(); i++) {
			if (Character.isUpperCase(fileName.charAt(i))) {
				laborProtocolNameStart = i;
				break;
			}
		}

		cellLineandTranscriptionFactor.setCellLineName(fileName.substring(cellLineNameStart, transcriptionFactorNameStart).toUpperCase(Locale.ENGLISH));
		cellLineandTranscriptionFactor.setTranscriptionFactorName(fileName.substring(transcriptionFactorNameStart, laborProtocolNameStart).toUpperCase(Locale.ENGLISH));
		cellLineandTranscriptionFactor.setFileName(fileName);

	}

	public static void readEncodeFilesWriteUnsortedChromBasedFilesWithNumbers(
			File directory, 
			ElementType elementType, 
			List<BufferedWriter> bufferedWriterList, 
			TObjectIntMap<String> tforHistoneName2NumberMap, 
			TIntObjectMap<String> tforHistoneNumber2NameMap, 
			TObjectIntMap<String> tforHistoneCellLineName2NumberMap, 
			TIntObjectMap<String> tforHistoneCellLineNumber2NameMap, 
			TObjectIntMap<String> cellLineName2NumberMap, 
			TIntObjectMap<String> cellLineNumber2NameMap, 
			TObjectIntMap<String> encodeFileName2NumberMap, 
			TIntObjectMap<String> encodeFileNumber2NameMap, 
			ENCODENumbers encodeNumbers) {

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		// int indexofFourthTab=0;
		// int indexofFifthTab=0;
		// int indexofSixthTab=0;
		// int indexofSeventhTab=0;
		// int indexofEigthTab=0;
		// int indexofNinethTab=0;

		int start;
		int end;
		int endInclusive;
		String chromosomeName;

		BufferedWriter bufferedWriter = null;

		int tfNumber = encodeNumbers.getTfNumber();
		int tfCellLineNumber = encodeNumbers.getTfCellLineNumber();
		int histoneNumber = encodeNumbers.getHistoneNumber();
		int histoneCellLineNumber = encodeNumbers.getHistoneCellLineNumber();
		int cellLineNumber = encodeNumbers.getCellLineNumber();
		int fileNumber = encodeNumbers.getFileNumber();

		// Initialization
		String cellLine = null;
		String tforHistone = null;
		String tforHistoneCellLine = null;

		if (!directory.exists()) {
			GlanetRunner.appendLog("No File/Dir" + directory.getName());
		}

		// Reading directory contents
		if (directory.isDirectory()) {// a directory!

			File[] files = directory.listFiles();
			int numberofFiles = files.length;

			System.out.printf("Number of " + elementType.convertEnumtoString() + " Files %d in %s" + System.getProperty("line.separator"), files.length, directory.getAbsolutePath());

			for (int i = 0; i < numberofFiles; i++) {

				FileReader fileReader = null;
				BufferedReader bufferedReader = null;

				if (files[i].isFile()) {

					// Eead the content of each file
					File file = files[i];

					String fileName = file.getName();
					String filePath = file.getPath();

					// Open the file that is the first
					try {

						switch (elementType) {

							case TF:
								CellLineTranscriptionFactor cellLineandTranscriptionFactorName = new CellLineTranscriptionFactor();
								// Get the cell line name and transcription
								// factor name from file name
								getCellLineNameandTranscriptionFactorName(cellLineandTranscriptionFactorName, fileName);

								tforHistone = cellLineandTranscriptionFactorName.getTranscriptionFactorName();
								cellLine = cellLineandTranscriptionFactorName.getCellLineName();

								tforHistoneCellLine = tforHistone + Commons.UNDERSCORE + cellLine;

								if (!tforHistoneName2NumberMap.containsKey(tforHistone)) {
									tforHistoneName2NumberMap.put(tforHistone, tfNumber);
									tforHistoneNumber2NameMap.put(tfNumber, tforHistone);
									tfNumber++;
								}

								if (!tforHistoneCellLineName2NumberMap.containsKey(tforHistoneCellLine)) {
									tforHistoneCellLineName2NumberMap.put(tforHistoneCellLine, tfCellLineNumber);
									tforHistoneCellLineNumber2NameMap.put(tfCellLineNumber, tforHistoneCellLine);
									tfCellLineNumber++;
								}

								cellLineandTranscriptionFactorName = null;
								break;

							case HISTONE:
								CellLineHistone cellLineNameHistoneName = new CellLineHistone();
								// Get the cell line name and histone name from
								// file name
								getCellLineNameandHistoneName(cellLineNameHistoneName, fileName);

								tforHistone = cellLineNameHistoneName.getHistoneName();
								cellLine = cellLineNameHistoneName.getCellLineName();

								tforHistoneCellLine = tforHistone + Commons.UNDERSCORE + cellLine;

								if (!tforHistoneName2NumberMap.containsKey(tforHistone)) {
									tforHistoneName2NumberMap.put(tforHistone, histoneNumber);
									tforHistoneNumber2NameMap.put(histoneNumber, tforHistone);
									histoneNumber++;
								}

								if (!tforHistoneCellLineName2NumberMap.containsKey(tforHistoneCellLine)) {
									tforHistoneCellLineName2NumberMap.put(tforHistoneCellLine, histoneCellLineNumber);
									tforHistoneCellLineNumber2NameMap.put(histoneCellLineNumber, tforHistoneCellLine);
									histoneCellLineNumber++;
								}

								cellLineNameHistoneName = null;
								break;

							default:
								break;

						}// End of switch

						if (!cellLineName2NumberMap.containsKey(cellLine)) {
							cellLineName2NumberMap.put(cellLine, cellLineNumber);
							cellLineNumber2NameMap.put(cellLineNumber, cellLine);
							cellLineNumber++;
						}

						if (!encodeFileName2NumberMap.containsKey(fileName)) {
							encodeFileName2NumberMap.put(fileName, fileNumber);
							encodeFileNumber2NameMap.put(fileNumber,fileName);
							fileNumber++;
						}

						fileReader = new FileReader(filePath);
						bufferedReader = new BufferedReader(fileReader);

						String strLine;

						try {

							while ((strLine = bufferedReader.readLine()) != null) {

								// example line
								// chr11 189626 190350 . 0 . 1501.49835704239 -1
								// 4.68899790310631 511
								indexofFirstTab = strLine.indexOf('\t');
								indexofSecondTab = (indexofFirstTab > 0) ? strLine.indexOf('\t', indexofFirstTab + 1) : -1;
								indexofThirdTab = (indexofSecondTab > 0) ? strLine.indexOf('\t', indexofSecondTab + 1) : -1;
								// indexofFourthTab = (indexofThirdTab>0) ?
								// strLine.indexOf('\t',indexofThirdTab+1) : -1;
								// indexofFifthTab = (indexofFourthTab>0) ?
								// strLine.indexOf('\t',indexofFourthTab+1): -1;
								// indexofSixthTab = (indexofFifthTab>0) ?
								// strLine.indexOf('\t',indexofFifthTab+1) : -1;
								// indexofSeventhTab = (indexofSixthTab>0) ?
								// strLine.indexOf('\t',indexofSixthTab+1) : -1;
								// indexofEigthTab = (indexofSeventhTab>0) ?
								// strLine.indexOf('\t',indexofSeventhTab+1):
								// -1;
								// indexofNinethTab = (indexofEigthTab>0) ?
								// strLine.indexOf('\t',indexofEigthTab+1) : -1;

								chromosomeName = strLine.substring(0, indexofFirstTab);
								start = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));
								end = Integer.parseInt(strLine.substring(indexofSecondTab + 1, indexofThirdTab));

								// Encode data
								// 0-based
								// start
								// end exclusive
								// GLANET convention is
								// 0-based
								// start
								// end
								endInclusive = end - 1;

								// Write to Unsorted Chromosome Based files with
								// numbers
								bufferedWriter = FileOperations.getChromosomeBasedBufferedWriter(ChromosomeName.convertStringtoEnum(chromosomeName), bufferedWriterList);
								bufferedWriter.write(chromosomeName + "\t" + start + "\t" + endInclusive + "\t" + tforHistoneName2NumberMap.get(tforHistone) + "\t" + cellLineName2NumberMap.get(cellLine) + "\t" + encodeFileName2NumberMap.get(fileName) + System.getProperty("line.separator"));

							}// End of while

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// Close the Buffered Reader
						bufferedReader.close();

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}// Check for each file and read each file
			} // End of For -----reading each file in the directory

		} // End of if this is a directory

		encodeNumbers.setTfNumber(tfNumber);
		encodeNumbers.setTfCellLineNumber(tfCellLineNumber);

		encodeNumbers.setHistoneNumber(histoneNumber);
		encodeNumbers.setHistoneCellLineNumber(histoneCellLineNumber);

		encodeNumbers.setCellLineNumber(cellLineNumber);
		encodeNumbers.setFileNumber(fileNumber);

	}

	// Version for DNASE
	public static void readEncodeFilesWriteUnsortedChromBasedFilesWithNumbers(
			File directory, 
			ElementType elementType, 
			List<BufferedWriter> bufferedWriterList, 
			TObjectIntMap<String> encodeDnaseCellLineName2NumberMap, 
			TIntObjectMap<String> encodeDnaseCellLineNumber2NameMap, 
			TObjectIntMap<String> encodeCellLineName2NumberMap, 
			TIntObjectMap<String> encodeCellLineNumber2NameMap, 
			TObjectIntMap<String> encodeFileName2NumberMap, 
			TIntObjectMap<String> encodeFileNumber2NameMap, 
			ENCODENumbers encodeNumbers) {

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		// int indexofFourthTab=0;
		// int indexofFifthTab=0;
		// int indexofSixthTab=0;
		// int indexofSeventhTab=0;
		// int indexofEigthTab=0;
		// int indexofNinethTab=0;

		int start;
		int end;
		int endInclusive;
		String chromosomeName;

		BufferedWriter bufferedWriter = null;

		int dnaseCellLineNumber = encodeNumbers.getDnaseCellLineNumber();
		int cellLineNumber = encodeNumbers.getCellLineNumber();
		int fileNumber = encodeNumbers.getFileNumber();

		// Initialization
		String cellLineDnase = null;

		if (!directory.exists()) {
			GlanetRunner.appendLog("No File/Dir" + directory.getName());
		}

		// Reading directory contents
		if (directory.isDirectory()) {// a directory!

			File[] files = directory.listFiles();
			int numberofFiles = files.length;

			System.out.printf("Number of " + elementType.convertEnumtoString() + " Files %d in %s" + System.getProperty("line.separator"), files.length, directory.getAbsolutePath());

			for (int i = 0; i < numberofFiles; i++) {

				FileReader fileReader = null;
				BufferedReader bufferedReader = null;

				if (files[i].isFile()) {

					// Eead the content of each file
					File file = files[i];

					String fileName = file.getName();
					String filePath = file.getPath();

					// Open the file that is the first
					try {
						// Get the cell line name from file name
						cellLineDnase = getCellLineName(fileName);

						if (!encodeDnaseCellLineName2NumberMap.containsKey(cellLineDnase)) {
							encodeDnaseCellLineName2NumberMap.put(cellLineDnase, dnaseCellLineNumber);
							encodeDnaseCellLineNumber2NameMap.put(dnaseCellLineNumber,cellLineDnase);
							dnaseCellLineNumber++;
						}

						if (!encodeCellLineName2NumberMap.containsKey(cellLineDnase)) {
							encodeCellLineName2NumberMap.put(cellLineDnase,cellLineNumber);
							encodeCellLineNumber2NameMap.put(cellLineNumber,cellLineDnase);
							cellLineNumber++;
						}

						if (!encodeFileName2NumberMap.containsKey(fileName)) {
							encodeFileName2NumberMap.put(fileName,fileNumber);
							encodeFileNumber2NameMap.put(fileNumber,fileName);
							fileNumber++;
						}

						fileReader = new FileReader(filePath);
						bufferedReader = new BufferedReader(fileReader);

						String strLine;

						try {

							while ((strLine = bufferedReader.readLine()) != null) {

								// example line
								// chr1 91852781 91853156 1 1 . 1728.25 7.867927
								// 7.867927 187

								indexofFirstTab = strLine.indexOf('\t');
								indexofSecondTab = (indexofFirstTab > 0) ? strLine.indexOf('\t', indexofFirstTab + 1) : -1;
								indexofThirdTab = (indexofSecondTab > 0) ? strLine.indexOf('\t', indexofSecondTab + 1) : -1;
								// indexofFourthTab = (indexofThirdTab>0) ?
								// strLine.indexOf('\t',indexofThirdTab+1) : -1;
								// indexofFifthTab = (indexofFourthTab>0) ?
								// strLine.indexOf('\t',indexofFourthTab+1): -1;
								// indexofSixthTab = (indexofFifthTab>0) ?
								// strLine.indexOf('\t',indexofFifthTab+1) : -1;
								// indexofSeventhTab = (indexofSixthTab>0) ?
								// strLine.indexOf('\t',indexofSixthTab+1) : -1;
								// indexofEigthTab = (indexofSeventhTab>0) ?
								// strLine.indexOf('\t',indexofSeventhTab+1):
								// -1;
								// indexofNinethTab = (indexofEigthTab>0) ?
								// strLine.indexOf('\t',indexofEigthTab+1) : -1;

								chromosomeName = strLine.substring(0, indexofFirstTab);
								start = Integer.parseInt(strLine.substring(indexofFirstTab + 1, indexofSecondTab));
								end = Integer.parseInt(strLine.substring(indexofSecondTab + 1, indexofThirdTab));

								// Encode data
								// start 0-based inclusive
								// end exclusive
								// GLANET convention is
								// start 0-based inclusive
								// end 0-based inclusive
								endInclusive = end - 1;

								// Write to Unsorted Chromosome Based files with
								// numbers
								bufferedWriter = FileOperations.getChromosomeBasedBufferedWriter(ChromosomeName.convertStringtoEnum(chromosomeName), bufferedWriterList);
								bufferedWriter.write(chromosomeName + "\t" + start + "\t" + endInclusive + "\t" + encodeDnaseCellLineName2NumberMap.get(cellLineDnase)  + "\t" + encodeFileName2NumberMap.get(fileName) + System.getProperty("line.separator"));

								
								
							}// End of while

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// Close the Buffered Reader
						bufferedReader.close();

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}// Check for each file and read each file
			} // End of For -----reading each file in the directory

		} // End of if this is a directory

		encodeNumbers.setDnaseCellLineNumber(dnaseCellLineNumber);
		encodeNumbers.setCellLineNumber(cellLineNumber);
		encodeNumbers.setFileNumber(fileNumber);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Number2NameMap is needed for all possible name files sorted w.r.t. number.
		//Name2NumberMap is needed for writing chromosome based files with numbers.

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty("file.separator");

		File dnaseDir1 = new File(Commons.LOCAL_DISK_G_GLANET_DATA + common.Commons.ENCODE_DNASE_DIRECTORY1);
		File dnaseDir2 = new File(Commons.LOCAL_DISK_G_GLANET_DATA + common.Commons.ENCODE_DNASE_DIRECTORY2);
		File tfDir = new File(Commons.LOCAL_DISK_G_GLANET_DATA + common.Commons.ENCODE_TFBS_DIRECTORY);
		File histoneDir = new File(Commons.LOCAL_DISK_G_GLANET_DATA + common.Commons.ENCODE_HISTONE_DIRECTORY);

		List<BufferedWriter> ENCODEDnaseBufferedWriterList = new ArrayList<BufferedWriter>();
		List<BufferedWriter> ENCODETFBufferedWriterList = new ArrayList<BufferedWriter>();
		List<BufferedWriter> ENCODEHistoneBufferedWriterList = new ArrayList<BufferedWriter>();

		// ENCODE DNASE CELLLINE NAME2NUMBER
		// ENCODE DNASE CELLLINE NUMBER2NAME
		TObjectIntMap<String> encodeDnaseCellLineName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> encodeDnaseCellLineNumber2NameMap = new TIntObjectHashMap<String>();
		
		// ENCODE CELLLINE NAME2NUMBER
		// ENCODE CELLLINE NUMBER2NAME
		TObjectIntMap<String> encodeCellLineName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> encodeCellLineNumber2NameMap = new TIntObjectHashMap<String>();

		// ENCODE FILE NAME2NUMBER
		// ENCODE FILE NUMBER2NAME
		TObjectIntMap<String> encodeFileName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> encodeFileNumber2NameMap = new TIntObjectHashMap<String>();

		// ENCODE TF NAME2NUMBER
		// ENCODE TF NUMBER2NAME
		TObjectIntMap<String> encodeTFName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> encodeTFNumber2NameMap = new TIntObjectHashMap<String>();

		// ENCODE TF CELLLINE NAME2NUMBER
		// ENCODE TF CELLLINE NUMBER2NAME
		TObjectIntMap<String> encodeTFCellLineName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> encodeTFCellLineNumber2NameMap = new TIntObjectHashMap<String>();

		// ENCODE Histone NAME2NUMBER
		// ENCODE Histone NUMBER2NAME
		TObjectIntMap<String> encodeHistoneName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> encodeHistoneNumber2NameMap = new TIntObjectHashMap<String>();

		// ENCODE Histone CELLLINE NAME2NUMBER
		// ENCODE Histone CELLLINE NUMBER2NAME
		TObjectIntMap<String> encodeHistoneCellLineName2NumberMap = new TObjectIntHashMap<String>();
		TIntObjectMap<String> encodeHistoneCellLineNumber2NameMap = new TIntObjectHashMap<String>();

		ENCODENumbers encodeNumbers = new ENCODENumbers();

		// Create BufferedWriters
		FileOperations.createUnsortedChromosomeBasedWithNumbersBufferedWriters(dataFolder, ElementType.DNASE, ENCODEDnaseBufferedWriterList);
		FileOperations.createUnsortedChromosomeBasedWithNumbersBufferedWriters(dataFolder, ElementType.TF, ENCODETFBufferedWriterList);
		FileOperations.createUnsortedChromosomeBasedWithNumbersBufferedWriters(dataFolder, ElementType.HISTONE, ENCODEHistoneBufferedWriterList);

		// Version for DNASE
		readEncodeFilesWriteUnsortedChromBasedFilesWithNumbers(dnaseDir1, ElementType.DNASE, ENCODEDnaseBufferedWriterList, encodeDnaseCellLineName2NumberMap,encodeDnaseCellLineNumber2NameMap, encodeCellLineName2NumberMap,encodeCellLineNumber2NameMap, encodeFileName2NumberMap,encodeFileNumber2NameMap, encodeNumbers);
		readEncodeFilesWriteUnsortedChromBasedFilesWithNumbers(dnaseDir2, ElementType.DNASE, ENCODEDnaseBufferedWriterList, encodeDnaseCellLineName2NumberMap,encodeDnaseCellLineNumber2NameMap, encodeCellLineName2NumberMap,encodeCellLineNumber2NameMap, encodeFileName2NumberMap,encodeFileNumber2NameMap, encodeNumbers);

		// Version for TF and HISTONE
		readEncodeFilesWriteUnsortedChromBasedFilesWithNumbers(
				tfDir, 
				ElementType.TF, 
				ENCODETFBufferedWriterList, 
				encodeTFName2NumberMap, 
				encodeTFNumber2NameMap,
				encodeTFCellLineName2NumberMap, 
				encodeTFCellLineNumber2NameMap,
				encodeCellLineName2NumberMap, 
				encodeCellLineNumber2NameMap,  
				encodeFileName2NumberMap, 
				encodeFileNumber2NameMap,
				encodeNumbers);
		
		readEncodeFilesWriteUnsortedChromBasedFilesWithNumbers(
				histoneDir, 
				ElementType.HISTONE, 
				ENCODEHistoneBufferedWriterList, 
				encodeHistoneName2NumberMap, 
				encodeHistoneNumber2NameMap, 
				encodeHistoneCellLineName2NumberMap, 
				encodeHistoneCellLineNumber2NameMap,
				encodeCellLineName2NumberMap, 
				encodeCellLineNumber2NameMap,
				encodeFileName2NumberMap, 
				encodeFileNumber2NameMap,
				encodeNumbers);

		// Write ENCODE DNASE TF HISTONE
		// Write number2name manner
		// Annotation requires number2name manner
		FileOperations.writeSortedNumber2NameMap(dataFolder, encodeDnaseCellLineNumber2NameMap, Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_DNASE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.writeSortedNumber2NameMap(dataFolder, encodeCellLineNumber2NameMap, Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.writeSortedNumber2NameMap(dataFolder, encodeTFNumber2NameMap, Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_TF_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.writeSortedNumber2NameMap(dataFolder, encodeTFCellLineNumber2NameMap, Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_TF_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.writeSortedNumber2NameMap(dataFolder, encodeHistoneNumber2NameMap, Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_HISTONE_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.writeSortedNumber2NameMap(dataFolder, encodeHistoneCellLineNumber2NameMap, Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_HISTONE_CELLLINE_NUMBER_2_NAME_OUTPUT_FILENAME);
		FileOperations.writeSortedNumber2NameMap(dataFolder, encodeFileNumber2NameMap, Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_FILE_NUMBER_2_NAME_OUTPUT_FILENAME);

		// Write name
		FileOperations.writeName(dataFolder, encodeCellLineNumber2NameMap, Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME, Commons.ALL_POSSIBLE_ENCODE_CELL_LINES_NAMES_FILENAME);

		// Close BufferedWriters
		FileOperations.closeChromosomeBasedBufferedWriters(ENCODEDnaseBufferedWriterList);
		FileOperations.closeChromosomeBasedBufferedWriters(ENCODETFBufferedWriterList);
		FileOperations.closeChromosomeBasedBufferedWriters(ENCODEHistoneBufferedWriterList);
	}

}
