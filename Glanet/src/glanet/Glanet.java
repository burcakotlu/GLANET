/**
 * @author Burcak Otlu
 * Feb 20, 2014
 * 11:10:01 AM
 * 2014
 *
 * 
 */
package glanet;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Glanet extends JPanel{

	/**
	 * 
	 */
	public Glanet() {
		// TODO Auto-generated constructor stub
	}

	
	public void openGLANETGUI(){
		   JFrame frame = new JFrame("GLANET: Genomic Loci ANnotation and Enrichment Tool");
		   
		   frame.setSize(300,300);
		   
//		   Toolkit kit = frame.getToolkit();
//		   Dimension screenSize = kit.getScreenSize();
//		   int screenWidth = screenSize.width;
//		   int screenHeight = screenSize.height;
//		   
//		   Dimension windowSize = frame.getSize();
//		   int windowWidth = windowSize.width;
//		   int windowHeight = windowSize.height;
//		   int upperLeftX = (screenWidth - windowWidth)/2;
//		   int upperLeftY = (screenHeight - windowHeight)/2;
//		   
//		   frame.setLocation(upperLeftX, upperLeftY);
		   
		   
		   
		   JPanel panel = new JPanel();
		   
		    frame.getContentPane().add(panel);           
			  
		   
		   frame.pack();
		   //the window is placed in the center of the screen
		   frame.setLocationRelativeTo(null);
		   frame.setVisible(true);
		
			  
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Glanet glanet = new Glanet();
		glanet.openGLANETGUI();

	}

}
