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
public enum EnrichmentDecisionType {

	ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_ZSCORE(1),
	ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_ZSCORE(2),
	
	ENRICHED_WRT_EMPIRICAL_PVALUE_FROM_RATIO_OF_SAMPLINGS(3),
	ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_RATIO_OF_SAMPLINGS(4),
	ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_SAMPLINGS(5);

	private final int enrichmentDecisionType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private EnrichmentDecisionType( int enrichmentDecisionType) {

		this.enrichmentDecisionType = enrichmentDecisionType;
	}

	public int getEnrichmentDecisionType() {

		return enrichmentDecisionType;
	}

	public static EnrichmentDecisionType convertStringtoEnum( String enrichmentDecisionType) {

		if( Commons.ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_ZSCORE.equals(enrichmentDecisionType)){
			return ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_ZSCORE;
		}
		
		else if( Commons.ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_ZSCORE.equals(enrichmentDecisionType)){
			return ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_ZSCORE;
		}
		
		else if( Commons.ENRICHED_WRT_EMPIRICAL_PVALUE_FROM_RATIO_OF_SAMPLINGS.equals(enrichmentDecisionType)){
			return ENRICHED_WRT_EMPIRICAL_PVALUE_FROM_RATIO_OF_SAMPLINGS;
		}
		
		else if( Commons.ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_RATIO_OF_SAMPLINGS.equals(enrichmentDecisionType)){
			return ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_RATIO_OF_SAMPLINGS;
		}
		
		else if( Commons.ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_SAMPLINGS.equals(enrichmentDecisionType)){
			return ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_SAMPLINGS;
		}
		
		else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( EnrichmentDecisionType.ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_ZSCORE))
			return Commons.ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_ZSCORE;
		
		else if( this.equals( EnrichmentDecisionType.ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_ZSCORE))
			return Commons.ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_ZSCORE;
		
		else if( this.equals( EnrichmentDecisionType.ENRICHED_WRT_EMPIRICAL_PVALUE_FROM_RATIO_OF_SAMPLINGS))
			return Commons.ENRICHED_WRT_EMPIRICAL_PVALUE_FROM_RATIO_OF_SAMPLINGS;
		
		else if( this.equals( EnrichmentDecisionType.ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_RATIO_OF_SAMPLINGS))
			return Commons.ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_RATIO_OF_SAMPLINGS;
		
		else if( this.equals( EnrichmentDecisionType.ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_SAMPLINGS))
			return Commons.ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_SAMPLINGS;
		else
			return null;
	}

	public boolean isEnrichedwrtBHFDRAdjustedPvalueFromZscore() {
		return ( this == EnrichmentDecisionType.ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_ZSCORE);
	}
	
	public boolean isEnrichedwrtBonferroniCorrectedPvalueFromZscore() {
		return ( this == EnrichmentDecisionType.ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_ZSCORE);
	}
	
	public boolean isEnrichedwrtEmpiricalPvalueFromRatioofSamplings() {
		return ( this == EnrichmentDecisionType.ENRICHED_WRT_EMPIRICAL_PVALUE_FROM_RATIO_OF_SAMPLINGS);
	}
	
	public boolean isEnrichedwrtBHFDRAdjustedPvalueFromRatioofSamplings() {
		return ( this == EnrichmentDecisionType.ENRICHED_WRT_BH_FDR_ADJUSTED_PVALUE_FROM_RATIO_OF_SAMPLINGS);
	}

	public boolean isEnrichedwrtBonferroniCorrectedPvalueFromRatioofSamplings() {
		return ( this == EnrichmentDecisionType.ENRICHED_WRT_BONFERRONI_CORRECTED_PVALUE_FROM_RATIO_OF_SAMPLINGS);
	}
	
}
