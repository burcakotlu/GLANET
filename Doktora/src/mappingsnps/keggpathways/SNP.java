package mappingsnps.keggpathways;


public class SNP {
	String rsID;
	long id;
	String geneID;
	

	
	public String getRsID() {
		return rsID;
	}
	public void setRsID(String rsID) {
		this.rsID = rsID;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGeneID() {
		return geneID;
	}
	public void setGeneID(String geneID) {
		this.geneID = geneID;
	}
	
	
	public SNP(String rsID, long id) {
		super();
		this.rsID = rsID;
		this.id = id;
	}

	public SNP(String rsID) {
		super();
		this.rsID = rsID;
	}

	
	
}
