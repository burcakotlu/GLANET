/**
 * @author Burcak Otlu
 * Aug 12, 2013
 * 12:01:51 AM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import empiricalpvalues.GCCharArray;

import common.Commons;

public class SingletonChromosomeBasedGCArray {
	
	private static GCCharArray SingletonChr1GCArray = null;
	private static GCCharArray SingletonChr2GCArray = null;
	private static GCCharArray SingletonChr3GCArray = null;
	private static GCCharArray SingletonChr4GCArray = null;
	private static GCCharArray SingletonChr5GCArray = null;
	private static GCCharArray SingletonChr6GCArray = null;
	private static GCCharArray SingletonChr7GCArray = null;
	private static GCCharArray SingletonChr8GCArray = null;
	private static GCCharArray SingletonChr9GCArray = null;
	private static GCCharArray SingletonChr10GCArray = null;
	private static GCCharArray SingletonChr11GCArray = null;
	private static GCCharArray SingletonChr12GCArray = null;
	private static GCCharArray SingletonChr13GCArray = null;
	private static GCCharArray SingletonChr14GCArray = null;
	private static GCCharArray SingletonChr15GCArray = null;
	private static GCCharArray SingletonChr16GCArray = null;
	private static GCCharArray SingletonChr17GCArray = null;
	private static GCCharArray SingletonChr18GCArray = null;
	private static GCCharArray SingletonChr19GCArray = null;
	private static GCCharArray SingletonChr20GCArray = null;
	private static GCCharArray SingletonChr21GCArray = null;
	private static GCCharArray SingletonChr22GCArray = null;
	private static GCCharArray SingletonChrXGCArray = null;
	private static GCCharArray SingletonChrYGCArray = null;
	
	
	// Note that the constructor is private
	private SingletonChromosomeBasedGCArray() {
			// Optional Code
	}

	public static GCCharArray getSingletonChromosomeGCArray(String dataFolder,String chromName, int chromSize){
		switch(chromName){
			case Commons.CHROMOSOME1 :  return getSingletonChr1GC(dataFolder,chromSize); 
			case Commons.CHROMOSOME2 :  return getSingletonChr2GC(dataFolder,chromSize);
			case Commons.CHROMOSOME3 :  return getSingletonChr3GC(dataFolder,chromSize);
			case Commons.CHROMOSOME4 :  return getSingletonChr4GC(dataFolder,chromSize);
			case Commons.CHROMOSOME5 :  return getSingletonChr5GC(dataFolder,chromSize);
			case Commons.CHROMOSOME6 :  return getSingletonChr6GC(dataFolder,chromSize);
			case Commons.CHROMOSOME7 :  return getSingletonChr7GC(dataFolder,chromSize);
			case Commons.CHROMOSOME8 :  return getSingletonChr8GC(dataFolder,chromSize);
			case Commons.CHROMOSOME9 :  return getSingletonChr9GC(dataFolder,chromSize);
			case Commons.CHROMOSOME10 : return getSingletonChr10GC(dataFolder,chromSize);
			case Commons.CHROMOSOME11 : return getSingletonChr11GC(dataFolder,chromSize);
			case Commons.CHROMOSOME12 : return  getSingletonChr12GC(dataFolder,chromSize);
			case Commons.CHROMOSOME13 : return getSingletonChr13GC(dataFolder,chromSize);
			case Commons.CHROMOSOME14 : return getSingletonChr14GC(dataFolder,chromSize);
			case Commons.CHROMOSOME15 : return getSingletonChr15GC(dataFolder,chromSize);
			case Commons.CHROMOSOME16 : return getSingletonChr16GC(dataFolder,chromSize);
			case Commons.CHROMOSOME17 : return getSingletonChr17GC(dataFolder,chromSize);
			case Commons.CHROMOSOME18 : return getSingletonChr18GC(dataFolder,chromSize);
			case Commons.CHROMOSOME19 : return  getSingletonChr19GC(dataFolder,chromSize);
			case Commons.CHROMOSOME20 : return getSingletonChr20GC(dataFolder,chromSize);
			case Commons.CHROMOSOME21 : return getSingletonChr21GC(dataFolder,chromSize);
			case Commons.CHROMOSOME22 : return getSingletonChr22GC(dataFolder,chromSize);
			case Commons.CHROMOSOMEX  : return getSingletonChrXGC(dataFolder,chromSize);
			case Commons.CHROMOSOMEY  : return getSingletonChrYGC(dataFolder,chromSize);						
		}
		
		return null;
		
	}
		
	
	public static GCCharArray getSingletonChr1GC(String dataFolder,int chromSize) {
		if (SingletonChr1GCArray == null) {
			SingletonChr1GCArray =new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME1,SingletonChr1GCArray);	
		}
		return SingletonChr1GCArray;
	}
	
	public static GCCharArray getSingletonChr2GC(String dataFolder,int chromSize) {
		if (SingletonChr2GCArray == null) {
			SingletonChr2GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME2,SingletonChr2GCArray);	
		}
		return SingletonChr2GCArray;
	}
	
	public static GCCharArray getSingletonChr3GC(String dataFolder,int chromSize) {
		if (SingletonChr3GCArray == null) {
			SingletonChr3GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME3,SingletonChr3GCArray);	
		}
		return SingletonChr3GCArray;
	}
	
	public static GCCharArray getSingletonChr4GC(String dataFolder,int chromSize) {
		if (SingletonChr4GCArray == null) {
			SingletonChr4GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME4,SingletonChr4GCArray);	
		}
		return SingletonChr4GCArray;
	}
	
	public static GCCharArray getSingletonChr5GC(String dataFolder,int chromSize) {
		if (SingletonChr5GCArray == null) {
			SingletonChr5GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME5,SingletonChr5GCArray);	
		}
		return SingletonChr5GCArray;
	}
	
	public static GCCharArray getSingletonChr6GC(String dataFolder,int chromSize) {
		if (SingletonChr6GCArray == null) {
			SingletonChr6GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME6,SingletonChr6GCArray);	
		}
		return SingletonChr6GCArray;
	}
	
	
	public static GCCharArray getSingletonChr7GC(String dataFolder,int chromSize) {
		if (SingletonChr7GCArray == null) {
			SingletonChr7GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME7,SingletonChr7GCArray);	
		}
		return SingletonChr7GCArray;
	}
	
	public static GCCharArray getSingletonChr8GC(String dataFolder,int chromSize) {
		if (SingletonChr8GCArray == null) {
			SingletonChr8GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME8,SingletonChr8GCArray);	
		}
		return SingletonChr8GCArray;
	}
	
	public static GCCharArray getSingletonChr9GC(String dataFolder,int chromSize) {
		if (SingletonChr9GCArray == null) {
			SingletonChr9GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME9,SingletonChr9GCArray);	
		}
		return SingletonChr9GCArray;
	}
	
	public static GCCharArray getSingletonChr10GC(String dataFolder,int chromSize) {
		if (SingletonChr10GCArray == null) {
			SingletonChr10GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME10,SingletonChr10GCArray);	
		}
		return SingletonChr10GCArray;
	}
	
	public static GCCharArray getSingletonChr11GC(String dataFolder,int chromSize) {
		if (SingletonChr11GCArray == null) {
			SingletonChr11GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME11,SingletonChr11GCArray);	
		}
		return SingletonChr11GCArray;
	}
	
	public static GCCharArray getSingletonChr12GC(String dataFolder,int chromSize) {
		if (SingletonChr12GCArray == null) {
			SingletonChr12GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME12,SingletonChr12GCArray);	
		}
		return SingletonChr12GCArray;
	}
	
	public static GCCharArray getSingletonChr13GC(String dataFolder,int chromSize) {
		if (SingletonChr13GCArray == null) {
			SingletonChr13GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME13,SingletonChr13GCArray);	
		}
		return SingletonChr13GCArray;
	}
	
	public static GCCharArray getSingletonChr14GC(String dataFolder,int chromSize) {
		if (SingletonChr14GCArray == null) {
			SingletonChr14GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME14,SingletonChr14GCArray);	
		}
		return SingletonChr14GCArray;
	}
	
	public static GCCharArray getSingletonChr15GC(String dataFolder,int chromSize) {
		if (SingletonChr15GCArray == null) {
			SingletonChr15GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME15,SingletonChr15GCArray);	
		}
		return SingletonChr15GCArray;
	}
	
	public static GCCharArray getSingletonChr16GC(String dataFolder,int chromSize) {
		if (SingletonChr16GCArray == null) {
			SingletonChr16GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME16,SingletonChr16GCArray);	
		}
		return SingletonChr16GCArray;
	}
	
	public static GCCharArray getSingletonChr17GC(String dataFolder,int chromSize) {
		if (SingletonChr17GCArray == null) {
			SingletonChr17GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME17,SingletonChr17GCArray);	
		}
		return SingletonChr17GCArray;
	}
	
	public static GCCharArray getSingletonChr18GC(String dataFolder,int chromSize) {
		if (SingletonChr18GCArray == null) {
			SingletonChr18GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME18,SingletonChr18GCArray);	
		}
		return SingletonChr18GCArray;
	}
	
	public static GCCharArray getSingletonChr19GC(String dataFolder,int chromSize) {
		if (SingletonChr19GCArray == null) {
			SingletonChr19GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME19,SingletonChr19GCArray);	
		}
		return SingletonChr19GCArray;
	}
	
	public static GCCharArray getSingletonChr20GC(String dataFolder,int chromSize) {
		if (SingletonChr20GCArray == null) {
			SingletonChr20GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME20,SingletonChr20GCArray);	
		}
		return SingletonChr20GCArray;
	}
	
	public static GCCharArray getSingletonChr21GC(String dataFolder,int chromSize) {
		if (SingletonChr21GCArray == null) {
			SingletonChr21GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME21,SingletonChr21GCArray);	
		}
		return SingletonChr21GCArray;
	}
	
	public static GCCharArray getSingletonChr22GC(String dataFolder,int chromSize) {
		if (SingletonChr22GCArray == null) {
			SingletonChr22GCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME22,SingletonChr22GCArray);	
		}
		return SingletonChr22GCArray;
	}
	
	public static GCCharArray getSingletonChrXGC(String dataFolder,int chromSize) {
		if (SingletonChrXGCArray == null) {
			SingletonChrXGCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOMEX,SingletonChrXGCArray);	
		}
		return SingletonChrXGCArray;
	}
	
	public static GCCharArray getSingletonChrYGC(String dataFolder,int chromSize) {
		if (SingletonChrYGCArray == null) {
			SingletonChrYGCArray = new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOMEY,SingletonChrYGCArray);	
		}
		return SingletonChrYGCArray;
	}
	
	//args[0] must have input file name with folder
	//args[1] must have GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2] must have Input File Format		
	//args[3] must have Number of Permutations	
	//args[4] must have False Discovery Rate (ex: 0.05)
	//args[5] must have Generate Random Data Mode (with GC and Mapability/without GC and Mapability)
	//args[6] must have writeGeneratedRandomDataMode checkBox
	//args[7] must have writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//args[8] must have writePermutationBasedAnnotationResultMode checkBox
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;
	
		int chromSize = 249250621;
		GCCharArray gcCharArray = new GCCharArray(chromSize);
		
		gcCharArray = getSingletonChr1GC(dataFolder,chromSize);
		gcCharArray = getSingletonChr1GC(dataFolder,chromSize);
		gcCharArray = getSingletonChr1GC(dataFolder,chromSize);
		gcCharArray = getSingletonChr1GC(dataFolder,chromSize);
		
	}
}


