package kegg;

import java.util.List;

 public class Entry {
	 
	private int id;
	private String name;
	private String type;
	private String link;
	private String reaction;
	private List entryComponentList;
	private List entryGraphicsList;
	

	



	public Entry(int id, String name, String type, String link,
			String reaction, List entryComponentList, List entryGraphicsList) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.link = link;
		this.reaction = reaction;
		this.entryComponentList = entryComponentList;
		this.entryGraphicsList = entryGraphicsList;
	}


	public Entry(int id, String name, String type, String link,
			String reaction) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.link = link;
		this.reaction = reaction;
		
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getReaction() {
		return reaction;
	}
	public void setReaction(String reaction) {
		this.reaction = reaction;
	}

	public List getEntryComponentList() {
		return entryComponentList;
	}

	public void setEntryComponentList(List entryComponentList) {
		this.entryComponentList = entryComponentList;
	}


	public List getEntryGraphicsList() {
		return entryGraphicsList;
	}


	public void setEntryGraphicsList(List entryGraphicsList) {
		this.entryGraphicsList = entryGraphicsList;
	}

	
	
	
}
