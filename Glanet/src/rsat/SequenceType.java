/**
 * 
 */
package rsat;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Jan 27, 2015
 * @project Glanet 
 *
 */
public enum SequenceType {
	
	SNP_REFERENCE_SEQUENCE(1),
	SNP_ALTERED_SEQUENCE(2),
	TF_EXTENDED_PEAK_SEQUENCE(3);

	private final int sequenceType;
	
	/* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private SequenceType(int sequenceType) {
    	this.sequenceType = sequenceType;
	}
    
    public int getSequenceType(){
    	return sequenceType;
    }
    
    
    public static SequenceType convertStringtoEnum(String sequenceType){
    	
    	if (Commons.SNP_REFERENCE_SEQUENCE.equals(sequenceType)){
    		return SNP_REFERENCE_SEQUENCE;
    	}else if  (Commons.SNP_ALTERED_SEQUENCE.equals(sequenceType)){
    		return SNP_ALTERED_SEQUENCE;
    	}else if  (Commons.TF_EXTENDED_PEAK_SEQUENCE.equals(sequenceType)){
    		return TF_EXTENDED_PEAK_SEQUENCE;
    	}else     	
    		return null;
    }
	
	 public String convertEnumtoString(){
	 	if (this.equals(SequenceType.SNP_REFERENCE_SEQUENCE))
	 		return Commons.SNP_REFERENCE_SEQUENCE;
	 	else if (this.equals(SequenceType.SNP_ALTERED_SEQUENCE))	
	 		return Commons.SNP_ALTERED_SEQUENCE; 	
	 	else if (this.equals(SequenceType.TF_EXTENDED_PEAK_SEQUENCE))	
	 		return Commons.TF_EXTENDED_PEAK_SEQUENCE; 	
	 	else
	 		return null;   		
	 }
	 
	 public boolean isSNPReferenceSequence(){
		 return this == SNP_REFERENCE_SEQUENCE;
	 }
	 
	 public boolean isSNPAlteredSequence(){
		 return this == SNP_ALTERED_SEQUENCE;
	 }
	
	 
	 public boolean isTFExtendedPeakSequence(){
		 return this == TF_EXTENDED_PEAK_SEQUENCE;
	 }

}
