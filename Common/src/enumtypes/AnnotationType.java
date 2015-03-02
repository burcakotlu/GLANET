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
	
	DO_DNASE_ANNOTATION(1),
	DO_NOT_DNASE_ANNOTATION(2),
	
	DO_TF_ANNOTATION(3),
	DO_NOT_TF_ANNOTATION(4),
	
	DO_HISTONE_ANNOTATION(5),
	DO_NOT_HISTONE_ANNOTATION(6),
	
	DO_GENE_ANNOTATION(7),
	DO_NOT_GENE_ANNOTATION(8),
	
	DO_KEGGPATHWAY_ANNOTATION(9),
	DO_NOT_KEGGPATHWAY_ANNOTATION(10),
	
	DO_TF_KEGGPATHWAY_ANNOTATION(11),
	DO_NOT_TF_KEGGPATHWAY_ANNOTATION(12),
	
	DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION(13),
	DO_NOT_TF_CELLLINE_KEGGPATHWAY_ANNOTATION(14),
	
	DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION(15),
	
	DO_USER_DEFINED_GENESET_ANNOTATION(16),
	DO_NOT_USER_DEFINED_GENESET_ANNOTATION(17),

	DO_USER_DEFINED_LIBRARY_ANNOTATION(18),
	DO_NOT_USER_DEFINED_LIBRARY_ANNOTATION(19);
	
	
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
    	
    	if (Commons.DO_DNASE_ANNOTATION.equals(annotationType)){
    		return DO_DNASE_ANNOTATION;
    	}else if  (Commons.DO_NOT_DNASE_ANNOTATION.equals(annotationType)){
    		return DO_NOT_DNASE_ANNOTATION;
    	}else if  (Commons.DO_TF_ANNOTATION.equals(annotationType)){
    		return DO_TF_ANNOTATION;
    	}else if  (Commons.DO_NOT_TF_ANNOTATION.equals(annotationType)){
    		return DO_NOT_TF_ANNOTATION; 		
    	}else if  (Commons.DO_HISTONE_ANNOTATION.equals(annotationType)){
    		return DO_HISTONE_ANNOTATION;
    	}else if  (Commons.DO_NOT_HISTONE_ANNOTATION.equals(annotationType)){
    		return DO_NOT_HISTONE_ANNOTATION;
    	}else if (Commons.DO_GENE_ANNOTATION.equals(annotationType)){
    		return DO_GENE_ANNOTATION;
    	}else if (Commons.DO_NOT_GENE_ANNOTATION.equals(annotationType)){
    		return DO_NOT_GENE_ANNOTATION;
    	}else if  (Commons.DO_KEGGPATHWAY_ANNOTATION.equals(annotationType)){
    		return DO_KEGGPATHWAY_ANNOTATION;
    	}else if  (Commons.DO_NOT_KEGGPATHWAY_ANNOTATION.equals(annotationType)){
    		return DO_NOT_KEGGPATHWAY_ANNOTATION;
    	}else if  (Commons.DO_TF_KEGGPATHWAY_ANNOTATION.equals(annotationType)){
    		return DO_TF_KEGGPATHWAY_ANNOTATION;
    	}else if  (Commons.DO_NOT_TF_KEGGPATHWAY_ANNOTATION.equals(annotationType)){
    		return DO_NOT_TF_KEGGPATHWAY_ANNOTATION;
    	}else if  (Commons.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.equals(annotationType)){
    		return DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;
    	}else if  (Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.equals(annotationType)){
    		return DO_NOT_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;
    	}else if (Commons.DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION.equals(annotationType)){
    		return DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;
    	}else if (Commons.DO_USER_DEFINED_GENESET_ANNOTATION.equals(annotationType)){
    		return DO_USER_DEFINED_GENESET_ANNOTATION;	
    	}else if (Commons.DO_NOT_USER_DEFINED_GENESET_ANNOTATION.equals(annotationType)){
    		return DO_NOT_USER_DEFINED_GENESET_ANNOTATION;	
    	}else if (Commons.DO_USER_DEFINED_LIBRARY_ANNOTATION.equals(annotationType)){
    		return DO_USER_DEFINED_LIBRARY_ANNOTATION;	
    	}else if (Commons.DO_NOT_USER_DEFINED_LIBRARY_ANNOTATION.equals(annotationType)){
    		return DO_NOT_USER_DEFINED_LIBRARY_ANNOTATION;	
    	}else     	
    		return null;
    	

    	
    }
    
    public String convertEnumtoString(){
    	
    	if (this.equals(AnnotationType.DO_DNASE_ANNOTATION))
    		return Commons.DO_DNASE_ANNOTATION;
    	else if (this.equals(AnnotationType.DO_NOT_DNASE_ANNOTATION))
    		return Commons.DO_NOT_DNASE_ANNOTATION;
    	
    	else if (this.equals(AnnotationType.DO_HISTONE_ANNOTATION))
    		return Commons.DO_HISTONE_ANNOTATION;
    	else if (this.equals(AnnotationType.DO_NOT_HISTONE_ANNOTATION))
    		return Commons.DO_NOT_HISTONE_ANNOTATION;
    	
    	else if (this.equals(AnnotationType.DO_TF_ANNOTATION))
    		return Commons.DO_TF_ANNOTATION;
    	else if (this.equals(AnnotationType.DO_NOT_TF_ANNOTATION))
    		return Commons.DO_NOT_TF_ANNOTATION;
    	
    	else if (this.equals(AnnotationType.DO_GENE_ANNOTATION))
    		return Commons.DO_GENE_ANNOTATION;
    	else if (this.equals(AnnotationType.DO_NOT_GENE_ANNOTATION))
    		return Commons.DO_NOT_GENE_ANNOTATION;
    	
    	else if (this.equals(AnnotationType.DO_KEGGPATHWAY_ANNOTATION))
    		return Commons.DO_KEGGPATHWAY_ANNOTATION;
    	else if (this.equals(AnnotationType.DO_NOT_KEGGPATHWAY_ANNOTATION))
    		return Commons.DO_NOT_KEGGPATHWAY_ANNOTATION;
    	
    	else if (this.equals(AnnotationType.DO_TF_KEGGPATHWAY_ANNOTATION))
    		return Commons.DO_TF_KEGGPATHWAY_ANNOTATION;
    	else if (this.equals(AnnotationType.DO_NOT_TF_KEGGPATHWAY_ANNOTATION))
    		return Commons.DO_NOT_TF_KEGGPATHWAY_ANNOTATION;
    	
    	else if (this.equals(AnnotationType.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION))
    		return Commons.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;
    	else if (this.equals(AnnotationType.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ANNOTATION))
    		return Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;
    	
    	else if (this.equals(AnnotationType.DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION))
    		return Commons.DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;
    	
    	else if (this.equals(AnnotationType.DO_USER_DEFINED_GENESET_ANNOTATION))
    		return Commons.DO_USER_DEFINED_GENESET_ANNOTATION;
    	else if (this.equals(AnnotationType.DO_NOT_USER_DEFINED_GENESET_ANNOTATION))
    		return Commons.DO_NOT_USER_DEFINED_GENESET_ANNOTATION;
    		
    	else if (this.equals(AnnotationType.DO_USER_DEFINED_LIBRARY_ANNOTATION))
    		return Commons.DO_USER_DEFINED_LIBRARY_ANNOTATION;
    	else if (this.equals(AnnotationType.DO_NOT_USER_DEFINED_LIBRARY_ANNOTATION))
    		return Commons.DO_NOT_USER_DEFINED_LIBRARY_ANNOTATION;
    	else 
    		return null;
    	  	
    	
    }
    
    /*************************************************************/
    public boolean doDnaseAnnotation() {
    	return this == DO_DNASE_ANNOTATION;
    }
    
    public boolean doTFAnnotation() {
    	return this == DO_TF_ANNOTATION;
    }
    
    public boolean doHistoneAnnotation() {
    	return this == DO_HISTONE_ANNOTATION;
    }
    
    public boolean doGeneAnnotation() {
    	return this == DO_GENE_ANNOTATION;
    }
    
    public boolean doKEGGPathwayAnnotation() {
    	return this == DO_KEGGPATHWAY_ANNOTATION;
    }
    
    public boolean doTFKEGGPathwayAnnotation() {
    	return this == DO_TF_KEGGPATHWAY_ANNOTATION;
    }
    
    public boolean doTFCellLineKEGGPathwayAnnotation() {
    	return this == DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;
    }
    
    public boolean doBothTFKEGGPathwayAndTFCellLineKEGGPathwayAnnotation(){
    	return this == DO_BOTH_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ANNOTATION;	
    }
    
    public boolean doUserDefinedGeneSetAnnotation() {
    	return this == DO_USER_DEFINED_GENESET_ANNOTATION;
    }
    
    public boolean doUserDefinedLibraryAnnotation() {
    	return this == DO_USER_DEFINED_LIBRARY_ANNOTATION;
    }
    /*************************************************************/
    
    

}
