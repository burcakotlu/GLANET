/**
 * @author burcakotlu
 * @date Sep 18, 2014 
 * @time 3:21:52 PM
 */
package collaboration;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class ElementEnrichmentMap {

	Map<String, ElementEnrichment> elementEnrichmentMap;

	public Map<String, ElementEnrichment> getElementEnrichmentMap() {

		return elementEnrichmentMap;
	}

	public void setElementEnrichmentMap( Map<String, ElementEnrichment> elementEnrichmentMap) {

		this.elementEnrichmentMap = elementEnrichmentMap;
	}

	public ElementEnrichmentMap( Map<String, ElementEnrichment> elementEnrichmentMap) {

		super();
		this.elementEnrichmentMap = elementEnrichmentMap;
	}

	public ElementEnrichmentMap() {

		super();
		this.elementEnrichmentMap = new HashMap<String, ElementEnrichment>();
		// TODO Auto-generated constructor stub
	}

}
