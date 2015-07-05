/**
 * @author Burcak Otlu
 * Feb 19, 2014
 * 9:20:22 AM
 * 2014
 *
 * 
 */
package multipletesting;

import java.util.Iterator;
import java.util.List;
import auxiliary.FunctionalElement;
import auxiliary.FunctionalElementMinimal;

public class BenjaminiandHochberg {

	/**
	 * 
	 */
	public BenjaminiandHochberg() {

		// TODO Auto-generated constructor stub
	}

	// ZScore Version
	public static void calculateBenjaminiHochbergFDRAdjustedPValueCalculatedFromZScore(
			List<FunctionalElementMinimal> list, float FDR) {

		FunctionalElementMinimal element;

		Iterator<FunctionalElementMinimal> itr = list.iterator();

		int j = 1;
		int m = list.size();
		Double BH_FDR_AdjustedPValue_CalculatedFromZScore;

		while( itr.hasNext()){

			element = itr.next();

			if( element.getEmpiricalPValueCalculatedFromZScore() != null){

				BH_FDR_AdjustedPValue_CalculatedFromZScore = ( element.getEmpiricalPValueCalculatedFromZScore() * m) / j;

				if( BH_FDR_AdjustedPValue_CalculatedFromZScore > 1.0){
					BH_FDR_AdjustedPValue_CalculatedFromZScore = 1.0;
				}

				element.setBHFDRAdjustedPValueCalculatedFromZScore( BH_FDR_AdjustedPValue_CalculatedFromZScore);

				if( element.getBHFDRAdjustedPValueCalculatedFromZScore() <= FDR){
					element.setRejectNullHypothesisCalculatedFromZScore( true);
				}else{
					element.setRejectNullHypothesisCalculatedFromZScore( false);
				}

			}else{

				element.setBHFDRAdjustedPValueCalculatedFromZScore( null);
				element.setRejectNullHypothesisCalculatedFromZScore( null);
			}

			j++;
		}// End of while

	}

	public static void calculateBenjaminiHochbergFDRAdjustedPValue( List<FunctionalElementMinimal> list, float FDR) {

		FunctionalElementMinimal element;

		Iterator<FunctionalElementMinimal> itr = list.iterator();

		int j = 1;
		int m = list.size();
		Float BH_FDR_AdjustedPValue;

		while( itr.hasNext()){

			element = itr.next();
			BH_FDR_AdjustedPValue = ( element.getEmpiricalPValue() * m) / j;

			if( BH_FDR_AdjustedPValue > 1f){
				BH_FDR_AdjustedPValue = 1f;
			}

			element.setBHFDRAdjustedPValue( BH_FDR_AdjustedPValue);

			if( element.getBHFDRAdjustedPValue() <= FDR){
				element.setRejectNullHypothesis( true);
			}else{
				element.setRejectNullHypothesis( false);
			}
			j++;
		}// End of while

	}

	public static void calculateBenjaminiHochbergFDRAdjustedPValuesFromZScores( List<FunctionalElement> list, float FDR) {

		FunctionalElement element;
		Iterator<FunctionalElement> itr = list.iterator();

		int j = 1;
		int m = list.size();
		double BH_FDR_AdjustedPValue_FromZScore;

		while( itr.hasNext()){

			element = itr.next();

			if( element.getEmpiricalPValueCalculatedFromZScore() != null){

				BH_FDR_AdjustedPValue_FromZScore = ( element.getEmpiricalPValueCalculatedFromZScore() * m) / j;

				if( BH_FDR_AdjustedPValue_FromZScore > 1){
					BH_FDR_AdjustedPValue_FromZScore = 1;
				}

				element.setBHFDRAdjustedPValueCalculatedFromZScore( BH_FDR_AdjustedPValue_FromZScore);

				if( element.getBHFDRAdjustedPValueCalculatedFromZScore() <= FDR){
					element.setRejectNullHypothesisCalculatedFromZScore( true);
				}else{
					element.setRejectNullHypothesisCalculatedFromZScore( false);
				}
			}else{

				element.setBHFDRAdjustedPValueCalculatedFromZScore( null);
				element.setRejectNullHypothesisCalculatedFromZScore( null);

			}

			// Increment element Count
			j++;

		}// End of while

	}

	public static void calculateBenjaminiHochbergFDRAdjustedPValues( List<FunctionalElement> list, float FDR) {

		FunctionalElement element;

		Iterator<FunctionalElement> itr = list.iterator();

		int j = 1;
		int m = list.size();
		Float BH_FDR_AdjustedPValue;

		while( itr.hasNext()){

			element = itr.next();
			BH_FDR_AdjustedPValue = ( element.getEmpiricalPValue() * m) / j;

			if( BH_FDR_AdjustedPValue > 1f){
				BH_FDR_AdjustedPValue = 1f;
			}

			element.setBHFDRAdjustedPValue( BH_FDR_AdjustedPValue);

			if( element.getBHFDRAdjustedPValue() <= FDR){
				element.setRejectNullHypothesis( true);
			}else{
				element.setRejectNullHypothesis( false);
			}
			j++;
		}

	}

	/**
	 * @param args
	 */
	public static void main( String[] args) {

		// TODO Auto-generated method stub

	}

}
