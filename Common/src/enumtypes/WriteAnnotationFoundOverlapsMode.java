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
	

	DO_WRITE_OVERLAPS_EACH_ONE_IN_SEPARATE_FILE_ELEMENT_BASED(1),
	DO_WRITE_OVERLAPS_ALL_IN_ONE_FILE_ELEMENT_TYPE_BASED(2),
	DO_NOT_WRITE_OVERLAPS_AT_ALL(3);
	
	private final int writeAnnotationFoundOverlapsMode;

	public int getWriteAnnotationFoundOverlapsMode() {
		return writeAnnotationFoundOverlapsMode;
	}

	private WriteAnnotationFoundOverlapsMode(int writeAnnotationFoundOverlapsMode) {

		this.writeAnnotationFoundOverlapsMode = writeAnnotationFoundOverlapsMode;
	}

	public static WriteAnnotationFoundOverlapsMode convertStringtoEnum(
			String writeAnnotationFoundOverlapsMode) {

		if( Commons.DO_WRITE_OVERLAPS_EACH_ONE_IN_SEPARATE_FILE_ELEMENT_BASED.equals(writeAnnotationFoundOverlapsMode)){
			return DO_WRITE_OVERLAPS_EACH_ONE_IN_SEPARATE_FILE_ELEMENT_BASED;
			
		}else if( Commons.DO_WRITE_OVERLAPS_ALL_IN_ONE_FILE_ELEMENT_TYPE_BASED.equals(writeAnnotationFoundOverlapsMode)){
			return DO_WRITE_OVERLAPS_ALL_IN_ONE_FILE_ELEMENT_TYPE_BASED;
		
		}else if( Commons.DO_NOT_WRITE_OVERLAPS_AT_ALL.equals(writeAnnotationFoundOverlapsMode)){
			return DO_NOT_WRITE_OVERLAPS_AT_ALL;
		}
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( DO_WRITE_OVERLAPS_EACH_ONE_IN_SEPARATE_FILE_ELEMENT_BASED))
			return Commons.DO_WRITE_OVERLAPS_EACH_ONE_IN_SEPARATE_FILE_ELEMENT_BASED;
		
		else if( this.equals(DO_WRITE_OVERLAPS_ALL_IN_ONE_FILE_ELEMENT_TYPE_BASED))
			return Commons.DO_WRITE_OVERLAPS_ALL_IN_ONE_FILE_ELEMENT_TYPE_BASED;
		
		else if( this.equals(DO_NOT_WRITE_OVERLAPS_AT_ALL))
			return Commons.DO_NOT_WRITE_OVERLAPS_AT_ALL;
		
		else
			return null;

	}

	/** An added method.  */
	public boolean isWriteFoundOverlapsElementBased() {
		return this == WriteAnnotationFoundOverlapsMode.DO_WRITE_OVERLAPS_EACH_ONE_IN_SEPARATE_FILE_ELEMENT_BASED;
	}

	public boolean isWriteFoundOverlapsElementTypeBased() {
		return this == WriteAnnotationFoundOverlapsMode.DO_WRITE_OVERLAPS_ALL_IN_ONE_FILE_ELEMENT_TYPE_BASED;
	}
	
	public boolean isDoNotWriteAnnotationFoundOverlapsAtAll() {
		return this == WriteAnnotationFoundOverlapsMode.DO_NOT_WRITE_OVERLAPS_AT_ALL;
	}
}
