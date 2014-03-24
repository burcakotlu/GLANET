/**
 * @author Burcak Otlu
 * Sep 15, 2013
 * 4:37:14 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import java.util.Comparator;
import java.util.Map;


public class StandardDeviationComparator implements Comparator<Object>{
	
	 Map mapToBeSorted;


	/**
	 * 
	 */
	public StandardDeviationComparator(Map mapToBeSorted) {
		this.mapToBeSorted = mapToBeSorted;
	}

	
	//In descending order
	@Override
	public int compare(Object key1, Object key2) {
		MeanandStandardDeviation val1 = (MeanandStandardDeviation) mapToBeSorted.get(key1);
		MeanandStandardDeviation val2 = (MeanandStandardDeviation) mapToBeSorted.get(key2);
	        if (val1.getStandardDeviation() < val2.getStandardDeviation()) {
	            return +1;
	        } else if (val1.getStandardDeviation() > val2.getStandardDeviation()){
	            return -1;
	        }else{
	        	return 0;
	        }
	}

}
