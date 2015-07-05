/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Bur�ak Otlu
 * @date Dec 24, 2014
 * @project Common 
 *
 */
public enum GivenInputDataType {

	GIVEN_INPUT_DATA_CONSISTS_OF_SNPS( 1), GIVEN_INPUT_DATA_CONSISTS_OF_MIXED_LENGTH_INTERVALS( 2);

	private final int givenInputDataType;

	public int getGivenInputDataType() {

		return givenInputDataType;
	}

	private GivenInputDataType( int givenInputDataType) {

		this.givenInputDataType = givenInputDataType;
	}

	public static GivenInputDataType convertStringtoEnum( String givenInputDataType) {

		if( Commons.GIVEN_INPUT_DATA_CONSISTS_OF_SNPS.equals( givenInputDataType)){
			return GIVEN_INPUT_DATA_CONSISTS_OF_SNPS;
		}else if( Commons.GIVEN_INPUT_DATA_CONSISTS_OF_MIXED_LENGTH_INTERVALS.equals( givenInputDataType)){
			return GIVEN_INPUT_DATA_CONSISTS_OF_MIXED_LENGTH_INTERVALS;
		}else
			return null;
	}

	public static String convertEnumtoString( GivenInputDataType givenInputDataType) {

		if( GIVEN_INPUT_DATA_CONSISTS_OF_SNPS.equals( givenInputDataType)){
			return Commons.GIVEN_INPUT_DATA_CONSISTS_OF_SNPS;
		}else if( GIVEN_INPUT_DATA_CONSISTS_OF_MIXED_LENGTH_INTERVALS.equals( givenInputDataType)){
			return Commons.GIVEN_INPUT_DATA_CONSISTS_OF_MIXED_LENGTH_INTERVALS;
		}else
			return null;
	}

	/** An added method.  */
	public boolean isGivenInputDataSNP() {

		return this == GIVEN_INPUT_DATA_CONSISTS_OF_SNPS;
	}

}
