/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Bur�ak Otlu
 * @date Mar 24, 2015
 * @project Common 
 *
 */
public enum Annotation {

	ANNOTATION_IN_PARALEL( 1), ANNOTATION_SEQUENTIALLY( 2);

	private final int annotation;

	private Annotation( int annotation) {

		this.annotation = annotation;
	}

	public int getAnnotation() {

		return annotation;
	}

	public static Annotation convertStringtoEnum( String annotation) {

		if( Commons.ANNOTATION_IN_PARALEL.equals( annotation)){
			return ANNOTATION_IN_PARALEL;
		}else if( Commons.ANNOTATION_SEQUENTIALLY.equals( annotation)){
			return ANNOTATION_SEQUENTIALLY;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( Annotation.ANNOTATION_IN_PARALEL))
			return Commons.ANNOTATION_IN_PARALEL;
		else if( this.equals( Annotation.ANNOTATION_SEQUENTIALLY))
			return Commons.ANNOTATION_SEQUENTIALLY;
		else
			return null;
	}

	public boolean annotateInParalel() {

		return this == ANNOTATION_IN_PARALEL;
	}

	public boolean annotateSequentially() {

		return this == ANNOTATION_SEQUENTIALLY;
	}

}
