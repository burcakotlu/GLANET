/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Bur�ak Otlu
 * @date Nov 21, 2014
 * @project Common 
 *
 */
public enum FileFormatType {

	NARROWPEAK( 1), BED( 2), FILE_FORMAT_TYPE_OTHER( 3);

	private final int fileFormatType;

	private FileFormatType( int fileFormatType) {

		this.fileFormatType = fileFormatType;
	}

	public int getFileFormatType() {

		return fileFormatType;
	}

	public static FileFormatType convertStringtoEnum( String fileFormatType) {

		if( Commons.NARROWPEAK.equals( fileFormatType)){
			return NARROWPEAK;
		}else if( Commons.BED.equals( fileFormatType)){
			return BED;
		}else if( Commons.FILE_FORMAT_TYPE_OTHER.equals( fileFormatType)){
			return FILE_FORMAT_TYPE_OTHER;
		}else
			return null;
	}

	public String convertEnumtoString() {

		if( this.equals( FileFormatType.NARROWPEAK))
			return Commons.NARROWPEAK;
		else if( this.equals( FileFormatType.BED))
			return Commons.BED;
		else if( this.equals( FileFormatType.FILE_FORMAT_TYPE_OTHER))
			return Commons.FILE_FORMAT_TYPE_OTHER;
		else
			return null;
	}

	/** An added method.  */
	public boolean isNarrowPeak() {

		return this == NARROWPEAK;
	}

}
