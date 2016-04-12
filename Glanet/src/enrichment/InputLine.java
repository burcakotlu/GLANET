/**
 * @author Burcak Otlu
 * Jul 22, 2013
 * 11:02:34 PM
 * 2013
 *
 * 
 */
package enrichment;

import intervaltree.Interval;
import enumtypes.ChromosomeName;

public class InputLine extends Interval {

	ChromosomeName chrName;

	public ChromosomeName getChrName() {

		return chrName;
	}

	public void setChrName( ChromosomeName chrName) {

		this.chrName = chrName;
	}

	public InputLine( ChromosomeName chrName, int low, int high) {

		super( low, high);
		this.chrName = chrName;

	}

}
