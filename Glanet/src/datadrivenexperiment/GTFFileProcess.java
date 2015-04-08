/**
 * 
 */
package datadrivenexperiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Burçak Otlu
 * @date Apr 8, 2015
 * @project Glanet 
 *
 */
public class GTFFileProcess {
	
	
	public static void reafGFTFile(String gtfFileNameWithPath){
		
		String strLine = null;
		String attribute = null;
		String geneID = null;
		
		float RPKM1;
		float RPKM2;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		
		int indexofSemiColon;
		
		
		try {
			FileReader fileReader =  FileOperations.createFileReader(gtfFileNameWithPath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			//chr14	gencodeV7	exon	105886353	105886400	0.002545	+	.	gene_ids "ENSG00000182979.10,"; transcript_ids "ENST00000438610.1,"; RPKM1 "0.028016"; RPKM2 "0.038508"; iIDR "0.193";

			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = (indexofFirstTab>0)?strLine.indexOf('\t',indexofFirstTab+1): -1;
				indexofThirdTab = (indexofSecondTab>0)?strLine.indexOf('\t',indexofSecondTab+1): -1;
				indexofFourthTab = (indexofThirdTab>0)?strLine.indexOf('\t',indexofThirdTab+1): -1;
				indexofFifthTab = (indexofFourthTab>0)?strLine.indexOf('\t',indexofFourthTab+1): -1;
				indexofSixthTab = (indexofFifthTab>0)?strLine.indexOf('\t',indexofFifthTab+1): -1;
				indexofSeventhTab = (indexofSixthTab>0)?strLine.indexOf('\t',indexofSixthTab+1): -1;
				indexofEigthTab = (indexofSeventhTab>0)?strLine.indexOf('\t',indexofSeventhTab+1): -1;
				
				attribute = strLine.substring(indexofEigthTab+1);
				
				indexofSemiColon = strLine.indexOf(';');
				
				geneID = strLine.substring(indexofEigthTab+1,indexofSemiColon);
				
			}//End of While
			
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	public static void main(String[] args) {

		//Get .gtf file
		String gtfFileName = Commons.LOCAL_DISK_G_GLANET_DATA + Commons.ENCODE_EXPERIMENT + System.getProperty("file.separator") + Commons.ENCSR000COQ + System.getProperty("file.separator") + Commons.GM12878_cell_longPolyA_CSHL_ExonGencV7_CSHL_LID16629_003WC_b1_LID16630_004WC_b2_gff; 

		//Read gtf file
		reafGFTFile(gtfFileName);
	}

}
