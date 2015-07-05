/**
 * 
 */
package annotation;

import java.util.Comparator;

/**
 * @author Bur�ak Otlu
 * @date Feb 23, 2015
 * @project Glanet 
 *
 */
public class Element {

	int elementIntNumber;
	short elementShortNumber;
	Integer elementNumberofOverlaps;

	public int getElementIntNumber() {

		return elementIntNumber;
	}

	public void setElementIntNumber( int elementIntNumber) {

		this.elementIntNumber = elementIntNumber;
	}

	public short getElementShortNumber() {

		return elementShortNumber;
	}

	public void setElementShortNumber( short elementShortNumber) {

		this.elementShortNumber = elementShortNumber;
	}

	public Integer getElementNumberofOverlaps() {

		return elementNumberofOverlaps;
	}

	public void setElementNumberofOverlaps( int elementNumberofOverlaps) {

		this.elementNumberofOverlaps = elementNumberofOverlaps;
	}

	public Element( int elementIntNumber, int elementNumberofOverlaps) {

		super();
		this.elementIntNumber = elementIntNumber;
		this.elementNumberofOverlaps = elementNumberofOverlaps;
	}

	public Element( short elementShortNumber, int elementNumberofOverlaps) {

		super();
		this.elementShortNumber = elementShortNumber;
		this.elementNumberofOverlaps = elementNumberofOverlaps;
	}

	static Comparator<Element> NUMBER_OF_OVERLAPS = new Comparator<Element>() {

		public int compare( Element element1, Element element2) {

			return element2.getElementNumberofOverlaps().compareTo( element1.getElementNumberofOverlaps());
		}
	};

}
