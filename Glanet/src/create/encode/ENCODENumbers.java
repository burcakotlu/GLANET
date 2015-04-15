package create.encode;

/**
 * @author Burcak Otlu
 * @date Nov 17, 2014
 * @project Glanet
 *
 */
public class ENCODENumbers {

	int dnaseCellLineNumber;
	int tfNumber;
	int histoneNumber;
	int cellLineNumber;
	int tfCellLineNumber;
	int histoneCellLineNumber;
	int fileNumber;

	public int getDnaseCellLineNumber() {
		return dnaseCellLineNumber;
	}

	public void setDnaseCellLineNumber(int dnaseCellLineNumber) {
		this.dnaseCellLineNumber = dnaseCellLineNumber;
	}

	public int getTfNumber() {
		return tfNumber;
	}

	public void setTfNumber(int tfNumber) {
		this.tfNumber = tfNumber;
	}

	public int getHistoneNumber() {
		return histoneNumber;
	}

	public void setHistoneNumber(int histoneNumber) {
		this.histoneNumber = histoneNumber;
	}

	public int getCellLineNumber() {
		return cellLineNumber;
	}

	public void setCellLineNumber(int cellLineNumber) {
		this.cellLineNumber = cellLineNumber;
	}

	public int getTfCellLineNumber() {
		return tfCellLineNumber;
	}

	public void setTfCellLineNumber(int tfCellLineNumber) {
		this.tfCellLineNumber = tfCellLineNumber;
	}

	public int getHistoneCellLineNumber() {
		return histoneCellLineNumber;
	}

	public void setHistoneCellLineNumber(int histoneCellLineNumber) {
		this.histoneCellLineNumber = histoneCellLineNumber;
	}

	public int getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}

	public ENCODENumbers() {
		
		this.dnaseCellLineNumber = 1;
		this.cellLineNumber = 1;
		this.tfNumber = 1;
		this.histoneNumber = 1;
		this.tfCellLineNumber = 1;
		this.histoneCellLineNumber = 1;
		this.fileNumber = 1;

	}

}
