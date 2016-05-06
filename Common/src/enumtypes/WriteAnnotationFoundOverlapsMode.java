/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burcak Otlu
 * @date Feb 20, 2015
 * @project Common 
 *
 */
public enum WriteAnnotationFoundOverlapsMode {

	DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS(1),
	DO_WRITE_ELEMENT_TYPE_BASED_ANNOTATION_FOUND_OVERLAPS(2),
	DO_NOT_WRITE_ANNOTATION_FOUND_OVERLAPS_AT_ALL(3);
	
	private final int writeAnnotationFoundOverlapsMode;

	public int getWriteAnnotationFoundOverlapsMode() {
		return writeAnnotationFoundOverlapsMode;
	}

	private WriteAnnotationFoundOverlapsMode(int writeAnnotationFoundOverlapsMode) {

		this.writeAnnotationFoundOverlapsMode = writeAnnotationFoundOverlapsMode;
	}

	public static WriteAnnotationFoundOverlapsMode convertStringtoEnum(
			String writeAnnotationFoundOverlapsMode) {

		if( Commons.DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS.equals(writeAnnotationFoundOverlapsMode)){
			return DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS;
			
		}else if( Commons.DO_WRITE_ELEMENT_TYPE_BASED_ANNOTATION_FOUND_OVERLAPS.equals(writeAnnotationFoundOverlapsMode)){
			return DO_WRITE_ELEMENT_TYPE_BASED_ANNOTATION_FOUND_OVERLAPS;
		
		}else if( Commons.DO_NOT_WRITE_ANNOTATION_FOUND_OVERLAPS_AT_ALL.equals(writeAnnotationFoundOverlapsMode)){
			return DO_NOT_WRITE_ANNOTATION_FOUND_OVERLAPS_AT_ALL;
		}
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS))
			return Commons.DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS;
		
		else if( this.equals(DO_WRITE_ELEMENT_TYPE_BASED_ANNOTATION_FOUND_OVERLAPS))
			return Commons.DO_WRITE_ELEMENT_TYPE_BASED_ANNOTATION_FOUND_OVERLAPS;
		
		else if( this.equals(DO_NOT_WRITE_ANNOTATION_FOUND_OVERLAPS_AT_ALL))
			return Commons.DO_NOT_WRITE_ANNOTATION_FOUND_OVERLAPS_AT_ALL;
		
		else
			return null;

	}

	/** An added method.  */
	public boolean isWriteElementBasedAnnotationFoundOverlaps() {
		return this == WriteAnnotationFoundOverlapsMode.DO_WRITE_ELEMENT_BASED_ANNOTATION_FOUND_OVERLAPS;
	}

	public boolean isWriteElementTypeBasedAnnotationFoundOverlaps() {
		return this == WriteAnnotationFoundOverlapsMode.DO_WRITE_ELEMENT_TYPE_BASED_ANNOTATION_FOUND_OVERLAPS;
	}
	
	public boolean isDoNotWriteAnnotationFoundOverlapsAtAll() {
		return this == WriteAnnotationFoundOverlapsMode.DO_NOT_WRITE_ANNOTATION_FOUND_OVERLAPS_AT_ALL;
	}
}
