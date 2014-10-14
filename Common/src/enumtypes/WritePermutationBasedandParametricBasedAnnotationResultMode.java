/**
 * @author burcakotlu
 * @date Jun 23, 2014 
 * @time 12:05:35 PM
 */
package enumtypes;

import common.Commons;

/**
 * 
 */
public enum WritePermutationBasedandParametricBasedAnnotationResultMode {
	
	WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT(1),
	DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT(2);
	
	private final int writePermutationBasedandParametricBasedAnnotationResultMode;
	
	
	
	public int getWritePermutationBasedandParametricBasedAnnotationResultMode() {
		return writePermutationBasedandParametricBasedAnnotationResultMode;
	}

	private WritePermutationBasedandParametricBasedAnnotationResultMode(int writePermutationBasedandParametricBasedAnnotationResultMode) {
    	this.writePermutationBasedandParametricBasedAnnotationResultMode = writePermutationBasedandParametricBasedAnnotationResultMode;
	}
	
	public static WritePermutationBasedandParametricBasedAnnotationResultMode convertStringtoEnum(String writePermutationBasedandParametricBasedAnnotationResultMode){
    	
    	if (Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT.equals(writePermutationBasedandParametricBasedAnnotationResultMode)){
    		return WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT;
    	}else if  (Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT.equals(writePermutationBasedandParametricBasedAnnotationResultMode)){
    		return DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT;
    	}else
    		return null;
	}
	
	public String convertEnumtoString(){
    	if (this.equals(WritePermutationBasedandParametricBasedAnnotationResultMode.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT))
    		return Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT;
    	else if (this.equals(WritePermutationBasedandParametricBasedAnnotationResultMode.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT))
    		return Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT;
    	else
    		return null;
    				   		
	}
	
	 /** An added method.  */
    public boolean isWritePermutationBasedandParametricBasedAnnotationResultMode() {
        return  this == WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT;
    }

    /** An added method.  */
    public boolean isDoNotWritePermutationBasedandParametricBasedAnnotationResultMode() {
        return  this == DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT;
    }
}
