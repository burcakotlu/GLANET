package create.encode;

public class CellLineTranscriptionFactor {

	String cellLineName;
	String transcriptionFactorName;
	String fileName;

	public String getCellLineName() {
		return cellLineName;
	}

	public void setCellLineName(String cellLineName) {
		this.cellLineName = cellLineName;
	}

	public String getTranscriptionFactorName() {
		return transcriptionFactorName;
	}

	public void setTranscriptionFactorName(String transcriptionFactorName) {
		this.transcriptionFactorName = transcriptionFactorName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public CellLineTranscriptionFactor(String cellLineName, String transcriptionFactorName, String fileName) {
		super();
		this.cellLineName = cellLineName;
		this.transcriptionFactorName = transcriptionFactorName;
		// this.fileName = fileName;
	}

	public CellLineTranscriptionFactor() {
		super();
		// TODO Auto-generated constructor stub
	}

}
