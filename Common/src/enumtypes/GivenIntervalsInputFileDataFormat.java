/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burcak Otlu
 * @date Nov 3, 2014
 * @project Common 
 *
 */
public enum GivenIntervalsInputFileDataFormat {

	INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES(1),
	INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES(2),
	INPUT_FILE_FORMAT_DBSNP_IDS(3),
	INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES(4),
	INPUT_FILE_FORMAT_NARROWPEAK_0BASED_START_ENDEXCLUSIVE_COORDINATES(5),
	INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES(6);

	private final int givenIntervalsInputFileDataFormat;

	public int getGivenIntervalsInputFileDataFormat() {

		return givenIntervalsInputFileDataFormat;
	}

	private GivenIntervalsInputFileDataFormat(int givenIntervalsInputFileDataFormat) {

		this.givenIntervalsInputFileDataFormat = givenIntervalsInputFileDataFormat;
	}

	public static GivenIntervalsInputFileDataFormat convertStringtoEnum(String givenIntervalsInputFileDataFormat) {

		if(Commons.INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES.equals(givenIntervalsInputFileDataFormat)){
			return INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES;
		}else if(Commons.INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES.equals(givenIntervalsInputFileDataFormat)){
			return INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES;
		}else if(Commons.INPUT_FILE_FORMAT_DBSNP_IDS.equals(givenIntervalsInputFileDataFormat)){
			return INPUT_FILE_FORMAT_DBSNP_IDS;
		}else if(Commons.INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES.equals(givenIntervalsInputFileDataFormat)){
			return INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES;
		}else if(Commons.INPUT_FILE_FORMAT_NARROWPEAK_0BASED_START_ENDEXCLUSIVE_COORDINATES.equals(givenIntervalsInputFileDataFormat)){
			return INPUT_FILE_FORMAT_NARROWPEAK_0BASED_START_ENDEXCLUSIVE_COORDINATES;
		}else if(Commons.INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES.equals(givenIntervalsInputFileDataFormat)){
			return INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES;
		}else
			return null;
	}

	public static String convertEnumtoString(GivenIntervalsInputFileDataFormat givenIntervalsInputFileDataFormat) {

		if(INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES.equals(givenIntervalsInputFileDataFormat)){
			return Commons.INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES;
		}else if(INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES.equals(givenIntervalsInputFileDataFormat)){
			return Commons.INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES;
		}else if(INPUT_FILE_FORMAT_DBSNP_IDS.equals(givenIntervalsInputFileDataFormat)){
			return Commons.INPUT_FILE_FORMAT_DBSNP_IDS;
		}else if(INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES.equals(givenIntervalsInputFileDataFormat)){
			return Commons.INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES;
		}else if(INPUT_FILE_FORMAT_NARROWPEAK_0BASED_START_ENDEXCLUSIVE_COORDINATES.equals(givenIntervalsInputFileDataFormat)){
			return Commons.INPUT_FILE_FORMAT_NARROWPEAK_0BASED_START_ENDEXCLUSIVE_COORDINATES;
		}else if(INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES.equals(givenIntervalsInputFileDataFormat)){
			return Commons.INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES;
		}else
			return null;
	}

}
