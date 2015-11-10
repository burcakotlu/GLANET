/**
 * 
 */
package intervaltree;

import enumtypes.ChromosomeName;
import enumtypes.NodeType;

/**
 * @author Burcak
 *
 */
public class UserDefinedLibraryIntervalTreeNodeWithNumbers extends IntervalTreeNode {

	// UserDefinedLibraryIntervalTreeNodeWithNumbers node =
	// new
	// UserDefinedLibraryIntervalTreeNodeWithNumbers(chromName,startPosition,endPosition,elementNumber,fileNumber,NodeType.ORIGINAL);

	// Just for search UserDefinedLibrary Output
	int elementTypeNumber;
	int elementNumber;
	int fileNumber;

	public int getElementNumber() {

		return elementNumber;
	}

	public void setElementNumber( int elementNumber) {

		this.elementNumber = elementNumber;
	}

	public int getFileNumber() {

		return fileNumber;
	}

	public void setFileNumber( int fileNumber) {

		this.fileNumber = fileNumber;
	}

	public int getElementTypeNumber() {

		return elementTypeNumber;
	}

	public void setElementTypeNumber( int elementTypeNumber) {

		this.elementTypeNumber = elementTypeNumber;
	}

	public UserDefinedLibraryIntervalTreeNodeWithNumbers(
			ChromosomeName chromName, 
			int low, 
			int high,
			int elementTypeNumber, 
			int elementNumber, 
			int fileNumber, 
			NodeType nodeType) {

		super(chromName,low,high,nodeType);

		this.elementTypeNumber = elementTypeNumber;
		this.elementNumber = elementNumber;
		this.fileNumber = fileNumber;
	}

}
