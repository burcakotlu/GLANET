/**
 * @author Burcak Otlu
 * Sep 2, 2013
 * 3:42:02 PM
 * 2013
 *
 * 
 */
package create.ucscgenome;

public class RefSeqGeneIntervalsInformation {
	
	long numberofRefSeqGeneIntervals;
	
	long numberofExons;
	long numberofIntrons;
	
	long numberof5p1s;
	long numberof5p2s;
	long numberof5ds;
	
	long numberof3p1s;
	long numberof3p2s;
	long numberof3ds;
	
	

	public long getNumberofExons() {
		return numberofExons;
	}

	public void setNumberofExons(long numberofExons) {
		this.numberofExons = numberofExons;
	}

	public long getNumberofIntrons() {
		return numberofIntrons;
	}

	public void setNumberofIntrons(long numberofIntrons) {
		this.numberofIntrons = numberofIntrons;
	}

	public long getNumberof5p1s() {
		return numberof5p1s;
	}

	public void setNumberof5p1s(long numberof5p1s) {
		this.numberof5p1s = numberof5p1s;
	}

	public long getNumberof5p2s() {
		return numberof5p2s;
	}

	public void setNumberof5p2s(long numberof5p2s) {
		this.numberof5p2s = numberof5p2s;
	}

	public long getNumberof5ds() {
		return numberof5ds;
	}

	public void setNumberof5ds(long numberof5ds) {
		this.numberof5ds = numberof5ds;
	}

	public long getNumberof3p1s() {
		return numberof3p1s;
	}

	public void setNumberof3p1s(long numberof3p1s) {
		this.numberof3p1s = numberof3p1s;
	}

	public long getNumberof3p2s() {
		return numberof3p2s;
	}

	public void setNumberof3p2s(long numberof3p2s) {
		this.numberof3p2s = numberof3p2s;
	}

	public long getNumberof3ds() {
		return numberof3ds;
	}

	public void setNumberof3ds(long numberof3ds) {
		this.numberof3ds = numberof3ds;
	}

	public long getNumberofRefSeqGeneIntervals() {
		return numberofRefSeqGeneIntervals;
	}

	public void setNumberofRefSeqGeneIntervals(long numberofRefSeqGeneIntervals) {
		this.numberofRefSeqGeneIntervals = numberofRefSeqGeneIntervals;
	}

	public RefSeqGeneIntervalsInformation() {
		super();
		this.numberofRefSeqGeneIntervals = 0;
		
		this.numberofExons = 0;
		this.numberofIntrons = 0;
		
		this.numberof5p1s = 0;
		this.numberof5p2s = 0;
		this.numberof5ds = 0;
		
		this.numberof3p1s = 0;
		this.numberof3p2s = 0;
		this.numberof3ds = 0;
		
	}
	
	

	

}
