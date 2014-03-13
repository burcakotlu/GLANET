package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class MainView extends JPanel{

	public interface MainViewDelegate {
		
		public void onSetSourceLocationPressed();
	}
	
	private MainViewDelegate delegate;
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==btnSourceLocation){
				
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int returnVal = fc.showOpenDialog(MainView.this);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            //This is where a real application would open the file.
		            log.append("Opening: " + file.getPath() + "." + System.getProperty("line.separator"));
		        } else {
		            log.append("Open command cancelled by user." + System.getProperty("line.separator"));
		        }
		        log.setCaretPosition(log.getDocument().getLength());
		        
				delegate.onSetSourceLocationPressed();
			}
		}
	};


	private JButton btnSourceLocation;
	private JTextArea log;
	
	public MainView() {
		
		add(new JLabel("GLANET"));
		btnSourceLocation = new JButton("Choose Folder");
		
		log = new JTextArea();
		
		btnSourceLocation.addActionListener( actionListener );
		
		add(btnSourceLocation);
		add(log);
	}
	
	public void setDelegate( MainViewDelegate delegate){
		
		this.delegate = delegate;
	}
}
