/**
 * @author burcakotlu
 * @date Sep 14, 2014 
 * @time 10:53:50 PM
 */
package giveninputdata;

import auxiliary.FileOperations;
import common.Commons;

/**
 * 
 */
public class Preparation {
	
	public static void prepare(String[] args){
		
		String glanetFolder = args[1];
		
		
		//jobName starts
		String jobName = args[17].trim();
		if (jobName.isEmpty()){
			jobName = "noname";
		}
		//jobName ends
			
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName +  System.getProperty("file.separator");
		
		//delete old files starts 
		FileOperations.deleteOldFiles(outputFolder);
		//delete old files ends
	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		prepare(args);
	}

}
