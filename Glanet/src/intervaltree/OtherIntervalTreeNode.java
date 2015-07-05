/**
 * @author burcakotlu
 * @date May 2, 2014 
 * @time 2:34:16 PM
 */
package intervaltree;

import java.util.List;
import common.Commons;
import enumtypes.ChromosomeName;
import enumtypes.NodeName;

/**
 * 
 */
public class OtherIntervalTreeNode extends IntervalTreeNode {

	// Added 7 March 2014
	String rsId;
	List<String> observedVariationAlleles;

	// added 11 December 2013
	int min;

	// added 12 December 2013
	int height;

	public String getRsId() {

		return rsId;
	}

	public void setRsId( String rsId) {

		this.rsId = rsId;
	}

	public List<String> getObservedVariationAlleles() {

		return observedVariationAlleles;
	}

	public void setObservedVariationAlleles( List<String> observedVariationAlleles) {

		this.observedVariationAlleles = observedVariationAlleles;
	}

	public int getMin() {

		return min;
	}

	public void setMin( int min) {

		this.min = min;
	}

	public int getHeight() {

		return height;
	}

	public void setHeight( int height) {

		this.height = height;
	}

	// 7 March 2014
	// For dbSNP node
	public OtherIntervalTreeNode( String rsId, ChromosomeName chrNumber, int chrPosition,
			List<String> observedVariationAlleles) {

		super( chrNumber, chrPosition, chrPosition);

		// no conversion here
		// since it is done in the caller class

		this.rsId = rsId;
		this.observedVariationAlleles = observedVariationAlleles;

	}

	// SENTINEL node
	public OtherIntervalTreeNode() {

		this.color = Commons.BLACK;
		this.nodeName = NodeName.SENTINEL;
		this.numberofBases = 0;
		this.height = -1;

	}
}
