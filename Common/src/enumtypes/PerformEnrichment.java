/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burcak Otlu
 * @date Feb 18, 2015
 * @project Common 
 *
 */
public enum PerformEnrichment {

	DO_ENRICH(1),
	DO_ENRICH_WITHOUT_ANNOTATION(2),
	DO_NOT_ENRICH(3);

	private final int performEnrichment;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private PerformEnrichment(int performEnrichment) {

		this.performEnrichment = performEnrichment;
	}

	public int getPerformEnrichment() {

		return performEnrichment;
	}

	public static PerformEnrichment convertStringtoEnum(String enrichment) {

		if(Commons.DO_ENRICH.equalsIgnoreCase(enrichment)){
			return DO_ENRICH;
		}else if (Commons.DO_ENRICH_WITHOUT_ANNOTATION.equalsIgnoreCase(enrichment)){
			return DO_ENRICH_WITHOUT_ANNOTATION;
		}else if(Commons.DO_NOT_ENRICH.equalsIgnoreCase(enrichment)){
			return DO_NOT_ENRICH;
		}else{
			return null;
		}

	}

	public String convertEnumtoString() {

		if(this.equals(PerformEnrichment.DO_ENRICH))
			return Commons.DO_ENRICH;
		else if (this.equals(PerformEnrichment.DO_ENRICH_WITHOUT_ANNOTATION))
			return Commons.DO_ENRICH_WITHOUT_ANNOTATION;
		else if (this.equals(PerformEnrichment.DO_NOT_ENRICH))
			return Commons.DO_NOT_ENRICH;
		else
			return null;
	}

	public boolean performEnrichment() {

		return (this == DO_ENRICH || this== DO_ENRICH_WITHOUT_ANNOTATION);
	}

	public boolean performEnrichmentWithoutAnnotation() {
		return (this== DO_ENRICH_WITHOUT_ANNOTATION);
	}

}
