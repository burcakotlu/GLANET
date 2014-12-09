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

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Dec 9, 2014
 * @project Glanet 
 *
 */
public class NewLogForEachGlanetRunFileAppender extends FileAppender {
	
	
	public NewLogForEachGlanetRunFileAppender() {
	}

	public NewLogForEachGlanetRunFileAppender(Layout layout, String filename,
	        boolean append, boolean bufferedIO, int bufferSize)
	        throws IOException {
	    super(layout, filename, append, bufferedIO, bufferSize);
	}

	public NewLogForEachGlanetRunFileAppender(Layout layout, String filename,
	        boolean append) throws IOException {
	    super(layout, filename, append);
	}

	public NewLogForEachGlanetRunFileAppender(Layout layout, String filename)
	        throws IOException {
	    super(layout, filename);
	}

	//@overwrite
	public void activateOptions() {
		if (fileName != null) {
		    try {
		        fileName = getNewLogFileName();
		        setFile(fileName, fileAppend, bufferedIO, bufferSize);
		    } catch (Exception e) {
		        errorHandler.error("Error while activating log options", e,
		                ErrorCode.FILE_OPEN_FAILURE);
		    }
		}
	}
	
	
	private String getNewLogFileName() {
		
		if (fileName != null) {
		  
			final File logFile = new File(fileName);
		    final String fileName = logFile.getName();
		    String newFileName = "";

		    final int dotIndex = fileName.indexOf(Commons.DOT);
		    if (dotIndex != -1) {
		        // the file name has an extension. so, insert the time stamp
		        // between the file name and the extension
		        newFileName = fileName.substring(0, dotIndex) +  Commons.STRING_HYPHEN + System.currentTimeMillis()  + Commons.DOT + fileName.substring(dotIndex+1);
		    } else {
		        // the file name has no extension. So, just append the timestamp
		        // at the end.
		        newFileName = fileName +  Commons.STRING_HYPHEN  +System.currentTimeMillis();
		    }
		    return logFile.getParent()  +  System.getProperty("file.separator")  + newFileName;
		}
		return null;
	}

}
