/**
 * 
 */
package rsat;

import java.util.List;

/**
 * @author Burçak Otlu
 * @date Dec 29, 2014
 * @project Glanet 
 *
 */
public class TFOverlap {
    
    String tfName;
    String peakSequence;
    
    List<TFCellLineOverlap> tfCellLineOverlaps;
    
    int minimumOneBasedStart;
    int maximumOneBasedEnd;
    
    
    
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
    public TFOverlap(String tfName) {
	super();
	this.tfName = tfName;
    }
    
    

}
