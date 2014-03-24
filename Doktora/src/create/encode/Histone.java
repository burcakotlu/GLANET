package create.encode;


public class Histone implements Comparable<Histone>{
	
	String histoneName;
	String chromName;
	String cellLineName;
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
	public String getHistoneName() {
		return histoneName;
	}
	public void setHistoneName(String histoneName) {
		this.histoneName = histoneName;
	}
	public String getChromName() {
		return chromName;
	}
	public void setChromName(String chromName) {
		this.chromName = chromName;
	}
	public String getCellLineName() {
		return cellLineName;
	}
	public void setCellLineName(String cellLineName) {
		this.cellLineName = cellLineName;
	}
	public Integer getStartPos() {
		return startPos;
	}
	public void setStartPos(Integer startPos) {
		this.startPos = startPos;
	}
	public int getEndPos() {
		return endPos;
	}
	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    
    
	 static final Comparator<Histone> START_POSITION_ORDER = 
	        new Comparator<Histone>() {
			 	public int compare(Histone histone1, Histone histone2) {
			 		return histone1.getStartPos().compareTo(histone2.getStartPos());
			 	}
		 	};


		@Override
		public int compareTo(Histone histone) {
			// TODO Auto-generated method stub
			if (this.startPos.intValue() < histone.getStartPos().intValue())
				return -1;
			else if (this.startPos.intValue() == histone.getStartPos().intValue())
				return 0;
			else 
				return 1;
		}
}
