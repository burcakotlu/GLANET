/**
 * 
 */
package enumtypes;

import common.Commons;

/**
 * @author Burçak Otlu
 * @date Jul 25, 2016
 * @project Common 
 *
 */
public enum ToolType {
	
	GLANET(1),
	GAT(2);
	
	private final int toolType;

	/*
	 * This constructor is private.
	 * Legal to declare a non-private constructor, but not legal
	 * to use such a constructor outside the enum.
	 * Can never use "new" with any enum, even inside the enum
	 * class itself.
	 */
	private ToolType(int toolType) {
		this.toolType = toolType;
	}

	public int getToolType() {
		return toolType;
	}

	public static ToolType convertStringtoEnum(String toolType) {

		if( Commons.GLANET.equals(toolType)){
			return GLANET;
		}else if( Commons.GAT.equals(toolType)){
			return GAT;
		}
		else
			return null;
	}

	public String convertEnumtoString() {

		if(this.equals(ToolType.GLANET))
			return Commons.GLANET;
		else if(this.equals(ToolType.GAT))
			return Commons.GAT;
		else
			return null;
	}

	public boolean isGLANET() {
		return this == GLANET;
	}

	public boolean isGAT() {
		return this == GAT;
	}


}
