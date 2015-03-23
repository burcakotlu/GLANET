/**
 * 
 */
package annotation;

/**
 * @author Bur�ak Otlu
 * @date Feb 12, 2015
 * @project Glanet
 *
 */
public class NumberofGeneOverlaps {

	int numberofExonOverlaps;
	int numberofIntronOverlaps;

	int numberof5p1Overlaps;
	int numberof5p2Overlaps;
	int numberof5dOverlaps;

	int numberof3p1Overlaps;
	int numberof3p2Overlaps;
	int numberof3dOverlaps;

	int totalNumberofOverlaps;

	float exonPercentage;
	float intronPercentage;

	float fivep1Percentage;
	float fivep2Percentage;
	float fivedPercentage;

	float threep1Percentage;
	float threep2Percentage;
	float threedPercentage;

	// has to be 100%
	float allPercentage;

	public int getTotalNumberofOverlaps() {
		return totalNumberofOverlaps;
	}

	public void setTotalNumberofOverlaps(int totalNumberofOverlaps) {
		this.totalNumberofOverlaps = totalNumberofOverlaps;
	}

	public float getAllPercentage() {
		return allPercentage;
	}

	public void setAllPercentage(float allPercentage) {
		this.allPercentage = allPercentage;
	}

	public float getExonPercentage() {
		return exonPercentage;
	}

	public void setExonPercentage(float exonPercentage) {
		this.exonPercentage = exonPercentage;
	}

	public float getIntronPercentage() {
		return intronPercentage;
	}

	public void setIntronPercentage(float intronPercentage) {
		this.intronPercentage = intronPercentage;
	}

	public float getFivep1Percentage() {
		return fivep1Percentage;
	}

	public void setFivep1Percentage(float fivep1Percentage) {
		this.fivep1Percentage = fivep1Percentage;
	}

	public float getFivep2Percentage() {
		return fivep2Percentage;
	}

	public void setFivep2Percentage(float fivep2Percentage) {
		this.fivep2Percentage = fivep2Percentage;
	}

	public float getFivedPercentage() {
		return fivedPercentage;
	}

	public void setFivedPercentage(float fivedPercentage) {
		this.fivedPercentage = fivedPercentage;
	}

	public float getThreep1Percentage() {
		return threep1Percentage;
	}

	public void setThreep1Percentage(float threep1Percentage) {
		this.threep1Percentage = threep1Percentage;
	}

	public float getThreep2Percentage() {
		return threep2Percentage;
	}

	public void setThreep2Percentage(float threep2Percentage) {
		this.threep2Percentage = threep2Percentage;
	}

	public float getThreedPercentage() {
		return threedPercentage;
	}

	public void setThreedPercentage(float threedPercentage) {
		this.threedPercentage = threedPercentage;
	}

	public void calculate() {
		totalNumberofOverlaps = numberofExonOverlaps + numberofIntronOverlaps + numberof5p1Overlaps + numberof5p2Overlaps + numberof5dOverlaps + numberof3p1Overlaps + numberof3p2Overlaps + numberof3dOverlaps;

		exonPercentage = (float) ((numberofExonOverlaps * 100.0) / totalNumberofOverlaps);
		intronPercentage = (float) ((numberofIntronOverlaps * 100.0) / totalNumberofOverlaps);

		fivep1Percentage = (float) ((numberof5p1Overlaps * 100.0) / totalNumberofOverlaps);
		fivep2Percentage = (float) ((numberof5p2Overlaps * 100.0) / totalNumberofOverlaps);
		fivedPercentage = (float) ((numberof5dOverlaps * 100.0) / totalNumberofOverlaps);

		threep1Percentage = (float) ((numberof3p1Overlaps * 100.0) / totalNumberofOverlaps);
		threep2Percentage = (float) ((numberof3p2Overlaps * 100.0) / totalNumberofOverlaps);
		threedPercentage = (float) ((numberof3dOverlaps * 100.0) / totalNumberofOverlaps);

		allPercentage = exonPercentage + intronPercentage + fivep1Percentage + fivep2Percentage + fivedPercentage + threep1Percentage + threep2Percentage + threedPercentage;

	}

	public int getNumberofExonOverlaps() {
		return numberofExonOverlaps;
	}

	public void setNumberofExonOverlaps(int numberofExonOverlaps) {
		this.numberofExonOverlaps = numberofExonOverlaps;
	}

	public int getNumberofIntronOverlaps() {
		return numberofIntronOverlaps;
	}

	public void setNumberofIntronOverlaps(int numberofIntronOverlaps) {
		this.numberofIntronOverlaps = numberofIntronOverlaps;
	}

	public int getNumberof5p1Overlaps() {
		return numberof5p1Overlaps;
	}

	public void setNumberof5p1Overlaps(int numberof5p1Overlaps) {
		this.numberof5p1Overlaps = numberof5p1Overlaps;
	}

	public int getNumberof5p2Overlaps() {
		return numberof5p2Overlaps;
	}

	public void setNumberof5p2Overlaps(int numberof5p2Overlaps) {
		this.numberof5p2Overlaps = numberof5p2Overlaps;
	}

	public int getNumberof5dOverlaps() {
		return numberof5dOverlaps;
	}

	public void setNumberof5dOverlaps(int numberof5dOverlaps) {
		this.numberof5dOverlaps = numberof5dOverlaps;
	}

	public int getNumberof3p1Overlaps() {
		return numberof3p1Overlaps;
	}

	public void setNumberof3p1Overlaps(int numberof3p1Overlaps) {
		this.numberof3p1Overlaps = numberof3p1Overlaps;
	}

	public int getNumberof3p2Overlaps() {
		return numberof3p2Overlaps;
	}

	public void setNumberof3p2Overlaps(int numberof3p2Overlaps) {
		this.numberof3p2Overlaps = numberof3p2Overlaps;
	}

	public int getNumberof3dOverlaps() {
		return numberof3dOverlaps;
	}

	public void setNumberof3dOverlaps(int numberof3dOverlaps) {
		this.numberof3dOverlaps = numberof3dOverlaps;
	}

	public NumberofGeneOverlaps() {
		super();

		numberofExonOverlaps = 0;
		numberofIntronOverlaps = 0;

		numberof5p1Overlaps = 0;
		numberof5p2Overlaps = 0;
		numberof5dOverlaps = 0;

		numberof3p1Overlaps = 0;
		numberof3p2Overlaps = 0;
		numberof3dOverlaps = 0;

	}

}
