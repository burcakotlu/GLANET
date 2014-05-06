/**
 * @author burcakotlu
 * @date May 6, 2014 
 * @time 1:25:43 PM
 */
package intervaltree;

/**
 * 
 */
public class MapabilityIntervalTreeNode extends IntervalTreeNode{
	
	//Mapability
	float mapability;
	
	

	
	public float getMapability() {
		return mapability;
	}




	public void setMapability(float mapability) {
		this.mapability = mapability;
	}




	//CalculateMapability
	public MapabilityIntervalTreeNode(ChromosomeName chromName, int low, int high, float mapability){
		super(chromName, low, high);
	
		this.mapability = mapability;		
	}

}
