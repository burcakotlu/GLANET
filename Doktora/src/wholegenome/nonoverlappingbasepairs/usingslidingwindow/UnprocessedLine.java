package wholegenome.nonoverlappingbasepairs.usingslidingwindow;

public class UnprocessedLine {
	
	int low;
	int high;
	String type;
	
	String name;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UnprocessedLine(int low, int high, String type) {
		super();
		this.low = low;
		this.high = high;
		this.type = type;
	}
	public UnprocessedLine(int low, int high, String type,
			String name) {
		super();
		this.low = low;
		this.high = high;
		this.type = type;
		this.name = name;
	}
	
	

}
