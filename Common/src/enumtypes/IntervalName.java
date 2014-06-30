/**
 * @author burcakotlu
 * @date May 12, 2014 
 * @time 10:47:47 AM
 */
package enumtypes;

import common.Commons;

/**
 * 
 */
public enum IntervalName {
	
	
	EXON(1),
	INTRON(2),
	FIVE_P_ONE(3),
	FIVE_P_TWO(4),
	FIVE_D(5),
	THREE_P_ONE(6),
	THREE_P_TWO(7),
	THREE_D(8);

	
	
	
//	public static final String EXON = "EXON";
//	public static final String INTRON = "INTRON";
//	public static final String FIVE_P_ONE = "5P1";
//	public static final String FIVE_P_TWO = "5P2";
//	public static final String THREE_P_ONE = "3P1";
//	public static final String THREE_P_TWO = "3P2";

	
	private final int intervalName;
	
	
  /* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private IntervalName(int intervalName) {
    	this.intervalName = intervalName;
	}
		
    
    public String getIntervalNameString(){
    	if (this.equals(IntervalName.EXON))
    		return Commons.EXON;
    	else if (this.equals(IntervalName.INTRON))
    		return Commons.INTRON;
    	else if (this.equals(IntervalName.FIVE_P_ONE))
    		return Commons.FIVE_P_ONE;
    	else if (this.equals(IntervalName.FIVE_P_TWO))
    		return Commons.FIVE_P_TWO;
    	else if (this.equals(IntervalName.FIVE_D))
    		return Commons.FIVE_D;
    	else if (this.equals(IntervalName.THREE_P_ONE))
    		return Commons.THREE_P_ONE;
    	else if (this.equals(IntervalName.THREE_P_TWO))
    		return Commons.THREE_P_TWO;
    	else if (this.equals(IntervalName.THREE_D))
    		return Commons.THREE_D;	
    	else
    		return null;   		
    }
    
    /** An added method.  */
    public boolean isExon() {
     return  this == EXON;
    }
    
    /** An added method.  */
    public boolean isIntron() {
     return  this == INTRON;
    }
	     
		
    /** An added method.  */
    public boolean isFivePOne() {
     return  this == FIVE_P_ONE;
    }
    
    /** An added method.  */
    public boolean isFivePTwo() {
     return  this == FIVE_P_TWO;
    }
    
    /** An added method.  */
    public boolean isFiveD() {
     return  this == FIVE_D;
    }
    
    /** An added method.  */
    public boolean isThreePOne() {
     return  this == THREE_P_ONE;
    }
    
    /** An added method.  */
    public boolean isThreePTwo() {
     return  this == THREE_P_TWO;
    }
    
    /** An added method.  */
    public boolean isThreeD() {
     return  this == THREE_D;
    }
    
    
    public static IntervalName convertStringtoEnum(String intervalName){
    	
    	if (Commons.EXON.equals(intervalName)){
    		return EXON;
    	}else if  (Commons.INTRON.equals(intervalName)){
    		return INTRON;
    	}else if  (Commons.FIVE_P_ONE.equals(intervalName)){
    		return FIVE_P_ONE;
    	}else if  (Commons.FIVE_P_TWO.equals(intervalName)){
    		return FIVE_P_TWO;
    	}else if  (Commons.FIVE_D.equals(intervalName)){
    		return FIVE_D;
    	}else if  (Commons.THREE_P_ONE.equals(intervalName)){
    		return THREE_P_ONE;
    	}else if  (Commons.THREE_P_TWO.equals(intervalName)){
    		return THREE_P_TWO;
    	}else if  (Commons.THREE_D.equals(intervalName)){
    		return THREE_D;
    	}else
    		return null;
    }
}
