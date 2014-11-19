/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Nov 17, 2014
 * @project Common 
 *
 */
public enum ElementType {
	
	DNASE(1),
	TF(2),
	HISTONE(3),
	HG19_REFSEQ_GENE(4);
	
	
	private final int elementType;
	
	/* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private ElementType(int elementType) {
    	this.elementType = elementType;
	}
    
    public int getElementType(){
    	return elementType;
    }
     
    
    public static ElementType convertStringtoEnum(String elementType){
    	
    	if (Commons.DNASE.equals(elementType)){
    		return DNASE;
    	}else if  (Commons.HISTONE.equals(elementType)){
    		return HISTONE;
    	}else if  (Commons.TF.equals(elementType)){
    		return TF;
    	}else if  (Commons.HG19_REFSEQ_GENE.equals(elementType)){
    		return HG19_REFSEQ_GENE;
    	}else     	
    		return null;
    }
	
	
 
	 public String convertEnumtoString(){
	 	if (this.equals(ElementType.DNASE))
	 		return Commons.DNASE;
	 	else if (this.equals(ElementType.HISTONE))	
	 		return Commons.HISTONE; 	
	 	else if (this.equals(ElementType.TF))
	 		return Commons.TF;
	 	else if(this.equals(ElementType.HG19_REFSEQ_GENE))
	 		return Commons.HG19_REFSEQ_GENE;
	 	else
	 		return null;   		
	 }
	

}
