/**
 * @author Burcak Otlu
 * Aug 19, 2013
 * 11:30:45 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import intervaltree.IntervalTree;

public class SingletonChromosomeBasedMapabilityIntervalTree {
	
	private static IntervalTree SingletonChromosome1MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome2MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome3MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome4MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome5MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome6MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome7MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome8MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome9MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome10MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome11MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome12MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome13MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome14MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome15MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome16MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome17MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome18MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome19MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome20MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome21MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome22MapabilityIntervalTree;
	private static IntervalTree SingletonChromosomeXMapabilityIntervalTree;
	private static IntervalTree SingletonChromosomeYMapabilityIntervalTree;
	
	// Note that the constructor is private
	private SingletonChromosomeBasedMapabilityIntervalTree() {
				// Optional Code
	}
	
	
	public static IntervalTree getSingletonChromosomeBasedMapabilityIntervalTree(String chromName, int chromSize){
		switch(chromName){
			case Commons.CHROMOSOME1 :  return getSingletonChromosome1MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME2 :  return getSingletonChromosome2MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME3 :  return getSingletonChromosome3MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME4 :  return getSingletonChromosome4MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME5 :  return getSingletonChromosome5MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME6 :  return getSingletonChromosome6MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME7 :  return getSingletonChromosome7MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME8 :  return getSingletonChromosome8MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME9 :  return getSingletonChromosome9MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME10 :  return getSingletonChromosome10MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME11 :  return getSingletonChromosome11MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME12 :  return getSingletonChromosome12MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME13 :  return getSingletonChromosome13MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME14 :  return getSingletonChromosome14MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME15 :  return getSingletonChromosome15MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME16 :  return getSingletonChromosome16MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME17 :  return getSingletonChromosome17MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME18 :  return getSingletonChromosome18MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME19 :  return getSingletonChromosome19MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME20:  return getSingletonChromosome20MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME21 :  return getSingletonChromosome21MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOME22 :  return getSingletonChromosome22MapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOMEX :  return getSingletonChromosomeXMapabilityIntervalTree(chromSize); 
			case Commons.CHROMOSOMEY :  return getSingletonChromosomeYMapabilityIntervalTree(chromSize); 
				
		}//End of switch
		
		return null;
		
	}
	
	public static IntervalTree getSingletonChromosome1MapabilityIntervalTree(int chromSize) {
		if (SingletonChromosome1MapabilityIntervalTree == null) {
			SingletonChromosome1MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME1,SingletonChromosome1MapabilityIntervalTree);	
		}
		return SingletonChromosome1MapabilityIntervalTree;
	}

	
	public static IntervalTree getSingletonChromosome2MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome2MapabilityIntervalTree == null) {
			SingletonChromosome2MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME2,SingletonChromosome2MapabilityIntervalTree);	
		}
		return SingletonChromosome2MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome3MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome3MapabilityIntervalTree == null) {
			SingletonChromosome3MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME3,SingletonChromosome3MapabilityIntervalTree);	
		}
		return SingletonChromosome3MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome4MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome4MapabilityIntervalTree == null) {
			SingletonChromosome4MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME4,SingletonChromosome4MapabilityIntervalTree);	
		}
		return SingletonChromosome4MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome5MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome5MapabilityIntervalTree == null) {
			SingletonChromosome5MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME5,SingletonChromosome5MapabilityIntervalTree);	
		}
		return SingletonChromosome5MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome6MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome6MapabilityIntervalTree == null) {
			SingletonChromosome6MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME6,SingletonChromosome6MapabilityIntervalTree);	
		}
		return SingletonChromosome6MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome7MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome7MapabilityIntervalTree == null) {
			SingletonChromosome7MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME7,SingletonChromosome7MapabilityIntervalTree);	
		}
		return SingletonChromosome7MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome8MapabilityIntervalTree(int chromSize) {
		if (SingletonChromosome8MapabilityIntervalTree == null) {
			SingletonChromosome8MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME8,SingletonChromosome8MapabilityIntervalTree);	
		}
		return SingletonChromosome8MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome9MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome9MapabilityIntervalTree == null) {
			SingletonChromosome9MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME9,SingletonChromosome9MapabilityIntervalTree);	
		}
		return SingletonChromosome9MapabilityIntervalTree;
	}
	public static IntervalTree getSingletonChromosome10MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome10MapabilityIntervalTree == null) {
			SingletonChromosome10MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME10,SingletonChromosome10MapabilityIntervalTree);	
		}
		return SingletonChromosome10MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome11MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome11MapabilityIntervalTree == null) {
			SingletonChromosome11MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME11,SingletonChromosome11MapabilityIntervalTree);	
		}
		return SingletonChromosome11MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome12MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome12MapabilityIntervalTree == null) {
			SingletonChromosome12MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME12,SingletonChromosome12MapabilityIntervalTree);	
		}
		return SingletonChromosome12MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome13MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome13MapabilityIntervalTree == null) {
			SingletonChromosome13MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME13,SingletonChromosome13MapabilityIntervalTree);	
		}
		return SingletonChromosome13MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome14MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome14MapabilityIntervalTree == null) {
			SingletonChromosome14MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME14,SingletonChromosome14MapabilityIntervalTree);	
		}
		return SingletonChromosome14MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome15MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome15MapabilityIntervalTree == null) {
			SingletonChromosome15MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME15,SingletonChromosome15MapabilityIntervalTree);	
		}
		return SingletonChromosome15MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome16MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome16MapabilityIntervalTree == null) {
			SingletonChromosome16MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME16,SingletonChromosome16MapabilityIntervalTree);	
		}
		return SingletonChromosome16MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome17MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome17MapabilityIntervalTree == null) {
			SingletonChromosome17MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME17,SingletonChromosome17MapabilityIntervalTree);	
		}
		return SingletonChromosome17MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome18MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome18MapabilityIntervalTree == null) {
			SingletonChromosome18MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME18,SingletonChromosome18MapabilityIntervalTree);	
		}
		return SingletonChromosome18MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome19MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome19MapabilityIntervalTree == null) {
			SingletonChromosome19MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME19,SingletonChromosome19MapabilityIntervalTree);	
		}
		return SingletonChromosome19MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome20MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome20MapabilityIntervalTree == null) {
			SingletonChromosome20MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME20,SingletonChromosome20MapabilityIntervalTree);	
		}
		return SingletonChromosome20MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome21MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome21MapabilityIntervalTree == null) {
			SingletonChromosome21MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME21,SingletonChromosome21MapabilityIntervalTree);	
		}
		return SingletonChromosome21MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome22MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome22MapabilityIntervalTree == null) {
			SingletonChromosome22MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOME22,SingletonChromosome22MapabilityIntervalTree);	
		}
		return SingletonChromosome22MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosomeXMapabilityIntervalTree( int chromSize) {
		if (SingletonChromosomeXMapabilityIntervalTree == null) {
			SingletonChromosomeXMapabilityIntervalTree = new IntervalTree();
				Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOMEX,SingletonChromosomeXMapabilityIntervalTree);	
		}
		return SingletonChromosomeXMapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosomeYMapabilityIntervalTree( int chromSize) {
		if (SingletonChromosomeYMapabilityIntervalTree == null) {
			SingletonChromosomeYMapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,Commons.CHROMOSOMEY,SingletonChromosomeYMapabilityIntervalTree);	
		}
		return SingletonChromosomeYMapabilityIntervalTree;
	}
	
	
	
	public static void main(String[] args) {
		
		List<Integer> chromosomeSizes = new ArrayList<Integer>();
		int chromSize;
		IntervalTree intervalTree;
	 	
		GRCh37Hg19Chromosome.initializeChromosomeSizes(chromosomeSizes);
	    
		GRCh37Hg19Chromosome.getHg19ChromosomeSizes(chromosomeSizes, Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		chromSize = GRCh37Hg19Chromosome.getHg19ChromsomeSize(chromosomeSizes, Commons.CHROMOSOME1);
		//for testing purposes
//		intervalTree = getSingletonChromosomeBasedMapabilityIntervalTree(Commons.CHROMOSOME1, chromSize);

		System.out.println("Look at interval tree");
	}

}
