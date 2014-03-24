package create.encode;

import java.util.Comparator;


public class Dnase implements Comparable<Dnase> {
	
	
	
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
	
	
	
	
	
	
	static final Comparator<Dnase> START_POSITION_ORDER = 
        new Comparator<Dnase>() {
		 	public int compare(Dnase dnase1, Dnase dnase2) {
		 		return dnase1.getStartPos().compareTo(dnase2.getStartPos());
		 	}
	 	};



	@Override
	public int compareTo(Dnase dnase) {
		// TODO Auto-generated method stub
		if (this.startPos.intValue() < dnase.getStartPos().intValue())
			return -1;
		else if (this.startPos.intValue() == dnase.getStartPos().intValue())
			return 0;
		else 
			return 1;
	
	
	}
    
}
