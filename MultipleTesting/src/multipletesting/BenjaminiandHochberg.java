/**
 * @author Burcak Otlu
 * Feb 19, 2014
 * 9:20:22 AM
 * 2014
 *
 * 
 */
package multipletesting;

import java.util.Iterator;
import java.util.List;

import auxiliary.FunctionalElement;

public class BenjaminiandHochberg {

	/**
	 * 
	 */
	public BenjaminiandHochberg() {
		// TODO Auto-generated constructor stub
	}

	
	//Control false discovery rate at level delta
	public static void controlFalseDiscoveryRate(List<FunctionalElement> list,float level){
		FunctionalElement element;
		
		Iterator<FunctionalElement> itr = list.iterator();
		
		int j = 1;
		int m = list.size();
		float joverMtimesDelta;
		
		while(itr.hasNext()){
			
			element = itr.next();
			joverMtimesDelta = (j*1.0f/m)*level;
			
			element.setJoverMtimesDelta(joverMtimesDelta);
			
			if(element.getEmpiricalPValue()<=joverMtimesDelta){
				element.setSignificantFDR(1);
			}else{
				element.setSignificantFDR(0);	
			}
			j++;
		}
		
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
