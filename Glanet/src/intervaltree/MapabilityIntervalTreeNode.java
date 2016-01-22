
/**
 * @author Burcak Otlu
 * @date Apr 13, 2015
 * @project Glanet 
 *
 */
package intervaltree;

public class MapabilityIntervalTreeNode extends IntervalTreeNode {

	// Mapability
	short mapability;

	public short getMapability() {

		return mapability;
	}

	public void setMapability( short mapability) {

		this.mapability = mapability;
	}

	// CalculateMapability
	public MapabilityIntervalTreeNode( int low, int high, short mapability) {

		super( low, high);
		this.mapability = mapability;
	}

}
