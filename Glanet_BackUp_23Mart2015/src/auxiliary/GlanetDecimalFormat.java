/**
 * 
 */
package auxiliary;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author Bur�ak Otlu
 * @date Jan 23, 2015
 * @project Glanet
 *
 */
public class GlanetDecimalFormat {

	public static DecimalFormat getGLANETDecimalFormat(String formatString) {

		Locale loc = Locale.getDefault();

		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(loc);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(',');
		DecimalFormat df = new DecimalFormat(formatString, otherSymbols);

		return df;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
