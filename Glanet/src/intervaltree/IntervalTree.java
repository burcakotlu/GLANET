/**
 * @author Burcak Otlu
 *
 * 
 */

/*
 * Interval Tree is a red black tree which is augmented with max attribute.
 * Red black tree is a variation of binary search tree + 1 bit per node: an attribute color, which is either red or black.
 * Red black tree is balanced, its height is O(log n), where is n is the number of nodes.
 * Operations will take O(log n) time in the worst case. 
 * 
 * Red black tree properties:
 * 1. Every node is either red or black.
 * 2. The root is black.
 * 3. Every leaf(nil[T]) is black.
 * 4. If a node is red, then both its children are black. (Hence no two reds in a row on a simple path from the root to a leaf.)
 * 5. For each node, all paths from the node to descendant leaves contain the same number of black nodes.
 * 
 * This Interval Tree implementation is based on Introduction to Algorithms book of Cormen et al. 2nd Edition
 * 
 * Please note that IntervalTreeDelete function does not handle erroneous inputs:
 * Like deleting a node which does not exists in the interval tree 
 * and deleting the root of tree when there is no NOT_SENTINEL node has left.
 * 
 * 
 */
package intervaltree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ui.GlanetRunner;
import annotation.OverlapInformation;
import annotation.PermutationNumberTfNameCellLineNameOverlap;
import annotation.PermutationNumberTfNumberCellLineNumberOverlap;
import annotation.PermutationNumberUcscRefSeqGeneNumberOverlap;
import annotation.PermutationNumberUcscRefSeqGeneOverlap;
import annotation.TFNumberCellLineNumberOverlap;
import annotation.TfCellLineOverlapWithNumbers;
import annotation.TfNameandCellLineNameOverlap;
import annotation.UcscRefSeqGeneOverlap;
import annotation.UcscRefSeqGeneOverlapWithNumbers;
import auxiliary.FileOperations;

import common.Commons;

import datadrivenexperiment.DataDrivenExperimentIntervalTreeNode;
import datadrivenexperiment.IntervalDataDrivenExperiment;
import enrichment.InputLineMinimal;
import enumtypes.AnnotationType;
import enumtypes.CalculateGC;
import enumtypes.ChromosomeName;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.GeneSetAnalysisType;
import enumtypes.GeneSetType;
import enumtypes.GeneratedMixedNumberDescriptionOrderLength;
import enumtypes.KeggPathwayAnalysisType;
import enumtypes.NodeName;
import enumtypes.NodeType;
import enumtypes.RegulatorySequenceAnalysisType;
import enumtypes.WriteElementBasedAnnotationFoundOverlapsMode;
import gc.GCIsochoreIntervalTreeFindAllOverlapsResult;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.list.TIntList;
import gnu.trove.map.TIntByteMap;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.TShortObjectMap;

//public class IntervalTree  extends TLinkableAdapter<IntervalTree>{
public class IntervalTree {

	final static Logger logger = Logger.getLogger(IntervalTree.class);

	IntervalTreeNode root;
	int numberofNodes;
	long numberofNonOverlappingBases;

	public long getNumberofNonOverlappingBases() {

		return numberofNonOverlappingBases;
	}

	public void setNumberofNonOverlappingBases( long numberofNonOverlappingBases) {

		this.numberofNonOverlappingBases = numberofNonOverlappingBases;
	}

	public int getNumberofNodes() {

		return numberofNodes;
	}

	public void setNumberofNodes( int numberofNodes) {

		this.numberofNodes = numberofNodes;
	}

	public IntervalTreeNode getRoot() {

		return root;
	}

	public void setRoot( IntervalTreeNode root) {

		this.root = root;
	}

	public IntervalTree() {

		super();
		root = new IntervalTreeNode();
		this.numberofNodes = 0;
		this.numberofNonOverlappingBases = 0;
	}

	public static int maximum( int x, int y, int z) {

		int max = x;

		if( y > max)
			max = y;
		if( z > max)
			max = z;

		return max;
	}

	public static int minimum( int x, int y, int z) {

		int min = x;

		if( y < min)
			min = y;
		if( z < min)
			min = z;

		return min;
	}

	public static int max( IntervalTreeNode node) {

		int a = Integer.MIN_VALUE;
		int b = Integer.MIN_VALUE;

		if( node.getLeft().getNodeName().isNotSentinel())
			a = node.getLeft().getMax();

		if( node.getRight().getNodeName().isNotSentinel())
			b = node.getRight().getMax();

		return maximum( node.getHigh(), a, b);

	}

	public static int min( IntervalTreeNode node) {

		int a = Integer.MAX_VALUE;
		int b = Integer.MAX_VALUE;

		if( node.getLeft().getNodeName().isNotSentinel())
			a = ( ( OtherIntervalTreeNode)( node.getLeft())).getMin();

		if( node.getRight().getNodeName().isNotSentinel())
			b = ( ( OtherIntervalTreeNode)( node.getRight())).getMin();

		return minimum( node.getLow(), a, b);

	}

	public void incrementHeightByOne( IntervalTreeNode node) {

		( ( OtherIntervalTreeNode)node).setHeight( ( ( OtherIntervalTreeNode)node).getHeight() + 1);

		if( node.getLeft().getNodeName().isNotSentinel()){
			incrementHeightByOne( node.getLeft());
		}
		if( node.getRight().getNodeName().isNotSentinel()){
			incrementHeightByOne( node.getRight());
		}

	}

	public void decrementHeightByOne( IntervalTreeNode node) {

		( ( OtherIntervalTreeNode)node).setHeight( ( ( OtherIntervalTreeNode)node).getHeight() - 1);

		if( node.getLeft().getNodeName().isNotSentinel()){
			decrementHeightByOne( node.getLeft());
		}
		if( node.getRight().getNodeName().isNotSentinel()){
			decrementHeightByOne( node.getRight());
		}

	}
	
	//later on delete this method
	//debug starts
	public void intervalTreeLeftRotate(long permutationNumberMixedNumber, IntervalTree tree, IntervalTreeNode x) {

		
		//debug starts
		logger.info("permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +
					"root of the tree" + "\t" + tree.getRoot().getChromName() + "\t" + tree.getRoot().getColor() + "\t" + tree.getRoot().getLow() + "\t" + tree.getRoot().getHigh());
		
		logger.info("permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +
				"node x" + "\t" + x.getChromName() + "\t" + x.getColor() + "\t" + x.getLow() + "\t" + x.getHigh());

		logger.info( "permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + "InfixTraversalofTheTree Before the LeftRotate");
		
		logger.info( "permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + "Root of the tree is: " + tree.getRoot().getChromName() + " " +  tree.getRoot().getColor() + " " + tree.getRoot().getLow() + " "+ tree.getRoot().getHigh());
		
		tree.intervalTreeInfixTraversal(permutationNumberMixedNumber, tree.getRoot());
		//debug ends
		
		IntervalTreeNode y = x.getRight();
		
		//debug starts
		if (y == null){
			logger.error("permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + "STOP here LeftRotate1");
			logger.error("permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +" x: " + x.getChromName() + "\t" + x.getColor() + "\t" + x.getLow() + "\t" + x.getHigh());
		}
		//debug ends

		x.setRight( y.getLeft());
		
		//debug starts
		if (y.getLeft() == null){
			logger.error("permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + "STOP here LeftRotate2");
			logger.error("permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + " y: " + y.getChromName() + "\t" + y.getColor() + "\t" + y.getLow() + "\t" + y.getHigh());
		}
		//debug ends
		
		if( !NodeName.SENTINEL.equals( y.getLeft().getNodeName())){
			y.getLeft().setParent( x);
		}
		y.setParent( x.getParent());

		if( NodeName.SENTINEL.equals( x.getParent().getNodeName())){
			tree.setRoot( y);
		}else{
			if( x == ( x.getParent().getLeft())){
				x.getParent().setLeft( y);
			}else{
				x.getParent().setRight( y);
			}
		}

		y.setLeft( x);
		x.setParent( y);

		// update max attributes
		x.setMax( max( x));
		y.setMax( max( y));

		// update min attributes
		if( x instanceof OtherIntervalTreeNode && y instanceof OtherIntervalTreeNode){
			( ( OtherIntervalTreeNode)x).setMin( min( x));
			( ( OtherIntervalTreeNode)y).setMin( min( y));

			// update height attributes
			( ( OtherIntervalTreeNode)x).setHeight( ( ( OtherIntervalTreeNode)x).getHeight() + 1);
			if( x.getLeft().getNodeName().isNotSentinel()){
				incrementHeightByOne( x.getLeft());
			}

			( ( OtherIntervalTreeNode)y).setHeight( ( ( OtherIntervalTreeNode)y).getHeight() - 1);
			if( y.getRight().getNodeName().isNotSentinel()){
				decrementHeightByOne( y.getRight());
			}

		}
		
		//debug starts
		logger.info( "permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + "InfixTraversalofTheTree After the LeftRotate");
		logger.info( "permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + "Root of the tree is: " + tree.getRoot().getChromName() + " " +  tree.getRoot().getColor() + " " + tree.getRoot().getLow() + " "+ tree.getRoot().getHigh());
		
		tree.intervalTreeInfixTraversal(permutationNumberMixedNumber, tree.getRoot());
		//debug ends

	}
	//debug ends
	

	// We change the pointer structure with Rotation
	// When we do a left rotation on a node x,
	// we assume that its right child y is not null.
	// Left rotation pivots around the link from x to y.
	// It makes the y the new root of the subtree, with x as the new left child of y
	// and y's left child becomes x's right child
	// burcak: left rotated node x, becomes its formerly right child's (let's call it y)'s left  child.
	public void intervalTreeLeftRotate( IntervalTree tree, IntervalTreeNode x) {

		IntervalTreeNode y = x.getRight();
		
		//debug starts
		if (y == null){
			logger.error("STOP here LeftRotate1");
			logger.error(x.getChromName() + "\t" + x.getColor() + "\t" + x.getLow() + "\t" + x.getHigh());
		}
		//debug ends

		x.setRight( y.getLeft());
		
		//debug starts
		if (y.getLeft() == null){
			logger.error("STOP here LeftRotate2");
			logger.error(y.getChromName() + "\t" + y.getColor() + "\t" + y.getLow() + "\t" + y.getHigh());
		}
		//debug ends
		
		if( !NodeName.SENTINEL.equals( y.getLeft().getNodeName())){
			y.getLeft().setParent( x);
		}
		y.setParent( x.getParent());

		if( NodeName.SENTINEL.equals( x.getParent().getNodeName())){
			tree.setRoot( y);
		}else{
			if( x == ( x.getParent().getLeft())){
				x.getParent().setLeft( y);
			}else{
				x.getParent().setRight( y);
			}
		}

		y.setLeft( x);
		x.setParent( y);

		// update max attributes
		x.setMax( max( x));
		y.setMax( max( y));

		// update min attributes
		if( x instanceof OtherIntervalTreeNode && y instanceof OtherIntervalTreeNode){
			( ( OtherIntervalTreeNode)x).setMin( min( x));
			( ( OtherIntervalTreeNode)y).setMin( min( y));

			// update height attributes
			( ( OtherIntervalTreeNode)x).setHeight( ( ( OtherIntervalTreeNode)x).getHeight() + 1);
			if( x.getLeft().getNodeName().isNotSentinel()){
				incrementHeightByOne( x.getLeft());
			}

			( ( OtherIntervalTreeNode)y).setHeight( ( ( OtherIntervalTreeNode)y).getHeight() - 1);
			if( y.getRight().getNodeName().isNotSentinel()){
				decrementHeightByOne( y.getRight());
			}

		}

	}

	// burcak: right rotate edilen node x left child'inin y diyelim, right child'i oluyor
	public void intervalTreeRightRotate( IntervalTree tree, IntervalTreeNode x) {

		IntervalTreeNode y = x.getLeft();

		x.setLeft( y.getRight());
		if( !NodeName.SENTINEL.equals( y.getRight().getNodeName())){
			y.getRight().setParent( x);
		}
		y.setParent( x.getParent());

		if( NodeName.SENTINEL.equals( x.getParent().getNodeName())){
			tree.setRoot( y);
		}else{
			if( x == ( x.getParent().getRight())){
				x.getParent().setRight( y);
			}else{
				x.getParent().setLeft( y);
			}
		}

		y.setRight( x);
		x.setParent( y);

		// update max attributes
		x.setMax( max( x));
		y.setMax( max( y));

		if( x instanceof OtherIntervalTreeNode && y instanceof OtherIntervalTreeNode){
			// update min attributes
			( ( OtherIntervalTreeNode)x).setMin( min( x));
			( ( OtherIntervalTreeNode)y).setMin( min( y));

			// update height attributes
			( ( OtherIntervalTreeNode)x).setHeight( ( ( OtherIntervalTreeNode)x).getHeight() + 1);
			if( x.getRight().getNodeName().isNotSentinel()){
				incrementHeightByOne( x.getRight());
			}

			( ( OtherIntervalTreeNode)y).setHeight( ( ( OtherIntervalTreeNode)y).getHeight() - 1);
			if( y.getLeft().getNodeName().isNotSentinel()){
				decrementHeightByOne( y.getLeft());
			}
		}

	}

	public void intervalTreeDeleteFixUp( IntervalTree tree, IntervalTreeNode x) {

		IntervalTreeNode w;

		while( x != tree.getRoot() && x.getColor() == Commons.BLACK){
			if( x == ( x.getParent()).getLeft()){
				w = x.getParent().getRight();

				// case1: w is red.
				if( w.getColor() == Commons.RED){
					w.setColor( Commons.BLACK); // Case1
					x.getParent().setColor( Commons.RED); // Case1
					intervalTreeLeftRotate( tree, x.getParent()); // Case1
					w = x.getParent().getRight(); // Case1
				}

				// case2: w is black and both of w's children are black
				if( w.getLeft().getColor() == Commons.BLACK && w.getRight().getColor() == Commons.BLACK){
					w.setColor( Commons.RED); // Case2
					x = x.getParent(); // Case2
				}else{
					// case3: w is black, w's left child is red and w's right
					// child is black
					if( ( w.getRight()).getColor() == Commons.BLACK){
						w.getLeft().setColor( Commons.BLACK); // Case3
						w.setColor( Commons.RED); // Case3
						intervalTreeRightRotate( tree, w); // Case3
						w = x.getParent().getRight(); // Case3
					}
					// case4: w is black, w's left child is black and w's right
					// child is red.
					w.setColor( x.getParent().getColor()); // Case4
					x.getParent().setColor( Commons.BLACK); // Case4
					w.getRight().setColor( Commons.BLACK); // Case4
					intervalTreeLeftRotate( tree, x.getParent()); // Case4
					x = tree.getRoot(); // Case4

				}

			}else{

				w = x.getParent().getLeft();

				// case1: w is red.
				if( w.getColor() == Commons.RED){
					w.setColor( Commons.BLACK); // Case1
					x.getParent().setColor( Commons.RED); // Case1
					intervalTreeRightRotate( tree, x.getParent()); // Case1
					w = x.getParent().getLeft(); // Case1
				}

				// case2: w is black and both of w's children are black
				if( w.getRight().getColor() == Commons.BLACK && w.getLeft().getColor() == Commons.BLACK){
					w.setColor( Commons.RED); // Case2
					x = x.getParent(); // Case2
				}else{
					// case3: w is black, w's right child is red and w's left
					// child is black
					if( ( w.getLeft()).getColor() == Commons.BLACK){
						w.getRight().setColor( Commons.BLACK); // Case3
						w.setColor( Commons.RED); // Case3
						intervalTreeLeftRotate( tree, w); // Case3
						w = x.getParent().getLeft(); // Case3
					}
					// case4: w is black, w's right child is black and w's left
					// child is red.
					w.setColor( x.getParent().getColor()); // Case4
					x.getParent().setColor( Commons.BLACK); // Case4
					w.getLeft().setColor( Commons.BLACK); // Case4
					intervalTreeRightRotate( tree, x.getParent()); // Case4
					x = tree.getRoot(); // Case4

				}

			}

		}// end of while

		x.setColor( Commons.BLACK);
	}
	
	
	//later on delete this method
	//debug starts
	public void intervalTreeInsertFixUp(
			long permutationNumberMixedNumber,
			IntervalTree tree, 
			IntervalTreeNode z) {

		IntervalTreeNode y;

		while( z.getParent().getColor() == Commons.RED){
			if( ( z.getParent().getParent().getLeft()) == z.getParent()){
				y = z.getParent().getParent().getRight();
				if( y.getColor() == Commons.RED){
					// Case1 y is red
					z.getParent().setColor( Commons.BLACK); // Case1
					y.setColor( Commons.BLACK); // Case1
					z.getParent().getParent().setColor( Commons.RED); // Case1
					z = z.getParent().getParent(); // Case1
				}else{
					if( z == z.getParent().getRight()){
						// Case2 y is black, z is a right child
						z = z.getParent(); // Case2
						
						//debug starts
						logger.info("For permutationNumberMixedNumber " + permutationNumberMixedNumber + "\t" + " intervalTreeLeftRotate for z " + z.getChromName() + " " + z.getColor() + " "+ z.getLow() + " "+ z.getHigh());
						//debug ends
						
						intervalTreeLeftRotate(permutationNumberMixedNumber, tree, z); // Case2
					}
					// Case3 y is black, z is a left child
					z.getParent().setColor( Commons.BLACK); // Case3
					z.getParent().getParent().setColor( Commons.RED); // Case3
					intervalTreeRightRotate( tree, z.getParent().getParent()); // Case3

				}
			}else{
				
				//Debug starts
				if (z == null || z.getParent() == null || z.getParent().getParent()==null){
					logger.error("permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + "stop here insertFixUp1");
				}
				//debug ends
				
				y = z.getParent().getParent().getLeft();
				
				//debug starts
				if (y==null){
					logger.error("permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + "stop here insertFixUp2");
				}
				//debug ends
				
				if( y.getColor() == Commons.RED){
					z.getParent().setColor( Commons.BLACK); // Case1
					y.setColor( Commons.BLACK); // Case1
					z.getParent().getParent().setColor( Commons.RED); // Case1
					z = z.getParent().getParent(); // Case1
				}else{
					if( z == z.getParent().getLeft()){
						z = z.getParent(); // Case2
						intervalTreeRightRotate( tree, z); // Case2
					}
					z.getParent().setColor( Commons.BLACK); // Case3
					z.getParent().getParent().setColor( Commons.RED); // Case3
					
					//debug starts
					logger.info("For permutationNumberMixedNumber " + permutationNumberMixedNumber + "\t" + " z is " +  z.getChromName() + " " +  z.getColor() + " "+ z.getLow() + " "+  z.getHigh());
					logger.info("For permutationNumberMixedNumber " + permutationNumberMixedNumber + "\t" + " intervalTreeLeftRotate for  z.getParent().getParent() " +  z.getParent().getParent().getChromName() + " " +  z.getParent().getParent().getColor() + " "+ z.getParent().getParent().getLow() + " "+  z.getParent().getParent().getHigh());
					//debug ends
					intervalTreeLeftRotate(permutationNumberMixedNumber, tree, z.getParent().getParent()); // Case3

				}
			}

		}
		tree.getRoot().setColor( Commons.BLACK);
	}
	//debug ends

	//To guarantee red black tree properties
	//We fix up the modified tree by recoloring nodes and performing rotations
	public void intervalTreeInsertFixUp(
			IntervalTree tree, 
			IntervalTreeNode z) {

		IntervalTreeNode y;

		while( z.getParent().getColor() == Commons.RED){
			if( ( z.getParent().getParent().getLeft()) == z.getParent()){
				y = z.getParent().getParent().getRight();
				if( y.getColor() == Commons.RED){
					// Case1 y is red
					z.getParent().setColor( Commons.BLACK); // Case1
					y.setColor( Commons.BLACK); // Case1
					z.getParent().getParent().setColor( Commons.RED); // Case1
					z = z.getParent().getParent(); // Case1
				}else{
					if( z == z.getParent().getRight()){
						// Case2 y is black, z is a right child
						z = z.getParent(); // Case2
						intervalTreeLeftRotate( tree, z); // Case2
					}
					// Case3 y is black, z is a left child
					z.getParent().setColor( Commons.BLACK); // Case3
					z.getParent().getParent().setColor( Commons.RED); // Case3
					intervalTreeRightRotate( tree, z.getParent().getParent()); // Case3

				}
			}else{
				
				//Debug starts
				if (z == null || z.getParent() == null || z.getParent().getParent()==null){
					logger.error("stop here insertFixUp1");
				}
				//debug ends
				
				y = z.getParent().getParent().getLeft();
				
				//debug starts
				if (y==null){
					logger.error("stop here insertFixUp2");
				}
				//debug ends
				
				if( y.getColor() == Commons.RED){
					z.getParent().setColor( Commons.BLACK); // Case1
					y.setColor( Commons.BLACK); // Case1
					z.getParent().getParent().setColor( Commons.RED); // Case1
					z = z.getParent().getParent(); // Case1
				}else{
					if( z == z.getParent().getLeft()){
						z = z.getParent(); // Case2
						intervalTreeRightRotate( tree, z); // Case2
					}
					z.getParent().setColor( Commons.BLACK); // Case3
					z.getParent().getParent().setColor( Commons.RED); // Case3
					intervalTreeLeftRotate( tree, z.getParent().getParent()); // Case3

				}
			}

		}
		tree.getRoot().setColor( Commons.BLACK);
	}

	public void updateMinAttribute( IntervalTreeNode x) {

		int savedMin;

		if( x.getNodeName().isNotSentinel()){

			savedMin = ( ( OtherIntervalTreeNode)x).getMin();
			( ( OtherIntervalTreeNode)x).setMin( min( x));

			if( savedMin != ( ( OtherIntervalTreeNode)x).getMin()){
				updateMinAttribute( x.getParent());
			}
		}

	}

	public void updateMaxAttribute( IntervalTreeNode x) {

		boolean hasChanged = false;
		int savedMax;

		if( x.getNodeName().isNotSentinel()){
			savedMax = x.getMax();
			x.setMax( max( x));

			if( savedMax != x.getMax()){
				hasChanged = true;
			}

		}else{
			return;
		}

		if( hasChanged)
			updateMaxAttribute( x.getParent());

	}

	/*
	 * intervalTreeMinimum returns the minimum elementin the subtree rooted at a
	 * given node x
	 */
	public IntervalTreeNode intervalTreeMinimum( IntervalTreeNode x) {

		while( !NodeName.SENTINEL.equals( x.getLeft().getNodeName())){
			x = x.getLeft();
		}

		return x;
	}

	/*
	 * intervalTreeSuccessor is broken into two cases. If the right subtree of
	 * node x is nonempty, then the successor of x is just the left-most node in
	 * the right subtree.
	 * 
	 * On the other hand, if the right subtree of node x is empty and x has a
	 * successor y, then is the lowest ancestor of x whose left child is an
	 * ancestor of x. To find y, we go up the tree from x until we encounter a
	 * node that is the left child of its parent.
	 */
	public IntervalTreeNode intervalTreeSuccessor( IntervalTreeNode x) {

		IntervalTreeNode y;

		if( !NodeName.SENTINEL.equals( x.getRight().getNodeName())){ 
			
			return intervalTreeMinimum( x.getRight()); 
			
		}

		y = x.getParent();

		while( !NodeName.SENTINEL.equals( y.getNodeName()) && ( x == y.getRight())){
			x = y;
			y = y.getParent();
		}

		return y;
	}

	
	//Later on delete this method  starts
	//4 NOV 2015
	/*
	 * If we delete, thus removing a node, what color was the node that was
	 * removed?
	 * 
	 * RED? Ok, since we won't have changed any black-heights, nor will we have
	 * created two red nodes in a row. Also, cannot cause a violation of
	 * property 2, since if the removed node was red, it could not have been the
	 * root.
	 * 
	 * BLACK? Could cause there to be two reds in a row(violating property 4),
	 * and can also cause a violation of property 5. Could also cause a
	 * violation of property 2, if the removed node was the root and its
	 * child-which becomes the new root -was red.
	 */
	public IntervalTreeNode intervalTreeDelete(long permutationNumberMixedNumber, IntervalTree tree, IntervalTreeNode z) {

		// since z might be changed, do the decrements before
		// Decrement the number of nodes by one
		tree.setNumberofNodes( tree.getNumberofNodes() - 1);

		// Decrease the number of non overlapping bases by size of the deleted node z
		tree.setNumberofNonOverlappingBases( tree.getNumberofNonOverlappingBases() - z.getNumberofBases());

		// Start by doing regular binary search tree
		IntervalTreeNode y;
		IntervalTreeNode x;

		// Determine a node y to splice out.
		// The node y is either the input node z (if z has at most 1 child) or
		// the successor of z if z has two children.
		if( NodeName.SENTINEL.equals( z.getLeft().getNodeName()) || NodeName.SENTINEL.equals( z.getRight().getNodeName())){
			y = z;
			logger.info("For permutationNumberMixedNumber: " +  permutationNumberMixedNumber + " Node to be spliced out is: " + y.getChromName() + " " + y.getColor() + " " + y.getLow() + " " + y.getHigh());
		}else{
			y = intervalTreeSuccessor( z);
			logger.info("For permutationNumberMixedNumber: " +  permutationNumberMixedNumber + " Node to be spliced out is: " + y.getChromName() + " " + y.getColor() + " " + y.getLow() + " " + y.getHigh());
		}

		// x is set to the not null child of the y or
		// x is set to nil[T] if y has no children.
		if( !NodeName.SENTINEL.equals( y.getLeft().getNodeName())){
			x = y.getLeft();
		}else{
			x = y.getRight();
		}

		// The node y is spliced out here by modifying pointers in p[y] and x.
		// Splicing out y is somewhat complicated by the need for proper handling of the boundary conditions,
		// which occur when x = null or when y is the root.

		x.setParent( y.getParent());

		if( ( x instanceof OtherIntervalTreeNode) && ( y instanceof OtherIntervalTreeNode)){
			( ( OtherIntervalTreeNode)x).setHeight( ( ( OtherIntervalTreeNode)( y.getParent())).getHeight() + 1);
		}

		if( NodeName.SENTINEL.equals( y.getParent().getNodeName())){
			tree.setRoot( x);
		}else{
			if( y == ( y.getParent()).getLeft()){
				( y.getParent()).setLeft( x);
			}else{
				( y.getParent()).setRight( x);
			}
		}

		// set max value of parent of x
		updateMaxAttribute( x.getParent());

		// set min value of parent of x
		if( x instanceof OtherIntervalTreeNode){
			updateMinAttribute( x.getParent());
		}

		// If the successor of z was the node spliced out,
		// the contents of z are moved from y to z, overwriting the previous contents.
		// data fields of node y is copied into node z.
		// node y takes place of node z.
		if( y != z){
			// copy y's satellite data into z
			z.setChromName( y.getChromName());
			z.setLow( y.getLow());
			z.setHigh( y.getHigh());
			z.setNumberofBases( y.getNumberofBases());
			
			//2 NOV 2015 starts
			if( ( z instanceof TforHistoneIntervalTreeNodeWithNumbers) && ( y instanceof TforHistoneIntervalTreeNodeWithNumbers)){
				( ( TforHistoneIntervalTreeNodeWithNumbers)z).setTforHistoneNumber( ( (TforHistoneIntervalTreeNodeWithNumbers)y).getTforHistoneNumber());
				( ( TforHistoneIntervalTreeNodeWithNumbers)z).setCellLineNumber( ( (TforHistoneIntervalTreeNodeWithNumbers)y).getCellLineNumber());
				( ( TforHistoneIntervalTreeNodeWithNumbers)z).setFileNumber( ( ( TforHistoneIntervalTreeNodeWithNumbers)y).getFileNumber());
			}
			else if ((z instanceof DnaseIntervalTreeNodeWithNumbers) && (y instanceof DnaseIntervalTreeNodeWithNumbers)){
				( ( DnaseIntervalTreeNodeWithNumbers)z).setCellLineNumber( ( ( DnaseIntervalTreeNodeWithNumbers)y).getCellLineNumber());
				( ( DnaseIntervalTreeNodeWithNumbers)z).setFileNumber( ( ( DnaseIntervalTreeNodeWithNumbers)y).getFileNumber());
			}
			else if( ( z instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers) && ( y instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers)){
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setRefSeqGeneNumber( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getRefSeqGeneNumber());
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setGeneEntrezId( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getGeneEntrezId());
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setGeneHugoSymbolNumber( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getGeneHugoSymbolNumber());
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setIntervalName( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getIntervalName());
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setIntervalNumber( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getIntervalNumber());
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setStrand( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getStrand());
			}
			//2 NOV 2015 ends
			

			else if( ( z instanceof DnaseIntervalTreeNode) && ( y instanceof DnaseIntervalTreeNode)){
				( ( DnaseIntervalTreeNode)z).setCellLineName( ( ( DnaseIntervalTreeNode)y).getCellLineName());
				( ( DnaseIntervalTreeNode)z).setFileName( ( ( DnaseIntervalTreeNode)y).getFileName());
			}

			else if( ( z instanceof TforHistoneIntervalTreeNode) && ( y instanceof TforHistoneIntervalTreeNode)){
				( ( TforHistoneIntervalTreeNode)z).setTfbsorHistoneName( ( ( TforHistoneIntervalTreeNode)y).getTfbsorHistoneName());
				( ( TforHistoneIntervalTreeNode)z).setCellLineName( ( ( TforHistoneIntervalTreeNode)y).getCellLineName());
				( ( TforHistoneIntervalTreeNode)z).setFileName( ( ( TforHistoneIntervalTreeNode)y).getFileName());
			}

			else if( ( z instanceof UcscRefSeqGeneIntervalTreeNode) && ( y instanceof UcscRefSeqGeneIntervalTreeNode)){
				( ( UcscRefSeqGeneIntervalTreeNode)z).setRefSeqGeneName( ( ( UcscRefSeqGeneIntervalTreeNode)y).getRefSeqGeneName());
				( ( UcscRefSeqGeneIntervalTreeNode)z).setGeneEntrezId( ( ( UcscRefSeqGeneIntervalTreeNode)y).getGeneEntrezId());
				( ( UcscRefSeqGeneIntervalTreeNode)z).setGeneHugoSymbol( ( ( UcscRefSeqGeneIntervalTreeNode)y).getGeneHugoSymbol());
				( ( UcscRefSeqGeneIntervalTreeNode)z).setIntervalName( ( ( UcscRefSeqGeneIntervalTreeNode)y).getIntervalName());
				( ( UcscRefSeqGeneIntervalTreeNode)z).setStrand( ( ( UcscRefSeqGeneIntervalTreeNode)y).getStrand());
			}
			
			//28 OCTOBER 2015 starts
			else if ((z instanceof DataDrivenExperimentIntervalTreeNode) && (y instanceof DataDrivenExperimentIntervalTreeNode)){
				((DataDrivenExperimentIntervalTreeNode)z).setGeneSymbol(((DataDrivenExperimentIntervalTreeNode) y).getGeneSymbol());
				((DataDrivenExperimentIntervalTreeNode)z).setTpm(((DataDrivenExperimentIntervalTreeNode) y).getTpm());
			}
			//28 OCTOBER 2015 ends
			

			// Burcak commented only the data has been changed
			// Left, Right and Parent does not change.
			// height does not change
			// z.setLeft(y.getLeft());
			// z.setRight(y.getRight());
			// z.setParent(y.getParent());
			// z.setColor(y.getColor());

			// set the max of node z
			updateMaxAttribute( z);

			if( z instanceof OtherIntervalTreeNode){
				// set the min of node z
				updateMinAttribute( z);
			}

		}

		// If y is black, we could have violations of red-black properties:
		// 1. Every node is either black or red. Ok.
		// 2. The root is black. If y is the root and x is red, then the root
		// has become red.
		// 3. Every leaf is black. Ok.
		// 4. If a node is red, then both of its children are black. (Hence no
		// two reds in a row on a simple path from the root to a leaf.)
		// Violation if p[y] and x are both red.
		// 5. For each node, all paths from the node to descendant leaves
		// contain the same number of black nodes. Any path containing y now has
		// 1 fewer black node.
		// 5. 5.1 correct by giving x an "extra black".
		// 5. 5.2 Add 1 to count of black nodes on paths containing x.
		// 5. 5.3 Now property 5 is Ok, but property 1 is not.
		// 5. 5.4 x is either doubly black (if color[x] = BLACK) or red&black
		// (if color[x] = RED) .
		// 5. 5.4 The attribute color [x] is still either RED or BLACK. No new
		// values for color attribute.
		// 5. 5.4 In other words, the extra blackness on a node is by virtue of
		// x pointing to the node.
		if( y.getColor() == Commons.BLACK){
			intervalTreeDeleteFixUp( tree, x);
		}

		// The node y is returned so that calling procedure can recyle it via  the free list.
		return y;

	}
	//intervalTreeDelete Ends
	//4 NOV 2015
	//Later on delete this method ends
	
	
	
	/*
	 * If we delete, thus removing a node, what color was the node that was
	 * removed?
	 * 
	 * RED? Ok, since we won't have changed any black-heights, nor will we have
	 * created two red nodes in a row. Also, cannot cause a violation of
	 * property 2, since if the removed node was red, it could not have been the
	 * root.
	 * 
	 * BLACK? Could cause there to be two reds in a row(violating property 4),
	 * and can also cause a violation of property 5. Could also cause a
	 * violation of property 2, if the removed node was the root and its
	 * child-which becomes the new root -was red.
	 */
	public IntervalTreeNode intervalTreeDelete( IntervalTree tree, IntervalTreeNode z) {

		// since z might be changed, do the decrements before
		// Decrement the number of nodes by one
		tree.setNumberofNodes( tree.getNumberofNodes() - 1);

		// Decrease the number of non overlapping bases by size of the deleted node z
		tree.setNumberofNonOverlappingBases( tree.getNumberofNonOverlappingBases() - z.getNumberofBases());

		// Start by doing regular binary search tree
		IntervalTreeNode y;
		IntervalTreeNode x;

		// Determine a node y to splice out.
		// The node y is either the input node z (if z has at most 1 child) or
		// the successor of z if z has two children.
		if( NodeName.SENTINEL.equals( z.getLeft().getNodeName()) || NodeName.SENTINEL.equals( z.getRight().getNodeName())){
			y = z;
		}else{
			y = intervalTreeSuccessor( z);
		}

		// x is set to the not null child of the y or
		// x is set to nil[T] if y has no children.
		if( !NodeName.SENTINEL.equals( y.getLeft().getNodeName())){
			x = y.getLeft();
		}else{
			x = y.getRight();
		}

		// The node y is spliced out here by modifying pointers in p[y] and x.
		// Splicing out y is somewhat complicated by the need for proper handling of the boundary conditions,
		// which occur when x = null or when y is the root.

		x.setParent( y.getParent());

		if( ( x instanceof OtherIntervalTreeNode) && ( y instanceof OtherIntervalTreeNode)){
			( ( OtherIntervalTreeNode)x).setHeight( ( ( OtherIntervalTreeNode)( y.getParent())).getHeight() + 1);
		}

		if( NodeName.SENTINEL.equals( y.getParent().getNodeName())){
			tree.setRoot( x);
		}else{
			if( y == ( y.getParent()).getLeft()){
				( y.getParent()).setLeft( x);
			}else{
				( y.getParent()).setRight( x);
			}
		}

		// set max value of parent of x
		updateMaxAttribute( x.getParent());

		// set min value of parent of x
		if( x instanceof OtherIntervalTreeNode){
			updateMinAttribute( x.getParent());
		}

		// If the successor of z was the node spliced out,
		// the contents of z are moved from y to z, overwriting the previous contents.
		// data fields of node y is copied into node z.
		// node y takes place of node z.
		if( y != z){
			// copy y's satellite data into z
			z.setChromName( y.getChromName());
			z.setLow( y.getLow());
			z.setHigh( y.getHigh());
			z.setNumberofBases( y.getNumberofBases());
			
			//2 NOV 2015 starts
			if( ( z instanceof TforHistoneIntervalTreeNodeWithNumbers) && ( y instanceof TforHistoneIntervalTreeNodeWithNumbers)){
				( ( TforHistoneIntervalTreeNodeWithNumbers)z).setTforHistoneNumber( ( (TforHistoneIntervalTreeNodeWithNumbers)y).getTforHistoneNumber());
				( ( TforHistoneIntervalTreeNodeWithNumbers)z).setCellLineNumber( ( (TforHistoneIntervalTreeNodeWithNumbers)y).getCellLineNumber());
				( ( TforHistoneIntervalTreeNodeWithNumbers)z).setFileNumber( ( ( TforHistoneIntervalTreeNodeWithNumbers)y).getFileNumber());
			}
			else if ((z instanceof DnaseIntervalTreeNodeWithNumbers) && (y instanceof DnaseIntervalTreeNodeWithNumbers)){
				( ( DnaseIntervalTreeNodeWithNumbers)z).setCellLineNumber( ( ( DnaseIntervalTreeNodeWithNumbers)y).getCellLineNumber());
				( ( DnaseIntervalTreeNodeWithNumbers)z).setFileNumber( ( ( DnaseIntervalTreeNodeWithNumbers)y).getFileNumber());
			}
			else if( ( z instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers) && ( y instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers)){
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setRefSeqGeneNumber( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getRefSeqGeneNumber());
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setGeneEntrezId( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getGeneEntrezId());
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setGeneHugoSymbolNumber( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getGeneHugoSymbolNumber());
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setIntervalName( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getIntervalName());
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setIntervalNumber( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getIntervalNumber());
				( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)z).setStrand( ( ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)y).getStrand());
			}
			//2 NOV 2015 ends
			

			else if( ( z instanceof DnaseIntervalTreeNode) && ( y instanceof DnaseIntervalTreeNode)){
				( ( DnaseIntervalTreeNode)z).setCellLineName( ( ( DnaseIntervalTreeNode)y).getCellLineName());
				( ( DnaseIntervalTreeNode)z).setFileName( ( ( DnaseIntervalTreeNode)y).getFileName());
			}

			else if( ( z instanceof TforHistoneIntervalTreeNode) && ( y instanceof TforHistoneIntervalTreeNode)){
				( ( TforHistoneIntervalTreeNode)z).setTfbsorHistoneName( ( ( TforHistoneIntervalTreeNode)y).getTfbsorHistoneName());
				( ( TforHistoneIntervalTreeNode)z).setCellLineName( ( ( TforHistoneIntervalTreeNode)y).getCellLineName());
				( ( TforHistoneIntervalTreeNode)z).setFileName( ( ( TforHistoneIntervalTreeNode)y).getFileName());
			}

			else if( ( z instanceof UcscRefSeqGeneIntervalTreeNode) && ( y instanceof UcscRefSeqGeneIntervalTreeNode)){
				( ( UcscRefSeqGeneIntervalTreeNode)z).setRefSeqGeneName( ( ( UcscRefSeqGeneIntervalTreeNode)y).getRefSeqGeneName());
				( ( UcscRefSeqGeneIntervalTreeNode)z).setGeneEntrezId( ( ( UcscRefSeqGeneIntervalTreeNode)y).getGeneEntrezId());
				( ( UcscRefSeqGeneIntervalTreeNode)z).setGeneHugoSymbol( ( ( UcscRefSeqGeneIntervalTreeNode)y).getGeneHugoSymbol());
				( ( UcscRefSeqGeneIntervalTreeNode)z).setIntervalName( ( ( UcscRefSeqGeneIntervalTreeNode)y).getIntervalName());
				( ( UcscRefSeqGeneIntervalTreeNode)z).setStrand( ( ( UcscRefSeqGeneIntervalTreeNode)y).getStrand());
			}
			
			//28 OCTOBER 2015 starts
			else if ((z instanceof DataDrivenExperimentIntervalTreeNode) && (y instanceof DataDrivenExperimentIntervalTreeNode)){
				((DataDrivenExperimentIntervalTreeNode)z).setGeneSymbol(((DataDrivenExperimentIntervalTreeNode) y).getGeneSymbol());
				((DataDrivenExperimentIntervalTreeNode)z).setTpm(((DataDrivenExperimentIntervalTreeNode) y).getTpm());
			}
			//28 OCTOBER 2015 ends
			

			// Burcak commented only the data has been changed
			// Left, Right and Parent does not change.
			// height does not change
			// z.setLeft(y.getLeft());
			// z.setRight(y.getRight());
			// z.setParent(y.getParent());
			// z.setColor(y.getColor());

			// set the max of node z
			updateMaxAttribute( z);

			if( z instanceof OtherIntervalTreeNode){
				// set the min of node z
				updateMinAttribute( z);
			}

		}

		// If y is black, we could have violations of red-black properties:
		// 1. Every node is either black or red. Ok.
		// 2. The root is black. If y is the root and x is red, then the root
		// has become red.
		// 3. Every leaf is black. Ok.
		// 4. If a node is red, then both of its children are black. (Hence no
		// two reds in a row on a simple path from the root to a leaf.)
		// Violation if p[y] and x are both red.
		// 5. For each node, all paths from the node to descendant leaves
		// contain the same number of black nodes. Any path containing y now has
		// 1 fewer black node.
		// 5. 5.1 correct by giving x an "extra black".
		// 5. 5.2 Add 1 to count of black nodes on paths containing x.
		// 5. 5.3 Now property 5 is Ok, but property 1 is not.
		// 5. 5.4 x is either doubly black (if color[x] = BLACK) or red&black
		// (if color[x] = RED) .
		// 5. 5.4 The attribute color [x] is still either RED or BLACK. No new
		// values for color attribute.
		// 5. 5.4 In other words, the extra blackness on a node is by virtue of
		// x pointing to the node.
		if( y.getColor() == Commons.BLACK){
			intervalTreeDeleteFixUp( tree, x);
		}

		// The node y is returned so that calling procedure can recyle it via  the free list.
		return y;

	}
	//intervalTreeDelete Ends
	
	//later on delete this method
	//debug version starts
	public void intervalTreeInsert(long permutationNumberMixedNumber, IntervalTree tree, IntervalTreeNode z) {

		// Increment the number of nodes by one
		tree.setNumberofNodes( tree.getNumberofNodes() + 1);

		// Increase the number of non overlapping bases by size of the inserted node z
		tree.setNumberofNonOverlappingBases( tree.getNumberofNonOverlappingBases() + z.getNumberofBases());

		// Set y to SENTINEL node
		IntervalTreeNode y = new IntervalTreeNode();
		// Set x to the root
		IntervalTreeNode x = tree.getRoot();

		// This while sets the parent for the new inserted node z
		while( !( NodeName.SENTINEL.equals( x.getNodeName()))){
			y = x;
			if( z.getLow() < x.getLow())
				x = x.getLeft();
			else
				x = x.getRight();
		}

		z.setParent( y);

		// This part sets whether the new inserted node is the left or right child of parent
		if( NodeName.SENTINEL.equals( y.getNodeName())){// enters for the first
			// insert
			tree.setRoot( z);
		}else{
			if( z.getLow() < y.getLow()){
				y.setLeft( z);
			}else{
				y.setRight( z);
			}
		}

		// sets the left right color attributes of the new inserted node
		z.setLeft( new IntervalTreeNode());
		z.setRight( new IntervalTreeNode());
		z.setColor( Commons.RED);
		z.setMax( z.getHigh());

		if( z instanceof OtherIntervalTreeNode){
			( ( OtherIntervalTreeNode)z).setMin( z.getLow());
			( ( OtherIntervalTreeNode)z).setHeight( ( ( OtherIntervalTreeNode)( z.getParent())).getHeight() + 1);

		}

		updateMaxAttribute( z.getParent());

		if( z instanceof OtherIntervalTreeNode){
			updateMinAttribute( z.getParent());
		}

		//debug starts
		logger.info("permutationNumberMixedNumber: " + permutationNumberMixedNumber + " InsertFixUp for z " + z.getChromName() + " " +z.getColor() + " " +z.getLow() + " " +z.getHigh() );
		//debug ends
		intervalTreeInsertFixUp(permutationNumberMixedNumber,tree, z);

	}	
	//debug version ends

	public void intervalTreeInsert( IntervalTree tree, IntervalTreeNode z) {

		// Increment the number of nodes by one
		tree.setNumberofNodes( tree.getNumberofNodes() + 1);

		// Increase the number of non overlapping bases by size of the inserted node z
		tree.setNumberofNonOverlappingBases( tree.getNumberofNonOverlappingBases() + z.getNumberofBases());

		// Set y to SENTINEL node
		IntervalTreeNode y = new IntervalTreeNode();
		// Set x to the root
		IntervalTreeNode x = tree.getRoot();

		// This while sets the parent for the new inserted node z
		while( !( NodeName.SENTINEL.equals( x.getNodeName()))){
			y = x;
			if( z.getLow() < x.getLow())
				x = x.getLeft();
			else
				x = x.getRight();
		}

		z.setParent( y);

		// This part sets whether the new inserted node is the left or right child of parent
		if( NodeName.SENTINEL.equals( y.getNodeName())){// enters for the first
			// insert
			tree.setRoot( z);
		}else{
			if( z.getLow() < y.getLow()){
				y.setLeft( z);
			}else{
				y.setRight( z);
			}
		}

		// sets the left right color attributes of the new inserted node
		z.setLeft( new IntervalTreeNode());
		z.setRight( new IntervalTreeNode());
		z.setColor( Commons.RED);
		z.setMax( z.getHigh());

		if( z instanceof OtherIntervalTreeNode){
			( ( OtherIntervalTreeNode)z).setMin( z.getLow());
			( ( OtherIntervalTreeNode)z).setHeight( ( ( OtherIntervalTreeNode)( z.getParent())).getHeight() + 1);

		}

		updateMaxAttribute( z.getParent());

		if( z instanceof OtherIntervalTreeNode){
			updateMinAttribute( z.getParent());
		}

		
		intervalTreeInsertFixUp(tree, z);

	}
	
	//for debug purposes starts
	public void intervalTreeInfixTraversal(long permutationNumberMixedNumber,IntervalTreeNode node) {

		if( node.getLeft()!= null && node.getLeft().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal(permutationNumberMixedNumber,node.getLeft());

		if( node.getNodeName().isNotSentinel()){
			//GlanetRunner.appendLog( node.getLow() + "\t" + node.getHigh() + "\t" + node.getMax() + "\t" + node.getColor());
			logger.info("For permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + 
					node.getChromName() + "\t" + 
					node.getColor() + "\t" + 
					node.getLow() + "\t" + 
					node.getHigh());
		}

		if( node.getRight()!= null && node.getRight().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal(permutationNumberMixedNumber,node.getRight());

	}
	//for debug purposes ends

	public void intervalTreeInfixTraversal(IntervalTreeNode node) {

		if( node.getLeft()!= null && node.getLeft().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal(node.getLeft());

		if( node.getNodeName().isNotSentinel()){
			//GlanetRunner.appendLog( node.getLow() + "\t" + node.getHigh() + "\t" + node.getMax() + "\t" + node.getColor());
			logger.info(node.getChromName() + "\t" + node.getColor() + "\t" + node.getLow() + "\t" + node.getHigh());
		}

		if( node.getRight()!= null && node.getRight().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal(node.getRight());

	}

	public void intervalTreeInfixTraversal( IntervalTreeNode node, BufferedWriter bufferedWriter) {

		if( node.getLeft().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal( node.getLeft(), bufferedWriter);

		try{

			if( node.getNodeName().isNotSentinel()){
				bufferedWriter.write( node.getLow() + "\t" + node.getHigh() + "\t" + node.getMax() + System.getProperty( "line.separator"));
				bufferedWriter.flush();
			}

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		if( node.getRight().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal( node.getRight(), bufferedWriter);

	}

	public void intervalTreeInfixTraversal( IntervalTreeNode node, BufferedWriter bufferedWriter, String type,
			GivenInputDataSNPSorIntervals givenInputDataSNPSorIntervals) {

		DnaseIntervalTreeNode castedNodeDnase = null;
		TforHistoneIntervalTreeNode castedNodeTforHistone = null;
		UcscRefSeqGeneIntervalTreeNode castedNodeUcscRefSeqGene = null;

		/*************************************************/
		int length = node.getNumberofBases();

		if( length > 1 && givenInputDataSNPSorIntervals.getGivenInputDataSNPs()){
			givenInputDataSNPSorIntervals.setGivenInputDataSNPs( false);
		}
		/*************************************************/

		if( node.getLeft().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal( node.getLeft(), bufferedWriter, type, givenInputDataSNPSorIntervals);

		try{
			if( node.getNodeName().isNotSentinel()){

				if( Commons.DNASE.equals( type)){
					castedNodeDnase = ( DnaseIntervalTreeNode)node;
					bufferedWriter.write( castedNodeDnase.getChromName().convertEnumtoString() + "\t" + castedNodeDnase.getLow() + "\t" + castedNodeDnase.getHigh() + "\t" + castedNodeDnase.getCellLineName() + "\t" + castedNodeDnase.getFileName() + System.getProperty( "line.separator"));
				}else if( Commons.TF.equals( type)){
					castedNodeTforHistone = ( TforHistoneIntervalTreeNode)node;
					bufferedWriter.write( castedNodeTforHistone.getChromName().convertEnumtoString() + "\t" + castedNodeTforHistone.getLow() + "\t" + castedNodeTforHistone.getHigh() + "\t" + castedNodeTforHistone.getTfbsorHistoneName() + "\t" + castedNodeTforHistone.getCellLineName() + "\t" + castedNodeTforHistone.getFileName() + System.getProperty( "line.separator"));
				}else if( Commons.HISTONE.equals( type)){
					castedNodeTforHistone = ( TforHistoneIntervalTreeNode)node;
					bufferedWriter.write( castedNodeTforHistone.getChromName().convertEnumtoString() + "\t" + castedNodeTforHistone.getLow() + "\t" + castedNodeTforHistone.getHigh() + "\t" + castedNodeTforHistone.getTfbsorHistoneName() + "\t" + castedNodeTforHistone.getCellLineName() + "\t" + castedNodeTforHistone.getFileName() + System.getProperty( "line.separator"));
				}else if( Commons.HG19_REFSEQ_GENE.equals( type)){
					castedNodeUcscRefSeqGene = ( UcscRefSeqGeneIntervalTreeNode)node;
					bufferedWriter.write( castedNodeUcscRefSeqGene.getChromName().convertEnumtoString() + "\t" + castedNodeUcscRefSeqGene.getLow() + "\t" + castedNodeUcscRefSeqGene.getHigh() + "\t" + castedNodeUcscRefSeqGene.getRefSeqGeneName() + "\t" + castedNodeUcscRefSeqGene.getGeneEntrezId() + "\t" + castedNodeUcscRefSeqGene.getIntervalName().convertEnumtoString() + "\t" + castedNodeUcscRefSeqGene.getIntervalNumber() + "\t" + castedNodeUcscRefSeqGene.getStrand() + "\t" + castedNodeUcscRefSeqGene.getGeneHugoSymbol() + System.getProperty( "line.separator"));
					// bufferedWriter.write(refSeqGeneIntervalList.get(j).getChromName()+
					// "\t"
					// +refSeqGeneIntervalList.get(j).getIntervalStart()+"\t"+
					// refSeqGeneIntervalList.get(j).getIntervalEnd()+"\t"
					// +refSeqGeneIntervalList.get(j).getRefSeqGeneName()+ "\t"
					// + refSeqGeneIntervalList.get(j).getGeneId()+ "\t"
					// +refSeqGeneIntervalList.get(j).getIntervalName()+ "\t" +
					// refSeqGeneIntervalList.get(j).getStrand() + "\t" +
					// refSeqGeneIntervalList.get(j).getAlternateGeneName()+
					// System.getProperty("line.separator"));
				}else if( Commons.PROCESS_INPUT_DATA_REMOVE_OVERLAPS.equals( type)){
					bufferedWriter.write( node.getChromName().convertEnumtoString() + "\t" + node.getLow() + "\t" + node.getHigh() + System.getProperty( "line.separator"));
				}
				bufferedWriter.flush();
			}

		}catch( IOException e){

			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}

		if( node.getRight().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal( node.getRight(), bufferedWriter, type, givenInputDataSNPSorIntervals);

	}

	// Return number of overlapping base pairs
	public static int findNumberofOverlapingBases( int low_x, int high_x, int low_y, int high_y) {

		if( ( low_x <= high_y) && ( low_y <= high_x)){
			return Math.min( high_x, high_y) - Math.max( low_x, low_y) + 1;
		}else
			return 0;
	}

	public static boolean overlaps( Interval x, Interval y) {

		if( ( x.getLow() <= y.getHigh()) && ( y.getLow() <= x.getHigh()))
			return true;
		else
			return false;
	}

	public static boolean overlaps( int low_x, int high_x, int low_y, int high_y) {

		if( ( low_x <= high_y) && ( low_y <= high_x))
			return true;
		else
			return false;
	}

	// overlap definition: number of overlapping bases necessary for overlap
	public static boolean overlaps( int low_x, int high_x, int low_y, int high_y, int numberofOverlappingBases) {

		if( ( low_x <= high_y) && ( low_y <= high_x)){

			if( ( Math.min( high_x, high_y) - Math.max( low_x, low_y) + 1) >= numberofOverlappingBases){
				return true;
			}else
				return false;
		}else
			return false;
	}

	// For debug purposes
	public static void findGivenNode( IntervalTreeNode node, int low, int high, List<IntervalTreeNode> foundNodes) {

		// exact node
		if( node.getLow() == low && node.getHigh() == high){
			// Found
			foundNodes.add( node);
		}

		// partial overlap possibility
		if( node.getLeft().getNodeName().isNotSentinel() && ( ( OtherIntervalTreeNode)( node.getLeft())).getMin() <= high && low <= node.getLeft().getMax()){
			findGivenNode( node.getLeft(), low, high, foundNodes);
		}

		// partial overlap possibility
		if( node.getRight().getNodeName().isNotSentinel() && ( ( OtherIntervalTreeNode)( node.getRight())).getMin() <= high && low <= node.getRight().getMax()){
			findGivenNode( node.getRight(), low, high, foundNodes);
		}

	}

	// finds the left most node in the previous non empty node list
	public static IntervalTreeNode findLeftMostNodefromPreviousQuery(
			List<IntervalTreeNode> previousNonEmptyOverlappingNodeList) {

		IntervalTreeNode leftMostNode = previousNonEmptyOverlappingNodeList.get( 0);

		for( IntervalTreeNode node : previousNonEmptyOverlappingNodeList){

			if( node.getLow() < leftMostNode.getLow()){
				leftMostNode = node;
			}
		}

		return leftMostNode;

	}

	// Go up in the interval tree for the new query
	public static IntervalTreeNode findMostGeneralSearchStaringNodeforNewQuery( Interval interval,
			IntervalTreeNode leftMostNode) {

		// For OCD GWAS taking right most node did not work
		// Test for POSITIVE CONTROL DATA

		IntervalTreeNode parent = leftMostNode;
		IntervalTreeNode previousParent;

		previousParent = parent;
		parent = parent.getParent();

		// Exit the loop when parent.getLow()>interval.getHigh()
		while( ( parent.getNodeName().isNotSentinel()) && ( parent.getLow() <= interval.getHigh())){
			previousParent = parent;
			parent = parent.getParent();

		}

		return previousParent;
	}

	// Go up in the interval tree for the new query
	public static IntervalTreeNode findMostGeneralSearchStaringNodeforNewQuery( Interval interval,
			IntervalTreeNode leftMostNode, IntervalTreeNode rightMostNode) {

		// For OCD GWAS taking right most node did not work
		// Test for POSITIVE CONTROL DATA

		IntervalTreeNode parent = leftMostNode;
		IntervalTreeNode previousParent;

		previousParent = parent;
		parent = parent.getParent();

		// Exit the loop when parent.getLow()>interval.getHigh()
		while( ( parent.getNodeName().isNotSentinel()) && ( parent.getLow() <= interval.getHigh())){
			previousParent = parent;
			parent = parent.getParent();

		}

		return previousParent;
	}

	// Go down in the interval tree for the new query
	public static IntervalTreeNode findMostSpecificSearchStaringNodeforNewQuery( Interval interval,
			IntervalTreeNode node) {

		// if there is possibility of overlap between interval and node's
		// children
		// or overlaps with the node
		if( ( ( node.getLeft().getNodeName().isNotSentinel()) && ( node.getRight().getNodeName().isNotSentinel()) && ( ( ( ( OtherIntervalTreeNode)( node.getLeft())).getMin() <= interval.getHigh()) && ( interval.getLow() <= node.getLeft().getMax())) && ( ( ( ( OtherIntervalTreeNode)( node.getRight())).getMin() <= interval.getHigh()) && ( interval.getLow() <= node.getRight().getMax()))) || ( node.getLow() <= interval.getHigh() && interval.getLow() <= node.getHigh())){
			return node;
		}else if( node.getLeft().getNodeName().isNotSentinel() && ( ( ( ( OtherIntervalTreeNode)( node.getLeft())).getMin() <= interval.getHigh()) && ( interval.getLow() <= node.getLeft().getMax()))){
			return findMostSpecificSearchStaringNodeforNewQuery( interval, node.getLeft());
		}else if( node.getRight().getNodeName().isNotSentinel() && ( ( ( ( OtherIntervalTreeNode)( node.getRight())).getMin() <= interval.getHigh()) && ( interval.getLow() <= node.getRight().getMax()))){
			return findMostSpecificSearchStaringNodeforNewQuery( interval, node.getRight());
		}
		// no way out
		// does not overlap with the node and its children
		else
			return new IntervalTreeNode();

	}

	public static void writeRouteFromRoottoThisNode( IntervalTreeNode node) {

		IntervalTreeNode parent;

		if( node.getNodeName().isNotSentinel()){
			parent = node.getParent();

			if( parent.getNodeName().isNotSentinel()){
				if( parent.getLeft() == node){
					writeRouteFromRoottoThisNode( node.getParent());
					GlanetRunner.appendLog( "Left");
				}else if( parent.getRight() == node){
					writeRouteFromRoottoThisNode( node.getParent());
					GlanetRunner.appendLog( "Right");
				}
			}
		}

	}

	public static void printRouteFromRoottoThisNode( IntervalTreeNode root, int low, int high,
			List<IntervalTreeNode> foundNodes) {

		OtherIntervalTreeNode castedNode = null;

		if( root.getNodeName().isNotSentinel()){
			findGivenNode( root, low, high, foundNodes);
		}

		for( IntervalTreeNode foundNode : foundNodes){

			if( foundNode instanceof OtherIntervalTreeNode){
				castedNode = ( OtherIntervalTreeNode)foundNode;
			}

			GlanetRunner.appendLog( "Height: " + castedNode.height);
			writeRouteFromRoottoThisNode( foundNode);
		}

	}

	public List<IntervalDataDrivenExperiment> findAllOverlappingIntervalsForExclusion( IntervalTreeNode node,
			IntervalDataDrivenExperiment interval, int overlapDefinition) {

		List<IntervalDataDrivenExperiment> overlappingIntervalList = new ArrayList<IntervalDataDrivenExperiment>();

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){
			overlappingIntervalList.add( new IntervalDataDrivenExperiment( Math.max( node.getLow(), interval.getLow()),
					Math.min( node.getHigh(), interval.getHigh())));
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			overlappingIntervalList.addAll( findAllOverlappingIntervalsForExclusion( node.getLeft(), interval,
					overlapDefinition));
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			overlappingIntervalList.addAll( findAllOverlappingIntervalsForExclusion( node.getRight(), interval,
					overlapDefinition));
		}

		return overlappingIntervalList;

	}

	// There can be gaps in the mapabilityIntervalTree
	public int findAllOverlappingMapabilityIntervals( IntervalTreeNode node, InputLineMinimal interval) {

		int numberofOverlappingBases = 0;

		int mapability = 0;
		int mapabilityLeft = 0;
		int mapabilityRight = 0;

		MapabilityIntervalTreeNode castedNode = null;

		if( node instanceof MapabilityIntervalTreeNode){
			castedNode = ( MapabilityIntervalTreeNode)node;
		}

		numberofOverlappingBases = findNumberofOverlapingBases( node.getLow(), node.getHigh(), interval.getLow(),
				interval.getHigh());

		if( numberofOverlappingBases > 0){

			mapability = ( numberofOverlappingBases * castedNode.getMapability());

		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			mapabilityLeft = findAllOverlappingMapabilityIntervals( node.getLeft(), interval);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			mapabilityRight = findAllOverlappingMapabilityIntervals( node.getRight(), interval);

		}

		return mapability + mapabilityLeft + mapabilityRight;
	}

	// Find the number of hits while finding overlaps in GCIsochoreIntervalTree
	public List<GCIsochoreIntervalTreeHitNode> findAllOverlappingGCIsochoreIntervals( IntervalTreeNode node,
			InputLineMinimal interval) {

		List<GCIsochoreIntervalTreeHitNode> hits = new ArrayList<GCIsochoreIntervalTreeHitNode>();

		GCIsochoreIntervalTreeNode castedNode = null;

		GCIsochoreIntervalTreeHitNode hit = null;
		int numberofOverlappingBases = 0;

		if( node instanceof GCIsochoreIntervalTreeNode){
			castedNode = ( GCIsochoreIntervalTreeNode)node;
		}

		numberofOverlappingBases = findNumberofOverlapingBases( castedNode.getLow(), castedNode.getHigh(),
				interval.getLow(), interval.getHigh());

		if( numberofOverlappingBases > 0){

			hit = new GCIsochoreIntervalTreeHitNode( castedNode.getLow(), castedNode.getHigh(),
					castedNode.getNumberofGCs(), castedNode.getIsochoreFamily(), numberofOverlappingBases);

			hits.add( hit);
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			hits.addAll( findAllOverlappingGCIsochoreIntervals( node.getLeft(), interval));
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			hits.addAll( findAllOverlappingGCIsochoreIntervals( node.getRight(), interval));

		}

		return hits;

	}

	// Return gc and all overlapping IsochoreIntervalTree hits starts
	public void findAllOverlappingGCIsochoreIntervals( IntervalTreeNode node, InputLineMinimal interval,
			GCIsochoreIntervalTreeFindAllOverlapsResult result) {

		GCIsochoreIntervalTreeNode castedNode = null;

		int numberofOverlappingBases = 0;
		float numberofGCs = 0f;

		if( node instanceof GCIsochoreIntervalTreeNode){
			castedNode = ( GCIsochoreIntervalTreeNode)node;
		}

		numberofOverlappingBases = findNumberofOverlapingBases( castedNode.getLow(), castedNode.getHigh(),
				interval.getLow(), interval.getHigh());

		if( numberofOverlappingBases > 0){

			// Find hit
			GCIsochoreIntervalTreeHitNode hit = new GCIsochoreIntervalTreeHitNode( castedNode.getLow(),
					castedNode.getHigh(), castedNode.getNumberofGCs(), castedNode.getIsochoreFamily(),
					numberofOverlappingBases);

			// Add hit
			result.getHits().add( hit);

			// Find numberofGCs
			numberofGCs = ( numberofOverlappingBases * castedNode.getNumberofGCs() * 1.0f) / ( castedNode.getHigh() - castedNode.getLow() + 1);

			// Accumulate gcContent
			result.setNumberofGCs( result.getNumberofGCs() + numberofGCs);

		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingGCIsochoreIntervals( node.getLeft(), interval, result);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingGCIsochoreIntervals( node.getRight(), interval, result);

		}

	}

	// Return gc and all overlapping IsochoreIntervalTree hits ends

	// There can be gaps in the gcIntervalTree
	public float findAllOverlappingGCIntervals( IntervalTreeNode node, InputLineMinimal interval,
			CalculateGC calculateGC) {

		int numberofOverlappingBases = 0;

		float gcContent = 0f;
		float gcContentLeft = 0f;
		float gcContentRight = 0f;

		GCIntervalTreeNode gcIntervalTreeNode = null;
		GCIsochoreIntervalTreeNode gcIsochoreIntervalTreeNode = null;

		if( node instanceof GCIntervalTreeNode){
			gcIntervalTreeNode = ( GCIntervalTreeNode)node;
		}else if( node instanceof GCIsochoreIntervalTreeNode){
			gcIsochoreIntervalTreeNode = ( GCIsochoreIntervalTreeNode)node;
		}

		numberofOverlappingBases = findNumberofOverlapingBases( node.getLow(), node.getHigh(), interval.getLow(),
				interval.getHigh());

		if( numberofOverlappingBases > 0){

			switch( calculateGC){

			case CALCULATE_GC_USING_GC_INTERVAL_TREE:
				gcContent = ( numberofOverlappingBases * gcIntervalTreeNode.getNumberofGCs() * 1.0f) / ( gcIntervalTreeNode.getHigh() - gcIntervalTreeNode.getLow() + 1);
				break;

			case CALCULATE_GC_USING_GC_ISOCHORE_INTERVAL_TREE:
				gcContent = ( numberofOverlappingBases * gcIsochoreIntervalTreeNode.getNumberofGCs() * 1.0f) / ( gcIsochoreIntervalTreeNode.getHigh() - gcIsochoreIntervalTreeNode.getLow() + 1);
				break;

			default:
				break;
			}// End of SWITCH

		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			gcContentLeft = findAllOverlappingGCIntervals( node.getLeft(), interval, calculateGC);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			gcContentRight = findAllOverlappingGCIntervals( node.getRight(), interval, calculateGC);

		}

		return gcContent + gcContentLeft + gcContentRight;

	}

	// Normal
	public void findAllOverlappingIntervals( IntervalTreeNode node, Interval interval) {

		if( node.getNodeName().isNotSentinel()){
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
				GlanetRunner.appendLog( "overlap" + node.getLow() + "\t" + node.getHigh());
			}

			if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
				findAllOverlappingIntervals( node.getLeft(), interval);
			}

			if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
				findAllOverlappingIntervals( node.getRight(), interval);

			}
		}
	}

	// Normal: for calculation of non overlapping base pairs in whole genome using interval tree
	public void findAllOverlappingIntervals(
			List<IntervalTreeNode> overlappedNodeList, 
			IntervalTreeNode root,
			IntervalTreeNode newNode) {

		if( root.getNodeName().isNotSentinel() && newNode.getNodeName().isNotSentinel()){
			if( overlaps( root.getLow(), root.getHigh(), newNode.getLow(), newNode.getHigh())){
				overlappedNodeList.add( root);
			}

			if( ( root.getLeft().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getLeft().getMax())){
				findAllOverlappingIntervals( overlappedNodeList, root.getLeft(), newNode);
			}

			if( ( root.getRight().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getRight().getMax()) && ( root.getLow() <= newNode.getHigh())){
				findAllOverlappingIntervals( overlappedNodeList, root.getRight(), newNode);

			}
		}

	}
	
	//27 OCTOBER 2015 starts
	//Test it
	public static List<IntervalTreeNode> getIntervalNodeList(IntervalTreeNode node){
		
		List<IntervalTreeNode> intervalTreeNodeList = new ArrayList<IntervalTreeNode>();
		
		if( node.getLeft().getNodeName().isNotSentinel())
			intervalTreeNodeList.addAll(getIntervalNodeList( node.getLeft()));

		if( node.getNodeName().isNotSentinel()){
			intervalTreeNodeList.add(node);
		}

		if( node.getRight().getNodeName().isNotSentinel())
			intervalTreeNodeList.addAll(getIntervalNodeList(node.getRight()));

		return intervalTreeNodeList;
		
	}
	//27 OCTOBER 2015 ends
	
	
	//27 OCTOBER 2015 starts
	// IntervalTree has nodes with mixed chromosome names
	// So we need to check the equality of chromosome names of nodes in addition to normal overlap check
	public static void findAllOverlappingIntervalsCheckingChrName(
			List<IntervalTreeNode> overlappedNodeList, 
			IntervalTreeNode root,
			IntervalTreeNode newNode) {

		if( root.getNodeName().isNotSentinel() && newNode.getNodeName().isNotSentinel()){
			
			if( root.getChromName().equals(newNode.getChromName()) && overlaps( root.getLow(), root.getHigh(), newNode.getLow(), newNode.getHigh())){
				overlappedNodeList.add( root);
			}

			if( ( root.getLeft().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getLeft().getMax())){
				findAllOverlappingIntervalsCheckingChrName(overlappedNodeList, root.getLeft(), newNode);
			}

			if( ( root.getRight().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getRight().getMax()) && ( root.getLow() <= newNode.getHigh())){
				findAllOverlappingIntervalsCheckingChrName(overlappedNodeList, root.getRight(), newNode);

			}
		}

	}
	//27 OCTOBER 2015 ends

	
	//27 OCTOBER 2015 starts
	public static int findNumberofOverlappingBases(
			List<IntervalTreeNode> overlappedNodeList, 
			IntervalTreeNode root,
			IntervalTreeNode newNode) {
		
		int numberofOverlappingBases = 0;

		if( root.getNodeName().isNotSentinel() && newNode.getNodeName().isNotSentinel()){
			
			numberofOverlappingBases = findNumberofOverlapingBases(root.getLow(), root.getHigh(), newNode.getLow(), newNode.getHigh());
			
			if(numberofOverlappingBases>0){
				
				overlappedNodeList.add(root);
			}

			if( ( root.getLeft().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getLeft().getMax())){
				numberofOverlappingBases += findNumberofOverlappingBases( overlappedNodeList,root.getLeft(), newNode);
			}

			if( ( root.getRight().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getRight().getMax()) && ( root.getLow() <= newNode.getHigh())){
				numberofOverlappingBases += findNumberofOverlappingBases( overlappedNodeList, root.getRight(), newNode);

			}
		}
		
		return numberofOverlappingBases;

	}
	//27 OCTOBER 2015 ends
	
	
	//27 OCTOBER 2015 starts
	public static int findNumberofOverlappingBases(
			IntervalTreeNode root,
			IntervalTreeNode newNode) {
		
		int numberofOverlappingBases = 0;

		if( root.getNodeName().isNotSentinel() && newNode.getNodeName().isNotSentinel()){
			
			numberofOverlappingBases = findNumberofOverlapingBases(root.getLow(), root.getHigh(), newNode.getLow(), newNode.getHigh());
			
			if( ( root.getLeft().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getLeft().getMax())){
				numberofOverlappingBases += findNumberofOverlappingBases(root.getLeft(), newNode);
			}

			if( ( root.getRight().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getRight().getMax()) && ( root.getLow() <= newNode.getHigh())){
				numberofOverlappingBases += findNumberofOverlappingBases(root.getRight(), newNode);

			}
		}
		
		return numberofOverlappingBases;

	}
	//27 OCTOBER 2015 ends

	
	//27 OCTOBER 2015 starts
	public static void updateMergedNode(IntervalTreeNode mergedNode, IntervalTreeNode overlappedNode) {

		if( overlappedNode.getLow() < mergedNode.getLow()){
			mergedNode.setLow( overlappedNode.getLow());
		}

		if( overlappedNode.getHigh() > mergedNode.getHigh()){
			mergedNode.setHigh( overlappedNode.getHigh());
		}

		mergedNode.setNumberofBases( mergedNode.getHigh() - mergedNode.getLow() + 1);
	}
	//27 OCTOBER 2015 ends
	
	
	//27 OCTOBER 2015 starts
	public static void updateMergedNode( DataDrivenExperimentIntervalTreeNode mergedNode, DataDrivenExperimentIntervalTreeNode overlappedNode, DataDrivenExperimentGeneType geneType) {

		if( overlappedNode.getLow() < mergedNode.getLow()){
			mergedNode.setLow( overlappedNode.getLow());
		}

		if( overlappedNode.getHigh() > mergedNode.getHigh()){
			mergedNode.setHigh( overlappedNode.getHigh());
		}

		mergedNode.setNumberofBases( mergedNode.getHigh() - mergedNode.getLow() + 1);
		mergedNode.setGeneSymbol(mergedNode.getGeneSymbol() + "_" + overlappedNode.getGeneSymbol());
		
		//Set TPM
		switch(geneType){
			case EXPRESSING_PROTEINCODING_GENES:
				mergedNode.setTpm(Math.min(mergedNode.getTpm(), overlappedNode.getTpm()));
				break;
			case NONEXPRESSING_PROTEINCODING_GENES:
				mergedNode.setTpm(Math.max(mergedNode.getTpm(), overlappedNode.getTpm()));
				break;
			
		}//End of SWITCH
		
	}
	//27 OCTOBER 2015 ends

	//27 OCTOBER 2015 starts
	public static IntervalTreeNode compute( 
			Map<IntervalTreeNode, IntervalTreeNode> splicedNode2CopiedNodeMap,
			IntervalTreeNode overlappedNode) {

		IntervalTreeNode node = splicedNode2CopiedNodeMap.get( overlappedNode);
		IntervalTreeNode savedPreviousNode = null;

		while( node != null){
			savedPreviousNode = node;
			node = splicedNode2CopiedNodeMap.get( node);
		}

		return savedPreviousNode;
	}
	//27 OCTOBER 2015 ends
	
	//30 OCTOBER 2015 starts
	public static int findNumberofOverlappingBases(
			IntervalTreeNode root,
			InputLineMinimal newNode) {
		
		int numberofOverlappingBases = 0;

		if( root.getNodeName().isNotSentinel()){
			
			numberofOverlappingBases = findNumberofOverlapingBases(root.getLow(), root.getHigh(), newNode.getLow(), newNode.getHigh());
			
			if( ( root.getLeft().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getLeft().getMax())){
				numberofOverlappingBases += findNumberofOverlappingBases(root.getLeft(), newNode);
			}

			if( ( root.getRight().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getRight().getMax()) && ( root.getLow() <= newNode.getHigh())){
				numberofOverlappingBases += findNumberofOverlappingBases(root.getRight(), newNode);

			}
		}
		
		return numberofOverlappingBases;

	}
	//30 OCTOBER 2015 ends
	
	//30 OCT 2015 starts
	public static void findNumberofOverlappingBases(
			InputLineMinimal inputLine,
			TLongObjectMap<IntervalTree> permutationNumberElementNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap,
			TLongIntMap permutationNumberElementNumberCellLineNumber2NumberofOverlappingBasesMap){
		
		long permutationNumberElementNumberCellLineNumber;
		IntervalTree intervalTree = null;
		int numberofOverlappingBases = 0;
		
		for(TLongObjectIterator<IntervalTree> itr = permutationNumberElementNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap.iterator(); itr.hasNext(); ){
		
			itr.advance();
			
			permutationNumberElementNumberCellLineNumber = itr.key();
			intervalTree = itr.value();
		
			numberofOverlappingBases = findNumberofOverlappingBases(intervalTree.getRoot(),inputLine);
			
			//Do we need accumulation here?
			//There must be no need for accumulation
			//There must be one interval tree per permutationNumberHistoneNumberCellLineNumber
			permutationNumberElementNumberCellLineNumber2NumberofOverlappingBasesMap.put(permutationNumberElementNumberCellLineNumber, numberofOverlappingBases);
		
		}//End of FOR each permutationNumberElementNumberCellLineNumber
		
	}
	//30 OCT 2015 ends
	
	
	//1 NOV 2015 starts
	public static void findNumberofOverlappingBases(
			InputLineMinimal inputLine,
			TIntObjectMap<IntervalTree> permutationNumberElementNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap,
			TIntIntMap permutationNumberElementNumberCellLineNumber2NumberofOverlappingBasesMap){
		
		int permutationNumberElementNumberCellLineNumber;
		IntervalTree intervalTree = null;
		int numberofOverlappingBases = 0;
		
		for(TIntObjectIterator<IntervalTree> itr = permutationNumberElementNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap.iterator(); itr.hasNext(); ){
		
			itr.advance();
			
			permutationNumberElementNumberCellLineNumber = itr.key();
			intervalTree = itr.value();
		
			numberofOverlappingBases = findNumberofOverlappingBases(intervalTree.getRoot(),inputLine);
			
			//Do we need accumulation here?
			//There must be no need for accumulation
			//There must be one interval tree per permutationNumberHistoneNumberCellLineNumber
			permutationNumberElementNumberCellLineNumber2NumberofOverlappingBasesMap.put(permutationNumberElementNumberCellLineNumber, numberofOverlappingBases);
		
		}//End of FOR each permutationNumberElementNumberCellLineNumber
		
	}
	//1 NOV 2015 ends
	
	//30 OCT 2015 starts
	public static void constructAnIntervalTreeWithNonOverlappingNodes(
			TLongObjectMap<List<IntervalTreeNode>> permutationNumberElementNumberCellLineNumber2OverlappingNodeListMap,
			TLongObjectMap<IntervalTree> permutationNumberElementNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap){
		
		long permutationNumberElementNumberCellLineNumber;
		List<IntervalTreeNode> overlappingNodeList = null;
		IntervalTree intervalTree = null;
		
		for(TLongObjectIterator<List<IntervalTreeNode>> itr = permutationNumberElementNumberCellLineNumber2OverlappingNodeListMap.iterator();itr.hasNext();){
			
			itr.advance();
			
			permutationNumberElementNumberCellLineNumber = itr.key();
			overlappingNodeList = itr.value();
			
//			//debug starts
//			logger.info(	"For permutationNumberMixedNumber: " + permutationNumberElementNumberCellLineNumber +  "\t"  +
//					"Constructing the interval tree  from the" + "\t" +
//					overlappingNodeList.size() + " nodes below starts.");
//	
//			for(int i = 0; i<overlappingNodeList.size(); i++){
//				logger.info(	"For permutationNumberMixedNumber: " + permutationNumberElementNumberCellLineNumber +  "\t"  +
//								"ChromName: " + overlappingNodeList.get(i).getChromName() + "\t" + 
//								"Low: " + overlappingNodeList.get(i).getLow() + "\t" + 
//								"High: " +  overlappingNodeList.get(i).getHigh() + "\t" + 
//								"NumberofBases:" + (overlappingNodeList.get(i).getHigh() - overlappingNodeList.get(i).getLow() + 1));				
//			}//End of FOR
//			//debug ends
			
		
		
			intervalTree = constructAnIntervalTreeWithNonOverlappingNodes(permutationNumberElementNumberCellLineNumber,overlappingNodeList);
		
//			//debug starts
//			logger.info(	"For permutationNumberMixedNumber: " + permutationNumberElementNumberCellLineNumber +  "\t"  
//					+"Constructed tree has these many NumberofNodes: " + intervalTree.getNumberofNodes() +  "\t" +
//					"NumberofNonOverlappingBases: " + intervalTree.getNumberofNonOverlappingBases());
//	
//			logger.info(	"For permutationNumberMixedNumber: " + permutationNumberElementNumberCellLineNumber +  "\t"  +
//					"Constructing the interval tree ends." );			
//			//debug ends
		
			permutationNumberElementNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap.put(permutationNumberElementNumberCellLineNumber, intervalTree);
			
		}//End of FOR each permutationNumberHistoneNumberCellLineNumber in the map
		
	}
	//30 OCT 2015 ends
	
	//1 NOV 2015 starts
	public static void constructAnIntervalTreeWithNonOverlappingNodes(
			TIntObjectMap<List<IntervalTreeNode>> permutationNumberElementNumberCellLineNumber2OverlappingNodeListMap,
			TIntObjectMap<IntervalTree> permutationNumberElementNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap){
		
		int permutationNumberElementNumberCellLineNumber;
		List<IntervalTreeNode> overlappingNodeList = null;
		IntervalTree intervalTree = null;
		
		for(TIntObjectIterator<List<IntervalTreeNode>> itr = permutationNumberElementNumberCellLineNumber2OverlappingNodeListMap.iterator();itr.hasNext();){
			
			itr.advance();
			
			permutationNumberElementNumberCellLineNumber = itr.key();
			overlappingNodeList = itr.value();
			
//			//debug starts	
//			logger.info(	"For permutationNumberMixedNumber: " + permutationNumberElementNumberCellLineNumber +  "\t"  +
//					"Constructing the interval tree  from the" + "\t" +
//					overlappingNodeList.size() + " nodes below starts.");
//	
//			for(int i = 0; i<overlappingNodeList.size(); i++){
//				logger.info(	"For permutationNumberMixedNumber: " + permutationNumberElementNumberCellLineNumber +  "\t"  +
//								"ChromName: " + overlappingNodeList.get(i).getChromName() + "\t" + 
//								"Low: " + overlappingNodeList.get(i).getLow() + "\t" + 
//								"High: " +  overlappingNodeList.get(i).getHigh() + "\t" + 
//								"NumberofBases:" + (overlappingNodeList.get(i).getHigh() - overlappingNodeList.get(i).getLow() + 1));				
//			}//End of FOR				
//			//debug ends
	
			intervalTree = constructAnIntervalTreeWithNonOverlappingNodes((long)permutationNumberElementNumberCellLineNumber,overlappingNodeList);
			
//			//debug starts
//			logger.info(	"For permutationNumberMixedNumber: " + permutationNumberElementNumberCellLineNumber +  "\t"  
//					+"Constructed tree has these many NumberofNodes: " + intervalTree.getNumberofNodes() +  "\t" +
//					"NumberofNonOverlappingBases: " + intervalTree.getNumberofNonOverlappingBases());
//	
//			logger.info(	"For permutationNumberMixedNumber: " + permutationNumberElementNumberCellLineNumber +  "\t"  +
//					"Constructing the interval tree ends." );			
//			//debug ends
			
			permutationNumberElementNumberCellLineNumber2IntervalTreeWithNonOverlappingNodesMap.put(permutationNumberElementNumberCellLineNumber, intervalTree);
			
		}//End of FOR each permutationNumberHistoneNumberCellLineNumber in the map
		
	}
	//1 NOV 2015 ends
	
	//30 OCT 2015 starts
	public static IntervalTree constructAnIntervalTreeWithNonOverlappingNodes(
			Long permutationNumberMixedNumber,
			List<IntervalTreeNode> intervalNodeList){
		
		//Construct an interval tree consisting of nonOverlapping intervals
		IntervalTree intervalTree = null;
		IntervalTreeNode  intervalTreeNode = null;
		
		//Get each interval in the overlappedNodeList
		//Insert one by one into the interval tree
		for(int i = 0; i < intervalNodeList.size(); i++){
			
			intervalTreeNode =  intervalNodeList.get(i);
			
//			//debug starts
//			logger.info("For permutationNumberMixedNumber: " + permutationNumberMixedNumber + " intervalTreeNode to be inserted: " + intervalTreeNode.getChromName() + " " + intervalTreeNode.getColor() + " " +intervalTreeNode.getLow() + " " + intervalTreeNode.getHigh());
//			//debug ends
			
			
			//Insertion for the first time
			if (intervalTree == null){
				intervalTree = new IntervalTree();
				intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);
				//intervalTree.intervalTreeInsert(permutationNumberMixedNumber,intervalTree, intervalTreeNode);
				
//				//debug starts
//				logger.info(	"permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +
//								"Infix traversal of the interval tree before inserting intervalTreeNode");
//				
//				intervalTree.intervalTreeInfixTraversal(permutationNumberMixedNumber,intervalTree.getRoot());
//				//debug ends
				
			}//End of IF 
			
			else{
				
				List<IntervalTreeNode>  overlappedNodeList = new ArrayList<IntervalTreeNode>();
				
				IntervalTree.findAllOverlappingIntervalsCheckingChrName(
						overlappedNodeList, 
						intervalTree.getRoot(),
						intervalTreeNode);
				
				
				// there is an overlap between the node to be inserted to the intervalTree right now and already existing nodes in the interval tree
				if( overlappedNodeList != null && overlappedNodeList.size() > 0){
					
					IntervalTreeNode mergedNode  = null;
					
					//Question what must be the color of the merged node?
					//Merged node is inserted to the interval tree.
					//Color of the newly insertedNode is set in the intervalTreeInsert method of IntervalTree class

					//Initialize the mergedNode from the concerned intervalTreeNode
					if (intervalTreeNode instanceof TforHistoneIntervalTreeNodeWithNumbers){
						
						mergedNode = new TforHistoneIntervalTreeNodeWithNumbers(
								intervalTreeNode.getChromName(), 
								intervalTreeNode.getLow(), 
								intervalTreeNode.getHigh(), 
								((TforHistoneIntervalTreeNodeWithNumbers) intervalTreeNode).getTforHistoneNumber(),
								((TforHistoneIntervalTreeNodeWithNumbers) intervalTreeNode).getCellLineNumber(), 
								((TforHistoneIntervalTreeNodeWithNumbers) intervalTreeNode).getFileNumber(), 
								NodeType.MERGED);
						
					}
					
					else if (intervalTreeNode instanceof DnaseIntervalTreeNodeWithNumbers){
						
						mergedNode = new DnaseIntervalTreeNodeWithNumbers(
								intervalTreeNode.getChromName(), 
								intervalTreeNode.getLow(), 
								intervalTreeNode.getHigh(),
								((DnaseIntervalTreeNodeWithNumbers) intervalTreeNode).getCellLineNumber(), 
								((DnaseIntervalTreeNodeWithNumbers) intervalTreeNode).getFileNumber(), 
								NodeType.MERGED);
						
					}
					
					else if(intervalTreeNode instanceof IntervalTreeNode){
						
						mergedNode = new IntervalTreeNode(
						intervalTreeNode.getChromName(),
						intervalTreeNode.getLow(), 
						intervalTreeNode.getHigh(), 
						NodeType.MERGED);
					}
					
					
					IntervalTreeNode splicedoutNode = null;
					IntervalTreeNode nodetoBeDeleted = null;
					// you may try to delete a node which is already spliced out by former deletions
					// therefore you must keep track of the real node to be deleted in case of trial of deletion of an already spliced out node.
					Map<IntervalTreeNode, IntervalTreeNode> splicedoutNode2CopiedNodeMap = new HashMap<IntervalTreeNode, IntervalTreeNode>();

//					//debug starts
//					logger.info( 		"permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +
//										"This node will be merged:" + "\t" + 
//										mergedNode.getColor() + "\t" +
//										mergedNode.getChromName() + "\t" +
//										mergedNode.getLow() + "\t" + 
//										mergedNode.getHigh() );
//					
//					logger.info(	"permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +
//									"Infix traversal of the interval tree before inserting mergedNode");
//					
//					intervalTree.intervalTreeInfixTraversal(permutationNumberMixedNumber,intervalTree.getRoot());
//					//debug ends
					
					
					//Delete each overlappedNode in the overlappedNodeList
					for( int j = 0; j < overlappedNodeList.size(); j++){
						
						IntervalTreeNode overlappedNode = overlappedNodeList.get(j);
						
//						//debug starts
//						logger.info(	"permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +
//											"there is overlappedNode:" + "\t" +
//											overlappedNode.getColor() + "\t" +
//											overlappedNode.getChromName() + "\t" + 
//											overlappedNode.getLow() + "\t" + 
//											overlappedNode.getHigh());
//						//debug ends

						//Update the mergedNode with the overlappedNode
						//Just update low, high and numberofBases
						//Therefore checking being instanceof IntervalTreeNode is enough
						if (mergedNode instanceof IntervalTreeNode){
							IntervalTree.updateMergedNode(mergedNode,overlappedNode);
						}
						
						// if the to be deleted, intended interval tree node is an already spliced out node
						// (By the way spliced out node means that a node that is no more valid, a death node)
						// (Spliced out node  is the node that's content has been copied to a formerly deleted node)
						// (Since formerly deleted node has one or two children and one of its children (spliced out one) took its place by being copied)
						// in other words if it is copied into another interval tree node
						// then you have to delete that node, not the already spliced out node

						//Since we have updated the mergedNode with the overlappedNode
						//we don't need overlappedNode anymore
						//Now delete the overlappedNode
						// If overlappedNode is already an splicedout node
						// which means that it is no more a valid node of the interval tree
						// then we need to find the node where the overlapped node is copied into
						// and delete that node
						nodetoBeDeleted = IntervalTree.compute(splicedoutNode2CopiedNodeMap, overlappedNode);

						if( nodetoBeDeleted != null){
							
//							//debug starts
//							logger.info("permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + 
//										"This nodetoBeDeleted will be deleted " + nodetoBeDeleted.getChromName() + " " + nodetoBeDeleted.getColor() + " " + nodetoBeDeleted.getLow() + " " + nodetoBeDeleted.getHigh());
//							//debug ends
							
							// they are the same
							// current overlapped node to_be_deleted has been copied to the previously deleted overlapped node
							// in other words, current overlapped node to_be_deleted is spliced out by the previous delete operation
							// so delete that previously deleted overlapped node in order to delete the current overlapped node
							// since current overlapped node is copied to the previously deleted overlapped node
							// Now we can delete this overlappedNode
							splicedoutNode = intervalTree.intervalTreeDelete(intervalTree, nodetoBeDeleted);
							//splicedoutNode = intervalTree.intervalTreeDelete(permutationNumberMixedNumber,intervalTree, nodetoBeDeleted);
							
//							//debug starts
//							logger.info(	"permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +
//											"Infix traversal of the interval tree after deleting nodetoBeDeleted");
//							
//							
//							intervalTree.intervalTreeInfixTraversal(permutationNumberMixedNumber,intervalTree.getRoot());
//							//debug ends


							if( splicedoutNode != nodetoBeDeleted)
								splicedoutNode2CopiedNodeMap.put(splicedoutNode, nodetoBeDeleted);
							
						}else{
							
//							//debug starts
//							logger.info("permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" + 
//										"This overlappedNode will be deleted " + overlappedNode.getChromName() + " " + overlappedNode.getColor() + " " + overlappedNode.getLow() + " " + overlappedNode.getHigh());
//							//debug ends
							
							// Now we can delete this overlappedNode
							splicedoutNode = intervalTree.intervalTreeDelete(intervalTree, overlappedNode);
							//splicedoutNode = intervalTree.intervalTreeDelete(permutationNumberMixedNumber,intervalTree, overlappedNode);
							
//							//debug starts
//							logger.info(	"permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +
//											"Infix traversal of the interval tree after deleting overlappedNode");
//							
//							
//							intervalTree.intervalTreeInfixTraversal(permutationNumberMixedNumber,intervalTree.getRoot());
//							//debug ends

							if( splicedoutNode != overlappedNode)
								splicedoutNode2CopiedNodeMap.put(splicedoutNode, overlappedNode);

						}//End of ELSE

					}// end of FOR: each overlapped node in the list
					
					
//					//debug starts
//					logger.info(	"permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +
//									"This mergedNode is inserted: " + mergedNode.getChromName() + " " + mergedNode.getColor() + " " + mergedNode.getLow() + " " + mergedNode.getHigh());
//					//debug ends
					
					//Insert the mergedNode
					intervalTree.intervalTreeInsert(intervalTree, mergedNode);
					//intervalTree.intervalTreeInsert(permutationNumberMixedNumber,intervalTree, mergedNode);
					
//					//debug starts
//					logger.info(	"permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +
//									"Infix traversal of the interval tree after inserting mergedNode");
//					intervalTree.intervalTreeInfixTraversal(permutationNumberMixedNumber,intervalTree.getRoot());
//					//debug ends


				}// overlappedNodeList is not null
				
				// there is no overlap
				else{
					
					
					// insert interval
					intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);
					//intervalTree.intervalTreeInsert(permutationNumberMixedNumber,intervalTree, intervalTreeNode);
					
//					//debug starts
//					logger.info(	"permutationNumberMixedNumber: " + permutationNumberMixedNumber + "\t" +
//									"Infix traversal of the interval tree after inserting intervalTreeNode");
//					
//					intervalTree.intervalTreeInfixTraversal(permutationNumberMixedNumber,intervalTree.getRoot());
//					//debug ends
					
				}//End of ELSE
				
				
			}//End of ELSE intervalTree is not null
			
		}//End of FOR each interval node to be inserted one by one into the intervalTree
		
		return intervalTree;
	}
	//30 OCT 2015 ends
	
	
	
	
	//Given an intervalNodeList 
	//Construct an interval tree
	//By inserting intervals in the given intervalNodeList one by one into the interval tree
	//While inserting if there is any overlap
	//Merge them and delete the unnecessary node
	//At the end, get an interval tree with nonOverlapping nodes
	//Pay attention the resulting interval tree keeps interval nodes from any chrname
	//Mixed interval tree w.r.t. chrName
	public static IntervalTree constructAnIntervalTreeWithNonOverlappingNodes(List<IntervalTreeNode> intervalNodeList, DataDrivenExperimentGeneType geneType){
		
		//Construct an interval tree consisting of nonOverlapping intervals
		IntervalTree intervalTree = null;
		IntervalTreeNode  intervalTreeNode = null;
		
		//Get each interval in the overlappedNodeList
		//Insert one by one into the interval tree
		for(int i = 0; i < intervalNodeList.size(); i++){
			
			intervalTreeNode =  intervalNodeList.get(i);
			
			//Insertion for the first time
			if (intervalTree == null){
				intervalTree = new IntervalTree();
				intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);
			}//End of IF 
			
			else{
				
				List<IntervalTreeNode>  overlappedNodeList = new ArrayList<IntervalTreeNode>();
				
				IntervalTree.findAllOverlappingIntervalsCheckingChrName(
						overlappedNodeList, 
						intervalTree.getRoot(),
						intervalTreeNode);
				
				
				// there is overlap
				if( overlappedNodeList != null && overlappedNodeList.size() > 0){
					
					IntervalTreeNode mergedNode  = null;

					//Initialize the mergedNode from the concerned intervalTreeNode
					 if(intervalTreeNode instanceof DataDrivenExperimentIntervalTreeNode){
					
								mergedNode = new DataDrivenExperimentIntervalTreeNode(
								intervalTreeNode.getChromName(),
								intervalTreeNode.getLow(), 
								intervalTreeNode.getHigh(), 
								((DataDrivenExperimentIntervalTreeNode) intervalTreeNode).getGeneSymbol(),
								((DataDrivenExperimentIntervalTreeNode) intervalTreeNode).getTpm(),
								NodeType.MERGED);
								
					}else if(intervalTreeNode instanceof IntervalTreeNode){
						
						mergedNode = new IntervalTreeNode(
						intervalTreeNode.getChromName(),
						intervalTreeNode.getLow(), 
						intervalTreeNode.getHigh(), 
						NodeType.MERGED);
					}
					
					
					
					IntervalTreeNode splicedoutNode = null;
					IntervalTreeNode nodetoBeDeleted = null;
					// you may try to delete a node which is already spliced out by former deletions
					// therefore you must keep track of the real node to be deleted in case of trial of deletion of an already spliced out node.
					Map<IntervalTreeNode, IntervalTreeNode> splicedoutNode2CopiedNodeMap = new HashMap<IntervalTreeNode, IntervalTreeNode>();

					for( int j = 0; j < overlappedNodeList.size(); j++){

						IntervalTreeNode overlappedNode = overlappedNodeList.get(j);

						//Update the mergedNode with the overlappedNode
						if (mergedNode instanceof DataDrivenExperimentIntervalTreeNode){
							IntervalTree.updateMergedNode((DataDrivenExperimentIntervalTreeNode)mergedNode, (DataDrivenExperimentIntervalTreeNode)overlappedNode,geneType);
						}else if (mergedNode instanceof IntervalTreeNode){
							IntervalTree.updateMergedNode(mergedNode,overlappedNode);
						}
						
						// if the to be deleted, intended interval tree node is an already spliced out node
						// (By the way spliced out node means that a node that is no more valid, a death node)
						// (Spliced out node  is the node that's content has been copied to a formerly deleted node)
						// (Since formerly deleted node has one or two children and one of its children (spliced out one) took its place by being copied)
						// in other words if it is copied into another interval tree node
						// then you have to delete that node, not the already spliced out node

						//Since we have updated the mergedNode with the overlappedNode
						//we don't need overlappedNode anymore
						//Now delete the overlappedNode
						// If overlappedNode is already an splicedout node
						// which means that it is no more a valid node of the interval tree
						// then we need to find the node where the overlapped node is copied into
						// and delete that node
						nodetoBeDeleted = IntervalTree.compute(splicedoutNode2CopiedNodeMap, overlappedNode);

						if( nodetoBeDeleted != null){
							// they are the same
							// current overlapped node to_be_deleted has been copied to the previously deleted overlapped node
							// in other words, current overlapped node to_be_deleted is spliced out by the previous delete operation
							// so delete that previously deleted overlapped node in order to delete the current overlapped node
							// since current overlapped node is copied to the previously deleted overlapped node
							// Now we can delete this overlappedNode
							splicedoutNode = intervalTree.intervalTreeDelete(intervalTree, nodetoBeDeleted);

							if( splicedoutNode != nodetoBeDeleted)
								splicedoutNode2CopiedNodeMap.put(splicedoutNode, nodetoBeDeleted);
							
						}else{
							
							// Now we can delete this overlappedNode
							splicedoutNode = intervalTree.intervalTreeDelete(intervalTree, overlappedNode);

							if( splicedoutNode != overlappedNode)
								splicedoutNode2CopiedNodeMap.put(splicedoutNode, overlappedNode);

						}//End of ELSE

					}// end of FOR: each overlapped node in the list
					
					intervalTree.intervalTreeInsert( intervalTree, mergedNode);

				}// overlappedNodeList is not null
				
				// there is no overlap
				else{
					// insert interval
					intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);
				}//End of ELSE
				
				
			}//End of ELSE intervalTree is not null
			
		}//End of FOR each interval node to be inserted
		
		return intervalTree;
				
	}
	//27 OCTOBER 2015 ends
	

	// Normal
	// First argument is the root of the interval tree
	// Second argument is the node that is looked for whether it overlaps with
	// any node in the interval tree
	public IntervalTreeNode findFirstOverlappingIntervals( IntervalTreeNode root, IntervalTreeNode newNode) {

		IntervalTreeNode overlappedNode = null;

		if( root.getNodeName().isNotSentinel()){
			if( overlaps( root.getLow(), root.getHigh(), newNode.getLow(), newNode.getHigh())){
				return root;

			}else{
				if( ( root.getLeft().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getLeft().getMax())){
					overlappedNode = findFirstOverlappingIntervals( root.getLeft(), newNode);
					if( overlappedNode != null)
						return overlappedNode;
				}

				if( ( root.getRight().getNodeName().isNotSentinel()) && ( newNode.getLow() <= root.getRight().getMax()) && ( root.getLow() <= newNode.getHigh())){
					overlappedNode = findFirstOverlappingIntervals( root.getRight(), newNode);
					if( overlappedNode != null)
						return overlappedNode;
				}
			}

		}

		return null;

	}

	// Search1
	public void findAllOverlappingHistoneIntervals( IntervalTreeNode node, Interval interval,
			BufferedWriter bufferedWriter, List<IntervalTreeNode> overlappingNodeList) {

		TforHistoneIntervalTreeNode castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){

			if( node instanceof TforHistoneIntervalTreeNode){
				castedNode = ( TforHistoneIntervalTreeNode)node;
			}

			overlappingNodeList.add( node);

			try{
				bufferedWriter.write( "histone" + "\t" + castedNode.getChromName().toString() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName().toString() + "\t" + castedNode.getCellLineName().toString() + "\t" + castedNode.getFileName().toString() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingHistoneIntervals( node.getLeft(), interval, bufferedWriter, overlappingNodeList);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingHistoneIntervals( node.getRight(), interval, bufferedWriter, overlappingNodeList);

		}

	}

	// @todo
	// with Numbers
	// Empirical P Value Calculation
	// with IO
	public void findAllOverlappingHistoneIntervalsWithIOWithNumbers( String outputFolder, int permutationNumber,
			IntervalTreeNode node, InputLineMinimal interval, ChromosomeName chromName,
			TLongObjectMap<BufferedWriter> bufferedWriterHashMap,
			TLongIntMap permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		long permutationNumberHistoneNumberCellLineNumber;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
				castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
			}

			try{
				permutationNumberHistoneNumberCellLineNumber = generateMixedNumber(
						permutationNumber,
						castedNode.getTforHistoneNumber(),
						castedNode.getCellLineNumber(),
						( short)0,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

				bufferedWriter = bufferedWriterHashMap.get( permutationNumberHistoneNumberCellLineNumber);

				if( bufferedWriter == null){

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_HISTONE + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + permutationNumberHistoneNumberCellLineNumber + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "histone" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "HistoneNumber" + "\t" + "CellLineNumber" + "\t" + "FileNumber" + System.getProperty( "line.separator"));
					bufferedWriter.flush();

					bufferedWriterHashMap.put( permutationNumberHistoneNumberCellLineNumber, bufferedWriter);
				}

				if( !( permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap.containsKey( permutationNumberHistoneNumberCellLineNumber))){
					permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap.put(
							permutationNumberHistoneNumberCellLineNumber, 1);
				}

				bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "histone" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTforHistoneNumber() + "\t" + castedNode.getCellLineNumber() + "\t" + castedNode.getFileNumber() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingHistoneIntervalsWithIOWithNumbers( outputFolder, permutationNumber, node.getLeft(),
					interval, chromName, bufferedWriterHashMap,
					permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingHistoneIntervalsWithIOWithNumbers( outputFolder, permutationNumber, node.getRight(),
					interval, chromName, bufferedWriterHashMap,
					permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap, overlapDefinition);

		}

	}

	// with Numbers
	// @todo

	// Empirical P Value Calculation
	// with IO
	public void findAllOverlappingHistoneIntervals( String outputFolder, int permutationNumber, IntervalTreeNode node,
			Interval interval, ChromosomeName chromName, Map<String, BufferedWriter> bufferedWriterHashMap,
			Map<String, Integer> permutationNumberHistoneNameCellLineName2ZeroorOneMap, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		String permutationNumberHistoneNameaCellLineName;

		TforHistoneIntervalTreeNode castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof TforHistoneIntervalTreeNode){
				castedNode = ( TforHistoneIntervalTreeNode)node;
			}

			try{
				permutationNumberHistoneNameaCellLineName = Commons.PERMUTATION + permutationNumber + "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();

				bufferedWriter = bufferedWriterHashMap.get( permutationNumberHistoneNameaCellLineName);

				if( bufferedWriter == null){

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_HISTONE + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + permutationNumberHistoneNameaCellLineName + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriterHashMap.put( permutationNumberHistoneNameaCellLineName, bufferedWriter);
				}

				if( permutationNumberHistoneNameCellLineName2ZeroorOneMap.get( permutationNumberHistoneNameaCellLineName) == null){
					permutationNumberHistoneNameCellLineName2ZeroorOneMap.put(
							permutationNumberHistoneNameaCellLineName, 1);
				}

				bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "histone" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName() + "\t" + String.valueOf( castedNode.getCellLineName()) + "\t" + castedNode.getFileName() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingHistoneIntervals( outputFolder, permutationNumber, node.getLeft(), interval, chromName,
					bufferedWriterHashMap, permutationNumberHistoneNameCellLineName2ZeroorOneMap, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingHistoneIntervals( outputFolder, permutationNumber, node.getRight(), interval, chromName,
					bufferedWriterHashMap, permutationNumberHistoneNameCellLineName2ZeroorOneMap, overlapDefinition);

		}

	}
	
	//30 OCTOBER 2015 starts
	//Without IO
	//WithNumbers
	//WithOverlappingNodeList
	//AssociationMeasureType NUMBER_OF_OVERLAPPING_BASES
	public void findAllOverlappingHistoneIntervalsWithoutIOWithNumbers(
			int permutationNumber, 
			IntervalTreeNode node,
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TLongObjectMap<List<IntervalTreeNode>> permutationNumberHistoneNumberCellLineNumber2OverlappingNodeListMap, 
			int overlapDefinition) {

		long permutationNumberHistoneNumberCellLineNumber;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;
		
		List<IntervalTreeNode> overlappingNodeList = null;
		

		if( overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			
			if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
				//castedNode = (TforHistoneIntervalTreeNodeWithNumbers)node;
				//castedNode must be newly created and there must be no color assigned at first.
				castedNode = new TforHistoneIntervalTreeNodeWithNumbers(
						node.getChromName(),
						node.getLow(),
						node.getHigh(),
						((TforHistoneIntervalTreeNodeWithNumbers) node).getTforHistoneNumber(), 
						((TforHistoneIntervalTreeNodeWithNumbers) node).getCellLineNumber(), 
						((TforHistoneIntervalTreeNodeWithNumbers) node).getFileNumber(),
						NodeType.ORIGINAL);
				
			}


			permutationNumberHistoneNumberCellLineNumber = generateMixedNumber(
					permutationNumber,
					castedNode.getTforHistoneNumber(),
					castedNode.getCellLineNumber(),
					(short)0,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			
			overlappingNodeList = permutationNumberHistoneNumberCellLineNumber2OverlappingNodeListMap.get(permutationNumberHistoneNumberCellLineNumber);
			
			//Pay attention: you have to add castedNode to the list
			//Further on you will need tforHistoneNumber in constructAnIntervalTreeWithNonOverlappingNodes method
			if (overlappingNodeList == null){
				overlappingNodeList = new ArrayList<IntervalTreeNode>();
				overlappingNodeList.add(castedNode);
				permutationNumberHistoneNumberCellLineNumber2OverlappingNodeListMap.put(permutationNumberHistoneNumberCellLineNumber, overlappingNodeList);
			}else{
				overlappingNodeList.add(castedNode);
			}

			
		}//End of IF there is an overlap

		if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow() <= node.getLeft().getMax())){
			
			findAllOverlappingHistoneIntervalsWithoutIOWithNumbers( 
					permutationNumber, 
					node.getLeft(), 
					interval,
					chromName, 
					permutationNumberHistoneNumberCellLineNumber2OverlappingNodeListMap, 
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			
			findAllOverlappingHistoneIntervalsWithoutIOWithNumbers( 
					permutationNumber, 
					node.getRight(), 
					interval,
					chromName, 
					permutationNumberHistoneNumberCellLineNumber2OverlappingNodeListMap, 
					overlapDefinition);

		}

	}	
	//30 OCTOBER 2015 ends


	// Empirical P Value Calculation
	// without IO
	// without overlappedNodeList
	// with Numbers
	//AssociationMeasureType EXISTENCE_OF_OVERLAP
	public void findAllOverlappingHistoneIntervalsWithoutIOWithNumbers(
			int permutationNumber, 
			IntervalTreeNode node,
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TLongIntMap permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap, 
			int overlapDefinition) {

		long permutationNumberHistoneNumberCellLineNumber;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
				castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
			}

			permutationNumberHistoneNumberCellLineNumber = generateMixedNumber(
					permutationNumber,
					castedNode.getTforHistoneNumber(),
					castedNode.getCellLineNumber(),
					( short)0,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

			if( !( permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap.containsKey( permutationNumberHistoneNumberCellLineNumber))){
				permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap.put(permutationNumberHistoneNumberCellLineNumber, 1);
				
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingHistoneIntervalsWithoutIOWithNumbers(
					permutationNumber, 
					node.getLeft(), 
					interval,
					chromName, 
					permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap, 
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingHistoneIntervalsWithoutIOWithNumbers(
					permutationNumber, 
					node.getRight(), 
					interval,
					chromName, 
					permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap, 
					overlapDefinition);

		}

	}

	// Empirical P Value Calculation
	// without IO
	// without overlappedNodeList
	public void findAllOverlappingHistoneIntervals( int permutationNumber, IntervalTreeNode node, Interval interval,
			ChromosomeName chromName, Map<String, Integer> permutationNumberHistoneNameCellLineName2ZeroorOneMap,
			int overlapDefinition) {

		String permutationNumberHistoneNameaCellLineName;

		TforHistoneIntervalTreeNode castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof TforHistoneIntervalTreeNode){
				castedNode = ( TforHistoneIntervalTreeNode)node;
			}

			permutationNumberHistoneNameaCellLineName = Commons.PERMUTATION + permutationNumber + "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();

			if( permutationNumberHistoneNameCellLineName2ZeroorOneMap.get( permutationNumberHistoneNameaCellLineName) == null){
				permutationNumberHistoneNameCellLineName2ZeroorOneMap.put( permutationNumberHistoneNameaCellLineName, 1);
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingHistoneIntervals( permutationNumber, node.getLeft(), interval, chromName,
					permutationNumberHistoneNameCellLineName2ZeroorOneMap, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingHistoneIntervals( permutationNumber, node.getRight(), interval, chromName,
					permutationNumberHistoneNameCellLineName2ZeroorOneMap, overlapDefinition);

		}

	}

	// Empirical P Value Calculation
	// without IO
	// with overlappedNodeList
	public void findAllOverlappingHistoneIntervals( 
			int repeatNumber, 
			int permutationNumber,
			int NUMBER_OF_PERMUTATIONS, 
			IntervalTreeNode node, 
			Interval interval, 
			String chromName,
			Map<String, Integer> permutationNumberHistoneNameCellLineName2ZeroorOneMap,
			List<IntervalTreeNode> overlappingNodeList) {

		String permutationNumberHistoneNameaCellLineName;

		TforHistoneIntervalTreeNode castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){

			if( node instanceof TforHistoneIntervalTreeNode){
				castedNode = ( TforHistoneIntervalTreeNode)node;
			}

			overlappingNodeList.add( node);

			permutationNumberHistoneNameaCellLineName = Commons.PERMUTATION + ( ( ( repeatNumber - 1) * NUMBER_OF_PERMUTATIONS) + permutationNumber) + "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();

			if( permutationNumberHistoneNameCellLineName2ZeroorOneMap.get( permutationNumberHistoneNameaCellLineName) == null){
				permutationNumberHistoneNameCellLineName2ZeroorOneMap.put( permutationNumberHistoneNameaCellLineName, 1);
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingHistoneIntervals( repeatNumber, permutationNumber, NUMBER_OF_PERMUTATIONS,
					node.getLeft(), interval, chromName, permutationNumberHistoneNameCellLineName2ZeroorOneMap,
					overlappingNodeList);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingHistoneIntervals( repeatNumber, permutationNumber, NUMBER_OF_PERMUTATIONS,
					node.getRight(), interval, chromName, permutationNumberHistoneNameCellLineName2ZeroorOneMap,
					overlappingNodeList);

		}

	}

	//Modified 20 July 2015
	// Annotation
	// HISTONE
	// With Numbers
	// Search2 For finding the number of each histoneNameandCellLineName: k for
	// the given search input size: n
	// For each search input line, each histoneNameandCellLineName will have
	// value 1 or 0
	// These 1 or 0's will be accumulated in histoneNameandCellLineName2KMap
	public void findAllOverlappingHistoneIntervalsWithNumbers(
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			IntervalTreeNode node, 
			Interval interval, 
			ChromosomeName chromName,
			TIntByteMap histoneNumberCellLineNumber2ZeroorOneMap, 
			int overlapDefinition,
			TIntObjectMap<String> histoneNumber2HistoneNameMap,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap, 
			TIntObjectMap<String> fileNumber2FileNameMap) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		String histoneName;
		String cellLineName;
		String fileName;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
			castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
		}

		
		// KEGG Pathway number included
		// WHY?
//		int histoneNumberCellLineNumber = generateElementNumberCellLineNumberKeggPathwayNumber(
//				castedNode.getTforHistoneNumber(),
//				castedNode.getCellLineNumber(),
//				( short)0,
//				GeneratedMixedNumberDescriptionOrderLength.INT_4DIGITS_ELEMENTNUMBER_3DIGITS_CELLLINENUMBER_3DIGITS_KEGGPATHWAYNUMBER);
		int elementNumberCellLineNumber = IntervalTree.generateElementNumberCellLineNumber(
				castedNode.getTforHistoneNumber(), 
				castedNode.getCellLineNumber(),
				GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);

		
		
		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),
				overlapDefinition)){
			try{

				// Write Annotation Found Overlaps to element Named File
				if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

					histoneName = histoneNumber2HistoneNameMap.get( castedNode.getTforHistoneNumber());
					cellLineName = cellLineNumber2CellLineNameMap.get( castedNode.getCellLineNumber());
					fileName = fileNumber2FileNameMap.get( castedNode.getFileNumber());

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.HISTONE_ANNOTATION_DIRECTORY + histoneName + "_" + cellLineName + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);

					if( !histoneNumberCellLineNumber2ZeroorOneMap.containsKey( elementNumberCellLineNumber)){
						histoneNumberCellLineNumber2ZeroorOneMap.put( elementNumberCellLineNumber, Commons.BYTE_1);
						bufferedWriter.write( "#Searched for chr" + "\t" + "interval low" + "\t" + "interval high" + "\t" + "histone node chrom name" + "\t" + "node Low" + "\t" + "node high" + "\t" + "node HistoneName" + "\t" + "node CellLineName" + "\t" + "node FileName" + System.getProperty( "line.separator"));
					}

					bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + histoneName + "\t" + cellLineName + "\t" + fileName + System.getProperty( "line.separator"));
					bufferedWriter.close();
				}

				// Do not Write Annotation Found Overlaps to element Named File
				else{
					if( !histoneNumberCellLineNumber2ZeroorOneMap.containsKey( elementNumberCellLineNumber)){
						histoneNumberCellLineNumber2ZeroorOneMap.put( elementNumberCellLineNumber, Commons.BYTE_1);
					}
				}

			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingHistoneIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					node.getLeft(), interval, chromName, histoneNumberCellLineNumber2ZeroorOneMap, overlapDefinition,
					histoneNumber2HistoneNameMap, cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingHistoneIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					node.getRight(), interval, chromName, histoneNumberCellLineNumber2ZeroorOneMap, overlapDefinition,
					histoneNumber2HistoneNameMap, cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);

		}

	}

	// @todo for Annotation with Numbers ends

	// Search2 For finding the number of each histoneNameandCellLineName: k for
	// the given search input size: n
	// For each search input line, each histoneNameandCellLineName will have
	// value 1 or 0
	// These 1 or 0's will be accumulated in histoneNameandCellLineName2KMap
	public void findAllOverlappingHistoneIntervals( String outputFolder, IntervalTreeNode node, Interval interval,
			ChromosomeName chromName, Map<String, BufferedWriter> bufferedWriterHashMap, List<String> histoneNameList,
			Map<String, Integer> histoneNameandCellLineName2ZeroorOneMap, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		TforHistoneIntervalTreeNode castedNode = null;

		if( node instanceof TforHistoneIntervalTreeNode){
			castedNode = ( TforHistoneIntervalTreeNode)node;
		}

		String histoneNameandCellLine = castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();

		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),
				overlapDefinition) && histoneNameList.contains( castedNode.getTfbsorHistoneName())){
			try{

				bufferedWriter = bufferedWriterHashMap.get( histoneNameandCellLine);

				if( bufferedWriter == null){
					fileWriter = FileOperations.createFileWriter( outputFolder + Commons.HISTONE_ANNOTATION_DIRECTORY + "_" + histoneNameandCellLine + ".txt");
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriterHashMap.put( histoneNameandCellLine, bufferedWriter);
					bufferedWriter.write( "Searched for chr" + "\t" + "interval low" + "\t" + "interval high" + "\t" + "histone node chrom name" + "\t" + "node Low" + "\t" + "node high" + "\t" + "node Histone Name" + "\t" + "node CellLineName" + "\t" + "node FileName" + System.getProperty( "line.separator"));

				}

				if( histoneNameandCellLineName2ZeroorOneMap.get( histoneNameandCellLine) == null){
					histoneNameandCellLineName2ZeroorOneMap.put( histoneNameandCellLine, 1);
				}

				bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName() + "\t" + String.valueOf( castedNode.getCellLineName()) + "\t" + castedNode.getFileName() + System.getProperty( "line.separator"));
				bufferedWriter.close();

			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingHistoneIntervals( outputFolder, node.getLeft(), interval, chromName,
					bufferedWriterHashMap, histoneNameList, histoneNameandCellLineName2ZeroorOneMap, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingHistoneIntervals( outputFolder, node.getRight(), interval, chromName,
					bufferedWriterHashMap, histoneNameList, histoneNameandCellLineName2ZeroorOneMap, overlapDefinition);

		}

	}

	// @todo
	// with Numbers
	// Empirical P Value Calculation
	// with IO
	// with OverlapNodeList
	public void findAllOverlappingTfbsIntervalsWithIOWithNumbers( String outputFolder, int permutationNumber,
			IntervalTreeNode node, InputLineMinimal interval, ChromosomeName chromName,
			TLongObjectMap<BufferedWriter> bufferedWriterHashMap,
			TLongIntMap permutationNumberTfNumberCellLineNumber2ZeroorOneMap,
			List<PermutationNumberTfNumberCellLineNumberOverlap> permutationNumberTfNumberCellLineNumberOverlapList,
			int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		long permutationNumberTfNumberCellLineNumber;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){
			try{

				if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
					castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
				}

				permutationNumberTfNumberCellLineNumber = generateMixedNumber(
						permutationNumber,
						castedNode.getTforHistoneNumber(),
						castedNode.getCellLineNumber(),
						( short)0,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

				permutationNumberTfNumberCellLineNumberOverlapList.add( new PermutationNumberTfNumberCellLineNumberOverlap(
						permutationNumberTfNumberCellLineNumber, castedNode.getLow(), castedNode.getHigh()));

				bufferedWriter = bufferedWriterHashMap.get( permutationNumberTfNumberCellLineNumber);

				if( bufferedWriter == null){

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_TFBS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + permutationNumberTfNumberCellLineNumber + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "tfbs" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "TfNumber" + "\t" + "CellLineNumber" + "\t" + "FileNumber" + System.getProperty( "line.separator"));
					bufferedWriter.flush();

					bufferedWriterHashMap.put( permutationNumberTfNumberCellLineNumber, bufferedWriter);
				}

				if( !( permutationNumberTfNumberCellLineNumber2ZeroorOneMap.containsKey( permutationNumberTfNumberCellLineNumber))){
					permutationNumberTfNumberCellLineNumber2ZeroorOneMap.put( permutationNumberTfNumberCellLineNumber,
							1);
				}

				bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "tfbs" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTforHistoneNumber() + "\t" + castedNode.getCellLineNumber() + "\t" + castedNode.getFileNumber() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervalsWithIOWithNumbers( outputFolder, permutationNumber, node.getLeft(),
					interval, chromName, bufferedWriterHashMap, permutationNumberTfNumberCellLineNumber2ZeroorOneMap,
					permutationNumberTfNumberCellLineNumberOverlapList, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervalsWithIOWithNumbers( outputFolder, permutationNumber, node.getRight(),
					interval, chromName, bufferedWriterHashMap, permutationNumberTfNumberCellLineNumber2ZeroorOneMap,
					permutationNumberTfNumberCellLineNumberOverlapList, overlapDefinition);

		}
	}

	// with Numbers
	// @todo

	// Empirical P Value Calculation
	// with IO
	// with OverlapNodeList
	public void findAllOverlappingTfbsIntervals( String outputFolder, int permutationNumber, IntervalTreeNode node,
			Interval interval, ChromosomeName chromName, Map<String, BufferedWriter> bufferedWriterHashMap,
			Map<String, Integer> permutationNumberTfbsNameCellLineName2ZeroorOneMap,
			List<PermutationNumberTfNameCellLineNameOverlap> permutationNumberTfNameCellLineNameOverlapList,
			int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		String permutationNumberTfNameCellLineName;

		TforHistoneIntervalTreeNode castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){
			try{

				if( node instanceof TforHistoneIntervalTreeNode){
					castedNode = ( TforHistoneIntervalTreeNode)node;
				}

				permutationNumberTfNameCellLineName = Commons.PERMUTATION + permutationNumber + "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();

				permutationNumberTfNameCellLineNameOverlapList.add( new PermutationNumberTfNameCellLineNameOverlap(
						permutationNumberTfNameCellLineName, castedNode.getLow(), castedNode.getHigh()));

				bufferedWriter = bufferedWriterHashMap.get( permutationNumberTfNameCellLineName);

				if( bufferedWriter == null){

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_TFBS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + permutationNumberTfNameCellLineName + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriterHashMap.put( permutationNumberTfNameCellLineName, bufferedWriter);
				}

				if( permutationNumberTfbsNameCellLineName2ZeroorOneMap.get( permutationNumberTfNameCellLineName) == null){
					permutationNumberTfbsNameCellLineName2ZeroorOneMap.put( permutationNumberTfNameCellLineName, 1);
				}

				bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "tfbs" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName() + "\t" + castedNode.getCellLineName() + "\t" + castedNode.getFileName() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervals( outputFolder, permutationNumber, node.getLeft(), interval, chromName,
					bufferedWriterHashMap, permutationNumberTfbsNameCellLineName2ZeroorOneMap,
					permutationNumberTfNameCellLineNameOverlapList, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervals( outputFolder, permutationNumber, node.getRight(), interval, chromName,
					bufferedWriterHashMap, permutationNumberTfbsNameCellLineName2ZeroorOneMap,
					permutationNumberTfNameCellLineNameOverlapList, overlapDefinition);

		}
	}

	
	// 6 July 2015
	//Enrichment
	//With IO
	//With Numbers
	//Without PermutationNumber
	//TF and Histone
	public void findAllOverlappingTForHistoneIntervalsWithIOWithNumbers( 
			String outputFolder, 
			int permutationNumber,
			IntervalTreeNode node, 
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TLongObjectMap<BufferedWriter> permutationNumberTForHistoneNumberCellLineNumberKEGGPathwayNumber2BufferedWriterMap,
			TIntByteMap tforHistoneNumberCellLineNumber2ZeroorOneMap, 
			int overlapDefinition,
			AnnotationType annotationType) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		int tforHistoneNumberCellLineNumber;
		long permutationNumberTforHistoneNumberCellLineNumber;
		
		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){
			try{

				if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
					castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
				}

				
				tforHistoneNumberCellLineNumber = generateElementNumberCellLineNumber(
						castedNode.getTforHistoneNumber(),
						castedNode.getCellLineNumber(),
						GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);
				
				permutationNumberTforHistoneNumberCellLineNumber = generateMixedNumber(
						permutationNumber,
						castedNode.getTforHistoneNumber(),
						castedNode.getCellLineNumber(),
						(short)0,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

				bufferedWriter = permutationNumberTForHistoneNumberCellLineNumberKEGGPathwayNumber2BufferedWriterMap.get(permutationNumberTforHistoneNumberCellLineNumber);

				if( bufferedWriter == null){

					if(annotationType.doTFAnnotation()){
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_TFBS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + permutationNumberTforHistoneNumberCellLineNumber + ".txt",true);
							
					}else if (annotationType.doHistoneAnnotation()){
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_HISTONE + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + permutationNumberTforHistoneNumberCellLineNumber + ".txt",true);
					}
					
					
					bufferedWriter = new BufferedWriter( fileWriter);
					
					if(annotationType.doTFAnnotation()){
						bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "tfbs" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "TfNumber" + "\t" + "CellLineNumber" + "\t" + "FileNumber" + System.getProperty( "line.separator"));
					}else if (annotationType.doHistoneAnnotation()){
						bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "histone" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "HistoneNumber" + "\t" + "CellLineNumber" + "\t" + "FileNumber" + System.getProperty( "line.separator"));						
					}
					
					bufferedWriter.flush();

					permutationNumberTForHistoneNumberCellLineNumberKEGGPathwayNumber2BufferedWriterMap.put(permutationNumberTforHistoneNumberCellLineNumber, bufferedWriter);
				}

				if( !( tforHistoneNumberCellLineNumber2ZeroorOneMap.containsKey(tforHistoneNumberCellLineNumber))){
					tforHistoneNumberCellLineNumber2ZeroorOneMap.put(tforHistoneNumberCellLineNumber,Commons.BYTE_1);
				}

				if(annotationType.doTFAnnotation()){
					bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "tfbs" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTforHistoneNumber() + "\t" + castedNode.getCellLineNumber() + "\t" + castedNode.getFileNumber() + System.getProperty( "line.separator"));
				}else if (annotationType.doHistoneAnnotation()){
					bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "histone" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTforHistoneNumber() + "\t" + castedNode.getCellLineNumber() + "\t" + castedNode.getFileNumber() + System.getProperty( "line.separator"));
				}
				
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			
			findAllOverlappingTForHistoneIntervalsWithIOWithNumbers(
					outputFolder, 
					permutationNumber, 
					node.getLeft(),
					interval, 
					chromName, 
					permutationNumberTForHistoneNumberCellLineNumberKEGGPathwayNumber2BufferedWriterMap, 
					tforHistoneNumberCellLineNumber2ZeroorOneMap,
					overlapDefinition,
					annotationType);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			
			findAllOverlappingTForHistoneIntervalsWithIOWithNumbers(
					outputFolder, 
					permutationNumber, 
					node.getRight(),
					interval, 
					chromName, 
					permutationNumberTForHistoneNumberCellLineNumberKEGGPathwayNumber2BufferedWriterMap, 
					tforHistoneNumberCellLineNumber2ZeroorOneMap,
					overlapDefinition,
					annotationType);

		}

	}
	
	
	// Enrichment
	// with IO
	// with Numbers
	// Empirical P Value Calculation
	public void findAllOverlappingTfbsIntervalsWithIOWithNumbers( String outputFolder, int permutationNumber,
			IntervalTreeNode node, InputLineMinimal interval, ChromosomeName chromName,
			TLongObjectMap<BufferedWriter> bufferedWriterHashMap,
			TLongIntMap permutationNumberTfNumberCellLineNumber2ZeroorOneMap, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		long permutationNumberTfNumberCellLineNumber;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){
			try{

				if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
					castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
				}

				permutationNumberTfNumberCellLineNumber = generateMixedNumber(
						permutationNumber,
						castedNode.getTforHistoneNumber(),
						castedNode.getCellLineNumber(),
						( short)0,
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

				bufferedWriter = bufferedWriterHashMap.get( permutationNumberTfNumberCellLineNumber);

				if( bufferedWriter == null){

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_TFBS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + permutationNumberTfNumberCellLineNumber + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "tfbs" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "TfNumber" + "\t" + "CellLineNumber" + "\t" + "FileNumber" + System.getProperty( "line.separator"));
					bufferedWriter.flush();

					bufferedWriterHashMap.put( permutationNumberTfNumberCellLineNumber, bufferedWriter);
				}

				if( !( permutationNumberTfNumberCellLineNumber2ZeroorOneMap.containsKey( permutationNumberTfNumberCellLineNumber))){
					permutationNumberTfNumberCellLineNumber2ZeroorOneMap.put( permutationNumberTfNumberCellLineNumber,
							1);
				}

				bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "tfbs" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTforHistoneNumber() + "\t" + castedNode.getCellLineNumber() + "\t" + castedNode.getFileNumber() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervalsWithIOWithNumbers( outputFolder, permutationNumber, node.getLeft(),
					interval, chromName, bufferedWriterHashMap, permutationNumberTfNumberCellLineNumber2ZeroorOneMap,
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervalsWithIOWithNumbers( outputFolder, permutationNumber, node.getRight(),
					interval, chromName, bufferedWriterHashMap, permutationNumberTfNumberCellLineNumber2ZeroorOneMap,
					overlapDefinition);

		}

	}

	// with Numbers
	// @todo

	// Empirical P Value Calculation
	// with IO
	public void findAllOverlappingTfbsIntervals( String outputFolder, int permutationNumber, IntervalTreeNode node,
			Interval interval, ChromosomeName chromName, Map<String, BufferedWriter> bufferedWriterHashMap,
			Map<String, Integer> permutationNumberTfbsNameCellLineName2ZeroorOneMap, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		String permutationNumberTfbsNameCellLineName;

		TforHistoneIntervalTreeNode castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){
			try{

				if( node instanceof TforHistoneIntervalTreeNode){
					castedNode = ( TforHistoneIntervalTreeNode)node;
				}

				permutationNumberTfbsNameCellLineName = Commons.PERMUTATION + permutationNumber + "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();

				bufferedWriter = bufferedWriterHashMap.get( permutationNumberTfbsNameCellLineName);

				if( bufferedWriter == null){

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_TFBS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + permutationNumberTfbsNameCellLineName + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriterHashMap.put( permutationNumberTfbsNameCellLineName, bufferedWriter);
				}

				if( permutationNumberTfbsNameCellLineName2ZeroorOneMap.get( permutationNumberTfbsNameCellLineName) == null){
					permutationNumberTfbsNameCellLineName2ZeroorOneMap.put( permutationNumberTfbsNameCellLineName, 1);
				}

				bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "tfbs" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName() + "\t" + castedNode.getCellLineName() + "\t" + castedNode.getFileName() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervals( outputFolder, permutationNumber, node.getLeft(), interval, chromName,
					bufferedWriterHashMap, permutationNumberTfbsNameCellLineName2ZeroorOneMap, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervals( outputFolder, permutationNumber, node.getRight(), interval, chromName,
					bufferedWriterHashMap, permutationNumberTfbsNameCellLineName2ZeroorOneMap, overlapDefinition);

		}

	}
	
	//1 NOVEMBER 2015 starts
	//Without IO
	//WithNumbers
	//WithOverlappingNodeList
	//AssociationMeasureType NUMBER_OF_OVERLAPPING_BASES
	public void findAllOverlappingTFIntervalsWithoutIOWithNumbers(
			int permutationNumber, 
			IntervalTreeNode node,
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TLongObjectMap<List<IntervalTreeNode>> permutationNumberTFNumberCellLineNumber2OverlappingNodeListMap, 
			int overlapDefinition){
		
		long permutationNumberTFNumberCellLineNumber;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;
		
		List<IntervalTreeNode> overlappingNodeList = null;

		if( overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
				//castedNode = (TforHistoneIntervalTreeNodeWithNumbers)node;
				//castedNode must be newly created and there must be no color assigned at first.
				castedNode = new TforHistoneIntervalTreeNodeWithNumbers(
						node.getChromName(),
						node.getLow(),
						node.getHigh(),
						((TforHistoneIntervalTreeNodeWithNumbers) node).getTforHistoneNumber(), 
						((TforHistoneIntervalTreeNodeWithNumbers) node).getCellLineNumber(), 
						((TforHistoneIntervalTreeNodeWithNumbers) node).getFileNumber(),
						NodeType.ORIGINAL);
				
			}

			permutationNumberTFNumberCellLineNumber = generateMixedNumber(
					permutationNumber,
					castedNode.getTforHistoneNumber(),
					castedNode.getCellLineNumber(),
					(short)0,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);
			
			overlappingNodeList = permutationNumberTFNumberCellLineNumber2OverlappingNodeListMap.get(permutationNumberTFNumberCellLineNumber);
			
			//Pay attention: you have to add castedNode to the list
			//Further on you will need tforHistoneNumber in constructAnIntervalTreeWithNonOverlappingNodes method
			if (overlappingNodeList == null){
				overlappingNodeList = new ArrayList<IntervalTreeNode>();
				overlappingNodeList.add(castedNode);
				permutationNumberTFNumberCellLineNumber2OverlappingNodeListMap.put(permutationNumberTFNumberCellLineNumber, overlappingNodeList);
			}else{
				overlappingNodeList.add(castedNode);
			}

		}//End of IF there is an overlap

		if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow() <= node.getLeft().getMax())){
			
			findAllOverlappingTFIntervalsWithoutIOWithNumbers( 
					permutationNumber, 
					node.getLeft(), 
					interval,
					chromName, 
					permutationNumberTFNumberCellLineNumber2OverlappingNodeListMap, 
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			
			findAllOverlappingTFIntervalsWithoutIOWithNumbers( 
					permutationNumber, 
					node.getRight(), 
					interval,
					chromName, 
					permutationNumberTFNumberCellLineNumber2OverlappingNodeListMap, 
					overlapDefinition);
		}
	
	}
	//1 NOVEMBER 2015 ends
	

	// @todo
	// Empirical P Value Calculation
	// without IO
	// without overlappedNodeList
	// with numbers
	public void findAllOverlappingTfbsIntervalsWithoutIOWithNumbers(
			int permutationNumber, 
			IntervalTreeNode node,
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TLongIntMap permutationNumberTfbsNameCellLineName2ZeroorOneMap, 
			int overlapDefinition) {

		long permutationNumberTfNumberCellLineNumber;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
				castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
			}

			permutationNumberTfNumberCellLineNumber = generateMixedNumber(
					permutationNumber,
					castedNode.getTforHistoneNumber(),
					castedNode.getCellLineNumber(),
					( short)0,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

			if( !( permutationNumberTfbsNameCellLineName2ZeroorOneMap.containsKey( permutationNumberTfNumberCellLineNumber))){
				permutationNumberTfbsNameCellLineName2ZeroorOneMap.put( permutationNumberTfNumberCellLineNumber, 1);
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervalsWithoutIOWithNumbers( permutationNumber, node.getLeft(), interval,
					chromName, permutationNumberTfbsNameCellLineName2ZeroorOneMap, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervalsWithoutIOWithNumbers( permutationNumber, node.getRight(), interval,
					chromName, permutationNumberTfbsNameCellLineName2ZeroorOneMap, overlapDefinition);
		}

	}

	// @todo

	// Empirical P Value Calculation
	// without IO
	// without overlappedNodeList
	public void findAllOverlappingTfbsIntervals( int permutationNumber, IntervalTreeNode node, Interval interval,
			ChromosomeName chromName, Map<String, Integer> permutationNumberTfbsNameCellLineName2ZeroorOneMap,
			int overlapDefinition) {

		String permutationNumberTfbsNameCellLineName;

		TforHistoneIntervalTreeNode castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof TforHistoneIntervalTreeNode){
				castedNode = ( TforHistoneIntervalTreeNode)node;
			}

			permutationNumberTfbsNameCellLineName = Commons.PERMUTATION + permutationNumber + "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();

			if( permutationNumberTfbsNameCellLineName2ZeroorOneMap.get( permutationNumberTfbsNameCellLineName) == null){
				permutationNumberTfbsNameCellLineName2ZeroorOneMap.put( permutationNumberTfbsNameCellLineName, 1);
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervals( permutationNumber, node.getLeft(), interval, chromName,
					permutationNumberTfbsNameCellLineName2ZeroorOneMap, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervals( permutationNumber, node.getRight(), interval, chromName,
					permutationNumberTfbsNameCellLineName2ZeroorOneMap, overlapDefinition);

		}

	}
	
	//9 July 2015
	// With Numbers
	// Without IO
	// WITH permutationNumberTfNameCellLineNameOverlapList
	// WITHOUT keeping number of overlaps coming from each permutation
	public void findAllOverlappingTfbsIntervalsWithoutIOWithNumbers(
			IntervalTreeNode node,
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntByteMap tfNumberCellLineNumber2PermutationZeroorOneMap,
			List<TFNumberCellLineNumberOverlap> tfNumberCellLineNumberOverlapList,
			int overlapDefinition) {

		int tfNumberCellLineNumber;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
				castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
			}

			tfNumberCellLineNumber = generateElementNumberCellLineNumber(
					castedNode.getTforHistoneNumber(),
					castedNode.getCellLineNumber(),
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);

			tfNumberCellLineNumberOverlapList.add(new TFNumberCellLineNumberOverlap(tfNumberCellLineNumber, castedNode.getLow(), castedNode.getHigh()));

			if( !( tfNumberCellLineNumber2PermutationZeroorOneMap.containsKey(tfNumberCellLineNumber))){
				tfNumberCellLineNumber2PermutationZeroorOneMap.put( tfNumberCellLineNumber, Commons.BYTE_1);
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervalsWithoutIOWithNumbers(
					node.getLeft(), 
					interval,
					chromName, 
					tfNumberCellLineNumber2PermutationZeroorOneMap,
					tfNumberCellLineNumberOverlapList, 
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervalsWithoutIOWithNumbers(
					node.getRight(), 
					interval,
					chromName, 
					tfNumberCellLineNumber2PermutationZeroorOneMap,
					tfNumberCellLineNumberOverlapList, 
					overlapDefinition);

		}

	}
	//9 July 2015

	// @todo
	// with Numbers starts
	// Empirical P Value Calculation
	// without IO
	// with permutationNumberTfNameCellLineNameOverlapList
	public void findAllOverlappingTfbsIntervalsWithoutIOWithNumbers( 
			int permutationNumber, 
			IntervalTreeNode node,
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TLongIntMap permutationNumberTfNumberCellLineNumber2ZeroorOneMap,
			List<PermutationNumberTfNumberCellLineNumberOverlap> permutationNumberTfNumberCellLineNumberOverlapList,
			int overlapDefinition) {

		long permutationNumberTfNumberCellLineNumber;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
				castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
			}

			permutationNumberTfNumberCellLineNumber = generateMixedNumber(
					permutationNumber,
					castedNode.getTforHistoneNumber(),
					castedNode.getCellLineNumber(),
					(short)0,
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER);

			permutationNumberTfNumberCellLineNumberOverlapList.add( new PermutationNumberTfNumberCellLineNumberOverlap(
					permutationNumberTfNumberCellLineNumber, castedNode.getLow(), castedNode.getHigh()));

			if( !( permutationNumberTfNumberCellLineNumber2ZeroorOneMap.containsKey( permutationNumberTfNumberCellLineNumber))){
				permutationNumberTfNumberCellLineNumber2ZeroorOneMap.put( permutationNumberTfNumberCellLineNumber, 1);
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervalsWithoutIOWithNumbers( permutationNumber, node.getLeft(), interval,
					chromName, permutationNumberTfNumberCellLineNumber2ZeroorOneMap,
					permutationNumberTfNumberCellLineNumberOverlapList, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervalsWithoutIOWithNumbers( permutationNumber, node.getRight(), interval,
					chromName, permutationNumberTfNumberCellLineNumber2ZeroorOneMap,
					permutationNumberTfNumberCellLineNumberOverlapList, overlapDefinition);

		}

	}

	// with Numbers ends
	// @todo

	// Empirical P Value Calculation
	// without IO
	// with permutationNumberTfNameCellLineNameOverlapList
	public void findAllOverlappingTfbsIntervals( int permutationNumber, IntervalTreeNode node, Interval interval,
			ChromosomeName chromName, Map<String, Integer> permutationNumberTfbsNameCellLineName2ZeroorOneMap,
			List<PermutationNumberTfNameCellLineNameOverlap> permutationNumberTfNameCellLineNameOverlapList,
			int overlapDefinition) {

		String permutationNumberTfNameCellLineName;

		TforHistoneIntervalTreeNode castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof TforHistoneIntervalTreeNode){
				castedNode = ( TforHistoneIntervalTreeNode)node;
			}

			permutationNumberTfNameCellLineName = Commons.PERMUTATION + permutationNumber + "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();

			permutationNumberTfNameCellLineNameOverlapList.add( new PermutationNumberTfNameCellLineNameOverlap(
					permutationNumberTfNameCellLineName, castedNode.getLow(), castedNode.getHigh()));

			if( permutationNumberTfbsNameCellLineName2ZeroorOneMap.get( permutationNumberTfNameCellLineName) == null){
				permutationNumberTfbsNameCellLineName2ZeroorOneMap.put( permutationNumberTfNameCellLineName, 1);
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervals( permutationNumber, node.getLeft(), interval, chromName,
					permutationNumberTfbsNameCellLineName2ZeroorOneMap, permutationNumberTfNameCellLineNameOverlapList,
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervals( permutationNumber, node.getRight(), interval, chromName,
					permutationNumberTfbsNameCellLineName2ZeroorOneMap, permutationNumberTfNameCellLineNameOverlapList,
					overlapDefinition);

		}

	}

	// Search2 For finding the number of each tfbs:k for the given search input
	// size: n
	// For each search input line, each tfbs will have value 1 or 0
	// These 1 or 0's will be accumulated in tfbs2KMap
	public void findAllOverlappingTfbsIntervals( IntervalTreeNode node, Interval interval, ChromosomeName chromName,
			Map<String, BufferedWriter> bufferedWriterHashMap, List<String> tfbsNameList,
			Map<String, Integer> tfbsNameandCellLineName2ZeroorOneMap) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		TforHistoneIntervalTreeNode castedNode = null;

		if( node instanceof TforHistoneIntervalTreeNode){
			castedNode = ( TforHistoneIntervalTreeNode)node;
		}

		String tfbsNameandCellLineName = castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();

		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh()) && tfbsNameList.contains( castedNode.getTfbsorHistoneName())){
			try{

				bufferedWriter = bufferedWriterHashMap.get( tfbsNameandCellLineName);

				if( bufferedWriter == null){
					fileWriter = FileOperations.createFileWriter( Commons.TF_ANNOTATION_DIRECTORY + tfbsNameandCellLineName + ".txt");
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriterHashMap.put( tfbsNameandCellLineName, bufferedWriter);
				}

				if( tfbsNameandCellLineName2ZeroorOneMap.get( tfbsNameandCellLineName) == null){
					tfbsNameandCellLineName2ZeroorOneMap.put( tfbsNameandCellLineName, 1);
				}

				bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "tfbs" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName() + "\t" + castedNode.getCellLineName() + "\t" + castedNode.getFileName() + System.getProperty( "line.separator"));
				bufferedWriter.close();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervals( node.getLeft(), interval, chromName, bufferedWriterHashMap, tfbsNameList,
					tfbsNameandCellLineName2ZeroorOneMap);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervals( node.getRight(), interval, chromName, bufferedWriterHashMap, tfbsNameList,
					tfbsNameandCellLineName2ZeroorOneMap);

		}
	}
	
	
	//27 July 2015 starts
	public void findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers( 
			IntervalTreeNode node, 
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntByteMap elementTypeNumberElementNumber2ZeroorOneMap, 
			int overlapDefinition) {

		int elementTypeNumberElementNumber;
		UserDefinedLibraryIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof UserDefinedLibraryIntervalTreeNodeWithNumbers){
				castedNode = ( UserDefinedLibraryIntervalTreeNodeWithNumbers)node;
			}

			elementTypeNumberElementNumber = generateElementTypeNumberElementNumber(
					castedNode.getElementTypeNumber(),
					castedNode.getElementNumber(),
					GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);

			if( !( elementTypeNumberElementNumber2ZeroorOneMap.containsKey(elementTypeNumberElementNumber))){
				elementTypeNumberElementNumber2ZeroorOneMap.put(elementTypeNumberElementNumber, Commons.BYTE_1);
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers( 
					node.getLeft(),
					interval, 
					chromName, 
					elementTypeNumberElementNumber2ZeroorOneMap,
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers(
					node.getRight(),
					interval, 
					chromName, 
					elementTypeNumberElementNumber2ZeroorOneMap,
					overlapDefinition);

		}

	}
	//27 July 2015 ends
	
	
	//10 NOV 2015 starts
	//Enrichment 
	//Without IO
	//With Numbers
	//With OverlappingNodeList
	//For AssociationMeasureType NUMBER_OF_OVERLAPPING_BASES 
	public void findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers(
			int permutationNumber,
			IntervalTreeNode node, 
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TLongObjectMap<List<IntervalTreeNode>> permutationNumberElementTypeNumberElementNumber2OverlappingNodeListMap, 
			int overlapDefinition) {
	
		long permutationNumberElementTypeNumberElementNumber;
		
		UserDefinedLibraryIntervalTreeNodeWithNumbers castedNode = null;
		
		List<IntervalTreeNode> overlappingNodeList = null;

		if( overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof UserDefinedLibraryIntervalTreeNodeWithNumbers){
				//castedNode = ( UserDefinedLibraryIntervalTreeNodeWithNumbers)node;
				//castedNode must be newly created and there must be no color assigned at first.
				castedNode = new UserDefinedLibraryIntervalTreeNodeWithNumbers(
						node.getChromName(),
						node.getLow(),
						node.getHigh(),
						((UserDefinedLibraryIntervalTreeNodeWithNumbers) node).getElementTypeNumber(),
						((UserDefinedLibraryIntervalTreeNodeWithNumbers) node).getElementNumber(),
						((UserDefinedLibraryIntervalTreeNodeWithNumbers) node).getFileNumber(),
						NodeType.ORIGINAL);
				
			}//End of IF

			permutationNumberElementTypeNumberElementNumber = generateMixedNumber(
					permutationNumber,
					castedNode.getElementTypeNumber(),
					castedNode.getElementNumber(),
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);
			
			overlappingNodeList = permutationNumberElementTypeNumberElementNumber2OverlappingNodeListMap.get(permutationNumberElementTypeNumberElementNumber);
			
			//Pay attention: you have to add castedNode to the list
			//Further on you will need tforHistoneNumber in constructAnIntervalTreeWithNonOverlappingNodes method
			if (overlappingNodeList == null){
				overlappingNodeList = new ArrayList<IntervalTreeNode>();
				overlappingNodeList.add(castedNode);
				permutationNumberElementTypeNumberElementNumber2OverlappingNodeListMap.put(permutationNumberElementTypeNumberElementNumber, overlappingNodeList);
			}else{
				overlappingNodeList.add(castedNode);
			}

			
		}//End of IF overlaps

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			
			findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers(
					permutationNumber, 
					node.getLeft(),
					interval, 
					chromName, 
					permutationNumberElementTypeNumberElementNumber2OverlappingNodeListMap,
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			
			findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers( 
					permutationNumber, 
					node.getRight(),
					interval, 
					chromName, 
					permutationNumberElementTypeNumberElementNumber2OverlappingNodeListMap,
					overlapDefinition);

		}

	}
	//10 NOV 2015 ends
	

	// 4 NOV 2014
	// Enrichment
	// Without IO
	// With Numbers
	public void findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers(
			int permutationNumber,
			IntervalTreeNode node, 
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TLongIntMap permutationNumberElementTypeNumberElementNumber2ZeroorOneMap, 
			int overlapDefinition) {

		long permutationNumberElementTypeNumberElementNumber;
		UserDefinedLibraryIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof UserDefinedLibraryIntervalTreeNodeWithNumbers){
				castedNode = ( UserDefinedLibraryIntervalTreeNodeWithNumbers)node;
			}

			permutationNumberElementTypeNumberElementNumber = generateMixedNumber(
					permutationNumber,
					castedNode.getElementTypeNumber(),
					castedNode.getElementNumber(),
					GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);

			if( !( permutationNumberElementTypeNumberElementNumber2ZeroorOneMap.containsKey( permutationNumberElementTypeNumberElementNumber))){
				permutationNumberElementTypeNumberElementNumber2ZeroorOneMap.put(
						permutationNumberElementTypeNumberElementNumber, 1);
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers(
					permutationNumber, 
					node.getLeft(),
					interval, 
					chromName, 
					permutationNumberElementTypeNumberElementNumber2ZeroorOneMap,
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUserDefinedLibraryIntervalsWithoutIOWithNumbers(
					permutationNumber, 
					node.getRight(),
					interval, 
					chromName, 
					permutationNumberElementTypeNumberElementNumber2ZeroorOneMap,
					overlapDefinition);

		}

	}

	// 5 NOV 2014
	// Enrichment
	// With IO
	// With Numbers
	public void findAllOverlappingUserDefinedLibraryIntervalsWithIOWithNumbers( String outputFolder,
			int permutationNumber, IntervalTreeNode node, InputLineMinimal interval, ChromosomeName chromName,
			TLongObjectMap<BufferedWriter> permutationNumberElementTypeNumberElementNumber2BufferedWriterHashMap,
			TLongIntMap permutationNumberElementTypeNumberElementNumber2ZeroorOneMap, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		long permutationNumberElementTypeNumberElementNumber;

		UserDefinedLibraryIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){
			try{

				if( node instanceof UserDefinedLibraryIntervalTreeNodeWithNumbers){
					castedNode = ( UserDefinedLibraryIntervalTreeNodeWithNumbers)node;
				}

				permutationNumberElementTypeNumberElementNumber = generateMixedNumber(
						permutationNumber,
						castedNode.getElementTypeNumber(),
						castedNode.getElementNumber(),
						GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER);

				bufferedWriter = permutationNumberElementTypeNumberElementNumber2BufferedWriterHashMap.get( permutationNumberElementTypeNumberElementNumber);

				if( bufferedWriter == null){

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_USERDEFINEDLIBRARY + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + castedNode.getElementTypeNumber() + System.getProperty( "file.separator") + permutationNumberElementTypeNumberElementNumber + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "UserDefinedLibrary" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "ElementTypeNumber" + "\t" + "ElementNumber" + "\t" + "FileNumber" + System.getProperty( "line.separator"));
					bufferedWriter.flush();

					permutationNumberElementTypeNumberElementNumber2BufferedWriterHashMap.put(
							permutationNumberElementTypeNumberElementNumber, bufferedWriter);
				}

				if( !( permutationNumberElementTypeNumberElementNumber2ZeroorOneMap.containsKey( permutationNumberElementTypeNumberElementNumber))){
					permutationNumberElementTypeNumberElementNumber2ZeroorOneMap.put(
							permutationNumberElementTypeNumberElementNumber, 1);
				}

				bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "UserDefinedLibrary" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getElementTypeNumber() + "\t" + castedNode.getElementNumber() + "\t" + castedNode.getFileNumber() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUserDefinedLibraryIntervalsWithIOWithNumbers( outputFolder, permutationNumber,
					node.getLeft(), interval, chromName,
					permutationNumberElementTypeNumberElementNumber2BufferedWriterHashMap,
					permutationNumberElementTypeNumberElementNumber2ZeroorOneMap, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUserDefinedLibraryIntervalsWithIOWithNumbers( outputFolder, permutationNumber,
					node.getRight(), interval, chromName,
					permutationNumberElementTypeNumberElementNumber2BufferedWriterHashMap,
					permutationNumberElementTypeNumberElementNumber2ZeroorOneMap, overlapDefinition);

		}

	}

	// Annotation
	// With Numbers
	public void findAllOverlappingUserDefinedLibraryIntervalsWithNumbers( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			IntervalTreeNode node, Interval interval, ChromosomeName chromName, TIntByteMap elementNumber2ZeroorOneMap,
			int overlapDefinition, String elementType, TIntObjectMap<String> elementNumber2ElementNameMap,
			TIntObjectMap<String> fileNumber2FileNameMap) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		int elementNumber;

		UserDefinedLibraryIntervalTreeNodeWithNumbers castedNode = null;

		if( node instanceof UserDefinedLibraryIntervalTreeNodeWithNumbers){
			castedNode = ( UserDefinedLibraryIntervalTreeNodeWithNumbers)node;
		}

		elementNumber = castedNode.getElementNumber();

		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),
				overlapDefinition)){
			try{

				/*******************************************************************/
				// Write Annotation Found Overlaps to element Named File
				if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){
					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.USERDEFINEDLIBRARY_ANNOTATION_DIRECTORY + elementType + System.getProperty( "file.separator") + elementNumber2ElementNameMap.get( elementNumber) + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);

					if( !elementNumber2ZeroorOneMap.containsKey( elementNumber)){
						elementNumber2ZeroorOneMap.put( elementNumber, Commons.BYTE_1);
						bufferedWriter.write( "#Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "UserDefinedLibraryNode ChromName" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node Element Name" + "\t" + "node FileName" + System.getProperty( "line.separator"));
					}

					bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + elementNumber2ElementNameMap.get( elementNumber) + "\t" + fileNumber2FileNameMap.get( castedNode.getFileNumber()) + System.getProperty( "line.separator"));
					bufferedWriter.close();
				}
				/*******************************************************************/

				/*******************************************************************/
				// Do not Write Annotation Found Overlaps to element Named File
				else{
					if( !elementNumber2ZeroorOneMap.containsKey( elementNumber)){
						elementNumber2ZeroorOneMap.put( elementNumber, Commons.BYTE_1);
					}
				}
				/*******************************************************************/

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUserDefinedLibraryIntervalsWithNumbers( outputFolder,
					writeElementBasedAnnotationFoundOverlapsMode, node.getLeft(), interval, chromName,
					elementNumber2ZeroorOneMap, overlapDefinition, elementType, elementNumber2ElementNameMap,
					fileNumber2FileNameMap);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUserDefinedLibraryIntervalsWithNumbers( outputFolder,
					writeElementBasedAnnotationFoundOverlapsMode, node.getRight(), interval, chromName,
					elementNumber2ZeroorOneMap, overlapDefinition, elementType, elementNumber2ElementNameMap,
					fileNumber2FileNameMap);
		}

	}

	//Modified 20 July 2015
	// Annotation
	// TF with Numbers starts
	public void findAllOverlappingTfbsIntervalsWithNumbers( 
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			RegulatorySequenceAnalysisType regulatorySequenceAnalysisUsingRSAT, 
			IntervalTreeNode node,
			Interval interval, 
			ChromosomeName chromName, 
			TIntByteMap tfNumberCellLineNumber2ZeroorOneMap,
			int overlapDefinition, 
			TIntObjectMap<String> tfNumber2TfNameMap,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap, 
			TIntObjectMap<String> fileNumber2FileNameMap) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
			castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
		}

		// KEGG Pathway number included
		// WHY?
		// int elementNumberCellLineNumber =
		// IntervalTree.generateElementNumberCellLineNumberKeggPathwayNumber(castedNode.getTforHistoneNumber(),
		// castedNode.getCellLineNumber(), (short) 0,
		// GeneratedMixedNumberDescriptionOrderLength.INT_4DIGITS_ELEMENTNUMBER_3DIGITS_CELLLINENUMBER_3DIGITS_KEGGPATHWAYNUMBER);
		int elementNumberCellLineNumber = IntervalTree.generateElementNumberCellLineNumber(
				castedNode.getTforHistoneNumber(), 
				castedNode.getCellLineNumber(),
				GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);

		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),
				overlapDefinition)){
			try{

				// Write Annotation Found Overlaps to element Named File
				// or RSAT is wanted
				if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps() || regulatorySequenceAnalysisUsingRSAT.isDoRegulatorySequenceAnalysisUsingRSAT()){

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.TF_ANNOTATION_DIRECTORY + tfNumber2TfNameMap.get( castedNode.getTforHistoneNumber()) + "_" + cellLineNumber2CellLineNameMap.get( castedNode.getCellLineNumber()) + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);

					if( !tfNumberCellLineNumber2ZeroorOneMap.containsKey( elementNumberCellLineNumber)){
						tfNumberCellLineNumber2ZeroorOneMap.put( elementNumberCellLineNumber, Commons.BYTE_1);
						bufferedWriter.write( "#Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "tfbs node Chrom Name" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node Tfbs Name" + "\t" + "node CellLineName" + "\t" + "node FileName" + System.getProperty( "line.separator"));
					}

					bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + tfNumber2TfNameMap.get( castedNode.getTforHistoneNumber()) + "\t" + cellLineNumber2CellLineNameMap.get( castedNode.getCellLineNumber()) + "\t" + fileNumber2FileNameMap.get( castedNode.getFileNumber()) + System.getProperty( "line.separator"));
					bufferedWriter.close();
				}

				// Do not Write Annotation Found Overlaps to element Named File
				else{
					if( !tfNumberCellLineNumber2ZeroorOneMap.containsKey( elementNumberCellLineNumber)){
						tfNumberCellLineNumber2ZeroorOneMap.put( elementNumberCellLineNumber, Commons.BYTE_1);
					}
				}

			}catch( IOException e){
				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					regulatorySequenceAnalysisUsingRSAT, node.getLeft(), interval, chromName,
					tfNumberCellLineNumber2ZeroorOneMap, overlapDefinition, tfNumber2TfNameMap,
					cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					regulatorySequenceAnalysisUsingRSAT, node.getRight(), interval, chromName,
					tfNumberCellLineNumber2ZeroorOneMap, overlapDefinition, tfNumber2TfNameMap,
					cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
		}
	}

	// @todo Annotation with Numbers ends

	// Modified 20 July 2015
	// Modified on 13 July 2015 
	// Annotation with Numbers with OverlapList starts
	public void findAllOverlappingTfbsIntervalsWithNumbers( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			IntervalTreeNode node, 
			Interval interval, 
			ChromosomeName chromName,
			TIntByteMap tfNumberCellLineNumber2ZeroorOneMap,
			List<TfCellLineOverlapWithNumbers> tfandCellLineOverlapList, 
			int overlapDefinition,
			TIntObjectMap<String> tfNumber2TfNameMap, 
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String> fileNumber2FileNameMap) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
			castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
		}

		
		int elementNumberCellLineNumber = IntervalTree.generateElementNumberCellLineNumber(
				castedNode.getTforHistoneNumber(),
				castedNode.getCellLineNumber(),
				GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);

		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
			try{

				/*******************************************************************/
				// Write Annotation Found Overlaps to element Named File
				if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.TF_ANNOTATION_DIRECTORY + tfNumber2TfNameMap.get( castedNode.getTforHistoneNumber()) + "_" + cellLineNumber2CellLineNameMap.get( castedNode.getCellLineNumber()) + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);

					if( !tfNumberCellLineNumber2ZeroorOneMap.containsKey( elementNumberCellLineNumber)){
						tfNumberCellLineNumber2ZeroorOneMap.put( elementNumberCellLineNumber, Commons.BYTE_1);
						bufferedWriter.write( "#Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "tfbs node Chrom Name" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node Tfbs Name" + "\t" + "node CellLineName" + "\t" + "node FileName" + System.getProperty( "line.separator"));
					}

					bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + tfNumber2TfNameMap.get( castedNode.getTforHistoneNumber()) + "\t" + cellLineNumber2CellLineNameMap.get( castedNode.getCellLineNumber()) + "\t" + fileNumber2FileNameMap.get( castedNode.getFileNumber()) + System.getProperty( "line.separator"));
					bufferedWriter.close();
				}
				/*******************************************************************/

				/*******************************************************************/
				// Do not Write Annotation Found Overlaps to element Named File
				else{
					if( !tfNumberCellLineNumber2ZeroorOneMap.containsKey(elementNumberCellLineNumber)){
						tfNumberCellLineNumber2ZeroorOneMap.put(elementNumberCellLineNumber, Commons.BYTE_1);
					}
				}
				/*******************************************************************/

				tfandCellLineOverlapList.add( 
						new TfCellLineOverlapWithNumbers( 
								elementNumberCellLineNumber,
								castedNode.getLow(), 
								castedNode.getHigh()));

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					node.getLeft(), interval, chromName, tfNumberCellLineNumber2ZeroorOneMap, tfandCellLineOverlapList,
					overlapDefinition, tfNumber2TfNameMap, cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					node.getRight(), interval, chromName, tfNumberCellLineNumber2ZeroorOneMap,
					tfandCellLineOverlapList, overlapDefinition, tfNumber2TfNameMap, cellLineNumber2CellLineNameMap,
					fileNumber2FileNameMap);
		}
	}
	// @todo Annotation with Numbers with OverlapList ends

	// New Functionality added
	// Search2 For finding the number of each tfbs:k for the given search input
	// size: n
	// For each search input line, each tfbs will have value 1 or 0
	// These 1 or 0's will be accumulated in tfbs2KMap
	public void findAllOverlappingTfbsIntervals( String outputFolder, IntervalTreeNode node, Interval interval,
			ChromosomeName chromName, Map<String, BufferedWriter> bufferedWriterHashMap, List<String> tfbsNameList,
			Map<String, Integer> tfbsNameandCellLineName2ZeroorOneMap,
			List<TfNameandCellLineNameOverlap> tfandCellLineOverlapList, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		TforHistoneIntervalTreeNode castedNode = null;

		if( node instanceof TforHistoneIntervalTreeNode){
			castedNode = ( TforHistoneIntervalTreeNode)node;
		}

		String tfbsNameandCellLineName = castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();

		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),
				overlapDefinition) && tfbsNameList.contains( castedNode.getTfbsorHistoneName())){
			try{

				bufferedWriter = bufferedWriterHashMap.get( tfbsNameandCellLineName);

				if( bufferedWriter == null){
					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.TF_ANNOTATION_DIRECTORY + tfbsNameandCellLineName + ".txt", true);
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriterHashMap.put( tfbsNameandCellLineName, bufferedWriter);
					bufferedWriter.write( "Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "tfbs node Chrom Name" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node Tfbs Name" + "\t" + "node CellLineName" + "\t" + "node FileName" + System.getProperty( "line.separator"));
					bufferedWriter.flush();
				}

				if( tfbsNameandCellLineName2ZeroorOneMap.get( tfbsNameandCellLineName) == null){
					tfbsNameandCellLineName2ZeroorOneMap.put( tfbsNameandCellLineName, 1);
				}

				bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName() + "\t" + castedNode.getCellLineName() + "\t" + castedNode.getFileName() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

				tfandCellLineOverlapList.add( new TfNameandCellLineNameOverlap( tfbsNameandCellLineName,
						castedNode.getLow(), castedNode.getHigh()));

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervals( outputFolder, node.getLeft(), interval, chromName, bufferedWriterHashMap,
					tfbsNameList, tfbsNameandCellLineName2ZeroorOneMap, tfandCellLineOverlapList, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervals( outputFolder, node.getRight(), interval, chromName, bufferedWriterHashMap,
					tfbsNameList, tfbsNameandCellLineName2ZeroorOneMap, tfandCellLineOverlapList, overlapDefinition);
		}
	}

	// New Functionality added

	// Search1
	public void findAllOverlappingTfbsIntervals( IntervalTreeNode node, Interval interval,
			BufferedWriter bufferedWriter, List<IntervalTreeNode> overlappedNodeList) {

		TforHistoneIntervalTreeNode castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){

			if( node instanceof TforHistoneIntervalTreeNode){
				castedNode = ( TforHistoneIntervalTreeNode)node;
			}

			overlappedNodeList.add( node);

			try{
				bufferedWriter.write( "tfbs" + "\t" + castedNode.getChromName().toString() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName().toString() + "\t" + castedNode.getCellLineName().toString() + "\t" + castedNode.getFileName().toString() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingTfbsIntervals( node.getLeft(), interval, bufferedWriter, overlappedNodeList);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingTfbsIntervals( node.getRight(), interval, bufferedWriter, overlappedNodeList);

		}

	}

	// Search1
	public void findAllOverlappingDnaseIntervals( IntervalTreeNode node, Interval interval,
			BufferedWriter bufferedWriter, List<IntervalTreeNode> overlappedNodeList) {

		DnaseIntervalTreeNode castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){

			castedNode = ( DnaseIntervalTreeNode)node;

			overlappedNodeList.add( node);

			try{
				bufferedWriter.write( "dnase" + "\t" + castedNode.getChromName().toString() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getCellLineName().toString() + "\t" + castedNode.getFileName().toString() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingDnaseIntervals( node.getLeft(), interval, bufferedWriter, overlappedNodeList);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingDnaseIntervals( node.getRight(), interval, bufferedWriter, overlappedNodeList);
		}

	}

	// 1 July 2015
	// with Numbers
	// Empirical P Value Calculation
	// with IO
	public void findAllOverlappingDnaseIntervalsWithIOWithNumbers(
			String outputFolder, 
			int permutationNumber,
			IntervalTreeNode node, 
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntObjectMap<BufferedWriter> bufferedWriterHashMap,
			TIntByteMap dnaseCellLineNumber2PermutationZeroorOneMap, 
			int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		int permutationNumberDnaseCellLineNumber;

		DnaseIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof DnaseIntervalTreeNodeWithNumbers){
				castedNode = (DnaseIntervalTreeNodeWithNumbers)node;
			}

			try{

				permutationNumberDnaseCellLineNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
						permutationNumber, 
						castedNode.getCellLineNumber(),
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER);

				bufferedWriter = bufferedWriterHashMap.get( permutationNumberDnaseCellLineNumber);

				if( bufferedWriter == null){

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_DNASE + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + permutationNumberDnaseCellLineNumber + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "dnase" + "\t" + "dnaseChromName" + "\t" + "dnaseLow" + "\t" + "dnaseHigh" + "\t" + "dnaseCellLineNumber" + "\t" + "dnaseFileNumber" + System.getProperty( "line.separator"));
					bufferedWriter.flush();

					bufferedWriterHashMap.put( permutationNumberDnaseCellLineNumber, bufferedWriter);
				}

				if( !( dnaseCellLineNumber2PermutationZeroorOneMap.containsKey( permutationNumberDnaseCellLineNumber))){
					dnaseCellLineNumber2PermutationZeroorOneMap.put( permutationNumberDnaseCellLineNumber,Commons.BYTE_1);
				}

				bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "dnase" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getCellLineNumber() + "\t" + castedNode.getFileNumber() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingDnaseIntervalsWithIOWithNumbers( outputFolder, permutationNumber, node.getLeft(),
					interval, chromName, bufferedWriterHashMap, dnaseCellLineNumber2PermutationZeroorOneMap,
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingDnaseIntervalsWithIOWithNumbers( outputFolder, permutationNumber, node.getRight(),
					interval, chromName, bufferedWriterHashMap, dnaseCellLineNumber2PermutationZeroorOneMap,
					overlapDefinition);

		}

	}
	// with Numbers
	// with IO
	// 1 July 2015

	// @todo
	// with Numbers
	// Empirical P Value Calculation
	// with IO
	public void findAllOverlappingDnaseIntervalsWithIOWithNumbers( String outputFolder, int permutationNumber,
			IntervalTreeNode node, InputLineMinimal interval, ChromosomeName chromName,
			TIntObjectMap<BufferedWriter> bufferedWriterHashMap,
			TIntIntMap permutationNumberDnaseCellLineNumber2ZeroorOneMap, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		int permutationNumberDnaseCellLineNumber;

		DnaseIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof DnaseIntervalTreeNodeWithNumbers){
				castedNode = ( DnaseIntervalTreeNodeWithNumbers)node;
			}

			try{

				permutationNumberDnaseCellLineNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
						permutationNumber, castedNode.getCellLineNumber(),
						GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER);

				bufferedWriter = bufferedWriterHashMap.get( permutationNumberDnaseCellLineNumber);

				if( bufferedWriter == null){

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_DNASE + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + permutationNumberDnaseCellLineNumber + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "dnase" + "\t" + "dnaseChromName" + "\t" + "dnaseLow" + "\t" + "dnaseHigh" + "\t" + "dnaseCellLineNumber" + "\t" + "dnaseFileNumber" + System.getProperty( "line.separator"));
					bufferedWriter.flush();

					bufferedWriterHashMap.put( permutationNumberDnaseCellLineNumber, bufferedWriter);
				}

				if( !( permutationNumberDnaseCellLineNumber2ZeroorOneMap.containsKey( permutationNumberDnaseCellLineNumber))){
					permutationNumberDnaseCellLineNumber2ZeroorOneMap.put( permutationNumberDnaseCellLineNumber, 1);
				}

				bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "dnase" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getCellLineNumber() + "\t" + castedNode.getFileNumber() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingDnaseIntervalsWithIOWithNumbers( outputFolder, permutationNumber, node.getLeft(),
					interval, chromName, bufferedWriterHashMap, permutationNumberDnaseCellLineNumber2ZeroorOneMap,
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingDnaseIntervalsWithIOWithNumbers( outputFolder, permutationNumber, node.getRight(),
					interval, chromName, bufferedWriterHashMap, permutationNumberDnaseCellLineNumber2ZeroorOneMap,
					overlapDefinition);

		}

	}

	// with Numbers
	// @todo

	// 17.OCT.2014
	// called from convert methods in AnnotatePermutations
	// called from writeAnnotationstoFiles methods in AnnotatePermutations
	// Called from writeAnnotationstoFiles_ElementNumberKeggPathwayNumber method
	// in AnnotatePermutations
	// Called from convertGeneratedMixedNumberToName in
	// CollectionofPermutationsResults
	// Get GeneSetNumber from mixed number
	public static int getGeneSetNumber( 
			long permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int geneSetNumber = Integer.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

			case INT_4DIGIT_KEGGPATHWAYNUMBER:
			case INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER:
			case LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER:
			case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:
				geneSetNumber = ( int)( permutationNumberElementNumberCellLineNumberKeggPathwayNumber % 10000L);
				break;
			
			case INT_10DIGIT_USERDEFINEDGENESETNUMBER:
			case LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER:
				geneSetNumber = ( int)( permutationNumberElementNumberCellLineNumberKeggPathwayNumber % Commons.LONG_10DIGITS);
				break;
			
			case INT_10DIGIT_KEGGPATHWAYNUMBER:
				geneSetNumber = (int)( permutationNumberElementNumberCellLineNumberKeggPathwayNumber % Commons.LONG_10DIGITS);
				break;
			case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER:
			case LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER:
				geneSetNumber = (int)( permutationNumberElementNumberCellLineNumberKeggPathwayNumber % 100000L);
				break;
								
			default:
				break;
			
			
		}// End of Switch

		return geneSetNumber;
	}

	// 17.OCT.2014
	// Called from convert methods
	// Called from writeAnnotationstoFiles methods
	// Get CellLineNumberOrGeneSetNumber from mixed number
	public static int getCellLineNumberOrGeneSetNumber( 
			int mixedNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int cellLineNumberOrGeneSetNumber = Integer.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

			case INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER:{
				cellLineNumberOrGeneSetNumber = mixedNumber % Commons.INT_4DIGITS;
				break;
			}
			case INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
				cellLineNumberOrGeneSetNumber = mixedNumber %  Commons.INT_4DIGITS;
				break;
			}
			
			case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER:
				cellLineNumberOrGeneSetNumber = mixedNumber % Commons.INT_5DIGITS;
				break;
				
			case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER:
				cellLineNumberOrGeneSetNumber = mixedNumber % Commons.INT_5DIGITS;
				break;
				
			default:
				break;
			
		}// End of SWITCH

		return cellLineNumberOrGeneSetNumber;
	}

	// @todo For Annotation with Numbers starts
	public static int removeCellLineNumber( int elementNumberCellLineNumberKeggPathwayNumber) {

		// 1., 2. and 3. digits show keggPathwayNumber
		// 4., 5. and 6. digits show cellLineNumber
		// 7., 8., 9. and 10. digits show elementNumber
		int elementNumber;
		int cellLineNumber;
		int keggPathwayNumber;

		int elmentNumberCellLineNumber;
		int elementNumberKeggPathwayNumber;

		// example 100_300_020
		keggPathwayNumber = elementNumberCellLineNumberKeggPathwayNumber % 1000;
		elmentNumberCellLineNumber = elementNumberCellLineNumberKeggPathwayNumber - keggPathwayNumber;
		cellLineNumber = elmentNumberCellLineNumber % 1000000;
		elementNumber = elmentNumberCellLineNumber - cellLineNumber;
		elementNumberKeggPathwayNumber = elementNumber + keggPathwayNumber;

		return elementNumberKeggPathwayNumber;
	}

	// @todo For Annotation with Numbers ends

	// @todo For Annotation with Numbers starts
	public static int removeElementNumberCellLineNumber( int elementNumberCellLineNumberKeggPathwayNumber) {

		// 1., 2. and 3. digits show keggPathwayNumber
		// 4., 5. and 6. digits show cellLineNumber
		// 7., 8., 9. and 10. digits show elementNumber
		int keggPathwayNumber;

		// example 100_300_020
		keggPathwayNumber = elementNumberCellLineNumberKeggPathwayNumber % 1000;

		return keggPathwayNumber;
	}

	// @todo For Annotation with Numbers ends

	// @todo For Annotation with Numbers starts
	public static int removeCellLineNumberKeggPathwayNumber( int elementNumberCellLineNumberKeggPathwayNumber) {

		// 1., 2. and 3. digits show keggPathwayNumber
		// 4., 5. and 6. digits show cellLineNumber
		// 7., 8., 9. and 10. digits show elementNumber
		int elementNumber;
		int cellLineNumberKeggPathwayNumber;

		// example 100_300_020
		cellLineNumberKeggPathwayNumber = elementNumberCellLineNumberKeggPathwayNumber % 1000000;

		elementNumber = elementNumberCellLineNumberKeggPathwayNumber - cellLineNumberKeggPathwayNumber;

		return elementNumber;
	}

	// @todo For Annotation with Numbers ends
	
	
	//13 July 2015
	public static int getKeggPathwayNumber( 
			long elementNumberCellLineNumberKeggPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int keggPathwayNumber = Integer.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

			case LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER:
				keggPathwayNumber = (int) (elementNumberCellLineNumberKeggPathwayNumber % Commons.LONG_5DIGITS);
				break;
		
			default:
				break;

		} // End of SWITCH

		return keggPathwayNumber;
	}

//	// Annotation
//	// AnnotateGivenIntervals with Numbers
//	public static short getKeggPathwayNumber(
//			int elementNumberCellLineNumberKeggPathwayNumber,
//			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {
//
//		short keggPathwayNumber = Short.MIN_VALUE;
//
//		switch( generatedMixedNumberDescriptionOrderLength){
//
//			case INT_4DIGITS_ELEMENTNUMBER_3DIGITS_CELLLINENUMBER_3DIGITS_KEGGPATHWAYNUMBER:
//				// example 100_300_020
//				keggPathwayNumber = ( short)( elementNumberCellLineNumberKeggPathwayNumber % 1000);
//				break;
//				
//			case	INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER:
//				keggPathwayNumber = ( short)( elementNumberCellLineNumberKeggPathwayNumber % 100000);
//				break;
//	
//			default:
//				break;
//
//		} // End of SWITCH
//
//		return keggPathwayNumber;
//	}

	// Annotation
	// AnnotateGivenIntervals with Numbers
	public static short getCellLineNumber( int mixedNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		// INT_4DIGITS_ELEMENTNUMBER_3DIGITS_CELLLINENUMBER_3DIGITS_KEGGPATHWAYNUMBER
		short cellLineNumber = Short.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

		case INT_4DIGITS_ELEMENTNUMBER_3DIGITS_CELLLINENUMBER_3DIGITS_KEGGPATHWAYNUMBER:
			// example 100_300_020
			cellLineNumber = ( short)( ( mixedNumber / 1000) % 1000);
			break;

		case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER:
			cellLineNumber = ( short)(mixedNumber % 100000) ;
			break;
			
		default:
			break;

		}// End of SWITCH

		return cellLineNumber;
	}

	// 17.OCT.2014
	// called from convert methods in AnnotatePermutations
	// called from writeAnnotationstoFiles_ElementNumberCellLineNumber method in
	// AnnotatePermutations
	// called from writeAnnotationstoFiles
	// called from convertGeneratedMixedNumberToName method in
	// CollectionofPermutationsResults
	// Get CellLineNumber from mixed number
	public static int getCellLineNumber( 
			long mixedNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int cellLineNumber = Integer.MIN_VALUE;
		long cellLineNumberKeggPathwayNumber;

		switch( generatedMixedNumberDescriptionOrderLength){

			case INT_4DIGIT_DNASECELLLINENUMBER:
			case INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER:
			case INT_4DIGIT_HISTONENUMBER_4DIGIT_CELLLINENUMBER:{
				cellLineNumber = ( int)( mixedNumber % 10000L);
				break;
			}
	
			case LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER:
			case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
				cellLineNumberKeggPathwayNumber = mixedNumber % 100000000L;
				cellLineNumber = ( int)( cellLineNumberKeggPathwayNumber / 10000L);
				break;
			}
			
			case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER:{
				cellLineNumber = (int) (mixedNumber % 100000);
				break;
			}
				
			case LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER:{
				cellLineNumber = (int) ((mixedNumber % Commons.LONG_10DIGITS) / Commons.INT_5DIGITS);
				break;
			}
				
			
			default:{
				break;
			}
		}// End of SWITCH

		return cellLineNumber;
	}

	// 4 Mart 2015
	// Annotation
	// AnnotateGivenIntervals with Numbers
	public static short getShortElementNumber( 
			int mixedNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		// INT_4DIGITS_ELEMENTNUMBER_3DIGITS_CELLLINENUMBER_3DIGITS_KEGGPATHWAYNUMBER
		short elementNumber = Short.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

		case INT_4DIGITS_ELEMENTNUMBER_3DIGITS_CELLLINENUMBER_3DIGITS_KEGGPATHWAYNUMBER:
			// example 100_300_020
			// cellLineNumberKeggPathwayNumber = elementNumberCellLineNumberKeggPathwayNumber % 1000000;
			// elementNumber = (short) ((elementNumberCellLineNumberKeggPathwayNumber - cellLineNumberKeggPathwayNumber)
			// / 1000000);
			elementNumber = ( short)( ( mixedNumber) / 1000000);

			break;
			
		case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER:
			elementNumber = (short) (( mixedNumber) / 100000);

			break;
			
		default:
			break;

		}

		return elementNumber;
	}

	// 6 NOV 2014
	public static int getElementTypeNumber( 
			long mixedNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		long permutationNumberElementTypeNumber = Long.MIN_VALUE;
		int elementTypeNumber = Integer.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

		case LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:
			permutationNumberElementTypeNumber = mixedNumber / 1000000L;
			elementTypeNumber = ( int)( permutationNumberElementTypeNumber % 10000L);
			break;
			
		default:
			break;

		}// End of SWITCH

		return elementTypeNumber;
	}

	// 8 NOV 2014
	public static int getElementTypeNumber( int elementTypeNumberElementNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int elementTypeNumber = Integer.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

		case INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:
			elementTypeNumber = ( int)( elementTypeNumberElementNumber / 1000000);
			break;

		default:
			break;

		}// End of SWITCH

		return elementTypeNumber;
	}

	// 8 NOV 2014
	public static int getElementNumber( 
			int mixedNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int elementNumber = Integer.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

			case INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:
				elementNumber = mixedNumber % Commons.INT_6DIGITS;
				break;
				
			case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER:
			case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER:
				elementNumber = mixedNumber / Commons.INT_5DIGITS;
				break;
	
			default:
				break;

		}// End of SWITCH

		return elementNumber;
	}

	// 17.OCT.2014
	// called from convert methods in AnnotatePermutations
	// Called from writeAnnotationstoFiles_ElementNumberCellLineNumber method in
	// AnnotatePermutations
	// Called from writeAnnotationstoFiles
	// Called from writeAnnotationstoFiles_ElementNumberKeggPathwayNumber method
	// in AnnotatePermutations
	// called from convertGeneratedMixedNumberToName in
	// CollectionofPermutationsResults
	// Get ElementNumber from mixed number
	public static int getElementNumber( long mixedNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int elementNumber = Integer.MIN_VALUE;
		long permutationNumberElementNumber = Long.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

			case INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER:
			case INT_4DIGIT_HISTONENUMBER_4DIGIT_CELLLINENUMBER:
			case INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER:{
				elementNumber = ( int)( mixedNumber / 10000L);
				break;
			}
			case LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER:
			case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
				permutationNumberElementNumber = mixedNumber / 100000000L;
				elementNumber = ( int)( permutationNumberElementNumber % 10000L);
				break;
			}
	
			case LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:{
				elementNumber = ( int)( mixedNumber % 1000000L);
				break;
			}
	
			case INT_6DIGIT_ELEMENTNUMBER:{
				elementNumber = ( int)( mixedNumber % 1000000L);
				break;
			}
	
			case INT_10DIGIT_GENENUMBER:{
				elementNumber = ( int)( mixedNumber % Commons.LONG_10DIGITS);
				break;
			}
	
			case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER: {
				elementNumber = (int)(mixedNumber / Commons.LONG_5DIGITS);
				break;
			}
			
			case LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER:{
				elementNumber = ( int)( mixedNumber / Commons.LONG_10DIGITS);
				break;
			}
	
			default:{
				break;
			}

		}// End of SWITCH

		return elementNumber;

	}

	// 17.OCT.2014
	// Called from convert methods in AnnotatePermutations
	// Get ElementNumberCellLineNumberOrKeggPathwayNumber from mixed number
	public static int getPermutationNumberRemovedMixedNumber(
			long permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int elementNumberCellLineNumberOrKeggPathwayNumber = Integer.MIN_VALUE;
		int elementTypeNumberElementNumber = Integer.MIN_VALUE;
		int userDefinedGeneSetNumber = Integer.MIN_VALUE;
		int geneNumber = Integer.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

			case LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER:{
				userDefinedGeneSetNumber = ( int)( permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber % Commons.LONG_10DIGITS);
				return userDefinedGeneSetNumber;
			}
			case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
				int elementNumber = getElementNumber( permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber,
						generatedMixedNumberDescriptionOrderLength);
				int cellLineNumber = getCellLineNumber( permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber,
						generatedMixedNumberDescriptionOrderLength);
				int keggPathwayNumber = getGeneSetNumber(
						permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber,
						generatedMixedNumberDescriptionOrderLength);
	
				if( cellLineNumber > 0){
					//GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER
					elementNumberCellLineNumberOrKeggPathwayNumber = elementNumber * Commons.INT_4DIGITS + cellLineNumber;
				}else if( keggPathwayNumber > 0){
					//GeneratedMixedNumberDescriptionOrderLength.INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER
					elementNumberCellLineNumberOrKeggPathwayNumber = elementNumber * Commons.INT_4DIGITS + keggPathwayNumber;
				}
				return elementNumberCellLineNumberOrKeggPathwayNumber;
			}
			case LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:{
				elementTypeNumberElementNumber = ( int)( permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber % 10000000000L);
				return elementTypeNumberElementNumber;
			}
			case LONG_7DIGIT_PERMUTATIONNUMBER_10DIGIT_GENENUMBER:{
				geneNumber = ( int)( permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber % 10000000000L);
				return geneNumber;
			}
			default:
				break;
	
		}// End of switch

		return elementNumberCellLineNumberOrKeggPathwayNumber;
	}
	
	//For Debug purposes starts
	//11 NOVEMBER 2015
	public static long getKEGGPathwayNumberRemovedMixedNumber(
			long permutationNumberElementNumberCellLineNumberKEGGPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength){
		
		long elementNumberCellLineNumber = Long.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){
			case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
				elementNumberCellLineNumber = permutationNumberElementNumberCellLineNumberKEGGPathwayNumber / 10000L;
				break;
			}
			default:{
				break;
			}

		}// End of SWITCH

		return elementNumberCellLineNumber;
		
	}
	//For Debug purposes ends
	

	// 17.OCT.2014
	// Called from convert methods in AnnotatePermutations
	// Get PermutationNumber from mixed number
	public static long getPermutationNumberRemovedLongMixedNumber(
			long permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		long elementNumberCellLineNumberKeggPathwayNumber = Long.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){
			case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
				elementNumberCellLineNumberKeggPathwayNumber = permutationNumberElementNumberCellLineNumberKeggPathwayNumber % 1000000000000L;
				break;
			}
			default:{
				break;
			}

		}// End of SWITCH

		return elementNumberCellLineNumberKeggPathwayNumber;
	}

	// Long version ends

	// 15.OCT.2014
	// Called from convert methods
	// Called from writeAnnotationstoFiles methods
	// Get PermutationNumber from mixed number
	public static int getPermutationNumber( int permutationNumberCellLineNumberOrGeneSetNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int permutationNumber = Integer.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

		case INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER:
		case INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
			permutationNumber = ( int)( permutationNumberCellLineNumberOrGeneSetNumber / 10000);
			break;
		}
		default:{
			break;
		}
		}// End of SWITCH

		return permutationNumber;
	}

	// 17.OCT.2014
	// Called from convert methods in AnnotatePermutations
	// Called from writeAnnotationstoFiles_ElementNumberCellLineNumber method in
	// AnnotatePermutations
	// Called from writeAnnotationstoFiles method in AnnotatePermutations
	// Called from writeAnnotationstoFiles_ElementNumberKeggPathwayNumber method
	// in AnnotatePermutations
	// Get PermutationNumber from mixed number
	public static int getPermutationNumber( 
			long permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		// Long.MAX_VALUE 9223372_0368_5477_5807
		// Long.MIN_VALUE -9223372_0368_5477_5808

		int permutationNumber = Integer.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){
		
			case LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER:{
				permutationNumber = ( int)( permutationNumberElementNumberCellLineNumberKeggPathwayNumber / Commons.LONG_10DIGITS);
				break;
			}
			case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
				permutationNumber = ( int)( permutationNumberElementNumberCellLineNumberKeggPathwayNumber / 1000000000000L);
				break;
			}
			case LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:{
				permutationNumber = ( int)( permutationNumberElementNumberCellLineNumberKeggPathwayNumber / 10000000000L);
				break;
			}
			case LONG_7DIGIT_PERMUTATIONNUMBER_10DIGIT_GENENUMBER:{
				permutationNumber = ( int)( permutationNumberElementNumberCellLineNumberKeggPathwayNumber / 10000000000L);
				break;
			}
			default:{
				break;
			}

		}// End of SWITCH

		return permutationNumber;
	}

	public static long generatePermutationNumberGeneNumber( int permutationNumber, int geneNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		// max integer 2147483647
		// min integer -2147483648

		// Long.MAX_VALUE 9223372_0368_5477_5807
		// Long.MIN_VALUE -9223372_0368_5477_5808

		long permutationNumberGeneNumber = Long.MIN_VALUE;
		long _permutationNumber;
		long _geneNumber;

		switch( generatedMixedNumberDescriptionOrderLength){

		case LONG_7DIGIT_PERMUTATIONNUMBER_10DIGIT_GENENUMBER:
			_permutationNumber = permutationNumber * Commons.LONG_10DIGITS;
			_geneNumber = geneNumber * 1L;
			permutationNumberGeneNumber = _permutationNumber + _geneNumber;
			break;
		default:
			break;

		}// End of SWITCH

		return permutationNumberGeneNumber;

	}

	// Enrichment
	// AnnotatePermutations WithoutIO withNumbers
	// AnnotatePermutations withIO withNumbers
	// PERMUTATIONNUMBER DNASECELLLINENUMBER
	// PERMUTATIONNUMBER KEGGPATHWAYNUMBER
	public static int generatePermutationNumberCellLineNumberorGeneSetNumber( 
			int permutationNumber,
			int cellLineNumberorGeneSetNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		// max integer 2147483647
		// min integer -2147483648

		int permutationNumberCellLineNumberorGeneSetNumber = Integer.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

		// PERMUTATIONNUMBER DNASECELLLINENUMBER
		case INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER:{
			permutationNumberCellLineNumberorGeneSetNumber = permutationNumber * 10000 + cellLineNumberorGeneSetNumber;
			break;
		}

		// PERMUTATIONNUMBER KEGGPATHWAYNUMBER
		case INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
			permutationNumberCellLineNumberorGeneSetNumber = permutationNumber * 10000 + cellLineNumberorGeneSetNumber;
			break;
		}
		default:{
			break;
		}
		}

		return permutationNumberCellLineNumberorGeneSetNumber;

	}

	// Enrichment
	// WithoutIO WithNumbers
	// PermutationNumber ElementTypeNumber ElementNumber
	public static long generateMixedNumber( 
			int permutationNumber, 
			int elementTypeNumber, 
			int elementNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		long mixedNumber = Long.MIN_VALUE;

		long _permutationNumber;
		long _elementTypeNumber;
		long _elementNumber;

		switch( generatedMixedNumberDescriptionOrderLength){

		// PermutationNumber ElementTypeNumber ElementNumber
		case LONG_7DIGIT_PERMUTATIONNUMBER_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:
			_permutationNumber = permutationNumber * 10000000000L;
			_elementTypeNumber = elementTypeNumber * 1000000L;
			_elementNumber = elementNumber * 1L;

			mixedNumber = _permutationNumber + _elementTypeNumber + _elementNumber;
			break;
		default:
			break;

		}// End of SWITCH

		return mixedNumber;
	}
	
	// 8 July 2015
	public static long generateMixedNumber(
			int permutationNumber, 
			short elementNumber, 
			short cellLineNumber,
			int geneSetNumber, 
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {
		
		long mixedNumber = Long.MIN_VALUE;
		
		switch(generatedMixedNumberDescriptionOrderLength){
		
			case LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER:
				mixedNumber = permutationNumber * Commons.LONG_10DIGITS + geneSetNumber * 1L;
				break;
			default:
				break;
		
		}//End of SWITCH
		
		return mixedNumber;

	}
	// 8 July 2015

	// Enrichment
	// AnnotatePermutations withoutIO withNumbers
	// AnnotatePermutations withIO withNumbers
	// PERMUTATIONNUMBER TFNUMBER CELLLINENUMBER
	// PERMUTATIONNUMBER HISTONENUMBER CELLLINENUMBER
	public static long generateMixedNumber( 
			int permutationNumber, 
			short elementNumber, 
			short cellLineNumber,
			short geneSetNumber, 
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		// Long.MAX_VALUE 9223372_0368_5477_5807
		// Long.MIN_VALUE -9223372_0368_5477_5808

		long mixedNumber = Long.MIN_VALUE;

		long _permutationNumber;
		long _elementNumber;
		long _cellLineNumber;
		long _keggPathwayNumber;

		switch( generatedMixedNumberDescriptionOrderLength){

		// PERMUTATIONNUMBER TFNUMBER CELLLINENUMBER
		// PERMUTATIONNUMBER HISTONENUMBER CELLLINENUMBER
		case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
			_permutationNumber = permutationNumber * 1000000000000L;
			_elementNumber = elementNumber * 100000000L;
			_cellLineNumber = cellLineNumber * 10000L;
			_keggPathwayNumber = geneSetNumber * 1L;

			mixedNumber = _permutationNumber + _elementNumber + _cellLineNumber + _keggPathwayNumber;

			break;

		}

		// PERMUTATIONNUMBER USERDEFINEDGENESETNUMBER
		case LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER:{
			mixedNumber = permutationNumber * Commons.LONG_10DIGITS + geneSetNumber * 1L;
			break;

		}
		default:{
			break;
		}
		}// End of switch

		return mixedNumber;
	}
	
	
	//27 July 2015
	// UserDefinedLibrary
	public static int generateElementTypeNumberElementNumber(
			int elementTypeNumber, 
			int elementNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int elementTypeNumberElementNumber = Integer.MIN_VALUE;

		// Integer.MAX 2147_483_647
		// Integer.MIN -2147_483_648
		switch( generatedMixedNumberDescriptionOrderLength){

			case INT_4DIGIT_ELEMENTTYPENUMBER_6DIGIT_ELEMENTNUMBER:{
				elementTypeNumberElementNumber = elementTypeNumber * Commons.INT_6DIGITS + elementNumber;
				break;
			}
			default:{
				break;
			}

		}// End of switch

		return elementTypeNumberElementNumber;
	}
	

	// 3 July 2015
	// Annotation
	// TF
	// Histone
	public static int generateElementNumberCellLineNumber(
			int elementNumber, 
			int cellLineNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		int elementNumberCellLineNumber = Integer.MIN_VALUE;

		// Integer.MAX 2147_483_647
		// Integer.MIN -2147_483_648

		switch( generatedMixedNumberDescriptionOrderLength){

		case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER:{
			elementNumberCellLineNumber = elementNumber * Commons.INT_5DIGITS + cellLineNumber;
			break;
		}
		default:{
			break;
		}

		}// End of switch

		return elementNumberCellLineNumber;
	}

	// 3 July 2015
	
	//13 July 2015
	//Annotation
	//WITH Numbers
	//TF CellLine KEGGPathway
	public static long generateLongElementNumberCellLineNumberKeggPathwayNumber(
			int elementNumber, 
			int cellLineNumber,
			int keggPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		long elementNumberCellLineNumberKeggPathwayNumber = Long.MIN_VALUE;

		// Integer.MAX 2147_483_647
		// Integer.MIN -2147_483_648

		switch( generatedMixedNumberDescriptionOrderLength){
		
			case LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER:{
				elementNumberCellLineNumberKeggPathwayNumber = elementNumber * Commons.LONG_10DIGITS + cellLineNumber * Commons.INT_5DIGITS + keggPathwayNumber;
				break;
			}
		
			default:{
				break;
			}

		}// End of SWITCH

		return elementNumberCellLineNumberKeggPathwayNumber;
	}

//	// Annotation
//	// AnnotateGivenIntervals with numbers
//	// TF
//	// TF KEGGPATHWAY
//	// TF CELLLINE KEGGPATHWAY
//	// This method is called with value of "0" for keggPathwayNumber
//	// But keggPathwayNumber is added in the code later
//	public static int generateElementNumberCellLineNumberKeggPathwayNumber(
//			short elementNumber, 
//			short cellLineNumber,
//			short keggPathwayNumber,
//			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {
//
//		int elementNumberCellLineNumberKeggPathwayNumber = Integer.MIN_VALUE;
//
//		// Integer.MAX 2147_483_647
//		// Integer.MIN -2147_483_648
//
//		switch( generatedMixedNumberDescriptionOrderLength){
//
//			case INT_4DIGITS_ELEMENTNUMBER_3DIGITS_CELLLINENUMBER_3DIGITS_KEGGPATHWAYNUMBER:{
//				elementNumberCellLineNumberKeggPathwayNumber = elementNumber * 1000000 + cellLineNumber * 1000 + keggPathwayNumber;
//				break;
//			}
//			
//			default:{
//				break;
//			}
//
//		}// End of switch
//
//		return elementNumberCellLineNumberKeggPathwayNumber;
//	}

	// AnnotatePermutations withoutIO withNumbers
	// AnnotatePermutations withIO withNumbers
	// TF_KEGGPATHWAY
	public static long removeCellLineNumber( long permutationNumberElementNumberCellLineNumberKeggPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		long cellLineNumberKeggPathwayNumber = Long.MIN_VALUE;
		short keggPathwayNumber = Short.MIN_VALUE;
		long cellLineNumber = Long.MIN_VALUE;
		long cellLineNumberRemoved = Long.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){

			case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
	
				// Get only CellLineNumber and KEGGPathwayNumber
				cellLineNumberKeggPathwayNumber = permutationNumberElementNumberCellLineNumberKeggPathwayNumber % 100000000L;
	
				// get KEGG Pathway Number
				keggPathwayNumber = ( short)( cellLineNumberKeggPathwayNumber % 10000L);
	
				cellLineNumber = cellLineNumberKeggPathwayNumber - keggPathwayNumber;
	
				cellLineNumberRemoved = permutationNumberElementNumberCellLineNumberKeggPathwayNumber - cellLineNumber;
				break;
			}
			default:{
				break;
			}
		}// End of SWITCH

		return cellLineNumberRemoved;

	}

	//10 July 2015
	public static long addKeggPathwayNumber(
			int elementNumberCellLineNumber,
			int keggPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		long elementNumberCellLineNumberKeggPathwayNumber = Long.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){
		
			case LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER:
				elementNumberCellLineNumberKeggPathwayNumber = elementNumberCellLineNumber * 100000L + keggPathwayNumber;
				break;
				
			default:
				break;
			
		}// End of SWITCH

		return elementNumberCellLineNumberKeggPathwayNumber;
	}
	
	// AnnotatePermutations withoutIO withNumbers
	// AnnotatePermutations withIO withNumbers
	// TF_KEGGPATHWAY
	// TF_CELLLINE_KEGGPATHWAY
	public static long addKeggPathwayNumber( long permutationNumberElementNumberCellLineNumber,
			int keggPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		long permutationNumberElementNumberCellLineNumberKeggPathwayNumber = Long.MIN_VALUE;

		switch( generatedMixedNumberDescriptionOrderLength){
		
			case LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER:{
				permutationNumberElementNumberCellLineNumberKeggPathwayNumber = permutationNumberElementNumberCellLineNumber + keggPathwayNumber;
				break;
			}
			
			case LONG_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER_5DIGITS_KEGGPATHWAYNUMBER:
				break;
				
			default:{
				break;
			}
			
		}// End of SWITCH

		return permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
	}

	// 10 July 2015
	public static int removeCellLineNumberAddKeggPathwayNumber(
			int mixedNumber, 
			int keggPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {
		
		int tfNumberKEGGPathwayNumber = Integer.MIN_VALUE;
		
		switch(generatedMixedNumberDescriptionOrderLength) {
		
			case INT_5DIGITS_ELEMENTNUMBER_5DIGITS_KEGGPATHWAYNUMBER:
				tfNumberKEGGPathwayNumber = (mixedNumber / 100000) * 100000 + keggPathwayNumber;
				break;
			
			default:
				break;
		
		}// End of SWITCH
		
		// debug starts
		if (keggPathwayNumber > 269){
			System.out.println("There is  situation, keggPathwayNumber: " + keggPathwayNumber);
		}
		
		if (tfNumberKEGGPathwayNumber < 10000){
			System.out.println("There is  situation, tfNumberKEGGPathwayNumber: " + tfNumberKEGGPathwayNumber);
			
		}
		// debug ends
		
		return tfNumberKEGGPathwayNumber;
	}
	
	// AnnotatePermutations withoutIO withNumbers
	// TF_KEGGPATHWAY
	public static long removeCellLineNumberAddKeggPathwayNumber(
			long mixedNumber, 
			int keggPathwayNumber,
			GeneratedMixedNumberDescriptionOrderLength generatedMixedNumberDescriptionOrderLength) {

		long cellLineRemoved = Long.MIN_VALUE;
		long cellLineRemovedKeggPathwayAdded = Long.MIN_VALUE;

		cellLineRemoved = removeCellLineNumber( mixedNumber, generatedMixedNumberDescriptionOrderLength);
		
		cellLineRemovedKeggPathwayAdded = addKeggPathwayNumber( cellLineRemoved, keggPathwayNumber,generatedMixedNumberDescriptionOrderLength);

		return cellLineRemovedKeggPathwayAdded;

	}

	// 26 June 2015
	// Find All Overlapping Dnase Intervals
	// Without IO
	// With Numbers
	// Without overlappedNodeList
	// For All Chromosomes
	public void findAllOverlappingDnaseIntervalsWithoutIOWithNumbersForAllChromosomes( int permutationNumber,
			IntervalTreeNode node, InputLineMinimal interval, ChromosomeName chromName,
			TIntIntMap dnaseCellLineName2ZeroorOneMap, int overlapDefinition) {

		int dnaseCellLineNumber;
		DnaseIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof DnaseIntervalTreeNodeWithNumbers){
				castedNode = ( DnaseIntervalTreeNodeWithNumbers)node;
			}

			dnaseCellLineNumber = castedNode.getCellLineNumber();

			if( !( dnaseCellLineName2ZeroorOneMap.containsKey( dnaseCellLineNumber))){
				dnaseCellLineName2ZeroorOneMap.put( dnaseCellLineNumber, 1);
			}
		}// End of IF OVERLAPS

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingDnaseIntervalsWithoutIOWithNumbersForAllChromosomes( permutationNumber, node.getLeft(),
					interval, chromName, dnaseCellLineName2ZeroorOneMap, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingDnaseIntervalsWithoutIOWithNumbersForAllChromosomes( permutationNumber, node.getRight(),
					interval, chromName, dnaseCellLineName2ZeroorOneMap, overlapDefinition);

		}

	}

	// 26 June 2015
	
	

	// 3 July 2015
	// For each interval return 1 or 0 if if permutationData has overlap with the interval and set to 1 for the corresponding tfNumberCellLineNumber
	public void findAllOverlappingTForHistoneIntervalsWithoutIOWithNumbers( 
			IntervalTreeNode node,
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntByteMap tforHistoneNumberCellLineNumber2PermutationZeroorOneMap, 
			int overlapDefinition) {

		int tforHistoneNumberCellLineNumber;
		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof TforHistoneIntervalTreeNodeWithNumbers){
				castedNode = ( TforHistoneIntervalTreeNodeWithNumbers)node;
			}

			// @todo
			// How to generate tfNumberCellLineNumber from tfNumber and cellLineNumber?
			// How does Annotation writes tfNumberCellLineNumber
			// INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER
			tforHistoneNumberCellLineNumber = IntervalTree.generateElementNumberCellLineNumber(
					castedNode.getTforHistoneNumber(), 
					castedNode.getCellLineNumber(),
					GeneratedMixedNumberDescriptionOrderLength.INT_5DIGITS_ELEMENTNUMBER_5DIGITS_CELLLINENUMBER);

			if( !( tforHistoneNumberCellLineNumber2PermutationZeroorOneMap.containsKey(tforHistoneNumberCellLineNumber))){
				tforHistoneNumberCellLineNumber2PermutationZeroorOneMap.put(tforHistoneNumberCellLineNumber, Commons.BYTE_1);
			}
		}// End of IF OVERLAPS

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			
			findAllOverlappingTForHistoneIntervalsWithoutIOWithNumbers(
					node.getLeft(), 
					interval, 
					chromName,
					tforHistoneNumberCellLineNumber2PermutationZeroorOneMap, 
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			
			findAllOverlappingTForHistoneIntervalsWithoutIOWithNumbers(
					node.getRight(), 
					interval, 
					chromName,
					tforHistoneNumberCellLineNumber2PermutationZeroorOneMap, 
					overlapDefinition);

		}

	}
	// 3 July 2015

	// 1 June 2015
	// with Numbers starts
	// Empirical P Value Calculation
	// without IO
	// without overlappedNodeList
	public void findAllOverlappingDnaseIntervalsWithoutIOWithNumbers(
			int permutationNumber, 
			IntervalTreeNode node,
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntByteMap dnaseCellLineNumber2PermutationZeroorOneMap, 
			int overlapDefinition) {

		int dnaseCellLineNumber;
		DnaseIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof DnaseIntervalTreeNodeWithNumbers){
				castedNode = ( DnaseIntervalTreeNodeWithNumbers)node;
			}

			dnaseCellLineNumber = castedNode.getCellLineNumber();

			if( !( dnaseCellLineNumber2PermutationZeroorOneMap.containsKey( dnaseCellLineNumber))){
				dnaseCellLineNumber2PermutationZeroorOneMap.put( dnaseCellLineNumber, Commons.BYTE_1);
			}
		}// End of IF OVERLAPS

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			
			findAllOverlappingDnaseIntervalsWithoutIOWithNumbers(
					permutationNumber, 
					node.getLeft(), 
					interval,
					chromName, 
					dnaseCellLineNumber2PermutationZeroorOneMap, 
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			
			findAllOverlappingDnaseIntervalsWithoutIOWithNumbers(
					permutationNumber, 
					node.getRight(), 
					interval,
					chromName, 
					dnaseCellLineNumber2PermutationZeroorOneMap, 
					overlapDefinition);

		}

	}
	// 1 June 2015
	
	
	//1 NOV 2015 starts
	//Without IO
	//With Numbers
	//With OverlappingNodelist
	public void findAllOverlappingDnaseIntervalsWithoutIOWithNumbers( 
			int permutationNumber, 
			IntervalTreeNode node,
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntObjectMap<List<IntervalTreeNode>> permutationNumberDnaseCellLineNumber2OverlappingNodeListMap, 
			int overlapDefinition) {
		
		int permutationNumberDnaseCellLineNumber;
		DnaseIntervalTreeNodeWithNumbers castedNode = null;
		List<IntervalTreeNode> overlappingNodeList = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof DnaseIntervalTreeNodeWithNumbers){
				//castedNode = ( DnaseIntervalTreeNodeWithNumbers)node;
				//castedNode must be newly created and We must not use the color of the node that comes from the chromosomeBased DnaseIntervalTree
				castedNode = new DnaseIntervalTreeNodeWithNumbers(
						node.getChromName(), 
						node.getLow(), 
						node.getHigh(), 
						((DnaseIntervalTreeNodeWithNumbers) node).getCellLineNumber(), 
						((DnaseIntervalTreeNodeWithNumbers) node).getFileNumber(), 
						NodeType.ORIGINAL);
			}

			permutationNumberDnaseCellLineNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
					permutationNumber, 
					castedNode.getCellLineNumber(),
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER);
			
			overlappingNodeList = permutationNumberDnaseCellLineNumber2OverlappingNodeListMap.get(permutationNumberDnaseCellLineNumber);
			
			//Pay attention: you have to add castedNode to the list
			//Further on you will need tforHistoneNumber in constructAnIntervalTreeWithNonOverlappingNodes method
			if (overlappingNodeList == null){
				overlappingNodeList = new ArrayList<IntervalTreeNode>();
				overlappingNodeList.add(castedNode);
				permutationNumberDnaseCellLineNumber2OverlappingNodeListMap.put(permutationNumberDnaseCellLineNumber, overlappingNodeList);
			}else{
				overlappingNodeList.add(castedNode);
			}

		}// End of IF OVERLAPS

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingDnaseIntervalsWithoutIOWithNumbers(
					permutationNumber, 
					node.getLeft(), 
					interval,
					chromName, 
					permutationNumberDnaseCellLineNumber2OverlappingNodeListMap, 
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingDnaseIntervalsWithoutIOWithNumbers( 
					permutationNumber, 
					node.getRight(), 
					interval,
					chromName, 
					permutationNumberDnaseCellLineNumber2OverlappingNodeListMap, 
					overlapDefinition);

		}

	}
	//1 NOV 2015 ends
	

	// with Numbers starts
	// Empirical P Value Calculation
	// without IO
	// without overlappedNodeList
	public void findAllOverlappingDnaseIntervalsWithoutIOWithNumbers( 
			int permutationNumber, 
			IntervalTreeNode node,
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntIntMap permutationNumberDnaseCellLineName2ZeroorOneMap, 
			int overlapDefinition) {

		int permutationNumberDnaseCellLineNumber;
		DnaseIntervalTreeNodeWithNumbers castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

			if( node instanceof DnaseIntervalTreeNodeWithNumbers){
				castedNode = ( DnaseIntervalTreeNodeWithNumbers)node;
			}

			permutationNumberDnaseCellLineNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
					permutationNumber, 
					castedNode.getCellLineNumber(),
					GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER);

			if( !( permutationNumberDnaseCellLineName2ZeroorOneMap.containsKey( permutationNumberDnaseCellLineNumber))){
				permutationNumberDnaseCellLineName2ZeroorOneMap.put( permutationNumberDnaseCellLineNumber, 1);
			}
		}// End of IF OVERLAPS

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingDnaseIntervalsWithoutIOWithNumbers( permutationNumber, node.getLeft(), interval,
					chromName, permutationNumberDnaseCellLineName2ZeroorOneMap, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingDnaseIntervalsWithoutIOWithNumbers( permutationNumber, node.getRight(), interval,
					chromName, permutationNumberDnaseCellLineName2ZeroorOneMap, overlapDefinition);

		}

	}

	// with Numbers ends

	// TShortObjectMap<StringBuilder>
	public void findAllOverlappingDnaseIntervalsWithNumbersStringBuilder( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			IntervalTreeNode node, Interval interval, ChromosomeName chromName, byte[] oneOrZeroByteArray,
			int overlapDefinition, TShortObjectMap<StringBuilder> cellLineNumber2CellLineNameMap,
			TShortObjectMap<StringBuilder> fileNumber2FileNameMap) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		StringBuilder cellLineName;
		StringBuilder fileName;

		DnaseIntervalTreeNodeWithNumbers castedNode = null;

		if( node instanceof DnaseIntervalTreeNodeWithNumbers){

			castedNode = ( DnaseIntervalTreeNodeWithNumbers)node;
		}

		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),
				overlapDefinition)){

			try{

				// Write Annotation Overlaps to element Named File
				if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

					cellLineName = cellLineNumber2CellLineNameMap.get( castedNode.getCellLineNumber());
					fileName = fileNumber2FileNameMap.get( castedNode.getFileNumber());

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.DNASE_ANNOTATION_DIRECTORY + cellLineName + ".txt", true);
					bufferedWriter = new BufferedWriter( fileWriter);

					oneOrZeroByteArray[castedNode.getCellLineNumber()] = 1;

					bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + cellLineName + "\t" + fileName + System.getProperty( "line.separator"));
					bufferedWriter.close();

				}// End of IF
					// Do not Write Annotation Overlaps to element Named File
				else{
					oneOrZeroByteArray[castedNode.getCellLineNumber()] = 1;
				}// End of ELSE

			}catch( IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingDnaseIntervalsWithNumbersStringBuilder( outputFolder,
					writeElementBasedAnnotationFoundOverlapsMode, node.getLeft(), interval, chromName,
					oneOrZeroByteArray, overlapDefinition, cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingDnaseIntervalsWithNumbersStringBuilder( outputFolder,
					writeElementBasedAnnotationFoundOverlapsMode, node.getRight(), interval, chromName,
					oneOrZeroByteArray, overlapDefinition, cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
		}
	}

	// new starts
	// Annotation Sequentially Starts
	public void findAllOverlappingDnaseIntervalsWithNumbers( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			IntervalTreeNode node, Interval interval, ChromosomeName chromName, byte[] oneOrZeroByteArray,
			int overlapDefinition, TShortObjectMap<CharSequence> cellLineNumber2CellLineNameMap,
			TShortObjectMap<CharSequence> fileNumber2FileNameMap) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		CharSequence cellLineName;
		CharSequence fileName;

		DnaseIntervalTreeNodeWithNumbers castedNode = null;

		if( node instanceof DnaseIntervalTreeNodeWithNumbers){

			castedNode = ( DnaseIntervalTreeNodeWithNumbers)node;
		}

		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),
				overlapDefinition)){

			try{

				// Write Annotation Overlaps to element Named File
				if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

					cellLineName = cellLineNumber2CellLineNameMap.get( castedNode.getCellLineNumber());
					fileName = fileNumber2FileNameMap.get( castedNode.getFileNumber());

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.DNASE_ANNOTATION_DIRECTORY + cellLineName + ".txt", true);
					bufferedWriter = new BufferedWriter( fileWriter);

					oneOrZeroByteArray[castedNode.getCellLineNumber()] = 1;

					bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + cellLineName + "\t" + fileName + System.getProperty( "line.separator"));
					bufferedWriter.close();

				}// End of IF
					// Do not Write Annotation Overlaps to element Named File
				else{
					oneOrZeroByteArray[castedNode.getCellLineNumber()] = 1;
				}// End of ELSE

			}catch( IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingDnaseIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					node.getLeft(), interval, chromName, oneOrZeroByteArray, overlapDefinition,
					cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingDnaseIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					node.getRight(), interval, chromName, oneOrZeroByteArray, overlapDefinition,
					cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
		}
	}

	// Annotation Sequent Ends
	// new ends

	// Annotation Sequentially Starts
	public void findAllOverlappingDnaseIntervalsWithNumbers( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			IntervalTreeNode node, Interval interval, ChromosomeName chromName, byte[] oneOrZeroByteArray,
			int overlapDefinition, char[][] dnaseCellLineNames, char[][] fileNames) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		char[] cellLineName;
		char[] fileName;

		DnaseIntervalTreeNodeWithNumbers castedNode = null;

		if( node instanceof DnaseIntervalTreeNodeWithNumbers){

			castedNode = ( DnaseIntervalTreeNodeWithNumbers)node;
		}

		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),
				overlapDefinition)){

			try{

				// Write Annotation Overlaps to element Named File
				if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

					cellLineName = dnaseCellLineNames[castedNode.getCellLineNumber()];
					fileName = fileNames[castedNode.getFileNumber()];

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.DNASE_ANNOTATION_DIRECTORY + String.valueOf( cellLineName) + ".txt",
							true);
					bufferedWriter = new BufferedWriter( fileWriter);

					oneOrZeroByteArray[castedNode.getCellLineNumber()] = 1;

					bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + String.valueOf( cellLineName) + "\t" + String.valueOf( fileName) + System.getProperty( "line.separator"));
					bufferedWriter.close();

				}// End of IF
					// Do not Write Annotation Overlaps to element Named File
				else{
					oneOrZeroByteArray[castedNode.getCellLineNumber()] = 1;
				}// End of ELSE

			}catch( IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingDnaseIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					node.getLeft(), interval, chromName, oneOrZeroByteArray, overlapDefinition, dnaseCellLineNames,
					fileNames);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingDnaseIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					node.getRight(), interval, chromName, oneOrZeroByteArray, overlapDefinition, dnaseCellLineNames,
					fileNames);
		}
	}

	// Annotation in Parallel Ends

	// Annotation
	// DNASE
	// with Numbers
	// Search2 For finding the number of each dnase cell line:k for the given
	// search input size: n
	// For each search input line, each dnase cell line will have value 1 or 0
	// These 1 or 0's will be accumulated in dnaseCellLine2KMap
	public void findAllOverlappingDnaseIntervalsWithNumbers(
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			IntervalTreeNode node, 
			Interval interval, 
			ChromosomeName chromName,
			TIntByteMap dnaseCellLineNumber2OneorZeroMap, 
			int overlapDefinition,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap, 
			TIntObjectMap<String> fileNumber2FileNameMap) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		String cellLineName;
		String fileName;

		DnaseIntervalTreeNodeWithNumbers castedNode = null;

		if( node instanceof DnaseIntervalTreeNodeWithNumbers){

			castedNode = ( DnaseIntervalTreeNodeWithNumbers)node;
		}

		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),
				overlapDefinition)){

			try{

				// Write Annotation Overlaps to element Named File
				if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

					cellLineName = cellLineNumber2CellLineNameMap.get( castedNode.getCellLineNumber());
					fileName = fileNumber2FileNameMap.get( castedNode.getFileNumber());

					fileWriter = FileOperations.createFileWriter(
							outputFolder + Commons.DNASE_ANNOTATION_DIRECTORY + cellLineName + ".txt", true);

					bufferedWriter = new BufferedWriter( fileWriter);

					if( !dnaseCellLineNumber2OneorZeroMap.containsKey( castedNode.getCellLineNumber())){
						dnaseCellLineNumber2OneorZeroMap.put( castedNode.getCellLineNumber(), Commons.BYTE_1);
						// bufferedWriter.write("#Searched for chr" + "\t" + "given interval low" + "\t" +
						// "given interval high" + "\t" + "dnase overlap chrom name" + "\t" + "node low" + "\t" +
						// "node high" + "\t" + "node CellLineName" + "\t" + "node FileName" +
						// System.getProperty("line.separator"));
					}

					bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + cellLineName + "\t" + fileName + System.getProperty( "line.separator"));
					bufferedWriter.close();

				}// End of IF

				// Do not Write Annotation Overlaps to element Named File
				else{
					if( !dnaseCellLineNumber2OneorZeroMap.containsKey( castedNode.getCellLineNumber())){
						dnaseCellLineNumber2OneorZeroMap.put( castedNode.getCellLineNumber(), Commons.BYTE_1);
					}

				}// End of ELSE

			}catch( IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingDnaseIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					node.getLeft(), interval, chromName, dnaseCellLineNumber2OneorZeroMap, overlapDefinition,
					cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingDnaseIntervalsWithNumbers( outputFolder, writeElementBasedAnnotationFoundOverlapsMode,
					node.getRight(), interval, chromName, dnaseCellLineNumber2OneorZeroMap, overlapDefinition,
					cellLineNumber2CellLineNameMap, fileNumber2FileNameMap);
		}
	}

	// @todo Annotation with Numbers ends

	// Search2 For finding the number of each dnase cell line:k for the given
	// search input size: n
	// For each search input line, each dnase cell line will have value 1 or 0
	// These 1 or 0's will be accumulated in dnaseCellLine2KMap
	public void findAllOverlappingDnaseIntervals( String outputFolder, IntervalTreeNode node, Interval interval,
			ChromosomeName chromName, Map<String, BufferedWriter> bufferedWriterHashMap,
			List<String> dnaseCellLineNameList, Map<String, Integer> dnaseCellLine2OneorZeroMap, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		DnaseIntervalTreeNode castedNode = null;

		if( node instanceof DnaseIntervalTreeNode){

			castedNode = ( DnaseIntervalTreeNode)node;
		}

		if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),
				overlapDefinition) && dnaseCellLineNameList.contains( castedNode.getCellLineName())){
			try{

				bufferedWriter = bufferedWriterHashMap.get( castedNode.getCellLineName());

				if( bufferedWriter == null){
					fileWriter = FileOperations.createFileWriter( outputFolder + Commons.DNASE_ANNOTATION_DIRECTORY + "_" + castedNode.getCellLineName() + ".txt");
					bufferedWriter = new BufferedWriter( fileWriter);
					bufferedWriterHashMap.put( castedNode.getCellLineName(), bufferedWriter);
					bufferedWriter.write( "Searched for chr" + "\t" + "given interval low" + "\t" + "given interval high" + "\t" + "dnase overlap chrom name" + "\t" + "node low" + "\t" + "node high" + "\t" + "node CellLineName" + "\t" + "node FileName" + System.getProperty( "line.separator"));

				}

				if( dnaseCellLine2OneorZeroMap.get( castedNode.getCellLineName()) == null){
					dnaseCellLine2OneorZeroMap.put( castedNode.getCellLineName(), 1);
				}

				bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getCellLineName() + "\t" + castedNode.getFileName() + System.getProperty( "line.separator"));
				bufferedWriter.close();
			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingDnaseIntervals( outputFolder, node.getLeft(), interval, chromName, bufferedWriterHashMap,
					dnaseCellLineNameList, dnaseCellLine2OneorZeroMap, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingDnaseIntervals( outputFolder, node.getRight(), interval, chromName,
					bufferedWriterHashMap, dnaseCellLineNameList, dnaseCellLine2OneorZeroMap, overlapDefinition);

		}
	}

	// @todo
	// with Numbers
	// Empirical P Value Calculation
	// Search2 KeggPathway
	// For finding the number of each keggpathway:k for the given search input
	// size: n
	// For each search input line, each kegg pathway will have a value of 1 or 0
	// These 1 or 0's will be accumulated in keggPathway2KMap
	// with IO
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithIOWithNumbers( String outputFolder,
			int permutationNumber, IntervalTreeNode node, InputLineMinimal interval, ChromosomeName chromName,
			TIntObjectMap<BufferedWriter> permutationNumberKeggPathwayNumber2BufferedWriterMap,
			TLongObjectMap<BufferedWriter> permutationNumberUserDefinedGeneSetNumber2BufferedWriterMap,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap,
			TIntIntMap permutationNumberKeggPathwayNumber2OneorZeroMap,
			TLongIntMap permutationNumberUserDefinedGeneSetNumber2OneorZeroMap, String type,
			GeneSetAnalysisType geneSetAnalysisType, GeneSetType geneSetType, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		long permutationNumberUserDefinedGeneSetNumber = Long.MIN_VALUE;
		int permutationNumberKeggPathwayNumber = Integer.MIN_VALUE;

		Integer geneSetNumber = null;
		TIntList listofGeneSetNumberContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
					castedNode = ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)node;
				}

				try{

					// write EXON based results starts
					if( geneSetAnalysisType.isExonBasedGeneSetAnalysis()){

						// exon based kegg pathway analysis
						if( castedNode.getIntervalName().isExon()){

							listofGeneSetNumberContainingThisGeneId = geneId2ListofGeneSetNumberMap.get( castedNode.getGeneEntrezId());

							if( listofGeneSetNumberContainingThisGeneId != null){
								for( int i = 0; i < listofGeneSetNumberContainingThisGeneId.size(); i++){

									geneSetNumber = listofGeneSetNumberContainingThisGeneId.get( i);

									// KEGG Pathway starts
									if( geneSetType.isKeggPathway()){

										permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
												permutationNumber,
												geneSetNumber,
												GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

										bufferedWriter = permutationNumberKeggPathwayNumber2BufferedWriterMap.get( permutationNumberKeggPathwayNumber);

										if( bufferedWriter == null){

											fileWriter = FileOperations.createFileWriter(
													outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_exonBased_" + geneSetNumber + ".txt",
													true);

											bufferedWriter = new BufferedWriter( fileWriter);
											bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t" + "GeneEntrezId" + System.getProperty( "line.separator"));
											bufferedWriter.flush();

											permutationNumberKeggPathwayNumber2BufferedWriterMap.put(
													permutationNumberKeggPathwayNumber, bufferedWriter);

										}// End of IF: bufferedWriter is null

										if( !( permutationNumberKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
											permutationNumberKeggPathwayNumber2OneorZeroMap.put(
													permutationNumberKeggPathwayNumber, 1);
										}

										bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
										bufferedWriter.flush();

									}
									// KEGG Pathway ends

									// UserDefinedGeneSet starts
									else if( geneSetType.isUserDefinedGeneSet()){

										permutationNumberUserDefinedGeneSetNumber = generateMixedNumber(
												permutationNumber,
												Short.MIN_VALUE,
												Short.MIN_VALUE,
												geneSetNumber,
												GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);

										bufferedWriter = permutationNumberUserDefinedGeneSetNumber2BufferedWriterMap.get( permutationNumberUserDefinedGeneSetNumber);

										if( bufferedWriter == null){

											fileWriter = FileOperations.createFileWriter(
													outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_USERDEFINED_GENESET_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_exonBased_" + geneSetNumber + ".txt",
													true);

											bufferedWriter = new BufferedWriter( fileWriter);
											bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t" + "GeneEntrezId" + System.getProperty( "line.separator"));
											bufferedWriter.flush();

											permutationNumberUserDefinedGeneSetNumber2BufferedWriterMap.put(
													permutationNumberUserDefinedGeneSetNumber, bufferedWriter);

										}// End of IF: bufferedWriter is null

										if( !( permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.containsKey( permutationNumberUserDefinedGeneSetNumber))){
											permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.put(
													permutationNumberUserDefinedGeneSetNumber, 1);
										}

										bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
										bufferedWriter.flush();

									}
									// UserDefinedGeneSet ends

								}// End of For: for each GeneSet (KeggPathway or
									// UserDefinedGeneSet does not matter)
									// having
									// this gene in their gene list
							} // End of If:
								// listofGeneSetNumberContainingThisGeneId is
								// not
								// null
						}// End of If: Exon Based Analysis, Overlapped node is
							// an exon

					}
					// write EXON based results ends

					// write REGULATION based results starts
					else if( geneSetAnalysisType.isRegulationBasedGeneSetAnalysis()){
						// Regulation Based kegg pathway analysis
						if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

							listofGeneSetNumberContainingThisGeneId = geneId2ListofGeneSetNumberMap.get( castedNode.getGeneEntrezId());

							if( listofGeneSetNumberContainingThisGeneId != null){
								for( int i = 0; i < listofGeneSetNumberContainingThisGeneId.size(); i++){
									geneSetNumber = listofGeneSetNumberContainingThisGeneId.get( i);

									// KEGG Pathway starts
									if( geneSetType.isKeggPathway()){
										permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
												permutationNumber,
												geneSetNumber,
												GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

										bufferedWriter = permutationNumberKeggPathwayNumber2BufferedWriterMap.get( permutationNumberKeggPathwayNumber);

										if( bufferedWriter == null){

											fileWriter = FileOperations.createFileWriter(
													outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_regulationBased_" + geneSetNumber + ".txt",
													true);

											bufferedWriter = new BufferedWriter( fileWriter);
											bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t" + "GeneEntrezId" + System.getProperty( "line.separator"));
											bufferedWriter.flush();

											permutationNumberKeggPathwayNumber2BufferedWriterMap.put(
													permutationNumberKeggPathwayNumber, bufferedWriter);

										}// End of IF: bufferedWriter is null

										if( !( permutationNumberKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
											permutationNumberKeggPathwayNumber2OneorZeroMap.put(
													permutationNumberKeggPathwayNumber, 1);
										}

										bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
										bufferedWriter.flush();

									}
									// KEGG Pathway ends

									// UserDefinedGeneSet starts
									else if( geneSetType.isUserDefinedGeneSet()){
										permutationNumberUserDefinedGeneSetNumber = generateMixedNumber(
												permutationNumber,
												Short.MIN_VALUE,
												Short.MIN_VALUE,
												geneSetNumber,
												GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);

										bufferedWriter = permutationNumberUserDefinedGeneSetNumber2BufferedWriterMap.get( permutationNumberUserDefinedGeneSetNumber);

										if( bufferedWriter == null){

											fileWriter = FileOperations.createFileWriter(
													outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_USERDEFINED_GENESET_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_regulationBased_" + geneSetNumber + ".txt",
													true);

											bufferedWriter = new BufferedWriter( fileWriter);
											bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t" + "GeneEntrezId" + System.getProperty( "line.separator"));
											bufferedWriter.flush();

											permutationNumberUserDefinedGeneSetNumber2BufferedWriterMap.put(
													permutationNumberUserDefinedGeneSetNumber, bufferedWriter);

										}// End of IF: bufferedWriter is null

										if( !( permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.containsKey( permutationNumberUserDefinedGeneSetNumber))){
											permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.put(
													permutationNumberUserDefinedGeneSetNumber, 1);
										}

										bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
										bufferedWriter.flush();

									}
									// UserDefinedGeneSet ends

								}// End of For: for each GeneSet (KeggPathway or
									// UserDefinedGeneSet does not matter)
									// having
									// this gene in their gene list
							} // End of If:
								// listofGeneSetNumberContainingThisGeneId is
								// not
								// null
						}// End of If: Regulation Based Analysis, Overlapped
							// node is an intron, 5P1, 5P2, 3P1, 3P2

					}
					// write REGULATION based results ends

					// write ALL BASED results starts
					else{
						listofGeneSetNumberContainingThisGeneId = geneId2ListofGeneSetNumberMap.get( castedNode.getGeneEntrezId());

						if( listofGeneSetNumberContainingThisGeneId != null){
							for( int i = 0; i < listofGeneSetNumberContainingThisGeneId.size(); i++){
								geneSetNumber = listofGeneSetNumberContainingThisGeneId.get( i);

								// KEGG Pathway starts
								if( geneSetType.isKeggPathway()){
									permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
											permutationNumber,
											geneSetNumber,
											GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									bufferedWriter = permutationNumberKeggPathwayNumber2BufferedWriterMap.get( permutationNumberKeggPathwayNumber);

									if( bufferedWriter == null){

										fileWriter = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_all_" + geneSetNumber + ".txt",
												true);

										bufferedWriter = new BufferedWriter( fileWriter);
										bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t" + "GeneEntrezId" + System.getProperty( "line.separator"));
										bufferedWriter.flush();

										permutationNumberKeggPathwayNumber2BufferedWriterMap.put(
												permutationNumberKeggPathwayNumber, bufferedWriter);

									}// End of IF: bufferedWriter is null

									if( !( permutationNumberKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
										permutationNumberKeggPathwayNumber2OneorZeroMap.put(
												permutationNumberKeggPathwayNumber, 1);
									}

									bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
									bufferedWriter.flush();

								}
								// KEGG Pathway ends

								// UserDefinedGeneSet starts
								else if( geneSetType.isUserDefinedGeneSet()){
									permutationNumberUserDefinedGeneSetNumber = generateMixedNumber(
											permutationNumber,
											Short.MIN_VALUE,
											Short.MIN_VALUE,
											geneSetNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);

									bufferedWriter = permutationNumberUserDefinedGeneSetNumber2BufferedWriterMap.get( permutationNumberUserDefinedGeneSetNumber);

									if( bufferedWriter == null){

										fileWriter = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_USERDEFINED_GENESET_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_all_" + geneSetNumber + ".txt",
												true);

										bufferedWriter = new BufferedWriter( fileWriter);
										bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t" + "GeneEntrezId" + System.getProperty( "line.separator"));
										bufferedWriter.flush();

										permutationNumberUserDefinedGeneSetNumber2BufferedWriterMap.put(
												permutationNumberUserDefinedGeneSetNumber, bufferedWriter);

									}// End of IF: bufferedWriter is null

									if( !( permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.containsKey( permutationNumberUserDefinedGeneSetNumber))){
										permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.put(
												permutationNumberUserDefinedGeneSetNumber, 1);
									}

									bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
									bufferedWriter.flush();

								}
								// UserDefinedGeneSet ends

							}// End of For: for each GeneSet (KeggPathway or
								// UserDefinedGeneSet does not matter) having
								// this
								// gene in their gene list
						} // End of If: listofGeneSetNumberContainingThisGeneId
							// is not null

					}
					// write ALL BASED results ends

				}catch( IOException e){
					if( GlanetRunner.shouldLog())logger.error( e.toString());
				}
			}
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithIOWithNumbers( outputFolder, permutationNumber,
					node.getLeft(), interval, chromName, permutationNumberKeggPathwayNumber2BufferedWriterMap,
					permutationNumberUserDefinedGeneSetNumber2BufferedWriterMap, geneId2ListofGeneSetNumberMap,
					permutationNumberKeggPathwayNumber2OneorZeroMap,
					permutationNumberUserDefinedGeneSetNumber2OneorZeroMap, type, geneSetAnalysisType, geneSetType,
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithIOWithNumbers( outputFolder, permutationNumber,
					node.getRight(), interval, chromName, permutationNumberKeggPathwayNumber2BufferedWriterMap,
					permutationNumberUserDefinedGeneSetNumber2BufferedWriterMap, geneId2ListofGeneSetNumberMap,
					permutationNumberKeggPathwayNumber2OneorZeroMap,
					permutationNumberUserDefinedGeneSetNumber2OneorZeroMap, type, geneSetAnalysisType, geneSetType,
					overlapDefinition);

		}

	}

	// with Numbers
	// @todo

	// Empirical P Value Calculation
	// Search2 KeggPathway
	// For finding the number of each keggpathway:k for the given search input
	// size: n
	// For each search input line, each kegg pathway will have a value of 1 or 0
	// These 1 or 0's will be accumulated in keggPathway2KMap
	// with IO
	public void findAllOverlappingUcscRefSeqGenesIntervals( String outputFolder, int permutationNumber,
			IntervalTreeNode node, Interval interval, ChromosomeName chromName,
			Map<String, BufferedWriter> bufferedWriterHashMap, Map<String, List<String>> geneId2KeggPathwayMap,
			Map<String, Integer> permutationNumberKeggPathway2OneorZeroMap, String type,
			KeggPathwayAnalysisType keggPathwayAnalysisType, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		String permutationNumberKeggPathwayName = null;
		String keggPathwayName = null;
		List<String> keggPathWayListContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNode castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNode){
					castedNode = ( UcscRefSeqGeneIntervalTreeNode)node;
				}

				try{

					// write exon based results
					if( keggPathwayAnalysisType.isExonBasedKeggPathwayAnalysis()){

						// exon based kegg pathway analysis
						if( castedNode.getIntervalName().isExon()){

							keggPathWayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

							if( keggPathWayListContainingThisGeneId != null){
								for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){
									keggPathwayName = keggPathWayListContainingThisGeneId.get( i);
									permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

									bufferedWriter = bufferedWriterHashMap.get( permutationNumberKeggPathwayName);

									if( bufferedWriter == null){

										fileWriter = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_exonBased_" + keggPathwayName + ".txt",
												true);
										bufferedWriter = new BufferedWriter( fileWriter);
										bufferedWriterHashMap.put( permutationNumberKeggPathwayName, bufferedWriter);

									}

									if( permutationNumberKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
										permutationNumberKeggPathway2OneorZeroMap.put(
												permutationNumberKeggPathwayName, 1);
									}

									bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
									bufferedWriter.flush();

								}// End of For: for all keggpathways having this
									// gene in their gene list
							} // End of If: keggPathWayListContainingThisGeneId
								// is not null
						}// End of If: Exon Based Kegg Pathway Analysis,
							// Overlapped node is an exon

					}
					// write regulation based results
					else if( keggPathwayAnalysisType.isRegulationBasedKeggPathwayAnalysis()){
						// Regulation Based kegg pathway analysis
						if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

							keggPathWayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

							if( keggPathWayListContainingThisGeneId != null){
								for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){
									keggPathwayName = keggPathWayListContainingThisGeneId.get( i);
									permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

									bufferedWriter = bufferedWriterHashMap.get( permutationNumberKeggPathwayName);

									if( bufferedWriter == null){

										fileWriter = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_regulationBased_" + keggPathwayName + ".txt",
												true);
										bufferedWriter = new BufferedWriter( fileWriter);
										bufferedWriterHashMap.put( permutationNumberKeggPathwayName, bufferedWriter);

									}

									if( permutationNumberKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
										permutationNumberKeggPathway2OneorZeroMap.put(
												permutationNumberKeggPathwayName, 1);
									}

									bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
									bufferedWriter.flush();

								}// End of For: for all kegg pathways having
									// this gene in their gene list
							} // End of If: keggPathWayListContainingThisGeneId
								// is not null
						}// End of If: Regulation Based kegg pathway Analysis,
							// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

					}
					// write all results
					else{
						keggPathWayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

						if( keggPathWayListContainingThisGeneId != null){
							for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){
								keggPathwayName = keggPathWayListContainingThisGeneId.get( i);
								permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

								bufferedWriter = bufferedWriterHashMap.get( permutationNumberKeggPathwayName);

								if( bufferedWriter == null){

									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_all_" + keggPathwayName + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);
									bufferedWriterHashMap.put( permutationNumberKeggPathwayName, bufferedWriter);

								}

								if( permutationNumberKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
									permutationNumberKeggPathway2OneorZeroMap.put( permutationNumberKeggPathwayName, 1);
								}

								bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
								bufferedWriter.flush();

							}// End of For: for all kegg pathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null

					}

				}catch( IOException e){
					if( GlanetRunner.shouldLog())logger.error( e.toString());
				}
			}
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervals( outputFolder, permutationNumber, node.getLeft(), interval,
					chromName, bufferedWriterHashMap, geneId2KeggPathwayMap, permutationNumberKeggPathway2OneorZeroMap,
					type, keggPathwayAnalysisType, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervals( outputFolder, permutationNumber, node.getRight(), interval,
					chromName, bufferedWriterHashMap, geneId2KeggPathwayMap, permutationNumberKeggPathway2OneorZeroMap,
					type, keggPathwayAnalysisType, overlapDefinition);

		}

	}
	
	
	//Modified 27 July 2015
	// 8 July 2015
	// WITHOUT IO
	// WITH Numbers
	// WITHOUT ZScores
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
			IntervalTreeNode node, 
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap,
			TIntByteMap keggPathwayNumber2PermutationOneorZeroMap,
			TIntByteMap userDefinedGeneSetNumber2PermutationOneorZeroMap,
			TIntByteMap geneNumber2PermutationOneorZeroMap, 
			String type, 
			GeneSetAnalysisType geneSetAnalysisType,
			GeneSetType geneSetType, 
			int overlapDefinition) {

		int keggPathwayNumber = Integer.MIN_VALUE;
		int userDefinedGeneSetNumber = Integer.MIN_VALUE;
		int geneNumber = Integer.MIN_VALUE;

		Integer geneSetNumber = null;
		TIntList ListofGeneSetNumberContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
					castedNode = ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)node;
				}

				// Gene Enrichment
				if( geneSetAnalysisType.isNoGeneSetAnalysIsTypeDefined()){
						geneNumber = castedNode.getGeneEntrezId();

					if( !(geneNumber2PermutationOneorZeroMap.containsKey(geneNumber))){
						geneNumber2PermutationOneorZeroMap.put(geneNumber, Commons.BYTE_1);
					}

				}

				// write EXON based results starts
				else if( geneSetAnalysisType.isExonBasedGeneSetAnalysis()){

					// EXON based KEGG Pathway analysis
					if( castedNode.getIntervalName().isExon()){

						//@todo Must be TIntList instead of TShortList
						ListofGeneSetNumberContainingThisGeneId = geneId2ListofGeneSetNumberMap.get(castedNode.getGeneEntrezId());

						if( ListofGeneSetNumberContainingThisGeneId != null){
							for( int i = 0; i < ListofGeneSetNumberContainingThisGeneId.size(); i++){

								geneSetNumber = ListofGeneSetNumberContainingThisGeneId.get( i);

								// KEGGPathway starts
								if( geneSetType.isKeggPathway()){
									keggPathwayNumber = geneSetNumber;
											
									if( !(keggPathwayNumber2PermutationOneorZeroMap.containsKey(keggPathwayNumber))){
										keggPathwayNumber2PermutationOneorZeroMap.put(keggPathwayNumber, Commons.BYTE_1);
									}
								}
								// KEGGPathway ends

								// UserDefinedGeneSet starts
								else if( geneSetType.isUserDefinedGeneSet()){
									userDefinedGeneSetNumber = geneSetNumber;
											
									if( !(userDefinedGeneSetNumber2PermutationOneorZeroMap.containsKey(userDefinedGeneSetNumber))){
										userDefinedGeneSetNumber2PermutationOneorZeroMap.put(
												userDefinedGeneSetNumber, Commons.BYTE_1);
									}
								}
								// UserDefinedGeneSet ends

							}// End of For: for all genesets having this gene in their gene list
						
						} // End of If: geneSetListContainingThisGeneId is not null
						
					}// End of If: Exon Based GeneSet Analysis, Overlapped node is an exon

				}
				// write EXON based results ends

				// write REGULATION based results
				else if( geneSetAnalysisType.isRegulationBasedGeneSetAnalysis()){
					// Regulation Based kegg pathway analysis
					if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

						ListofGeneSetNumberContainingThisGeneId = geneId2ListofGeneSetNumberMap.get( castedNode.getGeneEntrezId());

						if( ListofGeneSetNumberContainingThisGeneId != null){
							for( int i = 0; i < ListofGeneSetNumberContainingThisGeneId.size(); i++){

								geneSetNumber = ListofGeneSetNumberContainingThisGeneId.get( i);

								// Kegg Pathway starts
								if( geneSetType.isKeggPathway()){

									keggPathwayNumber = geneSetNumber;
								
									if( !(keggPathwayNumber2PermutationOneorZeroMap.containsKey(keggPathwayNumber))){
										keggPathwayNumber2PermutationOneorZeroMap.put(keggPathwayNumber, Commons.BYTE_1);
									}

								}
								// Kegg Pathway ends

								// UserDefinedGeneSet starts
								else if( geneSetType.isUserDefinedGeneSet()){
									
									userDefinedGeneSetNumber = geneSetNumber;
									
									if( !(userDefinedGeneSetNumber2PermutationOneorZeroMap.containsKey(userDefinedGeneSetNumber))){
										userDefinedGeneSetNumber2PermutationOneorZeroMap.put(userDefinedGeneSetNumber, Commons.BYTE_1);
									}

								}
								// UserDefinedGeneSet ends

							}// End of For: for all gene sets having this gene in their gene list
						} // End of If: geneSetListContainingThisGeneId is not null
					}// End of If: Regulation Based gene set Analysis,
					// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

				}
				// write REGULATION based results ends

				// write ALL based results starts
				else if( geneSetAnalysisType.isAllBasedGeneSetAnalysis()){

					ListofGeneSetNumberContainingThisGeneId = geneId2ListofGeneSetNumberMap.get( castedNode.getGeneEntrezId());

					if( ListofGeneSetNumberContainingThisGeneId != null){
						for( int i = 0; i < ListofGeneSetNumberContainingThisGeneId.size(); i++){

							geneSetNumber = ListofGeneSetNumberContainingThisGeneId.get( i);

							// Kegg Pathway starts
							if( geneSetType.isKeggPathway()){
								keggPathwayNumber = geneSetNumber;
								
								if( !(keggPathwayNumber2PermutationOneorZeroMap.containsKey(keggPathwayNumber))){
									keggPathwayNumber2PermutationOneorZeroMap.put(keggPathwayNumber, Commons.BYTE_1);
								}

							}
							// Kegg Pathway ends

							// UserDefinedGeneSet starts
							else if( geneSetType.isUserDefinedGeneSet()){

								userDefinedGeneSetNumber = geneSetNumber;
								
								if( !(userDefinedGeneSetNumber2PermutationOneorZeroMap.containsKey(userDefinedGeneSetNumber))){
									userDefinedGeneSetNumber2PermutationOneorZeroMap.put(userDefinedGeneSetNumber, Commons.BYTE_1);
								}

							}
							// UserDefinedGeneSet ends

						}// End of For: for all genesets having this gene in their gene list
					} // End of If: geneSetListContainingThisGeneId is not null

				}
				// write ALL based results ends

			}// End of if: there is overlap
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
					node.getLeft(),
					interval, 
					chromName, 
					geneId2ListofGeneSetNumberMap,
					keggPathwayNumber2PermutationOneorZeroMap,
					userDefinedGeneSetNumber2PermutationOneorZeroMap, 
					geneNumber2PermutationOneorZeroMap,
					type, 
					geneSetAnalysisType, 
					geneSetType, 
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
					node.getRight(),
					interval, 
					chromName, 
					geneId2ListofGeneSetNumberMap,
					keggPathwayNumber2PermutationOneorZeroMap,
					userDefinedGeneSetNumber2PermutationOneorZeroMap, 
					geneNumber2PermutationOneorZeroMap,
					type, 
					geneSetAnalysisType, 
					geneSetType, 
					overlapDefinition);
		}

	}
	//8 July 2015
	
	
	//11 NOV 2015 starts
	public static void fillMapofOverlappingNodeList(
			int permutationNumber,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap,
			UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode,
			GeneSetType geneSetType,
			TIntObjectMap<List<IntervalTreeNode>> permutationNumberKeggPathwayNumber2OverlappingNodeListMap,
			TLongObjectMap<List<IntervalTreeNode>> permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap){
		
		int permutationNumberKeggPathwayNumber = Integer.MIN_VALUE;
		long permutationNumberUserDefinedGeneSetNumber = Long.MIN_VALUE;
		
		Integer geneSetNumber = null;
		TIntList ListofGeneSetNumberContainingThisGeneId = null;	
		List<IntervalTreeNode> overlappingNodeList = null;
		

		ListofGeneSetNumberContainingThisGeneId = geneId2ListofGeneSetNumberMap.get(castedNode.getGeneEntrezId());

		if( ListofGeneSetNumberContainingThisGeneId != null){
			
			for( int i = 0; i < ListofGeneSetNumberContainingThisGeneId.size(); i++){

				geneSetNumber = ListofGeneSetNumberContainingThisGeneId.get(i);
				
				switch(geneSetType) {
				
					case KEGGPATHWAY:
						//KEGG Pathway starts
						permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
								permutationNumber,
								geneSetNumber,
								GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

						// Debug starts
						if( permutationNumberKeggPathwayNumber < 0){
							System.out.println( "there is a situation 4");
						}
						// Debug end

						overlappingNodeList = permutationNumberKeggPathwayNumber2OverlappingNodeListMap.get(permutationNumberKeggPathwayNumber);
						
						//Pay attention: you have to add castedNode to the list
						//Further on you will need tforHistoneNumber in constructAnIntervalTreeWithNonOverlappingNodes method
						if (overlappingNodeList == null){
							overlappingNodeList = new ArrayList<IntervalTreeNode>();
							overlappingNodeList.add(castedNode);
							permutationNumberKeggPathwayNumber2OverlappingNodeListMap.put(permutationNumberKeggPathwayNumber, overlappingNodeList);
						}else{
							overlappingNodeList.add(castedNode);
						}
						// Kegg Pathway ends
						break;
						
					case USERDEFINEDGENESET:
						// UserDefinedGeneSet starts
						permutationNumberUserDefinedGeneSetNumber = generateMixedNumber(
								permutationNumber,
								Short.MIN_VALUE,
								Short.MIN_VALUE,
								geneSetNumber,
								GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);

						// Debug starts
						if( permutationNumberUserDefinedGeneSetNumber < 0){
							System.out.println( "there is a situation 5");
						}
						// Debug end

						overlappingNodeList = permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap.get(permutationNumberUserDefinedGeneSetNumber);
						
						//Pay attention: you have to add castedNode to the list
						//Further on you will need tforHistoneNumber in constructAnIntervalTreeWithNonOverlappingNodes method
						if (overlappingNodeList == null){
							overlappingNodeList = new ArrayList<IntervalTreeNode>();
							overlappingNodeList.add(castedNode);
							permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap.put(permutationNumberUserDefinedGeneSetNumber, overlappingNodeList);
						}else{
							overlappingNodeList.add(castedNode);
						}
						// UserDefinedGeneSet ends
						break;
						
					default:
						break;
					
				}//End of SWITCH geneSetType

			}// End of FOR: for all genesets having this gene in their gene list
		} // End of IF: geneSetListContainingThisGeneId is not null
		
	}
	//11 NOV 2015 ends
	
	//10 NOV 2015 starts
	//Without IO
	//With Numbers
	//With overlappingNodeList
	//For AssociationMeasureType NUMBER_OF_OVERLAPPING_BASES
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
			int permutationNumber,
			IntervalTreeNode node, 
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap,
			TIntObjectMap<List<IntervalTreeNode>> permutationNumberKeggPathwayNumber2OverlappingNodeListMap,
			TLongObjectMap<List<IntervalTreeNode>> permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap,
			TLongObjectMap<List<IntervalTreeNode>> permutationNumberGeneNumber2OverlappingNodeListMap, 
			String type, 
			GeneSetAnalysisType geneSetAnalysisType,
			GeneSetType geneSetType, 
			int overlapDefinition) {

		long permutationNumberGeneNumber = Long.MIN_VALUE;
		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;
		List<IntervalTreeNode> overlappingNodeList = null;
		
		if( Commons.NCBI_GENE_ID.equals( type)){
			
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
					//castedNode = ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)node;
					//castedNode must be newly created and there must be no color assigned at first.
					castedNode = new UcscRefSeqGeneIntervalTreeNodeWithNumbers(
							node.getChromName(),
							node.getLow(),
							node.getHigh(),
							((UcscRefSeqGeneIntervalTreeNodeWithNumbers) node).getGeneEntrezId(),
							((UcscRefSeqGeneIntervalTreeNodeWithNumbers) node).getIntervalName(),
							((UcscRefSeqGeneIntervalTreeNodeWithNumbers) node).getIntervalNumber(),
							NodeType.ORIGINAL);
					
				}//End of IF

				switch(geneSetAnalysisType){
				
					case NO_GENESET_ANALYSIS_TYPE_IS_DEFINED:
						
						//Maybe we should also check geneSetType
						// Gene Enrichment
						permutationNumberGeneNumber = generatePermutationNumberGeneNumber( 
								permutationNumber,
								castedNode.getGeneEntrezId(),
								GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGIT_PERMUTATIONNUMBER_10DIGIT_GENENUMBER);
						
						overlappingNodeList = permutationNumberGeneNumber2OverlappingNodeListMap.get(permutationNumberGeneNumber);
						
						//Pay attention: you have to add castedNode to the list
						//Further on you will need tforHistoneNumber in constructAnIntervalTreeWithNonOverlappingNodes method
						if (overlappingNodeList == null){
							overlappingNodeList = new ArrayList<IntervalTreeNode>();
							overlappingNodeList.add(castedNode);
							permutationNumberGeneNumber2OverlappingNodeListMap.put(permutationNumberGeneNumber, overlappingNodeList);
						}else{
							overlappingNodeList.add(castedNode);
						}					
						break;
						
					case EXONBASEDGENESETANALYSIS:
						//EXON Based GeneSet Analysis
						if( castedNode.getIntervalName().isExon()){
							
							//11 NOV 2015 starts
							fillMapofOverlappingNodeList(
									permutationNumber,
									geneId2ListofGeneSetNumberMap,
									castedNode,
									geneSetType,
									permutationNumberKeggPathwayNumber2OverlappingNodeListMap,
									permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap);
							//11 NOV 2015 ends

						}// End of IF: Exon Based GeneSet Analysis, Overlapped node is an exon
						break;
						
					case REGULATIONBASEDGENESETANALYSIS:
						// Regulation Based  GeneSet Analysis
						if( castedNode.getIntervalName().isIntron() || 
								castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || 
								castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

							//11 NOV 2015 starts
							fillMapofOverlappingNodeList(
									permutationNumber,
									geneId2ListofGeneSetNumberMap,
									castedNode,
									geneSetType,
									permutationNumberKeggPathwayNumber2OverlappingNodeListMap,
									permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap);
							//11 NOV 2015 ends

						}// End of IF: Regulation Based GeneSet Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2
						break;
						
					case ALLBASEDGENESETANALYSIS:
						// ALL Based GeneSet Analysis
						//11 NOV 2015 starts
						fillMapofOverlappingNodeList(
								permutationNumber,
								geneId2ListofGeneSetNumberMap,
								castedNode,
								geneSetType,
								permutationNumberKeggPathwayNumber2OverlappingNodeListMap,
								permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap);
						//11 NOV 2015 ends
						break;
				
				}//End of SWITCH geneSetAnalysisType

			}// End of IF there is overlap
			
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
					permutationNumber, 
					node.getLeft(),
					interval, 
					chromName, 
					geneId2ListofGeneSetNumberMap,
					permutationNumberKeggPathwayNumber2OverlappingNodeListMap,
					permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap, 
					permutationNumberGeneNumber2OverlappingNodeListMap,
					type, 
					geneSetAnalysisType, 
					geneSetType, 
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
					permutationNumber, 
					node.getRight(),
					interval, 
					chromName, 
					geneId2ListofGeneSetNumberMap,
					permutationNumberKeggPathwayNumber2OverlappingNodeListMap,
					permutationNumberUserDefinedGeneSetNumber2OverlappingNodeListMap, 
					permutationNumberGeneNumber2OverlappingNodeListMap,
					type, 
					geneSetAnalysisType, 
					geneSetType, 
					overlapDefinition);
		}
		
	}	
	//10 NOV 2015 ends
	

	// 23 OCT 2014
	// Empirical P Value Calculation
	// Search2 KeggPathway
	// For finding the number of each keggpathway:k for the given search input
	// size: n
	// For each search input line, each kegg pathway will have a value of 1 or 0
	// These 1 or 0's will be accumulated in keggPathway2KMap
	// without IO
	// with Numbers
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
			int permutationNumber,
			IntervalTreeNode node, 
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap,
			TIntIntMap permutationNumberKeggPathwayNumber2OneorZeroMap,
			TLongIntMap permutationNumberUserDefinedGeneSetNumber2OneorZeroMap,
			TLongIntMap permutationNumberGeneNumber2OneorZeroMap, 
			String type, 
			GeneSetAnalysisType geneSetAnalysisType,
			GeneSetType geneSetType, 
			int overlapDefinition) {

		int permutationNumberKeggPathwayNumber = Integer.MIN_VALUE;
		long permutationNumberUserDefinedGeneSetNumber = Long.MIN_VALUE;
		long permutationNumberGeneNumber = Long.MIN_VALUE;

		Integer geneSetNumber = null;
		TIntList ListofGeneSetNumberContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
					castedNode = ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)node;
				}

				// Gene Enrichment
				if( geneSetAnalysisType.isNoGeneSetAnalysIsTypeDefined()){
					permutationNumberGeneNumber = generatePermutationNumberGeneNumber( permutationNumber,
							castedNode.getGeneEntrezId(),
							GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGIT_PERMUTATIONNUMBER_10DIGIT_GENENUMBER);

					if( !( permutationNumberGeneNumber2OneorZeroMap.containsKey( permutationNumberGeneNumber))){
						permutationNumberGeneNumber2OneorZeroMap.put( permutationNumberGeneNumber, 1);
					}

				}

				// write EXON based results starts
				else if( geneSetAnalysisType.isExonBasedGeneSetAnalysis()){

					// exon based kegg pathway analysis
					if( castedNode.getIntervalName().isExon()){

						ListofGeneSetNumberContainingThisGeneId = geneId2ListofGeneSetNumberMap.get(castedNode.getGeneEntrezId());

						if( ListofGeneSetNumberContainingThisGeneId != null){
							for( int i = 0; i < ListofGeneSetNumberContainingThisGeneId.size(); i++){

								geneSetNumber = ListofGeneSetNumberContainingThisGeneId.get( i);

								// Kegg Pathway starts
								if( geneSetType.isKeggPathway()){
									permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
											permutationNumber,
											geneSetNumber,
											GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									// Debug starts
									if( permutationNumberKeggPathwayNumber < 0){
										System.out.println( "there is a situation 4");
									}
									// Debug end

									if( !( permutationNumberKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
										permutationNumberKeggPathwayNumber2OneorZeroMap.put(
												permutationNumberKeggPathwayNumber, 1);
									}
								}
								// Kegg Pathway ends

								// UserDefinedGeneSet starts
								else if( geneSetType.isUserDefinedGeneSet()){
									permutationNumberUserDefinedGeneSetNumber = generateMixedNumber(
											permutationNumber,
											Short.MIN_VALUE,
											Short.MIN_VALUE,
											geneSetNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);

									// Debug starts
									if( permutationNumberUserDefinedGeneSetNumber < 0){
										System.out.println( "there is a situation 5");
									}
									// Debug end

									if( !( permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.containsKey( permutationNumberUserDefinedGeneSetNumber))){
										permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.put(
												permutationNumberUserDefinedGeneSetNumber, 1);
									}
								}
								// UserDefinedGeneSet ends

							}// End of For: for all genesets having this gene in
								// their gene list
						} // End of If: geneSetListContainingThisGeneId is not
							// null
					}// End of If: Exon Based GeneSet Analysis, Overlapped node
						// is an exon

				}
				// write EXON based results ends

				// write REGULATION based results
				else if( geneSetAnalysisType.isRegulationBasedGeneSetAnalysis()){
					// Regulation Based kegg pathway analysis
					if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

						ListofGeneSetNumberContainingThisGeneId = geneId2ListofGeneSetNumberMap.get( castedNode.getGeneEntrezId());

						if( ListofGeneSetNumberContainingThisGeneId != null){
							for( int i = 0; i < ListofGeneSetNumberContainingThisGeneId.size(); i++){

								geneSetNumber = ListofGeneSetNumberContainingThisGeneId.get( i);

								// Kegg Pathway starts
								if( geneSetType.isKeggPathway()){

									permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
											permutationNumber,
											geneSetNumber,
											GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

									// Debug starts
									if( permutationNumberKeggPathwayNumber < 0){
										System.out.println( "there is a situation 6");
									}
									// Debug end

									if( !( permutationNumberKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
										permutationNumberKeggPathwayNumber2OneorZeroMap.put(
												permutationNumberKeggPathwayNumber, 1);
									}

								}
								// Kegg Pathway ends

								// UserDefinedGeneSet starts
								else if( geneSetType.isUserDefinedGeneSet()){
									permutationNumberUserDefinedGeneSetNumber = generateMixedNumber(
											permutationNumber,
											Short.MIN_VALUE,
											Short.MIN_VALUE,
											geneSetNumber,
											GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);

									// Debug starts
									if( permutationNumberUserDefinedGeneSetNumber < 0){
										System.out.println( "there is a situation 7");
									}
									// Debug end

									if( !( permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.containsKey( permutationNumberUserDefinedGeneSetNumber))){
										permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.put(
												permutationNumberUserDefinedGeneSetNumber, 1);
									}

								}
								// UserDefinedGeneSet ends

							}// End of For: for all gene sets having this gene
								// in their gene list
						} // End of If: geneSetListContainingThisGeneId is not
							// null
					}// End of If: Regulation Based gene set Analysis,
						// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

				}
				// write REGULATION based results ends

				// write ALL based results starts
				else if( geneSetAnalysisType.isAllBasedGeneSetAnalysis()){

					ListofGeneSetNumberContainingThisGeneId = geneId2ListofGeneSetNumberMap.get( castedNode.getGeneEntrezId());

					if( ListofGeneSetNumberContainingThisGeneId != null){
						for( int i = 0; i < ListofGeneSetNumberContainingThisGeneId.size(); i++){

							geneSetNumber = ListofGeneSetNumberContainingThisGeneId.get( i);

							// Kegg Pathway starts
							if( geneSetType.isKeggPathway()){
								permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
										permutationNumber,
										geneSetNumber,
										GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								// Debug starts
								if( permutationNumberKeggPathwayNumber < 0){
									System.out.println( "there is a situation 8");
								}
								// Debug end

								if( !( permutationNumberKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
									permutationNumberKeggPathwayNumber2OneorZeroMap.put(
											permutationNumberKeggPathwayNumber, 1);
								}

							}
							// Kegg Pathway ends

							// UserDefinedGeneSet starts
							else if( geneSetType.isUserDefinedGeneSet()){

								permutationNumberUserDefinedGeneSetNumber = generateMixedNumber(
										permutationNumber,
										Short.MIN_VALUE,
										Short.MIN_VALUE,
										geneSetNumber,
										GeneratedMixedNumberDescriptionOrderLength.LONG_7DIGITS_PERMUTATIONNUMBER_10DIGITS_USERDEFINEDGENESETNUMBER);

								// Debug starts
								if( permutationNumberUserDefinedGeneSetNumber < 0){
									System.out.println( "there is a situation 9");
								}
								// Debug end

								if( !( permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.containsKey( permutationNumberUserDefinedGeneSetNumber))){
									permutationNumberUserDefinedGeneSetNumber2OneorZeroMap.put(
											permutationNumberUserDefinedGeneSetNumber, 1);
								}

							}
							// UserDefinedGeneSet ends

						}// End of For: for all genesets having this gene in
							// their gene list
					} // End of If: geneSetListContainingThisGeneId is not null

				}
				// write ALL based results ends

			}// End of if: there is overlap
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers( permutationNumber, node.getLeft(),
					interval, chromName, geneId2ListofGeneSetNumberMap,
					permutationNumberKeggPathwayNumber2OneorZeroMap,
					permutationNumberUserDefinedGeneSetNumber2OneorZeroMap, permutationNumberGeneNumber2OneorZeroMap,
					type, geneSetAnalysisType, geneSetType, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers( permutationNumber, node.getRight(),
					interval, chromName, geneId2ListofGeneSetNumberMap,
					permutationNumberKeggPathwayNumber2OneorZeroMap,
					permutationNumberUserDefinedGeneSetNumber2OneorZeroMap, permutationNumberGeneNumber2OneorZeroMap,
					type, geneSetAnalysisType, geneSetType, overlapDefinition);
		}

	}

	// @todo

	// Empirical P Value Calculation
	// Search2 KeggPathway
	// For finding the number of each keggpathway:k for the given search input
	// size: n
	// For each search input line, each kegg pathway will have a value of 1 or 0
	// These 1 or 0's will be accumulated in keggPathway2KMap
	// without IO
	public void findAllOverlappingUcscRefSeqGenesIntervals( int permutationNumber, IntervalTreeNode node,
			Interval interval, ChromosomeName chromName, Map<String, List<String>> geneId2KeggPathwayMap,
			Map<String, Integer> permutationNumberKeggPathway2OneorZeroMap, String type,
			KeggPathwayAnalysisType keggPathwayAnalysisType, int overlapDefinition) {

		String permutationNumberKeggPathwayName = null;
		String keggPathwayName = null;
		List<String> keggPathWayListContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNode castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNode){
					castedNode = ( UcscRefSeqGeneIntervalTreeNode)node;
				}

				// write exon based results
				if( keggPathwayAnalysisType.isExonBasedKeggPathwayAnalysis()){

					// exon based kegg pathway analysis
					if( castedNode.getIntervalName().isExon()){

						keggPathWayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

						if( keggPathWayListContainingThisGeneId != null){
							for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){

								keggPathwayName = keggPathWayListContainingThisGeneId.get( i);
								permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

								if( permutationNumberKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
									permutationNumberKeggPathway2OneorZeroMap.put( permutationNumberKeggPathwayName, 1);
								}

							}// End of For: for all keggpathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Exon Based Kegg Pathway Analysis, Overlapped
						// node is an exon

				}
				// write regulation based results
				else if( keggPathwayAnalysisType.isRegulationBasedKeggPathwayAnalysis()){
					// Regulation Based kegg pathway analysis
					if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

						keggPathWayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

						if( keggPathWayListContainingThisGeneId != null){
							for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){
								keggPathwayName = keggPathWayListContainingThisGeneId.get( i);
								permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

								if( permutationNumberKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
									permutationNumberKeggPathway2OneorZeroMap.put( permutationNumberKeggPathwayName, 1);
								}

							}// End of For: for all kegg pathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Regulation Based kegg pathway Analysis,
						// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

				}// regulation based kegg pathway analysis
					// write all results
				else{
					keggPathWayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

					if( keggPathWayListContainingThisGeneId != null){
						for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){
							keggPathwayName = keggPathWayListContainingThisGeneId.get( i);
							permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

							if( permutationNumberKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
								permutationNumberKeggPathway2OneorZeroMap.put( permutationNumberKeggPathwayName, 1);
							}

						}// End of For: for all kegg pathways having this gene
							// in their gene list
					} // End of If: keggPathWayListContainingThisGeneId is not
						// null

				}// all kegg pathway analysis

			}// End of if: there is overlap
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervals( permutationNumber, node.getLeft(), interval, chromName,
					geneId2KeggPathwayMap, permutationNumberKeggPathway2OneorZeroMap, type, keggPathwayAnalysisType,
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervals( permutationNumber, node.getRight(), interval, chromName,
					geneId2KeggPathwayMap, permutationNumberKeggPathway2OneorZeroMap, type, keggPathwayAnalysisType,
					overlapDefinition);
		}

	}

	// @todo
	// with Numbers
	// NEW FUNCIONALITY
	// with IO
	// with Overlap Node List
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithIOWithNumbers( String outputFolder,
			int permutationNumber, IntervalTreeNode node, InputLineMinimal interval, ChromosomeName chromName,
			TIntObjectMap<TIntList> geneId2KeggPathwayNumberMap,
			TIntObjectMap<BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap,
			TIntObjectMap<BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap,
			TIntObjectMap<BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap,
			TIntIntMap permutationNumberExonBasedKeggPathway2OneorZeroMap,
			TIntIntMap permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
			TIntIntMap permutationNumberAllBasedKeggPathway2OneorZeroMap, String type,
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberExonBasedKeggPathwayOverlapList,
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberRegulationBasedKeggPathwayOverlapList,
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberAllBasedKeggPathwayOverlapList,
			int overlapDefinition) {

		int permutationNumberKeggPathwayNumber;
		Integer keggPathwayNumber;
		TIntList keggPathwayNumberListContainingThisGeneId = null;

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			// There is overlap
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){
				try{

					if( node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
						castedNode = ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)node;
					}

					keggPathwayNumberListContainingThisGeneId = geneId2KeggPathwayNumberMap.get( castedNode.getGeneEntrezId());

					// write EXON based results
					if( castedNode.getIntervalName().isExon()){
						if( keggPathwayNumberListContainingThisGeneId != null){

							permutationNumberExonBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneNumberOverlap(
									permutationNumber, castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(),
									castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbolNumber(),
									castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),
									keggPathwayNumberListContainingThisGeneId));

							for( int i = 0; i < keggPathwayNumberListContainingThisGeneId.size(); i++){

								keggPathwayNumber = keggPathwayNumberListContainingThisGeneId.get(i);

								permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
										permutationNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								bufferedWriter = exonBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberKeggPathwayNumber);

								if( bufferedWriter == null){

									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_exonBased_" + keggPathwayNumber + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);
									bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t" + "GeneEntrezId" + System.getProperty( "line.separator"));
									bufferedWriter.flush();

									exonBasedKeggPathwayBufferedWriterHashMap.put( permutationNumberKeggPathwayNumber,
											bufferedWriter);
								}

								if( !( permutationNumberExonBasedKeggPathway2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
									permutationNumberExonBasedKeggPathway2OneorZeroMap.put(
											permutationNumberKeggPathwayNumber, 1);
								}

								bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
								bufferedWriter.flush();

							}// End of For: for all keggpathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Exon Based Kegg Pathway Analysis, Overlapped
						// node is an exon

					// write regulation based results
					// Regulation Based kegg pathway analysis
					if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

						if( keggPathwayNumberListContainingThisGeneId != null){

							permutationNumberRegulationBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneNumberOverlap(
									permutationNumber, castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(),
									castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbolNumber(),
									castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),
									keggPathwayNumberListContainingThisGeneId));

							for( int i = 0; i < keggPathwayNumberListContainingThisGeneId.size(); i++){

								keggPathwayNumber = keggPathwayNumberListContainingThisGeneId.get( i);
								permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
										permutationNumber,
										keggPathwayNumber,
										GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

								bufferedWriter = regulationBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberKeggPathwayNumber);

								if( bufferedWriter == null){

									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_regulationBased_" + keggPathwayNumber + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);

									bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t" + "GeneEntrezId" + System.getProperty( "line.separator"));
									bufferedWriter.flush();

									regulationBasedKeggPathwayBufferedWriterHashMap.put(
											permutationNumberKeggPathwayNumber, bufferedWriter);
								}

								if( !( permutationNumberRegulationBasedKeggPathway2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
									permutationNumberRegulationBasedKeggPathway2OneorZeroMap.put(
											permutationNumberKeggPathwayNumber, 1);
								}

								bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
								bufferedWriter.flush();

							}// End of For: for all kegg pathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Regulation Based kegg pathway Analysis,
						// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

					// write all results
					if( keggPathwayNumberListContainingThisGeneId != null){

						permutationNumberAllBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneNumberOverlap(
								permutationNumber, castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(),
								castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbolNumber(),
								castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),
								keggPathwayNumberListContainingThisGeneId));

						for( int i = 0; i < keggPathwayNumberListContainingThisGeneId.size(); i++){

							keggPathwayNumber = keggPathwayNumberListContainingThisGeneId.get( i);
							permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
									permutationNumber,
									keggPathwayNumber,
									GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

							bufferedWriter = allBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberKeggPathwayNumber);

							if( bufferedWriter == null){

								fileWriter = FileOperations.createFileWriter(
										outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_allBased_" + keggPathwayNumber + ".txt",
										true);
								bufferedWriter = new BufferedWriter( fileWriter);

								bufferedWriter.write( "Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName" + "\t" + "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t" + "GeneEntrezId" + System.getProperty( "line.separator"));
								bufferedWriter.flush();

								allBasedKeggPathwayBufferedWriterHashMap.put( permutationNumberKeggPathwayNumber,
										bufferedWriter);
							}

							if( !( permutationNumberAllBasedKeggPathway2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
								permutationNumberAllBasedKeggPathway2OneorZeroMap.put(
										permutationNumberKeggPathwayNumber, 1);
							}

							bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
							bufferedWriter.flush();

						}// End of For: for all kegg pathways having this gene
							// in their gene list
					} // End of If: keggPathWayListContainingThisGeneId is not
						// null

				}catch( IOException e){
					if( GlanetRunner.shouldLog())logger.error( e.toString());
				}

			}// End of if: there is overlap
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithIOWithNumbers( outputFolder, permutationNumber,
					node.getLeft(), interval, chromName, geneId2KeggPathwayNumberMap,
					exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,
					allBasedKeggPathwayBufferedWriterHashMap, permutationNumberExonBasedKeggPathway2OneorZeroMap,
					permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
					permutationNumberAllBasedKeggPathway2OneorZeroMap, type,
					permutationNumberExonBasedKeggPathwayOverlapList,
					permutationNumberRegulationBasedKeggPathwayOverlapList,
					permutationNumberAllBasedKeggPathwayOverlapList, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithIOWithNumbers( outputFolder, permutationNumber,
					node.getRight(), interval, chromName, geneId2KeggPathwayNumberMap,
					exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,
					allBasedKeggPathwayBufferedWriterHashMap, permutationNumberExonBasedKeggPathway2OneorZeroMap,
					permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
					permutationNumberAllBasedKeggPathway2OneorZeroMap, type,
					permutationNumberExonBasedKeggPathwayOverlapList,
					permutationNumberRegulationBasedKeggPathwayOverlapList,
					permutationNumberAllBasedKeggPathwayOverlapList, overlapDefinition);
		}
	}

	// with Numbers
	// @todo

	// NEW FUNCIONALITY
	// with IO
	// with Overlap Node List
	public void findAllOverlappingUcscRefSeqGenesIntervals( String outputFolder, int permutationNumber,
			IntervalTreeNode node, Interval interval, ChromosomeName chromName,
			Map<String, List<String>> geneId2KeggPathwayMap,
			Map<String, BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap,
			Map<String, BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap,
			Map<String, BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap,
			Map<String, Integer> permutationNumberExonBasedKeggPathway2OneorZeroMap,
			Map<String, Integer> permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
			Map<String, Integer> permutationNumberAllBasedKeggPathway2OneorZeroMap, String type,
			List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberExonBasedKeggPathwayOverlapList,
			List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberRegulationBasedKeggPathwayOverlapList,
			List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberAllBasedKeggPathwayOverlapList,
			int overlapDefinition) {

		String permutationNumberKeggPathwayName = null;
		String keggPathwayName;
		List<String> keggPathwayListContainingThisGeneId = null;

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		UcscRefSeqGeneIntervalTreeNode castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			// There is overlap
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){
				try{

					if( node instanceof UcscRefSeqGeneIntervalTreeNode){
						castedNode = ( UcscRefSeqGeneIntervalTreeNode)node;
					}

					keggPathwayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

					// write exon based results
					if( castedNode.getIntervalName().isExon()){
						if( keggPathwayListContainingThisGeneId != null){

							permutationNumberExonBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneOverlap(
									Commons.PERMUTATION + permutationNumber, castedNode.getRefSeqGeneName(),
									castedNode.getIntervalName(), castedNode.getIntervalNumber(),
									castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(),
									castedNode.getHigh(), keggPathwayListContainingThisGeneId));

							for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

								keggPathwayName = keggPathwayListContainingThisGeneId.get( i);
								permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

								bufferedWriter = exonBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberKeggPathwayName);

								if( bufferedWriter == null){

									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_exonBased_" + keggPathwayName + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);
									exonBasedKeggPathwayBufferedWriterHashMap.put( permutationNumberKeggPathwayName,
											bufferedWriter);
								}

								if( permutationNumberExonBasedKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
									permutationNumberExonBasedKeggPathway2OneorZeroMap.put(
											permutationNumberKeggPathwayName, 1);
								}

								bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
								bufferedWriter.flush();

							}// End of For: for all keggpathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Exon Based Kegg Pathway Analysis, Overlapped
						// node is an exon

					// write regulation based results
					// Regulation Based kegg pathway analysis
					if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

						if( keggPathwayListContainingThisGeneId != null){

							permutationNumberRegulationBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneOverlap(
									Commons.PERMUTATION + permutationNumber, castedNode.getRefSeqGeneName(),
									castedNode.getIntervalName(), castedNode.getIntervalNumber(),
									castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(),
									castedNode.getHigh(), keggPathwayListContainingThisGeneId));

							for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

								keggPathwayName = keggPathwayListContainingThisGeneId.get( i);
								permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

								bufferedWriter = regulationBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberKeggPathwayName);

								if( bufferedWriter == null){

									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_regulationBased_" + keggPathwayName + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);
									regulationBasedKeggPathwayBufferedWriterHashMap.put(
											permutationNumberKeggPathwayName, bufferedWriter);
								}

								if( permutationNumberRegulationBasedKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
									permutationNumberRegulationBasedKeggPathway2OneorZeroMap.put(
											permutationNumberKeggPathwayName, 1);
								}

								bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
								bufferedWriter.flush();

							}// End of For: for all kegg pathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Regulation Based kegg pathway Analysis,
						// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

					// write all results
					if( keggPathwayListContainingThisGeneId != null){

						permutationNumberAllBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneOverlap(
								Commons.PERMUTATION + permutationNumber, castedNode.getRefSeqGeneName(),
								castedNode.getIntervalName(), castedNode.getIntervalNumber(),
								castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(),
								castedNode.getHigh(), keggPathwayListContainingThisGeneId));

						for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

							keggPathwayName = keggPathwayListContainingThisGeneId.get( i);
							permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

							bufferedWriter = allBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberKeggPathwayName);

							if( bufferedWriter == null){

								fileWriter = FileOperations.createFileWriter(
										outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + permutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + permutationNumber + "_allBased_" + keggPathwayName + ".txt",
										true);
								bufferedWriter = new BufferedWriter( fileWriter);
								allBasedKeggPathwayBufferedWriterHashMap.put( permutationNumberKeggPathwayName,
										bufferedWriter);
							}

							if( permutationNumberAllBasedKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
								permutationNumberAllBasedKeggPathway2OneorZeroMap.put(
										permutationNumberKeggPathwayName, 1);
							}

							bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
							bufferedWriter.flush();

						}// End of For: for all kegg pathways having this gene
							// in their gene list
					} // End of If: keggPathWayListContainingThisGeneId is not
						// null

				}catch( IOException e){
					if( GlanetRunner.shouldLog())logger.error( e.toString());
				}

			}// End of if: there is overlap
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervals( outputFolder, permutationNumber, node.getLeft(), interval,
					chromName, geneId2KeggPathwayMap, exonBasedKeggPathwayBufferedWriterHashMap,
					regulationBasedKeggPathwayBufferedWriterHashMap, allBasedKeggPathwayBufferedWriterHashMap,
					permutationNumberExonBasedKeggPathway2OneorZeroMap,
					permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
					permutationNumberAllBasedKeggPathway2OneorZeroMap, type,
					permutationNumberExonBasedKeggPathwayOverlapList,
					permutationNumberRegulationBasedKeggPathwayOverlapList,
					permutationNumberAllBasedKeggPathwayOverlapList, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervals( outputFolder, permutationNumber, node.getRight(), interval,
					chromName, geneId2KeggPathwayMap, exonBasedKeggPathwayBufferedWriterHashMap,
					regulationBasedKeggPathwayBufferedWriterHashMap, allBasedKeggPathwayBufferedWriterHashMap,
					permutationNumberExonBasedKeggPathway2OneorZeroMap,
					permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
					permutationNumberAllBasedKeggPathway2OneorZeroMap, type,
					permutationNumberExonBasedKeggPathwayOverlapList,
					permutationNumberRegulationBasedKeggPathwayOverlapList,
					permutationNumberAllBasedKeggPathwayOverlapList, overlapDefinition);
		}
	}

	// NEW FUNCIONALITY
	// with IO
	public void findAllOverlappingUcscRefSeqGenesIntervals( String outputFolder, int repeatNumber,
			int permutationNumber, int NUMBER_OF_PERMUTATIONS, IntervalTreeNode node, Interval interval,
			String chromName, Map<String, List<String>> geneId2KeggPathwayMap,
			Map<String, BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap,
			Map<String, BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap,
			Map<String, BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap,
			Map<String, Integer> permutationNumberExonBasedKeggPathway2OneorZeroMap,
			Map<String, Integer> permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
			Map<String, Integer> permutationNumberAllBasedKeggPathway2OneorZeroMap, String type) {

		String permutationNumberKeggPathwayName = null;
		String keggPathwayName;
		List<String> keggPathwayListContainingThisGeneId = null;

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		UcscRefSeqGeneIntervalTreeNode castedNode = null;

		int repeatNumberReflectedPermutationNumber = ( ( repeatNumber - 1) * NUMBER_OF_PERMUTATIONS) + permutationNumber;

		if( Commons.NCBI_GENE_ID.equals( type)){
			// There is overlap
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
				try{

					if( node instanceof UcscRefSeqGeneIntervalTreeNode){
						castedNode = ( UcscRefSeqGeneIntervalTreeNode)node;
					}

					keggPathwayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

					// write exon based results
					if( castedNode.getIntervalName().isExon()){
						if( keggPathwayListContainingThisGeneId != null){
							for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

								keggPathwayName = keggPathwayListContainingThisGeneId.get( i);
								permutationNumberKeggPathwayName = Commons.PERMUTATION + ( ( ( repeatNumber - 1) * NUMBER_OF_PERMUTATIONS) + permutationNumber) + "_" + keggPathwayName;

								bufferedWriter = exonBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberKeggPathwayName);

								if( bufferedWriter == null){

									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + repeatNumberReflectedPermutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + repeatNumberReflectedPermutationNumber + "_exonBased_" + keggPathwayName + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);
									exonBasedKeggPathwayBufferedWriterHashMap.put( permutationNumberKeggPathwayName,
											bufferedWriter);
								}

								if( permutationNumberExonBasedKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
									permutationNumberExonBasedKeggPathway2OneorZeroMap.put(
											permutationNumberKeggPathwayName, 1);
								}

								bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
								bufferedWriter.flush();

							}// End of For: for all keggpathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Exon Based Kegg Pathway Analysis, Overlapped
						// node is an exon

					// write regulation based results
					// Regulation Based kegg pathway analysis
					if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

						if( keggPathwayListContainingThisGeneId != null){
							for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

								keggPathwayName = keggPathwayListContainingThisGeneId.get( i);
								permutationNumberKeggPathwayName = Commons.PERMUTATION + ( ( ( repeatNumber - 1) * NUMBER_OF_PERMUTATIONS) + permutationNumber) + "_" + keggPathwayName;

								bufferedWriter = regulationBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberKeggPathwayName);

								if( bufferedWriter == null){

									fileWriter = FileOperations.createFileWriter(
											Commons.ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + repeatNumberReflectedPermutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + repeatNumberReflectedPermutationNumber + "_regulationBased_" + keggPathwayName + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);
									regulationBasedKeggPathwayBufferedWriterHashMap.put(
											permutationNumberKeggPathwayName, bufferedWriter);
								}

								if( permutationNumberRegulationBasedKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
									permutationNumberRegulationBasedKeggPathway2OneorZeroMap.put(
											permutationNumberKeggPathwayName, 1);
								}

								bufferedWriter.write( "Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
								bufferedWriter.flush();

							}// End of For: for all kegg pathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Regulation Based kegg pathway Analysis,
						// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

					// write all results
					if( keggPathwayListContainingThisGeneId != null){
						for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

							keggPathwayName = keggPathwayListContainingThisGeneId.get( i);
							permutationNumberKeggPathwayName = Commons.PERMUTATION + ( ( ( repeatNumber - 1) * NUMBER_OF_PERMUTATIONS) + permutationNumber) + "_" + keggPathwayName;

							bufferedWriter = allBasedKeggPathwayBufferedWriterHashMap.get( permutationNumberKeggPathwayName);

							if( bufferedWriter == null){

								fileWriter = FileOperations.createFileWriter(
										Commons.ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION + repeatNumberReflectedPermutationNumber + System.getProperty( "file.separator") + Commons.PERMUTATION + repeatNumberReflectedPermutationNumber + "_allBased_" + keggPathwayName + ".txt",
										true);
								bufferedWriter = new BufferedWriter( fileWriter);
								allBasedKeggPathwayBufferedWriterHashMap.put( permutationNumberKeggPathwayName,
										bufferedWriter);
							}

							if( permutationNumberAllBasedKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
								permutationNumberAllBasedKeggPathway2OneorZeroMap.put(
										permutationNumberKeggPathwayName, 1);
							}

						}// End of For: for all kegg pathways having this gene
							// in their gene list
					} // End of If: keggPathWayListContainingThisGeneId is not
						// null

				}catch( IOException e){
					if( GlanetRunner.shouldLog())logger.error( e.toString());
				}

			}// End of if: there is overlap
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervals( outputFolder, repeatNumber, permutationNumber,
					NUMBER_OF_PERMUTATIONS, node.getLeft(), interval, chromName, geneId2KeggPathwayMap,
					exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,
					allBasedKeggPathwayBufferedWriterHashMap, permutationNumberExonBasedKeggPathway2OneorZeroMap,
					permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
					permutationNumberAllBasedKeggPathway2OneorZeroMap, type);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervals( outputFolder, repeatNumber, permutationNumber,
					NUMBER_OF_PERMUTATIONS, node.getRight(), interval, chromName, geneId2KeggPathwayMap,
					exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,
					allBasedKeggPathwayBufferedWriterHashMap, permutationNumberExonBasedKeggPathway2OneorZeroMap,
					permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
					permutationNumberAllBasedKeggPathway2OneorZeroMap, type);
		}
	}

	
	// 10 July 2015
	// Without IO
	// With Numbers
	// Without permutationNumber
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
			IntervalTreeNode node, 
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntObjectMap<TIntList> geneId2KeggPathwayNumbersMap,
			TIntByteMap exonBasedKEGGPathwayNumber2PermutationOneorZeroMap,
			TIntByteMap regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap,
			TIntByteMap allBasedKEGGPathwayNumber2PermutationOneorZeroMap, 
			String type,
			List<UcscRefSeqGeneOverlapWithNumbers> exonBasedKEGGPathwayOverlapList,
			List<UcscRefSeqGeneOverlapWithNumbers> regulationBasedKEGGPathwayOverlapList,
			List<UcscRefSeqGeneOverlapWithNumbers> allBasedKEGGPathwayOverlapList,
			int overlapDefinition) {

		int keggPathwayNumber;
		TIntList keggPathwayListContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			
			// There is overlap
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
					castedNode = ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)node;
				}

				keggPathwayListContainingThisGeneId = geneId2KeggPathwayNumbersMap.get( castedNode.getGeneEntrezId());

				// write EXON based results
				if( castedNode.getIntervalName().isExon()){
					
					if( keggPathwayListContainingThisGeneId != null){
						
						exonBasedKEGGPathwayOverlapList.add( new UcscRefSeqGeneOverlapWithNumbers(
								castedNode.getRefSeqGeneNumber(), 
								castedNode.getGeneHugoSymbolNumber(),
								castedNode.getGeneEntrezId(), 
								keggPathwayListContainingThisGeneId,
								castedNode.getIntervalName(),
								castedNode.getIntervalNumber(), 
								castedNode.getLow(), 
								castedNode.getHigh()
								));

						for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

							keggPathwayNumber = keggPathwayListContainingThisGeneId.get(i);
							
							if( !(exonBasedKEGGPathwayNumber2PermutationOneorZeroMap.containsKey(keggPathwayNumber))){
								exonBasedKEGGPathwayNumber2PermutationOneorZeroMap.put(keggPathwayNumber, Commons.BYTE_1);
							}

						}// End of FOR: for all keggpathways having this gene in their gene list
						
					} // End of IF: keggPathWayListContainingThisGeneId is not null
					
				}// End of IF: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

				// write REGULATION based results
				// Regulation Based kegg pathway analysis
				if( castedNode.getIntervalName().isIntron() || 
						castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || 
						castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

					if( keggPathwayListContainingThisGeneId != null){

						regulationBasedKEGGPathwayOverlapList.add( new UcscRefSeqGeneOverlapWithNumbers(
								castedNode.getRefSeqGeneNumber(), 
								castedNode.getGeneHugoSymbolNumber(),
								castedNode.getGeneEntrezId(), 
								keggPathwayListContainingThisGeneId,
								castedNode.getIntervalName(),
								castedNode.getIntervalNumber(), 
								castedNode.getLow(), 
								castedNode.getHigh()));

						for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

							keggPathwayNumber = keggPathwayListContainingThisGeneId.get( i);
							
							if( !(regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap.containsKey(keggPathwayNumber))){
								regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap.put(keggPathwayNumber, Commons.BYTE_1);
							}

						}// End of FOR: for all KEGG pathways having this gene in their gene list
						
					} // End of IF: keggPathWayListContainingThisGeneId is not null
					
				}// End of IF: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

				// write ALL results
				if( keggPathwayListContainingThisGeneId != null){

					allBasedKEGGPathwayOverlapList.add( new UcscRefSeqGeneOverlapWithNumbers(
							castedNode.getRefSeqGeneNumber(), 
							castedNode.getGeneHugoSymbolNumber(),
							castedNode.getGeneEntrezId(), 
							keggPathwayListContainingThisGeneId,
							castedNode.getIntervalName(),
							castedNode.getIntervalNumber(), 
							castedNode.getLow(), 
							castedNode.getHigh()));

					for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

						keggPathwayNumber = keggPathwayListContainingThisGeneId.get( i);
						
						if( !(allBasedKEGGPathwayNumber2PermutationOneorZeroMap.containsKey(keggPathwayNumber))){
							allBasedKEGGPathwayNumber2PermutationOneorZeroMap.put(keggPathwayNumber, Commons.BYTE_1);
						}

					}// End of For: for all kegg pathways having this gene in
						// their gene list
				} // End of If: keggPathWayListContainingThisGeneId is not null

			}// End of if: there is overlap
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
					node.getLeft(),
					interval, 
					chromName, 
					geneId2KeggPathwayNumbersMap,
					exonBasedKEGGPathwayNumber2PermutationOneorZeroMap,
					regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap,
					allBasedKEGGPathwayNumber2PermutationOneorZeroMap, 
					type,
					exonBasedKEGGPathwayOverlapList,
					regulationBasedKEGGPathwayOverlapList,
					allBasedKEGGPathwayOverlapList, 
					overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers( 
					node.getRight(),
					interval, 
					chromName, 
					geneId2KeggPathwayNumbersMap,
					exonBasedKEGGPathwayNumber2PermutationOneorZeroMap,
					regulationBasedKEGGPathwayNumber2PermutationOneorZeroMap,
					allBasedKEGGPathwayNumber2PermutationOneorZeroMap, 
					type,
					exonBasedKEGGPathwayOverlapList,
					regulationBasedKEGGPathwayOverlapList,
					allBasedKEGGPathwayOverlapList, 
					overlapDefinition);
		}

	}
	//10 July 2015
	
	// @todo
	// with numbers starts
	// NEW FUNCIONALITY
	// EXON BASED KEGG PATHWAY
	// REGULATION BASED KEGG PATHWAY
	// ALL BASED KEGG PATHWAY
	// Empirical P Value Calculation
	// Search2 KeggPathway
	// For finding the number of each keggpathway:k for the given search input
	// size: n
	// For each search input line, each kegg pathway will have a value of 1 or 0
	// These 1 or 0's will be accumulated in keggPathway2KMap
	// without IO
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers(
			int permutationNumber,
			IntervalTreeNode node, 
			InputLineMinimal interval, 
			ChromosomeName chromName,
			TIntObjectMap<TIntList> geneId2KeggPathwayNumbersMap,
			TIntIntMap permutationNumberExonBasedKeggPathwayNumber2OneorZeroMap,
			TIntIntMap permutationNumberRegulationBasedKeggPathwayNumber2OneorZeroMap,
			TIntIntMap permutationNumberAllBasedKeggPathwayNumber2OneorZeroMap, 
			String type,
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberExonBasedKeggPathwayOverlapList,
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberRegulationBasedKeggPathwayOverlapList,
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberAllBasedKeggPathwayOverlapList,
			int overlapDefinition) {

		int permutationNumberKeggPathwayNumber;
		int keggPathwayNumber;
		TIntList keggPathwayListContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			// There is overlap
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
					castedNode = ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)node;
				}

				keggPathwayListContainingThisGeneId = geneId2KeggPathwayNumbersMap.get( castedNode.getGeneEntrezId());

				// write EXON based results
				if( castedNode.getIntervalName().isExon()){
					if( keggPathwayListContainingThisGeneId != null){

						permutationNumberExonBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneNumberOverlap(
								permutationNumber, castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(),
								castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbolNumber(),
								castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),
								keggPathwayListContainingThisGeneId));

						for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

							keggPathwayNumber = keggPathwayListContainingThisGeneId.get(i);
							permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
									permutationNumber,
									keggPathwayNumber,
									GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

							if( !( permutationNumberExonBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
								permutationNumberExonBasedKeggPathwayNumber2OneorZeroMap.put(
										permutationNumberKeggPathwayNumber, 1);
							}

						}// End of For: for all keggpathways having this gene in
							// their gene list
					} // End of If: keggPathWayListContainingThisGeneId is not
						// null
				}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node
					// is an exon

				// write REGULATION based results
				// Regulation Based kegg pathway analysis
				if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

					if( keggPathwayListContainingThisGeneId != null){

						permutationNumberRegulationBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneNumberOverlap(
								permutationNumber, castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(),
								castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbolNumber(),
								castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),
								keggPathwayListContainingThisGeneId));

						for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

							keggPathwayNumber = keggPathwayListContainingThisGeneId.get( i);
							permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
									permutationNumber,
									keggPathwayNumber,
									GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

							if( !( permutationNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
								permutationNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.put(
										permutationNumberKeggPathwayNumber, 1);
							}

						}// End of For: for all kegg pathways having this gene
							// in their gene list
					} // End of If: keggPathWayListContainingThisGeneId is not
						// null
				}// End of If: Regulation Based kegg pathway Analysis,
					// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

				// write ALL results
				if( keggPathwayListContainingThisGeneId != null){

					permutationNumberAllBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneNumberOverlap(
							permutationNumber, castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(),
							castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbolNumber(),
							castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),
							keggPathwayListContainingThisGeneId));

					for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

						keggPathwayNumber = keggPathwayListContainingThisGeneId.get( i);
						permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(
								permutationNumber,
								keggPathwayNumber,
								GeneratedMixedNumberDescriptionOrderLength.INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER);

						if( !( permutationNumberAllBasedKeggPathwayNumber2OneorZeroMap.containsKey( permutationNumberKeggPathwayNumber))){
							permutationNumberAllBasedKeggPathwayNumber2OneorZeroMap.put(
									permutationNumberKeggPathwayNumber, 1);
						}

					}// End of For: for all kegg pathways having this gene in
						// their gene list
				} // End of If: keggPathWayListContainingThisGeneId is not null

			}// End of if: there is overlap
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers( permutationNumber, node.getLeft(),
					interval, chromName, geneId2KeggPathwayNumbersMap,
					permutationNumberExonBasedKeggPathwayNumber2OneorZeroMap,
					permutationNumberRegulationBasedKeggPathwayNumber2OneorZeroMap,
					permutationNumberAllBasedKeggPathwayNumber2OneorZeroMap, type,
					permutationNumberExonBasedKeggPathwayOverlapList,
					permutationNumberRegulationBasedKeggPathwayOverlapList,
					permutationNumberAllBasedKeggPathwayOverlapList, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithoutIOWithNumbers( permutationNumber, node.getRight(),
					interval, chromName, geneId2KeggPathwayNumbersMap,
					permutationNumberExonBasedKeggPathwayNumber2OneorZeroMap,
					permutationNumberRegulationBasedKeggPathwayNumber2OneorZeroMap,
					permutationNumberAllBasedKeggPathwayNumber2OneorZeroMap, type,
					permutationNumberExonBasedKeggPathwayOverlapList,
					permutationNumberRegulationBasedKeggPathwayOverlapList,
					permutationNumberAllBasedKeggPathwayOverlapList, overlapDefinition);
		}

	}

	// NEW FUNCIONALITY

	// with numbers ends
	// @todo

	// NEW FUNCIONALITY
	// EXON BASED KEGG PATHWAY
	// REGULATION BASED KEGG PATHWAY
	// ALL BASED KEGG PATHWAY
	// Empirical P Value Calculation
	// Search2 KeggPathway
	// For finding the number of each keggpathway:k for the given search input
	// size: n
	// For each search input line, each kegg pathway will have a value of 1 or 0
	// These 1 or 0's will be accumulated in keggPathway2KMap
	// without IO
	public void findAllOverlappingUcscRefSeqGenesIntervals( int permutationNumber, IntervalTreeNode node,
			Interval interval, ChromosomeName chromName, Map<String, List<String>> geneId2KeggPathwayMap,
			Map<String, Integer> permutationNumberExonBasedKeggPathway2OneorZeroMap,
			Map<String, Integer> permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
			Map<String, Integer> permutationNumberAllBasedKeggPathway2OneorZeroMap, String type,
			List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberExonBasedKeggPathwayOverlapList,
			List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberRegulationBasedKeggPathwayOverlapList,
			List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberAllBasedKeggPathwayOverlapList,
			int overlapDefinition) {

		String permutationNumberKeggPathwayName = null;
		String keggPathwayName;
		List<String> keggPathwayListContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNode castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			// There is overlap
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNode){
					castedNode = ( UcscRefSeqGeneIntervalTreeNode)node;
				}

				keggPathwayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

				// write exon based results
				if( castedNode.getIntervalName().isExon()){
					if( keggPathwayListContainingThisGeneId != null){

						permutationNumberExonBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneOverlap(
								Commons.PERMUTATION + permutationNumber, castedNode.getRefSeqGeneName(),
								castedNode.getIntervalName(), castedNode.getIntervalNumber(),
								castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(),
								castedNode.getHigh(), keggPathwayListContainingThisGeneId));

						for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

							keggPathwayName = keggPathwayListContainingThisGeneId.get( i);
							permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

							if( permutationNumberExonBasedKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
								permutationNumberExonBasedKeggPathway2OneorZeroMap.put(
										permutationNumberKeggPathwayName, 1);
							}

						}// End of For: for all keggpathways having this gene in
							// their gene list
					} // End of If: keggPathWayListContainingThisGeneId is not
						// null
				}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node
					// is an exon

				// write regulation based results
				// Regulation Based kegg pathway analysis
				if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

					if( keggPathwayListContainingThisGeneId != null){

						permutationNumberRegulationBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneOverlap(
								Commons.PERMUTATION + permutationNumber, castedNode.getRefSeqGeneName(),
								castedNode.getIntervalName(), castedNode.getIntervalNumber(),
								castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(),
								castedNode.getHigh(), keggPathwayListContainingThisGeneId));

						for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

							keggPathwayName = keggPathwayListContainingThisGeneId.get( i);
							permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

							if( permutationNumberRegulationBasedKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
								permutationNumberRegulationBasedKeggPathway2OneorZeroMap.put(
										permutationNumberKeggPathwayName, 1);
							}

						}// End of For: for all kegg pathways having this gene
							// in their gene list
					} // End of If: keggPathWayListContainingThisGeneId is not
						// null
				}// End of If: Regulation Based kegg pathway Analysis,
					// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

				// write all results
				if( keggPathwayListContainingThisGeneId != null){

					permutationNumberAllBasedKeggPathwayOverlapList.add( new PermutationNumberUcscRefSeqGeneOverlap(
							Commons.PERMUTATION + permutationNumber, castedNode.getRefSeqGeneName(),
							castedNode.getIntervalName(), castedNode.getIntervalNumber(),
							castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(),
							castedNode.getHigh(), keggPathwayListContainingThisGeneId));

					for( int i = 0; i < keggPathwayListContainingThisGeneId.size(); i++){

						keggPathwayName = keggPathwayListContainingThisGeneId.get( i);
						permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathwayName;

						if( permutationNumberAllBasedKeggPathway2OneorZeroMap.get( permutationNumberKeggPathwayName) == null){
							permutationNumberAllBasedKeggPathway2OneorZeroMap.put( permutationNumberKeggPathwayName, 1);
						}

					}// End of For: for all kegg pathways having this gene in
						// their gene list
				} // End of If: keggPathWayListContainingThisGeneId is not null

			}// End of if: there is overlap
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervals( permutationNumber, node.getLeft(), interval, chromName,
					geneId2KeggPathwayMap, permutationNumberExonBasedKeggPathway2OneorZeroMap,
					permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
					permutationNumberAllBasedKeggPathway2OneorZeroMap, type,
					permutationNumberExonBasedKeggPathwayOverlapList,
					permutationNumberRegulationBasedKeggPathwayOverlapList,
					permutationNumberAllBasedKeggPathwayOverlapList, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervals( permutationNumber, node.getRight(), interval, chromName,
					geneId2KeggPathwayMap, permutationNumberExonBasedKeggPathway2OneorZeroMap,
					permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
					permutationNumberAllBasedKeggPathway2OneorZeroMap, type,
					permutationNumberExonBasedKeggPathwayOverlapList,
					permutationNumberRegulationBasedKeggPathwayOverlapList,
					permutationNumberAllBasedKeggPathwayOverlapList, overlapDefinition);
		}

	}

	// NEW FUNCIONALITY

	public boolean contains( List<UcscRefSeqGeneIntervalTreeNodeWithNumbers> overlapList,
			UcscRefSeqGeneIntervalTreeNodeWithNumbers overlapNode) {

		// NM_022089 3P2 0 ATP13A2 23400
		// NM_001141973 3P2 0 ATP13A2 23400
		// NM_001141974 3P2 0 ATP13A2 23400

		boolean exists = false;

		// ArrayList<Overlap> overlapList = new
		// ArrayList<Overlap>(geneId2OverlapMap.valueCollection());

		for( int i = 0; i < overlapList.size(); i++){

			switch( overlapNode.getIntervalName()){

			case EXON:
			case INTRON:
				if( overlapList.get( i).getIntervalName().equals( overlapNode.getIntervalName()) && overlapList.get( i).getGeneHugoSymbolNumber() == overlapNode.getGeneHugoSymbolNumber() && overlapList.get(
						i).getGeneEntrezId() == overlapNode.getGeneEntrezId()){

					if( overlapList.get( i).getIntervalNumber() == overlapNode.getIntervalNumber()){
						exists = true;
					}else if( overlapList.get( i).getIntervalNumber() != overlapNode.getIntervalNumber() && overlapList.get(
							i).getLow() == overlapNode.getLow() && overlapList.get( i).getHigh() == overlapNode.getHigh()){
						exists = true;
					}

				} // End of IF

				break;

			case FIVE_P_ONE:
			case FIVE_P_TWO:
			case FIVE_D:
			case THREE_P_ONE:
			case THREE_P_TWO:
			case THREE_D:
				if( overlapList.get( i).getIntervalName().equals( overlapNode.getIntervalName()) && overlapList.get( i).getGeneHugoSymbolNumber() == overlapNode.getGeneHugoSymbolNumber() && overlapList.get(
						i).getGeneEntrezId() == overlapNode.getGeneEntrezId()){

					exists = true;

				} // End of IF

				break;

			}// End of SWITCH

			if( exists){

				break;

			}

		}// End of For: Look for each overlap

		return exists;

	}

	// Annotation
	// hg19 refseq Gene Annotation with numbers starts
	// Implemented for Chen Yao Paper
	public void findAllGeneOverlappingUcscRefSeqGenesIntervalsWithNumbers( 
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			BufferedWriter bufferedWriter, 
			int givenIntervalNumber,
			TIntObjectMap<OverlapInformation> givenIntervalNumber2OverlapInformationMap, 
			IntervalTreeNode node,
			Interval interval, 
			ChromosomeName chromName, 
			TIntByteMap entrezGeneID2OneorZeroMap, 
			String type,
			int overlapDefinition, 
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap) {

		int geneEntrezId;

		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;

		OverlapInformation overlapInformation = null;
		List<UcscRefSeqGeneIntervalTreeNodeWithNumbers> overlapList = null;

		if( Commons.NCBI_GENE_ID.equals( type)){

			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
					castedNode = ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)node;
				}

				geneEntrezId = castedNode.getGeneEntrezId();

				try{

					if( !entrezGeneID2OneorZeroMap.containsKey( geneEntrezId)){
						entrezGeneID2OneorZeroMap.put( geneEntrezId, Commons.BYTE_1);
					}

					/*******************************************************************************/
					/******** GIVEN INTERVAL NUMBER 2 OVERLAP INFORMATION MAP starts *****************/
					/*******************************************************************************/
					overlapInformation = givenIntervalNumber2OverlapInformationMap.get( givenIntervalNumber);

					// For this given interval, an overlap is put for the first time.
					if( overlapInformation == null){

						overlapInformation = new OverlapInformation();

						switch( castedNode.getIntervalName()){

						case EXON:
							overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
							overlapList.add( castedNode);
							overlapInformation.getGeneId2ExonOverlapListMap().put( castedNode.geneEntrezId, overlapList);

							break;

						case INTRON:
							overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
							overlapList.add( castedNode);
							overlapInformation.getGeneId2IntronOverlapListMap().put( castedNode.geneEntrezId,
									overlapList);

							break;

						case FIVE_P_ONE:
							overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
							overlapList.add( castedNode);
							overlapInformation.getGeneId2Fivep1OverlapListMap().put( castedNode.geneEntrezId,
									overlapList);

							break;

						case FIVE_P_TWO:
							overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
							overlapList.add( castedNode);
							overlapInformation.getGeneId2Fivep2OverlapListMap().put( castedNode.geneEntrezId,
									overlapList);

							break;

						case FIVE_D:
							overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
							overlapList.add( castedNode);
							overlapInformation.getGeneId2FivedOverlapListMap().put( castedNode.geneEntrezId,
									overlapList);

							break;

						case THREE_P_ONE:
							overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
							overlapList.add( castedNode);
							overlapInformation.getGeneId2Threep1OverlapListMap().put( castedNode.geneEntrezId,
									overlapList);

							break;

						case THREE_P_TWO:
							overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
							overlapList.add( castedNode);
							overlapInformation.getGeneId2Threep2OverlapListMap().put( castedNode.geneEntrezId,
									overlapList);

							break;

						case THREE_D:
							overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
							overlapList.add( castedNode);
							overlapInformation.getGeneId2ThreedOverlapListMap().put( castedNode.geneEntrezId,
									overlapList);

							break;

						}// End of SWITCH

						givenIntervalNumber2OverlapInformationMap.put( givenIntervalNumber, overlapInformation);

					}// End of IF: For this given interval , an overlap is put for the first time.

					// For this given interval, new overlap is seen
					// Check whether we have to put it or not.
					else{

						switch( castedNode.getIntervalName()){

						case EXON:
							overlapList = overlapInformation.getGeneId2ExonOverlapListMap().get(
									castedNode.getGeneEntrezId());

							if( overlapList == null){
								overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
								overlapList.add( castedNode);
								overlapInformation.getGeneId2ExonOverlapListMap().put( castedNode.geneEntrezId,
										overlapList);

							}else{
								if( !contains( overlapList, castedNode)){
									overlapList.add( castedNode);

								}
							}

							break;

						case INTRON:
							overlapList = overlapInformation.getGeneId2IntronOverlapListMap().get(
									castedNode.getGeneEntrezId());

							if( overlapList == null){
								overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
								overlapList.add( castedNode);
								overlapInformation.getGeneId2IntronOverlapListMap().put( castedNode.geneEntrezId,
										overlapList);

							}else{
								if( !contains( overlapList, castedNode)){
									overlapList.add( castedNode);

								}
							}

							break;

						case FIVE_P_ONE:
							overlapList = overlapInformation.getGeneId2Fivep1OverlapListMap().get(
									castedNode.getGeneEntrezId());

							if( overlapList == null){
								overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
								overlapList.add( castedNode);
								overlapInformation.getGeneId2Fivep1OverlapListMap().put( castedNode.geneEntrezId,
										overlapList);

							}else{
								if( !contains( overlapList, castedNode)){
									overlapList.add( castedNode);

								}
							}

							break;

						case FIVE_P_TWO:
							overlapList = overlapInformation.getGeneId2Fivep2OverlapListMap().get(
									castedNode.getGeneEntrezId());

							if( overlapList == null){
								overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
								overlapList.add( castedNode);
								overlapInformation.getGeneId2Fivep2OverlapListMap().put( castedNode.geneEntrezId,
										overlapList);

							}else{
								if( !contains( overlapList, castedNode)){
									overlapList.add( castedNode);

								}
							}

							break;

						case FIVE_D:
							overlapList = overlapInformation.getGeneId2FivedOverlapListMap().get(
									castedNode.getGeneEntrezId());

							if( overlapList == null){
								overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
								overlapList.add( castedNode);
								overlapInformation.getGeneId2FivedOverlapListMap().put( castedNode.geneEntrezId,
										overlapList);

							}else{
								if( !contains( overlapList, castedNode)){
									overlapList.add( castedNode);

								}
							}

							break;

						case THREE_P_ONE:
							overlapList = overlapInformation.getGeneId2Threep1OverlapListMap().get(
									castedNode.getGeneEntrezId());

							if( overlapList == null){
								overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
								overlapList.add( castedNode);
								overlapInformation.getGeneId2Threep1OverlapListMap().put( castedNode.geneEntrezId,
										overlapList);

							}else{
								if( !contains( overlapList, castedNode)){
									overlapList.add( castedNode);

								}
							}

							break;

						case THREE_P_TWO:
							overlapList = overlapInformation.getGeneId2Threep2OverlapListMap().get(
									castedNode.getGeneEntrezId());

							if( overlapList == null){
								overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
								overlapList.add( castedNode);
								overlapInformation.getGeneId2Threep2OverlapListMap().put( castedNode.geneEntrezId,
										overlapList);

							}else{
								if( !contains( overlapList, castedNode)){
									overlapList.add( castedNode);

								}
							}

							break;

						case THREE_D:
							overlapList = overlapInformation.getGeneId2ThreedOverlapListMap().get(
									castedNode.getGeneEntrezId());

							if( overlapList == null){
								overlapList = new ArrayList<UcscRefSeqGeneIntervalTreeNodeWithNumbers>();
								overlapList.add( castedNode);
								overlapInformation.getGeneId2ThreedOverlapListMap().put( castedNode.geneEntrezId,
										overlapList);

							}else{
								if( !contains( overlapList, castedNode)){
									overlapList.add( castedNode);

								}
							}

							break;

						}// End of Switch

					}// End of ELSE
					/*******************************************************************************/
					/******** GIVEN INTERVAL NUMBER 2 OVERLAP INFORMATION MAP ends *****************/
					/*******************************************************************************/

					// Write Annotation Found Overlaps to element Named File
					if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){
						bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( castedNode.getRefSeqGeneNumber()) + "\t" + castedNode.getIntervalName().convertEnumtoString() + "\t" + castedNode.getIntervalNumber() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( castedNode.getGeneHugoSymbolNumber()) + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
					}

				}catch( IOException e){
					if( GlanetRunner.shouldLog())logger.error( e.toString());
				}
			}// End of IF: overlaps
		} // End of IF: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllGeneOverlappingUcscRefSeqGenesIntervalsWithNumbers( outputFolder,
					writeElementBasedAnnotationFoundOverlapsMode, bufferedWriter, givenIntervalNumber,
					givenIntervalNumber2OverlapInformationMap, node.getLeft(), interval, chromName,
					entrezGeneID2OneorZeroMap, type, overlapDefinition, geneHugoSymbolNumber2GeneHugoSymbolNameMap,
					refSeqGeneNumber2RefSeqGeneNameMap);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllGeneOverlappingUcscRefSeqGenesIntervalsWithNumbers( outputFolder,
					writeElementBasedAnnotationFoundOverlapsMode, bufferedWriter, givenIntervalNumber,
					givenIntervalNumber2OverlapInformationMap, node.getRight(), interval, chromName,
					entrezGeneID2OneorZeroMap, type, overlapDefinition, geneHugoSymbolNumber2GeneHugoSymbolNameMap,
					refSeqGeneNumber2RefSeqGeneNameMap);

		}
	}

	// @todo Gene Annotation with numbers ends

	// Annotation with Numbers starts
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers( 
			String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			IntervalTreeNode node, 
			Interval interval, 
			ChromosomeName chromName,
			TIntByteMap exonBasedGeneSet2OneorZeroMap, 
			TIntByteMap regulationBasedGeneSet2OneorZeroMap,
			TIntByteMap allBasedGeneSet2OneorZeroMap, 
			String type, int overlapDefinition,
			TIntObjectMap<String> geneSetNumber2GeneSetNameMap,
			TIntObjectMap<TIntList> geneId2ListofGeneSetNumberMap,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap, 
			String geneSetName, 
			GeneSetType geneSetType) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		int geneSetNumber;
		TIntList geneSetNumberListContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){

			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
					castedNode = ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)node;
				}

				geneSetNumberListContainingThisGeneId = geneId2ListofGeneSetNumberMap.get( castedNode.getGeneEntrezId());

				try{

					// write EXON based GeneSet results
					if( castedNode.getIntervalName().isExon()){

						if( geneSetNumberListContainingThisGeneId != null){

							for( TIntIterator it = geneSetNumberListContainingThisGeneId.iterator(); it.hasNext();){

								geneSetNumber = it.next();

								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

									switch( geneSetType){

									case USERDEFINEDGENESET:
										fileWriter = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATION + System.getProperty( "file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty( "file.separator") + geneSetName + System.getProperty( "file.separator") + Commons.EXON_BASED + System.getProperty( "file.separator") + Commons.EXON_BASED + "_" + geneSetNumber2GeneSetNameMap.get( geneSetNumber) + ".txt",
												true);
										break;

									case KEGGPATHWAY:
										fileWriter = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATION + System.getProperty( "file.separator") + Commons.KEGG_PATHWAY + System.getProperty( "file.separator") + Commons.EXON_BASED + System.getProperty( "file.separator") + Commons.EXON_BASED + "_" + geneSetNumber2GeneSetNameMap.get( geneSetNumber) + ".txt",
												true);
										break;

									case NO_GENESET_TYPE_IS_DEFINED:
										break;

									}// End of switch

									bufferedWriter = new BufferedWriter( fileWriter);

									if( !exonBasedGeneSet2OneorZeroMap.containsKey( geneSetNumber)){
										exonBasedGeneSet2OneorZeroMap.put( geneSetNumber, Commons.BYTE_1);
										bufferedWriter.write( "#Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName" + "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol" + "\t" + "node GeneEntrezId" + System.getProperty( "line.separator"));
									}

									bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( castedNode.getRefSeqGeneNumber()) + "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( castedNode.getGeneHugoSymbolNumber()) + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
									bufferedWriter.close();
								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !exonBasedGeneSet2OneorZeroMap.containsKey( geneSetNumber)){
										exonBasedGeneSet2OneorZeroMap.put( geneSetNumber, Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							}// End of FOR: for all geneSets having this gene in their gene list

						} // End of IF: keggPathWayListContainingThisGeneId is not null

					}// End of IF: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

					// write REGULATION based GeneSet results
					if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

						if( geneSetNumberListContainingThisGeneId != null){

							for( TIntIterator it = geneSetNumberListContainingThisGeneId.iterator(); it.hasNext();){

								geneSetNumber = it.next();

								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

									switch( geneSetType){

									case USERDEFINEDGENESET:
										fileWriter = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATION + System.getProperty( "file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty( "file.separator") + geneSetName + System.getProperty( "file.separator") + Commons.REGULATION_BASED + System.getProperty( "file.separator") + Commons.REGULATION_BASED + "_" + geneSetNumber2GeneSetNameMap.get( geneSetNumber) + ".txt",
												true);
										break;
									case KEGGPATHWAY:
										fileWriter = FileOperations.createFileWriter(
												outputFolder + Commons.ANNOTATION + System.getProperty( "file.separator") + Commons.KEGG_PATHWAY + System.getProperty( "file.separator") + Commons.REGULATION_BASED + System.getProperty( "file.separator") + Commons.REGULATION_BASED + "_" + geneSetNumber2GeneSetNameMap.get( geneSetNumber) + ".txt",
												true);
										break;
									case NO_GENESET_TYPE_IS_DEFINED:
										break;

									}// End of SWITCH

									bufferedWriter = new BufferedWriter( fileWriter);

									if( !regulationBasedGeneSet2OneorZeroMap.containsKey( geneSetNumber)){
										regulationBasedGeneSet2OneorZeroMap.put( geneSetNumber, Commons.BYTE_1);
										bufferedWriter.write( "#Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName" + "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol" + "\t" + "node GeneEntrezId" + System.getProperty( "line.separator"));
									}

									bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( castedNode.getRefSeqGeneNumber()) + "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( castedNode.getGeneHugoSymbolNumber()) + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
									bufferedWriter.close();
								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !regulationBasedGeneSet2OneorZeroMap.containsKey( geneSetNumber)){
										regulationBasedGeneSet2OneorZeroMap.put( geneSetNumber, Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							}// End of FOR: for all gene sets having this gene in their gene list

						} // End of IF: keggPathWayListContainingThisGeneId is not null

					}// End of IF: Regulation Based Gene Set Analysis, Overlapped node is an Intron, 5P1, 5P2, 3P1, 3P2

					// write ALL Based GeneSet results
					if( geneSetNumberListContainingThisGeneId != null){

						for( TIntIterator it = geneSetNumberListContainingThisGeneId.iterator(); it.hasNext();){
							geneSetNumber = it.next();

							/*******************************************************************/
							// Write Annotation Found Overlaps to element Named File
							if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

								switch( geneSetType){

								case USERDEFINEDGENESET:
									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.ANNOTATION + System.getProperty( "file.separator") + Commons.USER_DEFINED_GENESET + System.getProperty( "file.separator") + geneSetName + System.getProperty( "file.separator") + Commons.ALL_BASED + System.getProperty( "file.separator") + Commons.ALL_BASED + "_" + geneSetNumber2GeneSetNameMap.get( geneSetNumber) + ".txt",
											true);
									break;
								case KEGGPATHWAY:
									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.ANNOTATION + System.getProperty( "file.separator") + Commons.KEGG_PATHWAY + System.getProperty( "file.separator") + Commons.ALL_BASED + System.getProperty( "file.separator") + Commons.ALL_BASED + "_" + geneSetNumber2GeneSetNameMap.get( geneSetNumber) + ".txt",
											true);
									break;
								case NO_GENESET_TYPE_IS_DEFINED:
									break;

								}// End of switch

								bufferedWriter = new BufferedWriter( fileWriter);

								if( !allBasedGeneSet2OneorZeroMap.containsKey( geneSetNumber)){
									allBasedGeneSet2OneorZeroMap.put( geneSetNumber, Commons.BYTE_1);
									bufferedWriter.write( "#Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName" + "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol" + "\t" + "node GeneEntrezId" + System.getProperty( "line.separator"));
								}

								bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( castedNode.getRefSeqGeneNumber()) + "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( castedNode.getGeneHugoSymbolNumber()) + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
								bufferedWriter.close();
							}
							/*******************************************************************/

							/*******************************************************************/
							// Do not Write Annotation Found Overlaps to element Named File
							else{
								if( !allBasedGeneSet2OneorZeroMap.containsKey( geneSetNumber)){
									allBasedGeneSet2OneorZeroMap.put( geneSetNumber, Commons.BYTE_1);
								}
							}
							/*******************************************************************/

						}// End of FOR: for all gene sets having this gene in their gene list
					} // End of IF: geneSetListContainingThisGeneId is not null

				}catch( IOException e){
					if( GlanetRunner.shouldLog())logger.error( e.toString());
				}

			}// End of IF: there is an overlap

		} // End of IF: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers( outputFolder,
					writeElementBasedAnnotationFoundOverlapsMode, node.getLeft(), interval, chromName,
					exonBasedGeneSet2OneorZeroMap, regulationBasedGeneSet2OneorZeroMap, allBasedGeneSet2OneorZeroMap,
					type, overlapDefinition, geneSetNumber2GeneSetNameMap, geneId2ListofGeneSetNumberMap,
					geneHugoSymbolNumber2GeneHugoSymbolNameMap, refSeqGeneNumber2RefSeqGeneNameMap, geneSetName,
					geneSetType);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers( outputFolder,
					writeElementBasedAnnotationFoundOverlapsMode, node.getRight(), interval, chromName,
					exonBasedGeneSet2OneorZeroMap, regulationBasedGeneSet2OneorZeroMap, allBasedGeneSet2OneorZeroMap,
					type, overlapDefinition, geneSetNumber2GeneSetNameMap, geneId2ListofGeneSetNumberMap,
					geneHugoSymbolNumber2GeneHugoSymbolNameMap, refSeqGeneNumber2RefSeqGeneNameMap, geneSetName,
					geneSetType);

		}
	}

	// Modified 20 July 2015
	// Annotation
	// with Numbers with OverlapList starts
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers( String outputFolder,
			WriteElementBasedAnnotationFoundOverlapsMode writeElementBasedAnnotationFoundOverlapsMode,
			IntervalTreeNode node, 
			Interval interval, 
			ChromosomeName chromName,
			TIntObjectMap<TIntList> geneId2ListofKeggPathwayNumberMap,
			TIntByteMap exonBasedKeggPathway2OneorZeroMap, 
			TIntByteMap regulationBasedKeggPathway2OneorZeroMap,
			TIntByteMap allBasedKeggPathway2OneorZeroMap, 
			String type,
			List<UcscRefSeqGeneOverlapWithNumbers> exonBasedKeggPathwayOverlapList,
			List<UcscRefSeqGeneOverlapWithNumbers> regulationBasedKeggPathwayOverlapList,
			List<UcscRefSeqGeneOverlapWithNumbers> allBasedKeggPathwayOverlapList, 
			int overlapDefinition,
			TIntObjectMap<String> keggPathwayNumber2KeggPathwayNameMap,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		int keggPathwayNumber;
		TIntList keggPathWayNumberListContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
					castedNode = ( UcscRefSeqGeneIntervalTreeNodeWithNumbers)node;
				}

				keggPathWayNumberListContainingThisGeneId = geneId2ListofKeggPathwayNumberMap.get( castedNode.getGeneEntrezId());

				try{

					/*******************************************************************/
					/************Write Exon Based KEGG Pathway results starts***********/
					/*******************************************************************/
					if( castedNode.getIntervalName().isExon()){

						if( keggPathWayNumberListContainingThisGeneId != null){

							exonBasedKeggPathwayOverlapList.add( new UcscRefSeqGeneOverlapWithNumbers(
									castedNode.getRefSeqGeneNumber(), castedNode.getGeneHugoSymbolNumber(),
									castedNode.getGeneEntrezId(), keggPathWayNumberListContainingThisGeneId,
									castedNode.getIntervalName(), castedNode.getIntervalNumber(), node.getLow(),
									node.getHigh()));

							for( TIntIterator it = keggPathWayNumberListContainingThisGeneId.iterator(); it.hasNext();){
								keggPathwayNumber = it.next();

								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.EXON_BASED_KEGG_PATHWAY_ANNOTATION + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);

									if( !exonBasedKeggPathway2OneorZeroMap.containsKey( keggPathwayNumber)){
										exonBasedKeggPathway2OneorZeroMap.put( keggPathwayNumber, Commons.BYTE_1);
										bufferedWriter.write( "#Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName" + "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol" + "\t" + "node GeneEntrezId" + System.getProperty( "line.separator"));
									}

									bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( castedNode.getRefSeqGeneNumber()) + "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( castedNode.getGeneHugoSymbolNumber()) + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
									bufferedWriter.close();
								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !exonBasedKeggPathway2OneorZeroMap.containsKey( keggPathwayNumber)){
										exonBasedKeggPathway2OneorZeroMap.put( keggPathwayNumber, Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							}// End of For: for all keggpathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Exon Based Kegg Pathway Analysis, Overlapped
						// node is an exon
					/*******************************************************************/
					/************Write Exon Based KEGG Pathway results ends*************/
					/*******************************************************************/

					/*******************************************************************/
					/*********Write Regulation Based KEGG Pathway results starts********/
					/*******************************************************************/
					if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

						if( keggPathWayNumberListContainingThisGeneId != null){

							regulationBasedKeggPathwayOverlapList.add( new UcscRefSeqGeneOverlapWithNumbers(
									castedNode.getRefSeqGeneNumber(), castedNode.getGeneHugoSymbolNumber(),
									castedNode.getGeneEntrezId(), keggPathWayNumberListContainingThisGeneId,
									castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getLow(),
									castedNode.getHigh()));

							for( TIntIterator it = keggPathWayNumberListContainingThisGeneId.iterator(); it.hasNext();){
								keggPathwayNumber = it.next();

								/*******************************************************************/
								// Write Annotation Found Overlaps to element Named File
								if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){

									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.REGULATION_BASED_KEGG_PATHWAY_ANNOTATION + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);

									if( !regulationBasedKeggPathway2OneorZeroMap.containsKey( keggPathwayNumber)){
										regulationBasedKeggPathway2OneorZeroMap.put( keggPathwayNumber, Commons.BYTE_1);
										bufferedWriter.write( "#Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName" + "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol" + "\t" + "node GeneEntrezId" + System.getProperty( "line.separator"));
									}

									bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( castedNode.getRefSeqGeneNumber()) + "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( castedNode.getGeneHugoSymbolNumber()) + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
									bufferedWriter.close();

								}
								/*******************************************************************/

								/*******************************************************************/
								// Do not Write Annotation Found Overlaps to element Named File
								else{
									if( !regulationBasedKeggPathway2OneorZeroMap.containsKey( keggPathwayNumber)){
										regulationBasedKeggPathway2OneorZeroMap.put( keggPathwayNumber, Commons.BYTE_1);
									}
								}
								/*******************************************************************/

							}// End of For: for all kegg pathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Regulation Based kegg pathway Analysis,
						// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2
					/*******************************************************************/
					/*********Write Regulation Based KEGG Pathway results ends**********/
					/*******************************************************************/

					/*******************************************************************/
					/*********Write ALL Based KEGG Pathway results starts***************/
					/*******************************************************************/
					if( keggPathWayNumberListContainingThisGeneId != null){

						allBasedKeggPathwayOverlapList.add( new UcscRefSeqGeneOverlapWithNumbers(
								castedNode.getRefSeqGeneNumber(), castedNode.getGeneHugoSymbolNumber(),
								castedNode.getGeneEntrezId(), keggPathWayNumberListContainingThisGeneId,
								castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getLow(),
								castedNode.getHigh()));

						for( TIntIterator it = keggPathWayNumberListContainingThisGeneId.iterator(); it.hasNext();){
							keggPathwayNumber = it.next();

							/*******************************************************************/
							// Write Annotation Found Overlaps to element Named File
							if( writeElementBasedAnnotationFoundOverlapsMode.isWriteElementBasedAnnotationFoundOverlaps()){
								fileWriter = FileOperations.createFileWriter(
										outputFolder + Commons.ALL_BASED_KEGG_PATHWAY_ANALYSIS + "_" + keggPathwayNumber2KeggPathwayNameMap.get( keggPathwayNumber) + ".txt",
										true);
								bufferedWriter = new BufferedWriter( fileWriter);

								if( !allBasedKeggPathway2OneorZeroMap.containsKey( keggPathwayNumber)){
									allBasedKeggPathway2OneorZeroMap.put( keggPathwayNumber, Commons.BYTE_1);
									bufferedWriter.write( "#Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName" + "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol" + "\t" + "node GeneEntrezId" + System.getProperty( "line.separator"));
								}

								bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString( castedNode.getChromName()) + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get( castedNode.getRefSeqGeneNumber()) + "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get( castedNode.getGeneHugoSymbolNumber()) + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
								bufferedWriter.close();
							}
							/*******************************************************************/

							/*******************************************************************/
							// Do not Write Annotation Found Overlaps to element Named File
							else{
								if( !allBasedKeggPathway2OneorZeroMap.containsKey( keggPathwayNumber)){
									allBasedKeggPathway2OneorZeroMap.put( keggPathwayNumber, Commons.BYTE_1);
								}
							}
							/*******************************************************************/

						}// End of For: for all kegg pathways having this gene
							// in their gene list
					} // End of If: keggPathWayListContainingThisGeneId is not
					/*******************************************************************/
					/*********Write ALL Based KEGG Pathway results ends*****************/
					/*******************************************************************/

				}catch( IOException e){
					if( GlanetRunner.shouldLog())logger.error( e.toString());
				}
			}
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers( outputFolder,
					writeElementBasedAnnotationFoundOverlapsMode, node.getLeft(), interval, chromName,
					geneId2ListofKeggPathwayNumberMap, exonBasedKeggPathway2OneorZeroMap,
					regulationBasedKeggPathway2OneorZeroMap, allBasedKeggPathway2OneorZeroMap, type,
					exonBasedKeggPathwayOverlapList, regulationBasedKeggPathwayOverlapList,
					allBasedKeggPathwayOverlapList, overlapDefinition, keggPathwayNumber2KeggPathwayNameMap,
					geneHugoSymbolNumber2GeneHugoSymbolNameMap, refSeqGeneNumber2RefSeqGeneNameMap);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers( outputFolder,
					writeElementBasedAnnotationFoundOverlapsMode, node.getRight(), interval, chromName,
					geneId2ListofKeggPathwayNumberMap, exonBasedKeggPathway2OneorZeroMap,
					regulationBasedKeggPathway2OneorZeroMap, allBasedKeggPathway2OneorZeroMap, type,
					exonBasedKeggPathwayOverlapList, regulationBasedKeggPathwayOverlapList,
					allBasedKeggPathwayOverlapList, overlapDefinition, keggPathwayNumber2KeggPathwayNameMap,
					geneHugoSymbolNumber2GeneHugoSymbolNameMap, refSeqGeneNumber2RefSeqGeneNameMap);

		}
	}

	// New Functionality ends
	// Annotation with Numbers with OverlapList ends

	// Is it used?
	// New Functionality starts
	// Search2 Kegg Pathway
	// Search for TF
	// Search for KEGG Pathways (exon based, regulation based, all based)
	// Search for TF and KEGG Pathways (tf and exonBased, tf and
	// regulationBased,tf and allBased)
	// will be modified
	public void findAllOverlappingUcscRefSeqGenesIntervals( String outputFolder, IntervalTreeNode node,
			Interval interval, ChromosomeName chromName,
			Map<String, BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap,
			Map<String, BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap,
			Map<String, BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap,
			Map<String, List<String>> geneId2KeggPathwayMap, List<String> keggPathwayNameList,
			Map<String, Integer> exonBasedKeggPathway2OneorZeroMap,
			Map<String, Integer> regulationBasedKeggPathway2OneorZeroMap,
			Map<String, Integer> allBasedKeggPathway2OneorZeroMap, String type,
			List<UcscRefSeqGeneOverlap> exonBasedKeggPathwayOverlapList,
			List<UcscRefSeqGeneOverlap> regulationBasedKeggPathwayOverlapList,
			List<UcscRefSeqGeneOverlap> allBasedKeggPathwayOverlapList, int overlapDefinition) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		String keggPathwayName = null;
		List<String> keggPathWayListContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNode castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){
			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(), overlapDefinition)){

				if( node instanceof UcscRefSeqGeneIntervalTreeNode){
					castedNode = ( UcscRefSeqGeneIntervalTreeNode)node;
				}

				keggPathWayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

				try{

					// write exon based kegg pathway results
					if( castedNode.getIntervalName().isExon()){

						if( keggPathWayListContainingThisGeneId != null){

							exonBasedKeggPathwayOverlapList.add( new UcscRefSeqGeneOverlap(
									castedNode.getRefSeqGeneName(), castedNode.getIntervalName(),
									castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbol(),
									castedNode.getGeneEntrezId(), node.getLow(), node.getHigh(),
									keggPathWayListContainingThisGeneId));

							for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){
								keggPathwayName = keggPathWayListContainingThisGeneId.get( i);

								if( keggPathwayNameList.contains( keggPathwayName)){

									bufferedWriter = exonBasedKeggPathwayBufferedWriterHashMap.get( keggPathwayName);

									if( bufferedWriter == null){
										fileWriter = FileOperations.createFileWriter(
												outputFolder + Commons.EXON_BASED_KEGG_PATHWAY_ANNOTATION + "_exonBased_" + keggPathwayName + ".txt",
												true);
										bufferedWriter = new BufferedWriter( fileWriter);
										exonBasedKeggPathwayBufferedWriterHashMap.put( keggPathwayName, bufferedWriter);
										bufferedWriter.write( "Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName" + "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol" + "\t" + "node GeneEntrezId" + System.getProperty( "line.separator"));
										bufferedWriter.flush();
									}

									if( exonBasedKeggPathway2OneorZeroMap.get( keggPathwayName) == null){
										exonBasedKeggPathway2OneorZeroMap.put( keggPathwayName, 1);
									}

									bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
									bufferedWriter.flush();

								}// If this keggPathwayName is in
									// keggPathwayNameList

							}// End of For: for all keggpathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Exon Based Kegg Pathway Analysis, Overlapped
						// node is an exon

					// write regulation based kegg pathway results
					if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

						if( keggPathWayListContainingThisGeneId != null){

							regulationBasedKeggPathwayOverlapList.add( new UcscRefSeqGeneOverlap(
									castedNode.getRefSeqGeneName(), castedNode.getIntervalName(),
									castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbol(),
									castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),
									keggPathWayListContainingThisGeneId));

							for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){
								keggPathwayName = keggPathWayListContainingThisGeneId.get( i);

								if( keggPathwayNameList.contains( keggPathwayName)){

									bufferedWriter = regulationBasedKeggPathwayBufferedWriterHashMap.get( keggPathwayName);

									if( bufferedWriter == null){
										fileWriter = FileOperations.createFileWriter(
												outputFolder + Commons.REGULATION_BASED_KEGG_PATHWAY_ANNOTATION + "_regulationBased_" + keggPathwayName + ".txt",
												true);
										bufferedWriter = new BufferedWriter( fileWriter);
										regulationBasedKeggPathwayBufferedWriterHashMap.put( keggPathwayName,
												bufferedWriter);
										bufferedWriter.write( "Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName" + "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol" + "\t" + "node GeneEntrezId" + System.getProperty( "line.separator"));
										bufferedWriter.flush();
									}

									if( regulationBasedKeggPathway2OneorZeroMap.get( keggPathwayName) == null){
										regulationBasedKeggPathway2OneorZeroMap.put( keggPathwayName, 1);
									}

									bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
									bufferedWriter.flush();

								}// If this keggPathwayName is in
									// keggPathwayNameList

							}// End of For: for all kegg pathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null
					}// End of If: Regulation Based kegg pathway Analysis,
						// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

					// write all results
					if( keggPathWayListContainingThisGeneId != null){

						allBasedKeggPathwayOverlapList.add( new UcscRefSeqGeneOverlap( castedNode.getRefSeqGeneName(),
								castedNode.getIntervalName(), castedNode.getIntervalNumber(),
								castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(),
								castedNode.getHigh(), keggPathWayListContainingThisGeneId));

						for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){
							keggPathwayName = keggPathWayListContainingThisGeneId.get( i);

							if( keggPathwayNameList.contains( keggPathwayName)){

								bufferedWriter = allBasedKeggPathwayBufferedWriterHashMap.get( keggPathwayName);

								if( bufferedWriter == null){
									fileWriter = FileOperations.createFileWriter(
											outputFolder + Commons.ALL_BASED_KEGG_PATHWAY_ANALYSIS + "_allBased_" + keggPathwayName + ".txt",
											true);
									bufferedWriter = new BufferedWriter( fileWriter);
									allBasedKeggPathwayBufferedWriterHashMap.put( keggPathwayName, bufferedWriter);
									bufferedWriter.write( "Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" + "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName" + "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol" + "\t" + "node GeneEntrezId" + System.getProperty( "line.separator"));
									bufferedWriter.flush();
								}

								if( allBasedKeggPathway2OneorZeroMap.get( keggPathwayName) == null){
									allBasedKeggPathway2OneorZeroMap.put( keggPathwayName, 1);
								}

								bufferedWriter.write( chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
								bufferedWriter.flush();

							}// If this keggPathwayName is in
								// keggPathwayNameList

						}// End of For: for all kegg pathways having this gene
							// in their gene list
					} // End of If: keggPathWayListContainingThisGeneId is not
						// null

				}catch( IOException e){
					if( GlanetRunner.shouldLog())logger.error( e.toString());
				}
			}
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervals( outputFolder, node.getLeft(), interval, chromName,
					exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,
					allBasedKeggPathwayBufferedWriterHashMap, geneId2KeggPathwayMap, keggPathwayNameList,
					exonBasedKeggPathway2OneorZeroMap, regulationBasedKeggPathway2OneorZeroMap,
					allBasedKeggPathway2OneorZeroMap, type, exonBasedKeggPathwayOverlapList,
					regulationBasedKeggPathwayOverlapList, allBasedKeggPathwayOverlapList, overlapDefinition);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervals( outputFolder, node.getRight(), interval, chromName,
					exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,
					allBasedKeggPathwayBufferedWriterHashMap, geneId2KeggPathwayMap, keggPathwayNameList,
					exonBasedKeggPathway2OneorZeroMap, regulationBasedKeggPathway2OneorZeroMap,
					allBasedKeggPathway2OneorZeroMap, type, exonBasedKeggPathwayOverlapList,
					regulationBasedKeggPathwayOverlapList, allBasedKeggPathwayOverlapList, overlapDefinition);

		}
	}

	// New Functionality ends
	// Search2 Kegg Pathway

	// Is it used?
	// Search2 KeggPathway
	// For finding the number of each keggpathway:k for the given search input
	// size: n
	// For each search input line, each kegg pathway will have a value of 1 or 0
	// These 1 or 0's will be accumulated in keggPathway2KMap
	public void findAllOverlappingUcscRefSeqGenesIntervals( IntervalTreeNode node, Interval interval,
			ChromosomeName chromName, Map<String, BufferedWriter> bufferedWriterHashMap,
			Map<String, List<String>> geneId2KeggPathwayMap, List<String> keggPathwayNameList,
			Map<String, Integer> keggPathway2OneorZeroMap, String type, KeggPathwayAnalysisType keggPathwayAnalysisType) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		String keggPathwayName = null;
		List<String> keggPathWayListContainingThisGeneId = null;

		UcscRefSeqGeneIntervalTreeNode castedNode = null;

		if( Commons.NCBI_GENE_ID.equals( type)){

			if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
				try{

					if( node instanceof UcscRefSeqGeneIntervalTreeNode){
						castedNode = ( UcscRefSeqGeneIntervalTreeNode)node;

					}

					// write exon based results
					if( keggPathwayAnalysisType.isExonBasedKeggPathwayAnalysis()){

						// exon based kegg pathway analysis
						if( castedNode.getIntervalName().isExon()){

							keggPathWayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

							if( keggPathWayListContainingThisGeneId != null){
								for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){
									keggPathwayName = keggPathWayListContainingThisGeneId.get( i);

									if( keggPathwayNameList.contains( keggPathwayName)){

										bufferedWriter = bufferedWriterHashMap.get( keggPathwayName);

										if( bufferedWriter == null){
											fileWriter = FileOperations.createFileWriter( Commons.EXON_BASED_KEGG_PATHWAY_ANNOTATION + "_exonBased_" + keggPathwayName + ".txt");
											bufferedWriter = new BufferedWriter( fileWriter);
											bufferedWriterHashMap.put( keggPathwayName, bufferedWriter);

										}

										if( keggPathway2OneorZeroMap.get( keggPathwayName) == null){
											keggPathway2OneorZeroMap.put( keggPathwayName, 1);
										}

										bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
										bufferedWriter.close();
									}// If this keggPathwayName is in
										// keggPathwayNameList

								}// End of For: for all keggpathways having this
									// gene in their gene list
							} // End of If: keggPathWayListContainingThisGeneId
								// is not null
						}// End of If: Exon Based Kegg Pathway Analysis,
							// Overlapped node is an exon

					}
					// write regulation based results
					else if( keggPathwayAnalysisType.isRegulationBasedKeggPathwayAnalysis()){
						// Regulation Based kegg pathway analysis
						if( castedNode.getIntervalName().isIntron() || castedNode.getIntervalName().isFivePOne() || castedNode.getIntervalName().isFivePTwo() || castedNode.getIntervalName().isThreePOne() || castedNode.getIntervalName().isThreePTwo()){

							keggPathWayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

							if( keggPathWayListContainingThisGeneId != null){
								for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){
									keggPathwayName = keggPathWayListContainingThisGeneId.get( i);

									if( keggPathwayNameList.contains( keggPathwayName)){

										bufferedWriter = bufferedWriterHashMap.get( keggPathwayName);

										if( bufferedWriter == null){
											fileWriter = FileOperations.createFileWriter( Commons.REGULATION_BASED_KEGG_PATHWAY_ANNOTATION + "_regulationBased_" + keggPathwayName + ".txt");
											bufferedWriter = new BufferedWriter( fileWriter);
											bufferedWriterHashMap.put( keggPathwayName, bufferedWriter);

										}

										if( keggPathway2OneorZeroMap.get( keggPathwayName) == null){
											keggPathway2OneorZeroMap.put( keggPathwayName, 1);
										}

										bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
										bufferedWriter.flush();
									}// If this keggPathwayName is in
										// keggPathwayNameList

								}// End of For: for all kegg pathways having
									// this gene in their gene list
							} // End of If: keggPathWayListContainingThisGeneId
								// is not null
						}// End of If: Regulation Based kegg pathway Analysis,
							// Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

					}
					// write all results
					else{
						keggPathWayListContainingThisGeneId = geneId2KeggPathwayMap.get( castedNode.getGeneEntrezId().toString());

						if( keggPathWayListContainingThisGeneId != null){
							for( int i = 0; i < keggPathWayListContainingThisGeneId.size(); i++){
								keggPathwayName = keggPathWayListContainingThisGeneId.get( i);

								if( keggPathwayNameList.contains( keggPathwayName)){

									bufferedWriter = bufferedWriterHashMap.get( keggPathwayName);

									if( bufferedWriter == null){
										fileWriter = FileOperations.createFileWriter( Commons.ALL_BASED_KEGG_PATHWAY_ANALYSIS + "_all_" + keggPathwayName + ".txt");
										bufferedWriter = new BufferedWriter( fileWriter);
										bufferedWriterHashMap.put( keggPathwayName, bufferedWriter);

									}

									if( keggPathway2OneorZeroMap.get( keggPathwayName) == null){
										keggPathway2OneorZeroMap.put( keggPathwayName, 1);
									}

									bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
									bufferedWriter.flush();
								}// If this keggPathwayName is in
									// keggPathwayNameList

							}// End of For: for all kegg pathways having this
								// gene in their gene list
						} // End of If: keggPathWayListContainingThisGeneId is
							// not null

					}

				}catch( IOException e){
					if( GlanetRunner.shouldLog())logger.error( e.toString());
				}
			}
		} // End of If: type is NCBI_GENE_ID

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervals( ( UcscRefSeqGeneIntervalTreeNode)node.getLeft(), interval,
					chromName, bufferedWriterHashMap, geneId2KeggPathwayMap, keggPathwayNameList,
					keggPathway2OneorZeroMap, type, keggPathwayAnalysisType);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervals( ( UcscRefSeqGeneIntervalTreeNode)node.getRight(), interval,
					chromName, bufferedWriterHashMap, geneId2KeggPathwayMap, keggPathwayNameList,
					keggPathway2OneorZeroMap, type, keggPathwayAnalysisType);

		}
	}

	// Search2
	public void findAllOverlappingUcscRefSeqGenesIntervals( IntervalTreeNode node, Interval interval,
			ChromosomeName chromName, Map<String, BufferedWriter> bufferedWriterHashMap,
			Map<String, Integer> nameorIdHashMap, String type) {

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		Integer count;

		UcscRefSeqGeneIntervalTreeNode castedNode = null;

		try{

			if( node.getNodeName().isNotSentinel()){

				if( node instanceof UcscRefSeqGeneIntervalTreeNode){
					castedNode = ( UcscRefSeqGeneIntervalTreeNode)node;
				}

				if( Commons.NCBI_GENE_ID.equals( type)){
					if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh()) && nameorIdHashMap.containsKey( castedNode.getGeneEntrezId().toString())){

						bufferedWriter = bufferedWriterHashMap.get( castedNode.getGeneEntrezId().toString());
						count = nameorIdHashMap.get( castedNode.getGeneEntrezId().toString());

						if( bufferedWriter == null){
							fileWriter = FileOperations.createFileWriter( Commons.NCBI_GENE_ID_ANNOTATION_DIRECTORY + "_" + String.valueOf( castedNode.getGeneEntrezId()) + ".txt");
							bufferedWriter = new BufferedWriter( fileWriter);
							bufferedWriterHashMap.put( castedNode.getGeneEntrezId().toString(), bufferedWriter);

						}

						bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
						bufferedWriter.close();

						count++;
						nameorIdHashMap.put( castedNode.getGeneEntrezId().toString(), count);
					}
				}else if( Commons.NCBI_RNA_NUCLEOTIDE_ACCESSION_VERSION.equals( type)){
					if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh()) && nameorIdHashMap.containsKey( castedNode.getRefSeqGeneName())){

						bufferedWriter = bufferedWriterHashMap.get( castedNode.getRefSeqGeneName());
						count = nameorIdHashMap.get( castedNode.getRefSeqGeneName());

						if( bufferedWriter == null){
							fileWriter = FileOperations.createFileWriter( Commons.NCBI_RNA_ANNOTATION_DIRECTORY + "_" + castedNode.getRefSeqGeneName() + ".txt");
							bufferedWriter = new BufferedWriter( fileWriter);
							bufferedWriterHashMap.put( castedNode.getRefSeqGeneName(), bufferedWriter);
						}

						bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
						bufferedWriter.close();

						count++;
						nameorIdHashMap.put( castedNode.getRefSeqGeneName(), count);

					}
				}else if( Commons.UCSC_GENE_ALTERNATE_NAME.equals( type)){
					if( overlaps( castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh()) && nameorIdHashMap.containsKey( castedNode.getGeneHugoSymbol())){

						bufferedWriter = bufferedWriterHashMap.get( castedNode.getGeneHugoSymbol());
						count = nameorIdHashMap.get( castedNode.getGeneHugoSymbol());

						if( bufferedWriter == null){
							fileWriter = FileOperations.createFileWriter( Commons.UCSC_GENE_ALTERNATE_NAME_ANNOTATION_DIRECTORY + "_" + castedNode.getGeneHugoSymbol() + ".txt");
							bufferedWriter = new BufferedWriter( fileWriter);
							bufferedWriterHashMap.put( castedNode.getGeneHugoSymbol(), bufferedWriter);
						}

						bufferedWriter.write( "Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
						bufferedWriter.close();

						count++;
						nameorIdHashMap.put( castedNode.getGeneHugoSymbol(), count);
					}
				}

				if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
					findAllOverlappingUcscRefSeqGenesIntervals( ( UcscRefSeqGeneIntervalTreeNode)node.getLeft(),
							interval, chromName, bufferedWriterHashMap, nameorIdHashMap, type);
				}

				if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
					findAllOverlappingUcscRefSeqGenesIntervals( ( UcscRefSeqGeneIntervalTreeNode)node.getRight(),
							interval, chromName, bufferedWriterHashMap, nameorIdHashMap, type);

				}
			} // If node is not null

		}catch( IOException e){
			if( GlanetRunner.shouldLog())logger.error( e.toString());
		}
	}

	// Search1
	public void findAllOverlappingUcscRefSeqGenesIntervals( IntervalTreeNode node, Interval interval,
			BufferedWriter bufferedWriter, List<IntervalTreeNode> overlappingNodeList) {

		UcscRefSeqGeneIntervalTreeNode castedNode = null;

		if( overlaps( node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){

			overlappingNodeList.add( node);

			if( node instanceof UcscRefSeqGeneIntervalTreeNode){
				castedNode = ( UcscRefSeqGeneIntervalTreeNode)node;
			}

			try{
				bufferedWriter.write( "ucscRefSeqGene" + "\t" + castedNode.getChromName().toString() + "\t" + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName().toString() + "\t" + castedNode.getIntervalName().toString() + "\t" + castedNode.getGeneHugoSymbol().toString() + "\t" + castedNode.getGeneEntrezId() + System.getProperty( "line.separator"));
				bufferedWriter.flush();

			}catch( IOException e){

				if( GlanetRunner.shouldLog())logger.error( e.toString());
			}
		}

		if( ( node.getLeft().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getLeft().getMax())){
			findAllOverlappingUcscRefSeqGenesIntervals( ( UcscRefSeqGeneIntervalTreeNode)node.getLeft(), interval,
					bufferedWriter, overlappingNodeList);
		}

		if( ( node.getRight().getNodeName().isNotSentinel()) && ( interval.getLow() <= node.getRight().getMax()) && ( node.getLow() <= interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervals( ( UcscRefSeqGeneIntervalTreeNode)node.getRight(), interval,
					bufferedWriter, overlappingNodeList);

		}

	}

	public void printOverlappingIntervalsList( List<IntervalTreeNode> list) {

		Iterator<IntervalTreeNode> itr = list.iterator();

		while( itr.hasNext()){
			IntervalTreeNode node = ( IntervalTreeNode)itr.next();
			GlanetRunner.appendLog( node.getLow() + "\t" + node.getHigh());

		}
	}

	public static void printPath( Path path) {

		IntervalTreeNode node;
		int numberofBlackNodes = 0;

		GlanetRunner.appendLog( "One Path Starts");

		for( int i = 0; i < path.getNodeList().size(); i++){
			node = path.getNodeList().get( i);

			if( node.getColor() == Commons.BLACK)
				numberofBlackNodes++;

			GlanetRunner.appendLog( "Node(" + node.getLow() + "," + node.getHigh() + ",Max: " + node.getMax() + ") Color: " + node.getColor() + " Name: " + node.getNodeName().convertEnumtoString());

		}

		GlanetRunner.appendLog( "--- Number of black nodes: " + numberofBlackNodes);

	}

	public static void findAllPathsFromRootToLeavesUsingDepthFirstSearch( IntervalTreeNode node,
			IntervalTreeProperties properties, Path path, List<Path> allPathsFromRootToLeaves) {

		// Null node
		if( node == null){
			return;
		}
		// SENTINEL or NOT_SENTINEL_NODE
		else{
			// SENTINEL NODE
			if( node.getNodeName().isSentinel()){

				if( node.getColor() != Commons.BLACK){
					properties.setEverySentinelLeafIsBlack( false);
					return;
				}

				path.getNodeList().add( node);

				if( node.getColor() == Commons.BLACK){
					path.setNumberofBlackNodes( path.getNumberofBlackNodes() + 1);
				}

				// A leaf has been reached
				// Create a new Path object
				// Since path instance changes rapidly during recursion

				Path onePathFromRootToLeaf = new Path( path.getNumberofBlackNodes(), path.getNodeList());
				// printPath(onePathFromRootToLeaf);
				allPathsFromRootToLeaves.add( onePathFromRootToLeaf);
			}

			// NOT SENTINEL NODE
			else if( node.getNodeName().isNotSentinel()){
				if( node.getColor() != Commons.RED && node.getColor() != Commons.BLACK){
					properties.setEveryNodeIsEitherRedorBlack( false);
					return;
				}

				if( node.getColor() == Commons.RED){
					if( node.getLeft().getColor() != Commons.BLACK && node.getRight().getColor() != Commons.BLACK){
						properties.setEveryRedNodeHasBlackChildren( false);
						return;
					}
				}

				if( node.getMax() != max( node)){
					properties.setEveryNotSentinelNodeHasRightMaxValue( false);
					return;

				}

				path.getNodeList().add( node);

				if( node.getColor() == Commons.BLACK){
					path.setNumberofBlackNodes( path.getNumberofBlackNodes() + 1);
				}

				findAllPathsFromRootToLeavesUsingDepthFirstSearch( node.getLeft(), properties, path,
						allPathsFromRootToLeaves);
				findAllPathsFromRootToLeavesUsingDepthFirstSearch( node.getRight(), properties, path,
						allPathsFromRootToLeaves);

			}
		} // SENTINEL or NOT_SENTINEL node

		path.getNodeList().remove( node);

		if( node.getColor() == Commons.BLACK){
			path.setNumberofBlackNodes( path.getNumberofBlackNodes() - 1);
		}
	}

	public static void checkNumberofBlackNodesinAllPathsFromRootToLeaves( IntervalTreeProperties properties,
			List<Path> allPaths) {

		int numberofBlackNodes = 0;

		if( allPaths.size() > 0){
			numberofBlackNodes = allPaths.get( 0).getNumberofBlackNodes();
		}

		for( int i = 1; i < allPaths.size(); i++){
			if( numberofBlackNodes != allPaths.get( i).getNumberofBlackNodes()){
				properties.setAllPathsfromRoottoLeavesHasSameNumberofBlackNodes( false);
				break;
			}
		}
	}

	/*
	 * Traverse the tree using depth first search And check whether the
	 * properties hold or not
	 */

	public static void checkProperties( IntervalTree tree, IntervalTreeProperties properties) {

		Path path = new Path();
		List<Path> allPathsFromRoottoLeaves = new ArrayList<Path>();

		findAllPathsFromRootToLeavesUsingDepthFirstSearch( tree.getRoot(), properties, path, allPathsFromRoottoLeaves);

		checkNumberofBlackNodesinAllPathsFromRootToLeaves( properties, allPathsFromRoottoLeaves);

		return;
	}

	/*
	 * Check for whether the given tree holds the red black tree properties
	 * 
	 * Red black tree properties: 1. Every node is either red or black. 2. The
	 * root is black. 3. Every leaf(nil[T]) is black. 4. If a node is red, then
	 * both its children are black. (Hence no two reds in a row on a simple path
	 * from the root to a leaf.) 5. For each node, all paths from the node to
	 * descendant leaves contain the same number of black nodes.
	 */
	public static boolean checkIntervalTreePropertiesHolds( IntervalTree tree) {

		boolean intevalTreePropertiesHold = true;

		IntervalTreeProperties properties = new IntervalTreeProperties();

		if( tree.getRoot().getColor() != Commons.BLACK)
			properties.setRootIsBlack( false);

		checkProperties( tree, properties);

		intevalTreePropertiesHold = properties.isEveryNodeIsEitherRedorBlack() && properties.isRootIsBlack() && properties.isEverySentinelLeafIsBlack() && properties.isEveryRedNodeHasBlackChildren() && properties.isAllPathsfromRoottoLeavesHasSameNumberofBlackNodes() && properties.isEveryNotSentinelNodeHasRightMaxValue();

		return intevalTreePropertiesHold;

	}

	public static void intervalTreeInsertDelete( IntervalTree tree, IntervalTreeNode node, String operation) {

		boolean isIntervalTreePropertiesHold;
		IntervalTreeNode splicedOutNode;

		if( Commons.INSERT.equals( operation)){
			GlanetRunner.appendLog( "After insert node (" + node.getLow() + "," + node.getHigh() + ")");
			tree.intervalTreeInsert( tree, node);
			GlanetRunner.appendLog( "Tree Root color: " + tree.getRoot().getColor() + " Tree Root Low: " + tree.getRoot().getLow() + " Tree Root High: " + tree.getRoot().getHigh() + " Tree Root Max: " + tree.getRoot().getMax() + " Tree Root's Parent's Name: " + tree.getRoot().getParent().getNodeName().convertEnumtoString());
			tree.intervalTreeInfixTraversal( tree.getRoot());
			isIntervalTreePropertiesHold = checkIntervalTreePropertiesHolds( tree);
			GlanetRunner.appendLog( "Does the interval tree properties hold? " + isIntervalTreePropertiesHold);

		}else if( Commons.DELETE.equals( operation)){
			GlanetRunner.appendLog( "After delete node (" + node.getLow() + "," + node.getHigh() + ")");
			splicedOutNode = tree.intervalTreeDelete( tree, node);
			GlanetRunner.appendLog( "splicedOutNode color: " + splicedOutNode.getColor() + " splicedOutNode Low: " + splicedOutNode.getLow() + " splicedOutNode High: " + splicedOutNode.getHigh() + " splicedOutNode Max: " + splicedOutNode.getMax());
			splicedOutNode = null;
			GlanetRunner.appendLog( "Tree Root color: " + tree.getRoot().getColor() + " Tree Root Low: " + tree.getRoot().getLow() + " Tree Root High: " + tree.getRoot().getHigh() + " Tree Root Max: " + tree.getRoot().getMax() + " Tree Root's Parent's Name: " + tree.getRoot().getParent().getNodeName().convertEnumtoString());
			tree.intervalTreeInfixTraversal( tree.getRoot());
			isIntervalTreePropertiesHold = checkIntervalTreePropertiesHolds( tree);
			GlanetRunner.appendLog( "Does the interval tree properties hold? " + isIntervalTreePropertiesHold);

		}

		GlanetRunner.appendLog( "-------------------------------------------------------------------------------");

	}

	/*
	 * IntervalTree is composed of sentinel and not sentinel nodes. Sentinel
	 * nodes are the leaf nodes. Sentinel nodes have left and right nodes null.
	 * 
	 * Not sentinel nodes are the not leaf nodes. Not sentinel nodes have not
	 * null left and right nodes.
	 */
	public static void deleteNodesofIntervalTree( IntervalTreeNode node) {

		if( node.getNodeName().isNotSentinel()){
			deleteNodesofIntervalTree( node.getLeft());
			deleteNodesofIntervalTree( node.getRight());
			node.setChromName( null);
			node.setLeft( null);
			node.setRight( null);
			node.setParent( null);
			node.setNodeName( null);
			node.setNodeType( null);
			node = null;
		}else if( node.getNodeName().isSentinel()){
			node.setParent( null);
			node = null;
		}

	}

	public static void main( String[] args) {

		IntervalTree tree = new IntervalTree();
		boolean isIntervalTreePropertiesHold;

		// Check whether interval tree properties hold when the tree is null?
		isIntervalTreePropertiesHold = checkIntervalTreePropertiesHolds( tree);

		System.out.println( "isIntervalTreePropertiesHold: " + isIntervalTreePropertiesHold);

		// List<IntervalTreeNode> resultList ;

		IntervalTreeNode node1 = new IntervalTreeNode( 10, 15);
		IntervalTreeNode node_1 = new IntervalTreeNode( 10, 15);
		IntervalTreeNode node_2 = new IntervalTreeNode( 10, 15);
		IntervalTreeNode node2 = new IntervalTreeNode( 5, 40);
		IntervalTreeNode node3 = new IntervalTreeNode( 15, 60);
		IntervalTreeNode node4 = new IntervalTreeNode( 2, 80);
		IntervalTreeNode node5 = new IntervalTreeNode( 1, 100);
		IntervalTreeNode node6 = new IntervalTreeNode( 3, 150);
		IntervalTreeNode node7 = new IntervalTreeNode( 6, 36);
		IntervalTreeNode node8 = new IntervalTreeNode( 7, 77);
		IntervalTreeNode node9 = new IntervalTreeNode( 8, 200);
		IntervalTreeNode node10 = new IntervalTreeNode( 4, 20);
		IntervalTreeNode node11 = new IntervalTreeNode( 40, 140);
		IntervalTreeNode node12 = new IntervalTreeNode( 60, 120);
		IntervalTreeNode node13 = new IntervalTreeNode( 50, 150);
		IntervalTreeNode node14 = new IntervalTreeNode( 55, 90);
		// IntervalTreeNode node15 = new IntervalTreeNode(30,40);

		intervalTreeInsertDelete( tree, node1, Commons.INSERT);
		intervalTreeInsertDelete( tree, node_1, Commons.INSERT);
		intervalTreeInsertDelete( tree, node_2, Commons.INSERT);
		intervalTreeInsertDelete( tree, node2, Commons.INSERT);
		intervalTreeInsertDelete( tree, node3, Commons.INSERT);
		intervalTreeInsertDelete( tree, node4, Commons.INSERT);
		intervalTreeInsertDelete( tree, node5, Commons.INSERT);
		intervalTreeInsertDelete( tree, node6, Commons.INSERT);
		intervalTreeInsertDelete( tree, node7, Commons.INSERT);
		intervalTreeInsertDelete( tree, node8, Commons.INSERT);
		intervalTreeInsertDelete( tree, node9, Commons.INSERT);
		intervalTreeInsertDelete( tree, node10, Commons.INSERT);
		intervalTreeInsertDelete( tree, node11, Commons.INSERT);
		intervalTreeInsertDelete( tree, node12, Commons.INSERT);
		intervalTreeInsertDelete( tree, node13, Commons.INSERT);
		intervalTreeInsertDelete( tree, node14, Commons.INSERT);

		intervalTreeInsertDelete( tree, node2, Commons.DELETE);
		intervalTreeInsertDelete( tree, node8, Commons.DELETE);
		intervalTreeInsertDelete( tree, node12, Commons.DELETE);
		intervalTreeInsertDelete( tree, node4, Commons.DELETE);
		intervalTreeInsertDelete( tree, node14, Commons.DELETE);
		intervalTreeInsertDelete( tree, node_1, Commons.DELETE);
		intervalTreeInsertDelete( tree, tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete( tree, node2, Commons.DELETE);
		intervalTreeInsertDelete( tree, tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete( tree, tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete( tree, tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete( tree, tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete( tree, tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete( tree, tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete( tree, tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete( tree, tree.getRoot(), Commons.DELETE);

		// Deletion of erroneous data: no NOT_SENTINEL node has been left
		// intervalTreeInsertDelete(tree,tree.getRoot(), Commons.DELETE);

		// Deletion of erroneous data: non existing node
		// GlanetRunner.appendLog("After delete node (" + node15.getLow() +"," +
		// node15.getHigh() +")");
		// node = tree.intervalTreeDelete(tree, node15);
		// node = null;
		// GlanetRunner.appendLog("Tree root name: "+
		// tree.getRoot().getNodeName() + " Tree Root color: " +
		// tree.getRoot().getColor()+ " Tree Root Low: " +
		// tree.getRoot().getLow() + " Tree Root High: "
		// +tree.getRoot().getHigh() + " Tree Root Max: " +
		// tree.getRoot().getMax() + " Tree Root's Parent's Name: " +
		// tree.getRoot().getParent().getNodeName() );
		// tree.intervalTreeInfixTraversal(tree.getRoot());

		// GlanetRunner.appendLog("Overlapping Intervals");
		// tree.findAllOverlappingIntervals(tree.getRoot(), new Interval(5,20));

		// tree.printOverlappingIntervalsList(resultList);

	}

}
