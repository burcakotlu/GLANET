package ui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Console;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Hello world!
 *
 */
public class App 
{
	public static JFrame mainFrame;
	private static JPanel mainPanel;
	public static ViewController initialViewController;
	
	public static void setInitialViewController( ViewController viewController){
		
		initialViewController = viewController;
	}
	
	public static void loadWindow(){
		
		//Initialize frame
		JFrame frame = new JFrame("GLANET");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setResizable(true);
        
        mainPanel = new JPanel();
        frame.getContentPane().add( mainPanel);
        
        setInitialViewController( new MainViewController(mainPanel));
        
        frame.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		super.windowClosing(e);
        	}
		});
        frame.setVisible(true);
        frame.pack();
        frame.repaint();
	}
	
    public static void main( String[] args) {
    	
    	Console console = System.console();
    	if( console != null){
    		
    		String argsForGlanetRunner[] = null;
    		//initialize the argsForGlanetRunner here. Get the necessary information from args and put it
    		//to argsForGlanetRunner. The format should be in the agreed format 
    		//to pass the appropriate indices see the comment above startRunActionsWithOptions(..) 
    		//in MainViewController 
    		
    		GlanetRunner.setMainView( null);
    		GlanetRunner.setArgs( argsForGlanetRunner);
    		
    	} else if( !GraphicsEnvironment.isHeadless())
    		loadWindow();}
}
