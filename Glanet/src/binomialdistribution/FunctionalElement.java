/**
 * @author Burcak Otlu
 *
 * 
 */

package binomialdistribution;

import java.util.Comparator;
import java.util.List;

public class FunctionalElement implements Comparable<FunctionalElement> {

	int numberofOccurrencesinSearchInputData;
	int numberofOccurrencesinWholeGenome;
	double probability;

	long genomeSize;
	int searchInputSize;

	String name;

	Double pValue;

	// In case of Functional element is a KEGG Pathway
	String keggPathwayName;
	List<String> keggPathwayGeneIdList;
	List<String> keggPathwayRefSeqGeneNameList;
	List<String> keggPathwayAlternateGeneNameList;

	public String getKeggPathwayName() {

		return keggPathwayName;
	}

	public void setKeggPathwayName( String keggPathwayName) {

		this.keggPathwayName = keggPathwayName;
	}

	public List<String> getKeggPathwayGeneIdList() {

		return keggPathwayGeneIdList;
	}

	public void setKeggPathwayGeneIdList( List<String> keggPathwayGeneIdList) {

		this.keggPathwayGeneIdList = keggPathwayGeneIdList;
	}

	public List<String> getKeggPathwayRefSeqGeneNameList() {

		return keggPathwayRefSeqGeneNameList;
	}

	public void setKeggPathwayRefSeqGeneNameList( List<String> keggPathwayRefSeqGeneNameList) {

		this.keggPathwayRefSeqGeneNameList = keggPathwayRefSeqGeneNameList;
	}

	public List<String> getKeggPathwayAlternateGeneNameList() {

		return keggPathwayAlternateGeneNameList;
	}

	public void setKeggPathwayAlternateGeneNameList( List<String> keggPathwayAlternateGeneNameList) {

		this.keggPathwayAlternateGeneNameList = keggPathwayAlternateGeneNameList;
	}

	public Double getPValue() {

		return pValue;
	}

	public void setPValue( Double value) {

		pValue = value;
	}

	public long getGenomeSize() {

		return genomeSize;
	}

	public void setGenomeSize( long genomeSize) {

		this.genomeSize = genomeSize;
	}

	public int getSearchInputSize() {

		return searchInputSize;
	}

	public void setSearchInputSize( int searchInputSize) {

		this.searchInputSize = searchInputSize;
	}

	public String getName() {

		return name;
	}

	public void setName( String name) {

		this.name = name;
	}

	public int getNumberofOccurrencesinSearchInputData() {

		return numberofOccurrencesinSearchInputData;
	}

	public void setNumberofOccurrencesinSearchInputData( int numberofOccurrencesinSearchInputData) {

		this.numberofOccurrencesinSearchInputData = numberofOccurrencesinSearchInputData;
	}

	public int getNumberofOccurrencesinWholeGenome() {

		return numberofOccurrencesinWholeGenome;
	}

	public void setNumberofOccurrencesinWholeGenome( int numberofOccurrencesinWholeGenome) {

		this.numberofOccurrencesinWholeGenome = numberofOccurrencesinWholeGenome;
	}

	public double getProbability() {

		return probability;
	}

	public void setProbability( double probability) {

		this.probability = probability;
	}

	public FunctionalElement( int numberofOccurrencesinSearchInputData, int numberofOccurrencesinWholeGenome,
			double probability, long genomeSize, int searchInputSize, String name) {

		super();
		this.numberofOccurrencesinSearchInputData = numberofOccurrencesinSearchInputData;
		this.numberofOccurrencesinWholeGenome = numberofOccurrencesinWholeGenome;
		this.probability = probability;
		this.genomeSize = genomeSize;
		this.searchInputSize = searchInputSize;
		this.name = name;
	}

	public FunctionalElement( int numberofOccurrencesinSearchInputData, double probability, int searchInputSize,
			String name) {

		super();
		this.numberofOccurrencesinSearchInputData = numberofOccurrencesinSearchInputData;
		this.probability = probability;
		this.searchInputSize = searchInputSize;
		this.name = name;
	}

	static Comparator<FunctionalElement> P_VALUE = new Comparator<FunctionalElement>() {

		public int compare( FunctionalElement element1, FunctionalElement element2) {

			return element1.getPValue().compareTo( element2.getPValue());

		}
	};

	@Override
	public int compareTo( FunctionalElement element) {

		// TODO Auto-generated method stub
		if( this.pValue < element.getPValue())
			return -1;
		else if( this.pValue == element.getPValue())
			return 0;
		else
			return 1;
	}

}
