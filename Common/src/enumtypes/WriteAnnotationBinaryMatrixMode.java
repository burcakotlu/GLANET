/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Feb 20, 2015
 * @project Common 
 *
 */
public enum WriteAnnotationBinaryMatrixMode {
	
	DO_WRITE_ANNOTATION_BINARY_MATRIX(1),
	DO_NOT_WRITE_ANNOTATION_BINARY_MATRIX(2);
	
	private final int writeAnnotationBinaryMatrixMode;

	public int getWriteAnnotationBinaryMatrixMode() {
		return writeAnnotationBinaryMatrixMode;
	}

	private WriteAnnotationBinaryMatrixMode(int writeAnnotationBinaryMatrixMode) {
    	this.writeAnnotationBinaryMatrixMode = writeAnnotationBinaryMatrixMode;
	}
	
	public static WriteAnnotationBinaryMatrixMode convertStringtoEnum(String writeElementBasedAnnotationFoundOverlapsMode){
    	
    	if (Commons.DO_WRITE_ANNOTATION_BINARY_MATRIX.equals(writeElementBasedAnnotationFoundOverlapsMode)){
    		return DO_WRITE_ANNOTATION_BINARY_MATRIX;
    	}else if  (Commons.DO_NOT_WRITE_ANNOTATION_BINARY_MATRIX.equals(writeElementBasedAnnotationFoundOverlapsMode)){
    		return DO_NOT_WRITE_ANNOTATION_BINARY_MATRIX;
    	}else
    		return null;
	}
	
	public String convertEnumtoString(){
    	if (this.equals(DO_WRITE_ANNOTATION_BINARY_MATRIX))
    		return Commons.DO_WRITE_ANNOTATION_BINARY_MATRIX;
    	else if (this.equals(DO_NOT_WRITE_ANNOTATION_BINARY_MATRIX))
    		return Commons.DO_NOT_WRITE_ANNOTATION_BINARY_MATRIX;
    	else
    		return null;
    				   		
	}
	
	 /** An added method.  */
    public boolean doWriteAnnotationBinaryMatrixMode() {
        return  this == WriteAnnotationBinaryMatrixMode.DO_WRITE_ANNOTATION_BINARY_MATRIX;
    }


}
