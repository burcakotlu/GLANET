/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

import ui.GlanetRunner;

package redblacktree;

public class RedBlackTree {
	
	RedBlackTreeNode root;
	
	
//	Red-Black trees are one of many search-tree schemes that are balanced
//	in order to guarantee that basic dynamic-set operations take O(lgn) time in the worst case
	
//	Properties of Red-Black tree
//	It is a binary search tree with one extra bit of strorage per node:its color,
//	which can be either RED or BLACK.

//	A binary search tree is a red-black tree if it satisfies the following red-black properties
//	1. Every node is either red or black.
//	2. The root is black. 
//	3. Every leaf(null) is black.
//	4. If a node is red, the both of its children are black.
//	5. For each node, all paths from the node to descendant leaves contain the same  number of black nodes.
	
	
	public RedBlackTreeNode getRoot() {
		return root;
	}



	public void setRoot(RedBlackTreeNode root) {
		this.root = root;
	}

	public void redBlackTreeInsertFixUp(RedBlackTree tree, RedBlackTreeNode z){
		RedBlackTreeNode y;
		
			while(z.getParent()!=null && z.getParent().getColor()=='r'){
				if ((z.getParent().getParent().getLeft()) == z.getParent()){
					y= z.getParent().getParent().getRight();
					if (y!=null && y.getColor()=='r'){
						z.getParent().setColor('b'); 				//Case1
						y.setColor('b');             				//Case1
						z.getParent().getParent().setColor('r'); 	//Case1
						z = z.getParent().getParent(); 				//Case1
					}else if (z==z.getParent().getRight()){
						z = z.getParent();							//Case2
						redBlackTreeLeftRotate(tree, z);			//Case2
					}else{
						z.getParent().setColor('b');								//Case3
						z.getParent().getParent().setColor('r');					//Case3 
						redBlackTreeRightRotate(tree,z.getParent().getParent()); 	//Case3
					}
				}else{
					y= z.getParent().getParent().getLeft();
					if (y!=null && y.getColor()=='r'){
						z.getParent().setColor('b'); 				//Case1
						y.setColor('b');             				//Case1
						z.getParent().getParent().setColor('r'); 	//Case1
						z = z.getParent().getParent(); 				//Case1
					}else if (z==z.getParent().getLeft()){
						z = z.getParent();							//Case2
						redBlackTreeRightRotate(tree, z);			//Case2
					}else{
						z.getParent().setColor('b');								//Case3
						z.getParent().getParent().setColor('r');					//Case3 
						redBlackTreeLeftRotate(tree,z.getParent().getParent()); 	//Case3
					}

				}							
			}
		tree.getRoot().setColor('b');
	}

