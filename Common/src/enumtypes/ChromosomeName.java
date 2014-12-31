/**
 * @author burcakotlu
 * @date May 6, 2014 
 * @time 11:21:39 AM
 */
package enumtypes;

import common.Commons;
/**
 * 
 */
public enum ChromosomeName {
	
	CHROMOSOME1(1),
	CHROMOSOME2(2),
	CHROMOSOME3(3),
	CHROMOSOME4(4),
	CHROMOSOME5(5),
	CHROMOSOME6(6),
	CHROMOSOME7(7),
	CHROMOSOME8(8),
	CHROMOSOME9(9),
	CHROMOSOME10(10),
	CHROMOSOME11(11),
	CHROMOSOME12(12),
	CHROMOSOME13(13),
	CHROMOSOME14(14),
	CHROMOSOME15(15),
	CHROMOSOME16(16),
	CHROMOSOME17(17),
	CHROMOSOME18(18),
	CHROMOSOME19(19),
	CHROMOSOME20(20),
	CHROMOSOME21(21),
	CHROMOSOME22(22),
	CHROMOSOMEX(23),
	CHROMOSOMEY(24);
	
	 private final int chromosomeName;
	 
	/* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private ChromosomeName(int chromosomeName) {
    	this.chromosomeName = chromosomeName;
	}
	   
    public int getChromosomeName(){
    	return chromosomeName;
    }
    
    public String convertEnumtoString(){
    	if (this.equals(ChromosomeName.CHROMOSOME1))
    		return Commons.CHROMOSOME1;
    	else if (this.equals(ChromosomeName.CHROMOSOME2))
    		return Commons.CHROMOSOME2;
    	else if (this.equals(ChromosomeName.CHROMOSOME3))
    		return Commons.CHROMOSOME3;
    	else if (this.equals(ChromosomeName.CHROMOSOME4))
    		return Commons.CHROMOSOME4;
    	else if (this.equals(ChromosomeName.CHROMOSOME5))
    		return Commons.CHROMOSOME5;
    	else if (this.equals(ChromosomeName.CHROMOSOME6))
    		return Commons.CHROMOSOME6;
    	else if (this.equals(ChromosomeName.CHROMOSOME7))
    		return Commons.CHROMOSOME7;
    	else if (this.equals(ChromosomeName.CHROMOSOME8))
    		return Commons.CHROMOSOME8;
    	else if (this.equals(ChromosomeName.CHROMOSOME9))
    		return Commons.CHROMOSOME9;
    	else if (this.equals(ChromosomeName.CHROMOSOME10))
    		return Commons.CHROMOSOME10;
    	else if (this.equals(ChromosomeName.CHROMOSOME11))
    		return Commons.CHROMOSOME11;
    	else if (this.equals(ChromosomeName.CHROMOSOME12))
    		return Commons.CHROMOSOME12;
    	else if (this.equals(ChromosomeName.CHROMOSOME13))
    		return Commons.CHROMOSOME13;
    	else if (this.equals(ChromosomeName.CHROMOSOME14))
    		return Commons.CHROMOSOME14;
    	else if (this.equals(ChromosomeName.CHROMOSOME15))
    		return Commons.CHROMOSOME15;
    	else if (this.equals(ChromosomeName.CHROMOSOME16))
    		return Commons.CHROMOSOME16;
    	else if (this.equals(ChromosomeName.CHROMOSOME17))
    		return Commons.CHROMOSOME17;
    	else if (this.equals(ChromosomeName.CHROMOSOME18))
    		return Commons.CHROMOSOME18;
    	else if (this.equals(ChromosomeName.CHROMOSOME19))
    		return Commons.CHROMOSOME19;
    	else if (this.equals(ChromosomeName.CHROMOSOME20))
    		return Commons.CHROMOSOME20;
    	else if (this.equals(ChromosomeName.CHROMOSOME21))
    		return Commons.CHROMOSOME21;
    	else if (this.equals(ChromosomeName.CHROMOSOME22))
    		return Commons.CHROMOSOME22;
    	else if (this.equals(ChromosomeName.CHROMOSOMEX))
    		return Commons.CHROMOSOMEX;
    	else if (this.equals(ChromosomeName.CHROMOSOMEY))
    		return Commons.CHROMOSOMEY;    	
    	else return null;
    	
    }
    
    public static ChromosomeName convertStringtoEnum(String chrName){
    	
    	if (Commons.CHROMOSOME1.equals(chrName)){
    		return CHROMOSOME1;
    	}else if  (Commons.CHROMOSOME2.equals(chrName)){
    		return CHROMOSOME2;
    	}else if  (Commons.CHROMOSOME3.equals(chrName)){
    		return CHROMOSOME3;
    	}else if  (Commons.CHROMOSOME4.equals(chrName)){
    		return CHROMOSOME4;
    	}else if  (Commons.CHROMOSOME5.equals(chrName)){
    		return CHROMOSOME5;
    	}else if  (Commons.CHROMOSOME6.equals(chrName)){
    		return CHROMOSOME6;
    	}else if  (Commons.CHROMOSOME7.equals(chrName)){
    		return CHROMOSOME7;
    	}else if  (Commons.CHROMOSOME8.equals(chrName)){
    		return CHROMOSOME8;
    	}else if  (Commons.CHROMOSOME9.equals(chrName)){
    		return CHROMOSOME9;
    	}else if  (Commons.CHROMOSOME10.equals(chrName)){
    		return CHROMOSOME10;
    	}else if  (Commons.CHROMOSOME11.equals(chrName)){
    		return CHROMOSOME11;
    	}else if  (Commons.CHROMOSOME12.equals(chrName)){
    		return CHROMOSOME12;
    	}else if  (Commons.CHROMOSOME13.equals(chrName)){
    		return CHROMOSOME13;
    	}else if  (Commons.CHROMOSOME14.equals(chrName)){
    		return CHROMOSOME14;
    	}else if  (Commons.CHROMOSOME15.equals(chrName)){
    		return CHROMOSOME15;
    	}else if  (Commons.CHROMOSOME16.equals(chrName)){
    		return CHROMOSOME16;
    	}else if  (Commons.CHROMOSOME17.equals(chrName)){
    		return CHROMOSOME17;
    	}else if  (Commons.CHROMOSOME18.equals(chrName)){
    		return CHROMOSOME18;
    	}else if  (Commons.CHROMOSOME19.equals(chrName)){
    		return CHROMOSOME19;
    	}else if  (Commons.CHROMOSOME20.equals(chrName)){
    		return CHROMOSOME20;
    	}else if  (Commons.CHROMOSOME21.equals(chrName)){
    		return CHROMOSOME21;
    	}else if  (Commons.CHROMOSOME22.equals(chrName)){
    		return CHROMOSOME22;
    	}else if  (Commons.CHROMOSOMEX.equals(chrName)){
    		return CHROMOSOMEX;
    	}else if  (Commons.CHROMOSOMEY.equals(chrName)){
    		return CHROMOSOMEY;
    	}else 
    		return null;
    }
	

    
public static String convertEnumtoString(ChromosomeName  chrName){
    	
    	if (ChromosomeName.CHROMOSOME1.equals(chrName)){
    		return Commons.CHROMOSOME1;
    	}else if  (ChromosomeName.CHROMOSOME2.equals(chrName)){
    		return Commons.CHROMOSOME2;
    	}else if  (ChromosomeName.CHROMOSOME3.equals(chrName)){
    		return Commons.CHROMOSOME3;
    	}else if  (ChromosomeName.CHROMOSOME4.equals(chrName)){
    		return Commons.CHROMOSOME4;
    	}else if  (ChromosomeName.CHROMOSOME5.equals(chrName)){
    		return Commons.CHROMOSOME5;
    	}else if  (ChromosomeName.CHROMOSOME6.equals(chrName)){
    		return Commons.CHROMOSOME6;
    	}else if  (ChromosomeName.CHROMOSOME7.equals(chrName)){
    		return Commons.CHROMOSOME7;
    	}else if  (ChromosomeName.CHROMOSOME8.equals(chrName)){
    		return Commons.CHROMOSOME8;
    	}else if  (ChromosomeName.CHROMOSOME9.equals(chrName)){
    		return Commons.CHROMOSOME9;
    	}else if  (ChromosomeName.CHROMOSOME10.equals(chrName)){
    		return Commons.CHROMOSOME10;
    	}else if  (ChromosomeName.CHROMOSOME11.equals(chrName)){
    		return Commons.CHROMOSOME11;
    	}else if  (ChromosomeName.CHROMOSOME12.equals(chrName)){
    		return Commons.CHROMOSOME12;
    	}else if  (ChromosomeName.CHROMOSOME13.equals(chrName)){
    		return Commons.CHROMOSOME13;
    	}else if  (ChromosomeName.CHROMOSOME14.equals(chrName)){
    		return Commons.CHROMOSOME14;
    	}else if  (ChromosomeName.CHROMOSOME15.equals(chrName)){
    		return Commons.CHROMOSOME15;
    	}else if  (ChromosomeName.CHROMOSOME16.equals(chrName)){
    		return Commons.CHROMOSOME16;
    	}else if  (ChromosomeName.CHROMOSOME17.equals(chrName)){
    		return Commons.CHROMOSOME17;
    	}else if  (ChromosomeName.CHROMOSOME18.equals(chrName)){
    		return Commons.CHROMOSOME18;
    	}else if  (ChromosomeName.CHROMOSOME19.equals(chrName)){
    		return Commons.CHROMOSOME19;
    	}else if  (ChromosomeName.CHROMOSOME20.equals(chrName)){
    		return Commons.CHROMOSOME20;
    	}else if  (ChromosomeName.CHROMOSOME21.equals(chrName)){
    		return Commons.CHROMOSOME21;
    	}else if  (ChromosomeName.CHROMOSOME22.equals(chrName)){
    		return Commons.CHROMOSOME22;
    	}else if  (ChromosomeName.CHROMOSOMEX.equals(chrName)){
    		return Commons.CHROMOSOMEX;
    	}else if  (ChromosomeName.CHROMOSOMEY.equals(chrName)){
    		return Commons.CHROMOSOMEY;
    	}else
    		return null;
    }


	/** An added method.  */
	public boolean isCHROMOSOME1() {
	    return  this == CHROMOSOME1;
	}
	
