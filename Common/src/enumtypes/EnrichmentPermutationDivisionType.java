/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Aug 15, 2015
 * @project Common 
 *
 */
public enum EnrichmentPermutationDivisionType {
	
	DIVIDE_PERMUTATIONS_AS_MUCH_AS_NUMBER_OF_PROCESSORS(1),
	DIVIDE_PERMUTATIONS_AS_LONG_AS_NUMBER_OF_PERMUTAIONS_IS_GREATER_THAN_NUMBER_OF_PROCESSORS(2);
	
	private final int enrichmentPermutationDivisionType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private EnrichmentPermutationDivisionType( int enrichmentPermutationDivisionType) {

		this.enrichmentPermutationDivisionType = enrichmentPermutationDivisionType;
	}

	public int getEnrichmentPermutationDivisionType() {

		return enrichmentPermutationDivisionType;
	}

	public static EnrichmentPermutationDivisionType convertStringtoEnum( String enrichmentPermutationDivisionType) {

		if( Commons.DIVIDE_PERMUTATIONS_AS_MUCH_AS_NUMBER_OF_PROCESSORS.equals( enrichmentPermutationDivisionType)){
			return DIVIDE_PERMUTATIONS_AS_MUCH_AS_NUMBER_OF_PROCESSORS;
		}else if( Commons.DIVIDE_PERMUTATIONS_AS_LONG_AS_NUMBER_OF_PERMUTAIONS_IS_GREATER_THAN_NUMBER_OF_PROCESSORS.equals( enrichmentPermutationDivisionType)){
			return DIVIDE_PERMUTATIONS_AS_LONG_AS_NUMBER_OF_PERMUTAIONS_IS_GREATER_THAN_NUMBER_OF_PROCESSORS;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( DIVIDE_PERMUTATIONS_AS_MUCH_AS_NUMBER_OF_PROCESSORS))
			return Commons.DIVIDE_PERMUTATIONS_AS_MUCH_AS_NUMBER_OF_PROCESSORS;
		else if( this.equals( DIVIDE_PERMUTATIONS_AS_LONG_AS_NUMBER_OF_PERMUTAIONS_IS_GREATER_THAN_NUMBER_OF_PROCESSORS))
			return Commons.DIVIDE_PERMUTATIONS_AS_LONG_AS_NUMBER_OF_PERMUTAIONS_IS_GREATER_THAN_NUMBER_OF_PROCESSORS;
		else
			return null;
	}

	public boolean isDividePermutationsAsMuchAsNumberofProcessors() {

		return this == DIVIDE_PERMUTATIONS_AS_MUCH_AS_NUMBER_OF_PROCESSORS;
	}

	public boolean isDividePermutationsAsLongAsNumberofPermutationsIsGreaterThanNumberofProcessors() {

		return this == DIVIDE_PERMUTATIONS_AS_LONG_AS_NUMBER_OF_PERMUTAIONS_IS_GREATER_THAN_NUMBER_OF_PROCESSORS;
	}

	
	

}
