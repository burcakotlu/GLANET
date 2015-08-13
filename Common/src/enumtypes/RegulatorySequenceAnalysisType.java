/**
 * @author burcakotlu
 * @date Jun 24, 2014 
 * @time 5:17:00 PM
 */
package enumtypes;

import common.Commons;

/**
 * 
 */
public enum RegulatorySequenceAnalysisType {

	DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT( 1),
	DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT( 2);

	private final int regulatorySequenceAnalysisType;

	public int getRegulatorySequenceAnalysisType() {

		return regulatorySequenceAnalysisType;
	}

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private RegulatorySequenceAnalysisType( int regulatorySequenceAnalysisType) {

		this.regulatorySequenceAnalysisType = regulatorySequenceAnalysisType;
	}

	public static RegulatorySequenceAnalysisType convertStringtoEnum( String regulatorySequenceAnalysisType) {

		if( Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT.equals( regulatorySequenceAnalysisType)){
			return DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT;
		}else if( Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT.equals( regulatorySequenceAnalysisType)){
			return DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( RegulatorySequenceAnalysisType.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT))
			return Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT;
		else if( this.equals( RegulatorySequenceAnalysisType.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT))
			return Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT;
		else
			return null;

	}

	/** An added method.  */
	public boolean isDoRegulatorySequenceAnalysisUsingRSAT() {

		return this == DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT;
	}

	/** An added method.  */
	public boolean isDoNotRegulatorySequenceAnalysisUsingRSAT() {

		return this == DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT;
	}

}
