/**
 * @author burcakotlu
 * @date Apr 22, 2014 
 * @time 10:21:01 AM
 */
package jaxbxjctool;

import java.util.List;

/**
 * 
 */
public class TfCellLineGivenInterval {
	
	String tfNameCellLineName;
	String givenIntervalName;
	
	
	String chromNamewithoutPreceedingChr;
	int givenIntervalStartOneBased;
	int givenIntervalEndOneBased;
	
	List<String> snpKeyList;
	List<String> tfCellLineBasedTfIntervalKeyList;
	
	//Will be generated from the peaks in this given interval
	String extendedPeakSequence;
	
	
	
	public String getChromNamewithoutPreceedingChr() {
		return chromNamewithoutPreceedingChr;
	}
	public void setChromNamewithoutPreceedingChr(
			String chromNamewithoutPreceedingChr) {
		this.chromNamewithoutPreceedingChr = chromNamewithoutPreceedingChr;
	}
	
	public int getGivenIntervalStartOneBased() {
		return givenIntervalStartOneBased;
	}
	public void setGivenIntervalStartOneBased(int givenIntervalStartOneBased) {
		this.givenIntervalStartOneBased = givenIntervalStartOneBased;
	}
	public int getGivenIntervalEndOneBased() {
		return givenIntervalEndOneBased;
	}
	public void setGivenIntervalEndOneBased(int givenIntervalEndOneBased) {
		this.givenIntervalEndOneBased = givenIntervalEndOneBased;
	}
	
	
	public List<String> getSnpKeyList() {
		return snpKeyList;
	}
	public void setSnpKeyList(List<String> snpKeyList) {
		this.snpKeyList = snpKeyList;
	}
	public List<String> getTfCellLineBasedTfIntervalKeyList() {
		return tfCellLineBasedTfIntervalKeyList;
	}
	public void setTfCellLineBasedTfIntervalKeyList(
			List<String> tfCellLineBasedTfIntervalKeyList) {
		this.tfCellLineBasedTfIntervalKeyList = tfCellLineBasedTfIntervalKeyList;
	}
	public String getExtendedPeakSequence() {
		return extendedPeakSequence;
	}
	public void setExtendedPeakSequence(String extendedPeakSequence) {
		this.extendedPeakSequence = extendedPeakSequence;
	}
	public String getGivenIntervalName() {
		return givenIntervalName;
	}
	public void setGivenIntervalName(String givenIntervalName) {
		this.givenIntervalName = givenIntervalName;
	}
	public String getTfNameCellLineName() {
		return tfNameCellLineName;
	}
	public void setTfNameCellLineName(String tfNameCellLineName) {
		this.tfNameCellLineName = tfNameCellLineName;
	}
	
	
	


}
