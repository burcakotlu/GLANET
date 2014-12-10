/**
 * 
 */
package log4j;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;


/**
 * @author Burçak Otlu
 * @date Dec 8, 2014
 * @project Common 
 *
 */
public class Log4jConfiguration {
	
	//private static Logger logger = Logger.getLogger(Log4jConfiguration.class);
	
	private static String fileName;
	
		
	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String fileName) {
		Log4jConfiguration.fileName = fileName;
	}


	public static void getGlanetApplicationLogger(String dataFolder, String outputFolder) throws IOException{
		
		String log4jPropertiesFile = "log4j.properties";
		
		//String glanetLogFile = outputFolder + fileName;
		
		String glanetLogFile;
		String glanetLogFileUnderOutputFolder;
		
		
		
		 Properties logProp = new Properties();      
		    try     
		    {      
		    	//FileInputStream gives error  	
		    	//logProp.load(new FileInputStream(log4jPropertiesFile));
		    	
		    	ClassLoader loader = Thread.currentThread().getContextClassLoader();
				try(InputStream resourceStream = loader.getResourceAsStream(log4jPropertiesFile)) {
				    logProp.load(resourceStream);
				}
				
				glanetLogFile = logProp.getProperty("log4j.appender.file.File");
				//for debug
				System.out.println("glanetLogFile: " + glanetLogFile);
		    	//for debug

		    	glanetLogFileUnderOutputFolder = outputFolder + glanetLogFile;
		    	
		    	logProp.setProperty("log4j.appender.file.File", glanetLogFileUnderOutputFolder); 
		    	
		    	//for debug
		    	System.out.println("glanetLogFileUnderOutputFolder: " + logProp.getProperty("log4j.appender.file.File"));
		    	//for debug
		    	System.out.println("Logging enabled");   
		    	
		    	//This calls activateOptions of NewLogForEachGlanetRunFileAppender class
		    	PropertyConfigurator.configure(logProp); 
		    	
		    	

		    }     
		    catch(IOException e)                
		    {       
		    	System.out.println("Logging not enabled");       
		    }  
	}

	
	
	

}
