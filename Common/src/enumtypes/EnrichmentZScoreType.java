/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burcak Otlu
 * @date Jun 24, 2015
 * @project Common 
 *
 */
public enum EnrichmentZScoreType {

	PerformEnrichmentWithZScore(1),
	PerformEnrichmentWithoutZScore(2);

	private final int enrichmentZScoreType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private EnrichmentZScoreType(int enrichmentZScoreType) {
		this.enrichmentZScoreType = enrichmentZScoreType;
	}

	public int getEnrichmentZScoreType() {
		return enrichmentZScoreType;
	}

	public static EnrichmentZScoreType convertStringtoEnum(String enrichmentZScoreType) {
		if( Commons.PERFORM_ENRICHMENT_WITH_ZSCORE.equals(enrichmentZScoreType)){
			return PerformEnrichmentWithZScore;
		}else if( Commons.PERFORM_ENRICHMENT_WITHOUT_ZSCORE.equals(enrichmentZScoreType)){
			return PerformEnrichmentWithoutZScore;
		}else
			return null;
	}

	public String convertEnumtoString() {
		if( this.equals( EnrichmentZScoreType.PerformEnrichmentWithZScore))
			return Commons.PERFORM_ENRICHMENT_WITH_ZSCORE;
		else if( this.equals( EnrichmentZScoreType.PerformEnrichmentWithoutZScore))
			return Commons.PERFORM_ENRICHMENT_WITHOUT_ZSCORE;
		else
			return null;
	}

	public boolean isPerformEnrichmentWithZScore() {
		return ( this == EnrichmentZScoreType.PerformEnrichmentWithZScore);
	}

	public boolean isPerformEnrichmentWithoutZScore() {
		return ( this == EnrichmentZScoreType.PerformEnrichmentWithoutZScore);
	}

}
