/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package intervaltree;



public class IntervalTreeNode{
	
	int max;
	int low;
	int high;
	
	
	//Added 7 March 2014
	String rsId;
	List<String> observedVariationAlleles;
	
	
	//added 11 December 2013
	int min;
	
	//added 12 December 2013
	int height;
	
	char color;
	IntervalTreeNode left;
	IntervalTreeNode right;
	IntervalTreeNode parent;
	
//	Just for search UCSC RefSeq Gene output
	char strand;
	String chromName;
	String  refSeqGeneName;
	Integer geneEntrezId;
	String intervalName;
	String geneHugoSymbol;
	
	
//	Just for search ENCODE Tfbs  and Histone output
	String tfbsorHistoneName;
	String cellLineName;
	String fileName;
	
	//Sentinel Node
	String name;
	
	//Node type whether it is original or merged node
	String nodeType;
	
	
	int numberofBases;
	
	//Mapability
	float mapability;
			
		
	public String getRsId() {
		return rsId;
	}

	public void setRsId(String rsId) {
		this.rsId = rsId;
	}

	public List<String> getObservedVariationAlleles() {
		return observedVariationAlleles;
	}

	public void setObservedVariationAlleles(List<String> observedVariationAlleles) {
		this.observedVariationAlleles = observedVariationAlleles;
	}

	public float getMapability() {
		return mapability;
	}

	public void setMapability(float mapability) {
		this.mapability = mapability;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public int getNumberofBases() {
		if (this.isNotSentinel()){
			return (this.getHigh()-this.getLow()+1);
		}else{
			return 0;
		}
			
		
	}

	public void setNumberofBases(int numberofBases) {
		this.numberofBases = numberofBases;
	}

	
	
	

	public boolean isSentinel(){
		if (Commons.SENTINEL.equals(this.getName())){
			return true;
		}else 
			return false;
		
	}
	
	public boolean isNotSentinel(){
		if (Commons.NOT_SENTINEL.equals(this.getName())){
			return true;
		}else 
			return false;
		
	}
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public char getStrand() {
		return strand;
	}


	public void setStrand(char strand) {
		this.strand = strand;
	}


	public Integer getGeneEntrezId() {
		return geneEntrezId;
	}
	
	
	public void setGeneEntrezId(Integer geneEntrezId) {
		this.geneEntrezId = geneEntrezId;
	}
	
	
	
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	
	
	
	
	
	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
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
	public char getColor() {
		return color;
	}
	public void setColor(char color) {
		this.color = color;
	}
	public IntervalTreeNode getLeft() {
		return left;
	}
	public void setLeft(IntervalTreeNode left) {
		this.left = left;
	}
	public IntervalTreeNode getRight() {
		return right;
	}
	public void setRight(IntervalTreeNode right) {
		this.right = right;
	}
	public IntervalTreeNode getParent() {
		return parent;
	}
	public void setParent(IntervalTreeNode parent) {
		this.parent = parent;
	}
	
	
	
	
	public String getChromName() {
		return chromName;
	}


	public void setChromName(String chromName) {
		this.chromName = chromName;
	}


	public String getRefSeqGeneName() {
		return refSeqGeneName;
	}


	public void setRefSeqGeneName(String refSeqGeneName) {
		this.refSeqGeneName = refSeqGeneName;
	}


	public String getIntervalName() {
		return intervalName;
	}


	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}


	public String getGeneHugoSymbol() {
		return geneHugoSymbol;
	}


	public void setGeneHugoSymbol(String geneHugoSymbol) {
		this.geneHugoSymbol = geneHugoSymbol;
	}


	public String getTfbsorHistoneName() {
		return tfbsorHistoneName;
	}


