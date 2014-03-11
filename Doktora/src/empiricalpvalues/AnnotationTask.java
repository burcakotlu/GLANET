/**
 * @author Burcak Otlu
 * Jul 26, 2013
 * 5:11:43 PM
 * 2013
 *
 * 
 */
package empiricalpvalues;

public class AnnotationTask {
	
	String chromName;
	Integer permutationNumber;
	
	
	public String getChromName() {
		return chromName;
	}
	public void setChromName(String chromName) {
		this.chromName = chromName;
	}
	
		
	public Integer getPermutationNumber() {
		return permutationNumber;
	}
	public void setPermutationNumber(Integer permutationNumber) {
		this.permutationNumber = permutationNumber;
	}
	
	
	public AnnotationTask(String chromName, Integer permutationNumber) {
		super();
		this.chromName = chromName;
		this.permutationNumber = permutationNumber;
	}
	
	
	
	

}
