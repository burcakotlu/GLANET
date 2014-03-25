package ui;

import java.io.File;
import javax.swing.*;
import common.Commons;
import java.awt.*;              //for layout managers and more
import java.awt.event.*;

public class MainView extends JPanel{

	private MainViewDelegate delegate;
	private JTextField outputTextField;
	private JTextField inputTextField;
	JPanel listPane;
	JButton runButton;
	
	public interface MainViewDelegate {
		
		public void onSetSourceLocationPressed();
	}
	
	private ActionListener chooseFilePressed = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int returnVal = fc.showOpenDialog(MainView.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            //This is where a real application would open the file.
	            if( e.getActionCommand() == "Output Folder")
	            	outputTextField.setText( file.getPath() + System.getProperty("file.separator"));
	            else if( e.getActionCommand() == "Input Folder")
	            	inputTextField.setText( file.getPath() + System.getProperty("file.separator"));
	            
	        } else {
	            System.out.println("Open command cancelled by user." + System.getProperty("line.separator"));
	        }
	        
			delegate.onSetSourceLocationPressed();
		}
	};
	
	
	private ActionListener runButtonPressed = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
				
		}
	};
	
	public MainView() {
		
		inputTextField = new JTextField(30);
		outputTextField = new JTextField(30);
		runButton = new JButton("Run");
		
		JPanel browseFilePane = createBrowseFileArea( "Input Folder", inputTextField);
		JPanel browseFilePane2 = createBrowseFileArea( "Output Folder", outputTextField);
		listPane = new JPanel();
		
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		
		runButton.addActionListener(runButtonPressed);
		
		listPane.add(browseFilePane);
		listPane.add(browseFilePane2);
        listPane.add(runButton);
        
        add(listPane);
	}
	
JPanel createBrowseFileArea( String fileType, JTextField textField){
	
	//Create a regular text field.
    textField.setActionCommand("Browse File...");
    
    //Create some labels for the fields.
    JLabel textFieldLabel = new JLabel("Browse: ");
    textFieldLabel.setLabelFor(textField);
    
    JButton browseButton = new JButton("Browse");
    browseButton.addActionListener( chooseFilePressed);
    browseButton.setActionCommand(fileType);

    //Lay out the text controls and the labels.
    JPanel textControlsPane = new JPanel();
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();

    textControlsPane.setLayout(gridbag);

    JButton[] labels = {browseButton};
    JTextField[] textFields = {textField};
    addLabelTextRows(labels, textFields, gridbag, textControlsPane);

    c.gridwidth = GridBagConstraints.REMAINDER; //last
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 1.0;
    textControlsPane.setBorder(
            BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder(fileType),
                            BorderFactory.createEmptyBorder(5,5,5,5)));

    //Put everything together.
    JPanel browseFilePane = new JPanel(new BorderLayout());
    browseFilePane.add(textControlsPane,
                 BorderLayout.PAGE_START);
    
    return browseFilePane;
}
private void addLabelTextRows(JButton[] labels, JTextField[] textFields, GridBagLayout gridbag, Container container) {
	GridBagConstraints c = new GridBagConstraints();
	c.anchor = GridBagConstraints.EAST;
	int numLabels = labels.length;
	
	for (int i = 0; i < numLabels; i++) {
		c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
		c.fill = GridBagConstraints.NONE;      //reset to default
		c.weightx = 0.0;                       //reset to default
		container.add(labels[i], c);
		
		c.gridwidth = GridBagConstraints.REMAINDER;     //end row
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		container.add(textFields[i], c);
	}
}
	
	public void setDelegate( MainViewDelegate delegate){
		
		this.delegate = delegate;
	}
}
