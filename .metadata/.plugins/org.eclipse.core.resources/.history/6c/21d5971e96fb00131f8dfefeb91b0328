/**
 * @author burcakotlu
 * @date May 5, 2014 
 * @time 5:41:08 PM
 */
package intervaltree;

import common.Commons;


/**
 * 
 */
public enum NodeType {
	
	ORIGINAL(1),
	MERGED(2);
	
	private final int nodeType;
	
	 // Constructor
	private NodeType(int nodeType){
        this.nodeType = nodeType;
     }
	
	public int getNodeType(){ 
		 return nodeType; 
	}
	 
	 @Override public String toString(){
	      /*
	      * Either name() or super.toString() may be called here.
	      * name() is final, and always returns the exact name as specified in
	      * declaration; toString() is not final, and is intended for presentation
	      * to the user. It seems best to call name() here.
	      */
		 
		 if (this.isOriginalNode())
			 return "Node Type: " + Commons.ORIGINAL_NODE;
		 else 
			 return "Node Type: " + Commons.MERGED_NODE;
	 }
	 
    /** An added method.  */
    public boolean isOriginalNode() {
      //only ORIGINAL is 'original'
      return  this == ORIGINAL;
    }
    
    /** An added method.  */
    public boolean isMergedNode() {
      //only MERGED is 'merged'
      return  this == MERGED;
    }
	   

}


