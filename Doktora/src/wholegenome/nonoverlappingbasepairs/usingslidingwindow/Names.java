package wholegenome.nonoverlappingbasepairs.usingslidingwindow;

import java.util.ArrayList;
import java.util.List;

public class Names {
	
	List<String> names;

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}
	
	public Names(){
		this.names = new ArrayList<String>();
	}
	

}
