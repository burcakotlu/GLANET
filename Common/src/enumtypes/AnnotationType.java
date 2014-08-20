/**
 * @author burcakotlu
 * @date Jun 23, 2014 
 * @time 4:09:39 PM
 */
package enumtypes;

import common.Commons;

/**
 * 
 */
public enum AnnotationType {
	
	DNASE_ANNOTATION(1),
	TF_ANNOTATION(2),
	HISTONE_ANNOTATION(3),
	GENE_SET_ANNOTATION(4),
	TF_GENE_SET_ANNOTATION(5),
	TF_CELLLINE_GENE_SET_ANNOTATION(6),
	BOTH_TF_GENESET_AND_TF_CELLLINE_GENESET_ANNOTATION(7);
	
	private final int annotationType;
	
	/* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private AnnotationType(int annotationType) {
    	this.annotationType = annotationType;
	}
    
    public static AnnotationType convertStringtoEnum(String annotationType){
    	
    	if (Commons.DNASE_ANNOTATION.equals(annotationType)){
    		return DNASE_ANNOTATION;
    	}else if  (Commons.HISTONE_ANNOTATION.equals(annotationType)){
    		return HISTONE_ANNOTATION;
    	}else if  (Commons.TF_ANNOTATION.equals(annotationType)){
    		return TF_ANNOTATION;
    	}else if  (Commons.GENE_SET_ANNOTATION.equals(annotationType)){
    		return GENE_SET_ANNOTATION; 		
    	}else if  (Commons.TF_GENE_SET_ANNOTATION.equals(annotationType)){
    		return TF_GENE_SET_ANNOTATION;
    	}else if  (Commons.TF_CELLLINE_GENE_SET_ANNOTATION.equals(annotationType)){
    		return TF_CELLLINE_GENE_SET_ANNOTATION;
    	}else if (Commons.BOTH_TF_GENESET_AND_TF_CELLLINE_GENESET_ANNOTATION.equals(annotationType)){
    		return BOTH_TF_GENESET_AND_TF_CELLLINE_GENESET_ANNOTATION;
    	}else     	
    		return null;
    }
    
    public String getAnnotationType(){
    	if (this.equals(AnnotationType.DNASE_ANNOTATION))
    		return Commons.DNASE_ANNOTATION;
    	else if (this.equals(AnnotationType.HISTONE_ANNOTATION))	
    		return Commons.HISTONE_ANNOTATION; 	
    	else if (this.equals(AnnotationType.TF_ANNOTATION))
    		return Commons.TF_ANNOTATION;
    	else if (this.equals(AnnotationType.GENE_SET_ANNOTATION))
    		return Commons.GENE_SET_ANNOTATION;    	
    	else if (this.equals(AnnotationType.TF_GENE_SET_ANNOTATION))
    		return Commons.TF_GENE_SET_ANNOTATION;
    	else if (this.equals(AnnotationType.TF_CELLLINE_GENE_SET_ANNOTATION))
    		return Commons.TF_CELLLINE_GENE_SET_ANNOTATION;  	
    	else if (this.equals(AnnotationType.BOTH_TF_GENESET_AND_TF_CELLLINE_GENESET_ANNOTATION))
    		return Commons.BOTH_TF_GENESET_AND_TF_CELLLINE_GENESET_ANNOTATION;
    	else
    		return null;   		
    }
    
    /** An added method.  */
    public boolean isDnaseAnnotation() {
        return  this == DNASE_ANNOTATION;
    }
	
    /** An added method.  */
    public boolean isHistoneAnnotation() {
        return  this == HISTONE_ANNOTATION;
    }
    
    /** An added method.  */
    public boolean isTfAnnotation() {
        return  this == TF_ANNOTATION;
    }
    
    /** An added method.  */
    public boolean isGeneSetAnnotation() {
        return  this == GENE_SET_ANNOTATION;
    }
    
    /** An added method.  */
    public boolean isTfGeneSetAnnotation() {
        return  this == TF_GENE_SET_ANNOTATION;
    }
    
    /** An added method.  */
    public boolean isTfCellLineGeneSetAnnotation() {
        return  this == TF_CELLLINE_GENE_SET_ANNOTATION;
    }
	    
    /** An added method.  */
    public boolean isBothTfGeneSetAndTfCellLineGeneSetAnnotation() {
        return  this == BOTH_TF_GENESET_AND_TF_CELLLINE_GENESET_ANNOTATION;
    }
}
