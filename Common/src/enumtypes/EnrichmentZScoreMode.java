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
public enum EnrichmentZScoreMode {

	PerformEnrichmentWithZScore(1),
	PerformEnrichmentWithoutZScore(2);

	private final int enrichmentZScoreMode;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private EnrichmentZScoreMode(int enrichmentZScoreMode) {
		this.enrichmentZScoreMode = enrichmentZScoreMode;
	}

	public int getEnrichmentZScoreMode() {
		return enrichmentZScoreMode;
	}

	public static EnrichmentZScoreMode convertStringtoEnum(String enrichmentZScoreMode) {
		if( Commons.PERFORM_ENRICHMENT_WITH_ZSCORE.equals(enrichmentZScoreMode)){
			return PerformEnrichmentWithZScore;
		}else if( Commons.PERFORM_ENRICHMENT_WITHOUT_ZSCORE.equals(enrichmentZScoreMode)){
			return PerformEnrichmentWithoutZScore;
		}else
			return null;
	}

	public String convertEnumtoString() {
		if( this.equals( EnrichmentZScoreMode.PerformEnrichmentWithZScore))
			return Commons.PERFORM_ENRICHMENT_WITH_ZSCORE;
		else if( this.equals( EnrichmentZScoreMode.PerformEnrichmentWithoutZScore))
			return Commons.PERFORM_ENRICHMENT_WITHOUT_ZSCORE;
		else
			return null;
	}

	public boolean isPerformEnrichmentWithZScore() {
		return ( this == EnrichmentZScoreMode.PerformEnrichmentWithZScore);
	}

	public boolean isPerformEnrichmentWithoutZScore() {
		return ( this == EnrichmentZScoreMode.PerformEnrichmentWithoutZScore);
	}

}
