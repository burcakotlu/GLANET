/**
 * 
 */
package datadrivenexperiment;

import java.util.Comparator;

import enumtypes.DataDrivenExperimentElementNameType;
import enumtypes.DataDrivenExperimentElementType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTPMType;

/**
 * @author Burçak Otlu
 * @date Oct 11, 2015
 * @project Glanet 
 *
 */
public class DataDrivenExperimentElementTPM {
	
	//For Element
	DataDrivenExperimentElementNameType elementNameType;
	DataDrivenExperimentElementType elementType;
	Float typeOneError;
	Float power;
	
	//For TPM
	DataDrivenExperimentTPMType tpmType;
	Float tpmValue;
	int numberofEnrichment;
	
	public int getNumberofEnrichment() {
		return numberofEnrichment;
	}
	public void setNumberofEnrichment(int numberofEnrichment) {
		this.numberofEnrichment = numberofEnrichment;
	}

	
	public DataDrivenExperimentElementNameType getElementNameType() {
		return elementNameType;
	}
	public void setElementNameType(DataDrivenExperimentElementNameType elementNameType) {
		this.elementNameType = elementNameType;
	}
	
	public DataDrivenExperimentElementType getElementType() {
		return elementType;
	}
	public void setElementType(DataDrivenExperimentElementType elementType) {
		this.elementType = elementType;
	}
	public Float getTypeOneError() {
		return typeOneError;
	}
	public void setTypeOneError(Float typeOneError) {
		this.typeOneError = typeOneError;
	}

	public Float getPower() {
		return power;
	}
	public void setPower(Float power) {
		this.power = power;
	}
	

	
	public DataDrivenExperimentTPMType getTpmType() {
		return tpmType;
	}
	public void setTpmType(DataDrivenExperimentTPMType tpmType) {
		this.tpmType = tpmType;
	}
	public Float getTpmValue() {
		return tpmValue;
	}
	public void setTpmValue(Float tpmValue) {
		this.tpmValue = tpmValue;
	}


	
	/**
	 * This is a chained comparator that is used to sort a list by multiple
	 * attributes by chaining a sequence of comparators of individual fields
	 * together.

	 *
	 */
	public static class DDEElementTPMChainedComparator implements Comparator<DataDrivenExperimentElementTPM> {
		
		DataDrivenExperimentGeneType geneType = null;
		
		
	 
		public DDEElementTPMChainedComparator(DataDrivenExperimentGeneType geneType) {
			super();
			this.geneType = geneType;
		}



		public int compare(DataDrivenExperimentElementTPM elementTPM1, DataDrivenExperimentElementTPM elementTPM2)
	    {
	        // Assume no nulls, and simple ordinal comparisons
			
			// First by elementType
			int elementTypeResult = elementTPM1.getElementType().convertEnumtoString().compareTo(elementTPM2.getElementType().convertEnumtoString());
		    if (elementTypeResult!=0){
		    	return elementTypeResult;
		    }

	        // Second by elementName
	        int elementNameTypeResult = elementTPM1.getElementNameType().convertEnumtoString().compareTo(elementTPM2.getElementNameType().convertEnumtoString());
	        if (elementNameTypeResult != 0)
	        {
	            return elementNameTypeResult;
	        }

	        // Next by TPMValue
	        switch(geneType){
	        
		        case EXPRESSING_PROTEINCODING_GENES:
		        	//TPMValues must be in the descending order
		        	return elementTPM2.getTpmValue().compareTo(elementTPM1.getTpmValue());
		  	       
		        case NONEXPRESSING_PROTEINCODING_GENES:
		        	//TPMValues must be in the ascending order
		        	return elementTPM1.getTpmValue().compareTo(elementTPM2.getTpmValue());
		        
		        default:
		        	//In ascending order
		        	return elementTPM1.getTpmValue().compareTo(elementTPM2.getTpmValue());
			        
	        }//End of SWITCH for geneType
	        
	       
	    }
	}
	

}
