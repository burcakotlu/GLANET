/**
 * @author Burcak Otlu
 * Sep 4, 2013
 * 1:37:25 PM
 * 2013
 *
 * 
 */
package empiricalpvalues;

public class GCCharArray {
	
	char[] gcArray;
	
	public char[] getGcArray() {
		return gcArray;
	}


	public void setGcArray(char[] gcArray) {
		this.gcArray = gcArray;
	}

	public GCCharArray(int chromSize) {
		this.gcArray = new char[chromSize];
	}
	
	public GCCharArray() {
	}

}
