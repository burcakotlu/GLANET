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

import common.Commons;

public class ChromosomeBasedMapabilityIntervalTree {
	
	
	
	
	public  static IntervalTree getChromosomeBasedMapabilityIntervalTree(String chromName, int chromSize){
		switch(chromName){
			case Commons.CHROMOSOME1 :  return getChromosome1MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME2 :  return getChromosome2MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME3 :  return getChromosome3MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME4 :  return getChromosome4MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME5 :  return getChromosome5MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME6 :  return getChromosome6MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME7 :  return getChromosome7MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME8 :  return getChromosome8MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME9 :  return getChromosome9MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME10 :  return getChromosome10MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME11 :  return getChromosome11MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME12 :  return getChromosome12MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME13 :  return getChromosome13MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME14 :  return getChromosome14MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME15 :  return getChromosome15MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME16 :  return getChromosome16MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME17 :  return getChromosome17MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME18 :  return getChromosome18MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME19 :  return getChromosome19MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME20:  return getChromosome20MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME21 :  return getChromosome21MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME22 :  return getChromosome22MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOMEX :  return getChromosomeXMapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOMEY :  return getChromosomeYMapabilityIntervalTree(chromSize); 
				
		}//End of switch
		
		return null;
		
	}
	
	public static IntervalTree getChromosome1MapabilityIntervalTree(int chromSize) {
			IntervalTree mapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME1,mapabilityIntervalTree);	
			return mapabilityIntervalTree;
	}

	public static IntervalTree getChromosome2MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME2,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome3MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME3,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome4MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME4,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome5MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME5,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome6MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME6,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome7MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME7,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome8MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME8,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome9MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME9,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome10MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME10,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome11MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME11,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome12MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME12,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome13MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME13,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome14MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME14,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome15MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME15,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome16MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME16,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome17MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME17,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome18MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME18,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome19MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME19,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome20MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME20,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome21MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME21,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosome22MapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME22,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosomeXMapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOMEX,mapabilityIntervalTree);	
		return mapabilityIntervalTree;
	}
	
	public static IntervalTree getChromosomeYMapabilityIntervalTree(int chromSize) {
		IntervalTree mapabilityIntervalTree = new IntervalTree();
		Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOMEY,mapabilityIntervalTree);	
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
