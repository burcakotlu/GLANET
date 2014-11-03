/**
 * 
 */
package intervaltree;

import enumtypes.ChromosomeName;
import enumtypes.NodeType;

/**
 * @author Burçak
 *
 */
public class UserDefinedLibraryIntervalTreeNodeWithNumbers extends IntervalTreeNode{
	
	
//	UserDefinedLibraryIntervalTreeNodeWithNumbers node = 
//	new UserDefinedLibraryIntervalTreeNodeWithNumbers(chromName,startPosition,endPosition,elementNumber,fileNumber,NodeType.ORIGINAL);
	
	//Just for search UserDefinedLibrary Output
	int elementNumber;
	int fileNumber;
	
	
	public int getElementNumber() {
		return elementNumber;
	}
	public void setElementNumber(int elementNumber) {
		this.elementNumber = elementNumber;
	}
	
	public int getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}
	
	
	public UserDefinedLibraryIntervalTreeNodeWithNumbers(
			ChromosomeName chromName, int low, int high, int elementNumber,
			int fileNumber, NodeType nodeType) {
	
		super(chromName,low,high,nodeType);
		
		
		this.elementNumber = elementNumber;
		this.fileNumber = fileNumber;
	}
	
	
	
	

}
