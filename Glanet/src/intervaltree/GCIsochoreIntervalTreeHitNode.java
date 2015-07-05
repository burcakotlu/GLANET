/**
 * 
 */
package intervaltree;

import enumtypes.IsochoreFamily;

/**
 * @author Bur�ak Otlu
 * @date Apr 28, 2015
 * @project Glanet 
 *
 */
public class GCIsochoreIntervalTreeHitNode extends GCIsochoreIntervalTreeNode {

	int numberofOverlappingBases;

	public int getNumberofOverlappingBases() {

		return numberofOverlappingBases;
	}

	public void setNumberofOverlappingBases( int numberofOverlappingBases) {

		this.numberofOverlappingBases = numberofOverlappingBases;
	}

	public GCIsochoreIntervalTreeHitNode( int low, int high, int numberofGCs, IsochoreFamily isochoreFamily,
			int numberofOverlappingBases) {

		super( low, high, numberofGCs, isochoreFamily);
		this.numberofOverlappingBases = numberofOverlappingBases;
	}

}
