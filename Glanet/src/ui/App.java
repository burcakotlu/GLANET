package ui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Commons;

/**
 * GLANET GUI Application
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
	
	//args contains all arguments including memory arguments, jar files and class paths
	//argsForGlanetRunner contains only the arguments required in the GUI
	public static void main(String args[]) {
		
		//if Command Line
    	if( args.length > 0 && args[args.length-1].startsWith( "--c")) {
    		
    		String[] argsForGlanetRunner = new String[Commons.NUMBER_OF_PROGRAM_RUNTIME_ARGUMENTS];
    		
    		for(int i = 0; i < Commons.NUMBER_OF_PROGRAM_RUNTIME_ARGUMENTS; i++) {
    			argsForGlanetRunner[i] = args[args.length-(Commons.NUMBER_OF_PROGRAM_RUNTIME_ARGUMENTS+1)+i];
    		}
    		
    		GlanetRunner.setMainView( null);
    		GlanetRunner.setArgs( argsForGlanetRunner);
    		
    		GlanetRunner runner = new GlanetRunner();
    		new Thread( runner).start();
    	} else if( !GraphicsEnvironment.isHeadless())
    		loadWindow();
	}
}
