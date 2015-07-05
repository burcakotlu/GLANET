/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Bur�ak Otlu
 * @date Jun 24, 2015
 * @project Common 
 *
 */
public enum EnrichmentKeepingNumberofOverlapsComingFromEachPermutationType {

	PerformEnrichmentWithKeepingNumberofOverlapsComingFromEachPermutation( 1),
	PerformEnrichmentWithoutKeepingNumberofOverlapsComingFromEachPermutation( 2);

	private final int enrichmentKeepingNumberofOverlapsDecision;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private EnrichmentKeepingNumberofOverlapsComingFromEachPermutationType(
			int enrichmentKeepingNumberofOverlapsDecision) {

		this.enrichmentKeepingNumberofOverlapsDecision = enrichmentKeepingNumberofOverlapsDecision;
	}

	public int getEnrichmentKeepingNumberofOverlapsDecision() {

		return enrichmentKeepingNumberofOverlapsDecision;
	}

	public static EnrichmentKeepingNumberofOverlapsComingFromEachPermutationType convertStringtoEnum(
			String enrichmentKeepingNumberofOverlapsDecision) {

		if( Commons.PERFORM_ENRICHMENT_WITH_KEEPING_NUMBER_OF_OVERLAPS_COMING_FROM_EACH_PERMUTATION.equals( enrichmentKeepingNumberofOverlapsDecision)){
			return PerformEnrichmentWithKeepingNumberofOverlapsComingFromEachPermutation;
		}else if( Commons.PERFORM_ENRICHMENT_WITHOUT_KEEPING_NUMBER_OF_OVERLAPS_COMING_FROM_EACH_PERMUTATION.equals( enrichmentKeepingNumberofOverlapsDecision)){
			return PerformEnrichmentWithoutKeepingNumberofOverlapsComingFromEachPermutation;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( EnrichmentKeepingNumberofOverlapsComingFromEachPermutationType.PerformEnrichmentWithKeepingNumberofOverlapsComingFromEachPermutation))
			return Commons.PERFORM_ENRICHMENT_WITH_KEEPING_NUMBER_OF_OVERLAPS_COMING_FROM_EACH_PERMUTATION;
		else if( this.equals( EnrichmentKeepingNumberofOverlapsComingFromEachPermutationType.PerformEnrichmentWithoutKeepingNumberofOverlapsComingFromEachPermutation))
			return Commons.PERFORM_ENRICHMENT_WITHOUT_KEEPING_NUMBER_OF_OVERLAPS_COMING_FROM_EACH_PERMUTATION;
		else
			return null;
	}

	public boolean isPerformEnrichmentWithKeepingNumberofOverlapsComingFromEachPermutationType() {

		return ( this == EnrichmentKeepingNumberofOverlapsComingFromEachPermutationType.PerformEnrichmentWithKeepingNumberofOverlapsComingFromEachPermutation);
	}

	public boolean isPerformEnrichmentWithoutKeepingNumberofOverlapsComingFromEachPermutationType() {

		return ( this == EnrichmentKeepingNumberofOverlapsComingFromEachPermutationType.PerformEnrichmentWithoutKeepingNumberofOverlapsComingFromEachPermutation);
	}

}
