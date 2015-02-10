/**
 * 
 */
package annotation;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.List;

/**
 * @author Burçak Otlu
 * @date Feb 10, 2015
 * @project Glanet 
 *
 */
public class OverlapInformation {
	
	TIntObjectMap<List<Overlap>> geneId2ExonOverlapListMap;
	TIntObjectMap<List<Overlap>> geneId2IntronOverlapListMap;
	
	TIntObjectMap<List<Overlap>> geneId2Fivep1OverlapListMap;
	TIntObjectMap<List<Overlap>> geneId2Fivep2OverlapListMap;
	TIntObjectMap<List<Overlap>> geneId2FivedOverlapListMap;
	
	TIntObjectMap<List<Overlap>> geneId2Threep1OverlapListMap;
	TIntObjectMap<List<Overlap>> geneId2Threep2OverlapListMap;
	TIntObjectMap<List<Overlap>> geneId2ThreedOverlapListMap;
	
	
	
	
	
	

	public TIntObjectMap<List<Overlap>> getGeneId2ExonOverlapListMap() {
		return geneId2ExonOverlapListMap;
	}







	public void setGeneId2ExonOverlapListMap(TIntObjectMap<List<Overlap>> geneId2ExonOverlapListMap) {
		this.geneId2ExonOverlapListMap = geneId2ExonOverlapListMap;
	}







	public TIntObjectMap<List<Overlap>> getGeneId2IntronOverlapListMap() {
		return geneId2IntronOverlapListMap;
	}







	public void setGeneId2IntronOverlapListMap(TIntObjectMap<List<Overlap>> geneId2IntronOverlapListMap) {
		this.geneId2IntronOverlapListMap = geneId2IntronOverlapListMap;
	}







	public TIntObjectMap<List<Overlap>> getGeneId2Fivep1OverlapListMap() {
		return geneId2Fivep1OverlapListMap;
	}







	public void setGeneId2Fivep1OverlapListMap(TIntObjectMap<List<Overlap>> geneId2Fivep1OverlapListMap) {
		this.geneId2Fivep1OverlapListMap = geneId2Fivep1OverlapListMap;
	}







	public TIntObjectMap<List<Overlap>> getGeneId2Fivep2OverlapListMap() {
		return geneId2Fivep2OverlapListMap;
	}







	public void setGeneId2Fivep2OverlapListMap(TIntObjectMap<List<Overlap>> geneId2Fivep2OverlapListMap) {
		this.geneId2Fivep2OverlapListMap = geneId2Fivep2OverlapListMap;
	}







	public TIntObjectMap<List<Overlap>> getGeneId2FivedOverlapListMap() {
		return geneId2FivedOverlapListMap;
	}







	public void setGeneId2FivedOverlapListMap(TIntObjectMap<List<Overlap>> geneId2FivedOverlapListMap) {
		this.geneId2FivedOverlapListMap = geneId2FivedOverlapListMap;
	}







	public TIntObjectMap<List<Overlap>> getGeneId2Threep1OverlapListMap() {
		return geneId2Threep1OverlapListMap;
	}







	public void setGeneId2Threep1OverlapListMap(TIntObjectMap<List<Overlap>> geneId2Threep1OverlapListMap) {
		this.geneId2Threep1OverlapListMap = geneId2Threep1OverlapListMap;
	}







	public TIntObjectMap<List<Overlap>> getGeneId2Threep2OverlapListMap() {
		return geneId2Threep2OverlapListMap;
	}







	public void setGeneId2Threep2OverlapListMap(TIntObjectMap<List<Overlap>> geneId2Threep2OverlapListMap) {
		this.geneId2Threep2OverlapListMap = geneId2Threep2OverlapListMap;
	}







	public TIntObjectMap<List<Overlap>> getGeneId2ThreedOverlapListMap() {
		return geneId2ThreedOverlapListMap;
	}







	public void setGeneId2ThreedOverlapListMap(TIntObjectMap<List<Overlap>> geneId2ThreedOverlapListMap) {
		this.geneId2ThreedOverlapListMap = geneId2ThreedOverlapListMap;
	}







	public OverlapInformation() {
		super();
		
		geneId2ExonOverlapListMap = new TIntObjectHashMap<List<Overlap>>();
		geneId2IntronOverlapListMap = new TIntObjectHashMap<List<Overlap>>();
		
		geneId2Fivep1OverlapListMap = new TIntObjectHashMap<List<Overlap>>();
		geneId2Fivep2OverlapListMap = new TIntObjectHashMap<List<Overlap>>();
		geneId2FivedOverlapListMap = new TIntObjectHashMap<List<Overlap>>();
		
		geneId2Threep1OverlapListMap = new TIntObjectHashMap<List<Overlap>>();
		geneId2Threep2OverlapListMap = new TIntObjectHashMap<List<Overlap>>();
		geneId2ThreedOverlapListMap = new TIntObjectHashMap<List<Overlap>>();
		
	}
	
	
	
	
	
	

	
}
