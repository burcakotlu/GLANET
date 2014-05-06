/**
 * @author burcakotlu
 * @date May 5, 2014 
 * @time 5:41:08 PM
 */
package intervaltree;


/**
 * 
 */
public enum NodeType {
	
	ORIGINAL(1),
	MERGED(2);
	
	private final int nodeType;
	
	 // Constructor
	NodeType(int nodeType) {
        this.nodeType = nodeType;
     }
	
	 public int nodeType()           { return nodeType; }
	   

}


