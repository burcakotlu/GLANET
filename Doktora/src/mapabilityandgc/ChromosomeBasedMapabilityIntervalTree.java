/**
 * @author Burcak Otlu
 * Sep 5, 2013
 * 6:10:08 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import intervaltree.IntervalTree;
import enumtypes.ChromosomeName;

/**
 * @author Can
 *
 */
public class ChromosomeBasedMapabilityIntervalTree {
	
	
	
	
	public  static IntervalTree getChromosomeBasedMapabilityIntervalTree(ChromosomeName chromName, int chromSize){
		switch(chromName){
			case CHROMOSOME1 :  return getChromosome1MapabilityIntervalTree(chromSize); 
			case CHROMOSOME2 :  return getChromosome2MapabilityIntervalTree(chromSize); 
			case CHROMOSOME3 :  return getChromosome3MapabilityIntervalTree(chromSize); 
			case CHROMOSOME4 :  return getChromosome4MapabilityIntervalTree(chromSize); 
			case CHROMOSOME5 :  return getChromosome5MapabilityIntervalTree(chromSize); 
			case CHROMOSOME6 :  return getChromosome6MapabilityIntervalTree(chromSize); 
			case CHROMOSOME7 :  return getChromosome7MapabilityIntervalTree(chromSize); 
			case CHROMOSOME8 :  return getChromosome8MapabilityIntervalTree(chromSize); 
			case CHROMOSOME9 :  return getChromosome9MapabilityIntervalTree(chromSize); 
			case CHROMOSOME10 :  return getChromosome10MapabilityIntervalTree(chromSize); 
			case CHROMOSOME11 :  return getChromosome11MapabilityIntervalTree(chromSize); 
			case CHROMOSOME12 :  return getChromosome12MapabilityIntervalTree(chromSize); 
			case CHROMOSOME13 :  return getChromosome13MapabilityIntervalTree(chromSize); 
			case CHROMOSOME14 :  return getChromosome14MapabilityIntervalTree(chromSize); 
			case CHROMOSOME15 :  return getChromosome15MapabilityIntervalTree(chromSize); 
			case CHROMOSOME16 :  return getChromosome16MapabilityIntervalTree(chromSize); 
			case CHROMOSOME17 :  return getChromosome17MapabilityIntervalTree(chromSize); 
			case CHROMOSOME18 :  return getChromosome18MapabilityIntervalTree(chromSize); 
			case CHROMOSOME19 :  return getChromosome19MapabilityIntervalTree(chromSize); 
			case CHROMOSOME20:  return getChromosome20MapabilityIntervalTree(chromSize); 
			case CHROMOSOME21 :  return getChromosome21MapabilityIntervalTree(chromSize); 
			case CHROMOSOME22 :  return getChromosome22MapabilityIntervalTree(chromSize); 
			case CHROMOSOMEX :  return getChromosomeXMapabilityIntervalTree(chromSize); 
			case CHROMOSOMEY :  return getChromosomeYMapabilityIntervalTree(chromSize); 
				
		}//End of switch
		
		return null;
		
	}
	
	public static IntervalTree getChromosome1MapabilityIntervalTree(int chromSize) {
			IntervalTree mapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME1,mapabilityIntervalTree);	
			return mapabilityIntervalTree;
	}

	public static IntervalTree getChromosome2MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME2,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome3MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME3,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome4MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME4,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome5MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME5,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome6MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME6,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome7MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME7,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome8MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME8,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome9MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME9,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome10MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME10,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome11MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME11,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome12MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME12,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome13MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME13,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome14MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME14,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome15MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME15,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome16MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME16,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome17MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME17,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome18MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME18,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome19MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME19,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome20MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME20,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome21MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME21,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome22MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME22,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosomeXMapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOMEX,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosomeYMapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOMEY,mapabilityIntervalTree);	
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
