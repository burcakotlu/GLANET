/**
 * @author burcakotlu
 * @date May 9, 2014 
 * @time 3:13:23 PM
 */
package intervaltree;

import enumtypes.ChromosomeName;
import enumtypes.NodeType;

/**
 * 
 */
public class DnaseIntervalTreeNodeWithNumbers extends IntervalTreeNode {

	// Just for search ENCODE Dnase output
	short cellLineNumber;
	short fileNumber;

	public short getCellLineNumber() {

		return cellLineNumber;
	}

	public void setCellLineNumber( short cellLineNumber) {

		this.cellLineNumber = cellLineNumber;
	}

	public short getFileNumber() {

		return fileNumber;
	}

	public void setFileNumber( short fileNumber) {

		this.fileNumber = fileNumber;
	}

	// For Encode dnase
	public DnaseIntervalTreeNodeWithNumbers( ChromosomeName chromName, int low, int high, short cellLineNumber,
			short fileNumber, NodeType nodeType) {

		super( chromName, low, high, nodeType);

		this.cellLineNumber = cellLineNumber;
		this.fileNumber = fileNumber;

	}

}
