/**
 * 
 */
package trees;

/**
 * @author Burçak Otlu
 * @date Jul 8, 2016
 * @project Glanet 
 *
 */
public class DnaseIntervalMarkdeBerg extends IntervalMarkdeBerg {
	
	int cellLineNumber;
	int fileNumber;
	
	

	public int getCellLineNumber() {
		return cellLineNumber;
	}

	public void setCellLineNumber(int cellLineNumber) {
		this.cellLineNumber = cellLineNumber;
	}

	public int getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}
	
	

	public DnaseIntervalMarkdeBerg(int low, int high, int cellLineNumber, int fileNumber) {
		super(low, high);
		this.cellLineNumber = cellLineNumber;
		this.fileNumber = fileNumber;
	}

	/**
	 * 
	 */
	public DnaseIntervalMarkdeBerg() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
