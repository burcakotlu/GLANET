/**
 * @author Burcak Otlu
 * Sep 18, 2013
 * 10:11:38 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import common.Commons;

import empiricalpvalues.MapabilityFloatArray;

public class ChromosomeBasedMapabilityArray {
	
	public static MapabilityFloatArray getChromosomeMapabilityArray(String chromName, int chromSize){
		switch(chromName){
			case Commons.CHROMOSOME1 :  return getChr1Mapability(chromSize); 
			case Commons.CHROMOSOME2 :  return getChr2Mapability(chromSize);
			case Commons.CHROMOSOME3 :  return getChr3Mapability(chromSize);
			case Commons.CHROMOSOME4 :  return getChr4Mapability(chromSize);
			case Commons.CHROMOSOME5 :  return getChr5Mapability(chromSize);
			case Commons.CHROMOSOME6 :  return getChr6Mapability(chromSize);
			case Commons.CHROMOSOME7 :  return getChr7Mapability(chromSize);
			case Commons.CHROMOSOME8 :  return getChr8Mapability(chromSize);
			case Commons.CHROMOSOME9 :  return getChr9Mapability(chromSize);
			case Commons.CHROMOSOME10 : return getChr10Mapability(chromSize);
			case Commons.CHROMOSOME11 : return getChr11Mapability(chromSize);
			case Commons.CHROMOSOME12 : return getChr12Mapability(chromSize);
			case Commons.CHROMOSOME13 : return getChr13Mapability(chromSize);
			case Commons.CHROMOSOME14 : return getChr14Mapability(chromSize);
			case Commons.CHROMOSOME15 : return getChr15Mapability(chromSize);
			case Commons.CHROMOSOME16 : return getChr16Mapability(chromSize);
			case Commons.CHROMOSOME17 : return getChr17Mapability(chromSize);
			case Commons.CHROMOSOME18 : return getChr18Mapability(chromSize);
			case Commons.CHROMOSOME19 : return getChr19Mapability(chromSize);
			case Commons.CHROMOSOME20 : return getChr20Mapability(chromSize);
			case Commons.CHROMOSOME21 : return getChr21Mapability(chromSize);
			case Commons.CHROMOSOME22 : return getChr22Mapability(chromSize);
			case Commons.CHROMOSOMEX  : return getChrXMapability(chromSize);
			case Commons.CHROMOSOMEY  : return getChrYMapability(chromSize);						
		}
		
		return null;
		
	}
	
	public static MapabilityFloatArray getChr1Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME1,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr2Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME2,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr3Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME3,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr4Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME4,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr5Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME5,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr6Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME6,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr7Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME7,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr8Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME8,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr9Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME9,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr10Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME10,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr11Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME11,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr12Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME12,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr13Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME13,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr14Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME14,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr15Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME15,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr16Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME16,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr17Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME17,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr18Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME18,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr19Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME19,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr20Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME20,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr21Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME21,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr22Mapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOME22,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChrXMapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOMEX,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChrYMapability(int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(chromSize,Commons.CHROMOSOMEY,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	
	
	/**
	 * 
	 */
	public ChromosomeBasedMapabilityArray() {
		// TODO Auto-generated constructor stub
	}

}
