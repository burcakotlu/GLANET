/**
 * @author Burcak Otlu
 * Sep 18, 2013
 * 10:07:56 PM
 * 2013
 *
 * 
 */
package enrichment;

public class MapabilityFloatArray {

	float[] mapabilityArray;

	public float[] getMapabilityArray() {

		return mapabilityArray;
	}

	public void setMapabilityArray( float[] mapabilityArray) {

		this.mapabilityArray = mapabilityArray;
	}

	/**
	 * 
	 */
	public MapabilityFloatArray( int chromSize) {

		this.mapabilityArray = new float[chromSize];
	}

	public MapabilityFloatArray() {

	}
}
