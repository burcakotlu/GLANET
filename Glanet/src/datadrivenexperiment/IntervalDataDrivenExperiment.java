package datadrivenexperiment;

/**
 * @author Burçak Otlu
 * @date Apr 17, 2015
 * @project Glanet 
 *
 */
public class IntervalDataDrivenExperiment extends intervaltree.Interval{
	
	boolean removed;
	
	

	
	public boolean isRemoved() {
		return removed;
	}



	public void setRemoved(boolean removed) {
		this.removed = removed;
	}



	



	public IntervalDataDrivenExperiment(int low, int high) {
		super(low, high);
		this.removed = false;
	}

}
