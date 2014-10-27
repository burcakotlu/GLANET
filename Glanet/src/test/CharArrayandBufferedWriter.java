/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package test;

import java.io.BufferedWriter;


public class CharArrayandBufferedWriter {
	
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
	public CharArrayandBufferedWriter(char[] name, BufferedWriter bufferedWriter) {
		super();
		this.name = name;
		this.bufferedWriter = bufferedWriter;
	}
	
	
	

}
