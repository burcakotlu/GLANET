/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package testlinearsearchversusintervaltreesearch;

public class CommonCompareObject {
	
	String objectName;
	String chromName;
	int low;
	int high;
	String cellLineName;
	String fileName;
	String tfbsorHistoneName;
	String refSeqName;
	String intervalName;
	String hugoSymbol;
	int entrezId;
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getChromName() {
		return chromName;
	}
	public void setChromName(String chromName) {
		this.chromName = chromName;
	}
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
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
	public String getTfbsorHistoneName() {
		return tfbsorHistoneName;
	}
	public void setTfbsorHistoneName(String tfbsorHistoneName) {
		this.tfbsorHistoneName = tfbsorHistoneName;
	}
	public String getRefSeqName() {
		return refSeqName;
	}
	public void setRefSeqName(String refSeqName) {
		this.refSeqName = refSeqName;
	}
	public String getIntervalName() {
		return intervalName;
	}
	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}
	public String getHugoSymbol() {
		return hugoSymbol;
	}
	public void setHugoSymbol(String hugoSymbol) {
		this.hugoSymbol = hugoSymbol;
	}
	public int getEntrezId() {
		return entrezId;
	}
	public void setEntrezId(int entrezId) {
		this.entrezId = entrezId;
	}
	
//	for Dnase Object
	public CommonCompareObject(String objectName, String chromName, int low,
			int high, String cellLineName, String fileName) {
		super();
		
		this.objectName = objectName;
		this.chromName = chromName;
		this.low = low;
		this.high = high;
		this.cellLineName = cellLineName;
		this.fileName = fileName;
	}
	
//	for tfbs or Histone Object
	public CommonCompareObject(String objectName, String chromName, int low,
			int high, String tfbsorHistoneName,String cellLineName, String fileName) {
		super();
		
		this.objectName = objectName;
		this.chromName = chromName;
		this.low = low;
		this.high = high;
		this.tfbsorHistoneName = tfbsorHistoneName;
		this.cellLineName = cellLineName;
		this.fileName = fileName;
	}

//	for ucscRefSeqGene Object
	public CommonCompareObject(String objectName, String chromName, int low,
			int high, String refSeqName, String intervalName, String hugoSymbol, int entrezId) {
		super();
		
		this.objectName = objectName;
		this.chromName = chromName;
		this.low = low;
		this.high = high;
		this.refSeqName = refSeqName;
		this.intervalName = intervalName;
		this.hugoSymbol = hugoSymbol;
		this.entrezId = entrezId;
	}
	@Override
	public boolean equals(Object obj) {
		
		if (this.getClass() == obj.getClass()){
			
			CommonCompareObject commonObject = (CommonCompareObject) obj;
			
			if 	((this.getObjectName().equals("ucscRefSeqGene")) &&
				(this.getRefSeqName().equals(commonObject.getRefSeqName())) &&
				(this.getIntervalName().equals(commonObject.getIntervalName())) &&
				(this.getHugoSymbol().equals(commonObject.getHugoSymbol())) &&
				(this.getEntrezId()==commonObject.getEntrezId())){
					return true;
				} else if ((this.getObjectName().equals("tfbs")) &&
						   (this.getTfbsorHistoneName().equals(commonObject.getTfbsorHistoneName())) &&
						   (this.getCellLineName().equals(commonObject.getCellLineName())) &&
						   (this.getFileName().equals(commonObject.getFileName()))){
							return true;					
				}else if ((this.getObjectName().equals("histone")) &&
						   (this.getTfbsorHistoneName().equals(commonObject.getTfbsorHistoneName())) &&
						   (this.getCellLineName().equals(commonObject.getCellLineName())) &&
						   (this.getFileName().equals(commonObject.getFileName()))){					
							return true;
				}else if ((this.getObjectName().equals("dnase")) &&
						   (this.getCellLineName().equals(commonObject.getCellLineName())) &&
						   (this.getFileName().equals(commonObject.getFileName()))){
							return true;
					 
				}else
					return false;
		}else 
			return false;
			
		
		
	}
	
	@Override
	public int hashCode() {
		String s = 	this.objectName+this.chromName+this.low+ this.high;
		return s.hashCode();
	}
	
	
	
	
	

}

	