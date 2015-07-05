/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Bur�ak Otlu
 * @date Feb 18, 2015
 * @project Common 
 *
 */
public enum PerformEnrichment {

	DO_ENRICH( 1), DO_NOT_ENRICH( 2);

	private final int performEnrichment;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private PerformEnrichment( int performEnrichment) {

		this.performEnrichment = performEnrichment;
	}

	public int getPerformEnrichment() {

		return performEnrichment;
	}

	public static PerformEnrichment convertStringtoEnum( String enrichment) {

		if( Commons.DO_ENRICH.equals( enrichment)){
			return DO_ENRICH;
		}else if( Commons.DO_NOT_ENRICH.equals( enrichment)){
			return DO_NOT_ENRICH;
		}else{
			return null;
		}

	}

	public String convertEnumtoString() {

		if( this.equals( PerformEnrichment.DO_ENRICH))
			return Commons.DO_ENRICH;
		else if( this.equals( PerformEnrichment.DO_NOT_ENRICH))
			return Commons.DO_NOT_ENRICH;
		else
			return null;
	}

	public boolean performEnrichment() {

		return this == DO_ENRICH;
	}

}
