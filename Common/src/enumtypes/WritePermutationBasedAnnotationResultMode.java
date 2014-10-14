/**
 * @author burcakotlu
 * @date Jun 23, 2014 
 * @time 12:12:26 PM
 */
package enumtypes;

import common.Commons;

/**
 * 
 */
public enum WritePermutationBasedAnnotationResultMode {
	
	WRITE_PERMUTATION_BASED_ANNOTATION_RESULT(1),
	DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT(2);
	
	private final int writePermutationBasedAnnotationResultMode;
	
	
	
	public int getWritePermutationBasedAnnotationResultMode() {
		return writePermutationBasedAnnotationResultMode;
	}


	private WritePermutationBasedAnnotationResultMode(int writePermutationBasedAnnotationResultMode) {
    	this.writePermutationBasedAnnotationResultMode = writePermutationBasedAnnotationResultMode;
	}
	

	public static WritePermutationBasedAnnotationResultMode convertStringtoEnum(String writePermutationBasedAnnotationResultMode){
    	
    	if (Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT.equals(writePermutationBasedAnnotationResultMode)){
    		return WRITE_PERMUTATION_BASED_ANNOTATION_RESULT;
    	}else if  (Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT.equals(writePermutationBasedAnnotationResultMode)){
    		return DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT;
    	}else
    		return null;
	}
	
	public String convertEnumtoString(){
    	if (this.equals(WritePermutationBasedAnnotationResultMode.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT))
    		return Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT;
    	else if (this.equals(WritePermutationBasedAnnotationResultMode.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT))
    		return Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT;
    	else
    		return null;
    				   		
	}
	
	/** An added method.  */
    public boolean isWritePermutationBasedAnnotationResultMode() {
        return  this == WRITE_PERMUTATION_BASED_ANNOTATION_RESULT;
    }
	
}
