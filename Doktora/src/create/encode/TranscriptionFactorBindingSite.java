package create.encode;


public class TranscriptionFactorBindingSite implements Comparable<TranscriptionFactorBindingSite>  {
	
	String transcriptionFactorName;
	String chromName;
	String cellLineName;
//	In order to compare with respect to startPos it has to be Integer
	Integer startPos;
	int endPos;	
    String fileName;
    
    char strand;
    
    
	
	
	
	public char getStrand() {
		return strand;
	}
	public void setStrand(char strand) {
		this.strand = strand;
	}
	public int getEndPos() {
		return endPos;
	}
	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}
	public void setStartPos(Integer startPos) {
		this.startPos = startPos;
	}
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
	public String getChromName() {
		return chromName;
	}
	public void setChromName(String chromName) {
		this.chromName = chromName;
	}
	public Integer getStartPos() {
		return startPos;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	 static final Comparator<TranscriptionFactorBindingSite> START_POSITION_ORDER = 
        new Comparator<TranscriptionFactorBindingSite>() {
		 	public int compare(TranscriptionFactorBindingSite tfbs1, TranscriptionFactorBindingSite tfbs2) {
		 		return tfbs1.getStartPos().compareTo(tfbs2.getStartPos());
		 	}
	 	};



	@Override
	public int compareTo(TranscriptionFactorBindingSite tfbs) {
		// TODO Auto-generated method stub
		if (this.startPos.intValue() < tfbs.getStartPos().intValue())
			return -1;
		else if (this.startPos.intValue() == tfbs.getStartPos().intValue())
			return 0;
		else 
			return 1;
	
	
	}


	
	

}
