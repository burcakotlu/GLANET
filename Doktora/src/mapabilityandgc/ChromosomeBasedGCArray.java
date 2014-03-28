/**
 * @author Burcak Otlu
 * Sep 6, 2013
 * 4:39:26 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import empiricalpvalues.GCCharArray;

import common.Commons;

public class ChromosomeBasedGCArray {
	
	
	
	public static GCCharArray getChromosomeGCArray(String dataFolder,String chromName, int chromSize){
		switch(chromName){
			case Commons.CHROMOSOME1 :  return getChr1GC(dataFolder,chromSize); 
			case Commons.CHROMOSOME2 :  return getChr2GC(dataFolder,chromSize);
			case Commons.CHROMOSOME3 :  return getChr3GC(dataFolder,chromSize);
			case Commons.CHROMOSOME4 :  return getChr4GC(dataFolder,chromSize);
			case Commons.CHROMOSOME5 :  return getChr5GC(dataFolder,chromSize);
			case Commons.CHROMOSOME6 :  return getChr6GC(dataFolder,chromSize);
			case Commons.CHROMOSOME7 :  return getChr7GC(dataFolder,chromSize);
			case Commons.CHROMOSOME8 :  return getChr8GC(dataFolder,chromSize);
			case Commons.CHROMOSOME9 :  return getChr9GC(dataFolder,chromSize);
			case Commons.CHROMOSOME10 : return getChr10GC(dataFolder,chromSize);
			case Commons.CHROMOSOME11 : return getChr11GC(dataFolder,chromSize);
			case Commons.CHROMOSOME12 : return getChr12GC(dataFolder,chromSize);
			case Commons.CHROMOSOME13 : return getChr13GC(dataFolder,chromSize);
			case Commons.CHROMOSOME14 : return getChr14GC(dataFolder,chromSize);
			case Commons.CHROMOSOME15 : return getChr15GC(dataFolder,chromSize);
			case Commons.CHROMOSOME16 : return getChr16GC(dataFolder,chromSize);
			case Commons.CHROMOSOME17 : return getChr17GC(dataFolder,chromSize);
			case Commons.CHROMOSOME18 : return getChr18GC(dataFolder,chromSize);
			case Commons.CHROMOSOME19 : return getChr19GC(dataFolder,chromSize);
			case Commons.CHROMOSOME20 : return getChr20GC(dataFolder,chromSize);
			case Commons.CHROMOSOME21 : return getChr21GC(dataFolder,chromSize);
			case Commons.CHROMOSOME22 : return getChr22GC(dataFolder,chromSize);
			case Commons.CHROMOSOMEX  : return getChrXGC(dataFolder,chromSize);
			case Commons.CHROMOSOMEY  : return getChrYGC(dataFolder,chromSize);						
		}
		
		return null;
		
	}
		
	
	public static GCCharArray getChr1GC(String dataFolder,int chromSize) {
		
			GCCharArray chrGCArray =new GCCharArray(chromSize);
			GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME1,chrGCArray);	
			return chrGCArray;
	}
	
	public static GCCharArray getChr2GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME2,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr3GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME3,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr4GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME4,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr5GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME5,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr6GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME6,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr7GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME7,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr8GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME8,chrGCArray);	
		return chrGCArray;
	}
	public static GCCharArray getChr9GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME9,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr10GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME10,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr11GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME11,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr12GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME12,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr13GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME13,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr14GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME14,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr15GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME15,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr16GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME16,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr17GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME17,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr18GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME18,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr19GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME19,chrGCArray);	
		return chrGCArray;
	}
	
	
	
	public static GCCharArray getChr20GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME20,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr21GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME21,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChr22GC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOME22,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChrXGC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOMEX,chrGCArray);	
		return chrGCArray;
	}
	
	public static GCCharArray getChrYGC(String dataFolder,int chromSize) {
		
		GCCharArray chrGCArray =new GCCharArray(chromSize);
		GC.fillChromBasedGCArray(dataFolder,Commons.CHROMOSOMEY,chrGCArray);	
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
