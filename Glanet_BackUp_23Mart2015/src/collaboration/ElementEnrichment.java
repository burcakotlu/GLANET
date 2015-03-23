/**
 * @author burcakotlu
 * @date Sep 18, 2014 
 * @time 12:03:35 PM
 */
package collaboration;

/**
 * 
 */
public class ElementEnrichment {

	String elementName;
	boolean enriched_BH_FDR;
	boolean enriched_Bonferroni_Correction;
	Float empiricalPValue;
	Float BonferroniCorrectedPValue;
	Float BHFDRAdjustedPValue;

	public Float getEmpiricalPValue() {
		return empiricalPValue;
	}

	public void setEmpiricalPValue(Float empiricalPValue) {
		this.empiricalPValue = empiricalPValue;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public boolean isEnriched_BH_FDR() {
		return enriched_BH_FDR;
	}

	public void setEnriched_BH_FDR(boolean enriched_BH_FDR) {
		this.enriched_BH_FDR = enriched_BH_FDR;
	}

	public boolean isEnriched_Bonferroni_Correction() {
		return enriched_Bonferroni_Correction;
	}

	public void setEnriched_Bonferroni_Correction(boolean enriched_Bonferroni_Correction) {
		this.enriched_Bonferroni_Correction = enriched_Bonferroni_Correction;
	}

	public Float getBonferroniCorrectedPValue() {
		return BonferroniCorrectedPValue;
	}

	public void setBonferroniCorrectedPValue(Float bonferroniCorrectedPValue) {
		BonferroniCorrectedPValue = bonferroniCorrectedPValue;
	}

	public Float getBHFDRAdjustedPValue() {
		return BHFDRAdjustedPValue;
	}

	public void setBHFDRAdjustedPValue(Float bHFDRAdjustedPValue) {
		BHFDRAdjustedPValue = bHFDRAdjustedPValue;
	}

	public ElementEnrichment(String elementName, boolean enriched_Bonferroni_Correction, boolean enriched_BH_FDR, Float empiricalPValue, Float bonferroniCorrectedPValue, Float bHFDRAdjustedPValue) {
		super();
		this.elementName = elementName;
		this.enriched_Bonferroni_Correction = enriched_Bonferroni_Correction;
		this.enriched_BH_FDR = enriched_BH_FDR;
		this.empiricalPValue = empiricalPValue;
		this.BonferroniCorrectedPValue = bonferroniCorrectedPValue;
		this.BHFDRAdjustedPValue = bHFDRAdjustedPValue;
	}
}
