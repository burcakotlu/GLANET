/**
 * @author Burcak Otlu
 * Jul 22, 2013
 * 11:02:34 PM
 * 2013
 *
 * 
 */
package enrichment;

import enumtypes.ChromosomeName;

public class InputLine {
	
	ChromosomeName chrName;
	int low;
	int high;
	float gcContent;
	float mapability;
	int length;
		
	
	public ChromosomeName getChrName() {
		return chrName;
	}
	public void setChrName(ChromosomeName chrName) {
		this.chrName = chrName;
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
	
	
	public float getGcContent() {
		return gcContent;
	}
	public void setGcContent(float gcContent) {
		this.gcContent = gcContent;
	}
	
	
	
	public float getMapability() {
		return mapability;
	}
	
	public void setMapability(float mapability) {
		this.mapability = mapability;
	}
	
	public InputLine(ChromosomeName chrName, int low, int high) {
		super();
		this.chrName = chrName;
		this.low = low;
		this.high = high;

		this.length = high-low+1;
	}
	
	public InputLine(ChromosomeName chrName, int low, int high, float gcContent,
			float mapability) {
		super();
		this.chrName = chrName;
		this.low = low;
		this.high = high;
		this.gcContent = gcContent;
		this.mapability = mapability;
		
		this.length = high-low+1;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	
	

}
