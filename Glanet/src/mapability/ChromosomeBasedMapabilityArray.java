/**
 * @author Burcak Otlu
 * Sep 18, 2013
 * 10:11:38 PM
 * 2013
 *
 * This is an old way of filling Mapability Float Array.
 * In the new way MapabilityIntList and MapabilityShortList is filled in ChromosomeBasedMappabilityTroveList class.
 */
package mapability;

import enrichment.MapabilityFloatArray;
import enumtypes.ChromosomeName;

public class ChromosomeBasedMapabilityArray {

	public static MapabilityFloatArray getChromosomeMapabilityArray(String dataFolder, ChromosomeName chromName, int chromSize) {
		switch (chromName) {
			case CHROMOSOME1:
				return getChr1Mapability(dataFolder, chromSize);
			case CHROMOSOME2:
				return getChr2Mapability(dataFolder, chromSize);
			case CHROMOSOME3:
				return getChr3Mapability(dataFolder, chromSize);
			case CHROMOSOME4:
				return getChr4Mapability(dataFolder, chromSize);
			case CHROMOSOME5:
				return getChr5Mapability(dataFolder, chromSize);
			case CHROMOSOME6:
				return getChr6Mapability(dataFolder, chromSize);
			case CHROMOSOME7:
				return getChr7Mapability(dataFolder, chromSize);
			case CHROMOSOME8:
				return getChr8Mapability(dataFolder, chromSize);
			case CHROMOSOME9:
				return getChr9Mapability(dataFolder, chromSize);
			case CHROMOSOME10:
				return getChr10Mapability(dataFolder, chromSize);
			case CHROMOSOME11:
				return getChr11Mapability(dataFolder, chromSize);
			case CHROMOSOME12:
				return getChr12Mapability(dataFolder, chromSize);
			case CHROMOSOME13:
				return getChr13Mapability(dataFolder, chromSize);
			case CHROMOSOME14:
				return getChr14Mapability(dataFolder, chromSize);
			case CHROMOSOME15:
				return getChr15Mapability(dataFolder, chromSize);
			case CHROMOSOME16:
				return getChr16Mapability(dataFolder, chromSize);
			case CHROMOSOME17:
				return getChr17Mapability(dataFolder, chromSize);
			case CHROMOSOME18:
				return getChr18Mapability(dataFolder, chromSize);
			case CHROMOSOME19:
				return getChr19Mapability(dataFolder, chromSize);
			case CHROMOSOME20:
				return getChr20Mapability(dataFolder, chromSize);
			case CHROMOSOME21:
				return getChr21Mapability(dataFolder, chromSize);
			case CHROMOSOME22:
				return getChr22Mapability(dataFolder, chromSize);
			case CHROMOSOMEX:
				return getChrXMapability(dataFolder, chromSize);
			case CHROMOSOMEY:
				return getChrYMapability(dataFolder, chromSize);
		}

		return null;

	}

	public static MapabilityFloatArray getChr1Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME1, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr2Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME2, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr3Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME3, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr4Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME4, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr5Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME5, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr6Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME6, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr7Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME7, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr8Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME8, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr9Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME9, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr10Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME10, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr11Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME11, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr12Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME12, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr13Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME13, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr14Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME14, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr15Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME15, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr16Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME16, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr17Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME17, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr18Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME18, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr19Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME19, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr20Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME20, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr21Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME21, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChr22Mapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOME22, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChrXMapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOMEX, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	public static MapabilityFloatArray getChrYMapability(String dataFolder, int chromSize) {
		MapabilityFloatArray mapabilityFloatArray = new MapabilityFloatArray(chromSize);
		Mapability.fillChromBasedMapabilityArray(dataFolder, chromSize, ChromosomeName.CHROMOSOMEY, mapabilityFloatArray);
		return mapabilityFloatArray;
	}

	/**
	 * 
	 */
	public ChromosomeBasedMapabilityArray() {
		// TODO Auto-generated constructor stub
	}

}