	/** An added method.  */
	public boolean isCHROMOSOME2() {
	    return  this == CHROMOSOME2;
	}

	/** An added method.  */
	public boolean isCHROMOSOME3() {
	    return  this == CHROMOSOME3;
	}
	
	/** An added method.  */
	public boolean isCHROMOSOME4() {
	    return  this == CHROMOSOME4;
	}
	/** An added method.  */
	public boolean isCHROMOSOME5() {
	    return  this == CHROMOSOME5;
	}
	
	/** An added method.  */
	public boolean isCHROMOSOME6() {
	    return  this == CHROMOSOME6;
	}
	/** An added method.  */
	public boolean isCHROMOSOME7() {
	    return  this == CHROMOSOME7;
	}
	/** An added method.  */
	public boolean isCHROMOSOME8() {
	    return  this == CHROMOSOME8;
	}
	/** An added method.  */
	public boolean isCHROMOSOME9() {
	    return  this == CHROMOSOME9;
	}
	/** An added method.  */
	public boolean isCHROMOSOME10() {
	    return  this == CHROMOSOME10;
	}
	/** An added method.  */
	public boolean isCHROMOSOME11() {
	    return  this == CHROMOSOME11;
	}
	/** An added method.  */
	public boolean isCHROMOSOME12() {
	    return  this == CHROMOSOME12;
	}
	/** An added method.  */
	public boolean isCHROMOSOME13() {
	    return  this == CHROMOSOME13;
	}
	/** An added method.  */
	public boolean isCHROMOSOME14() {
	    return  this == CHROMOSOME14;
	}
	/** An added method.  */
	public boolean isCHROMOSOME15() {
	    return  this == CHROMOSOME15;
	}
	/** An added method.  */
	public boolean isCHROMOSOME16() {
	    return  this == CHROMOSOME16;
	}
	/** An added method.  */
	public boolean isCHROMOSOME17() {
	    return  this == CHROMOSOME17;
	}
	/** An added method.  */
	public boolean isCHROMOSOME18() {
	    return  this == CHROMOSOME18;
	}
	/** An added method.  */
	public boolean isCHROMOSOME19() {
	    return  this == CHROMOSOME19;
	}
	/** An added method.  */
	public boolean isCHROMOSOME20() {
	    return  this == CHROMOSOME20;
	}
	/** An added method.  */
	public boolean isCHROMOSOME21() {
	    return  this == CHROMOSOME21;
	}/** An added method.  */
	public boolean isCHROMOSOME22() {
	    return  this == CHROMOSOME22;
	}
	/** An added method.  */
	public boolean isCHROMOSOMEX() {
	    return  this == CHROMOSOMEX;
	}
	/** An added method.  */
	public boolean isCHROMOSOMEY() {
	    return  this == CHROMOSOMEY;
	}
	
	
	public static void main(String[] args){
		ChromosomeName chrName = CHROMOSOMEY;
		System.out.println(chrName.toString());
		System.out.println(chrName.name());
		System.out.println(chrName.convertEnumtoString());

	}
}
