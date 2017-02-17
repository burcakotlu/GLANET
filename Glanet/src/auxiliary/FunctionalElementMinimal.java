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
public class FunctionalElementMinimal implements Comparable<FunctionalElementMinimal> {

	String name;

	int originalNumberofOverlaps;
	int numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps;
	int numberofPermutations;

	Float empiricalPValue;
	Float bonferroniCorrectedPValue;
	Float BHFDRAdjustedPValue;
	boolean rejectNullHypothesis;

	// 22 May 2015
	Double mean;
	Double stdDev;
	Double ZScore;
	Double empiricalPValueCalculatedFromZScore;
	Double bonferroniCorrectedPValueCalculatedFromZScore;
	Double BHFDRAdjustedPValueCalculatedFromZScore;
	Boolean rejectNullHypothesisCalculatedFromZScore;
	
	int numberofComparisons;
	
	

	public int getNumberofComparisons() {
		return numberofComparisons;
	}

	public void setNumberofComparisons(int numberofComparisons) {
		this.numberofComparisons = numberofComparisons;
	}

	public Double getMean() {

		return mean;
	}

	public void setMean( Double mean) {

		this.mean = mean;
	}

	public Double getStdDev() {

		return stdDev;
	}

	public void setStdDev( Double stdDev) {

		this.stdDev = stdDev;
	}

	public Double getZScore() {

		return ZScore;
	}

	public void setZScore( Double zScore) {

		ZScore = zScore;
	}

	public Double getEmpiricalPValueCalculatedFromZScore() {

		return empiricalPValueCalculatedFromZScore;
	}

	public void setEmpiricalPValueCalculatedFromZScore( Double empiricalPValueCalculatedFromZScore) {

		this.empiricalPValueCalculatedFromZScore = empiricalPValueCalculatedFromZScore;
	}

	public Double getBonferroniCorrectedPValueCalculatedFromZScore() {

		return bonferroniCorrectedPValueCalculatedFromZScore;
	}

	public void setBonferroniCorrectedPValueCalculatedFromZScore( Double bonferroniCorrectedPValueCalculatedFromZScore) {

		this.bonferroniCorrectedPValueCalculatedFromZScore = bonferroniCorrectedPValueCalculatedFromZScore;
	}

	public Double getBHFDRAdjustedPValueCalculatedFromZScore() {

		return BHFDRAdjustedPValueCalculatedFromZScore;
	}

	public void setBHFDRAdjustedPValueCalculatedFromZScore( Double bHFDRAdjustedPValueCalculatedFromZScore) {

		BHFDRAdjustedPValueCalculatedFromZScore = bHFDRAdjustedPValueCalculatedFromZScore;
	}

	public Boolean getRejectNullHypothesisCalculatedFromZScore() {

		return rejectNullHypothesisCalculatedFromZScore;
	}

	public void setRejectNullHypothesisCalculatedFromZScore( Boolean rejectNullHypothesisCalculatedFromZScore) {

		this.rejectNullHypothesisCalculatedFromZScore = rejectNullHypothesisCalculatedFromZScore;
	}

	public String getName() {

		return name;
	}

	public void setName( String name) {

		this.name = name;
	}

	public int getOriginalNumberofOverlaps() {

		return originalNumberofOverlaps;
	}

	public void setOriginalNumberofOverlaps( int originalNumberofOverlaps) {

		this.originalNumberofOverlaps = originalNumberofOverlaps;
	}

	public Integer getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() {

		return numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps;
	}

	public void setNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps(
			int numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps) {

		this.numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps = numberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps;
	}

	public int getNumberofPermutations() {

		return numberofPermutations;
	}

	public void setNumberofPermutations( int numberofPermutations) {

		this.numberofPermutations = numberofPermutations;
	}



	public Float getEmpiricalPValue() {

		return empiricalPValue;
	}

	public void setEmpiricalPValue( Float empiricalPValue) {

		this.empiricalPValue = empiricalPValue;
	}

	public Float getBonferroniCorrectedPValue() {

		return bonferroniCorrectedPValue;
	}

	public void setBonferroniCorrectedPValue( Float bonferroniCorrectedPValue) {

		this.bonferroniCorrectedPValue = bonferroniCorrectedPValue;
	}

	public Float getBHFDRAdjustedPValue() {

		return BHFDRAdjustedPValue;
	}

	public void setBHFDRAdjustedPValue( Float bHFDRAdjustedPValue) {

		BHFDRAdjustedPValue = bHFDRAdjustedPValue;
	}

	public boolean isRejectNullHypothesis() {

		return rejectNullHypothesis;
	}

	public void setRejectNullHypothesis( boolean rejectNullHypothesis) {

		this.rejectNullHypothesis = rejectNullHypothesis;
	}

