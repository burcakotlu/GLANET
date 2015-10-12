/**
 * 
 */
package datadrivenexperiment;

import java.util.Iterator;
import java.util.List;

import enumtypes.DataDrivenExperimentCellLineType;
import enumtypes.DataDrivenExperimentDnaseOverlapExclusionType;
import enumtypes.DataDrivenExperimentElementNameType;
import enumtypes.DataDrivenExperimentGeneType;
import enumtypes.DataDrivenExperimentTPMType;
import enumtypes.EnrichmentDecisionType;
import enumtypes.GenerateRandomDataMode;
import enumtypes.MultipleTestingType;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

/**
 * @author Burçak Otlu
 * @date Oct 11, 2015
 * @project Glanet 
 *
 */
public class DataDrivenExperiment {
	
	DataDrivenExperimentCellLineType cellLineType;
	DataDrivenExperimentGeneType geneType;
	DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType;
	GenerateRandomDataMode generateRandomDataMode;
	
	TObjectFloatMap<DataDrivenExperimentTPMType> tpmName2TPMValueMap;
	
	EnrichmentDecisionType enrichmentDecisionType;
	
	List<DataDrivenExperimentElementNameType> histoneElementNameList;
	List<DataDrivenExperimentElementNameType> tfElementNameList;
	
	TObjectIntMap<String> elementNameTPMName2NumberofEnrichmentMap = new TObjectIntHashMap<String>();
	
	int numberofGLANETRuns;
	
	float FDR;
	float bonferroniCorrectionSignificanceLevel;
	MultipleTestingType multipleTestingParameter;
	
	public DataDrivenExperimentCellLineType getCellLineType() {
		return cellLineType;
	}

	public void setCellLineType(DataDrivenExperimentCellLineType cellLineType) {
		this.cellLineType = cellLineType;
	}

	public DataDrivenExperimentGeneType getGeneType() {
		return geneType;
	}

	public void setGeneType(DataDrivenExperimentGeneType geneType) {
		this.geneType = geneType;
	}

	public DataDrivenExperimentDnaseOverlapExclusionType getDnaseOverlapExclusionType() {
		return dnaseOverlapExclusionType;
	}

	public void setDnaseOverlapExclusionType(DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType) {
		this.dnaseOverlapExclusionType = dnaseOverlapExclusionType;
	}

	public GenerateRandomDataMode getGenerateRandomDataMode() {
		return generateRandomDataMode;
	}
	
	public EnrichmentDecisionType getEnrichmentDecisionType() {
		return enrichmentDecisionType;
	}

	public void setEnrichmentDecisionType(EnrichmentDecisionType enrichmentDecisionType) {
		this.enrichmentDecisionType = enrichmentDecisionType;
	}

	public void setGenerateRandomDataMode(GenerateRandomDataMode generateRandomDataMode) {
		this.generateRandomDataMode = generateRandomDataMode;
	}

	public TObjectFloatMap<DataDrivenExperimentTPMType> getTpmName2TPMValueMap() {
		return tpmName2TPMValueMap;
	}

	public void setTpmName2TPMValueMap(TObjectFloatMap<DataDrivenExperimentTPMType> tpmName2TPMValueMap) {
		this.tpmName2TPMValueMap = tpmName2TPMValueMap;
	}


	





	public int getNumberofGLANETRuns() {
		return numberofGLANETRuns;
	}





	public void setNumberofGLANETRuns(int numberofGLANETRuns) {
		this.numberofGLANETRuns = numberofGLANETRuns;
	}





	public float getFDR() {
		return FDR;
	}





	public void setFDR(float fDR) {
		FDR = fDR;
	}





	public float getBonferroniCorrectionSignificanceLevel() {
		return bonferroniCorrectionSignificanceLevel;
	}





	public void setBonferroniCorrectionSignificanceLevel(float bonferroniCorrectionSignificanceLevel) {
		this.bonferroniCorrectionSignificanceLevel = bonferroniCorrectionSignificanceLevel;
	}





	public MultipleTestingType getMultipleTestingParameter() {
		return multipleTestingParameter;
	}





	public void setMultipleTestingParameter(MultipleTestingType multipleTestingParameter) {
		this.multipleTestingParameter = multipleTestingParameter;
	}



	
	


	public List<DataDrivenExperimentElementNameType> getHistoneElementNameList() {
		return histoneElementNameList;
	}

	public void setHistoneElementNameList(List<DataDrivenExperimentElementNameType> histoneElementNameList) {
		this.histoneElementNameList = histoneElementNameList;
	}

