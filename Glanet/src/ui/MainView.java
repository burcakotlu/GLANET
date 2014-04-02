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
	private JPanel listPane;
	private JButton runButton;
	private JComboBox<String> generateRandomDataModeCombo;
	private JComboBox<String> numberOfPerCombo;
	private JComboBox<String> inputFormatCombo;
	private JComboBox<String> enrichmentTypeCombo;
	
	public interface MainViewDelegate {
		
		public void startRunActionsWithOptions(String inputFolder, 
											   String inputFormat, 
											   String dataMode, 
											   String numberOfPermutations,
											   String enrichmentType,
											   String outputFolder);
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
	            
	        }
		}
	};
	
	
	private ActionListener runButtonPressed = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if( inputTextField.getText().length() <= 0 || outputTextField.getText().length() <= 0)
				JOptionPane.showMessageDialog(null, "Please fill all the options");
			else {
				
				//delegation to handle the run process.
				delegate.startRunActionsWithOptions(inputTextField.getText(), 
						inputFormatCombo.getSelectedItem().toString(), 
						generateRandomDataModeCombo.getSelectedItem().toString(), 
						numberOfPerCombo.getSelectedItem().toString(), 
						enrichmentTypeCombo.getSelectedItem().toString(), 
						outputTextField.getText());
			}
		}
	};
	
	public MainView() {
		
		inputTextField = new JTextField(20);
		outputTextField = new JTextField(30);
		runButton = new JButton("Run");
		runButton.addActionListener(runButtonPressed);
		
		JPanel inputBrowseAndOptionPane = new JPanel();
		JPanel inputBrowseFilePane = createBrowseFileArea( "Input Folder", inputTextField);
		JPanel outputBrowseFilePane = createBrowseFileArea( "Output Folder", outputTextField);
		listPane = new JPanel();
		
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		inputBrowseAndOptionPane.setLayout(new FlowLayout());
		
		String[] generateRandomDataModeSet = { "With GC and Mapability", "Without GC and Mapability" };
		String[] numberOfPermutations = { "5000", "10000", "50000", "100000" };
		
		String[] inputFormat = { 	Commons.GUI_INPUT_FILE_FORMAT_DBSNP_IDS, 
									Commons.GUI_INPUT_FILE_FORMAT_BED,
									Commons.GUI_INPUT_FILE_FORMAT_GFF3,
									Commons.GUI_INPUT_FILE_FORMAT_0_BASED_COORDINATES,
									Commons.GUI_INPUT_FILE_FORMAT_1_BASED_COORDINATES};
		
		String[] enrichmentType = { "DNase Hypersensitive sites",
									"Histone Modifications",
									"Transcription Factors (TFs)",
									"Kegg Pathway",
									"TFs and Kegg Pathway" };
		generateRandomDataModeCombo = new JComboBox<String>( generateRandomDataModeSet);
		numberOfPerCombo = new JComboBox<String>( numberOfPermutations);
		numberOfPerCombo = new JComboBox<String>( numberOfPermutations);
		inputFormatCombo = new JComboBox<String>( inputFormat);
		enrichmentTypeCombo = new JComboBox<String>( enrichmentType);
		
		inputBrowseAndOptionPane.add(inputBrowseFilePane);
		inputBrowseAndOptionPane.add(createBorderedPanel("Input Format", inputFormatCombo));
		
		listPane.add(inputBrowseAndOptionPane);
		listPane.add(createBorderedPanel("Generate Random Data Mode", generateRandomDataModeCombo));
		listPane.add(createBorderedPanel("Number of Permutations", numberOfPerCombo));
		listPane.add(createBorderedPanel("Enrichment Type", enrichmentTypeCombo));
		listPane.add(outputBrowseFilePane);
        listPane.add(runButton);
        
        add(listPane);
	}
	
JPanel createBorderedPanel( String borderName, JComponent panel){
    
    //Create some labels for the fields.
    JLabel componentLabel = new JLabel();
    componentLabel.setLabelFor(panel);

    //Lay out the text controls and the labels.
    JPanel paneControlsPane = new JPanel();
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();

    paneControlsPane.setLayout(gridbag);

    JLabel[] labels = {componentLabel};
    JComponent[] components = {panel};
    addLabelTextRows(labels, components, gridbag, paneControlsPane);

    c.gridwidth = GridBagConstraints.REMAINDER; //last
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 1.0;
    paneControlsPane.setBorder(
            BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder(borderName),
                            BorderFactory.createEmptyBorder(5,5,5,5)));

    //Put everything together.
    JPanel browseFilePane = new JPanel(new BorderLayout());
    browseFilePane.add(paneControlsPane,
                 BorderLayout.PAGE_START);
    
    return browseFilePane;
}
JPanel createBrowseFileArea( String fileType, JTextField textField){
    
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
private void addLabelTextRows(JComponent[] labels, JComponent[] textFields, GridBagLayout gridbag, Container container) {
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
