/**
 * 
 */
package multipletesting;

import java.util.List;
import org.apache.log4j.Logger;
import auxiliary.FunctionalElementMinimal;
import ui.GlanetRunner;

/**
 * @author Bur�ak Otlu
 * @date May 6, 2015
 * @project Glanet 
 *
 */
public class BonferroniCorrection {

	final static Logger logger = Logger.getLogger(BonferroniCorrection.class);

	// 22 May 2015
	// zScore version
	public static void calculateBonferroniCorrectedPValueCalculatedFromZScore(
			List<FunctionalElementMinimal> elementList) {

		FunctionalElementMinimal element = null;

		double empiricalPValueCalculatedFromZScore;
		double bonferroniCorrectedPValueCalculatedFromZScore;

		for( int i = 0; i < elementList.size(); i++){

			element = elementList.get( i);

			if( element.getEmpiricalPValueCalculatedFromZScore() != null){

				empiricalPValueCalculatedFromZScore = element.getEmpiricalPValueCalculatedFromZScore();

				bonferroniCorrectedPValueCalculatedFromZScore = empiricalPValueCalculatedFromZScore * element.getNumberofComparisons();

				if( bonferroniCorrectedPValueCalculatedFromZScore > 1.0f){
					bonferroniCorrectedPValueCalculatedFromZScore = 1.0f;
				}

				element.setBonferroniCorrectedPValueCalculatedFromZScore( bonferroniCorrectedPValueCalculatedFromZScore);
			}else{
				element.setBonferroniCorrectedPValueCalculatedFromZScore( null);
			}

		}// End of For each element

	}

	public static void calculateBonferroniCorrectedPValue( List<FunctionalElementMinimal> elementList) {

		FunctionalElementMinimal element = null;

		Float empiricalPValue;
		Float bonferroniCorrectedPValue;

		for( int i = 0; i < elementList.size(); i++){

			element = elementList.get( i);

			// For control purposes starts
			empiricalPValue = ( element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualtoOriginalNumberofOverlaps() * 1.0f) / element.getNumberofPermutations();

			if( !element.getEmpiricalPValue().equals( empiricalPValue)){
				if( GlanetRunner.shouldLog())logger.error( "There is a situation: EmpiricalPValues are not equal.");
			}
			// For control purposes ends

			bonferroniCorrectedPValue = empiricalPValue * element.getNumberofComparisons();

			if( bonferroniCorrectedPValue > 1.0f){
				bonferroniCorrectedPValue = 1.0f;
			}

			element.setBonferroniCorrectedPValue( bonferroniCorrectedPValue);

		}// End of For each element

	}

}