	public List<DataDrivenExperimentElementNameType> getTfElementNameList() {
		return tfElementNameList;
	}

	public void setTfElementNameList(List<DataDrivenExperimentElementNameType> tfElementNameList) {
		this.tfElementNameList = tfElementNameList;
	}


	public TObjectIntMap<String> getElementNameTPMName2NumberofEnrichmentMap() {
		return elementNameTPMName2NumberofEnrichmentMap;
	}

	public void setElementNameTPMName2NumberofEnrichmentMap(TObjectIntMap<String> elementNameTPMName2NumberofEnrichmentMap) {
		this.elementNameTPMName2NumberofEnrichmentMap = elementNameTPMName2NumberofEnrichmentMap;
	}

	
	//Initialize With Zeros
	public void initializeElementNameTPMName2NumberofEnrichmentMap(
			List<DataDrivenExperimentElementNameType> tfElementNameList,
			List<DataDrivenExperimentElementNameType> histoneElementNameList,
			TObjectFloatMap<DataDrivenExperimentTPMType> tpmName2TPMValueMap,
			TObjectIntMap<String> elementNameTPMName2NumberofEnrichmentMap){
		
		DataDrivenExperimentElementNameType elementNameType = null;
		DataDrivenExperimentTPMType tpmName = null;
		String key  = null;
		
		
		//TF
		for (Iterator<DataDrivenExperimentElementNameType> itr = tfElementNameList.iterator();itr.hasNext();){
			
			elementNameType = itr.next();
			
			for(TObjectFloatIterator<DataDrivenExperimentTPMType> it =tpmName2TPMValueMap.iterator();it.hasNext();){
				
				it.advance();
				
				tpmName = it.key();
				
				key = elementNameType.convertEnumtoString() + "_" + tpmName.convertEnumtoString();
				
				elementNameTPMName2NumberofEnrichmentMap.put(key, 0);
				
			}//Traverse each TPM in the list
			
		}//Traverse each TFElementName in the list
		
		
		//HISTONE
		for (Iterator<DataDrivenExperimentElementNameType> itr = histoneElementNameList.iterator();itr.hasNext();){
			
			elementNameType = itr.next();
			
			for(TObjectFloatIterator<DataDrivenExperimentTPMType> it =tpmName2TPMValueMap.iterator();it.hasNext();){
				
				it.advance();
				
				tpmName = it.key();
				
				key = elementNameType.convertEnumtoString() + "_" + tpmName.convertEnumtoString();
				
				elementNameTPMName2NumberofEnrichmentMap.put(key, 0);
				
			}//Traverse each TPM in the list
			
		}//Traverse each HISTONEElementName in the list
		

	}

	
	public DataDrivenExperiment(
			DataDrivenExperimentCellLineType cellLineType, 
			DataDrivenExperimentGeneType geneType, 
			DataDrivenExperimentDnaseOverlapExclusionType dnaseOverlapExclusionType, 
			GenerateRandomDataMode generateRandomDataMode, 
			TObjectFloatMap<DataDrivenExperimentTPMType> tpmName2TPMValueMap, 
			EnrichmentDecisionType enrichmentDecisionType, 
			List<DataDrivenExperimentElementNameType> tfElementNameList,
			List<DataDrivenExperimentElementNameType> histoneElementNameList,
			TObjectIntMap<String> elementNameTPMName2NumberofEnrichmentMap, 
			int numberofGLANETRuns, 
			float FDR, 
			float bonferroniCorrectionSignificanceLevel, 
			MultipleTestingType multipleTestingParameter) {
		
		
		super();
		this.cellLineType = cellLineType;
		this.geneType = geneType;
		this.dnaseOverlapExclusionType = dnaseOverlapExclusionType;
		this.generateRandomDataMode = generateRandomDataMode;
		this.tpmName2TPMValueMap = tpmName2TPMValueMap;
		this.enrichmentDecisionType = enrichmentDecisionType;
		this.tfElementNameList = tfElementNameList;
		this.histoneElementNameList = histoneElementNameList;
		this.elementNameTPMName2NumberofEnrichmentMap = elementNameTPMName2NumberofEnrichmentMap;
		this.numberofGLANETRuns = numberofGLANETRuns;
		this.FDR = FDR;
		this.bonferroniCorrectionSignificanceLevel = bonferroniCorrectionSignificanceLevel;
		this.multipleTestingParameter = multipleTestingParameter;
		
		initializeElementNameTPMName2NumberofEnrichmentMap(
				tfElementNameList,
				histoneElementNameList,
				tpmName2TPMValueMap,
				elementNameTPMName2NumberofEnrichmentMap);
		
	}





	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
