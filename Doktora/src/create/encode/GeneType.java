package create.encode;

public class GeneType {

	int numberofGenesIdExits;
	int numberofGenesNameExits;
	int numberofGenesDoesNotExist;
	int numberofUnknownGenes;
	int numberofGenes;
	
	
	
	
	public int getNumberofGenesIdExits() {
		return numberofGenesIdExits;
	}
	public void setNumberofGenesIdExits(int numberofGenesIdExits) {
		this.numberofGenesIdExits = numberofGenesIdExits;
	}
	public int getNumberofGenesNameExits() {
		return numberofGenesNameExits;
	}
	public void setNumberofGenesNameExits(int numberofGenesNameExits) {
		this.numberofGenesNameExits = numberofGenesNameExits;
	}
	public int getNumberofGenesDoesNotExist() {
		return numberofGenesDoesNotExist;
	}
	public void setNumberofGenesDoesNotExist(int numberofGenesDoesNotExist) {
		this.numberofGenesDoesNotExist = numberofGenesDoesNotExist;
	}
	
	public int getNumberofUnknownGenes() {
		return numberofUnknownGenes;
	}
	public void setNumberofUnknownGenes(int numberofUnknownGenes) {
		this.numberofUnknownGenes = numberofUnknownGenes;
	}
	public int getNumberofGenes() {
		return numberofGenes;
	}
	public void setNumberofGenes(int numberofGenes) {
		this.numberofGenes = numberofGenes;
	}
	
	public GeneType() {
		super();
		this.numberofGenesIdExits = 0;
		this.numberofGenesNameExits = 0;
		this.numberofGenesDoesNotExist = 0;
		this.numberofUnknownGenes = 0;
		this.numberofGenes = 0;
	}
	
	
}
