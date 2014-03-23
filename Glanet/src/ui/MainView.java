package ui;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.swing.*;

import common.Commons;

import java.awt.*;              //for layout managers and more
import java.awt.event.*;

public class MainView extends JPanel{

	private MainViewDelegate delegate;
	private JTextField textField1;
	private JTextField textField2;
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
	            //textField1.setText(file.getPath() + "." + System.getProperty("line.separator"));
	            System.out.println("Opening: " + file.getPath() + "." + System.getProperty("line.separator"));
	            
	        } else {
	            System.out.println("Open command cancelled by user." + System.getProperty("line.separator"));
	        }
	        
			delegate.onSetSourceLocationPressed();
		}
	};
	
	
	private ActionListener runButtonPressed = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
				
			System.out.println("Run Pressed");
			
			try {
            	ProcessBuilder pb = new ProcessBuilder(System.getProperty("user.dir") + System.getProperty("file.separator") + "PreparationofOCDSnps.jar", "-jar");
            	pb.directory(new File(System.getProperty("user.dir")));
            	Process p = pb.start();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
	};
	
	public MainView() {
		
		JPanel browseFilePane = createBrowseFileArea( "OCD_GWAS_SNP", textField1);
		JPanel browseFilePane2 = createBrowseFileArea( "Output Folder", textField2);
		runButton = new JButton("Run");
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
    textField = new JTextField(10);
    textField.setActionCommand("Browse File...");
    //textField.addActionListener(textFieldSelected);
    
    //Create some labels for the fields.
    JLabel textFieldLabel = new JLabel("Browse: ");
    textFieldLabel.setLabelFor(textField);
    
    JButton browseButton = new JButton("Browse");
    browseButton.addActionListener( chooseFilePressed);
	
	//Create a label to put messages during an action event.
    JLabel actionLabel = new JLabel(fileType);
    actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

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
    textControlsPane.add(actionLabel, c);
    textControlsPane.setBorder(
            BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder("Choose the location for " + fileType),
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
