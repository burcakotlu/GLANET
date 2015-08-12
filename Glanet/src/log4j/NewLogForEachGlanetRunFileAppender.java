/**
 * This is a customized log4j appender, which will create a new log file for every
 * run of the application.
 */
package log4j;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorCode;
import auxiliary.FileOperations;
import common.Commons;

/**
 * @author Bur�ak Otlu
 * @date Dec 9, 2014
 * @project Glanet
 *
 */
public class NewLogForEachGlanetRunFileAppender extends FileAppender {

	// This method is executed by internal calls
	// I don't know whether I can pass outputFolder to this method
	public NewLogForEachGlanetRunFileAppender() {

		// System.out.println("***************************************************");
		// System.out.println("NewLogForEachGlanetRunFileAppender()");
	}

	public NewLogForEachGlanetRunFileAppender( Layout layout, String filename, boolean append, boolean bufferedIO,
			int bufferSize) throws IOException {

		super( layout, filename, append, bufferedIO, bufferSize);
	}

	public NewLogForEachGlanetRunFileAppender( Layout layout, String filename, boolean append) throws IOException {

		super( layout, filename, append);
	}

	public NewLogForEachGlanetRunFileAppender( Layout layout, String filename) throws IOException {

		super( layout, filename);
	}

	// This method is executed by internal calls
	// I don't know whether I can pass outputFolder to this method
	// @overwrite
	public void activateOptions() {

		// //for debug
		// System.out.println("Before NewLogForEachGlanetRunFileAppender activateOptions() fileName: "
		// + fileName);
		// //for debug

		if( fileName != null){
			try{
				fileName = getNewLogFileName();
				FileOperations.createFile( fileName);
				setFile( fileName, fileAppend, bufferedIO, bufferSize);

			}catch( Exception e){
				errorHandler.error( "Error while activating log options", e, ErrorCode.FILE_OPEN_FAILURE);
			}
		}// End of IF filename is not NULL

		// //for debug
		// System.out.println("After NewLogForEachGlanetRunFileAppender activateOptions() fileName: "
		// + fileName);
		// System.out.println("***************************************************");
		// //for debug

		// Log4jConfiguration.setFileName(fileName);

	}

	private String getNewLogFileName() {

		if( fileName != null){

			final File logFile = new File( fileName);
			final String fileName = logFile.getName();
			String newFileName = "";

			final int dotIndex = fileName.indexOf( Commons.DOT);
			if( dotIndex != -1){
				// the file name has an extension. so, insert the time stamp
				// between the file name and the extension
				newFileName = fileName.substring( 0, dotIndex) + Commons.STRING_HYPHEN + System.currentTimeMillis() + Commons.DOT + fileName.substring( dotIndex + 1);
			}else{
				// the file name has no extension. So, just append the timestamp
				// at the end.
				newFileName = fileName + Commons.STRING_HYPHEN + System.currentTimeMillis();
			}

			if( logFile.getParent() != null){
				return logFile.getParent() + System.getProperty( "file.separator") + newFileName;
			}else{
				return newFileName;
			}

		}// End of IF
		return null;
	}

}
