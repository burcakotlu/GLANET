package enumtypes;

import common.Commons;

public enum Assembly {
	
	GRCh37_HG19(1),
	GRCh38_HG38(2);

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
    	
    	if (Commons.GRCh37_HG19.equals(assembly)){
    		return GRCh37_HG19;
    	}else if  (Commons.GRCh38_HG38.equals(assembly)){
    		return GRCh38_HG38;
    	}else     	
    		return null;
    }
	
	
 
	 public String convertEnumtoString(){
	 	if (this.equals(Assembly.GRCh37_HG19))
	 		return Commons.GRCh37_HG19;
	 	else if (this.equals(Assembly.GRCh38_HG38))	
	 		return Commons.GRCh38_HG38; 	
	 	else
	 		return null;   		
	 }
	 
	 public boolean isAssemblyGRCH37_hg19(){
		 return this == GRCh37_HG19;
	 }
	 
	 public boolean isAssemblyGRCH38_hg38(){
		 return this == GRCh38_HG38;
	 }

}
