
package intervaltree;

import enumtypes.IsochoreFamily;


/**
 * @author Burçak Otlu
 * @date Apr 22, 2015
 * @project Glanet 
 *
 */
public class GCIsochoreIntervalTreeNode extends IntervalTreeNode {

	int numberofGCs;
	IsochoreFamily isochoreFamily;
	

	public int getNumberofGCs() {
		return numberofGCs;
	}

	public void setNumberofGCs(int numberofGCs) {
		this.numberofGCs = numberofGCs;
	}

	public IsochoreFamily getIsochoreFamily() {
		return isochoreFamily;
	}

	public void setIsochoreFamily(IsochoreFamily isochoreFamily) {
		this.isochoreFamily = isochoreFamily;
	}
	
	public GCIsochoreIntervalTreeNode(
			int low, 
			int high,
			int numberofGCs,
			IsochoreFamily isochoreFamily) {
		
		super(low,high);
		this.numberofGCs = numberofGCs;
		this.isochoreFamily = isochoreFamily;
	}
	
 
}