	public void setTfbsorHistoneName(String tfbsorHistoneName) {
		this.tfbsorHistoneName = tfbsorHistoneName;
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

	
	//SENTINEL node
	public IntervalTreeNode(){
		this.color = Commons.BLACK;			
		this.name = Commons.SENTINEL;
		this.numberofBases = 0;
		this.height= -1;
		
	}
	
	//testing interval tree operations
	public IntervalTreeNode(int low, int high) {
		super();
		this.low = low;
		this.high = high;
		
		this.left = new IntervalTreeNode();
		this.right = new IntervalTreeNode();
		this.parent = new IntervalTreeNode();
		this.name = Commons.NOT_SENTINEL;
		this.numberofBases = high-low+1;
					

	}
	
	//CalculateMapability
	public IntervalTreeNode(String chromName, int low, int high, float mapability){
		super();
		this.chromName = chromName;
		this.low = low;
		this.high = high;
		
		this.left = new IntervalTreeNode();
		this.right = new IntervalTreeNode();
		this.parent = new IntervalTreeNode();
		this.name = Commons.NOT_SENTINEL;
		this.numberofBases = high-low+1;
		
		this.mapability = mapability;
		
	}
	
	//process input data
	//remove overlaps
	public IntervalTreeNode(String chromName, int low, int high) {
		super();
		this.chromName = chromName;
		this.low = low;
		this.high = high;
		
		this.left = new IntervalTreeNode();
		this.right = new IntervalTreeNode();
		this.parent = new IntervalTreeNode();
		this.name = Commons.NOT_SENTINEL;
		this.numberofBases = high-low+1;
					

	}
	
	//For Exon Based Kegg Pathway Enrcihment Analysis Ucsc gene
		public IntervalTreeNode(String chromName, int low, int high, 
				Integer geneEntrezId, String intervalName,String nodeType) {
			super();
			this.low = low;
			this.high = high;
			this.chromName = chromName;
			this.geneEntrezId = geneEntrezId;
			this.intervalName = intervalName;
			
			this.left = new IntervalTreeNode();
			this.right = new IntervalTreeNode();
			this.parent = new IntervalTreeNode();
			this.name = Commons.NOT_SENTINEL;
			
			this.nodeType = nodeType;
			this.numberofBases = high-low+1;
			
			
		}

	//For Ucsc gene without strand attribute
	public IntervalTreeNode(String chromName, int low, int high, 
			String refSeqGeneName, Integer geneEntrezId, String intervalName,
			String geneHugoSymbol,String nodeType) {
		super();
		this.low = low;
		this.high = high;
		this.chromName = chromName;
		this.refSeqGeneName = refSeqGeneName;
		this.geneEntrezId = geneEntrezId;
		this.intervalName = intervalName;
		this.geneHugoSymbol = geneHugoSymbol;
		
		this.left = new IntervalTreeNode();
		this.right = new IntervalTreeNode();
		this.parent = new IntervalTreeNode();
		this.name = Commons.NOT_SENTINEL;
		this.nodeType = nodeType;
		this.numberofBases = high-low+1;
		
	}

	
	//For Ucsc gene with strand attribute
	public IntervalTreeNode(String chromName, int low, int high, 
			String refSeqGeneName, Integer geneEntrezId, String intervalName,char strand,
			String geneHugoSymbol,String nodeType) {
		super();
		this.low = low;
		this.high = high;
		this.chromName = chromName;
		this.refSeqGeneName = refSeqGeneName;
		this.geneEntrezId = geneEntrezId;
		this.intervalName = intervalName;
		this.strand = strand;
		this.geneHugoSymbol = geneHugoSymbol;
		
		this.left = new IntervalTreeNode();
		this.right = new IntervalTreeNode();
		this.parent = new IntervalTreeNode();
		this.name = Commons.NOT_SENTINEL;
		this.nodeType = nodeType;
		this.numberofBases = high-low+1;
		
	}
	

	//For Encode tfbs and histone
	public IntervalTreeNode(String chromName,int low, int high,  String tfbsorHistoneName,
			String cellLineName, String fileName,String nodeType) {
			super();
			this.low = low;
			this.high = high;
			this.chromName = chromName;
			this.tfbsorHistoneName= tfbsorHistoneName;
			this.cellLineName = cellLineName;
			this.fileName = fileName;
			
			this.left = new IntervalTreeNode();
			this.right = new IntervalTreeNode();
			this.parent = new IntervalTreeNode();
			this.name = Commons.NOT_SENTINEL;
			
			this.nodeType = nodeType;
			
			this.numberofBases = high-low+1;
			
	}
	
	
	
	//For Encode dnase
	public IntervalTreeNode(String chromName,int low, int high,String cellLineName, String fileName,String nodeType) {
		super();
		this.low = low;
		this.high = high;
		this.chromName = chromName;
		this.cellLineName = cellLineName;
		this.fileName = fileName;
		
		this.left = new IntervalTreeNode();
		this.right = new IntervalTreeNode();
		this.parent = new IntervalTreeNode();
		this.name = Commons.NOT_SENTINEL;
		
		this.nodeType = nodeType;
		
		this.numberofBases = high-low+1;
	}	

	//7 March 2014
	//For dbSNP node
	public IntervalTreeNode(String rsId, String chrNumber, int  chrPosition, List<String> observedVariationAlleles) {
		super();
		
		//no conversion here
		//since it is done in the caller class
		this.low = chrPosition;
		this.high = chrPosition;
		
		this.chromName = chrNumber;
		
		this.rsId = rsId;
		this.observedVariationAlleles = observedVariationAlleles;
		
		this.name = Commons.NOT_SENTINEL;
		
		
		
		
	}

}
