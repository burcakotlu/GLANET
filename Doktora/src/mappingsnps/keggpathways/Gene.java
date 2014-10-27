package mappingsnps.keggpathways;

import java.util.ArrayList;


public class Gene {

	ArrayList<SNP> snpList;
	String name;
	String id;
	
	
	public ArrayList<SNP> getSnpList() {
		return snpList;
	}
	public void setSnpList(ArrayList<SNP> snpList) {
		this.snpList = snpList;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Gene(String name, String id,ArrayList<SNP> snpList) {
		super();
		this.snpList = snpList;
		this.name = name;
		this.id = id;
	}
	
	public Gene(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public Gene(String name) {
		super();
		this.name = name;
	}
	
}