	public static Comparator<FunctionalElementMinimal> EMPIRICAL_P_VALUE = new Comparator<FunctionalElementMinimal>() {

		public int compare( FunctionalElementMinimal element1, FunctionalElementMinimal element2) {

			return element1.getEmpiricalPValue().compareTo( element2.getEmpiricalPValue());

		}
	};

	public static Comparator<FunctionalElementMinimal> BONFERRONI_CORRECTED_P_VALUE = new Comparator<FunctionalElementMinimal>() {

		public int compare( FunctionalElementMinimal element1, FunctionalElementMinimal element2) {

			return element1.getBonferroniCorrectedPValue().compareTo( element2.getBonferroniCorrectedPValue());

		}
	};

	public static Comparator<FunctionalElementMinimal> BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE = new Comparator<FunctionalElementMinimal>() {

		public int compare( FunctionalElementMinimal element1, FunctionalElementMinimal element2) {

			return element1.getBHFDRAdjustedPValue().compareTo( element2.getBHFDRAdjustedPValue());

		}
	};

	@Override
	public int compareTo( FunctionalElementMinimal o) {

		// TODO Auto-generated method stub
		return 0;
	}

	// In Descending Order
	public static Comparator<FunctionalElementMinimal> Z_SCORE = new Comparator<FunctionalElementMinimal>() {

		public int compare( FunctionalElementMinimal element1, FunctionalElementMinimal element2) {

			// dummy initialization
			int c = 0;

			// element1 Null element2 Null
			if( element1.getZScore() == null && element2.getZScore() == null){

				c = 0;

			}
			// element1 notNull element2 notNull
			else if( element1.getZScore() != null && element2.getZScore() != null){
				// zScore In Descending Order
				c = element2.getZScore().compareTo( element1.getZScore());
			}

			// element1 notNull element2 Null
			else if( element1.getZScore() != null){
				c = -1;
			}

			// element2 notNull element1 Null
			else if( element2.getZScore() != null){
				c = 1;
			}

			return c;

		}

	};

	// //NumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps In Ascending Order
	// //zScore In Descending Order
	// public static Comparator<FunctionalElementMinimal>
	// NUMBER_OF_PERMUTATIONS_HAVING_OVERLAPS_GREATER_THAN_EQUAL_TO_ORIGINAL_NUMBER_OF_OVERLAPS = new
	// Comparator<FunctionalElementMinimal>() {
	// public int compare(FunctionalElementMinimal element1, FunctionalElementMinimal element2) {
	// int c;
	//
	// //NumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps In Ascending Order
	// c =
	// element1.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps().compareTo(element2.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps());
	//
	// if (c==0){
	// //zScore In Descending Order
	// c = element2.getZScore().compareTo(element1.getZScore());
	// }
	// return c;
	// }
	// };
	//

	// In Ascending Order
	public static Comparator<FunctionalElementMinimal> BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE_CALCULATED_FROM_Z_SCORE = new Comparator<FunctionalElementMinimal>() {

		public int compare( FunctionalElementMinimal element1, FunctionalElementMinimal element2) {

			// dummy initialization
			int c = 0;

			if( element1.getBHFDRAdjustedPValueCalculatedFromZScore() == null && element2.getBHFDRAdjustedPValueCalculatedFromZScore() == null){

				c = 0;

			}else if( element1.getBHFDRAdjustedPValueCalculatedFromZScore() != null && element2.getBHFDRAdjustedPValueCalculatedFromZScore() != null){

				c = element1.getBHFDRAdjustedPValueCalculatedFromZScore().compareTo(
						element2.getBHFDRAdjustedPValueCalculatedFromZScore());

			}else if( element1.getBHFDRAdjustedPValueCalculatedFromZScore() != null){

				c = 1;

			}else if( element2.getBHFDRAdjustedPValueCalculatedFromZScore() != null){

				c = -1;
			}

			return c;

		}
	};

	// In Ascending Order
	public static Comparator<FunctionalElementMinimal> EMPIRICAL_P_VALUE_CALCULATED_FROM_Z_SCORE = new Comparator<FunctionalElementMinimal>() {

		public int compare( FunctionalElementMinimal element1, FunctionalElementMinimal element2) {

			// dummy initialization
			int c = 0;

			if( element1.getEmpiricalPValueCalculatedFromZScore() == null && element2.getEmpiricalPValueCalculatedFromZScore() == null){

				c = 0;

			}else if( element1.getEmpiricalPValueCalculatedFromZScore() != null && element2.getEmpiricalPValueCalculatedFromZScore() != null){

				c = element1.getEmpiricalPValueCalculatedFromZScore().compareTo(
						element2.getEmpiricalPValueCalculatedFromZScore());

			}else if( element1.getEmpiricalPValueCalculatedFromZScore() != null){

				c = 1;

			}else if( element2.getEmpiricalPValueCalculatedFromZScore() != null){

				c = -1;

			}

			return c;

		}
	};

}
