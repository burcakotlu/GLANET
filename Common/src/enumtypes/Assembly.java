/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Nov 28, 2014
 * @project Common 
 *
 */
public enum Assembly {
	
	PRIMARYASSEMBLY(1),
	PATCHES(2);

	private final int assembly;
	
	/* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private Assembly(int assembly) {
    	this.assembly = assembly;
	}
    
    public int getAssembly(){
    	return assembly;
    }
    
    
    public static Assembly convertStringtoEnum(String assembly){
    	
    	if (Commons.PRIMARYASSEMBLY.equals(assembly)){
    		return PRIMARYASSEMBLY;
    	}else if  (Commons.PATCHES.equals(assembly)){
    		return PATCHES;
    	}else     	
    		return null;
    }
	
	
 
	 public String convertEnumtoString(){
	 	if (this.equals(Assembly.PRIMARYASSEMBLY))
	 		return Commons.PRIMARYASSEMBLY;
	 	else if (this.equals(Assembly.PATCHES))	
	 		return Commons.PATCHES; 	
	 	else
	 		return null;   		
	 }
	 
	 public boolean isPrimaryAssembly(){
		 return this == PRIMARYASSEMBLY;
	 }
}
