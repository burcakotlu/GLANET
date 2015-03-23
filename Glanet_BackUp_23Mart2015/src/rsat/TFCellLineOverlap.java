/**
 * 
 */
package rsat;

/**
 * @author Bur�ak Otlu
 * @date Dec 29, 2014
 * @project Glanet
 *
 */
public class TFCellLineOverlap {

	String tfName;
	String cellLineName;
	String fileName;

	int oneBasedStart;
	int oneBasedEnd;

	public String getTfName() {
		return tfName;
	}

	public void setTfName(String tfName) {
		this.tfName = tfName;
	}

	public String getCellLineName() {
		return cellLineName;
	}

	public void setCellLineName(String cellLineName) {
		this.cellLineName = cellLineName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getOneBasedStart() {
		return oneBasedStart;
	}

	public void setOneBasedStart(int oneBasedStart) {
		this.oneBasedStart = oneBasedStart;
	}

	public int getOneBasedEnd() {
		return oneBasedEnd;
	}

	public void setOneBasedEnd(int oneBasedEnd) {
		this.oneBasedEnd = oneBasedEnd;
	}

	public TFCellLineOverlap(String tfName, String cellLineName, String fileName, int oneBasedStart, int oneBasedEnd) {
		super();
		this.tfName = tfName;
		this.cellLineName = cellLineName;
		this.fileName = fileName;
		this.oneBasedStart = oneBasedStart;
		this.oneBasedEnd = oneBasedEnd;
	}

}
