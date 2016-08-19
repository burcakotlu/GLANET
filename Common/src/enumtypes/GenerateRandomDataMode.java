/**
 * @author burcakotlu
 * @date Jun 23, 2014 
 * @time 11:40:08 AM
 */
package enumtypes;

import common.Commons;

/**
 * 
 */
public enum GenerateRandomDataMode {

	GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT(1), 
	GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT(2),
	GENERATE_RANDOM_DATA_WITH_GC_CONTENT(3),
	GENERATE_RANDOM_DATA_WITH_MAPPABILITY(4);

	private final int generateRandomDataMode;

	public int getGenerateRandomDataMode() {

		return generateRandomDataMode;
	}

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private GenerateRandomDataMode( int generateRandomDataMode) {

		this.generateRandomDataMode = generateRandomDataMode;
	}

	public static GenerateRandomDataMode convertStringtoEnum( String generateRandomDataMode) {

		if( Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT.equalsIgnoreCase(generateRandomDataMode) ||
				Commons.WGCM.equalsIgnoreCase(generateRandomDataMode)){
			return GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
		}
		
		else if( Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT.equalsIgnoreCase(generateRandomDataMode) ||
				Commons.WOGCM.equalsIgnoreCase(generateRandomDataMode)){
			return GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
		}
		
		
		else if( Commons.GENERATE_RANDOM_DATA_WITH_GC_CONTENT.equalsIgnoreCase(generateRandomDataMode) ||
				Commons.WGC.equalsIgnoreCase(generateRandomDataMode)){
			return GENERATE_RANDOM_DATA_WITH_GC_CONTENT;
		}
		
		else if( Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY.equalsIgnoreCase(generateRandomDataMode) ||
				Commons.WM.equalsIgnoreCase(generateRandomDataMode)){
			return GENERATE_RANDOM_DATA_WITH_MAPPABILITY;
		}
		
		
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT))
			return Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
		else if( this.equals( GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT))
			return Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
		
		else if( this.equals( GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_GC_CONTENT))
			return Commons.GENERATE_RANDOM_DATA_WITH_GC_CONTENT;

		else if( this.equals( GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY))
			return Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY;

		
		else
			return null;

	}
	
	
	public String convertEnumtoShortString() {

		if( this.equals( GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT))
			return Commons.WGCM;
		else if( this.equals( GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT))
			return Commons.WOGCM;
		else if( this.equals( GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_GC_CONTENT))
			return Commons.WGC;
		else if( this.equals( GenerateRandomDataMode.GENERATE_RANDOM_DATA_WITH_MAPPABILITY))
			return Commons.WM;
		else
			return null;

	}

	/** An added method.  */
	public boolean isGenerateRandomDataModeWithMapabilityandGc() {
		return this == GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
	}

	
	public boolean isGenerateRandomDataModeWithMapability() {
		return this == GENERATE_RANDOM_DATA_WITH_MAPPABILITY;
	}
	
	
	public boolean isGenerateRandomDataModeWithGC() {
		return this == GENERATE_RANDOM_DATA_WITH_GC_CONTENT;
	}
	
	/** An added method.  */
	public boolean isGenerateRandomDataModeWithoutMapabilityandGc() {
		return this == GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT;
	}

}
