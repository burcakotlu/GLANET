/**
 * @author Burcak Otlu
 * Sep 16, 2013
 * 12:07:52 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import java.io.BufferedReader;

public class GCorMapabilityFile {
	
	String fileName;
	String functionalElementName;
	BufferedReader bufferedReader;
	Double standardDeviation;
	Double mean;
	
	
	

	public Double getMean() {
		return mean;
	}

	public void setMean(Double mean) {
		this.mean = mean;
	}

	public Double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(Double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getFunctionalElementName() {
		return functionalElementName;
	}

	public void setFunctionalElementName(String functionalElementName) {
		this.functionalElementName = functionalElementName;
	}

	
	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}

	public void setBufferedReader(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}




	/**
	 * 
	 */
	public GCorMapabilityFile() {
		// TODO Auto-generated constructor stub
	}

}
