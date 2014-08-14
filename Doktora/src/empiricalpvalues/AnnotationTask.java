/**
 * @author Burcak Otlu
 * Jul 26, 2013
 * 5:11:43 PM
 * 2013
 *
 * 
 */
package empiricalpvalues;

import enumtypes.ChromosomeName;

public class AnnotationTask {
	
	ChromosomeName chromName;
	int permutationNumber;
	
	
	
	public ChromosomeName getChromName() {
		return chromName;
	}



	public void setChromName(ChromosomeName chromName) {
		this.chromName = chromName;
	}





	public int getPermutationNumber() {
		return permutationNumber;
	}



	public void setPermutationNumber(int permutationNumber) {
		this.permutationNumber = permutationNumber;
	}








	public AnnotationTask(ChromosomeName chromName, int permutationNumber) {
		super();
		this.chromName = chromName;
		this.permutationNumber = permutationNumber;
	}
	
	
	
	

}
