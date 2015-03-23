/**
 * @author burcakotlu
 * @date May 2, 2014 
 * @time 2:17:03 PM
 */
package intervaltree;

import enumtypes.ChromosomeName;
import enumtypes.NodeType;

/**
 * 
 */
public class DnaseIntervalTreeNode extends IntervalTreeNode {

	// Just for search ENCODE Dnase output
	String cellLineName;
	String fileName;

	public String getCellLineName() {
		return cellLineName;
	}

	public void setCellLineName(String cellLineName) {
		this.cellLineName = cellLineName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	// For Encode dnase
	public DnaseIntervalTreeNode(ChromosomeName chromName, int low, int high, String cellLineName, String fileName, NodeType nodeType) {
		super(chromName, low, high, nodeType);

		this.cellLineName = cellLineName;
		this.fileName = fileName;

	}

}
