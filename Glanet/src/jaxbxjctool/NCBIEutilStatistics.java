/**
 * 
 */
package jaxbxjctool;

/**
 * @author Burçak Otlu
 * @date Jan 16, 2015
 * @project Glanet 
 *
 */
public class NCBIEutilStatistics {
	
	int numberofRsIDsDoesNotMapToAnyAssembly;
	int numberofRsIDsDoesNotReturnAnyRs;

	
	
	public int getNumberofRsIDsDoesNotMapToAnyAssembly() {
		return numberofRsIDsDoesNotMapToAnyAssembly;
	}



	public void setNumberofRsIDsDoesNotMapToAnyAssembly(int numberofRsIDsDoesNotMapToAnyAssembly) {
		this.numberofRsIDsDoesNotMapToAnyAssembly = numberofRsIDsDoesNotMapToAnyAssembly;
	}



	public int getNumberofRsIDsDoesNotReturnAnyRs() {
		return numberofRsIDsDoesNotReturnAnyRs;
	}



	public void setNumberofRsIDsDoesNotReturnAnyRs(int numberofRsIDsDoesNotReturnAnyRs) {
		this.numberofRsIDsDoesNotReturnAnyRs = numberofRsIDsDoesNotReturnAnyRs;
	}



	
	

	public NCBIEutilStatistics(int numberofRsIDsDoesNotMapToAnyAssembly, int numberofRsIDsDoesNotReturnAnyRs) {
		super();
		this.numberofRsIDsDoesNotMapToAnyAssembly = numberofRsIDsDoesNotMapToAnyAssembly;
		this.numberofRsIDsDoesNotReturnAnyRs = numberofRsIDsDoesNotReturnAnyRs;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
