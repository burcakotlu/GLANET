/**
 * 
 */
package auxiliary;

import java.text.NumberFormat;

/**
 * @author Burçak Otlu
 * @date Apr 21, 2015
 * @project Glanet 
 *
 */
public class GlanetPercentageFormat {
	
		
	public static NumberFormat getGlanetPercentageFormat() {

		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		defaultFormat.setMinimumFractionDigits(2);

		return defaultFormat;

	}

}
