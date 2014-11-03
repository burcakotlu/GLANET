/**
 * 
 */
package enumtypes;

import common.Commons;


/**
 * @author Burçak Otlu
 * @date Nov 3, 2014
 * @project Common 
 *
 */
public enum GivenIntervalsInputFileDataFormat {
	
	INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE(1),
	INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE(2),
	INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE(3),
	INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE(4),
	INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE(5);
	
	
	private final int givenIntervalsInputFileDataFormat;
	
	public int getGivenIntervalsInputFileDataFormat() {
		return givenIntervalsInputFileDataFormat;
	}

	private GivenIntervalsInputFileDataFormat(int givenIntervalsInputFileDataFormat) {
    	this.givenIntervalsInputFileDataFormat = givenIntervalsInputFileDataFormat;
	}
	
	
	public static GivenIntervalsInputFileDataFormat convertStringtoEnum(String givenIntervalsInputFileDataFormat){
    	
    	if (Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE.equals(givenIntervalsInputFileDataFormat)){
    		return INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
    	}else if  (Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE.equals(givenIntervalsInputFileDataFormat)){
    		return INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
    	}else if (Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE.equals(givenIntervalsInputFileDataFormat)){
    		return INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;	
    	}else if (Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE.equals(givenIntervalsInputFileDataFormat)){
    		return INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE;
    	}else if (Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE.equals(givenIntervalsInputFileDataFormat)){
    		return INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
    	}else
    		return null;
	}

	
	public static String convertEnumtoString(GivenIntervalsInputFileDataFormat givenIntervalsInputFileDataFormat){
    	
    	if (INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE.equals(givenIntervalsInputFileDataFormat)){
    		return Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
    	}else if  (INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE.equals(givenIntervalsInputFileDataFormat)){
    		return Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
    	}else if (INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE.equals(givenIntervalsInputFileDataFormat)){
    		return Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;	
    	}else if (INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE.equals(givenIntervalsInputFileDataFormat)){
    		return Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE;
    	}else if (INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE.equals(givenIntervalsInputFileDataFormat)){
    		return Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
    	}else
    		return null;
	}


}
