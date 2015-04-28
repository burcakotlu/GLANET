/**
 * 
 */
package gc;

import intervaltree.GCIsochoreIntervalTreeHitNode;

import java.util.ArrayList;
import java.util.List;

import enumtypes.IsochoreFamily;

/**
 * @author Burçak Otlu
 * @date Apr 28, 2015
 * @project Glanet 
 *
 */
public class GCIsochoreIntervalTreeFindAllOverlapsResult {
	
	List<GCIsochoreIntervalTreeHitNode> hits;
	float numberofGCs;
	
	float gc;
	IsochoreFamily isochoreFamily;
	
	
	public List<GCIsochoreIntervalTreeHitNode> getHits() {
		return hits;
	}
	public void setHits(List<GCIsochoreIntervalTreeHitNode> hits) {
		this.hits = hits;
	}
	
	
	
	
	
	
	
	
	
	public float getNumberofGCs() {
		return numberofGCs;
	}
	public void setNumberofGCs(float numberofGCs) {
		this.numberofGCs = numberofGCs;
	}
	
	
	public float getGc() {
		return gc;
	}
	public void setGc(float gc) {
		this.gc = gc;
	}
	
	
	
	
	public IsochoreFamily getIsochoreFamily() {
		return isochoreFamily;
	}
	public void setIsochoreFamily(IsochoreFamily isochoreFamily) {
		this.isochoreFamily = isochoreFamily;
	}
	
	
	
	public GCIsochoreIntervalTreeFindAllOverlapsResult() {
		super();
		
		numberofGCs = 0f;
		gc = 0f;
		isochoreFamily = null;
		hits = new ArrayList<GCIsochoreIntervalTreeHitNode>();	
	}
	
	public GCIsochoreIntervalTreeFindAllOverlapsResult(List<GCIsochoreIntervalTreeHitNode> hits, float numberofGCs) {
		super();
		this.hits = hits;
		this.numberofGCs = numberofGCs;
	}
	

	
}
