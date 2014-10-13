/*
 * This Path Class is to represent any path in an interval tree.
 * Path has its node list and the number of black nodes in the path.
 */
package intervaltree;

import java.util.ArrayList;
import java.util.List;


public class Path {
	
	int numberofBlackNodes;
	List<IntervalTreeNode> nodeList;
	
	public int getNumberofBlackNodes() {
		return numberofBlackNodes;
	}
	public void setNumberofBlackNodes(int numberofBlackNodes) {
		this.numberofBlackNodes = numberofBlackNodes;
	}
	public List<IntervalTreeNode> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<IntervalTreeNode> nodeList) {
		this.nodeList = nodeList;
	}
	public Path() {
		super();
		numberofBlackNodes = 0;
		nodeList = new ArrayList<IntervalTreeNode>();
	}
	public Path(int numberofBlackNodes, List<IntervalTreeNode> nodeList) {
		super();
		//Create a new ArrayList object
		//Since nodeList argument changes rapidly.
		
		List<IntervalTreeNode> list = new ArrayList<IntervalTreeNode>();
		
		for(int i = 0; i<nodeList.size(); i++){
			list.add(nodeList.get(i));
		}
	 	
	 	 
		this.numberofBlackNodes = numberofBlackNodes;
		this.nodeList = list;
	}
	
	
	
	
	

}
