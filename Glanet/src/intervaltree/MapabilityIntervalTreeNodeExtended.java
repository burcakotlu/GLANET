/**
 * @author burcakotlu
 * @date May 6, 2014 
 * @time 1:25:43 PM
 */


package intervaltree;

import enumtypes.ChromosomeName;


public class MapabilityIntervalTreeNodeExtended extends IntervalTreeNode{

	// Mapability
	float mapability;

	public float getMapability() {
		return mapability;
	}

	public void setMapability(short mapability) {
		this.mapability = mapability;
	}

	// CalculateMapability
	public MapabilityIntervalTreeNodeExtended(ChromosomeName chrName,int low, int high, float mapability) {
		super(chrName,low, high);
		this.mapability = mapability;
	}

}
