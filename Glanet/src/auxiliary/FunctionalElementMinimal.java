/**
 * 
 */
package auxiliary;

import java.util.Comparator;

/**
 * @author Burçak Otlu
 * @date May 6, 2015
 * @project Glanet 
 *
 */
public class FunctionalElementMinimal  implements Comparable<FunctionalElementMinimal>{
	
	String name;
	
	int originalNumberofOverlaps;
	int numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps;
	int numberofPermutations;
	int numberofComparisons;
	
	Float empiricalPValue;
	Float bonferroniCorrectedPValue;
	Float BHFDRAdjustedPValue;
	boolean rejectNullHypothesis;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOriginalNumberofOverlaps() {
		return originalNumberofOverlaps;
	}
	public void setOriginalNumberofOverlaps(int originalNumberofOverlaps) {
		this.originalNumberofOverlaps = originalNumberofOverlaps;
	}
	public Integer getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() {
		return numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps;
	}
	public void setNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps(int numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps) {
		this.numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps;
	}
	public int getNumberofPermutations() {
		return numberofPermutations;
	}
	public void setNumberofPermutations(int numberofPermutations) {
		this.numberofPermutations = numberofPermutations;
	}
	public int getNumberofComparisons() {
		return numberofComparisons;
	}
	public void setNumberofComparisons(int numberofComparisons) {
		this.numberofComparisons = numberofComparisons;
	}
	public Float getEmpiricalPValue() {
		return empiricalPValue;
	}
	public void setEmpiricalPValue(Float empiricalPValue) {
		this.empiricalPValue = empiricalPValue;
	}
	public Float getBonferroniCorrectedPValue() {
		return bonferroniCorrectedPValue;
	}
	public void setBonferroniCorrectedPValue(Float bonferroniCorrectedPValue) {
		this.bonferroniCorrectedPValue = bonferroniCorrectedPValue;
	}
	public Float getBHFDRAdjustedPValue() {
		return BHFDRAdjustedPValue;
	}
	public void setBHFDRAdjustedPValue(Float bHFDRAdjustedPValue) {
		BHFDRAdjustedPValue = bHFDRAdjustedPValue;
	}
	public boolean isRejectNullHypothesis() {
		return rejectNullHypothesis;
	}
	public void setRejectNullHypothesis(boolean rejectNullHypothesis) {
		this.rejectNullHypothesis = rejectNullHypothesis;
	}
	
	
	public static Comparator<FunctionalElementMinimal> EMPIRICAL_P_VALUE = new Comparator<FunctionalElementMinimal>() {
		public int compare(FunctionalElementMinimal element1, FunctionalElementMinimal element2) {

			return element1.getEmpiricalPValue().compareTo(element2.getEmpiricalPValue());

			
		}
	};

	public static Comparator<FunctionalElementMinimal> BONFERRONI_CORRECTED_P_VALUE = new Comparator<FunctionalElementMinimal>() {
		public int compare(FunctionalElementMinimal element1, FunctionalElementMinimal element2) {

			return element1.getBonferroniCorrectedPValue().compareTo(element2.getBonferroniCorrectedPValue());

			
		}
	};

	public static Comparator<FunctionalElementMinimal> BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE = new Comparator<FunctionalElementMinimal>() {
		public int compare(FunctionalElementMinimal element1, FunctionalElementMinimal element2) {

			return element1.getBHFDRAdjustedPValue().compareTo(element2.getBHFDRAdjustedPValue());

		}
	};


	@Override
	public int compareTo(FunctionalElementMinimal o) {
		// TODO Auto-generated method stub
		return 0;
	}


	


}
