/**
 * @author Burcak Otlu
 *
 * 
 */

package intervaltree;

import common.Commons;
import enumtypes.ChromosomeName;
import enumtypes.NodeName;
import enumtypes.NodeType;


public class IntervalTreeNode{
	
	//Basic attributes
	int max;
	int low;
	int high;
	
	//Enum types
	//Enum type ChromosomeName can be CHROMOSOME1,CHROMOSOME2,...,CHROMOSOMEY
	ChromosomeName chromName;
	//Enum type NodeName can be SENTINEL or NOT_SENTINEL
	NodeName nodeName;
	//Enum type NodeType can be ORIGINAL or MERGED
	NodeType nodeType;


	int numberofBases;		
	char color;
	IntervalTreeNode left;
	IntervalTreeNode right;
	IntervalTreeNode parent;

		
	
	
	
	public NodeName getNodeName() {
		return nodeName;
	}

	public void setNodeName(NodeName nodeName) {
		this.nodeName = nodeName;
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	public ChromosomeName getChromName() {
		return chromName;
	}

	public void setChromName(ChromosomeName chromName) {
		this.chromName = chromName;
	}

	

	public int getNumberofBases() {
		
		if (this.getNodeName().isNotSentinel()){
			return (this.getHigh()-this.getLow()+1);
		}else{
				return 0;
		}
		
		
		
			
		
	}

	public void setNumberofBases(int numberofBases) {
		this.numberofBases = numberofBases;
	}

	
	
	

//	public boolean isSentinel(){
//		if (Commons.SENTINEL.equals(this.getName())){
//			return true;
//		}else 
//			return false;
//		
//	}
//	
//	public boolean isNotSentinel(){
//		if (NodeName.NOT_SENTINEL.equals(this.getName())){
//			return true;
//		}else 
//			return false;
//		
//	}
	
	
	
	
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	
	
	
	
	


	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public char getColor() {
		return color;
	}
	public void setColor(char color) {
		this.color = color;
	}
	public IntervalTreeNode getLeft() {
		return left;
	}
	public void setLeft(IntervalTreeNode left) {
		this.left = left;
	}
	public IntervalTreeNode getRight() {
		return right;
	}
	public void setRight(IntervalTreeNode right) {
		this.right = right;
	}
	public IntervalTreeNode getParent() {
		return parent;
	}
	public void setParent(IntervalTreeNode parent) {
		this.parent = parent;
	}
	
	
	
	
	

	
	
	//SENTINEL node
	public IntervalTreeNode(){
		this.color = Commons.BLACK;			
		this.nodeName = NodeName.SENTINEL;
		this.numberofBases = 0;
	}
	
	//testing interval tree operations
	public IntervalTreeNode(int low, int high) {
		super();
		this.low = low;
		this.high = high;
		
		this.left = new IntervalTreeNode();
		this.right = new IntervalTreeNode();
		this.parent = new IntervalTreeNode();
		this.nodeName = NodeName.NOT_SENTINEL;
		this.numberofBases = high-low+1;
					
	}
	
	
	
	//process input data
	//remove overlaps
	public IntervalTreeNode(ChromosomeName chromName, int low, int high) {
		this.chromName = chromName;
		this.low = low;
		this.high = high;
		
		this.left = new IntervalTreeNode();
		this.right = new IntervalTreeNode();
		this.parent = new IntervalTreeNode();
		this.nodeName = NodeName.NOT_SENTINEL;
		this.numberofBases = high-low+1;
					

	}
	
	//process input data
	//remove overlaps
	//merged interval tree node
	public IntervalTreeNode(ChromosomeName chromName, int low, int high,NodeType nodeType) {
		this.chromName = chromName;
		this.low = low;
		this.high = high;
		this.nodeType = nodeType;
		
		this.left = new IntervalTreeNode();
		this.right = new IntervalTreeNode();
		this.parent = new IntervalTreeNode();
		this.nodeName = NodeName.NOT_SENTINEL;
		this.numberofBases = high-low+1;
					

	}
		
	
	

	

}
