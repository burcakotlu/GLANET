/**
 * @author burcakotlu
 * @date Jun 23, 2014 
 * @time 11:40:08 AM
 */
package empiricalpvalues;

import common.Commons;

/**
 * 
 */
public enum GenerateRandomDataMode {
	
	GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT(1),
	GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT(2);
	
	private final int generateRandomDataMode;
	
	/* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private GenerateRandomDataMode(int generateRandomDataMode) {
    	this.generateRandomDataMode = generateRandomDataMode;
	}
    
    public static GenerateRandomDataMode convertStringtoEnum(String generateRandomDataMode){
    	
    	if (Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT.equals(generateRandomDataMode)){
    		return GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
    	}else if  (Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT.equals(generateRandomDataMode)){
    		return GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
    	}else
    		return null;
    }
    
    public String getGenerateRandomDataMode(){
    	if (this.equals(GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT))
    		return Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
    	else if (this.equals(GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT))
    		return Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
    	else
    		return null;
    				   		
    }
    
    /** An added method.  */
    public boolean isGenerateRandomDataModeWithMapabilityandGc() {
        return  this == GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
    }

    /** An added method.  */
    public boolean isGenerateRandomDataModeWithoutMapabilityandGc() {
        return  this == GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
    }
	

}
