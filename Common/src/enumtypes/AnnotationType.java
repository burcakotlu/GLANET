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
	USER_DEFINED_GENE_SET_ANNOTATION(4),
	USER_DEFINED_LIBRARY_ANNOTATION(5),
	KEGG_PATHWAY_ANNOTATION(6),
	TF_KEGG_PATHWAY_ANNOTATION(7),
	TF_CELLLINE_KEGG_PATHWAY_ANNOTATION(8),
	BOTH_TF_KEGG_PATHWAY_AND_TF_CELLLINE_KEGG_PATHWAY_ANNOTATION(9);
	
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
    
    public int getAnnotationType(){
    	return annotationType;
    }
        
    
    public static AnnotationType convertStringtoEnum(String annotationType){
    	
    	if (Commons.DNASE_ANNOTATION.equals(annotationType)){
    		return DNASE_ANNOTATION;
    	}else if  (Commons.HISTONE_ANNOTATION.equals(annotationType)){
    		return HISTONE_ANNOTATION;
    	}else if  (Commons.TF_ANNOTATION.equals(annotationType)){
    		return TF_ANNOTATION;
    	}else if (Commons.USER_DEFINED_GENE_SET_ANNOTATION.equals(annotationType)){
    		return USER_DEFINED_GENE_SET_ANNOTATION;
    	}else if (Commons.USER_DEFINED_LIBRARY_ANNOTATION.equals(annotationType)){
    		return USER_DEFINED_LIBRARY_ANNOTATION;
    	}else if  (Commons.KEGG_PATHWAY_ANNOTATION.equals(annotationType)){
    		return KEGG_PATHWAY_ANNOTATION; 		
    	}else if  (Commons.TF_KEGG_PATHWAY_ANNOTATION.equals(annotationType)){
    		return TF_KEGG_PATHWAY_ANNOTATION;
    	}else if  (Commons.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION.equals(annotationType)){
    		return TF_CELLLINE_KEGG_PATHWAY_ANNOTATION;
    	}else if (Commons.BOTH_TF_KEGG_PATHWAY_AND_TF_CELLLINE_KEGG_PATHWAY_ANNOTATION.equals(annotationType)){
    		return BOTH_TF_KEGG_PATHWAY_AND_TF_CELLLINE_KEGG_PATHWAY_ANNOTATION;
    	}else     	
    		return null;
    }
    
    public String convertEnumtoString(){
    	if (this.equals(AnnotationType.DNASE_ANNOTATION))
    		return Commons.DNASE_ANNOTATION;
    	else if (this.equals(AnnotationType.HISTONE_ANNOTATION))	
    		return Commons.HISTONE_ANNOTATION; 	
    	else if (this.equals(AnnotationType.TF_ANNOTATION))
    		return Commons.TF_ANNOTATION;
    	else if (this.equals(AnnotationType.USER_DEFINED_GENE_SET_ANNOTATION))
    		return Commons.USER_DEFINED_GENE_SET_ANNOTATION;
    	else if (this.equals(AnnotationType.USER_DEFINED_LIBRARY_ANNOTATION))
    		return Commons.USER_DEFINED_LIBRARY_ANNOTATION;
    	else if (this.equals(AnnotationType.KEGG_PATHWAY_ANNOTATION))
    		return Commons.KEGG_PATHWAY_ANNOTATION;    	
    	else if (this.equals(AnnotationType.TF_KEGG_PATHWAY_ANNOTATION))
    		return Commons.TF_KEGG_PATHWAY_ANNOTATION;
    	else if (this.equals(AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION))
    		return Commons.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION;  	
    	else if (this.equals(AnnotationType.BOTH_TF_KEGG_PATHWAY_AND_TF_CELLLINE_KEGG_PATHWAY_ANNOTATION))
    		return Commons.BOTH_TF_KEGG_PATHWAY_AND_TF_CELLLINE_KEGG_PATHWAY_ANNOTATION;
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
    
    public boolean isUserDefinedGeneSetAnnotation(){
    	return  this == USER_DEFINED_GENE_SET_ANNOTATION;
    }
    
    
    public boolean isUserDefinedLibraryAnnotation(){
    	return  this == USER_DEFINED_LIBRARY_ANNOTATION;
    }
    
    /** An added method.  */
    public boolean isKeggPathwayAnnotation() {
        return  this == KEGG_PATHWAY_ANNOTATION;
    }
    
    /** An added method.  */
    public boolean isTfKeggPathwayAnnotation() {
        return  this == TF_KEGG_PATHWAY_ANNOTATION;
    }
    
    /** An added method.  */
    public boolean isTfCellLineKeggPathwayAnnotation() {
        return  this == TF_CELLLINE_KEGG_PATHWAY_ANNOTATION;
    }
	    
    /** An added method.  */
    public boolean isBothTfKeggPathwayAndTfCellLineKeggPathwayAnnotation() {
        return  this == BOTH_TF_KEGG_PATHWAY_AND_TF_CELLLINE_KEGG_PATHWAY_ANNOTATION;
    }
}
