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

	public static void calculateBenjaminiHochbergFDRAdjustedPValues(List<FunctionalElement> list, float FDR) {

		FunctionalElement element;

		Iterator<FunctionalElement> itr = list.iterator();

		int j = 1;
		int m = list.size();
		Float BH_FDR_AdjustedPValue;

		while (itr.hasNext()) {

			element = itr.next();
			BH_FDR_AdjustedPValue = (element.getEmpiricalPValue() * m) / j;

			element.setBH_FDR_adjustedPValue(BH_FDR_AdjustedPValue);

			if (element.getBH_FDR_adjustedPValue() <= FDR) {
				element.setRejectNullHypothesis(true);
			} else {
				element.setRejectNullHypothesis(false);
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
