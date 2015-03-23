/**
 * @author burcakotlu
 * @date May 9, 2014 
 * @time 3:17:03 PM
 */
package intervaltree;

import enumtypes.ChromosomeName;
import enumtypes.NodeType;

/**
 * 
 */
public class TforHistoneIntervalTreeNodeWithNumbers extends DnaseIntervalTreeNodeWithNumbers {

	// Just for search ENCODE Tfbs and Histone output
	short tforHistoneNumber;

	public short getTforHistoneNumber() {
		return tforHistoneNumber;
	}

	public void setTforHistoneNumber(short tforHistoneNumber) {
		this.tforHistoneNumber = tforHistoneNumber;
	}

	public TforHistoneIntervalTreeNodeWithNumbers(ChromosomeName chromName, int low, int high, short tforHistoneNumber, short cellLineNumber, short fileNumber, NodeType nodeType) {
		super(chromName, low, high, cellLineNumber, fileNumber, nodeType);

		this.tforHistoneNumber = tforHistoneNumber;
	}

}
