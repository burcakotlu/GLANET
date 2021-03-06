/**
 * @author burcakotlu
 * @date Apr 3, 2014 
 * @time 11:34:37 AM
 */
package jaxbxjctool;

import enumtypes.Orient;

/**
 * 
 */
public class RsInformation {

	int rsId;

	String chrNameWithoutChr;

	int zeroBasedStart;
	int zeroBasedEnd;
	String slashSeparatedObservedAlleles;

	Orient orient;
	String groupLabel;
	
	String snpClass;
	
	
	

	public String getSnpClass() {
		return snpClass;
	}

	public void setSnpClass(String snpClass) {
		this.snpClass = snpClass;
	}

	public String getGroupLabel() {

		return groupLabel;
	}

	public void setGroupLabel( String groupLabel) {

		this.groupLabel = groupLabel;
	}

	public Orient getOrient() {

		return orient;
	}

	public void setOrient( Orient orient) {

		this.orient = orient;
	}

	public String getChrNameWithoutChr() {

		return chrNameWithoutChr;
	}

	public void setChrNameWithoutChr( String chrNameWithoutChr) {

		this.chrNameWithoutChr = chrNameWithoutChr;
	}

	public int getZeroBasedStart() {

		return zeroBasedStart;
	}

	public void setZeroBasedStart( int zeroBasedStart) {

		this.zeroBasedStart = zeroBasedStart;
	}

	public int getZeroBasedEnd() {

		return zeroBasedEnd;
	}

	public void setZeroBasedEnd( int zeroBasedEnd) {

		this.zeroBasedEnd = zeroBasedEnd;
	}

	public int getRsId() {

		return rsId;
	}

	public void setRsId( int rsId) {

		this.rsId = rsId;
	}

	public String getSlashSeparatedObservedAlleles() {

		return slashSeparatedObservedAlleles;
	}

	public void setSlashSeparatedObservedAlleles( String slashSeparatedObservedAlleles) {

		this.slashSeparatedObservedAlleles = slashSeparatedObservedAlleles;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args) {

		// TODO Auto-generated method stub

	}

}
