/**
 * 
 */
package multipletesting;

import java.util.List;

import auxiliary.FunctionalElementMinimal;

/**
 * @author Burçak Otlu
 * @date May 6, 2015
 * @project Glanet 
 *
 */
public class BonferroniCorrection {
	
	public static void calculateBonferroniCorrectedPValue(List<FunctionalElementMinimal> elementList){
		
		FunctionalElementMinimal element = null;
		
		Float empiricalPValue;
		Float bonferroniCorrectedPValue;

		
		for(int i = 0; i< elementList.size(); i++){
			
			element = elementList.get(i);
			
			empiricalPValue = (element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() * 1.0f) / element.getNumberofPermutations();
			bonferroniCorrectedPValue = empiricalPValue * element.getNumberofComparisons();
			

			if (bonferroniCorrectedPValue > 1.0f) {
				bonferroniCorrectedPValue = 1.0f;
			}

			element.setEmpiricalPValue(empiricalPValue);
			element.setBonferroniCorrectedPValue(bonferroniCorrectedPValue);
			
		}//End of For each element
		
		
	}

}
