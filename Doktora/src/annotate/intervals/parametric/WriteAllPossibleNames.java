/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package annotate.intervals.parametric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.Commons;


public class WriteAllPossibleNames {
	
	public void closeBufferedReaders(List<BufferedReader> bufferedReaderList){
		try {
			for(int i = 0; i<bufferedReaderList.size(); i++){			
					bufferedReaderList.get(i).close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	
	public void createChromBaseDnaseBufferedReaders(List<BufferedReader> bufferedReaderList){
		
		try {
			FileReader fileReader1 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_DNASE);
			FileReader fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_DNASE);
			FileReader fileReader3 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_DNASE);
			FileReader fileReader4 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_DNASE);
			FileReader fileReader5 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_DNASE);
			FileReader fileReader6 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_DNASE);
			FileReader fileReader7 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_DNASE);
			FileReader fileReader8 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_DNASE);
			FileReader fileReader9 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_DNASE);
			FileReader fileReader10 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_DNASE);
			FileReader fileReader11 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_DNASE);
			FileReader fileReader12 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_DNASE);
			FileReader fileReader13 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_DNASE);
			FileReader fileReader14 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_DNASE);
			FileReader fileReader15 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_DNASE);
			FileReader fileReader16 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_DNASE);
			FileReader fileReader17 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_DNASE);
			FileReader fileReader18 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_DNASE);
			FileReader fileReader19 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_DNASE);
			FileReader fileReader20 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_DNASE);
			FileReader fileReader21 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_DNASE);
			FileReader fileReader22 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_DNASE);
			FileReader fileReader23 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_DNASE);
			FileReader fileReader24 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_DNASE);
			
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
			BufferedReader bufferedReader4 = new BufferedReader(fileReader4);
			BufferedReader bufferedReader5 = new BufferedReader(fileReader5);
			BufferedReader bufferedReader6 = new BufferedReader(fileReader6);
			BufferedReader bufferedReader7 = new BufferedReader(fileReader7);
			BufferedReader bufferedReader8 = new BufferedReader(fileReader8);
			BufferedReader bufferedReader9 = new BufferedReader(fileReader9);
			BufferedReader bufferedReader10 = new BufferedReader(fileReader10);
			BufferedReader bufferedReader11 = new BufferedReader(fileReader11);
			BufferedReader bufferedReader12 = new BufferedReader(fileReader12);
			BufferedReader bufferedReader13 = new BufferedReader(fileReader13);
			BufferedReader bufferedReader14 = new BufferedReader(fileReader14);
			BufferedReader bufferedReader15 = new BufferedReader(fileReader15);
			BufferedReader bufferedReader16 = new BufferedReader(fileReader16);
			BufferedReader bufferedReader17 = new BufferedReader(fileReader17);
			BufferedReader bufferedReader18 = new BufferedReader(fileReader18);
			BufferedReader bufferedReader19 = new BufferedReader(fileReader19);
			BufferedReader bufferedReader20 = new BufferedReader(fileReader20);
			BufferedReader bufferedReader21 = new BufferedReader(fileReader21);
			BufferedReader bufferedReader22 = new BufferedReader(fileReader22);
			BufferedReader bufferedReader23 = new BufferedReader(fileReader23);
			BufferedReader bufferedReader24 = new BufferedReader(fileReader24);
			
			bufferedReaderList.add(bufferedReader1);
			bufferedReaderList.add(bufferedReader2);
			bufferedReaderList.add(bufferedReader3);
			bufferedReaderList.add(bufferedReader4);
			bufferedReaderList.add(bufferedReader5);
			bufferedReaderList.add(bufferedReader6);
			bufferedReaderList.add(bufferedReader7);
			bufferedReaderList.add(bufferedReader8);
			bufferedReaderList.add(bufferedReader9);
			bufferedReaderList.add(bufferedReader10);
			bufferedReaderList.add(bufferedReader11);
			bufferedReaderList.add(bufferedReader12);
			bufferedReaderList.add(bufferedReader13);
			bufferedReaderList.add(bufferedReader14);
			bufferedReaderList.add(bufferedReader15);
			bufferedReaderList.add(bufferedReader16);
			bufferedReaderList.add(bufferedReader17);
			bufferedReaderList.add(bufferedReader18);
			bufferedReaderList.add(bufferedReader19);
			bufferedReaderList.add(bufferedReader20);
			bufferedReaderList.add(bufferedReader21);
			bufferedReaderList.add(bufferedReader22);
			bufferedReaderList.add(bufferedReader23);
			bufferedReaderList.add(bufferedReader24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void createChromBaseTfbsBufferedReaders(List<BufferedReader> bufferedReaderList){

		try {
			FileReader fileReader1 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_TFBS);
			FileReader fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_TFBS);
			FileReader fileReader3 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_TFBS);
			FileReader fileReader4 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_TFBS);
			FileReader fileReader5 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_TFBS);
			FileReader fileReader6 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_TFBS);
			FileReader fileReader7 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_TFBS);
			FileReader fileReader8 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_TFBS);
			FileReader fileReader9 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_TFBS);
			FileReader fileReader10 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_TFBS);
			FileReader fileReader11 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_TFBS);
			FileReader fileReader12 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_TFBS);
			FileReader fileReader13 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_TFBS);
			FileReader fileReader14 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_TFBS);
			FileReader fileReader15 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_TFBS);
			FileReader fileReader16 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_TFBS);
			FileReader fileReader17 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_TFBS);
			FileReader fileReader18 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_TFBS);
			FileReader fileReader19 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_TFBS);
			FileReader fileReader20 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_TFBS);
			FileReader fileReader21 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_TFBS);
			FileReader fileReader22 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_TFBS);
			FileReader fileReader23 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_TFBS);
			FileReader fileReader24 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_TFBS);
			
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
			BufferedReader bufferedReader4 = new BufferedReader(fileReader4);
			BufferedReader bufferedReader5 = new BufferedReader(fileReader5);
			BufferedReader bufferedReader6 = new BufferedReader(fileReader6);
			BufferedReader bufferedReader7 = new BufferedReader(fileReader7);
			BufferedReader bufferedReader8 = new BufferedReader(fileReader8);
			BufferedReader bufferedReader9 = new BufferedReader(fileReader9);
			BufferedReader bufferedReader10 = new BufferedReader(fileReader10);
			BufferedReader bufferedReader11 = new BufferedReader(fileReader11);
			BufferedReader bufferedReader12 = new BufferedReader(fileReader12);
			BufferedReader bufferedReader13 = new BufferedReader(fileReader13);
			BufferedReader bufferedReader14 = new BufferedReader(fileReader14);
			BufferedReader bufferedReader15 = new BufferedReader(fileReader15);
			BufferedReader bufferedReader16 = new BufferedReader(fileReader16);
			BufferedReader bufferedReader17 = new BufferedReader(fileReader17);
			BufferedReader bufferedReader18 = new BufferedReader(fileReader18);
			BufferedReader bufferedReader19 = new BufferedReader(fileReader19);
			BufferedReader bufferedReader20 = new BufferedReader(fileReader20);
			BufferedReader bufferedReader21 = new BufferedReader(fileReader21);
			BufferedReader bufferedReader22 = new BufferedReader(fileReader22);
			BufferedReader bufferedReader23 = new BufferedReader(fileReader23);
			BufferedReader bufferedReader24 = new BufferedReader(fileReader24);
			
			bufferedReaderList.add(bufferedReader1);
			bufferedReaderList.add(bufferedReader2);
			bufferedReaderList.add(bufferedReader3);
			bufferedReaderList.add(bufferedReader4);
			bufferedReaderList.add(bufferedReader5);
			bufferedReaderList.add(bufferedReader6);
			bufferedReaderList.add(bufferedReader7);
			bufferedReaderList.add(bufferedReader8);
			bufferedReaderList.add(bufferedReader9);
			bufferedReaderList.add(bufferedReader10);
			bufferedReaderList.add(bufferedReader11);
			bufferedReaderList.add(bufferedReader12);
			bufferedReaderList.add(bufferedReader13);
			bufferedReaderList.add(bufferedReader14);
			bufferedReaderList.add(bufferedReader15);
			bufferedReaderList.add(bufferedReader16);
			bufferedReaderList.add(bufferedReader17);
			bufferedReaderList.add(bufferedReader18);
			bufferedReaderList.add(bufferedReader19);
			bufferedReaderList.add(bufferedReader20);
			bufferedReaderList.add(bufferedReader21);
			bufferedReaderList.add(bufferedReader22);
			bufferedReaderList.add(bufferedReader23);
			bufferedReaderList.add(bufferedReader24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void createChromBaseHistoneBufferedReaders(List<BufferedReader> bufferedReaderList){

		try {
			FileReader fileReader1 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_HISTONE);
			FileReader fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_HISTONE);
			FileReader fileReader3 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_HISTONE);
			FileReader fileReader4 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_HISTONE);
			FileReader fileReader5 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_HISTONE);
			FileReader fileReader6 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_HISTONE);
			FileReader fileReader7 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_HISTONE);
			FileReader fileReader8 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_HISTONE);
			FileReader fileReader9 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_HISTONE);
			FileReader fileReader10 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_HISTONE);
			FileReader fileReader11 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_HISTONE);
			FileReader fileReader12 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_HISTONE);
			FileReader fileReader13 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_HISTONE);
			FileReader fileReader14 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_HISTONE);
			FileReader fileReader15 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_HISTONE);
			FileReader fileReader16 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_HISTONE);
			FileReader fileReader17 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_HISTONE);
			FileReader fileReader18 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_HISTONE);
			FileReader fileReader19 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_HISTONE);
			FileReader fileReader20 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_HISTONE);
			FileReader fileReader21 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_HISTONE);
			FileReader fileReader22 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_HISTONE);
			FileReader fileReader23 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_HISTONE);
			FileReader fileReader24 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_HISTONE);
			
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
			BufferedReader bufferedReader4 = new BufferedReader(fileReader4);
			BufferedReader bufferedReader5 = new BufferedReader(fileReader5);
			BufferedReader bufferedReader6 = new BufferedReader(fileReader6);
			BufferedReader bufferedReader7 = new BufferedReader(fileReader7);
			BufferedReader bufferedReader8 = new BufferedReader(fileReader8);
			BufferedReader bufferedReader9 = new BufferedReader(fileReader9);
			BufferedReader bufferedReader10 = new BufferedReader(fileReader10);
			BufferedReader bufferedReader11 = new BufferedReader(fileReader11);
			BufferedReader bufferedReader12 = new BufferedReader(fileReader12);
			BufferedReader bufferedReader13 = new BufferedReader(fileReader13);
			BufferedReader bufferedReader14 = new BufferedReader(fileReader14);
			BufferedReader bufferedReader15 = new BufferedReader(fileReader15);
			BufferedReader bufferedReader16 = new BufferedReader(fileReader16);
			BufferedReader bufferedReader17 = new BufferedReader(fileReader17);
			BufferedReader bufferedReader18 = new BufferedReader(fileReader18);
			BufferedReader bufferedReader19 = new BufferedReader(fileReader19);
			BufferedReader bufferedReader20 = new BufferedReader(fileReader20);
			BufferedReader bufferedReader21 = new BufferedReader(fileReader21);
			BufferedReader bufferedReader22 = new BufferedReader(fileReader22);
			BufferedReader bufferedReader23 = new BufferedReader(fileReader23);
			BufferedReader bufferedReader24 = new BufferedReader(fileReader24);
			
			bufferedReaderList.add(bufferedReader1);
			bufferedReaderList.add(bufferedReader2);
			bufferedReaderList.add(bufferedReader3);
			bufferedReaderList.add(bufferedReader4);
			bufferedReaderList.add(bufferedReader5);
			bufferedReaderList.add(bufferedReader6);
			bufferedReaderList.add(bufferedReader7);
			bufferedReaderList.add(bufferedReader8);
			bufferedReaderList.add(bufferedReader9);
			bufferedReaderList.add(bufferedReader10);
			bufferedReaderList.add(bufferedReader11);
			bufferedReaderList.add(bufferedReader12);
			bufferedReaderList.add(bufferedReader13);
			bufferedReaderList.add(bufferedReader14);
			bufferedReaderList.add(bufferedReader15);
			bufferedReaderList.add(bufferedReader16);
			bufferedReaderList.add(bufferedReader17);
			bufferedReaderList.add(bufferedReader18);
			bufferedReaderList.add(bufferedReader19);
			bufferedReaderList.add(bufferedReader20);
			bufferedReaderList.add(bufferedReader21);
			bufferedReaderList.add(bufferedReader22);
			bufferedReaderList.add(bufferedReader23);
			bufferedReaderList.add(bufferedReader24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void readDnaseCellLineNames(List<String> dnaseCellLineNames){
		
		List<BufferedReader> bufferedReaderList = new ArrayList<BufferedReader>();
		
		createChromBaseDnaseBufferedReaders(bufferedReaderList);
		
		String strLine;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab 	= 0;
		int indexofSecondTab 	= 0;
		int indexofThirdTab 	= 0;
		int indexofFourthTab	= 0;
		
		String dnaseCellLineName;
		
		try {
			for(int i = 0; i< bufferedReaderList.size() ; i++){
				 bufferedReader = bufferedReaderList.get(i);
				 				
					while((strLine = bufferedReader.readLine())!=null){
//						example unsorted dnase line
//						chrY	10036738	10039094	H1_ES	idrPool.H1_ES_DNaseHS_BP_TP_P_peaks_OV_DukeDNase_H1_ES_B1_peaks_VS_DukeDNase_H1_ES_B2_peaks.npk2.narrowPeak

						indexofFirstTab = strLine.indexOf('\t');
						indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
						
						dnaseCellLineName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						
						if(!(dnaseCellLineNames.contains(dnaseCellLineName))){
							dnaseCellLineNames.add(dnaseCellLineName);
						}
						
					 }// End of While			
			}// End of For						
			
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}	
		closeBufferedReaders(bufferedReaderList);
	}
	
	
	public void readTfbsorHistoneNames(List<String> tfbsorHistoneNames,String tfbsorHistone){
		List<BufferedReader> bufferedReaderList = new ArrayList<BufferedReader>();
		
		
		if (tfbsorHistone.equals(Commons.TFBS)){
			createChromBaseTfbsBufferedReaders(bufferedReaderList);
				
		}else if (tfbsorHistone.equals(Commons.HISTONE)) {
			createChromBaseHistoneBufferedReaders(bufferedReaderList);				
		}
		
		
		String strLine;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab 	= 0;
		int indexofSecondTab 	= 0;
		int indexofThirdTab 	= 0;
		int indexofFourthTab	= 0;
		
		String tfbsorHistoneName;
		
		try {
			for(int i = 0; i< bufferedReaderList.size() ; i++){
				 bufferedReader = bufferedReaderList.get(i);
				 				
					while((strLine = bufferedReader.readLine())!=null){
//						example unsorted tfbs line
//						chrY	2804079	2804213	Ctcf	H1hesc	spp.optimal.wgEncodeBroadHistoneH1hescCtcfStdAlnRep0_VS_wgEncodeBroadHistoneH1hescControlStdAlnRep0.narrowPeak
						
//						example unsorted histone line
//						chrY	15589743	15592520	H3k27ac	H1hesc	wgEncodeBroadHistoneH1hescH3k27acStdAln.narrowPeak

						indexofFirstTab = strLine.indexOf('\t');
						indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
						
						tfbsorHistoneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						
						if(!(tfbsorHistoneNames.contains(tfbsorHistoneName))){
							tfbsorHistoneNames.add(tfbsorHistoneName);
						}
						
					 }// End of While			
			}// End of For
									
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		closeBufferedReaders(bufferedReaderList);		
	}

	

	public void readGeneIds(List<String> geneIds, String inputFileName){
		
		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		
		String geneId;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
//			example line 
//9606	1	REVIEWED	NM_130786.3	161377438	NP_570602.2	21071030	AC_000151.1	157718668	55167315	55174019	-	Alternate HuRef
				

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				geneId = strLine.substring(indexofFirstTab+1,indexofSecondTab);				
				
				if(!(geneIds.contains(geneId))){					
					geneIds.add(geneId);								
				}													
			} // End of While
						
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void readRNAAccessionVersions(List<String> rnaNucleotideAccessionVersions,String inputFileName){
		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofDot = 0;
		
		String rnaNucleotideAccessionVersion;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
//				example line 
//9606	1	REVIEWED	NM_130786.3	161377438	NP_570602.2	21071030	AC_000151.1	157718668	55167315	55174019	-	Alternate HuRef

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
				
				rnaNucleotideAccessionVersion = strLine.substring(indexofThirdTab+1,indexofFourthTab);				
				indexofDot =rnaNucleotideAccessionVersion.indexOf('.');
				
				if(indexofDot>=0){
					rnaNucleotideAccessionVersion = rnaNucleotideAccessionVersion.substring(0, indexofDot);
				}
				
				
				if(!(rnaNucleotideAccessionVersions.contains(rnaNucleotideAccessionVersion))){					
					rnaNucleotideAccessionVersions.add(rnaNucleotideAccessionVersion);								
				}													
			} // End of While
			
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void readUcscRefSeqGeneName2s(List<String>  ucscRefSeqGeneName2s,String inputFileName){
		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		int indexofSixthTab = 0;
		int indexofSeventhTab = 0;
		int indexofEighthTab = 0;
		int indexofNinethTab = 0;
		int indexofTenthTab = 0;
		int indexofEleventhTab = 0;
		int indexofTwelfthTab = 0;
		int indexofThirteenthTab = 0;
		
		String ucscRefSeqGeneName2;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
//			consume first line
			strLine = bufferedReader.readLine();
			
			while((strLine = bufferedReader.readLine())!=null){
//				Example line
//				#bin	name	chrom	strand	txStart	txEnd	cdsStart	cdsEnd	exonCount	exonStarts	exonEnds	score	name2	cdsStartStat	cdsEndStat	exonFrames
//				1	NM_032785	chr1	-	48998526	50489626	48999844	50489468	14	48998526,49000561,49005313,49052675,49056504,49100164,49119008,49128823,49332862,49511255,49711441,50162984,50317067,50489434,	48999965,49000588,49005410,49052838,49056657,49100276,49119123,49128913,49332902,49511472,49711536,50163109,50317190,50489626,	0	AGBL4	cmpl	cmpl	2,2,1,0,0,2,1,1,0,2,0,1,1,0,				

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
				indexofSixthTab = strLine.indexOf('\t',indexofFifthTab+1);
				indexofSeventhTab = strLine.indexOf('\t',indexofSixthTab+1);
				indexofEighthTab = strLine.indexOf('\t',indexofSeventhTab+1);
				indexofNinethTab = strLine.indexOf('\t',indexofEighthTab+1);
				indexofTenthTab = strLine.indexOf('\t',indexofNinethTab+1);
				indexofEleventhTab = strLine.indexOf('\t',indexofTenthTab+1);
				indexofTwelfthTab = strLine.indexOf('\t',indexofEleventhTab+1);
				indexofThirteenthTab = strLine.indexOf('\t',indexofTwelfthTab+1);
				
				
				ucscRefSeqGeneName2 = strLine.substring(indexofTwelfthTab+1,indexofThirteenthTab);				
				
				if(!(ucscRefSeqGeneName2s.contains(ucscRefSeqGeneName2))){					
					ucscRefSeqGeneName2s.add(ucscRefSeqGeneName2);								
				}													
			} // End of While
			
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	public void readKeggPathwayNames(List<String> keggPathwayNameList, String inputFileName){

		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofTab 	= 0;
		int indexofColon = 0;
		
		String keggPathwayName;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
//				example line
//				path:hsa00010	hsa:10327	reverse

				indexofTab = strLine.indexOf('\t');				
				keggPathwayName = strLine.substring(0,indexofTab);
				
				indexofColon = keggPathwayName.indexOf(':');				
				keggPathwayName = keggPathwayName.substring(indexofColon+1);				
				
				if(!(keggPathwayNameList.contains(keggPathwayName))){					
					keggPathwayNameList.add(keggPathwayName);								
				}													
			} // End of While			
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
							
	}

	
	public void writeNames(List<String> nameList, String outputFileName){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		
		try {
			
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
		
			for(int i = 0; i<nameList.size() ;i++){
								
				bufferedWriter.write(nameList.get(i)+ "\n");
				bufferedWriter.flush();				
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	
	}
	
	
	
	public void writeAllPossibleDnaseCellLineNames(){
		
		List<String> dnaseCellLineNames = new ArrayList<String>();
		readDnaseCellLineNames(dnaseCellLineNames);
		writeNames(dnaseCellLineNames,Commons.WRITE_ALL_POSSIBLE_DNASE_CELL_NAMES_OUTPUT_FILE);		
	}
	
	
	public void writeAllPossibleTfbsNames(){
		
		List<String> tfbsNames = new ArrayList<String>();
		readTfbsorHistoneNames(tfbsNames, Commons.TFBS);
		writeNames(tfbsNames,Commons.WRITE_ALL_POSSIBLE_TFBS_NAMES_OUTPUT_FILE);		

	}

	public void writeAllPossibleHistoneNames(){
		
		List<String> histoneNames = new ArrayList<String>();
		readTfbsorHistoneNames(histoneNames,Commons.HISTONE);
		writeNames(histoneNames,Commons.WRITE_ALL_POSSIBLE_HISTONE_NAMES_OUTPUT_FILE);		

	}
	
	public void writeAllPossibleGeneIds(){

		List<String> geneIds = new ArrayList<String>();
		readGeneIds(geneIds,Commons.NCBI_HUMAN_GENE_TO_REF_SEQ);
		writeNames(geneIds,Commons.WRITE_ALL_POSSIBLE_GENE_IDS_OUTPUT_FILE);		
		
	}
	
	public void writeAllPossibleRNAAccessionVersions(){

		List<String> rnaNucleotideAccessionVersions = new ArrayList<String>();
		readRNAAccessionVersions(rnaNucleotideAccessionVersions,Commons.NCBI_HUMAN_GENE_TO_REF_SEQ);
		writeNames(rnaNucleotideAccessionVersions,Commons.WRITE_ALL_POSSIBLE_RNA_NUCLEUOTIDE_ACCESSION_VERSIONS_OUTPUT_FILE);		
		
	}

	
	public void writeAllPossibleUcscRefSeqGeneName2s(){

		List<String> ucscRefSeqGeneName2s = new ArrayList<String>();
		readUcscRefSeqGeneName2s(ucscRefSeqGeneName2s,Commons.FTP_HG19_REFSEQ_GENES);
		writeNames(ucscRefSeqGeneName2s,Commons.WRITE_ALL_POSSIBLE_ALTERNATE_GENE_NAMES_OUTPUT_FILE);		
		
	}
	
	public void writeAllPossibleKeggPathwayNames(){
		
		List<String> keggPathwayNameList = new ArrayList<String>();		
		readKeggPathwayNames(keggPathwayNameList,Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE);
		writeNames(keggPathwayNameList,Commons.WRITE_ALL_POSSIBLE_KEGG_PATHWAY_NAMES_OUTPUT_FILE);		
	
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		WriteAllPossibleNames writeAllPossibleNames = new WriteAllPossibleNames();

		//Write all possible dnase cell line names	
		//Using unsorted dnase txt files under C:\eclipse_ganymede\workspace\Doktora1\src\annotate\encode\input_output\dnase 
		writeAllPossibleNames.writeAllPossibleDnaseCellLineNames();
		
		//Write all possible tfbs names
		//Using unsorted tfbs txt files under C:\eclipse_ganymede\workspace\Doktora1\src\annotate\encode\input_output\tfbs 
		writeAllPossibleNames.writeAllPossibleTfbsNames();
		
		//Write all possible histone names
		//Using unsorted tfbs txt files under C:\eclipse_ganymede\workspace\Doktora1\src\annotate\encode\input_output\\histone
		writeAllPossibleNames.writeAllPossibleHistoneNames();

		//Write all possible gene ids
//		Using human_gene2refseq.txt under C:\eclipse_ganymede\workspace\Doktora1\src\ncbi\input_output
		writeAllPossibleNames.writeAllPossibleGeneIds();

		//Write all possible RNA nucleotide accession version, in other words ucsc refseq gene name
//		Using human_gene2refseq.txt under C:\eclipse_ganymede\workspace\Doktora1\src\ncbi\input_output
		writeAllPossibleNames.writeAllPossibleRNAAccessionVersions();

		//Write all possible ucsc refseq gene name2
//		Using hg19_refseq_genes.txt under C:\\eclipse_ganymede\\workspace\\Doktora1\\src\\annotate\\ucscgenome\\input_output	
		writeAllPossibleNames.writeAllPossibleUcscRefSeqGeneName2s();

		//Write all possible kegg pathway names		
		//Using pathway_hsa.list under C:\eclipse_ganymede\workspace\Doktora1\src\keggpathway\ncbigenes\input_output
		writeAllPossibleNames.writeAllPossibleKeggPathwayNames();
	}

}
