/**
 * @author Burcak Otlu
 * Sep 6, 2013
 * 4:39:26 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import intervaltree.ChromosomeName;
import empiricalpvalues.GCCharArray;
import common.Commons;

public class ChromosomeBasedGCArray {
	
	
	
	public static GCCharArray getChromosomeGCArray(String dataFolder,ChromosomeName chromName, int chromSize){
		switch(chromName){
			case CHROMOSOME1 :  return getChr1GC(dataFolder,chromSize); 
			case CHROMOSOME2 :  return getChr2GC(dataFolder,chromSize);
			case CHROMOSOME3 :  return getChr3GC(dataFolder,chromSize);
			case CHROMOSOME4 :  return getChr4GC(dataFolder,chromSize);
			case CHROMOSOME5 :  return getChr5GC(dataFolder,chromSize);
			case CHROMOSOME6 :  return getChr6GC(dataFolder,chromSize);
			case CHROMOSOME7 :  return getChr7GC(dataFolder,chromSize);
			case CHROMOSOME8 :  return getChr8GC(dataFolder,chromSize);
			case CHROMOSOME9 :  return getChr9GC(dataFolder,chromSize);
			case CHROMOSOME10 : return getChr10GC(dataFolder,chromSize);
			case CHROMOSOME11 : return getChr11GC(dataFolder,chromSize);
			case CHROMOSOME12 : return getChr12GC(dataFolder,chromSize);
			case CHROMOSOME13 : return getChr13GC(dataFolder,chromSize);
			case CHROMOSOME14 : return getChr14GC(dataFolder,chromSize);
			case CHROMOSOME15 : return getChr15GC(dataFolder,chromSize);
			case CHROMOSOME16 : return getChr16GC(dataFolder,chromSize);
			case CHROMOSOME17 : return getChr17GC(dataFolder,chromSize);
			case CHROMOSOME18 : return getChr18GC(dataFolder,chromSize);
			case CHROMOSOME19 : return getChr19GC(dataFolder,chromSize);
			case CHROMOSOME20 : return getChr20GC(dataFolder,chromSize);
			case CHROMOSOME21 : return getChr21GC(dataFolder,chromSize);
			case CHROMOSOME22 : return getChr22GC(dataFolder,chromSize);
			case CHROMOSOMEX  : return getChrXGC(dataFolder,chromSize);
			case CHROMOSOMEY  : return getChrYGC(dataFolder,chromSize);						
		}
		
		return null;
		
	}
		
	
	public static GCCharArray getChr1GC(String dataFolder,int chromSize) {
		
			GCCharArray chrGCArray =new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME1,chrGCArray);	
			return chrGCArray;
	}
	
	public static GCCharArray getChr2GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME2,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr3GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME3,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr4GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME4,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr5GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME5,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr6GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME6,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr7GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME7,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr8GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME8,chrGCArray);	
		return chrGCArray;
	}
	public static GCCharArray getChr9GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME9,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr10GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME10,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr11GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME11,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr12GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME12,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr13GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME13,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr14GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME14,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr15GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME15,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr16GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME16,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr17GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME17,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr18GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME18,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr19GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME19,chrGCArray);	
		return chrGCArray;
	}
	
	
	
	public static GCCharArray getChr20GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME20,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr21GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME21,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr22GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOME22,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChrXGC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOMEX,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChrYGC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,ChromosomeName.CHROMOSOMEY,chrGCArray);	
		return chrGCArray;
	}
	
	
	

	/**
	 * 
	 */
	public ChromosomeBasedGCArray() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
