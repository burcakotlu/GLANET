/**
 * 
 */
package rsat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import auxiliary.FileOperations;

/**
 * @author Burçak Otlu
 * @date Dec 23, 2016
 * @project Glanet 
 *
 */
public class UserDefinedObservedAlleles {
	
	
	public static void fillTargetSNPKey2ObservedAllelesMap(
			Map<String,String> source_snpKey_chrName_1BasedStart_1BasedEnd_2_ObservedAllelesMap,
			Map<String,String> source_snpKey_chrName_1BasedStart_1BasedEnd_2_TargetMap,
			Map<String,String>  target_snpKey_chrName_1BasedStart_1BasedEnd_2_ObservedAllelesMap){
		
		String observedAlleles = null;
		String source_snpKey_chrName_1BasedStart_1BasedEnd = null;
		String target_snpKey_chrName_1BasedStart_1BasedEnd = null;
		
		for(Map.Entry<String, String> entry: source_snpKey_chrName_1BasedStart_1BasedEnd_2_ObservedAllelesMap.entrySet()) {
			
			source_snpKey_chrName_1BasedStart_1BasedEnd = entry.getKey();
			observedAlleles = entry.getValue();
			
			target_snpKey_chrName_1BasedStart_1BasedEnd = source_snpKey_chrName_1BasedStart_1BasedEnd_2_TargetMap.get(source_snpKey_chrName_1BasedStart_1BasedEnd);
			
			if (target_snpKey_chrName_1BasedStart_1BasedEnd!=null){
				target_snpKey_chrName_1BasedStart_1BasedEnd_2_ObservedAllelesMap.put(target_snpKey_chrName_1BasedStart_1BasedEnd,observedAlleles);
			}//End of IF
			
		}//End of FOR
		
	
	}

	public static void fillSNPKey2ObservedAllelesMap(
			String inputFileName,
			Map<String,String> snpKey_chrName_1BasedStart_1BasedEnd_2ObservedAllelesMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		int indexofFirstTab = -1;
		int indexofSecondTab =-1;
		int indexofThirdTab = -1;
		int indexofFourthTab = -1;
		
		String chrName = null ;
		int _1BasedStart = -1;
		int _1BasedEnd = -2;
		String observedAlles = null;
		
		String snpKey = null;
		
		try {
			fileReader = FileOperations.createFileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//Skip header line
			strLine = bufferedReader.readLine();
			
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1) ;
				indexofFourthTab =  strLine.indexOf('\t',indexofThirdTab+1); 
				
				chrName = strLine.substring(0, indexofFirstTab);
				_1BasedStart = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				_1BasedEnd = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				
				if (indexofFourthTab>-1) {
					observedAlles = strLine.substring(indexofThirdTab+1,indexofFourthTab);					
				}else{
					observedAlles = strLine.substring(indexofThirdTab+1);					
				}
				
				snpKey = chrName + "_" + _1BasedStart + "_" + _1BasedEnd;
				
				snpKey_chrName_1BasedStart_1BasedEnd_2ObservedAllelesMap.put(snpKey, observedAlles);
				
			}//End of while
			
			//Close
			bufferedReader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
