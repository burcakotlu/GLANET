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
    		
    		int i = 0;
    		String argsForGlanetRunner[] = new String[22];
    		
    		argsForGlanetRunner[i++] = args[args.length-22];
    		argsForGlanetRunner[i++] = args[args.length-21];;
    		argsForGlanetRunner[i++] = args[args.length-20];;
    		argsForGlanetRunner[i++] = args[args.length-19];;
    		argsForGlanetRunner[i++] = args[args.length-18];;
    		argsForGlanetRunner[i++] = args[args.length-17];;
    		argsForGlanetRunner[i++] = args[args.length-16];;
    		argsForGlanetRunner[i++] = args[args.length-15];;
    		argsForGlanetRunner[i++] = args[args.length-14];;
    		argsForGlanetRunner[i++] = args[args.length-13];;
    		argsForGlanetRunner[i++] = args[args.length-12];;
    		argsForGlanetRunner[i++] = args[args.length-11];;
    		argsForGlanetRunner[i++] = args[args.length-10];;
    		argsForGlanetRunner[i++] = args[args.length-9];;
    		argsForGlanetRunner[i++] = args[args.length-8];;
    		argsForGlanetRunner[i++] = args[args.length-7];;
    		argsForGlanetRunner[i++] = args[args.length-6];;
    		argsForGlanetRunner[i++] = args[args.length-5];;
    		argsForGlanetRunner[i++] = args[args.length-4];;
    		argsForGlanetRunner[i++] = args[args.length-3];;
    		argsForGlanetRunner[i++] = args[args.length-2];;
    		argsForGlanetRunner[i++] = args[args.length-1];
    		
    		GlanetRunner.setMainView( null);
    		GlanetRunner.setArgs( argsForGlanetRunner);
    		
    	} else if( !GraphicsEnvironment.isHeadless())
    		loadWindow();}
}
