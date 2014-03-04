/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package test;

import java.io.BufferedWriter;

public class StringandBufferedWriter {
	
	String name;
	BufferedWriter bufferedWriter;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}
	public void setBufferedWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}
	public StringandBufferedWriter(String name, BufferedWriter bufferedWriter) {
		super();
		this.name = name;
		this.bufferedWriter = bufferedWriter;
	}
	
	

}
