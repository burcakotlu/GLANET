package create.ucscgenome;

import java.util.Comparator;

import enumtypes.ChromosomeName;
import enumtypes.IntervalName;




public class RefSeqGeneInterval implements Comparable<RefSeqGeneInterval>{
	 ChromosomeName chromName;
	 int intervalStart;
	 int intervalEnd;	 
	 String refSeqGeneName;
	 IntervalName intervalName;
	 int intervalNumber;
	 char strand;
	 String alternateGeneName;
	 Integer geneId;
	 
	 
	 
	public Integer getGeneId() {
		return geneId;
	}
	public void setGeneId(Integer geneId) {
		this.geneId = geneId;
	}


	public int getIntervalEnd() {
		return intervalEnd;
	}
	public void setIntervalEnd(int intervalEnd) {
		this.intervalEnd = intervalEnd;
	}
	public String getRefSeqGeneName() {
		return refSeqGeneName;
	}
	public void setRefSeqGeneName(String refSeqGeneName) {
		this.refSeqGeneName = refSeqGeneName;
	}
	
	public char getStrand() {
		return strand;
	}
	public void setStrand(char strand) {
		this.strand = strand;
	}
	public String getAlternateGeneName() {
		return alternateGeneName;
	}
	public void setAlternateGeneName(String alternateGeneName) {
		this.alternateGeneName = alternateGeneName;
	}

	
	
	public ChromosomeName getChromName() {
		return chromName;
	}
	public void setChromName(ChromosomeName chromName) {
		this.chromName = chromName;
	}
	public int getIntervalStart() {
		return intervalStart;
	}
	public void setIntervalStart(int intervalStart) {
		this.intervalStart = intervalStart;
	}
	public IntervalName getIntervalName() {
		return intervalName;
	}
	public void setIntervalName(IntervalName intervalName) {
		this.intervalName = intervalName;
	}
	public int getIntervalNumber() {
		return intervalNumber;
	}
	public void setIntervalNumber(int intervalNumber) {
		this.intervalNumber = intervalNumber;
	}



	static final Comparator<RefSeqGeneInterval> START_POSITION_ORDER = 
        new Comparator<RefSeqGeneInterval>() {
		 	public int compare(RefSeqGeneInterval refSeqGeneInterval1, RefSeqGeneInterval  refSeqGeneInterval2) {
		 		return refSeqGeneInterval1.compareTo(refSeqGeneInterval2);
		 	}
	 	};



	@Override
	public int compareTo(RefSeqGeneInterval refSeqGeneInterval) {
		if (this.intervalStart < refSeqGeneInterval.getIntervalStart())
			return -1;
		else if (this.intervalStart == refSeqGeneInterval.getIntervalStart())
			return 0;
		else 
			return 1;
	
	
	}
	 
}
