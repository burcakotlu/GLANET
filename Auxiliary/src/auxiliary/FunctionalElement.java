/**
 * @author Burcak Otlu
 * Sep 20, 2013
 * 11:14:46 PM
 * 2013
 *
 * 
 */
package auxiliary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FunctionalElement implements Comparable<FunctionalElement> {
	
	String name;
	Float empiricalPValue;
	Float bonferroniCorrectedEmpiricalPValue;
	String description;
	
	//18 FEB 2014 
	int originalNumberofOverlaps;
	int numberofPermutationsHavingOverlapsGreaterThanorEqualto;
	int numberofPermutations;
	int numberofComparisons;
	
	//19 FEB 2014
	float joverMtimesDelta;
	Integer significantFDR;
	
	//In case of Functional element is a KEGG Pathway
	String keggPathwayName;
	List<String> keggPathwayGeneIdList;
	List<String> keggPathwayRefSeqGeneNameList;
	List<String> keggPathwayAlternateGeneNameList;
	
		
	
	

	public int getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() {
		return numberofPermutationsHavingOverlapsGreaterThanorEqualto;
	}

	public void setNumberofPermutationsHavingOverlapsGreaterThanorEqualto(
			int numberofPermutationsHavingOverlapsGreaterThanorEqualto) {
		this.numberofPermutationsHavingOverlapsGreaterThanorEqualto = numberofPermutationsHavingOverlapsGreaterThanorEqualto;
	}

	public Integer getSignificantFDR() {
		return significantFDR;
	}

	public void setSignificantFDR(Integer significantFDR) {
		this.significantFDR = significantFDR;
	}

	public float getJoverMtimesDelta() {
		return joverMtimesDelta;
	}

	public void setJoverMtimesDelta(float joverMtimesDelta) {
		this.joverMtimesDelta = joverMtimesDelta;
	}

		
	public Float getEmpiricalPValue() {
		return empiricalPValue;
	}

	public void setEmpiricalPValue(Float empiricalPValue) {
		this.empiricalPValue = empiricalPValue;
	}

	public Float getBonferroniCorrectedEmpiricalPValue() {
		return bonferroniCorrectedEmpiricalPValue;
	}

	public void setBonferroniCorrectedEmpiricalPValue(
			Float bonferroniCorrectedEmpiricalPValue) {
		this.bonferroniCorrectedEmpiricalPValue = bonferroniCorrectedEmpiricalPValue;
	}

	public int getOriginalNumberofOverlaps() {
		return originalNumberofOverlaps;
	}

	public void setOriginalNumberofOverlaps(int originalNumberofOverlaps) {
		this.originalNumberofOverlaps = originalNumberofOverlaps;
	}

	

	public int getNumberofPermutations() {
		return numberofPermutations;
	}

	public void setNumberofPermutations(int numberofPermutations) {
		this.numberofPermutations = numberofPermutations;
	}

	public int getNumberofComparisons() {
		return numberofComparisons;
	}

	public void setNumberofComparisons(int numberofComparisons) {
		this.numberofComparisons = numberofComparisons;
	}

	public List<String> getKeggPathwayGeneIdList() {
		return keggPathwayGeneIdList;
	}

	public void setKeggPathwayGeneIdList(List<String> keggPathwayGeneIdList) {
		this.keggPathwayGeneIdList = keggPathwayGeneIdList;
	}

	

	public List<String> getKeggPathwayRefSeqGeneNameList() {
		return keggPathwayRefSeqGeneNameList;
	}

	public void setKeggPathwayRefSeqGeneNameList(
			List<String> keggPathwayRefSeqGeneNameList) {
		this.keggPathwayRefSeqGeneNameList = keggPathwayRefSeqGeneNameList;
	}

	public List<String> getKeggPathwayAlternateGeneNameList() {
		return keggPathwayAlternateGeneNameList;
	}

	public void setKeggPathwayAlternateGeneNameList(
			List<String> keggPathwayAlternateGeneNameList) {
		this.keggPathwayAlternateGeneNameList = keggPathwayAlternateGeneNameList;
	}

	public String getKeggPathwayName() {
		return keggPathwayName;
	}

	public void setKeggPathwayName(String keggPathwayName) {
		this.keggPathwayName = keggPathwayName;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	/**
	 * 
	 */
	public FunctionalElement() {
		// TODO Auto-generated constructor stub
	}

	
	public static void traverseList(List<FunctionalElement> list){
		for (FunctionalElement ele: list){
			System.out.println("empirical p value" +"\t" + ele.getEmpiricalPValue() + "\t" + "bonferroni corrected empirical p value" +"\t" + ele.getBonferroniCorrectedEmpiricalPValue());
			
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//For testing purposes
		List<FunctionalElement> list = new ArrayList<FunctionalElement>();
		
		FunctionalElement element1 = new FunctionalElement();
		FunctionalElement element2 = new FunctionalElement();
		FunctionalElement element3= new FunctionalElement();
		FunctionalElement element4 = new FunctionalElement();
		
		element1.setEmpiricalPValue((float)0.3);
		element1.setBonferroniCorrectedEmpiricalPValue((float) 0.05);
		
		element2.setEmpiricalPValue((float)0.5);
		element2.setBonferroniCorrectedEmpiricalPValue((float) 0.01);
		
		element3.setEmpiricalPValue((float)0.2);
		element3.setBonferroniCorrectedEmpiricalPValue((float) 0.04);
		
		element4.setEmpiricalPValue((float)0.1);
		element4.setBonferroniCorrectedEmpiricalPValue((float) 0.02);
		
		list.add(element1);
		list.add(element2);
		list.add(element3);
		list.add(element4);
		
		System.out.println("Before any sort");
		traverseList(list);
		
		
		Collections.sort(list,FunctionalElement.EMPIRICAL_P_VALUE);
		System.out.println("After sort empirical p value");
		traverseList(list);
		
		Collections.sort(list,FunctionalElement.BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE);
		System.out.println("After sort bonferroni corrected empirical p value");
		traverseList(list);
		
		
		// TODO Auto-generated method stub

	}
	
	public static Comparator<FunctionalElement> EMPIRICAL_P_VALUE = 
    		new Comparator<FunctionalElement>() {
				public int compare(FunctionalElement element1, FunctionalElement element2) {
					
					return element1.getEmpiricalPValue().compareTo(element2.getEmpiricalPValue());
					
//					if (element1.getEmpiricalPValue() < element2.getEmpiricalPValue())
//						return -1;
//					else if (element1.getEmpiricalPValue() > element2.getEmpiricalPValue())
//						return 1;
//					else 
//						return 0;
				}
    };
	
    public static Comparator<FunctionalElement> BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE = 
    		new Comparator<FunctionalElement>() {
				public int compare(FunctionalElement element1, FunctionalElement element2) {
					
					return element1.getBonferroniCorrectedEmpiricalPValue().compareTo(element2.getBonferroniCorrectedEmpiricalPValue());
					
//					if (element1.getBonferroniCorrectedEmpiricalPValue() < element2.getBonferroniCorrectedEmpiricalPValue())
//						return -1;
//					else if (element1.getBonferroniCorrectedEmpiricalPValue() > element2.getBonferroniCorrectedEmpiricalPValue())
//						return 1;
//					else 
//						return 0;
				}
    };
	
	  
    public static Comparator<FunctionalElement> BENJAMINI_HOCHBERG_FDR = 
    		new Comparator<FunctionalElement>() {
				public int compare(FunctionalElement element1, FunctionalElement element2) {
					
					return element2.getSignificantFDR().compareTo(element1.getSignificantFDR());
					
				}
    };
	
	
	

	@Override
	public int compareTo(FunctionalElement element) {
		// TODO Auto-generated method stub
		if (this.empiricalPValue < element.getEmpiricalPValue())
			return -1;
		else if (this.empiricalPValue == element.getEmpiricalPValue())
			return 0;
		else 
			return 1;
	}
	
	
	
	

}
