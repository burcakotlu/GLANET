/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package annotate.intervals.parametric;


public class NameandBufferedWriter {
	
	char[] name;
	BufferedWriter bufferedWriter;
	
	public char[] getName() {
		return name;
	}
	public void setName(char[] name) {
		this.name = name;
	}
	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}
	public void setBufferedWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}
	public NameandBufferedWriter(char[] name, BufferedWriter bufferedWriter) {
		super();
		this.name = name;
		this.bufferedWriter = bufferedWriter;
	}
	
	

}
