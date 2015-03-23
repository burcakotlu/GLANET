package intervaltree;

public class IntervalTreeProperties {

	// Five properties must hold for red black tree
	boolean everyNodeIsEitherRedorBlack;
	boolean rootIsBlack;
	boolean everySentinelLeafIsBlack;
	boolean everyRedNodeHasBlackChildren;
	boolean allPathsfromRoottoLeavesHasSameNumberofBlackNodes;

	// One property must hold for interval tree
	boolean everyNotSentinelNodeHasRightMaxValue;

	public boolean isEveryNodeIsEitherRedorBlack() {
		return everyNodeIsEitherRedorBlack;
	}

	public void setEveryNodeIsEitherRedorBlack(boolean everyNodeIsEitherRedorBlack) {
		this.everyNodeIsEitherRedorBlack = everyNodeIsEitherRedorBlack;
	}

	public boolean isRootIsBlack() {
		return rootIsBlack;
	}

	public void setRootIsBlack(boolean rootIsBlack) {
		this.rootIsBlack = rootIsBlack;
	}

	public boolean isEverySentinelLeafIsBlack() {
		return everySentinelLeafIsBlack;
	}

	public void setEverySentinelLeafIsBlack(boolean everySentinelLeafIsBlack) {
		this.everySentinelLeafIsBlack = everySentinelLeafIsBlack;
	}

	public boolean isEveryRedNodeHasBlackChildren() {
		return everyRedNodeHasBlackChildren;
	}

	public void setEveryRedNodeHasBlackChildren(boolean everyRedNodeHasBlackChildren) {
		this.everyRedNodeHasBlackChildren = everyRedNodeHasBlackChildren;
	}

	public boolean isAllPathsfromRoottoLeavesHasSameNumberofBlackNodes() {
		return allPathsfromRoottoLeavesHasSameNumberofBlackNodes;
	}

	public void setAllPathsfromRoottoLeavesHasSameNumberofBlackNodes(boolean allPathsfromRoottoLeavesHasSameNumberofBlackNodes) {
		this.allPathsfromRoottoLeavesHasSameNumberofBlackNodes = allPathsfromRoottoLeavesHasSameNumberofBlackNodes;
	}

	public boolean isEveryNotSentinelNodeHasRightMaxValue() {
		return everyNotSentinelNodeHasRightMaxValue;
	}

	public void setEveryNotSentinelNodeHasRightMaxValue(boolean everyNotSentinelNodeHasRightMaxValue) {
		this.everyNotSentinelNodeHasRightMaxValue = everyNotSentinelNodeHasRightMaxValue;
	}

	public IntervalTreeProperties() {
		super();

		// Five properties must hold for red black tree
		this.everyNodeIsEitherRedorBlack = true;
		this.rootIsBlack = true;
		this.everySentinelLeafIsBlack = true;
		this.everyRedNodeHasBlackChildren = true;
		this.allPathsfromRoottoLeavesHasSameNumberofBlackNodes = true;

		// One property must hold for interval tree
		this.everyNotSentinelNodeHasRightMaxValue = true;

	}

}
