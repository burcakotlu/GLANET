/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burcak Otlu
 * @date May 22, 2015
 * @project Common 
 *
 */
public enum EnrichmentDecision {

	P_VALUE_CALCULATED_FROM_Z_SCORE( 1), 
	P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO( 2),
	BOTH_ZSCORE_AND_NUMBEROFPERMUTATIONSRATIO( 3), 
	OLD_RESULT_FILE_VERSION( 4);

	private final int enrichmentDecision;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private EnrichmentDecision( int enrichmentDecision) {

		this.enrichmentDecision = enrichmentDecision;
	}

	public int getEnrichmentDecision() {

		return enrichmentDecision;
	}

	public static EnrichmentDecision convertStringtoEnum( String enrichmentDecision) {

		if( Commons.P_VALUE_CALCULATED_FROM_Z_SCORE.equals( enrichmentDecision)){
			return P_VALUE_CALCULATED_FROM_Z_SCORE;
		}else if( Commons.P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO.equals( enrichmentDecision)){
			return P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO;
		}else if( Commons.OLD_RESULT_FILE_VERSION.equals( enrichmentDecision)){
			return OLD_RESULT_FILE_VERSION;
		}else if( Commons.BOTH_ZSCORE_AND_NUMBEROFPERMUTATIONSRATIO.equals( enrichmentDecision)){
			return BOTH_ZSCORE_AND_NUMBEROFPERMUTATIONSRATIO;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( EnrichmentDecision.P_VALUE_CALCULATED_FROM_Z_SCORE))
			return Commons.P_VALUE_CALCULATED_FROM_Z_SCORE;
		else if( this.equals( EnrichmentDecision.P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO))
			return Commons.P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO;
		else if( this.equals( EnrichmentDecision.OLD_RESULT_FILE_VERSION))
			return Commons.OLD_RESULT_FILE_VERSION;
		else if( this.equals( EnrichmentDecision.BOTH_ZSCORE_AND_NUMBEROFPERMUTATIONSRATIO))
			return Commons.BOTH_ZSCORE_AND_NUMBEROFPERMUTATIONSRATIO;
		else
			return null;
	}

	public boolean isPValueCalculatedFromNumberofPermutationsRatio() {

		return ( this == EnrichmentDecision.P_VALUE_CALCULATED_FROM_NUMBER_OF_PERMUTATIONS_RATIO);
	}

	public boolean isPValueCalculatedFromZScore() {

		return ( this == EnrichmentDecision.P_VALUE_CALCULATED_FROM_Z_SCORE);
	}

	public boolean isBothZScoreandNumberofPermutationRatio() {

		return ( this == EnrichmentDecision.BOTH_ZSCORE_AND_NUMBEROFPERMUTATIONSRATIO);
	}
}
