/**
 * @author burcakotlu
 * @date Apr 3, 2014 
 * @time 11:34:37 AM
 */
package jaxbxjctool;

/**
 * 
 */
public class RsInformation {
	
	String rsId;
	String chrNameWithoutChr;
	
	int zeroBasedStart;
	int zeroBasedEnd;
	String observedAlleles;
	
	String orient;
	String groupLabel;
	
	
	public String getGroupLabel() {
		return groupLabel;
	}



	public void setGroupLabel(String groupLabel) {
		this.groupLabel = groupLabel;
	}



	public String getOrient() {
		return orient;
	}



	public void setOrient(String orient) {
		this.orient = orient;
	}







	



	public String getChrNameWithoutChr() {
		return chrNameWithoutChr;
	}



	public void setChrNameWithoutChr(String chrNameWithoutChr) {
		this.chrNameWithoutChr = chrNameWithoutChr;
	}



	public int getZeroBasedStart() {
		return zeroBasedStart;
	}



	public void setZeroBasedStart(int zeroBasedStart) {
		this.zeroBasedStart = zeroBasedStart;
	}



	public int getZeroBasedEnd() {
		return zeroBasedEnd;
	}



	public void setZeroBasedEnd(int zeroBasedEnd) {
		this.zeroBasedEnd = zeroBasedEnd;
	}



	public String getRsId() {
		return rsId;
	}



	public void setRsId(String rsId) {
		this.rsId = rsId;
	}






	public String getObservedAlleles() {
		return observedAlleles;
	}



	public void setObservedAlleles(String observedAlleles) {
		this.observedAlleles = observedAlleles;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
