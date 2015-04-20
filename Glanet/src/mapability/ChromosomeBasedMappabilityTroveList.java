/**
 * 
 */
package mapability;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;

/**
 * @author Burçak Otlu
 * @date Mar 12, 2015
 * @project Glanet 
 * 
 * This is the new way of filling MapabilityIntList and MapabilityShortList.
 
 *
 */
public class ChromosomeBasedMappabilityTroveList {

	
	final static Logger logger = Logger.getLogger(ChromosomeBasedMappabilityTroveList.class);
	
	public static void fillTroveList(
			String dataFolder, 
			ChromosomeName chromName,
			TIntList mapabilityChromosomePositionList,
			TShortList mapabilityShortValueList
			//TByteList mapabilityByteValueList
			){
		
		String fileName =   Commons.MAPABILITY + System.getProperty("file.separator") + chromName.convertEnumtoString() +  Commons.MAPABILITY_HG19_FILE_END;
		
		
		BufferedReader bufferedReader = FileOperations.createBufferedReader(dataFolder, fileName);
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		int low = Integer.MIN_VALUE;
		int high = Integer.MIN_VALUE;
		
		float mapabilityFloatValue;
	
		//For MapabilityShortValue
		short mapabilityShortValue; 
		
		//For MapabilityByteValue
		//byte mapabilityByteValue; 
		
		logger.info(chromName);
		
		int numberofGaps = 0;
		
		
		try {
			
			//chr17	81194312	81194331	0.125
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = (indexofFirstTab>=0) ? strLine.indexOf('\t',indexofFirstTab+1) : -1;
				indexofThirdTab = (indexofSecondTab>=0) ? strLine.indexOf('\t',indexofSecondTab+1) : -1;
				
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				
				if ( high!= Integer.MIN_VALUE  &&
					low!=high){
					//New low must be equal to the previous high
//					chr1	10000	10014	0.00277778
//					chr1	10014	10015	0.333333
//					chr1	10015	10026	0.5
					//logger.info("There is a gap in the given mapability file!" + " next low: " + low +  " previous high: " + high);
					numberofGaps++;
					
					//You have read the new low
					//But it is not equal to old high
					//Which means that there is a gap
					//Insert values for this gap
					mapabilityChromosomePositionList.add(high);
					mapabilityShortValueList.add(Commons.SHORT_0);
					//mapabilityByteValueList.add(Commons.BYTE_0);
				}
				
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				mapabilityFloatValue = Float.parseFloat(strLine.substring(indexofThirdTab+1));
				
				mapabilityShortValue =  (short) (mapabilityFloatValue*Commons.MAPABILITY_SHORT_TEN_THOUSAND);
				
				//for debugging purposes
				//mapabilityByteValue = (byte) (mapabilityFloatValue*Commons.MAPABILITY_BYTE_ONE_HUNDRED);
				
				mapabilityChromosomePositionList.add(low);
				mapabilityShortValueList.add(mapabilityShortValue);
				//mapabilityByteValueList.add(mapabilityByteValue);
				
			}//End of WHILE
			
			//Add the last chromosome position
			//This is a dummy insertion
			//We just want to know the last chromosome position with a mapability value
			mapabilityChromosomePositionList.add(high);
			mapabilityShortValueList.add(Commons.SHORT_0);
			//mapabilityByteValueList.add(Commons.BYTE_0);
			
			logger.info("numberofGaps:" + numberofGaps);
		} catch (IOException e) {
			logger.error(e.toString());
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
