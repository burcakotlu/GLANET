
package auxiliary;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * @author Burçak Otlu
 * @date May 20, 2015
 * @project Glanet 
 *
 */
public class StatisticsConversion {
	
	// Static normal distribution dist in order to transform z-socres into probabilities.
    private static NormalDistribution dist = new NormalDistribution();

    static {
    	//Create a normal distribution with mean equal to zero and standard deviation equal to one.
        dist = new NormalDistribution();
    }
    
    

    
    
    /**
     * The quantile calculation is done here.
     *
     * @return double a p value for P(X > x).
     */
    public static double cumulativeProbability(double aZValue) {
        double lQuantile = 0.5;
        
        //For a random variable X whose values are distributed according to this distribution, this method returns P(X <= x).
        lQuantile = 1 - dist.cumulativeProbability(aZValue);
      
        return lQuantile;
    }


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(Math.log1p(0));
		System.out.println(Math.log1p(1/10000));
		System.out.println(Math.log1p(2/10000));
		
		System.out.println(StatisticsConversion.cumulativeProbability(3));
		System.out.println(StatisticsConversion.cumulativeProbability(-3));
		
	}

}