	public void  redBlackTreeInsert(RedBlackTree tree, RedBlackTreeNode z){
		RedBlackTreeNode y = null;
		RedBlackTreeNode x = tree.getRoot();
		
//		This while sets the parent for the new inserted node 
		while(x!=null){
			y = x;
			if (z.getKey()< x.getKey())
				x = x.getLeft();
			else 
				x = x.getRight();
		}
		
		z.setParent(y);
		
//		This part sets whether the new inserted node is the left or right child of parent
		if (y==null){//enters for the first insert
			tree.setRoot(z);
		} else{
			if (z.getKey()<y.getKey()){
				y.setLeft(z);
			}else{
				y.setRight(z);
			}			
		}
		
//		sets the left right color attributes of the new inserted node
		z.setLeft(null);
		z.setRight(null);
		z.setColor('r');
		redBlackTreeInsertFixUp(tree,z);
	}
	
	
		
	
//	We change the pointer structure with Rotation  
//	When we do a left rotation on a node x,
//	we assume that its right child y is not null.
//	Left rotation pivots around the link from x to y.
//	It makes the y the new root of the subtree, with x as the new left child of y 
//	and y's left child becomes x's right child
//	burcak: left rotate edilen node x right child'inin y diyelim, left child'i oluyor
	public void redBlackTreeLeftRotate(RedBlackTree tree, RedBlackTreeNode x){
		RedBlackTreeNode y = x.getRight();
		
		if(y==null){
			GlanetRunner.appendLog("Invalid Left Rotate Operation");
		}
		else {
			x.setRight(y.getLeft());
			if(y.getLeft()!=null){
				y.getLeft().setParent(x);
			}
			y.setParent(x.getParent());
			
			if(x.getParent()==null){
				tree.setRoot(y);
			} else{
				if(x==(x.getParent().getLeft())){
					x.getParent().setLeft(y);
				}else{
					x.getParent().setRight(y);
				}	
			}
			
			y.setLeft(x);
			x.setParent(y);
				
		}

	}

	
//	We change the pointer structure with Rotation  
//	When we do a right rotation on a node x,
//	we assume that its right child y is not null.
//	Right rotation pivots around the link from x to y.
//	It makes the x the new root of the subtree, with y as the new right child of y 
//	and y's left child becomes x's right child
//	burcak: right rotate edilen node x left child'inin y diyelim, right child'i oluyor
	public void redBlackTreeRightRotate(RedBlackTree tree, RedBlackTreeNode x){
		RedBlackTreeNode y = x.getLeft();
		
		if(y==null){
			GlanetRunner.appendLog("Invalid Right Rotate Operation");
		}
		else {
			x.setLeft(y.getRight());
			if(y.getRight()!=null){
				y.getRight().setParent(x);
			}
			y.setParent(x.getParent());
			
			if(x.getParent()==null){
				tree.setRoot(y);
			} else{
				if(x==(x.getParent().getLeft())){
					x.getParent().setLeft(y);
				}else{
					x.getParent().setRight(y);
				}	
			}
			
			y.setRight(x);
			x.setParent(y);
				
		}
	}
	
	


	
	
	public RedBlackTree() {
		super();
		root = null;
	}

	public void redBlackTreeInfixTraversal(RedBlackTreeNode node){
		
		if (node.getLeft()!=null)
			redBlackTreeInfixTraversal(node.getLeft());
		
		GlanetRunner.appendLog(node.getKey());
		
		if (node.getRight()!=null)
			redBlackTreeInfixTraversal(node.getRight());
				
	}

	public static void main(String[] args){
		
		RedBlackTree tree = new RedBlackTree();
		RedBlackTreeNode node1 = new RedBlackTreeNode(10);
		RedBlackTreeNode node2 = new RedBlackTreeNode(5);
		RedBlackTreeNode node3 = new RedBlackTreeNode(15);
		RedBlackTreeNode node4 = new RedBlackTreeNode(2);
		RedBlackTreeNode node5 = new RedBlackTreeNode(1);
		RedBlackTreeNode node6 = new RedBlackTreeNode(3);
		RedBlackTreeNode node7 = new RedBlackTreeNode(6);
		RedBlackTreeNode node8 = new RedBlackTreeNode(7);
		RedBlackTreeNode node9 = new RedBlackTreeNode(8);
		RedBlackTreeNode node10 = new RedBlackTreeNode(4);
		RedBlackTreeNode node11 = new RedBlackTreeNode(40);
		RedBlackTreeNode node12 = new RedBlackTreeNode(60);
		RedBlackTreeNode node13 = new RedBlackTreeNode(50);
		RedBlackTreeNode node14 = new RedBlackTreeNode(55);

		tree.redBlackTreeInsert(tree,node1);
		tree.redBlackTreeInsert(tree,node2);
		tree.redBlackTreeInsert(tree,node3);
		tree.redBlackTreeInsert(tree,node4);
		tree.redBlackTreeInsert(tree,node5);
		tree.redBlackTreeInsert(tree,node6);
		tree.redBlackTreeInsert(tree,node7);
		tree.redBlackTreeInsert(tree,node8);
		tree.redBlackTreeInsert(tree,node9);
		tree.redBlackTreeInsert(tree,node10);
		tree.redBlackTreeInsert(tree,node11);
		tree.redBlackTreeInsert(tree,node12);
		tree.redBlackTreeInsert(tree,node13);
		tree.redBlackTreeInsert(tree,node14);
		
		if (tree.getRoot()!=null)
			tree.redBlackTreeInfixTraversal(tree.getRoot());			
	}
}
