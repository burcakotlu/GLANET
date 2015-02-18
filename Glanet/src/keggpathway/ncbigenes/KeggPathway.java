/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package keggpathway.ncbigenes;

import java.util.List;

public class KeggPathway {
	String keggPathwayName;

	List<String> geneIdList;

	public String getKeggPathwayName() {
		return keggPathwayName;
	}

	public void setKeggPathwayName(String keggPathwayName) {
		this.keggPathwayName = keggPathwayName;
	}

	public List<String> getGeneIdList() {
		return geneIdList;
	}

	public void setGeneIdList(List<String> geneIdList) {
		this.geneIdList = geneIdList;
	}

	public KeggPathway(String keggPathwayName, List<String> geneIdList) {
		super();
		this.keggPathwayName = keggPathwayName;
		this.geneIdList = geneIdList;
	}

	public KeggPathway(String keggPathwayName) {
		super();
		this.keggPathwayName = keggPathwayName;
	}

}
