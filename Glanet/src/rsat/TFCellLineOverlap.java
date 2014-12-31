/**
 * 
 */
package rsat;

/**
 * @author Burçak Otlu
 * @date Dec 29, 2014
 * @project Glanet 
 *
 */
public class TFCellLineOverlap {
    
    String tfName;
    String cellLineName;
    String fileName;
    
    int oneBasedStart;
    int oneBasedEnd;
    
    
    
    public TFCellLineOverlap(String tfName, String cellLineName,
	    String fileName, int oneBasedStart, int oneBasedEnd) {
	super();
	this.tfName = tfName;
	this.cellLineName = cellLineName;
	this.fileName = fileName;
	this.oneBasedStart = oneBasedStart;
	this.oneBasedEnd = oneBasedEnd;
    }
    
    
    

}
