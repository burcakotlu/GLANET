/**
 * 
 */
package rsat;

import java.util.ArrayList;
import java.util.List;

import enumtypes.ChromosomeName;

/**
 * @author Burçak Otlu
 * @date Dec 29, 2014
 * @project Glanet 
 *
 */
public class TFOverlap {
    
    String tfName;
    String peakSequence;
    
    ChromosomeName chrNameWithPreceedingChr;
    int minimumOneBasedStart;
    int maximumOneBasedEnd;
    
    List<TFCellLineOverlap> tfCellLineOverlaps;
    
    
    
  
	
	public ChromosomeName getChrNameWithPreceedingChr() {
		return chrNameWithPreceedingChr;
	}

	public void setChrNameWithPreceedingChr(ChromosomeName chrNameWithPreceedingChr) {
		this.chrNameWithPreceedingChr = chrNameWithPreceedingChr;
	}

	public String getTfName() {
        return tfName;
    }
	
    public void setTfName(String tfName) {
        this.tfName = tfName;
    }
    public String getPeakSequence() {
        return peakSequence;
    }
    
    public void setPeakSequence(String peakSequence) {
        this.peakSequence = peakSequence;
    }
    public List<TFCellLineOverlap> getTfCellLineOverlaps() {
        return tfCellLineOverlaps;
    }
    public void setTfCellLineOverlaps(List<TFCellLineOverlap> tfCellLineOverlaps) {
        this.tfCellLineOverlaps = tfCellLineOverlaps;
    }
    public int getMinimumOneBasedStart() {
        return minimumOneBasedStart;
    }
    public void setMinimumOneBasedStart(int minimumOneBasedStart) {
        this.minimumOneBasedStart = minimumOneBasedStart;
    }
    public int getMaximumOneBasedEnd() {
        return maximumOneBasedEnd;
    }
    public void setMaximumOneBasedEnd(int maximumOneBasedEnd) {
        this.maximumOneBasedEnd = maximumOneBasedEnd;
    }
    public TFOverlap(String tfName, ChromosomeName chrNameWithPreceedingChr) {
    	
    	super();
    	this.tfName = tfName;
    	this.chrNameWithPreceedingChr = chrNameWithPreceedingChr;
    	this.tfCellLineOverlaps = new ArrayList<TFCellLineOverlap>();
    	
    	this.minimumOneBasedStart = Integer.MAX_VALUE;
    	this.maximumOneBasedEnd = Integer.MIN_VALUE;
    }
    
    

}
