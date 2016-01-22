/**
 * @author Burcak Otlu
 * Sep 5, 2013
 * 6:10:08 PM
 * 2013
 *
 * 
 */
package mapability;

import intervaltree.IntervalTree;
import enumtypes.ChromosomeName;

/**
 * @author Can
 *
 */
public class ChromosomeBasedMapabilityIntervalTree {


	
	public static IntervalTree getChromosomeBasedMapabilityIntervalTree( String dataFolder,ChromosomeName chrName, int chromSize) {

		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree( dataFolder,chromSize, chrName,mapabilityIntervalTree);
		return mapabilityIntervalTree;
	}


	
	/**
	 * 
	 */
	public ChromosomeBasedMapabilityIntervalTree() {

		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main( String[] args) {

		// TODO Auto-generated method stub

	}

}
