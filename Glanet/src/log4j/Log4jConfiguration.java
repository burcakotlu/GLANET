/**
 * 
 */
package log4j;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import common.Commons;


/**
 * @author Burçak Otlu
 * @date Dec 8, 2014
 * @project Common 
 *
 */
public class Log4jConfiguration {
	
	private static Logger logger = Logger.getLogger(Log4jConfiguration.class);
	 
	
//	public void updateLog4jConfiguration(String dataFolder, String outputFolder) { 
//		 
//	 	String logFile = outputFolder + Commons.GLANET_LOG_FILE;
//	 	
//	    Properties props = new Properties(); 
//	    try { 
//	    	PropertyConfigurator.configure( dataFolder + "log4j.properties");
//	    	PropertyConfigurator.
//	        InputStream configStream = getClass().getResourceAsStream(dataFolder + "log4j.properties"); 
//	        props.load(configStream); 
//	        configStream.close(); 
//	    } catch (IOException e) { 
//	        System.out.println("Errornot laod configuration file "); 
//	    } 
//	    props.setProperty("log4j.appender.file.File", logFile); 
//	    LogManager.resetConfiguration(); 
//	    PropertyConfigurator.configure(props); 
//	}

	
//	public Logger getAppLogger(String dataFolder, String outputFolder,Logger rootLogger) throws IOException{
//		
//		String logFile = outputFolder + Commons.GLANET_LOG_FILE;
//		 
//		Layout pattern = new PatternLayout("%d{MM-dd-yyyy HH:mm:ss,SSS} %-5p %c:%m%n");
//		Appender newAppender = null;
//		newAppender = new FileAppender(pattern, logFile, true);
//		rootLogger.setAdditivity(false);
//		rootLogger.setLevel(Level.INFO);
//		rootLogger.addAppender(newAppender); 
//		return rootLogger;
//
//	}
	
	public void getAppLogger(String dataFolder, String outputFolder) throws IOException{
		
		String glanetLogFile = outputFolder + Commons.GLANET_LOG_FILE;
//		String log4jPropertiesFile = dataFolder + "log4j.properties";
		String log4jPropertiesFile = "log4j.properties";
		
		//Can we do it without using dataFolder? YES
		//Can we do it using the lib folder? YES
		//Keep in mind that user will have GLANET.jar and DATA folders
		//String log4jPropertiesFile = System.getProperty("user.dir") + System.getProperty("file.separator") + "src" +  System.getProperty("file.separator") + "log4j" + System.getProperty("file.separator") +"log4j.properties";
		
		 
		 Properties logProp = new Properties();      
		    try     
		    {      
		    	  	
		    	//logProp.load(new FileInputStream(log4jPropertiesFile)); 
		    	//logProp.load(new FileInputStream("." + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "log4j" + System.getProperty("file.separator")  +log4jPropertiesFile)); 
		    	logProp.load(new FileInputStream("." + System.getProperty("file.separator")  +log4jPropertiesFile)); 
		    	logProp.setProperty("log4j.appender.file.File", glanetLogFile); 
		    	logger.info("Logging enabled");   
//		    	LogManager.resetConfiguration(); 
		    	PropertyConfigurator.configure(logProp); 
		    	
		    	

		    }     
		    catch(IOException e)                
		    {       
		    	System.out.println("Logging not enabled");       
		    }  

	}
	
	
	

}
