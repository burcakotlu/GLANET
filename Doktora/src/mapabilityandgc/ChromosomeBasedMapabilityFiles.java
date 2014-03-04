/**
 * @author Burcak Otlu
 * Aug 18, 2013
 * 1:32:16 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import common.Commons;

public class ChromosomeBasedMapabilityFiles {
	
	
	public static void readMapabilityFileWriteChromBasedMapabilityFiles(Map<String, BufferedWriter> chromName2BufferedWriterHashMap){
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		String strLine;
		int indexofNumber;
		
		String chromName;
		int low;
		int high;
		double mapability;
		
		int indexofFirstTab; 
		int indexofSecondTab;
		int indexofThirdTab;
		
		BufferedWriter correspondingBufferedWriter =null;
		
		try {
			fileReader = new FileReader(Commons.WG_ENCODE_CRG_MAPABILITY_ALIGN_50_MER_WIG);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				indexofNumber = strLine.indexOf('#');
				
				//#bedGraph section chr1:10000-81873	(indexofNumber=0)
				//chr1	10015	10026	0.5   (indexofNumber=-1)
				
				if (indexofNumber==-1){
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
					
					chromName = strLine.substring(0, indexofFirstTab);
					low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					mapability = Double.parseDouble(strLine.substring(indexofThirdTab+1));	
					
					correspondingBufferedWriter = chromName2BufferedWriterHashMap.get(chromName);
					if (correspondingBufferedWriter== null){
						System.out.println(chromName);
					}else{
						correspondingBufferedWriter.write(chromName + "\t" + low + "\t"  + high + "\t" + mapability + "\n");
					}
					
				}
			}
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	public static void createChromBasedBufferedWriters(Map<String, BufferedWriter> chromName2BufferedWriterHashMap){
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		String chromName=null;
		String chromBasedMapabilityFileName=null;
		
		
		
		try {
			
			for(int i = 1; i<= Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
				switch(i){
					case 1:	chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR1_FILE;
							chromName  = Commons.CHROMOSOME1;
							break;
					case 2:	chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR2_FILE;
							chromName  = Commons.CHROMOSOME2;
							break;
					case 3:	chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR3_FILE;
							chromName  = Commons.CHROMOSOME3;
							break;
					case 4:	chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR4_FILE;
							chromName  = Commons.CHROMOSOME4;
							break;
					case 5:	chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR5_FILE;
							chromName  = Commons.CHROMOSOME5;
							break;
					case 6:	chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR6_FILE;
							chromName  = Commons.CHROMOSOME6;
							break;
					case 7:	chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR7_FILE;
							chromName  = Commons.CHROMOSOME7;
							break;
					case 8:	chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR8_FILE;
							chromName  = Commons.CHROMOSOME8;
							break;
					case 9:	chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR9_FILE;
							chromName  = Commons.CHROMOSOME9;
							break;
					case 10:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR10_FILE;
							chromName  = Commons.CHROMOSOME10;
							break;
					case 11:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR11_FILE;
							chromName  = Commons.CHROMOSOME11;
							break;
					case 12:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR12_FILE;
							chromName  = Commons.CHROMOSOME12;
							break;
					case 13:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR13_FILE;
							chromName  = Commons.CHROMOSOME13;
							break;
					case 14:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR14_FILE;
							chromName  = Commons.CHROMOSOME14;
							break;
					case 15:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR15_FILE;
							chromName  = Commons.CHROMOSOME15;
							break;
					case 16:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR16_FILE;
							chromName  = Commons.CHROMOSOME16;
							break;
					case 17:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR17_FILE;
							chromName  = Commons.CHROMOSOME17;
							break;
					case 18:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR18_FILE;
							chromName  = Commons.CHROMOSOME18;
							break;
					case 19:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR19_FILE;
							chromName  = Commons.CHROMOSOME19;
							break;
					case 20:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR20_FILE;
							chromName  = Commons.CHROMOSOME20;
							break;
					case 21:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR21_FILE;
							chromName  = Commons.CHROMOSOME21;
							break;
					case 22:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHR22_FILE;
							chromName  = Commons.CHROMOSOME22;
							break;
					case 23:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHRX_FILE;
							chromName  = Commons.CHROMOSOMEX;
							break;
					case 24:chromBasedMapabilityFileName =Commons.MAPABILITY_HG19_CHRY_FILE;
							chromName  = Commons.CHROMOSOMEY;
							break;
							
							//todo
				}//end of switch
				
				fileWriter = new FileWriter(chromBasedMapabilityFileName);
				bufferedWriter = new BufferedWriter(fileWriter);
				chromName2BufferedWriterHashMap.put(chromName, bufferedWriter);
				
			}//end of for
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void closeChromBasedBufferedWriters(Map<String, BufferedWriter> chromName2BufferedWriterHashMap){
		BufferedWriter bufferedWriter;
		
		for(Map.Entry<String, BufferedWriter> entry  :  chromName2BufferedWriterHashMap.entrySet()){
			bufferedWriter = entry.getValue();
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		
	}
	
	public static void main(String[] args) {
		Map<String, BufferedWriter> chromName2BufferedWriterHashMap = new HashMap<String, BufferedWriter>();
		
		createChromBasedBufferedWriters(chromName2BufferedWriterHashMap);
		readMapabilityFileWriteChromBasedMapabilityFiles(chromName2BufferedWriterHashMap);
		closeChromBasedBufferedWriters(chromName2BufferedWriterHashMap);
		

	}

}
