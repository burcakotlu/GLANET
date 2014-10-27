/**
 * @author burcakotlu
 * @date May 15, 2014 
 * @time 9:43:28 AM
 */
package enumtypes;

/**
 * 	//Long.MAX_VALUE	 9223372_0368_5477_5807 19 digits
 *  //Int.MAX_VALUE		 214748_3647 10 digitds
 *  
 */
public enum GeneratedMixedNumberDescriptionOrderLength {
	
	
	INT_4DIGIT_DNASECELLLINENUMBER(1),
	INT_5DIGIT_USERDEFINEDGENESETNUMBER(2),
	INT_4DIGIT_KEGGPATHWAYNUMBER(3),
	INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER(4),
	INT_4DIGIT_HISTONENUMBER_4DIGIT_CELLLINENUMBER(5),
	INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER(6),
	LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER(7),
	
	INT_4DIGITS_ELEMENTNUMBER_3DIGITS_CELLLINENUMBER_3DIGITS_KEGGPATHWAYNUMBER(8),
	INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER(9),
	INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER(10),
	LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER(11),
	LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER(12);
	
	 private final int generatedMixedNumberDescriptionOrderLength;
		
	/* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private GeneratedMixedNumberDescriptionOrderLength(int generatedMixedNumberDescriptionOrderLength) {
    	this.generatedMixedNumberDescriptionOrderLength = generatedMixedNumberDescriptionOrderLength;
	}
    
    
	public int getGeneratedMixedNumberDescriptionOrderLength(){ 
		 return generatedMixedNumberDescriptionOrderLength; 
	}
	
		
	public boolean is_INT_4DIGIT_DNASECELLLINENUMBER() {
        return  this == INT_4DIGIT_DNASECELLLINENUMBER;
    }
	
	/** An added method.  */
    public boolean is_INT_5DIGIT_USERDEFINEDGENESETNUMBER() {
        return  this == INT_5DIGIT_USERDEFINEDGENESETNUMBER;
    }
    
    
    /** An added method.  */
    public boolean is_INT_4DIGIT_KEGGPATHWAYNUMBER() {
        return  this == INT_4DIGIT_KEGGPATHWAYNUMBER;
    }
	
    /** An added method.  */
    public boolean is_INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER() {
        return  this == INT_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER;
    }
    
    /** An added method.  */
    public boolean is_INT_4DIGIT_HISTONENUMBER_4DIGIT_CELLLINENUMBER() {
        return  this == INT_4DIGIT_HISTONENUMBER_4DIGIT_CELLLINENUMBER;
    }
    
    /** An added method.  */
    public boolean is_INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER() {
        return  this == INT_4DIGIT_TFNUMBER_4DIGIT_KEGGPATHWAYNUMBER;
    }
    
    /** An added method.  */
    public boolean is_LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER() {
        return  this == LONG_4DIGIT_TFNUMBER_4DIGIT_CELLLINENUMBER_4DIGIT_KEGGPATHWAYNUMBER;
    }

    
    public boolean is_INT_4DIGITS_ELEMENTNUMBER_3DIGITS_CELLLINENUMBER_3DIGITS_KEGGPATHWAYNUMBER() {
        return  this == INT_4DIGITS_ELEMENTNUMBER_3DIGITS_CELLLINENUMBER_3DIGITS_KEGGPATHWAYNUMBER;
    }
       
    public boolean is_LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER() {
        return  this == LONG_7DIGITS_PERMUTATIONNUMBER_5DIGITS_USERDEFINEDGENESETNUMBER;
    }
    
    public boolean is_INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER() {
        return  this == INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_KEGGPATHWAYNUMBER;
    }
    
    public boolean is_INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER() {
        return  this == INT_6DIGITS_PERMUTATIONNUMBER_4DIGITS_CELLLINENUMBER;
    }
    
    
    public boolean is_LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER() {
        return  this == LONG_7DIGITS_PERMUTATIONNUMBER_4DIGITS_ELEMENTNUMBER_4DIGITS_CELLLINENUMBER_4DIGITS_KEGGPATHWAYNUMBER;
    }
  
  	}
