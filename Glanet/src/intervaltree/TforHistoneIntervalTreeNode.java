/**
 * @author burcakotlu
 * @date May 2, 2014 
 * @time 2:18:37 PM
 */
package intervaltree;

import enumtypes.ChromosomeName;
import enumtypes.NodeType;

/**
 * 
 */
public class TforHistoneIntervalTreeNode extends DnaseIntervalTreeNode {

	// Just for search ENCODE Tfbs and Histone output
	String tfbsorHistoneName;

	public String getTfbsorHistoneName() {

		return tfbsorHistoneName;
	}

	public void setTfbsorHistoneName( String tfbsorHistoneName) {

		this.tfbsorHistoneName = tfbsorHistoneName;
	}

	// For Encode tfbs and histone
	public TforHistoneIntervalTreeNode( ChromosomeName chromName, int low, int high, String tfbsorHistoneName,
			String cellLineName, String fileName, NodeType nodeType) {

		super( chromName, low, high, cellLineName, fileName, nodeType);
		this.tfbsorHistoneName = tfbsorHistoneName;

	}

}
