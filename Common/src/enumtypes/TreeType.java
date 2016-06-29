/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Jun 29, 2016
 * @project Common 
 *
 */
public enum TreeType {
	
	INTERVAL_TREE_CORMEN(1),
	INTERVAL_TREE_MARKDEBERG(2),
	SEGMENT_TREE_MARKDEBERG(3);

	private final int treeType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private TreeType(int treeType) {

		this.treeType = treeType;
	}

	public int getTreeType() {

		return treeType;
	}

	public static TreeType convertStringtoEnum(String treeType) {

		if( Commons.INTERVAL_TREE_CORMEN.equals(treeType)){
			return INTERVAL_TREE_CORMEN;
		}else if( Commons.INTERVAL_TREE_MARKDEBERG.equals(treeType)){
			return INTERVAL_TREE_MARKDEBERG;
		}else if( Commons.SEGMENT_TREE_MARKDEBERG.equals(treeType)){
			return SEGMENT_TREE_MARKDEBERG;
		}
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( TreeType.INTERVAL_TREE_CORMEN))
			return Commons.INTERVAL_TREE_CORMEN;
		else if( this.equals( TreeType.INTERVAL_TREE_MARKDEBERG))
			return Commons.INTERVAL_TREE_MARKDEBERG;
		else if( this.equals( TreeType.SEGMENT_TREE_MARKDEBERG))
			return Commons.SEGMENT_TREE_MARKDEBERG;
		else
			return null;
	}

	public boolean isIntervalTreeCormen() {
		return this == INTERVAL_TREE_CORMEN;
	}

	public boolean isIntervalTreeMarkdeBerg() {
		return this == INTERVAL_TREE_MARKDEBERG;
	}

	public boolean isSegmentTreeMarkdeBerg() {
		return this == SEGMENT_TREE_MARKDEBERG;
	}


}
