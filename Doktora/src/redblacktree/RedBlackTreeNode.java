/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package redblacktree;

public class RedBlackTreeNode {

	char color;
	RedBlackTreeNode left;
	RedBlackTreeNode right;
	RedBlackTreeNode parent;
	int key;
	public char getColor() {
		return color;
	}
	public void setColor(char color) {
		this.color = color;
	}
	public RedBlackTreeNode getLeft() {
		return left;
	}
	public void setLeft(RedBlackTreeNode left) {
		this.left = left;
	}
	public RedBlackTreeNode getRight() {
		return right;
	}
	public void setRight(RedBlackTreeNode right) {
		this.right = right;
	}
	public RedBlackTreeNode getParent() {
		return parent;
	}
	public void setParent(RedBlackTreeNode parent) {
		this.parent = parent;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	
	
	public RedBlackTreeNode(int key) {
		super();
		this.key = key;
	}
	
	public RedBlackTreeNode() {
		super();
	}

	
	
	
	
	
	
}
