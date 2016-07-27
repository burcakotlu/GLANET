/**
 * 
 */
package auxiliary;

/**
 * @author Burçak Otlu
 * @date Jul 27, 2016
 * @project Glanet 
 *
 */
public class GATElement {
	
	
	
	String cellLineNameElementName;
	Float empiricalPValue;
	
	
	

	public String getCellLineNameElementName() {
		return cellLineNameElementName;
	}

	public void setCellLineNameElementName(String cellLineNameElementName) {
		this.cellLineNameElementName = cellLineNameElementName;
	}

	public Float getEmpiricalPValue() {
		return empiricalPValue;
	}

	public void setEmpiricalPValue(Float empiricalPValue) {
		this.empiricalPValue = empiricalPValue;
	}

	/**
	 * 
	 */
	public GATElement(
		String cellLineNameElementName,
		Float empiricalPValue) {
		
		this.cellLineNameElementName = cellLineNameElementName;
		this.empiricalPValue = empiricalPValue;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
