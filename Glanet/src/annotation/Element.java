/**
 * 
 */
package annotation;

import java.util.Comparator;

/**
 * @author Burçak Otlu
 * @date Feb 23, 2015
 * @project Glanet 
 *
 */
public class Element {
	
	int elementNumber;
	Integer elementNumberofOverlaps;
	
	
	
	public int getElementNumber() {
		return elementNumber;
	}
	
	public void setElementNumber(int elementNumber) {
		this.elementNumber = elementNumber;
	}
	
	public Integer getElementNumberofOverlaps() {
		return elementNumberofOverlaps;
	}
	
	public void setElementNumberofOverlaps(int elementNumberofOverlaps) {
		this.elementNumberofOverlaps = elementNumberofOverlaps;
	}
	
	

	public Element(int elementNumber, int elementNumberofOverlaps) {
		super();
		this.elementNumber = elementNumber;
		this.elementNumberofOverlaps = elementNumberofOverlaps;
	}
	
	
	static Comparator<Element> NUMBER_OF_OVERLAPS = new Comparator<Element>() {
		public int compare(Element element1, Element element2) {
			return element2.getElementNumberofOverlaps().compareTo(element1.getElementNumberofOverlaps());
		}
	};

	
	

}
