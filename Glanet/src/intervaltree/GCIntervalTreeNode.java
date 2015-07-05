/**
 * 
 */
package intervaltree;

/**
 * @author Bur�ak Otlu
 * @date Apr 10, 2015
 * @project Glanet 
 *
 */
public class GCIntervalTreeNode extends IntervalTreeNode {

	// Just for GC Interval Tree
	short numberofGCs;

	public short getNumberofGCs() {

		return numberofGCs;
	}

	public void setNumberofGCs( short numberofGCs) {

		this.numberofGCs = numberofGCs;
	}

	public GCIntervalTreeNode( int low, int high, short numberofGCs) {

		super( low, high);
		this.numberofGCs = numberofGCs;
	}

}
