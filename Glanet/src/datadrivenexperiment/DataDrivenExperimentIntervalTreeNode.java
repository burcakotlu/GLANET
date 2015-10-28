/**
 * 
 */
package datadrivenexperiment;

import enumtypes.ChromosomeName;
import enumtypes.NodeType;
import intervaltree.IntervalTreeNode;

/**
 * @author Burçak Otlu
 * @date Oct 27, 2015
 * @project Glanet 
 *
 */
public class DataDrivenExperimentIntervalTreeNode extends IntervalTreeNode {
	
	String geneSymbol;
	Float tpm;
	
	
	public DataDrivenExperimentIntervalTreeNode(
			ChromosomeName chromName, 
			int low, 
			int high,
			String geneSymbol, 
			Float tpm) {
		
		super(chromName,low,high);
		
		this.geneSymbol = geneSymbol;
		this.tpm = tpm;
	}
	
	public DataDrivenExperimentIntervalTreeNode(
			ChromosomeName chromName, 
			int low, 
			int high,
			String geneSymbol, 
			Float tpm,
			NodeType nodeType) {
		
		super(chromName,low,high,nodeType);
		
		this.geneSymbol = geneSymbol;
		this.tpm = tpm;
	}
	
	
	public String getGeneSymbol() {
		return geneSymbol;
	}
	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}
	public Float getTpm() {
		return tpm;
	}
	public void setTpm(Float tpm) {
		this.tpm = tpm;
	}
	

	

}
