/**
 * @author Burcak Otlu
 * Sep 6, 2013
 * 4:39:26 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import common.Commons;

import empiricalpvalues.GCCharArray;

public class ChromosomeBasedGCArray {
	
	
	
	public static GCCharArray getChromosomeGCArray(String chromName, int chromSize){
		switch(chromName){
			case Commons.CHROMOSOME1 :  return getChr1GC(chromSize); 
			case Commons.CHROMOSOME2 :  return getChr2GC(chromSize);
			case Commons.CHROMOSOME3 :  return getChr3GC(chromSize);
			case Commons.CHROMOSOME4 :  return getChr4GC(chromSize);
			case Commons.CHROMOSOME5 :  return getChr5GC(chromSize);
			case Commons.CHROMOSOME6 :  return getChr6GC(chromSize);
			case Commons.CHROMOSOME7 :  return getChr7GC(chromSize);
			case Commons.CHROMOSOME8 :  return getChr8GC(chromSize);
			case Commons.CHROMOSOME9 :  return getChr9GC(chromSize);
			case Commons.CHROMOSOME10 : return getChr10GC(chromSize);
			case Commons.CHROMOSOME11 : return getChr11GC(chromSize);
			case Commons.CHROMOSOME12 : return getChr12GC(chromSize);
			case Commons.CHROMOSOME13 : return getChr13GC(chromSize);
			case Commons.CHROMOSOME14 : return getChr14GC(chromSize);
			case Commons.CHROMOSOME15 : return getChr15GC(chromSize);
			case Commons.CHROMOSOME16 : return getChr16GC(chromSize);
			case Commons.CHROMOSOME17 : return getChr17GC(chromSize);
			case Commons.CHROMOSOME18 : return getChr18GC(chromSize);
			case Commons.CHROMOSOME19 : return getChr19GC(chromSize);
			case Commons.CHROMOSOME20 : return getChr20GC(chromSize);
			case Commons.CHROMOSOME21 : return getChr21GC(chromSize);
			case Commons.CHROMOSOME22 : return getChr22GC(chromSize);
			case Commons.CHROMOSOMEX  : return getChrXGC(chromSize);
			case Commons.CHROMOSOMEY  : return getChrYGC(chromSize);						
		}
		
		return null;
		
	}
		
	
	public static GCCharArray getChr1GC(int chromSize) {
		
			GCCharArray chrGCArray =new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(Commons.CHROMOSOME1,chrGCArray);	
			return chrGCArray;
	}
	
	public static GCCharArray getChr2GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME2,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr3GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME3,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr4GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME4,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr5GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME5,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr6GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME6,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr7GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME7,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr8GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME8,chrGCArray);	
		return chrGCArray;
	}
	public static GCCharArray getChr9GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME9,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr10GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME10,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr11GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME11,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr12GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME12,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr13GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME13,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr14GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME14,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr15GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME15,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr16GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME16,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr17GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME17,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr18GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME18,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr19GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME19,chrGCArray);	
		return chrGCArray;
	}
	
	
	
	public static GCCharArray getChr20GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME20,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr21GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME21,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr22GC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOME22,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChrXGC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOMEX,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChrYGC(int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(Commons.CHROMOSOMEY,chrGCArray);	
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
