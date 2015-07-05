/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Bur�ak Otlu
 * @date Feb 20, 2015
 * @project Common 
 *
 */
public enum WriteElementBasedAnnotationFoundOverlapsMode {

	DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS( 1), DO_NOT_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS( 2);

	private final int writeElementBasedAnnotationFoundOverlapsMode;

	public int getWriteElementBasedAnnotationFoundOverlapsMode() {

		return writeElementBasedAnnotationFoundOverlapsMode;
	}

	private WriteElementBasedAnnotationFoundOverlapsMode( int writeElementBasedAnnotationFoundOverlapsMode) {

		this.writeElementBasedAnnotationFoundOverlapsMode = writeElementBasedAnnotationFoundOverlapsMode;
	}

	public static WriteElementBasedAnnotationFoundOverlapsMode convertStringtoEnum(
			String writeElementBasedAnnotationFoundOverlapsMode) {

		if( Commons.DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS.equals( writeElementBasedAnnotationFoundOverlapsMode)){
			return DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS;
		}else if( Commons.DO_NOT_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS.equals( writeElementBasedAnnotationFoundOverlapsMode)){
			return DO_NOT_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS))
			return Commons.DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS;
		else if( this.equals( DO_NOT_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS))
			return Commons.DO_NOT_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS;
		else
			return null;

	}

	/** An added method.  */
	public boolean isWriteElementBasedAnnotationFoundOverlaps() {

		return this == WriteElementBasedAnnotationFoundOverlapsMode.DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS;
	}

}
