/**
 * @author Burcak Otlu
 * Sep 18, 2013
 * 10:11:38 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import empiricalpvalues.MapabilityFloatArray;

import common.Commons;

public class ChromosomeBasedMapabilityArray {
	
	public static MapabilityFloatArray getChromosomeMapabilityArray(String dataFolder,String chromName, int chromSize){
		switch(chromName){
			case Commons.CHROMOSOME1 :  return getChr1Mapability(dataFolder,chromSize); 
			case Commons.CHROMOSOME2 :  return getChr2Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME3 :  return getChr3Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME4 :  return getChr4Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME5 :  return getChr5Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME6 :  return getChr6Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME7 :  return getChr7Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME8 :  return getChr8Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME9 :  return getChr9Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME10 : return getChr10Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME11 : return getChr11Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME12 : return getChr12Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME13 : return getChr13Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME14 : return getChr14Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME15 : return getChr15Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME16 : return getChr16Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME17 : return getChr17Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME18 : return getChr18Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME19 : return getChr19Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME20 : return getChr20Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME21 : return getChr21Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOME22 : return getChr22Mapability(dataFolder,chromSize);
			case Commons.CHROMOSOMEX  : return getChrXMapability(dataFolder,chromSize);
			case Commons.CHROMOSOMEY  : return getChrYMapability(dataFolder,chromSize);						
		}
		
		return null;
		
	}
	
	public static MapabilityFloatArray getChr1Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME1,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr2Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME2,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr3Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME3,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr4Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME4,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr5Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME5,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr6Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME6,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr7Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME7,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr8Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME8,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr9Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME9,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr10Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME10,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr11Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME11,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr12Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME12,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr13Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME13,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr14Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME14,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr15Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME15,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr16Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME16,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr17Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME17,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr18Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME18,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr19Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME19,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr20Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME20,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr21Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME21,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr22Mapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOME22,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChrXMapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOMEX,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChrYMapability(String dataFolder,int chromSize) {	
		MapabilityFloatArray mapabilityFloatArray =new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder,chromSize,Commons.CHROMOSOMEY,mapabilityFloatArray);	
		return mapabilityFloatArray;
	}

	
	
	/**
	 * 
	 */
	public ChromosomeBasedMapabilityArray() {
		// TODO Auto-generated constructor stub
	}

}
